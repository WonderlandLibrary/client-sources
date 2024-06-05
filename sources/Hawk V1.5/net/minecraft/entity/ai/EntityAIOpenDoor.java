package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityAIOpenDoor extends EntityAIDoorInteract {
   private static final String __OBFID = "CL_00001603";
   int closeDoorTemporisation;
   boolean closeDoor;

   public void updateTask() {
      --this.closeDoorTemporisation;
      super.updateTask();
   }

   public EntityAIOpenDoor(EntityLiving var1, boolean var2) {
      super(var1);
      this.theEntity = var1;
      this.closeDoor = var2;
   }

   public void resetTask() {
      if (this.closeDoor) {
         this.doorBlock.func_176512_a(this.theEntity.worldObj, this.field_179507_b, false);
      }

   }

   public void startExecuting() {
      this.closeDoorTemporisation = 20;
      this.doorBlock.func_176512_a(this.theEntity.worldObj, this.field_179507_b, true);
   }

   public boolean continueExecuting() {
      return this.closeDoor && this.closeDoorTemporisation > 0 && super.continueExecuting();
   }
}
