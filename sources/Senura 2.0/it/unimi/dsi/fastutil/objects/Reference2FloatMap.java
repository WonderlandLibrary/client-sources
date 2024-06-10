/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollection;
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
/*     */ public interface Reference2FloatMap<K>
/*     */   extends Reference2FloatFunction<K>, Map<K, Float>
/*     */ {
/*     */   public static interface FastEntrySet<K>
/*     */     extends ObjectSet<Entry<K>>
/*     */   {
/*     */     ObjectIterator<Reference2FloatMap.Entry<K>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Reference2FloatMap.Entry<K>> consumer) {
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
/*     */   default ObjectSet<Map.Entry<K, Float>> entrySet() {
/* 153 */     return (ObjectSet)reference2FloatEntrySet();
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
/*     */   default Float put(K key, Float value) {
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
/*     */   default Float get(Object key) {
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
/*     */   default Float remove(Object key) {
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
/* 244 */     return (value == null) ? false : containsValue(((Float)value).floatValue());
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
/*     */   default float getOrDefault(Object key, float defaultValue) {
/*     */     float v;
/* 264 */     return ((v = getFloat(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default float putIfAbsent(K key, float value) {
/* 284 */     float v = getFloat(key), drv = defaultReturnValue();
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
/*     */   default boolean remove(Object key, float value) {
/* 305 */     float curValue = getFloat(key);
/* 306 */     if (Float.floatToIntBits(curValue) != Float.floatToIntBits(value) || (curValue == 
/* 307 */       defaultReturnValue() && !containsKey(key)))
/* 308 */       return false; 
/* 309 */     removeFloat(key);
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
/*     */   default boolean replace(K key, float oldValue, float newValue) {
/* 329 */     float curValue = getFloat(key);
/* 330 */     if (Float.floatToIntBits(curValue) != Float.floatToIntBits(oldValue) || (curValue == 
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
/*     */   default float replace(K key, float value) {
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
/*     */   default float computeFloatIfAbsent(K key, ToDoubleFunction<? super K> mappingFunction) {
/* 380 */     Objects.requireNonNull(mappingFunction);
/* 381 */     float v = getFloat(key);
/* 382 */     if (v != defaultReturnValue() || containsKey(key))
/* 383 */       return v; 
/* 384 */     float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(key));
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
/*     */   default float computeFloatIfAbsentPartial(K key, Reference2FloatFunction<? super K> mappingFunction) {
/* 415 */     Objects.requireNonNull(mappingFunction);
/* 416 */     float v = getFloat(key), drv = defaultReturnValue();
/* 417 */     if (v != drv || containsKey(key))
/* 418 */       return v; 
/* 419 */     if (!mappingFunction.containsKey(key))
/* 420 */       return drv; 
/* 421 */     float newValue = mappingFunction.getFloat(key);
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
/*     */   default float computeFloatIfPresent(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/* 442 */     Objects.requireNonNull(remappingFunction);
/* 443 */     float oldValue = getFloat(key), drv = defaultReturnValue();
/* 444 */     if (oldValue == drv && !containsKey(key))
/* 445 */       return drv; 
/* 446 */     Float newValue = remappingFunction.apply(key, Float.valueOf(oldValue));
/* 447 */     if (newValue == null) {
/* 448 */       removeFloat(key);
/* 449 */       return drv;
/*     */     } 
/* 451 */     float newVal = newValue.floatValue();
/* 452 */     put(key, newVal);
/* 453 */     return newVal;
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
/*     */   default float computeFloat(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/* 478 */     Objects.requireNonNull(remappingFunction);
/* 479 */     float oldValue = getFloat(key), drv = defaultReturnValue();
/* 480 */     boolean contained = (oldValue != drv || containsKey(key));
/* 481 */     Float newValue = remappingFunction.apply(key, contained ? Float.valueOf(oldValue) : null);
/* 482 */     if (newValue == null) {
/* 483 */       if (contained)
/* 484 */         removeFloat(key); 
/* 485 */       return drv;
/*     */     } 
/* 487 */     float newVal = newValue.floatValue();
/* 488 */     put(key, newVal);
/* 489 */     return newVal;
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
/*     */   default float mergeFloat(K key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*     */     float newValue;
/* 515 */     Objects.requireNonNull(remappingFunction);
/* 516 */     float oldValue = getFloat(key), drv = defaultReturnValue();
/*     */     
/* 518 */     if (oldValue != drv || containsKey(key)) {
/* 519 */       Float mergedValue = remappingFunction.apply(Float.valueOf(oldValue), Float.valueOf(value));
/* 520 */       if (mergedValue == null) {
/* 521 */         removeFloat(key);
/* 522 */         return drv;
/*     */       } 
/* 524 */       newValue = mergedValue.floatValue();
/*     */     } else {
/* 526 */       newValue = value;
/*     */     } 
/* 528 */     put(key, newValue);
/* 529 */     return newValue;
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
/*     */   default Float getOrDefault(Object key, Float defaultValue) {
/* 542 */     return super.getOrDefault(key, defaultValue);
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
/*     */   default Float putIfAbsent(K key, Float value) {
/* 555 */     return super.putIfAbsent(key, value);
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
/* 568 */     return super.remove(key, value);
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
/*     */   default boolean replace(K key, Float oldValue, Float newValue) {
/* 581 */     return super.replace(key, oldValue, newValue);
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
/*     */   default Float replace(K key, Float value) {
/* 594 */     return super.replace(key, value);
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
/*     */   default Float merge(K key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/* 608 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   int size();
/*     */ 
/*     */   
/*     */   void defaultReturnValue(float paramFloat);
/*     */ 
/*     */   
/*     */   float defaultReturnValue();
/*     */ 
/*     */   
/*     */   ObjectSet<Entry<K>> reference2FloatEntrySet();
/*     */ 
/*     */   
/*     */   ReferenceSet<K> keySet();
/*     */ 
/*     */   
/*     */   FloatCollection values();
/*     */   
/*     */   boolean containsKey(Object paramObject);
/*     */   
/*     */   boolean containsValue(float paramFloat);
/*     */   
/*     */   public static interface Entry<K>
/*     */     extends Map.Entry<K, Float>
/*     */   {
/*     */     @Deprecated
/*     */     default Float getValue() {
/* 638 */       return Float.valueOf(getFloatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Float setValue(Float value) {
/* 648 */       return Float.valueOf(setValue(value.floatValue()));
/*     */     }
/*     */     
/*     */     float getFloatValue();
/*     */     
/*     */     float setValue(float param1Float);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2FloatMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */