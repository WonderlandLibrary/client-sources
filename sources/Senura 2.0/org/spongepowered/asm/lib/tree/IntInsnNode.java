/*    */ package org.spongepowered.asm.lib.tree;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.spongepowered.asm.lib.MethodVisitor;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IntInsnNode
/*    */   extends AbstractInsnNode
/*    */ {
/*    */   public int operand;
/*    */   
/*    */   public IntInsnNode(int opcode, int operand) {
/* 58 */     super(opcode);
/* 59 */     this.operand = operand;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setOpcode(int opcode) {
/* 70 */     this.opcode = opcode;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getType() {
/* 75 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void accept(MethodVisitor mv) {
/* 80 */     mv.visitIntInsn(this.opcode, this.operand);
/* 81 */     acceptAnnotations(mv);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) {
/* 86 */     return (new IntInsnNode(this.opcode, this.operand)).cloneAnnotations(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\IntInsnNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */