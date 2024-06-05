package net.minecraft.server.integrated;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.CryptManager;
import net.minecraft.util.HttpUtil;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldManager;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import optifine.Reflector;
import optifine.WorldServerOF;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntegratedServer extends MinecraftServer {
   private final WorldSettings theWorldSettings;
   private final Minecraft mc;
   private boolean isGamePaused;
   private boolean isPublic;
   private ThreadLanServerPing lanServerPing;
   private static final Logger logger = LogManager.getLogger();

   protected ServerCommandManager createNewCommandManager() {
      return new IntegratedServerCommandManager();
   }

   public void setDifficultyForAllWorlds(EnumDifficulty var1) {
      super.setDifficultyForAllWorlds(var1);
      if (this.mc.theWorld != null) {
         this.mc.theWorld.getWorldInfo().setDifficulty(var1);
      }

   }

   public void setGameType(WorldSettings.GameType var1) {
      this.getConfigurationManager().func_152604_a(var1);
   }

   public boolean isDedicatedServer() {
      return false;
   }

   public void initiateShutdown() {
      Futures.getUnchecked(this.addScheduledTask(new Runnable(this) {
         final IntegratedServer this$0;

         public void run() {
            ArrayList var1 = Lists.newArrayList(this.this$0.getConfigurationManager().playerEntityList);
            Iterator var2 = var1.iterator();

            while(var2.hasNext()) {
               EntityPlayerMP var3 = (EntityPlayerMP)var2.next();
               this.this$0.getConfigurationManager().playerLoggedOut(var3);
            }

         }

         {
            this.this$0 = var1;
         }
      }));
      super.initiateShutdown();
      if (this.lanServerPing != null) {
         this.lanServerPing.interrupt();
         this.lanServerPing = null;
      }

   }

   public boolean canStructuresSpawn() {
      return false;
   }

   public boolean isSnooperEnabled() {
      return Minecraft.getMinecraft().isSnooperEnabled();
   }

   public boolean isHardcore() {
      return this.theWorldSettings.getHardcoreEnabled();
   }

   public File getDataDirectory() {
      return this.mc.mcDataDir;
   }

   public boolean getPublic() {
      return this.isPublic;
   }

   public WorldSettings.GameType getGameType() {
      return this.theWorldSettings.getGameType();
   }

   public void stopServer() {
      super.stopServer();
      if (this.lanServerPing != null) {
         this.lanServerPing.interrupt();
         this.lanServerPing = null;
      }

   }

   public void tick() {
      boolean var1 = this.isGamePaused;
      this.isGamePaused = Minecraft.getMinecraft().getNetHandler() != null && Minecraft.getMinecraft().isGamePaused();
      if (!var1 && this.isGamePaused) {
         logger.info("Saving and pausing game...");
         this.getConfigurationManager().saveAllPlayerData();
         this.saveAllWorlds(false);
      }

      if (this.isGamePaused) {
         Queue var2 = this.futureTaskQueue;
         Queue var3 = this.futureTaskQueue;
         synchronized(this.futureTaskQueue) {
            while(!this.futureTaskQueue.isEmpty()) {
               try {
                  if (Reflector.FMLCommonHandler_callFuture.exists()) {
                     Reflector.callVoid(Reflector.FMLCommonHandler_callFuture, (FutureTask)this.futureTaskQueue.poll());
                  } else {
                     ((FutureTask)this.futureTaskQueue.poll()).run();
                  }
               } catch (Throwable var8) {
                  logger.fatal(var8);
               }
            }
         }
      } else {
         super.tick();
         if (this.mc.gameSettings.renderDistanceChunks != this.getConfigurationManager().getViewDistance()) {
            logger.info("Changing view distance to {}, from {}", new Object[]{this.mc.gameSettings.renderDistanceChunks, this.getConfigurationManager().getViewDistance()});
            this.getConfigurationManager().setViewDistance(this.mc.gameSettings.renderDistanceChunks);
         }

         if (this.mc.theWorld != null) {
            WorldInfo var10 = this.worldServers[0].getWorldInfo();
            WorldInfo var11 = this.mc.theWorld.getWorldInfo();
            if (!var10.isDifficultyLocked() && var11.getDifficulty() != var10.getDifficulty()) {
               logger.info("Changing difficulty to {}, from {}", new Object[]{var11.getDifficulty(), var10.getDifficulty()});
               this.setDifficultyForAllWorlds(var11.getDifficulty());
            } else if (var11.isDifficultyLocked() && !var10.isDifficultyLocked()) {
               logger.info("Locking difficulty to {}", new Object[]{var11.getDifficulty()});
               WorldServer[] var4 = this.worldServers;
               int var5 = var4.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  WorldServer var7 = var4[var6];
                  if (var7 != null) {
                     var7.getWorldInfo().setDifficultyLocked(true);
                  }
               }
            }
         }
      }

   }

   public boolean isCommandBlockEnabled() {
      return true;
   }

   protected void loadAllWorlds(String var1, String var2, long var3, WorldType var5, String var6) {
      this.convertMapIfNeeded(var1);
      ISaveHandler var7 = this.getActiveAnvilConverter().getSaveLoader(var1, true);
      this.setResourcePackFromWorld(this.getFolderName(), var7);
      WorldInfo var8 = var7.loadWorldInfo();
      if (Reflector.DimensionManager.exists()) {
         WorldServer var9 = this.isDemo() ? (WorldServer)(new DemoWorldServer(this, var7, var8, 0, this.theProfiler)).init() : (WorldServer)(new WorldServerOF(this, var7, var8, 0, this.theProfiler)).init();
         var9.initialize(this.theWorldSettings);
         Integer[] var10 = (Integer[])Reflector.call(Reflector.DimensionManager_getStaticDimensionIDs);
         Integer[] var11 = var10;
         int var12 = var10.length;

         for(int var13 = 0; var13 < var12; ++var13) {
            int var14 = var11[var13];
            WorldServer var15 = var14 == 0 ? var9 : (WorldServer)(new WorldServerMulti(this, var7, var14, var9, this.theProfiler)).init();
            var15.addWorldAccess(new WorldManager(this, var15));
            if (!this.isSinglePlayer()) {
               var15.getWorldInfo().setGameType(this.getGameType());
            }

            if (Reflector.EventBus.exists()) {
               Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, var15);
            }
         }

         this.getConfigurationManager().setPlayerManager(new WorldServer[]{var9});
         if (var9.getWorldInfo().getDifficulty() == null) {
            this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
         }
      } else {
         this.worldServers = new WorldServer[3];
         this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
         this.setResourcePackFromWorld(this.getFolderName(), var7);
         if (var8 == null) {
            var8 = new WorldInfo(this.theWorldSettings, var2);
         } else {
            var8.setWorldName(var2);
         }

         for(int var16 = 0; var16 < this.worldServers.length; ++var16) {
            byte var17 = 0;
            if (var16 == 1) {
               var17 = -1;
            }

            if (var16 == 2) {
               var17 = 1;
            }

            if (var16 == 0) {
               if (this.isDemo()) {
                  this.worldServers[var16] = (WorldServer)(new DemoWorldServer(this, var7, var8, var17, this.theProfiler)).init();
               } else {
                  this.worldServers[var16] = (WorldServer)(new WorldServerOF(this, var7, var8, var17, this.theProfiler)).init();
               }

               this.worldServers[var16].initialize(this.theWorldSettings);
            } else {
               this.worldServers[var16] = (WorldServer)(new WorldServerMulti(this, var7, var17, this.worldServers[0], this.theProfiler)).init();
            }

            this.worldServers[var16].addWorldAccess(new WorldManager(this, this.worldServers[var16]));
         }

         this.getConfigurationManager().setPlayerManager(this.worldServers);
         if (this.worldServers[0].getWorldInfo().getDifficulty() == null) {
            this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
         }
      }

      this.initialWorldChunkLoad();
   }

   public EnumDifficulty getDifficulty() {
      return this.mc.theWorld == null ? this.mc.gameSettings.difficulty : this.mc.theWorld.getWorldInfo().getDifficulty();
   }

   public void func_175592_a() {
      this.func_175585_v();
   }

   public IntegratedServer(Minecraft var1) {
      super(var1.getProxy(), new File(var1.mcDataDir, USER_CACHE_FILE.getName()));
      this.mc = var1;
      this.theWorldSettings = null;
   }

   protected boolean startServer() throws IOException {
      logger.info("Starting integrated minecraft server version 1.8");
      this.setOnlineMode(true);
      this.setCanSpawnAnimals(true);
      this.setCanSpawnNPCs(true);
      this.setAllowPvp(true);
      this.setAllowFlight(true);
      logger.info("Generating keypair");
      this.setKeyPair(CryptManager.generateKeyPair());
      Object var1;
      if (Reflector.FMLCommonHandler_handleServerAboutToStart.exists()) {
         var1 = Reflector.call(Reflector.FMLCommonHandler_instance);
         if (!Reflector.callBoolean(var1, Reflector.FMLCommonHandler_handleServerAboutToStart, this)) {
            return false;
         }
      }

      this.loadAllWorlds(this.getFolderName(), this.getWorldName(), this.theWorldSettings.getSeed(), this.theWorldSettings.getTerrainType(), this.theWorldSettings.getWorldName());
      this.setMOTD(String.valueOf((new StringBuilder(String.valueOf(this.getServerOwner()))).append(" - ").append(this.worldServers[0].getWorldInfo().getWorldName())));
      if (Reflector.FMLCommonHandler_handleServerStarting.exists()) {
         var1 = Reflector.call(Reflector.FMLCommonHandler_instance);
         if (Reflector.FMLCommonHandler_handleServerStarting.getReturnType() == Boolean.TYPE) {
            return Reflector.callBoolean(var1, Reflector.FMLCommonHandler_handleServerStarting, this);
         }

         Reflector.callVoid(var1, Reflector.FMLCommonHandler_handleServerStarting, this);
      }

      return true;
   }

   public void addServerStatsToSnooper(PlayerUsageSnooper var1) {
      super.addServerStatsToSnooper(var1);
      var1.addClientStat("snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
   }

   public CrashReport addServerInfoToCrashReport(CrashReport var1) {
      var1 = super.addServerInfoToCrashReport(var1);
      var1.getCategory().addCrashSectionCallable("Type", new Callable(this) {
         final IntegratedServer this$0;

         {
            this.this$0 = var1;
         }

         public Object call() throws Exception {
            return this.call();
         }

         public String call() {
            return "Integrated Server (map_client.txt)";
         }
      });
      var1.getCategory().addCrashSectionCallable("Is Modded", new Callable(this) {
         final IntegratedServer this$0;

         {
            this.this$0 = var1;
         }

         public Object call() throws Exception {
            return this.call();
         }

         public String call() {
            String var1 = ClientBrandRetriever.getClientModName();
            if (!var1.equals("vanilla")) {
               return String.valueOf((new StringBuilder("Definitely; Client brand changed to '")).append(var1).append("'"));
            } else {
               var1 = this.this$0.getServerModName();
               return !var1.equals("vanilla") ? String.valueOf((new StringBuilder("Definitely; Server brand changed to '")).append(var1).append("'")) : (Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and both client + server brands are untouched.");
            }
         }
      });
      return var1;
   }

   protected void finalTick(CrashReport var1) {
      this.mc.crashed(var1);
   }

   public int getOpPermissionLevel() {
      return 4;
   }

   public String shareToLAN(WorldSettings.GameType var1, boolean var2) {
      try {
         int var3 = -1;

         try {
            var3 = HttpUtil.getSuitableLanPort();
         } catch (IOException var5) {
         }

         if (var3 <= 0) {
            var3 = 25564;
         }

         this.getNetworkSystem().addLanEndpoint((InetAddress)null, var3);
         logger.info(String.valueOf((new StringBuilder("Started on ")).append(var3)));
         this.isPublic = true;
         this.lanServerPing = new ThreadLanServerPing(this.getMOTD(), String.valueOf(new StringBuilder(String.valueOf(var3))));
         this.lanServerPing.start();
         this.getConfigurationManager().func_152604_a(var1);
         this.getConfigurationManager().setCommandsAllowedForAll(var2);
         return String.valueOf(new StringBuilder(String.valueOf(var3)));
      } catch (IOException var6) {
         return null;
      }
   }

   public IntegratedServer(Minecraft var1, String var2, String var3, WorldSettings var4) {
      super(new File(var1.mcDataDir, "saves"), var1.getProxy(), new File(var1.mcDataDir, USER_CACHE_FILE.getName()));
      this.setServerOwner(var1.getSession().getUsername());
      this.setFolderName(var2);
      this.setWorldName(var3);
      this.setDemo(var1.isDemo());
      this.canCreateBonusChest(var4.isBonusChestEnabled());
      this.setBuildLimit(256);
      this.setConfigManager(new IntegratedPlayerList(this));
      this.mc = var1;
      this.theWorldSettings = this.isDemo() ? DemoWorldServer.demoWorldSettings : var4;
   }
}
