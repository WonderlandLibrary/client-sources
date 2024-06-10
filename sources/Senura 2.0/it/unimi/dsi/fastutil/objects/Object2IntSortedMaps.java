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
/*     */ public final class Object2IntSortedMaps
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
/*     */   public static <K> ObjectBidirectionalIterator<Object2IntMap.Entry<K>> fastIterator(Object2IntSortedMap<K> map) {
/*  59 */     ObjectSortedSet<Object2IntMap.Entry<K>> entries = map.object2IntEntrySet();
/*  60 */     return (entries instanceof Object2IntSortedMap.FastSortedEntrySet) ? (
/*  61 */       (Object2IntSortedMap.FastSortedEntrySet)entries).fastIterator() : 
/*  62 */       entries.iterator();
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
/*     */   public static <K> ObjectBidirectionalIterable<Object2IntMap.Entry<K>> fastIterable(Object2IntSortedMap<K> map) {
/*  78 */     ObjectSortedSet<Object2IntMap.Entry<K>> entries = map.object2IntEntrySet();
/*     */     
/*  80 */     Objects.requireNonNull((Object2IntSortedMap.FastSortedEntrySet)entries); return (entries instanceof Object2IntSortedMap.FastSortedEntrySet) ? (Object2IntSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  81 */       entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<K>
/*     */     extends Object2IntMaps.EmptyMap<K>
/*     */     implements Object2IntSortedMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 100 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
/* 105 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Integer>> entrySet() {
/* 116 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 121 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2IntSortedMap<K> subMap(K from, K to) {
/* 126 */       return Object2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2IntSortedMap<K> headMap(K to) {
/* 131 */       return Object2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2IntSortedMap<K> tailMap(K from) {
/* 136 */       return Object2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public K firstKey() {
/* 140 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public K lastKey() {
/* 144 */       throw new NoSuchElementException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 151 */   public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> Object2IntSortedMap<K> emptyMap() {
/* 162 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends Object2IntMaps.Singleton<K>
/*     */     implements Object2IntSortedMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final Comparator<? super K> comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(K key, int value, Comparator<? super K> comparator) {
/* 179 */       super(key, value);
/* 180 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(K key, int value) {
/* 183 */       this(key, value, (Comparator<? super K>)null);
/*     */     }
/*     */     
/*     */     final int compare(K k1, K k2) {
/* 187 */       return (this.comparator == null) ? ((Comparable<K>)k1).compareTo(k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 191 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
/* 196 */       if (this.entries == null)
/* 197 */         this.entries = ObjectSortedSets.singleton(new AbstractObject2IntMap.BasicEntry<>(this.key, this.value), 
/* 198 */             (Comparator)Object2IntSortedMaps.entryComparator(this.comparator)); 
/* 199 */       return (ObjectSortedSet<Object2IntMap.Entry<K>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Integer>> entrySet() {
/* 210 */       return (ObjectSortedSet)object2IntEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 214 */       if (this.keys == null)
/* 215 */         this.keys = ObjectSortedSets.singleton(this.key, this.comparator); 
/* 216 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2IntSortedMap<K> subMap(K from, K to) {
/* 221 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 222 */         return this; 
/* 223 */       return Object2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2IntSortedMap<K> headMap(K to) {
/* 228 */       if (compare(this.key, to) < 0)
/* 229 */         return this; 
/* 230 */       return Object2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2IntSortedMap<K> tailMap(K from) {
/* 235 */       if (compare(from, this.key) <= 0)
/* 236 */         return this; 
/* 237 */       return Object2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public K firstKey() {
/* 241 */       return this.key;
/*     */     }
/*     */     
/*     */     public K lastKey() {
/* 245 */       return this.key;
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
/*     */   public static <K> Object2IntSortedMap<K> singleton(K key, Integer value) {
/* 264 */     return new Singleton<>(key, value.intValue());
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
/*     */   public static <K> Object2IntSortedMap<K> singleton(K key, Integer value, Comparator<? super K> comparator) {
/* 284 */     return new Singleton<>(key, value.intValue(), comparator);
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
/*     */   public static <K> Object2IntSortedMap<K> singleton(K key, int value) {
/* 302 */     return new Singleton<>(key, value);
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
/*     */   public static <K> Object2IntSortedMap<K> singleton(K key, int value, Comparator<? super K> comparator) {
/* 322 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<K>
/*     */     extends Object2IntMaps.SynchronizedMap<K>
/*     */     implements Object2IntSortedMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2IntSortedMap<K> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Object2IntSortedMap<K> m, Object sync) {
/* 332 */       super(m, sync);
/* 333 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Object2IntSortedMap<K> m) {
/* 336 */       super(m);
/* 337 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 341 */       synchronized (this.sync) {
/* 342 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
/* 347 */       if (this.entries == null)
/* 348 */         this.entries = ObjectSortedSets.synchronize(this.sortedMap.object2IntEntrySet(), this.sync); 
/* 349 */       return (ObjectSortedSet<Object2IntMap.Entry<K>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Integer>> entrySet() {
/* 360 */       return (ObjectSortedSet)object2IntEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 364 */       if (this.keys == null)
/* 365 */         this.keys = ObjectSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 366 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */     
/*     */     public Object2IntSortedMap<K> subMap(K from, K to) {
/* 370 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Object2IntSortedMap<K> headMap(K to) {
/* 374 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Object2IntSortedMap<K> tailMap(K from) {
/* 378 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public K firstKey() {
/* 382 */       synchronized (this.sync) {
/* 383 */         return this.sortedMap.firstKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public K lastKey() {
/* 388 */       synchronized (this.sync) {
/* 389 */         return this.sortedMap.lastKey();
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
/*     */   public static <K> Object2IntSortedMap<K> synchronize(Object2IntSortedMap<K> m) {
/* 403 */     return new SynchronizedSortedMap<>(m);
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
/*     */   public static <K> Object2IntSortedMap<K> synchronize(Object2IntSortedMap<K> m, Object sync) {
/* 418 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<K>
/*     */     extends Object2IntMaps.UnmodifiableMap<K>
/*     */     implements Object2IntSortedMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2IntSortedMap<K> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Object2IntSortedMap<K> m) {
/* 428 */       super(m);
/* 429 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 433 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
/* 437 */       if (this.entries == null)
/* 438 */         this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.object2IntEntrySet()); 
/* 439 */       return (ObjectSortedSet<Object2IntMap.Entry<K>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Integer>> entrySet() {
/* 450 */       return (ObjectSortedSet)object2IntEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 454 */       if (this.keys == null)
/* 455 */         this.keys = ObjectSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 456 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */     
/*     */     public Object2IntSortedMap<K> subMap(K from, K to) {
/* 460 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Object2IntSortedMap<K> headMap(K to) {
/* 464 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Object2IntSortedMap<K> tailMap(K from) {
/* 468 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public K firstKey() {
/* 472 */       return this.sortedMap.firstKey();
/*     */     }
/*     */     
/*     */     public K lastKey() {
/* 476 */       return this.sortedMap.lastKey();
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
/*     */   public static <K> Object2IntSortedMap<K> unmodifiable(Object2IntSortedMap<K> m) {
/* 489 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2IntSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */