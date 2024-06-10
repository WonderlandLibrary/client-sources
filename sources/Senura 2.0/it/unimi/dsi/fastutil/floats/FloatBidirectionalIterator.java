/*    */ package it.unimi.dsi.fastutil.floats;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
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
/*    */ public interface FloatBidirectionalIterator
/*    */   extends FloatIterator, ObjectBidirectionalIterator<Float>
/*    */ {
/*    */   @Deprecated
/*    */   default Float previous() {
/* 41 */     return Float.valueOf(previousFloat());
/*    */   }
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
/*    */   default int back(int n) {
/* 58 */     int i = n;
/* 59 */     while (i-- != 0 && hasPrevious())
/* 60 */       previousFloat(); 
/* 61 */     return n - i - 1;
/*    */   }
/*    */ 
/*    */   
/*    */   default int skip(int n) {
/* 66 */     return super.skip(n);
/*    */   }
/*    */   
/*    */   float previousFloat();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatBidirectionalIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */