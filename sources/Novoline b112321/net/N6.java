package net;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.pathfinding.PathNavigate;

public class N6 {
   public static EntityLivingBase f(EntityBlaze var0) {
      return var0.getAttackTarget();
   }

   public static void a(EntityBlaze var0, boolean var1) {
      var0.setOnFire(var1);
   }

   public static double b(EntityBlaze var0, Entity var1) {
      return var0.getDistanceSqToEntity(var1);
   }

   public static boolean a(EntityBlaze var0, Entity var1) {
      return var0.attackEntityAsMob(var1);
   }

   public static EntityMoveHelper e(EntityBlaze var0) {
      return var0.getMoveHelper();
   }

   public static Random a(EntityBlaze var0) {
      return var0.getRNG();
   }

   public static EntityLookHelper c(EntityBlaze var0) {
      return var0.getLookHelper();
   }

   public static PathNavigate b(EntityBlaze var0) {
      return var0.getNavigator();
   }

   public static boolean d(EntityBlaze var0) {
      return var0.func_70845_n();
   }
}
