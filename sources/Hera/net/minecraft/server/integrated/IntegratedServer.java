/*     */ package net.minecraft.server.integrated;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
/*     */ import net.minecraft.client.ClientBrandRetriever;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ThreadLanServerPing;
/*     */ import net.minecraft.command.ServerCommandManager;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.profiler.PlayerUsageSnooper;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.CryptManager;
/*     */ import net.minecraft.util.HttpUtil;
/*     */ import net.minecraft.util.Util;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.IWorldAccess;
/*     */ import net.minecraft.world.WorldManager;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.WorldServerMulti;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.demo.DemoWorldServer;
/*     */ import net.minecraft.world.storage.ISaveHandler;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import optfine.Reflector;
/*     */ import optfine.WorldServerOF;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class IntegratedServer
/*     */   extends MinecraftServer
/*     */ {
/*  39 */   private static final Logger logger = LogManager.getLogger();
/*     */   
/*     */   private final Minecraft mc;
/*     */   
/*     */   private final WorldSettings theWorldSettings;
/*     */   
/*     */   private boolean isGamePaused;
/*     */   private boolean isPublic;
/*     */   private ThreadLanServerPing lanServerPing;
/*     */   private static final String __OBFID = "CL_00001129";
/*     */   
/*     */   public IntegratedServer(Minecraft mcIn) {
/*  51 */     super(mcIn.getProxy(), new File(mcIn.mcDataDir, USER_CACHE_FILE.getName()));
/*  52 */     this.mc = mcIn;
/*  53 */     this.theWorldSettings = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public IntegratedServer(Minecraft mcIn, String folderName, String worldName, WorldSettings settings) {
/*  58 */     super(new File(mcIn.mcDataDir, "saves"), mcIn.getProxy(), new File(mcIn.mcDataDir, USER_CACHE_FILE.getName()));
/*  59 */     setServerOwner(mcIn.getSession().getUsername());
/*  60 */     setFolderName(folderName);
/*  61 */     setWorldName(worldName);
/*  62 */     setDemo(mcIn.isDemo());
/*  63 */     canCreateBonusChest(settings.isBonusChestEnabled());
/*  64 */     setBuildLimit(256);
/*  65 */     setConfigManager(new IntegratedPlayerList(this));
/*  66 */     this.mc = mcIn;
/*  67 */     this.theWorldSettings = isDemo() ? DemoWorldServer.demoWorldSettings : settings;
/*  68 */     Reflector.callVoid(Reflector.ModLoader_registerServer, new Object[] { this });
/*     */   }
/*     */ 
/*     */   
/*     */   protected ServerCommandManager createNewCommandManager() {
/*  73 */     return new IntegratedServerCommandManager();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadAllWorlds(String p_71247_1_, String p_71247_2_, long seed, WorldType type, String p_71247_6_) {
/*  78 */     convertMapIfNeeded(p_71247_1_);
/*  79 */     ISaveHandler isavehandler = getActiveAnvilConverter().getSaveLoader(p_71247_1_, true);
/*  80 */     WorldInfo worldinfo = isavehandler.loadWorldInfo();
/*     */     
/*  82 */     if (Reflector.DimensionManager.exists()) {
/*     */       
/*  84 */       WorldServer worldserver = isDemo() ? (WorldServer)(new DemoWorldServer(this, isavehandler, worldinfo, 0, this.theProfiler)).init() : (WorldServer)(new WorldServerOF(this, isavehandler, worldinfo, 0, this.theProfiler)).init();
/*  85 */       worldserver.initialize(this.theWorldSettings);
/*  86 */       Integer[] ainteger = (Integer[])Reflector.call(Reflector.DimensionManager_getStaticDimensionIDs, new Object[0]);
/*  87 */       Integer[] ainteger1 = ainteger;
/*  88 */       int i = ainteger.length;
/*     */       
/*  90 */       for (int j = 0; j < i; j++) {
/*     */         
/*  92 */         int k = ainteger1[j].intValue();
/*  93 */         WorldServer worldserver1 = (k == 0) ? worldserver : (WorldServer)(new WorldServerMulti(this, isavehandler, k, worldserver, this.theProfiler)).init();
/*  94 */         worldserver1.addWorldAccess((IWorldAccess)new WorldManager(this, worldserver1));
/*     */         
/*  96 */         if (!isSinglePlayer())
/*     */         {
/*  98 */           worldserver1.getWorldInfo().setGameType(getGameType());
/*     */         }
/*     */         
/* 101 */         if (Reflector.EventBus.exists())
/*     */         {
/* 103 */           Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, new Object[] { worldserver1 });
/*     */         }
/*     */       } 
/*     */       
/* 107 */       getConfigurationManager().setPlayerManager(new WorldServer[] { worldserver });
/*     */       
/* 109 */       if (worldserver.getWorldInfo().getDifficulty() == null)
/*     */       {
/* 111 */         setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 116 */       this.worldServers = new WorldServer[3];
/* 117 */       this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
/* 118 */       setResourcePackFromWorld(getFolderName(), isavehandler);
/*     */       
/* 120 */       if (worldinfo == null) {
/*     */         
/* 122 */         worldinfo = new WorldInfo(this.theWorldSettings, p_71247_2_);
/*     */       }
/*     */       else {
/*     */         
/* 126 */         worldinfo.setWorldName(p_71247_2_);
/*     */       } 
/*     */       
/* 129 */       for (int l = 0; l < this.worldServers.length; l++) {
/*     */         
/* 131 */         byte b0 = 0;
/*     */         
/* 133 */         if (l == 1)
/*     */         {
/* 135 */           b0 = -1;
/*     */         }
/*     */         
/* 138 */         if (l == 2)
/*     */         {
/* 140 */           b0 = 1;
/*     */         }
/*     */         
/* 143 */         if (l == 0) {
/*     */           
/* 145 */           if (isDemo()) {
/*     */             
/* 147 */             this.worldServers[l] = (WorldServer)(new DemoWorldServer(this, isavehandler, worldinfo, b0, this.theProfiler)).init();
/*     */           }
/*     */           else {
/*     */             
/* 151 */             this.worldServers[l] = (WorldServer)(new WorldServerOF(this, isavehandler, worldinfo, b0, this.theProfiler)).init();
/*     */           } 
/*     */           
/* 154 */           this.worldServers[l].initialize(this.theWorldSettings);
/*     */         }
/*     */         else {
/*     */           
/* 158 */           this.worldServers[l] = (WorldServer)(new WorldServerMulti(this, isavehandler, b0, this.worldServers[0], this.theProfiler)).init();
/*     */         } 
/*     */         
/* 161 */         this.worldServers[l].addWorldAccess((IWorldAccess)new WorldManager(this, this.worldServers[l]));
/*     */       } 
/*     */       
/* 164 */       getConfigurationManager().setPlayerManager(this.worldServers);
/*     */       
/* 166 */       if (this.worldServers[0].getWorldInfo().getDifficulty() == null)
/*     */       {
/* 168 */         setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
/*     */       }
/*     */     } 
/*     */     
/* 172 */     initialWorldChunkLoad();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean startServer() throws IOException {
/* 180 */     logger.info("Starting integrated minecraft server version 1.8.8");
/* 181 */     setOnlineMode(true);
/* 182 */     setCanSpawnAnimals(true);
/* 183 */     setCanSpawnNPCs(true);
/* 184 */     setAllowPvp(true);
/* 185 */     setAllowFlight(true);
/* 186 */     logger.info("Generating keypair");
/* 187 */     setKeyPair(CryptManager.generateKeyPair());
/*     */     
/* 189 */     if (Reflector.FMLCommonHandler_handleServerAboutToStart.exists()) {
/*     */       
/* 191 */       Object object = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/*     */       
/* 193 */       if (!Reflector.callBoolean(object, Reflector.FMLCommonHandler_handleServerAboutToStart, new Object[] { this }))
/*     */       {
/* 195 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 199 */     loadAllWorlds(getFolderName(), getWorldName(), this.theWorldSettings.getSeed(), this.theWorldSettings.getTerrainType(), this.theWorldSettings.getWorldName());
/* 200 */     setMOTD(String.valueOf(getServerOwner()) + " - " + this.worldServers[0].getWorldInfo().getWorldName());
/*     */     
/* 202 */     if (Reflector.FMLCommonHandler_handleServerStarting.exists()) {
/*     */       
/* 204 */       Object object1 = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/*     */       
/* 206 */       if (Reflector.FMLCommonHandler_handleServerStarting.getReturnType() == boolean.class)
/*     */       {
/* 208 */         return Reflector.callBoolean(object1, Reflector.FMLCommonHandler_handleServerStarting, new Object[] { this });
/*     */       }
/*     */       
/* 211 */       Reflector.callVoid(object1, Reflector.FMLCommonHandler_handleServerStarting, new Object[] { this });
/*     */     } 
/*     */     
/* 214 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/* 222 */     boolean flag = this.isGamePaused;
/* 223 */     this.isGamePaused = (Minecraft.getMinecraft().getNetHandler() != null && Minecraft.getMinecraft().isGamePaused());
/*     */     
/* 225 */     if (!flag && this.isGamePaused) {
/*     */       
/* 227 */       logger.info("Saving and pausing game...");
/* 228 */       getConfigurationManager().saveAllPlayerData();
/* 229 */       saveAllWorlds(false);
/*     */     } 
/*     */     
/* 232 */     if (this.isGamePaused) {
/*     */       
/* 234 */       Queue var3 = this.futureTaskQueue;
/*     */       
/* 236 */       synchronized (this.futureTaskQueue)
/*     */       {
/* 238 */         while (!this.futureTaskQueue.isEmpty())
/*     */         {
/* 240 */           Util.func_181617_a(this.futureTaskQueue.poll(), logger);
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 246 */       super.tick();
/*     */       
/* 248 */       if (this.mc.gameSettings.renderDistanceChunks != getConfigurationManager().getViewDistance()) {
/*     */         
/* 250 */         logger.info("Changing view distance to {}, from {}", new Object[] { Integer.valueOf(this.mc.gameSettings.renderDistanceChunks), Integer.valueOf(getConfigurationManager().getViewDistance()) });
/* 251 */         getConfigurationManager().setViewDistance(this.mc.gameSettings.renderDistanceChunks);
/*     */       } 
/*     */       
/* 254 */       if (this.mc.theWorld != null) {
/*     */         
/* 256 */         WorldInfo worldinfo = this.worldServers[0].getWorldInfo();
/* 257 */         WorldInfo worldinfo1 = this.mc.theWorld.getWorldInfo();
/*     */         
/* 259 */         if (!worldinfo.isDifficultyLocked() && worldinfo1.getDifficulty() != worldinfo.getDifficulty()) {
/*     */           
/* 261 */           logger.info("Changing difficulty to {}, from {}", new Object[] { worldinfo1.getDifficulty(), worldinfo.getDifficulty() });
/* 262 */           setDifficultyForAllWorlds(worldinfo1.getDifficulty());
/*     */         }
/* 264 */         else if (worldinfo1.isDifficultyLocked() && !worldinfo.isDifficultyLocked()) {
/*     */           
/* 266 */           logger.info("Locking difficulty to {}", new Object[] { worldinfo1.getDifficulty() }); byte b; int i;
/*     */           WorldServer[] arrayOfWorldServer;
/* 268 */           for (i = (arrayOfWorldServer = this.worldServers).length, b = 0; b < i; ) { WorldServer worldserver = arrayOfWorldServer[b];
/*     */             
/* 270 */             if (worldserver != null)
/*     */             {
/* 272 */               worldserver.getWorldInfo().setDifficultyLocked(true);
/*     */             }
/*     */             b++; }
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean canStructuresSpawn() {
/* 282 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldSettings.GameType getGameType() {
/* 287 */     return this.theWorldSettings.getGameType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumDifficulty getDifficulty() {
/* 295 */     return (this.mc.theWorld == null) ? this.mc.gameSettings.difficulty : this.mc.theWorld.getWorldInfo().getDifficulty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHardcore() {
/* 303 */     return this.theWorldSettings.getHardcoreEnabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_181034_q() {
/* 308 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_183002_r() {
/* 313 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public File getDataDirectory() {
/* 318 */     return this.mc.mcDataDir;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_181035_ah() {
/* 323 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDedicatedServer() {
/* 328 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalTick(CrashReport report) {
/* 336 */     this.mc.crashed(report);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReport addServerInfoToCrashReport(CrashReport report) {
/* 344 */     report = super.addServerInfoToCrashReport(report);
/* 345 */     report.getCategory().addCrashSectionCallable("Type", new Callable()
/*     */         {
/*     */           private static final String __OBFID = "CL_00001130";
/*     */           
/*     */           public String call() throws Exception {
/* 350 */             return "Integrated Server (map_client.txt)";
/*     */           }
/*     */         });
/* 353 */     report.getCategory().addCrashSectionCallable("Is Modded", new Callable()
/*     */         {
/*     */           private static final String __OBFID = "CL_00001131";
/*     */           
/*     */           public String call() throws Exception {
/* 358 */             String s = ClientBrandRetriever.getClientModName();
/*     */             
/* 360 */             if (!s.equals("vanilla"))
/*     */             {
/* 362 */               return "Definitely; Client brand changed to '" + s + "'";
/*     */             }
/*     */ 
/*     */             
/* 366 */             s = IntegratedServer.this.getServerModName();
/* 367 */             return !s.equals("vanilla") ? ("Definitely; Server brand changed to '" + s + "'") : ((Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and both client + server brands are untouched.");
/*     */           }
/*     */         });
/*     */     
/* 371 */     return report;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDifficultyForAllWorlds(EnumDifficulty difficulty) {
/* 376 */     super.setDifficultyForAllWorlds(difficulty);
/*     */     
/* 378 */     if (this.mc.theWorld != null)
/*     */     {
/* 380 */       this.mc.theWorld.getWorldInfo().setDifficulty(difficulty);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addServerStatsToSnooper(PlayerUsageSnooper playerSnooper) {
/* 386 */     super.addServerStatsToSnooper(playerSnooper);
/* 387 */     playerSnooper.addClientStat("snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSnooperEnabled() {
/* 395 */     return Minecraft.getMinecraft().isSnooperEnabled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String shareToLAN(WorldSettings.GameType type, boolean allowCheats) {
/*     */     try {
/* 405 */       int i = -1;
/*     */ 
/*     */       
/*     */       try {
/* 409 */         i = HttpUtil.getSuitableLanPort();
/*     */       }
/* 411 */       catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 416 */       if (i <= 0)
/*     */       {
/* 418 */         i = 25564;
/*     */       }
/*     */       
/* 421 */       getNetworkSystem().addLanEndpoint(null, i);
/* 422 */       logger.info("Started on " + i);
/* 423 */       this.isPublic = true;
/* 424 */       this.lanServerPing = new ThreadLanServerPing(getMOTD(), (new StringBuilder(String.valueOf(i))).toString());
/* 425 */       this.lanServerPing.start();
/* 426 */       getConfigurationManager().setGameType(type);
/* 427 */       getConfigurationManager().setCommandsAllowedForAll(allowCheats);
/* 428 */       return (new StringBuilder(String.valueOf(i))).toString();
/*     */     }
/* 430 */     catch (IOException var6) {
/*     */       
/* 432 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopServer() {
/* 441 */     super.stopServer();
/*     */     
/* 443 */     if (this.lanServerPing != null) {
/*     */       
/* 445 */       this.lanServerPing.interrupt();
/* 446 */       this.lanServerPing = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initiateShutdown() {
/* 455 */     Futures.getUnchecked((Future)addScheduledTask(new Runnable()
/*     */           {
/*     */             private static final String __OBFID = "CL_00002380";
/*     */             
/*     */             public void run() {
/* 460 */               for (EntityPlayerMP entityplayermp : Lists.newArrayList(IntegratedServer.this.getConfigurationManager().func_181057_v()))
/*     */               {
/* 462 */                 IntegratedServer.this.getConfigurationManager().playerLoggedOut(entityplayermp);
/*     */               }
/*     */             }
/*     */           }));
/* 466 */     super.initiateShutdown();
/*     */     
/* 468 */     if (this.lanServerPing != null) {
/*     */       
/* 470 */       this.lanServerPing.interrupt();
/* 471 */       this.lanServerPing = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStaticInstance() {
/* 477 */     setInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getPublic() {
/* 485 */     return this.isPublic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGameType(WorldSettings.GameType gameMode) {
/* 493 */     getConfigurationManager().setGameType(gameMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCommandBlockEnabled() {
/* 501 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOpPermissionLevel() {
/* 506 */     return 4;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\server\integrated\IntegratedServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */