/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization.codecs;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.codecs.BaseMapCodec;
import java.util.Map;
import java.util.Objects;

public final class UnboundedMapCodec<K, V>
implements BaseMapCodec<K, V>,
Codec<Map<K, V>> {
    private final Codec<K> keyCodec;
    private final Codec<V> elementCodec;

    public UnboundedMapCodec(Codec<K> codec, Codec<V> codec2) {
        this.keyCodec = codec;
        this.elementCodec = codec2;
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
    public <T> DataResult<Pair<Map<K, V>, T>> decode(DynamicOps<T> dynamicOps, T t) {
        return dynamicOps.getMap(t).setLifecycle(Lifecycle.stable()).flatMap(arg_0 -> this.lambda$decode$0(dynamicOps, arg_0)).map(arg_0 -> UnboundedMapCodec.lambda$decode$1(t, arg_0));
    }

    @Override
    public <T> DataResult<T> encode(Map<K, V> map, DynamicOps<T> dynamicOps, T t) {
        return this.encode(map, dynamicOps, dynamicOps.mapBuilder()).build(t);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        UnboundedMapCodec unboundedMapCodec = (UnboundedMapCodec)object;
        return Objects.equals(this.keyCodec, unboundedMapCodec.keyCodec) && Objects.equals(this.elementCodec, unboundedMapCodec.elementCodec);
    }

    public int hashCode() {
        return Objects.hash(this.keyCodec, this.elementCodec);
    }

    public String toString() {
        return "UnboundedMapCodec[" + this.keyCodec + " -> " + this.elementCodec + ']';
    }

    @Override
    public DataResult encode(Object object, DynamicOps dynamicOps, Object object2) {
        return this.encode((Map)object, dynamicOps, object2);
    }

    private static Pair lambda$decode$1(Object object, Map map) {
        return Pair.of(map, object);
    }

    private DataResult lambda$decode$0(DynamicOps dynamicOps, MapLike mapLike) {
        return this.decode((DynamicOps)dynamicOps, (Object)mapLike);
    }
}

