/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Object2ReferenceMaps
/*     */ {
/*     */   public static <K, V> ObjectIterator<Object2ReferenceMap.Entry<K, V>> fastIterator(Object2ReferenceMap<K, V> map) {
/*  49 */     ObjectSet<Object2ReferenceMap.Entry<K, V>> entries = map.object2ReferenceEntrySet();
/*  50 */     return (entries instanceof Object2ReferenceMap.FastEntrySet) ? (
/*  51 */       (Object2ReferenceMap.FastEntrySet)entries).fastIterator() : 
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
/*     */   public static <K, V> void fastForEach(Object2ReferenceMap<K, V> map, Consumer<? super Object2ReferenceMap.Entry<K, V>> consumer) {
/*  71 */     ObjectSet<Object2ReferenceMap.Entry<K, V>> entries = map.object2ReferenceEntrySet();
/*  72 */     if (entries instanceof Object2ReferenceMap.FastEntrySet) {
/*  73 */       ((Object2ReferenceMap.FastEntrySet)entries).fastForEach(consumer);
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
/*     */   public static <K, V> ObjectIterable<Object2ReferenceMap.Entry<K, V>> fastIterable(Object2ReferenceMap<K, V> map) {
/*  91 */     final ObjectSet<Object2ReferenceMap.Entry<K, V>> entries = map.object2ReferenceEntrySet();
/*  92 */     return (entries instanceof Object2ReferenceMap.FastEntrySet) ? 
/*  93 */       (ObjectIterable)new ObjectIterable<Object2ReferenceMap.Entry<Object2ReferenceMap.Entry<K, V>, V>>() {
/*     */         public ObjectIterator<Object2ReferenceMap.Entry<K, V>> iterator() {
/*  95 */           return ((Object2ReferenceMap.FastEntrySet<K, V>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Object2ReferenceMap.Entry<K, V>> consumer) {
/*  98 */           ((Object2ReferenceMap.FastEntrySet<K, V>)entries).fastForEach(consumer);
/*     */         }
/* 101 */       } : entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<K, V>
/*     */     extends Object2ReferenceFunctions.EmptyFunction<K, V>
/*     */     implements Object2ReferenceMap<K, V>, Serializable, Cloneable
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
/*     */     public void putAll(Map<? extends K, ? extends V> m) {
/* 124 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Object2ReferenceMap.Entry<K, V>> object2ReferenceEntrySet() {
/* 129 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 134 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 139 */       return ReferenceSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 143 */       return Object2ReferenceMaps.EMPTY_MAP;
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
/* 168 */   public static final EmptyMap EMPTY_MAP = new EmptyMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> Object2ReferenceMap<K, V> emptyMap() {
/* 179 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K, V>
/*     */     extends Object2ReferenceFunctions.Singleton<K, V>
/*     */     implements Object2ReferenceMap<K, V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Object2ReferenceMap.Entry<K, V>> entries;
/*     */     
/*     */     protected transient ObjectSet<K> keys;
/*     */     
/*     */     protected transient ReferenceCollection<V> values;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, V value) {
/* 198 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 202 */       return (this.value == v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends V> m) {
/* 206 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Object2ReferenceMap.Entry<K, V>> object2ReferenceEntrySet() {
/* 210 */       if (this.entries == null)
/* 211 */         this.entries = ObjectSets.singleton(new AbstractObject2ReferenceMap.BasicEntry<>(this.key, this.value)); 
/* 212 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Map.Entry<K, V>> entrySet() {
/* 218 */       return (ObjectSet)object2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 222 */       if (this.keys == null)
/* 223 */         this.keys = ObjectSets.singleton(this.key); 
/* 224 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 228 */       if (this.values == null)
/* 229 */         this.values = ReferenceSets.singleton(this.value); 
/* 230 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 234 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 238 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ ((this.value == null) ? 0 : System.identityHashCode(this.value));
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 242 */       if (o == this)
/* 243 */         return true; 
/* 244 */       if (!(o instanceof Map))
/* 245 */         return false; 
/* 246 */       Map<?, ?> m = (Map<?, ?>)o;
/* 247 */       if (m.size() != 1)
/* 248 */         return false; 
/* 249 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 253 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static <K, V> Object2ReferenceMap<K, V> singleton(K key, V value) {
/* 272 */     return new Singleton<>(key, value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<K, V>
/*     */     extends Object2ReferenceFunctions.SynchronizedFunction<K, V>
/*     */     implements Object2ReferenceMap<K, V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ReferenceMap<K, V> map;
/*     */     protected transient ObjectSet<Object2ReferenceMap.Entry<K, V>> entries;
/*     */     protected transient ObjectSet<K> keys;
/*     */     protected transient ReferenceCollection<V> values;
/*     */     
/*     */     protected SynchronizedMap(Object2ReferenceMap<K, V> m, Object sync) {
/* 285 */       super(m, sync);
/* 286 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Object2ReferenceMap<K, V> m) {
/* 289 */       super(m);
/* 290 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 294 */       synchronized (this.sync) {
/* 295 */         return this.map.containsValue(v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends V> m) {
/* 300 */       synchronized (this.sync) {
/* 301 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Object2ReferenceMap.Entry<K, V>> object2ReferenceEntrySet() {
/* 306 */       synchronized (this.sync) {
/* 307 */         if (this.entries == null)
/* 308 */           this.entries = ObjectSets.synchronize(this.map.object2ReferenceEntrySet(), this.sync); 
/* 309 */         return this.entries;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Map.Entry<K, V>> entrySet() {
/* 316 */       return (ObjectSet)object2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 320 */       synchronized (this.sync) {
/* 321 */         if (this.keys == null)
/* 322 */           this.keys = ObjectSets.synchronize(this.map.keySet(), this.sync); 
/* 323 */         return this.keys;
/*     */       } 
/*     */     }
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 328 */       synchronized (this.sync) {
/* 329 */         if (this.values == null)
/* 330 */           return ReferenceCollections.synchronize(this.map.values(), this.sync); 
/* 331 */         return this.values;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 336 */       synchronized (this.sync) {
/* 337 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 342 */       synchronized (this.sync) {
/* 343 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 348 */       if (o == this)
/* 349 */         return true; 
/* 350 */       synchronized (this.sync) {
/* 351 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 355 */       synchronized (this.sync) {
/* 356 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 362 */       synchronized (this.sync) {
/* 363 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super V> action) {
/* 368 */       synchronized (this.sync) {
/* 369 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
/* 374 */       synchronized (this.sync) {
/* 375 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V putIfAbsent(K key, V value) {
/* 380 */       synchronized (this.sync) {
/* 381 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, Object value) {
/* 386 */       synchronized (this.sync) {
/* 387 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V replace(K key, V value) {
/* 392 */       synchronized (this.sync) {
/* 393 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(K key, V oldValue, V newValue) {
/* 398 */       synchronized (this.sync) {
/* 399 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 405 */       synchronized (this.sync) {
/* 406 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 412 */       synchronized (this.sync) {
/* 413 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 419 */       synchronized (this.sync) {
/* 420 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <K, V> Object2ReferenceMap<K, V> synchronize(Object2ReferenceMap<K, V> m) {
/* 434 */     return new SynchronizedMap<>(m);
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
/*     */   public static <K, V> Object2ReferenceMap<K, V> synchronize(Object2ReferenceMap<K, V> m, Object sync) {
/* 448 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<K, V>
/*     */     extends Object2ReferenceFunctions.UnmodifiableFunction<K, V>
/*     */     implements Object2ReferenceMap<K, V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ReferenceMap<K, V> map;
/*     */     protected transient ObjectSet<Object2ReferenceMap.Entry<K, V>> entries;
/*     */     protected transient ObjectSet<K> keys;
/*     */     protected transient ReferenceCollection<V> values;
/*     */     
/*     */     protected UnmodifiableMap(Object2ReferenceMap<K, V> m) {
/* 461 */       super(m);
/* 462 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 466 */       return this.map.containsValue(v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends V> m) {
/* 470 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Object2ReferenceMap.Entry<K, V>> object2ReferenceEntrySet() {
/* 474 */       if (this.entries == null)
/* 475 */         this.entries = ObjectSets.unmodifiable(this.map.object2ReferenceEntrySet()); 
/* 476 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Map.Entry<K, V>> entrySet() {
/* 482 */       return (ObjectSet)object2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 486 */       if (this.keys == null)
/* 487 */         this.keys = ObjectSets.unmodifiable(this.map.keySet()); 
/* 488 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 492 */       if (this.values == null)
/* 493 */         return ReferenceCollections.unmodifiable(this.map.values()); 
/* 494 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 498 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 502 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 506 */       if (o == this)
/* 507 */         return true; 
/* 508 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 513 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super V> action) {
/* 517 */       this.map.forEach(action);
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
/* 521 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V putIfAbsent(K key, V value) {
/* 525 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, Object value) {
/* 529 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V replace(K key, V value) {
/* 533 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(K key, V oldValue, V newValue) {
/* 537 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 542 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 547 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 552 */       throw new UnsupportedOperationException();
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
/*     */   public static <K, V> Object2ReferenceMap<K, V> unmodifiable(Object2ReferenceMap<K, V> m) {
/* 565 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ReferenceMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */