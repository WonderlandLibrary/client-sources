/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteIterable;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import java.util.Collection;

public interface ByteCollection
extends Collection<Byte>,
ByteIterable {
    @Override
    public ByteIterator iterator();

    @Deprecated
    public ByteIterator byteIterator();

    @Override
    public <T> T[] toArray(T[] var1);

    public boolean contains(byte var1);

    public byte[] toByteArray();

    public byte[] toByteArray(byte[] var1);

    public byte[] toArray(byte[] var1);

    @Override
    public boolean add(byte var1);

    public boolean rem(byte var1);

    public boolean addAll(ByteCollection var1);

    public boolean containsAll(ByteCollection var1);

    public boolean removeAll(ByteCollection var1);

    public boolean retainAll(ByteCollection var1);
}

