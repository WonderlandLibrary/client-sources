/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Reference2BooleanMap<K>
/*     */   extends Reference2BooleanFunction<K>, Map<K, Boolean>
/*     */ {
/*     */   public static interface FastEntrySet<K>
/*     */     extends ObjectSet<Entry<K>>
/*     */   {
/*     */     ObjectIterator<Reference2BooleanMap.Entry<K>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Reference2BooleanMap.Entry<K>> consumer) {
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
/*     */   default ObjectSet<Map.Entry<K, Boolean>> entrySet() {
/* 153 */     return (ObjectSet)reference2BooleanEntrySet();
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
/*     */   default Boolean put(K key, Boolean value) {
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean containsValue(Object value) {
/* 244 */     return (value == null) ? false : containsValue(((Boolean)value).booleanValue());
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
/*     */   default boolean getOrDefault(Object key, boolean defaultValue) {
/*     */     boolean v;
/* 264 */     return ((v = getBoolean(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default boolean putIfAbsent(K key, boolean value) {
/* 284 */     boolean v = getBoolean(key), drv = defaultReturnValue();
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
/*     */   default boolean remove(Object key, boolean value) {
/* 305 */     boolean curValue = getBoolean(key);
/* 306 */     if (curValue != value || (curValue == defaultReturnValue() && !containsKey(key)))
/* 307 */       return false; 
/* 308 */     removeBoolean(key);
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
/*     */   default boolean replace(K key, boolean oldValue, boolean newValue) {
/* 328 */     boolean curValue = getBoolean(key);
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
/*     */   default boolean replace(K key, boolean value) {
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
/*     */   default boolean computeBooleanIfAbsent(K key, Predicate<? super K> mappingFunction) {
/* 377 */     Objects.requireNonNull(mappingFunction);
/* 378 */     boolean v = getBoolean(key);
/* 379 */     if (v != defaultReturnValue() || containsKey(key))
/* 380 */       return v; 
/* 381 */     boolean newValue = mappingFunction.test(key);
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
/*     */   
/*     */   default boolean computeBooleanIfAbsentPartial(K key, Reference2BooleanFunction<? super K> mappingFunction) {
/* 413 */     Objects.requireNonNull(mappingFunction);
/* 414 */     boolean v = getBoolean(key), drv = defaultReturnValue();
/* 415 */     if (v != drv || containsKey(key))
/* 416 */       return v; 
/* 417 */     if (!mappingFunction.containsKey(key))
/* 418 */       return drv; 
/* 419 */     boolean newValue = mappingFunction.getBoolean(key);
/* 420 */     put(key, newValue);
/* 421 */     return newValue;
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
/*     */   default boolean computeBooleanIfPresent(K key, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 440 */     Objects.requireNonNull(remappingFunction);
/* 441 */     boolean oldValue = getBoolean(key), drv = defaultReturnValue();
/* 442 */     if (oldValue == drv && !containsKey(key))
/* 443 */       return drv; 
/* 444 */     Boolean newValue = remappingFunction.apply(key, Boolean.valueOf(oldValue));
/* 445 */     if (newValue == null) {
/* 446 */       removeBoolean(key);
/* 447 */       return drv;
/*     */     } 
/* 449 */     boolean newVal = newValue.booleanValue();
/* 450 */     put(key, newVal);
/* 451 */     return newVal;
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
/*     */   default boolean computeBoolean(K key, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 476 */     Objects.requireNonNull(remappingFunction);
/* 477 */     boolean oldValue = getBoolean(key), drv = defaultReturnValue();
/* 478 */     boolean contained = (oldValue != drv || containsKey(key));
/* 479 */     Boolean newValue = remappingFunction.apply(key, contained ? Boolean.valueOf(oldValue) : null);
/* 480 */     if (newValue == null) {
/* 481 */       if (contained)
/* 482 */         removeBoolean(key); 
/* 483 */       return drv;
/*     */     } 
/* 485 */     boolean newVal = newValue.booleanValue();
/* 486 */     put(key, newVal);
/* 487 */     return newVal;
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
/*     */   default boolean mergeBoolean(K key, boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*     */     boolean newValue;
/* 513 */     Objects.requireNonNull(remappingFunction);
/* 514 */     boolean oldValue = getBoolean(key), drv = defaultReturnValue();
/*     */     
/* 516 */     if (oldValue != drv || containsKey(key)) {
/* 517 */       Boolean mergedValue = remappingFunction.apply(Boolean.valueOf(oldValue), Boolean.valueOf(value));
/* 518 */       if (mergedValue == null) {
/* 519 */         removeBoolean(key);
/* 520 */         return drv;
/*     */       } 
/* 522 */       newValue = mergedValue.booleanValue();
/*     */     } else {
/* 524 */       newValue = value;
/*     */     } 
/* 526 */     put(key, newValue);
/* 527 */     return newValue;
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
/* 540 */     return super.getOrDefault(key, defaultValue);
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
/*     */   default Boolean putIfAbsent(K key, Boolean value) {
/* 553 */     return super.putIfAbsent(key, value);
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
/* 566 */     return super.remove(key, value);
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
/*     */   default boolean replace(K key, Boolean oldValue, Boolean newValue) {
/* 579 */     return super.replace(key, oldValue, newValue);
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
/*     */   default Boolean replace(K key, Boolean value) {
/* 592 */     return super.replace(key, value);
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
/*     */   default Boolean merge(K key, Boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 606 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   int size();
/*     */ 
/*     */   
/*     */   void defaultReturnValue(boolean paramBoolean);
/*     */ 
/*     */   
/*     */   boolean defaultReturnValue();
/*     */ 
/*     */   
/*     */   ObjectSet<Entry<K>> reference2BooleanEntrySet();
/*     */ 
/*     */   
/*     */   ReferenceSet<K> keySet();
/*     */ 
/*     */   
/*     */   BooleanCollection values();
/*     */   
/*     */   boolean containsKey(Object paramObject);
/*     */   
/*     */   boolean containsValue(boolean paramBoolean);
/*     */   
/*     */   public static interface Entry<K>
/*     */     extends Map.Entry<K, Boolean>
/*     */   {
/*     */     @Deprecated
/*     */     default Boolean getValue() {
/* 636 */       return Boolean.valueOf(getBooleanValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Boolean setValue(Boolean value) {
/* 646 */       return Boolean.valueOf(setValue(value.booleanValue()));
/*     */     }
/*     */     
/*     */     boolean getBooleanValue();
/*     */     
/*     */     boolean setValue(boolean param1Boolean);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2BooleanMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */