package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

public class ObjectOpenHashSet<K> extends AbstractObjectSet<K> implements Serializable, Cloneable, Hash {
   private static final long serialVersionUID = 0L;
   private static final boolean ASSERTS = false;
   protected transient K[] key;
   protected transient int mask;
   protected transient boolean containsNull;
   protected transient int n;
   protected transient int maxFill;
   protected final transient int minN;
   protected int size;
   protected final float f;
   private static final Collector<Object, ?, ObjectOpenHashSet<Object>> TO_SET_COLLECTOR = Collector.of(
      ObjectOpenHashSet::new, ObjectOpenHashSet::add, ObjectOpenHashSet::combine, Characteristics.UNORDERED
   );

   public ObjectOpenHashSet(int expected, float f) {
      if (f <= 0.0F || f >= 1.0F) {
         throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
      } else if (expected < 0) {
         throw new IllegalArgumentException("The expected number of elements must be nonnegative");
      } else {
         this.f = f;
         this.minN = this.n = HashCommon.arraySize(expected, f);
         this.mask = this.n - 1;
         this.maxFill = HashCommon.maxFill(this.n, f);
         this.key = (K[])(new Object[this.n + 1]);
      }
   }

   public ObjectOpenHashSet(int expected) {
      this(expected, 0.75F);
   }

   public ObjectOpenHashSet() {
      this(16, 0.75F);
   }

   public ObjectOpenHashSet(Collection<? extends K> c, float f) {
      this(c.size(), f);
      this.addAll(c);
   }

   public ObjectOpenHashSet(Collection<? extends K> c) {
      this(c, 0.75F);
   }

   public ObjectOpenHashSet(ObjectCollection<? extends K> c, float f) {
      this(c.size(), f);
      this.addAll(c);
   }

   public ObjectOpenHashSet(ObjectCollection<? extends K> c) {
      this(c, 0.75F);
   }

   public ObjectOpenHashSet(Iterator<? extends K> i, float f) {
      this(16, f);

      while (i.hasNext()) {
         this.add((K)i.next());
      }
   }

   public ObjectOpenHashSet(Iterator<? extends K> i) {
      this(i, 0.75F);
   }

   public ObjectOpenHashSet(K[] a, int offset, int length, float f) {
      this(length < 0 ? 0 : length, f);
      ObjectArrays.ensureOffsetLength(a, offset, length);

      for (int i = 0; i < length; i++) {
         this.add(a[offset + i]);
      }
   }

   public ObjectOpenHashSet(K[] a, int offset, int length) {
      this(a, offset, length, 0.75F);
   }

   public ObjectOpenHashSet(K[] a, float f) {
      this(a, 0, a.length, f);
   }

   public ObjectOpenHashSet(K[] a) {
      this(a, 0.75F);
   }

   public static <K> ObjectOpenHashSet<K> of() {
      return new ObjectOpenHashSet<>();
   }

   public static <K> ObjectOpenHashSet<K> of(K e) {
      ObjectOpenHashSet<K> result = new ObjectOpenHashSet<>(1, 0.75F);
      result.add(e);
      return result;
   }

   public static <K> ObjectOpenHashSet<K> of(K e0, K e1) {
      ObjectOpenHashSet<K> result = new ObjectOpenHashSet<>(2, 0.75F);
      result.add(e0);
      if (!result.add(e1)) {
         throw new IllegalArgumentException("Duplicate element: " + e1);
      } else {
         return result;
      }
   }

   public static <K> ObjectOpenHashSet<K> of(K e0, K e1, K e2) {
      ObjectOpenHashSet<K> result = new ObjectOpenHashSet<>(3, 0.75F);
      result.add(e0);
      if (!result.add(e1)) {
         throw new IllegalArgumentException("Duplicate element: " + e1);
      } else if (!result.add(e2)) {
         throw new IllegalArgumentException("Duplicate element: " + e2);
      } else {
         return result;
      }
   }

   @SafeVarargs
   public static <K> ObjectOpenHashSet<K> of(K... a) {
      ObjectOpenHashSet<K> result = new ObjectOpenHashSet<>(a.length, 0.75F);

      for (K element : a) {
         if (!result.add(element)) {
            throw new IllegalArgumentException("Duplicate element " + element);
         }
      }

      return result;
   }

   private ObjectOpenHashSet<K> combine(ObjectOpenHashSet<? extends K> toAddFrom) {
      this.addAll(toAddFrom);
      return this;
   }

   public static <K> Collector<K, ?, ObjectOpenHashSet<K>> toSet() {
      return (Collector<K, ?, ObjectOpenHashSet<K>>)TO_SET_COLLECTOR;
   }

   public static <K> Collector<K, ?, ObjectOpenHashSet<K>> toSetWithExpectedSize(int expectedSize) {
      return expectedSize <= 16
         ? toSet()
         : Collector.of(
            new ObjectCollections.SizeDecreasingSupplier<>(expectedSize, size -> size <= 16 ? new ObjectOpenHashSet() : new ObjectOpenHashSet(size)),
            ObjectOpenHashSet::add,
            ObjectOpenHashSet::combine,
            Characteristics.UNORDERED
         );
   }

   private int realSize() {
      return this.containsNull ? this.size - 1 : this.size;
   }

   public void ensureCapacity(int capacity) {
      int needed = HashCommon.arraySize(capacity, this.f);
      if (needed > this.n) {
         this.rehash(needed);
      }
   }

   private void tryCapacity(long capacity) {
      int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil((double)((float)capacity / this.f)))));
      if (needed > this.n) {
         this.rehash(needed);
      }
   }

   @Override
   public boolean addAll(Collection<? extends K> c) {
      if ((double)this.f <= 0.5) {
         this.ensureCapacity(c.size());
      } else {
         this.tryCapacity((long)(this.size() + c.size()));
      }

      return super.addAll(c);
   }

   @Override
   public boolean add(K k) {
      if (k == null) {
         if (this.containsNull) {
            return false;
         }

         this.containsNull = true;
      } else {
         K[] key = this.key;
         int pos;
         K curr;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
            if (curr.equals(k)) {
               return false;
            }

            while ((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (curr.equals(k)) {
                  return false;
               }
            }
         }

         key[pos] = k;
      }

      if (this.size++ >= this.maxFill) {
         this.rehash(HashCommon.arraySize(this.size + 1, this.f));
      }

      return true;
   }

   public K addOrGet(K k) {
      if (k == null) {
         if (this.containsNull) {
            return this.key[this.n];
         }

         this.containsNull = true;
      } else {
         K[] key = this.key;
         int pos;
         K curr;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
            if (curr.equals(k)) {
               return curr;
            }

            while ((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (curr.equals(k)) {
                  return curr;
               }
            }
         }

         key[pos] = k;
      }

      if (this.size++ >= this.maxFill) {
         this.rehash(HashCommon.arraySize(this.size + 1, this.f));
      }

      return k;
   }

   protected final void shiftKeys(int pos) {
      K[] key = this.key;

      label30:
      while (true) {
         int last = pos;

         K curr;
         for (pos = pos + 1 & this.mask; (curr = key[pos]) != null; pos = pos + 1 & this.mask) {
            int slot = HashCommon.mix(curr.hashCode()) & this.mask;
            if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
               key[last] = curr;
               continue label30;
            }
         }

         key[last] = null;
         return;
      }
   }

   private boolean removeEntry(int pos) {
      this.size--;
      this.shiftKeys(pos);
      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
         this.rehash(this.n / 2);
      }

      return true;
   }

   private boolean removeNullEntry() {
      this.containsNull = false;
      this.key[this.n] = null;
      this.size--;
      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
         this.rehash(this.n / 2);
      }

      return true;
   }

   @Override
   public boolean remove(Object k) {
      if (k == null) {
         return this.containsNull ? this.removeNullEntry() : false;
      } else {
         K[] key = this.key;
         K curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return false;
         } else if (k.equals(curr)) {
            return this.removeEntry(pos);
         } else {
            while ((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr)) {
                  return this.removeEntry(pos);
               }
            }

            return false;
         }
      }
   }

   @Override
   public boolean contains(Object k) {
      if (k == null) {
         return this.containsNull;
      } else {
         K[] key = this.key;
         K curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return false;
         } else if (k.equals(curr)) {
            return true;
         } else {
            while ((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr)) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public K get(Object k) {
      if (k == null) {
         return this.key[this.n];
      } else {
         K[] key = this.key;
         K curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return null;
         } else if (k.equals(curr)) {
            return curr;
         } else {
            while ((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr)) {
                  return curr;
               }
            }

            return null;
         }
      }
   }

   @Override
   public void clear() {
      if (this.size != 0) {
         this.size = 0;
         this.containsNull = false;
         Arrays.fill(this.key, null);
      }
   }

   @Override
   public int size() {
      return this.size;
   }

   @Override
   public boolean isEmpty() {
      return this.size == 0;
   }

   @Override
   public ObjectIterator<K> iterator() {
      return new ObjectOpenHashSet.SetIterator();
   }

   @Override
   public ObjectSpliterator<K> spliterator() {
      return new ObjectOpenHashSet.SetSpliterator();
   }

   @Override
   public void forEach(Consumer<? super K> action) {
      if (this.containsNull) {
         action.accept(this.key[this.n]);
      }

      K[] key = this.key;
      int pos = this.n;

      while (pos-- != 0) {
         if (key[pos] != null) {
            action.accept(key[pos]);
         }
      }
   }

   public boolean trim() {
      return this.trim(this.size);
   }

   public boolean trim(int n) {
      int l = HashCommon.nextPowerOfTwo((int)Math.ceil((double)((float)n / this.f)));
      if (l < this.n && this.size <= HashCommon.maxFill(l, this.f)) {
         try {
            this.rehash(l);
            return true;
         } catch (OutOfMemoryError var4) {
            return false;
         }
      } else {
         return true;
      }
   }

   protected void rehash(int newN) {
      K[] key = this.key;
      int mask = newN - 1;
      K[] newKey = (K[])(new Object[newN + 1]);
      int i = this.n;
      int j = this.realSize();

      while (j-- != 0) {
         while (key[--i] == null) {
         }

         int pos;
         if (newKey[pos = HashCommon.mix(key[i].hashCode()) & mask] != null) {
            while (newKey[pos = pos + 1 & mask] != null) {
            }
         }

         newKey[pos] = key[i];
      }

      this.n = newN;
      this.mask = mask;
      this.maxFill = HashCommon.maxFill(this.n, this.f);
      this.key = newKey;
   }

   public ObjectOpenHashSet<K> clone() {
      ObjectOpenHashSet<K> c;
      try {
         c = (ObjectOpenHashSet<K>)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      c.key = (K[])((Object[])this.key.clone());
      c.containsNull = this.containsNull;
      return c;
   }

   @Override
   public int hashCode() {
      int h = 0;
      int j = this.realSize();

      for (int i = 0; j-- != 0; i++) {
         while (this.key[i] == null) {
            i++;
         }

         if (this != this.key[i]) {
            h += this.key[i].hashCode();
         }
      }

      return h;
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      ObjectIterator<K> i = this.iterator();
      s.defaultWriteObject();
      int j = this.size;

      while (j-- != 0) {
         s.writeObject(i.next());
      }
   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.n = HashCommon.arraySize(this.size, this.f);
      this.maxFill = HashCommon.maxFill(this.n, this.f);
      this.mask = this.n - 1;
      K[] key = this.key = (K[])(new Object[this.n + 1]);
      int i = this.size;

      while (i-- != 0) {
         K k = (K)s.readObject();
         int pos;
         if (k == null) {
            pos = this.n;
            this.containsNull = true;
         } else if (key[pos = HashCommon.mix(k.hashCode()) & this.mask] != null) {
            while (key[pos = pos + 1 & this.mask] != null) {
            }
         }

         key[pos] = k;
      }
   }

   private void checkTable() {
   }

   private final class SetIterator implements ObjectIterator<K> {
      int pos = ObjectOpenHashSet.this.n;
      int last = -1;
      int c = ObjectOpenHashSet.this.size;
      boolean mustReturnNull = ObjectOpenHashSet.this.containsNull;
      ObjectArrayList<K> wrapped;

      private SetIterator() {
      }

      @Override
      public boolean hasNext() {
         return this.c != 0;
      }

      @Override
      public K next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this.c--;
            if (this.mustReturnNull) {
               this.mustReturnNull = false;
               this.last = ObjectOpenHashSet.this.n;
               return ObjectOpenHashSet.this.key[ObjectOpenHashSet.this.n];
            } else {
               K[] key = ObjectOpenHashSet.this.key;

               while (--this.pos >= 0) {
                  if (key[this.pos] != null) {
                     return key[this.last = this.pos];
                  }
               }

               this.last = Integer.MIN_VALUE;
               return this.wrapped.get(-this.pos - 1);
            }
         }
      }

      private final void shiftKeys(int pos) {
         K[] key = ObjectOpenHashSet.this.key;

         label38:
         while (true) {
            int last = pos;

            K curr;
            for (pos = pos + 1 & ObjectOpenHashSet.this.mask; (curr = key[pos]) != null; pos = pos + 1 & ObjectOpenHashSet.this.mask) {
               int slot = HashCommon.mix(curr.hashCode()) & ObjectOpenHashSet.this.mask;
               if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
                  if (pos < last) {
                     if (this.wrapped == null) {
                        this.wrapped = new ObjectArrayList<>(2);
                     }

                     this.wrapped.add(key[pos]);
                  }

                  key[last] = curr;
                  continue label38;
               }
            }

            key[last] = null;
            return;
         }
      }

      @Override
      public void remove() {
         if (this.last == -1) {
            throw new IllegalStateException();
         } else {
            if (this.last == ObjectOpenHashSet.this.n) {
               ObjectOpenHashSet.this.containsNull = false;
               ObjectOpenHashSet.this.key[ObjectOpenHashSet.this.n] = null;
            } else {
               if (this.pos < 0) {
                  ObjectOpenHashSet.this.remove(this.wrapped.set(-this.pos - 1, null));
                  this.last = -1;
                  return;
               }

               this.shiftKeys(this.last);
            }

            ObjectOpenHashSet.this.size--;
            this.last = -1;
         }
      }

      @Override
      public void forEachRemaining(Consumer<? super K> action) {
         K[] key = ObjectOpenHashSet.this.key;
         if (this.mustReturnNull) {
            this.mustReturnNull = false;
            this.last = ObjectOpenHashSet.this.n;
            action.accept(key[ObjectOpenHashSet.this.n]);
            this.c--;
         }

         while (this.c != 0) {
            if (--this.pos < 0) {
               this.last = Integer.MIN_VALUE;
               action.accept(this.wrapped.get(-this.pos - 1));
               this.c--;
            } else if (key[this.pos] != null) {
               action.accept(key[this.last = this.pos]);
               this.c--;
            }
         }
      }
   }

   private final class SetSpliterator implements ObjectSpliterator<K> {
      private static final int POST_SPLIT_CHARACTERISTICS = 1;
      int pos = 0;
      int max;
      int c;
      boolean mustReturnNull;
      boolean hasSplit;

      SetSpliterator() {
         this.max = ObjectOpenHashSet.this.n;
         this.c = 0;
         this.mustReturnNull = ObjectOpenHashSet.this.containsNull;
         this.hasSplit = false;
      }

      SetSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
         this.max = ObjectOpenHashSet.this.n;
         this.c = 0;
         this.mustReturnNull = ObjectOpenHashSet.this.containsNull;
         this.hasSplit = false;
         this.pos = pos;
         this.max = max;
         this.mustReturnNull = mustReturnNull;
         this.hasSplit = hasSplit;
      }

      @Override
      public boolean tryAdvance(Consumer<? super K> action) {
         if (this.mustReturnNull) {
            this.mustReturnNull = false;
            this.c++;
            action.accept(ObjectOpenHashSet.this.key[ObjectOpenHashSet.this.n]);
            return true;
         } else {
            for (K[] key = ObjectOpenHashSet.this.key; this.pos < this.max; this.pos++) {
               if (key[this.pos] != null) {
                  this.c++;
                  action.accept(key[this.pos++]);
                  return true;
               }
            }

            return false;
         }
      }

      @Override
      public void forEachRemaining(Consumer<? super K> action) {
         K[] key = ObjectOpenHashSet.this.key;
         if (this.mustReturnNull) {
            this.mustReturnNull = false;
            action.accept(key[ObjectOpenHashSet.this.n]);
            this.c++;
         }

         for (; this.pos < this.max; this.pos++) {
            if (key[this.pos] != null) {
               action.accept(key[this.pos]);
               this.c++;
            }
         }
      }

      @Override
      public int characteristics() {
         return this.hasSplit ? 1 : 65;
      }

      @Override
      public long estimateSize() {
         return !this.hasSplit
            ? (long)(ObjectOpenHashSet.this.size - this.c)
            : Math.min(
               (long)(ObjectOpenHashSet.this.size - this.c),
               (long)((double)ObjectOpenHashSet.this.realSize() / (double)ObjectOpenHashSet.this.n * (double)(this.max - this.pos))
                  + (long)(this.mustReturnNull ? 1 : 0)
            );
      }

      public ObjectOpenHashSet<K>.SetSpliterator trySplit() {
         if (this.pos >= this.max - 1) {
            return null;
         } else {
            int retLen = this.max - this.pos >> 1;
            if (retLen <= 1) {
               return null;
            } else {
               int myNewPos = this.pos + retLen;
               int retPos = this.pos;
               ObjectOpenHashSet<K>.SetSpliterator split = ObjectOpenHashSet.this.new SetSpliterator(retPos, myNewPos, this.mustReturnNull, true);
               this.pos = myNewPos;
               this.mustReturnNull = false;
               this.hasSplit = true;
               return split;
            }
         }
      }

      @Override
      public long skip(long n) {
         if (n < 0L) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else if (n == 0L) {
            return 0L;
         } else {
            long skipped = 0L;
            if (this.mustReturnNull) {
               this.mustReturnNull = false;
               skipped++;
               n--;
            }

            K[] key = ObjectOpenHashSet.this.key;

            while (this.pos < this.max && n > 0L) {
               if (key[this.pos++] != null) {
                  skipped++;
                  n--;
               }
            }

            return skipped;
         }
      }
   }
}
