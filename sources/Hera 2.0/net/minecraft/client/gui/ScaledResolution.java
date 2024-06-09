/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ public class ScaledResolution
/*    */ {
/*    */   private final double scaledWidthD;
/*    */   private final double scaledHeightD;
/*    */   private int scaledWidth;
/*    */   private int scaledHeight;
/*    */   private int scaleFactor;
/*    */   
/*    */   public ScaledResolution(Minecraft p_i46445_1_) {
/* 16 */     this.scaledWidth = p_i46445_1_.displayWidth;
/* 17 */     this.scaledHeight = p_i46445_1_.displayHeight;
/* 18 */     this.scaleFactor = 1;
/* 19 */     boolean flag = p_i46445_1_.isUnicode();
/* 20 */     int i = p_i46445_1_.gameSettings.guiScale;
/*    */     
/* 22 */     if (i == 0)
/*    */     {
/* 24 */       i = 1000;
/*    */     }
/*    */     
/* 27 */     while (this.scaleFactor < i && this.scaledWidth / (this.scaleFactor + 1) >= 320 && this.scaledHeight / (this.scaleFactor + 1) >= 240)
/*    */     {
/* 29 */       this.scaleFactor++;
/*    */     }
/*    */     
/* 32 */     if (flag && this.scaleFactor % 2 != 0 && this.scaleFactor != 1)
/*    */     {
/* 34 */       this.scaleFactor--;
/*    */     }
/*    */     
/* 37 */     this.scaledWidthD = this.scaledWidth / this.scaleFactor;
/* 38 */     this.scaledHeightD = this.scaledHeight / this.scaleFactor;
/* 39 */     this.scaledWidth = MathHelper.ceiling_double_int(this.scaledWidthD);
/* 40 */     this.scaledHeight = MathHelper.ceiling_double_int(this.scaledHeightD);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getScaledWidth() {
/* 45 */     return this.scaledWidth;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getScaledHeight() {
/* 50 */     return this.scaledHeight;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getScaledWidth_double() {
/* 55 */     return this.scaledWidthD;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getScaledHeight_double() {
/* 60 */     return this.scaledHeightD;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getScaleFactor() {
/* 65 */     return this.scaleFactor;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\ScaledResolution.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */