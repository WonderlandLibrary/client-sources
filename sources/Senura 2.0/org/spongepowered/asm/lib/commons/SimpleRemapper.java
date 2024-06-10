/*    */ package org.spongepowered.asm.lib.commons;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimpleRemapper
/*    */   extends Remapper
/*    */ {
/*    */   private final Map<String, String> mapping;
/*    */   
/*    */   public SimpleRemapper(Map<String, String> mapping) {
/* 46 */     this.mapping = mapping;
/*    */   }
/*    */   
/*    */   public SimpleRemapper(String oldName, String newName) {
/* 50 */     this.mapping = Collections.singletonMap(oldName, newName);
/*    */   }
/*    */ 
/*    */   
/*    */   public String mapMethodName(String owner, String name, String desc) {
/* 55 */     String s = map(owner + '.' + name + desc);
/* 56 */     return (s == null) ? name : s;
/*    */   }
/*    */ 
/*    */   
/*    */   public String mapInvokeDynamicMethodName(String name, String desc) {
/* 61 */     String s = map('.' + name + desc);
/* 62 */     return (s == null) ? name : s;
/*    */   }
/*    */ 
/*    */   
/*    */   public String mapFieldName(String owner, String name, String desc) {
/* 67 */     String s = map(owner + '.' + name);
/* 68 */     return (s == null) ? name : s;
/*    */   }
/*    */ 
/*    */   
/*    */   public String map(String key) {
/* 73 */     return this.mapping.get(key);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\commons\SimpleRemapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */