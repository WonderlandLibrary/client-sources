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
/*     */ public interface Object2LongMap<K>
/*     */   extends Object2LongFunction<K>, Map<K, Long>
/*     */ {
/*     */   public static interface FastEntrySet<K>
/*     */     extends ObjectSet<Entry<K>>
/*     */   {
/*     */     ObjectIterator<Object2LongMap.Entry<K>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Object2LongMap.Entry<K>> consumer) {
/*  78 */       forEach(consumer);
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
/* 101 */     throw new UnsupportedOperationException();
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
/* 151 */     return (ObjectSet)object2LongEntrySet();
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
/* 165 */     return super.put(key, value);
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
/* 179 */     return super.get(key);
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
/* 193 */     return super.remove(key);
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
/* 242 */     return (value == null) ? false : containsValue(((Long)value).longValue());
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
/* 262 */     return ((v = getLong(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/* 282 */     long v = getLong(key), drv = defaultReturnValue();
/* 283 */     if (v != drv || containsKey(key))
/* 284 */       return v; 
/* 285 */     put(key, value);
/* 286 */     return drv;
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
/* 303 */     long curValue = getLong(key);
/* 304 */     if (curValue != value || (curValue == defaultReturnValue() && !containsKey(key)))
/* 305 */       return false; 
/* 306 */     removeLong(key);
/* 307 */     return true;
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
/* 326 */     long curValue = getLong(key);
/* 327 */     if (curValue != oldValue || (curValue == defaultReturnValue() && !containsKey(key)))
/* 328 */       return false; 
/* 329 */     put(key, newValue);
/* 330 */     return true;
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
/* 349 */     return containsKey(key) ? put(key, value) : defaultReturnValue();
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
/* 375 */     Objects.requireNonNull(mappingFunction);
/* 376 */     long v = getLong(key);
/* 377 */     if (v != defaultReturnValue() || containsKey(key))
/* 378 */       return v; 
/* 379 */     long newValue = mappingFunction.applyAsLong(key);
/* 380 */     put(key, newValue);
/* 381 */     return newValue;
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
/*     */   default long computeLongIfAbsentPartial(K key, Object2LongFunction<? super K> mappingFunction) {
/* 410 */     Objects.requireNonNull(mappingFunction);
/* 411 */     long v = getLong(key), drv = defaultReturnValue();
/* 412 */     if (v != drv || containsKey(key))
/* 413 */       return v; 
/* 414 */     if (!mappingFunction.containsKey(key))
/* 415 */       return drv; 
/* 416 */     long newValue = mappingFunction.getLong(key);
/* 417 */     put(key, newValue);
/* 418 */     return newValue;
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
/* 437 */     Objects.requireNonNull(remappingFunction);
/* 438 */     long oldValue = getLong(key), drv = defaultReturnValue();
/* 439 */     if (oldValue == drv && !containsKey(key))
/* 440 */       return drv; 
/* 441 */     Long newValue = remappingFunction.apply(key, Long.valueOf(oldValue));
/* 442 */     if (newValue == null) {
/* 443 */       removeLong(key);
/* 444 */       return drv;
/*     */     } 
/* 446 */     long newVal = newValue.longValue();
/* 447 */     put(key, newVal);
/* 448 */     return newVal;
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
/* 473 */     Objects.requireNonNull(remappingFunction);
/* 474 */     long oldValue = getLong(key), drv = defaultReturnValue();
/* 475 */     boolean contained = (oldValue != drv || containsKey(key));
/* 476 */     Long newValue = remappingFunction.apply(key, contained ? Long.valueOf(oldValue) : null);
/* 477 */     if (newValue == null) {
/* 478 */       if (contained)
/* 479 */         removeLong(key); 
/* 480 */       return drv;
/*     */     } 
/* 482 */     long newVal = newValue.longValue();
/* 483 */     put(key, newVal);
/* 484 */     return newVal;
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
/* 510 */     Objects.requireNonNull(remappingFunction);
/* 511 */     long oldValue = getLong(key), drv = defaultReturnValue();
/*     */     
/* 513 */     if (oldValue != drv || containsKey(key)) {
/* 514 */       Long mergedValue = remappingFunction.apply(Long.valueOf(oldValue), Long.valueOf(value));
/* 515 */       if (mergedValue == null) {
/* 516 */         removeLong(key);
/* 517 */         return drv;
/*     */       } 
/* 519 */       newValue = mergedValue.longValue();
/*     */     } else {
/* 521 */       newValue = value;
/*     */     } 
/* 523 */     put(key, newValue);
/* 524 */     return newValue;
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
/* 537 */     return super.getOrDefault(key, defaultValue);
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
/* 550 */     return super.putIfAbsent(key, value);
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
/* 563 */     return super.remove(key, value);
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
/* 576 */     return super.replace(key, oldValue, newValue);
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
/* 589 */     return super.replace(key, value);
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
/* 603 */     return super.merge(key, value, remappingFunction);
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
/*     */   ObjectSet<Entry<K>> object2LongEntrySet();
/*     */ 
/*     */   
/*     */   ObjectSet<K> keySet();
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
/* 633 */       return Long.valueOf(getLongValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Long setValue(Long value) {
/* 643 */       return Long.valueOf(setValue(value.longValue()));
/*     */     }
/*     */     
/*     */     long getLongValue();
/*     */     
/*     */     long setValue(long param1Long);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2LongMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */