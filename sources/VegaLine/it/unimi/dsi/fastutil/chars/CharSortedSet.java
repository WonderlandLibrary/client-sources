/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSet;
import java.util.SortedSet;

public interface CharSortedSet
extends CharSet,
SortedSet<Character> {
    public CharBidirectionalIterator iterator(char var1);

    @Override
    @Deprecated
    public CharBidirectionalIterator charIterator();

    @Override
    public CharBidirectionalIterator iterator();

    public CharSortedSet subSet(Character var1, Character var2);

    public CharSortedSet headSet(Character var1);

    public CharSortedSet tailSet(Character var1);

    public CharComparator comparator();

    public CharSortedSet subSet(char var1, char var2);

    public CharSortedSet headSet(char var1);

    public CharSortedSet tailSet(char var1);

    public char firstChar();

    public char lastChar();
}

