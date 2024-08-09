/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.coroutines.intrinsics;

import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.ResultKt;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.RestrictedContinuationImpl;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\u001aF\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00012\u001c\b\u0004\u0010\u0005\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006H\u0083\b\u00a2\u0006\u0002\b\b\u001a'\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0002\u00a2\u0006\u0002\b\n\u001aD\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\u0003*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\f\u001a]\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u0003*#\b\u0001\u0012\u0004\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u000e\u00a2\u0006\u0002\b\u000f2\u0006\u0010\u0010\u001a\u0002H\r2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011\u001a\u001e\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007\u001aA\u0010\u0013\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0003*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014\u001aZ\u0010\u0013\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u0003*#\b\u0001\u0012\u0004\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u000e\u00a2\u0006\u0002\b\u000f2\u0006\u0010\u0010\u001a\u0002H\r2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015\u001an\u0010\u0013\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u0016\"\u0004\b\u0002\u0010\u0003*)\b\u0001\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0017\u00a2\u0006\u0002\b\u000f2\u0006\u0010\u0010\u001a\u0002H\r2\u0006\u0010\u0018\u001a\u0002H\u00162\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0081\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019\u001a@\u0010\u001a\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0003*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0001\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014\u001aY\u0010\u001a\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u0003*#\b\u0001\u0012\u0004\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u000e\u00a2\u0006\u0002\b\u000f2\u0006\u0010\u0010\u001a\u0002H\r2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0001\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015\u001am\u0010\u001a\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u0016\"\u0004\b\u0002\u0010\u0003*)\b\u0001\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0017\u00a2\u0006\u0002\b\u000f2\u0006\u0010\u0010\u001a\u0002H\r2\u0006\u0010\u0018\u001a\u0002H\u00162\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0001\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001b"}, d2={"createCoroutineFromSuspendFunction", "Lkotlin/coroutines/Continuation;", "", "T", "completion", "block", "Lkotlin/Function1;", "", "createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt", "createSimpleCoroutineForSuspendFunction", "createSimpleCoroutineForSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt", "createCoroutineUnintercepted", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "R", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "intercepted", "startCoroutineUninterceptedOrReturn", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "P", "Lkotlin/Function3;", "param", "(Lkotlin/jvm/functions/Function3;Ljava/lang/Object;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "wrapWithContinuationImpl", "kotlin-stdlib"}, xs="kotlin/coroutines/intrinsics/IntrinsicsKt")
@SourceDebugExtension(value={"SMAP\nIntrinsicsJvm.kt\nKotlin\n*S Kotlin\n*F\n+ 1 IntrinsicsJvm.kt\nkotlin/coroutines/intrinsics/IntrinsicsKt__IntrinsicsJvmKt\n*L\n1#1,269:1\n204#1,4:270\n225#1:274\n204#1,4:275\n225#1:279\n*S KotlinDebug\n*F\n+ 1 IntrinsicsJvm.kt\nkotlin/coroutines/intrinsics/IntrinsicsKt__IntrinsicsJvmKt\n*L\n130#1:270,4\n130#1:274\n165#1:275,4\n165#1:279\n*E\n"})
class IntrinsicsKt__IntrinsicsJvmKt {
    @SinceKotlin(version="1.3")
    @InlineOnly
    private static final <T> Object startCoroutineUninterceptedOrReturn(Function1<? super Continuation<? super T>, ? extends Object> function1, Continuation<? super T> continuation) {
        Intrinsics.checkNotNullParameter(function1, "<this>");
        Intrinsics.checkNotNullParameter(continuation, "completion");
        return !(function1 instanceof BaseContinuationImpl) ? IntrinsicsKt.wrapWithContinuationImpl(function1, continuation) : ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(function1, 1)).invoke(continuation);
    }

    @PublishedApi
    @Nullable
    public static final <T> Object wrapWithContinuationImpl(@NotNull Function1<? super Continuation<? super T>, ? extends Object> function1, @NotNull Continuation<? super T> continuation) {
        Intrinsics.checkNotNullParameter(function1, "<this>");
        Intrinsics.checkNotNullParameter(continuation, "completion");
        Continuation<? super T> continuation2 = IntrinsicsKt__IntrinsicsJvmKt.createSimpleCoroutineForSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt(DebugProbesKt.probeCoroutineCreated(continuation));
        return ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(function1, 1)).invoke(continuation2);
    }

    @SinceKotlin(version="1.3")
    @InlineOnly
    private static final <R, T> Object startCoroutineUninterceptedOrReturn(Function2<? super R, ? super Continuation<? super T>, ? extends Object> function2, R r, Continuation<? super T> continuation) {
        Intrinsics.checkNotNullParameter(function2, "<this>");
        Intrinsics.checkNotNullParameter(continuation, "completion");
        return !(function2 instanceof BaseContinuationImpl) ? IntrinsicsKt.wrapWithContinuationImpl(function2, r, continuation) : ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(function2, 2)).invoke(r, continuation);
    }

    @PublishedApi
    @Nullable
    public static final <R, T> Object wrapWithContinuationImpl(@NotNull Function2<? super R, ? super Continuation<? super T>, ? extends Object> function2, R r, @NotNull Continuation<? super T> continuation) {
        Intrinsics.checkNotNullParameter(function2, "<this>");
        Intrinsics.checkNotNullParameter(continuation, "completion");
        Continuation<? super T> continuation2 = IntrinsicsKt__IntrinsicsJvmKt.createSimpleCoroutineForSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt(DebugProbesKt.probeCoroutineCreated(continuation));
        return ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(function2, 2)).invoke(r, continuation2);
    }

    @InlineOnly
    private static final <R, P, T> Object startCoroutineUninterceptedOrReturn(Function3<? super R, ? super P, ? super Continuation<? super T>, ? extends Object> function3, R r, P p, Continuation<? super T> continuation) {
        Intrinsics.checkNotNullParameter(function3, "<this>");
        Intrinsics.checkNotNullParameter(continuation, "completion");
        return !(function3 instanceof BaseContinuationImpl) ? IntrinsicsKt.wrapWithContinuationImpl(function3, r, p, continuation) : ((Function3)TypeIntrinsics.beforeCheckcastToFunctionOfArity(function3, 3)).invoke(r, p, continuation);
    }

    @PublishedApi
    @Nullable
    public static final <R, P, T> Object wrapWithContinuationImpl(@NotNull Function3<? super R, ? super P, ? super Continuation<? super T>, ? extends Object> function3, R r, P p, @NotNull Continuation<? super T> continuation) {
        Intrinsics.checkNotNullParameter(function3, "<this>");
        Intrinsics.checkNotNullParameter(continuation, "completion");
        Continuation<? super T> continuation2 = IntrinsicsKt__IntrinsicsJvmKt.createSimpleCoroutineForSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt(DebugProbesKt.probeCoroutineCreated(continuation));
        return ((Function3)TypeIntrinsics.beforeCheckcastToFunctionOfArity(function3, 3)).invoke(r, p, continuation2);
    }

    @SinceKotlin(version="1.3")
    @NotNull
    public static final <T> Continuation<Unit> createCoroutineUnintercepted(@NotNull Function1<? super Continuation<? super T>, ? extends Object> function1, @NotNull Continuation<? super T> continuation) {
        Continuation continuation2;
        Intrinsics.checkNotNullParameter(function1, "<this>");
        Intrinsics.checkNotNullParameter(continuation, "completion");
        Continuation<T> continuation3 = DebugProbesKt.probeCoroutineCreated(continuation);
        if (function1 instanceof BaseContinuationImpl) {
            continuation2 = ((BaseContinuationImpl)((Object)function1)).create(continuation3);
        } else {
            boolean bl = false;
            CoroutineContext coroutineContext = continuation3.getContext();
            continuation2 = coroutineContext == EmptyCoroutineContext.INSTANCE ? (Continuation)new RestrictedContinuationImpl(continuation3, function1){
                private int label;
                final Function1 $this_createCoroutineUnintercepted$inlined;
                {
                    this.$this_createCoroutineUnintercepted$inlined = function1;
                    Intrinsics.checkNotNull(continuation, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
                    super(continuation);
                }

                @Nullable
                protected Object invokeSuspend(@NotNull Object object) {
                    Object object2;
                    switch (this.label) {
                        case 0: {
                            this.label = 1;
                            ResultKt.throwOnFailure(object);
                            Continuation continuation = this;
                            boolean bl = false;
                            Intrinsics.checkNotNull(this.$this_createCoroutineUnintercepted$inlined, "null cannot be cast to non-null type kotlin.Function1<kotlin.coroutines.Continuation<T of kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted$lambda$0>, kotlin.Any?>");
                            object2 = ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(this.$this_createCoroutineUnintercepted$inlined, 1)).invoke(continuation);
                            break;
                        }
                        case 1: {
                            this.label = 2;
                            Object object3 = object;
                            ResultKt.throwOnFailure(object3);
                            object2 = object3;
                            break;
                        }
                        default: {
                            throw new IllegalStateException("This coroutine had already completed".toString());
                        }
                    }
                    return object2;
                }
            } : (Continuation)new ContinuationImpl(continuation3, coroutineContext, function1){
                private int label;
                final Function1 $this_createCoroutineUnintercepted$inlined;
                {
                    this.$this_createCoroutineUnintercepted$inlined = function1;
                    Intrinsics.checkNotNull(continuation, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
                    super(continuation, coroutineContext);
                }

                @Nullable
                protected Object invokeSuspend(@NotNull Object object) {
                    Object object2;
                    switch (this.label) {
                        case 0: {
                            this.label = 1;
                            ResultKt.throwOnFailure(object);
                            Continuation continuation = this;
                            boolean bl = false;
                            Intrinsics.checkNotNull(this.$this_createCoroutineUnintercepted$inlined, "null cannot be cast to non-null type kotlin.Function1<kotlin.coroutines.Continuation<T of kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted$lambda$0>, kotlin.Any?>");
                            object2 = ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(this.$this_createCoroutineUnintercepted$inlined, 1)).invoke(continuation);
                            break;
                        }
                        case 1: {
                            this.label = 2;
                            Object object3 = object;
                            ResultKt.throwOnFailure(object3);
                            object2 = object3;
                            break;
                        }
                        default: {
                            throw new IllegalStateException("This coroutine had already completed".toString());
                        }
                    }
                    return object2;
                }
            };
        }
        return continuation2;
    }

    @SinceKotlin(version="1.3")
    @NotNull
    public static final <R, T> Continuation<Unit> createCoroutineUnintercepted(@NotNull Function2<? super R, ? super Continuation<? super T>, ? extends Object> function2, R r, @NotNull Continuation<? super T> continuation) {
        Continuation continuation2;
        Intrinsics.checkNotNullParameter(function2, "<this>");
        Intrinsics.checkNotNullParameter(continuation, "completion");
        Continuation<T> continuation3 = DebugProbesKt.probeCoroutineCreated(continuation);
        if (function2 instanceof BaseContinuationImpl) {
            continuation2 = ((BaseContinuationImpl)((Object)function2)).create(r, continuation3);
        } else {
            boolean bl = false;
            CoroutineContext coroutineContext = continuation3.getContext();
            continuation2 = coroutineContext == EmptyCoroutineContext.INSTANCE ? (Continuation)new RestrictedContinuationImpl(continuation3, function2, r){
                private int label;
                final Function2 $this_createCoroutineUnintercepted$inlined;
                final Object $receiver$inlined;
                {
                    this.$this_createCoroutineUnintercepted$inlined = function2;
                    this.$receiver$inlined = object;
                    Intrinsics.checkNotNull(continuation, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
                    super(continuation);
                }

                @Nullable
                protected Object invokeSuspend(@NotNull Object object) {
                    Object object2;
                    switch (this.label) {
                        case 0: {
                            this.label = 1;
                            ResultKt.throwOnFailure(object);
                            Continuation continuation = this;
                            boolean bl = false;
                            Intrinsics.checkNotNull(this.$this_createCoroutineUnintercepted$inlined, "null cannot be cast to non-null type kotlin.Function2<R of kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted$lambda$1, kotlin.coroutines.Continuation<T of kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted$lambda$1>, kotlin.Any?>");
                            object2 = ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(this.$this_createCoroutineUnintercepted$inlined, 2)).invoke(this.$receiver$inlined, continuation);
                            break;
                        }
                        case 1: {
                            this.label = 2;
                            Object object3 = object;
                            ResultKt.throwOnFailure(object3);
                            object2 = object3;
                            break;
                        }
                        default: {
                            throw new IllegalStateException("This coroutine had already completed".toString());
                        }
                    }
                    return object2;
                }
            } : (Continuation)new ContinuationImpl(continuation3, coroutineContext, function2, r){
                private int label;
                final Function2 $this_createCoroutineUnintercepted$inlined;
                final Object $receiver$inlined;
                {
                    this.$this_createCoroutineUnintercepted$inlined = function2;
                    this.$receiver$inlined = object;
                    Intrinsics.checkNotNull(continuation, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
                    super(continuation, coroutineContext);
                }

                @Nullable
                protected Object invokeSuspend(@NotNull Object object) {
                    Object object2;
                    switch (this.label) {
                        case 0: {
                            this.label = 1;
                            ResultKt.throwOnFailure(object);
                            Continuation continuation = this;
                            boolean bl = false;
                            Intrinsics.checkNotNull(this.$this_createCoroutineUnintercepted$inlined, "null cannot be cast to non-null type kotlin.Function2<R of kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted$lambda$1, kotlin.coroutines.Continuation<T of kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted$lambda$1>, kotlin.Any?>");
                            object2 = ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(this.$this_createCoroutineUnintercepted$inlined, 2)).invoke(this.$receiver$inlined, continuation);
                            break;
                        }
                        case 1: {
                            this.label = 2;
                            Object object3 = object;
                            ResultKt.throwOnFailure(object3);
                            object2 = object3;
                            break;
                        }
                        default: {
                            throw new IllegalStateException("This coroutine had already completed".toString());
                        }
                    }
                    return object2;
                }
            };
        }
        return continuation2;
    }

    @SinceKotlin(version="1.3")
    @NotNull
    public static final <T> Continuation<T> intercepted(@NotNull Continuation<? super T> continuation) {
        Intrinsics.checkNotNullParameter(continuation, "<this>");
        Continuation<Object> continuation2 = continuation instanceof ContinuationImpl ? (ContinuationImpl)continuation : null;
        if (continuation2 == null || (continuation2 = continuation2.intercepted()) == null) {
            continuation2 = continuation;
        }
        return continuation2;
    }

    @SinceKotlin(version="1.3")
    private static final <T> Continuation<Unit> createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt(Continuation<? super T> continuation, Function1<? super Continuation<? super T>, ? extends Object> function1) {
        boolean bl = false;
        CoroutineContext coroutineContext = continuation.getContext();
        return coroutineContext == EmptyCoroutineContext.INSTANCE ? (Continuation)new RestrictedContinuationImpl(continuation, function1){
            private int label;
            final Function1<Continuation<? super T>, Object> $block;
            {
                this.$block = function1;
                Intrinsics.checkNotNull(continuation, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
                super(continuation);
            }

            @Nullable
            protected Object invokeSuspend(@NotNull Object object) {
                Object object2;
                switch (this.label) {
                    case 0: {
                        this.label = 1;
                        Object object3 = object;
                        ResultKt.throwOnFailure(object3);
                        object2 = this.$block.invoke(this);
                        break;
                    }
                    case 1: {
                        this.label = 2;
                        Object object4 = object;
                        ResultKt.throwOnFailure(object4);
                        object2 = object4;
                        break;
                    }
                    default: {
                        throw new IllegalStateException("This coroutine had already completed".toString());
                    }
                }
                return object2;
            }
        } : (Continuation)new ContinuationImpl(continuation, coroutineContext, function1){
            private int label;
            final Function1<Continuation<? super T>, Object> $block;
            {
                this.$block = function1;
                Intrinsics.checkNotNull(continuation, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
                super(continuation, coroutineContext);
            }

            @Nullable
            protected Object invokeSuspend(@NotNull Object object) {
                Object object2;
                switch (this.label) {
                    case 0: {
                        this.label = 1;
                        Object object3 = object;
                        ResultKt.throwOnFailure(object3);
                        object2 = this.$block.invoke(this);
                        break;
                    }
                    case 1: {
                        this.label = 2;
                        Object object4 = object;
                        ResultKt.throwOnFailure(object4);
                        object2 = object4;
                        break;
                    }
                    default: {
                        throw new IllegalStateException("This coroutine had already completed".toString());
                    }
                }
                return object2;
            }
        };
    }

    private static final <T> Continuation<T> createSimpleCoroutineForSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt(Continuation<? super T> continuation) {
        CoroutineContext coroutineContext = continuation.getContext();
        return coroutineContext == EmptyCoroutineContext.INSTANCE ? (Continuation)new RestrictedContinuationImpl(continuation){
            {
                Intrinsics.checkNotNull(continuation, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
                super(continuation);
            }

            @Nullable
            protected Object invokeSuspend(@NotNull Object object) {
                Object object2 = object;
                ResultKt.throwOnFailure(object2);
                return object2;
            }
        } : (Continuation)new ContinuationImpl(continuation, coroutineContext){
            {
                Intrinsics.checkNotNull(continuation, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
                super(continuation, coroutineContext);
            }

            @Nullable
            protected Object invokeSuspend(@NotNull Object object) {
                Object object2 = object;
                ResultKt.throwOnFailure(object2);
                return object2;
            }
        };
    }
}

