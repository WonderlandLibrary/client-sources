/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import java.util.Iterator;

public interface FloatIterator
extends Iterator<Float> {
    public float nextFloat();

    public int skip(int var1);
}

