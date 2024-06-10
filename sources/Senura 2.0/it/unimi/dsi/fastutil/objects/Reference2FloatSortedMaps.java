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
/*     */ public final class Reference2FloatSortedMaps
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
/*     */   public static <K> ObjectBidirectionalIterator<Reference2FloatMap.Entry<K>> fastIterator(Reference2FloatSortedMap<K> map) {
/*  60 */     ObjectSortedSet<Reference2FloatMap.Entry<K>> entries = map.reference2FloatEntrySet();
/*  61 */     return (entries instanceof Reference2FloatSortedMap.FastSortedEntrySet) ? (
/*  62 */       (Reference2FloatSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static <K> ObjectBidirectionalIterable<Reference2FloatMap.Entry<K>> fastIterable(Reference2FloatSortedMap<K> map) {
/*  80 */     ObjectSortedSet<Reference2FloatMap.Entry<K>> entries = map.reference2FloatEntrySet();
/*     */     
/*  82 */     Objects.requireNonNull((Reference2FloatSortedMap.FastSortedEntrySet)entries); return (entries instanceof Reference2FloatSortedMap.FastSortedEntrySet) ? (Reference2FloatSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  83 */       entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<K>
/*     */     extends Reference2FloatMaps.EmptyMap<K>
/*     */     implements Reference2FloatSortedMap<K>, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Reference2FloatMap.Entry<K>> reference2FloatEntrySet() {
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
/*     */     public ObjectSortedSet<Map.Entry<K, Float>> entrySet() {
/* 118 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSortedSet<K> keySet() {
/* 123 */       return ReferenceSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2FloatSortedMap<K> subMap(K from, K to) {
/* 128 */       return Reference2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2FloatSortedMap<K> headMap(K to) {
/* 133 */       return Reference2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2FloatSortedMap<K> tailMap(K from) {
/* 138 */       return Reference2FloatSortedMaps.EMPTY_MAP;
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
/*     */   public static <K> Reference2FloatSortedMap<K> emptyMap() {
/* 164 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends Reference2FloatMaps.Singleton<K>
/*     */     implements Reference2FloatSortedMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final Comparator<? super K> comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(K key, float value, Comparator<? super K> comparator) {
/* 181 */       super(key, value);
/* 182 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(K key, float value) {
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
/*     */     public ObjectSortedSet<Reference2FloatMap.Entry<K>> reference2FloatEntrySet() {
/* 198 */       if (this.entries == null)
/* 199 */         this.entries = ObjectSortedSets.singleton(new AbstractReference2FloatMap.BasicEntry<>(this.key, this.value), 
/* 200 */             (Comparator)Reference2FloatSortedMaps.entryComparator(this.comparator)); 
/* 201 */       return (ObjectSortedSet<Reference2FloatMap.Entry<K>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Float>> entrySet() {
/* 212 */       return (ObjectSortedSet)reference2FloatEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> keySet() {
/* 216 */       if (this.keys == null)
/* 217 */         this.keys = ReferenceSortedSets.singleton(this.key, this.comparator); 
/* 218 */       return (ReferenceSortedSet<K>)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2FloatSortedMap<K> subMap(K from, K to) {
/* 223 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 224 */         return this; 
/* 225 */       return Reference2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2FloatSortedMap<K> headMap(K to) {
/* 230 */       if (compare(this.key, to) < 0)
/* 231 */         return this; 
/* 232 */       return Reference2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2FloatSortedMap<K> tailMap(K from) {
/* 237 */       if (compare(from, this.key) <= 0)
/* 238 */         return this; 
/* 239 */       return Reference2FloatSortedMaps.EMPTY_MAP;
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
/*     */   public static <K> Reference2FloatSortedMap<K> singleton(K key, Float value) {
/* 266 */     return new Singleton<>(key, value.floatValue());
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
/*     */   public static <K> Reference2FloatSortedMap<K> singleton(K key, Float value, Comparator<? super K> comparator) {
/* 287 */     return new Singleton<>(key, value.floatValue(), comparator);
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
/*     */   public static <K> Reference2FloatSortedMap<K> singleton(K key, float value) {
/* 305 */     return new Singleton<>(key, value);
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
/*     */   public static <K> Reference2FloatSortedMap<K> singleton(K key, float value, Comparator<? super K> comparator) {
/* 326 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<K>
/*     */     extends Reference2FloatMaps.SynchronizedMap<K>
/*     */     implements Reference2FloatSortedMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2FloatSortedMap<K> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Reference2FloatSortedMap<K> m, Object sync) {
/* 336 */       super(m, sync);
/* 337 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Reference2FloatSortedMap<K> m) {
/* 340 */       super(m);
/* 341 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 345 */       synchronized (this.sync) {
/* 346 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Reference2FloatMap.Entry<K>> reference2FloatEntrySet() {
/* 351 */       if (this.entries == null)
/* 352 */         this.entries = ObjectSortedSets.synchronize(this.sortedMap.reference2FloatEntrySet(), this.sync); 
/* 353 */       return (ObjectSortedSet<Reference2FloatMap.Entry<K>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Float>> entrySet() {
/* 364 */       return (ObjectSortedSet)reference2FloatEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> keySet() {
/* 368 */       if (this.keys == null)
/* 369 */         this.keys = ReferenceSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 370 */       return (ReferenceSortedSet<K>)this.keys;
/*     */     }
/*     */     
/*     */     public Reference2FloatSortedMap<K> subMap(K from, K to) {
/* 374 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Reference2FloatSortedMap<K> headMap(K to) {
/* 378 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Reference2FloatSortedMap<K> tailMap(K from) {
/* 382 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public K firstKey() {
/* 386 */       synchronized (this.sync) {
/* 387 */         return this.sortedMap.firstKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public K lastKey() {
/* 392 */       synchronized (this.sync) {
/* 393 */         return this.sortedMap.lastKey();
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
/*     */   public static <K> Reference2FloatSortedMap<K> synchronize(Reference2FloatSortedMap<K> m) {
/* 407 */     return new SynchronizedSortedMap<>(m);
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
/*     */   public static <K> Reference2FloatSortedMap<K> synchronize(Reference2FloatSortedMap<K> m, Object sync) {
/* 422 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<K>
/*     */     extends Reference2FloatMaps.UnmodifiableMap<K>
/*     */     implements Reference2FloatSortedMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2FloatSortedMap<K> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Reference2FloatSortedMap<K> m) {
/* 432 */       super(m);
/* 433 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 437 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Reference2FloatMap.Entry<K>> reference2FloatEntrySet() {
/* 441 */       if (this.entries == null)
/* 442 */         this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.reference2FloatEntrySet()); 
/* 443 */       return (ObjectSortedSet<Reference2FloatMap.Entry<K>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Float>> entrySet() {
/* 454 */       return (ObjectSortedSet)reference2FloatEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> keySet() {
/* 458 */       if (this.keys == null)
/* 459 */         this.keys = ReferenceSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 460 */       return (ReferenceSortedSet<K>)this.keys;
/*     */     }
/*     */     
/*     */     public Reference2FloatSortedMap<K> subMap(K from, K to) {
/* 464 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Reference2FloatSortedMap<K> headMap(K to) {
/* 468 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Reference2FloatSortedMap<K> tailMap(K from) {
/* 472 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public K firstKey() {
/* 476 */       return this.sortedMap.firstKey();
/*     */     }
/*     */     
/*     */     public K lastKey() {
/* 480 */       return this.sortedMap.lastKey();
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
/*     */   public static <K> Reference2FloatSortedMap<K> unmodifiable(Reference2FloatSortedMap<K> m) {
/* 493 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2FloatSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */