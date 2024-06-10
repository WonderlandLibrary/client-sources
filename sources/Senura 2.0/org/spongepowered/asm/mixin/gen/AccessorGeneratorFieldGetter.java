/*    */ package org.spongepowered.asm.mixin.gen;
/*    */ 
/*    */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*    */ import org.spongepowered.asm.lib.tree.FieldInsnNode;
/*    */ import org.spongepowered.asm.lib.tree.InsnNode;
/*    */ import org.spongepowered.asm.lib.tree.MethodNode;
/*    */ import org.spongepowered.asm.lib.tree.VarInsnNode;
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
/*    */ public class AccessorGeneratorFieldGetter
/*    */   extends AccessorGeneratorField
/*    */ {
/*    */   public AccessorGeneratorFieldGetter(AccessorInfo info) {
/* 39 */     super(info);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MethodNode generate() {
/* 47 */     MethodNode method = createMethod(this.targetType.getSize(), this.targetType.getSize());
/* 48 */     if (this.isInstanceField) {
/* 49 */       method.instructions.add((AbstractInsnNode)new VarInsnNode(25, 0));
/*    */     }
/* 51 */     int opcode = this.isInstanceField ? 180 : 178;
/* 52 */     method.instructions.add((AbstractInsnNode)new FieldInsnNode(opcode, (this.info.getClassNode()).name, this.targetField.name, this.targetField.desc));
/* 53 */     method.instructions.add((AbstractInsnNode)new InsnNode(this.targetType.getOpcode(172)));
/* 54 */     return method;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\gen\AccessorGeneratorFieldGetter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */