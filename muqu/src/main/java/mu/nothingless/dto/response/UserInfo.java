package mu.nothingless.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserInfo(
        @JsonProperty("userId") String userId
) {}