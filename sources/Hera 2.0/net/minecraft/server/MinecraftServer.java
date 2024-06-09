/*      */ package net.minecraft.server;
/*      */ import com.google.common.base.Charsets;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Queues;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import com.google.common.util.concurrent.ListenableFuture;
/*      */ import com.google.common.util.concurrent.ListenableFutureTask;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import com.mojang.authlib.GameProfileRepository;
/*      */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*      */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.ByteBufOutputStream;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import io.netty.handler.codec.base64.Base64;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.net.Proxy;
/*      */ import java.security.KeyPair;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.List;
/*      */ import java.util.Queue;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.Executors;
/*      */ import java.util.concurrent.FutureTask;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.minecraft.command.CommandBase;
/*      */ import net.minecraft.command.CommandResultStats;
/*      */ import net.minecraft.command.ICommandManager;
/*      */ import net.minecraft.command.ICommandSender;
/*      */ import net.minecraft.command.ServerCommandManager;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.network.NetworkSystem;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.ServerStatusResponse;
/*      */ import net.minecraft.network.play.server.S03PacketTimeUpdate;
/*      */ import net.minecraft.profiler.IPlayerUsage;
/*      */ import net.minecraft.profiler.PlayerUsageSnooper;
/*      */ import net.minecraft.profiler.Profiler;
/*      */ import net.minecraft.server.management.PlayerProfileCache;
/*      */ import net.minecraft.server.management.ServerConfigurationManager;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.IProgressUpdate;
/*      */ import net.minecraft.util.IThreadListener;
/*      */ import net.minecraft.util.ITickable;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.Util;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.minecraft.world.IWorldAccess;
/*      */ import net.minecraft.world.MinecraftException;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldManager;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import net.minecraft.world.WorldServerMulti;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.WorldType;
/*      */ import net.minecraft.world.chunk.storage.AnvilSaveConverter;
/*      */ import net.minecraft.world.demo.DemoWorldServer;
/*      */ import net.minecraft.world.storage.ISaveFormat;
/*      */ import net.minecraft.world.storage.ISaveHandler;
/*      */ import net.minecraft.world.storage.WorldInfo;
/*      */ import org.apache.commons.lang3.Validate;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public abstract class MinecraftServer implements Runnable, ICommandSender, IThreadListener, IPlayerUsage {
/*   82 */   private static final Logger logger = LogManager.getLogger();
/*   83 */   public static final File USER_CACHE_FILE = new File("usercache.json");
/*      */ 
/*      */   
/*      */   private static MinecraftServer mcServer;
/*      */   
/*      */   private final ISaveFormat anvilConverterForAnvilFile;
/*      */   
/*   90 */   private final PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("server", this, getCurrentTimeMillis());
/*      */   private final File anvilFile;
/*   92 */   private final List<ITickable> playersOnline = Lists.newArrayList();
/*      */   protected final ICommandManager commandManager;
/*   94 */   public final Profiler theProfiler = new Profiler();
/*      */   private final NetworkSystem networkSystem;
/*   96 */   private final ServerStatusResponse statusResponse = new ServerStatusResponse();
/*   97 */   private final Random random = new Random();
/*      */ 
/*      */   
/*  100 */   private int serverPort = -1;
/*      */ 
/*      */   
/*      */   public WorldServer[] worldServers;
/*      */ 
/*      */   
/*      */   private ServerConfigurationManager serverConfigManager;
/*      */ 
/*      */   
/*      */   private boolean serverRunning = true;
/*      */ 
/*      */   
/*      */   private boolean serverStopped;
/*      */ 
/*      */   
/*      */   private int tickCounter;
/*      */ 
/*      */   
/*      */   protected final Proxy serverProxy;
/*      */ 
/*      */   
/*      */   public String currentTask;
/*      */ 
/*      */   
/*      */   public int percentDone;
/*      */ 
/*      */   
/*      */   private boolean onlineMode;
/*      */ 
/*      */   
/*      */   private boolean canSpawnAnimals;
/*      */ 
/*      */   
/*      */   private boolean canSpawnNPCs;
/*      */ 
/*      */   
/*      */   private boolean pvpEnabled;
/*      */ 
/*      */   
/*      */   private boolean allowFlight;
/*      */ 
/*      */   
/*      */   private String motd;
/*      */   
/*      */   private int buildLimit;
/*      */   
/*  146 */   private int maxPlayerIdleMinutes = 0;
/*  147 */   public final long[] tickTimeArray = new long[100];
/*      */ 
/*      */   
/*      */   public long[][] timeOfLastDimensionTick;
/*      */   
/*      */   private KeyPair serverKeyPair;
/*      */   
/*      */   private String serverOwner;
/*      */   
/*      */   private String folderName;
/*      */   
/*      */   private String worldName;
/*      */   
/*      */   private boolean isDemo;
/*      */   
/*      */   private boolean enableBonusChest;
/*      */   
/*      */   private boolean worldIsBeingDeleted;
/*      */   
/*  166 */   private String resourcePackUrl = "";
/*  167 */   private String resourcePackHash = "";
/*      */   
/*      */   private boolean serverIsRunning;
/*      */   
/*      */   private long timeOfLastWarning;
/*      */   
/*      */   private String userMessage;
/*      */   
/*      */   private boolean startProfiling;
/*      */   private boolean isGamemodeForced;
/*      */   private final YggdrasilAuthenticationService authService;
/*      */   private final MinecraftSessionService sessionService;
/*  179 */   private long nanoTimeSinceStatusRefresh = 0L;
/*      */   private final GameProfileRepository profileRepo;
/*      */   private final PlayerProfileCache profileCache;
/*  182 */   protected final Queue<FutureTask<?>> futureTaskQueue = Queues.newArrayDeque();
/*      */   private Thread serverThread;
/*  184 */   private long currentTime = getCurrentTimeMillis();
/*      */ 
/*      */   
/*      */   public MinecraftServer(Proxy proxy, File workDir) {
/*  188 */     this.serverProxy = proxy;
/*  189 */     mcServer = this;
/*  190 */     this.anvilFile = null;
/*  191 */     this.networkSystem = null;
/*  192 */     this.profileCache = new PlayerProfileCache(this, workDir);
/*  193 */     this.commandManager = null;
/*  194 */     this.anvilConverterForAnvilFile = null;
/*  195 */     this.authService = new YggdrasilAuthenticationService(proxy, UUID.randomUUID().toString());
/*  196 */     this.sessionService = this.authService.createMinecraftSessionService();
/*  197 */     this.profileRepo = this.authService.createProfileRepository();
/*      */   }
/*      */ 
/*      */   
/*      */   public MinecraftServer(File workDir, Proxy proxy, File profileCacheDir) {
/*  202 */     this.serverProxy = proxy;
/*  203 */     mcServer = this;
/*  204 */     this.anvilFile = workDir;
/*  205 */     this.networkSystem = new NetworkSystem(this);
/*  206 */     this.profileCache = new PlayerProfileCache(this, profileCacheDir);
/*  207 */     this.commandManager = (ICommandManager)createNewCommandManager();
/*  208 */     this.anvilConverterForAnvilFile = (ISaveFormat)new AnvilSaveConverter(workDir);
/*  209 */     this.authService = new YggdrasilAuthenticationService(proxy, UUID.randomUUID().toString());
/*  210 */     this.sessionService = this.authService.createMinecraftSessionService();
/*  211 */     this.profileRepo = this.authService.createProfileRepository();
/*      */   }
/*      */ 
/*      */   
/*      */   protected ServerCommandManager createNewCommandManager() {
/*  216 */     return new ServerCommandManager();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void convertMapIfNeeded(String worldNameIn) {
/*  226 */     if (getActiveAnvilConverter().isOldMapFormat(worldNameIn)) {
/*      */       
/*  228 */       logger.info("Converting map!");
/*  229 */       setUserMessage("menu.convertingLevel");
/*  230 */       getActiveAnvilConverter().convertMapFormat(worldNameIn, new IProgressUpdate()
/*      */           {
/*  232 */             private long startTime = System.currentTimeMillis();
/*      */ 
/*      */             
/*      */             public void displaySavingString(String message) {}
/*      */ 
/*      */             
/*      */             public void resetProgressAndMessage(String message) {}
/*      */             
/*      */             public void setLoadingProgress(int progress) {
/*  241 */               if (System.currentTimeMillis() - this.startTime >= 1000L) {
/*      */                 
/*  243 */                 this.startTime = System.currentTimeMillis();
/*  244 */                 MinecraftServer.logger.info("Converting... " + progress + "%");
/*      */               } 
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public void setDoneWorking() {}
/*      */ 
/*      */ 
/*      */             
/*      */             public void displayLoadingString(String message) {}
/*      */           });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized void setUserMessage(String message) {
/*  262 */     this.userMessage = message;
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized String getUserMessage() {
/*  267 */     return this.userMessage;
/*      */   }
/*      */   
/*      */   protected void loadAllWorlds(String p_71247_1_, String p_71247_2_, long seed, WorldType type, String p_71247_6_) {
/*      */     WorldSettings worldsettings;
/*  272 */     convertMapIfNeeded(p_71247_1_);
/*  273 */     setUserMessage("menu.loadingLevel");
/*  274 */     this.worldServers = new WorldServer[3];
/*  275 */     this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
/*  276 */     ISaveHandler isavehandler = this.anvilConverterForAnvilFile.getSaveLoader(p_71247_1_, true);
/*  277 */     setResourcePackFromWorld(getFolderName(), isavehandler);
/*  278 */     WorldInfo worldinfo = isavehandler.loadWorldInfo();
/*      */ 
/*      */     
/*  281 */     if (worldinfo == null) {
/*      */       
/*  283 */       if (isDemo()) {
/*      */         
/*  285 */         worldsettings = DemoWorldServer.demoWorldSettings;
/*      */       }
/*      */       else {
/*      */         
/*  289 */         worldsettings = new WorldSettings(seed, getGameType(), canStructuresSpawn(), isHardcore(), type);
/*  290 */         worldsettings.setWorldName(p_71247_6_);
/*      */         
/*  292 */         if (this.enableBonusChest)
/*      */         {
/*  294 */           worldsettings.enableBonusChest();
/*      */         }
/*      */       } 
/*      */       
/*  298 */       worldinfo = new WorldInfo(worldsettings, p_71247_2_);
/*      */     }
/*      */     else {
/*      */       
/*  302 */       worldinfo.setWorldName(p_71247_2_);
/*  303 */       worldsettings = new WorldSettings(worldinfo);
/*      */     } 
/*      */     
/*  306 */     for (int i = 0; i < this.worldServers.length; i++) {
/*      */       
/*  308 */       int j = 0;
/*      */       
/*  310 */       if (i == 1)
/*      */       {
/*  312 */         j = -1;
/*      */       }
/*      */       
/*  315 */       if (i == 2)
/*      */       {
/*  317 */         j = 1;
/*      */       }
/*      */       
/*  320 */       if (i == 0) {
/*      */         
/*  322 */         if (isDemo()) {
/*      */           
/*  324 */           this.worldServers[i] = (WorldServer)(new DemoWorldServer(this, isavehandler, worldinfo, j, this.theProfiler)).init();
/*      */         }
/*      */         else {
/*      */           
/*  328 */           this.worldServers[i] = (WorldServer)(new WorldServer(this, isavehandler, worldinfo, j, this.theProfiler)).init();
/*      */         } 
/*      */         
/*  331 */         this.worldServers[i].initialize(worldsettings);
/*      */       }
/*      */       else {
/*      */         
/*  335 */         this.worldServers[i] = (WorldServer)(new WorldServerMulti(this, isavehandler, j, this.worldServers[0], this.theProfiler)).init();
/*      */       } 
/*      */       
/*  338 */       this.worldServers[i].addWorldAccess((IWorldAccess)new WorldManager(this, this.worldServers[i]));
/*      */       
/*  340 */       if (!isSinglePlayer())
/*      */       {
/*  342 */         this.worldServers[i].getWorldInfo().setGameType(getGameType());
/*      */       }
/*      */     } 
/*      */     
/*  346 */     this.serverConfigManager.setPlayerManager(this.worldServers);
/*  347 */     setDifficultyForAllWorlds(getDifficulty());
/*  348 */     initialWorldChunkLoad();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void initialWorldChunkLoad() {
/*  353 */     int i = 16;
/*  354 */     int j = 4;
/*  355 */     int k = 192;
/*  356 */     int l = 625;
/*  357 */     int i1 = 0;
/*  358 */     setUserMessage("menu.generatingTerrain");
/*  359 */     int j1 = 0;
/*  360 */     logger.info("Preparing start region for level " + j1);
/*  361 */     WorldServer worldserver = this.worldServers[j1];
/*  362 */     BlockPos blockpos = worldserver.getSpawnPoint();
/*  363 */     long k1 = getCurrentTimeMillis();
/*      */     
/*  365 */     for (int l1 = -192; l1 <= 192 && isServerRunning(); l1 += 16) {
/*      */       
/*  367 */       for (int i2 = -192; i2 <= 192 && isServerRunning(); i2 += 16) {
/*      */         
/*  369 */         long j2 = getCurrentTimeMillis();
/*      */         
/*  371 */         if (j2 - k1 > 1000L) {
/*      */           
/*  373 */           outputPercentRemaining("Preparing spawn area", i1 * 100 / 625);
/*  374 */           k1 = j2;
/*      */         } 
/*      */         
/*  377 */         i1++;
/*  378 */         worldserver.theChunkProviderServer.loadChunk(blockpos.getX() + l1 >> 4, blockpos.getZ() + i2 >> 4);
/*      */       } 
/*      */     } 
/*      */     
/*  382 */     clearCurrentTask();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setResourcePackFromWorld(String worldNameIn, ISaveHandler saveHandlerIn) {
/*  387 */     File file1 = new File(saveHandlerIn.getWorldDirectory(), "resources.zip");
/*      */     
/*  389 */     if (file1.isFile())
/*      */     {
/*  391 */       setResourcePack("level://" + worldNameIn + "/" + file1.getName(), "");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void outputPercentRemaining(String message, int percent) {
/*  420 */     this.currentTask = message;
/*  421 */     this.percentDone = percent;
/*  422 */     logger.info(String.valueOf(message) + ": " + percent + "%");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void clearCurrentTask() {
/*  430 */     this.currentTask = null;
/*  431 */     this.percentDone = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void saveAllWorlds(boolean dontLog) {
/*  439 */     if (!this.worldIsBeingDeleted) {
/*      */       byte b; int i; WorldServer[] arrayOfWorldServer;
/*  441 */       for (i = (arrayOfWorldServer = this.worldServers).length, b = 0; b < i; ) { WorldServer worldserver = arrayOfWorldServer[b];
/*      */         
/*  443 */         if (worldserver != null) {
/*      */           
/*  445 */           if (!dontLog)
/*      */           {
/*  447 */             logger.info("Saving chunks for level '" + worldserver.getWorldInfo().getWorldName() + "'/" + worldserver.provider.getDimensionName());
/*      */           }
/*      */ 
/*      */           
/*      */           try {
/*  452 */             worldserver.saveAllChunks(true, null);
/*      */           }
/*  454 */           catch (MinecraftException minecraftexception) {
/*      */             
/*  456 */             logger.warn(minecraftexception.getMessage());
/*      */           } 
/*      */         } 
/*      */         b++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void stopServer() {
/*  468 */     if (!this.worldIsBeingDeleted) {
/*      */       
/*  470 */       logger.info("Stopping server");
/*      */       
/*  472 */       if (getNetworkSystem() != null)
/*      */       {
/*  474 */         getNetworkSystem().terminateEndpoints();
/*      */       }
/*      */       
/*  477 */       if (this.serverConfigManager != null) {
/*      */         
/*  479 */         logger.info("Saving players");
/*  480 */         this.serverConfigManager.saveAllPlayerData();
/*  481 */         this.serverConfigManager.removeAllPlayers();
/*      */       } 
/*      */       
/*  484 */       if (this.worldServers != null) {
/*      */         
/*  486 */         logger.info("Saving worlds");
/*  487 */         saveAllWorlds(false);
/*      */         
/*  489 */         for (int i = 0; i < this.worldServers.length; i++) {
/*      */           
/*  491 */           WorldServer worldserver = this.worldServers[i];
/*  492 */           worldserver.flush();
/*      */         } 
/*      */       } 
/*      */       
/*  496 */       if (this.usageSnooper.isSnooperRunning())
/*      */       {
/*  498 */         this.usageSnooper.stopSnooper();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isServerRunning() {
/*  505 */     return this.serverRunning;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initiateShutdown() {
/*  513 */     this.serverRunning = false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setInstance() {
/*  518 */     mcServer = this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void run() {
/*      */     try {
/*  525 */       if (startServer()) {
/*      */         
/*  527 */         this.currentTime = getCurrentTimeMillis();
/*  528 */         long i = 0L;
/*  529 */         this.statusResponse.setServerDescription((IChatComponent)new ChatComponentText(this.motd));
/*  530 */         this.statusResponse.setProtocolVersionInfo(new ServerStatusResponse.MinecraftProtocolVersionIdentifier("1.8.8", 47));
/*  531 */         addFaviconToStatusResponse(this.statusResponse);
/*      */         
/*  533 */         while (this.serverRunning)
/*      */         {
/*  535 */           long k = getCurrentTimeMillis();
/*  536 */           long j = k - this.currentTime;
/*      */           
/*  538 */           if (j > 2000L && this.currentTime - this.timeOfLastWarning >= 15000L) {
/*      */             
/*  540 */             logger.warn("Can't keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)", new Object[] { Long.valueOf(j), Long.valueOf(j / 50L) });
/*  541 */             j = 2000L;
/*  542 */             this.timeOfLastWarning = this.currentTime;
/*      */           } 
/*      */           
/*  545 */           if (j < 0L) {
/*      */             
/*  547 */             logger.warn("Time ran backwards! Did the system time change?");
/*  548 */             j = 0L;
/*      */           } 
/*      */           
/*  551 */           i += j;
/*  552 */           this.currentTime = k;
/*      */           
/*  554 */           if (this.worldServers[0].areAllPlayersAsleep()) {
/*      */             
/*  556 */             tick();
/*  557 */             i = 0L;
/*      */           }
/*      */           else {
/*      */             
/*  561 */             while (i > 50L) {
/*      */               
/*  563 */               i -= 50L;
/*  564 */               tick();
/*      */             } 
/*      */           } 
/*      */           
/*  568 */           Thread.sleep(Math.max(1L, 50L - i));
/*  569 */           this.serverIsRunning = true;
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  574 */         finalTick(null);
/*      */       }
/*      */     
/*  577 */     } catch (Throwable throwable1) {
/*      */       
/*  579 */       logger.error("Encountered an unexpected exception", throwable1);
/*  580 */       CrashReport crashreport = null;
/*      */       
/*  582 */       if (throwable1 instanceof ReportedException) {
/*      */         
/*  584 */         crashreport = addServerInfoToCrashReport(((ReportedException)throwable1).getCrashReport());
/*      */       }
/*      */       else {
/*      */         
/*  588 */         crashreport = addServerInfoToCrashReport(new CrashReport("Exception in server tick loop", throwable1));
/*      */       } 
/*      */       
/*  591 */       File file1 = new File(new File(getDataDirectory(), "crash-reports"), "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-server.txt");
/*      */       
/*  593 */       if (crashreport.saveToFile(file1)) {
/*      */         
/*  595 */         logger.error("This crash report has been saved to: " + file1.getAbsolutePath());
/*      */       }
/*      */       else {
/*      */         
/*  599 */         logger.error("We were unable to save this crash report to disk.");
/*      */       } 
/*      */       
/*  602 */       finalTick(crashreport);
/*      */     } finally {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/*  608 */         this.serverStopped = true;
/*  609 */         stopServer();
/*      */       }
/*  611 */       catch (Throwable throwable) {
/*      */         
/*  613 */         logger.error("Exception stopping the server", throwable);
/*      */       }
/*      */       finally {
/*      */         
/*  617 */         systemExitNow();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void addFaviconToStatusResponse(ServerStatusResponse response) {
/*  624 */     File file1 = getFile("server-icon.png");
/*      */     
/*  626 */     if (file1.isFile()) {
/*      */       
/*  628 */       ByteBuf bytebuf = Unpooled.buffer();
/*      */ 
/*      */       
/*      */       try {
/*  632 */         BufferedImage bufferedimage = ImageIO.read(file1);
/*  633 */         Validate.validState((bufferedimage.getWidth() == 64), "Must be 64 pixels wide", new Object[0]);
/*  634 */         Validate.validState((bufferedimage.getHeight() == 64), "Must be 64 pixels high", new Object[0]);
/*  635 */         ImageIO.write(bufferedimage, "PNG", (OutputStream)new ByteBufOutputStream(bytebuf));
/*  636 */         ByteBuf bytebuf1 = Base64.encode(bytebuf);
/*  637 */         response.setFavicon("data:image/png;base64," + bytebuf1.toString(Charsets.UTF_8));
/*      */       }
/*  639 */       catch (Exception exception) {
/*      */         
/*  641 */         logger.error("Couldn't load server icon", exception);
/*      */       }
/*      */       finally {
/*      */         
/*  645 */         bytebuf.release();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public File getDataDirectory() {
/*  652 */     return new File(".");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void finalTick(CrashReport report) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void systemExitNow() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void tick() {
/*  674 */     long i = System.nanoTime();
/*  675 */     this.tickCounter++;
/*      */     
/*  677 */     if (this.startProfiling) {
/*      */       
/*  679 */       this.startProfiling = false;
/*  680 */       this.theProfiler.profilingEnabled = true;
/*  681 */       this.theProfiler.clearProfiling();
/*      */     } 
/*      */     
/*  684 */     this.theProfiler.startSection("root");
/*  685 */     updateTimeLightAndEntities();
/*      */     
/*  687 */     if (i - this.nanoTimeSinceStatusRefresh >= 5000000000L) {
/*      */       
/*  689 */       this.nanoTimeSinceStatusRefresh = i;
/*  690 */       this.statusResponse.setPlayerCountData(new ServerStatusResponse.PlayerCountData(getMaxPlayers(), getCurrentPlayerCount()));
/*  691 */       GameProfile[] agameprofile = new GameProfile[Math.min(getCurrentPlayerCount(), 12)];
/*  692 */       int j = MathHelper.getRandomIntegerInRange(this.random, 0, getCurrentPlayerCount() - agameprofile.length);
/*      */       
/*  694 */       for (int k = 0; k < agameprofile.length; k++)
/*      */       {
/*  696 */         agameprofile[k] = ((EntityPlayerMP)this.serverConfigManager.func_181057_v().get(j + k)).getGameProfile();
/*      */       }
/*      */       
/*  699 */       Collections.shuffle(Arrays.asList((Object[])agameprofile));
/*  700 */       this.statusResponse.getPlayerCountData().setPlayers(agameprofile);
/*      */     } 
/*      */     
/*  703 */     if (this.tickCounter % 900 == 0) {
/*      */       
/*  705 */       this.theProfiler.startSection("save");
/*  706 */       this.serverConfigManager.saveAllPlayerData();
/*  707 */       saveAllWorlds(true);
/*  708 */       this.theProfiler.endSection();
/*      */     } 
/*      */     
/*  711 */     this.theProfiler.startSection("tallying");
/*  712 */     this.tickTimeArray[this.tickCounter % 100] = System.nanoTime() - i;
/*  713 */     this.theProfiler.endSection();
/*  714 */     this.theProfiler.startSection("snooper");
/*      */     
/*  716 */     if (!this.usageSnooper.isSnooperRunning() && this.tickCounter > 100)
/*      */     {
/*  718 */       this.usageSnooper.startSnooper();
/*      */     }
/*      */     
/*  721 */     if (this.tickCounter % 6000 == 0)
/*      */     {
/*  723 */       this.usageSnooper.addMemoryStatsToSnooper();
/*      */     }
/*      */     
/*  726 */     this.theProfiler.endSection();
/*  727 */     this.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateTimeLightAndEntities() {
/*  732 */     this.theProfiler.startSection("jobs");
/*      */     
/*  734 */     synchronized (this.futureTaskQueue) {
/*      */       
/*  736 */       while (!this.futureTaskQueue.isEmpty())
/*      */       {
/*  738 */         Util.func_181617_a(this.futureTaskQueue.poll(), logger);
/*      */       }
/*      */     } 
/*      */     
/*  742 */     this.theProfiler.endStartSection("levels");
/*      */     
/*  744 */     for (int j = 0; j < this.worldServers.length; j++) {
/*      */       
/*  746 */       long i = System.nanoTime();
/*      */       
/*  748 */       if (j == 0 || getAllowNether()) {
/*      */         
/*  750 */         WorldServer worldserver = this.worldServers[j];
/*  751 */         this.theProfiler.startSection(worldserver.getWorldInfo().getWorldName());
/*      */         
/*  753 */         if (this.tickCounter % 20 == 0) {
/*      */           
/*  755 */           this.theProfiler.startSection("timeSync");
/*  756 */           this.serverConfigManager.sendPacketToAllPlayersInDimension((Packet)new S03PacketTimeUpdate(worldserver.getTotalWorldTime(), worldserver.getWorldTime(), worldserver.getGameRules().getBoolean("doDaylightCycle")), worldserver.provider.getDimensionId());
/*  757 */           this.theProfiler.endSection();
/*      */         } 
/*      */         
/*  760 */         this.theProfiler.startSection("tick");
/*      */ 
/*      */         
/*      */         try {
/*  764 */           worldserver.tick();
/*      */         }
/*  766 */         catch (Throwable throwable1) {
/*      */           
/*  768 */           CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Exception ticking world");
/*  769 */           worldserver.addWorldInfoToCrashReport(crashreport);
/*  770 */           throw new ReportedException(crashreport);
/*      */         } 
/*      */ 
/*      */         
/*      */         try {
/*  775 */           worldserver.updateEntities();
/*      */         }
/*  777 */         catch (Throwable throwable) {
/*      */           
/*  779 */           CrashReport crashreport1 = CrashReport.makeCrashReport(throwable, "Exception ticking world entities");
/*  780 */           worldserver.addWorldInfoToCrashReport(crashreport1);
/*  781 */           throw new ReportedException(crashreport1);
/*      */         } 
/*      */         
/*  784 */         this.theProfiler.endSection();
/*  785 */         this.theProfiler.startSection("tracker");
/*  786 */         worldserver.getEntityTracker().updateTrackedEntities();
/*  787 */         this.theProfiler.endSection();
/*  788 */         this.theProfiler.endSection();
/*      */       } 
/*      */       
/*  791 */       this.timeOfLastDimensionTick[j][this.tickCounter % 100] = System.nanoTime() - i;
/*      */     } 
/*      */     
/*  794 */     this.theProfiler.endStartSection("connection");
/*  795 */     getNetworkSystem().networkTick();
/*  796 */     this.theProfiler.endStartSection("players");
/*  797 */     this.serverConfigManager.onTick();
/*  798 */     this.theProfiler.endStartSection("tickables");
/*      */     
/*  800 */     for (int k = 0; k < this.playersOnline.size(); k++)
/*      */     {
/*  802 */       ((ITickable)this.playersOnline.get(k)).update();
/*      */     }
/*      */     
/*  805 */     this.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getAllowNether() {
/*  810 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void startServerThread() {
/*  815 */     this.serverThread = new Thread(this, "Server thread");
/*  816 */     this.serverThread.start();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File getFile(String fileName) {
/*  824 */     return new File(getDataDirectory(), fileName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void logWarning(String msg) {
/*  832 */     logger.warn(msg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldServer worldServerForDimension(int dimension) {
/*  840 */     return (dimension == -1) ? this.worldServers[1] : ((dimension == 1) ? this.worldServers[2] : this.worldServers[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMinecraftVersion() {
/*  848 */     return "1.8.8";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCurrentPlayerCount() {
/*  856 */     return this.serverConfigManager.getCurrentPlayerCount();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxPlayers() {
/*  864 */     return this.serverConfigManager.getMaxPlayers();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getAllUsernames() {
/*  872 */     return this.serverConfigManager.getAllUsernames();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GameProfile[] getGameProfiles() {
/*  880 */     return this.serverConfigManager.getAllProfiles();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getServerModName() {
/*  885 */     return "vanilla";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CrashReport addServerInfoToCrashReport(CrashReport report) {
/*  893 */     report.getCategory().addCrashSectionCallable("Profiler Position", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/*  897 */             return MinecraftServer.this.theProfiler.profilingEnabled ? MinecraftServer.this.theProfiler.getNameOfLastSection() : "N/A (disabled)";
/*      */           }
/*      */         });
/*      */     
/*  901 */     if (this.serverConfigManager != null)
/*      */     {
/*  903 */       report.getCategory().addCrashSectionCallable("Player Count", new Callable<String>()
/*      */           {
/*      */             public String call()
/*      */             {
/*  907 */               return String.valueOf(MinecraftServer.this.serverConfigManager.getCurrentPlayerCount()) + " / " + MinecraftServer.this.serverConfigManager.getMaxPlayers() + "; " + MinecraftServer.this.serverConfigManager.func_181057_v();
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*  912 */     return report;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<String> getTabCompletions(ICommandSender sender, String input, BlockPos pos) {
/*  917 */     List<String> list = Lists.newArrayList();
/*      */     
/*  919 */     if (input.startsWith("/")) {
/*      */       
/*  921 */       input = input.substring(1);
/*  922 */       boolean flag = !input.contains(" ");
/*  923 */       List<String> list1 = this.commandManager.getTabCompletionOptions(sender, input, pos);
/*      */       
/*  925 */       if (list1 != null)
/*      */       {
/*  927 */         for (String s2 : list1) {
/*      */           
/*  929 */           if (flag) {
/*      */             
/*  931 */             list.add("/" + s2);
/*      */             
/*      */             continue;
/*      */           } 
/*  935 */           list.add(s2);
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  940 */       return list;
/*      */     } 
/*      */ 
/*      */     
/*  944 */     String[] astring = input.split(" ", -1);
/*  945 */     String s = astring[astring.length - 1]; byte b; int i;
/*      */     String[] arrayOfString1;
/*  947 */     for (i = (arrayOfString1 = this.serverConfigManager.getAllUsernames()).length, b = 0; b < i; ) { String s1 = arrayOfString1[b];
/*      */       
/*  949 */       if (CommandBase.doesStringStartWith(s, s1))
/*      */       {
/*  951 */         list.add(s1);
/*      */       }
/*      */       b++; }
/*      */     
/*  955 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MinecraftServer getServer() {
/*  964 */     return mcServer;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAnvilFileSet() {
/*  969 */     return (this.anvilFile != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  977 */     return "Server";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChatMessage(IChatComponent component) {
/*  985 */     logger.info(component.getUnformattedText());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/*  993 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public ICommandManager getCommandManager() {
/*  998 */     return this.commandManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyPair getKeyPair() {
/* 1006 */     return this.serverKeyPair;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getServerOwner() {
/* 1014 */     return this.serverOwner;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setServerOwner(String owner) {
/* 1022 */     this.serverOwner = owner;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSinglePlayer() {
/* 1027 */     return (this.serverOwner != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getFolderName() {
/* 1032 */     return this.folderName;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setFolderName(String name) {
/* 1037 */     this.folderName = name;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setWorldName(String p_71246_1_) {
/* 1042 */     this.worldName = p_71246_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getWorldName() {
/* 1047 */     return this.worldName;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setKeyPair(KeyPair keyPair) {
/* 1052 */     this.serverKeyPair = keyPair;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDifficultyForAllWorlds(EnumDifficulty difficulty) {
/* 1057 */     for (int i = 0; i < this.worldServers.length; i++) {
/*      */       
/* 1059 */       WorldServer worldServer = this.worldServers[i];
/*      */       
/* 1061 */       if (worldServer != null)
/*      */       {
/* 1063 */         if (worldServer.getWorldInfo().isHardcoreModeEnabled()) {
/*      */           
/* 1065 */           worldServer.getWorldInfo().setDifficulty(EnumDifficulty.HARD);
/* 1066 */           worldServer.setAllowedSpawnTypes(true, true);
/*      */         }
/* 1068 */         else if (isSinglePlayer()) {
/*      */           
/* 1070 */           worldServer.getWorldInfo().setDifficulty(difficulty);
/* 1071 */           worldServer.setAllowedSpawnTypes((worldServer.getDifficulty() != EnumDifficulty.PEACEFUL), true);
/*      */         }
/*      */         else {
/*      */           
/* 1075 */           worldServer.getWorldInfo().setDifficulty(difficulty);
/* 1076 */           worldServer.setAllowedSpawnTypes(allowSpawnMonsters(), this.canSpawnAnimals);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean allowSpawnMonsters() {
/* 1084 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDemo() {
/* 1092 */     return this.isDemo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDemo(boolean demo) {
/* 1100 */     this.isDemo = demo;
/*      */   }
/*      */ 
/*      */   
/*      */   public void canCreateBonusChest(boolean enable) {
/* 1105 */     this.enableBonusChest = enable;
/*      */   }
/*      */ 
/*      */   
/*      */   public ISaveFormat getActiveAnvilConverter() {
/* 1110 */     return this.anvilConverterForAnvilFile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteWorldAndStopServer() {
/* 1119 */     this.worldIsBeingDeleted = true;
/* 1120 */     getActiveAnvilConverter().flushCache();
/*      */     
/* 1122 */     for (int i = 0; i < this.worldServers.length; i++) {
/*      */       
/* 1124 */       WorldServer worldserver = this.worldServers[i];
/*      */       
/* 1126 */       if (worldserver != null)
/*      */       {
/* 1128 */         worldserver.flush();
/*      */       }
/*      */     } 
/*      */     
/* 1132 */     getActiveAnvilConverter().deleteWorldDirectory(this.worldServers[0].getSaveHandler().getWorldDirectoryName());
/* 1133 */     initiateShutdown();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getResourcePackUrl() {
/* 1138 */     return this.resourcePackUrl;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getResourcePackHash() {
/* 1143 */     return this.resourcePackHash;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setResourcePack(String url, String hash) {
/* 1148 */     this.resourcePackUrl = url;
/* 1149 */     this.resourcePackHash = hash;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addServerStatsToSnooper(PlayerUsageSnooper playerSnooper) {
/* 1154 */     playerSnooper.addClientStat("whitelist_enabled", Boolean.valueOf(false));
/* 1155 */     playerSnooper.addClientStat("whitelist_count", Integer.valueOf(0));
/*      */     
/* 1157 */     if (this.serverConfigManager != null) {
/*      */       
/* 1159 */       playerSnooper.addClientStat("players_current", Integer.valueOf(getCurrentPlayerCount()));
/* 1160 */       playerSnooper.addClientStat("players_max", Integer.valueOf(getMaxPlayers()));
/* 1161 */       playerSnooper.addClientStat("players_seen", Integer.valueOf((this.serverConfigManager.getAvailablePlayerDat()).length));
/*      */     } 
/*      */     
/* 1164 */     playerSnooper.addClientStat("uses_auth", Boolean.valueOf(this.onlineMode));
/* 1165 */     playerSnooper.addClientStat("gui_state", getGuiEnabled() ? "enabled" : "disabled");
/* 1166 */     playerSnooper.addClientStat("run_time", Long.valueOf((getCurrentTimeMillis() - playerSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L));
/* 1167 */     playerSnooper.addClientStat("avg_tick_ms", Integer.valueOf((int)(MathHelper.average(this.tickTimeArray) * 1.0E-6D)));
/* 1168 */     int i = 0;
/*      */     
/* 1170 */     if (this.worldServers != null)
/*      */     {
/* 1172 */       for (int j = 0; j < this.worldServers.length; j++) {
/*      */         
/* 1174 */         if (this.worldServers[j] != null) {
/*      */           
/* 1176 */           WorldServer worldserver = this.worldServers[j];
/* 1177 */           WorldInfo worldinfo = worldserver.getWorldInfo();
/* 1178 */           playerSnooper.addClientStat("world[" + i + "][dimension]", Integer.valueOf(worldserver.provider.getDimensionId()));
/* 1179 */           playerSnooper.addClientStat("world[" + i + "][mode]", worldinfo.getGameType());
/* 1180 */           playerSnooper.addClientStat("world[" + i + "][difficulty]", worldserver.getDifficulty());
/* 1181 */           playerSnooper.addClientStat("world[" + i + "][hardcore]", Boolean.valueOf(worldinfo.isHardcoreModeEnabled()));
/* 1182 */           playerSnooper.addClientStat("world[" + i + "][generator_name]", worldinfo.getTerrainType().getWorldTypeName());
/* 1183 */           playerSnooper.addClientStat("world[" + i + "][generator_version]", Integer.valueOf(worldinfo.getTerrainType().getGeneratorVersion()));
/* 1184 */           playerSnooper.addClientStat("world[" + i + "][height]", Integer.valueOf(this.buildLimit));
/* 1185 */           playerSnooper.addClientStat("world[" + i + "][chunks_loaded]", Integer.valueOf(worldserver.getChunkProvider().getLoadedChunkCount()));
/* 1186 */           i++;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1191 */     playerSnooper.addClientStat("worlds", Integer.valueOf(i));
/*      */   }
/*      */ 
/*      */   
/*      */   public void addServerTypeToSnooper(PlayerUsageSnooper playerSnooper) {
/* 1196 */     playerSnooper.addStatToSnooper("singleplayer", Boolean.valueOf(isSinglePlayer()));
/* 1197 */     playerSnooper.addStatToSnooper("server_brand", getServerModName());
/* 1198 */     playerSnooper.addStatToSnooper("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
/* 1199 */     playerSnooper.addStatToSnooper("dedicated", Boolean.valueOf(isDedicatedServer()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSnooperEnabled() {
/* 1207 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isServerInOnlineMode() {
/* 1214 */     return this.onlineMode;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setOnlineMode(boolean online) {
/* 1219 */     this.onlineMode = online;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getCanSpawnAnimals() {
/* 1224 */     return this.canSpawnAnimals;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCanSpawnAnimals(boolean spawnAnimals) {
/* 1229 */     this.canSpawnAnimals = spawnAnimals;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getCanSpawnNPCs() {
/* 1234 */     return this.canSpawnNPCs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCanSpawnNPCs(boolean spawnNpcs) {
/* 1241 */     this.canSpawnNPCs = spawnNpcs;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPVPEnabled() {
/* 1246 */     return this.pvpEnabled;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAllowPvp(boolean allowPvp) {
/* 1251 */     this.pvpEnabled = allowPvp;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFlightAllowed() {
/* 1256 */     return this.allowFlight;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAllowFlight(boolean allow) {
/* 1261 */     this.allowFlight = allow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMOTD() {
/* 1271 */     return this.motd;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMOTD(String motdIn) {
/* 1276 */     this.motd = motdIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBuildLimit() {
/* 1281 */     return this.buildLimit;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBuildLimit(int maxBuildHeight) {
/* 1286 */     this.buildLimit = maxBuildHeight;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isServerStopped() {
/* 1291 */     return this.serverStopped;
/*      */   }
/*      */ 
/*      */   
/*      */   public ServerConfigurationManager getConfigurationManager() {
/* 1296 */     return this.serverConfigManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setConfigManager(ServerConfigurationManager configManager) {
/* 1301 */     this.serverConfigManager = configManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGameType(WorldSettings.GameType gameMode) {
/* 1309 */     for (int i = 0; i < this.worldServers.length; i++)
/*      */     {
/* 1311 */       (getServer()).worldServers[i].getWorldInfo().setGameType(gameMode);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public NetworkSystem getNetworkSystem() {
/* 1317 */     return this.networkSystem;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean serverIsInRunLoop() {
/* 1322 */     return this.serverIsRunning;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getGuiEnabled() {
/* 1327 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTickCounter() {
/* 1337 */     return this.tickCounter;
/*      */   }
/*      */ 
/*      */   
/*      */   public void enableProfiling() {
/* 1342 */     this.startProfiling = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public PlayerUsageSnooper getPlayerUsageSnooper() {
/* 1347 */     return this.usageSnooper;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getPosition() {
/* 1356 */     return BlockPos.ORIGIN;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getPositionVector() {
/* 1365 */     return new Vec3(0.0D, 0.0D, 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public World getEntityWorld() {
/* 1374 */     return (World)this.worldServers[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity getCommandSenderEntity() {
/* 1382 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSpawnProtectionSize() {
/* 1390 */     return 16;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlockProtected(World worldIn, BlockPos pos, EntityPlayer playerIn) {
/* 1395 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getForceGamemode() {
/* 1400 */     return this.isGamemodeForced;
/*      */   }
/*      */ 
/*      */   
/*      */   public Proxy getServerProxy() {
/* 1405 */     return this.serverProxy;
/*      */   }
/*      */ 
/*      */   
/*      */   public static long getCurrentTimeMillis() {
/* 1410 */     return System.currentTimeMillis();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxPlayerIdleMinutes() {
/* 1415 */     return this.maxPlayerIdleMinutes;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPlayerIdleTimeout(int idleTimeout) {
/* 1420 */     this.maxPlayerIdleMinutes = idleTimeout;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChatComponent getDisplayName() {
/* 1428 */     return (IChatComponent)new ChatComponentText(getName());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAnnouncingPlayerAchievements() {
/* 1433 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public MinecraftSessionService getMinecraftSessionService() {
/* 1438 */     return this.sessionService;
/*      */   }
/*      */ 
/*      */   
/*      */   public GameProfileRepository getGameProfileRepository() {
/* 1443 */     return this.profileRepo;
/*      */   }
/*      */ 
/*      */   
/*      */   public PlayerProfileCache getPlayerProfileCache() {
/* 1448 */     return this.profileCache;
/*      */   }
/*      */ 
/*      */   
/*      */   public ServerStatusResponse getServerStatusResponse() {
/* 1453 */     return this.statusResponse;
/*      */   }
/*      */ 
/*      */   
/*      */   public void refreshStatusNextTick() {
/* 1458 */     this.nanoTimeSinceStatusRefresh = 0L;
/*      */   } public Entity getEntityFromUuid(UUID uuid) {
/*      */     byte b;
/*      */     int i;
/*      */     WorldServer[] arrayOfWorldServer;
/* 1463 */     for (i = (arrayOfWorldServer = this.worldServers).length, b = 0; b < i; ) { WorldServer worldserver = arrayOfWorldServer[b];
/*      */       
/* 1465 */       if (worldserver != null) {
/*      */         
/* 1467 */         Entity entity = worldserver.getEntityFromUuid(uuid);
/*      */         
/* 1469 */         if (entity != null)
/*      */         {
/* 1471 */           return entity;
/*      */         }
/*      */       } 
/*      */       b++; }
/*      */     
/* 1476 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean sendCommandFeedback() {
/* 1484 */     return (getServer()).worldServers[0].getGameRules().getBoolean("sendCommandFeedback");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCommandStat(CommandResultStats.Type type, int amount) {}
/*      */ 
/*      */   
/*      */   public int getMaxWorldSize() {
/* 1493 */     return 29999984;
/*      */   }
/*      */ 
/*      */   
/*      */   public <V> ListenableFuture<V> callFromMainThread(Callable<V> callable) {
/* 1498 */     Validate.notNull(callable);
/*      */     
/* 1500 */     if (!isCallingFromMinecraftThread() && !isServerStopped()) {
/*      */       
/* 1502 */       ListenableFutureTask<V> listenablefuturetask = ListenableFutureTask.create(callable);
/*      */       
/* 1504 */       synchronized (this.futureTaskQueue) {
/*      */         
/* 1506 */         this.futureTaskQueue.add(listenablefuturetask);
/* 1507 */         return (ListenableFuture<V>)listenablefuturetask;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1514 */       return Futures.immediateFuture(callable.call());
/*      */     }
/* 1516 */     catch (Exception exception) {
/*      */       
/* 1518 */       return (ListenableFuture<V>)Futures.immediateFailedCheckedFuture(exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ListenableFuture<Object> addScheduledTask(Runnable runnableToSchedule) {
/* 1525 */     Validate.notNull(runnableToSchedule);
/* 1526 */     return callFromMainThread(Executors.callable(runnableToSchedule));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCallingFromMinecraftThread() {
/* 1531 */     return (Thread.currentThread() == this.serverThread);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNetworkCompressionTreshold() {
/* 1539 */     return 256;
/*      */   }
/*      */   
/*      */   protected abstract boolean startServer() throws IOException;
/*      */   
/*      */   public abstract boolean canStructuresSpawn();
/*      */   
/*      */   public abstract WorldSettings.GameType getGameType();
/*      */   
/*      */   public abstract EnumDifficulty getDifficulty();
/*      */   
/*      */   public abstract boolean isHardcore();
/*      */   
/*      */   public abstract int getOpPermissionLevel();
/*      */   
/*      */   public abstract boolean func_181034_q();
/*      */   
/*      */   public abstract boolean func_183002_r();
/*      */   
/*      */   public abstract boolean isDedicatedServer();
/*      */   
/*      */   public abstract boolean func_181035_ah();
/*      */   
/*      */   public abstract boolean isCommandBlockEnabled();
/*      */   
/*      */   public abstract String shareToLAN(WorldSettings.GameType paramGameType, boolean paramBoolean);
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\server\MinecraftServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */