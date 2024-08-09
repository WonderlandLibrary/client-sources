/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongIterator;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface LongIterable
extends Iterable<Long> {
    public LongIterator iterator();

    default public void forEach(LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
        LongIterator longIterator = this.iterator();
        while (longIterator.hasNext()) {
            longConsumer.accept(longIterator.nextLong());
        }
    }

    @Override
    @Deprecated
    default public void forEach(Consumer<? super Long> consumer) {
        this.forEach(consumer::accept);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }
}

