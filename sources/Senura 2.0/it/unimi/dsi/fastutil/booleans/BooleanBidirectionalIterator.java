/*    */ package it.unimi.dsi.fastutil.booleans;
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
/*    */ public interface BooleanBidirectionalIterator
/*    */   extends BooleanIterator, ObjectBidirectionalIterator<Boolean>
/*    */ {
/*    */   @Deprecated
/*    */   default Boolean previous() {
/* 41 */     return Boolean.valueOf(previousBoolean());
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
/* 60 */       previousBoolean(); 
/* 61 */     return n - i - 1;
/*    */   }
/*    */ 
/*    */   
/*    */   default int skip(int n) {
/* 66 */     return super.skip(n);
/*    */   }
/*    */   
/*    */   boolean previousBoolean();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\booleans\BooleanBidirectionalIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */