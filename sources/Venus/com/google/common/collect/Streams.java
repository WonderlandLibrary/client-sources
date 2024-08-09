/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.CollectSpliterators;
import com.google.common.collect.ImmutableList;
import com.google.common.math.LongMath;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;

@Beta
@GwtCompatible
public final class Streams {
    public static <T> Stream<T> stream(Iterable<T> iterable) {
        return iterable instanceof Collection ? ((Collection)iterable).stream() : StreamSupport.stream(iterable.spliterator(), false);
    }

    @Deprecated
    public static <T> Stream<T> stream(Collection<T> collection) {
        return collection.stream();
    }

    public static <T> Stream<T> stream(Iterator<T> iterator2) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator2, 0), false);
    }

    public static <T> Stream<T> stream(com.google.common.base.Optional<T> optional) {
        return optional.isPresent() ? Stream.of(optional.get()) : Stream.of(new Object[0]);
    }

    public static <T> Stream<T> stream(Optional<T> optional) {
        return optional.isPresent() ? Stream.of(optional.get()) : Stream.of(new Object[0]);
    }

    @SafeVarargs
    public static <T> Stream<T> concat(Stream<? extends T> ... streamArray) {
        boolean bl = false;
        int n = 336;
        long l = 0L;
        ImmutableList.Builder builder = new ImmutableList.Builder(streamArray.length);
        for (Stream<T> stream : streamArray) {
            bl |= stream.isParallel();
            Spliterator spliterator = stream.spliterator();
            builder.add(spliterator);
            n &= spliterator.characteristics();
            l = LongMath.saturatedAdd(l, spliterator.estimateSize());
        }
        return StreamSupport.stream(CollectSpliterators.flatMap(((ImmutableList)builder.build()).spliterator(), Streams::lambda$concat$0, n, l), bl);
    }

    public static IntStream concat(IntStream ... intStreamArray) {
        return Stream.of(intStreamArray).flatMapToInt(Streams::lambda$concat$1);
    }

    public static LongStream concat(LongStream ... longStreamArray) {
        return Stream.of(longStreamArray).flatMapToLong(Streams::lambda$concat$2);
    }

    public static DoubleStream concat(DoubleStream ... doubleStreamArray) {
        return Stream.of(doubleStreamArray).flatMapToDouble(Streams::lambda$concat$3);
    }

    public static IntStream stream(OptionalInt optionalInt) {
        return optionalInt.isPresent() ? IntStream.of(optionalInt.getAsInt()) : IntStream.empty();
    }

    public static LongStream stream(OptionalLong optionalLong) {
        return optionalLong.isPresent() ? LongStream.of(optionalLong.getAsLong()) : LongStream.empty();
    }

    public static DoubleStream stream(OptionalDouble optionalDouble) {
        return optionalDouble.isPresent() ? DoubleStream.of(optionalDouble.getAsDouble()) : DoubleStream.empty();
    }

    public static <T> Optional<T> findLast(Stream<T> stream) {
        class OptionalState<T> {
            boolean set = false;
            T value = null;

            OptionalState() {
            }

            void set(@Nullable T t) {
                this.set = true;
                this.value = t;
            }

            T get() {
                Preconditions.checkState(this.set);
                return this.value;
            }
        }
        OptionalState optionalState = new OptionalState();
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.addLast(stream.spliterator());
        while (!arrayDeque.isEmpty()) {
            Spliterator<Object> spliterator;
            Spliterator<Object> spliterator2;
            block7: {
                block6: {
                    spliterator2 = (Spliterator<Object>)arrayDeque.removeLast();
                    if (spliterator2.getExactSizeIfKnown() == 0L) continue;
                    if (spliterator2.hasCharacteristics(16384)) {
                        while ((spliterator = spliterator2.trySplit()) != null && spliterator.getExactSizeIfKnown() != 0L) {
                            if (spliterator2.getExactSizeIfKnown() != 0L) continue;
                            spliterator2 = spliterator;
                            break;
                        }
                        spliterator2.forEachRemaining(optionalState::set);
                        return Optional.of(optionalState.get());
                    }
                    spliterator = spliterator2.trySplit();
                    if (spliterator == null) break block6;
                    if (spliterator.getExactSizeIfKnown() != 0L) break block7;
                }
                spliterator2.forEachRemaining(optionalState::set);
                if (!optionalState.set) continue;
                return Optional.of(optionalState.get());
            }
            arrayDeque.addLast(spliterator);
            arrayDeque.addLast(spliterator2);
        }
        return Optional.empty();
    }

    public static OptionalInt findLast(IntStream intStream) {
        Optional<Integer> optional = Streams.findLast(intStream.boxed());
        return optional.isPresent() ? OptionalInt.of(optional.get()) : OptionalInt.empty();
    }

    public static OptionalLong findLast(LongStream longStream) {
        Optional<Long> optional = Streams.findLast(longStream.boxed());
        return optional.isPresent() ? OptionalLong.of(optional.get()) : OptionalLong.empty();
    }

    public static OptionalDouble findLast(DoubleStream doubleStream) {
        Optional<Double> optional = Streams.findLast(doubleStream.boxed());
        return optional.isPresent() ? OptionalDouble.of(optional.get()) : OptionalDouble.empty();
    }

    public static <A, B, R> Stream<R> zip(Stream<A> stream, Stream<B> stream2, BiFunction<? super A, ? super B, R> biFunction) {
        Preconditions.checkNotNull(stream);
        Preconditions.checkNotNull(stream2);
        Preconditions.checkNotNull(biFunction);
        boolean bl = stream.isParallel() || stream2.isParallel();
        Spliterator spliterator = stream.spliterator();
        Spliterator spliterator2 = stream2.spliterator();
        int n = spliterator.characteristics() & spliterator2.characteristics() & 0x50;
        Iterator iterator2 = Spliterators.iterator(spliterator);
        Iterator iterator3 = Spliterators.iterator(spliterator2);
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<R>(Math.min(spliterator.estimateSize(), spliterator2.estimateSize()), n, iterator2, iterator3, biFunction){
            final Iterator val$itrA;
            final Iterator val$itrB;
            final BiFunction val$function;
            {
                this.val$itrA = iterator2;
                this.val$itrB = iterator3;
                this.val$function = biFunction;
                super(l, n);
            }

            @Override
            public boolean tryAdvance(Consumer<? super R> consumer) {
                if (this.val$itrA.hasNext() && this.val$itrB.hasNext()) {
                    consumer.accept(this.val$function.apply(this.val$itrA.next(), this.val$itrB.next()));
                    return false;
                }
                return true;
            }
        }, bl);
    }

    public static <T, R> Stream<R> mapWithIndex(Stream<T> stream, FunctionWithIndex<? super T, ? extends R> functionWithIndex) {
        Preconditions.checkNotNull(stream);
        Preconditions.checkNotNull(functionWithIndex);
        boolean bl = stream.isParallel();
        Spliterator spliterator = stream.spliterator();
        if (!spliterator.hasCharacteristics(16384)) {
            Iterator iterator2 = Spliterators.iterator(spliterator);
            return StreamSupport.stream(new Spliterators.AbstractSpliterator<R>(spliterator.estimateSize(), spliterator.characteristics() & 0x50, iterator2, functionWithIndex){
                long index;
                final Iterator val$fromIterator;
                final FunctionWithIndex val$function;
                {
                    this.val$fromIterator = iterator2;
                    this.val$function = functionWithIndex;
                    super(l, n);
                    this.index = 0L;
                }

                @Override
                public boolean tryAdvance(Consumer<? super R> consumer) {
                    if (this.val$fromIterator.hasNext()) {
                        consumer.accept(this.val$function.apply(this.val$fromIterator.next(), this.index++));
                        return false;
                    }
                    return true;
                }
            }, bl);
        }
        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        class Splitr
        extends MapWithIndexSpliterator<Spliterator<T>, R, Splitr>
        implements Consumer<T> {
            T holder;
            final FunctionWithIndex val$function;

            Splitr(Spliterator<T> spliterator, long l) {
                this.val$function = var4_3;
                super(spliterator, l);
            }

            @Override
            public void accept(@Nullable T t) {
                this.holder = t;
            }

            @Override
            public boolean tryAdvance(Consumer<? super R> consumer) {
                if (this.fromSpliterator.tryAdvance(this)) {
                    try {
                        consumer.accept(this.val$function.apply(this.holder, this.index++));
                        boolean bl = true;
                        return bl;
                    } finally {
                        this.holder = null;
                    }
                }
                return true;
            }

            @Override
            Splitr createSplit(Spliterator<T> spliterator, long l) {
                return new Splitr(spliterator, l, this.val$function);
            }

            @Override
            MapWithIndexSpliterator createSplit(Spliterator spliterator, long l) {
                return this.createSplit(spliterator, l);
            }
        }
        return StreamSupport.stream(new Splitr(spliterator, 0L, functionWithIndex), bl);
    }

    public static <R> Stream<R> mapWithIndex(IntStream intStream, IntFunctionWithIndex<R> intFunctionWithIndex) {
        Preconditions.checkNotNull(intStream);
        Preconditions.checkNotNull(intFunctionWithIndex);
        boolean bl = intStream.isParallel();
        Spliterator.OfInt ofInt = intStream.spliterator();
        if (!ofInt.hasCharacteristics(16384)) {
            PrimitiveIterator.OfInt ofInt2 = Spliterators.iterator(ofInt);
            return StreamSupport.stream(new Spliterators.AbstractSpliterator<R>(ofInt.estimateSize(), ofInt.characteristics() & 0x50, ofInt2, intFunctionWithIndex){
                long index;
                final PrimitiveIterator.OfInt val$fromIterator;
                final IntFunctionWithIndex val$function;
                {
                    this.val$fromIterator = ofInt;
                    this.val$function = intFunctionWithIndex;
                    super(l, n);
                    this.index = 0L;
                }

                @Override
                public boolean tryAdvance(Consumer<? super R> consumer) {
                    if (this.val$fromIterator.hasNext()) {
                        consumer.accept(this.val$function.apply(this.val$fromIterator.nextInt(), this.index++));
                        return false;
                    }
                    return true;
                }
            }, bl);
        }
        class Splitr
        extends MapWithIndexSpliterator<Spliterator.OfInt, R, Splitr>
        implements IntConsumer,
        Spliterator<R> {
            int holder;
            final IntFunctionWithIndex val$function;

            Splitr(Spliterator.OfInt ofInt, long l) {
                this.val$function = var4_3;
                super(ofInt, l);
            }

            @Override
            public void accept(int n) {
                this.holder = n;
            }

            @Override
            public boolean tryAdvance(Consumer<? super R> consumer) {
                if (((Spliterator.OfInt)this.fromSpliterator).tryAdvance(this)) {
                    consumer.accept(this.val$function.apply(this.holder, this.index++));
                    return false;
                }
                return true;
            }

            @Override
            Splitr createSplit(Spliterator.OfInt ofInt, long l) {
                return new Splitr(ofInt, l, this.val$function);
            }

            @Override
            MapWithIndexSpliterator createSplit(Spliterator spliterator, long l) {
                return this.createSplit((Spliterator.OfInt)spliterator, l);
            }
        }
        return StreamSupport.stream(new Splitr(ofInt, 0L, intFunctionWithIndex), bl);
    }

    public static <R> Stream<R> mapWithIndex(LongStream longStream, LongFunctionWithIndex<R> longFunctionWithIndex) {
        Preconditions.checkNotNull(longStream);
        Preconditions.checkNotNull(longFunctionWithIndex);
        boolean bl = longStream.isParallel();
        Spliterator.OfLong ofLong = longStream.spliterator();
        if (!ofLong.hasCharacteristics(16384)) {
            PrimitiveIterator.OfLong ofLong2 = Spliterators.iterator(ofLong);
            return StreamSupport.stream(new Spliterators.AbstractSpliterator<R>(ofLong.estimateSize(), ofLong.characteristics() & 0x50, ofLong2, longFunctionWithIndex){
                long index;
                final PrimitiveIterator.OfLong val$fromIterator;
                final LongFunctionWithIndex val$function;
                {
                    this.val$fromIterator = ofLong;
                    this.val$function = longFunctionWithIndex;
                    super(l, n);
                    this.index = 0L;
                }

                @Override
                public boolean tryAdvance(Consumer<? super R> consumer) {
                    if (this.val$fromIterator.hasNext()) {
                        consumer.accept(this.val$function.apply(this.val$fromIterator.nextLong(), this.index++));
                        return false;
                    }
                    return true;
                }
            }, bl);
        }
        class Splitr
        extends MapWithIndexSpliterator<Spliterator.OfLong, R, Splitr>
        implements LongConsumer,
        Spliterator<R> {
            long holder;
            final LongFunctionWithIndex val$function;

            Splitr(Spliterator.OfLong ofLong, long l) {
                this.val$function = var4_3;
                super(ofLong, l);
            }

            @Override
            public void accept(long l) {
                this.holder = l;
            }

            @Override
            public boolean tryAdvance(Consumer<? super R> consumer) {
                if (((Spliterator.OfLong)this.fromSpliterator).tryAdvance(this)) {
                    consumer.accept(this.val$function.apply(this.holder, this.index++));
                    return false;
                }
                return true;
            }

            @Override
            Splitr createSplit(Spliterator.OfLong ofLong, long l) {
                return new Splitr(ofLong, l, this.val$function);
            }

            @Override
            MapWithIndexSpliterator createSplit(Spliterator spliterator, long l) {
                return this.createSplit((Spliterator.OfLong)spliterator, l);
            }
        }
        return StreamSupport.stream(new Splitr(ofLong, 0L, longFunctionWithIndex), bl);
    }

    public static <R> Stream<R> mapWithIndex(DoubleStream doubleStream, DoubleFunctionWithIndex<R> doubleFunctionWithIndex) {
        Preconditions.checkNotNull(doubleStream);
        Preconditions.checkNotNull(doubleFunctionWithIndex);
        boolean bl = doubleStream.isParallel();
        Spliterator.OfDouble ofDouble = doubleStream.spliterator();
        if (!ofDouble.hasCharacteristics(16384)) {
            PrimitiveIterator.OfDouble ofDouble2 = Spliterators.iterator(ofDouble);
            return StreamSupport.stream(new Spliterators.AbstractSpliterator<R>(ofDouble.estimateSize(), ofDouble.characteristics() & 0x50, ofDouble2, doubleFunctionWithIndex){
                long index;
                final PrimitiveIterator.OfDouble val$fromIterator;
                final DoubleFunctionWithIndex val$function;
                {
                    this.val$fromIterator = ofDouble;
                    this.val$function = doubleFunctionWithIndex;
                    super(l, n);
                    this.index = 0L;
                }

                @Override
                public boolean tryAdvance(Consumer<? super R> consumer) {
                    if (this.val$fromIterator.hasNext()) {
                        consumer.accept(this.val$function.apply(this.val$fromIterator.nextDouble(), this.index++));
                        return false;
                    }
                    return true;
                }
            }, bl);
        }
        class Splitr
        extends MapWithIndexSpliterator<Spliterator.OfDouble, R, Splitr>
        implements DoubleConsumer,
        Spliterator<R> {
            double holder;
            final DoubleFunctionWithIndex val$function;

            Splitr(Spliterator.OfDouble ofDouble, long l) {
                this.val$function = var4_3;
                super(ofDouble, l);
            }

            @Override
            public void accept(double d) {
                this.holder = d;
            }

            @Override
            public boolean tryAdvance(Consumer<? super R> consumer) {
                if (((Spliterator.OfDouble)this.fromSpliterator).tryAdvance(this)) {
                    consumer.accept(this.val$function.apply(this.holder, this.index++));
                    return false;
                }
                return true;
            }

            @Override
            Splitr createSplit(Spliterator.OfDouble ofDouble, long l) {
                return new Splitr(ofDouble, l, this.val$function);
            }

            @Override
            MapWithIndexSpliterator createSplit(Spliterator spliterator, long l) {
                return this.createSplit((Spliterator.OfDouble)spliterator, l);
            }
        }
        return StreamSupport.stream(new Splitr(ofDouble, 0L, doubleFunctionWithIndex), bl);
    }

    private Streams() {
    }

    private static DoubleStream lambda$concat$3(DoubleStream doubleStream) {
        return doubleStream;
    }

    private static LongStream lambda$concat$2(LongStream longStream) {
        return longStream;
    }

    private static IntStream lambda$concat$1(IntStream intStream) {
        return intStream;
    }

    private static Spliterator lambda$concat$0(Spliterator spliterator) {
        return spliterator;
    }

    @Beta
    public static interface DoubleFunctionWithIndex<R> {
        public R apply(double var1, long var3);
    }

    @Beta
    public static interface LongFunctionWithIndex<R> {
        public R apply(long var1, long var3);
    }

    @Beta
    public static interface IntFunctionWithIndex<R> {
        public R apply(int var1, long var2);
    }

    private static abstract class MapWithIndexSpliterator<F extends Spliterator<?>, R, S extends MapWithIndexSpliterator<F, R, S>>
    implements Spliterator<R> {
        final F fromSpliterator;
        long index;

        MapWithIndexSpliterator(F f, long l) {
            this.fromSpliterator = f;
            this.index = l;
        }

        abstract S createSplit(F var1, long var2);

        public S trySplit() {
            Spliterator spliterator = this.fromSpliterator.trySplit();
            if (spliterator == null) {
                return null;
            }
            S s = this.createSplit(spliterator, this.index);
            this.index += spliterator.getExactSizeIfKnown();
            return s;
        }

        @Override
        public long estimateSize() {
            return this.fromSpliterator.estimateSize();
        }

        @Override
        public int characteristics() {
            return this.fromSpliterator.characteristics() & 0x4050;
        }

        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }

    @Beta
    public static interface FunctionWithIndex<T, R> {
        public R apply(T var1, long var2);
    }
}

