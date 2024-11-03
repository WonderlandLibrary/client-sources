package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.Consumer;

public abstract class AbstractInt2ObjectMap<V> extends AbstractInt2ObjectFunction<V> implements Int2ObjectMap<V>, Serializable {
   private static final long serialVersionUID = -4940583368468432370L;

   protected AbstractInt2ObjectMap() {
   }

   @Override
   public boolean containsKey(int k) {
      ObjectIterator<Int2ObjectMap.Entry<V>> i = this.int2ObjectEntrySet().iterator();

      while (i.hasNext()) {
         if (i.next().getIntKey() == k) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean containsValue(Object v) {
      ObjectIterator<Int2ObjectMap.Entry<V>> i = this.int2ObjectEntrySet().iterator();

      while (i.hasNext()) {
         if (i.next().getValue() == v) {
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
   public IntSet keySet() {
      return new AbstractIntSet() {
         @Override
         public boolean contains(int k) {
            return AbstractInt2ObjectMap.this.containsKey(k);
         }

         @Override
         public int size() {
            return AbstractInt2ObjectMap.this.size();
         }

         @Override
         public void clear() {
            AbstractInt2ObjectMap.this.clear();
         }

         @Override
         public IntIterator iterator() {
            return new IntIterator() {
               private final ObjectIterator<Int2ObjectMap.Entry<V>> i = Int2ObjectMaps.fastIterator(AbstractInt2ObjectMap.this);

               @Override
               public int nextInt() {
                  return this.i.next().getIntKey();
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
               public void forEachRemaining(java.util.function.IntConsumer action) {
                  this.i.forEachRemaining(entry -> action.accept(entry.getIntKey()));
               }
            };
         }

         @Override
         public IntSpliterator spliterator() {
            return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(AbstractInt2ObjectMap.this), 321);
         }
      };
   }

   @Override
   public ObjectCollection<V> values() {
      return new AbstractObjectCollection<V>() {
         @Override
         public boolean contains(Object k) {
            return AbstractInt2ObjectMap.this.containsValue(k);
         }

         @Override
         public int size() {
            return AbstractInt2ObjectMap.this.size();
         }

         @Override
         public void clear() {
            AbstractInt2ObjectMap.this.clear();
         }

         @Override
         public ObjectIterator<V> iterator() {
            return new ObjectIterator<V>() {
               private final ObjectIterator<Int2ObjectMap.Entry<V>> i = Int2ObjectMaps.fastIterator(AbstractInt2ObjectMap.this);

               @Override
               public V next() {
                  return this.i.next().getValue();
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
               public void forEachRemaining(Consumer<? super V> action) {
                  this.i.forEachRemaining(entry -> action.accept(entry.getValue()));
               }
            };
         }

         @Override
         public ObjectSpliterator<V> spliterator() {
            return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(AbstractInt2ObjectMap.this), 64);
         }
      };
   }

   @Override
   public void putAll(Map<? extends Integer, ? extends V> m) {
      if (m instanceof Int2ObjectMap) {
         ObjectIterator<Int2ObjectMap.Entry<V>> i = Int2ObjectMaps.fastIterator((Int2ObjectMap<V>)m);

         while (i.hasNext()) {
            Int2ObjectMap.Entry<? extends V> e = i.next();
            this.put(e.getIntKey(), (V)e.getValue());
         }
      } else {
         int n = m.size();
         Iterator<? extends Entry<? extends Integer, ? extends V>> i = m.entrySet().iterator();

         while (n-- != 0) {
            Entry<? extends Integer, ? extends V> e = (Entry<? extends Integer, ? extends V>)i.next();
            this.put(e.getKey(), (V)e.getValue());
         }
      }
   }

   @Override
   public int hashCode() {
      int h = 0;
      int n = this.size();
      ObjectIterator<Int2ObjectMap.Entry<V>> i = Int2ObjectMaps.fastIterator(this);

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
         return m.size() != this.size() ? false : this.int2ObjectEntrySet().containsAll(m.entrySet());
      }
   }

   @Override
   public String toString() {
      StringBuilder s = new StringBuilder();
      ObjectIterator<Int2ObjectMap.Entry<V>> i = Int2ObjectMaps.fastIterator(this);
      int n = this.size();
      boolean first = true;
      s.append("{");

      while (n-- != 0) {
         if (first) {
            first = false;
         } else {
            s.append(", ");
         }

         Int2ObjectMap.Entry<V> e = i.next();
         s.append(String.valueOf(e.getIntKey()));
         s.append("=>");
         if (this == e.getValue()) {
            s.append("(this map)");
         } else {
            s.append(String.valueOf(e.getValue()));
         }
      }

      s.append("}");
      return s.toString();
   }

   public static class BasicEntry<V> implements Int2ObjectMap.Entry<V> {
      protected int key;
      protected V value;

      public BasicEntry() {
      }

      public BasicEntry(Integer key, V value) {
         this.key = key;
         this.value = value;
      }

      public BasicEntry(int key, V value) {
         this.key = key;
         this.value = value;
      }

      @Override
      public int getIntKey() {
         return this.key;
      }

      @Override
      public V getValue() {
         return this.value;
      }

      @Override
      public V setValue(V value) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean equals(Object o) {
         if (!(o instanceof Entry)) {
            return false;
         } else if (o instanceof Int2ObjectMap.Entry) {
            Int2ObjectMap.Entry<V> e = (Int2ObjectMap.Entry<V>)o;
            return this.key == e.getIntKey() && Objects.equals(this.value, e.getValue());
         } else {
            Entry<?, ?> e = (Entry<?, ?>)o;
            Object key = e.getKey();
            if (key != null && key instanceof Integer) {
               Object value = e.getValue();
               return this.key == (Integer)key && Objects.equals(this.value, value);
            } else {
               return false;
            }
         }
      }

      @Override
      public int hashCode() {
         return this.key ^ (this.value == null ? 0 : this.value.hashCode());
      }

      @Override
      public String toString() {
         return this.key + "->" + this.value;
      }
   }

   public abstract static class BasicEntrySet<V> extends AbstractObjectSet<Int2ObjectMap.Entry<V>> {
      protected final Int2ObjectMap<V> map;

      public BasicEntrySet(Int2ObjectMap<V> map) {
         this.map = map;
      }

      @Override
      public boolean contains(Object o) {
         if (!(o instanceof Entry)) {
            return false;
         } else if (o instanceof Int2ObjectMap.Entry) {
            Int2ObjectMap.Entry<V> e = (Int2ObjectMap.Entry<V>)o;
            int k = e.getIntKey();
            return this.map.containsKey(k) && Objects.equals(this.map.get(k), e.getValue());
         } else {
            Entry<?, ?> e = (Entry<?, ?>)o;
            Object key = e.getKey();
            if (key != null && key instanceof Integer) {
               int k = (Integer)key;
               Object value = e.getValue();
               return this.map.containsKey(k) && Objects.equals(this.map.get(k), value);
            } else {
               return false;
            }
         }
      }

      @Override
      public boolean remove(Object o) {
         if (!(o instanceof Entry)) {
            return false;
         } else if (o instanceof Int2ObjectMap.Entry) {
            Int2ObjectMap.Entry<V> e = (Int2ObjectMap.Entry<V>)o;
            return this.map.remove(e.getIntKey(), e.getValue());
         } else {
            Entry<?, ?> e = (Entry<?, ?>)o;
            Object key = e.getKey();
            if (key != null && key instanceof Integer) {
               int k = (Integer)key;
               Object v = e.getValue();
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
      public ObjectSpliterator<Int2ObjectMap.Entry<V>> spliterator() {
         return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.map), 65);
      }
   }
}
