/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapDecoder;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.codecs.FieldDecoder;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface Decoder<A> {
    public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> var1, T var2);

    default public <T> DataResult<A> parse(DynamicOps<T> dynamicOps, T t) {
        return this.decode(dynamicOps, t).map(Pair::getFirst);
    }

    default public <T> DataResult<Pair<A, T>> decode(Dynamic<T> dynamic) {
        return this.decode(dynamic.getOps(), dynamic.getValue());
    }

    default public <T> DataResult<A> parse(Dynamic<T> dynamic) {
        return this.decode(dynamic).map(Pair::getFirst);
    }

    default public Terminal<A> terminal() {
        return this::parse;
    }

    default public Boxed<A> boxed() {
        return this::decode;
    }

    default public Simple<A> simple() {
        return this::parse;
    }

    default public MapDecoder<A> fieldOf(String string) {
        return new FieldDecoder(string, this);
    }

    default public <B> Decoder<B> flatMap(Function<? super A, ? extends DataResult<? extends B>> function) {
        return new Decoder<B>(this, function){
            final Function val$function;
            final Decoder this$0;
            {
                this.this$0 = decoder;
                this.val$function = function;
            }

            @Override
            public <T> DataResult<Pair<B, T>> decode(DynamicOps<T> dynamicOps, T t) {
                return this.this$0.decode(dynamicOps, t).flatMap(arg_0 -> 1.lambda$decode$1(this.val$function, arg_0));
            }

            public String toString() {
                return this.this$0.toString() + "[flatMapped]";
            }

            private static DataResult lambda$decode$1(Function function, Pair pair) {
                return ((DataResult)function.apply(pair.getFirst())).map(arg_0 -> 1.lambda$null$0(pair, arg_0));
            }

            private static Pair lambda$null$0(Pair pair, Object object) {
                return Pair.of(object, pair.getSecond());
            }
        };
    }

    default public <B> Decoder<B> map(Function<? super A, ? extends B> function) {
        return new Decoder<B>(this, function){
            final Function val$function;
            final Decoder this$0;
            {
                this.this$0 = decoder;
                this.val$function = function;
            }

            @Override
            public <T> DataResult<Pair<B, T>> decode(DynamicOps<T> dynamicOps, T t) {
                return this.this$0.decode(dynamicOps, t).map(arg_0 -> 2.lambda$decode$0(this.val$function, arg_0));
            }

            public String toString() {
                return this.this$0.toString() + "[mapped]";
            }

            private static Pair lambda$decode$0(Function function, Pair pair) {
                return pair.mapFirst(function);
            }
        };
    }

    default public Decoder<A> promotePartial(Consumer<String> consumer) {
        return new Decoder<A>(this, consumer){
            final Consumer val$onError;
            final Decoder this$0;
            {
                this.this$0 = decoder;
                this.val$onError = consumer;
            }

            @Override
            public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> dynamicOps, T t) {
                return this.this$0.decode(dynamicOps, t).promotePartial(this.val$onError);
            }

            public String toString() {
                return this.this$0.toString() + "[promotePartial]";
            }
        };
    }

    default public Decoder<A> withLifecycle(Lifecycle lifecycle) {
        return new Decoder<A>(this, lifecycle){
            final Lifecycle val$lifecycle;
            final Decoder this$0;
            {
                this.this$0 = decoder;
                this.val$lifecycle = lifecycle;
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

    public static <A> Decoder<A> ofTerminal(Terminal<? extends A> terminal) {
        return terminal.decoder().map(Function.identity());
    }

    public static <A> Decoder<A> ofBoxed(Boxed<? extends A> boxed) {
        return boxed.decoder().map(Function.identity());
    }

    public static <A> Decoder<A> ofSimple(Simple<? extends A> simple) {
        return simple.decoder().map(Function.identity());
    }

    public static <A> MapDecoder<A> unit(A a) {
        return Decoder.unit(() -> Decoder.lambda$unit$0(a));
    }

    public static <A> MapDecoder<A> unit(Supplier<A> supplier) {
        return new MapDecoder.Implementation<A>(supplier){
            final Supplier val$instance;
            {
                this.val$instance = supplier;
            }

            @Override
            public <T> DataResult<A> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
                return DataResult.success(this.val$instance.get());
            }

            @Override
            public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                return Stream.empty();
            }

            public String toString() {
                return "UnitDecoder[" + this.val$instance.get() + "]";
            }
        };
    }

    public static <A> Decoder<A> error(String string) {
        return new Decoder<A>(string){
            final String val$error;
            {
                this.val$error = string;
            }

            @Override
            public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> dynamicOps, T t) {
                return DataResult.error(this.val$error);
            }

            public String toString() {
                return "ErrorDecoder[" + this.val$error + ']';
            }
        };
    }

    private static Object lambda$unit$0(Object object) {
        return object;
    }

    public static interface Simple<A> {
        public <T> DataResult<A> decode(Dynamic<T> var1);

        default public Decoder<A> decoder() {
            return new Decoder<A>(this){
                final Simple this$0;
                {
                    this.this$0 = simple;
                }

                @Override
                public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> dynamicOps, T t) {
                    return this.this$0.decode(new Dynamic<T>(dynamicOps, t)).map(arg_0 -> 1.lambda$decode$0(dynamicOps, arg_0));
                }

                public String toString() {
                    return "SimpleDecoder[" + this.this$0 + "]";
                }

                private static Pair lambda$decode$0(DynamicOps dynamicOps, Object object) {
                    return Pair.of(object, dynamicOps.empty());
                }
            };
        }
    }

    public static interface Boxed<A> {
        public <T> DataResult<Pair<A, T>> decode(Dynamic<T> var1);

        default public Decoder<A> decoder() {
            return new Decoder<A>(this){
                final Boxed this$0;
                {
                    this.this$0 = boxed;
                }

                @Override
                public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> dynamicOps, T t) {
                    return this.this$0.decode(new Dynamic<T>(dynamicOps, t));
                }

                public String toString() {
                    return "BoxedDecoder[" + this.this$0 + "]";
                }
            };
        }
    }

    public static interface Terminal<A> {
        public <T> DataResult<A> decode(DynamicOps<T> var1, T var2);

        default public Decoder<A> decoder() {
            return new Decoder<A>(this){
                final Terminal this$0;
                {
                    this.this$0 = terminal;
                }

                @Override
                public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> dynamicOps, T t) {
                    return this.this$0.decode(dynamicOps, t).map(arg_0 -> 1.lambda$decode$0(dynamicOps, arg_0));
                }

                public String toString() {
                    return "TerminalDecoder[" + this.this$0 + "]";
                }

                private static Pair lambda$decode$0(DynamicOps dynamicOps, Object object) {
                    return Pair.of(object, dynamicOps.empty());
                }
            };
        }
    }
}

