package net;

import java.util.Random;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.DifficultyInstance;

public class aug {
   private static boolean b;

   public static boolean B(EntityLiving var0) {
      return var0.getLeashed();
   }

   public static Entity G(EntityLiving var0) {
      return var0.getLeashedToEntity();
   }

   public static void a(EntityLiving var0, Entity var1, boolean var2) {
      var0.setLeashedToEntity(var1, var2);
   }

   public static void a(EntityLiving var0, boolean var1, boolean var2) {
      var0.clearLeashed(var1, var2);
   }

   public static Random F(EntityLiving var0) {
      return var0.getRNG();
   }

   public static boolean l(EntityLiving var0) {
      return var0.isChild();
   }

   public static PathNavigate q(EntityLiving var0) {
      return var0.getNavigator();
   }

   public static void d(EntityLiving var0) {
      var0.eatGrassBonus();
   }

   public static void c(EntityLiving var0, float var1) {
      var0.setMoveForward(var1);
   }

   public static AxisAlignedBB m(EntityLiving var0) {
      return var0.getEntityBoundingBox();
   }

   public static IAttributeInstance a(EntityLiving var0, IAttribute var1) {
      return var0.getEntityAttribute(var1);
   }

   public static void a(EntityLiving var0, float var1) {
      var0.setAIMoveSpeed(var1);
   }

   public static EntityJumpHelper x(EntityLiving var0) {
      return var0.getJumpHelper();
   }

   public static double a(EntityLiving var0, BlockPos var1) {
      return var0.getDistanceSq(var1);
   }

   public static int b(EntityLiving var0) {
      return var0.getEntityID();
   }

   public static EntityLivingBase D(EntityLiving var0) {
      return var0.getAttackTarget();
   }

   public static double b(EntityLiving var0, Entity var1) {
      return var0.getDistanceSqToEntity(var1);
   }

   public static void a(EntityLiving var0, boolean var1) {
      var0.setJumping(var1);
   }

   public static float y(EntityLiving var0) {
      return var0.getRenderSizeModifier();
   }

   public static double a(EntityLiving var0, double var1, double var3, double var5) {
      return var0.getDistanceSq(var1, var3, var5);
   }

   public static Team w(EntityLiving var0) {
      return var0.getTeam();
   }

   public static void a(EntityLiving var0, EntityLivingBase var1) {
      var0.setAttackTarget(var1);
   }

   public static boolean p(EntityLiving var0) {
      return var0.isInWater();
   }

   public static boolean n(EntityLiving var0) {
      return var0.isInLava();
   }

   public static EntityLookHelper v(EntityLiving var0) {
      return var0.getLookHelper();
   }

   public static boolean c(EntityLiving var0, Entity var1) {
      return var0.attackEntityAsMob(var1);
   }

   public static double b(EntityLiving var0, BlockPos var1) {
      return var0.getDistanceSqToCenter(var1);
   }

   public static EntityMoveHelper c(EntityLiving var0) {
      return var0.getMoveHelper();
   }

   public static int a(ItemStack var0) {
      return EntityLiving.getArmorPosition(var0);
   }

   public static void a(EntityLiving var0, int var1, float var2) {
      var0.setEquipmentDropChance(var1, var2);
   }

   public static int h(EntityLiving var0) {
      return var0.getVerticalFaceSpeed();
   }

   public static boolean o(EntityLiving var0) {
      return var0.isEntityAlive();
   }

   public static boolean z(EntityLiving var0) {
      return var0.canBeSteered();
   }

   public static void a(EntityLiving var0, float var1, float var2) {
      var0.moveEntityWithHeading(var1, var2);
   }

   public static boolean a(EntityLiving var0, Class var1) {
      return var0.canAttackClass(var1);
   }

   public static EntitySenses t(EntityLiving var0) {
      return var0.getEntitySenses();
   }

   public static void a(EntityLiving var0, double var1, double var3, double var5, float var7, float var8) {
      var0.setLocationAndAngles(var1, var3, var5, var7, var8);
   }

   public static boolean H(EntityLiving var0) {
      return var0.getCanSpawnHere();
   }

   public static boolean e(EntityLiving var0) {
      return var0.isNotColliding();
   }

   public static IEntityLivingData a(EntityLiving var0, DifficultyInstance var1, IEntityLivingData var2) {
      return var0.onInitialSpawn(var1, var2);
   }

   public static int C(EntityLiving var0) {
      return var0.getMaxSpawnedInChunk();
   }

   public static void g(EntityLiving var0) {
      var0.playLivingSound();
   }

   public static float k(EntityLiving var0) {
      return var0.getEyeHeight();
   }

   public static boolean u(EntityLiving var0) {
      return var0.getAlwaysRenderNameTagForRender();
   }

   public static boolean j(EntityLiving var0) {
      return var0.hasCustomName();
   }

   public static int b(EntityLiving var0, float var1) {
      return var0.getBrightnessForRender(var1);
   }

   public static boolean A(EntityLiving var0) {
      return var0.canPickUpLoot();
   }

   public static boolean i(EntityLiving var0) {
      return var0.isRiding();
   }

   public static void a(EntityLiving var0, String var1) {
      var0.setCustomNameTag(var1);
   }

   public static void r(EntityLiving var0) {
      var0.enablePersistence();
   }

   public static void E(EntityLiving var0) {
      var0.spawnExplosionParticle();
   }

   public static boolean f(EntityLiving var0) {
      return var0.isNoDespawnRequired();
   }

   public static BlockPos s(EntityLiving var0) {
      return var0.getPosition();
   }

   public static UUID a(EntityLiving var0) {
      return var0.getUniqueID();
   }

   public static boolean a(EntityLiving var0, Entity var1) {
      return var0.canEntityBeSeen(var1);
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!b()) {
         b(true);
      }

   }
}
