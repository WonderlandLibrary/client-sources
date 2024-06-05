package net.minecraft.entity.ai;

import java.util.Comparator;
import net.minecraft.entity.Entity;

public class EntityAINearestAttackableTarget$Sorter implements Comparator {
   private final Entity theEntity;

   public EntityAINearestAttackableTarget$Sorter(Entity var1) {
      this.theEntity = var1;
   }

   public int compare(Entity var1, Entity var2) {
      double var3 = this.theEntity.getDistanceSqToEntity(var1);
      double var5 = this.theEntity.getDistanceSqToEntity(var2);
      return var3 < var5?-1:(var3 > var5?1:0);
   }
}
