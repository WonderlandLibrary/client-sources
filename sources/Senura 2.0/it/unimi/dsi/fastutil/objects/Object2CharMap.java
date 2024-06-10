/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.chars.CharCollection;
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
/*     */ public interface Object2CharMap<K>
/*     */   extends Object2CharFunction<K>, Map<K, Character>
/*     */ {
/*     */   public static interface FastEntrySet<K>
/*     */     extends ObjectSet<Entry<K>>
/*     */   {
/*     */     ObjectIterator<Object2CharMap.Entry<K>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Object2CharMap.Entry<K>> consumer) {
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
/*     */   default ObjectSet<Map.Entry<K, Character>> entrySet() {
/* 151 */     return (ObjectSet)object2CharEntrySet();
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
/*     */   default Character put(K key, Character value) {
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
/*     */   default Character get(Object key) {
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
/*     */   default Character remove(Object key) {
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
/* 242 */     return (value == null) ? false : containsValue(((Character)value).charValue());
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
/*     */   default char getOrDefault(Object key, char defaultValue) {
/*     */     char v;
/* 262 */     return ((v = getChar(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default char putIfAbsent(K key, char value) {
/* 282 */     char v = getChar(key), drv = defaultReturnValue();
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
/*     */   default boolean remove(Object key, char value) {
/* 303 */     char curValue = getChar(key);
/* 304 */     if (curValue != value || (curValue == defaultReturnValue() && !containsKey(key)))
/* 305 */       return false; 
/* 306 */     removeChar(key);
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
/*     */   default boolean replace(K key, char oldValue, char newValue) {
/* 326 */     char curValue = getChar(key);
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
/*     */   default char replace(K key, char value) {
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
/*     */   default char computeCharIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
/* 375 */     Objects.requireNonNull(mappingFunction);
/* 376 */     char v = getChar(key);
/* 377 */     if (v != defaultReturnValue() || containsKey(key))
/* 378 */       return v; 
/* 379 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(key));
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
/*     */   default char computeCharIfAbsentPartial(K key, Object2CharFunction<? super K> mappingFunction) {
/* 410 */     Objects.requireNonNull(mappingFunction);
/* 411 */     char v = getChar(key), drv = defaultReturnValue();
/* 412 */     if (v != drv || containsKey(key))
/* 413 */       return v; 
/* 414 */     if (!mappingFunction.containsKey(key))
/* 415 */       return drv; 
/* 416 */     char newValue = mappingFunction.getChar(key);
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
/*     */   default char computeCharIfPresent(K key, BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
/* 437 */     Objects.requireNonNull(remappingFunction);
/* 438 */     char oldValue = getChar(key), drv = defaultReturnValue();
/* 439 */     if (oldValue == drv && !containsKey(key))
/* 440 */       return drv; 
/* 441 */     Character newValue = remappingFunction.apply(key, Character.valueOf(oldValue));
/* 442 */     if (newValue == null) {
/* 443 */       removeChar(key);
/* 444 */       return drv;
/*     */     } 
/* 446 */     char newVal = newValue.charValue();
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
/*     */   default char computeChar(K key, BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
/* 473 */     Objects.requireNonNull(remappingFunction);
/* 474 */     char oldValue = getChar(key), drv = defaultReturnValue();
/* 475 */     boolean contained = (oldValue != drv || containsKey(key));
/* 476 */     Character newValue = remappingFunction.apply(key, contained ? Character.valueOf(oldValue) : null);
/* 477 */     if (newValue == null) {
/* 478 */       if (contained)
/* 479 */         removeChar(key); 
/* 480 */       return drv;
/*     */     } 
/* 482 */     char newVal = newValue.charValue();
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
/*     */   default char mergeChar(K key, char value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*     */     char newValue;
/* 510 */     Objects.requireNonNull(remappingFunction);
/* 511 */     char oldValue = getChar(key), drv = defaultReturnValue();
/*     */     
/* 513 */     if (oldValue != drv || containsKey(key)) {
/* 514 */       Character mergedValue = remappingFunction.apply(Character.valueOf(oldValue), 
/* 515 */           Character.valueOf(value));
/* 516 */       if (mergedValue == null) {
/* 517 */         removeChar(key);
/* 518 */         return drv;
/*     */       } 
/* 520 */       newValue = mergedValue.charValue();
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
/*     */   default Character getOrDefault(Object key, Character defaultValue) {
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
/*     */   default Character putIfAbsent(K key, Character value) {
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
/*     */   default boolean replace(K key, Character oldValue, Character newValue) {
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
/*     */   default Character replace(K key, Character value) {
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
/*     */   default Character merge(K key, Character value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/* 604 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   int size();
/*     */ 
/*     */   
/*     */   void defaultReturnValue(char paramChar);
/*     */ 
/*     */   
/*     */   char defaultReturnValue();
/*     */ 
/*     */   
/*     */   ObjectSet<Entry<K>> object2CharEntrySet();
/*     */ 
/*     */   
/*     */   ObjectSet<K> keySet();
/*     */ 
/*     */   
/*     */   CharCollection values();
/*     */   
/*     */   boolean containsKey(Object paramObject);
/*     */   
/*     */   boolean containsValue(char paramChar);
/*     */   
/*     */   public static interface Entry<K>
/*     */     extends Map.Entry<K, Character>
/*     */   {
/*     */     @Deprecated
/*     */     default Character getValue() {
/* 634 */       return Character.valueOf(getCharValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Character setValue(Character value) {
/* 644 */       return Character.valueOf(setValue(value.charValue()));
/*     */     }
/*     */     
/*     */     char getCharValue();
/*     */     
/*     */     char setValue(char param1Char);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2CharMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */