package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.Pair;
import com.viaversion.viaversion.libs.fastutil.Size64;
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

public class Object2ObjectLinkedOpenHashMap<K, V> extends AbstractObject2ObjectSortedMap<K, V> implements Serializable, Cloneable, Hash {
   private static final long serialVersionUID = 0L;
   private static final boolean ASSERTS = false;
   protected transient K[] key;
   protected transient V[] value;
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
   protected transient Object2ObjectSortedMap.FastSortedEntrySet<K, V> entries;
   protected transient ObjectSortedSet<K> keys;
   protected transient ObjectCollection<V> values;

   public Object2ObjectLinkedOpenHashMap(int expected, float f) {
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
         this.value = (V[])(new Object[this.n + 1]);
         this.link = new long[this.n + 1];
      }
   }

   public Object2ObjectLinkedOpenHashMap(int expected) {
      this(expected, 0.75F);
   }

   public Object2ObjectLinkedOpenHashMap() {
      this(16, 0.75F);
   }

   public Object2ObjectLinkedOpenHashMap(Map<? extends K, ? extends V> m, float f) {
      this(m.size(), f);
      this.putAll(m);
   }

   public Object2ObjectLinkedOpenHashMap(Map<? extends K, ? extends V> m) {
      this(m, 0.75F);
   }

   public Object2ObjectLinkedOpenHashMap(Object2ObjectMap<K, V> m, float f) {
      this(m.size(), f);
      this.putAll(m);
   }

   public Object2ObjectLinkedOpenHashMap(Object2ObjectMap<K, V> m) {
      this(m, 0.75F);
   }

   public Object2ObjectLinkedOpenHashMap(K[] k, V[] v, float f) {
      this(k.length, f);
      if (k.length != v.length) {
         throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
      } else {
         for (int i = 0; i < k.length; i++) {
            this.put(k[i], v[i]);
         }
      }
   }

   public Object2ObjectLinkedOpenHashMap(K[] k, V[] v) {
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

   private V removeEntry(int pos) {
      V oldValue = this.value[pos];
      this.value[pos] = null;
      this.size--;
      this.fixPointers(pos);
      this.shiftKeys(pos);
      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
         this.rehash(this.n / 2);
      }

      return oldValue;
   }

   private V removeNullEntry() {
      this.containsNullKey = false;
      this.key[this.n] = null;
      V oldValue = this.value[this.n];
      this.value[this.n] = null;
      this.size--;
      this.fixPointers(this.n);
      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
         this.rehash(this.n / 2);
      }

      return oldValue;
   }

   @Override
   public void putAll(Map<? extends K, ? extends V> m) {
      if ((double)this.f <= 0.5) {
         this.ensureCapacity(m.size());
      } else {
         this.tryCapacity((long)(this.size() + m.size()));
      }

      super.putAll(m);
   }

   private int find(K k) {
      if (k == null) {
         return this.containsNullKey ? this.n : -(this.n + 1);
      } else {
         K[] key = this.key;
         K curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return -(pos + 1);
         } else if (k.equals(curr)) {
            return pos;
         } else {
            while ((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr)) {
                  return pos;
               }
            }

            return -(pos + 1);
         }
      }
   }

   private void insert(int pos, K k, V v) {
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
   public V put(K k, V v) {
      int pos = this.find(k);
      if (pos < 0) {
         this.insert(-pos - 1, k, v);
         return this.defRetValue;
      } else {
         V oldValue = this.value[pos];
         this.value[pos] = v;
         return oldValue;
      }
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
               this.value[last] = this.value[pos];
               this.fixPointers(pos, last);
               continue label30;
            }
         }

         key[last] = null;
         this.value[last] = null;
         return;
      }
   }

   @Override
   public V remove(Object k) {
      if (k == null) {
         return this.containsNullKey ? this.removeNullEntry() : this.defRetValue;
      } else {
         K[] key = this.key;
         K curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return this.defRetValue;
         } else if (k.equals(curr)) {
            return this.removeEntry(pos);
         } else {
            while ((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr)) {
                  return this.removeEntry(pos);
               }
            }

            return this.defRetValue;
         }
      }
   }

   private V setValue(int pos, V v) {
      V oldValue = this.value[pos];
      this.value[pos] = v;
      return oldValue;
   }

   public V removeFirst() {
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
         V v = this.value[pos];
         if (pos == this.n) {
            this.containsNullKey = false;
            this.key[this.n] = null;
            this.value[this.n] = null;
         } else {
            this.shiftKeys(pos);
         }

         if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
         }

         return v;
      }
   }

   public V removeLast() {
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
         V v = this.value[pos];
         if (pos == this.n) {
            this.containsNullKey = false;
            this.key[this.n] = null;
            this.value[this.n] = null;
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

   public V getAndMoveToFirst(K k) {
      if (k == null) {
         if (this.containsNullKey) {
            this.moveIndexToFirst(this.n);
            return this.value[this.n];
         } else {
            return this.defRetValue;
         }
      } else {
         K[] key = this.key;
         K curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return this.defRetValue;
         } else if (k.equals(curr)) {
            this.moveIndexToFirst(pos);
            return this.value[pos];
         } else {
            while ((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr)) {
                  this.moveIndexToFirst(pos);
                  return this.value[pos];
               }
            }

            return this.defRetValue;
         }
      }
   }

   public V getAndMoveToLast(K k) {
      if (k == null) {
         if (this.containsNullKey) {
            this.moveIndexToLast(this.n);
            return this.value[this.n];
         } else {
            return this.defRetValue;
         }
      } else {
         K[] key = this.key;
         K curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return this.defRetValue;
         } else if (k.equals(curr)) {
            this.moveIndexToLast(pos);
            return this.value[pos];
         } else {
            while ((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr)) {
                  this.moveIndexToLast(pos);
                  return this.value[pos];
               }
            }

            return this.defRetValue;
         }
      }
   }

   public V putAndMoveToFirst(K k, V v) {
      int pos;
      if (k == null) {
         if (this.containsNullKey) {
            this.moveIndexToFirst(this.n);
            return this.setValue(this.n, v);
         }

         this.containsNullKey = true;
         pos = this.n;
      } else {
         K[] key = this.key;
         K curr;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
            if (curr.equals(k)) {
               this.moveIndexToFirst(pos);
               return this.setValue(pos, v);
            }

            while ((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (curr.equals(k)) {
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

   public V putAndMoveToLast(K k, V v) {
      int pos;
      if (k == null) {
         if (this.containsNullKey) {
            this.moveIndexToLast(this.n);
            return this.setValue(this.n, v);
         }

         this.containsNullKey = true;
         pos = this.n;
      } else {
         K[] key = this.key;
         K curr;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
            if (curr.equals(k)) {
               this.moveIndexToLast(pos);
               return this.setValue(pos, v);
            }

            while ((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (curr.equals(k)) {
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
   public V get(Object k) {
      if (k == null) {
         return this.containsNullKey ? this.value[this.n] : this.defRetValue;
      } else {
         K[] key = this.key;
         K curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return this.defRetValue;
         } else if (k.equals(curr)) {
            return this.value[pos];
         } else {
            while ((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr)) {
                  return this.value[pos];
               }
            }

            return this.defRetValue;
         }
      }
   }

   @Override
   public boolean containsKey(Object k) {
      if (k == null) {
         return this.containsNullKey;
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

   @Override
   public boolean containsValue(Object v) {
      V[] value = this.value;
      K[] key = this.key;
      if (this.containsNullKey && Objects.equals(value[this.n], v)) {
         return true;
      } else {
         int i = this.n;

         while (i-- != 0) {
            if (key[i] != null && Objects.equals(value[i], v)) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public V getOrDefault(Object k, V defaultValue) {
      if (k == null) {
         return this.containsNullKey ? this.value[this.n] : defaultValue;
      } else {
         K[] key = this.key;
         K curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return defaultValue;
         } else if (k.equals(curr)) {
            return this.value[pos];
         } else {
            while ((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr)) {
                  return this.value[pos];
               }
            }

            return defaultValue;
         }
      }
   }

   @Override
   public V putIfAbsent(K k, V v) {
      int pos = this.find(k);
      if (pos >= 0) {
         return this.value[pos];
      } else {
         this.insert(-pos - 1, k, v);
         return this.defRetValue;
      }
   }

   @Override
   public boolean remove(Object k, Object v) {
      if (k == null) {
         if (this.containsNullKey && Objects.equals(v, this.value[this.n])) {
            this.removeNullEntry();
            return true;
         } else {
            return false;
         }
      } else {
         K[] key = this.key;
         K curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return false;
         } else if (k.equals(curr) && Objects.equals(v, this.value[pos])) {
            this.removeEntry(pos);
            return true;
         } else {
            while ((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr) && Objects.equals(v, this.value[pos])) {
                  this.removeEntry(pos);
                  return true;
               }
            }

            return false;
         }
      }
   }

   @Override
   public boolean replace(K k, V oldValue, V v) {
      int pos = this.find(k);
      if (pos >= 0 && Objects.equals(oldValue, this.value[pos])) {
         this.value[pos] = v;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public V replace(K k, V v) {
      int pos = this.find(k);
      if (pos < 0) {
         return this.defRetValue;
      } else {
         V oldValue = this.value[pos];
         this.value[pos] = v;
         return oldValue;
      }
   }

   @Override
   public V computeIfAbsent(K key, Object2ObjectFunction<? super K, ? extends V> mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      int pos = this.find(key);
      if (pos >= 0) {
         return this.value[pos];
      } else if (!mappingFunction.containsKey(key)) {
         return this.defRetValue;
      } else {
         V newValue = (V)mappingFunction.get(key);
         this.insert(-pos - 1, key, newValue);
         return newValue;
      }
   }

   @Override
   public V computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int pos = this.find(k);
      if (pos < 0) {
         return this.defRetValue;
      } else if (this.value[pos] == null) {
         return this.defRetValue;
      } else {
         V newValue = (V)remappingFunction.apply(k, this.value[pos]);
         if (newValue == null) {
            if (k == null) {
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
   public V compute(K k, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int pos = this.find(k);
      V newValue = (V)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
      if (newValue == null) {
         if (pos >= 0) {
            if (k == null) {
               this.removeNullEntry();
            } else {
               this.removeEntry(pos);
            }
         }

         return this.defRetValue;
      } else if (pos < 0) {
         this.insert(-pos - 1, k, newValue);
         return newValue;
      } else {
         return this.value[pos] = newValue;
      }
   }

   @Override
   public V merge(K k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      Objects.requireNonNull(v);
      int pos = this.find(k);
      if (pos >= 0 && this.value[pos] != null) {
         V newValue = (V)remappingFunction.apply(this.value[pos], v);
         if (newValue == null) {
            if (k == null) {
               this.removeNullEntry();
            } else {
               this.removeEntry(pos);
            }

            return this.defRetValue;
         } else {
            return this.value[pos] = newValue;
         }
      } else {
         if (pos < 0) {
            this.insert(-pos - 1, k, v);
         } else {
            this.value[pos] = v;
         }

         return v;
      }
   }

   @Override
   public void clear() {
      if (this.size != 0) {
         this.size = 0;
         this.containsNullKey = false;
         Arrays.fill(this.key, null);
         Arrays.fill(this.value, null);
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
   public K firstKey() {
      if (this.size == 0) {
         throw new NoSuchElementException();
      } else {
         return this.key[this.first];
      }
   }

   @Override
   public K lastKey() {
      if (this.size == 0) {
         throw new NoSuchElementException();
      } else {
         return this.key[this.last];
      }
   }

   @Override
   public Object2ObjectSortedMap<K, V> tailMap(K from) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object2ObjectSortedMap<K, V> headMap(K to) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object2ObjectSortedMap<K, V> subMap(K from, K to) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Comparator<? super K> comparator() {
      return null;
   }

   public Object2ObjectSortedMap.FastSortedEntrySet<K, V> object2ObjectEntrySet() {
      if (this.entries == null) {
         this.entries = new Object2ObjectLinkedOpenHashMap.MapEntrySet();
      }

      return this.entries;
   }

   @Override
   public ObjectSortedSet<K> keySet() {
      if (this.keys == null) {
         this.keys = new Object2ObjectLinkedOpenHashMap.KeySet();
      }

      return this.keys;
   }

   @Override
   public ObjectCollection<V> values() {
      if (this.values == null) {
         this.values = new AbstractObjectCollection<V>() {
            private static final int SPLITERATOR_CHARACTERISTICS = 80;

            @Override
            public ObjectIterator<V> iterator() {
               return Object2ObjectLinkedOpenHashMap.this.new ValueIterator();
            }

            @Override
            public ObjectSpliterator<V> spliterator() {
               return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(Object2ObjectLinkedOpenHashMap.this), 80);
            }

            @Override
            public void forEach(Consumer<? super V> consumer) {
               int i = Object2ObjectLinkedOpenHashMap.this.size;
               int next = Object2ObjectLinkedOpenHashMap.this.first;

               while (i-- != 0) {
                  int curr = next;
                  next = (int)Object2ObjectLinkedOpenHashMap.this.link[next];
                  consumer.accept(Object2ObjectLinkedOpenHashMap.this.value[curr]);
               }
            }

            @Override
            public int size() {
               return Object2ObjectLinkedOpenHashMap.this.size;
            }

            @Override
            public boolean contains(Object v) {
               return Object2ObjectLinkedOpenHashMap.this.containsValue(v);
            }

            @Override
            public void clear() {
               Object2ObjectLinkedOpenHashMap.this.clear();
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
      K[] key = this.key;
      V[] value = this.value;
      int mask = newN - 1;
      K[] newKey = (K[])(new Object[newN + 1]);
      V[] newValue = (V[])(new Object[newN + 1]);
      int i = this.first;
      int prev = -1;
      int newPrev = -1;
      long[] link = this.link;
      long[] newLink = new long[newN + 1];
      this.first = -1;
      int j = this.size;

      while (j-- != 0) {
         int pos;
         if (key[i] == null) {
            pos = newN;
         } else {
            pos = HashCommon.mix(key[i].hashCode()) & mask;

            while (newKey[pos] != null) {
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

   public Object2ObjectLinkedOpenHashMap<K, V> clone() {
      Object2ObjectLinkedOpenHashMap<K, V> c;
      try {
         c = (Object2ObjectLinkedOpenHashMap<K, V>)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      c.keys = null;
      c.values = null;
      c.entries = null;
      c.containsNullKey = this.containsNullKey;
      c.key = (K[])((Object[])this.key.clone());
      c.value = (V[])((Object[])this.value.clone());
      c.link = (long[])this.link.clone();
      return c;
   }

   @Override
   public int hashCode() {
      int h = 0;
      int j = this.realSize();
      int i = 0;

      for (int t = 0; j-- != 0; i++) {
         while (this.key[i] == null) {
            i++;
         }

         if (this != this.key[i]) {
            t = this.key[i].hashCode();
         }

         if (this != this.value[i]) {
            t ^= this.value[i] == null ? 0 : this.value[i].hashCode();
         }

         h += t;
      }

      if (this.containsNullKey) {
         h += this.value[this.n] == null ? 0 : this.value[this.n].hashCode();
      }

      return h;
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      K[] key = this.key;
      V[] value = this.value;
      Object2ObjectLinkedOpenHashMap<K, V>.EntryIterator i = new Object2ObjectLinkedOpenHashMap.EntryIterator();
      s.defaultWriteObject();
      int j = this.size;

      while (j-- != 0) {
         int e = i.nextEntry();
         s.writeObject(key[e]);
         s.writeObject(value[e]);
      }
   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.n = HashCommon.arraySize(this.size, this.f);
      this.maxFill = HashCommon.maxFill(this.n, this.f);
      this.mask = this.n - 1;
      K[] key = this.key = (K[])(new Object[this.n + 1]);
      V[] value = this.value = (V[])(new Object[this.n + 1]);
      long[] link = this.link = new long[this.n + 1];
      int prev = -1;
      this.first = this.last = -1;
      int i = this.size;

      while (i-- != 0) {
         K k = (K)s.readObject();
         V v = (V)s.readObject();
         int pos;
         if (k == null) {
            pos = this.n;
            this.containsNullKey = true;
         } else {
            pos = HashCommon.mix(k.hashCode()) & this.mask;

            while (key[pos] != null) {
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
      extends Object2ObjectLinkedOpenHashMap<K, V>.MapIterator<Consumer<? super Object2ObjectMap.Entry<K, V>>>
      implements ObjectListIterator<Object2ObjectMap.Entry<K, V>> {
      private Object2ObjectLinkedOpenHashMap<K, V>.MapEntry entry;

      public EntryIterator() {
      }

      public EntryIterator(K from) {
         super(from);
      }

      final void acceptOnIndex(Consumer<? super Object2ObjectMap.Entry<K, V>> action, int index) {
         action.accept(Object2ObjectLinkedOpenHashMap.this.new MapEntry(index));
      }

      public Object2ObjectLinkedOpenHashMap<K, V>.MapEntry next() {
         return this.entry = Object2ObjectLinkedOpenHashMap.this.new MapEntry(this.nextEntry());
      }

      public Object2ObjectLinkedOpenHashMap<K, V>.MapEntry previous() {
         return this.entry = Object2ObjectLinkedOpenHashMap.this.new MapEntry(this.previousEntry());
      }

      @Override
      public void remove() {
         super.remove();
         this.entry.index = -1;
      }
   }

   private final class FastEntryIterator
      extends Object2ObjectLinkedOpenHashMap<K, V>.MapIterator<Consumer<? super Object2ObjectMap.Entry<K, V>>>
      implements ObjectListIterator<Object2ObjectMap.Entry<K, V>> {
      final Object2ObjectLinkedOpenHashMap<K, V>.MapEntry entry;

      public FastEntryIterator() {
         this.entry = Object2ObjectLinkedOpenHashMap.this.new MapEntry();
      }

      public FastEntryIterator(K from) {
         super(from);
         this.entry = Object2ObjectLinkedOpenHashMap.this.new MapEntry();
      }

      final void acceptOnIndex(Consumer<? super Object2ObjectMap.Entry<K, V>> action, int index) {
         this.entry.index = index;
         action.accept(this.entry);
      }

      public Object2ObjectLinkedOpenHashMap<K, V>.MapEntry next() {
         this.entry.index = this.nextEntry();
         return this.entry;
      }

      public Object2ObjectLinkedOpenHashMap<K, V>.MapEntry previous() {
         this.entry.index = this.previousEntry();
         return this.entry;
      }
   }

   private final class KeyIterator extends Object2ObjectLinkedOpenHashMap<K, V>.MapIterator<Consumer<? super K>> implements ObjectListIterator<K> {
      public KeyIterator(K k) {
         super(k);
      }

      @Override
      public K previous() {
         return Object2ObjectLinkedOpenHashMap.this.key[this.previousEntry()];
      }

      public KeyIterator() {
      }

      final void acceptOnIndex(Consumer<? super K> action, int index) {
         action.accept(Object2ObjectLinkedOpenHashMap.this.key[index]);
      }

      @Override
      public K next() {
         return Object2ObjectLinkedOpenHashMap.this.key[this.nextEntry()];
      }
   }

   private final class KeySet extends AbstractObjectSortedSet<K> {
      private static final int SPLITERATOR_CHARACTERISTICS = 81;

      private KeySet() {
      }

      public ObjectListIterator<K> iterator(K from) {
         return Object2ObjectLinkedOpenHashMap.this.new KeyIterator(from);
      }

      public ObjectListIterator<K> iterator() {
         return Object2ObjectLinkedOpenHashMap.this.new KeyIterator();
      }

      @Override
      public ObjectSpliterator<K> spliterator() {
         return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(Object2ObjectLinkedOpenHashMap.this), 81);
      }

      @Override
      public void forEach(Consumer<? super K> consumer) {
         int i = Object2ObjectLinkedOpenHashMap.this.size;
         int next = Object2ObjectLinkedOpenHashMap.this.first;

         while (i-- != 0) {
            int curr = next;
            next = (int)Object2ObjectLinkedOpenHashMap.this.link[next];
            consumer.accept(Object2ObjectLinkedOpenHashMap.this.key[curr]);
         }
      }

      @Override
      public int size() {
         return Object2ObjectLinkedOpenHashMap.this.size;
      }

      @Override
      public boolean contains(Object k) {
         return Object2ObjectLinkedOpenHashMap.this.containsKey(k);
      }

      @Override
      public boolean remove(Object k) {
         int oldSize = Object2ObjectLinkedOpenHashMap.this.size;
         Object2ObjectLinkedOpenHashMap.this.remove(k);
         return Object2ObjectLinkedOpenHashMap.this.size != oldSize;
      }

      @Override
      public void clear() {
         Object2ObjectLinkedOpenHashMap.this.clear();
      }

      @Override
      public K first() {
         if (Object2ObjectLinkedOpenHashMap.this.size == 0) {
            throw new NoSuchElementException();
         } else {
            return Object2ObjectLinkedOpenHashMap.this.key[Object2ObjectLinkedOpenHashMap.this.first];
         }
      }

      @Override
      public K last() {
         if (Object2ObjectLinkedOpenHashMap.this.size == 0) {
            throw new NoSuchElementException();
         } else {
            return Object2ObjectLinkedOpenHashMap.this.key[Object2ObjectLinkedOpenHashMap.this.last];
         }
      }

      @Override
      public Comparator<? super K> comparator() {
         return null;
      }

      @Override
      public ObjectSortedSet<K> tailSet(K from) {
         throw new UnsupportedOperationException();
      }

      @Override
      public ObjectSortedSet<K> headSet(K to) {
         throw new UnsupportedOperationException();
      }

      @Override
      public ObjectSortedSet<K> subSet(K from, K to) {
         throw new UnsupportedOperationException();
      }
   }

   final class MapEntry implements Object2ObjectMap.Entry<K, V>, Entry<K, V>, Pair<K, V> {
      int index;

      MapEntry(int index) {
         this.index = index;
      }

      MapEntry() {
      }

      @Override
      public K getKey() {
         return Object2ObjectLinkedOpenHashMap.this.key[this.index];
      }

      @Override
      public K left() {
         return Object2ObjectLinkedOpenHashMap.this.key[this.index];
      }

      @Override
      public V getValue() {
         return Object2ObjectLinkedOpenHashMap.this.value[this.index];
      }

      @Override
      public V right() {
         return Object2ObjectLinkedOpenHashMap.this.value[this.index];
      }

      @Override
      public V setValue(V v) {
         V oldValue = Object2ObjectLinkedOpenHashMap.this.value[this.index];
         Object2ObjectLinkedOpenHashMap.this.value[this.index] = v;
         return oldValue;
      }

      @Override
      public Pair<K, V> right(V v) {
         Object2ObjectLinkedOpenHashMap.this.value[this.index] = v;
         return this;
      }

      @Override
      public boolean equals(Object o) {
         if (!(o instanceof Entry)) {
            return false;
         } else {
            Entry<K, V> e = (Entry<K, V>)o;
            return Objects.equals(Object2ObjectLinkedOpenHashMap.this.key[this.index], e.getKey())
               && Objects.equals(Object2ObjectLinkedOpenHashMap.this.value[this.index], e.getValue());
         }
      }

      @Override
      public int hashCode() {
         return (Object2ObjectLinkedOpenHashMap.this.key[this.index] == null ? 0 : Object2ObjectLinkedOpenHashMap.this.key[this.index].hashCode())
            ^ (Object2ObjectLinkedOpenHashMap.this.value[this.index] == null ? 0 : Object2ObjectLinkedOpenHashMap.this.value[this.index].hashCode());
      }

      @Override
      public String toString() {
         return Object2ObjectLinkedOpenHashMap.this.key[this.index] + "=>" + Object2ObjectLinkedOpenHashMap.this.value[this.index];
      }
   }

   private final class MapEntrySet extends AbstractObjectSortedSet<Object2ObjectMap.Entry<K, V>> implements Object2ObjectSortedMap.FastSortedEntrySet<K, V> {
      private static final int SPLITERATOR_CHARACTERISTICS = 81;

      private MapEntrySet() {
      }

      @Override
      public ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> iterator() {
         return Object2ObjectLinkedOpenHashMap.this.new EntryIterator();
      }

      @Override
      public ObjectSpliterator<Object2ObjectMap.Entry<K, V>> spliterator() {
         return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(Object2ObjectLinkedOpenHashMap.this), 81);
      }

      @Override
      public Comparator<? super Object2ObjectMap.Entry<K, V>> comparator() {
         return null;
      }

      public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> subSet(Object2ObjectMap.Entry<K, V> fromElement, Object2ObjectMap.Entry<K, V> toElement) {
         throw new UnsupportedOperationException();
      }

      public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> headSet(Object2ObjectMap.Entry<K, V> toElement) {
         throw new UnsupportedOperationException();
      }

      public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> tailSet(Object2ObjectMap.Entry<K, V> fromElement) {
         throw new UnsupportedOperationException();
      }

      public Object2ObjectMap.Entry<K, V> first() {
         if (Object2ObjectLinkedOpenHashMap.this.size == 0) {
            throw new NoSuchElementException();
         } else {
            return Object2ObjectLinkedOpenHashMap.this.new MapEntry(Object2ObjectLinkedOpenHashMap.this.first);
         }
      }

      public Object2ObjectMap.Entry<K, V> last() {
         if (Object2ObjectLinkedOpenHashMap.this.size == 0) {
            throw new NoSuchElementException();
         } else {
            return Object2ObjectLinkedOpenHashMap.this.new MapEntry(Object2ObjectLinkedOpenHashMap.this.last);
         }
      }

      @Override
      public boolean contains(Object o) {
         if (!(o instanceof Entry)) {
            return false;
         } else {
            Entry<?, ?> e = (Entry<?, ?>)o;
            K k = (K)e.getKey();
            V v = (V)e.getValue();
            if (k == null) {
               return Object2ObjectLinkedOpenHashMap.this.containsNullKey
                  && Objects.equals(Object2ObjectLinkedOpenHashMap.this.value[Object2ObjectLinkedOpenHashMap.this.n], v);
            } else {
               K[] key = Object2ObjectLinkedOpenHashMap.this.key;
               K curr;
               int pos;
               if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ObjectLinkedOpenHashMap.this.mask]) == null) {
                  return false;
               } else if (k.equals(curr)) {
                  return Objects.equals(Object2ObjectLinkedOpenHashMap.this.value[pos], v);
               } else {
                  while ((curr = key[pos = pos + 1 & Object2ObjectLinkedOpenHashMap.this.mask]) != null) {
                     if (k.equals(curr)) {
                        return Objects.equals(Object2ObjectLinkedOpenHashMap.this.value[pos], v);
                     }
                  }

                  return false;
               }
            }
         }
      }

      @Override
      public boolean remove(Object o) {
         if (!(o instanceof Entry)) {
            return false;
         } else {
            Entry<?, ?> e = (Entry<?, ?>)o;
            K k = (K)e.getKey();
            V v = (V)e.getValue();
            if (k == null) {
               if (Object2ObjectLinkedOpenHashMap.this.containsNullKey
                  && Objects.equals(Object2ObjectLinkedOpenHashMap.this.value[Object2ObjectLinkedOpenHashMap.this.n], v)) {
                  Object2ObjectLinkedOpenHashMap.this.removeNullEntry();
                  return true;
               } else {
                  return false;
               }
            } else {
               K[] key = Object2ObjectLinkedOpenHashMap.this.key;
               K curr;
               int pos;
               if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ObjectLinkedOpenHashMap.this.mask]) == null) {
                  return false;
               } else if (curr.equals(k)) {
                  if (Objects.equals(Object2ObjectLinkedOpenHashMap.this.value[pos], v)) {
                     Object2ObjectLinkedOpenHashMap.this.removeEntry(pos);
                     return true;
                  } else {
                     return false;
                  }
               } else {
                  while ((curr = key[pos = pos + 1 & Object2ObjectLinkedOpenHashMap.this.mask]) != null) {
                     if (curr.equals(k) && Objects.equals(Object2ObjectLinkedOpenHashMap.this.value[pos], v)) {
                        Object2ObjectLinkedOpenHashMap.this.removeEntry(pos);
                        return true;
                     }
                  }

                  return false;
               }
            }
         }
      }

      @Override
      public int size() {
         return Object2ObjectLinkedOpenHashMap.this.size;
      }

      @Override
      public void clear() {
         Object2ObjectLinkedOpenHashMap.this.clear();
      }

      public ObjectListIterator<Object2ObjectMap.Entry<K, V>> iterator(Object2ObjectMap.Entry<K, V> from) {
         return Object2ObjectLinkedOpenHashMap.this.new EntryIterator(from.getKey());
      }

      public ObjectListIterator<Object2ObjectMap.Entry<K, V>> fastIterator() {
         return Object2ObjectLinkedOpenHashMap.this.new FastEntryIterator();
      }

      public ObjectListIterator<Object2ObjectMap.Entry<K, V>> fastIterator(Object2ObjectMap.Entry<K, V> from) {
         return Object2ObjectLinkedOpenHashMap.this.new FastEntryIterator(from.getKey());
      }

      @Override
      public void forEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
         int i = Object2ObjectLinkedOpenHashMap.this.size;
         int next = Object2ObjectLinkedOpenHashMap.this.first;

         while (i-- != 0) {
            int curr = next;
            next = (int)Object2ObjectLinkedOpenHashMap.this.link[next];
            consumer.accept(Object2ObjectLinkedOpenHashMap.this.new MapEntry(curr));
         }
      }

      @Override
      public void fastForEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
         Object2ObjectLinkedOpenHashMap<K, V>.MapEntry entry = Object2ObjectLinkedOpenHashMap.this.new MapEntry();
         int i = Object2ObjectLinkedOpenHashMap.this.size;
         int next = Object2ObjectLinkedOpenHashMap.this.first;

         while (i-- != 0) {
            entry.index = next;
            next = (int)Object2ObjectLinkedOpenHashMap.this.link[next];
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
         this.next = Object2ObjectLinkedOpenHashMap.this.first;
         this.index = 0;
      }

      private MapIterator(K from) {
         if (from == null) {
            if (Object2ObjectLinkedOpenHashMap.this.containsNullKey) {
               this.next = (int)Object2ObjectLinkedOpenHashMap.this.link[Object2ObjectLinkedOpenHashMap.this.n];
               this.prev = Object2ObjectLinkedOpenHashMap.this.n;
            } else {
               throw new NoSuchElementException("The key " + from + " does not belong to this map.");
            }
         } else if (Objects.equals(Object2ObjectLinkedOpenHashMap.this.key[Object2ObjectLinkedOpenHashMap.this.last], from)) {
            this.prev = Object2ObjectLinkedOpenHashMap.this.last;
            this.index = Object2ObjectLinkedOpenHashMap.this.size;
         } else {
            for (int pos = HashCommon.mix(from.hashCode()) & Object2ObjectLinkedOpenHashMap.this.mask;
               Object2ObjectLinkedOpenHashMap.this.key[pos] != null;
               pos = pos + 1 & Object2ObjectLinkedOpenHashMap.this.mask
            ) {
               if (Object2ObjectLinkedOpenHashMap.this.key[pos].equals(from)) {
                  this.next = (int)Object2ObjectLinkedOpenHashMap.this.link[pos];
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
               this.index = Object2ObjectLinkedOpenHashMap.this.size;
            } else {
               int pos = Object2ObjectLinkedOpenHashMap.this.first;

               for (this.index = 1; pos != this.prev; this.index++) {
                  pos = (int)Object2ObjectLinkedOpenHashMap.this.link[pos];
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
            this.next = (int)Object2ObjectLinkedOpenHashMap.this.link[this.curr];
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
            this.prev = (int)(Object2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32);
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
            this.next = (int)Object2ObjectLinkedOpenHashMap.this.link[this.curr];
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
               this.prev = (int)(Object2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32);
            } else {
               this.next = (int)Object2ObjectLinkedOpenHashMap.this.link[this.curr];
            }

            Object2ObjectLinkedOpenHashMap.this.size--;
            if (this.prev == -1) {
               Object2ObjectLinkedOpenHashMap.this.first = this.next;
            } else {
               Object2ObjectLinkedOpenHashMap.this.link[this.prev] = Object2ObjectLinkedOpenHashMap.this.link[this.prev]
                  ^ (Object2ObjectLinkedOpenHashMap.this.link[this.prev] ^ (long)this.next & 4294967295L) & 4294967295L;
            }

            if (this.next == -1) {
               Object2ObjectLinkedOpenHashMap.this.last = this.prev;
            } else {
               Object2ObjectLinkedOpenHashMap.this.link[this.next] = Object2ObjectLinkedOpenHashMap.this.link[this.next]
                  ^ (Object2ObjectLinkedOpenHashMap.this.link[this.next] ^ ((long)this.prev & 4294967295L) << 32) & -4294967296L;
            }

            int pos = this.curr;
            this.curr = -1;
            if (pos == Object2ObjectLinkedOpenHashMap.this.n) {
               Object2ObjectLinkedOpenHashMap.this.containsNullKey = false;
               Object2ObjectLinkedOpenHashMap.this.key[Object2ObjectLinkedOpenHashMap.this.n] = null;
               Object2ObjectLinkedOpenHashMap.this.value[Object2ObjectLinkedOpenHashMap.this.n] = null;
            } else {
               K[] key = Object2ObjectLinkedOpenHashMap.this.key;

               label61:
               while (true) {
                  int last = pos;

                  K curr;
                  for (pos = pos + 1 & Object2ObjectLinkedOpenHashMap.this.mask;
                     (curr = key[pos]) != null;
                     pos = pos + 1 & Object2ObjectLinkedOpenHashMap.this.mask
                  ) {
                     int slot = HashCommon.mix(curr.hashCode()) & Object2ObjectLinkedOpenHashMap.this.mask;
                     if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
                        key[last] = curr;
                        Object2ObjectLinkedOpenHashMap.this.value[last] = Object2ObjectLinkedOpenHashMap.this.value[pos];
                        if (this.next == pos) {
                           this.next = last;
                        }

                        if (this.prev == pos) {
                           this.prev = last;
                        }

                        Object2ObjectLinkedOpenHashMap.this.fixPointers(pos, last);
                        continue label61;
                     }
                  }

                  key[last] = null;
                  Object2ObjectLinkedOpenHashMap.this.value[last] = null;
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

      public void set(Object2ObjectMap.Entry<K, V> ok) {
         throw new UnsupportedOperationException();
      }

      public void add(Object2ObjectMap.Entry<K, V> ok) {
         throw new UnsupportedOperationException();
      }
   }

   private final class ValueIterator extends Object2ObjectLinkedOpenHashMap<K, V>.MapIterator<Consumer<? super V>> implements ObjectListIterator<V> {
      @Override
      public V previous() {
         return Object2ObjectLinkedOpenHashMap.this.value[this.previousEntry()];
      }

      public ValueIterator() {
      }

      final void acceptOnIndex(Consumer<? super V> action, int index) {
         action.accept(Object2ObjectLinkedOpenHashMap.this.value[index]);
      }

      @Override
      public V next() {
         return Object2ObjectLinkedOpenHashMap.this.value[this.nextEntry()];
      }
   }
}
