/*    */ package org.spongepowered.asm.mixin.injection.struct;
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
/*    */ public class InvalidMemberDescriptorException
/*    */   extends MixinException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public InvalidMemberDescriptorException(String message) {
/* 37 */     super(message);
/*    */   }
/*    */   
/*    */   public InvalidMemberDescriptorException(Throwable cause) {
/* 41 */     super(cause);
/*    */   }
/*    */   
/*    */   public InvalidMemberDescriptorException(String message, Throwable cause) {
/* 45 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\struct\InvalidMemberDescriptorException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */