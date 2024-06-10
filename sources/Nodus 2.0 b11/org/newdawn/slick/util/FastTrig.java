/*  1:   */ package org.newdawn.slick.util;
/*  2:   */ 
/*  3:   */ public class FastTrig
/*  4:   */ {
/*  5:   */   private static double reduceSinAngle(double radians)
/*  6:   */   {
/*  7:19 */     double orig = radians;
/*  8:20 */     radians %= 6.283185307179586D;
/*  9:21 */     if (Math.abs(radians) > 3.141592653589793D) {
/* 10:22 */       radians -= 6.283185307179586D;
/* 11:   */     }
/* 12:24 */     if (Math.abs(radians) > 1.570796326794897D) {
/* 13:25 */       radians = 3.141592653589793D - radians;
/* 14:   */     }
/* 15:28 */     return radians;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public static double sin(double radians)
/* 19:   */   {
/* 20:38 */     radians = reduceSinAngle(radians);
/* 21:39 */     if (Math.abs(radians) <= 0.7853981633974483D) {
/* 22:40 */       return Math.sin(radians);
/* 23:   */     }
/* 24:42 */     return Math.cos(1.570796326794897D - radians);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static double cos(double radians)
/* 28:   */   {
/* 29:53 */     return sin(radians + 1.570796326794897D);
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.FastTrig
 * JD-Core Version:    0.7.0.1
 */