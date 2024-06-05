package net;

import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

public class q2 {
   private static String[] b;

   public static int e(WorldProvider var0) {
      return var0.getDimensionId();
   }

   public static boolean h(WorldProvider var0) {
      return var0.getHasNoSky();
   }

   public static boolean f(WorldProvider var0) {
      return var0.canRespawnHere();
   }

   public static int m(WorldProvider var0) {
      return var0.getAverageGroundLevel();
   }

   public static WorldProvider a(int var0) {
      return WorldProvider.getProviderForDimension(var0);
   }

   public static void a(WorldProvider var0, World var1) {
      var0.registerWorld(var1);
   }

   public static boolean k(WorldProvider var0) {
      return var0.isSurfaceWorld();
   }

   public static float[] c(WorldProvider var0) {
      return var0.getLightBrightnessTable();
   }

   public static float[] a(WorldProvider var0, float var1, float var2) {
      return var0.calcSunriseSunsetColors(var1, var2);
   }

   public static double l(WorldProvider var0) {
      return var0.getVoidFogYFactor();
   }

   public static boolean a(WorldProvider var0, int var1, int var2) {
      return var0.doesXZShowFog(var1, var2);
   }

   public static IChunkProvider i(WorldProvider var0) {
      return var0.createChunkGenerator();
   }

   public static boolean b(WorldProvider var0, int var1, int var2) {
      return var0.canCoordinateBeSpawn(var1, var2);
   }

   public static String g(WorldProvider var0) {
      return var0.getInternalNameSuffix();
   }

   public static boolean d(WorldProvider var0) {
      return var0.doesWaterVaporize();
   }

   public static boolean b(WorldProvider var0) {
      return var0.isSkyColored();
   }

   public static float j(WorldProvider var0) {
      return var0.getCloudHeight();
   }

   public static String a(WorldProvider var0) {
      return var0.getDimensionName();
   }

   public static float a(WorldProvider var0, long var1, float var3) {
      return var0.calculateCelestialAngle(var1, var3);
   }

   public static int a(WorldProvider var0, long var1) {
      return var0.getMoonPhase(var1);
   }

   public static Vec3 b(WorldProvider var0, float var1, float var2) {
      return var0.getFogColor(var1, var2);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[5]);
      }

   }
}
