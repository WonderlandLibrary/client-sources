/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization.codecs;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.util.function.Function;
import java.util.stream.Stream;

public class KeyDispatchCodec<K, V>
extends MapCodec<V> {
    private final String typeKey;
    private final Codec<K> keyCodec;
    private final String valueKey = "value";
    private final Function<? super V, ? extends DataResult<? extends K>> type;
    private final Function<? super K, ? extends DataResult<? extends Decoder<? extends V>>> decoder;
    private final Function<? super V, ? extends DataResult<? extends Encoder<V>>> encoder;
    private final boolean assumeMap;

    public static <K, V> KeyDispatchCodec<K, V> unsafe(String string, Codec<K> codec, Function<? super V, ? extends DataResult<? extends K>> function, Function<? super K, ? extends DataResult<? extends Decoder<? extends V>>> function2, Function<? super V, ? extends DataResult<? extends Encoder<V>>> function3) {
        return new KeyDispatchCodec<K, V>(string, codec, function, function2, function3, true);
    }

    protected KeyDispatchCodec(String string, Codec<K> codec, Function<? super V, ? extends DataResult<? extends K>> function, Function<? super K, ? extends DataResult<? extends Decoder<? extends V>>> function2, Function<? super V, ? extends DataResult<? extends Encoder<V>>> function3, boolean bl) {
        this.typeKey = string;
        this.keyCodec = codec;
        this.type = function;
        this.decoder = function2;
        this.encoder = function3;
        this.assumeMap = bl;
    }

    public KeyDispatchCodec(String string, Codec<K> codec, Function<? super V, ? extends DataResult<? extends K>> function, Function<? super K, ? extends DataResult<? extends Codec<? extends V>>> function2) {
        this(string, codec, function, function2, arg_0 -> KeyDispatchCodec.lambda$new$0(function, function2, arg_0), false);
    }

    @Override
    public <T> DataResult<V> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
        T t = mapLike.get(this.typeKey);
        if (t == null) {
            return DataResult.error("Input does not contain a key [" + this.typeKey + "]: " + mapLike);
        }
        return this.keyCodec.decode(dynamicOps, t).flatMap(arg_0 -> this.lambda$decode$2(dynamicOps, mapLike, arg_0));
    }

    @Override
    public <T> RecordBuilder<T> encode(V v, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
        DataResult<Encoder<V>> dataResult = this.encoder.apply(v);
        RecordBuilder<T> recordBuilder2 = recordBuilder.withErrorsFrom(dataResult);
        if (!dataResult.result().isPresent()) {
            return recordBuilder2;
        }
        Encoder<V> encoder = dataResult.result().get();
        if (dynamicOps.compressMaps()) {
            return recordBuilder.add(this.typeKey, this.type.apply(v).flatMap(arg_0 -> this.lambda$encode$3(dynamicOps, arg_0))).add("value", encoder.encodeStart(dynamicOps, v));
        }
        if (encoder instanceof MapCodec.MapCodecCodec) {
            return ((MapCodec.MapCodecCodec)encoder).codec().encode(v, dynamicOps, recordBuilder).add(this.typeKey, this.type.apply(v).flatMap(arg_0 -> this.lambda$encode$4(dynamicOps, arg_0)));
        }
        T t = dynamicOps.createString(this.typeKey);
        DataResult<T> dataResult2 = encoder.encodeStart(dynamicOps, v);
        if (this.assumeMap) {
            DataResult dataResult3 = dataResult2.flatMap(dynamicOps::getMap);
            return dataResult3.map(arg_0 -> this.lambda$encode$7(recordBuilder, t, v, dynamicOps, arg_0)).result().orElseGet(() -> KeyDispatchCodec.lambda$encode$8(recordBuilder, dataResult3));
        }
        recordBuilder.add(t, this.type.apply(v).flatMap(arg_0 -> this.lambda$encode$9(dynamicOps, arg_0)));
        recordBuilder.add("value", dataResult2);
        return recordBuilder;
    }

    @Override
    public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
        return Stream.of(this.typeKey, "value").map(dynamicOps::createString);
    }

    private static <K, V> DataResult<? extends Encoder<V>> getCodec(Function<? super V, ? extends DataResult<? extends K>> function, Function<? super K, ? extends DataResult<? extends Encoder<? extends V>>> function2, V v) {
        return function.apply(v).flatMap(arg_0 -> KeyDispatchCodec.lambda$getCodec$10(function2, arg_0)).map(KeyDispatchCodec::lambda$getCodec$11);
    }

    public String toString() {
        return "KeyDispatchCodec[" + this.keyCodec.toString() + " " + this.type + " " + this.decoder + "]";
    }

    private static Encoder lambda$getCodec$11(Encoder encoder) {
        return encoder;
    }

    private static DataResult lambda$getCodec$10(Function function, Object object) {
        return ((DataResult)function.apply(object)).map(Function.identity());
    }

    private DataResult lambda$encode$9(DynamicOps dynamicOps, Object object) {
        return this.keyCodec.encodeStart(dynamicOps, object);
    }

    private static RecordBuilder lambda$encode$8(RecordBuilder recordBuilder, DataResult dataResult) {
        return recordBuilder.withErrorsFrom(dataResult);
    }

    private RecordBuilder lambda$encode$7(RecordBuilder recordBuilder, Object object, Object object2, DynamicOps dynamicOps, MapLike mapLike) {
        recordBuilder.add(object, this.type.apply(object2).flatMap(arg_0 -> this.lambda$null$5(dynamicOps, arg_0)));
        mapLike.entries().forEach(arg_0 -> KeyDispatchCodec.lambda$null$6(object, recordBuilder, arg_0));
        return recordBuilder;
    }

    private static void lambda$null$6(Object object, RecordBuilder recordBuilder, Pair pair) {
        if (!pair.getFirst().equals(object)) {
            recordBuilder.add(pair.getFirst(), pair.getSecond());
        }
    }

    private DataResult lambda$null$5(DynamicOps dynamicOps, Object object) {
        return this.keyCodec.encodeStart(dynamicOps, object);
    }

    private DataResult lambda$encode$4(DynamicOps dynamicOps, Object object) {
        return this.keyCodec.encodeStart(dynamicOps, object);
    }

    private DataResult lambda$encode$3(DynamicOps dynamicOps, Object object) {
        return this.keyCodec.encodeStart(dynamicOps, object);
    }

    private DataResult lambda$decode$2(DynamicOps dynamicOps, MapLike mapLike, Pair pair) {
        DataResult<Decoder<V>> dataResult = this.decoder.apply(pair.getFirst());
        return dataResult.flatMap(arg_0 -> this.lambda$null$1(dynamicOps, mapLike, arg_0));
    }

    private DataResult lambda$null$1(DynamicOps dynamicOps, MapLike mapLike, Decoder decoder) {
        if (dynamicOps.compressMaps()) {
            Object t = mapLike.get(dynamicOps.createString("value"));
            if (t == null) {
                return DataResult.error("Input does not have a \"value\" entry: " + mapLike);
            }
            return decoder.parse(dynamicOps, t).map(Function.identity());
        }
        if (decoder instanceof MapCodec.MapCodecCodec) {
            return ((MapCodec.MapCodecCodec)decoder).codec().decode(dynamicOps, mapLike).map(Function.identity());
        }
        if (this.assumeMap) {
            return decoder.decode(dynamicOps, dynamicOps.createMap(mapLike.entries())).map(Pair::getFirst);
        }
        return decoder.decode(dynamicOps, mapLike.get("value")).map(Pair::getFirst);
    }

    private static DataResult lambda$new$0(Function function, Function function2, Object object) {
        return KeyDispatchCodec.getCodec(function, function2, object);
    }
}

