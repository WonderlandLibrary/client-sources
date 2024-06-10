/*    */ package org.spongepowered.asm.util;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class JavaVersion
/*    */ {
/* 35 */   private static double current = 0.0D;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static double current() {
/* 43 */     if (current == 0.0D) {
/* 44 */       current = resolveCurrentVersion();
/*    */     }
/* 46 */     return current;
/*    */   }
/*    */   
/*    */   private static double resolveCurrentVersion() {
/* 50 */     String version = System.getProperty("java.version");
/* 51 */     Matcher matcher = Pattern.compile("[0-9]+\\.[0-9]+").matcher(version);
/* 52 */     if (matcher.find()) {
/* 53 */       return Double.parseDouble(matcher.group());
/*    */     }
/* 55 */     return 1.6D;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\as\\util\JavaVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */