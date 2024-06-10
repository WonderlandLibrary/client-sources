/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanCollections;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanSets;
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
/*     */ import java.util.function.Predicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Reference2BooleanMaps
/*     */ {
/*     */   public static <K> ObjectIterator<Reference2BooleanMap.Entry<K>> fastIterator(Reference2BooleanMap<K> map) {
/*  49 */     ObjectSet<Reference2BooleanMap.Entry<K>> entries = map.reference2BooleanEntrySet();
/*  50 */     return (entries instanceof Reference2BooleanMap.FastEntrySet) ? (
/*  51 */       (Reference2BooleanMap.FastEntrySet)entries).fastIterator() : 
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
/*     */   public static <K> void fastForEach(Reference2BooleanMap<K> map, Consumer<? super Reference2BooleanMap.Entry<K>> consumer) {
/*  71 */     ObjectSet<Reference2BooleanMap.Entry<K>> entries = map.reference2BooleanEntrySet();
/*  72 */     if (entries instanceof Reference2BooleanMap.FastEntrySet) {
/*  73 */       ((Reference2BooleanMap.FastEntrySet)entries).fastForEach(consumer);
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
/*     */   public static <K> ObjectIterable<Reference2BooleanMap.Entry<K>> fastIterable(Reference2BooleanMap<K> map) {
/*  91 */     final ObjectSet<Reference2BooleanMap.Entry<K>> entries = map.reference2BooleanEntrySet();
/*  92 */     return (entries instanceof Reference2BooleanMap.FastEntrySet) ? 
/*  93 */       (ObjectIterable)new ObjectIterable<Reference2BooleanMap.Entry<Reference2BooleanMap.Entry<K>>>() {
/*     */         public ObjectIterator<Reference2BooleanMap.Entry<K>> iterator() {
/*  95 */           return ((Reference2BooleanMap.FastEntrySet<K>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Reference2BooleanMap.Entry<K>> consumer) {
/*  98 */           ((Reference2BooleanMap.FastEntrySet<K>)entries).fastForEach(consumer);
/*     */         }
/* 101 */       } : entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<K>
/*     */     extends Reference2BooleanFunctions.EmptyFunction<K>
/*     */     implements Reference2BooleanMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(boolean v) {
/* 120 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 130 */       return false;
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Boolean> m) {
/* 134 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Reference2BooleanMap.Entry<K>> reference2BooleanEntrySet() {
/* 139 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 144 */       return ReferenceSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public BooleanCollection values() {
/* 149 */       return (BooleanCollection)BooleanSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 153 */       return Reference2BooleanMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 157 */       return true;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 161 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 165 */       if (!(o instanceof Map))
/* 166 */         return false; 
/* 167 */       return ((Map)o).isEmpty();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 171 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 178 */   public static final EmptyMap EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> Reference2BooleanMap<K> emptyMap() {
/* 189 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends Reference2BooleanFunctions.Singleton<K>
/*     */     implements Reference2BooleanMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Reference2BooleanMap.Entry<K>> entries;
/*     */     
/*     */     protected transient ReferenceSet<K> keys;
/*     */     
/*     */     protected transient BooleanCollection values;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, boolean value) {
/* 208 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(boolean v) {
/* 212 */       return (this.value == v);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 222 */       return (((Boolean)ov).booleanValue() == this.value);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Boolean> m) {
/* 226 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Reference2BooleanMap.Entry<K>> reference2BooleanEntrySet() {
/* 230 */       if (this.entries == null)
/* 231 */         this.entries = ObjectSets.singleton(new AbstractReference2BooleanMap.BasicEntry<>(this.key, this.value)); 
/* 232 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<K, Boolean>> entrySet() {
/* 243 */       return (ObjectSet)reference2BooleanEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 247 */       if (this.keys == null)
/* 248 */         this.keys = ReferenceSets.singleton(this.key); 
/* 249 */       return this.keys;
/*     */     }
/*     */     
/*     */     public BooleanCollection values() {
/* 253 */       if (this.values == null)
/* 254 */         this.values = (BooleanCollection)BooleanSets.singleton(this.value); 
/* 255 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 259 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 263 */       return System.identityHashCode(this.key) ^ (this.value ? 1231 : 1237);
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 267 */       if (o == this)
/* 268 */         return true; 
/* 269 */       if (!(o instanceof Map))
/* 270 */         return false; 
/* 271 */       Map<?, ?> m = (Map<?, ?>)o;
/* 272 */       if (m.size() != 1)
/* 273 */         return false; 
/* 274 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 278 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static <K> Reference2BooleanMap<K> singleton(K key, boolean value) {
/* 297 */     return new Singleton<>(key, value);
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
/*     */   public static <K> Reference2BooleanMap<K> singleton(K key, Boolean value) {
/* 315 */     return new Singleton<>(key, value.booleanValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<K>
/*     */     extends Reference2BooleanFunctions.SynchronizedFunction<K>
/*     */     implements Reference2BooleanMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2BooleanMap<K> map;
/*     */     protected transient ObjectSet<Reference2BooleanMap.Entry<K>> entries;
/*     */     protected transient ReferenceSet<K> keys;
/*     */     protected transient BooleanCollection values;
/*     */     
/*     */     protected SynchronizedMap(Reference2BooleanMap<K> m, Object sync) {
/* 328 */       super(m, sync);
/* 329 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Reference2BooleanMap<K> m) {
/* 332 */       super(m);
/* 333 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(boolean v) {
/* 337 */       synchronized (this.sync) {
/* 338 */         return this.map.containsValue(v);
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
/* 349 */       synchronized (this.sync) {
/* 350 */         return this.map.containsValue(ov);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Boolean> m) {
/* 355 */       synchronized (this.sync) {
/* 356 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Reference2BooleanMap.Entry<K>> reference2BooleanEntrySet() {
/* 361 */       synchronized (this.sync) {
/* 362 */         if (this.entries == null)
/* 363 */           this.entries = ObjectSets.synchronize(this.map.reference2BooleanEntrySet(), this.sync); 
/* 364 */         return this.entries;
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
/*     */     public ObjectSet<Map.Entry<K, Boolean>> entrySet() {
/* 376 */       return (ObjectSet)reference2BooleanEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 380 */       synchronized (this.sync) {
/* 381 */         if (this.keys == null)
/* 382 */           this.keys = ReferenceSets.synchronize(this.map.keySet(), this.sync); 
/* 383 */         return this.keys;
/*     */       } 
/*     */     }
/*     */     
/*     */     public BooleanCollection values() {
/* 388 */       synchronized (this.sync) {
/* 389 */         if (this.values == null)
/* 390 */           return BooleanCollections.synchronize(this.map.values(), this.sync); 
/* 391 */         return this.values;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 396 */       synchronized (this.sync) {
/* 397 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 402 */       synchronized (this.sync) {
/* 403 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 408 */       if (o == this)
/* 409 */         return true; 
/* 410 */       synchronized (this.sync) {
/* 411 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 415 */       synchronized (this.sync) {
/* 416 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getOrDefault(Object key, boolean defaultValue) {
/* 422 */       synchronized (this.sync) {
/* 423 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Boolean> action) {
/* 428 */       synchronized (this.sync) {
/* 429 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Boolean, ? extends Boolean> function) {
/* 435 */       synchronized (this.sync) {
/* 436 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean putIfAbsent(K key, boolean value) {
/* 441 */       synchronized (this.sync) {
/* 442 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, boolean value) {
/* 447 */       synchronized (this.sync) {
/* 448 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(K key, boolean value) {
/* 453 */       synchronized (this.sync) {
/* 454 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(K key, boolean oldValue, boolean newValue) {
/* 459 */       synchronized (this.sync) {
/* 460 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean computeBooleanIfAbsent(K key, Predicate<? super K> mappingFunction) {
/* 466 */       synchronized (this.sync) {
/* 467 */         return this.map.computeBooleanIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean computeBooleanIfAbsentPartial(K key, Reference2BooleanFunction<? super K> mappingFunction) {
/* 473 */       synchronized (this.sync) {
/* 474 */         return this.map.computeBooleanIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean computeBooleanIfPresent(K key, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 480 */       synchronized (this.sync) {
/* 481 */         return this.map.computeBooleanIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean computeBoolean(K key, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 487 */       synchronized (this.sync) {
/* 488 */         return this.map.computeBoolean(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mergeBoolean(K key, boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 494 */       synchronized (this.sync) {
/* 495 */         return this.map.mergeBoolean(key, value, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean getOrDefault(Object key, Boolean defaultValue) {
/* 506 */       synchronized (this.sync) {
/* 507 */         return this.map.getOrDefault(key, defaultValue);
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
/* 518 */       synchronized (this.sync) {
/* 519 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean replace(K key, Boolean value) {
/* 530 */       synchronized (this.sync) {
/* 531 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(K key, Boolean oldValue, Boolean newValue) {
/* 542 */       synchronized (this.sync) {
/* 543 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean putIfAbsent(K key, Boolean value) {
/* 554 */       synchronized (this.sync) {
/* 555 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Boolean computeIfAbsent(K key, Function<? super K, ? extends Boolean> mappingFunction) {
/* 561 */       synchronized (this.sync) {
/* 562 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Boolean computeIfPresent(K key, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 568 */       synchronized (this.sync) {
/* 569 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Boolean compute(K key, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 575 */       synchronized (this.sync) {
/* 576 */         return this.map.compute(key, remappingFunction);
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
/*     */     public Boolean merge(K key, Boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 588 */       synchronized (this.sync) {
/* 589 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <K> Reference2BooleanMap<K> synchronize(Reference2BooleanMap<K> m) {
/* 603 */     return new SynchronizedMap<>(m);
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
/*     */   public static <K> Reference2BooleanMap<K> synchronize(Reference2BooleanMap<K> m, Object sync) {
/* 617 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<K>
/*     */     extends Reference2BooleanFunctions.UnmodifiableFunction<K>
/*     */     implements Reference2BooleanMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2BooleanMap<K> map;
/*     */     protected transient ObjectSet<Reference2BooleanMap.Entry<K>> entries;
/*     */     protected transient ReferenceSet<K> keys;
/*     */     protected transient BooleanCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Reference2BooleanMap<K> m) {
/* 630 */       super(m);
/* 631 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(boolean v) {
/* 635 */       return this.map.containsValue(v);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 645 */       return this.map.containsValue(ov);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Boolean> m) {
/* 649 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Reference2BooleanMap.Entry<K>> reference2BooleanEntrySet() {
/* 653 */       if (this.entries == null)
/* 654 */         this.entries = ObjectSets.unmodifiable(this.map.reference2BooleanEntrySet()); 
/* 655 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<K, Boolean>> entrySet() {
/* 666 */       return (ObjectSet)reference2BooleanEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 670 */       if (this.keys == null)
/* 671 */         this.keys = ReferenceSets.unmodifiable(this.map.keySet()); 
/* 672 */       return this.keys;
/*     */     }
/*     */     
/*     */     public BooleanCollection values() {
/* 676 */       if (this.values == null)
/* 677 */         return BooleanCollections.unmodifiable(this.map.values()); 
/* 678 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 682 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 686 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 690 */       if (o == this)
/* 691 */         return true; 
/* 692 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getOrDefault(Object key, boolean defaultValue) {
/* 697 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Boolean> action) {
/* 701 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Boolean, ? extends Boolean> function) {
/* 706 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean putIfAbsent(K key, boolean value) {
/* 710 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, boolean value) {
/* 714 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(K key, boolean value) {
/* 718 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(K key, boolean oldValue, boolean newValue) {
/* 722 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean computeBooleanIfAbsent(K key, Predicate<? super K> mappingFunction) {
/* 727 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean computeBooleanIfAbsentPartial(K key, Reference2BooleanFunction<? super K> mappingFunction) {
/* 732 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean computeBooleanIfPresent(K key, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 737 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean computeBoolean(K key, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 742 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mergeBoolean(K key, boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 747 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean getOrDefault(Object key, Boolean defaultValue) {
/* 757 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object key, Object value) {
/* 767 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean replace(K key, Boolean value) {
/* 777 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(K key, Boolean oldValue, Boolean newValue) {
/* 787 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean putIfAbsent(K key, Boolean value) {
/* 797 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Boolean computeIfAbsent(K key, Function<? super K, ? extends Boolean> mappingFunction) {
/* 802 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Boolean computeIfPresent(K key, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 807 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Boolean compute(K key, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 812 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean merge(K key, Boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 823 */       throw new UnsupportedOperationException();
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
/*     */   public static <K> Reference2BooleanMap<K> unmodifiable(Reference2BooleanMap<K> m) {
/* 836 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2BooleanMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */