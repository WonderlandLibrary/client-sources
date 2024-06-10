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
/*     */ public final class Reference2IntMaps
/*     */ {
/*     */   public static <K> ObjectIterator<Reference2IntMap.Entry<K>> fastIterator(Reference2IntMap<K> map) {
/*  49 */     ObjectSet<Reference2IntMap.Entry<K>> entries = map.reference2IntEntrySet();
/*  50 */     return (entries instanceof Reference2IntMap.FastEntrySet) ? (
/*  51 */       (Reference2IntMap.FastEntrySet)entries).fastIterator() : 
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
/*     */   public static <K> void fastForEach(Reference2IntMap<K> map, Consumer<? super Reference2IntMap.Entry<K>> consumer) {
/*  71 */     ObjectSet<Reference2IntMap.Entry<K>> entries = map.reference2IntEntrySet();
/*  72 */     if (entries instanceof Reference2IntMap.FastEntrySet) {
/*  73 */       ((Reference2IntMap.FastEntrySet)entries).fastForEach(consumer);
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
/*     */   public static <K> ObjectIterable<Reference2IntMap.Entry<K>> fastIterable(Reference2IntMap<K> map) {
/*  91 */     final ObjectSet<Reference2IntMap.Entry<K>> entries = map.reference2IntEntrySet();
/*  92 */     return (entries instanceof Reference2IntMap.FastEntrySet) ? (ObjectIterable)new ObjectIterable<Reference2IntMap.Entry<Reference2IntMap.Entry<K>>>() {
/*     */         public ObjectIterator<Reference2IntMap.Entry<K>> iterator() {
/*  94 */           return ((Reference2IntMap.FastEntrySet<K>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Reference2IntMap.Entry<K>> consumer) {
/*  97 */           ((Reference2IntMap.FastEntrySet<K>)entries).fastForEach(consumer);
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
/*     */     extends Reference2IntFunctions.EmptyFunction<K>
/*     */     implements Reference2IntMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(int v) {
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
/*     */     public void putAll(Map<? extends K, ? extends Integer> m) {
/* 132 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Reference2IntMap.Entry<K>> reference2IntEntrySet() {
/* 137 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 142 */       return ReferenceSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntCollection values() {
/* 147 */       return (IntCollection)IntSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 151 */       return Reference2IntMaps.EMPTY_MAP;
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
/*     */   public static <K> Reference2IntMap<K> emptyMap() {
/* 187 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends Reference2IntFunctions.Singleton<K>
/*     */     implements Reference2IntMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Reference2IntMap.Entry<K>> entries;
/*     */     
/*     */     protected transient ReferenceSet<K> keys;
/*     */     
/*     */     protected transient IntCollection values;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, int value) {
/* 206 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(int v) {
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
/* 220 */       return (((Integer)ov).intValue() == this.value);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Integer> m) {
/* 224 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Reference2IntMap.Entry<K>> reference2IntEntrySet() {
/* 228 */       if (this.entries == null)
/* 229 */         this.entries = ObjectSets.singleton(new AbstractReference2IntMap.BasicEntry<>(this.key, this.value)); 
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
/*     */     public ObjectSet<Map.Entry<K, Integer>> entrySet() {
/* 241 */       return (ObjectSet)reference2IntEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 245 */       if (this.keys == null)
/* 246 */         this.keys = ReferenceSets.singleton(this.key); 
/* 247 */       return this.keys;
/*     */     }
/*     */     
/*     */     public IntCollection values() {
/* 251 */       if (this.values == null)
/* 252 */         this.values = (IntCollection)IntSets.singleton(this.value); 
/* 253 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 257 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 261 */       return System.identityHashCode(this.key) ^ this.value;
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
/*     */   public static <K> Reference2IntMap<K> singleton(K key, int value) {
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
/*     */   public static <K> Reference2IntMap<K> singleton(K key, Integer value) {
/* 313 */     return new Singleton<>(key, value.intValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<K>
/*     */     extends Reference2IntFunctions.SynchronizedFunction<K>
/*     */     implements Reference2IntMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2IntMap<K> map;
/*     */     protected transient ObjectSet<Reference2IntMap.Entry<K>> entries;
/*     */     protected transient ReferenceSet<K> keys;
/*     */     protected transient IntCollection values;
/*     */     
/*     */     protected SynchronizedMap(Reference2IntMap<K> m, Object sync) {
/* 326 */       super(m, sync);
/* 327 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Reference2IntMap<K> m) {
/* 330 */       super(m);
/* 331 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(int v) {
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
/*     */     public void putAll(Map<? extends K, ? extends Integer> m) {
/* 353 */       synchronized (this.sync) {
/* 354 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Reference2IntMap.Entry<K>> reference2IntEntrySet() {
/* 359 */       synchronized (this.sync) {
/* 360 */         if (this.entries == null)
/* 361 */           this.entries = ObjectSets.synchronize(this.map.reference2IntEntrySet(), this.sync); 
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
/*     */     public ObjectSet<Map.Entry<K, Integer>> entrySet() {
/* 374 */       return (ObjectSet)reference2IntEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 378 */       synchronized (this.sync) {
/* 379 */         if (this.keys == null)
/* 380 */           this.keys = ReferenceSets.synchronize(this.map.keySet(), this.sync); 
/* 381 */         return this.keys;
/*     */       } 
/*     */     }
/*     */     
/*     */     public IntCollection values() {
/* 386 */       synchronized (this.sync) {
/* 387 */         if (this.values == null)
/* 388 */           return IntCollections.synchronize(this.map.values(), this.sync); 
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
/*     */     public int getOrDefault(Object key, int defaultValue) {
/* 420 */       synchronized (this.sync) {
/* 421 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Integer> action) {
/* 426 */       synchronized (this.sync) {
/* 427 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Integer, ? extends Integer> function) {
/* 433 */       synchronized (this.sync) {
/* 434 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int putIfAbsent(K key, int value) {
/* 439 */       synchronized (this.sync) {
/* 440 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, int value) {
/* 445 */       synchronized (this.sync) {
/* 446 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int replace(K key, int value) {
/* 451 */       synchronized (this.sync) {
/* 452 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(K key, int oldValue, int newValue) {
/* 457 */       synchronized (this.sync) {
/* 458 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int computeIntIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
/* 463 */       synchronized (this.sync) {
/* 464 */         return this.map.computeIntIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int computeIntIfAbsentPartial(K key, Reference2IntFunction<? super K> mappingFunction) {
/* 469 */       synchronized (this.sync) {
/* 470 */         return this.map.computeIntIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIntIfPresent(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 476 */       synchronized (this.sync) {
/* 477 */         return this.map.computeIntIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeInt(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 483 */       synchronized (this.sync) {
/* 484 */         return this.map.computeInt(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int mergeInt(K key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/* 490 */       synchronized (this.sync) {
/* 491 */         return this.map.mergeInt(key, value, remappingFunction);
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
/*     */     public Integer replace(K key, Integer value) {
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
/*     */     public boolean replace(K key, Integer oldValue, Integer newValue) {
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
/*     */     public Integer putIfAbsent(K key, Integer value) {
/* 550 */       synchronized (this.sync) {
/* 551 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer computeIfAbsent(K key, Function<? super K, ? extends Integer> mappingFunction) {
/* 557 */       synchronized (this.sync) {
/* 558 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer computeIfPresent(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 564 */       synchronized (this.sync) {
/* 565 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer compute(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
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
/*     */     public Integer merge(K key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
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
/*     */   public static <K> Reference2IntMap<K> synchronize(Reference2IntMap<K> m) {
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
/*     */   public static <K> Reference2IntMap<K> synchronize(Reference2IntMap<K> m, Object sync) {
/* 613 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<K>
/*     */     extends Reference2IntFunctions.UnmodifiableFunction<K>
/*     */     implements Reference2IntMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2IntMap<K> map;
/*     */     protected transient ObjectSet<Reference2IntMap.Entry<K>> entries;
/*     */     protected transient ReferenceSet<K> keys;
/*     */     protected transient IntCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Reference2IntMap<K> m) {
/* 626 */       super(m);
/* 627 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(int v) {
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
/*     */     public void putAll(Map<? extends K, ? extends Integer> m) {
/* 645 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Reference2IntMap.Entry<K>> reference2IntEntrySet() {
/* 649 */       if (this.entries == null)
/* 650 */         this.entries = ObjectSets.unmodifiable(this.map.reference2IntEntrySet()); 
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
/*     */     public ObjectSet<Map.Entry<K, Integer>> entrySet() {
/* 662 */       return (ObjectSet)reference2IntEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 666 */       if (this.keys == null)
/* 667 */         this.keys = ReferenceSets.unmodifiable(this.map.keySet()); 
/* 668 */       return this.keys;
/*     */     }
/*     */     
/*     */     public IntCollection values() {
/* 672 */       if (this.values == null)
/* 673 */         return IntCollections.unmodifiable(this.map.values()); 
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
/*     */     public int getOrDefault(Object key, int defaultValue) {
/* 693 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Integer> action) {
/* 697 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Integer, ? extends Integer> function) {
/* 702 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int putIfAbsent(K key, int value) {
/* 706 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, int value) {
/* 710 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int replace(K key, int value) {
/* 714 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(K key, int oldValue, int newValue) {
/* 718 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int computeIntIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
/* 722 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int computeIntIfAbsentPartial(K key, Reference2IntFunction<? super K> mappingFunction) {
/* 726 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIntIfPresent(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 731 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeInt(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 736 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int mergeInt(K key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/* 741 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer getOrDefault(Object key, Integer defaultValue) {
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
/*     */     public Integer replace(K key, Integer value) {
/* 771 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(K key, Integer oldValue, Integer newValue) {
/* 781 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer putIfAbsent(K key, Integer value) {
/* 791 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer computeIfAbsent(K key, Function<? super K, ? extends Integer> mappingFunction) {
/* 796 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer computeIfPresent(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 801 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer compute(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
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
/*     */     public Integer merge(K key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
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
/*     */   public static <K> Reference2IntMap<K> unmodifiable(Reference2IntMap<K> m) {
/* 830 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2IntMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */