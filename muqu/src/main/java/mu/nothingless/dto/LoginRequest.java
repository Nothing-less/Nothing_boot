package mu.nothingless.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @JsonProperty("userAccount")
        @NotBlank(message = "用户名不能为空")
        String userAccount,

        @JsonProperty("password")
        @NotBlank(message = "密码不能为空")
        String password
) {}