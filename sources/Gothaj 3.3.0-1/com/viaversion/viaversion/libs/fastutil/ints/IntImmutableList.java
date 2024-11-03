package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.SafeMath;
import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.stream.IntStream;

public class IntImmutableList extends IntLists.ImmutableListBase implements IntList, RandomAccess, Cloneable, Serializable {
   private static final long serialVersionUID = 0L;
   static final IntImmutableList EMPTY = new IntImmutableList(IntArrays.EMPTY_ARRAY);
   private final int[] a;

   public IntImmutableList(int[] a) {
      this.a = a;
   }

   public IntImmutableList(Collection<? extends Integer> c) {
      this(c.isEmpty() ? IntArrays.EMPTY_ARRAY : IntIterators.unwrap(IntIterators.asIntIterator(c.iterator())));
   }

   public IntImmutableList(IntCollection c) {
      this(c.isEmpty() ? IntArrays.EMPTY_ARRAY : IntIterators.unwrap(c.iterator()));
   }

   public IntImmutableList(IntList l) {
      this(l.isEmpty() ? IntArrays.EMPTY_ARRAY : new int[l.size()]);
      l.getElements(0, this.a, 0, l.size());
   }

   public IntImmutableList(int[] a, int offset, int length) {
      this(length == 0 ? IntArrays.EMPTY_ARRAY : new int[length]);
      System.arraycopy(a, offset, this.a, 0, length);
   }

   public IntImmutableList(IntIterator i) {
      this(i.hasNext() ? IntIterators.unwrap(i) : IntArrays.EMPTY_ARRAY);
   }

   public static IntImmutableList of() {
      return EMPTY;
   }

   public static IntImmutableList of(int... init) {
      return init.length == 0 ? of() : new IntImmutableList(init);
   }

   private static IntImmutableList convertTrustedToImmutableList(IntArrayList arrayList) {
      if (arrayList.isEmpty()) {
         return of();
      } else {
         int[] backingArray = arrayList.elements();
         if (arrayList.size() != backingArray.length) {
            backingArray = Arrays.copyOf(backingArray, arrayList.size());
         }

         return new IntImmutableList(backingArray);
      }
   }

   public static IntImmutableList toList(IntStream stream) {
      return convertTrustedToImmutableList(IntArrayList.toList(stream));
   }

   public static IntImmutableList toListWithExpectedSize(IntStream stream, int expectedSize) {
      return convertTrustedToImmutableList(IntArrayList.toListWithExpectedSize(stream, expectedSize));
   }

   @Override
   public int getInt(int index) {
      if (index >= this.a.length) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.a.length + ")");
      } else {
         return this.a[index];
      }
   }

   @Override
   public int indexOf(int k) {
      int i = 0;

      for (int size = this.a.length; i < size; i++) {
         if (k == this.a[i]) {
            return i;
         }
      }

      return -1;
   }

   @Override
   public int lastIndexOf(int k) {
      int i = this.a.length;

      while (i-- != 0) {
         if (k == this.a[i]) {
            return i;
         }
      }

      return -1;
   }

   @Override
   public int size() {
      return this.a.length;
   }

   @Override
   public boolean isEmpty() {
      return this.a.length == 0;
   }

   @Override
   public void getElements(int from, int[] a, int offset, int length) {
      IntArrays.ensureOffsetLength(a, offset, length);
      System.arraycopy(this.a, from, a, offset, length);
   }

   @Override
   public void forEach(java.util.function.IntConsumer action) {
      for (int i = 0; i < this.a.length; i++) {
         action.accept(this.a[i]);
      }
   }

   @Override
   public int[] toIntArray() {
      return this.a.length == 0 ? IntArrays.EMPTY_ARRAY : (int[])this.a.clone();
   }

   @Override
   public int[] toArray(int[] a) {
      if (a == null || a.length < this.size()) {
         a = new int[this.a.length];
      }

      System.arraycopy(this.a, 0, a, 0, a.length);
      return a;
   }

   @Override
   public IntListIterator listIterator(final int index) {
      this.ensureIndex(index);
      return new IntListIterator() {
         int pos = index;

         @Override
         public boolean hasNext() {
            return this.pos < IntImmutableList.this.a.length;
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
               return IntImmutableList.this.a[this.pos++];
            }
         }

         @Override
         public int previousInt() {
            if (!this.hasPrevious()) {
               throw new NoSuchElementException();
            } else {
               return IntImmutableList.this.a[--this.pos];
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
         public void forEachRemaining(java.util.function.IntConsumer action) {
            while (this.pos < IntImmutableList.this.a.length) {
               action.accept(IntImmutableList.this.a[this.pos++]);
            }
         }

         @Override
         public void add(int k) {
            throw new UnsupportedOperationException();
         }

         @Override
         public void set(int k) {
            throw new UnsupportedOperationException();
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException();
         }

         @Override
         public int back(int n) {
            if (n < 0) {
               throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            } else {
               int remaining = IntImmutableList.this.a.length - this.pos;
               if (n < remaining) {
                  this.pos -= n;
               } else {
                  n = remaining;
                  this.pos = 0;
               }

               return n;
            }
         }

         @Override
         public int skip(int n) {
            if (n < 0) {
               throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            } else {
               int remaining = IntImmutableList.this.a.length - this.pos;
               if (n < remaining) {
                  this.pos += n;
               } else {
                  n = remaining;
                  this.pos = IntImmutableList.this.a.length;
               }

               return n;
            }
         }
      };
   }

   @Override
   public IntSpliterator spliterator() {
      return new IntImmutableList.Spliterator();
   }

   @Override
   public IntList subList(int from, int to) {
      if (from == 0 && to == this.size()) {
         return this;
      } else {
         this.ensureIndex(from);
         this.ensureIndex(to);
         if (from == to) {
            return EMPTY;
         } else if (from > to) {
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
         } else {
            return new IntImmutableList.ImmutableSubList(this, from, to);
         }
      }
   }

   public IntImmutableList clone() {
      return this;
   }

   public boolean equals(IntImmutableList l) {
      if (l == this) {
         return true;
      } else if (this.a == l.a) {
         return true;
      } else {
         int s = this.size();
         if (s != l.size()) {
            return false;
         } else {
            int[] a1 = this.a;
            int[] a2 = l.a;
            return Arrays.equals(a1, a2);
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
      } else if (o instanceof IntImmutableList) {
         return this.equals((IntImmutableList)o);
      } else {
         return o instanceof IntImmutableList.ImmutableSubList ? ((IntImmutableList.ImmutableSubList)o).equals(this) : super.equals(o);
      }
   }

   public int compareTo(IntImmutableList l) {
      if (this.a == l.a) {
         return 0;
      } else {
         int s1 = this.size();
         int s2 = l.size();
         int[] a1 = this.a;
         int[] a2 = l.a;

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
      if (l instanceof IntImmutableList) {
         return this.compareTo((IntImmutableList)l);
      } else if (l instanceof IntImmutableList.ImmutableSubList) {
         IntImmutableList.ImmutableSubList other = (IntImmutableList.ImmutableSubList)l;
         return -other.compareTo(this);
      } else {
         return super.compareTo(l);
      }
   }

   private static final class ImmutableSubList extends IntLists.ImmutableListBase implements RandomAccess, Serializable {
      private static final long serialVersionUID = 7054639518438982401L;
      final IntImmutableList innerList;
      final int from;
      final int to;
      final transient int[] a;

      ImmutableSubList(IntImmutableList innerList, int from, int to) {
         this.innerList = innerList;
         this.from = from;
         this.to = to;
         this.a = innerList.a;
      }

      @Override
      public int getInt(int index) {
         this.ensureRestrictedIndex(index);
         return this.a[index + this.from];
      }

      @Override
      public int indexOf(int k) {
         for (int i = this.from; i < this.to; i++) {
            if (k == this.a[i]) {
               return i - this.from;
            }
         }

         return -1;
      }

      @Override
      public int lastIndexOf(int k) {
         int i = this.to;

         while (i-- != this.from) {
            if (k == this.a[i]) {
               return i - this.from;
            }
         }

         return -1;
      }

      @Override
      public int size() {
         return this.to - this.from;
      }

      @Override
      public boolean isEmpty() {
         return this.to <= this.from;
      }

      @Override
      public void getElements(int fromSublistIndex, int[] a, int offset, int length) {
         IntArrays.ensureOffsetLength(a, offset, length);
         this.ensureRestrictedIndex(fromSublistIndex);
         if (this.from + length > this.to) {
            throw new IndexOutOfBoundsException(
               "Final index "
                  + (this.from + length)
                  + " (startingIndex: "
                  + this.from
                  + " + length: "
                  + length
                  + ") is greater then list length "
                  + this.size()
            );
         } else {
            System.arraycopy(this.a, fromSublistIndex + this.from, a, offset, length);
         }
      }

      @Override
      public void forEach(java.util.function.IntConsumer action) {
         for (int i = this.from; i < this.to; i++) {
            action.accept(this.a[i]);
         }
      }

      @Override
      public int[] toIntArray() {
         return Arrays.copyOfRange(this.a, this.from, this.to);
      }

      @Override
      public int[] toArray(int[] a) {
         if (a == null || a.length < this.size()) {
            a = new int[this.size()];
         }

         System.arraycopy(this.a, this.from, a, 0, this.size());
         return a;
      }

      @Override
      public IntListIterator listIterator(final int index) {
         this.ensureIndex(index);
         return new IntListIterator() {
            int pos = index;

            @Override
            public boolean hasNext() {
               return this.pos < ImmutableSubList.this.to;
            }

            @Override
            public boolean hasPrevious() {
               return this.pos > ImmutableSubList.this.from;
            }

            @Override
            public int nextInt() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  return ImmutableSubList.this.a[this.pos++ + ImmutableSubList.this.from];
               }
            }

            @Override
            public int previousInt() {
               if (!this.hasPrevious()) {
                  throw new NoSuchElementException();
               } else {
                  return ImmutableSubList.this.a[--this.pos + ImmutableSubList.this.from];
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
            public void forEachRemaining(java.util.function.IntConsumer action) {
               while (this.pos < ImmutableSubList.this.to) {
                  action.accept(ImmutableSubList.this.a[this.pos++ + ImmutableSubList.this.from]);
               }
            }

            @Override
            public void add(int k) {
               throw new UnsupportedOperationException();
            }

            @Override
            public void set(int k) {
               throw new UnsupportedOperationException();
            }

            @Override
            public void remove() {
               throw new UnsupportedOperationException();
            }

            @Override
            public int back(int n) {
               if (n < 0) {
                  throw new IllegalArgumentException("Argument must be nonnegative: " + n);
               } else {
                  int remaining = ImmutableSubList.this.to - this.pos;
                  if (n < remaining) {
                     this.pos -= n;
                  } else {
                     n = remaining;
                     this.pos = 0;
                  }

                  return n;
               }
            }

            @Override
            public int skip(int n) {
               if (n < 0) {
                  throw new IllegalArgumentException("Argument must be nonnegative: " + n);
               } else {
                  int remaining = ImmutableSubList.this.to - this.pos;
                  if (n < remaining) {
                     this.pos += n;
                  } else {
                     n = remaining;
                     this.pos = ImmutableSubList.this.to;
                  }

                  return n;
               }
            }
         };
      }

      @Override
      public IntSpliterator spliterator() {
         return new IntImmutableList.ImmutableSubList.SubListSpliterator();
      }

      boolean contentsEquals(int[] otherA, int otherAFrom, int otherATo) {
         if (this.a == otherA && this.from == otherAFrom && this.to == otherATo) {
            return true;
         } else if (otherATo - otherAFrom != this.size()) {
            return false;
         } else {
            int pos = this.from;
            int otherPos = otherAFrom;

            while (pos < this.to) {
               if (this.a[pos++] != otherA[otherPos++]) {
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
         } else if (o instanceof IntImmutableList) {
            IntImmutableList other = (IntImmutableList)o;
            return this.contentsEquals(other.a, 0, other.size());
         } else if (o instanceof IntImmutableList.ImmutableSubList) {
            IntImmutableList.ImmutableSubList other = (IntImmutableList.ImmutableSubList)o;
            return this.contentsEquals(other.a, other.from, other.to);
         } else {
            return super.equals(o);
         }
      }

      int contentsCompareTo(int[] otherA, int otherAFrom, int otherATo) {
         if (this.a == otherA && this.from == otherAFrom && this.to == otherATo) {
            return 0;
         } else {
            int i = this.from;

            for (int j = otherAFrom; i < this.to && i < otherATo; j++) {
               int e1 = this.a[i];
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
         if (l instanceof IntImmutableList) {
            IntImmutableList other = (IntImmutableList)l;
            return this.contentsCompareTo(other.a, 0, other.size());
         } else if (l instanceof IntImmutableList.ImmutableSubList) {
            IntImmutableList.ImmutableSubList other = (IntImmutableList.ImmutableSubList)l;
            return this.contentsCompareTo(other.a, other.from, other.to);
         } else {
            return super.compareTo(l);
         }
      }

      private Object readResolve() throws ObjectStreamException {
         try {
            return this.innerList.subList(this.from, this.to);
         } catch (IndexOutOfBoundsException | IllegalArgumentException var2) {
            throw (InvalidObjectException)new InvalidObjectException(var2.getMessage()).initCause(var2);
         }
      }

      @Override
      public IntList subList(int from, int to) {
         this.ensureIndex(from);
         this.ensureIndex(to);
         if (from == to) {
            return IntImmutableList.EMPTY;
         } else if (from > to) {
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
         } else {
            return new IntImmutableList.ImmutableSubList(this.innerList, from + this.from, to + this.from);
         }
      }

      private final class SubListSpliterator extends IntSpliterators.EarlyBindingSizeIndexBasedSpliterator {
         SubListSpliterator() {
            super(ImmutableSubList.this.from, ImmutableSubList.this.to);
         }

         private SubListSpliterator(int pos, int maxPos) {
            super(pos, maxPos);
         }

         @Override
         protected final int get(int i) {
            return ImmutableSubList.this.a[i];
         }

         protected final IntImmutableList.ImmutableSubList.SubListSpliterator makeForSplit(int pos, int maxPos) {
            return ImmutableSubList.this.new SubListSpliterator(pos, maxPos);
         }

         @Override
         public boolean tryAdvance(java.util.function.IntConsumer action) {
            if (this.pos >= this.maxPos) {
               return false;
            } else {
               action.accept(ImmutableSubList.this.a[this.pos++]);
               return true;
            }
         }

         @Override
         public void forEachRemaining(java.util.function.IntConsumer action) {
            int max = this.maxPos;

            while (this.pos < max) {
               action.accept(ImmutableSubList.this.a[this.pos++]);
            }
         }

         @Override
         public int characteristics() {
            return 17744;
         }
      }
   }

   private final class Spliterator implements IntSpliterator {
      int pos;
      int max;

      public Spliterator() {
         this(0, IntImmutableList.this.a.length);
      }

      private Spliterator(int pos, int max) {
         assert pos <= max : "pos " + pos + " must be <= max " + max;

         this.pos = pos;
         this.max = max;
      }

      @Override
      public int characteristics() {
         return 17744;
      }

      @Override
      public long estimateSize() {
         return (long)(this.max - this.pos);
      }

      @Override
      public boolean tryAdvance(java.util.function.IntConsumer action) {
         if (this.pos >= this.max) {
            return false;
         } else {
            action.accept(IntImmutableList.this.a[this.pos++]);
            return true;
         }
      }

      @Override
      public void forEachRemaining(java.util.function.IntConsumer action) {
         while (this.pos < this.max) {
            action.accept(IntImmutableList.this.a[this.pos]);
            this.pos++;
         }
      }

      @Override
      public long skip(long n) {
         if (n < 0L) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else if (this.pos >= this.max) {
            return 0L;
         } else {
            int remaining = this.max - this.pos;
            if (n < (long)remaining) {
               this.pos = SafeMath.safeLongToInt((long)this.pos + n);
               return n;
            } else {
               n = (long)remaining;
               this.pos = this.max;
               return n;
            }
         }
      }

      @Override
      public IntSpliterator trySplit() {
         int retLen = this.max - this.pos >> 1;
         if (retLen <= 1) {
            return null;
         } else {
            int myNewPos = this.pos + retLen;
            int oldPos = this.pos;
            this.pos = myNewPos;
            return IntImmutableList.this.new Spliterator(oldPos, myNewPos);
         }
      }
   }
}
