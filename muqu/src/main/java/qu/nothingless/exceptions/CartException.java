package qu.nothingless.exceptions;

/** 购物车领域异常 */
public  class CartException extends RuntimeException {
    private static final long serialVersionUID = 10001L;

    public CartException(String message) {
        super(message);
    }

    public CartException(String message, Throwable cause) {
        super(message, cause);
    }
}