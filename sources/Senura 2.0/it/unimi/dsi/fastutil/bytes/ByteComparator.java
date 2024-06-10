/*    */ package it.unimi.dsi.fastutil.bytes;
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
/*    */ public interface ByteComparator
/*    */   extends Comparator<Byte>
/*    */ {
/*    */   default ByteComparator reversed() {
/* 43 */     return ByteComparators.oppositeComparator(this);
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
/*    */   default int compare(Byte ok1, Byte ok2) {
/* 55 */     return compare(ok1.byteValue(), ok2.byteValue());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default ByteComparator thenComparing(ByteComparator second) {
/* 64 */     return (k1, k2) -> {
/*    */         int comp = compare(k1, k2);
/*    */         return (comp == 0) ? second.compare(k1, k2) : comp;
/*    */       };
/*    */   }
/*    */   
/*    */   default Comparator<Byte> thenComparing(Comparator<? super Byte> second) {
/* 71 */     if (second instanceof ByteComparator)
/* 72 */       return thenComparing((ByteComparator)second); 
/* 73 */     return super.thenComparing(second);
/*    */   }
/*    */   
/*    */   int compare(byte paramByte1, byte paramByte2);
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\ByteComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */