package net.minecraft.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

public class RenderFireball extends Render {
   private float scale;
   private static final String __OBFID = "CL_00000995";

   public RenderFireball(RenderManager var1, float var2) {
      super(var1);
      this.scale = var2;
   }

   protected ResourceLocation func_180556_a(EntityFireball var1) {
      return TextureMap.locationBlocksTexture;
   }

   public void doRender(EntityFireball var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.pushMatrix();
      this.bindEntityTexture(var1);
      GlStateManager.translate((float)var2, (float)var4, (float)var6);
      GlStateManager.enableRescaleNormal();
      float var10 = this.scale;
      GlStateManager.scale(var10 / 1.0F, var10 / 1.0F, var10 / 1.0F);
      TextureAtlasSprite var11 = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(Items.fire_charge);
      Tessellator var12 = Tessellator.getInstance();
      WorldRenderer var13 = var12.getWorldRenderer();
      float var14 = var11.getMinU();
      float var15 = var11.getMaxU();
      float var16 = var11.getMinV();
      float var17 = var11.getMaxV();
      float var18 = 1.0F;
      float var19 = 0.5F;
      float var20 = 0.25F;
      GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
      var13.startDrawingQuads();
      var13.func_178980_d(0.0F, 1.0F, 0.0F);
      var13.addVertexWithUV((double)(0.0F - var19), (double)(0.0F - var20), 0.0D, (double)var14, (double)var17);
      var13.addVertexWithUV((double)(var18 - var19), (double)(0.0F - var20), 0.0D, (double)var15, (double)var17);
      var13.addVertexWithUV((double)(var18 - var19), (double)(1.0F - var20), 0.0D, (double)var15, (double)var16);
      var13.addVertexWithUV((double)(0.0F - var19), (double)(1.0F - var20), 0.0D, (double)var14, (double)var16);
      var12.draw();
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      super.doRender(var1, var2, var4, var6, var8, var9);
   }

   public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      this.doRender((EntityFireball)var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.func_180556_a((EntityFireball)var1);
   }
}
