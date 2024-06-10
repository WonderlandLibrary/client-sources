/*     */ package org.spongepowered.asm.mixin.injection.invoke;
/*     */ 
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.FieldInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.InsnNode;
/*     */ import org.spongepowered.asm.lib.tree.JumpInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.LocalVariableNode;
/*     */ import org.spongepowered.asm.lib.tree.VarInsnNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*     */ import org.spongepowered.asm.mixin.injection.invoke.util.InsnFinder;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ import org.spongepowered.asm.util.Locals;
/*     */ import org.spongepowered.asm.util.SignaturePrinter;
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
/*     */ public class ModifyConstantInjector
/*     */   extends RedirectInjector
/*     */ {
/*     */   private static final int OPCODE_OFFSET = 6;
/*     */   
/*     */   public ModifyConstantInjector(InjectionInfo info) {
/*  63 */     super(info, "@ModifyConstant");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void inject(Target target, InjectionNodes.InjectionNode node) {
/*  68 */     if (!preInject(node)) {
/*     */       return;
/*     */     }
/*     */     
/*  72 */     if (node.isReplaced()) {
/*  73 */       throw new UnsupportedOperationException("Target failure for " + this.info);
/*     */     }
/*     */     
/*  76 */     AbstractInsnNode targetNode = node.getCurrentTarget();
/*  77 */     if (targetNode instanceof JumpInsnNode) {
/*  78 */       checkTargetModifiers(target, false);
/*  79 */       injectExpandedConstantModifier(target, (JumpInsnNode)targetNode);
/*     */       
/*     */       return;
/*     */     } 
/*  83 */     if (Bytecode.isConstant(targetNode)) {
/*  84 */       checkTargetModifiers(target, false);
/*  85 */       injectConstantModifier(target, targetNode);
/*     */       
/*     */       return;
/*     */     } 
/*  89 */     throw new InvalidInjectionException(this.info, this.annotationType + " annotation is targetting an invalid insn in " + target + " in " + this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void injectExpandedConstantModifier(Target target, JumpInsnNode jumpNode) {
/* 100 */     int opcode = jumpNode.getOpcode();
/* 101 */     if (opcode < 155 || opcode > 158) {
/* 102 */       throw new InvalidInjectionException(this.info, this.annotationType + " annotation selected an invalid opcode " + 
/* 103 */           Bytecode.getOpcodeName(opcode) + " in " + target + " in " + this);
/*     */     }
/*     */     
/* 106 */     InsnList insns = new InsnList();
/* 107 */     insns.add((AbstractInsnNode)new InsnNode(3));
/* 108 */     AbstractInsnNode invoke = invokeConstantHandler(Type.getType("I"), target, insns, insns);
/* 109 */     insns.add((AbstractInsnNode)new JumpInsnNode(opcode + 6, jumpNode.label));
/* 110 */     target.replaceNode((AbstractInsnNode)jumpNode, invoke, insns);
/* 111 */     target.addToStack(1);
/*     */   }
/*     */   
/*     */   private void injectConstantModifier(Target target, AbstractInsnNode constNode) {
/* 115 */     Type constantType = Bytecode.getConstantType(constNode);
/*     */     
/* 117 */     if (constantType.getSort() <= 5 && this.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
/* 118 */       checkNarrowing(target, constNode, constantType);
/*     */     }
/*     */     
/* 121 */     InsnList before = new InsnList();
/* 122 */     InsnList after = new InsnList();
/* 123 */     AbstractInsnNode invoke = invokeConstantHandler(constantType, target, before, after);
/* 124 */     target.wrapNode(constNode, invoke, before, after);
/*     */   }
/*     */   
/*     */   private AbstractInsnNode invokeConstantHandler(Type constantType, Target target, InsnList before, InsnList after) {
/* 128 */     String handlerDesc = Bytecode.generateDescriptor(constantType, new Object[] { constantType });
/* 129 */     boolean withArgs = checkDescriptor(handlerDesc, target, "getter");
/*     */     
/* 131 */     if (!this.isStatic) {
/* 132 */       before.insert((AbstractInsnNode)new VarInsnNode(25, 0));
/* 133 */       target.addToStack(1);
/*     */     } 
/*     */     
/* 136 */     if (withArgs) {
/* 137 */       pushArgs(target.arguments, after, target.getArgIndices(), 0, target.arguments.length);
/* 138 */       target.addToStack(Bytecode.getArgsSize(target.arguments));
/*     */     } 
/*     */     
/* 141 */     return invokeHandler(after);
/*     */   }
/*     */   
/*     */   private void checkNarrowing(Target target, AbstractInsnNode constNode, Type constantType) {
/* 145 */     AbstractInsnNode pop = (new InsnFinder()).findPopInsn(target, constNode);
/*     */     
/* 147 */     if (pop == null)
/*     */       return; 
/* 149 */     if (pop instanceof FieldInsnNode) {
/* 150 */       FieldInsnNode fieldNode = (FieldInsnNode)pop;
/* 151 */       Type fieldType = Type.getType(fieldNode.desc);
/* 152 */       checkNarrowing(target, constNode, constantType, fieldType, target.indexOf(pop), String.format("%s %s %s.%s", new Object[] {
/* 153 */               Bytecode.getOpcodeName(pop), SignaturePrinter.getTypeName(fieldType, false), fieldNode.owner.replace('/', '.'), fieldNode.name }));
/* 154 */     } else if (pop.getOpcode() == 172) {
/* 155 */       checkNarrowing(target, constNode, constantType, target.returnType, target.indexOf(pop), "RETURN " + 
/* 156 */           SignaturePrinter.getTypeName(target.returnType, false));
/* 157 */     } else if (pop.getOpcode() == 54) {
/* 158 */       int var = ((VarInsnNode)pop).var;
/* 159 */       LocalVariableNode localVar = Locals.getLocalVariableAt(target.classNode, target.method, pop, var);
/*     */ 
/*     */ 
/*     */       
/* 163 */       if (localVar != null && localVar.desc != null) {
/* 164 */         String name = (localVar.name != null) ? localVar.name : "unnamed";
/* 165 */         Type localType = Type.getType(localVar.desc);
/* 166 */         checkNarrowing(target, constNode, constantType, localType, target.indexOf(pop), String.format("ISTORE[var=%d] %s %s", new Object[] { Integer.valueOf(var), 
/* 167 */                 SignaturePrinter.getTypeName(localType, false), name }));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkNarrowing(Target target, AbstractInsnNode constNode, Type constantType, Type type, int index, String description) {
/* 173 */     int fromSort = constantType.getSort();
/* 174 */     int toSort = type.getSort();
/* 175 */     if (toSort < fromSort) {
/* 176 */       String fromType = SignaturePrinter.getTypeName(constantType, false);
/* 177 */       String toType = SignaturePrinter.getTypeName(type, false);
/* 178 */       String message = (toSort == 1) ? ". Implicit conversion to <boolean> can cause nondeterministic (JVM-specific) behaviour!" : "";
/* 179 */       Level level = (toSort == 1) ? Level.ERROR : Level.WARN;
/* 180 */       Injector.logger.log(level, "Narrowing conversion of <{}> to <{}> in {} target {} at opcode {} ({}){}", new Object[] { fromType, toType, this.info, target, 
/* 181 */             Integer.valueOf(index), description, message });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\invoke\ModifyConstantInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */