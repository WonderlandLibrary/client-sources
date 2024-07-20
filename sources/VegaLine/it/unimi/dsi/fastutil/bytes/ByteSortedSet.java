/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import java.util.SortedSet;

public interface ByteSortedSet
extends ByteSet,
SortedSet<Byte> {
    public ByteBidirectionalIterator iterator(byte var1);

    @Override
    @Deprecated
    public ByteBidirectionalIterator byteIterator();

    @Override
    public ByteBidirectionalIterator iterator();

    public ByteSortedSet subSet(Byte var1, Byte var2);

    public ByteSortedSet headSet(Byte var1);

    public ByteSortedSet tailSet(Byte var1);

    public ByteComparator comparator();

    public ByteSortedSet subSet(byte var1, byte var2);

    public ByteSortedSet headSet(byte var1);

    public ByteSortedSet tailSet(byte var1);

    public byte firstByte();

    public byte lastByte();
}

