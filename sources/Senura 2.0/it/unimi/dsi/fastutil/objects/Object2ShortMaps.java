/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.shorts.ShortCollection;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortCollections;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortSets;
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
/*     */ import java.util.function.ToIntFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Object2ShortMaps
/*     */ {
/*     */   public static <K> ObjectIterator<Object2ShortMap.Entry<K>> fastIterator(Object2ShortMap<K> map) {
/*  49 */     ObjectSet<Object2ShortMap.Entry<K>> entries = map.object2ShortEntrySet();
/*  50 */     return (entries instanceof Object2ShortMap.FastEntrySet) ? (
/*  51 */       (Object2ShortMap.FastEntrySet)entries).fastIterator() : 
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
/*     */   public static <K> void fastForEach(Object2ShortMap<K> map, Consumer<? super Object2ShortMap.Entry<K>> consumer) {
/*  71 */     ObjectSet<Object2ShortMap.Entry<K>> entries = map.object2ShortEntrySet();
/*  72 */     if (entries instanceof Object2ShortMap.FastEntrySet) {
/*  73 */       ((Object2ShortMap.FastEntrySet)entries).fastForEach(consumer);
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
/*     */   public static <K> ObjectIterable<Object2ShortMap.Entry<K>> fastIterable(Object2ShortMap<K> map) {
/*  91 */     final ObjectSet<Object2ShortMap.Entry<K>> entries = map.object2ShortEntrySet();
/*  92 */     return (entries instanceof Object2ShortMap.FastEntrySet) ? (ObjectIterable)new ObjectIterable<Object2ShortMap.Entry<Object2ShortMap.Entry<K>>>() {
/*     */         public ObjectIterator<Object2ShortMap.Entry<K>> iterator() {
/*  94 */           return ((Object2ShortMap.FastEntrySet<K>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Object2ShortMap.Entry<K>> consumer) {
/*  97 */           ((Object2ShortMap.FastEntrySet<K>)entries).fastForEach(consumer);
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
/*     */     extends Object2ShortFunctions.EmptyFunction<K>
/*     */     implements Object2ShortMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(short v) {
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
/*     */     public void putAll(Map<? extends K, ? extends Short> m) {
/* 132 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Object2ShortMap.Entry<K>> object2ShortEntrySet() {
/* 137 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 142 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortCollection values() {
/* 147 */       return (ShortCollection)ShortSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 151 */       return Object2ShortMaps.EMPTY_MAP;
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
/*     */   public static <K> Object2ShortMap<K> emptyMap() {
/* 187 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends Object2ShortFunctions.Singleton<K>
/*     */     implements Object2ShortMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Object2ShortMap.Entry<K>> entries;
/*     */     
/*     */     protected transient ObjectSet<K> keys;
/*     */     
/*     */     protected transient ShortCollection values;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, short value) {
/* 206 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(short v) {
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
/* 220 */       return (((Short)ov).shortValue() == this.value);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Short> m) {
/* 224 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Object2ShortMap.Entry<K>> object2ShortEntrySet() {
/* 228 */       if (this.entries == null)
/* 229 */         this.entries = ObjectSets.singleton(new AbstractObject2ShortMap.BasicEntry<>(this.key, this.value)); 
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
/*     */     public ObjectSet<Map.Entry<K, Short>> entrySet() {
/* 241 */       return (ObjectSet)object2ShortEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 245 */       if (this.keys == null)
/* 246 */         this.keys = ObjectSets.singleton(this.key); 
/* 247 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ShortCollection values() {
/* 251 */       if (this.values == null)
/* 252 */         this.values = (ShortCollection)ShortSets.singleton(this.value); 
/* 253 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 257 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 261 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ this.value;
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
/*     */   public static <K> Object2ShortMap<K> singleton(K key, short value) {
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
/*     */   public static <K> Object2ShortMap<K> singleton(K key, Short value) {
/* 313 */     return new Singleton<>(key, value.shortValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<K>
/*     */     extends Object2ShortFunctions.SynchronizedFunction<K>
/*     */     implements Object2ShortMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ShortMap<K> map;
/*     */     protected transient ObjectSet<Object2ShortMap.Entry<K>> entries;
/*     */     protected transient ObjectSet<K> keys;
/*     */     protected transient ShortCollection values;
/*     */     
/*     */     protected SynchronizedMap(Object2ShortMap<K> m, Object sync) {
/* 326 */       super(m, sync);
/* 327 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Object2ShortMap<K> m) {
/* 330 */       super(m);
/* 331 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(short v) {
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
/*     */     public void putAll(Map<? extends K, ? extends Short> m) {
/* 353 */       synchronized (this.sync) {
/* 354 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Object2ShortMap.Entry<K>> object2ShortEntrySet() {
/* 359 */       synchronized (this.sync) {
/* 360 */         if (this.entries == null)
/* 361 */           this.entries = ObjectSets.synchronize(this.map.object2ShortEntrySet(), this.sync); 
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
/*     */     public ObjectSet<Map.Entry<K, Short>> entrySet() {
/* 374 */       return (ObjectSet)object2ShortEntrySet();
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
/*     */     public ShortCollection values() {
/* 386 */       synchronized (this.sync) {
/* 387 */         if (this.values == null)
/* 388 */           return ShortCollections.synchronize(this.map.values(), this.sync); 
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
/*     */     public short getOrDefault(Object key, short defaultValue) {
/* 420 */       synchronized (this.sync) {
/* 421 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Short> action) {
/* 426 */       synchronized (this.sync) {
/* 427 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Short, ? extends Short> function) {
/* 433 */       synchronized (this.sync) {
/* 434 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public short putIfAbsent(K key, short value) {
/* 439 */       synchronized (this.sync) {
/* 440 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, short value) {
/* 445 */       synchronized (this.sync) {
/* 446 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public short replace(K key, short value) {
/* 451 */       synchronized (this.sync) {
/* 452 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(K key, short oldValue, short newValue) {
/* 457 */       synchronized (this.sync) {
/* 458 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public short computeShortIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
/* 464 */       synchronized (this.sync) {
/* 465 */         return this.map.computeShortIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */     
/*     */     public short computeShortIfAbsentPartial(K key, Object2ShortFunction<? super K> mappingFunction) {
/* 470 */       synchronized (this.sync) {
/* 471 */         return this.map.computeShortIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public short computeShortIfPresent(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
/* 477 */       synchronized (this.sync) {
/* 478 */         return this.map.computeShortIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public short computeShort(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
/* 484 */       synchronized (this.sync) {
/* 485 */         return this.map.computeShort(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public short mergeShort(K key, short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/* 491 */       synchronized (this.sync) {
/* 492 */         return this.map.mergeShort(key, value, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short getOrDefault(Object key, Short defaultValue) {
/* 503 */       synchronized (this.sync) {
/* 504 */         return this.map.getOrDefault(key, defaultValue);
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
/* 515 */       synchronized (this.sync) {
/* 516 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short replace(K key, Short value) {
/* 527 */       synchronized (this.sync) {
/* 528 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(K key, Short oldValue, Short newValue) {
/* 539 */       synchronized (this.sync) {
/* 540 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short putIfAbsent(K key, Short value) {
/* 551 */       synchronized (this.sync) {
/* 552 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Short computeIfAbsent(K key, Function<? super K, ? extends Short> mappingFunction) {
/* 558 */       synchronized (this.sync) {
/* 559 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Short computeIfPresent(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
/* 565 */       synchronized (this.sync) {
/* 566 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Short compute(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
/* 572 */       synchronized (this.sync) {
/* 573 */         return this.map.compute(key, remappingFunction);
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
/*     */     public Short merge(K key, Short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/* 585 */       synchronized (this.sync) {
/* 586 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <K> Object2ShortMap<K> synchronize(Object2ShortMap<K> m) {
/* 600 */     return new SynchronizedMap<>(m);
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
/*     */   public static <K> Object2ShortMap<K> synchronize(Object2ShortMap<K> m, Object sync) {
/* 614 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<K>
/*     */     extends Object2ShortFunctions.UnmodifiableFunction<K>
/*     */     implements Object2ShortMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ShortMap<K> map;
/*     */     protected transient ObjectSet<Object2ShortMap.Entry<K>> entries;
/*     */     protected transient ObjectSet<K> keys;
/*     */     protected transient ShortCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Object2ShortMap<K> m) {
/* 627 */       super(m);
/* 628 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(short v) {
/* 632 */       return this.map.containsValue(v);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 642 */       return this.map.containsValue(ov);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Short> m) {
/* 646 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Object2ShortMap.Entry<K>> object2ShortEntrySet() {
/* 650 */       if (this.entries == null)
/* 651 */         this.entries = ObjectSets.unmodifiable(this.map.object2ShortEntrySet()); 
/* 652 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<K, Short>> entrySet() {
/* 663 */       return (ObjectSet)object2ShortEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 667 */       if (this.keys == null)
/* 668 */         this.keys = ObjectSets.unmodifiable(this.map.keySet()); 
/* 669 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ShortCollection values() {
/* 673 */       if (this.values == null)
/* 674 */         return ShortCollections.unmodifiable(this.map.values()); 
/* 675 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 679 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 683 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 687 */       if (o == this)
/* 688 */         return true; 
/* 689 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public short getOrDefault(Object key, short defaultValue) {
/* 694 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Short> action) {
/* 698 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Short, ? extends Short> function) {
/* 703 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public short putIfAbsent(K key, short value) {
/* 707 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, short value) {
/* 711 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public short replace(K key, short value) {
/* 715 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(K key, short oldValue, short newValue) {
/* 719 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public short computeShortIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
/* 724 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public short computeShortIfAbsentPartial(K key, Object2ShortFunction<? super K> mappingFunction) {
/* 728 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public short computeShortIfPresent(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
/* 733 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public short computeShort(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
/* 738 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public short mergeShort(K key, short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/* 743 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short getOrDefault(Object key, Short defaultValue) {
/* 753 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object key, Object value) {
/* 763 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short replace(K key, Short value) {
/* 773 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(K key, Short oldValue, Short newValue) {
/* 783 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short putIfAbsent(K key, Short value) {
/* 793 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Short computeIfAbsent(K key, Function<? super K, ? extends Short> mappingFunction) {
/* 798 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Short computeIfPresent(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
/* 803 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Short compute(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
/* 808 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short merge(K key, Short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/* 819 */       throw new UnsupportedOperationException();
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
/*     */   public static <K> Object2ShortMap<K> unmodifiable(Object2ShortMap<K> m) {
/* 832 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ShortMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */