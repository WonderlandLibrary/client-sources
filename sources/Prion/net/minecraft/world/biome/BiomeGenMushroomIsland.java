package net.minecraft.world.biome;

import java.util.List;
import net.minecraft.block.BlockMycelium;

public class BiomeGenMushroomIsland extends BiomeGenBase
{
  private static final String __OBFID = "CL_00000177";
  
  public BiomeGenMushroomIsland(int p_i1984_1_)
  {
    super(p_i1984_1_);
    theBiomeDecorator.treesPerChunk = -100;
    theBiomeDecorator.flowersPerChunk = -100;
    theBiomeDecorator.grassPerChunk = -100;
    theBiomeDecorator.mushroomsPerChunk = 1;
    theBiomeDecorator.bigMushroomsPerChunk = 1;
    topBlock = net.minecraft.init.Blocks.mycelium.getDefaultState();
    spawnableMonsterList.clear();
    spawnableCreatureList.clear();
    spawnableWaterCreatureList.clear();
    spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(net.minecraft.entity.passive.EntityMooshroom.class, 8, 4, 8));
  }
}
