/*  1:   */ package me.connorm.Nodus.font;
/*  2:   */ 
/*  3:   */ import java.awt.Font;
/*  4:   */ 
/*  5:   */ public class Fonts
/*  6:   */ {
/*  7:   */   public static UnicodeFontRenderer fontRenderer;
/*  8:   */   public static UnicodeFontRenderer mediumFontRenderer;
/*  9:   */   public static UnicodeFontRenderer largeFontRenderer;
/* 10:   */   
/* 11:   */   public static void loadFonts()
/* 12:   */   {
/* 13:13 */     fontRenderer = new UnicodeFontRenderer(new Font("Minecraft", 0, 75));
/* 14:   */   }
/* 15:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.font.Fonts
 * JD-Core Version:    0.7.0.1
 */