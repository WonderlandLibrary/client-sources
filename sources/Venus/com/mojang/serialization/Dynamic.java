/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicLike;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.OptionalDynamic;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class Dynamic<T>
extends DynamicLike<T> {
    private final T value;

    public Dynamic(DynamicOps<T> dynamicOps) {
        this(dynamicOps, dynamicOps.empty());
    }

    public Dynamic(DynamicOps<T> dynamicOps, @Nullable T t) {
        super(dynamicOps);
        this.value = t == null ? dynamicOps.empty() : t;
    }

    public T getValue() {
        return this.value;
    }

    public Dynamic<T> map(Function<? super T, ? extends T> function) {
        return new Dynamic<T>(this.ops, function.apply(this.value));
    }

    public <U> Dynamic<U> castTyped(DynamicOps<U> dynamicOps) {
        if (!Objects.equals(this.ops, dynamicOps)) {
            throw new IllegalStateException("Dynamic type doesn't match");
        }
        return this;
    }

    public <U> U cast(DynamicOps<U> dynamicOps) {
        return this.castTyped(dynamicOps).getValue();
    }

    public OptionalDynamic<T> merge(Dynamic<?> dynamic) {
        DataResult<Dynamic> dataResult = this.ops.mergeToList(this.value, dynamic.cast(this.ops));
        return new OptionalDynamic(this.ops, dataResult.map(this::lambda$merge$0));
    }

    public OptionalDynamic<T> merge(Dynamic<?> dynamic, Dynamic<?> dynamic2) {
        DataResult<Dynamic> dataResult = this.ops.mergeToMap(this.value, dynamic.cast(this.ops), dynamic2.cast(this.ops));
        return new OptionalDynamic(this.ops, dataResult.map(this::lambda$merge$1));
    }

    public DataResult<Map<Dynamic<T>, Dynamic<T>>> getMapValues() {
        return this.ops.getMapValues(this.value).map(this::lambda$getMapValues$3);
    }

    public Dynamic<T> updateMapValues(Function<Pair<Dynamic<?>, Dynamic<?>>, Pair<Dynamic<?>, Dynamic<?>>> function) {
        return DataFixUtils.orElse(this.getMapValues().map(arg_0 -> this.lambda$updateMapValues$5(function, arg_0)).map(this::createMap).result(), this);
    }

    @Override
    public DataResult<Number> asNumber() {
        return this.ops.getNumberValue(this.value);
    }

    @Override
    public DataResult<String> asString() {
        return this.ops.getStringValue(this.value);
    }

    @Override
    public DataResult<Stream<Dynamic<T>>> asStreamOpt() {
        return this.ops.getStream(this.value).map(this::lambda$asStreamOpt$7);
    }

    @Override
    public DataResult<Stream<Pair<Dynamic<T>, Dynamic<T>>>> asMapOpt() {
        return this.ops.getMapValues(this.value).map(this::lambda$asMapOpt$9);
    }

    @Override
    public DataResult<ByteBuffer> asByteBufferOpt() {
        return this.ops.getByteBuffer(this.value);
    }

    @Override
    public DataResult<IntStream> asIntStreamOpt() {
        return this.ops.getIntStream(this.value);
    }

    @Override
    public DataResult<LongStream> asLongStreamOpt() {
        return this.ops.getLongStream(this.value);
    }

    @Override
    public OptionalDynamic<T> get(String string) {
        return new OptionalDynamic(this.ops, this.ops.getMap(this.value).flatMap(arg_0 -> this.lambda$get$10(string, arg_0)));
    }

    @Override
    public DataResult<T> getGeneric(T t) {
        return this.ops.getGeneric(this.value, t);
    }

    public Dynamic<T> remove(String string) {
        return this.map(arg_0 -> this.lambda$remove$11(string, arg_0));
    }

    public Dynamic<T> set(String string, Dynamic<?> dynamic) {
        return this.map(arg_0 -> this.lambda$set$12(string, dynamic, arg_0));
    }

    public Dynamic<T> update(String string, Function<Dynamic<?>, Dynamic<?>> function) {
        return this.map(arg_0 -> this.lambda$update$14(string, function, arg_0));
    }

    public Dynamic<T> updateGeneric(T t, Function<T, T> function) {
        return this.map(arg_0 -> this.lambda$updateGeneric$15(t, function, arg_0));
    }

    @Override
    public DataResult<T> getElement(String string) {
        return this.getElementGeneric(this.ops.createString(string));
    }

    @Override
    public DataResult<T> getElementGeneric(T t) {
        return this.ops.getGeneric(this.value, t);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        Dynamic dynamic = (Dynamic)object;
        return Objects.equals(this.ops, dynamic.ops) && Objects.equals(this.value, dynamic.value);
    }

    public int hashCode() {
        return Objects.hash(this.ops, this.value);
    }

    public String toString() {
        return String.format("%s[%s]", this.ops, this.value);
    }

    public <R> Dynamic<R> convert(DynamicOps<R> dynamicOps) {
        return new Dynamic<R>(dynamicOps, Dynamic.convert(this.ops, dynamicOps, this.value));
    }

    public <V> V into(Function<? super Dynamic<T>, ? extends V> function) {
        return function.apply(this);
    }

    @Override
    public <A> DataResult<Pair<A, T>> decode(Decoder<? extends A> decoder) {
        return decoder.decode(this.ops, this.value).map(Dynamic::lambda$decode$16);
    }

    public static <S, T> T convert(DynamicOps<S> dynamicOps, DynamicOps<T> dynamicOps2, S s) {
        if (Objects.equals(dynamicOps, dynamicOps2)) {
            return (T)s;
        }
        return dynamicOps.convertTo(dynamicOps2, s);
    }

    private static Pair lambda$decode$16(Pair pair) {
        return pair.mapFirst(Function.identity());
    }

    private Object lambda$updateGeneric$15(Object object, Function function, Object object2) {
        return this.ops.updateGeneric(object2, object, function);
    }

    private Object lambda$update$14(String string, Function function, Object object) {
        return this.ops.update(object, string, arg_0 -> this.lambda$null$13(function, arg_0));
    }

    private Object lambda$null$13(Function function, Object object) {
        return ((Dynamic)function.apply(new Dynamic<Object>(this.ops, object))).cast(this.ops);
    }

    private Object lambda$set$12(String string, Dynamic dynamic, Object object) {
        return this.ops.set(object, string, dynamic.cast(this.ops));
    }

    private Object lambda$remove$11(String string, Object object) {
        return this.ops.remove(object, string);
    }

    private DataResult lambda$get$10(String string, MapLike mapLike) {
        Object t = mapLike.get(string);
        if (t == null) {
            return DataResult.error("key missing: " + string + " in " + this.value);
        }
        return DataResult.success(new Dynamic(this.ops, t));
    }

    private Stream lambda$asMapOpt$9(Stream stream) {
        return stream.map(this::lambda$null$8);
    }

    private Pair lambda$null$8(Pair pair) {
        return Pair.of(new Dynamic(this.ops, pair.getFirst()), new Dynamic(this.ops, pair.getSecond()));
    }

    private Stream lambda$asStreamOpt$7(Stream stream) {
        return stream.map(this::lambda$null$6);
    }

    private Dynamic lambda$null$6(Object object) {
        return new Dynamic<Object>(this.ops, object);
    }

    private Map lambda$updateMapValues$5(Function function, Map map) {
        return map.entrySet().stream().map(arg_0 -> this.lambda$null$4(function, arg_0)).collect(Pair.toMap());
    }

    private Pair lambda$null$4(Function function, Map.Entry entry) {
        Pair pair = (Pair)function.apply(Pair.of(entry.getKey(), entry.getValue()));
        return Pair.of(((Dynamic)pair.getFirst()).castTyped(this.ops), ((Dynamic)pair.getSecond()).castTyped(this.ops));
    }

    private Map lambda$getMapValues$3(Stream stream) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        stream.forEach(arg_0 -> this.lambda$null$2(builder, arg_0));
        return builder.build();
    }

    private void lambda$null$2(ImmutableMap.Builder builder, Pair pair) {
        builder.put(new Dynamic(this.ops, pair.getFirst()), new Dynamic(this.ops, pair.getSecond()));
    }

    private Dynamic lambda$merge$1(Object object) {
        return new Dynamic<Object>(this.ops, object);
    }

    private Dynamic lambda$merge$0(Object object) {
        return new Dynamic<Object>(this.ops, object);
    }
}

