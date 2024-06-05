package net;

import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettings$GameType;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.WorldInfo;

public class kz {
   private static String b;

   public static int s(WorldInfo var0) {
      return var0.getSpawnX();
   }

   public static int v(WorldInfo var0) {
      return var0.getSpawnZ();
   }

   public static WorldType n(WorldInfo var0) {
      return var0.getTerrainType();
   }

   public static boolean y(WorldInfo var0) {
      return var0.isHardcoreModeEnabled();
   }

   public static String H(WorldInfo var0) {
      return var0.getGeneratorOptions();
   }

   public static boolean d(WorldInfo var0) {
      return var0.isMapFeaturesEnabled();
   }

   public static boolean h(WorldInfo var0) {
      return var0.isDifficultyLocked();
   }

   public static void g(WorldInfo var0, boolean var1) {
      var0.setDifficultyLocked(var1);
   }

   public static void a(WorldInfo var0, EnumDifficulty var1) {
      var0.setDifficulty(var1);
   }

   public static EnumDifficulty e(WorldInfo var0) {
      return var0.getDifficulty();
   }

   public static void c(WorldInfo var0, int var1) {
      var0.setCleanWeatherTime(var1);
   }

   public static void i(WorldInfo var0, int var1) {
      var0.setRainTime(var1);
   }

   public static void e(WorldInfo var0, int var1) {
      var0.setThunderTime(var1);
   }

   public static void a(WorldInfo var0, boolean var1) {
      var0.setRaining(var1);
   }

   public static void d(WorldInfo var0, boolean var1) {
      var0.setThundering(var1);
   }

   public static String t(WorldInfo var0) {
      return var0.getWorldName();
   }

   public static long x(WorldInfo var0) {
      return var0.getLastTimePlayed();
   }

   public static WorldSettings$GameType k(WorldInfo var0) {
      return var0.getGameType();
   }

   public static boolean a(WorldInfo var0) {
      return var0.areCommandsAllowed();
   }

   public static long b(WorldInfo var0) {
      return var0.getSeed();
   }

   public static void d(WorldInfo var0, int var1) {
      var0.setSaveVersion(var1);
   }

   public static void a(WorldInfo var0, WorldType var1) {
      var0.setTerrainType(var1);
   }

   public static double G(WorldInfo var0) {
      return var0.getBorderCenterX();
   }

   public static double w(WorldInfo var0) {
      return var0.getBorderCenterZ();
   }

   public static double i(WorldInfo var0) {
      return var0.getBorderDamagePerBlock();
   }

   public static double F(WorldInfo var0) {
      return var0.getBorderSafeZone();
   }

   public static int l(WorldInfo var0) {
      return var0.getBorderWarningDistance();
   }

   public static int m(WorldInfo var0) {
      return var0.getBorderWarningTime();
   }

   public static long p(WorldInfo var0) {
      return var0.getBorderLerpTime();
   }

   public static double c(WorldInfo var0) {
      return var0.getBorderSize();
   }

   public static double o(WorldInfo var0) {
      return var0.getBorderLerpTarget();
   }

   public static long j(WorldInfo var0) {
      return var0.getWorldTotalTime();
   }

   public static void b(WorldInfo var0, long var1) {
      var0.setWorldTotalTime(var1);
   }

   public static int q(WorldInfo var0) {
      return var0.getSpawnY();
   }

   public static void f(WorldInfo var0, int var1) {
      var0.setSpawnY(var1);
   }

   public static void g(WorldInfo var0, int var1) {
      var0.setSpawnX(var1);
   }

   public static void a(WorldInfo var0, int var1) {
      var0.setSpawnZ(var1);
   }

   public static boolean E(WorldInfo var0) {
      return var0.isInitialized();
   }

   public static void b(WorldInfo var0, boolean var1) {
      var0.setServerInitialized(var1);
   }

   public static void c(WorldInfo var0, boolean var1) {
      var0.setMapFeaturesEnabled(var1);
   }

   public static void e(WorldInfo var0, boolean var1) {
      var0.setAllowCommands(var1);
   }

   public static void a(WorldInfo var0, WorldSettings$GameType var1) {
      var0.setGameType(var1);
   }

   public static void f(WorldInfo var0, boolean var1) {
      var0.setHardcore(var1);
   }

   public static void a(WorldInfo var0, BlockPos var1) {
      var0.setSpawn(var1);
   }

   public static void d(WorldInfo var0, double var1) {
      var0.setBorderSize(var1);
   }

   public static void f(WorldInfo var0, double var1) {
      var0.getBorderCenterX(var1);
   }

   public static void a(WorldInfo var0, double var1) {
      var0.getBorderCenterZ(var1);
   }

   public static void b(WorldInfo var0, double var1) {
      var0.setBorderSafeZone(var1);
   }

   public static void e(WorldInfo var0, double var1) {
      var0.setBorderDamagePerBlock(var1);
   }

   public static void h(WorldInfo var0, int var1) {
      var0.setBorderWarningDistance(var1);
   }

   public static void b(WorldInfo var0, int var1) {
      var0.setBorderWarningTime(var1);
   }

   public static void c(WorldInfo var0, double var1) {
      var0.setBorderLerpTarget(var1);
   }

   public static void a(WorldInfo var0, long var1) {
      var0.setBorderLerpTime(var1);
   }

   public static void a(WorldInfo var0, WorldSettings var1) {
      var0.populateFromWorldSettings(var1);
   }

   public static boolean f(WorldInfo var0) {
      return var0.isRaining();
   }

   public static long C(WorldInfo var0) {
      return var0.getWorldTime();
   }

   public static NBTTagCompound u(WorldInfo var0) {
      return var0.getPlayerNBTTagCompound();
   }

   public static int A(WorldInfo var0) {
      return var0.getCleanWeatherTime();
   }

   public static int D(WorldInfo var0) {
      return var0.getThunderTime();
   }

   public static int r(WorldInfo var0) {
      return var0.getRainTime();
   }

   public static GameRules B(WorldInfo var0) {
      return var0.getGameRulesInstance();
   }

   public static void a(WorldInfo var0, CrashReportCategory var1) {
      var0.addToCrashReport(var1);
   }

   public static NBTTagCompound a(WorldInfo var0, NBTTagCompound var1) {
      return var0.cloneNBTCompound(var1);
   }

   public static NBTTagCompound z(WorldInfo var0) {
      return var0.getNBTTagCompound();
   }

   public static long g(WorldInfo var0) {
      return var0.getSizeOnDisk();
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() != null) {
         b("Ukk2Pc");
      }

   }
}
