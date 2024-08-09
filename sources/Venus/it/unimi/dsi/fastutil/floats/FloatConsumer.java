/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.SafeMath;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface FloatConsumer
extends Consumer<Float>,
DoubleConsumer {
    @Override
    public void accept(float var1);

    @Override
    @Deprecated
    default public void accept(double d) {
        this.accept(SafeMath.safeDoubleToFloat(d));
    }

    @Override
    @Deprecated
    default public void accept(Float f) {
        this.accept(f.floatValue());
    }

    default public FloatConsumer andThen(FloatConsumer floatConsumer) {
        Objects.requireNonNull(floatConsumer);
        return arg_0 -> this.lambda$andThen$0(floatConsumer, arg_0);
    }

    @Override
    @Deprecated
    default public FloatConsumer andThen(DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        return arg_0 -> this.lambda$andThen$1(doubleConsumer, arg_0);
    }

    @Override
    @Deprecated
    default public Consumer<Float> andThen(Consumer<? super Float> consumer) {
        return Consumer.super.andThen(consumer);
    }

    @Override
    @Deprecated
    default public void accept(Object object) {
        this.accept((Float)object);
    }

    @Override
    @Deprecated
    default public DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return this.andThen(doubleConsumer);
    }

    private void lambda$andThen$1(DoubleConsumer doubleConsumer, float f) {
        this.accept(f);
        doubleConsumer.accept(f);
    }

    private void lambda$andThen$0(FloatConsumer floatConsumer, float f) {
        this.accept(f);
        floatConsumer.accept(f);
    }
}

