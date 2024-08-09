/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntIterator;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface IntIterable
extends Iterable<Integer> {
    public IntIterator iterator();

    default public void forEach(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        IntIterator intIterator = this.iterator();
        while (intIterator.hasNext()) {
            intConsumer.accept(intIterator.nextInt());
        }
    }

    @Override
    @Deprecated
    default public void forEach(Consumer<? super Integer> consumer) {
        this.forEach(consumer::accept);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }
}

