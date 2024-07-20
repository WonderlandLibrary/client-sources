/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteListIterator;

public abstract class AbstractByteListIterator
extends AbstractByteBidirectionalIterator
implements ByteListIterator {
    protected AbstractByteListIterator() {
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
}

