/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectCollections;
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
/*     */ import java.util.Objects;
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
/*     */ public final class Float2ObjectMaps
/*     */ {
/*     */   public static <V> ObjectIterator<Float2ObjectMap.Entry<V>> fastIterator(Float2ObjectMap<V> map) {
/*  48 */     ObjectSet<Float2ObjectMap.Entry<V>> entries = map.float2ObjectEntrySet();
/*  49 */     return (entries instanceof Float2ObjectMap.FastEntrySet) ? (
/*  50 */       (Float2ObjectMap.FastEntrySet)entries).fastIterator() : 
/*  51 */       entries.iterator();
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
/*     */   public static <V> void fastForEach(Float2ObjectMap<V> map, Consumer<? super Float2ObjectMap.Entry<V>> consumer) {
/*  70 */     ObjectSet<Float2ObjectMap.Entry<V>> entries = map.float2ObjectEntrySet();
/*  71 */     if (entries instanceof Float2ObjectMap.FastEntrySet) {
/*  72 */       ((Float2ObjectMap.FastEntrySet)entries).fastForEach(consumer);
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
/*     */   public static <V> ObjectIterable<Float2ObjectMap.Entry<V>> fastIterable(Float2ObjectMap<V> map) {
/*  90 */     final ObjectSet<Float2ObjectMap.Entry<V>> entries = map.float2ObjectEntrySet();
/*  91 */     return (entries instanceof Float2ObjectMap.FastEntrySet) ? new ObjectIterable<Float2ObjectMap.Entry<V>>() {
/*     */         public ObjectIterator<Float2ObjectMap.Entry<V>> iterator() {
/*  93 */           return ((Float2ObjectMap.FastEntrySet<V>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Float2ObjectMap.Entry<V>> consumer) {
/*  96 */           ((Float2ObjectMap.FastEntrySet<V>)entries).fastForEach(consumer);
/*     */         }
/*  98 */       } : (ObjectIterable<Float2ObjectMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<V>
/*     */     extends Float2ObjectFunctions.EmptyFunction<V>
/*     */     implements Float2ObjectMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 117 */       return false;
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Float, ? extends V> m) {
/* 121 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Float2ObjectMap.Entry<V>> float2ObjectEntrySet() {
/* 126 */       return (ObjectSet<Float2ObjectMap.Entry<V>>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSet keySet() {
/* 131 */       return FloatSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 136 */       return (ObjectCollection<V>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 140 */       return Float2ObjectMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 144 */       return true;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 148 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 152 */       if (!(o instanceof Map))
/* 153 */         return false; 
/* 154 */       return ((Map)o).isEmpty();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 158 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public static final EmptyMap EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Float2ObjectMap<V> emptyMap() {
/* 176 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Float2ObjectFunctions.Singleton<V>
/*     */     implements Float2ObjectMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Float2ObjectMap.Entry<V>> entries;
/*     */     
/*     */     protected transient FloatSet keys;
/*     */     
/*     */     protected transient ObjectCollection<V> values;
/*     */ 
/*     */     
/*     */     protected Singleton(float key, V value) {
/* 195 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 199 */       return Objects.equals(this.value, v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Float, ? extends V> m) {
/* 203 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Float2ObjectMap.Entry<V>> float2ObjectEntrySet() {
/* 207 */       if (this.entries == null)
/* 208 */         this.entries = ObjectSets.singleton(new AbstractFloat2ObjectMap.BasicEntry<>(this.key, this.value)); 
/* 209 */       return this.entries;
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
/* 220 */       return (ObjectSet)float2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public FloatSet keySet() {
/* 224 */       if (this.keys == null)
/* 225 */         this.keys = FloatSets.singleton(this.key); 
/* 226 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 230 */       if (this.values == null)
/* 231 */         this.values = (ObjectCollection<V>)ObjectSets.singleton(this.value); 
/* 232 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 236 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 240 */       return HashCommon.float2int(this.key) ^ ((this.value == null) ? 0 : this.value.hashCode());
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 244 */       if (o == this)
/* 245 */         return true; 
/* 246 */       if (!(o instanceof Map))
/* 247 */         return false; 
/* 248 */       Map<?, ?> m = (Map<?, ?>)o;
/* 249 */       if (m.size() != 1)
/* 250 */         return false; 
/* 251 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 255 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static <V> Float2ObjectMap<V> singleton(float key, V value) {
/* 274 */     return new Singleton<>(key, value);
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
/*     */   public static <V> Float2ObjectMap<V> singleton(Float key, V value) {
/* 292 */     return new Singleton<>(key.floatValue(), value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<V>
/*     */     extends Float2ObjectFunctions.SynchronizedFunction<V>
/*     */     implements Float2ObjectMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Float2ObjectMap<V> map;
/*     */     protected transient ObjectSet<Float2ObjectMap.Entry<V>> entries;
/*     */     protected transient FloatSet keys;
/*     */     protected transient ObjectCollection<V> values;
/*     */     
/*     */     protected SynchronizedMap(Float2ObjectMap<V> m, Object sync) {
/* 305 */       super(m, sync);
/* 306 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Float2ObjectMap<V> m) {
/* 309 */       super(m);
/* 310 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 314 */       synchronized (this.sync) {
/* 315 */         return this.map.containsValue(v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Float, ? extends V> m) {
/* 320 */       synchronized (this.sync) {
/* 321 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Float2ObjectMap.Entry<V>> float2ObjectEntrySet() {
/* 326 */       synchronized (this.sync) {
/* 327 */         if (this.entries == null)
/* 328 */           this.entries = ObjectSets.synchronize(this.map.float2ObjectEntrySet(), this.sync); 
/* 329 */         return this.entries;
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
/* 341 */       return (ObjectSet)float2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public FloatSet keySet() {
/* 345 */       synchronized (this.sync) {
/* 346 */         if (this.keys == null)
/* 347 */           this.keys = FloatSets.synchronize(this.map.keySet(), this.sync); 
/* 348 */         return this.keys;
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 353 */       synchronized (this.sync) {
/* 354 */         if (this.values == null)
/* 355 */           return ObjectCollections.synchronize(this.map.values(), this.sync); 
/* 356 */         return this.values;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 361 */       synchronized (this.sync) {
/* 362 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 367 */       synchronized (this.sync) {
/* 368 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 373 */       if (o == this)
/* 374 */         return true; 
/* 375 */       synchronized (this.sync) {
/* 376 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 380 */       synchronized (this.sync) {
/* 381 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(float key, V defaultValue) {
/* 387 */       synchronized (this.sync) {
/* 388 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Float, ? super V> action) {
/* 393 */       synchronized (this.sync) {
/* 394 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super Float, ? super V, ? extends V> function) {
/* 399 */       synchronized (this.sync) {
/* 400 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V putIfAbsent(float key, V value) {
/* 405 */       synchronized (this.sync) {
/* 406 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(float key, Object value) {
/* 411 */       synchronized (this.sync) {
/* 412 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V replace(float key, V value) {
/* 417 */       synchronized (this.sync) {
/* 418 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(float key, V oldValue, V newValue) {
/* 423 */       synchronized (this.sync) {
/* 424 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfAbsent(float key, DoubleFunction<? extends V> mappingFunction) {
/* 430 */       synchronized (this.sync) {
/* 431 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V computeIfAbsentPartial(float key, Float2ObjectFunction<? extends V> mappingFunction) {
/* 436 */       synchronized (this.sync) {
/* 437 */         return this.map.computeIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(float key, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
/* 443 */       synchronized (this.sync) {
/* 444 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(float key, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
/* 450 */       synchronized (this.sync) {
/* 451 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(float key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 457 */       synchronized (this.sync) {
/* 458 */         return this.map.merge(key, value, remappingFunction);
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
/* 469 */       synchronized (this.sync) {
/* 470 */         return this.map.getOrDefault(key, defaultValue);
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
/* 481 */       synchronized (this.sync) {
/* 482 */         return this.map.remove(key, value);
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
/* 493 */       synchronized (this.sync) {
/* 494 */         return this.map.replace(key, value);
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
/* 505 */       synchronized (this.sync) {
/* 506 */         return this.map.replace(key, oldValue, newValue);
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
/* 517 */       synchronized (this.sync) {
/* 518 */         return this.map.putIfAbsent(key, value);
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
/* 530 */       synchronized (this.sync) {
/* 531 */         return this.map.computeIfAbsent(key, mappingFunction);
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
/* 543 */       synchronized (this.sync) {
/* 544 */         return this.map.computeIfPresent(key, remappingFunction);
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
/* 556 */       synchronized (this.sync) {
/* 557 */         return this.map.compute(key, remappingFunction);
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
/* 569 */       synchronized (this.sync) {
/* 570 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <V> Float2ObjectMap<V> synchronize(Float2ObjectMap<V> m) {
/* 584 */     return new SynchronizedMap<>(m);
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
/*     */   public static <V> Float2ObjectMap<V> synchronize(Float2ObjectMap<V> m, Object sync) {
/* 598 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<V>
/*     */     extends Float2ObjectFunctions.UnmodifiableFunction<V>
/*     */     implements Float2ObjectMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Float2ObjectMap<V> map;
/*     */     protected transient ObjectSet<Float2ObjectMap.Entry<V>> entries;
/*     */     protected transient FloatSet keys;
/*     */     protected transient ObjectCollection<V> values;
/*     */     
/*     */     protected UnmodifiableMap(Float2ObjectMap<V> m) {
/* 611 */       super(m);
/* 612 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 616 */       return this.map.containsValue(v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Float, ? extends V> m) {
/* 620 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Float2ObjectMap.Entry<V>> float2ObjectEntrySet() {
/* 624 */       if (this.entries == null)
/* 625 */         this.entries = ObjectSets.unmodifiable(this.map.float2ObjectEntrySet()); 
/* 626 */       return this.entries;
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
/* 637 */       return (ObjectSet)float2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public FloatSet keySet() {
/* 641 */       if (this.keys == null)
/* 642 */         this.keys = FloatSets.unmodifiable(this.map.keySet()); 
/* 643 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 647 */       if (this.values == null)
/* 648 */         return ObjectCollections.unmodifiable(this.map.values()); 
/* 649 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 653 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 657 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 661 */       if (o == this)
/* 662 */         return true; 
/* 663 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(float key, V defaultValue) {
/* 668 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Float, ? super V> action) {
/* 672 */       this.map.forEach(action);
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super Float, ? super V, ? extends V> function) {
/* 676 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V putIfAbsent(float key, V value) {
/* 680 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(float key, Object value) {
/* 684 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V replace(float key, V value) {
/* 688 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(float key, V oldValue, V newValue) {
/* 692 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfAbsent(float key, DoubleFunction<? extends V> mappingFunction) {
/* 697 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V computeIfAbsentPartial(float key, Float2ObjectFunction<? extends V> mappingFunction) {
/* 701 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(float key, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
/* 706 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(float key, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
/* 711 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(float key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 716 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 726 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object key, Object value) {
/* 736 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V replace(Float key, V value) {
/* 746 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Float key, V oldValue, V newValue) {
/* 756 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V putIfAbsent(Float key, V value) {
/* 766 */       throw new UnsupportedOperationException();
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
/* 777 */       throw new UnsupportedOperationException();
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
/* 788 */       throw new UnsupportedOperationException();
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
/* 799 */       throw new UnsupportedOperationException();
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
/* 810 */       throw new UnsupportedOperationException();
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
/*     */   public static <V> Float2ObjectMap<V> unmodifiable(Float2ObjectMap<V> m) {
/* 823 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2ObjectMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */