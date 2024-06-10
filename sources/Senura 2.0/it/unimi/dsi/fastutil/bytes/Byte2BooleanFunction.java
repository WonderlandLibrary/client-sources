/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import java.util.function.IntPredicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public interface Byte2BooleanFunction
/*     */   extends Function<Byte, Boolean>, IntPredicate
/*     */ {
/*     */   @Deprecated
/*     */   default boolean test(int operand) {
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
/*     */   default boolean put(byte key, boolean value) {
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
/*     */   default boolean remove(byte key) {
/* 111 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Boolean put(Byte key, Boolean value) {
/* 121 */     byte k = key.byteValue();
/* 122 */     boolean containsKey = containsKey(k);
/* 123 */     boolean v = put(k, value.booleanValue());
/* 124 */     return containsKey ? Boolean.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Boolean get(Object key) {
/* 134 */     if (key == null)
/* 135 */       return null; 
/* 136 */     byte k = ((Byte)key).byteValue();
/* 137 */     boolean v = get(k);
/* 138 */     return (v != defaultReturnValue() || containsKey(k)) ? Boolean.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Boolean remove(Object key) {
/* 148 */     if (key == null)
/* 149 */       return null; 
/* 150 */     byte k = ((Byte)key).byteValue();
/* 151 */     return containsKey(k) ? Boolean.valueOf(remove(k)) : null;
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
/*     */   default void defaultReturnValue(boolean rv) {
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
/*     */   default boolean defaultReturnValue() {
/* 202 */     return false;
/*     */   }
/*     */   
/*     */   boolean get(byte paramByte);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\Byte2BooleanFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */