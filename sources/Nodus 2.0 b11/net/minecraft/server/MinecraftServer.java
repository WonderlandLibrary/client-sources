/*    1:     */ package net.minecraft.server;
/*    2:     */ 
/*    3:     */ import com.google.common.base.Charsets;
/*    4:     */ import com.mojang.authlib.GameProfile;
/*    5:     */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*    6:     */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*    7:     */ import io.netty.buffer.ByteBuf;
/*    8:     */ import io.netty.buffer.ByteBufOutputStream;
/*    9:     */ import io.netty.buffer.Unpooled;
/*   10:     */ import io.netty.handler.codec.base64.Base64;
/*   11:     */ import java.awt.GraphicsEnvironment;
/*   12:     */ import java.awt.image.BufferedImage;
/*   13:     */ import java.io.File;
/*   14:     */ import java.io.IOException;
/*   15:     */ import java.net.Proxy;
/*   16:     */ import java.security.KeyPair;
/*   17:     */ import java.text.SimpleDateFormat;
/*   18:     */ import java.util.ArrayList;
/*   19:     */ import java.util.Arrays;
/*   20:     */ import java.util.Collections;
/*   21:     */ import java.util.Date;
/*   22:     */ import java.util.Iterator;
/*   23:     */ import java.util.List;
/*   24:     */ import java.util.Random;
/*   25:     */ import java.util.UUID;
/*   26:     */ import java.util.concurrent.Callable;
/*   27:     */ import javax.imageio.ImageIO;
/*   28:     */ import net.minecraft.command.CommandBase;
/*   29:     */ import net.minecraft.command.ICommandManager;
/*   30:     */ import net.minecraft.command.ICommandSender;
/*   31:     */ import net.minecraft.command.ServerCommandManager;
/*   32:     */ import net.minecraft.crash.CrashReport;
/*   33:     */ import net.minecraft.crash.CrashReportCategory;
/*   34:     */ import net.minecraft.entity.EntityTracker;
/*   35:     */ import net.minecraft.entity.player.EntityPlayer;
/*   36:     */ import net.minecraft.entity.player.EntityPlayerMP;
/*   37:     */ import net.minecraft.network.NetworkSystem;
/*   38:     */ import net.minecraft.network.ServerStatusResponse;
/*   39:     */ import net.minecraft.network.ServerStatusResponse.MinecraftProtocolVersionIdentifier;
/*   40:     */ import net.minecraft.network.ServerStatusResponse.PlayerCountData;
/*   41:     */ import net.minecraft.network.play.server.S03PacketTimeUpdate;
/*   42:     */ import net.minecraft.profiler.IPlayerUsage;
/*   43:     */ import net.minecraft.profiler.PlayerUsageSnooper;
/*   44:     */ import net.minecraft.profiler.Profiler;
/*   45:     */ import net.minecraft.server.gui.IUpdatePlayerListBox;
/*   46:     */ import net.minecraft.server.management.ServerConfigurationManager;
/*   47:     */ import net.minecraft.util.AABBPool;
/*   48:     */ import net.minecraft.util.AxisAlignedBB;
/*   49:     */ import net.minecraft.util.ChatComponentText;
/*   50:     */ import net.minecraft.util.ChunkCoordinates;
/*   51:     */ import net.minecraft.util.IChatComponent;
/*   52:     */ import net.minecraft.util.IProgressUpdate;
/*   53:     */ import net.minecraft.util.MathHelper;
/*   54:     */ import net.minecraft.util.ReportedException;
/*   55:     */ import net.minecraft.util.Vec3Pool;
/*   56:     */ import net.minecraft.world.EnumDifficulty;
/*   57:     */ import net.minecraft.world.GameRules;
/*   58:     */ import net.minecraft.world.MinecraftException;
/*   59:     */ import net.minecraft.world.World;
/*   60:     */ import net.minecraft.world.WorldManager;
/*   61:     */ import net.minecraft.world.WorldProvider;
/*   62:     */ import net.minecraft.world.WorldServer;
/*   63:     */ import net.minecraft.world.WorldServerMulti;
/*   64:     */ import net.minecraft.world.WorldSettings;
/*   65:     */ import net.minecraft.world.WorldSettings.GameType;
/*   66:     */ import net.minecraft.world.WorldType;
/*   67:     */ import net.minecraft.world.chunk.IChunkProvider;
/*   68:     */ import net.minecraft.world.chunk.storage.AnvilSaveConverter;
/*   69:     */ import net.minecraft.world.demo.DemoWorldServer;
/*   70:     */ import net.minecraft.world.gen.ChunkProviderServer;
/*   71:     */ import net.minecraft.world.storage.ISaveFormat;
/*   72:     */ import net.minecraft.world.storage.ISaveHandler;
/*   73:     */ import net.minecraft.world.storage.WorldInfo;
/*   74:     */ import org.apache.commons.lang3.Validate;
/*   75:     */ import org.apache.logging.log4j.LogManager;
/*   76:     */ import org.apache.logging.log4j.Logger;
/*   77:     */ 
/*   78:     */ public abstract class MinecraftServer
/*   79:     */   implements ICommandSender, Runnable, IPlayerUsage
/*   80:     */ {
/*   81:  69 */   private static final Logger logger = ;
/*   82:     */   private static MinecraftServer mcServer;
/*   83:     */   private final ISaveFormat anvilConverterForAnvilFile;
/*   84:  76 */   private final PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("server", this, getSystemTimeMillis());
/*   85:     */   private final File anvilFile;
/*   86:  82 */   private final List tickables = new ArrayList();
/*   87:     */   private final ICommandManager commandManager;
/*   88:  84 */   public final Profiler theProfiler = new Profiler();
/*   89:     */   private final NetworkSystem field_147144_o;
/*   90:  86 */   private final ServerStatusResponse field_147147_p = new ServerStatusResponse();
/*   91:  87 */   private final Random field_147146_q = new Random();
/*   92:  90 */   private int serverPort = -1;
/*   93:     */   public WorldServer[] worldServers;
/*   94:     */   private ServerConfigurationManager serverConfigManager;
/*   95: 101 */   private boolean serverRunning = true;
/*   96:     */   private boolean serverStopped;
/*   97:     */   private int tickCounter;
/*   98:     */   protected final Proxy serverProxy;
/*   99:     */   public String currentTask;
/*  100:     */   public int percentDone;
/*  101:     */   private boolean onlineMode;
/*  102:     */   private boolean canSpawnAnimals;
/*  103:     */   private boolean canSpawnNPCs;
/*  104:     */   private boolean pvpEnabled;
/*  105:     */   private boolean allowFlight;
/*  106:     */   private String motd;
/*  107:     */   private int buildLimit;
/*  108: 136 */   private int field_143008_E = 0;
/*  109: 137 */   public final long[] tickTimeArray = new long[100];
/*  110:     */   public long[][] timeOfLastDimensionTick;
/*  111:     */   private KeyPair serverKeyPair;
/*  112:     */   private String serverOwner;
/*  113:     */   private String folderName;
/*  114:     */   private String worldName;
/*  115:     */   private boolean isDemo;
/*  116:     */   private boolean enableBonusChest;
/*  117:     */   private boolean worldIsBeingDeleted;
/*  118: 154 */   private String field_147141_M = "";
/*  119:     */   private boolean serverIsRunning;
/*  120:     */   private long timeOfLastWarning;
/*  121:     */   private String userMessage;
/*  122:     */   private boolean startProfiling;
/*  123:     */   private boolean isGamemodeForced;
/*  124:     */   private final MinecraftSessionService field_147143_S;
/*  125: 165 */   private long field_147142_T = 0L;
/*  126:     */   private static final String __OBFID = "CL_00001462";
/*  127:     */   
/*  128:     */   public MinecraftServer(File p_i45281_1_, Proxy p_i45281_2_)
/*  129:     */   {
/*  130: 170 */     mcServer = this;
/*  131: 171 */     this.serverProxy = p_i45281_2_;
/*  132: 172 */     this.anvilFile = p_i45281_1_;
/*  133: 173 */     this.field_147144_o = new NetworkSystem(this);
/*  134: 174 */     this.commandManager = new ServerCommandManager();
/*  135: 175 */     this.anvilConverterForAnvilFile = new AnvilSaveConverter(p_i45281_1_);
/*  136: 176 */     this.field_147143_S = new YggdrasilAuthenticationService(p_i45281_2_, UUID.randomUUID().toString()).createMinecraftSessionService();
/*  137:     */   }
/*  138:     */   
/*  139:     */   protected abstract boolean startServer()
/*  140:     */     throws IOException;
/*  141:     */   
/*  142:     */   protected void convertMapIfNeeded(String par1Str)
/*  143:     */   {
/*  144: 186 */     if (getActiveAnvilConverter().isOldMapFormat(par1Str))
/*  145:     */     {
/*  146: 188 */       logger.info("Converting map!");
/*  147: 189 */       setUserMessage("menu.convertingLevel");
/*  148: 190 */       getActiveAnvilConverter().convertMapFormat(par1Str, new IProgressUpdate()
/*  149:     */       {
/*  150: 192 */         private long field_96245_b = System.currentTimeMillis();
/*  151:     */         private static final String __OBFID = "CL_00001417";
/*  152:     */         
/*  153:     */         public void displayProgressMessage(String par1Str) {}
/*  154:     */         
/*  155:     */         public void resetProgressAndMessage(String par1Str) {}
/*  156:     */         
/*  157:     */         public void setLoadingProgress(int par1)
/*  158:     */         {
/*  159: 198 */           if (System.currentTimeMillis() - this.field_96245_b >= 1000L)
/*  160:     */           {
/*  161: 200 */             this.field_96245_b = System.currentTimeMillis();
/*  162: 201 */             MinecraftServer.logger.info("Converting... " + par1 + "%");
/*  163:     */           }
/*  164:     */         }
/*  165:     */         
/*  166:     */         public void func_146586_a() {}
/*  167:     */         
/*  168:     */         public void resetProgresAndWorkingMessage(String par1Str) {}
/*  169:     */       });
/*  170:     */     }
/*  171:     */   }
/*  172:     */   
/*  173:     */   protected synchronized void setUserMessage(String par1Str)
/*  174:     */   {
/*  175: 215 */     this.userMessage = par1Str;
/*  176:     */   }
/*  177:     */   
/*  178:     */   public synchronized String getUserMessage()
/*  179:     */   {
/*  180: 220 */     return this.userMessage;
/*  181:     */   }
/*  182:     */   
/*  183:     */   protected void loadAllWorlds(String par1Str, String par2Str, long par3, WorldType par5WorldType, String par6Str)
/*  184:     */   {
/*  185: 225 */     convertMapIfNeeded(par1Str);
/*  186: 226 */     setUserMessage("menu.loadingLevel");
/*  187: 227 */     this.worldServers = new WorldServer[3];
/*  188: 228 */     this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
/*  189: 229 */     ISaveHandler var7 = this.anvilConverterForAnvilFile.getSaveLoader(par1Str, true);
/*  190: 230 */     WorldInfo var9 = var7.loadWorldInfo();
/*  191:     */     WorldSettings var8;
/*  192: 233 */     if (var9 == null)
/*  193:     */     {
/*  194: 235 */       WorldSettings var8 = new WorldSettings(par3, getGameType(), canStructuresSpawn(), isHardcore(), par5WorldType);
/*  195: 236 */       var8.func_82750_a(par6Str);
/*  196:     */     }
/*  197:     */     else
/*  198:     */     {
/*  199: 240 */       var8 = new WorldSettings(var9);
/*  200:     */     }
/*  201: 243 */     if (this.enableBonusChest) {
/*  202: 245 */       var8.enableBonusChest();
/*  203:     */     }
/*  204: 248 */     for (int var10 = 0; var10 < this.worldServers.length; var10++)
/*  205:     */     {
/*  206: 250 */       byte var11 = 0;
/*  207: 252 */       if (var10 == 1) {
/*  208: 254 */         var11 = -1;
/*  209:     */       }
/*  210: 257 */       if (var10 == 2) {
/*  211: 259 */         var11 = 1;
/*  212:     */       }
/*  213: 262 */       if (var10 == 0)
/*  214:     */       {
/*  215: 264 */         if (isDemo()) {
/*  216: 266 */           this.worldServers[var10] = new DemoWorldServer(this, var7, par2Str, var11, this.theProfiler);
/*  217:     */         } else {
/*  218: 270 */           this.worldServers[var10] = new WorldServer(this, var7, par2Str, var11, var8, this.theProfiler);
/*  219:     */         }
/*  220:     */       }
/*  221:     */       else {
/*  222: 275 */         this.worldServers[var10] = new WorldServerMulti(this, var7, par2Str, var11, var8, this.worldServers[0], this.theProfiler);
/*  223:     */       }
/*  224: 278 */       this.worldServers[var10].addWorldAccess(new WorldManager(this, this.worldServers[var10]));
/*  225: 280 */       if (!isSinglePlayer()) {
/*  226: 282 */         this.worldServers[var10].getWorldInfo().setGameType(getGameType());
/*  227:     */       }
/*  228: 285 */       this.serverConfigManager.setPlayerManager(this.worldServers);
/*  229:     */     }
/*  230: 288 */     func_147139_a(func_147135_j());
/*  231: 289 */     initialWorldChunkLoad();
/*  232:     */   }
/*  233:     */   
/*  234:     */   protected void initialWorldChunkLoad()
/*  235:     */   {
/*  236: 294 */     boolean var1 = true;
/*  237: 295 */     boolean var2 = true;
/*  238: 296 */     boolean var3 = true;
/*  239: 297 */     boolean var4 = true;
/*  240: 298 */     int var5 = 0;
/*  241: 299 */     setUserMessage("menu.generatingTerrain");
/*  242: 300 */     byte var6 = 0;
/*  243: 301 */     logger.info("Preparing start region for level " + var6);
/*  244: 302 */     WorldServer var7 = this.worldServers[var6];
/*  245: 303 */     ChunkCoordinates var8 = var7.getSpawnPoint();
/*  246: 304 */     long var9 = getSystemTimeMillis();
/*  247: 306 */     for (int var11 = -192; (var11 <= 192) && (isServerRunning()); var11 += 16) {
/*  248: 308 */       for (int var12 = -192; (var12 <= 192) && (isServerRunning()); var12 += 16)
/*  249:     */       {
/*  250: 310 */         long var13 = getSystemTimeMillis();
/*  251: 312 */         if (var13 - var9 > 1000L)
/*  252:     */         {
/*  253: 314 */           outputPercentRemaining("Preparing spawn area", var5 * 100 / 625);
/*  254: 315 */           var9 = var13;
/*  255:     */         }
/*  256: 318 */         var5++;
/*  257: 319 */         var7.theChunkProviderServer.loadChunk(var8.posX + var11 >> 4, var8.posZ + var12 >> 4);
/*  258:     */       }
/*  259:     */     }
/*  260: 323 */     clearCurrentTask();
/*  261:     */   }
/*  262:     */   
/*  263:     */   public abstract boolean canStructuresSpawn();
/*  264:     */   
/*  265:     */   public abstract WorldSettings.GameType getGameType();
/*  266:     */   
/*  267:     */   public abstract EnumDifficulty func_147135_j();
/*  268:     */   
/*  269:     */   public abstract boolean isHardcore();
/*  270:     */   
/*  271:     */   public abstract int func_110455_j();
/*  272:     */   
/*  273:     */   protected void outputPercentRemaining(String par1Str, int par2)
/*  274:     */   {
/*  275: 344 */     this.currentTask = par1Str;
/*  276: 345 */     this.percentDone = par2;
/*  277: 346 */     logger.info(par1Str + ": " + par2 + "%");
/*  278:     */   }
/*  279:     */   
/*  280:     */   protected void clearCurrentTask()
/*  281:     */   {
/*  282: 354 */     this.currentTask = null;
/*  283: 355 */     this.percentDone = 0;
/*  284:     */   }
/*  285:     */   
/*  286:     */   protected void saveAllWorlds(boolean par1)
/*  287:     */   {
/*  288: 363 */     if (!this.worldIsBeingDeleted)
/*  289:     */     {
/*  290: 365 */       WorldServer[] var2 = this.worldServers;
/*  291: 366 */       int var3 = var2.length;
/*  292: 368 */       for (int var4 = 0; var4 < var3; var4++)
/*  293:     */       {
/*  294: 370 */         WorldServer var5 = var2[var4];
/*  295: 372 */         if (var5 != null)
/*  296:     */         {
/*  297: 374 */           if (!par1) {
/*  298: 376 */             logger.info("Saving chunks for level '" + var5.getWorldInfo().getWorldName() + "'/" + var5.provider.getDimensionName());
/*  299:     */           }
/*  300:     */           try
/*  301:     */           {
/*  302: 381 */             var5.saveAllChunks(true, null);
/*  303:     */           }
/*  304:     */           catch (MinecraftException var7)
/*  305:     */           {
/*  306: 385 */             logger.warn(var7.getMessage());
/*  307:     */           }
/*  308:     */         }
/*  309:     */       }
/*  310:     */     }
/*  311:     */   }
/*  312:     */   
/*  313:     */   public void stopServer()
/*  314:     */   {
/*  315: 397 */     if (!this.worldIsBeingDeleted)
/*  316:     */     {
/*  317: 399 */       logger.info("Stopping server");
/*  318: 401 */       if (func_147137_ag() != null) {
/*  319: 403 */         func_147137_ag().terminateEndpoints();
/*  320:     */       }
/*  321: 406 */       if (this.serverConfigManager != null)
/*  322:     */       {
/*  323: 408 */         logger.info("Saving players");
/*  324: 409 */         this.serverConfigManager.saveAllPlayerData();
/*  325: 410 */         this.serverConfigManager.removeAllPlayers();
/*  326:     */       }
/*  327: 413 */       logger.info("Saving worlds");
/*  328: 414 */       saveAllWorlds(false);
/*  329: 416 */       for (int var1 = 0; var1 < this.worldServers.length; var1++)
/*  330:     */       {
/*  331: 418 */         WorldServer var2 = this.worldServers[var1];
/*  332: 419 */         var2.flush();
/*  333:     */       }
/*  334: 422 */       if (this.usageSnooper.isSnooperRunning()) {
/*  335: 424 */         this.usageSnooper.stopSnooper();
/*  336:     */       }
/*  337:     */     }
/*  338:     */   }
/*  339:     */   
/*  340:     */   public boolean isServerRunning()
/*  341:     */   {
/*  342: 431 */     return this.serverRunning;
/*  343:     */   }
/*  344:     */   
/*  345:     */   public void initiateShutdown()
/*  346:     */   {
/*  347: 439 */     this.serverRunning = false;
/*  348:     */   }
/*  349:     */   
/*  350:     */   public void run()
/*  351:     */   {
/*  352:     */     try
/*  353:     */     {
/*  354: 446 */       if (startServer())
/*  355:     */       {
/*  356: 448 */         long var1 = getSystemTimeMillis();
/*  357: 449 */         long var50 = 0L;
/*  358: 450 */         this.field_147147_p.func_151315_a(new ChatComponentText(this.motd));
/*  359: 451 */         this.field_147147_p.func_151321_a(new ServerStatusResponse.MinecraftProtocolVersionIdentifier("1.7.2", 4));
/*  360: 452 */         func_147138_a(this.field_147147_p);
/*  361: 454 */         while (this.serverRunning)
/*  362:     */         {
/*  363: 456 */           long var5 = getSystemTimeMillis();
/*  364: 457 */           long var7 = var5 - var1;
/*  365: 459 */           if ((var7 > 2000L) && (var1 - this.timeOfLastWarning >= 15000L))
/*  366:     */           {
/*  367: 461 */             logger.warn("Can't keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)", new Object[] { Long.valueOf(var7), Long.valueOf(var7 / 50L) });
/*  368: 462 */             var7 = 2000L;
/*  369: 463 */             this.timeOfLastWarning = var1;
/*  370:     */           }
/*  371: 466 */           if (var7 < 0L)
/*  372:     */           {
/*  373: 468 */             logger.warn("Time ran backwards! Did the system time change?");
/*  374: 469 */             var7 = 0L;
/*  375:     */           }
/*  376: 472 */           var50 += var7;
/*  377: 473 */           var1 = var5;
/*  378: 475 */           if (this.worldServers[0].areAllPlayersAsleep())
/*  379:     */           {
/*  380: 477 */             tick();
/*  381: 478 */             var50 = 0L;
/*  382:     */           }
/*  383:     */           else
/*  384:     */           {
/*  385: 482 */             while (var50 > 50L)
/*  386:     */             {
/*  387: 484 */               var50 -= 50L;
/*  388: 485 */               tick();
/*  389:     */             }
/*  390:     */           }
/*  391: 489 */           Thread.sleep(1L);
/*  392: 490 */           this.serverIsRunning = true;
/*  393:     */         }
/*  394:     */       }
/*  395:     */       else
/*  396:     */       {
/*  397: 495 */         finalTick(null);
/*  398:     */       }
/*  399:     */     }
/*  400:     */     catch (Throwable var48)
/*  401:     */     {
/*  402: 500 */       logger.error("Encountered an unexpected exception", var48);
/*  403: 501 */       CrashReport var2 = null;
/*  404: 503 */       if ((var48 instanceof ReportedException)) {
/*  405: 505 */         var2 = addServerInfoToCrashReport(((ReportedException)var48).getCrashReport());
/*  406:     */       } else {
/*  407: 509 */         var2 = addServerInfoToCrashReport(new CrashReport("Exception in server tick loop", var48));
/*  408:     */       }
/*  409: 512 */       File var3 = new File(new File(getDataDirectory(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt");
/*  410: 514 */       if (var2.saveToFile(var3)) {
/*  411: 516 */         logger.error("This crash report has been saved to: " + var3.getAbsolutePath());
/*  412:     */       } else {
/*  413: 520 */         logger.error("We were unable to save this crash report to disk.");
/*  414:     */       }
/*  415: 523 */       finalTick(var2);
/*  416:     */     }
/*  417:     */     finally
/*  418:     */     {
/*  419:     */       try
/*  420:     */       {
/*  421: 529 */         stopServer();
/*  422: 530 */         this.serverStopped = true;
/*  423:     */       }
/*  424:     */       catch (Throwable var46)
/*  425:     */       {
/*  426: 534 */         logger.error("Exception stopping the server", var46);
/*  427:     */       }
/*  428:     */       finally
/*  429:     */       {
/*  430: 538 */         systemExitNow();
/*  431:     */       }
/*  432:     */     }
/*  433:     */   }
/*  434:     */   
/*  435:     */   private void func_147138_a(ServerStatusResponse p_147138_1_)
/*  436:     */   {
/*  437: 545 */     File var2 = getFile("server-icon.png");
/*  438: 547 */     if (var2.isFile())
/*  439:     */     {
/*  440: 549 */       ByteBuf var3 = Unpooled.buffer();
/*  441:     */       try
/*  442:     */       {
/*  443: 553 */         BufferedImage var4 = ImageIO.read(var2);
/*  444: 554 */         Validate.validState(var4.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
/*  445: 555 */         Validate.validState(var4.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
/*  446: 556 */         ImageIO.write(var4, "PNG", new ByteBufOutputStream(var3));
/*  447: 557 */         ByteBuf var5 = Base64.encode(var3);
/*  448: 558 */         p_147138_1_.func_151320_a("data:image/png;base64," + var5.toString(Charsets.UTF_8));
/*  449:     */       }
/*  450:     */       catch (Exception var6)
/*  451:     */       {
/*  452: 562 */         logger.error("Couldn't load server icon", var6);
/*  453:     */       }
/*  454:     */     }
/*  455:     */   }
/*  456:     */   
/*  457:     */   protected File getDataDirectory()
/*  458:     */   {
/*  459: 569 */     return new File(".");
/*  460:     */   }
/*  461:     */   
/*  462:     */   protected void finalTick(CrashReport par1CrashReport) {}
/*  463:     */   
/*  464:     */   protected void systemExitNow() {}
/*  465:     */   
/*  466:     */   public void tick()
/*  467:     */   {
/*  468: 587 */     long var1 = System.nanoTime();
/*  469: 588 */     AxisAlignedBB.getAABBPool().cleanPool();
/*  470: 589 */     this.tickCounter += 1;
/*  471: 591 */     if (this.startProfiling)
/*  472:     */     {
/*  473: 593 */       this.startProfiling = false;
/*  474: 594 */       this.theProfiler.profilingEnabled = true;
/*  475: 595 */       this.theProfiler.clearProfiling();
/*  476:     */     }
/*  477: 598 */     this.theProfiler.startSection("root");
/*  478: 599 */     updateTimeLightAndEntities();
/*  479: 601 */     if (var1 - this.field_147142_T >= 5000000000L)
/*  480:     */     {
/*  481: 603 */       this.field_147142_T = var1;
/*  482: 604 */       this.field_147147_p.func_151319_a(new ServerStatusResponse.PlayerCountData(getMaxPlayers(), getCurrentPlayerCount()));
/*  483: 605 */       GameProfile[] var3 = new GameProfile[Math.min(getCurrentPlayerCount(), 12)];
/*  484: 606 */       int var4 = MathHelper.getRandomIntegerInRange(this.field_147146_q, 0, getCurrentPlayerCount() - var3.length);
/*  485: 608 */       for (int var5 = 0; var5 < var3.length; var5++) {
/*  486: 610 */         var3[var5] = ((EntityPlayerMP)this.serverConfigManager.playerEntityList.get(var4 + var5)).getGameProfile();
/*  487:     */       }
/*  488: 613 */       Collections.shuffle(Arrays.asList(var3));
/*  489: 614 */       this.field_147147_p.func_151318_b().func_151330_a(var3);
/*  490:     */     }
/*  491: 617 */     if (this.tickCounter % 900 == 0)
/*  492:     */     {
/*  493: 619 */       this.theProfiler.startSection("save");
/*  494: 620 */       this.serverConfigManager.saveAllPlayerData();
/*  495: 621 */       saveAllWorlds(true);
/*  496: 622 */       this.theProfiler.endSection();
/*  497:     */     }
/*  498: 625 */     this.theProfiler.startSection("tallying");
/*  499: 626 */     this.tickTimeArray[(this.tickCounter % 100)] = (System.nanoTime() - var1);
/*  500: 627 */     this.theProfiler.endSection();
/*  501: 628 */     this.theProfiler.startSection("snooper");
/*  502: 630 */     if ((!this.usageSnooper.isSnooperRunning()) && (this.tickCounter > 100)) {
/*  503: 632 */       this.usageSnooper.startSnooper();
/*  504:     */     }
/*  505: 635 */     if (this.tickCounter % 6000 == 0) {
/*  506: 637 */       this.usageSnooper.addMemoryStatsToSnooper();
/*  507:     */     }
/*  508: 640 */     this.theProfiler.endSection();
/*  509: 641 */     this.theProfiler.endSection();
/*  510:     */   }
/*  511:     */   
/*  512:     */   public void updateTimeLightAndEntities()
/*  513:     */   {
/*  514: 646 */     this.theProfiler.startSection("levels");
/*  515: 649 */     for (int var1 = 0; var1 < this.worldServers.length; var1++)
/*  516:     */     {
/*  517: 651 */       long var2 = System.nanoTime();
/*  518: 653 */       if ((var1 == 0) || (getAllowNether()))
/*  519:     */       {
/*  520: 655 */         WorldServer var4 = this.worldServers[var1];
/*  521: 656 */         this.theProfiler.startSection(var4.getWorldInfo().getWorldName());
/*  522: 657 */         this.theProfiler.startSection("pools");
/*  523: 658 */         var4.getWorldVec3Pool().clear();
/*  524: 659 */         this.theProfiler.endSection();
/*  525: 661 */         if (this.tickCounter % 20 == 0)
/*  526:     */         {
/*  527: 663 */           this.theProfiler.startSection("timeSync");
/*  528: 664 */           this.serverConfigManager.func_148537_a(new S03PacketTimeUpdate(var4.getTotalWorldTime(), var4.getWorldTime(), var4.getGameRules().getGameRuleBooleanValue("doDaylightCycle")), var4.provider.dimensionId);
/*  529: 665 */           this.theProfiler.endSection();
/*  530:     */         }
/*  531: 668 */         this.theProfiler.startSection("tick");
/*  532:     */         try
/*  533:     */         {
/*  534: 673 */           var4.tick();
/*  535:     */         }
/*  536:     */         catch (Throwable var8)
/*  537:     */         {
/*  538: 677 */           CrashReport var6 = CrashReport.makeCrashReport(var8, "Exception ticking world");
/*  539: 678 */           var4.addWorldInfoToCrashReport(var6);
/*  540: 679 */           throw new ReportedException(var6);
/*  541:     */         }
/*  542:     */         try
/*  543:     */         {
/*  544: 684 */           var4.updateEntities();
/*  545:     */         }
/*  546:     */         catch (Throwable var7)
/*  547:     */         {
/*  548: 688 */           CrashReport var6 = CrashReport.makeCrashReport(var7, "Exception ticking world entities");
/*  549: 689 */           var4.addWorldInfoToCrashReport(var6);
/*  550: 690 */           throw new ReportedException(var6);
/*  551:     */         }
/*  552: 693 */         this.theProfiler.endSection();
/*  553: 694 */         this.theProfiler.startSection("tracker");
/*  554: 695 */         var4.getEntityTracker().updateTrackedEntities();
/*  555: 696 */         this.theProfiler.endSection();
/*  556: 697 */         this.theProfiler.endSection();
/*  557:     */       }
/*  558: 700 */       this.timeOfLastDimensionTick[var1][(this.tickCounter % 100)] = (System.nanoTime() - var2);
/*  559:     */     }
/*  560: 703 */     this.theProfiler.endStartSection("connection");
/*  561: 704 */     func_147137_ag().networkTick();
/*  562: 705 */     this.theProfiler.endStartSection("players");
/*  563: 706 */     this.serverConfigManager.sendPlayerInfoToAllPlayers();
/*  564: 707 */     this.theProfiler.endStartSection("tickables");
/*  565: 709 */     for (var1 = 0; var1 < this.tickables.size(); var1++) {
/*  566: 711 */       ((IUpdatePlayerListBox)this.tickables.get(var1)).update();
/*  567:     */     }
/*  568: 714 */     this.theProfiler.endSection();
/*  569:     */   }
/*  570:     */   
/*  571:     */   public boolean getAllowNether()
/*  572:     */   {
/*  573: 719 */     return true;
/*  574:     */   }
/*  575:     */   
/*  576:     */   public void startServerThread()
/*  577:     */   {
/*  578: 731 */     new Thread("Server thread")
/*  579:     */     {
/*  580:     */       private static final String __OBFID = "CL_00001418";
/*  581:     */       
/*  582:     */       public void run()
/*  583:     */       {
/*  584: 729 */         MinecraftServer.this.run();
/*  585:     */       }
/*  586:     */     }.start();
/*  587:     */   }
/*  588:     */   
/*  589:     */   public File getFile(String par1Str)
/*  590:     */   {
/*  591: 739 */     return new File(getDataDirectory(), par1Str);
/*  592:     */   }
/*  593:     */   
/*  594:     */   public void logWarning(String par1Str)
/*  595:     */   {
/*  596: 747 */     logger.warn(par1Str);
/*  597:     */   }
/*  598:     */   
/*  599:     */   public WorldServer worldServerForDimension(int par1)
/*  600:     */   {
/*  601: 755 */     return par1 == 1 ? this.worldServers[2] : par1 == -1 ? this.worldServers[1] : this.worldServers[0];
/*  602:     */   }
/*  603:     */   
/*  604:     */   public String getMinecraftVersion()
/*  605:     */   {
/*  606: 763 */     return "1.7.2";
/*  607:     */   }
/*  608:     */   
/*  609:     */   public int getCurrentPlayerCount()
/*  610:     */   {
/*  611: 771 */     return this.serverConfigManager.getCurrentPlayerCount();
/*  612:     */   }
/*  613:     */   
/*  614:     */   public int getMaxPlayers()
/*  615:     */   {
/*  616: 779 */     return this.serverConfigManager.getMaxPlayers();
/*  617:     */   }
/*  618:     */   
/*  619:     */   public String[] getAllUsernames()
/*  620:     */   {
/*  621: 787 */     return this.serverConfigManager.getAllUsernames();
/*  622:     */   }
/*  623:     */   
/*  624:     */   public String getServerModName()
/*  625:     */   {
/*  626: 792 */     return "vanilla";
/*  627:     */   }
/*  628:     */   
/*  629:     */   public CrashReport addServerInfoToCrashReport(CrashReport par1CrashReport)
/*  630:     */   {
/*  631: 800 */     par1CrashReport.getCategory().addCrashSectionCallable("Profiler Position", new Callable()
/*  632:     */     {
/*  633:     */       private static final String __OBFID = "CL_00001419";
/*  634:     */       
/*  635:     */       public String call()
/*  636:     */       {
/*  637: 805 */         return MinecraftServer.this.theProfiler.profilingEnabled ? MinecraftServer.this.theProfiler.getNameOfLastSection() : "N/A (disabled)";
/*  638:     */       }
/*  639:     */     });
/*  640: 809 */     if ((this.worldServers != null) && (this.worldServers.length > 0) && (this.worldServers[0] != null)) {
/*  641: 811 */       par1CrashReport.getCategory().addCrashSectionCallable("Vec3 Pool Size", new Callable()
/*  642:     */       {
/*  643:     */         private static final String __OBFID = "CL_00001420";
/*  644:     */         
/*  645:     */         public String call()
/*  646:     */         {
/*  647: 816 */           int var1 = MinecraftServer.this.worldServers[0].getWorldVec3Pool().getPoolSize();
/*  648: 817 */           int var2 = 56 * var1;
/*  649: 818 */           int var3 = var2 / 1024 / 1024;
/*  650: 819 */           int var4 = MinecraftServer.this.worldServers[0].getWorldVec3Pool().getNextFreeSpace();
/*  651: 820 */           int var5 = 56 * var4;
/*  652: 821 */           int var6 = var5 / 1024 / 1024;
/*  653: 822 */           return var1 + " (" + var2 + " bytes; " + var3 + " MB) allocated, " + var4 + " (" + var5 + " bytes; " + var6 + " MB) used";
/*  654:     */         }
/*  655:     */       });
/*  656:     */     }
/*  657: 827 */     if (this.serverConfigManager != null) {
/*  658: 829 */       par1CrashReport.getCategory().addCrashSectionCallable("Player Count", new Callable()
/*  659:     */       {
/*  660:     */         private static final String __OBFID = "CL_00001780";
/*  661:     */         
/*  662:     */         public String call()
/*  663:     */         {
/*  664: 834 */           return MinecraftServer.this.serverConfigManager.getCurrentPlayerCount() + " / " + MinecraftServer.this.serverConfigManager.getMaxPlayers() + "; " + MinecraftServer.this.serverConfigManager.playerEntityList;
/*  665:     */         }
/*  666:     */       });
/*  667:     */     }
/*  668: 839 */     return par1CrashReport;
/*  669:     */   }
/*  670:     */   
/*  671:     */   public List getPossibleCompletions(ICommandSender par1ICommandSender, String par2Str)
/*  672:     */   {
/*  673: 847 */     ArrayList var3 = new ArrayList();
/*  674: 849 */     if (par2Str.startsWith("/"))
/*  675:     */     {
/*  676: 851 */       par2Str = par2Str.substring(1);
/*  677: 852 */       boolean var10 = !par2Str.contains(" ");
/*  678: 853 */       List var11 = this.commandManager.getPossibleCommands(par1ICommandSender, par2Str);
/*  679: 855 */       if (var11 != null)
/*  680:     */       {
/*  681: 857 */         Iterator var12 = var11.iterator();
/*  682: 859 */         while (var12.hasNext())
/*  683:     */         {
/*  684: 861 */           String var13 = (String)var12.next();
/*  685: 863 */           if (var10) {
/*  686: 865 */             var3.add("/" + var13);
/*  687:     */           } else {
/*  688: 869 */             var3.add(var13);
/*  689:     */           }
/*  690:     */         }
/*  691:     */       }
/*  692: 874 */       return var3;
/*  693:     */     }
/*  694: 878 */     String[] var4 = par2Str.split(" ", -1);
/*  695: 879 */     String var5 = var4[(var4.length - 1)];
/*  696: 880 */     String[] var6 = this.serverConfigManager.getAllUsernames();
/*  697: 881 */     int var7 = var6.length;
/*  698: 883 */     for (int var8 = 0; var8 < var7; var8++)
/*  699:     */     {
/*  700: 885 */       String var9 = var6[var8];
/*  701: 887 */       if (CommandBase.doesStringStartWith(var5, var9)) {
/*  702: 889 */         var3.add(var9);
/*  703:     */       }
/*  704:     */     }
/*  705: 893 */     return var3;
/*  706:     */   }
/*  707:     */   
/*  708:     */   public static MinecraftServer getServer()
/*  709:     */   {
/*  710: 902 */     return mcServer;
/*  711:     */   }
/*  712:     */   
/*  713:     */   public String getCommandSenderName()
/*  714:     */   {
/*  715: 910 */     return "Server";
/*  716:     */   }
/*  717:     */   
/*  718:     */   public void addChatMessage(IChatComponent p_145747_1_)
/*  719:     */   {
/*  720: 921 */     logger.info(p_145747_1_.getUnformattedText());
/*  721:     */   }
/*  722:     */   
/*  723:     */   public boolean canCommandSenderUseCommand(int par1, String par2Str)
/*  724:     */   {
/*  725: 929 */     return true;
/*  726:     */   }
/*  727:     */   
/*  728:     */   public ICommandManager getCommandManager()
/*  729:     */   {
/*  730: 934 */     return this.commandManager;
/*  731:     */   }
/*  732:     */   
/*  733:     */   public KeyPair getKeyPair()
/*  734:     */   {
/*  735: 942 */     return this.serverKeyPair;
/*  736:     */   }
/*  737:     */   
/*  738:     */   public String getServerOwner()
/*  739:     */   {
/*  740: 950 */     return this.serverOwner;
/*  741:     */   }
/*  742:     */   
/*  743:     */   public void setServerOwner(String par1Str)
/*  744:     */   {
/*  745: 958 */     this.serverOwner = par1Str;
/*  746:     */   }
/*  747:     */   
/*  748:     */   public boolean isSinglePlayer()
/*  749:     */   {
/*  750: 963 */     return this.serverOwner != null;
/*  751:     */   }
/*  752:     */   
/*  753:     */   public String getFolderName()
/*  754:     */   {
/*  755: 968 */     return this.folderName;
/*  756:     */   }
/*  757:     */   
/*  758:     */   public void setFolderName(String par1Str)
/*  759:     */   {
/*  760: 973 */     this.folderName = par1Str;
/*  761:     */   }
/*  762:     */   
/*  763:     */   public void setWorldName(String par1Str)
/*  764:     */   {
/*  765: 978 */     this.worldName = par1Str;
/*  766:     */   }
/*  767:     */   
/*  768:     */   public String getWorldName()
/*  769:     */   {
/*  770: 983 */     return this.worldName;
/*  771:     */   }
/*  772:     */   
/*  773:     */   public void setKeyPair(KeyPair par1KeyPair)
/*  774:     */   {
/*  775: 988 */     this.serverKeyPair = par1KeyPair;
/*  776:     */   }
/*  777:     */   
/*  778:     */   public void func_147139_a(EnumDifficulty p_147139_1_)
/*  779:     */   {
/*  780: 993 */     for (int var2 = 0; var2 < this.worldServers.length; var2++)
/*  781:     */     {
/*  782: 995 */       WorldServer var3 = this.worldServers[var2];
/*  783: 997 */       if (var3 != null) {
/*  784: 999 */         if (var3.getWorldInfo().isHardcoreModeEnabled())
/*  785:     */         {
/*  786:1001 */           var3.difficultySetting = EnumDifficulty.HARD;
/*  787:1002 */           var3.setAllowedSpawnTypes(true, true);
/*  788:     */         }
/*  789:1004 */         else if (isSinglePlayer())
/*  790:     */         {
/*  791:1006 */           var3.difficultySetting = p_147139_1_;
/*  792:1007 */           var3.setAllowedSpawnTypes(var3.difficultySetting != EnumDifficulty.PEACEFUL, true);
/*  793:     */         }
/*  794:     */         else
/*  795:     */         {
/*  796:1011 */           var3.difficultySetting = p_147139_1_;
/*  797:1012 */           var3.setAllowedSpawnTypes(allowSpawnMonsters(), this.canSpawnAnimals);
/*  798:     */         }
/*  799:     */       }
/*  800:     */     }
/*  801:     */   }
/*  802:     */   
/*  803:     */   protected boolean allowSpawnMonsters()
/*  804:     */   {
/*  805:1020 */     return true;
/*  806:     */   }
/*  807:     */   
/*  808:     */   public boolean isDemo()
/*  809:     */   {
/*  810:1028 */     return this.isDemo;
/*  811:     */   }
/*  812:     */   
/*  813:     */   public void setDemo(boolean par1)
/*  814:     */   {
/*  815:1036 */     this.isDemo = par1;
/*  816:     */   }
/*  817:     */   
/*  818:     */   public void canCreateBonusChest(boolean par1)
/*  819:     */   {
/*  820:1041 */     this.enableBonusChest = par1;
/*  821:     */   }
/*  822:     */   
/*  823:     */   public ISaveFormat getActiveAnvilConverter()
/*  824:     */   {
/*  825:1046 */     return this.anvilConverterForAnvilFile;
/*  826:     */   }
/*  827:     */   
/*  828:     */   public void deleteWorldAndStopServer()
/*  829:     */   {
/*  830:1055 */     this.worldIsBeingDeleted = true;
/*  831:1056 */     getActiveAnvilConverter().flushCache();
/*  832:1058 */     for (int var1 = 0; var1 < this.worldServers.length; var1++)
/*  833:     */     {
/*  834:1060 */       WorldServer var2 = this.worldServers[var1];
/*  835:1062 */       if (var2 != null) {
/*  836:1064 */         var2.flush();
/*  837:     */       }
/*  838:     */     }
/*  839:1068 */     getActiveAnvilConverter().deleteWorldDirectory(this.worldServers[0].getSaveHandler().getWorldDirectoryName());
/*  840:1069 */     initiateShutdown();
/*  841:     */   }
/*  842:     */   
/*  843:     */   public String func_147133_T()
/*  844:     */   {
/*  845:1074 */     return this.field_147141_M;
/*  846:     */   }
/*  847:     */   
/*  848:     */   public void addServerStatsToSnooper(PlayerUsageSnooper par1PlayerUsageSnooper)
/*  849:     */   {
/*  850:1079 */     par1PlayerUsageSnooper.addData("whitelist_enabled", Boolean.valueOf(false));
/*  851:1080 */     par1PlayerUsageSnooper.addData("whitelist_count", Integer.valueOf(0));
/*  852:1081 */     par1PlayerUsageSnooper.addData("players_current", Integer.valueOf(getCurrentPlayerCount()));
/*  853:1082 */     par1PlayerUsageSnooper.addData("players_max", Integer.valueOf(getMaxPlayers()));
/*  854:1083 */     par1PlayerUsageSnooper.addData("players_seen", Integer.valueOf(this.serverConfigManager.getAvailablePlayerDat().length));
/*  855:1084 */     par1PlayerUsageSnooper.addData("uses_auth", Boolean.valueOf(this.onlineMode));
/*  856:1085 */     par1PlayerUsageSnooper.addData("gui_state", getGuiEnabled() ? "enabled" : "disabled");
/*  857:1086 */     par1PlayerUsageSnooper.addData("run_time", Long.valueOf((getSystemTimeMillis() - par1PlayerUsageSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L));
/*  858:1087 */     par1PlayerUsageSnooper.addData("avg_tick_ms", Integer.valueOf((int)(MathHelper.average(this.tickTimeArray) * 1.0E-006D)));
/*  859:1088 */     int var2 = 0;
/*  860:1090 */     for (int var3 = 0; var3 < this.worldServers.length; var3++) {
/*  861:1092 */       if (this.worldServers[var3] != null)
/*  862:     */       {
/*  863:1094 */         WorldServer var4 = this.worldServers[var3];
/*  864:1095 */         WorldInfo var5 = var4.getWorldInfo();
/*  865:1096 */         par1PlayerUsageSnooper.addData("world[" + var2 + "][dimension]", Integer.valueOf(var4.provider.dimensionId));
/*  866:1097 */         par1PlayerUsageSnooper.addData("world[" + var2 + "][mode]", var5.getGameType());
/*  867:1098 */         par1PlayerUsageSnooper.addData("world[" + var2 + "][difficulty]", var4.difficultySetting);
/*  868:1099 */         par1PlayerUsageSnooper.addData("world[" + var2 + "][hardcore]", Boolean.valueOf(var5.isHardcoreModeEnabled()));
/*  869:1100 */         par1PlayerUsageSnooper.addData("world[" + var2 + "][generator_name]", var5.getTerrainType().getWorldTypeName());
/*  870:1101 */         par1PlayerUsageSnooper.addData("world[" + var2 + "][generator_version]", Integer.valueOf(var5.getTerrainType().getGeneratorVersion()));
/*  871:1102 */         par1PlayerUsageSnooper.addData("world[" + var2 + "][height]", Integer.valueOf(this.buildLimit));
/*  872:1103 */         par1PlayerUsageSnooper.addData("world[" + var2 + "][chunks_loaded]", Integer.valueOf(var4.getChunkProvider().getLoadedChunkCount()));
/*  873:1104 */         var2++;
/*  874:     */       }
/*  875:     */     }
/*  876:1108 */     par1PlayerUsageSnooper.addData("worlds", Integer.valueOf(var2));
/*  877:     */   }
/*  878:     */   
/*  879:     */   public void addServerTypeToSnooper(PlayerUsageSnooper par1PlayerUsageSnooper)
/*  880:     */   {
/*  881:1113 */     par1PlayerUsageSnooper.addData("singleplayer", Boolean.valueOf(isSinglePlayer()));
/*  882:1114 */     par1PlayerUsageSnooper.addData("server_brand", getServerModName());
/*  883:1115 */     par1PlayerUsageSnooper.addData("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
/*  884:1116 */     par1PlayerUsageSnooper.addData("dedicated", Boolean.valueOf(isDedicatedServer()));
/*  885:     */   }
/*  886:     */   
/*  887:     */   public boolean isSnooperEnabled()
/*  888:     */   {
/*  889:1124 */     return true;
/*  890:     */   }
/*  891:     */   
/*  892:     */   public abstract boolean isDedicatedServer();
/*  893:     */   
/*  894:     */   public boolean isServerInOnlineMode()
/*  895:     */   {
/*  896:1131 */     return this.onlineMode;
/*  897:     */   }
/*  898:     */   
/*  899:     */   public void setOnlineMode(boolean par1)
/*  900:     */   {
/*  901:1136 */     this.onlineMode = par1;
/*  902:     */   }
/*  903:     */   
/*  904:     */   public boolean getCanSpawnAnimals()
/*  905:     */   {
/*  906:1141 */     return this.canSpawnAnimals;
/*  907:     */   }
/*  908:     */   
/*  909:     */   public void setCanSpawnAnimals(boolean par1)
/*  910:     */   {
/*  911:1146 */     this.canSpawnAnimals = par1;
/*  912:     */   }
/*  913:     */   
/*  914:     */   public boolean getCanSpawnNPCs()
/*  915:     */   {
/*  916:1151 */     return this.canSpawnNPCs;
/*  917:     */   }
/*  918:     */   
/*  919:     */   public void setCanSpawnNPCs(boolean par1)
/*  920:     */   {
/*  921:1156 */     this.canSpawnNPCs = par1;
/*  922:     */   }
/*  923:     */   
/*  924:     */   public boolean isPVPEnabled()
/*  925:     */   {
/*  926:1161 */     return this.pvpEnabled;
/*  927:     */   }
/*  928:     */   
/*  929:     */   public void setAllowPvp(boolean par1)
/*  930:     */   {
/*  931:1166 */     this.pvpEnabled = par1;
/*  932:     */   }
/*  933:     */   
/*  934:     */   public boolean isFlightAllowed()
/*  935:     */   {
/*  936:1171 */     return this.allowFlight;
/*  937:     */   }
/*  938:     */   
/*  939:     */   public void setAllowFlight(boolean par1)
/*  940:     */   {
/*  941:1176 */     this.allowFlight = par1;
/*  942:     */   }
/*  943:     */   
/*  944:     */   public abstract boolean isCommandBlockEnabled();
/*  945:     */   
/*  946:     */   public String getMOTD()
/*  947:     */   {
/*  948:1186 */     return this.motd;
/*  949:     */   }
/*  950:     */   
/*  951:     */   public void setMOTD(String par1Str)
/*  952:     */   {
/*  953:1191 */     this.motd = par1Str;
/*  954:     */   }
/*  955:     */   
/*  956:     */   public int getBuildLimit()
/*  957:     */   {
/*  958:1196 */     return this.buildLimit;
/*  959:     */   }
/*  960:     */   
/*  961:     */   public void setBuildLimit(int par1)
/*  962:     */   {
/*  963:1201 */     this.buildLimit = par1;
/*  964:     */   }
/*  965:     */   
/*  966:     */   public ServerConfigurationManager getConfigurationManager()
/*  967:     */   {
/*  968:1206 */     return this.serverConfigManager;
/*  969:     */   }
/*  970:     */   
/*  971:     */   public void setConfigurationManager(ServerConfigurationManager par1ServerConfigurationManager)
/*  972:     */   {
/*  973:1211 */     this.serverConfigManager = par1ServerConfigurationManager;
/*  974:     */   }
/*  975:     */   
/*  976:     */   public void setGameType(WorldSettings.GameType par1EnumGameType)
/*  977:     */   {
/*  978:1219 */     for (int var2 = 0; var2 < this.worldServers.length; var2++) {
/*  979:1221 */       getServer().worldServers[var2].getWorldInfo().setGameType(par1EnumGameType);
/*  980:     */     }
/*  981:     */   }
/*  982:     */   
/*  983:     */   public NetworkSystem func_147137_ag()
/*  984:     */   {
/*  985:1227 */     return this.field_147144_o;
/*  986:     */   }
/*  987:     */   
/*  988:     */   public boolean serverIsInRunLoop()
/*  989:     */   {
/*  990:1232 */     return this.serverIsRunning;
/*  991:     */   }
/*  992:     */   
/*  993:     */   public boolean getGuiEnabled()
/*  994:     */   {
/*  995:1237 */     return false;
/*  996:     */   }
/*  997:     */   
/*  998:     */   public abstract String shareToLAN(WorldSettings.GameType paramGameType, boolean paramBoolean);
/*  999:     */   
/* 1000:     */   public int getTickCounter()
/* 1001:     */   {
/* 1002:1247 */     return this.tickCounter;
/* 1003:     */   }
/* 1004:     */   
/* 1005:     */   public void enableProfiling()
/* 1006:     */   {
/* 1007:1252 */     this.startProfiling = true;
/* 1008:     */   }
/* 1009:     */   
/* 1010:     */   public PlayerUsageSnooper getPlayerUsageSnooper()
/* 1011:     */   {
/* 1012:1257 */     return this.usageSnooper;
/* 1013:     */   }
/* 1014:     */   
/* 1015:     */   public ChunkCoordinates getPlayerCoordinates()
/* 1016:     */   {
/* 1017:1265 */     return new ChunkCoordinates(0, 0, 0);
/* 1018:     */   }
/* 1019:     */   
/* 1020:     */   public World getEntityWorld()
/* 1021:     */   {
/* 1022:1270 */     return this.worldServers[0];
/* 1023:     */   }
/* 1024:     */   
/* 1025:     */   public int getSpawnProtectionSize()
/* 1026:     */   {
/* 1027:1278 */     return 16;
/* 1028:     */   }
/* 1029:     */   
/* 1030:     */   public boolean isBlockProtected(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
/* 1031:     */   {
/* 1032:1286 */     return false;
/* 1033:     */   }
/* 1034:     */   
/* 1035:     */   public boolean getForceGamemode()
/* 1036:     */   {
/* 1037:1291 */     return this.isGamemodeForced;
/* 1038:     */   }
/* 1039:     */   
/* 1040:     */   public Proxy getServerProxy()
/* 1041:     */   {
/* 1042:1296 */     return this.serverProxy;
/* 1043:     */   }
/* 1044:     */   
/* 1045:     */   public static long getSystemTimeMillis()
/* 1046:     */   {
/* 1047:1305 */     return System.currentTimeMillis();
/* 1048:     */   }
/* 1049:     */   
/* 1050:     */   public int func_143007_ar()
/* 1051:     */   {
/* 1052:1310 */     return this.field_143008_E;
/* 1053:     */   }
/* 1054:     */   
/* 1055:     */   public void func_143006_e(int par1)
/* 1056:     */   {
/* 1057:1315 */     this.field_143008_E = par1;
/* 1058:     */   }
/* 1059:     */   
/* 1060:     */   public IChatComponent func_145748_c_()
/* 1061:     */   {
/* 1062:1320 */     return new ChatComponentText(getCommandSenderName());
/* 1063:     */   }
/* 1064:     */   
/* 1065:     */   public boolean func_147136_ar()
/* 1066:     */   {
/* 1067:1325 */     return true;
/* 1068:     */   }
/* 1069:     */   
/* 1070:     */   public MinecraftSessionService func_147130_as()
/* 1071:     */   {
/* 1072:1330 */     return this.field_147143_S;
/* 1073:     */   }
/* 1074:     */   
/* 1075:     */   public ServerStatusResponse func_147134_at()
/* 1076:     */   {
/* 1077:1335 */     return this.field_147147_p;
/* 1078:     */   }
/* 1079:     */   
/* 1080:     */   public void func_147132_au()
/* 1081:     */   {
/* 1082:1340 */     this.field_147142_T = 0L;
/* 1083:     */   }
/* 1084:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.server.MinecraftServer
 * JD-Core Version:    0.7.0.1
 */