/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterable;
import it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ShortSortedSet
extends ShortSet,
SortedSet<Short>,
ShortBidirectionalIterable {
    public ShortBidirectionalIterator iterator(short var1);

    @Override
    public ShortBidirectionalIterator iterator();

    public ShortSortedSet subSet(short var1, short var2);

    public ShortSortedSet headSet(short var1);

    public ShortSortedSet tailSet(short var1);

    public ShortComparator comparator();

    public short firstShort();

    public short lastShort();

    @Deprecated
    default public ShortSortedSet subSet(Short s, Short s2) {
        return this.subSet((short)s, (short)s2);
    }

    @Deprecated
    default public ShortSortedSet headSet(Short s) {
        return this.headSet((short)s);
    }

    @Deprecated
    default public ShortSortedSet tailSet(Short s) {
        return this.tailSet((short)s);
    }

    @Override
    @Deprecated
    default public Short first() {
        return this.firstShort();
    }

    @Override
    @Deprecated
    default public Short last() {
        return this.lastShort();
    }

    @Override
    default public ShortIterator iterator() {
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
        return this.tailSet((Short)object);
    }

    @Override
    @Deprecated
    default public SortedSet headSet(Object object) {
        return this.headSet((Short)object);
    }

    @Override
    @Deprecated
    default public SortedSet subSet(Object object, Object object2) {
        return this.subSet((Short)object, (Short)object2);
    }

    @Override
    default public Comparator comparator() {
        return this.comparator();
    }
}

