/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization.codecs;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.RecordBuilder;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.apache.commons.lang3.mutable.MutableObject;

public final class CompoundListCodec<K, V>
implements Codec<List<Pair<K, V>>> {
    private final Codec<K> keyCodec;
    private final Codec<V> elementCodec;

    public CompoundListCodec(Codec<K> codec, Codec<V> codec2) {
        this.keyCodec = codec;
        this.elementCodec = codec2;
    }

    @Override
    public <T> DataResult<Pair<List<Pair<K, V>>, T>> decode(DynamicOps<T> dynamicOps, T t) {
        return dynamicOps.getMapEntries(t).flatMap(arg_0 -> this.lambda$decode$4(dynamicOps, arg_0));
    }

    @Override
    public <T> DataResult<T> encode(List<Pair<K, V>> list, DynamicOps<T> dynamicOps, T t) {
        RecordBuilder<T> recordBuilder = dynamicOps.mapBuilder();
        for (Pair<K, V> pair : list) {
            recordBuilder.add(this.keyCodec.encodeStart(dynamicOps, pair.getFirst()), this.elementCodec.encodeStart(dynamicOps, pair.getSecond()));
        }
        return recordBuilder.build(t);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        CompoundListCodec compoundListCodec = (CompoundListCodec)object;
        return Objects.equals(this.keyCodec, compoundListCodec.keyCodec) && Objects.equals(this.elementCodec, compoundListCodec.elementCodec);
    }

    public int hashCode() {
        return Objects.hash(this.keyCodec, this.elementCodec);
    }

    public String toString() {
        return "CompoundListCodec[" + this.keyCodec + " -> " + this.elementCodec + ']';
    }

    @Override
    public DataResult encode(Object object, DynamicOps dynamicOps, Object object2) {
        return this.encode((List)object, dynamicOps, object2);
    }

    private DataResult lambda$decode$4(DynamicOps dynamicOps, Consumer consumer) {
        ImmutableList.Builder builder = ImmutableList.builder();
        ImmutableMap.Builder builder2 = ImmutableMap.builder();
        MutableObject<DataResult<Unit>> mutableObject = new MutableObject<DataResult<Unit>>(DataResult.success(Unit.INSTANCE, Lifecycle.experimental()));
        consumer.accept((arg_0, arg_1) -> this.lambda$null$2(dynamicOps, builder2, mutableObject, builder, arg_0, arg_1));
        ImmutableCollection immutableCollection = builder.build();
        Object k = dynamicOps.createMap(builder2.build());
        Pair pair = Pair.of(immutableCollection, k);
        return mutableObject.getValue().map(arg_0 -> CompoundListCodec.lambda$null$3(pair, arg_0)).setPartial(pair);
    }

    private static Pair lambda$null$3(Pair pair, Unit unit) {
        return pair;
    }

    private void lambda$null$2(DynamicOps dynamicOps, ImmutableMap.Builder builder, MutableObject mutableObject, ImmutableList.Builder builder2, Object object, Object object2) {
        DataResult dataResult = this.keyCodec.parse(dynamicOps, object);
        DataResult dataResult2 = this.elementCodec.parse(dynamicOps, object2);
        DataResult<Pair> dataResult3 = dataResult.apply2stable(Pair::new, dataResult2);
        dataResult3.error().ifPresent(arg_0 -> CompoundListCodec.lambda$null$0(builder, object, object2, arg_0));
        mutableObject.setValue(((DataResult)mutableObject.getValue()).apply2stable((arg_0, arg_1) -> CompoundListCodec.lambda$null$1(builder2, arg_0, arg_1), dataResult3));
    }

    private static Unit lambda$null$1(ImmutableList.Builder builder, Unit unit, Pair pair) {
        builder.add(pair);
        return unit;
    }

    private static void lambda$null$0(ImmutableMap.Builder builder, Object object, Object object2, DataResult.PartialResult partialResult) {
        builder.put(object, object2);
    }
}

