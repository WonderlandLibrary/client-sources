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
/*     */ public final class Object2ByteSortedMaps
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
/*     */   public static <K> ObjectBidirectionalIterator<Object2ByteMap.Entry<K>> fastIterator(Object2ByteSortedMap<K> map) {
/*  59 */     ObjectSortedSet<Object2ByteMap.Entry<K>> entries = map.object2ByteEntrySet();
/*  60 */     return (entries instanceof Object2ByteSortedMap.FastSortedEntrySet) ? (
/*  61 */       (Object2ByteSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static <K> ObjectBidirectionalIterable<Object2ByteMap.Entry<K>> fastIterable(Object2ByteSortedMap<K> map) {
/*  78 */     ObjectSortedSet<Object2ByteMap.Entry<K>> entries = map.object2ByteEntrySet();
/*     */     
/*  80 */     Objects.requireNonNull((Object2ByteSortedMap.FastSortedEntrySet)entries); return (entries instanceof Object2ByteSortedMap.FastSortedEntrySet) ? (Object2ByteSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  81 */       entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<K>
/*     */     extends Object2ByteMaps.EmptyMap<K>
/*     */     implements Object2ByteSortedMap<K>, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Object2ByteMap.Entry<K>> object2ByteEntrySet() {
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
/*     */     public ObjectSortedSet<Map.Entry<K, Byte>> entrySet() {
/* 116 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 121 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ByteSortedMap<K> subMap(K from, K to) {
/* 126 */       return Object2ByteSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ByteSortedMap<K> headMap(K to) {
/* 131 */       return Object2ByteSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ByteSortedMap<K> tailMap(K from) {
/* 136 */       return Object2ByteSortedMaps.EMPTY_MAP;
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
/*     */   public static <K> Object2ByteSortedMap<K> emptyMap() {
/* 162 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends Object2ByteMaps.Singleton<K>
/*     */     implements Object2ByteSortedMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final Comparator<? super K> comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(K key, byte value, Comparator<? super K> comparator) {
/* 179 */       super(key, value);
/* 180 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(K key, byte value) {
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
/*     */     public ObjectSortedSet<Object2ByteMap.Entry<K>> object2ByteEntrySet() {
/* 196 */       if (this.entries == null)
/* 197 */         this.entries = ObjectSortedSets.singleton(new AbstractObject2ByteMap.BasicEntry<>(this.key, this.value), 
/* 198 */             (Comparator)Object2ByteSortedMaps.entryComparator(this.comparator)); 
/* 199 */       return (ObjectSortedSet<Object2ByteMap.Entry<K>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Byte>> entrySet() {
/* 210 */       return (ObjectSortedSet)object2ByteEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 214 */       if (this.keys == null)
/* 215 */         this.keys = ObjectSortedSets.singleton(this.key, this.comparator); 
/* 216 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ByteSortedMap<K> subMap(K from, K to) {
/* 221 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 222 */         return this; 
/* 223 */       return Object2ByteSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ByteSortedMap<K> headMap(K to) {
/* 228 */       if (compare(this.key, to) < 0)
/* 229 */         return this; 
/* 230 */       return Object2ByteSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ByteSortedMap<K> tailMap(K from) {
/* 235 */       if (compare(from, this.key) <= 0)
/* 236 */         return this; 
/* 237 */       return Object2ByteSortedMaps.EMPTY_MAP;
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
/*     */   public static <K> Object2ByteSortedMap<K> singleton(K key, Byte value) {
/* 264 */     return new Singleton<>(key, value.byteValue());
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
/*     */   public static <K> Object2ByteSortedMap<K> singleton(K key, Byte value, Comparator<? super K> comparator) {
/* 284 */     return new Singleton<>(key, value.byteValue(), comparator);
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
/*     */   public static <K> Object2ByteSortedMap<K> singleton(K key, byte value) {
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
/*     */   
/*     */   public static <K> Object2ByteSortedMap<K> singleton(K key, byte value, Comparator<? super K> comparator) {
/* 323 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<K>
/*     */     extends Object2ByteMaps.SynchronizedMap<K>
/*     */     implements Object2ByteSortedMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ByteSortedMap<K> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Object2ByteSortedMap<K> m, Object sync) {
/* 333 */       super(m, sync);
/* 334 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Object2ByteSortedMap<K> m) {
/* 337 */       super(m);
/* 338 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 342 */       synchronized (this.sync) {
/* 343 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Object2ByteMap.Entry<K>> object2ByteEntrySet() {
/* 348 */       if (this.entries == null)
/* 349 */         this.entries = ObjectSortedSets.synchronize(this.sortedMap.object2ByteEntrySet(), this.sync); 
/* 350 */       return (ObjectSortedSet<Object2ByteMap.Entry<K>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Byte>> entrySet() {
/* 361 */       return (ObjectSortedSet)object2ByteEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 365 */       if (this.keys == null)
/* 366 */         this.keys = ObjectSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 367 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */     
/*     */     public Object2ByteSortedMap<K> subMap(K from, K to) {
/* 371 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Object2ByteSortedMap<K> headMap(K to) {
/* 375 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Object2ByteSortedMap<K> tailMap(K from) {
/* 379 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public K firstKey() {
/* 383 */       synchronized (this.sync) {
/* 384 */         return this.sortedMap.firstKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public K lastKey() {
/* 389 */       synchronized (this.sync) {
/* 390 */         return this.sortedMap.lastKey();
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
/*     */   public static <K> Object2ByteSortedMap<K> synchronize(Object2ByteSortedMap<K> m) {
/* 404 */     return new SynchronizedSortedMap<>(m);
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
/*     */   public static <K> Object2ByteSortedMap<K> synchronize(Object2ByteSortedMap<K> m, Object sync) {
/* 419 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<K>
/*     */     extends Object2ByteMaps.UnmodifiableMap<K>
/*     */     implements Object2ByteSortedMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ByteSortedMap<K> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Object2ByteSortedMap<K> m) {
/* 429 */       super(m);
/* 430 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 434 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Object2ByteMap.Entry<K>> object2ByteEntrySet() {
/* 438 */       if (this.entries == null)
/* 439 */         this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.object2ByteEntrySet()); 
/* 440 */       return (ObjectSortedSet<Object2ByteMap.Entry<K>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Byte>> entrySet() {
/* 451 */       return (ObjectSortedSet)object2ByteEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 455 */       if (this.keys == null)
/* 456 */         this.keys = ObjectSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 457 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */     
/*     */     public Object2ByteSortedMap<K> subMap(K from, K to) {
/* 461 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Object2ByteSortedMap<K> headMap(K to) {
/* 465 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Object2ByteSortedMap<K> tailMap(K from) {
/* 469 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public K firstKey() {
/* 473 */       return this.sortedMap.firstKey();
/*     */     }
/*     */     
/*     */     public K lastKey() {
/* 477 */       return this.sortedMap.lastKey();
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
/*     */   public static <K> Object2ByteSortedMap<K> unmodifiable(Object2ByteSortedMap<K> m) {
/* 490 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ByteSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */