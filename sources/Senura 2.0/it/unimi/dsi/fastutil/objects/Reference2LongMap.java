/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Reference2LongMap<K>
/*     */   extends Reference2LongFunction<K>, Map<K, Long>
/*     */ {
/*     */   public static interface FastEntrySet<K>
/*     */     extends ObjectSet<Entry<K>>
/*     */   {
/*     */     ObjectIterator<Reference2LongMap.Entry<K>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Reference2LongMap.Entry<K>> consumer) {
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
/*     */   @Deprecated
/*     */   default ObjectSet<Map.Entry<K, Long>> entrySet() {
/* 153 */     return (ObjectSet)reference2LongEntrySet();
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
/*     */   default Long put(K key, Long value) {
/* 167 */     return super.put(key, value);
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
/*     */   default Long get(Object key) {
/* 181 */     return super.get(key);
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
/*     */   default Long remove(Object key) {
/* 195 */     return super.remove(key);
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
/*     */   @Deprecated
/*     */   default boolean containsValue(Object value) {
/* 244 */     return (value == null) ? false : containsValue(((Long)value).longValue());
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
/*     */   default long getOrDefault(Object key, long defaultValue) {
/*     */     long v;
/* 264 */     return ((v = getLong(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default long putIfAbsent(K key, long value) {
/* 284 */     long v = getLong(key), drv = defaultReturnValue();
/* 285 */     if (v != drv || containsKey(key))
/* 286 */       return v; 
/* 287 */     put(key, value);
/* 288 */     return drv;
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
/*     */   default boolean remove(Object key, long value) {
/* 305 */     long curValue = getLong(key);
/* 306 */     if (curValue != value || (curValue == defaultReturnValue() && !containsKey(key)))
/* 307 */       return false; 
/* 308 */     removeLong(key);
/* 309 */     return true;
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
/*     */   default boolean replace(K key, long oldValue, long newValue) {
/* 328 */     long curValue = getLong(key);
/* 329 */     if (curValue != oldValue || (curValue == defaultReturnValue() && !containsKey(key)))
/* 330 */       return false; 
/* 331 */     put(key, newValue);
/* 332 */     return true;
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
/*     */   default long replace(K key, long value) {
/* 351 */     return containsKey(key) ? put(key, value) : defaultReturnValue();
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
/*     */   default long computeLongIfAbsent(K key, ToLongFunction<? super K> mappingFunction) {
/* 377 */     Objects.requireNonNull(mappingFunction);
/* 378 */     long v = getLong(key);
/* 379 */     if (v != defaultReturnValue() || containsKey(key))
/* 380 */       return v; 
/* 381 */     long newValue = mappingFunction.applyAsLong(key);
/* 382 */     put(key, newValue);
/* 383 */     return newValue;
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
/*     */   default long computeLongIfAbsentPartial(K key, Reference2LongFunction<? super K> mappingFunction) {
/* 412 */     Objects.requireNonNull(mappingFunction);
/* 413 */     long v = getLong(key), drv = defaultReturnValue();
/* 414 */     if (v != drv || containsKey(key))
/* 415 */       return v; 
/* 416 */     if (!mappingFunction.containsKey(key))
/* 417 */       return drv; 
/* 418 */     long newValue = mappingFunction.getLong(key);
/* 419 */     put(key, newValue);
/* 420 */     return newValue;
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
/*     */   default long computeLongIfPresent(K key, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/* 439 */     Objects.requireNonNull(remappingFunction);
/* 440 */     long oldValue = getLong(key), drv = defaultReturnValue();
/* 441 */     if (oldValue == drv && !containsKey(key))
/* 442 */       return drv; 
/* 443 */     Long newValue = remappingFunction.apply(key, Long.valueOf(oldValue));
/* 444 */     if (newValue == null) {
/* 445 */       removeLong(key);
/* 446 */       return drv;
/*     */     } 
/* 448 */     long newVal = newValue.longValue();
/* 449 */     put(key, newVal);
/* 450 */     return newVal;
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
/*     */   default long computeLong(K key, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/* 475 */     Objects.requireNonNull(remappingFunction);
/* 476 */     long oldValue = getLong(key), drv = defaultReturnValue();
/* 477 */     boolean contained = (oldValue != drv || containsKey(key));
/* 478 */     Long newValue = remappingFunction.apply(key, contained ? Long.valueOf(oldValue) : null);
/* 479 */     if (newValue == null) {
/* 480 */       if (contained)
/* 481 */         removeLong(key); 
/* 482 */       return drv;
/*     */     } 
/* 484 */     long newVal = newValue.longValue();
/* 485 */     put(key, newVal);
/* 486 */     return newVal;
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
/*     */   default long mergeLong(K key, long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*     */     long newValue;
/* 512 */     Objects.requireNonNull(remappingFunction);
/* 513 */     long oldValue = getLong(key), drv = defaultReturnValue();
/*     */     
/* 515 */     if (oldValue != drv || containsKey(key)) {
/* 516 */       Long mergedValue = remappingFunction.apply(Long.valueOf(oldValue), Long.valueOf(value));
/* 517 */       if (mergedValue == null) {
/* 518 */         removeLong(key);
/* 519 */         return drv;
/*     */       } 
/* 521 */       newValue = mergedValue.longValue();
/*     */     } else {
/* 523 */       newValue = value;
/*     */     } 
/* 525 */     put(key, newValue);
/* 526 */     return newValue;
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
/*     */   default Long getOrDefault(Object key, Long defaultValue) {
/* 539 */     return super.getOrDefault(key, defaultValue);
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
/*     */   default Long putIfAbsent(K key, Long value) {
/* 552 */     return super.putIfAbsent(key, value);
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
/* 565 */     return super.remove(key, value);
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
/*     */   default boolean replace(K key, Long oldValue, Long newValue) {
/* 578 */     return super.replace(key, oldValue, newValue);
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
/*     */   default Long replace(K key, Long value) {
/* 591 */     return super.replace(key, value);
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
/*     */   default Long merge(K key, Long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 605 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   int size();
/*     */ 
/*     */   
/*     */   void defaultReturnValue(long paramLong);
/*     */ 
/*     */   
/*     */   long defaultReturnValue();
/*     */ 
/*     */   
/*     */   ObjectSet<Entry<K>> reference2LongEntrySet();
/*     */ 
/*     */   
/*     */   ReferenceSet<K> keySet();
/*     */ 
/*     */   
/*     */   LongCollection values();
/*     */   
/*     */   boolean containsKey(Object paramObject);
/*     */   
/*     */   boolean containsValue(long paramLong);
/*     */   
/*     */   public static interface Entry<K>
/*     */     extends Map.Entry<K, Long>
/*     */   {
/*     */     @Deprecated
/*     */     default Long getValue() {
/* 635 */       return Long.valueOf(getLongValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Long setValue(Long value) {
/* 645 */       return Long.valueOf(setValue(value.longValue()));
/*     */     }
/*     */     
/*     */     long getLongValue();
/*     */     
/*     */     long setValue(long param1Long);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2LongMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */