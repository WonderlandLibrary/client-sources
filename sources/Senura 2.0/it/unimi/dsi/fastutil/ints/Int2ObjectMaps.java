/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ public final class Int2ObjectMaps
/*     */ {
/*     */   public static <V> ObjectIterator<Int2ObjectMap.Entry<V>> fastIterator(Int2ObjectMap<V> map) {
/*  48 */     ObjectSet<Int2ObjectMap.Entry<V>> entries = map.int2ObjectEntrySet();
/*  49 */     return (entries instanceof Int2ObjectMap.FastEntrySet) ? (
/*  50 */       (Int2ObjectMap.FastEntrySet)entries).fastIterator() : 
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
/*     */   public static <V> void fastForEach(Int2ObjectMap<V> map, Consumer<? super Int2ObjectMap.Entry<V>> consumer) {
/*  69 */     ObjectSet<Int2ObjectMap.Entry<V>> entries = map.int2ObjectEntrySet();
/*  70 */     if (entries instanceof Int2ObjectMap.FastEntrySet) {
/*  71 */       ((Int2ObjectMap.FastEntrySet)entries).fastForEach(consumer);
/*     */     } else {
/*  73 */       entries.forEach(consumer);
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
/*     */   public static <V> ObjectIterable<Int2ObjectMap.Entry<V>> fastIterable(Int2ObjectMap<V> map) {
/*  89 */     final ObjectSet<Int2ObjectMap.Entry<V>> entries = map.int2ObjectEntrySet();
/*  90 */     return (entries instanceof Int2ObjectMap.FastEntrySet) ? new ObjectIterable<Int2ObjectMap.Entry<V>>() {
/*     */         public ObjectIterator<Int2ObjectMap.Entry<V>> iterator() {
/*  92 */           return ((Int2ObjectMap.FastEntrySet<V>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Int2ObjectMap.Entry<V>> consumer) {
/*  95 */           ((Int2ObjectMap.FastEntrySet<V>)entries).fastForEach(consumer);
/*     */         }
/*  97 */       } : (ObjectIterable<Int2ObjectMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<V>
/*     */     extends Int2ObjectFunctions.EmptyFunction<V>
/*     */     implements Int2ObjectMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 116 */       return false;
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Integer, ? extends V> m) {
/* 120 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
/* 125 */       return (ObjectSet<Int2ObjectMap.Entry<V>>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSet keySet() {
/* 130 */       return IntSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 135 */       return (ObjectCollection<V>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 139 */       return Int2ObjectMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 143 */       return true;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 147 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 151 */       if (!(o instanceof Map))
/* 152 */         return false; 
/* 153 */       return ((Map)o).isEmpty();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 157 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   public static final EmptyMap EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Int2ObjectMap<V> emptyMap() {
/* 175 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Int2ObjectFunctions.Singleton<V>
/*     */     implements Int2ObjectMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Int2ObjectMap.Entry<V>> entries;
/*     */     
/*     */     protected transient IntSet keys;
/*     */     
/*     */     protected transient ObjectCollection<V> values;
/*     */ 
/*     */     
/*     */     protected Singleton(int key, V value) {
/* 194 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 198 */       return Objects.equals(this.value, v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Integer, ? extends V> m) {
/* 202 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
/* 206 */       if (this.entries == null)
/* 207 */         this.entries = ObjectSets.singleton(new AbstractInt2ObjectMap.BasicEntry<>(this.key, this.value)); 
/* 208 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<Integer, V>> entrySet() {
/* 219 */       return (ObjectSet)int2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public IntSet keySet() {
/* 223 */       if (this.keys == null)
/* 224 */         this.keys = IntSets.singleton(this.key); 
/* 225 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 229 */       if (this.values == null)
/* 230 */         this.values = (ObjectCollection<V>)ObjectSets.singleton(this.value); 
/* 231 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 235 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 239 */       return this.key ^ ((this.value == null) ? 0 : this.value.hashCode());
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 243 */       if (o == this)
/* 244 */         return true; 
/* 245 */       if (!(o instanceof Map))
/* 246 */         return false; 
/* 247 */       Map<?, ?> m = (Map<?, ?>)o;
/* 248 */       if (m.size() != 1)
/* 249 */         return false; 
/* 250 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 254 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static <V> Int2ObjectMap<V> singleton(int key, V value) {
/* 273 */     return new Singleton<>(key, value);
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
/*     */   public static <V> Int2ObjectMap<V> singleton(Integer key, V value) {
/* 291 */     return new Singleton<>(key.intValue(), value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<V>
/*     */     extends Int2ObjectFunctions.SynchronizedFunction<V>
/*     */     implements Int2ObjectMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2ObjectMap<V> map;
/*     */     protected transient ObjectSet<Int2ObjectMap.Entry<V>> entries;
/*     */     protected transient IntSet keys;
/*     */     protected transient ObjectCollection<V> values;
/*     */     
/*     */     protected SynchronizedMap(Int2ObjectMap<V> m, Object sync) {
/* 304 */       super(m, sync);
/* 305 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Int2ObjectMap<V> m) {
/* 308 */       super(m);
/* 309 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 313 */       synchronized (this.sync) {
/* 314 */         return this.map.containsValue(v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Integer, ? extends V> m) {
/* 319 */       synchronized (this.sync) {
/* 320 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
/* 325 */       synchronized (this.sync) {
/* 326 */         if (this.entries == null)
/* 327 */           this.entries = ObjectSets.synchronize(this.map.int2ObjectEntrySet(), this.sync); 
/* 328 */         return this.entries;
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
/*     */     public ObjectSet<Map.Entry<Integer, V>> entrySet() {
/* 340 */       return (ObjectSet)int2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public IntSet keySet() {
/* 344 */       synchronized (this.sync) {
/* 345 */         if (this.keys == null)
/* 346 */           this.keys = IntSets.synchronize(this.map.keySet(), this.sync); 
/* 347 */         return this.keys;
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 352 */       synchronized (this.sync) {
/* 353 */         if (this.values == null)
/* 354 */           return ObjectCollections.synchronize(this.map.values(), this.sync); 
/* 355 */         return this.values;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 360 */       synchronized (this.sync) {
/* 361 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 366 */       synchronized (this.sync) {
/* 367 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 372 */       if (o == this)
/* 373 */         return true; 
/* 374 */       synchronized (this.sync) {
/* 375 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 379 */       synchronized (this.sync) {
/* 380 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(int key, V defaultValue) {
/* 386 */       synchronized (this.sync) {
/* 387 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Integer, ? super V> action) {
/* 392 */       synchronized (this.sync) {
/* 393 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super Integer, ? super V, ? extends V> function) {
/* 398 */       synchronized (this.sync) {
/* 399 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V putIfAbsent(int key, V value) {
/* 404 */       synchronized (this.sync) {
/* 405 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(int key, Object value) {
/* 410 */       synchronized (this.sync) {
/* 411 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V replace(int key, V value) {
/* 416 */       synchronized (this.sync) {
/* 417 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(int key, V oldValue, V newValue) {
/* 422 */       synchronized (this.sync) {
/* 423 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V computeIfAbsent(int key, IntFunction<? extends V> mappingFunction) {
/* 428 */       synchronized (this.sync) {
/* 429 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V computeIfAbsentPartial(int key, Int2ObjectFunction<? extends V> mappingFunction) {
/* 434 */       synchronized (this.sync) {
/* 435 */         return this.map.computeIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 441 */       synchronized (this.sync) {
/* 442 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 448 */       synchronized (this.sync) {
/* 449 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(int key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 455 */       synchronized (this.sync) {
/* 456 */         return this.map.merge(key, value, remappingFunction);
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
/* 467 */       synchronized (this.sync) {
/* 468 */         return this.map.getOrDefault(key, defaultValue);
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
/* 479 */       synchronized (this.sync) {
/* 480 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V replace(Integer key, V value) {
/* 491 */       synchronized (this.sync) {
/* 492 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Integer key, V oldValue, V newValue) {
/* 503 */       synchronized (this.sync) {
/* 504 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V putIfAbsent(Integer key, V value) {
/* 515 */       synchronized (this.sync) {
/* 516 */         return this.map.putIfAbsent(key, value);
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
/*     */     public V computeIfAbsent(Integer key, Function<? super Integer, ? extends V> mappingFunction) {
/* 528 */       synchronized (this.sync) {
/* 529 */         return this.map.computeIfAbsent(key, mappingFunction);
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
/*     */     public V computeIfPresent(Integer key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 541 */       synchronized (this.sync) {
/* 542 */         return this.map.computeIfPresent(key, remappingFunction);
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
/*     */     public V compute(Integer key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 554 */       synchronized (this.sync) {
/* 555 */         return this.map.compute(key, remappingFunction);
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
/*     */     public V merge(Integer key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 567 */       synchronized (this.sync) {
/* 568 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <V> Int2ObjectMap<V> synchronize(Int2ObjectMap<V> m) {
/* 582 */     return new SynchronizedMap<>(m);
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
/*     */   public static <V> Int2ObjectMap<V> synchronize(Int2ObjectMap<V> m, Object sync) {
/* 596 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<V>
/*     */     extends Int2ObjectFunctions.UnmodifiableFunction<V>
/*     */     implements Int2ObjectMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2ObjectMap<V> map;
/*     */     protected transient ObjectSet<Int2ObjectMap.Entry<V>> entries;
/*     */     protected transient IntSet keys;
/*     */     protected transient ObjectCollection<V> values;
/*     */     
/*     */     protected UnmodifiableMap(Int2ObjectMap<V> m) {
/* 609 */       super(m);
/* 610 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 614 */       return this.map.containsValue(v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Integer, ? extends V> m) {
/* 618 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
/* 622 */       if (this.entries == null)
/* 623 */         this.entries = ObjectSets.unmodifiable(this.map.int2ObjectEntrySet()); 
/* 624 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<Integer, V>> entrySet() {
/* 635 */       return (ObjectSet)int2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public IntSet keySet() {
/* 639 */       if (this.keys == null)
/* 640 */         this.keys = IntSets.unmodifiable(this.map.keySet()); 
/* 641 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 645 */       if (this.values == null)
/* 646 */         return ObjectCollections.unmodifiable(this.map.values()); 
/* 647 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 651 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 655 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 659 */       if (o == this)
/* 660 */         return true; 
/* 661 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(int key, V defaultValue) {
/* 666 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Integer, ? super V> action) {
/* 670 */       this.map.forEach(action);
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super Integer, ? super V, ? extends V> function) {
/* 674 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V putIfAbsent(int key, V value) {
/* 678 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(int key, Object value) {
/* 682 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V replace(int key, V value) {
/* 686 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(int key, V oldValue, V newValue) {
/* 690 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V computeIfAbsent(int key, IntFunction<? extends V> mappingFunction) {
/* 694 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V computeIfAbsentPartial(int key, Int2ObjectFunction<? extends V> mappingFunction) {
/* 698 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 703 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 708 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(int key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 713 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 723 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object key, Object value) {
/* 733 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V replace(Integer key, V value) {
/* 743 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Integer key, V oldValue, V newValue) {
/* 753 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V putIfAbsent(Integer key, V value) {
/* 763 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V computeIfAbsent(Integer key, Function<? super Integer, ? extends V> mappingFunction) {
/* 774 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V computeIfPresent(Integer key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 785 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V compute(Integer key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 796 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V merge(Integer key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 807 */       throw new UnsupportedOperationException();
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
/*     */   public static <V> Int2ObjectMap<V> unmodifiable(Int2ObjectMap<V> m) {
/* 820 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2ObjectMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */