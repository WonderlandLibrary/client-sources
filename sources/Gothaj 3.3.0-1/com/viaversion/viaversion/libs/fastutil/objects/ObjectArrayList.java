package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.SafeMath;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.Consumer;
import java.util.stream.Collector;

public class ObjectArrayList<K> extends AbstractObjectList<K> implements RandomAccess, Cloneable, Serializable {
   private static final long serialVersionUID = -7046029254386353131L;
   public static final int DEFAULT_INITIAL_CAPACITY = 10;
   protected final boolean wrapped;
   protected transient K[] a;
   protected int size;
   private static final Collector<Object, ?, ObjectArrayList<Object>> TO_LIST_COLLECTOR = Collector.of(
      ObjectArrayList::new, ObjectArrayList::add, ObjectArrayList::combine
   );

   private static final <K> K[] copyArraySafe(K[] a, int length) {
      return (K[])(length == 0 ? ObjectArrays.EMPTY_ARRAY : Arrays.copyOf(a, length, Object[].class));
   }

   private static final <K> K[] copyArrayFromSafe(ObjectArrayList<K> l) {
      return (K[])copyArraySafe(l.a, l.size);
   }

   protected ObjectArrayList(K[] a, boolean wrapped) {
      this.a = a;
      this.wrapped = wrapped;
   }

   private void initArrayFromCapacity(int capacity) {
      if (capacity < 0) {
         throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative");
      } else {
         if (capacity == 0) {
            this.a = (K[])ObjectArrays.EMPTY_ARRAY;
         } else {
            this.a = (K[])(new Object[capacity]);
         }
      }
   }

   public ObjectArrayList(int capacity) {
      this.initArrayFromCapacity(capacity);
      this.wrapped = false;
   }

   public ObjectArrayList() {
      this.a = (K[])ObjectArrays.DEFAULT_EMPTY_ARRAY;
      this.wrapped = false;
   }

   public ObjectArrayList(Collection<? extends K> c) {
      if (c instanceof ObjectArrayList) {
         this.a = (K[])copyArrayFromSafe((ObjectArrayList<? extends K>)c);
         this.size = this.a.length;
      } else {
         this.initArrayFromCapacity(c.size());
         if (c instanceof ObjectList) {
            ((ObjectList)c).getElements(0, this.a, 0, this.size = c.size());
         } else {
            this.size = ObjectIterators.unwrap(c.iterator(), this.a);
         }
      }

      this.wrapped = false;
   }

   public ObjectArrayList(ObjectCollection<? extends K> c) {
      if (c instanceof ObjectArrayList) {
         this.a = (K[])copyArrayFromSafe((ObjectArrayList<? extends K>)c);
         this.size = this.a.length;
      } else {
         this.initArrayFromCapacity(c.size());
         if (c instanceof ObjectList) {
            ((ObjectList)c).getElements(0, this.a, 0, this.size = c.size());
         } else {
            this.size = ObjectIterators.unwrap(c.iterator(), this.a);
         }
      }

      this.wrapped = false;
   }

   public ObjectArrayList(ObjectList<? extends K> l) {
      if (l instanceof ObjectArrayList) {
         this.a = (K[])copyArrayFromSafe((ObjectArrayList<? extends K>)l);
         this.size = this.a.length;
      } else {
         this.initArrayFromCapacity(l.size());
         l.getElements(0, this.a, 0, this.size = l.size());
      }

      this.wrapped = false;
   }

   public ObjectArrayList(K[] a) {
      this(a, 0, a.length);
   }

   public ObjectArrayList(K[] a, int offset, int length) {
      this(length);
      System.arraycopy(a, offset, this.a, 0, length);
      this.size = length;
   }

   public ObjectArrayList(Iterator<? extends K> i) {
      this();

      while (i.hasNext()) {
         this.add((K)i.next());
      }
   }

   public ObjectArrayList(ObjectIterator<? extends K> i) {
      this();

      while (i.hasNext()) {
         this.add((K)i.next());
      }
   }

   public K[] elements() {
      return this.a;
   }

   public static <K> ObjectArrayList<K> wrap(K[] a, int length) {
      if (length > a.length) {
         throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + a.length + ")");
      } else {
         ObjectArrayList<K> l = new ObjectArrayList<>(a, true);
         l.size = length;
         return l;
      }
   }

   public static <K> ObjectArrayList<K> wrap(K[] a) {
      return wrap(a, a.length);
   }

   public static <K> ObjectArrayList<K> of() {
      return new ObjectArrayList<>();
   }

   @SafeVarargs
   public static <K> ObjectArrayList<K> of(K... init) {
      return wrap(init);
   }

   ObjectArrayList<K> combine(ObjectArrayList<? extends K> toAddFrom) {
      this.addAll(toAddFrom);
      return this;
   }

   public static <K> Collector<K, ?, ObjectArrayList<K>> toList() {
      return (Collector<K, ?, ObjectArrayList<K>>)TO_LIST_COLLECTOR;
   }

   public static <K> Collector<K, ?, ObjectArrayList<K>> toListWithExpectedSize(int expectedSize) {
      return expectedSize <= 10
         ? toList()
         : Collector.of(
            new ObjectCollections.SizeDecreasingSupplier<>(expectedSize, size -> size <= 10 ? new ObjectArrayList() : new ObjectArrayList(size)),
            ObjectArrayList::add,
            ObjectArrayList::combine
         );
   }

   public void ensureCapacity(int capacity) {
      if (capacity > this.a.length && (this.a != ObjectArrays.DEFAULT_EMPTY_ARRAY || capacity > 10)) {
         if (this.wrapped) {
            this.a = (K[])ObjectArrays.ensureCapacity(this.a, capacity, this.size);
         } else if (capacity > this.a.length) {
            Object[] t = new Object[capacity];
            System.arraycopy(this.a, 0, t, 0, this.size);
            this.a = (K[])t;
         }

         assert this.size <= this.a.length;
      }
   }

   private void grow(int capacity) {
      if (capacity > this.a.length) {
         if (this.a != ObjectArrays.DEFAULT_EMPTY_ARRAY) {
            capacity = (int)Math.max(Math.min((long)this.a.length + (long)(this.a.length >> 1), 2147483639L), (long)capacity);
         } else if (capacity < 10) {
            capacity = 10;
         }

         if (this.wrapped) {
            this.a = (K[])ObjectArrays.forceCapacity(this.a, capacity, this.size);
         } else {
            Object[] t = new Object[capacity];
            System.arraycopy(this.a, 0, t, 0, this.size);
            this.a = (K[])t;
         }

         assert this.size <= this.a.length;
      }
   }

   @Override
   public void add(int index, K k) {
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
   public boolean add(K k) {
      this.grow(this.size + 1);
      this.a[this.size++] = k;

      assert this.size <= this.a.length;

      return true;
   }

   @Override
   public K get(int index) {
      if (index >= this.size) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
      } else {
         return this.a[index];
      }
   }

   @Override
   public int indexOf(Object k) {
      for (int i = 0; i < this.size; i++) {
         if (Objects.equals(k, this.a[i])) {
            return i;
         }
      }

      return -1;
   }

   @Override
   public int lastIndexOf(Object k) {
      int i = this.size;

      while (i-- != 0) {
         if (Objects.equals(k, this.a[i])) {
            return i;
         }
      }

      return -1;
   }

   @Override
   public K remove(int index) {
      if (index >= this.size) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
      } else {
         K old = this.a[index];
         this.size--;
         if (index != this.size) {
            System.arraycopy(this.a, index + 1, this.a, index, this.size - index);
         }

         this.a[this.size] = null;

         assert this.size <= this.a.length;

         return old;
      }
   }

   @Override
   public boolean remove(Object k) {
      int index = this.indexOf(k);
      if (index == -1) {
         return false;
      } else {
         this.remove(index);

         assert this.size <= this.a.length;

         return true;
      }
   }

   @Override
   public K set(int index, K k) {
      if (index >= this.size) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
      } else {
         K old = this.a[index];
         this.a[index] = k;
         return old;
      }
   }

   @Override
   public void clear() {
      Arrays.fill(this.a, 0, this.size, null);
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
         this.a = (K[])ObjectArrays.forceCapacity(this.a, size, this.size);
      }

      if (size > this.size) {
         Arrays.fill(this.a, this.size, size, null);
      } else {
         Arrays.fill(this.a, size, this.size, null);
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
         K[] t = (K[])(new Object[Math.max(n, this.size)]);
         System.arraycopy(this.a, 0, t, 0, this.size);
         this.a = t;

         assert this.size <= this.a.length;
      }
   }

   @Override
   public ObjectList<K> subList(int from, int to) {
      if (from == 0 && to == this.size()) {
         return this;
      } else {
         this.ensureIndex(from);
         this.ensureIndex(to);
         if (from > to) {
            throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
         } else {
            return new ObjectArrayList.SubList(from, to);
         }
      }
   }

   @Override
   public void getElements(int from, Object[] a, int offset, int length) {
      ObjectArrays.ensureOffsetLength(a, offset, length);
      System.arraycopy(this.a, from, a, offset, length);
   }

   @Override
   public void removeElements(int from, int to) {
      com.viaversion.viaversion.libs.fastutil.Arrays.ensureFromTo(this.size, from, to);
      System.arraycopy(this.a, to, this.a, from, this.size - to);
      this.size -= to - from;
      int i = to - from;

      while (i-- != 0) {
         this.a[this.size + i] = null;
      }
   }

   @Override
   public void addElements(int index, K[] a, int offset, int length) {
      this.ensureIndex(index);
      ObjectArrays.ensureOffsetLength(a, offset, length);
      this.grow(this.size + length);
      System.arraycopy(this.a, index, this.a, index + length, this.size - index);
      System.arraycopy(a, offset, this.a, index, length);
      this.size += length;
   }

   @Override
   public void setElements(int index, K[] a, int offset, int length) {
      this.ensureIndex(index);
      ObjectArrays.ensureOffsetLength(a, offset, length);
      if (index + length > this.size) {
         throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size + ")");
      } else {
         System.arraycopy(a, offset, this.a, index, length);
      }
   }

   @Override
   public void forEach(Consumer<? super K> action) {
      for (int i = 0; i < this.size; i++) {
         action.accept(this.a[i]);
      }
   }

   @Override
   public boolean addAll(int index, Collection<? extends K> c) {
      if (c instanceof ObjectList) {
         return this.addAll(index, (ObjectList<? extends K>)c);
      } else {
         this.ensureIndex(index);
         int n = c.size();
         if (n == 0) {
            return false;
         } else {
            this.grow(this.size + n);
            System.arraycopy(this.a, index, this.a, index + n, this.size - index);
            Iterator<? extends K> i = c.iterator();
            this.size += n;

            while (n-- != 0) {
               this.a[index++] = (K)i.next();
            }

            assert this.size <= this.a.length;

            return true;
         }
      }
   }

   @Override
   public boolean addAll(int index, ObjectList<? extends K> l) {
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
   public boolean removeAll(Collection<?> c) {
      Object[] a = this.a;
      int j = 0;

      for (int i = 0; i < this.size; i++) {
         if (!c.contains(a[i])) {
            a[j++] = a[i];
         }
      }

      Arrays.fill(a, j, this.size, null);
      boolean modified = this.size != j;
      this.size = j;
      return modified;
   }

   @Override
   public Object[] toArray() {
      int size = this.size();
      return size == 0 ? ObjectArrays.EMPTY_ARRAY : Arrays.copyOf(this.a, size, Object[].class);
   }

   @Override
   public <T> T[] toArray(T[] a) {
      if (a == null) {
         a = (T[])(new Object[this.size()]);
      } else if (a.length < this.size()) {
         a = (T[])Array.newInstance(a.getClass().getComponentType(), this.size());
      }

      System.arraycopy(this.a, 0, a, 0, this.size());
      if (a.length > this.size()) {
         a[this.size()] = null;
      }

      return a;
   }

   @Override
   public ObjectListIterator<K> listIterator(final int index) {
      this.ensureIndex(index);
      return new ObjectListIterator<K>() {
         int pos = index;
         int last = -1;

         @Override
         public boolean hasNext() {
            return this.pos < ObjectArrayList.this.size;
         }

         @Override
         public boolean hasPrevious() {
            return this.pos > 0;
         }

         @Override
         public K next() {
            if (!this.hasNext()) {
               throw new NoSuchElementException();
            } else {
               return ObjectArrayList.this.a[this.last = this.pos++];
            }
         }

         @Override
         public K previous() {
            if (!this.hasPrevious()) {
               throw new NoSuchElementException();
            } else {
               return ObjectArrayList.this.a[this.last = --this.pos];
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
            ObjectArrayList.this.add(this.pos++, k);
            this.last = -1;
         }

         @Override
         public void set(K k) {
            if (this.last == -1) {
               throw new IllegalStateException();
            } else {
               ObjectArrayList.this.set(this.last, k);
            }
         }

         @Override
         public void remove() {
            if (this.last == -1) {
               throw new IllegalStateException();
            } else {
               ObjectArrayList.this.remove(this.last);
               if (this.last < this.pos) {
                  this.pos--;
               }

               this.last = -1;
            }
         }

         @Override
         public void forEachRemaining(Consumer<? super K> action) {
            while (this.pos < ObjectArrayList.this.size) {
               action.accept(ObjectArrayList.this.a[this.last = this.pos++]);
            }
         }

         @Override
         public int back(int n) {
            if (n < 0) {
               throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            } else {
               int remaining = ObjectArrayList.this.size - this.pos;
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
               int remaining = ObjectArrayList.this.size - this.pos;
               if (n < remaining) {
                  this.pos += n;
               } else {
                  n = remaining;
                  this.pos = ObjectArrayList.this.size;
               }

               this.last = this.pos - 1;
               return n;
            }
         }
      };
   }

   @Override
   public ObjectSpliterator<K> spliterator() {
      return new ObjectArrayList.Spliterator();
   }

   @Override
   public void sort(Comparator<? super K> comp) {
      if (comp == null) {
         ObjectArrays.stableSort(this.a, 0, this.size);
      } else {
         ObjectArrays.stableSort(this.a, 0, this.size, comp);
      }
   }

   @Override
   public void unstableSort(Comparator<? super K> comp) {
      if (comp == null) {
         ObjectArrays.unstableSort(this.a, 0, this.size);
      } else {
         ObjectArrays.unstableSort(this.a, 0, this.size, comp);
      }
   }

   public ObjectArrayList<K> clone() {
      ObjectArrayList<K> cloned = null;
      if (this.getClass() == ObjectArrayList.class) {
         cloned = new ObjectArrayList<>((K[])copyArraySafe(this.a, this.size), false);
         cloned.size = this.size;
      } else {
         try {
            cloned = (ObjectArrayList<K>)super.clone();
         } catch (CloneNotSupportedException var3) {
            throw new InternalError(var3);
         }

         cloned.a = (K[])copyArraySafe(this.a, this.size);
      }

      return cloned;
   }

   public boolean equals(ObjectArrayList<K> l) {
      if (l == this) {
         return true;
      } else {
         int s = this.size();
         if (s != l.size()) {
            return false;
         } else {
            K[] a1 = this.a;
            K[] a2 = l.a;
            if (a1 == a2 && s == l.size()) {
               return true;
            } else {
               while (s-- != 0) {
                  if (!Objects.equals(a1[s], a2[s])) {
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
      } else if (o instanceof ObjectArrayList) {
         return this.equals((ObjectArrayList<K>)o);
      } else {
         return o instanceof ObjectArrayList.SubList ? ((ObjectArrayList.SubList)o).equals(this) : super.equals(o);
      }
   }

   public int compareTo(ObjectArrayList<? extends K> l) {
      int s1 = this.size();
      int s2 = l.size();
      K[] a1 = this.a;
      K[] a2 = (K[])l.a;

      int i;
      for (i = 0; i < s1 && i < s2; i++) {
         K e1 = a1[i];
         K e2 = a2[i];
         int r;
         if ((r = ((Comparable)e1).compareTo(e2)) != 0) {
            return r;
         }
      }

      return i < s2 ? -1 : (i < s1 ? 1 : 0);
   }

   @Override
   public int compareTo(List<? extends K> l) {
      if (l instanceof ObjectArrayList) {
         return this.compareTo((ObjectArrayList<? extends K>)l);
      } else {
         return l instanceof ObjectArrayList.SubList ? -((ObjectArrayList.SubList)l).compareTo(this) : super.compareTo(l);
      }
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      s.defaultWriteObject();

      for (int i = 0; i < this.size; i++) {
         s.writeObject(this.a[i]);
      }
   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.a = (K[])(new Object[this.size]);

      for (int i = 0; i < this.size; i++) {
         this.a[i] = (K)s.readObject();
      }
   }

   private final class Spliterator implements ObjectSpliterator<K> {
      boolean hasSplit = false;
      int pos;
      int max;

      public Spliterator() {
         this(0, ObjectArrayList.this.size, false);
      }

      private Spliterator(int pos, int max, boolean hasSplit) {
         assert pos <= max : "pos " + pos + " must be <= max " + max;

         this.pos = pos;
         this.max = max;
         this.hasSplit = hasSplit;
      }

      private int getWorkingMax() {
         return this.hasSplit ? this.max : ObjectArrayList.this.size;
      }

      @Override
      public int characteristics() {
         return 16464;
      }

      @Override
      public long estimateSize() {
         return (long)(this.getWorkingMax() - this.pos);
      }

      @Override
      public boolean tryAdvance(Consumer<? super K> action) {
         if (this.pos >= this.getWorkingMax()) {
            return false;
         } else {
            action.accept(ObjectArrayList.this.a[this.pos++]);
            return true;
         }
      }

      @Override
      public void forEachRemaining(Consumer<? super K> action) {
         for (int max = this.getWorkingMax(); this.pos < max; this.pos++) {
            action.accept(ObjectArrayList.this.a[this.pos]);
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
      public ObjectSpliterator<K> trySplit() {
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
            return ObjectArrayList.this.new Spliterator(oldPos, myNewPos, true);
         }
      }
   }

   private class SubList extends AbstractObjectList.ObjectRandomAccessSubList<K> {
      private static final long serialVersionUID = -3185226345314976296L;

      protected SubList(int from, int to) {
         super(ObjectArrayList.this, from, to);
      }

      private K[] getParentArray() {
         return ObjectArrayList.this.a;
      }

      @Override
      public K get(int i) {
         this.ensureRestrictedIndex(i);
         return ObjectArrayList.this.a[i + this.from];
      }

      @Override
      public ObjectListIterator<K> listIterator(int index) {
         return new ObjectArrayList.SubList.SubListIterator(index);
      }

      @Override
      public ObjectSpliterator<K> spliterator() {
         return new ObjectArrayList.SubList.SubListSpliterator();
      }

      boolean contentsEquals(K[] otherA, int otherAFrom, int otherATo) {
         if (ObjectArrayList.this.a == otherA && this.from == otherAFrom && this.to == otherATo) {
            return true;
         } else if (otherATo - otherAFrom != this.size()) {
            return false;
         } else {
            int pos = this.from;
            int otherPos = otherAFrom;

            while (pos < this.to) {
               if (!Objects.equals(ObjectArrayList.this.a[pos++], otherA[otherPos++])) {
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
         } else if (o instanceof ObjectArrayList) {
            ObjectArrayList<K> other = (ObjectArrayList<K>)o;
            return this.contentsEquals(other.a, 0, other.size());
         } else if (o instanceof ObjectArrayList.SubList) {
            ObjectArrayList<K>.SubList other = (ObjectArrayList.SubList)o;
            return this.contentsEquals(other.getParentArray(), other.from, other.to);
         } else {
            return super.equals(o);
         }
      }

      int contentsCompareTo(K[] otherA, int otherAFrom, int otherATo) {
         int i = this.from;

         for (int j = otherAFrom; i < this.to && i < otherATo; j++) {
            K e1 = ObjectArrayList.this.a[i];
            K e2 = otherA[j];
            int r;
            if ((r = ((Comparable)e1).compareTo(e2)) != 0) {
               return r;
            }

            i++;
         }

         return i < otherATo ? -1 : (i < this.to ? 1 : 0);
      }

      @Override
      public int compareTo(List<? extends K> l) {
         if (l instanceof ObjectArrayList) {
            ObjectArrayList<K> other = (ObjectArrayList<K>)l;
            return this.contentsCompareTo(other.a, 0, other.size());
         } else if (l instanceof ObjectArrayList.SubList) {
            ObjectArrayList<K>.SubList other = (ObjectArrayList.SubList)l;
            return this.contentsCompareTo(other.getParentArray(), other.from, other.to);
         } else {
            return super.compareTo(l);
         }
      }

      private final class SubListIterator extends ObjectIterators.AbstractIndexBasedListIterator<K> {
         SubListIterator(int index) {
            super(0, index);
         }

         @Override
         protected final K get(int i) {
            return ObjectArrayList.this.a[SubList.this.from + i];
         }

         @Override
         protected final void add(int i, K k) {
            SubList.this.add(i, k);
         }

         @Override
         protected final void set(int i, K k) {
            SubList.this.set(i, k);
         }

         @Override
         protected final void remove(int i) {
            SubList.this.remove(i);
         }

         @Override
         protected final int getMaxPos() {
            return SubList.this.to - SubList.this.from;
         }

         @Override
         public K next() {
            if (!this.hasNext()) {
               throw new NoSuchElementException();
            } else {
               return ObjectArrayList.this.a[SubList.this.from + (this.lastReturned = this.pos++)];
            }
         }

         @Override
         public K previous() {
            if (!this.hasPrevious()) {
               throw new NoSuchElementException();
            } else {
               return ObjectArrayList.this.a[SubList.this.from + (this.lastReturned = --this.pos)];
            }
         }

         @Override
         public void forEachRemaining(Consumer<? super K> action) {
            int max = SubList.this.to - SubList.this.from;

            while (this.pos < max) {
               action.accept(ObjectArrayList.this.a[SubList.this.from + (this.lastReturned = this.pos++)]);
            }
         }
      }

      private final class SubListSpliterator extends ObjectSpliterators.LateBindingSizeIndexBasedSpliterator<K> {
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
         protected final K get(int i) {
            return ObjectArrayList.this.a[i];
         }

         protected final ObjectArrayList<K>.SubList.SubListSpliterator makeForSplit(int pos, int maxPos) {
            return SubList.this.new SubListSpliterator(pos, maxPos);
         }

         @Override
         public boolean tryAdvance(Consumer<? super K> action) {
            if (this.pos >= this.getMaxPos()) {
               return false;
            } else {
               action.accept(ObjectArrayList.this.a[this.pos++]);
               return true;
            }
         }

         @Override
         public void forEachRemaining(Consumer<? super K> action) {
            int max = this.getMaxPos();

            while (this.pos < max) {
               action.accept(ObjectArrayList.this.a[this.pos++]);
            }
         }
      }
   }
}
