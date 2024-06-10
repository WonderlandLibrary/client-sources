/*    */ package org.spongepowered.asm.lib.tree;
/*    */ 
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
/*    */ public class ParameterNode
/*    */ {
/*    */   public String name;
/*    */   public int access;
/*    */   
/*    */   public ParameterNode(String name, int access) {
/* 63 */     this.name = name;
/* 64 */     this.access = access;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void accept(MethodVisitor mv) {
/* 74 */     mv.visitParameter(this.name, this.access);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\ParameterNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */