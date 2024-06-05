package net;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.Explosion;

public class ht {
   private static boolean b;

   public static Entity b(DamageSource var0) {
      return var0.getEntity();
   }

   public static DamageSource a(EntityArrow var0, Entity var1) {
      return DamageSource.causeArrowDamage(var0, var1);
   }

   public static DamageSource a(EntityLivingBase var0) {
      return DamageSource.causeMobDamage(var0);
   }

   public static boolean d(DamageSource var0) {
      return var0.isExplosion();
   }

   public static boolean g(DamageSource var0) {
      return var0.isFireDamage();
   }

   public static boolean i(DamageSource var0) {
      return var0.isUnblockable();
   }

   public static boolean l(DamageSource var0) {
      return var0.isDamageAbsolute();
   }

   public static DamageSource b(Entity var0, Entity var1) {
      return DamageSource.causeThrownDamage(var0, var1);
   }

   public static Entity m(DamageSource var0) {
      return var0.getSourceOfDamage();
   }

   public static IChatComponent a(DamageSource var0, EntityLivingBase var1) {
      return var0.getDeathMessage(var1);
   }

   public static boolean e(DamageSource var0) {
      return var0.isMagicDamage();
   }

   public static DamageSource a(Entity var0) {
      return DamageSource.causeThornsDamage(var0);
   }

   public static boolean h(DamageSource var0) {
      return var0.canHarmInCreative();
   }

   public static boolean k(DamageSource var0) {
      return var0.isProjectile();
   }

   public static boolean j(DamageSource var0) {
      return var0.isDifficultyScaled();
   }

   public static float f(DamageSource var0) {
      return var0.getHungerDamage();
   }

   public static DamageSource a(EntityPlayer var0) {
      return DamageSource.causePlayerDamage(var0);
   }

   public static DamageSource a(EntityFireball var0, Entity var1) {
      return DamageSource.causeFireballDamage(var0, var1);
   }

   public static DamageSource a(Entity var0, Entity var1) {
      return DamageSource.causeIndirectMagicDamage(var0, var1);
   }

   public static DamageSource a(Explosion var0) {
      return DamageSource.setExplosionSource(var0);
   }

   public static boolean c(DamageSource var0) {
      return var0.isCreativePlayer();
   }

   public static String a(DamageSource var0) {
      return var0.getDamageType();
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
      if(!a()) {
         b(true);
      }

   }
}
