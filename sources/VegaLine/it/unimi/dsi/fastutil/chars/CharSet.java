/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import java.util.Set;

public interface CharSet
extends CharCollection,
Set<Character> {
    @Override
    public CharIterator iterator();

    public boolean remove(char var1);
}

