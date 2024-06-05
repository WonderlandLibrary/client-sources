package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDirt$DirtType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenMutated;
import net.minecraft.world.chunk.ChunkPrimer;

public class BiomeGenSavanna$Mutated extends BiomeGenMutated {
   public BiomeGenSavanna$Mutated(int var1, BiomeGenBase var2) {
      super(var1, var2);
      this.theBiomeDecorator.treesPerChunk = 2;
      this.theBiomeDecorator.flowersPerChunk = 2;
      this.theBiomeDecorator.grassPerChunk = 5;
   }

   public void genTerrainBlocks(World var1, Random var2, ChunkPrimer var3, int var4, int var5, double var6) {
      this.topBlock = Blocks.grass.getDefaultState();
      this.fillerBlock = Blocks.dirt.getDefaultState();
      if(var6 > 1.75D) {
         this.topBlock = Blocks.stone.getDefaultState();
         this.fillerBlock = Blocks.stone.getDefaultState();
      } else if(var6 > -0.5D) {
         this.topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt$DirtType.COARSE_DIRT);
      }

      this.generateBiomeTerrain(var1, var2, var3, var4, var5, var6);
   }

   public void decorate(World var1, Random var2, BlockPos var3) {
      this.theBiomeDecorator.decorate(var1, var2, this, var3);
   }
}
