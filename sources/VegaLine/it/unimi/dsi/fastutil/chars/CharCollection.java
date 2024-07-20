/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharIterable;
import it.unimi.dsi.fastutil.chars.CharIterator;
import java.util.Collection;

public interface CharCollection
extends Collection<Character>,
CharIterable {
    @Override
    public CharIterator iterator();

    @Deprecated
    public CharIterator charIterator();

    @Override
    public <T> T[] toArray(T[] var1);

    public boolean contains(char var1);

    public char[] toCharArray();

    public char[] toCharArray(char[] var1);

    public char[] toArray(char[] var1);

    @Override
    public boolean add(char var1);

    public boolean rem(char var1);

    public boolean addAll(CharCollection var1);

    public boolean containsAll(CharCollection var1);

    public boolean removeAll(CharCollection var1);

    public boolean retainAll(CharCollection var1);
}

