package qu.nothingless.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** 购物车领域异常 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CartException extends RuntimeException {
    private static final long serialVersionUID = 10001L;

    public CartException(String message) {
        super(message);
    }

    public CartException(String message, Throwable cause) {
        super(message, cause);
    }
}