package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public abstract class AbstractInt2IntMap extends AbstractInt2IntFunction implements Int2IntMap, Serializable {
   private static final long serialVersionUID = -4940583368468432370L;

   protected AbstractInt2IntMap() {
   }

   @Override
   public boolean containsKey(int k) {
      ObjectIterator<Int2IntMap.Entry> i = this.int2IntEntrySet().iterator();

      while (i.hasNext()) {
         if (i.next().getIntKey() == k) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean containsValue(int v) {
      ObjectIterator<Int2IntMap.Entry> i = this.int2IntEntrySet().iterator();

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
   public final int mergeInt(int key, int value, IntBinaryOperator remappingFunction) {
      return this.mergeInt(key, value, remappingFunction);
   }

   @Override
   public IntSet keySet() {
      return new AbstractIntSet() {
         @Override
         public boolean contains(int k) {
            return AbstractInt2IntMap.this.containsKey(k);
         }

         @Override
         public int size() {
            return AbstractInt2IntMap.this.size();
         }

         @Override
         public void clear() {
            AbstractInt2IntMap.this.clear();
         }

         @Override
         public IntIterator iterator() {
            return new IntIterator() {
               private final ObjectIterator<Int2IntMap.Entry> i = Int2IntMaps.fastIterator(AbstractInt2IntMap.this);

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
            return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(AbstractInt2IntMap.this), 321);
         }
      };
   }

   @Override
   public IntCollection values() {
      return new AbstractIntCollection() {
         @Override
         public boolean contains(int k) {
            return AbstractInt2IntMap.this.containsValue(k);
         }

         @Override
         public int size() {
            return AbstractInt2IntMap.this.size();
         }

         @Override
         public void clear() {
            AbstractInt2IntMap.this.clear();
         }

         @Override
         public IntIterator iterator() {
            return new IntIterator() {
               private final ObjectIterator<Int2IntMap.Entry> i = Int2IntMaps.fastIterator(AbstractInt2IntMap.this);

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
               public void forEachRemaining(java.util.function.IntConsumer action) {
                  this.i.forEachRemaining(entry -> action.accept(entry.getIntValue()));
               }
            };
         }

         @Override
         public IntSpliterator spliterator() {
            return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(AbstractInt2IntMap.this), 320);
         }
      };
   }

   @Override
   public void putAll(Map<? extends Integer, ? extends Integer> m) {
      if (m instanceof Int2IntMap) {
         ObjectIterator<Int2IntMap.Entry> i = Int2IntMaps.fastIterator((Int2IntMap)m);

         while (i.hasNext()) {
            Int2IntMap.Entry e = i.next();
            this.put(e.getIntKey(), e.getIntValue());
         }
      } else {
         int n = m.size();
         Iterator<? extends Entry<? extends Integer, ? extends Integer>> i = m.entrySet().iterator();

         while (n-- != 0) {
            Entry<? extends Integer, ? extends Integer> e = (Entry<? extends Integer, ? extends Integer>)i.next();
            this.put(e.getKey(), e.getValue());
         }
      }
   }

   @Override
   public int hashCode() {
      int h = 0;
      int n = this.size();
      ObjectIterator<Int2IntMap.Entry> i = Int2IntMaps.fastIterator(this);

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
         return m.size() != this.size() ? false : this.int2IntEntrySet().containsAll(m.entrySet());
      }
   }

   @Override
   public String toString() {
      StringBuilder s = new StringBuilder();
      ObjectIterator<Int2IntMap.Entry> i = Int2IntMaps.fastIterator(this);
      int n = this.size();
      boolean first = true;
      s.append("{");

      while (n-- != 0) {
         if (first) {
            first = false;
         } else {
            s.append(", ");
         }

         Int2IntMap.Entry e = i.next();
         s.append(String.valueOf(e.getIntKey()));
         s.append("=>");
         s.append(String.valueOf(e.getIntValue()));
      }

      s.append("}");
      return s.toString();
   }

   public static class BasicEntry implements Int2IntMap.Entry {
      protected int key;
      protected int value;

      public BasicEntry() {
      }

      public BasicEntry(Integer key, Integer value) {
         this.key = key;
         this.value = value;
      }

      public BasicEntry(int key, int value) {
         this.key = key;
         this.value = value;
      }

      @Override
      public int getIntKey() {
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
         } else if (o instanceof Int2IntMap.Entry) {
            Int2IntMap.Entry e = (Int2IntMap.Entry)o;
            return this.key == e.getIntKey() && this.value == e.getIntValue();
         } else {
            Entry<?, ?> e = (Entry<?, ?>)o;
            Object key = e.getKey();
            if (key != null && key instanceof Integer) {
               Object value = e.getValue();
               return value != null && value instanceof Integer ? this.key == (Integer)key && this.value == (Integer)value : false;
            } else {
               return false;
            }
         }
      }

      @Override
      public int hashCode() {
         return this.key ^ this.value;
      }

      @Override
      public String toString() {
         return this.key + "->" + this.value;
      }
   }

   public abstract static class BasicEntrySet extends AbstractObjectSet<Int2IntMap.Entry> {
      protected final Int2IntMap map;

      public BasicEntrySet(Int2IntMap map) {
         this.map = map;
      }

      @Override
      public boolean contains(Object o) {
         if (!(o instanceof Entry)) {
            return false;
         } else if (o instanceof Int2IntMap.Entry) {
            Int2IntMap.Entry e = (Int2IntMap.Entry)o;
            int k = e.getIntKey();
            return this.map.containsKey(k) && this.map.get(k) == e.getIntValue();
         } else {
            Entry<?, ?> e = (Entry<?, ?>)o;
            Object key = e.getKey();
            if (key != null && key instanceof Integer) {
               int k = (Integer)key;
               Object value = e.getValue();
               return value != null && value instanceof Integer ? this.map.containsKey(k) && this.map.get(k) == (Integer)value : false;
            } else {
               return false;
            }
         }
      }

      @Override
      public boolean remove(Object o) {
         if (!(o instanceof Entry)) {
            return false;
         } else if (o instanceof Int2IntMap.Entry) {
            Int2IntMap.Entry e = (Int2IntMap.Entry)o;
            return this.map.remove(e.getIntKey(), e.getIntValue());
         } else {
            Entry<?, ?> e = (Entry<?, ?>)o;
            Object key = e.getKey();
            if (key != null && key instanceof Integer) {
               int k = (Integer)key;
               Object value = e.getValue();
               if (value != null && value instanceof Integer) {
                  int v = (Integer)value;
                  return this.map.remove(k, v);
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
         return this.map.size();
      }

      @Override
      public ObjectSpliterator<Int2IntMap.Entry> spliterator() {
         return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.map), 65);
      }
   }
}
