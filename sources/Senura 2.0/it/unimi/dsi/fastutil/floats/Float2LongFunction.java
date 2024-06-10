/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import java.util.function.DoubleToLongFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @FunctionalInterface
/*     */ public interface Float2LongFunction
/*     */   extends Function<Float, Long>, DoubleToLongFunction
/*     */ {
/*     */   @Deprecated
/*     */   default long applyAsLong(double operand) {
/*  75 */     return get(SafeMath.safeDoubleToFloat(operand));
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
/*     */   default long put(float key, long value) {
/*  89 */     throw new UnsupportedOperationException();
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
/*     */   default long remove(float key) {
/* 111 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Long put(Float key, Long value) {
/* 121 */     float k = key.floatValue();
/* 122 */     boolean containsKey = containsKey(k);
/* 123 */     long v = put(k, value.longValue());
/* 124 */     return containsKey ? Long.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Long get(Object key) {
/* 134 */     if (key == null)
/* 135 */       return null; 
/* 136 */     float k = ((Float)key).floatValue();
/* 137 */     long v = get(k);
/* 138 */     return (v != defaultReturnValue() || containsKey(k)) ? Long.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Long remove(Object key) {
/* 148 */     if (key == null)
/* 149 */       return null; 
/* 150 */     float k = ((Float)key).floatValue();
/* 151 */     return containsKey(k) ? Long.valueOf(remove(k)) : null;
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
/*     */   default boolean containsKey(float key) {
/* 166 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean containsKey(Object key) {
/* 176 */     return (key == null) ? false : containsKey(((Float)key).floatValue());
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
/*     */   default void defaultReturnValue(long rv) {
/* 190 */     throw new UnsupportedOperationException();
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
/*     */   default long defaultReturnValue() {
/* 202 */     return 0L;
/*     */   }
/*     */   
/*     */   long get(float paramFloat);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2LongFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */