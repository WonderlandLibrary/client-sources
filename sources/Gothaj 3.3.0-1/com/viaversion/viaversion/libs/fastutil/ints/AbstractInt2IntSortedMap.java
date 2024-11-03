package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;

public abstract class AbstractInt2IntSortedMap extends AbstractInt2IntMap implements Int2IntSortedMap {
   private static final long serialVersionUID = -1773560792952436569L;

   protected AbstractInt2IntSortedMap() {
   }

   @Override
   public IntSortedSet keySet() {
      return new AbstractInt2IntSortedMap.KeySet();
   }

   @Override
   public IntCollection values() {
      return new AbstractInt2IntSortedMap.ValuesCollection();
   }

   protected class KeySet extends AbstractIntSortedSet {
      @Override
      public boolean contains(int k) {
         return AbstractInt2IntSortedMap.this.containsKey(k);
      }

      @Override
      public int size() {
         return AbstractInt2IntSortedMap.this.size();
      }

      @Override
      public void clear() {
         AbstractInt2IntSortedMap.this.clear();
      }

      @Override
      public IntComparator comparator() {
         return AbstractInt2IntSortedMap.this.comparator();
      }

      @Override
      public int firstInt() {
         return AbstractInt2IntSortedMap.this.firstIntKey();
      }

      @Override
      public int lastInt() {
         return AbstractInt2IntSortedMap.this.lastIntKey();
      }

      @Override
      public IntSortedSet headSet(int to) {
         return AbstractInt2IntSortedMap.this.headMap(to).keySet();
      }

      @Override
      public IntSortedSet tailSet(int from) {
         return AbstractInt2IntSortedMap.this.tailMap(from).keySet();
      }

      @Override
      public IntSortedSet subSet(int from, int to) {
         return AbstractInt2IntSortedMap.this.subMap(from, to).keySet();
      }

      @Override
      public IntBidirectionalIterator iterator(int from) {
         return new AbstractInt2IntSortedMap.KeySetIterator(
            AbstractInt2IntSortedMap.this.int2IntEntrySet().iterator(new AbstractInt2IntMap.BasicEntry(from, 0))
         );
      }

      @Override
      public IntBidirectionalIterator iterator() {
         return new AbstractInt2IntSortedMap.KeySetIterator(Int2IntSortedMaps.fastIterator(AbstractInt2IntSortedMap.this));
      }
   }

   protected static class KeySetIterator implements IntBidirectionalIterator {
      protected final ObjectBidirectionalIterator<Int2IntMap.Entry> i;

      public KeySetIterator(ObjectBidirectionalIterator<Int2IntMap.Entry> i) {
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

   protected class ValuesCollection extends AbstractIntCollection {
      @Override
      public IntIterator iterator() {
         return new AbstractInt2IntSortedMap.ValuesIterator(Int2IntSortedMaps.fastIterator(AbstractInt2IntSortedMap.this));
      }

      @Override
      public boolean contains(int k) {
         return AbstractInt2IntSortedMap.this.containsValue(k);
      }

      @Override
      public int size() {
         return AbstractInt2IntSortedMap.this.size();
      }

      @Override
      public void clear() {
         AbstractInt2IntSortedMap.this.clear();
      }
   }

   protected static class ValuesIterator implements IntIterator {
      protected final ObjectBidirectionalIterator<Int2IntMap.Entry> i;

      public ValuesIterator(ObjectBidirectionalIterator<Int2IntMap.Entry> i) {
         this.i = i;
      }

      @Override
      public int nextInt() {
         return this.i.next().getIntValue();
      }

      @Override
      public boolean hasNext() {
         return this.i.hasNext();
      }
   }
}
