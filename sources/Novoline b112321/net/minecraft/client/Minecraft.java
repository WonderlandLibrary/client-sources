package net.minecraft.client;

import cc.novoline.Novoline;
import cc.novoline.events.EventManager;
import cc.novoline.events.events.KeyPressEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.gui.screen.login.GuiLogin;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.binds.KeyboardKeybind;
import cc.novoline.modules.binds.ModuleKeybind;
import cc.novoline.modules.binds.MouseKeybind;
import cc.novoline.modules.configurations.holder.ModuleHolder;
import cc.novoline.modules.misc.FastPlace;
import cc.novoline.modules.misc.Killsults;
import cc.novoline.modules.visual.KeyStrokes;
import cc.novoline.modules.visual.XRay;
import cc.novoline.utils.notifications.NotificationType;
import cc.novoline.utils.tasks.FutureTask;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ListenableFuture;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import net.BA;
import net.a0R;
import net.aHM;
import net.aHv;
import net.aNh;
import net.aTX;
import net.apX;
import net.cj;
import net.nk;
import net.qT;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft$1;
import net.minecraft.client.Minecraft$2;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.MusicTicker$MusicType;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.gui.stream.GuiStreamUnavailable;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.multiplayer.GuiConnecting;
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
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.FoliageColorReloadListener;
import net.minecraft.client.resources.GrassColorReloadListener;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.ResourceIndex;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.ResourcePackRepository$Entry;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
import net.minecraft.client.resources.data.FontMetadataSection;
import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
import net.minecraft.client.resources.data.IMetadataSectionSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.client.resources.data.PackMetadataSectionSerializer;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings$Options;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.stream.IStream;
import net.minecraft.client.stream.NullStream;
import net.minecraft.client.stream.TwitchStream;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayer$EnumChatVisibility;
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
import net.minecraft.network.play.client.C16PacketClientStatus$EnumState;
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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition$MovingObjectType;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import net.minecraft.util.Util$EnumOS;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
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
   public ScheduledExecutorService niggerService;
   private Logger logger = LogManager.getLogger();
   private final String[] errors = new String[]{"Internal Exception: io.netty.handler.codec.DecoderException: Badly compressed packet - size of 61 is below server threshold of 256", "Internal Exception: io.netty.handler.codec.DecoderException: java.lang.IndexOutOfBoundsException: readerIndex(906) + length(1) exceeds writerIndex(906): UnpooledHeapByteBuf(ridx: 986, widx: 986, cap: 906/906)", "Internal Exception: io.netty.handler.codec.DecoderException: java.io.IOException: Bad packet id 68", "Internal Exception: java.io.IOException: An existing connection was forcibly closed by the remote host", "java.net.ConnectException: Connection refused: no further information:", "Failed to verify username!"};
   public static final boolean isRunningOnMac = aNh.a() == Util$EnumOS.OSX;
   private static final ResourceLocation locationMojangPng = new ResourceLocation("textures/gui/title/mojang.png");
   private static final List G = Lists.newArrayList(new DisplayMode[]{new DisplayMode(2560, 1600), new DisplayMode(2880, 1800)});
   public static byte[] memoryReserve = new byte[10485760];
   private static Minecraft theMinecraft;
   private Novoline novoline;
   private static int debugFPS;
   public final File mcDataDir;
   public final FrameTimer field_181542_y = new FrameTimer();
   public final Profiler mcProfiler = new Profiler();
   private final File fileResourcepacks;
   private final PropertyMap twitchDetails;
   private final PropertyMap field_181038_N;
   private final boolean enableGLErrorChecking = true;
   private final PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("client", this, MinecraftServer.getCurrentTimeMillis());
   private final int tempDisplayWidth;
   private final int tempDisplayHeight;
   private final File fileAssets;
   private final String launchedVersion;
   private final Proxy proxy;
   private final boolean jvm64bit;
   private final boolean isDemo;
   private final apX ac = new apX();
   private final List defaultResourcePacks = Lists.newArrayList();
   private final DefaultResourcePack mcDefaultResourcePack;
   private final MinecraftSessionService sessionService;
   private final Queue scheduledTasks = Queues.newArrayDeque();
   private final long field_175615_aJ = 0L;
   private final Thread mcThread = Thread.currentThread();
   public Session session;
   public aTX at;
   public int displayWidth;
   public int displayHeight;
   public Timer timer = new Timer(20.0F);
   public WorldClient world;
   public RenderGlobal renderGlobal;
   public EntityPlayerSP player;
   public Minecraft gameController;
   public BlockHelper blockHelper;
   public Entity pointedEntity;
   public EffectRenderer effectRenderer;
   public FontRenderer fontRendererObj;
   public FontRenderer fontRendererCrack;
   public FontRenderer standardGalacticFontRenderer;
   public GuiScreen currentScreen;
   public LoadingScreenRenderer loadingScreen;
   public EntityRenderer entityRenderer;
   public int leftClickCounter;
   public GuiAchievement guiAchievement;
   public GuiIngame ingameGUI;
   public boolean skipRenderWorld;
   public MovingObjectPosition objectMouseOver;
   public GameSettings gameSettings;
   public MouseHelper mouseHelper;
   public boolean inGameHasFocus;
   public String debug = "";
   public boolean field_175613_B = false;
   public boolean field_175614_C = false;
   public boolean field_175611_D = false;
   public boolean renderChunksMany = true;
   long systemTime = getSystemTime();
   long field_181543_z = System.nanoTime();
   volatile boolean running = true;
   long debugUpdateTime = getSystemTime();
   int fpsCounter;
   long prevFrameTime = -1L;
   private ServerData currentServerData;
   private TextureManager renderEngine;
   private boolean fullscreen;
   private boolean hasCrashed;
   private CrashReport crashReporter;
   private boolean field_181541_X = false;
   private RenderManager renderManager;
   private RenderItem renderItem;
   private ItemRenderer itemRenderer;
   private Entity renderViewEntity;
   private boolean isGamePaused;
   private IntegratedServer theIntegratedServer;
   private ISaveFormat saveLoader;
   private int rightClickDelayTimer;
   private String serverName;
   private int serverPort;
   private int joinPlayerCounter;
   private NetworkManager myNetworkManager;
   private boolean integratedServerIsRunning;
   private long debugCrashKeyPressTime = -1L;
   private IReloadableResourceManager mcResourceManager;
   private ResourcePackRepository mcResourcePackRepository;
   private BA h;
   private IStream stream;
   private Framebuffer framebufferMc;
   private TextureMap textureMapBlocks;
   private SoundHandler mcSoundHandler;
   private MusicTicker mcMusicTicker;
   private ResourceLocation mojangLogo;
   private nk B;
   private ModelManager modelManager;
   private BlockRendererDispatcher blockRenderDispatcher;
   private String debugProfilerName = "root";
   public GuiScreen previousScreen;

   public Minecraft(GameConfiguration var1) {
      theMinecraft = this;
      this.mcDataDir = var1.folderInfo.mcDataDir;
      this.fileAssets = var1.folderInfo.assetsDir;
      this.fileResourcepacks = var1.folderInfo.resourcePacksDir;
      this.launchedVersion = var1.gameInfo.version;
      this.twitchDetails = var1.userInfo.userProperties;
      this.field_181038_N = var1.userInfo.field_181172_c;
      this.mcDefaultResourcePack = new DefaultResourcePack((new ResourceIndex(var1.folderInfo.assetsDir, var1.folderInfo.assetIndex)).getResourceMap());
      this.proxy = var1.userInfo.proxy == null?Proxy.NO_PROXY:var1.userInfo.proxy;
      this.sessionService = (new YggdrasilAuthenticationService(var1.userInfo.proxy, UUID.randomUUID().toString())).createMinecraftSessionService();
      this.session = var1.userInfo.session;
      this.getLogger().info("Setting user: " + this.session.getUsername());
      this.getLogger().info("(Session ID is " + this.session.getSessionID() + ")");
      this.isDemo = var1.gameInfo.isDemo;
      this.displayWidth = var1.displayInfo.width > 0?var1.displayInfo.width:1;
      this.displayHeight = var1.displayInfo.height > 0?var1.displayInfo.height:1;
      this.tempDisplayWidth = var1.displayInfo.width;
      this.tempDisplayHeight = var1.displayInfo.height;
      this.fullscreen = var1.displayInfo.fullscreen;
      this.jvm64bit = isJvm64bit();
      this.theIntegratedServer = new IntegratedServer(this);
      if(var1.serverInfo.serverName != null) {
         this.serverName = var1.serverInfo.serverName;
         this.serverPort = var1.serverInfo.serverPort;
      }

      ImageIO.setUseCache(false);
      Bootstrap.register();
   }

   public Novoline getNovoline() {
      return this.novoline;
   }

   public static int getGLMaximumTextureSize() {
      short var0 = 16384;
      GL11.glTexImage2D('聤', 0, 6408, var0, var0, 0, 6408, 5121, (ByteBuffer)null);
      int var1 = GL11.glGetTexLevelParameteri('聤', 0, 4096);
      return var0;
   }

   public static Map getSessionInfo() {
      HashMap var0 = Maps.newHashMap();
      var0.put("X-Minecraft-Username", getInstance().getSession().getUsername());
      var0.put("X-Minecraft-UUID", getInstance().getSession().getPlayerID());
      var0.put("X-Minecraft-Version", "1.8.8");
      return var0;
   }

   private static boolean isJvm64bit() {
      String[] var0 = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};

      for(String var4 : var0) {
         String var5 = System.getProperty(var4);
         if(var5.contains("64")) {
            return true;
         }
      }

      return false;
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

   public static Minecraft getInstance() {
      return theMinecraft;
   }

   public static void stopIntegratedServer() {
      if(theMinecraft != null) {
         IntegratedServer var0 = theMinecraft.getIntegratedServer();
         var0.stopServer();
      }

   }

   public static long getSystemTime() {
      return Sys.getTime() * 1000L / Sys.getTimerResolution();
   }

   private void registerMetadataSerializers() {
      this.ac.a((IMetadataSectionSerializer)(new TextureMetadataSectionSerializer()), (Class)TextureMetadataSection.class);
      this.ac.a((IMetadataSectionSerializer)(new FontMetadataSectionSerializer()), (Class)FontMetadataSection.class);
      this.ac.a((IMetadataSectionSerializer)(new AnimationMetadataSectionSerializer()), (Class)AnimationMetadataSection.class);
      this.ac.a((IMetadataSectionSerializer)(new PackMetadataSectionSerializer()), (Class)PackMetadataSection.class);
      this.ac.a((IMetadataSectionSerializer)(new LanguageMetadataSectionSerializer()), (Class)a0R.class);
   }

   public void run() {
      // $FF: Couldn't be decompiled
   }

   private void startGame() throws LWJGLException {
      this.gameSettings = new GameSettings(this, this.mcDataDir);
      this.defaultResourcePacks.add(this.mcDefaultResourcePack);
      this.startTimerHackThread();
      if(this.gameSettings.overrideHeight > 0 && this.gameSettings.overrideWidth > 0) {
         this.displayWidth = this.gameSettings.overrideWidth;
         this.displayHeight = this.gameSettings.overrideHeight;
      }

      this.getLogger().info("LWJGL Version: " + Sys.getVersion());
      this.setWindowIcon();
      this.setInitialDisplayMode();
      this.createDisplay();
      OpenGlHelper.initializeTextures();
      this.framebufferMc = new Framebuffer(this.displayWidth, this.displayHeight, true);
      this.framebufferMc.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
      this.registerMetadataSerializers();
      this.mcResourcePackRepository = new ResourcePackRepository(this.fileResourcepacks, new File(this.mcDataDir, "server-resource-packs"), this.mcDefaultResourcePack, this.ac, this.gameSettings);
      this.mcResourceManager = new SimpleReloadableResourceManager(this.ac);
      this.h = new BA(this.ac, this.gameSettings.language);
      this.mcResourceManager.registerReloadListener(this.h);
      this.refreshResources();
      this.renderEngine = new TextureManager(this.mcResourceManager);
      this.mcResourceManager.registerReloadListener(this.renderEngine);
      this.drawSplashScreen(this.renderEngine);
      this.initStream();
      this.B = new nk(this.renderEngine, new File(this.fileAssets, "skins"), this.sessionService);
      this.saveLoader = new AnvilSaveConverter(new File(this.mcDataDir, "saves"));
      this.mcSoundHandler = new SoundHandler(this.mcResourceManager, this.gameSettings);
      this.mcResourceManager.registerReloadListener(this.mcSoundHandler);
      this.mcMusicTicker = new MusicTicker(this);
      this.fontRendererObj = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii.png"), this.renderEngine, false);
      this.fontRendererCrack = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/crack.png"), this.renderEngine, false);
      if(this.gameSettings.language != null) {
         this.fontRendererObj.setUnicodeFlag(this.isUnicode());
         this.fontRendererObj.setBidiFlag(this.h.d());
         this.fontRendererCrack.setUnicodeFlag(this.isUnicode());
         this.fontRendererCrack.setBidiFlag(this.h.d());
      }

      this.standardGalacticFontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii_sga.png"), this.renderEngine, false);
      this.mcResourceManager.registerReloadListener(this.fontRendererObj);
      this.mcResourceManager.registerReloadListener(this.fontRendererCrack);
      this.mcResourceManager.registerReloadListener(this.standardGalacticFontRenderer);
      this.mcResourceManager.registerReloadListener(new GrassColorReloadListener());
      this.mcResourceManager.registerReloadListener(new FoliageColorReloadListener());
      AchievementList.openInventory.setStatStringFormatter(this::lambda$startGame$0);
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
      this.effectRenderer = new EffectRenderer(this.world, this.renderEngine);
      this.checkGLError("Post startup");
      this.ingameGUI = new GuiIngame(this);
      (this.novoline = Novoline.getInstance()).onStart();
      if(this.serverName != null) {
         this.displayGuiScreen(new GuiConnecting(new aHv(), this, this.serverName, this.serverPort));
      } else {
         this.displayGuiScreen(new GuiLogin());
      }

      this.renderEngine.deleteTexture(this.mojangLogo);
      this.mojangLogo = null;
      this.loadingScreen = new LoadingScreenRenderer(this);
      if(this.gameSettings.fullScreen && !this.fullscreen) {
         this.toggleFullscreen();
      }

      try {
         Display.setVSyncEnabled(this.gameSettings.enableVsync);
      } catch (OpenGLException var2) {
         this.gameSettings.enableVsync = false;
         this.gameSettings.saveOptions();
      }

      this.renderGlobal.makeEntityOutlineShader();
      this.novoline.onLoaded();
   }

   private void setInitialDisplayMode() throws LWJGLException {
      if(this.fullscreen) {
         Display.setFullscreen(true);
         DisplayMode var1 = Display.getDisplayMode();
         this.displayWidth = Math.max(1, var1.getWidth());
         this.displayHeight = Math.max(1, var1.getHeight());
      } else {
         Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
      }

   }

   private void initStream() {
      Minecraft var10000 = this;

      try {
         var10000.stream = new TwitchStream(this, (Property)Iterables.getFirst(this.twitchDetails.get("twitch_access_token"), (Object)null));
      } catch (Throwable var2) {
         this.stream = new NullStream(var2);
         this.getLogger().error("Couldn\'t initialize twitch stream");
      }

   }

   public Framebuffer getFramebuffer() {
      return this.framebufferMc;
   }

   public String getVersion() {
      return this.launchedVersion;
   }

   private void startTimerHackThread() {
      Minecraft$1 var1 = new Minecraft$1(this, "Timer hack thread");
      var1.setDaemon(true);
      var1.start();
   }

   public void crashed(CrashReport var1) {
      this.hasCrashed = true;
      this.crashReporter = var1;
   }

   public void displayCrashReport(CrashReport var1) {
      File var2 = new File(getInstance().mcDataDir, "crash-reports");
      File var3 = new File(var2, "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-client.txt");
      Bootstrap.printToSYSOUT(var1.getCompleteReport());
      if(XRay.isEnabled) {
         ((XRay)this.novoline.getModuleManager().getModule(XRay.class)).toggle();
      }

      this.novoline.onDisable();
      if(var1.getFile() != null) {
         Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + var1.getFile());
         System.exit(-1);
      } else if(var1.saveToFile(var3)) {
         Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + var3.getAbsolutePath());
         System.exit(-1);
      } else {
         Bootstrap.printToSYSOUT("#@?@# Game crashed! Crash report could not be saved. #@?@#");
         System.exit(-2);
      }

   }

   public boolean isUnicode() {
      return this.h.b() || this.gameSettings.forceUnicodeFont;
   }

   private void createDisplay() throws LWJGLException {
      Display.setResizable(true);
      Display.setTitle("Minecraft 1.8.8");

      try {
         Display.create((new PixelFormat()).withDepthBits(24));
      } catch (LWJGLException var4) {
         this.getLogger().error("Couldn\'t set pixel format", var4);
         long var10000 = 1000L;

         try {
            Thread.sleep(var10000);
         } catch (InterruptedException var3) {
            ;
         }

         if(this.fullscreen) {
            this.C();
         }

         Display.create();
      }

   }

   private void setWindowIcon() {
      // $FF: Couldn't be decompiled
   }

   public void refreshResources() {
      ArrayList var1 = Lists.newArrayList(this.defaultResourcePacks);

      for(ResourcePackRepository$Entry var3 : this.mcResourcePackRepository.getRepositoryEntries()) {
         var1.add(var3.getResourcePack());
      }

      if(this.mcResourcePackRepository.getResourcePackInstance() != null) {
         var1.add(this.mcResourcePackRepository.getResourcePackInstance());
      }

      try {
         this.mcResourceManager.reloadResources(var1);
      } catch (RuntimeException var4) {
         this.getLogger().info("Caught error stitching, removing all assigned resourcepacks", var4);
         var1.clear();
         var1.addAll(this.defaultResourcePacks);
         this.mcResourcePackRepository.setRepositories(Collections.emptyList());
         this.mcResourceManager.reloadResources(var1);
         this.gameSettings.resourcePacks.clear();
         this.gameSettings.field_183018_l.clear();
         this.gameSettings.saveOptions();
      }

      this.h.a((List)var1);
      if(this.renderGlobal != null) {
         this.renderGlobal.loadRenderers();
      }

   }

   private ByteBuffer readImageToBuffer(InputStream var1) throws IOException {
      BufferedImage var2 = ImageIO.read(var1);
      int[] var3 = qT.b(var2, 0, 0, var2.getWidth(), var2.getHeight(), (int[])null, 0, var2.getWidth());
      ByteBuffer var4 = ByteBuffer.allocate(4 * var3.length);

      for(int var8 : var3) {
         var4.putInt(var8 << 8 | var8 >> 24 & 255);
      }

      var4.flip();
      return var4;
   }

   private void C() throws LWJGLException {
      DisplayMode var1 = Display.getDesktopDisplayMode();
      Display.setDisplayMode(var1);
      this.displayWidth = var1.getWidth();
      this.displayHeight = var1.getHeight();
   }

   public ISaveFormat getSaveLoader() {
      return this.saveLoader;
   }

   private void drawSplashScreen(TextureManager param1) {
      // $FF: Couldn't be decompiled
   }

   private void checkGLError(String var1) {
      int var2 = GL11.glGetError();
      String var3 = GLU.gluErrorString(var2);
      this.getLogger().error("########## GL ERROR ##########");
      this.getLogger().error("@ " + var1);
      this.getLogger().error(var2 + ": " + var3);
   }

   public void func_181536_a(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10) {
      float var11 = 0.00390625F;
      float var12 = 0.00390625F;
      WorldRenderer var13 = Tessellator.getInstance().getWorldRenderer();
      var13.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      var13.pos((double)var1, (double)(var2 + var6), 0.0D).tex((double)((float)var3 * 0.00390625F), (double)((float)(var4 + var6) * 0.00390625F)).color(var7, var8, var9, var10).endVertex();
      var13.pos((double)(var1 + var5), (double)(var2 + var6), 0.0D).tex((double)((float)(var3 + var5) * 0.00390625F), (double)((float)(var4 + var6) * 0.00390625F)).color(var7, var8, var9, var10).endVertex();
      var13.pos((double)(var1 + var5), (double)var2, 0.0D).tex((double)((float)(var3 + var5) * 0.00390625F), (double)((float)var4 * 0.00390625F)).color(var7, var8, var9, var10).endVertex();
      var13.pos((double)var1, (double)var2, 0.0D).tex((double)((float)var3 * 0.00390625F), (double)((float)var4 * 0.00390625F)).color(var7, var8, var9, var10).endVertex();
      Tessellator.getInstance().draw();
   }

   public GuiScreen getPreviousScreen() {
      return this.previousScreen;
   }

   public void displayGuiScreen(GuiScreen var1) {
      if(this.currentScreen != null) {
         this.currentScreen.onGuiClosed();
      }

      if(this.world == null) {
         var1 = new aHv();
      } else if(this.player.getHealth() <= 0.0F) {
         var1 = new GuiGameOver();
      }

      this.previousScreen = this.currentScreen;
      this.currentScreen = (GuiScreen)var1;
      this.setIngameNotInFocus();
      ScaledResolution var2 = new ScaledResolution(this);
      int var3 = var2.getScaledWidth();
      int var4 = var2.getScaledHeight();
      ((GuiScreen)var1).setWorldAndResolution(this, var3, var4);
      this.skipRenderWorld = false;
   }

   public void updateDisplay() {
      this.mcProfiler.startSection("display_update");
      Display.update();
      this.mcProfiler.endSection();
      this.checkWindowResize();
   }

   protected void checkWindowResize() {
      if(!this.fullscreen && Display.wasResized()) {
         int var1 = this.displayWidth;
         int var2 = this.displayHeight;
         this.displayWidth = Display.getWidth();
         this.displayHeight = Display.getHeight();
         if(this.displayWidth != var1 || this.displayHeight != var2) {
            if(this.displayWidth <= 0) {
               this.displayWidth = 1;
            }

            if(this.displayHeight <= 0) {
               this.displayHeight = 1;
            }

            this.resize(this.displayWidth, this.displayHeight);
         }
      }

   }

   public int getLimitFramerate() {
      return this.world == null && this.currentScreen != null?240:this.gameSettings.limitFramerate;
   }

   public boolean isFramerateLimitBelowMax() {
      return (float)this.getLimitFramerate() < GameSettings$Options.FRAMERATE_LIMIT.getValueMax();
   }

   public void shutdownMinecraftApplet() {
      try {
         if(XRay.isEnabled) {
            ((XRay)this.novoline.getModuleManager().getModule(XRay.class)).toggle();
         }

         this.novoline.onDisable();
         this.stream.shutdownStream();
         this.getLogger().info("Stopping!");
         Minecraft var10000 = this;
         Object var10001 = null;

         try {
            var10000.loadWorld((WorldClient)var10001);
         } catch (Throwable var5) {
            ;
         }

         this.mcSoundHandler.unloadSounds();
      } finally {
         Display.destroy();
         if(!this.hasCrashed) {
            System.exit(0);
         }

      }

   }

   private void runGameLoop() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void freeMemory() {
      byte var10000 = 0;

      try {
         memoryReserve = new byte[var10000];
         this.renderGlobal.deleteAllDisplayLists();
      } catch (Throwable var3) {
         ;
      }

      Minecraft var4 = this;
      Object var10001 = null;

      try {
         var4.loadWorld((WorldClient)var10001);
      } catch (Throwable var2) {
         ;
      }

   }

   public void shutdown() {
      this.writeKillsults();
      this.gameSettings.saveOptions();
      this.running = false;
   }

   private void updateDebugProfilerName(int var1) {
      List var2 = this.mcProfiler.getProfilingData(this.debugProfilerName);
      if(!var2.isEmpty()) {
         cj var3 = (cj)var2.remove(0);
         if(!var3.b.isEmpty()) {
            int var4 = this.debugProfilerName.lastIndexOf(".");
            this.debugProfilerName = this.debugProfilerName.substring(0, var4);
         }
      }

   }

   public void setIngameNotInFocus() {
      if(this.inGameHasFocus) {
         KeyBinding.unPressAllKeys();
         this.inGameHasFocus = false;
         this.mouseHelper.ungrabMouseCursor();
      }

   }

   public void displayInGameMenu() {
      if(this.currentScreen == null) {
         this.displayGuiScreen(new GuiIngameMenu());
         if(this.isSingleplayer() && !this.theIntegratedServer.getPublic()) {
            this.mcSoundHandler.pauseSounds();
         }
      }

   }

   public void sendClickBlockToController(boolean var1) {
      this.leftClickCounter = 0;
      if(this.player != null && this.leftClickCounter <= 0 && !this.player.isUsingItem()) {
         if(this.objectMouseOver != null && this.objectMouseOver.typeOfHit == MovingObjectPosition$MovingObjectType.BLOCK) {
            BlockPos var2 = this.objectMouseOver.getBlockPos();
            if(this.world.getBlockState(var2).getBlock().getMaterial() != Material.air && this.at.a(var2, this.objectMouseOver.facing)) {
               this.effectRenderer.addBlockHitEffects(var2, this.objectMouseOver.facing);
               this.player.swingItem();
            }
         } else {
            this.at.d();
         }
      }

   }

   public void clickMouse() {
      if(this.leftClickCounter <= 0) {
         ((KeyStrokes)Novoline.getInstance().getModuleManager().getModule(KeyStrokes.class)).k();
         this.player.swingItem();
         if(this.objectMouseOver == null) {
            this.getLogger().error("Null returned as \'hitResult\', this shouldn\'t happen!");
            if(this.at.j()) {
               this.leftClickCounter = 10;
            }
         } else {
            switch(Minecraft$2.$SwitchMap$net$minecraft$util$MovingObjectPosition$MovingObjectType[this.objectMouseOver.typeOfHit.ordinal()]) {
            case 1:
               this.at.b((EntityPlayer)this.player, (Entity)this.objectMouseOver.entityHit);
               break;
            case 2:
               BlockPos var1 = this.objectMouseOver.getBlockPos();
               if(this.world.getBlockState(var1).getBlock().getMaterial() != Material.air) {
                  this.at.b(var1, this.objectMouseOver.facing);
                  break;
               }
            case 3:
            default:
               if(this.at.j()) {
                  this.leftClickCounter = 10;
               }
            }
         }
      }

   }

   public void rightClickMouse() {
      ((KeyStrokes)Novoline.getInstance().getModuleManager().getModule(KeyStrokes.class)).e();
      if(!this.at.m()) {
         FastPlace var1 = (FastPlace)this.novoline.getModuleManager().getModule(FastPlace.class);
         if(var1.isEnabled()) {
            if(this.world != null) {
               if(((Boolean)var1.blocksOnly.get()).booleanValue()) {
                  try {
                     if(this.player.getHeldItem().getItem() != null) {
                        if(this.player.getHeldItem().getItem() instanceof ItemBlock) {
                           this.rightClickDelayTimer = ((Integer)var1.placeDelay.get()).intValue();
                        } else {
                           this.rightClickDelayTimer = 4;
                        }
                     } else {
                        this.rightClickDelayTimer = 4;
                     }
                  } catch (NullPointerException var6) {
                     this.rightClickDelayTimer = 4;
                  }
               } else {
                  this.rightClickDelayTimer = ((Integer)var1.placeDelay.get()).intValue();
               }
            } else {
               this.rightClickDelayTimer = 4;
            }
         } else {
            this.rightClickDelayTimer = 4;
         }

         boolean var2 = true;
         ItemStack var3 = this.player.inventory.getCurrentItem();
         if(this.objectMouseOver == null) {
            this.getLogger().warn("Null returned as \'hitResult\', this shouldn\'t happen!");
         } else {
            switch(Minecraft$2.$SwitchMap$net$minecraft$util$MovingObjectPosition$MovingObjectType[this.objectMouseOver.typeOfHit.ordinal()]) {
            case 1:
               if(this.at.a(this.player, this.objectMouseOver.entityHit, this.objectMouseOver)) {
                  var2 = false;
               } else if(this.at.a((EntityPlayer)this.player, (Entity)this.objectMouseOver.entityHit)) {
                  var2 = false;
               }
               break;
            case 2:
               BlockPos var4 = this.objectMouseOver.getBlockPos();
               if(this.world.getBlockState(var4).getBlock().getMaterial() != Material.air) {
                  int var5 = var3.stackSize;
                  if(this.at.onPlayerRightClick(this.player, this.world, var3, var4, this.objectMouseOver.facing, this.objectMouseOver.hitVec)) {
                     var2 = false;
                     this.player.swingItem();
                  }

                  return;
               }
            }
         }

         ItemStack var10 = this.player.inventory.getCurrentItem();
         if(this.at.sendUseItem(this.player, this.world, var10)) {
            this.entityRenderer.itemRenderer.resetEquippedProgress2();
         }
      }

   }

   private void displayDebugInfo(long var1) {
      if(this.mcProfiler.profilingEnabled) {
         List var3 = this.mcProfiler.getProfilingData(this.debugProfilerName);
         cj var4 = (cj)var3.remove(0);
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
         Tessellator var5 = Tessellator.getInstance();
         WorldRenderer var6 = var5.getWorldRenderer();
         boolean var7 = true;
         int var8 = this.displayWidth - 160 - 10;
         int var9 = this.displayHeight - 320;
         GlStateManager.enableBlend();
         var6.begin(7, DefaultVertexFormats.POSITION_COLOR);
         var6.pos((double)((float)var8 - 176.0F), (double)((float)var9 - 96.0F - 16.0F), 0.0D).color(200, 0, 0, 0).endVertex();
         var6.pos((double)((float)var8 - 176.0F), (double)(var9 + 320), 0.0D).color(200, 0, 0, 0).endVertex();
         var6.pos((double)((float)var8 + 176.0F), (double)(var9 + 320), 0.0D).color(200, 0, 0, 0).endVertex();
         var6.pos((double)((float)var8 + 176.0F), (double)((float)var9 - 96.0F - 16.0F), 0.0D).color(200, 0, 0, 0).endVertex();
         var5.draw();
         GlStateManager.disableBlend();
         double var10 = 0.0D;
         Iterator var12 = var3.iterator();
         if(var12.hasNext()) {
            cj var26 = (cj)var12.next();
            int var27 = MathHelper.floor_double(var26.c / 4.0D) + 1;
            var6.begin(6, DefaultVertexFormats.POSITION_COLOR);
            int var28 = var26.a();
            int var29 = var28 >> 16 & 255;
            int var34 = var28 >> 8 & 255;
            int var18 = var28 & 255;
            var6.pos((double)var8, (double)var9, 0.0D).color(var29, var34, var18, 255).endVertex();
            int var19 = var27;

            while(true) {
               float var20 = (float)((var10 + var26.c * (double)var19 / (double)var27) * 3.141592653589793D * 2.0D / 100.0D);
               float var21 = MathHelper.sin(var20) * 160.0F;
               float var22 = MathHelper.cos(var20) * 160.0F * 0.5F;
               var6.pos((double)((float)var8 + var21), (double)((float)var9 - var22), 0.0D).color(var29, var34, var18, 255).endVertex();
               --var19;
            }
         }

         DecimalFormat var23 = new DecimalFormat("##0.00");
         GlStateManager.enableTexture2D();
         String var13 = "";
         if(!var4.b.equals("unspecified")) {
            var13 = var13 + "[0] ";
         }

         if(var4.b.isEmpty()) {
            var13 = var13 + "ROOT ";
         } else {
            var13 = var13 + var4.b + " ";
         }

         int var14 = 16777215;
         this.fontRendererObj.drawStringWithShadow(var13, (float)(var8 - 160), (float)(var9 - 80 - 16), 16777215);
         this.fontRendererObj.drawStringWithShadow(var13 = var23.format(var4.d) + "%", (float)(var8 + 160 - this.fontRendererObj.d(var13)), (float)(var9 - 80 - 16), 16777215);

         for(int var15 = 0; var15 < var3.size(); ++var15) {
            cj var16 = (cj)var3.get(var15);
            String var17 = "";
            if(var16.b.equals("unspecified")) {
               var17 = var17 + "[?] ";
            } else {
               var17 = var17 + "[" + (var15 + 1) + "] ";
            }

            var17 = var17 + var16.b;
            this.fontRendererObj.drawStringWithShadow(var17, (float)(var8 - 160), (float)(var9 + 80 + var15 * 8 + 20), var16.a());
            this.fontRendererObj.drawStringWithShadow(var17 = var23.format(var16.c) + "%", (float)(var8 + 160 - 50 - this.fontRendererObj.d(var17)), (float)(var9 + 80 + var15 * 8 + 20), var16.a());
            this.fontRendererObj.drawStringWithShadow(var17 = var23.format(var16.d) + "%", (float)(var8 + 160 - this.fontRendererObj.d(var17)), (float)(var9 + 80 + var15 * 8 + 20), var16.a());
         }
      }

   }

   private void resize(int var1, int var2) {
      this.displayWidth = Math.max(1, var1);
      this.displayHeight = Math.max(1, var2);
      if(this.currentScreen != null) {
         ScaledResolution var3 = new ScaledResolution(this);
         this.currentScreen.onResize(this, var3.getScaledWidth(), var3.getScaledHeight());
      }

      this.loadingScreen = new LoadingScreenRenderer(this);
      this.updateFramebufferSize();
   }

   private void updateFramebufferSize() {
      this.framebufferMc.createBindFramebuffer(this.displayWidth, this.displayHeight);
      if(this.entityRenderer != null) {
         this.entityRenderer.updateShaderGroupSize(this.displayWidth, this.displayHeight);
      }

   }

   public MusicTicker func_181535_r() {
      return this.mcMusicTicker;
   }

   public void setIngameFocus() {
      if(Display.isActive() && !this.inGameHasFocus) {
         this.inGameHasFocus = true;
         this.mouseHelper.grabMouseCursor();
         this.displayGuiScreen((GuiScreen)null);
         this.leftClickCounter = 10000;
      }

   }

   public void toggleFullscreen() {
      try {
         this.fullscreen = !this.fullscreen;
         this.gameSettings.fullScreen = this.fullscreen;
         if(this.fullscreen) {
            this.C();
            this.displayWidth = Display.getDisplayMode().getWidth();
            this.displayHeight = Display.getDisplayMode().getHeight();
         } else {
            Display.setDisplayMode(new DisplayMode(this.tempDisplayWidth, this.tempDisplayHeight));
            this.displayWidth = this.tempDisplayWidth;
            this.displayHeight = this.tempDisplayHeight;
         }

         if(this.displayWidth <= 0) {
            this.displayWidth = 1;
         }

         if(this.displayHeight <= 0) {
            this.displayHeight = 1;
         }

         if(this.currentScreen != null) {
            this.resize(this.displayWidth, this.displayHeight);
         } else {
            this.updateFramebufferSize();
         }

         Display.setFullscreen(this.fullscreen);
         Display.setVSyncEnabled(this.gameSettings.enableVsync);
         this.updateDisplay();
      } catch (LWJGLException var2) {
         var2.printStackTrace();
      }

   }

   public void loadWorld(WorldClient var1) {
      this.loadWorld(var1, "");
   }

   public void runTick() throws IOException {
      EventManager.call(new TickUpdateEvent());
      if(!Novoline.getInstance().isAnythingNull()) {
         Novoline.getInstance().getTaskManager().getFutureTasks().forEach(Minecraft::lambda$runTick$1);
      }

      if(this.rightClickDelayTimer > 0) {
         --this.rightClickDelayTimer;
      }

      this.mcProfiler.startSection("gui");
      if(!this.isGamePaused) {
         this.ingameGUI.updateTick();
      }

      this.mcProfiler.endSection();
      this.entityRenderer.getMouseOver(1.0F);
      this.mcProfiler.startSection("gameMode");
      if(!this.isGamePaused && this.world != null) {
         this.at.g();
      }

      this.mcProfiler.endStartSection("textures");
      if(!this.isGamePaused) {
         this.renderEngine.tick();
      }

      if(this.currentScreen == null && this.player != null) {
         if(this.player.getHealth() <= 0.0F) {
            this.displayGuiScreen((GuiScreen)null);
         } else if(this.player.isPlayerSleeping() && this.world != null) {
            this.displayGuiScreen(new GuiSleepMP());
         }
      } else if(this.currentScreen != null && this.currentScreen instanceof GuiSleepMP && !this.player.isPlayerSleeping()) {
         this.displayGuiScreen((GuiScreen)null);
      }

      if(this.currentScreen != null) {
         this.leftClickCounter = 10000;
      }

      if(this.currentScreen != null) {
         Minecraft var10000 = this;

         try {
            var10000.currentScreen.handleInput();
         } catch (Throwable var9) {
            CrashReport var2 = CrashReport.makeCrashReport(var9, "Updating screen events");
            CrashReportCategory var3 = var2.makeCategory("Affected screen");
            var3.addCrashSectionCallable("Screen name", this::lambda$runTick$2);
            throw new ReportedException(var2);
         }

         if(this.currentScreen != null) {
            var10000 = this;

            try {
               var10000.currentScreen.updateScreen();
            } catch (Throwable var8) {
               CrashReport var15 = CrashReport.makeCrashReport(var8, "Ticking screen");
               CrashReportCategory var22 = var15.makeCategory("Affected screen");
               var22.addCrashSectionCallable("Screen name", this::lambda$runTick$3);
               throw new ReportedException(var15);
            }
         }
      }

      if(this.currentScreen == null || this.currentScreen.allowUserInput) {
         this.mcProfiler.endStartSection("mouse");

         while(Mouse.next()) {
            int var1 = Mouse.getEventButton();
            KeyBinding.setKeyBindState(var1 - 100, Mouse.getEventButtonState());
            if(var1 != -1) {
               ObjectIterator var16 = this.novoline.getModuleManager().getModuleManager().values().iterator();

               while(var16.hasNext()) {
                  ModuleHolder var23 = (ModuleHolder)var16.next();
                  AbstractModule var4 = var23.getModule();
                  ModuleKeybind var5 = (ModuleKeybind)var4.getKeybind().get();
                  if(var5 instanceof MouseKeybind) {
                     MouseKeybind var6 = (MouseKeybind)var5;
                     if(var6.getKey() == var1 && var6.click()) {
                        var4.toggle();
                     }
                  }
               }
            }

            if(Mouse.getEventButtonState()) {
               if(this.player.isSpectator() && var1 == 2) {
                  this.ingameGUI.getSpectatorGui().func_175261_b();
               } else {
                  KeyBinding.onTick(var1 - 100);
               }
            }

            long var17 = getSystemTime() - this.systemTime;
            if(var17 <= 200L) {
               int var27 = Mouse.getEventDWheel();
               if(this.player.isSpectator()) {
                  var27 = -1;
                  if(this.ingameGUI.getSpectatorGui().func_175262_a()) {
                     this.ingameGUI.getSpectatorGui().func_175259_b(-var27);
                  } else {
                     float var31 = MathHelper.clamp_float(this.player.abilities.getFlySpeed() + (float)var27 * 0.005F, 0.0F, 0.2F);
                     this.player.abilities.setFlySpeed(var31);
                  }
               } else {
                  this.player.inventory.changeCurrentItem(var27);
               }

               if(this.currentScreen != null) {
                  Minecraft var34 = this;

                  try {
                     var34.currentScreen.handleMouseInput();
                  } catch (Throwable var7) {
                     throw new ReportedException(CrashReport.makeCrashReport(var7, "Exception in input tick"));
                  }
               } else if(!this.inGameHasFocus && Mouse.getEventButtonState()) {
                  this.setIngameFocus();
               }
            }
         }

         if(this.leftClickCounter > 0) {
            --this.leftClickCounter;
         }

         this.mcProfiler.endStartSection("keyboard");

         label806:
         while(true) {
            if(Keyboard.next()) {
               int var14 = Keyboard.getEventKey() == 0?Keyboard.getEventCharacter() + 256:Keyboard.getEventKey();
               KeyBinding.setKeyBindState(var14, Keyboard.getEventKeyState());
               if(Keyboard.getEventKeyState()) {
                  KeyBinding.onTick(var14);
               }

               if(this.debugCrashKeyPressTime > 0L) {
                  if(getSystemTime() - this.debugCrashKeyPressTime >= 6000L) {
                     throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
                  }

                  if(!Keyboard.isKeyDown(46) || !Keyboard.isKeyDown(61)) {
                     this.debugCrashKeyPressTime = -1L;
                  }
               } else if(Keyboard.isKeyDown(46) && Keyboard.isKeyDown(61)) {
                  this.debugCrashKeyPressTime = getSystemTime();
               }

               this.dispatchKeypresses();
               if(!Keyboard.getEventKeyState()) {
                  continue;
               }

               if(var14 == 62 && this.entityRenderer != null) {
                  this.entityRenderer.switchUseShader();
               }

               if(this.currentScreen != null) {
                  this.currentScreen.handleKeyboardInput();
               } else {
                  EventManager.call(new KeyPressEvent(var14));
                  if(var14 == 1) {
                     this.displayInGameMenu();
                  }

                  ObjectIterator var19 = this.novoline.getModuleManager().getModuleManager().values().iterator();

                  while(var19.hasNext()) {
                     ModuleHolder var25 = (ModuleHolder)var19.next();
                     AbstractModule var29 = var25.getModule();
                     int var32 = ((ModuleKeybind)var29.getKeybind().get()).getKey();
                     if(var29.getKeybind().get() instanceof KeyboardKeybind && var32 == var14) {
                        var29.toggle();
                     }
                  }

                  if(var14 == 32 && Keyboard.isKeyDown(61) && this.ingameGUI != null) {
                     this.ingameGUI.n().m();
                  }

                  if(var14 == 31 && Keyboard.isKeyDown(61)) {
                     this.refreshResources();
                  }

                  if(var14 == 17) {
                     Keyboard.isKeyDown(61);
                  }

                  if(var14 == 18) {
                     Keyboard.isKeyDown(61);
                  }

                  if(var14 == 47) {
                     Keyboard.isKeyDown(61);
                  }

                  if(var14 == 38) {
                     Keyboard.isKeyDown(61);
                  }

                  if(var14 == 22) {
                     Keyboard.isKeyDown(61);
                  }

                  if(var14 == 49 && Keyboard.isKeyDown(61)) {
                     Path var20 = this.novoline.getDataFolder().toAbsolutePath();
                     String var26 = var20.toString();
                     String var30 = null;
                     if(aNh.a() == Util$EnumOS.OSX) {
                        var30 = "/usr/bin/open";
                     } else if(aNh.a() == Util$EnumOS.WINDOWS) {
                        var30 = "cmd.exe /C start \"Open file\" \"%s\"";
                     } else {
                        this.logger.info("No idea what command to run");
                     }

                     try {
                        Runtime.getRuntime().exec(String.format(var30, new Object[]{var26}));
                        return;
                     } catch (IOException var11) {
                        this.logger.error("Couldn\'t open file", var11);
                     }
                  }

                  if(var14 == 20 && Keyboard.isKeyDown(61)) {
                     this.refreshResources();
                  }

                  if(var14 == 33 && Keyboard.isKeyDown(61)) {
                     this.gameSettings.setOptionValue(GameSettings$Options.RENDER_DISTANCE, GuiScreen.isShiftKeyDown()?-1:1);
                  }

                  if(var14 == 30 && Keyboard.isKeyDown(61)) {
                     this.renderGlobal.loadRenderers();
                  }

                  if(var14 == 35 && Keyboard.isKeyDown(61)) {
                     this.gameSettings.advancedItemTooltips = !this.gameSettings.advancedItemTooltips;
                     this.gameSettings.saveOptions();
                  }

                  if(var14 == 48 && Keyboard.isKeyDown(61)) {
                     this.renderManager.setDebugBoundingBox(!this.renderManager.isDebugBoundingBox());
                  }

                  if(var14 == 25 && Keyboard.isKeyDown(61)) {
                     this.gameSettings.pauseOnLostFocus = !this.gameSettings.pauseOnLostFocus;
                     this.gameSettings.saveOptions();
                  }

                  if(var14 == 59) {
                     this.gameSettings.hideGUI = !this.gameSettings.hideGUI;
                  }

                  if(var14 == 61) {
                     this.gameSettings.showDebugInfo = !this.gameSettings.showDebugInfo;
                     this.gameSettings.showDebugProfilerChart = GuiScreen.isShiftKeyDown();
                     this.gameSettings.field_181657_aC = GuiScreen.isAltKeyDown();
                  }

                  if(this.gameSettings.keyBindTogglePerspective.isPressed()) {
                     ++this.gameSettings.thirdPersonView;
                     if(this.gameSettings.thirdPersonView > 2) {
                        this.gameSettings.thirdPersonView = 0;
                     }

                     if(this.gameSettings.thirdPersonView == 0) {
                        this.entityRenderer.loadEntityShader(this.getRenderViewEntity());
                     } else if(this.gameSettings.thirdPersonView == 1) {
                        this.entityRenderer.loadEntityShader((Entity)null);
                     }

                     this.renderGlobal.setDisplayListEntitiesDirty();
                  }

                  if(this.gameSettings.keyBindSmoothCamera.isPressed()) {
                     this.gameSettings.smoothCamera = !this.gameSettings.smoothCamera;
                  }
               }

               if(!this.gameSettings.showDebugInfo || !this.gameSettings.showDebugProfilerChart) {
                  continue;
               }

               if(var14 == 11) {
                  this.updateDebugProfilerName(0);
               }

               int var21 = 0;

               while(true) {
                  if(var21 >= 9) {
                     continue label806;
                  }

                  if(var14 == 2 + var21) {
                     this.updateDebugProfilerName(var21 + 1);
                  }

                  ++var21;
               }
            }

            for(int var12 = 0; var12 < 9; ++var12) {
               if(this.gameSettings.keyBindsHotbar[var12].isPressed()) {
                  if(this.player.isSpectator()) {
                     this.ingameGUI.getSpectatorGui().func_175260_a(var12);
                  } else {
                     this.player.inventory.currentItem = var12;
                  }
               }
            }

            boolean var13 = this.gameSettings.chatVisibility != EntityPlayer$EnumChatVisibility.HIDDEN;

            while(this.gameSettings.keyBindInventory.isPressed()) {
               if(this.at.a()) {
                  this.player.sendHorseInventory();
               } else {
                  this.getNetHandler().b(new C16PacketClientStatus(C16PacketClientStatus$EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                  this.displayGuiScreen(new GuiInventory(this.player));
               }
            }

            while(this.gameSettings.keyBindDrop.isPressed()) {
               if(!this.player.isSpectator()) {
                  this.player.dropOneItem(GuiScreen.isCtrlKeyDown());
               }
            }

            while(this.gameSettings.keyBindChat.isPressed()) {
               this.displayGuiScreen(new aHM());
            }

            if(this.currentScreen == null && this.gameSettings.keyBindCommand.isPressed()) {
               this.displayGuiScreen(new aHM("/"));
            }

            if(this.player != null && this.player.isUsingItem()) {
               if(!this.gameSettings.keyBindUseItem.isKeyDown()) {
                  this.at.a((EntityPlayer)this.player);
               }

               while(this.gameSettings.keyBindAttack.isPressed()) {
                  ;
               }

               while(this.gameSettings.keyBindUseItem.isPressed()) {
                  ;
               }

               while(this.gameSettings.keyBindPickBlock.isPressed()) {
                  ;
               }
            } else {
               while(this.gameSettings.keyBindAttack.isPressed()) {
                  this.clickMouse();
               }

               while(this.gameSettings.keyBindUseItem.isPressed()) {
                  this.rightClickMouse();
               }

               while(this.gameSettings.keyBindPickBlock.isPressed()) {
                  this.D();
               }
            }

            if(this.gameSettings.keyBindUseItem.isKeyDown() && this.rightClickDelayTimer == 0 && !this.player.isUsingItem()) {
               this.rightClickMouse();
            }

            this.sendClickBlockToController(this.currentScreen == null && this.gameSettings.keyBindAttack.isKeyDown() && this.inGameHasFocus);
            break;
         }
      }

      if(this.world != null) {
         if(this.player != null) {
            ++this.joinPlayerCounter;
            if(this.joinPlayerCounter == 30) {
               this.joinPlayerCounter = 0;
               this.world.joinEntityInSurroundings(this.player);
            }
         }

         this.mcProfiler.endStartSection("gameRenderer");
         if(!this.isGamePaused) {
            this.entityRenderer.updateRenderer();
         }

         this.mcProfiler.endStartSection("levelRenderer");
         if(!this.isGamePaused) {
            this.renderGlobal.updateClouds();
         }

         this.mcProfiler.endStartSection("level");
         if(!this.isGamePaused) {
            if(this.world.getLastLightningBolt() > 0) {
               this.world.setLastLightningBolt(this.world.getLastLightningBolt() - 1);
            }

            this.world.updateEntities();
         }
      } else if(this.entityRenderer.isShaderActive()) {
         this.entityRenderer.func_181022_b();
      }

      if(!this.isGamePaused) {
         this.mcMusicTicker.update();
         this.mcSoundHandler.update();
      }

      if(this.world != null) {
         if(!this.isGamePaused) {
            this.world.setAllowedSpawnTypes(this.world.getDifficulty() != EnumDifficulty.PEACEFUL, true);
            Minecraft var35 = this;

            try {
               var35.world.tick();
            } catch (Throwable var10) {
               CrashReport var18 = CrashReport.makeCrashReport(var10, "Exception in world tick");
               if(this.world == null) {
                  CrashReportCategory var24 = var18.makeCategory("Affected level");
                  var24.addCrashSection("Problem", "Level is null!");
               } else {
                  this.world.addWorldInfoToCrashReport(var18);
               }

               throw new ReportedException(var18);
            }
         }

         this.mcProfiler.endStartSection("animateTick");
         if(!this.isGamePaused && this.world != null) {
            this.world.doVoidFogParticles(MathHelper.floor_double(this.player.posX), MathHelper.floor_double(this.player.posY), MathHelper.floor_double(this.player.posZ));
         }

         this.mcProfiler.endStartSection("particles");
         if(!this.isGamePaused) {
            this.effectRenderer.updateEffects();
         }
      } else if(this.myNetworkManager != null) {
         this.mcProfiler.endStartSection("pendingConnection");
         this.myNetworkManager.processReceivedPackets();
      }

      this.mcProfiler.endSection();
      this.systemTime = getSystemTime();
   }

   public void launchIntegratedServer(String var1, String var2, WorldSettings var3) {
      this.loadWorld((WorldClient)null);
      ISaveHandler var4 = this.saveLoader.getSaveLoader(var1, false);
      WorldInfo var5 = var4.loadWorldInfo();
      var5 = new WorldInfo(var3, var1);
      var4.saveWorldInfo(var5);
      var3 = new WorldSettings(var5);
      Minecraft var10000 = this;

      try {
         var10000.theIntegratedServer = new IntegratedServer(this, var1, var2, var3);
         this.theIntegratedServer.startServerThread();
         this.integratedServerIsRunning = true;
      } catch (Throwable var10) {
         CrashReport var7 = CrashReport.makeCrashReport(var10, "Starting integrated server");
         CrashReportCategory var8 = var7.makeCategory("Starting integrated server");
         var8.addCrashSection("Level ID", var1);
         var8.addCrashSection("Level Name", var2);
         throw new ReportedException(var7);
      }

      this.loadingScreen.displaySavingString(I18n.format("menu.loadingLevel", new Object[0]));

      while(!this.theIntegratedServer.serverIsInRunLoop()) {
         String var6 = this.theIntegratedServer.getUserMessage();
         this.loadingScreen.displayLoadingString(I18n.format(var6, new Object[0]));
         long var15 = 200L;

         try {
            Thread.sleep(var15);
         } catch (InterruptedException var9) {
            ;
         }
      }

      this.displayGuiScreen((GuiScreen)null);
      SocketAddress var13 = this.theIntegratedServer.getNetworkSystem().addLocalEndpoint();
      NetworkManager var14 = NetworkManager.provideLocalClient(var13);
      var14.setNetHandler(new NetHandlerLoginClient(var14, this, (GuiScreen)null));
      var14.sendPacket(new C00Handshake(47, var13.toString(), 0, EnumConnectionState.LOGIN));
      var14.sendPacket(new C00PacketLoginStart(this.getSession().getProfile()));
      this.myNetworkManager = var14;
   }

   public final boolean isDemo() {
      return this.isDemo;
   }

   public NetHandlerPlayClient getNetHandler() {
      return this.player != null?this.player.connection:null;
   }

   private void D() {
      if(this.objectMouseOver != null) {
         boolean var1 = this.player.abilities.isCreative();
         boolean var2 = false;
         boolean var3 = false;
         Object var4 = null;
         if(this.objectMouseOver.typeOfHit == MovingObjectPosition$MovingObjectType.BLOCK) {
            BlockPos var6 = this.objectMouseOver.getBlockPos();
            Block var7 = this.world.getBlockState(var6).getBlock();
            if(var7.getMaterial() != Material.air) {
               Item var5 = var7.getItem(this.world, var6);
            }
         } else {
            if(this.objectMouseOver.typeOfHit == MovingObjectPosition$MovingObjectType.ENTITY && this.objectMouseOver.entityHit != null) {
               ;
            }

         }
      }
   }

   private ItemStack func_181036_a(Item var1, int var2, TileEntity var3) {
      ItemStack var4 = new ItemStack(var1, 1, var2);
      NBTTagCompound var5 = new NBTTagCompound();
      var3.writeToNBT(var5);
      if(var1 == Items.skull && var5.hasKey("Owner")) {
         NBTTagCompound var8 = var5.getCompoundTag("Owner");
         NBTTagCompound var9 = new NBTTagCompound();
         var9.setTag("SkullOwner", var8);
         var4.setTagCompound(var9);
      } else {
         var4.setTagInfo("BlockEntityTag", var5);
         NBTTagCompound var6 = new NBTTagCompound();
         NBTTagList var7 = new NBTTagList();
         var7.appendTag(new NBTTagString("(+NBT)"));
         var6.setTag("Lore", var7);
         var4.setTagInfo("display", var6);
      }

      return var4;
   }

   public CrashReport addGraphicsAndWorldToCrashReport(CrashReport var1) {
      var1.getCategory().addCrashSectionCallable("Launched Version", this::lambda$addGraphicsAndWorldToCrashReport$4);
      var1.getCategory().addCrashSectionCallable("LWJGL", Sys::getVersion);
      var1.getCategory().addCrashSectionCallable("OpenGL", Minecraft::lambda$addGraphicsAndWorldToCrashReport$5);
      var1.getCategory().addCrashSectionCallable("GL Caps", OpenGlHelper::getLogText);
      var1.getCategory().addCrashSectionCallable("Using VBOs", this::lambda$addGraphicsAndWorldToCrashReport$6);
      var1.getCategory().addCrashSectionCallable("Is Modded", Minecraft::lambda$addGraphicsAndWorldToCrashReport$7);
      var1.getCategory().addCrashSectionCallable("Type", Minecraft::lambda$addGraphicsAndWorldToCrashReport$8);
      var1.getCategory().addCrashSectionCallable("Resource Packs", this::lambda$addGraphicsAndWorldToCrashReport$9);
      var1.getCategory().addCrashSectionCallable("Current Language", this::lambda$addGraphicsAndWorldToCrashReport$10);
      var1.getCategory().addCrashSectionCallable("Profiler Position", this::lambda$addGraphicsAndWorldToCrashReport$11);
      var1.getCategory().addCrashSectionCallable("CPU", OpenGlHelper::func_183029_j);
      if(this.world != null) {
         this.world.addWorldInfoToCrashReport(var1);
      }

      return var1;
   }

   public ListenableFuture scheduleResourcesRefresh() {
      return this.addScheduledTask(this::refreshResources);
   }

   public void addServerStatsToSnooper(PlayerUsageSnooper var1) {
      var1.addClientStat("fps", Integer.valueOf(debugFPS));
      var1.addClientStat("vsync_enabled", Boolean.valueOf(this.gameSettings.enableVsync));
      var1.addClientStat("display_frequency", Integer.valueOf(Display.getDisplayMode().getFrequency()));
      var1.addClientStat("display_type", this.fullscreen?"fullscreen":"windowed");
      var1.addClientStat("run_time", Long.valueOf((MinecraftServer.getCurrentTimeMillis() - var1.getMinecraftStartTimeMillis()) / 60L * 1000L));
      var1.addClientStat("current_action", this.func_181538_aA());
      String var2 = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN?"little":"big";
      var1.addClientStat("endianness", var2);
      var1.addClientStat("resource_packs", Integer.valueOf(this.mcResourcePackRepository.getRepositoryEntries().size()));
      int var3 = 0;

      for(ResourcePackRepository$Entry var5 : this.mcResourcePackRepository.getRepositoryEntries()) {
         var1.addClientStat("resource_pack[" + var3++ + "]", var5.getResourcePackName());
      }

      if(this.theIntegratedServer != null && this.theIntegratedServer.getPlayerUsageSnooper() != null) {
         var1.addClientStat("snooper_partner", this.theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
      }

   }

   private String func_181538_aA() {
      return this.theIntegratedServer != null?(this.theIntegratedServer.getPublic()?"hosting_lan":"singleplayer"):(this.currentServerData != null?(this.currentServerData.func_181041_d()?"playing_lan":"multiplayer"):"out_of_game");
   }

   public void addServerTypeToSnooper(PlayerUsageSnooper var1) {
      var1.addStatToSnooper("opengl_version", GL11.glGetString(7938));
      var1.addStatToSnooper("opengl_vendor", GL11.glGetString(7936));
      var1.addStatToSnooper("client_brand", ClientBrandRetriever.getClientModName());
      var1.addStatToSnooper("launched_version", this.launchedVersion);
      ContextCapabilities var2 = GLContext.getCapabilities();
      var1.addStatToSnooper("gl_caps[ARB_arrays_of_arrays]", Boolean.valueOf(var2.GL_ARB_arrays_of_arrays));
      var1.addStatToSnooper("gl_caps[ARB_base_instance]", Boolean.valueOf(var2.GL_ARB_base_instance));
      var1.addStatToSnooper("gl_caps[ARB_blend_func_extended]", Boolean.valueOf(var2.GL_ARB_blend_func_extended));
      var1.addStatToSnooper("gl_caps[ARB_clear_buffer_object]", Boolean.valueOf(var2.GL_ARB_clear_buffer_object));
      var1.addStatToSnooper("gl_caps[ARB_color_buffer_float]", Boolean.valueOf(var2.GL_ARB_color_buffer_float));
      var1.addStatToSnooper("gl_caps[ARB_compatibility]", Boolean.valueOf(var2.GL_ARB_compatibility));
      var1.addStatToSnooper("gl_caps[ARB_compressed_texture_pixel_storage]", Boolean.valueOf(var2.GL_ARB_compressed_texture_pixel_storage));
      var1.addStatToSnooper("gl_caps[ARB_compute_shader]", Boolean.valueOf(var2.GL_ARB_compute_shader));
      var1.addStatToSnooper("gl_caps[ARB_copy_buffer]", Boolean.valueOf(var2.GL_ARB_copy_buffer));
      var1.addStatToSnooper("gl_caps[ARB_copy_image]", Boolean.valueOf(var2.GL_ARB_copy_image));
      var1.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", Boolean.valueOf(var2.GL_ARB_depth_buffer_float));
      var1.addStatToSnooper("gl_caps[ARB_compute_shader]", Boolean.valueOf(var2.GL_ARB_compute_shader));
      var1.addStatToSnooper("gl_caps[ARB_copy_buffer]", Boolean.valueOf(var2.GL_ARB_copy_buffer));
      var1.addStatToSnooper("gl_caps[ARB_copy_image]", Boolean.valueOf(var2.GL_ARB_copy_image));
      var1.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", Boolean.valueOf(var2.GL_ARB_depth_buffer_float));
      var1.addStatToSnooper("gl_caps[ARB_depth_clamp]", Boolean.valueOf(var2.GL_ARB_depth_clamp));
      var1.addStatToSnooper("gl_caps[ARB_depth_texture]", Boolean.valueOf(var2.GL_ARB_depth_texture));
      var1.addStatToSnooper("gl_caps[ARB_draw_buffers]", Boolean.valueOf(var2.GL_ARB_draw_buffers));
      var1.addStatToSnooper("gl_caps[ARB_draw_buffers_blend]", Boolean.valueOf(var2.GL_ARB_draw_buffers_blend));
      var1.addStatToSnooper("gl_caps[ARB_draw_elements_base_vertex]", Boolean.valueOf(var2.GL_ARB_draw_elements_base_vertex));
      var1.addStatToSnooper("gl_caps[ARB_draw_indirect]", Boolean.valueOf(var2.GL_ARB_draw_indirect));
      var1.addStatToSnooper("gl_caps[ARB_draw_instanced]", Boolean.valueOf(var2.GL_ARB_draw_instanced));
      var1.addStatToSnooper("gl_caps[ARB_explicit_attrib_location]", Boolean.valueOf(var2.GL_ARB_explicit_attrib_location));
      var1.addStatToSnooper("gl_caps[ARB_explicit_uniform_location]", Boolean.valueOf(var2.GL_ARB_explicit_uniform_location));
      var1.addStatToSnooper("gl_caps[ARB_fragment_layer_viewport]", Boolean.valueOf(var2.GL_ARB_fragment_layer_viewport));
      var1.addStatToSnooper("gl_caps[ARB_fragment_program]", Boolean.valueOf(var2.GL_ARB_fragment_program));
      var1.addStatToSnooper("gl_caps[ARB_fragment_shader]", Boolean.valueOf(var2.GL_ARB_fragment_shader));
      var1.addStatToSnooper("gl_caps[ARB_fragment_program_shadow]", Boolean.valueOf(var2.GL_ARB_fragment_program_shadow));
      var1.addStatToSnooper("gl_caps[ARB_framebuffer_object]", Boolean.valueOf(var2.GL_ARB_framebuffer_object));
      var1.addStatToSnooper("gl_caps[ARB_framebuffer_sRGB]", Boolean.valueOf(var2.GL_ARB_framebuffer_sRGB));
      var1.addStatToSnooper("gl_caps[ARB_geometry_shader4]", Boolean.valueOf(var2.GL_ARB_geometry_shader4));
      var1.addStatToSnooper("gl_caps[ARB_gpu_shader5]", Boolean.valueOf(var2.GL_ARB_gpu_shader5));
      var1.addStatToSnooper("gl_caps[ARB_half_float_pixel]", Boolean.valueOf(var2.GL_ARB_half_float_pixel));
      var1.addStatToSnooper("gl_caps[ARB_half_float_vertex]", Boolean.valueOf(var2.GL_ARB_half_float_vertex));
      var1.addStatToSnooper("gl_caps[ARB_instanced_arrays]", Boolean.valueOf(var2.GL_ARB_instanced_arrays));
      var1.addStatToSnooper("gl_caps[ARB_map_buffer_alignment]", Boolean.valueOf(var2.GL_ARB_map_buffer_alignment));
      var1.addStatToSnooper("gl_caps[ARB_map_buffer_range]", Boolean.valueOf(var2.GL_ARB_map_buffer_range));
      var1.addStatToSnooper("gl_caps[ARB_multisample]", Boolean.valueOf(var2.GL_ARB_multisample));
      var1.addStatToSnooper("gl_caps[ARB_multitexture]", Boolean.valueOf(var2.GL_ARB_multitexture));
      var1.addStatToSnooper("gl_caps[ARB_occlusion_query2]", Boolean.valueOf(var2.GL_ARB_occlusion_query2));
      var1.addStatToSnooper("gl_caps[ARB_pixel_buffer_object]", Boolean.valueOf(var2.GL_ARB_pixel_buffer_object));
      var1.addStatToSnooper("gl_caps[ARB_seamless_cube_map]", Boolean.valueOf(var2.GL_ARB_seamless_cube_map));
      var1.addStatToSnooper("gl_caps[ARB_shader_objects]", Boolean.valueOf(var2.GL_ARB_shader_objects));
      var1.addStatToSnooper("gl_caps[ARB_shader_stencil_export]", Boolean.valueOf(var2.GL_ARB_shader_stencil_export));
      var1.addStatToSnooper("gl_caps[ARB_shader_texture_lod]", Boolean.valueOf(var2.GL_ARB_shader_texture_lod));
      var1.addStatToSnooper("gl_caps[ARB_shadow]", Boolean.valueOf(var2.GL_ARB_shadow));
      var1.addStatToSnooper("gl_caps[ARB_shadow_ambient]", Boolean.valueOf(var2.GL_ARB_shadow_ambient));
      var1.addStatToSnooper("gl_caps[ARB_stencil_texturing]", Boolean.valueOf(var2.GL_ARB_stencil_texturing));
      var1.addStatToSnooper("gl_caps[ARB_sync]", Boolean.valueOf(var2.GL_ARB_sync));
      var1.addStatToSnooper("gl_caps[ARB_tessellation_shader]", Boolean.valueOf(var2.GL_ARB_tessellation_shader));
      var1.addStatToSnooper("gl_caps[ARB_texture_border_clamp]", Boolean.valueOf(var2.GL_ARB_texture_border_clamp));
      var1.addStatToSnooper("gl_caps[ARB_texture_buffer_object]", Boolean.valueOf(var2.GL_ARB_texture_buffer_object));
      var1.addStatToSnooper("gl_caps[ARB_texture_cube_map]", Boolean.valueOf(var2.GL_ARB_texture_cube_map));
      var1.addStatToSnooper("gl_caps[ARB_texture_cube_map_array]", Boolean.valueOf(var2.GL_ARB_texture_cube_map_array));
      var1.addStatToSnooper("gl_caps[ARB_texture_non_power_of_two]", Boolean.valueOf(var2.GL_ARB_texture_non_power_of_two));
      var1.addStatToSnooper("gl_caps[ARB_uniform_buffer_object]", Boolean.valueOf(var2.GL_ARB_uniform_buffer_object));
      var1.addStatToSnooper("gl_caps[ARB_vertex_blend]", Boolean.valueOf(var2.GL_ARB_vertex_blend));
      var1.addStatToSnooper("gl_caps[ARB_vertex_buffer_object]", Boolean.valueOf(var2.GL_ARB_vertex_buffer_object));
      var1.addStatToSnooper("gl_caps[ARB_vertex_program]", Boolean.valueOf(var2.GL_ARB_vertex_program));
      var1.addStatToSnooper("gl_caps[ARB_vertex_shader]", Boolean.valueOf(var2.GL_ARB_vertex_shader));
      var1.addStatToSnooper("gl_caps[EXT_bindable_uniform]", Boolean.valueOf(var2.GL_EXT_bindable_uniform));
      var1.addStatToSnooper("gl_caps[EXT_blend_equation_separate]", Boolean.valueOf(var2.GL_EXT_blend_equation_separate));
      var1.addStatToSnooper("gl_caps[EXT_blend_func_separate]", Boolean.valueOf(var2.GL_EXT_blend_func_separate));
      var1.addStatToSnooper("gl_caps[EXT_blend_minmax]", Boolean.valueOf(var2.GL_EXT_blend_minmax));
      var1.addStatToSnooper("gl_caps[EXT_blend_subtract]", Boolean.valueOf(var2.GL_EXT_blend_subtract));
      var1.addStatToSnooper("gl_caps[EXT_draw_instanced]", Boolean.valueOf(var2.GL_EXT_draw_instanced));
      var1.addStatToSnooper("gl_caps[EXT_framebuffer_multisample]", Boolean.valueOf(var2.GL_EXT_framebuffer_multisample));
      var1.addStatToSnooper("gl_caps[EXT_framebuffer_object]", Boolean.valueOf(var2.GL_EXT_framebuffer_object));
      var1.addStatToSnooper("gl_caps[EXT_framebuffer_sRGB]", Boolean.valueOf(var2.GL_EXT_framebuffer_sRGB));
      var1.addStatToSnooper("gl_caps[EXT_geometry_shader4]", Boolean.valueOf(var2.GL_EXT_geometry_shader4));
      var1.addStatToSnooper("gl_caps[EXT_gpu_program_parameters]", Boolean.valueOf(var2.GL_EXT_gpu_program_parameters));
      var1.addStatToSnooper("gl_caps[EXT_gpu_shader4]", Boolean.valueOf(var2.GL_EXT_gpu_shader4));
      var1.addStatToSnooper("gl_caps[EXT_multi_draw_arrays]", Boolean.valueOf(var2.GL_EXT_multi_draw_arrays));
      var1.addStatToSnooper("gl_caps[EXT_packed_depth_stencil]", Boolean.valueOf(var2.GL_EXT_packed_depth_stencil));
      var1.addStatToSnooper("gl_caps[EXT_paletted_texture]", Boolean.valueOf(var2.GL_EXT_paletted_texture));
      var1.addStatToSnooper("gl_caps[EXT_rescale_normal]", Boolean.valueOf(var2.GL_EXT_rescale_normal));
      var1.addStatToSnooper("gl_caps[EXT_separate_shader_objects]", Boolean.valueOf(var2.GL_EXT_separate_shader_objects));
      var1.addStatToSnooper("gl_caps[EXT_shader_image_load_store]", Boolean.valueOf(var2.GL_EXT_shader_image_load_store));
      var1.addStatToSnooper("gl_caps[EXT_shadow_funcs]", Boolean.valueOf(var2.GL_EXT_shadow_funcs));
      var1.addStatToSnooper("gl_caps[EXT_shared_texture_palette]", Boolean.valueOf(var2.GL_EXT_shared_texture_palette));
      var1.addStatToSnooper("gl_caps[EXT_stencil_clear_tag]", Boolean.valueOf(var2.GL_EXT_stencil_clear_tag));
      var1.addStatToSnooper("gl_caps[EXT_stencil_two_side]", Boolean.valueOf(var2.GL_EXT_stencil_two_side));
      var1.addStatToSnooper("gl_caps[EXT_stencil_wrap]", Boolean.valueOf(var2.GL_EXT_stencil_wrap));
      var1.addStatToSnooper("gl_caps[EXT_texture_3d]", Boolean.valueOf(var2.GL_EXT_texture_3d));
      var1.addStatToSnooper("gl_caps[EXT_texture_array]", Boolean.valueOf(var2.GL_EXT_texture_array));
      var1.addStatToSnooper("gl_caps[EXT_texture_buffer_object]", Boolean.valueOf(var2.GL_EXT_texture_buffer_object));
      var1.addStatToSnooper("gl_caps[EXT_texture_integer]", Boolean.valueOf(var2.GL_EXT_texture_integer));
      var1.addStatToSnooper("gl_caps[EXT_texture_lod_bias]", Boolean.valueOf(var2.GL_EXT_texture_lod_bias));
      var1.addStatToSnooper("gl_caps[EXT_texture_sRGB]", Boolean.valueOf(var2.GL_EXT_texture_sRGB));
      var1.addStatToSnooper("gl_caps[EXT_vertex_shader]", Boolean.valueOf(var2.GL_EXT_vertex_shader));
      var1.addStatToSnooper("gl_caps[EXT_vertex_weighting]", Boolean.valueOf(var2.GL_EXT_vertex_weighting));
      var1.addStatToSnooper("gl_caps[gl_max_vertex_uniforms]", Integer.valueOf(GL11.glGetInteger('譊')));
      GL11.glGetError();
      var1.addStatToSnooper("gl_caps[gl_max_fragment_uniforms]", Integer.valueOf(GL11.glGetInteger('證')));
      GL11.glGetError();
      var1.addStatToSnooper("gl_caps[gl_max_vertex_attribs]", Integer.valueOf(GL11.glGetInteger('衩')));
      GL11.glGetError();
      var1.addStatToSnooper("gl_caps[gl_max_vertex_texture_image_units]", Integer.valueOf(GL11.glGetInteger('譌')));
      GL11.glGetError();
      var1.addStatToSnooper("gl_caps[gl_max_texture_image_units]", Integer.valueOf(GL11.glGetInteger('衲')));
      GL11.glGetError();
      var1.addStatToSnooper("gl_caps[gl_max_texture_image_units]", Integer.valueOf(GL11.glGetInteger('裿')));
      GL11.glGetError();
      var1.addStatToSnooper("gl_max_texture_size", Integer.valueOf(getGLMaximumTextureSize()));
   }

   public void loadWorld(WorldClient var1, String var2) {
      NetHandlerPlayClient var3 = this.getNetHandler();
      var3.cleanup();
      if(this.theIntegratedServer != null && this.theIntegratedServer.isAnvilFileSet()) {
         this.theIntegratedServer.initiateShutdown();
         this.theIntegratedServer.setStaticInstance();
      }

      this.theIntegratedServer = null;
      this.guiAchievement.clearAchievements();
      this.entityRenderer.getMapItemRenderer().clearLoadedMaps();
      this.renderViewEntity = null;
      this.myNetworkManager = null;
      if(Thread.currentThread() == this.mcThread && this.loadingScreen != null) {
         this.loadingScreen.resetProgressAndMessage(var2);
         this.loadingScreen.displayLoadingString("");
      }

      if(this.world != null) {
         this.mcResourcePackRepository.func_148529_f();
         this.ingameGUI.func_181029_i();
         this.setServerData((ServerData)null);
         this.integratedServerIsRunning = false;
      }

      this.mcSoundHandler.stopSounds();
      this.world = var1;
      if(this.renderGlobal != null) {
         this.renderGlobal.setWorldAndLoadRenderers(var1);
      }

      if(this.effectRenderer != null) {
         this.effectRenderer.clearEffects(var1);
      }

      if(this.player == null) {
         this.player = this.at.a((World)var1, (StatFileWriter)(new StatFileWriter()));
         this.at.b(this.player);
      }

      this.player.preparePlayerToSpawn();
      var1.spawnEntityInWorld(this.player);
      this.player.setMovementInput(new MovementInputFromOptions(this.gameSettings));
      this.at.c(this.player);
      this.renderViewEntity = this.player;
      this.systemTime = 0L;
   }

   public boolean isSnooperEnabled() {
      return this.gameSettings.snooperEnabled;
   }

   public void setServerData(ServerData var1) {
      this.currentServerData = var1;
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

   public PlayerUsageSnooper getPlayerUsageSnooper() {
      return this.usageSnooper;
   }

   public boolean isFullScreen() {
      return this.fullscreen;
   }

   public Session getSession() {
      return this.session;
   }

   public PropertyMap getTwitchDetails() {
      return this.twitchDetails;
   }

   public PropertyMap func_181037_M() {
      if(this.field_181038_N.isEmpty()) {
         GameProfile var1 = this.getSessionService().fillProfileProperties(this.session.getProfile(), false);
         this.field_181038_N.putAll(var1.getProperties());
      }

      return this.field_181038_N;
   }

   public PropertyMap fillSessionProfileProperties() {
      this.field_181038_N.clear();
      GameProfile var1 = this.getSessionService().fillProfileProperties(this.session.getProfile(), false);
      this.field_181038_N.putAll(var1.getProperties());
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

   public BA j() {
      return this.h;
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

   public MusicTicker$MusicType getAmbientMusicType() {
      return this.player != null?(this.player.worldObj.provider instanceof WorldProviderHell?MusicTicker$MusicType.NETHER:(this.player.worldObj.provider instanceof WorldProviderEnd?(BossStatus.bossName != null && BossStatus.statusBarTime > 0?MusicTicker$MusicType.END_BOSS:MusicTicker$MusicType.END):(this.player.abilities.isCreative() && this.player.abilities.isAllowFlying()?MusicTicker$MusicType.CREATIVE:MusicTicker$MusicType.GAME))):MusicTicker$MusicType.MENU;
   }

   public IStream getTwitchStream() {
      return this.stream;
   }

   public void setDimensionAndSpawnPlayer(int var1) {
      this.world.setInitialSpawnLocation();
      this.world.removeAllEntities();
      int var2 = 0;
      String var3 = null;
      if(this.player != null) {
         var2 = this.player.getEntityID();
         this.world.removeEntity(this.player);
         var3 = this.player.getClientBrand();
      }

      this.renderViewEntity = null;
      EntityPlayerSP var4 = this.player;
      this.player = this.at.a((World)this.world, (StatFileWriter)(this.player == null?new StatFileWriter():this.player.getStatFileWriter()));
      this.player.k().a(var4.k().c());
      this.player.dimension = var1;
      this.renderViewEntity = this.player;
      this.player.preparePlayerToSpawn();
      this.player.setClientBrand(var3);
      this.world.spawnEntityInWorld(this.player);
      this.at.b(this.player);
      this.player.setMovementInput(new MovementInputFromOptions(this.gameSettings));
      this.player.setEntityId(var2);
      this.at.c(this.player);
      this.player.setReducedDebug(var4.hasReducedDebug());
      if(this.currentScreen instanceof GuiGameOver) {
         this.displayGuiScreen((GuiScreen)null);
      }

   }

   public MinecraftSessionService getSessionService() {
      return this.sessionService;
   }

   public nk V() {
      return this.B;
   }

   public Entity getRenderViewEntity() {
      return this.renderViewEntity;
   }

   public void setRenderViewEntity(Entity var1) {
      this.renderViewEntity = var1;
      this.entityRenderer.loadEntityShader(var1);
   }

   public void dispatchKeypresses() {
      int var1 = Keyboard.getEventKey() == 0?Keyboard.getEventCharacter():Keyboard.getEventKey();
      if(!Keyboard.isRepeatEvent()) {
         if(this.currentScreen instanceof GuiControls && ((GuiControls)this.currentScreen).time > getSystemTime() - 20L) {
            if(var1 == this.gameSettings.keyBindStreamToggleMic.getKeyCode()) {
               this.stream.muteMicrophone(false);
            }
         } else if(Keyboard.getEventKeyState()) {
            if(var1 == this.gameSettings.keyBindStreamStartStop.getKeyCode()) {
               if(this.getTwitchStream().isBroadcasting()) {
                  this.getTwitchStream().stopBroadcasting();
               } else if(this.getTwitchStream().isReadyToBroadcast()) {
                  this.displayGuiScreen(new GuiYesNo(this::lambda$dispatchKeypresses$12, I18n.format("stream.confirm_start", new Object[0]), "", 0));
               } else if(this.getTwitchStream().func_152928_D() && this.getTwitchStream().func_152936_l()) {
                  if(this.world != null) {
                     this.ingameGUI.n().a((IChatComponent)(new ChatComponentText("Not ready to start streaming yet!")));
                  }
               } else {
                  GuiStreamUnavailable.func_152321_a(this.currentScreen);
               }
            } else if(var1 == this.gameSettings.keyBindStreamPauseUnpause.getKeyCode() && this.getTwitchStream().isBroadcasting()) {
               if(this.getTwitchStream().isPaused()) {
                  this.getTwitchStream().unpause();
               } else {
                  this.getTwitchStream().pause();
               }
            }
         } else if(var1 == this.gameSettings.keyBindStreamCommercials.getKeyCode()) {
            if(this.getTwitchStream().isBroadcasting()) {
               this.getTwitchStream().requestCommercial();
            }
         } else if(var1 == this.gameSettings.keyBindStreamToggleMic.getKeyCode()) {
            this.stream.muteMicrophone(true);
         } else if(var1 == this.gameSettings.keyBindFullscreen.getKeyCode()) {
            this.toggleFullscreen();
         } else if(var1 == this.gameSettings.keyBindScreenshot.getKeyCode()) {
            ScreenShotHelper.saveScreenshot(this.mcDataDir, this.displayWidth, this.displayHeight, this.framebufferMc);
            Novoline.getInstance().getNotificationManager().pop("Screenshot Taken!", 500, NotificationType.SUCCESS);
         }
      }

   }

   public ListenableFuture addScheduledTask(Callable param1) {
      // $FF: Couldn't be decompiled
   }

   private void writeKillsults() {
      try {
         Killsults var1 = (Killsults)Novoline.getInstance().getModuleManager().getModule(Killsults.class);
         BufferedWriter var2 = new BufferedWriter(new FileWriter(var1.getPath().toString()));

         for(String var4 : var1.getKillsults()) {
            var2.write(var4);
            var2.newLine();
         }

         var2.close();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

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

   public int getDebugFPS() {
      return Math.max(1, debugFPS);
   }

   public FrameTimer func_181539_aj() {
      return this.field_181542_y;
   }

   public ListenableFuture addScheduledTask(Runnable var1) {
      Validate.notNull(var1);
      return this.addScheduledTask(Executors.callable(var1));
   }

   public boolean func_181540_al() {
      return this.field_181541_X;
   }

   public void func_181537_a(boolean var1) {
      this.field_181541_X = var1;
   }

   public Logger getLogger() {
      return this.logger;
   }

   public Minecraft logger(Logger var1) {
      this.logger = var1;
      return this;
   }

   private void lambda$dispatchKeypresses$12(boolean var1, int var2) {
      this.getTwitchStream().func_152930_t();
      this.displayGuiScreen((GuiScreen)null);
   }

   private String lambda$addGraphicsAndWorldToCrashReport$11() throws Exception {
      return this.mcProfiler.profilingEnabled?this.mcProfiler.getNameOfLastSection():"N/A (disabled)";
   }

   private String lambda$addGraphicsAndWorldToCrashReport$10() throws Exception {
      return this.h.c().toString();
   }

   private String lambda$addGraphicsAndWorldToCrashReport$9() throws Exception {
      StringBuilder var1 = new StringBuilder();

      for(Object var3 : this.gameSettings.resourcePacks) {
         if(var1.length() > 0) {
            var1.append(", ");
         }

         var1.append(var3);
         if(this.gameSettings.field_183018_l.contains(var3)) {
            var1.append(" (incompatible)");
         }
      }

      return var1.toString();
   }

   private static String lambda$addGraphicsAndWorldToCrashReport$8() throws Exception {
      return "Client (map_client.txt)";
   }

   private static String lambda$addGraphicsAndWorldToCrashReport$7() throws Exception {
      String var0 = ClientBrandRetriever.getClientModName();
      return !var0.equals("vanilla")?"Definitely; Client brand changed to \'" + var0 + "\'":(Minecraft.class.getSigners() == null?"Very likely; Jar signature invalidated":"Probably not. Jar signature remains and client brand is untouched.");
   }

   private String lambda$addGraphicsAndWorldToCrashReport$6() throws Exception {
      return this.gameSettings.useVbo?"Yes":"No";
   }

   private static String lambda$addGraphicsAndWorldToCrashReport$5() throws Exception {
      return GL11.glGetString(7937) + " GL version " + GL11.glGetString(7938) + ", " + GL11.glGetString(7936);
   }

   private String lambda$addGraphicsAndWorldToCrashReport$4() throws Exception {
      return this.launchedVersion;
   }

   private String lambda$runTick$3() throws Exception {
      return this.currentScreen.getClass().getCanonicalName();
   }

   private String lambda$runTick$2() throws Exception {
      return this.currentScreen.getClass().getCanonicalName();
   }

   private static void lambda$runTick$1(FutureTask var0) {
      if(var0.delay()) {
         var0.execute();
         Novoline.getInstance().getTaskManager().getFutureTasks().remove(var0);
         EventManager.unregister(var0);
      } else {
         var0.run();
      }

   }

   private String lambda$startGame$0(String var1) {
      try {
         return String.format(var1, new Object[]{GameSettings.getKeyDisplayString(this.gameSettings.keyBindInventory.getKeyCode())});
      } catch (Exception var3) {
         return "Error: " + var3.getLocalizedMessage();
      }
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
