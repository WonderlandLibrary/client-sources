/*    */ package org.spongepowered.asm.mixin.injection.struct;
/*    */ 
/*    */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*    */ import org.spongepowered.asm.lib.tree.MethodNode;
/*    */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*    */ import org.spongepowered.asm.mixin.injection.invoke.ModifyArgInjector;
/*    */ import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
/*    */ import org.spongepowered.asm.util.Annotations;
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
/*    */ public class ModifyArgInjectionInfo
/*    */   extends InjectionInfo
/*    */ {
/*    */   public ModifyArgInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
/* 41 */     super(mixin, method, annotation);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Injector parseInjector(AnnotationNode injectAnnotation) {
/* 46 */     int index = ((Integer)Annotations.getValue(injectAnnotation, "index", Integer.valueOf(-1))).intValue();
/*    */     
/* 48 */     return (Injector)new ModifyArgInjector(this, index);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getDescription() {
/* 53 */     return "Argument modifier method";
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\struct\ModifyArgInjectionInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */