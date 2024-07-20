/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import java.util.Comparator;

public interface ShortComparator
extends Comparator<Short> {
    @Override
    public int compare(short var1, short var2);
}

