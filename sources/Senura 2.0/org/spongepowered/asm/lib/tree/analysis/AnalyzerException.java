/*    */ package org.spongepowered.asm.lib.tree.analysis;
/*    */ 
/*    */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnalyzerException
/*    */   extends Exception
/*    */ {
/*    */   public final AbstractInsnNode node;
/*    */   
/*    */   public AnalyzerException(AbstractInsnNode node, String msg) {
/* 46 */     super(msg);
/* 47 */     this.node = node;
/*    */   }
/*    */ 
/*    */   
/*    */   public AnalyzerException(AbstractInsnNode node, String msg, Throwable exception) {
/* 52 */     super(msg, exception);
/* 53 */     this.node = node;
/*    */   }
/*    */ 
/*    */   
/*    */   public AnalyzerException(AbstractInsnNode node, String msg, Object expected, Value encountered) {
/* 58 */     super(((msg == null) ? "Expected " : (msg + ": expected ")) + expected + ", but found " + encountered);
/*    */     
/* 60 */     this.node = node;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\analysis\AnalyzerException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */