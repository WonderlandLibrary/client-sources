/*    */ package org.spongepowered.asm.mixin.transformer.throwables;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*    */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*    */ import org.spongepowered.asm.mixin.throwables.MixinException;
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
/*    */ public class InvalidMixinException
/*    */   extends MixinException
/*    */ {
/*    */   private static final long serialVersionUID = 2L;
/*    */   private final IMixinInfo mixin;
/*    */   
/*    */   public InvalidMixinException(IMixinInfo mixin, String message) {
/* 41 */     super(message);
/* 42 */     this.mixin = mixin;
/*    */   }
/*    */   
/*    */   public InvalidMixinException(IMixinContext context, String message) {
/* 46 */     this(context.getMixin(), message);
/*    */   }
/*    */   
/*    */   public InvalidMixinException(IMixinInfo mixin, Throwable cause) {
/* 50 */     super(cause);
/* 51 */     this.mixin = mixin;
/*    */   }
/*    */   
/*    */   public InvalidMixinException(IMixinContext context, Throwable cause) {
/* 55 */     this(context.getMixin(), cause);
/*    */   }
/*    */   
/*    */   public InvalidMixinException(IMixinInfo mixin, String message, Throwable cause) {
/* 59 */     super(message, cause);
/* 60 */     this.mixin = mixin;
/*    */   }
/*    */   
/*    */   public InvalidMixinException(IMixinContext context, String message, Throwable cause) {
/* 64 */     super(message, cause);
/* 65 */     this.mixin = context.getMixin();
/*    */   }
/*    */   
/*    */   public IMixinInfo getMixin() {
/* 69 */     return this.mixin;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\throwables\InvalidMixinException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */