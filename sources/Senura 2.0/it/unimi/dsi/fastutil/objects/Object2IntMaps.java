/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollections;
/*     */ import it.unimi.dsi.fastutil.ints.IntSets;
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
/*     */ public final class Object2IntMaps
/*     */ {
/*     */   public static <K> ObjectIterator<Object2IntMap.Entry<K>> fastIterator(Object2IntMap<K> map) {
/*  49 */     ObjectSet<Object2IntMap.Entry<K>> entries = map.object2IntEntrySet();
/*  50 */     return (entries instanceof Object2IntMap.FastEntrySet) ? (
/*  51 */       (Object2IntMap.FastEntrySet)entries).fastIterator() : 
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
/*     */   public static <K> void fastForEach(Object2IntMap<K> map, Consumer<? super Object2IntMap.Entry<K>> consumer) {
/*  70 */     ObjectSet<Object2IntMap.Entry<K>> entries = map.object2IntEntrySet();
/*  71 */     if (entries instanceof Object2IntMap.FastEntrySet) {
/*  72 */       ((Object2IntMap.FastEntrySet)entries).fastForEach(consumer);
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
/*     */   public static <K> ObjectIterable<Object2IntMap.Entry<K>> fastIterable(Object2IntMap<K> map) {
/*  90 */     final ObjectSet<Object2IntMap.Entry<K>> entries = map.object2IntEntrySet();
/*  91 */     return (entries instanceof Object2IntMap.FastEntrySet) ? (ObjectIterable)new ObjectIterable<Object2IntMap.Entry<Object2IntMap.Entry<K>>>() {
/*     */         public ObjectIterator<Object2IntMap.Entry<K>> iterator() {
/*  93 */           return ((Object2IntMap.FastEntrySet<K>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
/*  96 */           ((Object2IntMap.FastEntrySet<K>)entries).fastForEach(consumer);
/*     */         }
/*  98 */       } : entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<K>
/*     */     extends Object2IntFunctions.EmptyFunction<K>
/*     */     implements Object2IntMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(int v) {
/* 117 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 127 */       return false;
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Integer> m) {
/* 131 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
/* 136 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 141 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntCollection values() {
/* 146 */       return (IntCollection)IntSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 150 */       return Object2IntMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 154 */       return true;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 158 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 162 */       if (!(o instanceof Map))
/* 163 */         return false; 
/* 164 */       return ((Map)o).isEmpty();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 168 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   public static final EmptyMap EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> Object2IntMap<K> emptyMap() {
/* 186 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends Object2IntFunctions.Singleton<K>
/*     */     implements Object2IntMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Object2IntMap.Entry<K>> entries;
/*     */     
/*     */     protected transient ObjectSet<K> keys;
/*     */     
/*     */     protected transient IntCollection values;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, int value) {
/* 205 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(int v) {
/* 209 */       return (this.value == v);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 219 */       return (((Integer)ov).intValue() == this.value);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Integer> m) {
/* 223 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
/* 227 */       if (this.entries == null)
/* 228 */         this.entries = ObjectSets.singleton(new AbstractObject2IntMap.BasicEntry<>(this.key, this.value)); 
/* 229 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<K, Integer>> entrySet() {
/* 240 */       return (ObjectSet)object2IntEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 244 */       if (this.keys == null)
/* 245 */         this.keys = ObjectSets.singleton(this.key); 
/* 246 */       return this.keys;
/*     */     }
/*     */     
/*     */     public IntCollection values() {
/* 250 */       if (this.values == null)
/* 251 */         this.values = (IntCollection)IntSets.singleton(this.value); 
/* 252 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 256 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 260 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ this.value;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 264 */       if (o == this)
/* 265 */         return true; 
/* 266 */       if (!(o instanceof Map))
/* 267 */         return false; 
/* 268 */       Map<?, ?> m = (Map<?, ?>)o;
/* 269 */       if (m.size() != 1)
/* 270 */         return false; 
/* 271 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 275 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static <K> Object2IntMap<K> singleton(K key, int value) {
/* 294 */     return new Singleton<>(key, value);
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
/*     */   public static <K> Object2IntMap<K> singleton(K key, Integer value) {
/* 312 */     return new Singleton<>(key, value.intValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<K>
/*     */     extends Object2IntFunctions.SynchronizedFunction<K>
/*     */     implements Object2IntMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2IntMap<K> map;
/*     */     protected transient ObjectSet<Object2IntMap.Entry<K>> entries;
/*     */     protected transient ObjectSet<K> keys;
/*     */     protected transient IntCollection values;
/*     */     
/*     */     protected SynchronizedMap(Object2IntMap<K> m, Object sync) {
/* 325 */       super(m, sync);
/* 326 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Object2IntMap<K> m) {
/* 329 */       super(m);
/* 330 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(int v) {
/* 334 */       synchronized (this.sync) {
/* 335 */         return this.map.containsValue(v);
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
/* 346 */       synchronized (this.sync) {
/* 347 */         return this.map.containsValue(ov);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Integer> m) {
/* 352 */       synchronized (this.sync) {
/* 353 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
/* 358 */       synchronized (this.sync) {
/* 359 */         if (this.entries == null)
/* 360 */           this.entries = ObjectSets.synchronize(this.map.object2IntEntrySet(), this.sync); 
/* 361 */         return this.entries;
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
/*     */     public ObjectSet<Map.Entry<K, Integer>> entrySet() {
/* 373 */       return (ObjectSet)object2IntEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 377 */       synchronized (this.sync) {
/* 378 */         if (this.keys == null)
/* 379 */           this.keys = ObjectSets.synchronize(this.map.keySet(), this.sync); 
/* 380 */         return this.keys;
/*     */       } 
/*     */     }
/*     */     
/*     */     public IntCollection values() {
/* 385 */       synchronized (this.sync) {
/* 386 */         if (this.values == null)
/* 387 */           return IntCollections.synchronize(this.map.values(), this.sync); 
/* 388 */         return this.values;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 393 */       synchronized (this.sync) {
/* 394 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 399 */       synchronized (this.sync) {
/* 400 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 405 */       if (o == this)
/* 406 */         return true; 
/* 407 */       synchronized (this.sync) {
/* 408 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 412 */       synchronized (this.sync) {
/* 413 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int getOrDefault(Object key, int defaultValue) {
/* 419 */       synchronized (this.sync) {
/* 420 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Integer> action) {
/* 425 */       synchronized (this.sync) {
/* 426 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Integer, ? extends Integer> function) {
/* 432 */       synchronized (this.sync) {
/* 433 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int putIfAbsent(K key, int value) {
/* 438 */       synchronized (this.sync) {
/* 439 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, int value) {
/* 444 */       synchronized (this.sync) {
/* 445 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int replace(K key, int value) {
/* 450 */       synchronized (this.sync) {
/* 451 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(K key, int oldValue, int newValue) {
/* 456 */       synchronized (this.sync) {
/* 457 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int computeIntIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
/* 462 */       synchronized (this.sync) {
/* 463 */         return this.map.computeIntIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int computeIntIfAbsentPartial(K key, Object2IntFunction<? super K> mappingFunction) {
/* 468 */       synchronized (this.sync) {
/* 469 */         return this.map.computeIntIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIntIfPresent(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 475 */       synchronized (this.sync) {
/* 476 */         return this.map.computeIntIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeInt(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 482 */       synchronized (this.sync) {
/* 483 */         return this.map.computeInt(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int mergeInt(K key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/* 489 */       synchronized (this.sync) {
/* 490 */         return this.map.mergeInt(key, value, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer getOrDefault(Object key, Integer defaultValue) {
/* 501 */       synchronized (this.sync) {
/* 502 */         return this.map.getOrDefault(key, defaultValue);
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
/* 513 */       synchronized (this.sync) {
/* 514 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer replace(K key, Integer value) {
/* 525 */       synchronized (this.sync) {
/* 526 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(K key, Integer oldValue, Integer newValue) {
/* 537 */       synchronized (this.sync) {
/* 538 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer putIfAbsent(K key, Integer value) {
/* 549 */       synchronized (this.sync) {
/* 550 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer computeIfAbsent(K key, Function<? super K, ? extends Integer> mappingFunction) {
/* 556 */       synchronized (this.sync) {
/* 557 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer computeIfPresent(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 563 */       synchronized (this.sync) {
/* 564 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer compute(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 570 */       synchronized (this.sync) {
/* 571 */         return this.map.compute(key, remappingFunction);
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
/*     */     public Integer merge(K key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/* 583 */       synchronized (this.sync) {
/* 584 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <K> Object2IntMap<K> synchronize(Object2IntMap<K> m) {
/* 598 */     return new SynchronizedMap<>(m);
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
/*     */   public static <K> Object2IntMap<K> synchronize(Object2IntMap<K> m, Object sync) {
/* 612 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<K>
/*     */     extends Object2IntFunctions.UnmodifiableFunction<K>
/*     */     implements Object2IntMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2IntMap<K> map;
/*     */     protected transient ObjectSet<Object2IntMap.Entry<K>> entries;
/*     */     protected transient ObjectSet<K> keys;
/*     */     protected transient IntCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Object2IntMap<K> m) {
/* 625 */       super(m);
/* 626 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(int v) {
/* 630 */       return this.map.containsValue(v);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 640 */       return this.map.containsValue(ov);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Integer> m) {
/* 644 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
/* 648 */       if (this.entries == null)
/* 649 */         this.entries = ObjectSets.unmodifiable(this.map.object2IntEntrySet()); 
/* 650 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<K, Integer>> entrySet() {
/* 661 */       return (ObjectSet)object2IntEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 665 */       if (this.keys == null)
/* 666 */         this.keys = ObjectSets.unmodifiable(this.map.keySet()); 
/* 667 */       return this.keys;
/*     */     }
/*     */     
/*     */     public IntCollection values() {
/* 671 */       if (this.values == null)
/* 672 */         return IntCollections.unmodifiable(this.map.values()); 
/* 673 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 677 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 681 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 685 */       if (o == this)
/* 686 */         return true; 
/* 687 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getOrDefault(Object key, int defaultValue) {
/* 692 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Integer> action) {
/* 696 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Integer, ? extends Integer> function) {
/* 701 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int putIfAbsent(K key, int value) {
/* 705 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, int value) {
/* 709 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int replace(K key, int value) {
/* 713 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(K key, int oldValue, int newValue) {
/* 717 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int computeIntIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
/* 721 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int computeIntIfAbsentPartial(K key, Object2IntFunction<? super K> mappingFunction) {
/* 725 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIntIfPresent(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 730 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeInt(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 735 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int mergeInt(K key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/* 740 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer getOrDefault(Object key, Integer defaultValue) {
/* 750 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object key, Object value) {
/* 760 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer replace(K key, Integer value) {
/* 770 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(K key, Integer oldValue, Integer newValue) {
/* 780 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer putIfAbsent(K key, Integer value) {
/* 790 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer computeIfAbsent(K key, Function<? super K, ? extends Integer> mappingFunction) {
/* 795 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer computeIfPresent(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 800 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer compute(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 805 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer merge(K key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/* 816 */       throw new UnsupportedOperationException();
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
/*     */   public static <K> Object2IntMap<K> unmodifiable(Object2IntMap<K> m) {
/* 829 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2IntMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */