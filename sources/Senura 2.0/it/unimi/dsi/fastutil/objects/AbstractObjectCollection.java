/*    */ package it.unimi.dsi.fastutil.objects;
/*    */ 
/*    */ import java.util.AbstractCollection;
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
/*    */ public abstract class AbstractObjectCollection<K>
/*    */   extends AbstractCollection<K>
/*    */   implements ObjectCollection<K>
/*    */ {
/*    */   public String toString() {
/* 41 */     StringBuilder s = new StringBuilder();
/* 42 */     ObjectIterator<K> i = iterator();
/* 43 */     int n = size();
/*    */     
/* 45 */     boolean first = true;
/* 46 */     s.append("{");
/* 47 */     while (n-- != 0) {
/* 48 */       if (first) {
/* 49 */         first = false;
/*    */       } else {
/* 51 */         s.append(", ");
/* 52 */       }  Object k = i.next();
/* 53 */       if (this == k) {
/* 54 */         s.append("(this collection)"); continue;
/*    */       } 
/* 56 */       s.append(String.valueOf(k));
/*    */     } 
/* 58 */     s.append("}");
/* 59 */     return s.toString();
/*    */   }
/*    */   
/*    */   public abstract ObjectIterator<K> iterator();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractObjectCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */