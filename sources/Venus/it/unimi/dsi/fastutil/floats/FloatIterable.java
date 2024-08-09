/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatIterator;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface FloatIterable
extends Iterable<Float> {
    public FloatIterator iterator();

    default public void forEach(DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        FloatIterator floatIterator = this.iterator();
        while (floatIterator.hasNext()) {
            doubleConsumer.accept(floatIterator.nextFloat());
        }
    }

    @Override
    @Deprecated
    default public void forEach(Consumer<? super Float> consumer) {
        this.forEach(new DoubleConsumer(this, consumer){
            final Consumer val$action;
            final FloatIterable this$0;
            {
                this.this$0 = floatIterable;
                this.val$action = consumer;
            }

            @Override
            public void accept(double d) {
                this.val$action.accept(Float.valueOf((float)d));
            }
        });
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }
}

