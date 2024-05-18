/*      */ package net.minecraft.client;
/*      */ 
/*      */ import com.google.common.collect.Iterables;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Multimap;
/*      */ import com.google.common.collect.Queues;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import com.google.common.util.concurrent.ListenableFuture;
/*      */ import com.google.common.util.concurrent.ListenableFutureTask;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*      */ import com.mojang.authlib.properties.Property;
/*      */ import com.mojang.authlib.properties.PropertyMap;
/*      */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.net.Proxy;
/*      */ import java.net.SocketAddress;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Queue;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.Executors;
/*      */ import java.util.concurrent.FutureTask;
/*      */ import javax.imageio.ImageIO;
/*      */ import me.eagler.Client;
/*      */ import me.eagler.module.Module;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.client.audio.MusicTicker;
/*      */ import net.minecraft.client.audio.SoundHandler;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.FontRenderer;
/*      */ import net.minecraft.client.gui.GuiChat;
/*      */ import net.minecraft.client.gui.GuiControls;
/*      */ import net.minecraft.client.gui.GuiGameOver;
/*      */ import net.minecraft.client.gui.GuiIngame;
/*      */ import net.minecraft.client.gui.GuiIngameMenu;
/*      */ import net.minecraft.client.gui.GuiMainMenu;
/*      */ import net.minecraft.client.gui.GuiMemoryErrorScreen;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.GuiSleepMP;
/*      */ import net.minecraft.client.gui.GuiYesNo;
/*      */ import net.minecraft.client.gui.GuiYesNoCallback;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.gui.achievement.GuiAchievement;
/*      */ import net.minecraft.client.gui.inventory.GuiInventory;
/*      */ import net.minecraft.client.gui.stream.GuiStreamUnavailable;
/*      */ import net.minecraft.client.main.GameConfiguration;
/*      */ import net.minecraft.client.multiplayer.GuiConnecting;
/*      */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*      */ import net.minecraft.client.multiplayer.ServerData;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.network.NetHandlerLoginClient;
/*      */ import net.minecraft.client.network.NetHandlerPlayClient;
/*      */ import net.minecraft.client.particle.EffectRenderer;
/*      */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*      */ import net.minecraft.client.renderer.EntityRenderer;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.ItemRenderer;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.RenderGlobal;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*      */ import net.minecraft.client.renderer.entity.RenderItem;
/*      */ import net.minecraft.client.renderer.entity.RenderManager;
/*      */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*      */ import net.minecraft.client.renderer.texture.ITickableTextureObject;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.DefaultResourcePack;
/*      */ import net.minecraft.client.resources.FoliageColorReloadListener;
/*      */ import net.minecraft.client.resources.GrassColorReloadListener;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.client.resources.IReloadableResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*      */ import net.minecraft.client.resources.IResourcePack;
/*      */ import net.minecraft.client.resources.LanguageManager;
/*      */ import net.minecraft.client.resources.ResourceIndex;
/*      */ import net.minecraft.client.resources.ResourcePackRepository;
/*      */ import net.minecraft.client.resources.SimpleReloadableResourceManager;
/*      */ import net.minecraft.client.resources.SkinManager;
/*      */ import net.minecraft.client.resources.data.AnimationMetadataSection;
/*      */ import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.data.FontMetadataSection;
/*      */ import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.data.IMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*      */ import net.minecraft.client.resources.data.LanguageMetadataSection;
/*      */ import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.data.PackMetadataSection;
/*      */ import net.minecraft.client.resources.data.PackMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.data.TextureMetadataSection;
/*      */ import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.model.ModelManager;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.client.settings.KeyBinding;
/*      */ import net.minecraft.client.shader.Framebuffer;
/*      */ import net.minecraft.client.stream.IStream;
/*      */ import net.minecraft.client.stream.NullStream;
/*      */ import net.minecraft.client.stream.TwitchStream;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.boss.BossStatus;
/*      */ import net.minecraft.entity.item.EntityItemFrame;
/*      */ import net.minecraft.entity.item.EntityMinecart;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.InventoryPlayer;
/*      */ import net.minecraft.init.Bootstrap;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemArmorStand;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.nbt.NBTTagString;
/*      */ import net.minecraft.network.EnumConnectionState;
/*      */ import net.minecraft.network.INetHandler;
/*      */ import net.minecraft.network.NetworkManager;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.handshake.client.C00Handshake;
/*      */ import net.minecraft.network.login.client.C00PacketLoginStart;
/*      */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*      */ import net.minecraft.profiler.IPlayerUsage;
/*      */ import net.minecraft.profiler.PlayerUsageSnooper;
/*      */ import net.minecraft.profiler.Profiler;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.server.integrated.IntegratedServer;
/*      */ import net.minecraft.stats.AchievementList;
/*      */ import net.minecraft.stats.IStatStringFormat;
/*      */ import net.minecraft.stats.StatFileWriter;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.FrameTimer;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.IThreadListener;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MinecraftError;
/*      */ import net.minecraft.util.MouseHelper;
/*      */ import net.minecraft.util.MovementInput;
/*      */ import net.minecraft.util.MovementInputFromOptions;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.ScreenShotHelper;
/*      */ import net.minecraft.util.Session;
/*      */ import net.minecraft.util.Timer;
/*      */ import net.minecraft.util.Util;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.chunk.storage.AnvilSaveConverter;
/*      */ import net.minecraft.world.storage.ISaveFormat;
/*      */ import net.minecraft.world.storage.ISaveHandler;
/*      */ import net.minecraft.world.storage.WorldInfo;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ import org.apache.commons.lang3.Validate;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.LWJGLException;
/*      */ import org.lwjgl.Sys;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ import org.lwjgl.input.Mouse;
/*      */ import org.lwjgl.opengl.ContextCapabilities;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.DisplayMode;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import org.lwjgl.opengl.OpenGLException;
/*      */ import org.lwjgl.opengl.PixelFormat;
/*      */ import org.lwjgl.util.glu.GLU;
/*      */ 
/*      */ public class Minecraft implements IThreadListener, IPlayerUsage {
/*  194 */   private static final Logger logger = LogManager.getLogger();
/*  195 */   private static final ResourceLocation locationMojangPng = new ResourceLocation("textures/gui/title/mojang.png");
/*  196 */   public static final boolean isRunningOnMac = (Util.getOSType() == Util.EnumOS.OSX);
/*      */ 
/*      */   
/*  199 */   public static byte[] memoryReserve = new byte[10485760];
/*  200 */   private static final List<DisplayMode> macDisplayModes = Lists.newArrayList((Object[])new DisplayMode[] { new DisplayMode(2560, 1600), new DisplayMode(2880, 1800) });
/*      */   
/*      */   private final File fileResourcepacks;
/*      */   
/*      */   private final PropertyMap twitchDetails;
/*      */   
/*      */   private final PropertyMap field_181038_N;
/*      */   
/*      */   private ServerData currentServerData;
/*      */   
/*      */   private TextureManager renderEngine;
/*      */   
/*      */   private static Minecraft theMinecraft;
/*      */   
/*      */   public PlayerControllerMP playerController;
/*      */   
/*      */   private boolean fullscreen;
/*      */   private boolean enableGLErrorChecking = true;
/*      */   private boolean hasCrashed;
/*      */   private CrashReport crashReporter;
/*      */   public int displayWidth;
/*      */   public int displayHeight;
/*      */   private boolean field_181541_X = false;
/*  223 */   public Timer timer = new Timer(20.0F);
/*      */ 
/*      */   
/*  226 */   private PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("client", this, MinecraftServer.getCurrentTimeMillis());
/*      */   
/*      */   public WorldClient theWorld;
/*      */   
/*      */   public RenderGlobal renderGlobal;
/*      */   
/*      */   private RenderManager renderManager;
/*      */   
/*      */   private RenderItem renderItem;
/*      */   
/*      */   private ItemRenderer itemRenderer;
/*      */   
/*      */   public EntityPlayerSP thePlayer;
/*      */   
/*      */   private Entity renderViewEntity;
/*      */   
/*      */   public Entity pointedEntity;
/*      */   
/*      */   public EffectRenderer effectRenderer;
/*      */   
/*      */   public Session session;
/*      */   
/*      */   private boolean isGamePaused;
/*      */   
/*      */   public FontRenderer fontRendererObj;
/*      */   
/*      */   public FontRenderer standardGalacticFontRenderer;
/*      */   
/*      */   public GuiScreen currentScreen;
/*      */   
/*      */   public LoadingScreenRenderer loadingScreen;
/*      */   
/*      */   public EntityRenderer entityRenderer;
/*      */   
/*      */   private int leftClickCounter;
/*      */   
/*      */   private int tempDisplayWidth;
/*      */   
/*      */   private int tempDisplayHeight;
/*      */   
/*      */   private IntegratedServer theIntegratedServer;
/*      */   
/*      */   public GuiAchievement guiAchievement;
/*      */   
/*      */   public GuiIngame ingameGUI;
/*      */   
/*      */   public boolean skipRenderWorld;
/*      */   
/*      */   public MovingObjectPosition objectMouseOver;
/*      */   
/*      */   public GameSettings gameSettings;
/*      */   
/*      */   public MouseHelper mouseHelper;
/*      */   
/*      */   public final File mcDataDir;
/*      */   
/*      */   private final File fileAssets;
/*      */   
/*      */   private final String launchedVersion;
/*      */   
/*      */   private final Proxy proxy;
/*      */   
/*      */   private ISaveFormat saveLoader;
/*      */   
/*      */   private static int debugFPS;
/*      */   
/*      */   private int rightClickDelayTimer;
/*      */   
/*      */   private String serverName;
/*      */   
/*      */   private int serverPort;
/*      */   public boolean inGameHasFocus;
/*  298 */   long systemTime = getSystemTime();
/*      */   
/*      */   private int joinPlayerCounter;
/*      */   
/*  302 */   public final FrameTimer field_181542_y = new FrameTimer();
/*  303 */   long field_181543_z = System.nanoTime();
/*      */   
/*      */   private final boolean jvm64bit;
/*      */   
/*      */   private final boolean isDemo;
/*      */   private NetworkManager myNetworkManager;
/*      */   private boolean integratedServerIsRunning;
/*  310 */   public final Profiler mcProfiler = new Profiler();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  315 */   private long debugCrashKeyPressTime = -1L;
/*      */   private IReloadableResourceManager mcResourceManager;
/*  317 */   private final IMetadataSerializer metadataSerializer_ = new IMetadataSerializer();
/*  318 */   private final List<IResourcePack> defaultResourcePacks = Lists.newArrayList();
/*      */   private final DefaultResourcePack mcDefaultResourcePack;
/*      */   private ResourcePackRepository mcResourcePackRepository;
/*      */   private LanguageManager mcLanguageManager;
/*      */   private IStream stream;
/*      */   private Framebuffer framebufferMc;
/*      */   private TextureMap textureMapBlocks;
/*      */   private SoundHandler mcSoundHandler;
/*      */   private MusicTicker mcMusicTicker;
/*      */   private ResourceLocation mojangLogo;
/*      */   private final MinecraftSessionService sessionService;
/*      */   private SkinManager skinManager;
/*  330 */   private final Queue<FutureTask<?>> scheduledTasks = Queues.newArrayDeque();
/*  331 */   private long field_175615_aJ = 0L;
/*  332 */   private final Thread mcThread = Thread.currentThread();
/*      */ 
/*      */ 
/*      */   
/*      */   private ModelManager modelManager;
/*      */ 
/*      */ 
/*      */   
/*      */   private BlockRendererDispatcher blockRenderDispatcher;
/*      */ 
/*      */   
/*      */   volatile boolean running = true;
/*      */ 
/*      */   
/*  346 */   public String debug = "";
/*      */   
/*      */   public boolean field_175613_B = false;
/*      */   
/*      */   public boolean field_175614_C = false;
/*      */   public boolean field_175611_D = false;
/*      */   public boolean renderChunksMany = true;
/*  353 */   long debugUpdateTime = getSystemTime();
/*      */   
/*      */   int fpsCounter;
/*      */   
/*  357 */   long prevFrameTime = -1L;
/*      */ 
/*      */   
/*  360 */   private String debugProfilerName = "root";
/*      */ 
/*      */   
/*      */   public Minecraft(GameConfiguration gameConfig) {
/*  364 */     theMinecraft = this;
/*  365 */     this.mcDataDir = gameConfig.folderInfo.mcDataDir;
/*  366 */     this.fileAssets = gameConfig.folderInfo.assetsDir;
/*  367 */     this.fileResourcepacks = gameConfig.folderInfo.resourcePacksDir;
/*  368 */     this.launchedVersion = gameConfig.gameInfo.version;
/*  369 */     this.twitchDetails = gameConfig.userInfo.userProperties;
/*  370 */     this.field_181038_N = gameConfig.userInfo.field_181172_c;
/*  371 */     this.mcDefaultResourcePack = new DefaultResourcePack((new ResourceIndex(gameConfig.folderInfo.assetsDir, gameConfig.folderInfo.assetIndex)).getResourceMap());
/*  372 */     this.proxy = (gameConfig.userInfo.proxy == null) ? Proxy.NO_PROXY : gameConfig.userInfo.proxy;
/*  373 */     this.sessionService = (new YggdrasilAuthenticationService(gameConfig.userInfo.proxy, UUID.randomUUID().toString())).createMinecraftSessionService();
/*  374 */     this.session = gameConfig.userInfo.session;
/*  375 */     logger.info("Setting user: " + this.session.getUsername());
/*  376 */     logger.info("(Session ID is " + this.session.getSessionID() + ")");
/*  377 */     this.isDemo = gameConfig.gameInfo.isDemo;
/*  378 */     this.displayWidth = (gameConfig.displayInfo.width > 0) ? gameConfig.displayInfo.width : 1;
/*  379 */     this.displayHeight = (gameConfig.displayInfo.height > 0) ? gameConfig.displayInfo.height : 1;
/*  380 */     this.tempDisplayWidth = gameConfig.displayInfo.width;
/*  381 */     this.tempDisplayHeight = gameConfig.displayInfo.height;
/*  382 */     this.fullscreen = gameConfig.displayInfo.fullscreen;
/*  383 */     this.jvm64bit = isJvm64bit();
/*  384 */     this.theIntegratedServer = new IntegratedServer(this);
/*      */     
/*  386 */     if (gameConfig.serverInfo.serverName != null) {
/*      */       
/*  388 */       this.serverName = gameConfig.serverInfo.serverName;
/*  389 */       this.serverPort = gameConfig.serverInfo.serverPort;
/*      */     } 
/*      */     
/*  392 */     ImageIO.setUseCache(false);
/*  393 */     Bootstrap.register();
/*      */   }
/*      */ 
/*      */   
/*      */   public void run() {
/*  398 */     this.running = true;
/*      */ 
/*      */     
/*      */     try {
/*  402 */       startGame();
/*      */     }
/*  404 */     catch (Throwable throwable) {
/*      */       
/*  406 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Initializing game");
/*  407 */       crashreport.makeCategory("Initialization");
/*  408 */       displayCrashReport(addGraphicsAndWorldToCrashReport(crashreport));
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  416 */     while (this.running)
/*      */     { 
/*  418 */       try { if (!this.hasCrashed || this.crashReporter == null) {
/*      */ 
/*      */           
/*      */           try {
/*  422 */             runGameLoop();
/*      */           }
/*  424 */           catch (OutOfMemoryError var10) {
/*      */             
/*  426 */             freeMemory();
/*  427 */             displayGuiScreen((GuiScreen)new GuiMemoryErrorScreen());
/*  428 */             System.gc();
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/*  433 */         displayCrashReport(this.crashReporter);
/*      */ 
/*      */         
/*      */         continue; }
/*  437 */       catch (MinecraftError var12)
/*      */       
/*      */       { 
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
/*  459 */         shutdownMinecraftApplet(); } catch (ReportedException reportedexception) { addGraphicsAndWorldToCrashReport(reportedexception.getCrashReport()); freeMemory(); logger.fatal("Reported exception thrown!", (Throwable)reportedexception); displayCrashReport(reportedexception.getCrashReport()); shutdownMinecraftApplet(); } catch (Throwable throwable1) { CrashReport crashreport1 = addGraphicsAndWorldToCrashReport(new CrashReport("Unexpected error", throwable1)); freeMemory(); logger.fatal("Unreported exception thrown!", throwable1); displayCrashReport(crashreport1); } finally { shutdownMinecraftApplet(); }  }  shutdownMinecraftApplet();
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
/*      */   private void startGame() throws LWJGLException, IOException {
/*  472 */     this.gameSettings = new GameSettings(this, this.mcDataDir);
/*  473 */     this.defaultResourcePacks.add(this.mcDefaultResourcePack);
/*  474 */     startTimerHackThread();
/*      */     
/*  476 */     if (this.gameSettings.overrideHeight > 0 && this.gameSettings.overrideWidth > 0) {
/*      */       
/*  478 */       this.displayWidth = this.gameSettings.overrideWidth;
/*  479 */       this.displayHeight = this.gameSettings.overrideHeight;
/*      */     } 
/*      */     
/*  482 */     logger.info("LWJGL Version: " + Sys.getVersion());
/*  483 */     setWindowIcon();
/*  484 */     setInitialDisplayMode();
/*  485 */     createDisplay();
/*  486 */     OpenGlHelper.initializeTextures();
/*  487 */     this.framebufferMc = new Framebuffer(this.displayWidth, this.displayHeight, true);
/*  488 */     this.framebufferMc.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
/*  489 */     registerMetadataSerializers();
/*  490 */     this.mcResourcePackRepository = new ResourcePackRepository(this.fileResourcepacks, new File(this.mcDataDir, "server-resource-packs"), (IResourcePack)this.mcDefaultResourcePack, this.metadataSerializer_, this.gameSettings);
/*  491 */     this.mcResourceManager = (IReloadableResourceManager)new SimpleReloadableResourceManager(this.metadataSerializer_);
/*  492 */     this.mcLanguageManager = new LanguageManager(this.metadataSerializer_, this.gameSettings.language);
/*  493 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.mcLanguageManager);
/*  494 */     refreshResources();
/*  495 */     this.renderEngine = new TextureManager((IResourceManager)this.mcResourceManager);
/*  496 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.renderEngine);
/*  497 */     drawSplashScreen(this.renderEngine);
/*  498 */     initStream();
/*  499 */     this.skinManager = new SkinManager(this.renderEngine, new File(this.fileAssets, "skins"), this.sessionService);
/*  500 */     this.saveLoader = (ISaveFormat)new AnvilSaveConverter(new File(this.mcDataDir, "saves"));
/*  501 */     this.mcSoundHandler = new SoundHandler((IResourceManager)this.mcResourceManager, this.gameSettings);
/*  502 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.mcSoundHandler);
/*  503 */     this.mcMusicTicker = new MusicTicker(this);
/*  504 */     this.fontRendererObj = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii.png"), this.renderEngine, false);
/*      */     
/*  506 */     if (this.gameSettings.language != null) {
/*      */       
/*  508 */       this.fontRendererObj.setUnicodeFlag(isUnicode());
/*  509 */       this.fontRendererObj.setBidiFlag(this.mcLanguageManager.isCurrentLanguageBidirectional());
/*      */     } 
/*      */     
/*  512 */     this.standardGalacticFontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii_sga.png"), this.renderEngine, false);
/*  513 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.fontRendererObj);
/*  514 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.standardGalacticFontRenderer);
/*  515 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)new GrassColorReloadListener());
/*  516 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)new FoliageColorReloadListener());
/*  517 */     AchievementList.openInventory.setStatStringFormatter(new IStatStringFormat()
/*      */         {
/*      */           
/*      */           public String formatString(String p_74535_1_)
/*      */           {
/*      */             try {
/*  523 */               return String.format(p_74535_1_, new Object[] { GameSettings.getKeyDisplayString(this.this$0.gameSettings.keyBindInventory.getKeyCode()) });
/*      */             }
/*  525 */             catch (Exception exception) {
/*      */               
/*  527 */               return "Error: " + exception.getLocalizedMessage();
/*      */             } 
/*      */           }
/*      */         });
/*  531 */     this.mouseHelper = new MouseHelper();
/*  532 */     checkGLError("Pre startup");
/*  533 */     GlStateManager.enableTexture2D();
/*  534 */     GlStateManager.shadeModel(7425);
/*  535 */     GlStateManager.clearDepth(1.0D);
/*  536 */     GlStateManager.enableDepth();
/*  537 */     GlStateManager.depthFunc(515);
/*  538 */     GlStateManager.enableAlpha();
/*  539 */     GlStateManager.alphaFunc(516, 0.1F);
/*  540 */     GlStateManager.cullFace(1029);
/*  541 */     GlStateManager.matrixMode(5889);
/*  542 */     GlStateManager.loadIdentity();
/*  543 */     GlStateManager.matrixMode(5888);
/*  544 */     checkGLError("Startup");
/*  545 */     this.textureMapBlocks = new TextureMap("textures");
/*  546 */     this.textureMapBlocks.setMipmapLevels(this.gameSettings.mipmapLevels);
/*  547 */     this.renderEngine.loadTickableTexture(TextureMap.locationBlocksTexture, (ITickableTextureObject)this.textureMapBlocks);
/*  548 */     this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
/*  549 */     this.textureMapBlocks.setBlurMipmapDirect(false, (this.gameSettings.mipmapLevels > 0));
/*  550 */     this.modelManager = new ModelManager(this.textureMapBlocks);
/*  551 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.modelManager);
/*  552 */     this.renderItem = new RenderItem(this.renderEngine, this.modelManager);
/*  553 */     this.renderManager = new RenderManager(this.renderEngine, this.renderItem);
/*  554 */     this.itemRenderer = new ItemRenderer(this);
/*  555 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.renderItem);
/*  556 */     this.entityRenderer = new EntityRenderer(this, (IResourceManager)this.mcResourceManager);
/*  557 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.entityRenderer);
/*  558 */     this.blockRenderDispatcher = new BlockRendererDispatcher(this.modelManager.getBlockModelShapes(), this.gameSettings);
/*  559 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.blockRenderDispatcher);
/*  560 */     this.renderGlobal = new RenderGlobal(this);
/*  561 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.renderGlobal);
/*  562 */     this.guiAchievement = new GuiAchievement(this);
/*  563 */     GlStateManager.viewport(0, 0, this.displayWidth, this.displayHeight);
/*  564 */     this.effectRenderer = new EffectRenderer((World)this.theWorld, this.renderEngine);
/*  565 */     checkGLError("Post startup");
/*  566 */     this.ingameGUI = new GuiIngame(this);
/*      */     
/*  568 */     if (this.serverName != null) {
/*      */       
/*  570 */       displayGuiScreen((GuiScreen)new GuiConnecting((GuiScreen)new GuiMainMenu(), this, this.serverName, this.serverPort));
/*      */     }
/*      */     else {
/*      */       
/*  574 */       displayGuiScreen((GuiScreen)new GuiMainMenu());
/*      */     } 
/*      */     
/*  577 */     this.renderEngine.deleteTexture(this.mojangLogo);
/*  578 */     this.mojangLogo = null;
/*  579 */     this.loadingScreen = new LoadingScreenRenderer(this);
/*      */     
/*  581 */     if (this.gameSettings.fullScreen && !this.fullscreen)
/*      */     {
/*  583 */       toggleFullscreen();
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  588 */       Display.setVSyncEnabled(this.gameSettings.enableVsync);
/*      */     }
/*  590 */     catch (OpenGLException var2) {
/*      */       
/*  592 */       this.gameSettings.enableVsync = false;
/*  593 */       this.gameSettings.saveOptions();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  598 */     (new Client()).start();
/*      */     
/*  600 */     this.renderGlobal.makeEntityOutlineShader();
/*      */   }
/*      */ 
/*      */   
/*      */   private void registerMetadataSerializers() {
/*  605 */     this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
/*  606 */     this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new FontMetadataSectionSerializer(), FontMetadataSection.class);
/*  607 */     this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
/*  608 */     this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new PackMetadataSectionSerializer(), PackMetadataSection.class);
/*  609 */     this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void initStream() {
/*      */     try {
/*  616 */       this.stream = (IStream)new TwitchStream(this, (Property)Iterables.getFirst(this.twitchDetails.get("twitch_access_token"), null));
/*      */     }
/*  618 */     catch (Throwable throwable) {
/*      */       
/*  620 */       this.stream = (IStream)new NullStream(throwable);
/*  621 */       logger.error("Couldn't initialize twitch stream");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void createDisplay() throws LWJGLException {
/*  627 */     Display.setResizable(true);
/*  628 */     Display.setTitle("Minecraft 1.8.8 | Hera v" + (new Client()).VERSION);
/*      */ 
/*      */     
/*      */     try {
/*  632 */       Display.create((new PixelFormat()).withDepthBits(24));
/*      */     }
/*  634 */     catch (LWJGLException lwjglexception) {
/*      */       
/*  636 */       logger.error("Couldn't set pixel format", (Throwable)lwjglexception);
/*      */ 
/*      */       
/*      */       try {
/*  640 */         Thread.sleep(1000L);
/*      */       }
/*  642 */       catch (InterruptedException interruptedException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  647 */       if (this.fullscreen)
/*      */       {
/*  649 */         updateDisplayMode();
/*      */       }
/*      */       
/*  652 */       Display.create();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setInitialDisplayMode() throws LWJGLException {
/*  658 */     if (this.fullscreen) {
/*      */       
/*  660 */       Display.setFullscreen(true);
/*  661 */       DisplayMode displaymode = Display.getDisplayMode();
/*  662 */       this.displayWidth = Math.max(1, displaymode.getWidth());
/*  663 */       this.displayHeight = Math.max(1, displaymode.getHeight());
/*      */     }
/*      */     else {
/*      */       
/*  667 */       Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setWindowIcon() {
/*  673 */     Util.EnumOS util$enumos = Util.getOSType();
/*      */     
/*  675 */     if (util$enumos != Util.EnumOS.OSX) {
/*      */       
/*  677 */       InputStream inputstream = null;
/*  678 */       InputStream inputstream1 = null;
/*      */ 
/*      */       
/*      */       try {
/*  682 */         inputstream = this.mcDefaultResourcePack.getInputStreamAssets(new ResourceLocation("icons/icon_16x16.png"));
/*  683 */         inputstream1 = this.mcDefaultResourcePack.getInputStreamAssets(new ResourceLocation("icons/icon_32x32.png"));
/*      */         
/*  685 */         if (inputstream != null && inputstream1 != null)
/*      */         {
/*  687 */           Display.setIcon(new ByteBuffer[] { readImageToBuffer(inputstream), readImageToBuffer(inputstream1) });
/*      */         }
/*      */       }
/*  690 */       catch (IOException ioexception) {
/*      */         
/*  692 */         logger.error("Couldn't set icon", ioexception);
/*      */       }
/*      */       finally {
/*      */         
/*  696 */         IOUtils.closeQuietly(inputstream);
/*  697 */         IOUtils.closeQuietly(inputstream1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isJvm64bit() {
/*  704 */     String[] astring = { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" }; byte b; int i;
/*      */     String[] arrayOfString1;
/*  706 */     for (i = (arrayOfString1 = astring).length, b = 0; b < i; ) { String s = arrayOfString1[b];
/*      */       
/*  708 */       String s1 = System.getProperty(s);
/*      */       
/*  710 */       if (s1 != null && s1.contains("64"))
/*      */       {
/*  712 */         return true;
/*      */       }
/*      */       b++; }
/*      */     
/*  716 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public Framebuffer getFramebuffer() {
/*  721 */     return this.framebufferMc;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getVersion() {
/*  726 */     return this.launchedVersion;
/*      */   }
/*      */ 
/*      */   
/*      */   private void startTimerHackThread() {
/*  731 */     Thread thread = new Thread("Timer hack thread")
/*      */       {
/*      */         public void run()
/*      */         {
/*  735 */           while (Minecraft.this.running) {
/*      */ 
/*      */             
/*      */             try {
/*  739 */               Thread.sleep(2147483647L);
/*      */             }
/*  741 */             catch (InterruptedException interruptedException) {}
/*      */           } 
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */     
/*  748 */     thread.setDaemon(true);
/*  749 */     thread.start();
/*      */   }
/*      */ 
/*      */   
/*      */   public void crashed(CrashReport crash) {
/*  754 */     this.hasCrashed = true;
/*  755 */     this.crashReporter = crash;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayCrashReport(CrashReport crashReportIn) {
/*  763 */     File file1 = new File((getMinecraft()).mcDataDir, "crash-reports");
/*  764 */     File file2 = new File(file1, "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-client.txt");
/*  765 */     Bootstrap.printToSYSOUT(crashReportIn.getCompleteReport());
/*      */     
/*  767 */     if (crashReportIn.getFile() != null) {
/*      */       
/*  769 */       Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + crashReportIn.getFile());
/*  770 */       System.exit(-1);
/*      */     }
/*  772 */     else if (crashReportIn.saveToFile(file2)) {
/*      */       
/*  774 */       Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + file2.getAbsolutePath());
/*  775 */       System.exit(-1);
/*      */     }
/*      */     else {
/*      */       
/*  779 */       Bootstrap.printToSYSOUT("#@?@# Game crashed! Crash report could not be saved. #@?@#");
/*  780 */       System.exit(-2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isUnicode() {
/*  786 */     return !(!this.mcLanguageManager.isCurrentLocaleUnicode() && !this.gameSettings.forceUnicodeFont);
/*      */   }
/*      */ 
/*      */   
/*      */   public void refreshResources() {
/*  791 */     List<IResourcePack> list = Lists.newArrayList(this.defaultResourcePacks);
/*      */     
/*  793 */     for (ResourcePackRepository.Entry resourcepackrepository$entry : this.mcResourcePackRepository.getRepositoryEntries())
/*      */     {
/*  795 */       list.add(resourcepackrepository$entry.getResourcePack());
/*      */     }
/*      */     
/*  798 */     if (this.mcResourcePackRepository.getResourcePackInstance() != null)
/*      */     {
/*  800 */       list.add(this.mcResourcePackRepository.getResourcePackInstance());
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  805 */       this.mcResourceManager.reloadResources(list);
/*      */     }
/*  807 */     catch (RuntimeException runtimeexception) {
/*      */       
/*  809 */       logger.info("Caught error stitching, removing all assigned resourcepacks", runtimeexception);
/*  810 */       list.clear();
/*  811 */       list.addAll(this.defaultResourcePacks);
/*  812 */       this.mcResourcePackRepository.setRepositories(Collections.emptyList());
/*  813 */       this.mcResourceManager.reloadResources(list);
/*  814 */       this.gameSettings.resourcePacks.clear();
/*  815 */       this.gameSettings.field_183018_l.clear();
/*  816 */       this.gameSettings.saveOptions();
/*      */     } 
/*      */     
/*  819 */     this.mcLanguageManager.parseLanguageMetadata(list);
/*      */     
/*  821 */     if (this.renderGlobal != null)
/*      */     {
/*  823 */       this.renderGlobal.loadRenderers();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException {
/*  829 */     BufferedImage bufferedimage = ImageIO.read(imageStream);
/*  830 */     int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
/*  831 */     ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length); byte b;
/*      */     int i, arrayOfInt1[];
/*  833 */     for (i = (arrayOfInt1 = aint).length, b = 0; b < i; ) { int j = arrayOfInt1[b];
/*      */       
/*  835 */       bytebuffer.putInt(j << 8 | j >> 24 & 0xFF);
/*      */       b++; }
/*      */     
/*  838 */     bytebuffer.flip();
/*  839 */     return bytebuffer;
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateDisplayMode() throws LWJGLException {
/*  844 */     Set<DisplayMode> set = Sets.newHashSet();
/*  845 */     Collections.addAll(set, Display.getAvailableDisplayModes());
/*  846 */     DisplayMode displaymode = Display.getDesktopDisplayMode();
/*      */     
/*  848 */     if (!set.contains(displaymode) && Util.getOSType() == Util.EnumOS.OSX)
/*      */     {
/*      */ 
/*      */       
/*  852 */       for (DisplayMode displaymode1 : macDisplayModes) {
/*      */         
/*  854 */         boolean flag = true;
/*      */         
/*  856 */         for (DisplayMode displaymode2 : set) {
/*      */           
/*  858 */           if (displaymode2.getBitsPerPixel() == 32 && displaymode2.getWidth() == displaymode1.getWidth() && displaymode2.getHeight() == displaymode1.getHeight()) {
/*      */             
/*  860 */             flag = false;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*  865 */         if (!flag) {
/*      */           
/*  867 */           Iterator<DisplayMode> iterator = set.iterator();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  872 */           while (iterator.hasNext()) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  877 */             DisplayMode displaymode3 = iterator.next();
/*      */             
/*  879 */             if (displaymode3.getBitsPerPixel() == 32 && displaymode3.getWidth() == displaymode1.getWidth() / 2 && displaymode3.getHeight() == displaymode1.getHeight() / 2)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  885 */               displaymode = displaymode3; } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*  890 */     Display.setDisplayMode(displaymode);
/*  891 */     this.displayWidth = displaymode.getWidth();
/*  892 */     this.displayHeight = displaymode.getHeight();
/*      */   }
/*      */ 
/*      */   
/*      */   private void drawSplashScreen(TextureManager textureManagerInstance) throws LWJGLException {
/*  897 */     ScaledResolution scaledresolution = new ScaledResolution(this);
/*  898 */     int i = scaledresolution.getScaleFactor();
/*  899 */     Framebuffer framebuffer = new Framebuffer(scaledresolution.getScaledWidth() * i, scaledresolution.getScaledHeight() * i, true);
/*  900 */     framebuffer.bindFramebuffer(false);
/*  901 */     GlStateManager.matrixMode(5889);
/*  902 */     GlStateManager.loadIdentity();
/*  903 */     GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
/*  904 */     GlStateManager.matrixMode(5888);
/*  905 */     GlStateManager.loadIdentity();
/*  906 */     GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/*  907 */     GlStateManager.disableLighting();
/*  908 */     GlStateManager.disableFog();
/*  909 */     GlStateManager.disableDepth();
/*  910 */     GlStateManager.enableTexture2D();
/*  911 */     InputStream inputstream = null;
/*      */ 
/*      */     
/*      */     try {
/*  915 */       inputstream = this.mcDefaultResourcePack.getInputStream(locationMojangPng);
/*  916 */       this.mojangLogo = textureManagerInstance.getDynamicTextureLocation("logo", new DynamicTexture(ImageIO.read(inputstream)));
/*  917 */       textureManagerInstance.bindTexture(this.mojangLogo);
/*      */     }
/*  919 */     catch (IOException ioexception) {
/*      */       
/*  921 */       logger.error("Unable to load logo: " + locationMojangPng, ioexception);
/*      */     }
/*      */     finally {
/*      */       
/*  925 */       IOUtils.closeQuietly(inputstream);
/*      */     } 
/*      */     
/*  928 */     Tessellator tessellator = Tessellator.getInstance();
/*  929 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  930 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/*  931 */     worldrenderer.pos(0.0D, this.displayHeight, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
/*  932 */     worldrenderer.pos(this.displayWidth, this.displayHeight, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
/*  933 */     worldrenderer.pos(this.displayWidth, 0.0D, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
/*  934 */     worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
/*  935 */     tessellator.draw();
/*  936 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  937 */     int j = 256;
/*  938 */     int k = 256;
/*  939 */     func_181536_a((scaledresolution.getScaledWidth() - j) / 2, (scaledresolution.getScaledHeight() - k) / 2, 0, 0, j, k, 255, 255, 255, 255);
/*  940 */     GlStateManager.disableLighting();
/*  941 */     GlStateManager.disableFog();
/*  942 */     framebuffer.unbindFramebuffer();
/*  943 */     framebuffer.framebufferRender(scaledresolution.getScaledWidth() * i, scaledresolution.getScaledHeight() * i);
/*  944 */     GlStateManager.enableAlpha();
/*  945 */     GlStateManager.alphaFunc(516, 0.1F);
/*  946 */     updateDisplay();
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_181536_a(int p_181536_1_, int p_181536_2_, int p_181536_3_, int p_181536_4_, int p_181536_5_, int p_181536_6_, int p_181536_7_, int p_181536_8_, int p_181536_9_, int p_181536_10_) {
/*  951 */     float f = 0.00390625F;
/*  952 */     float f1 = 0.00390625F;
/*  953 */     WorldRenderer worldrenderer = Tessellator.getInstance().getWorldRenderer();
/*  954 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/*  955 */     worldrenderer.pos(p_181536_1_, (p_181536_2_ + p_181536_6_), 0.0D).tex((p_181536_3_ * f), ((p_181536_4_ + p_181536_6_) * f1)).color(p_181536_7_, p_181536_8_, p_181536_9_, p_181536_10_).endVertex();
/*  956 */     worldrenderer.pos((p_181536_1_ + p_181536_5_), (p_181536_2_ + p_181536_6_), 0.0D).tex(((p_181536_3_ + p_181536_5_) * f), ((p_181536_4_ + p_181536_6_) * f1)).color(p_181536_7_, p_181536_8_, p_181536_9_, p_181536_10_).endVertex();
/*  957 */     worldrenderer.pos((p_181536_1_ + p_181536_5_), p_181536_2_, 0.0D).tex(((p_181536_3_ + p_181536_5_) * f), (p_181536_4_ * f1)).color(p_181536_7_, p_181536_8_, p_181536_9_, p_181536_10_).endVertex();
/*  958 */     worldrenderer.pos(p_181536_1_, p_181536_2_, 0.0D).tex((p_181536_3_ * f), (p_181536_4_ * f1)).color(p_181536_7_, p_181536_8_, p_181536_9_, p_181536_10_).endVertex();
/*  959 */     Tessellator.getInstance().draw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ISaveFormat getSaveLoader() {
/*  967 */     return this.saveLoader;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGuiScreen(GuiScreen guiScreenIn) {
/*      */     GuiMainMenu guiMainMenu;
/*      */     GuiGameOver guiGameOver;
/*  975 */     if (this.currentScreen != null)
/*      */     {
/*  977 */       this.currentScreen.onGuiClosed();
/*      */     }
/*      */     
/*  980 */     if (guiScreenIn == null && this.theWorld == null) {
/*      */       
/*  982 */       guiMainMenu = new GuiMainMenu();
/*      */     }
/*  984 */     else if (guiMainMenu == null && this.thePlayer.getHealth() <= 0.0F) {
/*      */       
/*  986 */       guiGameOver = new GuiGameOver();
/*      */     } 
/*      */     
/*  989 */     if (guiGameOver instanceof GuiMainMenu) {
/*      */       
/*  991 */       this.gameSettings.showDebugInfo = false;
/*  992 */       this.ingameGUI.getChatGUI().clearChatMessages();
/*      */     } 
/*      */     
/*  995 */     this.currentScreen = (GuiScreen)guiGameOver;
/*      */     
/*  997 */     if (guiGameOver != null) {
/*      */       
/*  999 */       setIngameNotInFocus();
/* 1000 */       ScaledResolution scaledresolution = new ScaledResolution(this);
/* 1001 */       int i = scaledresolution.getScaledWidth();
/* 1002 */       int j = scaledresolution.getScaledHeight();
/* 1003 */       guiGameOver.setWorldAndResolution(this, i, j);
/* 1004 */       this.skipRenderWorld = false;
/*      */     }
/*      */     else {
/*      */       
/* 1008 */       this.mcSoundHandler.resumeSounds();
/* 1009 */       setIngameFocus();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkGLError(String message) {
/* 1018 */     if (this.enableGLErrorChecking) {
/*      */       
/* 1020 */       int i = GL11.glGetError();
/*      */       
/* 1022 */       if (i != 0) {
/*      */         
/* 1024 */         String s = GLU.gluErrorString(i);
/* 1025 */         logger.error("########## GL ERROR ##########");
/* 1026 */         logger.error("@ " + message);
/* 1027 */         logger.error(String.valueOf(i) + ": " + s);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void shutdownMinecraftApplet() {
/*      */     try {
/* 1040 */       this.stream.shutdownStream();
/* 1041 */       logger.info("Stopping!");
/*      */ 
/*      */       
/*      */       try {
/* 1045 */         loadWorld(null);
/*      */       }
/* 1047 */       catch (Throwable throwable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1052 */       this.mcSoundHandler.unloadSounds();
/*      */     }
/*      */     finally {
/*      */       
/* 1056 */       Display.destroy();
/*      */       
/* 1058 */       if (!this.hasCrashed)
/*      */       {
/* 1060 */         System.exit(0);
/*      */       }
/*      */     } 
/*      */     
/* 1064 */     System.gc();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void runGameLoop() throws IOException {
/* 1072 */     long i = System.nanoTime();
/* 1073 */     this.mcProfiler.startSection("root");
/*      */     
/* 1075 */     if (Display.isCreated() && Display.isCloseRequested())
/*      */     {
/* 1077 */       shutdown();
/*      */     }
/*      */     
/* 1080 */     if (this.isGamePaused && this.theWorld != null) {
/*      */       
/* 1082 */       float f = this.timer.renderPartialTicks;
/* 1083 */       this.timer.updateTimer();
/* 1084 */       this.timer.renderPartialTicks = f;
/*      */     }
/*      */     else {
/*      */       
/* 1088 */       this.timer.updateTimer();
/*      */     } 
/*      */     
/* 1091 */     this.mcProfiler.startSection("scheduledExecutables");
/*      */     
/* 1093 */     synchronized (this.scheduledTasks) {
/*      */       
/* 1095 */       while (!this.scheduledTasks.isEmpty())
/*      */       {
/* 1097 */         Util.func_181617_a(this.scheduledTasks.poll(), logger);
/*      */       }
/*      */     } 
/*      */     
/* 1101 */     this.mcProfiler.endSection();
/* 1102 */     long l = System.nanoTime();
/* 1103 */     this.mcProfiler.startSection("tick");
/*      */     
/* 1105 */     for (int j = 0; j < this.timer.elapsedTicks; j++)
/*      */     {
/* 1107 */       runTick();
/*      */     }
/*      */     
/* 1110 */     this.mcProfiler.endStartSection("preRenderErrors");
/* 1111 */     long i1 = System.nanoTime() - l;
/* 1112 */     checkGLError("Pre render");
/* 1113 */     this.mcProfiler.endStartSection("sound");
/* 1114 */     this.mcSoundHandler.setListener((EntityPlayer)this.thePlayer, this.timer.renderPartialTicks);
/* 1115 */     this.mcProfiler.endSection();
/* 1116 */     this.mcProfiler.startSection("render");
/* 1117 */     GlStateManager.pushMatrix();
/* 1118 */     GlStateManager.clear(16640);
/* 1119 */     this.framebufferMc.bindFramebuffer(true);
/* 1120 */     this.mcProfiler.startSection("display");
/* 1121 */     GlStateManager.enableTexture2D();
/*      */     
/* 1123 */     if (this.thePlayer != null && this.thePlayer.isEntityInsideOpaqueBlock())
/*      */     {
/* 1125 */       this.gameSettings.thirdPersonView = 0;
/*      */     }
/*      */     
/* 1128 */     this.mcProfiler.endSection();
/*      */     
/* 1130 */     if (!this.skipRenderWorld) {
/*      */       
/* 1132 */       this.mcProfiler.endStartSection("gameRenderer");
/* 1133 */       this.entityRenderer.func_181560_a(this.timer.renderPartialTicks, i);
/* 1134 */       this.mcProfiler.endSection();
/*      */     } 
/*      */     
/* 1137 */     this.mcProfiler.endSection();
/*      */     
/* 1139 */     if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart && !this.gameSettings.hideGUI) {
/*      */       
/* 1141 */       if (!this.mcProfiler.profilingEnabled)
/*      */       {
/* 1143 */         this.mcProfiler.clearProfiling();
/*      */       }
/*      */       
/* 1146 */       this.mcProfiler.profilingEnabled = true;
/* 1147 */       displayDebugInfo(i1);
/*      */     }
/*      */     else {
/*      */       
/* 1151 */       this.mcProfiler.profilingEnabled = false;
/* 1152 */       this.prevFrameTime = System.nanoTime();
/*      */     } 
/*      */     
/* 1155 */     this.guiAchievement.updateAchievementWindow();
/* 1156 */     this.framebufferMc.unbindFramebuffer();
/* 1157 */     GlStateManager.popMatrix();
/* 1158 */     GlStateManager.pushMatrix();
/* 1159 */     this.framebufferMc.framebufferRender(this.displayWidth, this.displayHeight);
/* 1160 */     GlStateManager.popMatrix();
/* 1161 */     GlStateManager.pushMatrix();
/* 1162 */     this.entityRenderer.renderStreamIndicator(this.timer.renderPartialTicks);
/* 1163 */     GlStateManager.popMatrix();
/* 1164 */     this.mcProfiler.startSection("root");
/* 1165 */     updateDisplay();
/* 1166 */     Thread.yield();
/* 1167 */     this.mcProfiler.startSection("stream");
/* 1168 */     this.mcProfiler.startSection("update");
/* 1169 */     this.stream.func_152935_j();
/* 1170 */     this.mcProfiler.endStartSection("submit");
/* 1171 */     this.stream.func_152922_k();
/* 1172 */     this.mcProfiler.endSection();
/* 1173 */     this.mcProfiler.endSection();
/* 1174 */     checkGLError("Post render");
/* 1175 */     this.fpsCounter++;
/* 1176 */     this.isGamePaused = (isSingleplayer() && this.currentScreen != null && this.currentScreen.doesGuiPauseGame() && !this.theIntegratedServer.getPublic());
/* 1177 */     long k = System.nanoTime();
/* 1178 */     this.field_181542_y.func_181747_a(k - this.field_181543_z);
/* 1179 */     this.field_181543_z = k;
/*      */     
/* 1181 */     while (getSystemTime() >= this.debugUpdateTime + 1000L) {
/*      */       
/* 1183 */       debugFPS = this.fpsCounter;
/* 1184 */       this.debug = String.format("%d fps (%d chunk update%s) T: %s%s%s%s%s", new Object[] { Integer.valueOf(debugFPS), Integer.valueOf(RenderChunk.renderChunksUpdated), (RenderChunk.renderChunksUpdated != 1) ? "s" : "", (this.gameSettings.limitFramerate == GameSettings.Options.FRAMERATE_LIMIT.getValueMax()) ? "inf" : Integer.valueOf(this.gameSettings.limitFramerate), this.gameSettings.enableVsync ? " vsync" : "", this.gameSettings.fancyGraphics ? "" : " fast", (this.gameSettings.clouds == 0) ? "" : ((this.gameSettings.clouds == 1) ? " fast-clouds" : " fancy-clouds"), OpenGlHelper.useVbo() ? " vbo" : "" });
/* 1185 */       RenderChunk.renderChunksUpdated = 0;
/* 1186 */       this.debugUpdateTime += 1000L;
/* 1187 */       this.fpsCounter = 0;
/* 1188 */       this.usageSnooper.addMemoryStatsToSnooper();
/*      */       
/* 1190 */       if (!this.usageSnooper.isSnooperRunning())
/*      */       {
/* 1192 */         this.usageSnooper.startSnooper();
/*      */       }
/*      */     } 
/*      */     
/* 1196 */     if (isFramerateLimitBelowMax()) {
/*      */       
/* 1198 */       this.mcProfiler.startSection("fpslimit_wait");
/* 1199 */       Display.sync(getLimitFramerate());
/* 1200 */       this.mcProfiler.endSection();
/*      */     } 
/*      */     
/* 1203 */     this.mcProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateDisplay() {
/* 1208 */     this.mcProfiler.startSection("display_update");
/* 1209 */     Display.update();
/* 1210 */     this.mcProfiler.endSection();
/* 1211 */     checkWindowResize();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkWindowResize() {
/* 1216 */     if (!this.fullscreen && Display.wasResized()) {
/*      */       
/* 1218 */       int i = this.displayWidth;
/* 1219 */       int j = this.displayHeight;
/* 1220 */       this.displayWidth = Display.getWidth();
/* 1221 */       this.displayHeight = Display.getHeight();
/*      */       
/* 1223 */       if (this.displayWidth != i || this.displayHeight != j) {
/*      */         
/* 1225 */         if (this.displayWidth <= 0)
/*      */         {
/* 1227 */           this.displayWidth = 1;
/*      */         }
/*      */         
/* 1230 */         if (this.displayHeight <= 0)
/*      */         {
/* 1232 */           this.displayHeight = 1;
/*      */         }
/*      */         
/* 1235 */         resize(this.displayWidth, this.displayHeight);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLimitFramerate() {
/* 1242 */     return (this.theWorld == null && this.currentScreen != null) ? 30 : this.gameSettings.limitFramerate;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFramerateLimitBelowMax() {
/* 1247 */     return (getLimitFramerate() < GameSettings.Options.FRAMERATE_LIMIT.getValueMax());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void freeMemory() {
/*      */     try {
/* 1254 */       memoryReserve = new byte[0];
/* 1255 */       this.renderGlobal.deleteAllDisplayLists();
/*      */     }
/* 1257 */     catch (Throwable throwable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1264 */       System.gc();
/* 1265 */       loadWorld(null);
/*      */     }
/* 1267 */     catch (Throwable throwable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1272 */     System.gc();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateDebugProfilerName(int keyCount) {
/* 1280 */     List<Profiler.Result> list = this.mcProfiler.getProfilingData(this.debugProfilerName);
/*      */     
/* 1282 */     if (list != null && !list.isEmpty()) {
/*      */       
/* 1284 */       Profiler.Result profiler$result = list.remove(0);
/*      */       
/* 1286 */       if (keyCount == 0) {
/*      */         
/* 1288 */         if (profiler$result.field_76331_c.length() > 0)
/*      */         {
/* 1290 */           int i = this.debugProfilerName.lastIndexOf(".");
/*      */           
/* 1292 */           if (i >= 0)
/*      */           {
/* 1294 */             this.debugProfilerName = this.debugProfilerName.substring(0, i);
/*      */           }
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1300 */         keyCount--;
/*      */         
/* 1302 */         if (keyCount < list.size() && !((Profiler.Result)list.get(keyCount)).field_76331_c.equals("unspecified")) {
/*      */           
/* 1304 */           if (this.debugProfilerName.length() > 0)
/*      */           {
/* 1306 */             this.debugProfilerName = String.valueOf(this.debugProfilerName) + ".";
/*      */           }
/*      */           
/* 1309 */           this.debugProfilerName = String.valueOf(this.debugProfilerName) + ((Profiler.Result)list.get(keyCount)).field_76331_c;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void displayDebugInfo(long elapsedTicksTime) {
/* 1320 */     if (this.mcProfiler.profilingEnabled) {
/*      */       
/* 1322 */       List<Profiler.Result> list = this.mcProfiler.getProfilingData(this.debugProfilerName);
/* 1323 */       Profiler.Result profiler$result = list.remove(0);
/* 1324 */       GlStateManager.clear(256);
/* 1325 */       GlStateManager.matrixMode(5889);
/* 1326 */       GlStateManager.enableColorMaterial();
/* 1327 */       GlStateManager.loadIdentity();
/* 1328 */       GlStateManager.ortho(0.0D, this.displayWidth, this.displayHeight, 0.0D, 1000.0D, 3000.0D);
/* 1329 */       GlStateManager.matrixMode(5888);
/* 1330 */       GlStateManager.loadIdentity();
/* 1331 */       GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/* 1332 */       GL11.glLineWidth(1.0F);
/* 1333 */       GlStateManager.disableTexture2D();
/* 1334 */       Tessellator tessellator = Tessellator.getInstance();
/* 1335 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1336 */       int i = 160;
/* 1337 */       int j = this.displayWidth - i - 10;
/* 1338 */       int k = this.displayHeight - i * 2;
/* 1339 */       GlStateManager.enableBlend();
/* 1340 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 1341 */       worldrenderer.pos((j - i * 1.1F), (k - i * 0.6F - 16.0F), 0.0D).color(200, 0, 0, 0).endVertex();
/* 1342 */       worldrenderer.pos((j - i * 1.1F), (k + i * 2), 0.0D).color(200, 0, 0, 0).endVertex();
/* 1343 */       worldrenderer.pos((j + i * 1.1F), (k + i * 2), 0.0D).color(200, 0, 0, 0).endVertex();
/* 1344 */       worldrenderer.pos((j + i * 1.1F), (k - i * 0.6F - 16.0F), 0.0D).color(200, 0, 0, 0).endVertex();
/* 1345 */       tessellator.draw();
/* 1346 */       GlStateManager.disableBlend();
/* 1347 */       double d0 = 0.0D;
/*      */       
/* 1349 */       for (int l = 0; l < list.size(); l++) {
/*      */         
/* 1351 */         Profiler.Result profiler$result1 = list.get(l);
/* 1352 */         int i1 = MathHelper.floor_double(profiler$result1.field_76332_a / 4.0D) + 1;
/* 1353 */         worldrenderer.begin(6, DefaultVertexFormats.POSITION_COLOR);
/* 1354 */         int j1 = profiler$result1.func_76329_a();
/* 1355 */         int k1 = j1 >> 16 & 0xFF;
/* 1356 */         int l1 = j1 >> 8 & 0xFF;
/* 1357 */         int i2 = j1 & 0xFF;
/* 1358 */         worldrenderer.pos(j, k, 0.0D).color(k1, l1, i2, 255).endVertex();
/*      */         
/* 1360 */         for (int j2 = i1; j2 >= 0; j2--) {
/*      */           
/* 1362 */           float f = (float)((d0 + profiler$result1.field_76332_a * j2 / i1) * Math.PI * 2.0D / 100.0D);
/* 1363 */           float f1 = MathHelper.sin(f) * i;
/* 1364 */           float f2 = MathHelper.cos(f) * i * 0.5F;
/* 1365 */           worldrenderer.pos((j + f1), (k - f2), 0.0D).color(k1, l1, i2, 255).endVertex();
/*      */         } 
/*      */         
/* 1368 */         tessellator.draw();
/* 1369 */         worldrenderer.begin(5, DefaultVertexFormats.POSITION_COLOR);
/*      */         
/* 1371 */         for (int i3 = i1; i3 >= 0; i3--) {
/*      */           
/* 1373 */           float f3 = (float)((d0 + profiler$result1.field_76332_a * i3 / i1) * Math.PI * 2.0D / 100.0D);
/* 1374 */           float f4 = MathHelper.sin(f3) * i;
/* 1375 */           float f5 = MathHelper.cos(f3) * i * 0.5F;
/* 1376 */           worldrenderer.pos((j + f4), (k - f5), 0.0D).color(k1 >> 1, l1 >> 1, i2 >> 1, 255).endVertex();
/* 1377 */           worldrenderer.pos((j + f4), (k - f5 + 10.0F), 0.0D).color(k1 >> 1, l1 >> 1, i2 >> 1, 255).endVertex();
/*      */         } 
/*      */         
/* 1380 */         tessellator.draw();
/* 1381 */         d0 += profiler$result1.field_76332_a;
/*      */       } 
/*      */       
/* 1384 */       DecimalFormat decimalformat = new DecimalFormat("##0.00");
/* 1385 */       GlStateManager.enableTexture2D();
/* 1386 */       String s = "";
/*      */       
/* 1388 */       if (!profiler$result.field_76331_c.equals("unspecified"))
/*      */       {
/* 1390 */         s = String.valueOf(s) + "[0] ";
/*      */       }
/*      */       
/* 1393 */       if (profiler$result.field_76331_c.length() == 0) {
/*      */         
/* 1395 */         s = String.valueOf(s) + "ROOT ";
/*      */       }
/*      */       else {
/*      */         
/* 1399 */         s = String.valueOf(s) + profiler$result.field_76331_c + " ";
/*      */       } 
/*      */       
/* 1402 */       int l2 = 16777215;
/* 1403 */       this.fontRendererObj.drawStringWithShadow(s, (j - i), (k - i / 2 - 16), l2);
/* 1404 */       this.fontRendererObj.drawStringWithShadow(s = String.valueOf(decimalformat.format(profiler$result.field_76330_b)) + "%", (j + i - this.fontRendererObj.getStringWidth(s)), (k - i / 2 - 16), l2);
/*      */       
/* 1406 */       for (int k2 = 0; k2 < list.size(); k2++) {
/*      */         
/* 1408 */         Profiler.Result profiler$result2 = list.get(k2);
/* 1409 */         String s1 = "";
/*      */         
/* 1411 */         if (profiler$result2.field_76331_c.equals("unspecified")) {
/*      */           
/* 1413 */           s1 = String.valueOf(s1) + "[?] ";
/*      */         }
/*      */         else {
/*      */           
/* 1417 */           s1 = String.valueOf(s1) + "[" + (k2 + 1) + "] ";
/*      */         } 
/*      */         
/* 1420 */         s1 = String.valueOf(s1) + profiler$result2.field_76331_c;
/* 1421 */         this.fontRendererObj.drawStringWithShadow(s1, (j - i), (k + i / 2 + k2 * 8 + 20), profiler$result2.func_76329_a());
/* 1422 */         this.fontRendererObj.drawStringWithShadow(s1 = String.valueOf(decimalformat.format(profiler$result2.field_76332_a)) + "%", (j + i - 50 - this.fontRendererObj.getStringWidth(s1)), (k + i / 2 + k2 * 8 + 20), profiler$result2.func_76329_a());
/* 1423 */         this.fontRendererObj.drawStringWithShadow(s1 = String.valueOf(decimalformat.format(profiler$result2.field_76330_b)) + "%", (j + i - this.fontRendererObj.getStringWidth(s1)), (k + i / 2 + k2 * 8 + 20), profiler$result2.func_76329_a());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void shutdown() {
/* 1433 */     this.running = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIngameFocus() {
/* 1442 */     if (Display.isActive())
/*      */     {
/* 1444 */       if (!this.inGameHasFocus) {
/*      */         
/* 1446 */         this.inGameHasFocus = true;
/* 1447 */         this.mouseHelper.grabMouseCursor();
/* 1448 */         displayGuiScreen(null);
/* 1449 */         this.leftClickCounter = 10000;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIngameNotInFocus() {
/* 1459 */     if (this.inGameHasFocus) {
/*      */       
/* 1461 */       KeyBinding.unPressAllKeys();
/* 1462 */       this.inGameHasFocus = false;
/* 1463 */       this.mouseHelper.ungrabMouseCursor();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayInGameMenu() {
/* 1472 */     if (this.currentScreen == null) {
/*      */       
/* 1474 */       displayGuiScreen((GuiScreen)new GuiIngameMenu());
/*      */       
/* 1476 */       if (isSingleplayer() && !this.theIntegratedServer.getPublic())
/*      */       {
/* 1478 */         this.mcSoundHandler.pauseSounds();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void sendClickBlockToController(boolean leftClick) {
/* 1485 */     if (!leftClick)
/*      */     {
/* 1487 */       this.leftClickCounter = 0;
/*      */     }
/*      */     
/* 1490 */     if (this.leftClickCounter <= 0 && !this.thePlayer.isUsingItem())
/*      */     {
/* 1492 */       if (leftClick && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/*      */         
/* 1494 */         BlockPos blockpos = this.objectMouseOver.getBlockPos();
/*      */         
/* 1496 */         if (this.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air && this.playerController.onPlayerDamageBlock(blockpos, this.objectMouseOver.sideHit))
/*      */         {
/* 1498 */           this.effectRenderer.addBlockHitEffects(blockpos, this.objectMouseOver.sideHit);
/* 1499 */           this.thePlayer.swingItem();
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1504 */         this.playerController.resetBlockRemoving();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void clickMouse() {
/* 1511 */     if (this.leftClickCounter <= 0) {
/*      */       
/* 1513 */       this.thePlayer.swingItem();
/*      */       
/* 1515 */       if (this.objectMouseOver == null) {
/*      */         
/* 1517 */         logger.error("Null returned as 'hitResult', this shouldn't happen!");
/*      */         
/* 1519 */         if (this.playerController.isNotCreative())
/*      */         {
/* 1521 */           this.leftClickCounter = 10;
/*      */         }
/*      */       } else {
/*      */         BlockPos blockpos;
/*      */         
/* 1526 */         switch (this.objectMouseOver.typeOfHit) {
/*      */           
/*      */           case ENTITY:
/* 1529 */             this.playerController.attackEntity((EntityPlayer)this.thePlayer, this.objectMouseOver.entityHit);
/*      */             return;
/*      */           
/*      */           case null:
/* 1533 */             blockpos = this.objectMouseOver.getBlockPos();
/*      */             
/* 1535 */             if (this.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air) {
/*      */               
/* 1537 */               this.playerController.clickBlock(blockpos, this.objectMouseOver.sideHit);
/*      */               return;
/*      */             } 
/*      */             break;
/*      */         } 
/*      */         
/* 1543 */         if (this.playerController.isNotCreative())
/*      */         {
/* 1545 */           this.leftClickCounter = 10;
/*      */         }
/*      */       } 
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
/*      */   private void rightClickMouse() {
/* 1559 */     if (!this.playerController.func_181040_m()) {
/*      */       
/* 1561 */       this.rightClickDelayTimer = 4;
/* 1562 */       boolean flag = true;
/* 1563 */       ItemStack itemstack = this.thePlayer.inventory.getCurrentItem();
/*      */       
/* 1565 */       if (this.objectMouseOver == null) {
/*      */         
/* 1567 */         logger.warn("Null returned as 'hitResult', this shouldn't happen!");
/*      */       } else {
/*      */         BlockPos blockpos;
/*      */         
/* 1571 */         switch (this.objectMouseOver.typeOfHit) {
/*      */           
/*      */           case ENTITY:
/* 1574 */             if (this.playerController.func_178894_a((EntityPlayer)this.thePlayer, this.objectMouseOver.entityHit, this.objectMouseOver)) {
/*      */               
/* 1576 */               flag = false; break;
/*      */             } 
/* 1578 */             if (this.playerController.interactWithEntitySendPacket((EntityPlayer)this.thePlayer, this.objectMouseOver.entityHit))
/*      */             {
/* 1580 */               flag = false;
/*      */             }
/*      */             break;
/*      */ 
/*      */           
/*      */           case null:
/* 1586 */             blockpos = this.objectMouseOver.getBlockPos();
/*      */             
/* 1588 */             if (this.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air) {
/*      */               
/* 1590 */               int i = (itemstack != null) ? itemstack.stackSize : 0;
/*      */               
/* 1592 */               if (this.playerController.onPlayerRightClick(this.thePlayer, this.theWorld, itemstack, blockpos, this.objectMouseOver.sideHit, this.objectMouseOver.hitVec)) {
/*      */                 
/* 1594 */                 flag = false;
/* 1595 */                 this.thePlayer.swingItem();
/*      */               } 
/*      */               
/* 1598 */               if (itemstack == null) {
/*      */                 return;
/*      */               }
/*      */ 
/*      */               
/* 1603 */               if (itemstack.stackSize == 0) {
/*      */                 
/* 1605 */                 this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.currentItem] = null; break;
/*      */               } 
/* 1607 */               if (itemstack.stackSize != i || this.playerController.isInCreativeMode())
/*      */               {
/* 1609 */                 this.entityRenderer.itemRenderer.resetEquippedProgress();
/*      */               }
/*      */             } 
/*      */             break;
/*      */         } 
/*      */       } 
/* 1615 */       if (flag) {
/*      */         
/* 1617 */         ItemStack itemstack1 = this.thePlayer.inventory.getCurrentItem();
/*      */         
/* 1619 */         if (itemstack1 != null && this.playerController.sendUseItem((EntityPlayer)this.thePlayer, (World)this.theWorld, itemstack1))
/*      */         {
/* 1621 */           this.entityRenderer.itemRenderer.resetEquippedProgress2();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void toggleFullscreen() {
/*      */     try {
/* 1634 */       this.fullscreen = !this.fullscreen;
/* 1635 */       this.gameSettings.fullScreen = this.fullscreen;
/*      */       
/* 1637 */       if (this.fullscreen) {
/*      */         
/* 1639 */         updateDisplayMode();
/* 1640 */         this.displayWidth = Display.getDisplayMode().getWidth();
/* 1641 */         this.displayHeight = Display.getDisplayMode().getHeight();
/*      */         
/* 1643 */         if (this.displayWidth <= 0)
/*      */         {
/* 1645 */           this.displayWidth = 1;
/*      */         }
/*      */         
/* 1648 */         if (this.displayHeight <= 0)
/*      */         {
/* 1650 */           this.displayHeight = 1;
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 1655 */         Display.setDisplayMode(new DisplayMode(this.tempDisplayWidth, this.tempDisplayHeight));
/* 1656 */         this.displayWidth = this.tempDisplayWidth;
/* 1657 */         this.displayHeight = this.tempDisplayHeight;
/*      */         
/* 1659 */         if (this.displayWidth <= 0)
/*      */         {
/* 1661 */           this.displayWidth = 1;
/*      */         }
/*      */         
/* 1664 */         if (this.displayHeight <= 0)
/*      */         {
/* 1666 */           this.displayHeight = 1;
/*      */         }
/*      */       } 
/*      */       
/* 1670 */       if (this.currentScreen != null) {
/*      */         
/* 1672 */         resize(this.displayWidth, this.displayHeight);
/*      */       }
/*      */       else {
/*      */         
/* 1676 */         updateFramebufferSize();
/*      */       } 
/*      */       
/* 1679 */       Display.setFullscreen(this.fullscreen);
/* 1680 */       Display.setVSyncEnabled(this.gameSettings.enableVsync);
/* 1681 */       updateDisplay();
/*      */     }
/* 1683 */     catch (Exception exception) {
/*      */       
/* 1685 */       logger.error("Couldn't toggle fullscreen", exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resize(int width, int height) {
/* 1694 */     this.displayWidth = Math.max(1, width);
/* 1695 */     this.displayHeight = Math.max(1, height);
/*      */     
/* 1697 */     if (this.currentScreen != null) {
/*      */       
/* 1699 */       ScaledResolution scaledresolution = new ScaledResolution(this);
/* 1700 */       this.currentScreen.onResize(this, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight());
/*      */     } 
/*      */     
/* 1703 */     this.loadingScreen = new LoadingScreenRenderer(this);
/* 1704 */     updateFramebufferSize();
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateFramebufferSize() {
/* 1709 */     this.framebufferMc.createBindFramebuffer(this.displayWidth, this.displayHeight);
/*      */     
/* 1711 */     if (this.entityRenderer != null)
/*      */     {
/* 1713 */       this.entityRenderer.updateShaderGroupSize(this.displayWidth, this.displayHeight);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public MusicTicker func_181535_r() {
/* 1719 */     return this.mcMusicTicker;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void runTick() throws IOException {
/* 1727 */     if (this.rightClickDelayTimer > 0)
/*      */     {
/* 1729 */       this.rightClickDelayTimer--;
/*      */     }
/*      */     
/* 1732 */     this.mcProfiler.startSection("gui");
/*      */     
/* 1734 */     if (!this.isGamePaused)
/*      */     {
/* 1736 */       this.ingameGUI.updateTick();
/*      */     }
/*      */     
/* 1739 */     this.mcProfiler.endSection();
/* 1740 */     this.entityRenderer.getMouseOver(1.0F);
/* 1741 */     this.mcProfiler.startSection("gameMode");
/*      */     
/* 1743 */     if (!this.isGamePaused && this.theWorld != null)
/*      */     {
/* 1745 */       this.playerController.updateController();
/*      */     }
/*      */     
/* 1748 */     this.mcProfiler.endStartSection("textures");
/*      */     
/* 1750 */     if (!this.isGamePaused)
/*      */     {
/* 1752 */       this.renderEngine.tick();
/*      */     }
/*      */     
/* 1755 */     if (this.currentScreen == null && this.thePlayer != null) {
/*      */       
/* 1757 */       if (this.thePlayer.getHealth() <= 0.0F)
/*      */       {
/* 1759 */         displayGuiScreen(null);
/*      */       }
/* 1761 */       else if (this.thePlayer.isPlayerSleeping() && this.theWorld != null)
/*      */       {
/* 1763 */         displayGuiScreen((GuiScreen)new GuiSleepMP());
/*      */       }
/*      */     
/* 1766 */     } else if (this.currentScreen != null && this.currentScreen instanceof GuiSleepMP && !this.thePlayer.isPlayerSleeping()) {
/*      */       
/* 1768 */       displayGuiScreen(null);
/*      */     } 
/*      */     
/* 1771 */     if (this.currentScreen != null)
/*      */     {
/* 1773 */       this.leftClickCounter = 10000;
/*      */     }
/*      */     
/* 1776 */     if (this.currentScreen != null) {
/*      */ 
/*      */       
/*      */       try {
/* 1780 */         this.currentScreen.handleInput();
/*      */       }
/* 1782 */       catch (Throwable throwable1) {
/*      */         
/* 1784 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Updating screen events");
/* 1785 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Affected screen");
/* 1786 */         crashreportcategory.addCrashSectionCallable("Screen name", new Callable<String>()
/*      */             {
/*      */               public String call() throws Exception
/*      */               {
/* 1790 */                 return Minecraft.this.currentScreen.getClass().getCanonicalName();
/*      */               }
/*      */             });
/* 1793 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/* 1796 */       if (this.currentScreen != null) {
/*      */         
/*      */         try {
/*      */           
/* 1800 */           this.currentScreen.updateScreen();
/*      */         }
/* 1802 */         catch (Throwable throwable) {
/*      */           
/* 1804 */           CrashReport crashreport1 = CrashReport.makeCrashReport(throwable, "Ticking screen");
/* 1805 */           CrashReportCategory crashreportcategory1 = crashreport1.makeCategory("Affected screen");
/* 1806 */           crashreportcategory1.addCrashSectionCallable("Screen name", new Callable<String>()
/*      */               {
/*      */                 public String call() throws Exception
/*      */                 {
/* 1810 */                   return Minecraft.this.currentScreen.getClass().getCanonicalName();
/*      */                 }
/*      */               });
/* 1813 */           throw new ReportedException(crashreport1);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1818 */     if (this.currentScreen == null || this.currentScreen.allowUserInput) {
/*      */       
/* 1820 */       this.mcProfiler.endStartSection("mouse");
/*      */       
/* 1822 */       while (Mouse.next()) {
/*      */         
/* 1824 */         int i = Mouse.getEventButton();
/* 1825 */         KeyBinding.setKeyBindState(i - 100, Mouse.getEventButtonState());
/*      */         
/* 1827 */         if (Mouse.getEventButtonState())
/*      */         {
/* 1829 */           if (this.thePlayer.isSpectator() && i == 2) {
/*      */             
/* 1831 */             this.ingameGUI.getSpectatorGui().func_175261_b();
/*      */           }
/*      */           else {
/*      */             
/* 1835 */             KeyBinding.onTick(i - 100);
/*      */           } 
/*      */         }
/*      */         
/* 1839 */         long i1 = getSystemTime() - this.systemTime;
/*      */         
/* 1841 */         if (i1 <= 200L) {
/*      */           
/* 1843 */           int j = Mouse.getEventDWheel();
/*      */           
/* 1845 */           if (j != 0)
/*      */           {
/* 1847 */             if (this.thePlayer.isSpectator()) {
/*      */               
/* 1849 */               j = (j < 0) ? -1 : 1;
/*      */               
/* 1851 */               if (this.ingameGUI.getSpectatorGui().func_175262_a())
/*      */               {
/* 1853 */                 this.ingameGUI.getSpectatorGui().func_175259_b(-j);
/*      */               }
/*      */               else
/*      */               {
/* 1857 */                 float f = MathHelper.clamp_float(this.thePlayer.capabilities.getFlySpeed() + j * 0.005F, 0.0F, 0.2F);
/* 1858 */                 this.thePlayer.capabilities.setFlySpeed(f);
/*      */               }
/*      */             
/*      */             } else {
/*      */               
/* 1863 */               this.thePlayer.inventory.changeCurrentItem(j);
/*      */             } 
/*      */           }
/*      */           
/* 1867 */           if (this.currentScreen == null) {
/*      */             
/* 1869 */             if (!this.inGameHasFocus && Mouse.getEventButtonState())
/*      */             {
/* 1871 */               setIngameFocus(); } 
/*      */             continue;
/*      */           } 
/* 1874 */           if (this.currentScreen != null)
/*      */           {
/* 1876 */             this.currentScreen.handleMouseInput();
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1881 */       if (this.leftClickCounter > 0)
/*      */       {
/* 1883 */         this.leftClickCounter--;
/*      */       }
/*      */       
/* 1886 */       this.mcProfiler.endStartSection("keyboard");
/*      */       
/* 1888 */       while (Keyboard.next()) {
/*      */         
/* 1890 */         int k = (Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + 256) : Keyboard.getEventKey();
/* 1891 */         KeyBinding.setKeyBindState(k, Keyboard.getEventKeyState());
/*      */         
/* 1893 */         if (Keyboard.getEventKeyState())
/*      */         {
/* 1895 */           KeyBinding.onTick(k);
/*      */         }
/*      */         
/* 1898 */         if (this.debugCrashKeyPressTime > 0L) {
/*      */           
/* 1900 */           if (getSystemTime() - this.debugCrashKeyPressTime >= 6000L)
/*      */           {
/* 1902 */             throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
/*      */           }
/*      */           
/* 1905 */           if (!Keyboard.isKeyDown(46) || !Keyboard.isKeyDown(61))
/*      */           {
/* 1907 */             this.debugCrashKeyPressTime = -1L;
/*      */           }
/*      */         }
/* 1910 */         else if (Keyboard.isKeyDown(46) && Keyboard.isKeyDown(61)) {
/*      */           
/* 1912 */           this.debugCrashKeyPressTime = getSystemTime();
/*      */         } 
/*      */         
/* 1915 */         dispatchKeypresses();
/*      */         
/* 1917 */         if (Keyboard.getEventKeyState()) {
/*      */           
/* 1919 */           if (k == 62 && this.entityRenderer != null)
/*      */           {
/* 1921 */             this.entityRenderer.switchUseShader();
/*      */           }
/*      */           
/* 1924 */           if (this.currentScreen != null) {
/*      */             
/* 1926 */             this.currentScreen.handleKeyboardInput();
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 1931 */             for (Module module : Client.instance.getModuleManager().getModules()) {
/*      */               
/* 1933 */               if (k == module.getKey())
/*      */               {
/* 1935 */                 module.toggle();
/*      */               }
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 1941 */             if (k == 1)
/*      */             {
/* 1943 */               displayInGameMenu();
/*      */             }
/*      */             
/* 1946 */             if (k == 32 && Keyboard.isKeyDown(61) && this.ingameGUI != null)
/*      */             {
/* 1948 */               this.ingameGUI.getChatGUI().clearChatMessages();
/*      */             }
/*      */             
/* 1951 */             if (k == 31 && Keyboard.isKeyDown(61))
/*      */             {
/* 1953 */               refreshResources();
/*      */             }
/*      */             
/* 1956 */             if (k != 17 || Keyboard.isKeyDown(61));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1961 */             if (k != 18 || Keyboard.isKeyDown(61));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1966 */             if (k != 47 || Keyboard.isKeyDown(61));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1971 */             if (k != 38 || Keyboard.isKeyDown(61));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1976 */             if (k != 22 || Keyboard.isKeyDown(61));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1981 */             if (k == 20 && Keyboard.isKeyDown(61))
/*      */             {
/* 1983 */               refreshResources();
/*      */             }
/*      */             
/* 1986 */             if (k == 33 && Keyboard.isKeyDown(61))
/*      */             {
/* 1988 */               this.gameSettings.setOptionValue(GameSettings.Options.RENDER_DISTANCE, GuiScreen.isShiftKeyDown() ? -1 : 1);
/*      */             }
/*      */             
/* 1991 */             if (k == 30 && Keyboard.isKeyDown(61))
/*      */             {
/* 1993 */               this.renderGlobal.loadRenderers();
/*      */             }
/*      */             
/* 1996 */             if (k == 35 && Keyboard.isKeyDown(61)) {
/*      */               
/* 1998 */               this.gameSettings.advancedItemTooltips = !this.gameSettings.advancedItemTooltips;
/* 1999 */               this.gameSettings.saveOptions();
/*      */             } 
/*      */             
/* 2002 */             if (k == 48 && Keyboard.isKeyDown(61))
/*      */             {
/* 2004 */               this.renderManager.setDebugBoundingBox(!this.renderManager.isDebugBoundingBox());
/*      */             }
/*      */             
/* 2007 */             if (k == 25 && Keyboard.isKeyDown(61)) {
/*      */               
/* 2009 */               this.gameSettings.pauseOnLostFocus = !this.gameSettings.pauseOnLostFocus;
/* 2010 */               this.gameSettings.saveOptions();
/*      */             } 
/*      */             
/* 2013 */             if (k == 59)
/*      */             {
/* 2015 */               this.gameSettings.hideGUI = !this.gameSettings.hideGUI;
/*      */             }
/*      */             
/* 2018 */             if (k == 61) {
/*      */               
/* 2020 */               this.gameSettings.showDebugInfo = !this.gameSettings.showDebugInfo;
/* 2021 */               this.gameSettings.showDebugProfilerChart = GuiScreen.isShiftKeyDown();
/* 2022 */               this.gameSettings.field_181657_aC = GuiScreen.isAltKeyDown();
/*      */             } 
/*      */             
/* 2025 */             if (this.gameSettings.keyBindTogglePerspective.isPressed()) {
/*      */               
/* 2027 */               this.gameSettings.thirdPersonView++;
/*      */               
/* 2029 */               if (this.gameSettings.thirdPersonView > 2)
/*      */               {
/* 2031 */                 this.gameSettings.thirdPersonView = 0;
/*      */               }
/*      */               
/* 2034 */               if (this.gameSettings.thirdPersonView == 0) {
/*      */                 
/* 2036 */                 this.entityRenderer.loadEntityShader(getRenderViewEntity());
/*      */               }
/* 2038 */               else if (this.gameSettings.thirdPersonView == 1) {
/*      */                 
/* 2040 */                 this.entityRenderer.loadEntityShader(null);
/*      */               } 
/*      */               
/* 2043 */               this.renderGlobal.setDisplayListEntitiesDirty();
/*      */             } 
/*      */             
/* 2046 */             if (this.gameSettings.keyBindSmoothCamera.isPressed())
/*      */             {
/* 2048 */               this.gameSettings.smoothCamera = !this.gameSettings.smoothCamera;
/*      */             }
/*      */           } 
/*      */           
/* 2052 */           if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart) {
/*      */             
/* 2054 */             if (k == 11)
/*      */             {
/* 2056 */               updateDebugProfilerName(0);
/*      */             }
/*      */             
/* 2059 */             for (int j1 = 0; j1 < 9; j1++) {
/*      */               
/* 2061 */               if (k == 2 + j1)
/*      */               {
/* 2063 */                 updateDebugProfilerName(j1 + 1);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2070 */       for (int l = 0; l < 9; l++) {
/*      */         
/* 2072 */         if (this.gameSettings.keyBindsHotbar[l].isPressed())
/*      */         {
/* 2074 */           if (this.thePlayer.isSpectator()) {
/*      */             
/* 2076 */             this.ingameGUI.getSpectatorGui().func_175260_a(l);
/*      */           }
/*      */           else {
/*      */             
/* 2080 */             this.thePlayer.inventory.currentItem = l;
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/* 2085 */       boolean flag = (this.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN);
/*      */       
/* 2087 */       while (this.gameSettings.keyBindInventory.isPressed()) {
/*      */         
/* 2089 */         if (this.playerController.isRidingHorse()) {
/*      */           
/* 2091 */           this.thePlayer.sendHorseInventory();
/*      */           
/*      */           continue;
/*      */         } 
/* 2095 */         getNetHandler().addToSendQueue((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
/* 2096 */         displayGuiScreen((GuiScreen)new GuiInventory((EntityPlayer)this.thePlayer));
/*      */       } 
/*      */ 
/*      */       
/* 2100 */       while (this.gameSettings.keyBindDrop.isPressed()) {
/*      */         
/* 2102 */         if (!this.thePlayer.isSpectator())
/*      */         {
/* 2104 */           this.thePlayer.dropOneItem(GuiScreen.isCtrlKeyDown());
/*      */         }
/*      */       } 
/*      */       
/* 2108 */       while (this.gameSettings.keyBindChat.isPressed() && flag)
/*      */       {
/* 2110 */         displayGuiScreen((GuiScreen)new GuiChat());
/*      */       }
/*      */       
/* 2113 */       if (this.currentScreen == null && this.gameSettings.keyBindCommand.isPressed() && flag)
/*      */       {
/* 2115 */         displayGuiScreen((GuiScreen)new GuiChat("/"));
/*      */       }
/*      */       
/* 2118 */       if (this.thePlayer.isUsingItem()) {
/*      */         
/* 2120 */         if (!this.gameSettings.keyBindUseItem.isKeyDown())
/*      */         {
/* 2122 */           this.playerController.onStoppedUsingItem((EntityPlayer)this.thePlayer); } 
/*      */         do {
/*      */         
/* 2125 */         } while (this.gameSettings.keyBindAttack.isPressed());
/*      */ 
/*      */         
/*      */         do {
/*      */         
/* 2130 */         } while (this.gameSettings.keyBindUseItem.isPressed());
/*      */ 
/*      */         
/*      */         do {
/*      */         
/* 2135 */         } while (this.gameSettings.keyBindPickBlock.isPressed());
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 2142 */         while (this.gameSettings.keyBindAttack.isPressed())
/*      */         {
/* 2144 */           clickMouse();
/*      */         }
/*      */         
/* 2147 */         while (this.gameSettings.keyBindUseItem.isPressed())
/*      */         {
/* 2149 */           rightClickMouse();
/*      */         }
/*      */         
/* 2152 */         while (this.gameSettings.keyBindPickBlock.isPressed())
/*      */         {
/* 2154 */           middleClickMouse();
/*      */         }
/*      */       } 
/*      */       
/* 2158 */       if (this.gameSettings.keyBindUseItem.isKeyDown() && this.rightClickDelayTimer == 0 && !this.thePlayer.isUsingItem())
/*      */       {
/* 2160 */         rightClickMouse();
/*      */       }
/*      */       
/* 2163 */       sendClickBlockToController((this.currentScreen == null && this.gameSettings.keyBindAttack.isKeyDown() && this.inGameHasFocus));
/*      */     } 
/*      */     
/* 2166 */     if (this.theWorld != null) {
/*      */       
/* 2168 */       if (this.thePlayer != null) {
/*      */         
/* 2170 */         this.joinPlayerCounter++;
/*      */         
/* 2172 */         if (this.joinPlayerCounter == 30) {
/*      */           
/* 2174 */           this.joinPlayerCounter = 0;
/* 2175 */           this.theWorld.joinEntityInSurroundings((Entity)this.thePlayer);
/*      */         } 
/*      */       } 
/*      */       
/* 2179 */       this.mcProfiler.endStartSection("gameRenderer");
/*      */       
/* 2181 */       if (!this.isGamePaused)
/*      */       {
/* 2183 */         this.entityRenderer.updateRenderer();
/*      */       }
/*      */       
/* 2186 */       this.mcProfiler.endStartSection("levelRenderer");
/*      */       
/* 2188 */       if (!this.isGamePaused)
/*      */       {
/* 2190 */         this.renderGlobal.updateClouds();
/*      */       }
/*      */       
/* 2193 */       this.mcProfiler.endStartSection("level");
/*      */       
/* 2195 */       if (!this.isGamePaused)
/*      */       {
/* 2197 */         if (this.theWorld.getLastLightningBolt() > 0)
/*      */         {
/* 2199 */           this.theWorld.setLastLightningBolt(this.theWorld.getLastLightningBolt() - 1);
/*      */         }
/*      */         
/* 2202 */         this.theWorld.updateEntities();
/*      */       }
/*      */     
/* 2205 */     } else if (this.entityRenderer.isShaderActive()) {
/*      */       
/* 2207 */       this.entityRenderer.func_181022_b();
/*      */     } 
/*      */     
/* 2210 */     if (!this.isGamePaused) {
/*      */       
/* 2212 */       this.mcMusicTicker.update();
/* 2213 */       this.mcSoundHandler.update();
/*      */     } 
/*      */     
/* 2216 */     if (this.theWorld != null) {
/*      */       
/* 2218 */       if (!this.isGamePaused) {
/*      */         
/* 2220 */         this.theWorld.setAllowedSpawnTypes((this.theWorld.getDifficulty() != EnumDifficulty.PEACEFUL), true);
/*      */ 
/*      */         
/*      */         try {
/* 2224 */           this.theWorld.tick();
/*      */         }
/* 2226 */         catch (Throwable throwable2) {
/*      */           
/* 2228 */           CrashReport crashreport2 = CrashReport.makeCrashReport(throwable2, "Exception in world tick");
/*      */           
/* 2230 */           if (this.theWorld == null) {
/*      */             
/* 2232 */             CrashReportCategory crashreportcategory2 = crashreport2.makeCategory("Affected level");
/* 2233 */             crashreportcategory2.addCrashSection("Problem", "Level is null!");
/*      */           }
/*      */           else {
/*      */             
/* 2237 */             this.theWorld.addWorldInfoToCrashReport(crashreport2);
/*      */           } 
/*      */           
/* 2240 */           throw new ReportedException(crashreport2);
/*      */         } 
/*      */       } 
/*      */       
/* 2244 */       this.mcProfiler.endStartSection("animateTick");
/*      */       
/* 2246 */       if (!this.isGamePaused && this.theWorld != null)
/*      */       {
/* 2248 */         this.theWorld.doVoidFogParticles(MathHelper.floor_double(this.thePlayer.posX), MathHelper.floor_double(this.thePlayer.posY), MathHelper.floor_double(this.thePlayer.posZ));
/*      */       }
/*      */       
/* 2251 */       this.mcProfiler.endStartSection("particles");
/*      */       
/* 2253 */       if (!this.isGamePaused)
/*      */       {
/* 2255 */         this.effectRenderer.updateEffects();
/*      */       }
/*      */     }
/* 2258 */     else if (this.myNetworkManager != null) {
/*      */       
/* 2260 */       this.mcProfiler.endStartSection("pendingConnection");
/* 2261 */       this.myNetworkManager.processReceivedPackets();
/*      */     } 
/*      */     
/* 2264 */     this.mcProfiler.endSection();
/* 2265 */     this.systemTime = getSystemTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void launchIntegratedServer(String folderName, String worldName, WorldSettings worldSettingsIn) {
/* 2273 */     loadWorld(null);
/* 2274 */     System.gc();
/* 2275 */     ISaveHandler isavehandler = this.saveLoader.getSaveLoader(folderName, false);
/* 2276 */     WorldInfo worldinfo = isavehandler.loadWorldInfo();
/*      */     
/* 2278 */     if (worldinfo == null && worldSettingsIn != null) {
/*      */       
/* 2280 */       worldinfo = new WorldInfo(worldSettingsIn, folderName);
/* 2281 */       isavehandler.saveWorldInfo(worldinfo);
/*      */     } 
/*      */     
/* 2284 */     if (worldSettingsIn == null)
/*      */     {
/* 2286 */       worldSettingsIn = new WorldSettings(worldinfo);
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 2291 */       this.theIntegratedServer = new IntegratedServer(this, folderName, worldName, worldSettingsIn);
/* 2292 */       this.theIntegratedServer.startServerThread();
/* 2293 */       this.integratedServerIsRunning = true;
/*      */     }
/* 2295 */     catch (Throwable throwable) {
/*      */       
/* 2297 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Starting integrated server");
/* 2298 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Starting integrated server");
/* 2299 */       crashreportcategory.addCrashSection("Level ID", folderName);
/* 2300 */       crashreportcategory.addCrashSection("Level Name", worldName);
/* 2301 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */     
/* 2304 */     this.loadingScreen.displaySavingString(I18n.format("menu.loadingLevel", new Object[0]));
/*      */     
/* 2306 */     while (!this.theIntegratedServer.serverIsInRunLoop()) {
/*      */       
/* 2308 */       String s = this.theIntegratedServer.getUserMessage();
/*      */       
/* 2310 */       if (s != null) {
/*      */         
/* 2312 */         this.loadingScreen.displayLoadingString(I18n.format(s, new Object[0]));
/*      */       }
/*      */       else {
/*      */         
/* 2316 */         this.loadingScreen.displayLoadingString("");
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/* 2321 */         Thread.sleep(200L);
/*      */       }
/* 2323 */       catch (InterruptedException interruptedException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2329 */     displayGuiScreen(null);
/* 2330 */     SocketAddress socketaddress = this.theIntegratedServer.getNetworkSystem().addLocalEndpoint();
/* 2331 */     NetworkManager networkmanager = NetworkManager.provideLocalClient(socketaddress);
/* 2332 */     networkmanager.setNetHandler((INetHandler)new NetHandlerLoginClient(networkmanager, this, null));
/* 2333 */     networkmanager.sendPacket((Packet)new C00Handshake(47, socketaddress.toString(), 0, EnumConnectionState.LOGIN));
/* 2334 */     networkmanager.sendPacket((Packet)new C00PacketLoginStart(getSession().getProfile()));
/* 2335 */     this.myNetworkManager = networkmanager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadWorld(WorldClient worldClientIn) {
/* 2343 */     loadWorld(worldClientIn, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadWorld(WorldClient worldClientIn, String loadingMessage) {
/* 2351 */     if (worldClientIn == null) {
/*      */       
/* 2353 */       NetHandlerPlayClient nethandlerplayclient = getNetHandler();
/*      */       
/* 2355 */       if (nethandlerplayclient != null)
/*      */       {
/* 2357 */         nethandlerplayclient.cleanup();
/*      */       }
/*      */       
/* 2360 */       if (this.theIntegratedServer != null && this.theIntegratedServer.isAnvilFileSet()) {
/*      */         
/* 2362 */         this.theIntegratedServer.initiateShutdown();
/* 2363 */         this.theIntegratedServer.setStaticInstance();
/*      */       } 
/*      */       
/* 2366 */       this.theIntegratedServer = null;
/* 2367 */       this.guiAchievement.clearAchievements();
/* 2368 */       this.entityRenderer.getMapItemRenderer().clearLoadedMaps();
/*      */     } 
/*      */     
/* 2371 */     this.renderViewEntity = null;
/* 2372 */     this.myNetworkManager = null;
/*      */     
/* 2374 */     if (this.loadingScreen != null) {
/*      */       
/* 2376 */       this.loadingScreen.resetProgressAndMessage(loadingMessage);
/* 2377 */       this.loadingScreen.displayLoadingString("");
/*      */     } 
/*      */     
/* 2380 */     if (worldClientIn == null && this.theWorld != null) {
/*      */       
/* 2382 */       this.mcResourcePackRepository.func_148529_f();
/* 2383 */       this.ingameGUI.func_181029_i();
/* 2384 */       setServerData(null);
/* 2385 */       this.integratedServerIsRunning = false;
/*      */     } 
/*      */     
/* 2388 */     this.mcSoundHandler.stopSounds();
/* 2389 */     this.theWorld = worldClientIn;
/*      */     
/* 2391 */     if (worldClientIn != null) {
/*      */       
/* 2393 */       if (this.renderGlobal != null)
/*      */       {
/* 2395 */         this.renderGlobal.setWorldAndLoadRenderers(worldClientIn);
/*      */       }
/*      */       
/* 2398 */       if (this.effectRenderer != null)
/*      */       {
/* 2400 */         this.effectRenderer.clearEffects((World)worldClientIn);
/*      */       }
/*      */       
/* 2403 */       if (this.thePlayer == null) {
/*      */         
/* 2405 */         this.thePlayer = this.playerController.func_178892_a((World)worldClientIn, new StatFileWriter());
/* 2406 */         this.playerController.flipPlayer((EntityPlayer)this.thePlayer);
/*      */       } 
/*      */       
/* 2409 */       this.thePlayer.preparePlayerToSpawn();
/* 2410 */       worldClientIn.spawnEntityInWorld((Entity)this.thePlayer);
/* 2411 */       this.thePlayer.movementInput = (MovementInput)new MovementInputFromOptions(this.gameSettings);
/* 2412 */       this.playerController.setPlayerCapabilities((EntityPlayer)this.thePlayer);
/* 2413 */       this.renderViewEntity = (Entity)this.thePlayer;
/*      */     }
/*      */     else {
/*      */       
/* 2417 */       this.saveLoader.flushCache();
/* 2418 */       this.thePlayer = null;
/*      */     } 
/*      */     
/* 2421 */     System.gc();
/* 2422 */     this.systemTime = 0L;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDimensionAndSpawnPlayer(int dimension) {
/* 2427 */     this.theWorld.setInitialSpawnLocation();
/* 2428 */     this.theWorld.removeAllEntities();
/* 2429 */     int i = 0;
/* 2430 */     String s = null;
/*      */     
/* 2432 */     if (this.thePlayer != null) {
/*      */       
/* 2434 */       i = this.thePlayer.getEntityId();
/* 2435 */       this.theWorld.removeEntity((Entity)this.thePlayer);
/* 2436 */       s = this.thePlayer.getClientBrand();
/*      */     } 
/*      */     
/* 2439 */     this.renderViewEntity = null;
/* 2440 */     EntityPlayerSP entityplayersp = this.thePlayer;
/* 2441 */     this.thePlayer = this.playerController.func_178892_a((World)this.theWorld, (this.thePlayer == null) ? new StatFileWriter() : this.thePlayer.getStatFileWriter());
/* 2442 */     this.thePlayer.getDataWatcher().updateWatchedObjectsFromList(entityplayersp.getDataWatcher().getAllWatched());
/* 2443 */     this.thePlayer.dimension = dimension;
/* 2444 */     this.renderViewEntity = (Entity)this.thePlayer;
/* 2445 */     this.thePlayer.preparePlayerToSpawn();
/* 2446 */     this.thePlayer.setClientBrand(s);
/* 2447 */     this.theWorld.spawnEntityInWorld((Entity)this.thePlayer);
/* 2448 */     this.playerController.flipPlayer((EntityPlayer)this.thePlayer);
/* 2449 */     this.thePlayer.movementInput = (MovementInput)new MovementInputFromOptions(this.gameSettings);
/* 2450 */     this.thePlayer.setEntityId(i);
/* 2451 */     this.playerController.setPlayerCapabilities((EntityPlayer)this.thePlayer);
/* 2452 */     this.thePlayer.setReducedDebug(entityplayersp.hasReducedDebug());
/*      */     
/* 2454 */     if (this.currentScreen instanceof GuiGameOver)
/*      */     {
/* 2456 */       displayGuiScreen(null);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isDemo() {
/* 2465 */     return this.isDemo;
/*      */   }
/*      */ 
/*      */   
/*      */   public NetHandlerPlayClient getNetHandler() {
/* 2470 */     return (this.thePlayer != null) ? this.thePlayer.sendQueue : null;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isGuiEnabled() {
/* 2475 */     return !(theMinecraft != null && theMinecraft.gameSettings.hideGUI);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFancyGraphicsEnabled() {
/* 2480 */     return (theMinecraft != null && theMinecraft.gameSettings.fancyGraphics);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAmbientOcclusionEnabled() {
/* 2488 */     return (theMinecraft != null && theMinecraft.gameSettings.ambientOcclusion != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void middleClickMouse() {
/* 2496 */     if (this.objectMouseOver != null) {
/*      */       Item item;
/* 2498 */       boolean flag = this.thePlayer.capabilities.isCreativeMode;
/* 2499 */       int i = 0;
/* 2500 */       boolean flag1 = false;
/* 2501 */       TileEntity tileentity = null;
/*      */ 
/*      */       
/* 2504 */       if (this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/*      */         
/* 2506 */         BlockPos blockpos = this.objectMouseOver.getBlockPos();
/* 2507 */         Block block = this.theWorld.getBlockState(blockpos).getBlock();
/*      */         
/* 2509 */         if (block.getMaterial() == Material.air) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 2514 */         item = block.getItem((World)this.theWorld, blockpos);
/*      */         
/* 2516 */         if (item == null) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 2521 */         if (flag && GuiScreen.isCtrlKeyDown())
/*      */         {
/* 2523 */           tileentity = this.theWorld.getTileEntity(blockpos);
/*      */         }
/*      */         
/* 2526 */         Block block1 = (item instanceof net.minecraft.item.ItemBlock && !block.isFlowerPot()) ? Block.getBlockFromItem(item) : block;
/* 2527 */         i = block1.getDamageValue((World)this.theWorld, blockpos);
/* 2528 */         flag1 = item.getHasSubtypes();
/*      */       }
/*      */       else {
/*      */         
/* 2532 */         if (this.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY || this.objectMouseOver.entityHit == null || !flag) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 2537 */         if (this.objectMouseOver.entityHit instanceof net.minecraft.entity.item.EntityPainting) {
/*      */           
/* 2539 */           item = Items.painting;
/*      */         }
/* 2541 */         else if (this.objectMouseOver.entityHit instanceof net.minecraft.entity.EntityLeashKnot) {
/*      */           
/* 2543 */           item = Items.lead;
/*      */         }
/* 2545 */         else if (this.objectMouseOver.entityHit instanceof EntityItemFrame) {
/*      */           
/* 2547 */           EntityItemFrame entityitemframe = (EntityItemFrame)this.objectMouseOver.entityHit;
/* 2548 */           ItemStack itemstack = entityitemframe.getDisplayedItem();
/*      */           
/* 2550 */           if (itemstack == null)
/*      */           {
/* 2552 */             item = Items.item_frame;
/*      */           }
/*      */           else
/*      */           {
/* 2556 */             item = itemstack.getItem();
/* 2557 */             i = itemstack.getMetadata();
/* 2558 */             flag1 = true;
/*      */           }
/*      */         
/* 2561 */         } else if (this.objectMouseOver.entityHit instanceof EntityMinecart) {
/*      */           
/* 2563 */           EntityMinecart entityminecart = (EntityMinecart)this.objectMouseOver.entityHit;
/*      */           
/* 2565 */           switch (entityminecart.getMinecartType()) {
/*      */             
/*      */             case FURNACE:
/* 2568 */               item = Items.furnace_minecart;
/*      */               break;
/*      */             
/*      */             case null:
/* 2572 */               item = Items.chest_minecart;
/*      */               break;
/*      */             
/*      */             case TNT:
/* 2576 */               item = Items.tnt_minecart;
/*      */               break;
/*      */             
/*      */             case HOPPER:
/* 2580 */               item = Items.hopper_minecart;
/*      */               break;
/*      */             
/*      */             case COMMAND_BLOCK:
/* 2584 */               item = Items.command_block_minecart;
/*      */               break;
/*      */             
/*      */             default:
/* 2588 */               item = Items.minecart;
/*      */               break;
/*      */           } 
/* 2591 */         } else if (this.objectMouseOver.entityHit instanceof net.minecraft.entity.item.EntityBoat) {
/*      */           
/* 2593 */           item = Items.boat;
/*      */         }
/* 2595 */         else if (this.objectMouseOver.entityHit instanceof net.minecraft.entity.item.EntityArmorStand) {
/*      */           
/* 2597 */           ItemArmorStand itemArmorStand = Items.armor_stand;
/*      */         }
/*      */         else {
/*      */           
/* 2601 */           item = Items.spawn_egg;
/* 2602 */           i = EntityList.getEntityID(this.objectMouseOver.entityHit);
/* 2603 */           flag1 = true;
/*      */           
/* 2605 */           if (!EntityList.entityEggs.containsKey(Integer.valueOf(i))) {
/*      */             return;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2612 */       InventoryPlayer inventoryplayer = this.thePlayer.inventory;
/*      */       
/* 2614 */       if (tileentity == null) {
/*      */         
/* 2616 */         inventoryplayer.setCurrentItem(item, i, flag1, flag);
/*      */       }
/*      */       else {
/*      */         
/* 2620 */         ItemStack itemstack1 = func_181036_a(item, i, tileentity);
/* 2621 */         inventoryplayer.setInventorySlotContents(inventoryplayer.currentItem, itemstack1);
/*      */       } 
/*      */       
/* 2624 */       if (flag) {
/*      */         
/* 2626 */         int j = this.thePlayer.inventoryContainer.inventorySlots.size() - 9 + inventoryplayer.currentItem;
/* 2627 */         this.playerController.sendSlotPacket(inventoryplayer.getStackInSlot(inventoryplayer.currentItem), j);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private ItemStack func_181036_a(Item p_181036_1_, int p_181036_2_, TileEntity p_181036_3_) {
/* 2634 */     ItemStack itemstack = new ItemStack(p_181036_1_, 1, p_181036_2_);
/* 2635 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 2636 */     p_181036_3_.writeToNBT(nbttagcompound);
/*      */     
/* 2638 */     if (p_181036_1_ == Items.skull && nbttagcompound.hasKey("Owner")) {
/*      */       
/* 2640 */       NBTTagCompound nbttagcompound2 = nbttagcompound.getCompoundTag("Owner");
/* 2641 */       NBTTagCompound nbttagcompound3 = new NBTTagCompound();
/* 2642 */       nbttagcompound3.setTag("SkullOwner", (NBTBase)nbttagcompound2);
/* 2643 */       itemstack.setTagCompound(nbttagcompound3);
/* 2644 */       return itemstack;
/*      */     } 
/*      */ 
/*      */     
/* 2648 */     itemstack.setTagInfo("BlockEntityTag", (NBTBase)nbttagcompound);
/* 2649 */     NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 2650 */     NBTTagList nbttaglist = new NBTTagList();
/* 2651 */     nbttaglist.appendTag((NBTBase)new NBTTagString("(+NBT)"));
/* 2652 */     nbttagcompound1.setTag("Lore", (NBTBase)nbttaglist);
/* 2653 */     itemstack.setTagInfo("display", (NBTBase)nbttagcompound1);
/* 2654 */     return itemstack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CrashReport addGraphicsAndWorldToCrashReport(CrashReport theCrash) {
/* 2663 */     theCrash.getCategory().addCrashSectionCallable("Launched Version", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2667 */             return Minecraft.this.launchedVersion;
/*      */           }
/*      */         });
/* 2670 */     theCrash.getCategory().addCrashSectionCallable("LWJGL", new Callable<String>()
/*      */         {
/*      */           public String call()
/*      */           {
/* 2674 */             return Sys.getVersion();
/*      */           }
/*      */         });
/* 2677 */     theCrash.getCategory().addCrashSectionCallable("OpenGL", new Callable<String>()
/*      */         {
/*      */           public String call()
/*      */           {
/* 2681 */             return String.valueOf(GL11.glGetString(7937)) + " GL version " + GL11.glGetString(7938) + ", " + GL11.glGetString(7936);
/*      */           }
/*      */         });
/* 2684 */     theCrash.getCategory().addCrashSectionCallable("GL Caps", new Callable<String>()
/*      */         {
/*      */           public String call()
/*      */           {
/* 2688 */             return OpenGlHelper.getLogText();
/*      */           }
/*      */         });
/* 2691 */     theCrash.getCategory().addCrashSectionCallable("Using VBOs", new Callable<String>()
/*      */         {
/*      */           public String call()
/*      */           {
/* 2695 */             return Minecraft.this.gameSettings.useVbo ? "Yes" : "No";
/*      */           }
/*      */         });
/* 2698 */     theCrash.getCategory().addCrashSectionCallable("Is Modded", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2702 */             String s = ClientBrandRetriever.getClientModName();
/* 2703 */             return !s.equals("vanilla") ? ("Definitely; Client brand changed to '" + s + "'") : ((Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.");
/*      */           }
/*      */         });
/* 2706 */     theCrash.getCategory().addCrashSectionCallable("Type", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2710 */             return "Client (map_client.txt)";
/*      */           }
/*      */         });
/* 2713 */     theCrash.getCategory().addCrashSectionCallable("Resource Packs", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2717 */             StringBuilder stringbuilder = new StringBuilder();
/*      */             
/* 2719 */             for (Object s : Minecraft.this.gameSettings.resourcePacks) {
/*      */               
/* 2721 */               if (stringbuilder.length() > 0)
/*      */               {
/* 2723 */                 stringbuilder.append(", ");
/*      */               }
/*      */               
/* 2726 */               stringbuilder.append(s);
/*      */               
/* 2728 */               if (Minecraft.this.gameSettings.field_183018_l.contains(s))
/*      */               {
/* 2730 */                 stringbuilder.append(" (incompatible)");
/*      */               }
/*      */             } 
/*      */             
/* 2734 */             return stringbuilder.toString();
/*      */           }
/*      */         });
/* 2737 */     theCrash.getCategory().addCrashSectionCallable("Current Language", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2741 */             return Minecraft.this.mcLanguageManager.getCurrentLanguage().toString();
/*      */           }
/*      */         });
/* 2744 */     theCrash.getCategory().addCrashSectionCallable("Profiler Position", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2748 */             return Minecraft.this.mcProfiler.profilingEnabled ? Minecraft.this.mcProfiler.getNameOfLastSection() : "N/A (disabled)";
/*      */           }
/*      */         });
/* 2751 */     theCrash.getCategory().addCrashSectionCallable("CPU", new Callable<String>()
/*      */         {
/*      */           public String call()
/*      */           {
/* 2755 */             return OpenGlHelper.func_183029_j();
/*      */           }
/*      */         });
/*      */     
/* 2759 */     if (this.theWorld != null)
/*      */     {
/* 2761 */       this.theWorld.addWorldInfoToCrashReport(theCrash);
/*      */     }
/*      */     
/* 2764 */     return theCrash;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Minecraft getMinecraft() {
/* 2772 */     return theMinecraft;
/*      */   }
/*      */ 
/*      */   
/*      */   public ListenableFuture<Object> scheduleResourcesRefresh() {
/* 2777 */     return addScheduledTask(new Runnable()
/*      */         {
/*      */           public void run()
/*      */           {
/* 2781 */             Minecraft.this.refreshResources();
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public void addServerStatsToSnooper(PlayerUsageSnooper playerSnooper) {
/* 2788 */     playerSnooper.addClientStat("fps", Integer.valueOf(debugFPS));
/* 2789 */     playerSnooper.addClientStat("vsync_enabled", Boolean.valueOf(this.gameSettings.enableVsync));
/* 2790 */     playerSnooper.addClientStat("display_frequency", Integer.valueOf(Display.getDisplayMode().getFrequency()));
/* 2791 */     playerSnooper.addClientStat("display_type", this.fullscreen ? "fullscreen" : "windowed");
/* 2792 */     playerSnooper.addClientStat("run_time", Long.valueOf((MinecraftServer.getCurrentTimeMillis() - playerSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L));
/* 2793 */     playerSnooper.addClientStat("current_action", func_181538_aA());
/* 2794 */     String s = (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) ? "little" : "big";
/* 2795 */     playerSnooper.addClientStat("endianness", s);
/* 2796 */     playerSnooper.addClientStat("resource_packs", Integer.valueOf(this.mcResourcePackRepository.getRepositoryEntries().size()));
/* 2797 */     int i = 0;
/*      */     
/* 2799 */     for (ResourcePackRepository.Entry resourcepackrepository$entry : this.mcResourcePackRepository.getRepositoryEntries())
/*      */     {
/* 2801 */       playerSnooper.addClientStat("resource_pack[" + i++ + "]", resourcepackrepository$entry.getResourcePackName());
/*      */     }
/*      */     
/* 2804 */     if (this.theIntegratedServer != null && this.theIntegratedServer.getPlayerUsageSnooper() != null)
/*      */     {
/* 2806 */       playerSnooper.addClientStat("snooper_partner", this.theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private String func_181538_aA() {
/* 2812 */     return (this.theIntegratedServer != null) ? (this.theIntegratedServer.getPublic() ? "hosting_lan" : "singleplayer") : ((this.currentServerData != null) ? (this.currentServerData.func_181041_d() ? "playing_lan" : "multiplayer") : "out_of_game");
/*      */   }
/*      */ 
/*      */   
/*      */   public void addServerTypeToSnooper(PlayerUsageSnooper playerSnooper) {
/* 2817 */     playerSnooper.addStatToSnooper("opengl_version", GL11.glGetString(7938));
/* 2818 */     playerSnooper.addStatToSnooper("opengl_vendor", GL11.glGetString(7936));
/* 2819 */     playerSnooper.addStatToSnooper("client_brand", ClientBrandRetriever.getClientModName());
/* 2820 */     playerSnooper.addStatToSnooper("launched_version", this.launchedVersion);
/* 2821 */     ContextCapabilities contextcapabilities = GLContext.getCapabilities();
/* 2822 */     playerSnooper.addStatToSnooper("gl_caps[ARB_arrays_of_arrays]", Boolean.valueOf(contextcapabilities.GL_ARB_arrays_of_arrays));
/* 2823 */     playerSnooper.addStatToSnooper("gl_caps[ARB_base_instance]", Boolean.valueOf(contextcapabilities.GL_ARB_base_instance));
/* 2824 */     playerSnooper.addStatToSnooper("gl_caps[ARB_blend_func_extended]", Boolean.valueOf(contextcapabilities.GL_ARB_blend_func_extended));
/* 2825 */     playerSnooper.addStatToSnooper("gl_caps[ARB_clear_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_clear_buffer_object));
/* 2826 */     playerSnooper.addStatToSnooper("gl_caps[ARB_color_buffer_float]", Boolean.valueOf(contextcapabilities.GL_ARB_color_buffer_float));
/* 2827 */     playerSnooper.addStatToSnooper("gl_caps[ARB_compatibility]", Boolean.valueOf(contextcapabilities.GL_ARB_compatibility));
/* 2828 */     playerSnooper.addStatToSnooper("gl_caps[ARB_compressed_texture_pixel_storage]", Boolean.valueOf(contextcapabilities.GL_ARB_compressed_texture_pixel_storage));
/* 2829 */     playerSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_compute_shader));
/* 2830 */     playerSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", Boolean.valueOf(contextcapabilities.GL_ARB_copy_buffer));
/* 2831 */     playerSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", Boolean.valueOf(contextcapabilities.GL_ARB_copy_image));
/* 2832 */     playerSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", Boolean.valueOf(contextcapabilities.GL_ARB_depth_buffer_float));
/* 2833 */     playerSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_compute_shader));
/* 2834 */     playerSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", Boolean.valueOf(contextcapabilities.GL_ARB_copy_buffer));
/* 2835 */     playerSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", Boolean.valueOf(contextcapabilities.GL_ARB_copy_image));
/* 2836 */     playerSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", Boolean.valueOf(contextcapabilities.GL_ARB_depth_buffer_float));
/* 2837 */     playerSnooper.addStatToSnooper("gl_caps[ARB_depth_clamp]", Boolean.valueOf(contextcapabilities.GL_ARB_depth_clamp));
/* 2838 */     playerSnooper.addStatToSnooper("gl_caps[ARB_depth_texture]", Boolean.valueOf(contextcapabilities.GL_ARB_depth_texture));
/* 2839 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_buffers));
/* 2840 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers_blend]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_buffers_blend));
/* 2841 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_elements_base_vertex]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_elements_base_vertex));
/* 2842 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_indirect]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_indirect));
/* 2843 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_instanced]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_instanced));
/* 2844 */     playerSnooper.addStatToSnooper("gl_caps[ARB_explicit_attrib_location]", Boolean.valueOf(contextcapabilities.GL_ARB_explicit_attrib_location));
/* 2845 */     playerSnooper.addStatToSnooper("gl_caps[ARB_explicit_uniform_location]", Boolean.valueOf(contextcapabilities.GL_ARB_explicit_uniform_location));
/* 2846 */     playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_layer_viewport]", Boolean.valueOf(contextcapabilities.GL_ARB_fragment_layer_viewport));
/* 2847 */     playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_program]", Boolean.valueOf(contextcapabilities.GL_ARB_fragment_program));
/* 2848 */     playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_fragment_shader));
/* 2849 */     playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_program_shadow]", Boolean.valueOf(contextcapabilities.GL_ARB_fragment_program_shadow));
/* 2850 */     playerSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_framebuffer_object));
/* 2851 */     playerSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_sRGB]", Boolean.valueOf(contextcapabilities.GL_ARB_framebuffer_sRGB));
/* 2852 */     playerSnooper.addStatToSnooper("gl_caps[ARB_geometry_shader4]", Boolean.valueOf(contextcapabilities.GL_ARB_geometry_shader4));
/* 2853 */     playerSnooper.addStatToSnooper("gl_caps[ARB_gpu_shader5]", Boolean.valueOf(contextcapabilities.GL_ARB_gpu_shader5));
/* 2854 */     playerSnooper.addStatToSnooper("gl_caps[ARB_half_float_pixel]", Boolean.valueOf(contextcapabilities.GL_ARB_half_float_pixel));
/* 2855 */     playerSnooper.addStatToSnooper("gl_caps[ARB_half_float_vertex]", Boolean.valueOf(contextcapabilities.GL_ARB_half_float_vertex));
/* 2856 */     playerSnooper.addStatToSnooper("gl_caps[ARB_instanced_arrays]", Boolean.valueOf(contextcapabilities.GL_ARB_instanced_arrays));
/* 2857 */     playerSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_alignment]", Boolean.valueOf(contextcapabilities.GL_ARB_map_buffer_alignment));
/* 2858 */     playerSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_range]", Boolean.valueOf(contextcapabilities.GL_ARB_map_buffer_range));
/* 2859 */     playerSnooper.addStatToSnooper("gl_caps[ARB_multisample]", Boolean.valueOf(contextcapabilities.GL_ARB_multisample));
/* 2860 */     playerSnooper.addStatToSnooper("gl_caps[ARB_multitexture]", Boolean.valueOf(contextcapabilities.GL_ARB_multitexture));
/* 2861 */     playerSnooper.addStatToSnooper("gl_caps[ARB_occlusion_query2]", Boolean.valueOf(contextcapabilities.GL_ARB_occlusion_query2));
/* 2862 */     playerSnooper.addStatToSnooper("gl_caps[ARB_pixel_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_pixel_buffer_object));
/* 2863 */     playerSnooper.addStatToSnooper("gl_caps[ARB_seamless_cube_map]", Boolean.valueOf(contextcapabilities.GL_ARB_seamless_cube_map));
/* 2864 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shader_objects]", Boolean.valueOf(contextcapabilities.GL_ARB_shader_objects));
/* 2865 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shader_stencil_export]", Boolean.valueOf(contextcapabilities.GL_ARB_shader_stencil_export));
/* 2866 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shader_texture_lod]", Boolean.valueOf(contextcapabilities.GL_ARB_shader_texture_lod));
/* 2867 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shadow]", Boolean.valueOf(contextcapabilities.GL_ARB_shadow));
/* 2868 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shadow_ambient]", Boolean.valueOf(contextcapabilities.GL_ARB_shadow_ambient));
/* 2869 */     playerSnooper.addStatToSnooper("gl_caps[ARB_stencil_texturing]", Boolean.valueOf(contextcapabilities.GL_ARB_stencil_texturing));
/* 2870 */     playerSnooper.addStatToSnooper("gl_caps[ARB_sync]", Boolean.valueOf(contextcapabilities.GL_ARB_sync));
/* 2871 */     playerSnooper.addStatToSnooper("gl_caps[ARB_tessellation_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_tessellation_shader));
/* 2872 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_border_clamp]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_border_clamp));
/* 2873 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_buffer_object));
/* 2874 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_cube_map));
/* 2875 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map_array]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_cube_map_array));
/* 2876 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_non_power_of_two]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_non_power_of_two));
/* 2877 */     playerSnooper.addStatToSnooper("gl_caps[ARB_uniform_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_uniform_buffer_object));
/* 2878 */     playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_blend]", Boolean.valueOf(contextcapabilities.GL_ARB_vertex_blend));
/* 2879 */     playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_vertex_buffer_object));
/* 2880 */     playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_program]", Boolean.valueOf(contextcapabilities.GL_ARB_vertex_program));
/* 2881 */     playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_vertex_shader));
/* 2882 */     playerSnooper.addStatToSnooper("gl_caps[EXT_bindable_uniform]", Boolean.valueOf(contextcapabilities.GL_EXT_bindable_uniform));
/* 2883 */     playerSnooper.addStatToSnooper("gl_caps[EXT_blend_equation_separate]", Boolean.valueOf(contextcapabilities.GL_EXT_blend_equation_separate));
/* 2884 */     playerSnooper.addStatToSnooper("gl_caps[EXT_blend_func_separate]", Boolean.valueOf(contextcapabilities.GL_EXT_blend_func_separate));
/* 2885 */     playerSnooper.addStatToSnooper("gl_caps[EXT_blend_minmax]", Boolean.valueOf(contextcapabilities.GL_EXT_blend_minmax));
/* 2886 */     playerSnooper.addStatToSnooper("gl_caps[EXT_blend_subtract]", Boolean.valueOf(contextcapabilities.GL_EXT_blend_subtract));
/* 2887 */     playerSnooper.addStatToSnooper("gl_caps[EXT_draw_instanced]", Boolean.valueOf(contextcapabilities.GL_EXT_draw_instanced));
/* 2888 */     playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_multisample]", Boolean.valueOf(contextcapabilities.GL_EXT_framebuffer_multisample));
/* 2889 */     playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_object]", Boolean.valueOf(contextcapabilities.GL_EXT_framebuffer_object));
/* 2890 */     playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_sRGB]", Boolean.valueOf(contextcapabilities.GL_EXT_framebuffer_sRGB));
/* 2891 */     playerSnooper.addStatToSnooper("gl_caps[EXT_geometry_shader4]", Boolean.valueOf(contextcapabilities.GL_EXT_geometry_shader4));
/* 2892 */     playerSnooper.addStatToSnooper("gl_caps[EXT_gpu_program_parameters]", Boolean.valueOf(contextcapabilities.GL_EXT_gpu_program_parameters));
/* 2893 */     playerSnooper.addStatToSnooper("gl_caps[EXT_gpu_shader4]", Boolean.valueOf(contextcapabilities.GL_EXT_gpu_shader4));
/* 2894 */     playerSnooper.addStatToSnooper("gl_caps[EXT_multi_draw_arrays]", Boolean.valueOf(contextcapabilities.GL_EXT_multi_draw_arrays));
/* 2895 */     playerSnooper.addStatToSnooper("gl_caps[EXT_packed_depth_stencil]", Boolean.valueOf(contextcapabilities.GL_EXT_packed_depth_stencil));
/* 2896 */     playerSnooper.addStatToSnooper("gl_caps[EXT_paletted_texture]", Boolean.valueOf(contextcapabilities.GL_EXT_paletted_texture));
/* 2897 */     playerSnooper.addStatToSnooper("gl_caps[EXT_rescale_normal]", Boolean.valueOf(contextcapabilities.GL_EXT_rescale_normal));
/* 2898 */     playerSnooper.addStatToSnooper("gl_caps[EXT_separate_shader_objects]", Boolean.valueOf(contextcapabilities.GL_EXT_separate_shader_objects));
/* 2899 */     playerSnooper.addStatToSnooper("gl_caps[EXT_shader_image_load_store]", Boolean.valueOf(contextcapabilities.GL_EXT_shader_image_load_store));
/* 2900 */     playerSnooper.addStatToSnooper("gl_caps[EXT_shadow_funcs]", Boolean.valueOf(contextcapabilities.GL_EXT_shadow_funcs));
/* 2901 */     playerSnooper.addStatToSnooper("gl_caps[EXT_shared_texture_palette]", Boolean.valueOf(contextcapabilities.GL_EXT_shared_texture_palette));
/* 2902 */     playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_clear_tag]", Boolean.valueOf(contextcapabilities.GL_EXT_stencil_clear_tag));
/* 2903 */     playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_two_side]", Boolean.valueOf(contextcapabilities.GL_EXT_stencil_two_side));
/* 2904 */     playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_wrap]", Boolean.valueOf(contextcapabilities.GL_EXT_stencil_wrap));
/* 2905 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_3d]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_3d));
/* 2906 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_array]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_array));
/* 2907 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_buffer_object]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_buffer_object));
/* 2908 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_integer]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_integer));
/* 2909 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_lod_bias]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_lod_bias));
/* 2910 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_sRGB]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_sRGB));
/* 2911 */     playerSnooper.addStatToSnooper("gl_caps[EXT_vertex_shader]", Boolean.valueOf(contextcapabilities.GL_EXT_vertex_shader));
/* 2912 */     playerSnooper.addStatToSnooper("gl_caps[EXT_vertex_weighting]", Boolean.valueOf(contextcapabilities.GL_EXT_vertex_weighting));
/* 2913 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_uniforms]", Integer.valueOf(GL11.glGetInteger(35658)));
/* 2914 */     GL11.glGetError();
/* 2915 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_fragment_uniforms]", Integer.valueOf(GL11.glGetInteger(35657)));
/* 2916 */     GL11.glGetError();
/* 2917 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_attribs]", Integer.valueOf(GL11.glGetInteger(34921)));
/* 2918 */     GL11.glGetError();
/* 2919 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_texture_image_units]", Integer.valueOf(GL11.glGetInteger(35660)));
/* 2920 */     GL11.glGetError();
/* 2921 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", Integer.valueOf(GL11.glGetInteger(34930)));
/* 2922 */     GL11.glGetError();
/* 2923 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", Integer.valueOf(GL11.glGetInteger(35071)));
/* 2924 */     GL11.glGetError();
/* 2925 */     playerSnooper.addStatToSnooper("gl_max_texture_size", Integer.valueOf(getGLMaximumTextureSize()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getGLMaximumTextureSize() {
/* 2933 */     for (int i = 16384; i > 0; i >>= 1) {
/*      */       
/* 2935 */       GL11.glTexImage2D(32868, 0, 6408, i, i, 0, 6408, 5121, null);
/* 2936 */       int j = GL11.glGetTexLevelParameteri(32868, 0, 4096);
/*      */       
/* 2938 */       if (j != 0)
/*      */       {
/* 2940 */         return i;
/*      */       }
/*      */     } 
/*      */     
/* 2944 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSnooperEnabled() {
/* 2952 */     return this.gameSettings.snooperEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setServerData(ServerData serverDataIn) {
/* 2960 */     this.currentServerData = serverDataIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public ServerData getCurrentServerData() {
/* 2965 */     return this.currentServerData;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isIntegratedServerRunning() {
/* 2970 */     return this.integratedServerIsRunning;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSingleplayer() {
/* 2978 */     return (this.integratedServerIsRunning && this.theIntegratedServer != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntegratedServer getIntegratedServer() {
/* 2986 */     return this.theIntegratedServer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void stopIntegratedServer() {
/* 2991 */     if (theMinecraft != null) {
/*      */       
/* 2993 */       IntegratedServer integratedserver = theMinecraft.getIntegratedServer();
/*      */       
/* 2995 */       if (integratedserver != null)
/*      */       {
/* 2997 */         integratedserver.stopServer();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PlayerUsageSnooper getPlayerUsageSnooper() {
/* 3007 */     return this.usageSnooper;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getSystemTime() {
/* 3015 */     return Sys.getTime() * 1000L / Sys.getTimerResolution();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFullScreen() {
/* 3023 */     return this.fullscreen;
/*      */   }
/*      */ 
/*      */   
/*      */   public Session getSession() {
/* 3028 */     return this.session;
/*      */   }
/*      */ 
/*      */   
/*      */   public PropertyMap getTwitchDetails() {
/* 3033 */     return this.twitchDetails;
/*      */   }
/*      */ 
/*      */   
/*      */   public PropertyMap func_181037_M() {
/* 3038 */     if (this.field_181038_N.isEmpty()) {
/*      */       
/* 3040 */       GameProfile gameprofile = getSessionService().fillProfileProperties(this.session.getProfile(), false);
/* 3041 */       this.field_181038_N.putAll((Multimap)gameprofile.getProperties());
/*      */     } 
/*      */     
/* 3044 */     return this.field_181038_N;
/*      */   }
/*      */ 
/*      */   
/*      */   public Proxy getProxy() {
/* 3049 */     return this.proxy;
/*      */   }
/*      */ 
/*      */   
/*      */   public TextureManager getTextureManager() {
/* 3054 */     return this.renderEngine;
/*      */   }
/*      */ 
/*      */   
/*      */   public IResourceManager getResourceManager() {
/* 3059 */     return (IResourceManager)this.mcResourceManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public ResourcePackRepository getResourcePackRepository() {
/* 3064 */     return this.mcResourcePackRepository;
/*      */   }
/*      */ 
/*      */   
/*      */   public LanguageManager getLanguageManager() {
/* 3069 */     return this.mcLanguageManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public TextureMap getTextureMapBlocks() {
/* 3074 */     return this.textureMapBlocks;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isJava64bit() {
/* 3079 */     return this.jvm64bit;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isGamePaused() {
/* 3084 */     return this.isGamePaused;
/*      */   }
/*      */ 
/*      */   
/*      */   public SoundHandler getSoundHandler() {
/* 3089 */     return this.mcSoundHandler;
/*      */   }
/*      */ 
/*      */   
/*      */   public MusicTicker.MusicType getAmbientMusicType() {
/* 3094 */     return (this.thePlayer != null) ? ((this.thePlayer.worldObj.provider instanceof net.minecraft.world.WorldProviderHell) ? MusicTicker.MusicType.NETHER : ((this.thePlayer.worldObj.provider instanceof net.minecraft.world.WorldProviderEnd) ? ((BossStatus.bossName != null && BossStatus.statusBarTime > 0) ? MusicTicker.MusicType.END_BOSS : MusicTicker.MusicType.END) : ((this.thePlayer.capabilities.isCreativeMode && this.thePlayer.capabilities.allowFlying) ? MusicTicker.MusicType.CREATIVE : MusicTicker.MusicType.GAME))) : MusicTicker.MusicType.MENU;
/*      */   }
/*      */ 
/*      */   
/*      */   public IStream getTwitchStream() {
/* 3099 */     return this.stream;
/*      */   }
/*      */ 
/*      */   
/*      */   public void dispatchKeypresses() {
/* 3104 */     int i = (Keyboard.getEventKey() == 0) ? Keyboard.getEventCharacter() : Keyboard.getEventKey();
/*      */     
/* 3106 */     if (i != 0 && !Keyboard.isRepeatEvent())
/*      */     {
/* 3108 */       if (!(this.currentScreen instanceof GuiControls) || ((GuiControls)this.currentScreen).time <= getSystemTime() - 20L)
/*      */       {
/* 3110 */         if (Keyboard.getEventKeyState()) {
/*      */           
/* 3112 */           if (i == this.gameSettings.keyBindStreamStartStop.getKeyCode()) {
/*      */             
/* 3114 */             if (getTwitchStream().isBroadcasting())
/*      */             {
/* 3116 */               getTwitchStream().stopBroadcasting();
/*      */             }
/* 3118 */             else if (getTwitchStream().isReadyToBroadcast())
/*      */             {
/* 3120 */               displayGuiScreen((GuiScreen)new GuiYesNo(new GuiYesNoCallback()
/*      */                     {
/*      */                       public void confirmClicked(boolean result, int id)
/*      */                       {
/* 3124 */                         if (result)
/*      */                         {
/* 3126 */                           Minecraft.this.getTwitchStream().func_152930_t();
/*      */                         }
/*      */                         
/* 3129 */                         Minecraft.this.displayGuiScreen(null);
/*      */                       }
/* 3131 */                     },  I18n.format("stream.confirm_start", new Object[0]), "", 0));
/*      */             }
/* 3133 */             else if (getTwitchStream().func_152928_D() && getTwitchStream().func_152936_l())
/*      */             {
/* 3135 */               if (this.theWorld != null)
/*      */               {
/* 3137 */                 this.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentText("Not ready to start streaming yet!"));
/*      */               }
/*      */             }
/*      */             else
/*      */             {
/* 3142 */               GuiStreamUnavailable.func_152321_a(this.currentScreen);
/*      */             }
/*      */           
/* 3145 */           } else if (i == this.gameSettings.keyBindStreamPauseUnpause.getKeyCode()) {
/*      */             
/* 3147 */             if (getTwitchStream().isBroadcasting())
/*      */             {
/* 3149 */               if (getTwitchStream().isPaused())
/*      */               {
/* 3151 */                 getTwitchStream().unpause();
/*      */               }
/*      */               else
/*      */               {
/* 3155 */                 getTwitchStream().pause();
/*      */               }
/*      */             
/*      */             }
/* 3159 */           } else if (i == this.gameSettings.keyBindStreamCommercials.getKeyCode()) {
/*      */             
/* 3161 */             if (getTwitchStream().isBroadcasting())
/*      */             {
/* 3163 */               getTwitchStream().requestCommercial();
/*      */             }
/*      */           }
/* 3166 */           else if (i == this.gameSettings.keyBindStreamToggleMic.getKeyCode()) {
/*      */             
/* 3168 */             this.stream.muteMicrophone(true);
/*      */           }
/* 3170 */           else if (i == this.gameSettings.keyBindFullscreen.getKeyCode()) {
/*      */             
/* 3172 */             toggleFullscreen();
/*      */           }
/* 3174 */           else if (i == this.gameSettings.keyBindScreenshot.getKeyCode()) {
/*      */             
/* 3176 */             this.ingameGUI.getChatGUI().printChatMessage(ScreenShotHelper.saveScreenshot(this.mcDataDir, this.displayWidth, this.displayHeight, this.framebufferMc));
/*      */           }
/*      */         
/* 3179 */         } else if (i == this.gameSettings.keyBindStreamToggleMic.getKeyCode()) {
/*      */           
/* 3181 */           this.stream.muteMicrophone(false);
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public MinecraftSessionService getSessionService() {
/* 3189 */     return this.sessionService;
/*      */   }
/*      */ 
/*      */   
/*      */   public SkinManager getSkinManager() {
/* 3194 */     return this.skinManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public Entity getRenderViewEntity() {
/* 3199 */     return this.renderViewEntity;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRenderViewEntity(Entity viewingEntity) {
/* 3204 */     this.renderViewEntity = viewingEntity;
/* 3205 */     this.entityRenderer.loadEntityShader(viewingEntity);
/*      */   }
/*      */ 
/*      */   
/*      */   public <V> ListenableFuture<V> addScheduledTask(Callable<V> callableToSchedule) {
/* 3210 */     Validate.notNull(callableToSchedule);
/*      */     
/* 3212 */     if (!isCallingFromMinecraftThread()) {
/*      */       
/* 3214 */       ListenableFutureTask<V> listenablefuturetask = ListenableFutureTask.create(callableToSchedule);
/*      */       
/* 3216 */       synchronized (this.scheduledTasks) {
/*      */         
/* 3218 */         this.scheduledTasks.add(listenablefuturetask);
/* 3219 */         return (ListenableFuture<V>)listenablefuturetask;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 3226 */       return Futures.immediateFuture(callableToSchedule.call());
/*      */     }
/* 3228 */     catch (Exception exception) {
/*      */       
/* 3230 */       return (ListenableFuture<V>)Futures.immediateFailedCheckedFuture(exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ListenableFuture<Object> addScheduledTask(Runnable runnableToSchedule) {
/* 3237 */     Validate.notNull(runnableToSchedule);
/* 3238 */     return addScheduledTask(Executors.callable(runnableToSchedule));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCallingFromMinecraftThread() {
/* 3243 */     return (Thread.currentThread() == this.mcThread);
/*      */   }
/*      */ 
/*      */   
/*      */   public BlockRendererDispatcher getBlockRendererDispatcher() {
/* 3248 */     return this.blockRenderDispatcher;
/*      */   }
/*      */ 
/*      */   
/*      */   public RenderManager getRenderManager() {
/* 3253 */     return this.renderManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public RenderItem getRenderItem() {
/* 3258 */     return this.renderItem;
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemRenderer getItemRenderer() {
/* 3263 */     return this.itemRenderer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getDebugFPS() {
/* 3268 */     return debugFPS;
/*      */   }
/*      */ 
/*      */   
/*      */   public FrameTimer func_181539_aj() {
/* 3273 */     return this.field_181542_y;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Map<String, String> getSessionInfo() {
/* 3278 */     Map<String, String> map = Maps.newHashMap();
/* 3279 */     map.put("X-Minecraft-Username", getMinecraft().getSession().getUsername());
/* 3280 */     map.put("X-Minecraft-UUID", getMinecraft().getSession().getPlayerID());
/* 3281 */     map.put("X-Minecraft-Version", "1.8.8");
/* 3282 */     return map;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_181540_al() {
/* 3287 */     return this.field_181541_X;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_181537_a(boolean p_181537_1_) {
/* 3292 */     this.field_181541_X = p_181537_1_;
/*      */   }
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\Minecraft.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */