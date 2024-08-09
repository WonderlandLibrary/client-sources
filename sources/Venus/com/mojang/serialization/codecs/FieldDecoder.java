/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization.codecs;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapDecoder;
import com.mojang.serialization.MapLike;
import java.util.Objects;
import java.util.stream.Stream;

public final class FieldDecoder<A>
extends MapDecoder.Implementation<A> {
    protected final String name;
    private final Decoder<A> elementCodec;

    public FieldDecoder(String string, Decoder<A> decoder) {
        this.name = string;
        this.elementCodec = decoder;
    }

    @Override
    public <T> DataResult<A> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
        T t = mapLike.get(this.name);
        if (t == null) {
            return DataResult.error("No key " + this.name + " in " + mapLike);
        }
        return this.elementCodec.parse(dynamicOps, t);
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
        FieldDecoder fieldDecoder = (FieldDecoder)object;
        return Objects.equals(this.name, fieldDecoder.name) && Objects.equals(this.elementCodec, fieldDecoder.elementCodec);
    }

    public int hashCode() {
        return Objects.hash(this.name, this.elementCodec);
    }

    public String toString() {
        return "FieldDecoder[" + this.name + ": " + this.elementCodec + ']';
    }
}

