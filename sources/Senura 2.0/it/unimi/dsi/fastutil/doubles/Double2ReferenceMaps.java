/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterable;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSets;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceCollections;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceSets;
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
/*     */ public final class Double2ReferenceMaps
/*     */ {
/*     */   public static <V> ObjectIterator<Double2ReferenceMap.Entry<V>> fastIterator(Double2ReferenceMap<V> map) {
/*  49 */     ObjectSet<Double2ReferenceMap.Entry<V>> entries = map.double2ReferenceEntrySet();
/*  50 */     return (entries instanceof Double2ReferenceMap.FastEntrySet) ? (
/*  51 */       (Double2ReferenceMap.FastEntrySet)entries).fastIterator() : 
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
/*     */   public static <V> void fastForEach(Double2ReferenceMap<V> map, Consumer<? super Double2ReferenceMap.Entry<V>> consumer) {
/*  71 */     ObjectSet<Double2ReferenceMap.Entry<V>> entries = map.double2ReferenceEntrySet();
/*  72 */     if (entries instanceof Double2ReferenceMap.FastEntrySet) {
/*  73 */       ((Double2ReferenceMap.FastEntrySet)entries).fastForEach(consumer);
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
/*     */   public static <V> ObjectIterable<Double2ReferenceMap.Entry<V>> fastIterable(Double2ReferenceMap<V> map) {
/*  91 */     final ObjectSet<Double2ReferenceMap.Entry<V>> entries = map.double2ReferenceEntrySet();
/*  92 */     return (entries instanceof Double2ReferenceMap.FastEntrySet) ? 
/*  93 */       new ObjectIterable<Double2ReferenceMap.Entry<V>>() {
/*     */         public ObjectIterator<Double2ReferenceMap.Entry<V>> iterator() {
/*  95 */           return ((Double2ReferenceMap.FastEntrySet<V>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Double2ReferenceMap.Entry<V>> consumer) {
/*  98 */           ((Double2ReferenceMap.FastEntrySet<V>)entries).fastForEach(consumer);
/*     */         }
/* 101 */       } : (ObjectIterable<Double2ReferenceMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<V>
/*     */     extends Double2ReferenceFunctions.EmptyFunction<V>
/*     */     implements Double2ReferenceMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 120 */       return false;
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Double, ? extends V> m) {
/* 124 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
/* 129 */       return (ObjectSet<Double2ReferenceMap.Entry<V>>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSet keySet() {
/* 134 */       return DoubleSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 139 */       return (ReferenceCollection<V>)ReferenceSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 143 */       return Double2ReferenceMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 147 */       return true;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 151 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 155 */       if (!(o instanceof Map))
/* 156 */         return false; 
/* 157 */       return ((Map)o).isEmpty();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 161 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   public static final EmptyMap EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Double2ReferenceMap<V> emptyMap() {
/* 179 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Double2ReferenceFunctions.Singleton<V>
/*     */     implements Double2ReferenceMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Double2ReferenceMap.Entry<V>> entries;
/*     */     
/*     */     protected transient DoubleSet keys;
/*     */     
/*     */     protected transient ReferenceCollection<V> values;
/*     */ 
/*     */     
/*     */     protected Singleton(double key, V value) {
/* 198 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 202 */       return (this.value == v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Double, ? extends V> m) {
/* 206 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
/* 210 */       if (this.entries == null)
/* 211 */         this.entries = ObjectSets.singleton(new AbstractDouble2ReferenceMap.BasicEntry<>(this.key, this.value)); 
/* 212 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<Double, V>> entrySet() {
/* 223 */       return (ObjectSet)double2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public DoubleSet keySet() {
/* 227 */       if (this.keys == null)
/* 228 */         this.keys = DoubleSets.singleton(this.key); 
/* 229 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 233 */       if (this.values == null)
/* 234 */         this.values = (ReferenceCollection<V>)ReferenceSets.singleton(this.value); 
/* 235 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 239 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 243 */       return HashCommon.double2int(this.key) ^ (
/* 244 */         (this.value == null) ? 0 : System.identityHashCode(this.value));
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 248 */       if (o == this)
/* 249 */         return true; 
/* 250 */       if (!(o instanceof Map))
/* 251 */         return false; 
/* 252 */       Map<?, ?> m = (Map<?, ?>)o;
/* 253 */       if (m.size() != 1)
/* 254 */         return false; 
/* 255 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 259 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static <V> Double2ReferenceMap<V> singleton(double key, V value) {
/* 278 */     return new Singleton<>(key, value);
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
/*     */   public static <V> Double2ReferenceMap<V> singleton(Double key, V value) {
/* 296 */     return new Singleton<>(key.doubleValue(), value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<V>
/*     */     extends Double2ReferenceFunctions.SynchronizedFunction<V>
/*     */     implements Double2ReferenceMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2ReferenceMap<V> map;
/*     */     protected transient ObjectSet<Double2ReferenceMap.Entry<V>> entries;
/*     */     protected transient DoubleSet keys;
/*     */     protected transient ReferenceCollection<V> values;
/*     */     
/*     */     protected SynchronizedMap(Double2ReferenceMap<V> m, Object sync) {
/* 309 */       super(m, sync);
/* 310 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Double2ReferenceMap<V> m) {
/* 313 */       super(m);
/* 314 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 318 */       synchronized (this.sync) {
/* 319 */         return this.map.containsValue(v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Double, ? extends V> m) {
/* 324 */       synchronized (this.sync) {
/* 325 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
/* 330 */       synchronized (this.sync) {
/* 331 */         if (this.entries == null)
/* 332 */           this.entries = ObjectSets.synchronize(this.map.double2ReferenceEntrySet(), this.sync); 
/* 333 */         return this.entries;
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
/*     */     public ObjectSet<Map.Entry<Double, V>> entrySet() {
/* 345 */       return (ObjectSet)double2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public DoubleSet keySet() {
/* 349 */       synchronized (this.sync) {
/* 350 */         if (this.keys == null)
/* 351 */           this.keys = DoubleSets.synchronize(this.map.keySet(), this.sync); 
/* 352 */         return this.keys;
/*     */       } 
/*     */     }
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 357 */       synchronized (this.sync) {
/* 358 */         if (this.values == null)
/* 359 */           return ReferenceCollections.synchronize(this.map.values(), this.sync); 
/* 360 */         return this.values;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 365 */       synchronized (this.sync) {
/* 366 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 371 */       synchronized (this.sync) {
/* 372 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 377 */       if (o == this)
/* 378 */         return true; 
/* 379 */       synchronized (this.sync) {
/* 380 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 384 */       synchronized (this.sync) {
/* 385 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(double key, V defaultValue) {
/* 391 */       synchronized (this.sync) {
/* 392 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Double, ? super V> action) {
/* 397 */       synchronized (this.sync) {
/* 398 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super Double, ? super V, ? extends V> function) {
/* 403 */       synchronized (this.sync) {
/* 404 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V putIfAbsent(double key, V value) {
/* 409 */       synchronized (this.sync) {
/* 410 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(double key, Object value) {
/* 415 */       synchronized (this.sync) {
/* 416 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V replace(double key, V value) {
/* 421 */       synchronized (this.sync) {
/* 422 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(double key, V oldValue, V newValue) {
/* 427 */       synchronized (this.sync) {
/* 428 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfAbsent(double key, DoubleFunction<? extends V> mappingFunction) {
/* 434 */       synchronized (this.sync) {
/* 435 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V computeIfAbsentPartial(double key, Double2ReferenceFunction<? extends V> mappingFunction) {
/* 440 */       synchronized (this.sync) {
/* 441 */         return this.map.computeIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(double key, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/* 447 */       synchronized (this.sync) {
/* 448 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(double key, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/* 454 */       synchronized (this.sync) {
/* 455 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(double key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 461 */       synchronized (this.sync) {
/* 462 */         return this.map.merge(key, value, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 473 */       synchronized (this.sync) {
/* 474 */         return this.map.getOrDefault(key, defaultValue);
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
/* 485 */       synchronized (this.sync) {
/* 486 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V replace(Double key, V value) {
/* 497 */       synchronized (this.sync) {
/* 498 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Double key, V oldValue, V newValue) {
/* 509 */       synchronized (this.sync) {
/* 510 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V putIfAbsent(Double key, V value) {
/* 521 */       synchronized (this.sync) {
/* 522 */         return this.map.putIfAbsent(key, value);
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
/*     */     public V computeIfAbsent(Double key, Function<? super Double, ? extends V> mappingFunction) {
/* 534 */       synchronized (this.sync) {
/* 535 */         return this.map.computeIfAbsent(key, mappingFunction);
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
/*     */     public V computeIfPresent(Double key, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/* 547 */       synchronized (this.sync) {
/* 548 */         return this.map.computeIfPresent(key, remappingFunction);
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
/*     */     public V compute(Double key, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/* 560 */       synchronized (this.sync) {
/* 561 */         return this.map.compute(key, remappingFunction);
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
/*     */     public V merge(Double key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 573 */       synchronized (this.sync) {
/* 574 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <V> Double2ReferenceMap<V> synchronize(Double2ReferenceMap<V> m) {
/* 588 */     return new SynchronizedMap<>(m);
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
/*     */   public static <V> Double2ReferenceMap<V> synchronize(Double2ReferenceMap<V> m, Object sync) {
/* 602 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<V>
/*     */     extends Double2ReferenceFunctions.UnmodifiableFunction<V>
/*     */     implements Double2ReferenceMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2ReferenceMap<V> map;
/*     */     protected transient ObjectSet<Double2ReferenceMap.Entry<V>> entries;
/*     */     protected transient DoubleSet keys;
/*     */     protected transient ReferenceCollection<V> values;
/*     */     
/*     */     protected UnmodifiableMap(Double2ReferenceMap<V> m) {
/* 615 */       super(m);
/* 616 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 620 */       return this.map.containsValue(v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Double, ? extends V> m) {
/* 624 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
/* 628 */       if (this.entries == null)
/* 629 */         this.entries = ObjectSets.unmodifiable(this.map.double2ReferenceEntrySet()); 
/* 630 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<Double, V>> entrySet() {
/* 641 */       return (ObjectSet)double2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public DoubleSet keySet() {
/* 645 */       if (this.keys == null)
/* 646 */         this.keys = DoubleSets.unmodifiable(this.map.keySet()); 
/* 647 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 651 */       if (this.values == null)
/* 652 */         return ReferenceCollections.unmodifiable(this.map.values()); 
/* 653 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 657 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 661 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 665 */       if (o == this)
/* 666 */         return true; 
/* 667 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(double key, V defaultValue) {
/* 672 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Double, ? super V> action) {
/* 676 */       this.map.forEach(action);
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super Double, ? super V, ? extends V> function) {
/* 680 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V putIfAbsent(double key, V value) {
/* 684 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(double key, Object value) {
/* 688 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V replace(double key, V value) {
/* 692 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(double key, V oldValue, V newValue) {
/* 696 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfAbsent(double key, DoubleFunction<? extends V> mappingFunction) {
/* 701 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V computeIfAbsentPartial(double key, Double2ReferenceFunction<? extends V> mappingFunction) {
/* 705 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(double key, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/* 710 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(double key, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/* 715 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(double key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 720 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 730 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object key, Object value) {
/* 740 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V replace(Double key, V value) {
/* 750 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Double key, V oldValue, V newValue) {
/* 760 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V putIfAbsent(Double key, V value) {
/* 770 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V computeIfAbsent(Double key, Function<? super Double, ? extends V> mappingFunction) {
/* 781 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V computeIfPresent(Double key, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/* 792 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V compute(Double key, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/* 803 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V merge(Double key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 814 */       throw new UnsupportedOperationException();
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
/*     */   public static <V> Double2ReferenceMap<V> unmodifiable(Double2ReferenceMap<V> m) {
/* 827 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2ReferenceMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */