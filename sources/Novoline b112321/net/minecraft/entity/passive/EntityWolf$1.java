package net.minecraft.entity.passive;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;

class EntityWolf$1 implements Predicate {
   final EntityWolf this$0;

   EntityWolf$1(EntityWolf var1) {
      this.this$0 = var1;
   }

   public boolean apply(Entity var1) {
      return var1 instanceof EntitySheep || var1 instanceof EntityRabbit;
   }
}
