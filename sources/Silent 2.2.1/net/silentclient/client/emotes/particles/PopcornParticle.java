package net.silentclient.client.emotes.particles;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class PopcornParticle extends EntityFX {
   public static ModelRenderer kernel1;
   public static ModelRenderer kernel2;
   public static ModelRenderer kernel3;
   protected int color;

   public PopcornParticle(World world, double d0, double d1, double d2, double d3) {
      super(world, d0, d1, d2);
      this.particleGravity = 0.5F;
      this.particleMaxAge = 20 + this.rand.nextInt(10);
      this.motionX = this.rand.nextFloat() * 0.05F;
      this.motionZ = this.rand.nextFloat() * 0.05F;
      this.motionY = d3 == 0.0 ? d3 : this.rand.nextDouble() * 0.1F + d3;
      if (kernel1 == null) {
         ModelBase modelbase = new ModelBase() {
         };
         modelbase.textureWidth = 64;
         modelbase.textureHeight = 64;
         kernel1 = new ModelRenderer(modelbase, 0, 2);
         kernel1.addBox(-0.5F, -0.5F, 0.5F, 1, 1, 1);
         kernel2 = new ModelRenderer(modelbase, 0, 4);
         kernel2.addBox(-0.5F, -0.5F, 0.5F, 1, 1, 1);
         kernel3 = new ModelRenderer(modelbase, 0, 6);
         kernel3.addBox(-0.5F, -0.5F, 0.5F, 1, 1, 1);
      }

      this.color = this.rand.nextInt(2);
   }

   @Override
   public void renderParticle(WorldRenderer var1, Entity entity, float f, float var4, float var5, float var6, float var7, float var8) {
      float f1 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) f - interpPosX);
      float f2 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) f - interpPosY);
      float f3 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) f - interpPosZ);
      int i = this.particleMaxAge - this.particleAge;
      float f4 = 0.75F * (i < 5 ? (float) i / 5.0F : 1.0F);
      ModelRenderer modelrenderer = kernel1;
      if (this.color == 1) {
         modelrenderer = kernel2;
      } else if (this.color == 2) {
         modelrenderer = kernel3;
      }

      int j = entity.getBrightnessForRender(f);
      if (entity.isBurning()) {
         j = 15728880;
      }

      int k = j % 65536;
      int l = j / 65536;
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) k, (float) l);
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.pushMatrix();
      GlStateManager.translate(f1, f2, f3);
      GlStateManager.scale(f4, f4, f4);
      RenderHelper.enableStandardItemLighting();
      modelrenderer.render(0.0625F);
      RenderHelper.disableStandardItemLighting();
      GlStateManager.popMatrix();
   }

   @Override
   public int getFXLayer() {
      return 3;
   }
}
