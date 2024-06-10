/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
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
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Short2ObjectMaps
/*     */ {
/*     */   public static <V> ObjectIterator<Short2ObjectMap.Entry<V>> fastIterator(Short2ObjectMap<V> map) {
/*  48 */     ObjectSet<Short2ObjectMap.Entry<V>> entries = map.short2ObjectEntrySet();
/*  49 */     return (entries instanceof Short2ObjectMap.FastEntrySet) ? (
/*  50 */       (Short2ObjectMap.FastEntrySet)entries).fastIterator() : 
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
/*     */   public static <V> void fastForEach(Short2ObjectMap<V> map, Consumer<? super Short2ObjectMap.Entry<V>> consumer) {
/*  70 */     ObjectSet<Short2ObjectMap.Entry<V>> entries = map.short2ObjectEntrySet();
/*  71 */     if (entries instanceof Short2ObjectMap.FastEntrySet) {
/*  72 */       ((Short2ObjectMap.FastEntrySet)entries).fastForEach(consumer);
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
/*     */   public static <V> ObjectIterable<Short2ObjectMap.Entry<V>> fastIterable(Short2ObjectMap<V> map) {
/*  90 */     final ObjectSet<Short2ObjectMap.Entry<V>> entries = map.short2ObjectEntrySet();
/*  91 */     return (entries instanceof Short2ObjectMap.FastEntrySet) ? new ObjectIterable<Short2ObjectMap.Entry<V>>() {
/*     */         public ObjectIterator<Short2ObjectMap.Entry<V>> iterator() {
/*  93 */           return ((Short2ObjectMap.FastEntrySet<V>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Short2ObjectMap.Entry<V>> consumer) {
/*  96 */           ((Short2ObjectMap.FastEntrySet<V>)entries).fastForEach(consumer);
/*     */         }
/*  98 */       } : (ObjectIterable<Short2ObjectMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<V>
/*     */     extends Short2ObjectFunctions.EmptyFunction<V>
/*     */     implements Short2ObjectMap<V>, Serializable, Cloneable
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
/*     */     public void putAll(Map<? extends Short, ? extends V> m) {
/* 121 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Short2ObjectMap.Entry<V>> short2ObjectEntrySet() {
/* 126 */       return (ObjectSet<Short2ObjectMap.Entry<V>>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSet keySet() {
/* 131 */       return ShortSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 136 */       return (ObjectCollection<V>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 140 */       return Short2ObjectMaps.EMPTY_MAP;
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
/*     */   public static <V> Short2ObjectMap<V> emptyMap() {
/* 176 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Short2ObjectFunctions.Singleton<V>
/*     */     implements Short2ObjectMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Short2ObjectMap.Entry<V>> entries;
/*     */     
/*     */     protected transient ShortSet keys;
/*     */     
/*     */     protected transient ObjectCollection<V> values;
/*     */ 
/*     */     
/*     */     protected Singleton(short key, V value) {
/* 195 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 199 */       return Objects.equals(this.value, v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Short, ? extends V> m) {
/* 203 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Short2ObjectMap.Entry<V>> short2ObjectEntrySet() {
/* 207 */       if (this.entries == null)
/* 208 */         this.entries = ObjectSets.singleton(new AbstractShort2ObjectMap.BasicEntry<>(this.key, this.value)); 
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
/*     */     public ObjectSet<Map.Entry<Short, V>> entrySet() {
/* 220 */       return (ObjectSet)short2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public ShortSet keySet() {
/* 224 */       if (this.keys == null)
/* 225 */         this.keys = ShortSets.singleton(this.key); 
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
/* 240 */       return this.key ^ ((this.value == null) ? 0 : this.value.hashCode());
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
/*     */   public static <V> Short2ObjectMap<V> singleton(short key, V value) {
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
/*     */   public static <V> Short2ObjectMap<V> singleton(Short key, V value) {
/* 292 */     return new Singleton<>(key.shortValue(), value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<V>
/*     */     extends Short2ObjectFunctions.SynchronizedFunction<V>
/*     */     implements Short2ObjectMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Short2ObjectMap<V> map;
/*     */     protected transient ObjectSet<Short2ObjectMap.Entry<V>> entries;
/*     */     protected transient ShortSet keys;
/*     */     protected transient ObjectCollection<V> values;
/*     */     
/*     */     protected SynchronizedMap(Short2ObjectMap<V> m, Object sync) {
/* 305 */       super(m, sync);
/* 306 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Short2ObjectMap<V> m) {
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
/*     */     public void putAll(Map<? extends Short, ? extends V> m) {
/* 320 */       synchronized (this.sync) {
/* 321 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Short2ObjectMap.Entry<V>> short2ObjectEntrySet() {
/* 326 */       synchronized (this.sync) {
/* 327 */         if (this.entries == null)
/* 328 */           this.entries = ObjectSets.synchronize(this.map.short2ObjectEntrySet(), this.sync); 
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
/*     */     public ObjectSet<Map.Entry<Short, V>> entrySet() {
/* 341 */       return (ObjectSet)short2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public ShortSet keySet() {
/* 345 */       synchronized (this.sync) {
/* 346 */         if (this.keys == null)
/* 347 */           this.keys = ShortSets.synchronize(this.map.keySet(), this.sync); 
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
/*     */     public V getOrDefault(short key, V defaultValue) {
/* 387 */       synchronized (this.sync) {
/* 388 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Short, ? super V> action) {
/* 393 */       synchronized (this.sync) {
/* 394 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super Short, ? super V, ? extends V> function) {
/* 399 */       synchronized (this.sync) {
/* 400 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V putIfAbsent(short key, V value) {
/* 405 */       synchronized (this.sync) {
/* 406 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(short key, Object value) {
/* 411 */       synchronized (this.sync) {
/* 412 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V replace(short key, V value) {
/* 417 */       synchronized (this.sync) {
/* 418 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(short key, V oldValue, V newValue) {
/* 423 */       synchronized (this.sync) {
/* 424 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V computeIfAbsent(short key, IntFunction<? extends V> mappingFunction) {
/* 429 */       synchronized (this.sync) {
/* 430 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V computeIfAbsentPartial(short key, Short2ObjectFunction<? extends V> mappingFunction) {
/* 435 */       synchronized (this.sync) {
/* 436 */         return this.map.computeIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(short key, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
/* 442 */       synchronized (this.sync) {
/* 443 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(short key, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
/* 449 */       synchronized (this.sync) {
/* 450 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(short key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 456 */       synchronized (this.sync) {
/* 457 */         return this.map.merge(key, value, remappingFunction);
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
/* 468 */       synchronized (this.sync) {
/* 469 */         return this.map.getOrDefault(key, defaultValue);
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
/* 480 */       synchronized (this.sync) {
/* 481 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V replace(Short key, V value) {
/* 492 */       synchronized (this.sync) {
/* 493 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Short key, V oldValue, V newValue) {
/* 504 */       synchronized (this.sync) {
/* 505 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V putIfAbsent(Short key, V value) {
/* 516 */       synchronized (this.sync) {
/* 517 */         return this.map.putIfAbsent(key, value);
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
/*     */     public V computeIfAbsent(Short key, Function<? super Short, ? extends V> mappingFunction) {
/* 529 */       synchronized (this.sync) {
/* 530 */         return this.map.computeIfAbsent(key, mappingFunction);
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
/*     */     public V computeIfPresent(Short key, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
/* 542 */       synchronized (this.sync) {
/* 543 */         return this.map.computeIfPresent(key, remappingFunction);
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
/*     */     public V compute(Short key, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
/* 555 */       synchronized (this.sync) {
/* 556 */         return this.map.compute(key, remappingFunction);
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
/*     */     public V merge(Short key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 568 */       synchronized (this.sync) {
/* 569 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <V> Short2ObjectMap<V> synchronize(Short2ObjectMap<V> m) {
/* 583 */     return new SynchronizedMap<>(m);
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
/*     */   public static <V> Short2ObjectMap<V> synchronize(Short2ObjectMap<V> m, Object sync) {
/* 597 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<V>
/*     */     extends Short2ObjectFunctions.UnmodifiableFunction<V>
/*     */     implements Short2ObjectMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Short2ObjectMap<V> map;
/*     */     protected transient ObjectSet<Short2ObjectMap.Entry<V>> entries;
/*     */     protected transient ShortSet keys;
/*     */     protected transient ObjectCollection<V> values;
/*     */     
/*     */     protected UnmodifiableMap(Short2ObjectMap<V> m) {
/* 610 */       super(m);
/* 611 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 615 */       return this.map.containsValue(v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Short, ? extends V> m) {
/* 619 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Short2ObjectMap.Entry<V>> short2ObjectEntrySet() {
/* 623 */       if (this.entries == null)
/* 624 */         this.entries = ObjectSets.unmodifiable(this.map.short2ObjectEntrySet()); 
/* 625 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<Short, V>> entrySet() {
/* 636 */       return (ObjectSet)short2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public ShortSet keySet() {
/* 640 */       if (this.keys == null)
/* 641 */         this.keys = ShortSets.unmodifiable(this.map.keySet()); 
/* 642 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 646 */       if (this.values == null)
/* 647 */         return ObjectCollections.unmodifiable(this.map.values()); 
/* 648 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 652 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 656 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 660 */       if (o == this)
/* 661 */         return true; 
/* 662 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(short key, V defaultValue) {
/* 667 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Short, ? super V> action) {
/* 671 */       this.map.forEach(action);
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super Short, ? super V, ? extends V> function) {
/* 675 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V putIfAbsent(short key, V value) {
/* 679 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(short key, Object value) {
/* 683 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V replace(short key, V value) {
/* 687 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(short key, V oldValue, V newValue) {
/* 691 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V computeIfAbsent(short key, IntFunction<? extends V> mappingFunction) {
/* 695 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V computeIfAbsentPartial(short key, Short2ObjectFunction<? extends V> mappingFunction) {
/* 699 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(short key, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
/* 704 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(short key, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
/* 709 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(short key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 714 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 724 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object key, Object value) {
/* 734 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V replace(Short key, V value) {
/* 744 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Short key, V oldValue, V newValue) {
/* 754 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V putIfAbsent(Short key, V value) {
/* 764 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V computeIfAbsent(Short key, Function<? super Short, ? extends V> mappingFunction) {
/* 775 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V computeIfPresent(Short key, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
/* 786 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V compute(Short key, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
/* 797 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V merge(Short key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 808 */       throw new UnsupportedOperationException();
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
/*     */   public static <V> Short2ObjectMap<V> unmodifiable(Short2ObjectMap<V> m) {
/* 821 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2ObjectMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */