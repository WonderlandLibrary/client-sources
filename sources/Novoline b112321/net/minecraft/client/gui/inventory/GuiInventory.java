package net.minecraft.client.gui.inventory;

import java.io.IOException;
import net.aIB;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class GuiInventory extends InventoryEffectRenderer {
   private float oldMouseX;
   private float oldMouseY;

   public GuiInventory(EntityPlayer var1) {
      super(var1.inventoryContainer);
   }

   public void updateScreen() {
      if(this.mc.at.h()) {
         this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.player));
      }

      this.updateActivePotionEffects();
   }

   public void initGui() {
      this.buttonList.clear();
      if(this.mc.at.h()) {
         this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.player));
      } else {
         super.initGui();
      }

   }

   protected void drawGuiContainerForegroundLayer(int var1, int var2) {
      this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 86.0F, 16.0F, 4210752);
   }

   public void drawScreen(int var1, int var2, float var3) {
      super.drawScreen(var1, var2, var3);
      this.oldMouseX = (float)var1;
      this.oldMouseY = (float)var2;
   }

   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(v);
      int var4 = this.R;
      int var5 = this.W;
      this.drawTexturedModalRect(var4, var5, 0, 0, this.y, this.ab);
      drawEntityOnScreen(var4 + 51, var5 + 75, 30, (float)(var4 + 51) - this.oldMouseX, (float)(var5 + 75 - 50) - this.oldMouseY, this.mc.player);
   }

   public static void drawEntityOnScreen(int var0, int var1, int var2, float var3, float var4, EntityLivingBase var5) {
      GlStateManager.enableColorMaterial();
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)var0, (float)var1, 50.0F);
      GlStateManager.scale((float)(-var2), (float)var2, (float)var2);
      GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      float var6 = var5.renderYawOffset;
      float var7 = var5.rotationYaw;
      float var8 = var5.rotationPitch;
      float var9 = var5.prevRotationYawHead;
      float var10 = var5.rotationYawHead;
      GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.enableStandardItemLighting();
      GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
      float var11 = (float)Math.atan((double)(var3 / 40.0F));
      float var12 = -((float)Math.atan((double)(var4 / 40.0F)));
      GlStateManager.rotate(var12 * 20.0F, 1.0F, 0.0F, 0.0F);
      var5.renderYawOffset = var11 * 20.0F;
      var5.rotationYaw = var11 * 40.0F;
      var5.rotationPitch = var12 * 20.0F;
      var5.rotationYawHead = var5.rotationYaw;
      var5.prevRotationYawHead = var5.rotationYaw;
      GlStateManager.translate(0.0F, 0.0F, 0.0F);
      RenderManager var13 = Minecraft.getInstance().getRenderManager();
      var13.setPlayerViewY(180.0F);
      var13.setRenderShadow(false);
      aIB.a(var13, var5, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
      var13.setRenderShadow(true);
      var5.renderYawOffset = var6;
      var5.rotationYaw = var7;
      var5.rotationPitch = var8;
      var5.prevRotationYawHead = var9;
      var5.rotationYawHead = var10;
      GlStateManager.popMatrix();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableRescaleNormal();
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.disableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.id == 0) {
         this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.player.getStatFileWriter()));
      } else if(var1.id == 1) {
         this.mc.displayGuiScreen(new GuiStats(this, this.mc.player.getStatFileWriter()));
      }

   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
