/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.util;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public class Pair<X, Y> {
    private final X key;
    private Y value;

    public Pair(@Nullable X x, @Nullable Y y) {
        this.key = x;
        this.value = y;
    }

    public @Nullable X key() {
        return this.key;
    }

    public @Nullable Y value() {
        return this.value;
    }

    @Deprecated
    public @Nullable X getKey() {
        return this.key;
    }

    @Deprecated
    public @Nullable Y getValue() {
        return this.value;
    }

    @Deprecated
    public void setValue(@Nullable Y y) {
        this.value = y;
    }

    public String toString() {
        return "Pair{" + this.key + ", " + this.value + '}';
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        Pair pair = (Pair)object;
        if (!Objects.equals(this.key, pair.key)) {
            return true;
        }
        return Objects.equals(this.value, pair.value);
    }

    public int hashCode() {
        int n = this.key != null ? this.key.hashCode() : 0;
        n = 31 * n + (this.value != null ? this.value.hashCode() : 0);
        return n;
    }
}

