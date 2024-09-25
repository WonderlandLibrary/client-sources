/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.fastutil.ints;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import us.myles.viaversion.libs.fastutil.ints.IntIterator;

public interface IntIterable
extends Iterable<Integer> {
    public IntIterator iterator();

    default public void forEach(IntConsumer action) {
        Objects.requireNonNull(action);
        IntIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            action.accept(iterator.nextInt());
        }
    }

    @Override
    @Deprecated
    default public void forEach(Consumer<? super Integer> action) {
        this.forEach(action::accept);
    }
}

