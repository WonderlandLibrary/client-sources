/*    */ package org.spongepowered.asm.mixin.injection.struct;
/*    */ 
/*    */ import com.google.common.base.Strings;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.spongepowered.asm.lib.Type;
/*    */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*    */ import org.spongepowered.asm.lib.tree.MethodNode;
/*    */ import org.spongepowered.asm.mixin.injection.Constant;
/*    */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*    */ import org.spongepowered.asm.mixin.injection.invoke.ModifyConstantInjector;
/*    */ import org.spongepowered.asm.mixin.injection.points.BeforeConstant;
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
/*    */ 
/*    */ public class ModifyConstantInjectionInfo
/*    */   extends InjectionInfo
/*    */ {
/* 46 */   private static final String CONSTANT_ANNOTATION_CLASS = Constant.class.getName().replace('.', '/');
/*    */   
/*    */   public ModifyConstantInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
/* 49 */     super(mixin, method, annotation, "constant");
/*    */   }
/*    */   
/*    */   protected List<AnnotationNode> readInjectionPoints(String type) {
/*    */     ImmutableList immutableList;
/* 54 */     List<AnnotationNode> ats = super.readInjectionPoints(type);
/* 55 */     if (ats.isEmpty()) {
/* 56 */       AnnotationNode c = new AnnotationNode(CONSTANT_ANNOTATION_CLASS);
/* 57 */       c.visit("log", Boolean.TRUE);
/* 58 */       immutableList = ImmutableList.of(c);
/*    */     } 
/* 60 */     return (List<AnnotationNode>)immutableList;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void parseInjectionPoints(List<AnnotationNode> ats) {
/* 65 */     Type returnType = Type.getReturnType(this.method.desc);
/*    */     
/* 67 */     for (AnnotationNode at : ats) {
/* 68 */       this.injectionPoints.add(new BeforeConstant(getContext(), at, returnType.getDescriptor()));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected Injector parseInjector(AnnotationNode injectAnnotation) {
/* 74 */     return (Injector)new ModifyConstantInjector(this);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getDescription() {
/* 79 */     return "Constant modifier method";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSliceId(String id) {
/* 84 */     return Strings.nullToEmpty(id);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\struct\ModifyConstantInjectionInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */