package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class Object2IntArrayMap<K> extends AbstractObject2IntMap<K> implements Serializable, Cloneable {
   private static final long serialVersionUID = 1L;
   protected transient Object[] key;
   protected transient int[] value;
   protected int size;
   protected transient Object2IntMap.FastEntrySet<K> entries;
   protected transient ObjectSet<K> keys;
   protected transient IntCollection values;

   public Object2IntArrayMap(Object[] key, int[] value) {
      this.key = key;
      this.value = value;
      this.size = key.length;
      if (key.length != value.length) {
         throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
      }
   }

   public Object2IntArrayMap() {
      this.key = ObjectArrays.EMPTY_ARRAY;
      this.value = IntArrays.EMPTY_ARRAY;
   }

   public Object2IntArrayMap(int capacity) {
      this.key = new Object[capacity];
      this.value = new int[capacity];
   }

   public Object2IntArrayMap(Object2IntMap<K> m) {
      this(m.size());
      int i = 0;

      for (Object2IntMap.Entry<K> e : m.object2IntEntrySet()) {
         this.key[i] = e.getKey();
         this.value[i] = e.getIntValue();
         i++;
      }

      this.size = i;
   }

   public Object2IntArrayMap(Map<? extends K, ? extends Integer> m) {
      this(m.size());
      int i = 0;

      for (Entry<? extends K, ? extends Integer> e : m.entrySet()) {
         this.key[i] = e.getKey();
         this.value[i] = e.getValue();
         i++;
      }

      this.size = i;
   }

   public Object2IntArrayMap(Object[] key, int[] value, int size) {
      this.key = key;
      this.value = value;
      this.size = size;
      if (key.length != value.length) {
         throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
      } else if (size > key.length) {
         throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
      }
   }

   public Object2IntMap.FastEntrySet<K> object2IntEntrySet() {
      if (this.entries == null) {
         this.entries = new Object2IntArrayMap.EntrySet();
      }

      return this.entries;
   }

   private int findKey(Object k) {
      Object[] key = this.key;
      int i = this.size;

      while (i-- != 0) {
         if (Objects.equals(key[i], k)) {
            return i;
         }
      }

      return -1;
   }

   @Override
   public int getInt(Object k) {
      Object[] key = this.key;
      int i = this.size;

      while (i-- != 0) {
         if (Objects.equals(key[i], k)) {
            return this.value[i];
         }
      }

      return this.defRetValue;
   }

   @Override
   public int size() {
      return this.size;
   }

   @Override
   public void clear() {
      int i = this.size;

      while (i-- != 0) {
         this.key[i] = null;
      }

      this.size = 0;
   }

   @Override
   public boolean containsKey(Object k) {
      return this.findKey(k) != -1;
   }

   @Override
   public boolean containsValue(int v) {
      int i = this.size;

      while (i-- != 0) {
         if (this.value[i] == v) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean isEmpty() {
      return this.size == 0;
   }

   @Override
   public int put(K k, int v) {
      int oldKey = this.findKey(k);
      if (oldKey != -1) {
         int oldValue = this.value[oldKey];
         this.value[oldKey] = v;
         return oldValue;
      } else {
         if (this.size == this.key.length) {
            Object[] newKey = new Object[this.size == 0 ? 2 : this.size * 2];
            int[] newValue = new int[this.size == 0 ? 2 : this.size * 2];

            for (int i = this.size; i-- != 0; newValue[i] = this.value[i]) {
               newKey[i] = this.key[i];
            }

            this.key = newKey;
            this.value = newValue;
         }

         this.key[this.size] = k;
         this.value[this.size] = v;
         this.size++;
         return this.defRetValue;
      }
   }

   @Override
   public int removeInt(Object k) {
      int oldPos = this.findKey(k);
      if (oldPos == -1) {
         return this.defRetValue;
      } else {
         int oldValue = this.value[oldPos];
         int tail = this.size - oldPos - 1;
         System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
         System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
         this.size--;
         this.key[this.size] = null;
         return oldValue;
      }
   }

   @Override
   public ObjectSet<K> keySet() {
      if (this.keys == null) {
         this.keys = new Object2IntArrayMap.KeySet();
      }

      return this.keys;
   }

   @Override
   public IntCollection values() {
      if (this.values == null) {
         this.values = new Object2IntArrayMap.ValuesCollection();
      }

      return this.values;
   }

   public Object2IntArrayMap<K> clone() {
      Object2IntArrayMap<K> c;
      try {
         c = (Object2IntArrayMap<K>)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      c.key = (Object[])this.key.clone();
      c.value = (int[])this.value.clone();
      c.entries = null;
      c.keys = null;
      c.values = null;
      return c;
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      s.defaultWriteObject();
      int i = 0;

      for (int max = this.size; i < max; i++) {
         s.writeObject(this.key[i]);
         s.writeInt(this.value[i]);
      }
   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.key = new Object[this.size];
      this.value = new int[this.size];

      for (int i = 0; i < this.size; i++) {
         this.key[i] = s.readObject();
         this.value[i] = s.readInt();
      }
   }

   private final class EntrySet extends AbstractObjectSet<Object2IntMap.Entry<K>> implements Object2IntMap.FastEntrySet<K> {
      private EntrySet() {
      }

      @Override
      public ObjectIterator<Object2IntMap.Entry<K>> iterator() {
         return new ObjectIterator<Object2IntMap.Entry<K>>() {
            int curr = -1;
            int next = 0;

            @Override
            public boolean hasNext() {
               return this.next < Object2IntArrayMap.this.size;
            }

            public Object2IntMap.Entry<K> next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  return new AbstractObject2IntMap.BasicEntry<>(
                     (K)Object2IntArrayMap.this.key[this.curr = this.next], Object2IntArrayMap.this.value[this.next++]
                  );
               }
            }

            @Override
            public void remove() {
               if (this.curr == -1) {
                  throw new IllegalStateException();
               } else {
                  this.curr = -1;
                  int tail = Object2IntArrayMap.this.size-- - this.next--;
                  System.arraycopy(Object2IntArrayMap.this.key, this.next + 1, Object2IntArrayMap.this.key, this.next, tail);
                  System.arraycopy(Object2IntArrayMap.this.value, this.next + 1, Object2IntArrayMap.this.value, this.next, tail);
                  Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
               }
            }

            @Override
            public void forEachRemaining(Consumer<? super Object2IntMap.Entry<K>> action) {
               int max = Object2IntArrayMap.this.size;

               while (this.next < max) {
                  action.accept(
                     new AbstractObject2IntMap.BasicEntry<>((K)Object2IntArrayMap.this.key[this.curr = this.next], Object2IntArrayMap.this.value[this.next++])
                  );
               }
            }
         };
      }

      @Override
      public ObjectIterator<Object2IntMap.Entry<K>> fastIterator() {
         return new ObjectIterator<Object2IntMap.Entry<K>>() {
            int next = 0;
            int curr = -1;
            final AbstractObject2IntMap.BasicEntry<K> entry = new AbstractObject2IntMap.BasicEntry<>();

            @Override
            public boolean hasNext() {
               return this.next < Object2IntArrayMap.this.size;
            }

            public Object2IntMap.Entry<K> next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  this.entry.key = (K)Object2IntArrayMap.this.key[this.curr = this.next];
                  this.entry.value = Object2IntArrayMap.this.value[this.next++];
                  return this.entry;
               }
            }

            @Override
            public void remove() {
               if (this.curr == -1) {
                  throw new IllegalStateException();
               } else {
                  this.curr = -1;
                  int tail = Object2IntArrayMap.this.size-- - this.next--;
                  System.arraycopy(Object2IntArrayMap.this.key, this.next + 1, Object2IntArrayMap.this.key, this.next, tail);
                  System.arraycopy(Object2IntArrayMap.this.value, this.next + 1, Object2IntArrayMap.this.value, this.next, tail);
                  Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
               }
            }

            @Override
            public void forEachRemaining(Consumer<? super Object2IntMap.Entry<K>> action) {
               int max = Object2IntArrayMap.this.size;

               while (this.next < max) {
                  this.entry.key = (K)Object2IntArrayMap.this.key[this.curr = this.next];
                  this.entry.value = Object2IntArrayMap.this.value[this.next++];
                  action.accept(this.entry);
               }
            }
         };
      }

      @Override
      public ObjectSpliterator<Object2IntMap.Entry<K>> spliterator() {
         return new Object2IntArrayMap.EntrySet.EntrySetSpliterator(0, Object2IntArrayMap.this.size);
      }

      @Override
      public void forEach(Consumer<? super Object2IntMap.Entry<K>> action) {
         int i = 0;

         for (int max = Object2IntArrayMap.this.size; i < max; i++) {
            action.accept(new AbstractObject2IntMap.BasicEntry<>((K)Object2IntArrayMap.this.key[i], Object2IntArrayMap.this.value[i]));
         }
      }

      @Override
      public void fastForEach(Consumer<? super Object2IntMap.Entry<K>> action) {
         AbstractObject2IntMap.BasicEntry<K> entry = new AbstractObject2IntMap.BasicEntry<>();
         int i = 0;

         for (int max = Object2IntArrayMap.this.size; i < max; i++) {
            entry.key = (K)Object2IntArrayMap.this.key[i];
            entry.value = Object2IntArrayMap.this.value[i];
            action.accept(entry);
         }
      }

      @Override
      public int size() {
         return Object2IntArrayMap.this.size;
      }

      @Override
      public boolean contains(Object o) {
         if (!(o instanceof Entry)) {
            return false;
         } else {
            Entry<?, ?> e = (Entry<?, ?>)o;
            if (e.getValue() != null && e.getValue() instanceof Integer) {
               K k = (K)e.getKey();
               return Object2IntArrayMap.this.containsKey(k) && Object2IntArrayMap.this.getInt(k) == (Integer)e.getValue();
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
               int oldPos = Object2IntArrayMap.this.findKey(k);
               if (oldPos != -1 && v == Object2IntArrayMap.this.value[oldPos]) {
                  int tail = Object2IntArrayMap.this.size - oldPos - 1;
                  System.arraycopy(Object2IntArrayMap.this.key, oldPos + 1, Object2IntArrayMap.this.key, oldPos, tail);
                  System.arraycopy(Object2IntArrayMap.this.value, oldPos + 1, Object2IntArrayMap.this.value, oldPos, tail);
                  Object2IntArrayMap.this.size--;
                  Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
                  return true;
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
      }

      final class EntrySetSpliterator
         extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Object2IntMap.Entry<K>>
         implements ObjectSpliterator<Object2IntMap.Entry<K>> {
         EntrySetSpliterator(int pos, int maxPos) {
            super(pos, maxPos);
         }

         @Override
         public int characteristics() {
            return 16465;
         }

         protected final Object2IntMap.Entry<K> get(int location) {
            return new AbstractObject2IntMap.BasicEntry<>((K)Object2IntArrayMap.this.key[location], Object2IntArrayMap.this.value[location]);
         }

         protected final Object2IntArrayMap<K>.EntrySet.EntrySetSpliterator makeForSplit(int pos, int maxPos) {
            return EntrySet.this.new EntrySetSpliterator(pos, maxPos);
         }
      }
   }

   private final class KeySet extends AbstractObjectSet<K> {
      private KeySet() {
      }

      @Override
      public boolean contains(Object k) {
         return Object2IntArrayMap.this.findKey(k) != -1;
      }

      @Override
      public boolean remove(Object k) {
         int oldPos = Object2IntArrayMap.this.findKey(k);
         if (oldPos == -1) {
            return false;
         } else {
            int tail = Object2IntArrayMap.this.size - oldPos - 1;
            System.arraycopy(Object2IntArrayMap.this.key, oldPos + 1, Object2IntArrayMap.this.key, oldPos, tail);
            System.arraycopy(Object2IntArrayMap.this.value, oldPos + 1, Object2IntArrayMap.this.value, oldPos, tail);
            Object2IntArrayMap.this.size--;
            Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
            return true;
         }
      }

      @Override
      public ObjectIterator<K> iterator() {
         return new ObjectIterator<K>() {
            int pos = 0;

            @Override
            public boolean hasNext() {
               return this.pos < Object2IntArrayMap.this.size;
            }

            @Override
            public K next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  return (K)Object2IntArrayMap.this.key[this.pos++];
               }
            }

            @Override
            public void remove() {
               if (this.pos == 0) {
                  throw new IllegalStateException();
               } else {
                  int tail = Object2IntArrayMap.this.size - this.pos;
                  System.arraycopy(Object2IntArrayMap.this.key, this.pos, Object2IntArrayMap.this.key, this.pos - 1, tail);
                  System.arraycopy(Object2IntArrayMap.this.value, this.pos, Object2IntArrayMap.this.value, this.pos - 1, tail);
                  Object2IntArrayMap.this.size--;
                  this.pos--;
                  Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
               }
            }

            @Override
            public void forEachRemaining(Consumer<? super K> action) {
               int max = Object2IntArrayMap.this.size;

               while (this.pos < max) {
                  action.accept((K)Object2IntArrayMap.this.key[this.pos++]);
               }
            }
         };
      }

      @Override
      public ObjectSpliterator<K> spliterator() {
         return new Object2IntArrayMap.KeySet.KeySetSpliterator(0, Object2IntArrayMap.this.size);
      }

      @Override
      public void forEach(Consumer<? super K> action) {
         int i = 0;

         for (int max = Object2IntArrayMap.this.size; i < max; i++) {
            action.accept((K)Object2IntArrayMap.this.key[i]);
         }
      }

      @Override
      public int size() {
         return Object2IntArrayMap.this.size;
      }

      @Override
      public void clear() {
         Object2IntArrayMap.this.clear();
      }

      final class KeySetSpliterator extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<K> implements ObjectSpliterator<K> {
         KeySetSpliterator(int pos, int maxPos) {
            super(pos, maxPos);
         }

         @Override
         public int characteristics() {
            return 16465;
         }

         @Override
         protected final K get(int location) {
            return (K)Object2IntArrayMap.this.key[location];
         }

         protected final Object2IntArrayMap<K>.KeySet.KeySetSpliterator makeForSplit(int pos, int maxPos) {
            return KeySet.this.new KeySetSpliterator(pos, maxPos);
         }

         @Override
         public void forEachRemaining(Consumer<? super K> action) {
            int max = Object2IntArrayMap.this.size;

            while (this.pos < max) {
               action.accept((K)Object2IntArrayMap.this.key[this.pos++]);
            }
         }
      }
   }

   private final class ValuesCollection extends AbstractIntCollection {
      private ValuesCollection() {
      }

      @Override
      public boolean contains(int v) {
         return Object2IntArrayMap.this.containsValue(v);
      }

      @Override
      public IntIterator iterator() {
         return new IntIterator() {
            int pos = 0;

            @Override
            public boolean hasNext() {
               return this.pos < Object2IntArrayMap.this.size;
            }

            @Override
            public int nextInt() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  return Object2IntArrayMap.this.value[this.pos++];
               }
            }

            @Override
            public void remove() {
               if (this.pos == 0) {
                  throw new IllegalStateException();
               } else {
                  int tail = Object2IntArrayMap.this.size - this.pos;
                  System.arraycopy(Object2IntArrayMap.this.key, this.pos, Object2IntArrayMap.this.key, this.pos - 1, tail);
                  System.arraycopy(Object2IntArrayMap.this.value, this.pos, Object2IntArrayMap.this.value, this.pos - 1, tail);
                  Object2IntArrayMap.this.size--;
                  this.pos--;
                  Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
               }
            }

            @Override
            public void forEachRemaining(IntConsumer action) {
               int max = Object2IntArrayMap.this.size;

               while (this.pos < max) {
                  action.accept(Object2IntArrayMap.this.value[this.pos++]);
               }
            }
         };
      }

      @Override
      public IntSpliterator spliterator() {
         return new Object2IntArrayMap.ValuesCollection.ValuesSpliterator(0, Object2IntArrayMap.this.size);
      }

      @Override
      public void forEach(IntConsumer action) {
         int i = 0;

         for (int max = Object2IntArrayMap.this.size; i < max; i++) {
            action.accept(Object2IntArrayMap.this.value[i]);
         }
      }

      @Override
      public int size() {
         return Object2IntArrayMap.this.size;
      }

      @Override
      public void clear() {
         Object2IntArrayMap.this.clear();
      }

      final class ValuesSpliterator extends IntSpliterators.EarlyBindingSizeIndexBasedSpliterator implements IntSpliterator {
         ValuesSpliterator(int pos, int maxPos) {
            super(pos, maxPos);
         }

         @Override
         public int characteristics() {
            return 16720;
         }

         @Override
         protected final int get(int location) {
            return Object2IntArrayMap.this.value[location];
         }

         protected final Object2IntArrayMap<K>.ValuesCollection.ValuesSpliterator makeForSplit(int pos, int maxPos) {
            return ValuesCollection.this.new ValuesSpliterator(pos, maxPos);
         }

         @Override
         public void forEachRemaining(IntConsumer action) {
            int max = Object2IntArrayMap.this.size;

            while (this.pos < max) {
               action.accept(Object2IntArrayMap.this.value[this.pos++]);
            }
         }
      }
   }
}
