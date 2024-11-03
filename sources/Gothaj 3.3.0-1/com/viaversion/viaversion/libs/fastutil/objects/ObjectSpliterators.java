package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.SafeMath;
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
   public static final ObjectSpliterators.EmptySpliterator EMPTY_SPLITERATOR = new ObjectSpliterators.EmptySpliterator();

   private ObjectSpliterators() {
   }

   public static <K> ObjectSpliterator<K> emptySpliterator() {
      return EMPTY_SPLITERATOR;
   }

   public static <K> ObjectSpliterator<K> singleton(K element) {
      return new ObjectSpliterators.SingletonSpliterator<>(element);
   }

   public static <K> ObjectSpliterator<K> singleton(K element, Comparator<? super K> comparator) {
      return new ObjectSpliterators.SingletonSpliterator<>(element, comparator);
   }

   public static <K> ObjectSpliterator<K> wrap(K[] array, int offset, int length) {
      ObjectArrays.ensureOffsetLength(array, offset, length);
      return new ObjectSpliterators.ArraySpliterator<>(array, offset, length, 0);
   }

   public static <K> ObjectSpliterator<K> wrap(K[] array) {
      return new ObjectSpliterators.ArraySpliterator<>(array, 0, array.length, 0);
   }

   public static <K> ObjectSpliterator<K> wrap(K[] array, int offset, int length, int additionalCharacteristics) {
      ObjectArrays.ensureOffsetLength(array, offset, length);
      return new ObjectSpliterators.ArraySpliterator<>(array, offset, length, additionalCharacteristics);
   }

   public static <K> ObjectSpliterator<K> wrapPreSorted(K[] array, int offset, int length, int additionalCharacteristics, Comparator<? super K> comparator) {
      ObjectArrays.ensureOffsetLength(array, offset, length);
      return new ObjectSpliterators.ArraySpliteratorWithComparator<>(array, offset, length, additionalCharacteristics, comparator);
   }

   public static <K> ObjectSpliterator<K> wrapPreSorted(K[] array, int offset, int length, Comparator<? super K> comparator) {
      return wrapPreSorted(array, offset, length, 0, comparator);
   }

   public static <K> ObjectSpliterator<K> wrapPreSorted(K[] array, Comparator<? super K> comparator) {
      return wrapPreSorted(array, 0, array.length, comparator);
   }

   public static <K> ObjectSpliterator<K> asObjectSpliterator(Spliterator<K> i) {
      return (ObjectSpliterator<K>)(i instanceof ObjectSpliterator ? (ObjectSpliterator)i : new ObjectSpliterators.SpliteratorWrapper<>(i));
   }

   public static <K> ObjectSpliterator<K> asObjectSpliterator(Spliterator<K> i, Comparator<? super K> comparatorOverride) {
      if (i instanceof ObjectSpliterator) {
         throw new IllegalArgumentException("Cannot override comparator on instance that is already a " + ObjectSpliterator.class.getSimpleName());
      } else {
         return new ObjectSpliterators.SpliteratorWrapperWithComparator<>(i, comparatorOverride);
      }
   }

   public static <K> void onEachMatching(Spliterator<K> spliterator, Predicate<? super K> predicate, Consumer<? super K> action) {
      Objects.requireNonNull(predicate);
      Objects.requireNonNull(action);
      spliterator.forEachRemaining(value -> {
         if (predicate.test(value)) {
            action.accept(value);
         }
      });
   }

   @SafeVarargs
   public static <K> ObjectSpliterator<K> concat(ObjectSpliterator<? extends K>... a) {
      return concat(a, 0, a.length);
   }

   public static <K> ObjectSpliterator<K> concat(ObjectSpliterator<? extends K>[] a, int offset, int length) {
      return new ObjectSpliterators.SpliteratorConcatenator<>(a, offset, length);
   }

   public static <K> ObjectSpliterator<K> asSpliterator(ObjectIterator<? extends K> iter, long size, int additionalCharacterisitcs) {
      return new ObjectSpliterators.SpliteratorFromIterator<>(iter, size, additionalCharacterisitcs);
   }

   public static <K> ObjectSpliterator<K> asSpliteratorFromSorted(
      ObjectIterator<? extends K> iter, long size, int additionalCharacterisitcs, Comparator<? super K> comparator
   ) {
      return new ObjectSpliterators.SpliteratorFromIteratorWithComparator<>(iter, size, additionalCharacterisitcs, comparator);
   }

   public static <K> ObjectSpliterator<K> asSpliteratorUnknownSize(ObjectIterator<? extends K> iter, int characterisitcs) {
      return new ObjectSpliterators.SpliteratorFromIterator<>(iter, characterisitcs);
   }

   public static <K> ObjectSpliterator<K> asSpliteratorFromSortedUnknownSize(
      ObjectIterator<? extends K> iter, int additionalCharacterisitcs, Comparator<? super K> comparator
   ) {
      return new ObjectSpliterators.SpliteratorFromIteratorWithComparator<>(iter, additionalCharacterisitcs, comparator);
   }

   public static <K> ObjectIterator<K> asIterator(ObjectSpliterator<? extends K> spliterator) {
      return new ObjectSpliterators.IteratorFromSpliterator<>(spliterator);
   }

   public abstract static class AbstractIndexBasedSpliterator<K> extends AbstractObjectSpliterator<K> {
      protected int pos;

      protected AbstractIndexBasedSpliterator(int initialPos) {
         this.pos = initialPos;
      }

      protected abstract K get(int var1);

      protected abstract int getMaxPos();

      protected abstract ObjectSpliterator<K> makeForSplit(int var1, int var2);

      protected int computeSplitPoint() {
         return this.pos + (this.getMaxPos() - this.pos) / 2;
      }

      private void splitPointCheck(int splitPoint, int observedMax) {
         if (splitPoint < this.pos || splitPoint > observedMax) {
            throw new IndexOutOfBoundsException(
               "splitPoint " + splitPoint + " outside of range of current position " + this.pos + " and range end " + observedMax
            );
         }
      }

      @Override
      public int characteristics() {
         return 16464;
      }

      @Override
      public long estimateSize() {
         return (long)this.getMaxPos() - (long)this.pos;
      }

      @Override
      public boolean tryAdvance(Consumer<? super K> action) {
         if (this.pos >= this.getMaxPos()) {
            return false;
         } else {
            action.accept(this.get(this.pos++));
            return true;
         }
      }

      @Override
      public void forEachRemaining(Consumer<? super K> action) {
         for (int max = this.getMaxPos(); this.pos < max; this.pos++) {
            action.accept(this.get(this.pos));
         }
      }

      @Override
      public long skip(long n) {
         if (n < 0L) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else {
            int max = this.getMaxPos();
            if (this.pos >= max) {
               return 0L;
            } else {
               int remaining = max - this.pos;
               if (n < (long)remaining) {
                  this.pos = SafeMath.safeLongToInt((long)this.pos + n);
                  return n;
               } else {
                  n = (long)remaining;
                  this.pos = max;
                  return n;
               }
            }
         }
      }

      @Override
      public ObjectSpliterator<K> trySplit() {
         int max = this.getMaxPos();
         int splitPoint = this.computeSplitPoint();
         if (splitPoint != this.pos && splitPoint != max) {
            this.splitPointCheck(splitPoint, max);
            int oldPos = this.pos;
            ObjectSpliterator<K> maybeSplit = this.makeForSplit(oldPos, splitPoint);
            if (maybeSplit != null) {
               this.pos = splitPoint;
            }

            return maybeSplit;
         } else {
            return null;
         }
      }
   }

   private static class ArraySpliterator<K> implements ObjectSpliterator<K> {
      private static final int BASE_CHARACTERISTICS = 16464;
      final K[] array;
      private final int offset;
      private int length;
      private int curr;
      final int characteristics;

      public ArraySpliterator(K[] array, int offset, int length, int additionalCharacteristics) {
         this.array = array;
         this.offset = offset;
         this.length = length;
         this.characteristics = 16464 | additionalCharacteristics;
      }

      @Override
      public boolean tryAdvance(Consumer<? super K> action) {
         if (this.curr >= this.length) {
            return false;
         } else {
            Objects.requireNonNull(action);
            action.accept(this.array[this.offset + this.curr++]);
            return true;
         }
      }

      @Override
      public long estimateSize() {
         return (long)(this.length - this.curr);
      }

      @Override
      public int characteristics() {
         return this.characteristics;
      }

      protected ObjectSpliterators.ArraySpliterator<K> makeForSplit(int newOffset, int newLength) {
         return new ObjectSpliterators.ArraySpliterator<>(this.array, newOffset, newLength, this.characteristics);
      }

      @Override
      public ObjectSpliterator<K> trySplit() {
         int retLength = this.length - this.curr >> 1;
         if (retLength <= 1) {
            return null;
         } else {
            int myNewCurr = this.curr + retLength;
            int retOffset = this.offset + this.curr;
            this.curr = myNewCurr;
            return this.makeForSplit(retOffset, retLength);
         }
      }

      @Override
      public void forEachRemaining(Consumer<? super K> action) {
         Objects.requireNonNull(action);

         while (this.curr < this.length) {
            action.accept(this.array[this.offset + this.curr]);
            this.curr++;
         }
      }

      @Override
      public long skip(long n) {
         if (n < 0L) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else if (this.curr >= this.length) {
            return 0L;
         } else {
            int remaining = this.length - this.curr;
            if (n < (long)remaining) {
               this.curr = SafeMath.safeLongToInt((long)this.curr + n);
               return n;
            } else {
               n = (long)remaining;
               this.curr = this.length;
               return n;
            }
         }
      }
   }

   private static class ArraySpliteratorWithComparator<K> extends ObjectSpliterators.ArraySpliterator<K> {
      private final Comparator<? super K> comparator;

      public ArraySpliteratorWithComparator(K[] array, int offset, int length, int additionalCharacteristics, Comparator<? super K> comparator) {
         super(array, offset, length, additionalCharacteristics | 20);
         this.comparator = comparator;
      }

      protected ObjectSpliterators.ArraySpliteratorWithComparator<K> makeForSplit(int newOffset, int newLength) {
         return new ObjectSpliterators.ArraySpliteratorWithComparator<>(this.array, newOffset, newLength, this.characteristics, this.comparator);
      }

      @Override
      public Comparator<? super K> getComparator() {
         return this.comparator;
      }
   }

   public abstract static class EarlyBindingSizeIndexBasedSpliterator<K> extends ObjectSpliterators.AbstractIndexBasedSpliterator<K> {
      protected final int maxPos;

      protected EarlyBindingSizeIndexBasedSpliterator(int initialPos, int maxPos) {
         super(initialPos);
         this.maxPos = maxPos;
      }

      @Override
      protected final int getMaxPos() {
         return this.maxPos;
      }
   }

   public static class EmptySpliterator<K> implements ObjectSpliterator<K>, Serializable, Cloneable {
      private static final long serialVersionUID = 8379247926738230492L;
      private static final int CHARACTERISTICS = 16448;

      protected EmptySpliterator() {
      }

      @Override
      public boolean tryAdvance(Consumer<? super K> action) {
         return false;
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
         return 16448;
      }

      @Override
      public void forEachRemaining(Consumer<? super K> action) {
      }

      @Override
      public Object clone() {
         return ObjectSpliterators.EMPTY_SPLITERATOR;
      }

      private Object readResolve() {
         return ObjectSpliterators.EMPTY_SPLITERATOR;
      }
   }

   private static final class IteratorFromSpliterator<K> implements ObjectIterator<K>, Consumer<K> {
      private final ObjectSpliterator<? extends K> spliterator;
      private K holder = (K)null;
      private boolean hasPeeked = false;

      IteratorFromSpliterator(ObjectSpliterator<? extends K> spliterator) {
         this.spliterator = spliterator;
      }

      @Override
      public void accept(K item) {
         this.holder = item;
      }

      @Override
      public boolean hasNext() {
         if (this.hasPeeked) {
            return true;
         } else {
            boolean hadElement = this.spliterator.tryAdvance(this);
            if (!hadElement) {
               return false;
            } else {
               this.hasPeeked = true;
               return true;
            }
         }
      }

      @Override
      public K next() {
         if (this.hasPeeked) {
            this.hasPeeked = false;
            return this.holder;
         } else {
            boolean hadElement = this.spliterator.tryAdvance(this);
            if (!hadElement) {
               throw new NoSuchElementException();
            } else {
               return this.holder;
            }
         }
      }

      @Override
      public void forEachRemaining(Consumer<? super K> action) {
         if (this.hasPeeked) {
            this.hasPeeked = false;
            action.accept(this.holder);
         }

         this.spliterator.forEachRemaining(action);
      }

      @Override
      public int skip(int n) {
         if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else {
            int skipped = 0;
            if (this.hasPeeked) {
               this.hasPeeked = false;
               this.spliterator.skip(1L);
               skipped++;
               n--;
            }

            if (n > 0) {
               skipped += SafeMath.safeLongToInt(this.spliterator.skip((long)n));
            }

            return skipped;
         }
      }
   }

   public abstract static class LateBindingSizeIndexBasedSpliterator<K> extends ObjectSpliterators.AbstractIndexBasedSpliterator<K> {
      protected int maxPos = -1;
      private boolean maxPosFixed;

      protected LateBindingSizeIndexBasedSpliterator(int initialPos) {
         super(initialPos);
         this.maxPosFixed = false;
      }

      protected LateBindingSizeIndexBasedSpliterator(int initialPos, int fixedMaxPos) {
         super(initialPos);
         this.maxPos = fixedMaxPos;
         this.maxPosFixed = true;
      }

      protected abstract int getMaxPosFromBackingStore();

      @Override
      protected final int getMaxPos() {
         return this.maxPosFixed ? this.maxPos : this.getMaxPosFromBackingStore();
      }

      @Override
      public ObjectSpliterator<K> trySplit() {
         ObjectSpliterator<K> maybeSplit = super.trySplit();
         if (!this.maxPosFixed && maybeSplit != null) {
            this.maxPos = this.getMaxPosFromBackingStore();
            this.maxPosFixed = true;
         }

         return maybeSplit;
      }
   }

   private static class SingletonSpliterator<K> implements ObjectSpliterator<K> {
      private final K element;
      private final Comparator<? super K> comparator;
      private boolean consumed = false;
      private static final int CHARACTERISTICS = 17493;

      public SingletonSpliterator(K element) {
         this(element, null);
      }

      public SingletonSpliterator(K element, Comparator<? super K> comparator) {
         this.element = element;
         this.comparator = comparator;
      }

      @Override
      public boolean tryAdvance(Consumer<? super K> action) {
         Objects.requireNonNull(action);
         if (this.consumed) {
            return false;
         } else {
            this.consumed = true;
            action.accept(this.element);
            return true;
         }
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
         return 17493 | (this.element != null ? 256 : 0);
      }

      @Override
      public void forEachRemaining(Consumer<? super K> action) {
         Objects.requireNonNull(action);
         if (!this.consumed) {
            this.consumed = true;
            action.accept(this.element);
         }
      }

      @Override
      public Comparator<? super K> getComparator() {
         return this.comparator;
      }

      @Override
      public long skip(long n) {
         if (n < 0L) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else if (n != 0L && !this.consumed) {
            this.consumed = true;
            return 1L;
         } else {
            return 0L;
         }
      }
   }

   private static class SpliteratorConcatenator<K> implements ObjectSpliterator<K> {
      private static final int EMPTY_CHARACTERISTICS = 16448;
      private static final int CHARACTERISTICS_NOT_SUPPORTED_WHILE_MULTIPLE = 5;
      final ObjectSpliterator<? extends K>[] a;
      int offset;
      int length;
      long remainingEstimatedExceptCurrent = Long.MAX_VALUE;
      int characteristics = 0;

      public SpliteratorConcatenator(ObjectSpliterator<? extends K>[] a, int offset, int length) {
         this.a = a;
         this.offset = offset;
         this.length = length;
         this.remainingEstimatedExceptCurrent = this.recomputeRemaining();
         this.characteristics = this.computeCharacteristics();
      }

      private long recomputeRemaining() {
         int curLength = this.length - 1;
         int curOffset = this.offset + 1;
         long result = 0L;

         while (curLength > 0) {
            long cur = this.a[curOffset++].estimateSize();
            curLength--;
            if (cur == Long.MAX_VALUE) {
               return Long.MAX_VALUE;
            }

            result += cur;
            if (result == Long.MAX_VALUE || result < 0L) {
               return Long.MAX_VALUE;
            }
         }

         return result;
      }

      private int computeCharacteristics() {
         if (this.length <= 0) {
            return 16448;
         } else {
            int current = -1;
            int curLength = this.length;
            int curOffset = this.offset;
            if (curLength > 1) {
               current &= -6;
            }

            while (curLength > 0) {
               current &= this.a[curOffset++].characteristics();
               curLength--;
            }

            return current;
         }
      }

      private void advanceNextSpliterator() {
         if (this.length <= 0) {
            throw new AssertionError("advanceNextSpliterator() called with none remaining");
         } else {
            this.offset++;
            this.length--;
            this.remainingEstimatedExceptCurrent = this.recomputeRemaining();
         }
      }

      @Override
      public boolean tryAdvance(Consumer<? super K> action) {
         boolean any = false;

         while (this.length > 0) {
            if (this.a[this.offset].tryAdvance(action)) {
               any = true;
               break;
            }

            this.advanceNextSpliterator();
         }

         return any;
      }

      @Override
      public void forEachRemaining(Consumer<? super K> action) {
         while (this.length > 0) {
            this.a[this.offset].forEachRemaining(action);
            this.advanceNextSpliterator();
         }
      }

      @Override
      public long estimateSize() {
         if (this.length <= 0) {
            return 0L;
         } else {
            long est = this.a[this.offset].estimateSize() + this.remainingEstimatedExceptCurrent;
            return est < 0L ? Long.MAX_VALUE : est;
         }
      }

      @Override
      public int characteristics() {
         return this.characteristics;
      }

      @Override
      public Comparator<? super K> getComparator() {
         if (this.length == 1 && (this.characteristics & 4) != 0) {
            return this.a[this.offset].getComparator();
         } else {
            throw new IllegalStateException();
         }
      }

      @Override
      public ObjectSpliterator<K> trySplit() {
         switch (this.length) {
            case 0:
               return null;
            case 1: {
               ObjectSpliterator<K> split = (ObjectSpliterator<K>)this.a[this.offset].trySplit();
               this.characteristics = this.a[this.offset].characteristics();
               return split;
            }
            case 2: {
               ObjectSpliterator<K> split = (ObjectSpliterator<K>)this.a[this.offset++];
               this.length--;
               this.characteristics = this.a[this.offset].characteristics();
               this.remainingEstimatedExceptCurrent = 0L;
               return split;
            }
            default:
               int mid = this.length >> 1;
               int ret_offset = this.offset;
               int new_offset = this.offset + mid;
               int new_length = this.length - mid;
               this.offset = new_offset;
               this.length = new_length;
               this.remainingEstimatedExceptCurrent = this.recomputeRemaining();
               this.characteristics = this.computeCharacteristics();
               return new ObjectSpliterators.SpliteratorConcatenator<>(this.a, ret_offset, mid);
         }
      }

      @Override
      public long skip(long n) {
         long skipped = 0L;
         if (this.length <= 0) {
            return 0L;
         } else {
            while (skipped < n && this.length >= 0) {
               long curSkipped = this.a[this.offset].skip(n - skipped);
               skipped += curSkipped;
               if (skipped < n) {
                  this.advanceNextSpliterator();
               }
            }

            return skipped;
         }
      }
   }

   private static class SpliteratorFromIterator<K> implements ObjectSpliterator<K> {
      private static final int BATCH_INCREMENT_SIZE = 1024;
      private static final int BATCH_MAX_SIZE = 33554432;
      private final ObjectIterator<? extends K> iter;
      final int characteristics;
      private final boolean knownSize;
      private long size = Long.MAX_VALUE;
      private int nextBatchSize = 1024;
      private ObjectSpliterator<K> delegate = null;

      SpliteratorFromIterator(ObjectIterator<? extends K> iter, int characteristics) {
         this.iter = iter;
         this.characteristics = 0 | characteristics;
         this.knownSize = false;
      }

      SpliteratorFromIterator(ObjectIterator<? extends K> iter, long size, int additionalCharacteristics) {
         this.iter = iter;
         this.knownSize = true;
         this.size = size;
         if ((additionalCharacteristics & 4096) != 0) {
            this.characteristics = 0 | additionalCharacteristics;
         } else {
            this.characteristics = 16448 | additionalCharacteristics;
         }
      }

      @Override
      public boolean tryAdvance(Consumer<? super K> action) {
         if (this.delegate != null) {
            boolean hadRemaining = this.delegate.tryAdvance(action);
            if (!hadRemaining) {
               this.delegate = null;
            }

            return hadRemaining;
         } else if (!this.iter.hasNext()) {
            return false;
         } else {
            this.size--;
            action.accept((K)this.iter.next());
            return true;
         }
      }

      @Override
      public void forEachRemaining(Consumer<? super K> action) {
         if (this.delegate != null) {
            this.delegate.forEachRemaining(action);
            this.delegate = null;
         }

         this.iter.forEachRemaining(action);
         this.size = 0L;
      }

      @Override
      public long estimateSize() {
         if (this.delegate != null) {
            return this.delegate.estimateSize();
         } else if (!this.iter.hasNext()) {
            return 0L;
         } else {
            return this.knownSize && this.size >= 0L ? this.size : Long.MAX_VALUE;
         }
      }

      @Override
      public int characteristics() {
         return this.characteristics;
      }

      protected ObjectSpliterator<K> makeForSplit(K[] batch, int len) {
         return ObjectSpliterators.wrap(batch, 0, len, this.characteristics);
      }

      @Override
      public ObjectSpliterator<K> trySplit() {
         if (!this.iter.hasNext()) {
            return null;
         } else {
            int batchSizeEst = this.knownSize && this.size > 0L ? (int)Math.min((long)this.nextBatchSize, this.size) : this.nextBatchSize;
            K[] batch = (K[])(new Object[batchSizeEst]);

            int actualSeen;
            for (actualSeen = 0; actualSeen < batchSizeEst && this.iter.hasNext(); this.size--) {
               batch[actualSeen++] = (K)this.iter.next();
            }

            if (batchSizeEst < this.nextBatchSize && this.iter.hasNext()) {
               for (batch = Arrays.copyOf(batch, this.nextBatchSize); this.iter.hasNext() && actualSeen < this.nextBatchSize; this.size--) {
                  batch[actualSeen++] = (K)this.iter.next();
               }
            }

            this.nextBatchSize = Math.min(33554432, this.nextBatchSize + 1024);
            ObjectSpliterator<K> split = this.makeForSplit(batch, actualSeen);
            if (!this.iter.hasNext()) {
               this.delegate = split;
               return split.trySplit();
            } else {
               return split;
            }
         }
      }

      @Override
      public long skip(long n) {
         if (n < 0L) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else if (this.iter instanceof ObjectBigListIterator) {
            long skipped = ((ObjectBigListIterator)this.iter).skip(n);
            this.size -= skipped;
            return skipped;
         } else {
            long skippedSoFar = 0L;

            while (skippedSoFar < n && this.iter.hasNext()) {
               int skipped = this.iter.skip(SafeMath.safeLongToInt(Math.min(n, 2147483647L)));
               this.size -= (long)skipped;
               skippedSoFar += (long)skipped;
            }

            return skippedSoFar;
         }
      }
   }

   private static class SpliteratorFromIteratorWithComparator<K> extends ObjectSpliterators.SpliteratorFromIterator<K> {
      private final Comparator<? super K> comparator;

      SpliteratorFromIteratorWithComparator(ObjectIterator<? extends K> iter, int additionalCharacteristics, Comparator<? super K> comparator) {
         super(iter, additionalCharacteristics | 20);
         this.comparator = comparator;
      }

      SpliteratorFromIteratorWithComparator(ObjectIterator<? extends K> iter, long size, int additionalCharacteristics, Comparator<? super K> comparator) {
         super(iter, size, additionalCharacteristics | 20);
         this.comparator = comparator;
      }

      @Override
      public Comparator<? super K> getComparator() {
         return this.comparator;
      }

      @Override
      protected ObjectSpliterator<K> makeForSplit(K[] array, int len) {
         return ObjectSpliterators.wrapPreSorted(array, 0, len, this.characteristics, this.comparator);
      }
   }

   private static class SpliteratorWrapper<K> implements ObjectSpliterator<K> {
      final Spliterator<K> i;

      public SpliteratorWrapper(Spliterator<K> i) {
         this.i = i;
      }

      @Override
      public boolean tryAdvance(Consumer<? super K> action) {
         return this.i.tryAdvance(action);
      }

      @Override
      public void forEachRemaining(Consumer<? super K> action) {
         this.i.forEachRemaining(action);
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
         Spliterator<K> innerSplit = this.i.trySplit();
         return innerSplit == null ? null : new ObjectSpliterators.SpliteratorWrapper<>(innerSplit);
      }
   }

   private static class SpliteratorWrapperWithComparator<K> extends ObjectSpliterators.SpliteratorWrapper<K> {
      final Comparator<? super K> comparator;

      public SpliteratorWrapperWithComparator(Spliterator<K> i, Comparator<? super K> comparator) {
         super(i);
         this.comparator = comparator;
      }

      @Override
      public Comparator<? super K> getComparator() {
         return this.comparator;
      }

      @Override
      public ObjectSpliterator<K> trySplit() {
         Spliterator<K> innerSplit = this.i.trySplit();
         return innerSplit == null ? null : new ObjectSpliterators.SpliteratorWrapperWithComparator<>(innerSplit, this.comparator);
      }
   }
}
