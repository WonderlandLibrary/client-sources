package net.minecraft.world.biome;

import com.google.common.collect.Lists;
import java.util.Random;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenMutated extends BiomeGenBase
{
  protected BiomeGenBase baseBiome;
  private static final String __OBFID = "CL_00000178";
  
  public BiomeGenMutated(int p_i45381_1_, BiomeGenBase p_i45381_2_)
  {
    super(p_i45381_1_);
    baseBiome = p_i45381_2_;
    func_150557_a(color, true);
    biomeName = (biomeName + " M");
    topBlock = topBlock;
    fillerBlock = fillerBlock;
    fillerBlockMetadata = fillerBlockMetadata;
    minHeight = minHeight;
    maxHeight = maxHeight;
    temperature = temperature;
    rainfall = rainfall;
    waterColorMultiplier = waterColorMultiplier;
    enableSnow = enableSnow;
    enableRain = enableRain;
    spawnableCreatureList = Lists.newArrayList(spawnableCreatureList);
    spawnableMonsterList = Lists.newArrayList(spawnableMonsterList);
    spawnableCaveCreatureList = Lists.newArrayList(spawnableCaveCreatureList);
    spawnableWaterCreatureList = Lists.newArrayList(spawnableWaterCreatureList);
    temperature = temperature;
    rainfall = rainfall;
    minHeight = (minHeight + 0.1F);
    maxHeight = (maxHeight + 0.2F);
  }
  
  public void func_180624_a(World worldIn, Random p_180624_2_, BlockPos p_180624_3_)
  {
    baseBiome.theBiomeDecorator.func_180292_a(worldIn, p_180624_2_, this, p_180624_3_);
  }
  
  public void genTerrainBlocks(World worldIn, Random p_180622_2_, ChunkPrimer p_180622_3_, int p_180622_4_, int p_180622_5_, double p_180622_6_)
  {
    baseBiome.genTerrainBlocks(worldIn, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
  }
  



  public float getSpawningChance()
  {
    return baseBiome.getSpawningChance();
  }
  
  public WorldGenAbstractTree genBigTreeChance(Random p_150567_1_)
  {
    return baseBiome.genBigTreeChance(p_150567_1_);
  }
  
  public int func_180625_c(BlockPos p_180625_1_)
  {
    return baseBiome.func_180625_c(p_180625_1_);
  }
  
  public int func_180627_b(BlockPos p_180627_1_)
  {
    return baseBiome.func_180627_b(p_180627_1_);
  }
  
  public Class getBiomeClass()
  {
    return baseBiome.getBiomeClass();
  }
  



  public boolean isEqualTo(BiomeGenBase p_150569_1_)
  {
    return baseBiome.isEqualTo(p_150569_1_);
  }
  
  public BiomeGenBase.TempCategory getTempCategory()
  {
    return baseBiome.getTempCategory();
  }
}
