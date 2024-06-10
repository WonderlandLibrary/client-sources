/*     */ package org.spongepowered.asm.mixin.injection.invoke;
/*     */ 
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.InsnNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.VarInsnNode;
/*     */ import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*     */ import org.spongepowered.asm.util.Bytecode;
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
/*     */ public class ModifyArgsInjector
/*     */   extends InvokeInjector
/*     */ {
/*     */   private final ArgsClassGenerator argsClassGenerator;
/*     */   
/*     */   public ModifyArgsInjector(InjectionInfo info) {
/*  52 */     super(info, "@ModifyArgs");
/*     */     
/*  54 */     this.argsClassGenerator = (ArgsClassGenerator)info.getContext().getExtensions().getGenerator(ArgsClassGenerator.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkTarget(Target target) {
/*  63 */     checkTargetModifiers(target, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void inject(Target target, InjectionNodes.InjectionNode node) {
/*  68 */     checkTargetForNode(target, node);
/*  69 */     super.inject(target, node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void injectAtInvoke(Target target, InjectionNodes.InjectionNode node) {
/*  77 */     MethodInsnNode targetMethod = (MethodInsnNode)node.getCurrentTarget();
/*     */     
/*  79 */     Type[] args = Type.getArgumentTypes(targetMethod.desc);
/*  80 */     if (args.length == 0) {
/*  81 */       throw new InvalidInjectionException(this.info, "@ModifyArgs injector " + this + " targets a method invocation " + targetMethod.name + targetMethod.desc + " with no arguments!");
/*     */     }
/*     */ 
/*     */     
/*  85 */     String clArgs = this.argsClassGenerator.getClassRef(targetMethod.desc);
/*  86 */     boolean withArgs = verifyTarget(target);
/*     */     
/*  88 */     InsnList insns = new InsnList();
/*  89 */     target.addToStack(1);
/*     */     
/*  91 */     packArgs(insns, clArgs, targetMethod);
/*     */     
/*  93 */     if (withArgs) {
/*  94 */       target.addToStack(Bytecode.getArgsSize(target.arguments));
/*  95 */       Bytecode.loadArgs(target.arguments, insns, target.isStatic ? 0 : 1);
/*     */     } 
/*     */     
/*  98 */     invokeHandler(insns);
/*  99 */     unpackArgs(insns, clArgs, args);
/*     */     
/* 101 */     target.insns.insertBefore((AbstractInsnNode)targetMethod, insns);
/*     */   }
/*     */   
/*     */   private boolean verifyTarget(Target target) {
/* 105 */     String shortDesc = String.format("(L%s;)V", new Object[] { ArgsClassGenerator.ARGS_REF });
/* 106 */     if (!this.methodNode.desc.equals(shortDesc)) {
/* 107 */       String targetDesc = Bytecode.changeDescriptorReturnType(target.method.desc, "V");
/* 108 */       String longDesc = String.format("(L%s;%s", new Object[] { ArgsClassGenerator.ARGS_REF, targetDesc.substring(1) });
/*     */       
/* 110 */       if (this.methodNode.desc.equals(longDesc)) {
/* 111 */         return true;
/*     */       }
/*     */       
/* 114 */       throw new InvalidInjectionException(this.info, "@ModifyArgs injector " + this + " has an invalid signature " + this.methodNode.desc + ", expected " + shortDesc + " or " + longDesc);
/*     */     } 
/*     */     
/* 117 */     return false;
/*     */   }
/*     */   
/*     */   private void packArgs(InsnList insns, String clArgs, MethodInsnNode targetMethod) {
/* 121 */     String factoryDesc = Bytecode.changeDescriptorReturnType(targetMethod.desc, "L" + clArgs + ";");
/* 122 */     insns.add((AbstractInsnNode)new MethodInsnNode(184, clArgs, "of", factoryDesc, false));
/* 123 */     insns.add((AbstractInsnNode)new InsnNode(89));
/*     */     
/* 125 */     if (!this.isStatic) {
/* 126 */       insns.add((AbstractInsnNode)new VarInsnNode(25, 0));
/* 127 */       insns.add((AbstractInsnNode)new InsnNode(95));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void unpackArgs(InsnList insns, String clArgs, Type[] args) {
/* 132 */     for (int i = 0; i < args.length; i++) {
/* 133 */       if (i < args.length - 1) {
/* 134 */         insns.add((AbstractInsnNode)new InsnNode(89));
/*     */       }
/* 136 */       insns.add((AbstractInsnNode)new MethodInsnNode(182, clArgs, "$" + i, "()" + args[i].getDescriptor(), false));
/* 137 */       if (i < args.length - 1)
/* 138 */         if (args[i].getSize() == 1) {
/* 139 */           insns.add((AbstractInsnNode)new InsnNode(95));
/*     */         } else {
/* 141 */           insns.add((AbstractInsnNode)new InsnNode(93));
/* 142 */           insns.add((AbstractInsnNode)new InsnNode(88));
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\invoke\ModifyArgsInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */