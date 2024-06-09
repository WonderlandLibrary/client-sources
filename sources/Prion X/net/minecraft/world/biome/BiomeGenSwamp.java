package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower.EnumFlowerType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class BiomeGenSwamp extends BiomeGenBase
{
  private static final String __OBFID = "CL_00000185";
  
  protected BiomeGenSwamp(int p_i1988_1_)
  {
    super(p_i1988_1_);
    theBiomeDecorator.treesPerChunk = 2;
    theBiomeDecorator.flowersPerChunk = 1;
    theBiomeDecorator.deadBushPerChunk = 1;
    theBiomeDecorator.mushroomsPerChunk = 8;
    theBiomeDecorator.reedsPerChunk = 10;
    theBiomeDecorator.clayPerChunk = 1;
    theBiomeDecorator.waterlilyPerChunk = 4;
    theBiomeDecorator.sandPerChunk2 = 0;
    theBiomeDecorator.sandPerChunk = 0;
    theBiomeDecorator.grassPerChunk = 5;
    waterColorMultiplier = 14745518;
    spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(net.minecraft.entity.monster.EntitySlime.class, 1, 1, 1));
  }
  
  public net.minecraft.world.gen.feature.WorldGenAbstractTree genBigTreeChance(Random p_150567_1_)
  {
    return worldGeneratorSwamp;
  }
  
  public int func_180627_b(BlockPos p_180627_1_)
  {
    double var2 = field_180281_af.func_151601_a(p_180627_1_.getX() * 0.0225D, p_180627_1_.getZ() * 0.0225D);
    return var2 < -0.1D ? 5011004 : 6975545;
  }
  
  public int func_180625_c(BlockPos p_180625_1_)
  {
    return 6975545;
  }
  
  public BlockFlower.EnumFlowerType pickRandomFlower(Random p_180623_1_, BlockPos p_180623_2_)
  {
    return BlockFlower.EnumFlowerType.BLUE_ORCHID;
  }
  
  public void genTerrainBlocks(World worldIn, Random p_180622_2_, ChunkPrimer p_180622_3_, int p_180622_4_, int p_180622_5_, double p_180622_6_)
  {
    double var8 = field_180281_af.func_151601_a(p_180622_4_ * 0.25D, p_180622_5_ * 0.25D);
    
    if (var8 > 0.0D)
    {
      int var10 = p_180622_4_ & 0xF;
      int var11 = p_180622_5_ & 0xF;
      
      for (int var12 = 255; var12 >= 0; var12--)
      {
        if (p_180622_3_.getBlockState(var11, var12, var10).getBlock().getMaterial() != net.minecraft.block.material.Material.air)
        {
          if ((var12 != 62) || (p_180622_3_.getBlockState(var11, var12, var10).getBlock() == Blocks.water))
            break;
          p_180622_3_.setBlockState(var11, var12, var10, Blocks.water.getDefaultState());
          
          if (var8 >= 0.12D)
            break;
          p_180622_3_.setBlockState(var11, var12 + 1, var10, Blocks.waterlily.getDefaultState());
          


          break;
        }
      }
    }
    
    func_180628_b(worldIn, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
  }
}
