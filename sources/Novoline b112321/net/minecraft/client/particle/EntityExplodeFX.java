package net.minecraft.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class EntityExplodeFX extends EntityFX {
   protected EntityExplodeFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(var1, var2, var4, var6, var8, var10, var12);
      this.motionX = var8 + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
      this.motionY = var10 + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
      this.motionZ = var12 + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
      this.particleRed = this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.3F + 0.7F;
      this.particleScale = this.rand.nextFloat() * this.rand.nextFloat() * 6.0F + 1.0F;
      this.particleMaxAge = (int)(16.0D / ((double)this.rand.nextFloat() * 0.8D + 0.2D)) + 2;
   }

   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if(this.particleAge++ >= this.particleMaxAge) {
         this.setDead();
      }

      this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
      this.motionY += 0.004D;
      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      this.motionX *= 0.8999999761581421D;
      this.motionY *= 0.8999999761581421D;
      this.motionZ *= 0.8999999761581421D;
      if(this.onGround) {
         this.motionX *= 0.699999988079071D;
         this.motionZ *= 0.699999988079071D;
      }

   }
}
