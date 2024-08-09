/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteListIterator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ByteList
extends List<Byte>,
Comparable<List<? extends Byte>>,
ByteCollection {
    @Override
    public ByteListIterator iterator();

    public ByteListIterator listIterator();

    public ByteListIterator listIterator(int var1);

    public ByteList subList(int var1, int var2);

    public void size(int var1);

    public void getElements(int var1, byte[] var2, int var3, int var4);

    public void removeElements(int var1, int var2);

    public void addElements(int var1, byte[] var2);

    public void addElements(int var1, byte[] var2, int var3, int var4);

    @Override
    public boolean add(byte var1);

    @Override
    public void add(int var1, byte var2);

    @Override
    @Deprecated
    default public void add(int n, Byte by) {
        this.add(n, (byte)by);
    }

    public boolean addAll(int var1, ByteCollection var2);

    public boolean addAll(int var1, ByteList var2);

    public boolean addAll(ByteList var1);

    @Override
    public byte set(int var1, byte var2);

    public byte getByte(int var1);

    public int indexOf(byte var1);

    public int lastIndexOf(byte var1);

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        return ByteCollection.super.contains(object);
    }

    @Override
    @Deprecated
    default public Byte get(int n) {
        return this.getByte(n);
    }

    @Override
    @Deprecated
    default public int indexOf(Object object) {
        return this.indexOf((Byte)object);
    }

    @Override
    @Deprecated
    default public int lastIndexOf(Object object) {
        return this.lastIndexOf((Byte)object);
    }

    @Override
    @Deprecated
    default public boolean add(Byte by) {
        return this.add((byte)by);
    }

    public byte removeByte(int var1);

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        return ByteCollection.super.remove(object);
    }

    @Override
    @Deprecated
    default public Byte remove(int n) {
        return this.removeByte(n);
    }

    @Override
    @Deprecated
    default public Byte set(int n, Byte by) {
        return this.set(n, (byte)by);
    }

    @Override
    default public List subList(int n, int n2) {
        return this.subList(n, n2);
    }

    @Override
    default public ListIterator listIterator(int n) {
        return this.listIterator(n);
    }

    @Override
    default public ListIterator listIterator() {
        return this.listIterator();
    }

    @Override
    @Deprecated
    default public Object remove(int n) {
        return this.remove(n);
    }

    @Override
    @Deprecated
    default public void add(int n, Object object) {
        this.add(n, (Byte)object);
    }

    @Override
    @Deprecated
    default public Object set(int n, Object object) {
        return this.set(n, (Byte)object);
    }

    @Override
    @Deprecated
    default public Object get(int n) {
        return this.get(n);
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

    @Override
    default public ByteIterator iterator() {
        return this.iterator();
    }
}

