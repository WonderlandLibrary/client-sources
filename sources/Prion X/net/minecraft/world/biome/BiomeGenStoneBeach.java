package net.minecraft.world.biome;

import net.minecraft.block.Block;

public class BiomeGenStoneBeach extends BiomeGenBase
{
  private static final String __OBFID = "CL_00000184";
  
  public BiomeGenStoneBeach(int p_i45384_1_)
  {
    super(p_i45384_1_);
    spawnableCreatureList.clear();
    topBlock = net.minecraft.init.Blocks.stone.getDefaultState();
    fillerBlock = net.minecraft.init.Blocks.stone.getDefaultState();
    theBiomeDecorator.treesPerChunk = 64537;
    theBiomeDecorator.deadBushPerChunk = 0;
    theBiomeDecorator.reedsPerChunk = 0;
    theBiomeDecorator.cactiPerChunk = 0;
  }
}
