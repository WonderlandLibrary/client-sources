/*    */ package org.spongepowered.asm.lib.tree.analysis;
/*    */ 
/*    */ import java.util.Set;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SourceValue
/*    */   implements Value
/*    */ {
/*    */   public final int size;
/*    */   public final Set<AbstractInsnNode> insns;
/*    */   
/*    */   public SourceValue(int size) {
/* 67 */     this(size, SmallSet.emptySet());
/*    */   }
/*    */   
/*    */   public SourceValue(int size, AbstractInsnNode insn) {
/* 71 */     this.size = size;
/* 72 */     this.insns = new SmallSet<AbstractInsnNode>(insn, null);
/*    */   }
/*    */   
/*    */   public SourceValue(int size, Set<AbstractInsnNode> insns) {
/* 76 */     this.size = size;
/* 77 */     this.insns = insns;
/*    */   }
/*    */   
/*    */   public int getSize() {
/* 81 */     return this.size;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object value) {
/* 86 */     if (!(value instanceof SourceValue)) {
/* 87 */       return false;
/*    */     }
/* 89 */     SourceValue v = (SourceValue)value;
/* 90 */     return (this.size == v.size && this.insns.equals(v.insns));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return this.insns.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\analysis\SourceValue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */