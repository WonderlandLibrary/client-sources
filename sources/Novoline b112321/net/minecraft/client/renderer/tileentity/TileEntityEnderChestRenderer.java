package net.minecraft.client.renderer.tileentity;

import cc.novoline.Novoline;
import cc.novoline.modules.visual.ChestESP;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityEnderChestRenderer extends TileEntitySpecialRenderer {
   private static final ResourceLocation d = new ResourceLocation("textures/entity/chest/ender.png");
   private final ModelChest modelChest = new ModelChest();

   public void renderTileEntityAt(TileEntityEnderChest var1, double var2, double var4, double var6, float var8, int var9) {
      int var10 = 0;
      if(var1.hasWorldObj()) {
         var10 = var1.getBlockMetadata();
      }

      this.bindTexture(DESTROY_STAGES[var9]);
      GlStateManager.matrixMode(5890);
      GlStateManager.pushMatrix();
      GlStateManager.scale(4.0F, 4.0F, 1.0F);
      GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
      GlStateManager.matrixMode(5888);
      GlStateManager.pushMatrix();
      GlStateManager.enableRescaleNormal();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.translate((float)var2, (float)var4 + 1.0F, (float)var6 + 1.0F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      GlStateManager.translate(0.5F, 0.5F, 0.5F);
      short var11 = 0;
      if(var10 == 2) {
         var11 = 180;
      }

      if(var10 == 3) {
         var11 = 0;
      }

      if(var10 == 4) {
         var11 = 90;
      }

      if(var10 == 5) {
         var11 = -90;
      }

      GlStateManager.rotate((float)var11, 0.0F, 1.0F, 0.0F);
      GlStateManager.translate(-0.5F, -0.5F, -0.5F);
      float var12 = var1.prevLidAngle + (var1.lidAngle - var1.prevLidAngle) * var8;
      var12 = 1.0F - var12;
      var12 = 1.0F - var12 * var12 * var12;
      this.modelChest.chestLid.rotateAngleX = -(var12 * 3.1415927F / 2.0F);
      if(((ChestESP)Novoline.getInstance().getModuleManager().getModule(ChestESP.class)).isEnabled() && var1.hasWorldObj()) {
         ChestESP var13 = (ChestESP)Novoline.getInstance().getModuleManager().getModule(ChestESP.class);
         if(var13.getMode().equalsIgnoreCase("Outline")) {
            float[] var14 = var13.getColorForTileEntity();
            int var15 = var13.toRGBAHex(var14[0] / 255.0F, var14[1] / 255.0F, var14[2] / 255.0F, 1.0F);
            this.modelChest.renderAll();
            var13.pre3D();
            this.modelChest.renderAll();
            var13.setupStencilFirst();
            this.modelChest.renderAll();
            var13.setupStencilSecond();
            var13.renderOutline(var15);
            this.modelChest.renderAll();
            var13.post3D();
         } else if(var13.getMode().equalsIgnoreCase("Chams")) {
            int var18 = var13.getHidden().getAwtColor().getRGB();
            GL11.glPushMatrix();
            GL11.glDisable(3553);
            GL11.glEnable(3042);
            GL11.glDisable(2896);
            GL11.glBlendFunc(770, 771);
            GlStateManager.disableDepth();
            float[] var20 = var13.getColorForTileEntity();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GL11.glColor4f((float)(var18 >> 16 & 255) / 255.0F, (float)(var18 >> 8 & 255) / 255.0F, (float)(var18 & 255) / 255.0F, 255.0F);
            this.modelChest.renderAll();
            GlStateManager.enableDepth();
            var18 = var13.getVisible().getAwtColor().getRGB();
            GL11.glColor4f((float)(var18 >> 16 & 255) / 255.0F, (float)(var18 >> 8 & 255) / 255.0F, (float)(var18 & 255) / 255.0F, 255.0F);
            this.modelChest.renderAll();
            GL11.glEnable(2896);
            GL11.glDisable(3042);
            GL11.glEnable(3553);
            GL11.glPopMatrix();
         } else {
            this.modelChest.renderAll();
         }
      } else {
         this.modelChest.renderAll();
      }

      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.matrixMode(5890);
      GlStateManager.popMatrix();
      GlStateManager.matrixMode(5888);
   }
}
