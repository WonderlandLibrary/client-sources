/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.client;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import javax.imageio.ImageIO;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.MouseEvent;
import me.thekirkayt.event.events.KeyPressEvent;
import me.thekirkayt.event.events.RunLoopEvent;
import me.thekirkayt.event.events.TickEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.LoadingScreenRenderer;
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
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.BlockModelShapes;
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
import net.minecraft.client.renderer.texture.ITickableTextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.FoliageColorReloadListener;
import net.minecraft.client.resources.GrassColorReloadListener;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.resources.ResourceIndex;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
import net.minecraft.client.resources.data.FontMetadataSection;
import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
import net.minecraft.client.resources.data.IMetadataSectionSerializer;
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
import net.minecraft.client.stream.IStream;
import net.minecraft.client.stream.NullStream;
import net.minecraft.client.stream.TwitchStream;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmorStand;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.NetworkSystem;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.IStatStringFormat;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import net.minecraft.util.Util;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
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

public class Minecraft
implements IThreadListener,
IPlayerUsage {
    private static final Logger logger = LogManager.getLogger();
    private static final ResourceLocation locationMojangPng = new ResourceLocation("textures/gui/title/mojang.png");
    public static final boolean isRunningOnMac = Util.getOSType() == Util.EnumOS.OSX;
    public static byte[] memoryReserve = new byte[10485760];
    private static final List macDisplayModes = Lists.newArrayList((Object[])new DisplayMode[]{new DisplayMode(2560, 1600), new DisplayMode(2880, 1800)});
    private final File fileResourcepacks;
    private final PropertyMap twitchDetails;
    private ServerData currentServerData;
    public TextureManager renderEngine;
    private static Minecraft theMinecraft;
    public static PlayerControllerMP playerController;
    private boolean fullscreen;
    private boolean field_175619_R = true;
    private boolean hasCrashed;
    private CrashReport crashReporter;
    public int displayWidth;
    public int displayHeight;
    public Timer timer = new Timer(20.0f);
    private PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("client", this, MinecraftServer.getCurrentTimeMillis());
    public static WorldClient theWorld;
    public static RenderGlobal renderGlobal;
    public static RenderManager renderManager;
    private RenderItem renderItem;
    private ItemRenderer itemRenderer;
    public static EntityPlayerSP thePlayer;
    private Entity field_175622_Z;
    public Entity pointedEntity;
    public EffectRenderer effectRenderer;
    public Session session;
    private boolean isGamePaused;
    public FontRenderer fontRendererObj;
    public FontRenderer standardGalacticFontRenderer;
    public static GuiScreen currentScreen;
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
    public static int debugFPS;
    public int rightClickDelayTimer;
    private String serverName;
    private int serverPort;
    public boolean inGameHasFocus;
    long systemTime = Minecraft.getSystemTime();
    private int joinPlayerCounter;
    private final boolean jvm64bit;
    private final boolean isDemo;
    private NetworkManager myNetworkManager;
    private boolean integratedServerIsRunning;
    public final Profiler mcProfiler = new Profiler();
    private long debugCrashKeyPressTime = -1L;
    public IReloadableResourceManager mcResourceManager;
    private final IMetadataSerializer metadataSerializer_ = new IMetadataSerializer();
    private final List defaultResourcePacks = Lists.newArrayList();
    private final DefaultResourcePack mcDefaultResourcePack;
    private ResourcePackRepository mcResourcePackRepository;
    public LanguageManager mcLanguageManager;
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
    public ModelManager modelManager;
    private BlockRendererDispatcher field_175618_aM;
    public static volatile boolean running;
    public String debug = "";
    public boolean field_175613_B = false;
    public boolean field_175614_C = false;
    public boolean field_175611_D = false;
    public boolean field_175612_E = true;
    long debugUpdateTime = Minecraft.getSystemTime();
    int fpsCounter;
    long prevFrameTime = -1L;
    private String debugProfilerName = "root";
    private static final String __OBFID = "CL_00000631";

    static {
        running = true;
    }

    public Minecraft(GameConfiguration p_i45547_1_) {
        theMinecraft = this;
        this.mcDataDir = p_i45547_1_.field_178744_c.field_178760_a;
        this.fileAssets = p_i45547_1_.field_178744_c.field_178759_c;
        this.fileResourcepacks = p_i45547_1_.field_178744_c.field_178758_b;
        this.launchedVersion = p_i45547_1_.field_178741_d.field_178755_b;
        this.twitchDetails = p_i45547_1_.field_178745_a.field_178750_b;
        this.mcDefaultResourcePack = new DefaultResourcePack(new ResourceIndex(p_i45547_1_.field_178744_c.field_178759_c, p_i45547_1_.field_178744_c.field_178757_d).func_152782_a());
        this.proxy = p_i45547_1_.field_178745_a.field_178751_c == null ? Proxy.NO_PROXY : p_i45547_1_.field_178745_a.field_178751_c;
        this.sessionService = new YggdrasilAuthenticationService(p_i45547_1_.field_178745_a.field_178751_c, UUID.randomUUID().toString()).createMinecraftSessionService();
        this.session = p_i45547_1_.field_178745_a.field_178752_a;
        logger.info("Setting user: " + this.session.getUsername());
        logger.info("(Session ID is " + this.session.getSessionID() + ")");
        this.isDemo = p_i45547_1_.field_178741_d.field_178756_a;
        this.displayWidth = p_i45547_1_.field_178743_b.field_178764_a > 0 ? p_i45547_1_.field_178743_b.field_178764_a : 1;
        this.displayHeight = p_i45547_1_.field_178743_b.field_178762_b > 0 ? p_i45547_1_.field_178743_b.field_178762_b : 1;
        this.tempDisplayWidth = p_i45547_1_.field_178743_b.field_178764_a;
        this.tempDisplayHeight = p_i45547_1_.field_178743_b.field_178762_b;
        this.fullscreen = p_i45547_1_.field_178743_b.field_178763_c;
        this.jvm64bit = Minecraft.isJvm64bit();
        this.theIntegratedServer = new IntegratedServer(this);
        if (p_i45547_1_.field_178742_e.field_178754_a != null) {
            this.serverName = p_i45547_1_.field_178742_e.field_178754_a;
            this.serverPort = p_i45547_1_.field_178742_e.field_178753_b;
        }
        ImageIO.setUseCache(false);
        Bootstrap.register();
    }

    /*
     * Exception decompiling
     */
    public void run() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:697)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:185)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:130)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:890)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:792)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:128)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:63)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private void startGame() throws LWJGLException {
        this.gameSettings = new GameSettings(this, this.mcDataDir);
        this.defaultResourcePacks.add(this.mcDefaultResourcePack);
        this.startTimerHackThread();
        if (this.gameSettings.overrideHeight > 0 && this.gameSettings.overrideWidth > 0) {
            this.displayWidth = this.gameSettings.overrideWidth;
            this.displayHeight = this.gameSettings.overrideHeight;
        }
        logger.info("LWJGL Version: " + Sys.getVersion());
        this.func_175594_ao();
        this.func_175605_an();
        this.func_175609_am();
        Display.setTitle((String)"Loading Kirka b11...");
        OpenGlHelper.initializeTextures();
        this.framebufferMc = new Framebuffer(this.displayWidth, this.displayHeight, true);
        this.framebufferMc.setFramebufferColor(0.0f, 0.0f, 0.0f, 0.0f);
        this.func_175608_ak();
        this.mcResourcePackRepository = new ResourcePackRepository(this.fileResourcepacks, new File(this.mcDataDir, "server-resource-packs"), this.mcDefaultResourcePack, this.metadataSerializer_, this.gameSettings);
        this.mcResourceManager = new SimpleReloadableResourceManager(this.metadataSerializer_);
        this.mcLanguageManager = new LanguageManager(this.metadataSerializer_, this.gameSettings.language);
        this.mcResourceManager.registerReloadListener(this.mcLanguageManager);
        this.refreshResources();
        this.renderEngine = new TextureManager(this.mcResourceManager);
        this.mcResourceManager.registerReloadListener(this.renderEngine);
        this.func_180510_a(this.renderEngine);
        this.func_175595_al();
        this.skinManager = new SkinManager(this.renderEngine, new File(this.fileAssets, "skins"), this.sessionService);
        this.saveLoader = new AnvilSaveConverter(new File(this.mcDataDir, "saves"));
        this.mcSoundHandler = new SoundHandler(this.mcResourceManager, this.gameSettings);
        this.mcResourceManager.registerReloadListener(this.mcSoundHandler);
        this.mcMusicTicker = new MusicTicker(this);
        this.fontRendererObj = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii.png"), this.renderEngine, false);
        if (this.gameSettings.language != null) {
            this.fontRendererObj.setUnicodeFlag(this.isUnicode());
            this.fontRendererObj.setBidiFlag(this.mcLanguageManager.isCurrentLanguageBidirectional());
        }
        this.standardGalacticFontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii_sga.png"), this.renderEngine, false);
        this.mcResourceManager.registerReloadListener(this.fontRendererObj);
        this.mcResourceManager.registerReloadListener(this.standardGalacticFontRenderer);
        this.mcResourceManager.registerReloadListener(new GrassColorReloadListener());
        this.mcResourceManager.registerReloadListener(new FoliageColorReloadListener());
        AchievementList.openInventory.setStatStringFormatter(new IStatStringFormat(){
            private static final String __OBFID = "CL_00000632";

            @Override
            public String formatString(String p_74535_1_) {
                try {
                    return String.format(p_74535_1_, GameSettings.getKeyDisplayString(Minecraft.this.gameSettings.keyBindInventory.getKeyCode()));
                }
                catch (Exception var3) {
                    return "Error: " + var3.getLocalizedMessage();
                }
            }
        });
        this.mouseHelper = new MouseHelper();
        this.checkGLError("Pre startup");
        GlStateManager.bindTexture();
        GlStateManager.shadeModel(7425);
        GlStateManager.clearDepth(1.0);
        GlStateManager.enableDepth();
        GlStateManager.depthFunc(515);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.cullFace(1029);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        this.checkGLError("Startup");
        this.textureMapBlocks = new TextureMap("textures");
        this.textureMapBlocks.setMipmapLevels(this.gameSettings.mipmapLevels);
        this.renderEngine.loadTickableTexture(TextureMap.locationBlocksTexture, this.textureMapBlocks);
        this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        this.textureMapBlocks.func_174937_a(false, this.gameSettings.mipmapLevels > 0);
        this.modelManager = new ModelManager(this.textureMapBlocks);
        this.mcResourceManager.registerReloadListener(this.modelManager);
        this.renderItem = new RenderItem(this.renderEngine, this.modelManager);
        renderManager = new RenderManager(this.renderEngine, this.renderItem);
        this.itemRenderer = new ItemRenderer(this);
        this.mcResourceManager.registerReloadListener(this.renderItem);
        this.entityRenderer = new EntityRenderer(this, this.mcResourceManager);
        this.mcResourceManager.registerReloadListener(this.entityRenderer);
        this.field_175618_aM = new BlockRendererDispatcher(this.modelManager.getBlockModelShapes(), this.gameSettings);
        this.mcResourceManager.registerReloadListener(this.field_175618_aM);
        renderGlobal = new RenderGlobal(this);
        this.mcResourceManager.registerReloadListener(renderGlobal);
        this.guiAchievement = new GuiAchievement(this);
        GlStateManager.viewport(0, 0, this.displayWidth, this.displayHeight);
        this.effectRenderer = new EffectRenderer(theWorld, this.renderEngine);
        Display.setTitle((String)"Kirka b11");
        this.checkGLError("Post startup");
        this.ingameGUI = new GuiIngame(this);
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
            Display.setVSyncEnabled((boolean)this.gameSettings.enableVsync);
        }
        catch (OpenGLException var2) {
            this.gameSettings.enableVsync = false;
            this.gameSettings.saveOptions();
        }
        renderGlobal.func_174966_b();
    }

    private void func_175608_ak() {
        this.metadataSerializer_.registerMetadataSectionType(new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new FontMetadataSectionSerializer(), FontMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new PackMetadataSectionSerializer(), PackMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
    }

    private void func_175595_al() {
        try {
            this.stream = new TwitchStream(this, (Property)Iterables.getFirst((Iterable)this.twitchDetails.get((Object)"twitch_access_token"), null));
        }
        catch (Throwable var2) {
            this.stream = new NullStream(var2);
            logger.error("Couldn't initialize twitch stream");
        }
    }

    private void func_175609_am() throws LWJGLException {
        Display.setResizable((boolean)true);
        Display.setTitle((String)"Minecraft 1.8");
        try {
            Display.create((PixelFormat)new PixelFormat().withDepthBits(24));
        }
        catch (LWJGLException var4) {
            logger.error("Couldn't set pixel format", (Throwable)var4);
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            if (this.fullscreen) {
                this.updateDisplayMode();
            }
            Display.create();
        }
    }

    private void func_175605_an() throws LWJGLException {
        if (this.fullscreen) {
            Display.setFullscreen((boolean)true);
            DisplayMode var1 = Display.getDisplayMode();
            this.displayWidth = Math.max(1, var1.getWidth());
            this.displayHeight = Math.max(1, var1.getHeight());
        } else {
            Display.setDisplayMode((DisplayMode)new DisplayMode(this.displayWidth, this.displayHeight));
        }
    }

    private void func_175594_ao() {
        block7 : {
            Util.EnumOS var1 = Util.getOSType();
            if (var1 != Util.EnumOS.OSX) {
                InputStream var2 = null;
                InputStream var3 = null;
                try {
                    try {
                        var2 = this.mcDefaultResourcePack.func_152780_c(new ResourceLocation("icons/icon_16x16.png"));
                        var3 = this.mcDefaultResourcePack.func_152780_c(new ResourceLocation("icons/icon_32x32.png"));
                        if (var2 != null && var3 != null) {
                            Display.setIcon((ByteBuffer[])new ByteBuffer[]{this.readImageToBuffer(var2), this.readImageToBuffer(var3)});
                        }
                    }
                    catch (IOException var8) {
                        logger.error("Couldn't set icon", (Throwable)var8);
                        IOUtils.closeQuietly((InputStream)var2);
                        IOUtils.closeQuietly(var3);
                        break block7;
                    }
                }
                catch (Throwable throwable) {
                    IOUtils.closeQuietly(var2);
                    IOUtils.closeQuietly(var3);
                    throw throwable;
                }
                IOUtils.closeQuietly((InputStream)var2);
                IOUtils.closeQuietly((InputStream)var3);
            }
        }
    }

    private static boolean isJvm64bit() {
        String[] var0;
        String[] var1 = var0 = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};
        int var2 = var0.length;
        for (int var3 = 0; var3 < var2; ++var3) {
            String var4 = var1[var3];
            String var5 = System.getProperty(var4);
            if (var5 == null || !var5.contains("64")) continue;
            return true;
        }
        return false;
    }

    public Framebuffer getFramebuffer() {
        return this.framebufferMc;
    }

    public String func_175600_c() {
        return this.launchedVersion;
    }

    private void startTimerHackThread() {
        Thread var1 = new Thread("Timer hack thread"){
            private static final String __OBFID = "CL_00000639";

            @Override
            public void run() {
                while (running) {
                    try {
                        Thread.sleep(Integer.MAX_VALUE);
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                }
            }
        };
        var1.setDaemon(true);
        var1.start();
    }

    public void crashed(CrashReport crash) {
        this.hasCrashed = true;
        this.crashReporter = crash;
    }

    public void displayCrashReport(CrashReport crashReportIn) {
        File var2 = new File(Minecraft.getMinecraft().mcDataDir, "crash-reports");
        File var3 = new File(var2, "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt");
        Bootstrap.func_179870_a(crashReportIn.getCompleteReport());
        if (crashReportIn.getFile() != null) {
            Bootstrap.func_179870_a("#@!@# Game crashed! Crash report saved to: #@!@# " + crashReportIn.getFile());
            System.exit(-1);
        } else if (crashReportIn.saveToFile(var3)) {
            Bootstrap.func_179870_a("#@!@# Game crashed! Crash report saved to: #@!@# " + var3.getAbsolutePath());
            System.exit(-1);
        } else {
            Bootstrap.func_179870_a("#@?@# Game crashed! Crash report could not be saved. #@?@#");
            System.exit(-2);
        }
    }

    public boolean isUnicode() {
        return this.mcLanguageManager.isCurrentLocaleUnicode() || this.gameSettings.forceUnicodeFont;
    }

    public void refreshResources() {
        ArrayList var1 = Lists.newArrayList((Iterable)this.defaultResourcePacks);
        for (ResourcePackRepository.Entry var3 : this.mcResourcePackRepository.getRepositoryEntries()) {
            var1.add(var3.getResourcePack());
        }
        if (this.mcResourcePackRepository.getResourcePackInstance() != null) {
            var1.add(this.mcResourcePackRepository.getResourcePackInstance());
        }
        try {
            this.mcResourceManager.reloadResources(var1);
        }
        catch (RuntimeException var4) {
            logger.info("Caught error stitching, removing all assigned resourcepacks", (Throwable)var4);
            var1.clear();
            var1.addAll(this.defaultResourcePacks);
            this.mcResourcePackRepository.func_148527_a(Collections.emptyList());
            this.mcResourceManager.reloadResources(var1);
            this.gameSettings.resourcePacks.clear();
            this.gameSettings.saveOptions();
        }
        this.mcLanguageManager.parseLanguageMetadata(var1);
        if (renderGlobal != null) {
            renderGlobal.loadRenderers();
        }
    }

    private ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException {
        BufferedImage var2 = ImageIO.read(imageStream);
        int[] var3 = var2.getRGB(0, 0, var2.getWidth(), var2.getHeight(), null, 0, var2.getWidth());
        ByteBuffer var4 = ByteBuffer.allocate(4 * var3.length);
        int[] var5 = var3;
        int var6 = var3.length;
        for (int var7 = 0; var7 < var6; ++var7) {
            int var8 = var5[var7];
            var4.putInt(var8 << 8 | var8 >> 24 & 255);
        }
        var4.flip();
        return var4;
    }

    private void updateDisplayMode() throws LWJGLException {
        HashSet var1 = Sets.newHashSet();
        Collections.addAll(var1, Display.getAvailableDisplayModes());
        DisplayMode var2 = Display.getDesktopDisplayMode();
        if (!var1.contains((Object)var2) && Util.getOSType() == Util.EnumOS.OSX) {
            block0 : for (DisplayMode var4 : macDisplayModes) {
                boolean var5 = true;
                for (DisplayMode var7 : var1) {
                    if (var7.getBitsPerPixel() != 32 || var7.getWidth() != var4.getWidth() || var7.getHeight() != var4.getHeight()) continue;
                    var5 = false;
                    break;
                }
                if (var5) continue;
                for (DisplayMode var7 : var1) {
                    if (var7.getBitsPerPixel() != 32 || var7.getWidth() != var4.getWidth() / 2 || var7.getHeight() != var4.getHeight() / 2) continue;
                    var2 = var7;
                    continue block0;
                }
            }
        }
        Display.setDisplayMode((DisplayMode)var2);
        this.displayWidth = var2.getWidth();
        this.displayHeight = var2.getHeight();
    }

    private void func_180510_a(TextureManager p_180510_1_) {
        int var3;
        Framebuffer var4;
        block5 : {
            ScaledResolution var2 = new ScaledResolution(this, this.displayWidth, this.displayHeight);
            var3 = var2.getScaleFactor();
            var4 = new Framebuffer(ScaledResolution.getScaledWidth() * var3, ScaledResolution.getScaledHeight() * var3, true);
            var4.bindFramebuffer(false);
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            GlStateManager.ortho(0.0, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), 0.0, 1000.0, 3000.0);
            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0f, 0.0f, -2000.0f);
            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            GlStateManager.disableDepth();
            GlStateManager.bindTexture();
            InputStream var5 = null;
            try {
                try {
                    var5 = this.mcDefaultResourcePack.getInputStream(locationMojangPng);
                    this.mojangLogo = p_180510_1_.getDynamicTextureLocation("logo", new DynamicTexture(ImageIO.read(var5)));
                    p_180510_1_.bindTexture(this.mojangLogo);
                }
                catch (IOException var12) {
                    logger.error("Unable to load logo: " + locationMojangPng, (Throwable)var12);
                    IOUtils.closeQuietly((InputStream)var5);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                IOUtils.closeQuietly(var5);
                throw throwable;
            }
            IOUtils.closeQuietly((InputStream)var5);
        }
        Tessellator var6 = Tessellator.getInstance();
        WorldRenderer var7 = var6.getWorldRenderer();
        var7.startDrawingQuads();
        var7.func_178991_c(16777215);
        var7.addVertexWithUV(0.0, this.displayHeight, 0.0, 0.0, 0.0);
        var7.addVertexWithUV(this.displayWidth, this.displayHeight, 0.0, 0.0, 0.0);
        var7.addVertexWithUV(this.displayWidth, 0.0, 0.0, 0.0, 0.0);
        var7.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
        var6.draw();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        var7.func_178991_c(16777215);
        int var8 = 256;
        int var9 = 256;
        this.scaledTessellator((ScaledResolution.getScaledWidth() - var8) / 2, (ScaledResolution.getScaledHeight() - var9) / 2, 0, 0, var8, var9);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        var4.unbindFramebuffer();
        var4.framebufferRender(ScaledResolution.getScaledWidth() * var3, ScaledResolution.getScaledHeight() * var3);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1f);
        this.func_175601_h();
    }

    public void scaledTessellator(int width, int height, int width2, int height2, int stdTextureWidth, int stdTextureHeight) {
        float var7 = 0.00390625f;
        float var8 = 0.00390625f;
        WorldRenderer var9 = Tessellator.getInstance().getWorldRenderer();
        var9.startDrawingQuads();
        var9.addVertexWithUV(width + 0, height + stdTextureHeight, 0.0, (float)(width2 + 0) * var7, (float)(height2 + stdTextureHeight) * var8);
        var9.addVertexWithUV(width + stdTextureWidth, height + stdTextureHeight, 0.0, (float)(width2 + stdTextureWidth) * var7, (float)(height2 + stdTextureHeight) * var8);
        var9.addVertexWithUV(width + stdTextureWidth, height + 0, 0.0, (float)(width2 + stdTextureWidth) * var7, (float)(height2 + 0) * var8);
        var9.addVertexWithUV(width + 0, height + 0, 0.0, (float)(width2 + 0) * var7, (float)(height2 + 0) * var8);
        Tessellator.getInstance().draw();
    }

    public ISaveFormat getSaveLoader() {
        return this.saveLoader;
    }

    public void displayGuiScreen(GuiScreen guiScreenIn) {
        if (currentScreen != null) {
            currentScreen.onGuiClosed();
        }
        if (guiScreenIn == null && theWorld == null) {
            guiScreenIn = new GuiMainMenu();
        } else if (guiScreenIn == null && thePlayer.getHealth() <= 0.0f) {
            guiScreenIn = new GuiGameOver();
        }
        if (guiScreenIn instanceof GuiMainMenu) {
            this.gameSettings.showDebugInfo = false;
            this.ingameGUI.getChatGUI().clearChatMessages();
        }
        currentScreen = guiScreenIn;
        if (guiScreenIn != null) {
            this.setIngameNotInFocus();
            ScaledResolution var2 = new ScaledResolution(this, this.displayWidth, this.displayHeight);
            int var3 = ScaledResolution.getScaledWidth();
            int var4 = ScaledResolution.getScaledHeight();
            guiScreenIn.setWorldAndResolution(this, var3, var4);
            this.skipRenderWorld = false;
        } else {
            this.mcSoundHandler.resumeSounds();
            this.setIngameFocus();
        }
    }

    private void checkGLError(String message) {
        int var2;
        if (this.field_175619_R && (var2 = GL11.glGetError()) != 0) {
            String var3 = GLU.gluErrorString((int)var2);
            logger.error("########## GL ERROR ##########");
            logger.error("@ " + message);
            logger.error(String.valueOf(var2) + ": " + var3);
        }
    }

    public void shutdownMinecraftApplet() {
        try {
            this.stream.shutdownStream();
            logger.info("Stopping!");
            try {
                this.loadWorld(null);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            this.mcSoundHandler.unloadSounds();
        }
        finally {
            Display.destroy();
            if (!this.hasCrashed) {
                System.exit(0);
            }
        }
        System.gc();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void runGameLoop() throws IOException {
        new RunLoopEvent().call();
        this.mcProfiler.startSection("root");
        if (Display.isCreated() && Display.isCloseRequested()) {
            this.shutdown();
        }
        if (this.isGamePaused && theWorld != null) {
            float var1 = this.timer.renderPartialTicks;
            this.timer.updateTimer();
            this.timer.renderPartialTicks = var1;
        } else {
            this.timer.updateTimer();
        }
        this.mcProfiler.startSection("scheduledExecutables");
        Queue var6 = this.scheduledTasks;
        Queue queue = this.scheduledTasks;
        synchronized (queue) {
            while (!this.scheduledTasks.isEmpty()) {
                ((FutureTask)this.scheduledTasks.poll()).run();
            }
        }
        this.mcProfiler.endSection();
        long var7 = System.nanoTime();
        this.mcProfiler.startSection("tick");
        for (int var3 = 0; var3 < this.timer.elapsedTicks; ++var3) {
            this.runTick();
        }
        this.mcProfiler.endStartSection("preRenderErrors");
        long var8 = System.nanoTime() - var7;
        this.checkGLError("Pre render");
        this.mcProfiler.endStartSection("sound");
        this.mcSoundHandler.setListener(thePlayer, this.timer.renderPartialTicks);
        this.mcProfiler.endSection();
        this.mcProfiler.startSection("render");
        GlStateManager.pushMatrix();
        GlStateManager.clear(16640);
        this.framebufferMc.bindFramebuffer(true);
        this.mcProfiler.startSection("display");
        GlStateManager.bindTexture();
        if (thePlayer != null && thePlayer.isEntityInsideOpaqueBlock()) {
            this.gameSettings.thirdPersonView = 0;
        }
        this.mcProfiler.endSection();
        if (!this.skipRenderWorld) {
            this.mcProfiler.endStartSection("gameRenderer");
            this.entityRenderer.updateCameraAndRender(this.timer.renderPartialTicks);
            this.mcProfiler.endSection();
        }
        this.mcProfiler.endSection();
        if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart && !this.gameSettings.hideGUI) {
            if (!this.mcProfiler.profilingEnabled) {
                this.mcProfiler.clearProfiling();
            }
            this.mcProfiler.profilingEnabled = true;
            this.displayDebugInfo(var8);
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
        GlStateManager.pushMatrix();
        this.entityRenderer.func_152430_c(this.timer.renderPartialTicks);
        GlStateManager.popMatrix();
        this.mcProfiler.startSection("root");
        this.func_175601_h();
        Thread.yield();
        this.mcProfiler.startSection("stream");
        this.mcProfiler.startSection("update");
        this.stream.func_152935_j();
        this.mcProfiler.endStartSection("submit");
        this.stream.func_152922_k();
        this.mcProfiler.endSection();
        this.mcProfiler.endSection();
        this.checkGLError("Post render");
        ++this.fpsCounter;
        this.isGamePaused = this.isSingleplayer() && currentScreen != null && currentScreen.doesGuiPauseGame() && !this.theIntegratedServer.getPublic();
        while (Minecraft.getSystemTime() >= this.debugUpdateTime + 1000L) {
            debugFPS = this.fpsCounter;
            this.debug = String.format("%d fps (%d chunk update%s) T: %s%s%s%s%s", debugFPS, RenderChunk.field_178592_a, RenderChunk.field_178592_a != 1 ? "s" : "", (float)this.gameSettings.limitFramerate == GameSettings.Options.FRAMERATE_LIMIT.getValueMax() ? "inf" : Integer.valueOf(this.gameSettings.limitFramerate), this.gameSettings.enableVsync ? " vsync" : "", this.gameSettings.fancyGraphics ? "" : " fast", this.gameSettings.clouds ? " clouds" : "", OpenGlHelper.func_176075_f() ? " vbo" : "");
            RenderChunk.field_178592_a = 0;
            this.debugUpdateTime += 1000L;
            this.fpsCounter = 0;
            this.usageSnooper.addMemoryStatsToSnooper();
            if (this.usageSnooper.isSnooperRunning()) continue;
            this.usageSnooper.startSnooper();
        }
        if (this.isFramerateLimitBelowMax()) {
            this.mcProfiler.startSection("fpslimit_wait");
            Display.sync((int)this.getLimitFramerate());
            this.mcProfiler.endSection();
        }
        this.mcProfiler.endSection();
    }

    public void func_175601_h() {
        this.mcProfiler.startSection("display_update");
        Display.update();
        this.mcProfiler.endSection();
        this.func_175604_i();
    }

    protected void func_175604_i() {
        if (!this.fullscreen && Display.wasResized()) {
            int var1 = this.displayWidth;
            int var2 = this.displayHeight;
            this.displayWidth = Display.getWidth();
            this.displayHeight = Display.getHeight();
            if (this.displayWidth != var1 || this.displayHeight != var2) {
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
        return theWorld == null && currentScreen != null ? 30 : this.gameSettings.limitFramerate;
    }

    public boolean isFramerateLimitBelowMax() {
        return (float)this.getLimitFramerate() < GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
    }

    public void freeMemory() {
        try {
            memoryReserve = new byte[0];
            renderGlobal.deleteAllDisplayLists();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        try {
            System.gc();
            this.loadWorld(null);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        System.gc();
    }

    private void updateDebugProfilerName(int keyCount) {
        List var2 = this.mcProfiler.getProfilingData(this.debugProfilerName);
        if (var2 != null && !var2.isEmpty()) {
            Profiler.Result var3 = (Profiler.Result)var2.remove(0);
            if (keyCount == 0) {
                int var4;
                if (var3.field_76331_c.length() > 0 && (var4 = this.debugProfilerName.lastIndexOf(".")) >= 0) {
                    this.debugProfilerName = this.debugProfilerName.substring(0, var4);
                }
            } else if (--keyCount < var2.size() && !((Profiler.Result)var2.get((int)keyCount)).field_76331_c.equals("unspecified")) {
                if (this.debugProfilerName.length() > 0) {
                    this.debugProfilerName = String.valueOf(this.debugProfilerName) + ".";
                }
                this.debugProfilerName = String.valueOf(this.debugProfilerName) + ((Profiler.Result)var2.get((int)keyCount)).field_76331_c;
            }
        }
    }

    private void displayDebugInfo(long elapsedTicksTime) {
        if (this.mcProfiler.profilingEnabled) {
            int var14;
            List var3 = this.mcProfiler.getProfilingData(this.debugProfilerName);
            Profiler.Result var4 = (Profiler.Result)var3.remove(0);
            GlStateManager.clear(256);
            GlStateManager.matrixMode(5889);
            GlStateManager.enableColorMaterial();
            GlStateManager.loadIdentity();
            GlStateManager.ortho(0.0, this.displayWidth, this.displayHeight, 0.0, 1000.0, 3000.0);
            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0f, 0.0f, -2000.0f);
            GL11.glLineWidth((float)1.0f);
            GlStateManager.func_179090_x();
            Tessellator var5 = Tessellator.getInstance();
            WorldRenderer var6 = var5.getWorldRenderer();
            int var7 = 160;
            int var8 = this.displayWidth - var7 - 10;
            int var9 = this.displayHeight - var7 * 2;
            GlStateManager.enableBlend();
            var6.startDrawingQuads();
            var6.func_178974_a(0, 200);
            var6.addVertex((float)var8 - (float)var7 * 1.1f, (float)var9 - (float)var7 * 0.6f - 16.0f, 0.0);
            var6.addVertex((float)var8 - (float)var7 * 1.1f, var9 + var7 * 2, 0.0);
            var6.addVertex((float)var8 + (float)var7 * 1.1f, var9 + var7 * 2, 0.0);
            var6.addVertex((float)var8 + (float)var7 * 1.1f, (float)var9 - (float)var7 * 0.6f - 16.0f, 0.0);
            var5.draw();
            GlStateManager.disableBlend();
            double var10 = 0.0;
            for (int var12 = 0; var12 < var3.size(); ++var12) {
                float var18;
                float var17;
                float var16;
                int var15;
                Profiler.Result var13 = (Profiler.Result)var3.get(var12);
                var14 = MathHelper.floor_double(var13.field_76332_a / 4.0) + 1;
                var6.startDrawing(6);
                var6.func_178991_c(var13.func_76329_a());
                var6.addVertex(var8, var9, 0.0);
                for (var15 = var14; var15 >= 0; --var15) {
                    var16 = (float)((var10 + var13.field_76332_a * (double)var15 / (double)var14) * 3.141592653589793 * 2.0 / 100.0);
                    var17 = MathHelper.sin(var16) * (float)var7;
                    var18 = MathHelper.cos(var16) * (float)var7 * 0.5f;
                    var6.addVertex((float)var8 + var17, (float)var9 - var18, 0.0);
                }
                var5.draw();
                var6.startDrawing(5);
                var6.func_178991_c((var13.func_76329_a() & 16711422) >> 1);
                for (var15 = var14; var15 >= 0; --var15) {
                    var16 = (float)((var10 + var13.field_76332_a * (double)var15 / (double)var14) * 3.141592653589793 * 2.0 / 100.0);
                    var17 = MathHelper.sin(var16) * (float)var7;
                    var18 = MathHelper.cos(var16) * (float)var7 * 0.5f;
                    var6.addVertex((float)var8 + var17, (float)var9 - var18, 0.0);
                    var6.addVertex((float)var8 + var17, (float)var9 - var18 + 10.0f, 0.0);
                }
                var5.draw();
                var10 += var13.field_76332_a;
            }
            DecimalFormat var19 = new DecimalFormat("##0.00");
            GlStateManager.bindTexture();
            String var20 = "";
            if (!var4.field_76331_c.equals("unspecified")) {
                var20 = String.valueOf(var20) + "[0] ";
            }
            var20 = var4.field_76331_c.length() == 0 ? String.valueOf(var20) + "ROOT " : String.valueOf(var20) + var4.field_76331_c + " ";
            var14 = 16777215;
            this.fontRendererObj.func_175063_a(var20, var8 - var7, var9 - var7 / 2 - 16, var14);
            var20 = String.valueOf(var19.format(var4.field_76330_b)) + "%";
            this.fontRendererObj.func_175063_a(var20, var8 + var7 - this.fontRendererObj.getStringWidth(var20), var9 - var7 / 2 - 16, var14);
            for (int var21 = 0; var21 < var3.size(); ++var21) {
                Profiler.Result var22 = (Profiler.Result)var3.get(var21);
                String var23 = "";
                var23 = var22.field_76331_c.equals("unspecified") ? String.valueOf(var23) + "[?] " : String.valueOf(var23) + "[" + (var21 + 1) + "] ";
                var23 = String.valueOf(var23) + var22.field_76331_c;
                this.fontRendererObj.func_175063_a(var23, var8 - var7, var9 + var7 / 2 + var21 * 8 + 20, var22.func_76329_a());
                var23 = String.valueOf(var19.format(var22.field_76332_a)) + "%";
                this.fontRendererObj.func_175063_a(var23, var8 + var7 - 50 - this.fontRendererObj.getStringWidth(var23), var9 + var7 / 2 + var21 * 8 + 20, var22.func_76329_a());
                var23 = String.valueOf(var19.format(var22.field_76330_b)) + "%";
                this.fontRendererObj.func_175063_a(var23, var8 + var7 - this.fontRendererObj.getStringWidth(var23), var9 + var7 / 2 + var21 * 8 + 20, var22.func_76329_a());
            }
        }
    }

    public void shutdown() {
        running = false;
    }

    public void setIngameFocus() {
        if (Display.isActive() && !this.inGameHasFocus) {
            this.inGameHasFocus = true;
            this.mouseHelper.grabMouseCursor();
            this.displayGuiScreen(null);
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
        if (currentScreen == null) {
            this.displayGuiScreen(new GuiIngameMenu());
            if (this.isSingleplayer() && !this.theIntegratedServer.getPublic()) {
                this.mcSoundHandler.pauseSounds();
            }
        }
    }

    private void sendClickBlockToController(boolean leftClick) {
        if (!leftClick) {
            this.leftClickCounter = 0;
        }
        if (this.leftClickCounter <= 0 && !thePlayer.isUsingItem()) {
            if (leftClick && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                BlockPos var2 = this.objectMouseOver.func_178782_a();
                if (theWorld.getBlockState(var2).getBlock().getMaterial() != Material.air && playerController.func_180512_c(var2, this.objectMouseOver.field_178784_b)) {
                    this.effectRenderer.func_180532_a(var2, this.objectMouseOver.field_178784_b);
                    thePlayer.swingItem();
                }
            } else {
                playerController.resetBlockRemoving();
            }
        }
    }

    private void clickMouse() {
        if (this.leftClickCounter <= 0) {
            thePlayer.swingItem();
            if (this.objectMouseOver == null) {
                logger.error("Null returned as 'hitResult', this shouldn't happen!");
                if (playerController.isNotCreative()) {
                    this.leftClickCounter = 10;
                }
            } else {
                switch (this.objectMouseOver.typeOfHit) {
                    case ENTITY: {
                        playerController.attackEntity(thePlayer, this.objectMouseOver.entityHit);
                        break;
                    }
                    case BLOCK: {
                        BlockPos var1 = this.objectMouseOver.func_178782_a();
                        if (theWorld.getBlockState(var1).getBlock().getMaterial() != Material.air) {
                            playerController.func_180511_b(var1, this.objectMouseOver.field_178784_b);
                            break;
                        }
                    }
                    default: {
                        if (!playerController.isNotCreative()) break;
                        this.leftClickCounter = 10;
                    }
                }
            }
        }
    }

    private void rightClickMouse() {
        ItemStack var5;
        this.rightClickDelayTimer = 4;
        boolean var1 = true;
        ItemStack var2 = Minecraft.thePlayer.inventory.getCurrentItem();
        if (this.objectMouseOver == null) {
            logger.warn("Null returned as 'hitResult', this shouldn't happen!");
        } else {
            switch (this.objectMouseOver.typeOfHit) {
                case ENTITY: {
                    if (playerController.func_178894_a(thePlayer, this.objectMouseOver.entityHit, this.objectMouseOver)) {
                        var1 = false;
                        break;
                    }
                    if (!playerController.interactWithEntitySendPacket(thePlayer, this.objectMouseOver.entityHit)) break;
                    var1 = false;
                    break;
                }
                case BLOCK: {
                    int var4;
                    BlockPos var3 = this.objectMouseOver.func_178782_a();
                    if (theWorld.getBlockState(var3).getBlock().getMaterial() == Material.air) break;
                    int n = var4 = var2 != null ? var2.stackSize : 0;
                    if (playerController.func_178890_a(thePlayer, theWorld, var2, var3, this.objectMouseOver.field_178784_b, this.objectMouseOver.hitVec)) {
                        var1 = false;
                        thePlayer.swingItem();
                    }
                    if (var2 == null) {
                        return;
                    }
                    if (var2.stackSize == 0) {
                        Minecraft.thePlayer.inventory.mainInventory[Minecraft.thePlayer.inventory.currentItem] = null;
                        break;
                    }
                    if (var2.stackSize == var4 && !playerController.isInCreativeMode()) break;
                    this.entityRenderer.itemRenderer.resetEquippedProgress();
                }
            }
        }
        if (var1 && (var5 = Minecraft.thePlayer.inventory.getCurrentItem()) != null && playerController.sendUseItem(thePlayer, theWorld, var5)) {
            this.entityRenderer.itemRenderer.resetEquippedProgress2();
        }
    }

    public void toggleFullscreen() {
        try {
            this.gameSettings.fullScreen = this.fullscreen = !this.fullscreen;
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
                Display.setDisplayMode((DisplayMode)new DisplayMode(this.tempDisplayWidth, this.tempDisplayHeight));
                this.displayWidth = this.tempDisplayWidth;
                this.displayHeight = this.tempDisplayHeight;
                if (this.displayWidth <= 0) {
                    this.displayWidth = 1;
                }
                if (this.displayHeight <= 0) {
                    this.displayHeight = 1;
                }
            }
            if (currentScreen != null) {
                this.resize(this.displayWidth, this.displayHeight);
            } else {
                this.updateFramebufferSize();
            }
            Display.setFullscreen((boolean)this.fullscreen);
            Display.setVSyncEnabled((boolean)this.gameSettings.enableVsync);
            this.func_175601_h();
        }
        catch (Exception var2) {
            logger.error("Couldn't toggle fullscreen", (Throwable)var2);
        }
    }

    private void resize(int width, int height) {
        this.displayWidth = Math.max(1, width);
        this.displayHeight = Math.max(1, height);
        if (currentScreen != null) {
            ScaledResolution var3 = new ScaledResolution(this, width, height);
            currentScreen.func_175273_b(this, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight());
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

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void runTick() throws IOException {
        block94 : {
            block95 : {
                e = new TickEvent();
                e.call();
                if (this.rightClickDelayTimer > 0) {
                    --this.rightClickDelayTimer;
                }
                this.mcProfiler.startSection("gui");
                if (!this.isGamePaused) {
                    this.ingameGUI.updateTick();
                }
                this.mcProfiler.endSection();
                this.entityRenderer.getMouseOver(1.0f);
                this.mcProfiler.startSection("gameMode");
                if (!this.isGamePaused && Minecraft.theWorld != null) {
                    Minecraft.playerController.updateController();
                }
                this.mcProfiler.endStartSection("textures");
                if (!this.isGamePaused) {
                    this.renderEngine.tick();
                }
                if (Minecraft.currentScreen == null && Minecraft.thePlayer != null) {
                    if (Minecraft.thePlayer.getHealth() <= 0.0f) {
                        this.displayGuiScreen(null);
                    } else if (Minecraft.thePlayer.isPlayerSleeping() && Minecraft.theWorld != null) {
                        this.displayGuiScreen(new GuiSleepMP());
                    }
                } else if (Minecraft.currentScreen != null && Minecraft.currentScreen instanceof GuiSleepMP && !Minecraft.thePlayer.isPlayerSleeping()) {
                    this.displayGuiScreen(null);
                }
                if (Minecraft.currentScreen != null) {
                    this.leftClickCounter = 10000;
                }
                if (Minecraft.currentScreen != null) {
                    try {
                        Minecraft.currentScreen.handleInput();
                    }
                    catch (Throwable var7) {
                        var2 = CrashReport.makeCrashReport(var7, "Updating screen events");
                        var3 = var2.makeCategory("Affected screen");
                        var3.addCrashSectionCallable("Screen name", new Callable(){
                            private static final String __OBFID = "CL_00000640";

                            public String call() {
                                return currentScreen.getClass().getCanonicalName();
                            }
                        });
                        throw new ReportedException(var2);
                    }
                    if (Minecraft.currentScreen != null) {
                        try {
                            Minecraft.currentScreen.updateScreen();
                        }
                        catch (Throwable var6) {
                            var2 = CrashReport.makeCrashReport(var6, "Ticking screen");
                            var3 = var2.makeCategory("Affected screen");
                            var3.addCrashSectionCallable("Screen name", new Callable(){
                                private static final String __OBFID = "CL_00000642";

                                public String call() {
                                    return currentScreen.getClass().getCanonicalName();
                                }
                            });
                            throw new ReportedException(var2);
                        }
                    }
                }
                if (Minecraft.currentScreen != null && !Minecraft.currentScreen.allowUserInput) break block94;
                this.mcProfiler.endStartSection("mouse");
                while (Mouse.next()) {
                    var1 = Mouse.getEventButton();
                    KeyBinding.setKeyBindState(var1 - 100, Mouse.getEventButtonState());
                    if (Mouse.getEventButtonState()) {
                        eventMouse = new MouseEvent(var1);
                        eventMouse.call();
                        if (Minecraft.thePlayer.func_175149_v() && var1 == 2) {
                            this.ingameGUI.func_175187_g().func_175261_b();
                        } else {
                            KeyBinding.onTick(var1 - 100);
                        }
                    }
                    if ((var10 = Minecraft.getSystemTime() - this.systemTime) > 200L) continue;
                    var4 = Mouse.getEventDWheel();
                    if (var4 != 0) {
                        if (Minecraft.thePlayer.func_175149_v()) {
                            v0 = var4 = var4 < 0 ? -1 : 1;
                            if (this.ingameGUI.func_175187_g().func_175262_a()) {
                                this.ingameGUI.func_175187_g().func_175259_b(-var4);
                            } else {
                                var5 = MathHelper.clamp_float(Minecraft.thePlayer.capabilities.getFlySpeed() + (float)var4 * 0.005f, 0.0f, 0.2f);
                                Minecraft.thePlayer.capabilities.setFlySpeed(var5);
                            }
                        } else {
                            Minecraft.thePlayer.inventory.changeCurrentItem(var4);
                        }
                    }
                    if (Minecraft.currentScreen == null) {
                        if (this.inGameHasFocus || !Mouse.getEventButtonState()) continue;
                        this.setIngameFocus();
                        continue;
                    }
                    if (Minecraft.currentScreen == null) continue;
                    Minecraft.currentScreen.handleMouseInput();
                }
                if (this.leftClickCounter > 0) {
                    --this.leftClickCounter;
                }
                this.mcProfiler.endStartSection("keyboard");
                while (Keyboard.next()) {
                    var1 = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();
                    KeyBinding.setKeyBindState(var1, Keyboard.getEventKeyState());
                    if (Keyboard.getEventKeyState()) {
                        new KeyPressEvent(var1).call();
                        KeyBinding.onTick(var1);
                    }
                    if (this.debugCrashKeyPressTime > 0L) {
                        if (Minecraft.getSystemTime() - this.debugCrashKeyPressTime >= 6000L) {
                            throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
                        }
                        if (!Keyboard.isKeyDown((int)46) || !Keyboard.isKeyDown((int)61)) {
                            this.debugCrashKeyPressTime = -1L;
                        }
                    } else if (Keyboard.isKeyDown((int)46) && Keyboard.isKeyDown((int)61)) {
                        this.debugCrashKeyPressTime = Minecraft.getSystemTime();
                    }
                    this.dispatchKeypresses();
                    if (!Keyboard.getEventKeyState()) continue;
                    if (var1 == 62 && this.entityRenderer != null) {
                        this.entityRenderer.func_175071_c();
                    }
                    if (Minecraft.currentScreen != null) {
                        Minecraft.currentScreen.handleKeyboardInput();
                    } else {
                        if (var1 == 1) {
                            this.displayInGameMenu();
                        }
                        if (var1 == 32 && Keyboard.isKeyDown((int)61) && this.ingameGUI != null) {
                            this.ingameGUI.getChatGUI().clearChatMessages();
                        }
                        if (var1 == 31 && Keyboard.isKeyDown((int)61)) {
                            this.refreshResources();
                        }
                        if (var1 != 17 || Keyboard.isKeyDown((int)61)) {
                            // empty if block
                        }
                        if (var1 != 18 || Keyboard.isKeyDown((int)61)) {
                            // empty if block
                        }
                        if (var1 != 47 || Keyboard.isKeyDown((int)61)) {
                            // empty if block
                        }
                        if (var1 != 38 || Keyboard.isKeyDown((int)61)) {
                            // empty if block
                        }
                        if (var1 != 22 || Keyboard.isKeyDown((int)61)) {
                            // empty if block
                        }
                        if (var1 == 20 && Keyboard.isKeyDown((int)61)) {
                            this.refreshResources();
                        }
                        if (var1 == 33 && Keyboard.isKeyDown((int)61)) {
                            var11 = Keyboard.isKeyDown((int)42) | Keyboard.isKeyDown((int)54);
                            this.gameSettings.setOptionValue(GameSettings.Options.RENDER_DISTANCE, var11 != false ? -1 : 1);
                        }
                        if (var1 == 30 && Keyboard.isKeyDown((int)61)) {
                            Minecraft.renderGlobal.loadRenderers();
                        }
                        if (var1 == 35 && Keyboard.isKeyDown((int)61)) {
                            this.gameSettings.advancedItemTooltips = this.gameSettings.advancedItemTooltips == false;
                            this.gameSettings.saveOptions();
                        }
                        if (var1 == 48 && Keyboard.isKeyDown((int)61)) {
                            Minecraft.renderManager.func_178629_b(Minecraft.renderManager.func_178634_b() == false);
                        }
                        if (var1 == 25 && Keyboard.isKeyDown((int)61)) {
                            this.gameSettings.pauseOnLostFocus = this.gameSettings.pauseOnLostFocus == false;
                            this.gameSettings.saveOptions();
                        }
                        if (var1 == 59) {
                            v1 = this.gameSettings.hideGUI = this.gameSettings.hideGUI == false;
                        }
                        if (var1 == 61) {
                            this.gameSettings.showDebugInfo = this.gameSettings.showDebugInfo == false;
                            this.gameSettings.showDebugProfilerChart = GuiScreen.isShiftKeyDown();
                        }
                        if (this.gameSettings.keyBindTogglePerspective.isPressed()) {
                            ++this.gameSettings.thirdPersonView;
                            if (this.gameSettings.thirdPersonView > 2) {
                                this.gameSettings.thirdPersonView = 0;
                            }
                            if (this.gameSettings.thirdPersonView == 0) {
                                this.entityRenderer.func_175066_a(this.func_175606_aa());
                            } else if (this.gameSettings.thirdPersonView == 1) {
                                this.entityRenderer.func_175066_a(null);
                            }
                        }
                        if (this.gameSettings.keyBindSmoothCamera.isPressed()) {
                            v2 = this.gameSettings.smoothCamera = this.gameSettings.smoothCamera == false;
                        }
                    }
                    if (!this.gameSettings.showDebugInfo || !this.gameSettings.showDebugProfilerChart) continue;
                    if (var1 == 11) {
                        this.updateDebugProfilerName(0);
                    }
                    for (var12 = 0; var12 < 9; ++var12) {
                        if (var1 != 2 + var12) continue;
                        this.updateDebugProfilerName(var12 + 1);
                    }
                }
                for (var1 = 0; var1 < 9; ++var1) {
                    if (!this.gameSettings.keyBindsHotbar[var1].isPressed()) continue;
                    if (Minecraft.thePlayer.func_175149_v()) {
                        this.ingameGUI.func_175187_g().func_175260_a(var1);
                        continue;
                    }
                    Minecraft.thePlayer.inventory.currentItem = var1;
                }
                var9 = this.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN;
                while (this.gameSettings.keyBindInventory.isPressed()) {
                    if (Minecraft.playerController.isRidingHorse()) {
                        Minecraft.thePlayer.func_175163_u();
                        continue;
                    }
                    Minecraft.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                    this.displayGuiScreen(new GuiInventory(Minecraft.thePlayer));
                }
                while (this.gameSettings.keyBindDrop.isPressed()) {
                    if (Minecraft.thePlayer.func_175149_v()) continue;
                    Minecraft.thePlayer.dropOneItem(GuiScreen.isCtrlKeyDown());
                }
                while (this.gameSettings.keyBindChat.isPressed() && var9) {
                    this.displayGuiScreen(new GuiChat());
                }
                if (Minecraft.currentScreen == null && this.gameSettings.keyBindCommand.isPressed() && var9) {
                    this.displayGuiScreen(new GuiChat("/"));
                }
                if (!Minecraft.thePlayer.isUsingItem()) ** GOTO lbl194
                if (!this.gameSettings.keyBindUseItem.getIsKeyPressed()) {
                    Minecraft.playerController.onStoppedUsingItem(Minecraft.thePlayer);
                }
                while (this.gameSettings.keyBindAttack.isPressed()) {
                }
                while (this.gameSettings.keyBindUseItem.isPressed()) {
                }
                while (this.gameSettings.keyBindPickBlock.isPressed()) {
                }
                break block95;
lbl-1000: // 1 sources:
                {
                    this.clickMouse();
lbl194: // 2 sources:
                    ** while (this.gameSettings.keyBindAttack.isPressed())
                }
lbl195: // 2 sources:
                while (this.gameSettings.keyBindUseItem.isPressed()) {
                    this.rightClickMouse();
                }
                while (this.gameSettings.keyBindPickBlock.isPressed()) {
                    this.middleClickMouse();
                }
            }
            if (this.gameSettings.keyBindUseItem.getIsKeyPressed() && this.rightClickDelayTimer == 0 && !Minecraft.thePlayer.isUsingItem()) {
                this.rightClickMouse();
            }
            this.sendClickBlockToController(Minecraft.currentScreen == null && this.gameSettings.keyBindAttack.getIsKeyPressed() != false && this.inGameHasFocus != false);
        }
        if (Minecraft.theWorld != null) {
            if (Minecraft.thePlayer != null) {
                ++this.joinPlayerCounter;
                if (this.joinPlayerCounter == 30) {
                    this.joinPlayerCounter = 0;
                    Minecraft.theWorld.joinEntityInSurroundings(Minecraft.thePlayer);
                }
            }
            this.mcProfiler.endStartSection("gameRenderer");
            if (!this.isGamePaused) {
                this.entityRenderer.updateRenderer();
            }
            this.mcProfiler.endStartSection("levelRenderer");
            if (!this.isGamePaused) {
                Minecraft.renderGlobal.updateClouds();
            }
            this.mcProfiler.endStartSection("level");
            if (!this.isGamePaused) {
                if (Minecraft.theWorld.func_175658_ac() > 0) {
                    Minecraft.theWorld.setLastLightningBolt(Minecraft.theWorld.func_175658_ac() - 1);
                }
                Minecraft.theWorld.updateEntities();
            }
        }
        if (!this.isGamePaused) {
            this.mcMusicTicker.update();
            this.mcSoundHandler.update();
        }
        if (Minecraft.theWorld != null) {
            if (!this.isGamePaused) {
                Minecraft.theWorld.setAllowedSpawnTypes(Minecraft.theWorld.getDifficulty() != EnumDifficulty.PEACEFUL, true);
                try {
                    Minecraft.theWorld.tick();
                }
                catch (Throwable var8) {
                    var2 = CrashReport.makeCrashReport(var8, "Exception in world tick");
                    if (Minecraft.theWorld == null) {
                        var3 = var2.makeCategory("Affected level");
                        var3.addCrashSection("Problem", "Level is null!");
                        throw new ReportedException(var2);
                    }
                    Minecraft.theWorld.addWorldInfoToCrashReport(var2);
                    throw new ReportedException(var2);
                }
            }
            this.mcProfiler.endStartSection("animateTick");
            if (!this.isGamePaused && Minecraft.theWorld != null) {
                Minecraft.theWorld.doVoidFogParticles(MathHelper.floor_double(Minecraft.thePlayer.posX), MathHelper.floor_double(Minecraft.thePlayer.posY), MathHelper.floor_double(Minecraft.thePlayer.posZ));
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
        this.systemTime = Minecraft.getSystemTime();
    }

    public void launchIntegratedServer(String folderName, String worldName, WorldSettings worldSettingsIn) {
        this.loadWorld(null);
        System.gc();
        ISaveHandler var4 = this.saveLoader.getSaveLoader(folderName, false);
        WorldInfo var5 = var4.loadWorldInfo();
        if (var5 == null && worldSettingsIn != null) {
            var5 = new WorldInfo(worldSettingsIn, folderName);
            var4.saveWorldInfo(var5);
        }
        if (worldSettingsIn == null) {
            worldSettingsIn = new WorldSettings(var5);
        }
        try {
            this.theIntegratedServer = new IntegratedServer(this, folderName, worldName, worldSettingsIn);
            this.theIntegratedServer.startServerThread();
            this.integratedServerIsRunning = true;
        }
        catch (Throwable var10) {
            CrashReport var7 = CrashReport.makeCrashReport(var10, "Starting integrated server");
            CrashReportCategory var8 = var7.makeCategory("Starting integrated server");
            var8.addCrashSection("Level ID", folderName);
            var8.addCrashSection("Level Name", worldName);
            throw new ReportedException(var7);
        }
        this.loadingScreen.displaySavingString(I18n.format("menu.loadingLevel", new Object[0]));
        while (!this.theIntegratedServer.serverIsInRunLoop()) {
            String var6 = this.theIntegratedServer.getUserMessage();
            if (var6 != null) {
                this.loadingScreen.displayLoadingString(I18n.format(var6, new Object[0]));
            } else {
                this.loadingScreen.displayLoadingString("");
            }
            try {
                Thread.sleep(200L);
            }
            catch (InterruptedException var7) {
                // empty catch block
            }
        }
        this.displayGuiScreen(null);
        SocketAddress var11 = this.theIntegratedServer.getNetworkSystem().addLocalEndpoint();
        NetworkManager var12 = NetworkManager.provideLocalClient(var11);
        var12.setNetHandler(new NetHandlerLoginClient(var12, this, null));
        var12.sendPacket(new C00Handshake(47, var11.toString(), 0, EnumConnectionState.LOGIN));
        var12.sendPacket(new C00PacketLoginStart(this.getSession().getProfile()));
        this.myNetworkManager = var12;
    }

    public void loadWorld(WorldClient worldClientIn) {
        this.loadWorld(worldClientIn, "");
    }

    public void loadWorld(WorldClient worldClientIn, String loadingMessage) {
        if (worldClientIn == null) {
            NetHandlerPlayClient var3 = Minecraft.getNetHandler();
            if (var3 != null) {
                var3.cleanup();
            }
            if (this.theIntegratedServer != null && this.theIntegratedServer.func_175578_N()) {
                this.theIntegratedServer.initiateShutdown();
                this.theIntegratedServer.func_175592_a();
            }
            this.theIntegratedServer = null;
            this.guiAchievement.clearAchievements();
            this.entityRenderer.getMapItemRenderer().func_148249_a();
        }
        this.field_175622_Z = null;
        this.myNetworkManager = null;
        if (this.loadingScreen != null) {
            this.loadingScreen.resetProgressAndMessage(loadingMessage);
            this.loadingScreen.displayLoadingString("");
        }
        if (worldClientIn == null && theWorld != null) {
            if (this.mcResourcePackRepository.getResourcePackInstance() != null) {
                this.mcResourcePackRepository.func_148529_f();
                this.func_175603_A();
            }
            this.setServerData(null);
            this.integratedServerIsRunning = false;
        }
        this.mcSoundHandler.stopSounds();
        theWorld = worldClientIn;
        if (worldClientIn != null) {
            if (renderGlobal != null) {
                renderGlobal.setWorldAndLoadRenderers(worldClientIn);
            }
            if (this.effectRenderer != null) {
                this.effectRenderer.clearEffects(worldClientIn);
            }
            if (thePlayer == null) {
                thePlayer = playerController.func_178892_a(worldClientIn, new StatFileWriter());
                playerController.flipPlayer(thePlayer);
            }
            thePlayer.preparePlayerToSpawn();
            worldClientIn.spawnEntityInWorld(thePlayer);
            Minecraft.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
            playerController.setPlayerCapabilities(thePlayer);
            this.field_175622_Z = thePlayer;
        } else {
            this.saveLoader.flushCache();
            thePlayer = null;
        }
        System.gc();
        this.systemTime = 0L;
    }

    public void setDimensionAndSpawnPlayer(int dimension) {
        theWorld.setInitialSpawnLocation();
        theWorld.removeAllEntities();
        int var2 = 0;
        String var3 = null;
        if (thePlayer != null) {
            var2 = thePlayer.getEntityId();
            theWorld.removeEntity(thePlayer);
            var3 = thePlayer.getClientBrand();
        }
        this.field_175622_Z = null;
        EntityPlayerSP var4 = thePlayer;
        thePlayer = playerController.func_178892_a(theWorld, thePlayer == null ? new StatFileWriter() : thePlayer.getStatFileWriter());
        thePlayer.getDataWatcher().updateWatchedObjectsFromList(var4.getDataWatcher().getAllWatched());
        Minecraft.thePlayer.dimension = dimension;
        this.field_175622_Z = thePlayer;
        thePlayer.preparePlayerToSpawn();
        thePlayer.func_175158_f(var3);
        theWorld.spawnEntityInWorld(thePlayer);
        playerController.flipPlayer(thePlayer);
        Minecraft.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
        thePlayer.setEntityId(var2);
        playerController.setPlayerCapabilities(thePlayer);
        thePlayer.func_175150_k(var4.func_175140_cp());
        if (currentScreen instanceof GuiGameOver) {
            this.displayGuiScreen(null);
        }
    }

    public final boolean isDemo() {
        return this.isDemo;
    }

    public static NetHandlerPlayClient getNetHandler() {
        return thePlayer != null ? Minecraft.thePlayer.sendQueue : null;
    }

    public static boolean isGuiEnabled() {
        return theMinecraft == null || !Minecraft.theMinecraft.gameSettings.hideGUI;
    }

    public static boolean isFancyGraphicsEnabled() {
        return theMinecraft != null && Minecraft.theMinecraft.gameSettings.fancyGraphics;
    }

    public static boolean isAmbientOcclusionEnabled() {
        return theMinecraft != null && Minecraft.theMinecraft.gameSettings.ambientOcclusion != 0;
    }

    private void middleClickMouse() {
        if (this.objectMouseOver != null) {
            Item var2;
            boolean var1 = Minecraft.thePlayer.capabilities.isCreativeMode;
            int var3 = 0;
            boolean var4 = false;
            TileEntity var5 = null;
            if (this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                BlockPos var6 = this.objectMouseOver.func_178782_a();
                Block var7 = theWorld.getBlockState(var6).getBlock();
                if (var7.getMaterial() == Material.air) {
                    return;
                }
                var2 = var7.getItem(theWorld, var6);
                if (var2 == null) {
                    return;
                }
                if (var1 && GuiScreen.isCtrlKeyDown()) {
                    var5 = theWorld.getTileEntity(var6);
                }
                Block var8 = var2 instanceof ItemBlock && !var7.isFlowerPot() ? Block.getBlockFromItem(var2) : var7;
                var3 = var8.getDamageValue(theWorld, var6);
                var4 = var2.getHasSubtypes();
            } else {
                if (this.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY || this.objectMouseOver.entityHit == null || !var1) {
                    return;
                }
                if (this.objectMouseOver.entityHit instanceof EntityPainting) {
                    var2 = Items.painting;
                } else if (this.objectMouseOver.entityHit instanceof EntityLeashKnot) {
                    var2 = Items.lead;
                } else if (this.objectMouseOver.entityHit instanceof EntityItemFrame) {
                    EntityItemFrame var11 = (EntityItemFrame)this.objectMouseOver.entityHit;
                    ItemStack var14 = var11.getDisplayedItem();
                    if (var14 == null) {
                        var2 = Items.item_frame;
                    } else {
                        var2 = var14.getItem();
                        var3 = var14.getMetadata();
                        var4 = true;
                    }
                } else if (this.objectMouseOver.entityHit instanceof EntityMinecart) {
                    EntityMinecart var12 = (EntityMinecart)this.objectMouseOver.entityHit;
                    switch (var12.func_180456_s()) {
                        case FURNACE: {
                            var2 = Items.furnace_minecart;
                            break;
                        }
                        case CHEST: {
                            var2 = Items.chest_minecart;
                            break;
                        }
                        case TNT: {
                            var2 = Items.tnt_minecart;
                            break;
                        }
                        case HOPPER: {
                            var2 = Items.hopper_minecart;
                            break;
                        }
                        case COMMAND_BLOCK: {
                            var2 = Items.command_block_minecart;
                            break;
                        }
                        default: {
                            var2 = Items.minecart;
                            break;
                        }
                    }
                } else if (this.objectMouseOver.entityHit instanceof EntityBoat) {
                    var2 = Items.boat;
                } else if (this.objectMouseOver.entityHit instanceof EntityArmorStand) {
                    var2 = Items.armor_stand;
                } else {
                    var2 = Items.spawn_egg;
                    var3 = EntityList.getEntityID(this.objectMouseOver.entityHit);
                    var4 = true;
                    if (!EntityList.entityEggs.containsKey(var3)) {
                        return;
                    }
                }
            }
            InventoryPlayer var13 = Minecraft.thePlayer.inventory;
            if (var5 == null) {
                var13.setCurrentItem(var2, var3, var4, var1);
            } else {
                NBTTagCompound var15 = new NBTTagCompound();
                var5.writeToNBT(var15);
                ItemStack var17 = new ItemStack(var2, 1, var3);
                var17.setTagInfo("BlockEntityTag", var15);
                NBTTagCompound var9 = new NBTTagCompound();
                NBTTagList var10 = new NBTTagList();
                var10.appendTag(new NBTTagString("(+NBT)"));
                var9.setTag("Lore", var10);
                var17.setTagInfo("display", var9);
                var13.setInventorySlotContents(var13.currentItem, var17);
            }
            if (var1) {
                int var16 = Minecraft.thePlayer.inventoryContainer.inventorySlots.size() - 9 + var13.currentItem;
                playerController.sendSlotPacket(var13.getStackInSlot(var13.currentItem), var16);
            }
        }
    }

    public CrashReport addGraphicsAndWorldToCrashReport(CrashReport theCrash) {
        theCrash.getCategory().addCrashSectionCallable("Launched Version", new Callable(){
            private static final String __OBFID = "CL_00000643";

            public String call() {
                return Minecraft.this.launchedVersion;
            }
        });
        theCrash.getCategory().addCrashSectionCallable("LWJGL", new Callable(){
            private static final String __OBFID = "CL_00000644";

            public String call() {
                return Sys.getVersion();
            }
        });
        theCrash.getCategory().addCrashSectionCallable("OpenGL", new Callable(){
            private static final String __OBFID = "CL_00000645";

            public String call() {
                return String.valueOf(GL11.glGetString((int)7937)) + " GL version " + GL11.glGetString((int)7938) + ", " + GL11.glGetString((int)7936);
            }
        });
        theCrash.getCategory().addCrashSectionCallable("GL Caps", new Callable(){
            private static final String __OBFID = "CL_00000646";

            public String call() {
                return OpenGlHelper.func_153172_c();
            }
        });
        theCrash.getCategory().addCrashSectionCallable("Using VBOs", new Callable(){
            private static final String __OBFID = "CL_00000647";

            public String call() {
                return Minecraft.this.gameSettings.field_178881_t ? "Yes" : "No";
            }
        });
        theCrash.getCategory().addCrashSectionCallable("Is Modded", new Callable(){
            private static final String __OBFID = "CL_00000633";

            public String call() {
                String var1 = ClientBrandRetriever.getClientModName();
                return !var1.equals("vanilla") ? "Definitely; Client brand changed to '" + var1 + "'" : (Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.");
            }
        });
        theCrash.getCategory().addCrashSectionCallable("Type", new Callable(){
            private static final String __OBFID = "CL_00000634";

            public String call() {
                return "Client (map_client.txt)";
            }
        });
        theCrash.getCategory().addCrashSectionCallable("Resource Packs", new Callable(){
            private static final String __OBFID = "CL_00000635";

            public String call() {
                return Minecraft.this.gameSettings.resourcePacks.toString();
            }
        });
        theCrash.getCategory().addCrashSectionCallable("Current Language", new Callable(){
            private static final String __OBFID = "CL_00000636";

            public String call() {
                return Minecraft.this.mcLanguageManager.getCurrentLanguage().toString();
            }
        });
        theCrash.getCategory().addCrashSectionCallable("Profiler Position", new Callable(){
            private static final String __OBFID = "CL_00000637";

            public String call() {
                return Minecraft.this.mcProfiler.profilingEnabled ? Minecraft.this.mcProfiler.getNameOfLastSection() : "N/A (disabled)";
            }
        });
        if (theWorld != null) {
            theWorld.addWorldInfoToCrashReport(theCrash);
        }
        return theCrash;
    }

    public static Minecraft getMinecraft() {
        return theMinecraft;
    }

    public ListenableFuture func_175603_A() {
        return this.addScheduledTask(new Runnable(){
            private static final String __OBFID = "CL_00001853";

            @Override
            public void run() {
                Minecraft.this.refreshResources();
            }
        });
    }

    @Override
    public void addServerStatsToSnooper(PlayerUsageSnooper playerSnooper) {
        playerSnooper.addClientStat("fps", debugFPS);
        playerSnooper.addClientStat("vsync_enabled", this.gameSettings.enableVsync);
        playerSnooper.addClientStat("display_frequency", Display.getDisplayMode().getFrequency());
        playerSnooper.addClientStat("display_type", this.fullscreen ? "fullscreen" : "windowed");
        playerSnooper.addClientStat("run_time", (MinecraftServer.getCurrentTimeMillis() - playerSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L);
        String var2 = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? "little" : "big";
        playerSnooper.addClientStat("endianness", var2);
        playerSnooper.addClientStat("resource_packs", this.mcResourcePackRepository.getRepositoryEntries().size());
        int var3 = 0;
        for (ResourcePackRepository.Entry var5 : this.mcResourcePackRepository.getRepositoryEntries()) {
            playerSnooper.addClientStat("resource_pack[" + var3++ + "]", var5.getResourcePackName());
        }
        if (this.theIntegratedServer != null && this.theIntegratedServer.getPlayerUsageSnooper() != null) {
            playerSnooper.addClientStat("snooper_partner", this.theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
        }
    }

    @Override
    public void addServerTypeToSnooper(PlayerUsageSnooper playerSnooper) {
        playerSnooper.addStatToSnooper("opengl_version", GL11.glGetString((int)7938));
        playerSnooper.addStatToSnooper("opengl_vendor", GL11.glGetString((int)7936));
        playerSnooper.addStatToSnooper("client_brand", ClientBrandRetriever.getClientModName());
        playerSnooper.addStatToSnooper("launched_version", this.launchedVersion);
        ContextCapabilities var2 = GLContext.getCapabilities();
        playerSnooper.addStatToSnooper("gl_caps[ARB_arrays_of_arrays]", var2.GL_ARB_arrays_of_arrays);
        playerSnooper.addStatToSnooper("gl_caps[ARB_base_instance]", var2.GL_ARB_base_instance);
        playerSnooper.addStatToSnooper("gl_caps[ARB_blend_func_extended]", var2.GL_ARB_blend_func_extended);
        playerSnooper.addStatToSnooper("gl_caps[ARB_clear_buffer_object]", var2.GL_ARB_clear_buffer_object);
        playerSnooper.addStatToSnooper("gl_caps[ARB_color_buffer_float]", var2.GL_ARB_color_buffer_float);
        playerSnooper.addStatToSnooper("gl_caps[ARB_compatibility]", var2.GL_ARB_compatibility);
        playerSnooper.addStatToSnooper("gl_caps[ARB_compressed_texture_pixel_storage]", var2.GL_ARB_compressed_texture_pixel_storage);
        playerSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", var2.GL_ARB_compute_shader);
        playerSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", var2.GL_ARB_copy_buffer);
        playerSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", var2.GL_ARB_copy_image);
        playerSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", var2.GL_ARB_depth_buffer_float);
        playerSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", var2.GL_ARB_compute_shader);
        playerSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", var2.GL_ARB_copy_buffer);
        playerSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", var2.GL_ARB_copy_image);
        playerSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", var2.GL_ARB_depth_buffer_float);
        playerSnooper.addStatToSnooper("gl_caps[ARB_depth_clamp]", var2.GL_ARB_depth_clamp);
        playerSnooper.addStatToSnooper("gl_caps[ARB_depth_texture]", var2.GL_ARB_depth_texture);
        playerSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers]", var2.GL_ARB_draw_buffers);
        playerSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers_blend]", var2.GL_ARB_draw_buffers_blend);
        playerSnooper.addStatToSnooper("gl_caps[ARB_draw_elements_base_vertex]", var2.GL_ARB_draw_elements_base_vertex);
        playerSnooper.addStatToSnooper("gl_caps[ARB_draw_indirect]", var2.GL_ARB_draw_indirect);
        playerSnooper.addStatToSnooper("gl_caps[ARB_draw_instanced]", var2.GL_ARB_draw_instanced);
        playerSnooper.addStatToSnooper("gl_caps[ARB_explicit_attrib_location]", var2.GL_ARB_explicit_attrib_location);
        playerSnooper.addStatToSnooper("gl_caps[ARB_explicit_uniform_location]", var2.GL_ARB_explicit_uniform_location);
        playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_layer_viewport]", var2.GL_ARB_fragment_layer_viewport);
        playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_program]", var2.GL_ARB_fragment_program);
        playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_shader]", var2.GL_ARB_fragment_shader);
        playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_program_shadow]", var2.GL_ARB_fragment_program_shadow);
        playerSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_object]", var2.GL_ARB_framebuffer_object);
        playerSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_sRGB]", var2.GL_ARB_framebuffer_sRGB);
        playerSnooper.addStatToSnooper("gl_caps[ARB_geometry_shader4]", var2.GL_ARB_geometry_shader4);
        playerSnooper.addStatToSnooper("gl_caps[ARB_gpu_shader5]", var2.GL_ARB_gpu_shader5);
        playerSnooper.addStatToSnooper("gl_caps[ARB_half_float_pixel]", var2.GL_ARB_half_float_pixel);
        playerSnooper.addStatToSnooper("gl_caps[ARB_half_float_vertex]", var2.GL_ARB_half_float_vertex);
        playerSnooper.addStatToSnooper("gl_caps[ARB_instanced_arrays]", var2.GL_ARB_instanced_arrays);
        playerSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_alignment]", var2.GL_ARB_map_buffer_alignment);
        playerSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_range]", var2.GL_ARB_map_buffer_range);
        playerSnooper.addStatToSnooper("gl_caps[ARB_multisample]", var2.GL_ARB_multisample);
        playerSnooper.addStatToSnooper("gl_caps[ARB_multitexture]", var2.GL_ARB_multitexture);
        playerSnooper.addStatToSnooper("gl_caps[ARB_occlusion_query2]", var2.GL_ARB_occlusion_query2);
        playerSnooper.addStatToSnooper("gl_caps[ARB_pixel_buffer_object]", var2.GL_ARB_pixel_buffer_object);
        playerSnooper.addStatToSnooper("gl_caps[ARB_seamless_cube_map]", var2.GL_ARB_seamless_cube_map);
        playerSnooper.addStatToSnooper("gl_caps[ARB_shader_objects]", var2.GL_ARB_shader_objects);
        playerSnooper.addStatToSnooper("gl_caps[ARB_shader_stencil_export]", var2.GL_ARB_shader_stencil_export);
        playerSnooper.addStatToSnooper("gl_caps[ARB_shader_texture_lod]", var2.GL_ARB_shader_texture_lod);
        playerSnooper.addStatToSnooper("gl_caps[ARB_shadow]", var2.GL_ARB_shadow);
        playerSnooper.addStatToSnooper("gl_caps[ARB_shadow_ambient]", var2.GL_ARB_shadow_ambient);
        playerSnooper.addStatToSnooper("gl_caps[ARB_stencil_texturing]", var2.GL_ARB_stencil_texturing);
        playerSnooper.addStatToSnooper("gl_caps[ARB_sync]", var2.GL_ARB_sync);
        playerSnooper.addStatToSnooper("gl_caps[ARB_tessellation_shader]", var2.GL_ARB_tessellation_shader);
        playerSnooper.addStatToSnooper("gl_caps[ARB_texture_border_clamp]", var2.GL_ARB_texture_border_clamp);
        playerSnooper.addStatToSnooper("gl_caps[ARB_texture_buffer_object]", var2.GL_ARB_texture_buffer_object);
        playerSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map]", var2.GL_ARB_texture_cube_map);
        playerSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map_array]", var2.GL_ARB_texture_cube_map_array);
        playerSnooper.addStatToSnooper("gl_caps[ARB_texture_non_power_of_two]", var2.GL_ARB_texture_non_power_of_two);
        playerSnooper.addStatToSnooper("gl_caps[ARB_uniform_buffer_object]", var2.GL_ARB_uniform_buffer_object);
        playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_blend]", var2.GL_ARB_vertex_blend);
        playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_buffer_object]", var2.GL_ARB_vertex_buffer_object);
        playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_program]", var2.GL_ARB_vertex_program);
        playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_shader]", var2.GL_ARB_vertex_shader);
        playerSnooper.addStatToSnooper("gl_caps[EXT_bindable_uniform]", var2.GL_EXT_bindable_uniform);
        playerSnooper.addStatToSnooper("gl_caps[EXT_blend_equation_separate]", var2.GL_EXT_blend_equation_separate);
        playerSnooper.addStatToSnooper("gl_caps[EXT_blend_func_separate]", var2.GL_EXT_blend_func_separate);
        playerSnooper.addStatToSnooper("gl_caps[EXT_blend_minmax]", var2.GL_EXT_blend_minmax);
        playerSnooper.addStatToSnooper("gl_caps[EXT_blend_subtract]", var2.GL_EXT_blend_subtract);
        playerSnooper.addStatToSnooper("gl_caps[EXT_draw_instanced]", var2.GL_EXT_draw_instanced);
        playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_multisample]", var2.GL_EXT_framebuffer_multisample);
        playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_object]", var2.GL_EXT_framebuffer_object);
        playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_sRGB]", var2.GL_EXT_framebuffer_sRGB);
        playerSnooper.addStatToSnooper("gl_caps[EXT_geometry_shader4]", var2.GL_EXT_geometry_shader4);
        playerSnooper.addStatToSnooper("gl_caps[EXT_gpu_program_parameters]", var2.GL_EXT_gpu_program_parameters);
        playerSnooper.addStatToSnooper("gl_caps[EXT_gpu_shader4]", var2.GL_EXT_gpu_shader4);
        playerSnooper.addStatToSnooper("gl_caps[EXT_multi_draw_arrays]", var2.GL_EXT_multi_draw_arrays);
        playerSnooper.addStatToSnooper("gl_caps[EXT_packed_depth_stencil]", var2.GL_EXT_packed_depth_stencil);
        playerSnooper.addStatToSnooper("gl_caps[EXT_paletted_texture]", var2.GL_EXT_paletted_texture);
        playerSnooper.addStatToSnooper("gl_caps[EXT_rescale_normal]", var2.GL_EXT_rescale_normal);
        playerSnooper.addStatToSnooper("gl_caps[EXT_separate_shader_objects]", var2.GL_EXT_separate_shader_objects);
        playerSnooper.addStatToSnooper("gl_caps[EXT_shader_image_load_store]", var2.GL_EXT_shader_image_load_store);
        playerSnooper.addStatToSnooper("gl_caps[EXT_shadow_funcs]", var2.GL_EXT_shadow_funcs);
        playerSnooper.addStatToSnooper("gl_caps[EXT_shared_texture_palette]", var2.GL_EXT_shared_texture_palette);
        playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_clear_tag]", var2.GL_EXT_stencil_clear_tag);
        playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_two_side]", var2.GL_EXT_stencil_two_side);
        playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_wrap]", var2.GL_EXT_stencil_wrap);
        playerSnooper.addStatToSnooper("gl_caps[EXT_texture_3d]", var2.GL_EXT_texture_3d);
        playerSnooper.addStatToSnooper("gl_caps[EXT_texture_array]", var2.GL_EXT_texture_array);
        playerSnooper.addStatToSnooper("gl_caps[EXT_texture_buffer_object]", var2.GL_EXT_texture_buffer_object);
        playerSnooper.addStatToSnooper("gl_caps[EXT_texture_integer]", var2.GL_EXT_texture_integer);
        playerSnooper.addStatToSnooper("gl_caps[EXT_texture_lod_bias]", var2.GL_EXT_texture_lod_bias);
        playerSnooper.addStatToSnooper("gl_caps[EXT_texture_sRGB]", var2.GL_EXT_texture_sRGB);
        playerSnooper.addStatToSnooper("gl_caps[EXT_vertex_shader]", var2.GL_EXT_vertex_shader);
        playerSnooper.addStatToSnooper("gl_caps[EXT_vertex_weighting]", var2.GL_EXT_vertex_weighting);
        playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_uniforms]", GL11.glGetInteger((int)35658));
        GL11.glGetError();
        playerSnooper.addStatToSnooper("gl_caps[gl_max_fragment_uniforms]", GL11.glGetInteger((int)35657));
        GL11.glGetError();
        playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_attribs]", GL11.glGetInteger((int)34921));
        GL11.glGetError();
        playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_texture_image_units]", GL11.glGetInteger((int)35660));
        GL11.glGetError();
        playerSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", GL11.glGetInteger((int)34930));
        GL11.glGetError();
        playerSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", GL11.glGetInteger((int)35071));
        GL11.glGetError();
        playerSnooper.addStatToSnooper("gl_max_texture_size", Minecraft.getGLMaximumTextureSize());
    }

    public static int getGLMaximumTextureSize() {
        for (int var0 = 16384; var0 > 0; var0 >>= 1) {
            GL11.glTexImage2D((int)32868, (int)0, (int)6408, (int)var0, (int)var0, (int)0, (int)6408, (int)5121, null);
            int var1 = GL11.glGetTexLevelParameteri((int)32868, (int)0, (int)4096);
            if (var1 == 0) continue;
            return var0;
        }
        return -1;
    }

    @Override
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
        IntegratedServer var0;
        if (theMinecraft != null && (var0 = theMinecraft.getIntegratedServer()) != null) {
            var0.stopServer();
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

    public PropertyMap func_180509_L() {
        return this.twitchDetails;
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

    public Entity getRenderViewEntity() {
        return this.field_175622_Z;
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
        return currentScreen instanceof GuiWinGame ? MusicTicker.MusicType.CREDITS : (thePlayer != null ? (Minecraft.thePlayer.worldObj.provider instanceof WorldProviderHell ? MusicTicker.MusicType.NETHER : (Minecraft.thePlayer.worldObj.provider instanceof WorldProviderEnd ? (BossStatus.bossName != null && BossStatus.statusBarTime > 0 ? MusicTicker.MusicType.END_BOSS : MusicTicker.MusicType.END) : (Minecraft.thePlayer.capabilities.isCreativeMode && Minecraft.thePlayer.capabilities.allowFlying ? MusicTicker.MusicType.CREATIVE : MusicTicker.MusicType.GAME))) : MusicTicker.MusicType.MENU);
    }

    public IStream getTwitchStream() {
        return this.stream;
    }

    public void dispatchKeypresses() {
        int var1;
        int n = var1 = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() : Keyboard.getEventKey();
        if (!(var1 == 0 || Keyboard.isRepeatEvent() || currentScreen instanceof GuiControls && ((GuiControls)Minecraft.currentScreen).time > Minecraft.getSystemTime() - 20L)) {
            if (Keyboard.getEventKeyState()) {
                if (var1 == this.gameSettings.keyBindStreamStartStop.getKeyCode()) {
                    if (this.getTwitchStream().func_152934_n()) {
                        this.getTwitchStream().func_152914_u();
                    } else if (this.getTwitchStream().func_152924_m()) {
                        this.displayGuiScreen(new GuiYesNo(new GuiYesNoCallback(){
                            private static final String __OBFID = "CL_00001852";

                            @Override
                            public void confirmClicked(boolean result, int id) {
                                if (result) {
                                    Minecraft.this.getTwitchStream().func_152930_t();
                                }
                                Minecraft.this.displayGuiScreen(null);
                            }
                        }, I18n.format("stream.confirm_start", new Object[0]), "", 0));
                    } else if (this.getTwitchStream().func_152928_D() && this.getTwitchStream().func_152936_l()) {
                        if (theWorld != null) {
                            this.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("Not ready to start streaming yet!"));
                        }
                    } else {
                        GuiStreamUnavailable.func_152321_a(currentScreen);
                    }
                } else if (var1 == this.gameSettings.keyBindStreamPauseUnpause.getKeyCode()) {
                    if (this.getTwitchStream().func_152934_n()) {
                        if (this.getTwitchStream().isPaused()) {
                            this.getTwitchStream().func_152933_r();
                        } else {
                            this.getTwitchStream().func_152916_q();
                        }
                    }
                } else if (var1 == this.gameSettings.keyBindStreamCommercials.getKeyCode()) {
                    if (this.getTwitchStream().func_152934_n()) {
                        this.getTwitchStream().func_152931_p();
                    }
                } else if (var1 == this.gameSettings.keyBindStreamToggleMic.getKeyCode()) {
                    this.stream.func_152910_a(true);
                } else if (var1 == this.gameSettings.keyBindFullscreen.getKeyCode()) {
                    this.toggleFullscreen();
                } else if (var1 == this.gameSettings.keyBindScreenshot.getKeyCode()) {
                    this.ingameGUI.getChatGUI().printChatMessage(ScreenShotHelper.saveScreenshot(this.mcDataDir, this.displayWidth, this.displayHeight, this.framebufferMc));
                }
            } else if (var1 == this.gameSettings.keyBindStreamToggleMic.getKeyCode()) {
                this.stream.func_152910_a(false);
            }
        }
    }

    public MinecraftSessionService getSessionService() {
        return this.sessionService;
    }

    public SkinManager getSkinManager() {
        return this.skinManager;
    }

    public Entity func_175606_aa() {
        return this.field_175622_Z;
    }

    public void func_175607_a(Entity p_175607_1_) {
        this.field_175622_Z = p_175607_1_;
        this.entityRenderer.func_175066_a(p_175607_1_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ListenableFuture addScheduledTask(Callable callableToSchedule) {
        Validate.notNull((Object)callableToSchedule);
        if (!this.isCallingFromMinecraftThread()) {
            ListenableFutureTask var2 = ListenableFutureTask.create((Callable)callableToSchedule);
            Queue var3 = this.scheduledTasks;
            Queue queue = this.scheduledTasks;
            synchronized (queue) {
                this.scheduledTasks.add(var2);
                return var2;
            }
        }
        try {
            return Futures.immediateFuture(callableToSchedule.call());
        }
        catch (Exception var6) {
            return Futures.immediateFailedCheckedFuture((Exception)var6);
        }
    }

    @Override
    public ListenableFuture addScheduledTask(Runnable runnableToSchedule) {
        Validate.notNull((Object)runnableToSchedule);
        return this.addScheduledTask(Executors.callable(runnableToSchedule));
    }

    @Override
    public boolean isCallingFromMinecraftThread() {
        return Thread.currentThread() == this.mcThread;
    }

    public BlockRendererDispatcher getBlockRendererDispatcher() {
        return this.field_175618_aM;
    }

    public static RenderManager getRenderManager() {
        return renderManager;
    }

    public RenderItem getRenderItem() {
        return this.renderItem;
    }

    public ItemRenderer getItemRenderer() {
        return this.itemRenderer;
    }

    public static int func_175610_ah() {
        return debugFPS;
    }

    public static Map func_175596_ai() {
        HashMap var0 = Maps.newHashMap();
        var0.put("X-Minecraft-Username", Minecraft.getMinecraft().getSession().getUsername());
        var0.put("X-Minecraft-UUID", Minecraft.getMinecraft().getSession().getPlayerID());
        var0.put("X-Minecraft-Version", "1.8");
        return var0;
    }

}

