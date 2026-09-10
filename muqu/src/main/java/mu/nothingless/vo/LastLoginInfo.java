package mu.nothingless.vo;

import java.time.LocalDateTime;

public record LastLoginInfo(String ip, String city, String deviceFingerprint, LocalDateTime time) {}
