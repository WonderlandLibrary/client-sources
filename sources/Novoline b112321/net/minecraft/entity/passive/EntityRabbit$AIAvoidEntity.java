package net.minecraft.entity.passive;

import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.passive.EntityRabbit;

class EntityRabbit$AIAvoidEntity extends EntityAIAvoidEntity {
   private EntityRabbit entityInstance;

   public EntityRabbit$AIAvoidEntity(EntityRabbit var1, Class var2, float var3, double var4, double var6) {
      super(var1, var2, var3, var4, var6);
      this.entityInstance = var1;
   }

   public void updateTask() {
      super.updateTask();
   }
}
