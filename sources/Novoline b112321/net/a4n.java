package net;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.border.EnumBorderStatus;
import net.minecraft.world.border.IBorderListener;
import net.minecraft.world.border.WorldBorder;

public class a4n {
   private static int[] b;

   public static boolean a(WorldBorder var0, BlockPos var1) {
      return var0.contains(var1);
   }

   public static boolean a(WorldBorder var0, AxisAlignedBB var1) {
      return var0.contains(var1);
   }

   public static double a(WorldBorder var0, Entity var1) {
      return var0.getClosestDistance(var1);
   }

   public static double i(WorldBorder var0) {
      return var0.getDamageBuffer();
   }

   public static double c(WorldBorder var0) {
      return var0.getDamageAmount();
   }

   public static void b(WorldBorder var0, int var1) {
      var0.setSize(var1);
   }

   public static void b(WorldBorder var0, double var1, double var3) {
      var0.setCenter(var1, var3);
   }

   public static void a(WorldBorder var0, double var1) {
      var0.setDamageAmount(var1);
   }

   public static void b(WorldBorder var0, double var1) {
      var0.setDamageBuffer(var1);
   }

   public static void a(WorldBorder var0, int var1) {
      var0.setWarningDistance(var1);
   }

   public static void c(WorldBorder var0, int var1) {
      var0.setWarningTime(var1);
   }

   public static void a(WorldBorder var0, double var1, double var3, long var5) {
      var0.setTransition(var1, var3, var5);
   }

   public static void c(WorldBorder var0, double var1) {
      var0.setTransition(var1);
   }

   public static double f(WorldBorder var0) {
      return var0.getDiameter();
   }

   public static double l(WorldBorder var0) {
      return var0.getCenterX();
   }

   public static double e(WorldBorder var0) {
      return var0.getCenterZ();
   }

   public static int j(WorldBorder var0) {
      return var0.getWarningDistance();
   }

   public static int h(WorldBorder var0) {
      return var0.getWarningTime();
   }

   public static double m(WorldBorder var0) {
      return var0.getTargetSize();
   }

   public static long k(WorldBorder var0) {
      return var0.getTimeUntilTarget();
   }

   public static boolean a(WorldBorder var0, ChunkCoordIntPair var1) {
      return var0.contains(var1);
   }

   public static void a(WorldBorder var0, IBorderListener var1) {
      var0.addListener(var1);
   }

   public static double d(WorldBorder var0) {
      return var0.minX();
   }

   public static double n(WorldBorder var0) {
      return var0.maxX();
   }

   public static double o(WorldBorder var0) {
      return var0.minZ();
   }

   public static double p(WorldBorder var0) {
      return var0.maxZ();
   }

   public static EnumBorderStatus g(WorldBorder var0) {
      return var0.getStatus();
   }

   public static int b(WorldBorder var0) {
      return var0.getSize();
   }

   public static double a(WorldBorder var0, double var1, double var3) {
      return var0.getClosestDistance(var1, var3);
   }

   public static double a(WorldBorder var0) {
      return var0.getResizeSpeed();
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[2]);
      }

   }
}
