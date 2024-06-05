package net;

import com.google.common.base.Predicate;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.acE;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.Chunk$EnumCreateEntityType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class zj {
   private static acE[] b;

   public static boolean g(Chunk var0) {
      return var0.isEmpty();
   }

   public static int a(Chunk var0, int var1, int var2) {
      return var0.getHeightValue(var1, var2);
   }

   public static IBlockState c(Chunk var0, BlockPos var1) {
      return var0.getBlockState(var1);
   }

   public static Block a(Chunk var0, int var1, int var2, int var3) {
      return var0.getBlock(var1, var2, var3);
   }

   public static void p(Chunk var0) {
      var0.onChunkLoad();
   }

   public static void a(Chunk var0, IChunkProvider var1, IChunkProvider var2, int var3, int var4) {
      var0.populateChunk(var1, var2, var3, var4);
   }

   public static void a(Chunk var0, long var1) {
      var0.setLastSaveTime(var1);
   }

   public static boolean u(Chunk var0) {
      return var0.isTerrainPopulated();
   }

   public static void a(Chunk var0) {
      var0.func_150809_p();
   }

   public static void q(Chunk var0) {
      var0.setChunkModified();
   }

   public static boolean g(Chunk var0, boolean var1) {
      return var0.needsSaving(var1);
   }

   public static void a(Chunk var0, boolean var1) {
      var0.setModified(var1);
   }

   public static void r(Chunk var0) {
      var0.onChunkUnload();
   }

   public static boolean b(Chunk var0, BlockPos var1) {
      return var0.canSeeSky(var1);
   }

   public static boolean s(Chunk var0) {
      return var0.isPopulated();
   }

   public static long b(Chunk var0) {
      return var0.getInhabitedTime();
   }

   public static void b(Chunk var0, long var1) {
      var0.setInhabitedTime(var1);
   }

   public static TileEntity a(Chunk var0, BlockPos var1, Chunk$EnumCreateEntityType var2) {
      return var0.getTileEntity(var1, var2);
   }

   public static boolean m(Chunk var0) {
      return var0.isLoaded();
   }

   public static World n(Chunk var0) {
      return var0.getWorld();
   }

   public static ExtendedBlockStorage[] v(Chunk var0) {
      return var0.getBlockStorageArray();
   }

   public static byte[] d(Chunk var0) {
      return var0.getBiomeArray();
   }

   public static void i(Chunk var0) {
      var0.generateSkylightMap();
   }

   public static void f(Chunk var0, boolean var1) {
      var0.func_150804_b(var1);
   }

   public static ChunkCoordIntPair l(Chunk var0) {
      return var0.getChunkCoordIntPair();
   }

   public static void c(Chunk var0, boolean var1) {
      var0.setHasEntities(var1);
   }

   public static int d(Chunk var0, BlockPos var1) {
      return var0.getHeight(var1);
   }

   public static int h(Chunk var0) {
      return var0.getTopFilledSegment();
   }

   public static void a(Chunk var0, byte[] var1, int var2, boolean var3) {
      var0.fillChunk(var1, var2, var3);
   }

   public static void o(Chunk var0) {
      var0.resetRelightChecks();
   }

   public static boolean c(Chunk var0, int var1, int var2) {
      return var0.getAreLevelsEmpty(var1, var2);
   }

   public static ClassInheritanceMultiMap[] t(Chunk var0) {
      return var0.getEntityLists();
   }

   public static Block a(Chunk var0, BlockPos var1) {
      return var0.getBlock(var1);
   }

   public static Random c(Chunk var0, long var1) {
      return var0.getRandomWithSeed(var1);
   }

   public static boolean b(Chunk var0, int var1, int var2) {
      return var0.isAtLocation(var1, var2);
   }

   public static int[] k(Chunk var0) {
      return var0.getHeightMap();
   }

   public static boolean j(Chunk var0) {
      return var0.isLightPopulated();
   }

   public static Map f(Chunk var0) {
      return var0.getTileEntityMap();
   }

   public static void a(Chunk var0, int[] var1) {
      var0.setHeightMap(var1);
   }

   public static void b(Chunk var0, boolean var1) {
      var0.setTerrainPopulated(var1);
   }

   public static void d(Chunk var0, boolean var1) {
      var0.setLightPopulated(var1);
   }

   public static void a(Chunk var0, ExtendedBlockStorage[] var1) {
      var0.setStorageArrays(var1);
   }

   public static void a(Chunk var0, byte[] var1) {
      var0.setBiomeArray(var1);
   }

   public static void a(Chunk var0, Entity var1) {
      var0.addEntity(var1);
   }

   public static void a(Chunk var0, TileEntity var1) {
      var0.addTileEntity(var1);
   }

   public static BiomeGenBase a(Chunk var0, BlockPos var1, WorldChunkManager var2) {
      return var0.getBiome(var1, var2);
   }

   public static IBlockState a(Chunk var0, BlockPos var1, IBlockState var2) {
      return var0.setBlockState(var1, var2);
   }

   public static int a(Chunk var0, BlockPos var1, int var2) {
      return var0.getLightSubtracted(var1, var2);
   }

   public static int c(Chunk var0) {
      return var0.getLowestHeight();
   }

   public static void a(Chunk var0, BlockPos var1, TileEntity var2) {
      var0.addTileEntity(var1, var2);
   }

   public static void a(Chunk var0, Entity var1, int var2) {
      var0.removeEntityAtIndex(var1, var2);
   }

   public static void e(Chunk var0) {
      var0.enqueueRelightChecks();
   }

   public static void a(Chunk var0, Entity var1, AxisAlignedBB var2, List var3, Predicate var4) {
      var0.getEntitiesWithinAABBForEntity(var1, var2, var3, var4);
   }

   public static void a(Chunk var0, Class var1, AxisAlignedBB var2, List var3, Predicate var4) {
      var0.getEntitiesOfTypeWithinAAAB(var1, var2, var3, var4);
   }

   public static Block b(Chunk var0, int var1, int var2, int var3) {
      return var0.getBlock0(var1, var2, var3);
   }

   public static void e(Chunk var0, boolean var1) {
      var0.setChunkLoaded(var1);
   }

   public static int a(Chunk var0, EnumSkyBlock var1, BlockPos var2) {
      return var0.getLightFor(var1, var2);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[2]);
      }

   }
}
