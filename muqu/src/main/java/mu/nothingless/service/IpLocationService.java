package mu.nothingless.service;

import java.net.InetAddress;

import org.lionsoul.ip2region.xdb.LongByteArray;
import org.lionsoul.ip2region.xdb.Searcher;
import org.lionsoul.ip2region.xdb.Version;
import org.springframework.stereotype.Component;

@Component
public class IpLocationService {

    private final Searcher ipv4Searcher;
    private final Searcher ipv6Searcher;

    public IpLocationService() {
        Searcher v4 = null;
        Searcher v6 = null;
        Throwable v4Err = null;
        Throwable v6Err = null;

        try {
            v4 = loadSearcher("ip2region/ip2region_v4.xdb");
        } catch (Exception e) {
            v4Err = e;
        }
        try {
            v6 = loadSearcher("ip2region/ip2region_v6.xdb");
        } catch (Exception e) {
            v6Err = e;
        }

        // 异常链带上，别只抛一句看不懂的话
        if (v4 == null) {
            throw new IllegalStateException("加载 IPv4 库失败，请检查 ip2region_v4.xdb 是否为完整二进制文件", v4Err);
        }
        if (v6 == null) {
            throw new IllegalStateException("加载 IPv6 库失败，请检查 ip2region_v6.xdb 是否为完整二进制文件", v6Err);
        }

        this.ipv4Searcher = v4;   // 单次赋值，final 也认
        this.ipv6Searcher = v6;
    }

    public String getCity(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            Searcher searcher = address.getAddress().length == 4 ? ipv4Searcher : ipv6Searcher;
            String region = searcher.search(ip);
            String[] parts = region.split("\\|", -1);

            if (parts.length < 4) {
                return "未知";
            }

            return parts[2] + "|" + parts[3];
        } catch (Exception e) {
            return "未知";
        }
    }

    private Searcher loadSearcher(String classpathLocation) throws Exception {
        LongByteArray buffer;
        try (var inputStream = getClass().getClassLoader().getResourceAsStream(classpathLocation)) {
            if (inputStream == null) {
                throw new IllegalStateException("找不到资源文件: " + classpathLocation);
            }
            buffer = Searcher.loadContentFromInputStream(inputStream);
        }

        Version version = Version.fromHeader(Searcher.loadHeaderFromBuffer(buffer));
        return Searcher.newWithBuffer(version, buffer);
    }
}
