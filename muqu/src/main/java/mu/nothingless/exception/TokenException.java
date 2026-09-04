package mu.nothingless.exception;

import java.io.IOException;

public class TokenException extends IOException {
    public TokenException(String message) {
        super(message);
    }
    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
