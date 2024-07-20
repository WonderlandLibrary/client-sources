/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import java.util.Iterator;

public interface ShortIterator
extends Iterator<Short> {
    public short nextShort();

    public int skip(int var1);
}

