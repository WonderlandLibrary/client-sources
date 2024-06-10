/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.settings.GameSettings;
/*  4:   */ import net.minecraft.util.MathHelper;
/*  5:   */ 
/*  6:   */ public class ScaledResolution
/*  7:   */ {
/*  8:   */   private int scaledWidth;
/*  9:   */   private int scaledHeight;
/* 10:   */   private double scaledWidthD;
/* 11:   */   private double scaledHeightD;
/* 12:   */   private int scaleFactor;
/* 13:   */   private static final String __OBFID = "CL_00000666";
/* 14:   */   
/* 15:   */   public ScaledResolution(GameSettings par1GameSettings, int par2, int par3)
/* 16:   */   {
/* 17:17 */     this.scaledWidth = par2;
/* 18:18 */     this.scaledHeight = par3;
/* 19:19 */     this.scaleFactor = 1;
/* 20:20 */     int var4 = par1GameSettings.guiScale;
/* 21:22 */     if (var4 == 0) {
/* 22:24 */       var4 = 1000;
/* 23:   */     }
/* 24:27 */     while ((this.scaleFactor < var4) && (this.scaledWidth / (this.scaleFactor + 1) >= 320) && (this.scaledHeight / (this.scaleFactor + 1) >= 240)) {
/* 25:29 */       this.scaleFactor += 1;
/* 26:   */     }
/* 27:32 */     this.scaledWidthD = (this.scaledWidth / this.scaleFactor);
/* 28:33 */     this.scaledHeightD = (this.scaledHeight / this.scaleFactor);
/* 29:34 */     this.scaledWidth = MathHelper.ceiling_double_int(this.scaledWidthD);
/* 30:35 */     this.scaledHeight = MathHelper.ceiling_double_int(this.scaledHeightD);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public int getScaledWidth()
/* 34:   */   {
/* 35:40 */     return this.scaledWidth;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public int getScaledHeight()
/* 39:   */   {
/* 40:45 */     return this.scaledHeight;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public double getScaledWidth_double()
/* 44:   */   {
/* 45:50 */     return this.scaledWidthD;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public double getScaledHeight_double()
/* 49:   */   {
/* 50:55 */     return this.scaledHeightD;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public int getScaleFactor()
/* 54:   */   {
/* 55:60 */     return this.scaleFactor;
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.ScaledResolution
 * JD-Core Version:    0.7.0.1
 */