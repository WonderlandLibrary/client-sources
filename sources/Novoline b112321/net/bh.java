package net;

import java.util.List;
import java.util.Random;
import net.acE;
import net.minecraft.block.BlockFlower$EnumFlowerType;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase$TempCategory;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;

public class bh {
   private static int[] b;

   public static BiomeGenBase a(int var0, BiomeGenBase var1) {
      return BiomeGenBase.getBiomeFromBiomeList(var0, var1);
   }

   public static int c(BiomeGenBase var0, BlockPos var1) {
      return var0.getGrassColorAtPos(var1);
   }

   public static int b(BiomeGenBase var0, BlockPos var1) {
      return var0.getFoliageColorAtPos(var1);
   }

   public static BiomeGenBase a(int var0) {
      return BiomeGenBase.getBiome(var0);
   }

   public static Class i(BiomeGenBase var0) {
      return var0.getBiomeClass();
   }

   public static boolean g(BiomeGenBase var0) {
      return var0.isSnowyBiome();
   }

   public static List a(BiomeGenBase var0, EnumCreatureType var1) {
      return var0.getSpawnableList(var1);
   }

   public static boolean d(BiomeGenBase var0) {
      return var0.canSpawnLightningBolt();
   }

   public static float a(BiomeGenBase var0, BlockPos var1) {
      return var0.getFloatTemperature(var1);
   }

   public static boolean b(BiomeGenBase var0) {
      return var0.getEnableSnow();
   }

   public static BiomeGenBase[] c() {
      return BiomeGenBase.getBiomeGenArray();
   }

   public static void a(BiomeGenBase var0, World var1, Random var2, BlockPos var3) {
      var0.decorate(var1, var2, var3);
   }

   public static float h(BiomeGenBase var0) {
      return var0.getFloatRainfall();
   }

   public static WorldGenAbstractTree b(BiomeGenBase var0, Random var1) {
      return var0.genBigTreeChance(var1);
   }

   public static BlockFlower$EnumFlowerType a(BiomeGenBase var0, Random var1, BlockPos var2) {
      return var0.pickRandomFlower(var1, var2);
   }

   public static WorldGenerator a(BiomeGenBase var0, Random var1) {
      return var0.getRandomWorldGenForGrass(var1);
   }

   public static float f(BiomeGenBase var0) {
      return var0.getSpawningChance();
   }

   public static void a(BiomeGenBase var0, World var1, Random var2, ChunkPrimer var3, int var4, int var5, double var6) {
      int[] var8 = b();
      var0.genTerrainBlocks(var1, var2, var3, var4, var5, var6);
      if(acE.b() == null) {
         b(new int[2]);
      }

   }

   public static boolean a(BiomeGenBase var0, BiomeGenBase var1) {
      return var0.isEqualTo(var1);
   }

   public static int a(BiomeGenBase var0, float var1) {
      return var0.getSkyColorByTemp(var1);
   }

   public static boolean e(BiomeGenBase var0) {
      return var0.isHighHumidity();
   }

   public static BiomeGenBase$TempCategory c(BiomeGenBase var0) {
      return var0.getTempCategory();
   }

   public static int a(BiomeGenBase var0) {
      return var0.getIntRainfall();
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new int[1]);
      }

   }
}
