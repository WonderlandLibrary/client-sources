/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIntPair;
import java.io.Serializable;
import java.util.Objects;

public class ObjectIntMutablePair<K>
implements ObjectIntPair<K>,
Serializable {
    private static final long serialVersionUID = 0L;
    protected K left;
    protected int right;

    public ObjectIntMutablePair(K k, int n) {
        this.left = k;
        this.right = n;
    }

    public static <K> ObjectIntMutablePair<K> of(K k, int n) {
        return new ObjectIntMutablePair<K>(k, n);
    }

    @Override
    public K left() {
        return this.left;
    }

    public ObjectIntMutablePair<K> left(K k) {
        this.left = k;
        return this;
    }

    @Override
    public int rightInt() {
        return this.right;
    }

    @Override
    public ObjectIntMutablePair<K> right(int n) {
        this.right = n;
        return this;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof ObjectIntPair) {
            return Objects.equals(this.left, ((ObjectIntPair)object).left()) && this.right == ((ObjectIntPair)object).rightInt();
        }
        if (object instanceof Pair) {
            return Objects.equals(this.left, ((Pair)object).left()) && Objects.equals(this.right, ((Pair)object).right());
        }
        return true;
    }

    public int hashCode() {
        return (this.left == null ? 0 : this.left.hashCode()) * 19 + this.right;
    }

    public String toString() {
        return "<" + this.left() + "," + this.rightInt() + ">";
    }

    @Override
    public ObjectIntPair right(int n) {
        return this.right(n);
    }

    @Override
    public Pair left(Object object) {
        return this.left(object);
    }
}

