/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import java.util.Objects;
import java.util.function.Consumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface LongConsumer
extends Consumer<Long>,
java.util.function.LongConsumer {
    @Override
    @Deprecated
    default public void accept(Long l) {
        this.accept(l.longValue());
    }

    @Override
    default public LongConsumer andThen(java.util.function.LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
        return arg_0 -> this.lambda$andThen$0(longConsumer, arg_0);
    }

    @Override
    @Deprecated
    default public Consumer<Long> andThen(Consumer<? super Long> consumer) {
        return Consumer.super.andThen(consumer);
    }

    @Override
    @Deprecated
    default public void accept(Object object) {
        this.accept((Long)object);
    }

    @Override
    default public java.util.function.LongConsumer andThen(java.util.function.LongConsumer longConsumer) {
        return this.andThen(longConsumer);
    }

    private void lambda$andThen$0(java.util.function.LongConsumer longConsumer, long l) {
        this.accept(l);
        longConsumer.accept(l);
    }
}

