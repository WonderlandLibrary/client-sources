/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.longs.LongIterator;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.net.Proxy;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.advancements.FunctionManager;
import net.minecraft.block.Block;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ICommandSource;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.SpawnLocationHelper;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.loot.LootPredicateManager;
import net.minecraft.loot.LootTableManager;
import net.minecraft.network.NetworkSystem;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.play.server.SServerDifficultyPacket;
import net.minecraft.network.play.server.SUpdateTimePacket;
import net.minecraft.profiler.EmptyProfiler;
import net.minecraft.profiler.IProfileResult;
import net.minecraft.profiler.IProfiler;
import net.minecraft.profiler.ISnooperInfo;
import net.minecraft.profiler.LongTickDetector;
import net.minecraft.profiler.Snooper;
import net.minecraft.profiler.TimeTracker;
import net.minecraft.resources.DataPackRegistries;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.scoreboard.ScoreboardSaveData;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.CustomServerBossInfoManager;
import net.minecraft.server.management.OpEntry;
import net.minecraft.server.management.PlayerList;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.server.management.WhiteList;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.test.TestCollection;
import net.minecraft.util.CryptException;
import net.minecraft.util.CryptManager;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.concurrent.RecursiveEventLoop;
import net.minecraft.util.concurrent.TickDelayedTask;
import net.minecraft.util.datafix.codec.DatapackCodec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.filter.IChatFilter;
import net.minecraft.village.VillageSiege;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.ForcedChunksSaveData;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.border.IBorderListener;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.listener.IChunkStatusListener;
import net.minecraft.world.chunk.listener.IChunkStatusListenerFactory;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;
import net.minecraft.world.spawner.CatSpawner;
import net.minecraft.world.spawner.ISpecialSpawner;
import net.minecraft.world.spawner.PatrolSpawner;
import net.minecraft.world.spawner.PhantomSpawner;
import net.minecraft.world.spawner.WanderingTraderSpawner;
import net.minecraft.world.storage.CommandStorage;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.FolderName;
import net.minecraft.world.storage.IServerConfiguration;
import net.minecraft.world.storage.IServerWorldInfo;
import net.minecraft.world.storage.IWorldInfo;
import net.minecraft.world.storage.PlayerData;
import net.minecraft.world.storage.SaveFormat;
import net.minecraft.world.storage.WorldSavedDataCallableSave;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class MinecraftServer
extends RecursiveEventLoop<TickDelayedTask>
implements ISnooperInfo,
ICommandSource,
AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final File USER_CACHE_FILE = new File("usercache.json");
    public static final WorldSettings DEMO_WORLD_SETTINGS = new WorldSettings("Demo World", GameType.SURVIVAL, false, Difficulty.NORMAL, false, new GameRules(), DatapackCodec.VANILLA_CODEC);
    protected final SaveFormat.LevelSave anvilConverterForAnvilFile;
    protected final PlayerData playerDataManager;
    private final Snooper snooper = new Snooper("server", this, Util.milliTime());
    private final List<Runnable> tickables = Lists.newArrayList();
    private final TimeTracker timeTracker = new TimeTracker(Util.nanoTimeSupplier, this::getTickCounter);
    private IProfiler profiler = EmptyProfiler.INSTANCE;
    private final NetworkSystem networkSystem;
    private final IChunkStatusListenerFactory chunkStatusListenerFactory;
    private final ServerStatusResponse statusResponse = new ServerStatusResponse();
    private final Random random = new Random();
    private final DataFixer dataFixer;
    private String hostname;
    private int serverPort = -1;
    protected final DynamicRegistries.Impl field_240767_f_;
    private final Map<RegistryKey<World>, ServerWorld> worlds = Maps.newLinkedHashMap();
    private PlayerList playerList;
    private volatile boolean serverRunning = true;
    private boolean serverStopped;
    private int tickCounter;
    protected final Proxy serverProxy;
    private boolean onlineMode;
    private boolean preventProxyConnections;
    private boolean pvpEnabled;
    private boolean allowFlight;
    @Nullable
    private String motd;
    private int buildLimit;
    private int maxPlayerIdleMinutes;
    public final long[] tickTimeArray = new long[100];
    @Nullable
    private KeyPair serverKeyPair;
    @Nullable
    private String serverOwner;
    private boolean isDemo;
    private String resourcePackUrl = "";
    private String resourcePackHash = "";
    private volatile boolean serverIsRunning;
    private long timeOfLastWarning;
    private boolean startProfiling;
    private boolean isGamemodeForced;
    private final MinecraftSessionService sessionService;
    private final GameProfileRepository profileRepo;
    private final PlayerProfileCache profileCache;
    private long nanoTimeSinceStatusRefresh;
    private final Thread serverThread;
    private long serverTime = Util.milliTime();
    private long runTasksUntil;
    private boolean isRunningScheduledTasks;
    private boolean worldIconSet;
    private final ResourcePackList resourcePacks;
    private final ServerScoreboard scoreboard = new ServerScoreboard(this);
    @Nullable
    private CommandStorage field_229733_al_;
    private final CustomServerBossInfoManager customBossEvents = new CustomServerBossInfoManager();
    private final FunctionManager functionManager;
    private final FrameTimer frameTimer = new FrameTimer();
    private boolean whitelistEnabled;
    private float tickTime;
    private final Executor backgroundExecutor;
    @Nullable
    private String serverId;
    private DataPackRegistries resourceManager;
    private final TemplateManager field_240765_ak_;
    protected final IServerConfiguration field_240768_i_;

    public static <S extends MinecraftServer> S func_240784_a_(Function<Thread, S> function) {
        AtomicReference<MinecraftServer> atomicReference = new AtomicReference<MinecraftServer>();
        Thread thread2 = new Thread(() -> MinecraftServer.lambda$func_240784_a_$0(atomicReference), "Server thread");
        thread2.setUncaughtExceptionHandler(MinecraftServer::lambda$func_240784_a_$1);
        MinecraftServer minecraftServer = (MinecraftServer)function.apply(thread2);
        atomicReference.set(minecraftServer);
        thread2.start();
        return (S)minecraftServer;
    }

    public MinecraftServer(Thread thread2, DynamicRegistries.Impl impl, SaveFormat.LevelSave levelSave, IServerConfiguration iServerConfiguration, ResourcePackList resourcePackList, Proxy proxy, DataFixer dataFixer, DataPackRegistries dataPackRegistries, MinecraftSessionService minecraftSessionService, GameProfileRepository gameProfileRepository, PlayerProfileCache playerProfileCache, IChunkStatusListenerFactory iChunkStatusListenerFactory) {
        super("Server");
        this.field_240767_f_ = impl;
        this.field_240768_i_ = iServerConfiguration;
        this.serverProxy = proxy;
        this.resourcePacks = resourcePackList;
        this.resourceManager = dataPackRegistries;
        this.sessionService = minecraftSessionService;
        this.profileRepo = gameProfileRepository;
        this.profileCache = playerProfileCache;
        this.networkSystem = new NetworkSystem(this);
        this.chunkStatusListenerFactory = iChunkStatusListenerFactory;
        this.anvilConverterForAnvilFile = levelSave;
        this.playerDataManager = levelSave.getPlayerDataManager();
        this.dataFixer = dataFixer;
        this.functionManager = new FunctionManager(this, dataPackRegistries.getFunctionReloader());
        this.field_240765_ak_ = new TemplateManager(dataPackRegistries.getResourceManager(), levelSave, dataFixer);
        this.serverThread = thread2;
        this.backgroundExecutor = Util.getServerExecutor();
    }

    private void func_213204_a(DimensionSavedDataManager dimensionSavedDataManager) {
        ScoreboardSaveData scoreboardSaveData = dimensionSavedDataManager.getOrCreate(ScoreboardSaveData::new, "scoreboard");
        scoreboardSaveData.setScoreboard(this.getScoreboard());
        this.getScoreboard().addDirtyRunnable(new WorldSavedDataCallableSave(scoreboardSaveData));
    }

    protected abstract boolean init() throws IOException;

    public static void func_240777_a_(SaveFormat.LevelSave levelSave) {
        if (levelSave.isSaveFormatOutdated()) {
            LOGGER.info("Converting map!");
            levelSave.convertRegions(new IProgressUpdate(){
                private long startTime = Util.milliTime();

                @Override
                public void displaySavingString(ITextComponent iTextComponent) {
                }

                @Override
                public void resetProgressAndMessage(ITextComponent iTextComponent) {
                }

                @Override
                public void setLoadingProgress(int n) {
                    if (Util.milliTime() - this.startTime >= 1000L) {
                        this.startTime = Util.milliTime();
                        LOGGER.info("Converting... {}%", (Object)n);
                    }
                }

                @Override
                public void setDoneWorking() {
                }

                @Override
                public void displayLoadingString(ITextComponent iTextComponent) {
                }
            });
        }
    }

    protected void func_240800_l__() {
        this.setResourcePackFromWorld();
        this.field_240768_i_.addServerBranding(this.getServerModName(), this.func_230045_q_().isPresent());
        IChunkStatusListener iChunkStatusListener = this.chunkStatusListenerFactory.create(11);
        this.func_240787_a_(iChunkStatusListener);
        this.func_230543_p_();
        this.loadInitialChunks(iChunkStatusListener);
    }

    protected void func_230543_p_() {
    }

    protected void func_240787_a_(IChunkStatusListener iChunkStatusListener) {
        ChunkGenerator chunkGenerator;
        DimensionType dimensionType;
        IServerWorldInfo iServerWorldInfo = this.field_240768_i_.getServerWorldInfo();
        DimensionGeneratorSettings dimensionGeneratorSettings = this.field_240768_i_.getDimensionGeneratorSettings();
        boolean bl = dimensionGeneratorSettings.func_236227_h_();
        long l = dimensionGeneratorSettings.getSeed();
        long l2 = BiomeManager.getHashedSeed(l);
        ImmutableList<ISpecialSpawner> immutableList = ImmutableList.of(new PhantomSpawner(), new PatrolSpawner(), new CatSpawner(), new VillageSiege(), new WanderingTraderSpawner(iServerWorldInfo));
        SimpleRegistry<Dimension> simpleRegistry = dimensionGeneratorSettings.func_236224_e_();
        Dimension dimension = simpleRegistry.getValueForKey(Dimension.OVERWORLD);
        if (dimension == null) {
            dimensionType = this.field_240767_f_.func_230520_a_().getOrThrow(DimensionType.OVERWORLD);
            chunkGenerator = DimensionGeneratorSettings.func_242750_a(this.field_240767_f_.getRegistry(Registry.BIOME_KEY), this.field_240767_f_.getRegistry(Registry.NOISE_SETTINGS_KEY), new Random().nextLong());
        } else {
            dimensionType = dimension.getDimensionType();
            chunkGenerator = dimension.getChunkGenerator();
        }
        ServerWorld serverWorld = new ServerWorld(this, this.backgroundExecutor, this.anvilConverterForAnvilFile, iServerWorldInfo, World.OVERWORLD, dimensionType, iChunkStatusListener, chunkGenerator, bl, l2, immutableList, true);
        this.worlds.put(World.OVERWORLD, serverWorld);
        DimensionSavedDataManager dimensionSavedDataManager = serverWorld.getSavedData();
        this.func_213204_a(dimensionSavedDataManager);
        this.field_229733_al_ = new CommandStorage(dimensionSavedDataManager);
        WorldBorder worldBorder = serverWorld.getWorldBorder();
        worldBorder.deserialize(iServerWorldInfo.getWorldBorderSerializer());
        if (!iServerWorldInfo.isInitialized()) {
            try {
                MinecraftServer.func_240786_a_(serverWorld, iServerWorldInfo, dimensionGeneratorSettings.hasBonusChest(), bl, true);
                iServerWorldInfo.setInitialized(true);
                if (bl) {
                    this.func_240778_a_(this.field_240768_i_);
                }
            } catch (Throwable throwable) {
                CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Exception initializing level");
                try {
                    serverWorld.fillCrashReport(crashReport);
                } catch (Throwable throwable2) {
                    // empty catch block
                }
                throw new ReportedException(crashReport);
            }
            iServerWorldInfo.setInitialized(true);
        }
        this.getPlayerList().func_212504_a(serverWorld);
        if (this.field_240768_i_.getCustomBossEventData() != null) {
            this.getCustomBossEvents().read(this.field_240768_i_.getCustomBossEventData());
        }
        for (Map.Entry<RegistryKey<Dimension>, Dimension> entry : simpleRegistry.getEntries()) {
            RegistryKey<Dimension> registryKey = entry.getKey();
            if (registryKey == Dimension.OVERWORLD) continue;
            RegistryKey<World> registryKey2 = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, registryKey.getLocation());
            DimensionType dimensionType2 = entry.getValue().getDimensionType();
            ChunkGenerator chunkGenerator2 = entry.getValue().getChunkGenerator();
            DerivedWorldInfo derivedWorldInfo = new DerivedWorldInfo(this.field_240768_i_, iServerWorldInfo);
            ServerWorld serverWorld2 = new ServerWorld(this, this.backgroundExecutor, this.anvilConverterForAnvilFile, derivedWorldInfo, registryKey2, dimensionType2, iChunkStatusListener, chunkGenerator2, bl, l2, ImmutableList.of(), false);
            worldBorder.addListener(new IBorderListener.Impl(serverWorld2.getWorldBorder()));
            this.worlds.put(registryKey2, serverWorld2);
        }
    }

    private static void func_240786_a_(ServerWorld serverWorld, IServerWorldInfo iServerWorldInfo, boolean bl, boolean bl2, boolean bl3) {
        ChunkGenerator chunkGenerator = serverWorld.getChunkProvider().getChunkGenerator();
        if (!bl3) {
            iServerWorldInfo.setSpawn(BlockPos.ZERO.up(chunkGenerator.getGroundHeight()), 0.0f);
        } else if (bl2) {
            iServerWorldInfo.setSpawn(BlockPos.ZERO.up(), 0.0f);
        } else {
            ChunkPos chunkPos;
            BiomeProvider biomeProvider = chunkGenerator.getBiomeProvider();
            Random random2 = new Random(serverWorld.getSeed());
            BlockPos blockPos = biomeProvider.findBiomePosition(0, serverWorld.getSeaLevel(), 0, 256, MinecraftServer::lambda$func_240786_a_$2, random2);
            ChunkPos chunkPos2 = chunkPos = blockPos == null ? new ChunkPos(0, 0) : new ChunkPos(blockPos);
            if (blockPos == null) {
                LOGGER.warn("Unable to find spawn biome");
            }
            boolean bl4 = false;
            for (Block block : BlockTags.VALID_SPAWN.getAllElements()) {
                if (!biomeProvider.getSurfaceBlocks().contains(block.getDefaultState())) continue;
                bl4 = true;
                break;
            }
            iServerWorldInfo.setSpawn(chunkPos.asBlockPos().add(8, chunkGenerator.getGroundHeight(), 8), 0.0f);
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            int n4 = -1;
            int n5 = 32;
            for (int i = 0; i < 1024; ++i) {
                BlockPos blockPos2;
                if (n > -16 && n <= 16 && n2 > -16 && n2 <= 16 && (blockPos2 = SpawnLocationHelper.func_241094_a_(serverWorld, new ChunkPos(chunkPos.x + n, chunkPos.z + n2), bl4)) != null) {
                    iServerWorldInfo.setSpawn(blockPos2, 0.0f);
                    break;
                }
                if (n == n2 || n < 0 && n == -n2 || n > 0 && n == 1 - n2) {
                    int n6 = n3;
                    n3 = -n4;
                    n4 = n6;
                }
                n += n3;
                n2 += n4;
            }
            if (bl) {
                ConfiguredFeature<?, ?> configuredFeature = Features.BONUS_CHEST;
                configuredFeature.func_242765_a(serverWorld, chunkGenerator, serverWorld.rand, new BlockPos(iServerWorldInfo.getSpawnX(), iServerWorldInfo.getSpawnY(), iServerWorldInfo.getSpawnZ()));
            }
        }
    }

    private void func_240778_a_(IServerConfiguration iServerConfiguration) {
        iServerConfiguration.setDifficulty(Difficulty.PEACEFUL);
        iServerConfiguration.setDifficultyLocked(true);
        IServerWorldInfo iServerWorldInfo = iServerConfiguration.getServerWorldInfo();
        iServerWorldInfo.setRaining(false);
        iServerWorldInfo.setThundering(false);
        iServerWorldInfo.setClearWeatherTime(1000000000);
        iServerWorldInfo.setDayTime(6000L);
        iServerWorldInfo.setGameType(GameType.SPECTATOR);
    }

    private void loadInitialChunks(IChunkStatusListener iChunkStatusListener) {
        ServerWorld serverWorld = this.func_241755_D_();
        LOGGER.info("Preparing start region for dimension {}", (Object)serverWorld.getDimensionKey().getLocation());
        BlockPos blockPos = serverWorld.getSpawnPoint();
        iChunkStatusListener.start(new ChunkPos(blockPos));
        ServerChunkProvider serverChunkProvider = serverWorld.getChunkProvider();
        serverChunkProvider.getLightManager().func_215598_a(500);
        this.serverTime = Util.milliTime();
        serverChunkProvider.registerTicket(TicketType.START, new ChunkPos(blockPos), 11, Unit.INSTANCE);
        while (serverChunkProvider.getLoadedChunksCount() != 441) {
            this.serverTime = Util.milliTime() + 10L;
            this.runScheduledTasks();
        }
        this.serverTime = Util.milliTime() + 10L;
        this.runScheduledTasks();
        for (ServerWorld serverWorld2 : this.worlds.values()) {
            ForcedChunksSaveData forcedChunksSaveData = serverWorld2.getSavedData().get(ForcedChunksSaveData::new, "chunks");
            if (forcedChunksSaveData == null) continue;
            LongIterator longIterator = forcedChunksSaveData.getChunks().iterator();
            while (longIterator.hasNext()) {
                long l = longIterator.nextLong();
                ChunkPos chunkPos = new ChunkPos(l);
                serverWorld2.getChunkProvider().forceChunk(chunkPos, false);
            }
        }
        this.serverTime = Util.milliTime() + 10L;
        this.runScheduledTasks();
        iChunkStatusListener.stop();
        serverChunkProvider.getLightManager().func_215598_a(5);
        this.func_240794_aZ_();
    }

    protected void setResourcePackFromWorld() {
        File file = this.anvilConverterForAnvilFile.resolveFilePath(FolderName.RESOURCES_ZIP).toFile();
        if (file.isFile()) {
            String string = this.anvilConverterForAnvilFile.getSaveName();
            try {
                this.setResourcePack("level://" + URLEncoder.encode(string, StandardCharsets.UTF_8.toString()) + "/resources.zip", "");
            } catch (UnsupportedEncodingException unsupportedEncodingException) {
                LOGGER.warn("Something went wrong url encoding {}", (Object)string);
            }
        }
    }

    public GameType getGameType() {
        return this.field_240768_i_.getGameType();
    }

    public boolean isHardcore() {
        return this.field_240768_i_.isHardcore();
    }

    public abstract int getOpPermissionLevel();

    public abstract int getFunctionLevel();

    public abstract boolean allowLoggingRcon();

    public boolean save(boolean bl, boolean bl2, boolean bl3) {
        boolean bl4 = false;
        for (ServerWorld object2 : this.getWorlds()) {
            if (!bl) {
                LOGGER.info("Saving chunks for level '{}'/{}", (Object)object2, (Object)object2.getDimensionKey().getLocation());
            }
            object2.save(null, bl2, object2.disableLevelSaving && !bl3);
            bl4 = true;
        }
        ServerWorld serverWorld = this.func_241755_D_();
        IServerWorldInfo iServerWorldInfo = this.field_240768_i_.getServerWorldInfo();
        iServerWorldInfo.setWorldBorderSerializer(serverWorld.getWorldBorder().getSerializer());
        this.field_240768_i_.setCustomBossEventData(this.getCustomBossEvents().write());
        this.anvilConverterForAnvilFile.saveLevel(this.field_240767_f_, this.field_240768_i_, this.getPlayerList().getHostPlayerData());
        return bl4;
    }

    @Override
    public void close() {
        this.stopServer();
    }

    protected void stopServer() {
        LOGGER.info("Stopping server");
        if (this.getNetworkSystem() != null) {
            this.getNetworkSystem().terminateEndpoints();
        }
        if (this.playerList != null) {
            LOGGER.info("Saving players");
            this.playerList.saveAllPlayerData();
            this.playerList.removeAllPlayers();
        }
        LOGGER.info("Saving worlds");
        for (ServerWorld serverWorld : this.getWorlds()) {
            if (serverWorld == null) continue;
            serverWorld.disableLevelSaving = false;
        }
        this.save(false, true, true);
        for (ServerWorld serverWorld : this.getWorlds()) {
            if (serverWorld == null) continue;
            try {
                serverWorld.close();
            } catch (IOException iOException) {
                LOGGER.error("Exception closing the level", (Throwable)iOException);
            }
        }
        if (this.snooper.isSnooperRunning()) {
            this.snooper.stop();
        }
        this.resourceManager.close();
        try {
            this.anvilConverterForAnvilFile.close();
        } catch (IOException iOException) {
            LOGGER.error("Failed to unlock level {}", (Object)this.anvilConverterForAnvilFile.getSaveName(), (Object)iOException);
        }
    }

    public String getServerHostname() {
        return this.hostname;
    }

    public void setHostname(String string) {
        this.hostname = string;
    }

    public boolean isServerRunning() {
        return this.serverRunning;
    }

    public void initiateShutdown(boolean bl) {
        this.serverRunning = false;
        if (bl) {
            try {
                this.serverThread.join();
            } catch (InterruptedException interruptedException) {
                LOGGER.error("Error while shutting down", (Throwable)interruptedException);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void func_240802_v_() {
        try {
            if (this.init()) {
                this.serverTime = Util.milliTime();
                this.statusResponse.setServerDescription(new StringTextComponent(this.motd));
                this.statusResponse.setVersion(new ServerStatusResponse.Version(SharedConstants.getVersion().getName(), SharedConstants.getVersion().getProtocolVersion()));
                this.applyServerIconToResponse(this.statusResponse);
                while (this.serverRunning) {
                    long l = Util.milliTime() - this.serverTime;
                    if (l > 2000L && this.serverTime - this.timeOfLastWarning >= 15000L) {
                        long l2 = l / 50L;
                        LOGGER.warn("Can't keep up! Is the server overloaded? Running {}ms or {} ticks behind", (Object)l, (Object)l2);
                        this.serverTime += l2 * 50L;
                        this.timeOfLastWarning = this.serverTime;
                    }
                    this.serverTime += 50L;
                    LongTickDetector longTickDetector = LongTickDetector.func_233524_a_("Server");
                    this.func_240773_a_(longTickDetector);
                    this.profiler.startTick();
                    this.profiler.startSection("tick");
                    this.tick(this::isAheadOfTime);
                    this.profiler.endStartSection("nextTickWait");
                    this.isRunningScheduledTasks = true;
                    this.runTasksUntil = Math.max(Util.milliTime() + 50L, this.serverTime);
                    this.runScheduledTasks();
                    this.profiler.endSection();
                    this.profiler.endTick();
                    this.func_240795_b_(longTickDetector);
                    this.serverIsRunning = true;
                }
            } else {
                this.finalTick(null);
            }
        } catch (Throwable throwable) {
            LOGGER.error("Encountered an unexpected exception", throwable);
            CrashReport crashReport = throwable instanceof ReportedException ? this.addServerInfoToCrashReport(((ReportedException)throwable).getCrashReport()) : this.addServerInfoToCrashReport(new CrashReport("Exception in server tick loop", throwable));
            File file = new File(new File(this.getDataDirectory(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt");
            if (crashReport.saveToFile(file)) {
                LOGGER.error("This crash report has been saved to: {}", (Object)file.getAbsolutePath());
            } else {
                LOGGER.error("We were unable to save this crash report to disk.");
            }
            this.finalTick(crashReport);
        } finally {
            try {
                this.serverStopped = true;
                this.stopServer();
            } catch (Throwable throwable) {
                LOGGER.error("Exception stopping the server", throwable);
            } finally {
                this.systemExitNow();
            }
        }
    }

    private boolean isAheadOfTime() {
        return this.isTaskRunning() || Util.milliTime() < (this.isRunningScheduledTasks ? this.runTasksUntil : this.serverTime);
    }

    protected void runScheduledTasks() {
        this.drainTasks();
        this.driveUntil(this::lambda$runScheduledTasks$3);
    }

    @Override
    protected TickDelayedTask wrapTask(Runnable runnable) {
        return new TickDelayedTask(this.tickCounter, runnable);
    }

    @Override
    protected boolean canRun(TickDelayedTask tickDelayedTask) {
        return tickDelayedTask.getScheduledTime() + 3 < this.tickCounter || this.isAheadOfTime();
    }

    @Override
    public boolean driveOne() {
        boolean bl;
        this.isRunningScheduledTasks = bl = this.driveOneInternal();
        return bl;
    }

    private boolean driveOneInternal() {
        if (super.driveOne()) {
            return false;
        }
        if (this.isAheadOfTime()) {
            for (ServerWorld serverWorld : this.getWorlds()) {
                if (!serverWorld.getChunkProvider().driveOneTask()) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    protected void run(TickDelayedTask tickDelayedTask) {
        this.getProfiler().func_230035_c_("runTask");
        super.run(tickDelayedTask);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void applyServerIconToResponse(ServerStatusResponse serverStatusResponse) {
        File file = this.getFile("server-icon.png");
        if (!file.exists()) {
            file = this.anvilConverterForAnvilFile.getIconFile();
        }
        if (file.isFile()) {
            ByteBuf byteBuf = Unpooled.buffer();
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                Validate.validState(bufferedImage.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
                Validate.validState(bufferedImage.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
                ImageIO.write((RenderedImage)bufferedImage, "PNG", new ByteBufOutputStream(byteBuf));
                ByteBuffer byteBuffer = Base64.getEncoder().encode(byteBuf.nioBuffer());
                serverStatusResponse.setFavicon("data:image/png;base64," + StandardCharsets.UTF_8.decode(byteBuffer));
            } catch (Exception exception) {
                LOGGER.error("Couldn't load server icon", (Throwable)exception);
            } finally {
                byteBuf.release();
            }
        }
    }

    public boolean isWorldIconSet() {
        this.worldIconSet = this.worldIconSet || this.getWorldIconFile().isFile();
        return this.worldIconSet;
    }

    public File getWorldIconFile() {
        return this.anvilConverterForAnvilFile.getIconFile();
    }

    public File getDataDirectory() {
        return new File(".");
    }

    protected void finalTick(CrashReport crashReport) {
    }

    protected void systemExitNow() {
    }

    protected void tick(BooleanSupplier booleanSupplier) {
        long l = Util.nanoTime();
        ++this.tickCounter;
        this.updateTimeLightAndEntities(booleanSupplier);
        if (l - this.nanoTimeSinceStatusRefresh >= 5000000000L) {
            this.nanoTimeSinceStatusRefresh = l;
            this.statusResponse.setPlayers(new ServerStatusResponse.Players(this.getMaxPlayers(), this.getCurrentPlayerCount()));
            GameProfile[] gameProfileArray = new GameProfile[Math.min(this.getCurrentPlayerCount(), 12)];
            int n = MathHelper.nextInt(this.random, 0, this.getCurrentPlayerCount() - gameProfileArray.length);
            for (int i = 0; i < gameProfileArray.length; ++i) {
                gameProfileArray[i] = this.playerList.getPlayers().get(n + i).getGameProfile();
            }
            Collections.shuffle(Arrays.asList(gameProfileArray));
            this.statusResponse.getPlayers().setPlayers(gameProfileArray);
        }
        if (this.tickCounter % 6000 == 0) {
            LOGGER.debug("Autosave started");
            this.profiler.startSection("save");
            this.playerList.saveAllPlayerData();
            this.save(true, false, true);
            this.profiler.endSection();
            LOGGER.debug("Autosave finished");
        }
        this.profiler.startSection("snooper");
        if (!this.snooper.isSnooperRunning() && this.tickCounter > 100) {
            this.snooper.start();
        }
        if (this.tickCounter % 6000 == 0) {
            this.snooper.addMemoryStatsToSnooper();
        }
        this.profiler.endSection();
        this.profiler.startSection("tallying");
        long l2 = Util.nanoTime() - l;
        this.tickTimeArray[this.tickCounter % 100] = l2;
        long l3 = l2;
        this.tickTime = this.tickTime * 0.8f + (float)l3 / 1000000.0f * 0.19999999f;
        long l4 = Util.nanoTime();
        this.frameTimer.addFrame(l4 - l);
        this.profiler.endSection();
    }

    protected void updateTimeLightAndEntities(BooleanSupplier booleanSupplier) {
        this.profiler.startSection("commandFunctions");
        this.getFunctionManager().tick();
        this.profiler.endStartSection("levels");
        for (ServerWorld serverWorld : this.getWorlds()) {
            this.profiler.startSection(() -> MinecraftServer.lambda$updateTimeLightAndEntities$4(serverWorld));
            if (this.tickCounter % 20 == 0) {
                this.profiler.startSection("timeSync");
                this.playerList.func_232642_a_(new SUpdateTimePacket(serverWorld.getGameTime(), serverWorld.getDayTime(), serverWorld.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)), serverWorld.getDimensionKey());
                this.profiler.endSection();
            }
            this.profiler.startSection("tick");
            try {
                serverWorld.tick(booleanSupplier);
            } catch (Throwable throwable) {
                CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Exception ticking world");
                serverWorld.fillCrashReport(crashReport);
                throw new ReportedException(crashReport);
            }
            this.profiler.endSection();
            this.profiler.endSection();
        }
        this.profiler.endStartSection("connection");
        this.getNetworkSystem().tick();
        this.profiler.endStartSection("players");
        this.playerList.tick();
        if (SharedConstants.developmentMode) {
            TestCollection.field_229570_a_.func_229574_b_();
        }
        this.profiler.endStartSection("server gui refresh");
        for (int i = 0; i < this.tickables.size(); ++i) {
            this.tickables.get(i).run();
        }
        this.profiler.endSection();
    }

    public boolean getAllowNether() {
        return false;
    }

    public void registerTickable(Runnable runnable) {
        this.tickables.add(runnable);
    }

    protected void setServerId(String string) {
        this.serverId = string;
    }

    public boolean isThreadAlive() {
        return !this.serverThread.isAlive();
    }

    public File getFile(String string) {
        return new File(this.getDataDirectory(), string);
    }

    public final ServerWorld func_241755_D_() {
        return this.worlds.get(World.OVERWORLD);
    }

    @Nullable
    public ServerWorld getWorld(RegistryKey<World> registryKey) {
        return this.worlds.get(registryKey);
    }

    public Set<RegistryKey<World>> func_240770_D_() {
        return this.worlds.keySet();
    }

    public Iterable<ServerWorld> getWorlds() {
        return this.worlds.values();
    }

    public String getMinecraftVersion() {
        return SharedConstants.getVersion().getName();
    }

    public int getCurrentPlayerCount() {
        return this.playerList.getCurrentPlayerCount();
    }

    public int getMaxPlayers() {
        return this.playerList.getMaxPlayers();
    }

    public String[] getOnlinePlayerNames() {
        return this.playerList.getOnlinePlayerNames();
    }

    public String getServerModName() {
        return "vanilla";
    }

    public CrashReport addServerInfoToCrashReport(CrashReport crashReport) {
        if (this.playerList != null) {
            crashReport.getCategory().addDetail("Player Count", this::lambda$addServerInfoToCrashReport$5);
        }
        crashReport.getCategory().addDetail("Data Packs", this::lambda$addServerInfoToCrashReport$6);
        if (this.serverId != null) {
            crashReport.getCategory().addDetail("Server Id", this::lambda$addServerInfoToCrashReport$7);
        }
        return crashReport;
    }

    public abstract Optional<String> func_230045_q_();

    @Override
    public void sendMessage(ITextComponent iTextComponent, UUID uUID) {
        LOGGER.info(iTextComponent.getString());
    }

    public KeyPair getKeyPair() {
        return this.serverKeyPair;
    }

    public int getServerPort() {
        return this.serverPort;
    }

    public void setServerPort(int n) {
        this.serverPort = n;
    }

    public String getServerOwner() {
        return this.serverOwner;
    }

    public void setServerOwner(String string) {
        this.serverOwner = string;
    }

    public boolean isSinglePlayer() {
        return this.serverOwner != null;
    }

    protected void func_244801_P() {
        LOGGER.info("Generating keypair");
        try {
            this.serverKeyPair = CryptManager.generateKeyPair();
        } catch (CryptException cryptException) {
            throw new IllegalStateException("Failed to generate key pair", cryptException);
        }
    }

    public void setDifficultyForAllWorlds(Difficulty difficulty, boolean bl) {
        if (bl || !this.field_240768_i_.isDifficultyLocked()) {
            this.field_240768_i_.setDifficulty(this.field_240768_i_.isHardcore() ? Difficulty.HARD : difficulty);
            this.func_240794_aZ_();
            this.getPlayerList().getPlayers().forEach(this::sendDifficultyToPlayer);
        }
    }

    public int func_230512_b_(int n) {
        return n;
    }

    private void func_240794_aZ_() {
        for (ServerWorld serverWorld : this.getWorlds()) {
            serverWorld.setAllowedSpawnTypes(this.func_230536_N_(), this.func_230537_U_());
        }
    }

    public void setDifficultyLocked(boolean bl) {
        this.field_240768_i_.setDifficultyLocked(bl);
        this.getPlayerList().getPlayers().forEach(this::sendDifficultyToPlayer);
    }

    private void sendDifficultyToPlayer(ServerPlayerEntity serverPlayerEntity) {
        IWorldInfo iWorldInfo = serverPlayerEntity.getServerWorld().getWorldInfo();
        serverPlayerEntity.connection.sendPacket(new SServerDifficultyPacket(iWorldInfo.getDifficulty(), iWorldInfo.isDifficultyLocked()));
    }

    protected boolean func_230536_N_() {
        return this.field_240768_i_.getDifficulty() != Difficulty.PEACEFUL;
    }

    public boolean isDemo() {
        return this.isDemo;
    }

    public void setDemo(boolean bl) {
        this.isDemo = bl;
    }

    public String getResourcePackUrl() {
        return this.resourcePackUrl;
    }

    public String getResourcePackHash() {
        return this.resourcePackHash;
    }

    public void setResourcePack(String string, String string2) {
        this.resourcePackUrl = string;
        this.resourcePackHash = string2;
    }

    @Override
    public void fillSnooper(Snooper snooper) {
        snooper.addClientStat("whitelist_enabled", false);
        snooper.addClientStat("whitelist_count", 0);
        if (this.playerList != null) {
            snooper.addClientStat("players_current", this.getCurrentPlayerCount());
            snooper.addClientStat("players_max", this.getMaxPlayers());
            snooper.addClientStat("players_seen", this.playerDataManager.getSeenPlayerUUIDs().length);
        }
        snooper.addClientStat("uses_auth", this.onlineMode);
        snooper.addClientStat("gui_state", this.getGuiEnabled() ? "enabled" : "disabled");
        snooper.addClientStat("run_time", (Util.milliTime() - snooper.getMinecraftStartTimeMillis()) / 60L * 1000L);
        snooper.addClientStat("avg_tick_ms", (int)(MathHelper.average(this.tickTimeArray) * 1.0E-6));
        int n = 0;
        for (ServerWorld serverWorld : this.getWorlds()) {
            if (serverWorld == null) continue;
            snooper.addClientStat("world[" + n + "][dimension]", serverWorld.getDimensionKey().getLocation());
            snooper.addClientStat("world[" + n + "][mode]", (Object)this.field_240768_i_.getGameType());
            snooper.addClientStat("world[" + n + "][difficulty]", (Object)serverWorld.getDifficulty());
            snooper.addClientStat("world[" + n + "][hardcore]", this.field_240768_i_.isHardcore());
            snooper.addClientStat("world[" + n + "][height]", this.buildLimit);
            snooper.addClientStat("world[" + n + "][chunks_loaded]", serverWorld.getChunkProvider().getLoadedChunkCount());
            ++n;
        }
        snooper.addClientStat("worlds", n);
    }

    public abstract boolean isDedicatedServer();

    public abstract int func_241871_k();

    public boolean isServerInOnlineMode() {
        return this.onlineMode;
    }

    public void setOnlineMode(boolean bl) {
        this.onlineMode = bl;
    }

    public boolean getPreventProxyConnections() {
        return this.preventProxyConnections;
    }

    public void setPreventProxyConnections(boolean bl) {
        this.preventProxyConnections = bl;
    }

    public boolean func_230537_U_() {
        return false;
    }

    public boolean func_230538_V_() {
        return false;
    }

    public abstract boolean shouldUseNativeTransport();

    public boolean isPVPEnabled() {
        return this.pvpEnabled;
    }

    public void setAllowPvp(boolean bl) {
        this.pvpEnabled = bl;
    }

    public boolean isFlightAllowed() {
        return this.allowFlight;
    }

    public void setAllowFlight(boolean bl) {
        this.allowFlight = bl;
    }

    public abstract boolean isCommandBlockEnabled();

    public String getMOTD() {
        return this.motd;
    }

    public void setMOTD(String string) {
        this.motd = string;
    }

    public int getBuildLimit() {
        return this.buildLimit;
    }

    public void setBuildLimit(int n) {
        this.buildLimit = n;
    }

    public boolean isServerStopped() {
        return this.serverStopped;
    }

    public PlayerList getPlayerList() {
        return this.playerList;
    }

    public void setPlayerList(PlayerList playerList) {
        this.playerList = playerList;
    }

    public abstract boolean getPublic();

    public void setGameType(GameType gameType) {
        this.field_240768_i_.setGameType(gameType);
    }

    @Nullable
    public NetworkSystem getNetworkSystem() {
        return this.networkSystem;
    }

    public boolean serverIsInRunLoop() {
        return this.serverIsRunning;
    }

    public boolean getGuiEnabled() {
        return true;
    }

    public abstract boolean shareToLAN(GameType var1, boolean var2, int var3);

    public int getTickCounter() {
        return this.tickCounter;
    }

    public Snooper getSnooper() {
        return this.snooper;
    }

    public int getSpawnProtectionSize() {
        return 1;
    }

    public boolean isBlockProtected(ServerWorld serverWorld, BlockPos blockPos, PlayerEntity playerEntity) {
        return true;
    }

    public void setForceGamemode(boolean bl) {
        this.isGamemodeForced = bl;
    }

    public boolean getForceGamemode() {
        return this.isGamemodeForced;
    }

    public boolean func_230541_aj_() {
        return false;
    }

    public int getMaxPlayerIdleMinutes() {
        return this.maxPlayerIdleMinutes;
    }

    public void setPlayerIdleTimeout(int n) {
        this.maxPlayerIdleMinutes = n;
    }

    public MinecraftSessionService getMinecraftSessionService() {
        return this.sessionService;
    }

    public GameProfileRepository getGameProfileRepository() {
        return this.profileRepo;
    }

    public PlayerProfileCache getPlayerProfileCache() {
        return this.profileCache;
    }

    public ServerStatusResponse getServerStatusResponse() {
        return this.statusResponse;
    }

    public void refreshStatusNextTick() {
        this.nanoTimeSinceStatusRefresh = 0L;
    }

    public int getMaxWorldSize() {
        return 1;
    }

    @Override
    public boolean shouldDeferTasks() {
        return super.shouldDeferTasks() && !this.isServerStopped();
    }

    @Override
    public Thread getExecutionThread() {
        return this.serverThread;
    }

    public int getNetworkCompressionThreshold() {
        return 1;
    }

    public long getServerTime() {
        return this.serverTime;
    }

    public DataFixer getDataFixer() {
        return this.dataFixer;
    }

    public int getSpawnRadius(@Nullable ServerWorld serverWorld) {
        return serverWorld != null ? serverWorld.getGameRules().getInt(GameRules.SPAWN_RADIUS) : 10;
    }

    public AdvancementManager getAdvancementManager() {
        return this.resourceManager.getAdvancementManager();
    }

    public FunctionManager getFunctionManager() {
        return this.functionManager;
    }

    public CompletableFuture<Void> func_240780_a_(Collection<String> collection) {
        CompletionStage completionStage = ((CompletableFuture)CompletableFuture.supplyAsync(() -> this.lambda$func_240780_a_$8(collection), this).thenCompose(this::lambda$func_240780_a_$9)).thenAcceptAsync(arg_0 -> this.lambda$func_240780_a_$10(collection, arg_0), (Executor)this);
        if (this.isOnExecutionThread()) {
            this.driveUntil(((CompletableFuture)completionStage)::isDone);
        }
        return completionStage;
    }

    public static DatapackCodec func_240772_a_(ResourcePackList resourcePackList, DatapackCodec datapackCodec, boolean bl) {
        resourcePackList.reloadPacksFromFinders();
        if (bl) {
            resourcePackList.setEnabledPacks(Collections.singleton("vanilla"));
            return new DatapackCodec(ImmutableList.of("vanilla"), ImmutableList.of());
        }
        LinkedHashSet<String> linkedHashSet = Sets.newLinkedHashSet();
        for (String object : datapackCodec.getEnabled()) {
            if (resourcePackList.func_232617_b_(object)) {
                linkedHashSet.add(object);
                continue;
            }
            LOGGER.warn("Missing data pack {}", (Object)object);
        }
        for (ResourcePackInfo resourcePackInfo : resourcePackList.getAllPacks()) {
            String string = resourcePackInfo.getName();
            if (datapackCodec.getDisabled().contains(string) || linkedHashSet.contains(string)) continue;
            LOGGER.info("Found new data pack {}, loading it automatically", (Object)string);
            linkedHashSet.add(string);
        }
        if (linkedHashSet.isEmpty()) {
            LOGGER.info("No datapacks selected, forcing vanilla");
            linkedHashSet.add("vanilla");
        }
        resourcePackList.setEnabledPacks(linkedHashSet);
        return MinecraftServer.func_240771_a_(resourcePackList);
    }

    private static DatapackCodec func_240771_a_(ResourcePackList resourcePackList) {
        Collection<String> collection = resourcePackList.func_232621_d_();
        ImmutableList<String> immutableList = ImmutableList.copyOf(collection);
        List list = resourcePackList.func_232616_b_().stream().filter(arg_0 -> MinecraftServer.lambda$func_240771_a_$11(collection, arg_0)).collect(ImmutableList.toImmutableList());
        return new DatapackCodec(immutableList, list);
    }

    public void kickPlayersNotWhitelisted(CommandSource commandSource) {
        if (this.isWhitelistEnabled()) {
            PlayerList playerList = commandSource.getServer().getPlayerList();
            WhiteList whiteList = playerList.getWhitelistedPlayers();
            for (ServerPlayerEntity serverPlayerEntity : Lists.newArrayList(playerList.getPlayers())) {
                if (whiteList.isWhitelisted(serverPlayerEntity.getGameProfile())) continue;
                serverPlayerEntity.connection.disconnect(new TranslationTextComponent("multiplayer.disconnect.not_whitelisted"));
            }
        }
    }

    public ResourcePackList getResourcePacks() {
        return this.resourcePacks;
    }

    public Commands getCommandManager() {
        return this.resourceManager.getCommandManager();
    }

    public CommandSource getCommandSource() {
        ServerWorld serverWorld = this.func_241755_D_();
        return new CommandSource(this, serverWorld == null ? Vector3d.ZERO : Vector3d.copy(serverWorld.getSpawnPoint()), Vector2f.ZERO, serverWorld, 4, "Server", new StringTextComponent("Server"), this, null);
    }

    @Override
    public boolean shouldReceiveFeedback() {
        return false;
    }

    @Override
    public boolean shouldReceiveErrors() {
        return false;
    }

    public RecipeManager getRecipeManager() {
        return this.resourceManager.getRecipeManager();
    }

    public ITagCollectionSupplier func_244266_aF() {
        return this.resourceManager.func_244358_d();
    }

    public ServerScoreboard getScoreboard() {
        return this.scoreboard;
    }

    public CommandStorage func_229735_aN_() {
        if (this.field_229733_al_ == null) {
            throw new NullPointerException("Called before server init");
        }
        return this.field_229733_al_;
    }

    public LootTableManager getLootTableManager() {
        return this.resourceManager.getLootTableManager();
    }

    public LootPredicateManager func_229736_aP_() {
        return this.resourceManager.getLootPredicateManager();
    }

    public GameRules getGameRules() {
        return this.func_241755_D_().getGameRules();
    }

    public CustomServerBossInfoManager getCustomBossEvents() {
        return this.customBossEvents;
    }

    public boolean isWhitelistEnabled() {
        return this.whitelistEnabled;
    }

    public void setWhitelistEnabled(boolean bl) {
        this.whitelistEnabled = bl;
    }

    public float getTickTime() {
        return this.tickTime;
    }

    public int getPermissionLevel(GameProfile gameProfile) {
        if (this.getPlayerList().canSendCommands(gameProfile)) {
            OpEntry opEntry = (OpEntry)this.getPlayerList().getOppedPlayers().getEntry(gameProfile);
            if (opEntry != null) {
                return opEntry.getPermissionLevel();
            }
            if (this.isServerOwner(gameProfile)) {
                return 1;
            }
            if (this.isSinglePlayer()) {
                return this.getPlayerList().commandsAllowedForAll() ? 4 : 0;
            }
            return this.getOpPermissionLevel();
        }
        return 1;
    }

    public FrameTimer getFrameTimer() {
        return this.frameTimer;
    }

    public IProfiler getProfiler() {
        return this.profiler;
    }

    public abstract boolean isServerOwner(GameProfile var1);

    public void dumpDebugInfo(Path path) throws IOException {
        Path path2 = path.resolve("levels");
        for (Map.Entry<RegistryKey<World>, ServerWorld> entry : this.worlds.entrySet()) {
            ResourceLocation resourceLocation = entry.getKey().getLocation();
            Path path3 = path2.resolve(resourceLocation.getNamespace()).resolve(resourceLocation.getPath());
            Files.createDirectories(path3, new FileAttribute[0]);
            entry.getValue().writeDebugInfo(path3);
        }
        this.dumpGameRules(path.resolve("gamerules.txt"));
        this.dumpClasspath(path.resolve("classpath.txt"));
        this.dumpDummyCrashReport(path.resolve("example_crash.txt"));
        this.dumpStats(path.resolve("stats.txt"));
        this.dumpThreads(path.resolve("threads.txt"));
    }

    private void dumpStats(Path path) throws IOException {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, new OpenOption[0]);){
            bufferedWriter.write(String.format("pending_tasks: %d\n", this.getQueueSize()));
            bufferedWriter.write(String.format("average_tick_time: %f\n", Float.valueOf(this.getTickTime())));
            bufferedWriter.write(String.format("tick_times: %s\n", Arrays.toString(this.tickTimeArray)));
            bufferedWriter.write(String.format("queue: %s\n", Util.getServerExecutor()));
        }
    }

    private void dumpDummyCrashReport(Path path) throws IOException {
        CrashReport crashReport = new CrashReport("Server dump", new Exception("dummy"));
        this.addServerInfoToCrashReport(crashReport);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, new OpenOption[0]);){
            bufferedWriter.write(crashReport.getCompleteReport());
        }
    }

    private void dumpGameRules(Path path) throws IOException {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, new OpenOption[0]);){
            ArrayList<String> arrayList = Lists.newArrayList();
            GameRules gameRules = this.getGameRules();
            GameRules.visitAll(new GameRules.IRuleEntryVisitor(){
                final List val$list;
                final GameRules val$gamerules;
                final MinecraftServer this$0;
                {
                    this.this$0 = minecraftServer;
                    this.val$list = list;
                    this.val$gamerules = gameRules;
                }

                @Override
                public <T extends GameRules.RuleValue<T>> void visit(GameRules.RuleKey<T> ruleKey, GameRules.RuleType<T> ruleType) {
                    this.val$list.add(String.format("%s=%s\n", ruleKey.getName(), ((GameRules.RuleValue)this.val$gamerules.get(ruleKey)).toString()));
                }
            });
            for (String string : arrayList) {
                bufferedWriter.write(string);
            }
        }
    }

    private void dumpClasspath(Path path) throws IOException {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, new OpenOption[0]);){
            String string = System.getProperty("java.class.path");
            String string2 = System.getProperty("path.separator");
            for (String string3 : Splitter.on(string2).split(string)) {
                bufferedWriter.write(string3);
                bufferedWriter.write("\n");
            }
        }
    }

    private void dumpThreads(Path path) throws IOException {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfoArray = threadMXBean.dumpAllThreads(true, true);
        Arrays.sort(threadInfoArray, Comparator.comparing(ThreadInfo::getThreadName));
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, new OpenOption[0]);){
            for (ThreadInfo threadInfo : threadInfoArray) {
                bufferedWriter.write(threadInfo.toString());
                ((Writer)bufferedWriter).write(10);
            }
        }
    }

    private void func_240773_a_(@Nullable LongTickDetector longTickDetector) {
        if (this.startProfiling) {
            this.startProfiling = false;
            this.timeTracker.func_233507_c_();
        }
        this.profiler = LongTickDetector.func_233523_a_(this.timeTracker.func_233508_d_(), longTickDetector);
    }

    private void func_240795_b_(@Nullable LongTickDetector longTickDetector) {
        if (longTickDetector != null) {
            longTickDetector.func_233525_b_();
        }
        this.profiler = this.timeTracker.func_233508_d_();
    }

    public boolean func_240789_aP_() {
        return this.timeTracker.func_233505_a_();
    }

    public void func_240790_aQ_() {
        this.startProfiling = true;
    }

    public IProfileResult func_240791_aR_() {
        IProfileResult iProfileResult = this.timeTracker.func_233509_e_();
        this.timeTracker.func_233506_b_();
        return iProfileResult;
    }

    public Path func_240776_a_(FolderName folderName) {
        return this.anvilConverterForAnvilFile.resolveFilePath(folderName);
    }

    public boolean func_230540_aS_() {
        return false;
    }

    public TemplateManager func_240792_aT_() {
        return this.field_240765_ak_;
    }

    public IServerConfiguration func_240793_aU_() {
        return this.field_240768_i_;
    }

    public DynamicRegistries func_244267_aX() {
        return this.field_240767_f_;
    }

    @Nullable
    public IChatFilter func_244435_a(ServerPlayerEntity serverPlayerEntity) {
        return null;
    }

    @Override
    protected void run(Runnable runnable) {
        this.run((TickDelayedTask)runnable);
    }

    @Override
    protected boolean canRun(Runnable runnable) {
        return this.canRun((TickDelayedTask)runnable);
    }

    @Override
    protected Runnable wrapTask(Runnable runnable) {
        return this.wrapTask(runnable);
    }

    private static boolean lambda$func_240771_a_$11(Collection collection, String string) {
        return !collection.contains(string);
    }

    private void lambda$func_240780_a_$10(Collection collection, DataPackRegistries dataPackRegistries) {
        this.resourceManager.close();
        this.resourceManager = dataPackRegistries;
        this.resourcePacks.setEnabledPacks(collection);
        this.field_240768_i_.setDatapackCodec(MinecraftServer.func_240771_a_(this.resourcePacks));
        dataPackRegistries.updateTags();
        this.getPlayerList().saveAllPlayerData();
        this.getPlayerList().reloadResources();
        this.functionManager.setFunctionReloader(this.resourceManager.getFunctionReloader());
        this.field_240765_ak_.onResourceManagerReload(this.resourceManager.getResourceManager());
    }

    private CompletionStage lambda$func_240780_a_$9(ImmutableList immutableList) {
        return DataPackRegistries.func_240961_a_(immutableList, this.isDedicatedServer() ? Commands.EnvironmentType.DEDICATED : Commands.EnvironmentType.INTEGRATED, this.getFunctionLevel(), this.backgroundExecutor, this);
    }

    private ImmutableList lambda$func_240780_a_$8(Collection collection) {
        return collection.stream().map(this.resourcePacks::getPackInfo).filter(Objects::nonNull).map(ResourcePackInfo::getResourcePack).collect(ImmutableList.toImmutableList());
    }

    private String lambda$addServerInfoToCrashReport$7() throws Exception {
        return this.serverId;
    }

    private String lambda$addServerInfoToCrashReport$6() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        for (ResourcePackInfo resourcePackInfo : this.resourcePacks.getEnabledPacks()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(resourcePackInfo.getName());
            if (resourcePackInfo.getCompatibility().isCompatible()) continue;
            stringBuilder.append(" (incompatible)");
        }
        return stringBuilder.toString();
    }

    private String lambda$addServerInfoToCrashReport$5() throws Exception {
        return this.playerList.getCurrentPlayerCount() + " / " + this.playerList.getMaxPlayers() + "; " + this.playerList.getPlayers();
    }

    private static String lambda$updateTimeLightAndEntities$4(ServerWorld serverWorld) {
        return serverWorld + " " + serverWorld.getDimensionKey().getLocation();
    }

    private boolean lambda$runScheduledTasks$3() {
        return !this.isAheadOfTime();
    }

    private static boolean lambda$func_240786_a_$2(Biome biome) {
        return biome.getMobSpawnInfo().isValidSpawnBiomeForPlayer();
    }

    private static void lambda$func_240784_a_$1(Thread thread2, Throwable throwable) {
        LOGGER.error(throwable);
    }

    private static void lambda$func_240784_a_$0(AtomicReference atomicReference) {
        ((MinecraftServer)atomicReference.get()).func_240802_v_();
    }
}

