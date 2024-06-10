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
/*    */ 
/*    */ 
/*    */ public class IincInsnNode
/*    */   extends AbstractInsnNode
/*    */ {
/*    */   public int var;
/*    */   public int incr;
/*    */   
/*    */   public IincInsnNode(int var, int incr) {
/* 63 */     super(132);
/* 64 */     this.var = var;
/* 65 */     this.incr = incr;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getType() {
/* 70 */     return 10;
/*    */   }
/*    */ 
/*    */   
/*    */   public void accept(MethodVisitor mv) {
/* 75 */     mv.visitIincInsn(this.var, this.incr);
/* 76 */     acceptAnnotations(mv);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) {
/* 81 */     return (new IincInsnNode(this.var, this.incr)).cloneAnnotations(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\IincInsnNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */