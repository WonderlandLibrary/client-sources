/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.IndirectPriorityQueue;
import it.unimi.dsi.fastutil.chars.CharComparator;

public interface CharIndirectPriorityQueue
extends IndirectPriorityQueue<Character> {
    public CharComparator comparator();
}

