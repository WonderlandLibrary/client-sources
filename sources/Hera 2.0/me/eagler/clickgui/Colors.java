/*    */ package me.eagler.clickgui;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.eagler.Client;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Colors
/*    */ {
/*    */   public static Color getPrimary() {
/* 11 */     if (Client.instance.getUtils().isTheme("Dark"))
/*    */     {
/* 13 */       return Color.DARK_GRAY;
/*    */     }
/* 15 */     if (Client.instance.getUtils().isTheme("Bright"))
/*    */     {
/* 17 */       return Color.WHITE;
/*    */     }
/*    */ 
/*    */     
/* 21 */     return Color.DARK_GRAY;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static Color getSecondary() {
/* 27 */     if (Client.instance.getUtils().isTheme("Dark"))
/*    */     {
/* 29 */       return Color.gray;
/*    */     }
/* 31 */     if (Client.instance.getUtils().isTheme("Bright"))
/*    */     {
/* 33 */       return new Color(180, 180, 180, 255);
/*    */     }
/*    */ 
/*    */     
/* 37 */     return Color.gray;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static Color getText() {
/* 43 */     if (Client.instance.getUtils().isTheme("Dark"))
/*    */     {
/* 45 */       return Color.white;
/*    */     }
/* 47 */     if (Client.instance.getUtils().isTheme("Bright"))
/*    */     {
/* 49 */       return Color.black;
/*    */     }
/*    */ 
/*    */     
/* 53 */     return Color.white;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\clickgui\Colors.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */