/*    */ package org.spongepowered.asm.mixin.injection.struct;
/*    */ 
/*    */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*    */ import org.spongepowered.asm.lib.tree.MethodNode;
/*    */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*    */ import org.spongepowered.asm.mixin.injection.invoke.ModifyArgsInjector;
/*    */ import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
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
/*    */ public class ModifyArgsInjectionInfo
/*    */   extends InjectionInfo
/*    */ {
/*    */   public ModifyArgsInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
/* 40 */     super(mixin, method, annotation);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Injector parseInjector(AnnotationNode injectAnnotation) {
/* 45 */     return (Injector)new ModifyArgsInjector(this);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getDescription() {
/* 50 */     return "Multi-argument modifier method";
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\struct\ModifyArgsInjectionInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */