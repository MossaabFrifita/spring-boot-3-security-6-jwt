package fr.mossaab.security.service;

import fr.mossaab.security.entities.RefreshToken;
import fr.mossaab.security.payload.request.RefreshTokenRequest;
import fr.mossaab.security.payload.response.RefreshTokenResponse;

import java.util.Optional;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(Long userId);
    RefreshToken verifyExpiration(RefreshToken token);
    Optional<RefreshToken> findByToken(String token);

    RefreshTokenResponse generateNewToken(RefreshTokenRequest request);

}
