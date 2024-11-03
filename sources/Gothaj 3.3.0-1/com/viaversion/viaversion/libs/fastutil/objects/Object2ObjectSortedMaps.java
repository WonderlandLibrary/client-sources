package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMaps.SynchronizedSortedMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMaps.UnmodifiableSortedMap;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

public final class Object2ObjectSortedMaps {
   public static final Object2ObjectSortedMaps.EmptySortedMap EMPTY_MAP = new Object2ObjectSortedMaps.EmptySortedMap();

   private Object2ObjectSortedMaps() {
   }

   public static <K> Comparator<? super Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
      return (x, y) -> comparator.compare(x.getKey(), y.getKey());
   }

   public static <K, V> ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> fastIterator(Object2ObjectSortedMap<K, V> map) {
      ObjectSortedSet<Object2ObjectMap.Entry<K, V>> entries = map.object2ObjectEntrySet();
      return entries instanceof Object2ObjectSortedMap.FastSortedEntrySet
         ? ((Object2ObjectSortedMap.FastSortedEntrySet)entries).fastIterator()
         : entries.iterator();
   }

   public static <K, V> ObjectBidirectionalIterable<Object2ObjectMap.Entry<K, V>> fastIterable(Object2ObjectSortedMap<K, V> map) {
      ObjectSortedSet<Object2ObjectMap.Entry<K, V>> entries = map.object2ObjectEntrySet();
      return (ObjectBidirectionalIterable<Object2ObjectMap.Entry<K, V>>)(entries instanceof Object2ObjectSortedMap.FastSortedEntrySet
         ? ((Object2ObjectSortedMap.FastSortedEntrySet)entries)::fastIterator
         : entries);
   }

   public static <K, V> Object2ObjectSortedMap<K, V> emptyMap() {
      return EMPTY_MAP;
   }

   public static <K, V> Object2ObjectSortedMap<K, V> singleton(K key, V value) {
      return new Object2ObjectSortedMaps.Singleton<>(key, value);
   }

   public static <K, V> Object2ObjectSortedMap<K, V> singleton(K key, V value, Comparator<? super K> comparator) {
      return new Object2ObjectSortedMaps.Singleton<>(key, value, comparator);
   }

   public static <K, V> Object2ObjectSortedMap<K, V> synchronize(Object2ObjectSortedMap<K, V> m) {
      return new SynchronizedSortedMap(m);
   }

   public static <K, V> Object2ObjectSortedMap<K, V> synchronize(Object2ObjectSortedMap<K, V> m, Object sync) {
      return new SynchronizedSortedMap(m, sync);
   }

   public static <K, V> Object2ObjectSortedMap<K, V> unmodifiable(Object2ObjectSortedMap<K, ? extends V> m) {
      return new UnmodifiableSortedMap(m);
   }

   public static class EmptySortedMap<K, V> extends Object2ObjectMaps.EmptyMap<K, V> implements Object2ObjectSortedMap<K, V>, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptySortedMap() {
      }

      @Override
      public Comparator<? super K> comparator() {
         return null;
      }

      @Override
      public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
         return ObjectSortedSets.EMPTY_SET;
      }

      @Override
      public ObjectSortedSet<Entry<K, V>> entrySet() {
         return ObjectSortedSets.EMPTY_SET;
      }

      @Override
      public ObjectSortedSet<K> keySet() {
         return ObjectSortedSets.EMPTY_SET;
      }

      @Override
      public Object2ObjectSortedMap<K, V> subMap(K from, K to) {
         return Object2ObjectSortedMaps.EMPTY_MAP;
      }

      @Override
      public Object2ObjectSortedMap<K, V> headMap(K to) {
         return Object2ObjectSortedMaps.EMPTY_MAP;
      }

      @Override
      public Object2ObjectSortedMap<K, V> tailMap(K from) {
         return Object2ObjectSortedMaps.EMPTY_MAP;
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

   public static class Singleton<K, V> extends Object2ObjectMaps.Singleton<K, V> implements Object2ObjectSortedMap<K, V>, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected final Comparator<? super K> comparator;

      protected Singleton(K key, V value, Comparator<? super K> comparator) {
         super(key, value);
         this.comparator = comparator;
      }

      protected Singleton(K key, V value) {
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
      public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
         if (this.entries == null) {
            this.entries = ObjectSortedSets.singleton(
               new AbstractObject2ObjectMap.BasicEntry<>(this.key, this.value),
               (Comparator<? super AbstractObject2ObjectMap.BasicEntry<K, V>>)Object2ObjectSortedMaps.entryComparator(this.comparator)
            );
         }

         return (ObjectSortedSet<Object2ObjectMap.Entry<K, V>>)this.entries;
      }

      @Override
      public ObjectSortedSet<Entry<K, V>> entrySet() {
         return this.object2ObjectEntrySet();
      }

      @Override
      public ObjectSortedSet<K> keySet() {
         if (this.keys == null) {
            this.keys = ObjectSortedSets.singleton(this.key, this.comparator);
         }

         return (ObjectSortedSet<K>)this.keys;
      }

      @Override
      public Object2ObjectSortedMap<K, V> subMap(K from, K to) {
         return (Object2ObjectSortedMap<K, V>)(this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0 ? this : Object2ObjectSortedMaps.EMPTY_MAP);
      }

      @Override
      public Object2ObjectSortedMap<K, V> headMap(K to) {
         return (Object2ObjectSortedMap<K, V>)(this.compare(this.key, to) < 0 ? this : Object2ObjectSortedMaps.EMPTY_MAP);
      }

      @Override
      public Object2ObjectSortedMap<K, V> tailMap(K from) {
         return (Object2ObjectSortedMap<K, V>)(this.compare(from, this.key) <= 0 ? this : Object2ObjectSortedMaps.EMPTY_MAP);
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
