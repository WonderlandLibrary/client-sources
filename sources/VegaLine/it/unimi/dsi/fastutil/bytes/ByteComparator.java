/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import java.util.Comparator;

public interface ByteComparator
extends Comparator<Byte> {
    @Override
    public int compare(byte var1, byte var2);
}

