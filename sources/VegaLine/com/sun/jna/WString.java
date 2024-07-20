/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public final class WString
implements CharSequence,
Comparable {
    private String string;

    public WString(String s) {
        if (s == null) {
            throw new NullPointerException("String initializer must be non-null");
        }
        this.string = s;
    }

    @Override
    public String toString() {
        return this.string;
    }

    public boolean equals(Object o) {
        return o instanceof WString && this.toString().equals(o.toString());
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }

    @Override
    public int length() {
        return this.toString().length();
    }

    @Override
    public char charAt(int index) {
        return this.toString().charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return this.toString().subSequence(start, end);
    }
}

