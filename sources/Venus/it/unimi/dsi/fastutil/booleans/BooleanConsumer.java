/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface BooleanConsumer
extends Consumer<Boolean> {
    @Override
    public void accept(boolean var1);

    @Override
    @Deprecated
    default public void accept(Boolean bl) {
        this.accept((boolean)bl);
    }

    default public BooleanConsumer andThen(BooleanConsumer booleanConsumer) {
        Objects.requireNonNull(booleanConsumer);
        return arg_0 -> this.lambda$andThen$0(booleanConsumer, arg_0);
    }

    @Override
    @Deprecated
    default public Consumer<Boolean> andThen(Consumer<? super Boolean> consumer) {
        return Consumer.super.andThen(consumer);
    }

    @Override
    @Deprecated
    default public void accept(Object object) {
        this.accept((Boolean)object);
    }

    private void lambda$andThen$0(BooleanConsumer booleanConsumer, boolean bl) {
        this.accept(bl);
        booleanConsumer.accept(bl);
    }
}

