/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.Keyable;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapDecoder;
import com.mojang.serialization.MapEncoder;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.codecs.CompoundListCodec;
import com.mojang.serialization.codecs.EitherCodec;
import com.mojang.serialization.codecs.EitherMapCodec;
import com.mojang.serialization.codecs.KeyDispatchCodec;
import com.mojang.serialization.codecs.ListCodec;
import com.mojang.serialization.codecs.OptionalFieldCodec;
import com.mojang.serialization.codecs.PairCodec;
import com.mojang.serialization.codecs.PairMapCodec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.SimpleMapCodec;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Codec<A>
extends Encoder<A>,
Decoder<A> {
    public static final PrimitiveCodec<Boolean> BOOL = new PrimitiveCodec<Boolean>(){

        @Override
        public <T> DataResult<Boolean> read(DynamicOps<T> dynamicOps, T t) {
            return dynamicOps.getBooleanValue(t);
        }

        @Override
        public <T> T write(DynamicOps<T> dynamicOps, Boolean bl) {
            return dynamicOps.createBoolean(bl);
        }

        public String toString() {
            return "Bool";
        }

        @Override
        public Object write(DynamicOps dynamicOps, Object object) {
            return this.write(dynamicOps, (Boolean)object);
        }
    };
    public static final PrimitiveCodec<Byte> BYTE = new PrimitiveCodec<Byte>(){

        @Override
        public <T> DataResult<Byte> read(DynamicOps<T> dynamicOps, T t) {
            return dynamicOps.getNumberValue(t).map(Number::byteValue);
        }

        @Override
        public <T> T write(DynamicOps<T> dynamicOps, Byte by) {
            return dynamicOps.createByte(by);
        }

        public String toString() {
            return "Byte";
        }

        @Override
        public Object write(DynamicOps dynamicOps, Object object) {
            return this.write(dynamicOps, (Byte)object);
        }
    };
    public static final PrimitiveCodec<Short> SHORT = new PrimitiveCodec<Short>(){

        @Override
        public <T> DataResult<Short> read(DynamicOps<T> dynamicOps, T t) {
            return dynamicOps.getNumberValue(t).map(Number::shortValue);
        }

        @Override
        public <T> T write(DynamicOps<T> dynamicOps, Short s) {
            return dynamicOps.createShort(s);
        }

        public String toString() {
            return "Short";
        }

        @Override
        public Object write(DynamicOps dynamicOps, Object object) {
            return this.write(dynamicOps, (Short)object);
        }
    };
    public static final PrimitiveCodec<Integer> INT = new PrimitiveCodec<Integer>(){

        @Override
        public <T> DataResult<Integer> read(DynamicOps<T> dynamicOps, T t) {
            return dynamicOps.getNumberValue(t).map(Number::intValue);
        }

        @Override
        public <T> T write(DynamicOps<T> dynamicOps, Integer n) {
            return dynamicOps.createInt(n);
        }

        public String toString() {
            return "Int";
        }

        @Override
        public Object write(DynamicOps dynamicOps, Object object) {
            return this.write(dynamicOps, (Integer)object);
        }
    };
    public static final PrimitiveCodec<Long> LONG = new PrimitiveCodec<Long>(){

        @Override
        public <T> DataResult<Long> read(DynamicOps<T> dynamicOps, T t) {
            return dynamicOps.getNumberValue(t).map(Number::longValue);
        }

        @Override
        public <T> T write(DynamicOps<T> dynamicOps, Long l) {
            return dynamicOps.createLong(l);
        }

        public String toString() {
            return "Long";
        }

        @Override
        public Object write(DynamicOps dynamicOps, Object object) {
            return this.write(dynamicOps, (Long)object);
        }
    };
    public static final PrimitiveCodec<Float> FLOAT = new PrimitiveCodec<Float>(){

        @Override
        public <T> DataResult<Float> read(DynamicOps<T> dynamicOps, T t) {
            return dynamicOps.getNumberValue(t).map(Number::floatValue);
        }

        @Override
        public <T> T write(DynamicOps<T> dynamicOps, Float f) {
            return dynamicOps.createFloat(f.floatValue());
        }

        public String toString() {
            return "Float";
        }

        @Override
        public Object write(DynamicOps dynamicOps, Object object) {
            return this.write(dynamicOps, (Float)object);
        }
    };
    public static final PrimitiveCodec<Double> DOUBLE = new PrimitiveCodec<Double>(){

        @Override
        public <T> DataResult<Double> read(DynamicOps<T> dynamicOps, T t) {
            return dynamicOps.getNumberValue(t).map(Number::doubleValue);
        }

        @Override
        public <T> T write(DynamicOps<T> dynamicOps, Double d) {
            return dynamicOps.createDouble(d);
        }

        public String toString() {
            return "Double";
        }

        @Override
        public Object write(DynamicOps dynamicOps, Object object) {
            return this.write(dynamicOps, (Double)object);
        }
    };
    public static final PrimitiveCodec<String> STRING = new PrimitiveCodec<String>(){

        @Override
        public <T> DataResult<String> read(DynamicOps<T> dynamicOps, T t) {
            return dynamicOps.getStringValue(t);
        }

        @Override
        public <T> T write(DynamicOps<T> dynamicOps, String string) {
            return dynamicOps.createString(string);
        }

        public String toString() {
            return "String";
        }

        @Override
        public Object write(DynamicOps dynamicOps, Object object) {
            return this.write(dynamicOps, (String)object);
        }
    };
    public static final PrimitiveCodec<ByteBuffer> BYTE_BUFFER = new PrimitiveCodec<ByteBuffer>(){

        @Override
        public <T> DataResult<ByteBuffer> read(DynamicOps<T> dynamicOps, T t) {
            return dynamicOps.getByteBuffer(t);
        }

        @Override
        public <T> T write(DynamicOps<T> dynamicOps, ByteBuffer byteBuffer) {
            return dynamicOps.createByteList(byteBuffer);
        }

        public String toString() {
            return "ByteBuffer";
        }

        @Override
        public Object write(DynamicOps dynamicOps, Object object) {
            return this.write(dynamicOps, (ByteBuffer)object);
        }
    };
    public static final PrimitiveCodec<IntStream> INT_STREAM = new PrimitiveCodec<IntStream>(){

        @Override
        public <T> DataResult<IntStream> read(DynamicOps<T> dynamicOps, T t) {
            return dynamicOps.getIntStream(t);
        }

        @Override
        public <T> T write(DynamicOps<T> dynamicOps, IntStream intStream) {
            return dynamicOps.createIntList(intStream);
        }

        public String toString() {
            return "IntStream";
        }

        @Override
        public Object write(DynamicOps dynamicOps, Object object) {
            return this.write(dynamicOps, (IntStream)object);
        }
    };
    public static final PrimitiveCodec<LongStream> LONG_STREAM = new PrimitiveCodec<LongStream>(){

        @Override
        public <T> DataResult<LongStream> read(DynamicOps<T> dynamicOps, T t) {
            return dynamicOps.getLongStream(t);
        }

        @Override
        public <T> T write(DynamicOps<T> dynamicOps, LongStream longStream) {
            return dynamicOps.createLongList(longStream);
        }

        public String toString() {
            return "LongStream";
        }

        @Override
        public Object write(DynamicOps dynamicOps, Object object) {
            return this.write(dynamicOps, (LongStream)object);
        }
    };
    public static final Codec<Dynamic<?>> PASSTHROUGH = new Codec<Dynamic<?>>(){

        @Override
        public <T> DataResult<Pair<Dynamic<?>, T>> decode(DynamicOps<T> dynamicOps, T t) {
            return DataResult.success(Pair.of(new Dynamic<T>(dynamicOps, t), dynamicOps.empty()));
        }

        @Override
        public <T> DataResult<T> encode(Dynamic<?> dynamic, DynamicOps<T> dynamicOps, T t) {
            if (dynamic.getValue() == dynamic.getOps().empty()) {
                return DataResult.success(t, Lifecycle.experimental());
            }
            T t2 = dynamic.convert(dynamicOps).getValue();
            if (t == dynamicOps.empty()) {
                return DataResult.success(t2, Lifecycle.experimental());
            }
            DataResult dataResult = dynamicOps.getMap(t2).flatMap(arg_0 -> 20.lambda$encode$0(dynamicOps, t, arg_0));
            return dataResult.result().map(DataResult::success).orElseGet(() -> 20.lambda$encode$3(dynamicOps, t2, t));
        }

        public String toString() {
            return "passthrough";
        }

        @Override
        public DataResult encode(Object object, DynamicOps dynamicOps, Object object2) {
            return this.encode((Dynamic)object, dynamicOps, object2);
        }

        private static DataResult lambda$encode$3(DynamicOps dynamicOps, Object object, Object object2) {
            DataResult dataResult = dynamicOps.getStream(object).flatMap(arg_0 -> 20.lambda$null$1(dynamicOps, object2, arg_0));
            return dataResult.result().map(DataResult::success).orElseGet(() -> 20.lambda$null$2(object2, object));
        }

        private static DataResult lambda$null$2(Object object, Object object2) {
            return DataResult.error("Don't know how to merge " + object + " and " + object2, object, Lifecycle.experimental());
        }

        private static DataResult lambda$null$1(DynamicOps dynamicOps, Object object, Stream stream) {
            return dynamicOps.mergeToList(object, stream.collect(Collectors.toList()));
        }

        private static DataResult lambda$encode$0(DynamicOps dynamicOps, Object object, MapLike mapLike) {
            return dynamicOps.mergeToMap(object, mapLike);
        }
    };
    public static final MapCodec<Unit> EMPTY = MapCodec.of(Encoder.empty(), Decoder.unit(Unit.INSTANCE));

    @Override
    default public Codec<A> withLifecycle(Lifecycle lifecycle) {
        return new Codec<A>(this, lifecycle){
            final Lifecycle val$lifecycle;
            final Codec this$0;
            {
                this.this$0 = codec;
                this.val$lifecycle = lifecycle;
            }

            @Override
            public <T> DataResult<T> encode(A a, DynamicOps<T> dynamicOps, T t) {
                return this.this$0.encode(a, dynamicOps, t).setLifecycle(this.val$lifecycle);
            }

            @Override
            public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> dynamicOps, T t) {
                return this.this$0.decode(dynamicOps, t).setLifecycle(this.val$lifecycle);
            }

            public String toString() {
                return this.this$0.toString();
            }
        };
    }

    default public Codec<A> stable() {
        return this.withLifecycle(Lifecycle.stable());
    }

    default public Codec<A> deprecated(int n) {
        return this.withLifecycle(Lifecycle.deprecated(n));
    }

    public static <A> Codec<A> of(Encoder<A> encoder, Decoder<A> decoder) {
        return Codec.of(encoder, decoder, "Codec[" + encoder + " " + decoder + "]");
    }

    public static <A> Codec<A> of(Encoder<A> encoder, Decoder<A> decoder, String string) {
        return new Codec<A>(decoder, encoder, string){
            final Decoder val$decoder;
            final Encoder val$encoder;
            final String val$name;
            {
                this.val$decoder = decoder;
                this.val$encoder = encoder;
                this.val$name = string;
            }

            @Override
            public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> dynamicOps, T t) {
                return this.val$decoder.decode(dynamicOps, t);
            }

            @Override
            public <T> DataResult<T> encode(A a, DynamicOps<T> dynamicOps, T t) {
                return this.val$encoder.encode(a, dynamicOps, t);
            }

            public String toString() {
                return this.val$name;
            }
        };
    }

    public static <A> MapCodec<A> of(MapEncoder<A> mapEncoder, MapDecoder<A> mapDecoder) {
        return Codec.of(mapEncoder, mapDecoder, () -> Codec.lambda$of$0(mapEncoder, mapDecoder));
    }

    public static <A> MapCodec<A> of(MapEncoder<A> mapEncoder, MapDecoder<A> mapDecoder, Supplier<String> supplier) {
        return new MapCodec<A>(mapEncoder, mapDecoder, supplier){
            final MapEncoder val$encoder;
            final MapDecoder val$decoder;
            final Supplier val$name;
            {
                this.val$encoder = mapEncoder;
                this.val$decoder = mapDecoder;
                this.val$name = supplier;
            }

            @Override
            public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                return Stream.concat(this.val$encoder.keys(dynamicOps), this.val$decoder.keys(dynamicOps));
            }

            @Override
            public <T> DataResult<A> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
                return this.val$decoder.decode(dynamicOps, mapLike);
            }

            @Override
            public <T> RecordBuilder<T> encode(A a, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
                return this.val$encoder.encode(a, dynamicOps, recordBuilder);
            }

            public String toString() {
                return (String)this.val$name.get();
            }
        };
    }

    public static <F, S> Codec<Pair<F, S>> pair(Codec<F> codec, Codec<S> codec2) {
        return new PairCodec<F, S>(codec, codec2);
    }

    public static <F, S> Codec<Either<F, S>> either(Codec<F> codec, Codec<S> codec2) {
        return new EitherCodec<F, S>(codec, codec2);
    }

    public static <F, S> MapCodec<Pair<F, S>> mapPair(MapCodec<F> mapCodec, MapCodec<S> mapCodec2) {
        return new PairMapCodec<F, S>(mapCodec, mapCodec2);
    }

    public static <F, S> MapCodec<Either<F, S>> mapEither(MapCodec<F> mapCodec, MapCodec<S> mapCodec2) {
        return new EitherMapCodec<F, S>(mapCodec, mapCodec2);
    }

    public static <E> Codec<List<E>> list(Codec<E> codec) {
        return new ListCodec<E>(codec);
    }

    public static <K, V> Codec<List<Pair<K, V>>> compoundList(Codec<K> codec, Codec<V> codec2) {
        return new CompoundListCodec<K, V>(codec, codec2);
    }

    public static <K, V> SimpleMapCodec<K, V> simpleMap(Codec<K> codec, Codec<V> codec2, Keyable keyable) {
        return new SimpleMapCodec<K, V>(codec, codec2, keyable);
    }

    public static <K, V> UnboundedMapCodec<K, V> unboundedMap(Codec<K> codec, Codec<V> codec2) {
        return new UnboundedMapCodec<K, V>(codec, codec2);
    }

    public static <F> MapCodec<Optional<F>> optionalField(String string, Codec<F> codec) {
        return new OptionalFieldCodec<F>(string, codec);
    }

    default public Codec<List<A>> listOf() {
        return Codec.list(this);
    }

    default public <S> Codec<S> xmap(Function<? super A, ? extends S> function, Function<? super S, ? extends A> function2) {
        return Codec.of(this.comap(function2), this.map(function), this.toString() + "[xmapped]");
    }

    default public <S> Codec<S> comapFlatMap(Function<? super A, ? extends DataResult<? extends S>> function, Function<? super S, ? extends A> function2) {
        return Codec.of(this.comap(function2), this.flatMap(function), this.toString() + "[comapFlatMapped]");
    }

    default public <S> Codec<S> flatComapMap(Function<? super A, ? extends S> function, Function<? super S, ? extends DataResult<? extends A>> function2) {
        return Codec.of(this.flatComap(function2), this.map(function), this.toString() + "[flatComapMapped]");
    }

    default public <S> Codec<S> flatXmap(Function<? super A, ? extends DataResult<? extends S>> function, Function<? super S, ? extends DataResult<? extends A>> function2) {
        return Codec.of(this.flatComap(function2), this.flatMap(function), this.toString() + "[flatXmapped]");
    }

    @Override
    default public MapCodec<A> fieldOf(String string) {
        return MapCodec.of(Encoder.super.fieldOf(string), Decoder.super.fieldOf(string), () -> this.lambda$fieldOf$1(string));
    }

    default public MapCodec<Optional<A>> optionalFieldOf(String string) {
        return Codec.optionalField(string, this);
    }

    default public MapCodec<A> optionalFieldOf(String string, A a) {
        return Codec.optionalField(string, this).xmap(arg_0 -> Codec.lambda$optionalFieldOf$2(a, arg_0), arg_0 -> Codec.lambda$optionalFieldOf$3(a, arg_0));
    }

    default public MapCodec<A> optionalFieldOf(String string, A a, Lifecycle lifecycle) {
        return this.optionalFieldOf(string, Lifecycle.experimental(), a, lifecycle);
    }

    default public MapCodec<A> optionalFieldOf(String string, Lifecycle lifecycle, A a, Lifecycle lifecycle2) {
        return Codec.optionalField(string, this).stable().flatXmap(arg_0 -> Codec.lambda$optionalFieldOf$5(lifecycle, a, lifecycle2, arg_0), arg_0 -> Codec.lambda$optionalFieldOf$6(a, lifecycle2, lifecycle, arg_0));
    }

    default public Codec<A> mapResult(ResultFunction<A> resultFunction) {
        return new Codec<A>(this, resultFunction){
            final ResultFunction val$function;
            final Codec this$0;
            {
                this.this$0 = codec;
                this.val$function = resultFunction;
            }

            @Override
            public <T> DataResult<T> encode(A a, DynamicOps<T> dynamicOps, T t) {
                return this.val$function.coApply(dynamicOps, a, this.this$0.encode(a, dynamicOps, t));
            }

            @Override
            public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> dynamicOps, T t) {
                return this.val$function.apply(dynamicOps, t, this.this$0.decode(dynamicOps, t));
            }

            public String toString() {
                return this.this$0 + "[mapResult " + this.val$function + "]";
            }
        };
    }

    default public Codec<A> orElse(Consumer<String> consumer, A a) {
        return this.orElse(DataFixUtils.consumerToFunction(consumer), a);
    }

    default public Codec<A> orElse(UnaryOperator<String> unaryOperator, A a) {
        return this.mapResult(new ResultFunction<A>(this, unaryOperator, a){
            final UnaryOperator val$onError;
            final Object val$value;
            final Codec this$0;
            {
                this.this$0 = codec;
                this.val$onError = unaryOperator;
                this.val$value = object;
            }

            @Override
            public <T> DataResult<Pair<A, T>> apply(DynamicOps<T> dynamicOps, T t, DataResult<Pair<A, T>> dataResult) {
                return DataResult.success(dataResult.mapError(this.val$onError).result().orElseGet(() -> 5.lambda$apply$0(this.val$value, t)));
            }

            @Override
            public <T> DataResult<T> coApply(DynamicOps<T> dynamicOps, A a, DataResult<T> dataResult) {
                return dataResult.mapError(this.val$onError);
            }

            public String toString() {
                return "OrElse[" + this.val$onError + " " + this.val$value + "]";
            }

            private static Pair lambda$apply$0(Object object, Object object2) {
                return Pair.of(object, object2);
            }
        });
    }

    default public Codec<A> orElseGet(Consumer<String> consumer, Supplier<? extends A> supplier) {
        return this.orElseGet(DataFixUtils.consumerToFunction(consumer), supplier);
    }

    default public Codec<A> orElseGet(UnaryOperator<String> unaryOperator, Supplier<? extends A> supplier) {
        return this.mapResult(new ResultFunction<A>(this, unaryOperator, supplier){
            final UnaryOperator val$onError;
            final Supplier val$value;
            final Codec this$0;
            {
                this.this$0 = codec;
                this.val$onError = unaryOperator;
                this.val$value = supplier;
            }

            @Override
            public <T> DataResult<Pair<A, T>> apply(DynamicOps<T> dynamicOps, T t, DataResult<Pair<A, T>> dataResult) {
                return DataResult.success(dataResult.mapError(this.val$onError).result().orElseGet(() -> 6.lambda$apply$0((Supplier)this.val$value, t)));
            }

            @Override
            public <T> DataResult<T> coApply(DynamicOps<T> dynamicOps, A a, DataResult<T> dataResult) {
                return dataResult.mapError(this.val$onError);
            }

            public String toString() {
                return "OrElseGet[" + this.val$onError + " " + this.val$value.get() + "]";
            }

            private static Pair lambda$apply$0(Supplier supplier, Object object) {
                return Pair.of(supplier.get(), object);
            }
        });
    }

    default public Codec<A> orElse(A a) {
        return this.mapResult(new ResultFunction<A>(this, a){
            final Object val$value;
            final Codec this$0;
            {
                this.this$0 = codec;
                this.val$value = object;
            }

            @Override
            public <T> DataResult<Pair<A, T>> apply(DynamicOps<T> dynamicOps, T t, DataResult<Pair<A, T>> dataResult) {
                return DataResult.success(dataResult.result().orElseGet(() -> 7.lambda$apply$0(this.val$value, t)));
            }

            @Override
            public <T> DataResult<T> coApply(DynamicOps<T> dynamicOps, A a, DataResult<T> dataResult) {
                return dataResult;
            }

            public String toString() {
                return "OrElse[" + this.val$value + "]";
            }

            private static Pair lambda$apply$0(Object object, Object object2) {
                return Pair.of(object, object2);
            }
        });
    }

    default public Codec<A> orElseGet(Supplier<? extends A> supplier) {
        return this.mapResult(new ResultFunction<A>(this, supplier){
            final Supplier val$value;
            final Codec this$0;
            {
                this.this$0 = codec;
                this.val$value = supplier;
            }

            @Override
            public <T> DataResult<Pair<A, T>> apply(DynamicOps<T> dynamicOps, T t, DataResult<Pair<A, T>> dataResult) {
                return DataResult.success(dataResult.result().orElseGet(() -> 8.lambda$apply$0((Supplier)this.val$value, t)));
            }

            @Override
            public <T> DataResult<T> coApply(DynamicOps<T> dynamicOps, A a, DataResult<T> dataResult) {
                return dataResult;
            }

            public String toString() {
                return "OrElseGet[" + this.val$value.get() + "]";
            }

            private static Pair lambda$apply$0(Supplier supplier, Object object) {
                return Pair.of(supplier.get(), object);
            }
        });
    }

    @Override
    default public Codec<A> promotePartial(Consumer<String> consumer) {
        return Codec.of(this, Decoder.super.promotePartial(consumer));
    }

    public static <A> Codec<A> unit(A a) {
        return Codec.unit(() -> Codec.lambda$unit$7(a));
    }

    public static <A> Codec<A> unit(Supplier<A> supplier) {
        return MapCodec.unit(supplier).codec();
    }

    default public <E> Codec<E> dispatch(Function<? super E, ? extends A> function, Function<? super A, ? extends Codec<? extends E>> function2) {
        return this.dispatch("type", function, function2);
    }

    default public <E> Codec<E> dispatch(String string, Function<? super E, ? extends A> function, Function<? super A, ? extends Codec<? extends E>> function2) {
        return this.partialDispatch(string, function.andThen(DataResult::success), function2.andThen(DataResult::success));
    }

    default public <E> Codec<E> dispatchStable(Function<? super E, ? extends A> function, Function<? super A, ? extends Codec<? extends E>> function2) {
        return this.partialDispatch("type", arg_0 -> Codec.lambda$dispatchStable$8(function, arg_0), arg_0 -> Codec.lambda$dispatchStable$9(function2, arg_0));
    }

    default public <E> Codec<E> partialDispatch(String string, Function<? super E, ? extends DataResult<? extends A>> function, Function<? super A, ? extends DataResult<? extends Codec<? extends E>>> function2) {
        return new KeyDispatchCodec<A, E>(string, this, function, function2).codec();
    }

    default public <E> MapCodec<E> dispatchMap(Function<? super E, ? extends A> function, Function<? super A, ? extends Codec<? extends E>> function2) {
        return this.dispatchMap("type", function, function2);
    }

    default public <E> MapCodec<E> dispatchMap(String string, Function<? super E, ? extends A> function, Function<? super A, ? extends Codec<? extends E>> function2) {
        return new KeyDispatchCodec<A, E>(string, this, function.andThen(DataResult::success), function2.andThen(DataResult::success));
    }

    public static <N extends Number> Function<N, DataResult<N>> checkRange(N n, N n2) {
        return arg_0 -> Codec.lambda$checkRange$10(n, n2, arg_0);
    }

    public static Codec<Integer> intRange(int n, int n2) {
        Function<Integer, DataResult<Integer>> function = Codec.checkRange(n, n2);
        return INT.flatXmap(function, function);
    }

    public static Codec<Float> floatRange(float f, float f2) {
        Function<Float, DataResult<Float>> function = Codec.checkRange(Float.valueOf(f), Float.valueOf(f2));
        return FLOAT.flatXmap(function, function);
    }

    public static Codec<Double> doubleRange(double d, double d2) {
        Function<Double, DataResult<Double>> function = Codec.checkRange(d, d2);
        return DOUBLE.flatXmap(function, function);
    }

    @Override
    default public Encoder withLifecycle(Lifecycle lifecycle) {
        return this.withLifecycle(lifecycle);
    }

    @Override
    default public MapEncoder fieldOf(String string) {
        return this.fieldOf(string);
    }

    @Override
    default public Decoder withLifecycle(Lifecycle lifecycle) {
        return this.withLifecycle(lifecycle);
    }

    @Override
    default public Decoder promotePartial(Consumer consumer) {
        return this.promotePartial(consumer);
    }

    @Override
    default public MapDecoder fieldOf(String string) {
        return this.fieldOf(string);
    }

    private static DataResult lambda$checkRange$10(Number number, Number number2, Number number3) {
        if (((Comparable)((Object)number3)).compareTo(number) >= 0 && ((Comparable)((Object)number3)).compareTo(number2) <= 0) {
            return DataResult.success(number3);
        }
        return DataResult.error("Value " + number3 + " outside of range [" + number + ":" + number2 + "]", number3);
    }

    private static DataResult lambda$dispatchStable$9(Function function, Object object) {
        return DataResult.success(function.apply(object), Lifecycle.stable());
    }

    private static DataResult lambda$dispatchStable$8(Function function, Object object) {
        return DataResult.success(function.apply(object), Lifecycle.stable());
    }

    private static Object lambda$unit$7(Object object) {
        return object;
    }

    private static DataResult lambda$optionalFieldOf$6(Object object, Lifecycle lifecycle, Lifecycle lifecycle2, Object object2) {
        return Objects.equals(object2, object) ? DataResult.success(Optional.empty(), lifecycle) : DataResult.success(Optional.of(object2), lifecycle2);
    }

    private static DataResult lambda$optionalFieldOf$5(Lifecycle lifecycle, Object object, Lifecycle lifecycle2, Optional optional) {
        return optional.map(arg_0 -> Codec.lambda$null$4(lifecycle, arg_0)).orElse(DataResult.success(object, lifecycle2));
    }

    private static DataResult lambda$null$4(Lifecycle lifecycle, Object object) {
        return DataResult.success(object, lifecycle);
    }

    private static Optional lambda$optionalFieldOf$3(Object object, Object object2) {
        return Objects.equals(object2, object) ? Optional.empty() : Optional.of(object2);
    }

    private static Object lambda$optionalFieldOf$2(Object object, Optional optional) {
        return optional.orElse(object);
    }

    private String lambda$fieldOf$1(String string) {
        return "Field[" + string + ": " + this.toString() + "]";
    }

    private static String lambda$of$0(MapEncoder mapEncoder, MapDecoder mapDecoder) {
        return "MapCodec[" + mapEncoder + " " + mapDecoder + "]";
    }

    public static interface ResultFunction<A> {
        public <T> DataResult<Pair<A, T>> apply(DynamicOps<T> var1, T var2, DataResult<Pair<A, T>> var3);

        public <T> DataResult<T> coApply(DynamicOps<T> var1, A var2, DataResult<T> var3);
    }
}

