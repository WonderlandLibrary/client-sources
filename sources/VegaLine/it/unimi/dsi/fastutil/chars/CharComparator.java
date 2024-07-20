/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import java.util.Comparator;

public interface CharComparator
extends Comparator<Character> {
    @Override
    public int compare(char var1, char var2);
}

