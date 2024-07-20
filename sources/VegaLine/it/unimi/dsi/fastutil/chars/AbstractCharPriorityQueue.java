/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.AbstractPriorityQueue;
import it.unimi.dsi.fastutil.chars.CharPriorityQueue;
import java.io.Serializable;

public abstract class AbstractCharPriorityQueue
extends AbstractPriorityQueue<Character>
implements Serializable,
CharPriorityQueue {
    private static final long serialVersionUID = 1L;

    @Override
    @Deprecated
    public void enqueue(Character x) {
        this.enqueue(x.charValue());
    }

    @Override
    @Deprecated
    public Character dequeue() {
        return Character.valueOf(this.dequeueChar());
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
    public char lastChar() {
        throw new UnsupportedOperationException();
    }
}

