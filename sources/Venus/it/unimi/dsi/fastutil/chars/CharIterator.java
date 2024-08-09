/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharConsumer;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface CharIterator
extends Iterator<Character> {
    public char nextChar();

    @Override
    @Deprecated
    default public Character next() {
        return Character.valueOf(this.nextChar());
    }

    default public void forEachRemaining(CharConsumer charConsumer) {
        Objects.requireNonNull(charConsumer);
        while (this.hasNext()) {
            charConsumer.accept(this.nextChar());
        }
    }

    @Override
    @Deprecated
    default public void forEachRemaining(Consumer<? super Character> consumer) {
        this.forEachRemaining(consumer::accept);
    }

    default public int skip(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
        }
        int n2 = n;
        while (n2-- != 0 && this.hasNext()) {
            this.nextChar();
        }
        return n - n2 - 1;
    }

    @Override
    @Deprecated
    default public Object next() {
        return this.next();
    }
}

