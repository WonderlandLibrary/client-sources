package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.renderer.GlStateManager;

class GuiMerchant$MerchantButton extends GuiButton {
   private final boolean field_146157_o;

   public GuiMerchant$MerchantButton(int var1, int var2, int var3, boolean var4) {
      super(var1, var2, var3, 12, 19, "");
      this.field_146157_o = var4;
   }

   public void drawButton(Minecraft var1, int var2, int var3) {
      if(this.visible) {
         var1.getTextureManager().bindTexture(GuiMerchant.access$000());
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         if((double)var2 >= this.xPosition && (double)var3 >= this.yPosition && (double)var2 < this.xPosition + (double)this.width && (double)var3 < this.yPosition + (double)this.height) {
            boolean var8 = true;
         } else {
            boolean var10000 = false;
         }

         int var5 = 0;
         int var6 = 176;
         if(!this.enabled) {
            var6 = var6 + this.width * 2;
         } else {
            var6 = var6 + this.width;
         }

         if(!this.field_146157_o) {
            var5 += this.height;
         }

         this.drawTexturedModalRect((int)this.xPosition, (int)this.yPosition, var6, var5, this.width, this.height);
      }

   }
}
