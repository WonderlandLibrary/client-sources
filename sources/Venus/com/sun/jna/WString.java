/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

public final class WString
implements CharSequence,
Comparable {
    private String string;

    public WString(String string) {
        if (string == null) {
            throw new NullPointerException("String initializer must be non-null");
        }
        this.string = string;
    }

    @Override
    public String toString() {
        return this.string;
    }

    public boolean equals(Object object) {
        return object instanceof WString && this.toString().equals(object.toString());
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public int compareTo(Object object) {
        return this.toString().compareTo(object.toString());
    }

    @Override
    public int length() {
        return this.toString().length();
    }

    @Override
    public char charAt(int n) {
        return this.toString().charAt(n);
    }

    @Override
    public CharSequence subSequence(int n, int n2) {
        return this.toString().subSequence(n, n2);
    }
}

