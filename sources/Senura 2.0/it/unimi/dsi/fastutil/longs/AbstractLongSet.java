/*    */ package it.unimi.dsi.fastutil.longs;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.HashCommon;
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
/*    */ public abstract class AbstractLongSet
/*    */   extends AbstractLongCollection
/*    */   implements Cloneable, LongSet
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
/* 55 */     LongIterator i = iterator();
/*    */     
/* 57 */     while (n-- != 0) {
/* 58 */       long k = i.nextLong();
/* 59 */       h += HashCommon.long2int(k);
/*    */     } 
/* 61 */     return h;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean remove(long k) {
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
/*    */   public boolean rem(long k) {
/* 80 */     return remove(k);
/*    */   }
/*    */   
/*    */   public abstract LongIterator iterator();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\AbstractLongSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */