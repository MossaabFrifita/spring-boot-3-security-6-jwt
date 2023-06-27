package fr.mossaab.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/resource")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
@Tag(name = "Authorization", description = "The Authorization API. Contains a secure hello method")
public class AuthorizationController {



    @GetMapping
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    @Operation(
            description = "This endpoint require a valid JWT, ADMIN or USER role with READ_PRIVILEGE",
            summary = "Hello secured endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }

    )
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from secured endpoint with READ_PRIVILEGE AND USER OR ADMIN ROLE");
    }



}
