package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class ScaledResolution {
   private final double scaledWidthD;
   private int scaledHeight;
   private int scaledWidth;
   private static final String __OBFID = "CL_00000666";
   private int scaleFactor;
   private final double scaledHeightD;

   public double getScaledWidth_double() {
      return this.scaledWidthD;
   }

   public double getScaledHeight_double() {
      return this.scaledHeightD;
   }

   public int getScaledWidth() {
      return this.scaledWidth;
   }

   public int getScaleFactor() {
      return this.scaleFactor;
   }

   public int getScaledHeight() {
      return this.scaledHeight;
   }

   public ScaledResolution(Minecraft var1, int var2, int var3) {
      this.scaledWidth = var2;
      this.scaledHeight = var3;
      this.scaleFactor = 1;
      boolean var4 = var1.isUnicode();
      int var5 = var1.gameSettings.guiScale;
      if (var5 == 0) {
         var5 = 1000;
      }

      while(this.scaleFactor < var5 && this.scaledWidth / (this.scaleFactor + 1) >= 320 && this.scaledHeight / (this.scaleFactor + 1) >= 240) {
         ++this.scaleFactor;
      }

      if (var4 && this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
         --this.scaleFactor;
      }

      this.scaledWidthD = (double)this.scaledWidth / (double)this.scaleFactor;
      this.scaledHeightD = (double)this.scaledHeight / (double)this.scaleFactor;
      this.scaledWidth = MathHelper.ceiling_double_int(this.scaledWidthD);
      this.scaledHeight = MathHelper.ceiling_double_int(this.scaledHeightD);
   }
}
