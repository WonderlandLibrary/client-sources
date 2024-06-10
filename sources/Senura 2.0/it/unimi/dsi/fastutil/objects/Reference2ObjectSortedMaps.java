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
/*     */ public final class Reference2ObjectSortedMaps
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
/*     */   public static <K, V> ObjectBidirectionalIterator<Reference2ObjectMap.Entry<K, V>> fastIterator(Reference2ObjectSortedMap<K, V> map) {
/*  60 */     ObjectSortedSet<Reference2ObjectMap.Entry<K, V>> entries = map.reference2ObjectEntrySet();
/*  61 */     return (entries instanceof Reference2ObjectSortedMap.FastSortedEntrySet) ? (
/*  62 */       (Reference2ObjectSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static <K, V> ObjectBidirectionalIterable<Reference2ObjectMap.Entry<K, V>> fastIterable(Reference2ObjectSortedMap<K, V> map) {
/*  80 */     ObjectSortedSet<Reference2ObjectMap.Entry<K, V>> entries = map.reference2ObjectEntrySet();
/*     */     
/*  82 */     Objects.requireNonNull((Reference2ObjectSortedMap.FastSortedEntrySet)entries); return (entries instanceof Reference2ObjectSortedMap.FastSortedEntrySet) ? (Reference2ObjectSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  83 */       entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<K, V>
/*     */     extends Reference2ObjectMaps.EmptyMap<K, V>
/*     */     implements Reference2ObjectSortedMap<K, V>, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Reference2ObjectMap.Entry<K, V>> reference2ObjectEntrySet() {
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
/*     */     public ReferenceSortedSet<K> keySet() {
/* 118 */       return ReferenceSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> subMap(K from, K to) {
/* 123 */       return Reference2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> headMap(K to) {
/* 128 */       return Reference2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> tailMap(K from) {
/* 133 */       return Reference2ObjectSortedMaps.EMPTY_MAP;
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
/*     */   public static <K, V> Reference2ObjectSortedMap<K, V> emptyMap() {
/* 159 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K, V>
/*     */     extends Reference2ObjectMaps.Singleton<K, V>
/*     */     implements Reference2ObjectSortedMap<K, V>, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Reference2ObjectMap.Entry<K, V>> reference2ObjectEntrySet() {
/* 193 */       if (this.entries == null)
/* 194 */         this.entries = ObjectSortedSets.singleton(new AbstractReference2ObjectMap.BasicEntry<>(this.key, this.value), 
/* 195 */             (Comparator)Reference2ObjectSortedMaps.entryComparator(this.comparator)); 
/* 196 */       return (ObjectSortedSet<Reference2ObjectMap.Entry<K, V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
/* 202 */       return (ObjectSortedSet)reference2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> keySet() {
/* 206 */       if (this.keys == null)
/* 207 */         this.keys = ReferenceSortedSets.singleton(this.key, this.comparator); 
/* 208 */       return (ReferenceSortedSet<K>)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> subMap(K from, K to) {
/* 213 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 214 */         return this; 
/* 215 */       return Reference2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> headMap(K to) {
/* 220 */       if (compare(this.key, to) < 0)
/* 221 */         return this; 
/* 222 */       return Reference2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> tailMap(K from) {
/* 227 */       if (compare(from, this.key) <= 0)
/* 228 */         return this; 
/* 229 */       return Reference2ObjectSortedMaps.EMPTY_MAP;
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
/*     */   public static <K, V> Reference2ObjectSortedMap<K, V> singleton(K key, V value) {
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
/*     */   public static <K, V> Reference2ObjectSortedMap<K, V> singleton(K key, V value, Comparator<? super K> comparator) {
/* 277 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<K, V>
/*     */     extends Reference2ObjectMaps.SynchronizedMap<K, V>
/*     */     implements Reference2ObjectSortedMap<K, V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2ObjectSortedMap<K, V> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Reference2ObjectSortedMap<K, V> m, Object sync) {
/* 287 */       super(m, sync);
/* 288 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Reference2ObjectSortedMap<K, V> m) {
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
/*     */     public ObjectSortedSet<Reference2ObjectMap.Entry<K, V>> reference2ObjectEntrySet() {
/* 302 */       if (this.entries == null)
/* 303 */         this.entries = ObjectSortedSets.synchronize(this.sortedMap.reference2ObjectEntrySet(), this.sync); 
/* 304 */       return (ObjectSortedSet<Reference2ObjectMap.Entry<K, V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
/* 310 */       return (ObjectSortedSet)reference2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> keySet() {
/* 314 */       if (this.keys == null)
/* 315 */         this.keys = ReferenceSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 316 */       return (ReferenceSortedSet<K>)this.keys;
/*     */     }
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> subMap(K from, K to) {
/* 320 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> headMap(K to) {
/* 324 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> tailMap(K from) {
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
/*     */   public static <K, V> Reference2ObjectSortedMap<K, V> synchronize(Reference2ObjectSortedMap<K, V> m) {
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
/*     */   public static <K, V> Reference2ObjectSortedMap<K, V> synchronize(Reference2ObjectSortedMap<K, V> m, Object sync) {
/* 369 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<K, V>
/*     */     extends Reference2ObjectMaps.UnmodifiableMap<K, V>
/*     */     implements Reference2ObjectSortedMap<K, V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2ObjectSortedMap<K, V> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Reference2ObjectSortedMap<K, V> m) {
/* 379 */       super(m);
/* 380 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 384 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Reference2ObjectMap.Entry<K, V>> reference2ObjectEntrySet() {
/* 388 */       if (this.entries == null)
/* 389 */         this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.reference2ObjectEntrySet()); 
/* 390 */       return (ObjectSortedSet<Reference2ObjectMap.Entry<K, V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
/* 396 */       return (ObjectSortedSet)reference2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSortedSet<K> keySet() {
/* 400 */       if (this.keys == null)
/* 401 */         this.keys = ReferenceSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 402 */       return (ReferenceSortedSet<K>)this.keys;
/*     */     }
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> subMap(K from, K to) {
/* 406 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> headMap(K to) {
/* 410 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> tailMap(K from) {
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
/*     */   public static <K, V> Reference2ObjectSortedMap<K, V> unmodifiable(Reference2ObjectSortedMap<K, V> m) {
/* 435 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2ObjectSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */