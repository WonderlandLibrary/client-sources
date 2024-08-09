/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.kinds.ListBox;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.OptionalDynamic;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.apache.commons.lang3.mutable.MutableObject;

public abstract class DynamicLike<T> {
    protected final DynamicOps<T> ops;

    public DynamicLike(DynamicOps<T> dynamicOps) {
        this.ops = dynamicOps;
    }

    public DynamicOps<T> getOps() {
        return this.ops;
    }

    public abstract DataResult<Number> asNumber();

    public abstract DataResult<String> asString();

    public abstract DataResult<Stream<Dynamic<T>>> asStreamOpt();

    public abstract DataResult<Stream<Pair<Dynamic<T>, Dynamic<T>>>> asMapOpt();

    public abstract DataResult<ByteBuffer> asByteBufferOpt();

    public abstract DataResult<IntStream> asIntStreamOpt();

    public abstract DataResult<LongStream> asLongStreamOpt();

    public abstract OptionalDynamic<T> get(String var1);

    public abstract DataResult<T> getGeneric(T var1);

    public abstract DataResult<T> getElement(String var1);

    public abstract DataResult<T> getElementGeneric(T var1);

    public abstract <A> DataResult<Pair<A, T>> decode(Decoder<? extends A> var1);

    public <U> DataResult<List<U>> asListOpt(Function<Dynamic<T>, U> function) {
        return this.asStreamOpt().map(arg_0 -> DynamicLike.lambda$asListOpt$0(function, arg_0));
    }

    public <K, V> DataResult<Map<K, V>> asMapOpt(Function<Dynamic<T>, K> function, Function<Dynamic<T>, V> function2) {
        return this.asMapOpt().map(arg_0 -> DynamicLike.lambda$asMapOpt$2(function, function2, arg_0));
    }

    public <A> DataResult<A> read(Decoder<? extends A> decoder) {
        return this.decode(decoder).map(Pair::getFirst);
    }

    public <E> DataResult<List<E>> readList(Decoder<E> decoder) {
        return this.asStreamOpt().map(arg_0 -> DynamicLike.lambda$readList$4(decoder, arg_0)).flatMap(DynamicLike::lambda$readList$5);
    }

    public <E> DataResult<List<E>> readList(Function<? super Dynamic<?>, ? extends DataResult<? extends E>> function) {
        return this.asStreamOpt().map(arg_0 -> DynamicLike.lambda$readList$8(function, arg_0)).flatMap(DynamicLike::lambda$readList$9);
    }

    public <K, V> DataResult<List<Pair<K, V>>> readMap(Decoder<K> decoder, Decoder<V> decoder2) {
        return this.asMapOpt().map(arg_0 -> DynamicLike.lambda$readMap$13(decoder, decoder2, arg_0)).flatMap(DynamicLike::lambda$readMap$14);
    }

    public <K, V> DataResult<List<Pair<K, V>>> readMap(Decoder<K> decoder, Function<K, Decoder<V>> function) {
        return this.asMapOpt().map(arg_0 -> DynamicLike.lambda$readMap$18(decoder, function, arg_0)).flatMap(DynamicLike::lambda$readMap$19);
    }

    public <R> DataResult<R> readMap(DataResult<R> dataResult, Function3<R, Dynamic<T>, Dynamic<T>, DataResult<R>> function3) {
        return this.asMapOpt().flatMap(arg_0 -> DynamicLike.lambda$readMap$22(dataResult, function3, arg_0));
    }

    public Number asNumber(Number number) {
        return this.asNumber().result().orElse(number);
    }

    public int asInt(int n) {
        return this.asNumber(n).intValue();
    }

    public long asLong(long l) {
        return this.asNumber(l).longValue();
    }

    public float asFloat(float f) {
        return this.asNumber(Float.valueOf(f)).floatValue();
    }

    public double asDouble(double d) {
        return this.asNumber(d).doubleValue();
    }

    public byte asByte(byte by) {
        return this.asNumber(by).byteValue();
    }

    public short asShort(short s) {
        return this.asNumber(s).shortValue();
    }

    public boolean asBoolean(boolean bl) {
        return this.asNumber(bl ? 1 : 0).intValue() != 0;
    }

    public String asString(String string) {
        return this.asString().result().orElse(string);
    }

    public Stream<Dynamic<T>> asStream() {
        return this.asStreamOpt().result().orElseGet(Stream::empty);
    }

    public ByteBuffer asByteBuffer() {
        return this.asByteBufferOpt().result().orElseGet(DynamicLike::lambda$asByteBuffer$23);
    }

    public IntStream asIntStream() {
        return this.asIntStreamOpt().result().orElseGet(IntStream::empty);
    }

    public LongStream asLongStream() {
        return this.asLongStreamOpt().result().orElseGet(LongStream::empty);
    }

    public <U> List<U> asList(Function<Dynamic<T>, U> function) {
        return this.asListOpt(function).result().orElseGet(ImmutableList::of);
    }

    public <K, V> Map<K, V> asMap(Function<Dynamic<T>, K> function, Function<Dynamic<T>, V> function2) {
        return this.asMapOpt(function, function2).result().orElseGet(ImmutableMap::of);
    }

    public T getElement(String string, T t) {
        return this.getElement(string).result().orElse(t);
    }

    public T getElementGeneric(T t, T t2) {
        return this.getElementGeneric(t).result().orElse(t2);
    }

    public Dynamic<T> emptyList() {
        return new Dynamic<T>(this.ops, this.ops.emptyList());
    }

    public Dynamic<T> emptyMap() {
        return new Dynamic<T>(this.ops, this.ops.emptyMap());
    }

    public Dynamic<T> createNumeric(Number number) {
        return new Dynamic<T>(this.ops, this.ops.createNumeric(number));
    }

    public Dynamic<T> createByte(byte by) {
        return new Dynamic<T>(this.ops, this.ops.createByte(by));
    }

    public Dynamic<T> createShort(short s) {
        return new Dynamic<T>(this.ops, this.ops.createShort(s));
    }

    public Dynamic<T> createInt(int n) {
        return new Dynamic<T>(this.ops, this.ops.createInt(n));
    }

    public Dynamic<T> createLong(long l) {
        return new Dynamic<T>(this.ops, this.ops.createLong(l));
    }

    public Dynamic<T> createFloat(float f) {
        return new Dynamic<T>(this.ops, this.ops.createFloat(f));
    }

    public Dynamic<T> createDouble(double d) {
        return new Dynamic<T>(this.ops, this.ops.createDouble(d));
    }

    public Dynamic<T> createBoolean(boolean bl) {
        return new Dynamic<T>(this.ops, this.ops.createBoolean(bl));
    }

    public Dynamic<T> createString(String string) {
        return new Dynamic<T>(this.ops, this.ops.createString(string));
    }

    public Dynamic<T> createList(Stream<? extends Dynamic<?>> stream) {
        return new Dynamic<Object>(this.ops, this.ops.createList(stream.map(this::lambda$createList$24)));
    }

    public Dynamic<T> createMap(Map<? extends Dynamic<?>, ? extends Dynamic<?>> map) {
        ImmutableMap.Builder<T, T> builder = ImmutableMap.builder();
        for (Map.Entry<Dynamic<?>, Dynamic<?>> entry : map.entrySet()) {
            builder.put(entry.getKey().cast(this.ops), entry.getValue().cast(this.ops));
        }
        return new Dynamic<T>(this.ops, this.ops.createMap(builder.build()));
    }

    public Dynamic<?> createByteList(ByteBuffer byteBuffer) {
        return new Dynamic<T>(this.ops, this.ops.createByteList(byteBuffer));
    }

    public Dynamic<?> createIntList(IntStream intStream) {
        return new Dynamic<T>(this.ops, this.ops.createIntList(intStream));
    }

    public Dynamic<?> createLongList(LongStream longStream) {
        return new Dynamic<T>(this.ops, this.ops.createLongList(longStream));
    }

    private Object lambda$createList$24(Dynamic dynamic) {
        return dynamic.cast(this.ops);
    }

    private static ByteBuffer lambda$asByteBuffer$23() {
        return ByteBuffer.wrap(new byte[0]);
    }

    private static DataResult lambda$readMap$22(DataResult dataResult, Function3 function3, Stream stream) {
        MutableObject<DataResult> mutableObject = new MutableObject<DataResult>(dataResult);
        stream.forEach(arg_0 -> DynamicLike.lambda$null$21(mutableObject, function3, arg_0));
        return mutableObject.getValue();
    }

    private static void lambda$null$21(MutableObject mutableObject, Function3 function3, Pair pair) {
        mutableObject.setValue(((DataResult)mutableObject.getValue()).flatMap(arg_0 -> DynamicLike.lambda$null$20(function3, pair, arg_0)));
    }

    private static DataResult lambda$null$20(Function3 function3, Pair pair, Object object) {
        return (DataResult)function3.apply(object, pair.getFirst(), pair.getSecond());
    }

    private static DataResult lambda$readMap$19(List list) {
        return DataResult.unbox(ListBox.flip(DataResult.instance(), list));
    }

    private static List lambda$readMap$18(Decoder decoder, Function function, Stream stream) {
        return stream.map(arg_0 -> DynamicLike.lambda$null$17(decoder, function, arg_0)).collect(Collectors.toList());
    }

    private static DataResult lambda$null$17(Decoder decoder, Function function, Pair pair) {
        return ((Dynamic)pair.getFirst()).read(decoder).flatMap(arg_0 -> DynamicLike.lambda$null$16(pair, function, arg_0));
    }

    private static DataResult lambda$null$16(Pair pair, Function function, Object object) {
        return ((Dynamic)pair.getSecond()).read((Decoder)function.apply(object)).map(arg_0 -> DynamicLike.lambda$null$15(object, arg_0));
    }

    private static Pair lambda$null$15(Object object, Object object2) {
        return Pair.of(object, object2);
    }

    private static DataResult lambda$readMap$14(List list) {
        return DataResult.unbox(ListBox.flip(DataResult.instance(), list));
    }

    private static List lambda$readMap$13(Decoder decoder, Decoder decoder2, Stream stream) {
        return stream.map(arg_0 -> DynamicLike.lambda$null$12(decoder, decoder2, arg_0)).collect(Collectors.toList());
    }

    private static DataResult lambda$null$12(Decoder decoder, Decoder decoder2, Pair pair) {
        return ((Dynamic)pair.getFirst()).read(decoder).flatMap(arg_0 -> DynamicLike.lambda$null$11(pair, decoder2, arg_0));
    }

    private static DataResult lambda$null$11(Pair pair, Decoder decoder, Object object) {
        return ((Dynamic)pair.getSecond()).read(decoder).map(arg_0 -> DynamicLike.lambda$null$10(object, arg_0));
    }

    private static Pair lambda$null$10(Object object, Object object2) {
        return Pair.of(object, object2);
    }

    private static DataResult lambda$readList$9(List list) {
        return DataResult.unbox(ListBox.flip(DataResult.instance(), list));
    }

    private static List lambda$readList$8(Function function, Stream stream) {
        return stream.map(function).map(DynamicLike::lambda$null$7).collect(Collectors.toList());
    }

    private static DataResult lambda$null$7(DataResult dataResult) {
        return dataResult.map(DynamicLike::lambda$null$6);
    }

    private static Object lambda$null$6(Object object) {
        return object;
    }

    private static DataResult lambda$readList$5(List list) {
        return DataResult.unbox(ListBox.flip(DataResult.instance(), list));
    }

    private static List lambda$readList$4(Decoder decoder, Stream stream) {
        return stream.map(arg_0 -> DynamicLike.lambda$null$3(decoder, arg_0)).collect(Collectors.toList());
    }

    private static DataResult lambda$null$3(Decoder decoder, Dynamic dynamic) {
        return dynamic.read(decoder);
    }

    private static Map lambda$asMapOpt$2(Function function, Function function2, Stream stream) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        stream.forEach(arg_0 -> DynamicLike.lambda$null$1(builder, function, function2, arg_0));
        return builder.build();
    }

    private static void lambda$null$1(ImmutableMap.Builder builder, Function function, Function function2, Pair pair) {
        builder.put(function.apply(pair.getFirst()), function2.apply(pair.getSecond()));
    }

    private static List lambda$asListOpt$0(Function function, Stream stream) {
        return stream.map(function).collect(Collectors.toList());
    }
}

