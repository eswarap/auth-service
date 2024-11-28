package org.woven.apigateway.exception;

import java.io.Serial;

public class AuthException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public AuthException(final String message) {
        super(message);
    }

    public AuthException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AuthException(final Throwable cause) {
        super(cause);
    }
}
