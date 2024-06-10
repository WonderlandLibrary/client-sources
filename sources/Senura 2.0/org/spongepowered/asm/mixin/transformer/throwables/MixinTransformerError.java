/*    */ package org.spongepowered.asm.mixin.transformer.throwables;
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
/*    */ public class MixinTransformerError
/*    */   extends Error
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public MixinTransformerError(String message) {
/* 35 */     super(message);
/*    */   }
/*    */   
/*    */   public MixinTransformerError(Throwable cause) {
/* 39 */     super(cause);
/*    */   }
/*    */   
/*    */   public MixinTransformerError(String message, Throwable cause) {
/* 43 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\throwables\MixinTransformerError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */