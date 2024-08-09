/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import javax.annotation.Nullable;

@GwtCompatible
final class CollectSpliterators {
    private CollectSpliterators() {
    }

    static <T> Spliterator<T> indexed(int n, int n2, IntFunction<T> intFunction) {
        return CollectSpliterators.indexed(n, n2, intFunction, null);
    }

    static <T> Spliterator<T> indexed(int n, int n2, IntFunction<T> intFunction, Comparator<? super T> comparator) {
        if (comparator != null) {
            Preconditions.checkArgument((n2 & 4) != 0);
        }
        class WithCharacteristics
        implements Spliterator<T> {
            private final Spliterator<T> delegate;
            final int val$extraCharacteristics;
            final Comparator val$comparator;

            WithCharacteristics(Spliterator<T> spliterator) {
                this.val$extraCharacteristics = n;
                this.val$comparator = var3_3;
                this.delegate = spliterator;
            }

            @Override
            public boolean tryAdvance(Consumer<? super T> consumer) {
                return this.delegate.tryAdvance(consumer);
            }

            @Override
            public void forEachRemaining(Consumer<? super T> consumer) {
                this.delegate.forEachRemaining(consumer);
            }

            @Override
            @Nullable
            public Spliterator<T> trySplit() {
                Spliterator spliterator = this.delegate.trySplit();
                return spliterator == null ? null : new WithCharacteristics(spliterator, this.val$extraCharacteristics, this.val$comparator);
            }

            @Override
            public long estimateSize() {
                return this.delegate.estimateSize();
            }

            @Override
            public int characteristics() {
                return this.delegate.characteristics() | this.val$extraCharacteristics;
            }

            @Override
            public Comparator<? super T> getComparator() {
                if (this.hasCharacteristics(1)) {
                    return this.val$comparator;
                }
                throw new IllegalStateException();
            }
        }
        return new WithCharacteristics(IntStream.range(0, n).mapToObj(intFunction).spliterator(), n2, comparator);
    }

    static <F, T> Spliterator<T> map(Spliterator<F> spliterator, Function<? super F, ? extends T> function) {
        Preconditions.checkNotNull(spliterator);
        Preconditions.checkNotNull(function);
        return new Spliterator<T>(spliterator, function){
            final Spliterator val$fromSpliterator;
            final Function val$function;
            {
                this.val$fromSpliterator = spliterator;
                this.val$function = function;
            }

            @Override
            public boolean tryAdvance(Consumer<? super T> consumer) {
                return this.val$fromSpliterator.tryAdvance(arg_0 -> 1.lambda$tryAdvance$0(consumer, this.val$function, arg_0));
            }

            @Override
            public void forEachRemaining(Consumer<? super T> consumer) {
                this.val$fromSpliterator.forEachRemaining(arg_0 -> 1.lambda$forEachRemaining$1(consumer, this.val$function, arg_0));
            }

            @Override
            public Spliterator<T> trySplit() {
                Spliterator spliterator = this.val$fromSpliterator.trySplit();
                return spliterator != null ? CollectSpliterators.map(spliterator, this.val$function) : null;
            }

            @Override
            public long estimateSize() {
                return this.val$fromSpliterator.estimateSize();
            }

            @Override
            public int characteristics() {
                return this.val$fromSpliterator.characteristics() & 0xFFFFFEFA;
            }

            private static void lambda$forEachRemaining$1(Consumer consumer, Function function, Object object) {
                consumer.accept(function.apply(object));
            }

            private static void lambda$tryAdvance$0(Consumer consumer, Function function, Object object) {
                consumer.accept(function.apply(object));
            }
        };
    }

    static <T> Spliterator<T> filter(Spliterator<T> spliterator, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(spliterator);
        Preconditions.checkNotNull(predicate);
        class Splitr
        implements Spliterator<T>,
        Consumer<T> {
            T holder;
            final Spliterator val$fromSpliterator;
            final Predicate val$predicate;

            Splitr() {
                this.val$fromSpliterator = spliterator;
                this.val$predicate = predicate;
                this.holder = null;
            }

            @Override
            public void accept(T t) {
                this.holder = t;
            }

            @Override
            public boolean tryAdvance(Consumer<? super T> consumer) {
                while (this.val$fromSpliterator.tryAdvance(this)) {
                    try {
                        if (!this.val$predicate.test(this.holder)) continue;
                        consumer.accept(this.holder);
                        boolean bl = true;
                        return bl;
                    } finally {
                        this.holder = null;
                    }
                }
                return true;
            }

            @Override
            public Spliterator<T> trySplit() {
                Spliterator spliterator = this.val$fromSpliterator.trySplit();
                return spliterator == null ? null : CollectSpliterators.filter(spliterator, this.val$predicate);
            }

            @Override
            public long estimateSize() {
                return this.val$fromSpliterator.estimateSize() / 2L;
            }

            @Override
            public Comparator<? super T> getComparator() {
                return this.val$fromSpliterator.getComparator();
            }

            @Override
            public int characteristics() {
                return this.val$fromSpliterator.characteristics() & 0x115;
            }
        }
        return new Splitr();
    }

    static <F, T> Spliterator<T> flatMap(Spliterator<F> spliterator, Function<? super F, Spliterator<T>> function, int n, long l) {
        Preconditions.checkArgument((n & 0x4000) == 0, "flatMap does not support SUBSIZED characteristic");
        Preconditions.checkArgument((n & 4) == 0, "flatMap does not support SORTED characteristic");
        Preconditions.checkNotNull(spliterator);
        Preconditions.checkNotNull(function);
        class FlatMapSpliterator
        implements Spliterator<T> {
            @Nullable
            Spliterator<T> prefix;
            final Spliterator<F> from;
            final int characteristics;
            long estimatedSize;
            final Function val$function;

            FlatMapSpliterator(Spliterator<T> spliterator, Spliterator<F> spliterator2, int n, long l) {
                this.val$function = var6_5;
                this.prefix = spliterator;
                this.from = spliterator2;
                this.characteristics = n;
                this.estimatedSize = l;
            }

            @Override
            public boolean tryAdvance(Consumer<? super T> consumer) {
                do {
                    if (this.prefix != null && this.prefix.tryAdvance(consumer)) {
                        if (this.estimatedSize != Long.MAX_VALUE) {
                            --this.estimatedSize;
                        }
                        return false;
                    }
                    this.prefix = null;
                } while (this.from.tryAdvance(arg_0 -> this.lambda$tryAdvance$0(this.val$function, arg_0)));
                return true;
            }

            @Override
            public void forEachRemaining(Consumer<? super T> consumer) {
                if (this.prefix != null) {
                    this.prefix.forEachRemaining(consumer);
                    this.prefix = null;
                }
                this.from.forEachRemaining(arg_0 -> FlatMapSpliterator.lambda$forEachRemaining$1(this.val$function, consumer, arg_0));
                this.estimatedSize = 0L;
            }

            @Override
            public Spliterator<T> trySplit() {
                Spliterator spliterator = this.from.trySplit();
                if (spliterator != null) {
                    int n = this.characteristics & 0xFFFFFFBF;
                    long l = this.estimateSize();
                    if (l < Long.MAX_VALUE) {
                        this.estimatedSize -= (l /= 2L);
                    }
                    FlatMapSpliterator flatMapSpliterator = new FlatMapSpliterator(this.prefix, spliterator, n, l, this.val$function);
                    this.prefix = null;
                    return flatMapSpliterator;
                }
                if (this.prefix != null) {
                    Spliterator spliterator2 = this.prefix;
                    this.prefix = null;
                    return spliterator2;
                }
                return null;
            }

            @Override
            public long estimateSize() {
                if (this.prefix != null) {
                    this.estimatedSize = Math.max(this.estimatedSize, this.prefix.estimateSize());
                }
                return Math.max(this.estimatedSize, 0L);
            }

            @Override
            public int characteristics() {
                return this.characteristics;
            }

            private static void lambda$forEachRemaining$1(Function function, Consumer consumer, Object object) {
                ((Spliterator)function.apply(object)).forEachRemaining(consumer);
            }

            private void lambda$tryAdvance$0(Function function, Object object) {
                this.prefix = (Spliterator)function.apply(object);
            }
        }
        return new FlatMapSpliterator(null, spliterator, n, l, function);
    }
}

