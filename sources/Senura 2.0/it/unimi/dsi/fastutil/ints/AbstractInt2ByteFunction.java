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
/*    */ public abstract class AbstractInt2ByteFunction
/*    */   implements Int2ByteFunction, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -4940583368468432370L;
/*    */   protected byte defRetValue;
/*    */   
/*    */   public void defaultReturnValue(byte rv) {
/* 43 */     this.defRetValue = rv;
/*    */   }
/*    */   
/*    */   public byte defaultReturnValue() {
/* 47 */     return this.defRetValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\AbstractInt2ByteFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */