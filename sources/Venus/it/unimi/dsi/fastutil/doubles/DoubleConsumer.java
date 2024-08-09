/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import java.util.Objects;
import java.util.function.Consumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface DoubleConsumer
extends Consumer<Double>,
java.util.function.DoubleConsumer {
    @Override
    @Deprecated
    default public void accept(Double d) {
        this.accept(d.doubleValue());
    }

    @Override
    default public DoubleConsumer andThen(java.util.function.DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        return arg_0 -> this.lambda$andThen$0(doubleConsumer, arg_0);
    }

    @Override
    @Deprecated
    default public Consumer<Double> andThen(Consumer<? super Double> consumer) {
        return Consumer.super.andThen(consumer);
    }

    @Override
    @Deprecated
    default public void accept(Object object) {
        this.accept((Double)object);
    }

    @Override
    default public java.util.function.DoubleConsumer andThen(java.util.function.DoubleConsumer doubleConsumer) {
        return this.andThen(doubleConsumer);
    }

    private void lambda$andThen$0(java.util.function.DoubleConsumer doubleConsumer, double d) {
        this.accept(d);
        doubleConsumer.accept(d);
    }
}

