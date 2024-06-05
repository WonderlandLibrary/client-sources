package net.minecraft.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class EntityFishWakeFX extends EntityFX {
   protected EntityFishWakeFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(var1, var2, var4, var6, 0.0D, 0.0D, 0.0D);
      this.motionX *= 0.30000001192092896D;
      this.motionY = Math.random() * 0.20000000298023224D + 0.10000000149011612D;
      this.motionZ *= 0.30000001192092896D;
      this.particleRed = 1.0F;
      this.particleGreen = 1.0F;
      this.particleBlue = 1.0F;
      this.setParticleTextureIndex(19);
      this.setSize(0.01F, 0.01F);
      this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
      this.particleGravity = 0.0F;
      this.motionX = var8;
      this.motionY = var10;
      this.motionZ = var12;
   }

   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      this.motionY -= (double)this.particleGravity;
      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      this.motionX *= 0.9800000190734863D;
      this.motionY *= 0.9800000190734863D;
      this.motionZ *= 0.9800000190734863D;
      int var1 = 60 - this.particleMaxAge;
      float var2 = (float)var1 * 0.001F;
      this.setSize(var2, var2);
      this.setParticleTextureIndex(19 + var1 % 4);
      if(this.particleMaxAge-- <= 0) {
         this.setDead();
      }

   }
}
