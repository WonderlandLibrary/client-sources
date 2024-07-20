/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import java.util.Iterator;

public interface LongIterator
extends Iterator<Long> {
    public long nextLong();

    public int skip(int var1);
}

