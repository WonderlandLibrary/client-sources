package net.minecraft.server.management;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import org.apache.logging.log4j.Logger;

public class PlayerManager
{
  private static final Logger field_152627_a = ;
  
  private final WorldServer theWorldServer;
  
  private final List players = Lists.newArrayList();
  

  private final LongHashMap playerInstances = new LongHashMap();
  

  private final List playerInstancesToUpdate = Lists.newArrayList();
  

  private final List playerInstanceList = Lists.newArrayList();
  


  private int playerViewRadius;
  


  private long previousTotalWorldTime;
  

  private final int[][] xzDirectionsConst = { { 1 }, { 0, 1 }, { -1 }, { 0, -1 } };
  private static final String __OBFID = "CL_00001434";
  
  public PlayerManager(WorldServer p_i1176_1_)
  {
    theWorldServer = p_i1176_1_;
    func_152622_a(p_i1176_1_.func_73046_m().getConfigurationManager().getViewDistance());
  }
  



  public WorldServer getMinecraftServer()
  {
    return theWorldServer;
  }
  



  public void updatePlayerInstances()
  {
    long var1 = theWorldServer.getTotalWorldTime();
    


    if (var1 - previousTotalWorldTime > 8000L)
    {
      previousTotalWorldTime = var1;
      
      for (int var3 = 0; var3 < playerInstanceList.size(); var3++)
      {
        PlayerInstance var4 = (PlayerInstance)playerInstanceList.get(var3);
        var4.onUpdate();
        var4.processChunk();
      }
    }
    else
    {
      for (int var3 = 0; var3 < playerInstancesToUpdate.size(); var3++)
      {
        PlayerInstance var4 = (PlayerInstance)playerInstancesToUpdate.get(var3);
        var4.onUpdate();
      }
    }
    
    playerInstancesToUpdate.clear();
    
    if (players.isEmpty())
    {
      WorldProvider var5 = theWorldServer.provider;
      
      if (!var5.canRespawnHere())
      {
        theWorldServer.theChunkProviderServer.unloadAllChunks();
      }
    }
  }
  
  public boolean func_152621_a(int p_152621_1_, int p_152621_2_)
  {
    long var3 = p_152621_1_ + 2147483647L | p_152621_2_ + 2147483647L << 32;
    return playerInstances.getValueByKey(var3) != null;
  }
  



  private PlayerInstance getPlayerInstance(int p_72690_1_, int p_72690_2_, boolean p_72690_3_)
  {
    long var4 = p_72690_1_ + 2147483647L | p_72690_2_ + 2147483647L << 32;
    PlayerInstance var6 = (PlayerInstance)playerInstances.getValueByKey(var4);
    
    if ((var6 == null) && (p_72690_3_))
    {
      var6 = new PlayerInstance(p_72690_1_, p_72690_2_);
      playerInstances.add(var4, var6);
      playerInstanceList.add(var6);
    }
    
    return var6;
  }
  
  public void func_180244_a(BlockPos p_180244_1_)
  {
    int var2 = p_180244_1_.getX() >> 4;
    int var3 = p_180244_1_.getZ() >> 4;
    PlayerInstance var4 = getPlayerInstance(var2, var3, false);
    
    if (var4 != null)
    {
      var4.flagChunkForUpdate(p_180244_1_.getX() & 0xF, p_180244_1_.getY(), p_180244_1_.getZ() & 0xF);
    }
  }
  



  public void addPlayer(EntityPlayerMP p_72683_1_)
  {
    int var2 = (int)posX >> 4;
    int var3 = (int)posZ >> 4;
    managedPosX = posX;
    managedPosZ = posZ;
    
    for (int var4 = var2 - playerViewRadius; var4 <= var2 + playerViewRadius; var4++)
    {
      for (int var5 = var3 - playerViewRadius; var5 <= var3 + playerViewRadius; var5++)
      {
        getPlayerInstance(var4, var5, true).addPlayer(p_72683_1_);
      }
    }
    
    players.add(p_72683_1_);
    filterChunkLoadQueue(p_72683_1_);
  }
  



  public void filterChunkLoadQueue(EntityPlayerMP p_72691_1_)
  {
    ArrayList var2 = Lists.newArrayList(loadedChunks);
    int var3 = 0;
    int var4 = playerViewRadius;
    int var5 = (int)posX >> 4;
    int var6 = (int)posZ >> 4;
    int var7 = 0;
    int var8 = 0;
    ChunkCoordIntPair var9 = getPlayerInstancecurrentChunk;
    loadedChunks.clear();
    
    if (var2.contains(var9))
    {
      loadedChunks.add(var9);
    }
    


    for (int var10 = 1; var10 <= var4 * 2; var10++)
    {
      for (int var11 = 0; var11 < 2; var11++)
      {
        int[] var12 = xzDirectionsConst[(var3++ % 4)];
        
        for (int var13 = 0; var13 < var10; var13++)
        {
          var7 += var12[0];
          var8 += var12[1];
          var9 = getPlayerInstancecurrentChunk;
          
          if (var2.contains(var9))
          {
            loadedChunks.add(var9);
          }
        }
      }
    }
    
    var3 %= 4;
    
    for (var10 = 0; var10 < var4 * 2; var10++)
    {
      var7 += xzDirectionsConst[var3][0];
      var8 += xzDirectionsConst[var3][1];
      var9 = getPlayerInstancecurrentChunk;
      
      if (var2.contains(var9))
      {
        loadedChunks.add(var9);
      }
    }
  }
  



  public void removePlayer(EntityPlayerMP p_72695_1_)
  {
    int var2 = (int)managedPosX >> 4;
    int var3 = (int)managedPosZ >> 4;
    
    for (int var4 = var2 - playerViewRadius; var4 <= var2 + playerViewRadius; var4++)
    {
      for (int var5 = var3 - playerViewRadius; var5 <= var3 + playerViewRadius; var5++)
      {
        PlayerInstance var6 = getPlayerInstance(var4, var5, false);
        
        if (var6 != null)
        {
          var6.removePlayer(p_72695_1_);
        }
      }
    }
    
    players.remove(p_72695_1_);
  }
  




  private boolean overlaps(int p_72684_1_, int p_72684_2_, int p_72684_3_, int p_72684_4_, int p_72684_5_)
  {
    int var6 = p_72684_1_ - p_72684_3_;
    int var7 = p_72684_2_ - p_72684_4_;
    return (var7 >= -p_72684_5_) && (var7 <= p_72684_5_);
  }
  



  public void updateMountedMovingPlayer(EntityPlayerMP p_72685_1_)
  {
    int var2 = (int)posX >> 4;
    int var3 = (int)posZ >> 4;
    double var4 = managedPosX - posX;
    double var6 = managedPosZ - posZ;
    double var8 = var4 * var4 + var6 * var6;
    
    if (var8 >= 64.0D)
    {
      int var10 = (int)managedPosX >> 4;
      int var11 = (int)managedPosZ >> 4;
      int var12 = playerViewRadius;
      int var13 = var2 - var10;
      int var14 = var3 - var11;
      
      if ((var13 != 0) || (var14 != 0))
      {
        for (int var15 = var2 - var12; var15 <= var2 + var12; var15++)
        {
          for (int var16 = var3 - var12; var16 <= var3 + var12; var16++)
          {
            if (!overlaps(var15, var16, var10, var11, var12))
            {
              getPlayerInstance(var15, var16, true).addPlayer(p_72685_1_);
            }
            
            if (!overlaps(var15 - var13, var16 - var14, var2, var3, var12))
            {
              PlayerInstance var17 = getPlayerInstance(var15 - var13, var16 - var14, false);
              
              if (var17 != null)
              {
                var17.removePlayer(p_72685_1_);
              }
            }
          }
        }
        
        filterChunkLoadQueue(p_72685_1_);
        managedPosX = posX;
        managedPosZ = posZ;
      }
    }
  }
  
  public boolean isPlayerWatchingChunk(EntityPlayerMP p_72694_1_, int p_72694_2_, int p_72694_3_)
  {
    PlayerInstance var4 = getPlayerInstance(p_72694_2_, p_72694_3_, false);
    return (var4 != null) && (playersWatchingChunk.contains(p_72694_1_)) && (!loadedChunks.contains(currentChunk));
  }
  
  public void func_152622_a(int p_152622_1_)
  {
    p_152622_1_ = net.minecraft.util.MathHelper.clamp_int(p_152622_1_, 3, 32);
    
    if (p_152622_1_ != playerViewRadius)
    {
      int var2 = p_152622_1_ - playerViewRadius;
      ArrayList var3 = Lists.newArrayList(players);
      Iterator var4 = var3.iterator();
      
      while (var4.hasNext())
      {
        EntityPlayerMP var5 = (EntityPlayerMP)var4.next();
        int var6 = (int)posX >> 4;
        int var7 = (int)posZ >> 4;
        


        if (var2 > 0)
        {
          for (int var8 = var6 - p_152622_1_; var8 <= var6 + p_152622_1_; var8++)
          {
            for (int var9 = var7 - p_152622_1_; var9 <= var7 + p_152622_1_; var9++)
            {
              PlayerInstance var10 = getPlayerInstance(var8, var9, true);
              
              if (!playersWatchingChunk.contains(var5))
              {
                var10.addPlayer(var5);
              }
              
            }
            
          }
        } else {
          for (int var8 = var6 - playerViewRadius; var8 <= var6 + playerViewRadius; var8++)
          {
            for (int var9 = var7 - playerViewRadius; var9 <= var7 + playerViewRadius; var9++)
            {
              if (!overlaps(var8, var9, var6, var7, p_152622_1_))
              {
                getPlayerInstance(var8, var9, true).removePlayer(var5);
              }
            }
          }
        }
      }
      
      playerViewRadius = p_152622_1_;
    }
  }
  



  public static int getFurthestViewableBlock(int p_72686_0_)
  {
    return p_72686_0_ * 16 - 16;
  }
  
  class PlayerInstance
  {
    private final List playersWatchingChunk = Lists.newArrayList();
    private final ChunkCoordIntPair currentChunk;
    private short[] locationOfBlockChange = new short[64];
    private int numBlocksToUpdate;
    private int flagsYAreasToUpdate;
    private long previousWorldTime;
    private static final String __OBFID = "CL_00001435";
    
    public PlayerInstance(int p_i1518_2_, int p_i1518_3_)
    {
      currentChunk = new ChunkCoordIntPair(p_i1518_2_, p_i1518_3_);
      getMinecraftServer().theChunkProviderServer.loadChunk(p_i1518_2_, p_i1518_3_);
    }
    
    public void addPlayer(EntityPlayerMP p_73255_1_)
    {
      if (playersWatchingChunk.contains(p_73255_1_))
      {
        PlayerManager.field_152627_a.debug("Failed to add player. {} already is in chunk {}, {}", new Object[] { p_73255_1_, Integer.valueOf(currentChunk.chunkXPos), Integer.valueOf(currentChunk.chunkZPos) });
      }
      else
      {
        if (playersWatchingChunk.isEmpty())
        {
          previousWorldTime = theWorldServer.getTotalWorldTime();
        }
        
        playersWatchingChunk.add(p_73255_1_);
        loadedChunks.add(currentChunk);
      }
    }
    
    public void removePlayer(EntityPlayerMP p_73252_1_)
    {
      if (playersWatchingChunk.contains(p_73252_1_))
      {
        Chunk var2 = theWorldServer.getChunkFromChunkCoords(currentChunk.chunkXPos, currentChunk.chunkZPos);
        
        if (var2.isPopulated())
        {
          playerNetServerHandler.sendPacket(new S21PacketChunkData(var2, true, 0));
        }
        
        playersWatchingChunk.remove(p_73252_1_);
        loadedChunks.remove(currentChunk);
        
        if (playersWatchingChunk.isEmpty())
        {
          long var3 = currentChunk.chunkXPos + 2147483647L | currentChunk.chunkZPos + 2147483647L << 32;
          increaseInhabitedTime(var2);
          playerInstances.remove(var3);
          playerInstanceList.remove(this);
          
          if (numBlocksToUpdate > 0)
          {
            playerInstancesToUpdate.remove(this);
          }
          
          getMinecraftServer().theChunkProviderServer.dropChunk(currentChunk.chunkXPos, currentChunk.chunkZPos);
        }
      }
    }
    
    public void processChunk()
    {
      increaseInhabitedTime(theWorldServer.getChunkFromChunkCoords(currentChunk.chunkXPos, currentChunk.chunkZPos));
    }
    
    private void increaseInhabitedTime(Chunk p_111196_1_)
    {
      p_111196_1_.setInhabitedTime(p_111196_1_.getInhabitedTime() + theWorldServer.getTotalWorldTime() - previousWorldTime);
      previousWorldTime = theWorldServer.getTotalWorldTime();
    }
    
    public void flagChunkForUpdate(int p_151253_1_, int p_151253_2_, int p_151253_3_)
    {
      if (numBlocksToUpdate == 0)
      {
        playerInstancesToUpdate.add(this);
      }
      
      flagsYAreasToUpdate |= 1 << (p_151253_2_ >> 4);
      
      if (numBlocksToUpdate < 64)
      {
        short var4 = (short)(p_151253_1_ << 12 | p_151253_3_ << 8 | p_151253_2_);
        
        for (int var5 = 0; var5 < numBlocksToUpdate; var5++)
        {
          if (locationOfBlockChange[var5] == var4)
          {
            return;
          }
        }
        
        locationOfBlockChange[(numBlocksToUpdate++)] = var4;
      }
    }
    
    public void sendToAllPlayersWatchingChunk(Packet p_151251_1_)
    {
      for (int var2 = 0; var2 < playersWatchingChunk.size(); var2++)
      {
        EntityPlayerMP var3 = (EntityPlayerMP)playersWatchingChunk.get(var2);
        
        if (!loadedChunks.contains(currentChunk))
        {
          playerNetServerHandler.sendPacket(p_151251_1_);
        }
      }
    }
    
    public void onUpdate()
    {
      if (numBlocksToUpdate != 0)
      {




        if (numBlocksToUpdate == 1)
        {
          int var1 = (locationOfBlockChange[0] >> 12 & 0xF) + currentChunk.chunkXPos * 16;
          int var2 = locationOfBlockChange[0] & 0xFF;
          int var3 = (locationOfBlockChange[0] >> 8 & 0xF) + currentChunk.chunkZPos * 16;
          BlockPos var4 = new BlockPos(var1, var2, var3);
          sendToAllPlayersWatchingChunk(new net.minecraft.network.play.server.S23PacketBlockChange(theWorldServer, var4));
          
          if (theWorldServer.getBlockState(var4).getBlock().hasTileEntity())
          {
            sendTileToAllPlayersWatchingChunk(theWorldServer.getTileEntity(var4));

          }
          


        }
        else if (numBlocksToUpdate == 64)
        {
          int var1 = currentChunk.chunkXPos * 16;
          int var2 = currentChunk.chunkZPos * 16;
          sendToAllPlayersWatchingChunk(new S21PacketChunkData(theWorldServer.getChunkFromChunkCoords(currentChunk.chunkXPos, currentChunk.chunkZPos), false, flagsYAreasToUpdate));
          
          for (int var3 = 0; var3 < 16; var3++)
          {
            if ((flagsYAreasToUpdate & 1 << var3) != 0)
            {
              int var7 = var3 << 4;
              List var5 = theWorldServer.func_147486_a(var1, var7, var2, var1 + 16, var7 + 16, var2 + 16);
              
              for (int var6 = 0; var6 < var5.size(); var6++)
              {
                sendTileToAllPlayersWatchingChunk((TileEntity)var5.get(var6));
              }
            }
          }
        }
        else
        {
          sendToAllPlayersWatchingChunk(new net.minecraft.network.play.server.S22PacketMultiBlockChange(numBlocksToUpdate, locationOfBlockChange, theWorldServer.getChunkFromChunkCoords(currentChunk.chunkXPos, currentChunk.chunkZPos)));
          
          for (int var1 = 0; var1 < numBlocksToUpdate; var1++)
          {
            int var2 = (locationOfBlockChange[var1] >> 12 & 0xF) + currentChunk.chunkXPos * 16;
            int var3 = locationOfBlockChange[var1] & 0xFF;
            int var7 = (locationOfBlockChange[var1] >> 8 & 0xF) + currentChunk.chunkZPos * 16;
            BlockPos var8 = new BlockPos(var2, var3, var7);
            
            if (theWorldServer.getBlockState(var8).getBlock().hasTileEntity())
            {
              sendTileToAllPlayersWatchingChunk(theWorldServer.getTileEntity(var8));
            }
          }
        }
        

        numBlocksToUpdate = 0;
        flagsYAreasToUpdate = 0;
      }
    }
    
    private void sendTileToAllPlayersWatchingChunk(TileEntity p_151252_1_)
    {
      if (p_151252_1_ != null)
      {
        Packet var2 = p_151252_1_.getDescriptionPacket();
        
        if (var2 != null)
        {
          sendToAllPlayersWatchingChunk(var2);
        }
      }
    }
  }
}
