/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ByteSet
extends ByteCollection,
Set<Byte> {
    @Override
    public ByteIterator iterator();

    public boolean remove(byte var1);

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        return ByteCollection.super.remove(object);
    }

    @Override
    @Deprecated
    default public boolean add(Byte by) {
        return ByteCollection.super.add(by);
    }

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        return ByteCollection.super.contains(object);
    }

    @Override
    @Deprecated
    default public boolean rem(byte by) {
        return this.remove(by);
    }

    @Override
    @Deprecated
    default public boolean add(Object object) {
        return this.add((Byte)object);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }
}

