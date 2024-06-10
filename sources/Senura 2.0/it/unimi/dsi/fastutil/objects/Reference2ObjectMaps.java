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
/*     */ public final class Reference2ObjectMaps
/*     */ {
/*     */   public static <K, V> ObjectIterator<Reference2ObjectMap.Entry<K, V>> fastIterator(Reference2ObjectMap<K, V> map) {
/*  48 */     ObjectSet<Reference2ObjectMap.Entry<K, V>> entries = map.reference2ObjectEntrySet();
/*  49 */     return (entries instanceof Reference2ObjectMap.FastEntrySet) ? (
/*  50 */       (Reference2ObjectMap.FastEntrySet)entries).fastIterator() : 
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
/*     */   public static <K, V> void fastForEach(Reference2ObjectMap<K, V> map, Consumer<? super Reference2ObjectMap.Entry<K, V>> consumer) {
/*  70 */     ObjectSet<Reference2ObjectMap.Entry<K, V>> entries = map.reference2ObjectEntrySet();
/*  71 */     if (entries instanceof Reference2ObjectMap.FastEntrySet) {
/*  72 */       ((Reference2ObjectMap.FastEntrySet)entries).fastForEach(consumer);
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
/*     */   public static <K, V> ObjectIterable<Reference2ObjectMap.Entry<K, V>> fastIterable(Reference2ObjectMap<K, V> map) {
/*  90 */     final ObjectSet<Reference2ObjectMap.Entry<K, V>> entries = map.reference2ObjectEntrySet();
/*  91 */     return (entries instanceof Reference2ObjectMap.FastEntrySet) ? 
/*  92 */       (ObjectIterable)new ObjectIterable<Reference2ObjectMap.Entry<Reference2ObjectMap.Entry<K, V>, V>>() {
/*     */         public ObjectIterator<Reference2ObjectMap.Entry<K, V>> iterator() {
/*  94 */           return ((Reference2ObjectMap.FastEntrySet<K, V>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Reference2ObjectMap.Entry<K, V>> consumer) {
/*  97 */           ((Reference2ObjectMap.FastEntrySet<K, V>)entries).fastForEach(consumer);
/*     */         }
/* 100 */       } : entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<K, V>
/*     */     extends Reference2ObjectFunctions.EmptyFunction<K, V>
/*     */     implements Reference2ObjectMap<K, V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 119 */       return false;
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends V> m) {
/* 123 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Reference2ObjectMap.Entry<K, V>> reference2ObjectEntrySet() {
/* 128 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 133 */       return ReferenceSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 138 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 142 */       return Reference2ObjectMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 146 */       return true;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 150 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 154 */       if (!(o instanceof Map))
/* 155 */         return false; 
/* 156 */       return ((Map)o).isEmpty();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 160 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 167 */   public static final EmptyMap EMPTY_MAP = new EmptyMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> Reference2ObjectMap<K, V> emptyMap() {
/* 178 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K, V>
/*     */     extends Reference2ObjectFunctions.Singleton<K, V>
/*     */     implements Reference2ObjectMap<K, V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Reference2ObjectMap.Entry<K, V>> entries;
/*     */     
/*     */     protected transient ReferenceSet<K> keys;
/*     */     
/*     */     protected transient ObjectCollection<V> values;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, V value) {
/* 197 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 201 */       return Objects.equals(this.value, v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends V> m) {
/* 205 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Reference2ObjectMap.Entry<K, V>> reference2ObjectEntrySet() {
/* 209 */       if (this.entries == null)
/* 210 */         this.entries = ObjectSets.singleton(new AbstractReference2ObjectMap.BasicEntry<>(this.key, this.value)); 
/* 211 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Map.Entry<K, V>> entrySet() {
/* 217 */       return (ObjectSet)reference2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 221 */       if (this.keys == null)
/* 222 */         this.keys = ReferenceSets.singleton(this.key); 
/* 223 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 227 */       if (this.values == null)
/* 228 */         this.values = ObjectSets.singleton(this.value); 
/* 229 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 233 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 237 */       return System.identityHashCode(this.key) ^ ((this.value == null) ? 0 : this.value.hashCode());
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 241 */       if (o == this)
/* 242 */         return true; 
/* 243 */       if (!(o instanceof Map))
/* 244 */         return false; 
/* 245 */       Map<?, ?> m = (Map<?, ?>)o;
/* 246 */       if (m.size() != 1)
/* 247 */         return false; 
/* 248 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 252 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static <K, V> Reference2ObjectMap<K, V> singleton(K key, V value) {
/* 271 */     return new Singleton<>(key, value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<K, V>
/*     */     extends Reference2ObjectFunctions.SynchronizedFunction<K, V>
/*     */     implements Reference2ObjectMap<K, V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2ObjectMap<K, V> map;
/*     */     protected transient ObjectSet<Reference2ObjectMap.Entry<K, V>> entries;
/*     */     protected transient ReferenceSet<K> keys;
/*     */     protected transient ObjectCollection<V> values;
/*     */     
/*     */     protected SynchronizedMap(Reference2ObjectMap<K, V> m, Object sync) {
/* 284 */       super(m, sync);
/* 285 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Reference2ObjectMap<K, V> m) {
/* 288 */       super(m);
/* 289 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 293 */       synchronized (this.sync) {
/* 294 */         return this.map.containsValue(v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends V> m) {
/* 299 */       synchronized (this.sync) {
/* 300 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Reference2ObjectMap.Entry<K, V>> reference2ObjectEntrySet() {
/* 305 */       synchronized (this.sync) {
/* 306 */         if (this.entries == null)
/* 307 */           this.entries = ObjectSets.synchronize(this.map.reference2ObjectEntrySet(), this.sync); 
/* 308 */         return this.entries;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Map.Entry<K, V>> entrySet() {
/* 315 */       return (ObjectSet)reference2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 319 */       synchronized (this.sync) {
/* 320 */         if (this.keys == null)
/* 321 */           this.keys = ReferenceSets.synchronize(this.map.keySet(), this.sync); 
/* 322 */         return this.keys;
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 327 */       synchronized (this.sync) {
/* 328 */         if (this.values == null)
/* 329 */           return ObjectCollections.synchronize(this.map.values(), this.sync); 
/* 330 */         return this.values;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 335 */       synchronized (this.sync) {
/* 336 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 341 */       synchronized (this.sync) {
/* 342 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 347 */       if (o == this)
/* 348 */         return true; 
/* 349 */       synchronized (this.sync) {
/* 350 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 354 */       synchronized (this.sync) {
/* 355 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 361 */       synchronized (this.sync) {
/* 362 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super V> action) {
/* 367 */       synchronized (this.sync) {
/* 368 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
/* 373 */       synchronized (this.sync) {
/* 374 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V putIfAbsent(K key, V value) {
/* 379 */       synchronized (this.sync) {
/* 380 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, Object value) {
/* 385 */       synchronized (this.sync) {
/* 386 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V replace(K key, V value) {
/* 391 */       synchronized (this.sync) {
/* 392 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(K key, V oldValue, V newValue) {
/* 397 */       synchronized (this.sync) {
/* 398 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 404 */       synchronized (this.sync) {
/* 405 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 411 */       synchronized (this.sync) {
/* 412 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 418 */       synchronized (this.sync) {
/* 419 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <K, V> Reference2ObjectMap<K, V> synchronize(Reference2ObjectMap<K, V> m) {
/* 433 */     return new SynchronizedMap<>(m);
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
/*     */   public static <K, V> Reference2ObjectMap<K, V> synchronize(Reference2ObjectMap<K, V> m, Object sync) {
/* 447 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<K, V>
/*     */     extends Reference2ObjectFunctions.UnmodifiableFunction<K, V>
/*     */     implements Reference2ObjectMap<K, V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2ObjectMap<K, V> map;
/*     */     protected transient ObjectSet<Reference2ObjectMap.Entry<K, V>> entries;
/*     */     protected transient ReferenceSet<K> keys;
/*     */     protected transient ObjectCollection<V> values;
/*     */     
/*     */     protected UnmodifiableMap(Reference2ObjectMap<K, V> m) {
/* 460 */       super(m);
/* 461 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 465 */       return this.map.containsValue(v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends V> m) {
/* 469 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Reference2ObjectMap.Entry<K, V>> reference2ObjectEntrySet() {
/* 473 */       if (this.entries == null)
/* 474 */         this.entries = ObjectSets.unmodifiable(this.map.reference2ObjectEntrySet()); 
/* 475 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Map.Entry<K, V>> entrySet() {
/* 481 */       return (ObjectSet)reference2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 485 */       if (this.keys == null)
/* 486 */         this.keys = ReferenceSets.unmodifiable(this.map.keySet()); 
/* 487 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 491 */       if (this.values == null)
/* 492 */         return ObjectCollections.unmodifiable(this.map.values()); 
/* 493 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 497 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 501 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 505 */       if (o == this)
/* 506 */         return true; 
/* 507 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 512 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super V> action) {
/* 516 */       this.map.forEach(action);
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
/* 520 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V putIfAbsent(K key, V value) {
/* 524 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, Object value) {
/* 528 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V replace(K key, V value) {
/* 532 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(K key, V oldValue, V newValue) {
/* 536 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 541 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 546 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 551 */       throw new UnsupportedOperationException();
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
/*     */   public static <K, V> Reference2ObjectMap<K, V> unmodifiable(Reference2ObjectMap<K, V> m) {
/* 564 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2ObjectMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */