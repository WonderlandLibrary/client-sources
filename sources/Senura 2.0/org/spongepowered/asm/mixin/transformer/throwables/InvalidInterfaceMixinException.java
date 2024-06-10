/*    */ package org.spongepowered.asm.mixin.transformer.throwables;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*    */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
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
/*    */ public class InvalidInterfaceMixinException
/*    */   extends InvalidMixinException
/*    */ {
/*    */   private static final long serialVersionUID = 2L;
/*    */   
/*    */   public InvalidInterfaceMixinException(IMixinInfo mixin, String message) {
/* 38 */     super(mixin, message);
/*    */   }
/*    */   
/*    */   public InvalidInterfaceMixinException(IMixinContext context, String message) {
/* 42 */     super(context, message);
/*    */   }
/*    */   
/*    */   public InvalidInterfaceMixinException(IMixinInfo mixin, Throwable cause) {
/* 46 */     super(mixin, cause);
/*    */   }
/*    */   
/*    */   public InvalidInterfaceMixinException(IMixinContext context, Throwable cause) {
/* 50 */     super(context, cause);
/*    */   }
/*    */   
/*    */   public InvalidInterfaceMixinException(IMixinInfo mixin, String message, Throwable cause) {
/* 54 */     super(mixin, message, cause);
/*    */   }
/*    */   
/*    */   public InvalidInterfaceMixinException(IMixinContext context, String message, Throwable cause) {
/* 58 */     super(context, message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\throwables\InvalidInterfaceMixinException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */