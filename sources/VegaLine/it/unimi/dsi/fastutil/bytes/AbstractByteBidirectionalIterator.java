/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;

public abstract class AbstractByteBidirectionalIterator
extends AbstractByteIterator
implements ByteBidirectionalIterator {
    protected AbstractByteBidirectionalIterator() {
    }

    @Override
    public byte previousByte() {
        return this.previous();
    }

    @Override
    public Byte previous() {
        return this.previousByte();
    }

    @Override
    public int back(int n) {
        int i = n;
        while (i-- != 0 && this.hasPrevious()) {
            this.previousByte();
        }
        return n - i - 1;
    }
}

