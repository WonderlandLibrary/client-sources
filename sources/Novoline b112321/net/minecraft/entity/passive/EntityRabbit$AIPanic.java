package net.minecraft.entity.passive;

import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.passive.EntityRabbit;

class EntityRabbit$AIPanic extends EntityAIPanic {
   private EntityRabbit theEntity;

   public EntityRabbit$AIPanic(EntityRabbit var1, double var2) {
      super(var1, var2);
      this.theEntity = var1;
   }

   public void updateTask() {
      super.updateTask();
      this.theEntity.setMovementSpeed(this.speed);
   }
}
