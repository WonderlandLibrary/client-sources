/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteBigListIterator;

public abstract class AbstractByteBigListIterator
extends AbstractByteBidirectionalIterator
implements ByteBigListIterator {
    protected AbstractByteBigListIterator() {
    }

    @Override
    public void set(Byte ok) {
        this.set((byte)ok);
    }

    @Override
    public void add(Byte ok) {
        this.add((byte)ok);
    }

    @Override
    public void set(byte k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(byte k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long skip(long n) {
        long i = n;
        while (i-- != 0L && this.hasNext()) {
            this.nextByte();
        }
        return n - i - 1L;
    }

    public long back(long n) {
        long i = n;
        while (i-- != 0L && this.hasPrevious()) {
            this.previousByte();
        }
        return n - i - 1L;
    }
}

