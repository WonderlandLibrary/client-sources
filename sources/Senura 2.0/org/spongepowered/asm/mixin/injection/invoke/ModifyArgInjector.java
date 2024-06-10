/*     */ package org.spongepowered.asm.mixin.injection.invoke;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
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
/*     */ public class ModifyArgInjector
/*     */   extends InvokeInjector
/*     */ {
/*     */   private final int index;
/*     */   private final boolean singleArgMode;
/*     */   
/*     */   public ModifyArgInjector(InjectionInfo info, int index) {
/*  64 */     super(info, "@ModifyArg");
/*  65 */     this.index = index;
/*  66 */     this.singleArgMode = (this.methodArgs.length == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sanityCheck(Target target, List<InjectionPoint> injectionPoints) {
/*  76 */     super.sanityCheck(target, injectionPoints);
/*     */     
/*  78 */     if (this.singleArgMode && 
/*  79 */       !this.methodArgs[0].equals(this.returnType)) {
/*  80 */       throw new InvalidInjectionException(this.info, "@ModifyArg return type on " + this + " must match the parameter type. ARG=" + this.methodArgs[0] + " RETURN=" + this.returnType);
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
/*     */   protected void checkTarget(Target target) {
/*  92 */     if (!this.isStatic && target.isStatic) {
/*  93 */       throw new InvalidInjectionException(this.info, "non-static callback method " + this + " targets a static method which is not supported");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void inject(Target target, InjectionNodes.InjectionNode node) {
/*  99 */     checkTargetForNode(target, node);
/* 100 */     super.inject(target, node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void injectAtInvoke(Target target, InjectionNodes.InjectionNode node) {
/* 108 */     MethodInsnNode methodNode = (MethodInsnNode)node.getCurrentTarget();
/* 109 */     Type[] args = Type.getArgumentTypes(methodNode.desc);
/* 110 */     int argIndex = findArgIndex(target, args);
/* 111 */     InsnList insns = new InsnList();
/* 112 */     int extraLocals = 0;
/*     */     
/* 114 */     if (this.singleArgMode) {
/* 115 */       extraLocals = injectSingleArgHandler(target, args, argIndex, insns);
/*     */     } else {
/* 117 */       extraLocals = injectMultiArgHandler(target, args, argIndex, insns);
/*     */     } 
/*     */     
/* 120 */     target.insns.insertBefore((AbstractInsnNode)methodNode, insns);
/* 121 */     target.addToLocals(extraLocals);
/* 122 */     target.addToStack(2 - extraLocals - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int injectSingleArgHandler(Target target, Type[] args, int argIndex, InsnList insns) {
/* 129 */     int[] argMap = storeArgs(target, args, insns, argIndex);
/* 130 */     invokeHandlerWithArgs(args, insns, argMap, argIndex, argIndex + 1);
/* 131 */     pushArgs(args, insns, argMap, argIndex + 1, args.length);
/* 132 */     return argMap[argMap.length - 1] - target.getMaxLocals() + args[args.length - 1].getSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int injectMultiArgHandler(Target target, Type[] args, int argIndex, InsnList insns) {
/* 139 */     if (!Arrays.equals((Object[])args, (Object[])this.methodArgs)) {
/* 140 */       throw new InvalidInjectionException(this.info, "@ModifyArg method " + this + " targets a method with an invalid signature " + 
/* 141 */           Bytecode.getDescriptor(args) + ", expected " + Bytecode.getDescriptor(this.methodArgs));
/*     */     }
/*     */     
/* 144 */     int[] argMap = storeArgs(target, args, insns, 0);
/* 145 */     pushArgs(args, insns, argMap, 0, argIndex);
/* 146 */     invokeHandlerWithArgs(args, insns, argMap, 0, args.length);
/* 147 */     pushArgs(args, insns, argMap, argIndex + 1, args.length);
/* 148 */     return argMap[argMap.length - 1] - target.getMaxLocals() + args[args.length - 1].getSize();
/*     */   }
/*     */   
/*     */   protected int findArgIndex(Target target, Type[] args) {
/* 152 */     if (this.index > -1) {
/* 153 */       if (this.index >= args.length || !args[this.index].equals(this.returnType)) {
/* 154 */         throw new InvalidInjectionException(this.info, "Specified index " + this.index + " for @ModifyArg is invalid for args " + 
/* 155 */             Bytecode.getDescriptor(args) + ", expected " + this.returnType + " on " + this);
/*     */       }
/* 157 */       return this.index;
/*     */     } 
/*     */     
/* 160 */     int argIndex = -1;
/*     */     
/* 162 */     for (int arg = 0; arg < args.length; arg++) {
/* 163 */       if (args[arg].equals(this.returnType)) {
/*     */ 
/*     */ 
/*     */         
/* 167 */         if (argIndex != -1) {
/* 168 */           throw new InvalidInjectionException(this.info, "Found duplicate args with index [" + argIndex + ", " + arg + "] matching type " + this.returnType + " for @ModifyArg target " + target + " in " + this + ". Please specify index of desired arg.");
/*     */         }
/*     */ 
/*     */         
/* 172 */         argIndex = arg;
/*     */       } 
/*     */     } 
/* 175 */     if (argIndex == -1) {
/* 176 */       throw new InvalidInjectionException(this.info, "Could not find arg matching type " + this.returnType + " for @ModifyArg target " + target + " in " + this);
/*     */     }
/*     */ 
/*     */     
/* 180 */     return argIndex;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\invoke\ModifyArgInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */