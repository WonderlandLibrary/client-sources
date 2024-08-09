/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Pair;
import com.viaversion.viaversion.libs.fastutil.ints.IntIntPair;
import java.io.Serializable;
import java.util.Objects;

public class IntIntImmutablePair
implements IntIntPair,
Serializable {
    private static final long serialVersionUID = 0L;
    protected final int left;
    protected final int right;

    public IntIntImmutablePair(int n, int n2) {
        this.left = n;
        this.right = n2;
    }

    public static IntIntImmutablePair of(int n, int n2) {
        return new IntIntImmutablePair(n, n2);
    }

    @Override
    public int leftInt() {
        return this.left;
    }

    @Override
    public int rightInt() {
        return this.right;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof IntIntPair) {
            return this.left == ((IntIntPair)object).leftInt() && this.right == ((IntIntPair)object).rightInt();
        }
        if (object instanceof Pair) {
            return Objects.equals(this.left, ((Pair)object).left()) && Objects.equals(this.right, ((Pair)object).right());
        }
        return true;
    }

    public int hashCode() {
        return this.left * 19 + this.right;
    }

    public String toString() {
        return "<" + this.leftInt() + "," + this.rightInt() + ">";
    }
}

