package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.Consumer;

public class Int2ObjectArrayMap<V> extends AbstractInt2ObjectMap<V> implements Serializable, Cloneable {
   private static final long serialVersionUID = 1L;
   protected transient int[] key;
   protected transient Object[] value;
   protected int size;
   protected transient Int2ObjectMap.FastEntrySet<V> entries;
   protected transient IntSet keys;
   protected transient ObjectCollection<V> values;

   public Int2ObjectArrayMap(int[] key, Object[] value) {
      this.key = key;
      this.value = value;
      this.size = key.length;
      if (key.length != value.length) {
         throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
      }
   }

   public Int2ObjectArrayMap() {
      this.key = IntArrays.EMPTY_ARRAY;
      this.value = ObjectArrays.EMPTY_ARRAY;
   }

   public Int2ObjectArrayMap(int capacity) {
      this.key = new int[capacity];
      this.value = new Object[capacity];
   }

   public Int2ObjectArrayMap(Int2ObjectMap<V> m) {
      this(m.size());
      int i = 0;

      for (Int2ObjectMap.Entry<V> e : m.int2ObjectEntrySet()) {
         this.key[i] = e.getIntKey();
         this.value[i] = e.getValue();
         i++;
      }

      this.size = i;
   }

   public Int2ObjectArrayMap(Map<? extends Integer, ? extends V> m) {
      this(m.size());
      int i = 0;

      for (Entry<? extends Integer, ? extends V> e : m.entrySet()) {
         this.key[i] = e.getKey();
         this.value[i] = e.getValue();
         i++;
      }

      this.size = i;
   }

   public Int2ObjectArrayMap(int[] key, Object[] value, int size) {
      this.key = key;
      this.value = value;
      this.size = size;
      if (key.length != value.length) {
         throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
      } else if (size > key.length) {
         throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
      }
   }

   public Int2ObjectMap.FastEntrySet<V> int2ObjectEntrySet() {
      if (this.entries == null) {
         this.entries = new Int2ObjectArrayMap.EntrySet();
      }

      return this.entries;
   }

   private int findKey(int k) {
      int[] key = this.key;
      int i = this.size;

      while (i-- != 0) {
         if (key[i] == k) {
            return i;
         }
      }

      return -1;
   }

   @Override
   public V get(int k) {
      int[] key = this.key;
      int i = this.size;

      while (i-- != 0) {
         if (key[i] == k) {
            return (V)this.value[i];
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
         this.value[i] = null;
      }

      this.size = 0;
   }

   @Override
   public boolean containsKey(int k) {
      return this.findKey(k) != -1;
   }

   @Override
   public boolean containsValue(Object v) {
      int i = this.size;

      while (i-- != 0) {
         if (Objects.equals(this.value[i], v)) {
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
   public V put(int k, V v) {
      int oldKey = this.findKey(k);
      if (oldKey != -1) {
         V oldValue = (V)this.value[oldKey];
         this.value[oldKey] = v;
         return oldValue;
      } else {
         if (this.size == this.key.length) {
            int[] newKey = new int[this.size == 0 ? 2 : this.size * 2];
            Object[] newValue = new Object[this.size == 0 ? 2 : this.size * 2];

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
   public V remove(int k) {
      int oldPos = this.findKey(k);
      if (oldPos == -1) {
         return this.defRetValue;
      } else {
         V oldValue = (V)this.value[oldPos];
         int tail = this.size - oldPos - 1;
         System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
         System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
         this.size--;
         this.value[this.size] = null;
         return oldValue;
      }
   }

   @Override
   public IntSet keySet() {
      if (this.keys == null) {
         this.keys = new Int2ObjectArrayMap.KeySet();
      }

      return this.keys;
   }

   @Override
   public ObjectCollection<V> values() {
      if (this.values == null) {
         this.values = new Int2ObjectArrayMap.ValuesCollection();
      }

      return this.values;
   }

   public Int2ObjectArrayMap<V> clone() {
      Int2ObjectArrayMap<V> c;
      try {
         c = (Int2ObjectArrayMap<V>)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      c.key = (int[])this.key.clone();
      c.value = (Object[])this.value.clone();
      c.entries = null;
      c.keys = null;
      c.values = null;
      return c;
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      s.defaultWriteObject();
      int i = 0;

      for (int max = this.size; i < max; i++) {
         s.writeInt(this.key[i]);
         s.writeObject(this.value[i]);
      }
   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.key = new int[this.size];
      this.value = new Object[this.size];

      for (int i = 0; i < this.size; i++) {
         this.key[i] = s.readInt();
         this.value[i] = s.readObject();
      }
   }

   private final class EntrySet extends AbstractObjectSet<Int2ObjectMap.Entry<V>> implements Int2ObjectMap.FastEntrySet<V> {
      private EntrySet() {
      }

      @Override
      public ObjectIterator<Int2ObjectMap.Entry<V>> iterator() {
         return new ObjectIterator<Int2ObjectMap.Entry<V>>() {
            int curr = -1;
            int next = 0;

            @Override
            public boolean hasNext() {
               return this.next < Int2ObjectArrayMap.this.size;
            }

            public Int2ObjectMap.Entry<V> next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  return new AbstractInt2ObjectMap.BasicEntry<>(
                     Int2ObjectArrayMap.this.key[this.curr = this.next], (V)Int2ObjectArrayMap.this.value[this.next++]
                  );
               }
            }

            @Override
            public void remove() {
               if (this.curr == -1) {
                  throw new IllegalStateException();
               } else {
                  this.curr = -1;
                  int tail = Int2ObjectArrayMap.this.size-- - this.next--;
                  System.arraycopy(Int2ObjectArrayMap.this.key, this.next + 1, Int2ObjectArrayMap.this.key, this.next, tail);
                  System.arraycopy(Int2ObjectArrayMap.this.value, this.next + 1, Int2ObjectArrayMap.this.value, this.next, tail);
                  Int2ObjectArrayMap.this.value[Int2ObjectArrayMap.this.size] = null;
               }
            }

            @Override
            public void forEachRemaining(Consumer<? super Int2ObjectMap.Entry<V>> action) {
               int max = Int2ObjectArrayMap.this.size;

               while (this.next < max) {
                  action.accept(
                     new AbstractInt2ObjectMap.BasicEntry<>(Int2ObjectArrayMap.this.key[this.curr = this.next], (V)Int2ObjectArrayMap.this.value[this.next++])
                  );
               }
            }
         };
      }

      @Override
      public ObjectIterator<Int2ObjectMap.Entry<V>> fastIterator() {
         return new ObjectIterator<Int2ObjectMap.Entry<V>>() {
            int next = 0;
            int curr = -1;
            final AbstractInt2ObjectMap.BasicEntry<V> entry = new AbstractInt2ObjectMap.BasicEntry<>();

            @Override
            public boolean hasNext() {
               return this.next < Int2ObjectArrayMap.this.size;
            }

            public Int2ObjectMap.Entry<V> next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  this.entry.key = Int2ObjectArrayMap.this.key[this.curr = this.next];
                  this.entry.value = (V)Int2ObjectArrayMap.this.value[this.next++];
                  return this.entry;
               }
            }

            @Override
            public void remove() {
               if (this.curr == -1) {
                  throw new IllegalStateException();
               } else {
                  this.curr = -1;
                  int tail = Int2ObjectArrayMap.this.size-- - this.next--;
                  System.arraycopy(Int2ObjectArrayMap.this.key, this.next + 1, Int2ObjectArrayMap.this.key, this.next, tail);
                  System.arraycopy(Int2ObjectArrayMap.this.value, this.next + 1, Int2ObjectArrayMap.this.value, this.next, tail);
                  Int2ObjectArrayMap.this.value[Int2ObjectArrayMap.this.size] = null;
               }
            }

            @Override
            public void forEachRemaining(Consumer<? super Int2ObjectMap.Entry<V>> action) {
               int max = Int2ObjectArrayMap.this.size;

               while (this.next < max) {
                  this.entry.key = Int2ObjectArrayMap.this.key[this.curr = this.next];
                  this.entry.value = (V)Int2ObjectArrayMap.this.value[this.next++];
                  action.accept(this.entry);
               }
            }
         };
      }

      @Override
      public ObjectSpliterator<Int2ObjectMap.Entry<V>> spliterator() {
         return new Int2ObjectArrayMap.EntrySet.EntrySetSpliterator(0, Int2ObjectArrayMap.this.size);
      }

      @Override
      public void forEach(Consumer<? super Int2ObjectMap.Entry<V>> action) {
         int i = 0;

         for (int max = Int2ObjectArrayMap.this.size; i < max; i++) {
            action.accept(new AbstractInt2ObjectMap.BasicEntry<>(Int2ObjectArrayMap.this.key[i], (V)Int2ObjectArrayMap.this.value[i]));
         }
      }

      @Override
      public void fastForEach(Consumer<? super Int2ObjectMap.Entry<V>> action) {
         AbstractInt2ObjectMap.BasicEntry<V> entry = new AbstractInt2ObjectMap.BasicEntry<>();
         int i = 0;

         for (int max = Int2ObjectArrayMap.this.size; i < max; i++) {
            entry.key = Int2ObjectArrayMap.this.key[i];
            entry.value = (V)Int2ObjectArrayMap.this.value[i];
            action.accept(entry);
         }
      }

      @Override
      public int size() {
         return Int2ObjectArrayMap.this.size;
      }

      @Override
      public boolean contains(Object o) {
         if (!(o instanceof Entry)) {
            return false;
         } else {
            Entry<?, ?> e = (Entry<?, ?>)o;
            if (e.getKey() != null && e.getKey() instanceof Integer) {
               int k = (Integer)e.getKey();
               return Int2ObjectArrayMap.this.containsKey(k) && Objects.equals(Int2ObjectArrayMap.this.get(k), e.getValue());
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
               int k = (Integer)e.getKey();
               V v = (V)e.getValue();
               int oldPos = Int2ObjectArrayMap.this.findKey(k);
               if (oldPos != -1 && Objects.equals(v, Int2ObjectArrayMap.this.value[oldPos])) {
                  int tail = Int2ObjectArrayMap.this.size - oldPos - 1;
                  System.arraycopy(Int2ObjectArrayMap.this.key, oldPos + 1, Int2ObjectArrayMap.this.key, oldPos, tail);
                  System.arraycopy(Int2ObjectArrayMap.this.value, oldPos + 1, Int2ObjectArrayMap.this.value, oldPos, tail);
                  Int2ObjectArrayMap.this.size--;
                  Int2ObjectArrayMap.this.value[Int2ObjectArrayMap.this.size] = null;
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
         extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Int2ObjectMap.Entry<V>>
         implements ObjectSpliterator<Int2ObjectMap.Entry<V>> {
         EntrySetSpliterator(int pos, int maxPos) {
            super(pos, maxPos);
         }

         @Override
         public int characteristics() {
            return 16465;
         }

         protected final Int2ObjectMap.Entry<V> get(int location) {
            return new AbstractInt2ObjectMap.BasicEntry<>(Int2ObjectArrayMap.this.key[location], (V)Int2ObjectArrayMap.this.value[location]);
         }

         protected final Int2ObjectArrayMap<V>.EntrySet.EntrySetSpliterator makeForSplit(int pos, int maxPos) {
            return EntrySet.this.new EntrySetSpliterator(pos, maxPos);
         }
      }
   }

   private final class KeySet extends AbstractIntSet {
      private KeySet() {
      }

      @Override
      public boolean contains(int k) {
         return Int2ObjectArrayMap.this.findKey(k) != -1;
      }

      @Override
      public boolean remove(int k) {
         int oldPos = Int2ObjectArrayMap.this.findKey(k);
         if (oldPos == -1) {
            return false;
         } else {
            int tail = Int2ObjectArrayMap.this.size - oldPos - 1;
            System.arraycopy(Int2ObjectArrayMap.this.key, oldPos + 1, Int2ObjectArrayMap.this.key, oldPos, tail);
            System.arraycopy(Int2ObjectArrayMap.this.value, oldPos + 1, Int2ObjectArrayMap.this.value, oldPos, tail);
            Int2ObjectArrayMap.this.size--;
            Int2ObjectArrayMap.this.value[Int2ObjectArrayMap.this.size] = null;
            return true;
         }
      }

      @Override
      public IntIterator iterator() {
         return new IntIterator() {
            int pos = 0;

            @Override
            public boolean hasNext() {
               return this.pos < Int2ObjectArrayMap.this.size;
            }

            @Override
            public int nextInt() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  return Int2ObjectArrayMap.this.key[this.pos++];
               }
            }

            @Override
            public void remove() {
               if (this.pos == 0) {
                  throw new IllegalStateException();
               } else {
                  int tail = Int2ObjectArrayMap.this.size - this.pos;
                  System.arraycopy(Int2ObjectArrayMap.this.key, this.pos, Int2ObjectArrayMap.this.key, this.pos - 1, tail);
                  System.arraycopy(Int2ObjectArrayMap.this.value, this.pos, Int2ObjectArrayMap.this.value, this.pos - 1, tail);
                  Int2ObjectArrayMap.this.size--;
                  this.pos--;
                  Int2ObjectArrayMap.this.value[Int2ObjectArrayMap.this.size] = null;
               }
            }

            @Override
            public void forEachRemaining(java.util.function.IntConsumer action) {
               int max = Int2ObjectArrayMap.this.size;

               while (this.pos < max) {
                  action.accept(Int2ObjectArrayMap.this.key[this.pos++]);
               }
            }
         };
      }

      @Override
      public IntSpliterator spliterator() {
         return new Int2ObjectArrayMap.KeySet.KeySetSpliterator(0, Int2ObjectArrayMap.this.size);
      }

      @Override
      public void forEach(java.util.function.IntConsumer action) {
         int i = 0;

         for (int max = Int2ObjectArrayMap.this.size; i < max; i++) {
            action.accept(Int2ObjectArrayMap.this.key[i]);
         }
      }

      @Override
      public int size() {
         return Int2ObjectArrayMap.this.size;
      }

      @Override
      public void clear() {
         Int2ObjectArrayMap.this.clear();
      }

      final class KeySetSpliterator extends IntSpliterators.EarlyBindingSizeIndexBasedSpliterator implements IntSpliterator {
         KeySetSpliterator(int pos, int maxPos) {
            super(pos, maxPos);
         }

         @Override
         public int characteristics() {
            return 16721;
         }

         @Override
         protected final int get(int location) {
            return Int2ObjectArrayMap.this.key[location];
         }

         protected final Int2ObjectArrayMap<V>.KeySet.KeySetSpliterator makeForSplit(int pos, int maxPos) {
            return KeySet.this.new KeySetSpliterator(pos, maxPos);
         }

         @Override
         public void forEachRemaining(java.util.function.IntConsumer action) {
            int max = Int2ObjectArrayMap.this.size;

            while (this.pos < max) {
               action.accept(Int2ObjectArrayMap.this.key[this.pos++]);
            }
         }
      }
   }

   private final class ValuesCollection extends AbstractObjectCollection<V> {
      private ValuesCollection() {
      }

      @Override
      public boolean contains(Object v) {
         return Int2ObjectArrayMap.this.containsValue(v);
      }

      @Override
      public ObjectIterator<V> iterator() {
         return new ObjectIterator<V>() {
            int pos = 0;

            @Override
            public boolean hasNext() {
               return this.pos < Int2ObjectArrayMap.this.size;
            }

            @Override
            public V next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  return (V)Int2ObjectArrayMap.this.value[this.pos++];
               }
            }

            @Override
            public void remove() {
               if (this.pos == 0) {
                  throw new IllegalStateException();
               } else {
                  int tail = Int2ObjectArrayMap.this.size - this.pos;
                  System.arraycopy(Int2ObjectArrayMap.this.key, this.pos, Int2ObjectArrayMap.this.key, this.pos - 1, tail);
                  System.arraycopy(Int2ObjectArrayMap.this.value, this.pos, Int2ObjectArrayMap.this.value, this.pos - 1, tail);
                  Int2ObjectArrayMap.this.size--;
                  this.pos--;
                  Int2ObjectArrayMap.this.value[Int2ObjectArrayMap.this.size] = null;
               }
            }

            @Override
            public void forEachRemaining(Consumer<? super V> action) {
               int max = Int2ObjectArrayMap.this.size;

               while (this.pos < max) {
                  action.accept((V)Int2ObjectArrayMap.this.value[this.pos++]);
               }
            }
         };
      }

      @Override
      public ObjectSpliterator<V> spliterator() {
         return new Int2ObjectArrayMap.ValuesCollection.ValuesSpliterator(0, Int2ObjectArrayMap.this.size);
      }

      @Override
      public void forEach(Consumer<? super V> action) {
         int i = 0;

         for (int max = Int2ObjectArrayMap.this.size; i < max; i++) {
            action.accept((V)Int2ObjectArrayMap.this.value[i]);
         }
      }

      @Override
      public int size() {
         return Int2ObjectArrayMap.this.size;
      }

      @Override
      public void clear() {
         Int2ObjectArrayMap.this.clear();
      }

      final class ValuesSpliterator extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<V> implements ObjectSpliterator<V> {
         ValuesSpliterator(int pos, int maxPos) {
            super(pos, maxPos);
         }

         @Override
         public int characteristics() {
            return 16464;
         }

         @Override
         protected final V get(int location) {
            return (V)Int2ObjectArrayMap.this.value[location];
         }

         protected final Int2ObjectArrayMap<V>.ValuesCollection.ValuesSpliterator makeForSplit(int pos, int maxPos) {
            return ValuesCollection.this.new ValuesSpliterator(pos, maxPos);
         }

         @Override
         public void forEachRemaining(Consumer<? super V> action) {
            int max = Int2ObjectArrayMap.this.size;

            while (this.pos < max) {
               action.accept((V)Int2ObjectArrayMap.this.value[this.pos++]);
            }
         }
      }
   }
}
