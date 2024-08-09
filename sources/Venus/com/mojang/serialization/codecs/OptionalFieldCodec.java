/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization.codecs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class OptionalFieldCodec<A>
extends MapCodec<Optional<A>> {
    private final String name;
    private final Codec<A> elementCodec;

    public OptionalFieldCodec(String string, Codec<A> codec) {
        this.name = string;
        this.elementCodec = codec;
    }

    @Override
    public <T> DataResult<Optional<A>> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
        T t = mapLike.get(this.name);
        if (t == null) {
            return DataResult.success(Optional.empty());
        }
        DataResult dataResult = this.elementCodec.parse(dynamicOps, t);
        if (dataResult.result().isPresent()) {
            return dataResult.map(Optional::of);
        }
        return DataResult.success(Optional.empty());
    }

    @Override
    public <T> RecordBuilder<T> encode(Optional<A> optional, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
        if (optional.isPresent()) {
            return recordBuilder.add(this.name, this.elementCodec.encodeStart(dynamicOps, optional.get()));
        }
        return recordBuilder;
    }

    @Override
    public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
        return Stream.of(dynamicOps.createString(this.name));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        OptionalFieldCodec optionalFieldCodec = (OptionalFieldCodec)object;
        return Objects.equals(this.name, optionalFieldCodec.name) && Objects.equals(this.elementCodec, optionalFieldCodec.elementCodec);
    }

    public int hashCode() {
        return Objects.hash(this.name, this.elementCodec);
    }

    public String toString() {
        return "OptionalFieldCodec[" + this.name + ": " + this.elementCodec + ']';
    }

    @Override
    public RecordBuilder encode(Object object, DynamicOps dynamicOps, RecordBuilder recordBuilder) {
        return this.encode((Optional)object, dynamicOps, recordBuilder);
    }
}

