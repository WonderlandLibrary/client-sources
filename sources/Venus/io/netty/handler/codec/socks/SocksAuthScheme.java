/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socks;

public enum SocksAuthScheme {
    NO_AUTH(0),
    AUTH_GSSAPI(1),
    AUTH_PASSWORD(2),
    UNKNOWN(-1);

    private final byte b;

    private SocksAuthScheme(byte by) {
        this.b = by;
    }

    @Deprecated
    public static SocksAuthScheme fromByte(byte by) {
        return SocksAuthScheme.valueOf(by);
    }

    public static SocksAuthScheme valueOf(byte by) {
        for (SocksAuthScheme socksAuthScheme : SocksAuthScheme.values()) {
            if (socksAuthScheme.b != by) continue;
            return socksAuthScheme;
        }
        return UNKNOWN;
    }

    public byte byteValue() {
        return this.b;
    }
}

