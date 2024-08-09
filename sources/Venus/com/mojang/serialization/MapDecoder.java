/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.CompressorHolder;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.KeyCompressor;
import com.mojang.serialization.Keyable;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapLike;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public interface MapDecoder<A>
extends Keyable {
    public <T> DataResult<A> decode(DynamicOps<T> var1, MapLike<T> var2);

    default public <T> DataResult<A> compressedDecode(DynamicOps<T> dynamicOps, T t) {
        if (dynamicOps.compressMaps()) {
            Optional<Consumer<Consumer<T>>> optional = dynamicOps.getList(t).result();
            if (!optional.isPresent()) {
                return DataResult.error("Input is not a list");
            }
            KeyCompressor<T> keyCompressor = this.compressor(dynamicOps);
            ArrayList arrayList = new ArrayList();
            optional.get().accept(arrayList::add);
            MapLike mapLike = new MapLike<T>(this, arrayList, keyCompressor){
                final List val$entries;
                final KeyCompressor val$compressor;
                final MapDecoder this$0;
                {
                    this.this$0 = mapDecoder;
                    this.val$entries = list;
                    this.val$compressor = keyCompressor;
                }

                @Override
                @Nullable
                public T get(T t) {
                    return this.val$entries.get(this.val$compressor.compress(t));
                }

                @Override
                @Nullable
                public T get(String string) {
                    return this.val$entries.get(this.val$compressor.compress(string));
                }

                @Override
                public Stream<Pair<T, T>> entries() {
                    return IntStream.range(0, this.val$entries.size()).mapToObj(arg_0 -> 1.lambda$entries$0(this.val$compressor, this.val$entries, arg_0)).filter(1::lambda$entries$1);
                }

                private static boolean lambda$entries$1(Pair pair) {
                    return pair.getSecond() != null;
                }

                private static Pair lambda$entries$0(KeyCompressor keyCompressor, List list, int n) {
                    return Pair.of(keyCompressor.decompress(n), list.get(n));
                }
            };
            return this.decode(dynamicOps, mapLike);
        }
        return dynamicOps.getMap(t).setLifecycle(Lifecycle.stable()).flatMap(arg_0 -> this.lambda$compressedDecode$0(dynamicOps, arg_0));
    }

    public <T> KeyCompressor<T> compressor(DynamicOps<T> var1);

    default public Decoder<A> decoder() {
        return new Decoder<A>(this){
            final MapDecoder this$0;
            {
                this.this$0 = mapDecoder;
            }

            @Override
            public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> dynamicOps, T t) {
                return this.this$0.compressedDecode(dynamicOps, t).map(arg_0 -> 2.lambda$decode$0(t, arg_0));
            }

            public String toString() {
                return this.this$0.toString();
            }

            private static Pair lambda$decode$0(Object object, Object object2) {
                return Pair.of(object2, object);
            }
        };
    }

    default public <B> MapDecoder<B> flatMap(Function<? super A, ? extends DataResult<? extends B>> function) {
        return new Implementation<B>(this, function){
            final Function val$function;
            final MapDecoder this$0;
            {
                this.this$0 = mapDecoder;
                this.val$function = function;
            }

            @Override
            public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                return this.this$0.keys(dynamicOps);
            }

            @Override
            public <T> DataResult<B> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
                return this.this$0.decode(dynamicOps, mapLike).flatMap(arg_0 -> 3.lambda$decode$0(this.val$function, arg_0));
            }

            public String toString() {
                return this.this$0.toString() + "[flatMapped]";
            }

            private static DataResult lambda$decode$0(Function function, Object object) {
                return ((DataResult)function.apply(object)).map(Function.identity());
            }
        };
    }

    default public <B> MapDecoder<B> map(Function<? super A, ? extends B> function) {
        return new Implementation<B>(this, function){
            final Function val$function;
            final MapDecoder this$0;
            {
                this.this$0 = mapDecoder;
                this.val$function = function;
            }

            @Override
            public <T> DataResult<B> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
                return this.this$0.decode(dynamicOps, mapLike).map(this.val$function);
            }

            @Override
            public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                return this.this$0.keys(dynamicOps);
            }

            public String toString() {
                return this.this$0.toString() + "[mapped]";
            }
        };
    }

    default public <E> MapDecoder<E> ap(MapDecoder<Function<? super A, ? extends E>> mapDecoder) {
        return new Implementation<E>(this, mapDecoder){
            final MapDecoder val$decoder;
            final MapDecoder this$0;
            {
                this.this$0 = mapDecoder;
                this.val$decoder = mapDecoder2;
            }

            @Override
            public <T> DataResult<E> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
                return this.this$0.decode(dynamicOps, mapLike).flatMap(arg_0 -> 5.lambda$decode$1(this.val$decoder, dynamicOps, mapLike, arg_0));
            }

            @Override
            public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                return Stream.concat(this.this$0.keys(dynamicOps), this.val$decoder.keys(dynamicOps));
            }

            public String toString() {
                return this.val$decoder.toString() + " * " + this.this$0.toString();
            }

            private static DataResult lambda$decode$1(MapDecoder mapDecoder, DynamicOps dynamicOps, MapLike mapLike, Object object) {
                return mapDecoder.decode(dynamicOps, mapLike).map(arg_0 -> 5.lambda$null$0(object, arg_0));
            }

            private static Object lambda$null$0(Object object, Function function) {
                return function.apply(object);
            }
        };
    }

    default public MapDecoder<A> withLifecycle(Lifecycle lifecycle) {
        return new Implementation<A>(this, lifecycle){
            final Lifecycle val$lifecycle;
            final MapDecoder this$0;
            {
                this.this$0 = mapDecoder;
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

            public String toString() {
                return this.this$0.toString();
            }
        };
    }

    private DataResult lambda$compressedDecode$0(DynamicOps dynamicOps, MapLike mapLike) {
        return this.decode(dynamicOps, mapLike);
    }

    public static abstract class Implementation<A>
    extends CompressorHolder
    implements MapDecoder<A> {
    }
}

