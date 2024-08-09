/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.tuple;

import java.io.Serializable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.tuple.ImmutableTriple;

public abstract class Triple<L, M, R>
implements Comparable<Triple<L, M, R>>,
Serializable {
    private static final long serialVersionUID = 1L;

    public static <L, M, R> Triple<L, M, R> of(L l, M m, R r) {
        return new ImmutableTriple<L, M, R>(l, m, r);
    }

    public abstract L getLeft();

    public abstract M getMiddle();

    public abstract R getRight();

    @Override
    public int compareTo(Triple<L, M, R> triple) {
        return new CompareToBuilder().append(this.getLeft(), triple.getLeft()).append(this.getMiddle(), triple.getMiddle()).append(this.getRight(), triple.getRight()).toComparison();
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof Triple) {
            Triple triple = (Triple)object;
            return ObjectUtils.equals(this.getLeft(), triple.getLeft()) && ObjectUtils.equals(this.getMiddle(), triple.getMiddle()) && ObjectUtils.equals(this.getRight(), triple.getRight());
        }
        return true;
    }

    public int hashCode() {
        return (this.getLeft() == null ? 0 : this.getLeft().hashCode()) ^ (this.getMiddle() == null ? 0 : this.getMiddle().hashCode()) ^ (this.getRight() == null ? 0 : this.getRight().hashCode());
    }

    public String toString() {
        return "(" + this.getLeft() + "," + this.getMiddle() + "," + this.getRight() + ")";
    }

    public String toString(String string) {
        return String.format(string, this.getLeft(), this.getMiddle(), this.getRight());
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Triple)object);
    }
}

