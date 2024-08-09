/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

public enum SpdyVersion {
    SPDY_3_1(3, 1);

    private final int version;
    private final int minorVersion;

    private SpdyVersion(int n2, int n3) {
        this.version = n2;
        this.minorVersion = n3;
    }

    int getVersion() {
        return this.version;
    }

    int getMinorVersion() {
        return this.minorVersion;
    }
}

