package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityHeartFX extends EntityFX {
   final float particleScaleOverTime;

   protected EntityHeartFX(World worldIn, double p_i1211_2_, double p_i1211_4_, double p_i1211_6_, double p_i1211_8_, double p_i1211_10_, double p_i1211_12_) {
      this(worldIn, p_i1211_2_, p_i1211_4_, p_i1211_6_, p_i1211_8_, p_i1211_10_, p_i1211_12_, 2.0F);
   }

   protected EntityHeartFX(
      World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46354_8_, double p_i46354_10_, double p_i46354_12_, float scale
   ) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0, 0.0, 0.0);
      this.motionX *= 0.01F;
      this.motionY *= 0.01F;
      this.motionZ *= 0.01F;
      this.motionY += 0.1;
      this.particleScale *= 0.75F;
      this.particleScale *= scale;
      this.particleScaleOverTime = this.particleScale;
      this.particleMaxAge = 16;
      this.noClip = false;
      this.setParticleTextureIndex(80);
   }

   @Override
   public void renderParticle(
      WorldRenderer worldRendererIn,
      Entity entityIn,
      float partialTicks,
      float p_180434_4_,
      float p_180434_5_,
      float p_180434_6_,
      float p_180434_7_,
      float p_180434_8_
   ) {
      float f = ((float)this.particleAge + partialTicks) / (float)this.particleMaxAge * 32.0F;
      f = MathHelper.clamp_float(f, 0.0F, 1.0F);
      this.particleScale = this.particleScaleOverTime * f;
      super.renderParticle(worldRendererIn, entityIn, partialTicks, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
   }

   @Override
   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if (this.particleAge++ >= this.particleMaxAge) {
         this.setDead();
      }

      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      if (this.posY == this.prevPosY) {
         this.motionX *= 1.1;
         this.motionZ *= 1.1;
      }

      this.motionX *= 0.86F;
      this.motionY *= 0.86F;
      this.motionZ *= 0.86F;
      if (this.onGround) {
         this.motionX *= 0.7F;
         this.motionZ *= 0.7F;
      }
   }

   public static class AngryVillagerFactory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int particleID,
         World worldIn,
         double xCoordIn,
         double yCoordIn,
         double zCoordIn,
         double xSpeedIn,
         double ySpeedIn,
         double zSpeedIn,
         int... p_178902_15_
      ) {
         EntityFX entityfx = new EntityHeartFX(worldIn, xCoordIn, yCoordIn + 0.5, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
         entityfx.setParticleTextureIndex(81);
         entityfx.setRBGColorF(1.0F, 1.0F, 1.0F);
         return entityfx;
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int particleID,
         World worldIn,
         double xCoordIn,
         double yCoordIn,
         double zCoordIn,
         double xSpeedIn,
         double ySpeedIn,
         double zSpeedIn,
         int... p_178902_15_
      ) {
         return new EntityHeartFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      }
   }
}
