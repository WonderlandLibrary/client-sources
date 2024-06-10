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
/*     */ public interface Object2ShortMap<K>
/*     */   extends Object2ShortFunction<K>, Map<K, Short>
/*     */ {
/*     */   public static interface FastEntrySet<K>
/*     */     extends ObjectSet<Entry<K>>
/*     */   {
/*     */     ObjectIterator<Object2ShortMap.Entry<K>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Object2ShortMap.Entry<K>> consumer) {
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
/*     */   default ObjectSet<Map.Entry<K, Short>> entrySet() {
/* 151 */     return (ObjectSet)object2ShortEntrySet();
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
/*     */   default Short get(Object key) {
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
/*     */   default Short remove(Object key) {
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
/* 242 */     return (value == null) ? false : containsValue(((Short)value).shortValue());
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
/* 262 */     return ((v = getShort(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/* 282 */     short v = getShort(key), drv = defaultReturnValue();
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
/*     */   default boolean remove(Object key, short value) {
/* 303 */     short curValue = getShort(key);
/* 304 */     if (curValue != value || (curValue == defaultReturnValue() && !containsKey(key)))
/* 305 */       return false; 
/* 306 */     removeShort(key);
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
/*     */   default boolean replace(K key, short oldValue, short newValue) {
/* 326 */     short curValue = getShort(key);
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
/*     */   default short replace(K key, short value) {
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
/*     */   default short computeShortIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
/* 375 */     Objects.requireNonNull(mappingFunction);
/* 376 */     short v = getShort(key);
/* 377 */     if (v != defaultReturnValue() || containsKey(key))
/* 378 */       return v; 
/* 379 */     short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt(key));
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
/*     */   default short computeShortIfAbsentPartial(K key, Object2ShortFunction<? super K> mappingFunction) {
/* 410 */     Objects.requireNonNull(mappingFunction);
/* 411 */     short v = getShort(key), drv = defaultReturnValue();
/* 412 */     if (v != drv || containsKey(key))
/* 413 */       return v; 
/* 414 */     if (!mappingFunction.containsKey(key))
/* 415 */       return drv; 
/* 416 */     short newValue = mappingFunction.getShort(key);
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
/*     */   default short computeShortIfPresent(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
/* 437 */     Objects.requireNonNull(remappingFunction);
/* 438 */     short oldValue = getShort(key), drv = defaultReturnValue();
/* 439 */     if (oldValue == drv && !containsKey(key))
/* 440 */       return drv; 
/* 441 */     Short newValue = remappingFunction.apply(key, Short.valueOf(oldValue));
/* 442 */     if (newValue == null) {
/* 443 */       removeShort(key);
/* 444 */       return drv;
/*     */     } 
/* 446 */     short newVal = newValue.shortValue();
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
/*     */   default short computeShort(K key, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
/* 473 */     Objects.requireNonNull(remappingFunction);
/* 474 */     short oldValue = getShort(key), drv = defaultReturnValue();
/* 475 */     boolean contained = (oldValue != drv || containsKey(key));
/* 476 */     Short newValue = remappingFunction.apply(key, contained ? Short.valueOf(oldValue) : null);
/* 477 */     if (newValue == null) {
/* 478 */       if (contained)
/* 479 */         removeShort(key); 
/* 480 */       return drv;
/*     */     } 
/* 482 */     short newVal = newValue.shortValue();
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
/*     */   default short mergeShort(K key, short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*     */     short newValue;
/* 510 */     Objects.requireNonNull(remappingFunction);
/* 511 */     short oldValue = getShort(key), drv = defaultReturnValue();
/*     */     
/* 513 */     if (oldValue != drv || containsKey(key)) {
/* 514 */       Short mergedValue = remappingFunction.apply(Short.valueOf(oldValue), Short.valueOf(value));
/* 515 */       if (mergedValue == null) {
/* 516 */         removeShort(key);
/* 517 */         return drv;
/*     */       } 
/* 519 */       newValue = mergedValue.shortValue();
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
/*     */   default Short getOrDefault(Object key, Short defaultValue) {
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
/*     */   default Short putIfAbsent(K key, Short value) {
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
/*     */   default boolean replace(K key, Short oldValue, Short newValue) {
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
/*     */   default Short replace(K key, Short value) {
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
/*     */   default Short merge(K key, Short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/* 603 */     return super.merge(key, value, remappingFunction);
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
/*     */   ObjectSet<Entry<K>> object2ShortEntrySet();
/*     */ 
/*     */   
/*     */   ObjectSet<K> keySet();
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
/* 633 */       return Short.valueOf(getShortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Short setValue(Short value) {
/* 643 */       return Short.valueOf(setValue(value.shortValue()));
/*     */     }
/*     */     
/*     */     short getShortValue();
/*     */     
/*     */     short setValue(short param1Short);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ShortMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */