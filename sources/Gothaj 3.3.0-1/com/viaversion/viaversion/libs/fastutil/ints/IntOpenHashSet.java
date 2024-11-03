package com.viaversion.viaversion.libs.fastutil.ints;

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
import java.util.stream.IntStream;

public class IntOpenHashSet extends AbstractIntSet implements Serializable, Cloneable, Hash {
   private static final long serialVersionUID = 0L;
   private static final boolean ASSERTS = false;
   protected transient int[] key;
   protected transient int mask;
   protected transient boolean containsNull;
   protected transient int n;
   protected transient int maxFill;
   protected final transient int minN;
   protected int size;
   protected final float f;

   public IntOpenHashSet(int expected, float f) {
      if (f <= 0.0F || f >= 1.0F) {
         throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
      } else if (expected < 0) {
         throw new IllegalArgumentException("The expected number of elements must be nonnegative");
      } else {
         this.f = f;
         this.minN = this.n = HashCommon.arraySize(expected, f);
         this.mask = this.n - 1;
         this.maxFill = HashCommon.maxFill(this.n, f);
         this.key = new int[this.n + 1];
      }
   }

   public IntOpenHashSet(int expected) {
      this(expected, 0.75F);
   }

   public IntOpenHashSet() {
      this(16, 0.75F);
   }

   public IntOpenHashSet(Collection<? extends Integer> c, float f) {
      this(c.size(), f);
      this.addAll(c);
   }

   public IntOpenHashSet(Collection<? extends Integer> c) {
      this(c, 0.75F);
   }

   public IntOpenHashSet(IntCollection c, float f) {
      this(c.size(), f);
      this.addAll(c);
   }

   public IntOpenHashSet(IntCollection c) {
      this(c, 0.75F);
   }

   public IntOpenHashSet(IntIterator i, float f) {
      this(16, f);

      while (i.hasNext()) {
         this.add(i.nextInt());
      }
   }

   public IntOpenHashSet(IntIterator i) {
      this(i, 0.75F);
   }

   public IntOpenHashSet(Iterator<?> i, float f) {
      this(IntIterators.asIntIterator(i), f);
   }

   public IntOpenHashSet(Iterator<?> i) {
      this(IntIterators.asIntIterator(i));
   }

   public IntOpenHashSet(int[] a, int offset, int length, float f) {
      this(length < 0 ? 0 : length, f);
      IntArrays.ensureOffsetLength(a, offset, length);

      for (int i = 0; i < length; i++) {
         this.add(a[offset + i]);
      }
   }

   public IntOpenHashSet(int[] a, int offset, int length) {
      this(a, offset, length, 0.75F);
   }

   public IntOpenHashSet(int[] a, float f) {
      this(a, 0, a.length, f);
   }

   public IntOpenHashSet(int[] a) {
      this(a, 0.75F);
   }

   public static IntOpenHashSet of() {
      return new IntOpenHashSet();
   }

   public static IntOpenHashSet of(int e) {
      IntOpenHashSet result = new IntOpenHashSet(1, 0.75F);
      result.add(e);
      return result;
   }

   public static IntOpenHashSet of(int e0, int e1) {
      IntOpenHashSet result = new IntOpenHashSet(2, 0.75F);
      result.add(e0);
      if (!result.add(e1)) {
         throw new IllegalArgumentException("Duplicate element: " + e1);
      } else {
         return result;
      }
   }

   public static IntOpenHashSet of(int e0, int e1, int e2) {
      IntOpenHashSet result = new IntOpenHashSet(3, 0.75F);
      result.add(e0);
      if (!result.add(e1)) {
         throw new IllegalArgumentException("Duplicate element: " + e1);
      } else if (!result.add(e2)) {
         throw new IllegalArgumentException("Duplicate element: " + e2);
      } else {
         return result;
      }
   }

   public static IntOpenHashSet of(int... a) {
      IntOpenHashSet result = new IntOpenHashSet(a.length, 0.75F);

      for (int element : a) {
         if (!result.add(element)) {
            throw new IllegalArgumentException("Duplicate element " + element);
         }
      }

      return result;
   }

   public static IntOpenHashSet toSet(IntStream stream) {
      return stream.collect(IntOpenHashSet::new, IntOpenHashSet::add, IntOpenHashSet::addAll);
   }

   public static IntOpenHashSet toSetWithExpectedSize(IntStream stream, int expectedSize) {
      return expectedSize <= 16
         ? toSet(stream)
         : stream.collect(
            new IntCollections.SizeDecreasingSupplier<>(expectedSize, size -> size <= 16 ? new IntOpenHashSet() : new IntOpenHashSet(size)),
            IntOpenHashSet::add,
            IntOpenHashSet::addAll
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
   public boolean addAll(IntCollection c) {
      if ((double)this.f <= 0.5) {
         this.ensureCapacity(c.size());
      } else {
         this.tryCapacity((long)(this.size() + c.size()));
      }

      return super.addAll(c);
   }

   @Override
   public boolean addAll(Collection<? extends Integer> c) {
      if ((double)this.f <= 0.5) {
         this.ensureCapacity(c.size());
      } else {
         this.tryCapacity((long)(this.size() + c.size()));
      }

      return super.addAll(c);
   }

   @Override
   public boolean add(int k) {
      if (k == 0) {
         if (this.containsNull) {
            return false;
         }

         this.containsNull = true;
      } else {
         int[] key = this.key;
         int pos;
         int curr;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
            if (curr == k) {
               return false;
            }

            while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (curr == k) {
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

   protected final void shiftKeys(int pos) {
      int[] key = this.key;

      label30:
      while (true) {
         int last = pos;

         int curr;
         for (pos = pos + 1 & this.mask; (curr = key[pos]) != 0; pos = pos + 1 & this.mask) {
            int slot = HashCommon.mix(curr) & this.mask;
            if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
               key[last] = curr;
               continue label30;
            }
         }

         key[last] = 0;
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
      this.key[this.n] = 0;
      this.size--;
      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
         this.rehash(this.n / 2);
      }

      return true;
   }

   @Override
   public boolean remove(int k) {
      if (k == 0) {
         return this.containsNull ? this.removeNullEntry() : false;
      } else {
         int[] key = this.key;
         int curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
            return false;
         } else if (k == curr) {
            return this.removeEntry(pos);
         } else {
            while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (k == curr) {
                  return this.removeEntry(pos);
               }
            }

            return false;
         }
      }
   }

   @Override
   public boolean contains(int k) {
      if (k == 0) {
         return this.containsNull;
      } else {
         int[] key = this.key;
         int curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
            return false;
         } else if (k == curr) {
            return true;
         } else {
            while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (k == curr) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   @Override
   public void clear() {
      if (this.size != 0) {
         this.size = 0;
         this.containsNull = false;
         Arrays.fill(this.key, 0);
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
   public IntIterator iterator() {
      return new IntOpenHashSet.SetIterator();
   }

   @Override
   public IntSpliterator spliterator() {
      return new IntOpenHashSet.SetSpliterator();
   }

   @Override
   public void forEach(java.util.function.IntConsumer action) {
      if (this.containsNull) {
         action.accept(this.key[this.n]);
      }

      int[] key = this.key;
      int pos = this.n;

      while (pos-- != 0) {
         if (key[pos] != 0) {
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
      int[] key = this.key;
      int mask = newN - 1;
      int[] newKey = new int[newN + 1];
      int i = this.n;
      int j = this.realSize();

      while (j-- != 0) {
         while (key[--i] == 0) {
         }

         int pos;
         if (newKey[pos = HashCommon.mix(key[i]) & mask] != 0) {
            while (newKey[pos = pos + 1 & mask] != 0) {
            }
         }

         newKey[pos] = key[i];
      }

      this.n = newN;
      this.mask = mask;
      this.maxFill = HashCommon.maxFill(this.n, this.f);
      this.key = newKey;
   }

   public IntOpenHashSet clone() {
      IntOpenHashSet c;
      try {
         c = (IntOpenHashSet)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      c.key = (int[])this.key.clone();
      c.containsNull = this.containsNull;
      return c;
   }

   @Override
   public int hashCode() {
      int h = 0;
      int j = this.realSize();

      for (int i = 0; j-- != 0; i++) {
         while (this.key[i] == 0) {
            i++;
         }

         h += this.key[i];
      }

      return h;
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      IntIterator i = this.iterator();
      s.defaultWriteObject();
      int j = this.size;

      while (j-- != 0) {
         s.writeInt(i.nextInt());
      }
   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.n = HashCommon.arraySize(this.size, this.f);
      this.maxFill = HashCommon.maxFill(this.n, this.f);
      this.mask = this.n - 1;
      int[] key = this.key = new int[this.n + 1];
      int i = this.size;

      while (i-- != 0) {
         int k = s.readInt();
         int pos;
         if (k == 0) {
            pos = this.n;
            this.containsNull = true;
         } else if (key[pos = HashCommon.mix(k) & this.mask] != 0) {
            while (key[pos = pos + 1 & this.mask] != 0) {
            }
         }

         key[pos] = k;
      }
   }

   private void checkTable() {
   }

   private final class SetIterator implements IntIterator {
      int pos = IntOpenHashSet.this.n;
      int last = -1;
      int c = IntOpenHashSet.this.size;
      boolean mustReturnNull = IntOpenHashSet.this.containsNull;
      IntArrayList wrapped;

      private SetIterator() {
      }

      @Override
      public boolean hasNext() {
         return this.c != 0;
      }

      @Override
      public int nextInt() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this.c--;
            if (this.mustReturnNull) {
               this.mustReturnNull = false;
               this.last = IntOpenHashSet.this.n;
               return IntOpenHashSet.this.key[IntOpenHashSet.this.n];
            } else {
               int[] key = IntOpenHashSet.this.key;

               while (--this.pos >= 0) {
                  if (key[this.pos] != 0) {
                     return key[this.last = this.pos];
                  }
               }

               this.last = Integer.MIN_VALUE;
               return this.wrapped.getInt(-this.pos - 1);
            }
         }
      }

      private final void shiftKeys(int pos) {
         int[] key = IntOpenHashSet.this.key;

         label38:
         while (true) {
            int last = pos;

            int curr;
            for (pos = pos + 1 & IntOpenHashSet.this.mask; (curr = key[pos]) != 0; pos = pos + 1 & IntOpenHashSet.this.mask) {
               int slot = HashCommon.mix(curr) & IntOpenHashSet.this.mask;
               if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
                  if (pos < last) {
                     if (this.wrapped == null) {
                        this.wrapped = new IntArrayList(2);
                     }

                     this.wrapped.add(key[pos]);
                  }

                  key[last] = curr;
                  continue label38;
               }
            }

            key[last] = 0;
            return;
         }
      }

      @Override
      public void remove() {
         if (this.last == -1) {
            throw new IllegalStateException();
         } else {
            if (this.last == IntOpenHashSet.this.n) {
               IntOpenHashSet.this.containsNull = false;
               IntOpenHashSet.this.key[IntOpenHashSet.this.n] = 0;
            } else {
               if (this.pos < 0) {
                  IntOpenHashSet.this.remove(this.wrapped.getInt(-this.pos - 1));
                  this.last = -1;
                  return;
               }

               this.shiftKeys(this.last);
            }

            IntOpenHashSet.this.size--;
            this.last = -1;
         }
      }

      @Override
      public void forEachRemaining(java.util.function.IntConsumer action) {
         int[] key = IntOpenHashSet.this.key;
         if (this.mustReturnNull) {
            this.mustReturnNull = false;
            this.last = IntOpenHashSet.this.n;
            action.accept(key[IntOpenHashSet.this.n]);
            this.c--;
         }

         while (this.c != 0) {
            if (--this.pos < 0) {
               this.last = Integer.MIN_VALUE;
               action.accept(this.wrapped.getInt(-this.pos - 1));
               this.c--;
            } else if (key[this.pos] != 0) {
               action.accept(key[this.last = this.pos]);
               this.c--;
            }
         }
      }
   }

   private final class SetSpliterator implements IntSpliterator {
      private static final int POST_SPLIT_CHARACTERISTICS = 257;
      int pos = 0;
      int max;
      int c;
      boolean mustReturnNull;
      boolean hasSplit;

      SetSpliterator() {
         this.max = IntOpenHashSet.this.n;
         this.c = 0;
         this.mustReturnNull = IntOpenHashSet.this.containsNull;
         this.hasSplit = false;
      }

      SetSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
         this.max = IntOpenHashSet.this.n;
         this.c = 0;
         this.mustReturnNull = IntOpenHashSet.this.containsNull;
         this.hasSplit = false;
         this.pos = pos;
         this.max = max;
         this.mustReturnNull = mustReturnNull;
         this.hasSplit = hasSplit;
      }

      @Override
      public boolean tryAdvance(java.util.function.IntConsumer action) {
         if (this.mustReturnNull) {
            this.mustReturnNull = false;
            this.c++;
            action.accept(IntOpenHashSet.this.key[IntOpenHashSet.this.n]);
            return true;
         } else {
            for (int[] key = IntOpenHashSet.this.key; this.pos < this.max; this.pos++) {
               if (key[this.pos] != 0) {
                  this.c++;
                  action.accept(key[this.pos++]);
                  return true;
               }
            }

            return false;
         }
      }

      @Override
      public void forEachRemaining(java.util.function.IntConsumer action) {
         int[] key = IntOpenHashSet.this.key;
         if (this.mustReturnNull) {
            this.mustReturnNull = false;
            action.accept(key[IntOpenHashSet.this.n]);
            this.c++;
         }

         for (; this.pos < this.max; this.pos++) {
            if (key[this.pos] != 0) {
               action.accept(key[this.pos]);
               this.c++;
            }
         }
      }

      @Override
      public int characteristics() {
         return this.hasSplit ? 257 : 321;
      }

      @Override
      public long estimateSize() {
         return !this.hasSplit
            ? (long)(IntOpenHashSet.this.size - this.c)
            : Math.min(
               (long)(IntOpenHashSet.this.size - this.c),
               (long)((double)IntOpenHashSet.this.realSize() / (double)IntOpenHashSet.this.n * (double)(this.max - this.pos))
                  + (long)(this.mustReturnNull ? 1 : 0)
            );
      }

      public IntOpenHashSet.SetSpliterator trySplit() {
         if (this.pos >= this.max - 1) {
            return null;
         } else {
            int retLen = this.max - this.pos >> 1;
            if (retLen <= 1) {
               return null;
            } else {
               int myNewPos = this.pos + retLen;
               int retPos = this.pos;
               IntOpenHashSet.SetSpliterator split = IntOpenHashSet.this.new SetSpliterator(retPos, myNewPos, this.mustReturnNull, true);
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

            int[] key = IntOpenHashSet.this.key;

            while (this.pos < this.max && n > 0L) {
               if (key[this.pos++] != 0) {
                  skipped++;
                  n--;
               }
            }

            return skipped;
         }
      }
   }
}
