package net.minecraft.entity.monster;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.monster.EntitySpider;

class EntitySpider$AISpiderAttack extends EntityAIAttackOnCollide {
   public EntitySpider$AISpiderAttack(EntitySpider var1, Class var2) {
      super(var1, var2, 1.0D, true);
   }

   public boolean continueExecuting() {
      float var1 = this.attacker.getBrightness(1.0F);
      if(var1 >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
         this.attacker.setAttackTarget((EntityLivingBase)null);
         return false;
      } else {
         return super.continueExecuting();
      }
   }

   protected double func_179512_a(EntityLivingBase var1) {
      return (double)(4.0F + var1.width);
   }
}
