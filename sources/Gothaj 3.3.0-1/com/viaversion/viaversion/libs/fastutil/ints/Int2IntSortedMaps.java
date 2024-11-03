package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMaps.SynchronizedSortedMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMaps.UnmodifiableSortedMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterable;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public final class Int2IntSortedMaps {
   public static final Int2IntSortedMaps.EmptySortedMap EMPTY_MAP = new Int2IntSortedMaps.EmptySortedMap();

   private Int2IntSortedMaps() {
   }

   public static Comparator<? super Entry<Integer, ?>> entryComparator(IntComparator comparator) {
      return (x, y) -> comparator.compare(x.getKey().intValue(), y.getKey().intValue());
   }

   public static ObjectBidirectionalIterator<Int2IntMap.Entry> fastIterator(Int2IntSortedMap map) {
      ObjectSortedSet<Int2IntMap.Entry> entries = map.int2IntEntrySet();
      return entries instanceof Int2IntSortedMap.FastSortedEntrySet ? ((Int2IntSortedMap.FastSortedEntrySet)entries).fastIterator() : entries.iterator();
   }

   public static ObjectBidirectionalIterable<Int2IntMap.Entry> fastIterable(Int2IntSortedMap map) {
      ObjectSortedSet<Int2IntMap.Entry> entries = map.int2IntEntrySet();
      return (ObjectBidirectionalIterable<Int2IntMap.Entry>)(entries instanceof Int2IntSortedMap.FastSortedEntrySet
         ? ((Int2IntSortedMap.FastSortedEntrySet)entries)::fastIterator
         : entries);
   }

   public static Int2IntSortedMap singleton(Integer key, Integer value) {
      return new Int2IntSortedMaps.Singleton(key, value);
   }

   public static Int2IntSortedMap singleton(Integer key, Integer value, IntComparator comparator) {
      return new Int2IntSortedMaps.Singleton(key, value, comparator);
   }

   public static Int2IntSortedMap singleton(int key, int value) {
      return new Int2IntSortedMaps.Singleton(key, value);
   }

   public static Int2IntSortedMap singleton(int key, int value, IntComparator comparator) {
      return new Int2IntSortedMaps.Singleton(key, value, comparator);
   }

   public static Int2IntSortedMap synchronize(Int2IntSortedMap m) {
      return new SynchronizedSortedMap(m);
   }

   public static Int2IntSortedMap synchronize(Int2IntSortedMap m, Object sync) {
      return new SynchronizedSortedMap(m, sync);
   }

   public static Int2IntSortedMap unmodifiable(Int2IntSortedMap m) {
      return new UnmodifiableSortedMap(m);
   }

   public static class EmptySortedMap extends Int2IntMaps.EmptyMap implements Int2IntSortedMap, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptySortedMap() {
      }

      @Override
      public IntComparator comparator() {
         return null;
      }

      @Override
      public ObjectSortedSet<Int2IntMap.Entry> int2IntEntrySet() {
         return ObjectSortedSets.EMPTY_SET;
      }

      @Deprecated
      @Override
      public ObjectSortedSet<Entry<Integer, Integer>> entrySet() {
         return ObjectSortedSets.EMPTY_SET;
      }

      @Override
      public IntSortedSet keySet() {
         return IntSortedSets.EMPTY_SET;
      }

      @Override
      public Int2IntSortedMap subMap(int from, int to) {
         return Int2IntSortedMaps.EMPTY_MAP;
      }

      @Override
      public Int2IntSortedMap headMap(int to) {
         return Int2IntSortedMaps.EMPTY_MAP;
      }

      @Override
      public Int2IntSortedMap tailMap(int from) {
         return Int2IntSortedMaps.EMPTY_MAP;
      }

      @Override
      public int firstIntKey() {
         throw new NoSuchElementException();
      }

      @Override
      public int lastIntKey() {
         throw new NoSuchElementException();
      }

      @Deprecated
      @Override
      public Int2IntSortedMap headMap(Integer oto) {
         return this.headMap(oto.intValue());
      }

      @Deprecated
      @Override
      public Int2IntSortedMap tailMap(Integer ofrom) {
         return this.tailMap(ofrom.intValue());
      }

      @Deprecated
      @Override
      public Int2IntSortedMap subMap(Integer ofrom, Integer oto) {
         return this.subMap(ofrom.intValue(), oto.intValue());
      }

      @Deprecated
      @Override
      public Integer firstKey() {
         return this.firstIntKey();
      }

      @Deprecated
      @Override
      public Integer lastKey() {
         return this.lastIntKey();
      }
   }

   public static class Singleton extends Int2IntMaps.Singleton implements Int2IntSortedMap, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected final IntComparator comparator;

      protected Singleton(int key, int value, IntComparator comparator) {
         super(key, value);
         this.comparator = comparator;
      }

      protected Singleton(int key, int value) {
         this(key, value, null);
      }

      final int compare(int k1, int k2) {
         return this.comparator == null ? Integer.compare(k1, k2) : this.comparator.compare(k1, k2);
      }

      @Override
      public IntComparator comparator() {
         return this.comparator;
      }

      @Override
      public ObjectSortedSet<Int2IntMap.Entry> int2IntEntrySet() {
         if (this.entries == null) {
            this.entries = ObjectSortedSets.singleton(
               new AbstractInt2IntMap.BasicEntry(this.key, this.value), Int2IntSortedMaps.entryComparator(this.comparator)
            );
         }

         return (ObjectSortedSet<Int2IntMap.Entry>)this.entries;
      }

      @Deprecated
      @Override
      public ObjectSortedSet<Entry<Integer, Integer>> entrySet() {
         return this.int2IntEntrySet();
      }

      @Override
      public IntSortedSet keySet() {
         if (this.keys == null) {
            this.keys = IntSortedSets.singleton(this.key, this.comparator);
         }

         return (IntSortedSet)this.keys;
      }

      @Override
      public Int2IntSortedMap subMap(int from, int to) {
         return (Int2IntSortedMap)(this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0 ? this : Int2IntSortedMaps.EMPTY_MAP);
      }

      @Override
      public Int2IntSortedMap headMap(int to) {
         return (Int2IntSortedMap)(this.compare(this.key, to) < 0 ? this : Int2IntSortedMaps.EMPTY_MAP);
      }

      @Override
      public Int2IntSortedMap tailMap(int from) {
         return (Int2IntSortedMap)(this.compare(from, this.key) <= 0 ? this : Int2IntSortedMaps.EMPTY_MAP);
      }

      @Override
      public int firstIntKey() {
         return this.key;
      }

      @Override
      public int lastIntKey() {
         return this.key;
      }

      @Deprecated
      @Override
      public Int2IntSortedMap headMap(Integer oto) {
         return this.headMap(oto.intValue());
      }

      @Deprecated
      @Override
      public Int2IntSortedMap tailMap(Integer ofrom) {
         return this.tailMap(ofrom.intValue());
      }

      @Deprecated
      @Override
      public Int2IntSortedMap subMap(Integer ofrom, Integer oto) {
         return this.subMap(ofrom.intValue(), oto.intValue());
      }

      @Deprecated
      @Override
      public Integer firstKey() {
         return this.firstIntKey();
      }

      @Deprecated
      @Override
      public Integer lastKey() {
         return this.lastIntKey();
      }
   }
}
