/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.integrated;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.LanServerPingThread;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.profiler.IProfiler;
import net.minecraft.profiler.Snooper;
import net.minecraft.resources.DataPackRegistries;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedPlayerList;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.listener.IChunkStatusListenerFactory;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.IServerConfiguration;
import net.minecraft.world.storage.SaveFormat;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntegratedServer
extends MinecraftServer {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Minecraft mc;
    private boolean isGamePaused;
    private int serverPort = -1;
    private LanServerPingThread lanServerPing;
    private UUID playerUuid;
    private long ticksSaveLast = 0L;
    public World difficultyUpdateWorld = null;
    public BlockPos difficultyUpdatePos = null;
    public DifficultyInstance difficultyLast = null;

    public IntegratedServer(Thread thread2, Minecraft minecraft, DynamicRegistries.Impl impl, SaveFormat.LevelSave levelSave, ResourcePackList resourcePackList, DataPackRegistries dataPackRegistries, IServerConfiguration iServerConfiguration, MinecraftSessionService minecraftSessionService, GameProfileRepository gameProfileRepository, PlayerProfileCache playerProfileCache, IChunkStatusListenerFactory iChunkStatusListenerFactory) {
        super(thread2, impl, levelSave, iServerConfiguration, resourcePackList, minecraft.getProxy(), minecraft.getDataFixer(), dataPackRegistries, minecraftSessionService, gameProfileRepository, playerProfileCache, iChunkStatusListenerFactory);
        this.setServerOwner(minecraft.getSession().getUsername());
        this.setDemo(minecraft.isDemo());
        this.setBuildLimit(256);
        this.setPlayerList(new IntegratedPlayerList(this, this.field_240767_f_, this.playerDataManager));
        this.mc = minecraft;
    }

    @Override
    public boolean init() {
        LOGGER.info("Starting integrated minecraft server version " + SharedConstants.getVersion().getName());
        this.setOnlineMode(false);
        this.setAllowPvp(false);
        this.setAllowFlight(false);
        this.func_244801_P();
        if (Reflector.ServerLifecycleHooks_handleServerAboutToStart.exists() && !Reflector.callBoolean(Reflector.ServerLifecycleHooks_handleServerAboutToStart, this)) {
            return true;
        }
        this.func_240800_l__();
        this.setMOTD(this.getServerOwner() + " - " + this.func_240793_aU_().getWorldName());
        return Reflector.ServerLifecycleHooks_handleServerStarting.exists() ? Reflector.callBoolean(Reflector.ServerLifecycleHooks_handleServerStarting, this) : true;
    }

    @Override
    public void tick(BooleanSupplier booleanSupplier) {
        this.onTick();
        boolean bl = this.isGamePaused;
        this.isGamePaused = Minecraft.getInstance().getConnection() != null && Minecraft.getInstance().isGamePaused();
        IProfiler iProfiler = this.getProfiler();
        if (!bl && this.isGamePaused) {
            iProfiler.startSection("autoSave");
            LOGGER.info("Saving and pausing game...");
            this.getPlayerList().saveAllPlayerData();
            this.save(false, false, true);
            iProfiler.endSection();
        }
        if (!this.isGamePaused) {
            super.tick(booleanSupplier);
            int n = Math.max(2, this.mc.gameSettings.renderDistanceChunks + -1);
            if (n != this.getPlayerList().getViewDistance()) {
                LOGGER.info("Changing view distance to {}, from {}", (Object)n, (Object)this.getPlayerList().getViewDistance());
                this.getPlayerList().setViewDistance(n);
            }
        }
    }

    @Override
    public boolean allowLoggingRcon() {
        return false;
    }

    @Override
    public boolean allowLogging() {
        return false;
    }

    @Override
    public File getDataDirectory() {
        return this.mc.gameDir;
    }

    @Override
    public boolean isDedicatedServer() {
        return true;
    }

    @Override
    public int func_241871_k() {
        return 1;
    }

    @Override
    public boolean shouldUseNativeTransport() {
        return true;
    }

    @Override
    public void finalTick(CrashReport crashReport) {
        this.mc.crashed(crashReport);
    }

    @Override
    public CrashReport addServerInfoToCrashReport(CrashReport crashReport) {
        crashReport = super.addServerInfoToCrashReport(crashReport);
        crashReport.getCategory().addDetail("Type", "Integrated Server (map_client.txt)");
        crashReport.getCategory().addDetail("Is Modded", this::lambda$addServerInfoToCrashReport$0);
        return crashReport;
    }

    @Override
    public Optional<String> func_230045_q_() {
        String string = ClientBrandRetriever.getClientModName();
        if (!string.equals("vanilla")) {
            return Optional.of("Definitely; Client brand changed to '" + string + "'");
        }
        string = this.getServerModName();
        if (!"vanilla".equals(string)) {
            return Optional.of("Definitely; Server brand changed to '" + string + "'");
        }
        return Minecraft.class.getSigners() == null ? Optional.of("Very likely; Jar signature invalidated") : Optional.empty();
    }

    @Override
    public void fillSnooper(Snooper snooper) {
        super.fillSnooper(snooper);
        snooper.addClientStat("snooper_partner", this.mc.getSnooper().getUniqueID());
    }

    @Override
    public boolean shareToLAN(GameType gameType, boolean bl, int n) {
        try {
            this.getNetworkSystem().addEndpoint(null, n);
            LOGGER.info("Started serving on {}", (Object)n);
            this.serverPort = n;
            this.lanServerPing = new LanServerPingThread(this.getMOTD(), "" + n);
            this.lanServerPing.start();
            this.getPlayerList().setGameType(gameType);
            this.getPlayerList().setCommandsAllowedForAll(bl);
            int n2 = this.getPermissionLevel(this.mc.player.getGameProfile());
            this.mc.player.setPermissionLevel(n2);
            for (ServerPlayerEntity serverPlayerEntity : this.getPlayerList().getPlayers()) {
                this.getCommandManager().send(serverPlayerEntity);
            }
            return true;
        } catch (IOException iOException) {
            return true;
        }
    }

    @Override
    public void stopServer() {
        super.stopServer();
        if (this.lanServerPing != null) {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }

    @Override
    public void initiateShutdown(boolean bl) {
        if (!Reflector.MinecraftForge.exists() || this.isServerRunning()) {
            this.runImmediately(this::lambda$initiateShutdown$1);
        }
        super.initiateShutdown(bl);
        if (this.lanServerPing != null) {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }

    @Override
    public boolean getPublic() {
        return this.serverPort > -1;
    }

    @Override
    public int getServerPort() {
        return this.serverPort;
    }

    @Override
    public void setGameType(GameType gameType) {
        super.setGameType(gameType);
        this.getPlayerList().setGameType(gameType);
    }

    @Override
    public boolean isCommandBlockEnabled() {
        return false;
    }

    @Override
    public int getOpPermissionLevel() {
        return 1;
    }

    @Override
    public int getFunctionLevel() {
        return 1;
    }

    public void setPlayerUuid(UUID uUID) {
        this.playerUuid = uUID;
    }

    @Override
    public boolean isServerOwner(GameProfile gameProfile) {
        return gameProfile.getName().equalsIgnoreCase(this.getServerOwner());
    }

    @Override
    public int func_230512_b_(int n) {
        return (int)(this.mc.gameSettings.entityDistanceScaling * (float)n);
    }

    @Override
    public boolean func_230540_aS_() {
        return this.mc.gameSettings.syncChunkWrites;
    }

    private void onTick() {
        for (ServerWorld serverWorld : this.getWorlds()) {
            this.onTick(serverWorld);
        }
    }

    private void onTick(ServerWorld serverWorld) {
        if (!Config.isTimeDefault()) {
            this.fixWorldTime(serverWorld);
        }
        if (!Config.isWeatherEnabled()) {
            this.fixWorldWeather(serverWorld);
        }
        if (this.difficultyUpdateWorld == serverWorld && this.difficultyUpdatePos != null) {
            this.difficultyLast = serverWorld.getDifficultyForLocation(this.difficultyUpdatePos);
            this.difficultyUpdateWorld = null;
            this.difficultyUpdatePos = null;
        }
    }

    public DifficultyInstance getDifficultyAsync(World world, BlockPos blockPos) {
        this.difficultyUpdateWorld = world;
        this.difficultyUpdatePos = blockPos;
        return this.difficultyLast;
    }

    private void fixWorldWeather(ServerWorld serverWorld) {
        if (serverWorld.getRainStrength(1.0f) > 0.0f || serverWorld.isThundering()) {
            serverWorld.func_241113_a_(6000, 0, false, true);
        }
    }

    private void fixWorldTime(ServerWorld serverWorld) {
        if (this.getGameType() == GameType.CREATIVE) {
            long l = serverWorld.getDayTime();
            long l2 = l % 24000L;
            if (Config.isTimeDayOnly()) {
                if (l2 <= 1000L) {
                    serverWorld.func_241114_a_(l - l2 + 1001L);
                }
                if (l2 >= 11000L) {
                    serverWorld.func_241114_a_(l - l2 + 24001L);
                }
            }
            if (Config.isTimeNightOnly()) {
                if (l2 <= 14000L) {
                    serverWorld.func_241114_a_(l - l2 + 14001L);
                }
                if (l2 >= 22000L) {
                    serverWorld.func_241114_a_(l - l2 + 24000L + 14001L);
                }
            }
        }
    }

    @Override
    public boolean save(boolean bl, boolean bl2, boolean bl3) {
        if (bl) {
            int n;
            int n2 = this.getTickCounter();
            if ((long)n2 < this.ticksSaveLast + (long)(n = this.mc.gameSettings.ofAutoSaveTicks)) {
                return true;
            }
            this.ticksSaveLast = n2;
        }
        return super.save(bl, bl2, bl3);
    }

    private void lambda$initiateShutdown$1() {
        for (ServerPlayerEntity serverPlayerEntity : Lists.newArrayList(this.getPlayerList().getPlayers())) {
            if (serverPlayerEntity.getUniqueID().equals(this.playerUuid)) continue;
            this.getPlayerList().playerLoggedOut(serverPlayerEntity);
        }
    }

    private String lambda$addServerInfoToCrashReport$0() throws Exception {
        return this.func_230045_q_().orElse("Probably not. Jar signature remains and both client + server brands are untouched.");
    }
}

