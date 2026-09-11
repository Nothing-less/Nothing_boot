package mu.nothingless.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserInfo(
        @JsonProperty("userId") String userId
) {}