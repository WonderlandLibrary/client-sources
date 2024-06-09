package net.minecraft.world.biome;

import net.minecraft.block.BlockSand;

public class BiomeGenBeach extends BiomeGenBase
{
  private static final String __OBFID = "CL_00000157";
  
  public BiomeGenBeach(int p_i1969_1_)
  {
    super(p_i1969_1_);
    spawnableCreatureList.clear();
    topBlock = net.minecraft.init.Blocks.sand.getDefaultState();
    fillerBlock = net.minecraft.init.Blocks.sand.getDefaultState();
    theBiomeDecorator.treesPerChunk = 64537;
    theBiomeDecorator.deadBushPerChunk = 0;
    theBiomeDecorator.reedsPerChunk = 0;
    theBiomeDecorator.cactiPerChunk = 0;
  }
}
