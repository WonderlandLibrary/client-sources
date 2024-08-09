/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.SafeMath;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface ByteConsumer
extends Consumer<Byte>,
IntConsumer {
    @Override
    public void accept(byte var1);

    @Override
    @Deprecated
    default public void accept(int n) {
        this.accept(SafeMath.safeIntToByte(n));
    }

    @Override
    @Deprecated
    default public void accept(Byte by) {
        this.accept((byte)by);
    }

    default public ByteConsumer andThen(ByteConsumer byteConsumer) {
        Objects.requireNonNull(byteConsumer);
        return arg_0 -> this.lambda$andThen$0(byteConsumer, arg_0);
    }

    @Override
    @Deprecated
    default public ByteConsumer andThen(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        return arg_0 -> this.lambda$andThen$1(intConsumer, arg_0);
    }

    @Override
    @Deprecated
    default public Consumer<Byte> andThen(Consumer<? super Byte> consumer) {
        return Consumer.super.andThen(consumer);
    }

    @Override
    @Deprecated
    default public void accept(Object object) {
        this.accept((Byte)object);
    }

    @Override
    @Deprecated
    default public IntConsumer andThen(IntConsumer intConsumer) {
        return this.andThen(intConsumer);
    }

    private void lambda$andThen$1(IntConsumer intConsumer, byte by) {
        this.accept(by);
        intConsumer.accept(by);
    }

    private void lambda$andThen$0(ByteConsumer byteConsumer, byte by) {
        this.accept(by);
        byteConsumer.accept(by);
    }
}

