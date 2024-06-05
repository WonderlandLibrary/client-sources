package net.minecraft.client.renderer.entity.layers;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;

public class LayerHeldBlock implements LayerRenderer {
   private final RenderEnderman field_177174_a;
   private static final String __OBFID = "CL_00002424";

   public boolean shouldCombineTextures() {
      return false;
   }

   public void doRenderLayer(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      this.func_177173_a((EntityEnderman)var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public LayerHeldBlock(RenderEnderman var1) {
      this.field_177174_a = var1;
   }

   public void func_177173_a(EntityEnderman var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      IBlockState var9 = var1.func_175489_ck();
      if (var9.getBlock().getMaterial() != Material.air) {
         BlockRendererDispatcher var10 = Minecraft.getMinecraft().getBlockRendererDispatcher();
         GlStateManager.enableRescaleNormal();
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, 0.6875F, -0.75F);
         GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(0.25F, 0.1875F, 0.25F);
         float var11 = 0.5F;
         GlStateManager.scale(-var11, -var11, var11);
         int var12 = var1.getBrightnessForRender(var4);
         int var13 = var12 % 65536;
         int var14 = var12 / 65536;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var13 / 1.0F, (float)var14 / 1.0F);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_177174_a.bindTexture(TextureMap.locationBlocksTexture);
         var10.func_175016_a(var9, 1.0F);
         GlStateManager.popMatrix();
         GlStateManager.disableRescaleNormal();
      }

   }
}
