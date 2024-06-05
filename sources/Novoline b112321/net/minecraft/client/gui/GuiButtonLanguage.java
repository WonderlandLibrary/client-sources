package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class GuiButtonLanguage extends GuiButton {
   public GuiButtonLanguage(int var1, int var2, int var3) {
      super(var1, var2, var3, 20, 20, "");
   }

   public void drawButton(Minecraft var1, int var2, int var3) {
      if(this.visible) {
         var1.getTextureManager().bindTexture(GuiButton.buttonTextures);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         if((double)var2 >= this.xPosition && (double)var3 >= this.yPosition && (double)var2 < this.xPosition + (double)this.width && (double)var3 < this.yPosition + (double)this.height) {
            boolean var7 = true;
         } else {
            boolean var10000 = false;
         }

         int var5 = 106;
         var5 = var5 + this.height;
         this.drawTexturedModalRect((int)this.xPosition, (int)this.yPosition, 0, var5, this.width, this.height);
      }

   }
}
