/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Object2ReferenceSortedMaps
/*     */ {
/*     */   public static <K> Comparator<? super Map.Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
/*  43 */     return (x, y) -> comparator.compare(x.getKey(), y.getKey());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ObjectBidirectionalIterator<Object2ReferenceMap.Entry<K, V>> fastIterator(Object2ReferenceSortedMap<K, V> map) {
/*  60 */     ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> entries = map.object2ReferenceEntrySet();
/*  61 */     return (entries instanceof Object2ReferenceSortedMap.FastSortedEntrySet) ? (
/*  62 */       (Object2ReferenceSortedMap.FastSortedEntrySet)entries).fastIterator() : 
/*  63 */       entries.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ObjectBidirectionalIterable<Object2ReferenceMap.Entry<K, V>> fastIterable(Object2ReferenceSortedMap<K, V> map) {
/*  80 */     ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> entries = map.object2ReferenceEntrySet();
/*     */     
/*  82 */     Objects.requireNonNull((Object2ReferenceSortedMap.FastSortedEntrySet)entries); return (entries instanceof Object2ReferenceSortedMap.FastSortedEntrySet) ? (Object2ReferenceSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  83 */       entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<K, V>
/*     */     extends Object2ReferenceMaps.EmptyMap<K, V>
/*     */     implements Object2ReferenceSortedMap<K, V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 102 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> object2ReferenceEntrySet() {
/* 107 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
/* 113 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 118 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> subMap(K from, K to) {
/* 123 */       return Object2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> headMap(K to) {
/* 128 */       return Object2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> tailMap(K from) {
/* 133 */       return Object2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public K firstKey() {
/* 137 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public K lastKey() {
/* 141 */       throw new NoSuchElementException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 148 */   public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> Object2ReferenceSortedMap<K, V> emptyMap() {
/* 159 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K, V>
/*     */     extends Object2ReferenceMaps.Singleton<K, V>
/*     */     implements Object2ReferenceSortedMap<K, V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final Comparator<? super K> comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(K key, V value, Comparator<? super K> comparator) {
/* 176 */       super(key, value);
/* 177 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(K key, V value) {
/* 180 */       this(key, value, (Comparator<? super K>)null);
/*     */     }
/*     */     
/*     */     final int compare(K k1, K k2) {
/* 184 */       return (this.comparator == null) ? ((Comparable<K>)k1).compareTo(k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 188 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> object2ReferenceEntrySet() {
/* 193 */       if (this.entries == null)
/* 194 */         this.entries = ObjectSortedSets.singleton(new AbstractObject2ReferenceMap.BasicEntry<>(this.key, this.value), 
/* 195 */             (Comparator)Object2ReferenceSortedMaps.entryComparator(this.comparator)); 
/* 196 */       return (ObjectSortedSet<Object2ReferenceMap.Entry<K, V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
/* 202 */       return (ObjectSortedSet)object2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 206 */       if (this.keys == null)
/* 207 */         this.keys = ObjectSortedSets.singleton(this.key, this.comparator); 
/* 208 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> subMap(K from, K to) {
/* 213 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 214 */         return this; 
/* 215 */       return Object2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> headMap(K to) {
/* 220 */       if (compare(this.key, to) < 0)
/* 221 */         return this; 
/* 222 */       return Object2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> tailMap(K from) {
/* 227 */       if (compare(from, this.key) <= 0)
/* 228 */         return this; 
/* 229 */       return Object2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public K firstKey() {
/* 233 */       return this.key;
/*     */     }
/*     */     
/*     */     public K lastKey() {
/* 237 */       return this.key;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> Object2ReferenceSortedMap<K, V> singleton(K key, V value) {
/* 256 */     return new Singleton<>(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> Object2ReferenceSortedMap<K, V> singleton(K key, V value, Comparator<? super K> comparator) {
/* 277 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<K, V>
/*     */     extends Object2ReferenceMaps.SynchronizedMap<K, V>
/*     */     implements Object2ReferenceSortedMap<K, V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ReferenceSortedMap<K, V> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Object2ReferenceSortedMap<K, V> m, Object sync) {
/* 287 */       super(m, sync);
/* 288 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Object2ReferenceSortedMap<K, V> m) {
/* 291 */       super(m);
/* 292 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 296 */       synchronized (this.sync) {
/* 297 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> object2ReferenceEntrySet() {
/* 302 */       if (this.entries == null)
/* 303 */         this.entries = ObjectSortedSets.synchronize(this.sortedMap.object2ReferenceEntrySet(), this.sync); 
/* 304 */       return (ObjectSortedSet<Object2ReferenceMap.Entry<K, V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
/* 310 */       return (ObjectSortedSet)object2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 314 */       if (this.keys == null)
/* 315 */         this.keys = ObjectSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 316 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> subMap(K from, K to) {
/* 320 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> headMap(K to) {
/* 324 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> tailMap(K from) {
/* 328 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public K firstKey() {
/* 332 */       synchronized (this.sync) {
/* 333 */         return this.sortedMap.firstKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public K lastKey() {
/* 338 */       synchronized (this.sync) {
/* 339 */         return this.sortedMap.lastKey();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> Object2ReferenceSortedMap<K, V> synchronize(Object2ReferenceSortedMap<K, V> m) {
/* 353 */     return new SynchronizedSortedMap<>(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> Object2ReferenceSortedMap<K, V> synchronize(Object2ReferenceSortedMap<K, V> m, Object sync) {
/* 369 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<K, V>
/*     */     extends Object2ReferenceMaps.UnmodifiableMap<K, V>
/*     */     implements Object2ReferenceSortedMap<K, V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ReferenceSortedMap<K, V> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Object2ReferenceSortedMap<K, V> m) {
/* 379 */       super(m);
/* 380 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 384 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> object2ReferenceEntrySet() {
/* 388 */       if (this.entries == null)
/* 389 */         this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.object2ReferenceEntrySet()); 
/* 390 */       return (ObjectSortedSet<Object2ReferenceMap.Entry<K, V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
/* 396 */       return (ObjectSortedSet)object2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 400 */       if (this.keys == null)
/* 401 */         this.keys = ObjectSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 402 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> subMap(K from, K to) {
/* 406 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> headMap(K to) {
/* 410 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> tailMap(K from) {
/* 414 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public K firstKey() {
/* 418 */       return this.sortedMap.firstKey();
/*     */     }
/*     */     
/*     */     public K lastKey() {
/* 422 */       return this.sortedMap.lastKey();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> Object2ReferenceSortedMap<K, V> unmodifiable(Object2ReferenceSortedMap<K, V> m) {
/* 435 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ReferenceSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */