package net.minecraft.client.gui.inventory;

import net.aHz;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiDispenser extends aHz {
   private static final ResourceLocation dispenserGuiTextures = new ResourceLocation("textures/gui/container/dispenser.png");
   private final InventoryPlayer playerInventory;
   public IInventory dispenserInventory;

   public GuiDispenser(InventoryPlayer var1, IInventory var2) {
      super(new ContainerDispenser(var1, var2));
      this.playerInventory = var1;
      this.dispenserInventory = var2;
   }

   protected void drawGuiContainerForegroundLayer(int var1, int var2) {
      String var3 = this.dispenserInventory.getDisplayName().getUnformattedText();
      this.fontRendererObj.drawString(var3, (float)(this.y / 2 - this.fontRendererObj.d(var3) / 2), 6.0F, 4210752);
      this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8.0F, (float)(this.ab - 96 + 2), 4210752);
   }

   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(dispenserGuiTextures);
      int var4 = (this.width - this.y) / 2;
      int var5 = (this.height - this.ab) / 2;
      this.drawTexturedModalRect(var4, var5, 0, 0, this.y, this.ab);
   }
}
