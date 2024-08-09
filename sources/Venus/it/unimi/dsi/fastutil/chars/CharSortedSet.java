/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharBidirectionalIterable;
import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface CharSortedSet
extends CharSet,
SortedSet<Character>,
CharBidirectionalIterable {
    public CharBidirectionalIterator iterator(char var1);

    @Override
    public CharBidirectionalIterator iterator();

    public CharSortedSet subSet(char var1, char var2);

    public CharSortedSet headSet(char var1);

    public CharSortedSet tailSet(char var1);

    public CharComparator comparator();

    public char firstChar();

    public char lastChar();

    @Deprecated
    default public CharSortedSet subSet(Character c, Character c2) {
        return this.subSet(c.charValue(), c2.charValue());
    }

    @Deprecated
    default public CharSortedSet headSet(Character c) {
        return this.headSet(c.charValue());
    }

    @Deprecated
    default public CharSortedSet tailSet(Character c) {
        return this.tailSet(c.charValue());
    }

    @Override
    @Deprecated
    default public Character first() {
        return Character.valueOf(this.firstChar());
    }

    @Override
    @Deprecated
    default public Character last() {
        return Character.valueOf(this.lastChar());
    }

    @Override
    default public CharIterator iterator() {
        return this.iterator();
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    @Override
    @Deprecated
    default public Object last() {
        return this.last();
    }

    @Override
    @Deprecated
    default public Object first() {
        return this.first();
    }

    @Override
    @Deprecated
    default public SortedSet tailSet(Object object) {
        return this.tailSet((Character)object);
    }

    @Override
    @Deprecated
    default public SortedSet headSet(Object object) {
        return this.headSet((Character)object);
    }

    @Override
    @Deprecated
    default public SortedSet subSet(Object object, Object object2) {
        return this.subSet((Character)object, (Character)object2);
    }

    @Override
    default public Comparator comparator() {
        return this.comparator();
    }
}

