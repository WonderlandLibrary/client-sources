/*    */ package it.unimi.dsi.fastutil.ints;
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
/*    */ public interface IntComparator
/*    */   extends Comparator<Integer>
/*    */ {
/*    */   default IntComparator reversed() {
/* 43 */     return IntComparators.oppositeComparator(this);
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
/*    */   default int compare(Integer ok1, Integer ok2) {
/* 55 */     return compare(ok1.intValue(), ok2.intValue());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default IntComparator thenComparing(IntComparator second) {
/* 64 */     return (k1, k2) -> {
/*    */         int comp = compare(k1, k2);
/*    */         return (comp == 0) ? second.compare(k1, k2) : comp;
/*    */       };
/*    */   }
/*    */   
/*    */   default Comparator<Integer> thenComparing(Comparator<? super Integer> second) {
/* 71 */     if (second instanceof IntComparator)
/* 72 */       return thenComparing((IntComparator)second); 
/* 73 */     return super.thenComparing(second);
/*    */   }
/*    */   
/*    */   int compare(int paramInt1, int paramInt2);
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */