package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;

class EntityEnderman$1 implements Predicate {
   final EntityEnderman this$0;

   EntityEnderman$1(EntityEnderman var1) {
      this.this$0 = var1;
   }

   public boolean apply(EntityEndermite var1) {
      return var1.isSpawnedByPlayer();
   }
}
