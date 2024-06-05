package net.minecraft.util;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;

final class EntitySelectors$3 implements Predicate {
   public boolean apply(Entity var1) {
      return var1 instanceof IInventory && var1.isEntityAlive();
   }
}
