/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortCollection;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Reference2ShortMap<K>
/*     */   extends Reference2ShortFunction<K>, Map<K, Short>
/*     */ {
/*     */   public static interface FastEntrySet<K>
/*     */     extends ObjectSet<Entry<K>>
/*     */   {
/*     */     ObjectIterator<Reference2ShortMap.Entry<K>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Reference2ShortMap.Entry<K>> consumer) {
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
/*     */   default ObjectSet<Map.Entry<K, Short>> entrySet() {
/* 153 */     return (ObjectSet)reference2ShortEntrySet();
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
/*     */   default Short put(K key, Short value) {
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
/*     */   default Short get(Object key) {
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
/*     */   default Short remove(Object key) {
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
/* 244 */     return (value == null) ? false : containsValue(((Short)value).shortValue());
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
/*     */   default short getOrDefault(Object key, short defaultValue) {
/*     */     short v;
/* 264 */     return ((v = getShort(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default short putIfAbsent(K key, short value) {
/* 284 */     short v = getShort(key), drv = defaultReturnValue();
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
/*     */   default boolean remove(Object key, short value) {
/* 305 */     short curValue = getShort(key);
/* 306 */     if (curValue != value || (curValue == defaultReturnValue() && !containsKey(key)))
/* 307 */       return false; 
/* 308 */     removeShort(key);
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
/*     */   default boolean replace(K key, short oldValue, short newValue) {
/* 328 */     short curValue = getShort(key);
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
/*     */   default short replace(K key, short value) {
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
/*     */   default short computeShortIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
/* 377 */     Objects.requireNonNull(mappingFunction);
/* 378 */     short v = getShort(key);
/* 379 */     if (v != defaultReturnValue() || containsKey(key))
/* 380 */       return v; 
/* 381 */     short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt(key));
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
/*     */   default short computeShortIfAbsentPartial(K key, Reference2ShortFunction<? super K> mappingFunction) {
/* 412 */     Objects.requireNonNull(mappingFunction);
/* 413 */     short v = getShort(key), drv = defaultReturnValue();
/* 414 */     if (v != drv || containsKey(key))
/* 415 */       return v; 
/* 416 */     if (!mappingFunction.containsKey(key))
/* 417 */       return drv; 
/* 418 */     short newValue = mappingFunction.getShort(key);
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
/*     */   default short computeShortIfPresent(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
/* 439 */     Objects.requireNonNull(remappingFunction);
/* 440 */     short oldValue = getShort(key), drv = defaultReturnValue();
/* 441 */     if (oldValue == drv && !containsKey(key))
/* 442 */       return drv; 
/* 443 */     Short newValue = remappingFunction.apply(key, Short.valueOf(oldValue));
/* 444 */     if (newValue == null) {
/* 445 */       removeShort(key);
/* 446 */       return drv;
/*     */     } 
/* 448 */     short newVal = newValue.shortValue();
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
/*     */   default short computeShort(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
/* 475 */     Objects.requireNonNull(remappingFunction);
/* 476 */     short oldValue = getShort(key), drv = defaultReturnValue();
/* 477 */     boolean contained = (oldValue != drv || containsKey(key));
/* 478 */     Short newValue = remappingFunction.apply(key, contained ? Short.valueOf(oldValue) : null);
/* 479 */     if (newValue == null) {
/* 480 */       if (contained)
/* 481 */         removeShort(key); 
/* 482 */       return drv;
/*     */     } 
/* 484 */     short newVal = newValue.shortValue();
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
/*     */   default short mergeShort(K key, short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*     */     short newValue;
/* 512 */     Objects.requireNonNull(remappingFunction);
/* 513 */     short oldValue = getShort(key), drv = defaultReturnValue();
/*     */     
/* 515 */     if (oldValue != drv || containsKey(key)) {
/* 516 */       Short mergedValue = remappingFunction.apply(Short.valueOf(oldValue), Short.valueOf(value));
/* 517 */       if (mergedValue == null) {
/* 518 */         removeShort(key);
/* 519 */         return drv;
/*     */       } 
/* 521 */       newValue = mergedValue.shortValue();
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
/*     */   default Short getOrDefault(Object key, Short defaultValue) {
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
/*     */   default Short putIfAbsent(K key, Short value) {
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
/*     */   default boolean replace(K key, Short oldValue, Short newValue) {
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
/*     */   default Short replace(K key, Short value) {
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
/*     */   default Short merge(K key, Short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/* 605 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   int size();
/*     */ 
/*     */   
/*     */   void defaultReturnValue(short paramShort);
/*     */ 
/*     */   
/*     */   short defaultReturnValue();
/*     */ 
/*     */   
/*     */   ObjectSet<Entry<K>> reference2ShortEntrySet();
/*     */ 
/*     */   
/*     */   ReferenceSet<K> keySet();
/*     */ 
/*     */   
/*     */   ShortCollection values();
/*     */   
/*     */   boolean containsKey(Object paramObject);
/*     */   
/*     */   boolean containsValue(short paramShort);
/*     */   
/*     */   public static interface Entry<K>
/*     */     extends Map.Entry<K, Short>
/*     */   {
/*     */     @Deprecated
/*     */     default Short getValue() {
/* 635 */       return Short.valueOf(getShortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Short setValue(Short value) {
/* 645 */       return Short.valueOf(setValue(value.shortValue()));
/*     */     }
/*     */     
/*     */     short getShortValue();
/*     */     
/*     */     short setValue(short param1Short);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2ShortMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */