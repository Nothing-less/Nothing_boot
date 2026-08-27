package mu.nothingless.utils;

import java.util.Map;

import lombok.Data;

@Data
public class RetResult<T> {

    public static final int SUCCESS = 200;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int CONFLICT = 409;
    public static final int UNPROCESSABLE_ENTITY = 422;
    public static final int INTERNAL_SERVER_ERROR = 500;

    private Integer code;
    private String message;
    private T data;
    private Long timestamp;
    private Map<String, Object> extra;

    public RetResult<T> extraData(Map<String, Object> extra) {
        if (extra == null || extra.isEmpty()) {
            return this;
        }
        if (this.extra == null) {
            this.extra = new java.util.HashMap<>();
        }
        this.extra.putAll(extra);
        return this;
    }

    public RetResult<T> extraData(String key, Object value) {
        if (this.extra == null) {
            this.extra = new java.util.HashMap<>();
        }
        this.extra.put(key, value);
        return this;
    }

    private RetResult() {
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> RetResult<T> success() {
        RetResult<T> result = new RetResult<>();
        result.setCode(SUCCESS);
        result.setMessage("success");
        return result;
    }

    public static <T> RetResult<T> success(T data) {
        RetResult<T> result = new RetResult<>();
        result.setCode(SUCCESS);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static <T> RetResult<T> ok() {
        return success();
    }

    public static <T> RetResult<T> ok(T data) {
        return success(data);
    }

    public static <T> RetResult<T> error(String message) {
        RetResult<T> result = new RetResult<>();
        result.setCode(INTERNAL_SERVER_ERROR);
        result.setMessage(message);
        return result;
    }

    public static <T> RetResult<T> error(Integer code, String message) {
        RetResult<T> result = new RetResult<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> RetResult<T> of(boolean successFlag, String message, T data) {
        if (successFlag) {
            return success(data);
        }
        return error(INTERNAL_SERVER_ERROR, message);
    }

    // =========== Universal error returns ===========
    public static <T> RetResult<T> fail() {
        return error(INTERNAL_SERVER_ERROR, "fail");
    }

    public static <T> RetResult<T> fail(String message) {
        return error(INTERNAL_SERVER_ERROR, message);
    }

    public static <T> RetResult<T> fail(Integer code, String message) {
        return error(code, message);
    }

    public static <T> RetResult<T> unauthorized() {
        return error(UNAUTHORIZED, "unauthorized");
    }

    public static <T> RetResult<T> forbidden() {
        return error(FORBIDDEN, "forbidden");
    }

    public static <T> RetResult<T> notFound() {
        return error(NOT_FOUND, "not found");
    }

    public static <T> RetResult<T> badRequest() {
        return error(BAD_REQUEST, "bad request");
    }

    public static <T> RetResult<T> badRequest(String message) {
        return error(BAD_REQUEST, message);
    }

    public static <T> RetResult<T> validateError(String message) {
        return error(UNPROCESSABLE_ENTITY, message);
    }

    public static <T> RetResult<T> conflict() {
        return error(CONFLICT, "conflict");
    }

    public static <T> RetResult<T> conflict(String message) {
        return error(CONFLICT, message);
    }

    public static <T> RetResult<T> serverError() {
        return error(INTERNAL_SERVER_ERROR, "internal server error");
    }

    public static <T> RetResult<T> serverError(String message) {
        return error(INTERNAL_SERVER_ERROR, message);
    }
}