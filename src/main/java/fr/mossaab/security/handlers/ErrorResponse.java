package fr.mossaab.security.handlers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ErrorResponse {
    private int statusCode;
    private Instant timestamp;
    private String message;
    private String description;
}
