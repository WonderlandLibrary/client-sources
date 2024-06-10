/*    */ package org.spongepowered.asm.mixin.injection.invoke.arg;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Args
/*    */ {
/*    */   protected final Object[] values;
/*    */   
/*    */   protected Args(Object[] values) {
/* 47 */     this.values = values;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int size() {
/* 56 */     return this.values.length;
/*    */   }
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
/*    */   public <T> T get(int index) {
/* 70 */     return (T)this.values[index];
/*    */   }
/*    */   
/*    */   public abstract <T> void set(int paramInt, T paramT);
/*    */   
/*    */   public abstract void setAll(Object... paramVarArgs);
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\invoke\arg\Args.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */