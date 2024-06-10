/*    */ package org.spongepowered.tools.obfuscation.mirror;
/*    */ 
/*    */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MemberHandle<T extends IMapping<T>>
/*    */ {
/*    */   private final String owner;
/*    */   private final String name;
/*    */   private final String desc;
/*    */   
/*    */   protected MemberHandle(String owner, String name, String desc) {
/* 43 */     this.owner = owner;
/* 44 */     this.name = name;
/* 45 */     this.desc = desc;
/*    */   }
/*    */   
/*    */   public final String getOwner() {
/* 49 */     return this.owner;
/*    */   }
/*    */   
/*    */   public final String getName() {
/* 53 */     return this.name;
/*    */   }
/*    */   
/*    */   public final String getDesc() {
/* 57 */     return this.desc;
/*    */   }
/*    */   
/*    */   public abstract Visibility getVisibility();
/*    */   
/*    */   public abstract T asMapping(boolean paramBoolean);
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\mirror\MemberHandle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */