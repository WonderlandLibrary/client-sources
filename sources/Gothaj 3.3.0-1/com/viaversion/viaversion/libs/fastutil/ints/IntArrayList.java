package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.SafeMath;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.stream.IntStream;

public class IntArrayList extends AbstractIntList implements RandomAccess, Cloneable, Serializable {
   private static final long serialVersionUID = -7046029254386353130L;
   public static final int DEFAULT_INITIAL_CAPACITY = 10;
   protected transient int[] a;
   protected int size;

   private static final int[] copyArraySafe(int[] a, int length) {
      return length == 0 ? IntArrays.EMPTY_ARRAY : Arrays.copyOf(a, length);
   }

   private static final int[] copyArrayFromSafe(IntArrayList l) {
      return copyArraySafe(l.a, l.size);
   }

   protected IntArrayList(int[] a, boolean wrapped) {
      this.a = a;
   }

   private void initArrayFromCapacity(int capacity) {
      if (capacity < 0) {
         throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative");
      } else {
         if (capacity == 0) {
            this.a = IntArrays.EMPTY_ARRAY;
         } else {
            this.a = new int[capacity];
         }
      }
   }

   public IntArrayList(int capacity) {
      this.initArrayFromCapacity(capacity);
   }

   public IntArrayList() {
      this.a = IntArrays.DEFAULT_EMPTY_ARRAY;
   }

   public IntArrayList(Collection<? extends Integer> c) {
      if (c instanceof IntArrayList) {
         this.a = copyArrayFromSafe((IntArrayList)c);
         this.size = this.a.length;
      } else {
         this.initArrayFromCapacity(c.size());
         if (c instanceof IntList) {
            ((IntList)c).getElements(0, this.a, 0, this.size = c.size());
         } else {
            this.size = IntIterators.unwrap(IntIterators.asIntIterator(c.iterator()), this.a);
         }
      }
   }

   public IntArrayList(IntCollection c) {
      if (c instanceof IntArrayList) {
         this.a = copyArrayFromSafe((IntArrayList)c);
         this.size = this.a.length;
      } else {
         this.initArrayFromCapacity(c.size());
         if (c instanceof IntList) {
            ((IntList)c).getElements(0, this.a, 0, this.size = c.size());
         } else {
            this.size = IntIterators.unwrap(c.iterator(), this.a);
         }
      }
   }

   public IntArrayList(IntList l) {
      if (l instanceof IntArrayList) {
         this.a = copyArrayFromSafe((IntArrayList)l);
         this.size = this.a.length;
      } else {
         this.initArrayFromCapacity(l.size());
         l.getElements(0, this.a, 0, this.size = l.size());
      }
   }

   public IntArrayList(int[] a) {
      this(a, 0, a.length);
   }

   public IntArrayList(int[] a, int offset, int length) {
      this(length);
      System.arraycopy(a, offset, this.a, 0, length);
      this.size = length;
   }

   public IntArrayList(Iterator<? extends Integer> i) {
      this();

      while (i.hasNext()) {
         this.add(i.next());
      }
   }

   public IntArrayList(IntIterator i) {
      this();

      while (i.hasNext()) {
         this.add(i.nextInt());
      }
   }

   public int[] elements() {
      return this.a;
   }

   public static IntArrayList wrap(int[] a, int length) {
      if (length > a.length) {
         throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + a.length + ")");
      } else {
         IntArrayList l = new IntArrayList(a, true);
         l.size = length;
         return l;
      }
   }

   public static IntArrayList wrap(int[] a) {
      return wrap(a, a.length);
   }

   public static IntArrayList of() {
      return new IntArrayList();
   }

   public static IntArrayList of(int... init) {
      return wrap(init);
   }

   public static IntArrayList toList(IntStream stream) {
      return stream.collect(IntArrayList::new, IntArrayList::add, IntList::addAll);
   }

   public static IntArrayList toListWithExpectedSize(IntStream stream, int expectedSize) {
      return expectedSize <= 10
         ? toList(stream)
         : stream.collect(
            new IntCollections.SizeDecreasingSupplier<>(expectedSize, size -> size <= 10 ? new IntArrayList() : new IntArrayList(size)),
            IntArrayList::add,
            IntList::addAll
         );
   }

   public void ensureCapacity(int capacity) {
      if (capacity > this.a.length && (this.a != IntArrays.DEFAULT_EMPTY_ARRAY || capacity > 10)) {
         this.a = IntArrays.ensureCapacity(this.a, capacity, this.size);

         assert this.size <= this.a.length;
      }
   }

   private void grow(int capacity) {
      if (capacity > this.a.length) {
         if (this.a != IntArrays.DEFAULT_EMPTY_ARRAY) {
            capacity = (int)Math.max(Math.min((long)this.a.length + (long)(this.a.length >> 1), 2147483639L), (long)capacity);
         } else if (capacity < 10) {
            capacity = 10;
         }

         this.a = IntArrays.forceCapacity(this.a, capacity, this.size);

         assert this.size <= this.a.length;
      }
   }

   @Override
   public void add(int index, int k) {
      this.ensureIndex(index);
      this.grow(this.size + 1);
      if (index != this.size) {
         System.arraycopy(this.a, index, this.a, index + 1, this.size - index);
      }

      this.a[index] = k;
      this.size++;

      assert this.size <= this.a.length;
   }

   @Override
   public boolean add(int k) {
      this.grow(this.size + 1);
      this.a[this.size++] = k;

      assert this.size <= this.a.length;

      return true;
   }

   @Override
   public int getInt(int index) {
      if (index >= this.size) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
      } else {
         return this.a[index];
      }
   }

   @Override
   public int indexOf(int k) {
      for (int i = 0; i < this.size; i++) {
         if (k == this.a[i]) {
            return i;
         }
      }

      return -1;
   }

   @Override
   public int lastIndexOf(int k) {
      int i = this.size;

      while (i-- != 0) {
         if (k == this.a[i]) {
            return i;
         }
      }

      return -1;
   }

   @Override
   public int removeInt(int index) {
      if (index >= this.size) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
      } else {
         int old = this.a[index];
         this.size--;
         if (index != this.size) {
            System.arraycopy(this.a, index + 1, this.a, index, this.size - index);
         }

         assert this.size <= this.a.length;

         return old;
      }
   }

   @Override
   public boolean rem(int k) {
      int index = this.indexOf(k);
      if (index == -1) {
         return false;
      } else {
         this.removeInt(index);

         assert this.size <= this.a.length;

         return true;
      }
   }

   @Override
   public int set(int index, int k) {
      if (index >= this.size) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
      } else {
         int old = this.a[index];
         this.a[index] = k;
         return old;
      }
   }

   @Override
   public void clear() {
      this.size = 0;

      assert this.size <= this.a.length;
   }

   @Override
   public int size() {
      return this.size;
   }

   @Override
   public void size(int size) {
      if (size > this.a.length) {
         this.a = IntArrays.forceCapacity(this.a, size, this.size);
      }

      if (size > this.size) {
         Arrays.fill(this.a, this.size, size, 0);
      }

      this.size = size;
   }

   @Override
   public boolean isEmpty() {
      return this.size == 0;
   }

   public void trim() {
      this.trim(0);
   }

   public void trim(int n) {
      if (n < this.a.length && this.size != this.a.length) {
         int[] t = new int[Math.max(n, this.size)];
         System.arraycopy(this.a, 0, t, 0, this.size);
         this.a = t;

         assert this.size <= this.a.length;
      }
   }

   @Override
   public IntList subList(int from, int to) {
      if (from == 0 && to == this.size()) {
         return this;
      } else {
         this.ensureIndex(from);
         this.ensureIndex(to);
         if (from > to) {
            throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
         } else {
            return new IntArrayList.SubList(from, to);
         }
      }
   }

   @Override
   public void getElements(int from, int[] a, int offset, int length) {
      IntArrays.ensureOffsetLength(a, offset, length);
      System.arraycopy(this.a, from, a, offset, length);
   }

   @Override
   public void removeElements(int from, int to) {
      com.viaversion.viaversion.libs.fastutil.Arrays.ensureFromTo(this.size, from, to);
      System.arraycopy(this.a, to, this.a, from, this.size - to);
      this.size -= to - from;
   }

   @Override
   public void addElements(int index, int[] a, int offset, int length) {
      this.ensureIndex(index);
      IntArrays.ensureOffsetLength(a, offset, length);
      this.grow(this.size + length);
      System.arraycopy(this.a, index, this.a, index + length, this.size - index);
      System.arraycopy(a, offset, this.a, index, length);
      this.size += length;
   }

   @Override
   public void setElements(int index, int[] a, int offset, int length) {
      this.ensureIndex(index);
      IntArrays.ensureOffsetLength(a, offset, length);
      if (index + length > this.size) {
         throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size + ")");
      } else {
         System.arraycopy(a, offset, this.a, index, length);
      }
   }

   @Override
   public void forEach(java.util.function.IntConsumer action) {
      for (int i = 0; i < this.size; i++) {
         action.accept(this.a[i]);
      }
   }

   @Override
   public boolean addAll(int index, IntCollection c) {
      if (c instanceof IntList) {
         return this.addAll(index, (IntList)c);
      } else {
         this.ensureIndex(index);
         int n = c.size();
         if (n == 0) {
            return false;
         } else {
            this.grow(this.size + n);
            System.arraycopy(this.a, index, this.a, index + n, this.size - index);
            IntIterator i = c.iterator();
            this.size += n;

            while (n-- != 0) {
               this.a[index++] = i.nextInt();
            }

            assert this.size <= this.a.length;

            return true;
         }
      }
   }

   @Override
   public boolean addAll(int index, IntList l) {
      this.ensureIndex(index);
      int n = l.size();
      if (n == 0) {
         return false;
      } else {
         this.grow(this.size + n);
         System.arraycopy(this.a, index, this.a, index + n, this.size - index);
         l.getElements(0, this.a, index, n);
         this.size += n;

         assert this.size <= this.a.length;

         return true;
      }
   }

   @Override
   public boolean removeAll(IntCollection c) {
      int[] a = this.a;
      int j = 0;

      for (int i = 0; i < this.size; i++) {
         if (!c.contains(a[i])) {
            a[j++] = a[i];
         }
      }

      boolean modified = this.size != j;
      this.size = j;
      return modified;
   }

   @Override
   public int[] toArray(int[] a) {
      if (a == null || a.length < this.size) {
         a = Arrays.copyOf(a, this.size);
      }

      System.arraycopy(this.a, 0, a, 0, this.size);
      return a;
   }

   @Override
   public IntListIterator listIterator(final int index) {
      this.ensureIndex(index);
      return new IntListIterator() {
         int pos = index;
         int last = -1;

         @Override
         public boolean hasNext() {
            return this.pos < IntArrayList.this.size;
         }

         @Override
         public boolean hasPrevious() {
            return this.pos > 0;
         }

         @Override
         public int nextInt() {
            if (!this.hasNext()) {
               throw new NoSuchElementException();
            } else {
               return IntArrayList.this.a[this.last = this.pos++];
            }
         }

         @Override
         public int previousInt() {
            if (!this.hasPrevious()) {
               throw new NoSuchElementException();
            } else {
               return IntArrayList.this.a[this.last = --this.pos];
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
         public void add(int k) {
            IntArrayList.this.add(this.pos++, k);
            this.last = -1;
         }

         @Override
         public void set(int k) {
            if (this.last == -1) {
               throw new IllegalStateException();
            } else {
               IntArrayList.this.set(this.last, k);
            }
         }

         @Override
         public void remove() {
            if (this.last == -1) {
               throw new IllegalStateException();
            } else {
               IntArrayList.this.removeInt(this.last);
               if (this.last < this.pos) {
                  this.pos--;
               }

               this.last = -1;
            }
         }

         @Override
         public void forEachRemaining(java.util.function.IntConsumer action) {
            while (this.pos < IntArrayList.this.size) {
               action.accept(IntArrayList.this.a[this.last = this.pos++]);
            }
         }

         @Override
         public int back(int n) {
            if (n < 0) {
               throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            } else {
               int remaining = IntArrayList.this.size - this.pos;
               if (n < remaining) {
                  this.pos -= n;
               } else {
                  n = remaining;
                  this.pos = 0;
               }

               this.last = this.pos;
               return n;
            }
         }

         @Override
         public int skip(int n) {
            if (n < 0) {
               throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            } else {
               int remaining = IntArrayList.this.size - this.pos;
               if (n < remaining) {
                  this.pos += n;
               } else {
                  n = remaining;
                  this.pos = IntArrayList.this.size;
               }

               this.last = this.pos - 1;
               return n;
            }
         }
      };
   }

   @Override
   public IntSpliterator spliterator() {
      return new IntArrayList.Spliterator();
   }

   @Override
   public void sort(IntComparator comp) {
      if (comp == null) {
         IntArrays.stableSort(this.a, 0, this.size);
      } else {
         IntArrays.stableSort(this.a, 0, this.size, comp);
      }
   }

   @Override
   public void unstableSort(IntComparator comp) {
      if (comp == null) {
         IntArrays.unstableSort(this.a, 0, this.size);
      } else {
         IntArrays.unstableSort(this.a, 0, this.size, comp);
      }
   }

   public IntArrayList clone() {
      IntArrayList cloned = null;
      if (this.getClass() == IntArrayList.class) {
         cloned = new IntArrayList(copyArraySafe(this.a, this.size), false);
         cloned.size = this.size;
      } else {
         try {
            cloned = (IntArrayList)super.clone();
         } catch (CloneNotSupportedException var3) {
            throw new InternalError(var3);
         }

         cloned.a = copyArraySafe(this.a, this.size);
      }

      return cloned;
   }

   public boolean equals(IntArrayList l) {
      if (l == this) {
         return true;
      } else {
         int s = this.size();
         if (s != l.size()) {
            return false;
         } else {
            int[] a1 = this.a;
            int[] a2 = l.a;
            if (a1 == a2 && s == l.size()) {
               return true;
            } else {
               while (s-- != 0) {
                  if (a1[s] != a2[s]) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (o == null) {
         return false;
      } else if (!(o instanceof List)) {
         return false;
      } else if (o instanceof IntArrayList) {
         return this.equals((IntArrayList)o);
      } else {
         return o instanceof IntArrayList.SubList ? ((IntArrayList.SubList)o).equals(this) : super.equals(o);
      }
   }

   public int compareTo(IntArrayList l) {
      int s1 = this.size();
      int s2 = l.size();
      int[] a1 = this.a;
      int[] a2 = l.a;
      if (a1 == a2 && s1 == s2) {
         return 0;
      } else {
         int i;
         for (i = 0; i < s1 && i < s2; i++) {
            int e1 = a1[i];
            int e2 = a2[i];
            int r;
            if ((r = Integer.compare(e1, e2)) != 0) {
               return r;
            }
         }

         return i < s2 ? -1 : (i < s1 ? 1 : 0);
      }
   }

   @Override
   public int compareTo(List<? extends Integer> l) {
      if (l instanceof IntArrayList) {
         return this.compareTo((IntArrayList)l);
      } else {
         return l instanceof IntArrayList.SubList ? -((IntArrayList.SubList)l).compareTo(this) : super.compareTo(l);
      }
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      s.defaultWriteObject();

      for (int i = 0; i < this.size; i++) {
         s.writeInt(this.a[i]);
      }
   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.a = new int[this.size];

      for (int i = 0; i < this.size; i++) {
         this.a[i] = s.readInt();
      }
   }

   private final class Spliterator implements IntSpliterator {
      boolean hasSplit = false;
      int pos;
      int max;

      public Spliterator() {
         this(0, IntArrayList.this.size, false);
      }

      private Spliterator(int pos, int max, boolean hasSplit) {
         assert pos <= max : "pos " + pos + " must be <= max " + max;

         this.pos = pos;
         this.max = max;
         this.hasSplit = hasSplit;
      }

      private int getWorkingMax() {
         return this.hasSplit ? this.max : IntArrayList.this.size;
      }

      @Override
      public int characteristics() {
         return 16720;
      }

      @Override
      public long estimateSize() {
         return (long)(this.getWorkingMax() - this.pos);
      }

      @Override
      public boolean tryAdvance(java.util.function.IntConsumer action) {
         if (this.pos >= this.getWorkingMax()) {
            return false;
         } else {
            action.accept(IntArrayList.this.a[this.pos++]);
            return true;
         }
      }

      @Override
      public void forEachRemaining(java.util.function.IntConsumer action) {
         for (int max = this.getWorkingMax(); this.pos < max; this.pos++) {
            action.accept(IntArrayList.this.a[this.pos]);
         }
      }

      @Override
      public long skip(long n) {
         if (n < 0L) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else {
            int max = this.getWorkingMax();
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
      public IntSpliterator trySplit() {
         int max = this.getWorkingMax();
         int retLen = max - this.pos >> 1;
         if (retLen <= 1) {
            return null;
         } else {
            this.max = max;
            int myNewPos = this.pos + retLen;
            int oldPos = this.pos;
            this.pos = myNewPos;
            this.hasSplit = true;
            return IntArrayList.this.new Spliterator(oldPos, myNewPos, true);
         }
      }
   }

   private class SubList extends AbstractIntList.IntRandomAccessSubList {
      private static final long serialVersionUID = -3185226345314976296L;

      protected SubList(int from, int to) {
         super(IntArrayList.this, from, to);
      }

      private int[] getParentArray() {
         return IntArrayList.this.a;
      }

      @Override
      public int getInt(int i) {
         this.ensureRestrictedIndex(i);
         return IntArrayList.this.a[i + this.from];
      }

      @Override
      public IntListIterator listIterator(int index) {
         return new IntArrayList.SubList.SubListIterator(index);
      }

      @Override
      public IntSpliterator spliterator() {
         return new IntArrayList.SubList.SubListSpliterator();
      }

      boolean contentsEquals(int[] otherA, int otherAFrom, int otherATo) {
         if (IntArrayList.this.a == otherA && this.from == otherAFrom && this.to == otherATo) {
            return true;
         } else if (otherATo - otherAFrom != this.size()) {
            return false;
         } else {
            int pos = this.from;
            int otherPos = otherAFrom;

            while (pos < this.to) {
               if (IntArrayList.this.a[pos++] != otherA[otherPos++]) {
                  return false;
               }
            }

            return true;
         }
      }

      @Override
      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else if (o == null) {
            return false;
         } else if (!(o instanceof List)) {
            return false;
         } else if (o instanceof IntArrayList) {
            IntArrayList other = (IntArrayList)o;
            return this.contentsEquals(other.a, 0, other.size());
         } else if (o instanceof IntArrayList.SubList) {
            IntArrayList.SubList other = (IntArrayList.SubList)o;
            return this.contentsEquals(other.getParentArray(), other.from, other.to);
         } else {
            return super.equals(o);
         }
      }

      int contentsCompareTo(int[] otherA, int otherAFrom, int otherATo) {
         if (IntArrayList.this.a == otherA && this.from == otherAFrom && this.to == otherATo) {
            return 0;
         } else {
            int i = this.from;

            for (int j = otherAFrom; i < this.to && i < otherATo; j++) {
               int e1 = IntArrayList.this.a[i];
               int e2 = otherA[j];
               int r;
               if ((r = Integer.compare(e1, e2)) != 0) {
                  return r;
               }

               i++;
            }

            return i < otherATo ? -1 : (i < this.to ? 1 : 0);
         }
      }

      @Override
      public int compareTo(List<? extends Integer> l) {
         if (l instanceof IntArrayList) {
            IntArrayList other = (IntArrayList)l;
            return this.contentsCompareTo(other.a, 0, other.size());
         } else if (l instanceof IntArrayList.SubList) {
            IntArrayList.SubList other = (IntArrayList.SubList)l;
            return this.contentsCompareTo(other.getParentArray(), other.from, other.to);
         } else {
            return super.compareTo(l);
         }
      }

      private final class SubListIterator extends IntIterators.AbstractIndexBasedListIterator {
         SubListIterator(int index) {
            super(0, index);
         }

         @Override
         protected final int get(int i) {
            return IntArrayList.this.a[SubList.this.from + i];
         }

         @Override
         protected final void add(int i, int k) {
            SubList.this.add(i, k);
         }

         @Override
         protected final void set(int i, int k) {
            SubList.this.set(i, k);
         }

         @Override
         protected final void remove(int i) {
            SubList.this.removeInt(i);
         }

         @Override
         protected final int getMaxPos() {
            return SubList.this.to - SubList.this.from;
         }

         @Override
         public int nextInt() {
            if (!this.hasNext()) {
               throw new NoSuchElementException();
            } else {
               return IntArrayList.this.a[SubList.this.from + (this.lastReturned = this.pos++)];
            }
         }

         @Override
         public int previousInt() {
            if (!this.hasPrevious()) {
               throw new NoSuchElementException();
            } else {
               return IntArrayList.this.a[SubList.this.from + (this.lastReturned = --this.pos)];
            }
         }

         @Override
         public void forEachRemaining(java.util.function.IntConsumer action) {
            int max = SubList.this.to - SubList.this.from;

            while (this.pos < max) {
               action.accept(IntArrayList.this.a[SubList.this.from + (this.lastReturned = this.pos++)]);
            }
         }
      }

      private final class SubListSpliterator extends IntSpliterators.LateBindingSizeIndexBasedSpliterator {
         SubListSpliterator() {
            super(SubList.this.from);
         }

         private SubListSpliterator(int pos, int maxPos) {
            super(pos, maxPos);
         }

         @Override
         protected final int getMaxPosFromBackingStore() {
            return SubList.this.to;
         }

         @Override
         protected final int get(int i) {
            return IntArrayList.this.a[i];
         }

         protected final IntArrayList.SubList.SubListSpliterator makeForSplit(int pos, int maxPos) {
            return SubList.this.new SubListSpliterator(pos, maxPos);
         }

         @Override
         public boolean tryAdvance(java.util.function.IntConsumer action) {
            if (this.pos >= this.getMaxPos()) {
               return false;
            } else {
               action.accept(IntArrayList.this.a[this.pos++]);
               return true;
            }
         }

         @Override
         public void forEachRemaining(java.util.function.IntConsumer action) {
            int max = this.getMaxPos();

            while (this.pos < max) {
               action.accept(IntArrayList.this.a[this.pos++]);
            }
         }
      }
   }
}
