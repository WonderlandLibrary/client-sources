package net.minecraft.world;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEventData;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.INpc;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.ScoreboardSaveData;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.village.VillageCollection;
import net.minecraft.village.VillageSiege;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.feature.WorldGeneratorBonusChest;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldServer
  extends World
  implements IThreadListener
{
  private static final Logger logger = ;
  private final MinecraftServer mcServer;
  private final EntityTracker theEntityTracker;
  private final PlayerManager thePlayerManager;
  private final Set pendingTickListEntriesHashSet = Sets.newHashSet();
  private final TreeSet pendingTickListEntriesTreeSet = new TreeSet();
  private final Map entitiesByUuid = Maps.newHashMap();
  public ChunkProviderServer theChunkProviderServer;
  public boolean disableLevelSaving;
  private boolean allPlayersSleeping;
  private int updateEntityTick;
  private final Teleporter worldTeleporter;
  private final SpawnerAnimals field_175742_R = new SpawnerAnimals();
  protected final VillageSiege villageSiege = new VillageSiege(this);
  private ServerBlockEventList[] field_147490_S = { new ServerBlockEventList(null), new ServerBlockEventList(null) };
  private int blockEventCacheIndex;
  private static final List bonusChestContent = Lists.newArrayList(new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.stick, 0, 1, 3, 10), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.planks), 0, 1, 3, 10), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log), 0, 1, 3, 10), new WeightedRandomChestContent(Items.stone_axe, 0, 1, 1, 3), new WeightedRandomChestContent(Items.wooden_axe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.stone_pickaxe, 0, 1, 1, 3), new WeightedRandomChestContent(Items.wooden_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.apple, 0, 2, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 2, 3, 3), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log2), 0, 1, 3, 10) });
  private List pendingTickListEntriesThisTick = Lists.newArrayList();
  private static final String __OBFID = "CL_00001437";
  
  public WorldServer(MinecraftServer server, ISaveHandler saveHandlerIn, WorldInfo info, int dimensionId, Profiler profilerIn)
  {
    super(saveHandlerIn, info, WorldProvider.getProviderForDimension(dimensionId), profilerIn, false);
    this.mcServer = server;
    this.theEntityTracker = new EntityTracker(this);
    this.thePlayerManager = new PlayerManager(this);
    this.provider.registerWorld(this);
    this.chunkProvider = createChunkProvider();
    this.worldTeleporter = new Teleporter(this);
    calculateInitialSkylight();
    calculateInitialWeather();
    getWorldBorder().setSize(server.getMaxWorldSize());
  }
  
  public World init()
  {
    this.mapStorage = new MapStorage(this.saveHandler);
    String var1 = VillageCollection.func_176062_a(this.provider);
    VillageCollection var2 = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, var1);
    if (var2 == null)
    {
      this.villageCollectionObj = new VillageCollection(this);
      this.mapStorage.setData(var1, this.villageCollectionObj);
    }
    else
    {
      this.villageCollectionObj = var2;
      this.villageCollectionObj.func_82566_a(this);
    }
    this.worldScoreboard = new ServerScoreboard(this.mcServer);
    ScoreboardSaveData var3 = (ScoreboardSaveData)this.mapStorage.loadData(ScoreboardSaveData.class, "scoreboard");
    if (var3 == null)
    {
      var3 = new ScoreboardSaveData();
      this.mapStorage.setData("scoreboard", var3);
    }
    var3.func_96499_a(this.worldScoreboard);
    ((ServerScoreboard)this.worldScoreboard).func_96547_a(var3);
    getWorldBorder().setCenter(this.worldInfo.func_176120_C(), this.worldInfo.func_176126_D());
    getWorldBorder().func_177744_c(this.worldInfo.func_176140_I());
    getWorldBorder().setDamageBuffer(this.worldInfo.func_176138_H());
    getWorldBorder().setWarningDistance(this.worldInfo.func_176131_J());
    getWorldBorder().setWarningTime(this.worldInfo.func_176139_K());
    if (this.worldInfo.func_176134_F() > 0L) {
      getWorldBorder().setTransition(this.worldInfo.func_176137_E(), this.worldInfo.func_176132_G(), this.worldInfo.func_176134_F());
    } else {
      getWorldBorder().setTransition(this.worldInfo.func_176137_E());
    }
    return this;
  }
  
  public void tick()
  {
    super.tick();
    if ((getWorldInfo().isHardcoreModeEnabled()) && (getDifficulty() != EnumDifficulty.HARD)) {
      getWorldInfo().setDifficulty(EnumDifficulty.HARD);
    }
    this.provider.getWorldChunkManager().cleanupCache();
    if (areAllPlayersAsleep())
    {
      if (getGameRules().getGameRuleBooleanValue("doDaylightCycle"))
      {
        long var1 = this.worldInfo.getWorldTime() + 24000L;
        this.worldInfo.setWorldTime(var1 - var1 % 24000L);
      }
      wakeAllPlayers();
    }
    this.theProfiler.startSection("mobSpawner");
    if ((getGameRules().getGameRuleBooleanValue("doMobSpawning")) && (this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD)) {
      this.field_175742_R.findChunksForSpawning(this, this.spawnHostileMobs, this.spawnPeacefulMobs, this.worldInfo.getWorldTotalTime() % 400L == 0L);
    }
    this.theProfiler.endStartSection("chunkSource");
    this.chunkProvider.unloadQueuedChunks();
    int var3 = calculateSkylightSubtracted(1.0F);
    if (var3 != getSkylightSubtracted()) {
      setSkylightSubtracted(var3);
    }
    this.worldInfo.incrementTotalWorldTime(this.worldInfo.getWorldTotalTime() + 1L);
    if (getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
      this.worldInfo.setWorldTime(this.worldInfo.getWorldTime() + 1L);
    }
    this.theProfiler.endStartSection("tickPending");
    tickUpdates(false);
    this.theProfiler.endStartSection("tickBlocks");
    func_147456_g();
    this.theProfiler.endStartSection("chunkMap");
    this.thePlayerManager.updatePlayerInstances();
    this.theProfiler.endStartSection("village");
    this.villageCollectionObj.tick();
    this.villageSiege.tick();
    this.theProfiler.endStartSection("portalForcer");
    this.worldTeleporter.removeStalePortalLocations(getTotalWorldTime());
    this.theProfiler.endSection();
    func_147488_Z();
  }
  
  public BiomeGenBase.SpawnListEntry func_175734_a(EnumCreatureType p_175734_1_, BlockPos p_175734_2_)
  {
    List var3 = getChunkProvider().func_177458_a(p_175734_1_, p_175734_2_);
    return (var3 != null) && (!var3.isEmpty()) ? (BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(this.rand, var3) : null;
  }
  
  public boolean func_175732_a(EnumCreatureType p_175732_1_, BiomeGenBase.SpawnListEntry p_175732_2_, BlockPos p_175732_3_)
  {
    List var4 = getChunkProvider().func_177458_a(p_175732_1_, p_175732_3_);
    return (var4 != null) && (!var4.isEmpty()) ? var4.contains(p_175732_2_) : false;
  }
  
  public void updateAllPlayersSleepingFlag()
  {
    this.allPlayersSleeping = false;
    if (!this.playerEntities.isEmpty())
    {
      int var1 = 0;
      int var2 = 0;
      Iterator var3 = this.playerEntities.iterator();
      while (var3.hasNext())
      {
        EntityPlayer var4 = (EntityPlayer)var3.next();
        if (var4.func_175149_v()) {
          var1++;
        } else if (var4.isPlayerSleeping()) {
          var2++;
        }
      }
      this.allPlayersSleeping = ((var2 > 0) && (var2 >= this.playerEntities.size() - var1));
    }
  }
  
  protected void wakeAllPlayers()
  {
    this.allPlayersSleeping = false;
    Iterator var1 = this.playerEntities.iterator();
    while (var1.hasNext())
    {
      EntityPlayer var2 = (EntityPlayer)var1.next();
      if (var2.isPlayerSleeping()) {
        var2.wakeUpPlayer(false, false, true);
      }
    }
    resetRainAndThunder();
  }
  
  private void resetRainAndThunder()
  {
    this.worldInfo.setRainTime(0);
    this.worldInfo.setRaining(false);
    this.worldInfo.setThunderTime(0);
    this.worldInfo.setThundering(false);
  }
  
  public boolean areAllPlayersAsleep()
  {
    if ((this.allPlayersSleeping) && (!this.isRemote))
    {
      Iterator var1 = this.playerEntities.iterator();
      EntityPlayer var2;
      do
      {
        if (!var1.hasNext()) {
          return true;
        }
        var2 = (EntityPlayer)var1.next();
      } while ((!var2.func_175149_v()) && (var2.isPlayerFullyAsleep()));
      return false;
    }
    return false;
  }
  
  public void setInitialSpawnLocation()
  {
    if (this.worldInfo.getSpawnY() <= 0) {
      this.worldInfo.setSpawnY(64);
    }
    int var1 = this.worldInfo.getSpawnX();
    int var2 = this.worldInfo.getSpawnZ();
    int var3 = 0;
    while (getGroundAboveSeaLevel(new BlockPos(var1, 0, var2)).getMaterial() == Material.air)
    {
      var1 += this.rand.nextInt(8) - this.rand.nextInt(8);
      var2 += this.rand.nextInt(8) - this.rand.nextInt(8);
      var3++;
      if (var3 == 10000) {
        break;
      }
    }
    this.worldInfo.setSpawnX(var1);
    this.worldInfo.setSpawnZ(var2);
  }
  
  protected void func_147456_g()
  {
    super.func_147456_g();
    if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD)
    {
      Iterator var21 = this.activeChunkSet.iterator();
      while (var21.hasNext())
      {
        ChunkCoordIntPair var22 = (ChunkCoordIntPair)var21.next();
        getChunkFromChunkCoords(var22.chunkXPos, var22.chunkZPos).func_150804_b(false);
      }
    }
    else
    {
      int var1 = 0;
      int var2 = 0;
      for (Iterator var3 = this.activeChunkSet.iterator(); var3.hasNext(); this.theProfiler.endSection())
      {
        ChunkCoordIntPair var4 = (ChunkCoordIntPair)var3.next();
        int var5 = var4.chunkXPos * 16;
        int var6 = var4.chunkZPos * 16;
        this.theProfiler.startSection("getChunk");
        Chunk var7 = getChunkFromChunkCoords(var4.chunkXPos, var4.chunkZPos);
        func_147467_a(var5, var6, var7);
        this.theProfiler.endStartSection("tickChunk");
        var7.func_150804_b(false);
        this.theProfiler.endStartSection("thunder");
        if ((this.rand.nextInt(100000) == 0) && (isRaining()) && (isThundering()))
        {
          this.updateLCG = (this.updateLCG * 3 + 1013904223);
          int var8 = this.updateLCG >> 2;
          BlockPos var9 = func_175736_a(new BlockPos(var5 + (var8 & 0xF), 0, var6 + (var8 >> 8 & 0xF)));
          if (func_175727_C(var9)) {
            addWeatherEffect(new EntityLightningBolt(this, var9.getX(), var9.getY(), var9.getZ()));
          }
        }
        this.theProfiler.endStartSection("iceandsnow");
        if (this.rand.nextInt(16) == 0)
        {
          this.updateLCG = (this.updateLCG * 3 + 1013904223);
          int var8 = this.updateLCG >> 2;
          BlockPos var9 = func_175725_q(new BlockPos(var5 + (var8 & 0xF), 0, var6 + (var8 >> 8 & 0xF)));
          BlockPos var10 = var9.offsetDown();
          if (func_175662_w(var10)) {
            setBlockState(var10, Blocks.ice.getDefaultState());
          }
          if ((isRaining()) && (func_175708_f(var9, true))) {
            setBlockState(var9, Blocks.snow_layer.getDefaultState());
          }
          if ((isRaining()) && (getBiomeGenForCoords(var10).canSpawnLightningBolt())) {
            getBlockState(var10).getBlock().fillWithRain(this, var10);
          }
        }
        this.theProfiler.endStartSection("tickBlocks");
        int var8 = getGameRules().getInt("randomTickSpeed");
        if (var8 > 0)
        {
          ExtendedBlockStorage[] var23 = var7.getBlockStorageArray();
          int var24 = var23.length;
          for (int var11 = 0; var11 < var24; var11++)
          {
            ExtendedBlockStorage var12 = var23[var11];
            if ((var12 != null) && (var12.getNeedsRandomTick())) {
              for (int var13 = 0; var13 < var8; var13++)
              {
                this.updateLCG = (this.updateLCG * 3 + 1013904223);
                int var14 = this.updateLCG >> 2;
                int var15 = var14 & 0xF;
                int var16 = var14 >> 8 & 0xF;
                int var17 = var14 >> 16 & 0xF;
                var2++;
                BlockPos var18 = new BlockPos(var15 + var5, var17 + var12.getYLocation(), var16 + var6);
                IBlockState var19 = var12.get(var15, var17, var16);
                Block var20 = var19.getBlock();
                if (var20.getTickRandomly())
                {
                  var1++;
                  var20.randomTick(this, var18, var19, this.rand);
                }
              }
            }
          }
        }
      }
    }
  }
  
  protected BlockPos func_175736_a(BlockPos p_175736_1_)
  {
    BlockPos var2 = func_175725_q(p_175736_1_);
    AxisAlignedBB var3 = new AxisAlignedBB(var2, new BlockPos(var2.getX(), getHeight(), var2.getZ())).expand(3.0D, 3.0D, 3.0D);
    List var4 = func_175647_a(EntityLivingBase.class, var3, new Predicate()
    {
      private static final String __OBFID = "CL_00001889";
      
      public boolean func_180242_a(EntityLivingBase p_180242_1_)
      {
        return (p_180242_1_ != null) && (p_180242_1_.isEntityAlive()) && (WorldServer.this.isAgainstSky(p_180242_1_.getPosition()));
      }
      
      public boolean apply(Object p_apply_1_)
      {
        return func_180242_a((EntityLivingBase)p_apply_1_);
      }
    });
    return !var4.isEmpty() ? ((EntityLivingBase)var4.get(this.rand.nextInt(var4.size()))).getPosition() : var2;
  }
  
  public boolean isBlockTickPending(BlockPos pos, Block blockType)
  {
    NextTickListEntry var3 = new NextTickListEntry(pos, blockType);
    return this.pendingTickListEntriesThisTick.contains(var3);
  }
  
  public void scheduleUpdate(BlockPos pos, Block blockIn, int delay)
  {
    func_175654_a(pos, blockIn, delay, 0);
  }
  
  public void func_175654_a(BlockPos p_175654_1_, Block p_175654_2_, int p_175654_3_, int p_175654_4_)
  {
    NextTickListEntry var5 = new NextTickListEntry(p_175654_1_, p_175654_2_);
    byte var6 = 0;
    if ((this.scheduledUpdatesAreImmediate) && (p_175654_2_.getMaterial() != Material.air))
    {
      if (p_175654_2_.requiresUpdates())
      {
        var6 = 8;
        if (isAreaLoaded(var5.field_180282_a.add(-var6, -var6, -var6), var5.field_180282_a.add(var6, var6, var6)))
        {
          IBlockState var7 = getBlockState(var5.field_180282_a);
          if ((var7.getBlock().getMaterial() != Material.air) && (var7.getBlock() == var5.func_151351_a())) {
            var7.getBlock().updateTick(this, var5.field_180282_a, var7, this.rand);
          }
        }
        return;
      }
      p_175654_3_ = 1;
    }
    if (isAreaLoaded(p_175654_1_.add(-var6, -var6, -var6), p_175654_1_.add(var6, var6, var6)))
    {
      if (p_175654_2_.getMaterial() != Material.air)
      {
        var5.setScheduledTime(p_175654_3_ + this.worldInfo.getWorldTotalTime());
        var5.setPriority(p_175654_4_);
      }
      if (!this.pendingTickListEntriesHashSet.contains(var5))
      {
        this.pendingTickListEntriesHashSet.add(var5);
        this.pendingTickListEntriesTreeSet.add(var5);
      }
    }
  }
  
  public void func_180497_b(BlockPos p_180497_1_, Block p_180497_2_, int p_180497_3_, int p_180497_4_)
  {
    NextTickListEntry var5 = new NextTickListEntry(p_180497_1_, p_180497_2_);
    var5.setPriority(p_180497_4_);
    if (p_180497_2_.getMaterial() != Material.air) {
      var5.setScheduledTime(p_180497_3_ + this.worldInfo.getWorldTotalTime());
    }
    if (!this.pendingTickListEntriesHashSet.contains(var5))
    {
      this.pendingTickListEntriesHashSet.add(var5);
      this.pendingTickListEntriesTreeSet.add(var5);
    }
  }
  
  public void updateEntities()
  {
    if (this.playerEntities.isEmpty())
    {
      if (this.updateEntityTick++ < 1200) {}
    }
    else {
      resetUpdateEntityTick();
    }
    super.updateEntities();
  }
  
  public void resetUpdateEntityTick()
  {
    this.updateEntityTick = 0;
  }
  
  public boolean tickUpdates(boolean p_72955_1_)
  {
    if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
      return false;
    }
    int var2 = this.pendingTickListEntriesTreeSet.size();
    if (var2 != this.pendingTickListEntriesHashSet.size()) {
      throw new IllegalStateException("TickNextTick list out of synch");
    }
    if (var2 > 1000) {
      var2 = 1000;
    }
    this.theProfiler.startSection("cleaning");
    for (int var3 = 0; var3 < var2; var3++)
    {
      NextTickListEntry var4 = (NextTickListEntry)this.pendingTickListEntriesTreeSet.first();
      if ((!p_72955_1_) && (var4.scheduledTime > this.worldInfo.getWorldTotalTime())) {
        break;
      }
      this.pendingTickListEntriesTreeSet.remove(var4);
      this.pendingTickListEntriesHashSet.remove(var4);
      this.pendingTickListEntriesThisTick.add(var4);
    }
    this.theProfiler.endSection();
    this.theProfiler.startSection("ticking");
    Iterator var11 = this.pendingTickListEntriesThisTick.iterator();
    while (var11.hasNext())
    {
      NextTickListEntry var4 = (NextTickListEntry)var11.next();
      var11.remove();
      byte var5 = 0;
      if (isAreaLoaded(var4.field_180282_a.add(-var5, -var5, -var5), var4.field_180282_a.add(var5, var5, var5)))
      {
        IBlockState var6 = getBlockState(var4.field_180282_a);
        if ((var6.getBlock().getMaterial() != Material.air) && (Block.isEqualTo(var6.getBlock(), var4.func_151351_a()))) {
          try
          {
            var6.getBlock().updateTick(this, var4.field_180282_a, var6, this.rand);
          }
          catch (Throwable var10)
          {
            CrashReport var8 = CrashReport.makeCrashReport(var10, "Exception while ticking a block");
            CrashReportCategory var9 = var8.makeCategory("Block being ticked");
            CrashReportCategory.addBlockInfo(var9, var4.field_180282_a, var6);
            throw new ReportedException(var8);
          }
        }
      }
      else
      {
        scheduleUpdate(var4.field_180282_a, var4.func_151351_a(), 0);
      }
    }
    this.theProfiler.endSection();
    this.pendingTickListEntriesThisTick.clear();
    return !this.pendingTickListEntriesTreeSet.isEmpty();
  }
  
  public List getPendingBlockUpdates(Chunk p_72920_1_, boolean p_72920_2_)
  {
    ChunkCoordIntPair var3 = p_72920_1_.getChunkCoordIntPair();
    int var4 = (var3.chunkXPos << 4) - 2;
    int var5 = var4 + 16 + 2;
    int var6 = (var3.chunkZPos << 4) - 2;
    int var7 = var6 + 16 + 2;
    return func_175712_a(new StructureBoundingBox(var4, 0, var6, var5, 256, var7), p_72920_2_);
  }
  
  public List func_175712_a(StructureBoundingBox p_175712_1_, boolean p_175712_2_)
  {
    ArrayList var3 = null;
    for (int var4 = 0; var4 < 2; var4++)
    {
      Iterator var5;
      Iterator var5;
      if (var4 == 0)
      {
        var5 = this.pendingTickListEntriesTreeSet.iterator();
      }
      else
      {
        var5 = this.pendingTickListEntriesThisTick.iterator();
        if (!this.pendingTickListEntriesThisTick.isEmpty()) {
          logger.debug("toBeTicked = " + this.pendingTickListEntriesThisTick.size());
        }
      }
      while (var5.hasNext())
      {
        NextTickListEntry var6 = (NextTickListEntry)var5.next();
        BlockPos var7 = var6.field_180282_a;
        if ((var7.getX() >= p_175712_1_.minX) && (var7.getX() < p_175712_1_.maxX) && (var7.getZ() >= p_175712_1_.minZ) && (var7.getZ() < p_175712_1_.maxZ))
        {
          if (p_175712_2_)
          {
            this.pendingTickListEntriesHashSet.remove(var6);
            var5.remove();
          }
          if (var3 == null) {
            var3 = Lists.newArrayList();
          }
          var3.add(var6);
        }
      }
    }
    return var3;
  }
  
  public void updateEntityWithOptionalForce(Entity p_72866_1_, boolean p_72866_2_)
  {
    if ((!func_175735_ai()) && (((p_72866_1_ instanceof EntityAnimal)) || ((p_72866_1_ instanceof EntityWaterMob)))) {
      p_72866_1_.setDead();
    }
    if ((!func_175738_ah()) && ((p_72866_1_ instanceof INpc))) {
      p_72866_1_.setDead();
    }
    super.updateEntityWithOptionalForce(p_72866_1_, p_72866_2_);
  }
  
  private boolean func_175738_ah()
  {
    return this.mcServer.getCanSpawnNPCs();
  }
  
  private boolean func_175735_ai()
  {
    return this.mcServer.getCanSpawnAnimals();
  }
  
  protected IChunkProvider createChunkProvider()
  {
    IChunkLoader var1 = this.saveHandler.getChunkLoader(this.provider);
    this.theChunkProviderServer = new ChunkProviderServer(this, var1, this.provider.createChunkGenerator());
    return this.theChunkProviderServer;
  }
  
  public List func_147486_a(int p_147486_1_, int p_147486_2_, int p_147486_3_, int p_147486_4_, int p_147486_5_, int p_147486_6_)
  {
    ArrayList var7 = Lists.newArrayList();
    for (int var8 = 0; var8 < this.loadedTileEntityList.size(); var8++)
    {
      TileEntity var9 = (TileEntity)this.loadedTileEntityList.get(var8);
      BlockPos var10 = var9.getPos();
      if ((var10.getX() >= p_147486_1_) && (var10.getY() >= p_147486_2_) && (var10.getZ() >= p_147486_3_) && (var10.getX() < p_147486_4_) && (var10.getY() < p_147486_5_) && (var10.getZ() < p_147486_6_)) {
        var7.add(var9);
      }
    }
    return var7;
  }
  
  public boolean isBlockModifiable(EntityPlayer p_175660_1_, BlockPos p_175660_2_)
  {
    return (!this.mcServer.isBlockProtected(this, p_175660_2_, p_175660_1_)) && (getWorldBorder().contains(p_175660_2_));
  }
  
  public void initialize(WorldSettings settings)
  {
    if (!this.worldInfo.isInitialized())
    {
      try
      {
        createSpawnPosition(settings);
        if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
          setDebugWorldSettings();
        }
        super.initialize(settings);
      }
      catch (Throwable var6)
      {
        CrashReport var3 = CrashReport.makeCrashReport(var6, "Exception initializing level");
        try
        {
          addWorldInfoToCrashReport(var3);
        }
        catch (Throwable localThrowable1) {}
        throw new ReportedException(var3);
      }
      this.worldInfo.setServerInitialized(true);
    }
  }
  
  private void setDebugWorldSettings()
  {
    this.worldInfo.setMapFeaturesEnabled(false);
    this.worldInfo.setAllowCommands(true);
    this.worldInfo.setRaining(false);
    this.worldInfo.setThundering(false);
    this.worldInfo.func_176142_i(1000000000);
    this.worldInfo.setWorldTime(6000L);
    this.worldInfo.setGameType(WorldSettings.GameType.SPECTATOR);
    this.worldInfo.setHardcore(false);
    this.worldInfo.setDifficulty(EnumDifficulty.PEACEFUL);
    this.worldInfo.setDifficultyLocked(true);
    getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
  }
  
  private void createSpawnPosition(WorldSettings p_73052_1_)
  {
    if (!this.provider.canRespawnHere())
    {
      this.worldInfo.setSpawn(BlockPos.ORIGIN.offsetUp(this.provider.getAverageGroundLevel()));
    }
    else if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD)
    {
      this.worldInfo.setSpawn(BlockPos.ORIGIN.offsetUp());
    }
    else
    {
      this.findingSpawnPoint = true;
      WorldChunkManager var2 = this.provider.getWorldChunkManager();
      List var3 = var2.getBiomesToSpawnIn();
      Random var4 = new Random(getSeed());
      BlockPos var5 = var2.findBiomePosition(0, 0, 256, var3, var4);
      int var6 = 0;
      int var7 = this.provider.getAverageGroundLevel();
      int var8 = 0;
      if (var5 != null)
      {
        var6 = var5.getX();
        var8 = var5.getZ();
      }
      else
      {
        logger.warn("Unable to find spawn biome");
      }
      int var9 = 0;
      while (!this.provider.canCoordinateBeSpawn(var6, var8))
      {
        var6 += var4.nextInt(64) - var4.nextInt(64);
        var8 += var4.nextInt(64) - var4.nextInt(64);
        var9++;
        if (var9 == 1000) {
          break;
        }
      }
      this.worldInfo.setSpawn(new BlockPos(var6, var7, var8));
      this.findingSpawnPoint = false;
      if (p_73052_1_.isBonusChestEnabled()) {
        createBonusChest();
      }
    }
  }
  
  protected void createBonusChest()
  {
    WorldGeneratorBonusChest var1 = new WorldGeneratorBonusChest(bonusChestContent, 10);
    for (int var2 = 0; var2 < 10; var2++)
    {
      int var3 = this.worldInfo.getSpawnX() + this.rand.nextInt(6) - this.rand.nextInt(6);
      int var4 = this.worldInfo.getSpawnZ() + this.rand.nextInt(6) - this.rand.nextInt(6);
      BlockPos var5 = func_175672_r(new BlockPos(var3, 0, var4)).offsetUp();
      if (var1.generate(this, this.rand, var5)) {
        break;
      }
    }
  }
  
  public BlockPos func_180504_m()
  {
    return this.provider.func_177496_h();
  }
  
  public void saveAllChunks(boolean p_73044_1_, IProgressUpdate p_73044_2_)
    throws MinecraftException
  {
    if (this.chunkProvider.canSave())
    {
      if (p_73044_2_ != null) {
        p_73044_2_.displaySavingString("Saving level");
      }
      saveLevel();
      if (p_73044_2_ != null) {
        p_73044_2_.displayLoadingString("Saving chunks");
      }
      this.chunkProvider.saveChunks(p_73044_1_, p_73044_2_);
      List var3 = this.theChunkProviderServer.func_152380_a();
      Iterator var4 = var3.iterator();
      while (var4.hasNext())
      {
        Chunk var5 = (Chunk)var4.next();
        if (!this.thePlayerManager.func_152621_a(var5.xPosition, var5.zPosition)) {
          this.theChunkProviderServer.dropChunk(var5.xPosition, var5.zPosition);
        }
      }
    }
  }
  
  public void saveChunkData()
  {
    if (this.chunkProvider.canSave()) {
      this.chunkProvider.saveExtraData();
    }
  }
  
  protected void saveLevel()
    throws MinecraftException
  {
    checkSessionLock();
    this.worldInfo.func_176145_a(getWorldBorder().getDiameter());
    this.worldInfo.func_176124_d(getWorldBorder().getCenterX());
    this.worldInfo.func_176141_c(getWorldBorder().getCenterZ());
    this.worldInfo.func_176129_e(getWorldBorder().getDamageBuffer());
    this.worldInfo.func_176125_f(getWorldBorder().func_177727_n());
    this.worldInfo.func_176122_j(getWorldBorder().getWarningDistance());
    this.worldInfo.func_176136_k(getWorldBorder().getWarningTime());
    this.worldInfo.func_176118_b(getWorldBorder().getTargetSize());
    this.worldInfo.func_176135_e(getWorldBorder().getTimeUntilTarget());
    this.saveHandler.saveWorldInfoWithPlayer(this.worldInfo, this.mcServer.getConfigurationManager().getHostPlayerData());
    this.mapStorage.saveAllData();
  }
  
  protected void onEntityAdded(Entity p_72923_1_)
  {
    super.onEntityAdded(p_72923_1_);
    this.entitiesById.addKey(p_72923_1_.getEntityId(), p_72923_1_);
    this.entitiesByUuid.put(p_72923_1_.getUniqueID(), p_72923_1_);
    Entity[] var2 = p_72923_1_.getParts();
    if (var2 != null) {
      for (int var3 = 0; var3 < var2.length; var3++) {
        this.entitiesById.addKey(var2[var3].getEntityId(), var2[var3]);
      }
    }
  }
  
  protected void onEntityRemoved(Entity p_72847_1_)
  {
    super.onEntityRemoved(p_72847_1_);
    this.entitiesById.removeObject(p_72847_1_.getEntityId());
    this.entitiesByUuid.remove(p_72847_1_.getUniqueID());
    Entity[] var2 = p_72847_1_.getParts();
    if (var2 != null) {
      for (int var3 = 0; var3 < var2.length; var3++) {
        this.entitiesById.removeObject(var2[var3].getEntityId());
      }
    }
  }
  
  public boolean addWeatherEffect(Entity p_72942_1_)
  {
    if (super.addWeatherEffect(p_72942_1_))
    {
      this.mcServer.getConfigurationManager().sendToAllNear(p_72942_1_.posX, p_72942_1_.posY, p_72942_1_.posZ, 512.0D, this.provider.getDimensionId(), new S2CPacketSpawnGlobalEntity(p_72942_1_));
      return true;
    }
    return false;
  }
  
  public void setEntityState(Entity entityIn, byte p_72960_2_)
  {
    getEntityTracker().func_151248_b(entityIn, new S19PacketEntityStatus(entityIn, p_72960_2_));
  }
  
  public Explosion newExplosion(Entity p_72885_1_, double p_72885_2_, double p_72885_4_, double p_72885_6_, float p_72885_8_, boolean p_72885_9_, boolean p_72885_10_)
  {
    Explosion var11 = new Explosion(this, p_72885_1_, p_72885_2_, p_72885_4_, p_72885_6_, p_72885_8_, p_72885_9_, p_72885_10_);
    var11.doExplosionA();
    var11.doExplosionB(false);
    if (!p_72885_10_) {
      var11.func_180342_d();
    }
    Iterator var12 = this.playerEntities.iterator();
    while (var12.hasNext())
    {
      EntityPlayer var13 = (EntityPlayer)var12.next();
      if (var13.getDistanceSq(p_72885_2_, p_72885_4_, p_72885_6_) < 4096.0D) {
        ((EntityPlayerMP)var13).playerNetServerHandler.sendPacket(new S27PacketExplosion(p_72885_2_, p_72885_4_, p_72885_6_, p_72885_8_, var11.func_180343_e(), (Vec3)var11.func_77277_b().get(var13)));
      }
    }
    return var11;
  }
  
  public void addBlockEvent(BlockPos pos, Block blockIn, int eventID, int eventParam)
  {
    BlockEventData var5 = new BlockEventData(pos, blockIn, eventID, eventParam);
    Iterator var6 = this.field_147490_S[this.blockEventCacheIndex].iterator();
    BlockEventData var7;
    do
    {
      if (!var6.hasNext())
      {
        this.field_147490_S[this.blockEventCacheIndex].add(var5);
        return;
      }
      var7 = (BlockEventData)var6.next();
    } while (!var7.equals(var5));
  }
  
  private void func_147488_Z()
  {
    while (!this.field_147490_S[this.blockEventCacheIndex].isEmpty())
    {
      int var1 = this.blockEventCacheIndex;
      this.blockEventCacheIndex ^= 0x1;
      Iterator var2 = this.field_147490_S[var1].iterator();
      while (var2.hasNext())
      {
        BlockEventData var3 = (BlockEventData)var2.next();
        if (func_147485_a(var3)) {
          this.mcServer.getConfigurationManager().sendToAllNear(var3.func_180328_a().getX(), var3.func_180328_a().getY(), var3.func_180328_a().getZ(), 64.0D, this.provider.getDimensionId(), new S24PacketBlockAction(var3.func_180328_a(), var3.getBlock(), var3.getEventID(), var3.getEventParameter()));
        }
      }
      this.field_147490_S[var1].clear();
    }
  }
  
  private boolean func_147485_a(BlockEventData p_147485_1_)
  {
    IBlockState var2 = getBlockState(p_147485_1_.func_180328_a());
    return var2.getBlock() == p_147485_1_.getBlock() ? var2.getBlock().onBlockEventReceived(this, p_147485_1_.func_180328_a(), var2, p_147485_1_.getEventID(), p_147485_1_.getEventParameter()) : false;
  }
  
  public void flush()
  {
    this.saveHandler.flush();
  }
  
  protected void updateWeather()
  {
    boolean var1 = isRaining();
    super.updateWeather();
    if (this.prevRainingStrength != this.rainingStrength) {
      this.mcServer.getConfigurationManager().sendPacketToAllPlayersInDimension(new S2BPacketChangeGameState(7, this.rainingStrength), this.provider.getDimensionId());
    }
    if (this.prevThunderingStrength != this.thunderingStrength) {
      this.mcServer.getConfigurationManager().sendPacketToAllPlayersInDimension(new S2BPacketChangeGameState(8, this.thunderingStrength), this.provider.getDimensionId());
    }
    if (var1 != isRaining())
    {
      if (var1) {
        this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(2, 0.0F));
      } else {
        this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(1, 0.0F));
      }
      this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(7, this.rainingStrength));
      this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(8, this.thunderingStrength));
    }
  }
  
  protected int getRenderDistanceChunks()
  {
    return this.mcServer.getConfigurationManager().getViewDistance();
  }
  
  public MinecraftServer func_73046_m()
  {
    return this.mcServer;
  }
  
  public EntityTracker getEntityTracker()
  {
    return this.theEntityTracker;
  }
  
  public PlayerManager getPlayerManager()
  {
    return this.thePlayerManager;
  }
  
  public Teleporter getDefaultTeleporter()
  {
    return this.worldTeleporter;
  }
  
  public void func_175739_a(EnumParticleTypes p_175739_1_, double p_175739_2_, double p_175739_4_, double p_175739_6_, int p_175739_8_, double p_175739_9_, double p_175739_11_, double p_175739_13_, double p_175739_15_, int... p_175739_17_)
  {
    func_180505_a(p_175739_1_, false, p_175739_2_, p_175739_4_, p_175739_6_, p_175739_8_, p_175739_9_, p_175739_11_, p_175739_13_, p_175739_15_, p_175739_17_);
  }
  
  public void func_180505_a(EnumParticleTypes p_180505_1_, boolean p_180505_2_, double p_180505_3_, double p_180505_5_, double p_180505_7_, int p_180505_9_, double p_180505_10_, double p_180505_12_, double p_180505_14_, double p_180505_16_, int... p_180505_18_)
  {
    S2APacketParticles var19 = new S2APacketParticles(p_180505_1_, p_180505_2_, (float)p_180505_3_, (float)p_180505_5_, (float)p_180505_7_, (float)p_180505_10_, (float)p_180505_12_, (float)p_180505_14_, (float)p_180505_16_, p_180505_9_, p_180505_18_);
    for (int var20 = 0; var20 < this.playerEntities.size(); var20++)
    {
      EntityPlayerMP var21 = (EntityPlayerMP)this.playerEntities.get(var20);
      BlockPos var22 = var21.getPosition();
      double var23 = var22.distanceSq(p_180505_3_, p_180505_5_, p_180505_7_);
      if ((var23 <= 256.0D) || ((p_180505_2_) && (var23 <= 65536.0D))) {
        var21.playerNetServerHandler.sendPacket(var19);
      }
    }
  }
  
  public Entity getEntityFromUuid(UUID uuid)
  {
    return (Entity)this.entitiesByUuid.get(uuid);
  }
  
  public ListenableFuture addScheduledTask(Runnable runnableToSchedule)
  {
    return this.mcServer.addScheduledTask(runnableToSchedule);
  }
  
  public boolean isCallingFromMinecraftThread()
  {
    return this.mcServer.isCallingFromMinecraftThread();
  }
  
  static class ServerBlockEventList
    extends ArrayList
  {
    private static final String __OBFID = "CL_00001439";
    
    private ServerBlockEventList() {}
    
    ServerBlockEventList(Object p_i1521_1_)
    {
      this();
    }
  }
}
