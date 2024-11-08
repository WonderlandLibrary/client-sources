package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySnowShovelFX extends EntityFX {
   final float snowDigParticleScale;

   protected EntitySnowShovelFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
      this(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, 1.0F);
   }

   protected EntitySnowShovelFX(
      World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, float p_i1228_14_
   ) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      this.motionX *= 0.1F;
      this.motionY *= 0.1F;
      this.motionZ *= 0.1F;
      this.motionX += xSpeedIn;
      this.motionY += ySpeedIn;
      this.motionZ += zSpeedIn;
      this.particleRed = this.particleGreen = this.particleBlue = 1.0F - (float)(Math.random() * 0.3F);
      this.particleScale *= 0.75F;
      this.particleScale *= p_i1228_14_;
      this.snowDigParticleScale = this.particleScale;
      this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
      this.particleMaxAge = (int)((float)this.particleMaxAge * p_i1228_14_);
      this.noClip = false;
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
      this.particleScale = this.snowDigParticleScale * f;
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

      this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
      this.motionY -= 0.03;
      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      this.motionX *= 0.99F;
      this.motionY *= 0.99F;
      this.motionZ *= 0.99F;
      if (this.onGround) {
         this.motionX *= 0.7F;
         this.motionZ *= 0.7F;
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
         return new EntitySnowShovelFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      }
   }
}
