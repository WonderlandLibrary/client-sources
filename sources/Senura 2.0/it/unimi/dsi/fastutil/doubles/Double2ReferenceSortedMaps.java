/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ public final class Double2ReferenceSortedMaps
/*     */ {
/*     */   public static Comparator<? super Map.Entry<Double, ?>> entryComparator(DoubleComparator comparator) {
/*  43 */     return (x, y) -> comparator.compare(((Double)x.getKey()).doubleValue(), ((Double)y.getKey()).doubleValue());
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
/*     */   public static <V> ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> fastIterator(Double2ReferenceSortedMap<V> map) {
/*  61 */     ObjectSortedSet<Double2ReferenceMap.Entry<V>> entries = map.double2ReferenceEntrySet();
/*  62 */     return (entries instanceof Double2ReferenceSortedMap.FastSortedEntrySet) ? (
/*  63 */       (Double2ReferenceSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static <V> ObjectBidirectionalIterable<Double2ReferenceMap.Entry<V>> fastIterable(Double2ReferenceSortedMap<V> map) {
/*  81 */     ObjectSortedSet<Double2ReferenceMap.Entry<V>> entries = map.double2ReferenceEntrySet();
/*     */     
/*  83 */     Objects.requireNonNull((Double2ReferenceSortedMap.FastSortedEntrySet)entries); return (entries instanceof Double2ReferenceSortedMap.FastSortedEntrySet) ? (Double2ReferenceSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  84 */       (ObjectBidirectionalIterable<Double2ReferenceMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<V>
/*     */     extends Double2ReferenceMaps.EmptyMap<V>
/*     */     implements Double2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DoubleComparator comparator() {
/* 103 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
/* 108 */       return (ObjectSortedSet<Double2ReferenceMap.Entry<V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Double, V>> entrySet() {
/* 119 */       return (ObjectSortedSet<Map.Entry<Double, V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 124 */       return DoubleSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2ReferenceSortedMap<V> subMap(double from, double to) {
/* 129 */       return Double2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2ReferenceSortedMap<V> headMap(double to) {
/* 134 */       return Double2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2ReferenceSortedMap<V> tailMap(double from) {
/* 139 */       return Double2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public double firstDoubleKey() {
/* 143 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public double lastDoubleKey() {
/* 147 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2ReferenceSortedMap<V> headMap(Double oto) {
/* 157 */       return headMap(oto.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2ReferenceSortedMap<V> tailMap(Double ofrom) {
/* 167 */       return tailMap(ofrom.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2ReferenceSortedMap<V> subMap(Double ofrom, Double oto) {
/* 177 */       return subMap(ofrom.doubleValue(), oto.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double firstKey() {
/* 187 */       return Double.valueOf(firstDoubleKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double lastKey() {
/* 197 */       return Double.valueOf(lastDoubleKey());
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
/*     */   public static <V> Double2ReferenceSortedMap<V> emptyMap() {
/* 215 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Double2ReferenceMaps.Singleton<V>
/*     */     implements Double2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final DoubleComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(double key, V value, DoubleComparator comparator) {
/* 232 */       super(key, value);
/* 233 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(double key, V value) {
/* 236 */       this(key, value, (DoubleComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(double k1, double k2) {
/* 240 */       return (this.comparator == null) ? Double.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public DoubleComparator comparator() {
/* 244 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
/* 249 */       if (this.entries == null)
/* 250 */         this.entries = (ObjectSet<Double2ReferenceMap.Entry<V>>)ObjectSortedSets.singleton(new AbstractDouble2ReferenceMap.BasicEntry<>(this.key, this.value), 
/* 251 */             Double2ReferenceSortedMaps.entryComparator(this.comparator)); 
/* 252 */       return (ObjectSortedSet<Double2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Double, V>> entrySet() {
/* 263 */       return (ObjectSortedSet)double2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 267 */       if (this.keys == null)
/* 268 */         this.keys = DoubleSortedSets.singleton(this.key, this.comparator); 
/* 269 */       return (DoubleSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2ReferenceSortedMap<V> subMap(double from, double to) {
/* 274 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 275 */         return this; 
/* 276 */       return Double2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2ReferenceSortedMap<V> headMap(double to) {
/* 281 */       if (compare(this.key, to) < 0)
/* 282 */         return this; 
/* 283 */       return Double2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2ReferenceSortedMap<V> tailMap(double from) {
/* 288 */       if (compare(from, this.key) <= 0)
/* 289 */         return this; 
/* 290 */       return Double2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public double firstDoubleKey() {
/* 294 */       return this.key;
/*     */     }
/*     */     
/*     */     public double lastDoubleKey() {
/* 298 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2ReferenceSortedMap<V> headMap(Double oto) {
/* 308 */       return headMap(oto.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2ReferenceSortedMap<V> tailMap(Double ofrom) {
/* 318 */       return tailMap(ofrom.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2ReferenceSortedMap<V> subMap(Double ofrom, Double oto) {
/* 328 */       return subMap(ofrom.doubleValue(), oto.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double firstKey() {
/* 338 */       return Double.valueOf(firstDoubleKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double lastKey() {
/* 348 */       return Double.valueOf(lastDoubleKey());
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
/*     */   public static <V> Double2ReferenceSortedMap<V> singleton(Double key, V value) {
/* 367 */     return new Singleton<>(key.doubleValue(), value);
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
/*     */   public static <V> Double2ReferenceSortedMap<V> singleton(Double key, V value, DoubleComparator comparator) {
/* 387 */     return new Singleton<>(key.doubleValue(), value, comparator);
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
/*     */   public static <V> Double2ReferenceSortedMap<V> singleton(double key, V value) {
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
/*     */   
/*     */   public static <V> Double2ReferenceSortedMap<V> singleton(double key, V value, DoubleComparator comparator) {
/* 426 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<V>
/*     */     extends Double2ReferenceMaps.SynchronizedMap<V>
/*     */     implements Double2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2ReferenceSortedMap<V> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Double2ReferenceSortedMap<V> m, Object sync) {
/* 436 */       super(m, sync);
/* 437 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Double2ReferenceSortedMap<V> m) {
/* 440 */       super(m);
/* 441 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public DoubleComparator comparator() {
/* 445 */       synchronized (this.sync) {
/* 446 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
/* 451 */       if (this.entries == null)
/* 452 */         this.entries = (ObjectSet<Double2ReferenceMap.Entry<V>>)ObjectSortedSets.synchronize(this.sortedMap.double2ReferenceEntrySet(), this.sync); 
/* 453 */       return (ObjectSortedSet<Double2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Double, V>> entrySet() {
/* 464 */       return (ObjectSortedSet)double2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 468 */       if (this.keys == null)
/* 469 */         this.keys = DoubleSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 470 */       return (DoubleSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Double2ReferenceSortedMap<V> subMap(double from, double to) {
/* 474 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Double2ReferenceSortedMap<V> headMap(double to) {
/* 478 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Double2ReferenceSortedMap<V> tailMap(double from) {
/* 482 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public double firstDoubleKey() {
/* 486 */       synchronized (this.sync) {
/* 487 */         return this.sortedMap.firstDoubleKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public double lastDoubleKey() {
/* 492 */       synchronized (this.sync) {
/* 493 */         return this.sortedMap.lastDoubleKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double firstKey() {
/* 504 */       synchronized (this.sync) {
/* 505 */         return this.sortedMap.firstKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double lastKey() {
/* 516 */       synchronized (this.sync) {
/* 517 */         return this.sortedMap.lastKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2ReferenceSortedMap<V> subMap(Double from, Double to) {
/* 528 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2ReferenceSortedMap<V> headMap(Double to) {
/* 538 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2ReferenceSortedMap<V> tailMap(Double from) {
/* 548 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
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
/*     */   public static <V> Double2ReferenceSortedMap<V> synchronize(Double2ReferenceSortedMap<V> m) {
/* 561 */     return new SynchronizedSortedMap<>(m);
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
/*     */   public static <V> Double2ReferenceSortedMap<V> synchronize(Double2ReferenceSortedMap<V> m, Object sync) {
/* 577 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<V>
/*     */     extends Double2ReferenceMaps.UnmodifiableMap<V>
/*     */     implements Double2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2ReferenceSortedMap<V> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Double2ReferenceSortedMap<V> m) {
/* 587 */       super(m);
/* 588 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public DoubleComparator comparator() {
/* 592 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
/* 596 */       if (this.entries == null)
/* 597 */         this.entries = (ObjectSet<Double2ReferenceMap.Entry<V>>)ObjectSortedSets.unmodifiable(this.sortedMap.double2ReferenceEntrySet()); 
/* 598 */       return (ObjectSortedSet<Double2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Double, V>> entrySet() {
/* 609 */       return (ObjectSortedSet)double2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 613 */       if (this.keys == null)
/* 614 */         this.keys = DoubleSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 615 */       return (DoubleSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Double2ReferenceSortedMap<V> subMap(double from, double to) {
/* 619 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Double2ReferenceSortedMap<V> headMap(double to) {
/* 623 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Double2ReferenceSortedMap<V> tailMap(double from) {
/* 627 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public double firstDoubleKey() {
/* 631 */       return this.sortedMap.firstDoubleKey();
/*     */     }
/*     */     
/*     */     public double lastDoubleKey() {
/* 635 */       return this.sortedMap.lastDoubleKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double firstKey() {
/* 645 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double lastKey() {
/* 655 */       return this.sortedMap.lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2ReferenceSortedMap<V> subMap(Double from, Double to) {
/* 665 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2ReferenceSortedMap<V> headMap(Double to) {
/* 675 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2ReferenceSortedMap<V> tailMap(Double from) {
/* 685 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
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
/*     */   public static <V> Double2ReferenceSortedMap<V> unmodifiable(Double2ReferenceSortedMap<V> m) {
/* 698 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2ReferenceSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */