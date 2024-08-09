/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import java.util.PrimitiveIterator;
import java.util.function.Consumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface IntIterator
extends PrimitiveIterator.OfInt {
    @Override
    public int nextInt();

    @Override
    @Deprecated
    default public Integer next() {
        return this.nextInt();
    }

    @Override
    @Deprecated
    default public void forEachRemaining(Consumer<? super Integer> consumer) {
        this.forEachRemaining(consumer::accept);
    }

    default public int skip(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
        }
        int n2 = n;
        while (n2-- != 0 && this.hasNext()) {
            this.nextInt();
        }
        return n - n2 - 1;
    }

    @Override
    @Deprecated
    default public Object next() {
        return this.next();
    }
}

