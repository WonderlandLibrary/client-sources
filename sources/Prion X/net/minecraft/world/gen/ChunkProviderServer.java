package net.minecraft.world.gen;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkProviderServer implements IChunkProvider
{
  private static final Logger logger = ;
  private Set droppedChunksSet = Collections.newSetFromMap(new ConcurrentHashMap());
  


  private Chunk dummyChunk;
  


  private IChunkProvider serverChunkGenerator;
  


  private IChunkLoader chunkLoader;
  

  public boolean chunkLoadOverride = true;
  

  private LongHashMap id2ChunkMap = new LongHashMap();
  private List loadedChunks = Lists.newArrayList();
  private WorldServer worldObj;
  private static final String __OBFID = "CL_00001436";
  
  public ChunkProviderServer(WorldServer p_i1520_1_, IChunkLoader p_i1520_2_, IChunkProvider p_i1520_3_)
  {
    dummyChunk = new EmptyChunk(p_i1520_1_, 0, 0);
    worldObj = p_i1520_1_;
    chunkLoader = p_i1520_2_;
    serverChunkGenerator = p_i1520_3_;
  }
  



  public boolean chunkExists(int p_73149_1_, int p_73149_2_)
  {
    return id2ChunkMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(p_73149_1_, p_73149_2_));
  }
  
  public List func_152380_a()
  {
    return loadedChunks;
  }
  
  public void dropChunk(int p_73241_1_, int p_73241_2_)
  {
    if (worldObj.provider.canRespawnHere())
    {
      if (!worldObj.chunkExists(p_73241_1_, p_73241_2_))
      {
        droppedChunksSet.add(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(p_73241_1_, p_73241_2_)));
      }
      
    }
    else {
      droppedChunksSet.add(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(p_73241_1_, p_73241_2_)));
    }
  }
  



  public void unloadAllChunks()
  {
    Iterator var1 = loadedChunks.iterator();
    
    while (var1.hasNext())
    {
      Chunk var2 = (Chunk)var1.next();
      dropChunk(xPosition, zPosition);
    }
  }
  



  public Chunk loadChunk(int p_73158_1_, int p_73158_2_)
  {
    long var3 = ChunkCoordIntPair.chunkXZ2Int(p_73158_1_, p_73158_2_);
    droppedChunksSet.remove(Long.valueOf(var3));
    Chunk var5 = (Chunk)id2ChunkMap.getValueByKey(var3);
    
    if (var5 == null)
    {
      var5 = loadChunkFromFile(p_73158_1_, p_73158_2_);
      
      if (var5 == null)
      {
        if (serverChunkGenerator == null)
        {
          var5 = dummyChunk;
        }
        else
        {
          try
          {
            var5 = serverChunkGenerator.provideChunk(p_73158_1_, p_73158_2_);
          }
          catch (Throwable var9)
          {
            CrashReport var7 = CrashReport.makeCrashReport(var9, "Exception generating new chunk");
            CrashReportCategory var8 = var7.makeCategory("Chunk to be generated");
            var8.addCrashSection("Location", String.format("%d,%d", new Object[] { Integer.valueOf(p_73158_1_), Integer.valueOf(p_73158_2_) }));
            var8.addCrashSection("Position hash", Long.valueOf(var3));
            var8.addCrashSection("Generator", serverChunkGenerator.makeString());
            throw new net.minecraft.util.ReportedException(var7);
          }
        }
      }
      
      id2ChunkMap.add(var3, var5);
      loadedChunks.add(var5);
      var5.onChunkLoad();
      var5.populateChunk(this, this, p_73158_1_, p_73158_2_);
    }
    
    return var5;
  }
  




  public Chunk provideChunk(int p_73154_1_, int p_73154_2_)
  {
    Chunk var3 = (Chunk)id2ChunkMap.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(p_73154_1_, p_73154_2_));
    return var3 == null ? loadChunk(p_73154_1_, p_73154_2_) : (!worldObj.isFindingSpawnPoint()) && (!chunkLoadOverride) ? dummyChunk : var3;
  }
  
  private Chunk loadChunkFromFile(int p_73239_1_, int p_73239_2_)
  {
    if (chunkLoader == null)
    {
      return null;
    }
    

    try
    {
      Chunk var3 = chunkLoader.loadChunk(worldObj, p_73239_1_, p_73239_2_);
      
      if (var3 != null)
      {
        var3.setLastSaveTime(worldObj.getTotalWorldTime());
        
        if (serverChunkGenerator != null)
        {
          serverChunkGenerator.func_180514_a(var3, p_73239_1_, p_73239_2_);
        }
      }
      
      return var3;
    }
    catch (Exception var4)
    {
      logger.error("Couldn't load chunk", var4); }
    return null;
  }
  


  private void saveChunkExtraData(Chunk p_73243_1_)
  {
    if (chunkLoader != null)
    {
      try
      {
        chunkLoader.saveExtraChunkData(worldObj, p_73243_1_);
      }
      catch (Exception var3)
      {
        logger.error("Couldn't save entities", var3);
      }
    }
  }
  
  private void saveChunkData(Chunk p_73242_1_)
  {
    if (chunkLoader != null)
    {
      try
      {
        p_73242_1_.setLastSaveTime(worldObj.getTotalWorldTime());
        chunkLoader.saveChunk(worldObj, p_73242_1_);
      }
      catch (IOException var3)
      {
        logger.error("Couldn't save chunk", var3);
      }
      catch (MinecraftException var4)
      {
        logger.error("Couldn't save chunk; already in use by another instance of Minecraft?", var4);
      }
    }
  }
  



  public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_)
  {
    Chunk var4 = provideChunk(p_73153_2_, p_73153_3_);
    
    if (!var4.isTerrainPopulated())
    {
      var4.func_150809_p();
      
      if (serverChunkGenerator != null)
      {
        serverChunkGenerator.populate(p_73153_1_, p_73153_2_, p_73153_3_);
        var4.setChunkModified();
      }
    }
  }
  
  public boolean func_177460_a(IChunkProvider p_177460_1_, Chunk p_177460_2_, int p_177460_3_, int p_177460_4_)
  {
    if ((serverChunkGenerator != null) && (serverChunkGenerator.func_177460_a(p_177460_1_, p_177460_2_, p_177460_3_, p_177460_4_)))
    {
      Chunk var5 = provideChunk(p_177460_3_, p_177460_4_);
      var5.setChunkModified();
      return true;
    }
    

    return false;
  }
  





  public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_)
  {
    int var3 = 0;
    
    for (int var4 = 0; var4 < loadedChunks.size(); var4++)
    {
      Chunk var5 = (Chunk)loadedChunks.get(var4);
      
      if (p_73151_1_)
      {
        saveChunkExtraData(var5);
      }
      
      if (var5.needsSaving(p_73151_1_))
      {
        saveChunkData(var5);
        var5.setModified(false);
        var3++;
        
        if ((var3 == 24) && (!p_73151_1_))
        {
          return false;
        }
      }
    }
    
    return true;
  }
  




  public void saveExtraData()
  {
    if (chunkLoader != null)
    {
      chunkLoader.saveExtraData();
    }
  }
  



  public boolean unloadQueuedChunks()
  {
    if (!worldObj.disableLevelSaving)
    {
      for (int var1 = 0; var1 < 100; var1++)
      {
        if (!droppedChunksSet.isEmpty())
        {
          Long var2 = (Long)droppedChunksSet.iterator().next();
          Chunk var3 = (Chunk)id2ChunkMap.getValueByKey(var2.longValue());
          
          if (var3 != null)
          {
            var3.onChunkUnload();
            saveChunkData(var3);
            saveChunkExtraData(var3);
            id2ChunkMap.remove(var2.longValue());
            loadedChunks.remove(var3);
          }
          
          droppedChunksSet.remove(var2);
        }
      }
      
      if (chunkLoader != null)
      {
        chunkLoader.chunkTick();
      }
    }
    
    return serverChunkGenerator.unloadQueuedChunks();
  }
  



  public boolean canSave()
  {
    return !worldObj.disableLevelSaving;
  }
  



  public String makeString()
  {
    return "ServerChunkCache: " + id2ChunkMap.getNumHashElements() + " Drop: " + droppedChunksSet.size();
  }
  
  public List func_177458_a(EnumCreatureType p_177458_1_, BlockPos p_177458_2_)
  {
    return serverChunkGenerator.func_177458_a(p_177458_1_, p_177458_2_);
  }
  
  public BlockPos func_180513_a(World worldIn, String p_180513_2_, BlockPos p_180513_3_)
  {
    return serverChunkGenerator.func_180513_a(worldIn, p_180513_2_, p_180513_3_);
  }
  
  public int getLoadedChunkCount()
  {
    return id2ChunkMap.getNumHashElements();
  }
  
  public void func_180514_a(Chunk p_180514_1_, int p_180514_2_, int p_180514_3_) {}
  
  public Chunk func_177459_a(BlockPos p_177459_1_)
  {
    return provideChunk(p_177459_1_.getX() >> 4, p_177459_1_.getZ() >> 4);
  }
}
