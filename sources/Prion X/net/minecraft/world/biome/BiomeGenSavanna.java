package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant.EnumPlantType;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;

public class BiomeGenSavanna extends BiomeGenBase
{
  private static final WorldGenSavannaTree field_150627_aC = new WorldGenSavannaTree(false);
  private static final String __OBFID = "CL_00000182";
  
  protected BiomeGenSavanna(int p_i45383_1_)
  {
    super(p_i45383_1_);
    spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityHorse.class, 1, 2, 6));
    theBiomeDecorator.treesPerChunk = 1;
    theBiomeDecorator.flowersPerChunk = 4;
    theBiomeDecorator.grassPerChunk = 20;
  }
  
  public net.minecraft.world.gen.feature.WorldGenAbstractTree genBigTreeChance(Random p_150567_1_)
  {
    return p_150567_1_.nextInt(5) > 0 ? field_150627_aC : worldGeneratorTrees;
  }
  
  protected BiomeGenBase createMutatedBiome(int p_180277_1_)
  {
    Mutated var2 = new Mutated(p_180277_1_, this);
    temperature = ((temperature + 1.0F) * 0.5F);
    minHeight = (minHeight * 0.5F + 0.3F);
    maxHeight = (maxHeight * 0.5F + 1.2F);
    return var2;
  }
  
  public void func_180624_a(World worldIn, Random p_180624_2_, BlockPos p_180624_3_)
  {
    field_180280_ag.func_180710_a(BlockDoublePlant.EnumPlantType.GRASS);
    
    for (int var4 = 0; var4 < 7; var4++)
    {
      int var5 = p_180624_2_.nextInt(16) + 8;
      int var6 = p_180624_2_.nextInt(16) + 8;
      int var7 = p_180624_2_.nextInt(worldIn.getHorizon(p_180624_3_.add(var5, 0, var6)).getY() + 32);
      field_180280_ag.generate(worldIn, p_180624_2_, p_180624_3_.add(var5, var7, var6));
    }
    
    super.func_180624_a(worldIn, p_180624_2_, p_180624_3_);
  }
  
  public static class Mutated extends BiomeGenMutated
  {
    private static final String __OBFID = "CL_00000183";
    
    public Mutated(int p_i45382_1_, BiomeGenBase p_i45382_2_)
    {
      super(p_i45382_2_);
      theBiomeDecorator.treesPerChunk = 2;
      theBiomeDecorator.flowersPerChunk = 2;
      theBiomeDecorator.grassPerChunk = 5;
    }
    
    public void genTerrainBlocks(World worldIn, Random p_180622_2_, ChunkPrimer p_180622_3_, int p_180622_4_, int p_180622_5_, double p_180622_6_)
    {
      topBlock = Blocks.grass.getDefaultState();
      fillerBlock = Blocks.dirt.getDefaultState();
      
      if (p_180622_6_ > 1.75D)
      {
        topBlock = Blocks.stone.getDefaultState();
        fillerBlock = Blocks.stone.getDefaultState();
      }
      else if (p_180622_6_ > -0.5D)
      {
        topBlock = Blocks.dirt.getDefaultState().withProperty(net.minecraft.block.BlockDirt.VARIANT, net.minecraft.block.BlockDirt.DirtType.COARSE_DIRT);
      }
      
      func_180628_b(worldIn, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
    }
    
    public void func_180624_a(World worldIn, Random p_180624_2_, BlockPos p_180624_3_)
    {
      theBiomeDecorator.func_180292_a(worldIn, p_180624_2_, this, p_180624_3_);
    }
  }
}
