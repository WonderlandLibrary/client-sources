/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.chars.CharComparator;

public interface CharPriorityQueue
extends PriorityQueue<Character> {
    @Override
    public void enqueue(char var1);

    public char dequeueChar();

    public char firstChar();

    public char lastChar();

    public CharComparator comparator();
}

