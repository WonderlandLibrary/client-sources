/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import java.util.Iterator;

public interface CharIterator
extends Iterator<Character> {
    public char nextChar();

    public int skip(int var1);
}

