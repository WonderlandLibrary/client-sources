/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ShortIterable
extends Iterable<Short> {
    public ShortIterator iterator();

    default public void forEach(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        ShortIterator shortIterator = this.iterator();
        while (shortIterator.hasNext()) {
            intConsumer.accept(shortIterator.nextShort());
        }
    }

    @Override
    @Deprecated
    default public void forEach(Consumer<? super Short> consumer) {
        this.forEach(new IntConsumer(this, consumer){
            final Consumer val$action;
            final ShortIterable this$0;
            {
                this.this$0 = shortIterable;
                this.val$action = consumer;
            }

            @Override
            public void accept(int n) {
                this.val$action.accept((short)n);
            }
        });
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }
}

