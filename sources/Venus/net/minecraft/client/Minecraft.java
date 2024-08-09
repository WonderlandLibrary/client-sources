/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Queues;
import com.google.gson.JsonElement;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.OfflineSocialInteractions;
import com.mojang.authlib.minecraft.SocialInteractionsService;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.PlatformDescriptors;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Function4;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.SocketAddress;
import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import mpp.venusfr.events.EventChangeWorld;
import mpp.venusfr.events.TickEvent;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.functions.impl.combat.KillAura;
import mpp.venusfr.functions.impl.player.ItemCooldown;
import mpp.venusfr.ui.mainmenu.MainScreen;
import mpp.venusfr.utils.client.ClientUtil;
import mpp.venusfr.utils.render.font.Fonts;
import mpp.venusfr.venusfr;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.GameConfiguration;
import net.minecraft.client.GameSettings;
import net.minecraft.client.KeyboardListener;
import net.minecraft.client.MainWindow;
import net.minecraft.client.MinecraftGame;
import net.minecraft.client.MouseHelper;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.client.audio.BackgroundMusicTracks;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.gui.LoadingGui;
import net.minecraft.client.gui.ResourceLoadProgressGui;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.advancements.AdvancementsScreen;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.fonts.FontResourceManager;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ConfirmBackupScreen;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.ConnectingScreen;
import net.minecraft.client.gui.screen.DatapackFailureScreen;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.DirtMessageScreen;
import net.minecraft.client.gui.screen.EditWorldScreen;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraft.client.gui.screen.MemoryErrorScreen;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SleepInMultiplayerScreen;
import net.minecraft.client.gui.screen.WinGameScreen;
import net.minecraft.client.gui.screen.WorkingScreen;
import net.minecraft.client.gui.screen.WorldLoadProgressScreen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.social.FilterManager;
import net.minecraft.client.gui.social.SocialInteractionsScreen;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.client.gui.toasts.TutorialToast;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.login.ClientLoginNetHandler;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.FirstPersonRenderer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GPUWarning;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.IWindowEventListener;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.ScreenSize;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VirtualScreen;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.texture.PaintingSpriteUploader;
import net.minecraft.client.renderer.texture.PotionSpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.DownloadingPackFinder;
import net.minecraft.client.resources.FoliageColorReloadListener;
import net.minecraft.client.resources.GrassColorReloadListener;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.resources.LegacyResourcePackWrapper;
import net.minecraft.client.resources.LegacyResourcePackWrapperV4;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.settings.AmbientOcclusionStatus;
import net.minecraft.client.settings.CloudOption;
import net.minecraft.client.settings.CreativeSettings;
import net.minecraft.client.settings.GraphicsFanciness;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.client.util.IMutableSearchTree;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.SearchTree;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.client.util.SearchTreeReloadable;
import net.minecraft.client.util.Splashes;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.Commands;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.entity.item.PaintingEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.ChatVisibility;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SkullItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.ProtocolType;
import net.minecraft.network.handshake.client.CHandshakePacket;
import net.minecraft.network.login.client.CLoginStartPacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.profiler.DataPoint;
import net.minecraft.profiler.EmptyProfiler;
import net.minecraft.profiler.IProfileResult;
import net.minecraft.profiler.IProfiler;
import net.minecraft.profiler.ISnooperInfo;
import net.minecraft.profiler.LongTickDetector;
import net.minecraft.profiler.Snooper;
import net.minecraft.profiler.TimeTracker;
import net.minecraft.resources.DataPackRegistries;
import net.minecraft.resources.FolderPackFinder;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.ServerPackFinder;
import net.minecraft.resources.SimpleReloadableResourceManager;
import net.minecraft.resources.data.PackMetadataSection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Timer;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.concurrent.RecursiveEventLoop;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.codec.DatapackCodec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.registry.Bootstrap;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenSettingsExport;
import net.minecraft.util.registry.WorldSettingsImport;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.KeybindTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.listener.ChainedChunkStatusListener;
import net.minecraft.world.chunk.listener.IChunkStatusListener;
import net.minecraft.world.chunk.listener.TrackingChunkStatusListener;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.storage.FolderName;
import net.minecraft.world.storage.IServerConfiguration;
import net.minecraft.world.storage.SaveFormat;
import net.minecraft.world.storage.ServerWorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import via.fixes.AttackOrder;

public class Minecraft
extends RecursiveEventLoop<Runnable>
implements ISnooperInfo,
IWindowEventListener {
    private static Minecraft instance;
    private static final Logger LOGGER;
    public static final boolean IS_RUNNING_ON_MAC;
    public static final ResourceLocation DEFAULT_FONT_RENDERER_NAME;
    public static final ResourceLocation UNIFORM_FONT_RENDERER_NAME;
    public static final ResourceLocation standardGalacticFontRenderer;
    private static final CompletableFuture<Unit> RESOURCE_RELOAD_INIT_TASK;
    private static final ITextComponent field_244596_I;
    public File fileResourcepacks;
    private final PropertyMap profileProperties;
    private final TextureManager textureManager;
    private final DataFixer dataFixer;
    private final VirtualScreen virtualScreen;
    private final MainWindow mainWindow;
    public final Timer timer = new Timer(20.0f, 0L);
    private final Snooper snooper = new Snooper("client", this, Util.milliTime());
    private final RenderTypeBuffers renderTypeBuffers;
    public final WorldRenderer worldRenderer;
    private final EntityRendererManager renderManager;
    private final ItemRenderer itemRenderer;
    private final FirstPersonRenderer firstPersonRenderer;
    public final ParticleManager particles;
    private final SearchTreeManager searchTreeManager = new SearchTreeManager();
    public Session session;
    public final FontRenderer fontRenderer;
    public final GameRenderer gameRenderer;
    public final DebugRenderer debugRenderer;
    private final AtomicReference<TrackingChunkStatusListener> refChunkStatusListener = new AtomicReference();
    public final IngameGui ingameGUI;
    public final GameSettings gameSettings;
    private final CreativeSettings creativeSettings;
    public final MouseHelper mouseHelper;
    public final KeyboardListener keyboardListener;
    public final File gameDir;
    private final String launchedVersion;
    private final String versionType;
    private final Proxy proxy;
    private final SaveFormat saveFormat;
    public final FrameTimer frameTimer = new FrameTimer();
    private final boolean jvm64bit;
    private final boolean isDemo;
    private final boolean enableMultiplayer;
    private final boolean enableChat;
    private final IReloadableResourceManager resourceManager;
    private final DownloadingPackFinder packFinder;
    private final ResourcePackList resourcePackRepository;
    private final LanguageManager languageManager;
    private final BlockColors blockColors;
    private final ItemColors itemColors;
    private final Framebuffer framebuffer;
    private final SoundHandler soundHandler;
    private final MusicTicker musicTicker;
    private final FontResourceManager fontResourceMananger;
    private final Splashes splashes;
    private final GPUWarning warningGPU;
    private final MinecraftSessionService sessionService;
    private final SocialInteractionsService field_244734_au;
    private final SkinManager skinManager;
    private final ModelManager modelManager;
    private final BlockRendererDispatcher blockRenderDispatcher;
    private final PaintingSpriteUploader paintingSprites;
    private final PotionSpriteUploader potionSprites;
    private final ToastGui toastGui;
    private final MinecraftGame game = new MinecraftGame(this);
    private final Tutorial tutorial;
    private final FilterManager field_244597_aC;
    public static byte[] memoryReserve;
    public PlayerController playerController;
    public ClientWorld world;
    public ClientPlayerEntity player;
    @Nullable
    private IntegratedServer integratedServer;
    @Nullable
    private ServerData currentServerData;
    @Nullable
    private NetworkManager networkManager;
    private boolean integratedServerIsRunning;
    @Nullable
    public Entity renderViewEntity;
    @Nullable
    public Entity pointedEntity;
    @Nullable
    public RayTraceResult objectMouseOver;
    public int rightClickDelayTimer;
    protected int leftClickCounter;
    private boolean isGamePaused;
    private float renderPartialTicksPaused;
    private long startNanoTime = Util.nanoTime();
    private long debugUpdateTime;
    private int fpsCounter;
    public boolean skipRenderWorld;
    @Nullable
    public Screen currentScreen;
    @Nullable
    public LoadingGui loadingGui;
    private boolean connectedToRealms;
    private Thread thread;
    private volatile boolean running = true;
    @Nullable
    private CrashReport crashReporter;
    public int debugFPS;
    public String debug = "";
    public boolean debugWireframe;
    public boolean debugChunkPath;
    public boolean renderChunksMany = true;
    private boolean isWindowFocused;
    private final Queue<Runnable> queueChunkTracking = Queues.newConcurrentLinkedQueue();
    @Nullable
    private CompletableFuture<Void> futureRefreshResources;
    @Nullable
    private TutorialToast field_244598_aV;
    private IProfiler profiler = EmptyProfiler.INSTANCE;
    private int gameTime;
    private final TimeTracker gameTimeTracker = new TimeTracker(Util.nanoTimeSupplier, this::lambda$new$0);
    @Nullable
    private IProfileResult profilerResult;
    private String debugProfilerName = "root";
    TickEvent tickEvent = new TickEvent(this);

    public Minecraft(GameConfiguration gameConfiguration) {
        super("Client");
        Object object;
        Object object2;
        int n;
        String string;
        instance = this;
        this.gameDir = gameConfiguration.folderInfo.gameDir;
        File file = gameConfiguration.folderInfo.assetsDir;
        this.fileResourcepacks = gameConfiguration.folderInfo.resourcePacksDir;
        this.launchedVersion = gameConfiguration.gameInfo.version;
        this.versionType = gameConfiguration.gameInfo.versionType;
        this.profileProperties = gameConfiguration.userInfo.profileProperties;
        this.packFinder = new DownloadingPackFinder(new File(this.gameDir, "server-resource-packs"), gameConfiguration.folderInfo.getAssetsIndex());
        this.resourcePackRepository = new ResourcePackList(Minecraft::makePackInfo, this.packFinder, new FolderPackFinder(this.fileResourcepacks, IPackNameDecorator.PLAIN));
        this.proxy = gameConfiguration.userInfo.proxy;
        YggdrasilAuthenticationService yggdrasilAuthenticationService = new YggdrasilAuthenticationService(this.proxy);
        this.sessionService = yggdrasilAuthenticationService.createMinecraftSessionService();
        this.field_244734_au = this.func_244735_a(yggdrasilAuthenticationService, gameConfiguration);
        this.session = gameConfiguration.userInfo.session;
        LOGGER.info("Setting user: {}", (Object)this.session.getUsername());
        LOGGER.debug("(Session ID is {})", (Object)this.session.getSessionID());
        this.isDemo = gameConfiguration.gameInfo.isDemo;
        this.enableMultiplayer = !gameConfiguration.gameInfo.disableMultiplayer;
        this.enableChat = !gameConfiguration.gameInfo.disableChat;
        this.jvm64bit = Minecraft.isJvm64bit();
        this.integratedServer = null;
        if (this.isMultiplayerEnabled() && gameConfiguration.serverInfo.serverName != null) {
            string = gameConfiguration.serverInfo.serverName;
            n = gameConfiguration.serverInfo.serverPort;
        } else {
            string = null;
            n = 0;
        }
        KeybindTextComponent.func_240696_a_(KeyBinding::getDisplayString);
        this.dataFixer = DataFixesManager.getDataFixer();
        this.toastGui = new ToastGui(this);
        this.tutorial = new Tutorial(this);
        this.thread = Thread.currentThread();
        this.gameSettings = new GameSettings(this, this.gameDir);
        this.creativeSettings = new CreativeSettings(this.gameDir, this.dataFixer);
        LOGGER.info("Backend library: {}", (Object)RenderSystem.getBackendDescription());
        ScreenSize screenSize = this.gameSettings.overrideHeight > 0 && this.gameSettings.overrideWidth > 0 ? new ScreenSize(this.gameSettings.overrideWidth, this.gameSettings.overrideHeight, gameConfiguration.displayInfo.fullscreenWidth, gameConfiguration.displayInfo.fullscreenHeight, gameConfiguration.displayInfo.fullscreen) : gameConfiguration.displayInfo;
        Util.nanoTimeSupplier = RenderSystem.initBackendSystem();
        this.virtualScreen = new VirtualScreen(this);
        this.mainWindow = this.virtualScreen.create(screenSize, this.gameSettings.fullscreenResolution, this.getWindowTitle());
        this.setGameFocused(false);
        try {
            object2 = this.getPackFinder().getVanillaPack().getResourceStream(ResourcePackType.CLIENT_RESOURCES, new ResourceLocation("icons/icon_16x16.png"));
            object = this.getPackFinder().getVanillaPack().getResourceStream(ResourcePackType.CLIENT_RESOURCES, new ResourceLocation("icons/icon_32x32.png"));
            this.mainWindow.setWindowIcon((InputStream)object2, (InputStream)object);
        } catch (IOException iOException) {
            LOGGER.error("Couldn't set icon", (Throwable)iOException);
        }
        this.mainWindow.setFramerateLimit(this.gameSettings.framerateLimit);
        this.mouseHelper = new MouseHelper(this);
        this.mouseHelper.registerCallbacks(this.mainWindow.getHandle());
        this.keyboardListener = new KeyboardListener(this);
        this.keyboardListener.setupCallbacks(this.mainWindow.getHandle());
        RenderSystem.initRenderer(this.gameSettings.glDebugVerbosity, false);
        this.framebuffer = new Framebuffer(this.mainWindow.getFramebufferWidth(), this.mainWindow.getFramebufferHeight(), true, IS_RUNNING_ON_MAC);
        this.framebuffer.setFramebufferColor(0.0f, 0.0f, 0.0f, 0.0f);
        this.resourceManager = new SimpleReloadableResourceManager(ResourcePackType.CLIENT_RESOURCES);
        this.resourcePackRepository.reloadPacksFromFinders();
        this.gameSettings.fillResourcePackList(this.resourcePackRepository);
        this.languageManager = new LanguageManager(this.gameSettings.language);
        this.resourceManager.addReloadListener(this.languageManager);
        this.textureManager = new TextureManager(this.resourceManager);
        this.resourceManager.addReloadListener(this.textureManager);
        this.skinManager = new SkinManager(this.textureManager, new File(file, "skins"), this.sessionService);
        this.saveFormat = new SaveFormat(this.gameDir.toPath().resolve("saves"), this.gameDir.toPath().resolve("backups"), this.dataFixer);
        this.soundHandler = new SoundHandler(this.resourceManager, this.gameSettings);
        this.resourceManager.addReloadListener(this.soundHandler);
        this.splashes = new Splashes(this.session);
        this.resourceManager.addReloadListener(this.splashes);
        this.musicTicker = new MusicTicker(this);
        this.fontResourceMananger = new FontResourceManager(this.textureManager);
        this.fontRenderer = this.fontResourceMananger.func_238548_a_();
        this.resourceManager.addReloadListener(this.fontResourceMananger.getReloadListener());
        this.forceUnicodeFont(this.getForceUnicodeFont());
        this.resourceManager.addReloadListener(new GrassColorReloadListener());
        this.resourceManager.addReloadListener(new FoliageColorReloadListener());
        this.mainWindow.setRenderPhase("Startup");
        RenderSystem.setupDefaultState(0, 0, this.mainWindow.getFramebufferWidth(), this.mainWindow.getFramebufferHeight());
        this.mainWindow.setRenderPhase("Post startup");
        this.blockColors = BlockColors.init();
        this.itemColors = ItemColors.init(this.blockColors);
        this.modelManager = new ModelManager(this.textureManager, this.blockColors, this.gameSettings.mipmapLevels);
        this.resourceManager.addReloadListener(this.modelManager);
        this.itemRenderer = new ItemRenderer(this.textureManager, this.modelManager, this.itemColors);
        this.renderManager = new EntityRendererManager(this.textureManager, this.itemRenderer, this.resourceManager, this.fontRenderer, this.gameSettings);
        this.firstPersonRenderer = new FirstPersonRenderer(this);
        this.resourceManager.addReloadListener(this.itemRenderer);
        this.renderTypeBuffers = new RenderTypeBuffers();
        this.gameRenderer = new GameRenderer(this, this.resourceManager, this.renderTypeBuffers);
        this.resourceManager.addReloadListener(this.gameRenderer);
        this.field_244597_aC = new FilterManager(this, this.field_244734_au);
        this.blockRenderDispatcher = new BlockRendererDispatcher(this.modelManager.getBlockModelShapes(), this.blockColors);
        this.resourceManager.addReloadListener(this.blockRenderDispatcher);
        this.worldRenderer = new WorldRenderer(this, this.renderTypeBuffers);
        this.resourceManager.addReloadListener(this.worldRenderer);
        this.populateSearchTreeManager();
        this.resourceManager.addReloadListener(this.searchTreeManager);
        this.particles = new ParticleManager(this.world, this.textureManager);
        this.resourceManager.addReloadListener(this.particles);
        this.paintingSprites = new PaintingSpriteUploader(this.textureManager);
        this.resourceManager.addReloadListener(this.paintingSprites);
        this.potionSprites = new PotionSpriteUploader(this.textureManager);
        this.resourceManager.addReloadListener(this.potionSprites);
        this.warningGPU = new GPUWarning();
        this.resourceManager.addReloadListener(this.warningGPU);
        this.ingameGUI = new IngameGui(this);
        this.debugRenderer = new DebugRenderer(this);
        new venusfr();
        RenderSystem.setErrorCallback(this::disableVSyncAfterGlError);
        if (this.gameSettings.fullscreen && !this.mainWindow.isFullscreen()) {
            this.mainWindow.toggleFullscreen();
            this.gameSettings.fullscreen = this.mainWindow.isFullscreen();
        }
        this.mainWindow.setVsync(this.gameSettings.vsync);
        this.mainWindow.setRawMouseInput(this.gameSettings.rawMouseInput);
        this.mainWindow.setLogOnGlError();
        this.updateWindowSize();
        object2 = venusfr.getInstance().getFunctionRegistry();
        if (string != null) {
            this.displayGuiScreen(new ConnectingScreen(new MainScreen(), this, string, n));
        } else {
            this.displayGuiScreen(new MainScreen());
        }
        ResourceLoadProgressGui.loadLogoTexture(this);
        object = this.resourcePackRepository.func_232623_f_();
        this.setLoadingGui(new ResourceLoadProgressGui(this, this.resourceManager.reloadResources(Util.getServerExecutor(), this, RESOURCE_RELOAD_INIT_TASK, (List<IResourcePack>)object), this::lambda$new$2, false));
        Fonts.register();
    }

    public void setDefaultMinecraftTitle() {
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        this.mainWindow.setWindowTitle("VenusWare Free");
    }

    private String getWindowTitle() {
        StringBuilder stringBuilder = new StringBuilder("Minecraft");
        if (this.isModdedClient()) {
            stringBuilder.append("*");
        }
        stringBuilder.append(" ");
        stringBuilder.append(SharedConstants.getVersion().getName());
        ClientPlayNetHandler clientPlayNetHandler = this.getConnection();
        if (clientPlayNetHandler != null && clientPlayNetHandler.getNetworkManager().isChannelOpen()) {
            stringBuilder.append(" - ");
            if (this.integratedServer != null && !this.integratedServer.getPublic()) {
                stringBuilder.append(I18n.format("title.singleplayer", new Object[0]));
            } else if (this.isConnectedToRealms()) {
                stringBuilder.append(I18n.format("title.multiplayer.realms", new Object[0]));
            } else if (!(this.integratedServer != null || this.currentServerData != null && this.currentServerData.isOnLAN())) {
                stringBuilder.append(I18n.format("title.multiplayer.other", new Object[0]));
            } else {
                stringBuilder.append(I18n.format("title.multiplayer.lan", new Object[0]));
            }
        }
        return stringBuilder.toString();
    }

    private SocialInteractionsService func_244735_a(YggdrasilAuthenticationService yggdrasilAuthenticationService, GameConfiguration gameConfiguration) {
        try {
            return yggdrasilAuthenticationService.createSocialInteractionsService(gameConfiguration.userInfo.session.getToken());
        } catch (AuthenticationException authenticationException) {
            LOGGER.error("Failed to verify authentication", (Throwable)authenticationException);
            return new OfflineSocialInteractions();
        }
    }

    public boolean isModdedClient() {
        return !"vanilla".equals(ClientBrandRetriever.getClientModName()) || Minecraft.class.getSigners() == null;
    }

    private void restoreResourcePacks(Throwable throwable) {
        if (this.resourcePackRepository.func_232621_d_().size() > 1) {
            StringTextComponent stringTextComponent = throwable instanceof SimpleReloadableResourceManager.FailedPackException ? new StringTextComponent(((SimpleReloadableResourceManager.FailedPackException)throwable).getPack().getName()) : null;
            this.throwResourcePackLoadError(throwable, stringTextComponent);
        } else {
            Util.toRuntimeException(throwable);
        }
    }

    public void throwResourcePackLoadError(Throwable throwable, @Nullable ITextComponent iTextComponent) {
        LOGGER.info("Caught error loading resourcepacks, removing all selected resourcepacks", throwable);
        this.resourcePackRepository.setEnabledPacks(Collections.emptyList());
        this.gameSettings.resourcePacks.clear();
        this.gameSettings.incompatibleResourcePacks.clear();
        this.gameSettings.saveOptions();
        this.reloadResources().thenRun(() -> this.lambda$throwResourcePackLoadError$3(iTextComponent));
    }

    public void run() {
        this.thread = Thread.currentThread();
        try {
            boolean bl = false;
            while (this.running) {
                if (this.crashReporter != null) {
                    Minecraft.displayCrashReport(this.crashReporter);
                    return;
                }
                try {
                    LongTickDetector longTickDetector = LongTickDetector.func_233524_a_("Renderer");
                    boolean bl2 = this.isDebugMode();
                    this.tick(bl2, longTickDetector);
                    this.profiler.startTick();
                    this.runGameLoop(!bl);
                    this.profiler.endTick();
                    this.func_238210_b_(bl2, longTickDetector);
                } catch (OutOfMemoryError outOfMemoryError) {
                    if (bl) {
                        throw outOfMemoryError;
                    }
                    this.freeMemory();
                    this.displayGuiScreen(new MemoryErrorScreen());
                    System.gc();
                    LOGGER.fatal("Out of memory", (Throwable)outOfMemoryError);
                    bl = true;
                }
            }
        } catch (ReportedException reportedException) {
            this.addGraphicsAndWorldToCrashReport(reportedException.getCrashReport());
            this.freeMemory();
            LOGGER.fatal("Reported exception thrown!", (Throwable)reportedException);
            Minecraft.displayCrashReport(reportedException.getCrashReport());
        } catch (Throwable throwable) {
            CrashReport crashReport = this.addGraphicsAndWorldToCrashReport(new CrashReport("Unexpected error", throwable));
            LOGGER.fatal("Unreported exception thrown!", throwable);
            this.freeMemory();
            Minecraft.displayCrashReport(crashReport);
        }
    }

    void forceUnicodeFont(boolean bl) {
        this.fontResourceMananger.func_238551_a_(bl ? ImmutableMap.of(DEFAULT_FONT_RENDERER_NAME, UNIFORM_FONT_RENDERER_NAME) : ImmutableMap.of());
    }

    private void populateSearchTreeManager() {
        SearchTree<ItemStack> searchTree = new SearchTree<ItemStack>(Minecraft::lambda$populateSearchTreeManager$6, Minecraft::lambda$populateSearchTreeManager$7);
        SearchTreeReloadable<ItemStack> searchTreeReloadable = new SearchTreeReloadable<ItemStack>(Minecraft::lambda$populateSearchTreeManager$8);
        NonNullList<ItemStack> nonNullList = NonNullList.create();
        for (Item item : Registry.ITEM) {
            item.fillItemGroup(ItemGroup.SEARCH, nonNullList);
        }
        nonNullList.forEach(arg_0 -> Minecraft.lambda$populateSearchTreeManager$9(searchTree, searchTreeReloadable, arg_0));
        SearchTree<RecipeList> searchTree2 = new SearchTree<RecipeList>(Minecraft::lambda$populateSearchTreeManager$13, Minecraft::lambda$populateSearchTreeManager$15);
        this.searchTreeManager.add(SearchTreeManager.ITEMS, searchTree);
        this.searchTreeManager.add(SearchTreeManager.TAGS, searchTreeReloadable);
        this.searchTreeManager.add(SearchTreeManager.RECIPES, searchTree2);
    }

    private void disableVSyncAfterGlError(int n, long l) {
        this.gameSettings.vsync = false;
        this.gameSettings.saveOptions();
    }

    private static boolean isJvm64bit() {
        String[] stringArray;
        for (String string : stringArray = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"}) {
            String string2 = System.getProperty(string);
            if (string2 == null || !string2.contains("64")) continue;
            return false;
        }
        return true;
    }

    public Framebuffer getFramebuffer() {
        return this.framebuffer;
    }

    public String getVersion() {
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        return this.launchedVersion;
    }

    public String getVersionType() {
        return this.versionType;
    }

    public void crashed(CrashReport crashReport) {
        this.crashReporter = crashReport;
    }

    public static void displayCrashReport(CrashReport crashReport) {
        File file = new File(Minecraft.getInstance().gameDir, "crash-reports");
        File file2 = new File(file, "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt");
        Bootstrap.printToSYSOUT(crashReport.getCompleteReport());
        if (crashReport.getFile() != null) {
            Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + crashReport.getFile());
            System.exit(-1);
        } else if (crashReport.saveToFile(file2)) {
            Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + file2.getAbsolutePath());
            System.exit(-1);
        } else {
            Bootstrap.printToSYSOUT("#@?@# Game crashed! Crash report could not be saved. #@?@#");
            System.exit(-2);
        }
    }

    public boolean getForceUnicodeFont() {
        return this.gameSettings.forceUnicodeFont;
    }

    public CompletableFuture<Void> reloadResources() {
        if (this.futureRefreshResources != null) {
            return this.futureRefreshResources;
        }
        CompletableFuture<Void> completableFuture = new CompletableFuture<Void>();
        if (this.loadingGui instanceof ResourceLoadProgressGui) {
            this.futureRefreshResources = completableFuture;
            return completableFuture;
        }
        this.resourcePackRepository.reloadPacksFromFinders();
        List<IResourcePack> list = this.resourcePackRepository.func_232623_f_();
        this.setLoadingGui(new ResourceLoadProgressGui(this, this.resourceManager.reloadResources(Util.getServerExecutor(), this, RESOURCE_RELOAD_INIT_TASK, list), arg_0 -> this.lambda$reloadResources$17(completableFuture, arg_0), true));
        return completableFuture;
    }

    private void checkMissingData() {
        boolean bl = false;
        BlockModelShapes blockModelShapes = this.getBlockRendererDispatcher().getBlockModelShapes();
        IBakedModel iBakedModel = blockModelShapes.getModelManager().getMissingModel();
        for (Block nonNullList2 : Registry.BLOCK) {
            for (BlockState blockState : nonNullList2.getStateContainer().getValidStates()) {
                IBakedModel iBakedModel2;
                if (blockState.getRenderType() != BlockRenderType.MODEL || (iBakedModel2 = blockModelShapes.getModel(blockState)) != iBakedModel) continue;
                LOGGER.debug("Missing model for: {}", (Object)blockState);
                bl = true;
            }
        }
        TextureAtlasSprite textureAtlasSprite = iBakedModel.getParticleTexture();
        for (Block block : Registry.BLOCK) {
            for (BlockState blockState : block.getStateContainer().getValidStates()) {
                TextureAtlasSprite textureAtlasSprite2 = blockModelShapes.getTexture(blockState);
                if (blockState.isAir() || textureAtlasSprite2 != textureAtlasSprite) continue;
                LOGGER.debug("Missing particle icon for: {}", (Object)blockState);
                bl = true;
            }
        }
        NonNullList<ItemStack> nonNullList = NonNullList.create();
        for (Item item : Registry.ITEM) {
            nonNullList.clear();
            item.fillItemGroup(ItemGroup.SEARCH, nonNullList);
            for (ItemStack itemStack : nonNullList) {
                String string = itemStack.getTranslationKey();
                String string2 = new TranslationTextComponent(string).getString();
                if (!string2.toLowerCase(Locale.ROOT).equals(item.getTranslationKey())) continue;
                LOGGER.debug("Missing translation for: {} {} {}", (Object)itemStack, (Object)string, (Object)itemStack.getItem());
            }
        }
        if (bl |= ScreenManager.isMissingScreen()) {
            throw new IllegalStateException("Your game data is foobar, fix the errors above!");
        }
    }

    public SaveFormat getSaveLoader() {
        return this.saveFormat;
    }

    private void openChatScreen(String string) {
        if (!this.isIntegratedServerRunning() && !this.isChatEnabled()) {
            if (this.player != null) {
                this.player.sendMessage(new TranslationTextComponent("chat.cannotSend").mergeStyle(TextFormatting.RED), Util.DUMMY_UUID);
            }
        } else {
            this.displayGuiScreen(new ChatScreen(string));
        }
    }

    public void displayGuiScreen(@Nullable Screen screen) {
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        if (this.currentScreen != null) {
            this.currentScreen.onClose();
        }
        if (screen == null && this.world == null) {
            screen = new MainScreen();
        } else if (screen == null && this.player.getShouldBeDead()) {
            if (this.player.isShowDeathScreen()) {
                screen = new DeathScreen(null, this.world.getWorldInfo().isHardcore());
            } else {
                this.player.respawnPlayer();
            }
        }
        if (screen instanceof MainScreen || screen instanceof MultiplayerScreen) {
            this.gameSettings.showDebugInfo = false;
            this.ingameGUI.getChatGUI().clearChatMessages(false);
        }
        this.currentScreen = screen;
        if (screen != null) {
            this.mouseHelper.ungrabMouse();
            KeyBinding.unPressAllKeys();
            screen.init(this, this.mainWindow.getScaledWidth(), this.mainWindow.getScaledHeight());
            this.skipRenderWorld = false;
            NarratorChatListener.INSTANCE.say(screen.getNarrationMessage());
        } else {
            this.soundHandler.resume();
            this.mouseHelper.grabMouse();
        }
        this.setDefaultMinecraftTitle();
    }

    public void setLoadingGui(@Nullable LoadingGui loadingGui) {
        this.loadingGui = loadingGui;
    }

    public void shutdownMinecraftApplet() {
        try {
            LOGGER.info("Stopping!");
            try {
                NarratorChatListener.INSTANCE.close();
            } catch (Throwable throwable) {
                // empty catch block
            }
            try {
                if (this.world != null) {
                    this.world.sendQuittingDisconnectingPacket();
                }
                this.unloadWorld();
            } catch (Throwable throwable) {
                // empty catch block
            }
            if (this.currentScreen != null) {
                this.currentScreen.onClose();
            }
            this.close();
        } finally {
            Util.nanoTimeSupplier = System::nanoTime;
            if (this.crashReporter == null) {
                System.exit(0);
            }
        }
    }

    @Override
    public void close() {
        try {
            this.modelManager.close();
            this.fontResourceMananger.close();
            this.gameRenderer.close();
            this.worldRenderer.close();
            this.soundHandler.unloadSounds();
            this.resourcePackRepository.close();
            this.particles.close();
            this.potionSprites.close();
            this.paintingSprites.close();
            this.textureManager.close();
            this.resourceManager.close();
            Util.shutdown();
        } catch (Throwable throwable) {
            LOGGER.error("Shutdown failure!", throwable);
            throw throwable;
        } finally {
            this.virtualScreen.close();
            this.mainWindow.close();
        }
    }

    private void runGameLoop(boolean bl) {
        int n;
        int n2;
        Object object;
        this.mainWindow.setRenderPhase("Pre render");
        long l = Util.nanoTime();
        if (this.mainWindow.shouldClose()) {
            this.shutdown();
        }
        if (this.futureRefreshResources != null && !(this.loadingGui instanceof ResourceLoadProgressGui)) {
            object = this.futureRefreshResources;
            this.futureRefreshResources = null;
            this.reloadResources().thenRun(() -> Minecraft.lambda$runGameLoop$18((CompletableFuture)object));
        }
        while ((object = this.queueChunkTracking.poll()) != null) {
            object.run();
        }
        if (bl) {
            n2 = this.timer.getPartialTicks(Util.milliTime());
            this.profiler.startSection("scheduledExecutables");
            this.drainTasks();
            this.profiler.endSection();
            this.profiler.startSection("tick");
            for (n = 0; n < Math.min(10, n2); ++n) {
                this.profiler.func_230035_c_("clientTick");
                this.runTick();
            }
            this.profiler.endSection();
        }
        this.mouseHelper.updatePlayerLook();
        this.mainWindow.setRenderPhase("Render");
        this.profiler.startSection("sound");
        this.soundHandler.updateListener(this.gameRenderer.getActiveRenderInfo());
        this.profiler.endSection();
        this.profiler.startSection("render");
        RenderSystem.pushMatrix();
        RenderSystem.clear(16640, IS_RUNNING_ON_MAC);
        this.framebuffer.bindFramebuffer(false);
        FogRenderer.resetFog();
        this.profiler.startSection("display");
        RenderSystem.enableTexture();
        RenderSystem.enableCull();
        this.profiler.endSection();
        if (!this.skipRenderWorld) {
            this.profiler.endStartSection("gameRenderer");
            this.gameRenderer.updateCameraAndRender(this.isGamePaused ? this.renderPartialTicksPaused : this.timer.renderPartialTicks, l, bl);
            this.profiler.endStartSection("toasts");
            this.toastGui.func_238541_a_(new MatrixStack());
            this.profiler.endSection();
        }
        if (this.profilerResult != null) {
            this.profiler.startSection("fpsPie");
            this.func_238183_a_(new MatrixStack(), this.profilerResult);
            this.profiler.endSection();
        }
        this.profiler.startSection("blit");
        this.framebuffer.unbindFramebuffer();
        RenderSystem.popMatrix();
        RenderSystem.pushMatrix();
        this.framebuffer.framebufferRender(this.mainWindow.getFramebufferWidth(), this.mainWindow.getFramebufferHeight());
        RenderSystem.popMatrix();
        this.profiler.endStartSection("updateDisplay");
        this.mainWindow.flipFrame();
        n2 = this.getFramerateLimit();
        if ((double)n2 < AbstractOption.FRAMERATE_LIMIT.getMaxValue()) {
            RenderSystem.limitDisplayFPS(n2);
        }
        this.profiler.endStartSection("yield");
        Thread.yield();
        this.profiler.endSection();
        this.mainWindow.setRenderPhase("Post render");
        ++this.fpsCounter;
        int n3 = n = this.isSingleplayer() && (this.currentScreen != null && this.currentScreen.isPauseScreen() || this.loadingGui != null && this.loadingGui.isPauseScreen()) && !this.integratedServer.getPublic() ? 1 : 0;
        if (this.isGamePaused != n) {
            if (this.isGamePaused) {
                this.renderPartialTicksPaused = this.timer.renderPartialTicks;
            } else {
                this.timer.renderPartialTicks = this.renderPartialTicksPaused;
            }
            this.isGamePaused = n;
        }
        long l2 = Util.nanoTime();
        this.frameTimer.addFrame(l2 - this.startNanoTime);
        this.startNanoTime = l2;
        this.profiler.startSection("fpsUpdate");
        while (Util.milliTime() >= this.debugUpdateTime + 1000L) {
            this.debugFPS = this.fpsCounter;
            this.debug = String.format("%d fps T: %s%s%s%s B: %d", this.debugFPS, (double)this.gameSettings.framerateLimit == AbstractOption.FRAMERATE_LIMIT.getMaxValue() ? "inf" : Integer.valueOf(this.gameSettings.framerateLimit), this.gameSettings.vsync ? " vsync" : "", this.gameSettings.graphicFanciness.toString(), this.gameSettings.cloudOption == CloudOption.OFF ? "" : (this.gameSettings.cloudOption == CloudOption.FAST ? " fast-clouds" : " fancy-clouds"), this.gameSettings.biomeBlendRadius);
            this.debugUpdateTime += 1000L;
            this.fpsCounter = 0;
            this.snooper.addMemoryStatsToSnooper();
            if (this.snooper.isSnooperRunning()) continue;
            this.snooper.start();
        }
        this.profiler.endSection();
    }

    private boolean isDebugMode() {
        return this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart && !this.gameSettings.hideGUI;
    }

    private void tick(boolean bl, @Nullable LongTickDetector longTickDetector) {
        venusfr.getInstance().getEventBus().post(this.tickEvent);
        if (bl) {
            if (!this.gameTimeTracker.func_233505_a_()) {
                this.gameTime = 0;
                this.gameTimeTracker.func_233507_c_();
            }
            ++this.gameTime;
        } else {
            this.gameTimeTracker.func_233506_b_();
        }
        this.profiler = LongTickDetector.func_233523_a_(this.gameTimeTracker.func_233508_d_(), longTickDetector);
    }

    private void func_238210_b_(boolean bl, @Nullable LongTickDetector longTickDetector) {
        if (longTickDetector != null) {
            longTickDetector.func_233525_b_();
        }
        this.profilerResult = bl ? this.gameTimeTracker.func_233509_e_() : null;
        this.profiler = this.gameTimeTracker.func_233508_d_();
    }

    @Override
    public void updateWindowSize() {
        int n = this.mainWindow.calcGuiScale(this.gameSettings.guiScale, this.getForceUnicodeFont());
        this.mainWindow.setGuiScale(n);
        if (this.currentScreen != null) {
            this.currentScreen.resize(this, this.mainWindow.getScaledWidth(), this.mainWindow.getScaledHeight());
        }
        Framebuffer framebuffer = this.getFramebuffer();
        framebuffer.resize(this.mainWindow.getFramebufferWidth(), this.mainWindow.getFramebufferHeight(), IS_RUNNING_ON_MAC);
        this.gameRenderer.updateShaderGroupSize(this.mainWindow.getFramebufferWidth(), this.mainWindow.getFramebufferHeight());
        this.mouseHelper.setIgnoreFirstMove();
    }

    @Override
    public void ignoreFirstMove() {
        this.mouseHelper.ignoreFirstMove();
    }

    private int getFramerateLimit() {
        return this.world != null || this.currentScreen == null && this.loadingGui == null ? this.mainWindow.getLimitFramerate() : 60;
    }

    public void freeMemory() {
        try {
            memoryReserve = new byte[0];
            this.worldRenderer.deleteAllDisplayLists();
        } catch (Throwable throwable) {
            // empty catch block
        }
        try {
            System.gc();
            if (this.integratedServerIsRunning && this.integratedServer != null) {
                this.integratedServer.initiateShutdown(false);
            }
            this.unloadWorld(new DirtMessageScreen(new TranslationTextComponent("menu.savingLevel")));
        } catch (Throwable throwable) {
            // empty catch block
        }
        System.gc();
    }

    void updateDebugProfilerName(int n) {
        List<DataPoint> list;
        if (this.profilerResult != null && !(list = this.profilerResult.getDataPoints(this.debugProfilerName)).isEmpty()) {
            DataPoint dataPoint = list.remove(0);
            if (n == 0) {
                int n2;
                if (!dataPoint.name.isEmpty() && (n2 = this.debugProfilerName.lastIndexOf(30)) >= 0) {
                    this.debugProfilerName = this.debugProfilerName.substring(0, n2);
                }
            } else if (--n < list.size() && !"unspecified".equals(list.get((int)n).name)) {
                if (!this.debugProfilerName.isEmpty()) {
                    this.debugProfilerName = this.debugProfilerName + "\u001e";
                }
                this.debugProfilerName = this.debugProfilerName + list.get((int)n).name;
            }
        }
    }

    private void func_238183_a_(MatrixStack matrixStack, IProfileResult iProfileResult) {
        int n;
        int n2;
        List<DataPoint> list = iProfileResult.getDataPoints(this.debugProfilerName);
        DataPoint dataPoint = list.remove(0);
        RenderSystem.clear(256, IS_RUNNING_ON_MAC);
        RenderSystem.matrixMode(5889);
        RenderSystem.loadIdentity();
        RenderSystem.ortho(0.0, this.mainWindow.getFramebufferWidth(), this.mainWindow.getFramebufferHeight(), 0.0, 1000.0, 3000.0);
        RenderSystem.matrixMode(5888);
        RenderSystem.loadIdentity();
        RenderSystem.translatef(0.0f, 0.0f, -2000.0f);
        RenderSystem.lineWidth(1.0f);
        RenderSystem.disableTexture();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        int n3 = 160;
        int n4 = this.mainWindow.getFramebufferWidth() - 160 - 10;
        int n5 = this.mainWindow.getFramebufferHeight() - 320;
        RenderSystem.enableBlend();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos((float)n4 - 176.0f, (float)n5 - 96.0f - 16.0f, 0.0).color(200, 0, 0, 0).endVertex();
        bufferBuilder.pos((float)n4 - 176.0f, n5 + 320, 0.0).color(200, 0, 0, 0).endVertex();
        bufferBuilder.pos((float)n4 + 176.0f, n5 + 320, 0.0).color(200, 0, 0, 0).endVertex();
        bufferBuilder.pos((float)n4 + 176.0f, (float)n5 - 96.0f - 16.0f, 0.0).color(200, 0, 0, 0).endVertex();
        tessellator.draw();
        RenderSystem.disableBlend();
        double d = 0.0;
        for (DataPoint object2 : list) {
            float f;
            float f2;
            float f3;
            int n6;
            int object3 = MathHelper.floor(object2.relTime / 4.0) + 1;
            bufferBuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
            n2 = object2.getTextColor();
            n = n2 >> 16 & 0xFF;
            int dataPoint2 = n2 >> 8 & 0xFF;
            int stringBuilder = n2 & 0xFF;
            bufferBuilder.pos(n4, n5, 0.0).color(n, dataPoint2, stringBuilder, 255).endVertex();
            for (n6 = object3; n6 >= 0; --n6) {
                f3 = (float)((d + object2.relTime * (double)n6 / (double)object3) * 6.2831854820251465 / 100.0);
                f2 = MathHelper.sin(f3) * 160.0f;
                f = MathHelper.cos(f3) * 160.0f * 0.5f;
                bufferBuilder.pos((float)n4 + f2, (float)n5 - f, 0.0).color(n, dataPoint2, stringBuilder, 255).endVertex();
            }
            tessellator.draw();
            bufferBuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
            for (n6 = object3; n6 >= 0; --n6) {
                f3 = (float)((d + object2.relTime * (double)n6 / (double)object3) * 6.2831854820251465 / 100.0);
                f2 = MathHelper.sin(f3) * 160.0f;
                f = MathHelper.cos(f3) * 160.0f * 0.5f;
                if (f > 0.0f) continue;
                bufferBuilder.pos((float)n4 + f2, (float)n5 - f, 0.0).color(n >> 1, dataPoint2 >> 1, stringBuilder >> 1, 255).endVertex();
                bufferBuilder.pos((float)n4 + f2, (float)n5 - f + 10.0f, 0.0).color(n >> 1, dataPoint2 >> 1, stringBuilder >> 1, 255).endVertex();
            }
            tessellator.draw();
            d += object2.relTime;
        }
        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        RenderSystem.enableTexture();
        String string = IProfileResult.decodePath(dataPoint.name);
        Object object = "";
        if (!"unspecified".equals(string)) {
            object = (String)object + "[0] ";
        }
        object = string.isEmpty() ? (String)object + "ROOT " : (String)object + string + " ";
        n2 = 0xFFFFFF;
        this.fontRenderer.drawStringWithShadow(matrixStack, (String)object, n4 - 160, n5 - 80 - 16, 0xFFFFFF);
        object = decimalFormat.format(dataPoint.rootRelTime) + "%";
        this.fontRenderer.drawStringWithShadow(matrixStack, (String)object, n4 + 160 - this.fontRenderer.getStringWidth((String)object), n5 - 80 - 16, 0xFFFFFF);
        for (n = 0; n < list.size(); ++n) {
            DataPoint dataPoint2 = list.get(n);
            StringBuilder stringBuilder = new StringBuilder();
            if ("unspecified".equals(dataPoint2.name)) {
                stringBuilder.append("[?] ");
            } else {
                stringBuilder.append("[").append(n + 1).append("] ");
            }
            Object object2 = stringBuilder.append(dataPoint2.name).toString();
            this.fontRenderer.drawStringWithShadow(matrixStack, (String)object2, n4 - 160, n5 + 80 + n * 8 + 20, dataPoint2.getTextColor());
            object2 = decimalFormat.format(dataPoint2.relTime) + "%";
            this.fontRenderer.drawStringWithShadow(matrixStack, (String)object2, n4 + 160 - 50 - this.fontRenderer.getStringWidth((String)object2), n5 + 80 + n * 8 + 20, dataPoint2.getTextColor());
            object2 = decimalFormat.format(dataPoint2.rootRelTime) + "%";
            this.fontRenderer.drawStringWithShadow(matrixStack, (String)object2, n4 + 160 - this.fontRenderer.getStringWidth((String)object2), n5 + 80 + n * 8 + 20, dataPoint2.getTextColor());
        }
    }

    public void shutdown() {
        this.running = false;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void displayInGameMenu(boolean bl) {
        if (this.currentScreen == null) {
            boolean bl2;
            boolean bl3 = bl2 = this.isSingleplayer() && !this.integratedServer.getPublic();
            if (bl2) {
                this.displayGuiScreen(new IngameMenuScreen(!bl));
                this.soundHandler.pause();
            } else {
                this.displayGuiScreen(new IngameMenuScreen(true));
            }
        }
    }

    private void sendClickBlockToController(boolean bl) {
        if (!bl) {
            this.leftClickCounter = 0;
        }
        if (this.leftClickCounter <= 0 && !this.player.isHandActive()) {
            if (bl && this.objectMouseOver != null && this.objectMouseOver.getType() == RayTraceResult.Type.BLOCK) {
                Direction direction;
                BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult)this.objectMouseOver;
                BlockPos blockPos = blockRayTraceResult.getPos();
                if (!this.world.getBlockState(blockPos).isAir() && this.playerController.onPlayerDamageBlock(blockPos, direction = blockRayTraceResult.getFace())) {
                    this.particles.addBlockHitEffects(blockPos, direction);
                    this.player.swingArm(Hand.MAIN_HAND);
                }
            } else {
                this.playerController.resetBlockRemoving();
            }
        }
    }

    public void clickMouse() {
        if (this.leftClickCounter <= 0) {
            if (this.objectMouseOver == null) {
                LOGGER.error("Null returned as 'hitResult', this shouldn't happen!");
                if (this.playerController.isNotCreative()) {
                    this.leftClickCounter = 10;
                }
            } else if (!this.player.isRowingBoat()) {
                switch (this.objectMouseOver.getType()) {
                    case ENTITY: {
                        AttackOrder.sendFixedAttack(this.player, ((EntityRayTraceResult)this.objectMouseOver).getEntity(), Hand.MAIN_HAND);
                        break;
                    }
                    case BLOCK: {
                        BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult)this.objectMouseOver;
                        BlockPos blockPos = blockRayTraceResult.getPos();
                        if (!this.world.getBlockState(blockPos).isAir()) {
                            this.playerController.clickBlock(blockPos, blockRayTraceResult.getFace());
                            break;
                        }
                    }
                    case MISS: {
                        if (this.playerController.isNotCreative()) {
                            this.leftClickCounter = 10;
                        }
                        this.player.resetCooldown();
                    }
                }
                AttackOrder.sendConditionalSwing(this.objectMouseOver, Hand.MAIN_HAND);
            }
        }
    }

    private void rightClickMouse() {
        if (!this.playerController.getIsHittingBlock()) {
            this.rightClickDelayTimer = 4;
            if (!this.player.isRowingBoat()) {
                if (this.objectMouseOver == null) {
                    LOGGER.warn("Null returned as 'hitResult', this shouldn't happen!");
                }
                for (Hand hand : Hand.values()) {
                    Object object;
                    Object object2;
                    mpp.venusfr.functions.api.Function function;
                    Object object3;
                    ItemStack itemStack = this.player.getHeldItem(hand);
                    if (this.objectMouseOver != null) {
                        switch (this.objectMouseOver.getType()) {
                            case ENTITY: {
                                boolean bl;
                                object3 = venusfr.getInstance().getFunctionRegistry();
                                function = ((FunctionRegistry)object3).getKillAura();
                                object2 = (EntityRayTraceResult)this.objectMouseOver;
                                object = ((EntityRayTraceResult)object2).getEntity();
                                ActionResultType actionResultType = this.playerController.interactWithEntity(this.player, (Entity)object, (EntityRayTraceResult)object2, hand);
                                boolean bl2 = bl = function.isState() && ((KillAura)function).getTarget() != null;
                                if (bl) break;
                                if (!actionResultType.isSuccessOrConsume()) {
                                    actionResultType = this.playerController.interactWithEntity(this.player, (Entity)object, hand);
                                }
                                if (!actionResultType.isSuccessOrConsume()) break;
                                if (actionResultType.isSuccess()) {
                                    this.player.swingArm(hand);
                                }
                                return;
                            }
                            case BLOCK: {
                                object3 = (BlockRayTraceResult)this.objectMouseOver;
                                int n = itemStack.getCount();
                                object2 = this.playerController.processRightClickBlock(this.player, this.world, hand, (BlockRayTraceResult)object3);
                                if (((ActionResultType)((Object)object2)).isSuccessOrConsume()) {
                                    if (((ActionResultType)((Object)object2)).isSuccess()) {
                                        this.player.swingArm(hand);
                                        if (!itemStack.isEmpty() && (itemStack.getCount() != n || this.playerController.isInCreativeMode())) {
                                            this.gameRenderer.itemRenderer.resetEquippedProgress(hand);
                                        }
                                    }
                                    return;
                                }
                                if (object2 != ActionResultType.FAIL) break;
                                return;
                            }
                        }
                    }
                    object3 = venusfr.getInstance().getFunctionRegistry();
                    function = ((FunctionRegistry)object3).getItemCooldown();
                    object2 = ItemCooldown.ItemEnum.getItemEnum(itemStack.getItem());
                    if (itemStack.isEmpty() || !((ActionResultType)((Object)(object = function.isState() && object2 != null && ((ItemCooldown)function).isCurrentItem((ItemCooldown.ItemEnum)((Object)object2)) && this.player.getCooldownTracker().hasCooldown(((ItemCooldown.ItemEnum)((Object)object2)).getItem()) ? ActionResultType.FAIL : this.playerController.processRightClick(this.player, this.world, hand)))).isSuccessOrConsume()) continue;
                    if (((ActionResultType)((Object)object)).isSuccess()) {
                        this.player.swingArm(hand);
                    }
                    this.gameRenderer.itemRenderer.resetEquippedProgress(hand);
                    return;
                }
            }
        }
    }

    public MusicTicker getMusicTicker() {
        return this.musicTicker;
    }

    public void runTick() {
        if (this.rightClickDelayTimer > 0) {
            --this.rightClickDelayTimer;
        }
        this.profiler.startSection("gui");
        if (!this.isGamePaused) {
            this.ingameGUI.tick();
        }
        this.profiler.endSection();
        this.gameRenderer.getMouseOver(1.0f);
        this.tutorial.onMouseHover(this.world, this.objectMouseOver);
        this.profiler.startSection("gameMode");
        if (!this.isGamePaused && this.world != null) {
            this.playerController.tick();
        }
        this.profiler.endStartSection("textures");
        if (this.world != null) {
            this.textureManager.tick();
        }
        if (this.currentScreen == null && this.player != null) {
            if (this.player.getShouldBeDead() && !(this.currentScreen instanceof DeathScreen)) {
                this.displayGuiScreen(null);
            } else if (this.player.isSleeping() && this.world != null) {
                this.displayGuiScreen(new SleepInMultiplayerScreen());
            }
        } else if (this.currentScreen != null && this.currentScreen instanceof SleepInMultiplayerScreen && !this.player.isSleeping()) {
            this.displayGuiScreen(null);
        }
        if (this.currentScreen != null) {
            this.leftClickCounter = 10000;
        }
        if (this.currentScreen != null) {
            Screen.wrapScreenError(this::lambda$runTick$19, "Ticking screen", this.currentScreen.getClass().getCanonicalName());
        }
        if (!this.gameSettings.showDebugInfo) {
            this.ingameGUI.reset();
        }
        if (this.loadingGui == null && (this.currentScreen == null || this.currentScreen.passEvents)) {
            this.profiler.endStartSection("Keybindings");
            this.processKeyBinds();
            if (this.leftClickCounter > 0) {
                --this.leftClickCounter;
            }
        }
        if (this.world != null) {
            venusfr.getInstance().getEventBus().post(this.player.tick);
            this.profiler.endStartSection("gameRenderer");
            if (!this.isGamePaused) {
                this.gameRenderer.tick();
            }
            this.profiler.endStartSection("levelRenderer");
            if (!this.isGamePaused) {
                this.worldRenderer.tick();
            }
            this.profiler.endStartSection("level");
            if (!this.isGamePaused) {
                if (this.world.getTimeLightningFlash() > 0) {
                    this.world.setTimeLightningFlash(this.world.getTimeLightningFlash() - 1);
                }
                this.world.tickEntities();
            }
        } else if (this.gameRenderer.getShaderGroup() != null) {
            this.gameRenderer.stopUseShader();
        }
        if (!this.isGamePaused) {
            this.musicTicker.tick();
        }
        this.soundHandler.tick(this.isGamePaused);
        if (this.world != null) {
            if (!this.isGamePaused) {
                Object object;
                if (!this.gameSettings.field_244601_E && this.func_244600_aM()) {
                    TranslationTextComponent translationTextComponent = new TranslationTextComponent("tutorial.socialInteractions.title");
                    object = new TranslationTextComponent("tutorial.socialInteractions.description", Tutorial.createKeybindComponent("socialInteractions"));
                    this.field_244598_aV = new TutorialToast(TutorialToast.Icons.SOCIAL_INTERACTIONS, translationTextComponent, (ITextComponent)object, true);
                    this.tutorial.func_244698_a(this.field_244598_aV, 160);
                    this.gameSettings.field_244601_E = true;
                    this.gameSettings.saveOptions();
                }
                this.tutorial.tick();
                try {
                    this.world.tick(Minecraft::lambda$runTick$20);
                } catch (Throwable throwable) {
                    object = CrashReport.makeCrashReport(throwable, "Exception in world tick");
                    if (this.world == null) {
                        CrashReportCategory crashReportCategory = ((CrashReport)object).makeCategory("Affected level");
                        crashReportCategory.addDetail("Problem", "Level is null!");
                    } else {
                        this.world.fillCrashReport((CrashReport)object);
                    }
                    throw new ReportedException((CrashReport)object);
                }
            }
            this.profiler.endStartSection("animateTick");
            if (!this.isGamePaused && this.world != null) {
                this.world.animateTick(MathHelper.floor(this.player.getPosX()), MathHelper.floor(this.player.getPosY()), MathHelper.floor(this.player.getPosZ()));
            }
            this.profiler.endStartSection("particles");
            if (!this.isGamePaused) {
                this.particles.tick();
            }
        } else if (this.networkManager != null) {
            this.profiler.endStartSection("pendingConnection");
            this.networkManager.tick();
        }
        this.profiler.endStartSection("keyboard");
        this.keyboardListener.tick();
        this.profiler.endSection();
        ClientUtil.MainMenu();
    }

    private boolean func_244600_aM() {
        return !this.integratedServerIsRunning || this.integratedServer != null && this.integratedServer.getPublic();
    }

    private void processKeyBinds() {
        int n;
        while (this.gameSettings.keyBindTogglePerspective.isPressed()) {
            PointOfView pointOfView = this.gameSettings.getPointOfView();
            this.gameSettings.setPointOfView(this.gameSettings.getPointOfView().func_243194_c());
            if (pointOfView.func_243192_a() != this.gameSettings.getPointOfView().func_243192_a()) {
                this.gameRenderer.loadEntityShader(this.gameSettings.getPointOfView().func_243192_a() ? this.getRenderViewEntity() : null);
            }
            this.worldRenderer.setDisplayListEntitiesDirty();
        }
        while (this.gameSettings.keyBindSmoothCamera.isPressed()) {
            this.gameSettings.smoothCamera = !this.gameSettings.smoothCamera;
        }
        for (n = 0; n < 9; ++n) {
            boolean bl = this.gameSettings.keyBindSaveToolbar.isKeyDown();
            boolean bl2 = this.gameSettings.keyBindLoadToolbar.isKeyDown();
            if (!this.gameSettings.keyBindsHotbar[n].isPressed()) continue;
            if (this.player.isSpectator()) {
                this.ingameGUI.getSpectatorGui().onHotbarSelected(n);
                continue;
            }
            if (!this.player.isCreative() || this.currentScreen != null || !bl2 && !bl) {
                this.player.inventory.currentItem = n;
                continue;
            }
            CreativeScreen.handleHotbarSnapshots(this, n, bl2, bl);
        }
        while (this.gameSettings.field_244602_au.isPressed()) {
            if (!this.func_244600_aM()) {
                this.player.sendStatusMessage(field_244596_I, false);
                NarratorChatListener.INSTANCE.say(field_244596_I.getString());
                continue;
            }
            if (this.field_244598_aV != null) {
                this.tutorial.func_244697_a(this.field_244598_aV);
                this.field_244598_aV = null;
            }
            this.displayGuiScreen(new SocialInteractionsScreen());
        }
        while (this.gameSettings.keyBindInventory.isPressed()) {
            if (this.playerController.isRidingHorse()) {
                this.player.sendHorseInventory();
                continue;
            }
            this.tutorial.openInventory();
            this.displayGuiScreen(new InventoryScreen(this.player));
        }
        while (this.gameSettings.keyBindAdvancements.isPressed()) {
            this.displayGuiScreen(new AdvancementsScreen(this.player.connection.getAdvancementManager()));
        }
        while (this.gameSettings.keyBindSwapHands.isPressed()) {
            if (this.player.isSpectator()) continue;
            this.getConnection().sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ZERO, Direction.DOWN));
        }
        while (this.gameSettings.keyBindDrop.isPressed()) {
            if (this.player.isSpectator() || !this.player.drop(Screen.hasControlDown())) continue;
            this.player.swingArm(Hand.MAIN_HAND);
        }
        int n2 = n = this.gameSettings.chatVisibility != ChatVisibility.HIDDEN ? 1 : 0;
        if (n != 0) {
            while (this.gameSettings.keyBindChat.isPressed()) {
                this.openChatScreen("");
            }
            if (this.currentScreen == null && this.loadingGui == null && this.gameSettings.keyBindCommand.isPressed()) {
                this.openChatScreen("/");
            }
        }
        if (this.player.isHandActive()) {
            if (!this.gameSettings.keyBindUseItem.isKeyDown()) {
                this.playerController.onStoppedUsingItem(this.player);
            }
            while (this.gameSettings.keyBindAttack.isPressed()) {
            }
            while (this.gameSettings.keyBindUseItem.isPressed()) {
            }
            while (this.gameSettings.keyBindPickBlock.isPressed()) {
            }
        } else {
            while (this.gameSettings.keyBindAttack.isPressed()) {
                this.clickMouse();
            }
            while (this.gameSettings.keyBindUseItem.isPressed()) {
                this.rightClickMouse();
            }
            while (this.gameSettings.keyBindPickBlock.isPressed()) {
                this.middleClickMouse();
            }
        }
        if (this.gameSettings.keyBindUseItem.isKeyDown() && this.rightClickDelayTimer == 0 && !this.player.isHandActive()) {
            this.rightClickMouse();
        }
        this.sendClickBlockToController(this.currentScreen == null && this.gameSettings.keyBindAttack.isKeyDown() && this.mouseHelper.isMouseGrabbed());
    }

    public static DatapackCodec loadDataPackCodec(SaveFormat.LevelSave levelSave) {
        MinecraftServer.func_240777_a_(levelSave);
        DatapackCodec datapackCodec = levelSave.readDatapackCodec();
        if (datapackCodec == null) {
            throw new IllegalStateException("Failed to load data pack config");
        }
        return datapackCodec;
    }

    public static IServerConfiguration loadWorld(SaveFormat.LevelSave levelSave, DynamicRegistries.Impl impl, IResourceManager iResourceManager, DatapackCodec datapackCodec) {
        WorldSettingsImport<INBT> worldSettingsImport = WorldSettingsImport.create(NBTDynamicOps.INSTANCE, iResourceManager, impl);
        IServerConfiguration iServerConfiguration = levelSave.readServerConfiguration(worldSettingsImport, datapackCodec);
        if (iServerConfiguration == null) {
            throw new IllegalStateException("Failed to load world");
        }
        return iServerConfiguration;
    }

    public void loadWorld(String string) {
        this.loadWorld(string, DynamicRegistries.func_239770_b_(), Minecraft::loadDataPackCodec, Minecraft::loadWorld, false, WorldSelectionType.BACKUP);
    }

    public void createWorld(String string, WorldSettings worldSettings, DynamicRegistries.Impl impl, DimensionGeneratorSettings dimensionGeneratorSettings) {
        this.loadWorld(string, impl, arg_0 -> Minecraft.lambda$createWorld$21(worldSettings, arg_0), (arg_0, arg_1, arg_2, arg_3) -> Minecraft.lambda$createWorld$23(impl, dimensionGeneratorSettings, worldSettings, arg_0, arg_1, arg_2, arg_3), false, WorldSelectionType.CREATE);
    }

    private void loadWorld(String string, DynamicRegistries.Impl impl, Function<SaveFormat.LevelSave, DatapackCodec> function, Function4<SaveFormat.LevelSave, DynamicRegistries.Impl, IResourceManager, DatapackCodec, IServerConfiguration> function4, boolean bl, WorldSelectionType worldSelectionType) {
        boolean bl2;
        PackManager packManager;
        SaveFormat.LevelSave levelSave;
        try {
            levelSave = this.saveFormat.getLevelSave(string);
        } catch (IOException iOException) {
            LOGGER.warn("Failed to read level {} data", (Object)string, (Object)iOException);
            SystemToast.func_238535_a_(this, string);
            this.displayGuiScreen(null);
            return;
        }
        try {
            packManager = this.reloadDatapacks(impl, function, function4, bl, levelSave);
        } catch (Exception exception) {
            LOGGER.warn("Failed to load datapacks, can't proceed with server load", (Throwable)exception);
            this.displayGuiScreen(new DatapackFailureScreen(() -> this.lambda$loadWorld$24(string, impl, function, function4, worldSelectionType)));
            try {
                levelSave.close();
            } catch (IOException iOException) {
                LOGGER.warn("Failed to unlock access to level {}", (Object)string, (Object)iOException);
            }
            return;
        }
        IServerConfiguration iServerConfiguration = packManager.getServerConfiguration();
        boolean bl3 = iServerConfiguration.getDimensionGeneratorSettings().func_236229_j_();
        boolean bl4 = bl2 = iServerConfiguration.getLifecycle() != Lifecycle.stable();
        if (worldSelectionType == WorldSelectionType.NONE || !bl3 && !bl2) {
            Object object;
            Object object2;
            Object object3;
            this.unloadWorld();
            this.refChunkStatusListener.set(null);
            try {
                levelSave.saveLevel(impl, iServerConfiguration);
                packManager.getDataPackRegistries().updateTags();
                object3 = new YggdrasilAuthenticationService(this.proxy);
                object2 = ((YggdrasilAuthenticationService)object3).createMinecraftSessionService();
                object = ((YggdrasilAuthenticationService)object3).createProfileRepository();
                PlayerProfileCache playerProfileCache = new PlayerProfileCache((GameProfileRepository)object, new File(this.gameDir, MinecraftServer.USER_CACHE_FILE.getName()));
                SkullTileEntity.setProfileCache(playerProfileCache);
                SkullTileEntity.setSessionService((MinecraftSessionService)object2);
                PlayerProfileCache.setOnlineMode(false);
                this.integratedServer = MinecraftServer.func_240784_a_(arg_0 -> this.lambda$loadWorld$26(impl, levelSave, packManager, iServerConfiguration, (MinecraftSessionService)object2, (GameProfileRepository)object, playerProfileCache, arg_0));
                this.integratedServerIsRunning = true;
            } catch (Throwable throwable) {
                CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Starting integrated server");
                CrashReportCategory crashReportCategory = crashReport.makeCategory("Starting integrated server");
                crashReportCategory.addDetail("Level ID", string);
                crashReportCategory.addDetail("Level Name", iServerConfiguration.getWorldName());
                throw new ReportedException(crashReport);
            }
            while (this.refChunkStatusListener.get() == null) {
                Thread.yield();
            }
            object3 = new WorldLoadProgressScreen(this.refChunkStatusListener.get());
            this.displayGuiScreen((Screen)object3);
            this.profiler.startSection("waitForServer");
            while (!this.integratedServer.serverIsInRunLoop()) {
                ((Screen)object3).tick();
                this.runGameLoop(true);
                try {
                    Thread.sleep(16L);
                } catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                if (this.crashReporter == null) continue;
                Minecraft.displayCrashReport(this.crashReporter);
                return;
            }
            this.profiler.endSection();
            object2 = this.integratedServer.getNetworkSystem().addLocalEndpoint();
            object = NetworkManager.provideLocalClient((SocketAddress)object2);
            ((NetworkManager)object).setNetHandler(new ClientLoginNetHandler((NetworkManager)object, this, null, Minecraft::lambda$loadWorld$27));
            ((NetworkManager)object).sendPacket(new CHandshakePacket(object2.toString(), 0, ProtocolType.LOGIN));
            ((NetworkManager)object).sendPacket(new CLoginStartPacket(this.getSession().getProfile()));
            this.networkManager = object;
        } else {
            this.deleteWorld(worldSelectionType, string, bl3, () -> this.lambda$loadWorld$28(string, impl, function, function4, bl));
            packManager.close();
            try {
                levelSave.close();
            } catch (IOException iOException) {
                LOGGER.warn("Failed to unlock access to level {}", (Object)string, (Object)iOException);
            }
        }
    }

    private void deleteWorld(WorldSelectionType worldSelectionType, String string, boolean bl, Runnable runnable) {
        if (worldSelectionType == WorldSelectionType.BACKUP) {
            TranslationTextComponent translationTextComponent;
            TranslationTextComponent translationTextComponent2;
            if (bl) {
                translationTextComponent2 = new TranslationTextComponent("selectWorld.backupQuestion.customized");
                translationTextComponent = new TranslationTextComponent("selectWorld.backupWarning.customized");
            } else {
                translationTextComponent2 = new TranslationTextComponent("selectWorld.backupQuestion.experimental");
                translationTextComponent = new TranslationTextComponent("selectWorld.backupWarning.experimental");
            }
            this.displayGuiScreen(new ConfirmBackupScreen(null, (arg_0, arg_1) -> this.lambda$deleteWorld$29(string, runnable, arg_0, arg_1), translationTextComponent2, translationTextComponent, false));
        } else {
            this.displayGuiScreen(new ConfirmScreen(arg_0 -> this.lambda$deleteWorld$30(runnable, string, arg_0), new TranslationTextComponent("selectWorld.backupQuestion.experimental"), new TranslationTextComponent("selectWorld.backupWarning.experimental"), DialogTexts.GUI_PROCEED, DialogTexts.GUI_CANCEL));
        }
    }

    public PackManager reloadDatapacks(DynamicRegistries.Impl impl, Function<SaveFormat.LevelSave, DatapackCodec> function, Function4<SaveFormat.LevelSave, DynamicRegistries.Impl, IResourceManager, DatapackCodec, IServerConfiguration> function4, boolean bl, SaveFormat.LevelSave levelSave) throws InterruptedException, ExecutionException {
        DatapackCodec datapackCodec = function.apply(levelSave);
        ResourcePackList resourcePackList = new ResourcePackList(new ServerPackFinder(), new FolderPackFinder(levelSave.resolveFilePath(FolderName.DATAPACKS).toFile(), IPackNameDecorator.WORLD));
        try {
            DatapackCodec datapackCodec2 = MinecraftServer.func_240772_a_(resourcePackList, datapackCodec, bl);
            CompletableFuture<DataPackRegistries> completableFuture = DataPackRegistries.func_240961_a_(resourcePackList.func_232623_f_(), Commands.EnvironmentType.INTEGRATED, 2, Util.getServerExecutor(), this);
            this.driveUntil(completableFuture::isDone);
            DataPackRegistries dataPackRegistries = completableFuture.get();
            IServerConfiguration iServerConfiguration = function4.apply(levelSave, impl, dataPackRegistries.getResourceManager(), datapackCodec2);
            return new PackManager(resourcePackList, dataPackRegistries, iServerConfiguration);
        } catch (InterruptedException | ExecutionException exception) {
            resourcePackList.close();
            throw exception;
        }
    }

    public void loadWorld(ClientWorld clientWorld) {
        ClientUtil.stopSound();
        WorkingScreen workingScreen = new WorkingScreen();
        workingScreen.displaySavingString(new TranslationTextComponent("connect.joining"));
        this.updateScreenTick(workingScreen);
        this.world = clientWorld;
        this.updateWorldRenderer(clientWorld);
        if (!this.integratedServerIsRunning) {
            YggdrasilAuthenticationService yggdrasilAuthenticationService = new YggdrasilAuthenticationService(this.proxy);
            MinecraftSessionService minecraftSessionService = yggdrasilAuthenticationService.createMinecraftSessionService();
            GameProfileRepository gameProfileRepository = yggdrasilAuthenticationService.createProfileRepository();
            PlayerProfileCache playerProfileCache = new PlayerProfileCache(gameProfileRepository, new File(this.gameDir, MinecraftServer.USER_CACHE_FILE.getName()));
            SkullTileEntity.setProfileCache(playerProfileCache);
            SkullTileEntity.setSessionService(minecraftSessionService);
            PlayerProfileCache.setOnlineMode(false);
        }
        venusfr.getInstance().getEventBus().post(new EventChangeWorld());
    }

    public void unloadWorld() {
        this.unloadWorld(new WorkingScreen());
    }

    public void unloadWorld(Screen screen) {
        ClientPlayNetHandler clientPlayNetHandler = this.getConnection();
        if (clientPlayNetHandler != null) {
            this.dropTasks();
            clientPlayNetHandler.cleanup();
        }
        IntegratedServer integratedServer = this.integratedServer;
        this.integratedServer = null;
        this.gameRenderer.resetData();
        this.playerController = null;
        NarratorChatListener.INSTANCE.clear();
        this.updateScreenTick(screen);
        if (this.world != null) {
            if (integratedServer != null) {
                this.profiler.startSection("waitForServer");
                while (!integratedServer.isThreadAlive()) {
                    this.runGameLoop(true);
                }
                this.profiler.endSection();
            }
            this.packFinder.clearResourcePack();
            this.ingameGUI.resetPlayersOverlayFooterHeader();
            this.currentServerData = null;
            this.integratedServerIsRunning = false;
            this.game.leaveGameSession();
        }
        this.world = null;
        this.updateWorldRenderer(null);
        this.player = null;
    }

    private void updateScreenTick(Screen screen) {
        this.profiler.startSection("forcedTick");
        this.soundHandler.stop();
        this.renderViewEntity = null;
        this.networkManager = null;
        this.displayGuiScreen(screen);
        this.runGameLoop(true);
        this.profiler.endSection();
    }

    public void forcedScreenTick(Screen screen) {
        this.profiler.startSection("forcedTick");
        this.displayGuiScreen(screen);
        this.runGameLoop(true);
        this.profiler.endSection();
    }

    private void updateWorldRenderer(@Nullable ClientWorld clientWorld) {
        this.worldRenderer.setWorldAndLoadRenderers(clientWorld);
        this.particles.clearEffects(clientWorld);
        TileEntityRendererDispatcher.instance.setWorld(clientWorld);
        this.setDefaultMinecraftTitle();
    }

    public boolean isMultiplayerEnabled() {
        return false;
    }

    public boolean cannotSendChatMessages(UUID uUID) {
        return true;
    }

    public boolean isChatEnabled() {
        return false;
    }

    public final boolean isDemo() {
        return true;
    }

    @Nullable
    public ClientPlayNetHandler getConnection() {
        return this.player == null ? null : this.player.connection;
    }

    public static boolean isGuiEnabled() {
        return !Minecraft.instance.gameSettings.hideGUI;
    }

    public static boolean isFancyGraphicsEnabled() {
        return Minecraft.instance.gameSettings.graphicFanciness.func_238162_a_() >= GraphicsFanciness.FANCY.func_238162_a_();
    }

    public static boolean isFabulousGraphicsEnabled() {
        return Minecraft.instance.gameSettings.graphicFanciness.func_238162_a_() >= GraphicsFanciness.FABULOUS.func_238162_a_();
    }

    public static boolean isAmbientOcclusionEnabled() {
        return Minecraft.instance.gameSettings.ambientOcclusionStatus != AmbientOcclusionStatus.OFF;
    }

    private void middleClickMouse() {
        if (this.objectMouseOver != null && this.objectMouseOver.getType() != RayTraceResult.Type.MISS) {
            ItemStack itemStack;
            Object object;
            boolean bl = this.player.abilities.isCreativeMode;
            TileEntity tileEntity = null;
            RayTraceResult.Type type = this.objectMouseOver.getType();
            if (type == RayTraceResult.Type.BLOCK) {
                object = ((BlockRayTraceResult)this.objectMouseOver).getPos();
                var6_5 = this.world.getBlockState((BlockPos)object);
                Block block = ((AbstractBlock.AbstractBlockState)var6_5).getBlock();
                if (((AbstractBlock.AbstractBlockState)var6_5).isAir()) {
                    return;
                }
                itemStack = block.getItem(this.world, (BlockPos)object, (BlockState)var6_5);
                if (itemStack.isEmpty()) {
                    return;
                }
                if (bl && Screen.hasControlDown() && block.isTileEntityProvider()) {
                    tileEntity = this.world.getTileEntity((BlockPos)object);
                }
            } else {
                if (type != RayTraceResult.Type.ENTITY || !bl) {
                    return;
                }
                object = ((EntityRayTraceResult)this.objectMouseOver).getEntity();
                if (object instanceof PaintingEntity) {
                    itemStack = new ItemStack(Items.PAINTING);
                } else if (object instanceof LeashKnotEntity) {
                    itemStack = new ItemStack(Items.LEAD);
                } else if (object instanceof ItemFrameEntity) {
                    var6_5 = (ItemFrameEntity)object;
                    ItemStack itemStack2 = ((ItemFrameEntity)var6_5).getDisplayedItem();
                    itemStack = itemStack2.isEmpty() ? new ItemStack(Items.ITEM_FRAME) : itemStack2.copy();
                } else if (object instanceof AbstractMinecartEntity) {
                    var6_5 = (AbstractMinecartEntity)object;
                    itemStack = new ItemStack(switch (((AbstractMinecartEntity)var6_5).getMinecartType()) {
                        case AbstractMinecartEntity.Type.FURNACE -> Items.FURNACE_MINECART;
                        case AbstractMinecartEntity.Type.CHEST -> Items.CHEST_MINECART;
                        case AbstractMinecartEntity.Type.TNT -> Items.TNT_MINECART;
                        case AbstractMinecartEntity.Type.HOPPER -> Items.HOPPER_MINECART;
                        case AbstractMinecartEntity.Type.COMMAND_BLOCK -> Items.COMMAND_BLOCK_MINECART;
                        default -> Items.MINECART;
                    });
                } else if (object instanceof BoatEntity) {
                    itemStack = new ItemStack(((BoatEntity)object).getItemBoat());
                } else if (object instanceof ArmorStandEntity) {
                    itemStack = new ItemStack(Items.ARMOR_STAND);
                } else if (object instanceof EnderCrystalEntity) {
                    itemStack = new ItemStack(Items.END_CRYSTAL);
                } else {
                    var6_5 = SpawnEggItem.getEgg(((Entity)object).getType());
                    if (var6_5 == null) {
                        return;
                    }
                    itemStack = new ItemStack((IItemProvider)var6_5);
                }
            }
            if (itemStack.isEmpty()) {
                object = "";
                if (type == RayTraceResult.Type.BLOCK) {
                    object = Registry.BLOCK.getKey(this.world.getBlockState(((BlockRayTraceResult)this.objectMouseOver).getPos()).getBlock()).toString();
                } else if (type == RayTraceResult.Type.ENTITY) {
                    object = Registry.ENTITY_TYPE.getKey(((EntityRayTraceResult)this.objectMouseOver).getEntity().getType()).toString();
                }
                LOGGER.warn("Picking on: [{}] {} gave null item", (Object)type, object);
            } else {
                object = this.player.inventory;
                if (tileEntity != null) {
                    this.storeTEInStack(itemStack, tileEntity);
                }
                int n = ((PlayerInventory)object).getSlotFor(itemStack);
                if (bl) {
                    ((PlayerInventory)object).setPickedItemStack(itemStack);
                    this.playerController.sendSlotPacket(this.player.getHeldItem(Hand.MAIN_HAND), 36 + ((PlayerInventory)object).currentItem);
                } else if (n != -1) {
                    if (PlayerInventory.isHotbar(n)) {
                        ((PlayerInventory)object).currentItem = n;
                    } else {
                        this.playerController.pickItem(n);
                    }
                }
            }
        }
    }

    private ItemStack storeTEInStack(ItemStack itemStack, TileEntity tileEntity) {
        CompoundNBT compoundNBT = tileEntity.write(new CompoundNBT());
        if (itemStack.getItem() instanceof SkullItem && compoundNBT.contains("SkullOwner")) {
            CompoundNBT compoundNBT2 = compoundNBT.getCompound("SkullOwner");
            itemStack.getOrCreateTag().put("SkullOwner", compoundNBT2);
            return itemStack;
        }
        itemStack.setTagInfo("BlockEntityTag", compoundNBT);
        CompoundNBT compoundNBT3 = new CompoundNBT();
        ListNBT listNBT = new ListNBT();
        listNBT.add(StringNBT.valueOf("\"(+NBT)\""));
        compoundNBT3.put("Lore", listNBT);
        itemStack.setTagInfo("display", compoundNBT3);
        return itemStack;
    }

    public CrashReport addGraphicsAndWorldToCrashReport(CrashReport crashReport) {
        Minecraft.fillCrashReport(this.languageManager, this.launchedVersion, this.gameSettings, crashReport);
        if (this.world != null) {
            this.world.fillCrashReport(crashReport);
        }
        return crashReport;
    }

    public static void fillCrashReport(@Nullable LanguageManager languageManager, String string, @Nullable GameSettings gameSettings, CrashReport crashReport) {
        CrashReportCategory crashReportCategory = crashReport.getCategory();
        crashReportCategory.addDetail("Launched Version", () -> Minecraft.lambda$fillCrashReport$31(string));
        crashReportCategory.addDetail("Backend library", RenderSystem::getBackendDescription);
        crashReportCategory.addDetail("Backend API", RenderSystem::getApiDescription);
        crashReportCategory.addDetail("GL Caps", RenderSystem::getCapsString);
        crashReportCategory.addDetail("Using VBOs", Minecraft::lambda$fillCrashReport$32);
        crashReportCategory.addDetail("Is Modded", Minecraft::lambda$fillCrashReport$33);
        crashReportCategory.addDetail("Type", "Client (map_client.txt)");
        if (gameSettings != null) {
            String string2;
            if (instance != null && (string2 = instance.getGPUWarning().func_243499_m()) != null) {
                crashReportCategory.addDetail("GPU Warnings", string2);
            }
            crashReportCategory.addDetail("Graphics mode", (Object)gameSettings.graphicFanciness);
            crashReportCategory.addDetail("Resource Packs", () -> Minecraft.lambda$fillCrashReport$34(gameSettings));
        }
        if (languageManager != null) {
            crashReportCategory.addDetail("Current Language", () -> Minecraft.lambda$fillCrashReport$35(languageManager));
        }
        crashReportCategory.addDetail("CPU", PlatformDescriptors::getCpuInfo);
    }

    public static Minecraft getInstance() {
        return instance;
    }

    public CompletableFuture<Void> scheduleResourcesRefresh() {
        return this.supplyAsync(this::reloadResources).thenCompose(Minecraft::lambda$scheduleResourcesRefresh$36);
    }

    @Override
    public void fillSnooper(Snooper snooper) {
        snooper.addClientStat("fps", this.debugFPS);
        snooper.addClientStat("vsync_enabled", this.gameSettings.vsync);
        snooper.addClientStat("display_frequency", this.mainWindow.getRefreshRate());
        snooper.addClientStat("display_type", this.mainWindow.isFullscreen() ? "fullscreen" : "windowed");
        snooper.addClientStat("run_time", (Util.milliTime() - snooper.getMinecraftStartTimeMillis()) / 60L * 1000L);
        snooper.addClientStat("current_action", this.getCurrentAction());
        snooper.addClientStat("language", this.gameSettings.language == null ? "en_us" : this.gameSettings.language);
        String string = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? "little" : "big";
        snooper.addClientStat("endianness", string);
        snooper.addClientStat("subtitles", this.gameSettings.showSubtitles);
        snooper.addClientStat("touch", this.gameSettings.touchscreen ? "touch" : "mouse");
        int n = 0;
        for (ResourcePackInfo resourcePackInfo : this.resourcePackRepository.getEnabledPacks()) {
            if (resourcePackInfo.isAlwaysEnabled() || resourcePackInfo.isOrderLocked()) continue;
            snooper.addClientStat("resource_pack[" + n++ + "]", resourcePackInfo.getName());
        }
        snooper.addClientStat("resource_packs", n);
        if (this.integratedServer != null) {
            snooper.addClientStat("snooper_partner", this.integratedServer.getSnooper().getUniqueID());
        }
    }

    private String getCurrentAction() {
        if (this.integratedServer != null) {
            return this.integratedServer.getPublic() ? "hosting_lan" : "singleplayer";
        }
        if (this.currentServerData != null) {
            return this.currentServerData.isOnLAN() ? "playing_lan" : "multiplayer";
        }
        return "out_of_game";
    }

    public void setServerData(@Nullable ServerData serverData) {
        this.currentServerData = serverData;
    }

    @Nullable
    public ServerData getCurrentServerData() {
        return this.currentServerData;
    }

    public boolean isIntegratedServerRunning() {
        return this.integratedServerIsRunning;
    }

    public boolean isSingleplayer() {
        return this.integratedServerIsRunning && this.integratedServer != null;
    }

    @Nullable
    public IntegratedServer getIntegratedServer() {
        return this.integratedServer;
    }

    public Snooper getSnooper() {
        return this.snooper;
    }

    public Session getSession() {
        return this.session;
    }

    public PropertyMap getProfileProperties() {
        if (this.profileProperties.isEmpty()) {
            GameProfile gameProfile = this.getSessionService().fillProfileProperties(this.session.getProfile(), false);
            this.profileProperties.putAll(gameProfile.getProperties());
        }
        return this.profileProperties;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public TextureManager getTextureManager() {
        return this.textureManager;
    }

    public IResourceManager getResourceManager() {
        return this.resourceManager;
    }

    public ResourcePackList getResourcePackList() {
        return this.resourcePackRepository;
    }

    public DownloadingPackFinder getPackFinder() {
        return this.packFinder;
    }

    public File getFileResourcePacks() {
        return this.fileResourcepacks;
    }

    public LanguageManager getLanguageManager() {
        return this.languageManager;
    }

    public Function<ResourceLocation, TextureAtlasSprite> getAtlasSpriteGetter(ResourceLocation resourceLocation) {
        return this.modelManager.getAtlasTexture(resourceLocation)::getSprite;
    }

    public boolean isJava64bit() {
        return this.jvm64bit;
    }

    public boolean isGamePaused() {
        return this.isGamePaused;
    }

    public GPUWarning getGPUWarning() {
        return this.warningGPU;
    }

    public SoundHandler getSoundHandler() {
        return this.soundHandler;
    }

    public BackgroundMusicSelector getBackgroundMusicSelector() {
        if (this.currentScreen instanceof WinGameScreen) {
            return BackgroundMusicTracks.CREDITS_MUSIC;
        }
        if (this.player != null) {
            if (this.player.world.getDimensionKey() == World.THE_END) {
                return this.ingameGUI.getBossOverlay().shouldPlayEndBossMusic() ? BackgroundMusicTracks.DRAGON_FIGHT_MUSIC : BackgroundMusicTracks.END_MUSIC;
            }
            Biome.Category category = this.player.world.getBiome(this.player.getPosition()).getCategory();
            if (!this.musicTicker.isBackgroundMusicPlaying(BackgroundMusicTracks.UNDER_WATER_MUSIC) && (!this.player.canSwim() || category != Biome.Category.OCEAN && category != Biome.Category.RIVER)) {
                return this.player.world.getDimensionKey() != World.THE_NETHER && this.player.abilities.isCreativeMode && this.player.abilities.allowFlying ? BackgroundMusicTracks.CREATIVE_MODE_MUSIC : this.world.getBiomeManager().getBiomeAtPosition(this.player.getPosition()).getBackgroundMusic().orElse(BackgroundMusicTracks.WORLD_MUSIC);
            }
            return BackgroundMusicTracks.UNDER_WATER_MUSIC;
        }
        return BackgroundMusicTracks.MAIN_MENU_MUSIC;
    }

    public MinecraftSessionService getSessionService() {
        return this.sessionService;
    }

    public SkinManager getSkinManager() {
        return this.skinManager;
    }

    @Nullable
    public Entity getRenderViewEntity() {
        return this.renderViewEntity;
    }

    public void setRenderViewEntity(Entity entity2) {
        this.renderViewEntity = entity2;
        this.gameRenderer.loadEntityShader(entity2);
    }

    public boolean isEntityGlowing(Entity entity2) {
        return entity2.isGlowing() || this.player != null && this.player.isSpectator() && this.gameSettings.keyBindSpectatorOutlines.isKeyDown() && entity2.getType() == EntityType.PLAYER;
    }

    @Override
    protected Thread getExecutionThread() {
        return this.thread;
    }

    @Override
    protected Runnable wrapTask(Runnable runnable) {
        return runnable;
    }

    @Override
    protected boolean canRun(Runnable runnable) {
        return false;
    }

    public BlockRendererDispatcher getBlockRendererDispatcher() {
        return this.blockRenderDispatcher;
    }

    public EntityRendererManager getRenderManager() {
        return this.renderManager;
    }

    public ItemRenderer getItemRenderer() {
        return this.itemRenderer;
    }

    public FirstPersonRenderer getFirstPersonRenderer() {
        return this.firstPersonRenderer;
    }

    public <T> IMutableSearchTree<T> getSearchTree(SearchTreeManager.Key<T> key) {
        return this.searchTreeManager.get(key);
    }

    public FrameTimer getFrameTimer() {
        return this.frameTimer;
    }

    public boolean isConnectedToRealms() {
        return this.connectedToRealms;
    }

    public void setConnectedToRealms(boolean bl) {
        this.connectedToRealms = bl;
    }

    public DataFixer getDataFixer() {
        return this.dataFixer;
    }

    public float getRenderPartialTicks() {
        return this.timer.renderPartialTicks;
    }

    public float getTickLength() {
        return this.timer.elapsedPartialTicks;
    }

    public BlockColors getBlockColors() {
        return this.blockColors;
    }

    public boolean isReducedDebug() {
        return this.player != null && this.player.hasReducedDebug() || this.gameSettings.reducedDebugInfo;
    }

    public ToastGui getToastGui() {
        return this.toastGui;
    }

    public Tutorial getTutorial() {
        return this.tutorial;
    }

    public boolean isGameFocused() {
        return this.isWindowFocused;
    }

    public CreativeSettings getCreativeSettings() {
        return this.creativeSettings;
    }

    public ModelManager getModelManager() {
        return this.modelManager;
    }

    public PaintingSpriteUploader getPaintingSpriteUploader() {
        return this.paintingSprites;
    }

    public PotionSpriteUploader getPotionSpriteUploader() {
        return this.potionSprites;
    }

    @Override
    public void setGameFocused(boolean bl) {
        this.isWindowFocused = bl;
    }

    public IProfiler getProfiler() {
        return this.profiler;
    }

    public MinecraftGame getMinecraftGame() {
        return this.game;
    }

    public Splashes getSplashes() {
        return this.splashes;
    }

    @Nullable
    public LoadingGui getLoadingGui() {
        return this.loadingGui;
    }

    public FilterManager func_244599_aA() {
        return this.field_244597_aC;
    }

    public boolean isRenderOnThread() {
        return true;
    }

    public MainWindow getMainWindow() {
        return this.mainWindow;
    }

    public RenderTypeBuffers getRenderTypeBuffers() {
        return this.renderTypeBuffers;
    }

    private static ResourcePackInfo makePackInfo(String string, boolean bl, Supplier<IResourcePack> supplier, IResourcePack iResourcePack, PackMetadataSection packMetadataSection, ResourcePackInfo.Priority priority, IPackNameDecorator iPackNameDecorator) {
        int n = packMetadataSection.getPackFormat();
        Supplier<IResourcePack> supplier2 = supplier;
        if (n <= 3) {
            supplier2 = Minecraft.wrapV3(supplier);
        }
        if (n <= 4) {
            supplier2 = Minecraft.wrapV4(supplier2);
        }
        return new ResourcePackInfo(string, bl, supplier2, iResourcePack, packMetadataSection, priority, iPackNameDecorator);
    }

    private static Supplier<IResourcePack> wrapV3(Supplier<IResourcePack> supplier) {
        return () -> Minecraft.lambda$wrapV3$37(supplier);
    }

    private static Supplier<IResourcePack> wrapV4(Supplier<IResourcePack> supplier) {
        return () -> Minecraft.lambda$wrapV4$38(supplier);
    }

    public void setMipmapLevels(int n) {
        this.modelManager.setMaxMipmapLevel(n);
    }

    public int getDebugFPS() {
        return this.debugFPS;
    }

    private static IResourcePack lambda$wrapV4$38(Supplier supplier) {
        return new LegacyResourcePackWrapperV4((IResourcePack)supplier.get());
    }

    private static IResourcePack lambda$wrapV3$37(Supplier supplier) {
        return new LegacyResourcePackWrapper((IResourcePack)supplier.get(), LegacyResourcePackWrapper.NEW_TO_LEGACY_MAP);
    }

    private static CompletionStage lambda$scheduleResourcesRefresh$36(CompletableFuture completableFuture) {
        return completableFuture;
    }

    private static String lambda$fillCrashReport$35(LanguageManager languageManager) throws Exception {
        return languageManager.getCurrentLanguage().toString();
    }

    private static String lambda$fillCrashReport$34(GameSettings gameSettings) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : gameSettings.resourcePacks) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(string);
            if (!gameSettings.incompatibleResourcePacks.contains(string)) continue;
            stringBuilder.append(" (incompatible)");
        }
        return stringBuilder.toString();
    }

    private static String lambda$fillCrashReport$33() throws Exception {
        String string = ClientBrandRetriever.getClientModName();
        if (!"vanilla".equals(string)) {
            return "Definitely; Client brand changed to '" + string + "'";
        }
        return Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.";
    }

    private static String lambda$fillCrashReport$32() throws Exception {
        return "Yes";
    }

    private static String lambda$fillCrashReport$31(String string) throws Exception {
        return string;
    }

    private void lambda$deleteWorld$30(Runnable runnable, String string, boolean bl) {
        if (bl) {
            runnable.run();
        } else {
            this.displayGuiScreen(null);
            try (SaveFormat.LevelSave levelSave = this.saveFormat.getLevelSave(string);){
                levelSave.deleteSave();
            } catch (IOException iOException) {
                SystemToast.func_238538_b_(this, string);
                LOGGER.error("Failed to delete world {}", (Object)string, (Object)iOException);
            }
        }
    }

    private void lambda$deleteWorld$29(String string, Runnable runnable, boolean bl, boolean bl2) {
        if (bl) {
            EditWorldScreen.func_241651_a_(this.saveFormat, string);
        }
        runnable.run();
    }

    private void lambda$loadWorld$28(String string, DynamicRegistries.Impl impl, Function function, Function4 function4, boolean bl) {
        this.loadWorld(string, impl, function, function4, bl, WorldSelectionType.NONE);
    }

    private static void lambda$loadWorld$27(ITextComponent iTextComponent) {
    }

    private IntegratedServer lambda$loadWorld$26(DynamicRegistries.Impl impl, SaveFormat.LevelSave levelSave, PackManager packManager, IServerConfiguration iServerConfiguration, MinecraftSessionService minecraftSessionService, GameProfileRepository gameProfileRepository, PlayerProfileCache playerProfileCache, Thread thread2) {
        return new IntegratedServer(thread2, this, impl, levelSave, packManager.getResourcePacks(), packManager.getDataPackRegistries(), iServerConfiguration, minecraftSessionService, gameProfileRepository, playerProfileCache, this::lambda$loadWorld$25);
    }

    private IChunkStatusListener lambda$loadWorld$25(int n) {
        TrackingChunkStatusListener trackingChunkStatusListener = new TrackingChunkStatusListener(n + 0);
        trackingChunkStatusListener.startTracking();
        this.refChunkStatusListener.set(trackingChunkStatusListener);
        return new ChainedChunkStatusListener(trackingChunkStatusListener, this.queueChunkTracking::add);
    }

    private void lambda$loadWorld$24(String string, DynamicRegistries.Impl impl, Function function, Function4 function4, WorldSelectionType worldSelectionType) {
        this.loadWorld(string, impl, function, function4, true, worldSelectionType);
    }

    private static IServerConfiguration lambda$createWorld$23(DynamicRegistries.Impl impl, DimensionGeneratorSettings dimensionGeneratorSettings, WorldSettings worldSettings, SaveFormat.LevelSave levelSave, DynamicRegistries.Impl impl2, IResourceManager iResourceManager, DatapackCodec datapackCodec) {
        WorldGenSettingsExport<JsonElement> worldGenSettingsExport = WorldGenSettingsExport.create(JsonOps.INSTANCE, impl);
        WorldSettingsImport<JsonElement> worldSettingsImport = WorldSettingsImport.create(JsonOps.INSTANCE, iResourceManager, impl);
        DataResult dataResult = DimensionGeneratorSettings.field_236201_a_.encodeStart(worldGenSettingsExport, dimensionGeneratorSettings).setLifecycle(Lifecycle.stable()).flatMap(arg_0 -> Minecraft.lambda$createWorld$22(worldSettingsImport, arg_0));
        DimensionGeneratorSettings dimensionGeneratorSettings2 = dataResult.resultOrPartial(Util.func_240982_a_("Error reading worldgen settings after loading data packs: ", LOGGER::error)).orElse(dimensionGeneratorSettings);
        return new ServerWorldInfo(worldSettings, dimensionGeneratorSettings2, dataResult.lifecycle());
    }

    private static DataResult lambda$createWorld$22(WorldSettingsImport worldSettingsImport, JsonElement jsonElement) {
        return DimensionGeneratorSettings.field_236201_a_.parse(worldSettingsImport, jsonElement);
    }

    private static DatapackCodec lambda$createWorld$21(WorldSettings worldSettings, SaveFormat.LevelSave levelSave) {
        return worldSettings.getDatapackCodec();
    }

    private static boolean lambda$runTick$20() {
        return false;
    }

    private void lambda$runTick$19() {
        this.currentScreen.tick();
    }

    private static void lambda$runGameLoop$18(CompletableFuture completableFuture) {
        completableFuture.complete(null);
    }

    private void lambda$reloadResources$17(CompletableFuture completableFuture, Optional optional) {
        Util.acceptOrElse(optional, this::restoreResourcePacks, () -> this.lambda$reloadResources$16(completableFuture));
    }

    private void lambda$reloadResources$16(CompletableFuture completableFuture) {
        this.worldRenderer.loadRenderers();
        completableFuture.complete(null);
    }

    private static Stream lambda$populateSearchTreeManager$15(RecipeList recipeList) {
        return recipeList.getRecipes().stream().map(Minecraft::lambda$populateSearchTreeManager$14);
    }

    private static ResourceLocation lambda$populateSearchTreeManager$14(IRecipe iRecipe) {
        return Registry.ITEM.getKey(iRecipe.getRecipeOutput().getItem());
    }

    private static Stream lambda$populateSearchTreeManager$13(RecipeList recipeList) {
        return recipeList.getRecipes().stream().flatMap(Minecraft::lambda$populateSearchTreeManager$10).map(Minecraft::lambda$populateSearchTreeManager$11).filter(Minecraft::lambda$populateSearchTreeManager$12);
    }

    private static boolean lambda$populateSearchTreeManager$12(String string) {
        return !string.isEmpty();
    }

    private static String lambda$populateSearchTreeManager$11(ITextComponent iTextComponent) {
        return TextFormatting.getTextWithoutFormattingCodes(iTextComponent.getString()).trim();
    }

    private static Stream lambda$populateSearchTreeManager$10(IRecipe iRecipe) {
        return iRecipe.getRecipeOutput().getTooltip(null, ITooltipFlag.TooltipFlags.NORMAL).stream();
    }

    private static void lambda$populateSearchTreeManager$9(SearchTree searchTree, SearchTreeReloadable searchTreeReloadable, ItemStack itemStack) {
        searchTree.func_217872_a(itemStack);
        searchTreeReloadable.func_217872_a(itemStack);
    }

    private static Stream lambda$populateSearchTreeManager$8(ItemStack itemStack) {
        return ItemTags.getCollection().getOwningTags(itemStack.getItem()).stream();
    }

    private static Stream lambda$populateSearchTreeManager$7(ItemStack itemStack) {
        return Stream.of(Registry.ITEM.getKey(itemStack.getItem()));
    }

    private static Stream lambda$populateSearchTreeManager$6(ItemStack itemStack) {
        return itemStack.getTooltip(null, ITooltipFlag.TooltipFlags.NORMAL).stream().map(Minecraft::lambda$populateSearchTreeManager$4).filter(Minecraft::lambda$populateSearchTreeManager$5);
    }

    private static boolean lambda$populateSearchTreeManager$5(String string) {
        return !string.isEmpty();
    }

    private static String lambda$populateSearchTreeManager$4(ITextComponent iTextComponent) {
        return TextFormatting.getTextWithoutFormattingCodes(iTextComponent.getString()).trim();
    }

    private void lambda$throwResourcePackLoadError$3(ITextComponent iTextComponent) {
        ToastGui toastGui = this.getToastGui();
        SystemToast.addOrUpdate(toastGui, SystemToast.Type.PACK_LOAD_FAILURE, new TranslationTextComponent("resourcePack.load_fail"), iTextComponent);
    }

    private void lambda$new$2(Optional optional) {
        Util.acceptOrElse(optional, this::restoreResourcePacks, this::lambda$new$1);
    }

    private void lambda$new$1() {
        if (SharedConstants.developmentMode) {
            this.checkMissingData();
        }
    }

    private int lambda$new$0() {
        return this.gameTime;
    }

    static {
        LOGGER = LogManager.getLogger();
        IS_RUNNING_ON_MAC = Util.getOSType() == Util.OS.OSX;
        DEFAULT_FONT_RENDERER_NAME = new ResourceLocation("default");
        UNIFORM_FONT_RENDERER_NAME = new ResourceLocation("uniform");
        standardGalacticFontRenderer = new ResourceLocation("alt");
        RESOURCE_RELOAD_INIT_TASK = CompletableFuture.completedFuture(Unit.INSTANCE);
        field_244596_I = new TranslationTextComponent("multiplayer.socialInteractions.not_available");
        memoryReserve = new byte[0xA00000];
    }

    static enum WorldSelectionType {
        NONE,
        CREATE,
        BACKUP;

    }

    public static final class PackManager
    implements AutoCloseable {
        private final ResourcePackList resourcePackList;
        private final DataPackRegistries datapackRegistries;
        private final IServerConfiguration serverConfiguration;

        private PackManager(ResourcePackList resourcePackList, DataPackRegistries dataPackRegistries, IServerConfiguration iServerConfiguration) {
            this.resourcePackList = resourcePackList;
            this.datapackRegistries = dataPackRegistries;
            this.serverConfiguration = iServerConfiguration;
        }

        public ResourcePackList getResourcePacks() {
            return this.resourcePackList;
        }

        public DataPackRegistries getDataPackRegistries() {
            return this.datapackRegistries;
        }

        public IServerConfiguration getServerConfiguration() {
            return this.serverConfiguration;
        }

        @Override
        public void close() {
            this.resourcePackList.close();
            this.datapackRegistries.close();
        }
    }
}

