package club.bluezenith.ui.alt.rewrite.iptracker;

import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

public class IpAddress {
    private final String ipHash;
    private final boolean isBanned;

    public IpAddress(String ip, boolean banned) {
        this.ipHash = Hashing.sha1().hashString(ip, Charset.defaultCharset()).toString();
        this.isBanned = banned;
    }

    public IpAddress(String ipHash) {
        this.ipHash = ipHash;
        this.isBanned = true;
    }

    public boolean compareHash(String ipHash) {
        return this.ipHash.equals(ipHash);
    }

    public boolean compareIP(String ip) {
        return compareHash(Hashing.sha1().hashString(ip, Charset.defaultCharset()).toString());
    }

    public boolean isBanned() {
        return this.isBanned;
    }

    public String getIpHash() {
        return this.ipHash;
    }
}
