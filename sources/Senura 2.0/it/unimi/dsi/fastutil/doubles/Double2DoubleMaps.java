/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterable;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSets;
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
/*     */ import java.util.function.DoubleFunction;
/*     */ import java.util.function.DoubleUnaryOperator;
/*     */ import java.util.function.Function;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Double2DoubleMaps
/*     */ {
/*     */   public static ObjectIterator<Double2DoubleMap.Entry> fastIterator(Double2DoubleMap map) {
/*  49 */     ObjectSet<Double2DoubleMap.Entry> entries = map.double2DoubleEntrySet();
/*  50 */     return (entries instanceof Double2DoubleMap.FastEntrySet) ? (
/*  51 */       (Double2DoubleMap.FastEntrySet)entries).fastIterator() : 
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
/*     */   public static void fastForEach(Double2DoubleMap map, Consumer<? super Double2DoubleMap.Entry> consumer) {
/*  70 */     ObjectSet<Double2DoubleMap.Entry> entries = map.double2DoubleEntrySet();
/*  71 */     if (entries instanceof Double2DoubleMap.FastEntrySet) {
/*  72 */       ((Double2DoubleMap.FastEntrySet)entries).fastForEach(consumer);
/*     */     } else {
/*  74 */       entries.forEach(consumer);
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
/*     */   public static ObjectIterable<Double2DoubleMap.Entry> fastIterable(Double2DoubleMap map) {
/*  90 */     final ObjectSet<Double2DoubleMap.Entry> entries = map.double2DoubleEntrySet();
/*  91 */     return (entries instanceof Double2DoubleMap.FastEntrySet) ? new ObjectIterable<Double2DoubleMap.Entry>() {
/*     */         public ObjectIterator<Double2DoubleMap.Entry> iterator() {
/*  93 */           return ((Double2DoubleMap.FastEntrySet)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Double2DoubleMap.Entry> consumer) {
/*  96 */           ((Double2DoubleMap.FastEntrySet)entries).fastForEach(consumer);
/*     */         }
/*  98 */       } : (ObjectIterable<Double2DoubleMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap
/*     */     extends Double2DoubleFunctions.EmptyFunction
/*     */     implements Double2DoubleMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(double v) {
/* 117 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 127 */       return false;
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Double, ? extends Double> m) {
/* 131 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Double2DoubleMap.Entry> double2DoubleEntrySet() {
/* 136 */       return (ObjectSet<Double2DoubleMap.Entry>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSet keySet() {
/* 141 */       return DoubleSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleCollection values() {
/* 146 */       return DoubleSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 150 */       return Double2DoubleMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 154 */       return true;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 158 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 162 */       if (!(o instanceof Map))
/* 163 */         return false; 
/* 164 */       return ((Map)o).isEmpty();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 168 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   public static final EmptyMap EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton
/*     */     extends Double2DoubleFunctions.Singleton
/*     */     implements Double2DoubleMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Double2DoubleMap.Entry> entries;
/*     */     
/*     */     protected transient DoubleSet keys;
/*     */     
/*     */     protected transient DoubleCollection values;
/*     */ 
/*     */     
/*     */     protected Singleton(double key, double value) {
/* 193 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(double v) {
/* 197 */       return (Double.doubleToLongBits(this.value) == Double.doubleToLongBits(v));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 207 */       return (Double.doubleToLongBits(((Double)ov).doubleValue()) == Double.doubleToLongBits(this.value));
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Double, ? extends Double> m) {
/* 211 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Double2DoubleMap.Entry> double2DoubleEntrySet() {
/* 215 */       if (this.entries == null)
/* 216 */         this.entries = ObjectSets.singleton(new AbstractDouble2DoubleMap.BasicEntry(this.key, this.value)); 
/* 217 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<Double, Double>> entrySet() {
/* 228 */       return (ObjectSet)double2DoubleEntrySet();
/*     */     }
/*     */     
/*     */     public DoubleSet keySet() {
/* 232 */       if (this.keys == null)
/* 233 */         this.keys = DoubleSets.singleton(this.key); 
/* 234 */       return this.keys;
/*     */     }
/*     */     
/*     */     public DoubleCollection values() {
/* 238 */       if (this.values == null)
/* 239 */         this.values = DoubleSets.singleton(this.value); 
/* 240 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 244 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 248 */       return HashCommon.double2int(this.key) ^ 
/* 249 */         HashCommon.double2int(this.value);
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 253 */       if (o == this)
/* 254 */         return true; 
/* 255 */       if (!(o instanceof Map))
/* 256 */         return false; 
/* 257 */       Map<?, ?> m = (Map<?, ?>)o;
/* 258 */       if (m.size() != 1)
/* 259 */         return false; 
/* 260 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 264 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static Double2DoubleMap singleton(double key, double value) {
/* 283 */     return new Singleton(key, value);
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
/*     */   public static Double2DoubleMap singleton(Double key, Double value) {
/* 301 */     return new Singleton(key.doubleValue(), value.doubleValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap
/*     */     extends Double2DoubleFunctions.SynchronizedFunction
/*     */     implements Double2DoubleMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2DoubleMap map;
/*     */     protected transient ObjectSet<Double2DoubleMap.Entry> entries;
/*     */     protected transient DoubleSet keys;
/*     */     protected transient DoubleCollection values;
/*     */     
/*     */     protected SynchronizedMap(Double2DoubleMap m, Object sync) {
/* 314 */       super(m, sync);
/* 315 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Double2DoubleMap m) {
/* 318 */       super(m);
/* 319 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(double v) {
/* 323 */       synchronized (this.sync) {
/* 324 */         return this.map.containsValue(v);
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
/* 335 */       synchronized (this.sync) {
/* 336 */         return this.map.containsValue(ov);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Double, ? extends Double> m) {
/* 341 */       synchronized (this.sync) {
/* 342 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Double2DoubleMap.Entry> double2DoubleEntrySet() {
/* 347 */       synchronized (this.sync) {
/* 348 */         if (this.entries == null)
/* 349 */           this.entries = ObjectSets.synchronize(this.map.double2DoubleEntrySet(), this.sync); 
/* 350 */         return this.entries;
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
/*     */     public ObjectSet<Map.Entry<Double, Double>> entrySet() {
/* 362 */       return (ObjectSet)double2DoubleEntrySet();
/*     */     }
/*     */     
/*     */     public DoubleSet keySet() {
/* 366 */       synchronized (this.sync) {
/* 367 */         if (this.keys == null)
/* 368 */           this.keys = DoubleSets.synchronize(this.map.keySet(), this.sync); 
/* 369 */         return this.keys;
/*     */       } 
/*     */     }
/*     */     
/*     */     public DoubleCollection values() {
/* 374 */       synchronized (this.sync) {
/* 375 */         if (this.values == null)
/* 376 */           return DoubleCollections.synchronize(this.map.values(), this.sync); 
/* 377 */         return this.values;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 382 */       synchronized (this.sync) {
/* 383 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 388 */       synchronized (this.sync) {
/* 389 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 394 */       if (o == this)
/* 395 */         return true; 
/* 396 */       synchronized (this.sync) {
/* 397 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 401 */       synchronized (this.sync) {
/* 402 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double getOrDefault(double key, double defaultValue) {
/* 408 */       synchronized (this.sync) {
/* 409 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Double, ? super Double> action) {
/* 414 */       synchronized (this.sync) {
/* 415 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Double, ? super Double, ? extends Double> function) {
/* 421 */       synchronized (this.sync) {
/* 422 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public double putIfAbsent(double key, double value) {
/* 427 */       synchronized (this.sync) {
/* 428 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(double key, double value) {
/* 433 */       synchronized (this.sync) {
/* 434 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public double replace(double key, double value) {
/* 439 */       synchronized (this.sync) {
/* 440 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(double key, double oldValue, double newValue) {
/* 445 */       synchronized (this.sync) {
/* 446 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public double computeIfAbsent(double key, DoubleUnaryOperator mappingFunction) {
/* 451 */       synchronized (this.sync) {
/* 452 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeIfAbsentNullable(double key, DoubleFunction<? extends Double> mappingFunction) {
/* 458 */       synchronized (this.sync) {
/* 459 */         return this.map.computeIfAbsentNullable(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */     
/*     */     public double computeIfAbsentPartial(double key, Double2DoubleFunction mappingFunction) {
/* 464 */       synchronized (this.sync) {
/* 465 */         return this.map.computeIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeIfPresent(double key, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 471 */       synchronized (this.sync) {
/* 472 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double compute(double key, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 478 */       synchronized (this.sync) {
/* 479 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double merge(double key, double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 485 */       synchronized (this.sync) {
/* 486 */         return this.map.merge(key, value, remappingFunction);
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
/* 497 */       synchronized (this.sync) {
/* 498 */         return this.map.getOrDefault(key, defaultValue);
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
/* 509 */       synchronized (this.sync) {
/* 510 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double replace(Double key, Double value) {
/* 521 */       synchronized (this.sync) {
/* 522 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Double key, Double oldValue, Double newValue) {
/* 533 */       synchronized (this.sync) {
/* 534 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double putIfAbsent(Double key, Double value) {
/* 545 */       synchronized (this.sync) {
/* 546 */         return this.map.putIfAbsent(key, value);
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
/*     */     public Double computeIfAbsent(Double key, Function<? super Double, ? extends Double> mappingFunction) {
/* 558 */       synchronized (this.sync) {
/* 559 */         return this.map.computeIfAbsent(key, mappingFunction);
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
/*     */     public Double computeIfPresent(Double key, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 571 */       synchronized (this.sync) {
/* 572 */         return this.map.computeIfPresent(key, remappingFunction);
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
/*     */     public Double compute(Double key, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 584 */       synchronized (this.sync) {
/* 585 */         return this.map.compute(key, remappingFunction);
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
/*     */     public Double merge(Double key, Double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 597 */       synchronized (this.sync) {
/* 598 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static Double2DoubleMap synchronize(Double2DoubleMap m) {
/* 612 */     return new SynchronizedMap(m);
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
/*     */   public static Double2DoubleMap synchronize(Double2DoubleMap m, Object sync) {
/* 626 */     return new SynchronizedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap
/*     */     extends Double2DoubleFunctions.UnmodifiableFunction
/*     */     implements Double2DoubleMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2DoubleMap map;
/*     */     protected transient ObjectSet<Double2DoubleMap.Entry> entries;
/*     */     protected transient DoubleSet keys;
/*     */     protected transient DoubleCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Double2DoubleMap m) {
/* 639 */       super(m);
/* 640 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(double v) {
/* 644 */       return this.map.containsValue(v);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 654 */       return this.map.containsValue(ov);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Double, ? extends Double> m) {
/* 658 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Double2DoubleMap.Entry> double2DoubleEntrySet() {
/* 662 */       if (this.entries == null)
/* 663 */         this.entries = ObjectSets.unmodifiable(this.map.double2DoubleEntrySet()); 
/* 664 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<Double, Double>> entrySet() {
/* 675 */       return (ObjectSet)double2DoubleEntrySet();
/*     */     }
/*     */     
/*     */     public DoubleSet keySet() {
/* 679 */       if (this.keys == null)
/* 680 */         this.keys = DoubleSets.unmodifiable(this.map.keySet()); 
/* 681 */       return this.keys;
/*     */     }
/*     */     
/*     */     public DoubleCollection values() {
/* 685 */       if (this.values == null)
/* 686 */         return DoubleCollections.unmodifiable(this.map.values()); 
/* 687 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 691 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 695 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 699 */       if (o == this)
/* 700 */         return true; 
/* 701 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public double getOrDefault(double key, double defaultValue) {
/* 706 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Double, ? super Double> action) {
/* 710 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Double, ? super Double, ? extends Double> function) {
/* 715 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public double putIfAbsent(double key, double value) {
/* 719 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(double key, double value) {
/* 723 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public double replace(double key, double value) {
/* 727 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(double key, double oldValue, double newValue) {
/* 731 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public double computeIfAbsent(double key, DoubleUnaryOperator mappingFunction) {
/* 735 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeIfAbsentNullable(double key, DoubleFunction<? extends Double> mappingFunction) {
/* 740 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public double computeIfAbsentPartial(double key, Double2DoubleFunction mappingFunction) {
/* 744 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeIfPresent(double key, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 749 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double compute(double key, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 754 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double merge(double key, double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 759 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double getOrDefault(Object key, Double defaultValue) {
/* 769 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object key, Object value) {
/* 779 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double replace(Double key, Double value) {
/* 789 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Double key, Double oldValue, Double newValue) {
/* 799 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double putIfAbsent(Double key, Double value) {
/* 809 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double computeIfAbsent(Double key, Function<? super Double, ? extends Double> mappingFunction) {
/* 820 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double computeIfPresent(Double key, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 831 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double compute(Double key, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 842 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double merge(Double key, Double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 853 */       throw new UnsupportedOperationException();
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
/*     */   public static Double2DoubleMap unmodifiable(Double2DoubleMap m) {
/* 866 */     return new UnmodifiableMap(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2DoubleMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */