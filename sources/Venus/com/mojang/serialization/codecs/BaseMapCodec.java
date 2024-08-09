/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization.codecs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.util.Map;

public interface BaseMapCodec<K, V> {
    public Codec<K> keyCodec();

    public Codec<V> elementCodec();

    default public <T> DataResult<Map<K, V>> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        ImmutableList.Builder builder2 = ImmutableList.builder();
        DataResult dataResult = mapLike.entries().reduce(DataResult.success(Unit.INSTANCE, Lifecycle.stable()), (arg_0, arg_1) -> this.lambda$decode$2(dynamicOps, builder2, builder, arg_0, arg_1), BaseMapCodec::lambda$decode$4);
        ImmutableMap immutableMap = builder.build();
        T t = dynamicOps.createMap(builder2.build().stream());
        return dataResult.map(arg_0 -> BaseMapCodec.lambda$decode$5(immutableMap, arg_0)).setPartial(immutableMap).mapError(arg_0 -> BaseMapCodec.lambda$decode$6(t, arg_0));
    }

    default public <T> RecordBuilder<T> encode(Map<K, V> map, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            recordBuilder.add(this.keyCodec().encodeStart(dynamicOps, entry.getKey()), this.elementCodec().encodeStart(dynamicOps, entry.getValue()));
        }
        return recordBuilder;
    }

    private static String lambda$decode$6(Object object, String string) {
        return string + " missed input: " + object;
    }

    private static Map lambda$decode$5(Map map, Unit unit) {
        return map;
    }

    private static DataResult lambda$decode$4(DataResult dataResult, DataResult dataResult2) {
        return dataResult.apply2stable(BaseMapCodec::lambda$null$3, dataResult2);
    }

    private static Unit lambda$null$3(Unit unit, Unit unit2) {
        return unit;
    }

    private DataResult lambda$decode$2(DynamicOps dynamicOps, ImmutableList.Builder builder, ImmutableMap.Builder builder2, DataResult dataResult, Pair pair) {
        DataResult dataResult2 = this.keyCodec().parse(dynamicOps, pair.getFirst());
        DataResult dataResult3 = this.elementCodec().parse(dynamicOps, pair.getSecond());
        DataResult<Pair> dataResult4 = dataResult2.apply2stable(Pair::of, dataResult3);
        dataResult4.error().ifPresent(arg_0 -> BaseMapCodec.lambda$null$0(builder, pair, arg_0));
        return dataResult.apply2stable((arg_0, arg_1) -> BaseMapCodec.lambda$null$1(builder2, arg_0, arg_1), dataResult4);
    }

    private static Unit lambda$null$1(ImmutableMap.Builder builder, Unit unit, Pair pair) {
        builder.put(pair.getFirst(), pair.getSecond());
        return unit;
    }

    private static void lambda$null$0(ImmutableList.Builder builder, Pair pair, DataResult.PartialResult partialResult) {
        builder.add(pair);
    }
}

