/*    */ package org.spongepowered.asm.mixin.gen;
/*    */ 
/*    */ import org.spongepowered.asm.lib.Type;
/*    */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*    */ import org.spongepowered.asm.lib.tree.InsnNode;
/*    */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*    */ import org.spongepowered.asm.lib.tree.MethodNode;
/*    */ import org.spongepowered.asm.lib.tree.VarInsnNode;
/*    */ import org.spongepowered.asm.util.Bytecode;
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
/*    */ public class AccessorGeneratorMethodProxy
/*    */   extends AccessorGenerator
/*    */ {
/*    */   private final MethodNode targetMethod;
/*    */   private final Type[] argTypes;
/*    */   private final Type returnType;
/*    */   private final boolean isInstanceMethod;
/*    */   
/*    */   public AccessorGeneratorMethodProxy(AccessorInfo info) {
/* 61 */     super(info);
/* 62 */     this.targetMethod = info.getTargetMethod();
/* 63 */     this.argTypes = info.getArgTypes();
/* 64 */     this.returnType = info.getReturnType();
/* 65 */     this.isInstanceMethod = !Bytecode.hasFlag(this.targetMethod, 8);
/*    */   }
/*    */ 
/*    */   
/*    */   public MethodNode generate() {
/* 70 */     int size = Bytecode.getArgsSize(this.argTypes) + this.returnType.getSize() + (this.isInstanceMethod ? 1 : 0);
/* 71 */     MethodNode method = createMethod(size, size);
/* 72 */     if (this.isInstanceMethod) {
/* 73 */       method.instructions.add((AbstractInsnNode)new VarInsnNode(25, 0));
/*    */     }
/* 75 */     Bytecode.loadArgs(this.argTypes, method.instructions, this.isInstanceMethod ? 1 : 0);
/* 76 */     boolean isPrivate = Bytecode.hasFlag(this.targetMethod, 2);
/* 77 */     int opcode = this.isInstanceMethod ? (isPrivate ? 183 : 182) : 184;
/* 78 */     method.instructions.add((AbstractInsnNode)new MethodInsnNode(opcode, (this.info.getClassNode()).name, this.targetMethod.name, this.targetMethod.desc, false));
/* 79 */     method.instructions.add((AbstractInsnNode)new InsnNode(this.returnType.getOpcode(172)));
/* 80 */     return method;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\gen\AccessorGeneratorMethodProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */