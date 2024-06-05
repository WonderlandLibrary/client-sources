package net.minecraft.entity.passive;

import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.passive.EntityRabbit;

class EntityRabbit$RabbitMoveHelper extends EntityMoveHelper {
   private EntityRabbit theEntity;

   public EntityRabbit$RabbitMoveHelper(EntityRabbit var1) {
      super(var1);
      this.theEntity = var1;
   }

   public void onUpdateMoveHelper() {
      if(this.theEntity.onGround && !this.theEntity.func_175523_cj()) {
         this.theEntity.setMovementSpeed(0.0D);
      }

      super.onUpdateMoveHelper();
   }
}
