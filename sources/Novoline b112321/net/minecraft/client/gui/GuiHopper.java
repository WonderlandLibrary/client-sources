package net.minecraft.client.gui;

import net.aHz;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiHopper extends aHz {
   private static final ResourceLocation HOPPER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/hopper.png");
   private IInventory playerInventory;
   private IInventory hopperInventory;

   public GuiHopper(InventoryPlayer var1, IInventory var2) {
      super(new ContainerHopper(var1, var2, Minecraft.getInstance().player));
      this.playerInventory = var1;
      this.hopperInventory = var2;
      this.ab = 133;
   }

   protected void drawGuiContainerForegroundLayer(int var1, int var2) {
      this.fontRendererObj.drawString(this.hopperInventory.getDisplayName().getUnformattedText(), 8.0F, 6.0F, 4210752);
      this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8.0F, (float)(this.ab - 96 + 2), 4210752);
   }

   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(HOPPER_GUI_TEXTURE);
      int var4 = (this.width - this.y) / 2;
      int var5 = (this.height - this.ab) / 2;
      this.drawTexturedModalRect(var4, var5, 0, 0, this.y, this.ab);
   }
}
