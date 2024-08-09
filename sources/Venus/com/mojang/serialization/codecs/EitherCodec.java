/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization.codecs;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;

public final class EitherCodec<F, S>
implements Codec<Either<F, S>> {
    private final Codec<F> first;
    private final Codec<S> second;

    public EitherCodec(Codec<F> codec, Codec<S> codec2) {
        this.first = codec;
        this.second = codec2;
    }

    @Override
    public <T> DataResult<Pair<Either<F, S>, T>> decode(DynamicOps<T> dynamicOps, T t) {
        DataResult<Pair<Either<F, S>, T>> dataResult = this.first.decode(dynamicOps, t).map(EitherCodec::lambda$decode$0);
        if (dataResult.result().isPresent()) {
            return dataResult;
        }
        return this.second.decode(dynamicOps, t).map(EitherCodec::lambda$decode$1);
    }

    @Override
    public <T> DataResult<T> encode(Either<F, S> either, DynamicOps<T> dynamicOps, T t) {
        return either.map(arg_0 -> this.lambda$encode$2(dynamicOps, t, arg_0), arg_0 -> this.lambda$encode$3(dynamicOps, t, arg_0));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        EitherCodec eitherCodec = (EitherCodec)object;
        return Objects.equals(this.first, eitherCodec.first) && Objects.equals(this.second, eitherCodec.second);
    }

    public int hashCode() {
        return Objects.hash(this.first, this.second);
    }

    public String toString() {
        return "EitherCodec[" + this.first + ", " + this.second + ']';
    }

    @Override
    public DataResult encode(Object object, DynamicOps dynamicOps, Object object2) {
        return this.encode((Either)object, dynamicOps, object2);
    }

    private DataResult lambda$encode$3(DynamicOps dynamicOps, Object object, Object object2) {
        return this.second.encode(object2, dynamicOps, object);
    }

    private DataResult lambda$encode$2(DynamicOps dynamicOps, Object object, Object object2) {
        return this.first.encode(object2, dynamicOps, object);
    }

    private static Pair lambda$decode$1(Pair pair) {
        return pair.mapFirst(Either::right);
    }

    private static Pair lambda$decode$0(Pair pair) {
        return pair.mapFirst(Either::left);
    }
}

