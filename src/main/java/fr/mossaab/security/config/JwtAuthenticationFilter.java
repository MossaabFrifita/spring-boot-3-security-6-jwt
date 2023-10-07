package fr.mossaab.security.config;

import fr.mossaab.security.service.JwtService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
    * Our jwt class extends OnePerRequestFilter to be executed on every http request
    * We can also implement the Filter interface (jakarta EE), but Spring gives us a OncePerRequestFilter
        class that extends the GenericFilterBean, which also implements the Filter interface.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; /** implementation is provided in config.ApplicationSecurityConfig */

    @Override
    protected void doFilterInternal(
           @NonNull HttpServletRequest request,
           @NonNull HttpServletResponse response,
           @NonNull FilterChain filterChain
    ) throws ServletException, IOException {


        // try to get JWT in cookie or in Authorization Header
        String jwt = jwtService.getJwtFromCookies(request);
        final String authHeader = request.getHeader("Authorization");

        if((jwt == null && (authHeader ==  null || !authHeader.startsWith("Bearer "))) || request.getRequestURI().contains("/auth")){
            filterChain.doFilter(request, response);
            return;
        }

        // If the JWT is not in the cookies but in the "Authorization" header
        if (jwt == null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // after "Bearer "
        }


        final String userEmail =jwtService.extractUserName(jwt);
        /*
           SecurityContextHolder: is where Spring Security stores the details of who is authenticated.
           Spring Security uses that information for authorization.*/

        if(StringUtils.isNotEmpty(userEmail)
                && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if(jwtService.isTokenValid(jwt, userDetails)){
                //update the spring security context by adding a new UsernamePasswordAuthenticationToken
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request,response);
    }
}
