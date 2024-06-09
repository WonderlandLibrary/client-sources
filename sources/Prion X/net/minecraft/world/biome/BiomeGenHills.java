package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenHills extends BiomeGenBase
{
  private WorldGenerator theWorldGenerator;
  private WorldGenTaiga2 field_150634_aD;
  private int field_150635_aE;
  private int field_150636_aF;
  private int field_150637_aG;
  private int field_150638_aH;
  private static final String __OBFID = "CL_00000168";
  
  protected BiomeGenHills(int p_i45373_1_, boolean p_i45373_2_)
  {
    super(p_i45373_1_);
    theWorldGenerator = new net.minecraft.world.gen.feature.WorldGenMinable(Blocks.monster_egg.getDefaultState().withProperty(net.minecraft.block.BlockSilverfish.VARIANT_PROP, net.minecraft.block.BlockSilverfish.EnumType.STONE), 9);
    field_150634_aD = new WorldGenTaiga2(false);
    field_150635_aE = 0;
    field_150636_aF = 1;
    field_150637_aG = 2;
    field_150638_aH = field_150635_aE;
    
    if (p_i45373_2_)
    {
      theBiomeDecorator.treesPerChunk = 3;
      field_150638_aH = field_150636_aF;
    }
  }
  
  public WorldGenAbstractTree genBigTreeChance(Random p_150567_1_)
  {
    return p_150567_1_.nextInt(3) > 0 ? field_150634_aD : super.genBigTreeChance(p_150567_1_);
  }
  
  public void func_180624_a(World worldIn, Random p_180624_2_, BlockPos p_180624_3_)
  {
    super.func_180624_a(worldIn, p_180624_2_, p_180624_3_);
    int var4 = 3 + p_180624_2_.nextInt(6);
    



    for (int var5 = 0; var5 < var4; var5++)
    {
      int var6 = p_180624_2_.nextInt(16);
      int var7 = p_180624_2_.nextInt(28) + 4;
      int var8 = p_180624_2_.nextInt(16);
      BlockPos var9 = p_180624_3_.add(var6, var7, var8);
      
      if (worldIn.getBlockState(var9).getBlock() == Blocks.stone)
      {
        worldIn.setBlockState(var9, Blocks.emerald_ore.getDefaultState(), 2);
      }
    }
    
    for (var4 = 0; var4 < 7; var4++)
    {
      var5 = p_180624_2_.nextInt(16);
      int var6 = p_180624_2_.nextInt(64);
      int var7 = p_180624_2_.nextInt(16);
      theWorldGenerator.generate(worldIn, p_180624_2_, p_180624_3_.add(var5, var6, var7));
    }
  }
  
  public void genTerrainBlocks(World worldIn, Random p_180622_2_, ChunkPrimer p_180622_3_, int p_180622_4_, int p_180622_5_, double p_180622_6_)
  {
    topBlock = Blocks.grass.getDefaultState();
    fillerBlock = Blocks.dirt.getDefaultState();
    
    if (((p_180622_6_ < -1.0D) || (p_180622_6_ > 2.0D)) && (field_150638_aH == field_150637_aG))
    {
      topBlock = Blocks.gravel.getDefaultState();
      fillerBlock = Blocks.gravel.getDefaultState();
    }
    else if ((p_180622_6_ > 1.0D) && (field_150638_aH != field_150636_aF))
    {
      topBlock = Blocks.stone.getDefaultState();
      fillerBlock = Blocks.stone.getDefaultState();
    }
    
    func_180628_b(worldIn, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
  }
  



  private BiomeGenHills mutateHills(BiomeGenBase p_150633_1_)
  {
    field_150638_aH = field_150637_aG;
    func_150557_a(color, true);
    setBiomeName(biomeName + " M");
    setHeight(new BiomeGenBase.Height(minHeight, maxHeight));
    setTemperatureRainfall(temperature, rainfall);
    return this;
  }
  
  protected BiomeGenBase createMutatedBiome(int p_180277_1_)
  {
    return new BiomeGenHills(p_180277_1_, false).mutateHills(this);
  }
}
