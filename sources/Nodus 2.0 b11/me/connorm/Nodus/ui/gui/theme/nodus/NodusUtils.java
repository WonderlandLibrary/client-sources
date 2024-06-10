/*  1:   */ package me.connorm.Nodus.ui.gui.theme.nodus;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.utils.RenderUtils;
/*  4:   */ 
/*  5:   */ public class NodusUtils
/*  6:   */ {
/*  7:   */   public static void drawNodusButton(float par1, float par2, float par3, float par4, boolean isHighlighted)
/*  8:   */   {
/*  9:10 */     RenderUtils.drawRect(par1, par2, par3, par4, 1627389951);
/* 10:11 */     RenderUtils.drawRect(par1 + 1.0F, par2 + 1.0F, par3 - 1.0F, par4 - 1.0F, isHighlighted ? 1627389951 : -1728053248);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public static void drawNodusRect(float par1, float par2, float par3, float par4)
/* 14:   */   {
/* 15:16 */     RenderUtils.drawRect(par1, par2, par3, par4, 553648127);
/* 16:17 */     RenderUtils.drawRect(par1 + 2.0F, par2, par3 - 2.0F, par4 - 2.0F, -2147483648);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static void drawNodusConsole(float par1, float par2, float par3, float par4)
/* 20:   */   {
/* 21:22 */     RenderUtils.drawRect(par1 - 2.0F, par2 - 2.0F, par3 + 2.0F, par4 + 2.0F, 553648127);
/* 22:23 */     RenderUtils.drawRect(par1, par2, par3, par4, -2147483648);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static void drawTopNodusRect(float par1, float par2, float par3, float par4)
/* 26:   */   {
/* 27:28 */     RenderUtils.drawRect(par1, par2, par3, par4, 553648127);
/* 28:29 */     RenderUtils.drawRect(par1 + 2.0F, par2 + 2.0F, par3 - 2.0F, par4 - 2.0F, -1728053248);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static void drawNodusTabRect(float par1, float par2, float par3, float par4)
/* 32:   */   {
/* 33:34 */     RenderUtils.drawRect(par1 - 2.0F, par2 - 2.0F, par3 + 2.0F, par4 + 2.0F, 553648127);
/* 34:35 */     RenderUtils.drawRect(par1, par2, par3, par4, -1728053248);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void drawSmallNodusRect(float par1, float par2, float par3, float par4)
/* 38:   */   {
/* 39:40 */     RenderUtils.drawRect(par1, par2, par3, par4, 553648127);
/* 40:41 */     RenderUtils.drawRect(par1 + 1.0F, par2 + 1.0F, par3 - 1.0F, par4 - 1.0F, -2147483648);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public static void drawSmallNodusButton(float par1, float par2, float par3, float par4, int fillColor)
/* 44:   */   {
/* 45:46 */     RenderUtils.drawRect(par1 - 1.0F, par2 - 1.0F, par3 + 1.0F, par4 + 1.0F, 553648127);
/* 46:47 */     RenderUtils.drawRect(par1, par2, par3, par4, fillColor);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static void drawNodusNametag(int par1, int par2, int par3, int par4, int fillColor)
/* 50:   */   {
/* 51:52 */     RenderUtils.drawRect(par1, par2, par3, par4, 553648127);
/* 52:53 */     RenderUtils.drawRect(par1 + 2, par2 + 2, par3 - 2, par4 - 2, fillColor);
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.gui.theme.nodus.NodusUtils
 * JD-Core Version:    0.7.0.1
 */