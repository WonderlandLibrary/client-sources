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
/*     */ public final class Float2ObjectSortedMaps
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
/*     */   public static <V> ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>> fastIterator(Float2ObjectSortedMap<V> map) {
/*  60 */     ObjectSortedSet<Float2ObjectMap.Entry<V>> entries = map.float2ObjectEntrySet();
/*  61 */     return (entries instanceof Float2ObjectSortedMap.FastSortedEntrySet) ? (
/*  62 */       (Float2ObjectSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static <V> ObjectBidirectionalIterable<Float2ObjectMap.Entry<V>> fastIterable(Float2ObjectSortedMap<V> map) {
/*  79 */     ObjectSortedSet<Float2ObjectMap.Entry<V>> entries = map.float2ObjectEntrySet();
/*     */     
/*  81 */     Objects.requireNonNull((Float2ObjectSortedMap.FastSortedEntrySet)entries); return (entries instanceof Float2ObjectSortedMap.FastSortedEntrySet) ? (Float2ObjectSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  82 */       (ObjectBidirectionalIterable<Float2ObjectMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<V>
/*     */     extends Float2ObjectMaps.EmptyMap<V>
/*     */     implements Float2ObjectSortedMap<V>, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Float2ObjectMap.Entry<V>> float2ObjectEntrySet() {
/* 106 */       return (ObjectSortedSet<Float2ObjectMap.Entry<V>>)ObjectSortedSets.EMPTY_SET;
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
/* 117 */       return (ObjectSortedSet<Map.Entry<Float, V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet keySet() {
/* 122 */       return FloatSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2ObjectSortedMap<V> subMap(float from, float to) {
/* 127 */       return Float2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2ObjectSortedMap<V> headMap(float to) {
/* 132 */       return Float2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2ObjectSortedMap<V> tailMap(float from) {
/* 137 */       return Float2ObjectSortedMaps.EMPTY_MAP;
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
/*     */     public Float2ObjectSortedMap<V> headMap(Float oto) {
/* 155 */       return headMap(oto.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2ObjectSortedMap<V> tailMap(Float ofrom) {
/* 165 */       return tailMap(ofrom.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2ObjectSortedMap<V> subMap(Float ofrom, Float oto) {
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Float2ObjectSortedMap<V> emptyMap() {
/* 213 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Float2ObjectMaps.Singleton<V>
/*     */     implements Float2ObjectSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final FloatComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(float key, V value, FloatComparator comparator) {
/* 230 */       super(key, value);
/* 231 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(float key, V value) {
/* 234 */       this(key, value, (FloatComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(float k1, float k2) {
/* 238 */       return (this.comparator == null) ? Float.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public FloatComparator comparator() {
/* 242 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Float2ObjectMap.Entry<V>> float2ObjectEntrySet() {
/* 247 */       if (this.entries == null)
/* 248 */         this.entries = (ObjectSet<Float2ObjectMap.Entry<V>>)ObjectSortedSets.singleton(new AbstractFloat2ObjectMap.BasicEntry<>(this.key, this.value), 
/* 249 */             Float2ObjectSortedMaps.entryComparator(this.comparator)); 
/* 250 */       return (ObjectSortedSet<Float2ObjectMap.Entry<V>>)this.entries;
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
/* 261 */       return (ObjectSortedSet)float2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public FloatSortedSet keySet() {
/* 265 */       if (this.keys == null)
/* 266 */         this.keys = FloatSortedSets.singleton(this.key, this.comparator); 
/* 267 */       return (FloatSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2ObjectSortedMap<V> subMap(float from, float to) {
/* 272 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 273 */         return this; 
/* 274 */       return Float2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2ObjectSortedMap<V> headMap(float to) {
/* 279 */       if (compare(this.key, to) < 0)
/* 280 */         return this; 
/* 281 */       return Float2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2ObjectSortedMap<V> tailMap(float from) {
/* 286 */       if (compare(from, this.key) <= 0)
/* 287 */         return this; 
/* 288 */       return Float2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public float firstFloatKey() {
/* 292 */       return this.key;
/*     */     }
/*     */     
/*     */     public float lastFloatKey() {
/* 296 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2ObjectSortedMap<V> headMap(Float oto) {
/* 306 */       return headMap(oto.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2ObjectSortedMap<V> tailMap(Float ofrom) {
/* 316 */       return tailMap(ofrom.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2ObjectSortedMap<V> subMap(Float ofrom, Float oto) {
/* 326 */       return subMap(ofrom.floatValue(), oto.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float firstKey() {
/* 336 */       return Float.valueOf(firstFloatKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float lastKey() {
/* 346 */       return Float.valueOf(lastFloatKey());
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
/*     */   public static <V> Float2ObjectSortedMap<V> singleton(Float key, V value) {
/* 365 */     return new Singleton<>(key.floatValue(), value);
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
/*     */   public static <V> Float2ObjectSortedMap<V> singleton(Float key, V value, FloatComparator comparator) {
/* 385 */     return new Singleton<>(key.floatValue(), value, comparator);
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
/*     */   public static <V> Float2ObjectSortedMap<V> singleton(float key, V value) {
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
/*     */   public static <V> Float2ObjectSortedMap<V> singleton(float key, V value, FloatComparator comparator) {
/* 423 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<V>
/*     */     extends Float2ObjectMaps.SynchronizedMap<V>
/*     */     implements Float2ObjectSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Float2ObjectSortedMap<V> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Float2ObjectSortedMap<V> m, Object sync) {
/* 433 */       super(m, sync);
/* 434 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Float2ObjectSortedMap<V> m) {
/* 437 */       super(m);
/* 438 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public FloatComparator comparator() {
/* 442 */       synchronized (this.sync) {
/* 443 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Float2ObjectMap.Entry<V>> float2ObjectEntrySet() {
/* 448 */       if (this.entries == null)
/* 449 */         this.entries = (ObjectSet<Float2ObjectMap.Entry<V>>)ObjectSortedSets.synchronize(this.sortedMap.float2ObjectEntrySet(), this.sync); 
/* 450 */       return (ObjectSortedSet<Float2ObjectMap.Entry<V>>)this.entries;
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
/* 461 */       return (ObjectSortedSet)float2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public FloatSortedSet keySet() {
/* 465 */       if (this.keys == null)
/* 466 */         this.keys = FloatSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 467 */       return (FloatSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Float2ObjectSortedMap<V> subMap(float from, float to) {
/* 471 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Float2ObjectSortedMap<V> headMap(float to) {
/* 475 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Float2ObjectSortedMap<V> tailMap(float from) {
/* 479 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public float firstFloatKey() {
/* 483 */       synchronized (this.sync) {
/* 484 */         return this.sortedMap.firstFloatKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public float lastFloatKey() {
/* 489 */       synchronized (this.sync) {
/* 490 */         return this.sortedMap.lastFloatKey();
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
/*     */     public Float lastKey() {
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
/*     */     public Float2ObjectSortedMap<V> subMap(Float from, Float to) {
/* 525 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2ObjectSortedMap<V> headMap(Float to) {
/* 535 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2ObjectSortedMap<V> tailMap(Float from) {
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
/*     */   public static <V> Float2ObjectSortedMap<V> synchronize(Float2ObjectSortedMap<V> m) {
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
/*     */   public static <V> Float2ObjectSortedMap<V> synchronize(Float2ObjectSortedMap<V> m, Object sync) {
/* 573 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<V>
/*     */     extends Float2ObjectMaps.UnmodifiableMap<V>
/*     */     implements Float2ObjectSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Float2ObjectSortedMap<V> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Float2ObjectSortedMap<V> m) {
/* 583 */       super(m);
/* 584 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public FloatComparator comparator() {
/* 588 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Float2ObjectMap.Entry<V>> float2ObjectEntrySet() {
/* 592 */       if (this.entries == null)
/* 593 */         this.entries = (ObjectSet<Float2ObjectMap.Entry<V>>)ObjectSortedSets.unmodifiable(this.sortedMap.float2ObjectEntrySet()); 
/* 594 */       return (ObjectSortedSet<Float2ObjectMap.Entry<V>>)this.entries;
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
/* 605 */       return (ObjectSortedSet)float2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public FloatSortedSet keySet() {
/* 609 */       if (this.keys == null)
/* 610 */         this.keys = FloatSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 611 */       return (FloatSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Float2ObjectSortedMap<V> subMap(float from, float to) {
/* 615 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Float2ObjectSortedMap<V> headMap(float to) {
/* 619 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Float2ObjectSortedMap<V> tailMap(float from) {
/* 623 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public float firstFloatKey() {
/* 627 */       return this.sortedMap.firstFloatKey();
/*     */     }
/*     */     
/*     */     public float lastFloatKey() {
/* 631 */       return this.sortedMap.lastFloatKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float firstKey() {
/* 641 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float lastKey() {
/* 651 */       return this.sortedMap.lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2ObjectSortedMap<V> subMap(Float from, Float to) {
/* 661 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2ObjectSortedMap<V> headMap(Float to) {
/* 671 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2ObjectSortedMap<V> tailMap(Float from) {
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
/*     */   public static <V> Float2ObjectSortedMap<V> unmodifiable(Float2ObjectSortedMap<V> m) {
/* 694 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2ObjectSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */