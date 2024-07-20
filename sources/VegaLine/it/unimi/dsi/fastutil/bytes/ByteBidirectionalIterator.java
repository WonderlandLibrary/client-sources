/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public interface ByteBidirectionalIterator
extends ByteIterator,
ObjectBidirectionalIterator<Byte> {
    public byte previousByte();

    @Override
    public int back(int var1);
}

