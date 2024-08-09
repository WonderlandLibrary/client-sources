/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.ShortConsumer;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ShortIterator
extends Iterator<Short> {
    public short nextShort();

    @Override
    @Deprecated
    default public Short next() {
        return this.nextShort();
    }

    default public void forEachRemaining(ShortConsumer shortConsumer) {
        Objects.requireNonNull(shortConsumer);
        while (this.hasNext()) {
            shortConsumer.accept(this.nextShort());
        }
    }

    @Override
    @Deprecated
    default public void forEachRemaining(Consumer<? super Short> consumer) {
        this.forEachRemaining(consumer::accept);
    }

    default public int skip(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
        }
        int n2 = n;
        while (n2-- != 0 && this.hasNext()) {
            this.nextShort();
        }
        return n - n2 - 1;
    }

    @Override
    @Deprecated
    default public Object next() {
        return this.next();
    }
}

