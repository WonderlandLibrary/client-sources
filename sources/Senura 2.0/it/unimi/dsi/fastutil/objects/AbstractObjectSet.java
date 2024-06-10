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
/*    */ public abstract class AbstractObjectSet<K>
/*    */   extends AbstractObjectCollection<K>
/*    */   implements Cloneable, ObjectSet<K>
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
/* 55 */     ObjectIterator<K> i = iterator();
/*    */     
/* 57 */     while (n-- != 0) {
/* 58 */       K k = i.next();
/* 59 */       h += (k == null) ? 0 : k.hashCode();
/*    */     } 
/* 61 */     return h;
/*    */   }
/*    */   
/*    */   public abstract ObjectIterator<K> iterator();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractObjectSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */