/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapEncoder;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.codecs.FieldEncoder;
import java.util.function.Function;
import java.util.stream.Stream;

public interface Encoder<A> {
    public <T> DataResult<T> encode(A var1, DynamicOps<T> var2, T var3);

    default public <T> DataResult<T> encodeStart(DynamicOps<T> dynamicOps, A a) {
        return this.encode(a, dynamicOps, dynamicOps.empty());
    }

    default public MapEncoder<A> fieldOf(String string) {
        return new FieldEncoder(string, this);
    }

    default public <B> Encoder<B> comap(Function<? super B, ? extends A> function) {
        return new Encoder<B>(this, function){
            final Function val$function;
            final Encoder this$0;
            {
                this.this$0 = encoder;
                this.val$function = function;
            }

            @Override
            public <T> DataResult<T> encode(B b, DynamicOps<T> dynamicOps, T t) {
                return this.this$0.encode(this.val$function.apply(b), dynamicOps, t);
            }

            public String toString() {
                return this.this$0.toString() + "[comapped]";
            }
        };
    }

    default public <B> Encoder<B> flatComap(Function<? super B, ? extends DataResult<? extends A>> function) {
        return new Encoder<B>(this, function){
            final Function val$function;
            final Encoder this$0;
            {
                this.this$0 = encoder;
                this.val$function = function;
            }

            @Override
            public <T> DataResult<T> encode(B b, DynamicOps<T> dynamicOps, T t) {
                return ((DataResult)this.val$function.apply(b)).flatMap(arg_0 -> this.lambda$encode$0(dynamicOps, t, arg_0));
            }

            public String toString() {
                return this.this$0.toString() + "[flatComapped]";
            }

            private DataResult lambda$encode$0(DynamicOps dynamicOps, Object object, Object object2) {
                return this.this$0.encode(object2, dynamicOps, object);
            }
        };
    }

    default public Encoder<A> withLifecycle(Lifecycle lifecycle) {
        return new Encoder<A>(this, lifecycle){
            final Lifecycle val$lifecycle;
            final Encoder this$0;
            {
                this.this$0 = encoder;
                this.val$lifecycle = lifecycle;
            }

            @Override
            public <T> DataResult<T> encode(A a, DynamicOps<T> dynamicOps, T t) {
                return this.this$0.encode(a, dynamicOps, t).setLifecycle(this.val$lifecycle);
            }

            public String toString() {
                return this.this$0.toString();
            }
        };
    }

    public static <A> MapEncoder<A> empty() {
        return new MapEncoder.Implementation<A>(){

            @Override
            public <T> RecordBuilder<T> encode(A a, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
                return recordBuilder;
            }

            @Override
            public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                return Stream.empty();
            }

            public String toString() {
                return "EmptyEncoder";
            }
        };
    }

    public static <A> Encoder<A> error(String string) {
        return new Encoder<A>(string){
            final String val$error;
            {
                this.val$error = string;
            }

            @Override
            public <T> DataResult<T> encode(A a, DynamicOps<T> dynamicOps, T t) {
                return DataResult.error(this.val$error + " " + a);
            }

            public String toString() {
                return "ErrorEncoder[" + this.val$error + "]";
            }
        };
    }
}

