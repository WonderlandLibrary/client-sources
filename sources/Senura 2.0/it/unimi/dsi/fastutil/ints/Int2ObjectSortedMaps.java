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
/*     */ public final class Int2ObjectSortedMaps
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
/*     */   public static <V> ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> fastIterator(Int2ObjectSortedMap<V> map) {
/*  60 */     ObjectSortedSet<Int2ObjectMap.Entry<V>> entries = map.int2ObjectEntrySet();
/*  61 */     return (entries instanceof Int2ObjectSortedMap.FastSortedEntrySet) ? (
/*  62 */       (Int2ObjectSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static <V> ObjectBidirectionalIterable<Int2ObjectMap.Entry<V>> fastIterable(Int2ObjectSortedMap<V> map) {
/*  79 */     ObjectSortedSet<Int2ObjectMap.Entry<V>> entries = map.int2ObjectEntrySet();
/*     */     
/*  81 */     Objects.requireNonNull((Int2ObjectSortedMap.FastSortedEntrySet)entries); return (entries instanceof Int2ObjectSortedMap.FastSortedEntrySet) ? (Int2ObjectSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  82 */       (ObjectBidirectionalIterable<Int2ObjectMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<V>
/*     */     extends Int2ObjectMaps.EmptyMap<V>
/*     */     implements Int2ObjectSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public IntComparator comparator() {
/* 101 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
/* 106 */       return (ObjectSortedSet<Int2ObjectMap.Entry<V>>)ObjectSortedSets.EMPTY_SET;
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
/* 117 */       return (ObjectSortedSet<Map.Entry<Integer, V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet keySet() {
/* 122 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ObjectSortedMap<V> subMap(int from, int to) {
/* 127 */       return Int2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ObjectSortedMap<V> headMap(int to) {
/* 132 */       return Int2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ObjectSortedMap<V> tailMap(int from) {
/* 137 */       return Int2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public int firstIntKey() {
/* 141 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public int lastIntKey() {
/* 145 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ObjectSortedMap<V> headMap(Integer oto) {
/* 155 */       return headMap(oto.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ObjectSortedMap<V> tailMap(Integer ofrom) {
/* 165 */       return tailMap(ofrom.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ObjectSortedMap<V> subMap(Integer ofrom, Integer oto) {
/* 175 */       return subMap(ofrom.intValue(), oto.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer firstKey() {
/* 185 */       return Integer.valueOf(firstIntKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer lastKey() {
/* 195 */       return Integer.valueOf(lastIntKey());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 202 */   public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Int2ObjectSortedMap<V> emptyMap() {
/* 213 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Int2ObjectMaps.Singleton<V>
/*     */     implements Int2ObjectSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final IntComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(int key, V value, IntComparator comparator) {
/* 230 */       super(key, value);
/* 231 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(int key, V value) {
/* 234 */       this(key, value, (IntComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(int k1, int k2) {
/* 238 */       return (this.comparator == null) ? Integer.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public IntComparator comparator() {
/* 242 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
/* 247 */       if (this.entries == null)
/* 248 */         this.entries = (ObjectSet<Int2ObjectMap.Entry<V>>)ObjectSortedSets.singleton(new AbstractInt2ObjectMap.BasicEntry<>(this.key, this.value), 
/* 249 */             Int2ObjectSortedMaps.entryComparator(this.comparator)); 
/* 250 */       return (ObjectSortedSet<Int2ObjectMap.Entry<V>>)this.entries;
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
/* 261 */       return (ObjectSortedSet)int2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public IntSortedSet keySet() {
/* 265 */       if (this.keys == null)
/* 266 */         this.keys = IntSortedSets.singleton(this.key, this.comparator); 
/* 267 */       return (IntSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ObjectSortedMap<V> subMap(int from, int to) {
/* 272 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 273 */         return this; 
/* 274 */       return Int2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ObjectSortedMap<V> headMap(int to) {
/* 279 */       if (compare(this.key, to) < 0)
/* 280 */         return this; 
/* 281 */       return Int2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ObjectSortedMap<V> tailMap(int from) {
/* 286 */       if (compare(from, this.key) <= 0)
/* 287 */         return this; 
/* 288 */       return Int2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public int firstIntKey() {
/* 292 */       return this.key;
/*     */     }
/*     */     
/*     */     public int lastIntKey() {
/* 296 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ObjectSortedMap<V> headMap(Integer oto) {
/* 306 */       return headMap(oto.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ObjectSortedMap<V> tailMap(Integer ofrom) {
/* 316 */       return tailMap(ofrom.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ObjectSortedMap<V> subMap(Integer ofrom, Integer oto) {
/* 326 */       return subMap(ofrom.intValue(), oto.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer firstKey() {
/* 336 */       return Integer.valueOf(firstIntKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer lastKey() {
/* 346 */       return Integer.valueOf(lastIntKey());
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
/*     */   public static <V> Int2ObjectSortedMap<V> singleton(Integer key, V value) {
/* 365 */     return new Singleton<>(key.intValue(), value);
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
/*     */   public static <V> Int2ObjectSortedMap<V> singleton(Integer key, V value, IntComparator comparator) {
/* 385 */     return new Singleton<>(key.intValue(), value, comparator);
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
/*     */   public static <V> Int2ObjectSortedMap<V> singleton(int key, V value) {
/* 403 */     return new Singleton<>(key, value);
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
/*     */   public static <V> Int2ObjectSortedMap<V> singleton(int key, V value, IntComparator comparator) {
/* 423 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<V>
/*     */     extends Int2ObjectMaps.SynchronizedMap<V>
/*     */     implements Int2ObjectSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2ObjectSortedMap<V> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Int2ObjectSortedMap<V> m, Object sync) {
/* 433 */       super(m, sync);
/* 434 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Int2ObjectSortedMap<V> m) {
/* 437 */       super(m);
/* 438 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public IntComparator comparator() {
/* 442 */       synchronized (this.sync) {
/* 443 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
/* 448 */       if (this.entries == null)
/* 449 */         this.entries = (ObjectSet<Int2ObjectMap.Entry<V>>)ObjectSortedSets.synchronize(this.sortedMap.int2ObjectEntrySet(), this.sync); 
/* 450 */       return (ObjectSortedSet<Int2ObjectMap.Entry<V>>)this.entries;
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
/* 461 */       return (ObjectSortedSet)int2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public IntSortedSet keySet() {
/* 465 */       if (this.keys == null)
/* 466 */         this.keys = IntSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 467 */       return (IntSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Int2ObjectSortedMap<V> subMap(int from, int to) {
/* 471 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Int2ObjectSortedMap<V> headMap(int to) {
/* 475 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Int2ObjectSortedMap<V> tailMap(int from) {
/* 479 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public int firstIntKey() {
/* 483 */       synchronized (this.sync) {
/* 484 */         return this.sortedMap.firstIntKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int lastIntKey() {
/* 489 */       synchronized (this.sync) {
/* 490 */         return this.sortedMap.lastIntKey();
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
/* 501 */       synchronized (this.sync) {
/* 502 */         return this.sortedMap.firstKey();
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
/* 513 */       synchronized (this.sync) {
/* 514 */         return this.sortedMap.lastKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ObjectSortedMap<V> subMap(Integer from, Integer to) {
/* 525 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ObjectSortedMap<V> headMap(Integer to) {
/* 535 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ObjectSortedMap<V> tailMap(Integer from) {
/* 545 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
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
/*     */   public static <V> Int2ObjectSortedMap<V> synchronize(Int2ObjectSortedMap<V> m) {
/* 558 */     return new SynchronizedSortedMap<>(m);
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
/*     */   public static <V> Int2ObjectSortedMap<V> synchronize(Int2ObjectSortedMap<V> m, Object sync) {
/* 573 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<V>
/*     */     extends Int2ObjectMaps.UnmodifiableMap<V>
/*     */     implements Int2ObjectSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2ObjectSortedMap<V> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Int2ObjectSortedMap<V> m) {
/* 583 */       super(m);
/* 584 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public IntComparator comparator() {
/* 588 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
/* 592 */       if (this.entries == null)
/* 593 */         this.entries = (ObjectSet<Int2ObjectMap.Entry<V>>)ObjectSortedSets.unmodifiable(this.sortedMap.int2ObjectEntrySet()); 
/* 594 */       return (ObjectSortedSet<Int2ObjectMap.Entry<V>>)this.entries;
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
/* 605 */       return (ObjectSortedSet)int2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public IntSortedSet keySet() {
/* 609 */       if (this.keys == null)
/* 610 */         this.keys = IntSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 611 */       return (IntSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Int2ObjectSortedMap<V> subMap(int from, int to) {
/* 615 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Int2ObjectSortedMap<V> headMap(int to) {
/* 619 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Int2ObjectSortedMap<V> tailMap(int from) {
/* 623 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public int firstIntKey() {
/* 627 */       return this.sortedMap.firstIntKey();
/*     */     }
/*     */     
/*     */     public int lastIntKey() {
/* 631 */       return this.sortedMap.lastIntKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer firstKey() {
/* 641 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer lastKey() {
/* 651 */       return this.sortedMap.lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ObjectSortedMap<V> subMap(Integer from, Integer to) {
/* 661 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ObjectSortedMap<V> headMap(Integer to) {
/* 671 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ObjectSortedMap<V> tailMap(Integer from) {
/* 681 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
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
/*     */   public static <V> Int2ObjectSortedMap<V> unmodifiable(Int2ObjectSortedMap<V> m) {
/* 694 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2ObjectSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */