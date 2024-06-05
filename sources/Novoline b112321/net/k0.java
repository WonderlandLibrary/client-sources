package net;

import java.util.Random;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.village.Village;

public class k0 {
   public static int a(EntityIronGolem var0) {
      return var0.getHoldRoseTick();
   }

   public static int a(EntityIronGolem var0, float var1) {
      return var0.getBrightnessForRender(var1);
   }

   public static Random d(EntityIronGolem var0) {
      return var0.getRNG();
   }

   public static AxisAlignedBB g(EntityIronGolem var0) {
      return var0.getEntityBoundingBox();
   }

   public static void a(EntityIronGolem var0, boolean var1) {
      var0.setHoldingRose(var1);
   }

   public static EntityLookHelper e(EntityIronGolem var0) {
      return var0.getLookHelper();
   }

   public static void b(EntityIronGolem var0, boolean var1) {
      var0.setPlayerCreated(var1);
   }

   public static void a(EntityIronGolem var0, double var1, double var3, double var5, float var7, float var8) {
      var0.setLocationAndAngles(var1, var3, var5, var7, var8);
   }

   public static void a(EntityIronGolem var0, double var1, double var3, double var5) {
      var0.setPosition(var1, var3, var5);
   }

   public static Village b(EntityIronGolem var0) {
      return var0.getVillage();
   }

   public static void a(EntityIronGolem var0, EntityLivingBase var1) {
      var0.setAttackTarget(var1);
   }

   public static PathNavigate c(EntityIronGolem var0) {
      return var0.getNavigator();
   }

   public static int f(EntityIronGolem var0) {
      return var0.getAttackTimer();
   }
}
