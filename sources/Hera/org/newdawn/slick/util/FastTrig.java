/*    */ package org.newdawn.slick.util;
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
/*    */ public class FastTrig
/*    */ {
/*    */   private static double reduceSinAngle(double radians) {
/* 19 */     double orig = radians;
/* 20 */     radians %= 6.283185307179586D;
/* 21 */     if (Math.abs(radians) > Math.PI) {
/* 22 */       radians -= 6.283185307179586D;
/*    */     }
/* 24 */     if (Math.abs(radians) > 1.5707963267948966D) {
/* 25 */       radians = Math.PI - radians;
/*    */     }
/*    */     
/* 28 */     return radians;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static double sin(double radians) {
/* 38 */     radians = reduceSinAngle(radians);
/* 39 */     if (Math.abs(radians) <= 0.7853981633974483D) {
/* 40 */       return Math.sin(radians);
/*    */     }
/* 42 */     return Math.cos(1.5707963267948966D - radians);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static double cos(double radians) {
/* 53 */     return sin(radians + 1.5707963267948966D);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\FastTrig.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */