package net.minecraft.client.multiplayer;

import com.google.common.collect.Sets;
import java.util.Random;
import java.util.Set;
import net.AB;
import net.aFd;
import net.aT6;
import net.aTX;
import net.azQ;
import net.jZ;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettings$GameType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.SaveDataMemoryStorage;
import net.minecraft.world.storage.SaveHandlerMP;
import net.minecraft.world.storage.WorldInfo;
import net.optifine.BlockPosM;
import net.optifine.Config;
import net.optifine.DynamicLights;
import net.optifine.PlayerControllerOF;
import net.optifine.Reflector;

public class WorldClient extends World {
   private final Set entityList = Sets.newHashSet();
   private NetHandlerPlayClient sendQueue;
   public static boolean R;
   private ChunkProviderClient clientChunkProvider;
   private final Set entitySpawnQueue = Sets.newHashSet();
   private final Minecraft mc = Minecraft.getInstance();
   private final Set previousActiveChunkSet = Sets.newHashSet();
   private static final String Q = "CL_00000882";
   private BlockPosM randomTickPosM = new BlockPosM(0, 0, 0, 3);
   private boolean playerUpdate = false;

   public WorldClient(NetHandlerPlayClient var1, WorldSettings var2, int var3, EnumDifficulty var4, Profiler var5) {
      super(new SaveHandlerMP(), new WorldInfo(var2, "MpServer"), WorldProvider.getProviderForDimension(var3), var5, true);
      this.sendQueue = var1;
      this.getWorldInfo().setDifficulty(var4);
      this.provider.registerWorld(this);
      this.setSpawnPoint(new BlockPos(8, 64, 8));
      this.chunkProvider = this.createChunkProvider();
      this.mapStorage = new SaveDataMemoryStorage();
      this.calculateInitialSkylight();
      this.calculateInitialWeather();
      Reflector.a(Reflector.bP, new Object[]{this});
      if(this.mc.at != null && this.mc.at.getClass() == aTX.class) {
         this.mc.at = new PlayerControllerOF(this.mc, var1);
      }

   }

   public void tick() {
      super.tick();
      this.setTotalWorldTime(this.getTotalWorldTime() + 1L);
      if(this.getGameRules().getBoolean("doDaylightCycle")) {
         this.setWorldTime(this.getWorldTime() + 1L);
      }

      this.theProfiler.startSection("reEntryProcessing");

      for(int var1 = 0; var1 < 10 && !this.entitySpawnQueue.isEmpty(); ++var1) {
         Entity var2 = (Entity)this.entitySpawnQueue.iterator().next();
         this.entitySpawnQueue.remove(var2);
         if(!this.getLoadedEntityList().contains(var2)) {
            this.spawnEntityInWorld(var2);
         }
      }

      this.theProfiler.endStartSection("chunkCache");
      this.clientChunkProvider.unloadQueuedChunks();
      this.theProfiler.endStartSection("blocks");
      this.updateBlocks();
      this.theProfiler.endSection();
   }

   public void invalidateBlockReceiveRegion(int var1, int var2, int var3, int var4, int var5, int var6) {
   }

   protected IChunkProvider createChunkProvider() {
      this.clientChunkProvider = new ChunkProviderClient(this);
      return this.clientChunkProvider;
   }

   protected void updateBlocks() {
      super.updateBlocks();
      this.previousActiveChunkSet.retainAll(this.activeChunkSet);
      if(this.previousActiveChunkSet.size() == this.activeChunkSet.size()) {
         this.previousActiveChunkSet.clear();
      }

      int var1 = 0;

      for(ChunkCoordIntPair var3 : this.activeChunkSet) {
         if(!this.previousActiveChunkSet.contains(var3)) {
            int var4 = var3.chunkXPos * 16;
            int var5 = var3.chunkZPos * 16;
            this.theProfiler.startSection("getChunk");
            Chunk var6 = this.getChunkFromChunkCoords(var3.chunkXPos, var3.chunkZPos);
            this.playMoodSoundAndCheckLight(var4, var5, var6);
            this.theProfiler.endSection();
            this.previousActiveChunkSet.add(var3);
            ++var1;
            if(var1 >= 10) {
               return;
            }
         }
      }

   }

   public void doPreChunk(int var1, int var2, boolean var3) {
      this.clientChunkProvider.loadChunk(var1, var2);
      this.markBlockRangeForRenderUpdate(var1 * 16, 0, var2 * 16, var1 * 16 + 15, 256, var2 * 16 + 15);
   }

   public boolean spawnEntityInWorld(Entity var1) {
      boolean var2 = super.spawnEntityInWorld(var1);
      this.entityList.add(var1);
      this.entitySpawnQueue.add(var1);
      return var2;
   }

   public void removeEntity(Entity var1) {
      super.removeEntity(var1);
      this.entityList.remove(var1);
   }

   protected void onEntityAdded(Entity var1) {
      super.onEntityAdded(var1);
      this.entitySpawnQueue.remove(var1);
   }

   protected void onEntityRemoved(Entity var1) {
      super.onEntityRemoved(var1);
      boolean var2 = false;
      if(this.entityList.contains(var1)) {
         if(var1.isEntityAlive()) {
            this.entitySpawnQueue.add(var1);
         } else {
            this.entityList.remove(var1);
         }
      }

   }

   public void addEntityToWorld(int var1, Entity var2) {
      Entity var3 = this.removeEntityFromWorld(var1);
      this.removeEntity(var3);
      this.entityList.add(var2);
      var2.setEntityId(var1);
      if(!this.spawnEntityInWorld(var2)) {
         this.entitySpawnQueue.add(var2);
      }

      this.entitiesById.addKey(var1, var2);
   }

   public Entity removeEntityFromWorld(int var1) {
      return (Entity)(var1 == this.mc.player.getEntityID()?this.mc.player:super.getEntityByID(var1));
   }

   public void d(int var1) {
      Entity var2 = (Entity)this.entitiesById.removeObject(var1);
      this.entityList.remove(var2);
      this.removeEntity(var2);
   }

   public void a(BlockPos var1, IBlockState var2) {
      int var3 = var1.getX();
      int var4 = var1.getY();
      int var5 = var1.getZ();
      this.invalidateBlockReceiveRegion(var3, var4, var5, var3, var4, var5);
      super.setBlockState(var1, var2, 3);
   }

   public void sendQuittingDisconnectingPacket() {
      this.sendQueue.getNetworkManager().closeChannel(new ChatComponentText("Quitting"));
   }

   protected void updateWeather() {
   }

   protected int getRenderDistanceChunks() {
      return this.mc.gameSettings.renderDistanceChunks;
   }

   public void doVoidFogParticles(int var1, int var2, int var3) {
      byte var4 = 16;
      Random var5 = new Random();
      ItemStack var6 = this.mc.player.getHeldItem();
      if(this.mc.at.l() == WorldSettings$GameType.CREATIVE && Block.getBlockFromItem(var6.getItem()) == Blocks.barrier) {
         boolean var14 = true;
      } else {
         boolean var10000 = false;
      }

      BlockPosM var8 = this.randomTickPosM;

      for(int var9 = 0; var9 < 1000; ++var9) {
         int var10 = var1 + this.rand.nextInt(var4) - this.rand.nextInt(var4);
         int var11 = var2 + this.rand.nextInt(var4) - this.rand.nextInt(var4);
         int var12 = var3 + this.rand.nextInt(var4) - this.rand.nextInt(var4);
         var8.setXyz(var10, var11, var12);
         IBlockState var13 = this.getBlockState(var8);
         var13.getBlock().randomDisplayTick(this, var8, var13, var5);
         if(var13.getBlock() == Blocks.barrier) {
            this.spawnParticle(EnumParticleTypes.BARRIER, (double)((float)var10 + 0.5F), (double)((float)var11 + 0.5F), (double)((float)var12 + 0.5F), 0.0D, 0.0D, 0.0D, new int[0]);
         }
      }

   }

   public void removeAllEntities() {
      this.getLoadedEntityList().removeAll(this.unloadedEntityList);

      for(Entity var2 : this.unloadedEntityList) {
         int var3 = var2.chunkCoordX;
         int var4 = var2.chunkCoordZ;
         if(var2.addedToChunk && this.isChunkLoaded(var3, var4, true)) {
            this.getChunkFromChunkCoords(var3, var4).removeEntity(var2);
         }
      }

      for(Entity var7 : this.unloadedEntityList) {
         this.onEntityRemoved(var7);
      }

      this.unloadedEntityList.clear();

      for(int var6 = 0; var6 < this.getLoadedEntityList().size(); ++var6) {
         Entity var8 = (Entity)this.getLoadedEntityList().get(var6);
         if(var8.ridingEntity != null) {
            if(!var8.ridingEntity.isDead && var8.ridingEntity.riddenByEntity == var8) {
               continue;
            }

            var8.ridingEntity.riddenByEntity = null;
            var8.ridingEntity = null;
         }

         if(var8.isDead) {
            int var9 = var8.chunkCoordX;
            int var10 = var8.chunkCoordZ;
            if(var8.addedToChunk && this.isChunkLoaded(var9, var10, true)) {
               this.getChunkFromChunkCoords(var9, var10).removeEntity(var8);
            }

            this.getLoadedEntityList().remove(var6--);
            this.onEntityRemoved(var8);
         }
      }

   }

   public CrashReportCategory addWorldInfoToCrashReport(CrashReport var1) {
      CrashReportCategory var2 = super.addWorldInfoToCrashReport(var1);
      var2.addCrashSectionCallable("Forced entities", new aT6(this));
      var2.addCrashSectionCallable("Retry entities", new AB(this));
      var2.addCrashSectionCallable("Server brand", new azQ(this));
      var2.addCrashSectionCallable("Server type", new jZ(this));
      return var2;
   }

   public void playSoundAtPos(BlockPos var1, String var2, float var3, float var4, boolean var5) {
      this.playSound((double)var1.getX() + 0.5D, (double)var1.getY() + 0.5D, (double)var1.getZ() + 0.5D, var2, var3, var4, var5);
   }

   public void playSound(double var1, double var3, double var5, String var7, float var8, float var9, boolean var10) {
      double var11 = this.mc.getRenderViewEntity().getDistanceSq(var1, var3, var5);
      PositionedSoundRecord var13 = new PositionedSoundRecord(new ResourceLocation(var7), var8, var9, (float)var1, (float)var3, (float)var5);
      if(var11 > 100.0D) {
         double var14 = Math.sqrt(var11) / 40.0D;
         this.mc.getSoundHandler().playDelayedSound(var13, (int)(var14 * 20.0D));
      } else {
         this.mc.getSoundHandler().playSound(var13);
      }

   }

   public void makeFireworks(double var1, double var3, double var5, double var7, double var9, double var11, NBTTagCompound var13) {
      this.mc.effectRenderer.addEffect(new aFd(this, var1, var3, var5, var7, var9, var11, this.mc.effectRenderer, var13));
   }

   public void setWorldScoreboard(Scoreboard var1) {
      this.worldScoreboard = var1;
   }

   public void setWorldTime(long var1) {
      if(var1 < 0L) {
         var1 = -var1;
         this.getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
      } else {
         this.getGameRules().setOrCreateGameRule("doDaylightCycle", "true");
      }

      super.setWorldTime(var1);
   }

   public int getCombinedLight(BlockPos var1, int var2) {
      int var3 = super.getCombinedLight(var1, var2);
      if(Config.al()) {
         var3 = DynamicLights.getCombinedLight(var1, var3);
      }

      return var3;
   }

   public boolean setBlockState(BlockPos var1, IBlockState var2, int var3) {
      this.playerUpdate = this.isPlayerActing();
      boolean var4 = super.setBlockState(var1, var2, var3);
      this.playerUpdate = false;
      return var4;
   }

   private boolean isPlayerActing() {
      if(this.mc.at instanceof PlayerControllerOF) {
         PlayerControllerOF var1 = (PlayerControllerOF)this.mc.at;
         return var1.isActing();
      } else {
         return false;
      }
   }

   public boolean isPlayerUpdate() {
      return this.playerUpdate;
   }

   static Set access$000(WorldClient var0) {
      return var0.entityList;
   }

   static Set access$100(WorldClient var0) {
      return var0.entitySpawnQueue;
   }

   static Minecraft access$200(WorldClient var0) {
      return var0.mc;
   }
}
