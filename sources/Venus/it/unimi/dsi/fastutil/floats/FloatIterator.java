/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatConsumer;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface FloatIterator
extends Iterator<Float> {
    public float nextFloat();

    @Override
    @Deprecated
    default public Float next() {
        return Float.valueOf(this.nextFloat());
    }

    default public void forEachRemaining(FloatConsumer floatConsumer) {
        Objects.requireNonNull(floatConsumer);
        while (this.hasNext()) {
            floatConsumer.accept(this.nextFloat());
        }
    }

    @Override
    @Deprecated
    default public void forEachRemaining(Consumer<? super Float> consumer) {
        this.forEachRemaining(consumer::accept);
    }

    default public int skip(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
        }
        int n2 = n;
        while (n2-- != 0 && this.hasNext()) {
            this.nextFloat();
        }
        return n - n2 - 1;
    }

    @Override
    @Deprecated
    default public Object next() {
        return this.next();
    }
}

