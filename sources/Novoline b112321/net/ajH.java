package net;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class ajH {
   private static String b;

   public static float a(EntityCreature var0, float var1) {
      return var0.getBrightness(var1);
   }

   public static Random q(EntityCreature var0) {
      return var0.getRNG();
   }

   public static void a(EntityCreature var0, EntityLivingBase var1) {
      var0.setAttackTarget(var1);
   }

   public static boolean n(EntityCreature var0) {
      return var0.hasHome();
   }

   public static BlockPos h(EntityCreature var0) {
      return var0.getHomePosition();
   }

   public static float c(EntityCreature var0) {
      return var0.getMaximumHomeDistance();
   }

   public static boolean a(EntityCreature var0, BlockPos var1) {
      return var0.isWithinHomeDistanceFromPosition(var1);
   }

   public static float d(EntityCreature var0, BlockPos var1) {
      return var0.getBlockPathWeight(var1);
   }

   public static PathNavigate e(EntityCreature var0) {
      return var0.getNavigator();
   }

   public static double a(EntityCreature var0, Entity var1) {
      return var0.getDistanceSqToEntity(var1);
   }

   public static EntityLookHelper p(EntityCreature var0) {
      return var0.getLookHelper();
   }

   public static int o(EntityCreature var0) {
      return var0.getVerticalFaceSpeed();
   }

   public static EntitySenses d(EntityCreature var0) {
      return var0.getEntitySenses();
   }

   public static double a(EntityCreature var0, double var1, double var3, double var5) {
      return var0.getDistanceSq(var1, var3, var5);
   }

   public static double c(EntityCreature var0, BlockPos var1) {
      return var0.getDistanceSq(var1);
   }

   public static AxisAlignedBB t(EntityCreature var0) {
      return var0.getEntityBoundingBox();
   }

   public static float f(EntityCreature var0) {
      return var0.getAIMoveSpeed();
   }

   public static EntityJumpHelper g(EntityCreature var0) {
      return var0.getJumpHelper();
   }

   public static EntityLivingBase r(EntityCreature var0) {
      return var0.getAITarget();
   }

   public static boolean a(EntityCreature var0) {
      return var0.isBurning();
   }

   public static EntityLivingBase b(EntityCreature var0) {
      return var0.getAttackTarget();
   }

   public static Team l(EntityCreature var0) {
      return var0.getTeam();
   }

   public static IAttributeInstance a(EntityCreature var0, IAttribute var1) {
      return var0.getEntityAttribute(var1);
   }

   public static int j(EntityCreature var0) {
      return var0.getAge();
   }

   public static int k(EntityCreature var0) {
      return var0.getRevengeTimer();
   }

   public static boolean b(EntityCreature var0, EntityLivingBase var1) {
      return var0.isOnSameTeam(var1);
   }

   public static double b(EntityCreature var0, BlockPos var1) {
      return var0.getDistanceSqToCenter(var1);
   }

   public static boolean s(EntityCreature var0) {
      return var0.isWithinHomeDistanceCurrentPosition();
   }

   public static ItemStack m(EntityCreature var0) {
      return var0.getHeldItem();
   }

   public static void i(EntityCreature var0) {
      var0.swingItem();
   }

   public static boolean b(EntityCreature var0, Entity var1) {
      return var0.attackEntityAsMob(var1);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() != null) {
         b("L5Af6");
      }

   }
}
