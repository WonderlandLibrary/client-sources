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
/*     */ public interface Object2BooleanMap<K>
/*     */   extends Object2BooleanFunction<K>, Map<K, Boolean>
/*     */ {
/*     */   public static interface FastEntrySet<K>
/*     */     extends ObjectSet<Entry<K>>
/*     */   {
/*     */     ObjectIterator<Object2BooleanMap.Entry<K>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Object2BooleanMap.Entry<K>> consumer) {
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
/*     */   default ObjectSet<Map.Entry<K, Boolean>> entrySet() {
/* 151 */     return (ObjectSet)object2BooleanEntrySet();
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
/*     */   default Boolean get(Object key) {
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
/*     */   default Boolean remove(Object key) {
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
/* 242 */     return (value == null) ? false : containsValue(((Boolean)value).booleanValue());
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
/* 262 */     return ((v = getBoolean(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/* 282 */     boolean v = getBoolean(key), drv = defaultReturnValue();
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
/*     */   default boolean remove(Object key, boolean value) {
/* 303 */     boolean curValue = getBoolean(key);
/* 304 */     if (curValue != value || (curValue == defaultReturnValue() && !containsKey(key)))
/* 305 */       return false; 
/* 306 */     removeBoolean(key);
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
/*     */   default boolean replace(K key, boolean oldValue, boolean newValue) {
/* 326 */     boolean curValue = getBoolean(key);
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
/*     */   default boolean replace(K key, boolean value) {
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
/*     */   default boolean computeBooleanIfAbsent(K key, Predicate<? super K> mappingFunction) {
/* 375 */     Objects.requireNonNull(mappingFunction);
/* 376 */     boolean v = getBoolean(key);
/* 377 */     if (v != defaultReturnValue() || containsKey(key))
/* 378 */       return v; 
/* 379 */     boolean newValue = mappingFunction.test(key);
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
/*     */   
/*     */   default boolean computeBooleanIfAbsentPartial(K key, Object2BooleanFunction<? super K> mappingFunction) {
/* 411 */     Objects.requireNonNull(mappingFunction);
/* 412 */     boolean v = getBoolean(key), drv = defaultReturnValue();
/* 413 */     if (v != drv || containsKey(key))
/* 414 */       return v; 
/* 415 */     if (!mappingFunction.containsKey(key))
/* 416 */       return drv; 
/* 417 */     boolean newValue = mappingFunction.getBoolean(key);
/* 418 */     put(key, newValue);
/* 419 */     return newValue;
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
/* 438 */     Objects.requireNonNull(remappingFunction);
/* 439 */     boolean oldValue = getBoolean(key), drv = defaultReturnValue();
/* 440 */     if (oldValue == drv && !containsKey(key))
/* 441 */       return drv; 
/* 442 */     Boolean newValue = remappingFunction.apply(key, Boolean.valueOf(oldValue));
/* 443 */     if (newValue == null) {
/* 444 */       removeBoolean(key);
/* 445 */       return drv;
/*     */     } 
/* 447 */     boolean newVal = newValue.booleanValue();
/* 448 */     put(key, newVal);
/* 449 */     return newVal;
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
/* 474 */     Objects.requireNonNull(remappingFunction);
/* 475 */     boolean oldValue = getBoolean(key), drv = defaultReturnValue();
/* 476 */     boolean contained = (oldValue != drv || containsKey(key));
/* 477 */     Boolean newValue = remappingFunction.apply(key, contained ? Boolean.valueOf(oldValue) : null);
/* 478 */     if (newValue == null) {
/* 479 */       if (contained)
/* 480 */         removeBoolean(key); 
/* 481 */       return drv;
/*     */     } 
/* 483 */     boolean newVal = newValue.booleanValue();
/* 484 */     put(key, newVal);
/* 485 */     return newVal;
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
/* 511 */     Objects.requireNonNull(remappingFunction);
/* 512 */     boolean oldValue = getBoolean(key), drv = defaultReturnValue();
/*     */     
/* 514 */     if (oldValue != drv || containsKey(key)) {
/* 515 */       Boolean mergedValue = remappingFunction.apply(Boolean.valueOf(oldValue), Boolean.valueOf(value));
/* 516 */       if (mergedValue == null) {
/* 517 */         removeBoolean(key);
/* 518 */         return drv;
/*     */       } 
/* 520 */       newValue = mergedValue.booleanValue();
/*     */     } else {
/* 522 */       newValue = value;
/*     */     } 
/* 524 */     put(key, newValue);
/* 525 */     return newValue;
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
/* 538 */     return super.getOrDefault(key, defaultValue);
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
/* 551 */     return super.putIfAbsent(key, value);
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
/* 564 */     return super.remove(key, value);
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
/* 577 */     return super.replace(key, oldValue, newValue);
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
/* 590 */     return super.replace(key, value);
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
/* 604 */     return super.merge(key, value, remappingFunction);
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
/*     */   ObjectSet<Entry<K>> object2BooleanEntrySet();
/*     */ 
/*     */   
/*     */   ObjectSet<K> keySet();
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
/* 634 */       return Boolean.valueOf(getBooleanValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Boolean setValue(Boolean value) {
/* 644 */       return Boolean.valueOf(setValue(value.booleanValue()));
/*     */     }
/*     */     
/*     */     boolean getBooleanValue();
/*     */     
/*     */     boolean setValue(boolean param1Boolean);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2BooleanMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */