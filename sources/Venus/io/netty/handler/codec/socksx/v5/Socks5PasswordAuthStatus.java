/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v5;

public class Socks5PasswordAuthStatus
implements Comparable<Socks5PasswordAuthStatus> {
    public static final Socks5PasswordAuthStatus SUCCESS = new Socks5PasswordAuthStatus(0, "SUCCESS");
    public static final Socks5PasswordAuthStatus FAILURE = new Socks5PasswordAuthStatus(255, "FAILURE");
    private final byte byteValue;
    private final String name;
    private String text;

    public static Socks5PasswordAuthStatus valueOf(byte by) {
        switch (by) {
            case 0: {
                return SUCCESS;
            }
            case -1: {
                return FAILURE;
            }
        }
        return new Socks5PasswordAuthStatus(by);
    }

    public Socks5PasswordAuthStatus(int n) {
        this(n, "UNKNOWN");
    }

    public Socks5PasswordAuthStatus(int n, String string) {
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
        return this.byteValue == 0;
    }

    public int hashCode() {
        return this.byteValue;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Socks5PasswordAuthStatus)) {
            return true;
        }
        return this.byteValue == ((Socks5PasswordAuthStatus)object).byteValue;
    }

    @Override
    public int compareTo(Socks5PasswordAuthStatus socks5PasswordAuthStatus) {
        return this.byteValue - socks5PasswordAuthStatus.byteValue;
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
        return this.compareTo((Socks5PasswordAuthStatus)object);
    }
}

