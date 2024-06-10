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
/*     */ public final class Object2BooleanSortedMaps
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
/*     */   public static <K> ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> fastIterator(Object2BooleanSortedMap<K> map) {
/*  60 */     ObjectSortedSet<Object2BooleanMap.Entry<K>> entries = map.object2BooleanEntrySet();
/*  61 */     return (entries instanceof Object2BooleanSortedMap.FastSortedEntrySet) ? (
/*  62 */       (Object2BooleanSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static <K> ObjectBidirectionalIterable<Object2BooleanMap.Entry<K>> fastIterable(Object2BooleanSortedMap<K> map) {
/*  80 */     ObjectSortedSet<Object2BooleanMap.Entry<K>> entries = map.object2BooleanEntrySet();
/*     */     
/*  82 */     Objects.requireNonNull((Object2BooleanSortedMap.FastSortedEntrySet)entries); return (entries instanceof Object2BooleanSortedMap.FastSortedEntrySet) ? (Object2BooleanSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  83 */       entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<K>
/*     */     extends Object2BooleanMaps.EmptyMap<K>
/*     */     implements Object2BooleanSortedMap<K>, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Object2BooleanMap.Entry<K>> object2BooleanEntrySet() {
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
/*     */     public ObjectSortedSet<Map.Entry<K, Boolean>> entrySet() {
/* 118 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 123 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2BooleanSortedMap<K> subMap(K from, K to) {
/* 128 */       return Object2BooleanSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2BooleanSortedMap<K> headMap(K to) {
/* 133 */       return Object2BooleanSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2BooleanSortedMap<K> tailMap(K from) {
/* 138 */       return Object2BooleanSortedMaps.EMPTY_MAP;
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
/*     */   public static <K> Object2BooleanSortedMap<K> emptyMap() {
/* 164 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends Object2BooleanMaps.Singleton<K>
/*     */     implements Object2BooleanSortedMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final Comparator<? super K> comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(K key, boolean value, Comparator<? super K> comparator) {
/* 181 */       super(key, value);
/* 182 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(K key, boolean value) {
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
/*     */     public ObjectSortedSet<Object2BooleanMap.Entry<K>> object2BooleanEntrySet() {
/* 198 */       if (this.entries == null)
/* 199 */         this.entries = ObjectSortedSets.singleton(new AbstractObject2BooleanMap.BasicEntry<>(this.key, this.value), 
/* 200 */             (Comparator)Object2BooleanSortedMaps.entryComparator(this.comparator)); 
/* 201 */       return (ObjectSortedSet<Object2BooleanMap.Entry<K>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Boolean>> entrySet() {
/* 212 */       return (ObjectSortedSet)object2BooleanEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 216 */       if (this.keys == null)
/* 217 */         this.keys = ObjectSortedSets.singleton(this.key, this.comparator); 
/* 218 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2BooleanSortedMap<K> subMap(K from, K to) {
/* 223 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 224 */         return this; 
/* 225 */       return Object2BooleanSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2BooleanSortedMap<K> headMap(K to) {
/* 230 */       if (compare(this.key, to) < 0)
/* 231 */         return this; 
/* 232 */       return Object2BooleanSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2BooleanSortedMap<K> tailMap(K from) {
/* 237 */       if (compare(from, this.key) <= 0)
/* 238 */         return this; 
/* 239 */       return Object2BooleanSortedMaps.EMPTY_MAP;
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
/*     */   public static <K> Object2BooleanSortedMap<K> singleton(K key, Boolean value) {
/* 266 */     return new Singleton<>(key, value.booleanValue());
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
/*     */   public static <K> Object2BooleanSortedMap<K> singleton(K key, Boolean value, Comparator<? super K> comparator) {
/* 287 */     return new Singleton<>(key, value.booleanValue(), comparator);
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
/*     */   public static <K> Object2BooleanSortedMap<K> singleton(K key, boolean value) {
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
/*     */   public static <K> Object2BooleanSortedMap<K> singleton(K key, boolean value, Comparator<? super K> comparator) {
/* 326 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<K>
/*     */     extends Object2BooleanMaps.SynchronizedMap<K>
/*     */     implements Object2BooleanSortedMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2BooleanSortedMap<K> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Object2BooleanSortedMap<K> m, Object sync) {
/* 336 */       super(m, sync);
/* 337 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Object2BooleanSortedMap<K> m) {
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
/*     */     public ObjectSortedSet<Object2BooleanMap.Entry<K>> object2BooleanEntrySet() {
/* 351 */       if (this.entries == null)
/* 352 */         this.entries = ObjectSortedSets.synchronize(this.sortedMap.object2BooleanEntrySet(), this.sync); 
/* 353 */       return (ObjectSortedSet<Object2BooleanMap.Entry<K>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Boolean>> entrySet() {
/* 364 */       return (ObjectSortedSet)object2BooleanEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 368 */       if (this.keys == null)
/* 369 */         this.keys = ObjectSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 370 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */     
/*     */     public Object2BooleanSortedMap<K> subMap(K from, K to) {
/* 374 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Object2BooleanSortedMap<K> headMap(K to) {
/* 378 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Object2BooleanSortedMap<K> tailMap(K from) {
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
/*     */   public static <K> Object2BooleanSortedMap<K> synchronize(Object2BooleanSortedMap<K> m) {
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
/*     */   public static <K> Object2BooleanSortedMap<K> synchronize(Object2BooleanSortedMap<K> m, Object sync) {
/* 422 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<K>
/*     */     extends Object2BooleanMaps.UnmodifiableMap<K>
/*     */     implements Object2BooleanSortedMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2BooleanSortedMap<K> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Object2BooleanSortedMap<K> m) {
/* 432 */       super(m);
/* 433 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 437 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Object2BooleanMap.Entry<K>> object2BooleanEntrySet() {
/* 441 */       if (this.entries == null)
/* 442 */         this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.object2BooleanEntrySet()); 
/* 443 */       return (ObjectSortedSet<Object2BooleanMap.Entry<K>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Boolean>> entrySet() {
/* 454 */       return (ObjectSortedSet)object2BooleanEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 458 */       if (this.keys == null)
/* 459 */         this.keys = ObjectSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 460 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */     
/*     */     public Object2BooleanSortedMap<K> subMap(K from, K to) {
/* 464 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Object2BooleanSortedMap<K> headMap(K to) {
/* 468 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Object2BooleanSortedMap<K> tailMap(K from) {
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
/*     */   public static <K> Object2BooleanSortedMap<K> unmodifiable(Object2BooleanSortedMap<K> m) {
/* 493 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2BooleanSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */