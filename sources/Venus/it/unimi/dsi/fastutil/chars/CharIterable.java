/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharIterator;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface CharIterable
extends Iterable<Character> {
    public CharIterator iterator();

    default public void forEach(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        CharIterator charIterator = this.iterator();
        while (charIterator.hasNext()) {
            intConsumer.accept(charIterator.nextChar());
        }
    }

    @Override
    @Deprecated
    default public void forEach(Consumer<? super Character> consumer) {
        this.forEach(new IntConsumer(this, consumer){
            final Consumer val$action;
            final CharIterable this$0;
            {
                this.this$0 = charIterable;
                this.val$action = consumer;
            }

            @Override
            public void accept(int n) {
                this.val$action.accept(Character.valueOf((char)n));
            }
        });
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }
}

