/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v4;

public class Socks4CommandStatus
implements Comparable<Socks4CommandStatus> {
    public static final Socks4CommandStatus SUCCESS = new Socks4CommandStatus(90, "SUCCESS");
    public static final Socks4CommandStatus REJECTED_OR_FAILED = new Socks4CommandStatus(91, "REJECTED_OR_FAILED");
    public static final Socks4CommandStatus IDENTD_UNREACHABLE = new Socks4CommandStatus(92, "IDENTD_UNREACHABLE");
    public static final Socks4CommandStatus IDENTD_AUTH_FAILURE = new Socks4CommandStatus(93, "IDENTD_AUTH_FAILURE");
    private final byte byteValue;
    private final String name;
    private String text;

    public static Socks4CommandStatus valueOf(byte by) {
        switch (by) {
            case 90: {
                return SUCCESS;
            }
            case 91: {
                return REJECTED_OR_FAILED;
            }
            case 92: {
                return IDENTD_UNREACHABLE;
            }
            case 93: {
                return IDENTD_AUTH_FAILURE;
            }
        }
        return new Socks4CommandStatus(by);
    }

    public Socks4CommandStatus(int n) {
        this(n, "UNKNOWN");
    }

    public Socks4CommandStatus(int n, String string) {
        if (string == null) {
            throw new NullPointerException("name");
        }
        this.byteValue = (byte)n;
        this.name = string;
    }

    public byte byteValue() {
        return this.byteValue;
    }

    public boolean isSuccess() {
        return this.byteValue == 90;
    }

    public int hashCode() {
        return this.byteValue;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Socks4CommandStatus)) {
            return true;
        }
        return this.byteValue == ((Socks4CommandStatus)object).byteValue;
    }

    @Override
    public int compareTo(Socks4CommandStatus socks4CommandStatus) {
        return this.byteValue - socks4CommandStatus.byteValue;
    }

    public String toString() {
        String string = this.text;
        if (string == null) {
            this.text = string = this.name + '(' + (this.byteValue & 0xFF) + ')';
        }
        return string;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Socks4CommandStatus)object);
    }
}

