/*    */ package org.spongepowered.asm.mixin.throwables;
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
/*    */ public class MixinPrepareError
/*    */   extends Error
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public MixinPrepareError(String message) {
/* 35 */     super(message);
/*    */   }
/*    */   
/*    */   public MixinPrepareError(Throwable cause) {
/* 39 */     super(cause);
/*    */   }
/*    */   
/*    */   public MixinPrepareError(String message, Throwable cause) {
/* 43 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\throwables\MixinPrepareError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */