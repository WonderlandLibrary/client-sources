/*    */ package it.unimi.dsi.fastutil.booleans;
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
/*    */ public interface BooleanComparator
/*    */   extends Comparator<Boolean>
/*    */ {
/*    */   default BooleanComparator reversed() {
/* 43 */     return BooleanComparators.oppositeComparator(this);
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
/*    */   default int compare(Boolean ok1, Boolean ok2) {
/* 55 */     return compare(ok1.booleanValue(), ok2.booleanValue());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default BooleanComparator thenComparing(BooleanComparator second) {
/* 64 */     return (k1, k2) -> {
/*    */         int comp = compare(k1, k2);
/*    */         return (comp == 0) ? second.compare(k1, k2) : comp;
/*    */       };
/*    */   }
/*    */   
/*    */   default Comparator<Boolean> thenComparing(Comparator<? super Boolean> second) {
/* 71 */     if (second instanceof BooleanComparator)
/* 72 */       return thenComparing((BooleanComparator)second); 
/* 73 */     return super.thenComparing(second);
/*    */   }
/*    */   
/*    */   int compare(boolean paramBoolean1, boolean paramBoolean2);
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\booleans\BooleanComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */