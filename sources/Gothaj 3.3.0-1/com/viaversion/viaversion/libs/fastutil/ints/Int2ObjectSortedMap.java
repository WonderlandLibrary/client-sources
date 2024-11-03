package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;
import java.util.Map.Entry;

public interface Int2ObjectSortedMap<V> extends Int2ObjectMap<V>, SortedMap<Integer, V> {
   Int2ObjectSortedMap<V> subMap(int var1, int var2);

   Int2ObjectSortedMap<V> headMap(int var1);

   Int2ObjectSortedMap<V> tailMap(int var1);

   int firstIntKey();

   int lastIntKey();

   @Deprecated
   default Int2ObjectSortedMap<V> subMap(Integer from, Integer to) {
      return this.subMap(from.intValue(), to.intValue());
   }

   @Deprecated
   default Int2ObjectSortedMap<V> headMap(Integer to) {
      return this.headMap(to.intValue());
   }

   @Deprecated
   default Int2ObjectSortedMap<V> tailMap(Integer from) {
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
   default ObjectSortedSet<Entry<Integer, V>> entrySet() {
      return this.int2ObjectEntrySet();
   }

   ObjectSortedSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet();

   IntSortedSet keySet();

   @Override
   ObjectCollection<V> values();

   IntComparator comparator();

   public interface FastSortedEntrySet<V> extends ObjectSortedSet<Int2ObjectMap.Entry<V>>, Int2ObjectMap.FastEntrySet<V> {
      ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> fastIterator();

      ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> fastIterator(Int2ObjectMap.Entry<V> var1);
   }
}
