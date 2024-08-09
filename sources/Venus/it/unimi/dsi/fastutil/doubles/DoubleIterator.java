/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import java.util.PrimitiveIterator;
import java.util.function.Consumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface DoubleIterator
extends PrimitiveIterator.OfDouble {
    @Override
    public double nextDouble();

    @Override
    @Deprecated
    default public Double next() {
        return this.nextDouble();
    }

    @Override
    @Deprecated
    default public void forEachRemaining(Consumer<? super Double> consumer) {
        this.forEachRemaining(consumer::accept);
    }

    default public int skip(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
        }
        int n2 = n;
        while (n2-- != 0 && this.hasNext()) {
            this.nextDouble();
        }
        return n - n2 - 1;
    }

    @Override
    @Deprecated
    default public Object next() {
        return this.next();
    }
}

