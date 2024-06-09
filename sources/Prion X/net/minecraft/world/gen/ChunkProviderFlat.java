package net.minecraft.world.gen;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
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
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureOceanMonument;

public class ChunkProviderFlat implements IChunkProvider
{
  private World worldObj;
  private Random random;
  private final IBlockState[] cachedBlockIDs = new IBlockState['Ā'];
  private final FlatGeneratorInfo flatWorldGenInfo;
  private final List structureGenerators = com.google.common.collect.Lists.newArrayList();
  private final boolean hasDecoration;
  private final boolean hasDungeons;
  private WorldGenLakes waterLakeGenerator;
  private WorldGenLakes lavaLakeGenerator;
  private static final String __OBFID = "CL_00000391";
  
  public ChunkProviderFlat(World worldIn, long p_i2004_2_, boolean p_i2004_4_, String p_i2004_5_)
  {
    worldObj = worldIn;
    random = new Random(p_i2004_2_);
    flatWorldGenInfo = FlatGeneratorInfo.createFlatGeneratorFromString(p_i2004_5_);
    
    if (p_i2004_4_)
    {
      Map var6 = flatWorldGenInfo.getWorldFeatures();
      
      if (var6.containsKey("village"))
      {
        Map var7 = (Map)var6.get("village");
        
        if (!var7.containsKey("size"))
        {
          var7.put("size", "1");
        }
        
        structureGenerators.add(new MapGenVillage(var7));
      }
      
      if (var6.containsKey("biome_1"))
      {
        structureGenerators.add(new MapGenScatteredFeature((Map)var6.get("biome_1")));
      }
      
      if (var6.containsKey("mineshaft"))
      {
        structureGenerators.add(new MapGenMineshaft((Map)var6.get("mineshaft")));
      }
      
      if (var6.containsKey("stronghold"))
      {
        structureGenerators.add(new MapGenStronghold((Map)var6.get("stronghold")));
      }
      
      if (var6.containsKey("oceanmonument"))
      {
        structureGenerators.add(new StructureOceanMonument((Map)var6.get("oceanmonument")));
      }
    }
    
    if (flatWorldGenInfo.getWorldFeatures().containsKey("lake"))
    {
      waterLakeGenerator = new WorldGenLakes(Blocks.water);
    }
    
    if (flatWorldGenInfo.getWorldFeatures().containsKey("lava_lake"))
    {
      lavaLakeGenerator = new WorldGenLakes(Blocks.lava);
    }
    
    hasDungeons = flatWorldGenInfo.getWorldFeatures().containsKey("dungeon");
    boolean var11 = true;
    Iterator var12 = flatWorldGenInfo.getFlatLayers().iterator();
    FlatLayerInfo var8;
    int var9; for (; var12.hasNext(); 
        


        var9 < var8.getMinY() + var8.getLayerCount())
    {
      var8 = (FlatLayerInfo)var12.next();
      
      var9 = var8.getMinY(); continue;
      
      IBlockState var10 = var8.func_175900_c();
      
      if (var10.getBlock() != Blocks.air)
      {
        var11 = false;
        cachedBlockIDs[var9] = var10;
      }
      var9++;
    }
    









    hasDecoration = (var11 ? false : flatWorldGenInfo.getWorldFeatures().containsKey("decoration"));
  }
  




  public Chunk provideChunk(int p_73154_1_, int p_73154_2_)
  {
    ChunkPrimer var3 = new ChunkPrimer();
    

    for (int var4 = 0; var4 < cachedBlockIDs.length; var4++)
    {
      IBlockState var5 = cachedBlockIDs[var4];
      
      if (var5 != null)
      {
        for (int var6 = 0; var6 < 16; var6++)
        {
          for (int var7 = 0; var7 < 16; var7++)
          {
            var3.setBlockState(var6, var4, var7, var5);
          }
        }
      }
    }
    
    Iterator var8 = structureGenerators.iterator();
    
    while (var8.hasNext())
    {
      MapGenBase var10 = (MapGenBase)var8.next();
      var10.func_175792_a(this, worldObj, p_73154_1_, p_73154_2_, var3);
    }
    
    Chunk var9 = new Chunk(worldObj, var3, p_73154_1_, p_73154_2_);
    BiomeGenBase[] var11 = worldObj.getWorldChunkManager().loadBlockGeneratorData(null, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16);
    byte[] var12 = var9.getBiomeArray();
    
    for (int var7 = 0; var7 < var12.length; var7++)
    {
      var12[var7] = ((byte)biomeID);
    }
    
    var9.generateSkylightMap();
    return var9;
  }
  



  public boolean chunkExists(int p_73149_1_, int p_73149_2_)
  {
    return true;
  }
  



  public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_)
  {
    int var4 = p_73153_2_ * 16;
    int var5 = p_73153_3_ * 16;
    BlockPos var6 = new BlockPos(var4, 0, var5);
    BiomeGenBase var7 = worldObj.getBiomeGenForCoords(new BlockPos(var4 + 16, 0, var5 + 16));
    boolean var8 = false;
    random.setSeed(worldObj.getSeed());
    long var9 = random.nextLong() / 2L * 2L + 1L;
    long var11 = random.nextLong() / 2L * 2L + 1L;
    random.setSeed(p_73153_2_ * var9 + p_73153_3_ * var11 ^ worldObj.getSeed());
    ChunkCoordIntPair var13 = new ChunkCoordIntPair(p_73153_2_, p_73153_3_);
    Iterator var14 = structureGenerators.iterator();
    
    while (var14.hasNext())
    {
      MapGenStructure var15 = (MapGenStructure)var14.next();
      boolean var16 = var15.func_175794_a(worldObj, random, var13);
      
      if ((var15 instanceof MapGenVillage))
      {
        var8 |= var16;
      }
    }
    
    if ((waterLakeGenerator != null) && (!var8) && (random.nextInt(4) == 0))
    {
      waterLakeGenerator.generate(worldObj, random, var6.add(random.nextInt(16) + 8, random.nextInt(256), random.nextInt(16) + 8));
    }
    
    if ((lavaLakeGenerator != null) && (!var8) && (random.nextInt(8) == 0))
    {
      BlockPos var17 = var6.add(random.nextInt(16) + 8, random.nextInt(random.nextInt(248) + 8), random.nextInt(16) + 8);
      
      if ((var17.getY() < 63) || (random.nextInt(10) == 0))
      {
        lavaLakeGenerator.generate(worldObj, random, var17);
      }
    }
    
    if (hasDungeons)
    {
      for (int var18 = 0; var18 < 8; var18++)
      {
        new WorldGenDungeons().generate(worldObj, random, var6.add(random.nextInt(16) + 8, random.nextInt(256), random.nextInt(16) + 8));
      }
    }
    
    if (hasDecoration)
    {
      var7.func_180624_a(worldObj, random, new BlockPos(var4, 0, var5));
    }
  }
  
  public boolean func_177460_a(IChunkProvider p_177460_1_, Chunk p_177460_2_, int p_177460_3_, int p_177460_4_)
  {
    return false;
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
    return "FlatLevelSource";
  }
  
  public List func_177458_a(EnumCreatureType p_177458_1_, BlockPos p_177458_2_)
  {
    BiomeGenBase var3 = worldObj.getBiomeGenForCoords(p_177458_2_);
    return var3.getSpawnableList(p_177458_1_);
  }
  
  public BlockPos func_180513_a(World worldIn, String p_180513_2_, BlockPos p_180513_3_)
  {
    if ("Stronghold".equals(p_180513_2_))
    {
      Iterator var4 = structureGenerators.iterator();
      
      while (var4.hasNext())
      {
        MapGenStructure var5 = (MapGenStructure)var4.next();
        
        if ((var5 instanceof MapGenStronghold))
        {
          return var5.func_180706_b(worldIn, p_180513_3_);
        }
      }
    }
    
    return null;
  }
  
  public int getLoadedChunkCount()
  {
    return 0;
  }
  
  public void func_180514_a(Chunk p_180514_1_, int p_180514_2_, int p_180514_3_)
  {
    Iterator var4 = structureGenerators.iterator();
    
    while (var4.hasNext())
    {
      MapGenStructure var5 = (MapGenStructure)var4.next();
      var5.func_175792_a(this, worldObj, p_180514_2_, p_180514_3_, null);
    }
  }
  
  public Chunk func_177459_a(BlockPos p_177459_1_)
  {
    return provideChunk(p_177459_1_.getX() >> 4, p_177459_1_.getZ() >> 4);
  }
}
