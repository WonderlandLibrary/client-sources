package net;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigate;

public class _6 {
   public static void a(EntityHorse var0, int var1) {
      var0.setJumpPower(var1);
   }

   public static void b(EntityHorse var0, EntityPlayer var1) {
      var0.openGUI(var1);
   }

   public static int n(EntityHorse var0) {
      return var0.getHorseType();
   }

   public static float a(EntityHorse var0, float var1) {
      return var0.getGrassEatingAmount(var1);
   }

   public static boolean l(EntityHorse var0) {
      return var0.isAdultHorse();
   }

   public static boolean f(EntityHorse var0) {
      return var0.isHorseSaddled();
   }

   public static boolean d(EntityHorse var0) {
      return var0.isChested();
   }

   public static float m(EntityHorse var0) {
      return var0.getHorseSize();
   }

   public static float c(EntityHorse var0, float var1) {
      return var0.getRearingAmount(var1);
   }

   public static float b(EntityHorse var0, float var1) {
      return var0.getMouthOpennessAngle(var1);
   }

   public static boolean e(EntityHorse var0) {
      return var0.func_110239_cn();
   }

   public static String k(EntityHorse var0) {
      return var0.getHorseTexture();
   }

   public static boolean p(EntityHorse var0) {
      return var0.func_175507_cI();
   }

   public static String[] b(EntityHorse var0) {
      return var0.getVariantTexturePaths();
   }

   public static boolean q(EntityHorse var0) {
      return var0.canWearArmor();
   }

   public static boolean a(Item var0) {
      return EntityHorse.isArmorItem(var0);
   }

   public static boolean j(EntityHorse var0) {
      return var0.isTame();
   }

   public static PathNavigate c(EntityHorse var0) {
      return var0.getNavigator();
   }

   public static Random i(EntityHorse var0) {
      return var0.getRNG();
   }

   public static int g(EntityHorse var0) {
      return var0.getTemper();
   }

   public static int a(EntityHorse var0) {
      return var0.getMaxTemper();
   }

   public static boolean a(EntityHorse var0, EntityPlayer var1) {
      return var0.setTamedBy(var1);
   }

   public static int b(EntityHorse var0, int var1) {
      return var0.increaseTemper(var1);
   }

   public static void o(EntityHorse var0) {
      var0.makeHorseRearWithSound();
   }

   public static boolean h(EntityHorse var0) {
      return var0.isEntityAlive();
   }

   public static float a(EntityHorse var0, Entity var1) {
      return var0.getDistanceToEntity(var1);
   }
}
