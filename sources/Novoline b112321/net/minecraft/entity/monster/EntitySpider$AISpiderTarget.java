package net.minecraft.entity.monster;

import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntitySpider;

class EntitySpider$AISpiderTarget extends EntityAINearestAttackableTarget {
   public EntitySpider$AISpiderTarget(EntitySpider var1, Class var2) {
      super(var1, var2, true);
   }

   public boolean shouldExecute() {
      float var1 = this.taskOwner.getBrightness(1.0F);
      return var1 < 0.5F && super.shouldExecute();
   }
}
