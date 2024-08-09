/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.util;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public class Triple<A, B, C> {
    private final A first;
    private final B second;
    private final C third;

    public Triple(@Nullable A a, @Nullable B b, @Nullable C c) {
        this.first = a;
        this.second = b;
        this.third = c;
    }

    public @Nullable A first() {
        return this.first;
    }

    public @Nullable B second() {
        return this.second;
    }

    public @Nullable C third() {
        return this.third;
    }

    @Deprecated
    public @Nullable A getFirst() {
        return this.first;
    }

    @Deprecated
    public @Nullable B getSecond() {
        return this.second;
    }

    @Deprecated
    public @Nullable C getThird() {
        return this.third;
    }

    public String toString() {
        return "Triple{" + this.first + ", " + this.second + ", " + this.third + '}';
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        Triple triple = (Triple)object;
        if (!Objects.equals(this.first, triple.first)) {
            return true;
        }
        if (!Objects.equals(this.second, triple.second)) {
            return true;
        }
        return Objects.equals(this.third, triple.third);
    }

    public int hashCode() {
        int n = this.first != null ? this.first.hashCode() : 0;
        n = 31 * n + (this.second != null ? this.second.hashCode() : 0);
        n = 31 * n + (this.third != null ? this.third.hashCode() : 0);
        return n;
    }
}

