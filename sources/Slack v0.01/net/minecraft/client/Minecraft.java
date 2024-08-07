package net.minecraft.client;

import cc.slack.Slack;
import cc.slack.events.State;
import cc.slack.events.impl.game.TickEvent;
import cc.slack.events.impl.input.KeyEvent;
import cc.slack.features.modules.impl.exploit.MultiAction;
import cc.slack.features.modules.impl.other.Tweaks;
import cc.slack.features.modules.impl.world.FastPlace;
import cc.slack.utils.player.ItemSpoofUtil;
import cc.slack.utils.player.MovementUtil;
import cc.slack.utils.player.RotationUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import de.florianmichael.viamcp.fixes.AttackOrder;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import javax.imageio.ImageIO;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMemoryErrorScreen;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.FoliageColorReloadListener;
import net.minecraft.client.resources.GrassColorReloadListener;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.resources.ResourceIndex;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
import net.minecraft.client.resources.data.FontMetadataSection;
import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.client.resources.data.PackMetadataSectionSerializer;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.IStatStringFormat;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MinecraftError;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import net.minecraft.util.Util;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.OpenGLException;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

public class Minecraft implements IThreadListener, IPlayerUsage {
   private static final Logger logger = LogManager.getLogger();
   private static final ResourceLocation locationMojangPng = new ResourceLocation("textures/gui/title/mojang.png");
   public static final boolean isRunningOnMac;
   public static byte[] memoryReserve;
   private static final List<DisplayMode> macDisplayModes;
   private final File fileResourcepacks;
   private final PropertyMap field_181038_N;
   private ServerData currentServerData;
   private TextureManager renderEngine;
   private static Minecraft theMinecraft;
   public PlayerControllerMP playerController;
   private boolean fullscreen;
   private boolean enableGLErrorChecking = true;
   private boolean hasCrashed;
   private CrashReport crashReporter;
   public int displayWidth;
   public int displayHeight;
   private boolean field_181541_X = false;
   public Timer timer = new Timer(20.0F);
   private PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("client", this, MinecraftServer.getCurrentTimeMillis());
   public WorldClient theWorld;
   public RenderGlobal renderGlobal;
   private RenderManager renderManager;
   private RenderItem renderItem;
   private ItemRenderer itemRenderer;
   public EntityPlayerSP thePlayer;
   private Entity renderViewEntity;
   public Entity pointedEntity;
   public EffectRenderer effectRenderer;
   public Session session;
   private boolean isGamePaused;
   public FontRenderer MCfontRenderer;
   public FontRenderer standardGalacticFontRenderer;
   public GuiScreen currentScreen;
   public LoadingScreenRenderer loadingScreen;
   public EntityRenderer entityRenderer;
   public int leftClickCounter;
   private int tempDisplayWidth;
   private int tempDisplayHeight;
   private IntegratedServer theIntegratedServer;
   public GuiAchievement guiAchievement;
   public GuiIngame ingameGUI;
   public boolean skipRenderWorld;
   public MovingObjectPosition objectMouseOver;
   public GameSettings gameSettings;
   public MouseHelper mouseHelper;
   public final File mcDataDir;
   private final File fileAssets;
   private final String launchedVersion;
   private final Proxy proxy;
   private ISaveFormat saveLoader;
   private static int debugFPS;
   private int rightClickDelayTimer;
   private String serverName;
   private int serverPort;
   public boolean inGameHasFocus;
   long systemTime = getSystemTime();
   private int joinPlayerCounter;
   public final FrameTimer field_181542_y = new FrameTimer();
   long field_181543_z = System.nanoTime();
   private final boolean jvm64bit;
   private NetworkManager myNetworkManager;
   private boolean integratedServerIsRunning;
   public final Profiler mcProfiler = new Profiler();
   private long debugCrashKeyPressTime = -1L;
   private IReloadableResourceManager mcResourceManager;
   private final IMetadataSerializer metadataSerializer_ = new IMetadataSerializer();
   private final List<IResourcePack> defaultResourcePacks = Lists.newArrayList();
   public final DefaultResourcePack mcDefaultResourcePack;
   private ResourcePackRepository mcResourcePackRepository;
   private LanguageManager mcLanguageManager;
   private Framebuffer framebufferMc;
   private TextureMap textureMapBlocks;
   private SoundHandler mcSoundHandler;
   private MusicTicker mcMusicTicker;
   private ResourceLocation mojangLogo;
   private final MinecraftSessionService sessionService;
   private SkinManager skinManager;
   private final Queue<FutureTask<?>> scheduledTasks = Queues.newArrayDeque();
   private long field_175615_aJ = 0L;
   private final Thread mcThread = Thread.currentThread();
   private ModelManager modelManager;
   private BlockRendererDispatcher blockRenderDispatcher;
   volatile boolean running = true;
   public String debug = "";
   public boolean field_175613_B = false;
   public boolean field_175614_C = false;
   public boolean field_175611_D = false;
   public boolean renderChunksMany = true;
   long debugUpdateTime = getSystemTime();
   int fpsCounter;
   long prevFrameTime = -1L;
   private String debugProfilerName = "root";

   public Minecraft(GameConfiguration gameConfig) {
      theMinecraft = this;
      this.mcDataDir = gameConfig.folderInfo.mcDataDir;
      this.fileAssets = gameConfig.folderInfo.assetsDir;
      this.fileResourcepacks = gameConfig.folderInfo.resourcePacksDir;
      this.launchedVersion = gameConfig.gameInfo.version;
      this.field_181038_N = gameConfig.userInfo.field_181172_c;
      this.mcDefaultResourcePack = new DefaultResourcePack((new ResourceIndex(gameConfig.folderInfo.assetsDir, gameConfig.folderInfo.assetIndex)).getResourceMap());
      this.proxy = gameConfig.userInfo.proxy == null ? Proxy.NO_PROXY : gameConfig.userInfo.proxy;
      this.sessionService = (new YggdrasilAuthenticationService(gameConfig.userInfo.proxy, UUID.randomUUID().toString())).createMinecraftSessionService();
      this.session = gameConfig.userInfo.session;
      logger.info("Setting user: " + this.session.getUsername());
      logger.info("(Session ID is " + this.session.getSessionID() + ")");
      this.displayWidth = gameConfig.displayInfo.width > 0 ? gameConfig.displayInfo.width : 1;
      this.displayHeight = gameConfig.displayInfo.height > 0 ? gameConfig.displayInfo.height : 1;
      this.tempDisplayWidth = gameConfig.displayInfo.width;
      this.tempDisplayHeight = gameConfig.displayInfo.height;
      this.fullscreen = gameConfig.displayInfo.fullscreen;
      this.jvm64bit = isJvm64bit();
      this.theIntegratedServer = new IntegratedServer(this);
      if (gameConfig.serverInfo.serverName != null) {
         this.serverName = gameConfig.serverInfo.serverName;
         this.serverPort = gameConfig.serverInfo.serverPort;
      }

      ImageIO.setUseCache(false);
      Bootstrap.register();
   }

   public void run() {
      this.running = true;

      CrashReport crashreport1;
      try {
         this.startGame();
      } catch (Throwable var11) {
         crashreport1 = CrashReport.makeCrashReport(var11, "Initializing game");
         crashreport1.makeCategory("Initialization");
         this.displayCrashReport(this.addGraphicsAndWorldToCrashReport(crashreport1));
         return;
      }

      try {
         while(this.running) {
            if (this.hasCrashed && this.crashReporter != null) {
               this.displayCrashReport(this.crashReporter);
            } else {
               try {
                  this.runGameLoop();
               } catch (OutOfMemoryError var10) {
                  this.freeMemory();
                  this.displayGuiScreen(new GuiMemoryErrorScreen());
                  System.gc();
               }
            }
         }

         return;
      } catch (MinecraftError var12) {
      } catch (ReportedException var13) {
         this.addGraphicsAndWorldToCrashReport(var13.getCrashReport());
         this.freeMemory();
         logger.fatal("Reported exception thrown!", var13);
         this.displayCrashReport(var13.getCrashReport());
      } catch (Throwable var14) {
         crashreport1 = this.addGraphicsAndWorldToCrashReport(new CrashReport("Unexpected error", var14));
         this.freeMemory();
         logger.fatal("Unreported exception thrown!", var14);
         this.displayCrashReport(crashreport1);
      } finally {
         this.shutdownMinecraftApplet();
      }

   }

   private void startGame() throws LWJGLException, IOException {
      this.gameSettings = new GameSettings(this, this.mcDataDir);
      this.defaultResourcePacks.add(this.mcDefaultResourcePack);
      this.startTimerHackThread();
      if (this.gameSettings.overrideHeight > 0 && this.gameSettings.overrideWidth > 0) {
         this.displayWidth = this.gameSettings.overrideWidth;
         this.displayHeight = this.gameSettings.overrideHeight;
      }

      logger.info("LWJGL Version: " + Sys.getVersion());
      this.setWindowIcon();
      this.setInitialDisplayMode();
      this.createDisplay();
      OpenGlHelper.initializeTextures();
      this.framebufferMc = new Framebuffer(this.displayWidth, this.displayHeight, true);
      this.framebufferMc.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
      this.registerMetadataSerializers();
      this.mcResourcePackRepository = new ResourcePackRepository(this.fileResourcepacks, new File(this.mcDataDir, "server-resource-packs"), this.mcDefaultResourcePack, this.metadataSerializer_, this.gameSettings);
      this.mcResourceManager = new SimpleReloadableResourceManager(this.metadataSerializer_);
      this.mcLanguageManager = new LanguageManager(this.metadataSerializer_, this.gameSettings.language);
      this.mcResourceManager.registerReloadListener(this.mcLanguageManager);
      this.refreshResources();
      this.renderEngine = new TextureManager(this.mcResourceManager);
      this.mcResourceManager.registerReloadListener(this.renderEngine);
      this.drawSplashScreen(this.renderEngine);
      this.skinManager = new SkinManager(this.renderEngine, new File(this.fileAssets, "skins"), this.sessionService);
      this.saveLoader = new AnvilSaveConverter(new File(this.mcDataDir, "saves"));
      this.mcSoundHandler = new SoundHandler(this.mcResourceManager, this.gameSettings);
      this.mcResourceManager.registerReloadListener(this.mcSoundHandler);
      this.mcMusicTicker = new MusicTicker(this);
      this.MCfontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii.png"), this.renderEngine, false);
      if (this.gameSettings.language != null) {
         this.MCfontRenderer.setUnicodeFlag(this.isUnicode());
         this.MCfontRenderer.setBidiFlag(this.mcLanguageManager.isCurrentLanguageBidirectional());
      }

      this.standardGalacticFontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii_sga.png"), this.renderEngine, false);
      this.mcResourceManager.registerReloadListener(this.MCfontRenderer);
      this.mcResourceManager.registerReloadListener(this.standardGalacticFontRenderer);
      this.mcResourceManager.registerReloadListener(new GrassColorReloadListener());
      this.mcResourceManager.registerReloadListener(new FoliageColorReloadListener());
      AchievementList.openInventory.setStatStringFormatter(new IStatStringFormat() {
         public String formatString(String p_74535_1_) {
            try {
               return String.format(p_74535_1_, GameSettings.getKeyDisplayString(Minecraft.this.gameSettings.keyBindInventory.getKeyCode()));
            } catch (Exception var3) {
               return "Error: " + var3.getLocalizedMessage();
            }
         }
      });
      this.mouseHelper = new MouseHelper();
      this.checkGLError("Pre startup");
      GlStateManager.enableTexture2D();
      GlStateManager.shadeModel(7425);
      GlStateManager.clearDepth(1.0D);
      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.cullFace(1029);
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      GlStateManager.matrixMode(5888);
      this.checkGLError("Startup");
      this.textureMapBlocks = new TextureMap("textures");
      this.textureMapBlocks.setMipmapLevels(this.gameSettings.mipmapLevels);
      this.renderEngine.loadTickableTexture(TextureMap.locationBlocksTexture, this.textureMapBlocks);
      this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
      this.textureMapBlocks.setBlurMipmapDirect(false, this.gameSettings.mipmapLevels > 0);
      this.modelManager = new ModelManager(this.textureMapBlocks);
      this.mcResourceManager.registerReloadListener(this.modelManager);
      this.renderItem = new RenderItem(this.renderEngine, this.modelManager);
      this.renderManager = new RenderManager(this.renderEngine, this.renderItem);
      this.itemRenderer = new ItemRenderer(this);
      this.mcResourceManager.registerReloadListener(this.renderItem);
      this.entityRenderer = new EntityRenderer(this, this.mcResourceManager);
      this.mcResourceManager.registerReloadListener(this.entityRenderer);
      this.blockRenderDispatcher = new BlockRendererDispatcher(this.modelManager.getBlockModelShapes(), this.gameSettings);
      this.mcResourceManager.registerReloadListener(this.blockRenderDispatcher);
      this.renderGlobal = new RenderGlobal(this);
      this.mcResourceManager.registerReloadListener(this.renderGlobal);
      this.guiAchievement = new GuiAchievement(this);
      GlStateManager.viewport(0, 0, this.displayWidth, this.displayHeight);
      this.effectRenderer = new EffectRenderer(this.theWorld, this.renderEngine);
      this.checkGLError("Post startup");
      this.ingameGUI = new GuiIngame(this);
      Slack.getInstance().start();
      if (this.serverName != null) {
         this.displayGuiScreen(new GuiConnecting(new GuiMainMenu(), this, this.serverName, this.serverPort));
      } else {
         this.displayGuiScreen(new GuiMainMenu());
      }

      this.renderEngine.deleteTexture(this.mojangLogo);
      this.mojangLogo = null;
      this.loadingScreen = new LoadingScreenRenderer(this);
      if (this.gameSettings.fullScreen && !this.fullscreen) {
         this.toggleFullscreen();
      }

      try {
         Display.setVSyncEnabled(this.gameSettings.enableVsync);
      } catch (OpenGLException var2) {
         this.gameSettings.enableVsync = false;
         this.gameSettings.saveOptions();
      }

      this.renderGlobal.makeEntityOutlineShader();
   }

   private void registerMetadataSerializers() {
      this.metadataSerializer_.registerMetadataSectionType(new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
      this.metadataSerializer_.registerMetadataSectionType(new FontMetadataSectionSerializer(), FontMetadataSection.class);
      this.metadataSerializer_.registerMetadataSectionType(new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
      this.metadataSerializer_.registerMetadataSectionType(new PackMetadataSectionSerializer(), PackMetadataSection.class);
      this.metadataSerializer_.registerMetadataSectionType(new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
   }

   private void createDisplay() throws LWJGLException {
      Display.setResizable(true);
      Display.setTitle("Loading " + Slack.getInstance().getInfo().getName() + " " + Slack.getInstance().getInfo().getVersion() + "-" + Slack.getInstance().getInfo().getType());

      try {
         Display.create((new PixelFormat()).withDepthBits(24));
      } catch (LWJGLException var4) {
         logger.error("Couldn't set pixel format", var4);

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var3) {
         }

         if (this.fullscreen) {
            this.updateDisplayMode();
         }

         Display.create();
      }

   }

   private void setInitialDisplayMode() throws LWJGLException {
      if (this.fullscreen) {
         Display.setFullscreen(true);
         DisplayMode displaymode = Display.getDisplayMode();
         this.displayWidth = Math.max(1, displaymode.getWidth());
         this.displayHeight = Math.max(1, displaymode.getHeight());
      } else {
         Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
      }

   }

   public void setWindowIcon() {
      Util.EnumOS util$enumos = Util.getOSType();
      InputStream smallIcon;
      if (util$enumos != Util.EnumOS.OSX) {
         smallIcon = null;
         InputStream bigIcon = null;

         try {
            smallIcon = this.mcDefaultResourcePack.getInputStream(new ResourceLocation("slack/textures/logo/16.png"));
            bigIcon = this.mcDefaultResourcePack.getInputStream(new ResourceLocation("slack/textures/logo/32.png"));
            if (smallIcon != null && bigIcon != null) {
               Display.setIcon(new ByteBuffer[]{this.readImageToBuffer(smallIcon), this.readImageToBuffer(bigIcon)});
            }
         } catch (IOException var12) {
            logger.error("Couldn't set icon", var12);
         } finally {
            IOUtils.closeQuietly(smallIcon);
            IOUtils.closeQuietly(bigIcon);
         }
      } else {
         try {
            smallIcon = this.mcDefaultResourcePack.getInputStream(new ResourceLocation("slack/textures/logo/32.png"));
            if (smallIcon != null) {
               try {
                  Class<?> Application = Class.forName("com.apple.eawt.Application");
                  Application.getMethod("setDockIconImage", Image.class).invoke(Application.getMethod("getApplication").invoke((Object)null), ImageIO.read(smallIcon));
               } catch (Exception var10) {
                  System.err.println("[ IconUtils ] Error setting dock icon: " + var10.getMessage());
               }

               IOUtils.closeQuietly(smallIcon);
            } else {
               System.err.println("[ IconUtils ] Icon file could not be found");
            }
         } catch (IOException var11) {
            logger.error("Couldn't set icon", var11);
         }
      }

   }

   private static boolean isJvm64bit() {
      String[] astring = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};
      String[] var1 = astring;
      int var2 = astring.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String s = var1[var3];
         String s1 = System.getProperty(s);
         if (s1 != null && s1.contains("64")) {
            return true;
         }
      }

      return false;
   }

   public Framebuffer getFramebuffer() {
      return this.framebufferMc;
   }

   public String getVersion() {
      return this.launchedVersion;
   }

   private void startTimerHackThread() {
      Thread thread = new Thread("Timer hack thread") {
         public void run() {
            while(Minecraft.this.running) {
               try {
                  Thread.sleep(2147483647L);
               } catch (InterruptedException var2) {
               }
            }

         }
      };
      thread.setDaemon(true);
      thread.start();
   }

   public void crashed(CrashReport crash) {
      this.hasCrashed = true;
      this.crashReporter = crash;
   }

   public void displayCrashReport(CrashReport crashReportIn) {
      File file1 = new File(getMinecraft().mcDataDir, "crash-reports");
      File file2 = new File(file1, "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-client.txt");
      Bootstrap.printToSYSOUT(crashReportIn.getCompleteReport());
      if (crashReportIn.getFile() != null) {
         Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + crashReportIn.getFile());
         System.exit(-1);
      } else if (crashReportIn.saveToFile(file2)) {
         Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + file2.getAbsolutePath());
         System.exit(-1);
      } else {
         Bootstrap.printToSYSOUT("#@?@# Game crashed! Crash report could not be saved. #@?@#");
         System.exit(-2);
      }

   }

   public boolean isUnicode() {
      return this.mcLanguageManager.isCurrentLocaleUnicode() || this.gameSettings.forceUnicodeFont;
   }

   public void refreshResources() {
      List<IResourcePack> list = Lists.newArrayList(this.defaultResourcePacks);
      Iterator var2 = this.mcResourcePackRepository.getRepositoryEntries().iterator();

      while(var2.hasNext()) {
         ResourcePackRepository.Entry resourcepackrepository$entry = (ResourcePackRepository.Entry)var2.next();
         list.add(resourcepackrepository$entry.getResourcePack());
      }

      if (this.mcResourcePackRepository.getResourcePackInstance() != null) {
         list.add(this.mcResourcePackRepository.getResourcePackInstance());
      }

      try {
         this.mcResourceManager.reloadResources(list);
      } catch (RuntimeException var4) {
         logger.info("Caught error stitching, removing all assigned resourcepacks", var4);
         list.clear();
         list.addAll(this.defaultResourcePacks);
         this.mcResourcePackRepository.setRepositories(Collections.emptyList());
         this.mcResourceManager.reloadResources(list);
         this.gameSettings.resourcePacks.clear();
         this.gameSettings.field_183018_l.clear();
         this.gameSettings.saveOptions();
      }

      this.mcLanguageManager.parseLanguageMetadata(list);
      if (this.renderGlobal != null) {
         this.renderGlobal.loadRenderers();
      }

   }

   private ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException {
      BufferedImage bufferedimage = ImageIO.read(imageStream);
      int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), (int[])null, 0, bufferedimage.getWidth());
      ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
      int[] var5 = aint;
      int var6 = aint.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         int i = var5[var7];
         bytebuffer.putInt(i << 8 | i >> 24 & 255);
      }

      bytebuffer.flip();
      return bytebuffer;
   }

   private void updateDisplayMode() throws LWJGLException {
      Set<DisplayMode> set = Sets.newHashSet();
      Collections.addAll(set, Display.getAvailableDisplayModes());
      DisplayMode displaymode = Display.getDesktopDisplayMode();
      if (!set.contains(displaymode) && Util.getOSType() == Util.EnumOS.OSX) {
         Iterator var3 = macDisplayModes.iterator();

         label52:
         while(true) {
            while(true) {
               DisplayMode displaymode1;
               boolean flag;
               Iterator iterator;
               DisplayMode displaymode3;
               do {
                  if (!var3.hasNext()) {
                     break label52;
                  }

                  displaymode1 = (DisplayMode)var3.next();
                  flag = true;
                  iterator = set.iterator();

                  while(iterator.hasNext()) {
                     displaymode3 = (DisplayMode)iterator.next();
                     if (displaymode3.getBitsPerPixel() == 32 && displaymode3.getWidth() == displaymode1.getWidth() && displaymode3.getHeight() == displaymode1.getHeight()) {
                        flag = false;
                        break;
                     }
                  }
               } while(flag);

               iterator = set.iterator();

               while(iterator.hasNext()) {
                  displaymode3 = (DisplayMode)iterator.next();
                  if (displaymode3.getBitsPerPixel() == 32 && displaymode3.getWidth() == displaymode1.getWidth() / 2 && displaymode3.getHeight() == displaymode1.getHeight() / 2) {
                     displaymode = displaymode3;
                     break;
                  }
               }
            }
         }
      }

      Display.setDisplayMode(displaymode);
      this.displayWidth = displaymode.getWidth();
      this.displayHeight = displaymode.getHeight();
   }

   private void drawSplashScreen(TextureManager textureManagerInstance) throws LWJGLException {
      ScaledResolution scaledresolution = new ScaledResolution(this);
      int i = scaledresolution.getScaleFactor();
      Framebuffer framebuffer = new Framebuffer(scaledresolution.getScaledWidth() * i, scaledresolution.getScaledHeight() * i, true);
      framebuffer.bindFramebuffer(false);
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      GlStateManager.ortho(0.0D, (double)scaledresolution.getScaledWidth(), (double)scaledresolution.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      GlStateManager.translate(0.0F, 0.0F, -2000.0F);
      GlStateManager.disableLighting();
      GlStateManager.disableFog();
      GlStateManager.disableDepth();
      GlStateManager.enableTexture2D();
      InputStream inputstream = null;

      try {
         inputstream = this.mcDefaultResourcePack.getInputStream(locationMojangPng);
         this.mojangLogo = textureManagerInstance.getDynamicTextureLocation("logo", new DynamicTexture(ImageIO.read(inputstream)));
         textureManagerInstance.bindTexture(this.mojangLogo);
      } catch (IOException var12) {
         logger.error("Unable to load logo: " + locationMojangPng, var12);
      } finally {
         IOUtils.closeQuietly(inputstream);
      }

      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
      worldrenderer.pos(0.0D, (double)this.displayHeight, 0.0D).tex(0.0D, 0.0D).func_181669_b(255, 255, 255, 255).endVertex();
      worldrenderer.pos((double)this.displayWidth, (double)this.displayHeight, 0.0D).tex(0.0D, 0.0D).func_181669_b(255, 255, 255, 255).endVertex();
      worldrenderer.pos((double)this.displayWidth, 0.0D, 0.0D).tex(0.0D, 0.0D).func_181669_b(255, 255, 255, 255).endVertex();
      worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, 0.0D).func_181669_b(255, 255, 255, 255).endVertex();
      tessellator.draw();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      int j = 256;
      int k = 256;
      this.func_181536_a((scaledresolution.getScaledWidth() - j) / 2, (scaledresolution.getScaledHeight() - k) / 2, 0, 0, j, k, 255, 255, 255, 255);
      GlStateManager.disableLighting();
      GlStateManager.disableFog();
      framebuffer.unbindFramebuffer();
      framebuffer.framebufferRender(scaledresolution.getScaledWidth() * i, scaledresolution.getScaledHeight() * i);
      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(516, 0.1F);
      this.updateDisplay();
   }

   public void func_181536_a(int p_181536_1_, int p_181536_2_, int p_181536_3_, int p_181536_4_, int p_181536_5_, int p_181536_6_, int p_181536_7_, int p_181536_8_, int p_181536_9_, int p_181536_10_) {
      float f = 0.00390625F;
      float f1 = 0.00390625F;
      WorldRenderer worldrenderer = Tessellator.getInstance().getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
      worldrenderer.pos((double)p_181536_1_, (double)(p_181536_2_ + p_181536_6_), 0.0D).tex((double)((float)p_181536_3_ * f), (double)((float)(p_181536_4_ + p_181536_6_) * f1)).func_181669_b(p_181536_7_, p_181536_8_, p_181536_9_, p_181536_10_).endVertex();
      worldrenderer.pos((double)(p_181536_1_ + p_181536_5_), (double)(p_181536_2_ + p_181536_6_), 0.0D).tex((double)((float)(p_181536_3_ + p_181536_5_) * f), (double)((float)(p_181536_4_ + p_181536_6_) * f1)).func_181669_b(p_181536_7_, p_181536_8_, p_181536_9_, p_181536_10_).endVertex();
      worldrenderer.pos((double)(p_181536_1_ + p_181536_5_), (double)p_181536_2_, 0.0D).tex((double)((float)(p_181536_3_ + p_181536_5_) * f), (double)((float)p_181536_4_ * f1)).func_181669_b(p_181536_7_, p_181536_8_, p_181536_9_, p_181536_10_).endVertex();
      worldrenderer.pos((double)p_181536_1_, (double)p_181536_2_, 0.0D).tex((double)((float)p_181536_3_ * f), (double)((float)p_181536_4_ * f1)).func_181669_b(p_181536_7_, p_181536_8_, p_181536_9_, p_181536_10_).endVertex();
      Tessellator.getInstance().draw();
   }

   public ISaveFormat getSaveLoader() {
      return this.saveLoader;
   }

   public void displayGuiScreen(GuiScreen guiScreenIn) {
      if (this.currentScreen != null) {
         this.currentScreen.onGuiClosed();
      }

      if (guiScreenIn == null && this.theWorld == null) {
         guiScreenIn = new GuiMainMenu();
      } else if (guiScreenIn == null && this.thePlayer.getHealth() <= 0.0F) {
         guiScreenIn = new GuiGameOver();
      }

      if (guiScreenIn instanceof GuiMainMenu) {
         this.gameSettings.showDebugInfo = false;
         this.ingameGUI.getChatGUI().clearChatMessages();
      }

      this.currentScreen = (GuiScreen)guiScreenIn;
      if (guiScreenIn != null) {
         this.setIngameNotInFocus();
         ScaledResolution scaledresolution = new ScaledResolution(this);
         int i = scaledresolution.getScaledWidth();
         int j = scaledresolution.getScaledHeight();
         ((GuiScreen)guiScreenIn).setWorldAndResolution(this, i, j);
         this.skipRenderWorld = false;
      } else {
         this.mcSoundHandler.resumeSounds();
         this.setIngameFocus();
      }

   }

   private void checkGLError(String message) {
      if (this.enableGLErrorChecking) {
         int i = GL11.glGetError();
         if (i != 0) {
            String s = GLU.gluErrorString(i);
            logger.error("########## GL ERROR ##########");
            logger.error("@ " + message);
            logger.error(i + ": " + s);
         }
      }

   }

   public void shutdownMinecraftApplet() {
      try {
         Slack.getInstance().shutdown();
         logger.info("Stopping!");

         try {
            this.loadWorld((WorldClient)null);
         } catch (Throwable var5) {
         }

         this.mcSoundHandler.unloadSounds();
      } finally {
         Display.destroy();
         if (!this.hasCrashed) {
            System.exit(0);
         }

      }

      System.gc();
   }

   private void runGameLoop() throws IOException {
      long i = System.nanoTime();
      this.mcProfiler.startSection("root");
      if (Display.isCreated() && Display.isCloseRequested()) {
         this.shutdown();
      }

      if (this.isGamePaused && this.theWorld != null) {
         float f = this.timer.renderPartialTicks;
         this.timer.updateTimer();
         this.timer.renderPartialTicks = f;
      } else {
         this.timer.updateTimer();
      }

      this.mcProfiler.startSection("scheduledExecutables");
      synchronized(this.scheduledTasks) {
         while(!this.scheduledTasks.isEmpty()) {
            Util.func_181617_a((FutureTask)this.scheduledTasks.poll(), logger);
         }
      }

      this.mcProfiler.endSection();
      long l = System.nanoTime();
      this.mcProfiler.startSection("tick");

      for(int j = 0; j < this.timer.elapsedTicks; ++j) {
         this.runTick();
      }

      this.mcProfiler.endStartSection("preRenderErrors");
      long i1 = System.nanoTime() - l;
      this.checkGLError("Pre render");
      this.mcProfiler.endStartSection("sound");
      this.mcSoundHandler.setListener(this.thePlayer, this.timer.renderPartialTicks);
      this.mcProfiler.endSection();
      this.mcProfiler.startSection("render");
      GlStateManager.pushMatrix();
      GlStateManager.clear(16640);
      this.framebufferMc.bindFramebuffer(true);
      this.mcProfiler.startSection("display");
      GlStateManager.enableTexture2D();
      if (this.thePlayer != null && this.thePlayer.isEntityInsideOpaqueBlock()) {
         this.gameSettings.thirdPersonView = 0;
      }

      this.mcProfiler.endSection();
      if (!this.skipRenderWorld) {
         this.mcProfiler.endStartSection("gameRenderer");
         this.entityRenderer.func_181560_a(this.timer.renderPartialTicks, i);
         this.mcProfiler.endSection();
      }

      this.mcProfiler.endSection();
      if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart && !this.gameSettings.hideGUI) {
         if (!this.mcProfiler.profilingEnabled) {
            this.mcProfiler.clearProfiling();
         }

         this.mcProfiler.profilingEnabled = true;
         this.displayDebugInfo(i1);
      } else {
         this.mcProfiler.profilingEnabled = false;
         this.prevFrameTime = System.nanoTime();
      }

      this.guiAchievement.updateAchievementWindow();
      this.framebufferMc.unbindFramebuffer();
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      this.framebufferMc.framebufferRender(this.displayWidth, this.displayHeight);
      GlStateManager.popMatrix();
      this.mcProfiler.startSection("root");
      this.updateDisplay();
      Thread.yield();
      this.mcProfiler.startSection("update");
      this.mcProfiler.endStartSection("submit");
      this.mcProfiler.endSection();
      this.checkGLError("Post render");
      ++this.fpsCounter;
      this.isGamePaused = this.isSingleplayer() && this.currentScreen != null && this.currentScreen.doesGuiPauseGame() && !this.theIntegratedServer.getPublic();
      long k = System.nanoTime();
      this.field_181542_y.func_181747_a(k - this.field_181543_z);
      this.field_181543_z = k;

      while(getSystemTime() >= this.debugUpdateTime + 1000L) {
         debugFPS = this.fpsCounter;
         this.debug = String.format("%d fps (%d chunk update%s) T: %s%s%s%s%s", debugFPS, RenderChunk.renderChunksUpdated, RenderChunk.renderChunksUpdated != 1 ? "s" : "", (float)this.gameSettings.limitFramerate == GameSettings.Options.FRAMERATE_LIMIT.getValueMax() ? "inf" : this.gameSettings.limitFramerate, this.gameSettings.enableVsync ? " vsync" : "", this.gameSettings.fancyGraphics ? "" : " fast", this.gameSettings.clouds == 0 ? "" : (this.gameSettings.clouds == 1 ? " fast-clouds" : " fancy-clouds"), OpenGlHelper.useVbo() ? " vbo" : "");
         RenderChunk.renderChunksUpdated = 0;
         this.debugUpdateTime += 1000L;
         this.fpsCounter = 0;
         this.usageSnooper.addMemoryStatsToSnooper();
         if (!this.usageSnooper.isSnooperRunning()) {
            this.usageSnooper.startSnooper();
         }
      }

      if (this.isFramerateLimitBelowMax()) {
         this.mcProfiler.startSection("fpslimit_wait");
         Display.sync(this.getLimitFramerate());
         this.mcProfiler.endSection();
      }

      this.mcProfiler.endSection();
   }

   public void updateDisplay() {
      this.mcProfiler.startSection("display_update");
      Display.update();
      this.mcProfiler.endSection();
      this.checkWindowResize();
   }

   protected void checkWindowResize() {
      if (!this.fullscreen && Display.wasResized()) {
         int i = this.displayWidth;
         int j = this.displayHeight;
         this.displayWidth = Display.getWidth();
         this.displayHeight = Display.getHeight();
         if (this.displayWidth != i || this.displayHeight != j) {
            if (this.displayWidth <= 0) {
               this.displayWidth = 1;
            }

            if (this.displayHeight <= 0) {
               this.displayHeight = 1;
            }

            this.resize(this.displayWidth, this.displayHeight);
         }
      }

   }

   public int getLimitFramerate() {
      return this.currentScreen != null ? 30 : this.gameSettings.limitFramerate;
   }

   public boolean isFramerateLimitBelowMax() {
      return (float)this.getLimitFramerate() < GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
   }

   public void freeMemory() {
      try {
         memoryReserve = new byte[0];
         this.renderGlobal.deleteAllDisplayLists();
      } catch (Throwable var3) {
      }

      try {
         System.gc();
         this.loadWorld((WorldClient)null);
      } catch (Throwable var2) {
      }

      System.gc();
   }

   private void updateDebugProfilerName(int keyCount) {
      List<Profiler.Result> list = this.mcProfiler.getProfilingData(this.debugProfilerName);
      if (list != null && !list.isEmpty()) {
         Profiler.Result profiler$result = (Profiler.Result)list.remove(0);
         if (keyCount == 0) {
            if (profiler$result.field_76331_c.length() > 0) {
               int i = this.debugProfilerName.lastIndexOf(".");
               if (i >= 0) {
                  this.debugProfilerName = this.debugProfilerName.substring(0, i);
               }
            }
         } else {
            --keyCount;
            if (keyCount < list.size() && !((Profiler.Result)list.get(keyCount)).field_76331_c.equals("unspecified")) {
               if (this.debugProfilerName.length() > 0) {
                  this.debugProfilerName = this.debugProfilerName + ".";
               }

               this.debugProfilerName = this.debugProfilerName + ((Profiler.Result)list.get(keyCount)).field_76331_c;
            }
         }
      }

   }

   private void displayDebugInfo(long elapsedTicksTime) {
      if (this.mcProfiler.profilingEnabled) {
         List<Profiler.Result> list = this.mcProfiler.getProfilingData(this.debugProfilerName);
         Profiler.Result profiler$result = (Profiler.Result)list.remove(0);
         GlStateManager.clear(256);
         GlStateManager.matrixMode(5889);
         GlStateManager.enableColorMaterial();
         GlStateManager.loadIdentity();
         GlStateManager.ortho(0.0D, (double)this.displayWidth, (double)this.displayHeight, 0.0D, 1000.0D, 3000.0D);
         GlStateManager.matrixMode(5888);
         GlStateManager.loadIdentity();
         GlStateManager.translate(0.0F, 0.0F, -2000.0F);
         GL11.glLineWidth(1.0F);
         GlStateManager.disableTexture2D();
         Tessellator tessellator = Tessellator.getInstance();
         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
         int i = 160;
         int j = this.displayWidth - i - 10;
         int k = this.displayHeight - i * 2;
         GlStateManager.enableBlend();
         worldrenderer.begin(7, DefaultVertexFormats.field_181706_f);
         worldrenderer.pos((double)((float)j - (float)i * 1.1F), (double)((float)k - (float)i * 0.6F - 16.0F), 0.0D).func_181669_b(200, 0, 0, 0).endVertex();
         worldrenderer.pos((double)((float)j - (float)i * 1.1F), (double)(k + i * 2), 0.0D).func_181669_b(200, 0, 0, 0).endVertex();
         worldrenderer.pos((double)((float)j + (float)i * 1.1F), (double)(k + i * 2), 0.0D).func_181669_b(200, 0, 0, 0).endVertex();
         worldrenderer.pos((double)((float)j + (float)i * 1.1F), (double)((float)k - (float)i * 0.6F - 16.0F), 0.0D).func_181669_b(200, 0, 0, 0).endVertex();
         tessellator.draw();
         GlStateManager.disableBlend();
         double d0 = 0.0D;

         int i1;
         int k2;
         for(int l = 0; l < list.size(); ++l) {
            Profiler.Result profiler$result1 = (Profiler.Result)list.get(l);
            i1 = MathHelper.floor_double(profiler$result1.field_76332_a / 4.0D) + 1;
            worldrenderer.begin(6, DefaultVertexFormats.field_181706_f);
            k2 = profiler$result1.func_76329_a();
            int k1 = k2 >> 16 & 255;
            int l1 = k2 >> 8 & 255;
            int i2 = k2 & 255;
            worldrenderer.pos((double)j, (double)k, 0.0D).func_181669_b(k1, l1, i2, 255).endVertex();

            int i3;
            float f3;
            float f4;
            float f5;
            for(i3 = i1; i3 >= 0; --i3) {
               f3 = (float)((d0 + profiler$result1.field_76332_a * (double)i3 / (double)i1) * 3.141592653589793D * 2.0D / 100.0D);
               f4 = MathHelper.sin(f3) * (float)i;
               f5 = MathHelper.cos(f3) * (float)i * 0.5F;
               worldrenderer.pos((double)((float)j + f4), (double)((float)k - f5), 0.0D).func_181669_b(k1, l1, i2, 255).endVertex();
            }

            tessellator.draw();
            worldrenderer.begin(5, DefaultVertexFormats.field_181706_f);

            for(i3 = i1; i3 >= 0; --i3) {
               f3 = (float)((d0 + profiler$result1.field_76332_a * (double)i3 / (double)i1) * 3.141592653589793D * 2.0D / 100.0D);
               f4 = MathHelper.sin(f3) * (float)i;
               f5 = MathHelper.cos(f3) * (float)i * 0.5F;
               worldrenderer.pos((double)((float)j + f4), (double)((float)k - f5), 0.0D).func_181669_b(k1 >> 1, l1 >> 1, i2 >> 1, 255).endVertex();
               worldrenderer.pos((double)((float)j + f4), (double)((float)k - f5 + 10.0F), 0.0D).func_181669_b(k1 >> 1, l1 >> 1, i2 >> 1, 255).endVertex();
            }

            tessellator.draw();
            d0 += profiler$result1.field_76332_a;
         }

         DecimalFormat decimalformat = new DecimalFormat("##0.00");
         GlStateManager.enableTexture2D();
         String s = "";
         if (!profiler$result.field_76331_c.equals("unspecified")) {
            s = s + "[0] ";
         }

         if (profiler$result.field_76331_c.length() == 0) {
            s = s + "ROOT ";
         } else {
            s = s + profiler$result.field_76331_c + " ";
         }

         i1 = 16777215;
         this.MCfontRenderer.drawStringWithShadow(s, (float)(j - i), (float)(k - i / 2 - 16), i1);
         this.MCfontRenderer.drawStringWithShadow(s = decimalformat.format(profiler$result.field_76330_b) + "%", (float)(j + i - this.MCfontRenderer.getStringWidth(s)), (float)(k - i / 2 - 16), i1);

         for(k2 = 0; k2 < list.size(); ++k2) {
            Profiler.Result profiler$result2 = (Profiler.Result)list.get(k2);
            String s1 = "";
            if (profiler$result2.field_76331_c.equals("unspecified")) {
               s1 = s1 + "[?] ";
            } else {
               s1 = s1 + "[" + (k2 + 1) + "] ";
            }

            s1 = s1 + profiler$result2.field_76331_c;
            this.MCfontRenderer.drawStringWithShadow(s1, (float)(j - i), (float)(k + i / 2 + k2 * 8 + 20), profiler$result2.func_76329_a());
            this.MCfontRenderer.drawStringWithShadow(s1 = decimalformat.format(profiler$result2.field_76332_a) + "%", (float)(j + i - 50 - this.MCfontRenderer.getStringWidth(s1)), (float)(k + i / 2 + k2 * 8 + 20), profiler$result2.func_76329_a());
            this.MCfontRenderer.drawStringWithShadow(s1 = decimalformat.format(profiler$result2.field_76330_b) + "%", (float)(j + i - this.MCfontRenderer.getStringWidth(s1)), (float)(k + i / 2 + k2 * 8 + 20), profiler$result2.func_76329_a());
         }
      }

   }

   public void shutdown() {
      this.running = false;
   }

   public void setIngameFocus() {
      if (Display.isActive() && !this.inGameHasFocus) {
         this.inGameHasFocus = true;
         this.mouseHelper.grabMouseCursor();
         this.displayGuiScreen((GuiScreen)null);
         this.leftClickCounter = 10000;
      }

   }

   public void setIngameNotInFocus() {
      if (this.inGameHasFocus) {
         KeyBinding.unPressAllKeys();
         this.inGameHasFocus = false;
         this.mouseHelper.ungrabMouseCursor();
      }

   }

   public void displayInGameMenu() {
      if (this.currentScreen == null) {
         this.displayGuiScreen(new GuiIngameMenu());
         if (this.isSingleplayer() && !this.theIntegratedServer.getPublic()) {
            this.mcSoundHandler.pauseSounds();
         }
      }

   }

   private void sendClickBlockToController(boolean leftClick) {
      if (!leftClick || ((Tweaks)Slack.getInstance().getModuleManager().getInstance(Tweaks.class)).isToggle() && (Boolean)((Tweaks)Slack.getInstance().getModuleManager().getInstance(Tweaks.class)).noclickdelay.getValue() && this.objectMouseOver != null) {
         this.leftClickCounter = 0;
      }

      if (this.leftClickCounter <= 0 && (!this.thePlayer.isUsingItem() || ((MultiAction)Slack.getInstance().getModuleManager().getInstance(MultiAction.class)).isToggle())) {
         if (leftClick && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            BlockPos blockpos = this.objectMouseOver.getBlockPos();
            if (this.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air && this.playerController.onPlayerDamageBlock(blockpos, this.objectMouseOver.sideHit)) {
               this.effectRenderer.addBlockHitEffects(blockpos, this.objectMouseOver.sideHit);
               this.thePlayer.swingItem();
            }
         } else {
            this.playerController.resetBlockRemoving();
         }
      }

   }

   private void clickMouse() {
      if (this.leftClickCounter <= 0) {
         AttackOrder.sendConditionalSwing(this.objectMouseOver);
         if (this.objectMouseOver == null) {
            logger.error("Null returned as 'hitResult', this shouldn't happen!");
            if (this.playerController.isNotCreative()) {
               this.leftClickCounter = 10;
            }
         } else {
            switch(this.objectMouseOver.typeOfHit) {
            case ENTITY:
               AttackOrder.sendFixedAttack(this.thePlayer, this.objectMouseOver.entityHit);
               break;
            case BLOCK:
               BlockPos blockpos = this.objectMouseOver.getBlockPos();
               if (this.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air) {
                  this.playerController.clickBlock(blockpos, this.objectMouseOver.sideHit);
                  break;
               }
            case MISS:
            default:
               if (this.playerController.isNotCreative()) {
                  this.leftClickCounter = 10;
               }
            }
         }
      }

   }

   private void rightClickMouse() {
      if (!this.playerController.func_181040_m()) {
         this.rightClickDelayTimer = 4;
         if (((FastPlace)Slack.getInstance().getModuleManager().getInstance(FastPlace.class)).isToggle()) {
            this.rightClickDelayTimer = (Integer)((FastPlace)Slack.getInstance().getModuleManager().getInstance(FastPlace.class)).placeDelay.getValue();
         }

         boolean flag = true;
         ItemStack itemstack = this.thePlayer.inventory.getCurrentItem();
         if (this.objectMouseOver == null) {
            logger.warn("Null returned as 'hitResult', this shouldn't happen!");
         } else {
            switch(this.objectMouseOver.typeOfHit) {
            case ENTITY:
               if (this.playerController.func_178894_a(this.thePlayer, this.objectMouseOver.entityHit, this.objectMouseOver)) {
                  flag = false;
               } else if (this.playerController.interactWithEntitySendPacket(this.thePlayer, this.objectMouseOver.entityHit)) {
                  flag = false;
               }
               break;
            case BLOCK:
               BlockPos blockpos = this.objectMouseOver.getBlockPos();
               if (this.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air) {
                  int i = itemstack != null ? itemstack.stackSize : 0;
                  if (this.playerController.onPlayerRightClick(this.thePlayer, this.theWorld, itemstack, blockpos, this.objectMouseOver.sideHit, this.objectMouseOver.hitVec)) {
                     flag = false;
                     this.thePlayer.swingItem();
                  }

                  if (itemstack == null) {
                     return;
                  }

                  if (itemstack.stackSize == 0) {
                     this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.currentItem] = null;
                  } else if (itemstack.stackSize != i || this.playerController.isInCreativeMode()) {
                     this.entityRenderer.itemRenderer.resetEquippedProgress();
                  }
               }
            }
         }

         if (flag) {
            ItemStack itemstack1 = this.thePlayer.inventory.getCurrentItem();
            if (itemstack1 != null && this.playerController.sendUseItem(this.thePlayer, this.theWorld, itemstack1)) {
               this.entityRenderer.itemRenderer.resetEquippedProgress2();
            }
         }
      }

   }

   public void toggleFullscreen() {
      try {
         this.fullscreen = !this.fullscreen;
         this.gameSettings.fullScreen = this.fullscreen;
         if (this.fullscreen) {
            this.updateDisplayMode();
            this.displayWidth = Display.getDisplayMode().getWidth();
            this.displayHeight = Display.getDisplayMode().getHeight();
            if (this.displayWidth <= 0) {
               this.displayWidth = 1;
            }

            if (this.displayHeight <= 0) {
               this.displayHeight = 1;
            }
         } else {
            Display.setDisplayMode(new DisplayMode(this.tempDisplayWidth, this.tempDisplayHeight));
            this.displayWidth = this.tempDisplayWidth;
            this.displayHeight = this.tempDisplayHeight;
            if (this.displayWidth <= 0) {
               this.displayWidth = 1;
            }

            if (this.displayHeight <= 0) {
               this.displayHeight = 1;
            }
         }

         if (this.currentScreen != null) {
            this.resize(this.displayWidth, this.displayHeight);
         } else {
            this.updateFramebufferSize();
         }

         Display.setFullscreen(this.fullscreen);
         Display.setVSyncEnabled(this.gameSettings.enableVsync);
         this.updateDisplay();
      } catch (Exception var2) {
         logger.error("Couldn't toggle fullscreen", var2);
      }

   }

   private void resize(int width, int height) {
      this.displayWidth = Math.max(1, width);
      this.displayHeight = Math.max(1, height);
      if (this.currentScreen != null) {
         ScaledResolution scaledresolution = new ScaledResolution(this);
         this.currentScreen.onResize(this, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight());
      }

      this.loadingScreen = new LoadingScreenRenderer(this);
      this.updateFramebufferSize();
   }

   private void updateFramebufferSize() {
      this.framebufferMc.createBindFramebuffer(this.displayWidth, this.displayHeight);
      if (this.entityRenderer != null) {
         this.entityRenderer.updateShaderGroupSize(this.displayWidth, this.displayHeight);
      }

   }

   public MusicTicker func_181535_r() {
      return this.mcMusicTicker;
   }

   public void runTick() throws IOException {
      TickEvent tickEvent = new TickEvent();
      if (this.thePlayer != null && this.theWorld != null) {
         tickEvent.call();
      }

      if (this.rightClickDelayTimer > 0) {
         --this.rightClickDelayTimer;
      }

      this.mcProfiler.startSection("gui");
      if (!this.isGamePaused) {
         this.ingameGUI.updateTick();
      }

      this.mcProfiler.endSection();
      this.entityRenderer.getMouseOver(1.0F);
      this.mcProfiler.startSection("gameMode");
      int k;
      if (!this.isGamePaused && this.theWorld != null) {
         this.playerController.updateController();
         if (RotationUtil.isEnabled && RotationUtil.strafeFix && !RotationUtil.strictStrafeFix) {
            if (MovementUtil.isBindsMoving()) {
               k = Math.round((RotationUtil.clientRotation[0] - MovementUtil.getBindsDirection(this.thePlayer.rotationYaw)) / 45.0F);
               if (k > 4) {
                  k -= 8;
               }

               if (k < -4) {
                  k += 8;
               }

               this.gameSettings.keyBindForward.pressed = Math.abs(k) <= 1;
               this.gameSettings.keyBindLeft.pressed = k >= 1 && k <= 3;
               this.gameSettings.keyBindBack.pressed = Math.abs(k) >= 3;
               this.gameSettings.keyBindRight.pressed = k >= -3 && k <= -1;
            } else {
               this.gameSettings.keyBindForward.pressed = false;
               this.gameSettings.keyBindRight.pressed = false;
               this.gameSettings.keyBindBack.pressed = false;
               this.gameSettings.keyBindLeft.pressed = false;
            }
         }
      }

      this.mcProfiler.endStartSection("textures");
      if (!this.isGamePaused) {
         this.renderEngine.tick();
      }

      if (this.currentScreen == null && this.thePlayer != null) {
         if (this.thePlayer.getHealth() <= 0.0F) {
            this.displayGuiScreen((GuiScreen)null);
         } else if (this.thePlayer.isPlayerSleeping() && this.theWorld != null) {
            this.displayGuiScreen(new GuiSleepMP());
         }
      } else if (this.currentScreen != null && this.currentScreen instanceof GuiSleepMP && !this.thePlayer.isPlayerSleeping()) {
         this.displayGuiScreen((GuiScreen)null);
      }

      if (this.currentScreen != null) {
         this.leftClickCounter = 10000;
      }

      CrashReport crashreport2;
      CrashReportCategory crashreportcategory2;
      if (this.currentScreen != null) {
         try {
            this.currentScreen.handleInput();
         } catch (Throwable var8) {
            crashreport2 = CrashReport.makeCrashReport(var8, "Updating screen events");
            crashreportcategory2 = crashreport2.makeCategory("Affected screen");
            crashreportcategory2.addCrashSectionCallable("Screen name", new Callable<String>() {
               public String call() throws Exception {
                  return Minecraft.this.currentScreen.getClass().getCanonicalName();
               }
            });
            throw new ReportedException(crashreport2);
         }

         if (this.currentScreen != null) {
            try {
               this.currentScreen.updateScreen();
            } catch (Throwable var7) {
               crashreport2 = CrashReport.makeCrashReport(var7, "Ticking screen");
               crashreportcategory2 = crashreport2.makeCategory("Affected screen");
               crashreportcategory2.addCrashSectionCallable("Screen name", new Callable<String>() {
                  public String call() throws Exception {
                     return Minecraft.this.currentScreen.getClass().getCanonicalName();
                  }
               });
               throw new ReportedException(crashreport2);
            }
         }
      }

      if (this.currentScreen == null || this.currentScreen.allowUserInput) {
         this.mcProfiler.endStartSection("mouse");

         while(Mouse.next()) {
            k = Mouse.getEventButton();
            KeyBinding.setKeyBindState(k - 100, Mouse.getEventButtonState());
            if (Mouse.getEventButtonState()) {
               if (this.thePlayer.isSpectator() && k == 2) {
                  this.ingameGUI.getSpectatorGui().func_175261_b();
               } else {
                  KeyBinding.onTick(k - 100);
               }
            }

            long i1 = getSystemTime() - this.systemTime;
            if (i1 <= 200L) {
               int j = Mouse.getEventDWheel();
               if (j != 0) {
                  if (this.thePlayer.isSpectator()) {
                     j = j < 0 ? -1 : 1;
                     if (this.ingameGUI.getSpectatorGui().func_175262_a()) {
                        this.ingameGUI.getSpectatorGui().func_175259_b(-j);
                     } else {
                        float f = MathHelper.clamp_float(this.thePlayer.capabilities.getFlySpeed() + (float)j * 0.005F, 0.0F, 0.2F);
                        this.thePlayer.capabilities.setFlySpeed(f);
                     }
                  } else {
                     this.thePlayer.inventory.changeCurrentItem(j);
                  }
               }

               if (this.currentScreen == null) {
                  if (!this.inGameHasFocus && Mouse.getEventButtonState()) {
                     this.setIngameFocus();
                  }
               } else if (this.currentScreen != null) {
                  this.currentScreen.handleMouseInput();
               }
            }
         }

         if (this.leftClickCounter > 0) {
            --this.leftClickCounter;
         }

         this.mcProfiler.endStartSection("keyboard");

         label540:
         while(true) {
            do {
               do {
                  do {
                     if (!Keyboard.next()) {
                        break label540;
                     }

                     k = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();
                     KeyBinding.setKeyBindState(k, Keyboard.getEventKeyState());
                     if (Keyboard.getEventKeyState()) {
                        KeyBinding.onTick(k);
                     }

                     if (this.debugCrashKeyPressTime > 0L) {
                        if (getSystemTime() - this.debugCrashKeyPressTime >= 6000L) {
                           throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
                        }

                        if (!Keyboard.isKeyDown(46) || !Keyboard.isKeyDown(61)) {
                           this.debugCrashKeyPressTime = -1L;
                        }
                     } else if (Keyboard.isKeyDown(46) && Keyboard.isKeyDown(61)) {
                        this.debugCrashKeyPressTime = getSystemTime();
                     }

                     this.dispatchKeypresses();
                  } while(!Keyboard.getEventKeyState());

                  if ((new KeyEvent(k)).call().isCanceled()) {
                     break label540;
                  }

                  if (k == 62 && this.entityRenderer != null) {
                     this.entityRenderer.switchUseShader();
                  }

                  if (this.currentScreen != null) {
                     this.currentScreen.handleKeyboardInput();
                  } else {
                     if (k == 1) {
                        this.displayInGameMenu();
                     }

                     if (k == 32 && Keyboard.isKeyDown(61) && this.ingameGUI != null) {
                        this.ingameGUI.getChatGUI().clearChatMessages();
                     }

                     if (k == 31 && Keyboard.isKeyDown(61)) {
                        this.refreshResources();
                     }

                     if (k == 17 && Keyboard.isKeyDown(61)) {
                     }

                     if (k == 18 && Keyboard.isKeyDown(61)) {
                     }

                     if (k == 47 && Keyboard.isKeyDown(61)) {
                     }

                     if (k == 38 && Keyboard.isKeyDown(61)) {
                     }

                     if (k == 22 && Keyboard.isKeyDown(61)) {
                     }

                     if (k == 20 && Keyboard.isKeyDown(61)) {
                        this.refreshResources();
                     }

                     if (k == 33 && Keyboard.isKeyDown(61)) {
                        this.gameSettings.setOptionValue(GameSettings.Options.RENDER_DISTANCE, GuiScreen.isShiftKeyDown() ? -1 : 1);
                     }

                     if (k == 30 && Keyboard.isKeyDown(61)) {
                        this.renderGlobal.loadRenderers();
                     }

                     if (k == 35 && Keyboard.isKeyDown(61)) {
                        this.gameSettings.advancedItemTooltips = !this.gameSettings.advancedItemTooltips;
                        this.gameSettings.saveOptions();
                     }

                     if (k == 48 && Keyboard.isKeyDown(61)) {
                        this.renderManager.setDebugBoundingBox(!this.renderManager.isDebugBoundingBox());
                     }

                     if (k == 25 && Keyboard.isKeyDown(61)) {
                        this.gameSettings.pauseOnLostFocus = !this.gameSettings.pauseOnLostFocus;
                        this.gameSettings.saveOptions();
                     }

                     if (k == 59) {
                        this.gameSettings.hideGUI = !this.gameSettings.hideGUI;
                     }

                     if (k == 61) {
                        this.gameSettings.showDebugInfo = !this.gameSettings.showDebugInfo;
                        this.gameSettings.showDebugProfilerChart = GuiScreen.isShiftKeyDown();
                        this.gameSettings.field_181657_aC = GuiScreen.isAltKeyDown();
                     }

                     if (this.gameSettings.keyBindTogglePerspective.isPressed()) {
                        ++this.gameSettings.thirdPersonView;
                        if (this.gameSettings.thirdPersonView > 2) {
                           this.gameSettings.thirdPersonView = 0;
                        }

                        if (this.gameSettings.thirdPersonView == 0) {
                           this.entityRenderer.loadEntityShader(this.getRenderViewEntity());
                        } else if (this.gameSettings.thirdPersonView == 1) {
                           this.entityRenderer.loadEntityShader((Entity)null);
                        }

                        this.renderGlobal.setDisplayListEntitiesDirty();
                     }

                     if (this.gameSettings.keyBindSmoothCamera.isPressed()) {
                        this.gameSettings.smoothCamera = !this.gameSettings.smoothCamera;
                     }
                  }
               } while(!this.gameSettings.showDebugInfo);
            } while(!this.gameSettings.showDebugProfilerChart);

            if (k == 11) {
               this.updateDebugProfilerName(0);
            }

            for(int j1 = 0; j1 < 9; ++j1) {
               if (k == 2 + j1) {
                  this.updateDebugProfilerName(j1 + 1);
               }
            }
         }

         for(k = 0; k < 9; ++k) {
            if (this.gameSettings.keyBindsHotbar[k].isPressed()) {
               if (this.thePlayer.isSpectator()) {
                  this.ingameGUI.getSpectatorGui().func_175260_a(k);
               } else if (ItemSpoofUtil.isEnabled) {
                  ItemSpoofUtil.renderSlot = k;
               } else {
                  this.thePlayer.inventory.currentItem = k;
               }
            }
         }

         boolean flag = this.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN;

         while(this.gameSettings.keyBindInventory.isPressed()) {
            if (this.playerController.isRidingHorse()) {
               this.thePlayer.sendHorseInventory();
            } else {
               this.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
               this.displayGuiScreen(new GuiInventory(this.thePlayer));
            }
         }

         while(this.gameSettings.keyBindDrop.isPressed()) {
            if (!this.thePlayer.isSpectator()) {
               this.thePlayer.dropOneItem(GuiScreen.isCtrlKeyDown());
            }
         }

         while(this.gameSettings.keyBindChat.isPressed() && flag) {
            this.displayGuiScreen(new GuiChat());
         }

         if (this.currentScreen == null && this.gameSettings.keyBindCommand.isPressed() && flag) {
            this.displayGuiScreen(new GuiChat("/"));
         }

         if (this.thePlayer.isUsingItem()) {
            if (!this.gameSettings.keyBindUseItem.isKeyDown()) {
               this.playerController.onStoppedUsingItem(this.thePlayer);
            }

            while(true) {
               if (!this.gameSettings.keyBindAttack.isPressed()) {
                  while(this.gameSettings.keyBindUseItem.isPressed()) {
                  }

                  while(this.gameSettings.keyBindPickBlock.isPressed()) {
                  }
                  break;
               }
            }
         } else {
            while(this.gameSettings.keyBindAttack.isPressed()) {
               this.clickMouse();
            }

            while(this.gameSettings.keyBindUseItem.isPressed()) {
               this.rightClickMouse();
            }

            while(this.gameSettings.keyBindPickBlock.isPressed()) {
               this.middleClickMouse();
            }
         }

         if (this.gameSettings.keyBindUseItem.isKeyDown() && this.rightClickDelayTimer == 0 && !this.thePlayer.isUsingItem()) {
            this.rightClickMouse();
         }

         this.sendClickBlockToController(this.currentScreen == null && this.gameSettings.keyBindAttack.isKeyDown() && this.inGameHasFocus);
      }

      tickEvent.setState(State.POST);
      tickEvent.call();
      if (this.theWorld != null) {
         if (this.thePlayer != null) {
            ++this.joinPlayerCounter;
            if (this.joinPlayerCounter == 30) {
               this.joinPlayerCounter = 0;
               this.theWorld.joinEntityInSurroundings(this.thePlayer);
            }
         }

         this.mcProfiler.endStartSection("gameRenderer");
         if (!this.isGamePaused) {
            this.entityRenderer.updateRenderer();
         }

         this.mcProfiler.endStartSection("levelRenderer");
         if (!this.isGamePaused) {
            this.renderGlobal.updateClouds();
         }

         this.mcProfiler.endStartSection("level");
         if (!this.isGamePaused) {
            if (this.theWorld.getLastLightningBolt() > 0) {
               this.theWorld.setLastLightningBolt(this.theWorld.getLastLightningBolt() - 1);
            }

            this.theWorld.updateEntities();
         }
      } else if (this.entityRenderer.isShaderActive()) {
         this.entityRenderer.func_181022_b();
      }

      if (!this.isGamePaused) {
         this.mcMusicTicker.update();
         this.mcSoundHandler.update();
      }

      if (this.theWorld != null) {
         if (!this.isGamePaused) {
            this.theWorld.setAllowedSpawnTypes(this.theWorld.getDifficulty() != EnumDifficulty.PEACEFUL, true);

            try {
               this.theWorld.tick();
            } catch (Throwable var9) {
               crashreport2 = CrashReport.makeCrashReport(var9, "Exception in world tick");
               if (this.theWorld == null) {
                  crashreportcategory2 = crashreport2.makeCategory("Affected level");
                  crashreportcategory2.addCrashSection("Problem", "Level is null!");
               } else {
                  this.theWorld.addWorldInfoToCrashReport(crashreport2);
               }

               throw new ReportedException(crashreport2);
            }
         }

         this.mcProfiler.endStartSection("animateTick");
         if (!this.isGamePaused && this.theWorld != null) {
            this.theWorld.doVoidFogParticles(MathHelper.floor_double(this.thePlayer.posX), MathHelper.floor_double(this.thePlayer.posY), MathHelper.floor_double(this.thePlayer.posZ));
         }

         this.mcProfiler.endStartSection("particles");
         if (!this.isGamePaused) {
            this.effectRenderer.updateEffects();
         }
      } else if (this.myNetworkManager != null) {
         this.mcProfiler.endStartSection("pendingConnection");
         this.myNetworkManager.processReceivedPackets();
      }

      this.mcProfiler.endSection();
      this.systemTime = getSystemTime();
   }

   public void launchIntegratedServer(String folderName, String worldName, WorldSettings worldSettingsIn) {
      this.loadWorld((WorldClient)null);
      System.gc();
      ISaveHandler isavehandler = this.saveLoader.getSaveLoader(folderName, false);
      WorldInfo worldinfo = isavehandler.loadWorldInfo();
      if (worldinfo == null && worldSettingsIn != null) {
         worldinfo = new WorldInfo(worldSettingsIn, folderName);
         isavehandler.saveWorldInfo(worldinfo);
      }

      if (worldSettingsIn == null) {
         worldSettingsIn = new WorldSettings(worldinfo);
      }

      try {
         this.theIntegratedServer = new IntegratedServer(this, folderName, worldName, worldSettingsIn);
         this.theIntegratedServer.startServerThread();
         this.integratedServerIsRunning = true;
      } catch (Throwable var10) {
         CrashReport crashreport = CrashReport.makeCrashReport(var10, "Starting integrated server");
         CrashReportCategory crashreportcategory = crashreport.makeCategory("Starting integrated server");
         crashreportcategory.addCrashSection("Level ID", folderName);
         crashreportcategory.addCrashSection("Level Name", worldName);
         throw new ReportedException(crashreport);
      }

      this.loadingScreen.displaySavingString(I18n.format("menu.loadingLevel"));

      while(!this.theIntegratedServer.serverIsInRunLoop()) {
         String s = this.theIntegratedServer.getUserMessage();
         if (s != null) {
            this.loadingScreen.displayLoadingString(I18n.format(s));
         } else {
            this.loadingScreen.displayLoadingString("");
         }

         try {
            Thread.sleep(200L);
         } catch (InterruptedException var9) {
         }
      }

      this.displayGuiScreen((GuiScreen)null);
      SocketAddress socketaddress = this.theIntegratedServer.getNetworkSystem().addLocalEndpoint();
      NetworkManager networkmanager = NetworkManager.provideLocalClient(socketaddress);
      networkmanager.setNetHandler(new NetHandlerLoginClient(networkmanager, this, (GuiScreen)null));
      networkmanager.sendPacket(new C00Handshake(47, socketaddress.toString(), 0, EnumConnectionState.LOGIN));
      networkmanager.sendPacket(new C00PacketLoginStart(this.getSession().getProfile()));
      this.myNetworkManager = networkmanager;
   }

   public void loadWorld(WorldClient worldClientIn) {
      this.loadWorld(worldClientIn, "");
   }

   public void loadWorld(WorldClient worldClientIn, String loadingMessage) {
      if (worldClientIn == null) {
         NetHandlerPlayClient nethandlerplayclient = this.getNetHandler();
         if (nethandlerplayclient != null) {
            nethandlerplayclient.cleanup();
         }

         if (this.theIntegratedServer != null && this.theIntegratedServer.isAnvilFileSet()) {
            this.theIntegratedServer.initiateShutdown();
            this.theIntegratedServer.setStaticInstance();
         }

         this.theIntegratedServer = null;
         this.guiAchievement.clearAchievements();
         this.entityRenderer.getMapItemRenderer().clearLoadedMaps();
      }

      this.renderViewEntity = null;
      this.myNetworkManager = null;
      if (this.loadingScreen != null) {
         this.loadingScreen.resetProgressAndMessage(loadingMessage);
         this.loadingScreen.displayLoadingString("");
      }

      if (worldClientIn == null && this.theWorld != null) {
         this.mcResourcePackRepository.func_148529_f();
         this.ingameGUI.func_181029_i();
         this.setServerData((ServerData)null);
         this.integratedServerIsRunning = false;
      }

      this.mcSoundHandler.stopSounds();
      this.theWorld = worldClientIn;
      if (worldClientIn != null) {
         if (this.renderGlobal != null) {
            this.renderGlobal.setWorldAndLoadRenderers(worldClientIn);
         }

         if (this.effectRenderer != null) {
            this.effectRenderer.clearEffects(worldClientIn);
         }

         if (this.thePlayer == null) {
            this.thePlayer = this.playerController.func_178892_a(worldClientIn, new StatFileWriter());
            this.playerController.flipPlayer(this.thePlayer);
         }

         this.thePlayer.preparePlayerToSpawn();
         worldClientIn.spawnEntityInWorld(this.thePlayer);
         this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
         this.playerController.setPlayerCapabilities(this.thePlayer);
         this.renderViewEntity = this.thePlayer;
      } else {
         this.saveLoader.flushCache();
         this.thePlayer = null;
      }

      System.gc();
      this.systemTime = 0L;
   }

   public void setDimensionAndSpawnPlayer(int dimension) {
      this.theWorld.setInitialSpawnLocation();
      this.theWorld.removeAllEntities();
      int i = 0;
      String s = null;
      if (this.thePlayer != null) {
         i = this.thePlayer.getEntityId();
         this.theWorld.removeEntity(this.thePlayer);
         s = this.thePlayer.getClientBrand();
      }

      this.renderViewEntity = null;
      EntityPlayerSP entityplayersp = this.thePlayer;
      this.thePlayer = this.playerController.func_178892_a(this.theWorld, this.thePlayer == null ? new StatFileWriter() : this.thePlayer.getStatFileWriter());
      this.thePlayer.getDataWatcher().updateWatchedObjectsFromList(entityplayersp.getDataWatcher().getAllWatched());
      this.thePlayer.dimension = dimension;
      this.renderViewEntity = this.thePlayer;
      this.thePlayer.preparePlayerToSpawn();
      this.thePlayer.setClientBrand(s);
      this.theWorld.spawnEntityInWorld(this.thePlayer);
      this.playerController.flipPlayer(this.thePlayer);
      this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
      this.thePlayer.setEntityId(i);
      this.playerController.setPlayerCapabilities(this.thePlayer);
      this.thePlayer.setReducedDebug(entityplayersp.hasReducedDebug());
      if (this.currentScreen instanceof GuiGameOver) {
         this.displayGuiScreen((GuiScreen)null);
      }

   }

   public NetHandlerPlayClient getNetHandler() {
      return this.thePlayer != null ? this.thePlayer.sendQueue : null;
   }

   public static boolean isGuiEnabled() {
      return theMinecraft == null || !theMinecraft.gameSettings.hideGUI;
   }

   public static boolean isFancyGraphicsEnabled() {
      return theMinecraft != null && theMinecraft.gameSettings.fancyGraphics;
   }

   public static boolean isAmbientOcclusionEnabled() {
      return theMinecraft != null && theMinecraft.gameSettings.ambientOcclusion != 0;
   }

   private void middleClickMouse() {
      if (this.objectMouseOver != null) {
         boolean flag = this.thePlayer.capabilities.isCreativeMode;
         int i = 0;
         boolean flag1 = false;
         TileEntity tileentity = null;
         Object item;
         ItemStack itemstack;
         if (this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            BlockPos blockpos = this.objectMouseOver.getBlockPos();
            Block block = this.theWorld.getBlockState(blockpos).getBlock();
            if (block.getMaterial() == Material.air) {
               return;
            }

            item = block.getItem(this.theWorld, blockpos);
            if (item == null) {
               return;
            }

            if (flag && GuiScreen.isCtrlKeyDown()) {
               tileentity = this.theWorld.getTileEntity(blockpos);
            }

            Block block1 = item instanceof ItemBlock && !block.isFlowerPot() ? Block.getBlockFromItem((Item)item) : block;
            i = block1.getDamageValue(this.theWorld, blockpos);
            flag1 = ((Item)item).getHasSubtypes();
         } else {
            if (this.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY || this.objectMouseOver.entityHit == null || !flag) {
               return;
            }

            if (this.objectMouseOver.entityHit instanceof EntityPainting) {
               item = Items.painting;
            } else if (this.objectMouseOver.entityHit instanceof EntityLeashKnot) {
               item = Items.lead;
            } else if (this.objectMouseOver.entityHit instanceof EntityItemFrame) {
               EntityItemFrame entityitemframe = (EntityItemFrame)this.objectMouseOver.entityHit;
               itemstack = entityitemframe.getDisplayedItem();
               if (itemstack == null) {
                  item = Items.item_frame;
               } else {
                  item = itemstack.getItem();
                  i = itemstack.getMetadata();
                  flag1 = true;
               }
            } else if (this.objectMouseOver.entityHit instanceof EntityMinecart) {
               EntityMinecart entityminecart = (EntityMinecart)this.objectMouseOver.entityHit;
               switch(entityminecart.getMinecartType()) {
               case FURNACE:
                  item = Items.furnace_minecart;
                  break;
               case CHEST:
                  item = Items.chest_minecart;
                  break;
               case TNT:
                  item = Items.tnt_minecart;
                  break;
               case HOPPER:
                  item = Items.hopper_minecart;
                  break;
               case COMMAND_BLOCK:
                  item = Items.command_block_minecart;
                  break;
               default:
                  item = Items.minecart;
               }
            } else if (this.objectMouseOver.entityHit instanceof EntityBoat) {
               item = Items.boat;
            } else if (this.objectMouseOver.entityHit instanceof EntityArmorStand) {
               item = Items.armor_stand;
            } else {
               item = Items.spawn_egg;
               i = EntityList.getEntityID(this.objectMouseOver.entityHit);
               flag1 = true;
               if (!EntityList.entityEggs.containsKey(i)) {
                  return;
               }
            }
         }

         InventoryPlayer inventoryplayer = this.thePlayer.inventory;
         if (tileentity == null) {
            inventoryplayer.setCurrentItem((Item)item, i, flag1, flag);
         } else {
            itemstack = this.func_181036_a((Item)item, i, tileentity);
            inventoryplayer.setInventorySlotContents(inventoryplayer.currentItem, itemstack);
         }

         if (flag) {
            int j = this.thePlayer.inventoryContainer.inventorySlots.size() - 9 + inventoryplayer.currentItem;
            this.playerController.sendSlotPacket(inventoryplayer.getStackInSlot(inventoryplayer.currentItem), j);
         }
      }

   }

   private ItemStack func_181036_a(Item p_181036_1_, int p_181036_2_, TileEntity p_181036_3_) {
      ItemStack itemstack = new ItemStack(p_181036_1_, 1, p_181036_2_);
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      p_181036_3_.writeToNBT(nbttagcompound);
      NBTTagCompound nbttagcompound1;
      if (p_181036_1_ == Items.skull && nbttagcompound.hasKey("Owner")) {
         nbttagcompound1 = nbttagcompound.getCompoundTag("Owner");
         NBTTagCompound nbttagcompound3 = new NBTTagCompound();
         nbttagcompound3.setTag("SkullOwner", nbttagcompound1);
         itemstack.setTagCompound(nbttagcompound3);
      } else {
         itemstack.setTagInfo("BlockEntityTag", nbttagcompound);
         nbttagcompound1 = new NBTTagCompound();
         NBTTagList nbttaglist = new NBTTagList();
         nbttaglist.appendTag(new NBTTagString("(+NBT)"));
         nbttagcompound1.setTag("Lore", nbttaglist);
         itemstack.setTagInfo("display", nbttagcompound1);
      }

      return itemstack;
   }

   public CrashReport addGraphicsAndWorldToCrashReport(CrashReport theCrash) {
      theCrash.getCategory().addCrashSectionCallable("Launched Version", new Callable<String>() {
         public String call() throws Exception {
            return Minecraft.this.launchedVersion;
         }
      });
      theCrash.getCategory().addCrashSectionCallable("LWJGL", new Callable<String>() {
         public String call() {
            return Sys.getVersion();
         }
      });
      theCrash.getCategory().addCrashSectionCallable("OpenGL", new Callable<String>() {
         public String call() {
            return GL11.glGetString(7937) + " GL version " + GL11.glGetString(7938) + ", " + GL11.glGetString(7936);
         }
      });
      theCrash.getCategory().addCrashSectionCallable("GL Caps", new Callable<String>() {
         public String call() {
            return OpenGlHelper.getLogText();
         }
      });
      theCrash.getCategory().addCrashSectionCallable("Using VBOs", new Callable<String>() {
         public String call() {
            return Minecraft.this.gameSettings.useVbo ? "Yes" : "No";
         }
      });
      theCrash.getCategory().addCrashSectionCallable("Is Modded", new Callable<String>() {
         public String call() throws Exception {
            String s = ClientBrandRetriever.getClientModName();
            return !s.equals("vanilla") ? "Definitely; Client brand changed to '" + s + "'" : (Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.");
         }
      });
      theCrash.getCategory().addCrashSectionCallable("Type", new Callable<String>() {
         public String call() throws Exception {
            return "Client (map_client.txt)";
         }
      });
      theCrash.getCategory().addCrashSectionCallable("Resource Packs", new Callable<String>() {
         public String call() throws Exception {
            StringBuilder stringbuilder = new StringBuilder();
            Iterator var2 = Minecraft.this.gameSettings.resourcePacks.iterator();

            while(var2.hasNext()) {
               String s = (String)var2.next();
               if (stringbuilder.length() > 0) {
                  stringbuilder.append(", ");
               }

               stringbuilder.append(s);
               if (Minecraft.this.gameSettings.field_183018_l.contains(s)) {
                  stringbuilder.append(" (incompatible)");
               }
            }

            return stringbuilder.toString();
         }
      });
      theCrash.getCategory().addCrashSectionCallable("Current Language", new Callable<String>() {
         public String call() throws Exception {
            return Minecraft.this.mcLanguageManager.getCurrentLanguage().toString();
         }
      });
      theCrash.getCategory().addCrashSectionCallable("Profiler Position", new Callable<String>() {
         public String call() throws Exception {
            return Minecraft.this.mcProfiler.profilingEnabled ? Minecraft.this.mcProfiler.getNameOfLastSection() : "N/A (disabled)";
         }
      });
      theCrash.getCategory().addCrashSectionCallable("CPU", new Callable<String>() {
         public String call() {
            return OpenGlHelper.func_183029_j();
         }
      });
      if (this.theWorld != null) {
         this.theWorld.addWorldInfoToCrashReport(theCrash);
      }

      return theCrash;
   }

   public static Minecraft getMinecraft() {
      return theMinecraft;
   }

   public static void setMinecraft(Minecraft mc) {
      theMinecraft = mc;
   }

   public ListenableFuture<Object> scheduleResourcesRefresh() {
      return this.addScheduledTask(new Runnable() {
         public void run() {
            Minecraft.this.refreshResources();
         }
      });
   }

   public void addServerStatsToSnooper(PlayerUsageSnooper playerSnooper) {
      playerSnooper.addClientStat("fps", debugFPS);
      playerSnooper.addClientStat("vsync_enabled", this.gameSettings.enableVsync);
      playerSnooper.addClientStat("display_frequency", Display.getDisplayMode().getFrequency());
      playerSnooper.addClientStat("display_type", this.fullscreen ? "fullscreen" : "windowed");
      playerSnooper.addClientStat("run_time", (MinecraftServer.getCurrentTimeMillis() - playerSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L);
      playerSnooper.addClientStat("current_action", this.func_181538_aA());
      String s = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? "little" : "big";
      playerSnooper.addClientStat("endianness", s);
      playerSnooper.addClientStat("resource_packs", this.mcResourcePackRepository.getRepositoryEntries().size());
      int i = 0;
      Iterator var4 = this.mcResourcePackRepository.getRepositoryEntries().iterator();

      while(var4.hasNext()) {
         ResourcePackRepository.Entry resourcepackrepository$entry = (ResourcePackRepository.Entry)var4.next();
         playerSnooper.addClientStat("resource_pack[" + i++ + "]", resourcepackrepository$entry.getResourcePackName());
      }

      if (this.theIntegratedServer != null && this.theIntegratedServer.getPlayerUsageSnooper() != null) {
         playerSnooper.addClientStat("snooper_partner", this.theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
      }

   }

   private String func_181538_aA() {
      return this.theIntegratedServer != null ? (this.theIntegratedServer.getPublic() ? "hosting_lan" : "singleplayer") : (this.currentServerData != null ? (this.currentServerData.func_181041_d() ? "playing_lan" : "multiplayer") : "out_of_game");
   }

   public void addServerTypeToSnooper(PlayerUsageSnooper playerSnooper) {
      playerSnooper.addStatToSnooper("opengl_version", GL11.glGetString(7938));
      playerSnooper.addStatToSnooper("opengl_vendor", GL11.glGetString(7936));
      playerSnooper.addStatToSnooper("client_brand", ClientBrandRetriever.getClientModName());
      playerSnooper.addStatToSnooper("launched_version", this.launchedVersion);
      ContextCapabilities contextcapabilities = GLContext.getCapabilities();
      playerSnooper.addStatToSnooper("gl_caps[ARB_arrays_of_arrays]", contextcapabilities.GL_ARB_arrays_of_arrays);
      playerSnooper.addStatToSnooper("gl_caps[ARB_base_instance]", contextcapabilities.GL_ARB_base_instance);
      playerSnooper.addStatToSnooper("gl_caps[ARB_blend_func_extended]", contextcapabilities.GL_ARB_blend_func_extended);
      playerSnooper.addStatToSnooper("gl_caps[ARB_clear_buffer_object]", contextcapabilities.GL_ARB_clear_buffer_object);
      playerSnooper.addStatToSnooper("gl_caps[ARB_color_buffer_float]", contextcapabilities.GL_ARB_color_buffer_float);
      playerSnooper.addStatToSnooper("gl_caps[ARB_compatibility]", contextcapabilities.GL_ARB_compatibility);
      playerSnooper.addStatToSnooper("gl_caps[ARB_compressed_texture_pixel_storage]", contextcapabilities.GL_ARB_compressed_texture_pixel_storage);
      playerSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", contextcapabilities.GL_ARB_compute_shader);
      playerSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", contextcapabilities.GL_ARB_copy_buffer);
      playerSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", contextcapabilities.GL_ARB_copy_image);
      playerSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", contextcapabilities.GL_ARB_depth_buffer_float);
      playerSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", contextcapabilities.GL_ARB_compute_shader);
      playerSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", contextcapabilities.GL_ARB_copy_buffer);
      playerSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", contextcapabilities.GL_ARB_copy_image);
      playerSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", contextcapabilities.GL_ARB_depth_buffer_float);
      playerSnooper.addStatToSnooper("gl_caps[ARB_depth_clamp]", contextcapabilities.GL_ARB_depth_clamp);
      playerSnooper.addStatToSnooper("gl_caps[ARB_depth_texture]", contextcapabilities.GL_ARB_depth_texture);
      playerSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers]", contextcapabilities.GL_ARB_draw_buffers);
      playerSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers_blend]", contextcapabilities.GL_ARB_draw_buffers_blend);
      playerSnooper.addStatToSnooper("gl_caps[ARB_draw_elements_base_vertex]", contextcapabilities.GL_ARB_draw_elements_base_vertex);
      playerSnooper.addStatToSnooper("gl_caps[ARB_draw_indirect]", contextcapabilities.GL_ARB_draw_indirect);
      playerSnooper.addStatToSnooper("gl_caps[ARB_draw_instanced]", contextcapabilities.GL_ARB_draw_instanced);
      playerSnooper.addStatToSnooper("gl_caps[ARB_explicit_attrib_location]", contextcapabilities.GL_ARB_explicit_attrib_location);
      playerSnooper.addStatToSnooper("gl_caps[ARB_explicit_uniform_location]", contextcapabilities.GL_ARB_explicit_uniform_location);
      playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_layer_viewport]", contextcapabilities.GL_ARB_fragment_layer_viewport);
      playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_program]", contextcapabilities.GL_ARB_fragment_program);
      playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_shader]", contextcapabilities.GL_ARB_fragment_shader);
      playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_program_shadow]", contextcapabilities.GL_ARB_fragment_program_shadow);
      playerSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_object]", contextcapabilities.GL_ARB_framebuffer_object);
      playerSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_sRGB]", contextcapabilities.GL_ARB_framebuffer_sRGB);
      playerSnooper.addStatToSnooper("gl_caps[ARB_geometry_shader4]", contextcapabilities.GL_ARB_geometry_shader4);
      playerSnooper.addStatToSnooper("gl_caps[ARB_gpu_shader5]", contextcapabilities.GL_ARB_gpu_shader5);
      playerSnooper.addStatToSnooper("gl_caps[ARB_half_float_pixel]", contextcapabilities.GL_ARB_half_float_pixel);
      playerSnooper.addStatToSnooper("gl_caps[ARB_half_float_vertex]", contextcapabilities.GL_ARB_half_float_vertex);
      playerSnooper.addStatToSnooper("gl_caps[ARB_instanced_arrays]", contextcapabilities.GL_ARB_instanced_arrays);
      playerSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_alignment]", contextcapabilities.GL_ARB_map_buffer_alignment);
      playerSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_range]", contextcapabilities.GL_ARB_map_buffer_range);
      playerSnooper.addStatToSnooper("gl_caps[ARB_multisample]", contextcapabilities.GL_ARB_multisample);
      playerSnooper.addStatToSnooper("gl_caps[ARB_multitexture]", contextcapabilities.GL_ARB_multitexture);
      playerSnooper.addStatToSnooper("gl_caps[ARB_occlusion_query2]", contextcapabilities.GL_ARB_occlusion_query2);
      playerSnooper.addStatToSnooper("gl_caps[ARB_pixel_buffer_object]", contextcapabilities.GL_ARB_pixel_buffer_object);
      playerSnooper.addStatToSnooper("gl_caps[ARB_seamless_cube_map]", contextcapabilities.GL_ARB_seamless_cube_map);
      playerSnooper.addStatToSnooper("gl_caps[ARB_shader_objects]", contextcapabilities.GL_ARB_shader_objects);
      playerSnooper.addStatToSnooper("gl_caps[ARB_shader_stencil_export]", contextcapabilities.GL_ARB_shader_stencil_export);
      playerSnooper.addStatToSnooper("gl_caps[ARB_shader_texture_lod]", contextcapabilities.GL_ARB_shader_texture_lod);
      playerSnooper.addStatToSnooper("gl_caps[ARB_shadow]", contextcapabilities.GL_ARB_shadow);
      playerSnooper.addStatToSnooper("gl_caps[ARB_shadow_ambient]", contextcapabilities.GL_ARB_shadow_ambient);
      playerSnooper.addStatToSnooper("gl_caps[ARB_stencil_texturing]", contextcapabilities.GL_ARB_stencil_texturing);
      playerSnooper.addStatToSnooper("gl_caps[ARB_sync]", contextcapabilities.GL_ARB_sync);
      playerSnooper.addStatToSnooper("gl_caps[ARB_tessellation_shader]", contextcapabilities.GL_ARB_tessellation_shader);
      playerSnooper.addStatToSnooper("gl_caps[ARB_texture_border_clamp]", contextcapabilities.GL_ARB_texture_border_clamp);
      playerSnooper.addStatToSnooper("gl_caps[ARB_texture_buffer_object]", contextcapabilities.GL_ARB_texture_buffer_object);
      playerSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map]", contextcapabilities.GL_ARB_texture_cube_map);
      playerSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map_array]", contextcapabilities.GL_ARB_texture_cube_map_array);
      playerSnooper.addStatToSnooper("gl_caps[ARB_texture_non_power_of_two]", contextcapabilities.GL_ARB_texture_non_power_of_two);
      playerSnooper.addStatToSnooper("gl_caps[ARB_uniform_buffer_object]", contextcapabilities.GL_ARB_uniform_buffer_object);
      playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_blend]", contextcapabilities.GL_ARB_vertex_blend);
      playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_buffer_object]", contextcapabilities.GL_ARB_vertex_buffer_object);
      playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_program]", contextcapabilities.GL_ARB_vertex_program);
      playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_shader]", contextcapabilities.GL_ARB_vertex_shader);
      playerSnooper.addStatToSnooper("gl_caps[EXT_bindable_uniform]", contextcapabilities.GL_EXT_bindable_uniform);
      playerSnooper.addStatToSnooper("gl_caps[EXT_blend_equation_separate]", contextcapabilities.GL_EXT_blend_equation_separate);
      playerSnooper.addStatToSnooper("gl_caps[EXT_blend_func_separate]", contextcapabilities.GL_EXT_blend_func_separate);
      playerSnooper.addStatToSnooper("gl_caps[EXT_blend_minmax]", contextcapabilities.GL_EXT_blend_minmax);
      playerSnooper.addStatToSnooper("gl_caps[EXT_blend_subtract]", contextcapabilities.GL_EXT_blend_subtract);
      playerSnooper.addStatToSnooper("gl_caps[EXT_draw_instanced]", contextcapabilities.GL_EXT_draw_instanced);
      playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_multisample]", contextcapabilities.GL_EXT_framebuffer_multisample);
      playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_object]", contextcapabilities.GL_EXT_framebuffer_object);
      playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_sRGB]", contextcapabilities.GL_EXT_framebuffer_sRGB);
      playerSnooper.addStatToSnooper("gl_caps[EXT_geometry_shader4]", contextcapabilities.GL_EXT_geometry_shader4);
      playerSnooper.addStatToSnooper("gl_caps[EXT_gpu_program_parameters]", contextcapabilities.GL_EXT_gpu_program_parameters);
      playerSnooper.addStatToSnooper("gl_caps[EXT_gpu_shader4]", contextcapabilities.GL_EXT_gpu_shader4);
      playerSnooper.addStatToSnooper("gl_caps[EXT_multi_draw_arrays]", contextcapabilities.GL_EXT_multi_draw_arrays);
      playerSnooper.addStatToSnooper("gl_caps[EXT_packed_depth_stencil]", contextcapabilities.GL_EXT_packed_depth_stencil);
      playerSnooper.addStatToSnooper("gl_caps[EXT_paletted_texture]", contextcapabilities.GL_EXT_paletted_texture);
      playerSnooper.addStatToSnooper("gl_caps[EXT_rescale_normal]", contextcapabilities.GL_EXT_rescale_normal);
      playerSnooper.addStatToSnooper("gl_caps[EXT_separate_shader_objects]", contextcapabilities.GL_EXT_separate_shader_objects);
      playerSnooper.addStatToSnooper("gl_caps[EXT_shader_image_load_store]", contextcapabilities.GL_EXT_shader_image_load_store);
      playerSnooper.addStatToSnooper("gl_caps[EXT_shadow_funcs]", contextcapabilities.GL_EXT_shadow_funcs);
      playerSnooper.addStatToSnooper("gl_caps[EXT_shared_texture_palette]", contextcapabilities.GL_EXT_shared_texture_palette);
      playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_clear_tag]", contextcapabilities.GL_EXT_stencil_clear_tag);
      playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_two_side]", contextcapabilities.GL_EXT_stencil_two_side);
      playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_wrap]", contextcapabilities.GL_EXT_stencil_wrap);
      playerSnooper.addStatToSnooper("gl_caps[EXT_texture_3d]", contextcapabilities.GL_EXT_texture_3d);
      playerSnooper.addStatToSnooper("gl_caps[EXT_texture_array]", contextcapabilities.GL_EXT_texture_array);
      playerSnooper.addStatToSnooper("gl_caps[EXT_texture_buffer_object]", contextcapabilities.GL_EXT_texture_buffer_object);
      playerSnooper.addStatToSnooper("gl_caps[EXT_texture_integer]", contextcapabilities.GL_EXT_texture_integer);
      playerSnooper.addStatToSnooper("gl_caps[EXT_texture_lod_bias]", contextcapabilities.GL_EXT_texture_lod_bias);
      playerSnooper.addStatToSnooper("gl_caps[EXT_texture_sRGB]", contextcapabilities.GL_EXT_texture_sRGB);
      playerSnooper.addStatToSnooper("gl_caps[EXT_vertex_shader]", contextcapabilities.GL_EXT_vertex_shader);
      playerSnooper.addStatToSnooper("gl_caps[EXT_vertex_weighting]", contextcapabilities.GL_EXT_vertex_weighting);
      playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_uniforms]", GL11.glGetInteger(35658));
      GL11.glGetError();
      playerSnooper.addStatToSnooper("gl_caps[gl_max_fragment_uniforms]", GL11.glGetInteger(35657));
      GL11.glGetError();
      playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_attribs]", GL11.glGetInteger(34921));
      GL11.glGetError();
      playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_texture_image_units]", GL11.glGetInteger(35660));
      GL11.glGetError();
      playerSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", GL11.glGetInteger(34930));
      GL11.glGetError();
      playerSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", GL11.glGetInteger(35071));
      GL11.glGetError();
      playerSnooper.addStatToSnooper("gl_max_texture_size", getGLMaximumTextureSize());
   }

   public static int getGLMaximumTextureSize() {
      for(int i = 16384; i > 0; i >>= 1) {
         GL11.glTexImage2D(32868, 0, 6408, i, i, 0, 6408, 5121, (ByteBuffer)null);
         int j = GL11.glGetTexLevelParameteri(32868, 0, 4096);
         if (j != 0) {
            return i;
         }
      }

      return -1;
   }

   public boolean isSnooperEnabled() {
      return this.gameSettings.snooperEnabled;
   }

   public void setServerData(ServerData serverDataIn) {
      this.currentServerData = serverDataIn;
   }

   public ServerData getCurrentServerData() {
      return this.currentServerData;
   }

   public boolean isIntegratedServerRunning() {
      return this.integratedServerIsRunning;
   }

   public boolean isSingleplayer() {
      return this.integratedServerIsRunning && this.theIntegratedServer != null;
   }

   public IntegratedServer getIntegratedServer() {
      return this.theIntegratedServer;
   }

   public static void stopIntegratedServer() {
      if (theMinecraft != null) {
         IntegratedServer integratedserver = theMinecraft.getIntegratedServer();
         if (integratedserver != null) {
            integratedserver.stopServer();
         }
      }

   }

   public PlayerUsageSnooper getPlayerUsageSnooper() {
      return this.usageSnooper;
   }

   public static long getSystemTime() {
      return Sys.getTime() * 1000L / Sys.getTimerResolution();
   }

   public boolean isFullScreen() {
      return this.fullscreen;
   }

   public Session getSession() {
      return this.session;
   }

   public PropertyMap func_181037_M() {
      if (this.field_181038_N.isEmpty()) {
         GameProfile gameprofile = this.getSessionService().fillProfileProperties(this.session.getProfile(), false);
         this.field_181038_N.putAll(gameprofile.getProperties());
      }

      return this.field_181038_N;
   }

   public Proxy getProxy() {
      return this.proxy;
   }

   public TextureManager getTextureManager() {
      return this.renderEngine;
   }

   public IResourceManager getResourceManager() {
      return this.mcResourceManager;
   }

   public ResourcePackRepository getResourcePackRepository() {
      return this.mcResourcePackRepository;
   }

   public LanguageManager getLanguageManager() {
      return this.mcLanguageManager;
   }

   public TextureMap getTextureMapBlocks() {
      return this.textureMapBlocks;
   }

   public boolean isJava64bit() {
      return this.jvm64bit;
   }

   public boolean isGamePaused() {
      return this.isGamePaused;
   }

   public SoundHandler getSoundHandler() {
      return this.mcSoundHandler;
   }

   public MusicTicker.MusicType getAmbientMusicType() {
      return this.thePlayer != null ? (this.thePlayer.worldObj.provider instanceof WorldProviderHell ? MusicTicker.MusicType.NETHER : (this.thePlayer.worldObj.provider instanceof WorldProviderEnd ? (BossStatus.bossName != null && BossStatus.statusBarTime > 0 ? MusicTicker.MusicType.END_BOSS : MusicTicker.MusicType.END) : (this.thePlayer.capabilities.isCreativeMode && this.thePlayer.capabilities.allowFlying ? MusicTicker.MusicType.CREATIVE : MusicTicker.MusicType.GAME))) : MusicTicker.MusicType.MENU;
   }

   public void dispatchKeypresses() {
      int i = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() : Keyboard.getEventKey();
      if (i != 0 && !Keyboard.isRepeatEvent() && (!(this.currentScreen instanceof GuiControls) || ((GuiControls)this.currentScreen).time <= getSystemTime() - 20L) && Keyboard.getEventKeyState()) {
         if (i == this.gameSettings.keyBindFullscreen.getKeyCode()) {
            this.toggleFullscreen();
         } else if (i == this.gameSettings.keyBindScreenshot.getKeyCode()) {
            this.ingameGUI.getChatGUI().printChatMessage(ScreenShotHelper.saveScreenshot(this.mcDataDir, this.displayWidth, this.displayHeight, this.framebufferMc));
         }
      }

   }

   public MinecraftSessionService getSessionService() {
      return this.sessionService;
   }

   public SkinManager getSkinManager() {
      return this.skinManager;
   }

   public Entity getRenderViewEntity() {
      return this.renderViewEntity;
   }

   public void setRenderViewEntity(Entity viewingEntity) {
      this.renderViewEntity = viewingEntity;
      this.entityRenderer.loadEntityShader(viewingEntity);
   }

   public <V> ListenableFuture<V> addScheduledTask(Callable<V> callableToSchedule) {
      Validate.notNull(callableToSchedule);
      if (!this.isCallingFromMinecraftThread()) {
         ListenableFutureTask<V> listenablefuturetask = ListenableFutureTask.create(callableToSchedule);
         synchronized(this.scheduledTasks) {
            this.scheduledTasks.add(listenablefuturetask);
            return listenablefuturetask;
         }
      } else {
         try {
            return Futures.immediateFuture(callableToSchedule.call());
         } catch (Exception var6) {
            return Futures.immediateFailedCheckedFuture(var6);
         }
      }
   }

   public ListenableFuture<Object> addScheduledTask(Runnable runnableToSchedule) {
      Validate.notNull(runnableToSchedule);
      return this.addScheduledTask(Executors.callable(runnableToSchedule));
   }

   public boolean isCallingFromMinecraftThread() {
      return Thread.currentThread() == this.mcThread;
   }

   public BlockRendererDispatcher getBlockRendererDispatcher() {
      return this.blockRenderDispatcher;
   }

   public RenderManager getRenderManager() {
      return this.renderManager;
   }

   public RenderItem getRenderItem() {
      return this.renderItem;
   }

   public ItemRenderer getItemRenderer() {
      return this.itemRenderer;
   }

   public static int getDebugFPS() {
      return debugFPS;
   }

   public FrameTimer func_181539_aj() {
      return this.field_181542_y;
   }

   public static Map<String, String> getSessionInfo() {
      Map<String, String> map = Maps.newHashMap();
      map.put("X-Minecraft-Username", getMinecraft().getSession().getUsername());
      map.put("X-Minecraft-UUID", getMinecraft().getSession().getPlayerID());
      map.put("X-Minecraft-Version", "1.8.9");
      return map;
   }

   public boolean func_181540_al() {
      return this.field_181541_X;
   }

   public void func_181537_a(boolean p_181537_1_) {
      this.field_181541_X = p_181537_1_;
   }

   static {
      isRunningOnMac = Util.getOSType() == Util.EnumOS.OSX;
      memoryReserve = new byte[10485760];
      macDisplayModes = Lists.newArrayList(new DisplayMode[]{new DisplayMode(2560, 1600), new DisplayMode(2880, 1800)});
   }
}
