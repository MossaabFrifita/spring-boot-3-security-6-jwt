package fr.mossaab.security.payload.request;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;

}
