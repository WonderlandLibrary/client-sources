/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx;

public enum SocksVersion {
    SOCKS4a(4),
    SOCKS5(5),
    UNKNOWN(-1);

    private final byte b;

    public static SocksVersion valueOf(byte by) {
        if (by == SOCKS4a.byteValue()) {
            return SOCKS4a;
        }
        if (by == SOCKS5.byteValue()) {
            return SOCKS5;
        }
        return UNKNOWN;
    }

    private SocksVersion(byte by) {
        this.b = by;
    }

    public byte byteValue() {
        return this.b;
    }
}

