/*    */ package it.unimi.dsi.fastutil.floats;
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
/*    */ public abstract class AbstractFloat2BooleanFunction
/*    */   implements Float2BooleanFunction, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -4940583368468432370L;
/*    */   protected boolean defRetValue;
/*    */   
/*    */   public void defaultReturnValue(boolean rv) {
/* 43 */     this.defRetValue = rv;
/*    */   }
/*    */   
/*    */   public boolean defaultReturnValue() {
/* 47 */     return this.defRetValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\AbstractFloat2BooleanFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */