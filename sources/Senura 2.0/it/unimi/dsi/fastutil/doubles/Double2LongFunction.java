/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
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
/*     */ @FunctionalInterface
/*     */ public interface Double2LongFunction
/*     */   extends Function<Double, Long>, DoubleToLongFunction
/*     */ {
/*     */   default long applyAsLong(double operand) {
/*  61 */     return get(operand);
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
/*     */   default long put(double key, long value) {
/*  75 */     throw new UnsupportedOperationException();
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
/*     */   default long remove(double key) {
/*  97 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Long put(Double key, Long value) {
/* 107 */     double k = key.doubleValue();
/* 108 */     boolean containsKey = containsKey(k);
/* 109 */     long v = put(k, value.longValue());
/* 110 */     return containsKey ? Long.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Long get(Object key) {
/* 120 */     if (key == null)
/* 121 */       return null; 
/* 122 */     double k = ((Double)key).doubleValue();
/* 123 */     long v = get(k);
/* 124 */     return (v != defaultReturnValue() || containsKey(k)) ? Long.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Long remove(Object key) {
/* 134 */     if (key == null)
/* 135 */       return null; 
/* 136 */     double k = ((Double)key).doubleValue();
/* 137 */     return containsKey(k) ? Long.valueOf(remove(k)) : null;
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
/*     */   default boolean containsKey(double key) {
/* 152 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean containsKey(Object key) {
/* 162 */     return (key == null) ? false : containsKey(((Double)key).doubleValue());
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
/* 176 */     throw new UnsupportedOperationException();
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
/* 188 */     return 0L;
/*     */   }
/*     */   
/*     */   long get(double paramDouble);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2LongFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */