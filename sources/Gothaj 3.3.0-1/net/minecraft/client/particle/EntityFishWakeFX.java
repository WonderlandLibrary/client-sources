package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityFishWakeFX extends EntityFX {
   protected EntityFishWakeFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i45073_8_, double p_i45073_10_, double p_i45073_12_) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0, 0.0, 0.0);
      this.motionX *= 0.3F;
      this.motionY = Math.random() * 0.2F + 0.1F;
      this.motionZ *= 0.3F;
      this.particleRed = 1.0F;
      this.particleGreen = 1.0F;
      this.particleBlue = 1.0F;
      this.setParticleTextureIndex(19);
      this.setSize(0.01F, 0.01F);
      this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
      this.particleGravity = 0.0F;
      this.motionX = p_i45073_8_;
      this.motionY = p_i45073_10_;
      this.motionZ = p_i45073_12_;
   }

   @Override
   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      this.motionY = this.motionY - (double)this.particleGravity;
      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      this.motionX *= 0.98F;
      this.motionY *= 0.98F;
      this.motionZ *= 0.98F;
      int i = 60 - this.particleMaxAge;
      float f = (float)i * 0.001F;
      this.setSize(f, f);
      this.setParticleTextureIndex(19 + i % 4);
      if (this.particleMaxAge-- <= 0) {
         this.setDead();
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
         return new EntityFishWakeFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      }
   }
}
