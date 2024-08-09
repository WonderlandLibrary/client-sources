/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.Size64;
import it.unimi.dsi.fastutil.bytes.ByteBigListIterator;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import java.util.Iterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ByteBigList
extends BigList<Byte>,
ByteCollection,
Size64,
Comparable<BigList<? extends Byte>> {
    @Override
    public ByteBigListIterator iterator();

    public ByteBigListIterator listIterator();

    public ByteBigListIterator listIterator(long var1);

    public ByteBigList subList(long var1, long var3);

    public void getElements(long var1, byte[][] var3, long var4, long var6);

    public void removeElements(long var1, long var3);

    public void addElements(long var1, byte[][] var3);

    public void addElements(long var1, byte[][] var3, long var4, long var6);

    @Override
    public void add(long var1, byte var3);

    public boolean addAll(long var1, ByteCollection var3);

    public boolean addAll(long var1, ByteBigList var3);

    public boolean addAll(ByteBigList var1);

    public byte getByte(long var1);

    public byte removeByte(long var1);

    @Override
    public byte set(long var1, byte var3);

    public long indexOf(byte var1);

    public long lastIndexOf(byte var1);

    @Override
    @Deprecated
    public void add(long var1, Byte var3);

    @Override
    @Deprecated
    public Byte get(long var1);

    @Override
    @Deprecated
    public long indexOf(Object var1);

    @Override
    @Deprecated
    public long lastIndexOf(Object var1);

    @Override
    @Deprecated
    public Byte remove(long var1);

    @Override
    @Deprecated
    public Byte set(long var1, Byte var3);

    @Override
    default public BigList subList(long l, long l2) {
        return this.subList(l, l2);
    }

    @Override
    default public BigListIterator listIterator(long l) {
        return this.listIterator(l);
    }

    @Override
    default public BigListIterator listIterator() {
        return this.listIterator();
    }

    @Override
    @Deprecated
    default public void add(long l, Object object) {
        this.add(l, (Byte)object);
    }

    @Override
    @Deprecated
    default public Object set(long l, Object object) {
        return this.set(l, (Byte)object);
    }

    @Override
    @Deprecated
    default public Object remove(long l) {
        return this.remove(l);
    }

    @Override
    @Deprecated
    default public Object get(long l) {
        return this.get(l);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    @Override
    default public ByteIterator iterator() {
        return this.iterator();
    }
}

