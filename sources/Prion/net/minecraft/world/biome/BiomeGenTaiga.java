package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDirt.DirtType;
import net.minecraft.block.BlockTallGrass.EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTallGrass;

public class BiomeGenTaiga extends BiomeGenBase
{
  private static final WorldGenTaiga1 field_150639_aC = new WorldGenTaiga1();
  private static final WorldGenTaiga2 field_150640_aD = new WorldGenTaiga2(false);
  private static final WorldGenMegaPineTree field_150641_aE = new WorldGenMegaPineTree(false, false);
  private static final WorldGenMegaPineTree field_150642_aF = new WorldGenMegaPineTree(false, true);
  private static final WorldGenBlockBlob field_150643_aG = new WorldGenBlockBlob(Blocks.mossy_cobblestone, 0);
  private int field_150644_aH;
  private static final String __OBFID = "CL_00000186";
  
  public BiomeGenTaiga(int p_i45385_1_, int p_i45385_2_)
  {
    super(p_i45385_1_);
    field_150644_aH = p_i45385_2_;
    spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(net.minecraft.entity.passive.EntityWolf.class, 8, 4, 4));
    theBiomeDecorator.treesPerChunk = 10;
    
    if ((p_i45385_2_ != 1) && (p_i45385_2_ != 2))
    {
      theBiomeDecorator.grassPerChunk = 1;
      theBiomeDecorator.mushroomsPerChunk = 1;
    }
    else
    {
      theBiomeDecorator.grassPerChunk = 7;
      theBiomeDecorator.deadBushPerChunk = 1;
      theBiomeDecorator.mushroomsPerChunk = 3;
    }
  }
  
  public net.minecraft.world.gen.feature.WorldGenAbstractTree genBigTreeChance(Random p_150567_1_)
  {
    return p_150567_1_.nextInt(3) == 0 ? field_150639_aC : ((field_150644_aH == 1) || (field_150644_aH == 2)) && (p_150567_1_.nextInt(3) == 0) ? field_150642_aF : (field_150644_aH != 2) && (p_150567_1_.nextInt(13) != 0) ? field_150641_aE : field_150640_aD;
  }
  



  public net.minecraft.world.gen.feature.WorldGenerator getRandomWorldGenForGrass(Random p_76730_1_)
  {
    return p_76730_1_.nextInt(5) > 0 ? new WorldGenTallGrass(BlockTallGrass.EnumType.FERN) : new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
  }
  





  public void func_180624_a(World worldIn, Random p_180624_2_, BlockPos p_180624_3_)
  {
    if ((field_150644_aH == 1) || (field_150644_aH == 2))
    {
      int var4 = p_180624_2_.nextInt(3);
      
      for (int var5 = 0; var5 < var4; var5++)
      {
        int var6 = p_180624_2_.nextInt(16) + 8;
        int var7 = p_180624_2_.nextInt(16) + 8;
        BlockPos var8 = worldIn.getHorizon(p_180624_3_.add(var6, 0, var7));
        field_150643_aG.generate(worldIn, p_180624_2_, var8);
      }
    }
    
    field_180280_ag.func_180710_a(net.minecraft.block.BlockDoublePlant.EnumPlantType.FERN);
    
    for (int var4 = 0; var4 < 7; var4++)
    {
      int var5 = p_180624_2_.nextInt(16) + 8;
      int var6 = p_180624_2_.nextInt(16) + 8;
      int var7 = p_180624_2_.nextInt(worldIn.getHorizon(p_180624_3_.add(var5, 0, var6)).getY() + 32);
      field_180280_ag.generate(worldIn, p_180624_2_, p_180624_3_.add(var5, var7, var6));
    }
    
    super.func_180624_a(worldIn, p_180624_2_, p_180624_3_);
  }
  
  public void genTerrainBlocks(World worldIn, Random p_180622_2_, ChunkPrimer p_180622_3_, int p_180622_4_, int p_180622_5_, double p_180622_6_)
  {
    if ((field_150644_aH == 1) || (field_150644_aH == 2))
    {
      topBlock = Blocks.grass.getDefaultState();
      fillerBlock = Blocks.dirt.getDefaultState();
      
      if (p_180622_6_ > 1.75D)
      {
        topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
      }
      else if (p_180622_6_ > -0.95D)
      {
        topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);
      }
    }
    
    func_180628_b(worldIn, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
  }
  
  protected BiomeGenBase createMutatedBiome(int p_180277_1_)
  {
    return biomeID == megaTaigabiomeID ? new BiomeGenTaiga(p_180277_1_, 2).func_150557_a(5858897, true).setBiomeName("Mega Spruce Taiga").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.25F, 0.8F).setHeight(new BiomeGenBase.Height(minHeight, maxHeight)) : super.createMutatedBiome(p_180277_1_);
  }
}
