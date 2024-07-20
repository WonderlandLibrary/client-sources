/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharSortedSet;

public abstract class AbstractCharSortedSet
extends AbstractCharSet
implements CharSortedSet {
    protected AbstractCharSortedSet() {
    }

    @Override
    @Deprecated
    public CharSortedSet headSet(Character to) {
        return this.headSet(to.charValue());
    }

    @Override
    @Deprecated
    public CharSortedSet tailSet(Character from) {
        return this.tailSet(from.charValue());
    }

    @Override
    @Deprecated
    public CharSortedSet subSet(Character from, Character to) {
        return this.subSet(from.charValue(), to.charValue());
    }

    @Override
    @Deprecated
    public Character first() {
        return Character.valueOf(this.firstChar());
    }

    @Override
    @Deprecated
    public Character last() {
        return Character.valueOf(this.lastChar());
    }

    @Override
    @Deprecated
    public CharBidirectionalIterator charIterator() {
        return this.iterator();
    }

    @Override
    public abstract CharBidirectionalIterator iterator();
}

