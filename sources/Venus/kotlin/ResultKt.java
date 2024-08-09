/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin;

import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.Result;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u0000:\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u001a.\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00060\bH\u0087\b\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010\t\u001a\u0087\u0001\u0010\n\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006\"\u0004\b\u0001\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\f\u001a\u001d\u0012\u0013\u0012\u0011H\u000b\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u00060\r2!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u0002H\u00060\rH\u0087\b\u00f8\u0001\u0000\u00f8\u0001\u0001\u0082\u0002\u0014\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0000\u00a2\u0006\u0002\u0010\u0012\u001a3\u0010\u0013\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006\"\b\b\u0001\u0010\u000b*\u0002H\u0006*\b\u0012\u0004\u0012\u0002H\u000b0\u00052\u0006\u0010\u0014\u001a\u0002H\u0006H\u0087\b\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010\u0015\u001a^\u0010\u0016\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006\"\b\b\u0001\u0010\u000b*\u0002H\u0006*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u0002H\u00060\rH\u0087\b\u00f8\u0001\u0000\u00f8\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000\u00a2\u0006\u0002\u0010\u0017\u001a!\u0010\u0018\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u0005H\u0087\b\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010\u0019\u001a`\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u0006\"\u0004\b\u0001\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u0011H\u000b\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u00060\rH\u0087\b\u00f8\u0001\u0000\u00f8\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000\u00a2\u0006\u0002\u0010\u0017\u001aS\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u0006\"\u0004\b\u0001\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u0011H\u000b\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u00060\rH\u0087\b\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010\u0017\u001aZ\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0005\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001d\u001a\u001d\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u00020\u001e0\rH\u0087\b\u00f8\u0001\u0000\u00f8\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000\u00a2\u0006\u0002\u0010\u0017\u001aZ\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0005\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001d\u001a\u001d\u0012\u0013\u0012\u0011H\u000b\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u001e0\rH\u0087\b\u00f8\u0001\u0000\u00f8\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000\u00a2\u0006\u0002\u0010\u0017\u001ad\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u0006\"\b\b\u0001\u0010\u000b*\u0002H\u0006*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u0002H\u00060\rH\u0087\b\u00f8\u0001\u0000\u00f8\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000\u00a2\u0006\u0002\u0010\u0017\u001aW\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u0006\"\b\b\u0001\u0010\u000b*\u0002H\u0006*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u0002H\u00060\rH\u0087\b\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010\u0017\u001aC\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\u0006*\u0002H\u000b2\u0017\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\u00060\r\u00a2\u0006\u0002\b!H\u0087\b\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010\u0017\u001a\u0018\u0010\"\u001a\u00020\u001e*\u0006\u0012\u0002\b\u00030\u0005H\u0001\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010#\u0082\u0002\u000b\n\u0005\b\u009920\u0001\n\u0002\b\u0019\u00a8\u0006$"}, d2={"createFailure", "", "exception", "", "runCatching", "Lkotlin/Result;", "R", "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "fold", "T", "onSuccess", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "value", "onFailure", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "getOrDefault", "defaultValue", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "getOrElse", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "getOrThrow", "(Ljava/lang/Object;)Ljava/lang/Object;", "map", "transform", "mapCatching", "action", "", "recover", "recoverCatching", "Lkotlin/ExtensionFunctionType;", "throwOnFailure", "(Ljava/lang/Object;)V", "kotlin-stdlib"})
@SourceDebugExtension(value={"SMAP\nResult.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Result.kt\nkotlin/ResultKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,340:1\n1#2:341\n*E\n"})
public final class ResultKt {
    @PublishedApi
    @SinceKotlin(version="1.3")
    @NotNull
    public static final Object createFailure(@NotNull Throwable throwable) {
        Intrinsics.checkNotNullParameter(throwable, "exception");
        return new Result.Failure(throwable);
    }

    @PublishedApi
    @SinceKotlin(version="1.3")
    public static final void throwOnFailure(@NotNull Object object) {
        if (object instanceof Result.Failure) {
            throw ((Result.Failure)object).exception;
        }
    }

    @InlineOnly
    @SinceKotlin(version="1.3")
    private static final <R> Object runCatching(Function0<? extends R> function0) {
        Object object;
        Intrinsics.checkNotNullParameter(function0, "block");
        try {
            object = Result.constructor-impl(function0.invoke());
        } catch (Throwable throwable) {
            object = Result.constructor-impl(ResultKt.createFailure(throwable));
        }
        return object;
    }

    @InlineOnly
    @SinceKotlin(version="1.3")
    private static final <T, R> Object runCatching(T t, Function1<? super T, ? extends R> function1) {
        Object object;
        Intrinsics.checkNotNullParameter(function1, "block");
        try {
            object = Result.constructor-impl(function1.invoke(t));
        } catch (Throwable throwable) {
            object = Result.constructor-impl(ResultKt.createFailure(throwable));
        }
        return object;
    }

    @InlineOnly
    @SinceKotlin(version="1.3")
    private static final <T> T getOrThrow(Object object) {
        ResultKt.throwOnFailure(object);
        return (T)object;
    }

    @InlineOnly
    @SinceKotlin(version="1.3")
    private static final <R, T extends R> R getOrElse(Object object, Function1<? super Throwable, ? extends R> function1) {
        Intrinsics.checkNotNullParameter(function1, "onFailure");
        Throwable throwable = Result.exceptionOrNull-impl(object);
        return (R)(throwable == null ? object : function1.invoke(throwable));
    }

    @InlineOnly
    @SinceKotlin(version="1.3")
    private static final <R, T extends R> R getOrDefault(Object object, R r) {
        if (Result.isFailure-impl(object)) {
            return r;
        }
        return (R)object;
    }

    @InlineOnly
    @SinceKotlin(version="1.3")
    private static final <R, T> R fold(Object object, Function1<? super T, ? extends R> function1, Function1<? super Throwable, ? extends R> function12) {
        Intrinsics.checkNotNullParameter(function1, "onSuccess");
        Intrinsics.checkNotNullParameter(function12, "onFailure");
        Throwable throwable = Result.exceptionOrNull-impl(object);
        return throwable == null ? function1.invoke(object) : function12.invoke(throwable);
    }

    @InlineOnly
    @SinceKotlin(version="1.3")
    private static final <R, T> Object map(Object object, Function1<? super T, ? extends R> function1) {
        Intrinsics.checkNotNullParameter(function1, "transform");
        return Result.isSuccess-impl(object) ? Result.constructor-impl(function1.invoke(object)) : Result.constructor-impl(object);
    }

    @InlineOnly
    @SinceKotlin(version="1.3")
    private static final <R, T> Object mapCatching(Object object, Function1<? super T, ? extends R> function1) {
        Object object2;
        Intrinsics.checkNotNullParameter(function1, "transform");
        if (Result.isSuccess-impl(object)) {
            Object object3;
            Object object4 = object;
            try {
                object3 = object4;
                boolean bl = false;
                object3 = Result.constructor-impl(function1.invoke(object3));
            } catch (Throwable throwable) {
                object3 = Result.constructor-impl(ResultKt.createFailure(throwable));
            }
            object2 = object3;
        } else {
            object2 = Result.constructor-impl(object);
        }
        return object2;
    }

    @InlineOnly
    @SinceKotlin(version="1.3")
    private static final <R, T extends R> Object recover(Object object, Function1<? super Throwable, ? extends R> function1) {
        Intrinsics.checkNotNullParameter(function1, "transform");
        Throwable throwable = Result.exceptionOrNull-impl(object);
        return throwable == null ? object : Result.constructor-impl(function1.invoke(throwable));
    }

    @InlineOnly
    @SinceKotlin(version="1.3")
    private static final <R, T extends R> Object recoverCatching(Object object, Function1<? super Throwable, ? extends R> function1) {
        Object object2;
        Intrinsics.checkNotNullParameter(function1, "transform");
        Throwable throwable = Result.exceptionOrNull-impl(object);
        if (throwable == null) {
            object2 = object;
        } else {
            Object object3;
            Object object4 = object;
            try {
                object3 = object4;
                boolean bl = false;
                object3 = Result.constructor-impl(function1.invoke(throwable));
            } catch (Throwable throwable2) {
                object3 = Result.constructor-impl(ResultKt.createFailure(throwable2));
            }
            object2 = object3;
        }
        return object2;
    }

    @InlineOnly
    @SinceKotlin(version="1.3")
    private static final <T> Object onFailure(Object object, Function1<? super Throwable, Unit> function1) {
        block0: {
            Throwable throwable;
            Intrinsics.checkNotNullParameter(function1, "action");
            Throwable throwable2 = Result.exceptionOrNull-impl(object);
            if (throwable2 == null) break block0;
            Throwable throwable3 = throwable = throwable2;
            boolean bl = false;
            function1.invoke(throwable3);
        }
        return object;
    }

    @InlineOnly
    @SinceKotlin(version="1.3")
    private static final <T> Object onSuccess(Object object, Function1<? super T, Unit> function1) {
        Intrinsics.checkNotNullParameter(function1, "action");
        if (Result.isSuccess-impl(object)) {
            function1.invoke(object);
        }
        return object;
    }
}

