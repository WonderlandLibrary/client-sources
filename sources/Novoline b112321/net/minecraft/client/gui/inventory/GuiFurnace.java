package net.minecraft.client.gui.inventory;

import net.aHz;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;

public class GuiFurnace extends aHz {
   private static final ResourceLocation furnaceGuiTextures = new ResourceLocation("textures/gui/container/furnace.png");
   private final InventoryPlayer playerInventory;
   private IInventory tileFurnace;

   public GuiFurnace(InventoryPlayer var1, IInventory var2) {
      super(new ContainerFurnace(var1, var2));
      this.playerInventory = var1;
      this.tileFurnace = var2;
   }

   protected void drawGuiContainerForegroundLayer(int var1, int var2) {
      String var3 = this.tileFurnace.getDisplayName().getUnformattedText();
      this.fontRendererObj.drawString(var3, (float)(this.y / 2 - this.fontRendererObj.d(var3) / 2), 6.0F, 4210752);
      this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8.0F, (float)(this.ab - 96 + 2), 4210752);
   }

   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
      int var4 = (this.width - this.y) / 2;
      int var5 = (this.height - this.ab) / 2;
      this.drawTexturedModalRect(var4, var5, 0, 0, this.y, this.ab);
      if(TileEntityFurnace.isBurning(this.tileFurnace)) {
         int var6 = this.getBurnLeftScaled(13);
         this.drawTexturedModalRect(var4 + 56, var5 + 36 + 12 - var6, 176, 12 - var6, 14, var6 + 1);
      }

      int var7 = this.getCookProgressScaled(24);
      this.drawTexturedModalRect(var4 + 79, var5 + 34, 176, 14, var7 + 1, 16);
   }

   private int getCookProgressScaled(int var1) {
      int var2 = this.tileFurnace.getField(2);
      int var3 = this.tileFurnace.getField(3);
      return var2 * var1 / var3;
   }

   private int getBurnLeftScaled(int var1) {
      int var2 = this.tileFurnace.getField(1);
      var2 = 200;
      return this.tileFurnace.getField(0) * var1 / var2;
   }
}
