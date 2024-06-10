/*    */ package org.spongepowered.asm.lib.tree;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.spongepowered.asm.lib.Label;
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
/*    */ public class LabelNode
/*    */   extends AbstractInsnNode
/*    */ {
/*    */   private Label label;
/*    */   
/*    */   public LabelNode() {
/* 45 */     super(-1);
/*    */   }
/*    */   
/*    */   public LabelNode(Label label) {
/* 49 */     super(-1);
/* 50 */     this.label = label;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getType() {
/* 55 */     return 8;
/*    */   }
/*    */   
/*    */   public Label getLabel() {
/* 59 */     if (this.label == null) {
/* 60 */       this.label = new Label();
/*    */     }
/* 62 */     return this.label;
/*    */   }
/*    */ 
/*    */   
/*    */   public void accept(MethodVisitor cv) {
/* 67 */     cv.visitLabel(getLabel());
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) {
/* 72 */     return labels.get(this);
/*    */   }
/*    */   
/*    */   public void resetLabel() {
/* 76 */     this.label = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\LabelNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */