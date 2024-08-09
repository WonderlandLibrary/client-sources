/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.Lifecycle;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class DataResult<R>
implements App<Mu, R> {
    private final Either<R, PartialResult<R>> result;
    private final Lifecycle lifecycle;

    public static <R> DataResult<R> unbox(App<Mu, R> app) {
        return (DataResult)app;
    }

    public static <R> DataResult<R> success(R r) {
        return DataResult.success(r, Lifecycle.experimental());
    }

    public static <R> DataResult<R> error(String string, R r) {
        return DataResult.error(string, r, Lifecycle.experimental());
    }

    public static <R> DataResult<R> error(String string) {
        return DataResult.error(string, Lifecycle.experimental());
    }

    public static <R> DataResult<R> success(R r, Lifecycle lifecycle) {
        return new DataResult<R>(Either.left(r), lifecycle);
    }

    public static <R> DataResult<R> error(String string, R r, Lifecycle lifecycle) {
        return new DataResult(Either.right(new PartialResult<R>(string, Optional.of(r))), lifecycle);
    }

    public static <R> DataResult<R> error(String string, Lifecycle lifecycle) {
        return new DataResult(Either.right(new PartialResult(string, Optional.empty())), lifecycle);
    }

    public static <K, V> Function<K, DataResult<V>> partialGet(Function<K, V> function, Supplier<String> supplier) {
        return arg_0 -> DataResult.lambda$partialGet$1(function, supplier, arg_0);
    }

    private static <R> DataResult<R> create(Either<R, PartialResult<R>> either, Lifecycle lifecycle) {
        return new DataResult<R>(either, lifecycle);
    }

    private DataResult(Either<R, PartialResult<R>> either, Lifecycle lifecycle) {
        this.result = either;
        this.lifecycle = lifecycle;
    }

    public Either<R, PartialResult<R>> get() {
        return this.result;
    }

    public Optional<R> result() {
        return this.result.left();
    }

    public Lifecycle lifecycle() {
        return this.lifecycle;
    }

    public Optional<R> resultOrPartial(Consumer<String> consumer) {
        return this.result.map(Optional::of, arg_0 -> DataResult.lambda$resultOrPartial$2(consumer, arg_0));
    }

    public R getOrThrow(boolean bl, Consumer<String> consumer) {
        return (R)this.result.map(DataResult::lambda$getOrThrow$3, arg_0 -> DataResult.lambda$getOrThrow$4(consumer, bl, arg_0));
    }

    public Optional<PartialResult<R>> error() {
        return this.result.right();
    }

    public <T> DataResult<T> map(Function<? super R, ? extends T> function) {
        return DataResult.create(this.result.mapBoth(function, arg_0 -> DataResult.lambda$map$5(function, arg_0)), this.lifecycle);
    }

    public DataResult<R> promotePartial(Consumer<String> consumer) {
        return this.result.map(this::lambda$promotePartial$6, arg_0 -> this.lambda$promotePartial$9(consumer, arg_0));
    }

    private static String appendMessages(String string, String string2) {
        return string + "; " + string2;
    }

    public <R2> DataResult<R2> flatMap(Function<? super R, ? extends DataResult<R2>> function) {
        return this.result.map(arg_0 -> this.lambda$flatMap$10(function, arg_0), arg_0 -> this.lambda$flatMap$15(function, arg_0));
    }

    public <R2> DataResult<R2> ap(DataResult<Function<R, R2>> dataResult) {
        return DataResult.create(this.result.map(arg_0 -> DataResult.lambda$ap$19(dataResult, arg_0), arg_0 -> DataResult.lambda$ap$24(dataResult, arg_0)), this.lifecycle.add(dataResult.lifecycle));
    }

    public <R2, S> DataResult<S> apply2(BiFunction<R, R2, S> biFunction, DataResult<R2> dataResult) {
        return DataResult.unbox(DataResult.instance().apply2(biFunction, this, dataResult));
    }

    public <R2, S> DataResult<S> apply2stable(BiFunction<R, R2, S> biFunction, DataResult<R2> dataResult) {
        Instance instance = DataResult.instance();
        DataResult dataResult2 = DataResult.unbox(instance.point(biFunction)).setLifecycle(Lifecycle.stable());
        return DataResult.unbox(instance.ap2(dataResult2, this, dataResult));
    }

    public <R2, R3, S> DataResult<S> apply3(Function3<R, R2, R3, S> function3, DataResult<R2> dataResult, DataResult<R3> dataResult2) {
        return DataResult.unbox(DataResult.instance().apply3(function3, this, dataResult, dataResult2));
    }

    public DataResult<R> setPartial(Supplier<R> supplier) {
        return DataResult.create(this.result.mapRight(arg_0 -> DataResult.lambda$setPartial$25(supplier, arg_0)), this.lifecycle);
    }

    public DataResult<R> setPartial(R r) {
        return DataResult.create(this.result.mapRight(arg_0 -> DataResult.lambda$setPartial$26(r, arg_0)), this.lifecycle);
    }

    public DataResult<R> mapError(UnaryOperator<String> unaryOperator) {
        return DataResult.create(this.result.mapRight(arg_0 -> DataResult.lambda$mapError$27(unaryOperator, arg_0)), this.lifecycle);
    }

    public DataResult<R> setLifecycle(Lifecycle lifecycle) {
        return DataResult.create(this.result, lifecycle);
    }

    public DataResult<R> addLifecycle(Lifecycle lifecycle) {
        return DataResult.create(this.result, this.lifecycle.add(lifecycle));
    }

    public static Instance instance() {
        return Instance.INSTANCE;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        DataResult dataResult = (DataResult)object;
        return Objects.equals(this.result, dataResult.result);
    }

    public int hashCode() {
        return Objects.hash(this.result);
    }

    public String toString() {
        return "DataResult[" + this.result + ']';
    }

    private static PartialResult lambda$mapError$27(UnaryOperator unaryOperator, PartialResult partialResult) {
        return new PartialResult((String)unaryOperator.apply(PartialResult.access$400(partialResult)), PartialResult.access$500(partialResult));
    }

    private static PartialResult lambda$setPartial$26(Object object, PartialResult partialResult) {
        return new PartialResult<Object>(PartialResult.access$400(partialResult), Optional.of(object));
    }

    private static PartialResult lambda$setPartial$25(Supplier supplier, PartialResult partialResult) {
        return new PartialResult(PartialResult.access$400(partialResult), Optional.of(supplier.get()));
    }

    private static Either lambda$ap$24(DataResult dataResult, PartialResult partialResult) {
        return Either.right(dataResult.result.map(arg_0 -> DataResult.lambda$null$20(partialResult, arg_0), arg_0 -> DataResult.lambda$null$23(partialResult, arg_0)));
    }

    private static PartialResult lambda$null$23(PartialResult partialResult, PartialResult partialResult2) {
        return new PartialResult(DataResult.appendMessages(PartialResult.access$400(partialResult), PartialResult.access$400(partialResult2)), PartialResult.access$500(partialResult).flatMap(arg_0 -> DataResult.lambda$null$22(partialResult2, arg_0)));
    }

    private static Optional lambda$null$22(PartialResult partialResult, Object object) {
        return PartialResult.access$500(partialResult).map(arg_0 -> DataResult.lambda$null$21(object, arg_0));
    }

    private static Object lambda$null$21(Object object, Function function) {
        return function.apply(object);
    }

    private static PartialResult lambda$null$20(PartialResult partialResult, Function function) {
        return new PartialResult(PartialResult.access$400(partialResult), PartialResult.access$500(partialResult).map(function));
    }

    private static Either lambda$ap$19(DataResult dataResult, Object object) {
        return dataResult.result.mapBoth(arg_0 -> DataResult.lambda$null$16(object, arg_0), arg_0 -> DataResult.lambda$null$18(object, arg_0));
    }

    private static PartialResult lambda$null$18(Object object, PartialResult partialResult) {
        return new PartialResult<Object>(PartialResult.access$400(partialResult), PartialResult.access$500(partialResult).map(arg_0 -> DataResult.lambda$null$17(object, arg_0)));
    }

    private static Object lambda$null$17(Object object, Function function) {
        return function.apply(object);
    }

    private static Object lambda$null$16(Object object, Function function) {
        return function.apply(object);
    }

    private DataResult lambda$flatMap$15(Function function, PartialResult partialResult) {
        return PartialResult.access$500(partialResult).map(arg_0 -> this.lambda$null$13(function, partialResult, arg_0)).orElseGet(() -> this.lambda$null$14(partialResult));
    }

    private DataResult lambda$null$14(PartialResult partialResult) {
        return DataResult.create(Either.right(new PartialResult(PartialResult.access$400(partialResult), Optional.empty())), this.lifecycle);
    }

    private DataResult lambda$null$13(Function function, PartialResult partialResult, Object object) {
        DataResult dataResult = (DataResult)function.apply(object);
        return DataResult.create(Either.right(dataResult.get().map(arg_0 -> DataResult.lambda$null$11(partialResult, arg_0), arg_0 -> DataResult.lambda$null$12(partialResult, arg_0))), this.lifecycle.add(dataResult.lifecycle));
    }

    private static PartialResult lambda$null$12(PartialResult partialResult, PartialResult partialResult2) {
        return new PartialResult(DataResult.appendMessages(PartialResult.access$400(partialResult), PartialResult.access$400(partialResult2)), PartialResult.access$500(partialResult2));
    }

    private static PartialResult lambda$null$11(PartialResult partialResult, Object object) {
        return new PartialResult<Object>(PartialResult.access$400(partialResult), Optional.of(object));
    }

    private DataResult lambda$flatMap$10(Function function, Object object) {
        DataResult dataResult = (DataResult)function.apply(object);
        return DataResult.create(dataResult.get(), this.lifecycle.add(dataResult.lifecycle));
    }

    private DataResult lambda$promotePartial$9(Consumer consumer, PartialResult partialResult) {
        consumer.accept(PartialResult.access$400(partialResult));
        return PartialResult.access$500(partialResult).map(this::lambda$null$7).orElseGet(() -> this.lambda$null$8(partialResult));
    }

    private DataResult lambda$null$8(PartialResult partialResult) {
        return DataResult.create(Either.right(partialResult), this.lifecycle);
    }

    private DataResult lambda$null$7(Object object) {
        return new DataResult<Object>(Either.left(object), this.lifecycle);
    }

    private DataResult lambda$promotePartial$6(Object object) {
        return new DataResult<Object>(Either.left(object), this.lifecycle);
    }

    private static PartialResult lambda$map$5(Function function, PartialResult partialResult) {
        return new PartialResult(PartialResult.access$400(partialResult), PartialResult.access$500(partialResult).map(function));
    }

    private static Object lambda$getOrThrow$4(Consumer consumer, boolean bl, PartialResult partialResult) {
        consumer.accept(PartialResult.access$400(partialResult));
        if (bl && PartialResult.access$500(partialResult).isPresent()) {
            return PartialResult.access$500(partialResult).get();
        }
        throw new RuntimeException(PartialResult.access$400(partialResult));
    }

    private static Object lambda$getOrThrow$3(Object object) {
        return object;
    }

    private static Optional lambda$resultOrPartial$2(Consumer consumer, PartialResult partialResult) {
        consumer.accept(PartialResult.access$400(partialResult));
        return PartialResult.access$500(partialResult);
    }

    private static DataResult lambda$partialGet$1(Function function, Supplier supplier, Object object) {
        return Optional.ofNullable(function.apply(object)).map(DataResult::success).orElseGet(() -> DataResult.lambda$null$0((Supplier)supplier, object));
    }

    private static DataResult lambda$null$0(Supplier supplier, Object object) {
        return DataResult.error((String)supplier.get() + object);
    }

    static String access$000(String string, String string2) {
        return DataResult.appendMessages(string, string2);
    }

    static Either access$100(DataResult dataResult) {
        return dataResult.result;
    }

    static Lifecycle access$200(DataResult dataResult) {
        return dataResult.lifecycle;
    }

    DataResult(Either either, Lifecycle lifecycle, 1 var3_3) {
        this(either, lifecycle);
    }

    public static enum Instance implements Applicative<com.mojang.serialization.DataResult$Mu, Mu>
    {
        INSTANCE;


        @Override
        public <T, R> App<com.mojang.serialization.DataResult$Mu, R> map(Function<? super T, ? extends R> function, App<com.mojang.serialization.DataResult$Mu, T> app) {
            return DataResult.unbox(app).map(function);
        }

        @Override
        public <A> App<com.mojang.serialization.DataResult$Mu, A> point(A a) {
            return DataResult.success(a);
        }

        @Override
        public <A, R> Function<App<com.mojang.serialization.DataResult$Mu, A>, App<com.mojang.serialization.DataResult$Mu, R>> lift1(App<com.mojang.serialization.DataResult$Mu, Function<A, R>> app) {
            return arg_0 -> this.lambda$lift1$0(app, arg_0);
        }

        @Override
        public <A, R> App<com.mojang.serialization.DataResult$Mu, R> ap(App<com.mojang.serialization.DataResult$Mu, Function<A, R>> app, App<com.mojang.serialization.DataResult$Mu, A> app2) {
            return DataResult.unbox(app2).ap(DataResult.unbox(app));
        }

        @Override
        public <A, B, R> App<com.mojang.serialization.DataResult$Mu, R> ap2(App<com.mojang.serialization.DataResult$Mu, BiFunction<A, B, R>> app, App<com.mojang.serialization.DataResult$Mu, A> app2, App<com.mojang.serialization.DataResult$Mu, B> app3) {
            DataResult<BiFunction<A, B, R>> dataResult = DataResult.unbox(app);
            DataResult<A> dataResult2 = DataResult.unbox(app2);
            DataResult<B> dataResult3 = DataResult.unbox(app3);
            if (DataResult.access$100(dataResult).left().isPresent() && DataResult.access$100(dataResult2).left().isPresent() && DataResult.access$100(dataResult3).left().isPresent()) {
                return new DataResult(Either.left(((BiFunction)DataResult.access$100(dataResult).left().get()).apply(DataResult.access$100(dataResult2).left().get(), DataResult.access$100(dataResult3).left().get())), DataResult.access$200(dataResult).add(DataResult.access$200(dataResult2)).add(DataResult.access$200(dataResult3)), null);
            }
            return Applicative.super.ap2(app, app2, app3);
        }

        @Override
        public <T1, T2, T3, R> App<com.mojang.serialization.DataResult$Mu, R> ap3(App<com.mojang.serialization.DataResult$Mu, Function3<T1, T2, T3, R>> app, App<com.mojang.serialization.DataResult$Mu, T1> app2, App<com.mojang.serialization.DataResult$Mu, T2> app3, App<com.mojang.serialization.DataResult$Mu, T3> app4) {
            DataResult<Function3<T1, T2, T3, R>> dataResult = DataResult.unbox(app);
            DataResult<T1> dataResult2 = DataResult.unbox(app2);
            DataResult<T2> dataResult3 = DataResult.unbox(app3);
            DataResult<T3> dataResult4 = DataResult.unbox(app4);
            if (DataResult.access$100(dataResult).left().isPresent() && DataResult.access$100(dataResult2).left().isPresent() && DataResult.access$100(dataResult3).left().isPresent() && DataResult.access$100(dataResult4).left().isPresent()) {
                return new DataResult(Either.left(((Function3)DataResult.access$100(dataResult).left().get()).apply(DataResult.access$100(dataResult2).left().get(), DataResult.access$100(dataResult3).left().get(), DataResult.access$100(dataResult4).left().get())), DataResult.access$200(dataResult).add(DataResult.access$200(dataResult2)).add(DataResult.access$200(dataResult3)).add(DataResult.access$200(dataResult4)), null);
            }
            return Applicative.super.ap3(app, app2, app3, app4);
        }

        private App lambda$lift1$0(App app, App app2) {
            return this.ap(app, app2);
        }

        public static final class Mu
        implements Applicative.Mu {
        }
    }

    public static class PartialResult<R> {
        private final String message;
        private final Optional<R> partialResult;

        public PartialResult(String string, Optional<R> optional) {
            this.message = string;
            this.partialResult = optional;
        }

        public <R2> PartialResult<R2> map(Function<? super R, ? extends R2> function) {
            return new PartialResult<R2>(this.message, this.partialResult.map(function));
        }

        public <R2> PartialResult<R2> flatMap(Function<R, PartialResult<R2>> function) {
            if (this.partialResult.isPresent()) {
                PartialResult<R2> partialResult = function.apply(this.partialResult.get());
                return new PartialResult<R>(DataResult.access$000(this.message, partialResult.message), partialResult.partialResult);
            }
            return new PartialResult(this.message, Optional.empty());
        }

        public String message() {
            return this.message;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return true;
            }
            PartialResult partialResult = (PartialResult)object;
            return Objects.equals(this.message, partialResult.message) && Objects.equals(this.partialResult, partialResult.partialResult);
        }

        public int hashCode() {
            return Objects.hash(this.message, this.partialResult);
        }

        public String toString() {
            return "DynamicException[" + this.message + ' ' + this.partialResult + ']';
        }

        static String access$400(PartialResult partialResult) {
            return partialResult.message;
        }

        static Optional access$500(PartialResult partialResult) {
            return partialResult.partialResult;
        }
    }

    public static final class Mu
    implements K1 {
    }
}

