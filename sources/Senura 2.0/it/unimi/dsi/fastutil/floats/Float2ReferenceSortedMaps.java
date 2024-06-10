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
/*     */ public final class Float2ReferenceSortedMaps
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
/*     */   
/*     */   public static <V> ObjectBidirectionalIterator<Float2ReferenceMap.Entry<V>> fastIterator(Float2ReferenceSortedMap<V> map) {
/*  61 */     ObjectSortedSet<Float2ReferenceMap.Entry<V>> entries = map.float2ReferenceEntrySet();
/*  62 */     return (entries instanceof Float2ReferenceSortedMap.FastSortedEntrySet) ? (
/*  63 */       (Float2ReferenceSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static <V> ObjectBidirectionalIterable<Float2ReferenceMap.Entry<V>> fastIterable(Float2ReferenceSortedMap<V> map) {
/*  81 */     ObjectSortedSet<Float2ReferenceMap.Entry<V>> entries = map.float2ReferenceEntrySet();
/*     */     
/*  83 */     Objects.requireNonNull((Float2ReferenceSortedMap.FastSortedEntrySet)entries); return (entries instanceof Float2ReferenceSortedMap.FastSortedEntrySet) ? (Float2ReferenceSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  84 */       (ObjectBidirectionalIterable<Float2ReferenceMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<V>
/*     */     extends Float2ReferenceMaps.EmptyMap<V>
/*     */     implements Float2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public FloatComparator comparator() {
/* 103 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Float2ReferenceMap.Entry<V>> float2ReferenceEntrySet() {
/* 108 */       return (ObjectSortedSet<Float2ReferenceMap.Entry<V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Float, V>> entrySet() {
/* 119 */       return (ObjectSortedSet<Map.Entry<Float, V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet keySet() {
/* 124 */       return FloatSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2ReferenceSortedMap<V> subMap(float from, float to) {
/* 129 */       return Float2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2ReferenceSortedMap<V> headMap(float to) {
/* 134 */       return Float2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2ReferenceSortedMap<V> tailMap(float from) {
/* 139 */       return Float2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public float firstFloatKey() {
/* 143 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public float lastFloatKey() {
/* 147 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2ReferenceSortedMap<V> headMap(Float oto) {
/* 157 */       return headMap(oto.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2ReferenceSortedMap<V> tailMap(Float ofrom) {
/* 167 */       return tailMap(ofrom.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2ReferenceSortedMap<V> subMap(Float ofrom, Float oto) {
/* 177 */       return subMap(ofrom.floatValue(), oto.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float firstKey() {
/* 187 */       return Float.valueOf(firstFloatKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float lastKey() {
/* 197 */       return Float.valueOf(lastFloatKey());
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
/*     */   public static <V> Float2ReferenceSortedMap<V> emptyMap() {
/* 215 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Float2ReferenceMaps.Singleton<V>
/*     */     implements Float2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final FloatComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(float key, V value, FloatComparator comparator) {
/* 232 */       super(key, value);
/* 233 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(float key, V value) {
/* 236 */       this(key, value, (FloatComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(float k1, float k2) {
/* 240 */       return (this.comparator == null) ? Float.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public FloatComparator comparator() {
/* 244 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Float2ReferenceMap.Entry<V>> float2ReferenceEntrySet() {
/* 249 */       if (this.entries == null)
/* 250 */         this.entries = (ObjectSet<Float2ReferenceMap.Entry<V>>)ObjectSortedSets.singleton(new AbstractFloat2ReferenceMap.BasicEntry<>(this.key, this.value), 
/* 251 */             Float2ReferenceSortedMaps.entryComparator(this.comparator)); 
/* 252 */       return (ObjectSortedSet<Float2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Float, V>> entrySet() {
/* 263 */       return (ObjectSortedSet)float2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public FloatSortedSet keySet() {
/* 267 */       if (this.keys == null)
/* 268 */         this.keys = FloatSortedSets.singleton(this.key, this.comparator); 
/* 269 */       return (FloatSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2ReferenceSortedMap<V> subMap(float from, float to) {
/* 274 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 275 */         return this; 
/* 276 */       return Float2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2ReferenceSortedMap<V> headMap(float to) {
/* 281 */       if (compare(this.key, to) < 0)
/* 282 */         return this; 
/* 283 */       return Float2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2ReferenceSortedMap<V> tailMap(float from) {
/* 288 */       if (compare(from, this.key) <= 0)
/* 289 */         return this; 
/* 290 */       return Float2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public float firstFloatKey() {
/* 294 */       return this.key;
/*     */     }
/*     */     
/*     */     public float lastFloatKey() {
/* 298 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2ReferenceSortedMap<V> headMap(Float oto) {
/* 308 */       return headMap(oto.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2ReferenceSortedMap<V> tailMap(Float ofrom) {
/* 318 */       return tailMap(ofrom.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2ReferenceSortedMap<V> subMap(Float ofrom, Float oto) {
/* 328 */       return subMap(ofrom.floatValue(), oto.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float firstKey() {
/* 338 */       return Float.valueOf(firstFloatKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float lastKey() {
/* 348 */       return Float.valueOf(lastFloatKey());
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
/*     */   public static <V> Float2ReferenceSortedMap<V> singleton(Float key, V value) {
/* 367 */     return new Singleton<>(key.floatValue(), value);
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
/*     */   public static <V> Float2ReferenceSortedMap<V> singleton(Float key, V value, FloatComparator comparator) {
/* 387 */     return new Singleton<>(key.floatValue(), value, comparator);
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
/*     */   public static <V> Float2ReferenceSortedMap<V> singleton(float key, V value) {
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
/*     */   public static <V> Float2ReferenceSortedMap<V> singleton(float key, V value, FloatComparator comparator) {
/* 426 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<V>
/*     */     extends Float2ReferenceMaps.SynchronizedMap<V>
/*     */     implements Float2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Float2ReferenceSortedMap<V> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Float2ReferenceSortedMap<V> m, Object sync) {
/* 436 */       super(m, sync);
/* 437 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Float2ReferenceSortedMap<V> m) {
/* 440 */       super(m);
/* 441 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public FloatComparator comparator() {
/* 445 */       synchronized (this.sync) {
/* 446 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Float2ReferenceMap.Entry<V>> float2ReferenceEntrySet() {
/* 451 */       if (this.entries == null)
/* 452 */         this.entries = (ObjectSet<Float2ReferenceMap.Entry<V>>)ObjectSortedSets.synchronize(this.sortedMap.float2ReferenceEntrySet(), this.sync); 
/* 453 */       return (ObjectSortedSet<Float2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Float, V>> entrySet() {
/* 464 */       return (ObjectSortedSet)float2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public FloatSortedSet keySet() {
/* 468 */       if (this.keys == null)
/* 469 */         this.keys = FloatSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 470 */       return (FloatSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Float2ReferenceSortedMap<V> subMap(float from, float to) {
/* 474 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Float2ReferenceSortedMap<V> headMap(float to) {
/* 478 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Float2ReferenceSortedMap<V> tailMap(float from) {
/* 482 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public float firstFloatKey() {
/* 486 */       synchronized (this.sync) {
/* 487 */         return this.sortedMap.firstFloatKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public float lastFloatKey() {
/* 492 */       synchronized (this.sync) {
/* 493 */         return this.sortedMap.lastFloatKey();
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
/*     */     public Float lastKey() {
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
/*     */     public Float2ReferenceSortedMap<V> subMap(Float from, Float to) {
/* 528 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2ReferenceSortedMap<V> headMap(Float to) {
/* 538 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2ReferenceSortedMap<V> tailMap(Float from) {
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
/*     */   public static <V> Float2ReferenceSortedMap<V> synchronize(Float2ReferenceSortedMap<V> m) {
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
/*     */   public static <V> Float2ReferenceSortedMap<V> synchronize(Float2ReferenceSortedMap<V> m, Object sync) {
/* 576 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<V>
/*     */     extends Float2ReferenceMaps.UnmodifiableMap<V>
/*     */     implements Float2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Float2ReferenceSortedMap<V> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Float2ReferenceSortedMap<V> m) {
/* 586 */       super(m);
/* 587 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public FloatComparator comparator() {
/* 591 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Float2ReferenceMap.Entry<V>> float2ReferenceEntrySet() {
/* 595 */       if (this.entries == null)
/* 596 */         this.entries = (ObjectSet<Float2ReferenceMap.Entry<V>>)ObjectSortedSets.unmodifiable(this.sortedMap.float2ReferenceEntrySet()); 
/* 597 */       return (ObjectSortedSet<Float2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Float, V>> entrySet() {
/* 608 */       return (ObjectSortedSet)float2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public FloatSortedSet keySet() {
/* 612 */       if (this.keys == null)
/* 613 */         this.keys = FloatSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 614 */       return (FloatSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Float2ReferenceSortedMap<V> subMap(float from, float to) {
/* 618 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Float2ReferenceSortedMap<V> headMap(float to) {
/* 622 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Float2ReferenceSortedMap<V> tailMap(float from) {
/* 626 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public float firstFloatKey() {
/* 630 */       return this.sortedMap.firstFloatKey();
/*     */     }
/*     */     
/*     */     public float lastFloatKey() {
/* 634 */       return this.sortedMap.lastFloatKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float firstKey() {
/* 644 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float lastKey() {
/* 654 */       return this.sortedMap.lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2ReferenceSortedMap<V> subMap(Float from, Float to) {
/* 664 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2ReferenceSortedMap<V> headMap(Float to) {
/* 674 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2ReferenceSortedMap<V> tailMap(Float from) {
/* 684 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
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
/*     */   public static <V> Float2ReferenceSortedMap<V> unmodifiable(Float2ReferenceSortedMap<V> m) {
/* 697 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2ReferenceSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */