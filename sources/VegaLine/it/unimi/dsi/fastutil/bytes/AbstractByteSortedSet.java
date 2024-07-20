/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByteSet;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;

public abstract class AbstractByteSortedSet
extends AbstractByteSet
implements ByteSortedSet {
    protected AbstractByteSortedSet() {
    }

    @Override
    @Deprecated
    public ByteSortedSet headSet(Byte to) {
        return this.headSet((byte)to);
    }

    @Override
    @Deprecated
    public ByteSortedSet tailSet(Byte from) {
        return this.tailSet((byte)from);
    }

    @Override
    @Deprecated
    public ByteSortedSet subSet(Byte from, Byte to) {
        return this.subSet((byte)from, (byte)to);
    }

    @Override
    @Deprecated
    public Byte first() {
        return this.firstByte();
    }

    @Override
    @Deprecated
    public Byte last() {
        return this.lastByte();
    }

    @Override
    @Deprecated
    public ByteBidirectionalIterator byteIterator() {
        return this.iterator();
    }

    @Override
    public abstract ByteBidirectionalIterator iterator();
}

