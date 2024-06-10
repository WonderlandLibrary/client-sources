/*    */ package org.spongepowered.asm.mixin.transformer.ext.extensions;
/*    */ 
/*    */ import org.spongepowered.asm.lib.ClassVisitor;
/*    */ import org.spongepowered.asm.lib.util.CheckClassAdapter;
/*    */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*    */ import org.spongepowered.asm.mixin.throwables.MixinException;
/*    */ import org.spongepowered.asm.mixin.transformer.ext.IExtension;
/*    */ import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
/*    */ import org.spongepowered.asm.transformers.MixinClassWriter;
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
/*    */ 
/*    */ 
/*    */ public class ExtensionCheckClass
/*    */   implements IExtension
/*    */ {
/*    */   public static class ValidationFailedException
/*    */     extends MixinException
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */     public ValidationFailedException(String message, Throwable cause) {
/* 50 */       super(message, cause);
/*    */     }
/*    */     
/*    */     public ValidationFailedException(String message) {
/* 54 */       super(message);
/*    */     }
/*    */     
/*    */     public ValidationFailedException(Throwable cause) {
/* 58 */       super(cause);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean checkActive(MixinEnvironment environment) {
/* 69 */     return environment.getOption(MixinEnvironment.Option.DEBUG_VERIFY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void preApply(ITargetClassContext context) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void postApply(ITargetClassContext context) {
/*    */     try {
/* 87 */       context.getClassNode().accept((ClassVisitor)new CheckClassAdapter((ClassVisitor)new MixinClassWriter(2)));
/* 88 */     } catch (RuntimeException ex) {
/* 89 */       throw new ValidationFailedException(ex.getMessage(), ex);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void export(MixinEnvironment env, String name, boolean force, byte[] bytes) {}
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\ext\extensions\ExtensionCheckClass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */