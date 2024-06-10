/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @FunctionalInterface
/*     */ public interface Byte2DoubleFunction
/*     */   extends Function<Byte, Double>, IntToDoubleFunction
/*     */ {
/*     */   @Deprecated
/*     */   default double applyAsDouble(int operand) {
/*  75 */     return get(SafeMath.safeIntToByte(operand));
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
/*     */   default double put(byte key, double value) {
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
/*     */   default double remove(byte key) {
/* 111 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Double put(Byte key, Double value) {
/* 121 */     byte k = key.byteValue();
/* 122 */     boolean containsKey = containsKey(k);
/* 123 */     double v = put(k, value.doubleValue());
/* 124 */     return containsKey ? Double.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Double get(Object key) {
/* 134 */     if (key == null)
/* 135 */       return null; 
/* 136 */     byte k = ((Byte)key).byteValue();
/* 137 */     double v = get(k);
/* 138 */     return (v != defaultReturnValue() || containsKey(k)) ? Double.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Double remove(Object key) {
/* 148 */     if (key == null)
/* 149 */       return null; 
/* 150 */     byte k = ((Byte)key).byteValue();
/* 151 */     return containsKey(k) ? Double.valueOf(remove(k)) : null;
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
/*     */   default boolean containsKey(byte key) {
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
/* 176 */     return (key == null) ? false : containsKey(((Byte)key).byteValue());
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
/*     */   default void defaultReturnValue(double rv) {
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
/*     */   default double defaultReturnValue() {
/* 202 */     return 0.0D;
/*     */   }
/*     */   
/*     */   double get(byte paramByte);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\Byte2DoubleFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */