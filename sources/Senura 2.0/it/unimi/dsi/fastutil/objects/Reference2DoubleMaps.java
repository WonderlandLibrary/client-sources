/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleCollections;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleSets;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.ToDoubleFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Reference2DoubleMaps
/*     */ {
/*     */   public static <K> ObjectIterator<Reference2DoubleMap.Entry<K>> fastIterator(Reference2DoubleMap<K> map) {
/*  49 */     ObjectSet<Reference2DoubleMap.Entry<K>> entries = map.reference2DoubleEntrySet();
/*  50 */     return (entries instanceof Reference2DoubleMap.FastEntrySet) ? (
/*  51 */       (Reference2DoubleMap.FastEntrySet)entries).fastIterator() : 
/*  52 */       entries.iterator();
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
/*     */   public static <K> void fastForEach(Reference2DoubleMap<K> map, Consumer<? super Reference2DoubleMap.Entry<K>> consumer) {
/*  71 */     ObjectSet<Reference2DoubleMap.Entry<K>> entries = map.reference2DoubleEntrySet();
/*  72 */     if (entries instanceof Reference2DoubleMap.FastEntrySet) {
/*  73 */       ((Reference2DoubleMap.FastEntrySet)entries).fastForEach(consumer);
/*     */     } else {
/*  75 */       entries.forEach(consumer);
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
/*     */   public static <K> ObjectIterable<Reference2DoubleMap.Entry<K>> fastIterable(Reference2DoubleMap<K> map) {
/*  91 */     final ObjectSet<Reference2DoubleMap.Entry<K>> entries = map.reference2DoubleEntrySet();
/*  92 */     return (entries instanceof Reference2DoubleMap.FastEntrySet) ? 
/*  93 */       (ObjectIterable)new ObjectIterable<Reference2DoubleMap.Entry<Reference2DoubleMap.Entry<K>>>() {
/*     */         public ObjectIterator<Reference2DoubleMap.Entry<K>> iterator() {
/*  95 */           return ((Reference2DoubleMap.FastEntrySet<K>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Reference2DoubleMap.Entry<K>> consumer) {
/*  98 */           ((Reference2DoubleMap.FastEntrySet<K>)entries).fastForEach(consumer);
/*     */         }
/* 101 */       } : entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<K>
/*     */     extends Reference2DoubleFunctions.EmptyFunction<K>
/*     */     implements Reference2DoubleMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(double v) {
/* 120 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 130 */       return false;
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Double> m) {
/* 134 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Reference2DoubleMap.Entry<K>> reference2DoubleEntrySet() {
/* 139 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 144 */       return ReferenceSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleCollection values() {
/* 149 */       return (DoubleCollection)DoubleSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 153 */       return Reference2DoubleMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 157 */       return true;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 161 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 165 */       if (!(o instanceof Map))
/* 166 */         return false; 
/* 167 */       return ((Map)o).isEmpty();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 171 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 178 */   public static final EmptyMap EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> Reference2DoubleMap<K> emptyMap() {
/* 189 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends Reference2DoubleFunctions.Singleton<K>
/*     */     implements Reference2DoubleMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Reference2DoubleMap.Entry<K>> entries;
/*     */     
/*     */     protected transient ReferenceSet<K> keys;
/*     */     
/*     */     protected transient DoubleCollection values;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, double value) {
/* 208 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(double v) {
/* 212 */       return (Double.doubleToLongBits(this.value) == Double.doubleToLongBits(v));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 222 */       return (Double.doubleToLongBits(((Double)ov).doubleValue()) == Double.doubleToLongBits(this.value));
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Double> m) {
/* 226 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Reference2DoubleMap.Entry<K>> reference2DoubleEntrySet() {
/* 230 */       if (this.entries == null)
/* 231 */         this.entries = ObjectSets.singleton(new AbstractReference2DoubleMap.BasicEntry<>(this.key, this.value)); 
/* 232 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<K, Double>> entrySet() {
/* 243 */       return (ObjectSet)reference2DoubleEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 247 */       if (this.keys == null)
/* 248 */         this.keys = ReferenceSets.singleton(this.key); 
/* 249 */       return this.keys;
/*     */     }
/*     */     
/*     */     public DoubleCollection values() {
/* 253 */       if (this.values == null)
/* 254 */         this.values = (DoubleCollection)DoubleSets.singleton(this.value); 
/* 255 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 259 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 263 */       return System.identityHashCode(this.key) ^ HashCommon.double2int(this.value);
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 267 */       if (o == this)
/* 268 */         return true; 
/* 269 */       if (!(o instanceof Map))
/* 270 */         return false; 
/* 271 */       Map<?, ?> m = (Map<?, ?>)o;
/* 272 */       if (m.size() != 1)
/* 273 */         return false; 
/* 274 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 278 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static <K> Reference2DoubleMap<K> singleton(K key, double value) {
/* 297 */     return new Singleton<>(key, value);
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
/*     */   public static <K> Reference2DoubleMap<K> singleton(K key, Double value) {
/* 315 */     return new Singleton<>(key, value.doubleValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<K>
/*     */     extends Reference2DoubleFunctions.SynchronizedFunction<K>
/*     */     implements Reference2DoubleMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2DoubleMap<K> map;
/*     */     protected transient ObjectSet<Reference2DoubleMap.Entry<K>> entries;
/*     */     protected transient ReferenceSet<K> keys;
/*     */     protected transient DoubleCollection values;
/*     */     
/*     */     protected SynchronizedMap(Reference2DoubleMap<K> m, Object sync) {
/* 328 */       super(m, sync);
/* 329 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Reference2DoubleMap<K> m) {
/* 332 */       super(m);
/* 333 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(double v) {
/* 337 */       synchronized (this.sync) {
/* 338 */         return this.map.containsValue(v);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 349 */       synchronized (this.sync) {
/* 350 */         return this.map.containsValue(ov);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Double> m) {
/* 355 */       synchronized (this.sync) {
/* 356 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Reference2DoubleMap.Entry<K>> reference2DoubleEntrySet() {
/* 361 */       synchronized (this.sync) {
/* 362 */         if (this.entries == null)
/* 363 */           this.entries = ObjectSets.synchronize(this.map.reference2DoubleEntrySet(), this.sync); 
/* 364 */         return this.entries;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<K, Double>> entrySet() {
/* 376 */       return (ObjectSet)reference2DoubleEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 380 */       synchronized (this.sync) {
/* 381 */         if (this.keys == null)
/* 382 */           this.keys = ReferenceSets.synchronize(this.map.keySet(), this.sync); 
/* 383 */         return this.keys;
/*     */       } 
/*     */     }
/*     */     
/*     */     public DoubleCollection values() {
/* 388 */       synchronized (this.sync) {
/* 389 */         if (this.values == null)
/* 390 */           return DoubleCollections.synchronize(this.map.values(), this.sync); 
/* 391 */         return this.values;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 396 */       synchronized (this.sync) {
/* 397 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 402 */       synchronized (this.sync) {
/* 403 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 408 */       if (o == this)
/* 409 */         return true; 
/* 410 */       synchronized (this.sync) {
/* 411 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 415 */       synchronized (this.sync) {
/* 416 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double getOrDefault(Object key, double defaultValue) {
/* 422 */       synchronized (this.sync) {
/* 423 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Double> action) {
/* 428 */       synchronized (this.sync) {
/* 429 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Double, ? extends Double> function) {
/* 435 */       synchronized (this.sync) {
/* 436 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public double putIfAbsent(K key, double value) {
/* 441 */       synchronized (this.sync) {
/* 442 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, double value) {
/* 447 */       synchronized (this.sync) {
/* 448 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public double replace(K key, double value) {
/* 453 */       synchronized (this.sync) {
/* 454 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(K key, double oldValue, double newValue) {
/* 459 */       synchronized (this.sync) {
/* 460 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeDoubleIfAbsent(K key, ToDoubleFunction<? super K> mappingFunction) {
/* 466 */       synchronized (this.sync) {
/* 467 */         return this.map.computeDoubleIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeDoubleIfAbsentPartial(K key, Reference2DoubleFunction<? super K> mappingFunction) {
/* 473 */       synchronized (this.sync) {
/* 474 */         return this.map.computeDoubleIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeDoubleIfPresent(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/* 480 */       synchronized (this.sync) {
/* 481 */         return this.map.computeDoubleIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeDouble(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/* 487 */       synchronized (this.sync) {
/* 488 */         return this.map.computeDouble(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double mergeDouble(K key, double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 494 */       synchronized (this.sync) {
/* 495 */         return this.map.mergeDouble(key, value, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double getOrDefault(Object key, Double defaultValue) {
/* 506 */       synchronized (this.sync) {
/* 507 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object key, Object value) {
/* 518 */       synchronized (this.sync) {
/* 519 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double replace(K key, Double value) {
/* 530 */       synchronized (this.sync) {
/* 531 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(K key, Double oldValue, Double newValue) {
/* 542 */       synchronized (this.sync) {
/* 543 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double putIfAbsent(K key, Double value) {
/* 554 */       synchronized (this.sync) {
/* 555 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Double computeIfAbsent(K key, Function<? super K, ? extends Double> mappingFunction) {
/* 561 */       synchronized (this.sync) {
/* 562 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Double computeIfPresent(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/* 568 */       synchronized (this.sync) {
/* 569 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Double compute(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/* 575 */       synchronized (this.sync) {
/* 576 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double merge(K key, Double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 588 */       synchronized (this.sync) {
/* 589 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <K> Reference2DoubleMap<K> synchronize(Reference2DoubleMap<K> m) {
/* 603 */     return new SynchronizedMap<>(m);
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
/*     */   public static <K> Reference2DoubleMap<K> synchronize(Reference2DoubleMap<K> m, Object sync) {
/* 617 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<K>
/*     */     extends Reference2DoubleFunctions.UnmodifiableFunction<K>
/*     */     implements Reference2DoubleMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2DoubleMap<K> map;
/*     */     protected transient ObjectSet<Reference2DoubleMap.Entry<K>> entries;
/*     */     protected transient ReferenceSet<K> keys;
/*     */     protected transient DoubleCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Reference2DoubleMap<K> m) {
/* 630 */       super(m);
/* 631 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(double v) {
/* 635 */       return this.map.containsValue(v);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 645 */       return this.map.containsValue(ov);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Double> m) {
/* 649 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Reference2DoubleMap.Entry<K>> reference2DoubleEntrySet() {
/* 653 */       if (this.entries == null)
/* 654 */         this.entries = ObjectSets.unmodifiable(this.map.reference2DoubleEntrySet()); 
/* 655 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<K, Double>> entrySet() {
/* 666 */       return (ObjectSet)reference2DoubleEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 670 */       if (this.keys == null)
/* 671 */         this.keys = ReferenceSets.unmodifiable(this.map.keySet()); 
/* 672 */       return this.keys;
/*     */     }
/*     */     
/*     */     public DoubleCollection values() {
/* 676 */       if (this.values == null)
/* 677 */         return DoubleCollections.unmodifiable(this.map.values()); 
/* 678 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 682 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 686 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 690 */       if (o == this)
/* 691 */         return true; 
/* 692 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public double getOrDefault(Object key, double defaultValue) {
/* 697 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Double> action) {
/* 701 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Double, ? extends Double> function) {
/* 706 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public double putIfAbsent(K key, double value) {
/* 710 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, double value) {
/* 714 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public double replace(K key, double value) {
/* 718 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(K key, double oldValue, double newValue) {
/* 722 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeDoubleIfAbsent(K key, ToDoubleFunction<? super K> mappingFunction) {
/* 727 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeDoubleIfAbsentPartial(K key, Reference2DoubleFunction<? super K> mappingFunction) {
/* 732 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeDoubleIfPresent(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/* 737 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeDouble(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/* 742 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double mergeDouble(K key, double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 747 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double getOrDefault(Object key, Double defaultValue) {
/* 757 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object key, Object value) {
/* 767 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double replace(K key, Double value) {
/* 777 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(K key, Double oldValue, Double newValue) {
/* 787 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double putIfAbsent(K key, Double value) {
/* 797 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Double computeIfAbsent(K key, Function<? super K, ? extends Double> mappingFunction) {
/* 802 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Double computeIfPresent(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/* 807 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Double compute(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/* 812 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double merge(K key, Double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 823 */       throw new UnsupportedOperationException();
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
/*     */   public static <K> Reference2DoubleMap<K> unmodifiable(Reference2DoubleMap<K> m) {
/* 836 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2DoubleMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */