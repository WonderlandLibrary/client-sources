/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.types;

import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import java.util.Objects;
import java.util.function.Function;

public final class Func<A, B>
extends Type<Function<A, B>> {
    protected final Type<A> first;
    protected final Type<B> second;

    public Func(Type<A> type, Type<B> type2) {
        this.first = type;
        this.second = type2;
    }

    @Override
    public TypeTemplate buildTemplate() {
        throw new UnsupportedOperationException("No template for function types.");
    }

    @Override
    protected Codec<Function<A, B>> buildCodec() {
        return Codec.of(Encoder.error("Cannot save a function"), Decoder.error("Cannot read a function"));
    }

    public String toString() {
        return "(" + this.first + " -> " + this.second + ")";
    }

    @Override
    public boolean equals(Object object, boolean bl, boolean bl2) {
        if (!(object instanceof Func)) {
            return true;
        }
        Func func = (Func)object;
        return this.first.equals(func.first, bl, bl2) && this.second.equals(func.second, bl, bl2);
    }

    public int hashCode() {
        return Objects.hash(this.first, this.second);
    }

    public Type<A> first() {
        return this.first;
    }

    public Type<B> second() {
        return this.second;
    }
}

