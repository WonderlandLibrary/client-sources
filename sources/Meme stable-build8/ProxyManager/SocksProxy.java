/*
 * Decompiled with CFR 0_122.
 */
package ProxyManager;

public final class SocksProxy {
    private String mask = "";
    private final String username;
    private String password;

    public SocksProxy(String username, String password, ProxyVersion version) {
        this(username, password, version.name().toUpperCase().replace("V", "v"));
    }

    public SocksProxy(String username, String password, String mask) {
        this.username = username;
        this.password = password;
        this.mask = mask;
    }

    public String getMask() {
        return this.mask;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

