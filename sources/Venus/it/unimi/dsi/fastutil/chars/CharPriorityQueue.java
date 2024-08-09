/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.chars.CharComparator;
import java.util.Comparator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface CharPriorityQueue
extends PriorityQueue<Character> {
    @Override
    public void enqueue(char var1);

    public char dequeueChar();

    public char firstChar();

    default public char lastChar() {
        throw new UnsupportedOperationException();
    }

    public CharComparator comparator();

    @Override
    @Deprecated
    default public void enqueue(Character c) {
        this.enqueue(c.charValue());
    }

    @Override
    @Deprecated
    default public Character dequeue() {
        return Character.valueOf(this.dequeueChar());
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
    default public Comparator comparator() {
        return this.comparator();
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
    default public Object dequeue() {
        return this.dequeue();
    }

    @Override
    @Deprecated
    default public void enqueue(Object object) {
        this.enqueue((Character)object);
    }
}

