package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAIFollowOwner extends EntityAIBase {
   World theWorld;
   private static final String __OBFID = "CL_00001585";
   float maxDist;
   float minDist;
   private PathNavigate petPathfinder;
   private double field_75336_f;
   private boolean field_75344_i;
   private int field_75343_h;
   private EntityLivingBase theOwner;
   private EntityTameable thePet;

   public void startExecuting() {
      this.field_75343_h = 0;
      this.field_75344_i = ((PathNavigateGround)this.thePet.getNavigator()).func_179689_e();
      ((PathNavigateGround)this.thePet.getNavigator()).func_179690_a(false);
   }

   public boolean continueExecuting() {
      return !this.petPathfinder.noPath() && this.thePet.getDistanceSqToEntity(this.theOwner) > (double)(this.maxDist * this.maxDist) && !this.thePet.isSitting();
   }

   public void updateTask() {
      this.thePet.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0F, (float)this.thePet.getVerticalFaceSpeed());
      if (!this.thePet.isSitting() && --this.field_75343_h <= 0) {
         this.field_75343_h = 10;
         if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.field_75336_f) && !this.thePet.getLeashed() && this.thePet.getDistanceSqToEntity(this.theOwner) >= 144.0D) {
            int var1 = MathHelper.floor_double(this.theOwner.posX) - 2;
            int var2 = MathHelper.floor_double(this.theOwner.posZ) - 2;
            int var3 = MathHelper.floor_double(this.theOwner.getEntityBoundingBox().minY);

            for(int var4 = 0; var4 <= 4; ++var4) {
               for(int var5 = 0; var5 <= 4; ++var5) {
                  if ((var4 < 1 || var5 < 1 || var4 > 3 || var5 > 3) && World.doesBlockHaveSolidTopSurface(this.theWorld, new BlockPos(var1 + var4, var3 - 1, var2 + var5)) && !this.theWorld.getBlockState(new BlockPos(var1 + var4, var3, var2 + var5)).getBlock().isFullCube() && !this.theWorld.getBlockState(new BlockPos(var1 + var4, var3 + 1, var2 + var5)).getBlock().isFullCube()) {
                     this.thePet.setLocationAndAngles((double)((float)(var1 + var4) + 0.5F), (double)var3, (double)((float)(var2 + var5) + 0.5F), this.thePet.rotationYaw, this.thePet.rotationPitch);
                     this.petPathfinder.clearPathEntity();
                     return;
                  }
               }
            }
         }
      }

   }

   public void resetTask() {
      this.theOwner = null;
      this.petPathfinder.clearPathEntity();
      ((PathNavigateGround)this.thePet.getNavigator()).func_179690_a(true);
   }

   public EntityAIFollowOwner(EntityTameable var1, double var2, float var4, float var5) {
      this.thePet = var1;
      this.theWorld = var1.worldObj;
      this.field_75336_f = var2;
      this.petPathfinder = var1.getNavigator();
      this.minDist = var4;
      this.maxDist = var5;
      this.setMutexBits(3);
      if (!(var1.getNavigator() instanceof PathNavigateGround)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
      }
   }

   public boolean shouldExecute() {
      EntityLivingBase var1 = this.thePet.func_180492_cm();
      if (var1 == null) {
         return false;
      } else if (this.thePet.isSitting()) {
         return false;
      } else if (this.thePet.getDistanceSqToEntity(var1) < (double)(this.minDist * this.minDist)) {
         return false;
      } else {
         this.theOwner = var1;
         return true;
      }
   }
}
