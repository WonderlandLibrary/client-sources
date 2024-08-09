/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.io.Serializable;
import java.util.Objects;

public class ObjectObjectMutablePair<K, V>
implements Pair<K, V>,
Serializable {
    private static final long serialVersionUID = 0L;
    protected K left;
    protected V right;

    public ObjectObjectMutablePair(K k, V v) {
        this.left = k;
        this.right = v;
    }

    public static <K, V> ObjectObjectMutablePair<K, V> of(K k, V v) {
        return new ObjectObjectMutablePair<K, V>(k, v);
    }

    @Override
    public K left() {
        return this.left;
    }

    @Override
    public ObjectObjectMutablePair<K, V> left(K k) {
        this.left = k;
        return this;
    }

    @Override
    public V right() {
        return this.right;
    }

    @Override
    public ObjectObjectMutablePair<K, V> right(V v) {
        this.right = v;
        return this;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof Pair) {
            return Objects.equals(this.left, ((Pair)object).left()) && Objects.equals(this.right, ((Pair)object).right());
        }
        return true;
    }

    public int hashCode() {
        return (this.left == null ? 0 : this.left.hashCode()) * 19 + (this.right == null ? 0 : this.right.hashCode());
    }

    public String toString() {
        return "<" + this.left() + "," + this.right() + ">";
    }

    @Override
    public Pair right(Object object) {
        return this.right(object);
    }

    @Override
    public Pair left(Object object) {
        return this.left(object);
    }
}

