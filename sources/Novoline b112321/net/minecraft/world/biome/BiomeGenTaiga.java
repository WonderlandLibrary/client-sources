package net.minecraft.world.biome;

import java.util.Random;
import net.nA;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDirt$DirtType;
import net.minecraft.block.BlockDoublePlant$EnumPlantType;
import net.minecraft.block.BlockTallGrass$EnumType;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase$Height;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenTaiga extends BiomeGenBase {
   private static final WorldGenTaiga1 field_150639_aC = new WorldGenTaiga1();
   private static final WorldGenTaiga2 field_150640_aD = new WorldGenTaiga2(false);
   private static final WorldGenMegaPineTree field_150641_aE = new WorldGenMegaPineTree(false, false);
   private static final WorldGenMegaPineTree field_150642_aF = new WorldGenMegaPineTree(false, true);
   private static final WorldGenBlockBlob field_150643_aG = new WorldGenBlockBlob(Blocks.mossy_cobblestone, 0);
   private int field_150644_aH;

   public BiomeGenTaiga(int var1, int var2) {
      super(var1);
      this.field_150644_aH = var2;
      this.spawnableCreatureList.add(new nA(EntityWolf.class, 8, 4, 4));
      this.theBiomeDecorator.treesPerChunk = 10;
      if(var2 != 1 && var2 != 2) {
         this.theBiomeDecorator.grassPerChunk = 1;
         this.theBiomeDecorator.mushroomsPerChunk = 1;
      } else {
         this.theBiomeDecorator.grassPerChunk = 7;
         this.theBiomeDecorator.deadBushPerChunk = 1;
         this.theBiomeDecorator.mushroomsPerChunk = 3;
      }

   }

   public WorldGenAbstractTree genBigTreeChance(Random var1) {
      return (WorldGenAbstractTree)((this.field_150644_aH == 1 || this.field_150644_aH == 2) && var1.nextInt(3) == 0?(this.field_150644_aH != 2 && var1.nextInt(13) != 0?field_150641_aE:field_150642_aF):(var1.nextInt(3) == 0?field_150639_aC:field_150640_aD));
   }

   public WorldGenerator getRandomWorldGenForGrass(Random var1) {
      return var1.nextInt(5) > 0?new WorldGenTallGrass(BlockTallGrass$EnumType.FERN):new WorldGenTallGrass(BlockTallGrass$EnumType.GRASS);
   }

   public void decorate(World var1, Random var2, BlockPos var3) {
      if(this.field_150644_aH == 1 || this.field_150644_aH == 2) {
         int var4 = var2.nextInt(3);

         for(int var5 = 0; var5 < var4; ++var5) {
            int var6 = var2.nextInt(16) + 8;
            int var7 = var2.nextInt(16) + 8;
            BlockPos var8 = var1.getHeight(var3.a(var6, 0, var7));
            field_150643_aG.generate(var1, var2, var8);
         }
      }

      DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant$EnumPlantType.FERN);

      for(int var9 = 0; var9 < 7; ++var9) {
         int var10 = var2.nextInt(16) + 8;
         int var11 = var2.nextInt(16) + 8;
         int var12 = var2.nextInt(var1.getHeight(var3.a(var10, 0, var11)).getY() + 32);
         DOUBLE_PLANT_GENERATOR.generate(var1, var2, var3.a(var10, var12, var11));
      }

      super.decorate(var1, var2, var3);
   }

   public void genTerrainBlocks(World var1, Random var2, ChunkPrimer var3, int var4, int var5, double var6) {
      if(this.field_150644_aH == 1 || this.field_150644_aH == 2) {
         this.topBlock = Blocks.grass.getDefaultState();
         this.fillerBlock = Blocks.dirt.getDefaultState();
         if(var6 > 1.75D) {
            this.topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt$DirtType.COARSE_DIRT);
         } else if(var6 > -0.95D) {
            this.topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt$DirtType.PODZOL);
         }
      }

      this.generateBiomeTerrain(var1, var2, var3, var4, var5, var6);
   }

   protected BiomeGenBase createMutatedBiome(int var1) {
      return this.biomeID == BiomeGenBase.megaTaiga.biomeID?(new BiomeGenTaiga(var1, 2)).func_150557_a(5858897, true).setBiomeName("Mega Spruce Taiga").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.25F, 0.8F).setHeight(new BiomeGenBase$Height(this.minHeight, this.maxHeight)):super.createMutatedBiome(var1);
   }
}
