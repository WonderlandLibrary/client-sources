/*    */ package org.spongepowered.asm.mixin.injection.struct;
/*    */ 
/*    */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*    */ import org.spongepowered.asm.lib.tree.MethodNode;
/*    */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*    */ import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator;
/*    */ import org.spongepowered.asm.mixin.injection.modify.ModifyVariableInjector;
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
/*    */ public class ModifyVariableInjectionInfo
/*    */   extends InjectionInfo
/*    */ {
/*    */   public ModifyVariableInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
/* 41 */     super(mixin, method, annotation);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Injector parseInjector(AnnotationNode injectAnnotation) {
/* 46 */     return (Injector)new ModifyVariableInjector(this, LocalVariableDiscriminator.parse(injectAnnotation));
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getDescription() {
/* 51 */     return "Variable modifier method";
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\struct\ModifyVariableInjectionInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */