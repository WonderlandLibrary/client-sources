/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface CharSet
extends CharCollection,
Set<Character> {
    @Override
    public CharIterator iterator();

    public boolean remove(char var1);

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        return CharCollection.super.remove(object);
    }

    @Override
    @Deprecated
    default public boolean add(Character c) {
        return CharCollection.super.add(c);
    }

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        return CharCollection.super.contains(object);
    }

    @Override
    @Deprecated
    default public boolean rem(char c) {
        return this.remove(c);
    }

    @Override
    @Deprecated
    default public boolean add(Object object) {
        return this.add((Character)object);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }
}

