package net.minecraft.client.gui.inventory;

import net.aHz;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiCrafting extends aHz {
   private static final ResourceLocation craftingTableGuiTextures = new ResourceLocation("textures/gui/container/crafting_table.png");

   public GuiCrafting(InventoryPlayer var1, World var2) {
      this(var1, var2, BlockPos.ORIGIN);
   }

   public GuiCrafting(InventoryPlayer var1, World var2, BlockPos var3) {
      super(new ContainerWorkbench(var1, var2, var3));
   }

   protected void drawGuiContainerForegroundLayer(int var1, int var2) {
      this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 28.0F, 6.0F, 4210752);
      this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8.0F, (float)(this.ab - 96 + 2), 4210752);
   }

   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(craftingTableGuiTextures);
      int var4 = (this.width - this.y) / 2;
      int var5 = (this.height - this.ab) / 2;
      this.drawTexturedModalRect(var4, var5, 0, 0, this.y, this.ab);
   }
}
