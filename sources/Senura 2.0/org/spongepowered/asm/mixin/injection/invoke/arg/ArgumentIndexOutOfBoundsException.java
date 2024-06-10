/*    */ package org.spongepowered.asm.mixin.injection.invoke.arg;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArgumentIndexOutOfBoundsException
/*    */   extends IndexOutOfBoundsException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public ArgumentIndexOutOfBoundsException(int index) {
/* 36 */     super("Argument index is out of bounds: " + index);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\invoke\arg\ArgumentIndexOutOfBoundsException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */