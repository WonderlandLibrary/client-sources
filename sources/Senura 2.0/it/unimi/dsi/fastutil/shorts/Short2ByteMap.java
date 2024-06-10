/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
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
/*     */ import java.util.function.IntUnaryOperator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Short2ByteMap
/*     */   extends Short2ByteFunction, Map<Short, Byte>
/*     */ {
/*     */   public static interface FastEntrySet
/*     */     extends ObjectSet<Entry>
/*     */   {
/*     */     ObjectIterator<Short2ByteMap.Entry> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Short2ByteMap.Entry> consumer) {
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
/*     */   default ObjectSet<Map.Entry<Short, Byte>> entrySet() {
/* 153 */     return (ObjectSet)short2ByteEntrySet();
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
/*     */   default Byte put(Short key, Byte value) {
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
/*     */   default Byte get(Object key) {
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
/*     */   default Byte remove(Object key) {
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
/* 258 */     return (value == null) ? false : containsValue(((Byte)value).byteValue());
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
/*     */   default byte getOrDefault(short key, byte defaultValue) {
/*     */     byte v;
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
/*     */   default byte putIfAbsent(short key, byte value) {
/* 298 */     byte v = get(key), drv = defaultReturnValue();
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
/*     */   default boolean remove(short key, byte value) {
/* 319 */     byte curValue = get(key);
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
/*     */   default boolean replace(short key, byte oldValue, byte newValue) {
/* 342 */     byte curValue = get(key);
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
/*     */   default byte replace(short key, byte value) {
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
/*     */   default byte computeIfAbsent(short key, IntUnaryOperator mappingFunction) {
/* 391 */     Objects.requireNonNull(mappingFunction);
/* 392 */     byte v = get(key);
/* 393 */     if (v != defaultReturnValue() || containsKey(key))
/* 394 */       return v; 
/* 395 */     byte newValue = SafeMath.safeIntToByte(mappingFunction.applyAsInt(key));
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
/*     */   default byte computeIfAbsentNullable(short key, IntFunction<? extends Byte> mappingFunction) {
/* 424 */     Objects.requireNonNull(mappingFunction);
/* 425 */     byte v = get(key), drv = defaultReturnValue();
/* 426 */     if (v != drv || containsKey(key))
/* 427 */       return v; 
/* 428 */     Byte mappedValue = mappingFunction.apply(key);
/* 429 */     if (mappedValue == null)
/* 430 */       return drv; 
/* 431 */     byte newValue = mappedValue.byteValue();
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
/*     */   default byte computeIfAbsentPartial(short key, Short2ByteFunction mappingFunction) {
/* 462 */     Objects.requireNonNull(mappingFunction);
/* 463 */     byte v = get(key), drv = defaultReturnValue();
/* 464 */     if (v != drv || containsKey(key))
/* 465 */       return v; 
/* 466 */     if (!mappingFunction.containsKey(key))
/* 467 */       return drv; 
/* 468 */     byte newValue = mappingFunction.get(key);
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
/*     */   default byte computeIfPresent(short key, BiFunction<? super Short, ? super Byte, ? extends Byte> remappingFunction) {
/* 489 */     Objects.requireNonNull(remappingFunction);
/* 490 */     byte oldValue = get(key), drv = defaultReturnValue();
/* 491 */     if (oldValue == drv && !containsKey(key))
/* 492 */       return drv; 
/* 493 */     Byte newValue = remappingFunction.apply(Short.valueOf(key), Byte.valueOf(oldValue));
/* 494 */     if (newValue == null) {
/* 495 */       remove(key);
/* 496 */       return drv;
/*     */     } 
/* 498 */     byte newVal = newValue.byteValue();
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
/*     */   default byte compute(short key, BiFunction<? super Short, ? super Byte, ? extends Byte> remappingFunction) {
/* 525 */     Objects.requireNonNull(remappingFunction);
/* 526 */     byte oldValue = get(key), drv = defaultReturnValue();
/* 527 */     boolean contained = (oldValue != drv || containsKey(key));
/* 528 */     Byte newValue = remappingFunction.apply(Short.valueOf(key), contained ? Byte.valueOf(oldValue) : null);
/* 529 */     if (newValue == null) {
/* 530 */       if (contained)
/* 531 */         remove(key); 
/* 532 */       return drv;
/*     */     } 
/* 534 */     byte newVal = newValue.byteValue();
/* 535 */     put(key, newVal);
/* 536 */     return newVal;
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
/*     */   default byte merge(short key, byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*     */     byte newValue;
/* 562 */     Objects.requireNonNull(remappingFunction);
/* 563 */     byte oldValue = get(key), drv = defaultReturnValue();
/*     */     
/* 565 */     if (oldValue != drv || containsKey(key)) {
/* 566 */       Byte mergedValue = remappingFunction.apply(Byte.valueOf(oldValue), Byte.valueOf(value));
/* 567 */       if (mergedValue == null) {
/* 568 */         remove(key);
/* 569 */         return drv;
/*     */       } 
/* 571 */       newValue = mergedValue.byteValue();
/*     */     } else {
/* 573 */       newValue = value;
/*     */     } 
/* 575 */     put(key, newValue);
/* 576 */     return newValue;
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
/*     */   default Byte getOrDefault(Object key, Byte defaultValue) {
/* 589 */     return super.getOrDefault(key, defaultValue);
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
/*     */   default Byte putIfAbsent(Short key, Byte value) {
/* 602 */     return super.putIfAbsent(key, value);
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
/* 615 */     return super.remove(key, value);
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
/*     */   default boolean replace(Short key, Byte oldValue, Byte newValue) {
/* 628 */     return super.replace(key, oldValue, newValue);
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
/*     */   default Byte replace(Short key, Byte value) {
/* 641 */     return super.replace(key, value);
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
/*     */   default Byte computeIfAbsent(Short key, Function<? super Short, ? extends Byte> mappingFunction) {
/* 655 */     return super.computeIfAbsent(key, mappingFunction);
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
/*     */   default Byte computeIfPresent(Short key, BiFunction<? super Short, ? super Byte, ? extends Byte> remappingFunction) {
/* 669 */     return super.computeIfPresent(key, remappingFunction);
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
/*     */   default Byte compute(Short key, BiFunction<? super Short, ? super Byte, ? extends Byte> remappingFunction) {
/* 683 */     return super.compute(key, remappingFunction);
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
/*     */   default Byte merge(Short key, Byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/* 697 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */   
/*     */   int size();
/*     */   
/*     */   void defaultReturnValue(byte paramByte);
/*     */   
/*     */   byte defaultReturnValue();
/*     */   
/*     */   ObjectSet<Entry> short2ByteEntrySet();
/*     */   
/*     */   ShortSet keySet();
/*     */   
/*     */   ByteCollection values();
/*     */   
/*     */   boolean containsKey(short paramShort);
/*     */   
/*     */   boolean containsValue(byte paramByte);
/*     */   
/*     */   public static interface Entry
/*     */     extends Map.Entry<Short, Byte> {
/*     */     @Deprecated
/*     */     default Short getKey() {
/* 720 */       return Short.valueOf(getShortKey());
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
/*     */     default Byte getValue() {
/* 743 */       return Byte.valueOf(getByteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Byte setValue(Byte value) {
/* 753 */       return Byte.valueOf(setValue(value.byteValue()));
/*     */     }
/*     */     
/*     */     short getShortKey();
/*     */     
/*     */     byte getByteValue();
/*     */     
/*     */     byte setValue(byte param1Byte);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2ByteMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */