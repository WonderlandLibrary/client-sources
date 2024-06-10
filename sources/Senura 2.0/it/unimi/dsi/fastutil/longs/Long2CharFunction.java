/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import java.util.function.LongToIntFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public interface Long2CharFunction
/*     */   extends Function<Long, Character>, LongToIntFunction
/*     */ {
/*     */   default int applyAsInt(long operand) {
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
/*     */   default char put(long key, char value) {
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
/*     */   default char remove(long key) {
/*  97 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Character put(Long key, Character value) {
/* 107 */     long k = key.longValue();
/* 108 */     boolean containsKey = containsKey(k);
/* 109 */     char v = put(k, value.charValue());
/* 110 */     return containsKey ? Character.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Character get(Object key) {
/* 120 */     if (key == null)
/* 121 */       return null; 
/* 122 */     long k = ((Long)key).longValue();
/* 123 */     char v = get(k);
/* 124 */     return (v != defaultReturnValue() || containsKey(k)) ? Character.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Character remove(Object key) {
/* 134 */     if (key == null)
/* 135 */       return null; 
/* 136 */     long k = ((Long)key).longValue();
/* 137 */     return containsKey(k) ? Character.valueOf(remove(k)) : null;
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
/*     */   default boolean containsKey(long key) {
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
/* 162 */     return (key == null) ? false : containsKey(((Long)key).longValue());
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
/*     */   default void defaultReturnValue(char rv) {
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
/*     */   default char defaultReturnValue() {
/* 188 */     return Character.MIN_VALUE;
/*     */   }
/*     */   
/*     */   char get(long paramLong);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2CharFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */