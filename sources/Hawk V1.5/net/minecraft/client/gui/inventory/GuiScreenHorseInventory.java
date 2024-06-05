package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiScreenHorseInventory extends GuiContainer {
   private EntityHorse field_147034_x;
   private float field_147033_y;
   private static final ResourceLocation horseGuiTextures = new ResourceLocation("textures/gui/container/horse.png");
   private float field_147032_z;
   private IInventory field_147029_w;
   private IInventory field_147030_v;
   private static final String __OBFID = "CL_00000760";

   protected void drawGuiContainerForegroundLayer(int var1, int var2) {
      this.fontRendererObj.drawString(this.field_147029_w.getDisplayName().getUnformattedText(), 8.0D, 6.0D, 4210752);
      this.fontRendererObj.drawString(this.field_147030_v.getDisplayName().getUnformattedText(), 8.0D, (double)(this.ySize - 96 + 2), 4210752);
   }

   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(horseGuiTextures);
      int var4 = (this.width - this.xSize) / 2;
      int var5 = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
      if (this.field_147034_x.isChested()) {
         this.drawTexturedModalRect(var4 + 79, var5 + 17, 0, this.ySize, 90, 54);
      }

      if (this.field_147034_x.canWearArmor()) {
         this.drawTexturedModalRect(var4 + 7, var5 + 35, 0, this.ySize + 54, 18, 18);
      }

      GuiInventory.drawEntityOnScreen(var4 + 51, var5 + 60, 17, (float)(var4 + 51) - this.field_147033_y, (float)(var5 + 75 - 50) - this.field_147032_z, this.field_147034_x);
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.field_147033_y = (float)var1;
      this.field_147032_z = (float)var2;
      super.drawScreen(var1, var2, var3);
   }

   public GuiScreenHorseInventory(IInventory var1, IInventory var2, EntityHorse var3) {
      super(new ContainerHorseInventory(var1, var2, var3, Minecraft.getMinecraft().thePlayer));
      this.field_147030_v = var1;
      this.field_147029_w = var2;
      this.field_147034_x = var3;
      this.allowUserInput = false;
   }
}
