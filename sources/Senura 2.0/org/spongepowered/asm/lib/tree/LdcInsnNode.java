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
/*    */ 
/*    */ 
/*    */ public class LdcInsnNode
/*    */   extends AbstractInsnNode
/*    */ {
/*    */   public Object cst;
/*    */   
/*    */   public LdcInsnNode(Object cst) {
/* 60 */     super(18);
/* 61 */     this.cst = cst;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getType() {
/* 66 */     return 9;
/*    */   }
/*    */ 
/*    */   
/*    */   public void accept(MethodVisitor mv) {
/* 71 */     mv.visitLdcInsn(this.cst);
/* 72 */     acceptAnnotations(mv);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) {
/* 77 */     return (new LdcInsnNode(this.cst)).cloneAnnotations(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\LdcInsnNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */