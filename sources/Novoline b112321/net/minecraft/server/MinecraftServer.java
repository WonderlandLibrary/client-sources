package net.minecraft.server;

import cc.novoline.Novoline;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ListenableFuture;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import net.aBt;
import net.aqE;
import net.ary;
import net.at_;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandResultStats$Type;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetworkSystem;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer$1;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldManager;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettings$GameType;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class MinecraftServer implements Runnable, ICommandSender, IThreadListener, IPlayerUsage {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final File USER_CACHE_FILE = new File("usercache.json");
   private static MinecraftServer mcServer;
   private final ISaveFormat anvilConverterForAnvilFile;
   private final PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("server", this, getCurrentTimeMillis());
   private final File anvilFile;
   private final List playersOnline = Lists.newArrayList();
   protected final ICommandManager commandManager;
   public final Profiler theProfiler = new Profiler();
   private final NetworkSystem networkSystem;
   private final ServerStatusResponse statusResponse = new ServerStatusResponse();
   private final Random random = new Random();
   private final int serverPort = -1;
   public WorldServer[] worldServers;
   private ServerConfigurationManager serverConfigManager;
   private boolean serverRunning = true;
   private boolean serverStopped;
   private int tickCounter;
   protected final Proxy serverProxy;
   public String currentTask;
   public int percentDone;
   private boolean onlineMode;
   private boolean canSpawnAnimals;
   private boolean canSpawnNPCs;
   private boolean pvpEnabled;
   private boolean allowFlight;
   private String motd;
   private int buildLimit;
   private int maxPlayerIdleMinutes = 0;
   public final long[] tickTimeArray = new long[100];
   public long[][] timeOfLastDimensionTick;
   private KeyPair serverKeyPair;
   private String serverOwner;
   private String folderName;
   private String worldName;
   private boolean isDemo;
   private boolean enableBonusChest;
   private boolean worldIsBeingDeleted;
   private String resourcePackUrl = "";
   private String resourcePackHash = "";
   private boolean serverIsRunning;
   private long timeOfLastWarning;
   private String userMessage;
   private boolean startProfiling;
   private boolean isGamemodeForced;
   private final YggdrasilAuthenticationService authService;
   private final MinecraftSessionService sessionService;
   private long nanoTimeSinceStatusRefresh = 0L;
   private final GameProfileRepository profileRepo;
   private final PlayerProfileCache profileCache;
   protected final Queue futureTaskQueue = Queues.newArrayDeque();
   private Thread serverThread;
   private long currentTime = getCurrentTimeMillis();

   public MinecraftServer(Proxy var1, File var2) {
      this.serverProxy = var1;
      mcServer = this;
      this.anvilFile = null;
      this.networkSystem = null;
      this.profileCache = new PlayerProfileCache(this, var2);
      this.commandManager = null;
      this.anvilConverterForAnvilFile = null;
      this.authService = new YggdrasilAuthenticationService(var1, UUID.randomUUID().toString());
      this.sessionService = this.authService.createMinecraftSessionService();
      this.profileRepo = this.authService.createProfileRepository();
   }

   public MinecraftServer(File var1, Proxy var2, File var3) {
      this.serverProxy = var2;
      mcServer = this;
      this.anvilFile = var1;
      this.networkSystem = new NetworkSystem(this);
      this.profileCache = new PlayerProfileCache(this, var3);
      this.commandManager = this.createNewCommandManager();
      this.anvilConverterForAnvilFile = new AnvilSaveConverter(var1);
      this.authService = new YggdrasilAuthenticationService(var2, UUID.randomUUID().toString());
      this.sessionService = this.authService.createMinecraftSessionService();
      this.profileRepo = this.authService.createProfileRepository();
   }

   protected ServerCommandManager createNewCommandManager() {
      return new ServerCommandManager("/");
   }

   protected abstract boolean startServer() throws IOException;

   protected void convertMapIfNeeded(String var1) {
      if(this.getActiveAnvilConverter().isOldMapFormat(var1)) {
         LOGGER.info("Converting map!");
         this.setUserMessage("menu.convertingLevel");
         this.getActiveAnvilConverter().convertMapFormat(var1, new MinecraftServer$1(this));
      }

   }

   public synchronized String getUserMessage() {
      return this.userMessage;
   }

   protected synchronized void setUserMessage(String var1) {
      this.userMessage = var1;
   }

   protected void loadAllWorlds(String var1, String var2, long var3, WorldType var5, String var6) {
      this.convertMapIfNeeded(var1);
      this.setUserMessage("menu.loadingLevel");
      this.worldServers = new WorldServer[3];
      this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
      ISaveHandler var7 = this.anvilConverterForAnvilFile.getSaveLoader(var1, true);
      this.setResourcePackFromWorld(this.getFolderName(), var7);
      WorldInfo var8 = var7.loadWorldInfo();
      WorldSettings var9;
      if(this.isDemo()) {
         var9 = DemoWorldServer.demoWorldSettings;
      } else {
         var9 = new WorldSettings(var3, this.getGameType(), this.canStructuresSpawn(), this.isHardcore(), var5);
         var9.setWorldName(var6);
         if(this.enableBonusChest) {
            var9.enableBonusChest();
         }
      }

      var8 = new WorldInfo(var9, var2);

      for(int var10 = 0; var10 < this.worldServers.length; ++var10) {
         byte var11 = 0;
         if(var10 == 1) {
            var11 = -1;
         }

         if(var10 == 2) {
            var11 = 1;
         }

         if(this.isDemo()) {
            this.worldServers[var10] = (WorldServer)(new DemoWorldServer(this, var7, var8, var11, this.theProfiler)).init();
         } else {
            this.worldServers[var10] = (WorldServer)(new WorldServer(this, var7, var8, var11, this.theProfiler)).init();
         }

         this.worldServers[var10].initialize(var9);
         this.worldServers[var10].addWorldAccess(new WorldManager(this, this.worldServers[var10]));
         if(!this.isSinglePlayer()) {
            this.worldServers[var10].getWorldInfo().setGameType(this.getGameType());
         }
      }

      this.serverConfigManager.setPlayerManager(this.worldServers);
      this.setDifficultyForAllWorlds(this.getDifficulty());
      this.initialWorldChunkLoad();
   }

   protected void initialWorldChunkLoad() {
      boolean var1 = true;
      boolean var2 = true;
      boolean var3 = true;
      boolean var4 = true;
      int var5 = 0;
      this.setUserMessage("menu.generatingTerrain");
      boolean var6 = false;
      LOGGER.info("Preparing start region for level 0");
      WorldServer var7 = this.worldServers[0];
      BlockPos var8 = var7.getSpawnPoint();
      long var9 = getCurrentTimeMillis();

      for(int var11 = -192; var11 <= 192 && this.isServerRunning(); var11 += 16) {
         for(int var12 = -192; var12 <= 192 && this.isServerRunning(); var12 += 16) {
            long var13 = getCurrentTimeMillis();
            if(var13 - var9 > 1000L) {
               this.outputPercentRemaining("Preparing spawn area", var5 * 100 / 625);
               var9 = var13;
            }

            ++var5;
            var7.theChunkProviderServer.loadChunk(var8.getX() + var11 >> 4, var8.getZ() + var12 >> 4);
         }
      }

      this.clearCurrentTask();
   }

   protected void setResourcePackFromWorld(String var1, ISaveHandler var2) {
      File var3 = new File(var2.getWorldDirectory(), "resources.zip");
      if(var3.isFile()) {
         this.setResourcePack("level://" + var1 + "/" + var3.getName(), "");
      }

   }

   public abstract boolean canStructuresSpawn();

   public abstract WorldSettings$GameType getGameType();

   public void setGameType(WorldSettings$GameType var1) {
      for(int var2 = 0; var2 < this.worldServers.length; ++var2) {
         getServer().worldServers[var2].getWorldInfo().setGameType(var1);
      }

   }

   public abstract EnumDifficulty getDifficulty();

   public abstract boolean isHardcore();

   public abstract int getOpPermissionLevel();

   public abstract boolean func_181034_q();

   public abstract boolean func_183002_r();

   protected void outputPercentRemaining(String var1, int var2) {
      this.currentTask = var1;
      this.percentDone = var2;
      LOGGER.info(var1 + ": " + var2 + "%");
   }

   protected void clearCurrentTask() {
      this.currentTask = null;
      this.percentDone = 0;
   }

   protected void saveAllWorlds(boolean var1) {
      if(!this.worldIsBeingDeleted) {
         for(WorldServer var5 : this.worldServers) {
            LOGGER.info("Saving chunks for level \'" + var5.getWorldInfo().getWorldName() + "\'/" + var5.provider.getDimensionName());
            WorldServer var10000 = var5;
            boolean var10001 = true;
            Object var10002 = null;

            try {
               var10000.saveAllChunks(var10001, (IProgressUpdate)var10002);
            } catch (MinecraftException var7) {
               LOGGER.warn(var7.getMessage());
            }
         }
      }

   }

   public void stopServer() {
      if(!this.worldIsBeingDeleted) {
         LOGGER.info("Stopping server");
         if(this.getNetworkSystem() != null) {
            this.getNetworkSystem().terminateEndpoints();
         }

         if(this.serverConfigManager != null) {
            LOGGER.info("Saving players");
            this.serverConfigManager.saveAllPlayerData();
            this.serverConfigManager.removeAllPlayers();
         }

         if(this.worldServers != null) {
            LOGGER.info("Saving worlds");
            this.saveAllWorlds(false);

            for(WorldServer var4 : this.worldServers) {
               var4.flush();
            }
         }

         if(this.usageSnooper.isSnooperRunning()) {
            this.usageSnooper.stopSnooper();
         }
      }

   }

   public boolean isServerRunning() {
      return this.serverRunning;
   }

   public void initiateShutdown() {
      this.serverRunning = false;
   }

   protected void setInstance() {
      mcServer = this;
   }

   public void run() {
      MinecraftServer var10000 = this;

      try {
         if(var10000.startServer()) {
            this.currentTime = getCurrentTimeMillis();
            long var1 = 0L;
            this.statusResponse.setServerDescription(new ChatComponentText(this.motd));
            this.statusResponse.a(new at_("1.8.8", 47));
            this.addFaviconToStatusResponse(this.statusResponse);

            while(this.serverRunning) {
               long var49 = getCurrentTimeMillis();
               long var5 = var49 - this.currentTime;
               if(var5 > 2000L && this.currentTime - this.timeOfLastWarning >= 15000L) {
                  LOGGER.warn("Can\'t keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)", new Object[]{Long.valueOf(var5), Long.valueOf(var5 / 50L)});
                  var5 = 2000L;
                  this.timeOfLastWarning = this.currentTime;
               }

               if(var5 < 0L) {
                  LOGGER.warn("Time ran backwards! Did the system time change?");
                  var5 = 0L;
               }

               var1 += var5;
               this.currentTime = var49;
               if(this.worldServers[0].areAllPlayersAsleep()) {
                  this.tick();
                  var1 = 0L;
               } else {
                  while(var1 > 50L) {
                     var1 -= 50L;
                     this.tick();
                  }
               }

               Thread.sleep(Math.max(1L, 50L - var1));
               this.serverIsRunning = true;
            }
         } else {
            this.finalTick((CrashReport)null);
         }
      } catch (Throwable var46) {
         LOGGER.error("Encountered an unexpected exception", var46);
         CrashReport var2 = null;
         if(var46 instanceof ReportedException) {
            var2 = this.addServerInfoToCrashReport(((ReportedException)var46).getCrashReport());
         } else {
            var2 = this.addServerInfoToCrashReport(new CrashReport("Exception in server tick loop", var46));
         }

         File var3 = new File(new File(this.getDataDirectory(), "crash-reports"), "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-server.txt");
         if(var2.saveToFile(var3)) {
            LOGGER.error("This crash report has been saved to: " + var3.getAbsolutePath());
         } else {
            LOGGER.error("We were unable to save this crash report to disk.");
         }

         this.finalTick(var2);
      } finally {
         var10000 = this;
         boolean var10001 = true;

         try {
            var10000.serverStopped = var10001;
            this.stopServer();
         } catch (Throwable var44) {
            LOGGER.error("Exception stopping the server", var44);
         } finally {
            this.systemExitNow();
         }

      }

   }

   private void addFaviconToStatusResponse(ServerStatusResponse param1) {
      // $FF: Couldn't be decompiled
   }

   public File getDataDirectory() {
      return new File(".");
   }

   protected void finalTick(CrashReport var1) {
   }

   protected void systemExitNow() {
   }

   public void tick() {
      long var1 = System.nanoTime();
      ++this.tickCounter;
      if(this.startProfiling) {
         this.startProfiling = false;
         this.theProfiler.profilingEnabled = true;
         this.theProfiler.clearProfiling();
      }

      this.theProfiler.startSection("root");
      this.updateTimeLightAndEntities();
      if(var1 - this.nanoTimeSinceStatusRefresh >= 5000000000L) {
         this.nanoTimeSinceStatusRefresh = var1;
         this.statusResponse.a(new ary(this.getMaxPlayers(), this.getCurrentPlayerCount()));
         GameProfile[] var3 = new GameProfile[Math.min(this.getCurrentPlayerCount(), 12)];
         int var4 = MathHelper.getRandomIntegerInRange(this.random, 0, this.getCurrentPlayerCount() - var3.length);

         for(int var5 = 0; var5 < var3.length; ++var5) {
            var3[var5] = ((EntityPlayerMP)this.serverConfigManager.func_181057_v().get(var4 + var5)).getGameProfile();
         }

         Collections.shuffle(Arrays.asList(var3));
         this.statusResponse.c().a(var3);
      }

      if(this.tickCounter % 900 == 0) {
         this.theProfiler.startSection("save");
         this.serverConfigManager.saveAllPlayerData();
         this.saveAllWorlds(true);
         this.theProfiler.endSection();
      }

      this.theProfiler.startSection("tallying");
      this.tickTimeArray[this.tickCounter % 100] = System.nanoTime() - var1;
      this.theProfiler.endSection();
      this.theProfiler.startSection("snooper");
      if(!this.usageSnooper.isSnooperRunning() && this.tickCounter > 100) {
         this.usageSnooper.startSnooper();
      }

      if(this.tickCounter % 6000 == 0) {
         this.usageSnooper.addMemoryStatsToSnooper();
      }

      this.theProfiler.endSection();
      this.theProfiler.endSection();
   }

   public void updateTimeLightAndEntities() {
      // $FF: Couldn't be decompiled
   }

   public boolean getAllowNether() {
      return true;
   }

   public void startServerThread() {
      this.serverThread = new Thread(this, "Server thread");
      this.serverThread.start();
   }

   public File getFile(String var1) {
      return new File(this.getDataDirectory(), var1);
   }

   public void logWarning(String var1) {
      LOGGER.warn(var1);
   }

   public WorldServer worldServerForDimension(int var1) {
      return var1 == -1?this.worldServers[1]:(var1 == 1?this.worldServers[2]:this.worldServers[0]);
   }

   public String getMinecraftVersion() {
      return "1.8.8";
   }

   public int getCurrentPlayerCount() {
      return this.serverConfigManager.getCurrentPlayerCount();
   }

   public int getMaxPlayers() {
      return this.serverConfigManager.getMaxPlayers();
   }

   public String[] getAllUsernames() {
      return this.serverConfigManager.getAllUsernames();
   }

   public GameProfile[] getGameProfiles() {
      return this.serverConfigManager.getAllProfiles();
   }

   public String getServerModName() {
      return "vanilla";
   }

   public CrashReport addServerInfoToCrashReport(CrashReport var1) {
      var1.getCategory().addCrashSectionCallable("Profiler Position", new aqE(this));
      if(this.serverConfigManager != null) {
         var1.getCategory().addCrashSectionCallable("Player Count", new aBt(this));
      }

      return var1;
   }

   public List getTabCompletions(ICommandSender var1, String var2, BlockPos var3) {
      ArrayList var4 = Lists.newArrayList();
      if(var2.startsWith("/")) {
         this.complete(var1, var2, var3, var4, this.commandManager, "/");
      } else if(var2.startsWith(".")) {
         this.complete(var1, var2, var3, var4, Novoline.getInstance().getNovoCommandHandler(), ".");
      } else {
         String[] var5 = var2.split(" ", -1);
         String var6 = var5[var5.length - 1];

         for(String var10 : this.serverConfigManager.getAllUsernames()) {
            if(CommandBase.doesStringStartWith(var6, var10)) {
               var4.add(var10);
            }
         }
      }

      return var4;
   }

   private void complete(ICommandSender var1, String var2, BlockPos var3, List var4, ICommandManager var5, String var6) {
      var2 = var2.substring(1);
      boolean var7 = !var2.contains(" ");

      for(String var10 : var5.getTabCompletionOptions(var1, var2, var3)) {
         var4.add(var6 + var10);
      }

   }

   public static MinecraftServer getServer() {
      return mcServer;
   }

   public boolean isAnvilFileSet() {
      return this.anvilFile != null;
   }

   public String getName() {
      return "Server";
   }

   public void addChatMessage(IChatComponent var1) {
      LOGGER.info(var1.getUnformattedText());
   }

   public boolean canCommandSenderUseCommand(int var1, String var2) {
      return true;
   }

   public ICommandManager getCommandManager() {
      return this.commandManager;
   }

   public KeyPair getKeyPair() {
      return this.serverKeyPair;
   }

   public void setKeyPair(KeyPair var1) {
      this.serverKeyPair = var1;
   }

   public String getServerOwner() {
      return this.serverOwner;
   }

   public void setServerOwner(String var1) {
      this.serverOwner = var1;
   }

   public boolean isSinglePlayer() {
      return this.serverOwner != null;
   }

   public String getFolderName() {
      return this.folderName;
   }

   public void setFolderName(String var1) {
      this.folderName = var1;
   }

   public String getWorldName() {
      return this.worldName;
   }

   public void setWorldName(String var1) {
      this.worldName = var1;
   }

   public void setDifficultyForAllWorlds(EnumDifficulty var1) {
      for(WorldServer var5 : this.worldServers) {
         if(var5.getWorldInfo().isHardcoreModeEnabled()) {
            var5.getWorldInfo().setDifficulty(EnumDifficulty.HARD);
            var5.setAllowedSpawnTypes(true, true);
         } else if(this.isSinglePlayer()) {
            var5.getWorldInfo().setDifficulty(var1);
            var5.setAllowedSpawnTypes(var5.getDifficulty() != EnumDifficulty.PEACEFUL, true);
         } else {
            var5.getWorldInfo().setDifficulty(var1);
            var5.setAllowedSpawnTypes(this.allowSpawnMonsters(), this.canSpawnAnimals);
         }
      }

   }

   protected boolean allowSpawnMonsters() {
      return true;
   }

   public boolean isDemo() {
      return this.isDemo;
   }

   public void setDemo(boolean var1) {
      this.isDemo = var1;
   }

   public void canCreateBonusChest(boolean var1) {
      this.enableBonusChest = var1;
   }

   public ISaveFormat getActiveAnvilConverter() {
      return this.anvilConverterForAnvilFile;
   }

   public void deleteWorldAndStopServer() {
      this.worldIsBeingDeleted = true;
      this.getActiveAnvilConverter().flushCache();

      for(WorldServer var4 : this.worldServers) {
         var4.flush();
      }

      this.getActiveAnvilConverter().deleteWorldDirectory(this.worldServers[0].getSaveHandler().getWorldDirectoryName());
      this.initiateShutdown();
   }

   public String getResourcePackUrl() {
      return this.resourcePackUrl;
   }

   public String getResourcePackHash() {
      return this.resourcePackHash;
   }

   public void setResourcePack(String var1, String var2) {
      this.resourcePackUrl = var1;
      this.resourcePackHash = var2;
   }

   public void addServerStatsToSnooper(PlayerUsageSnooper var1) {
      var1.addClientStat("whitelist_enabled", Boolean.FALSE);
      var1.addClientStat("whitelist_count", Integer.valueOf(0));
      if(this.serverConfigManager != null) {
         var1.addClientStat("players_current", Integer.valueOf(this.getCurrentPlayerCount()));
         var1.addClientStat("players_max", Integer.valueOf(this.getMaxPlayers()));
         var1.addClientStat("players_seen", Integer.valueOf(this.serverConfigManager.getAvailablePlayerDat().length));
      }

      var1.addClientStat("uses_auth", Boolean.valueOf(this.onlineMode));
      var1.addClientStat("gui_state", this.getGuiEnabled()?"enabled":"disabled");
      var1.addClientStat("run_time", Long.valueOf((getCurrentTimeMillis() - var1.getMinecraftStartTimeMillis()) / 60L * 1000L));
      var1.addClientStat("avg_tick_ms", Integer.valueOf((int)(MathHelper.average(this.tickTimeArray) * 1.0E-6D)));
      int var2 = 0;
      if(this.worldServers != null) {
         for(WorldServer var6 : this.worldServers) {
            WorldInfo var8 = var6.getWorldInfo();
            var1.addClientStat("world[" + var2 + "][dimension]", Integer.valueOf(var6.provider.getDimensionId()));
            var1.addClientStat("world[" + var2 + "][mode]", var8.getGameType());
            var1.addClientStat("world[" + var2 + "][difficulty]", var6.getDifficulty());
            var1.addClientStat("world[" + var2 + "][hardcore]", Boolean.valueOf(var8.isHardcoreModeEnabled()));
            var1.addClientStat("world[" + var2 + "][generator_name]", var8.getTerrainType().getWorldTypeName());
            var1.addClientStat("world[" + var2 + "][generator_version]", Integer.valueOf(var8.getTerrainType().getGeneratorVersion()));
            var1.addClientStat("world[" + var2 + "][height]", Integer.valueOf(this.buildLimit));
            var1.addClientStat("world[" + var2 + "][chunks_loaded]", Integer.valueOf(var6.getChunkProvider().getLoadedChunkCount()));
            ++var2;
         }
      }

      var1.addClientStat("worlds", Integer.valueOf(var2));
   }

   public void addServerTypeToSnooper(PlayerUsageSnooper var1) {
      var1.addStatToSnooper("singleplayer", Boolean.valueOf(this.isSinglePlayer()));
      var1.addStatToSnooper("server_brand", this.getServerModName());
      var1.addStatToSnooper("gui_supported", GraphicsEnvironment.isHeadless()?"headless":"supported");
      var1.addStatToSnooper("dedicated", Boolean.valueOf(this.isDedicatedServer()));
   }

   public boolean isSnooperEnabled() {
      return true;
   }

   public abstract boolean isDedicatedServer();

   public boolean isServerInOnlineMode() {
      return this.onlineMode;
   }

   public void setOnlineMode(boolean var1) {
      this.onlineMode = var1;
   }

   public boolean getCanSpawnAnimals() {
      return this.canSpawnAnimals;
   }

   public void setCanSpawnAnimals(boolean var1) {
      this.canSpawnAnimals = var1;
   }

   public boolean getCanSpawnNPCs() {
      return this.canSpawnNPCs;
   }

   public void setCanSpawnNPCs(boolean var1) {
      this.canSpawnNPCs = var1;
   }

   public abstract boolean func_181035_ah();

   public boolean isPVPEnabled() {
      return this.pvpEnabled;
   }

   public void setAllowPvp(boolean var1) {
      this.pvpEnabled = var1;
   }

   public boolean isFlightAllowed() {
      return this.allowFlight;
   }

   public void setAllowFlight(boolean var1) {
      this.allowFlight = var1;
   }

   public abstract boolean isCommandBlockEnabled();

   public String getMOTD() {
      return this.motd;
   }

   public void setMOTD(String var1) {
      this.motd = var1;
   }

   public int getBuildLimit() {
      return this.buildLimit;
   }

   public void setBuildLimit(int var1) {
      this.buildLimit = var1;
   }

   public boolean isServerStopped() {
      return this.serverStopped;
   }

   public ServerConfigurationManager getConfigurationManager() {
      return this.serverConfigManager;
   }

   public void setConfigManager(ServerConfigurationManager var1) {
      this.serverConfigManager = var1;
   }

   public NetworkSystem getNetworkSystem() {
      return this.networkSystem;
   }

   public boolean serverIsInRunLoop() {
      return this.serverIsRunning;
   }

   public boolean getGuiEnabled() {
      return false;
   }

   public abstract String shareToLAN(WorldSettings$GameType var1, boolean var2);

   public int getTickCounter() {
      return this.tickCounter;
   }

   public void enableProfiling() {
      this.startProfiling = true;
   }

   public PlayerUsageSnooper getPlayerUsageSnooper() {
      return this.usageSnooper;
   }

   public BlockPos getPosition() {
      return BlockPos.ORIGIN;
   }

   public Vec3 getPositionVector() {
      return new Vec3(0.0D, 0.0D, 0.0D);
   }

   public World getEntityWorld() {
      return this.worldServers[0];
   }

   public Entity getCommandSenderEntity() {
      return null;
   }

   public int getSpawnProtectionSize() {
      return 16;
   }

   public boolean isBlockProtected(World var1, BlockPos var2, EntityPlayer var3) {
      return false;
   }

   public boolean getForceGamemode() {
      return this.isGamemodeForced;
   }

   public Proxy getServerProxy() {
      return this.serverProxy;
   }

   public static long getCurrentTimeMillis() {
      return System.currentTimeMillis();
   }

   public int getMaxPlayerIdleMinutes() {
      return this.maxPlayerIdleMinutes;
   }

   public void setPlayerIdleTimeout(int var1) {
      this.maxPlayerIdleMinutes = var1;
   }

   public IChatComponent getDisplayName() {
      return new ChatComponentText(this.getName());
   }

   public boolean isAnnouncingPlayerAchievements() {
      return true;
   }

   public MinecraftSessionService getMinecraftSessionService() {
      return this.sessionService;
   }

   public GameProfileRepository getGameProfileRepository() {
      return this.profileRepo;
   }

   public PlayerProfileCache getPlayerProfileCache() {
      return this.profileCache;
   }

   public ServerStatusResponse getServerStatusResponse() {
      return this.statusResponse;
   }

   public void refreshStatusNextTick() {
      this.nanoTimeSinceStatusRefresh = 0L;
   }

   public Entity getEntityFromUuid(UUID var1) {
      WorldServer[] var2 = this.worldServers;
      int var3 = var2.length;
      byte var4 = 0;
      if(var4 < var3) {
         WorldServer var5 = var2[var4];
         Entity var6 = var5.getEntityFromUuid(var1);
         return var6;
      } else {
         return null;
      }
   }

   public boolean sendCommandFeedback() {
      return getServer().worldServers[0].getGameRules().getBoolean("sendCommandFeedback");
   }

   public void setCommandStat(CommandResultStats$Type var1, int var2) {
   }

   public int getMaxWorldSize() {
      return 29999984;
   }

   public ListenableFuture callFromMainThread(Callable param1) {
      // $FF: Couldn't be decompiled
   }

   public ListenableFuture addScheduledTask(Runnable var1) {
      Validate.notNull(var1);
      return this.callFromMainThread(Executors.callable(var1));
   }

   public boolean isCallingFromMinecraftThread() {
      return Thread.currentThread() == this.serverThread;
   }

   public int getNetworkCompressionTreshold() {
      return 256;
   }

   static Logger access$000() {
      return LOGGER;
   }

   static ServerConfigurationManager access$100(MinecraftServer var0) {
      return var0.serverConfigManager;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
