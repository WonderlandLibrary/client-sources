package net.minecraft.world.gen;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureOceanMonument;
import net.minecraft.world.storage.WorldInfo;


















public class ChunkProviderGenerate
  implements IChunkProvider
{
  private Random rand;
  private NoiseGeneratorOctaves field_147431_j;
  private NoiseGeneratorOctaves field_147432_k;
  private NoiseGeneratorOctaves field_147429_l;
  private NoiseGeneratorPerlin field_147430_m;
  public NoiseGeneratorOctaves noiseGen5;
  public NoiseGeneratorOctaves noiseGen6;
  public NoiseGeneratorOctaves mobSpawnerNoise;
  private World worldObj;
  private final boolean mapFeaturesEnabled;
  private WorldType field_177475_o;
  private final double[] field_147434_q;
  private final float[] parabolicField;
  private ChunkProviderSettings field_177477_r;
  private Block field_177476_s;
  private double[] stoneNoise;
  private MapGenBase caveGenerator;
  private MapGenStronghold strongholdGenerator;
  private MapGenVillage villageGenerator;
  private MapGenMineshaft mineshaftGenerator;
  private MapGenScatteredFeature scatteredFeatureGenerator;
  private MapGenBase ravineGenerator;
  private StructureOceanMonument field_177474_A;
  private BiomeGenBase[] biomesForGeneration;
  double[] field_147427_d;
  double[] field_147428_e;
  double[] field_147425_f;
  double[] field_147426_g;
  private static final String __OBFID = "CL_00000396";
  
  public ChunkProviderGenerate(World worldIn, long p_i45636_2_, boolean p_i45636_4_, String p_i45636_5_)
  {
    field_177476_s = Blocks.water;
    stoneNoise = new double['Ā'];
    caveGenerator = new MapGenCaves();
    strongholdGenerator = new MapGenStronghold();
    villageGenerator = new MapGenVillage();
    mineshaftGenerator = new MapGenMineshaft();
    scatteredFeatureGenerator = new MapGenScatteredFeature();
    ravineGenerator = new MapGenRavine();
    field_177474_A = new StructureOceanMonument();
    worldObj = worldIn;
    mapFeaturesEnabled = p_i45636_4_;
    field_177475_o = worldIn.getWorldInfo().getTerrainType();
    rand = new Random(p_i45636_2_);
    field_147431_j = new NoiseGeneratorOctaves(rand, 16);
    field_147432_k = new NoiseGeneratorOctaves(rand, 16);
    field_147429_l = new NoiseGeneratorOctaves(rand, 8);
    field_147430_m = new NoiseGeneratorPerlin(rand, 4);
    noiseGen5 = new NoiseGeneratorOctaves(rand, 10);
    noiseGen6 = new NoiseGeneratorOctaves(rand, 16);
    mobSpawnerNoise = new NoiseGeneratorOctaves(rand, 8);
    field_147434_q = new double['̹'];
    parabolicField = new float[25];
    
    for (int var6 = -2; var6 <= 2; var6++)
    {
      for (int var7 = -2; var7 <= 2; var7++)
      {
        float var8 = 10.0F / MathHelper.sqrt_float(var6 * var6 + var7 * var7 + 0.2F);
        parabolicField[(var6 + 2 + (var7 + 2) * 5)] = var8;
      }
    }
    
    if (p_i45636_5_ != null)
    {
      field_177477_r = ChunkProviderSettings.Factory.func_177865_a(p_i45636_5_).func_177864_b();
      field_177476_s = (field_177477_r.field_177778_E ? Blocks.lava : Blocks.water);
    }
  }
  
  public void func_180518_a(int p_180518_1_, int p_180518_2_, ChunkPrimer p_180518_3_)
  {
    biomesForGeneration = worldObj.getWorldChunkManager().getBiomesForGeneration(biomesForGeneration, p_180518_1_ * 4 - 2, p_180518_2_ * 4 - 2, 10, 10);
    func_147423_a(p_180518_1_ * 4, 0, p_180518_2_ * 4);
    
    for (int var4 = 0; var4 < 4; var4++)
    {
      int var5 = var4 * 5;
      int var6 = (var4 + 1) * 5;
      
      for (int var7 = 0; var7 < 4; var7++)
      {
        int var8 = (var5 + var7) * 33;
        int var9 = (var5 + var7 + 1) * 33;
        int var10 = (var6 + var7) * 33;
        int var11 = (var6 + var7 + 1) * 33;
        
        for (int var12 = 0; var12 < 32; var12++)
        {
          double var13 = 0.125D;
          double var15 = field_147434_q[(var8 + var12)];
          double var17 = field_147434_q[(var9 + var12)];
          double var19 = field_147434_q[(var10 + var12)];
          double var21 = field_147434_q[(var11 + var12)];
          double var23 = (field_147434_q[(var8 + var12 + 1)] - var15) * var13;
          double var25 = (field_147434_q[(var9 + var12 + 1)] - var17) * var13;
          double var27 = (field_147434_q[(var10 + var12 + 1)] - var19) * var13;
          double var29 = (field_147434_q[(var11 + var12 + 1)] - var21) * var13;
          
          for (int var31 = 0; var31 < 8; var31++)
          {
            double var32 = 0.25D;
            double var34 = var15;
            double var36 = var17;
            double var38 = (var19 - var15) * var32;
            double var40 = (var21 - var17) * var32;
            
            for (int var42 = 0; var42 < 4; var42++)
            {
              double var43 = 0.25D;
              double var47 = (var36 - var34) * var43;
              double var45 = var34 - var47;
              
              for (int var49 = 0; var49 < 4; var49++)
              {
                if (var45 += var47 > 0.0D)
                {
                  p_180518_3_.setBlockState(var4 * 4 + var42, var12 * 8 + var31, var7 * 4 + var49, Blocks.stone.getDefaultState());
                }
                else if (var12 * 8 + var31 < field_177477_r.field_177841_q)
                {
                  p_180518_3_.setBlockState(var4 * 4 + var42, var12 * 8 + var31, var7 * 4 + var49, field_177476_s.getDefaultState());
                }
              }
              
              var34 += var38;
              var36 += var40;
            }
            
            var15 += var23;
            var17 += var25;
            var19 += var27;
            var21 += var29;
          }
        }
      }
    }
  }
  
  public void func_180517_a(int p_180517_1_, int p_180517_2_, ChunkPrimer p_180517_3_, BiomeGenBase[] p_180517_4_)
  {
    double var5 = 0.03125D;
    stoneNoise = field_147430_m.func_151599_a(stoneNoise, p_180517_1_ * 16, p_180517_2_ * 16, 16, 16, var5 * 2.0D, var5 * 2.0D, 1.0D);
    
    for (int var7 = 0; var7 < 16; var7++)
    {
      for (int var8 = 0; var8 < 16; var8++)
      {
        BiomeGenBase var9 = p_180517_4_[(var8 + var7 * 16)];
        var9.genTerrainBlocks(worldObj, rand, p_180517_3_, p_180517_1_ * 16 + var7, p_180517_2_ * 16 + var8, stoneNoise[(var8 + var7 * 16)]);
      }
    }
  }
  




  public Chunk provideChunk(int p_73154_1_, int p_73154_2_)
  {
    rand.setSeed(p_73154_1_ * 341873128712L + p_73154_2_ * 132897987541L);
    ChunkPrimer var3 = new ChunkPrimer();
    func_180518_a(p_73154_1_, p_73154_2_, var3);
    biomesForGeneration = worldObj.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16);
    func_180517_a(p_73154_1_, p_73154_2_, var3, biomesForGeneration);
    
    if (field_177477_r.field_177839_r)
    {
      caveGenerator.func_175792_a(this, worldObj, p_73154_1_, p_73154_2_, var3);
    }
    
    if (field_177477_r.field_177850_z)
    {
      ravineGenerator.func_175792_a(this, worldObj, p_73154_1_, p_73154_2_, var3);
    }
    
    if ((field_177477_r.field_177829_w) && (mapFeaturesEnabled))
    {
      mineshaftGenerator.func_175792_a(this, worldObj, p_73154_1_, p_73154_2_, var3);
    }
    
    if ((field_177477_r.field_177831_v) && (mapFeaturesEnabled))
    {
      villageGenerator.func_175792_a(this, worldObj, p_73154_1_, p_73154_2_, var3);
    }
    
    if ((field_177477_r.field_177833_u) && (mapFeaturesEnabled))
    {
      strongholdGenerator.func_175792_a(this, worldObj, p_73154_1_, p_73154_2_, var3);
    }
    
    if ((field_177477_r.field_177854_x) && (mapFeaturesEnabled))
    {
      scatteredFeatureGenerator.func_175792_a(this, worldObj, p_73154_1_, p_73154_2_, var3);
    }
    
    if ((field_177477_r.field_177852_y) && (mapFeaturesEnabled))
    {
      field_177474_A.func_175792_a(this, worldObj, p_73154_1_, p_73154_2_, var3);
    }
    
    Chunk var4 = new Chunk(worldObj, var3, p_73154_1_, p_73154_2_);
    byte[] var5 = var4.getBiomeArray();
    
    for (int var6 = 0; var6 < var5.length; var6++)
    {
      var5[var6] = ((byte)biomesForGeneration[var6].biomeID);
    }
    
    var4.generateSkylightMap();
    return var4;
  }
  
  private void func_147423_a(int p_147423_1_, int p_147423_2_, int p_147423_3_)
  {
    field_147426_g = noiseGen6.generateNoiseOctaves(field_147426_g, p_147423_1_, p_147423_3_, 5, 5, field_177477_r.field_177808_e, field_177477_r.field_177803_f, field_177477_r.field_177804_g);
    float var4 = field_177477_r.field_177811_a;
    float var5 = field_177477_r.field_177809_b;
    field_147427_d = field_147429_l.generateNoiseOctaves(field_147427_d, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, var4 / field_177477_r.field_177825_h, var5 / field_177477_r.field_177827_i, var4 / field_177477_r.field_177821_j);
    field_147428_e = field_147431_j.generateNoiseOctaves(field_147428_e, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, var4, var5, var4);
    field_147425_f = field_147432_k.generateNoiseOctaves(field_147425_f, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, var4, var5, var4);
    boolean var37 = false;
    boolean var36 = false;
    int var6 = 0;
    int var7 = 0;
    
    for (int var8 = 0; var8 < 5; var8++)
    {
      for (int var9 = 0; var9 < 5; var9++)
      {
        float var10 = 0.0F;
        float var11 = 0.0F;
        float var12 = 0.0F;
        byte var13 = 2;
        BiomeGenBase var14 = biomesForGeneration[(var8 + 2 + (var9 + 2) * 10)];
        
        for (int var15 = -var13; var15 <= var13; var15++)
        {
          for (int var16 = -var13; var16 <= var13; var16++)
          {
            BiomeGenBase var17 = biomesForGeneration[(var8 + var15 + 2 + (var9 + var16 + 2) * 10)];
            float var18 = field_177477_r.field_177813_n + minHeight * field_177477_r.field_177819_m;
            float var19 = field_177477_r.field_177843_p + maxHeight * field_177477_r.field_177815_o;
            
            if ((field_177475_o == WorldType.AMPLIFIED) && (var18 > 0.0F))
            {
              var18 = 1.0F + var18 * 2.0F;
              var19 = 1.0F + var19 * 4.0F;
            }
            
            float var20 = parabolicField[(var15 + 2 + (var16 + 2) * 5)] / (var18 + 2.0F);
            
            if (minHeight > minHeight)
            {
              var20 /= 2.0F;
            }
            
            var10 += var19 * var20;
            var11 += var18 * var20;
            var12 += var20;
          }
        }
        
        var10 /= var12;
        var11 /= var12;
        var10 = var10 * 0.9F + 0.1F;
        var11 = (var11 * 4.0F - 1.0F) / 8.0F;
        double var38 = field_147426_g[var7] / 8000.0D;
        
        if (var38 < 0.0D)
        {
          var38 = -var38 * 0.3D;
        }
        
        var38 = var38 * 3.0D - 2.0D;
        
        if (var38 < 0.0D)
        {
          var38 /= 2.0D;
          
          if (var38 < -1.0D)
          {
            var38 = -1.0D;
          }
          
          var38 /= 1.4D;
          var38 /= 2.0D;
        }
        else
        {
          if (var38 > 1.0D)
          {
            var38 = 1.0D;
          }
          
          var38 /= 8.0D;
        }
        
        var7++;
        double var39 = var11;
        double var40 = var10;
        var39 += var38 * 0.2D;
        var39 = var39 * field_177477_r.field_177823_k / 8.0D;
        double var21 = field_177477_r.field_177823_k + var39 * 4.0D;
        
        for (int var23 = 0; var23 < 33; var23++)
        {
          double var24 = (var23 - var21) * field_177477_r.field_177817_l * 128.0D / 256.0D / var40;
          
          if (var24 < 0.0D)
          {
            var24 *= 4.0D;
          }
          
          double var26 = field_147428_e[var6] / field_177477_r.field_177806_d;
          double var28 = field_147425_f[var6] / field_177477_r.field_177810_c;
          double var30 = (field_147427_d[var6] / 10.0D + 1.0D) / 2.0D;
          double var32 = MathHelper.denormalizeClamp(var26, var28, var30) - var24;
          
          if (var23 > 29)
          {
            double var34 = (var23 - 29) / 3.0F;
            var32 = var32 * (1.0D - var34) + -10.0D * var34;
          }
          
          field_147434_q[var6] = var32;
          var6++;
        }
      }
    }
  }
  



  public boolean chunkExists(int p_73149_1_, int p_73149_2_)
  {
    return true;
  }
  



  public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_)
  {
    net.minecraft.block.BlockFalling.fallInstantly = true;
    int var4 = p_73153_2_ * 16;
    int var5 = p_73153_3_ * 16;
    BlockPos var6 = new BlockPos(var4, 0, var5);
    BiomeGenBase var7 = worldObj.getBiomeGenForCoords(var6.add(16, 0, 16));
    rand.setSeed(worldObj.getSeed());
    long var8 = rand.nextLong() / 2L * 2L + 1L;
    long var10 = rand.nextLong() / 2L * 2L + 1L;
    rand.setSeed(p_73153_2_ * var8 + p_73153_3_ * var10 ^ worldObj.getSeed());
    boolean var12 = false;
    ChunkCoordIntPair var13 = new ChunkCoordIntPair(p_73153_2_, p_73153_3_);
    
    if ((field_177477_r.field_177829_w) && (mapFeaturesEnabled))
    {
      mineshaftGenerator.func_175794_a(worldObj, rand, var13);
    }
    
    if ((field_177477_r.field_177831_v) && (mapFeaturesEnabled))
    {
      var12 = villageGenerator.func_175794_a(worldObj, rand, var13);
    }
    
    if ((field_177477_r.field_177833_u) && (mapFeaturesEnabled))
    {
      strongholdGenerator.func_175794_a(worldObj, rand, var13);
    }
    
    if ((field_177477_r.field_177854_x) && (mapFeaturesEnabled))
    {
      scatteredFeatureGenerator.func_175794_a(worldObj, rand, var13);
    }
    
    if ((field_177477_r.field_177852_y) && (mapFeaturesEnabled))
    {
      field_177474_A.func_175794_a(worldObj, rand, var13);
    }
    




    if ((var7 != BiomeGenBase.desert) && (var7 != BiomeGenBase.desertHills) && (field_177477_r.field_177781_A) && (!var12) && (rand.nextInt(field_177477_r.field_177782_B) == 0))
    {
      int var14 = rand.nextInt(16) + 8;
      int var15 = rand.nextInt(256);
      int var16 = rand.nextInt(16) + 8;
      new WorldGenLakes(Blocks.water).generate(worldObj, rand, var6.add(var14, var15, var16));
    }
    
    if ((!var12) && (rand.nextInt(field_177477_r.field_177777_D / 10) == 0) && (field_177477_r.field_177783_C))
    {
      int var14 = rand.nextInt(16) + 8;
      int var15 = rand.nextInt(rand.nextInt(248) + 8);
      int var16 = rand.nextInt(16) + 8;
      
      if ((var15 < 63) || (rand.nextInt(field_177477_r.field_177777_D / 8) == 0))
      {
        new WorldGenLakes(Blocks.lava).generate(worldObj, rand, var6.add(var14, var15, var16));
      }
    }
    
    if (field_177477_r.field_177837_s)
    {
      for (int var14 = 0; var14 < field_177477_r.field_177835_t; var14++)
      {
        int var15 = rand.nextInt(16) + 8;
        int var16 = rand.nextInt(256);
        int var17 = rand.nextInt(16) + 8;
        new WorldGenDungeons().generate(worldObj, rand, var6.add(var15, var16, var17));
      }
    }
    
    var7.func_180624_a(worldObj, rand, new BlockPos(var4, 0, var5));
    SpawnerAnimals.performWorldGenSpawning(worldObj, var7, var4 + 8, var5 + 8, 16, 16, rand);
    var6 = var6.add(8, 0, 8);
    
    for (int var14 = 0; var14 < 16; var14++)
    {
      for (int var15 = 0; var15 < 16; var15++)
      {
        BlockPos var18 = worldObj.func_175725_q(var6.add(var14, 0, var15));
        BlockPos var19 = var18.offsetDown();
        
        if (worldObj.func_175675_v(var19))
        {
          worldObj.setBlockState(var19, Blocks.ice.getDefaultState(), 2);
        }
        
        if (worldObj.func_175708_f(var18, true))
        {
          worldObj.setBlockState(var18, Blocks.snow_layer.getDefaultState(), 2);
        }
      }
    }
    
    net.minecraft.block.BlockFalling.fallInstantly = false;
  }
  
  public boolean func_177460_a(IChunkProvider p_177460_1_, Chunk p_177460_2_, int p_177460_3_, int p_177460_4_)
  {
    boolean var5 = false;
    
    if ((field_177477_r.field_177852_y) && (mapFeaturesEnabled) && (p_177460_2_.getInhabitedTime() < 3600L))
    {
      var5 |= field_177474_A.func_175794_a(worldObj, rand, new ChunkCoordIntPair(p_177460_3_, p_177460_4_));
    }
    
    return var5;
  }
  




  public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_)
  {
    return true;
  }
  




  public void saveExtraData() {}
  



  public boolean unloadQueuedChunks()
  {
    return false;
  }
  



  public boolean canSave()
  {
    return true;
  }
  



  public String makeString()
  {
    return "RandomLevelSource";
  }
  
  public List func_177458_a(EnumCreatureType p_177458_1_, BlockPos p_177458_2_)
  {
    BiomeGenBase var3 = worldObj.getBiomeGenForCoords(p_177458_2_);
    
    if (mapFeaturesEnabled)
    {
      if ((p_177458_1_ == EnumCreatureType.MONSTER) && (scatteredFeatureGenerator.func_175798_a(p_177458_2_)))
      {
        return scatteredFeatureGenerator.getScatteredFeatureSpawnList();
      }
      
      if ((p_177458_1_ == EnumCreatureType.MONSTER) && (field_177477_r.field_177852_y) && (field_177474_A.func_175796_a(worldObj, p_177458_2_)))
      {
        return field_177474_A.func_175799_b();
      }
    }
    
    return var3.getSpawnableList(p_177458_1_);
  }
  
  public BlockPos func_180513_a(World worldIn, String p_180513_2_, BlockPos p_180513_3_)
  {
    return ("Stronghold".equals(p_180513_2_)) && (strongholdGenerator != null) ? strongholdGenerator.func_180706_b(worldIn, p_180513_3_) : null;
  }
  
  public int getLoadedChunkCount()
  {
    return 0;
  }
  
  public void func_180514_a(Chunk p_180514_1_, int p_180514_2_, int p_180514_3_)
  {
    if ((field_177477_r.field_177829_w) && (mapFeaturesEnabled))
    {
      mineshaftGenerator.func_175792_a(this, worldObj, p_180514_2_, p_180514_3_, null);
    }
    
    if ((field_177477_r.field_177831_v) && (mapFeaturesEnabled))
    {
      villageGenerator.func_175792_a(this, worldObj, p_180514_2_, p_180514_3_, null);
    }
    
    if ((field_177477_r.field_177833_u) && (mapFeaturesEnabled))
    {
      strongholdGenerator.func_175792_a(this, worldObj, p_180514_2_, p_180514_3_, null);
    }
    
    if ((field_177477_r.field_177854_x) && (mapFeaturesEnabled))
    {
      scatteredFeatureGenerator.func_175792_a(this, worldObj, p_180514_2_, p_180514_3_, null);
    }
    
    if ((field_177477_r.field_177852_y) && (mapFeaturesEnabled))
    {
      field_177474_A.func_175792_a(this, worldObj, p_180514_2_, p_180514_3_, null);
    }
  }
  
  public Chunk func_177459_a(BlockPos p_177459_1_)
  {
    return provideChunk(p_177459_1_.getX() >> 4, p_177459_1_.getZ() >> 4);
  }
}
