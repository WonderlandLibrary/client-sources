package net.minecraft.client.gui.inventory;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiDispenser extends GuiContainer {
   private static final String __OBFID = "CL_00000765";
   private final InventoryPlayer field_175376_w;
   public IInventory field_175377_u;
   private static final ResourceLocation dispenserGuiTextures = new ResourceLocation("textures/gui/container/dispenser.png");

   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(dispenserGuiTextures);
      int var4 = (this.width - this.xSize) / 2;
      int var5 = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
   }

   protected void drawGuiContainerForegroundLayer(int var1, int var2) {
      String var3 = this.field_175377_u.getDisplayName().getUnformattedText();
      this.fontRendererObj.drawString(var3, (double)(this.xSize / 2 - this.fontRendererObj.getStringWidth(var3) / 2), 6.0D, 4210752);
      this.fontRendererObj.drawString(this.field_175376_w.getDisplayName().getUnformattedText(), 8.0D, (double)(this.ySize - 96 + 2), 4210752);
   }

   public GuiDispenser(InventoryPlayer var1, IInventory var2) {
      super(new ContainerDispenser(var1, var2));
      this.field_175376_w = var1;
      this.field_175377_u = var2;
   }
}
