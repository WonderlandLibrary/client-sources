/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.objects.ObjectBigListIterator
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.SafeMath;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBigListIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectComparators;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class ObjectSpliterators {
    static final int BASE_SPLITERATOR_CHARACTERISTICS = 0;
    public static final int COLLECTION_SPLITERATOR_CHARACTERISTICS = 64;
    public static final int LIST_SPLITERATOR_CHARACTERISTICS = 16464;
    public static final int SET_SPLITERATOR_CHARACTERISTICS = 65;
    private static final int SORTED_CHARACTERISTICS = 20;
    public static final int SORTED_SET_SPLITERATOR_CHARACTERISTICS = 85;
    public static final EmptySpliterator EMPTY_SPLITERATOR = new EmptySpliterator();

    private ObjectSpliterators() {
    }

    public static <K> ObjectSpliterator<K> emptySpliterator() {
        return EMPTY_SPLITERATOR;
    }

    public static <K> ObjectSpliterator<K> singleton(K k) {
        return new SingletonSpliterator<K>(k);
    }

    public static <K> ObjectSpliterator<K> singleton(K k, Comparator<? super K> comparator) {
        return new SingletonSpliterator<K>(k, comparator);
    }

    public static <K> ObjectSpliterator<K> wrap(K[] KArray, int n, int n2) {
        ObjectArrays.ensureOffsetLength(KArray, n, n2);
        return new ArraySpliterator<K>(KArray, n, n2, 0);
    }

    public static <K> ObjectSpliterator<K> wrap(K[] KArray) {
        return new ArraySpliterator<K>(KArray, 0, KArray.length, 0);
    }

    public static <K> ObjectSpliterator<K> wrap(K[] KArray, int n, int n2, int n3) {
        ObjectArrays.ensureOffsetLength(KArray, n, n2);
        return new ArraySpliterator<K>(KArray, n, n2, n3);
    }

    public static <K> ObjectSpliterator<K> wrapPreSorted(K[] KArray, int n, int n2, int n3, Comparator<? super K> comparator) {
        ObjectArrays.ensureOffsetLength(KArray, n, n2);
        return new ArraySpliteratorWithComparator<K>(KArray, n, n2, n3, comparator);
    }

    public static <K> ObjectSpliterator<K> wrapPreSorted(K[] KArray, int n, int n2, Comparator<? super K> comparator) {
        return ObjectSpliterators.wrapPreSorted(KArray, n, n2, 0, comparator);
    }

    public static <K> ObjectSpliterator<K> wrapPreSorted(K[] KArray, Comparator<? super K> comparator) {
        return ObjectSpliterators.wrapPreSorted(KArray, 0, KArray.length, comparator);
    }

    public static <K> ObjectSpliterator<K> asObjectSpliterator(Spliterator<K> spliterator) {
        if (spliterator instanceof ObjectSpliterator) {
            return (ObjectSpliterator)spliterator;
        }
        return new SpliteratorWrapper<K>(spliterator);
    }

    public static <K> ObjectSpliterator<K> asObjectSpliterator(Spliterator<K> spliterator, Comparator<? super K> comparator) {
        if (spliterator instanceof ObjectSpliterator) {
            throw new IllegalArgumentException("Cannot override comparator on instance that is already a " + ObjectSpliterator.class.getSimpleName());
        }
        return new SpliteratorWrapperWithComparator<K>(spliterator, comparator);
    }

    public static <K> void onEachMatching(Spliterator<K> spliterator, Predicate<? super K> predicate, Consumer<? super K> consumer) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(consumer);
        spliterator.forEachRemaining(arg_0 -> ObjectSpliterators.lambda$onEachMatching$0(predicate, consumer, arg_0));
    }

    @SafeVarargs
    public static <K> ObjectSpliterator<K> concat(ObjectSpliterator<? extends K> ... objectSpliteratorArray) {
        return ObjectSpliterators.concat(objectSpliteratorArray, 0, objectSpliteratorArray.length);
    }

    public static <K> ObjectSpliterator<K> concat(ObjectSpliterator<? extends K>[] objectSpliteratorArray, int n, int n2) {
        return new SpliteratorConcatenator<K>(objectSpliteratorArray, n, n2);
    }

    public static <K> ObjectSpliterator<K> asSpliterator(ObjectIterator<? extends K> objectIterator, long l, int n) {
        return new SpliteratorFromIterator<K>(objectIterator, l, n);
    }

    public static <K> ObjectSpliterator<K> asSpliteratorFromSorted(ObjectIterator<? extends K> objectIterator, long l, int n, Comparator<? super K> comparator) {
        return new SpliteratorFromIteratorWithComparator<K>(objectIterator, l, n, comparator);
    }

    public static <K> ObjectSpliterator<K> asSpliteratorUnknownSize(ObjectIterator<? extends K> objectIterator, int n) {
        return new SpliteratorFromIterator<K>(objectIterator, n);
    }

    public static <K> ObjectSpliterator<K> asSpliteratorFromSortedUnknownSize(ObjectIterator<? extends K> objectIterator, int n, Comparator<? super K> comparator) {
        return new SpliteratorFromIteratorWithComparator<K>(objectIterator, n, comparator);
    }

    public static <K> ObjectIterator<K> asIterator(ObjectSpliterator<? extends K> objectSpliterator) {
        return new IteratorFromSpliterator<K>(objectSpliterator);
    }

    private static void lambda$onEachMatching$0(Predicate predicate, Consumer consumer, Object object) {
        if (predicate.test(object)) {
            consumer.accept(object);
        }
    }

    public static class EmptySpliterator<K>
    implements ObjectSpliterator<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = 8379247926738230492L;
        private static final int CHARACTERISTICS = 16448;

        protected EmptySpliterator() {
        }

        @Override
        public boolean tryAdvance(Consumer<? super K> consumer) {
            return true;
        }

        @Override
        public ObjectSpliterator<K> trySplit() {
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
        public void forEachRemaining(Consumer<? super K> consumer) {
        }

        public Object clone() {
            return EMPTY_SPLITERATOR;
        }

        private Object readResolve() {
            return EMPTY_SPLITERATOR;
        }

        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }

    private static class SingletonSpliterator<K>
    implements ObjectSpliterator<K> {
        private final K element;
        private final Comparator<? super K> comparator;
        private boolean consumed = false;
        private static final int CHARACTERISTICS = 17493;

        public SingletonSpliterator(K k) {
            this(k, null);
        }

        public SingletonSpliterator(K k, Comparator<? super K> comparator) {
            this.element = k;
            this.comparator = comparator;
        }

        @Override
        public boolean tryAdvance(Consumer<? super K> consumer) {
            Objects.requireNonNull(consumer);
            if (this.consumed) {
                return true;
            }
            this.consumed = true;
            consumer.accept(this.element);
            return false;
        }

        @Override
        public ObjectSpliterator<K> trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return this.consumed ? 0L : 1L;
        }

        @Override
        public int characteristics() {
            return 0x4455 | (this.element != null ? 256 : 0);
        }

        @Override
        public void forEachRemaining(Consumer<? super K> consumer) {
            Objects.requireNonNull(consumer);
            if (!this.consumed) {
                this.consumed = true;
                consumer.accept(this.element);
            }
        }

        @Override
        public Comparator<? super K> getComparator() {
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
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }

    private static class ArraySpliterator<K>
    implements ObjectSpliterator<K> {
        private static final int BASE_CHARACTERISTICS = 16464;
        final K[] array;
        private final int offset;
        private int length;
        private int curr;
        final int characteristics;

        public ArraySpliterator(K[] KArray, int n, int n2, int n3) {
            this.array = KArray;
            this.offset = n;
            this.length = n2;
            this.characteristics = 0x4050 | n3;
        }

        @Override
        public boolean tryAdvance(Consumer<? super K> consumer) {
            if (this.curr >= this.length) {
                return true;
            }
            Objects.requireNonNull(consumer);
            consumer.accept(this.array[this.offset + this.curr++]);
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

        protected ArraySpliterator<K> makeForSplit(int n, int n2) {
            return new ArraySpliterator<K>(this.array, n, n2, this.characteristics);
        }

        @Override
        public ObjectSpliterator<K> trySplit() {
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
        public void forEachRemaining(Consumer<? super K> consumer) {
            Objects.requireNonNull(consumer);
            while (this.curr < this.length) {
                consumer.accept(this.array[this.offset + this.curr]);
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
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }

    private static class ArraySpliteratorWithComparator<K>
    extends ArraySpliterator<K> {
        private final Comparator<? super K> comparator;

        public ArraySpliteratorWithComparator(K[] KArray, int n, int n2, int n3, Comparator<? super K> comparator) {
            super(KArray, n, n2, n3 | 0x14);
            this.comparator = comparator;
        }

        @Override
        protected ArraySpliteratorWithComparator<K> makeForSplit(int n, int n2) {
            return new ArraySpliteratorWithComparator<K>(this.array, n, n2, this.characteristics, this.comparator);
        }

        @Override
        public Comparator<? super K> getComparator() {
            return this.comparator;
        }

        @Override
        protected ArraySpliterator makeForSplit(int n, int n2) {
            return this.makeForSplit(n, n2);
        }
    }

    private static class SpliteratorWrapper<K>
    implements ObjectSpliterator<K> {
        final Spliterator<K> i;

        public SpliteratorWrapper(Spliterator<K> spliterator) {
            this.i = spliterator;
        }

        @Override
        public boolean tryAdvance(Consumer<? super K> consumer) {
            return this.i.tryAdvance(consumer);
        }

        @Override
        public void forEachRemaining(Consumer<? super K> consumer) {
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
        public Comparator<? super K> getComparator() {
            return ObjectComparators.asObjectComparator(this.i.getComparator());
        }

        @Override
        public ObjectSpliterator<K> trySplit() {
            Spliterator<K> spliterator = this.i.trySplit();
            if (spliterator == null) {
                return null;
            }
            return new SpliteratorWrapper<K>(spliterator);
        }

        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }

    private static class SpliteratorWrapperWithComparator<K>
    extends SpliteratorWrapper<K> {
        final Comparator<? super K> comparator;

        public SpliteratorWrapperWithComparator(Spliterator<K> spliterator, Comparator<? super K> comparator) {
            super(spliterator);
            this.comparator = comparator;
        }

        @Override
        public Comparator<? super K> getComparator() {
            return this.comparator;
        }

        @Override
        public ObjectSpliterator<K> trySplit() {
            Spliterator spliterator = this.i.trySplit();
            if (spliterator == null) {
                return null;
            }
            return new SpliteratorWrapperWithComparator<K>(spliterator, this.comparator);
        }

        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }

    private static class SpliteratorConcatenator<K>
    implements ObjectSpliterator<K> {
        private static final int EMPTY_CHARACTERISTICS = 16448;
        private static final int CHARACTERISTICS_NOT_SUPPORTED_WHILE_MULTIPLE = 5;
        final ObjectSpliterator<? extends K>[] a;
        int offset;
        int length;
        long remainingEstimatedExceptCurrent = Long.MAX_VALUE;
        int characteristics = 0;

        public SpliteratorConcatenator(ObjectSpliterator<? extends K>[] objectSpliteratorArray, int n, int n2) {
            this.a = objectSpliteratorArray;
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
        public boolean tryAdvance(Consumer<? super K> consumer) {
            boolean bl = false;
            while (this.length > 0) {
                if (this.a[this.offset].tryAdvance(consumer)) {
                    bl = true;
                    break;
                }
                this.advanceNextSpliterator();
            }
            return bl;
        }

        @Override
        public void forEachRemaining(Consumer<? super K> consumer) {
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
        public Comparator<? super K> getComparator() {
            if (this.length == 1 && (this.characteristics & 4) != 0) {
                return this.a[this.offset].getComparator();
            }
            throw new IllegalStateException();
        }

        @Override
        public ObjectSpliterator<K> trySplit() {
            switch (this.length) {
                case 0: {
                    return null;
                }
                case 1: {
                    Spliterator spliterator = this.a[this.offset].trySplit();
                    this.characteristics = this.a[this.offset].characteristics();
                    return spliterator;
                }
                case 2: {
                    ObjectSpliterator<? extends K> objectSpliterator = this.a[this.offset++];
                    --this.length;
                    this.characteristics = this.a[this.offset].characteristics();
                    this.remainingEstimatedExceptCurrent = 0L;
                    return objectSpliterator;
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
            return new SpliteratorConcatenator<K>(this.a, n2, n4);
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
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }

    private static class SpliteratorFromIterator<K>
    implements ObjectSpliterator<K> {
        private static final int BATCH_INCREMENT_SIZE = 1024;
        private static final int BATCH_MAX_SIZE = 0x2000000;
        private final ObjectIterator<? extends K> iter;
        final int characteristics;
        private final boolean knownSize;
        private long size = Long.MAX_VALUE;
        private int nextBatchSize = 1024;
        private ObjectSpliterator<K> delegate = null;

        SpliteratorFromIterator(ObjectIterator<? extends K> objectIterator, int n) {
            this.iter = objectIterator;
            this.characteristics = 0 | n;
            this.knownSize = false;
        }

        SpliteratorFromIterator(ObjectIterator<? extends K> objectIterator, long l, int n) {
            this.iter = objectIterator;
            this.knownSize = true;
            this.size = l;
            this.characteristics = (n & 0x1000) != 0 ? 0 | n : 0x4040 | n;
        }

        @Override
        public boolean tryAdvance(Consumer<? super K> consumer) {
            if (this.delegate != null) {
                boolean bl = this.delegate.tryAdvance(consumer);
                if (!bl) {
                    this.delegate = null;
                }
                return bl;
            }
            if (!this.iter.hasNext()) {
                return true;
            }
            --this.size;
            consumer.accept(this.iter.next());
            return false;
        }

        @Override
        public void forEachRemaining(Consumer<? super K> consumer) {
            if (this.delegate != null) {
                this.delegate.forEachRemaining(consumer);
                this.delegate = null;
            }
            this.iter.forEachRemaining(consumer);
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

        protected ObjectSpliterator<K> makeForSplit(K[] KArray, int n) {
            return ObjectSpliterators.wrap(KArray, 0, n, this.characteristics);
        }

        @Override
        public ObjectSpliterator<K> trySplit() {
            if (!this.iter.hasNext()) {
                return null;
            }
            int n = this.knownSize && this.size > 0L ? (int)Math.min((long)this.nextBatchSize, this.size) : this.nextBatchSize;
            Object[] objectArray = new Object[n];
            int n2 = 0;
            while (n2 < n && this.iter.hasNext()) {
                objectArray[n2++] = this.iter.next();
                --this.size;
            }
            if (n < this.nextBatchSize && this.iter.hasNext()) {
                objectArray = Arrays.copyOf(objectArray, this.nextBatchSize);
                while (this.iter.hasNext() && n2 < this.nextBatchSize) {
                    objectArray[n2++] = this.iter.next();
                    --this.size;
                }
            }
            this.nextBatchSize = Math.min(0x2000000, this.nextBatchSize + 1024);
            ObjectSpliterator<Object> objectSpliterator = this.makeForSplit(objectArray, n2);
            if (!this.iter.hasNext()) {
                this.delegate = objectSpliterator;
                return objectSpliterator.trySplit();
            }
            return objectSpliterator;
        }

        @Override
        public long skip(long l) {
            long l2;
            int n;
            if (l < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + l);
            }
            if (this.iter instanceof ObjectBigListIterator) {
                long l3 = ((ObjectBigListIterator)this.iter).skip(l);
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
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }

    private static class SpliteratorFromIteratorWithComparator<K>
    extends SpliteratorFromIterator<K> {
        private final Comparator<? super K> comparator;

        SpliteratorFromIteratorWithComparator(ObjectIterator<? extends K> objectIterator, int n, Comparator<? super K> comparator) {
            super(objectIterator, n | 0x14);
            this.comparator = comparator;
        }

        SpliteratorFromIteratorWithComparator(ObjectIterator<? extends K> objectIterator, long l, int n, Comparator<? super K> comparator) {
            super(objectIterator, l, n | 0x14);
            this.comparator = comparator;
        }

        @Override
        public Comparator<? super K> getComparator() {
            return this.comparator;
        }

        @Override
        protected ObjectSpliterator<K> makeForSplit(K[] KArray, int n) {
            return ObjectSpliterators.wrapPreSorted(KArray, 0, n, this.characteristics, this.comparator);
        }
    }

    private static final class IteratorFromSpliterator<K>
    implements ObjectIterator<K>,
    Consumer<K> {
        private final ObjectSpliterator<? extends K> spliterator;
        private K holder = null;
        private boolean hasPeeked = false;

        IteratorFromSpliterator(ObjectSpliterator<? extends K> objectSpliterator) {
            this.spliterator = objectSpliterator;
        }

        @Override
        public void accept(K k) {
            this.holder = k;
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
        public K next() {
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
        public void forEachRemaining(Consumer<? super K> consumer) {
            if (this.hasPeeked) {
                this.hasPeeked = false;
                consumer.accept(this.holder);
            }
            this.spliterator.forEachRemaining(consumer);
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
    }

    public static abstract class LateBindingSizeIndexBasedSpliterator<K>
    extends AbstractIndexBasedSpliterator<K> {
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
        public ObjectSpliterator<K> trySplit() {
            Spliterator spliterator = super.trySplit();
            if (!this.maxPosFixed && spliterator != null) {
                this.maxPos = this.getMaxPosFromBackingStore();
                this.maxPosFixed = true;
            }
            return spliterator;
        }

        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }

    public static abstract class EarlyBindingSizeIndexBasedSpliterator<K>
    extends AbstractIndexBasedSpliterator<K> {
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

    public static abstract class AbstractIndexBasedSpliterator<K>
    extends AbstractObjectSpliterator<K> {
        protected int pos;

        protected AbstractIndexBasedSpliterator(int n) {
            this.pos = n;
        }

        protected abstract K get(int var1);

        protected abstract int getMaxPos();

        protected abstract ObjectSpliterator<K> makeForSplit(int var1, int var2);

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
        public boolean tryAdvance(Consumer<? super K> consumer) {
            if (this.pos >= this.getMaxPos()) {
                return true;
            }
            consumer.accept(this.get(this.pos++));
            return false;
        }

        @Override
        public void forEachRemaining(Consumer<? super K> consumer) {
            int n = this.getMaxPos();
            while (this.pos < n) {
                consumer.accept(this.get(this.pos));
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
        public ObjectSpliterator<K> trySplit() {
            int n = this.getMaxPos();
            int n2 = this.computeSplitPoint();
            if (n2 == this.pos || n2 == n) {
                return null;
            }
            this.splitPointCheck(n2, n);
            int n3 = this.pos;
            ObjectSpliterator<K> objectSpliterator = this.makeForSplit(n3, n2);
            if (objectSpliterator != null) {
                this.pos = n2;
            }
            return objectSpliterator;
        }

        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
}

