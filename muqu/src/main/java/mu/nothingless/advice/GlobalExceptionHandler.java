package mu.nothingless.advice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import mu.nothingless.exceptions.BusinessException;
import mu.nothingless.utils.RetResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "mu.nothingless.controller")
public class GlobalExceptionHandler {

    /** 业务异常 */
    @ExceptionHandler(BusinessException.class)
    public RetResult<Void> handleBusinessException(BusinessException e) {
        log.error("Business Service Exception Occurred:       \n", e);
        return RetResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public RetResult<Void> handleRuntimeException(RuntimeException e) {
        log.error("Runtime Exception Occurred:                \n", e);
        return RetResult.error(500, "系统内部错误");
    }

    /** Token 过期 */
    @ExceptionHandler(ExpiredJwtException.class)
    public RetResult<Void> handleExpiredJwt(ExpiredJwtException e) {
        log.error("Expired JWT Service Exception Occurred:    \n", e);
        return RetResult.error(RetResult.UNAUTHORIZED, "Token 已过期");
    }

    /** Token 无效（签名错误、格式错误等） */
    @ExceptionHandler({ JwtException.class, IllegalArgumentException.class })
    public RetResult<Void> handleJwtException(Exception e) {
        log.error("JWT Service Exception Occurred:            \n", e);
        return RetResult.error(RetResult.UNAUTHORIZED, "Token 无效");
    }

    /** Spring Security 认证失败（用户名密码错误等） */
    @ExceptionHandler(AuthenticationException.class)
    public RetResult<Void> handleAuthentication(AuthenticationException e) {
        log.error("Authentication Service Exception Occurred: {}", e.getMessage());
        return RetResult.error(RetResult.UNAUTHORIZED, "认证失败：" + e.getMessage());
    }

    @ExceptionHandler({ BindException.class, ConstraintViolationException.class })
    public RetResult<Void> handleValidation(Exception e) {
        String message = e.getMessage();
        if (e instanceof BindException) {
            message = ((BindException) e).getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        }
        log.error("Parameter validation failure:\n {}", message);
        return RetResult.error(400, message);
    }

    /** 兜底异常 */
    @ExceptionHandler(Exception.class)
    public RetResult<Void> handleException(Exception e) {
        log.error("Unexpected Exception Occurred:\n", e);
        return RetResult.serverError(e.getMessage());
    }
}