package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIAvoidEntity;

class EntityAIAvoidEntity$1 implements Predicate {
   final EntityAIAvoidEntity this$0;

   EntityAIAvoidEntity$1(EntityAIAvoidEntity var1) {
      this.this$0 = var1;
   }

   public boolean apply(Entity var1) {
      return var1.isEntityAlive() && this.this$0.theEntity.getEntitySenses().canSee(var1);
   }
}
