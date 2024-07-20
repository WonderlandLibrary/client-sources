/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.socket;

import io.netty.util.NetUtil;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;

public enum InternetProtocolFamily {
    IPv4(Inet4Address.class),
    IPv6(Inet6Address.class);

    private final Class<? extends InetAddress> addressType;
    private final int addressNumber;
    private final InetAddress localHost;

    private InternetProtocolFamily(Class<? extends InetAddress> addressType) {
        this.addressType = addressType;
        this.addressNumber = InternetProtocolFamily.addressNumber(addressType);
        this.localHost = InternetProtocolFamily.localhost(addressType);
    }

    public Class<? extends InetAddress> addressType() {
        return this.addressType;
    }

    public int addressNumber() {
        return this.addressNumber;
    }

    public InetAddress localhost() {
        return this.localHost;
    }

    private static InetAddress localhost(Class<? extends InetAddress> addressType) {
        if (addressType.isAssignableFrom(Inet4Address.class)) {
            return NetUtil.LOCALHOST4;
        }
        if (addressType.isAssignableFrom(Inet6Address.class)) {
            return NetUtil.LOCALHOST6;
        }
        throw new Error();
    }

    private static int addressNumber(Class<? extends InetAddress> addressType) {
        if (addressType.isAssignableFrom(Inet4Address.class)) {
            return 1;
        }
        if (addressType.isAssignableFrom(Inet6Address.class)) {
            return 2;
        }
        throw new IllegalArgumentException("addressType " + addressType + " not supported");
    }

    public static InternetProtocolFamily of(InetAddress address) {
        if (address instanceof Inet4Address) {
            return IPv4;
        }
        if (address instanceof Inet6Address) {
            return IPv6;
        }
        throw new IllegalArgumentException("address " + address + " not supported");
    }
}

