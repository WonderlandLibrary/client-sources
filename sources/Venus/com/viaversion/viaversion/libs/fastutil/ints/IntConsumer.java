/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.Objects;
import java.util.function.Consumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface IntConsumer
extends Consumer<Integer>,
java.util.function.IntConsumer {
    @Override
    @Deprecated
    default public void accept(Integer n) {
        this.accept(n.intValue());
    }

    @Override
    default public IntConsumer andThen(java.util.function.IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        return arg_0 -> this.lambda$andThen$0(intConsumer, arg_0);
    }

    default public IntConsumer andThen(IntConsumer intConsumer) {
        return this.andThen((java.util.function.IntConsumer)intConsumer);
    }

    @Override
    @Deprecated
    default public Consumer<Integer> andThen(Consumer<? super Integer> consumer) {
        return Consumer.super.andThen(consumer);
    }

    @Override
    @Deprecated
    default public void accept(Object object) {
        this.accept((Integer)object);
    }

    @Override
    default public java.util.function.IntConsumer andThen(java.util.function.IntConsumer intConsumer) {
        return this.andThen(intConsumer);
    }

    private void lambda$andThen$0(java.util.function.IntConsumer intConsumer, int n) {
        this.accept(n);
        intConsumer.accept(n);
    }
}

