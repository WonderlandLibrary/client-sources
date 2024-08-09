/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.IntComparator;
import com.viaversion.viaversion.libs.fastutil.ints.IntConsumer;
import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface IntSpliterator
extends Spliterator.OfInt {
    @Override
    @Deprecated
    default public boolean tryAdvance(Consumer<? super Integer> consumer) {
        return this.tryAdvance(consumer instanceof java.util.function.IntConsumer ? (java.util.function.IntConsumer)((Object)consumer) : consumer::accept);
    }

    @Override
    default public boolean tryAdvance(IntConsumer intConsumer) {
        return this.tryAdvance((java.util.function.IntConsumer)intConsumer);
    }

    @Override
    @Deprecated
    default public void forEachRemaining(Consumer<? super Integer> consumer) {
        this.forEachRemaining(consumer instanceof java.util.function.IntConsumer ? (java.util.function.IntConsumer)((Object)consumer) : consumer::accept);
    }

    @Override
    default public void forEachRemaining(IntConsumer intConsumer) {
        this.forEachRemaining((java.util.function.IntConsumer)intConsumer);
    }

    default public long skip(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + l);
        }
        long l2 = l;
        while (l2-- != 0L && this.tryAdvance(IntSpliterator::lambda$skip$0)) {
        }
        return l - l2 - 1L;
    }

    @Override
    public IntSpliterator trySplit();

    default public IntComparator getComparator() {
        throw new IllegalStateException();
    }

    @Override
    default public Spliterator.OfInt trySplit() {
        return this.trySplit();
    }

    @Override
    default public Spliterator.OfPrimitive trySplit() {
        return this.trySplit();
    }

    @Override
    default public Comparator getComparator() {
        return this.getComparator();
    }

    @Override
    default public Spliterator trySplit() {
        return this.trySplit();
    }

    private static void lambda$skip$0(int n) {
    }
}

