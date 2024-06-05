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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.village.VillageCollection;
import net.minecraft.village.VillageSiege;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
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

public class WorldServer extends World implements IThreadListener {
   private final Set pendingTickListEntriesHashSet = Sets.newHashSet();
   private static final Logger logger = LogManager.getLogger();
   private List pendingTickListEntriesThisTick = Lists.newArrayList();
   private boolean allPlayersSleeping;
   private final Teleporter worldTeleporter;
   private int updateEntityTick;
   private final EntityTracker theEntityTracker;
   private final Map entitiesByUuid = Maps.newHashMap();
   private static final List bonusChestContent;
   private WorldServer.ServerBlockEventList[] field_147490_S = new WorldServer.ServerBlockEventList[]{new WorldServer.ServerBlockEventList((Object)null), new WorldServer.ServerBlockEventList((Object)null)};
   public boolean disableLevelSaving;
   private final MinecraftServer mcServer;
   private final PlayerManager thePlayerManager;
   private final SpawnerAnimals field_175742_R = new SpawnerAnimals();
   protected final VillageSiege villageSiege = new VillageSiege(this);
   private final TreeSet pendingTickListEntriesTreeSet = new TreeSet();
   public ChunkProviderServer theChunkProviderServer;
   private static final String __OBFID = "CL_00001437";
   private int blockEventCacheIndex;

   public boolean isCallingFromMinecraftThread() {
      return this.mcServer.isCallingFromMinecraftThread();
   }

   public void saveChunkData() {
      if (this.chunkProvider.canSave()) {
         this.chunkProvider.saveExtraData();
      }

   }

   private boolean func_175735_ai() {
      return this.mcServer.getCanSpawnAnimals();
   }

   protected void createBonusChest() {
      WorldGeneratorBonusChest var1 = new WorldGeneratorBonusChest(bonusChestContent, 10);

      for(int var2 = 0; var2 < 10; ++var2) {
         int var3 = this.worldInfo.getSpawnX() + this.rand.nextInt(6) - this.rand.nextInt(6);
         int var4 = this.worldInfo.getSpawnZ() + this.rand.nextInt(6) - this.rand.nextInt(6);
         BlockPos var5 = this.func_175672_r(new BlockPos(var3, 0, var4)).offsetUp();
         if (var1.generate(this, this.rand, var5)) {
            break;
         }
      }

   }

   public boolean tickUpdates(boolean var1) {
      if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
         return false;
      } else {
         int var2 = this.pendingTickListEntriesTreeSet.size();
         if (var2 != this.pendingTickListEntriesHashSet.size()) {
            throw new IllegalStateException("TickNextTick list out of synch");
         } else {
            if (var2 > 1000) {
               var2 = 1000;
            }

            this.theProfiler.startSection("cleaning");

            NextTickListEntry var3;
            for(int var4 = 0; var4 < var2; ++var4) {
               var3 = (NextTickListEntry)this.pendingTickListEntriesTreeSet.first();
               if (!var1 && var3.scheduledTime > this.worldInfo.getWorldTotalTime()) {
                  break;
               }

               this.pendingTickListEntriesTreeSet.remove(var3);
               this.pendingTickListEntriesHashSet.remove(var3);
               this.pendingTickListEntriesThisTick.add(var3);
            }

            this.theProfiler.endSection();
            this.theProfiler.startSection("ticking");
            Iterator var11 = this.pendingTickListEntriesThisTick.iterator();

            while(var11.hasNext()) {
               var3 = (NextTickListEntry)var11.next();
               var11.remove();
               byte var5 = 0;
               if (this.isAreaLoaded(var3.field_180282_a.add(-var5, -var5, -var5), var3.field_180282_a.add(var5, var5, var5))) {
                  IBlockState var6 = this.getBlockState(var3.field_180282_a);
                  if (var6.getBlock().getMaterial() != Material.air && Block.isEqualTo(var6.getBlock(), var3.func_151351_a())) {
                     try {
                        var6.getBlock().updateTick(this, var3.field_180282_a, var6, this.rand);
                     } catch (Throwable var10) {
                        CrashReport var8 = CrashReport.makeCrashReport(var10, "Exception while ticking a block");
                        CrashReportCategory var9 = var8.makeCategory("Block being ticked");
                        CrashReportCategory.addBlockInfo(var9, var3.field_180282_a, var6);
                        throw new ReportedException(var8);
                     }
                  }
               } else {
                  this.scheduleUpdate(var3.field_180282_a, var3.func_151351_a(), 0);
               }
            }

            this.theProfiler.endSection();
            this.pendingTickListEntriesThisTick.clear();
            return !this.pendingTickListEntriesTreeSet.isEmpty();
         }
      }
   }

   private boolean func_175738_ah() {
      return this.mcServer.getCanSpawnNPCs();
   }

   protected int getRenderDistanceChunks() {
      return this.mcServer.getConfigurationManager().getViewDistance();
   }

   public void updateEntityWithOptionalForce(Entity var1, boolean var2) {
      if (!this.func_175735_ai() && (var1 instanceof EntityAnimal || var1 instanceof EntityWaterMob)) {
         var1.setDead();
      }

      if (!this.func_175738_ah() && var1 instanceof INpc) {
         var1.setDead();
      }

      super.updateEntityWithOptionalForce(var1, var2);
   }

   public BlockPos func_180504_m() {
      return this.provider.func_177496_h();
   }

   protected void onEntityAdded(Entity var1) {
      super.onEntityAdded(var1);
      this.entitiesById.addKey(var1.getEntityId(), var1);
      this.entitiesByUuid.put(var1.getUniqueID(), var1);
      Entity[] var2 = var1.getParts();
      if (var2 != null) {
         for(int var3 = 0; var3 < var2.length; ++var3) {
            this.entitiesById.addKey(var2[var3].getEntityId(), var2[var3]);
         }
      }

   }

   public WorldServer(MinecraftServer var1, ISaveHandler var2, WorldInfo var3, int var4, Profiler var5) {
      super(var2, var3, WorldProvider.getProviderForDimension(var4), var5, false);
      this.mcServer = var1;
      this.theEntityTracker = new EntityTracker(this);
      this.thePlayerManager = new PlayerManager(this);
      this.provider.registerWorld(this);
      this.chunkProvider = this.createChunkProvider();
      this.worldTeleporter = new Teleporter(this);
      this.calculateInitialSkylight();
      this.calculateInitialWeather();
      this.getWorldBorder().setSize(var1.getMaxWorldSize());
   }

   protected BlockPos func_175736_a(BlockPos var1) {
      BlockPos var2 = this.func_175725_q(var1);
      AxisAlignedBB var3 = (new AxisAlignedBB(var2, new BlockPos(var2.getX(), this.getHeight(), var2.getZ()))).expand(3.0D, 3.0D, 3.0D);
      List var4 = this.func_175647_a(EntityLivingBase.class, var3, new Predicate(this) {
         private static final String __OBFID = "CL_00001889";
         final WorldServer this$0;

         {
            this.this$0 = var1;
         }

         public boolean apply(Object var1) {
            return this.func_180242_a((EntityLivingBase)var1);
         }

         public boolean func_180242_a(EntityLivingBase var1) {
            return var1 != null && var1.isEntityAlive() && this.this$0.isAgainstSky(var1.getPosition());
         }
      });
      return !var4.isEmpty() ? ((EntityLivingBase)var4.get(this.rand.nextInt(var4.size()))).getPosition() : var2;
   }

   public Explosion newExplosion(Entity var1, double var2, double var4, double var6, float var8, boolean var9, boolean var10) {
      Explosion var11 = new Explosion(this, var1, var2, var4, var6, var8, var9, var10);
      var11.doExplosionA();
      var11.doExplosionB(false);
      if (!var10) {
         var11.func_180342_d();
      }

      Iterator var12 = this.playerEntities.iterator();

      while(var12.hasNext()) {
         EntityPlayer var13 = (EntityPlayer)var12.next();
         if (var13.getDistanceSq(var2, var4, var6) < 4096.0D) {
            ((EntityPlayerMP)var13).playerNetServerHandler.sendPacket(new S27PacketExplosion(var2, var4, var6, var8, var11.func_180343_e(), (Vec3)var11.func_77277_b().get(var13)));
         }
      }

      return var11;
   }

   public boolean addWeatherEffect(Entity var1) {
      if (super.addWeatherEffect(var1)) {
         this.mcServer.getConfigurationManager().sendToAllNear(var1.posX, var1.posY, var1.posZ, 512.0D, this.provider.getDimensionId(), new S2CPacketSpawnGlobalEntity(var1));
         return true;
      } else {
         return false;
      }
   }

   protected void updateWeather() {
      boolean var1 = this.isRaining();
      super.updateWeather();
      if (this.prevRainingStrength != this.rainingStrength) {
         this.mcServer.getConfigurationManager().sendPacketToAllPlayersInDimension(new S2BPacketChangeGameState(7, this.rainingStrength), this.provider.getDimensionId());
      }

      if (this.prevThunderingStrength != this.thunderingStrength) {
         this.mcServer.getConfigurationManager().sendPacketToAllPlayersInDimension(new S2BPacketChangeGameState(8, this.thunderingStrength), this.provider.getDimensionId());
      }

      if (var1 != this.isRaining()) {
         if (var1) {
            this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(2, 0.0F));
         } else {
            this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(1, 0.0F));
         }

         this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(7, this.rainingStrength));
         this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(8, this.thunderingStrength));
      }

   }

   public void flush() {
      this.saveHandler.flush();
   }

   public void initialize(WorldSettings var1) {
      if (!this.worldInfo.isInitialized()) {
         try {
            this.createSpawnPosition(var1);
            if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
               this.setDebugWorldSettings();
            }

            super.initialize(var1);
         } catch (Throwable var6) {
            CrashReport var3 = CrashReport.makeCrashReport(var6, "Exception initializing level");

            try {
               this.addWorldInfoToCrashReport(var3);
            } catch (Throwable var5) {
            }

            throw new ReportedException(var3);
         }

         this.worldInfo.setServerInitialized(true);
      }

   }

   protected void onEntityRemoved(Entity var1) {
      super.onEntityRemoved(var1);
      this.entitiesById.removeObject(var1.getEntityId());
      this.entitiesByUuid.remove(var1.getUniqueID());
      Entity[] var2 = var1.getParts();
      if (var2 != null) {
         for(int var3 = 0; var3 < var2.length; ++var3) {
            this.entitiesById.removeObject(var2[var3].getEntityId());
         }
      }

   }

   static {
      bonusChestContent = Lists.newArrayList(new WeightedRandomChestContent[]{new WeightedRandomChestContent(Items.stick, 0, 1, 3, 10), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.planks), 0, 1, 3, 10), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log), 0, 1, 3, 10), new WeightedRandomChestContent(Items.stone_axe, 0, 1, 1, 3), new WeightedRandomChestContent(Items.wooden_axe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.stone_pickaxe, 0, 1, 1, 3), new WeightedRandomChestContent(Items.wooden_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.apple, 0, 2, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 2, 3, 3), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log2), 0, 1, 3, 10)});
   }

   protected IChunkProvider createChunkProvider() {
      IChunkLoader var1 = this.saveHandler.getChunkLoader(this.provider);
      this.theChunkProviderServer = new ChunkProviderServer(this, var1, this.provider.createChunkGenerator());
      return this.theChunkProviderServer;
   }

   public Entity getEntityFromUuid(UUID var1) {
      return (Entity)this.entitiesByUuid.get(var1);
   }

   public ListenableFuture addScheduledTask(Runnable var1) {
      return this.mcServer.addScheduledTask(var1);
   }

   public BiomeGenBase.SpawnListEntry func_175734_a(EnumCreatureType var1, BlockPos var2) {
      List var3 = this.getChunkProvider().func_177458_a(var1, var2);
      return var3 != null && !var3.isEmpty() ? (BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(this.rand, var3) : null;
   }

   public void func_180505_a(EnumParticleTypes var1, boolean var2, double var3, double var5, double var7, int var9, double var10, double var12, double var14, double var16, int... var18) {
      S2APacketParticles var19 = new S2APacketParticles(var1, var2, (float)var3, (float)var5, (float)var7, (float)var10, (float)var12, (float)var14, (float)var16, var9, var18);

      for(int var20 = 0; var20 < this.playerEntities.size(); ++var20) {
         EntityPlayerMP var21 = (EntityPlayerMP)this.playerEntities.get(var20);
         BlockPos var22 = var21.getPosition();
         double var23 = var22.distanceSq(var3, var5, var7);
         if (var23 <= 256.0D || var2 && var23 <= 65536.0D) {
            var21.playerNetServerHandler.sendPacket(var19);
         }
      }

   }

   protected void saveLevel() throws MinecraftException {
      this.checkSessionLock();
      this.worldInfo.func_176145_a(this.getWorldBorder().getDiameter());
      this.worldInfo.func_176124_d(this.getWorldBorder().getCenterX());
      this.worldInfo.func_176141_c(this.getWorldBorder().getCenterZ());
      this.worldInfo.func_176129_e(this.getWorldBorder().getDamageBuffer());
      this.worldInfo.func_176125_f(this.getWorldBorder().func_177727_n());
      this.worldInfo.func_176122_j(this.getWorldBorder().getWarningDistance());
      this.worldInfo.func_176136_k(this.getWorldBorder().getWarningTime());
      this.worldInfo.func_176118_b(this.getWorldBorder().getTargetSize());
      this.worldInfo.func_176135_e(this.getWorldBorder().getTimeUntilTarget());
      this.saveHandler.saveWorldInfoWithPlayer(this.worldInfo, this.mcServer.getConfigurationManager().getHostPlayerData());
      this.mapStorage.saveAllData();
   }

   public List func_175712_a(StructureBoundingBox var1, boolean var2) {
      ArrayList var3 = null;

      for(int var4 = 0; var4 < 2; ++var4) {
         Iterator var5;
         if (var4 == 0) {
            var5 = this.pendingTickListEntriesTreeSet.iterator();
         } else {
            var5 = this.pendingTickListEntriesThisTick.iterator();
            if (!this.pendingTickListEntriesThisTick.isEmpty()) {
               logger.debug(String.valueOf((new StringBuilder("toBeTicked = ")).append(this.pendingTickListEntriesThisTick.size())));
            }
         }

         while(var5.hasNext()) {
            NextTickListEntry var6 = (NextTickListEntry)var5.next();
            BlockPos var7 = var6.field_180282_a;
            if (var7.getX() >= var1.minX && var7.getX() < var1.maxX && var7.getZ() >= var1.minZ && var7.getZ() < var1.maxZ) {
               if (var2) {
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

   public boolean func_175732_a(EnumCreatureType var1, BiomeGenBase.SpawnListEntry var2, BlockPos var3) {
      List var4 = this.getChunkProvider().func_177458_a(var1, var3);
      return var4 != null && !var4.isEmpty() ? var4.contains(var2) : false;
   }

   public World init() {
      this.mapStorage = new MapStorage(this.saveHandler);
      String var1 = VillageCollection.func_176062_a(this.provider);
      VillageCollection var2 = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, var1);
      if (var2 == null) {
         this.villageCollectionObj = new VillageCollection(this);
         this.mapStorage.setData(var1, this.villageCollectionObj);
      } else {
         this.villageCollectionObj = var2;
         this.villageCollectionObj.func_82566_a(this);
      }

      this.worldScoreboard = new ServerScoreboard(this.mcServer);
      ScoreboardSaveData var3 = (ScoreboardSaveData)this.mapStorage.loadData(ScoreboardSaveData.class, "scoreboard");
      if (var3 == null) {
         var3 = new ScoreboardSaveData();
         this.mapStorage.setData("scoreboard", var3);
      }

      var3.func_96499_a(this.worldScoreboard);
      ((ServerScoreboard)this.worldScoreboard).func_96547_a(var3);
      this.getWorldBorder().setCenter(this.worldInfo.func_176120_C(), this.worldInfo.func_176126_D());
      this.getWorldBorder().func_177744_c(this.worldInfo.func_176140_I());
      this.getWorldBorder().setDamageBuffer(this.worldInfo.func_176138_H());
      this.getWorldBorder().setWarningDistance(this.worldInfo.func_176131_J());
      this.getWorldBorder().setWarningTime(this.worldInfo.func_176139_K());
      if (this.worldInfo.func_176134_F() > 0L) {
         this.getWorldBorder().setTransition(this.worldInfo.func_176137_E(), this.worldInfo.func_176132_G(), this.worldInfo.func_176134_F());
      } else {
         this.getWorldBorder().setTransition(this.worldInfo.func_176137_E());
      }

      return this;
   }

   public void setInitialSpawnLocation() {
      if (this.worldInfo.getSpawnY() <= 0) {
         this.worldInfo.setSpawnY(64);
      }

      int var1 = this.worldInfo.getSpawnX();
      int var2 = this.worldInfo.getSpawnZ();
      int var3 = 0;

      while(this.getGroundAboveSeaLevel(new BlockPos(var1, 0, var2)).getMaterial() == Material.air) {
         var1 += this.rand.nextInt(8) - this.rand.nextInt(8);
         var2 += this.rand.nextInt(8) - this.rand.nextInt(8);
         ++var3;
         if (var3 == 10000) {
            break;
         }
      }

      this.worldInfo.setSpawnX(var1);
      this.worldInfo.setSpawnZ(var2);
   }

   public MinecraftServer func_73046_m() {
      return this.mcServer;
   }

   public boolean isBlockTickPending(BlockPos var1, Block var2) {
      NextTickListEntry var3 = new NextTickListEntry(var1, var2);
      return this.pendingTickListEntriesThisTick.contains(var3);
   }

   public EntityTracker getEntityTracker() {
      return this.theEntityTracker;
   }

   public boolean areAllPlayersAsleep() {
      if (this.allPlayersSleeping && !this.isRemote) {
         Iterator var1 = this.playerEntities.iterator();

         EntityPlayer var2;
         do {
            if (!var1.hasNext()) {
               return true;
            }

            var2 = (EntityPlayer)var1.next();
         } while(!var2.func_175149_v() && var2.isPlayerFullyAsleep());

         return false;
      } else {
         return false;
      }
   }

   public boolean isBlockModifiable(EntityPlayer var1, BlockPos var2) {
      return !this.mcServer.isBlockProtected(this, var2, var1) && this.getWorldBorder().contains(var2);
   }

   private boolean func_147485_a(BlockEventData var1) {
      IBlockState var2 = this.getBlockState(var1.func_180328_a());
      return var2.getBlock() == var1.getBlock() ? var2.getBlock().onBlockEventReceived(this, var1.func_180328_a(), var2, var1.getEventID(), var1.getEventParameter()) : false;
   }

   protected void func_147456_g() {
      super.func_147456_g();
      if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
         Iterator var1 = this.activeChunkSet.iterator();

         while(var1.hasNext()) {
            ChunkCoordIntPair var2 = (ChunkCoordIntPair)var1.next();
            this.getChunkFromChunkCoords(var2.chunkXPos, var2.chunkZPos).func_150804_b(false);
         }
      } else {
         int var22 = 0;
         int var23 = 0;

         for(Iterator var3 = this.activeChunkSet.iterator(); var3.hasNext(); this.theProfiler.endSection()) {
            ChunkCoordIntPair var4 = (ChunkCoordIntPair)var3.next();
            int var5 = var4.chunkXPos * 16;
            int var6 = var4.chunkZPos * 16;
            this.theProfiler.startSection("getChunk");
            Chunk var7 = this.getChunkFromChunkCoords(var4.chunkXPos, var4.chunkZPos);
            this.func_147467_a(var5, var6, var7);
            this.theProfiler.endStartSection("tickChunk");
            var7.func_150804_b(false);
            this.theProfiler.endStartSection("thunder");
            int var8;
            BlockPos var9;
            if (this.rand.nextInt(100000) == 0 && this.isRaining() && this.isThundering()) {
               this.updateLCG = this.updateLCG * 3 + 1013904223;
               var8 = this.updateLCG >> 2;
               var9 = this.func_175736_a(new BlockPos(var5 + (var8 & 15), 0, var6 + (var8 >> 8 & 15)));
               if (this.func_175727_C(var9)) {
                  this.addWeatherEffect(new EntityLightningBolt(this, (double)var9.getX(), (double)var9.getY(), (double)var9.getZ()));
               }
            }

            this.theProfiler.endStartSection("iceandsnow");
            if (this.rand.nextInt(16) == 0) {
               this.updateLCG = this.updateLCG * 3 + 1013904223;
               var8 = this.updateLCG >> 2;
               var9 = this.func_175725_q(new BlockPos(var5 + (var8 & 15), 0, var6 + (var8 >> 8 & 15)));
               BlockPos var10 = var9.offsetDown();
               if (this.func_175662_w(var10)) {
                  this.setBlockState(var10, Blocks.ice.getDefaultState());
               }

               if (this.isRaining() && this.func_175708_f(var9, true)) {
                  this.setBlockState(var9, Blocks.snow_layer.getDefaultState());
               }

               if (this.isRaining() && this.getBiomeGenForCoords(var10).canSpawnLightningBolt()) {
                  this.getBlockState(var10).getBlock().fillWithRain(this, var10);
               }
            }

            this.theProfiler.endStartSection("tickBlocks");
            var8 = this.getGameRules().getInt("randomTickSpeed");
            if (var8 > 0) {
               ExtendedBlockStorage[] var24 = var7.getBlockStorageArray();
               int var11 = var24.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  ExtendedBlockStorage var13 = var24[var12];
                  if (var13 != null && var13.getNeedsRandomTick()) {
                     for(int var14 = 0; var14 < var8; ++var14) {
                        this.updateLCG = this.updateLCG * 3 + 1013904223;
                        int var15 = this.updateLCG >> 2;
                        int var16 = var15 & 15;
                        int var17 = var15 >> 8 & 15;
                        int var18 = var15 >> 16 & 15;
                        ++var23;
                        BlockPos var19 = new BlockPos(var16 + var5, var18 + var13.getYLocation(), var17 + var6);
                        IBlockState var20 = var13.get(var16, var18, var17);
                        Block var21 = var20.getBlock();
                        if (var21.getTickRandomly()) {
                           ++var22;
                           var21.randomTick(this, var19, var20, this.rand);
                        }
                     }
                  }
               }
            }
         }
      }

   }

   public void updateAllPlayersSleepingFlag() {
      this.allPlayersSleeping = false;
      if (!this.playerEntities.isEmpty()) {
         int var1 = 0;
         int var2 = 0;
         Iterator var3 = this.playerEntities.iterator();

         while(var3.hasNext()) {
            EntityPlayer var4 = (EntityPlayer)var3.next();
            if (var4.func_175149_v()) {
               ++var1;
            } else if (var4.isPlayerSleeping()) {
               ++var2;
            }
         }

         this.allPlayersSleeping = var2 > 0 && var2 >= this.playerEntities.size() - var1;
      }

   }

   public List getPendingBlockUpdates(Chunk var1, boolean var2) {
      ChunkCoordIntPair var3 = var1.getChunkCoordIntPair();
      int var4 = (var3.chunkXPos << 4) - 2;
      int var5 = var4 + 16 + 2;
      int var6 = (var3.chunkZPos << 4) - 2;
      int var7 = var6 + 16 + 2;
      return this.func_175712_a(new StructureBoundingBox(var4, 0, var6, var5, 256, var7), var2);
   }

   public void saveAllChunks(boolean var1, IProgressUpdate var2) throws MinecraftException {
      if (this.chunkProvider.canSave()) {
         if (var2 != null) {
            var2.displaySavingString("Saving level");
         }

         this.saveLevel();
         if (var2 != null) {
            var2.displayLoadingString("Saving chunks");
         }

         this.chunkProvider.saveChunks(var1, var2);
         List var3 = this.theChunkProviderServer.func_152380_a();
         Iterator var4 = var3.iterator();

         while(var4.hasNext()) {
            Chunk var5 = (Chunk)var4.next();
            if (!this.thePlayerManager.func_152621_a(var5.xPosition, var5.zPosition)) {
               this.theChunkProviderServer.dropChunk(var5.xPosition, var5.zPosition);
            }
         }
      }

   }

   public PlayerManager getPlayerManager() {
      return this.thePlayerManager;
   }

   public void tick() {
      super.tick();
      if (this.getWorldInfo().isHardcoreModeEnabled() && this.getDifficulty() != EnumDifficulty.HARD) {
         this.getWorldInfo().setDifficulty(EnumDifficulty.HARD);
      }

      this.provider.getWorldChunkManager().cleanupCache();
      if (this.areAllPlayersAsleep()) {
         if (this.getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
            long var1 = this.worldInfo.getWorldTime() + 24000L;
            this.worldInfo.setWorldTime(var1 - var1 % 24000L);
         }

         this.wakeAllPlayers();
      }

      this.theProfiler.startSection("mobSpawner");
      if (this.getGameRules().getGameRuleBooleanValue("doMobSpawning") && this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD) {
         this.field_175742_R.findChunksForSpawning(this, this.spawnHostileMobs, this.spawnPeacefulMobs, this.worldInfo.getWorldTotalTime() % 400L == 0L);
      }

      this.theProfiler.endStartSection("chunkSource");
      this.chunkProvider.unloadQueuedChunks();
      int var3 = this.calculateSkylightSubtracted(1.0F);
      if (var3 != this.getSkylightSubtracted()) {
         this.setSkylightSubtracted(var3);
      }

      this.worldInfo.incrementTotalWorldTime(this.worldInfo.getWorldTotalTime() + 1L);
      if (this.getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
         this.worldInfo.setWorldTime(this.worldInfo.getWorldTime() + 1L);
      }

      this.theProfiler.endStartSection("tickPending");
      this.tickUpdates(false);
      this.theProfiler.endStartSection("tickBlocks");
      this.func_147456_g();
      this.theProfiler.endStartSection("chunkMap");
      this.thePlayerManager.updatePlayerInstances();
      this.theProfiler.endStartSection("village");
      this.villageCollectionObj.tick();
      this.villageSiege.tick();
      this.theProfiler.endStartSection("portalForcer");
      this.worldTeleporter.removeStalePortalLocations(this.getTotalWorldTime());
      this.theProfiler.endSection();
      this.func_147488_Z();
   }

   private void resetRainAndThunder() {
      this.worldInfo.setRainTime(0);
      this.worldInfo.setRaining(false);
      this.worldInfo.setThunderTime(0);
      this.worldInfo.setThundering(false);
   }

   private void createSpawnPosition(WorldSettings var1) {
      if (!this.provider.canRespawnHere()) {
         this.worldInfo.setSpawn(BlockPos.ORIGIN.offsetUp(this.provider.getAverageGroundLevel()));
      } else if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
         this.worldInfo.setSpawn(BlockPos.ORIGIN.offsetUp());
      } else {
         this.findingSpawnPoint = true;
         WorldChunkManager var2 = this.provider.getWorldChunkManager();
         List var3 = var2.getBiomesToSpawnIn();
         Random var4 = new Random(this.getSeed());
         BlockPos var5 = var2.findBiomePosition(0, 0, 256, var3, var4);
         int var6 = 0;
         int var7 = this.provider.getAverageGroundLevel();
         int var8 = 0;
         if (var5 != null) {
            var6 = var5.getX();
            var8 = var5.getZ();
         } else {
            logger.warn("Unable to find spawn biome");
         }

         int var9 = 0;

         while(!this.provider.canCoordinateBeSpawn(var6, var8)) {
            var6 += var4.nextInt(64) - var4.nextInt(64);
            var8 += var4.nextInt(64) - var4.nextInt(64);
            ++var9;
            if (var9 == 1000) {
               break;
            }
         }

         this.worldInfo.setSpawn(new BlockPos(var6, var7, var8));
         this.findingSpawnPoint = false;
         if (var1.isBonusChestEnabled()) {
            this.createBonusChest();
         }
      }

   }

   public List func_147486_a(int var1, int var2, int var3, int var4, int var5, int var6) {
      ArrayList var7 = Lists.newArrayList();

      for(int var8 = 0; var8 < this.loadedTileEntityList.size(); ++var8) {
         TileEntity var9 = (TileEntity)this.loadedTileEntityList.get(var8);
         BlockPos var10 = var9.getPos();
         if (var10.getX() >= var1 && var10.getY() >= var2 && var10.getZ() >= var3 && var10.getX() < var4 && var10.getY() < var5 && var10.getZ() < var6) {
            var7.add(var9);
         }
      }

      return var7;
   }

   public void func_180497_b(BlockPos var1, Block var2, int var3, int var4) {
      NextTickListEntry var5 = new NextTickListEntry(var1, var2);
      var5.setPriority(var4);
      if (var2.getMaterial() != Material.air) {
         var5.setScheduledTime((long)var3 + this.worldInfo.getWorldTotalTime());
      }

      if (!this.pendingTickListEntriesHashSet.contains(var5)) {
         this.pendingTickListEntriesHashSet.add(var5);
         this.pendingTickListEntriesTreeSet.add(var5);
      }

   }

   public void updateEntities() {
      if (this.playerEntities.isEmpty()) {
         if (this.updateEntityTick++ >= 1200) {
            return;
         }
      } else {
         this.resetUpdateEntityTick();
      }

      super.updateEntities();
   }

   public void addBlockEvent(BlockPos var1, Block var2, int var3, int var4) {
      BlockEventData var5 = new BlockEventData(var1, var2, var3, var4);
      Iterator var6 = this.field_147490_S[this.blockEventCacheIndex].iterator();

      while(var6.hasNext()) {
         BlockEventData var7 = (BlockEventData)var6.next();
         if (var7.equals(var5)) {
            return;
         }
      }

      this.field_147490_S[this.blockEventCacheIndex].add(var5);
   }

   public void setEntityState(Entity var1, byte var2) {
      this.getEntityTracker().func_151248_b(var1, new S19PacketEntityStatus(var1, var2));
   }

   public Teleporter getDefaultTeleporter() {
      return this.worldTeleporter;
   }

   public void func_175739_a(EnumParticleTypes var1, double var2, double var4, double var6, int var8, double var9, double var11, double var13, double var15, int... var17) {
      this.func_180505_a(var1, false, var2, var4, var6, var8, var9, var11, var13, var15, var17);
   }

   public void resetUpdateEntityTick() {
      this.updateEntityTick = 0;
   }

   private void func_147488_Z() {
      while(!this.field_147490_S[this.blockEventCacheIndex].isEmpty()) {
         int var1 = this.blockEventCacheIndex;
         this.blockEventCacheIndex ^= 1;
         Iterator var2 = this.field_147490_S[var1].iterator();

         while(var2.hasNext()) {
            BlockEventData var3 = (BlockEventData)var2.next();
            if (this.func_147485_a(var3)) {
               this.mcServer.getConfigurationManager().sendToAllNear((double)var3.func_180328_a().getX(), (double)var3.func_180328_a().getY(), (double)var3.func_180328_a().getZ(), 64.0D, this.provider.getDimensionId(), new S24PacketBlockAction(var3.func_180328_a(), var3.getBlock(), var3.getEventID(), var3.getEventParameter()));
            }
         }

         this.field_147490_S[var1].clear();
      }

   }

   protected void wakeAllPlayers() {
      this.allPlayersSleeping = false;
      Iterator var1 = this.playerEntities.iterator();

      while(var1.hasNext()) {
         EntityPlayer var2 = (EntityPlayer)var1.next();
         if (var2.isPlayerSleeping()) {
            var2.wakeUpPlayer(false, false, true);
         }
      }

      this.resetRainAndThunder();
   }

   private void setDebugWorldSettings() {
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
      this.getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
   }

   public void scheduleUpdate(BlockPos var1, Block var2, int var3) {
      this.func_175654_a(var1, var2, var3, 0);
   }

   public void func_175654_a(BlockPos var1, Block var2, int var3, int var4) {
      NextTickListEntry var5 = new NextTickListEntry(var1, var2);
      byte var6 = 0;
      if (this.scheduledUpdatesAreImmediate && var2.getMaterial() != Material.air) {
         if (var2.requiresUpdates()) {
            var6 = 8;
            if (this.isAreaLoaded(var5.field_180282_a.add(-var6, -var6, -var6), var5.field_180282_a.add(var6, var6, var6))) {
               IBlockState var7 = this.getBlockState(var5.field_180282_a);
               if (var7.getBlock().getMaterial() != Material.air && var7.getBlock() == var5.func_151351_a()) {
                  var7.getBlock().updateTick(this, var5.field_180282_a, var7, this.rand);
               }
            }

            return;
         }

         var3 = 1;
      }

      if (this.isAreaLoaded(var1.add(-var6, -var6, -var6), var1.add(var6, var6, var6))) {
         if (var2.getMaterial() != Material.air) {
            var5.setScheduledTime((long)var3 + this.worldInfo.getWorldTotalTime());
            var5.setPriority(var4);
         }

         if (!this.pendingTickListEntriesHashSet.contains(var5)) {
            this.pendingTickListEntriesHashSet.add(var5);
            this.pendingTickListEntriesTreeSet.add(var5);
         }
      }

   }

   static class ServerBlockEventList extends ArrayList {
      private static final String __OBFID = "CL_00001439";

      private ServerBlockEventList() {
      }

      ServerBlockEventList(Object var1) {
         this();
      }
   }
}
