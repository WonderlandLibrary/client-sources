/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import java.util.ListIterator;

public interface CharListIterator
extends ListIterator<Character>,
CharBidirectionalIterator {
    @Override
    public void set(char var1);

    @Override
    public void add(char var1);
}

