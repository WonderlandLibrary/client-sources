package net;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;

public class aga {
   public static boolean c(EntityAnimal var0) {
      return var0.isInLove();
   }

   public static boolean j(EntityAnimal var0) {
      return var0.isEntityAlive();
   }

   public static EntityLookHelper f(EntityAnimal var0) {
      return var0.getLookHelper();
   }

   public static int a(EntityAnimal var0) {
      return var0.getVerticalFaceSpeed();
   }

   public static PathNavigate g(EntityAnimal var0) {
      return var0.getNavigator();
   }

   public static double a(EntityAnimal var0, Entity var1) {
      return var0.getDistanceSqToEntity(var1);
   }

   public static AxisAlignedBB b(EntityAnimal var0) {
      return var0.getEntityBoundingBox();
   }

   public static boolean a(EntityAnimal var0, EntityAnimal var1) {
      return var0.canMateWith(var1);
   }

   public static EntityAgeable a(EntityAnimal var0, EntityAgeable var1) {
      return var0.createChild(var1);
   }

   public static EntityPlayer i(EntityAnimal var0) {
      return var0.getPlayerInLove();
   }

   public static void a(EntityAnimal var0, int var1) {
      var0.setGrowingAge(var1);
   }

   public static void d(EntityAnimal var0) {
      var0.resetInLove();
   }

   public static Random h(EntityAnimal var0) {
      return var0.getRNG();
   }

   public static int e(EntityAnimal var0) {
      return var0.getGrowingAge();
   }
}
