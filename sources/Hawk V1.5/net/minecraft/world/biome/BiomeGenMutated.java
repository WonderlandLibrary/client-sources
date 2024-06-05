package net.minecraft.world.biome;

import com.google.common.collect.Lists;
import java.util.Random;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenMutated extends BiomeGenBase {
   protected BiomeGenBase baseBiome;
   private static final String __OBFID = "CL_00000178";

   public void func_180624_a(World var1, Random var2, BlockPos var3) {
      this.baseBiome.theBiomeDecorator.func_180292_a(var1, var2, this, var3);
   }

   public BiomeGenBase.TempCategory getTempCategory() {
      return this.baseBiome.getTempCategory();
   }

   public Class getBiomeClass() {
      return this.baseBiome.getBiomeClass();
   }

   public boolean isEqualTo(BiomeGenBase var1) {
      return this.baseBiome.isEqualTo(var1);
   }

   public int func_180625_c(BlockPos var1) {
      return this.baseBiome.func_180625_c(var1);
   }

   public BiomeGenMutated(int var1, BiomeGenBase var2) {
      super(var1);
      this.baseBiome = var2;
      this.func_150557_a(var2.color, true);
      this.biomeName = String.valueOf((new StringBuilder(String.valueOf(var2.biomeName))).append(" M"));
      this.topBlock = var2.topBlock;
      this.fillerBlock = var2.fillerBlock;
      this.fillerBlockMetadata = var2.fillerBlockMetadata;
      this.minHeight = var2.minHeight;
      this.maxHeight = var2.maxHeight;
      this.temperature = var2.temperature;
      this.rainfall = var2.rainfall;
      this.waterColorMultiplier = var2.waterColorMultiplier;
      this.enableSnow = var2.enableSnow;
      this.enableRain = var2.enableRain;
      this.spawnableCreatureList = Lists.newArrayList(var2.spawnableCreatureList);
      this.spawnableMonsterList = Lists.newArrayList(var2.spawnableMonsterList);
      this.spawnableCaveCreatureList = Lists.newArrayList(var2.spawnableCaveCreatureList);
      this.spawnableWaterCreatureList = Lists.newArrayList(var2.spawnableWaterCreatureList);
      this.temperature = var2.temperature;
      this.rainfall = var2.rainfall;
      this.minHeight = var2.minHeight + 0.1F;
      this.maxHeight = var2.maxHeight + 0.2F;
   }

   public int func_180627_b(BlockPos var1) {
      return this.baseBiome.func_180627_b(var1);
   }

   public void genTerrainBlocks(World var1, Random var2, ChunkPrimer var3, int var4, int var5, double var6) {
      this.baseBiome.genTerrainBlocks(var1, var2, var3, var4, var5, var6);
   }

   public float getSpawningChance() {
      return this.baseBiome.getSpawningChance();
   }

   public WorldGenAbstractTree genBigTreeChance(Random var1) {
      return this.baseBiome.genBigTreeChance(var1);
   }
}
