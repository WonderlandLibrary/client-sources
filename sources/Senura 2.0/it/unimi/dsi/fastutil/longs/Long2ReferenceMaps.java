/*     */ package it.unimi.dsi.fastutil.longs;
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
/*     */ import java.util.function.Function;
/*     */ import java.util.function.LongFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Long2ReferenceMaps
/*     */ {
/*     */   public static <V> ObjectIterator<Long2ReferenceMap.Entry<V>> fastIterator(Long2ReferenceMap<V> map) {
/*  49 */     ObjectSet<Long2ReferenceMap.Entry<V>> entries = map.long2ReferenceEntrySet();
/*  50 */     return (entries instanceof Long2ReferenceMap.FastEntrySet) ? (
/*  51 */       (Long2ReferenceMap.FastEntrySet)entries).fastIterator() : 
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
/*     */   public static <V> void fastForEach(Long2ReferenceMap<V> map, Consumer<? super Long2ReferenceMap.Entry<V>> consumer) {
/*  71 */     ObjectSet<Long2ReferenceMap.Entry<V>> entries = map.long2ReferenceEntrySet();
/*  72 */     if (entries instanceof Long2ReferenceMap.FastEntrySet) {
/*  73 */       ((Long2ReferenceMap.FastEntrySet)entries).fastForEach(consumer);
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
/*     */   public static <V> ObjectIterable<Long2ReferenceMap.Entry<V>> fastIterable(Long2ReferenceMap<V> map) {
/*  91 */     final ObjectSet<Long2ReferenceMap.Entry<V>> entries = map.long2ReferenceEntrySet();
/*  92 */     return (entries instanceof Long2ReferenceMap.FastEntrySet) ? new ObjectIterable<Long2ReferenceMap.Entry<V>>() {
/*     */         public ObjectIterator<Long2ReferenceMap.Entry<V>> iterator() {
/*  94 */           return ((Long2ReferenceMap.FastEntrySet<V>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Long2ReferenceMap.Entry<V>> consumer) {
/*  97 */           ((Long2ReferenceMap.FastEntrySet<V>)entries).fastForEach(consumer);
/*     */         }
/*  99 */       } : (ObjectIterable<Long2ReferenceMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<V>
/*     */     extends Long2ReferenceFunctions.EmptyFunction<V>
/*     */     implements Long2ReferenceMap<V>, Serializable, Cloneable
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
/*     */     public void putAll(Map<? extends Long, ? extends V> m) {
/* 122 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Long2ReferenceMap.Entry<V>> long2ReferenceEntrySet() {
/* 127 */       return (ObjectSet<Long2ReferenceMap.Entry<V>>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSet keySet() {
/* 132 */       return LongSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 137 */       return (ReferenceCollection<V>)ReferenceSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 141 */       return Long2ReferenceMaps.EMPTY_MAP;
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
/*     */   public static <V> Long2ReferenceMap<V> emptyMap() {
/* 177 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Long2ReferenceFunctions.Singleton<V>
/*     */     implements Long2ReferenceMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Long2ReferenceMap.Entry<V>> entries;
/*     */     
/*     */     protected transient LongSet keys;
/*     */     
/*     */     protected transient ReferenceCollection<V> values;
/*     */ 
/*     */     
/*     */     protected Singleton(long key, V value) {
/* 196 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 200 */       return (this.value == v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Long, ? extends V> m) {
/* 204 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Long2ReferenceMap.Entry<V>> long2ReferenceEntrySet() {
/* 208 */       if (this.entries == null)
/* 209 */         this.entries = ObjectSets.singleton(new AbstractLong2ReferenceMap.BasicEntry<>(this.key, this.value)); 
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
/*     */     public ObjectSet<Map.Entry<Long, V>> entrySet() {
/* 221 */       return (ObjectSet)long2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public LongSet keySet() {
/* 225 */       if (this.keys == null)
/* 226 */         this.keys = LongSets.singleton(this.key); 
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
/* 241 */       return HashCommon.long2int(this.key) ^ (
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
/*     */   public static <V> Long2ReferenceMap<V> singleton(long key, V value) {
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
/*     */   public static <V> Long2ReferenceMap<V> singleton(Long key, V value) {
/* 294 */     return new Singleton<>(key.longValue(), value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<V>
/*     */     extends Long2ReferenceFunctions.SynchronizedFunction<V>
/*     */     implements Long2ReferenceMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2ReferenceMap<V> map;
/*     */     protected transient ObjectSet<Long2ReferenceMap.Entry<V>> entries;
/*     */     protected transient LongSet keys;
/*     */     protected transient ReferenceCollection<V> values;
/*     */     
/*     */     protected SynchronizedMap(Long2ReferenceMap<V> m, Object sync) {
/* 307 */       super(m, sync);
/* 308 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Long2ReferenceMap<V> m) {
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
/*     */     public void putAll(Map<? extends Long, ? extends V> m) {
/* 322 */       synchronized (this.sync) {
/* 323 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Long2ReferenceMap.Entry<V>> long2ReferenceEntrySet() {
/* 328 */       synchronized (this.sync) {
/* 329 */         if (this.entries == null)
/* 330 */           this.entries = ObjectSets.synchronize(this.map.long2ReferenceEntrySet(), this.sync); 
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
/*     */     public ObjectSet<Map.Entry<Long, V>> entrySet() {
/* 343 */       return (ObjectSet)long2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public LongSet keySet() {
/* 347 */       synchronized (this.sync) {
/* 348 */         if (this.keys == null)
/* 349 */           this.keys = LongSets.synchronize(this.map.keySet(), this.sync); 
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
/*     */     public V getOrDefault(long key, V defaultValue) {
/* 389 */       synchronized (this.sync) {
/* 390 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Long, ? super V> action) {
/* 395 */       synchronized (this.sync) {
/* 396 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super Long, ? super V, ? extends V> function) {
/* 401 */       synchronized (this.sync) {
/* 402 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V putIfAbsent(long key, V value) {
/* 407 */       synchronized (this.sync) {
/* 408 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(long key, Object value) {
/* 413 */       synchronized (this.sync) {
/* 414 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V replace(long key, V value) {
/* 419 */       synchronized (this.sync) {
/* 420 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(long key, V oldValue, V newValue) {
/* 425 */       synchronized (this.sync) {
/* 426 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V computeIfAbsent(long key, LongFunction<? extends V> mappingFunction) {
/* 431 */       synchronized (this.sync) {
/* 432 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V computeIfAbsentPartial(long key, Long2ReferenceFunction<? extends V> mappingFunction) {
/* 437 */       synchronized (this.sync) {
/* 438 */         return this.map.computeIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
/* 444 */       synchronized (this.sync) {
/* 445 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
/* 451 */       synchronized (this.sync) {
/* 452 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(long key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 458 */       synchronized (this.sync) {
/* 459 */         return this.map.merge(key, value, remappingFunction);
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
/* 470 */       synchronized (this.sync) {
/* 471 */         return this.map.getOrDefault(key, defaultValue);
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
/* 482 */       synchronized (this.sync) {
/* 483 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V replace(Long key, V value) {
/* 494 */       synchronized (this.sync) {
/* 495 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Long key, V oldValue, V newValue) {
/* 506 */       synchronized (this.sync) {
/* 507 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V putIfAbsent(Long key, V value) {
/* 518 */       synchronized (this.sync) {
/* 519 */         return this.map.putIfAbsent(key, value);
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
/*     */     public V computeIfAbsent(Long key, Function<? super Long, ? extends V> mappingFunction) {
/* 531 */       synchronized (this.sync) {
/* 532 */         return this.map.computeIfAbsent(key, mappingFunction);
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
/*     */     public V computeIfPresent(Long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
/* 544 */       synchronized (this.sync) {
/* 545 */         return this.map.computeIfPresent(key, remappingFunction);
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
/*     */     public V compute(Long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
/* 557 */       synchronized (this.sync) {
/* 558 */         return this.map.compute(key, remappingFunction);
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
/*     */     public V merge(Long key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 570 */       synchronized (this.sync) {
/* 571 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <V> Long2ReferenceMap<V> synchronize(Long2ReferenceMap<V> m) {
/* 585 */     return new SynchronizedMap<>(m);
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
/*     */   public static <V> Long2ReferenceMap<V> synchronize(Long2ReferenceMap<V> m, Object sync) {
/* 599 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<V>
/*     */     extends Long2ReferenceFunctions.UnmodifiableFunction<V>
/*     */     implements Long2ReferenceMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2ReferenceMap<V> map;
/*     */     protected transient ObjectSet<Long2ReferenceMap.Entry<V>> entries;
/*     */     protected transient LongSet keys;
/*     */     protected transient ReferenceCollection<V> values;
/*     */     
/*     */     protected UnmodifiableMap(Long2ReferenceMap<V> m) {
/* 612 */       super(m);
/* 613 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 617 */       return this.map.containsValue(v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Long, ? extends V> m) {
/* 621 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Long2ReferenceMap.Entry<V>> long2ReferenceEntrySet() {
/* 625 */       if (this.entries == null)
/* 626 */         this.entries = ObjectSets.unmodifiable(this.map.long2ReferenceEntrySet()); 
/* 627 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<Long, V>> entrySet() {
/* 638 */       return (ObjectSet)long2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public LongSet keySet() {
/* 642 */       if (this.keys == null)
/* 643 */         this.keys = LongSets.unmodifiable(this.map.keySet()); 
/* 644 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 648 */       if (this.values == null)
/* 649 */         return ReferenceCollections.unmodifiable(this.map.values()); 
/* 650 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 654 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 658 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 662 */       if (o == this)
/* 663 */         return true; 
/* 664 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(long key, V defaultValue) {
/* 669 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Long, ? super V> action) {
/* 673 */       this.map.forEach(action);
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super Long, ? super V, ? extends V> function) {
/* 677 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V putIfAbsent(long key, V value) {
/* 681 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(long key, Object value) {
/* 685 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V replace(long key, V value) {
/* 689 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(long key, V oldValue, V newValue) {
/* 693 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V computeIfAbsent(long key, LongFunction<? extends V> mappingFunction) {
/* 697 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V computeIfAbsentPartial(long key, Long2ReferenceFunction<? extends V> mappingFunction) {
/* 701 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
/* 706 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
/* 711 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(long key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
/*     */     public V replace(Long key, V value) {
/* 746 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Long key, V oldValue, V newValue) {
/* 756 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V putIfAbsent(Long key, V value) {
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
/*     */     public V computeIfAbsent(Long key, Function<? super Long, ? extends V> mappingFunction) {
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
/*     */     public V computeIfPresent(Long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
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
/*     */     public V compute(Long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
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
/*     */     public V merge(Long key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
/*     */   public static <V> Long2ReferenceMap<V> unmodifiable(Long2ReferenceMap<V> m) {
/* 823 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2ReferenceMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */