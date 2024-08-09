/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.SafeMath;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface CharConsumer
extends Consumer<Character>,
IntConsumer {
    @Override
    public void accept(char var1);

    @Override
    @Deprecated
    default public void accept(int n) {
        this.accept(SafeMath.safeIntToChar(n));
    }

    @Override
    @Deprecated
    default public void accept(Character c) {
        this.accept(c.charValue());
    }

    default public CharConsumer andThen(CharConsumer charConsumer) {
        Objects.requireNonNull(charConsumer);
        return arg_0 -> this.lambda$andThen$0(charConsumer, arg_0);
    }

    @Override
    @Deprecated
    default public CharConsumer andThen(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        return arg_0 -> this.lambda$andThen$1(intConsumer, arg_0);
    }

    @Override
    @Deprecated
    default public Consumer<Character> andThen(Consumer<? super Character> consumer) {
        return Consumer.super.andThen(consumer);
    }

    @Override
    @Deprecated
    default public void accept(Object object) {
        this.accept((Character)object);
    }

    @Override
    @Deprecated
    default public IntConsumer andThen(IntConsumer intConsumer) {
        return this.andThen(intConsumer);
    }

    private void lambda$andThen$1(IntConsumer intConsumer, char c) {
        this.accept(c);
        intConsumer.accept(c);
    }

    private void lambda$andThen$0(CharConsumer charConsumer, char c) {
        this.accept(c);
        charConsumer.accept(c);
    }
}

