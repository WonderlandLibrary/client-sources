/*    */ package org.spongepowered.asm.mixin.injection.throwables;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
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
/*    */ 
/*    */ public class InvalidInjectionException
/*    */   extends InvalidMixinException
/*    */ {
/*    */   private static final long serialVersionUID = 2L;
/*    */   private final InjectionInfo info;
/*    */   
/*    */   public InvalidInjectionException(IMixinContext context, String message) {
/* 42 */     super(context, message);
/* 43 */     this.info = null;
/*    */   }
/*    */   
/*    */   public InvalidInjectionException(InjectionInfo info, String message) {
/* 47 */     super(info.getContext(), message);
/* 48 */     this.info = info;
/*    */   }
/*    */   
/*    */   public InvalidInjectionException(IMixinContext context, Throwable cause) {
/* 52 */     super(context, cause);
/* 53 */     this.info = null;
/*    */   }
/*    */   
/*    */   public InvalidInjectionException(InjectionInfo info, Throwable cause) {
/* 57 */     super(info.getContext(), cause);
/* 58 */     this.info = info;
/*    */   }
/*    */   
/*    */   public InvalidInjectionException(IMixinContext context, String message, Throwable cause) {
/* 62 */     super(context, message, cause);
/* 63 */     this.info = null;
/*    */   }
/*    */   
/*    */   public InvalidInjectionException(InjectionInfo info, String message, Throwable cause) {
/* 67 */     super(info.getContext(), message, cause);
/* 68 */     this.info = info;
/*    */   }
/*    */   
/*    */   public InjectionInfo getInjectionInfo() {
/* 72 */     return this.info;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\throwables\InvalidInjectionException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */