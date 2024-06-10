/*     */ package org.spongepowered.asm.mixin.transformer;
/*     */ 
/*     */ import java.util.Map;
/*     */ import org.spongepowered.asm.lib.tree.FieldNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidInterfaceMixinException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MixinApplicatorInterface
/*     */   extends MixinApplicatorStandard
/*     */ {
/*     */   MixinApplicatorInterface(TargetClassContext context) {
/*  48 */     super(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void applyInterfaces(MixinTargetContext mixin) {
/*  58 */     for (String interfaceName : mixin.getInterfaces()) {
/*  59 */       if (!this.targetClass.name.equals(interfaceName) && !this.targetClass.interfaces.contains(interfaceName)) {
/*  60 */         this.targetClass.interfaces.add(interfaceName);
/*  61 */         mixin.getTargetClassInfo().addInterface(interfaceName);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void applyFields(MixinTargetContext mixin) {
/*  74 */     for (Map.Entry<FieldNode, ClassInfo.Field> entry : mixin.getShadowFields()) {
/*  75 */       FieldNode shadow = entry.getKey();
/*  76 */       this.logger.error("Ignoring redundant @Shadow field {}:{} in {}", new Object[] { shadow.name, shadow.desc, mixin });
/*     */     } 
/*     */     
/*  79 */     mergeNewFields(mixin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void applyInitialisers(MixinTargetContext mixin) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void prepareInjections(MixinTargetContext mixin) {
/* 100 */     for (MethodNode method : this.targetClass.methods) {
/*     */       try {
/* 102 */         InjectionInfo injectInfo = InjectionInfo.parse(mixin, method);
/* 103 */         if (injectInfo != null) {
/* 104 */           throw new InvalidInterfaceMixinException(mixin, injectInfo + " is not supported on interface mixin method " + method.name);
/*     */         }
/* 106 */       } catch (InvalidInjectionException ex) {
/* 107 */         String description = (ex.getInjectionInfo() != null) ? ex.getInjectionInfo().toString() : "Injection";
/* 108 */         throw new InvalidInterfaceMixinException(mixin, description + " is not supported in interface mixin");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void applyInjections(MixinTargetContext mixin) {}
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\MixinApplicatorInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */