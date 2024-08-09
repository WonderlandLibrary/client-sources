/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import javax.annotation.Nullable;

@GwtCompatible
final class Count
implements Serializable {
    private int value;

    Count(int n) {
        this.value = n;
    }

    public int get() {
        return this.value;
    }

    public void add(int n) {
        this.value += n;
    }

    public int addAndGet(int n) {
        return this.value += n;
    }

    public void set(int n) {
        this.value = n;
    }

    public int getAndSet(int n) {
        int n2 = this.value;
        this.value = n;
        return n2;
    }

    public int hashCode() {
        return this.value;
    }

    public boolean equals(@Nullable Object object) {
        return object instanceof Count && ((Count)object).value == this.value;
    }

    public String toString() {
        return Integer.toString(this.value);
    }
}

