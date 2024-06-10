/*    */ package it.unimi.dsi.fastutil.objects;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ObjectIterator<K>
/*    */   extends Iterator<K>
/*    */ {
/*    */   default int skip(int n) {
/* 39 */     if (n < 0)
/* 40 */       throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 41 */     int i = n;
/* 42 */     while (i-- != 0 && hasNext())
/* 43 */       next(); 
/* 44 */     return n - i - 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */