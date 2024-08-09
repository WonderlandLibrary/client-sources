/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v5;

public class Socks5CommandType
implements Comparable<Socks5CommandType> {
    public static final Socks5CommandType CONNECT = new Socks5CommandType(1, "CONNECT");
    public static final Socks5CommandType BIND = new Socks5CommandType(2, "BIND");
    public static final Socks5CommandType UDP_ASSOCIATE = new Socks5CommandType(3, "UDP_ASSOCIATE");
    private final byte byteValue;
    private final String name;
    private String text;

    public static Socks5CommandType valueOf(byte by) {
        switch (by) {
            case 1: {
                return CONNECT;
            }
            case 2: {
                return BIND;
            }
            case 3: {
                return UDP_ASSOCIATE;
            }
        }
        return new Socks5CommandType(by);
    }

    public Socks5CommandType(int n) {
        this(n, "UNKNOWN");
    }

    public Socks5CommandType(int n, String string) {
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
        if (!(object instanceof Socks5CommandType)) {
            return true;
        }
        return this.byteValue == ((Socks5CommandType)object).byteValue;
    }

    @Override
    public int compareTo(Socks5CommandType socks5CommandType) {
        return this.byteValue - socks5CommandType.byteValue;
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
        return this.compareTo((Socks5CommandType)object);
    }
}

