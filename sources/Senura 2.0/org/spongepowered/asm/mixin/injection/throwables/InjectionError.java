/*    */ package org.spongepowered.asm.mixin.injection.throwables;
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
/*    */ public class InjectionError
/*    */   extends Error
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public InjectionError() {}
/*    */   
/*    */   public InjectionError(String message) {
/* 38 */     super(message);
/*    */   }
/*    */   
/*    */   public InjectionError(Throwable cause) {
/* 42 */     super(cause);
/*    */   }
/*    */   
/*    */   public InjectionError(String message, Throwable cause) {
/* 46 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\throwables\InjectionError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */