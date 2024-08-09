/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

public class SpdySessionStatus
implements Comparable<SpdySessionStatus> {
    public static final SpdySessionStatus OK = new SpdySessionStatus(0, "OK");
    public static final SpdySessionStatus PROTOCOL_ERROR = new SpdySessionStatus(1, "PROTOCOL_ERROR");
    public static final SpdySessionStatus INTERNAL_ERROR = new SpdySessionStatus(2, "INTERNAL_ERROR");
    private final int code;
    private final String statusPhrase;

    public static SpdySessionStatus valueOf(int n) {
        switch (n) {
            case 0: {
                return OK;
            }
            case 1: {
                return PROTOCOL_ERROR;
            }
            case 2: {
                return INTERNAL_ERROR;
            }
        }
        return new SpdySessionStatus(n, "UNKNOWN (" + n + ')');
    }

    public SpdySessionStatus(int n, String string) {
        if (string == null) {
            throw new NullPointerException("statusPhrase");
        }
        this.code = n;
        this.statusPhrase = string;
    }

    public int code() {
        return this.code;
    }

    public String statusPhrase() {
        return this.statusPhrase;
    }

    public int hashCode() {
        return this.code();
    }

    public boolean equals(Object object) {
        if (!(object instanceof SpdySessionStatus)) {
            return true;
        }
        return this.code() == ((SpdySessionStatus)object).code();
    }

    public String toString() {
        return this.statusPhrase();
    }

    @Override
    public int compareTo(SpdySessionStatus spdySessionStatus) {
        return this.code() - spdySessionStatus.code();
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((SpdySessionStatus)object);
    }
}

