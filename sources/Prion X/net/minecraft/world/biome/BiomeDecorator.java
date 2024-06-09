package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlower.EnumFlowerType;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStone.EnumType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.ChunkProviderSettings.Factory;
import net.minecraft.world.gen.GeneratorBushFeature;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenSand;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.storage.WorldInfo;

public class BiomeDecorator
{
  protected World currentWorld;
  protected Random randomGenerator;
  protected BlockPos field_180294_c;
  protected ChunkProviderSettings field_180293_d;
  protected WorldGenerator clayGen = new WorldGenClay(4);
  

  protected WorldGenerator sandGen;
  

  protected WorldGenerator gravelAsSandGen;
  

  protected WorldGenerator dirtGen;
  

  protected WorldGenerator gravelGen;
  

  protected WorldGenerator field_180296_j;
  

  protected WorldGenerator field_180297_k;
  

  protected WorldGenerator field_180295_l;
  

  protected WorldGenerator coalGen;
  

  protected WorldGenerator ironGen;
  

  protected WorldGenerator goldGen;
  

  protected WorldGenerator field_180299_p;
  

  protected WorldGenerator field_180298_q;
  

  protected WorldGenerator lapisGen;
  

  protected WorldGenFlowers yellowFlowerGen;
  

  protected WorldGenerator mushroomBrownGen;
  

  protected WorldGenerator mushroomRedGen;
  

  protected WorldGenerator bigMushroomGen;
  

  protected WorldGenerator reedGen;
  

  protected WorldGenerator cactusGen;
  

  protected WorldGenerator waterlilyGen;
  

  protected int waterlilyPerChunk;
  

  protected int treesPerChunk;
  

  protected int flowersPerChunk;
  

  protected int grassPerChunk;
  

  protected int deadBushPerChunk;
  

  protected int mushroomsPerChunk;
  

  protected int reedsPerChunk;
  

  protected int cactiPerChunk;
  

  protected int sandPerChunk;
  

  protected int sandPerChunk2;
  

  protected int clayPerChunk;
  

  protected int bigMushroomsPerChunk;
  

  public boolean generateLakes;
  

  private static final String __OBFID = "CL_00000164";
  

  public BiomeDecorator()
  {
    sandGen = new WorldGenSand(Blocks.sand, 7);
    gravelAsSandGen = new WorldGenSand(Blocks.gravel, 6);
    yellowFlowerGen = new WorldGenFlowers(Blocks.yellow_flower, BlockFlower.EnumFlowerType.DANDELION);
    mushroomBrownGen = new GeneratorBushFeature(Blocks.brown_mushroom);
    mushroomRedGen = new GeneratorBushFeature(Blocks.red_mushroom);
    bigMushroomGen = new WorldGenBigMushroom();
    reedGen = new WorldGenReed();
    cactusGen = new WorldGenCactus();
    waterlilyGen = new net.minecraft.world.gen.feature.WorldGenWaterlily();
    flowersPerChunk = 2;
    grassPerChunk = 1;
    sandPerChunk = 1;
    sandPerChunk2 = 3;
    clayPerChunk = 1;
    generateLakes = true;
  }
  
  public void func_180292_a(World worldIn, Random p_180292_2_, BiomeGenBase p_180292_3_, BlockPos p_180292_4_)
  {
    if (currentWorld != null)
    {
      throw new RuntimeException("Already decorating");
    }
    

    currentWorld = worldIn;
    String var5 = worldIn.getWorldInfo().getGeneratorOptions();
    
    if (var5 != null)
    {
      field_180293_d = ChunkProviderSettings.Factory.func_177865_a(var5).func_177864_b();
    }
    else
    {
      field_180293_d = ChunkProviderSettings.Factory.func_177865_a("").func_177864_b();
    }
    
    randomGenerator = p_180292_2_;
    field_180294_c = p_180292_4_;
    dirtGen = new WorldGenMinable(Blocks.dirt.getDefaultState(), field_180293_d.field_177789_I);
    gravelGen = new WorldGenMinable(Blocks.gravel.getDefaultState(), field_180293_d.field_177785_M);
    field_180296_j = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.GRANITE), field_180293_d.field_177796_Q);
    field_180297_k = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.DIORITE), field_180293_d.field_177792_U);
    field_180295_l = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.ANDESITE), field_180293_d.field_177800_Y);
    coalGen = new WorldGenMinable(Blocks.coal_ore.getDefaultState(), field_180293_d.field_177844_ac);
    ironGen = new WorldGenMinable(Blocks.iron_ore.getDefaultState(), field_180293_d.field_177848_ag);
    goldGen = new WorldGenMinable(Blocks.gold_ore.getDefaultState(), field_180293_d.field_177828_ak);
    field_180299_p = new WorldGenMinable(Blocks.redstone_ore.getDefaultState(), field_180293_d.field_177836_ao);
    field_180298_q = new WorldGenMinable(Blocks.diamond_ore.getDefaultState(), field_180293_d.field_177814_as);
    lapisGen = new WorldGenMinable(Blocks.lapis_ore.getDefaultState(), field_180293_d.field_177822_aw);
    genDecorations(p_180292_3_);
    currentWorld = null;
    randomGenerator = null;
  }
  

  protected void genDecorations(BiomeGenBase p_150513_1_)
  {
    generateOres();
    



    for (int var2 = 0; var2 < sandPerChunk2; var2++)
    {
      int var3 = randomGenerator.nextInt(16) + 8;
      int var4 = randomGenerator.nextInt(16) + 8;
      sandGen.generate(currentWorld, randomGenerator, currentWorld.func_175672_r(field_180294_c.add(var3, 0, var4)));
    }
    
    for (var2 = 0; var2 < clayPerChunk; var2++)
    {
      int var3 = randomGenerator.nextInt(16) + 8;
      int var4 = randomGenerator.nextInt(16) + 8;
      clayGen.generate(currentWorld, randomGenerator, currentWorld.func_175672_r(field_180294_c.add(var3, 0, var4)));
    }
    
    for (var2 = 0; var2 < sandPerChunk; var2++)
    {
      int var3 = randomGenerator.nextInt(16) + 8;
      int var4 = randomGenerator.nextInt(16) + 8;
      gravelAsSandGen.generate(currentWorld, randomGenerator, currentWorld.func_175672_r(field_180294_c.add(var3, 0, var4)));
    }
    
    var2 = treesPerChunk;
    
    if (randomGenerator.nextInt(10) == 0)
    {
      var2++;
    }
    



    for (int var3 = 0; var3 < var2; var3++)
    {
      int var4 = randomGenerator.nextInt(16) + 8;
      int var5 = randomGenerator.nextInt(16) + 8;
      WorldGenAbstractTree var6 = p_150513_1_.genBigTreeChance(randomGenerator);
      var6.func_175904_e();
      BlockPos var7 = currentWorld.getHorizon(field_180294_c.add(var4, 0, var5));
      
      if (var6.generate(currentWorld, randomGenerator, var7))
      {
        var6.func_180711_a(currentWorld, randomGenerator, var7);
      }
    }
    
    for (var3 = 0; var3 < bigMushroomsPerChunk; var3++)
    {
      int var4 = randomGenerator.nextInt(16) + 8;
      int var5 = randomGenerator.nextInt(16) + 8;
      bigMushroomGen.generate(currentWorld, randomGenerator, currentWorld.getHorizon(field_180294_c.add(var4, 0, var5)));
    }
    


    for (var3 = 0; var3 < flowersPerChunk; var3++)
    {
      int var4 = randomGenerator.nextInt(16) + 8;
      int var5 = randomGenerator.nextInt(16) + 8;
      int var11 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var4, 0, var5)).getY() + 32);
      BlockPos var7 = field_180294_c.add(var4, var11, var5);
      BlockFlower.EnumFlowerType var8 = p_150513_1_.pickRandomFlower(randomGenerator, var7);
      BlockFlower var9 = var8.func_176964_a().func_180346_a();
      
      if (var9.getMaterial() != Material.air)
      {
        yellowFlowerGen.setGeneratedBlock(var9, var8);
        yellowFlowerGen.generate(currentWorld, randomGenerator, var7);
      }
    }
    
    for (var3 = 0; var3 < grassPerChunk; var3++)
    {
      int var4 = randomGenerator.nextInt(16) + 8;
      int var5 = randomGenerator.nextInt(16) + 8;
      int var11 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var4, 0, var5)).getY() * 2);
      p_150513_1_.getRandomWorldGenForGrass(randomGenerator).generate(currentWorld, randomGenerator, field_180294_c.add(var4, var11, var5));
    }
    
    for (var3 = 0; var3 < deadBushPerChunk; var3++)
    {
      int var4 = randomGenerator.nextInt(16) + 8;
      int var5 = randomGenerator.nextInt(16) + 8;
      int var11 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var4, 0, var5)).getY() * 2);
      new WorldGenDeadBush().generate(currentWorld, randomGenerator, field_180294_c.add(var4, var11, var5));
    }
    
    var3 = 0;
    
    while (var3 < waterlilyPerChunk)
    {
      int var4 = randomGenerator.nextInt(16) + 8;
      int var5 = randomGenerator.nextInt(16) + 8;
      int var11 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var4, 0, var5)).getY() * 2);
      BlockPos var7 = field_180294_c.add(var4, var11, var5);
      


      while (var7.getY() > 0)
      {
        BlockPos var13 = var7.offsetDown();
        
        if (!currentWorld.isAirBlock(var13))
          break;
        var7 = var13;
      }
      


      waterlilyGen.generate(currentWorld, randomGenerator, var7);
      var3++;
    }
    


    for (var3 = 0; var3 < mushroomsPerChunk; var3++)
    {
      if (randomGenerator.nextInt(4) == 0)
      {
        int var4 = randomGenerator.nextInt(16) + 8;
        int var5 = randomGenerator.nextInt(16) + 8;
        BlockPos var12 = currentWorld.getHorizon(field_180294_c.add(var4, 0, var5));
        mushroomBrownGen.generate(currentWorld, randomGenerator, var12);
      }
      
      if (randomGenerator.nextInt(8) == 0)
      {
        int var4 = randomGenerator.nextInt(16) + 8;
        int var5 = randomGenerator.nextInt(16) + 8;
        int var11 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var4, 0, var5)).getY() * 2);
        BlockPos var7 = field_180294_c.add(var4, var11, var5);
        mushroomRedGen.generate(currentWorld, randomGenerator, var7);
      }
    }
    
    if (randomGenerator.nextInt(4) == 0)
    {
      var3 = randomGenerator.nextInt(16) + 8;
      int var4 = randomGenerator.nextInt(16) + 8;
      int var5 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var3, 0, var4)).getY() * 2);
      mushroomBrownGen.generate(currentWorld, randomGenerator, field_180294_c.add(var3, var5, var4));
    }
    
    if (randomGenerator.nextInt(8) == 0)
    {
      var3 = randomGenerator.nextInt(16) + 8;
      int var4 = randomGenerator.nextInt(16) + 8;
      int var5 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var3, 0, var4)).getY() * 2);
      mushroomRedGen.generate(currentWorld, randomGenerator, field_180294_c.add(var3, var5, var4));
    }
    
    for (var3 = 0; var3 < reedsPerChunk; var3++)
    {
      int var4 = randomGenerator.nextInt(16) + 8;
      int var5 = randomGenerator.nextInt(16) + 8;
      int var11 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var4, 0, var5)).getY() * 2);
      reedGen.generate(currentWorld, randomGenerator, field_180294_c.add(var4, var11, var5));
    }
    
    for (var3 = 0; var3 < 10; var3++)
    {
      int var4 = randomGenerator.nextInt(16) + 8;
      int var5 = randomGenerator.nextInt(16) + 8;
      int var11 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var4, 0, var5)).getY() * 2);
      reedGen.generate(currentWorld, randomGenerator, field_180294_c.add(var4, var11, var5));
    }
    
    if (randomGenerator.nextInt(32) == 0)
    {
      var3 = randomGenerator.nextInt(16) + 8;
      int var4 = randomGenerator.nextInt(16) + 8;
      int var5 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var3, 0, var4)).getY() * 2);
      new WorldGenPumpkin().generate(currentWorld, randomGenerator, field_180294_c.add(var3, var5, var4));
    }
    
    for (var3 = 0; var3 < cactiPerChunk; var3++)
    {
      int var4 = randomGenerator.nextInt(16) + 8;
      int var5 = randomGenerator.nextInt(16) + 8;
      int var11 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var4, 0, var5)).getY() * 2);
      cactusGen.generate(currentWorld, randomGenerator, field_180294_c.add(var4, var11, var5));
    }
    
    if (generateLakes)
    {


      for (var3 = 0; var3 < 50; var3++)
      {
        BlockPos var10 = field_180294_c.add(randomGenerator.nextInt(16) + 8, randomGenerator.nextInt(randomGenerator.nextInt(248) + 8), randomGenerator.nextInt(16) + 8);
        new WorldGenLiquids(Blocks.flowing_water).generate(currentWorld, randomGenerator, var10);
      }
      
      for (var3 = 0; var3 < 20; var3++)
      {
        BlockPos var10 = field_180294_c.add(randomGenerator.nextInt(16) + 8, randomGenerator.nextInt(randomGenerator.nextInt(randomGenerator.nextInt(240) + 8) + 8), randomGenerator.nextInt(16) + 8);
        new WorldGenLiquids(Blocks.flowing_lava).generate(currentWorld, randomGenerator, var10);
      }
    }
  }
  





  protected void genStandardOre1(int p_76795_1_, WorldGenerator p_76795_2_, int p_76795_3_, int p_76795_4_)
  {
    if (p_76795_4_ < p_76795_3_)
    {
      int var5 = p_76795_3_;
      p_76795_3_ = p_76795_4_;
      p_76795_4_ = var5;
    }
    else if (p_76795_4_ == p_76795_3_)
    {
      if (p_76795_3_ < 255)
      {
        p_76795_4_++;
      }
      else
      {
        p_76795_3_--;
      }
    }
    
    for (int var5 = 0; var5 < p_76795_1_; var5++)
    {
      BlockPos var6 = field_180294_c.add(randomGenerator.nextInt(16), randomGenerator.nextInt(p_76795_4_ - p_76795_3_) + p_76795_3_, randomGenerator.nextInt(16));
      p_76795_2_.generate(currentWorld, randomGenerator, var6);
    }
  }
  



  protected void genStandardOre2(int p_76793_1_, WorldGenerator p_76793_2_, int p_76793_3_, int p_76793_4_)
  {
    for (int var5 = 0; var5 < p_76793_1_; var5++)
    {
      BlockPos var6 = field_180294_c.add(randomGenerator.nextInt(16), randomGenerator.nextInt(p_76793_4_) + randomGenerator.nextInt(p_76793_4_) + p_76793_3_ - p_76793_4_, randomGenerator.nextInt(16));
      p_76793_2_.generate(currentWorld, randomGenerator, var6);
    }
  }
  



  protected void generateOres()
  {
    genStandardOre1(field_180293_d.field_177790_J, dirtGen, field_180293_d.field_177791_K, field_180293_d.field_177784_L);
    genStandardOre1(field_180293_d.field_177786_N, gravelGen, field_180293_d.field_177787_O, field_180293_d.field_177797_P);
    genStandardOre1(field_180293_d.field_177795_V, field_180297_k, field_180293_d.field_177794_W, field_180293_d.field_177801_X);
    genStandardOre1(field_180293_d.field_177799_R, field_180296_j, field_180293_d.field_177798_S, field_180293_d.field_177793_T);
    genStandardOre1(field_180293_d.field_177802_Z, field_180295_l, field_180293_d.field_177846_aa, field_180293_d.field_177847_ab);
    genStandardOre1(field_180293_d.field_177845_ad, coalGen, field_180293_d.field_177851_ae, field_180293_d.field_177853_af);
    genStandardOre1(field_180293_d.field_177849_ah, ironGen, field_180293_d.field_177832_ai, field_180293_d.field_177834_aj);
    genStandardOre1(field_180293_d.field_177830_al, goldGen, field_180293_d.field_177840_am, field_180293_d.field_177842_an);
    genStandardOre1(field_180293_d.field_177838_ap, field_180299_p, field_180293_d.field_177818_aq, field_180293_d.field_177816_ar);
    genStandardOre1(field_180293_d.field_177812_at, field_180298_q, field_180293_d.field_177826_au, field_180293_d.field_177824_av);
    genStandardOre2(field_180293_d.field_177820_ax, lapisGen, field_180293_d.field_177807_ay, field_180293_d.field_177805_az);
  }
}
