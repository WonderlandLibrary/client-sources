/*    */ package org.spongepowered.asm.util.asm;
/*    */ 
/*    */ import org.spongepowered.asm.lib.MethodVisitor;
/*    */ import org.spongepowered.asm.util.Bytecode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MethodVisitorEx
/*    */   extends MethodVisitor
/*    */ {
/*    */   public MethodVisitorEx(MethodVisitor mv) {
/* 37 */     super(327680, mv);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void visitConstant(byte constant) {
/* 47 */     if (constant > -2 && constant < 6) {
/* 48 */       visitInsn(Bytecode.CONSTANTS_INT[constant + 1]);
/*    */       return;
/*    */     } 
/* 51 */     visitIntInsn(16, constant);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\as\\util\asm\MethodVisitorEx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */