package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityAIFleeSun extends EntityAIBase {
   private EntityCreature theCreature;
   private double b;
   private double d;
   private double e;
   private double movementSpeed;
   private World theWorld;

   public EntityAIFleeSun(EntityCreature var1, double var2) {
      this.theCreature = var1;
      this.movementSpeed = var2;
      this.theWorld = var1.worldObj;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      if(!this.theWorld.isDaytime()) {
         return false;
      } else if(!this.theCreature.isBurning()) {
         return false;
      } else if(!this.theWorld.canSeeSky(new BlockPos(this.theCreature.posX, this.theCreature.getEntityBoundingBox().minY, this.theCreature.posZ))) {
         return false;
      } else {
         Vec3 var1 = this.findPossibleShelter();
         return false;
      }
   }

   public boolean continueExecuting() {
      return !this.theCreature.getNavigator().noPath();
   }

   public void startExecuting() {
      this.theCreature.getNavigator().tryMoveToXYZ(this.b, this.d, this.e, this.movementSpeed);
   }

   private Vec3 findPossibleShelter() {
      Random var1 = this.theCreature.getRNG();
      BlockPos var2 = new BlockPos(this.theCreature.posX, this.theCreature.getEntityBoundingBox().minY, this.theCreature.posZ);

      for(int var3 = 0; var3 < 10; ++var3) {
         BlockPos var4 = var2.a(var1.nextInt(20) - 10, var1.nextInt(6) - 3, var1.nextInt(20) - 10);
         if(!this.theWorld.canSeeSky(var4) && this.theCreature.getBlockPathWeight(var4) < 0.0F) {
            return new Vec3((double)var4.getX(), (double)var4.getY(), (double)var4.getZ());
         }
      }

      return null;
   }
}
