/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterable;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
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
/*     */ public final class Int2ReferenceSortedMaps
/*     */ {
/*     */   public static Comparator<? super Map.Entry<Integer, ?>> entryComparator(IntComparator comparator) {
/*  43 */     return (x, y) -> comparator.compare(((Integer)x.getKey()).intValue(), ((Integer)y.getKey()).intValue());
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
/*     */   public static <V> ObjectBidirectionalIterator<Int2ReferenceMap.Entry<V>> fastIterator(Int2ReferenceSortedMap<V> map) {
/*  61 */     ObjectSortedSet<Int2ReferenceMap.Entry<V>> entries = map.int2ReferenceEntrySet();
/*  62 */     return (entries instanceof Int2ReferenceSortedMap.FastSortedEntrySet) ? (
/*  63 */       (Int2ReferenceSortedMap.FastSortedEntrySet)entries).fastIterator() : 
/*  64 */       entries.iterator();
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
/*     */   public static <V> ObjectBidirectionalIterable<Int2ReferenceMap.Entry<V>> fastIterable(Int2ReferenceSortedMap<V> map) {
/*  81 */     ObjectSortedSet<Int2ReferenceMap.Entry<V>> entries = map.int2ReferenceEntrySet();
/*     */     
/*  83 */     Objects.requireNonNull((Int2ReferenceSortedMap.FastSortedEntrySet)entries); return (entries instanceof Int2ReferenceSortedMap.FastSortedEntrySet) ? (Int2ReferenceSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  84 */       (ObjectBidirectionalIterable<Int2ReferenceMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<V>
/*     */     extends Int2ReferenceMaps.EmptyMap<V>
/*     */     implements Int2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public IntComparator comparator() {
/* 103 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Int2ReferenceMap.Entry<V>> int2ReferenceEntrySet() {
/* 108 */       return (ObjectSortedSet<Int2ReferenceMap.Entry<V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Integer, V>> entrySet() {
/* 119 */       return (ObjectSortedSet<Map.Entry<Integer, V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet keySet() {
/* 124 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ReferenceSortedMap<V> subMap(int from, int to) {
/* 129 */       return Int2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ReferenceSortedMap<V> headMap(int to) {
/* 134 */       return Int2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ReferenceSortedMap<V> tailMap(int from) {
/* 139 */       return Int2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public int firstIntKey() {
/* 143 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public int lastIntKey() {
/* 147 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> headMap(Integer oto) {
/* 157 */       return headMap(oto.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> tailMap(Integer ofrom) {
/* 167 */       return tailMap(ofrom.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> subMap(Integer ofrom, Integer oto) {
/* 177 */       return subMap(ofrom.intValue(), oto.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer firstKey() {
/* 187 */       return Integer.valueOf(firstIntKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer lastKey() {
/* 197 */       return Integer.valueOf(lastIntKey());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 204 */   public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Int2ReferenceSortedMap<V> emptyMap() {
/* 215 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Int2ReferenceMaps.Singleton<V>
/*     */     implements Int2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final IntComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(int key, V value, IntComparator comparator) {
/* 232 */       super(key, value);
/* 233 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(int key, V value) {
/* 236 */       this(key, value, (IntComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(int k1, int k2) {
/* 240 */       return (this.comparator == null) ? Integer.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public IntComparator comparator() {
/* 244 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Int2ReferenceMap.Entry<V>> int2ReferenceEntrySet() {
/* 249 */       if (this.entries == null)
/* 250 */         this.entries = (ObjectSet<Int2ReferenceMap.Entry<V>>)ObjectSortedSets.singleton(new AbstractInt2ReferenceMap.BasicEntry<>(this.key, this.value), 
/* 251 */             Int2ReferenceSortedMaps.entryComparator(this.comparator)); 
/* 252 */       return (ObjectSortedSet<Int2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Integer, V>> entrySet() {
/* 263 */       return (ObjectSortedSet)int2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public IntSortedSet keySet() {
/* 267 */       if (this.keys == null)
/* 268 */         this.keys = IntSortedSets.singleton(this.key, this.comparator); 
/* 269 */       return (IntSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ReferenceSortedMap<V> subMap(int from, int to) {
/* 274 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 275 */         return this; 
/* 276 */       return Int2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ReferenceSortedMap<V> headMap(int to) {
/* 281 */       if (compare(this.key, to) < 0)
/* 282 */         return this; 
/* 283 */       return Int2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ReferenceSortedMap<V> tailMap(int from) {
/* 288 */       if (compare(from, this.key) <= 0)
/* 289 */         return this; 
/* 290 */       return Int2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public int firstIntKey() {
/* 294 */       return this.key;
/*     */     }
/*     */     
/*     */     public int lastIntKey() {
/* 298 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> headMap(Integer oto) {
/* 308 */       return headMap(oto.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> tailMap(Integer ofrom) {
/* 318 */       return tailMap(ofrom.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> subMap(Integer ofrom, Integer oto) {
/* 328 */       return subMap(ofrom.intValue(), oto.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer firstKey() {
/* 338 */       return Integer.valueOf(firstIntKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer lastKey() {
/* 348 */       return Integer.valueOf(lastIntKey());
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
/*     */   public static <V> Int2ReferenceSortedMap<V> singleton(Integer key, V value) {
/* 367 */     return new Singleton<>(key.intValue(), value);
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
/*     */   public static <V> Int2ReferenceSortedMap<V> singleton(Integer key, V value, IntComparator comparator) {
/* 387 */     return new Singleton<>(key.intValue(), value, comparator);
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
/*     */   public static <V> Int2ReferenceSortedMap<V> singleton(int key, V value) {
/* 405 */     return new Singleton<>(key, value);
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
/*     */   public static <V> Int2ReferenceSortedMap<V> singleton(int key, V value, IntComparator comparator) {
/* 425 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<V>
/*     */     extends Int2ReferenceMaps.SynchronizedMap<V>
/*     */     implements Int2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2ReferenceSortedMap<V> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Int2ReferenceSortedMap<V> m, Object sync) {
/* 435 */       super(m, sync);
/* 436 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Int2ReferenceSortedMap<V> m) {
/* 439 */       super(m);
/* 440 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public IntComparator comparator() {
/* 444 */       synchronized (this.sync) {
/* 445 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Int2ReferenceMap.Entry<V>> int2ReferenceEntrySet() {
/* 450 */       if (this.entries == null)
/* 451 */         this.entries = (ObjectSet<Int2ReferenceMap.Entry<V>>)ObjectSortedSets.synchronize(this.sortedMap.int2ReferenceEntrySet(), this.sync); 
/* 452 */       return (ObjectSortedSet<Int2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Integer, V>> entrySet() {
/* 463 */       return (ObjectSortedSet)int2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public IntSortedSet keySet() {
/* 467 */       if (this.keys == null)
/* 468 */         this.keys = IntSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 469 */       return (IntSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Int2ReferenceSortedMap<V> subMap(int from, int to) {
/* 473 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Int2ReferenceSortedMap<V> headMap(int to) {
/* 477 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Int2ReferenceSortedMap<V> tailMap(int from) {
/* 481 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public int firstIntKey() {
/* 485 */       synchronized (this.sync) {
/* 486 */         return this.sortedMap.firstIntKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int lastIntKey() {
/* 491 */       synchronized (this.sync) {
/* 492 */         return this.sortedMap.lastIntKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer firstKey() {
/* 503 */       synchronized (this.sync) {
/* 504 */         return this.sortedMap.firstKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer lastKey() {
/* 515 */       synchronized (this.sync) {
/* 516 */         return this.sortedMap.lastKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> subMap(Integer from, Integer to) {
/* 527 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> headMap(Integer to) {
/* 537 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> tailMap(Integer from) {
/* 547 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
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
/*     */   public static <V> Int2ReferenceSortedMap<V> synchronize(Int2ReferenceSortedMap<V> m) {
/* 560 */     return new SynchronizedSortedMap<>(m);
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
/*     */   public static <V> Int2ReferenceSortedMap<V> synchronize(Int2ReferenceSortedMap<V> m, Object sync) {
/* 575 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<V>
/*     */     extends Int2ReferenceMaps.UnmodifiableMap<V>
/*     */     implements Int2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2ReferenceSortedMap<V> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Int2ReferenceSortedMap<V> m) {
/* 585 */       super(m);
/* 586 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public IntComparator comparator() {
/* 590 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Int2ReferenceMap.Entry<V>> int2ReferenceEntrySet() {
/* 594 */       if (this.entries == null)
/* 595 */         this.entries = (ObjectSet<Int2ReferenceMap.Entry<V>>)ObjectSortedSets.unmodifiable(this.sortedMap.int2ReferenceEntrySet()); 
/* 596 */       return (ObjectSortedSet<Int2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Integer, V>> entrySet() {
/* 607 */       return (ObjectSortedSet)int2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public IntSortedSet keySet() {
/* 611 */       if (this.keys == null)
/* 612 */         this.keys = IntSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 613 */       return (IntSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Int2ReferenceSortedMap<V> subMap(int from, int to) {
/* 617 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Int2ReferenceSortedMap<V> headMap(int to) {
/* 621 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Int2ReferenceSortedMap<V> tailMap(int from) {
/* 625 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public int firstIntKey() {
/* 629 */       return this.sortedMap.firstIntKey();
/*     */     }
/*     */     
/*     */     public int lastIntKey() {
/* 633 */       return this.sortedMap.lastIntKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer firstKey() {
/* 643 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer lastKey() {
/* 653 */       return this.sortedMap.lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> subMap(Integer from, Integer to) {
/* 663 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> headMap(Integer to) {
/* 673 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> tailMap(Integer from) {
/* 683 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
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
/*     */   public static <V> Int2ReferenceSortedMap<V> unmodifiable(Int2ReferenceSortedMap<V> m) {
/* 696 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2ReferenceSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */