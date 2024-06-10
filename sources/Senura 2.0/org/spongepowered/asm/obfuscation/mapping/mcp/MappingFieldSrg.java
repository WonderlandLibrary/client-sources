/*    */ package org.spongepowered.asm.obfuscation.mapping.mcp;
/*    */ 
/*    */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MappingFieldSrg
/*    */   extends MappingField
/*    */ {
/*    */   private final String srg;
/*    */   
/*    */   public MappingFieldSrg(String srg) {
/* 37 */     super(getOwnerFromSrg(srg), getNameFromSrg(srg), null);
/* 38 */     this.srg = srg;
/*    */   }
/*    */   
/*    */   public MappingFieldSrg(MappingField field) {
/* 42 */     super(field.getOwner(), field.getName(), null);
/* 43 */     this.srg = field.getOwner() + "/" + field.getName();
/*    */   }
/*    */ 
/*    */   
/*    */   public String serialise() {
/* 48 */     return this.srg;
/*    */   }
/*    */   
/*    */   private static String getNameFromSrg(String srg) {
/* 52 */     if (srg == null) {
/* 53 */       return null;
/*    */     }
/* 55 */     int pos = srg.lastIndexOf('/');
/* 56 */     return (pos > -1) ? srg.substring(pos + 1) : srg;
/*    */   }
/*    */   
/*    */   private static String getOwnerFromSrg(String srg) {
/* 60 */     if (srg == null) {
/* 61 */       return null;
/*    */     }
/* 63 */     int pos = srg.lastIndexOf('/');
/* 64 */     return (pos > -1) ? srg.substring(0, pos) : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\obfuscation\mapping\mcp\MappingFieldSrg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */