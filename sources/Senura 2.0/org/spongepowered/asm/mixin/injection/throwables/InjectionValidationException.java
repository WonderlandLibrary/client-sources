/*    */ package org.spongepowered.asm.mixin.injection.throwables;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InjectionValidationException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final InjectorGroupInfo group;
/*    */   
/*    */   public InjectionValidationException(InjectorGroupInfo group, String message) {
/* 39 */     super(message);
/* 40 */     this.group = group;
/*    */   }
/*    */   
/*    */   public InjectorGroupInfo getGroup() {
/* 44 */     return this.group;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\throwables\InjectionValidationException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */