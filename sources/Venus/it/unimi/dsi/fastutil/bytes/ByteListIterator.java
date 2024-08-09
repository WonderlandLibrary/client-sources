/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import java.util.ListIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ByteListIterator
extends ByteBidirectionalIterator,
ListIterator<Byte> {
    @Override
    default public void set(byte by) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void add(byte by) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public void set(Byte by) {
        this.set((byte)by);
    }

    @Override
    @Deprecated
    default public void add(Byte by) {
        this.add((byte)by);
    }

    @Override
    @Deprecated
    default public Byte next() {
        return ByteBidirectionalIterator.super.next();
    }

    @Override
    @Deprecated
    default public Byte previous() {
        return ByteBidirectionalIterator.super.previous();
    }

    @Override
    @Deprecated
    default public Object next() {
        return this.next();
    }

    @Override
    @Deprecated
    default public Object previous() {
        return this.previous();
    }

    @Override
    @Deprecated
    default public void add(Object object) {
        this.add((Byte)object);
    }

    @Override
    @Deprecated
    default public void set(Object object) {
        this.set((Byte)object);
    }
}

