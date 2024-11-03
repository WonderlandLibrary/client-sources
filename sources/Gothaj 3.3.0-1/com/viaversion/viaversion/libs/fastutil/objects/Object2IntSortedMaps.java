package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.Object2IntSortedMaps.SynchronizedSortedMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntSortedMaps.UnmodifiableSortedMap;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public final class Object2IntSortedMaps {
   public static final Object2IntSortedMaps.EmptySortedMap EMPTY_MAP = new Object2IntSortedMaps.EmptySortedMap();

   private Object2IntSortedMaps() {
   }

   public static <K> Comparator<? super Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
      return (x, y) -> comparator.compare(x.getKey(), y.getKey());
   }

   public static <K> ObjectBidirectionalIterator<Object2IntMap.Entry<K>> fastIterator(Object2IntSortedMap<K> map) {
      ObjectSortedSet<Object2IntMap.Entry<K>> entries = map.object2IntEntrySet();
      return entries instanceof Object2IntSortedMap.FastSortedEntrySet ? ((Object2IntSortedMap.FastSortedEntrySet)entries).fastIterator() : entries.iterator();
   }

   public static <K> ObjectBidirectionalIterable<Object2IntMap.Entry<K>> fastIterable(Object2IntSortedMap<K> map) {
      ObjectSortedSet<Object2IntMap.Entry<K>> entries = map.object2IntEntrySet();
      return (ObjectBidirectionalIterable<Object2IntMap.Entry<K>>)(entries instanceof Object2IntSortedMap.FastSortedEntrySet
         ? ((Object2IntSortedMap.FastSortedEntrySet)entries)::fastIterator
         : entries);
   }

   public static <K> Object2IntSortedMap<K> emptyMap() {
      return EMPTY_MAP;
   }

   public static <K> Object2IntSortedMap<K> singleton(K key, Integer value) {
      return new Object2IntSortedMaps.Singleton<>(key, value);
   }

   public static <K> Object2IntSortedMap<K> singleton(K key, Integer value, Comparator<? super K> comparator) {
      return new Object2IntSortedMaps.Singleton<>(key, value, comparator);
   }

   public static <K> Object2IntSortedMap<K> singleton(K key, int value) {
      return new Object2IntSortedMaps.Singleton<>(key, value);
   }

   public static <K> Object2IntSortedMap<K> singleton(K key, int value, Comparator<? super K> comparator) {
      return new Object2IntSortedMaps.Singleton<>(key, value, comparator);
   }

   public static <K> Object2IntSortedMap<K> synchronize(Object2IntSortedMap<K> m) {
      return new SynchronizedSortedMap(m);
   }

   public static <K> Object2IntSortedMap<K> synchronize(Object2IntSortedMap<K> m, Object sync) {
      return new SynchronizedSortedMap(m, sync);
   }

   public static <K> Object2IntSortedMap<K> unmodifiable(Object2IntSortedMap<K> m) {
      return new UnmodifiableSortedMap(m);
   }

   public static class EmptySortedMap<K> extends Object2IntMaps.EmptyMap<K> implements Object2IntSortedMap<K>, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptySortedMap() {
      }

      @Override
      public Comparator<? super K> comparator() {
         return null;
      }

      @Override
      public ObjectSortedSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
         return ObjectSortedSets.EMPTY_SET;
      }

      @Deprecated
      @Override
      public ObjectSortedSet<Entry<K, Integer>> entrySet() {
         return ObjectSortedSets.EMPTY_SET;
      }

      @Override
      public ObjectSortedSet<K> keySet() {
         return ObjectSortedSets.EMPTY_SET;
      }

      @Override
      public Object2IntSortedMap<K> subMap(K from, K to) {
         return Object2IntSortedMaps.EMPTY_MAP;
      }

      @Override
      public Object2IntSortedMap<K> headMap(K to) {
         return Object2IntSortedMaps.EMPTY_MAP;
      }

      @Override
      public Object2IntSortedMap<K> tailMap(K from) {
         return Object2IntSortedMaps.EMPTY_MAP;
      }

      @Override
      public K firstKey() {
         throw new NoSuchElementException();
      }

      @Override
      public K lastKey() {
         throw new NoSuchElementException();
      }
   }

   public static class Singleton<K> extends Object2IntMaps.Singleton<K> implements Object2IntSortedMap<K>, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected final Comparator<? super K> comparator;

      protected Singleton(K key, int value, Comparator<? super K> comparator) {
         super(key, value);
         this.comparator = comparator;
      }

      protected Singleton(K key, int value) {
         this(key, value, null);
      }

      final int compare(K k1, K k2) {
         return this.comparator == null ? ((Comparable)k1).compareTo(k2) : this.comparator.compare(k1, k2);
      }

      @Override
      public Comparator<? super K> comparator() {
         return this.comparator;
      }

      @Override
      public ObjectSortedSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
         if (this.entries == null) {
            this.entries = ObjectSortedSets.singleton(
               new AbstractObject2IntMap.BasicEntry<>(this.key, this.value), Object2IntSortedMaps.entryComparator(this.comparator)
            );
         }

         return (ObjectSortedSet<Object2IntMap.Entry<K>>)this.entries;
      }

      @Deprecated
      @Override
      public ObjectSortedSet<Entry<K, Integer>> entrySet() {
         return this.object2IntEntrySet();
      }

      @Override
      public ObjectSortedSet<K> keySet() {
         if (this.keys == null) {
            this.keys = ObjectSortedSets.singleton(this.key, this.comparator);
         }

         return (ObjectSortedSet<K>)this.keys;
      }

      @Override
      public Object2IntSortedMap<K> subMap(K from, K to) {
         return (Object2IntSortedMap<K>)(this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0 ? this : Object2IntSortedMaps.EMPTY_MAP);
      }

      @Override
      public Object2IntSortedMap<K> headMap(K to) {
         return (Object2IntSortedMap<K>)(this.compare(this.key, to) < 0 ? this : Object2IntSortedMaps.EMPTY_MAP);
      }

      @Override
      public Object2IntSortedMap<K> tailMap(K from) {
         return (Object2IntSortedMap<K>)(this.compare(from, this.key) <= 0 ? this : Object2IntSortedMaps.EMPTY_MAP);
      }

      @Override
      public K firstKey() {
         return this.key;
      }

      @Override
      public K lastKey() {
         return this.key;
      }
   }
}
