package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSoundMinecart;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EntityFireworkStarterFX;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.src.BlockPosM;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.SaveHandlerMP;
import net.minecraft.world.storage.WorldInfo;

public class WorldClient extends World
{
  private NetHandlerPlayClient sendQueue;
  private ChunkProviderClient clientChunkProvider;
  private final Set entityList = Sets.newHashSet();
  




  private final Set entitySpawnQueue = Sets.newHashSet();
  private final Minecraft mc = Minecraft.getMinecraft();
  private final Set previousActiveChunkSet = Sets.newHashSet();
  private static final String __OBFID = "CL_00000882";
  
  public WorldClient(NetHandlerPlayClient p_i45063_1_, WorldSettings p_i45063_2_, int p_i45063_3_, EnumDifficulty p_i45063_4_, Profiler p_i45063_5_)
  {
    super(new SaveHandlerMP(), new WorldInfo(p_i45063_2_, "MpServer"), WorldProvider.getProviderForDimension(p_i45063_3_), p_i45063_5_, true);
    sendQueue = p_i45063_1_;
    getWorldInfo().setDifficulty(p_i45063_4_);
    setSpawnLocation(new BlockPos(8, 64, 8));
    provider.registerWorld(this);
    chunkProvider = createChunkProvider();
    mapStorage = new net.minecraft.world.storage.SaveDataMemoryStorage();
    calculateInitialSkylight();
    calculateInitialWeather();
  }
  



  public void tick()
  {
    super.tick();
    func_82738_a(getTotalWorldTime() + 1L);
    
    if (getGameRules().getGameRuleBooleanValue("doDaylightCycle"))
    {
      setWorldTime(getWorldTime() + 1L);
    }
    
    theProfiler.startSection("reEntryProcessing");
    
    for (int var1 = 0; (var1 < 10) && (!entitySpawnQueue.isEmpty()); var1++)
    {
      Entity var2 = (Entity)entitySpawnQueue.iterator().next();
      entitySpawnQueue.remove(var2);
      
      if (!loadedEntityList.contains(var2))
      {
        spawnEntityInWorld(var2);
      }
    }
    
    theProfiler.endStartSection("chunkCache");
    clientChunkProvider.unloadQueuedChunks();
    theProfiler.endStartSection("blocks");
    func_147456_g();
    theProfiler.endSection();
  }
  




  public void invalidateBlockReceiveRegion(int p_73031_1_, int p_73031_2_, int p_73031_3_, int p_73031_4_, int p_73031_5_, int p_73031_6_) {}
  



  protected IChunkProvider createChunkProvider()
  {
    clientChunkProvider = new ChunkProviderClient(this);
    return clientChunkProvider;
  }
  
  protected void func_147456_g()
  {
    super.func_147456_g();
    previousActiveChunkSet.retainAll(activeChunkSet);
    
    if (previousActiveChunkSet.size() == activeChunkSet.size())
    {
      previousActiveChunkSet.clear();
    }
    
    int var1 = 0;
    Iterator var2 = activeChunkSet.iterator();
    
    while (var2.hasNext())
    {
      ChunkCoordIntPair var3 = (ChunkCoordIntPair)var2.next();
      
      if (!previousActiveChunkSet.contains(var3))
      {
        int var4 = chunkXPos * 16;
        int var5 = chunkZPos * 16;
        theProfiler.startSection("getChunk");
        Chunk var6 = getChunkFromChunkCoords(chunkXPos, chunkZPos);
        func_147467_a(var4, var5, var6);
        theProfiler.endSection();
        previousActiveChunkSet.add(var3);
        var1++;
        
        if (var1 >= 10)
        {
          return;
        }
      }
    }
  }
  
  public void doPreChunk(int p_73025_1_, int p_73025_2_, boolean p_73025_3_)
  {
    if (p_73025_3_)
    {
      clientChunkProvider.loadChunk(p_73025_1_, p_73025_2_);
    }
    else
    {
      clientChunkProvider.unloadChunk(p_73025_1_, p_73025_2_);
    }
    
    if (!p_73025_3_)
    {
      markBlockRangeForRenderUpdate(p_73025_1_ * 16, 0, p_73025_2_ * 16, p_73025_1_ * 16 + 15, 256, p_73025_2_ * 16 + 15);
    }
  }
  



  public boolean spawnEntityInWorld(Entity p_72838_1_)
  {
    boolean var2 = super.spawnEntityInWorld(p_72838_1_);
    entityList.add(p_72838_1_);
    
    if (!var2)
    {
      entitySpawnQueue.add(p_72838_1_);
    }
    else if ((p_72838_1_ instanceof EntityMinecart))
    {
      mc.getSoundHandler().playSound(new MovingSoundMinecart((EntityMinecart)p_72838_1_));
    }
    
    return var2;
  }
  



  public void removeEntity(Entity p_72900_1_)
  {
    super.removeEntity(p_72900_1_);
    entityList.remove(p_72900_1_);
  }
  
  protected void onEntityAdded(Entity p_72923_1_)
  {
    super.onEntityAdded(p_72923_1_);
    
    if (entitySpawnQueue.contains(p_72923_1_))
    {
      entitySpawnQueue.remove(p_72923_1_);
    }
  }
  
  protected void onEntityRemoved(Entity p_72847_1_)
  {
    super.onEntityRemoved(p_72847_1_);
    boolean var2 = false;
    
    if (entityList.contains(p_72847_1_))
    {
      if (p_72847_1_.isEntityAlive())
      {
        entitySpawnQueue.add(p_72847_1_);
        var2 = true;
      }
      else
      {
        entityList.remove(p_72847_1_);
      }
    }
  }
  



  public void addEntityToWorld(int p_73027_1_, Entity p_73027_2_)
  {
    Entity var3 = getEntityByID(p_73027_1_);
    
    if (var3 != null)
    {
      removeEntity(var3);
    }
    
    entityList.add(p_73027_2_);
    p_73027_2_.setEntityId(p_73027_1_);
    
    if (!spawnEntityInWorld(p_73027_2_))
    {
      entitySpawnQueue.add(p_73027_2_);
    }
    
    entitiesById.addKey(p_73027_1_, p_73027_2_);
  }
  



  public Entity getEntityByID(int p_73045_1_)
  {
    return p_73045_1_ == Minecraft.thePlayer.getEntityId() ? Minecraft.thePlayer : super.getEntityByID(p_73045_1_);
  }
  
  public Entity removeEntityFromWorld(int p_73028_1_)
  {
    Entity var2 = (Entity)entitiesById.removeObject(p_73028_1_);
    
    if (var2 != null)
    {
      entityList.remove(var2);
      removeEntity(var2);
    }
    
    return var2;
  }
  
  public boolean func_180503_b(BlockPos p_180503_1_, IBlockState p_180503_2_)
  {
    int var3 = p_180503_1_.getX();
    int var4 = p_180503_1_.getY();
    int var5 = p_180503_1_.getZ();
    invalidateBlockReceiveRegion(var3, var4, var5, var3, var4, var5);
    return super.setBlockState(p_180503_1_, p_180503_2_, 3);
  }
  



  public void sendQuittingDisconnectingPacket()
  {
    sendQueue.getNetworkManager().closeChannel(new ChatComponentText("Quitting"));
  }
  


  protected void updateWeather() {}
  

  protected int getRenderDistanceChunks()
  {
    return gameSettingsrenderDistanceChunks;
  }
  
  public void doVoidFogParticles(int p_73029_1_, int p_73029_2_, int p_73029_3_)
  {
    byte var4 = 16;
    Random var5 = new Random();
    ItemStack var6 = Minecraft.thePlayer.getHeldItem();
    boolean var7 = (Minecraft.playerController.func_178889_l() == WorldSettings.GameType.CREATIVE) && (var6 != null) && (Block.getBlockFromItem(var6.getItem()) == Blocks.barrier);
    BlockPosM blockPosM = new BlockPosM(0, 0, 0, 3);
    
    for (int var8 = 0; var8 < 1000; var8++)
    {
      int var9 = p_73029_1_ + rand.nextInt(var4) - rand.nextInt(var4);
      int var10 = p_73029_2_ + rand.nextInt(var4) - rand.nextInt(var4);
      int var11 = p_73029_3_ + rand.nextInt(var4) - rand.nextInt(var4);
      blockPosM.setXyz(var9, var10, var11);
      IBlockState var13 = getBlockState(blockPosM);
      var13.getBlock().randomDisplayTick(this, blockPosM, var13, var5);
      
      if ((var7) && (var13.getBlock() == Blocks.barrier))
      {
        spawnParticle(EnumParticleTypes.BARRIER, var9 + 0.5F, var10 + 0.5F, var11 + 0.5F, 0.0D, 0.0D, 0.0D, new int[0]);
      }
    }
  }
  



  public void removeAllEntities()
  {
    loadedEntityList.removeAll(unloadedEntityList);
    




    for (int var1 = 0; var1 < unloadedEntityList.size(); var1++)
    {
      Entity var2 = (Entity)unloadedEntityList.get(var1);
      int var3 = chunkCoordX;
      int var4 = chunkCoordZ;
      
      if ((addedToChunk) && (isChunkLoaded(var3, var4, true)))
      {
        getChunkFromChunkCoords(var3, var4).removeEntity(var2);
      }
    }
    
    for (var1 = 0; var1 < unloadedEntityList.size(); var1++)
    {
      onEntityRemoved((Entity)unloadedEntityList.get(var1));
    }
    
    unloadedEntityList.clear();
    
    for (var1 = 0; var1 < loadedEntityList.size(); var1++)
    {
      Entity var2 = (Entity)loadedEntityList.get(var1);
      
      if (ridingEntity != null)
      {
        if ((ridingEntity.isDead) || (ridingEntity.riddenByEntity != var2))
        {



          ridingEntity.riddenByEntity = null;
          ridingEntity = null;
        }
      }
      else if (isDead)
      {
        int var3 = chunkCoordX;
        int var4 = chunkCoordZ;
        
        if ((addedToChunk) && (isChunkLoaded(var3, var4, true)))
        {
          getChunkFromChunkCoords(var3, var4).removeEntity(var2);
        }
        
        loadedEntityList.remove(var1--);
        onEntityRemoved(var2);
      }
    }
  }
  



  public CrashReportCategory addWorldInfoToCrashReport(CrashReport report)
  {
    CrashReportCategory var2 = super.addWorldInfoToCrashReport(report);
    var2.addCrashSectionCallable("Forced entities", new Callable()
    {
      private static final String __OBFID = "CL_00000883";
      
      public String call() {
        return entityList.size() + " total; " + entityList.toString();
      }
    });
    var2.addCrashSectionCallable("Retry entities", new Callable()
    {
      private static final String __OBFID = "CL_00000884";
      
      public String call() {
        return entitySpawnQueue.size() + " total; " + entitySpawnQueue.toString();
      }
    });
    var2.addCrashSectionCallable("Server brand", new Callable()
    {
      private static final String __OBFID = "CL_00000885";
      
      public String call() {
        return Minecraft.thePlayer.getClientBrand();
      }
    });
    var2.addCrashSectionCallable("Server type", new Callable()
    {
      private static final String __OBFID = "CL_00000886";
      
      public String call() {
        return mc.getIntegratedServer() == null ? "Non-integrated multiplayer server" : "Integrated singleplayer server";
      }
    });
    return var2;
  }
  
  public void func_175731_a(BlockPos p_175731_1_, String p_175731_2_, float p_175731_3_, float p_175731_4_, boolean p_175731_5_)
  {
    playSound(p_175731_1_.getX() + 0.5D, p_175731_1_.getY() + 0.5D, p_175731_1_.getZ() + 0.5D, p_175731_2_, p_175731_3_, p_175731_4_, p_175731_5_);
  }
  



  public void playSound(double x, double y, double z, String soundName, float volume, float pitch, boolean distanceDelay)
  {
    double var11 = mc.func_175606_aa().getDistanceSq(x, y, z);
    PositionedSoundRecord var13 = new PositionedSoundRecord(new ResourceLocation(soundName), volume, pitch, (float)x, (float)y, (float)z);
    
    if ((distanceDelay) && (var11 > 100.0D))
    {
      double var14 = Math.sqrt(var11) / 40.0D;
      mc.getSoundHandler().playDelayedSound(var13, (int)(var14 * 20.0D));
    }
    else
    {
      mc.getSoundHandler().playSound(var13);
    }
  }
  
  public void makeFireworks(double x, double y, double z, double motionX, double motionY, double motionZ, NBTTagCompound compund)
  {
    mc.effectRenderer.addEffect(new EntityFireworkStarterFX(this, x, y, z, motionX, motionY, motionZ, mc.effectRenderer, compund));
  }
  
  public void setWorldScoreboard(Scoreboard p_96443_1_)
  {
    worldScoreboard = p_96443_1_;
  }
  



  public void setWorldTime(long time)
  {
    if (time < 0L)
    {
      time = -time;
      getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
    }
    else
    {
      getGameRules().setOrCreateGameRule("doDaylightCycle", "true");
    }
    
    super.setWorldTime(time);
  }
  
  public List getCollidingBoundingBoxes(Entity entityIn, AxisAlignedBB bb, boolean blocksOnly)
  {
    if (blocksOnly)
    {
      ArrayList var3 = Lists.newArrayList();
      int var4 = MathHelper.floor_double(minX);
      int var5 = MathHelper.floor_double(maxX + 1.0D);
      int var6 = MathHelper.floor_double(minY);
      int var7 = MathHelper.floor_double(maxY + 1.0D);
      int var8 = MathHelper.floor_double(minZ);
      int var9 = MathHelper.floor_double(maxZ + 1.0D);
      for (int var10 = var4; var10 < var5; var10++) {
        for (int var11 = var8; var11 < var9; var11++) {
          if (isBlockLoaded(new BlockPos(var10, 64, var11))) {
            for (int var12 = var6 - 1; var12 < var7; var12++)
            {
              BlockPos var13 = new BlockPos(var10, var12, var11);
              boolean var14 = entityIn.isOutsideBorder();
              boolean var15 = isInsideBorder(getWorldBorder(), entityIn);
              if ((var14) && (var15)) {
                entityIn.setOutsideBorder(false);
              } else if ((!var14) && (!var15)) {
                entityIn.setOutsideBorder(true);
              }
              IBlockState var16;
              IBlockState var16;
              if ((!getWorldBorder().contains(var13)) && (var15)) {
                var16 = Blocks.stone.getDefaultState();
              } else {
                var16 = getBlockState(var13);
              }
              var16.getBlock().addCollisionBoxesToList(this, var13, var16, bb, var3, entityIn);
            }
          }
        }
      }
      return var3;
    }
    return null;
  }
}
