/*    */ package net.minecraft.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TupleIntJsonSerializable
/*    */ {
/*    */   private int integerValue;
/*    */   private IJsonSerializable jsonSerializableValue;
/*    */   
/*    */   public int getIntegerValue() {
/* 13 */     return this.integerValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setIntegerValue(int integerValueIn) {
/* 21 */     this.integerValue = integerValueIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T extends IJsonSerializable> T getJsonSerializableValue() {
/* 26 */     return (T)this.jsonSerializableValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setJsonSerializableValue(IJsonSerializable jsonSerializableValueIn) {
/* 34 */     this.jsonSerializableValue = jsonSerializableValueIn;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraf\\util\TupleIntJsonSerializable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */