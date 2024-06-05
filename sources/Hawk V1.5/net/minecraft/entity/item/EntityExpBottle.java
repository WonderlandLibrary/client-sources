package net.minecraft.entity.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityExpBottle extends EntityThrowable {
   private static final String __OBFID = "CL_00001726";

   protected float func_70183_g() {
      return -20.0F;
   }

   protected void onImpact(MovingObjectPosition var1) {
      if (!this.worldObj.isRemote) {
         this.worldObj.playAuxSFX(2002, new BlockPos(this), 0);
         int var2 = 3 + this.worldObj.rand.nextInt(5) + this.worldObj.rand.nextInt(5);

         while(var2 > 0) {
            int var3 = EntityXPOrb.getXPSplit(var2);
            var2 -= var3;
            this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var3));
         }

         this.setDead();
      }

   }

   public EntityExpBottle(World var1, EntityLivingBase var2) {
      super(var1, var2);
   }

   protected float func_70182_d() {
      return 0.7F;
   }

   public EntityExpBottle(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6);
   }

   protected float getGravityVelocity() {
      return 0.07F;
   }

   public EntityExpBottle(World var1) {
      super(var1);
   }
}
