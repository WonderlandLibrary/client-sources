package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;
import java.util.Map.Entry;

public interface Int2IntSortedMap extends Int2IntMap, SortedMap<Integer, Integer> {
   Int2IntSortedMap subMap(int var1, int var2);

   Int2IntSortedMap headMap(int var1);

   Int2IntSortedMap tailMap(int var1);

   int firstIntKey();

   int lastIntKey();

   @Deprecated
   default Int2IntSortedMap subMap(Integer from, Integer to) {
      return this.subMap(from.intValue(), to.intValue());
   }

   @Deprecated
   default Int2IntSortedMap headMap(Integer to) {
      return this.headMap(to.intValue());
   }

   @Deprecated
   default Int2IntSortedMap tailMap(Integer from) {
      return this.tailMap(from.intValue());
   }

   @Deprecated
   default Integer firstKey() {
      return this.firstIntKey();
   }

   @Deprecated
   default Integer lastKey() {
      return this.lastIntKey();
   }

   @Deprecated
   default ObjectSortedSet<Entry<Integer, Integer>> entrySet() {
      return this.int2IntEntrySet();
   }

   ObjectSortedSet<Int2IntMap.Entry> int2IntEntrySet();

   IntSortedSet keySet();

   @Override
   IntCollection values();

   IntComparator comparator();

   public interface FastSortedEntrySet extends ObjectSortedSet<Int2IntMap.Entry>, Int2IntMap.FastEntrySet {
      ObjectBidirectionalIterator<Int2IntMap.Entry> fastIterator();

      ObjectBidirectionalIterator<Int2IntMap.Entry> fastIterator(Int2IntMap.Entry var1);
   }
}
