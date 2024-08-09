/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface BooleanIterator
extends Iterator<Boolean> {
    public boolean nextBoolean();

    @Override
    @Deprecated
    default public Boolean next() {
        return this.nextBoolean();
    }

    default public void forEachRemaining(BooleanConsumer booleanConsumer) {
        Objects.requireNonNull(booleanConsumer);
        while (this.hasNext()) {
            booleanConsumer.accept(this.nextBoolean());
        }
    }

    @Override
    @Deprecated
    default public void forEachRemaining(Consumer<? super Boolean> consumer) {
        this.forEachRemaining(consumer::accept);
    }

    default public int skip(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
        }
        int n2 = n;
        while (n2-- != 0 && this.hasNext()) {
            this.nextBoolean();
        }
        return n - n2 - 1;
    }

    @Override
    @Deprecated
    default public Object next() {
        return this.next();
    }
}

