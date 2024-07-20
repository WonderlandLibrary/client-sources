/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import java.util.Iterator;

public interface ByteIterator
extends Iterator<Byte> {
    public byte nextByte();

    public int skip(int var1);
}

