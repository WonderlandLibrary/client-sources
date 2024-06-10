/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.DoubleFunction;
/*     */ import java.util.function.DoubleToIntFunction;
/*     */ import java.util.function.Function;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Double2CharMap
/*     */   extends Double2CharFunction, Map<Double, Character>
/*     */ {
/*     */   public static interface FastEntrySet
/*     */     extends ObjectSet<Entry>
/*     */   {
/*     */     ObjectIterator<Double2CharMap.Entry> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Double2CharMap.Entry> consumer) {
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
/*     */   default ObjectSet<Map.Entry<Double, Character>> entrySet() {
/* 153 */     return (ObjectSet)double2CharEntrySet();
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
/*     */   default Character put(Double key, Character value) {
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
/*     */   default Character get(Object key) {
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
/*     */   default Character remove(Object key) {
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
/* 258 */     return (value == null) ? false : containsValue(((Character)value).charValue());
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
/*     */   default char getOrDefault(double key, char defaultValue) {
/*     */     char v;
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
/*     */   default char putIfAbsent(double key, char value) {
/* 298 */     char v = get(key), drv = defaultReturnValue();
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
/*     */   default boolean remove(double key, char value) {
/* 319 */     char curValue = get(key);
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
/*     */   default boolean replace(double key, char oldValue, char newValue) {
/* 342 */     char curValue = get(key);
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
/*     */   default char replace(double key, char value) {
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
/*     */   default char computeIfAbsent(double key, DoubleToIntFunction mappingFunction) {
/* 391 */     Objects.requireNonNull(mappingFunction);
/* 392 */     char v = get(key);
/* 393 */     if (v != defaultReturnValue() || containsKey(key))
/* 394 */       return v; 
/* 395 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(key));
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
/*     */   default char computeIfAbsentNullable(double key, DoubleFunction<? extends Character> mappingFunction) {
/* 424 */     Objects.requireNonNull(mappingFunction);
/* 425 */     char v = get(key), drv = defaultReturnValue();
/* 426 */     if (v != drv || containsKey(key))
/* 427 */       return v; 
/* 428 */     Character mappedValue = mappingFunction.apply(key);
/* 429 */     if (mappedValue == null)
/* 430 */       return drv; 
/* 431 */     char newValue = mappedValue.charValue();
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
/*     */   default char computeIfAbsentPartial(double key, Double2CharFunction mappingFunction) {
/* 462 */     Objects.requireNonNull(mappingFunction);
/* 463 */     char v = get(key), drv = defaultReturnValue();
/* 464 */     if (v != drv || containsKey(key))
/* 465 */       return v; 
/* 466 */     if (!mappingFunction.containsKey(key))
/* 467 */       return drv; 
/* 468 */     char newValue = mappingFunction.get(key);
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
/*     */   default char computeIfPresent(double key, BiFunction<? super Double, ? super Character, ? extends Character> remappingFunction) {
/* 489 */     Objects.requireNonNull(remappingFunction);
/* 490 */     char oldValue = get(key), drv = defaultReturnValue();
/* 491 */     if (oldValue == drv && !containsKey(key))
/* 492 */       return drv; 
/* 493 */     Character newValue = remappingFunction.apply(Double.valueOf(key), Character.valueOf(oldValue));
/* 494 */     if (newValue == null) {
/* 495 */       remove(key);
/* 496 */       return drv;
/*     */     } 
/* 498 */     char newVal = newValue.charValue();
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
/*     */   default char compute(double key, BiFunction<? super Double, ? super Character, ? extends Character> remappingFunction) {
/* 525 */     Objects.requireNonNull(remappingFunction);
/* 526 */     char oldValue = get(key), drv = defaultReturnValue();
/* 527 */     boolean contained = (oldValue != drv || containsKey(key));
/* 528 */     Character newValue = remappingFunction.apply(Double.valueOf(key), 
/* 529 */         contained ? Character.valueOf(oldValue) : null);
/* 530 */     if (newValue == null) {
/* 531 */       if (contained)
/* 532 */         remove(key); 
/* 533 */       return drv;
/*     */     } 
/* 535 */     char newVal = newValue.charValue();
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
/*     */   default char merge(double key, char value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*     */     char newValue;
/* 563 */     Objects.requireNonNull(remappingFunction);
/* 564 */     char oldValue = get(key), drv = defaultReturnValue();
/*     */     
/* 566 */     if (oldValue != drv || containsKey(key)) {
/* 567 */       Character mergedValue = remappingFunction.apply(Character.valueOf(oldValue), 
/* 568 */           Character.valueOf(value));
/* 569 */       if (mergedValue == null) {
/* 570 */         remove(key);
/* 571 */         return drv;
/*     */       } 
/* 573 */       newValue = mergedValue.charValue();
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
/*     */   default Character getOrDefault(Object key, Character defaultValue) {
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
/*     */   default Character putIfAbsent(Double key, Character value) {
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
/*     */   default boolean replace(Double key, Character oldValue, Character newValue) {
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
/*     */   default Character replace(Double key, Character value) {
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
/*     */   default Character computeIfAbsent(Double key, Function<? super Double, ? extends Character> mappingFunction) {
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
/*     */   default Character computeIfPresent(Double key, BiFunction<? super Double, ? super Character, ? extends Character> remappingFunction) {
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
/*     */   default Character compute(Double key, BiFunction<? super Double, ? super Character, ? extends Character> remappingFunction) {
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
/*     */   default Character merge(Double key, Character value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/* 699 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */   
/*     */   int size();
/*     */   
/*     */   void defaultReturnValue(char paramChar);
/*     */   
/*     */   char defaultReturnValue();
/*     */   
/*     */   ObjectSet<Entry> double2CharEntrySet();
/*     */   
/*     */   DoubleSet keySet();
/*     */   
/*     */   CharCollection values();
/*     */   
/*     */   boolean containsKey(double paramDouble);
/*     */   
/*     */   boolean containsValue(char paramChar);
/*     */   
/*     */   public static interface Entry
/*     */     extends Map.Entry<Double, Character> {
/*     */     @Deprecated
/*     */     default Double getKey() {
/* 722 */       return Double.valueOf(getDoubleKey());
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
/*     */     default Character getValue() {
/* 745 */       return Character.valueOf(getCharValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Character setValue(Character value) {
/* 755 */       return Character.valueOf(setValue(value.charValue()));
/*     */     }
/*     */     
/*     */     double getDoubleKey();
/*     */     
/*     */     char getCharValue();
/*     */     
/*     */     char setValue(char param1Char);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2CharMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */