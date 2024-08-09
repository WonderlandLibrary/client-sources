/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.CompressorHolder;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapDecoder;
import com.mojang.serialization.MapEncoder;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class MapCodec<A>
extends CompressorHolder
implements MapDecoder<A>,
MapEncoder<A> {
    public final <O> RecordCodecBuilder<O, A> forGetter(Function<O, A> function) {
        return RecordCodecBuilder.of(function, this);
    }

    public static <A> MapCodec<A> of(MapEncoder<A> mapEncoder, MapDecoder<A> mapDecoder) {
        return MapCodec.of(mapEncoder, mapDecoder, () -> MapCodec.lambda$of$0(mapEncoder, mapDecoder));
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

            @Override
            public MapDecoder withLifecycle(Lifecycle lifecycle) {
                return super.withLifecycle(lifecycle);
            }

            @Override
            public MapEncoder withLifecycle(Lifecycle lifecycle) {
                return super.withLifecycle(lifecycle);
            }
        };
    }

    public MapCodec<A> fieldOf(String string) {
        return this.codec().fieldOf(string);
    }

    @Override
    public MapCodec<A> withLifecycle(Lifecycle lifecycle) {
        return new MapCodec<A>(this, lifecycle){
            final Lifecycle val$lifecycle;
            final MapCodec this$0;
            {
                this.this$0 = mapCodec;
                this.val$lifecycle = lifecycle;
            }

            @Override
            public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                return this.this$0.keys(dynamicOps);
            }

            @Override
            public <T> DataResult<A> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
                return this.this$0.decode(dynamicOps, mapLike).setLifecycle(this.val$lifecycle);
            }

            @Override
            public <T> RecordBuilder<T> encode(A a, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
                return this.this$0.encode(a, dynamicOps, recordBuilder).setLifecycle(this.val$lifecycle);
            }

            public String toString() {
                return this.this$0.toString();
            }

            @Override
            public MapDecoder withLifecycle(Lifecycle lifecycle) {
                return super.withLifecycle(lifecycle);
            }

            @Override
            public MapEncoder withLifecycle(Lifecycle lifecycle) {
                return super.withLifecycle(lifecycle);
            }
        };
    }

    public Codec<A> codec() {
        return new MapCodecCodec(this);
    }

    public MapCodec<A> stable() {
        return this.withLifecycle(Lifecycle.stable());
    }

    public MapCodec<A> deprecated(int n) {
        return this.withLifecycle(Lifecycle.deprecated(n));
    }

    public <S> MapCodec<S> xmap(Function<? super A, ? extends S> function, Function<? super S, ? extends A> function2) {
        return MapCodec.of(this.comap(function2), this.map(function), this::lambda$xmap$1);
    }

    public <S> MapCodec<S> flatXmap(Function<? super A, ? extends DataResult<? extends S>> function, Function<? super S, ? extends DataResult<? extends A>> function2) {
        return Codec.of(this.flatComap(function2), this.flatMap(function), this::lambda$flatXmap$2);
    }

    public <E> MapCodec<A> dependent(MapCodec<E> mapCodec, Function<A, Pair<E, MapCodec<E>>> function, BiFunction<A, E, A> biFunction) {
        return new Dependent<A, E>(this, mapCodec, function, biFunction);
    }

    @Override
    public abstract <T> Stream<T> keys(DynamicOps<T> var1);

    public MapCodec<A> mapResult(ResultFunction<A> resultFunction) {
        return new MapCodec<A>(this, resultFunction){
            final ResultFunction val$function;
            final MapCodec this$0;
            {
                this.this$0 = mapCodec;
                this.val$function = resultFunction;
            }

            @Override
            public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                return this.this$0.keys(dynamicOps);
            }

            @Override
            public <T> RecordBuilder<T> encode(A a, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
                return this.val$function.coApply(dynamicOps, a, this.this$0.encode(a, dynamicOps, recordBuilder));
            }

            @Override
            public <T> DataResult<A> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
                return this.val$function.apply(dynamicOps, mapLike, this.this$0.decode(dynamicOps, mapLike));
            }

            public String toString() {
                return this.this$0 + "[mapResult " + this.val$function + "]";
            }

            @Override
            public MapDecoder withLifecycle(Lifecycle lifecycle) {
                return super.withLifecycle(lifecycle);
            }

            @Override
            public MapEncoder withLifecycle(Lifecycle lifecycle) {
                return super.withLifecycle(lifecycle);
            }
        };
    }

    public MapCodec<A> orElse(Consumer<String> consumer, A a) {
        return this.orElse(DataFixUtils.consumerToFunction(consumer), a);
    }

    public MapCodec<A> orElse(UnaryOperator<String> unaryOperator, A a) {
        return this.mapResult(new ResultFunction<A>(this, unaryOperator, a){
            final UnaryOperator val$onError;
            final Object val$value;
            final MapCodec this$0;
            {
                this.this$0 = mapCodec;
                this.val$onError = unaryOperator;
                this.val$value = object;
            }

            @Override
            public <T> DataResult<A> apply(DynamicOps<T> dynamicOps, MapLike<T> mapLike, DataResult<A> dataResult) {
                return DataResult.success(dataResult.mapError(this.val$onError).result().orElse(this.val$value));
            }

            @Override
            public <T> RecordBuilder<T> coApply(DynamicOps<T> dynamicOps, A a, RecordBuilder<T> recordBuilder) {
                return recordBuilder.mapError(this.val$onError);
            }

            public String toString() {
                return "OrElse[" + this.val$onError + " " + this.val$value + "]";
            }
        });
    }

    public MapCodec<A> orElseGet(Consumer<String> consumer, Supplier<? extends A> supplier) {
        return this.orElseGet(DataFixUtils.consumerToFunction(consumer), supplier);
    }

    public MapCodec<A> orElseGet(UnaryOperator<String> unaryOperator, Supplier<? extends A> supplier) {
        return this.mapResult(new ResultFunction<A>(this, unaryOperator, supplier){
            final UnaryOperator val$onError;
            final Supplier val$value;
            final MapCodec this$0;
            {
                this.this$0 = mapCodec;
                this.val$onError = unaryOperator;
                this.val$value = supplier;
            }

            @Override
            public <T> DataResult<A> apply(DynamicOps<T> dynamicOps, MapLike<T> mapLike, DataResult<A> dataResult) {
                return DataResult.success(dataResult.mapError(this.val$onError).result().orElseGet(this.val$value));
            }

            @Override
            public <T> RecordBuilder<T> coApply(DynamicOps<T> dynamicOps, A a, RecordBuilder<T> recordBuilder) {
                return recordBuilder.mapError(this.val$onError);
            }

            public String toString() {
                return "OrElseGet[" + this.val$onError + " " + this.val$value.get() + "]";
            }
        });
    }

    public MapCodec<A> orElse(A a) {
        return this.mapResult(new ResultFunction<A>(this, a){
            final Object val$value;
            final MapCodec this$0;
            {
                this.this$0 = mapCodec;
                this.val$value = object;
            }

            @Override
            public <T> DataResult<A> apply(DynamicOps<T> dynamicOps, MapLike<T> mapLike, DataResult<A> dataResult) {
                return DataResult.success(dataResult.result().orElse(this.val$value));
            }

            @Override
            public <T> RecordBuilder<T> coApply(DynamicOps<T> dynamicOps, A a, RecordBuilder<T> recordBuilder) {
                return recordBuilder;
            }

            public String toString() {
                return "OrElse[" + this.val$value + "]";
            }
        });
    }

    public MapCodec<A> orElseGet(Supplier<? extends A> supplier) {
        return this.mapResult(new ResultFunction<A>(this, supplier){
            final Supplier val$value;
            final MapCodec this$0;
            {
                this.this$0 = mapCodec;
                this.val$value = supplier;
            }

            @Override
            public <T> DataResult<A> apply(DynamicOps<T> dynamicOps, MapLike<T> mapLike, DataResult<A> dataResult) {
                return DataResult.success(dataResult.result().orElseGet(this.val$value));
            }

            @Override
            public <T> RecordBuilder<T> coApply(DynamicOps<T> dynamicOps, A a, RecordBuilder<T> recordBuilder) {
                return recordBuilder;
            }

            public String toString() {
                return "OrElseGet[" + this.val$value.get() + "]";
            }
        });
    }

    public MapCodec<A> setPartial(Supplier<A> supplier) {
        return this.mapResult(new ResultFunction<A>(this, supplier){
            final Supplier val$value;
            final MapCodec this$0;
            {
                this.this$0 = mapCodec;
                this.val$value = supplier;
            }

            @Override
            public <T> DataResult<A> apply(DynamicOps<T> dynamicOps, MapLike<T> mapLike, DataResult<A> dataResult) {
                return dataResult.setPartial((Object)this.val$value);
            }

            @Override
            public <T> RecordBuilder<T> coApply(DynamicOps<T> dynamicOps, A a, RecordBuilder<T> recordBuilder) {
                return recordBuilder;
            }

            public String toString() {
                return "SetPartial[" + this.val$value + "]";
            }
        });
    }

    public static <A> MapCodec<A> unit(A a) {
        return MapCodec.unit(() -> MapCodec.lambda$unit$3(a));
    }

    public static <A> MapCodec<A> unit(Supplier<A> supplier) {
        return MapCodec.of(Encoder.empty(), Decoder.unit(supplier));
    }

    @Override
    public MapDecoder withLifecycle(Lifecycle lifecycle) {
        return this.withLifecycle(lifecycle);
    }

    @Override
    public MapEncoder withLifecycle(Lifecycle lifecycle) {
        return this.withLifecycle(lifecycle);
    }

    private static Object lambda$unit$3(Object object) {
        return object;
    }

    private String lambda$flatXmap$2() {
        return this.toString() + "[flatXmapped]";
    }

    private String lambda$xmap$1() {
        return this.toString() + "[xmapped]";
    }

    private static String lambda$of$0(MapEncoder mapEncoder, MapDecoder mapDecoder) {
        return "MapCodec[" + mapEncoder + " " + mapDecoder + "]";
    }

    public static interface ResultFunction<A> {
        public <T> DataResult<A> apply(DynamicOps<T> var1, MapLike<T> var2, DataResult<A> var3);

        public <T> RecordBuilder<T> coApply(DynamicOps<T> var1, A var2, RecordBuilder<T> var3);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class Dependent<O, E>
    extends MapCodec<O> {
        private final MapCodec<E> initialInstance;
        private final Function<O, Pair<E, MapCodec<E>>> splitter;
        private final MapCodec<O> codec;
        private final BiFunction<O, E, O> combiner;

        public Dependent(MapCodec<O> mapCodec, MapCodec<E> mapCodec2, Function<O, Pair<E, MapCodec<E>>> function, BiFunction<O, E, O> biFunction) {
            this.initialInstance = mapCodec2;
            this.splitter = function;
            this.codec = mapCodec;
            this.combiner = biFunction;
        }

        @Override
        public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
            return Stream.concat(this.codec.keys(dynamicOps), this.initialInstance.keys(dynamicOps));
        }

        @Override
        public <T> DataResult<O> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
            return this.codec.decode(dynamicOps, mapLike).flatMap(arg_0 -> this.lambda$decode$1(dynamicOps, mapLike, arg_0));
        }

        @Override
        public <T> RecordBuilder<T> encode(O o, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
            this.codec.encode(o, dynamicOps, recordBuilder);
            Pair<E, MapCodec<E>> pair = this.splitter.apply(o);
            pair.getSecond().encode(pair.getFirst(), dynamicOps, recordBuilder);
            return recordBuilder.setLifecycle(Lifecycle.experimental());
        }

        @Override
        public MapDecoder withLifecycle(Lifecycle lifecycle) {
            return super.withLifecycle(lifecycle);
        }

        @Override
        public MapEncoder withLifecycle(Lifecycle lifecycle) {
            return super.withLifecycle(lifecycle);
        }

        private DataResult lambda$decode$1(DynamicOps dynamicOps, MapLike mapLike, Object object) {
            return this.splitter.apply(object).getSecond().decode(dynamicOps, mapLike).map(arg_0 -> this.lambda$null$0(object, arg_0)).setLifecycle(Lifecycle.experimental());
        }

        private Object lambda$null$0(Object object, Object object2) {
            return this.combiner.apply(object, object2);
        }
    }

    public static final class MapCodecCodec<A>
    implements Codec<A> {
        private final MapCodec<A> codec;

        public MapCodecCodec(MapCodec<A> mapCodec) {
            this.codec = mapCodec;
        }

        public MapCodec<A> codec() {
            return this.codec;
        }

        @Override
        public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> dynamicOps, T t) {
            return this.codec.compressedDecode(dynamicOps, t).map(arg_0 -> MapCodecCodec.lambda$decode$0(t, arg_0));
        }

        @Override
        public <T> DataResult<T> encode(A a, DynamicOps<T> dynamicOps, T t) {
            return this.codec.encode(a, dynamicOps, this.codec.compressedBuilder(dynamicOps)).build(t);
        }

        public String toString() {
            return this.codec.toString();
        }

        private static Pair lambda$decode$0(Object object, Object object2) {
            return Pair.of(object2, object);
        }
    }
}

