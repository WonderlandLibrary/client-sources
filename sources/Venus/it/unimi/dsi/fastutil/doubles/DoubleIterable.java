/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface DoubleIterable
extends Iterable<Double> {
    public DoubleIterator iterator();

    default public void forEach(DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        DoubleIterator doubleIterator = this.iterator();
        while (doubleIterator.hasNext()) {
            doubleConsumer.accept(doubleIterator.nextDouble());
        }
    }

    @Override
    @Deprecated
    default public void forEach(Consumer<? super Double> consumer) {
        this.forEach(consumer::accept);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }
}

