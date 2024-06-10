/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
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
/*     */ 
/*     */ public interface Char2DoubleMap
/*     */   extends Char2DoubleFunction, Map<Character, Double>
/*     */ {
/*     */   public static interface FastEntrySet
/*     */     extends ObjectSet<Entry>
/*     */   {
/*     */     ObjectIterator<Char2DoubleMap.Entry> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Char2DoubleMap.Entry> consumer) {
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
/*     */   default ObjectSet<Map.Entry<Character, Double>> entrySet() {
/* 153 */     return (ObjectSet)char2DoubleEntrySet();
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
/*     */   default Double put(Character key, Double value) {
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
/* 258 */     return (value == null) ? false : containsValue(((Double)value).doubleValue());
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
/*     */   default double getOrDefault(char key, double defaultValue) {
/*     */     double v;
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
/*     */   default double putIfAbsent(char key, double value) {
/* 298 */     double v = get(key), drv = defaultReturnValue();
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
/*     */   default boolean remove(char key, double value) {
/* 319 */     double curValue = get(key);
/* 320 */     if (Double.doubleToLongBits(curValue) != Double.doubleToLongBits(value) || (curValue == 
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
/*     */   default boolean replace(char key, double oldValue, double newValue) {
/* 343 */     double curValue = get(key);
/* 344 */     if (Double.doubleToLongBits(curValue) != Double.doubleToLongBits(oldValue) || (curValue == 
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
/*     */   default double replace(char key, double value) {
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
/*     */   default double computeIfAbsent(char key, IntToDoubleFunction mappingFunction) {
/* 393 */     Objects.requireNonNull(mappingFunction);
/* 394 */     double v = get(key);
/* 395 */     if (v != defaultReturnValue() || containsKey(key))
/* 396 */       return v; 
/* 397 */     double newValue = mappingFunction.applyAsDouble(key);
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
/*     */   default double computeIfAbsentNullable(char key, IntFunction<? extends Double> mappingFunction) {
/* 426 */     Objects.requireNonNull(mappingFunction);
/* 427 */     double v = get(key), drv = defaultReturnValue();
/* 428 */     if (v != drv || containsKey(key))
/* 429 */       return v; 
/* 430 */     Double mappedValue = mappingFunction.apply(key);
/* 431 */     if (mappedValue == null)
/* 432 */       return drv; 
/* 433 */     double newValue = mappedValue.doubleValue();
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
/*     */   default double computeIfAbsentPartial(char key, Char2DoubleFunction mappingFunction) {
/* 464 */     Objects.requireNonNull(mappingFunction);
/* 465 */     double v = get(key), drv = defaultReturnValue();
/* 466 */     if (v != drv || containsKey(key))
/* 467 */       return v; 
/* 468 */     if (!mappingFunction.containsKey(key))
/* 469 */       return drv; 
/* 470 */     double newValue = mappingFunction.get(key);
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
/*     */   default double computeIfPresent(char key, BiFunction<? super Character, ? super Double, ? extends Double> remappingFunction) {
/* 491 */     Objects.requireNonNull(remappingFunction);
/* 492 */     double oldValue = get(key), drv = defaultReturnValue();
/* 493 */     if (oldValue == drv && !containsKey(key))
/* 494 */       return drv; 
/* 495 */     Double newValue = remappingFunction.apply(Character.valueOf(key), Double.valueOf(oldValue));
/* 496 */     if (newValue == null) {
/* 497 */       remove(key);
/* 498 */       return drv;
/*     */     } 
/* 500 */     double newVal = newValue.doubleValue();
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
/*     */   default double compute(char key, BiFunction<? super Character, ? super Double, ? extends Double> remappingFunction) {
/* 527 */     Objects.requireNonNull(remappingFunction);
/* 528 */     double oldValue = get(key), drv = defaultReturnValue();
/* 529 */     boolean contained = (oldValue != drv || containsKey(key));
/* 530 */     Double newValue = remappingFunction.apply(Character.valueOf(key), 
/* 531 */         contained ? Double.valueOf(oldValue) : null);
/* 532 */     if (newValue == null) {
/* 533 */       if (contained)
/* 534 */         remove(key); 
/* 535 */       return drv;
/*     */     } 
/* 537 */     double newVal = newValue.doubleValue();
/* 538 */     put(key, newVal);
/* 539 */     return newVal;
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
/*     */   default double merge(char key, double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*     */     double newValue;
/* 565 */     Objects.requireNonNull(remappingFunction);
/* 566 */     double oldValue = get(key), drv = defaultReturnValue();
/*     */     
/* 568 */     if (oldValue != drv || containsKey(key)) {
/* 569 */       Double mergedValue = remappingFunction.apply(Double.valueOf(oldValue), Double.valueOf(value));
/* 570 */       if (mergedValue == null) {
/* 571 */         remove(key);
/* 572 */         return drv;
/*     */       } 
/* 574 */       newValue = mergedValue.doubleValue();
/*     */     } else {
/* 576 */       newValue = value;
/*     */     } 
/* 578 */     put(key, newValue);
/* 579 */     return newValue;
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
/* 592 */     return super.getOrDefault(key, defaultValue);
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
/*     */   default Double putIfAbsent(Character key, Double value) {
/* 605 */     return super.putIfAbsent(key, value);
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
/* 618 */     return super.remove(key, value);
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
/*     */   default boolean replace(Character key, Double oldValue, Double newValue) {
/* 631 */     return super.replace(key, oldValue, newValue);
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
/*     */   default Double replace(Character key, Double value) {
/* 644 */     return super.replace(key, value);
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
/*     */   default Double computeIfAbsent(Character key, Function<? super Character, ? extends Double> mappingFunction) {
/* 658 */     return super.computeIfAbsent(key, mappingFunction);
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
/*     */   default Double computeIfPresent(Character key, BiFunction<? super Character, ? super Double, ? extends Double> remappingFunction) {
/* 672 */     return super.computeIfPresent(key, remappingFunction);
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
/*     */   default Double compute(Character key, BiFunction<? super Character, ? super Double, ? extends Double> remappingFunction) {
/* 686 */     return super.compute(key, remappingFunction);
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
/*     */   default Double merge(Character key, Double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 700 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */   
/*     */   int size();
/*     */   
/*     */   void defaultReturnValue(double paramDouble);
/*     */   
/*     */   double defaultReturnValue();
/*     */   
/*     */   ObjectSet<Entry> char2DoubleEntrySet();
/*     */   
/*     */   CharSet keySet();
/*     */   
/*     */   DoubleCollection values();
/*     */   
/*     */   boolean containsKey(char paramChar);
/*     */   
/*     */   boolean containsValue(double paramDouble);
/*     */   
/*     */   public static interface Entry
/*     */     extends Map.Entry<Character, Double> {
/*     */     @Deprecated
/*     */     default Character getKey() {
/* 723 */       return Character.valueOf(getCharKey());
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
/*     */     default Double getValue() {
/* 746 */       return Double.valueOf(getDoubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Double setValue(Double value) {
/* 756 */       return Double.valueOf(setValue(value.doubleValue()));
/*     */     }
/*     */     
/*     */     char getCharKey();
/*     */     
/*     */     double getDoubleValue();
/*     */     
/*     */     double setValue(double param1Double);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2DoubleMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */