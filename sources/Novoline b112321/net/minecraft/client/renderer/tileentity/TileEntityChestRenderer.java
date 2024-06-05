package net.minecraft.client.renderer.tileentity;

import cc.novoline.Novoline;
import cc.novoline.modules.visual.ChestESP;
import java.util.Calendar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityChestRenderer extends TileEntitySpecialRenderer {
   private static final ResourceLocation g = new ResourceLocation("textures/entity/chest/trapped_double.png");
   private static final ResourceLocation c = new ResourceLocation("textures/entity/chest/christmas_double.png");
   private static final ResourceLocation h = new ResourceLocation("textures/entity/chest/normal_double.png");
   private static final ResourceLocation j = new ResourceLocation("textures/entity/chest/trapped.png");
   private static final ResourceLocation e = new ResourceLocation("textures/entity/chest/christmas.png");
   private static final ResourceLocation i = new ResourceLocation("textures/entity/chest/normal.png");
   private final ModelChest simpleChest = new ModelChest();
   private final ModelChest largeChest = new ModelLargeChest();
   private boolean k;

   public TileEntityChestRenderer() {
      Calendar var1 = Calendar.getInstance();
      if(var1.get(2) + 1 == 12 && var1.get(5) >= 24 && var1.get(5) <= 26) {
         this.k = true;
      }

   }

   public void renderTileEntityAt(TileEntityChest var1, double var2, double var4, double var6, float var8, int var9) {
      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.depthMask(true);
      int var10;
      if(!var1.hasWorldObj()) {
         var10 = 0;
      } else {
         Block var11 = var1.getBlockType();
         var10 = var1.getBlockMetadata();
         if(var11 instanceof BlockChest) {
            ((BlockChest)var11).checkForSurroundingChests(var1.getWorld(), var1.getPos(), var1.getWorld().getBlockState(var1.getPos()));
            var10 = var1.getBlockMetadata();
         }

         var1.checkForAdjacentChests();
      }

      if(var1.adjacentChestZNeg == null && var1.adjacentChestXNeg == null) {
         ModelChest var17;
         if(var1.adjacentChestXPos == null && var1.adjacentChestZPos == null) {
            var17 = this.simpleChest;
            this.bindTexture(DESTROY_STAGES[var9]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
         } else {
            var17 = this.largeChest;
            this.bindTexture(DESTROY_STAGES[var9]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(8.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
         }

         GlStateManager.pushMatrix();
         GlStateManager.enableRescaleNormal();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.translate((float)var2, (float)var4 + 1.0F, (float)var6 + 1.0F);
         GlStateManager.scale(1.0F, -1.0F, -1.0F);
         GlStateManager.translate(0.5F, 0.5F, 0.5F);
         short var12 = 0;
         if(var10 == 2) {
            var12 = 180;
         }

         if(var10 == 3) {
            var12 = 0;
         }

         if(var10 == 4) {
            var12 = 90;
         }

         if(var10 == 5) {
            var12 = -90;
         }

         if(var10 == 2 && var1.adjacentChestXPos != null) {
            GlStateManager.translate(1.0F, 0.0F, 0.0F);
         }

         if(var10 == 5 && var1.adjacentChestZPos != null) {
            GlStateManager.translate(0.0F, 0.0F, -1.0F);
         }

         GlStateManager.rotate((float)var12, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(-0.5F, -0.5F, -0.5F);
         float var13 = var1.prevLidAngle + (var1.lidAngle - var1.prevLidAngle) * var8;
         if(var1.adjacentChestZNeg != null) {
            float var14 = var1.adjacentChestZNeg.prevLidAngle + (var1.adjacentChestZNeg.lidAngle - var1.adjacentChestZNeg.prevLidAngle) * var8;
            if(var14 > var13) {
               var13 = var14;
            }
         }

         if(var1.adjacentChestXNeg != null) {
            float var20 = var1.adjacentChestXNeg.prevLidAngle + (var1.adjacentChestXNeg.lidAngle - var1.adjacentChestXNeg.prevLidAngle) * var8;
            if(var20 > var13) {
               var13 = var20;
            }
         }

         var13 = 1.0F - var13;
         var13 = 1.0F - var13 * var13 * var13;
         var17.chestLid.rotateAngleX = -(var13 * 3.1415927F / 2.0F);
         if(((ChestESP)Novoline.getInstance().getModuleManager().getModule(ChestESP.class)).isEnabled() && var1.hasWorldObj()) {
            ChestESP var21 = (ChestESP)Novoline.getInstance().getModuleManager().getModule(ChestESP.class);
            if(!var21.getMode().equalsIgnoreCase("Outline") && !var21.getMode().equalsIgnoreCase("Combined")) {
               if(var21.getMode().equalsIgnoreCase("Chams")) {
                  int var22 = var21.getHidden().getAwtColor().getRGB();
                  GL11.glPushMatrix();
                  GL11.glDisable(3553);
                  GL11.glEnable(3042);
                  GL11.glDisable(2896);
                  GL11.glBlendFunc(770, 771);
                  GlStateManager.disableDepth();
                  float[] var24 = var21.getColorForTileEntity();
                  OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
                  GL11.glColor4f((float)(var22 >> 16 & 255) / 255.0F, (float)(var22 >> 8 & 255) / 255.0F, (float)(var22 & 255) / 255.0F, 255.0F);
                  var17.renderAll();
                  GlStateManager.enableDepth();
                  var22 = var21.getVisible().getAwtColor().getRGB();
                  GL11.glColor4f((float)(var22 >> 16 & 255) / 255.0F, (float)(var22 >> 8 & 255) / 255.0F, (float)(var22 & 255) / 255.0F, 255.0F);
                  var17.renderAll();
                  GL11.glEnable(2896);
                  GL11.glDisable(3042);
                  GL11.glEnable(3553);
                  GL11.glPopMatrix();
               } else {
                  var17.renderAll();
               }
            } else {
               float[] var15 = var21.getColorForTileEntity();
               int var16 = var21.toRGBAHex(var15[0] / 255.0F, var15[1] / 255.0F, var15[2] / 255.0F, 1.0F);
               var17.renderAll();
               var21.pre3D();
               var17.renderAll();
               var21.setupStencil();
               var17.renderAll();
               var17.renderAll();
               var21.setupStencil2();
               var21.renderOutline(var16);
               var17.renderAll();
               var21.post3D();
            }
         } else {
            var17.renderAll();
         }

         GlStateManager.disableRescaleNormal();
         GlStateManager.popMatrix();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.matrixMode(5890);
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5888);
      }

   }
}
