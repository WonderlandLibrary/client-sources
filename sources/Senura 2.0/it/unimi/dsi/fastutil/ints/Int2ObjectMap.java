/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Int2ObjectMap<V>
/*     */   extends Int2ObjectFunction<V>, Map<Integer, V>
/*     */ {
/*     */   public static interface FastEntrySet<V>
/*     */     extends ObjectSet<Entry<V>>
/*     */   {
/*     */     ObjectIterator<Int2ObjectMap.Entry<V>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Int2ObjectMap.Entry<V>> consumer) {
/*  80 */       forEach(consumer);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void clear() {
/* 103 */     throw new UnsupportedOperationException();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default ObjectSet<Map.Entry<Integer, V>> entrySet() {
/* 159 */     return (ObjectSet)int2ObjectEntrySet();
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
/*     */   @Deprecated
/*     */   default V put(Integer key, V value) {
/* 173 */     return super.put(key, value);
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
/*     */   @Deprecated
/*     */   default V get(Object key) {
/* 187 */     return super.get(key);
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
/*     */   @Deprecated
/*     */   default V remove(Object key) {
/* 201 */     return super.remove(key);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean containsKey(Object key) {
/* 247 */     return super.containsKey(key);
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
/*     */   default V getOrDefault(int key, V defaultValue) {
/*     */     V v;
/* 267 */     return ((v = get(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   
/*     */   default V putIfAbsent(int key, V value) {
/* 287 */     V v = get(key), drv = defaultReturnValue();
/* 288 */     if (v != drv || containsKey(key))
/* 289 */       return v; 
/* 290 */     put(key, value);
/* 291 */     return drv;
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
/*     */   default boolean remove(int key, Object value) {
/* 308 */     V curValue = get(key);
/* 309 */     if (!Objects.equals(curValue, value) || (curValue == defaultReturnValue() && !containsKey(key)))
/* 310 */       return false; 
/* 311 */     remove(key);
/* 312 */     return true;
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
/*     */   default boolean replace(int key, V oldValue, V newValue) {
/* 331 */     V curValue = get(key);
/* 332 */     if (!Objects.equals(curValue, oldValue) || (curValue == defaultReturnValue() && !containsKey(key)))
/* 333 */       return false; 
/* 334 */     put(key, newValue);
/* 335 */     return true;
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
/*     */   default V replace(int key, V value) {
/* 354 */     return containsKey(key) ? put(key, value) : defaultReturnValue();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default V computeIfAbsent(int key, IntFunction<? extends V> mappingFunction) {
/* 380 */     Objects.requireNonNull(mappingFunction);
/* 381 */     V v = get(key);
/* 382 */     if (v != defaultReturnValue() || containsKey(key))
/* 383 */       return v; 
/* 384 */     V newValue = mappingFunction.apply(key);
/* 385 */     put(key, newValue);
/* 386 */     return newValue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default V computeIfAbsentPartial(int key, Int2ObjectFunction<? extends V> mappingFunction) {
/* 415 */     Objects.requireNonNull(mappingFunction);
/* 416 */     V v = get(key), drv = defaultReturnValue();
/* 417 */     if (v != drv || containsKey(key))
/* 418 */       return v; 
/* 419 */     if (!mappingFunction.containsKey(key))
/* 420 */       return drv; 
/* 421 */     V newValue = mappingFunction.get(key);
/* 422 */     put(key, newValue);
/* 423 */     return newValue;
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
/*     */   default V computeIfPresent(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 442 */     Objects.requireNonNull(remappingFunction);
/* 443 */     V oldValue = get(key), drv = defaultReturnValue();
/* 444 */     if (oldValue == drv && !containsKey(key))
/* 445 */       return drv; 
/* 446 */     V newValue = remappingFunction.apply(Integer.valueOf(key), oldValue);
/* 447 */     if (newValue == null) {
/* 448 */       remove(key);
/* 449 */       return drv;
/*     */     } 
/* 451 */     put(key, newValue);
/* 452 */     return newValue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default V compute(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 477 */     Objects.requireNonNull(remappingFunction);
/* 478 */     V oldValue = get(key), drv = defaultReturnValue();
/* 479 */     boolean contained = (oldValue != drv || containsKey(key));
/* 480 */     V newValue = remappingFunction.apply(Integer.valueOf(key), contained ? oldValue : null);
/* 481 */     if (newValue == null) {
/* 482 */       if (contained)
/* 483 */         remove(key); 
/* 484 */       return drv;
/*     */     } 
/* 486 */     put(key, newValue);
/* 487 */     return newValue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default V merge(int key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*     */     V newValue;
/* 513 */     Objects.requireNonNull(remappingFunction);
/* 514 */     Objects.requireNonNull(value);
/* 515 */     V oldValue = get(key), drv = defaultReturnValue();
/*     */     
/* 517 */     if (oldValue != drv || containsKey(key)) {
/* 518 */       V mergedValue = remappingFunction.apply(oldValue, value);
/* 519 */       if (mergedValue == null) {
/* 520 */         remove(key);
/* 521 */         return drv;
/*     */       } 
/* 523 */       newValue = mergedValue;
/*     */     } else {
/* 525 */       newValue = value;
/*     */     } 
/* 527 */     put(key, newValue);
/* 528 */     return newValue;
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
/*     */   @Deprecated
/*     */   default V getOrDefault(Object key, V defaultValue) {
/* 541 */     return super.getOrDefault(key, defaultValue);
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
/*     */   @Deprecated
/*     */   default V putIfAbsent(Integer key, V value) {
/* 554 */     return super.putIfAbsent(key, value);
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
/*     */   @Deprecated
/*     */   default boolean remove(Object key, Object value) {
/* 567 */     return super.remove(key, value);
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
/*     */   @Deprecated
/*     */   default boolean replace(Integer key, V oldValue, V newValue) {
/* 580 */     return super.replace(key, oldValue, newValue);
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
/*     */   @Deprecated
/*     */   default V replace(Integer key, V value) {
/* 593 */     return super.replace(key, value);
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
/*     */   @Deprecated
/*     */   default V computeIfAbsent(Integer key, Function<? super Integer, ? extends V> mappingFunction) {
/* 607 */     return super.computeIfAbsent(key, mappingFunction);
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
/*     */   @Deprecated
/*     */   default V computeIfPresent(Integer key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 621 */     return super.computeIfPresent(key, remappingFunction);
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
/*     */   @Deprecated
/*     */   default V compute(Integer key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 635 */     return super.compute(key, remappingFunction);
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
/*     */   @Deprecated
/*     */   default V merge(Integer key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 649 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   int size();
/*     */   
/*     */   void defaultReturnValue(V paramV);
/*     */   
/*     */   V defaultReturnValue();
/*     */   
/*     */   ObjectSet<Entry<V>> int2ObjectEntrySet();
/*     */   
/*     */   IntSet keySet();
/*     */   
/*     */   ObjectCollection<V> values();
/*     */   
/*     */   boolean containsKey(int paramInt);
/*     */   
/*     */   public static interface Entry<V>
/*     */     extends Map.Entry<Integer, V>
/*     */   {
/*     */     @Deprecated
/*     */     default Integer getKey() {
/* 672 */       return Integer.valueOf(getIntKey());
/*     */     }
/*     */     
/*     */     int getIntKey();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2ObjectMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */