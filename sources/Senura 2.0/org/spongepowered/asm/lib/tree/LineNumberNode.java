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
/*    */ 
/*    */ 
/*    */ public class LineNumberNode
/*    */   extends AbstractInsnNode
/*    */ {
/*    */   public int line;
/*    */   public LabelNode start;
/*    */   
/*    */   public LineNumberNode(int line, LabelNode start) {
/* 65 */     super(-1);
/* 66 */     this.line = line;
/* 67 */     this.start = start;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getType() {
/* 72 */     return 15;
/*    */   }
/*    */ 
/*    */   
/*    */   public void accept(MethodVisitor mv) {
/* 77 */     mv.visitLineNumber(this.line, this.start.getLabel());
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) {
/* 82 */     return new LineNumberNode(this.line, clone(this.start, labels));
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\LineNumberNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */