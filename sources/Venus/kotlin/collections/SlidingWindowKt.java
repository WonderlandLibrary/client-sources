/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyIterator;
import kotlin.collections.RingBuffer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0000\u001aH\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00070\u0006\"\u0004\b\u0000\u0010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\b0\u00062\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0000\u001aD\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00070\u000e\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u000e2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0000\u00a8\u0006\u000f"}, d2={"checkWindowSizeStep", "", "size", "", "step", "windowedIterator", "", "", "T", "iterator", "partialWindows", "", "reuseBuffer", "windowedSequence", "Lkotlin/sequences/Sequence;", "kotlin-stdlib"})
public final class SlidingWindowKt {
    public static final void checkWindowSizeStep(int n, int n2) {
        boolean bl;
        boolean bl2 = bl = n > 0 && n2 > 0;
        if (!bl) {
            boolean bl3 = false;
            String string = n != n2 ? "Both size " + n + " and step " + n2 + " must be greater than zero." : "size " + n + " must be greater than zero.";
            throw new IllegalArgumentException(string.toString());
        }
    }

    @NotNull
    public static final <T> Sequence<List<T>> windowedSequence(@NotNull Sequence<? extends T> sequence, int n, int n2, boolean bl, boolean bl2) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        SlidingWindowKt.checkWindowSizeStep(n, n2);
        return new Sequence<List<? extends T>>(sequence, n, n2, bl, bl2){
            final Sequence $this_windowedSequence$inlined;
            final int $size$inlined;
            final int $step$inlined;
            final boolean $partialWindows$inlined;
            final boolean $reuseBuffer$inlined;
            {
                this.$this_windowedSequence$inlined = sequence;
                this.$size$inlined = n;
                this.$step$inlined = n2;
                this.$partialWindows$inlined = bl;
                this.$reuseBuffer$inlined = bl2;
            }

            @NotNull
            public Iterator<List<? extends T>> iterator() {
                boolean bl = false;
                return SlidingWindowKt.windowedIterator(this.$this_windowedSequence$inlined.iterator(), this.$size$inlined, this.$step$inlined, this.$partialWindows$inlined, this.$reuseBuffer$inlined);
            }
        };
    }

    @NotNull
    public static final <T> Iterator<List<T>> windowedIterator(@NotNull Iterator<? extends T> iterator2, int n, int n2, boolean bl, boolean bl2) {
        Intrinsics.checkNotNullParameter(iterator2, "iterator");
        if (!iterator2.hasNext()) {
            return EmptyIterator.INSTANCE;
        }
        return SequencesKt.iterator(new Function2<SequenceScope<? super List<? extends T>>, Continuation<? super Unit>, Object>(n, n2, iterator2, bl2, bl, null){
            Object L$1;
            Object L$2;
            int I$0;
            int label;
            private Object L$0;
            final int $size;
            final int $step;
            final Iterator<T> $iterator;
            final boolean $reuseBuffer;
            final boolean $partialWindows;
            {
                this.$size = n;
                this.$step = n2;
                this.$iterator = iterator2;
                this.$reuseBuffer = bl;
                this.$partialWindows = bl2;
                super(2, continuation);
            }

            /*
             * Unable to fully structure code
             */
            @Nullable
            public final Object invokeSuspend(@NotNull Object var1_1) {
                var9_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0: {
                        ResultKt.throwOnFailure(var1_1);
                        var2_3 = (SequenceScope)this.L$0;
                        var3_4 = RangesKt.coerceAtMost(this.$size, 1024);
                        var4_5 = this.$step - this.$size;
                        if (var4_5 < 0) break;
                        var5_6 = new ArrayList<T>(var3_4);
                        var6_8 = 0;
                        var7_10 = this.$iterator;
lbl12:
                        // 4 sources

                        while (var7_10.hasNext()) {
                            var8_12 = var7_10.next();
                            if (var6_8 > 0) {
                                --var6_8;
                                continue;
                            }
                            var5_6.add(var8_12);
                            if (var5_6.size() != this.$size) continue;
                            this.L$0 = var2_3;
                            this.L$1 = var5_6;
                            this.L$2 = var7_10;
                            this.I$0 = var4_5;
                            this.label = 1;
                            v0 = var2_3.yield(var5_6, this);
                            if (v0 == var9_2) {
                                return var9_2;
                            }
                            ** GOTO lbl37
                        }
                        break;
                    }
                    case 1: {
                        var4_5 = this.I$0;
                        var7_10 = (Iterator<T>)this.L$2;
                        var5_6 = (ArrayList<T>)this.L$1;
                        var2_3 = (SequenceScope)this.L$0;
                        ResultKt.throwOnFailure(var1_1);
                        v0 = var1_1;
lbl37:
                        // 2 sources

                        if (this.$reuseBuffer) {
                            var5_6.clear();
                        } else {
                            var5_6 = new ArrayList<E>(this.$size);
                        }
                        var6_8 = var4_5;
                        ** GOTO lbl12
                    }
                }
                if (((Collection)var5_6).isEmpty() == false && (this.$partialWindows || var5_6.size() == this.$size)) {
                    this.L$0 = null;
                    this.L$1 = null;
                    this.L$2 = null;
                    this.label = 2;
                    v1 = var2_3.yield(var5_6, this);
                    if (v1 == var9_2) {
                        return var9_2;
                    }
                }
                ** GOTO lbl112
                {
                    case 2: {
                        ResultKt.throwOnFailure(var1_1);
                        v1 = var1_1;
                        ** GOTO lbl112
                    }
                }
                var5_7 = new RingBuffer<E>(var3_4);
                var6_9 = this.$iterator;
lbl58:
                // 4 sources

                while (var6_9.hasNext()) {
                    var7_11 = var6_9.next();
                    var5_7.add(var7_11);
                    if (!var5_7.isFull()) continue;
                    if (var5_7.size() < this.$size) {
                        var5_7 = var5_7.expanded(this.$size);
                        continue;
                    }
                    this.L$0 = var2_3;
                    this.L$1 = var5_7;
                    this.L$2 = var6_9;
                    this.label = 3;
                    v2 = var2_3.yield(this.$reuseBuffer != false ? (List)var5_7 : (List)new ArrayList<E>((Collection)var5_7), this);
                    if (v2 == var9_2) {
                        return var9_2;
                    }
                    ** GOTO lbl80
                }
                {
                    break;
                    case 3: {
                        var6_9 = (Iterator<T>)this.L$2;
                        var5_7 = (RingBuffer)this.L$1;
                        var2_3 = (SequenceScope)this.L$0;
                        ResultKt.throwOnFailure(var1_1);
                        v2 = var1_1;
lbl80:
                        // 2 sources

                        var5_7.removeFirst(this.$step);
                        ** GOTO lbl58
                    }
                }
                if (!this.$partialWindows) ** GOTO lbl112
lbl83:
                // 2 sources

                while (var5_7.size() > this.$step) {
                    this.L$0 = var2_3;
                    this.L$1 = var5_7;
                    this.L$2 = null;
                    this.label = 4;
                    v3 = var2_3.yield(this.$reuseBuffer != false ? (List)var5_7 : (List)new ArrayList<E>((Collection)var5_7), this);
                    if (v3 == var9_2) {
                        return var9_2;
                    }
                    ** GOTO lbl98
                }
                {
                    break;
                    case 4: {
                        var5_7 = (RingBuffer<E>)this.L$1;
                        var2_3 = (SequenceScope)this.L$0;
                        ResultKt.throwOnFailure(var1_1);
                        v3 = var1_1;
lbl98:
                        // 2 sources

                        var5_7.removeFirst(this.$step);
                        ** GOTO lbl83
                    }
                }
                if (((Collection)var5_7).isEmpty() == false) {
                    this.L$0 = null;
                    this.L$1 = null;
                    this.L$2 = null;
                    this.label = 5;
                    v4 = var2_3.yield(var5_7, this);
                    if (v4 == var9_2) {
                        return var9_2;
                    }
                }
                ** GOTO lbl112
                {
                    case 5: {
                        ResultKt.throwOnFailure(var1_1);
                        v4 = var1_1;
lbl112:
                        // 5 sources

                        return Unit.INSTANCE;
                    }
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            @NotNull
            public final Continuation<Unit> create(@Nullable Object object, @NotNull Continuation<?> continuation) {
                Function2<SequenceScope<? super List<? extends T>>, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                function2.L$0 = object;
                return (Continuation)((Object)function2);
            }

            @Nullable
            public final Object invoke(@NotNull SequenceScope<? super List<? extends T>> sequenceScope, @Nullable Continuation<? super Unit> continuation) {
                return (this.create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            public Object invoke(Object object, Object object2) {
                return this.invoke((SequenceScope)object, (Continuation)object2);
            }
        });
    }
}

