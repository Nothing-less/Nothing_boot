package mu.nothingless.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mu.nothingless.utils.RetResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "mu.nothingless.controller")
public class RetResultResponseAdvice implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        if (returnType.hasMethodAnnotation(IgnoreWrapper.class)) {
            return false;
        }
        return !RetResult.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        String path = request.getURI().getPath();
        
        // 排除 Swagger、Actuator、错误页
        if (path != null && (
                path.startsWith("/swagger") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/actuator") ||
                path.startsWith("/error"))) {
            return body;
        }

        log.debug("统一包装响应: path={}, originalBodyType={}",
                path, body != null ? body.getClass().getSimpleName() : "null");

        // 非 JSON 响应不包装（文件下载、纯文本等）
        if (selectedContentType != null
                && !selectedContentType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
            return body;
        }

        // 已经被包装
        if (body instanceof RetResult) {
            return body;
        }

        RetResult<Object> result = RetResult.success(body);

        // String 处理
        if (body instanceof String) {
            try {
                return objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException e) {
                log.error("响应序列化失败, path={}", path, e);
                throw new RuntimeException("响应序列化失败", e);
            }
        }

        return result;
    }
}