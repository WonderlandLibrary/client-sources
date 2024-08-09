/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.ListBuilder;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.apache.commons.lang3.mutable.MutableObject;

public interface DynamicOps<T> {
    public T empty();

    default public T emptyMap() {
        return (T)this.createMap(ImmutableMap.of());
    }

    default public T emptyList() {
        return this.createList(Stream.empty());
    }

    public <U> U convertTo(DynamicOps<U> var1, T var2);

    public DataResult<Number> getNumberValue(T var1);

    default public Number getNumberValue(T t, Number number) {
        return this.getNumberValue(t).result().orElse(number);
    }

    public T createNumeric(Number var1);

    default public T createByte(byte by) {
        return this.createNumeric(by);
    }

    default public T createShort(short s) {
        return this.createNumeric(s);
    }

    default public T createInt(int n) {
        return this.createNumeric(n);
    }

    default public T createLong(long l) {
        return this.createNumeric(l);
    }

    default public T createFloat(float f) {
        return this.createNumeric(Float.valueOf(f));
    }

    default public T createDouble(double d) {
        return this.createNumeric(d);
    }

    default public DataResult<Boolean> getBooleanValue(T t) {
        return this.getNumberValue(t).map(DynamicOps::lambda$getBooleanValue$0);
    }

    default public T createBoolean(boolean bl) {
        return this.createByte((byte)(bl ? 1 : 0));
    }

    public DataResult<String> getStringValue(T var1);

    public T createString(String var1);

    public DataResult<T> mergeToList(T var1, T var2);

    default public DataResult<T> mergeToList(T t, List<T> list) {
        DataResult<Object> dataResult = DataResult.success(t);
        for (T t2 : list) {
            dataResult = dataResult.flatMap(arg_0 -> this.lambda$mergeToList$1(t2, arg_0));
        }
        return dataResult;
    }

    public DataResult<T> mergeToMap(T var1, T var2, T var3);

    default public DataResult<T> mergeToMap(T t, Map<T, T> map) {
        return this.mergeToMap(t, MapLike.forMap(map, this));
    }

    default public DataResult<T> mergeToMap(T t, MapLike<T> mapLike) {
        MutableObject<DataResult<T>> mutableObject = new MutableObject<DataResult<T>>(DataResult.success(t));
        mapLike.entries().forEach(arg_0 -> this.lambda$mergeToMap$3(mutableObject, arg_0));
        return mutableObject.getValue();
    }

    default public DataResult<T> mergeToPrimitive(T t, T t2) {
        if (!Objects.equals(t, this.empty())) {
            return DataResult.error("Do not know how to append a primitive value " + t2 + " to " + t, t2);
        }
        return DataResult.success(t2);
    }

    public DataResult<Stream<Pair<T, T>>> getMapValues(T var1);

    default public DataResult<Consumer<BiConsumer<T, T>>> getMapEntries(T t) {
        return this.getMapValues(t).map(DynamicOps::lambda$getMapEntries$6);
    }

    public T createMap(Stream<Pair<T, T>> var1);

    default public DataResult<MapLike<T>> getMap(T t) {
        return this.getMapValues(t).flatMap(this::lambda$getMap$7);
    }

    default public T createMap(Map<T, T> map) {
        return this.createMap(map.entrySet().stream().map(DynamicOps::lambda$createMap$8));
    }

    public DataResult<Stream<T>> getStream(T var1);

    default public DataResult<Consumer<Consumer<T>>> getList(T t) {
        return this.getStream(t).map(DynamicOps::lambda$getList$9);
    }

    public T createList(Stream<T> var1);

    default public DataResult<ByteBuffer> getByteBuffer(T t) {
        return this.getStream(t).flatMap(arg_0 -> this.lambda$getByteBuffer$11(t, arg_0));
    }

    default public T createByteList(ByteBuffer byteBuffer) {
        return (T)this.createList(IntStream.range(0, byteBuffer.capacity()).mapToObj(arg_0 -> this.lambda$createByteList$12(byteBuffer, arg_0)));
    }

    default public DataResult<IntStream> getIntStream(T t) {
        return this.getStream(t).flatMap(arg_0 -> this.lambda$getIntStream$15(t, arg_0));
    }

    default public T createIntList(IntStream intStream) {
        return (T)this.createList(intStream.mapToObj(this::createInt));
    }

    default public DataResult<LongStream> getLongStream(T t) {
        return this.getStream(t).flatMap(arg_0 -> this.lambda$getLongStream$18(t, arg_0));
    }

    default public T createLongList(LongStream longStream) {
        return (T)this.createList(longStream.mapToObj(this::createLong));
    }

    public T remove(T var1, String var2);

    default public boolean compressMaps() {
        return true;
    }

    default public DataResult<T> get(T t, String string) {
        return this.getGeneric(t, this.createString(string));
    }

    default public DataResult<T> getGeneric(T t, T t2) {
        return this.getMap(t).flatMap(arg_0 -> DynamicOps.lambda$getGeneric$20(t2, t, arg_0));
    }

    default public T set(T t, String string, T t2) {
        return this.mergeToMap(t, this.createString(string), t2).result().orElse(t);
    }

    default public T update(T t, String string, Function<T, T> function) {
        return (T)this.get(t, string).map(arg_0 -> this.lambda$update$21(t, string, function, arg_0)).result().orElse(t);
    }

    default public T updateGeneric(T t, T t2, Function<T, T> function) {
        return (T)this.getGeneric(t, t2).flatMap(arg_0 -> this.lambda$updateGeneric$22(t, t2, function, arg_0)).result().orElse(t);
    }

    default public ListBuilder<T> listBuilder() {
        return new ListBuilder.Builder(this);
    }

    default public RecordBuilder<T> mapBuilder() {
        return new RecordBuilder.MapBuilder(this);
    }

    default public <E> Function<E, DataResult<T>> withEncoder(Encoder<E> encoder) {
        return arg_0 -> this.lambda$withEncoder$23(encoder, arg_0);
    }

    default public <E> Function<T, DataResult<Pair<E, T>>> withDecoder(Decoder<E> decoder) {
        return arg_0 -> this.lambda$withDecoder$24(decoder, arg_0);
    }

    default public <E> Function<T, DataResult<E>> withParser(Decoder<E> decoder) {
        return arg_0 -> this.lambda$withParser$25(decoder, arg_0);
    }

    default public <U> U convertList(DynamicOps<U> dynamicOps, T t) {
        return (U)dynamicOps.createList(this.getStream(t).result().orElse(Stream.empty()).map(arg_0 -> this.lambda$convertList$26(dynamicOps, arg_0)));
    }

    default public <U> U convertMap(DynamicOps<U> dynamicOps, T t) {
        return dynamicOps.createMap(this.getMapValues(t).result().orElse(Stream.empty()).map(arg_0 -> this.lambda$convertMap$27(dynamicOps, arg_0)));
    }

    private Pair lambda$convertMap$27(DynamicOps dynamicOps, Pair pair) {
        return Pair.of(this.convertTo(dynamicOps, pair.getFirst()), this.convertTo(dynamicOps, pair.getSecond()));
    }

    private Object lambda$convertList$26(DynamicOps dynamicOps, Object object) {
        return this.convertTo(dynamicOps, object);
    }

    private DataResult lambda$withParser$25(Decoder decoder, Object object) {
        return decoder.parse(this, object);
    }

    private DataResult lambda$withDecoder$24(Decoder decoder, Object object) {
        return decoder.decode(this, object);
    }

    private DataResult lambda$withEncoder$23(Encoder encoder, Object object) {
        return encoder.encodeStart(this, object);
    }

    private DataResult lambda$updateGeneric$22(Object object, Object object2, Function function, Object object3) {
        return this.mergeToMap(object, object2, function.apply(object3));
    }

    private Object lambda$update$21(Object object, String string, Function function, Object object2) {
        return this.set(object, string, function.apply(object2));
    }

    private static DataResult lambda$getGeneric$20(Object object, Object object2, MapLike mapLike) {
        return Optional.ofNullable(mapLike.get(object)).map(DataResult::success).orElseGet(() -> DynamicOps.lambda$null$19(object, object2));
    }

    private static DataResult lambda$null$19(Object object, Object object2) {
        return DataResult.error("No element " + object + " in the map " + object2);
    }

    private DataResult lambda$getLongStream$18(Object object, Stream stream) {
        List list = stream.collect(Collectors.toList());
        if (list.stream().allMatch(this::lambda$null$16)) {
            return DataResult.success(list.stream().mapToLong(this::lambda$null$17));
        }
        return DataResult.error("Some elements are not longs: " + object);
    }

    private long lambda$null$17(Object object) {
        return this.getNumberValue(object).result().get().longValue();
    }

    private boolean lambda$null$16(Object object) {
        return this.getNumberValue(object).result().isPresent();
    }

    private DataResult lambda$getIntStream$15(Object object, Stream stream) {
        List list = stream.collect(Collectors.toList());
        if (list.stream().allMatch(this::lambda$null$13)) {
            return DataResult.success(list.stream().mapToInt(this::lambda$null$14));
        }
        return DataResult.error("Some elements are not ints: " + object);
    }

    private int lambda$null$14(Object object) {
        return this.getNumberValue(object).result().get().intValue();
    }

    private boolean lambda$null$13(Object object) {
        return this.getNumberValue(object).result().isPresent();
    }

    private Object lambda$createByteList$12(ByteBuffer byteBuffer, int n) {
        return this.createByte(byteBuffer.get(n));
    }

    private DataResult lambda$getByteBuffer$11(Object object, Stream stream) {
        List list = stream.collect(Collectors.toList());
        if (list.stream().allMatch(this::lambda$null$10)) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[list.size()]);
            for (int i = 0; i < list.size(); ++i) {
                byteBuffer.put(i, this.getNumberValue(list.get(i)).result().get().byteValue());
            }
            return DataResult.success(byteBuffer);
        }
        return DataResult.error("Some elements are not bytes: " + object);
    }

    private boolean lambda$null$10(Object object) {
        return this.getNumberValue(object).result().isPresent();
    }

    private static Consumer lambda$getList$9(Stream stream) {
        return stream::forEach;
    }

    private static Pair lambda$createMap$8(Map.Entry entry) {
        return Pair.of(entry.getKey(), entry.getValue());
    }

    private DataResult lambda$getMap$7(Stream stream) {
        try {
            return DataResult.success(MapLike.forMap(stream.collect(Pair.toMap()), this));
        } catch (IllegalStateException illegalStateException) {
            return DataResult.error("Error while building map: " + illegalStateException.getMessage());
        }
    }

    private static Consumer lambda$getMapEntries$6(Stream stream) {
        return arg_0 -> DynamicOps.lambda$null$5(stream, arg_0);
    }

    private static void lambda$null$5(Stream stream, BiConsumer biConsumer) {
        stream.forEach(arg_0 -> DynamicOps.lambda$null$4(biConsumer, arg_0));
    }

    private static void lambda$null$4(BiConsumer biConsumer, Pair pair) {
        biConsumer.accept(pair.getFirst(), pair.getSecond());
    }

    private void lambda$mergeToMap$3(MutableObject mutableObject, Pair pair) {
        mutableObject.setValue(((DataResult)mutableObject.getValue()).flatMap(arg_0 -> this.lambda$null$2(pair, arg_0)));
    }

    private DataResult lambda$null$2(Pair pair, Object object) {
        return this.mergeToMap(object, pair.getFirst(), pair.getSecond());
    }

    private DataResult lambda$mergeToList$1(Object object, Object object2) {
        return this.mergeToList(object2, object);
    }

    private static Boolean lambda$getBooleanValue$0(Number number) {
        return number.byteValue() != 0;
    }
}

