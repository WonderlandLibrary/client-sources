/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ByteBidirectionalIterator
extends ByteIterator,
ObjectBidirectionalIterator<Byte> {
    public byte previousByte();

    @Override
    @Deprecated
    default public Byte previous() {
        return this.previousByte();
    }

    @Override
    default public int back(int n) {
        int n2 = n;
        while (n2-- != 0 && this.hasPrevious()) {
            this.previousByte();
        }
        return n - n2 - 1;
    }

    @Override
    default public int skip(int n) {
        return ByteIterator.super.skip(n);
    }

    @Override
    @Deprecated
    default public Object previous() {
        return this.previous();
    }
}

