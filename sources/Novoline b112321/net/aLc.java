package net;

import com.google.common.base.Predicate;
import java.util.List;
import java.util.UUID;
import net.acE;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.WorldInfo;

public class aLc {
   private static String b;

   public static IBlockState d(WorldClient var0, BlockPos var1) {
      return var0.getBlockState(var1);
   }

   public static List v(WorldClient var0) {
      return var0.getLoadedEntityList();
   }

   public static MovingObjectPosition a(WorldClient var0, Vec3 var1, Vec3 var2) {
      return var0.rayTraceBlocks(var1, var2);
   }

   public static void k(WorldClient var0) {
      var0.sendQuittingDisconnectingPacket();
   }

   public static void b(WorldClient var0, int var1) {
      var0.d(var1);
   }

   public static void a(WorldClient var0, int var1, Entity var2) {
      var0.addEntityToWorld(var1, var2);
   }

   public static List p(WorldClient var0) {
      return var0.getLoadedTileEntityList();
   }

   public static void d(WorldClient var0, Entity var1) {
      var0.joinEntityInSurroundings(var1);
   }

   public static int m(WorldClient var0) {
      return var0.getLastLightningBolt();
   }

   public static void a(WorldClient var0, int var1) {
      var0.setLastLightningBolt(var1);
   }

   public static void e(WorldClient var0) {
      var0.updateEntities();
   }

   public static EnumDifficulty l(WorldClient var0) {
      return var0.getDifficulty();
   }

   public static void a(WorldClient var0, boolean var1, boolean var2) {
      var0.setAllowedSpawnTypes(var1, var2);
   }

   public static void u(WorldClient var0) {
      var0.tick();
   }

   public static CrashReportCategory a(WorldClient var0, CrashReport var1) {
      return var0.addWorldInfoToCrashReport(var1);
   }

   public static void a(WorldClient var0, int var1, int var2, int var3) {
      var0.doVoidFogParticles(var1, var2, var3);
   }

   public static TileEntity a(WorldClient var0, BlockPos var1) {
      return var0.getTileEntity(var1);
   }

   public static boolean a(WorldClient var0, Entity var1) {
      return var0.spawnEntityInWorld(var1);
   }

   public static void g(WorldClient var0) {
      var0.setInitialSpawnLocation();
   }

   public static void f(WorldClient var0) {
      var0.removeAllEntities();
   }

   public static void b(WorldClient var0, Entity var1) {
      var0.removeEntity(var1);
   }

   public static boolean a(WorldClient var0, BlockPos var1, IBlockState var2, int var3) {
      return var0.setBlockState(var1, var2, var3);
   }

   public static long c(WorldClient var0) {
      return var0.getWorldTime();
   }

   public static int o(WorldClient var0) {
      return var0.getMoonPhase();
   }

   public static float b(WorldClient var0, float var1) {
      return var0.getRainStrength(var1);
   }

   public static Vec3 a(WorldClient var0, Entity var1, float var2) {
      return var0.getSkyColor(var1, var2);
   }

   public static float e(WorldClient var0, float var1) {
      return var0.getCelestialAngle(var1);
   }

   public static Scoreboard s(WorldClient var0) {
      return var0.getScoreboard();
   }

   public static WorldInfo i(WorldClient var0) {
      return var0.getWorldInfo();
   }

   public static Entity c(WorldClient var0, int var1) {
      return var0.removeEntityFromWorld(var1);
   }

   public static MovingObjectPosition a(WorldClient var0, Vec3 var1, Vec3 var2, boolean var3, boolean var4, boolean var5) {
      return var0.rayTraceBlocks(var1, var2, var3, var4, var5);
   }

   public static List a(WorldClient var0, Entity var1, AxisAlignedBB var2) {
      return var0.getCollidingBoundingBoxes(var1, var2);
   }

   public static List j(WorldClient var0) {
      return var0.getPlayerEntities();
   }

   public static void b(WorldClient var0, int var1, BlockPos var2, int var3) {
      var0.sendBlockBreakProgress(var1, var2, var3);
   }

   public static float h(WorldClient var0, BlockPos var1) {
      return var0.getLightBrightness(var1);
   }

   public static List a(WorldClient var0, Entity var1, AxisAlignedBB var2, Predicate var3) {
      return var0.getEntitiesInAABBexcluding(var1, var2, var3);
   }

   public static float f(WorldClient var0, float var1) {
      return var0.getSunBrightness(var1);
   }

   public static BlockPos b(WorldClient var0, BlockPos var1) {
      return var0.getPrecipitationHeight(var1);
   }

   public static BiomeGenBase e(WorldClient var0, BlockPos var1) {
      return var0.getBiomeGenForCoords(var1);
   }

   public static void a(WorldClient var0, EnumParticleTypes var1, double var2, double var4, double var6, double var8, double var10, double var12, int[] var14) {
      String var15 = b();
      var0.spawnParticle(var1, var2, var4, var6, var8, var10, var12, var14);
      if(acE.b() == null) {
         b("TB35kc");
      }

   }

   public static void a(WorldClient var0, double var1, double var3, double var5, String var7, float var8, float var9, boolean var10) {
      var0.playSound(var1, var3, var5, var7, var8, var9, var10);
   }

   public static WorldChunkManager r(WorldClient var0) {
      return var0.getWorldChunkManager();
   }

   public static int a(WorldClient var0, BlockPos var1, int var2) {
      return var0.getCombinedLight(var1, var2);
   }

   public static Vec3 a(WorldClient var0, float var1) {
      return var0.getFogColor(var1);
   }

   public static float g(WorldClient var0, float var1) {
      return var0.getCelestialAngleRadians(var1);
   }

   public static float j(WorldClient var0, float var1) {
      return var0.getThunderStrength(var1);
   }

   public static Vec3 i(WorldClient var0, float var1) {
      return var0.getCloudColour(var1);
   }

   public static Block b(WorldClient var0, int var1, int var2, int var3) {
      return var0.getBlock(var1, var2, var3);
   }

   public static void c(WorldClient var0, float var1) {
      var0.setRainStrength(var1);
   }

   public static void d(WorldClient var0, float var1) {
      var0.setThunderStrength(var1);
   }

   public static void b(WorldClient var0, long var1) {
      var0.setWorldTime(var1);
   }

   public static IBlockState a(WorldClient var0, double var1, double var3, double var5) {
      return var0.a(var1, var3, var5);
   }

   public static boolean c(WorldClient var0, Entity var1) {
      return var0.addWeatherEffect(var1);
   }

   public static void a(WorldClient var0, BlockPos var1, IBlockState var2) {
      var0.a(var1, var2);
   }

   public static void a(WorldClient var0, int var1, int var2, boolean var3) {
      var0.doPreChunk(var1, var2, var3);
   }

   public static void a(WorldClient var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      var0.invalidateBlockReceiveRegion(var1, var2, var3, var4, var5, var6);
   }

   public static Chunk a(WorldClient var0, int var1, int var2) {
      return var0.getChunkFromChunkCoords(var1, var2);
   }

   public static void b(WorldClient var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      var0.markBlockRangeForRenderUpdate(var1, var2, var3, var4, var5, var6);
   }

   public static void a(WorldClient var0, Entity var1, String var2, float var3, float var4) {
      var0.playSoundAtEntity(var1, var2, var3, var4);
   }

   public static void a(WorldClient var0, long var1) {
      var0.setTotalWorldTime(var1);
   }

   public static void a(WorldClient var0, Scoreboard var1) {
      var0.setWorldScoreboard(var1);
   }

   public static boolean f(WorldClient var0, BlockPos var1) {
      return var0.isBlockLoaded(var1);
   }

   public static void a(WorldClient var0, BlockPos var1, Block var2, int var3, int var4) {
      var0.addBlockEvent(var1, var2, var3, var4);
   }

   public static void a(WorldClient var0, int var1, BlockPos var2, int var3) {
      var0.playBroadcastSound(var1, var2, var3);
   }

   public static void c(WorldClient var0, int var1, BlockPos var2, int var3) {
      var0.playAuxSFX(var1, var2, var3);
   }

   public static WorldBorder q(WorldClient var0) {
      return var0.getWorldBorder();
   }

   public static void a(WorldClient var0, EnumParticleTypes var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13, int[] var15) {
      String var16 = b();
      var0.spawnParticle(var1, var2, var3, var5, var7, var9, var11, var13, var15);
   }

   public static EntityPlayer a(WorldClient var0, UUID var1) {
      return var0.getPlayerEntityByUUID(var1);
   }

   public static void a(WorldClient var0, IWorldAccess var1) {
      var0.removeWorldAccess(var1);
   }

   public static void b(WorldClient var0, IWorldAccess var1) {
      var0.addWorldAccess(var1);
   }

   public static List a(WorldClient var0) {
      return var0.getWeatherEffects();
   }

   public static Chunk g(WorldClient var0, BlockPos var1) {
      return var0.getChunkFromBlockCoords(var1);
   }

   public static float h(WorldClient var0, float var1) {
      return var0.getStarBrightness(var1);
   }

   public static double t(WorldClient var0) {
      return var0.getHorizon();
   }

   public static void a(WorldClient var0, BlockPos var1, String var2, float var3, float var4, boolean var5) {
      var0.playSoundAtPos(var1, var2, var3, var4, var5);
   }

   public static void a(WorldClient var0, BlockPos var1, String var2) {
      var0.playRecord(var1, var2);
   }

   public static boolean a(WorldClient var0, EntityPlayer var1, BlockPos var2, EnumFacing var3) {
      return var0.extinguishFire(var1, var2, var3);
   }

   public static String n(WorldClient var0) {
      return var0.getDebugLoadedEntities();
   }

   public static String b(WorldClient var0) {
      return var0.getProviderName();
   }

   public static DifficultyInstance c(WorldClient var0, BlockPos var1) {
      return var0.getDifficultyForLocation(var1);
   }

   public static WorldType d(WorldClient var0) {
      return var0.getWorldType();
   }

   public static long h(WorldClient var0) {
      return var0.getTotalWorldTime();
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() != null) {
         b("uJiHec");
      }

   }
}
