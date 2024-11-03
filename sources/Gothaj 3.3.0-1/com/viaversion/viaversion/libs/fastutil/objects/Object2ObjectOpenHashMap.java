package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.Pair;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class Object2ObjectOpenHashMap<K, V> extends AbstractObject2ObjectMap<K, V> implements Serializable, Cloneable, Hash {
   private static final long serialVersionUID = 0L;
   private static final boolean ASSERTS = false;
   protected transient K[] key;
   protected transient V[] value;
   protected transient int mask;
   protected transient boolean containsNullKey;
   protected transient int n;
   protected transient int maxFill;
   protected final transient int minN;
   protected int size;
   protected final float f;
   protected transient Object2ObjectMap.FastEntrySet<K, V> entries;
   protected transient ObjectSet<K> keys;
   protected transient ObjectCollection<V> values;

   public Object2ObjectOpenHashMap(int expected, float f) {
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
      }
   }

   public Object2ObjectOpenHashMap(int expected) {
      this(expected, 0.75F);
   }

   public Object2ObjectOpenHashMap() {
      this(16, 0.75F);
   }

   public Object2ObjectOpenHashMap(Map<? extends K, ? extends V> m, float f) {
      this(m.size(), f);
      this.putAll(m);
   }

   public Object2ObjectOpenHashMap(Map<? extends K, ? extends V> m) {
      this(m, 0.75F);
   }

   public Object2ObjectOpenHashMap(Object2ObjectMap<K, V> m, float f) {
      this(m.size(), f);
      this.putAll(m);
   }

   public Object2ObjectOpenHashMap(Object2ObjectMap<K, V> m) {
      this(m, 0.75F);
   }

   public Object2ObjectOpenHashMap(K[] k, V[] v, float f) {
      this(k.length, f);
      if (k.length != v.length) {
         throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
      } else {
         for (int i = 0; i < k.length; i++) {
            this.put(k[i], v[i]);
         }
      }
   }

   public Object2ObjectOpenHashMap(K[] k, V[] v) {
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

   public Object2ObjectMap.FastEntrySet<K, V> object2ObjectEntrySet() {
      if (this.entries == null) {
         this.entries = new Object2ObjectOpenHashMap.MapEntrySet();
      }

      return this.entries;
   }

   @Override
   public ObjectSet<K> keySet() {
      if (this.keys == null) {
         this.keys = new Object2ObjectOpenHashMap.KeySet();
      }

      return this.keys;
   }

   @Override
   public ObjectCollection<V> values() {
      if (this.values == null) {
         this.values = new AbstractObjectCollection<V>() {
            @Override
            public ObjectIterator<V> iterator() {
               return Object2ObjectOpenHashMap.this.new ValueIterator();
            }

            @Override
            public ObjectSpliterator<V> spliterator() {
               return Object2ObjectOpenHashMap.this.new ValueSpliterator();
            }

            @Override
            public void forEach(Consumer<? super V> consumer) {
               if (Object2ObjectOpenHashMap.this.containsNullKey) {
                  consumer.accept(Object2ObjectOpenHashMap.this.value[Object2ObjectOpenHashMap.this.n]);
               }

               int pos = Object2ObjectOpenHashMap.this.n;

               while (pos-- != 0) {
                  if (Object2ObjectOpenHashMap.this.key[pos] != null) {
                     consumer.accept(Object2ObjectOpenHashMap.this.value[pos]);
                  }
               }
            }

            @Override
            public int size() {
               return Object2ObjectOpenHashMap.this.size;
            }

            @Override
            public boolean contains(Object v) {
               return Object2ObjectOpenHashMap.this.containsValue(v);
            }

            @Override
            public void clear() {
               Object2ObjectOpenHashMap.this.clear();
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
         newValue[pos] = value[i];
      }

      newValue[newN] = value[this.n];
      this.n = newN;
      this.mask = mask;
      this.maxFill = HashCommon.maxFill(this.n, this.f);
      this.key = newKey;
      this.value = newValue;
   }

   public Object2ObjectOpenHashMap<K, V> clone() {
      Object2ObjectOpenHashMap<K, V> c;
      try {
         c = (Object2ObjectOpenHashMap<K, V>)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      c.keys = null;
      c.values = null;
      c.entries = null;
      c.containsNullKey = this.containsNullKey;
      c.key = (K[])((Object[])this.key.clone());
      c.value = (V[])((Object[])this.value.clone());
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
      Object2ObjectOpenHashMap<K, V>.EntryIterator i = new Object2ObjectOpenHashMap.EntryIterator();
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
      }
   }

   private void checkTable() {
   }

   private final class EntryIterator
      extends Object2ObjectOpenHashMap<K, V>.MapIterator<Consumer<? super Object2ObjectMap.Entry<K, V>>>
      implements ObjectIterator<Object2ObjectMap.Entry<K, V>> {
      private Object2ObjectOpenHashMap<K, V>.MapEntry entry;

      private EntryIterator() {
      }

      public Object2ObjectOpenHashMap<K, V>.MapEntry next() {
         return this.entry = Object2ObjectOpenHashMap.this.new MapEntry(this.nextEntry());
      }

      final void acceptOnIndex(Consumer<? super Object2ObjectMap.Entry<K, V>> action, int index) {
         action.accept(this.entry = Object2ObjectOpenHashMap.this.new MapEntry(index));
      }

      @Override
      public void remove() {
         super.remove();
         this.entry.index = -1;
      }
   }

   private final class EntrySpliterator
      extends Object2ObjectOpenHashMap<K, V>.MapSpliterator<Consumer<? super Object2ObjectMap.Entry<K, V>>, Object2ObjectOpenHashMap<K, V>.EntrySpliterator>
      implements ObjectSpliterator<Object2ObjectMap.Entry<K, V>> {
      private static final int POST_SPLIT_CHARACTERISTICS = 1;

      EntrySpliterator() {
      }

      EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
         super(pos, max, mustReturnNull, hasSplit);
      }

      @Override
      public int characteristics() {
         return this.hasSplit ? 1 : 65;
      }

      final void acceptOnIndex(Consumer<? super Object2ObjectMap.Entry<K, V>> action, int index) {
         action.accept(Object2ObjectOpenHashMap.this.new MapEntry(index));
      }

      final Object2ObjectOpenHashMap<K, V>.EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
         return Object2ObjectOpenHashMap.this.new EntrySpliterator(pos, max, mustReturnNull, true);
      }
   }

   private final class FastEntryIterator
      extends Object2ObjectOpenHashMap<K, V>.MapIterator<Consumer<? super Object2ObjectMap.Entry<K, V>>>
      implements ObjectIterator<Object2ObjectMap.Entry<K, V>> {
      private final Object2ObjectOpenHashMap<K, V>.MapEntry entry = Object2ObjectOpenHashMap.this.new MapEntry();

      private FastEntryIterator() {
      }

      public Object2ObjectOpenHashMap<K, V>.MapEntry next() {
         this.entry.index = this.nextEntry();
         return this.entry;
      }

      final void acceptOnIndex(Consumer<? super Object2ObjectMap.Entry<K, V>> action, int index) {
         this.entry.index = index;
         action.accept(this.entry);
      }
   }

   private final class KeyIterator extends Object2ObjectOpenHashMap<K, V>.MapIterator<Consumer<? super K>> implements ObjectIterator<K> {
      public KeyIterator() {
      }

      final void acceptOnIndex(Consumer<? super K> action, int index) {
         action.accept(Object2ObjectOpenHashMap.this.key[index]);
      }

      @Override
      public K next() {
         return Object2ObjectOpenHashMap.this.key[this.nextEntry()];
      }
   }

   private final class KeySet extends AbstractObjectSet<K> {
      private KeySet() {
      }

      @Override
      public ObjectIterator<K> iterator() {
         return Object2ObjectOpenHashMap.this.new KeyIterator();
      }

      @Override
      public ObjectSpliterator<K> spliterator() {
         return Object2ObjectOpenHashMap.this.new KeySpliterator();
      }

      @Override
      public void forEach(Consumer<? super K> consumer) {
         if (Object2ObjectOpenHashMap.this.containsNullKey) {
            consumer.accept(Object2ObjectOpenHashMap.this.key[Object2ObjectOpenHashMap.this.n]);
         }

         int pos = Object2ObjectOpenHashMap.this.n;

         while (pos-- != 0) {
            K k = Object2ObjectOpenHashMap.this.key[pos];
            if (k != null) {
               consumer.accept(k);
            }
         }
      }

      @Override
      public int size() {
         return Object2ObjectOpenHashMap.this.size;
      }

      @Override
      public boolean contains(Object k) {
         return Object2ObjectOpenHashMap.this.containsKey(k);
      }

      @Override
      public boolean remove(Object k) {
         int oldSize = Object2ObjectOpenHashMap.this.size;
         Object2ObjectOpenHashMap.this.remove(k);
         return Object2ObjectOpenHashMap.this.size != oldSize;
      }

      @Override
      public void clear() {
         Object2ObjectOpenHashMap.this.clear();
      }
   }

   private final class KeySpliterator
      extends Object2ObjectOpenHashMap<K, V>.MapSpliterator<Consumer<? super K>, Object2ObjectOpenHashMap<K, V>.KeySpliterator>
      implements ObjectSpliterator<K> {
      private static final int POST_SPLIT_CHARACTERISTICS = 1;

      KeySpliterator() {
      }

      KeySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
         super(pos, max, mustReturnNull, hasSplit);
      }

      @Override
      public int characteristics() {
         return this.hasSplit ? 1 : 65;
      }

      final void acceptOnIndex(Consumer<? super K> action, int index) {
         action.accept(Object2ObjectOpenHashMap.this.key[index]);
      }

      final Object2ObjectOpenHashMap<K, V>.KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
         return Object2ObjectOpenHashMap.this.new KeySpliterator(pos, max, mustReturnNull, true);
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
         return Object2ObjectOpenHashMap.this.key[this.index];
      }

      @Override
      public K left() {
         return Object2ObjectOpenHashMap.this.key[this.index];
      }

      @Override
      public V getValue() {
         return Object2ObjectOpenHashMap.this.value[this.index];
      }

      @Override
      public V right() {
         return Object2ObjectOpenHashMap.this.value[this.index];
      }

      @Override
      public V setValue(V v) {
         V oldValue = Object2ObjectOpenHashMap.this.value[this.index];
         Object2ObjectOpenHashMap.this.value[this.index] = v;
         return oldValue;
      }

      @Override
      public Pair<K, V> right(V v) {
         Object2ObjectOpenHashMap.this.value[this.index] = v;
         return this;
      }

      @Override
      public boolean equals(Object o) {
         if (!(o instanceof Entry)) {
            return false;
         } else {
            Entry<K, V> e = (Entry<K, V>)o;
            return Objects.equals(Object2ObjectOpenHashMap.this.key[this.index], e.getKey())
               && Objects.equals(Object2ObjectOpenHashMap.this.value[this.index], e.getValue());
         }
      }

      @Override
      public int hashCode() {
         return (Object2ObjectOpenHashMap.this.key[this.index] == null ? 0 : Object2ObjectOpenHashMap.this.key[this.index].hashCode())
            ^ (Object2ObjectOpenHashMap.this.value[this.index] == null ? 0 : Object2ObjectOpenHashMap.this.value[this.index].hashCode());
      }

      @Override
      public String toString() {
         return Object2ObjectOpenHashMap.this.key[this.index] + "=>" + Object2ObjectOpenHashMap.this.value[this.index];
      }
   }

   private final class MapEntrySet extends AbstractObjectSet<Object2ObjectMap.Entry<K, V>> implements Object2ObjectMap.FastEntrySet<K, V> {
      private MapEntrySet() {
      }

      @Override
      public ObjectIterator<Object2ObjectMap.Entry<K, V>> iterator() {
         return Object2ObjectOpenHashMap.this.new EntryIterator();
      }

      @Override
      public ObjectIterator<Object2ObjectMap.Entry<K, V>> fastIterator() {
         return Object2ObjectOpenHashMap.this.new FastEntryIterator();
      }

      @Override
      public ObjectSpliterator<Object2ObjectMap.Entry<K, V>> spliterator() {
         return Object2ObjectOpenHashMap.this.new EntrySpliterator();
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
               return Object2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Object2ObjectOpenHashMap.this.value[Object2ObjectOpenHashMap.this.n], v);
            } else {
               K[] key = Object2ObjectOpenHashMap.this.key;
               K curr;
               int pos;
               if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ObjectOpenHashMap.this.mask]) == null) {
                  return false;
               } else if (k.equals(curr)) {
                  return Objects.equals(Object2ObjectOpenHashMap.this.value[pos], v);
               } else {
                  while ((curr = key[pos = pos + 1 & Object2ObjectOpenHashMap.this.mask]) != null) {
                     if (k.equals(curr)) {
                        return Objects.equals(Object2ObjectOpenHashMap.this.value[pos], v);
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
               if (Object2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Object2ObjectOpenHashMap.this.value[Object2ObjectOpenHashMap.this.n], v)) {
                  Object2ObjectOpenHashMap.this.removeNullEntry();
                  return true;
               } else {
                  return false;
               }
            } else {
               K[] key = Object2ObjectOpenHashMap.this.key;
               K curr;
               int pos;
               if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ObjectOpenHashMap.this.mask]) == null) {
                  return false;
               } else if (curr.equals(k)) {
                  if (Objects.equals(Object2ObjectOpenHashMap.this.value[pos], v)) {
                     Object2ObjectOpenHashMap.this.removeEntry(pos);
                     return true;
                  } else {
                     return false;
                  }
               } else {
                  while ((curr = key[pos = pos + 1 & Object2ObjectOpenHashMap.this.mask]) != null) {
                     if (curr.equals(k) && Objects.equals(Object2ObjectOpenHashMap.this.value[pos], v)) {
                        Object2ObjectOpenHashMap.this.removeEntry(pos);
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
         return Object2ObjectOpenHashMap.this.size;
      }

      @Override
      public void clear() {
         Object2ObjectOpenHashMap.this.clear();
      }

      @Override
      public void forEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
         if (Object2ObjectOpenHashMap.this.containsNullKey) {
            consumer.accept(Object2ObjectOpenHashMap.this.new MapEntry(Object2ObjectOpenHashMap.this.n));
         }

         int pos = Object2ObjectOpenHashMap.this.n;

         while (pos-- != 0) {
            if (Object2ObjectOpenHashMap.this.key[pos] != null) {
               consumer.accept(Object2ObjectOpenHashMap.this.new MapEntry(pos));
            }
         }
      }

      @Override
      public void fastForEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
         Object2ObjectOpenHashMap<K, V>.MapEntry entry = Object2ObjectOpenHashMap.this.new MapEntry();
         if (Object2ObjectOpenHashMap.this.containsNullKey) {
            entry.index = Object2ObjectOpenHashMap.this.n;
            consumer.accept(entry);
         }

         int pos = Object2ObjectOpenHashMap.this.n;

         while (pos-- != 0) {
            if (Object2ObjectOpenHashMap.this.key[pos] != null) {
               entry.index = pos;
               consumer.accept(entry);
            }
         }
      }
   }

   private abstract class MapIterator<ConsumerType> {
      int pos = Object2ObjectOpenHashMap.this.n;
      int last = -1;
      int c = Object2ObjectOpenHashMap.this.size;
      boolean mustReturnNullKey = Object2ObjectOpenHashMap.this.containsNullKey;
      ObjectArrayList<K> wrapped;

      private MapIterator() {
      }

      abstract void acceptOnIndex(ConsumerType var1, int var2);

      public boolean hasNext() {
         return this.c != 0;
      }

      public int nextEntry() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this.c--;
            if (this.mustReturnNullKey) {
               this.mustReturnNullKey = false;
               return this.last = Object2ObjectOpenHashMap.this.n;
            } else {
               K[] key = Object2ObjectOpenHashMap.this.key;

               while (--this.pos >= 0) {
                  if (key[this.pos] != null) {
                     return this.last = this.pos;
                  }
               }

               this.last = Integer.MIN_VALUE;
               K k = this.wrapped.get(-this.pos - 1);
               int p = HashCommon.mix(k.hashCode()) & Object2ObjectOpenHashMap.this.mask;

               while (!k.equals(key[p])) {
                  p = p + 1 & Object2ObjectOpenHashMap.this.mask;
               }

               return p;
            }
         }
      }

      public void forEachRemaining(ConsumerType action) {
         if (this.mustReturnNullKey) {
            this.mustReturnNullKey = false;
            this.acceptOnIndex(action, this.last = Object2ObjectOpenHashMap.this.n);
            this.c--;
         }

         K[] key = Object2ObjectOpenHashMap.this.key;

         while (this.c != 0) {
            if (--this.pos < 0) {
               this.last = Integer.MIN_VALUE;
               K k = this.wrapped.get(-this.pos - 1);
               int p = HashCommon.mix(k.hashCode()) & Object2ObjectOpenHashMap.this.mask;

               while (!k.equals(key[p])) {
                  p = p + 1 & Object2ObjectOpenHashMap.this.mask;
               }

               this.acceptOnIndex(action, p);
               this.c--;
            } else if (key[this.pos] != null) {
               this.acceptOnIndex(action, this.last = this.pos);
               this.c--;
            }
         }
      }

      private void shiftKeys(int pos) {
         K[] key = Object2ObjectOpenHashMap.this.key;

         label38:
         while (true) {
            int last = pos;

            K curr;
            for (pos = pos + 1 & Object2ObjectOpenHashMap.this.mask; (curr = key[pos]) != null; pos = pos + 1 & Object2ObjectOpenHashMap.this.mask) {
               int slot = HashCommon.mix(curr.hashCode()) & Object2ObjectOpenHashMap.this.mask;
               if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
                  if (pos < last) {
                     if (this.wrapped == null) {
                        this.wrapped = new ObjectArrayList<>(2);
                     }

                     this.wrapped.add(key[pos]);
                  }

                  key[last] = curr;
                  Object2ObjectOpenHashMap.this.value[last] = Object2ObjectOpenHashMap.this.value[pos];
                  continue label38;
               }
            }

            key[last] = null;
            Object2ObjectOpenHashMap.this.value[last] = null;
            return;
         }
      }

      public void remove() {
         if (this.last == -1) {
            throw new IllegalStateException();
         } else {
            if (this.last == Object2ObjectOpenHashMap.this.n) {
               Object2ObjectOpenHashMap.this.containsNullKey = false;
               Object2ObjectOpenHashMap.this.key[Object2ObjectOpenHashMap.this.n] = null;
               Object2ObjectOpenHashMap.this.value[Object2ObjectOpenHashMap.this.n] = null;
            } else {
               if (this.pos < 0) {
                  Object2ObjectOpenHashMap.this.remove(this.wrapped.set(-this.pos - 1, null));
                  this.last = -1;
                  return;
               }

               this.shiftKeys(this.last);
            }

            Object2ObjectOpenHashMap.this.size--;
            this.last = -1;
         }
      }

      public int skip(int n) {
         int i = n;

         while (i-- != 0 && this.hasNext()) {
            this.nextEntry();
         }

         return n - i - 1;
      }
   }

   private abstract class MapSpliterator<ConsumerType, SplitType extends Object2ObjectOpenHashMap<K, V>.MapSpliterator<ConsumerType, SplitType>> {
      int pos = 0;
      int max;
      int c;
      boolean mustReturnNull;
      boolean hasSplit;

      MapSpliterator() {
         this.max = Object2ObjectOpenHashMap.this.n;
         this.c = 0;
         this.mustReturnNull = Object2ObjectOpenHashMap.this.containsNullKey;
         this.hasSplit = false;
      }

      MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
         this.max = Object2ObjectOpenHashMap.this.n;
         this.c = 0;
         this.mustReturnNull = Object2ObjectOpenHashMap.this.containsNullKey;
         this.hasSplit = false;
         this.pos = pos;
         this.max = max;
         this.mustReturnNull = mustReturnNull;
         this.hasSplit = hasSplit;
      }

      abstract void acceptOnIndex(ConsumerType var1, int var2);

      abstract SplitType makeForSplit(int var1, int var2, boolean var3);

      public boolean tryAdvance(ConsumerType action) {
         if (this.mustReturnNull) {
            this.mustReturnNull = false;
            this.c++;
            this.acceptOnIndex(action, Object2ObjectOpenHashMap.this.n);
            return true;
         } else {
            for (K[] key = Object2ObjectOpenHashMap.this.key; this.pos < this.max; this.pos++) {
               if (key[this.pos] != null) {
                  this.c++;
                  this.acceptOnIndex(action, this.pos++);
                  return true;
               }
            }

            return false;
         }
      }

      public void forEachRemaining(ConsumerType action) {
         if (this.mustReturnNull) {
            this.mustReturnNull = false;
            this.c++;
            this.acceptOnIndex(action, Object2ObjectOpenHashMap.this.n);
         }

         for (K[] key = Object2ObjectOpenHashMap.this.key; this.pos < this.max; this.pos++) {
            if (key[this.pos] != null) {
               this.acceptOnIndex(action, this.pos);
               this.c++;
            }
         }
      }

      public long estimateSize() {
         return !this.hasSplit
            ? (long)(Object2ObjectOpenHashMap.this.size - this.c)
            : Math.min(
               (long)(Object2ObjectOpenHashMap.this.size - this.c),
               (long)((double)Object2ObjectOpenHashMap.this.realSize() / (double)Object2ObjectOpenHashMap.this.n * (double)(this.max - this.pos))
                  + (long)(this.mustReturnNull ? 1 : 0)
            );
      }

      public SplitType trySplit() {
         if (this.pos >= this.max - 1) {
            return null;
         } else {
            int retLen = this.max - this.pos >> 1;
            if (retLen <= 1) {
               return null;
            } else {
               int myNewPos = this.pos + retLen;
               int retPos = this.pos;
               SplitType split = this.makeForSplit(retPos, myNewPos, this.mustReturnNull);
               this.pos = myNewPos;
               this.mustReturnNull = false;
               this.hasSplit = true;
               return split;
            }
         }
      }

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

            K[] key = Object2ObjectOpenHashMap.this.key;

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

   private final class ValueIterator extends Object2ObjectOpenHashMap<K, V>.MapIterator<Consumer<? super V>> implements ObjectIterator<V> {
      public ValueIterator() {
      }

      final void acceptOnIndex(Consumer<? super V> action, int index) {
         action.accept(Object2ObjectOpenHashMap.this.value[index]);
      }

      @Override
      public V next() {
         return Object2ObjectOpenHashMap.this.value[this.nextEntry()];
      }
   }

   private final class ValueSpliterator
      extends Object2ObjectOpenHashMap<K, V>.MapSpliterator<Consumer<? super V>, Object2ObjectOpenHashMap<K, V>.ValueSpliterator>
      implements ObjectSpliterator<V> {
      private static final int POST_SPLIT_CHARACTERISTICS = 0;

      ValueSpliterator() {
      }

      ValueSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
         super(pos, max, mustReturnNull, hasSplit);
      }

      @Override
      public int characteristics() {
         return this.hasSplit ? 0 : 64;
      }

      final void acceptOnIndex(Consumer<? super V> action, int index) {
         action.accept(Object2ObjectOpenHashMap.this.value[index]);
      }

      final Object2ObjectOpenHashMap<K, V>.ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
         return Object2ObjectOpenHashMap.this.new ValueSpliterator(pos, max, mustReturnNull, true);
      }
   }
}
