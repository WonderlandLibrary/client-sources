/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import java.util.Comparator;

@FunctionalInterface
public interface ByteComparator
extends Comparator<Byte> {
    @Override
    public int compare(byte var1, byte var2);

    @Override
    @Deprecated
    default public int compare(Byte by, Byte by2) {
        return this.compare((byte)by, (byte)by2);
    }

    @Override
    @Deprecated
    default public int compare(Object object, Object object2) {
        return this.compare((Byte)object, (Byte)object2);
    }
}

