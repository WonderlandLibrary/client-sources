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
/*     */ public interface Object2DoubleMap<K>
/*     */   extends Object2DoubleFunction<K>, Map<K, Double>
/*     */ {
/*     */   public static interface FastEntrySet<K>
/*     */     extends ObjectSet<Entry<K>>
/*     */   {
/*     */     ObjectIterator<Object2DoubleMap.Entry<K>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Object2DoubleMap.Entry<K>> consumer) {
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
/*     */   default ObjectSet<Map.Entry<K, Double>> entrySet() {
/* 151 */     return (ObjectSet)object2DoubleEntrySet();
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
/*     */   default Double get(Object key) {
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
/*     */   default Double remove(Object key) {
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
/* 242 */     return (value == null) ? false : containsValue(((Double)value).doubleValue());
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
/* 262 */     return ((v = getDouble(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/* 282 */     double v = getDouble(key), drv = defaultReturnValue();
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
/*     */   default boolean remove(Object key, double value) {
/* 303 */     double curValue = getDouble(key);
/* 304 */     if (Double.doubleToLongBits(curValue) != Double.doubleToLongBits(value) || (curValue == 
/* 305 */       defaultReturnValue() && !containsKey(key)))
/* 306 */       return false; 
/* 307 */     removeDouble(key);
/* 308 */     return true;
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
/* 327 */     double curValue = getDouble(key);
/* 328 */     if (Double.doubleToLongBits(curValue) != Double.doubleToLongBits(oldValue) || (curValue == 
/* 329 */       defaultReturnValue() && !containsKey(key)))
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
/*     */   default double replace(K key, double value) {
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
/*     */   
/*     */   default double computeDoubleIfAbsent(K key, ToDoubleFunction<? super K> mappingFunction) {
/* 378 */     Objects.requireNonNull(mappingFunction);
/* 379 */     double v = getDouble(key);
/* 380 */     if (v != defaultReturnValue() || containsKey(key))
/* 381 */       return v; 
/* 382 */     double newValue = mappingFunction.applyAsDouble(key);
/* 383 */     put(key, newValue);
/* 384 */     return newValue;
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
/*     */   default double computeDoubleIfAbsentPartial(K key, Object2DoubleFunction<? super K> mappingFunction) {
/* 413 */     Objects.requireNonNull(mappingFunction);
/* 414 */     double v = getDouble(key), drv = defaultReturnValue();
/* 415 */     if (v != drv || containsKey(key))
/* 416 */       return v; 
/* 417 */     if (!mappingFunction.containsKey(key))
/* 418 */       return drv; 
/* 419 */     double newValue = mappingFunction.getDouble(key);
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
/*     */   default double computeDoubleIfPresent(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/* 440 */     Objects.requireNonNull(remappingFunction);
/* 441 */     double oldValue = getDouble(key), drv = defaultReturnValue();
/* 442 */     if (oldValue == drv && !containsKey(key))
/* 443 */       return drv; 
/* 444 */     Double newValue = remappingFunction.apply(key, Double.valueOf(oldValue));
/* 445 */     if (newValue == null) {
/* 446 */       removeDouble(key);
/* 447 */       return drv;
/*     */     } 
/* 449 */     double newVal = newValue.doubleValue();
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
/*     */   default double computeDouble(K key, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/* 476 */     Objects.requireNonNull(remappingFunction);
/* 477 */     double oldValue = getDouble(key), drv = defaultReturnValue();
/* 478 */     boolean contained = (oldValue != drv || containsKey(key));
/* 479 */     Double newValue = remappingFunction.apply(key, contained ? Double.valueOf(oldValue) : null);
/* 480 */     if (newValue == null) {
/* 481 */       if (contained)
/* 482 */         removeDouble(key); 
/* 483 */       return drv;
/*     */     } 
/* 485 */     double newVal = newValue.doubleValue();
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
/*     */   default double mergeDouble(K key, double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*     */     double newValue;
/* 513 */     Objects.requireNonNull(remappingFunction);
/* 514 */     double oldValue = getDouble(key), drv = defaultReturnValue();
/*     */     
/* 516 */     if (oldValue != drv || containsKey(key)) {
/* 517 */       Double mergedValue = remappingFunction.apply(Double.valueOf(oldValue), Double.valueOf(value));
/* 518 */       if (mergedValue == null) {
/* 519 */         removeDouble(key);
/* 520 */         return drv;
/*     */       } 
/* 522 */       newValue = mergedValue.doubleValue();
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
/*     */   default Double getOrDefault(Object key, Double defaultValue) {
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
/*     */   default Double putIfAbsent(K key, Double value) {
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
/*     */   default boolean replace(K key, Double oldValue, Double newValue) {
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
/*     */   default Double replace(K key, Double value) {
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
/*     */   default Double merge(K key, Double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 606 */     return super.merge(key, value, remappingFunction);
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
/*     */   ObjectSet<Entry<K>> object2DoubleEntrySet();
/*     */ 
/*     */   
/*     */   ObjectSet<K> keySet();
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
/* 636 */       return Double.valueOf(getDoubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Double setValue(Double value) {
/* 646 */       return Double.valueOf(setValue(value.doubleValue()));
/*     */     }
/*     */     
/*     */     double getDoubleValue();
/*     */     
/*     */     double setValue(double param1Double);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2DoubleMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */