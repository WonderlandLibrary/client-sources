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
/*     */ public final class Object2CharSortedMaps
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
/*     */   public static <K> ObjectBidirectionalIterator<Object2CharMap.Entry<K>> fastIterator(Object2CharSortedMap<K> map) {
/*  59 */     ObjectSortedSet<Object2CharMap.Entry<K>> entries = map.object2CharEntrySet();
/*  60 */     return (entries instanceof Object2CharSortedMap.FastSortedEntrySet) ? (
/*  61 */       (Object2CharSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static <K> ObjectBidirectionalIterable<Object2CharMap.Entry<K>> fastIterable(Object2CharSortedMap<K> map) {
/*  78 */     ObjectSortedSet<Object2CharMap.Entry<K>> entries = map.object2CharEntrySet();
/*     */     
/*  80 */     Objects.requireNonNull((Object2CharSortedMap.FastSortedEntrySet)entries); return (entries instanceof Object2CharSortedMap.FastSortedEntrySet) ? (Object2CharSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  81 */       entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<K>
/*     */     extends Object2CharMaps.EmptyMap<K>
/*     */     implements Object2CharSortedMap<K>, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Object2CharMap.Entry<K>> object2CharEntrySet() {
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
/*     */     public ObjectSortedSet<Map.Entry<K, Character>> entrySet() {
/* 116 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 121 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2CharSortedMap<K> subMap(K from, K to) {
/* 126 */       return Object2CharSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2CharSortedMap<K> headMap(K to) {
/* 131 */       return Object2CharSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2CharSortedMap<K> tailMap(K from) {
/* 136 */       return Object2CharSortedMaps.EMPTY_MAP;
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
/*     */   public static <K> Object2CharSortedMap<K> emptyMap() {
/* 162 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends Object2CharMaps.Singleton<K>
/*     */     implements Object2CharSortedMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final Comparator<? super K> comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(K key, char value, Comparator<? super K> comparator) {
/* 179 */       super(key, value);
/* 180 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(K key, char value) {
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
/*     */     public ObjectSortedSet<Object2CharMap.Entry<K>> object2CharEntrySet() {
/* 196 */       if (this.entries == null)
/* 197 */         this.entries = ObjectSortedSets.singleton(new AbstractObject2CharMap.BasicEntry<>(this.key, this.value), 
/* 198 */             (Comparator)Object2CharSortedMaps.entryComparator(this.comparator)); 
/* 199 */       return (ObjectSortedSet<Object2CharMap.Entry<K>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Character>> entrySet() {
/* 210 */       return (ObjectSortedSet)object2CharEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 214 */       if (this.keys == null)
/* 215 */         this.keys = ObjectSortedSets.singleton(this.key, this.comparator); 
/* 216 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2CharSortedMap<K> subMap(K from, K to) {
/* 221 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 222 */         return this; 
/* 223 */       return Object2CharSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2CharSortedMap<K> headMap(K to) {
/* 228 */       if (compare(this.key, to) < 0)
/* 229 */         return this; 
/* 230 */       return Object2CharSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2CharSortedMap<K> tailMap(K from) {
/* 235 */       if (compare(from, this.key) <= 0)
/* 236 */         return this; 
/* 237 */       return Object2CharSortedMaps.EMPTY_MAP;
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
/*     */   public static <K> Object2CharSortedMap<K> singleton(K key, Character value) {
/* 264 */     return new Singleton<>(key, value.charValue());
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
/*     */   public static <K> Object2CharSortedMap<K> singleton(K key, Character value, Comparator<? super K> comparator) {
/* 285 */     return new Singleton<>(key, value.charValue(), comparator);
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
/*     */   public static <K> Object2CharSortedMap<K> singleton(K key, char value) {
/* 303 */     return new Singleton<>(key, value);
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
/*     */   public static <K> Object2CharSortedMap<K> singleton(K key, char value, Comparator<? super K> comparator) {
/* 324 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<K>
/*     */     extends Object2CharMaps.SynchronizedMap<K>
/*     */     implements Object2CharSortedMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2CharSortedMap<K> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Object2CharSortedMap<K> m, Object sync) {
/* 334 */       super(m, sync);
/* 335 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Object2CharSortedMap<K> m) {
/* 338 */       super(m);
/* 339 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 343 */       synchronized (this.sync) {
/* 344 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Object2CharMap.Entry<K>> object2CharEntrySet() {
/* 349 */       if (this.entries == null)
/* 350 */         this.entries = ObjectSortedSets.synchronize(this.sortedMap.object2CharEntrySet(), this.sync); 
/* 351 */       return (ObjectSortedSet<Object2CharMap.Entry<K>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Character>> entrySet() {
/* 362 */       return (ObjectSortedSet)object2CharEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 366 */       if (this.keys == null)
/* 367 */         this.keys = ObjectSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 368 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */     
/*     */     public Object2CharSortedMap<K> subMap(K from, K to) {
/* 372 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Object2CharSortedMap<K> headMap(K to) {
/* 376 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Object2CharSortedMap<K> tailMap(K from) {
/* 380 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public K firstKey() {
/* 384 */       synchronized (this.sync) {
/* 385 */         return this.sortedMap.firstKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public K lastKey() {
/* 390 */       synchronized (this.sync) {
/* 391 */         return this.sortedMap.lastKey();
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
/*     */   public static <K> Object2CharSortedMap<K> synchronize(Object2CharSortedMap<K> m) {
/* 405 */     return new SynchronizedSortedMap<>(m);
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
/*     */   public static <K> Object2CharSortedMap<K> synchronize(Object2CharSortedMap<K> m, Object sync) {
/* 420 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<K>
/*     */     extends Object2CharMaps.UnmodifiableMap<K>
/*     */     implements Object2CharSortedMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2CharSortedMap<K> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Object2CharSortedMap<K> m) {
/* 430 */       super(m);
/* 431 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 435 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Object2CharMap.Entry<K>> object2CharEntrySet() {
/* 439 */       if (this.entries == null)
/* 440 */         this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.object2CharEntrySet()); 
/* 441 */       return (ObjectSortedSet<Object2CharMap.Entry<K>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Character>> entrySet() {
/* 452 */       return (ObjectSortedSet)object2CharEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 456 */       if (this.keys == null)
/* 457 */         this.keys = ObjectSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 458 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */     
/*     */     public Object2CharSortedMap<K> subMap(K from, K to) {
/* 462 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Object2CharSortedMap<K> headMap(K to) {
/* 466 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Object2CharSortedMap<K> tailMap(K from) {
/* 470 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public K firstKey() {
/* 474 */       return this.sortedMap.firstKey();
/*     */     }
/*     */     
/*     */     public K lastKey() {
/* 478 */       return this.sortedMap.lastKey();
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
/*     */   public static <K> Object2CharSortedMap<K> unmodifiable(Object2CharSortedMap<K> m) {
/* 491 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2CharSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */