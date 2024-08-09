/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization.codecs;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.util.Objects;
import java.util.stream.Stream;

public final class PairMapCodec<F, S>
extends MapCodec<Pair<F, S>> {
    private final MapCodec<F> first;
    private final MapCodec<S> second;

    public PairMapCodec(MapCodec<F> mapCodec, MapCodec<S> mapCodec2) {
        this.first = mapCodec;
        this.second = mapCodec2;
    }

    @Override
    public <T> DataResult<Pair<F, S>> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
        return this.first.decode(dynamicOps, mapLike).flatMap(arg_0 -> this.lambda$decode$1(dynamicOps, mapLike, arg_0));
    }

    @Override
    public <T> RecordBuilder<T> encode(Pair<F, S> pair, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
        return this.first.encode(pair.getFirst(), dynamicOps, this.second.encode(pair.getSecond(), dynamicOps, recordBuilder));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        PairMapCodec pairMapCodec = (PairMapCodec)object;
        return Objects.equals(this.first, pairMapCodec.first) && Objects.equals(this.second, pairMapCodec.second);
    }

    public int hashCode() {
        return Objects.hash(this.first, this.second);
    }

    public String toString() {
        return "PairMapCodec[" + this.first + ", " + this.second + ']';
    }

    @Override
    public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
        return Stream.concat(this.first.keys(dynamicOps), this.second.keys(dynamicOps));
    }

    @Override
    public RecordBuilder encode(Object object, DynamicOps dynamicOps, RecordBuilder recordBuilder) {
        return this.encode((Pair)object, dynamicOps, recordBuilder);
    }

    private DataResult lambda$decode$1(DynamicOps dynamicOps, MapLike mapLike, Object object) {
        return this.second.decode(dynamicOps, mapLike).map(arg_0 -> PairMapCodec.lambda$null$0(object, arg_0));
    }

    private static Pair lambda$null$0(Object object, Object object2) {
        return Pair.of(object, object2);
    }
}

