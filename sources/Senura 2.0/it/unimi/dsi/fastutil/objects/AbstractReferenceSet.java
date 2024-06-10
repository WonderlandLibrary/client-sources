/*    */ package it.unimi.dsi.fastutil.objects;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractReferenceSet<K>
/*    */   extends AbstractReferenceCollection<K>
/*    */   implements Cloneable, ReferenceSet<K>
/*    */ {
/*    */   public boolean equals(Object o) {
/* 39 */     if (o == this)
/* 40 */       return true; 
/* 41 */     if (!(o instanceof Set))
/* 42 */       return false; 
/* 43 */     Set<?> s = (Set)o;
/* 44 */     if (s.size() != size())
/* 45 */       return false; 
/* 46 */     return containsAll(s);
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
/* 57 */     int h = 0, n = size();
/* 58 */     ObjectIterator<K> i = iterator();
/*    */     
/* 60 */     while (n-- != 0) {
/* 61 */       K k = i.next();
/* 62 */       h += System.identityHashCode(k);
/*    */     } 
/* 64 */     return h;
/*    */   }
/*    */   
/*    */   public abstract ObjectIterator<K> iterator();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractReferenceSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */