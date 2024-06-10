/*    */ package org.spongepowered.asm.mixin.injection.throwables;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.injection.code.ISliceContext;
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
/*    */ 
/*    */ public class InvalidSliceException
/*    */   extends InvalidInjectionException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public InvalidSliceException(IMixinContext context, String message) {
/* 39 */     super(context, message);
/*    */   }
/*    */   
/*    */   public InvalidSliceException(ISliceContext owner, String message) {
/* 43 */     super(owner.getContext(), message);
/*    */   }
/*    */   
/*    */   public InvalidSliceException(IMixinContext context, Throwable cause) {
/* 47 */     super(context, cause);
/*    */   }
/*    */   
/*    */   public InvalidSliceException(ISliceContext owner, Throwable cause) {
/* 51 */     super(owner.getContext(), cause);
/*    */   }
/*    */   
/*    */   public InvalidSliceException(IMixinContext context, String message, Throwable cause) {
/* 55 */     super(context, message, cause);
/*    */   }
/*    */   
/*    */   public InvalidSliceException(ISliceContext owner, String message, Throwable cause) {
/* 59 */     super(owner.getContext(), message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\throwables\InvalidSliceException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */