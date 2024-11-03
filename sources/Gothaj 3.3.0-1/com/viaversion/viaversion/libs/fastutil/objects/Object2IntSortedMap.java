package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.Map.Entry;

public interface Object2IntSortedMap<K> extends Object2IntMap<K>, SortedMap<K, Integer> {
   Object2IntSortedMap<K> subMap(K var1, K var2);

   Object2IntSortedMap<K> headMap(K var1);

   Object2IntSortedMap<K> tailMap(K var1);

   @Deprecated
   default ObjectSortedSet<Entry<K, Integer>> entrySet() {
      return this.object2IntEntrySet();
   }

   ObjectSortedSet<Object2IntMap.Entry<K>> object2IntEntrySet();

   ObjectSortedSet<K> keySet();

   @Override
   IntCollection values();

   @Override
   Comparator<? super K> comparator();

   public interface FastSortedEntrySet<K> extends ObjectSortedSet<Object2IntMap.Entry<K>>, Object2IntMap.FastEntrySet<K> {
      ObjectBidirectionalIterator<Object2IntMap.Entry<K>> fastIterator();

      ObjectBidirectionalIterator<Object2IntMap.Entry<K>> fastIterator(Object2IntMap.Entry<K> var1);
   }
}
