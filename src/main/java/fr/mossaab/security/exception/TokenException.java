package fr.mossaab.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenException extends RuntimeException {

    public TokenException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }

}
