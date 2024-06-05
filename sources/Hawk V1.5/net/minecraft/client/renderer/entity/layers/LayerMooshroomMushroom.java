package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderMooshroom;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.init.Blocks;

public class LayerMooshroomMushroom implements LayerRenderer {
   private final RenderMooshroom field_177205_a;
   private static final String __OBFID = "CL_00002415";

   public boolean shouldCombineTextures() {
      return true;
   }

   public void func_177204_a(EntityMooshroom var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (!var1.isChild() && !var1.isInvisible()) {
         BlockRendererDispatcher var9 = Minecraft.getMinecraft().getBlockRendererDispatcher();
         this.field_177205_a.bindTexture(TextureMap.locationBlocksTexture);
         GlStateManager.enableCull();
         GlStateManager.pushMatrix();
         GlStateManager.scale(1.0F, -1.0F, 1.0F);
         GlStateManager.translate(0.2F, 0.35F, 0.5F);
         GlStateManager.rotate(42.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.pushMatrix();
         GlStateManager.translate(-0.5F, -0.5F, 0.5F);
         var9.func_175016_a(Blocks.red_mushroom.getDefaultState(), 1.0F);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.1F, 0.0F, -0.6F);
         GlStateManager.rotate(42.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(-0.5F, -0.5F, 0.5F);
         var9.func_175016_a(Blocks.red_mushroom.getDefaultState(), 1.0F);
         GlStateManager.popMatrix();
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         ((ModelQuadruped)this.field_177205_a.getMainModel()).head.postRender(0.0625F);
         GlStateManager.scale(1.0F, -1.0F, 1.0F);
         GlStateManager.translate(0.0F, 0.7F, -0.2F);
         GlStateManager.rotate(12.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(-0.5F, -0.5F, 0.5F);
         var9.func_175016_a(Blocks.red_mushroom.getDefaultState(), 1.0F);
         GlStateManager.popMatrix();
         GlStateManager.disableCull();
      }

   }

   public void doRenderLayer(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      this.func_177204_a((EntityMooshroom)var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public LayerMooshroomMushroom(RenderMooshroom var1) {
      this.field_177205_a = var1;
   }
}
