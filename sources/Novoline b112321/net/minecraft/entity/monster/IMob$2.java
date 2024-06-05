package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;

final class IMob$2 implements Predicate {
   public boolean apply(Entity var1) {
      return var1 instanceof IMob && !var1.isInvisible();
   }
}
