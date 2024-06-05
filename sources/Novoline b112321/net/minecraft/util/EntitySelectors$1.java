package net.minecraft.util;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;

final class EntitySelectors$1 implements Predicate {
   public boolean apply(Entity var1) {
      return var1.isEntityAlive();
   }
}
