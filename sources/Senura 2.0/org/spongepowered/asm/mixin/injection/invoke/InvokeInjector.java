/*     */ package org.spongepowered.asm.mixin.injection.invoke;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.VarInsnNode;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
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
/*     */ public abstract class InvokeInjector
/*     */   extends Injector
/*     */ {
/*     */   protected final String annotationType;
/*     */   
/*     */   public InvokeInjector(InjectionInfo info, String annotationType) {
/*  54 */     super(info);
/*  55 */     this.annotationType = annotationType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sanityCheck(Target target, List<InjectionPoint> injectionPoints) {
/*  65 */     super.sanityCheck(target, injectionPoints);
/*  66 */     checkTarget(target);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkTarget(Target target) {
/*  75 */     checkTargetModifiers(target, true);
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
/*     */   protected final void checkTargetModifiers(Target target, boolean exactMatch) {
/*  87 */     if (exactMatch && target.isStatic != this.isStatic)
/*  88 */       throw new InvalidInjectionException(this.info, "'static' modifier of handler method does not match target in " + this); 
/*  89 */     if (!exactMatch && !this.isStatic && target.isStatic) {
/*  90 */       throw new InvalidInjectionException(this.info, "non-static callback method " + this + " targets a static method which is not supported");
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkTargetForNode(Target target, InjectionNodes.InjectionNode node) {
/* 106 */     if (target.isCtor) {
/* 107 */       MethodInsnNode superCall = target.findSuperInitNode();
/* 108 */       int superCallIndex = target.indexOf((AbstractInsnNode)superCall);
/* 109 */       int targetIndex = target.indexOf(node.getCurrentTarget());
/* 110 */       if (targetIndex <= superCallIndex) {
/* 111 */         if (!this.isStatic) {
/* 112 */           throw new InvalidInjectionException(this.info, "Pre-super " + this.annotationType + " invocation must be static in " + this);
/*     */         }
/*     */         return;
/*     */       } 
/*     */     } 
/* 117 */     checkTargetModifiers(target, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void inject(Target target, InjectionNodes.InjectionNode node) {
/* 127 */     if (!(node.getCurrentTarget() instanceof MethodInsnNode)) {
/* 128 */       throw new InvalidInjectionException(this.info, this.annotationType + " annotation on is targetting a non-method insn in " + target + " in " + this);
/*     */     }
/*     */ 
/*     */     
/* 132 */     injectAtInvoke(target, node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void injectAtInvoke(Target paramTarget, InjectionNodes.InjectionNode paramInjectionNode);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractInsnNode invokeHandlerWithArgs(Type[] args, InsnList insns, int[] argMap) {
/* 150 */     return invokeHandlerWithArgs(args, insns, argMap, 0, args.length);
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
/*     */   protected AbstractInsnNode invokeHandlerWithArgs(Type[] args, InsnList insns, int[] argMap, int startArg, int endArg) {
/* 162 */     if (!this.isStatic) {
/* 163 */       insns.add((AbstractInsnNode)new VarInsnNode(25, 0));
/*     */     }
/* 165 */     pushArgs(args, insns, argMap, startArg, endArg);
/* 166 */     return invokeHandler(insns);
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
/*     */ 
/*     */   
/*     */   protected int[] storeArgs(Target target, Type[] args, InsnList insns, int start) {
/* 180 */     int[] argMap = target.generateArgMap(args, start);
/* 181 */     storeArgs(args, insns, argMap, start, args.length);
/* 182 */     return argMap;
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
/*     */   
/*     */   protected void storeArgs(Type[] args, InsnList insns, int[] argMap, int start, int end) {
/* 195 */     for (int arg = end - 1; arg >= start; arg--) {
/* 196 */       insns.add((AbstractInsnNode)new VarInsnNode(args[arg].getOpcode(54), argMap[arg]));
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
/*     */   
/*     */   protected void pushArgs(Type[] args, InsnList insns, int[] argMap, int start, int end) {
/* 209 */     for (int arg = start; arg < end; arg++)
/* 210 */       insns.add((AbstractInsnNode)new VarInsnNode(args[arg].getOpcode(21), argMap[arg])); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\invoke\InvokeInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */