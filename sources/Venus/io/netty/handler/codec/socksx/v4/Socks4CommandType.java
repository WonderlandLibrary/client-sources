/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v4;

public class Socks4CommandType
implements Comparable<Socks4CommandType> {
    public static final Socks4CommandType CONNECT = new Socks4CommandType(1, "CONNECT");
    public static final Socks4CommandType BIND = new Socks4CommandType(2, "BIND");
    private final byte byteValue;
    private final String name;
    private String text;

    public static Socks4CommandType valueOf(byte by) {
        switch (by) {
            case 1: {
                return CONNECT;
            }
            case 2: {
                return BIND;
            }
        }
        return new Socks4CommandType(by);
    }

    public Socks4CommandType(int n) {
        this(n, "UNKNOWN");
    }

    public Socks4CommandType(int n, String string) {
        if (string == null) {
            throw new NullPointerException("name");
        }
        this.byteValue = (byte)n;
        this.name = string;
    }

    public byte byteValue() {
        return this.byteValue;
    }

    public int hashCode() {
        return this.byteValue;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Socks4CommandType)) {
            return true;
        }
        return this.byteValue == ((Socks4CommandType)object).byteValue;
    }

    @Override
    public int compareTo(Socks4CommandType socks4CommandType) {
        return this.byteValue - socks4CommandType.byteValue;
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
        return this.compareTo((Socks4CommandType)object);
    }
}

