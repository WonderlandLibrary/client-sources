package net.minecraft.server;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import javax.imageio.ImageIO;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetworkSystem;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.gui.IUpdatePlayerListBox;
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
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class MinecraftServer implements ICommandSender, Runnable, IThreadListener, IPlayerUsage {
   private String folderName;
   private int serverPort = -1;
   private final Random random = new Random();
   public String currentTask;
   private int buildLimit;
   private boolean canSpawnNPCs;
   private boolean serverRunning = true;
   public final Profiler theProfiler = new Profiler();
   private String userMessage;
   private final PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("server", this, getCurrentTimeMillis());
   private boolean serverIsRunning;
   private boolean allowFlight;
   private final List playersOnline = Lists.newArrayList();
   private static MinecraftServer mcServer;
   public final long[] tickTimeArray = new long[100];
   private ServerConfigurationManager serverConfigManager;
   public static final File USER_CACHE_FILE = new File("usercache.json");
   private final MinecraftSessionService sessionService;
   private static final Logger logger = LogManager.getLogger();
   private final PlayerProfileCache profileCache;
   private final GameProfileRepository profileRepo;
   private static final String __OBFID = "CL_00001462";
   private final ServerStatusResponse statusResponse = new ServerStatusResponse();
   private final ICommandManager commandManager;
   private boolean enableBonusChest;
   private boolean onlineMode;
   private int tickCounter;
   private String motd;
   private String resourcePackHash = "";
   protected final Proxy serverProxy;
   private long timeOfLastWarning;
   private final File anvilFile;
   private boolean pvpEnabled;
   private boolean isDemo;
   private int maxPlayerIdleMinutes = 0;
   private String serverOwner;
   private boolean isGamemodeForced;
   private final YggdrasilAuthenticationService authService;
   private String worldName;
   private Thread serverThread;
   public long[][] timeOfLastDimensionTick;
   private long nanoTimeSinceStatusRefresh = 0L;
   private long currentTime = getCurrentTimeMillis();
   public int percentDone;
   public WorldServer[] worldServers;
   private KeyPair serverKeyPair;
   private boolean startProfiling;
   private boolean serverStopped;
   protected final Queue futureTaskQueue = Queues.newArrayDeque();
   private boolean canSpawnAnimals;
   private final NetworkSystem networkSystem;
   private String resourcePackUrl = "";
   private boolean worldIsBeingDeleted;
   private final ISaveFormat anvilConverterForAnvilFile;

   public abstract int getOpPermissionLevel();

   public void setConfigManager(ServerConfigurationManager var1) {
      this.serverConfigManager = var1;
   }

   public boolean getCanSpawnNPCs() {
      return this.canSpawnNPCs;
   }

   public boolean getForceGamemode() {
      return this.isGamemodeForced;
   }

   public File getDataDirectory() {
      return new File(".");
   }

   public boolean isServerInOnlineMode() {
      return this.onlineMode;
   }

   public void updateTimeLightAndEntities() {
      this.theProfiler.startSection("jobs");
      Queue var1 = this.futureTaskQueue;
      synchronized(this.futureTaskQueue) {
         while(!this.futureTaskQueue.isEmpty()) {
            try {
               ((FutureTask)this.futureTaskQueue.poll()).run();
            } catch (Throwable var10) {
               logger.fatal(var10);
            }
         }
      }

      this.theProfiler.endStartSection("levels");

      int var2;
      for(var2 = 0; var2 < this.worldServers.length; ++var2) {
         long var3 = System.nanoTime();
         if (var2 == 0 || this.getAllowNether()) {
            WorldServer var5 = this.worldServers[var2];
            this.theProfiler.startSection(var5.getWorldInfo().getWorldName());
            if (this.tickCounter % 20 == 0) {
               this.theProfiler.startSection("timeSync");
               this.serverConfigManager.sendPacketToAllPlayersInDimension(new S03PacketTimeUpdate(var5.getTotalWorldTime(), var5.getWorldTime(), var5.getGameRules().getGameRuleBooleanValue("doDaylightCycle")), var5.provider.getDimensionId());
               this.theProfiler.endSection();
            }

            this.theProfiler.startSection("tick");

            CrashReport var6;
            try {
               var5.tick();
            } catch (Throwable var9) {
               var6 = CrashReport.makeCrashReport(var9, "Exception ticking world");
               var5.addWorldInfoToCrashReport(var6);
               throw new ReportedException(var6);
            }

            try {
               var5.updateEntities();
            } catch (Throwable var8) {
               var6 = CrashReport.makeCrashReport(var8, "Exception ticking world entities");
               var5.addWorldInfoToCrashReport(var6);
               throw new ReportedException(var6);
            }

            this.theProfiler.endSection();
            this.theProfiler.startSection("tracker");
            var5.getEntityTracker().updateTrackedEntities();
            this.theProfiler.endSection();
            this.theProfiler.endSection();
         }

         this.timeOfLastDimensionTick[var2][this.tickCounter % 100] = System.nanoTime() - var3;
      }

      this.theProfiler.endStartSection("connection");
      this.getNetworkSystem().networkTick();
      this.theProfiler.endStartSection("players");
      this.serverConfigManager.onTick();
      this.theProfiler.endStartSection("tickables");

      for(var2 = 0; var2 < this.playersOnline.size(); ++var2) {
         ((IUpdatePlayerListBox)this.playersOnline.get(var2)).update();
      }

      this.theProfiler.endSection();
   }

   public abstract WorldSettings.GameType getGameType();

   public int getBuildLimit() {
      return this.buildLimit;
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

   public void stopServer() {
      if (!this.worldIsBeingDeleted) {
         logger.info("Stopping server");
         if (this.getNetworkSystem() != null) {
            this.getNetworkSystem().terminateEndpoints();
         }

         if (this.serverConfigManager != null) {
            logger.info("Saving players");
            this.serverConfigManager.saveAllPlayerData();
            this.serverConfigManager.removeAllPlayers();
         }

         if (this.worldServers != null) {
            logger.info("Saving worlds");
            this.saveAllWorlds(false);

            for(int var1 = 0; var1 < this.worldServers.length; ++var1) {
               WorldServer var2 = this.worldServers[var1];
               var2.flush();
            }
         }

         if (this.usageSnooper.isSnooperRunning()) {
            this.usageSnooper.stopSnooper();
         }
      }

   }

   public int getCurrentPlayerCount() {
      return this.serverConfigManager.getCurrentPlayerCount();
   }

   public boolean sendCommandFeedback() {
      return getServer().worldServers[0].getGameRules().getGameRuleBooleanValue("sendCommandFeedback");
   }

   public void setAllowFlight(boolean var1) {
      this.allowFlight = var1;
   }

   public int getMaxPlayerIdleMinutes() {
      return this.maxPlayerIdleMinutes;
   }

   protected ServerCommandManager createNewCommandManager() {
      return new ServerCommandManager();
   }

   public ServerConfigurationManager getConfigurationManager() {
      return this.serverConfigManager;
   }

   public abstract EnumDifficulty getDifficulty();

   public WorldServer worldServerForDimension(int var1) {
      return var1 == -1 ? this.worldServers[1] : (var1 == 1 ? this.worldServers[2] : this.worldServers[0]);
   }

   public Entity getEntityFromUuid(UUID var1) {
      WorldServer[] var2 = this.worldServers;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         WorldServer var5 = var2[var4];
         if (var5 != null) {
            Entity var6 = var5.getEntityFromUuid(var1);
            if (var6 != null) {
               return var6;
            }
         }
      }

      return null;
   }

   public String getName() {
      return "Server";
   }

   public boolean isDemo() {
      return this.isDemo;
   }

   public void tick() {
      long var1 = System.nanoTime();
      ++this.tickCounter;
      if (this.startProfiling) {
         this.startProfiling = false;
         this.theProfiler.profilingEnabled = true;
         this.theProfiler.clearProfiling();
      }

      this.theProfiler.startSection("root");
      this.updateTimeLightAndEntities();
      if (var1 - this.nanoTimeSinceStatusRefresh >= 5000000000L) {
         this.nanoTimeSinceStatusRefresh = var1;
         this.statusResponse.setPlayerCountData(new ServerStatusResponse.PlayerCountData(this.getMaxPlayers(), this.getCurrentPlayerCount()));
         GameProfile[] var3 = new GameProfile[Math.min(this.getCurrentPlayerCount(), 12)];
         int var4 = MathHelper.getRandomIntegerInRange(this.random, 0, this.getCurrentPlayerCount() - var3.length);

         for(int var5 = 0; var5 < var3.length; ++var5) {
            var3[var5] = ((EntityPlayerMP)this.serverConfigManager.playerEntityList.get(var4 + var5)).getGameProfile();
         }

         Collections.shuffle(Arrays.asList(var3));
         this.statusResponse.getPlayerCountData().setPlayers(var3);
      }

      if (this.tickCounter % 900 == 0) {
         this.theProfiler.startSection("save");
         this.serverConfigManager.saveAllPlayerData();
         this.saveAllWorlds(true);
         this.theProfiler.endSection();
      }

      this.theProfiler.startSection("tallying");
      this.tickTimeArray[this.tickCounter % 100] = System.nanoTime() - var1;
      this.theProfiler.endSection();
      this.theProfiler.startSection("snooper");
      if (!this.usageSnooper.isSnooperRunning() && this.tickCounter > 100) {
         this.usageSnooper.startSnooper();
      }

      if (this.tickCounter % 6000 == 0) {
         this.usageSnooper.addMemoryStatsToSnooper();
      }

      this.theProfiler.endSection();
      this.theProfiler.endSection();
   }

   public boolean serverIsInRunLoop() {
      return this.serverIsRunning;
   }

   public ISaveFormat getActiveAnvilConverter() {
      return this.anvilConverterForAnvilFile;
   }

   public int getTickCounter() {
      return this.tickCounter;
   }

   public void setDifficultyForAllWorlds(EnumDifficulty var1) {
      for(int var2 = 0; var2 < this.worldServers.length; ++var2) {
         WorldServer var3 = this.worldServers[var2];
         if (var3 != null) {
            if (var3.getWorldInfo().isHardcoreModeEnabled()) {
               var3.getWorldInfo().setDifficulty(EnumDifficulty.HARD);
               var3.setAllowedSpawnTypes(true, true);
            } else if (this.isSinglePlayer()) {
               var3.getWorldInfo().setDifficulty(var1);
               var3.setAllowedSpawnTypes(var3.getDifficulty() != EnumDifficulty.PEACEFUL, true);
            } else {
               var3.getWorldInfo().setDifficulty(var1);
               var3.setAllowedSpawnTypes(this.allowSpawnMonsters(), this.canSpawnAnimals);
            }
         }
      }

   }

   public boolean isBlockProtected(World var1, BlockPos var2, EntityPlayer var3) {
      return false;
   }

   public String getMOTD() {
      return this.motd;
   }

   public void setWorldName(String var1) {
      this.worldName = var1;
   }

   public void startServerThread() {
      this.serverThread = new Thread(this, "Server thread");
      this.serverThread.start();
   }

   public void setDemo(boolean var1) {
      this.isDemo = var1;
   }

   public String getServerOwner() {
      return this.serverOwner;
   }

   public boolean isPVPEnabled() {
      return this.pvpEnabled;
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
      if (var8 == null) {
         if (this.isDemo()) {
            var9 = DemoWorldServer.demoWorldSettings;
         } else {
            var9 = new WorldSettings(var3, this.getGameType(), this.canStructuresSpawn(), this.isHardcore(), var5);
            var9.setWorldName(var6);
            if (this.enableBonusChest) {
               var9.enableBonusChest();
            }
         }

         var8 = new WorldInfo(var9, var2);
      } else {
         var8.setWorldName(var2);
         var9 = new WorldSettings(var8);
      }

      for(int var10 = 0; var10 < this.worldServers.length; ++var10) {
         byte var11 = 0;
         if (var10 == 1) {
            var11 = -1;
         }

         if (var10 == 2) {
            var11 = 1;
         }

         if (var10 == 0) {
            if (this.isDemo()) {
               this.worldServers[var10] = (WorldServer)(new DemoWorldServer(this, var7, var8, var11, this.theProfiler)).init();
            } else {
               this.worldServers[var10] = (WorldServer)(new WorldServer(this, var7, var8, var11, this.theProfiler)).init();
            }

            this.worldServers[var10].initialize(var9);
         } else {
            this.worldServers[var10] = (WorldServer)(new WorldServerMulti(this, var7, var11, this.worldServers[0], this.theProfiler)).init();
         }

         this.worldServers[var10].addWorldAccess(new WorldManager(this, this.worldServers[var10]));
         if (!this.isSinglePlayer()) {
            this.worldServers[var10].getWorldInfo().setGameType(this.getGameType());
         }
      }

      this.serverConfigManager.setPlayerManager(this.worldServers);
      this.setDifficultyForAllWorlds(this.getDifficulty());
      this.initialWorldChunkLoad();
   }

   public boolean canCommandSenderUseCommand(int var1, String var2) {
      return true;
   }

   public void setBuildLimit(int var1) {
      this.buildLimit = var1;
   }

   public GameProfile[] getGameProfiles() {
      return this.serverConfigManager.getAllProfiles();
   }

   public abstract String shareToLAN(WorldSettings.GameType var1, boolean var2);

   public CrashReport addServerInfoToCrashReport(CrashReport var1) {
      var1.getCategory().addCrashSectionCallable("Profiler Position", new Callable(this) {
         final MinecraftServer this$0;
         private static final String __OBFID = "CL_00001418";

         public String func_179879_a() {
            return this.this$0.theProfiler.profilingEnabled ? this.this$0.theProfiler.getNameOfLastSection() : "N/A (disabled)";
         }

         {
            this.this$0 = var1;
         }

         public Object call() {
            return this.func_179879_a();
         }
      });
      if (this.serverConfigManager != null) {
         var1.getCategory().addCrashSectionCallable("Player Count", new Callable(this) {
            final MinecraftServer this$0;
            private static final String __OBFID = "CL_00001419";

            public Object call() throws Exception {
               return this.call();
            }

            {
               this.this$0 = var1;
            }

            public String call() {
               return String.valueOf((new StringBuilder(String.valueOf(MinecraftServer.access$1(this.this$0).getCurrentPlayerCount()))).append(" / ").append(MinecraftServer.access$1(this.this$0).getMaxPlayers()).append("; ").append(MinecraftServer.access$1(this.this$0).playerEntityList));
            }
         });
      }

      return var1;
   }

   public PlayerUsageSnooper getPlayerUsageSnooper() {
      return this.usageSnooper;
   }

   public void logWarning(String var1) {
      logger.warn(var1);
   }

   static Logger access$0() {
      return logger;
   }

   public MinecraftSessionService getMinecraftSessionService() {
      return this.sessionService;
   }

   public void setMOTD(String var1) {
      this.motd = var1;
   }

   public Vec3 getPositionVector() {
      return new Vec3(0.0D, 0.0D, 0.0D);
   }

   public void setCanSpawnAnimals(boolean var1) {
      this.canSpawnAnimals = var1;
   }

   public void initiateShutdown() {
      this.serverRunning = false;
   }

   public void setGameType(WorldSettings.GameType var1) {
      for(int var2 = 0; var2 < this.worldServers.length; ++var2) {
         getServer().worldServers[var2].getWorldInfo().setGameType(var1);
      }

   }

   protected void func_175585_v() {
      mcServer = this;
   }

   public NetworkSystem getNetworkSystem() {
      return this.networkSystem;
   }

   public GameProfileRepository getGameProfileRepository() {
      return this.profileRepo;
   }

   public void setCanSpawnNPCs(boolean var1) {
      this.canSpawnNPCs = var1;
   }

   public ListenableFuture addScheduledTask(Runnable var1) {
      Validate.notNull(var1);
      return this.callFromMainThread(Executors.callable(var1));
   }

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

   protected void outputPercentRemaining(String var1, int var2) {
      this.currentTask = var1;
      this.percentDone = var2;
      logger.info(String.valueOf((new StringBuilder(String.valueOf(var1))).append(": ").append(var2).append("%")));
   }

   private void addFaviconToStatusResponse(ServerStatusResponse var1) {
      File var2 = this.getFile("server-icon.png");
      if (var2.isFile()) {
         ByteBuf var3 = Unpooled.buffer();

         try {
            BufferedImage var4 = ImageIO.read(var2);
            Validate.validState(var4.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
            Validate.validState(var4.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
            ImageIO.write(var4, "PNG", new ByteBufOutputStream(var3));
            ByteBuf var5 = Base64.encode(var3);
            var1.setFavicon(String.valueOf((new StringBuilder("data:image/png;base64,")).append(var5.toString(Charsets.UTF_8))));
         } catch (Exception var9) {
            logger.error("Couldn't load server icon", var9);
         } finally {
            var3.release();
         }
      }

   }

   public String getFolderName() {
      return this.folderName;
   }

   public boolean isSinglePlayer() {
      return this.serverOwner != null;
   }

   protected void saveAllWorlds(boolean var1) {
      if (!this.worldIsBeingDeleted) {
         WorldServer[] var2 = this.worldServers;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            WorldServer var5 = var2[var4];
            if (var5 != null) {
               if (!var1) {
                  logger.info(String.valueOf((new StringBuilder("Saving chunks for level '")).append(var5.getWorldInfo().getWorldName()).append("'/").append(var5.provider.getDimensionName())));
               }

               try {
                  var5.saveAllChunks(true, (IProgressUpdate)null);
               } catch (MinecraftException var7) {
                  logger.warn(var7.getMessage());
               }
            }
         }
      }

   }

   static ServerConfigurationManager access$1(MinecraftServer var0) {
      return var0.serverConfigManager;
   }

   public File getFile(String var1) {
      return new File(this.getDataDirectory(), var1);
   }

   public abstract boolean isDedicatedServer();

   public boolean getGuiEnabled() {
      return false;
   }

   protected synchronized void setUserMessage(String var1) {
      this.userMessage = var1;
   }

   public String[] getAllUsernames() {
      return this.serverConfigManager.getAllUsernames();
   }

   public boolean isAnnouncingPlayerAchievements() {
      return true;
   }

   public void setKeyPair(KeyPair var1) {
      this.serverKeyPair = var1;
   }

   public List func_180506_a(ICommandSender var1, String var2, BlockPos var3) {
      ArrayList var4 = Lists.newArrayList();
      if (var2.startsWith("/")) {
         var2 = var2.substring(1);
         boolean var11 = !var2.contains(" ");
         List var12 = this.commandManager.getTabCompletionOptions(var1, var2, var3);
         if (var12 != null) {
            Iterator var13 = var12.iterator();

            while(var13.hasNext()) {
               String var14 = (String)var13.next();
               if (var11) {
                  var4.add(String.valueOf((new StringBuilder("/")).append(var14)));
               } else {
                  var4.add(var14);
               }
            }
         }

         return var4;
      } else {
         String[] var5 = var2.split(" ", -1);
         String var6 = var5[var5.length - 1];
         String[] var7 = this.serverConfigManager.getAllUsernames();
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            String var10 = var7[var9];
            if (CommandBase.doesStringStartWith(var6, var10)) {
               var4.add(var10);
            }
         }

         return var4;
      }
   }

   protected void convertMapIfNeeded(String var1) {
      if (this.getActiveAnvilConverter().isOldMapFormat(var1)) {
         logger.info("Converting map!");
         this.setUserMessage("menu.convertingLevel");
         this.getActiveAnvilConverter().convertMapFormat(var1, new IProgressUpdate(this) {
            private long startTime;
            final MinecraftServer this$0;
            private static final String __OBFID = "CL_00001417";

            public void setLoadingProgress(int var1) {
               if (System.currentTimeMillis() - this.startTime >= 1000L) {
                  this.startTime = System.currentTimeMillis();
                  MinecraftServer.access$0().info(String.valueOf((new StringBuilder("Converting... ")).append(var1).append("%")));
               }

            }

            {
               this.this$0 = var1;
               this.startTime = System.currentTimeMillis();
            }

            public void resetProgressAndMessage(String var1) {
            }

            public void displaySavingString(String var1) {
            }

            public void displayLoadingString(String var1) {
            }

            public void setDoneWorking() {
            }
         });
      }

   }

   public void setOnlineMode(boolean var1) {
      this.onlineMode = var1;
   }

   public void canCreateBonusChest(boolean var1) {
      this.enableBonusChest = var1;
   }

   public ServerStatusResponse getServerStatusResponse() {
      return this.statusResponse;
   }

   public void addChatMessage(IChatComponent var1) {
      logger.info(var1.getUnformattedText());
   }

   public void addServerTypeToSnooper(PlayerUsageSnooper var1) {
      var1.addStatToSnooper("singleplayer", this.isSinglePlayer());
      var1.addStatToSnooper("server_brand", this.getServerModName());
      var1.addStatToSnooper("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
      var1.addStatToSnooper("dedicated", this.isDedicatedServer());
   }

   public boolean isFlightAllowed() {
      return this.allowFlight;
   }

   public boolean getAllowNether() {
      return true;
   }

   public static long getCurrentTimeMillis() {
      return System.currentTimeMillis();
   }

   public void enableProfiling() {
      this.startProfiling = true;
   }

   protected void systemExitNow() {
   }

   public ListenableFuture callFromMainThread(Callable var1) {
      Validate.notNull(var1);
      if (!this.isCallingFromMinecraftThread()) {
         ListenableFutureTask var2 = ListenableFutureTask.create(var1);
         Queue var3 = this.futureTaskQueue;
         synchronized(this.futureTaskQueue) {
            this.futureTaskQueue.add(var2);
            return var2;
         }
      } else {
         try {
            return Futures.immediateFuture(var1.call());
         } catch (Exception var6) {
            return Futures.immediateFailedCheckedFuture(var6);
         }
      }
   }

   protected void finalTick(CrashReport var1) {
   }

   public IChatComponent getDisplayName() {
      return new ChatComponentText(this.getName());
   }

   public void setAllowPvp(boolean var1) {
      this.pvpEnabled = var1;
   }

   public BlockPos getPosition() {
      return BlockPos.ORIGIN;
   }

   public void func_174794_a(CommandResultStats.Type var1, int var2) {
   }

   public abstract boolean canStructuresSpawn();

   protected void clearCurrentTask() {
      this.currentTask = null;
      this.percentDone = 0;
   }

   public static MinecraftServer getServer() {
      return mcServer;
   }

   public void setPlayerIdleTimeout(int var1) {
      this.maxPlayerIdleMinutes = var1;
   }

   protected abstract boolean startServer() throws IOException;

   public Entity getCommandSenderEntity() {
      return null;
   }

   public String getMinecraftVersion() {
      return "1.8";
   }

   public void setFolderName(String var1) {
      this.folderName = var1;
   }

   public boolean func_175578_N() {
      return this.anvilFile != null;
   }

   public boolean isServerRunning() {
      return this.serverRunning;
   }

   public void run() {
      boolean var43 = false;

      label541: {
         try {
            CrashReport var2;
            try {
               var43 = true;
               if (this.startServer()) {
                  this.currentTime = getCurrentTimeMillis();
                  long var1 = 0L;
                  this.statusResponse.setServerDescription(new ChatComponentText(this.motd));
                  this.statusResponse.setProtocolVersionInfo(new ServerStatusResponse.MinecraftProtocolVersionIdentifier("1.8", 47));
                  this.addFaviconToStatusResponse(this.statusResponse);

                  while(this.serverRunning) {
                     long var52 = getCurrentTimeMillis();
                     long var5 = var52 - this.currentTime;
                     if (var5 > 2000L && this.currentTime - this.timeOfLastWarning >= 15000L) {
                        logger.warn("Can't keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)", new Object[]{var5, var5 / 50L});
                        var5 = 2000L;
                        this.timeOfLastWarning = this.currentTime;
                     }

                     if (var5 < 0L) {
                        logger.warn("Time ran backwards! Did the system time change?");
                        var5 = 0L;
                     }

                     var1 += var5;
                     this.currentTime = var52;
                     if (this.worldServers[0].areAllPlayersAsleep()) {
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

                  var43 = false;
                  break label541;
               }

               this.finalTick((CrashReport)null);
               var43 = false;
               break label541;
            } catch (Throwable var50) {
               logger.error("Encountered an unexpected exception", var50);
               var2 = null;
               if (var50 instanceof ReportedException) {
                  var2 = this.addServerInfoToCrashReport(((ReportedException)var50).getCrashReport());
               } else {
                  var2 = this.addServerInfoToCrashReport(new CrashReport("Exception in server tick loop", var50));
               }
            }

            File var3 = new File(new File(this.getDataDirectory(), "crash-reports"), String.valueOf((new StringBuilder("crash-")).append((new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date())).append("-server.txt")));
            if (var2.saveToFile(var3)) {
               logger.error(String.valueOf((new StringBuilder("This crash report has been saved to: ")).append(var3.getAbsolutePath())));
            } else {
               logger.error("We were unable to save this crash report to disk.");
            }

            this.finalTick(var2);
            var43 = false;
         } finally {
            if (var43) {
               try {
                  this.stopServer();
                  this.serverStopped = true;
               } catch (Throwable var44) {
                  logger.error("Exception stopping the server", var44);
               } finally {
                  this.systemExitNow();
               }

            }
         }

         boolean var26 = false;

         try {
            label542: {
               try {
                  var26 = true;
                  this.stopServer();
                  this.serverStopped = true;
               } catch (Throwable var48) {
                  logger.error("Exception stopping the server", var48);
                  var26 = false;
                  break label542;
               }

               this.systemExitNow();
               var26 = false;
               return;
            }
         } finally {
            if (var26) {
               this.systemExitNow();
            }
         }

         this.systemExitNow();
         return;
      }

      try {
         this.stopServer();
         this.serverStopped = true;
      } catch (Throwable var46) {
         logger.error("Exception stopping the server", var46);
      } finally {
         this.systemExitNow();
      }

   }

   public void deleteWorldAndStopServer() {
      this.worldIsBeingDeleted = true;
      this.getActiveAnvilConverter().flushCache();

      for(int var1 = 0; var1 < this.worldServers.length; ++var1) {
         WorldServer var2 = this.worldServers[var1];
         if (var2 != null) {
            var2.flush();
         }
      }

      this.getActiveAnvilConverter().deleteWorldDirectory(this.worldServers[0].getSaveHandler().getWorldDirectoryName());
      this.initiateShutdown();
   }

   public void setResourcePack(String var1, String var2) {
      this.resourcePackUrl = var1;
      this.resourcePackHash = var2;
   }

   public KeyPair getKeyPair() {
      return this.serverKeyPair;
   }

   public boolean isCallingFromMinecraftThread() {
      return Thread.currentThread() == this.serverThread;
   }

   public void setServerOwner(String var1) {
      this.serverOwner = var1;
   }

   public synchronized String getUserMessage() {
      return this.userMessage;
   }

   protected void setResourcePackFromWorld(String var1, ISaveHandler var2) {
      File var3 = new File(var2.getWorldDirectory(), "resources.zip");
      if (var3.isFile()) {
         this.setResourcePack(String.valueOf((new StringBuilder("level://")).append(var1).append("/").append(var3.getName())), "");
      }

   }

   public ICommandManager getCommandManager() {
      return this.commandManager;
   }

   public int getMaxWorldSize() {
      return 29999984;
   }

   public PlayerProfileCache getPlayerProfileCache() {
      return this.profileCache;
   }

   public int getNetworkCompressionTreshold() {
      return 256;
   }

   public String getResourcePackHash() {
      return this.resourcePackHash;
   }

   public Proxy getServerProxy() {
      return this.serverProxy;
   }

   protected boolean allowSpawnMonsters() {
      return true;
   }

   public void refreshStatusNextTick() {
      this.nanoTimeSinceStatusRefresh = 0L;
   }

   public int getSpawnProtectionSize() {
      return 16;
   }

   public boolean getCanSpawnAnimals() {
      return this.canSpawnAnimals;
   }

   public void addServerStatsToSnooper(PlayerUsageSnooper var1) {
      var1.addClientStat("whitelist_enabled", false);
      var1.addClientStat("whitelist_count", 0);
      if (this.serverConfigManager != null) {
         var1.addClientStat("players_current", this.getCurrentPlayerCount());
         var1.addClientStat("players_max", this.getMaxPlayers());
         var1.addClientStat("players_seen", this.serverConfigManager.getAvailablePlayerDat().length);
      }

      var1.addClientStat("uses_auth", this.onlineMode);
      var1.addClientStat("gui_state", this.getGuiEnabled() ? "enabled" : "disabled");
      var1.addClientStat("run_time", (getCurrentTimeMillis() - var1.getMinecraftStartTimeMillis()) / 60L * 1000L);
      var1.addClientStat("avg_tick_ms", (int)(MathHelper.average(this.tickTimeArray) * 1.0E-6D));
      int var2 = 0;
      if (this.worldServers != null) {
         for(int var3 = 0; var3 < this.worldServers.length; ++var3) {
            if (this.worldServers[var3] != null) {
               WorldServer var4 = this.worldServers[var3];
               WorldInfo var5 = var4.getWorldInfo();
               var1.addClientStat(String.valueOf((new StringBuilder("world[")).append(var2).append("][dimension]")), var4.provider.getDimensionId());
               var1.addClientStat(String.valueOf((new StringBuilder("world[")).append(var2).append("][mode]")), var5.getGameType());
               var1.addClientStat(String.valueOf((new StringBuilder("world[")).append(var2).append("][difficulty]")), var4.getDifficulty());
               var1.addClientStat(String.valueOf((new StringBuilder("world[")).append(var2).append("][hardcore]")), var5.isHardcoreModeEnabled());
               var1.addClientStat(String.valueOf((new StringBuilder("world[")).append(var2).append("][generator_name]")), var5.getTerrainType().getWorldTypeName());
               var1.addClientStat(String.valueOf((new StringBuilder("world[")).append(var2).append("][generator_version]")), var5.getTerrainType().getGeneratorVersion());
               var1.addClientStat(String.valueOf((new StringBuilder("world[")).append(var2).append("][height]")), this.buildLimit);
               var1.addClientStat(String.valueOf((new StringBuilder("world[")).append(var2).append("][chunks_loaded]")), var4.getChunkProvider().getLoadedChunkCount());
               ++var2;
            }
         }
      }

      var1.addClientStat("worlds", var2);
   }

   public abstract boolean isCommandBlockEnabled();

   public World getEntityWorld() {
      return this.worldServers[0];
   }

   public String getWorldName() {
      return this.worldName;
   }

   public boolean isSnooperEnabled() {
      return true;
   }

   public String getResourcePackUrl() {
      return this.resourcePackUrl;
   }

   public int getMaxPlayers() {
      return this.serverConfigManager.getMaxPlayers();
   }

   public String getServerModName() {
      return "vanilla";
   }

   public abstract boolean isHardcore();

   protected void initialWorldChunkLoad() {
      boolean var1 = true;
      boolean var2 = true;
      boolean var3 = true;
      boolean var4 = true;
      int var5 = 0;
      this.setUserMessage("menu.generatingTerrain");
      byte var6 = 0;
      logger.info(String.valueOf((new StringBuilder("Preparing start region for level ")).append(var6)));
      WorldServer var7 = this.worldServers[var6];
      BlockPos var8 = var7.getSpawnPoint();
      long var9 = getCurrentTimeMillis();

      for(int var11 = -192; var11 <= 192 && this.isServerRunning(); var11 += 16) {
         for(int var12 = -192; var12 <= 192 && this.isServerRunning(); var12 += 16) {
            long var13 = getCurrentTimeMillis();
            if (var13 - var9 > 1000L) {
               this.outputPercentRemaining("Preparing spawn area", var5 * 100 / 625);
               var9 = var13;
            }

            ++var5;
            var7.theChunkProviderServer.loadChunk(var8.getX() + var11 >> 4, var8.getZ() + var12 >> 4);
         }
      }

      this.clearCurrentTask();
   }
}
