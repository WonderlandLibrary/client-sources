/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public interface CharBidirectionalIterator
extends CharIterator,
ObjectBidirectionalIterator<Character> {
    public char previousChar();

    @Override
    public int back(int var1);
}

