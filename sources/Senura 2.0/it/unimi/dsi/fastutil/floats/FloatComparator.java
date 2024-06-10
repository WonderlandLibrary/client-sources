/*    */ package it.unimi.dsi.fastutil.floats;
/*    */ 
/*    */ import java.lang.invoke.SerializedLambda;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface FloatComparator
/*    */   extends Comparator<Float>
/*    */ {
/*    */   default FloatComparator reversed() {
/* 43 */     return FloatComparators.oppositeComparator(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default int compare(Float ok1, Float ok2) {
/* 55 */     return compare(ok1.floatValue(), ok2.floatValue());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default FloatComparator thenComparing(FloatComparator second) {
/* 64 */     return (k1, k2) -> {
/*    */         int comp = compare(k1, k2);
/*    */         return (comp == 0) ? second.compare(k1, k2) : comp;
/*    */       };
/*    */   }
/*    */   
/*    */   default Comparator<Float> thenComparing(Comparator<? super Float> second) {
/* 71 */     if (second instanceof FloatComparator)
/* 72 */       return thenComparing((FloatComparator)second); 
/* 73 */     return super.thenComparing(second);
/*    */   }
/*    */   
/*    */   int compare(float paramFloat1, float paramFloat2);
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */