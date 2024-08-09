/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.SafeMath;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface ShortConsumer
extends Consumer<Short>,
IntConsumer {
    @Override
    public void accept(short var1);

    @Override
    @Deprecated
    default public void accept(int n) {
        this.accept(SafeMath.safeIntToShort(n));
    }

    @Override
    @Deprecated
    default public void accept(Short s) {
        this.accept((short)s);
    }

    default public ShortConsumer andThen(ShortConsumer shortConsumer) {
        Objects.requireNonNull(shortConsumer);
        return arg_0 -> this.lambda$andThen$0(shortConsumer, arg_0);
    }

    @Override
    @Deprecated
    default public ShortConsumer andThen(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        return arg_0 -> this.lambda$andThen$1(intConsumer, arg_0);
    }

    @Override
    @Deprecated
    default public Consumer<Short> andThen(Consumer<? super Short> consumer) {
        return Consumer.super.andThen(consumer);
    }

    @Override
    @Deprecated
    default public void accept(Object object) {
        this.accept((Short)object);
    }

    @Override
    @Deprecated
    default public IntConsumer andThen(IntConsumer intConsumer) {
        return this.andThen(intConsumer);
    }

    private void lambda$andThen$1(IntConsumer intConsumer, short s) {
        this.accept(s);
        intConsumer.accept(s);
    }

    private void lambda$andThen$0(ShortConsumer shortConsumer, short s) {
        this.accept(s);
        shortConsumer.accept(s);
    }
}

