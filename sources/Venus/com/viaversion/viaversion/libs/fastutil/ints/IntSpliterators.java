/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.bytes.ByteSpliterator
 *  com.viaversion.viaversion.libs.fastutil.chars.CharSpliterator
 *  com.viaversion.viaversion.libs.fastutil.ints.IntBigListIterator
 *  com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators$ByteSpliteratorWrapper
 *  com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators$CharSpliteratorWrapper
 *  com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators$ShortSpliteratorWrapper
 *  com.viaversion.viaversion.libs.fastutil.shorts.ShortSpliterator
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.SafeMath;
import com.viaversion.viaversion.libs.fastutil.bytes.ByteSpliterator;
import com.viaversion.viaversion.libs.fastutil.chars.CharSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
import com.viaversion.viaversion.libs.fastutil.ints.IntBigListIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntComparator;
import com.viaversion.viaversion.libs.fastutil.ints.IntComparators;
import com.viaversion.viaversion.libs.fastutil.ints.IntConsumer;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import com.viaversion.viaversion.libs.fastutil.shorts.ShortSpliterator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntPredicate;

public final class IntSpliterators {
    static final int BASE_SPLITERATOR_CHARACTERISTICS = 256;
    public static final int COLLECTION_SPLITERATOR_CHARACTERISTICS = 320;
    public static final int LIST_SPLITERATOR_CHARACTERISTICS = 16720;
    public static final int SET_SPLITERATOR_CHARACTERISTICS = 321;
    private static final int SORTED_CHARACTERISTICS = 20;
    public static final int SORTED_SET_SPLITERATOR_CHARACTERISTICS = 341;
    public static final EmptySpliterator EMPTY_SPLITERATOR = new EmptySpliterator();

    private IntSpliterators() {
    }

    public static IntSpliterator singleton(int n) {
        return new SingletonSpliterator(n);
    }

    public static IntSpliterator singleton(int n, IntComparator intComparator) {
        return new SingletonSpliterator(n, intComparator);
    }

    public static IntSpliterator wrap(int[] nArray, int n, int n2) {
        IntArrays.ensureOffsetLength(nArray, n, n2);
        return new ArraySpliterator(nArray, n, n2, 0);
    }

    public static IntSpliterator wrap(int[] nArray) {
        return new ArraySpliterator(nArray, 0, nArray.length, 0);
    }

    public static IntSpliterator wrap(int[] nArray, int n, int n2, int n3) {
        IntArrays.ensureOffsetLength(nArray, n, n2);
        return new ArraySpliterator(nArray, n, n2, n3);
    }

    public static IntSpliterator wrapPreSorted(int[] nArray, int n, int n2, int n3, IntComparator intComparator) {
        IntArrays.ensureOffsetLength(nArray, n, n2);
        return new ArraySpliteratorWithComparator(nArray, n, n2, n3, intComparator);
    }

    public static IntSpliterator wrapPreSorted(int[] nArray, int n, int n2, IntComparator intComparator) {
        return IntSpliterators.wrapPreSorted(nArray, n, n2, 0, intComparator);
    }

    public static IntSpliterator wrapPreSorted(int[] nArray, IntComparator intComparator) {
        return IntSpliterators.wrapPreSorted(nArray, 0, nArray.length, intComparator);
    }

    public static IntSpliterator asIntSpliterator(Spliterator spliterator) {
        if (spliterator instanceof IntSpliterator) {
            return (IntSpliterator)spliterator;
        }
        if (spliterator instanceof Spliterator.OfInt) {
            return new PrimitiveSpliteratorWrapper((Spliterator.OfInt)spliterator);
        }
        return new SpliteratorWrapper(spliterator);
    }

    public static IntSpliterator asIntSpliterator(Spliterator spliterator, IntComparator intComparator) {
        if (spliterator instanceof IntSpliterator) {
            throw new IllegalArgumentException("Cannot override comparator on instance that is already a " + IntSpliterator.class.getSimpleName());
        }
        if (spliterator instanceof Spliterator.OfInt) {
            return new PrimitiveSpliteratorWrapperWithComparator((Spliterator.OfInt)spliterator, intComparator);
        }
        return new SpliteratorWrapperWithComparator(spliterator, intComparator);
    }

    public static void onEachMatching(IntSpliterator intSpliterator, IntPredicate intPredicate, java.util.function.IntConsumer intConsumer) {
        Objects.requireNonNull(intPredicate);
        Objects.requireNonNull(intConsumer);
        intSpliterator.forEachRemaining(arg_0 -> IntSpliterators.lambda$onEachMatching$0(intPredicate, intConsumer, arg_0));
    }

    public static IntSpliterator fromTo(int n, int n2) {
        return new IntervalSpliterator(n, n2);
    }

    public static IntSpliterator concat(IntSpliterator ... intSpliteratorArray) {
        return IntSpliterators.concat(intSpliteratorArray, 0, intSpliteratorArray.length);
    }

    public static IntSpliterator concat(IntSpliterator[] intSpliteratorArray, int n, int n2) {
        return new SpliteratorConcatenator(intSpliteratorArray, n, n2);
    }

    public static IntSpliterator asSpliterator(IntIterator intIterator, long l, int n) {
        return new SpliteratorFromIterator(intIterator, l, n);
    }

    public static IntSpliterator asSpliteratorFromSorted(IntIterator intIterator, long l, int n, IntComparator intComparator) {
        return new SpliteratorFromIteratorWithComparator(intIterator, l, n, intComparator);
    }

    public static IntSpliterator asSpliteratorUnknownSize(IntIterator intIterator, int n) {
        return new SpliteratorFromIterator(intIterator, n);
    }

    public static IntSpliterator asSpliteratorFromSortedUnknownSize(IntIterator intIterator, int n, IntComparator intComparator) {
        return new SpliteratorFromIteratorWithComparator(intIterator, n, intComparator);
    }

    public static IntIterator asIterator(IntSpliterator intSpliterator) {
        return new IteratorFromSpliterator(intSpliterator);
    }

    public static IntSpliterator wrap(ByteSpliterator byteSpliterator) {
        return new ByteSpliteratorWrapper(byteSpliterator);
    }

    public static IntSpliterator wrap(ShortSpliterator shortSpliterator) {
        return new ShortSpliteratorWrapper(shortSpliterator);
    }

    public static IntSpliterator wrap(CharSpliterator charSpliterator) {
        return new CharSpliteratorWrapper(charSpliterator);
    }

    private static void lambda$onEachMatching$0(IntPredicate intPredicate, java.util.function.IntConsumer intConsumer, int n) {
        if (intPredicate.test(n)) {
            intConsumer.accept(n);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class SingletonSpliterator
    implements IntSpliterator {
        private final int element;
        private final IntComparator comparator;
        private boolean consumed = false;
        private static final int CHARACTERISTICS = 17749;

        public SingletonSpliterator(int n) {
            this(n, null);
        }

        public SingletonSpliterator(int n, IntComparator intComparator) {
            this.element = n;
            this.comparator = intComparator;
        }

        @Override
        public boolean tryAdvance(java.util.function.IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            if (this.consumed) {
                return true;
            }
            this.consumed = true;
            intConsumer.accept(this.element);
            return false;
        }

        @Override
        public IntSpliterator trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return this.consumed ? 0L : 1L;
        }

        @Override
        public int characteristics() {
            return 0;
        }

        @Override
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            if (!this.consumed) {
                this.consumed = true;
                intConsumer.accept(this.element);
            }
        }

        @Override
        public IntComparator getComparator() {
            return this.comparator;
        }

        @Override
        public long skip(long l) {
            if (l < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + l);
            }
            if (l == 0L || this.consumed) {
                return 0L;
            }
            this.consumed = true;
            return 1L;
        }

        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }

        @Override
        public void forEachRemaining(Object object) {
            this.forEachRemaining((java.util.function.IntConsumer)object);
        }

        @Override
        public boolean tryAdvance(Object object) {
            return this.tryAdvance((java.util.function.IntConsumer)object);
        }

        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }

        @Override
        public Comparator getComparator() {
            return this.getComparator();
        }

        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class ArraySpliterator
    implements IntSpliterator {
        private static final int BASE_CHARACTERISTICS = 16720;
        final int[] array;
        private final int offset;
        private int length;
        private int curr;
        final int characteristics;

        public ArraySpliterator(int[] nArray, int n, int n2, int n3) {
            this.array = nArray;
            this.offset = n;
            this.length = n2;
            this.characteristics = 0x4150 | n3;
        }

        @Override
        public boolean tryAdvance(java.util.function.IntConsumer intConsumer) {
            if (this.curr >= this.length) {
                return true;
            }
            Objects.requireNonNull(intConsumer);
            intConsumer.accept(this.array[this.offset + this.curr++]);
            return false;
        }

        @Override
        public long estimateSize() {
            return this.length - this.curr;
        }

        @Override
        public int characteristics() {
            return this.characteristics;
        }

        protected ArraySpliterator makeForSplit(int n, int n2) {
            return new ArraySpliterator(this.array, n, n2, this.characteristics);
        }

        @Override
        public IntSpliterator trySplit() {
            int n = this.length - this.curr >> 1;
            if (n <= 1) {
                return null;
            }
            int n2 = this.curr + n;
            int n3 = this.offset + this.curr;
            this.curr = n2;
            return this.makeForSplit(n3, n);
        }

        @Override
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            while (this.curr < this.length) {
                intConsumer.accept(this.array[this.offset + this.curr]);
                ++this.curr;
            }
        }

        @Override
        public long skip(long l) {
            if (l < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + l);
            }
            if (this.curr >= this.length) {
                return 0L;
            }
            int n = this.length - this.curr;
            if (l < (long)n) {
                this.curr = SafeMath.safeLongToInt((long)this.curr + l);
                return l;
            }
            l = n;
            this.curr = this.length;
            return l;
        }

        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }

        @Override
        public void forEachRemaining(Object object) {
            this.forEachRemaining((java.util.function.IntConsumer)object);
        }

        @Override
        public boolean tryAdvance(Object object) {
            return this.tryAdvance((java.util.function.IntConsumer)object);
        }

        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }

        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class ArraySpliteratorWithComparator
    extends ArraySpliterator {
        private final IntComparator comparator;

        public ArraySpliteratorWithComparator(int[] nArray, int n, int n2, int n3, IntComparator intComparator) {
            super(nArray, n, n2, n3 | 0x14);
            this.comparator = intComparator;
        }

        @Override
        protected ArraySpliteratorWithComparator makeForSplit(int n, int n2) {
            return new ArraySpliteratorWithComparator(this.array, n, n2, this.characteristics, this.comparator);
        }

        @Override
        public IntComparator getComparator() {
            return this.comparator;
        }

        @Override
        protected ArraySpliterator makeForSplit(int n, int n2) {
            return this.makeForSplit(n, n2);
        }

        @Override
        public Comparator getComparator() {
            return this.getComparator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class PrimitiveSpliteratorWrapper
    implements IntSpliterator {
        final Spliterator.OfInt i;

        public PrimitiveSpliteratorWrapper(Spliterator.OfInt ofInt) {
            this.i = ofInt;
        }

        @Override
        public boolean tryAdvance(java.util.function.IntConsumer intConsumer) {
            return this.i.tryAdvance(intConsumer);
        }

        @Override
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            this.i.forEachRemaining(intConsumer);
        }

        @Override
        public long estimateSize() {
            return this.i.estimateSize();
        }

        @Override
        public int characteristics() {
            return this.i.characteristics();
        }

        @Override
        public IntComparator getComparator() {
            return IntComparators.asIntComparator(this.i.getComparator());
        }

        @Override
        public IntSpliterator trySplit() {
            Spliterator.OfInt ofInt = this.i.trySplit();
            if (ofInt == null) {
                return null;
            }
            return new PrimitiveSpliteratorWrapper(ofInt);
        }

        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }

        @Override
        public void forEachRemaining(Object object) {
            this.forEachRemaining((java.util.function.IntConsumer)object);
        }

        @Override
        public boolean tryAdvance(Object object) {
            return this.tryAdvance((java.util.function.IntConsumer)object);
        }

        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }

        @Override
        public Comparator getComparator() {
            return this.getComparator();
        }

        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class SpliteratorWrapper
    implements IntSpliterator {
        final Spliterator<Integer> i;

        public SpliteratorWrapper(Spliterator<Integer> spliterator) {
            this.i = spliterator;
        }

        @Override
        public boolean tryAdvance(IntConsumer intConsumer) {
            return this.i.tryAdvance(intConsumer);
        }

        @Override
        public boolean tryAdvance(java.util.function.IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            return this.i.tryAdvance(intConsumer instanceof Consumer ? (Consumer<Integer>)((Object)intConsumer) : intConsumer::accept);
        }

        @Override
        @Deprecated
        public boolean tryAdvance(Consumer<? super Integer> consumer) {
            return this.i.tryAdvance(consumer);
        }

        @Override
        public void forEachRemaining(IntConsumer intConsumer) {
            this.i.forEachRemaining(intConsumer);
        }

        @Override
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            this.i.forEachRemaining(intConsumer instanceof Consumer ? (Consumer<Integer>)((Object)intConsumer) : intConsumer::accept);
        }

        @Override
        @Deprecated
        public void forEachRemaining(Consumer<? super Integer> consumer) {
            this.i.forEachRemaining(consumer);
        }

        @Override
        public long estimateSize() {
            return this.i.estimateSize();
        }

        @Override
        public int characteristics() {
            return this.i.characteristics();
        }

        @Override
        public IntComparator getComparator() {
            return IntComparators.asIntComparator(this.i.getComparator());
        }

        @Override
        public IntSpliterator trySplit() {
            Spliterator<Integer> spliterator = this.i.trySplit();
            if (spliterator == null) {
                return null;
            }
            return new SpliteratorWrapper(spliterator);
        }

        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }

        @Override
        public void forEachRemaining(Object object) {
            this.forEachRemaining((java.util.function.IntConsumer)object);
        }

        @Override
        public boolean tryAdvance(Object object) {
            return this.tryAdvance((java.util.function.IntConsumer)object);
        }

        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }

        @Override
        public Comparator getComparator() {
            return this.getComparator();
        }

        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class PrimitiveSpliteratorWrapperWithComparator
    extends PrimitiveSpliteratorWrapper {
        final IntComparator comparator;

        public PrimitiveSpliteratorWrapperWithComparator(Spliterator.OfInt ofInt, IntComparator intComparator) {
            super(ofInt);
            this.comparator = intComparator;
        }

        @Override
        public IntComparator getComparator() {
            return this.comparator;
        }

        @Override
        public IntSpliterator trySplit() {
            Spliterator.OfInt ofInt = this.i.trySplit();
            if (ofInt == null) {
                return null;
            }
            return new PrimitiveSpliteratorWrapperWithComparator(ofInt, this.comparator);
        }

        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }

        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }

        @Override
        public Comparator getComparator() {
            return this.getComparator();
        }

        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class SpliteratorWrapperWithComparator
    extends SpliteratorWrapper {
        final IntComparator comparator;

        public SpliteratorWrapperWithComparator(Spliterator<Integer> spliterator, IntComparator intComparator) {
            super(spliterator);
            this.comparator = intComparator;
        }

        @Override
        public IntComparator getComparator() {
            return this.comparator;
        }

        @Override
        public IntSpliterator trySplit() {
            Spliterator<Integer> spliterator = this.i.trySplit();
            if (spliterator == null) {
                return null;
            }
            return new SpliteratorWrapperWithComparator(spliterator, this.comparator);
        }

        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }

        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }

        @Override
        public Comparator getComparator() {
            return this.getComparator();
        }

        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class IntervalSpliterator
    implements IntSpliterator {
        private static final int DONT_SPLIT_THRESHOLD = 2;
        private static final int CHARACTERISTICS = 17749;
        private int curr;
        private int to;

        public IntervalSpliterator(int n, int n2) {
            this.curr = n;
            this.to = n2;
        }

        @Override
        public boolean tryAdvance(java.util.function.IntConsumer intConsumer) {
            if (this.curr >= this.to) {
                return true;
            }
            intConsumer.accept(this.curr++);
            return false;
        }

        @Override
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            while (this.curr < this.to) {
                intConsumer.accept(this.curr);
                ++this.curr;
            }
        }

        @Override
        public long estimateSize() {
            return (long)this.to - (long)this.curr;
        }

        @Override
        public int characteristics() {
            return 0;
        }

        @Override
        public IntComparator getComparator() {
            return null;
        }

        @Override
        public IntSpliterator trySplit() {
            long l = this.to - this.curr;
            int n = (int)((long)this.curr + (l >> 1));
            if (l >= 0L && l <= 2L) {
                return null;
            }
            int n2 = this.curr;
            this.curr = n;
            return new IntervalSpliterator(n2, n);
        }

        @Override
        public long skip(long l) {
            if (l < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + l);
            }
            if (this.curr >= this.to) {
                return 0L;
            }
            long l2 = (long)this.curr + l;
            if (l2 <= (long)this.to && l2 >= (long)this.curr) {
                this.curr = SafeMath.safeLongToInt(l2);
                return l;
            }
            l = this.to - this.curr;
            this.curr = this.to;
            return l;
        }

        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }

        @Override
        public void forEachRemaining(Object object) {
            this.forEachRemaining((java.util.function.IntConsumer)object);
        }

        @Override
        public boolean tryAdvance(Object object) {
            return this.tryAdvance((java.util.function.IntConsumer)object);
        }

        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }

        @Override
        public Comparator getComparator() {
            return this.getComparator();
        }

        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class SpliteratorConcatenator
    implements IntSpliterator {
        private static final int EMPTY_CHARACTERISTICS = 16448;
        private static final int CHARACTERISTICS_NOT_SUPPORTED_WHILE_MULTIPLE = 5;
        final IntSpliterator[] a;
        int offset;
        int length;
        long remainingEstimatedExceptCurrent = Long.MAX_VALUE;
        int characteristics = 0;

        public SpliteratorConcatenator(IntSpliterator[] intSpliteratorArray, int n, int n2) {
            this.a = intSpliteratorArray;
            this.offset = n;
            this.length = n2;
            this.remainingEstimatedExceptCurrent = this.recomputeRemaining();
            this.characteristics = this.computeCharacteristics();
        }

        private long recomputeRemaining() {
            int n = this.offset + 1;
            long l = 0L;
            for (int i = this.length - 1; i > 0; --i) {
                long l2 = this.a[n++].estimateSize();
                if (l2 != Long.MAX_VALUE) continue;
                return Long.MAX_VALUE;
            }
            return l;
        }

        private int computeCharacteristics() {
            if (this.length <= 0) {
                return 1;
            }
            int n = -1;
            int n2 = this.length;
            int n3 = this.offset;
            if (n2 > 1) {
                n &= 0xFFFFFFFA;
            }
            while (n2 > 0) {
                n &= this.a[n3++].characteristics();
                --n2;
            }
            return n;
        }

        private void advanceNextSpliterator() {
            if (this.length <= 0) {
                throw new AssertionError((Object)"advanceNextSpliterator() called with none remaining");
            }
            ++this.offset;
            --this.length;
            this.remainingEstimatedExceptCurrent = this.recomputeRemaining();
        }

        @Override
        public boolean tryAdvance(java.util.function.IntConsumer intConsumer) {
            boolean bl = false;
            while (this.length > 0) {
                if (this.a[this.offset].tryAdvance(intConsumer)) {
                    bl = true;
                    break;
                }
                this.advanceNextSpliterator();
            }
            return bl;
        }

        @Override
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            while (this.length > 0) {
                this.a[this.offset].forEachRemaining(intConsumer);
                this.advanceNextSpliterator();
            }
        }

        @Override
        @Deprecated
        public void forEachRemaining(Consumer<? super Integer> consumer) {
            while (this.length > 0) {
                this.a[this.offset].forEachRemaining(consumer);
                this.advanceNextSpliterator();
            }
        }

        @Override
        public long estimateSize() {
            if (this.length <= 0) {
                return 0L;
            }
            long l = this.a[this.offset].estimateSize() + this.remainingEstimatedExceptCurrent;
            if (l < 0L) {
                return Long.MAX_VALUE;
            }
            return l;
        }

        @Override
        public int characteristics() {
            return this.characteristics;
        }

        @Override
        public IntComparator getComparator() {
            if (this.length == 1 && (this.characteristics & 4) != 0) {
                return this.a[this.offset].getComparator();
            }
            throw new IllegalStateException();
        }

        @Override
        public IntSpliterator trySplit() {
            switch (this.length) {
                case 0: {
                    return null;
                }
                case 1: {
                    IntSpliterator intSpliterator = this.a[this.offset].trySplit();
                    this.characteristics = this.a[this.offset].characteristics();
                    return intSpliterator;
                }
                case 2: {
                    IntSpliterator intSpliterator = this.a[this.offset++];
                    --this.length;
                    this.characteristics = this.a[this.offset].characteristics();
                    this.remainingEstimatedExceptCurrent = 0L;
                    return intSpliterator;
                }
            }
            int n = this.length >> 1;
            int n2 = this.offset;
            int n3 = this.offset + n;
            int n4 = n;
            int n5 = this.length - n;
            this.offset = n3;
            this.length = n5;
            this.remainingEstimatedExceptCurrent = this.recomputeRemaining();
            this.characteristics = this.computeCharacteristics();
            return new SpliteratorConcatenator(this.a, n2, n4);
        }

        @Override
        public long skip(long l) {
            long l2 = 0L;
            if (this.length <= 0) {
                return 0L;
            }
            while (l2 < l && this.length >= 0) {
                long l3;
                if ((l2 += (l3 = this.a[this.offset].skip(l - l2))) >= l) continue;
                this.advanceNextSpliterator();
            }
            return l2;
        }

        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }

        @Override
        public void forEachRemaining(Object object) {
            this.forEachRemaining((java.util.function.IntConsumer)object);
        }

        @Override
        public boolean tryAdvance(Object object) {
            return this.tryAdvance((java.util.function.IntConsumer)object);
        }

        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }

        @Override
        public Comparator getComparator() {
            return this.getComparator();
        }

        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class SpliteratorFromIterator
    implements IntSpliterator {
        private static final int BATCH_INCREMENT_SIZE = 1024;
        private static final int BATCH_MAX_SIZE = 0x2000000;
        private final IntIterator iter;
        final int characteristics;
        private final boolean knownSize;
        private long size = Long.MAX_VALUE;
        private int nextBatchSize = 1024;
        private IntSpliterator delegate = null;

        SpliteratorFromIterator(IntIterator intIterator, int n) {
            this.iter = intIterator;
            this.characteristics = 0x100 | n;
            this.knownSize = false;
        }

        SpliteratorFromIterator(IntIterator intIterator, long l, int n) {
            this.iter = intIterator;
            this.knownSize = true;
            this.size = l;
            this.characteristics = (n & 0x1000) != 0 ? 0x100 | n : 0x4140 | n;
        }

        @Override
        public boolean tryAdvance(java.util.function.IntConsumer intConsumer) {
            if (this.delegate != null) {
                boolean bl = this.delegate.tryAdvance(intConsumer);
                if (!bl) {
                    this.delegate = null;
                }
                return bl;
            }
            if (!this.iter.hasNext()) {
                return true;
            }
            --this.size;
            intConsumer.accept(this.iter.nextInt());
            return false;
        }

        @Override
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            if (this.delegate != null) {
                this.delegate.forEachRemaining(intConsumer);
                this.delegate = null;
            }
            this.iter.forEachRemaining(intConsumer);
            this.size = 0L;
        }

        @Override
        public long estimateSize() {
            if (this.delegate != null) {
                return this.delegate.estimateSize();
            }
            if (!this.iter.hasNext()) {
                return 0L;
            }
            return this.knownSize && this.size >= 0L ? this.size : Long.MAX_VALUE;
        }

        @Override
        public int characteristics() {
            return this.characteristics;
        }

        protected IntSpliterator makeForSplit(int[] nArray, int n) {
            return IntSpliterators.wrap(nArray, 0, n, this.characteristics);
        }

        @Override
        public IntSpliterator trySplit() {
            if (!this.iter.hasNext()) {
                return null;
            }
            int n = this.knownSize && this.size > 0L ? (int)Math.min((long)this.nextBatchSize, this.size) : this.nextBatchSize;
            int[] nArray = new int[n];
            int n2 = 0;
            while (n2 < n && this.iter.hasNext()) {
                nArray[n2++] = this.iter.nextInt();
                --this.size;
            }
            if (n < this.nextBatchSize && this.iter.hasNext()) {
                nArray = Arrays.copyOf(nArray, this.nextBatchSize);
                while (this.iter.hasNext() && n2 < this.nextBatchSize) {
                    nArray[n2++] = this.iter.nextInt();
                    --this.size;
                }
            }
            this.nextBatchSize = Math.min(0x2000000, this.nextBatchSize + 1024);
            IntSpliterator intSpliterator = this.makeForSplit(nArray, n2);
            if (!this.iter.hasNext()) {
                this.delegate = intSpliterator;
                return intSpliterator.trySplit();
            }
            return intSpliterator;
        }

        @Override
        public long skip(long l) {
            long l2;
            int n;
            if (l < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + l);
            }
            if (this.iter instanceof IntBigListIterator) {
                long l3 = ((IntBigListIterator)this.iter).skip(l);
                this.size -= l3;
                return l3;
            }
            for (l2 = 0L; l2 < l && this.iter.hasNext(); l2 += (long)n) {
                n = this.iter.skip(SafeMath.safeLongToInt(Math.min(l, Integer.MAX_VALUE)));
                this.size -= (long)n;
            }
            return l2;
        }

        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }

        @Override
        public void forEachRemaining(Object object) {
            this.forEachRemaining((java.util.function.IntConsumer)object);
        }

        @Override
        public boolean tryAdvance(Object object) {
            return this.tryAdvance((java.util.function.IntConsumer)object);
        }

        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }

        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class SpliteratorFromIteratorWithComparator
    extends SpliteratorFromIterator {
        private final IntComparator comparator;

        SpliteratorFromIteratorWithComparator(IntIterator intIterator, int n, IntComparator intComparator) {
            super(intIterator, n | 0x14);
            this.comparator = intComparator;
        }

        SpliteratorFromIteratorWithComparator(IntIterator intIterator, long l, int n, IntComparator intComparator) {
            super(intIterator, l, n | 0x14);
            this.comparator = intComparator;
        }

        @Override
        public IntComparator getComparator() {
            return this.comparator;
        }

        @Override
        protected IntSpliterator makeForSplit(int[] nArray, int n) {
            return IntSpliterators.wrapPreSorted(nArray, 0, n, this.characteristics, this.comparator);
        }

        @Override
        public Comparator getComparator() {
            return this.getComparator();
        }
    }

    private static final class IteratorFromSpliterator
    implements IntIterator,
    IntConsumer {
        private final IntSpliterator spliterator;
        private int holder = 0;
        private boolean hasPeeked = false;

        IteratorFromSpliterator(IntSpliterator intSpliterator) {
            this.spliterator = intSpliterator;
        }

        @Override
        public void accept(int n) {
            this.holder = n;
        }

        @Override
        public boolean hasNext() {
            if (this.hasPeeked) {
                return false;
            }
            boolean bl = this.spliterator.tryAdvance(this);
            if (!bl) {
                return true;
            }
            this.hasPeeked = true;
            return false;
        }

        @Override
        public int nextInt() {
            if (this.hasPeeked) {
                this.hasPeeked = false;
                return this.holder;
            }
            boolean bl = this.spliterator.tryAdvance(this);
            if (!bl) {
                throw new NoSuchElementException();
            }
            return this.holder;
        }

        @Override
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            if (this.hasPeeked) {
                this.hasPeeked = false;
                intConsumer.accept(this.holder);
            }
            this.spliterator.forEachRemaining(intConsumer);
        }

        @Override
        public int skip(int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            int n2 = 0;
            if (this.hasPeeked) {
                this.hasPeeked = false;
                this.spliterator.skip(1L);
                ++n2;
                --n;
            }
            if (n > 0) {
                n2 += SafeMath.safeLongToInt(this.spliterator.skip(n));
            }
            return n2;
        }

        @Override
        public void forEachRemaining(Object object) {
            this.forEachRemaining((java.util.function.IntConsumer)object);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptySpliterator
    implements IntSpliterator,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = 8379247926738230492L;
        private static final int CHARACTERISTICS = 16448;

        protected EmptySpliterator() {
        }

        @Override
        public boolean tryAdvance(java.util.function.IntConsumer intConsumer) {
            return true;
        }

        @Override
        @Deprecated
        public boolean tryAdvance(Consumer<? super Integer> consumer) {
            return true;
        }

        @Override
        public IntSpliterator trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return 0L;
        }

        @Override
        public int characteristics() {
            return 1;
        }

        @Override
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
        }

        @Override
        @Deprecated
        public void forEachRemaining(Consumer<? super Integer> consumer) {
        }

        public Object clone() {
            return EMPTY_SPLITERATOR;
        }

        private Object readResolve() {
            return EMPTY_SPLITERATOR;
        }

        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }

        @Override
        public void forEachRemaining(Object object) {
            this.forEachRemaining((java.util.function.IntConsumer)object);
        }

        @Override
        public boolean tryAdvance(Object object) {
            return this.tryAdvance((java.util.function.IntConsumer)object);
        }

        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }

        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static abstract class LateBindingSizeIndexBasedSpliterator
    extends AbstractIndexBasedSpliterator {
        protected int maxPos = -1;
        private boolean maxPosFixed;

        protected LateBindingSizeIndexBasedSpliterator(int n) {
            super(n);
            this.maxPosFixed = false;
        }

        protected LateBindingSizeIndexBasedSpliterator(int n, int n2) {
            super(n);
            this.maxPos = n2;
            this.maxPosFixed = true;
        }

        protected abstract int getMaxPosFromBackingStore();

        @Override
        protected final int getMaxPos() {
            return this.maxPosFixed ? this.maxPos : this.getMaxPosFromBackingStore();
        }

        @Override
        public IntSpliterator trySplit() {
            IntSpliterator intSpliterator = super.trySplit();
            if (!this.maxPosFixed && intSpliterator != null) {
                this.maxPos = this.getMaxPosFromBackingStore();
                this.maxPosFixed = true;
            }
            return intSpliterator;
        }

        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }

        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }

        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }

    public static abstract class EarlyBindingSizeIndexBasedSpliterator
    extends AbstractIndexBasedSpliterator {
        protected final int maxPos;

        protected EarlyBindingSizeIndexBasedSpliterator(int n, int n2) {
            super(n);
            this.maxPos = n2;
        }

        @Override
        protected final int getMaxPos() {
            return this.maxPos;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static abstract class AbstractIndexBasedSpliterator
    extends AbstractIntSpliterator {
        protected int pos;

        protected AbstractIndexBasedSpliterator(int n) {
            this.pos = n;
        }

        protected abstract int get(int var1);

        protected abstract int getMaxPos();

        protected abstract IntSpliterator makeForSplit(int var1, int var2);

        protected int computeSplitPoint() {
            return this.pos + (this.getMaxPos() - this.pos) / 2;
        }

        private void splitPointCheck(int n, int n2) {
            if (n < this.pos || n > n2) {
                throw new IndexOutOfBoundsException("splitPoint " + n + " outside of range of current position " + this.pos + " and range end " + n2);
            }
        }

        @Override
        public int characteristics() {
            return 1;
        }

        @Override
        public long estimateSize() {
            return (long)this.getMaxPos() - (long)this.pos;
        }

        @Override
        public boolean tryAdvance(java.util.function.IntConsumer intConsumer) {
            if (this.pos >= this.getMaxPos()) {
                return true;
            }
            intConsumer.accept(this.get(this.pos++));
            return false;
        }

        @Override
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            int n = this.getMaxPos();
            while (this.pos < n) {
                intConsumer.accept(this.get(this.pos));
                ++this.pos;
            }
        }

        @Override
        public long skip(long l) {
            if (l < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + l);
            }
            int n = this.getMaxPos();
            if (this.pos >= n) {
                return 0L;
            }
            int n2 = n - this.pos;
            if (l < (long)n2) {
                this.pos = SafeMath.safeLongToInt((long)this.pos + l);
                return l;
            }
            l = n2;
            this.pos = n;
            return l;
        }

        @Override
        public IntSpliterator trySplit() {
            int n = this.getMaxPos();
            int n2 = this.computeSplitPoint();
            if (n2 == this.pos || n2 == n) {
                return null;
            }
            this.splitPointCheck(n2, n);
            int n3 = this.pos;
            IntSpliterator intSpliterator = this.makeForSplit(n3, n2);
            if (intSpliterator != null) {
                this.pos = n2;
            }
            return intSpliterator;
        }

        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }

        @Override
        public void forEachRemaining(Object object) {
            this.forEachRemaining((java.util.function.IntConsumer)object);
        }

        @Override
        public boolean tryAdvance(Object object) {
            return this.tryAdvance((java.util.function.IntConsumer)object);
        }

        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }

        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
}

