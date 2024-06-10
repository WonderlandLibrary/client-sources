/*    */ package it.unimi.dsi.fastutil.chars;
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
/*    */ public abstract class AbstractChar2LongFunction
/*    */   implements Char2LongFunction, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -4940583368468432370L;
/*    */   protected long defRetValue;
/*    */   
/*    */   public void defaultReturnValue(long rv) {
/* 43 */     this.defRetValue = rv;
/*    */   }
/*    */   
/*    */   public long defaultReturnValue() {
/* 47 */     return this.defRetValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\AbstractChar2LongFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */