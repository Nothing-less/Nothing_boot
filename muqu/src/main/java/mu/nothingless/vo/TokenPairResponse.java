package mu.nothingless.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenPairResponse(
        @JsonProperty("_token_access") String accessToken,
        @JsonProperty("_token_refresh") String refreshToken,
        @JsonProperty("_time_expire") Long expiresIn
) {}