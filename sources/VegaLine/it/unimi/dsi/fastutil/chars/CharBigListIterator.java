/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;

public interface CharBigListIterator
extends CharBidirectionalIterator,
BigListIterator<Character> {
    public void set(char var1);

    public void add(char var1);

    public void set(Character var1);

    public void add(Character var1);
}

