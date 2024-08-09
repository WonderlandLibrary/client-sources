/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteIterator;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ByteIterable
extends Iterable<Byte> {
    public ByteIterator iterator();

    default public void forEach(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        ByteIterator byteIterator = this.iterator();
        while (byteIterator.hasNext()) {
            intConsumer.accept(byteIterator.nextByte());
        }
    }

    @Override
    @Deprecated
    default public void forEach(Consumer<? super Byte> consumer) {
        this.forEach(new IntConsumer(this, consumer){
            final Consumer val$action;
            final ByteIterable this$0;
            {
                this.this$0 = byteIterable;
                this.val$action = consumer;
            }

            @Override
            public void accept(int n) {
                this.val$action.accept((byte)n);
            }
        });
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }
}

