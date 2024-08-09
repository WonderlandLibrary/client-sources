/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socks;

public enum SocksAuthStatus {
    SUCCESS(0),
    FAILURE(-1);

    private final byte b;

    private SocksAuthStatus(byte by) {
        this.b = by;
    }

    @Deprecated
    public static SocksAuthStatus fromByte(byte by) {
        return SocksAuthStatus.valueOf(by);
    }

    public static SocksAuthStatus valueOf(byte by) {
        for (SocksAuthStatus socksAuthStatus : SocksAuthStatus.values()) {
            if (socksAuthStatus.b != by) continue;
            return socksAuthStatus;
        }
        return FAILURE;
    }

    public byte byteValue() {
        return this.b;
    }
}

