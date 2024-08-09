/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

import io.netty.util.Constant;
import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstractConstant<T extends AbstractConstant<T>>
implements Constant<T> {
    private static final AtomicLong uniqueIdGenerator = new AtomicLong();
    private final int id;
    private final String name;
    private final long uniquifier;

    protected AbstractConstant(int n, String string) {
        this.id = n;
        this.name = string;
        this.uniquifier = uniqueIdGenerator.getAndIncrement();
    }

    @Override
    public final String name() {
        return this.name;
    }

    @Override
    public final int id() {
        return this.id;
    }

    public final String toString() {
        return this.name();
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public final boolean equals(Object object) {
        return super.equals(object);
    }

    @Override
    public final int compareTo(T t) {
        if (this == t) {
            return 1;
        }
        T t2 = t;
        int n = this.hashCode() - ((AbstractConstant)t2).hashCode();
        if (n != 0) {
            return n;
        }
        if (this.uniquifier < ((AbstractConstant)t2).uniquifier) {
            return 1;
        }
        if (this.uniquifier > ((AbstractConstant)t2).uniquifier) {
            return 0;
        }
        throw new Error("failed to compare two different constants");
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((T)((AbstractConstant)object));
    }
}

