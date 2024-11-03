package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.Size64;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public class IntLinkedOpenHashSet extends AbstractIntSortedSet implements Serializable, Cloneable, Hash {
   private static final long serialVersionUID = 0L;
   private static final boolean ASSERTS = false;
   protected transient int[] key;
   protected transient int mask;
   protected transient boolean containsNull;
   protected transient int first = -1;
   protected transient int last = -1;
   protected transient long[] link;
   protected transient int n;
   protected transient int maxFill;
   protected final transient int minN;
   protected int size;
   protected final float f;
   private static final int SPLITERATOR_CHARACTERISTICS = 337;

   public IntLinkedOpenHashSet(int expected, float f) {
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
         this.link = new long[this.n + 1];
      }
   }

   public IntLinkedOpenHashSet(int expected) {
      this(expected, 0.75F);
   }

   public IntLinkedOpenHashSet() {
      this(16, 0.75F);
   }

   public IntLinkedOpenHashSet(Collection<? extends Integer> c, float f) {
      this(c.size(), f);
      this.addAll(c);
   }

   public IntLinkedOpenHashSet(Collection<? extends Integer> c) {
      this(c, 0.75F);
   }

   public IntLinkedOpenHashSet(IntCollection c, float f) {
      this(c.size(), f);
      this.addAll(c);
   }

   public IntLinkedOpenHashSet(IntCollection c) {
      this(c, 0.75F);
   }

   public IntLinkedOpenHashSet(IntIterator i, float f) {
      this(16, f);

      while (i.hasNext()) {
         this.add(i.nextInt());
      }
   }

   public IntLinkedOpenHashSet(IntIterator i) {
      this(i, 0.75F);
   }

   public IntLinkedOpenHashSet(Iterator<?> i, float f) {
      this(IntIterators.asIntIterator(i), f);
   }

   public IntLinkedOpenHashSet(Iterator<?> i) {
      this(IntIterators.asIntIterator(i));
   }

   public IntLinkedOpenHashSet(int[] a, int offset, int length, float f) {
      this(length < 0 ? 0 : length, f);
      IntArrays.ensureOffsetLength(a, offset, length);

      for (int i = 0; i < length; i++) {
         this.add(a[offset + i]);
      }
   }

   public IntLinkedOpenHashSet(int[] a, int offset, int length) {
      this(a, offset, length, 0.75F);
   }

   public IntLinkedOpenHashSet(int[] a, float f) {
      this(a, 0, a.length, f);
   }

   public IntLinkedOpenHashSet(int[] a) {
      this(a, 0.75F);
   }

   public static IntLinkedOpenHashSet of() {
      return new IntLinkedOpenHashSet();
   }

   public static IntLinkedOpenHashSet of(int e) {
      IntLinkedOpenHashSet result = new IntLinkedOpenHashSet(1, 0.75F);
      result.add(e);
      return result;
   }

   public static IntLinkedOpenHashSet of(int e0, int e1) {
      IntLinkedOpenHashSet result = new IntLinkedOpenHashSet(2, 0.75F);
      result.add(e0);
      if (!result.add(e1)) {
         throw new IllegalArgumentException("Duplicate element: " + e1);
      } else {
         return result;
      }
   }

   public static IntLinkedOpenHashSet of(int e0, int e1, int e2) {
      IntLinkedOpenHashSet result = new IntLinkedOpenHashSet(3, 0.75F);
      result.add(e0);
      if (!result.add(e1)) {
         throw new IllegalArgumentException("Duplicate element: " + e1);
      } else if (!result.add(e2)) {
         throw new IllegalArgumentException("Duplicate element: " + e2);
      } else {
         return result;
      }
   }

   public static IntLinkedOpenHashSet of(int... a) {
      IntLinkedOpenHashSet result = new IntLinkedOpenHashSet(a.length, 0.75F);

      for (int element : a) {
         if (!result.add(element)) {
            throw new IllegalArgumentException("Duplicate element " + element);
         }
      }

      return result;
   }

   public static IntLinkedOpenHashSet toSet(IntStream stream) {
      return stream.collect(IntLinkedOpenHashSet::new, IntLinkedOpenHashSet::add, IntLinkedOpenHashSet::addAll);
   }

   public static IntLinkedOpenHashSet toSetWithExpectedSize(IntStream stream, int expectedSize) {
      return expectedSize <= 16
         ? toSet(stream)
         : stream.collect(
            new IntCollections.SizeDecreasingSupplier<>(expectedSize, size -> size <= 16 ? new IntLinkedOpenHashSet() : new IntLinkedOpenHashSet(size)),
            IntLinkedOpenHashSet::add,
            IntLinkedOpenHashSet::addAll
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
      int pos;
      if (k == 0) {
         if (this.containsNull) {
            return false;
         }

         pos = this.n;
         this.containsNull = true;
      } else {
         int[] key = this.key;
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

      if (this.size == 0) {
         this.first = this.last = pos;
         this.link[pos] = -1L;
      } else {
         this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ (long)pos & 4294967295L) & 4294967295L;
         this.link[pos] = ((long)this.last & 4294967295L) << 32 | 4294967295L;
         this.last = pos;
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
               this.fixPointers(pos, last);
               continue label30;
            }
         }

         key[last] = 0;
         return;
      }
   }

   private boolean removeEntry(int pos) {
      this.size--;
      this.fixPointers(pos);
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
      this.fixPointers(this.n);
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

   public int removeFirstInt() {
      if (this.size == 0) {
         throw new NoSuchElementException();
      } else {
         int pos = this.first;
         if (this.size == 1) {
            this.first = this.last = -1;
         } else {
            this.first = (int)this.link[pos];
            if (0 <= this.first) {
               this.link[this.first] = this.link[this.first] | -4294967296L;
            }
         }

         int k = this.key[pos];
         this.size--;
         if (k == 0) {
            this.containsNull = false;
            this.key[this.n] = 0;
         } else {
            this.shiftKeys(pos);
         }

         if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
         }

         return k;
      }
   }

   public int removeLastInt() {
      if (this.size == 0) {
         throw new NoSuchElementException();
      } else {
         int pos = this.last;
         if (this.size == 1) {
            this.first = this.last = -1;
         } else {
            this.last = (int)(this.link[pos] >>> 32);
            if (0 <= this.last) {
               this.link[this.last] = this.link[this.last] | 4294967295L;
            }
         }

         int k = this.key[pos];
         this.size--;
         if (k == 0) {
            this.containsNull = false;
            this.key[this.n] = 0;
         } else {
            this.shiftKeys(pos);
         }

         if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
         }

         return k;
      }
   }

   private void moveIndexToFirst(int i) {
      if (this.size != 1 && this.first != i) {
         if (this.last == i) {
            this.last = (int)(this.link[i] >>> 32);
            this.link[this.last] = this.link[this.last] | 4294967295L;
         } else {
            long linki = this.link[i];
            int prev = (int)(linki >>> 32);
            int next = (int)linki;
            this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 4294967295L) & 4294967295L;
            this.link[next] = this.link[next] ^ (this.link[next] ^ linki & -4294967296L) & -4294967296L;
         }

         this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ ((long)i & 4294967295L) << 32) & -4294967296L;
         this.link[i] = -4294967296L | (long)this.first & 4294967295L;
         this.first = i;
      }
   }

   private void moveIndexToLast(int i) {
      if (this.size != 1 && this.last != i) {
         if (this.first == i) {
            this.first = (int)this.link[i];
            this.link[this.first] = this.link[this.first] | -4294967296L;
         } else {
            long linki = this.link[i];
            int prev = (int)(linki >>> 32);
            int next = (int)linki;
            this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 4294967295L) & 4294967295L;
            this.link[next] = this.link[next] ^ (this.link[next] ^ linki & -4294967296L) & -4294967296L;
         }

         this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ (long)i & 4294967295L) & 4294967295L;
         this.link[i] = ((long)this.last & 4294967295L) << 32 | 4294967295L;
         this.last = i;
      }
   }

   public boolean addAndMoveToFirst(int k) {
      int pos;
      if (k == 0) {
         if (this.containsNull) {
            this.moveIndexToFirst(this.n);
            return false;
         }

         this.containsNull = true;
         pos = this.n;
      } else {
         int[] key = this.key;

         for (pos = HashCommon.mix(k) & this.mask; key[pos] != 0; pos = pos + 1 & this.mask) {
            if (k == key[pos]) {
               this.moveIndexToFirst(pos);
               return false;
            }
         }
      }

      this.key[pos] = k;
      if (this.size == 0) {
         this.first = this.last = pos;
         this.link[pos] = -1L;
      } else {
         this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ ((long)pos & 4294967295L) << 32) & -4294967296L;
         this.link[pos] = -4294967296L | (long)this.first & 4294967295L;
         this.first = pos;
      }

      if (this.size++ >= this.maxFill) {
         this.rehash(HashCommon.arraySize(this.size, this.f));
      }

      return true;
   }

   public boolean addAndMoveToLast(int k) {
      int pos;
      if (k == 0) {
         if (this.containsNull) {
            this.moveIndexToLast(this.n);
            return false;
         }

         this.containsNull = true;
         pos = this.n;
      } else {
         int[] key = this.key;

         for (pos = HashCommon.mix(k) & this.mask; key[pos] != 0; pos = pos + 1 & this.mask) {
            if (k == key[pos]) {
               this.moveIndexToLast(pos);
               return false;
            }
         }
      }

      this.key[pos] = k;
      if (this.size == 0) {
         this.first = this.last = pos;
         this.link[pos] = -1L;
      } else {
         this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ (long)pos & 4294967295L) & 4294967295L;
         this.link[pos] = ((long)this.last & 4294967295L) << 32 | 4294967295L;
         this.last = pos;
      }

      if (this.size++ >= this.maxFill) {
         this.rehash(HashCommon.arraySize(this.size, this.f));
      }

      return true;
   }

   @Override
   public void clear() {
      if (this.size != 0) {
         this.size = 0;
         this.containsNull = false;
         Arrays.fill(this.key, 0);
         this.first = this.last = -1;
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

   protected void fixPointers(int i) {
      if (this.size == 0) {
         this.first = this.last = -1;
      } else if (this.first == i) {
         this.first = (int)this.link[i];
         if (0 <= this.first) {
            this.link[this.first] = this.link[this.first] | -4294967296L;
         }
      } else if (this.last == i) {
         this.last = (int)(this.link[i] >>> 32);
         if (0 <= this.last) {
            this.link[this.last] = this.link[this.last] | 4294967295L;
         }
      } else {
         long linki = this.link[i];
         int prev = (int)(linki >>> 32);
         int next = (int)linki;
         this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 4294967295L) & 4294967295L;
         this.link[next] = this.link[next] ^ (this.link[next] ^ linki & -4294967296L) & -4294967296L;
      }
   }

   protected void fixPointers(int s, int d) {
      if (this.size == 1) {
         this.first = this.last = d;
         this.link[d] = -1L;
      } else if (this.first == s) {
         this.first = d;
         this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ ((long)d & 4294967295L) << 32) & -4294967296L;
         this.link[d] = this.link[s];
      } else if (this.last == s) {
         this.last = d;
         this.link[(int)(this.link[s] >>> 32)] = this.link[(int)(this.link[s] >>> 32)]
            ^ (this.link[(int)(this.link[s] >>> 32)] ^ (long)d & 4294967295L) & 4294967295L;
         this.link[d] = this.link[s];
      } else {
         long links = this.link[s];
         int prev = (int)(links >>> 32);
         int next = (int)links;
         this.link[prev] = this.link[prev] ^ (this.link[prev] ^ (long)d & 4294967295L) & 4294967295L;
         this.link[next] = this.link[next] ^ (this.link[next] ^ ((long)d & 4294967295L) << 32) & -4294967296L;
         this.link[d] = links;
      }
   }

   @Override
   public int firstInt() {
      if (this.size == 0) {
         throw new NoSuchElementException();
      } else {
         return this.key[this.first];
      }
   }

   @Override
   public int lastInt() {
      if (this.size == 0) {
         throw new NoSuchElementException();
      } else {
         return this.key[this.last];
      }
   }

   @Override
   public IntSortedSet tailSet(int from) {
      throw new UnsupportedOperationException();
   }

   @Override
   public IntSortedSet headSet(int to) {
      throw new UnsupportedOperationException();
   }

   @Override
   public IntSortedSet subSet(int from, int to) {
      throw new UnsupportedOperationException();
   }

   @Override
   public IntComparator comparator() {
      return null;
   }

   public IntListIterator iterator(int from) {
      return new IntLinkedOpenHashSet.SetIterator(from);
   }

   public IntListIterator iterator() {
      return new IntLinkedOpenHashSet.SetIterator();
   }

   @Override
   public IntSpliterator spliterator() {
      return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this), 337);
   }

   @Override
   public void forEach(java.util.function.IntConsumer action) {
      int next = this.first;

      while (next != -1) {
         int curr = next;
         next = (int)this.link[next];
         action.accept(this.key[curr]);
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
      int i = this.first;
      int prev = -1;
      int newPrev = -1;
      long[] link = this.link;
      long[] newLink = new long[newN + 1];
      this.first = -1;
      int j = this.size;

      while (j-- != 0) {
         int pos;
         if (key[i] == 0) {
            pos = newN;
         } else {
            pos = HashCommon.mix(key[i]) & mask;

            while (newKey[pos] != 0) {
               pos = pos + 1 & mask;
            }
         }

         newKey[pos] = key[i];
         if (prev != -1) {
            newLink[newPrev] ^= (newLink[newPrev] ^ (long)pos & 4294967295L) & 4294967295L;
            newLink[pos] ^= (newLink[pos] ^ ((long)newPrev & 4294967295L) << 32) & -4294967296L;
            newPrev = pos;
         } else {
            newPrev = this.first = pos;
            newLink[pos] = -1L;
         }

         int t = i;
         i = (int)link[i];
         prev = t;
      }

      this.link = newLink;
      this.last = newPrev;
      if (newPrev != -1) {
         newLink[newPrev] |= 4294967295L;
      }

      this.n = newN;
      this.mask = mask;
      this.maxFill = HashCommon.maxFill(this.n, this.f);
      this.key = newKey;
   }

   public IntLinkedOpenHashSet clone() {
      IntLinkedOpenHashSet c;
      try {
         c = (IntLinkedOpenHashSet)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      c.key = (int[])this.key.clone();
      c.containsNull = this.containsNull;
      c.link = (long[])this.link.clone();
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
      long[] link = this.link = new long[this.n + 1];
      int prev = -1;
      this.first = this.last = -1;
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
         if (this.first != -1) {
            link[prev] ^= (link[prev] ^ (long)pos & 4294967295L) & 4294967295L;
            link[pos] ^= (link[pos] ^ ((long)prev & 4294967295L) << 32) & -4294967296L;
            prev = pos;
         } else {
            prev = this.first = pos;
            link[pos] |= -4294967296L;
         }
      }

      this.last = prev;
      if (prev != -1) {
         link[prev] |= 4294967295L;
      }
   }

   private void checkTable() {
   }

   private final class SetIterator implements IntListIterator {
      int prev = -1;
      int next = -1;
      int curr = -1;
      int index = -1;

      SetIterator() {
         this.next = IntLinkedOpenHashSet.this.first;
         this.index = 0;
      }

      SetIterator(int from) {
         if (from == 0) {
            if (IntLinkedOpenHashSet.this.containsNull) {
               this.next = (int)IntLinkedOpenHashSet.this.link[IntLinkedOpenHashSet.this.n];
               this.prev = IntLinkedOpenHashSet.this.n;
            } else {
               throw new NoSuchElementException("The key " + from + " does not belong to this set.");
            }
         } else if (IntLinkedOpenHashSet.this.key[IntLinkedOpenHashSet.this.last] == from) {
            this.prev = IntLinkedOpenHashSet.this.last;
            this.index = IntLinkedOpenHashSet.this.size;
         } else {
            int[] key = IntLinkedOpenHashSet.this.key;

            for (int pos = HashCommon.mix(from) & IntLinkedOpenHashSet.this.mask; key[pos] != 0; pos = pos + 1 & IntLinkedOpenHashSet.this.mask) {
               if (key[pos] == from) {
                  this.next = (int)IntLinkedOpenHashSet.this.link[pos];
                  this.prev = pos;
                  return;
               }
            }

            throw new NoSuchElementException("The key " + from + " does not belong to this set.");
         }
      }

      @Override
      public boolean hasNext() {
         return this.next != -1;
      }

      @Override
      public boolean hasPrevious() {
         return this.prev != -1;
      }

      @Override
      public int nextInt() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this.curr = this.next;
            this.next = (int)IntLinkedOpenHashSet.this.link[this.curr];
            this.prev = this.curr;
            if (this.index >= 0) {
               this.index++;
            }

            return IntLinkedOpenHashSet.this.key[this.curr];
         }
      }

      @Override
      public int previousInt() {
         if (!this.hasPrevious()) {
            throw new NoSuchElementException();
         } else {
            this.curr = this.prev;
            this.prev = (int)(IntLinkedOpenHashSet.this.link[this.curr] >>> 32);
            this.next = this.curr;
            if (this.index >= 0) {
               this.index--;
            }

            return IntLinkedOpenHashSet.this.key[this.curr];
         }
      }

      @Override
      public void forEachRemaining(java.util.function.IntConsumer action) {
         int[] key = IntLinkedOpenHashSet.this.key;
         long[] link = IntLinkedOpenHashSet.this.link;

         while (this.next != -1) {
            this.curr = this.next;
            this.next = (int)link[this.curr];
            this.prev = this.curr;
            if (this.index >= 0) {
               this.index++;
            }

            action.accept(key[this.curr]);
         }
      }

      private final void ensureIndexKnown() {
         if (this.index < 0) {
            if (this.prev == -1) {
               this.index = 0;
            } else if (this.next == -1) {
               this.index = IntLinkedOpenHashSet.this.size;
            } else {
               int pos = IntLinkedOpenHashSet.this.first;

               for (this.index = 1; pos != this.prev; this.index++) {
                  pos = (int)IntLinkedOpenHashSet.this.link[pos];
               }
            }
         }
      }

      @Override
      public int nextIndex() {
         this.ensureIndexKnown();
         return this.index;
      }

      @Override
      public int previousIndex() {
         this.ensureIndexKnown();
         return this.index - 1;
      }

      @Override
      public void remove() {
         this.ensureIndexKnown();
         if (this.curr == -1) {
            throw new IllegalStateException();
         } else {
            if (this.curr == this.prev) {
               this.index--;
               this.prev = (int)(IntLinkedOpenHashSet.this.link[this.curr] >>> 32);
            } else {
               this.next = (int)IntLinkedOpenHashSet.this.link[this.curr];
            }

            IntLinkedOpenHashSet.this.size--;
            if (this.prev == -1) {
               IntLinkedOpenHashSet.this.first = this.next;
            } else {
               IntLinkedOpenHashSet.this.link[this.prev] = IntLinkedOpenHashSet.this.link[this.prev]
                  ^ (IntLinkedOpenHashSet.this.link[this.prev] ^ (long)this.next & 4294967295L) & 4294967295L;
            }

            if (this.next == -1) {
               IntLinkedOpenHashSet.this.last = this.prev;
            } else {
               IntLinkedOpenHashSet.this.link[this.next] = IntLinkedOpenHashSet.this.link[this.next]
                  ^ (IntLinkedOpenHashSet.this.link[this.next] ^ ((long)this.prev & 4294967295L) << 32) & -4294967296L;
            }

            int pos = this.curr;
            this.curr = -1;
            if (pos == IntLinkedOpenHashSet.this.n) {
               IntLinkedOpenHashSet.this.containsNull = false;
               IntLinkedOpenHashSet.this.key[IntLinkedOpenHashSet.this.n] = 0;
            } else {
               int[] key = IntLinkedOpenHashSet.this.key;

               label61:
               while (true) {
                  int last = pos;

                  int curr;
                  for (pos = pos + 1 & IntLinkedOpenHashSet.this.mask; (curr = key[pos]) != 0; pos = pos + 1 & IntLinkedOpenHashSet.this.mask) {
                     int slot = HashCommon.mix(curr) & IntLinkedOpenHashSet.this.mask;
                     if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
                        key[last] = curr;
                        if (this.next == pos) {
                           this.next = last;
                        }

                        if (this.prev == pos) {
                           this.prev = last;
                        }

                        IntLinkedOpenHashSet.this.fixPointers(pos, last);
                        continue label61;
                     }
                  }

                  key[last] = 0;
                  return;
               }
            }
         }
      }
   }
}
