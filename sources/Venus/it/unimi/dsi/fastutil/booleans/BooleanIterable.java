/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface BooleanIterable
extends Iterable<Boolean> {
    public BooleanIterator iterator();

    default public void forEach(BooleanConsumer booleanConsumer) {
        Objects.requireNonNull(booleanConsumer);
        BooleanIterator booleanIterator = this.iterator();
        while (booleanIterator.hasNext()) {
            booleanConsumer.accept(booleanIterator.nextBoolean());
        }
    }

    @Override
    @Deprecated
    default public void forEach(Consumer<? super Boolean> consumer) {
        this.forEach(consumer::accept);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }
}

