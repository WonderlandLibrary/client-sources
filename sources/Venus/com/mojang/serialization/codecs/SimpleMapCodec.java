/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization.codecs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.codecs.BaseMapCodec;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public final class SimpleMapCodec<K, V>
extends MapCodec<Map<K, V>>
implements BaseMapCodec<K, V> {
    private final Codec<K> keyCodec;
    private final Codec<V> elementCodec;
    private final Keyable keys;

    public SimpleMapCodec(Codec<K> codec, Codec<V> codec2, Keyable keyable) {
        this.keyCodec = codec;
        this.elementCodec = codec2;
        this.keys = keyable;
    }

    @Override
    public Codec<K> keyCodec() {
        return this.keyCodec;
    }

    @Override
    public Codec<V> elementCodec() {
        return this.elementCodec;
    }

    @Override
    public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
        return this.keys.keys(dynamicOps);
    }

    @Override
    public <T> DataResult<Map<K, V>> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
        return BaseMapCodec.super.decode(dynamicOps, mapLike);
    }

    @Override
    public <T> RecordBuilder<T> encode(Map<K, V> map, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
        return BaseMapCodec.super.encode(map, dynamicOps, recordBuilder);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        SimpleMapCodec simpleMapCodec = (SimpleMapCodec)object;
        return Objects.equals(this.keyCodec, simpleMapCodec.keyCodec) && Objects.equals(this.elementCodec, simpleMapCodec.elementCodec);
    }

    public int hashCode() {
        return Objects.hash(this.keyCodec, this.elementCodec);
    }

    public String toString() {
        return "SimpleMapCodec[" + this.keyCodec + " -> " + this.elementCodec + ']';
    }

    @Override
    public RecordBuilder encode(Object object, DynamicOps dynamicOps, RecordBuilder recordBuilder) {
        return this.encode((Map)object, dynamicOps, recordBuilder);
    }
}

