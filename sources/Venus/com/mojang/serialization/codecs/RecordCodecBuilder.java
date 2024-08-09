/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization.codecs;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapDecoder;
import com.mojang.serialization.MapEncoder;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public final class RecordCodecBuilder<O, F>
implements App<Mu<O>, F> {
    private final Function<O, F> getter;
    private final Function<O, MapEncoder<F>> encoder;
    private final MapDecoder<F> decoder;

    public static <O, F> RecordCodecBuilder<O, F> unbox(App<Mu<O>, F> app) {
        return (RecordCodecBuilder)app;
    }

    private RecordCodecBuilder(Function<O, F> function, Function<O, MapEncoder<F>> function2, MapDecoder<F> mapDecoder) {
        this.getter = function;
        this.encoder = function2;
        this.decoder = mapDecoder;
    }

    public static <O> Instance<O> instance() {
        return new Instance();
    }

    public static <O, F> RecordCodecBuilder<O, F> of(Function<O, F> function, String string, Codec<F> codec) {
        return RecordCodecBuilder.of(function, codec.fieldOf(string));
    }

    public static <O, F> RecordCodecBuilder<O, F> of(Function<O, F> function, MapCodec<F> mapCodec) {
        return new RecordCodecBuilder<Object, F>(function, arg_0 -> RecordCodecBuilder.lambda$of$0(mapCodec, arg_0), mapCodec);
    }

    public static <O, F> RecordCodecBuilder<O, F> point(F f) {
        return new RecordCodecBuilder<Object, Object>(arg_0 -> RecordCodecBuilder.lambda$point$1(f, arg_0), RecordCodecBuilder::lambda$point$2, Decoder.unit(f));
    }

    public static <O, F> RecordCodecBuilder<O, F> stable(F f) {
        return RecordCodecBuilder.point(f, Lifecycle.stable());
    }

    public static <O, F> RecordCodecBuilder<O, F> deprecated(F f, int n) {
        return RecordCodecBuilder.point(f, Lifecycle.deprecated(n));
    }

    public static <O, F> RecordCodecBuilder<O, F> point(F f, Lifecycle lifecycle) {
        return new RecordCodecBuilder<Object, Object>(arg_0 -> RecordCodecBuilder.lambda$point$3(f, arg_0), arg_0 -> RecordCodecBuilder.lambda$point$4(lifecycle, arg_0), Decoder.unit(f).withLifecycle(lifecycle));
    }

    public static <O> Codec<O> create(Function<Instance<O>, ? extends App<Mu<O>, O>> function) {
        return RecordCodecBuilder.build(function.apply(RecordCodecBuilder.instance())).codec();
    }

    public static <O> MapCodec<O> mapCodec(Function<Instance<O>, ? extends App<Mu<O>, O>> function) {
        return RecordCodecBuilder.build(function.apply(RecordCodecBuilder.instance()));
    }

    public <E> RecordCodecBuilder<O, E> dependent(Function<O, E> function, MapEncoder<E> mapEncoder, Function<? super F, ? extends MapDecoder<E>> function2) {
        return new RecordCodecBuilder<Object, E>(function, arg_0 -> RecordCodecBuilder.lambda$dependent$5(mapEncoder, arg_0), new MapDecoder.Implementation<E>(this, function2, mapEncoder){
            final Function val$decoderGetter;
            final MapEncoder val$encoder;
            final RecordCodecBuilder this$0;
            {
                this.this$0 = recordCodecBuilder;
                this.val$decoderGetter = function;
                this.val$encoder = mapEncoder;
            }

            @Override
            public <T> DataResult<E> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
                return RecordCodecBuilder.access$000(this.this$0).decode(dynamicOps, mapLike).map(this.val$decoderGetter).flatMap(arg_0 -> 1.lambda$decode$0(dynamicOps, mapLike, arg_0));
            }

            @Override
            public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                return this.val$encoder.keys(dynamicOps);
            }

            public String toString() {
                return "Dependent[" + this.val$encoder + "]";
            }

            private static DataResult lambda$decode$0(DynamicOps dynamicOps, MapLike mapLike, MapDecoder mapDecoder) {
                return mapDecoder.decode(dynamicOps, mapLike).map(Function.identity());
            }
        });
    }

    public static <O> MapCodec<O> build(App<Mu<O>, O> app) {
        RecordCodecBuilder<O, O> recordCodecBuilder = RecordCodecBuilder.unbox(app);
        return new MapCodec<O>(recordCodecBuilder){
            final RecordCodecBuilder val$builder;
            {
                this.val$builder = recordCodecBuilder;
            }

            @Override
            public <T> DataResult<O> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
                return RecordCodecBuilder.access$000(this.val$builder).decode(dynamicOps, mapLike);
            }

            @Override
            public <T> RecordBuilder<T> encode(O o, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
                return ((MapEncoder)RecordCodecBuilder.access$100(this.val$builder).apply(o)).encode(o, dynamicOps, recordBuilder);
            }

            @Override
            public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                return RecordCodecBuilder.access$000(this.val$builder).keys(dynamicOps);
            }

            public String toString() {
                return "RecordCodec[" + RecordCodecBuilder.access$000(this.val$builder) + "]";
            }
        };
    }

    private static MapEncoder lambda$dependent$5(MapEncoder mapEncoder, Object object) {
        return mapEncoder;
    }

    private static MapEncoder lambda$point$4(Lifecycle lifecycle, Object object) {
        return Encoder.empty().withLifecycle(lifecycle);
    }

    private static Object lambda$point$3(Object object, Object object2) {
        return object;
    }

    private static MapEncoder lambda$point$2(Object object) {
        return Encoder.empty();
    }

    private static Object lambda$point$1(Object object, Object object2) {
        return object;
    }

    private static MapEncoder lambda$of$0(MapCodec mapCodec, Object object) {
        return mapCodec;
    }

    static MapDecoder access$000(RecordCodecBuilder recordCodecBuilder) {
        return recordCodecBuilder.decoder;
    }

    static Function access$100(RecordCodecBuilder recordCodecBuilder) {
        return recordCodecBuilder.encoder;
    }

    RecordCodecBuilder(Function function, Function function2, MapDecoder mapDecoder, 1 var4_4) {
        this(function, function2, mapDecoder);
    }

    static Function access$300(RecordCodecBuilder recordCodecBuilder) {
        return recordCodecBuilder.getter;
    }

    public static final class Instance<O>
    implements Applicative<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, Mu<O>> {
        public <A> App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, A> stable(A a) {
            return RecordCodecBuilder.stable(a);
        }

        public <A> App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, A> deprecated(A a, int n) {
            return RecordCodecBuilder.deprecated(a, n);
        }

        public <A> App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, A> point(A a, Lifecycle lifecycle) {
            return RecordCodecBuilder.point(a, lifecycle);
        }

        @Override
        public <A> App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, A> point(A a) {
            return RecordCodecBuilder.point(a);
        }

        @Override
        public <A, R> Function<App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, A>, App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, R>> lift1(App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, Function<A, R>> app) {
            return arg_0 -> this.lambda$lift1$2(app, arg_0);
        }

        @Override
        public <A, B, R> App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, R> ap2(App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, BiFunction<A, B, R>> app, App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, A> app2, App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, B> app3) {
            RecordCodecBuilder<O, BiFunction<A, B, R>> recordCodecBuilder = RecordCodecBuilder.unbox(app);
            RecordCodecBuilder<O, A> recordCodecBuilder2 = RecordCodecBuilder.unbox(app2);
            RecordCodecBuilder<O, B> recordCodecBuilder3 = RecordCodecBuilder.unbox(app3);
            return new RecordCodecBuilder(arg_0 -> Instance.lambda$ap2$3(recordCodecBuilder, recordCodecBuilder2, recordCodecBuilder3, arg_0), arg_0 -> this.lambda$ap2$4(recordCodecBuilder, recordCodecBuilder2, recordCodecBuilder3, arg_0), new MapDecoder.Implementation<R>(this, recordCodecBuilder, recordCodecBuilder2, recordCodecBuilder3){
                final RecordCodecBuilder val$function;
                final RecordCodecBuilder val$fa;
                final RecordCodecBuilder val$fb;
                final Instance this$0;
                {
                    this.this$0 = instance;
                    this.val$function = recordCodecBuilder;
                    this.val$fa = recordCodecBuilder2;
                    this.val$fb = recordCodecBuilder3;
                }

                @Override
                public <T> DataResult<R> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
                    return DataResult.unbox(DataResult.instance().ap2(RecordCodecBuilder.access$000(this.val$function).decode(dynamicOps, mapLike), RecordCodecBuilder.access$000(this.val$fa).decode(dynamicOps, mapLike), RecordCodecBuilder.access$000(this.val$fb).decode(dynamicOps, mapLike)));
                }

                @Override
                public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                    return Stream.of(RecordCodecBuilder.access$000(this.val$function).keys(dynamicOps), RecordCodecBuilder.access$000(this.val$fa).keys(dynamicOps), RecordCodecBuilder.access$000(this.val$fb).keys(dynamicOps)).flatMap(Function.identity());
                }

                public String toString() {
                    return RecordCodecBuilder.access$000(this.val$function) + " * " + RecordCodecBuilder.access$000(this.val$fa) + " * " + RecordCodecBuilder.access$000(this.val$fb);
                }
            }, null);
        }

        @Override
        public <T1, T2, T3, R> App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, R> ap3(App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, Function3<T1, T2, T3, R>> app, App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, T1> app2, App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, T2> app3, App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, T3> app4) {
            RecordCodecBuilder<O, Function3<T1, T2, T3, R>> recordCodecBuilder = RecordCodecBuilder.unbox(app);
            RecordCodecBuilder<O, T1> recordCodecBuilder2 = RecordCodecBuilder.unbox(app2);
            RecordCodecBuilder<O, T2> recordCodecBuilder3 = RecordCodecBuilder.unbox(app3);
            RecordCodecBuilder<O, T3> recordCodecBuilder4 = RecordCodecBuilder.unbox(app4);
            return new RecordCodecBuilder(arg_0 -> Instance.lambda$ap3$5(recordCodecBuilder, recordCodecBuilder2, recordCodecBuilder3, recordCodecBuilder4, arg_0), arg_0 -> this.lambda$ap3$6(recordCodecBuilder, recordCodecBuilder2, recordCodecBuilder3, recordCodecBuilder4, arg_0), new MapDecoder.Implementation<R>(this, recordCodecBuilder, recordCodecBuilder2, recordCodecBuilder3, recordCodecBuilder4){
                final RecordCodecBuilder val$function;
                final RecordCodecBuilder val$f1;
                final RecordCodecBuilder val$f2;
                final RecordCodecBuilder val$f3;
                final Instance this$0;
                {
                    this.this$0 = instance;
                    this.val$function = recordCodecBuilder;
                    this.val$f1 = recordCodecBuilder2;
                    this.val$f2 = recordCodecBuilder3;
                    this.val$f3 = recordCodecBuilder4;
                }

                @Override
                public <T> DataResult<R> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
                    return DataResult.unbox(DataResult.instance().ap3(RecordCodecBuilder.access$000(this.val$function).decode(dynamicOps, mapLike), RecordCodecBuilder.access$000(this.val$f1).decode(dynamicOps, mapLike), RecordCodecBuilder.access$000(this.val$f2).decode(dynamicOps, mapLike), RecordCodecBuilder.access$000(this.val$f3).decode(dynamicOps, mapLike)));
                }

                @Override
                public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                    return Stream.of(RecordCodecBuilder.access$000(this.val$function).keys(dynamicOps), RecordCodecBuilder.access$000(this.val$f1).keys(dynamicOps), RecordCodecBuilder.access$000(this.val$f2).keys(dynamicOps), RecordCodecBuilder.access$000(this.val$f3).keys(dynamicOps)).flatMap(Function.identity());
                }

                public String toString() {
                    return RecordCodecBuilder.access$000(this.val$function) + " * " + RecordCodecBuilder.access$000(this.val$f1) + " * " + RecordCodecBuilder.access$000(this.val$f2) + " * " + RecordCodecBuilder.access$000(this.val$f3);
                }
            }, null);
        }

        @Override
        public <T1, T2, T3, T4, R> App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, R> ap4(App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, Function4<T1, T2, T3, T4, R>> app, App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, T1> app2, App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, T2> app3, App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, T3> app4, App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, T4> app5) {
            RecordCodecBuilder<O, Function4<T1, T2, T3, T4, R>> recordCodecBuilder = RecordCodecBuilder.unbox(app);
            RecordCodecBuilder<O, T1> recordCodecBuilder2 = RecordCodecBuilder.unbox(app2);
            RecordCodecBuilder<O, T2> recordCodecBuilder3 = RecordCodecBuilder.unbox(app3);
            RecordCodecBuilder<O, T3> recordCodecBuilder4 = RecordCodecBuilder.unbox(app4);
            RecordCodecBuilder<O, T4> recordCodecBuilder5 = RecordCodecBuilder.unbox(app5);
            return new RecordCodecBuilder(arg_0 -> Instance.lambda$ap4$7(recordCodecBuilder, recordCodecBuilder2, recordCodecBuilder3, recordCodecBuilder4, recordCodecBuilder5, arg_0), arg_0 -> this.lambda$ap4$8(recordCodecBuilder, recordCodecBuilder2, recordCodecBuilder3, recordCodecBuilder4, recordCodecBuilder5, arg_0), new MapDecoder.Implementation<R>(this, recordCodecBuilder, recordCodecBuilder2, recordCodecBuilder3, recordCodecBuilder4, recordCodecBuilder5){
                final RecordCodecBuilder val$function;
                final RecordCodecBuilder val$f1;
                final RecordCodecBuilder val$f2;
                final RecordCodecBuilder val$f3;
                final RecordCodecBuilder val$f4;
                final Instance this$0;
                {
                    this.this$0 = instance;
                    this.val$function = recordCodecBuilder;
                    this.val$f1 = recordCodecBuilder2;
                    this.val$f2 = recordCodecBuilder3;
                    this.val$f3 = recordCodecBuilder4;
                    this.val$f4 = recordCodecBuilder5;
                }

                @Override
                public <T> DataResult<R> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
                    return DataResult.unbox(DataResult.instance().ap4(RecordCodecBuilder.access$000(this.val$function).decode(dynamicOps, mapLike), RecordCodecBuilder.access$000(this.val$f1).decode(dynamicOps, mapLike), RecordCodecBuilder.access$000(this.val$f2).decode(dynamicOps, mapLike), RecordCodecBuilder.access$000(this.val$f3).decode(dynamicOps, mapLike), RecordCodecBuilder.access$000(this.val$f4).decode(dynamicOps, mapLike)));
                }

                @Override
                public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                    return Stream.of(RecordCodecBuilder.access$000(this.val$function).keys(dynamicOps), RecordCodecBuilder.access$000(this.val$f1).keys(dynamicOps), RecordCodecBuilder.access$000(this.val$f2).keys(dynamicOps), RecordCodecBuilder.access$000(this.val$f3).keys(dynamicOps), RecordCodecBuilder.access$000(this.val$f4).keys(dynamicOps)).flatMap(Function.identity());
                }

                public String toString() {
                    return RecordCodecBuilder.access$000(this.val$function) + " * " + RecordCodecBuilder.access$000(this.val$f1) + " * " + RecordCodecBuilder.access$000(this.val$f2) + " * " + RecordCodecBuilder.access$000(this.val$f3) + " * " + RecordCodecBuilder.access$000(this.val$f4);
                }
            }, null);
        }

        @Override
        public <T, R> App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, R> map(Function<? super T, ? extends R> function, App<com.mojang.serialization.codecs.RecordCodecBuilder$Mu<O>, T> app) {
            RecordCodecBuilder<O, T> recordCodecBuilder = RecordCodecBuilder.unbox(app);
            Function function2 = RecordCodecBuilder.access$300(recordCodecBuilder);
            return new RecordCodecBuilder(function2.andThen(function), arg_0 -> this.lambda$map$9(recordCodecBuilder, function2, arg_0), RecordCodecBuilder.access$000(recordCodecBuilder).map(function), null);
        }

        private MapEncoder lambda$map$9(RecordCodecBuilder recordCodecBuilder, Function function, Object object) {
            return new MapEncoder.Implementation<R>(this, recordCodecBuilder, object, function){
                private final MapEncoder encoder;
                final RecordCodecBuilder val$unbox;
                final Object val$o;
                final Function val$getter;
                final Instance this$0;
                {
                    this.this$0 = instance;
                    this.val$unbox = recordCodecBuilder;
                    this.val$o = object;
                    this.val$getter = function;
                    this.encoder = (MapEncoder)RecordCodecBuilder.access$100(this.val$unbox).apply(this.val$o);
                }

                @Override
                public <U> RecordBuilder<U> encode(R r, DynamicOps<U> dynamicOps, RecordBuilder<U> recordBuilder) {
                    return this.encoder.encode(this.val$getter.apply(this.val$o), dynamicOps, recordBuilder);
                }

                public <U> Stream<U> keys(DynamicOps<U> dynamicOps) {
                    return this.encoder.keys(dynamicOps);
                }

                public String toString() {
                    return this.encoder + "[mapped]";
                }
            };
        }

        private MapEncoder lambda$ap4$8(RecordCodecBuilder recordCodecBuilder, RecordCodecBuilder recordCodecBuilder2, RecordCodecBuilder recordCodecBuilder3, RecordCodecBuilder recordCodecBuilder4, RecordCodecBuilder recordCodecBuilder5, Object object) {
            MapEncoder mapEncoder = (MapEncoder)RecordCodecBuilder.access$100(recordCodecBuilder).apply(object);
            MapEncoder mapEncoder2 = (MapEncoder)RecordCodecBuilder.access$100(recordCodecBuilder2).apply(object);
            Object r = RecordCodecBuilder.access$300(recordCodecBuilder2).apply(object);
            MapEncoder mapEncoder3 = (MapEncoder)RecordCodecBuilder.access$100(recordCodecBuilder3).apply(object);
            Object r2 = RecordCodecBuilder.access$300(recordCodecBuilder3).apply(object);
            MapEncoder mapEncoder4 = (MapEncoder)RecordCodecBuilder.access$100(recordCodecBuilder4).apply(object);
            Object r3 = RecordCodecBuilder.access$300(recordCodecBuilder4).apply(object);
            MapEncoder mapEncoder5 = (MapEncoder)RecordCodecBuilder.access$100(recordCodecBuilder5).apply(object);
            Object r4 = RecordCodecBuilder.access$300(recordCodecBuilder5).apply(object);
            return new MapEncoder.Implementation<R>(this, mapEncoder2, r, mapEncoder3, r2, mapEncoder4, r3, mapEncoder5, r4, mapEncoder){
                final MapEncoder val$e1;
                final Object val$v1;
                final MapEncoder val$e2;
                final Object val$v2;
                final MapEncoder val$e3;
                final Object val$v3;
                final MapEncoder val$e4;
                final Object val$v4;
                final MapEncoder val$fEncoder;
                final Instance this$0;
                {
                    this.this$0 = instance;
                    this.val$e1 = mapEncoder;
                    this.val$v1 = object;
                    this.val$e2 = mapEncoder2;
                    this.val$v2 = object2;
                    this.val$e3 = mapEncoder3;
                    this.val$v3 = object3;
                    this.val$e4 = mapEncoder4;
                    this.val$v4 = object4;
                    this.val$fEncoder = mapEncoder5;
                }

                @Override
                public <T> RecordBuilder<T> encode(R r, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
                    this.val$e1.encode(this.val$v1, dynamicOps, recordBuilder);
                    this.val$e2.encode(this.val$v2, dynamicOps, recordBuilder);
                    this.val$e3.encode(this.val$v3, dynamicOps, recordBuilder);
                    this.val$e4.encode(this.val$v4, dynamicOps, recordBuilder);
                    this.val$fEncoder.encode((arg_0, arg_1, arg_2, arg_3) -> 8.lambda$encode$0(r, arg_0, arg_1, arg_2, arg_3), dynamicOps, recordBuilder);
                    return recordBuilder;
                }

                @Override
                public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                    return Stream.of(this.val$fEncoder.keys(dynamicOps), this.val$e1.keys(dynamicOps), this.val$e2.keys(dynamicOps), this.val$e3.keys(dynamicOps), this.val$e4.keys(dynamicOps)).flatMap(Function.identity());
                }

                public String toString() {
                    return this.val$fEncoder + " * " + this.val$e1 + " * " + this.val$e2 + " * " + this.val$e3 + " * " + this.val$e4;
                }

                private static Object lambda$encode$0(Object object, Object object2, Object object3, Object object4, Object object5) {
                    return object;
                }
            };
        }

        private static Object lambda$ap4$7(RecordCodecBuilder recordCodecBuilder, RecordCodecBuilder recordCodecBuilder2, RecordCodecBuilder recordCodecBuilder3, RecordCodecBuilder recordCodecBuilder4, RecordCodecBuilder recordCodecBuilder5, Object object) {
            return ((Function4)RecordCodecBuilder.access$300(recordCodecBuilder).apply(object)).apply(RecordCodecBuilder.access$300(recordCodecBuilder2).apply(object), RecordCodecBuilder.access$300(recordCodecBuilder3).apply(object), RecordCodecBuilder.access$300(recordCodecBuilder4).apply(object), RecordCodecBuilder.access$300(recordCodecBuilder5).apply(object));
        }

        private MapEncoder lambda$ap3$6(RecordCodecBuilder recordCodecBuilder, RecordCodecBuilder recordCodecBuilder2, RecordCodecBuilder recordCodecBuilder3, RecordCodecBuilder recordCodecBuilder4, Object object) {
            MapEncoder mapEncoder = (MapEncoder)RecordCodecBuilder.access$100(recordCodecBuilder).apply(object);
            MapEncoder mapEncoder2 = (MapEncoder)RecordCodecBuilder.access$100(recordCodecBuilder2).apply(object);
            Object r = RecordCodecBuilder.access$300(recordCodecBuilder2).apply(object);
            MapEncoder mapEncoder3 = (MapEncoder)RecordCodecBuilder.access$100(recordCodecBuilder3).apply(object);
            Object r2 = RecordCodecBuilder.access$300(recordCodecBuilder3).apply(object);
            MapEncoder mapEncoder4 = (MapEncoder)RecordCodecBuilder.access$100(recordCodecBuilder4).apply(object);
            Object r3 = RecordCodecBuilder.access$300(recordCodecBuilder4).apply(object);
            return new MapEncoder.Implementation<R>(this, mapEncoder2, r, mapEncoder3, r2, mapEncoder4, r3, mapEncoder){
                final MapEncoder val$e1;
                final Object val$v1;
                final MapEncoder val$e2;
                final Object val$v2;
                final MapEncoder val$e3;
                final Object val$v3;
                final MapEncoder val$fEncoder;
                final Instance this$0;
                {
                    this.this$0 = instance;
                    this.val$e1 = mapEncoder;
                    this.val$v1 = object;
                    this.val$e2 = mapEncoder2;
                    this.val$v2 = object2;
                    this.val$e3 = mapEncoder3;
                    this.val$v3 = object3;
                    this.val$fEncoder = mapEncoder4;
                }

                @Override
                public <T> RecordBuilder<T> encode(R r, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
                    this.val$e1.encode(this.val$v1, dynamicOps, recordBuilder);
                    this.val$e2.encode(this.val$v2, dynamicOps, recordBuilder);
                    this.val$e3.encode(this.val$v3, dynamicOps, recordBuilder);
                    this.val$fEncoder.encode((arg_0, arg_1, arg_2) -> 6.lambda$encode$0(r, arg_0, arg_1, arg_2), dynamicOps, recordBuilder);
                    return recordBuilder;
                }

                @Override
                public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                    return Stream.of(this.val$fEncoder.keys(dynamicOps), this.val$e1.keys(dynamicOps), this.val$e2.keys(dynamicOps), this.val$e3.keys(dynamicOps)).flatMap(Function.identity());
                }

                public String toString() {
                    return this.val$fEncoder + " * " + this.val$e1 + " * " + this.val$e2 + " * " + this.val$e3;
                }

                private static Object lambda$encode$0(Object object, Object object2, Object object3, Object object4) {
                    return object;
                }
            };
        }

        private static Object lambda$ap3$5(RecordCodecBuilder recordCodecBuilder, RecordCodecBuilder recordCodecBuilder2, RecordCodecBuilder recordCodecBuilder3, RecordCodecBuilder recordCodecBuilder4, Object object) {
            return ((Function3)RecordCodecBuilder.access$300(recordCodecBuilder).apply(object)).apply(RecordCodecBuilder.access$300(recordCodecBuilder2).apply(object), RecordCodecBuilder.access$300(recordCodecBuilder3).apply(object), RecordCodecBuilder.access$300(recordCodecBuilder4).apply(object));
        }

        private MapEncoder lambda$ap2$4(RecordCodecBuilder recordCodecBuilder, RecordCodecBuilder recordCodecBuilder2, RecordCodecBuilder recordCodecBuilder3, Object object) {
            MapEncoder mapEncoder = (MapEncoder)RecordCodecBuilder.access$100(recordCodecBuilder).apply(object);
            MapEncoder mapEncoder2 = (MapEncoder)RecordCodecBuilder.access$100(recordCodecBuilder2).apply(object);
            Object r = RecordCodecBuilder.access$300(recordCodecBuilder2).apply(object);
            MapEncoder mapEncoder3 = (MapEncoder)RecordCodecBuilder.access$100(recordCodecBuilder3).apply(object);
            Object r2 = RecordCodecBuilder.access$300(recordCodecBuilder3).apply(object);
            return new MapEncoder.Implementation<R>(this, mapEncoder2, r, mapEncoder3, r2, mapEncoder){
                final MapEncoder val$aEncoder;
                final Object val$aFromO;
                final MapEncoder val$bEncoder;
                final Object val$bFromO;
                final MapEncoder val$fEncoder;
                final Instance this$0;
                {
                    this.this$0 = instance;
                    this.val$aEncoder = mapEncoder;
                    this.val$aFromO = object;
                    this.val$bEncoder = mapEncoder2;
                    this.val$bFromO = object2;
                    this.val$fEncoder = mapEncoder3;
                }

                @Override
                public <T> RecordBuilder<T> encode(R r, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
                    this.val$aEncoder.encode(this.val$aFromO, dynamicOps, recordBuilder);
                    this.val$bEncoder.encode(this.val$bFromO, dynamicOps, recordBuilder);
                    this.val$fEncoder.encode((arg_0, arg_1) -> 4.lambda$encode$0(r, arg_0, arg_1), dynamicOps, recordBuilder);
                    return recordBuilder;
                }

                @Override
                public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                    return Stream.of(this.val$fEncoder.keys(dynamicOps), this.val$aEncoder.keys(dynamicOps), this.val$bEncoder.keys(dynamicOps)).flatMap(Function.identity());
                }

                public String toString() {
                    return this.val$fEncoder + " * " + this.val$aEncoder + " * " + this.val$bEncoder;
                }

                private static Object lambda$encode$0(Object object, Object object2, Object object3) {
                    return object;
                }
            };
        }

        private static Object lambda$ap2$3(RecordCodecBuilder recordCodecBuilder, RecordCodecBuilder recordCodecBuilder2, RecordCodecBuilder recordCodecBuilder3, Object object) {
            return ((BiFunction)RecordCodecBuilder.access$300(recordCodecBuilder).apply(object)).apply(RecordCodecBuilder.access$300(recordCodecBuilder2).apply(object), RecordCodecBuilder.access$300(recordCodecBuilder3).apply(object));
        }

        private App lambda$lift1$2(App app, App app2) {
            RecordCodecBuilder recordCodecBuilder = RecordCodecBuilder.unbox(app);
            RecordCodecBuilder recordCodecBuilder2 = RecordCodecBuilder.unbox(app2);
            return new RecordCodecBuilder(arg_0 -> Instance.lambda$null$0(recordCodecBuilder, recordCodecBuilder2, arg_0), arg_0 -> this.lambda$null$1(recordCodecBuilder, recordCodecBuilder2, arg_0), new MapDecoder.Implementation<R>(this, recordCodecBuilder2, recordCodecBuilder){
                final RecordCodecBuilder val$a;
                final RecordCodecBuilder val$f;
                final Instance this$0;
                {
                    this.this$0 = instance;
                    this.val$a = recordCodecBuilder;
                    this.val$f = recordCodecBuilder2;
                }

                @Override
                public <T> DataResult<R> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
                    return RecordCodecBuilder.access$000(this.val$a).decode(dynamicOps, mapLike).flatMap(arg_0 -> 1.lambda$decode$1(this.val$f, dynamicOps, mapLike, arg_0));
                }

                @Override
                public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                    return Stream.concat(RecordCodecBuilder.access$000(this.val$a).keys(dynamicOps), RecordCodecBuilder.access$000(this.val$f).keys(dynamicOps));
                }

                public String toString() {
                    return RecordCodecBuilder.access$000(this.val$f) + " * " + RecordCodecBuilder.access$000(this.val$a);
                }

                private static DataResult lambda$decode$1(RecordCodecBuilder recordCodecBuilder, DynamicOps dynamicOps, MapLike mapLike, Object object) {
                    return RecordCodecBuilder.access$000(recordCodecBuilder).decode(dynamicOps, mapLike).map(arg_0 -> 1.lambda$null$0(object, arg_0));
                }

                private static Object lambda$null$0(Object object, Function function) {
                    return function.apply(object);
                }
            }, null);
        }

        private MapEncoder lambda$null$1(RecordCodecBuilder recordCodecBuilder, RecordCodecBuilder recordCodecBuilder2, Object object) {
            MapEncoder mapEncoder = (MapEncoder)RecordCodecBuilder.access$100(recordCodecBuilder).apply(object);
            MapEncoder mapEncoder2 = (MapEncoder)RecordCodecBuilder.access$100(recordCodecBuilder2).apply(object);
            Object r = RecordCodecBuilder.access$300(recordCodecBuilder2).apply(object);
            return new MapEncoder.Implementation<R>(this, mapEncoder2, r, mapEncoder){
                final MapEncoder val$aEnc;
                final Object val$aFromO;
                final MapEncoder val$fEnc;
                final Instance this$0;
                {
                    this.this$0 = instance;
                    this.val$aEnc = mapEncoder;
                    this.val$aFromO = object;
                    this.val$fEnc = mapEncoder2;
                }

                @Override
                public <T> RecordBuilder<T> encode(R r, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
                    this.val$aEnc.encode(this.val$aFromO, dynamicOps, recordBuilder);
                    this.val$fEnc.encode(arg_0 -> 2.lambda$encode$0(r, arg_0), dynamicOps, recordBuilder);
                    return recordBuilder;
                }

                @Override
                public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                    return Stream.concat(this.val$aEnc.keys(dynamicOps), this.val$fEnc.keys(dynamicOps));
                }

                public String toString() {
                    return this.val$fEnc + " * " + this.val$aEnc;
                }

                private static Object lambda$encode$0(Object object, Object object2) {
                    return object;
                }
            };
        }

        private static Object lambda$null$0(RecordCodecBuilder recordCodecBuilder, RecordCodecBuilder recordCodecBuilder2, Object object) {
            return ((Function)RecordCodecBuilder.access$300(recordCodecBuilder).apply(object)).apply(RecordCodecBuilder.access$300(recordCodecBuilder2).apply(object));
        }

        private static final class Mu<O>
        implements Applicative.Mu {
            private Mu() {
            }
        }
    }

    public static final class Mu<O>
    implements K1 {
    }
}

