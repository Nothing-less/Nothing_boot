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
        try {
            this.ipv4Searcher = loadSearcher("ip2region/ip2region_v4.xdb");
            this.ipv6Searcher = loadSearcher("ip2region/ip2region_v6.xdb");
        } catch (Exception e) {
            throw new IllegalStateException("加载 ip2region IPv4/IPv6 数据库失败", e);
        }
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
