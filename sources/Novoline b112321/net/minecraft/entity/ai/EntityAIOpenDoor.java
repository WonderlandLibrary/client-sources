package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIDoorInteract;

public class EntityAIOpenDoor extends EntityAIDoorInteract {
   boolean closeDoor;
   int closeDoorTemporisation;

   public EntityAIOpenDoor(EntityLiving var1, boolean var2) {
      super(var1);
      this.theEntity = var1;
      this.closeDoor = var2;
   }

   public boolean continueExecuting() {
      return this.closeDoor && this.closeDoorTemporisation > 0 && super.continueExecuting();
   }

   public void startExecuting() {
      this.closeDoorTemporisation = 20;
      this.doorBlock.toggleDoor(this.theEntity.worldObj, this.doorPosition, true);
   }

   public void resetTask() {
      if(this.closeDoor) {
         this.doorBlock.toggleDoor(this.theEntity.worldObj, this.doorPosition, false);
      }

   }

   public void updateTask() {
      --this.closeDoorTemporisation;
      super.updateTask();
   }
}
