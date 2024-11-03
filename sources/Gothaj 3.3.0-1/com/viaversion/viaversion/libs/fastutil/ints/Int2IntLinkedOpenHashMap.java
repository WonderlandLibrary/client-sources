package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSortedSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntFunction;

public class Int2IntLinkedOpenHashMap extends AbstractInt2IntSortedMap implements Serializable, Cloneable, Hash {
   private static final long serialVersionUID = 0L;
   private static final boolean ASSERTS = false;
   protected transient int[] key;
   protected transient int[] value;
   protected transient int mask;
   protected transient boolean containsNullKey;
   protected transient int first = -1;
   protected transient int last = -1;
   protected transient long[] link;
   protected transient int n;
   protected transient int maxFill;
   protected final transient int minN;
   protected int size;
   protected final float f;
   protected transient Int2IntSortedMap.FastSortedEntrySet entries;
   protected transient IntSortedSet keys;
   protected transient IntCollection values;

   public Int2IntLinkedOpenHashMap(int expected, float f) {
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
         this.value = new int[this.n + 1];
         this.link = new long[this.n + 1];
      }
   }

   public Int2IntLinkedOpenHashMap(int expected) {
      this(expected, 0.75F);
   }

   public Int2IntLinkedOpenHashMap() {
      this(16, 0.75F);
   }

   public Int2IntLinkedOpenHashMap(Map<? extends Integer, ? extends Integer> m, float f) {
      this(m.size(), f);
      this.putAll(m);
   }

   public Int2IntLinkedOpenHashMap(Map<? extends Integer, ? extends Integer> m) {
      this(m, 0.75F);
   }

   public Int2IntLinkedOpenHashMap(Int2IntMap m, float f) {
      this(m.size(), f);
      this.putAll(m);
   }

   public Int2IntLinkedOpenHashMap(Int2IntMap m) {
      this(m, 0.75F);
   }

   public Int2IntLinkedOpenHashMap(int[] k, int[] v, float f) {
      this(k.length, f);
      if (k.length != v.length) {
         throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
      } else {
         for (int i = 0; i < k.length; i++) {
            this.put(k[i], v[i]);
         }
      }
   }

   public Int2IntLinkedOpenHashMap(int[] k, int[] v) {
      this(k, v, 0.75F);
   }

   private int realSize() {
      return this.containsNullKey ? this.size - 1 : this.size;
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

   private int removeEntry(int pos) {
      int oldValue = this.value[pos];
      this.size--;
      this.fixPointers(pos);
      this.shiftKeys(pos);
      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
         this.rehash(this.n / 2);
      }

      return oldValue;
   }

   private int removeNullEntry() {
      this.containsNullKey = false;
      int oldValue = this.value[this.n];
      this.size--;
      this.fixPointers(this.n);
      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
         this.rehash(this.n / 2);
      }

      return oldValue;
   }

   @Override
   public void putAll(Map<? extends Integer, ? extends Integer> m) {
      if ((double)this.f <= 0.5) {
         this.ensureCapacity(m.size());
      } else {
         this.tryCapacity((long)(this.size() + m.size()));
      }

      super.putAll(m);
   }

   private int find(int k) {
      if (k == 0) {
         return this.containsNullKey ? this.n : -(this.n + 1);
      } else {
         int[] key = this.key;
         int curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
            return -(pos + 1);
         } else if (k == curr) {
            return pos;
         } else {
            while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (k == curr) {
                  return pos;
               }
            }

            return -(pos + 1);
         }
      }
   }

   private void insert(int pos, int k, int v) {
      if (pos == this.n) {
         this.containsNullKey = true;
      }

      this.key[pos] = k;
      this.value[pos] = v;
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
   }

   @Override
   public int put(int k, int v) {
      int pos = this.find(k);
      if (pos < 0) {
         this.insert(-pos - 1, k, v);
         return this.defRetValue;
      } else {
         int oldValue = this.value[pos];
         this.value[pos] = v;
         return oldValue;
      }
   }

   private int addToValue(int pos, int incr) {
      int oldValue = this.value[pos];
      this.value[pos] = oldValue + incr;
      return oldValue;
   }

   public int addTo(int k, int incr) {
      int pos;
      if (k == 0) {
         if (this.containsNullKey) {
            return this.addToValue(this.n, incr);
         }

         pos = this.n;
         this.containsNullKey = true;
      } else {
         int[] key = this.key;
         int curr;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
            if (curr == k) {
               return this.addToValue(pos, incr);
            }

            while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (curr == k) {
                  return this.addToValue(pos, incr);
               }
            }
         }
      }

      this.key[pos] = k;
      this.value[pos] = this.defRetValue + incr;
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

      return this.defRetValue;
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
               this.value[last] = this.value[pos];
               this.fixPointers(pos, last);
               continue label30;
            }
         }

         key[last] = 0;
         return;
      }
   }

   @Override
   public int remove(int k) {
      if (k == 0) {
         return this.containsNullKey ? this.removeNullEntry() : this.defRetValue;
      } else {
         int[] key = this.key;
         int curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
            return this.defRetValue;
         } else if (k == curr) {
            return this.removeEntry(pos);
         } else {
            while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (k == curr) {
                  return this.removeEntry(pos);
               }
            }

            return this.defRetValue;
         }
      }
   }

   private int setValue(int pos, int v) {
      int oldValue = this.value[pos];
      this.value[pos] = v;
      return oldValue;
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

         this.size--;
         int v = this.value[pos];
         if (pos == this.n) {
            this.containsNullKey = false;
         } else {
            this.shiftKeys(pos);
         }

         if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
         }

         return v;
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

         this.size--;
         int v = this.value[pos];
         if (pos == this.n) {
            this.containsNullKey = false;
         } else {
            this.shiftKeys(pos);
         }

         if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
         }

         return v;
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

   public int getAndMoveToFirst(int k) {
      if (k == 0) {
         if (this.containsNullKey) {
            this.moveIndexToFirst(this.n);
            return this.value[this.n];
         } else {
            return this.defRetValue;
         }
      } else {
         int[] key = this.key;
         int curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
            return this.defRetValue;
         } else if (k == curr) {
            this.moveIndexToFirst(pos);
            return this.value[pos];
         } else {
            while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (k == curr) {
                  this.moveIndexToFirst(pos);
                  return this.value[pos];
               }
            }

            return this.defRetValue;
         }
      }
   }

   public int getAndMoveToLast(int k) {
      if (k == 0) {
         if (this.containsNullKey) {
            this.moveIndexToLast(this.n);
            return this.value[this.n];
         } else {
            return this.defRetValue;
         }
      } else {
         int[] key = this.key;
         int curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
            return this.defRetValue;
         } else if (k == curr) {
            this.moveIndexToLast(pos);
            return this.value[pos];
         } else {
            while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (k == curr) {
                  this.moveIndexToLast(pos);
                  return this.value[pos];
               }
            }

            return this.defRetValue;
         }
      }
   }

   public int putAndMoveToFirst(int k, int v) {
      int pos;
      if (k == 0) {
         if (this.containsNullKey) {
            this.moveIndexToFirst(this.n);
            return this.setValue(this.n, v);
         }

         this.containsNullKey = true;
         pos = this.n;
      } else {
         int[] key = this.key;
         int curr;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
            if (curr == k) {
               this.moveIndexToFirst(pos);
               return this.setValue(pos, v);
            }

            while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (curr == k) {
                  this.moveIndexToFirst(pos);
                  return this.setValue(pos, v);
               }
            }
         }
      }

      this.key[pos] = k;
      this.value[pos] = v;
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

      return this.defRetValue;
   }

   public int putAndMoveToLast(int k, int v) {
      int pos;
      if (k == 0) {
         if (this.containsNullKey) {
            this.moveIndexToLast(this.n);
            return this.setValue(this.n, v);
         }

         this.containsNullKey = true;
         pos = this.n;
      } else {
         int[] key = this.key;
         int curr;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
            if (curr == k) {
               this.moveIndexToLast(pos);
               return this.setValue(pos, v);
            }

            while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (curr == k) {
                  this.moveIndexToLast(pos);
                  return this.setValue(pos, v);
               }
            }
         }
      }

      this.key[pos] = k;
      this.value[pos] = v;
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

      return this.defRetValue;
   }

   @Override
   public int get(int k) {
      if (k == 0) {
         return this.containsNullKey ? this.value[this.n] : this.defRetValue;
      } else {
         int[] key = this.key;
         int curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
            return this.defRetValue;
         } else if (k == curr) {
            return this.value[pos];
         } else {
            while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (k == curr) {
                  return this.value[pos];
               }
            }

            return this.defRetValue;
         }
      }
   }

   @Override
   public boolean containsKey(int k) {
      if (k == 0) {
         return this.containsNullKey;
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
   public boolean containsValue(int v) {
      int[] value = this.value;
      int[] key = this.key;
      if (this.containsNullKey && value[this.n] == v) {
         return true;
      } else {
         int i = this.n;

         while (i-- != 0) {
            if (key[i] != 0 && value[i] == v) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public int getOrDefault(int k, int defaultValue) {
      if (k == 0) {
         return this.containsNullKey ? this.value[this.n] : defaultValue;
      } else {
         int[] key = this.key;
         int curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
            return defaultValue;
         } else if (k == curr) {
            return this.value[pos];
         } else {
            while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (k == curr) {
                  return this.value[pos];
               }
            }

            return defaultValue;
         }
      }
   }

   @Override
   public int putIfAbsent(int k, int v) {
      int pos = this.find(k);
      if (pos >= 0) {
         return this.value[pos];
      } else {
         this.insert(-pos - 1, k, v);
         return this.defRetValue;
      }
   }

   @Override
   public boolean remove(int k, int v) {
      if (k == 0) {
         if (this.containsNullKey && v == this.value[this.n]) {
            this.removeNullEntry();
            return true;
         } else {
            return false;
         }
      } else {
         int[] key = this.key;
         int curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
            return false;
         } else if (k == curr && v == this.value[pos]) {
            this.removeEntry(pos);
            return true;
         } else {
            while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (k == curr && v == this.value[pos]) {
                  this.removeEntry(pos);
                  return true;
               }
            }

            return false;
         }
      }
   }

   @Override
   public boolean replace(int k, int oldValue, int v) {
      int pos = this.find(k);
      if (pos >= 0 && oldValue == this.value[pos]) {
         this.value[pos] = v;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public int replace(int k, int v) {
      int pos = this.find(k);
      if (pos < 0) {
         return this.defRetValue;
      } else {
         int oldValue = this.value[pos];
         this.value[pos] = v;
         return oldValue;
      }
   }

   @Override
   public int computeIfAbsent(int k, java.util.function.IntUnaryOperator mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      int pos = this.find(k);
      if (pos >= 0) {
         return this.value[pos];
      } else {
         int newValue = mappingFunction.applyAsInt(k);
         this.insert(-pos - 1, k, newValue);
         return newValue;
      }
   }

   @Override
   public int computeIfAbsent(int key, Int2IntFunction mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      int pos = this.find(key);
      if (pos >= 0) {
         return this.value[pos];
      } else if (!mappingFunction.containsKey(key)) {
         return this.defRetValue;
      } else {
         int newValue = mappingFunction.get(key);
         this.insert(-pos - 1, key, newValue);
         return newValue;
      }
   }

   @Override
   public int computeIfAbsentNullable(int k, IntFunction<? extends Integer> mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      int pos = this.find(k);
      if (pos >= 0) {
         return this.value[pos];
      } else {
         Integer newValue = mappingFunction.apply(k);
         if (newValue == null) {
            return this.defRetValue;
         } else {
            int v = newValue;
            this.insert(-pos - 1, k, v);
            return v;
         }
      }
   }

   @Override
   public int computeIfPresent(int k, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int pos = this.find(k);
      if (pos < 0) {
         return this.defRetValue;
      } else {
         Integer newValue = remappingFunction.apply(k, this.value[pos]);
         if (newValue == null) {
            if (k == 0) {
               this.removeNullEntry();
            } else {
               this.removeEntry(pos);
            }

            return this.defRetValue;
         } else {
            return this.value[pos] = newValue;
         }
      }
   }

   @Override
   public int compute(int k, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int pos = this.find(k);
      Integer newValue = remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
      if (newValue == null) {
         if (pos >= 0) {
            if (k == 0) {
               this.removeNullEntry();
            } else {
               this.removeEntry(pos);
            }
         }

         return this.defRetValue;
      } else {
         int newVal = newValue;
         if (pos < 0) {
            this.insert(-pos - 1, k, newVal);
            return newVal;
         } else {
            return this.value[pos] = newVal;
         }
      }
   }

   @Override
   public int merge(int k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int pos = this.find(k);
      if (pos < 0) {
         if (pos < 0) {
            this.insert(-pos - 1, k, v);
         } else {
            this.value[pos] = v;
         }

         return v;
      } else {
         Integer newValue = remappingFunction.apply(this.value[pos], v);
         if (newValue == null) {
            if (k == 0) {
               this.removeNullEntry();
            } else {
               this.removeEntry(pos);
            }

            return this.defRetValue;
         } else {
            return this.value[pos] = newValue;
         }
      }
   }

   @Override
   public void clear() {
      if (this.size != 0) {
         this.size = 0;
         this.containsNullKey = false;
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
   public int firstIntKey() {
      if (this.size == 0) {
         throw new NoSuchElementException();
      } else {
         return this.key[this.first];
      }
   }

   @Override
   public int lastIntKey() {
      if (this.size == 0) {
         throw new NoSuchElementException();
      } else {
         return this.key[this.last];
      }
   }

   @Override
   public Int2IntSortedMap tailMap(int from) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Int2IntSortedMap headMap(int to) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Int2IntSortedMap subMap(int from, int to) {
      throw new UnsupportedOperationException();
   }

   @Override
   public IntComparator comparator() {
      return null;
   }

   public Int2IntSortedMap.FastSortedEntrySet int2IntEntrySet() {
      if (this.entries == null) {
         this.entries = new Int2IntLinkedOpenHashMap.MapEntrySet();
      }

      return this.entries;
   }

   @Override
   public IntSortedSet keySet() {
      if (this.keys == null) {
         this.keys = new Int2IntLinkedOpenHashMap.KeySet();
      }

      return this.keys;
   }

   @Override
   public IntCollection values() {
      if (this.values == null) {
         this.values = new AbstractIntCollection() {
            private static final int SPLITERATOR_CHARACTERISTICS = 336;

            @Override
            public IntIterator iterator() {
               return Int2IntLinkedOpenHashMap.this.new ValueIterator();
            }

            @Override
            public IntSpliterator spliterator() {
               return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(Int2IntLinkedOpenHashMap.this), 336);
            }

            @Override
            public void forEach(java.util.function.IntConsumer consumer) {
               int i = Int2IntLinkedOpenHashMap.this.size;
               int next = Int2IntLinkedOpenHashMap.this.first;

               while (i-- != 0) {
                  int curr = next;
                  next = (int)Int2IntLinkedOpenHashMap.this.link[next];
                  consumer.accept(Int2IntLinkedOpenHashMap.this.value[curr]);
               }
            }

            @Override
            public int size() {
               return Int2IntLinkedOpenHashMap.this.size;
            }

            @Override
            public boolean contains(int v) {
               return Int2IntLinkedOpenHashMap.this.containsValue(v);
            }

            @Override
            public void clear() {
               Int2IntLinkedOpenHashMap.this.clear();
            }
         };
      }

      return this.values;
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
      int[] value = this.value;
      int mask = newN - 1;
      int[] newKey = new int[newN + 1];
      int[] newValue = new int[newN + 1];
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
         newValue[pos] = value[i];
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
      this.value = newValue;
   }

   public Int2IntLinkedOpenHashMap clone() {
      Int2IntLinkedOpenHashMap c;
      try {
         c = (Int2IntLinkedOpenHashMap)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      c.keys = null;
      c.values = null;
      c.entries = null;
      c.containsNullKey = this.containsNullKey;
      c.key = (int[])this.key.clone();
      c.value = (int[])this.value.clone();
      c.link = (long[])this.link.clone();
      return c;
   }

   @Override
   public int hashCode() {
      int h = 0;
      int j = this.realSize();
      int i = 0;

      for (int t = 0; j-- != 0; i++) {
         while (this.key[i] == 0) {
            i++;
         }

         t = this.key[i];
         t ^= this.value[i];
         h += t;
      }

      if (this.containsNullKey) {
         h += this.value[this.n];
      }

      return h;
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      int[] key = this.key;
      int[] value = this.value;
      Int2IntLinkedOpenHashMap.EntryIterator i = new Int2IntLinkedOpenHashMap.EntryIterator();
      s.defaultWriteObject();
      int j = this.size;

      while (j-- != 0) {
         int e = i.nextEntry();
         s.writeInt(key[e]);
         s.writeInt(value[e]);
      }
   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.n = HashCommon.arraySize(this.size, this.f);
      this.maxFill = HashCommon.maxFill(this.n, this.f);
      this.mask = this.n - 1;
      int[] key = this.key = new int[this.n + 1];
      int[] value = this.value = new int[this.n + 1];
      long[] link = this.link = new long[this.n + 1];
      int prev = -1;
      this.first = this.last = -1;
      int i = this.size;

      while (i-- != 0) {
         int k = s.readInt();
         int v = s.readInt();
         int pos;
         if (k == 0) {
            pos = this.n;
            this.containsNullKey = true;
         } else {
            pos = HashCommon.mix(k) & this.mask;

            while (key[pos] != 0) {
               pos = pos + 1 & this.mask;
            }
         }

         key[pos] = k;
         value[pos] = v;
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

   private final class EntryIterator
      extends Int2IntLinkedOpenHashMap.MapIterator<Consumer<? super Int2IntMap.Entry>>
      implements ObjectListIterator<Int2IntMap.Entry> {
      private Int2IntLinkedOpenHashMap.MapEntry entry;

      public EntryIterator() {
      }

      public EntryIterator(int from) {
         super(from);
      }

      final void acceptOnIndex(Consumer<? super Int2IntMap.Entry> action, int index) {
         action.accept(Int2IntLinkedOpenHashMap.this.new MapEntry(index));
      }

      public Int2IntLinkedOpenHashMap.MapEntry next() {
         return this.entry = Int2IntLinkedOpenHashMap.this.new MapEntry(this.nextEntry());
      }

      public Int2IntLinkedOpenHashMap.MapEntry previous() {
         return this.entry = Int2IntLinkedOpenHashMap.this.new MapEntry(this.previousEntry());
      }

      @Override
      public void remove() {
         super.remove();
         this.entry.index = -1;
      }
   }

   private final class FastEntryIterator
      extends Int2IntLinkedOpenHashMap.MapIterator<Consumer<? super Int2IntMap.Entry>>
      implements ObjectListIterator<Int2IntMap.Entry> {
      final Int2IntLinkedOpenHashMap.MapEntry entry;

      public FastEntryIterator() {
         this.entry = Int2IntLinkedOpenHashMap.this.new MapEntry();
      }

      public FastEntryIterator(int from) {
         super(from);
         this.entry = Int2IntLinkedOpenHashMap.this.new MapEntry();
      }

      final void acceptOnIndex(Consumer<? super Int2IntMap.Entry> action, int index) {
         this.entry.index = index;
         action.accept(this.entry);
      }

      public Int2IntLinkedOpenHashMap.MapEntry next() {
         this.entry.index = this.nextEntry();
         return this.entry;
      }

      public Int2IntLinkedOpenHashMap.MapEntry previous() {
         this.entry.index = this.previousEntry();
         return this.entry;
      }
   }

   private final class KeyIterator extends Int2IntLinkedOpenHashMap.MapIterator<java.util.function.IntConsumer> implements IntListIterator {
      public KeyIterator(int k) {
         super(k);
      }

      @Override
      public int previousInt() {
         return Int2IntLinkedOpenHashMap.this.key[this.previousEntry()];
      }

      public KeyIterator() {
      }

      final void acceptOnIndex(java.util.function.IntConsumer action, int index) {
         action.accept(Int2IntLinkedOpenHashMap.this.key[index]);
      }

      @Override
      public int nextInt() {
         return Int2IntLinkedOpenHashMap.this.key[this.nextEntry()];
      }
   }

   private final class KeySet extends AbstractIntSortedSet {
      private static final int SPLITERATOR_CHARACTERISTICS = 337;

      private KeySet() {
      }

      public IntListIterator iterator(int from) {
         return Int2IntLinkedOpenHashMap.this.new KeyIterator(from);
      }

      public IntListIterator iterator() {
         return Int2IntLinkedOpenHashMap.this.new KeyIterator();
      }

      @Override
      public IntSpliterator spliterator() {
         return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(Int2IntLinkedOpenHashMap.this), 337);
      }

      @Override
      public void forEach(java.util.function.IntConsumer consumer) {
         int i = Int2IntLinkedOpenHashMap.this.size;
         int next = Int2IntLinkedOpenHashMap.this.first;

         while (i-- != 0) {
            int curr = next;
            next = (int)Int2IntLinkedOpenHashMap.this.link[next];
            consumer.accept(Int2IntLinkedOpenHashMap.this.key[curr]);
         }
      }

      @Override
      public int size() {
         return Int2IntLinkedOpenHashMap.this.size;
      }

      @Override
      public boolean contains(int k) {
         return Int2IntLinkedOpenHashMap.this.containsKey(k);
      }

      @Override
      public boolean remove(int k) {
         int oldSize = Int2IntLinkedOpenHashMap.this.size;
         Int2IntLinkedOpenHashMap.this.remove(k);
         return Int2IntLinkedOpenHashMap.this.size != oldSize;
      }

      @Override
      public void clear() {
         Int2IntLinkedOpenHashMap.this.clear();
      }

      @Override
      public int firstInt() {
         if (Int2IntLinkedOpenHashMap.this.size == 0) {
            throw new NoSuchElementException();
         } else {
            return Int2IntLinkedOpenHashMap.this.key[Int2IntLinkedOpenHashMap.this.first];
         }
      }

      @Override
      public int lastInt() {
         if (Int2IntLinkedOpenHashMap.this.size == 0) {
            throw new NoSuchElementException();
         } else {
            return Int2IntLinkedOpenHashMap.this.key[Int2IntLinkedOpenHashMap.this.last];
         }
      }

      @Override
      public IntComparator comparator() {
         return null;
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
   }

   final class MapEntry implements Int2IntMap.Entry, Entry<Integer, Integer>, IntIntPair {
      int index;

      MapEntry(int index) {
         this.index = index;
      }

      MapEntry() {
      }

      @Override
      public int getIntKey() {
         return Int2IntLinkedOpenHashMap.this.key[this.index];
      }

      @Override
      public int leftInt() {
         return Int2IntLinkedOpenHashMap.this.key[this.index];
      }

      @Override
      public int getIntValue() {
         return Int2IntLinkedOpenHashMap.this.value[this.index];
      }

      @Override
      public int rightInt() {
         return Int2IntLinkedOpenHashMap.this.value[this.index];
      }

      @Override
      public int setValue(int v) {
         int oldValue = Int2IntLinkedOpenHashMap.this.value[this.index];
         Int2IntLinkedOpenHashMap.this.value[this.index] = v;
         return oldValue;
      }

      @Override
      public IntIntPair right(int v) {
         Int2IntLinkedOpenHashMap.this.value[this.index] = v;
         return this;
      }

      @Deprecated
      @Override
      public Integer getKey() {
         return Int2IntLinkedOpenHashMap.this.key[this.index];
      }

      @Deprecated
      @Override
      public Integer getValue() {
         return Int2IntLinkedOpenHashMap.this.value[this.index];
      }

      @Deprecated
      @Override
      public Integer setValue(Integer v) {
         return this.setValue(v.intValue());
      }

      @Override
      public boolean equals(Object o) {
         if (!(o instanceof Entry)) {
            return false;
         } else {
            Entry<Integer, Integer> e = (Entry<Integer, Integer>)o;
            return Int2IntLinkedOpenHashMap.this.key[this.index] == e.getKey() && Int2IntLinkedOpenHashMap.this.value[this.index] == e.getValue();
         }
      }

      @Override
      public int hashCode() {
         return Int2IntLinkedOpenHashMap.this.key[this.index] ^ Int2IntLinkedOpenHashMap.this.value[this.index];
      }

      @Override
      public String toString() {
         return Int2IntLinkedOpenHashMap.this.key[this.index] + "=>" + Int2IntLinkedOpenHashMap.this.value[this.index];
      }
   }

   private final class MapEntrySet extends AbstractObjectSortedSet<Int2IntMap.Entry> implements Int2IntSortedMap.FastSortedEntrySet {
      private static final int SPLITERATOR_CHARACTERISTICS = 81;

      private MapEntrySet() {
      }

      @Override
      public ObjectBidirectionalIterator<Int2IntMap.Entry> iterator() {
         return Int2IntLinkedOpenHashMap.this.new EntryIterator();
      }

      @Override
      public ObjectSpliterator<Int2IntMap.Entry> spliterator() {
         return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(Int2IntLinkedOpenHashMap.this), 81);
      }

      @Override
      public Comparator<? super Int2IntMap.Entry> comparator() {
         return null;
      }

      public ObjectSortedSet<Int2IntMap.Entry> subSet(Int2IntMap.Entry fromElement, Int2IntMap.Entry toElement) {
         throw new UnsupportedOperationException();
      }

      public ObjectSortedSet<Int2IntMap.Entry> headSet(Int2IntMap.Entry toElement) {
         throw new UnsupportedOperationException();
      }

      public ObjectSortedSet<Int2IntMap.Entry> tailSet(Int2IntMap.Entry fromElement) {
         throw new UnsupportedOperationException();
      }

      public Int2IntMap.Entry first() {
         if (Int2IntLinkedOpenHashMap.this.size == 0) {
            throw new NoSuchElementException();
         } else {
            return Int2IntLinkedOpenHashMap.this.new MapEntry(Int2IntLinkedOpenHashMap.this.first);
         }
      }

      public Int2IntMap.Entry last() {
         if (Int2IntLinkedOpenHashMap.this.size == 0) {
            throw new NoSuchElementException();
         } else {
            return Int2IntLinkedOpenHashMap.this.new MapEntry(Int2IntLinkedOpenHashMap.this.last);
         }
      }

      @Override
      public boolean contains(Object o) {
         if (!(o instanceof Entry)) {
            return false;
         } else {
            Entry<?, ?> e = (Entry<?, ?>)o;
            if (e.getKey() == null || !(e.getKey() instanceof Integer)) {
               return false;
            } else if (e.getValue() != null && e.getValue() instanceof Integer) {
               int k = (Integer)e.getKey();
               int v = (Integer)e.getValue();
               if (k == 0) {
                  return Int2IntLinkedOpenHashMap.this.containsNullKey && Int2IntLinkedOpenHashMap.this.value[Int2IntLinkedOpenHashMap.this.n] == v;
               } else {
                  int[] key = Int2IntLinkedOpenHashMap.this.key;
                  int curr;
                  int pos;
                  if ((curr = key[pos = HashCommon.mix(k) & Int2IntLinkedOpenHashMap.this.mask]) == 0) {
                     return false;
                  } else if (k == curr) {
                     return Int2IntLinkedOpenHashMap.this.value[pos] == v;
                  } else {
                     while ((curr = key[pos = pos + 1 & Int2IntLinkedOpenHashMap.this.mask]) != 0) {
                        if (k == curr) {
                           return Int2IntLinkedOpenHashMap.this.value[pos] == v;
                        }
                     }

                     return false;
                  }
               }
            } else {
               return false;
            }
         }
      }

      @Override
      public boolean remove(Object o) {
         if (!(o instanceof Entry)) {
            return false;
         } else {
            Entry<?, ?> e = (Entry<?, ?>)o;
            if (e.getKey() != null && e.getKey() instanceof Integer) {
               if (e.getValue() != null && e.getValue() instanceof Integer) {
                  int k = (Integer)e.getKey();
                  int v = (Integer)e.getValue();
                  if (k == 0) {
                     if (Int2IntLinkedOpenHashMap.this.containsNullKey && Int2IntLinkedOpenHashMap.this.value[Int2IntLinkedOpenHashMap.this.n] == v) {
                        Int2IntLinkedOpenHashMap.this.removeNullEntry();
                        return true;
                     } else {
                        return false;
                     }
                  } else {
                     int[] key = Int2IntLinkedOpenHashMap.this.key;
                     int curr;
                     int pos;
                     if ((curr = key[pos = HashCommon.mix(k) & Int2IntLinkedOpenHashMap.this.mask]) == 0) {
                        return false;
                     } else if (curr == k) {
                        if (Int2IntLinkedOpenHashMap.this.value[pos] == v) {
                           Int2IntLinkedOpenHashMap.this.removeEntry(pos);
                           return true;
                        } else {
                           return false;
                        }
                     } else {
                        while ((curr = key[pos = pos + 1 & Int2IntLinkedOpenHashMap.this.mask]) != 0) {
                           if (curr == k && Int2IntLinkedOpenHashMap.this.value[pos] == v) {
                              Int2IntLinkedOpenHashMap.this.removeEntry(pos);
                              return true;
                           }
                        }

                        return false;
                     }
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
      }

      @Override
      public int size() {
         return Int2IntLinkedOpenHashMap.this.size;
      }

      @Override
      public void clear() {
         Int2IntLinkedOpenHashMap.this.clear();
      }

      public ObjectListIterator<Int2IntMap.Entry> iterator(Int2IntMap.Entry from) {
         return Int2IntLinkedOpenHashMap.this.new EntryIterator(from.getIntKey());
      }

      public ObjectListIterator<Int2IntMap.Entry> fastIterator() {
         return Int2IntLinkedOpenHashMap.this.new FastEntryIterator();
      }

      public ObjectListIterator<Int2IntMap.Entry> fastIterator(Int2IntMap.Entry from) {
         return Int2IntLinkedOpenHashMap.this.new FastEntryIterator(from.getIntKey());
      }

      @Override
      public void forEach(Consumer<? super Int2IntMap.Entry> consumer) {
         int i = Int2IntLinkedOpenHashMap.this.size;
         int next = Int2IntLinkedOpenHashMap.this.first;

         while (i-- != 0) {
            int curr = next;
            next = (int)Int2IntLinkedOpenHashMap.this.link[next];
            consumer.accept(Int2IntLinkedOpenHashMap.this.new MapEntry(curr));
         }
      }

      @Override
      public void fastForEach(Consumer<? super Int2IntMap.Entry> consumer) {
         Int2IntLinkedOpenHashMap.MapEntry entry = Int2IntLinkedOpenHashMap.this.new MapEntry();
         int i = Int2IntLinkedOpenHashMap.this.size;
         int next = Int2IntLinkedOpenHashMap.this.first;

         while (i-- != 0) {
            entry.index = next;
            next = (int)Int2IntLinkedOpenHashMap.this.link[next];
            consumer.accept(entry);
         }
      }
   }

   private abstract class MapIterator<ConsumerType> {
      int prev = -1;
      int next = -1;
      int curr = -1;
      int index = -1;

      abstract void acceptOnIndex(ConsumerType var1, int var2);

      protected MapIterator() {
         this.next = Int2IntLinkedOpenHashMap.this.first;
         this.index = 0;
      }

      private MapIterator(int from) {
         if (from == 0) {
            if (Int2IntLinkedOpenHashMap.this.containsNullKey) {
               this.next = (int)Int2IntLinkedOpenHashMap.this.link[Int2IntLinkedOpenHashMap.this.n];
               this.prev = Int2IntLinkedOpenHashMap.this.n;
            } else {
               throw new NoSuchElementException("The key " + from + " does not belong to this map.");
            }
         } else if (Int2IntLinkedOpenHashMap.this.key[Int2IntLinkedOpenHashMap.this.last] == from) {
            this.prev = Int2IntLinkedOpenHashMap.this.last;
            this.index = Int2IntLinkedOpenHashMap.this.size;
         } else {
            for (int pos = HashCommon.mix(from) & Int2IntLinkedOpenHashMap.this.mask;
               Int2IntLinkedOpenHashMap.this.key[pos] != 0;
               pos = pos + 1 & Int2IntLinkedOpenHashMap.this.mask
            ) {
               if (Int2IntLinkedOpenHashMap.this.key[pos] == from) {
                  this.next = (int)Int2IntLinkedOpenHashMap.this.link[pos];
                  this.prev = pos;
                  return;
               }
            }

            throw new NoSuchElementException("The key " + from + " does not belong to this map.");
         }
      }

      public boolean hasNext() {
         return this.next != -1;
      }

      public boolean hasPrevious() {
         return this.prev != -1;
      }

      private final void ensureIndexKnown() {
         if (this.index < 0) {
            if (this.prev == -1) {
               this.index = 0;
            } else if (this.next == -1) {
               this.index = Int2IntLinkedOpenHashMap.this.size;
            } else {
               int pos = Int2IntLinkedOpenHashMap.this.first;

               for (this.index = 1; pos != this.prev; this.index++) {
                  pos = (int)Int2IntLinkedOpenHashMap.this.link[pos];
               }
            }
         }
      }

      public int nextIndex() {
         this.ensureIndexKnown();
         return this.index;
      }

      public int previousIndex() {
         this.ensureIndexKnown();
         return this.index - 1;
      }

      public int nextEntry() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this.curr = this.next;
            this.next = (int)Int2IntLinkedOpenHashMap.this.link[this.curr];
            this.prev = this.curr;
            if (this.index >= 0) {
               this.index++;
            }

            return this.curr;
         }
      }

      public int previousEntry() {
         if (!this.hasPrevious()) {
            throw new NoSuchElementException();
         } else {
            this.curr = this.prev;
            this.prev = (int)(Int2IntLinkedOpenHashMap.this.link[this.curr] >>> 32);
            this.next = this.curr;
            if (this.index >= 0) {
               this.index--;
            }

            return this.curr;
         }
      }

      public void forEachRemaining(ConsumerType action) {
         while (this.hasNext()) {
            this.curr = this.next;
            this.next = (int)Int2IntLinkedOpenHashMap.this.link[this.curr];
            this.prev = this.curr;
            if (this.index >= 0) {
               this.index++;
            }

            this.acceptOnIndex(action, this.curr);
         }
      }

      public void remove() {
         this.ensureIndexKnown();
         if (this.curr == -1) {
            throw new IllegalStateException();
         } else {
            if (this.curr == this.prev) {
               this.index--;
               this.prev = (int)(Int2IntLinkedOpenHashMap.this.link[this.curr] >>> 32);
            } else {
               this.next = (int)Int2IntLinkedOpenHashMap.this.link[this.curr];
            }

            Int2IntLinkedOpenHashMap.this.size--;
            if (this.prev == -1) {
               Int2IntLinkedOpenHashMap.this.first = this.next;
            } else {
               Int2IntLinkedOpenHashMap.this.link[this.prev] = Int2IntLinkedOpenHashMap.this.link[this.prev]
                  ^ (Int2IntLinkedOpenHashMap.this.link[this.prev] ^ (long)this.next & 4294967295L) & 4294967295L;
            }

            if (this.next == -1) {
               Int2IntLinkedOpenHashMap.this.last = this.prev;
            } else {
               Int2IntLinkedOpenHashMap.this.link[this.next] = Int2IntLinkedOpenHashMap.this.link[this.next]
                  ^ (Int2IntLinkedOpenHashMap.this.link[this.next] ^ ((long)this.prev & 4294967295L) << 32) & -4294967296L;
            }

            int pos = this.curr;
            this.curr = -1;
            if (pos == Int2IntLinkedOpenHashMap.this.n) {
               Int2IntLinkedOpenHashMap.this.containsNullKey = false;
            } else {
               int[] key = Int2IntLinkedOpenHashMap.this.key;

               label61:
               while (true) {
                  int last = pos;

                  int curr;
                  for (pos = pos + 1 & Int2IntLinkedOpenHashMap.this.mask; (curr = key[pos]) != 0; pos = pos + 1 & Int2IntLinkedOpenHashMap.this.mask) {
                     int slot = HashCommon.mix(curr) & Int2IntLinkedOpenHashMap.this.mask;
                     if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
                        key[last] = curr;
                        Int2IntLinkedOpenHashMap.this.value[last] = Int2IntLinkedOpenHashMap.this.value[pos];
                        if (this.next == pos) {
                           this.next = last;
                        }

                        if (this.prev == pos) {
                           this.prev = last;
                        }

                        Int2IntLinkedOpenHashMap.this.fixPointers(pos, last);
                        continue label61;
                     }
                  }

                  key[last] = 0;
                  return;
               }
            }
         }
      }

      public int skip(int n) {
         int i = n;

         while (i-- != 0 && this.hasNext()) {
            this.nextEntry();
         }

         return n - i - 1;
      }

      public int back(int n) {
         int i = n;

         while (i-- != 0 && this.hasPrevious()) {
            this.previousEntry();
         }

         return n - i - 1;
      }

      public void set(Int2IntMap.Entry ok) {
         throw new UnsupportedOperationException();
      }

      public void add(Int2IntMap.Entry ok) {
         throw new UnsupportedOperationException();
      }
   }

   private final class ValueIterator extends Int2IntLinkedOpenHashMap.MapIterator<java.util.function.IntConsumer> implements IntListIterator {
      @Override
      public int previousInt() {
         return Int2IntLinkedOpenHashMap.this.value[this.previousEntry()];
      }

      public ValueIterator() {
      }

      final void acceptOnIndex(java.util.function.IntConsumer action, int index) {
         action.accept(Int2IntLinkedOpenHashMap.this.value[index]);
      }

      @Override
      public int nextInt() {
         return Int2IntLinkedOpenHashMap.this.value[this.nextEntry()];
      }
   }
}
