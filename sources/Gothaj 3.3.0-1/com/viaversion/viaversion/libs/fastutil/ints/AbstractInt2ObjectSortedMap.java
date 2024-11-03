package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;

public abstract class AbstractInt2ObjectSortedMap<V> extends AbstractInt2ObjectMap<V> implements Int2ObjectSortedMap<V> {
   private static final long serialVersionUID = -1773560792952436569L;

   protected AbstractInt2ObjectSortedMap() {
   }

   @Override
   public IntSortedSet keySet() {
      return new AbstractInt2ObjectSortedMap.KeySet();
   }

   @Override
   public ObjectCollection<V> values() {
      return new AbstractInt2ObjectSortedMap.ValuesCollection();
   }

   protected class KeySet extends AbstractIntSortedSet {
      @Override
      public boolean contains(int k) {
         return AbstractInt2ObjectSortedMap.this.containsKey(k);
      }

      @Override
      public int size() {
         return AbstractInt2ObjectSortedMap.this.size();
      }

      @Override
      public void clear() {
         AbstractInt2ObjectSortedMap.this.clear();
      }

      @Override
      public IntComparator comparator() {
         return AbstractInt2ObjectSortedMap.this.comparator();
      }

      @Override
      public int firstInt() {
         return AbstractInt2ObjectSortedMap.this.firstIntKey();
      }

      @Override
      public int lastInt() {
         return AbstractInt2ObjectSortedMap.this.lastIntKey();
      }

      @Override
      public IntSortedSet headSet(int to) {
         return AbstractInt2ObjectSortedMap.this.headMap(to).keySet();
      }

      @Override
      public IntSortedSet tailSet(int from) {
         return AbstractInt2ObjectSortedMap.this.tailMap(from).keySet();
      }

      @Override
      public IntSortedSet subSet(int from, int to) {
         return AbstractInt2ObjectSortedMap.this.subMap(from, to).keySet();
      }

      @Override
      public IntBidirectionalIterator iterator(int from) {
         return new AbstractInt2ObjectSortedMap.KeySetIterator<>(
            AbstractInt2ObjectSortedMap.this.int2ObjectEntrySet().iterator(new AbstractInt2ObjectMap.BasicEntry<>(from, null))
         );
      }

      @Override
      public IntBidirectionalIterator iterator() {
         return new AbstractInt2ObjectSortedMap.KeySetIterator<>(Int2ObjectSortedMaps.fastIterator(AbstractInt2ObjectSortedMap.this));
      }
   }

   protected static class KeySetIterator<V> implements IntBidirectionalIterator {
      protected final ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> i;

      public KeySetIterator(ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> i) {
         this.i = i;
      }

      @Override
      public int nextInt() {
         return this.i.next().getIntKey();
      }

      @Override
      public int previousInt() {
         return this.i.previous().getIntKey();
      }

      @Override
      public boolean hasNext() {
         return this.i.hasNext();
      }

      @Override
      public boolean hasPrevious() {
         return this.i.hasPrevious();
      }
   }

   protected class ValuesCollection extends AbstractObjectCollection<V> {
      @Override
      public ObjectIterator<V> iterator() {
         return new AbstractInt2ObjectSortedMap.ValuesIterator<>(Int2ObjectSortedMaps.fastIterator(AbstractInt2ObjectSortedMap.this));
      }

      @Override
      public boolean contains(Object k) {
         return AbstractInt2ObjectSortedMap.this.containsValue(k);
      }

      @Override
      public int size() {
         return AbstractInt2ObjectSortedMap.this.size();
      }

      @Override
      public void clear() {
         AbstractInt2ObjectSortedMap.this.clear();
      }
   }

   protected static class ValuesIterator<V> implements ObjectIterator<V> {
      protected final ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> i;

      public ValuesIterator(ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> i) {
         this.i = i;
      }

      @Override
      public V next() {
         return this.i.next().getValue();
      }

      @Override
      public boolean hasNext() {
         return this.i.hasNext();
      }
   }
}
