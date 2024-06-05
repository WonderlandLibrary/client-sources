package net.minecraft.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityHugeExplodeFX extends EntityFX {
   private int timeSinceStart;
   private int maximumTime = 8;

   protected EntityHugeExplodeFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(var1, var2, var4, var6, 0.0D, 0.0D, 0.0D);
   }

   public void renderParticle(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
   }

   public void onUpdate() {
      for(int var1 = 0; var1 < 6; ++var1) {
         double var2 = this.posX + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
         double var4 = this.posY + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
         double var6 = this.posZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
         this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, var2, var4, var6, (double)((float)this.timeSinceStart / (float)this.maximumTime), 0.0D, 0.0D, new int[0]);
      }

      ++this.timeSinceStart;
      if(this.timeSinceStart == this.maximumTime) {
         this.setDead();
      }

   }

   public int getFXLayer() {
      return 1;
   }
}
