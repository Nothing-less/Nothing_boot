package mu.nothingless.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccessTokenResponse(
        @JsonProperty("_token_access") String accessToken,
        @JsonProperty("_time_expire") Long expiresIn
) {}
