/*    */ package it.unimi.dsi.fastutil.objects;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ 
/*    */ 
/*    */ public abstract class AbstractReference2ObjectFunction<K, V>
/*    */   implements Reference2ObjectFunction<K, V>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -4940583368468432370L;
/*    */   protected V defRetValue;
/*    */   
/*    */   public void defaultReturnValue(V rv) {
/* 46 */     this.defRetValue = rv;
/*    */   }
/*    */   
/*    */   public V defaultReturnValue() {
/* 50 */     return this.defRetValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractReference2ObjectFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */