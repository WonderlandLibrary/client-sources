package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
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
import java.util.function.IntConsumer;
import java.util.function.ToIntFunction;

public class Object2IntOpenHashMap<K> extends AbstractObject2IntMap<K> implements Serializable, Cloneable, Hash {
   private static final long serialVersionUID = 0L;
   private static final boolean ASSERTS = false;
   protected transient K[] key;
   protected transient int[] value;
   protected transient int mask;
   protected transient boolean containsNullKey;
   protected transient int n;
   protected transient int maxFill;
   protected final transient int minN;
   protected int size;
   protected final float f;
   protected transient Object2IntMap.FastEntrySet<K> entries;
   protected transient ObjectSet<K> keys;
   protected transient IntCollection values;

   public Object2IntOpenHashMap(int expected, float f) {
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
         this.value = new int[this.n + 1];
      }
   }

   public Object2IntOpenHashMap(int expected) {
      this(expected, 0.75F);
   }

   public Object2IntOpenHashMap() {
      this(16, 0.75F);
   }

   public Object2IntOpenHashMap(Map<? extends K, ? extends Integer> m, float f) {
      this(m.size(), f);
      this.putAll(m);
   }

   public Object2IntOpenHashMap(Map<? extends K, ? extends Integer> m) {
      this(m, 0.75F);
   }

   public Object2IntOpenHashMap(Object2IntMap<K> m, float f) {
      this(m.size(), f);
      this.putAll(m);
   }

   public Object2IntOpenHashMap(Object2IntMap<K> m) {
      this(m, 0.75F);
   }

   public Object2IntOpenHashMap(K[] k, int[] v, float f) {
      this(k.length, f);
      if (k.length != v.length) {
         throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
      } else {
         for (int i = 0; i < k.length; i++) {
            this.put(k[i], v[i]);
         }
      }
   }

   public Object2IntOpenHashMap(K[] k, int[] v) {
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
      this.shiftKeys(pos);
      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
         this.rehash(this.n / 2);
      }

      return oldValue;
   }

   private int removeNullEntry() {
      this.containsNullKey = false;
      this.key[this.n] = null;
      int oldValue = this.value[this.n];
      this.size--;
      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
         this.rehash(this.n / 2);
      }

      return oldValue;
   }

   @Override
   public void putAll(Map<? extends K, ? extends Integer> m) {
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

   private void insert(int pos, K k, int v) {
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
   public int put(K k, int v) {
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

   public int addTo(K k, int incr) {
      int pos;
      if (k == null) {
         if (this.containsNullKey) {
            return this.addToValue(this.n, incr);
         }

         pos = this.n;
         this.containsNullKey = true;
      } else {
         K[] key = this.key;
         K curr;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
            if (curr.equals(k)) {
               return this.addToValue(pos, incr);
            }

            while ((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (curr.equals(k)) {
                  return this.addToValue(pos, incr);
               }
            }
         }
      }

      this.key[pos] = k;
      this.value[pos] = this.defRetValue + incr;
      if (this.size++ >= this.maxFill) {
         this.rehash(HashCommon.arraySize(this.size + 1, this.f));
      }

      return this.defRetValue;
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
         return;
      }
   }

   @Override
   public int removeInt(Object k) {
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
   public int getInt(Object k) {
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
   public boolean containsValue(int v) {
      int[] value = this.value;
      K[] key = this.key;
      if (this.containsNullKey && value[this.n] == v) {
         return true;
      } else {
         int i = this.n;

         while (i-- != 0) {
            if (key[i] != null && value[i] == v) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public int getOrDefault(Object k, int defaultValue) {
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
   public int putIfAbsent(K k, int v) {
      int pos = this.find(k);
      if (pos >= 0) {
         return this.value[pos];
      } else {
         this.insert(-pos - 1, k, v);
         return this.defRetValue;
      }
   }

   @Override
   public boolean remove(Object k, int v) {
      if (k == null) {
         if (this.containsNullKey && v == this.value[this.n]) {
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
         } else if (k.equals(curr) && v == this.value[pos]) {
            this.removeEntry(pos);
            return true;
         } else {
            while ((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr) && v == this.value[pos]) {
                  this.removeEntry(pos);
                  return true;
               }
            }

            return false;
         }
      }
   }

   @Override
   public boolean replace(K k, int oldValue, int v) {
      int pos = this.find(k);
      if (pos >= 0 && oldValue == this.value[pos]) {
         this.value[pos] = v;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public int replace(K k, int v) {
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
   public int computeIfAbsent(K k, ToIntFunction<? super K> mappingFunction) {
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
   public int computeIfAbsent(K key, Object2IntFunction<? super K> mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      int pos = this.find(key);
      if (pos >= 0) {
         return this.value[pos];
      } else if (!mappingFunction.containsKey(key)) {
         return this.defRetValue;
      } else {
         int newValue = mappingFunction.getInt(key);
         this.insert(-pos - 1, key, newValue);
         return newValue;
      }
   }

   @Override
   public int computeIntIfPresent(K k, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int pos = this.find(k);
      if (pos < 0) {
         return this.defRetValue;
      } else {
         Integer newValue = remappingFunction.apply(k, this.value[pos]);
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
   public int computeInt(K k, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int pos = this.find(k);
      Integer newValue = remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
      if (newValue == null) {
         if (pos >= 0) {
            if (k == null) {
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
   public int merge(K k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
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
   public void clear() {
      if (this.size != 0) {
         this.size = 0;
         this.containsNullKey = false;
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

   public Object2IntMap.FastEntrySet<K> object2IntEntrySet() {
      if (this.entries == null) {
         this.entries = new Object2IntOpenHashMap.MapEntrySet();
      }

      return this.entries;
   }

   @Override
   public ObjectSet<K> keySet() {
      if (this.keys == null) {
         this.keys = new Object2IntOpenHashMap.KeySet();
      }

      return this.keys;
   }

   @Override
   public IntCollection values() {
      if (this.values == null) {
         this.values = new AbstractIntCollection() {
            @Override
            public IntIterator iterator() {
               return Object2IntOpenHashMap.this.new ValueIterator();
            }

            @Override
            public IntSpliterator spliterator() {
               return Object2IntOpenHashMap.this.new ValueSpliterator();
            }

            @Override
            public void forEach(IntConsumer consumer) {
               if (Object2IntOpenHashMap.this.containsNullKey) {
                  consumer.accept(Object2IntOpenHashMap.this.value[Object2IntOpenHashMap.this.n]);
               }

               int pos = Object2IntOpenHashMap.this.n;

               while (pos-- != 0) {
                  if (Object2IntOpenHashMap.this.key[pos] != null) {
                     consumer.accept(Object2IntOpenHashMap.this.value[pos]);
                  }
               }
            }

            @Override
            public int size() {
               return Object2IntOpenHashMap.this.size;
            }

            @Override
            public boolean contains(int v) {
               return Object2IntOpenHashMap.this.containsValue(v);
            }

            @Override
            public void clear() {
               Object2IntOpenHashMap.this.clear();
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
      int[] value = this.value;
      int mask = newN - 1;
      K[] newKey = (K[])(new Object[newN + 1]);
      int[] newValue = new int[newN + 1];
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

   public Object2IntOpenHashMap<K> clone() {
      Object2IntOpenHashMap<K> c;
      try {
         c = (Object2IntOpenHashMap<K>)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      c.keys = null;
      c.values = null;
      c.entries = null;
      c.containsNullKey = this.containsNullKey;
      c.key = (K[])((Object[])this.key.clone());
      c.value = (int[])this.value.clone();
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

         t ^= this.value[i];
         h += t;
      }

      if (this.containsNullKey) {
         h += this.value[this.n];
      }

      return h;
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      K[] key = this.key;
      int[] value = this.value;
      Object2IntOpenHashMap<K>.EntryIterator i = new Object2IntOpenHashMap.EntryIterator();
      s.defaultWriteObject();
      int j = this.size;

      while (j-- != 0) {
         int e = i.nextEntry();
         s.writeObject(key[e]);
         s.writeInt(value[e]);
      }
   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.n = HashCommon.arraySize(this.size, this.f);
      this.maxFill = HashCommon.maxFill(this.n, this.f);
      this.mask = this.n - 1;
      K[] key = this.key = (K[])(new Object[this.n + 1]);
      int[] value = this.value = new int[this.n + 1];
      int i = this.size;

      while (i-- != 0) {
         K k = (K)s.readObject();
         int v = s.readInt();
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
      extends Object2IntOpenHashMap<K>.MapIterator<Consumer<? super Object2IntMap.Entry<K>>>
      implements ObjectIterator<Object2IntMap.Entry<K>> {
      private Object2IntOpenHashMap<K>.MapEntry entry;

      private EntryIterator() {
      }

      public Object2IntOpenHashMap<K>.MapEntry next() {
         return this.entry = Object2IntOpenHashMap.this.new MapEntry(this.nextEntry());
      }

      final void acceptOnIndex(Consumer<? super Object2IntMap.Entry<K>> action, int index) {
         action.accept(this.entry = Object2IntOpenHashMap.this.new MapEntry(index));
      }

      @Override
      public void remove() {
         super.remove();
         this.entry.index = -1;
      }
   }

   private final class EntrySpliterator
      extends Object2IntOpenHashMap<K>.MapSpliterator<Consumer<? super Object2IntMap.Entry<K>>, Object2IntOpenHashMap<K>.EntrySpliterator>
      implements ObjectSpliterator<Object2IntMap.Entry<K>> {
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

      final void acceptOnIndex(Consumer<? super Object2IntMap.Entry<K>> action, int index) {
         action.accept(Object2IntOpenHashMap.this.new MapEntry(index));
      }

      final Object2IntOpenHashMap<K>.EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
         return Object2IntOpenHashMap.this.new EntrySpliterator(pos, max, mustReturnNull, true);
      }
   }

   private final class FastEntryIterator
      extends Object2IntOpenHashMap<K>.MapIterator<Consumer<? super Object2IntMap.Entry<K>>>
      implements ObjectIterator<Object2IntMap.Entry<K>> {
      private final Object2IntOpenHashMap<K>.MapEntry entry = Object2IntOpenHashMap.this.new MapEntry();

      private FastEntryIterator() {
      }

      public Object2IntOpenHashMap<K>.MapEntry next() {
         this.entry.index = this.nextEntry();
         return this.entry;
      }

      final void acceptOnIndex(Consumer<? super Object2IntMap.Entry<K>> action, int index) {
         this.entry.index = index;
         action.accept(this.entry);
      }
   }

   private final class KeyIterator extends Object2IntOpenHashMap<K>.MapIterator<Consumer<? super K>> implements ObjectIterator<K> {
      public KeyIterator() {
      }

      final void acceptOnIndex(Consumer<? super K> action, int index) {
         action.accept(Object2IntOpenHashMap.this.key[index]);
      }

      @Override
      public K next() {
         return Object2IntOpenHashMap.this.key[this.nextEntry()];
      }
   }

   private final class KeySet extends AbstractObjectSet<K> {
      private KeySet() {
      }

      @Override
      public ObjectIterator<K> iterator() {
         return Object2IntOpenHashMap.this.new KeyIterator();
      }

      @Override
      public ObjectSpliterator<K> spliterator() {
         return Object2IntOpenHashMap.this.new KeySpliterator();
      }

      @Override
      public void forEach(Consumer<? super K> consumer) {
         if (Object2IntOpenHashMap.this.containsNullKey) {
            consumer.accept(Object2IntOpenHashMap.this.key[Object2IntOpenHashMap.this.n]);
         }

         int pos = Object2IntOpenHashMap.this.n;

         while (pos-- != 0) {
            K k = Object2IntOpenHashMap.this.key[pos];
            if (k != null) {
               consumer.accept(k);
            }
         }
      }

      @Override
      public int size() {
         return Object2IntOpenHashMap.this.size;
      }

      @Override
      public boolean contains(Object k) {
         return Object2IntOpenHashMap.this.containsKey(k);
      }

      @Override
      public boolean remove(Object k) {
         int oldSize = Object2IntOpenHashMap.this.size;
         Object2IntOpenHashMap.this.removeInt(k);
         return Object2IntOpenHashMap.this.size != oldSize;
      }

      @Override
      public void clear() {
         Object2IntOpenHashMap.this.clear();
      }
   }

   private final class KeySpliterator
      extends Object2IntOpenHashMap<K>.MapSpliterator<Consumer<? super K>, Object2IntOpenHashMap<K>.KeySpliterator>
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
         action.accept(Object2IntOpenHashMap.this.key[index]);
      }

      final Object2IntOpenHashMap<K>.KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
         return Object2IntOpenHashMap.this.new KeySpliterator(pos, max, mustReturnNull, true);
      }
   }

   final class MapEntry implements Object2IntMap.Entry<K>, Entry<K, Integer>, ObjectIntPair<K> {
      int index;

      MapEntry(int index) {
         this.index = index;
      }

      MapEntry() {
      }

      @Override
      public K getKey() {
         return Object2IntOpenHashMap.this.key[this.index];
      }

      @Override
      public K left() {
         return Object2IntOpenHashMap.this.key[this.index];
      }

      @Override
      public int getIntValue() {
         return Object2IntOpenHashMap.this.value[this.index];
      }

      @Override
      public int rightInt() {
         return Object2IntOpenHashMap.this.value[this.index];
      }

      @Override
      public int setValue(int v) {
         int oldValue = Object2IntOpenHashMap.this.value[this.index];
         Object2IntOpenHashMap.this.value[this.index] = v;
         return oldValue;
      }

      @Override
      public ObjectIntPair<K> right(int v) {
         Object2IntOpenHashMap.this.value[this.index] = v;
         return this;
      }

      @Deprecated
      @Override
      public Integer getValue() {
         return Object2IntOpenHashMap.this.value[this.index];
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
            Entry<K, Integer> e = (Entry<K, Integer>)o;
            return Objects.equals(Object2IntOpenHashMap.this.key[this.index], e.getKey()) && Object2IntOpenHashMap.this.value[this.index] == e.getValue();
         }
      }

      @Override
      public int hashCode() {
         return (Object2IntOpenHashMap.this.key[this.index] == null ? 0 : Object2IntOpenHashMap.this.key[this.index].hashCode())
            ^ Object2IntOpenHashMap.this.value[this.index];
      }

      @Override
      public String toString() {
         return Object2IntOpenHashMap.this.key[this.index] + "=>" + Object2IntOpenHashMap.this.value[this.index];
      }
   }

   private final class MapEntrySet extends AbstractObjectSet<Object2IntMap.Entry<K>> implements Object2IntMap.FastEntrySet<K> {
      private MapEntrySet() {
      }

      @Override
      public ObjectIterator<Object2IntMap.Entry<K>> iterator() {
         return Object2IntOpenHashMap.this.new EntryIterator();
      }

      @Override
      public ObjectIterator<Object2IntMap.Entry<K>> fastIterator() {
         return Object2IntOpenHashMap.this.new FastEntryIterator();
      }

      @Override
      public ObjectSpliterator<Object2IntMap.Entry<K>> spliterator() {
         return Object2IntOpenHashMap.this.new EntrySpliterator();
      }

      @Override
      public boolean contains(Object o) {
         if (!(o instanceof Entry)) {
            return false;
         } else {
            Entry<?, ?> e = (Entry<?, ?>)o;
            if (e.getValue() != null && e.getValue() instanceof Integer) {
               K k = (K)e.getKey();
               int v = (Integer)e.getValue();
               if (k == null) {
                  return Object2IntOpenHashMap.this.containsNullKey && Object2IntOpenHashMap.this.value[Object2IntOpenHashMap.this.n] == v;
               } else {
                  K[] key = Object2IntOpenHashMap.this.key;
                  K curr;
                  int pos;
                  if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2IntOpenHashMap.this.mask]) == null) {
                     return false;
                  } else if (k.equals(curr)) {
                     return Object2IntOpenHashMap.this.value[pos] == v;
                  } else {
                     while ((curr = key[pos = pos + 1 & Object2IntOpenHashMap.this.mask]) != null) {
                        if (k.equals(curr)) {
                           return Object2IntOpenHashMap.this.value[pos] == v;
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
            if (e.getValue() != null && e.getValue() instanceof Integer) {
               K k = (K)e.getKey();
               int v = (Integer)e.getValue();
               if (k == null) {
                  if (Object2IntOpenHashMap.this.containsNullKey && Object2IntOpenHashMap.this.value[Object2IntOpenHashMap.this.n] == v) {
                     Object2IntOpenHashMap.this.removeNullEntry();
                     return true;
                  } else {
                     return false;
                  }
               } else {
                  K[] key = Object2IntOpenHashMap.this.key;
                  K curr;
                  int pos;
                  if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2IntOpenHashMap.this.mask]) == null) {
                     return false;
                  } else if (curr.equals(k)) {
                     if (Object2IntOpenHashMap.this.value[pos] == v) {
                        Object2IntOpenHashMap.this.removeEntry(pos);
                        return true;
                     } else {
                        return false;
                     }
                  } else {
                     while ((curr = key[pos = pos + 1 & Object2IntOpenHashMap.this.mask]) != null) {
                        if (curr.equals(k) && Object2IntOpenHashMap.this.value[pos] == v) {
                           Object2IntOpenHashMap.this.removeEntry(pos);
                           return true;
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
      public int size() {
         return Object2IntOpenHashMap.this.size;
      }

      @Override
      public void clear() {
         Object2IntOpenHashMap.this.clear();
      }

      @Override
      public void forEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
         if (Object2IntOpenHashMap.this.containsNullKey) {
            consumer.accept(Object2IntOpenHashMap.this.new MapEntry(Object2IntOpenHashMap.this.n));
         }

         int pos = Object2IntOpenHashMap.this.n;

         while (pos-- != 0) {
            if (Object2IntOpenHashMap.this.key[pos] != null) {
               consumer.accept(Object2IntOpenHashMap.this.new MapEntry(pos));
            }
         }
      }

      @Override
      public void fastForEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
         Object2IntOpenHashMap<K>.MapEntry entry = Object2IntOpenHashMap.this.new MapEntry();
         if (Object2IntOpenHashMap.this.containsNullKey) {
            entry.index = Object2IntOpenHashMap.this.n;
            consumer.accept(entry);
         }

         int pos = Object2IntOpenHashMap.this.n;

         while (pos-- != 0) {
            if (Object2IntOpenHashMap.this.key[pos] != null) {
               entry.index = pos;
               consumer.accept(entry);
            }
         }
      }
   }

   private abstract class MapIterator<ConsumerType> {
      int pos = Object2IntOpenHashMap.this.n;
      int last = -1;
      int c = Object2IntOpenHashMap.this.size;
      boolean mustReturnNullKey = Object2IntOpenHashMap.this.containsNullKey;
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
               return this.last = Object2IntOpenHashMap.this.n;
            } else {
               K[] key = Object2IntOpenHashMap.this.key;

               while (--this.pos >= 0) {
                  if (key[this.pos] != null) {
                     return this.last = this.pos;
                  }
               }

               this.last = Integer.MIN_VALUE;
               K k = this.wrapped.get(-this.pos - 1);
               int p = HashCommon.mix(k.hashCode()) & Object2IntOpenHashMap.this.mask;

               while (!k.equals(key[p])) {
                  p = p + 1 & Object2IntOpenHashMap.this.mask;
               }

               return p;
            }
         }
      }

      public void forEachRemaining(ConsumerType action) {
         if (this.mustReturnNullKey) {
            this.mustReturnNullKey = false;
            this.acceptOnIndex(action, this.last = Object2IntOpenHashMap.this.n);
            this.c--;
         }

         K[] key = Object2IntOpenHashMap.this.key;

         while (this.c != 0) {
            if (--this.pos < 0) {
               this.last = Integer.MIN_VALUE;
               K k = this.wrapped.get(-this.pos - 1);
               int p = HashCommon.mix(k.hashCode()) & Object2IntOpenHashMap.this.mask;

               while (!k.equals(key[p])) {
                  p = p + 1 & Object2IntOpenHashMap.this.mask;
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
         K[] key = Object2IntOpenHashMap.this.key;

         label38:
         while (true) {
            int last = pos;

            K curr;
            for (pos = pos + 1 & Object2IntOpenHashMap.this.mask; (curr = key[pos]) != null; pos = pos + 1 & Object2IntOpenHashMap.this.mask) {
               int slot = HashCommon.mix(curr.hashCode()) & Object2IntOpenHashMap.this.mask;
               if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
                  if (pos < last) {
                     if (this.wrapped == null) {
                        this.wrapped = new ObjectArrayList<>(2);
                     }

                     this.wrapped.add(key[pos]);
                  }

                  key[last] = curr;
                  Object2IntOpenHashMap.this.value[last] = Object2IntOpenHashMap.this.value[pos];
                  continue label38;
               }
            }

            key[last] = null;
            return;
         }
      }

      public void remove() {
         if (this.last == -1) {
            throw new IllegalStateException();
         } else {
            if (this.last == Object2IntOpenHashMap.this.n) {
               Object2IntOpenHashMap.this.containsNullKey = false;
               Object2IntOpenHashMap.this.key[Object2IntOpenHashMap.this.n] = null;
            } else {
               if (this.pos < 0) {
                  Object2IntOpenHashMap.this.removeInt(this.wrapped.set(-this.pos - 1, null));
                  this.last = -1;
                  return;
               }

               this.shiftKeys(this.last);
            }

            Object2IntOpenHashMap.this.size--;
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

   private abstract class MapSpliterator<ConsumerType, SplitType extends Object2IntOpenHashMap<K>.MapSpliterator<ConsumerType, SplitType>> {
      int pos = 0;
      int max;
      int c;
      boolean mustReturnNull;
      boolean hasSplit;

      MapSpliterator() {
         this.max = Object2IntOpenHashMap.this.n;
         this.c = 0;
         this.mustReturnNull = Object2IntOpenHashMap.this.containsNullKey;
         this.hasSplit = false;
      }

      MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
         this.max = Object2IntOpenHashMap.this.n;
         this.c = 0;
         this.mustReturnNull = Object2IntOpenHashMap.this.containsNullKey;
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
            this.acceptOnIndex(action, Object2IntOpenHashMap.this.n);
            return true;
         } else {
            for (K[] key = Object2IntOpenHashMap.this.key; this.pos < this.max; this.pos++) {
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
            this.acceptOnIndex(action, Object2IntOpenHashMap.this.n);
         }

         for (K[] key = Object2IntOpenHashMap.this.key; this.pos < this.max; this.pos++) {
            if (key[this.pos] != null) {
               this.acceptOnIndex(action, this.pos);
               this.c++;
            }
         }
      }

      public long estimateSize() {
         return !this.hasSplit
            ? (long)(Object2IntOpenHashMap.this.size - this.c)
            : Math.min(
               (long)(Object2IntOpenHashMap.this.size - this.c),
               (long)((double)Object2IntOpenHashMap.this.realSize() / (double)Object2IntOpenHashMap.this.n * (double)(this.max - this.pos))
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

            K[] key = Object2IntOpenHashMap.this.key;

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

   private final class ValueIterator extends Object2IntOpenHashMap<K>.MapIterator<IntConsumer> implements IntIterator {
      public ValueIterator() {
      }

      final void acceptOnIndex(IntConsumer action, int index) {
         action.accept(Object2IntOpenHashMap.this.value[index]);
      }

      @Override
      public int nextInt() {
         return Object2IntOpenHashMap.this.value[this.nextEntry()];
      }
   }

   private final class ValueSpliterator
      extends Object2IntOpenHashMap<K>.MapSpliterator<IntConsumer, Object2IntOpenHashMap<K>.ValueSpliterator>
      implements IntSpliterator {
      private static final int POST_SPLIT_CHARACTERISTICS = 256;

      ValueSpliterator() {
      }

      ValueSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
         super(pos, max, mustReturnNull, hasSplit);
      }

      @Override
      public int characteristics() {
         return this.hasSplit ? 256 : 320;
      }

      final void acceptOnIndex(IntConsumer action, int index) {
         action.accept(Object2IntOpenHashMap.this.value[index]);
      }

      final Object2IntOpenHashMap<K>.ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
         return Object2IntOpenHashMap.this.new ValueSpliterator(pos, max, mustReturnNull, true);
      }
   }
}
