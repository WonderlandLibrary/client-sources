/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

public class Pair<F, S> {
    public final F first;
    public final S second;

    protected Pair(F f, S s) {
        this.first = f;
        this.second = s;
    }

    public static <F, S> Pair<F, S> of(F f, S s) {
        if (f == null || s == null) {
            throw new IllegalArgumentException("Pair.of requires non null values.");
        }
        return new Pair<F, S>(f, s);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof Pair)) {
            return true;
        }
        Pair pair = (Pair)object;
        return this.first.equals(pair.first) && this.second.equals(pair.second);
    }

    public int hashCode() {
        return this.first.hashCode() * 37 + this.second.hashCode();
    }
}

