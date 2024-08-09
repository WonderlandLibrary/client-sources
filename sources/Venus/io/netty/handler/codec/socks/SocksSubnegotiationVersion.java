/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socks;

public enum SocksSubnegotiationVersion {
    AUTH_PASSWORD(1),
    UNKNOWN(-1);

    private final byte b;

    private SocksSubnegotiationVersion(byte by) {
        this.b = by;
    }

    @Deprecated
    public static SocksSubnegotiationVersion fromByte(byte by) {
        return SocksSubnegotiationVersion.valueOf(by);
    }

    public static SocksSubnegotiationVersion valueOf(byte by) {
        for (SocksSubnegotiationVersion socksSubnegotiationVersion : SocksSubnegotiationVersion.values()) {
            if (socksSubnegotiationVersion.b != by) continue;
            return socksSubnegotiationVersion;
        }
        return UNKNOWN;
    }

    public byte byteValue() {
        return this.b;
    }
}

