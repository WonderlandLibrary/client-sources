/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterable;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ByteSortedSet
extends ByteSet,
SortedSet<Byte>,
ByteBidirectionalIterable {
    public ByteBidirectionalIterator iterator(byte var1);

    @Override
    public ByteBidirectionalIterator iterator();

    public ByteSortedSet subSet(byte var1, byte var2);

    public ByteSortedSet headSet(byte var1);

    public ByteSortedSet tailSet(byte var1);

    public ByteComparator comparator();

    public byte firstByte();

    public byte lastByte();

    @Deprecated
    default public ByteSortedSet subSet(Byte by, Byte by2) {
        return this.subSet((byte)by, (byte)by2);
    }

    @Deprecated
    default public ByteSortedSet headSet(Byte by) {
        return this.headSet((byte)by);
    }

    @Deprecated
    default public ByteSortedSet tailSet(Byte by) {
        return this.tailSet((byte)by);
    }

    @Override
    @Deprecated
    default public Byte first() {
        return this.firstByte();
    }

    @Override
    @Deprecated
    default public Byte last() {
        return this.lastByte();
    }

    @Override
    default public ByteIterator iterator() {
        return this.iterator();
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    @Override
    @Deprecated
    default public Object last() {
        return this.last();
    }

    @Override
    @Deprecated
    default public Object first() {
        return this.first();
    }

    @Override
    @Deprecated
    default public SortedSet tailSet(Object object) {
        return this.tailSet((Byte)object);
    }

    @Override
    @Deprecated
    default public SortedSet headSet(Object object) {
        return this.headSet((Byte)object);
    }

    @Override
    @Deprecated
    default public SortedSet subSet(Object object, Object object2) {
        return this.subSet((Byte)object, (Byte)object2);
    }

    @Override
    default public Comparator comparator() {
        return this.comparator();
    }
}

