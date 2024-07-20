/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import java.util.Iterator;

public interface BooleanIterator
extends Iterator<Boolean> {
    public boolean nextBoolean();

    public int skip(int var1);
}

