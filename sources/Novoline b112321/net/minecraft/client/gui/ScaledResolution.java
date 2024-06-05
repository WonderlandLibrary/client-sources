package net.minecraft.client.gui;

import cc.novoline.gui.screen.alt.repository.AltRepositoryGUI;
import cc.novoline.gui.screen.click.DiscordGUI;
import net.aHv;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class ScaledResolution {
   private final double scaledWidthD;
   private final double scaledHeightD;
   private int scaledWidth;
   private int scaledHeight;
   private int scaleFactor;

   public ScaledResolution(Minecraft var1) {
      this.scaledWidth = var1.displayWidth;
      this.scaledHeight = var1.displayHeight;
      this.scaleFactor = 1;
      boolean var2 = var1.isUnicode();
      if(!(var1.currentScreen instanceof AltRepositoryGUI) && !(var1.currentScreen instanceof DiscordGUI) && !(var1.currentScreen instanceof aHv)) {
         int var4 = var1.gameSettings.guiScale;
      } else {
         boolean var3 = true;
      }

      for(short var5 = 1000; this.scaleFactor < var5 && this.scaledWidth / (this.scaleFactor + 1) >= 320 && this.scaledHeight / (this.scaleFactor + 1) >= 240; ++this.scaleFactor) {
         ;
      }

      if(this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
         --this.scaleFactor;
      }

      this.scaledWidthD = (double)this.scaledWidth / (double)this.scaleFactor;
      this.scaledHeightD = (double)this.scaledHeight / (double)this.scaleFactor;
      this.scaledWidth = MathHelper.ceiling_double_int(this.scaledWidthD);
      this.scaledHeight = MathHelper.ceiling_double_int(this.scaledHeightD);
   }

   public int getScaledWidth() {
      return this.scaledWidth;
   }

   public int getScaledHeight() {
      return this.scaledHeight;
   }

   public int getScaledWidthStatic(Minecraft var1) {
      if(var1.currentScreen instanceof DiscordGUI) {
         return this.getScaledWidth();
      } else {
         switch(Minecraft.getInstance().gameSettings.guiScale) {
         case 0:
            return this.getScaledWidth() * 2;
         case 1:
            return (int)((double)this.getScaledWidth() * 0.5D);
         case 2:
         default:
            return this.getScaledWidth();
         case 3:
            return (int)((double)this.getScaledWidth() * 1.5D);
         }
      }
   }

   public int getScaledHeightStatic(Minecraft var1) {
      if(var1.currentScreen instanceof DiscordGUI) {
         return this.getScaledHeight();
      } else {
         switch(Minecraft.getInstance().gameSettings.guiScale) {
         case 0:
            return this.getScaledHeight() * 2;
         case 1:
            return (int)((double)this.getScaledHeight() * 0.5D);
         case 2:
         default:
            return this.getScaledHeight();
         case 3:
            return (int)((double)this.getScaledHeight() * 1.5D);
         }
      }
   }

   public double getScaledWidth_double() {
      return this.scaledWidthD;
   }

   public double getScaledHeight_double() {
      return this.scaledHeightD;
   }

   public int getScaleFactor() {
      return this.scaleFactor;
   }
}
