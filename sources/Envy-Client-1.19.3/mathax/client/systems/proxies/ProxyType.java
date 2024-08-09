package mathax.client.systems.proxies;

import org.jetbrains.annotations.Nullable;

public enum ProxyType {
    Socks4("Socks4"),
    Socks5("Socks5");

    private final String title;

    ProxyType(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    @Nullable
    public static ProxyType parse(String group) {
        for (ProxyType type : values()) {
            if (type.name().equalsIgnoreCase(group)) return type;
        }

        return null;
    }
}
