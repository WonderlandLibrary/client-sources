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
/*     */ public final class Object2DoubleMaps
/*     */ {
/*     */   public static <K> ObjectIterator<Object2DoubleMap.Entry<K>> fastIterator(Object2DoubleMap<K> map) {
/*  49 */     ObjectSet<Object2DoubleMap.Entry<K>> entries = map.object2DoubleEntrySet();
/*  50 */     return (entries instanceof Object2DoubleMap.FastEntrySet) ? (
/*  51 */       (Object2DoubleMap.FastEntrySet)entries).fastIterator() : 
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
/*     */   public static <K> void fastForEach(Object2DoubleMap<K> map, Consumer<? super Object2DoubleMap.Entry<K>> consumer) {
/*  71 */     ObjectSet<Object2DoubleMap.Entry<K>> entries = map.object2DoubleEntrySet();
/*  72 */     if (entries instanceof Object2DoubleMap.FastEntrySet) {
/*  73 */       ((Object2DoubleMap.FastEntrySet)entries).fastForEach(consumer);
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
/*     */   public static <K> ObjectIterable<Object2DoubleMap.Entry<K>> fastIterable(Object2DoubleMap<K> map) {
/*  91 */     final ObjectSet<Object2DoubleMap.Entry<K>> entries = map.object2DoubleEntrySet();
/*  92 */     return (entries instanceof Object2DoubleMap.FastEntrySet) ? (ObjectIterable)new ObjectIterable<Object2DoubleMap.Entry<Object2DoubleMap.Entry<K>>>() {
/*     */         public ObjectIterator<Object2DoubleMap.Entry<K>> iterator() {
/*  94 */           return ((Object2DoubleMap.FastEntrySet<K>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Object2DoubleMap.Entry<K>> consumer) {
/*  97 */           ((Object2DoubleMap.FastEntrySet<K>)entries).fastForEach(consumer);
/*     */         }
/*  99 */       } : entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<K>
/*     */     extends Object2DoubleFunctions.EmptyFunction<K>
/*     */     implements Object2DoubleMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(double v) {
/* 118 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 128 */       return false;
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Double> m) {
/* 132 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Object2DoubleMap.Entry<K>> object2DoubleEntrySet() {
/* 137 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 142 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleCollection values() {
/* 147 */       return (DoubleCollection)DoubleSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 151 */       return Object2DoubleMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 155 */       return true;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 159 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 163 */       if (!(o instanceof Map))
/* 164 */         return false; 
/* 165 */       return ((Map)o).isEmpty();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 169 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 176 */   public static final EmptyMap EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> Object2DoubleMap<K> emptyMap() {
/* 187 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends Object2DoubleFunctions.Singleton<K>
/*     */     implements Object2DoubleMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Object2DoubleMap.Entry<K>> entries;
/*     */     
/*     */     protected transient ObjectSet<K> keys;
/*     */     
/*     */     protected transient DoubleCollection values;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, double value) {
/* 206 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(double v) {
/* 210 */       return (Double.doubleToLongBits(this.value) == Double.doubleToLongBits(v));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 220 */       return (Double.doubleToLongBits(((Double)ov).doubleValue()) == Double.doubleToLongBits(this.value));
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Double> m) {
/* 224 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Object2DoubleMap.Entry<K>> object2DoubleEntrySet() {
/* 228 */       if (this.entries == null)
/* 229 */         this.entries = ObjectSets.singleton(new AbstractObject2DoubleMap.BasicEntry<>(this.key, this.value)); 
/* 230 */       return this.entries;
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
/* 241 */       return (ObjectSet)object2DoubleEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 245 */       if (this.keys == null)
/* 246 */         this.keys = ObjectSets.singleton(this.key); 
/* 247 */       return this.keys;
/*     */     }
/*     */     
/*     */     public DoubleCollection values() {
/* 251 */       if (this.values == null)
/* 252 */         this.values = (DoubleCollection)DoubleSets.singleton(this.value); 
/* 253 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 257 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 261 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ HashCommon.double2int(this.value);
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 265 */       if (o == this)
/* 266 */         return true; 
/* 267 */       if (!(o instanceof Map))
/* 268 */         return false; 
/* 269 */       Map<?, ?> m = (Map<?, ?>)o;
/* 270 */       if (m.size() != 1)
/* 271 */         return false; 
/* 272 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 276 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static <K> Object2DoubleMap<K> singleton(K key, double value) {
/* 295 */     return new Singleton<>(key, value);
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
/*     */   public static <K> Object2DoubleMap<K> singleton(K key, Double value) {
/* 313 */     return new Singleton<>(key, value.doubleValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<K>
/*     */     extends Object2DoubleFunctions.SynchronizedFunction<K>
/*     */     implements Object2DoubleMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2DoubleMap<K> map;
/*     */     protected transient ObjectSet<Object2DoubleMap.Entry<K>> entries;
/*     */     protected transient ObjectSet<K> keys;
/*     */     protected transient DoubleCollection values;
/*     */     
/*     */     protected SynchronizedMap(Object2DoubleMap<K> m, Object sync) {
/* 326 */       super(m, sync);
/* 327 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Object2DoubleMap<K> m) {
/* 330 */       super(m);
/* 331 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(double v) {
/* 335 */       synchronized (this.sync) {
/* 336 */         return this.map.containsValue(v);
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
/* 347 */       synchronized (this.sync) {
/* 348 */         return this.map.containsValue(ov);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Double> m) {
/* 353 */       synchronized (this.sync) {
/* 354 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Object2DoubleMap.Entry<K>> object2DoubleEntrySet() {
/* 359 */       synchronized (this.sync) {
/* 360 */         if (this.entries == null)
/* 361 */           this.entries = ObjectSets.synchronize(this.map.object2DoubleEntrySet(), this.sync); 
/* 362 */         return this.entries;
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
/* 374 */       return (ObjectSet)object2DoubleEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 378 */       synchronized (this.sync) {
/* 379 */         if (this.keys == null)
/* 380 */           this.keys = ObjectSets.synchronize(this.map.keySet(), this.sync); 
/* 381 */         return this.keys;
/*     */       } 
/*     */     }
/*     */     
/*     */     public DoubleCollection values() {
/* 386 */       synchronized (this.sync) {
/* 387 */         if (this.values == null)
/* 388 */           return DoubleCollections.synchronize(this.map.values(), this.sync); 
/* 389 */         return this.values;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 394 */       synchronized (this.sync) {
/* 395 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 400 */       synchronized (this.sync) {
/* 401 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 406 */       if (o == this)
/* 407 */         return true; 
/* 408 */       synchronized (this.sync) {
/* 409 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 413 */       synchronized (this.sync) {
/* 414 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double getOrDefault(Object key, double defaultValue) {
/* 420 */       synchronized (this.sync) {
/* 421 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Double> action) {
/* 426 */       synchronized (this.sync) {
/* 427 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Double, ? extends Double> function) {
/* 433 */       synchronized (this.sync) {
/* 434 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public double putIfAbsent(K key, double value) {
/* 439 */       synchronized (this.sync) {
/* 440 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, double value) {
/* 445 */       synchronized (this.sync) {
/* 446 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public double replace(K key, double value) {
/* 451 */       synchronized (this.sync) {
/* 452 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(K key, double oldValue, double newValue) {
/* 457 */       synchronized (this.sync) {
/* 458 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeDoubleIfAbsent(K key, ToDoubleFunction<? super K> mappingFunction) {
/* 464 */       synchronized (this.sync) {
/* 465 */         return this.map.computeDoubleIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeDoubleIfAbsentPartial(K key, Object2DoubleFunction<? super K> mappingFunction) {
/* 471 */       synchronized (this.sync) {
/* 472 */         return this.map.computeDoubleIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeDoubleIfPresent(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/* 478 */       synchronized (this.sync) {
/* 479 */         return this.map.computeDoubleIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeDouble(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/* 485 */       synchronized (this.sync) {
/* 486 */         return this.map.computeDouble(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double mergeDouble(K key, double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 492 */       synchronized (this.sync) {
/* 493 */         return this.map.mergeDouble(key, value, remappingFunction);
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
/* 504 */       synchronized (this.sync) {
/* 505 */         return this.map.getOrDefault(key, defaultValue);
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
/* 516 */       synchronized (this.sync) {
/* 517 */         return this.map.remove(key, value);
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
/* 528 */       synchronized (this.sync) {
/* 529 */         return this.map.replace(key, value);
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
/* 540 */       synchronized (this.sync) {
/* 541 */         return this.map.replace(key, oldValue, newValue);
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
/* 552 */       synchronized (this.sync) {
/* 553 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Double computeIfAbsent(K key, Function<? super K, ? extends Double> mappingFunction) {
/* 559 */       synchronized (this.sync) {
/* 560 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Double computeIfPresent(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/* 566 */       synchronized (this.sync) {
/* 567 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Double compute(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/* 573 */       synchronized (this.sync) {
/* 574 */         return this.map.compute(key, remappingFunction);
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
/* 586 */       synchronized (this.sync) {
/* 587 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <K> Object2DoubleMap<K> synchronize(Object2DoubleMap<K> m) {
/* 601 */     return new SynchronizedMap<>(m);
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
/*     */   public static <K> Object2DoubleMap<K> synchronize(Object2DoubleMap<K> m, Object sync) {
/* 615 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<K>
/*     */     extends Object2DoubleFunctions.UnmodifiableFunction<K>
/*     */     implements Object2DoubleMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2DoubleMap<K> map;
/*     */     protected transient ObjectSet<Object2DoubleMap.Entry<K>> entries;
/*     */     protected transient ObjectSet<K> keys;
/*     */     protected transient DoubleCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Object2DoubleMap<K> m) {
/* 628 */       super(m);
/* 629 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(double v) {
/* 633 */       return this.map.containsValue(v);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 643 */       return this.map.containsValue(ov);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Double> m) {
/* 647 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Object2DoubleMap.Entry<K>> object2DoubleEntrySet() {
/* 651 */       if (this.entries == null)
/* 652 */         this.entries = ObjectSets.unmodifiable(this.map.object2DoubleEntrySet()); 
/* 653 */       return this.entries;
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
/* 664 */       return (ObjectSet)object2DoubleEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 668 */       if (this.keys == null)
/* 669 */         this.keys = ObjectSets.unmodifiable(this.map.keySet()); 
/* 670 */       return this.keys;
/*     */     }
/*     */     
/*     */     public DoubleCollection values() {
/* 674 */       if (this.values == null)
/* 675 */         return DoubleCollections.unmodifiable(this.map.values()); 
/* 676 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 680 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 684 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 688 */       if (o == this)
/* 689 */         return true; 
/* 690 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public double getOrDefault(Object key, double defaultValue) {
/* 695 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Double> action) {
/* 699 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Double, ? extends Double> function) {
/* 704 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public double putIfAbsent(K key, double value) {
/* 708 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, double value) {
/* 712 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public double replace(K key, double value) {
/* 716 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(K key, double oldValue, double newValue) {
/* 720 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeDoubleIfAbsent(K key, ToDoubleFunction<? super K> mappingFunction) {
/* 725 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeDoubleIfAbsentPartial(K key, Object2DoubleFunction<? super K> mappingFunction) {
/* 730 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeDoubleIfPresent(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/* 735 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeDouble(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/* 740 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double mergeDouble(K key, double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 745 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double getOrDefault(Object key, Double defaultValue) {
/* 755 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object key, Object value) {
/* 765 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double replace(K key, Double value) {
/* 775 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(K key, Double oldValue, Double newValue) {
/* 785 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double putIfAbsent(K key, Double value) {
/* 795 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Double computeIfAbsent(K key, Function<? super K, ? extends Double> mappingFunction) {
/* 800 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Double computeIfPresent(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/* 805 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Double compute(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/* 810 */       throw new UnsupportedOperationException();
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
/* 821 */       throw new UnsupportedOperationException();
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
/*     */   public static <K> Object2DoubleMap<K> unmodifiable(Object2DoubleMap<K> m) {
/* 834 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2DoubleMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */