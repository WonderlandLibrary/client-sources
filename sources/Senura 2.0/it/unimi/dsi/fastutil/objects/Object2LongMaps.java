/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollections;
/*     */ import it.unimi.dsi.fastutil.longs.LongSets;
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
/*     */ import java.util.function.ToLongFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Object2LongMaps
/*     */ {
/*     */   public static <K> ObjectIterator<Object2LongMap.Entry<K>> fastIterator(Object2LongMap<K> map) {
/*  49 */     ObjectSet<Object2LongMap.Entry<K>> entries = map.object2LongEntrySet();
/*  50 */     return (entries instanceof Object2LongMap.FastEntrySet) ? (
/*  51 */       (Object2LongMap.FastEntrySet)entries).fastIterator() : 
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
/*     */   public static <K> void fastForEach(Object2LongMap<K> map, Consumer<? super Object2LongMap.Entry<K>> consumer) {
/*  71 */     ObjectSet<Object2LongMap.Entry<K>> entries = map.object2LongEntrySet();
/*  72 */     if (entries instanceof Object2LongMap.FastEntrySet) {
/*  73 */       ((Object2LongMap.FastEntrySet)entries).fastForEach(consumer);
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
/*     */   public static <K> ObjectIterable<Object2LongMap.Entry<K>> fastIterable(Object2LongMap<K> map) {
/*  91 */     final ObjectSet<Object2LongMap.Entry<K>> entries = map.object2LongEntrySet();
/*  92 */     return (entries instanceof Object2LongMap.FastEntrySet) ? (ObjectIterable)new ObjectIterable<Object2LongMap.Entry<Object2LongMap.Entry<K>>>() {
/*     */         public ObjectIterator<Object2LongMap.Entry<K>> iterator() {
/*  94 */           return ((Object2LongMap.FastEntrySet<K>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Object2LongMap.Entry<K>> consumer) {
/*  97 */           ((Object2LongMap.FastEntrySet<K>)entries).fastForEach(consumer);
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
/*     */     extends Object2LongFunctions.EmptyFunction<K>
/*     */     implements Object2LongMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(long v) {
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
/*     */     public void putAll(Map<? extends K, ? extends Long> m) {
/* 132 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Object2LongMap.Entry<K>> object2LongEntrySet() {
/* 137 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 142 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongCollection values() {
/* 147 */       return (LongCollection)LongSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 151 */       return Object2LongMaps.EMPTY_MAP;
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
/*     */   public static <K> Object2LongMap<K> emptyMap() {
/* 187 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends Object2LongFunctions.Singleton<K>
/*     */     implements Object2LongMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Object2LongMap.Entry<K>> entries;
/*     */     
/*     */     protected transient ObjectSet<K> keys;
/*     */     
/*     */     protected transient LongCollection values;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, long value) {
/* 206 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(long v) {
/* 210 */       return (this.value == v);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 220 */       return (((Long)ov).longValue() == this.value);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Long> m) {
/* 224 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Object2LongMap.Entry<K>> object2LongEntrySet() {
/* 228 */       if (this.entries == null)
/* 229 */         this.entries = ObjectSets.singleton(new AbstractObject2LongMap.BasicEntry<>(this.key, this.value)); 
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
/*     */     public ObjectSet<Map.Entry<K, Long>> entrySet() {
/* 241 */       return (ObjectSet)object2LongEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 245 */       if (this.keys == null)
/* 246 */         this.keys = ObjectSets.singleton(this.key); 
/* 247 */       return this.keys;
/*     */     }
/*     */     
/*     */     public LongCollection values() {
/* 251 */       if (this.values == null)
/* 252 */         this.values = (LongCollection)LongSets.singleton(this.value); 
/* 253 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 257 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 261 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ HashCommon.long2int(this.value);
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
/*     */   public static <K> Object2LongMap<K> singleton(K key, long value) {
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
/*     */   public static <K> Object2LongMap<K> singleton(K key, Long value) {
/* 313 */     return new Singleton<>(key, value.longValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<K>
/*     */     extends Object2LongFunctions.SynchronizedFunction<K>
/*     */     implements Object2LongMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2LongMap<K> map;
/*     */     protected transient ObjectSet<Object2LongMap.Entry<K>> entries;
/*     */     protected transient ObjectSet<K> keys;
/*     */     protected transient LongCollection values;
/*     */     
/*     */     protected SynchronizedMap(Object2LongMap<K> m, Object sync) {
/* 326 */       super(m, sync);
/* 327 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Object2LongMap<K> m) {
/* 330 */       super(m);
/* 331 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(long v) {
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
/*     */     public void putAll(Map<? extends K, ? extends Long> m) {
/* 353 */       synchronized (this.sync) {
/* 354 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Object2LongMap.Entry<K>> object2LongEntrySet() {
/* 359 */       synchronized (this.sync) {
/* 360 */         if (this.entries == null)
/* 361 */           this.entries = ObjectSets.synchronize(this.map.object2LongEntrySet(), this.sync); 
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
/*     */     public ObjectSet<Map.Entry<K, Long>> entrySet() {
/* 374 */       return (ObjectSet)object2LongEntrySet();
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
/*     */     public LongCollection values() {
/* 386 */       synchronized (this.sync) {
/* 387 */         if (this.values == null)
/* 388 */           return LongCollections.synchronize(this.map.values(), this.sync); 
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
/*     */     public long getOrDefault(Object key, long defaultValue) {
/* 420 */       synchronized (this.sync) {
/* 421 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Long> action) {
/* 426 */       synchronized (this.sync) {
/* 427 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Long, ? extends Long> function) {
/* 432 */       synchronized (this.sync) {
/* 433 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public long putIfAbsent(K key, long value) {
/* 438 */       synchronized (this.sync) {
/* 439 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, long value) {
/* 444 */       synchronized (this.sync) {
/* 445 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public long replace(K key, long value) {
/* 450 */       synchronized (this.sync) {
/* 451 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(K key, long oldValue, long newValue) {
/* 456 */       synchronized (this.sync) {
/* 457 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeLongIfAbsent(K key, ToLongFunction<? super K> mappingFunction) {
/* 463 */       synchronized (this.sync) {
/* 464 */         return this.map.computeLongIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */     
/*     */     public long computeLongIfAbsentPartial(K key, Object2LongFunction<? super K> mappingFunction) {
/* 469 */       synchronized (this.sync) {
/* 470 */         return this.map.computeLongIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeLongIfPresent(K key, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/* 476 */       synchronized (this.sync) {
/* 477 */         return this.map.computeLongIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeLong(K key, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/* 483 */       synchronized (this.sync) {
/* 484 */         return this.map.computeLong(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long mergeLong(K key, long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 490 */       synchronized (this.sync) {
/* 491 */         return this.map.mergeLong(key, value, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long getOrDefault(Object key, Long defaultValue) {
/* 502 */       synchronized (this.sync) {
/* 503 */         return this.map.getOrDefault(key, defaultValue);
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
/* 514 */       synchronized (this.sync) {
/* 515 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long replace(K key, Long value) {
/* 526 */       synchronized (this.sync) {
/* 527 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(K key, Long oldValue, Long newValue) {
/* 538 */       synchronized (this.sync) {
/* 539 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long putIfAbsent(K key, Long value) {
/* 550 */       synchronized (this.sync) {
/* 551 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Long computeIfAbsent(K key, Function<? super K, ? extends Long> mappingFunction) {
/* 557 */       synchronized (this.sync) {
/* 558 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Long computeIfPresent(K key, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/* 564 */       synchronized (this.sync) {
/* 565 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Long compute(K key, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/* 571 */       synchronized (this.sync) {
/* 572 */         return this.map.compute(key, remappingFunction);
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
/*     */     public Long merge(K key, Long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 584 */       synchronized (this.sync) {
/* 585 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <K> Object2LongMap<K> synchronize(Object2LongMap<K> m) {
/* 599 */     return new SynchronizedMap<>(m);
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
/*     */   public static <K> Object2LongMap<K> synchronize(Object2LongMap<K> m, Object sync) {
/* 613 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<K>
/*     */     extends Object2LongFunctions.UnmodifiableFunction<K>
/*     */     implements Object2LongMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2LongMap<K> map;
/*     */     protected transient ObjectSet<Object2LongMap.Entry<K>> entries;
/*     */     protected transient ObjectSet<K> keys;
/*     */     protected transient LongCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Object2LongMap<K> m) {
/* 626 */       super(m);
/* 627 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(long v) {
/* 631 */       return this.map.containsValue(v);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 641 */       return this.map.containsValue(ov);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Long> m) {
/* 645 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Object2LongMap.Entry<K>> object2LongEntrySet() {
/* 649 */       if (this.entries == null)
/* 650 */         this.entries = ObjectSets.unmodifiable(this.map.object2LongEntrySet()); 
/* 651 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<K, Long>> entrySet() {
/* 662 */       return (ObjectSet)object2LongEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 666 */       if (this.keys == null)
/* 667 */         this.keys = ObjectSets.unmodifiable(this.map.keySet()); 
/* 668 */       return this.keys;
/*     */     }
/*     */     
/*     */     public LongCollection values() {
/* 672 */       if (this.values == null)
/* 673 */         return LongCollections.unmodifiable(this.map.values()); 
/* 674 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 678 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 682 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 686 */       if (o == this)
/* 687 */         return true; 
/* 688 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public long getOrDefault(Object key, long defaultValue) {
/* 693 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Long> action) {
/* 697 */       this.map.forEach(action);
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Long, ? extends Long> function) {
/* 701 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public long putIfAbsent(K key, long value) {
/* 705 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, long value) {
/* 709 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public long replace(K key, long value) {
/* 713 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(K key, long oldValue, long newValue) {
/* 717 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeLongIfAbsent(K key, ToLongFunction<? super K> mappingFunction) {
/* 722 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public long computeLongIfAbsentPartial(K key, Object2LongFunction<? super K> mappingFunction) {
/* 726 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeLongIfPresent(K key, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/* 731 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeLong(K key, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/* 736 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long mergeLong(K key, long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 741 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long getOrDefault(Object key, Long defaultValue) {
/* 751 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object key, Object value) {
/* 761 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long replace(K key, Long value) {
/* 771 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(K key, Long oldValue, Long newValue) {
/* 781 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long putIfAbsent(K key, Long value) {
/* 791 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Long computeIfAbsent(K key, Function<? super K, ? extends Long> mappingFunction) {
/* 796 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Long computeIfPresent(K key, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/* 801 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Long compute(K key, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/* 806 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long merge(K key, Long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 817 */       throw new UnsupportedOperationException();
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
/*     */   public static <K> Object2LongMap<K> unmodifiable(Object2LongMap<K> m) {
/* 830 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2LongMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */