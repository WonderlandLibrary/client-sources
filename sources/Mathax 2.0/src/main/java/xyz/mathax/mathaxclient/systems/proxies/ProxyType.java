package xyz.mathax.mathaxclient.systems.proxies;

import org.jetbrains.annotations.Nullable;

public enum ProxyType {
    Socks4("Socks 4"),
    Socks5("Socks 5");

    private final String name;

    ProxyType(String name) {
        this.name = name;
    }

    @Nullable
    public static ProxyType parse(String group) {
        for (ProxyType type : values()) {
            if (type.name().equalsIgnoreCase(group)) {
                return type;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
