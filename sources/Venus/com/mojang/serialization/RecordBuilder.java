/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.Lifecycle;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public interface RecordBuilder<T> {
    public DynamicOps<T> ops();

    public RecordBuilder<T> add(T var1, T var2);

    public RecordBuilder<T> add(T var1, DataResult<T> var2);

    public RecordBuilder<T> add(DataResult<T> var1, DataResult<T> var2);

    public RecordBuilder<T> withErrorsFrom(DataResult<?> var1);

    public RecordBuilder<T> setLifecycle(Lifecycle var1);

    public RecordBuilder<T> mapError(UnaryOperator<String> var1);

    public DataResult<T> build(T var1);

    default public DataResult<T> build(DataResult<T> dataResult) {
        return dataResult.flatMap(this::build);
    }

    default public RecordBuilder<T> add(String string, T t) {
        return this.add(this.ops().createString(string), t);
    }

    default public RecordBuilder<T> add(String string, DataResult<T> dataResult) {
        return this.add(this.ops().createString(string), dataResult);
    }

    default public <E> RecordBuilder<T> add(String string, E e, Encoder<E> encoder) {
        return this.add(string, encoder.encodeStart(this.ops(), e));
    }

    public static final class MapBuilder<T>
    extends AbstractUniversalBuilder<T, ImmutableMap.Builder<T, T>> {
        public MapBuilder(DynamicOps<T> dynamicOps) {
            super(dynamicOps);
        }

        @Override
        protected ImmutableMap.Builder<T, T> initBuilder() {
            return ImmutableMap.builder();
        }

        @Override
        protected ImmutableMap.Builder<T, T> append(T t, T t2, ImmutableMap.Builder<T, T> builder) {
            return builder.put(t, t2);
        }

        @Override
        protected DataResult<T> build(ImmutableMap.Builder<T, T> builder, T t) {
            return this.ops().mergeToMap(t, builder.build());
        }

        @Override
        protected Object append(Object object, Object object2, Object object3) {
            return this.append(object, object2, (ImmutableMap.Builder)object3);
        }

        @Override
        protected DataResult build(Object object, Object object2) {
            return this.build((ImmutableMap.Builder)object, object2);
        }

        @Override
        protected Object initBuilder() {
            return this.initBuilder();
        }
    }

    public static abstract class AbstractUniversalBuilder<T, R>
    extends AbstractBuilder<T, R> {
        protected AbstractUniversalBuilder(DynamicOps<T> dynamicOps) {
            super(dynamicOps);
        }

        protected abstract R append(T var1, T var2, R var3);

        @Override
        public RecordBuilder<T> add(T t, T t2) {
            this.builder = this.builder.map(arg_0 -> this.lambda$add$0(t, t2, arg_0));
            return this;
        }

        @Override
        public RecordBuilder<T> add(T t, DataResult<T> dataResult) {
            this.builder = this.builder.apply2stable((arg_0, arg_1) -> this.lambda$add$1(t, arg_0, arg_1), dataResult);
            return this;
        }

        @Override
        public RecordBuilder<T> add(DataResult<T> dataResult, DataResult<T> dataResult2) {
            this.builder = this.builder.ap(dataResult.apply2stable(this::lambda$add$3, dataResult2));
            return this;
        }

        private Function lambda$add$3(Object object, Object object2) {
            return arg_0 -> this.lambda$null$2(object, object2, arg_0);
        }

        private Object lambda$null$2(Object object, Object object2, Object object3) {
            return this.append(object, object2, object3);
        }

        private Object lambda$add$1(Object object, Object object2, Object object3) {
            return this.append(object, object3, object2);
        }

        private Object lambda$add$0(Object object, Object object2, Object object3) {
            return this.append(object, object2, object3);
        }
    }

    public static abstract class AbstractStringBuilder<T, R>
    extends AbstractBuilder<T, R> {
        protected AbstractStringBuilder(DynamicOps<T> dynamicOps) {
            super(dynamicOps);
        }

        protected abstract R append(String var1, T var2, R var3);

        @Override
        public RecordBuilder<T> add(String string, T t) {
            this.builder = this.builder.map(arg_0 -> this.lambda$add$0(string, t, arg_0));
            return this;
        }

        @Override
        public RecordBuilder<T> add(String string, DataResult<T> dataResult) {
            this.builder = this.builder.apply2stable((arg_0, arg_1) -> this.lambda$add$1(string, arg_0, arg_1), dataResult);
            return this;
        }

        @Override
        public RecordBuilder<T> add(T t, T t2) {
            this.builder = this.ops().getStringValue(t).flatMap(arg_0 -> this.lambda$add$2(t2, arg_0));
            return this;
        }

        @Override
        public RecordBuilder<T> add(T t, DataResult<T> dataResult) {
            this.builder = this.ops().getStringValue(t).flatMap(arg_0 -> this.lambda$add$3(dataResult, arg_0));
            return this;
        }

        @Override
        public RecordBuilder<T> add(DataResult<T> dataResult, DataResult<T> dataResult2) {
            this.builder = dataResult.flatMap(this.ops()::getStringValue).flatMap(arg_0 -> this.lambda$add$4(dataResult2, arg_0));
            return this;
        }

        private DataResult lambda$add$4(DataResult dataResult, String string) {
            this.add((T)string, (DataResult<T>)dataResult);
            return this.builder;
        }

        private DataResult lambda$add$3(DataResult dataResult, String string) {
            this.add((T)string, (DataResult<T>)dataResult);
            return this.builder;
        }

        private DataResult lambda$add$2(Object object, String string) {
            this.add((T)string, (T)object);
            return this.builder;
        }

        private Object lambda$add$1(String string, Object object, Object object2) {
            return this.append(string, object2, object);
        }

        private Object lambda$add$0(String string, Object object, Object object2) {
            return this.append(string, object, object2);
        }
    }

    public static abstract class AbstractBuilder<T, R>
    implements RecordBuilder<T> {
        private final DynamicOps<T> ops;
        protected DataResult<R> builder = DataResult.success(this.initBuilder(), Lifecycle.stable());

        protected AbstractBuilder(DynamicOps<T> dynamicOps) {
            this.ops = dynamicOps;
        }

        @Override
        public DynamicOps<T> ops() {
            return this.ops;
        }

        protected abstract R initBuilder();

        protected abstract DataResult<T> build(R var1, T var2);

        @Override
        public DataResult<T> build(T t) {
            DataResult dataResult = this.builder.flatMap(arg_0 -> this.lambda$build$0(t, arg_0));
            this.builder = DataResult.success(this.initBuilder(), Lifecycle.stable());
            return dataResult;
        }

        @Override
        public RecordBuilder<T> withErrorsFrom(DataResult<?> dataResult) {
            this.builder = this.builder.flatMap(arg_0 -> AbstractBuilder.lambda$withErrorsFrom$2(dataResult, arg_0));
            return this;
        }

        @Override
        public RecordBuilder<T> setLifecycle(Lifecycle lifecycle) {
            this.builder = this.builder.setLifecycle(lifecycle);
            return this;
        }

        @Override
        public RecordBuilder<T> mapError(UnaryOperator<String> unaryOperator) {
            this.builder = this.builder.mapError(unaryOperator);
            return this;
        }

        private static DataResult lambda$withErrorsFrom$2(DataResult dataResult, Object object) {
            return dataResult.map(arg_0 -> AbstractBuilder.lambda$null$1(object, arg_0));
        }

        private static Object lambda$null$1(Object object, Object object2) {
            return object;
        }

        private DataResult lambda$build$0(Object object, Object object2) {
            return this.build(object2, object);
        }
    }
}

