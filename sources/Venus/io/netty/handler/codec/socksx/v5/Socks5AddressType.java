/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v5;

public class Socks5AddressType
implements Comparable<Socks5AddressType> {
    public static final Socks5AddressType IPv4 = new Socks5AddressType(1, "IPv4");
    public static final Socks5AddressType DOMAIN = new Socks5AddressType(3, "DOMAIN");
    public static final Socks5AddressType IPv6 = new Socks5AddressType(4, "IPv6");
    private final byte byteValue;
    private final String name;
    private String text;

    public static Socks5AddressType valueOf(byte by) {
        switch (by) {
            case 1: {
                return IPv4;
            }
            case 3: {
                return DOMAIN;
            }
            case 4: {
                return IPv6;
            }
        }
        return new Socks5AddressType(by);
    }

    public Socks5AddressType(int n) {
        this(n, "UNKNOWN");
    }

    public Socks5AddressType(int n, String string) {
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
        if (!(object instanceof Socks5AddressType)) {
            return true;
        }
        return this.byteValue == ((Socks5AddressType)object).byteValue;
    }

    @Override
    public int compareTo(Socks5AddressType socks5AddressType) {
        return this.byteValue - socks5AddressType.byteValue;
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
        return this.compareTo((Socks5AddressType)object);
    }
}

