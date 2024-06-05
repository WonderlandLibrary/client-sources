package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

class GuiBeacon$Button extends GuiButton {
   private final ResourceLocation field_146145_o;
   private final int field_146144_p;
   private final int field_146143_q;
   private boolean field_146142_r;

   protected GuiBeacon$Button(int var1, int var2, int var3, ResourceLocation var4, int var5, int var6) {
      super(var1, var2, var3, 22, 22, "");
      this.field_146145_o = var4;
      this.field_146144_p = var5;
      this.field_146143_q = var6;
   }

   public void drawButton(Minecraft var1, int var2, int var3) {
      if(this.visible) {
         var1.getTextureManager().bindTexture(GuiBeacon.access$000());
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.hovered = (double)var2 >= this.xPosition && (double)var3 >= this.yPosition && (double)var2 < this.xPosition + (double)this.width && (double)var3 < this.yPosition + (double)this.height;
         boolean var4 = true;
         int var5 = 0;
         if(!this.enabled) {
            var5 += this.width * 2;
         } else if(this.field_146142_r) {
            var5 += this.width;
         } else if(this.hovered) {
            var5 += this.width * 3;
         }

         this.drawTexturedModalRect((int)this.xPosition, (int)this.yPosition, var5, 219, this.width, this.height);
         if(!GuiBeacon.access$000().equals(this.field_146145_o)) {
            var1.getTextureManager().bindTexture(this.field_146145_o);
         }

         this.drawTexturedModalRect((int)this.xPosition + 2, (int)this.yPosition + 2, this.field_146144_p, this.field_146143_q, 18, 18);
      }

   }

   public boolean func_146141_c() {
      return this.field_146142_r;
   }

   public void func_146140_b(boolean var1) {
      this.field_146142_r = var1;
   }
}
