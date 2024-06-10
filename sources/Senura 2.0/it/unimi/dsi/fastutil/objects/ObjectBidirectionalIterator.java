/*    */ package it.unimi.dsi.fastutil.objects;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.BidirectionalIterator;
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
/*    */ public interface ObjectBidirectionalIterator<K>
/*    */   extends ObjectIterator<K>, BidirectionalIterator<K>
/*    */ {
/*    */   default int back(int n) {
/* 39 */     int i = n;
/* 40 */     while (i-- != 0 && hasPrevious())
/* 41 */       previous(); 
/* 42 */     return n - i - 1;
/*    */   }
/*    */ 
/*    */   
/*    */   default int skip(int n) {
/* 47 */     return super.skip(n);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectBidirectionalIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */