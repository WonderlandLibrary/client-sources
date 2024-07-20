/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteIterator;

public abstract class AbstractByteIterator
implements ByteIterator {
    protected AbstractByteIterator() {
    }

    @Override
    public byte nextByte() {
        return this.next();
    }

    @Override
    @Deprecated
    public Byte next() {
        return this.nextByte();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int skip(int n) {
        int i = n;
        while (i-- != 0 && this.hasNext()) {
            this.nextByte();
        }
        return n - i - 1;
    }
}

