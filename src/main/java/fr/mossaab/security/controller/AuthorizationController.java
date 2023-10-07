package fr.mossaab.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
@Tag(name = "Authorization", description = "The Authorization API. Contains a secure hello method")
public class AuthorizationController {



    @GetMapping("/admin/resource")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE') and hasRole('ADMIN')")
    @Operation(
            description = "This endpoint require a valid JWT, ADMIN role with READ_PRIVILEGE",
            summary = "Hello secured endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    public ResponseEntity<String> sayHelloWithRoleAdminAndReadAuthority() {
        return ResponseEntity.ok("Hello, you have access to a protected resource that requires admin role and read authority.");
    }

    @DeleteMapping("/admin/resource")
    @PreAuthorize("hasAuthority('DELETE_PRIVILEGE') and hasRole('ADMIN')")
    public ResponseEntity<String> sayHelloWithRoleAdminAndDeleteAuthority() {
        return ResponseEntity.ok("Hello, you have access to a protected resource that requires admin role and delete authority.");
    }
    @PostMapping("/user/resource")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE') and hasAnyRole('ADMIN','USER')")
    public ResponseEntity<String> sayHelloWithRoleUserAndCreateAuthority() {
        return ResponseEntity.ok("Hello, you have access to a protected resource that requires user role and write authority.");
    }
    @PutMapping("/user/resource")
    @PreAuthorize("hasAuthority('UPDATE_PRIVILEGE') and hasAnyRole('ADMIN','USER')")
    public ResponseEntity<String> sayHelloWithRoleUserAndUpdateAuthority() {
        return ResponseEntity.ok("Hello, you have access to a protected resource that requires user role and update authority.");
    }

}
