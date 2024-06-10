/*    */ package it.unimi.dsi.fastutil.ints;
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
/*    */ public abstract class AbstractInt2DoubleFunction
/*    */   implements Int2DoubleFunction, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -4940583368468432370L;
/*    */   protected double defRetValue;
/*    */   
/*    */   public void defaultReturnValue(double rv) {
/* 43 */     this.defRetValue = rv;
/*    */   }
/*    */   
/*    */   public double defaultReturnValue() {
/* 47 */     return this.defRetValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\AbstractInt2DoubleFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */