/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v5;

public class Socks5AuthMethod
implements Comparable<Socks5AuthMethod> {
    public static final Socks5AuthMethod NO_AUTH = new Socks5AuthMethod(0, "NO_AUTH");
    public static final Socks5AuthMethod GSSAPI = new Socks5AuthMethod(1, "GSSAPI");
    public static final Socks5AuthMethod PASSWORD = new Socks5AuthMethod(2, "PASSWORD");
    public static final Socks5AuthMethod UNACCEPTED = new Socks5AuthMethod(255, "UNACCEPTED");
    private final byte byteValue;
    private final String name;
    private String text;

    public static Socks5AuthMethod valueOf(byte by) {
        switch (by) {
            case 0: {
                return NO_AUTH;
            }
            case 1: {
                return GSSAPI;
            }
            case 2: {
                return PASSWORD;
            }
            case -1: {
                return UNACCEPTED;
            }
        }
        return new Socks5AuthMethod(by);
    }

    public Socks5AuthMethod(int n) {
        this(n, "UNKNOWN");
    }

    public Socks5AuthMethod(int n, String string) {
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
        if (!(object instanceof Socks5AuthMethod)) {
            return true;
        }
        return this.byteValue == ((Socks5AuthMethod)object).byteValue;
    }

    @Override
    public int compareTo(Socks5AuthMethod socks5AuthMethod) {
        return this.byteValue - socks5AuthMethod.byteValue;
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
        return this.compareTo((Socks5AuthMethod)object);
    }
}

