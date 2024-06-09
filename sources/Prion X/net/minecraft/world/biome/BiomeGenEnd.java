package net.minecraft.world.biome;

import java.util.List;
import net.minecraft.block.Block;

public class BiomeGenEnd extends BiomeGenBase
{
  private static final String __OBFID = "CL_00000187";
  
  public BiomeGenEnd(int p_i1990_1_)
  {
    super(p_i1990_1_);
    spawnableMonsterList.clear();
    spawnableCreatureList.clear();
    spawnableWaterCreatureList.clear();
    spawnableCaveCreatureList.clear();
    spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(net.minecraft.entity.monster.EntityEnderman.class, 10, 4, 4));
    topBlock = net.minecraft.init.Blocks.dirt.getDefaultState();
    fillerBlock = net.minecraft.init.Blocks.dirt.getDefaultState();
    theBiomeDecorator = new BiomeEndDecorator();
  }
  



  public int getSkyColorByTemp(float p_76731_1_)
  {
    return 0;
  }
}
