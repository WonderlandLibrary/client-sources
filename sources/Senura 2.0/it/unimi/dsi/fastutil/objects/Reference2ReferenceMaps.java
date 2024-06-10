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
/*     */ 
/*     */ public final class Reference2ReferenceMaps
/*     */ {
/*     */   public static <K, V> ObjectIterator<Reference2ReferenceMap.Entry<K, V>> fastIterator(Reference2ReferenceMap<K, V> map) {
/*  50 */     ObjectSet<Reference2ReferenceMap.Entry<K, V>> entries = map.reference2ReferenceEntrySet();
/*  51 */     return (entries instanceof Reference2ReferenceMap.FastEntrySet) ? (
/*  52 */       (Reference2ReferenceMap.FastEntrySet)entries).fastIterator() : 
/*  53 */       entries.iterator();
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
/*     */   public static <K, V> void fastForEach(Reference2ReferenceMap<K, V> map, Consumer<? super Reference2ReferenceMap.Entry<K, V>> consumer) {
/*  72 */     ObjectSet<Reference2ReferenceMap.Entry<K, V>> entries = map.reference2ReferenceEntrySet();
/*  73 */     if (entries instanceof Reference2ReferenceMap.FastEntrySet) {
/*  74 */       ((Reference2ReferenceMap.FastEntrySet)entries).fastForEach(consumer);
/*     */     } else {
/*  76 */       entries.forEach(consumer);
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
/*     */   public static <K, V> ObjectIterable<Reference2ReferenceMap.Entry<K, V>> fastIterable(Reference2ReferenceMap<K, V> map) {
/*  93 */     final ObjectSet<Reference2ReferenceMap.Entry<K, V>> entries = map.reference2ReferenceEntrySet();
/*  94 */     return (entries instanceof Reference2ReferenceMap.FastEntrySet) ? 
/*  95 */       (ObjectIterable)new ObjectIterable<Reference2ReferenceMap.Entry<Reference2ReferenceMap.Entry<K, V>, V>>() {
/*     */         public ObjectIterator<Reference2ReferenceMap.Entry<K, V>> iterator() {
/*  97 */           return ((Reference2ReferenceMap.FastEntrySet<K, V>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Reference2ReferenceMap.Entry<K, V>> consumer) {
/* 100 */           ((Reference2ReferenceMap.FastEntrySet<K, V>)entries).fastForEach(consumer);
/*     */         }
/* 103 */       } : entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<K, V>
/*     */     extends Reference2ReferenceFunctions.EmptyFunction<K, V>
/*     */     implements Reference2ReferenceMap<K, V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 122 */       return false;
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends V> m) {
/* 126 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Reference2ReferenceMap.Entry<K, V>> reference2ReferenceEntrySet() {
/* 131 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 136 */       return ReferenceSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 141 */       return ReferenceSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 145 */       return Reference2ReferenceMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 149 */       return true;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 153 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 157 */       if (!(o instanceof Map))
/* 158 */         return false; 
/* 159 */       return ((Map)o).isEmpty();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 163 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   public static final EmptyMap EMPTY_MAP = new EmptyMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> Reference2ReferenceMap<K, V> emptyMap() {
/* 181 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K, V>
/*     */     extends Reference2ReferenceFunctions.Singleton<K, V>
/*     */     implements Reference2ReferenceMap<K, V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Reference2ReferenceMap.Entry<K, V>> entries;
/*     */     
/*     */     protected transient ReferenceSet<K> keys;
/*     */     
/*     */     protected transient ReferenceCollection<V> values;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, V value) {
/* 200 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 204 */       return (this.value == v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends V> m) {
/* 208 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Reference2ReferenceMap.Entry<K, V>> reference2ReferenceEntrySet() {
/* 212 */       if (this.entries == null)
/* 213 */         this.entries = ObjectSets.singleton(new AbstractReference2ReferenceMap.BasicEntry<>(this.key, this.value)); 
/* 214 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Map.Entry<K, V>> entrySet() {
/* 220 */       return (ObjectSet)reference2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 224 */       if (this.keys == null)
/* 225 */         this.keys = ReferenceSets.singleton(this.key); 
/* 226 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 230 */       if (this.values == null)
/* 231 */         this.values = ReferenceSets.singleton(this.value); 
/* 232 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 236 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 240 */       return System.identityHashCode(this.key) ^ ((this.value == null) ? 0 : System.identityHashCode(this.value));
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
/*     */   public static <K, V> Reference2ReferenceMap<K, V> singleton(K key, V value) {
/* 274 */     return new Singleton<>(key, value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<K, V>
/*     */     extends Reference2ReferenceFunctions.SynchronizedFunction<K, V>
/*     */     implements Reference2ReferenceMap<K, V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2ReferenceMap<K, V> map;
/*     */     protected transient ObjectSet<Reference2ReferenceMap.Entry<K, V>> entries;
/*     */     protected transient ReferenceSet<K> keys;
/*     */     protected transient ReferenceCollection<V> values;
/*     */     
/*     */     protected SynchronizedMap(Reference2ReferenceMap<K, V> m, Object sync) {
/* 287 */       super(m, sync);
/* 288 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Reference2ReferenceMap<K, V> m) {
/* 291 */       super(m);
/* 292 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 296 */       synchronized (this.sync) {
/* 297 */         return this.map.containsValue(v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends V> m) {
/* 302 */       synchronized (this.sync) {
/* 303 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Reference2ReferenceMap.Entry<K, V>> reference2ReferenceEntrySet() {
/* 308 */       synchronized (this.sync) {
/* 309 */         if (this.entries == null)
/* 310 */           this.entries = ObjectSets.synchronize(this.map.reference2ReferenceEntrySet(), this.sync); 
/* 311 */         return this.entries;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Map.Entry<K, V>> entrySet() {
/* 318 */       return (ObjectSet)reference2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 322 */       synchronized (this.sync) {
/* 323 */         if (this.keys == null)
/* 324 */           this.keys = ReferenceSets.synchronize(this.map.keySet(), this.sync); 
/* 325 */         return this.keys;
/*     */       } 
/*     */     }
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 330 */       synchronized (this.sync) {
/* 331 */         if (this.values == null)
/* 332 */           return ReferenceCollections.synchronize(this.map.values(), this.sync); 
/* 333 */         return this.values;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 338 */       synchronized (this.sync) {
/* 339 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 344 */       synchronized (this.sync) {
/* 345 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 350 */       if (o == this)
/* 351 */         return true; 
/* 352 */       synchronized (this.sync) {
/* 353 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 357 */       synchronized (this.sync) {
/* 358 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 364 */       synchronized (this.sync) {
/* 365 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super V> action) {
/* 370 */       synchronized (this.sync) {
/* 371 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
/* 376 */       synchronized (this.sync) {
/* 377 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V putIfAbsent(K key, V value) {
/* 382 */       synchronized (this.sync) {
/* 383 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, Object value) {
/* 388 */       synchronized (this.sync) {
/* 389 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V replace(K key, V value) {
/* 394 */       synchronized (this.sync) {
/* 395 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(K key, V oldValue, V newValue) {
/* 400 */       synchronized (this.sync) {
/* 401 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 407 */       synchronized (this.sync) {
/* 408 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 414 */       synchronized (this.sync) {
/* 415 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 421 */       synchronized (this.sync) {
/* 422 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <K, V> Reference2ReferenceMap<K, V> synchronize(Reference2ReferenceMap<K, V> m) {
/* 436 */     return new SynchronizedMap<>(m);
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
/*     */   public static <K, V> Reference2ReferenceMap<K, V> synchronize(Reference2ReferenceMap<K, V> m, Object sync) {
/* 451 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<K, V>
/*     */     extends Reference2ReferenceFunctions.UnmodifiableFunction<K, V>
/*     */     implements Reference2ReferenceMap<K, V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2ReferenceMap<K, V> map;
/*     */     protected transient ObjectSet<Reference2ReferenceMap.Entry<K, V>> entries;
/*     */     protected transient ReferenceSet<K> keys;
/*     */     protected transient ReferenceCollection<V> values;
/*     */     
/*     */     protected UnmodifiableMap(Reference2ReferenceMap<K, V> m) {
/* 464 */       super(m);
/* 465 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 469 */       return this.map.containsValue(v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends V> m) {
/* 473 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Reference2ReferenceMap.Entry<K, V>> reference2ReferenceEntrySet() {
/* 477 */       if (this.entries == null)
/* 478 */         this.entries = ObjectSets.unmodifiable(this.map.reference2ReferenceEntrySet()); 
/* 479 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Map.Entry<K, V>> entrySet() {
/* 485 */       return (ObjectSet)reference2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 489 */       if (this.keys == null)
/* 490 */         this.keys = ReferenceSets.unmodifiable(this.map.keySet()); 
/* 491 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 495 */       if (this.values == null)
/* 496 */         return ReferenceCollections.unmodifiable(this.map.values()); 
/* 497 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 501 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 505 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 509 */       if (o == this)
/* 510 */         return true; 
/* 511 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 516 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super V> action) {
/* 520 */       this.map.forEach(action);
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
/* 524 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V putIfAbsent(K key, V value) {
/* 528 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, Object value) {
/* 532 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V replace(K key, V value) {
/* 536 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(K key, V oldValue, V newValue) {
/* 540 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 545 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 550 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 555 */       throw new UnsupportedOperationException();
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
/*     */   public static <K, V> Reference2ReferenceMap<K, V> unmodifiable(Reference2ReferenceMap<K, V> m) {
/* 568 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2ReferenceMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */