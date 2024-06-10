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
/*     */ public final class Object2DoubleSortedMaps
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
/*     */   public static <K> ObjectBidirectionalIterator<Object2DoubleMap.Entry<K>> fastIterator(Object2DoubleSortedMap<K> map) {
/*  60 */     ObjectSortedSet<Object2DoubleMap.Entry<K>> entries = map.object2DoubleEntrySet();
/*  61 */     return (entries instanceof Object2DoubleSortedMap.FastSortedEntrySet) ? (
/*  62 */       (Object2DoubleSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static <K> ObjectBidirectionalIterable<Object2DoubleMap.Entry<K>> fastIterable(Object2DoubleSortedMap<K> map) {
/*  80 */     ObjectSortedSet<Object2DoubleMap.Entry<K>> entries = map.object2DoubleEntrySet();
/*     */     
/*  82 */     Objects.requireNonNull((Object2DoubleSortedMap.FastSortedEntrySet)entries); return (entries instanceof Object2DoubleSortedMap.FastSortedEntrySet) ? (Object2DoubleSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  83 */       entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<K>
/*     */     extends Object2DoubleMaps.EmptyMap<K>
/*     */     implements Object2DoubleSortedMap<K>, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Object2DoubleMap.Entry<K>> object2DoubleEntrySet() {
/* 107 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Double>> entrySet() {
/* 118 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 123 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2DoubleSortedMap<K> subMap(K from, K to) {
/* 128 */       return Object2DoubleSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2DoubleSortedMap<K> headMap(K to) {
/* 133 */       return Object2DoubleSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2DoubleSortedMap<K> tailMap(K from) {
/* 138 */       return Object2DoubleSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public K firstKey() {
/* 142 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public K lastKey() {
/* 146 */       throw new NoSuchElementException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 153 */   public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> Object2DoubleSortedMap<K> emptyMap() {
/* 164 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends Object2DoubleMaps.Singleton<K>
/*     */     implements Object2DoubleSortedMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final Comparator<? super K> comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(K key, double value, Comparator<? super K> comparator) {
/* 181 */       super(key, value);
/* 182 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(K key, double value) {
/* 185 */       this(key, value, (Comparator<? super K>)null);
/*     */     }
/*     */     
/*     */     final int compare(K k1, K k2) {
/* 189 */       return (this.comparator == null) ? ((Comparable<K>)k1).compareTo(k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 193 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Object2DoubleMap.Entry<K>> object2DoubleEntrySet() {
/* 198 */       if (this.entries == null)
/* 199 */         this.entries = ObjectSortedSets.singleton(new AbstractObject2DoubleMap.BasicEntry<>(this.key, this.value), 
/* 200 */             (Comparator)Object2DoubleSortedMaps.entryComparator(this.comparator)); 
/* 201 */       return (ObjectSortedSet<Object2DoubleMap.Entry<K>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Double>> entrySet() {
/* 212 */       return (ObjectSortedSet)object2DoubleEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 216 */       if (this.keys == null)
/* 217 */         this.keys = ObjectSortedSets.singleton(this.key, this.comparator); 
/* 218 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2DoubleSortedMap<K> subMap(K from, K to) {
/* 223 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 224 */         return this; 
/* 225 */       return Object2DoubleSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2DoubleSortedMap<K> headMap(K to) {
/* 230 */       if (compare(this.key, to) < 0)
/* 231 */         return this; 
/* 232 */       return Object2DoubleSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2DoubleSortedMap<K> tailMap(K from) {
/* 237 */       if (compare(from, this.key) <= 0)
/* 238 */         return this; 
/* 239 */       return Object2DoubleSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public K firstKey() {
/* 243 */       return this.key;
/*     */     }
/*     */     
/*     */     public K lastKey() {
/* 247 */       return this.key;
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
/*     */   public static <K> Object2DoubleSortedMap<K> singleton(K key, Double value) {
/* 266 */     return new Singleton<>(key, value.doubleValue());
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
/*     */   public static <K> Object2DoubleSortedMap<K> singleton(K key, Double value, Comparator<? super K> comparator) {
/* 286 */     return new Singleton<>(key, value.doubleValue(), comparator);
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
/*     */   public static <K> Object2DoubleSortedMap<K> singleton(K key, double value) {
/* 304 */     return new Singleton<>(key, value);
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
/*     */   public static <K> Object2DoubleSortedMap<K> singleton(K key, double value, Comparator<? super K> comparator) {
/* 325 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<K>
/*     */     extends Object2DoubleMaps.SynchronizedMap<K>
/*     */     implements Object2DoubleSortedMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2DoubleSortedMap<K> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Object2DoubleSortedMap<K> m, Object sync) {
/* 335 */       super(m, sync);
/* 336 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Object2DoubleSortedMap<K> m) {
/* 339 */       super(m);
/* 340 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 344 */       synchronized (this.sync) {
/* 345 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Object2DoubleMap.Entry<K>> object2DoubleEntrySet() {
/* 350 */       if (this.entries == null)
/* 351 */         this.entries = ObjectSortedSets.synchronize(this.sortedMap.object2DoubleEntrySet(), this.sync); 
/* 352 */       return (ObjectSortedSet<Object2DoubleMap.Entry<K>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Double>> entrySet() {
/* 363 */       return (ObjectSortedSet)object2DoubleEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 367 */       if (this.keys == null)
/* 368 */         this.keys = ObjectSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 369 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */     
/*     */     public Object2DoubleSortedMap<K> subMap(K from, K to) {
/* 373 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Object2DoubleSortedMap<K> headMap(K to) {
/* 377 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Object2DoubleSortedMap<K> tailMap(K from) {
/* 381 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public K firstKey() {
/* 385 */       synchronized (this.sync) {
/* 386 */         return this.sortedMap.firstKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public K lastKey() {
/* 391 */       synchronized (this.sync) {
/* 392 */         return this.sortedMap.lastKey();
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
/*     */   public static <K> Object2DoubleSortedMap<K> synchronize(Object2DoubleSortedMap<K> m) {
/* 406 */     return new SynchronizedSortedMap<>(m);
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
/*     */   public static <K> Object2DoubleSortedMap<K> synchronize(Object2DoubleSortedMap<K> m, Object sync) {
/* 421 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<K>
/*     */     extends Object2DoubleMaps.UnmodifiableMap<K>
/*     */     implements Object2DoubleSortedMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2DoubleSortedMap<K> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Object2DoubleSortedMap<K> m) {
/* 431 */       super(m);
/* 432 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 436 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Object2DoubleMap.Entry<K>> object2DoubleEntrySet() {
/* 440 */       if (this.entries == null)
/* 441 */         this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.object2DoubleEntrySet()); 
/* 442 */       return (ObjectSortedSet<Object2DoubleMap.Entry<K>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Double>> entrySet() {
/* 453 */       return (ObjectSortedSet)object2DoubleEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 457 */       if (this.keys == null)
/* 458 */         this.keys = ObjectSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 459 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */     
/*     */     public Object2DoubleSortedMap<K> subMap(K from, K to) {
/* 463 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Object2DoubleSortedMap<K> headMap(K to) {
/* 467 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Object2DoubleSortedMap<K> tailMap(K from) {
/* 471 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public K firstKey() {
/* 475 */       return this.sortedMap.firstKey();
/*     */     }
/*     */     
/*     */     public K lastKey() {
/* 479 */       return this.sortedMap.lastKey();
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
/*     */   public static <K> Object2DoubleSortedMap<K> unmodifiable(Object2DoubleSortedMap<K> m) {
/* 492 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2DoubleSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */