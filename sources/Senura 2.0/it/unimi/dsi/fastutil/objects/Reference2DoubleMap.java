/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Reference2DoubleMap<K>
/*     */   extends Reference2DoubleFunction<K>, Map<K, Double>
/*     */ {
/*     */   public static interface FastEntrySet<K>
/*     */     extends ObjectSet<Entry<K>>
/*     */   {
/*     */     ObjectIterator<Reference2DoubleMap.Entry<K>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Reference2DoubleMap.Entry<K>> consumer) {
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
/*     */   default ObjectSet<Map.Entry<K, Double>> entrySet() {
/* 153 */     return (ObjectSet)reference2DoubleEntrySet();
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
/*     */   default Double put(K key, Double value) {
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
/*     */   default Double get(Object key) {
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
/*     */   default Double remove(Object key) {
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
/* 244 */     return (value == null) ? false : containsValue(((Double)value).doubleValue());
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
/*     */   default double getOrDefault(Object key, double defaultValue) {
/*     */     double v;
/* 264 */     return ((v = getDouble(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default double putIfAbsent(K key, double value) {
/* 284 */     double v = getDouble(key), drv = defaultReturnValue();
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
/*     */   default boolean remove(Object key, double value) {
/* 305 */     double curValue = getDouble(key);
/* 306 */     if (Double.doubleToLongBits(curValue) != Double.doubleToLongBits(value) || (curValue == 
/* 307 */       defaultReturnValue() && !containsKey(key)))
/* 308 */       return false; 
/* 309 */     removeDouble(key);
/* 310 */     return true;
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
/*     */   default boolean replace(K key, double oldValue, double newValue) {
/* 329 */     double curValue = getDouble(key);
/* 330 */     if (Double.doubleToLongBits(curValue) != Double.doubleToLongBits(oldValue) || (curValue == 
/* 331 */       defaultReturnValue() && !containsKey(key)))
/* 332 */       return false; 
/* 333 */     put(key, newValue);
/* 334 */     return true;
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
/*     */   default double replace(K key, double value) {
/* 353 */     return containsKey(key) ? put(key, value) : defaultReturnValue();
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
/*     */   default double computeDoubleIfAbsent(K key, ToDoubleFunction<? super K> mappingFunction) {
/* 380 */     Objects.requireNonNull(mappingFunction);
/* 381 */     double v = getDouble(key);
/* 382 */     if (v != defaultReturnValue() || containsKey(key))
/* 383 */       return v; 
/* 384 */     double newValue = mappingFunction.applyAsDouble(key);
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
/*     */   
/*     */   default double computeDoubleIfAbsentPartial(K key, Reference2DoubleFunction<? super K> mappingFunction) {
/* 416 */     Objects.requireNonNull(mappingFunction);
/* 417 */     double v = getDouble(key), drv = defaultReturnValue();
/* 418 */     if (v != drv || containsKey(key))
/* 419 */       return v; 
/* 420 */     if (!mappingFunction.containsKey(key))
/* 421 */       return drv; 
/* 422 */     double newValue = mappingFunction.getDouble(key);
/* 423 */     put(key, newValue);
/* 424 */     return newValue;
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
/*     */   default double computeDoubleIfPresent(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/* 443 */     Objects.requireNonNull(remappingFunction);
/* 444 */     double oldValue = getDouble(key), drv = defaultReturnValue();
/* 445 */     if (oldValue == drv && !containsKey(key))
/* 446 */       return drv; 
/* 447 */     Double newValue = remappingFunction.apply(key, Double.valueOf(oldValue));
/* 448 */     if (newValue == null) {
/* 449 */       removeDouble(key);
/* 450 */       return drv;
/*     */     } 
/* 452 */     double newVal = newValue.doubleValue();
/* 453 */     put(key, newVal);
/* 454 */     return newVal;
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
/*     */   default double computeDouble(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/* 479 */     Objects.requireNonNull(remappingFunction);
/* 480 */     double oldValue = getDouble(key), drv = defaultReturnValue();
/* 481 */     boolean contained = (oldValue != drv || containsKey(key));
/* 482 */     Double newValue = remappingFunction.apply(key, contained ? Double.valueOf(oldValue) : null);
/* 483 */     if (newValue == null) {
/* 484 */       if (contained)
/* 485 */         removeDouble(key); 
/* 486 */       return drv;
/*     */     } 
/* 488 */     double newVal = newValue.doubleValue();
/* 489 */     put(key, newVal);
/* 490 */     return newVal;
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
/*     */   default double mergeDouble(K key, double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*     */     double newValue;
/* 516 */     Objects.requireNonNull(remappingFunction);
/* 517 */     double oldValue = getDouble(key), drv = defaultReturnValue();
/*     */     
/* 519 */     if (oldValue != drv || containsKey(key)) {
/* 520 */       Double mergedValue = remappingFunction.apply(Double.valueOf(oldValue), Double.valueOf(value));
/* 521 */       if (mergedValue == null) {
/* 522 */         removeDouble(key);
/* 523 */         return drv;
/*     */       } 
/* 525 */       newValue = mergedValue.doubleValue();
/*     */     } else {
/* 527 */       newValue = value;
/*     */     } 
/* 529 */     put(key, newValue);
/* 530 */     return newValue;
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
/*     */   default Double getOrDefault(Object key, Double defaultValue) {
/* 543 */     return super.getOrDefault(key, defaultValue);
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
/*     */   default Double putIfAbsent(K key, Double value) {
/* 556 */     return super.putIfAbsent(key, value);
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
/* 569 */     return super.remove(key, value);
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
/*     */   default boolean replace(K key, Double oldValue, Double newValue) {
/* 582 */     return super.replace(key, oldValue, newValue);
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
/*     */   default Double replace(K key, Double value) {
/* 595 */     return super.replace(key, value);
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
/*     */   default Double merge(K key, Double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 609 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   int size();
/*     */ 
/*     */   
/*     */   void defaultReturnValue(double paramDouble);
/*     */ 
/*     */   
/*     */   double defaultReturnValue();
/*     */ 
/*     */   
/*     */   ObjectSet<Entry<K>> reference2DoubleEntrySet();
/*     */ 
/*     */   
/*     */   ReferenceSet<K> keySet();
/*     */ 
/*     */   
/*     */   DoubleCollection values();
/*     */   
/*     */   boolean containsKey(Object paramObject);
/*     */   
/*     */   boolean containsValue(double paramDouble);
/*     */   
/*     */   public static interface Entry<K>
/*     */     extends Map.Entry<K, Double>
/*     */   {
/*     */     @Deprecated
/*     */     default Double getValue() {
/* 639 */       return Double.valueOf(getDoubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Double setValue(Double value) {
/* 649 */       return Double.valueOf(setValue(value.doubleValue()));
/*     */     }
/*     */     
/*     */     double getDoubleValue();
/*     */     
/*     */     double setValue(double param1Double);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2DoubleMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */