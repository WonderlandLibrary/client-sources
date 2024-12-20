package net.minecraft.world.biome;

import java.util.List;
import net.minecraft.block.BlockSand;
import net.minecraft.init.Blocks;

public class BiomeGenBeach
  extends BiomeGenBase
{
  private static final String __OBFID = "CL_00000157";
  
  public BiomeGenBeach(int p_i1969_1_)
  {
    super(p_i1969_1_);
    this.spawnableCreatureList.clear();
    this.topBlock = Blocks.sand.getDefaultState();
    this.fillerBlock = Blocks.sand.getDefaultState();
    this.theBiomeDecorator.treesPerChunk = 64537;
    this.theBiomeDecorator.deadBushPerChunk = 0;
    this.theBiomeDecorator.reedsPerChunk = 0;
    this.theBiomeDecorator.cactiPerChunk = 0;
  }
}
