/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization.codecs;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;

public final class PairCodec<F, S>
implements Codec<Pair<F, S>> {
    private final Codec<F> first;
    private final Codec<S> second;

    public PairCodec(Codec<F> codec, Codec<S> codec2) {
        this.first = codec;
        this.second = codec2;
    }

    @Override
    public <T> DataResult<Pair<Pair<F, S>, T>> decode(DynamicOps<T> dynamicOps, T t) {
        return this.first.decode(dynamicOps, t).flatMap(arg_0 -> this.lambda$decode$1(dynamicOps, arg_0));
    }

    @Override
    public <T> DataResult<T> encode(Pair<F, S> pair, DynamicOps<T> dynamicOps, T t) {
        return this.second.encode(pair.getSecond(), dynamicOps, t).flatMap(arg_0 -> this.lambda$encode$2(pair, dynamicOps, arg_0));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        PairCodec pairCodec = (PairCodec)object;
        return Objects.equals(this.first, pairCodec.first) && Objects.equals(this.second, pairCodec.second);
    }

    public int hashCode() {
        return Objects.hash(this.first, this.second);
    }

    public String toString() {
        return "PairCodec[" + this.first + ", " + this.second + ']';
    }

    @Override
    public DataResult encode(Object object, DynamicOps dynamicOps, Object object2) {
        return this.encode((Pair)object, dynamicOps, object2);
    }

    private DataResult lambda$encode$2(Pair pair, DynamicOps dynamicOps, Object object) {
        return this.first.encode(pair.getFirst(), dynamicOps, object);
    }

    private DataResult lambda$decode$1(DynamicOps dynamicOps, Pair pair) {
        return this.second.decode(dynamicOps, pair.getSecond()).map(arg_0 -> PairCodec.lambda$null$0(pair, arg_0));
    }

    private static Pair lambda$null$0(Pair pair, Pair pair2) {
        return Pair.of(Pair.of(pair.getFirst(), pair2.getFirst()), pair2.getSecond());
    }
}

