package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLockIconButton$Icon;
import net.minecraft.client.renderer.GlStateManager;

public class GuiLockIconButton extends GuiButton {
   private boolean field_175231_o = false;

   public GuiLockIconButton(int var1, int var2, int var3) {
      super(var1, var2, var3, 20, 20, "");
   }

   public boolean func_175230_c() {
      return this.field_175231_o;
   }

   public void func_175229_b(boolean var1) {
      this.field_175231_o = var1;
   }

   public void drawButton(Minecraft var1, int var2, int var3) {
      if(this.visible) {
         var1.getTextureManager().bindTexture(GuiButton.buttonTextures);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         if((double)var2 >= this.xPosition && (double)var3 >= this.yPosition && (double)var2 < this.xPosition + (double)this.width && (double)var3 < this.yPosition + (double)this.height) {
            boolean var6 = true;
         } else {
            boolean var10000 = false;
         }

         GuiLockIconButton$Icon var5;
         if(this.field_175231_o) {
            if(!this.enabled) {
               var5 = GuiLockIconButton$Icon.LOCKED_DISABLED;
            } else {
               var5 = GuiLockIconButton$Icon.LOCKED_HOVER;
            }
         } else if(!this.enabled) {
            var5 = GuiLockIconButton$Icon.UNLOCKED_DISABLED;
         } else {
            var5 = GuiLockIconButton$Icon.UNLOCKED_HOVER;
         }

         this.drawTexturedModalRect((int)this.xPosition, (int)this.yPosition, var5.func_178910_a(), var5.func_178912_b(), this.width, this.height);
      }

   }
}
