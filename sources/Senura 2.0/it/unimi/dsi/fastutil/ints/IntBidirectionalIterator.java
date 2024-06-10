/*    */ package it.unimi.dsi.fastutil.ints;
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
/*    */ public interface IntBidirectionalIterator
/*    */   extends IntIterator, ObjectBidirectionalIterator<Integer>
/*    */ {
/*    */   @Deprecated
/*    */   default Integer previous() {
/* 41 */     return Integer.valueOf(previousInt());
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
/* 60 */       previousInt(); 
/* 61 */     return n - i - 1;
/*    */   }
/*    */ 
/*    */   
/*    */   default int skip(int n) {
/* 66 */     return super.skip(n);
/*    */   }
/*    */   
/*    */   int previousInt();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntBidirectionalIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */