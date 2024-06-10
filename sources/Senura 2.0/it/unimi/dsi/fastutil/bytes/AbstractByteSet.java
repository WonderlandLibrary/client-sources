/*    */ package it.unimi.dsi.fastutil.bytes;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
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
/*    */ public abstract class AbstractByteSet
/*    */   extends AbstractByteCollection
/*    */   implements Cloneable, ByteSet
/*    */ {
/*    */   public boolean equals(Object o) {
/* 36 */     if (o == this)
/* 37 */       return true; 
/* 38 */     if (!(o instanceof Set))
/* 39 */       return false; 
/* 40 */     Set<?> s = (Set)o;
/* 41 */     if (s.size() != size())
/* 42 */       return false; 
/* 43 */     return containsAll(s);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 54 */     int h = 0, n = size();
/* 55 */     ByteIterator i = iterator();
/*    */     
/* 57 */     while (n-- != 0) {
/* 58 */       byte k = i.nextByte();
/* 59 */       h += k;
/*    */     } 
/* 61 */     return h;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean remove(byte k) {
/* 69 */     return super.rem(k);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public boolean rem(byte k) {
/* 80 */     return remove(k);
/*    */   }
/*    */   
/*    */   public abstract ByteIterator iterator();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\AbstractByteSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */