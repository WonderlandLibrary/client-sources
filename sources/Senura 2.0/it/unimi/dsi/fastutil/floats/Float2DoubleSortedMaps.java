/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public final class Float2DoubleSortedMaps
/*     */ {
/*     */   public static Comparator<? super Map.Entry<Float, ?>> entryComparator(FloatComparator comparator) {
/*  43 */     return (x, y) -> comparator.compare(((Float)x.getKey()).floatValue(), ((Float)y.getKey()).floatValue());
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
/*     */   public static ObjectBidirectionalIterator<Float2DoubleMap.Entry> fastIterator(Float2DoubleSortedMap map) {
/*  60 */     ObjectSortedSet<Float2DoubleMap.Entry> entries = map.float2DoubleEntrySet();
/*  61 */     return (entries instanceof Float2DoubleSortedMap.FastSortedEntrySet) ? (
/*  62 */       (Float2DoubleSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static ObjectBidirectionalIterable<Float2DoubleMap.Entry> fastIterable(Float2DoubleSortedMap map) {
/*  79 */     ObjectSortedSet<Float2DoubleMap.Entry> entries = map.float2DoubleEntrySet();
/*     */     
/*  81 */     Objects.requireNonNull((Float2DoubleSortedMap.FastSortedEntrySet)entries); return (entries instanceof Float2DoubleSortedMap.FastSortedEntrySet) ? (Float2DoubleSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  82 */       (ObjectBidirectionalIterable<Float2DoubleMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap
/*     */     extends Float2DoubleMaps.EmptyMap
/*     */     implements Float2DoubleSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public FloatComparator comparator() {
/* 101 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Float2DoubleMap.Entry> float2DoubleEntrySet() {
/* 106 */       return (ObjectSortedSet<Float2DoubleMap.Entry>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Float, Double>> entrySet() {
/* 117 */       return (ObjectSortedSet<Map.Entry<Float, Double>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet keySet() {
/* 122 */       return FloatSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2DoubleSortedMap subMap(float from, float to) {
/* 127 */       return Float2DoubleSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2DoubleSortedMap headMap(float to) {
/* 132 */       return Float2DoubleSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2DoubleSortedMap tailMap(float from) {
/* 137 */       return Float2DoubleSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public float firstFloatKey() {
/* 141 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public float lastFloatKey() {
/* 145 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2DoubleSortedMap headMap(Float oto) {
/* 155 */       return headMap(oto.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2DoubleSortedMap tailMap(Float ofrom) {
/* 165 */       return tailMap(ofrom.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2DoubleSortedMap subMap(Float ofrom, Float oto) {
/* 175 */       return subMap(ofrom.floatValue(), oto.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float firstKey() {
/* 185 */       return Float.valueOf(firstFloatKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float lastKey() {
/* 195 */       return Float.valueOf(lastFloatKey());
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
/*     */   public static class Singleton
/*     */     extends Float2DoubleMaps.Singleton
/*     */     implements Float2DoubleSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final FloatComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(float key, double value, FloatComparator comparator) {
/* 218 */       super(key, value);
/* 219 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(float key, double value) {
/* 222 */       this(key, value, (FloatComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(float k1, float k2) {
/* 226 */       return (this.comparator == null) ? Float.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public FloatComparator comparator() {
/* 230 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Float2DoubleMap.Entry> float2DoubleEntrySet() {
/* 235 */       if (this.entries == null)
/* 236 */         this.entries = (ObjectSet<Float2DoubleMap.Entry>)ObjectSortedSets.singleton(new AbstractFloat2DoubleMap.BasicEntry(this.key, this.value), 
/* 237 */             Float2DoubleSortedMaps.entryComparator(this.comparator)); 
/* 238 */       return (ObjectSortedSet<Float2DoubleMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Float, Double>> entrySet() {
/* 249 */       return (ObjectSortedSet)float2DoubleEntrySet();
/*     */     }
/*     */     
/*     */     public FloatSortedSet keySet() {
/* 253 */       if (this.keys == null)
/* 254 */         this.keys = FloatSortedSets.singleton(this.key, this.comparator); 
/* 255 */       return (FloatSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2DoubleSortedMap subMap(float from, float to) {
/* 260 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 261 */         return this; 
/* 262 */       return Float2DoubleSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2DoubleSortedMap headMap(float to) {
/* 267 */       if (compare(this.key, to) < 0)
/* 268 */         return this; 
/* 269 */       return Float2DoubleSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2DoubleSortedMap tailMap(float from) {
/* 274 */       if (compare(from, this.key) <= 0)
/* 275 */         return this; 
/* 276 */       return Float2DoubleSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public float firstFloatKey() {
/* 280 */       return this.key;
/*     */     }
/*     */     
/*     */     public float lastFloatKey() {
/* 284 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2DoubleSortedMap headMap(Float oto) {
/* 294 */       return headMap(oto.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2DoubleSortedMap tailMap(Float ofrom) {
/* 304 */       return tailMap(ofrom.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2DoubleSortedMap subMap(Float ofrom, Float oto) {
/* 314 */       return subMap(ofrom.floatValue(), oto.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float firstKey() {
/* 324 */       return Float.valueOf(firstFloatKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float lastKey() {
/* 334 */       return Float.valueOf(lastFloatKey());
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
/*     */   public static Float2DoubleSortedMap singleton(Float key, Double value) {
/* 353 */     return new Singleton(key.floatValue(), value.doubleValue());
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
/*     */   public static Float2DoubleSortedMap singleton(Float key, Double value, FloatComparator comparator) {
/* 373 */     return new Singleton(key.floatValue(), value.doubleValue(), comparator);
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
/*     */   public static Float2DoubleSortedMap singleton(float key, double value) {
/* 391 */     return new Singleton(key, value);
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
/*     */   public static Float2DoubleSortedMap singleton(float key, double value, FloatComparator comparator) {
/* 411 */     return new Singleton(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap
/*     */     extends Float2DoubleMaps.SynchronizedMap
/*     */     implements Float2DoubleSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Float2DoubleSortedMap sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Float2DoubleSortedMap m, Object sync) {
/* 421 */       super(m, sync);
/* 422 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Float2DoubleSortedMap m) {
/* 425 */       super(m);
/* 426 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public FloatComparator comparator() {
/* 430 */       synchronized (this.sync) {
/* 431 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Float2DoubleMap.Entry> float2DoubleEntrySet() {
/* 436 */       if (this.entries == null)
/* 437 */         this.entries = (ObjectSet<Float2DoubleMap.Entry>)ObjectSortedSets.synchronize(this.sortedMap.float2DoubleEntrySet(), this.sync); 
/* 438 */       return (ObjectSortedSet<Float2DoubleMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Float, Double>> entrySet() {
/* 449 */       return (ObjectSortedSet)float2DoubleEntrySet();
/*     */     }
/*     */     
/*     */     public FloatSortedSet keySet() {
/* 453 */       if (this.keys == null)
/* 454 */         this.keys = FloatSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 455 */       return (FloatSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Float2DoubleSortedMap subMap(float from, float to) {
/* 459 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Float2DoubleSortedMap headMap(float to) {
/* 463 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Float2DoubleSortedMap tailMap(float from) {
/* 467 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public float firstFloatKey() {
/* 471 */       synchronized (this.sync) {
/* 472 */         return this.sortedMap.firstFloatKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public float lastFloatKey() {
/* 477 */       synchronized (this.sync) {
/* 478 */         return this.sortedMap.lastFloatKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float firstKey() {
/* 489 */       synchronized (this.sync) {
/* 490 */         return this.sortedMap.firstKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float lastKey() {
/* 501 */       synchronized (this.sync) {
/* 502 */         return this.sortedMap.lastKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2DoubleSortedMap subMap(Float from, Float to) {
/* 513 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2DoubleSortedMap headMap(Float to) {
/* 523 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2DoubleSortedMap tailMap(Float from) {
/* 533 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
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
/*     */   public static Float2DoubleSortedMap synchronize(Float2DoubleSortedMap m) {
/* 546 */     return new SynchronizedSortedMap(m);
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
/*     */   public static Float2DoubleSortedMap synchronize(Float2DoubleSortedMap m, Object sync) {
/* 561 */     return new SynchronizedSortedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap
/*     */     extends Float2DoubleMaps.UnmodifiableMap
/*     */     implements Float2DoubleSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Float2DoubleSortedMap sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Float2DoubleSortedMap m) {
/* 571 */       super(m);
/* 572 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public FloatComparator comparator() {
/* 576 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Float2DoubleMap.Entry> float2DoubleEntrySet() {
/* 580 */       if (this.entries == null)
/* 581 */         this.entries = (ObjectSet<Float2DoubleMap.Entry>)ObjectSortedSets.unmodifiable(this.sortedMap.float2DoubleEntrySet()); 
/* 582 */       return (ObjectSortedSet<Float2DoubleMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Float, Double>> entrySet() {
/* 593 */       return (ObjectSortedSet)float2DoubleEntrySet();
/*     */     }
/*     */     
/*     */     public FloatSortedSet keySet() {
/* 597 */       if (this.keys == null)
/* 598 */         this.keys = FloatSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 599 */       return (FloatSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Float2DoubleSortedMap subMap(float from, float to) {
/* 603 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Float2DoubleSortedMap headMap(float to) {
/* 607 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Float2DoubleSortedMap tailMap(float from) {
/* 611 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public float firstFloatKey() {
/* 615 */       return this.sortedMap.firstFloatKey();
/*     */     }
/*     */     
/*     */     public float lastFloatKey() {
/* 619 */       return this.sortedMap.lastFloatKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float firstKey() {
/* 629 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float lastKey() {
/* 639 */       return this.sortedMap.lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2DoubleSortedMap subMap(Float from, Float to) {
/* 649 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2DoubleSortedMap headMap(Float to) {
/* 659 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2DoubleSortedMap tailMap(Float from) {
/* 669 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
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
/*     */   public static Float2DoubleSortedMap unmodifiable(Float2DoubleSortedMap m) {
/* 682 */     return new UnmodifiableSortedMap(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2DoubleSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */