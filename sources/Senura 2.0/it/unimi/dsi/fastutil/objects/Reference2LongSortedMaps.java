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
/*     */ public final class Reference2LongSortedMaps
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
/*     */   public static <K> ObjectBidirectionalIterator<Reference2LongMap.Entry<K>> fastIterator(Reference2LongSortedMap<K> map) {
/*  60 */     ObjectSortedSet<Reference2LongMap.Entry<K>> entries = map.reference2LongEntrySet();
/*  61 */     return (entries instanceof Reference2LongSortedMap.FastSortedEntrySet) ? (
/*  62 */       (Reference2LongSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static <K> ObjectBidirectionalIterable<Reference2LongMap.Entry<K>> fastIterable(Reference2LongSortedMap<K> map) {
/*  80 */     ObjectSortedSet<Reference2LongMap.Entry<K>> entries = map.reference2LongEntrySet();
/*     */     
/*  82 */     Objects.requireNonNull((Reference2LongSortedMap.FastSortedEntrySet)entries); return (entries instanceof Reference2LongSortedMap.FastSortedEntrySet) ? (Reference2LongSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  83 */       entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<K>
/*     */     extends Reference2LongMaps.EmptyMap<K>
/*     */     implements Reference2LongSortedMap<K>, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Reference2LongMap.Entry<K>> reference2LongEntrySet() {
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
/*     */     public ObjectSortedSet<Map.Entry<K, Long>> entrySet() {
/* 118 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSortedSet<K> keySet() {
/* 123 */       return ReferenceSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2LongSortedMap<K> subMap(K from, K to) {
/* 128 */       return Reference2LongSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2LongSortedMap<K> headMap(K to) {
/* 133 */       return Reference2LongSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2LongSortedMap<K> tailMap(K from) {
/* 138 */       return Reference2LongSortedMaps.EMPTY_MAP;
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
/*     */   public static <K> Reference2LongSortedMap<K> emptyMap() {
/* 164 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends Reference2LongMaps.Singleton<K>
/*     */     implements Reference2LongSortedMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final Comparator<? super K> comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(K key, long value, Comparator<? super K> comparator) {
/* 181 */       super(key, value);
/* 182 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(K key, long value) {
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
/*     */     public ObjectSortedSet<Reference2LongMap.Entry<K>> reference2LongEntrySet() {
/* 198 */       if (this.entries == null)
/* 199 */         this.entries = ObjectSortedSets.singleton(new AbstractReference2LongMap.BasicEntry<>(this.key, this.value), 
/* 200 */             (Comparator)Reference2LongSortedMaps.entryComparator(this.comparator)); 
/* 201 */       return (ObjectSortedSet<Reference2LongMap.Entry<K>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Long>> entrySet() {
/* 212 */       return (ObjectSortedSet)reference2LongEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> keySet() {
/* 216 */       if (this.keys == null)
/* 217 */         this.keys = ReferenceSortedSets.singleton(this.key, this.comparator); 
/* 218 */       return (ReferenceSortedSet<K>)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2LongSortedMap<K> subMap(K from, K to) {
/* 223 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 224 */         return this; 
/* 225 */       return Reference2LongSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2LongSortedMap<K> headMap(K to) {
/* 230 */       if (compare(this.key, to) < 0)
/* 231 */         return this; 
/* 232 */       return Reference2LongSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2LongSortedMap<K> tailMap(K from) {
/* 237 */       if (compare(from, this.key) <= 0)
/* 238 */         return this; 
/* 239 */       return Reference2LongSortedMaps.EMPTY_MAP;
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
/*     */   public static <K> Reference2LongSortedMap<K> singleton(K key, Long value) {
/* 266 */     return new Singleton<>(key, value.longValue());
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
/*     */   public static <K> Reference2LongSortedMap<K> singleton(K key, Long value, Comparator<? super K> comparator) {
/* 286 */     return new Singleton<>(key, value.longValue(), comparator);
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
/*     */   public static <K> Reference2LongSortedMap<K> singleton(K key, long value) {
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
/*     */   public static <K> Reference2LongSortedMap<K> singleton(K key, long value, Comparator<? super K> comparator) {
/* 325 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<K>
/*     */     extends Reference2LongMaps.SynchronizedMap<K>
/*     */     implements Reference2LongSortedMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2LongSortedMap<K> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Reference2LongSortedMap<K> m, Object sync) {
/* 335 */       super(m, sync);
/* 336 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Reference2LongSortedMap<K> m) {
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
/*     */     public ObjectSortedSet<Reference2LongMap.Entry<K>> reference2LongEntrySet() {
/* 350 */       if (this.entries == null)
/* 351 */         this.entries = ObjectSortedSets.synchronize(this.sortedMap.reference2LongEntrySet(), this.sync); 
/* 352 */       return (ObjectSortedSet<Reference2LongMap.Entry<K>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Long>> entrySet() {
/* 363 */       return (ObjectSortedSet)reference2LongEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> keySet() {
/* 367 */       if (this.keys == null)
/* 368 */         this.keys = ReferenceSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 369 */       return (ReferenceSortedSet<K>)this.keys;
/*     */     }
/*     */     
/*     */     public Reference2LongSortedMap<K> subMap(K from, K to) {
/* 373 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Reference2LongSortedMap<K> headMap(K to) {
/* 377 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Reference2LongSortedMap<K> tailMap(K from) {
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
/*     */   public static <K> Reference2LongSortedMap<K> synchronize(Reference2LongSortedMap<K> m) {
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
/*     */   public static <K> Reference2LongSortedMap<K> synchronize(Reference2LongSortedMap<K> m, Object sync) {
/* 421 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<K>
/*     */     extends Reference2LongMaps.UnmodifiableMap<K>
/*     */     implements Reference2LongSortedMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2LongSortedMap<K> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Reference2LongSortedMap<K> m) {
/* 431 */       super(m);
/* 432 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 436 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Reference2LongMap.Entry<K>> reference2LongEntrySet() {
/* 440 */       if (this.entries == null)
/* 441 */         this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.reference2LongEntrySet()); 
/* 442 */       return (ObjectSortedSet<Reference2LongMap.Entry<K>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Long>> entrySet() {
/* 453 */       return (ObjectSortedSet)reference2LongEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> keySet() {
/* 457 */       if (this.keys == null)
/* 458 */         this.keys = ReferenceSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 459 */       return (ReferenceSortedSet<K>)this.keys;
/*     */     }
/*     */     
/*     */     public Reference2LongSortedMap<K> subMap(K from, K to) {
/* 463 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Reference2LongSortedMap<K> headMap(K to) {
/* 467 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Reference2LongSortedMap<K> tailMap(K from) {
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
/*     */   public static <K> Reference2LongSortedMap<K> unmodifiable(Reference2LongSortedMap<K> m) {
/* 492 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2LongSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */