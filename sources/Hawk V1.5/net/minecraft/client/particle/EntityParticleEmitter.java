package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityParticleEmitter extends EntityFX {
   private int field_174850_ay;
   private Entity field_174851_a;
   private EnumParticleTypes field_174849_az;
   private int field_174852_ax;
   private static final String __OBFID = "CL_00002574";

   public EntityParticleEmitter(World var1, Entity var2, EnumParticleTypes var3) {
      super(var1, var2.posX, var2.getEntityBoundingBox().minY + (double)(var2.height / 2.0F), var2.posZ, var2.motionX, var2.motionY, var2.motionZ);
      this.field_174851_a = var2;
      this.field_174850_ay = 3;
      this.field_174849_az = var3;
      this.onUpdate();
   }

   public int getFXLayer() {
      return 3;
   }

   public void func_180434_a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
   }

   public void onUpdate() {
      for(int var1 = 0; var1 < 16; ++var1) {
         double var2 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);
         double var4 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);
         double var6 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);
         if (var2 * var2 + var4 * var4 + var6 * var6 <= 1.0D) {
            double var8 = this.field_174851_a.posX + var2 * (double)this.field_174851_a.width / 4.0D;
            double var10 = this.field_174851_a.getEntityBoundingBox().minY + (double)(this.field_174851_a.height / 2.0F) + var4 * (double)this.field_174851_a.height / 4.0D;
            double var12 = this.field_174851_a.posZ + var6 * (double)this.field_174851_a.width / 4.0D;
            this.worldObj.spawnParticle(this.field_174849_az, false, var8, var10, var12, var2, var4 + 0.2D, var6);
         }
      }

      ++this.field_174852_ax;
      if (this.field_174852_ax >= this.field_174850_ay) {
         this.setDead();
      }

   }
}
