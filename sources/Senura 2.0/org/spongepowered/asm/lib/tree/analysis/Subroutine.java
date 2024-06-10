/*    */ package org.spongepowered.asm.lib.tree.analysis;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.spongepowered.asm.lib.tree.JumpInsnNode;
/*    */ import org.spongepowered.asm.lib.tree.LabelNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Subroutine
/*    */ {
/*    */   LabelNode start;
/*    */   boolean[] access;
/*    */   List<JumpInsnNode> callers;
/*    */   
/*    */   private Subroutine() {}
/*    */   
/*    */   Subroutine(LabelNode start, int maxLocals, JumpInsnNode caller) {
/* 56 */     this.start = start;
/* 57 */     this.access = new boolean[maxLocals];
/* 58 */     this.callers = new ArrayList<JumpInsnNode>();
/* 59 */     this.callers.add(caller);
/*    */   }
/*    */   
/*    */   public Subroutine copy() {
/* 63 */     Subroutine result = new Subroutine();
/* 64 */     result.start = this.start;
/* 65 */     result.access = new boolean[this.access.length];
/* 66 */     System.arraycopy(this.access, 0, result.access, 0, this.access.length);
/* 67 */     result.callers = new ArrayList<JumpInsnNode>(this.callers);
/* 68 */     return result;
/*    */   }
/*    */   
/*    */   public boolean merge(Subroutine subroutine) throws AnalyzerException {
/* 72 */     boolean changes = false; int i;
/* 73 */     for (i = 0; i < this.access.length; i++) {
/* 74 */       if (subroutine.access[i] && !this.access[i]) {
/* 75 */         this.access[i] = true;
/* 76 */         changes = true;
/*    */       } 
/*    */     } 
/* 79 */     if (subroutine.start == this.start) {
/* 80 */       for (i = 0; i < subroutine.callers.size(); i++) {
/* 81 */         JumpInsnNode caller = subroutine.callers.get(i);
/* 82 */         if (!this.callers.contains(caller)) {
/* 83 */           this.callers.add(caller);
/* 84 */           changes = true;
/*    */         } 
/*    */       } 
/*    */     }
/* 88 */     return changes;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\analysis\Subroutine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */