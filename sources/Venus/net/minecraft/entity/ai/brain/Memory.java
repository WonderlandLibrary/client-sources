/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;

public class Memory<T> {
    private final T value;
    private long timeToLive;

    public Memory(T t, long l) {
        this.value = t;
        this.timeToLive = l;
    }

    public void tick() {
        if (this.isForgettable()) {
            --this.timeToLive;
        }
    }

    public static <T> Memory<T> create(T t) {
        return new Memory<T>(t, Long.MAX_VALUE);
    }

    public static <T> Memory<T> create(T t, long l) {
        return new Memory<T>(t, l);
    }

    public T getValue() {
        return this.value;
    }

    public boolean isForgotten() {
        return this.timeToLive <= 0L;
    }

    public String toString() {
        return this.value.toString() + (String)(this.isForgettable() ? " (ttl: " + this.timeToLive + ")" : "");
    }

    public boolean isForgettable() {
        return this.timeToLive != Long.MAX_VALUE;
    }

    public static <T> Codec<Memory<T>> createCodec(Codec<T> codec) {
        return RecordCodecBuilder.create(arg_0 -> Memory.lambda$createCodec$3(codec, arg_0));
    }

    private static App lambda$createCodec$3(Codec codec, RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)codec.fieldOf("value")).forGetter(Memory::lambda$createCodec$0), Codec.LONG.optionalFieldOf("ttl").forGetter(Memory::lambda$createCodec$1)).apply(instance, Memory::lambda$createCodec$2);
    }

    private static Memory lambda$createCodec$2(Object object, Optional optional) {
        return new Memory<Object>(object, optional.orElse(Long.MAX_VALUE));
    }

    private static Optional lambda$createCodec$1(Memory memory) {
        return memory.isForgettable() ? Optional.of(memory.timeToLive) : Optional.empty();
    }

    private static Object lambda$createCodec$0(Memory memory) {
        return memory.value;
    }
}

