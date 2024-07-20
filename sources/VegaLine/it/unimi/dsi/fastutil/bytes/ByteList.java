/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteListIterator;
import java.util.List;

public interface ByteList
extends List<Byte>,
Comparable<List<? extends Byte>>,
ByteCollection {
    @Override
    public ByteListIterator iterator();

    @Deprecated
    public ByteListIterator byteListIterator();

    @Deprecated
    public ByteListIterator byteListIterator(int var1);

    public ByteListIterator listIterator();

    public ByteListIterator listIterator(int var1);

    @Deprecated
    public ByteList byteSubList(int var1, int var2);

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

    public boolean addAll(int var1, ByteCollection var2);

    public boolean addAll(int var1, ByteList var2);

    public boolean addAll(ByteList var1);

    public byte getByte(int var1);

    public int indexOf(byte var1);

    public int lastIndexOf(byte var1);

    public byte removeByte(int var1);

    @Override
    public byte set(int var1, byte var2);
}

