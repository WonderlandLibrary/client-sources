/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.IntToDoubleFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Byte2FloatMap
/*     */   extends Byte2FloatFunction, Map<Byte, Float>
/*     */ {
/*     */   public static interface FastEntrySet
/*     */     extends ObjectSet<Entry>
/*     */   {
/*     */     ObjectIterator<Byte2FloatMap.Entry> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Byte2FloatMap.Entry> consumer) {
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
/*     */   default ObjectSet<Map.Entry<Byte, Float>> entrySet() {
/* 153 */     return (ObjectSet)byte2FloatEntrySet();
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
/*     */   default Float put(Byte key, Float value) {
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
/* 258 */     return (value == null) ? false : containsValue(((Float)value).floatValue());
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
/*     */   default float getOrDefault(byte key, float defaultValue) {
/*     */     float v;
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
/*     */   default float putIfAbsent(byte key, float value) {
/* 298 */     float v = get(key), drv = defaultReturnValue();
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
/*     */   default boolean remove(byte key, float value) {
/* 319 */     float curValue = get(key);
/* 320 */     if (Float.floatToIntBits(curValue) != Float.floatToIntBits(value) || (curValue == 
/* 321 */       defaultReturnValue() && !containsKey(key)))
/* 322 */       return false; 
/* 323 */     remove(key);
/* 324 */     return true;
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
/*     */   default boolean replace(byte key, float oldValue, float newValue) {
/* 343 */     float curValue = get(key);
/* 344 */     if (Float.floatToIntBits(curValue) != Float.floatToIntBits(oldValue) || (curValue == 
/* 345 */       defaultReturnValue() && !containsKey(key)))
/* 346 */       return false; 
/* 347 */     put(key, newValue);
/* 348 */     return true;
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
/*     */   default float replace(byte key, float value) {
/* 367 */     return containsKey(key) ? put(key, value) : defaultReturnValue();
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
/*     */   default float computeIfAbsent(byte key, IntToDoubleFunction mappingFunction) {
/* 393 */     Objects.requireNonNull(mappingFunction);
/* 394 */     float v = get(key);
/* 395 */     if (v != defaultReturnValue() || containsKey(key))
/* 396 */       return v; 
/* 397 */     float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(key));
/* 398 */     put(key, newValue);
/* 399 */     return newValue;
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
/*     */   default float computeIfAbsentNullable(byte key, IntFunction<? extends Float> mappingFunction) {
/* 426 */     Objects.requireNonNull(mappingFunction);
/* 427 */     float v = get(key), drv = defaultReturnValue();
/* 428 */     if (v != drv || containsKey(key))
/* 429 */       return v; 
/* 430 */     Float mappedValue = mappingFunction.apply(key);
/* 431 */     if (mappedValue == null)
/* 432 */       return drv; 
/* 433 */     float newValue = mappedValue.floatValue();
/* 434 */     put(key, newValue);
/* 435 */     return newValue;
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
/*     */   default float computeIfAbsentPartial(byte key, Byte2FloatFunction mappingFunction) {
/* 464 */     Objects.requireNonNull(mappingFunction);
/* 465 */     float v = get(key), drv = defaultReturnValue();
/* 466 */     if (v != drv || containsKey(key))
/* 467 */       return v; 
/* 468 */     if (!mappingFunction.containsKey(key))
/* 469 */       return drv; 
/* 470 */     float newValue = mappingFunction.get(key);
/* 471 */     put(key, newValue);
/* 472 */     return newValue;
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
/*     */   default float computeIfPresent(byte key, BiFunction<? super Byte, ? super Float, ? extends Float> remappingFunction) {
/* 491 */     Objects.requireNonNull(remappingFunction);
/* 492 */     float oldValue = get(key), drv = defaultReturnValue();
/* 493 */     if (oldValue == drv && !containsKey(key))
/* 494 */       return drv; 
/* 495 */     Float newValue = remappingFunction.apply(Byte.valueOf(key), Float.valueOf(oldValue));
/* 496 */     if (newValue == null) {
/* 497 */       remove(key);
/* 498 */       return drv;
/*     */     } 
/* 500 */     float newVal = newValue.floatValue();
/* 501 */     put(key, newVal);
/* 502 */     return newVal;
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
/*     */   default float compute(byte key, BiFunction<? super Byte, ? super Float, ? extends Float> remappingFunction) {
/* 527 */     Objects.requireNonNull(remappingFunction);
/* 528 */     float oldValue = get(key), drv = defaultReturnValue();
/* 529 */     boolean contained = (oldValue != drv || containsKey(key));
/* 530 */     Float newValue = remappingFunction.apply(Byte.valueOf(key), contained ? Float.valueOf(oldValue) : null);
/* 531 */     if (newValue == null) {
/* 532 */       if (contained)
/* 533 */         remove(key); 
/* 534 */       return drv;
/*     */     } 
/* 536 */     float newVal = newValue.floatValue();
/* 537 */     put(key, newVal);
/* 538 */     return newVal;
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
/*     */   default float merge(byte key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*     */     float newValue;
/* 564 */     Objects.requireNonNull(remappingFunction);
/* 565 */     float oldValue = get(key), drv = defaultReturnValue();
/*     */     
/* 567 */     if (oldValue != drv || containsKey(key)) {
/* 568 */       Float mergedValue = remappingFunction.apply(Float.valueOf(oldValue), Float.valueOf(value));
/* 569 */       if (mergedValue == null) {
/* 570 */         remove(key);
/* 571 */         return drv;
/*     */       } 
/* 573 */       newValue = mergedValue.floatValue();
/*     */     } else {
/* 575 */       newValue = value;
/*     */     } 
/* 577 */     put(key, newValue);
/* 578 */     return newValue;
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
/* 591 */     return super.getOrDefault(key, defaultValue);
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
/*     */   default Float putIfAbsent(Byte key, Float value) {
/* 604 */     return super.putIfAbsent(key, value);
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
/* 617 */     return super.remove(key, value);
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
/*     */   default boolean replace(Byte key, Float oldValue, Float newValue) {
/* 630 */     return super.replace(key, oldValue, newValue);
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
/*     */   default Float replace(Byte key, Float value) {
/* 643 */     return super.replace(key, value);
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
/*     */   default Float computeIfAbsent(Byte key, Function<? super Byte, ? extends Float> mappingFunction) {
/* 657 */     return super.computeIfAbsent(key, mappingFunction);
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
/*     */   default Float computeIfPresent(Byte key, BiFunction<? super Byte, ? super Float, ? extends Float> remappingFunction) {
/* 671 */     return super.computeIfPresent(key, remappingFunction);
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
/*     */   default Float compute(Byte key, BiFunction<? super Byte, ? super Float, ? extends Float> remappingFunction) {
/* 685 */     return super.compute(key, remappingFunction);
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
/*     */   default Float merge(Byte key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/* 699 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */   
/*     */   int size();
/*     */   
/*     */   void defaultReturnValue(float paramFloat);
/*     */   
/*     */   float defaultReturnValue();
/*     */   
/*     */   ObjectSet<Entry> byte2FloatEntrySet();
/*     */   
/*     */   ByteSet keySet();
/*     */   
/*     */   FloatCollection values();
/*     */   
/*     */   boolean containsKey(byte paramByte);
/*     */   
/*     */   boolean containsValue(float paramFloat);
/*     */   
/*     */   public static interface Entry
/*     */     extends Map.Entry<Byte, Float> {
/*     */     @Deprecated
/*     */     default Byte getKey() {
/* 722 */       return Byte.valueOf(getByteKey());
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
/*     */     default Float getValue() {
/* 745 */       return Float.valueOf(getFloatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Float setValue(Float value) {
/* 755 */       return Float.valueOf(setValue(value.floatValue()));
/*     */     }
/*     */     
/*     */     byte getByteKey();
/*     */     
/*     */     float getFloatValue();
/*     */     
/*     */     float setValue(float param1Float);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\Byte2FloatMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */