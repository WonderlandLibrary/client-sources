/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Char2ReferenceMap<V>
/*     */   extends Char2ReferenceFunction<V>, Map<Character, V>
/*     */ {
/*     */   public static interface FastEntrySet<V>
/*     */     extends ObjectSet<Entry<V>>
/*     */   {
/*     */     ObjectIterator<Char2ReferenceMap.Entry<V>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Char2ReferenceMap.Entry<V>> consumer) {
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default ObjectSet<Map.Entry<Character, V>> entrySet() {
/* 159 */     return (ObjectSet)char2ReferenceEntrySet();
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
/*     */   default V put(Character key, V value) {
/* 173 */     return super.put(key, value);
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
/*     */   default V get(Object key) {
/* 187 */     return super.get(key);
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
/*     */   default V remove(Object key) {
/* 201 */     return super.remove(key);
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
/* 247 */     return super.containsKey(key);
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
/*     */   default V getOrDefault(char key, V defaultValue) {
/*     */     V v;
/* 267 */     return ((v = get(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default V putIfAbsent(char key, V value) {
/* 287 */     V v = get(key), drv = defaultReturnValue();
/* 288 */     if (v != drv || containsKey(key))
/* 289 */       return v; 
/* 290 */     put(key, value);
/* 291 */     return drv;
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
/*     */   default boolean remove(char key, Object value) {
/* 308 */     V curValue = get(key);
/* 309 */     if (curValue != value || (curValue == defaultReturnValue() && !containsKey(key)))
/* 310 */       return false; 
/* 311 */     remove(key);
/* 312 */     return true;
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
/*     */   default boolean replace(char key, V oldValue, V newValue) {
/* 331 */     V curValue = get(key);
/* 332 */     if (curValue != oldValue || (curValue == defaultReturnValue() && !containsKey(key)))
/* 333 */       return false; 
/* 334 */     put(key, newValue);
/* 335 */     return true;
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
/*     */   default V replace(char key, V value) {
/* 354 */     return containsKey(key) ? put(key, value) : defaultReturnValue();
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
/*     */   default V computeIfAbsent(char key, IntFunction<? extends V> mappingFunction) {
/* 380 */     Objects.requireNonNull(mappingFunction);
/* 381 */     V v = get(key);
/* 382 */     if (v != defaultReturnValue() || containsKey(key))
/* 383 */       return v; 
/* 384 */     V newValue = mappingFunction.apply(key);
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
/*     */   default V computeIfAbsentPartial(char key, Char2ReferenceFunction<? extends V> mappingFunction) {
/* 415 */     Objects.requireNonNull(mappingFunction);
/* 416 */     V v = get(key), drv = defaultReturnValue();
/* 417 */     if (v != drv || containsKey(key))
/* 418 */       return v; 
/* 419 */     if (!mappingFunction.containsKey(key))
/* 420 */       return drv; 
/* 421 */     V newValue = mappingFunction.get(key);
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
/*     */   default V computeIfPresent(char key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 442 */     Objects.requireNonNull(remappingFunction);
/* 443 */     V oldValue = get(key), drv = defaultReturnValue();
/* 444 */     if (oldValue == drv && !containsKey(key))
/* 445 */       return drv; 
/* 446 */     V newValue = remappingFunction.apply(Character.valueOf(key), oldValue);
/* 447 */     if (newValue == null) {
/* 448 */       remove(key);
/* 449 */       return drv;
/*     */     } 
/* 451 */     put(key, newValue);
/* 452 */     return newValue;
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
/*     */   default V compute(char key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 477 */     Objects.requireNonNull(remappingFunction);
/* 478 */     V oldValue = get(key), drv = defaultReturnValue();
/* 479 */     boolean contained = (oldValue != drv || containsKey(key));
/* 480 */     V newValue = remappingFunction.apply(Character.valueOf(key), contained ? oldValue : null);
/* 481 */     if (newValue == null) {
/* 482 */       if (contained)
/* 483 */         remove(key); 
/* 484 */       return drv;
/*     */     } 
/* 486 */     put(key, newValue);
/* 487 */     return newValue;
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
/*     */   default V merge(char key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*     */     V newValue;
/* 513 */     Objects.requireNonNull(remappingFunction);
/* 514 */     Objects.requireNonNull(value);
/* 515 */     V oldValue = get(key), drv = defaultReturnValue();
/*     */     
/* 517 */     if (oldValue != drv || containsKey(key)) {
/* 518 */       V mergedValue = remappingFunction.apply(oldValue, value);
/* 519 */       if (mergedValue == null) {
/* 520 */         remove(key);
/* 521 */         return drv;
/*     */       } 
/* 523 */       newValue = mergedValue;
/*     */     } else {
/* 525 */       newValue = value;
/*     */     } 
/* 527 */     put(key, newValue);
/* 528 */     return newValue;
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
/*     */   default V getOrDefault(Object key, V defaultValue) {
/* 541 */     return super.getOrDefault(key, defaultValue);
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
/*     */   default V putIfAbsent(Character key, V value) {
/* 554 */     return super.putIfAbsent(key, value);
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
/* 567 */     return super.remove(key, value);
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
/*     */   default boolean replace(Character key, V oldValue, V newValue) {
/* 580 */     return super.replace(key, oldValue, newValue);
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
/*     */   default V replace(Character key, V value) {
/* 593 */     return super.replace(key, value);
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
/*     */   default V computeIfAbsent(Character key, Function<? super Character, ? extends V> mappingFunction) {
/* 607 */     return super.computeIfAbsent(key, mappingFunction);
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
/*     */   default V computeIfPresent(Character key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 621 */     return super.computeIfPresent(key, remappingFunction);
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
/*     */   default V compute(Character key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 635 */     return super.compute(key, remappingFunction);
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
/*     */   default V merge(Character key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 649 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   int size();
/*     */   
/*     */   void defaultReturnValue(V paramV);
/*     */   
/*     */   V defaultReturnValue();
/*     */   
/*     */   ObjectSet<Entry<V>> char2ReferenceEntrySet();
/*     */   
/*     */   CharSet keySet();
/*     */   
/*     */   ReferenceCollection<V> values();
/*     */   
/*     */   boolean containsKey(char paramChar);
/*     */   
/*     */   public static interface Entry<V>
/*     */     extends Map.Entry<Character, V>
/*     */   {
/*     */     @Deprecated
/*     */     default Character getKey() {
/* 672 */       return Character.valueOf(getCharKey());
/*     */     }
/*     */     
/*     */     char getCharKey();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2ReferenceMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */