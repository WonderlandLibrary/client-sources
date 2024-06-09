/*    */ package me.eagler.font;
/*    */ 
/*    */ import java.awt.Font;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FontHelper
/*    */ {
/*    */   public static ClientFontRenderer cfClickGui;
/*    */   public static ClientFontRenderer cfArrayList;
/*    */   public static ClientFontRenderer cfSmall;
/*    */   public static ClientFontRenderer cfButton;
/*    */   public static ClientFontRenderer cfBig;
/*    */   
/*    */   public static void loadFonts() {
/*    */     try {
/* 17 */       cfClickGui = new ClientFontRenderer(new Font("Sans", 1, 46), true, 8);
/* 18 */       cfArrayList = new ClientFontRenderer(new Font("Sans", 1, 36), true, 8);
/* 19 */       cfSmall = new ClientFontRenderer(new Font("Sans", 1, 30), true, 8);
/* 20 */       cfButton = new ClientFontRenderer(new Font("Sans", 1, 40), true, 8);
/* 21 */       cfBig = new ClientFontRenderer(new Font("Sans", 1, 60), true, 8);
/*    */     }
/* 23 */     catch (Exception exception) {}
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\font\FontHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */