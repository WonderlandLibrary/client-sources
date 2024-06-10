/*    1:     */ package net.minecraft.client;
/*    2:     */ 
/*    3:     */ import com.google.common.collect.Lists;
/*    4:     */ import io.netty.util.concurrent.GenericFutureListener;
/*    5:     */ import java.awt.Toolkit;
/*    6:     */ import java.awt.image.BufferedImage;
/*    7:     */ import java.io.File;
/*    8:     */ import java.io.IOException;
/*    9:     */ import java.io.PrintStream;
/*   10:     */ import java.lang.reflect.Field;
/*   11:     */ import java.net.Proxy;
/*   12:     */ import java.net.SocketAddress;
/*   13:     */ import java.nio.ByteBuffer;
/*   14:     */ import java.text.DecimalFormat;
/*   15:     */ import java.text.SimpleDateFormat;
/*   16:     */ import java.util.ArrayList;
/*   17:     */ import java.util.Collections;
/*   18:     */ import java.util.Date;
/*   19:     */ import java.util.HashMap;
/*   20:     */ import java.util.HashSet;
/*   21:     */ import java.util.Iterator;
/*   22:     */ import java.util.List;
/*   23:     */ import java.util.concurrent.Callable;
/*   24:     */ import javax.imageio.ImageIO;
/*   25:     */ import me.connorm.Nodus.Nodus;
/*   26:     */ import me.connorm.Nodus.event.other.EventKeyPress;
/*   27:     */ import me.connorm.Nodus.event.other.EventShutdown;
/*   28:     */ import me.connorm.Nodus.font.Fonts;
/*   29:     */ import me.connorm.Nodus.ui.NodusGuiMainMenu;
/*   30:     */ import me.connorm.lib.EventManager;
/*   31:     */ import net.minecraft.block.Block;
/*   32:     */ import net.minecraft.block.material.Material;
/*   33:     */ import net.minecraft.client.audio.MusicTicker;
/*   34:     */ import net.minecraft.client.audio.MusicTicker.MusicType;
/*   35:     */ import net.minecraft.client.audio.SoundHandler;
/*   36:     */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*   37:     */ import net.minecraft.client.gui.FontRenderer;
/*   38:     */ import net.minecraft.client.gui.GuiChat;
/*   39:     */ import net.minecraft.client.gui.GuiGameOver;
/*   40:     */ import net.minecraft.client.gui.GuiIngame;
/*   41:     */ import net.minecraft.client.gui.GuiIngameMenu;
/*   42:     */ import net.minecraft.client.gui.GuiMemoryErrorScreen;
/*   43:     */ import net.minecraft.client.gui.GuiNewChat;
/*   44:     */ import net.minecraft.client.gui.GuiScreen;
/*   45:     */ import net.minecraft.client.gui.GuiSleepMP;
/*   46:     */ import net.minecraft.client.gui.GuiWinGame;
/*   47:     */ import net.minecraft.client.gui.MapItemRenderer;
/*   48:     */ import net.minecraft.client.gui.ScaledResolution;
/*   49:     */ import net.minecraft.client.gui.achievement.GuiAchievement;
/*   50:     */ import net.minecraft.client.gui.inventory.GuiInventory;
/*   51:     */ import net.minecraft.client.multiplayer.GuiConnecting;
/*   52:     */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*   53:     */ import net.minecraft.client.multiplayer.ServerData;
/*   54:     */ import net.minecraft.client.multiplayer.WorldClient;
/*   55:     */ import net.minecraft.client.network.NetHandlerLoginClient;
/*   56:     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*   57:     */ import net.minecraft.client.particle.EffectRenderer;
/*   58:     */ import net.minecraft.client.renderer.EntityRenderer;
/*   59:     */ import net.minecraft.client.renderer.GLAllocation;
/*   60:     */ import net.minecraft.client.renderer.ItemRenderer;
/*   61:     */ import net.minecraft.client.renderer.OpenGlHelper;
/*   62:     */ import net.minecraft.client.renderer.RenderGlobal;
/*   63:     */ import net.minecraft.client.renderer.Tessellator;
/*   64:     */ import net.minecraft.client.renderer.WorldRenderer;
/*   65:     */ import net.minecraft.client.renderer.entity.RenderManager;
/*   66:     */ import net.minecraft.client.renderer.texture.TextureManager;
/*   67:     */ import net.minecraft.client.renderer.texture.TextureMap;
/*   68:     */ import net.minecraft.client.resources.DefaultResourcePack;
/*   69:     */ import net.minecraft.client.resources.FoliageColorReloadListener;
/*   70:     */ import net.minecraft.client.resources.GrassColorReloadListener;
/*   71:     */ import net.minecraft.client.resources.I18n;
/*   72:     */ import net.minecraft.client.resources.IReloadableResourceManager;
/*   73:     */ import net.minecraft.client.resources.IResourceManager;
/*   74:     */ import net.minecraft.client.resources.Language;
/*   75:     */ import net.minecraft.client.resources.LanguageManager;
/*   76:     */ import net.minecraft.client.resources.ResourcePackRepository;
/*   77:     */ import net.minecraft.client.resources.ResourcePackRepository.Entry;
/*   78:     */ import net.minecraft.client.resources.SimpleReloadableResourceManager;
/*   79:     */ import net.minecraft.client.resources.data.AnimationMetadataSection;
/*   80:     */ import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
/*   81:     */ import net.minecraft.client.resources.data.FontMetadataSection;
/*   82:     */ import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
/*   83:     */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*   84:     */ import net.minecraft.client.resources.data.LanguageMetadataSection;
/*   85:     */ import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
/*   86:     */ import net.minecraft.client.resources.data.PackMetadataSection;
/*   87:     */ import net.minecraft.client.resources.data.PackMetadataSectionSerializer;
/*   88:     */ import net.minecraft.client.resources.data.TextureMetadataSection;
/*   89:     */ import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
/*   90:     */ import net.minecraft.client.settings.GameSettings;
/*   91:     */ import net.minecraft.client.settings.GameSettings.Options;
/*   92:     */ import net.minecraft.client.settings.KeyBinding;
/*   93:     */ import net.minecraft.client.shader.Framebuffer;
/*   94:     */ import net.minecraft.crash.CrashReport;
/*   95:     */ import net.minecraft.crash.CrashReportCategory;
/*   96:     */ import net.minecraft.entity.Entity;
/*   97:     */ import net.minecraft.entity.EntityLeashKnot;
/*   98:     */ import net.minecraft.entity.EntityList;
/*   99:     */ import net.minecraft.entity.EntityLivingBase;
/*  100:     */ import net.minecraft.entity.boss.BossStatus;
/*  101:     */ import net.minecraft.entity.item.EntityBoat;
/*  102:     */ import net.minecraft.entity.item.EntityItemFrame;
/*  103:     */ import net.minecraft.entity.item.EntityMinecart;
/*  104:     */ import net.minecraft.entity.item.EntityPainting;
/*  105:     */ import net.minecraft.entity.player.EntityPlayer.EnumChatVisibility;
/*  106:     */ import net.minecraft.entity.player.InventoryPlayer;
/*  107:     */ import net.minecraft.entity.player.PlayerCapabilities;
/*  108:     */ import net.minecraft.init.Bootstrap;
/*  109:     */ import net.minecraft.init.Items;
/*  110:     */ import net.minecraft.inventory.Container;
/*  111:     */ import net.minecraft.item.Item;
/*  112:     */ import net.minecraft.item.ItemBlock;
/*  113:     */ import net.minecraft.item.ItemStack;
/*  114:     */ import net.minecraft.network.EnumConnectionState;
/*  115:     */ import net.minecraft.network.NetworkManager;
/*  116:     */ import net.minecraft.network.NetworkSystem;
/*  117:     */ import net.minecraft.network.handshake.client.C00Handshake;
/*  118:     */ import net.minecraft.network.login.client.C00PacketLoginStart;
/*  119:     */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*  120:     */ import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
/*  121:     */ import net.minecraft.profiler.IPlayerUsage;
/*  122:     */ import net.minecraft.profiler.PlayerUsageSnooper;
/*  123:     */ import net.minecraft.profiler.Profiler;
/*  124:     */ import net.minecraft.profiler.Profiler.Result;
/*  125:     */ import net.minecraft.server.MinecraftServer;
/*  126:     */ import net.minecraft.server.integrated.IntegratedServer;
/*  127:     */ import net.minecraft.stats.Achievement;
/*  128:     */ import net.minecraft.stats.AchievementList;
/*  129:     */ import net.minecraft.stats.IStatStringFormat;
/*  130:     */ import net.minecraft.stats.StatFileWriter;
/*  131:     */ import net.minecraft.util.AABBPool;
/*  132:     */ import net.minecraft.util.AxisAlignedBB;
/*  133:     */ import net.minecraft.util.MathHelper;
/*  134:     */ import net.minecraft.util.MinecraftError;
/*  135:     */ import net.minecraft.util.MouseHelper;
/*  136:     */ import net.minecraft.util.MovementInputFromOptions;
/*  137:     */ import net.minecraft.util.MovingObjectPosition;
/*  138:     */ import net.minecraft.util.MovingObjectPosition.MovingObjectType;
/*  139:     */ import net.minecraft.util.ReportedException;
/*  140:     */ import net.minecraft.util.ResourceLocation;
/*  141:     */ import net.minecraft.util.ScreenShotHelper;
/*  142:     */ import net.minecraft.util.Session;
/*  143:     */ import net.minecraft.util.Timer;
/*  144:     */ import net.minecraft.util.Util;
/*  145:     */ import net.minecraft.util.Util.EnumOS;
/*  146:     */ import net.minecraft.util.Vec3Pool;
/*  147:     */ import net.minecraft.world.EnumDifficulty;
/*  148:     */ import net.minecraft.world.World;
/*  149:     */ import net.minecraft.world.WorldProviderEnd;
/*  150:     */ import net.minecraft.world.WorldProviderHell;
/*  151:     */ import net.minecraft.world.WorldSettings;
/*  152:     */ import net.minecraft.world.chunk.storage.AnvilSaveConverter;
/*  153:     */ import net.minecraft.world.storage.ISaveFormat;
/*  154:     */ import net.minecraft.world.storage.ISaveHandler;
/*  155:     */ import net.minecraft.world.storage.WorldInfo;
/*  156:     */ import org.apache.logging.log4j.LogManager;
/*  157:     */ import org.apache.logging.log4j.Logger;
/*  158:     */ import org.lwjgl.LWJGLException;
/*  159:     */ import org.lwjgl.Sys;
/*  160:     */ import org.lwjgl.input.Keyboard;
/*  161:     */ import org.lwjgl.input.Mouse;
/*  162:     */ import org.lwjgl.opengl.ContextCapabilities;
/*  163:     */ import org.lwjgl.opengl.Display;
/*  164:     */ import org.lwjgl.opengl.DisplayMode;
/*  165:     */ import org.lwjgl.opengl.GL11;
/*  166:     */ import org.lwjgl.opengl.GLContext;
/*  167:     */ import org.lwjgl.opengl.PixelFormat;
/*  168:     */ import org.lwjgl.util.glu.GLU;
/*  169:     */ 
/*  170:     */ public class Minecraft
/*  171:     */   implements IPlayerUsage
/*  172:     */ {
/*  173: 159 */   private static final Logger logger = ;
/*  174: 160 */   private static final ResourceLocation locationMojangPng = new ResourceLocation("textures/gui/title/mojang.png");
/*  175: 161 */   public static final boolean isRunningOnMac = Util.getOSType() == Util.EnumOS.MACOS;
/*  176: 164 */   public static byte[] memoryReserve = new byte[10485760];
/*  177: 165 */   private static final List macDisplayModes = Lists.newArrayList(new DisplayMode[] { new DisplayMode(2560, 1600), new DisplayMode(2880, 1800) });
/*  178:     */   private final File fileResourcepacks;
/*  179:     */   private ServerData currentServerData;
/*  180:     */   public TextureManager renderEngine;
/*  181:     */   private static Minecraft theMinecraft;
/*  182:     */   public PlayerControllerMP playerController;
/*  183:     */   private boolean fullscreen;
/*  184:     */   private boolean hasCrashed;
/*  185:     */   private CrashReport crashReporter;
/*  186:     */   public int displayWidth;
/*  187:     */   public int displayHeight;
/*  188: 184 */   public Timer timer = new Timer(20.0F);
/*  189: 187 */   private PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("client", this, MinecraftServer.getSystemTimeMillis());
/*  190:     */   public WorldClient theWorld;
/*  191:     */   public RenderGlobal renderGlobal;
/*  192:     */   public EntityClientPlayerMP thePlayer;
/*  193:     */   public EntityLivingBase renderViewEntity;
/*  194:     */   public Entity pointedEntity;
/*  195:     */   public EffectRenderer effectRenderer;
/*  196:     */   public Session session;
/*  197:     */   private boolean isGamePaused;
/*  198:     */   public FontRenderer fontRenderer;
/*  199:     */   public FontRenderer standardGalacticFontRenderer;
/*  200:     */   public GuiScreen currentScreen;
/*  201:     */   public LoadingScreenRenderer loadingScreen;
/*  202:     */   public EntityRenderer entityRenderer;
/*  203:     */   private int leftClickCounter;
/*  204:     */   private int tempDisplayWidth;
/*  205:     */   private int tempDisplayHeight;
/*  206:     */   private IntegratedServer theIntegratedServer;
/*  207:     */   public GuiAchievement guiAchievement;
/*  208:     */   public GuiIngame ingameGUI;
/*  209:     */   public boolean skipRenderWorld;
/*  210:     */   public MovingObjectPosition objectMouseOver;
/*  211:     */   public GameSettings gameSettings;
/*  212:     */   public MouseHelper mouseHelper;
/*  213:     */   public final File mcDataDir;
/*  214:     */   private final File fileAssets;
/*  215:     */   private final String launchedVersion;
/*  216:     */   private final Proxy proxy;
/*  217:     */   private ISaveFormat saveLoader;
/*  218:     */   private static int debugFPS;
/*  219:     */   public int rightClickDelayTimer;
/*  220:     */   private boolean refreshTexturePacksScheduled;
/*  221:     */   private String serverName;
/*  222:     */   private int serverPort;
/*  223:     */   boolean isTakingScreenshot;
/*  224:     */   public boolean inGameHasFocus;
/*  225: 273 */   long systemTime = getSystemTime();
/*  226:     */   private int joinPlayerCounter;
/*  227:     */   private final boolean jvm64bit;
/*  228:     */   private final boolean isDemo;
/*  229:     */   private NetworkManager myNetworkManager;
/*  230:     */   private boolean integratedServerIsRunning;
/*  231: 283 */   public final Profiler mcProfiler = new Profiler();
/*  232: 284 */   private long field_83002_am = -1L;
/*  233:     */   private IReloadableResourceManager mcResourceManager;
/*  234: 286 */   private final IMetadataSerializer metadataSerializer_ = new IMetadataSerializer();
/*  235: 287 */   private List defaultResourcePacks = Lists.newArrayList();
/*  236:     */   private DefaultResourcePack mcDefaultResourcePack;
/*  237:     */   private ResourcePackRepository mcResourcePackRepository;
/*  238:     */   private LanguageManager mcLanguageManager;
/*  239:     */   private Framebuffer mcFramebuffer;
/*  240:     */   private TextureMap textureMapBlocks;
/*  241:     */   private SoundHandler mcSoundHandler;
/*  242:     */   private MusicTicker mcMusicTicker;
/*  243: 299 */   volatile boolean running = true;
/*  244: 302 */   public String debug = "";
/*  245: 305 */   long debugUpdateTime = getSystemTime();
/*  246:     */   int fpsCounter;
/*  247: 309 */   long prevFrameTime = -1L;
/*  248: 312 */   private String debugProfilerName = "root";
/*  249:     */   private static final String __OBFID = "CL_00000631";
/*  250:     */   
/*  251:     */   public Minecraft(Session par1Session, int par2, int par3, boolean par4, boolean par5, File par6File, File par7File, File par8File, Proxy par9Proxy, String par10Str)
/*  252:     */   {
/*  253: 317 */     theMinecraft = this;
/*  254: 318 */     this.mcDataDir = par6File;
/*  255: 319 */     this.fileAssets = par7File;
/*  256: 320 */     this.fileResourcepacks = par8File;
/*  257: 321 */     this.launchedVersion = par10Str;
/*  258: 322 */     this.mcDefaultResourcePack = new DefaultResourcePack(this.fileAssets);
/*  259: 323 */     addDefaultResourcePack();
/*  260: 324 */     this.proxy = (par9Proxy == null ? Proxy.NO_PROXY : par9Proxy);
/*  261: 325 */     startTimerHackThread();
/*  262: 326 */     this.session = par1Session;
/*  263: 327 */     logger.info("Setting user: " + par1Session.getUsername());
/*  264: 328 */     logger.info("(Session ID is " + par1Session.getSessionID() + ")");
/*  265: 329 */     this.isDemo = par5;
/*  266: 330 */     this.displayWidth = par2;
/*  267: 331 */     this.displayHeight = par3;
/*  268: 332 */     this.tempDisplayWidth = par2;
/*  269: 333 */     this.tempDisplayHeight = par3;
/*  270: 334 */     this.fullscreen = par4;
/*  271: 335 */     this.jvm64bit = isJvm64bit();
/*  272: 336 */     ImageIO.setUseCache(false);
/*  273: 337 */     Bootstrap.func_151354_b();
/*  274:     */   }
/*  275:     */   
/*  276:     */   private static boolean isJvm64bit()
/*  277:     */   {
/*  278: 342 */     String[] var0 = { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
/*  279: 343 */     String[] var1 = var0;
/*  280: 344 */     int var2 = var0.length;
/*  281: 346 */     for (int var3 = 0; var3 < var2; var3++)
/*  282:     */     {
/*  283: 348 */       String var4 = var1[var3];
/*  284: 349 */       String var5 = System.getProperty(var4);
/*  285: 351 */       if ((var5 != null) && (var5.contains("64"))) {
/*  286: 353 */         return true;
/*  287:     */       }
/*  288:     */     }
/*  289: 357 */     return false;
/*  290:     */   }
/*  291:     */   
/*  292:     */   public static void func_147105_a(String p_147105_0_)
/*  293:     */   {
/*  294:     */     try
/*  295:     */     {
/*  296: 364 */       Toolkit var1 = Toolkit.getDefaultToolkit();
/*  297: 365 */       Class var2 = var1.getClass();
/*  298: 367 */       if (var2.getName().equals("sun.awt.X11.XToolkit"))
/*  299:     */       {
/*  300: 369 */         Field var3 = var2.getDeclaredField("awtAppClassName");
/*  301: 370 */         var3.setAccessible(true);
/*  302: 371 */         var3.set(var1, p_147105_0_);
/*  303:     */       }
/*  304:     */     }
/*  305:     */     catch (Exception localException) {}
/*  306:     */   }
/*  307:     */   
/*  308:     */   public Framebuffer getFramebuffer()
/*  309:     */   {
/*  310: 382 */     return this.mcFramebuffer;
/*  311:     */   }
/*  312:     */   
/*  313:     */   private void startTimerHackThread()
/*  314:     */   {
/*  315: 387 */     Thread var1 = new Thread("Timer hack thread")
/*  316:     */     {
/*  317:     */       private static final String __OBFID = "CL_00000632";
/*  318:     */       
/*  319:     */       public void run()
/*  320:     */       {
/*  321: 392 */         while (Minecraft.this.running) {
/*  322:     */           try
/*  323:     */           {
/*  324: 396 */             Thread.sleep(2147483647L);
/*  325:     */           }
/*  326:     */           catch (InterruptedException localInterruptedException) {}
/*  327:     */         }
/*  328:     */       }
/*  329: 404 */     };
/*  330: 405 */     var1.setDaemon(true);
/*  331: 406 */     var1.start();
/*  332:     */   }
/*  333:     */   
/*  334:     */   public void crashed(CrashReport par1CrashReport)
/*  335:     */   {
/*  336: 411 */     this.hasCrashed = true;
/*  337: 412 */     this.crashReporter = par1CrashReport;
/*  338:     */   }
/*  339:     */   
/*  340:     */   public void displayCrashReport(CrashReport par1CrashReport)
/*  341:     */   {
/*  342: 420 */     File var2 = new File(getMinecraft().mcDataDir, "crash-reports");
/*  343: 421 */     File var3 = new File(var2, "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt");
/*  344: 422 */     System.out.println(par1CrashReport.getCompleteReport());
/*  345: 424 */     if (par1CrashReport.getFile() != null)
/*  346:     */     {
/*  347: 426 */       System.out.println("#@!@# Game crashed! Crash report saved to: #@!@# " + par1CrashReport.getFile());
/*  348: 427 */       System.exit(-1);
/*  349:     */     }
/*  350: 429 */     else if (par1CrashReport.saveToFile(var3))
/*  351:     */     {
/*  352: 431 */       System.out.println("#@!@# Game crashed! Crash report saved to: #@!@# " + var3.getAbsolutePath());
/*  353: 432 */       System.exit(-1);
/*  354:     */     }
/*  355:     */     else
/*  356:     */     {
/*  357: 436 */       System.out.println("#@?@# Game crashed! Crash report could not be saved. #@?@#");
/*  358: 437 */       System.exit(-2);
/*  359:     */     }
/*  360:     */   }
/*  361:     */   
/*  362:     */   public void setServer(String par1Str, int par2)
/*  363:     */   {
/*  364: 443 */     this.serverName = par1Str;
/*  365: 444 */     this.serverPort = par2;
/*  366:     */   }
/*  367:     */   
/*  368:     */   private void startGame()
/*  369:     */     throws LWJGLException
/*  370:     */   {
/*  371: 452 */     this.gameSettings = new GameSettings(this, this.mcDataDir);
/*  372: 454 */     if ((this.gameSettings.overrideHeight > 0) && (this.gameSettings.overrideWidth > 0))
/*  373:     */     {
/*  374: 456 */       this.displayWidth = this.gameSettings.overrideWidth;
/*  375: 457 */       this.displayHeight = this.gameSettings.overrideHeight;
/*  376:     */     }
/*  377: 460 */     if (this.fullscreen)
/*  378:     */     {
/*  379: 462 */       Display.setFullscreen(true);
/*  380: 463 */       this.displayWidth = Display.getDisplayMode().getWidth();
/*  381: 464 */       this.displayHeight = Display.getDisplayMode().getHeight();
/*  382: 466 */       if (this.displayWidth <= 0) {
/*  383: 468 */         this.displayWidth = 1;
/*  384:     */       }
/*  385: 471 */       if (this.displayHeight <= 0) {
/*  386: 473 */         this.displayHeight = 1;
/*  387:     */       }
/*  388:     */     }
/*  389:     */     else
/*  390:     */     {
/*  391: 478 */       Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
/*  392:     */     }
/*  393: 481 */     Display.setResizable(true);
/*  394:     */     
/*  395: 483 */     Display.setTitle("Nodus 2.0");
/*  396: 484 */     logger.info("LWJGL Version: " + Sys.getVersion());
/*  397: 485 */     Util.EnumOS var1 = Util.getOSType();
/*  398: 487 */     if (var1 != Util.EnumOS.MACOS)
/*  399:     */     {
/*  400:     */       try
/*  401:     */       {
/*  402: 491 */         Display.setIcon(new ByteBuffer[] { readImage(new File(this.fileAssets, "/icons/icon_16x16.png")), readImage(new File(this.fileAssets, "/icons/icon_32x32.png")) });
/*  403:     */       }
/*  404:     */       catch (IOException var6)
/*  405:     */       {
/*  406: 495 */         logger.error("Couldn't set icon", var6);
/*  407:     */       }
/*  408: 498 */       if (var1 != Util.EnumOS.WINDOWS) {
/*  409: 500 */         func_147105_a("Minecraft");
/*  410:     */       }
/*  411:     */     }
/*  412:     */     try
/*  413:     */     {
/*  414: 506 */       Display.create(new PixelFormat().withDepthBits(24));
/*  415:     */     }
/*  416:     */     catch (LWJGLException var5)
/*  417:     */     {
/*  418: 510 */       logger.error("Couldn't set pixel format", var5);
/*  419:     */       try
/*  420:     */       {
/*  421: 514 */         Thread.sleep(1000L);
/*  422:     */       }
/*  423:     */       catch (InterruptedException localInterruptedException) {}
/*  424: 521 */       if (this.fullscreen) {
/*  425: 523 */         updateDisplayMode();
/*  426:     */       }
/*  427: 526 */       Display.create();
/*  428:     */     }
/*  429: 529 */     OpenGlHelper.initializeTextures();
/*  430: 530 */     this.mcFramebuffer = new Framebuffer(this.displayWidth, this.displayHeight, true);
/*  431: 531 */     this.mcFramebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
/*  432: 532 */     this.guiAchievement = new GuiAchievement(this);
/*  433: 533 */     this.metadataSerializer_.registerMetadataSectionType(new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
/*  434: 534 */     this.metadataSerializer_.registerMetadataSectionType(new FontMetadataSectionSerializer(), FontMetadataSection.class);
/*  435: 535 */     this.metadataSerializer_.registerMetadataSectionType(new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
/*  436: 536 */     this.metadataSerializer_.registerMetadataSectionType(new PackMetadataSectionSerializer(), PackMetadataSection.class);
/*  437: 537 */     this.metadataSerializer_.registerMetadataSectionType(new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
/*  438: 538 */     this.saveLoader = new AnvilSaveConverter(new File(this.mcDataDir, "saves"));
/*  439: 539 */     this.mcResourcePackRepository = new ResourcePackRepository(this.fileResourcepacks, new File(this.mcDataDir, "server-resource-packs"), this.mcDefaultResourcePack, this.metadataSerializer_, this.gameSettings);
/*  440: 540 */     this.mcResourceManager = new SimpleReloadableResourceManager(this.metadataSerializer_);
/*  441: 541 */     this.mcLanguageManager = new LanguageManager(this.metadataSerializer_, this.gameSettings.language);
/*  442: 542 */     this.mcResourceManager.registerReloadListener(this.mcLanguageManager);
/*  443: 543 */     refreshResources();
/*  444: 544 */     this.renderEngine = new TextureManager(this.mcResourceManager);
/*  445: 545 */     this.mcResourceManager.registerReloadListener(this.renderEngine);
/*  446: 546 */     this.mcSoundHandler = new SoundHandler(this.mcResourceManager, this.gameSettings);
/*  447: 547 */     this.mcMusicTicker = new MusicTicker(this);
/*  448: 548 */     this.mcResourceManager.registerReloadListener(this.mcSoundHandler);
/*  449: 549 */     loadScreen();
/*  450: 550 */     this.fontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii.png"), this.renderEngine, false);
/*  451:     */     
/*  452:     */ 
/*  453: 553 */     Fonts.loadFonts();
/*  454: 555 */     if (this.gameSettings.language != null)
/*  455:     */     {
/*  456: 557 */       this.fontRenderer.setUnicodeFlag((this.mcLanguageManager.isCurrentLocaleUnicode()) || (this.gameSettings.forceUnicodeFont));
/*  457: 558 */       this.fontRenderer.setBidiFlag(this.mcLanguageManager.isCurrentLanguageBidirectional());
/*  458:     */     }
/*  459: 561 */     this.standardGalacticFontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii_sga.png"), this.renderEngine, false);
/*  460: 562 */     this.mcResourceManager.registerReloadListener(this.fontRenderer);
/*  461: 563 */     this.mcResourceManager.registerReloadListener(this.standardGalacticFontRenderer);
/*  462: 564 */     this.mcResourceManager.registerReloadListener(new GrassColorReloadListener());
/*  463: 565 */     this.mcResourceManager.registerReloadListener(new FoliageColorReloadListener());
/*  464: 566 */     RenderManager.instance.itemRenderer = new ItemRenderer(this);
/*  465: 567 */     this.entityRenderer = new EntityRenderer(this, this.mcResourceManager);
/*  466: 568 */     this.mcResourceManager.registerReloadListener(this.entityRenderer);
/*  467: 569 */     AchievementList.openInventory.setStatStringFormatter(new IStatStringFormat()
/*  468:     */     {
/*  469:     */       private static final String __OBFID = "CL_00000639";
/*  470:     */       
/*  471:     */       public String formatString(String par1Str)
/*  472:     */       {
/*  473:     */         try
/*  474:     */         {
/*  475: 576 */           return String.format(par1Str, new Object[] { GameSettings.getKeyDisplayString(Minecraft.this.gameSettings.keyBindInventory.getKeyCode()) });
/*  476:     */         }
/*  477:     */         catch (Exception var3)
/*  478:     */         {
/*  479: 580 */           return "Error: " + var3.getLocalizedMessage();
/*  480:     */         }
/*  481:     */       }
/*  482: 583 */     });
/*  483: 584 */     this.mouseHelper = new MouseHelper();
/*  484: 585 */     checkGLError("Pre startup");
/*  485: 586 */     GL11.glEnable(3553);
/*  486: 587 */     GL11.glShadeModel(7425);
/*  487: 588 */     GL11.glClearDepth(1.0D);
/*  488: 589 */     GL11.glEnable(2929);
/*  489: 590 */     GL11.glDepthFunc(515);
/*  490: 591 */     GL11.glEnable(3008);
/*  491: 592 */     GL11.glAlphaFunc(516, 0.1F);
/*  492: 593 */     GL11.glCullFace(1029);
/*  493: 594 */     GL11.glMatrixMode(5889);
/*  494: 595 */     GL11.glLoadIdentity();
/*  495: 596 */     GL11.glMatrixMode(5888);
/*  496: 597 */     checkGLError("Startup");
/*  497: 598 */     this.renderGlobal = new RenderGlobal(this);
/*  498: 599 */     this.textureMapBlocks = new TextureMap(0, "textures/blocks");
/*  499: 600 */     this.textureMapBlocks.func_147632_b(this.gameSettings.anisotropicFiltering);
/*  500: 601 */     this.textureMapBlocks.func_147633_a(this.gameSettings.mipmapLevels);
/*  501: 602 */     this.renderEngine.loadTextureMap(TextureMap.locationBlocksTexture, this.textureMapBlocks);
/*  502: 603 */     this.renderEngine.loadTextureMap(TextureMap.locationItemsTexture, new TextureMap(1, "textures/items"));
/*  503: 604 */     GL11.glViewport(0, 0, this.displayWidth, this.displayHeight);
/*  504: 605 */     this.effectRenderer = new EffectRenderer(this.theWorld, this.renderEngine);
/*  505: 606 */     checkGLError("Post startup");
/*  506: 607 */     this.ingameGUI = new GuiIngame(this);
/*  507: 609 */     if (this.serverName != null) {
/*  508: 611 */       displayGuiScreen(new GuiConnecting(new NodusGuiMainMenu(), this, this.serverName, this.serverPort));
/*  509:     */     } else {
/*  510: 615 */       displayGuiScreen(new NodusGuiMainMenu());
/*  511:     */     }
/*  512: 618 */     this.loadingScreen = new LoadingScreenRenderer(this);
/*  513: 620 */     if ((this.gameSettings.fullScreen) && (!this.fullscreen)) {
/*  514: 622 */       toggleFullscreen();
/*  515:     */     }
/*  516: 626 */     Nodus.theNodus.startNodus(this);
/*  517:     */     
/*  518: 628 */     Display.setVSyncEnabled(this.gameSettings.enableVsync);
/*  519:     */   }
/*  520:     */   
/*  521:     */   public void refreshResources()
/*  522:     */   {
/*  523: 633 */     ArrayList var1 = Lists.newArrayList(this.defaultResourcePacks);
/*  524: 634 */     Iterator var2 = this.mcResourcePackRepository.getRepositoryEntries().iterator();
/*  525: 636 */     while (var2.hasNext())
/*  526:     */     {
/*  527: 638 */       ResourcePackRepository.Entry var3 = (ResourcePackRepository.Entry)var2.next();
/*  528: 639 */       var1.add(var3.getResourcePack());
/*  529:     */     }
/*  530: 642 */     if (this.mcResourcePackRepository.func_148530_e() != null) {
/*  531: 644 */       var1.add(this.mcResourcePackRepository.func_148530_e());
/*  532:     */     }
/*  533: 647 */     this.mcLanguageManager.parseLanguageMetadata(var1);
/*  534: 648 */     this.mcResourceManager.reloadResources(var1);
/*  535: 650 */     if (this.renderGlobal != null) {
/*  536: 652 */       this.renderGlobal.loadRenderers();
/*  537:     */     }
/*  538:     */   }
/*  539:     */   
/*  540:     */   private void addDefaultResourcePack()
/*  541:     */   {
/*  542: 658 */     this.defaultResourcePacks.add(this.mcDefaultResourcePack);
/*  543:     */   }
/*  544:     */   
/*  545:     */   private ByteBuffer readImage(File par1File)
/*  546:     */     throws IOException
/*  547:     */   {
/*  548: 663 */     BufferedImage var2 = ImageIO.read(par1File);
/*  549: 664 */     int[] var3 = var2.getRGB(0, 0, var2.getWidth(), var2.getHeight(), null, 0, var2.getWidth());
/*  550: 665 */     ByteBuffer var4 = ByteBuffer.allocate(4 * var3.length);
/*  551: 666 */     int[] var5 = var3;
/*  552: 667 */     int var6 = var3.length;
/*  553: 669 */     for (int var7 = 0; var7 < var6; var7++)
/*  554:     */     {
/*  555: 671 */       int var8 = var5[var7];
/*  556: 672 */       var4.putInt(var8 << 8 | var8 >> 24 & 0xFF);
/*  557:     */     }
/*  558: 675 */     var4.flip();
/*  559: 676 */     return var4;
/*  560:     */   }
/*  561:     */   
/*  562:     */   private void updateDisplayMode()
/*  563:     */     throws LWJGLException
/*  564:     */   {
/*  565: 681 */     HashSet var1 = new HashSet();
/*  566: 682 */     Collections.addAll(var1, Display.getAvailableDisplayModes());
/*  567: 683 */     DisplayMode var2 = Display.getDesktopDisplayMode();
/*  568: 685 */     if ((!var1.contains(var2)) && (Util.getOSType() == Util.EnumOS.MACOS))
/*  569:     */     {
/*  570: 687 */       Iterator var3 = macDisplayModes.iterator();
/*  571: 689 */       while (var3.hasNext())
/*  572:     */       {
/*  573: 691 */         DisplayMode var4 = (DisplayMode)var3.next();
/*  574: 692 */         boolean var5 = true;
/*  575: 693 */         Iterator var6 = var1.iterator();
/*  576: 696 */         while (var6.hasNext())
/*  577:     */         {
/*  578: 698 */           DisplayMode var7 = (DisplayMode)var6.next();
/*  579: 700 */           if ((var7.getBitsPerPixel() == 32) && (var7.getWidth() == var4.getWidth()) && (var7.getHeight() == var4.getHeight()))
/*  580:     */           {
/*  581: 702 */             var5 = false;
/*  582: 703 */             break;
/*  583:     */           }
/*  584:     */         }
/*  585: 707 */         if (!var5)
/*  586:     */         {
/*  587: 709 */           var6 = var1.iterator();
/*  588: 711 */           while (var6.hasNext())
/*  589:     */           {
/*  590: 713 */             DisplayMode var7 = (DisplayMode)var6.next();
/*  591: 715 */             if ((var7.getBitsPerPixel() == 32) && (var7.getWidth() == var4.getWidth() / 2) && (var7.getHeight() == var4.getHeight() / 2))
/*  592:     */             {
/*  593: 717 */               var2 = var7;
/*  594: 718 */               break;
/*  595:     */             }
/*  596:     */           }
/*  597:     */         }
/*  598:     */       }
/*  599:     */     }
/*  600: 725 */     Display.setDisplayMode(var2);
/*  601: 726 */     this.displayWidth = var2.getWidth();
/*  602: 727 */     this.displayHeight = var2.getHeight();
/*  603:     */   }
/*  604:     */   
/*  605:     */   private void loadScreen()
/*  606:     */     throws LWJGLException
/*  607:     */   {
/*  608: 735 */     GL11.glEnable(3553);
/*  609: 736 */     this.renderEngine.bindTexture(locationMojangPng);
/*  610: 737 */     ScaledResolution var1 = new ScaledResolution(this.gameSettings, this.displayWidth, this.displayHeight);
/*  611: 738 */     int var2 = var1.getScaleFactor();
/*  612: 739 */     Framebuffer var3 = new Framebuffer(var1.getScaledWidth() * var2, var1.getScaledHeight() * var2, true);
/*  613: 740 */     var3.bindFramebuffer(false);
/*  614: 741 */     GL11.glMatrixMode(5889);
/*  615: 742 */     GL11.glLoadIdentity();
/*  616: 743 */     GL11.glOrtho(0.0D, var1.getScaledWidth(), var1.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
/*  617: 744 */     GL11.glMatrixMode(5888);
/*  618: 745 */     GL11.glLoadIdentity();
/*  619: 746 */     GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
/*  620: 747 */     GL11.glDisable(2896);
/*  621: 748 */     GL11.glDisable(2912);
/*  622: 749 */     GL11.glDisable(2929);
/*  623: 750 */     GL11.glEnable(3553);
/*  624: 751 */     this.renderEngine.bindTexture(locationMojangPng);
/*  625: 752 */     Tessellator var4 = Tessellator.instance;
/*  626: 753 */     var4.startDrawingQuads();
/*  627: 754 */     var4.setColorOpaque_I(16777215);
/*  628: 755 */     var4.addVertexWithUV(0.0D, this.displayHeight, 0.0D, 0.0D, 0.0D);
/*  629: 756 */     var4.addVertexWithUV(this.displayWidth, this.displayHeight, 0.0D, 0.0D, 0.0D);
/*  630: 757 */     var4.addVertexWithUV(this.displayWidth, 0.0D, 0.0D, 0.0D, 0.0D);
/*  631: 758 */     var4.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*  632: 759 */     var4.draw();
/*  633: 760 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  634: 761 */     var4.setColorOpaque_I(16777215);
/*  635: 762 */     short var5 = 256;
/*  636: 763 */     short var6 = 256;
/*  637: 764 */     scaledTessellator((var1.getScaledWidth() - var5) / 2, (var1.getScaledHeight() - var6) / 2, 0, 0, var5, var6);
/*  638: 765 */     GL11.glDisable(2896);
/*  639: 766 */     GL11.glDisable(2912);
/*  640: 767 */     var3.unbindFramebuffer();
/*  641: 768 */     var3.framebufferRender(var1.getScaledWidth() * var2, var1.getScaledHeight() * var2);
/*  642: 769 */     GL11.glEnable(3008);
/*  643: 770 */     GL11.glAlphaFunc(516, 0.1F);
/*  644: 771 */     GL11.glFlush();
/*  645: 772 */     func_147120_f();
/*  646:     */   }
/*  647:     */   
/*  648:     */   public void scaledTessellator(int par1, int par2, int par3, int par4, int par5, int par6)
/*  649:     */   {
/*  650: 780 */     float var7 = 0.0039063F;
/*  651: 781 */     float var8 = 0.0039063F;
/*  652: 782 */     Tessellator var9 = Tessellator.instance;
/*  653: 783 */     var9.startDrawingQuads();
/*  654: 784 */     var9.addVertexWithUV(par1 + 0, par2 + par6, 0.0D, (par3 + 0) * var7, (par4 + par6) * var8);
/*  655: 785 */     var9.addVertexWithUV(par1 + par5, par2 + par6, 0.0D, (par3 + par5) * var7, (par4 + par6) * var8);
/*  656: 786 */     var9.addVertexWithUV(par1 + par5, par2 + 0, 0.0D, (par3 + par5) * var7, (par4 + 0) * var8);
/*  657: 787 */     var9.addVertexWithUV(par1 + 0, par2 + 0, 0.0D, (par3 + 0) * var7, (par4 + 0) * var8);
/*  658: 788 */     var9.draw();
/*  659:     */   }
/*  660:     */   
/*  661:     */   public ISaveFormat getSaveLoader()
/*  662:     */   {
/*  663: 796 */     return this.saveLoader;
/*  664:     */   }
/*  665:     */   
/*  666:     */   public void displayGuiScreen(GuiScreen p_147108_1_)
/*  667:     */   {
/*  668: 804 */     if (this.currentScreen != null) {
/*  669: 806 */       this.currentScreen.onGuiClosed();
/*  670:     */     }
/*  671: 809 */     if ((p_147108_1_ == null) && (this.theWorld == null)) {
/*  672: 811 */       p_147108_1_ = new NodusGuiMainMenu();
/*  673: 813 */     } else if ((p_147108_1_ == null) && (this.thePlayer.getHealth() <= 0.0F)) {
/*  674: 815 */       p_147108_1_ = new GuiGameOver();
/*  675:     */     }
/*  676: 818 */     if ((p_147108_1_ instanceof NodusGuiMainMenu))
/*  677:     */     {
/*  678: 820 */       this.gameSettings.showDebugInfo = false;
/*  679: 821 */       this.ingameGUI.getChatGUI().func_146231_a();
/*  680:     */     }
/*  681: 824 */     this.currentScreen = p_147108_1_;
/*  682: 826 */     if (p_147108_1_ != null)
/*  683:     */     {
/*  684: 828 */       setIngameNotInFocus();
/*  685: 829 */       ScaledResolution var2 = new ScaledResolution(this.gameSettings, this.displayWidth, this.displayHeight);
/*  686: 830 */       int var3 = var2.getScaledWidth();
/*  687: 831 */       int var4 = var2.getScaledHeight();
/*  688: 832 */       p_147108_1_.setWorldAndResolution(this, var3, var4);
/*  689: 833 */       this.skipRenderWorld = false;
/*  690:     */     }
/*  691:     */     else
/*  692:     */     {
/*  693: 837 */       this.mcSoundHandler.func_147687_e();
/*  694: 838 */       setIngameFocus();
/*  695:     */     }
/*  696:     */   }
/*  697:     */   
/*  698:     */   private void checkGLError(String par1Str)
/*  699:     */   {
/*  700: 847 */     int var2 = GL11.glGetError();
/*  701: 849 */     if (var2 != 0)
/*  702:     */     {
/*  703: 851 */       String var3 = GLU.gluErrorString(var2);
/*  704: 852 */       logger.error("########## GL ERROR ##########");
/*  705: 853 */       logger.error("@ " + par1Str);
/*  706: 854 */       logger.error(var2 + ": " + var3);
/*  707:     */     }
/*  708:     */   }
/*  709:     */   
/*  710:     */   public void shutdownMinecraftApplet()
/*  711:     */   {
/*  712: 865 */     EventManager.call(new EventShutdown());
/*  713:     */     try
/*  714:     */     {
/*  715: 869 */       logger.info("Stopping!");
/*  716:     */       try
/*  717:     */       {
/*  718: 873 */         loadWorld(null);
/*  719:     */       }
/*  720:     */       catch (Throwable localThrowable) {}
/*  721:     */       try
/*  722:     */       {
/*  723: 882 */         GLAllocation.deleteTexturesAndDisplayLists();
/*  724:     */       }
/*  725:     */       catch (Throwable localThrowable1) {}
/*  726: 889 */       this.mcSoundHandler.func_147685_d();
/*  727:     */     }
/*  728:     */     finally
/*  729:     */     {
/*  730: 893 */       Display.destroy();
/*  731: 895 */       if (!this.hasCrashed) {
/*  732: 897 */         System.exit(0);
/*  733:     */       }
/*  734:     */     }
/*  735: 901 */     System.gc();
/*  736:     */   }
/*  737:     */   
/*  738:     */   public void run()
/*  739:     */   {
/*  740: 906 */     this.running = true;
/*  741:     */     try
/*  742:     */     {
/*  743: 911 */       startGame();
/*  744:     */     }
/*  745:     */     catch (Throwable var11)
/*  746:     */     {
/*  747: 915 */       CrashReport var2 = CrashReport.makeCrashReport(var11, "Initializing game");
/*  748: 916 */       var2.makeCategory("Initialization");
/*  749: 917 */       displayCrashReport(addGraphicsAndWorldToCrashReport(var2));
/*  750: 918 */       return;
/*  751:     */     }
/*  752:     */     try
/*  753:     */     {
/*  754:     */       do
/*  755:     */       {
/*  756: 927 */         if ((!this.hasCrashed) || (this.crashReporter == null)) {
/*  757:     */           try
/*  758:     */           {
/*  759: 931 */             runGameLoop();
/*  760:     */           }
/*  761:     */           catch (OutOfMemoryError var10)
/*  762:     */           {
/*  763: 935 */             freeMemory();
/*  764: 936 */             displayGuiScreen(new GuiMemoryErrorScreen());
/*  765: 937 */             System.gc();
/*  766:     */           }
/*  767:     */         } else {
/*  768: 943 */           displayCrashReport(this.crashReporter);
/*  769:     */         }
/*  770: 925 */       } while (this.running);
/*  771:     */     }
/*  772:     */     catch (MinecraftError var12) {}catch (ReportedException var13)
/*  773:     */     {
/*  774: 952 */       addGraphicsAndWorldToCrashReport(var13.getCrashReport());
/*  775: 953 */       freeMemory();
/*  776: 954 */       logger.fatal("Reported exception thrown!", var13);
/*  777: 955 */       displayCrashReport(var13.getCrashReport());
/*  778:     */     }
/*  779:     */     catch (Throwable var14)
/*  780:     */     {
/*  781: 960 */       CrashReport var2 = addGraphicsAndWorldToCrashReport(new CrashReport("Unexpected error", var14));
/*  782: 961 */       freeMemory();
/*  783: 962 */       logger.fatal("Unreported exception thrown!", var14);
/*  784: 963 */       displayCrashReport(var2);
/*  785:     */     }
/*  786:     */     finally
/*  787:     */     {
/*  788: 968 */       shutdownMinecraftApplet();
/*  789:     */     }
/*  790: 971 */     return;
/*  791:     */   }
/*  792:     */   
/*  793:     */   private void runGameLoop()
/*  794:     */   {
/*  795: 980 */     AxisAlignedBB.getAABBPool().cleanPool();
/*  796: 982 */     if (this.theWorld != null) {
/*  797: 984 */       this.theWorld.getWorldVec3Pool().clear();
/*  798:     */     }
/*  799: 987 */     this.mcProfiler.startSection("root");
/*  800: 989 */     if ((Display.isCreated()) && (Display.isCloseRequested())) {
/*  801: 991 */       shutdown();
/*  802:     */     }
/*  803: 994 */     if ((this.isGamePaused) && (this.theWorld != null))
/*  804:     */     {
/*  805: 996 */       float var1 = this.timer.renderPartialTicks;
/*  806: 997 */       this.timer.updateTimer();
/*  807: 998 */       this.timer.renderPartialTicks = var1;
/*  808:     */     }
/*  809:     */     else
/*  810:     */     {
/*  811:1002 */       this.timer.updateTimer();
/*  812:     */     }
/*  813:1005 */     if (((this.theWorld == null) || (this.currentScreen == null)) && (this.refreshTexturePacksScheduled))
/*  814:     */     {
/*  815:1007 */       this.refreshTexturePacksScheduled = false;
/*  816:1008 */       refreshResources();
/*  817:     */     }
/*  818:1011 */     long var5 = System.nanoTime();
/*  819:1012 */     this.mcProfiler.startSection("tick");
/*  820:1014 */     for (int var3 = 0; var3 < this.timer.elapsedTicks; var3++) {
/*  821:1016 */       runTick();
/*  822:     */     }
/*  823:1019 */     this.mcProfiler.endStartSection("preRenderErrors");
/*  824:1020 */     long var6 = System.nanoTime() - var5;
/*  825:1021 */     checkGLError("Pre render");
/*  826:1022 */     net.minecraft.client.renderer.RenderBlocks.fancyGrass = this.gameSettings.fancyGraphics;
/*  827:1023 */     this.mcProfiler.endStartSection("sound");
/*  828:1024 */     this.mcSoundHandler.func_147691_a(this.thePlayer, this.timer.renderPartialTicks);
/*  829:1025 */     this.mcProfiler.endSection();
/*  830:1026 */     this.mcProfiler.startSection("render");
/*  831:1027 */     GL11.glPushMatrix();
/*  832:1028 */     GL11.glClear(16640);
/*  833:1029 */     this.mcFramebuffer.bindFramebuffer(true);
/*  834:1030 */     this.mcProfiler.startSection("display");
/*  835:1031 */     GL11.glEnable(3553);
/*  836:1033 */     if ((this.thePlayer != null) && (this.thePlayer.isEntityInsideOpaqueBlock())) {
/*  837:1035 */       this.gameSettings.thirdPersonView = 0;
/*  838:     */     }
/*  839:1038 */     this.mcProfiler.endSection();
/*  840:1040 */     if (!this.skipRenderWorld)
/*  841:     */     {
/*  842:1042 */       this.mcProfiler.endStartSection("gameRenderer");
/*  843:1043 */       this.entityRenderer.updateCameraAndRender(this.timer.renderPartialTicks);
/*  844:1044 */       this.mcProfiler.endSection();
/*  845:     */     }
/*  846:1047 */     GL11.glFlush();
/*  847:1048 */     this.mcProfiler.endSection();
/*  848:1050 */     if ((!Display.isActive()) && (this.fullscreen)) {
/*  849:1052 */       toggleFullscreen();
/*  850:     */     }
/*  851:1055 */     if ((this.gameSettings.showDebugInfo) && (this.gameSettings.showDebugProfilerChart))
/*  852:     */     {
/*  853:1057 */       if (!this.mcProfiler.profilingEnabled) {
/*  854:1059 */         this.mcProfiler.clearProfiling();
/*  855:     */       }
/*  856:1062 */       this.mcProfiler.profilingEnabled = true;
/*  857:1063 */       displayDebugInfo(var6);
/*  858:     */     }
/*  859:     */     else
/*  860:     */     {
/*  861:1067 */       this.mcProfiler.profilingEnabled = false;
/*  862:1068 */       this.prevFrameTime = System.nanoTime();
/*  863:     */     }
/*  864:1071 */     this.guiAchievement.func_146254_a();
/*  865:1072 */     this.mcFramebuffer.unbindFramebuffer();
/*  866:1073 */     GL11.glPopMatrix();
/*  867:1074 */     GL11.glPushMatrix();
/*  868:1075 */     this.mcFramebuffer.framebufferRender(this.displayWidth, this.displayHeight);
/*  869:1076 */     GL11.glPopMatrix();
/*  870:1077 */     this.mcProfiler.startSection("root");
/*  871:1078 */     func_147120_f();
/*  872:1079 */     Thread.yield();
/*  873:1080 */     screenshotListener();
/*  874:1081 */     checkGLError("Post render");
/*  875:1082 */     this.fpsCounter += 1;
/*  876:1083 */     this.isGamePaused = ((isSingleplayer()) && (this.currentScreen != null) && (this.currentScreen.doesGuiPauseGame()) && (!this.theIntegratedServer.getPublic()));
/*  877:1085 */     while (getSystemTime() >= this.debugUpdateTime + 1000L)
/*  878:     */     {
/*  879:1087 */       debugFPS = this.fpsCounter;
/*  880:1088 */       this.debug = (debugFPS + " fps, " + WorldRenderer.chunksUpdated + " chunk updates");
/*  881:1089 */       WorldRenderer.chunksUpdated = 0;
/*  882:1090 */       this.debugUpdateTime += 1000L;
/*  883:1091 */       this.fpsCounter = 0;
/*  884:1092 */       this.usageSnooper.addMemoryStatsToSnooper();
/*  885:1094 */       if (!this.usageSnooper.isSnooperRunning()) {
/*  886:1096 */         this.usageSnooper.startSnooper();
/*  887:     */       }
/*  888:     */     }
/*  889:1100 */     this.mcProfiler.endSection();
/*  890:1102 */     if (isFramerateLimitBelowMax()) {
/*  891:1104 */       Display.sync(getLimitFramerate());
/*  892:     */     }
/*  893:     */   }
/*  894:     */   
/*  895:     */   public void func_147120_f()
/*  896:     */   {
/*  897:     */     
/*  898:1112 */     if ((!this.fullscreen) && (Display.wasResized()))
/*  899:     */     {
/*  900:1114 */       int var1 = this.displayWidth;
/*  901:1115 */       int var2 = this.displayHeight;
/*  902:1116 */       this.displayWidth = Display.getWidth();
/*  903:1117 */       this.displayHeight = Display.getHeight();
/*  904:1119 */       if ((this.displayWidth != var1) || (this.displayHeight != var2))
/*  905:     */       {
/*  906:1121 */         if (this.displayWidth <= 0) {
/*  907:1123 */           this.displayWidth = 1;
/*  908:     */         }
/*  909:1126 */         if (this.displayHeight <= 0) {
/*  910:1128 */           this.displayHeight = 1;
/*  911:     */         }
/*  912:1131 */         resize(this.displayWidth, this.displayHeight);
/*  913:     */       }
/*  914:     */     }
/*  915:     */   }
/*  916:     */   
/*  917:     */   public int getLimitFramerate()
/*  918:     */   {
/*  919:1138 */     return (this.theWorld == null) && (this.currentScreen != null) ? 30 : this.gameSettings.limitFramerate;
/*  920:     */   }
/*  921:     */   
/*  922:     */   public boolean isFramerateLimitBelowMax()
/*  923:     */   {
/*  924:1143 */     return getLimitFramerate() < GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
/*  925:     */   }
/*  926:     */   
/*  927:     */   public void freeMemory()
/*  928:     */   {
/*  929:     */     try
/*  930:     */     {
/*  931:1150 */       memoryReserve = new byte[0];
/*  932:1151 */       this.renderGlobal.deleteAllDisplayLists();
/*  933:     */     }
/*  934:     */     catch (Throwable localThrowable) {}
/*  935:     */     try
/*  936:     */     {
/*  937:1160 */       System.gc();
/*  938:1161 */       AxisAlignedBB.getAABBPool().clearPool();
/*  939:1162 */       this.theWorld.getWorldVec3Pool().clearAndFreeCache();
/*  940:     */     }
/*  941:     */     catch (Throwable localThrowable1) {}
/*  942:     */     try
/*  943:     */     {
/*  944:1171 */       System.gc();
/*  945:1172 */       loadWorld(null);
/*  946:     */     }
/*  947:     */     catch (Throwable localThrowable2) {}
/*  948:1179 */     System.gc();
/*  949:     */   }
/*  950:     */   
/*  951:     */   private void screenshotListener()
/*  952:     */   {
/*  953:1187 */     if (this.gameSettings.keyBindScreenshot.isPressed())
/*  954:     */     {
/*  955:1189 */       if (!this.isTakingScreenshot)
/*  956:     */       {
/*  957:1191 */         this.isTakingScreenshot = true;
/*  958:1192 */         this.ingameGUI.getChatGUI().func_146227_a(ScreenShotHelper.saveScreenshot(this.mcDataDir, this.displayWidth, this.displayHeight, this.mcFramebuffer));
/*  959:     */       }
/*  960:     */     }
/*  961:     */     else {
/*  962:1197 */       this.isTakingScreenshot = false;
/*  963:     */     }
/*  964:     */   }
/*  965:     */   
/*  966:     */   private void updateDebugProfilerName(int par1)
/*  967:     */   {
/*  968:1206 */     List var2 = this.mcProfiler.getProfilingData(this.debugProfilerName);
/*  969:1208 */     if ((var2 != null) && (!var2.isEmpty()))
/*  970:     */     {
/*  971:1210 */       Profiler.Result var3 = (Profiler.Result)var2.remove(0);
/*  972:1212 */       if (par1 == 0)
/*  973:     */       {
/*  974:1214 */         if (var3.field_76331_c.length() > 0)
/*  975:     */         {
/*  976:1216 */           int var4 = this.debugProfilerName.lastIndexOf(".");
/*  977:1218 */           if (var4 >= 0) {
/*  978:1220 */             this.debugProfilerName = this.debugProfilerName.substring(0, var4);
/*  979:     */           }
/*  980:     */         }
/*  981:     */       }
/*  982:     */       else
/*  983:     */       {
/*  984:1226 */         par1--;
/*  985:1228 */         if ((par1 < var2.size()) && (!((Profiler.Result)var2.get(par1)).field_76331_c.equals("unspecified")))
/*  986:     */         {
/*  987:1230 */           if (this.debugProfilerName.length() > 0) {
/*  988:1232 */             this.debugProfilerName += ".";
/*  989:     */           }
/*  990:1235 */           this.debugProfilerName += ((Profiler.Result)var2.get(par1)).field_76331_c;
/*  991:     */         }
/*  992:     */       }
/*  993:     */     }
/*  994:     */   }
/*  995:     */   
/*  996:     */   private void displayDebugInfo(long par1)
/*  997:     */   {
/*  998:1243 */     if (this.mcProfiler.profilingEnabled)
/*  999:     */     {
/* 1000:1245 */       List var3 = this.mcProfiler.getProfilingData(this.debugProfilerName);
/* 1001:1246 */       Profiler.Result var4 = (Profiler.Result)var3.remove(0);
/* 1002:1247 */       GL11.glClear(256);
/* 1003:1248 */       GL11.glMatrixMode(5889);
/* 1004:1249 */       GL11.glEnable(2903);
/* 1005:1250 */       GL11.glLoadIdentity();
/* 1006:1251 */       GL11.glOrtho(0.0D, this.displayWidth, this.displayHeight, 0.0D, 1000.0D, 3000.0D);
/* 1007:1252 */       GL11.glMatrixMode(5888);
/* 1008:1253 */       GL11.glLoadIdentity();
/* 1009:1254 */       GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
/* 1010:1255 */       GL11.glLineWidth(1.0F);
/* 1011:1256 */       GL11.glDisable(3553);
/* 1012:1257 */       Tessellator var5 = Tessellator.instance;
/* 1013:1258 */       short var6 = 160;
/* 1014:1259 */       int var7 = this.displayWidth - var6 - 10;
/* 1015:1260 */       int var8 = this.displayHeight - var6 * 2;
/* 1016:1261 */       GL11.glEnable(3042);
/* 1017:1262 */       var5.startDrawingQuads();
/* 1018:1263 */       var5.setColorRGBA_I(0, 200);
/* 1019:1264 */       var5.addVertex(var7 - var6 * 1.1F, var8 - var6 * 0.6F - 16.0F, 0.0D);
/* 1020:1265 */       var5.addVertex(var7 - var6 * 1.1F, var8 + var6 * 2, 0.0D);
/* 1021:1266 */       var5.addVertex(var7 + var6 * 1.1F, var8 + var6 * 2, 0.0D);
/* 1022:1267 */       var5.addVertex(var7 + var6 * 1.1F, var8 - var6 * 0.6F - 16.0F, 0.0D);
/* 1023:1268 */       var5.draw();
/* 1024:1269 */       GL11.glDisable(3042);
/* 1025:1270 */       double var9 = 0.0D;
/* 1026:1273 */       for (int var11 = 0; var11 < var3.size(); var11++)
/* 1027:     */       {
/* 1028:1275 */         Profiler.Result var12 = (Profiler.Result)var3.get(var11);
/* 1029:1276 */         int var13 = MathHelper.floor_double(var12.field_76332_a / 4.0D) + 1;
/* 1030:1277 */         var5.startDrawing(6);
/* 1031:1278 */         var5.setColorOpaque_I(var12.func_76329_a());
/* 1032:1279 */         var5.addVertex(var7, var8, 0.0D);
/* 1033:1285 */         for (int var14 = var13; var14 >= 0; var14--)
/* 1034:     */         {
/* 1035:1287 */           float var15 = (float)((var9 + var12.field_76332_a * var14 / var13) * 3.141592653589793D * 2.0D / 100.0D);
/* 1036:1288 */           float var16 = MathHelper.sin(var15) * var6;
/* 1037:1289 */           float var17 = MathHelper.cos(var15) * var6 * 0.5F;
/* 1038:1290 */           var5.addVertex(var7 + var16, var8 - var17, 0.0D);
/* 1039:     */         }
/* 1040:1293 */         var5.draw();
/* 1041:1294 */         var5.startDrawing(5);
/* 1042:1295 */         var5.setColorOpaque_I((var12.func_76329_a() & 0xFEFEFE) >> 1);
/* 1043:1297 */         for (var14 = var13; var14 >= 0; var14--)
/* 1044:     */         {
/* 1045:1299 */           float var15 = (float)((var9 + var12.field_76332_a * var14 / var13) * 3.141592653589793D * 2.0D / 100.0D);
/* 1046:1300 */           float var16 = MathHelper.sin(var15) * var6;
/* 1047:1301 */           float var17 = MathHelper.cos(var15) * var6 * 0.5F;
/* 1048:1302 */           var5.addVertex(var7 + var16, var8 - var17, 0.0D);
/* 1049:1303 */           var5.addVertex(var7 + var16, var8 - var17 + 10.0F, 0.0D);
/* 1050:     */         }
/* 1051:1306 */         var5.draw();
/* 1052:1307 */         var9 += var12.field_76332_a;
/* 1053:     */       }
/* 1054:1310 */       DecimalFormat var19 = new DecimalFormat("##0.00");
/* 1055:1311 */       GL11.glEnable(3553);
/* 1056:1312 */       String var18 = "";
/* 1057:1314 */       if (!var4.field_76331_c.equals("unspecified")) {
/* 1058:1316 */         var18 = var18 + "[0] ";
/* 1059:     */       }
/* 1060:1319 */       if (var4.field_76331_c.length() == 0) {
/* 1061:1321 */         var18 = var18 + "ROOT ";
/* 1062:     */       } else {
/* 1063:1325 */         var18 = var18 + var4.field_76331_c + " ";
/* 1064:     */       }
/* 1065:1328 */       int var13 = 16777215;
/* 1066:1329 */       this.fontRenderer.drawStringWithShadow(var18, var7 - var6, var8 - var6 / 2 - 16, var13);
/* 1067:1330 */       this.fontRenderer.drawStringWithShadow(var18 = var19.format(var4.field_76330_b) + "%", var7 + var6 - this.fontRenderer.getStringWidth(var18), var8 - var6 / 2 - 16, var13);
/* 1068:1332 */       for (int var21 = 0; var21 < var3.size(); var21++)
/* 1069:     */       {
/* 1070:1334 */         Profiler.Result var20 = (Profiler.Result)var3.get(var21);
/* 1071:1335 */         String var22 = "";
/* 1072:1337 */         if (var20.field_76331_c.equals("unspecified")) {
/* 1073:1339 */           var22 = var22 + "[?] ";
/* 1074:     */         } else {
/* 1075:1343 */           var22 = var22 + "[" + (var21 + 1) + "] ";
/* 1076:     */         }
/* 1077:1346 */         var22 = var22 + var20.field_76331_c;
/* 1078:1347 */         this.fontRenderer.drawStringWithShadow(var22, var7 - var6, var8 + var6 / 2 + var21 * 8 + 20, var20.func_76329_a());
/* 1079:1348 */         this.fontRenderer.drawStringWithShadow(var22 = var19.format(var20.field_76332_a) + "%", var7 + var6 - 50 - this.fontRenderer.getStringWidth(var22), var8 + var6 / 2 + var21 * 8 + 20, var20.func_76329_a());
/* 1080:1349 */         this.fontRenderer.drawStringWithShadow(var22 = var19.format(var20.field_76330_b) + "%", var7 + var6 - this.fontRenderer.getStringWidth(var22), var8 + var6 / 2 + var21 * 8 + 20, var20.func_76329_a());
/* 1081:     */       }
/* 1082:     */     }
/* 1083:     */   }
/* 1084:     */   
/* 1085:     */   public void shutdown()
/* 1086:     */   {
/* 1087:1359 */     this.running = false;
/* 1088:     */   }
/* 1089:     */   
/* 1090:     */   public void setIngameFocus()
/* 1091:     */   {
/* 1092:1368 */     if (Display.isActive()) {
/* 1093:1370 */       if (!this.inGameHasFocus)
/* 1094:     */       {
/* 1095:1372 */         this.inGameHasFocus = true;
/* 1096:1373 */         this.mouseHelper.grabMouseCursor();
/* 1097:1374 */         displayGuiScreen(null);
/* 1098:1375 */         this.leftClickCounter = 10000;
/* 1099:     */       }
/* 1100:     */     }
/* 1101:     */   }
/* 1102:     */   
/* 1103:     */   public void setIngameNotInFocus()
/* 1104:     */   {
/* 1105:1385 */     if (this.inGameHasFocus)
/* 1106:     */     {
/* 1107:1387 */       KeyBinding.unPressAllKeys();
/* 1108:1388 */       this.inGameHasFocus = false;
/* 1109:1389 */       this.mouseHelper.ungrabMouseCursor();
/* 1110:     */     }
/* 1111:     */   }
/* 1112:     */   
/* 1113:     */   public void displayInGameMenu()
/* 1114:     */   {
/* 1115:1398 */     if (this.currentScreen == null)
/* 1116:     */     {
/* 1117:1400 */       displayGuiScreen(new GuiIngameMenu());
/* 1118:1402 */       if ((isSingleplayer()) && (!this.theIntegratedServer.getPublic())) {
/* 1119:1404 */         this.mcSoundHandler.func_147689_b();
/* 1120:     */       }
/* 1121:     */     }
/* 1122:     */   }
/* 1123:     */   
/* 1124:     */   private void func_147115_a(boolean p_147115_1_)
/* 1125:     */   {
/* 1126:1411 */     if (!p_147115_1_) {
/* 1127:1413 */       this.leftClickCounter = 0;
/* 1128:     */     }
/* 1129:1416 */     if (this.leftClickCounter <= 0) {
/* 1130:1418 */       if ((p_147115_1_) && (this.objectMouseOver != null) && (this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK))
/* 1131:     */       {
/* 1132:1420 */         int var2 = this.objectMouseOver.blockX;
/* 1133:1421 */         int var3 = this.objectMouseOver.blockY;
/* 1134:1422 */         int var4 = this.objectMouseOver.blockZ;
/* 1135:1424 */         if (this.theWorld.getBlock(var2, var3, var4).getMaterial() != Material.air)
/* 1136:     */         {
/* 1137:1426 */           this.playerController.onPlayerDamageBlock(var2, var3, var4, this.objectMouseOver.sideHit);
/* 1138:1428 */           if (this.thePlayer.isCurrentToolAdventureModeExempt(var2, var3, var4))
/* 1139:     */           {
/* 1140:1430 */             this.effectRenderer.addBlockHitEffects(var2, var3, var4, this.objectMouseOver.sideHit);
/* 1141:1431 */             this.thePlayer.swingItem();
/* 1142:     */           }
/* 1143:     */         }
/* 1144:     */       }
/* 1145:     */       else
/* 1146:     */       {
/* 1147:1437 */         this.playerController.resetBlockRemoving();
/* 1148:     */       }
/* 1149:     */     }
/* 1150:     */   }
/* 1151:     */   
/* 1152:     */   private void func_147116_af()
/* 1153:     */   {
/* 1154:1444 */     if (this.leftClickCounter <= 0)
/* 1155:     */     {
/* 1156:1446 */       this.thePlayer.swingItem();
/* 1157:1448 */       if (this.objectMouseOver == null)
/* 1158:     */       {
/* 1159:1450 */         logger.error("Null returned as 'hitResult', this shouldn't happen!");
/* 1160:1452 */         if (this.playerController.isNotCreative()) {
/* 1161:1454 */           this.leftClickCounter = 10;
/* 1162:     */         }
/* 1163:     */       }
/* 1164:     */       else
/* 1165:     */       {
/* 1166:1459 */         switch (SwitchMovingObjectType.field_151437_a[this.objectMouseOver.typeOfHit.ordinal()])
/* 1167:     */         {
/* 1168:     */         case 1: 
/* 1169:1462 */           this.playerController.attackEntity(this.thePlayer, this.objectMouseOver.entityHit);
/* 1170:1463 */           break;
/* 1171:     */         case 2: 
/* 1172:1466 */           int var1 = this.objectMouseOver.blockX;
/* 1173:1467 */           int var2 = this.objectMouseOver.blockY;
/* 1174:1468 */           int var3 = this.objectMouseOver.blockZ;
/* 1175:1470 */           if (this.theWorld.getBlock(var1, var2, var3).getMaterial() == Material.air)
/* 1176:     */           {
/* 1177:1472 */             if (this.playerController.isNotCreative()) {
/* 1178:1474 */               this.leftClickCounter = 10;
/* 1179:     */             }
/* 1180:     */           }
/* 1181:     */           else {
/* 1182:1479 */             this.playerController.clickBlock(var1, var2, var3, this.objectMouseOver.sideHit);
/* 1183:     */           }
/* 1184:     */           break;
/* 1185:     */         }
/* 1186:     */       }
/* 1187:     */     }
/* 1188:     */   }
/* 1189:     */   
/* 1190:     */   private void func_147121_ag()
/* 1191:     */   {
/* 1192:1488 */     this.rightClickDelayTimer = 4;
/* 1193:1489 */     boolean var1 = true;
/* 1194:1490 */     ItemStack var2 = this.thePlayer.inventory.getCurrentItem();
/* 1195:1492 */     if (this.objectMouseOver == null) {
/* 1196:1494 */       logger.warn("Null returned as 'hitResult', this shouldn't happen!");
/* 1197:     */     } else {
/* 1198:1498 */       switch (SwitchMovingObjectType.field_151437_a[this.objectMouseOver.typeOfHit.ordinal()])
/* 1199:     */       {
/* 1200:     */       case 1: 
/* 1201:1501 */         if (this.playerController.interactWithEntitySendPacket(this.thePlayer, this.objectMouseOver.entityHit)) {
/* 1202:1503 */           var1 = false;
/* 1203:     */         }
/* 1204:1506 */         break;
/* 1205:     */       case 2: 
/* 1206:1509 */         int var3 = this.objectMouseOver.blockX;
/* 1207:1510 */         int var4 = this.objectMouseOver.blockY;
/* 1208:1511 */         int var5 = this.objectMouseOver.blockZ;
/* 1209:1513 */         if (this.theWorld.getBlock(var3, var4, var5).getMaterial() != Material.air)
/* 1210:     */         {
/* 1211:1515 */           int var6 = var2 != null ? var2.stackSize : 0;
/* 1212:1517 */           if (this.playerController.onPlayerRightClick(this.thePlayer, this.theWorld, var2, var3, var4, var5, this.objectMouseOver.sideHit, this.objectMouseOver.hitVec))
/* 1213:     */           {
/* 1214:1519 */             var1 = false;
/* 1215:1520 */             this.thePlayer.swingItem();
/* 1216:     */           }
/* 1217:1523 */           if (var2 == null) {
/* 1218:1525 */             return;
/* 1219:     */           }
/* 1220:1528 */           if (var2.stackSize == 0) {
/* 1221:1530 */             this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.currentItem] = null;
/* 1222:1532 */           } else if ((var2.stackSize != var6) || (this.playerController.isInCreativeMode())) {
/* 1223:1534 */             this.entityRenderer.itemRenderer.resetEquippedProgress();
/* 1224:     */           }
/* 1225:     */         }
/* 1226:     */         break;
/* 1227:     */       }
/* 1228:     */     }
/* 1229:1540 */     if (var1)
/* 1230:     */     {
/* 1231:1542 */       ItemStack var7 = this.thePlayer.inventory.getCurrentItem();
/* 1232:1544 */       if ((var7 != null) && (this.playerController.sendUseItem(this.thePlayer, this.theWorld, var7))) {
/* 1233:1546 */         this.entityRenderer.itemRenderer.resetEquippedProgress2();
/* 1234:     */       }
/* 1235:     */     }
/* 1236:     */   }
/* 1237:     */   
/* 1238:     */   public void toggleFullscreen()
/* 1239:     */   {
/* 1240:     */     try
/* 1241:     */     {
/* 1242:1558 */       this.fullscreen = (!this.fullscreen);
/* 1243:1560 */       if (this.fullscreen)
/* 1244:     */       {
/* 1245:1562 */         updateDisplayMode();
/* 1246:1563 */         this.displayWidth = Display.getDisplayMode().getWidth();
/* 1247:1564 */         this.displayHeight = Display.getDisplayMode().getHeight();
/* 1248:1566 */         if (this.displayWidth <= 0) {
/* 1249:1568 */           this.displayWidth = 1;
/* 1250:     */         }
/* 1251:1571 */         if (this.displayHeight <= 0) {
/* 1252:1573 */           this.displayHeight = 1;
/* 1253:     */         }
/* 1254:     */       }
/* 1255:     */       else
/* 1256:     */       {
/* 1257:1578 */         Display.setDisplayMode(new DisplayMode(this.tempDisplayWidth, this.tempDisplayHeight));
/* 1258:1579 */         this.displayWidth = this.tempDisplayWidth;
/* 1259:1580 */         this.displayHeight = this.tempDisplayHeight;
/* 1260:1582 */         if (this.displayWidth <= 0) {
/* 1261:1584 */           this.displayWidth = 1;
/* 1262:     */         }
/* 1263:1587 */         if (this.displayHeight <= 0) {
/* 1264:1589 */           this.displayHeight = 1;
/* 1265:     */         }
/* 1266:     */       }
/* 1267:1593 */       if (this.currentScreen != null) {
/* 1268:1595 */         resize(this.displayWidth, this.displayHeight);
/* 1269:     */       } else {
/* 1270:1599 */         updateFramebufferSize();
/* 1271:     */       }
/* 1272:1602 */       Display.setFullscreen(this.fullscreen);
/* 1273:1603 */       Display.setVSyncEnabled(this.gameSettings.enableVsync);
/* 1274:1604 */       func_147120_f();
/* 1275:     */     }
/* 1276:     */     catch (Exception var2)
/* 1277:     */     {
/* 1278:1608 */       logger.error("Couldn't toggle fullscreen", var2);
/* 1279:     */     }
/* 1280:     */   }
/* 1281:     */   
/* 1282:     */   private void resize(int par1, int par2)
/* 1283:     */   {
/* 1284:1617 */     this.displayWidth = (par1 <= 0 ? 1 : par1);
/* 1285:1618 */     this.displayHeight = (par2 <= 0 ? 1 : par2);
/* 1286:1620 */     if (this.currentScreen != null)
/* 1287:     */     {
/* 1288:1622 */       ScaledResolution var3 = new ScaledResolution(this.gameSettings, par1, par2);
/* 1289:1623 */       int var4 = var3.getScaledWidth();
/* 1290:1624 */       int var5 = var3.getScaledHeight();
/* 1291:1625 */       this.currentScreen.setWorldAndResolution(this, var4, var5);
/* 1292:     */     }
/* 1293:1628 */     this.loadingScreen = new LoadingScreenRenderer(this);
/* 1294:1629 */     updateFramebufferSize();
/* 1295:     */   }
/* 1296:     */   
/* 1297:     */   private void updateFramebufferSize()
/* 1298:     */   {
/* 1299:1634 */     this.mcFramebuffer.createBindFramebuffer(this.displayWidth, this.displayHeight);
/* 1300:1636 */     if (this.entityRenderer != null) {
/* 1301:1638 */       this.entityRenderer.updateShaderGroupSize(this.displayWidth, this.displayHeight);
/* 1302:     */     }
/* 1303:     */   }
/* 1304:     */   
/* 1305:     */   public void runTick()
/* 1306:     */   {
/* 1307:1647 */     if (this.rightClickDelayTimer > 0) {
/* 1308:1649 */       this.rightClickDelayTimer -= 1;
/* 1309:     */     }
/* 1310:1652 */     this.mcProfiler.startSection("gui");
/* 1311:1654 */     if (!this.isGamePaused) {
/* 1312:1656 */       this.ingameGUI.updateTick();
/* 1313:     */     }
/* 1314:1659 */     this.mcProfiler.endStartSection("pick");
/* 1315:1660 */     this.entityRenderer.getMouseOver(1.0F);
/* 1316:1661 */     this.mcProfiler.endStartSection("gameMode");
/* 1317:1663 */     if ((!this.isGamePaused) && (this.theWorld != null)) {
/* 1318:1665 */       this.playerController.updateController();
/* 1319:     */     }
/* 1320:1668 */     this.mcProfiler.endStartSection("textures");
/* 1321:1670 */     if (!this.isGamePaused) {
/* 1322:1672 */       this.renderEngine.tick();
/* 1323:     */     }
/* 1324:1675 */     if ((this.currentScreen == null) && (this.thePlayer != null))
/* 1325:     */     {
/* 1326:1677 */       if (this.thePlayer.getHealth() <= 0.0F) {
/* 1327:1679 */         displayGuiScreen(null);
/* 1328:1681 */       } else if ((this.thePlayer.isPlayerSleeping()) && (this.theWorld != null)) {
/* 1329:1683 */         displayGuiScreen(new GuiSleepMP());
/* 1330:     */       }
/* 1331:     */     }
/* 1332:1686 */     else if ((this.currentScreen != null) && ((this.currentScreen instanceof GuiSleepMP)) && (!this.thePlayer.isPlayerSleeping())) {
/* 1333:1688 */       displayGuiScreen(null);
/* 1334:     */     }
/* 1335:1691 */     if (this.currentScreen != null) {
/* 1336:1693 */       this.leftClickCounter = 10000;
/* 1337:     */     }
/* 1338:1699 */     if (this.currentScreen != null)
/* 1339:     */     {
/* 1340:     */       try
/* 1341:     */       {
/* 1342:1703 */         this.currentScreen.handleInput();
/* 1343:     */       }
/* 1344:     */       catch (Throwable var6)
/* 1345:     */       {
/* 1346:1707 */         CrashReport var2 = CrashReport.makeCrashReport(var6, "Updating screen events");
/* 1347:1708 */         CrashReportCategory var3 = var2.makeCategory("Affected screen");
/* 1348:1709 */         var3.addCrashSectionCallable("Screen name", new Callable()
/* 1349:     */         {
/* 1350:     */           private static final String __OBFID = "CL_00000640";
/* 1351:     */           
/* 1352:     */           public String call()
/* 1353:     */           {
/* 1354:1714 */             return Minecraft.this.currentScreen.getClass().getCanonicalName();
/* 1355:     */           }
/* 1356:1716 */         });
/* 1357:1717 */         throw new ReportedException(var2);
/* 1358:     */       }
/* 1359:1720 */       if (this.currentScreen != null) {
/* 1360:     */         try
/* 1361:     */         {
/* 1362:1724 */           this.currentScreen.updateScreen();
/* 1363:     */         }
/* 1364:     */         catch (Throwable var5)
/* 1365:     */         {
/* 1366:1728 */           CrashReport var2 = CrashReport.makeCrashReport(var5, "Ticking screen");
/* 1367:1729 */           CrashReportCategory var3 = var2.makeCategory("Affected screen");
/* 1368:1730 */           var3.addCrashSectionCallable("Screen name", new Callable()
/* 1369:     */           {
/* 1370:     */             private static final String __OBFID = "CL_00000642";
/* 1371:     */             
/* 1372:     */             public String call()
/* 1373:     */             {
/* 1374:1735 */               return Minecraft.this.currentScreen.getClass().getCanonicalName();
/* 1375:     */             }
/* 1376:1737 */           });
/* 1377:1738 */           throw new ReportedException(var2);
/* 1378:     */         }
/* 1379:     */       }
/* 1380:     */     }
/* 1381:1743 */     if ((this.currentScreen == null) || (this.currentScreen.field_146291_p))
/* 1382:     */     {
/* 1383:1745 */       this.mcProfiler.endStartSection("mouse");
/* 1384:1748 */       while (Mouse.next())
/* 1385:     */       {
/* 1386:1750 */         int var1 = Mouse.getEventButton();
/* 1387:1752 */         if ((isRunningOnMac) && (var1 == 0) && ((Keyboard.isKeyDown(29)) || (Keyboard.isKeyDown(157)))) {
/* 1388:1754 */           var1 = 1;
/* 1389:     */         }
/* 1390:1757 */         KeyBinding.setKeyBindState(var1 - 100, Mouse.getEventButtonState());
/* 1391:1759 */         if (Mouse.getEventButtonState()) {
/* 1392:1761 */           KeyBinding.onTick(var1 - 100);
/* 1393:     */         }
/* 1394:1764 */         long var9 = getSystemTime() - this.systemTime;
/* 1395:1766 */         if (var9 <= 200L)
/* 1396:     */         {
/* 1397:1768 */           int var4 = Mouse.getEventDWheel();
/* 1398:1770 */           if (var4 != 0)
/* 1399:     */           {
/* 1400:1772 */             this.thePlayer.inventory.changeCurrentItem(var4);
/* 1401:1774 */             if (this.gameSettings.noclip)
/* 1402:     */             {
/* 1403:1776 */               if (var4 > 0) {
/* 1404:1778 */                 var4 = 1;
/* 1405:     */               }
/* 1406:1781 */               if (var4 < 0) {
/* 1407:1783 */                 var4 = -1;
/* 1408:     */               }
/* 1409:1786 */               this.gameSettings.noclipRate += var4 * 0.25F;
/* 1410:     */             }
/* 1411:     */           }
/* 1412:1790 */           if (this.currentScreen == null)
/* 1413:     */           {
/* 1414:1792 */             if ((!this.inGameHasFocus) && (Mouse.getEventButtonState())) {
/* 1415:1794 */               setIngameFocus();
/* 1416:     */             }
/* 1417:     */           }
/* 1418:1797 */           else if (this.currentScreen != null) {
/* 1419:1799 */             this.currentScreen.handleMouseInput();
/* 1420:     */           }
/* 1421:     */         }
/* 1422:     */       }
/* 1423:1804 */       if (this.leftClickCounter > 0) {
/* 1424:1806 */         this.leftClickCounter -= 1;
/* 1425:     */       }
/* 1426:1809 */       this.mcProfiler.endStartSection("keyboard");
/* 1427:1812 */       while (Keyboard.next())
/* 1428:     */       {
/* 1429:1814 */         KeyBinding.setKeyBindState(Keyboard.getEventKey(), Keyboard.getEventKeyState());
/* 1430:1816 */         if (Keyboard.getEventKeyState()) {
/* 1431:1818 */           KeyBinding.onTick(Keyboard.getEventKey());
/* 1432:     */         }
/* 1433:1821 */         if (this.field_83002_am > 0L)
/* 1434:     */         {
/* 1435:1823 */           if (getSystemTime() - this.field_83002_am >= 6000L) {
/* 1436:1825 */             throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
/* 1437:     */           }
/* 1438:1828 */           if ((!Keyboard.isKeyDown(46)) || (!Keyboard.isKeyDown(61))) {
/* 1439:1830 */             this.field_83002_am = -1L;
/* 1440:     */           }
/* 1441:     */         }
/* 1442:1833 */         else if ((Keyboard.isKeyDown(46)) && (Keyboard.isKeyDown(61)))
/* 1443:     */         {
/* 1444:1835 */           this.field_83002_am = getSystemTime();
/* 1445:     */         }
/* 1446:1838 */         if (Keyboard.getEventKeyState())
/* 1447:     */         {
/* 1448:1840 */           if ((Keyboard.getEventKey() == 62) && (this.entityRenderer != null)) {
/* 1449:1842 */             this.entityRenderer.deactivateShader();
/* 1450:     */           }
/* 1451:1845 */           if (Keyboard.getEventKey() == 87)
/* 1452:     */           {
/* 1453:1847 */             toggleFullscreen();
/* 1454:     */           }
/* 1455:     */           else
/* 1456:     */           {
/* 1457:1851 */             if (this.currentScreen != null)
/* 1458:     */             {
/* 1459:1853 */               this.currentScreen.handleKeyboardInput();
/* 1460:     */             }
/* 1461:     */             else
/* 1462:     */             {
/* 1463:1858 */               EventManager.call(new EventKeyPress(Keyboard.getEventKey()));
/* 1464:1860 */               if (Keyboard.getEventKey() == 1) {
/* 1465:1862 */                 displayInGameMenu();
/* 1466:     */               }
/* 1467:1865 */               if ((Keyboard.getEventKey() == 31) && (Keyboard.isKeyDown(61))) {
/* 1468:1867 */                 refreshResources();
/* 1469:     */               }
/* 1470:1870 */               if ((Keyboard.getEventKey() == 20) && (Keyboard.isKeyDown(61))) {
/* 1471:1872 */                 refreshResources();
/* 1472:     */               }
/* 1473:1875 */               if ((Keyboard.getEventKey() == 33) && (Keyboard.isKeyDown(61)))
/* 1474:     */               {
/* 1475:1877 */                 boolean var8 = Keyboard.isKeyDown(42) | Keyboard.isKeyDown(54);
/* 1476:1878 */                 this.gameSettings.setOptionValue(GameSettings.Options.RENDER_DISTANCE, var8 ? -1 : 1);
/* 1477:     */               }
/* 1478:1881 */               if ((Keyboard.getEventKey() == 30) && (Keyboard.isKeyDown(61))) {
/* 1479:1883 */                 this.renderGlobal.loadRenderers();
/* 1480:     */               }
/* 1481:1886 */               if ((Keyboard.getEventKey() == 35) && (Keyboard.isKeyDown(61)))
/* 1482:     */               {
/* 1483:1888 */                 this.gameSettings.advancedItemTooltips = (!this.gameSettings.advancedItemTooltips);
/* 1484:1889 */                 this.gameSettings.saveOptions();
/* 1485:     */               }
/* 1486:1892 */               if ((Keyboard.getEventKey() == 48) && (Keyboard.isKeyDown(61))) {
/* 1487:1894 */                 RenderManager.field_85095_o = !RenderManager.field_85095_o;
/* 1488:     */               }
/* 1489:1897 */               if ((Keyboard.getEventKey() == 25) && (Keyboard.isKeyDown(61)))
/* 1490:     */               {
/* 1491:1899 */                 this.gameSettings.pauseOnLostFocus = (!this.gameSettings.pauseOnLostFocus);
/* 1492:1900 */                 this.gameSettings.saveOptions();
/* 1493:     */               }
/* 1494:1903 */               if (Keyboard.getEventKey() == 59) {
/* 1495:1905 */                 this.gameSettings.hideGUI = (!this.gameSettings.hideGUI);
/* 1496:     */               }
/* 1497:1908 */               if (Keyboard.getEventKey() == 61)
/* 1498:     */               {
/* 1499:1910 */                 this.gameSettings.showDebugInfo = (!this.gameSettings.showDebugInfo);
/* 1500:1911 */                 this.gameSettings.showDebugProfilerChart = GuiScreen.isShiftKeyDown();
/* 1501:     */               }
/* 1502:1914 */               if (this.gameSettings.keyBindTogglePerspective.isPressed())
/* 1503:     */               {
/* 1504:1916 */                 this.gameSettings.thirdPersonView += 1;
/* 1505:1918 */                 if (this.gameSettings.thirdPersonView > 2) {
/* 1506:1920 */                   this.gameSettings.thirdPersonView = 0;
/* 1507:     */                 }
/* 1508:     */               }
/* 1509:1924 */               if (this.gameSettings.keyBindSmoothCamera.isPressed()) {
/* 1510:1926 */                 this.gameSettings.smoothCamera = (!this.gameSettings.smoothCamera);
/* 1511:     */               }
/* 1512:     */             }
/* 1513:1930 */             if ((this.gameSettings.showDebugInfo) && (this.gameSettings.showDebugProfilerChart))
/* 1514:     */             {
/* 1515:1932 */               if (Keyboard.getEventKey() == 11) {
/* 1516:1934 */                 updateDebugProfilerName(0);
/* 1517:     */               }
/* 1518:1937 */               for (int var1 = 0; var1 < 9; var1++) {
/* 1519:1939 */                 if (Keyboard.getEventKey() == 2 + var1) {
/* 1520:1941 */                   updateDebugProfilerName(var1 + 1);
/* 1521:     */                 }
/* 1522:     */               }
/* 1523:     */             }
/* 1524:     */           }
/* 1525:     */         }
/* 1526:     */       }
/* 1527:1949 */       for (int var1 = 0; var1 < 9; var1++) {
/* 1528:1951 */         if (this.gameSettings.keyBindsHotbar[var1].isPressed()) {
/* 1529:1953 */           this.thePlayer.inventory.currentItem = var1;
/* 1530:     */         }
/* 1531:     */       }
/* 1532:1957 */       boolean var8 = this.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN;
/* 1533:1959 */       while (this.gameSettings.keyBindInventory.isPressed()) {
/* 1534:1961 */         if (this.playerController.func_110738_j())
/* 1535:     */         {
/* 1536:1963 */           this.thePlayer.func_110322_i();
/* 1537:     */         }
/* 1538:     */         else
/* 1539:     */         {
/* 1540:1967 */           getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
/* 1541:1968 */           displayGuiScreen(new GuiInventory(this.thePlayer));
/* 1542:     */         }
/* 1543:     */       }
/* 1544:1972 */       while (this.gameSettings.keyBindDrop.isPressed()) {
/* 1545:1974 */         this.thePlayer.dropOneItem(GuiScreen.isCtrlKeyDown());
/* 1546:     */       }
/* 1547:1977 */       while ((this.gameSettings.keyBindChat.isPressed()) && (var8)) {
/* 1548:1979 */         displayGuiScreen(new GuiChat());
/* 1549:     */       }
/* 1550:1982 */       if ((this.currentScreen == null) && (this.gameSettings.keyBindCommand.isPressed()) && (var8)) {
/* 1551:1984 */         displayGuiScreen(new GuiChat("/"));
/* 1552:     */       }
/* 1553:1987 */       if (this.thePlayer.isUsingItem())
/* 1554:     */       {
/* 1555:1989 */         if (!this.gameSettings.keyBindUseItem.getIsKeyPressed()) {
/* 1556:1991 */           this.playerController.onStoppedUsingItem(this.thePlayer);
/* 1557:     */         }
/* 1558:1998 */         while (this.gameSettings.keyBindAttack.isPressed()) {}
/* 1559:2000 */         while (this.gameSettings.keyBindUseItem.isPressed()) {}
/* 1560:2007 */         while (this.gameSettings.keyBindPickBlock.isPressed()) {}
/* 1561:     */       }
/* 1562:2019 */       while (this.gameSettings.keyBindAttack.isPressed()) {
/* 1563:2021 */         func_147116_af();
/* 1564:     */       }
/* 1565:2024 */       while (this.gameSettings.keyBindUseItem.isPressed()) {
/* 1566:2026 */         func_147121_ag();
/* 1567:     */       }
/* 1568:2029 */       while (this.gameSettings.keyBindPickBlock.isPressed()) {
/* 1569:2031 */         func_147112_ai();
/* 1570:     */       }
/* 1571:2035 */       if ((this.gameSettings.keyBindUseItem.getIsKeyPressed()) && (this.rightClickDelayTimer == 0) && (!this.thePlayer.isUsingItem())) {
/* 1572:2037 */         func_147121_ag();
/* 1573:     */       }
/* 1574:2040 */       func_147115_a((this.currentScreen == null) && (this.gameSettings.keyBindAttack.getIsKeyPressed()) && (this.inGameHasFocus));
/* 1575:     */     }
/* 1576:2043 */     if (this.theWorld != null)
/* 1577:     */     {
/* 1578:2045 */       if (this.thePlayer != null)
/* 1579:     */       {
/* 1580:2047 */         this.joinPlayerCounter += 1;
/* 1581:2049 */         if (this.joinPlayerCounter == 30)
/* 1582:     */         {
/* 1583:2051 */           this.joinPlayerCounter = 0;
/* 1584:2052 */           this.theWorld.joinEntityInSurroundings(this.thePlayer);
/* 1585:     */         }
/* 1586:     */       }
/* 1587:2056 */       this.mcProfiler.endStartSection("gameRenderer");
/* 1588:2058 */       if (!this.isGamePaused) {
/* 1589:2060 */         this.entityRenderer.updateRenderer();
/* 1590:     */       }
/* 1591:2063 */       this.mcProfiler.endStartSection("levelRenderer");
/* 1592:2065 */       if (!this.isGamePaused) {
/* 1593:2067 */         this.renderGlobal.updateClouds();
/* 1594:     */       }
/* 1595:2070 */       this.mcProfiler.endStartSection("level");
/* 1596:2072 */       if (!this.isGamePaused)
/* 1597:     */       {
/* 1598:2074 */         if (this.theWorld.lastLightningBolt > 0) {
/* 1599:2076 */           this.theWorld.lastLightningBolt -= 1;
/* 1600:     */         }
/* 1601:2079 */         this.theWorld.updateEntities();
/* 1602:     */       }
/* 1603:     */     }
/* 1604:2083 */     if (!this.isGamePaused)
/* 1605:     */     {
/* 1606:2085 */       this.mcMusicTicker.update();
/* 1607:2086 */       this.mcSoundHandler.update();
/* 1608:     */     }
/* 1609:2089 */     if (this.theWorld != null)
/* 1610:     */     {
/* 1611:2091 */       if (!this.isGamePaused)
/* 1612:     */       {
/* 1613:2093 */         this.theWorld.setAllowedSpawnTypes(this.theWorld.difficultySetting != EnumDifficulty.PEACEFUL, true);
/* 1614:     */         try
/* 1615:     */         {
/* 1616:2097 */           this.theWorld.tick();
/* 1617:     */         }
/* 1618:     */         catch (Throwable var7)
/* 1619:     */         {
/* 1620:2101 */           CrashReport var2 = CrashReport.makeCrashReport(var7, "Exception in world tick");
/* 1621:2103 */           if (this.theWorld == null)
/* 1622:     */           {
/* 1623:2105 */             CrashReportCategory var3 = var2.makeCategory("Affected level");
/* 1624:2106 */             var3.addCrashSection("Problem", "Level is null!");
/* 1625:     */           }
/* 1626:     */           else
/* 1627:     */           {
/* 1628:2110 */             this.theWorld.addWorldInfoToCrashReport(var2);
/* 1629:     */           }
/* 1630:2113 */           throw new ReportedException(var2);
/* 1631:     */         }
/* 1632:     */       }
/* 1633:2117 */       this.mcProfiler.endStartSection("animateTick");
/* 1634:2119 */       if ((!this.isGamePaused) && (this.theWorld != null)) {
/* 1635:2121 */         this.theWorld.doVoidFogParticles(MathHelper.floor_double(this.thePlayer.posX), MathHelper.floor_double(this.thePlayer.posY), MathHelper.floor_double(this.thePlayer.posZ));
/* 1636:     */       }
/* 1637:2124 */       this.mcProfiler.endStartSection("particles");
/* 1638:2126 */       if (!this.isGamePaused) {
/* 1639:2128 */         this.effectRenderer.updateEffects();
/* 1640:     */       }
/* 1641:     */     }
/* 1642:2131 */     else if (this.myNetworkManager != null)
/* 1643:     */     {
/* 1644:2133 */       this.mcProfiler.endStartSection("pendingConnection");
/* 1645:2134 */       this.myNetworkManager.processReceivedPackets();
/* 1646:     */     }
/* 1647:2137 */     this.mcProfiler.endSection();
/* 1648:2138 */     this.systemTime = getSystemTime();
/* 1649:     */   }
/* 1650:     */   
/* 1651:     */   public void launchIntegratedServer(String par1Str, String par2Str, WorldSettings par3WorldSettings)
/* 1652:     */   {
/* 1653:2146 */     loadWorld(null);
/* 1654:2147 */     System.gc();
/* 1655:2148 */     ISaveHandler var4 = this.saveLoader.getSaveLoader(par1Str, false);
/* 1656:2149 */     WorldInfo var5 = var4.loadWorldInfo();
/* 1657:2151 */     if ((var5 == null) && (par3WorldSettings != null))
/* 1658:     */     {
/* 1659:2153 */       var5 = new WorldInfo(par3WorldSettings, par1Str);
/* 1660:2154 */       var4.saveWorldInfo(var5);
/* 1661:     */     }
/* 1662:2157 */     if (par3WorldSettings == null) {
/* 1663:2159 */       par3WorldSettings = new WorldSettings(var5);
/* 1664:     */     }
/* 1665:     */     try
/* 1666:     */     {
/* 1667:2164 */       this.theIntegratedServer = new IntegratedServer(this, par1Str, par2Str, par3WorldSettings);
/* 1668:2165 */       this.theIntegratedServer.startServerThread();
/* 1669:2166 */       this.integratedServerIsRunning = true;
/* 1670:     */     }
/* 1671:     */     catch (Throwable var10)
/* 1672:     */     {
/* 1673:2170 */       CrashReport var7 = CrashReport.makeCrashReport(var10, "Starting integrated server");
/* 1674:2171 */       CrashReportCategory var8 = var7.makeCategory("Starting integrated server");
/* 1675:2172 */       var8.addCrashSection("Level ID", par1Str);
/* 1676:2173 */       var8.addCrashSection("Level Name", par2Str);
/* 1677:2174 */       throw new ReportedException(var7);
/* 1678:     */     }
/* 1679:2177 */     this.loadingScreen.displayProgressMessage(I18n.format("menu.loadingLevel", new Object[0]));
/* 1680:2179 */     while (!this.theIntegratedServer.serverIsInRunLoop())
/* 1681:     */     {
/* 1682:2181 */       String var6 = this.theIntegratedServer.getUserMessage();
/* 1683:2183 */       if (var6 != null) {
/* 1684:2185 */         this.loadingScreen.resetProgresAndWorkingMessage(I18n.format(var6, new Object[0]));
/* 1685:     */       } else {
/* 1686:2189 */         this.loadingScreen.resetProgresAndWorkingMessage("");
/* 1687:     */       }
/* 1688:     */       try
/* 1689:     */       {
/* 1690:2194 */         Thread.sleep(200L);
/* 1691:     */       }
/* 1692:     */       catch (InterruptedException localInterruptedException) {}
/* 1693:     */     }
/* 1694:2202 */     displayGuiScreen(null);
/* 1695:2203 */     SocketAddress var11 = this.theIntegratedServer.func_147137_ag().addLocalEndpoint();
/* 1696:2204 */     NetworkManager var12 = NetworkManager.provideLocalClient(var11);
/* 1697:2205 */     var12.setNetHandler(new NetHandlerLoginClient(var12, this, null));
/* 1698:     */     
/* 1699:2207 */     var12.scheduleOutboundPacket(new C00Handshake(Nodus.theNodus.getMinecraftVersion(), var11.toString(), 0, EnumConnectionState.LOGIN), new GenericFutureListener[0]);
/* 1700:2208 */     var12.scheduleOutboundPacket(new C00PacketLoginStart(getSession().func_148256_e()), new GenericFutureListener[0]);
/* 1701:2209 */     this.myNetworkManager = var12;
/* 1702:     */   }
/* 1703:     */   
/* 1704:     */   public void loadWorld(WorldClient par1WorldClient)
/* 1705:     */   {
/* 1706:2217 */     loadWorld(par1WorldClient, "");
/* 1707:     */   }
/* 1708:     */   
/* 1709:     */   public void loadWorld(WorldClient par1WorldClient, String par2Str)
/* 1710:     */   {
/* 1711:2225 */     if (par1WorldClient == null)
/* 1712:     */     {
/* 1713:2227 */       NetHandlerPlayClient var3 = getNetHandler();
/* 1714:2229 */       if (var3 != null) {
/* 1715:2231 */         var3.cleanup();
/* 1716:     */       }
/* 1717:2234 */       if (this.theIntegratedServer != null) {
/* 1718:2236 */         this.theIntegratedServer.initiateShutdown();
/* 1719:     */       }
/* 1720:2239 */       this.theIntegratedServer = null;
/* 1721:2240 */       this.guiAchievement.func_146257_b();
/* 1722:2241 */       this.entityRenderer.getMapItemRenderer().func_148249_a();
/* 1723:     */     }
/* 1724:2244 */     this.renderViewEntity = null;
/* 1725:2245 */     this.myNetworkManager = null;
/* 1726:2247 */     if (this.loadingScreen != null)
/* 1727:     */     {
/* 1728:2249 */       this.loadingScreen.resetProgressAndMessage(par2Str);
/* 1729:2250 */       this.loadingScreen.resetProgresAndWorkingMessage("");
/* 1730:     */     }
/* 1731:2253 */     if ((par1WorldClient == null) && (this.theWorld != null))
/* 1732:     */     {
/* 1733:2255 */       if (this.mcResourcePackRepository.func_148530_e() != null) {
/* 1734:2257 */         scheduleResourcesRefresh();
/* 1735:     */       }
/* 1736:2260 */       this.mcResourcePackRepository.func_148529_f();
/* 1737:2261 */       setServerData(null);
/* 1738:2262 */       this.integratedServerIsRunning = false;
/* 1739:     */     }
/* 1740:2265 */     this.mcSoundHandler.func_147690_c();
/* 1741:2266 */     this.theWorld = par1WorldClient;
/* 1742:2268 */     if (par1WorldClient != null)
/* 1743:     */     {
/* 1744:2270 */       if (this.renderGlobal != null) {
/* 1745:2272 */         this.renderGlobal.setWorldAndLoadRenderers(par1WorldClient);
/* 1746:     */       }
/* 1747:2275 */       if (this.effectRenderer != null) {
/* 1748:2277 */         this.effectRenderer.clearEffects(par1WorldClient);
/* 1749:     */       }
/* 1750:2280 */       if (this.thePlayer == null)
/* 1751:     */       {
/* 1752:2282 */         this.thePlayer = this.playerController.func_147493_a(par1WorldClient, new StatFileWriter());
/* 1753:2283 */         this.playerController.flipPlayer(this.thePlayer);
/* 1754:     */       }
/* 1755:2286 */       this.thePlayer.preparePlayerToSpawn();
/* 1756:2287 */       par1WorldClient.spawnEntityInWorld(this.thePlayer);
/* 1757:2288 */       this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
/* 1758:2289 */       this.playerController.setPlayerCapabilities(this.thePlayer);
/* 1759:2290 */       this.renderViewEntity = this.thePlayer;
/* 1760:     */     }
/* 1761:     */     else
/* 1762:     */     {
/* 1763:2294 */       this.saveLoader.flushCache();
/* 1764:2295 */       this.thePlayer = null;
/* 1765:     */     }
/* 1766:2298 */     System.gc();
/* 1767:2299 */     this.systemTime = 0L;
/* 1768:     */   }
/* 1769:     */   
/* 1770:     */   public String debugInfoRenders()
/* 1771:     */   {
/* 1772:2307 */     return this.renderGlobal.getDebugInfoRenders();
/* 1773:     */   }
/* 1774:     */   
/* 1775:     */   public String getEntityDebug()
/* 1776:     */   {
/* 1777:2315 */     return this.renderGlobal.getDebugInfoEntities();
/* 1778:     */   }
/* 1779:     */   
/* 1780:     */   public String getWorldProviderName()
/* 1781:     */   {
/* 1782:2323 */     return this.theWorld.getProviderName();
/* 1783:     */   }
/* 1784:     */   
/* 1785:     */   public String debugInfoEntities()
/* 1786:     */   {
/* 1787:2331 */     return "P: " + this.effectRenderer.getStatistics() + ". T: " + this.theWorld.getDebugLoadedEntities();
/* 1788:     */   }
/* 1789:     */   
/* 1790:     */   public void setDimensionAndSpawnPlayer(int par1)
/* 1791:     */   {
/* 1792:2336 */     this.theWorld.setSpawnLocation();
/* 1793:2337 */     this.theWorld.removeAllEntities();
/* 1794:2338 */     int var2 = 0;
/* 1795:2339 */     String var3 = null;
/* 1796:2341 */     if (this.thePlayer != null)
/* 1797:     */     {
/* 1798:2343 */       var2 = this.thePlayer.getEntityId();
/* 1799:2344 */       this.theWorld.removeEntity(this.thePlayer);
/* 1800:2345 */       var3 = this.thePlayer.func_142021_k();
/* 1801:     */     }
/* 1802:2348 */     this.renderViewEntity = null;
/* 1803:2349 */     this.thePlayer = this.playerController.func_147493_a(this.theWorld, this.thePlayer == null ? new StatFileWriter() : this.thePlayer.func_146107_m());
/* 1804:2350 */     this.thePlayer.dimension = par1;
/* 1805:2351 */     this.renderViewEntity = this.thePlayer;
/* 1806:2352 */     this.thePlayer.preparePlayerToSpawn();
/* 1807:2353 */     this.thePlayer.func_142020_c(var3);
/* 1808:2354 */     this.theWorld.spawnEntityInWorld(this.thePlayer);
/* 1809:2355 */     this.playerController.flipPlayer(this.thePlayer);
/* 1810:2356 */     this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
/* 1811:2357 */     this.thePlayer.setEntityId(var2);
/* 1812:2358 */     this.playerController.setPlayerCapabilities(this.thePlayer);
/* 1813:2360 */     if ((this.currentScreen instanceof GuiGameOver)) {
/* 1814:2362 */       displayGuiScreen(null);
/* 1815:     */     }
/* 1816:     */   }
/* 1817:     */   
/* 1818:     */   public final boolean isDemo()
/* 1819:     */   {
/* 1820:2371 */     return this.isDemo;
/* 1821:     */   }
/* 1822:     */   
/* 1823:     */   public NetHandlerPlayClient getNetHandler()
/* 1824:     */   {
/* 1825:2376 */     return this.thePlayer != null ? this.thePlayer.sendQueue : null;
/* 1826:     */   }
/* 1827:     */   
/* 1828:     */   public static boolean isGuiEnabled()
/* 1829:     */   {
/* 1830:2381 */     return (theMinecraft == null) || (!theMinecraft.gameSettings.hideGUI);
/* 1831:     */   }
/* 1832:     */   
/* 1833:     */   public static boolean isFancyGraphicsEnabled()
/* 1834:     */   {
/* 1835:2386 */     return (theMinecraft != null) && (theMinecraft.gameSettings.fancyGraphics);
/* 1836:     */   }
/* 1837:     */   
/* 1838:     */   public static boolean isAmbientOcclusionEnabled()
/* 1839:     */   {
/* 1840:2394 */     return (theMinecraft != null) && (theMinecraft.gameSettings.ambientOcclusion != 0);
/* 1841:     */   }
/* 1842:     */   
/* 1843:     */   private void func_147112_ai()
/* 1844:     */   {
/* 1845:2399 */     if (this.objectMouseOver != null)
/* 1846:     */     {
/* 1847:2401 */       boolean var1 = this.thePlayer.capabilities.isCreativeMode;
/* 1848:2402 */       int var3 = 0;
/* 1849:2403 */       boolean var4 = false;
/* 1850:     */       Item var2;
/* 1851:2407 */       if (this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
/* 1852:     */       {
/* 1853:2409 */         int var5 = this.objectMouseOver.blockX;
/* 1854:2410 */         int var6 = this.objectMouseOver.blockY;
/* 1855:2411 */         int var7 = this.objectMouseOver.blockZ;
/* 1856:2412 */         Block var8 = this.theWorld.getBlock(var5, var6, var7);
/* 1857:2414 */         if (var8.getMaterial() == Material.air) {
/* 1858:2416 */           return;
/* 1859:     */         }
/* 1860:2419 */         Item var2 = var8.getItem(this.theWorld, var5, var6, var7);
/* 1861:2421 */         if (var2 == null) {
/* 1862:2423 */           return;
/* 1863:     */         }
/* 1864:2426 */         var4 = var2.getHasSubtypes();
/* 1865:2427 */         Block var9 = ((var2 instanceof ItemBlock)) && (!var8.isFlowerPot()) ? Block.getBlockFromItem(var2) : var8;
/* 1866:2428 */         var3 = var9.getDamageValue(this.theWorld, var5, var6, var7);
/* 1867:     */       }
/* 1868:     */       else
/* 1869:     */       {
/* 1870:2432 */         if ((this.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY) || (this.objectMouseOver.entityHit == null) || (!var1)) {
/* 1871:     */           return;
/* 1872:     */         }
/* 1873:     */         Item var2;
/* 1874:2437 */         if ((this.objectMouseOver.entityHit instanceof EntityPainting))
/* 1875:     */         {
/* 1876:2439 */           var2 = Items.painting;
/* 1877:     */         }
/* 1878:     */         else
/* 1879:     */         {
/* 1880:     */           Item var2;
/* 1881:2441 */           if ((this.objectMouseOver.entityHit instanceof EntityLeashKnot))
/* 1882:     */           {
/* 1883:2443 */             var2 = Items.lead;
/* 1884:     */           }
/* 1885:2445 */           else if ((this.objectMouseOver.entityHit instanceof EntityItemFrame))
/* 1886:     */           {
/* 1887:2447 */             EntityItemFrame var10 = (EntityItemFrame)this.objectMouseOver.entityHit;
/* 1888:2448 */             ItemStack var12 = var10.getDisplayedItem();
/* 1889:     */             Item var2;
/* 1890:2450 */             if (var12 == null)
/* 1891:     */             {
/* 1892:2452 */               var2 = Items.item_frame;
/* 1893:     */             }
/* 1894:     */             else
/* 1895:     */             {
/* 1896:2456 */               Item var2 = var12.getItem();
/* 1897:2457 */               var3 = var12.getItemDamage();
/* 1898:2458 */               var4 = true;
/* 1899:     */             }
/* 1900:     */           }
/* 1901:     */           else
/* 1902:     */           {
/* 1903:     */             Item var2;
/* 1904:2461 */             if ((this.objectMouseOver.entityHit instanceof EntityMinecart))
/* 1905:     */             {
/* 1906:2463 */               EntityMinecart var11 = (EntityMinecart)this.objectMouseOver.entityHit;
/* 1907:     */               Item var2;
/* 1908:2465 */               if (var11.getMinecartType() == 2)
/* 1909:     */               {
/* 1910:2467 */                 var2 = Items.furnace_minecart;
/* 1911:     */               }
/* 1912:     */               else
/* 1913:     */               {
/* 1914:     */                 Item var2;
/* 1915:2469 */                 if (var11.getMinecartType() == 1)
/* 1916:     */                 {
/* 1917:2471 */                   var2 = Items.chest_minecart;
/* 1918:     */                 }
/* 1919:     */                 else
/* 1920:     */                 {
/* 1921:     */                   Item var2;
/* 1922:2473 */                   if (var11.getMinecartType() == 3)
/* 1923:     */                   {
/* 1924:2475 */                     var2 = Items.tnt_minecart;
/* 1925:     */                   }
/* 1926:     */                   else
/* 1927:     */                   {
/* 1928:     */                     Item var2;
/* 1929:2477 */                     if (var11.getMinecartType() == 5)
/* 1930:     */                     {
/* 1931:2479 */                       var2 = Items.hopper_minecart;
/* 1932:     */                     }
/* 1933:     */                     else
/* 1934:     */                     {
/* 1935:     */                       Item var2;
/* 1936:2481 */                       if (var11.getMinecartType() == 6) {
/* 1937:2483 */                         var2 = Items.command_block_minecart;
/* 1938:     */                       } else {
/* 1939:2487 */                         var2 = Items.minecart;
/* 1940:     */                       }
/* 1941:     */                     }
/* 1942:     */                   }
/* 1943:     */                 }
/* 1944:     */               }
/* 1945:     */             }
/* 1946:     */             else
/* 1947:     */             {
/* 1948:     */               Item var2;
/* 1949:2490 */               if ((this.objectMouseOver.entityHit instanceof EntityBoat))
/* 1950:     */               {
/* 1951:2492 */                 var2 = Items.boat;
/* 1952:     */               }
/* 1953:     */               else
/* 1954:     */               {
/* 1955:2496 */                 var2 = Items.spawn_egg;
/* 1956:2497 */                 var3 = EntityList.getEntityID(this.objectMouseOver.entityHit);
/* 1957:2498 */                 var4 = true;
/* 1958:2500 */                 if ((var3 <= 0) || (!EntityList.entityEggs.containsKey(Integer.valueOf(var3)))) {
/* 1959:2502 */                   return;
/* 1960:     */                 }
/* 1961:     */               }
/* 1962:     */             }
/* 1963:     */           }
/* 1964:     */         }
/* 1965:     */       }
/* 1966:2507 */       this.thePlayer.inventory.func_146030_a(var2, var3, var4, var1);
/* 1967:2509 */       if (var1)
/* 1968:     */       {
/* 1969:2511 */         int var5 = this.thePlayer.inventoryContainer.inventorySlots.size() - 9 + this.thePlayer.inventory.currentItem;
/* 1970:2512 */         this.playerController.sendSlotPacket(this.thePlayer.inventory.getStackInSlot(this.thePlayer.inventory.currentItem), var5);
/* 1971:     */       }
/* 1972:     */     }
/* 1973:     */   }
/* 1974:     */   
/* 1975:     */   public CrashReport addGraphicsAndWorldToCrashReport(CrashReport par1CrashReport)
/* 1976:     */   {
/* 1977:2522 */     par1CrashReport.getCategory().addCrashSectionCallable("Launched Version", new Callable()
/* 1978:     */     {
/* 1979:     */       private static final String __OBFID = "CL_00000643";
/* 1980:     */       
/* 1981:     */       public String call()
/* 1982:     */       {
/* 1983:2527 */         return Minecraft.this.launchedVersion;
/* 1984:     */       }
/* 1985:2529 */     });
/* 1986:2530 */     par1CrashReport.getCategory().addCrashSectionCallable("LWJGL", new Callable()
/* 1987:     */     {
/* 1988:     */       private static final String __OBFID = "CL_00000644";
/* 1989:     */       
/* 1990:     */       public String call()
/* 1991:     */       {
/* 1992:2535 */         return Sys.getVersion();
/* 1993:     */       }
/* 1994:2537 */     });
/* 1995:2538 */     par1CrashReport.getCategory().addCrashSectionCallable("OpenGL", new Callable()
/* 1996:     */     {
/* 1997:     */       private static final String __OBFID = "CL_00000645";
/* 1998:     */       
/* 1999:     */       public String call()
/* 2000:     */       {
/* 2001:2543 */         return GL11.glGetString(7937) + " GL version " + GL11.glGetString(7938) + ", " + GL11.glGetString(7936);
/* 2002:     */       }
/* 2003:2545 */     });
/* 2004:2546 */     par1CrashReport.getCategory().addCrashSectionCallable("Is Modded", new Callable()
/* 2005:     */     {
/* 2006:     */       private static final String __OBFID = "CL_00000646";
/* 2007:     */       
/* 2008:     */       public String call()
/* 2009:     */       {
/* 2010:2551 */         String var1 = ClientBrandRetriever.getClientModName();
/* 2011:2552 */         return Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : !var1.equals("vanilla") ? "Definitely; Client brand changed to '" + var1 + "'" : "Probably not. Jar signature remains and client brand is untouched.";
/* 2012:     */       }
/* 2013:2554 */     });
/* 2014:2555 */     par1CrashReport.getCategory().addCrashSectionCallable("Type", new Callable()
/* 2015:     */     {
/* 2016:     */       private static final String __OBFID = "CL_00000647";
/* 2017:     */       
/* 2018:     */       public String call()
/* 2019:     */       {
/* 2020:2560 */         return "Client (map_client.txt)";
/* 2021:     */       }
/* 2022:2562 */     });
/* 2023:2563 */     par1CrashReport.getCategory().addCrashSectionCallable("Resource Packs", new Callable()
/* 2024:     */     {
/* 2025:     */       private static final String __OBFID = "CL_00000633";
/* 2026:     */       
/* 2027:     */       public String call()
/* 2028:     */       {
/* 2029:2568 */         return Minecraft.this.gameSettings.resourcePacks.toString();
/* 2030:     */       }
/* 2031:2570 */     });
/* 2032:2571 */     par1CrashReport.getCategory().addCrashSectionCallable("Current Language", new Callable()
/* 2033:     */     {
/* 2034:     */       private static final String __OBFID = "CL_00000634";
/* 2035:     */       
/* 2036:     */       public String call()
/* 2037:     */       {
/* 2038:2576 */         return Minecraft.this.mcLanguageManager.getCurrentLanguage().toString();
/* 2039:     */       }
/* 2040:2578 */     });
/* 2041:2579 */     par1CrashReport.getCategory().addCrashSectionCallable("Profiler Position", new Callable()
/* 2042:     */     {
/* 2043:     */       private static final String __OBFID = "CL_00000635";
/* 2044:     */       
/* 2045:     */       public String call()
/* 2046:     */       {
/* 2047:2584 */         return Minecraft.this.mcProfiler.profilingEnabled ? Minecraft.this.mcProfiler.getNameOfLastSection() : "N/A (disabled)";
/* 2048:     */       }
/* 2049:2586 */     });
/* 2050:2587 */     par1CrashReport.getCategory().addCrashSectionCallable("Vec3 Pool Size", new Callable()
/* 2051:     */     {
/* 2052:     */       private static final String __OBFID = "CL_00000636";
/* 2053:     */       
/* 2054:     */       public String call()
/* 2055:     */       {
/* 2056:2592 */         int var1 = Minecraft.this.theWorld.getWorldVec3Pool().getPoolSize();
/* 2057:2593 */         int var2 = 56 * var1;
/* 2058:2594 */         int var3 = var2 / 1024 / 1024;
/* 2059:2595 */         int var4 = Minecraft.this.theWorld.getWorldVec3Pool().getNextFreeSpace();
/* 2060:2596 */         int var5 = 56 * var4;
/* 2061:2597 */         int var6 = var5 / 1024 / 1024;
/* 2062:2598 */         return var1 + " (" + var2 + " bytes; " + var3 + " MB) allocated, " + var4 + " (" + var5 + " bytes; " + var6 + " MB) used";
/* 2063:     */       }
/* 2064:2600 */     });
/* 2065:2601 */     par1CrashReport.getCategory().addCrashSectionCallable("Anisotropic Filtering", new Callable()
/* 2066:     */     {
/* 2067:     */       private static final String __OBFID = "CL_00000637";
/* 2068:     */       
/* 2069:     */       public String call()
/* 2070:     */       {
/* 2071:2606 */         return "On (" + Minecraft.this.gameSettings.anisotropicFiltering + ")";
/* 2072:     */       }
/* 2073:     */     });
/* 2074:2610 */     if (this.theWorld != null) {
/* 2075:2612 */       this.theWorld.addWorldInfoToCrashReport(par1CrashReport);
/* 2076:     */     }
/* 2077:2615 */     return par1CrashReport;
/* 2078:     */   }
/* 2079:     */   
/* 2080:     */   public static Minecraft getMinecraft()
/* 2081:     */   {
/* 2082:2623 */     return theMinecraft;
/* 2083:     */   }
/* 2084:     */   
/* 2085:     */   public void scheduleResourcesRefresh()
/* 2086:     */   {
/* 2087:2628 */     this.refreshTexturePacksScheduled = true;
/* 2088:     */   }
/* 2089:     */   
/* 2090:     */   public void addServerStatsToSnooper(PlayerUsageSnooper par1PlayerUsageSnooper)
/* 2091:     */   {
/* 2092:2633 */     par1PlayerUsageSnooper.addData("fps", Integer.valueOf(debugFPS));
/* 2093:2634 */     par1PlayerUsageSnooper.addData("vsync_enabled", Boolean.valueOf(this.gameSettings.enableVsync));
/* 2094:2635 */     par1PlayerUsageSnooper.addData("display_frequency", Integer.valueOf(Display.getDisplayMode().getFrequency()));
/* 2095:2636 */     par1PlayerUsageSnooper.addData("display_type", this.fullscreen ? "fullscreen" : "windowed");
/* 2096:2637 */     par1PlayerUsageSnooper.addData("run_time", Long.valueOf((MinecraftServer.getSystemTimeMillis() - par1PlayerUsageSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L));
/* 2097:2638 */     par1PlayerUsageSnooper.addData("resource_packs", Integer.valueOf(this.mcResourcePackRepository.getRepositoryEntries().size()));
/* 2098:2639 */     int var2 = 0;
/* 2099:2640 */     Iterator var3 = this.mcResourcePackRepository.getRepositoryEntries().iterator();
/* 2100:2642 */     while (var3.hasNext())
/* 2101:     */     {
/* 2102:2644 */       ResourcePackRepository.Entry var4 = (ResourcePackRepository.Entry)var3.next();
/* 2103:2645 */       par1PlayerUsageSnooper.addData("resource_pack[" + var2++ + "]", var4.getResourcePackName());
/* 2104:     */     }
/* 2105:2648 */     if ((this.theIntegratedServer != null) && (this.theIntegratedServer.getPlayerUsageSnooper() != null)) {
/* 2106:2650 */       par1PlayerUsageSnooper.addData("snooper_partner", this.theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
/* 2107:     */     }
/* 2108:     */   }
/* 2109:     */   
/* 2110:     */   public void addServerTypeToSnooper(PlayerUsageSnooper par1PlayerUsageSnooper)
/* 2111:     */   {
/* 2112:2656 */     par1PlayerUsageSnooper.addData("opengl_version", GL11.glGetString(7938));
/* 2113:2657 */     par1PlayerUsageSnooper.addData("opengl_vendor", GL11.glGetString(7936));
/* 2114:2658 */     par1PlayerUsageSnooper.addData("client_brand", ClientBrandRetriever.getClientModName());
/* 2115:2659 */     par1PlayerUsageSnooper.addData("launched_version", this.launchedVersion);
/* 2116:2660 */     ContextCapabilities var2 = GLContext.getCapabilities();
/* 2117:2661 */     par1PlayerUsageSnooper.addData("gl_caps[ARB_multitexture]", Boolean.valueOf(var2.GL_ARB_multitexture));
/* 2118:2662 */     par1PlayerUsageSnooper.addData("gl_caps[ARB_multisample]", Boolean.valueOf(var2.GL_ARB_multisample));
/* 2119:2663 */     par1PlayerUsageSnooper.addData("gl_caps[ARB_texture_cube_map]", Boolean.valueOf(var2.GL_ARB_texture_cube_map));
/* 2120:2664 */     par1PlayerUsageSnooper.addData("gl_caps[ARB_vertex_blend]", Boolean.valueOf(var2.GL_ARB_vertex_blend));
/* 2121:2665 */     par1PlayerUsageSnooper.addData("gl_caps[ARB_matrix_palette]", Boolean.valueOf(var2.GL_ARB_matrix_palette));
/* 2122:2666 */     par1PlayerUsageSnooper.addData("gl_caps[ARB_vertex_program]", Boolean.valueOf(var2.GL_ARB_vertex_program));
/* 2123:2667 */     par1PlayerUsageSnooper.addData("gl_caps[ARB_vertex_shader]", Boolean.valueOf(var2.GL_ARB_vertex_shader));
/* 2124:2668 */     par1PlayerUsageSnooper.addData("gl_caps[ARB_fragment_program]", Boolean.valueOf(var2.GL_ARB_fragment_program));
/* 2125:2669 */     par1PlayerUsageSnooper.addData("gl_caps[ARB_fragment_shader]", Boolean.valueOf(var2.GL_ARB_fragment_shader));
/* 2126:2670 */     par1PlayerUsageSnooper.addData("gl_caps[ARB_shader_objects]", Boolean.valueOf(var2.GL_ARB_shader_objects));
/* 2127:2671 */     par1PlayerUsageSnooper.addData("gl_caps[ARB_vertex_buffer_object]", Boolean.valueOf(var2.GL_ARB_vertex_buffer_object));
/* 2128:2672 */     par1PlayerUsageSnooper.addData("gl_caps[ARB_framebuffer_object]", Boolean.valueOf(var2.GL_ARB_framebuffer_object));
/* 2129:2673 */     par1PlayerUsageSnooper.addData("gl_caps[ARB_pixel_buffer_object]", Boolean.valueOf(var2.GL_ARB_pixel_buffer_object));
/* 2130:2674 */     par1PlayerUsageSnooper.addData("gl_caps[ARB_uniform_buffer_object]", Boolean.valueOf(var2.GL_ARB_uniform_buffer_object));
/* 2131:2675 */     par1PlayerUsageSnooper.addData("gl_caps[ARB_texture_non_power_of_two]", Boolean.valueOf(var2.GL_ARB_texture_non_power_of_two));
/* 2132:2676 */     par1PlayerUsageSnooper.addData("gl_caps[gl_max_vertex_uniforms]", Integer.valueOf(GL11.glGetInteger(35658)));
/* 2133:2677 */     par1PlayerUsageSnooper.addData("gl_caps[gl_max_fragment_uniforms]", Integer.valueOf(GL11.glGetInteger(35657)));
/* 2134:2678 */     par1PlayerUsageSnooper.addData("gl_max_texture_size", Integer.valueOf(getGLMaximumTextureSize()));
/* 2135:     */   }
/* 2136:     */   
/* 2137:     */   public static int getGLMaximumTextureSize()
/* 2138:     */   {
/* 2139:2686 */     for (int var0 = 16384; var0 > 0; var0 >>= 1)
/* 2140:     */     {
/* 2141:2688 */       GL11.glTexImage2D(32868, 0, 6408, var0, var0, 0, 6408, 5121, null);
/* 2142:2689 */       int var1 = GL11.glGetTexLevelParameteri(32868, 0, 4096);
/* 2143:2691 */       if (var1 != 0) {
/* 2144:2693 */         return var0;
/* 2145:     */       }
/* 2146:     */     }
/* 2147:2697 */     return -1;
/* 2148:     */   }
/* 2149:     */   
/* 2150:     */   public boolean isSnooperEnabled()
/* 2151:     */   {
/* 2152:2705 */     return this.gameSettings.snooperEnabled;
/* 2153:     */   }
/* 2154:     */   
/* 2155:     */   public void setServerData(ServerData par1ServerData)
/* 2156:     */   {
/* 2157:2713 */     this.currentServerData = par1ServerData;
/* 2158:     */   }
/* 2159:     */   
/* 2160:     */   public ServerData func_147104_D()
/* 2161:     */   {
/* 2162:2718 */     return this.currentServerData;
/* 2163:     */   }
/* 2164:     */   
/* 2165:     */   public boolean isIntegratedServerRunning()
/* 2166:     */   {
/* 2167:2723 */     return this.integratedServerIsRunning;
/* 2168:     */   }
/* 2169:     */   
/* 2170:     */   public boolean isSingleplayer()
/* 2171:     */   {
/* 2172:2731 */     return (this.integratedServerIsRunning) && (this.theIntegratedServer != null);
/* 2173:     */   }
/* 2174:     */   
/* 2175:     */   public IntegratedServer getIntegratedServer()
/* 2176:     */   {
/* 2177:2739 */     return this.theIntegratedServer;
/* 2178:     */   }
/* 2179:     */   
/* 2180:     */   public static void stopIntegratedServer()
/* 2181:     */   {
/* 2182:2744 */     if (theMinecraft != null)
/* 2183:     */     {
/* 2184:2746 */       IntegratedServer var0 = theMinecraft.getIntegratedServer();
/* 2185:2748 */       if (var0 != null) {
/* 2186:2750 */         var0.stopServer();
/* 2187:     */       }
/* 2188:     */     }
/* 2189:     */   }
/* 2190:     */   
/* 2191:     */   public PlayerUsageSnooper getPlayerUsageSnooper()
/* 2192:     */   {
/* 2193:2760 */     return this.usageSnooper;
/* 2194:     */   }
/* 2195:     */   
/* 2196:     */   public static long getSystemTime()
/* 2197:     */   {
/* 2198:2768 */     return Sys.getTime() * 1000L / Sys.getTimerResolution();
/* 2199:     */   }
/* 2200:     */   
/* 2201:     */   public boolean isFullScreen()
/* 2202:     */   {
/* 2203:2776 */     return this.fullscreen;
/* 2204:     */   }
/* 2205:     */   
/* 2206:     */   public Session getSession()
/* 2207:     */   {
/* 2208:2781 */     return this.session;
/* 2209:     */   }
/* 2210:     */   
/* 2211:     */   public Proxy getProxy()
/* 2212:     */   {
/* 2213:2786 */     return this.proxy;
/* 2214:     */   }
/* 2215:     */   
/* 2216:     */   public TextureManager getTextureManager()
/* 2217:     */   {
/* 2218:2791 */     return this.renderEngine;
/* 2219:     */   }
/* 2220:     */   
/* 2221:     */   public IResourceManager getResourceManager()
/* 2222:     */   {
/* 2223:2796 */     return this.mcResourceManager;
/* 2224:     */   }
/* 2225:     */   
/* 2226:     */   public ResourcePackRepository getResourcePackRepository()
/* 2227:     */   {
/* 2228:2801 */     return this.mcResourcePackRepository;
/* 2229:     */   }
/* 2230:     */   
/* 2231:     */   public LanguageManager getLanguageManager()
/* 2232:     */   {
/* 2233:2806 */     return this.mcLanguageManager;
/* 2234:     */   }
/* 2235:     */   
/* 2236:     */   public TextureMap getTextureMapBlocks()
/* 2237:     */   {
/* 2238:2811 */     return this.textureMapBlocks;
/* 2239:     */   }
/* 2240:     */   
/* 2241:     */   public boolean isJava64bit()
/* 2242:     */   {
/* 2243:2816 */     return this.jvm64bit;
/* 2244:     */   }
/* 2245:     */   
/* 2246:     */   public boolean func_147113_T()
/* 2247:     */   {
/* 2248:2821 */     return this.isGamePaused;
/* 2249:     */   }
/* 2250:     */   
/* 2251:     */   public SoundHandler getSoundHandler()
/* 2252:     */   {
/* 2253:2826 */     return this.mcSoundHandler;
/* 2254:     */   }
/* 2255:     */   
/* 2256:     */   public MusicTicker.MusicType func_147109_W()
/* 2257:     */   {
/* 2258:2831 */     return this.thePlayer != null ? MusicTicker.MusicType.GAME : (this.thePlayer.capabilities.isCreativeMode) && (this.thePlayer.capabilities.allowFlying) ? MusicTicker.MusicType.CREATIVE : (this.thePlayer.worldObj.provider instanceof WorldProviderEnd) ? MusicTicker.MusicType.END : (BossStatus.bossName != null) && (BossStatus.statusBarTime > 0) ? MusicTicker.MusicType.END_BOSS : (this.thePlayer.worldObj.provider instanceof WorldProviderHell) ? MusicTicker.MusicType.NETHER : (this.currentScreen instanceof GuiWinGame) ? MusicTicker.MusicType.CREDITS : MusicTicker.MusicType.MENU;
/* 2259:     */   }
/* 2260:     */   
/* 2261:     */   static final class SwitchMovingObjectType
/* 2262:     */   {
/* 2263:2836 */     static final int[] field_151437_a = new int[MovingObjectPosition.MovingObjectType.values().length];
/* 2264:     */     private static final String __OBFID = "CL_00000638";
/* 2265:     */     
/* 2266:     */     static
/* 2267:     */     {
/* 2268:     */       try
/* 2269:     */       {
/* 2270:2843 */         field_151437_a[MovingObjectPosition.MovingObjectType.ENTITY.ordinal()] = 1;
/* 2271:     */       }
/* 2272:     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/* 2273:     */       try
/* 2274:     */       {
/* 2275:2852 */         field_151437_a[MovingObjectPosition.MovingObjectType.BLOCK.ordinal()] = 2;
/* 2276:     */       }
/* 2277:     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/* 2278:     */     }
/* 2279:     */   }
/* 2280:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.Minecraft
 * JD-Core Version:    0.7.0.1
 */