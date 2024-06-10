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
/*    */ public abstract class AbstractObject2ShortFunction<K>
/*    */   implements Object2ShortFunction<K>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -4940583368468432370L;
/*    */   protected short defRetValue;
/*    */   
/*    */   public void defaultReturnValue(short rv) {
/* 43 */     this.defRetValue = rv;
/*    */   }
/*    */   
/*    */   public short defaultReturnValue() {
/* 47 */     return this.defRetValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractObject2ShortFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */