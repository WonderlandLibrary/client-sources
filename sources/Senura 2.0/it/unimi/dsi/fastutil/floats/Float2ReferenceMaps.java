/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public final class Float2ReferenceMaps
/*     */ {
/*     */   public static <V> ObjectIterator<Float2ReferenceMap.Entry<V>> fastIterator(Float2ReferenceMap<V> map) {
/*  49 */     ObjectSet<Float2ReferenceMap.Entry<V>> entries = map.float2ReferenceEntrySet();
/*  50 */     return (entries instanceof Float2ReferenceMap.FastEntrySet) ? (
/*  51 */       (Float2ReferenceMap.FastEntrySet)entries).fastIterator() : 
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
/*     */   public static <V> void fastForEach(Float2ReferenceMap<V> map, Consumer<? super Float2ReferenceMap.Entry<V>> consumer) {
/*  71 */     ObjectSet<Float2ReferenceMap.Entry<V>> entries = map.float2ReferenceEntrySet();
/*  72 */     if (entries instanceof Float2ReferenceMap.FastEntrySet) {
/*  73 */       ((Float2ReferenceMap.FastEntrySet)entries).fastForEach(consumer);
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
/*     */   public static <V> ObjectIterable<Float2ReferenceMap.Entry<V>> fastIterable(Float2ReferenceMap<V> map) {
/*  91 */     final ObjectSet<Float2ReferenceMap.Entry<V>> entries = map.float2ReferenceEntrySet();
/*  92 */     return (entries instanceof Float2ReferenceMap.FastEntrySet) ? new ObjectIterable<Float2ReferenceMap.Entry<V>>() {
/*     */         public ObjectIterator<Float2ReferenceMap.Entry<V>> iterator() {
/*  94 */           return ((Float2ReferenceMap.FastEntrySet<V>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Float2ReferenceMap.Entry<V>> consumer) {
/*  97 */           ((Float2ReferenceMap.FastEntrySet<V>)entries).fastForEach(consumer);
/*     */         }
/*  99 */       } : (ObjectIterable<Float2ReferenceMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<V>
/*     */     extends Float2ReferenceFunctions.EmptyFunction<V>
/*     */     implements Float2ReferenceMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 118 */       return false;
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Float, ? extends V> m) {
/* 122 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Float2ReferenceMap.Entry<V>> float2ReferenceEntrySet() {
/* 127 */       return (ObjectSet<Float2ReferenceMap.Entry<V>>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSet keySet() {
/* 132 */       return FloatSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 137 */       return (ReferenceCollection<V>)ReferenceSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 141 */       return Float2ReferenceMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 145 */       return true;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 149 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 153 */       if (!(o instanceof Map))
/* 154 */         return false; 
/* 155 */       return ((Map)o).isEmpty();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 159 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   public static final EmptyMap EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Float2ReferenceMap<V> emptyMap() {
/* 177 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Float2ReferenceFunctions.Singleton<V>
/*     */     implements Float2ReferenceMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Float2ReferenceMap.Entry<V>> entries;
/*     */     
/*     */     protected transient FloatSet keys;
/*     */     
/*     */     protected transient ReferenceCollection<V> values;
/*     */ 
/*     */     
/*     */     protected Singleton(float key, V value) {
/* 196 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 200 */       return (this.value == v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Float, ? extends V> m) {
/* 204 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Float2ReferenceMap.Entry<V>> float2ReferenceEntrySet() {
/* 208 */       if (this.entries == null)
/* 209 */         this.entries = ObjectSets.singleton(new AbstractFloat2ReferenceMap.BasicEntry<>(this.key, this.value)); 
/* 210 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<Float, V>> entrySet() {
/* 221 */       return (ObjectSet)float2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public FloatSet keySet() {
/* 225 */       if (this.keys == null)
/* 226 */         this.keys = FloatSets.singleton(this.key); 
/* 227 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 231 */       if (this.values == null)
/* 232 */         this.values = (ReferenceCollection<V>)ReferenceSets.singleton(this.value); 
/* 233 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 237 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 241 */       return HashCommon.float2int(this.key) ^ (
/* 242 */         (this.value == null) ? 0 : System.identityHashCode(this.value));
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 246 */       if (o == this)
/* 247 */         return true; 
/* 248 */       if (!(o instanceof Map))
/* 249 */         return false; 
/* 250 */       Map<?, ?> m = (Map<?, ?>)o;
/* 251 */       if (m.size() != 1)
/* 252 */         return false; 
/* 253 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 257 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static <V> Float2ReferenceMap<V> singleton(float key, V value) {
/* 276 */     return new Singleton<>(key, value);
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
/*     */   public static <V> Float2ReferenceMap<V> singleton(Float key, V value) {
/* 294 */     return new Singleton<>(key.floatValue(), value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<V>
/*     */     extends Float2ReferenceFunctions.SynchronizedFunction<V>
/*     */     implements Float2ReferenceMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Float2ReferenceMap<V> map;
/*     */     protected transient ObjectSet<Float2ReferenceMap.Entry<V>> entries;
/*     */     protected transient FloatSet keys;
/*     */     protected transient ReferenceCollection<V> values;
/*     */     
/*     */     protected SynchronizedMap(Float2ReferenceMap<V> m, Object sync) {
/* 307 */       super(m, sync);
/* 308 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Float2ReferenceMap<V> m) {
/* 311 */       super(m);
/* 312 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 316 */       synchronized (this.sync) {
/* 317 */         return this.map.containsValue(v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Float, ? extends V> m) {
/* 322 */       synchronized (this.sync) {
/* 323 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Float2ReferenceMap.Entry<V>> float2ReferenceEntrySet() {
/* 328 */       synchronized (this.sync) {
/* 329 */         if (this.entries == null)
/* 330 */           this.entries = ObjectSets.synchronize(this.map.float2ReferenceEntrySet(), this.sync); 
/* 331 */         return this.entries;
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
/*     */     public ObjectSet<Map.Entry<Float, V>> entrySet() {
/* 343 */       return (ObjectSet)float2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public FloatSet keySet() {
/* 347 */       synchronized (this.sync) {
/* 348 */         if (this.keys == null)
/* 349 */           this.keys = FloatSets.synchronize(this.map.keySet(), this.sync); 
/* 350 */         return this.keys;
/*     */       } 
/*     */     }
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 355 */       synchronized (this.sync) {
/* 356 */         if (this.values == null)
/* 357 */           return ReferenceCollections.synchronize(this.map.values(), this.sync); 
/* 358 */         return this.values;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 363 */       synchronized (this.sync) {
/* 364 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 369 */       synchronized (this.sync) {
/* 370 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 375 */       if (o == this)
/* 376 */         return true; 
/* 377 */       synchronized (this.sync) {
/* 378 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 382 */       synchronized (this.sync) {
/* 383 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(float key, V defaultValue) {
/* 389 */       synchronized (this.sync) {
/* 390 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Float, ? super V> action) {
/* 395 */       synchronized (this.sync) {
/* 396 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super Float, ? super V, ? extends V> function) {
/* 401 */       synchronized (this.sync) {
/* 402 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V putIfAbsent(float key, V value) {
/* 407 */       synchronized (this.sync) {
/* 408 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(float key, Object value) {
/* 413 */       synchronized (this.sync) {
/* 414 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V replace(float key, V value) {
/* 419 */       synchronized (this.sync) {
/* 420 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(float key, V oldValue, V newValue) {
/* 425 */       synchronized (this.sync) {
/* 426 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfAbsent(float key, DoubleFunction<? extends V> mappingFunction) {
/* 432 */       synchronized (this.sync) {
/* 433 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V computeIfAbsentPartial(float key, Float2ReferenceFunction<? extends V> mappingFunction) {
/* 438 */       synchronized (this.sync) {
/* 439 */         return this.map.computeIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(float key, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
/* 445 */       synchronized (this.sync) {
/* 446 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(float key, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
/* 452 */       synchronized (this.sync) {
/* 453 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(float key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 459 */       synchronized (this.sync) {
/* 460 */         return this.map.merge(key, value, remappingFunction);
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
/* 471 */       synchronized (this.sync) {
/* 472 */         return this.map.getOrDefault(key, defaultValue);
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
/* 483 */       synchronized (this.sync) {
/* 484 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V replace(Float key, V value) {
/* 495 */       synchronized (this.sync) {
/* 496 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Float key, V oldValue, V newValue) {
/* 507 */       synchronized (this.sync) {
/* 508 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V putIfAbsent(Float key, V value) {
/* 519 */       synchronized (this.sync) {
/* 520 */         return this.map.putIfAbsent(key, value);
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
/*     */     public V computeIfAbsent(Float key, Function<? super Float, ? extends V> mappingFunction) {
/* 532 */       synchronized (this.sync) {
/* 533 */         return this.map.computeIfAbsent(key, mappingFunction);
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
/*     */     public V computeIfPresent(Float key, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
/* 545 */       synchronized (this.sync) {
/* 546 */         return this.map.computeIfPresent(key, remappingFunction);
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
/*     */     public V compute(Float key, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
/* 558 */       synchronized (this.sync) {
/* 559 */         return this.map.compute(key, remappingFunction);
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
/*     */     public V merge(Float key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 571 */       synchronized (this.sync) {
/* 572 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <V> Float2ReferenceMap<V> synchronize(Float2ReferenceMap<V> m) {
/* 586 */     return new SynchronizedMap<>(m);
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
/*     */   public static <V> Float2ReferenceMap<V> synchronize(Float2ReferenceMap<V> m, Object sync) {
/* 600 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<V>
/*     */     extends Float2ReferenceFunctions.UnmodifiableFunction<V>
/*     */     implements Float2ReferenceMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Float2ReferenceMap<V> map;
/*     */     protected transient ObjectSet<Float2ReferenceMap.Entry<V>> entries;
/*     */     protected transient FloatSet keys;
/*     */     protected transient ReferenceCollection<V> values;
/*     */     
/*     */     protected UnmodifiableMap(Float2ReferenceMap<V> m) {
/* 613 */       super(m);
/* 614 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 618 */       return this.map.containsValue(v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Float, ? extends V> m) {
/* 622 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Float2ReferenceMap.Entry<V>> float2ReferenceEntrySet() {
/* 626 */       if (this.entries == null)
/* 627 */         this.entries = ObjectSets.unmodifiable(this.map.float2ReferenceEntrySet()); 
/* 628 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<Float, V>> entrySet() {
/* 639 */       return (ObjectSet)float2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public FloatSet keySet() {
/* 643 */       if (this.keys == null)
/* 644 */         this.keys = FloatSets.unmodifiable(this.map.keySet()); 
/* 645 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 649 */       if (this.values == null)
/* 650 */         return ReferenceCollections.unmodifiable(this.map.values()); 
/* 651 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 655 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 659 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 663 */       if (o == this)
/* 664 */         return true; 
/* 665 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(float key, V defaultValue) {
/* 670 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Float, ? super V> action) {
/* 674 */       this.map.forEach(action);
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super Float, ? super V, ? extends V> function) {
/* 678 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V putIfAbsent(float key, V value) {
/* 682 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(float key, Object value) {
/* 686 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V replace(float key, V value) {
/* 690 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(float key, V oldValue, V newValue) {
/* 694 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfAbsent(float key, DoubleFunction<? extends V> mappingFunction) {
/* 699 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V computeIfAbsentPartial(float key, Float2ReferenceFunction<? extends V> mappingFunction) {
/* 703 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(float key, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
/* 708 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(float key, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
/* 713 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(float key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 718 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 728 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object key, Object value) {
/* 738 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V replace(Float key, V value) {
/* 748 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Float key, V oldValue, V newValue) {
/* 758 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V putIfAbsent(Float key, V value) {
/* 768 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V computeIfAbsent(Float key, Function<? super Float, ? extends V> mappingFunction) {
/* 779 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V computeIfPresent(Float key, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
/* 790 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V compute(Float key, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
/* 801 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V merge(Float key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 812 */       throw new UnsupportedOperationException();
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
/*     */   public static <V> Float2ReferenceMap<V> unmodifiable(Float2ReferenceMap<V> m) {
/* 825 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2ReferenceMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */