package fr.mossaab.security.service;

import fr.mossaab.security.payload.request.AuthenticationRequest;
import fr.mossaab.security.payload.request.RegisterRequest;
import fr.mossaab.security.payload.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
