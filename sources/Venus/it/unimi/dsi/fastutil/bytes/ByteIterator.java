/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteConsumer;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ByteIterator
extends Iterator<Byte> {
    public byte nextByte();

    @Override
    @Deprecated
    default public Byte next() {
        return this.nextByte();
    }

    default public void forEachRemaining(ByteConsumer byteConsumer) {
        Objects.requireNonNull(byteConsumer);
        while (this.hasNext()) {
            byteConsumer.accept(this.nextByte());
        }
    }

    @Override
    @Deprecated
    default public void forEachRemaining(Consumer<? super Byte> consumer) {
        this.forEachRemaining(consumer::accept);
    }

    default public int skip(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
        }
        int n2 = n;
        while (n2-- != 0 && this.hasNext()) {
            this.nextByte();
        }
        return n - n2 - 1;
    }

    @Override
    @Deprecated
    default public Object next() {
        return this.next();
    }
}

