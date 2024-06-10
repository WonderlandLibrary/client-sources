/*    */ package org.spongepowered.asm.mixin.injection.modify;
/*    */ 
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
/*    */ public class InvalidImplicitDiscriminatorException
/*    */   extends MixinException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public InvalidImplicitDiscriminatorException(String message) {
/* 38 */     super(message);
/*    */   }
/*    */   
/*    */   public InvalidImplicitDiscriminatorException(String message, Throwable cause) {
/* 42 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\modify\InvalidImplicitDiscriminatorException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */