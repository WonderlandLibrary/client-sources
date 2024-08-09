/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization;

import com.mojang.serialization.CompressorHolder;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.KeyCompressor;
import com.mojang.serialization.Keyable;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.RecordBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public interface MapEncoder<A>
extends Keyable {
    public <T> RecordBuilder<T> encode(A var1, DynamicOps<T> var2, RecordBuilder<T> var3);

    default public <T> RecordBuilder<T> compressedBuilder(DynamicOps<T> dynamicOps) {
        if (dynamicOps.compressMaps()) {
            return MapEncoder.makeCompressedBuilder(dynamicOps, this.compressor(dynamicOps));
        }
        return dynamicOps.mapBuilder();
    }

    public <T> KeyCompressor<T> compressor(DynamicOps<T> var1);

    default public <B> MapEncoder<B> comap(Function<? super B, ? extends A> function) {
        return new Implementation<B>(this, function){
            final Function val$function;
            final MapEncoder this$0;
            {
                this.this$0 = mapEncoder;
                this.val$function = function;
            }

            @Override
            public <T> RecordBuilder<T> encode(B b, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
                return this.this$0.encode(this.val$function.apply(b), dynamicOps, recordBuilder);
            }

            @Override
            public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                return this.this$0.keys(dynamicOps);
            }

            public String toString() {
                return this.this$0.toString() + "[comapped]";
            }
        };
    }

    default public <B> MapEncoder<B> flatComap(Function<? super B, ? extends DataResult<? extends A>> function) {
        return new Implementation<B>(this, function){
            final Function val$function;
            final MapEncoder this$0;
            {
                this.this$0 = mapEncoder;
                this.val$function = function;
            }

            @Override
            public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                return this.this$0.keys(dynamicOps);
            }

            @Override
            public <T> RecordBuilder<T> encode(B b, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
                DataResult dataResult = (DataResult)this.val$function.apply(b);
                RecordBuilder<T> recordBuilder2 = recordBuilder.withErrorsFrom(dataResult);
                return dataResult.map(arg_0 -> this.lambda$encode$0(dynamicOps, recordBuilder2, arg_0)).result().orElse(recordBuilder2);
            }

            public String toString() {
                return this.this$0.toString() + "[flatComapped]";
            }

            private RecordBuilder lambda$encode$0(DynamicOps dynamicOps, RecordBuilder recordBuilder, Object object) {
                return this.this$0.encode(object, dynamicOps, recordBuilder);
            }
        };
    }

    default public Encoder<A> encoder() {
        return new Encoder<A>(this){
            final MapEncoder this$0;
            {
                this.this$0 = mapEncoder;
            }

            @Override
            public <T> DataResult<T> encode(A a, DynamicOps<T> dynamicOps, T t) {
                return this.this$0.encode(a, dynamicOps, this.this$0.compressedBuilder(dynamicOps)).build(t);
            }

            public String toString() {
                return this.this$0.toString();
            }
        };
    }

    default public MapEncoder<A> withLifecycle(Lifecycle lifecycle) {
        return new Implementation<A>(this, lifecycle){
            final Lifecycle val$lifecycle;
            final MapEncoder this$0;
            {
                this.this$0 = mapEncoder;
                this.val$lifecycle = lifecycle;
            }

            @Override
            public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                return this.this$0.keys(dynamicOps);
            }

            @Override
            public <T> RecordBuilder<T> encode(A a, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
                return this.this$0.encode(a, dynamicOps, recordBuilder).setLifecycle(this.val$lifecycle);
            }

            public String toString() {
                return this.this$0.toString();
            }
        };
    }

    public static <T> RecordBuilder<T> makeCompressedBuilder(DynamicOps<T> dynamicOps, KeyCompressor<T> keyCompressor) {
        class CompressedRecordBuilder
        extends RecordBuilder.AbstractUniversalBuilder<T, List<T>> {
            final DynamicOps val$ops;
            final KeyCompressor val$compressor;

            CompressedRecordBuilder() {
                this.val$ops = dynamicOps;
                this.val$compressor = keyCompressor;
                super(dynamicOps);
            }

            @Override
            protected List<T> initBuilder() {
                ArrayList<Object> arrayList = new ArrayList<Object>(this.val$compressor.size());
                for (int i = 0; i < this.val$compressor.size(); ++i) {
                    arrayList.add(null);
                }
                return arrayList;
            }

            @Override
            protected List<T> append(T t, T t2, List<T> list) {
                list.set(this.val$compressor.compress(t), t2);
                return list;
            }

            @Override
            protected DataResult<T> build(List<T> list, T t) {
                return this.ops().mergeToList(t, list);
            }

            @Override
            protected Object append(Object object, Object object2, Object object3) {
                return this.append(object, object2, (List)object3);
            }

            @Override
            protected DataResult build(Object object, Object object2) {
                return this.build((List)object, object2);
            }

            @Override
            protected Object initBuilder() {
                return this.initBuilder();
            }
        }
        return new CompressedRecordBuilder();
    }

    public static abstract class Implementation<A>
    extends CompressorHolder
    implements MapEncoder<A> {
    }
}

