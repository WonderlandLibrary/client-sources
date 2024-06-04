package net.minecraft.client;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import javax.imageio.ImageIO;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.MusicTicker.MusicType;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMemoryErrorScreen;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.gui.stream.GuiStreamUnavailable;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.main.GameConfiguration.DisplayInformation;
import net.minecraft.client.main.GameConfiguration.FolderInformation;
import net.minecraft.client.main.GameConfiguration.GameInformation;
import net.minecraft.client.main.GameConfiguration.ServerInformation;
import net.minecraft.client.main.GameConfiguration.UserInformation;
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
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.GrassColorReloadListener;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.resources.ResourceIndex;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.ResourcePackRepository.Entry;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.client.resources.data.PackMetadataSectionSerializer;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings.Options;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.stream.IStream;
import net.minecraft.client.stream.NullStream;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecart.EnumMinecartType;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.profiler.Profiler.Result;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.stats.IStatStringFormat;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MinecraftError;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import net.minecraft.util.Util;
import net.minecraft.util.Util.EnumOS;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
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
import yung.purity.Client;
import yung.purity.api.EventBus;
import yung.purity.api.events.EventKey;

public class Minecraft implements IThreadListener, IPlayerUsage
{
  private static final Logger logger = ;
  private static final ResourceLocation locationMojangPng = new ResourceLocation("textures/gui/title/mojang.png");
  public static final boolean isRunningOnMac = Util.getOSType() == Util.EnumOS.OSX;
  

  public static byte[] memoryReserve = new byte[10485760];
  private static final List macDisplayModes = Lists.newArrayList(new DisplayMode[] { new DisplayMode(2560, 1600), new DisplayMode(2880, 1800) });
  
  private final File fileResourcepacks;
  
  private final PropertyMap twitchDetails;
  
  private ServerData currentServerData;
  
  private TextureManager renderEngine;
  
  private static Minecraft theMinecraft;
  
  public PlayerControllerMP playerController;
  private boolean fullscreen;
  private boolean field_175619_R = true;
  
  private boolean hasCrashed;
  
  private CrashReport crashReporter;
  public int displayWidth;
  public int displayHeight;
  public Timer timer = new Timer(20.0F);
  

  private PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("client", this, MinecraftServer.getCurrentTimeMillis());
  
  public WorldClient theWorld;
  
  public RenderGlobal renderGlobal;
  
  private RenderManager renderManager;
  
  private RenderItem renderItem;
  
  private ItemRenderer itemRenderer;
  
  public EntityPlayerSP thePlayer;
  
  private Entity field_175622_Z;
  
  public Entity pointedEntity;
  
  public EffectRenderer effectRenderer;
  
  private final Session session;
  
  private boolean isGamePaused;
  
  public FontRenderer fontRendererObj;
  
  public FontRenderer standardGalacticFontRenderer;
  
  public GuiScreen currentScreen;
  
  public LoadingScreenRenderer loadingScreen;
  
  public EntityRenderer entityRenderer;
  
  private int leftClickCounter;
  
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
  
  private final boolean jvm64bit;
  
  private final boolean isDemo;
  
  private NetworkManager myNetworkManager;
  private boolean integratedServerIsRunning;
  public final Profiler mcProfiler = new Profiler();
  



  private long debugCrashKeyPressTime = -1L;
  private IReloadableResourceManager mcResourceManager;
  private final IMetadataSerializer metadataSerializer_ = new IMetadataSerializer();
  private final List defaultResourcePacks = Lists.newArrayList();
  private final DefaultResourcePack mcDefaultResourcePack;
  private ResourcePackRepository mcResourcePackRepository;
  private LanguageManager mcLanguageManager;
  private IStream stream;
  private Framebuffer framebufferMc;
  private TextureMap textureMapBlocks;
  private SoundHandler mcSoundHandler;
  private MusicTicker mcMusicTicker;
  private ResourceLocation mojangLogo;
  private final MinecraftSessionService sessionService;
  private SkinManager skinManager;
  private final Queue scheduledTasks = Queues.newArrayDeque();
  private long field_175615_aJ = 0L;
  private final Thread mcThread = Thread.currentThread();
  

  private ModelManager modelManager;
  
  private BlockRendererDispatcher field_175618_aM;
  
  volatile boolean running = true;
  

  public String debug = "";
  public boolean field_175613_B = false;
  public boolean field_175614_C = false;
  public boolean field_175611_D = false;
  public boolean field_175612_E = true;
  

  long debugUpdateTime = getSystemTime();
  
  int fpsCounter;
  
  long prevFrameTime = -1L;
  

  private String debugProfilerName = "root";
  private static final String __OBFID = "CL_00000631";
  
  public Minecraft(GameConfiguration p_i45547_1_)
  {
    yung.purity.utils.Security.operationfc();
    
    theMinecraft = this;
    mcDataDir = field_178744_c.field_178760_a;
    fileAssets = field_178744_c.field_178759_c;
    fileResourcepacks = field_178744_c.field_178758_b;
    launchedVersion = field_178741_d.field_178755_b;
    twitchDetails = field_178745_a.field_178750_b;
    mcDefaultResourcePack = new DefaultResourcePack(new ResourceIndex(field_178744_c.field_178759_c, field_178744_c.field_178757_d).func_152782_a());
    proxy = (field_178745_a.field_178751_c == null ? Proxy.NO_PROXY : field_178745_a.field_178751_c);
    sessionService = new YggdrasilAuthenticationService(field_178745_a.field_178751_c, UUID.randomUUID().toString()).createMinecraftSessionService();
    session = field_178745_a.field_178752_a;
    logger.info("Setting user: " + session.getUsername());
    logger.info("(Session ID is " + session.getSessionID() + ")");
    isDemo = field_178741_d.field_178756_a;
    displayWidth = (field_178743_b.field_178764_a > 0 ? field_178743_b.field_178764_a : 1);
    displayHeight = (field_178743_b.field_178762_b > 0 ? field_178743_b.field_178762_b : 1);
    tempDisplayWidth = field_178743_b.field_178764_a;
    tempDisplayHeight = field_178743_b.field_178762_b;
    fullscreen = field_178743_b.field_178763_c;
    jvm64bit = isJvm64bit();
    theIntegratedServer = new IntegratedServer(this);
    
    if (field_178742_e.field_178754_a != null)
    {
      serverName = field_178742_e.field_178754_a;
      serverPort = field_178742_e.field_178753_b;
    }
    
    ImageIO.setUseCache(false);
    Bootstrap.register();
  }
  
  public void run()
  {
    running = true;
    

    try
    {
      startGame();
    }
    catch (Throwable var11)
    {
      CrashReport var2 = CrashReport.makeCrashReport(var11, "Initializing game");
      var2.makeCategory("Initialization");
      displayCrashReport(addGraphicsAndWorldToCrashReport(var2)); return;
    }
    



    for (;;)
    {
      try
      {
        if ((!hasCrashed) || (crashReporter == null))
        {
          try
          {
            runGameLoop();
          }
          catch (OutOfMemoryError var10)
          {
            freeMemory();
            displayGuiScreen(new GuiMemoryErrorScreen());
            System.gc();
          }
          
        }
        else
        {
          displayCrashReport(crashReporter);
          return;

        }
        

      }
      catch (MinecraftError localMinecraftError) {}catch (ReportedException var13)
      {

        addGraphicsAndWorldToCrashReport(var13.getCrashReport());
        freeMemory();
        logger.fatal("Reported exception thrown!", var13);
        displayCrashReport(var13.getCrashReport());
      }
      catch (Throwable var14)
      {
        CrashReport var2 = addGraphicsAndWorldToCrashReport(new CrashReport("Unexpected error", var14));
        freeMemory();
        logger.fatal("Unreported exception thrown!", var14);
        displayCrashReport(var2);
      }
      finally
      {
        shutdownMinecraftApplet();
      }
      if (!running) {}
    }
  }
  















































  private void startGame()
    throws LWJGLException
  {
    gameSettings = new GameSettings(this, mcDataDir);
    defaultResourcePacks.add(mcDefaultResourcePack);
    startTimerHackThread();
    
    if ((gameSettings.overrideHeight > 0) && (gameSettings.overrideWidth > 0))
    {
      displayWidth = gameSettings.overrideWidth;
      displayHeight = gameSettings.overrideHeight;
    }
    
    logger.info("LWJGL Version: " + Sys.getVersion());
    func_175594_ao();
    func_175605_an();
    func_175609_am();
    OpenGlHelper.initializeTextures();
    framebufferMc = new Framebuffer(displayWidth, displayHeight, true);
    framebufferMc.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
    func_175608_ak();
    mcResourcePackRepository = new ResourcePackRepository(fileResourcepacks, new File(mcDataDir, "server-resource-packs"), mcDefaultResourcePack, metadataSerializer_, gameSettings);
    mcResourceManager = new net.minecraft.client.resources.SimpleReloadableResourceManager(metadataSerializer_);
    mcLanguageManager = new LanguageManager(metadataSerializer_, gameSettings.language);
    mcResourceManager.registerReloadListener(mcLanguageManager);
    refreshResources();
    renderEngine = new TextureManager(mcResourceManager);
    mcResourceManager.registerReloadListener(renderEngine);
    func_180510_a(renderEngine);
    func_175595_al();
    skinManager = new SkinManager(renderEngine, new File(fileAssets, "skins"), sessionService);
    saveLoader = new AnvilSaveConverter(new File(mcDataDir, "saves"));
    mcSoundHandler = new SoundHandler(mcResourceManager, gameSettings);
    mcResourceManager.registerReloadListener(mcSoundHandler);
    mcMusicTicker = new MusicTicker(this);
    fontRendererObj = new FontRenderer(gameSettings, new ResourceLocation("textures/font/ascii.png"), renderEngine, false);
    
    if (gameSettings.language != null)
    {
      fontRendererObj.setUnicodeFlag(isUnicode());
      fontRendererObj.setBidiFlag(mcLanguageManager.isCurrentLanguageBidirectional());
    }
    
    standardGalacticFontRenderer = new FontRenderer(gameSettings, new ResourceLocation("textures/font/ascii_sga.png"), renderEngine, false);
    mcResourceManager.registerReloadListener(fontRendererObj);
    mcResourceManager.registerReloadListener(standardGalacticFontRenderer);
    mcResourceManager.registerReloadListener(new GrassColorReloadListener());
    mcResourceManager.registerReloadListener(new net.minecraft.client.resources.FoliageColorReloadListener());
    net.minecraft.stats.AchievementList.openInventory.setStatStringFormatter(new IStatStringFormat()
    {
      private static final String __OBFID = "CL_00000632";
      
      public String formatString(String p_74535_1_)
      {
        try {
          return String.format(p_74535_1_, new Object[] { GameSettings.getKeyDisplayString(gameSettings.keyBindInventory.getKeyCode()) });
        }
        catch (Exception var3)
        {
          return "Error: " + var3.getLocalizedMessage();
        }
      }
    });
    mouseHelper = new MouseHelper();
    checkGLError("Pre startup");
    GlStateManager.func_179098_w();
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
    checkGLError("Startup");
    textureMapBlocks = new TextureMap("textures");
    textureMapBlocks.setMipmapLevels(gameSettings.mipmapLevels);
    renderEngine.loadTickableTexture(TextureMap.locationBlocksTexture, textureMapBlocks);
    renderEngine.bindTexture(TextureMap.locationBlocksTexture);
    textureMapBlocks.func_174937_a(false, gameSettings.mipmapLevels > 0);
    modelManager = new ModelManager(textureMapBlocks);
    mcResourceManager.registerReloadListener(modelManager);
    renderItem = new RenderItem(renderEngine, modelManager);
    renderManager = new RenderManager(renderEngine, renderItem);
    itemRenderer = new ItemRenderer(this);
    mcResourceManager.registerReloadListener(renderItem);
    entityRenderer = new EntityRenderer(this, mcResourceManager);
    mcResourceManager.registerReloadListener(entityRenderer);
    field_175618_aM = new BlockRendererDispatcher(modelManager.getBlockModelShapes(), gameSettings);
    mcResourceManager.registerReloadListener(field_175618_aM);
    renderGlobal = new RenderGlobal(this);
    mcResourceManager.registerReloadListener(renderGlobal);
    guiAchievement = new GuiAchievement(this);
    GlStateManager.viewport(0, 0, displayWidth, displayHeight);
    effectRenderer = new EffectRenderer(theWorld, renderEngine);
    checkGLError("Post startup");cr0.a();
    ingameGUI = new GuiIngame(this);
    
    if (serverName != null)
    {
      displayGuiScreen(new GuiConnecting(new GuiMainMenu(), this, serverName, serverPort));
    }
    else
    {
      displayGuiScreen(new GuiMainMenu());
    }
    
    renderEngine.deleteTexture(mojangLogo);
    mojangLogo = null;
    loadingScreen = new LoadingScreenRenderer(this);
    
    if ((gameSettings.fullScreen) && (!fullscreen))
    {
      toggleFullscreen();
    }
    
    try
    {
      Display.setVSyncEnabled(gameSettings.enableVsync);
    }
    catch (OpenGLException var2)
    {
      gameSettings.enableVsync = false;
      gameSettings.saveOptions();
    }
    
    renderGlobal.func_174966_b();
    

    Client.instance.initiate();
  }
  
  private void func_175608_ak()
  {
    metadataSerializer_.registerMetadataSectionType(new net.minecraft.client.resources.data.TextureMetadataSectionSerializer(), TextureMetadataSection.class);
    metadataSerializer_.registerMetadataSectionType(new FontMetadataSectionSerializer(), net.minecraft.client.resources.data.FontMetadataSection.class);
    metadataSerializer_.registerMetadataSectionType(new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
    metadataSerializer_.registerMetadataSectionType(new PackMetadataSectionSerializer(), PackMetadataSection.class);
    metadataSerializer_.registerMetadataSectionType(new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
  }
  
  private void func_175595_al()
  {
    try
    {
      stream = new net.minecraft.client.stream.TwitchStream(this, (Property)Iterables.getFirst(twitchDetails.get("twitch_access_token"), null));
    }
    catch (Throwable var2)
    {
      stream = new NullStream(var2);
      logger.error("Couldn't initialize twitch stream");
    }
  }
  
  private void func_175609_am() throws LWJGLException
  {
    Display.setResizable(true);
    Display.setTitle("Minecraft 1.8");
    
    try
    {
      Display.create(new PixelFormat().withDepthBits(24));
    }
    catch (LWJGLException var4)
    {
      logger.error("Couldn't set pixel format", var4);
      
      try
      {
        Thread.sleep(1000L);
      }
      catch (InterruptedException localInterruptedException) {}
      



      if (fullscreen)
      {
        updateDisplayMode();
      }
      
      Display.create();
    }
  }
  
  private void func_175605_an() throws LWJGLException
  {
    if (fullscreen)
    {
      Display.setFullscreen(true);
      DisplayMode var1 = Display.getDisplayMode();
      displayWidth = Math.max(1, var1.getWidth());
      displayHeight = Math.max(1, var1.getHeight());
    }
    else
    {
      Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
    }
  }
  
  private void func_175594_ao()
  {
    Util.EnumOS var1 = Util.getOSType();
    
    if (var1 != Util.EnumOS.OSX)
    {
      InputStream var2 = null;
      InputStream var3 = null;
      
      try
      {
        var2 = mcDefaultResourcePack.func_152780_c(new ResourceLocation("icons/icon_16x16.png"));
        var3 = mcDefaultResourcePack.func_152780_c(new ResourceLocation("icons/icon_32x32.png"));
        
        if ((var2 != null) && (var3 != null))
        {
          Display.setIcon(new ByteBuffer[] { readImageToBuffer(var2), readImageToBuffer(var3) });
        }
      }
      catch (IOException var8)
      {
        logger.error("Couldn't set icon", var8);
      }
      finally
      {
        IOUtils.closeQuietly(var2);
        IOUtils.closeQuietly(var3);
      }
    }
  }
  
  private static boolean isJvm64bit()
  {
    String[] var0 = { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
    String[] var1 = var0;
    int var2 = var0.length;
    
    for (int var3 = 0; var3 < var2; var3++)
    {
      String var4 = var1[var3];
      String var5 = System.getProperty(var4);
      
      if ((var5 != null) && (var5.contains("64")))
      {
        return true;
      }
    }
    
    return false;
  }
  
  public Framebuffer getFramebuffer()
  {
    return framebufferMc;
  }
  
  public String func_175600_c()
  {
    return launchedVersion;
  }
  
  private void startTimerHackThread()
  {
    Thread var1 = new Thread("Timer hack thread")
    {
      private static final String __OBFID = "CL_00000639";
      
      public void run() {
        while (running)
        {
          try
          {
            Thread.sleep(2147483647L);

          }
          catch (InterruptedException localInterruptedException) {}
        }
        
      }
      
    };
    var1.setDaemon(true);
    var1.start();
  }
  
  public void crashed(CrashReport crash)
  {
    hasCrashed = true;
    crashReporter = crash;
  }
  



  public void displayCrashReport(CrashReport crashReportIn)
  {
    File var2 = new File(getMinecraftmcDataDir, "crash-reports");
    File var3 = new File(var2, "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt");
    Bootstrap.func_179870_a(crashReportIn.getCompleteReport());
    
    if (crashReportIn.getFile() != null)
    {
      Bootstrap.func_179870_a("#@!@# Game crashed! Crash report saved to: #@!@# " + crashReportIn.getFile());
      System.exit(-1);
    }
    else if (crashReportIn.saveToFile(var3))
    {
      Bootstrap.func_179870_a("#@!@# Game crashed! Crash report saved to: #@!@# " + var3.getAbsolutePath());
      System.exit(-1);
    }
    else
    {
      Bootstrap.func_179870_a("#@?@# Game crashed! Crash report could not be saved. #@?@#");
      System.exit(-2);
    }
  }
  
  public boolean isUnicode()
  {
    return (mcLanguageManager.isCurrentLocaleUnicode()) || (gameSettings.forceUnicodeFont);
  }
  
  public void refreshResources()
  {
    ArrayList var1 = Lists.newArrayList(defaultResourcePacks);
    Iterator var2 = mcResourcePackRepository.getRepositoryEntries().iterator();
    
    while (var2.hasNext())
    {
      ResourcePackRepository.Entry var3 = (ResourcePackRepository.Entry)var2.next();
      var1.add(var3.getResourcePack());
    }
    
    if (mcResourcePackRepository.getResourcePackInstance() != null)
    {
      var1.add(mcResourcePackRepository.getResourcePackInstance());
    }
    
    try
    {
      mcResourceManager.reloadResources(var1);
    }
    catch (RuntimeException var4)
    {
      logger.info("Caught error stitching, removing all assigned resourcepacks", var4);
      var1.clear();
      var1.addAll(defaultResourcePacks);
      mcResourcePackRepository.func_148527_a(Collections.emptyList());
      mcResourceManager.reloadResources(var1);
      gameSettings.resourcePacks.clear();
      gameSettings.saveOptions();
    }
    
    mcLanguageManager.parseLanguageMetadata(var1);
    
    if (renderGlobal != null)
    {
      renderGlobal.loadRenderers();
    }
  }
  
  private ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException
  {
    BufferedImage var2 = ImageIO.read(imageStream);
    int[] var3 = var2.getRGB(0, 0, var2.getWidth(), var2.getHeight(), null, 0, var2.getWidth());
    ByteBuffer var4 = ByteBuffer.allocate(4 * var3.length);
    int[] var5 = var3;
    int var6 = var3.length;
    
    for (int var7 = 0; var7 < var6; var7++)
    {
      int var8 = var5[var7];
      var4.putInt(var8 << 8 | var8 >> 24 & 0xFF);
    }
    
    var4.flip();
    return var4;
  }
  
  private void updateDisplayMode() throws LWJGLException
  {
    HashSet var1 = Sets.newHashSet();
    Collections.addAll(var1, Display.getAvailableDisplayModes());
    DisplayMode var2 = Display.getDesktopDisplayMode();
    
    if ((!var1.contains(var2)) && (Util.getOSType() == Util.EnumOS.OSX))
    {
      Iterator var3 = macDisplayModes.iterator();
      
      while (var3.hasNext())
      {
        DisplayMode var4 = (DisplayMode)var3.next();
        boolean var5 = true;
        Iterator var6 = var1.iterator();
        

        while (var6.hasNext())
        {
          DisplayMode var7 = (DisplayMode)var6.next();
          
          if ((var7.getBitsPerPixel() == 32) && (var7.getWidth() == var4.getWidth()) && (var7.getHeight() == var4.getHeight()))
          {
            var5 = false;
            break;
          }
        }
        
        if (!var5)
        {
          var6 = var1.iterator();
          
          while (var6.hasNext())
          {
            DisplayMode var7 = (DisplayMode)var6.next();
            
            if ((var7.getBitsPerPixel() == 32) && (var7.getWidth() == var4.getWidth() / 2) && (var7.getHeight() == var4.getHeight() / 2))
            {
              var2 = var7;
              break;
            }
          }
        }
      }
    }
    
    Display.setDisplayMode(var2);
    displayWidth = var2.getWidth();
    displayHeight = var2.getHeight();
  }
  
  private void func_180510_a(TextureManager p_180510_1_)
  {
    ScaledResolution var2 = new ScaledResolution(this);
    int var3 = var2.getScaleFactor();
    Framebuffer var4 = new Framebuffer(var2.getScaledWidth() * var3, var2.getScaledHeight() * var3, true);
    var4.bindFramebuffer(false);
    GlStateManager.matrixMode(5889);
    GlStateManager.loadIdentity();
    GlStateManager.ortho(0.0D, var2.getScaledWidth(), var2.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
    GlStateManager.matrixMode(5888);
    GlStateManager.loadIdentity();
    GlStateManager.translate(0.0F, 0.0F, -2000.0F);
    GlStateManager.disableLighting();
    GlStateManager.disableFog();
    GlStateManager.disableDepth();
    GlStateManager.func_179098_w();
    InputStream var5 = null;
    
    try
    {
      var5 = mcDefaultResourcePack.getInputStream(locationMojangPng);
      mojangLogo = p_180510_1_.getDynamicTextureLocation("logo", new net.minecraft.client.renderer.texture.DynamicTexture(ImageIO.read(var5)));
      p_180510_1_.bindTexture(mojangLogo);
    }
    catch (IOException var12)
    {
      logger.error("Unable to load logo: " + locationMojangPng, var12);
    }
    finally
    {
      IOUtils.closeQuietly(var5);
    }
    
    Tessellator var6 = Tessellator.getInstance();
    WorldRenderer var7 = var6.getWorldRenderer();
    var7.startDrawingQuads();
    var7.func_178991_c(16777215);
    var7.addVertexWithUV(0.0D, displayHeight, 0.0D, 0.0D, 0.0D);
    var7.addVertexWithUV(displayWidth, displayHeight, 0.0D, 0.0D, 0.0D);
    var7.addVertexWithUV(displayWidth, 0.0D, 0.0D, 0.0D, 0.0D);
    var7.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    var6.draw();
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    var7.func_178991_c(16777215);
    short var8 = 256;
    short var9 = 256;
    scaledTessellator((var2.getScaledWidth() - var8) / 2, (var2.getScaledHeight() - var9) / 2, 0, 0, var8, var9);
    GlStateManager.disableLighting();
    GlStateManager.disableFog();
    var4.unbindFramebuffer();
    var4.framebufferRender(var2.getScaledWidth() * var3, var2.getScaledHeight() * var3);
    GlStateManager.enableAlpha();
    GlStateManager.alphaFunc(516, 0.1F);
    func_175601_h();
  }
  



  public void scaledTessellator(int width, int height, int width2, int height2, int stdTextureWidth, int stdTextureHeight)
  {
    float var7 = 0.00390625F;
    float var8 = 0.00390625F;
    WorldRenderer var9 = Tessellator.getInstance().getWorldRenderer();
    var9.startDrawingQuads();
    var9.addVertexWithUV(width + 0, height + stdTextureHeight, 0.0D, (width2 + 0) * var7, (height2 + stdTextureHeight) * var8);
    var9.addVertexWithUV(width + stdTextureWidth, height + stdTextureHeight, 0.0D, (width2 + stdTextureWidth) * var7, (height2 + stdTextureHeight) * var8);
    var9.addVertexWithUV(width + stdTextureWidth, height + 0, 0.0D, (width2 + stdTextureWidth) * var7, (height2 + 0) * var8);
    var9.addVertexWithUV(width + 0, height + 0, 0.0D, (width2 + 0) * var7, (height2 + 0) * var8);
    Tessellator.getInstance().draw();
  }
  



  public ISaveFormat getSaveLoader()
  {
    return saveLoader;
  }
  



  public void displayGuiScreen(GuiScreen guiScreenIn)
  {
    if (currentScreen != null)
    {
      currentScreen.onGuiClosed();
    }
    
    if ((guiScreenIn == null) && (theWorld == null))
    {
      guiScreenIn = new GuiMainMenu();
    }
    else if ((guiScreenIn == null) && (thePlayer.getHealth() <= 0.0F))
    {
      guiScreenIn = new GuiGameOver();
    }
    
    if ((guiScreenIn instanceof GuiMainMenu))
    {
      gameSettings.showDebugInfo = false;
      ingameGUI.getChatGUI().clearChatMessages();
    }
    
    currentScreen = guiScreenIn;
    
    if (guiScreenIn != null)
    {
      setIngameNotInFocus();
      ScaledResolution var2 = new ScaledResolution(this);
      int var3 = var2.getScaledWidth();
      int var4 = var2.getScaledHeight();
      guiScreenIn.setWorldAndResolution(this, var3, var4);
      skipRenderWorld = false;
    }
    else
    {
      mcSoundHandler.resumeSounds();
      setIngameFocus();
    }
  }
  



  private void checkGLError(String message)
  {
    if (field_175619_R)
    {
      int var2 = GL11.glGetError();
      
      if (var2 != 0)
      {
        String var3 = GLU.gluErrorString(var2);
        logger.error("########## GL ERROR ##########");
        logger.error("@ " + message);
        logger.error(var2 + ": " + var3);
      }
    }
  }
  




  public void shutdownMinecraftApplet()
  {
    try
    {
      stream.shutdownStream();
      logger.info("Stopping!");
      
      Client.instance.shutDown();
      try
      {
        loadWorld(null);
      }
      catch (Throwable localThrowable) {}
      



      mcSoundHandler.unloadSounds();
    }
    finally
    {
      Display.destroy();
      
      if (!hasCrashed)
      {
        System.exit(0);
      }
    }
    
    System.gc();
  }
  


  private void runGameLoop()
    throws IOException
  {
    mcProfiler.startSection("root");
    
    if ((Display.isCreated()) && (Display.isCloseRequested()))
    {
      shutdown();
    }
    
    if ((isGamePaused) && (theWorld != null))
    {
      float var1 = timer.renderPartialTicks;
      timer.updateTimer();
      timer.renderPartialTicks = var1;
    }
    else
    {
      timer.updateTimer();
    }
    
    mcProfiler.startSection("scheduledExecutables");
    Queue var6 = scheduledTasks;
    
    synchronized (scheduledTasks)
    {
      while (!scheduledTasks.isEmpty())
      {
        ((FutureTask)scheduledTasks.poll()).run();
      }
    }
    
    mcProfiler.endSection();
    long var7 = System.nanoTime();
    mcProfiler.startSection("tick");
    
    for (int var3 = 0; var3 < timer.elapsedTicks; var3++)
    {
      runTick();
    }
    
    mcProfiler.endStartSection("preRenderErrors");
    long var8 = System.nanoTime() - var7;
    checkGLError("Pre render");
    mcProfiler.endStartSection("sound");
    mcSoundHandler.setListener(thePlayer, timer.renderPartialTicks);
    mcProfiler.endSection();
    mcProfiler.startSection("render");
    GlStateManager.pushMatrix();
    GlStateManager.clear(16640);
    framebufferMc.bindFramebuffer(true);
    mcProfiler.startSection("display");
    GlStateManager.func_179098_w();
    
    if ((thePlayer != null) && (thePlayer.isEntityInsideOpaqueBlock()))
    {
      gameSettings.thirdPersonView = 0;
    }
    
    mcProfiler.endSection();
    
    if (!skipRenderWorld)
    {
      mcProfiler.endStartSection("gameRenderer");
      entityRenderer.updateCameraAndRender(timer.renderPartialTicks);
      mcProfiler.endSection();
    }
    
    mcProfiler.endSection();
    
    if ((gameSettings.showDebugInfo) && (gameSettings.showDebugProfilerChart) && (!gameSettings.hideGUI))
    {
      if (!mcProfiler.profilingEnabled)
      {
        mcProfiler.clearProfiling();
      }
      
      mcProfiler.profilingEnabled = true;
      displayDebugInfo(var8);
    }
    else
    {
      mcProfiler.profilingEnabled = false;
      prevFrameTime = System.nanoTime();
    }
    
    guiAchievement.updateAchievementWindow();
    framebufferMc.unbindFramebuffer();
    GlStateManager.popMatrix();
    GlStateManager.pushMatrix();
    framebufferMc.framebufferRender(displayWidth, displayHeight);
    GlStateManager.popMatrix();
    GlStateManager.pushMatrix();
    entityRenderer.func_152430_c(timer.renderPartialTicks);
    GlStateManager.popMatrix();
    mcProfiler.startSection("root");
    func_175601_h();
    Thread.yield();
    mcProfiler.startSection("stream");
    mcProfiler.startSection("update");
    stream.func_152935_j();
    mcProfiler.endStartSection("submit");
    stream.func_152922_k();
    mcProfiler.endSection();
    mcProfiler.endSection();
    checkGLError("Post render");
    fpsCounter += 1;
    isGamePaused = ((isSingleplayer()) && (currentScreen != null) && (currentScreen.doesGuiPauseGame()) && (!theIntegratedServer.getPublic()));
    
    while (getSystemTime() >= debugUpdateTime + 1000L)
    {
      debugFPS = fpsCounter;
      debug = String.format("%d fps (%d chunk update%s) T: %s%s%s%s%s", new Object[] { Integer.valueOf(debugFPS), Integer.valueOf(RenderChunk.field_178592_a), RenderChunk.field_178592_a != 1 ? "s" : "", gameSettings.limitFramerate == GameSettings.Options.FRAMERATE_LIMIT.getValueMax() ? "inf" : Integer.valueOf(gameSettings.limitFramerate), gameSettings.enableVsync ? " vsync" : "", gameSettings.fancyGraphics ? "" : " fast", gameSettings.clouds ? " clouds" : "", OpenGlHelper.func_176075_f() ? " vbo" : "" });
      RenderChunk.field_178592_a = 0;
      debugUpdateTime += 1000L;
      fpsCounter = 0;
      usageSnooper.addMemoryStatsToSnooper();
      
      if (!usageSnooper.isSnooperRunning())
      {
        usageSnooper.startSnooper();
      }
    }
    
    if (isFramerateLimitBelowMax())
    {
      mcProfiler.startSection("fpslimit_wait");
      Display.sync(getLimitFramerate());
      mcProfiler.endSection();
    }
    
    mcProfiler.endSection();
  }
  
  public void func_175601_h()
  {
    mcProfiler.startSection("display_update");
    Display.update();
    mcProfiler.endSection();
    func_175604_i();
  }
  
  protected void func_175604_i()
  {
    if ((!fullscreen) && (Display.wasResized()))
    {
      int var1 = displayWidth;
      int var2 = displayHeight;
      displayWidth = Display.getWidth();
      displayHeight = Display.getHeight();
      
      if ((displayWidth != var1) || (displayHeight != var2))
      {
        if (displayWidth <= 0)
        {
          displayWidth = 1;
        }
        
        if (displayHeight <= 0)
        {
          displayHeight = 1;
        }
        
        resize(displayWidth, displayHeight);
      }
    }
  }
  
  public int getLimitFramerate()
  {
    return (theWorld == null) && (currentScreen != null) ? 30 : gameSettings.limitFramerate;
  }
  
  public boolean isFramerateLimitBelowMax()
  {
    return getLimitFramerate() < GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
  }
  
  public void freeMemory()
  {
    try
    {
      memoryReserve = new byte[0];
      renderGlobal.deleteAllDisplayLists();
    }
    catch (Throwable localThrowable) {}
    



    try
    {
      System.gc();
      loadWorld(null);
    }
    catch (Throwable localThrowable1) {}
    



    System.gc();
  }
  



  private void updateDebugProfilerName(int keyCount)
  {
    List var2 = mcProfiler.getProfilingData(debugProfilerName);
    
    if ((var2 != null) && (!var2.isEmpty()))
    {
      Profiler.Result var3 = (Profiler.Result)var2.remove(0);
      
      if (keyCount == 0)
      {
        if (field_76331_c.length() > 0)
        {
          int var4 = debugProfilerName.lastIndexOf(".");
          
          if (var4 >= 0)
          {
            debugProfilerName = debugProfilerName.substring(0, var4);
          }
        }
      }
      else
      {
        keyCount--;
        
        if ((keyCount < var2.size()) && (!getfield_76331_c.equals("unspecified")))
        {
          if (debugProfilerName.length() > 0)
          {
            debugProfilerName += ".";
          }
          
          debugProfilerName += getfield_76331_c;
        }
      }
    }
  }
  



  private void displayDebugInfo(long elapsedTicksTime)
  {
    if (mcProfiler.profilingEnabled)
    {
      List var3 = mcProfiler.getProfilingData(debugProfilerName);
      Profiler.Result var4 = (Profiler.Result)var3.remove(0);
      GlStateManager.clear(256);
      GlStateManager.matrixMode(5889);
      GlStateManager.enableColorMaterial();
      GlStateManager.loadIdentity();
      GlStateManager.ortho(0.0D, displayWidth, displayHeight, 0.0D, 1000.0D, 3000.0D);
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      GlStateManager.translate(0.0F, 0.0F, -2000.0F);
      GL11.glLineWidth(1.0F);
      GlStateManager.func_179090_x();
      Tessellator var5 = Tessellator.getInstance();
      WorldRenderer var6 = var5.getWorldRenderer();
      short var7 = 160;
      int var8 = displayWidth - var7 - 10;
      int var9 = displayHeight - var7 * 2;
      GlStateManager.enableBlend();
      var6.startDrawingQuads();
      var6.func_178974_a(0, 200);
      var6.addVertex(var8 - var7 * 1.1F, var9 - var7 * 0.6F - 16.0F, 0.0D);
      var6.addVertex(var8 - var7 * 1.1F, var9 + var7 * 2, 0.0D);
      var6.addVertex(var8 + var7 * 1.1F, var9 + var7 * 2, 0.0D);
      var6.addVertex(var8 + var7 * 1.1F, var9 - var7 * 0.6F - 16.0F, 0.0D);
      var5.draw();
      GlStateManager.disableBlend();
      double var10 = 0.0D;
      

      for (int var12 = 0; var12 < var3.size(); var12++)
      {
        Profiler.Result var13 = (Profiler.Result)var3.get(var12);
        int var14 = MathHelper.floor_double(field_76332_a / 4.0D) + 1;
        var6.startDrawing(6);
        var6.func_178991_c(var13.func_76329_a());
        var6.addVertex(var8, var9, 0.0D);
        




        for (int var15 = var14; var15 >= 0; var15--)
        {
          float var16 = (float)((var10 + field_76332_a * var15 / var14) * 3.141592653589793D * 2.0D / 100.0D);
          float var17 = MathHelper.sin(var16) * var7;
          float var18 = MathHelper.cos(var16) * var7 * 0.5F;
          var6.addVertex(var8 + var17, var9 - var18, 0.0D);
        }
        
        var5.draw();
        var6.startDrawing(5);
        var6.func_178991_c((var13.func_76329_a() & 0xFEFEFE) >> 1);
        
        for (var15 = var14; var15 >= 0; var15--)
        {
          float var16 = (float)((var10 + field_76332_a * var15 / var14) * 3.141592653589793D * 2.0D / 100.0D);
          float var17 = MathHelper.sin(var16) * var7;
          float var18 = MathHelper.cos(var16) * var7 * 0.5F;
          var6.addVertex(var8 + var17, var9 - var18, 0.0D);
          var6.addVertex(var8 + var17, var9 - var18 + 10.0F, 0.0D);
        }
        
        var5.draw();
        var10 += field_76332_a;
      }
      
      DecimalFormat var19 = new DecimalFormat("##0.00");
      GlStateManager.func_179098_w();
      String var20 = "";
      
      if (!field_76331_c.equals("unspecified"))
      {
        var20 = var20 + "[0] ";
      }
      
      if (field_76331_c.length() == 0)
      {
        var20 = var20 + "ROOT ";
      }
      else
      {
        var20 = var20 + field_76331_c + " ";
      }
      
      int var14 = 16777215;
      fontRendererObj.drawStringWithShadow(var20, var8 - var7, var9 - var7 / 2 - 16, var14);
      fontRendererObj.drawStringWithShadow(var20 = var19.format(field_76330_b) + "%", var8 + var7 - fontRendererObj.getStringWidth(var20), var9 - var7 / 2 - 16, var14);
      
      for (int var21 = 0; var21 < var3.size(); var21++)
      {
        Profiler.Result var22 = (Profiler.Result)var3.get(var21);
        String var23 = "";
        
        if (field_76331_c.equals("unspecified"))
        {
          var23 = var23 + "[?] ";
        }
        else
        {
          var23 = var23 + "[" + (var21 + 1) + "] ";
        }
        
        var23 = var23 + field_76331_c;
        fontRendererObj.drawStringWithShadow(var23, var8 - var7, var9 + var7 / 2 + var21 * 8 + 20, var22.func_76329_a());
        fontRendererObj.drawStringWithShadow(var23 = var19.format(field_76332_a) + "%", var8 + var7 - 50 - fontRendererObj.getStringWidth(var23), var9 + var7 / 2 + var21 * 8 + 20, var22.func_76329_a());
        fontRendererObj.drawStringWithShadow(var23 = var19.format(field_76330_b) + "%", var8 + var7 - fontRendererObj.getStringWidth(var23), var9 + var7 / 2 + var21 * 8 + 20, var22.func_76329_a());
      }
    }
  }
  



  public void shutdown()
  {
    running = false;
  }
  




  public void setIngameFocus()
  {
    if (Display.isActive())
    {
      if (!inGameHasFocus)
      {
        inGameHasFocus = true;
        mouseHelper.grabMouseCursor();
        displayGuiScreen(null);
        leftClickCounter = 10000;
      }
    }
  }
  



  public void setIngameNotInFocus()
  {
    if (inGameHasFocus)
    {
      KeyBinding.unPressAllKeys();
      inGameHasFocus = false;
      mouseHelper.ungrabMouseCursor();
    }
  }
  



  public void displayInGameMenu()
  {
    if (currentScreen == null)
    {
      displayGuiScreen(new net.minecraft.client.gui.GuiIngameMenu());
      
      if ((isSingleplayer()) && (!theIntegratedServer.getPublic()))
      {
        mcSoundHandler.pauseSounds();
      }
    }
  }
  
  private void sendClickBlockToController(boolean leftClick)
  {
    if (!leftClick)
    {
      leftClickCounter = 0;
    }
    
    if ((leftClickCounter <= 0) && (!thePlayer.isUsingItem()))
    {
      if ((leftClick) && (objectMouseOver != null) && (objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK))
      {
        BlockPos var2 = objectMouseOver.func_178782_a();
        
        if ((theWorld.getBlockState(var2).getBlock().getMaterial() != Material.air) && (playerController.func_180512_c(var2, objectMouseOver.field_178784_b)))
        {
          effectRenderer.func_180532_a(var2, objectMouseOver.field_178784_b);
          thePlayer.swingItem();
        }
      }
      else
      {
        playerController.resetBlockRemoving();
      }
    }
  }
  
  private void clickMouse()
  {
    if (leftClickCounter <= 0)
    {
      thePlayer.swingItem();
      
      if (objectMouseOver == null)
      {
        logger.error("Null returned as 'hitResult', this shouldn't happen!");
        
        if (playerController.isNotCreative())
        {
          leftClickCounter = 10;
        }
      }
      else
      {
        switch (SwitchEnumMinecartType.field_152390_a[objectMouseOver.typeOfHit.ordinal()])
        {
        case 1: 
          playerController.attackEntity(thePlayer, objectMouseOver.entityHit);
          break;
        
        case 2: 
          BlockPos var1 = objectMouseOver.func_178782_a();
          
          if (theWorld.getBlockState(var1).getBlock().getMaterial() != Material.air)
          {
            playerController.func_180511_b(var1, objectMouseOver.field_178784_b); }
          break;
        }
        
        

        if (playerController.isNotCreative())
        {
          leftClickCounter = 10;
        }
      }
    }
  }
  




  private void rightClickMouse()
  {
    rightClickDelayTimer = 4;
    boolean var1 = true;
    ItemStack var2 = thePlayer.inventory.getCurrentItem();
    
    if (objectMouseOver == null)
    {
      logger.warn("Null returned as 'hitResult', this shouldn't happen!");
    }
    else
    {
      switch (SwitchEnumMinecartType.field_152390_a[objectMouseOver.typeOfHit.ordinal()])
      {
      case 1: 
        if (playerController.func_178894_a(thePlayer, objectMouseOver.entityHit, objectMouseOver))
        {
          var1 = false;
        }
        else if (playerController.interactWithEntitySendPacket(thePlayer, objectMouseOver.entityHit))
        {
          var1 = false;
        }
        
        break;
      
      case 2: 
        BlockPos var3 = objectMouseOver.func_178782_a();
        
        if (theWorld.getBlockState(var3).getBlock().getMaterial() != Material.air)
        {
          int var4 = var2 != null ? stackSize : 0;
          
          if (playerController.func_178890_a(thePlayer, theWorld, var2, var3, objectMouseOver.field_178784_b, objectMouseOver.hitVec))
          {
            var1 = false;
            thePlayer.swingItem();
          }
          
          if (var2 == null)
          {
            return;
          }
          
          if (stackSize == 0)
          {
            thePlayer.inventory.mainInventory[thePlayer.inventory.currentItem] = null;
          }
          else if ((stackSize != var4) || (playerController.isInCreativeMode()))
          {
            entityRenderer.itemRenderer.resetEquippedProgress();
          }
        }
        break;
      }
    }
    if (var1)
    {
      ItemStack var5 = thePlayer.inventory.getCurrentItem();
      
      if ((var5 != null) && (playerController.sendUseItem(thePlayer, theWorld, var5)))
      {
        entityRenderer.itemRenderer.resetEquippedProgress2();
      }
    }
  }
  



  public void toggleFullscreen()
  {
    try
    {
      fullscreen = (!fullscreen);
      gameSettings.fullScreen = fullscreen;
      
      if (fullscreen)
      {
        updateDisplayMode();
        displayWidth = Display.getDisplayMode().getWidth();
        displayHeight = Display.getDisplayMode().getHeight();
        
        if (displayWidth <= 0)
        {
          displayWidth = 1;
        }
        
        if (displayHeight <= 0)
        {
          displayHeight = 1;
        }
      }
      else
      {
        Display.setDisplayMode(new DisplayMode(tempDisplayWidth, tempDisplayHeight));
        displayWidth = tempDisplayWidth;
        displayHeight = tempDisplayHeight;
        
        if (displayWidth <= 0)
        {
          displayWidth = 1;
        }
        
        if (displayHeight <= 0)
        {
          displayHeight = 1;
        }
      }
      
      if (currentScreen != null)
      {
        resize(displayWidth, displayHeight);
      }
      else
      {
        updateFramebufferSize();
      }
      
      Display.setFullscreen(fullscreen);
      Display.setVSyncEnabled(gameSettings.enableVsync);
      func_175601_h();
    }
    catch (Exception var2)
    {
      logger.error("Couldn't toggle fullscreen", var2);
    }
  }
  



  private void resize(int width, int height)
  {
    displayWidth = Math.max(1, width);
    displayHeight = Math.max(1, height);
    
    if (currentScreen != null)
    {
      ScaledResolution var3 = new ScaledResolution(this);
      currentScreen.func_175273_b(this, var3.getScaledWidth(), var3.getScaledHeight());
    }
    
    loadingScreen = new LoadingScreenRenderer(this);
    updateFramebufferSize();
  }
  
  private void updateFramebufferSize()
  {
    framebufferMc.createBindFramebuffer(displayWidth, displayHeight);
    
    if (entityRenderer != null)
    {
      entityRenderer.updateShaderGroupSize(displayWidth, displayHeight);
    }
  }
  


  public void runTick()
    throws IOException
  {
    if (rightClickDelayTimer > 0)
    {
      rightClickDelayTimer -= 1;
    }
    
    mcProfiler.startSection("gui");
    
    if (!isGamePaused)
    {
      ingameGUI.updateTick();
    }
    
    mcProfiler.endSection();
    entityRenderer.getMouseOver(1.0F);
    mcProfiler.startSection("gameMode");
    
    if ((!isGamePaused) && (theWorld != null))
    {
      playerController.updateController();
    }
    
    mcProfiler.endStartSection("textures");
    
    if (!isGamePaused)
    {
      renderEngine.tick();
    }
    
    if ((currentScreen == null) && (thePlayer != null))
    {
      if (thePlayer.getHealth() <= 0.0F)
      {
        displayGuiScreen(null);
      }
      else if ((thePlayer.isPlayerSleeping()) && (theWorld != null))
      {
        displayGuiScreen(new GuiSleepMP());
      }
    }
    else if ((currentScreen != null) && ((currentScreen instanceof GuiSleepMP)) && (!thePlayer.isPlayerSleeping()))
    {
      displayGuiScreen(null);
    }
    
    if (currentScreen != null)
    {
      leftClickCounter = 10000;
    }
    



    if (currentScreen != null)
    {
      try
      {
        currentScreen.handleInput();
      }
      catch (Throwable var7)
      {
        CrashReport var2 = CrashReport.makeCrashReport(var7, "Updating screen events");
        CrashReportCategory var3 = var2.makeCategory("Affected screen");
        var3.addCrashSectionCallable("Screen name", new Callable()
        {
          private static final String __OBFID = "CL_00000640";
          
          public String call() {
            return currentScreen.getClass().getCanonicalName();
          }
          
          public Object call1() {
            return call();
          }
        });
        throw new ReportedException(var2);
      }
      
      if (currentScreen != null)
      {
        try
        {
          currentScreen.updateScreen();
        }
        catch (Throwable var6)
        {
          CrashReport var2 = CrashReport.makeCrashReport(var6, "Ticking screen");
          CrashReportCategory var3 = var2.makeCategory("Affected screen");
          var3.addCrashSectionCallable("Screen name", new Callable()
          {
            private static final String __OBFID = "CL_00000642";
            
            public String call() {
              return currentScreen.getClass().getCanonicalName();
            }
            
            public Object call1() {
              return call();
            }
          });
          throw new ReportedException(var2);
        }
      }
    }
    
    if ((currentScreen == null) || (currentScreen.allowUserInput))
    {
      mcProfiler.endStartSection("mouse");
      

      while (Mouse.next())
      {
        int var1 = Mouse.getEventButton();
        KeyBinding.setKeyBindState(var1 - 100, Mouse.getEventButtonState());
        
        if (Mouse.getEventButtonState())
        {
          if ((thePlayer.func_175149_v()) && (var1 == 2))
          {
            ingameGUI.func_175187_g().func_175261_b();
          }
          else
          {
            KeyBinding.onTick(var1 - 100);
          }
        }
        
        long var10 = getSystemTime() - systemTime;
        
        if (var10 <= 200L)
        {
          int var4 = Mouse.getEventDWheel();
          
          if (var4 != 0)
          {
            if (thePlayer.func_175149_v())
            {
              var4 = var4 < 0 ? -1 : 1;
              
              if (ingameGUI.func_175187_g().func_175262_a())
              {
                ingameGUI.func_175187_g().func_175259_b(-var4);
              }
              else
              {
                float var5 = MathHelper.clamp_float(thePlayer.capabilities.getFlySpeed() + var4 * 0.005F, 0.0F, 0.2F);
                thePlayer.capabilities.setFlySpeed(var5);
              }
            }
            else
            {
              thePlayer.inventory.changeCurrentItem(var4);
            }
          }
          
          if (currentScreen == null)
          {
            if ((!inGameHasFocus) && (Mouse.getEventButtonState()))
            {
              setIngameFocus();
            }
          }
          else if (currentScreen != null)
          {
            currentScreen.handleMouseInput();
          }
        }
      }
      
      if (leftClickCounter > 0)
      {
        leftClickCounter -= 1;
      }
      
      mcProfiler.endStartSection("keyboard");
      
      while (Keyboard.next())
      {
        int var1 = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 'Ā' : Keyboard.getEventKey();
        KeyBinding.setKeyBindState(var1, Keyboard.getEventKeyState());
        
        if (Keyboard.getEventKeyState())
        {
          KeyBinding.onTick(var1);
        }
        
        if (debugCrashKeyPressTime > 0L)
        {
          if (getSystemTime() - debugCrashKeyPressTime >= 6000L)
          {
            throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
          }
          
          if ((!Keyboard.isKeyDown(46)) || (!Keyboard.isKeyDown(61)))
          {
            debugCrashKeyPressTime = -1L;
          }
        }
        else if ((Keyboard.isKeyDown(46)) && (Keyboard.isKeyDown(61)))
        {
          debugCrashKeyPressTime = getSystemTime();
        }
        
        dispatchKeypresses();
        
        if (Keyboard.getEventKeyState())
        {
          if ((var1 == 62) && (entityRenderer != null))
          {
            entityRenderer.func_175071_c();
          }
          
          if (currentScreen != null)
          {
            currentScreen.handleKeyboardInput();

          }
          else
          {
            EventBus.getDefault().call(new EventKey(var1));
            
            if (var1 == 1)
            {
              displayInGameMenu();
            }
            
            if ((var1 == 32) && (Keyboard.isKeyDown(61)) && (ingameGUI != null))
            {
              ingameGUI.getChatGUI().clearChatMessages();
            }
            
            if ((var1 == 31) && (Keyboard.isKeyDown(61)))
            {
              refreshResources();
            }
            
            if (((var1 != 17) || (!Keyboard.isKeyDown(61))) || (
            



              ((var1 != 18) || (!Keyboard.isKeyDown(61))) || (
              



              ((var1 != 47) || (!Keyboard.isKeyDown(61))) || (
              



              ((var1 != 38) || (!Keyboard.isKeyDown(61))) || (
              



              ((var1 != 22) || (!Keyboard.isKeyDown(61))) || (
              



              (var1 == 20) && (Keyboard.isKeyDown(61))))))))
            {
              refreshResources();
            }
            
            if ((var1 == 33) && (Keyboard.isKeyDown(61)))
            {
              boolean var11 = Keyboard.isKeyDown(42) | Keyboard.isKeyDown(54);
              gameSettings.setOptionValue(GameSettings.Options.RENDER_DISTANCE, var11 ? -1 : 1);
            }
            
            if ((var1 == 30) && (Keyboard.isKeyDown(61)))
            {
              renderGlobal.loadRenderers();
            }
            
            if ((var1 == 35) && (Keyboard.isKeyDown(61)))
            {
              gameSettings.advancedItemTooltips = (!gameSettings.advancedItemTooltips);
              gameSettings.saveOptions();
            }
            
            if ((var1 == 48) && (Keyboard.isKeyDown(61)))
            {
              renderManager.func_178629_b(!renderManager.func_178634_b());
            }
            
            if ((var1 == 25) && (Keyboard.isKeyDown(61)))
            {
              gameSettings.pauseOnLostFocus = (!gameSettings.pauseOnLostFocus);
              gameSettings.saveOptions();
            }
            
            if (var1 == 59)
            {
              gameSettings.hideGUI = (!gameSettings.hideGUI);
            }
            
            if (var1 == 61)
            {
              gameSettings.showDebugInfo = (!gameSettings.showDebugInfo);
              gameSettings.showDebugProfilerChart = GuiScreen.isShiftKeyDown();
            }
            
            if (gameSettings.keyBindTogglePerspective.isPressed())
            {
              gameSettings.thirdPersonView += 1;
              
              if (gameSettings.thirdPersonView > 2)
              {
                gameSettings.thirdPersonView = 0;
              }
              
              if (gameSettings.thirdPersonView == 0)
              {
                entityRenderer.func_175066_a(func_175606_aa());
              }
              else if (gameSettings.thirdPersonView == 1)
              {
                entityRenderer.func_175066_a(null);
              }
            }
            
            if (gameSettings.keyBindSmoothCamera.isPressed())
            {
              gameSettings.smoothCamera = (!gameSettings.smoothCamera);
            }
          }
          
          if ((gameSettings.showDebugInfo) && (gameSettings.showDebugProfilerChart))
          {
            if (var1 == 11)
            {
              updateDebugProfilerName(0);
            }
            
            for (int var12 = 0; var12 < 9; var12++)
            {
              if (var1 == 2 + var12)
              {
                updateDebugProfilerName(var12 + 1);
              }
            }
          }
        }
      }
      
      for (int var1 = 0; var1 < 9; var1++)
      {
        if (gameSettings.keyBindsHotbar[var1].isPressed())
        {
          if (thePlayer.func_175149_v())
          {
            ingameGUI.func_175187_g().func_175260_a(var1);
          }
          else
          {
            thePlayer.inventory.currentItem = var1;
          }
        }
      }
      
      boolean var9 = gameSettings.chatVisibility != net.minecraft.entity.player.EntityPlayer.EnumChatVisibility.HIDDEN;
      
      while (gameSettings.keyBindInventory.isPressed())
      {
        if (playerController.isRidingHorse())
        {
          thePlayer.func_175163_u();
        }
        else
        {
          getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
          displayGuiScreen(new GuiInventory(thePlayer));
        }
      }
      
      while (gameSettings.keyBindDrop.isPressed())
      {
        if (!thePlayer.func_175149_v())
        {
          thePlayer.dropOneItem(GuiScreen.isCtrlKeyDown());
        }
      }
      
      while ((gameSettings.keyBindChat.isPressed()) && (var9))
      {
        displayGuiScreen(new GuiChat());
      }
      
      if ((currentScreen == null) && (gameSettings.keyBindCommand.isPressed()) && (var9))
      {
        displayGuiScreen(new GuiChat("/"));
      }
      
      if (thePlayer.isUsingItem())
      {
        if (!gameSettings.keyBindUseItem.getIsKeyPressed())
        {
          playerController.onStoppedUsingItem(thePlayer);
        }
        




        while (gameSettings.keyBindAttack.isPressed()) {}
        
        while (gameSettings.keyBindUseItem.isPressed()) {}
        





        while (gameSettings.keyBindPickBlock.isPressed()) {}
      }
      









      while (gameSettings.keyBindAttack.isPressed())
      {
        clickMouse();
      }
      
      while (gameSettings.keyBindUseItem.isPressed())
      {
        rightClickMouse();
      }
      
      while (gameSettings.keyBindPickBlock.isPressed())
      {
        middleClickMouse();
      }
      

      if ((gameSettings.keyBindUseItem.getIsKeyPressed()) && (rightClickDelayTimer == 0) && (!thePlayer.isUsingItem()))
      {
        rightClickMouse();
      }
      
      sendClickBlockToController((currentScreen == null) && (gameSettings.keyBindAttack.getIsKeyPressed()) && (inGameHasFocus));
    }
    
    if (theWorld != null)
    {
      if (thePlayer != null)
      {
        joinPlayerCounter += 1;
        
        if (joinPlayerCounter == 30)
        {
          joinPlayerCounter = 0;
          theWorld.joinEntityInSurroundings(thePlayer);
        }
      }
      
      mcProfiler.endStartSection("gameRenderer");
      
      if (!isGamePaused)
      {
        entityRenderer.updateRenderer();
      }
      
      mcProfiler.endStartSection("levelRenderer");
      
      if (!isGamePaused)
      {
        renderGlobal.updateClouds();
      }
      
      mcProfiler.endStartSection("level");
      
      if (!isGamePaused)
      {
        if (theWorld.func_175658_ac() > 0)
        {
          theWorld.setLastLightningBolt(theWorld.func_175658_ac() - 1);
        }
        
        theWorld.updateEntities();
      }
    }
    
    if (!isGamePaused)
    {
      mcMusicTicker.update();
      mcSoundHandler.update();
    }
    
    if (theWorld != null)
    {
      if (!isGamePaused)
      {
        theWorld.setAllowedSpawnTypes(theWorld.getDifficulty() != EnumDifficulty.PEACEFUL, true);
        
        try
        {
          theWorld.tick();
        }
        catch (Throwable var8)
        {
          CrashReport var2 = CrashReport.makeCrashReport(var8, "Exception in world tick");
          
          if (theWorld == null)
          {
            CrashReportCategory var3 = var2.makeCategory("Affected level");
            var3.addCrashSection("Problem", "Level is null!");
          }
          else
          {
            theWorld.addWorldInfoToCrashReport(var2);
          }
          
          throw new ReportedException(var2);
        }
      }
      
      mcProfiler.endStartSection("animateTick");
      
      if ((!isGamePaused) && (theWorld != null))
      {
        theWorld.doVoidFogParticles(MathHelper.floor_double(thePlayer.posX), MathHelper.floor_double(thePlayer.posY), MathHelper.floor_double(thePlayer.posZ));
      }
      
      mcProfiler.endStartSection("particles");
      
      if (!isGamePaused)
      {
        effectRenderer.updateEffects();
      }
    }
    else if (myNetworkManager != null)
    {
      mcProfiler.endStartSection("pendingConnection");
      myNetworkManager.processReceivedPackets();
    }
    
    mcProfiler.endSection();
    systemTime = getSystemTime();
  }
  



  public void launchIntegratedServer(String folderName, String worldName, WorldSettings worldSettingsIn)
  {
    loadWorld(null);
    System.gc();
    ISaveHandler var4 = saveLoader.getSaveLoader(folderName, false);
    WorldInfo var5 = var4.loadWorldInfo();
    
    if ((var5 == null) && (worldSettingsIn != null))
    {
      var5 = new WorldInfo(worldSettingsIn, folderName);
      var4.saveWorldInfo(var5);
    }
    
    if (worldSettingsIn == null)
    {
      worldSettingsIn = new WorldSettings(var5);
    }
    
    try
    {
      theIntegratedServer = new IntegratedServer(this, folderName, worldName, worldSettingsIn);
      theIntegratedServer.startServerThread();
      integratedServerIsRunning = true;
    }
    catch (Throwable var10)
    {
      CrashReport var7 = CrashReport.makeCrashReport(var10, "Starting integrated server");
      CrashReportCategory var8 = var7.makeCategory("Starting integrated server");
      var8.addCrashSection("Level ID", folderName);
      var8.addCrashSection("Level Name", worldName);
      throw new ReportedException(var7);
    }
    
    loadingScreen.displaySavingString(I18n.format("menu.loadingLevel", new Object[0]));
    
    while (!theIntegratedServer.serverIsInRunLoop())
    {
      String var6 = theIntegratedServer.getUserMessage();
      
      if (var6 != null)
      {
        loadingScreen.displayLoadingString(I18n.format(var6, new Object[0]));
      }
      else
      {
        loadingScreen.displayLoadingString("");
      }
      
      try
      {
        Thread.sleep(200L);
      }
      catch (InterruptedException localInterruptedException) {}
    }
    



    displayGuiScreen(null);
    java.net.SocketAddress var11 = theIntegratedServer.getNetworkSystem().addLocalEndpoint();
    NetworkManager var12 = NetworkManager.provideLocalClient(var11);
    var12.setNetHandler(new NetHandlerLoginClient(var12, this, null));
    var12.sendPacket(new C00Handshake(47, var11.toString(), 0, net.minecraft.network.EnumConnectionState.LOGIN));
    var12.sendPacket(new C00PacketLoginStart(getSession().getProfile()));
    myNetworkManager = var12;
  }
  



  public void loadWorld(WorldClient worldClientIn)
  {
    loadWorld(worldClientIn, "");
  }
  



  public void loadWorld(WorldClient worldClientIn, String loadingMessage)
  {
    if (worldClientIn == null)
    {
      NetHandlerPlayClient var3 = getNetHandler();
      
      if (var3 != null)
      {
        var3.cleanup();
      }
      
      if ((theIntegratedServer != null) && (theIntegratedServer.func_175578_N()))
      {
        theIntegratedServer.initiateShutdown();
        theIntegratedServer.func_175592_a();
      }
      
      theIntegratedServer = null;
      guiAchievement.clearAchievements();
      entityRenderer.getMapItemRenderer().func_148249_a();
    }
    
    field_175622_Z = null;
    myNetworkManager = null;
    
    if (loadingScreen != null)
    {
      loadingScreen.resetProgressAndMessage(loadingMessage);
      loadingScreen.displayLoadingString("");
    }
    
    if ((worldClientIn == null) && (theWorld != null))
    {
      if (mcResourcePackRepository.getResourcePackInstance() != null)
      {
        mcResourcePackRepository.func_148529_f();
        func_175603_A();
      }
      
      setServerData(null);
      integratedServerIsRunning = false;
    }
    
    mcSoundHandler.stopSounds();
    theWorld = worldClientIn;
    
    if (worldClientIn != null)
    {
      if (renderGlobal != null)
      {
        renderGlobal.setWorldAndLoadRenderers(worldClientIn);
      }
      
      if (effectRenderer != null)
      {
        effectRenderer.clearEffects(worldClientIn);
      }
      
      if (thePlayer == null)
      {
        thePlayer = playerController.func_178892_a(worldClientIn, new StatFileWriter());
        playerController.flipPlayer(thePlayer);
      }
      
      thePlayer.preparePlayerToSpawn();
      worldClientIn.spawnEntityInWorld(thePlayer);
      thePlayer.movementInput = new MovementInputFromOptions(gameSettings);
      playerController.setPlayerCapabilities(thePlayer);
      field_175622_Z = thePlayer;
    }
    else
    {
      saveLoader.flushCache();
      thePlayer = null;
    }
    
    System.gc();
    systemTime = 0L;
  }
  
  public void setDimensionAndSpawnPlayer(int dimension)
  {
    theWorld.setInitialSpawnLocation();
    theWorld.removeAllEntities();
    int var2 = 0;
    String var3 = null;
    
    if (thePlayer != null)
    {
      var2 = thePlayer.getEntityId();
      theWorld.removeEntity(thePlayer);
      var3 = thePlayer.getClientBrand();
    }
    
    field_175622_Z = null;
    EntityPlayerSP var4 = thePlayer;
    thePlayer = playerController.func_178892_a(theWorld, thePlayer == null ? new StatFileWriter() : thePlayer.getStatFileWriter());
    thePlayer.getDataWatcher().updateWatchedObjectsFromList(var4.getDataWatcher().getAllWatched());
    thePlayer.dimension = dimension;
    field_175622_Z = thePlayer;
    thePlayer.preparePlayerToSpawn();
    thePlayer.func_175158_f(var3);
    theWorld.spawnEntityInWorld(thePlayer);
    playerController.flipPlayer(thePlayer);
    thePlayer.movementInput = new MovementInputFromOptions(gameSettings);
    thePlayer.setEntityId(var2);
    playerController.setPlayerCapabilities(thePlayer);
    thePlayer.func_175150_k(var4.func_175140_cp());
    
    if ((currentScreen instanceof GuiGameOver))
    {
      displayGuiScreen(null);
    }
  }
  



  public final boolean isDemo()
  {
    return isDemo;
  }
  
  public NetHandlerPlayClient getNetHandler()
  {
    return thePlayer != null ? thePlayer.sendQueue : null;
  }
  
  public static boolean isGuiEnabled()
  {
    return (theMinecraft == null) || (!theMinecraftgameSettings.hideGUI);
  }
  
  public static boolean isFancyGraphicsEnabled()
  {
    return (theMinecraft != null) && (theMinecraftgameSettings.fancyGraphics);
  }
  



  public static boolean isAmbientOcclusionEnabled()
  {
    return (theMinecraft != null) && (theMinecraftgameSettings.ambientOcclusion != 0);
  }
  



  private void middleClickMouse()
  {
    if (objectMouseOver != null)
    {
      boolean var1 = thePlayer.capabilities.isCreativeMode;
      int var3 = 0;
      boolean var4 = false;
      TileEntity var5 = null;
      
      Object var2;
      if (objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
      {
        BlockPos var6 = objectMouseOver.func_178782_a();
        Block var7 = theWorld.getBlockState(var6).getBlock();
        
        if (var7.getMaterial() == Material.air)
        {
          return;
        }
        
        Object var2 = var7.getItem(theWorld, var6);
        
        if (var2 == null)
        {
          return;
        }
        
        if ((var1) && (GuiScreen.isCtrlKeyDown()))
        {
          var5 = theWorld.getTileEntity(var6);
        }
        
        Block var8 = ((var2 instanceof net.minecraft.item.ItemBlock)) && (!var7.isFlowerPot()) ? Block.getBlockFromItem((Item)var2) : var7;
        var3 = var8.getDamageValue(theWorld, var6);
        var4 = ((Item)var2).getHasSubtypes();
      }
      else
      {
        if ((objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY) || (objectMouseOver.entityHit == null) || (!var1)) {
          return;
        }
        
        Object var2;
        if ((objectMouseOver.entityHit instanceof EntityPainting))
        {
          var2 = Items.painting;
        } else { Object var2;
          if ((objectMouseOver.entityHit instanceof net.minecraft.entity.EntityLeashKnot))
          {
            var2 = Items.lead;
          }
          else if ((objectMouseOver.entityHit instanceof EntityItemFrame))
          {
            EntityItemFrame var11 = (EntityItemFrame)objectMouseOver.entityHit;
            ItemStack var14 = var11.getDisplayedItem();
            Object var2;
            if (var14 == null)
            {
              var2 = Items.item_frame;
            }
            else
            {
              Object var2 = var14.getItem();
              var3 = var14.getMetadata();
              var4 = true;
            }
          }
          else if ((objectMouseOver.entityHit instanceof EntityMinecart))
          {
            EntityMinecart var12 = (EntityMinecart)objectMouseOver.entityHit;
            Object var2;
            Object var2; Object var2; Object var2; Object var2; Object var2; switch (SwitchEnumMinecartType.field_178901_b[var12.func_180456_s().ordinal()])
            {
            case 1: 
              var2 = Items.furnace_minecart;
              break;
            
            case 2: 
              var2 = Items.chest_minecart;
              break;
            
            case 3: 
              var2 = Items.tnt_minecart;
              break;
            
            case 4: 
              var2 = Items.hopper_minecart;
              break;
            
            case 5: 
              var2 = Items.command_block_minecart;
              break;
            
            default: 
              var2 = Items.minecart;
              
              break; } } else { Object var2;
            if ((objectMouseOver.entityHit instanceof net.minecraft.entity.item.EntityBoat))
            {
              var2 = Items.boat;
            } else { Object var2;
              if ((objectMouseOver.entityHit instanceof EntityArmorStand))
              {
                var2 = Items.armor_stand;
              }
              else
              {
                var2 = Items.spawn_egg;
                var3 = EntityList.getEntityID(objectMouseOver.entityHit);
                var4 = true;
                
                if (!EntityList.entityEggs.containsKey(Integer.valueOf(var3)))
                {
                  return; }
              }
            }
          }
        } }
      InventoryPlayer var13 = thePlayer.inventory;
      
      if (var5 == null)
      {
        var13.setCurrentItem((Item)var2, var3, var4, var1);
      }
      else
      {
        NBTTagCompound var15 = new NBTTagCompound();
        var5.writeToNBT(var15);
        ItemStack var17 = new ItemStack((Item)var2, 1, var3);
        var17.setTagInfo("BlockEntityTag", var15);
        NBTTagCompound var9 = new NBTTagCompound();
        NBTTagList var10 = new NBTTagList();
        var10.appendTag(new NBTTagString("(+NBT)"));
        var9.setTag("Lore", var10);
        var17.setTagInfo("display", var9);
        var13.setInventorySlotContents(currentItem, var17);
      }
      
      if (var1)
      {
        int var16 = thePlayer.inventoryContainer.inventorySlots.size() - 9 + currentItem;
        playerController.sendSlotPacket(var13.getStackInSlot(currentItem), var16);
      }
    }
  }
  



  public CrashReport addGraphicsAndWorldToCrashReport(CrashReport theCrash)
  {
    theCrash.getCategory().addCrashSectionCallable("Launched Version", new Callable()
    {
      private static final String __OBFID = "CL_00000643";
      
      public String call() {
        return launchedVersion;
      }
      
      public Object call1() {
        return call();
      }
    });
    theCrash.getCategory().addCrashSectionCallable("LWJGL", new Callable()
    {
      private static final String __OBFID = "CL_00000644";
      
      public String call() {
        return Sys.getVersion();
      }
      
      public Object call1() {
        return call();
      }
    });
    theCrash.getCategory().addCrashSectionCallable("OpenGL", new Callable()
    {
      private static final String __OBFID = "CL_00000645";
      
      public String call() {
        return GL11.glGetString(7937) + " GL version " + GL11.glGetString(7938) + ", " + GL11.glGetString(7936);
      }
      
      public Object call1() {
        return call();
      }
    });
    theCrash.getCategory().addCrashSectionCallable("GL Caps", new Callable()
    {
      private static final String __OBFID = "CL_00000646";
      
      public String call() {
        return OpenGlHelper.func_153172_c();
      }
      
      public Object call1() {
        return call();
      }
    });
    theCrash.getCategory().addCrashSectionCallable("Using VBOs", new Callable()
    {
      private static final String __OBFID = "CL_00000647";
      
      public String call() {
        return gameSettings.field_178881_t ? "Yes" : "No";
      }
      
      public Object call1() {
        return call();
      }
    });
    theCrash.getCategory().addCrashSectionCallable("Is Modded", new Callable()
    {
      private static final String __OBFID = "CL_00000633";
      
      public String call() {
        String var1 = ClientBrandRetriever.getClientModName();
        return Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : !var1.equals("vanilla") ? "Definitely; Client brand changed to '" + var1 + "'" : "Probably not. Jar signature remains and client brand is untouched.";
      }
      
      public Object call1() {
        return call();
      }
    });
    theCrash.getCategory().addCrashSectionCallable("Type", new Callable()
    {
      private static final String __OBFID = "CL_00000634";
      
      public String call() {
        return "Client (map_client.txt)";
      }
      
      public Object call1() {
        return call();
      }
    });
    theCrash.getCategory().addCrashSectionCallable("Resource Packs", new Callable()
    {
      private static final String __OBFID = "CL_00000635";
      
      public String call() {
        return gameSettings.resourcePacks.toString();
      }
      
      public Object call1() {
        return call();
      }
    });
    theCrash.getCategory().addCrashSectionCallable("Current Language", new Callable()
    {
      private static final String __OBFID = "CL_00000636";
      
      public String call() {
        return mcLanguageManager.getCurrentLanguage().toString();
      }
      
      public Object call1() {
        return call();
      }
    });
    theCrash.getCategory().addCrashSectionCallable("Profiler Position", new Callable()
    {
      private static final String __OBFID = "CL_00000637";
      
      public String call1() {
        return mcProfiler.profilingEnabled ? mcProfiler.getNameOfLastSection() : "N/A (disabled)";
      }
      
      public Object call() {
        return call1();
      }
    });
    
    if (theWorld != null)
    {
      theWorld.addWorldInfoToCrashReport(theCrash);
    }
    
    return theCrash;
  }
  



  public static Minecraft getMinecraft()
  {
    return theMinecraft;
  }
  
  public ListenableFuture func_175603_A()
  {
    addScheduledTask(new Runnable()
    {
      private static final String __OBFID = "CL_00001853";
      
      public void run() {
        refreshResources();
      }
    });
  }
  
  public void addServerStatsToSnooper(PlayerUsageSnooper playerSnooper)
  {
    playerSnooper.addClientStat("fps", Integer.valueOf(debugFPS));
    playerSnooper.addClientStat("vsync_enabled", Boolean.valueOf(gameSettings.enableVsync));
    playerSnooper.addClientStat("display_frequency", Integer.valueOf(Display.getDisplayMode().getFrequency()));
    playerSnooper.addClientStat("display_type", fullscreen ? "fullscreen" : "windowed");
    playerSnooper.addClientStat("run_time", Long.valueOf((MinecraftServer.getCurrentTimeMillis() - playerSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L));
    String var2 = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? "little" : "big";
    playerSnooper.addClientStat("endianness", var2);
    playerSnooper.addClientStat("resource_packs", Integer.valueOf(mcResourcePackRepository.getRepositoryEntries().size()));
    int var3 = 0;
    Iterator var4 = mcResourcePackRepository.getRepositoryEntries().iterator();
    
    while (var4.hasNext())
    {
      ResourcePackRepository.Entry var5 = (ResourcePackRepository.Entry)var4.next();
      playerSnooper.addClientStat("resource_pack[" + var3++ + "]", var5.getResourcePackName());
    }
    
    if ((theIntegratedServer != null) && (theIntegratedServer.getPlayerUsageSnooper() != null))
    {
      playerSnooper.addClientStat("snooper_partner", theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
    }
  }
  
  public void addServerTypeToSnooper(PlayerUsageSnooper playerSnooper)
  {
    playerSnooper.addStatToSnooper("opengl_version", GL11.glGetString(7938));
    playerSnooper.addStatToSnooper("opengl_vendor", GL11.glGetString(7936));
    playerSnooper.addStatToSnooper("client_brand", ClientBrandRetriever.getClientModName());
    playerSnooper.addStatToSnooper("launched_version", launchedVersion);
    ContextCapabilities var2 = GLContext.getCapabilities();
    playerSnooper.addStatToSnooper("gl_caps[ARB_arrays_of_arrays]", Boolean.valueOf(GL_ARB_arrays_of_arrays));
    playerSnooper.addStatToSnooper("gl_caps[ARB_base_instance]", Boolean.valueOf(GL_ARB_base_instance));
    playerSnooper.addStatToSnooper("gl_caps[ARB_blend_func_extended]", Boolean.valueOf(GL_ARB_blend_func_extended));
    playerSnooper.addStatToSnooper("gl_caps[ARB_clear_buffer_object]", Boolean.valueOf(GL_ARB_clear_buffer_object));
    playerSnooper.addStatToSnooper("gl_caps[ARB_color_buffer_float]", Boolean.valueOf(GL_ARB_color_buffer_float));
    playerSnooper.addStatToSnooper("gl_caps[ARB_compatibility]", Boolean.valueOf(GL_ARB_compatibility));
    playerSnooper.addStatToSnooper("gl_caps[ARB_compressed_texture_pixel_storage]", Boolean.valueOf(GL_ARB_compressed_texture_pixel_storage));
    playerSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", Boolean.valueOf(GL_ARB_compute_shader));
    playerSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", Boolean.valueOf(GL_ARB_copy_buffer));
    playerSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", Boolean.valueOf(GL_ARB_copy_image));
    playerSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", Boolean.valueOf(GL_ARB_depth_buffer_float));
    playerSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", Boolean.valueOf(GL_ARB_compute_shader));
    playerSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", Boolean.valueOf(GL_ARB_copy_buffer));
    playerSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", Boolean.valueOf(GL_ARB_copy_image));
    playerSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", Boolean.valueOf(GL_ARB_depth_buffer_float));
    playerSnooper.addStatToSnooper("gl_caps[ARB_depth_clamp]", Boolean.valueOf(GL_ARB_depth_clamp));
    playerSnooper.addStatToSnooper("gl_caps[ARB_depth_texture]", Boolean.valueOf(GL_ARB_depth_texture));
    playerSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers]", Boolean.valueOf(GL_ARB_draw_buffers));
    playerSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers_blend]", Boolean.valueOf(GL_ARB_draw_buffers_blend));
    playerSnooper.addStatToSnooper("gl_caps[ARB_draw_elements_base_vertex]", Boolean.valueOf(GL_ARB_draw_elements_base_vertex));
    playerSnooper.addStatToSnooper("gl_caps[ARB_draw_indirect]", Boolean.valueOf(GL_ARB_draw_indirect));
    playerSnooper.addStatToSnooper("gl_caps[ARB_draw_instanced]", Boolean.valueOf(GL_ARB_draw_instanced));
    playerSnooper.addStatToSnooper("gl_caps[ARB_explicit_attrib_location]", Boolean.valueOf(GL_ARB_explicit_attrib_location));
    playerSnooper.addStatToSnooper("gl_caps[ARB_explicit_uniform_location]", Boolean.valueOf(GL_ARB_explicit_uniform_location));
    playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_layer_viewport]", Boolean.valueOf(GL_ARB_fragment_layer_viewport));
    playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_program]", Boolean.valueOf(GL_ARB_fragment_program));
    playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_shader]", Boolean.valueOf(GL_ARB_fragment_shader));
    playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_program_shadow]", Boolean.valueOf(GL_ARB_fragment_program_shadow));
    playerSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_object]", Boolean.valueOf(GL_ARB_framebuffer_object));
    playerSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_sRGB]", Boolean.valueOf(GL_ARB_framebuffer_sRGB));
    playerSnooper.addStatToSnooper("gl_caps[ARB_geometry_shader4]", Boolean.valueOf(GL_ARB_geometry_shader4));
    playerSnooper.addStatToSnooper("gl_caps[ARB_gpu_shader5]", Boolean.valueOf(GL_ARB_gpu_shader5));
    playerSnooper.addStatToSnooper("gl_caps[ARB_half_float_pixel]", Boolean.valueOf(GL_ARB_half_float_pixel));
    playerSnooper.addStatToSnooper("gl_caps[ARB_half_float_vertex]", Boolean.valueOf(GL_ARB_half_float_vertex));
    playerSnooper.addStatToSnooper("gl_caps[ARB_instanced_arrays]", Boolean.valueOf(GL_ARB_instanced_arrays));
    playerSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_alignment]", Boolean.valueOf(GL_ARB_map_buffer_alignment));
    playerSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_range]", Boolean.valueOf(GL_ARB_map_buffer_range));
    playerSnooper.addStatToSnooper("gl_caps[ARB_multisample]", Boolean.valueOf(GL_ARB_multisample));
    playerSnooper.addStatToSnooper("gl_caps[ARB_multitexture]", Boolean.valueOf(GL_ARB_multitexture));
    playerSnooper.addStatToSnooper("gl_caps[ARB_occlusion_query2]", Boolean.valueOf(GL_ARB_occlusion_query2));
    playerSnooper.addStatToSnooper("gl_caps[ARB_pixel_buffer_object]", Boolean.valueOf(GL_ARB_pixel_buffer_object));
    playerSnooper.addStatToSnooper("gl_caps[ARB_seamless_cube_map]", Boolean.valueOf(GL_ARB_seamless_cube_map));
    playerSnooper.addStatToSnooper("gl_caps[ARB_shader_objects]", Boolean.valueOf(GL_ARB_shader_objects));
    playerSnooper.addStatToSnooper("gl_caps[ARB_shader_stencil_export]", Boolean.valueOf(GL_ARB_shader_stencil_export));
    playerSnooper.addStatToSnooper("gl_caps[ARB_shader_texture_lod]", Boolean.valueOf(GL_ARB_shader_texture_lod));
    playerSnooper.addStatToSnooper("gl_caps[ARB_shadow]", Boolean.valueOf(GL_ARB_shadow));
    playerSnooper.addStatToSnooper("gl_caps[ARB_shadow_ambient]", Boolean.valueOf(GL_ARB_shadow_ambient));
    playerSnooper.addStatToSnooper("gl_caps[ARB_stencil_texturing]", Boolean.valueOf(GL_ARB_stencil_texturing));
    playerSnooper.addStatToSnooper("gl_caps[ARB_sync]", Boolean.valueOf(GL_ARB_sync));
    playerSnooper.addStatToSnooper("gl_caps[ARB_tessellation_shader]", Boolean.valueOf(GL_ARB_tessellation_shader));
    playerSnooper.addStatToSnooper("gl_caps[ARB_texture_border_clamp]", Boolean.valueOf(GL_ARB_texture_border_clamp));
    playerSnooper.addStatToSnooper("gl_caps[ARB_texture_buffer_object]", Boolean.valueOf(GL_ARB_texture_buffer_object));
    playerSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map]", Boolean.valueOf(GL_ARB_texture_cube_map));
    playerSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map_array]", Boolean.valueOf(GL_ARB_texture_cube_map_array));
    playerSnooper.addStatToSnooper("gl_caps[ARB_texture_non_power_of_two]", Boolean.valueOf(GL_ARB_texture_non_power_of_two));
    playerSnooper.addStatToSnooper("gl_caps[ARB_uniform_buffer_object]", Boolean.valueOf(GL_ARB_uniform_buffer_object));
    playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_blend]", Boolean.valueOf(GL_ARB_vertex_blend));
    playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_buffer_object]", Boolean.valueOf(GL_ARB_vertex_buffer_object));
    playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_program]", Boolean.valueOf(GL_ARB_vertex_program));
    playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_shader]", Boolean.valueOf(GL_ARB_vertex_shader));
    playerSnooper.addStatToSnooper("gl_caps[EXT_bindable_uniform]", Boolean.valueOf(GL_EXT_bindable_uniform));
    playerSnooper.addStatToSnooper("gl_caps[EXT_blend_equation_separate]", Boolean.valueOf(GL_EXT_blend_equation_separate));
    playerSnooper.addStatToSnooper("gl_caps[EXT_blend_func_separate]", Boolean.valueOf(GL_EXT_blend_func_separate));
    playerSnooper.addStatToSnooper("gl_caps[EXT_blend_minmax]", Boolean.valueOf(GL_EXT_blend_minmax));
    playerSnooper.addStatToSnooper("gl_caps[EXT_blend_subtract]", Boolean.valueOf(GL_EXT_blend_subtract));
    playerSnooper.addStatToSnooper("gl_caps[EXT_draw_instanced]", Boolean.valueOf(GL_EXT_draw_instanced));
    playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_multisample]", Boolean.valueOf(GL_EXT_framebuffer_multisample));
    playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_object]", Boolean.valueOf(GL_EXT_framebuffer_object));
    playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_sRGB]", Boolean.valueOf(GL_EXT_framebuffer_sRGB));
    playerSnooper.addStatToSnooper("gl_caps[EXT_geometry_shader4]", Boolean.valueOf(GL_EXT_geometry_shader4));
    playerSnooper.addStatToSnooper("gl_caps[EXT_gpu_program_parameters]", Boolean.valueOf(GL_EXT_gpu_program_parameters));
    playerSnooper.addStatToSnooper("gl_caps[EXT_gpu_shader4]", Boolean.valueOf(GL_EXT_gpu_shader4));
    playerSnooper.addStatToSnooper("gl_caps[EXT_multi_draw_arrays]", Boolean.valueOf(GL_EXT_multi_draw_arrays));
    playerSnooper.addStatToSnooper("gl_caps[EXT_packed_depth_stencil]", Boolean.valueOf(GL_EXT_packed_depth_stencil));
    playerSnooper.addStatToSnooper("gl_caps[EXT_paletted_texture]", Boolean.valueOf(GL_EXT_paletted_texture));
    playerSnooper.addStatToSnooper("gl_caps[EXT_rescale_normal]", Boolean.valueOf(GL_EXT_rescale_normal));
    playerSnooper.addStatToSnooper("gl_caps[EXT_separate_shader_objects]", Boolean.valueOf(GL_EXT_separate_shader_objects));
    playerSnooper.addStatToSnooper("gl_caps[EXT_shader_image_load_store]", Boolean.valueOf(GL_EXT_shader_image_load_store));
    playerSnooper.addStatToSnooper("gl_caps[EXT_shadow_funcs]", Boolean.valueOf(GL_EXT_shadow_funcs));
    playerSnooper.addStatToSnooper("gl_caps[EXT_shared_texture_palette]", Boolean.valueOf(GL_EXT_shared_texture_palette));
    playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_clear_tag]", Boolean.valueOf(GL_EXT_stencil_clear_tag));
    playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_two_side]", Boolean.valueOf(GL_EXT_stencil_two_side));
    playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_wrap]", Boolean.valueOf(GL_EXT_stencil_wrap));
    playerSnooper.addStatToSnooper("gl_caps[EXT_texture_3d]", Boolean.valueOf(GL_EXT_texture_3d));
    playerSnooper.addStatToSnooper("gl_caps[EXT_texture_array]", Boolean.valueOf(GL_EXT_texture_array));
    playerSnooper.addStatToSnooper("gl_caps[EXT_texture_buffer_object]", Boolean.valueOf(GL_EXT_texture_buffer_object));
    playerSnooper.addStatToSnooper("gl_caps[EXT_texture_integer]", Boolean.valueOf(GL_EXT_texture_integer));
    playerSnooper.addStatToSnooper("gl_caps[EXT_texture_lod_bias]", Boolean.valueOf(GL_EXT_texture_lod_bias));
    playerSnooper.addStatToSnooper("gl_caps[EXT_texture_sRGB]", Boolean.valueOf(GL_EXT_texture_sRGB));
    playerSnooper.addStatToSnooper("gl_caps[EXT_vertex_shader]", Boolean.valueOf(GL_EXT_vertex_shader));
    playerSnooper.addStatToSnooper("gl_caps[EXT_vertex_weighting]", Boolean.valueOf(GL_EXT_vertex_weighting));
    playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_uniforms]", Integer.valueOf(GL11.glGetInteger(35658)));
    GL11.glGetError();
    playerSnooper.addStatToSnooper("gl_caps[gl_max_fragment_uniforms]", Integer.valueOf(GL11.glGetInteger(35657)));
    GL11.glGetError();
    playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_attribs]", Integer.valueOf(GL11.glGetInteger(34921)));
    GL11.glGetError();
    playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_texture_image_units]", Integer.valueOf(GL11.glGetInteger(35660)));
    GL11.glGetError();
    playerSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", Integer.valueOf(GL11.glGetInteger(34930)));
    GL11.glGetError();
    playerSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", Integer.valueOf(GL11.glGetInteger(35071)));
    GL11.glGetError();
    playerSnooper.addStatToSnooper("gl_max_texture_size", Integer.valueOf(getGLMaximumTextureSize()));
  }
  



  public static int getGLMaximumTextureSize()
  {
    for (int var0 = 16384; var0 > 0; var0 >>= 1)
    {
      GL11.glTexImage2D(32868, 0, 6408, var0, var0, 0, 6408, 5121, null);
      int var1 = GL11.glGetTexLevelParameteri(32868, 0, 4096);
      
      if (var1 != 0)
      {
        return var0;
      }
    }
    
    return -1;
  }
  



  public boolean isSnooperEnabled()
  {
    return gameSettings.snooperEnabled;
  }
  



  public void setServerData(ServerData serverDataIn)
  {
    currentServerData = serverDataIn;
  }
  
  public ServerData getCurrentServerData()
  {
    return currentServerData;
  }
  
  public boolean isIntegratedServerRunning()
  {
    return integratedServerIsRunning;
  }
  



  public boolean isSingleplayer()
  {
    return (integratedServerIsRunning) && (theIntegratedServer != null);
  }
  



  public IntegratedServer getIntegratedServer()
  {
    return theIntegratedServer;
  }
  
  public static void stopIntegratedServer()
  {
    if (theMinecraft != null)
    {
      IntegratedServer var0 = theMinecraft.getIntegratedServer();
      
      if (var0 != null)
      {
        var0.stopServer();
      }
    }
  }
  



  public PlayerUsageSnooper getPlayerUsageSnooper()
  {
    return usageSnooper;
  }
  



  public static long getSystemTime()
  {
    return Sys.getTime() * 1000L / Sys.getTimerResolution();
  }
  



  public boolean isFullScreen()
  {
    return fullscreen;
  }
  
  public Session getSession()
  {
    return session;
  }
  
  public PropertyMap func_180509_L()
  {
    return twitchDetails;
  }
  
  public Proxy getProxy()
  {
    return proxy;
  }
  
  public TextureManager getTextureManager()
  {
    return renderEngine;
  }
  
  public IResourceManager getResourceManager()
  {
    return mcResourceManager;
  }
  
  public ResourcePackRepository getResourcePackRepository()
  {
    return mcResourcePackRepository;
  }
  
  public LanguageManager getLanguageManager()
  {
    return mcLanguageManager;
  }
  
  public TextureMap getTextureMapBlocks()
  {
    return textureMapBlocks;
  }
  
  public boolean isJava64bit()
  {
    return jvm64bit;
  }
  
  public boolean isGamePaused()
  {
    return isGamePaused;
  }
  
  public SoundHandler getSoundHandler()
  {
    return mcSoundHandler;
  }
  
  public MusicTicker.MusicType getAmbientMusicType()
  {
    return thePlayer != null ? MusicTicker.MusicType.GAME : (thePlayer.capabilities.isCreativeMode) && (thePlayer.capabilities.allowFlying) ? MusicTicker.MusicType.CREATIVE : (thePlayer.worldObj.provider instanceof WorldProviderEnd) ? MusicTicker.MusicType.END : (BossStatus.bossName != null) && (BossStatus.statusBarTime > 0) ? MusicTicker.MusicType.END_BOSS : (thePlayer.worldObj.provider instanceof net.minecraft.world.WorldProviderHell) ? MusicTicker.MusicType.NETHER : (currentScreen instanceof GuiWinGame) ? MusicTicker.MusicType.CREDITS : MusicTicker.MusicType.MENU;
  }
  
  public IStream getTwitchStream()
  {
    return stream;
  }
  
  public void dispatchKeypresses()
  {
    int var1 = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() : Keyboard.getEventKey();
    
    if ((var1 != 0) && (!Keyboard.isRepeatEvent()))
    {
      if ((!(currentScreen instanceof GuiControls)) || (currentScreen).time <= getSystemTime() - 20L))
      {
        if (Keyboard.getEventKeyState())
        {
          if (var1 == gameSettings.keyBindStreamStartStop.getKeyCode())
          {
            if (getTwitchStream().func_152934_n())
            {
              getTwitchStream().func_152914_u();
            }
            else if (getTwitchStream().func_152924_m())
            {
              displayGuiScreen(new GuiYesNo(new GuiYesNoCallback()
              {
                private static final String __OBFID = "CL_00001852";
                
                public void confirmClicked(boolean result, int id) {
                  if (result)
                  {
                    getTwitchStream().func_152930_t();
                  }
                  
                  displayGuiScreen(null);
                }
              }, I18n.format("stream.confirm_start", new Object[0]), "", 0));
            }
            else if ((getTwitchStream().func_152928_D()) && (getTwitchStream().func_152936_l()))
            {
              if (theWorld != null)
              {
                ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("Not ready to start streaming yet!"));
              }
              
            }
            else {
              GuiStreamUnavailable.func_152321_a(currentScreen);
            }
          }
          else if (var1 == gameSettings.keyBindStreamPauseUnpause.getKeyCode())
          {
            if (getTwitchStream().func_152934_n())
            {
              if (getTwitchStream().isPaused())
              {
                getTwitchStream().func_152933_r();
              }
              else
              {
                getTwitchStream().func_152916_q();
              }
            }
          }
          else if (var1 == gameSettings.keyBindStreamCommercials.getKeyCode())
          {
            if (getTwitchStream().func_152934_n())
            {
              getTwitchStream().func_152931_p();
            }
          }
          else if (var1 == gameSettings.keyBindStreamToggleMic.getKeyCode())
          {
            stream.func_152910_a(true);
          }
          else if (var1 == gameSettings.keyBindFullscreen.getKeyCode())
          {
            toggleFullscreen();
          }
          else if (var1 == gameSettings.keyBindScreenshot.getKeyCode())
          {
            ingameGUI.getChatGUI().printChatMessage(ScreenShotHelper.saveScreenshot(mcDataDir, displayWidth, displayHeight, framebufferMc));
          }
        }
        else if (var1 == gameSettings.keyBindStreamToggleMic.getKeyCode())
        {
          stream.func_152910_a(false);
        }
      }
    }
  }
  
  public MinecraftSessionService getSessionService()
  {
    return sessionService;
  }
  
  public SkinManager getSkinManager()
  {
    return skinManager;
  }
  
  public Entity func_175606_aa()
  {
    return field_175622_Z;
  }
  
  public void func_175607_a(Entity p_175607_1_)
  {
    field_175622_Z = p_175607_1_;
    entityRenderer.func_175066_a(p_175607_1_);
  }
  
  public ListenableFuture addScheduledTask(Callable callableToSchedule)
  {
    Validate.notNull(callableToSchedule);
    
    if (!isCallingFromMinecraftThread())
    {
      ListenableFutureTask var2 = ListenableFutureTask.create(callableToSchedule);
      Queue var3 = scheduledTasks;
      
      synchronized (scheduledTasks)
      {
        scheduledTasks.add(var2);
        return var2;
      }
    }
    

    try
    {
      return Futures.immediateFuture(callableToSchedule.call());
    }
    catch (Exception var6)
    {
      return Futures.immediateFailedCheckedFuture(var6);
    }
  }
  

  public ListenableFuture addScheduledTask(Runnable runnableToSchedule)
  {
    Validate.notNull(runnableToSchedule);
    return addScheduledTask(Executors.callable(runnableToSchedule));
  }
  
  public boolean isCallingFromMinecraftThread()
  {
    return Thread.currentThread() == mcThread;
  }
  
  public BlockRendererDispatcher getBlockRendererDispatcher()
  {
    return field_175618_aM;
  }
  
  public RenderManager getRenderManager()
  {
    return renderManager;
  }
  
  public RenderItem getRenderItem()
  {
    return renderItem;
  }
  
  public ItemRenderer getItemRenderer()
  {
    return itemRenderer;
  }
  
  public static int func_175610_ah()
  {
    return debugFPS;
  }
  
  public static Map func_175596_ai()
  {
    HashMap var0 = Maps.newHashMap();
    var0.put("X-Minecraft-Username", getMinecraft().getSession().getUsername());
    var0.put("X-Minecraft-UUID", getMinecraft().getSession().getPlayerID());
    var0.put("X-Minecraft-Version", "1.8");
    return var0;
  }
  

  static final class SwitchEnumMinecartType
  {
    static final int[] field_152390_a;
    static final int[] field_178901_b = new int[EntityMinecart.EnumMinecartType.values().length];
    private static final String __OBFID = "CL_00001959";
    
    static
    {
      try
      {
        field_178901_b[EntityMinecart.EnumMinecartType.FURNACE.ordinal()] = 1;
      }
      catch (NoSuchFieldError localNoSuchFieldError1) {}
      



      try
      {
        field_178901_b[EntityMinecart.EnumMinecartType.CHEST.ordinal()] = 2;
      }
      catch (NoSuchFieldError localNoSuchFieldError2) {}
      



      try
      {
        field_178901_b[EntityMinecart.EnumMinecartType.TNT.ordinal()] = 3;
      }
      catch (NoSuchFieldError localNoSuchFieldError3) {}
      



      try
      {
        field_178901_b[EntityMinecart.EnumMinecartType.HOPPER.ordinal()] = 4;
      }
      catch (NoSuchFieldError localNoSuchFieldError4) {}
      



      try
      {
        field_178901_b[EntityMinecart.EnumMinecartType.COMMAND_BLOCK.ordinal()] = 5;
      }
      catch (NoSuchFieldError localNoSuchFieldError5) {}
      



      field_152390_a = new int[MovingObjectPosition.MovingObjectType.values().length];
      
      try
      {
        field_152390_a[MovingObjectPosition.MovingObjectType.ENTITY.ordinal()] = 1;
      }
      catch (NoSuchFieldError localNoSuchFieldError6) {}
      



      try
      {
        field_152390_a[MovingObjectPosition.MovingObjectType.BLOCK.ordinal()] = 2;
      }
      catch (NoSuchFieldError localNoSuchFieldError7) {}
      



      try
      {
        field_152390_a[MovingObjectPosition.MovingObjectType.MISS.ordinal()] = 3;
      }
      catch (NoSuchFieldError localNoSuchFieldError8) {}
    }
    
    SwitchEnumMinecartType() {}
  }
}
