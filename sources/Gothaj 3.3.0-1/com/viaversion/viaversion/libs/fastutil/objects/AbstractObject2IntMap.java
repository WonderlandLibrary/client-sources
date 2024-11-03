package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntBinaryOperator;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public abstract class AbstractObject2IntMap<K> extends AbstractObject2IntFunction<K> implements Object2IntMap<K>, Serializable {
   private static final long serialVersionUID = -4940583368468432370L;

   protected AbstractObject2IntMap() {
   }

   @Override
   public boolean containsKey(Object k) {
      ObjectIterator<Object2IntMap.Entry<K>> i = this.object2IntEntrySet().iterator();

      while (i.hasNext()) {
         if (i.next().getKey() == k) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean containsValue(int v) {
      ObjectIterator<Object2IntMap.Entry<K>> i = this.object2IntEntrySet().iterator();

      while (i.hasNext()) {
         if (i.next().getIntValue() == v) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean isEmpty() {
      return this.size() == 0;
   }

   @Override
   public final int mergeInt(K key, int value, IntBinaryOperator remappingFunction) {
      return this.mergeInt(key, value, remappingFunction);
   }

   @Override
   public ObjectSet<K> keySet() {
      return new AbstractObjectSet<K>() {
         @Override
         public boolean contains(Object k) {
            return AbstractObject2IntMap.this.containsKey(k);
         }

         @Override
         public int size() {
            return AbstractObject2IntMap.this.size();
         }

         @Override
         public void clear() {
            AbstractObject2IntMap.this.clear();
         }

         @Override
         public ObjectIterator<K> iterator() {
            return new ObjectIterator<K>() {
               private final ObjectIterator<Object2IntMap.Entry<K>> i = Object2IntMaps.fastIterator(AbstractObject2IntMap.this);

               @Override
               public K next() {
                  return this.i.next().getKey();
               }

               @Override
               public boolean hasNext() {
                  return this.i.hasNext();
               }

               @Override
               public void remove() {
                  this.i.remove();
               }

               @Override
               public void forEachRemaining(Consumer<? super K> action) {
                  this.i.forEachRemaining(entry -> action.accept(entry.getKey()));
               }
            };
         }

         @Override
         public ObjectSpliterator<K> spliterator() {
            return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(AbstractObject2IntMap.this), 65);
         }
      };
   }

   @Override
   public IntCollection values() {
      return new AbstractIntCollection() {
         @Override
         public boolean contains(int k) {
            return AbstractObject2IntMap.this.containsValue(k);
         }

         @Override
         public int size() {
            return AbstractObject2IntMap.this.size();
         }

         @Override
         public void clear() {
            AbstractObject2IntMap.this.clear();
         }

         @Override
         public IntIterator iterator() {
            return new IntIterator() {
               private final ObjectIterator<Object2IntMap.Entry<K>> i = Object2IntMaps.fastIterator(AbstractObject2IntMap.this);

               @Override
               public int nextInt() {
                  return this.i.next().getIntValue();
               }

               @Override
               public boolean hasNext() {
                  return this.i.hasNext();
               }

               @Override
               public void remove() {
                  this.i.remove();
               }

               @Override
               public void forEachRemaining(IntConsumer action) {
                  this.i.forEachRemaining(entry -> action.accept(entry.getIntValue()));
               }
            };
         }

         @Override
         public IntSpliterator spliterator() {
            return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(AbstractObject2IntMap.this), 320);
         }
      };
   }

   @Override
   public void putAll(Map<? extends K, ? extends Integer> m) {
      if (m instanceof Object2IntMap) {
         ObjectIterator<Object2IntMap.Entry<K>> i = Object2IntMaps.fastIterator((Object2IntMap<K>)m);

         while (i.hasNext()) {
            Object2IntMap.Entry<? extends K> e = i.next();
            this.put((K)e.getKey(), e.getIntValue());
         }
      } else {
         int n = m.size();
         Iterator<? extends Entry<? extends K, ? extends Integer>> i = m.entrySet().iterator();

         while (n-- != 0) {
            Entry<? extends K, ? extends Integer> e = (Entry<? extends K, ? extends Integer>)i.next();
            this.put((K)e.getKey(), e.getValue());
         }
      }
   }

   @Override
   public int hashCode() {
      int h = 0;
      int n = this.size();
      ObjectIterator<Object2IntMap.Entry<K>> i = Object2IntMaps.fastIterator(this);

      while (n-- != 0) {
         h += i.next().hashCode();
      }

      return h;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Map)) {
         return false;
      } else {
         Map<?, ?> m = (Map<?, ?>)o;
         return m.size() != this.size() ? false : this.object2IntEntrySet().containsAll(m.entrySet());
      }
   }

   @Override
   public String toString() {
      StringBuilder s = new StringBuilder();
      ObjectIterator<Object2IntMap.Entry<K>> i = Object2IntMaps.fastIterator(this);
      int n = this.size();
      boolean first = true;
      s.append("{");

      while (n-- != 0) {
         if (first) {
            first = false;
         } else {
            s.append(", ");
         }

         Object2IntMap.Entry<K> e = i.next();
         if (this == e.getKey()) {
            s.append("(this map)");
         } else {
            s.append(String.valueOf(e.getKey()));
         }

         s.append("=>");
         s.append(String.valueOf(e.getIntValue()));
      }

      s.append("}");
      return s.toString();
   }

   public static class BasicEntry<K> implements Object2IntMap.Entry<K> {
      protected K key;
      protected int value;

      public BasicEntry() {
      }

      public BasicEntry(K key, Integer value) {
         this.key = key;
         this.value = value;
      }

      public BasicEntry(K key, int value) {
         this.key = key;
         this.value = value;
      }

      @Override
      public K getKey() {
         return this.key;
      }

      @Override
      public int getIntValue() {
         return this.value;
      }

      @Override
      public int setValue(int value) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean equals(Object o) {
         if (!(o instanceof Entry)) {
            return false;
         } else if (o instanceof Object2IntMap.Entry) {
            Object2IntMap.Entry<K> e = (Object2IntMap.Entry<K>)o;
            return Objects.equals(this.key, e.getKey()) && this.value == e.getIntValue();
         } else {
            Entry<?, ?> e = (Entry<?, ?>)o;
            Object key = e.getKey();
            Object value = e.getValue();
            return value != null && value instanceof Integer ? Objects.equals(this.key, key) && this.value == (Integer)value : false;
         }
      }

      @Override
      public int hashCode() {
         return (this.key == null ? 0 : this.key.hashCode()) ^ this.value;
      }

      @Override
      public String toString() {
         return this.key + "->" + this.value;
      }
   }

   public abstract static class BasicEntrySet<K> extends AbstractObjectSet<Object2IntMap.Entry<K>> {
      protected final Object2IntMap<K> map;

      public BasicEntrySet(Object2IntMap<K> map) {
         this.map = map;
      }

      @Override
      public boolean contains(Object o) {
         if (!(o instanceof Entry)) {
            return false;
         } else if (o instanceof Object2IntMap.Entry) {
            Object2IntMap.Entry<K> e = (Object2IntMap.Entry<K>)o;
            K k = e.getKey();
            return this.map.containsKey(k) && this.map.getInt(k) == e.getIntValue();
         } else {
            Entry<?, ?> e = (Entry<?, ?>)o;
            Object k = e.getKey();
            Object value = e.getValue();
            return value != null && value instanceof Integer ? this.map.containsKey(k) && this.map.getInt(k) == (Integer)value : false;
         }
      }

      @Override
      public boolean remove(Object o) {
         if (!(o instanceof Entry)) {
            return false;
         } else if (o instanceof Object2IntMap.Entry) {
            Object2IntMap.Entry<K> e = (Object2IntMap.Entry<K>)o;
            return this.map.remove(e.getKey(), e.getIntValue());
         } else {
            Entry<?, ?> e = (Entry<?, ?>)o;
            Object k = e.getKey();
            Object value = e.getValue();
            if (value != null && value instanceof Integer) {
               int v = (Integer)value;
               return this.map.remove(k, v);
            } else {
               return false;
            }
         }
      }

      @Override
      public int size() {
         return this.map.size();
      }

      @Override
      public ObjectSpliterator<Object2IntMap.Entry<K>> spliterator() {
         return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.map), 65);
      }
   }
}
