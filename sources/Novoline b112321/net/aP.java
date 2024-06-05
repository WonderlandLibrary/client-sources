package net;

import com.google.common.base.Predicate;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityIronGolem$AINearestAttackableTargetNonCreeper$1;

class aP extends EntityAINearestAttackableTarget {
   public aP(EntityCreature var1, Class var2, int var3, boolean var4, boolean var5, Predicate var6) {
      super(var1, var2, var3, var4, var5, var6);
      this.targetEntitySelector = new EntityIronGolem$AINearestAttackableTargetNonCreeper$1(this, var6, var1);
   }

   static double a(aP var0) {
      return var0.getTargetDistance();
   }

   static boolean a(aP var0, EntityLivingBase var1, boolean var2) {
      return var0.isSuitableTarget(var1, var2);
   }
}
