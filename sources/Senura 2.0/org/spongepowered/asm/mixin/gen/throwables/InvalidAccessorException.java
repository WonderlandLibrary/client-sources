/*    */ package org.spongepowered.asm.mixin.gen.throwables;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.gen.AccessorInfo;
/*    */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*    */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
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
/*    */ public class InvalidAccessorException
/*    */   extends InvalidMixinException
/*    */ {
/*    */   private static final long serialVersionUID = 2L;
/*    */   private final AccessorInfo info;
/*    */   
/*    */   public InvalidAccessorException(IMixinContext context, String message) {
/* 41 */     super(context, message);
/* 42 */     this.info = null;
/*    */   }
/*    */   
/*    */   public InvalidAccessorException(AccessorInfo info, String message) {
/* 46 */     super(info.getContext(), message);
/* 47 */     this.info = info;
/*    */   }
/*    */   
/*    */   public InvalidAccessorException(IMixinContext context, Throwable cause) {
/* 51 */     super(context, cause);
/* 52 */     this.info = null;
/*    */   }
/*    */   
/*    */   public InvalidAccessorException(AccessorInfo info, Throwable cause) {
/* 56 */     super(info.getContext(), cause);
/* 57 */     this.info = info;
/*    */   }
/*    */   
/*    */   public InvalidAccessorException(IMixinContext context, String message, Throwable cause) {
/* 61 */     super(context, message, cause);
/* 62 */     this.info = null;
/*    */   }
/*    */   
/*    */   public InvalidAccessorException(AccessorInfo info, String message, Throwable cause) {
/* 66 */     super(info.getContext(), message, cause);
/* 67 */     this.info = info;
/*    */   }
/*    */   
/*    */   public AccessorInfo getAccessorInfo() {
/* 71 */     return this.info;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\gen\throwables\InvalidAccessorException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */