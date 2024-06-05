package net;

import java.util.UUID;
import net.rF;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandResultStats;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class AU {
   private static boolean b;

   public static float b(Entity var0) {
      return var0.getEyeHeight();
   }

   public static boolean D(Entity var0) {
      return var0.isSneaking();
   }

   public static void a(Entity var0, float var1, float var2) {
      var0.fall(var1, var2);
   }

   public static IChatComponent a(Entity var0) {
      return var0.getDisplayName();
   }

   public static int n(Entity var0) {
      return var0.getEntityID();
   }

   public static boolean f(Entity var0) {
      return var0.isRiding();
   }

   public static boolean J(Entity var0) {
      return var0.isInWater();
   }

   public static boolean Q(Entity var0) {
      return var0.isSprinting();
   }

   public static boolean h(Entity var0) {
      return var0.isEntityAlive();
   }

   public static boolean s(Entity var0) {
      return var0.isInvisible();
   }

   public static float i(Entity var0) {
      return var0.getCollisionBorderSize();
   }

   public static AxisAlignedBB C(Entity var0) {
      return var0.getEntityBoundingBox();
   }

   public static float b(Entity var0, Entity var1) {
      return var0.getDistanceToEntity(var1);
   }

   public static void j(Entity var0) {
      var0.updateRiderPosition();
   }

   public static boolean a(Entity var0, EntityPlayer var1, Vec3 var2) {
      return var0.interactAt(var1, var2);
   }

   public static boolean B(Entity var0) {
      return var0.doesEntityNotTriggerPressurePlate();
   }

   public static UUID d(Entity var0) {
      return var0.getUniqueID();
   }

   public static boolean w(Entity var0) {
      return var0.canBeCollidedWith();
   }

   public static boolean a(Entity var0, double var1, double var3, double var5) {
      return var0.isInRangeToRender3d(var1, var3, var5);
   }

   public static boolean P(Entity var0) {
      return var0.getAlwaysRenderNameTagForRender();
   }

   public static boolean x(Entity var0) {
      return var0.hasCustomName();
   }

   public static boolean v(Entity var0) {
      return var0.canRenderOnFire();
   }

   public static double d(Entity var0, Entity var1) {
      return var0.getDistanceSqToEntity(var1);
   }

   public static int a(Entity var0, float var1) {
      return var0.getBrightnessForRender(var1);
   }

   public static void e(Entity var0, double var1, double var3, double var5) {
      var0.setPositionAndUpdate(var1, var3, var5);
   }

   public static boolean a(Entity var0, DamageSource var1, float var2) {
      return var0.attackEntityFrom(var1, var2);
   }

   public static ItemStack[] u(Entity var0) {
      return var0.getInventory();
   }

   public static void a(Entity var0, String var1, float var2, float var3) {
      var0.playSound(var1, var2, var3);
   }

   public static void c(Entity var0, int var1) {
      var0.setEntityId(var1);
   }

   public static double f(Entity var0, double var1, double var3, double var5) {
      return var0.getDistanceSq(var1, var3, var5);
   }

   public static void d(Entity var0, NBTTagCompound var1) {
      var0.readFromNBT(var1);
   }

   public static MovingObjectPosition a(Entity var0, double var1, float var3) {
      return var0.rayTrace(var1, var3);
   }

   public static Vec3 d(Entity var0, float var1) {
      return var0.getPositionEyes(var1);
   }

   public static Vec3 b(Entity var0, float var1) {
      return var0.getLook(var1);
   }

   public static boolean a(Entity var0, Material var1) {
      return var0.isInsideOfMaterial(var1);
   }

   public static void F(Entity var0) {
      var0.setDead();
   }

   public static Entity[] S(Entity var0) {
      return var0.getParts();
   }

   public static void c(Entity var0, NBTTagCompound var1) {
      var0.writeToNBT(var1);
   }

   public static double c(Entity var0, BlockPos var1) {
      return var0.getDistanceSqToCenter(var1);
   }

   public static boolean a(Entity var0, int var1, ItemStack var2) {
      return var0.replaceItemInInventory(var1, var2);
   }

   public static World g(Entity var0) {
      return var0.getEntityWorld();
   }

   public static String q(Entity var0) {
      return var0.getName();
   }

   public static void a(Entity var0, CrashReportCategory var1) {
      var0.addEntityCrashInfo(var1);
   }

   public static void a(Entity var0, int var1) {
      var0.travelToDimension(var1);
   }

   public static int L(Entity var0) {
      return var0.getMaxFallHeight();
   }

   public static boolean G(Entity var0) {
      return var0.isInLava();
   }

   public static void a(Entity var0, Entity var1) {
      var0.mountEntity(var1);
   }

   public static float a(Entity var0, Explosion var1, World var2, BlockPos var3, IBlockState var4) {
      return var0.getExplosionResistance(var1, var2, var3, var4);
   }

   public static boolean a(Entity var0, Explosion var1, World var2, BlockPos var3, IBlockState var4, float var5) {
      return var0.verifyExplosion(var1, var2, var3, var4, var5);
   }

   public static boolean M(Entity var0) {
      return var0.isImmuneToExplosions();
   }

   public static double d(Entity var0, double var1, double var3, double var5) {
      return var0.getDistance(var1, var3, var5);
   }

   public static EnumFacing R(Entity var0) {
      return var0.getHorizontalFacing();
   }

   public static void a(Entity var0, String var1) {
      var0.setCustomNameTag(var1);
   }

   public static void b(Entity var0, double var1, double var3, double var5, float var7, float var8) {
      var0.setLocationAndAngles(var1, var3, var5, var7, var8);
   }

   public static boolean N(Entity var0) {
      return var0.isBurning();
   }

   public static void c(Entity var0, float var1) {
      var0.setRotationYawHead(var1);
   }

   public static float I(Entity var0) {
      return var0.getRotationYawHead();
   }

   public static rF p(Entity var0) {
      return var0.k();
   }

   public static NBTTagCompound m(Entity var0) {
      return var0.getNBTTagCompound();
   }

   public static boolean a(Entity var0, EntityPlayerMP var1) {
      return var0.isSpectatedByPlayer(var1);
   }

   public static double b(Entity var0, BlockPos var1) {
      return var0.getDistanceSq(var1);
   }

   public static void b(Entity var0, double var1, double var3, double var5) {
      var0.setVelocity(var1, var3, var5);
   }

   public static void a(Entity var0, double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      boolean var11 = b();
      var0.setPositionAndRotation2(var1, var3, var5, var7, var8, var9, var10);
   }

   public static void e(Entity var0) {
      var0.performHurtAnimation();
   }

   public static void a(Entity var0, double var1, double var3, double var5, float var7, float var8) {
      var0.setPositionAndRotation(var1, var3, var5, var7, var8);
   }

   public static void a(Entity var0, byte var1) {
      var0.handleStatusUpdate(var1);
   }

   public static void b(Entity var0, int var1, ItemStack var2) {
      var0.setCurrentItemOrArmor(var1, var2);
   }

   public static void b(Entity var0, NBTTagCompound var1) {
      var0.clientUpdateEntityNBT(var1);
   }

   public static Vec3 K(Entity var0) {
      return var0.func_181014_aG();
   }

   public static EnumFacing o(Entity var0) {
      return var0.func_181012_aH();
   }

   public static BlockPos l(Entity var0) {
      return var0.getPosition();
   }

   public static void a(Entity var0, World var1) {
      var0.setWorld(var1);
   }

   public static void b(Entity var0, int var1) {
      var0.onDataWatcherUpdate(var1);
   }

   public static void t(Entity var0) {
      var0.extinguish();
   }

   public static boolean a(Entity var0, NBTTagCompound var1) {
      return var0.writeToNBTOptional(var1);
   }

   public static CommandResultStats r(Entity var0) {
      return var0.getCommandStats();
   }

   public static void E(Entity var0) {
      var0.onKillCommand();
   }

   public static int k(Entity var0) {
      return var0.getPortalCoolDown();
   }

   public static void a(Entity var0, BlockPos var1) {
      var0.func_181015_d(var1);
   }

   public static boolean H(Entity var0) {
      return var0.isOutsideBorder();
   }

   public static void a(Entity var0, boolean var1) {
      var0.setOutsideBorder(var1);
   }

   public static AxisAlignedBB y(Entity var0) {
      return var0.getCollisionBoundingBox();
   }

   public static AxisAlignedBB c(Entity var0, Entity var1) {
      return var0.getCollisionBox(var1);
   }

   public static void z(Entity var0) {
      var0.onUpdate();
   }

   public static void c(Entity var0) {
      var0.updateRidden();
   }

   public static boolean A(Entity var0) {
      return var0.isPushedByWater();
   }

   public static void O(Entity var0) {
      var0.setInWeb();
   }

   public static void c(Entity var0, double var1, double var3, double var5) {
      var0.moveEntity(var1, var3, var5);
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean c() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!c()) {
         b(true);
      }

   }
}
