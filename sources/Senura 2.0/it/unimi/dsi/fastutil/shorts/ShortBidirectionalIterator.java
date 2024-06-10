/*    */ package it.unimi.dsi.fastutil.shorts;
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
/*    */ public interface ShortBidirectionalIterator
/*    */   extends ShortIterator, ObjectBidirectionalIterator<Short>
/*    */ {
/*    */   @Deprecated
/*    */   default Short previous() {
/* 41 */     return Short.valueOf(previousShort());
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
/* 60 */       previousShort(); 
/* 61 */     return n - i - 1;
/*    */   }
/*    */ 
/*    */   
/*    */   default int skip(int n) {
/* 66 */     return super.skip(n);
/*    */   }
/*    */   
/*    */   short previousShort();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\ShortBidirectionalIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */