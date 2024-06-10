/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Object2ObjectMaps
/*     */ {
/*     */   public static <K, V> ObjectIterator<Object2ObjectMap.Entry<K, V>> fastIterator(Object2ObjectMap<K, V> map) {
/*  48 */     ObjectSet<Object2ObjectMap.Entry<K, V>> entries = map.object2ObjectEntrySet();
/*  49 */     return (entries instanceof Object2ObjectMap.FastEntrySet) ? (
/*  50 */       (Object2ObjectMap.FastEntrySet)entries).fastIterator() : 
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
/*     */   public static <K, V> void fastForEach(Object2ObjectMap<K, V> map, Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
/*  70 */     ObjectSet<Object2ObjectMap.Entry<K, V>> entries = map.object2ObjectEntrySet();
/*  71 */     if (entries instanceof Object2ObjectMap.FastEntrySet) {
/*  72 */       ((Object2ObjectMap.FastEntrySet)entries).fastForEach(consumer);
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
/*     */   public static <K, V> ObjectIterable<Object2ObjectMap.Entry<K, V>> fastIterable(Object2ObjectMap<K, V> map) {
/*  90 */     final ObjectSet<Object2ObjectMap.Entry<K, V>> entries = map.object2ObjectEntrySet();
/*  91 */     return (entries instanceof Object2ObjectMap.FastEntrySet) ? (ObjectIterable)new ObjectIterable<Object2ObjectMap.Entry<Object2ObjectMap.Entry<K, V>, V>>() {
/*     */         public ObjectIterator<Object2ObjectMap.Entry<K, V>> iterator() {
/*  93 */           return ((Object2ObjectMap.FastEntrySet<K, V>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
/*  96 */           ((Object2ObjectMap.FastEntrySet<K, V>)entries).fastForEach(consumer);
/*     */         }
/*  98 */       } : entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<K, V>
/*     */     extends Object2ObjectFunctions.EmptyFunction<K, V>
/*     */     implements Object2ObjectMap<K, V>, Serializable, Cloneable
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
/*     */     public void putAll(Map<? extends K, ? extends V> m) {
/* 121 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
/* 126 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 131 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 136 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 140 */       return Object2ObjectMaps.EMPTY_MAP;
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
/* 165 */   public static final EmptyMap EMPTY_MAP = new EmptyMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> Object2ObjectMap<K, V> emptyMap() {
/* 176 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K, V>
/*     */     extends Object2ObjectFunctions.Singleton<K, V>
/*     */     implements Object2ObjectMap<K, V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Object2ObjectMap.Entry<K, V>> entries;
/*     */     
/*     */     protected transient ObjectSet<K> keys;
/*     */     
/*     */     protected transient ObjectCollection<V> values;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, V value) {
/* 195 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 199 */       return Objects.equals(this.value, v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends V> m) {
/* 203 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
/* 207 */       if (this.entries == null)
/* 208 */         this.entries = ObjectSets.singleton(new AbstractObject2ObjectMap.BasicEntry<>(this.key, this.value)); 
/* 209 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Map.Entry<K, V>> entrySet() {
/* 215 */       return (ObjectSet)object2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 219 */       if (this.keys == null)
/* 220 */         this.keys = ObjectSets.singleton(this.key); 
/* 221 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 225 */       if (this.values == null)
/* 226 */         this.values = ObjectSets.singleton(this.value); 
/* 227 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 231 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 235 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ ((this.value == null) ? 0 : this.value.hashCode());
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 239 */       if (o == this)
/* 240 */         return true; 
/* 241 */       if (!(o instanceof Map))
/* 242 */         return false; 
/* 243 */       Map<?, ?> m = (Map<?, ?>)o;
/* 244 */       if (m.size() != 1)
/* 245 */         return false; 
/* 246 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 250 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static <K, V> Object2ObjectMap<K, V> singleton(K key, V value) {
/* 269 */     return new Singleton<>(key, value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<K, V>
/*     */     extends Object2ObjectFunctions.SynchronizedFunction<K, V>
/*     */     implements Object2ObjectMap<K, V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ObjectMap<K, V> map;
/*     */     protected transient ObjectSet<Object2ObjectMap.Entry<K, V>> entries;
/*     */     protected transient ObjectSet<K> keys;
/*     */     protected transient ObjectCollection<V> values;
/*     */     
/*     */     protected SynchronizedMap(Object2ObjectMap<K, V> m, Object sync) {
/* 282 */       super(m, sync);
/* 283 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Object2ObjectMap<K, V> m) {
/* 286 */       super(m);
/* 287 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 291 */       synchronized (this.sync) {
/* 292 */         return this.map.containsValue(v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends V> m) {
/* 297 */       synchronized (this.sync) {
/* 298 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
/* 303 */       synchronized (this.sync) {
/* 304 */         if (this.entries == null)
/* 305 */           this.entries = ObjectSets.synchronize(this.map.object2ObjectEntrySet(), this.sync); 
/* 306 */         return this.entries;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Map.Entry<K, V>> entrySet() {
/* 313 */       return (ObjectSet)object2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 317 */       synchronized (this.sync) {
/* 318 */         if (this.keys == null)
/* 319 */           this.keys = ObjectSets.synchronize(this.map.keySet(), this.sync); 
/* 320 */         return this.keys;
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 325 */       synchronized (this.sync) {
/* 326 */         if (this.values == null)
/* 327 */           return ObjectCollections.synchronize(this.map.values(), this.sync); 
/* 328 */         return this.values;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 333 */       synchronized (this.sync) {
/* 334 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 339 */       synchronized (this.sync) {
/* 340 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 345 */       if (o == this)
/* 346 */         return true; 
/* 347 */       synchronized (this.sync) {
/* 348 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 352 */       synchronized (this.sync) {
/* 353 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 359 */       synchronized (this.sync) {
/* 360 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super V> action) {
/* 365 */       synchronized (this.sync) {
/* 366 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
/* 371 */       synchronized (this.sync) {
/* 372 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V putIfAbsent(K key, V value) {
/* 377 */       synchronized (this.sync) {
/* 378 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, Object value) {
/* 383 */       synchronized (this.sync) {
/* 384 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V replace(K key, V value) {
/* 389 */       synchronized (this.sync) {
/* 390 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(K key, V oldValue, V newValue) {
/* 395 */       synchronized (this.sync) {
/* 396 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 402 */       synchronized (this.sync) {
/* 403 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 409 */       synchronized (this.sync) {
/* 410 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 416 */       synchronized (this.sync) {
/* 417 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <K, V> Object2ObjectMap<K, V> synchronize(Object2ObjectMap<K, V> m) {
/* 431 */     return new SynchronizedMap<>(m);
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
/*     */   public static <K, V> Object2ObjectMap<K, V> synchronize(Object2ObjectMap<K, V> m, Object sync) {
/* 445 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<K, V>
/*     */     extends Object2ObjectFunctions.UnmodifiableFunction<K, V>
/*     */     implements Object2ObjectMap<K, V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ObjectMap<K, V> map;
/*     */     protected transient ObjectSet<Object2ObjectMap.Entry<K, V>> entries;
/*     */     protected transient ObjectSet<K> keys;
/*     */     protected transient ObjectCollection<V> values;
/*     */     
/*     */     protected UnmodifiableMap(Object2ObjectMap<K, V> m) {
/* 458 */       super(m);
/* 459 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 463 */       return this.map.containsValue(v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends V> m) {
/* 467 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
/* 471 */       if (this.entries == null)
/* 472 */         this.entries = ObjectSets.unmodifiable(this.map.object2ObjectEntrySet()); 
/* 473 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Map.Entry<K, V>> entrySet() {
/* 479 */       return (ObjectSet)object2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 483 */       if (this.keys == null)
/* 484 */         this.keys = ObjectSets.unmodifiable(this.map.keySet()); 
/* 485 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 489 */       if (this.values == null)
/* 490 */         return ObjectCollections.unmodifiable(this.map.values()); 
/* 491 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 495 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 499 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 503 */       if (o == this)
/* 504 */         return true; 
/* 505 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 510 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super V> action) {
/* 514 */       this.map.forEach(action);
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
/* 518 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V putIfAbsent(K key, V value) {
/* 522 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, Object value) {
/* 526 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V replace(K key, V value) {
/* 530 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(K key, V oldValue, V newValue) {
/* 534 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 539 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 544 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 549 */       throw new UnsupportedOperationException();
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
/*     */   public static <K, V> Object2ObjectMap<K, V> unmodifiable(Object2ObjectMap<K, V> m) {
/* 562 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ObjectMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */