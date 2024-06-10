/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.LongFunction;
/*     */ import java.util.function.LongPredicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Long2BooleanMap
/*     */   extends Long2BooleanFunction, Map<Long, Boolean>
/*     */ {
/*     */   public static interface FastEntrySet
/*     */     extends ObjectSet<Entry>
/*     */   {
/*     */     ObjectIterator<Long2BooleanMap.Entry> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Long2BooleanMap.Entry> consumer) {
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
/*     */   default ObjectSet<Map.Entry<Long, Boolean>> entrySet() {
/* 153 */     return (ObjectSet)long2BooleanEntrySet();
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
/*     */   default Boolean put(Long key, Boolean value) {
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
/*     */   default Boolean get(Object key) {
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
/*     */   default Boolean remove(Object key) {
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
/*     */   @Deprecated
/*     */   default boolean containsKey(Object key) {
/* 241 */     return super.containsKey(key);
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
/*     */   @Deprecated
/*     */   default boolean containsValue(Object value) {
/* 258 */     return (value == null) ? false : containsValue(((Boolean)value).booleanValue());
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
/*     */   default boolean getOrDefault(long key, boolean defaultValue) {
/*     */     boolean v;
/* 278 */     return ((v = get(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default boolean putIfAbsent(long key, boolean value) {
/* 298 */     boolean v = get(key), drv = defaultReturnValue();
/* 299 */     if (v != drv || containsKey(key))
/* 300 */       return v; 
/* 301 */     put(key, value);
/* 302 */     return drv;
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
/*     */   default boolean remove(long key, boolean value) {
/* 319 */     boolean curValue = get(key);
/* 320 */     if (curValue != value || (curValue == defaultReturnValue() && !containsKey(key)))
/* 321 */       return false; 
/* 322 */     remove(key);
/* 323 */     return true;
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
/*     */   default boolean replace(long key, boolean oldValue, boolean newValue) {
/* 342 */     boolean curValue = get(key);
/* 343 */     if (curValue != oldValue || (curValue == defaultReturnValue() && !containsKey(key)))
/* 344 */       return false; 
/* 345 */     put(key, newValue);
/* 346 */     return true;
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
/*     */   default boolean replace(long key, boolean value) {
/* 365 */     return containsKey(key) ? put(key, value) : defaultReturnValue();
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
/*     */   default boolean computeIfAbsent(long key, LongPredicate mappingFunction) {
/* 391 */     Objects.requireNonNull(mappingFunction);
/* 392 */     boolean v = get(key);
/* 393 */     if (v != defaultReturnValue() || containsKey(key))
/* 394 */       return v; 
/* 395 */     boolean newValue = mappingFunction.test(key);
/* 396 */     put(key, newValue);
/* 397 */     return newValue;
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
/*     */   default boolean computeIfAbsentNullable(long key, LongFunction<? extends Boolean> mappingFunction) {
/* 424 */     Objects.requireNonNull(mappingFunction);
/* 425 */     boolean v = get(key), drv = defaultReturnValue();
/* 426 */     if (v != drv || containsKey(key))
/* 427 */       return v; 
/* 428 */     Boolean mappedValue = mappingFunction.apply(key);
/* 429 */     if (mappedValue == null)
/* 430 */       return drv; 
/* 431 */     boolean newValue = mappedValue.booleanValue();
/* 432 */     put(key, newValue);
/* 433 */     return newValue;
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
/*     */   default boolean computeIfAbsentPartial(long key, Long2BooleanFunction mappingFunction) {
/* 462 */     Objects.requireNonNull(mappingFunction);
/* 463 */     boolean v = get(key), drv = defaultReturnValue();
/* 464 */     if (v != drv || containsKey(key))
/* 465 */       return v; 
/* 466 */     if (!mappingFunction.containsKey(key))
/* 467 */       return drv; 
/* 468 */     boolean newValue = mappingFunction.get(key);
/* 469 */     put(key, newValue);
/* 470 */     return newValue;
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
/*     */   default boolean computeIfPresent(long key, BiFunction<? super Long, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 489 */     Objects.requireNonNull(remappingFunction);
/* 490 */     boolean oldValue = get(key), drv = defaultReturnValue();
/* 491 */     if (oldValue == drv && !containsKey(key))
/* 492 */       return drv; 
/* 493 */     Boolean newValue = remappingFunction.apply(Long.valueOf(key), Boolean.valueOf(oldValue));
/* 494 */     if (newValue == null) {
/* 495 */       remove(key);
/* 496 */       return drv;
/*     */     } 
/* 498 */     boolean newVal = newValue.booleanValue();
/* 499 */     put(key, newVal);
/* 500 */     return newVal;
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
/*     */   default boolean compute(long key, BiFunction<? super Long, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 525 */     Objects.requireNonNull(remappingFunction);
/* 526 */     boolean oldValue = get(key), drv = defaultReturnValue();
/* 527 */     boolean contained = (oldValue != drv || containsKey(key));
/* 528 */     Boolean newValue = remappingFunction.apply(Long.valueOf(key), 
/* 529 */         contained ? Boolean.valueOf(oldValue) : null);
/* 530 */     if (newValue == null) {
/* 531 */       if (contained)
/* 532 */         remove(key); 
/* 533 */       return drv;
/*     */     } 
/* 535 */     boolean newVal = newValue.booleanValue();
/* 536 */     put(key, newVal);
/* 537 */     return newVal;
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
/*     */   default boolean merge(long key, boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*     */     boolean newValue;
/* 563 */     Objects.requireNonNull(remappingFunction);
/* 564 */     boolean oldValue = get(key), drv = defaultReturnValue();
/*     */     
/* 566 */     if (oldValue != drv || containsKey(key)) {
/* 567 */       Boolean mergedValue = remappingFunction.apply(Boolean.valueOf(oldValue), Boolean.valueOf(value));
/* 568 */       if (mergedValue == null) {
/* 569 */         remove(key);
/* 570 */         return drv;
/*     */       } 
/* 572 */       newValue = mergedValue.booleanValue();
/*     */     } else {
/* 574 */       newValue = value;
/*     */     } 
/* 576 */     put(key, newValue);
/* 577 */     return newValue;
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
/*     */   default Boolean getOrDefault(Object key, Boolean defaultValue) {
/* 590 */     return super.getOrDefault(key, defaultValue);
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
/*     */   default Boolean putIfAbsent(Long key, Boolean value) {
/* 603 */     return super.putIfAbsent(key, value);
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
/* 616 */     return super.remove(key, value);
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
/*     */   default boolean replace(Long key, Boolean oldValue, Boolean newValue) {
/* 629 */     return super.replace(key, oldValue, newValue);
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
/*     */   default Boolean replace(Long key, Boolean value) {
/* 642 */     return super.replace(key, value);
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
/*     */   default Boolean computeIfAbsent(Long key, Function<? super Long, ? extends Boolean> mappingFunction) {
/* 656 */     return super.computeIfAbsent(key, mappingFunction);
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
/*     */   default Boolean computeIfPresent(Long key, BiFunction<? super Long, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 670 */     return super.computeIfPresent(key, remappingFunction);
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
/*     */   default Boolean compute(Long key, BiFunction<? super Long, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 684 */     return super.compute(key, remappingFunction);
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
/*     */   default Boolean merge(Long key, Boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 698 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */   
/*     */   int size();
/*     */   
/*     */   void defaultReturnValue(boolean paramBoolean);
/*     */   
/*     */   boolean defaultReturnValue();
/*     */   
/*     */   ObjectSet<Entry> long2BooleanEntrySet();
/*     */   
/*     */   LongSet keySet();
/*     */   
/*     */   BooleanCollection values();
/*     */   
/*     */   boolean containsKey(long paramLong);
/*     */   
/*     */   boolean containsValue(boolean paramBoolean);
/*     */   
/*     */   public static interface Entry
/*     */     extends Map.Entry<Long, Boolean> {
/*     */     @Deprecated
/*     */     default Long getKey() {
/* 721 */       return Long.valueOf(getLongKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Boolean getValue() {
/* 744 */       return Boolean.valueOf(getBooleanValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Boolean setValue(Boolean value) {
/* 754 */       return Boolean.valueOf(setValue(value.booleanValue()));
/*     */     }
/*     */     
/*     */     long getLongKey();
/*     */     
/*     */     boolean getBooleanValue();
/*     */     
/*     */     boolean setValue(boolean param1Boolean);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2BooleanMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */