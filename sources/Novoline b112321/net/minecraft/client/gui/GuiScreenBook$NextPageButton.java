package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.renderer.GlStateManager;

class GuiScreenBook$NextPageButton extends GuiButton {
   private final boolean field_146151_o;

   public GuiScreenBook$NextPageButton(int var1, int var2, int var3, boolean var4) {
      super(var1, var2, var3, 23, 13, "");
      this.field_146151_o = var4;
   }

   public void drawButton(Minecraft var1, int var2, int var3) {
      if(this.visible) {
         if((double)var2 >= this.xPosition && (double)var3 >= this.yPosition && (double)var2 < this.xPosition + (double)this.width && (double)var3 < this.yPosition + (double)this.height) {
            boolean var8 = true;
         } else {
            boolean var10000 = false;
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         var1.getTextureManager().bindTexture(GuiScreenBook.access$000());
         int var5 = 0;
         int var6 = 192;
         var5 = var5 + 23;
         if(!this.field_146151_o) {
            var6 += 13;
         }

         this.drawTexturedModalRect((int)this.xPosition, (int)this.yPosition, var5, var6, 23, 13);
      }

   }
}
