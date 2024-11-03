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

public class SaltParticle extends EntityFX {
   public static ModelRenderer salt;

   public SaltParticle(World world, double d0, double d1, double d2, double d3, double d4, double d5) {
      super(world, d0, d1, d2);
      this.particleGravity = 0.5F;
      this.particleMaxAge = 20 + this.rand.nextInt(10);
      this.motionX = d3;
      this.motionZ = d4;
      this.motionY = d5;
      if (salt == null) {
         ModelBase modelbase = new ModelBase() {
         };
         modelbase.textureWidth = 64;
         modelbase.textureHeight = 64;
         salt = new ModelRenderer(modelbase, 0, 0);
         salt.addBox(-0.5F, -0.5F, 0.5F, 1, 1, 1);
      }
   }

   @Override
   public void renderParticle(WorldRenderer var1, Entity entity, float f, float var4, float var5, float var6, float var7, float var8) {
      float f1 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) f - interpPosX);
      float f2 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) f - interpPosY);
      float f3 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) f - interpPosZ);
      int i = this.particleMaxAge - this.particleAge;
      float f4 = 0.5F * (i < 5 ? (float) i / 5.0F : 1.0F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
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
      salt.render(0.0625F);
      RenderHelper.disableStandardItemLighting();
      GlStateManager.popMatrix();
   }

   @Override
   public int getFXLayer() {
      return 3;
   }
}
