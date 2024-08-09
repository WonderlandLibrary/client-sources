/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socks;

public enum SocksProtocolVersion {
    SOCKS4a(4),
    SOCKS5(5),
    UNKNOWN(-1);

    private final byte b;

    private SocksProtocolVersion(byte by) {
        this.b = by;
    }

    @Deprecated
    public static SocksProtocolVersion fromByte(byte by) {
        return SocksProtocolVersion.valueOf(by);
    }

    public static SocksProtocolVersion valueOf(byte by) {
        for (SocksProtocolVersion socksProtocolVersion : SocksProtocolVersion.values()) {
            if (socksProtocolVersion.b != by) continue;
            return socksProtocolVersion;
        }
        return UNKNOWN;
    }

    public byte byteValue() {
        return this.b;
    }
}

