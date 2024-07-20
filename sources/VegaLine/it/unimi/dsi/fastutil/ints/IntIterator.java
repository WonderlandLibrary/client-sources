/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import java.util.Iterator;

public interface IntIterator
extends Iterator<Integer> {
    public int nextInt();

    public int skip(int var1);
}

