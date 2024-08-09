/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization.codecs;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.util.Objects;
import java.util.stream.Stream;

public final class EitherMapCodec<F, S>
extends MapCodec<Either<F, S>> {
    private final MapCodec<F> first;
    private final MapCodec<S> second;

    public EitherMapCodec(MapCodec<F> mapCodec, MapCodec<S> mapCodec2) {
        this.first = mapCodec;
        this.second = mapCodec2;
    }

    @Override
    public <T> DataResult<Either<F, S>> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
        DataResult<Either<F, S>> dataResult = this.first.decode(dynamicOps, mapLike).map(Either::left);
        if (dataResult.result().isPresent()) {
            return dataResult;
        }
        return this.second.decode(dynamicOps, mapLike).map(Either::right);
    }

    @Override
    public <T> RecordBuilder<T> encode(Either<F, S> either, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
        return either.map(arg_0 -> this.lambda$encode$0(dynamicOps, recordBuilder, arg_0), arg_0 -> this.lambda$encode$1(dynamicOps, recordBuilder, arg_0));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        EitherMapCodec eitherMapCodec = (EitherMapCodec)object;
        return Objects.equals(this.first, eitherMapCodec.first) && Objects.equals(this.second, eitherMapCodec.second);
    }

    public int hashCode() {
        return Objects.hash(this.first, this.second);
    }

    public String toString() {
        return "EitherMapCodec[" + this.first + ", " + this.second + ']';
    }

    @Override
    public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
        return Stream.concat(this.first.keys(dynamicOps), this.second.keys(dynamicOps));
    }

    @Override
    public RecordBuilder encode(Object object, DynamicOps dynamicOps, RecordBuilder recordBuilder) {
        return this.encode((Either)object, dynamicOps, recordBuilder);
    }

    private RecordBuilder lambda$encode$1(DynamicOps dynamicOps, RecordBuilder recordBuilder, Object object) {
        return this.second.encode(object, dynamicOps, recordBuilder);
    }

    private RecordBuilder lambda$encode$0(DynamicOps dynamicOps, RecordBuilder recordBuilder, Object object) {
        return this.first.encode(object, dynamicOps, recordBuilder);
    }
}

