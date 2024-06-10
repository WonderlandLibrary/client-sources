/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollections;
/*     */ import it.unimi.dsi.fastutil.floats.FloatSets;
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
/*     */ import java.util.function.ToDoubleFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Reference2FloatMaps
/*     */ {
/*     */   public static <K> ObjectIterator<Reference2FloatMap.Entry<K>> fastIterator(Reference2FloatMap<K> map) {
/*  49 */     ObjectSet<Reference2FloatMap.Entry<K>> entries = map.reference2FloatEntrySet();
/*  50 */     return (entries instanceof Reference2FloatMap.FastEntrySet) ? (
/*  51 */       (Reference2FloatMap.FastEntrySet)entries).fastIterator() : 
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
/*     */   public static <K> void fastForEach(Reference2FloatMap<K> map, Consumer<? super Reference2FloatMap.Entry<K>> consumer) {
/*  71 */     ObjectSet<Reference2FloatMap.Entry<K>> entries = map.reference2FloatEntrySet();
/*  72 */     if (entries instanceof Reference2FloatMap.FastEntrySet) {
/*  73 */       ((Reference2FloatMap.FastEntrySet)entries).fastForEach(consumer);
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
/*     */   public static <K> ObjectIterable<Reference2FloatMap.Entry<K>> fastIterable(Reference2FloatMap<K> map) {
/*  91 */     final ObjectSet<Reference2FloatMap.Entry<K>> entries = map.reference2FloatEntrySet();
/*  92 */     return (entries instanceof Reference2FloatMap.FastEntrySet) ? (ObjectIterable)new ObjectIterable<Reference2FloatMap.Entry<Reference2FloatMap.Entry<K>>>() {
/*     */         public ObjectIterator<Reference2FloatMap.Entry<K>> iterator() {
/*  94 */           return ((Reference2FloatMap.FastEntrySet<K>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Reference2FloatMap.Entry<K>> consumer) {
/*  97 */           ((Reference2FloatMap.FastEntrySet<K>)entries).fastForEach(consumer);
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
/*     */     extends Reference2FloatFunctions.EmptyFunction<K>
/*     */     implements Reference2FloatMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(float v) {
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
/*     */     public void putAll(Map<? extends K, ? extends Float> m) {
/* 132 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Reference2FloatMap.Entry<K>> reference2FloatEntrySet() {
/* 137 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 142 */       return ReferenceSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatCollection values() {
/* 147 */       return (FloatCollection)FloatSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 151 */       return Reference2FloatMaps.EMPTY_MAP;
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
/*     */   public static <K> Reference2FloatMap<K> emptyMap() {
/* 187 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends Reference2FloatFunctions.Singleton<K>
/*     */     implements Reference2FloatMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Reference2FloatMap.Entry<K>> entries;
/*     */     
/*     */     protected transient ReferenceSet<K> keys;
/*     */     
/*     */     protected transient FloatCollection values;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, float value) {
/* 206 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(float v) {
/* 210 */       return (Float.floatToIntBits(this.value) == Float.floatToIntBits(v));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 220 */       return (Float.floatToIntBits(((Float)ov).floatValue()) == Float.floatToIntBits(this.value));
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Float> m) {
/* 224 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Reference2FloatMap.Entry<K>> reference2FloatEntrySet() {
/* 228 */       if (this.entries == null)
/* 229 */         this.entries = ObjectSets.singleton(new AbstractReference2FloatMap.BasicEntry<>(this.key, this.value)); 
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
/*     */     public ObjectSet<Map.Entry<K, Float>> entrySet() {
/* 241 */       return (ObjectSet)reference2FloatEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 245 */       if (this.keys == null)
/* 246 */         this.keys = ReferenceSets.singleton(this.key); 
/* 247 */       return this.keys;
/*     */     }
/*     */     
/*     */     public FloatCollection values() {
/* 251 */       if (this.values == null)
/* 252 */         this.values = (FloatCollection)FloatSets.singleton(this.value); 
/* 253 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 257 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 261 */       return System.identityHashCode(this.key) ^ HashCommon.float2int(this.value);
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
/*     */   public static <K> Reference2FloatMap<K> singleton(K key, float value) {
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
/*     */   public static <K> Reference2FloatMap<K> singleton(K key, Float value) {
/* 313 */     return new Singleton<>(key, value.floatValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<K>
/*     */     extends Reference2FloatFunctions.SynchronizedFunction<K>
/*     */     implements Reference2FloatMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2FloatMap<K> map;
/*     */     protected transient ObjectSet<Reference2FloatMap.Entry<K>> entries;
/*     */     protected transient ReferenceSet<K> keys;
/*     */     protected transient FloatCollection values;
/*     */     
/*     */     protected SynchronizedMap(Reference2FloatMap<K> m, Object sync) {
/* 326 */       super(m, sync);
/* 327 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Reference2FloatMap<K> m) {
/* 330 */       super(m);
/* 331 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(float v) {
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
/*     */     public void putAll(Map<? extends K, ? extends Float> m) {
/* 353 */       synchronized (this.sync) {
/* 354 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Reference2FloatMap.Entry<K>> reference2FloatEntrySet() {
/* 359 */       synchronized (this.sync) {
/* 360 */         if (this.entries == null)
/* 361 */           this.entries = ObjectSets.synchronize(this.map.reference2FloatEntrySet(), this.sync); 
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
/*     */     public ObjectSet<Map.Entry<K, Float>> entrySet() {
/* 374 */       return (ObjectSet)reference2FloatEntrySet();
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
/*     */     public FloatCollection values() {
/* 386 */       synchronized (this.sync) {
/* 387 */         if (this.values == null)
/* 388 */           return FloatCollections.synchronize(this.map.values(), this.sync); 
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
/*     */     public float getOrDefault(Object key, float defaultValue) {
/* 420 */       synchronized (this.sync) {
/* 421 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Float> action) {
/* 426 */       synchronized (this.sync) {
/* 427 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Float, ? extends Float> function) {
/* 433 */       synchronized (this.sync) {
/* 434 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public float putIfAbsent(K key, float value) {
/* 439 */       synchronized (this.sync) {
/* 440 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, float value) {
/* 445 */       synchronized (this.sync) {
/* 446 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public float replace(K key, float value) {
/* 451 */       synchronized (this.sync) {
/* 452 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(K key, float oldValue, float newValue) {
/* 457 */       synchronized (this.sync) {
/* 458 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeFloatIfAbsent(K key, ToDoubleFunction<? super K> mappingFunction) {
/* 464 */       synchronized (this.sync) {
/* 465 */         return this.map.computeFloatIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeFloatIfAbsentPartial(K key, Reference2FloatFunction<? super K> mappingFunction) {
/* 471 */       synchronized (this.sync) {
/* 472 */         return this.map.computeFloatIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeFloatIfPresent(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/* 478 */       synchronized (this.sync) {
/* 479 */         return this.map.computeFloatIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeFloat(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/* 485 */       synchronized (this.sync) {
/* 486 */         return this.map.computeFloat(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float mergeFloat(K key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/* 492 */       synchronized (this.sync) {
/* 493 */         return this.map.mergeFloat(key, value, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float getOrDefault(Object key, Float defaultValue) {
/* 504 */       synchronized (this.sync) {
/* 505 */         return this.map.getOrDefault(key, defaultValue);
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
/* 516 */       synchronized (this.sync) {
/* 517 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float replace(K key, Float value) {
/* 528 */       synchronized (this.sync) {
/* 529 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(K key, Float oldValue, Float newValue) {
/* 540 */       synchronized (this.sync) {
/* 541 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float putIfAbsent(K key, Float value) {
/* 552 */       synchronized (this.sync) {
/* 553 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Float computeIfAbsent(K key, Function<? super K, ? extends Float> mappingFunction) {
/* 559 */       synchronized (this.sync) {
/* 560 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Float computeIfPresent(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/* 566 */       synchronized (this.sync) {
/* 567 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Float compute(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/* 573 */       synchronized (this.sync) {
/* 574 */         return this.map.compute(key, remappingFunction);
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
/*     */     public Float merge(K key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/* 586 */       synchronized (this.sync) {
/* 587 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <K> Reference2FloatMap<K> synchronize(Reference2FloatMap<K> m) {
/* 601 */     return new SynchronizedMap<>(m);
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
/*     */   public static <K> Reference2FloatMap<K> synchronize(Reference2FloatMap<K> m, Object sync) {
/* 615 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<K>
/*     */     extends Reference2FloatFunctions.UnmodifiableFunction<K>
/*     */     implements Reference2FloatMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2FloatMap<K> map;
/*     */     protected transient ObjectSet<Reference2FloatMap.Entry<K>> entries;
/*     */     protected transient ReferenceSet<K> keys;
/*     */     protected transient FloatCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Reference2FloatMap<K> m) {
/* 628 */       super(m);
/* 629 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(float v) {
/* 633 */       return this.map.containsValue(v);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 643 */       return this.map.containsValue(ov);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Float> m) {
/* 647 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Reference2FloatMap.Entry<K>> reference2FloatEntrySet() {
/* 651 */       if (this.entries == null)
/* 652 */         this.entries = ObjectSets.unmodifiable(this.map.reference2FloatEntrySet()); 
/* 653 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<K, Float>> entrySet() {
/* 664 */       return (ObjectSet)reference2FloatEntrySet();
/*     */     }
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 668 */       if (this.keys == null)
/* 669 */         this.keys = ReferenceSets.unmodifiable(this.map.keySet()); 
/* 670 */       return this.keys;
/*     */     }
/*     */     
/*     */     public FloatCollection values() {
/* 674 */       if (this.values == null)
/* 675 */         return FloatCollections.unmodifiable(this.map.values()); 
/* 676 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 680 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 684 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 688 */       if (o == this)
/* 689 */         return true; 
/* 690 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public float getOrDefault(Object key, float defaultValue) {
/* 695 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Float> action) {
/* 699 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Float, ? extends Float> function) {
/* 704 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public float putIfAbsent(K key, float value) {
/* 708 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, float value) {
/* 712 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public float replace(K key, float value) {
/* 716 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(K key, float oldValue, float newValue) {
/* 720 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeFloatIfAbsent(K key, ToDoubleFunction<? super K> mappingFunction) {
/* 725 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeFloatIfAbsentPartial(K key, Reference2FloatFunction<? super K> mappingFunction) {
/* 730 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeFloatIfPresent(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/* 735 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeFloat(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/* 740 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float mergeFloat(K key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/* 745 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float getOrDefault(Object key, Float defaultValue) {
/* 755 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object key, Object value) {
/* 765 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float replace(K key, Float value) {
/* 775 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(K key, Float oldValue, Float newValue) {
/* 785 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float putIfAbsent(K key, Float value) {
/* 795 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Float computeIfAbsent(K key, Function<? super K, ? extends Float> mappingFunction) {
/* 800 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Float computeIfPresent(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/* 805 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Float compute(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/* 810 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float merge(K key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/* 821 */       throw new UnsupportedOperationException();
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
/*     */   public static <K> Reference2FloatMap<K> unmodifiable(Reference2FloatMap<K> m) {
/* 834 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2FloatMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */