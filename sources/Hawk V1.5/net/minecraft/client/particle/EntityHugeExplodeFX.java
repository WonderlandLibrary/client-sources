package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityHugeExplodeFX extends EntityFX {
   private static final String __OBFID = "CL_00000911";
   private int timeSinceStart;
   private int maximumTime = 8;

   public int getFXLayer() {
      return 1;
   }

   public void onUpdate() {
      for(int var1 = 0; var1 < 6; ++var1) {
         double var2 = this.posX + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
         double var4 = this.posY + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
         double var6 = this.posZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
         this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, var2, var4, var6, (double)((float)this.timeSinceStart / (float)this.maximumTime), 0.0D, 0.0D);
      }

      ++this.timeSinceStart;
      if (this.timeSinceStart == this.maximumTime) {
         this.setDead();
      }

   }

   public void func_180434_a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
   }

   protected EntityHugeExplodeFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(var1, var2, var4, var6, 0.0D, 0.0D, 0.0D);
   }

   public static class Factory implements IParticleFactory {
      private static final String __OBFID = "CL_00002597";

      public EntityFX func_178902_a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new EntityHugeExplodeFX(var2, var3, var5, var7, var9, var11, var13);
      }
   }
}
