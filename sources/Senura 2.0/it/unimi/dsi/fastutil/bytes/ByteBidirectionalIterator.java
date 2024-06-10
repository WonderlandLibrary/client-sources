/*    */ package it.unimi.dsi.fastutil.bytes;
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
/*    */ public interface ByteBidirectionalIterator
/*    */   extends ByteIterator, ObjectBidirectionalIterator<Byte>
/*    */ {
/*    */   @Deprecated
/*    */   default Byte previous() {
/* 41 */     return Byte.valueOf(previousByte());
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
/* 60 */       previousByte(); 
/* 61 */     return n - i - 1;
/*    */   }
/*    */ 
/*    */   
/*    */   default int skip(int n) {
/* 66 */     return super.skip(n);
/*    */   }
/*    */   
/*    */   byte previousByte();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\ByteBidirectionalIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */