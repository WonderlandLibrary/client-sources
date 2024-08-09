/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.Lifecycle;
import java.util.List;
import java.util.function.UnaryOperator;

public interface ListBuilder<T> {
    public DynamicOps<T> ops();

    public DataResult<T> build(T var1);

    public ListBuilder<T> add(T var1);

    public ListBuilder<T> add(DataResult<T> var1);

    public ListBuilder<T> withErrorsFrom(DataResult<?> var1);

    public ListBuilder<T> mapError(UnaryOperator<String> var1);

    default public DataResult<T> build(DataResult<T> dataResult) {
        return dataResult.flatMap(this::build);
    }

    default public <E> ListBuilder<T> add(E e, Encoder<E> encoder) {
        return this.add(encoder.encodeStart(this.ops(), e));
    }

    default public <E> ListBuilder<T> addAll(Iterable<E> iterable, Encoder<E> encoder) {
        iterable.forEach(arg_0 -> this.lambda$addAll$0(encoder, arg_0));
        return this;
    }

    private void lambda$addAll$0(Encoder encoder, Object object) {
        encoder.encode(object, this.ops(), this.ops().empty());
    }

    public static final class Builder<T>
    implements ListBuilder<T> {
        private final DynamicOps<T> ops;
        private DataResult<ImmutableList.Builder<T>> builder = DataResult.success(ImmutableList.builder(), Lifecycle.stable());

        public Builder(DynamicOps<T> dynamicOps) {
            this.ops = dynamicOps;
        }

        @Override
        public DynamicOps<T> ops() {
            return this.ops;
        }

        @Override
        public ListBuilder<T> add(T t) {
            this.builder = this.builder.map(arg_0 -> Builder.lambda$add$0(t, arg_0));
            return this;
        }

        @Override
        public ListBuilder<T> add(DataResult<T> dataResult) {
            this.builder = this.builder.apply2stable(ImmutableList.Builder::add, dataResult);
            return this;
        }

        @Override
        public ListBuilder<T> withErrorsFrom(DataResult<?> dataResult) {
            this.builder = this.builder.flatMap(arg_0 -> Builder.lambda$withErrorsFrom$2(dataResult, arg_0));
            return this;
        }

        @Override
        public ListBuilder<T> mapError(UnaryOperator<String> unaryOperator) {
            this.builder = this.builder.mapError(unaryOperator);
            return this;
        }

        @Override
        public DataResult<T> build(T t) {
            DataResult dataResult = this.builder.flatMap(arg_0 -> this.lambda$build$3(t, arg_0));
            this.builder = DataResult.success(ImmutableList.builder(), Lifecycle.stable());
            return dataResult;
        }

        private DataResult lambda$build$3(Object object, ImmutableList.Builder builder) {
            return this.ops.mergeToList(object, (List<Object>)((Object)builder.build()));
        }

        private static DataResult lambda$withErrorsFrom$2(DataResult dataResult, ImmutableList.Builder builder) {
            return dataResult.map(arg_0 -> Builder.lambda$null$1(builder, arg_0));
        }

        private static ImmutableList.Builder lambda$null$1(ImmutableList.Builder builder, Object object) {
            return builder;
        }

        private static ImmutableList.Builder lambda$add$0(Object object, ImmutableList.Builder builder) {
            return builder.add(object);
        }
    }
}

