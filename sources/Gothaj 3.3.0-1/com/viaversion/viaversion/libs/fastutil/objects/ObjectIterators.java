package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.BigArrays;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators.UnmodifiableBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators.UnmodifiableIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators.UnmodifiableListIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class ObjectIterators {
   public static final ObjectIterators.EmptyIterator EMPTY_ITERATOR = new ObjectIterators.EmptyIterator();

   private ObjectIterators() {
   }

   public static <K> ObjectIterator<K> emptyIterator() {
      return EMPTY_ITERATOR;
   }

   public static <K> ObjectListIterator<K> singleton(K element) {
      return new ObjectIterators.SingletonIterator<>(element);
   }

   public static <K> ObjectListIterator<K> wrap(K[] array, int offset, int length) {
      ObjectArrays.ensureOffsetLength(array, offset, length);
      return new ObjectIterators.ArrayIterator<>(array, offset, length);
   }

   public static <K> ObjectListIterator<K> wrap(K[] array) {
      return new ObjectIterators.ArrayIterator<>(array, 0, array.length);
   }

   public static <K> int unwrap(Iterator<? extends K> i, K[] array, int offset, int max) {
      if (max < 0) {
         throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
      } else if (offset >= 0 && offset + max <= array.length) {
         int j = max;

         while (j-- != 0 && i.hasNext()) {
            array[offset++] = (K)i.next();
         }

         return max - j - 1;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static <K> int unwrap(Iterator<? extends K> i, K[] array) {
      return unwrap(i, array, 0, array.length);
   }

   public static <K> K[] unwrap(Iterator<? extends K> i, int max) {
      if (max < 0) {
         throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
      } else {
         K[] array = (K[])(new Object[16]);
         int j = 0;

         while (max-- != 0 && i.hasNext()) {
            if (j == array.length) {
               array = (K[])ObjectArrays.grow(array, j + 1);
            }

            array[j++] = (K)i.next();
         }

         return (K[])ObjectArrays.trim(array, j);
      }
   }

   public static <K> K[] unwrap(Iterator<? extends K> i) {
      return unwrap(i, Integer.MAX_VALUE);
   }

   public static <K> long unwrap(Iterator<? extends K> i, K[][] array, long offset, long max) {
      if (max < 0L) {
         throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
      } else if (offset >= 0L && offset + max <= BigArrays.length(array)) {
         long j = max;

         while (j-- != 0L && i.hasNext()) {
            BigArrays.set(array, offset++, (K)i.next());
         }

         return max - j - 1L;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static <K> long unwrap(Iterator<? extends K> i, K[][] array) {
      return unwrap(i, array, 0L, BigArrays.length(array));
   }

   public static <K> int unwrap(Iterator<K> i, ObjectCollection<? super K> c, int max) {
      if (max < 0) {
         throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
      } else {
         int j = max;

         while (j-- != 0 && i.hasNext()) {
            c.add(i.next());
         }

         return max - j - 1;
      }
   }

   public static <K> K[][] unwrapBig(Iterator<? extends K> i, long max) {
      if (max < 0L) {
         throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
      } else {
         K[][] array = (K[][])ObjectBigArrays.newBigArray(16L);
         long j = 0L;

         while (max-- != 0L && i.hasNext()) {
            if (j == BigArrays.length(array)) {
               array = (K[][])BigArrays.grow(array, j + 1L);
            }

            BigArrays.set(array, j++, (K)i.next());
         }

         return (K[][])BigArrays.trim(array, j);
      }
   }

   public static <K> K[][] unwrapBig(Iterator<? extends K> i) {
      return unwrapBig(i, Long.MAX_VALUE);
   }

   public static <K> long unwrap(Iterator<K> i, ObjectCollection<? super K> c) {
      long n;
      for (n = 0L; i.hasNext(); n++) {
         c.add(i.next());
      }

      return n;
   }

   public static <K> int pour(Iterator<K> i, ObjectCollection<? super K> s, int max) {
      if (max < 0) {
         throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
      } else {
         int j = max;

         while (j-- != 0 && i.hasNext()) {
            s.add(i.next());
         }

         return max - j - 1;
      }
   }

   public static <K> int pour(Iterator<K> i, ObjectCollection<? super K> s) {
      return pour(i, s, Integer.MAX_VALUE);
   }

   public static <K> ObjectList<K> pour(Iterator<K> i, int max) {
      ObjectArrayList<K> l = new ObjectArrayList<>();
      pour(i, l, max);
      l.trim();
      return l;
   }

   public static <K> ObjectList<K> pour(Iterator<K> i) {
      return pour(i, Integer.MAX_VALUE);
   }

   public static <K> ObjectIterator<K> asObjectIterator(Iterator<K> i) {
      return (ObjectIterator<K>)(i instanceof ObjectIterator ? (ObjectIterator)i : new ObjectIterators.IteratorWrapper<>(i));
   }

   public static <K> ObjectListIterator<K> asObjectIterator(ListIterator<K> i) {
      return (ObjectListIterator<K>)(i instanceof ObjectListIterator ? (ObjectListIterator)i : new ObjectIterators.ListIteratorWrapper<>(i));
   }

   public static <K> boolean any(Iterator<K> iterator, Predicate<? super K> predicate) {
      return indexOf(iterator, predicate) != -1;
   }

   public static <K> boolean all(Iterator<K> iterator, Predicate<? super K> predicate) {
      Objects.requireNonNull(predicate);

      while (iterator.hasNext()) {
         if (!predicate.test(iterator.next())) {
            return false;
         }
      }

      return true;
   }

   public static <K> int indexOf(Iterator<K> iterator, Predicate<? super K> predicate) {
      for (int i = 0; iterator.hasNext(); i++) {
         if (predicate.test(iterator.next())) {
            return i;
         }
      }

      return -1;
   }

   @SafeVarargs
   public static <K> ObjectIterator<K> concat(ObjectIterator<? extends K>... a) {
      return concat(a, 0, a.length);
   }

   public static <K> ObjectIterator<K> concat(ObjectIterator<? extends K>[] a, int offset, int length) {
      return new ObjectIterators.IteratorConcatenator<>(a, offset, length);
   }

   public static <K> ObjectIterator<K> unmodifiable(ObjectIterator<? extends K> i) {
      return new UnmodifiableIterator(i);
   }

   public static <K> ObjectBidirectionalIterator<K> unmodifiable(ObjectBidirectionalIterator<? extends K> i) {
      return new UnmodifiableBidirectionalIterator(i);
   }

   public static <K> ObjectListIterator<K> unmodifiable(ObjectListIterator<? extends K> i) {
      return new UnmodifiableListIterator(i);
   }

   public abstract static class AbstractIndexBasedIterator<K> extends AbstractObjectIterator<K> {
      protected final int minPos;
      protected int pos;
      protected int lastReturned;

      protected AbstractIndexBasedIterator(int minPos, int initialPos) {
         this.minPos = minPos;
         this.pos = initialPos;
      }

      protected abstract K get(int var1);

      protected abstract void remove(int var1);

      protected abstract int getMaxPos();

      @Override
      public boolean hasNext() {
         return this.pos < this.getMaxPos();
      }

      @Override
      public K next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            return this.get(this.lastReturned = this.pos++);
         }
      }

      @Override
      public void remove() {
         if (this.lastReturned == -1) {
            throw new IllegalStateException();
         } else {
            this.remove(this.lastReturned);
            if (this.lastReturned < this.pos) {
               this.pos--;
            }

            this.lastReturned = -1;
         }
      }

      @Override
      public void forEachRemaining(Consumer<? super K> action) {
         while (this.pos < this.getMaxPos()) {
            action.accept(this.get(this.lastReturned = this.pos++));
         }
      }

      @Override
      public int skip(int n) {
         if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else {
            int max = this.getMaxPos();
            int remaining = max - this.pos;
            if (n < remaining) {
               this.pos += n;
            } else {
               n = remaining;
               this.pos = max;
            }

            this.lastReturned = this.pos - 1;
            return n;
         }
      }
   }

   public abstract static class AbstractIndexBasedListIterator<K> extends ObjectIterators.AbstractIndexBasedIterator<K> implements ObjectListIterator<K> {
      protected AbstractIndexBasedListIterator(int minPos, int initialPos) {
         super(minPos, initialPos);
      }

      protected abstract void add(int var1, K var2);

      protected abstract void set(int var1, K var2);

      @Override
      public boolean hasPrevious() {
         return this.pos > this.minPos;
      }

      @Override
      public K previous() {
         if (!this.hasPrevious()) {
            throw new NoSuchElementException();
         } else {
            return this.get(this.lastReturned = --this.pos);
         }
      }

      @Override
      public int nextIndex() {
         return this.pos;
      }

      @Override
      public int previousIndex() {
         return this.pos - 1;
      }

      @Override
      public void add(K k) {
         this.add(this.pos++, k);
         this.lastReturned = -1;
      }

      @Override
      public void set(K k) {
         if (this.lastReturned == -1) {
            throw new IllegalStateException();
         } else {
            this.set(this.lastReturned, k);
         }
      }

      @Override
      public int back(int n) {
         if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else {
            int remaining = this.pos - this.minPos;
            if (n < remaining) {
               this.pos -= n;
            } else {
               n = remaining;
               this.pos = this.minPos;
            }

            this.lastReturned = this.pos;
            return n;
         }
      }
   }

   private static class ArrayIterator<K> implements ObjectListIterator<K> {
      private final K[] array;
      private final int offset;
      private final int length;
      private int curr;

      public ArrayIterator(K[] array, int offset, int length) {
         this.array = array;
         this.offset = offset;
         this.length = length;
      }

      @Override
      public boolean hasNext() {
         return this.curr < this.length;
      }

      @Override
      public boolean hasPrevious() {
         return this.curr > 0;
      }

      @Override
      public K next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            return this.array[this.offset + this.curr++];
         }
      }

      @Override
      public K previous() {
         if (!this.hasPrevious()) {
            throw new NoSuchElementException();
         } else {
            return this.array[this.offset + --this.curr];
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
      public int skip(int n) {
         if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else if (n <= this.length - this.curr) {
            this.curr += n;
            return n;
         } else {
            n = this.length - this.curr;
            this.curr = this.length;
            return n;
         }
      }

      @Override
      public int back(int n) {
         if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else if (n <= this.curr) {
            this.curr -= n;
            return n;
         } else {
            n = this.curr;
            this.curr = 0;
            return n;
         }
      }

      @Override
      public int nextIndex() {
         return this.curr;
      }

      @Override
      public int previousIndex() {
         return this.curr - 1;
      }
   }

   public static class EmptyIterator<K> implements ObjectListIterator<K>, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptyIterator() {
      }

      @Override
      public boolean hasNext() {
         return false;
      }

      @Override
      public boolean hasPrevious() {
         return false;
      }

      @Override
      public K next() {
         throw new NoSuchElementException();
      }

      @Override
      public K previous() {
         throw new NoSuchElementException();
      }

      @Override
      public int nextIndex() {
         return 0;
      }

      @Override
      public int previousIndex() {
         return -1;
      }

      @Override
      public int skip(int n) {
         return 0;
      }

      @Override
      public int back(int n) {
         return 0;
      }

      @Override
      public void forEachRemaining(Consumer<? super K> action) {
      }

      @Override
      public Object clone() {
         return ObjectIterators.EMPTY_ITERATOR;
      }

      private Object readResolve() {
         return ObjectIterators.EMPTY_ITERATOR;
      }
   }

   private static class IteratorConcatenator<K> implements ObjectIterator<K> {
      final ObjectIterator<? extends K>[] a;
      int offset;
      int length;
      int lastOffset = -1;

      public IteratorConcatenator(ObjectIterator<? extends K>[] a, int offset, int length) {
         this.a = a;
         this.offset = offset;
         this.length = length;
         this.advance();
      }

      private void advance() {
         while (this.length != 0 && !this.a[this.offset].hasNext()) {
            this.length--;
            this.offset++;
         }
      }

      @Override
      public boolean hasNext() {
         return this.length > 0;
      }

      @Override
      public K next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            K next = (K)this.a[this.lastOffset = this.offset].next();
            this.advance();
            return next;
         }
      }

      @Override
      public void forEachRemaining(Consumer<? super K> action) {
         while (this.length > 0) {
            this.a[this.lastOffset = this.offset].forEachRemaining(action);
            this.advance();
         }
      }

      @Override
      public void remove() {
         if (this.lastOffset == -1) {
            throw new IllegalStateException();
         } else {
            this.a[this.lastOffset].remove();
         }
      }

      @Override
      public int skip(int n) {
         if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else {
            this.lastOffset = -1;

            int skipped;
            for (skipped = 0; skipped < n && this.length != 0; this.offset++) {
               skipped += this.a[this.offset].skip(n - skipped);
               if (this.a[this.offset].hasNext()) {
                  break;
               }

               this.length--;
            }

            return skipped;
         }
      }
   }

   private static class IteratorWrapper<K> implements ObjectIterator<K> {
      final Iterator<K> i;

      public IteratorWrapper(Iterator<K> i) {
         this.i = i;
      }

      @Override
      public boolean hasNext() {
         return this.i.hasNext();
      }

      @Override
      public void remove() {
         this.i.remove();
      }

      @Override
      public K next() {
         return this.i.next();
      }

      @Override
      public void forEachRemaining(Consumer<? super K> action) {
         this.i.forEachRemaining(action);
      }
   }

   private static class ListIteratorWrapper<K> implements ObjectListIterator<K> {
      final ListIterator<K> i;

      public ListIteratorWrapper(ListIterator<K> i) {
         this.i = i;
      }

      @Override
      public boolean hasNext() {
         return this.i.hasNext();
      }

      @Override
      public boolean hasPrevious() {
         return this.i.hasPrevious();
      }

      @Override
      public int nextIndex() {
         return this.i.nextIndex();
      }

      @Override
      public int previousIndex() {
         return this.i.previousIndex();
      }

      @Override
      public void set(K k) {
         this.i.set(k);
      }

      @Override
      public void add(K k) {
         this.i.add(k);
      }

      @Override
      public void remove() {
         this.i.remove();
      }

      @Override
      public K next() {
         return this.i.next();
      }

      @Override
      public K previous() {
         return this.i.previous();
      }

      @Override
      public void forEachRemaining(Consumer<? super K> action) {
         this.i.forEachRemaining(action);
      }
   }

   private static class SingletonIterator<K> implements ObjectListIterator<K> {
      private final K element;
      private byte curr;

      public SingletonIterator(K element) {
         this.element = element;
      }

      @Override
      public boolean hasNext() {
         return this.curr == 0;
      }

      @Override
      public boolean hasPrevious() {
         return this.curr == 1;
      }

      @Override
      public K next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this.curr = 1;
            return this.element;
         }
      }

      @Override
      public K previous() {
         if (!this.hasPrevious()) {
            throw new NoSuchElementException();
         } else {
            this.curr = 0;
            return this.element;
         }
      }

      @Override
      public void forEachRemaining(Consumer<? super K> action) {
         Objects.requireNonNull(action);
         if (this.curr == 0) {
            action.accept(this.element);
            this.curr = 1;
         }
      }

      @Override
      public int nextIndex() {
         return this.curr;
      }

      @Override
      public int previousIndex() {
         return this.curr - 1;
      }

      @Override
      public int back(int n) {
         if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else if (n != 0 && this.curr >= 1) {
            this.curr = 1;
            return 1;
         } else {
            return 0;
         }
      }

      @Override
      public int skip(int n) {
         if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else if (n != 0 && this.curr <= 0) {
            this.curr = 0;
            return 1;
         } else {
            return 0;
         }
      }
   }
}
