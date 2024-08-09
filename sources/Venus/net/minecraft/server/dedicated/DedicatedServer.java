/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.dedicated;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.network.rcon.IServer;
import net.minecraft.network.rcon.MainThread;
import net.minecraft.network.rcon.QueryThread;
import net.minecraft.network.rcon.RConConsoleSource;
import net.minecraft.profiler.Snooper;
import net.minecraft.resources.DataPackRegistries;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerPropertiesProvider;
import net.minecraft.server.dedicated.DedicatedPlayerList;
import net.minecraft.server.dedicated.PendingCommand;
import net.minecraft.server.dedicated.ServerHangWatchdog;
import net.minecraft.server.dedicated.ServerInfoMBean;
import net.minecraft.server.dedicated.ServerProperties;
import net.minecraft.server.gui.MinecraftServerGui;
import net.minecraft.server.management.PlayerList;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import net.minecraft.util.DefaultWithNameUncaughtExceptionHandler;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.text.filter.ChatFilterClient;
import net.minecraft.util.text.filter.IChatFilter;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.listener.IChunkStatusListenerFactory;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.IServerConfiguration;
import net.minecraft.world.storage.SaveFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DedicatedServer
extends MinecraftServer
implements IServer {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Pattern RESOURCE_PACK_SHA1_PATTERN = Pattern.compile("^[a-fA-F0-9]{40}$");
    private final List<PendingCommand> pendingCommandList = Collections.synchronizedList(Lists.newArrayList());
    private QueryThread rconQueryThread;
    private final RConConsoleSource rconConsoleSource;
    private MainThread rconThread;
    private final ServerPropertiesProvider settings;
    @Nullable
    private MinecraftServerGui serverGui;
    @Nullable
    private final ChatFilterClient field_244714_r;

    public DedicatedServer(Thread thread2, DynamicRegistries.Impl impl, SaveFormat.LevelSave levelSave, ResourcePackList resourcePackList, DataPackRegistries dataPackRegistries, IServerConfiguration iServerConfiguration, ServerPropertiesProvider serverPropertiesProvider, DataFixer dataFixer, MinecraftSessionService minecraftSessionService, GameProfileRepository gameProfileRepository, PlayerProfileCache playerProfileCache, IChunkStatusListenerFactory iChunkStatusListenerFactory) {
        super(thread2, impl, levelSave, iServerConfiguration, resourcePackList, Proxy.NO_PROXY, dataFixer, dataPackRegistries, minecraftSessionService, gameProfileRepository, playerProfileCache, iChunkStatusListenerFactory);
        this.settings = serverPropertiesProvider;
        this.rconConsoleSource = new RConConsoleSource(this);
        this.field_244714_r = null;
    }

    @Override
    public boolean init() throws IOException {
        Thread thread2 = new Thread(this, "Server console handler"){
            final DedicatedServer this$0;
            {
                this.this$0 = dedicatedServer;
                super(string);
            }

            @Override
            public void run() {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
                try {
                    String string;
                    while (!this.this$0.isServerStopped() && this.this$0.isServerRunning() && (string = bufferedReader.readLine()) != null) {
                        this.this$0.handleConsoleInput(string, this.this$0.getCommandSource());
                    }
                } catch (IOException iOException) {
                    LOGGER.error("Exception handling console input", (Throwable)iOException);
                }
            }
        };
        thread2.setDaemon(false);
        thread2.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
        thread2.start();
        LOGGER.info("Starting minecraft server version " + SharedConstants.getVersion().getName());
        if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
            LOGGER.warn("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
        }
        LOGGER.info("Loading properties");
        ServerProperties serverProperties = this.settings.getProperties();
        if (this.isSinglePlayer()) {
            this.setHostname("127.0.0.1");
        } else {
            this.setOnlineMode(serverProperties.onlineMode);
            this.setPreventProxyConnections(serverProperties.preventProxyConnections);
            this.setHostname(serverProperties.serverIp);
        }
        this.setAllowPvp(serverProperties.allowPvp);
        this.setAllowFlight(serverProperties.allowFlight);
        this.setResourcePack(serverProperties.resourcePack, this.loadResourcePackSHA());
        this.setMOTD(serverProperties.motd);
        this.setForceGamemode(serverProperties.forceGamemode);
        super.setPlayerIdleTimeout(serverProperties.playerIdleTimeout.get());
        this.setWhitelistEnabled(serverProperties.enforceWhitelist);
        this.field_240768_i_.setGameType(serverProperties.gamemode);
        LOGGER.info("Default game type: {}", (Object)serverProperties.gamemode);
        InetAddress inetAddress = null;
        if (!this.getServerHostname().isEmpty()) {
            inetAddress = InetAddress.getByName(this.getServerHostname());
        }
        if (this.getServerPort() < 0) {
            this.setServerPort(serverProperties.serverPort);
        }
        this.func_244801_P();
        LOGGER.info("Starting Minecraft server on {}:{}", (Object)(this.getServerHostname().isEmpty() ? "*" : this.getServerHostname()), (Object)this.getServerPort());
        try {
            this.getNetworkSystem().addEndpoint(inetAddress, this.getServerPort());
        } catch (IOException iOException) {
            LOGGER.warn("**** FAILED TO BIND TO PORT!");
            LOGGER.warn("The exception was: {}", (Object)iOException.toString());
            LOGGER.warn("Perhaps a server is already running on that port?");
            return true;
        }
        if (!this.isServerInOnlineMode()) {
            LOGGER.warn("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
            LOGGER.warn("The server will make no attempt to authenticate usernames. Beware.");
            LOGGER.warn("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
            LOGGER.warn("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
        }
        if (this.convertFiles()) {
            this.getPlayerProfileCache().save();
        }
        if (!PreYggdrasilConverter.func_219587_e(this)) {
            return true;
        }
        this.setPlayerList(new DedicatedPlayerList(this, this.field_240767_f_, this.playerDataManager));
        long l = Util.nanoTime();
        this.setBuildLimit(serverProperties.maxBuildHeight);
        SkullTileEntity.setProfileCache(this.getPlayerProfileCache());
        SkullTileEntity.setSessionService(this.getMinecraftSessionService());
        PlayerProfileCache.setOnlineMode(this.isServerInOnlineMode());
        LOGGER.info("Preparing level \"{}\"", (Object)this.func_230542_k__());
        this.func_240800_l__();
        long l2 = Util.nanoTime() - l;
        String string = String.format(Locale.ROOT, "%.3fs", (double)l2 / 1.0E9);
        LOGGER.info("Done ({})! For help, type \"help\"", (Object)string);
        if (serverProperties.announceAdvancements != null) {
            this.getGameRules().get(GameRules.ANNOUNCE_ADVANCEMENTS).set(serverProperties.announceAdvancements, this);
        }
        if (serverProperties.enableQuery) {
            LOGGER.info("Starting GS4 status listener");
            this.rconQueryThread = QueryThread.func_242129_a(this);
        }
        if (serverProperties.enableRcon) {
            LOGGER.info("Starting remote control listener");
            this.rconThread = MainThread.func_242130_a(this);
        }
        if (this.getMaxTickTime() > 0L) {
            Thread thread3 = new Thread(new ServerHangWatchdog(this));
            thread3.setUncaughtExceptionHandler(new DefaultWithNameUncaughtExceptionHandler(LOGGER));
            thread3.setName("Server Watchdog");
            thread3.setDaemon(false);
            thread3.start();
        }
        Items.AIR.fillItemGroup(ItemGroup.SEARCH, NonNullList.create());
        if (serverProperties.field_241079_P_) {
            ServerInfoMBean.func_233490_a_(this);
        }
        return false;
    }

    @Override
    public boolean func_230537_U_() {
        return this.getServerProperties().spawnAnimals && super.func_230537_U_();
    }

    @Override
    public boolean func_230536_N_() {
        return this.settings.getProperties().spawnMonsters && super.func_230536_N_();
    }

    @Override
    public boolean func_230538_V_() {
        return this.settings.getProperties().spawnNPCs && super.func_230538_V_();
    }

    public String loadResourcePackSHA() {
        String string;
        ServerProperties serverProperties = this.settings.getProperties();
        if (!serverProperties.resourcePackSha1.isEmpty()) {
            string = serverProperties.resourcePackSha1;
            if (!Strings.isNullOrEmpty(serverProperties.resourcePackHash)) {
                LOGGER.warn("resource-pack-hash is deprecated and found along side resource-pack-sha1. resource-pack-hash will be ignored.");
            }
        } else if (!Strings.isNullOrEmpty(serverProperties.resourcePackHash)) {
            LOGGER.warn("resource-pack-hash is deprecated. Please use resource-pack-sha1 instead.");
            string = serverProperties.resourcePackHash;
        } else {
            string = "";
        }
        if (!string.isEmpty() && !RESOURCE_PACK_SHA1_PATTERN.matcher(string).matches()) {
            LOGGER.warn("Invalid sha1 for ressource-pack-sha1");
        }
        if (!serverProperties.resourcePack.isEmpty() && string.isEmpty()) {
            LOGGER.warn("You specified a resource pack without providing a sha1 hash. Pack will be updated on the client only if you change the name of the pack.");
        }
        return string;
    }

    @Override
    public ServerProperties getServerProperties() {
        return this.settings.getProperties();
    }

    @Override
    public void func_230543_p_() {
        this.setDifficultyForAllWorlds(this.getServerProperties().difficulty, false);
    }

    @Override
    public boolean isHardcore() {
        return this.getServerProperties().hardcore;
    }

    @Override
    public CrashReport addServerInfoToCrashReport(CrashReport crashReport) {
        crashReport = super.addServerInfoToCrashReport(crashReport);
        crashReport.getCategory().addDetail("Is Modded", this::lambda$addServerInfoToCrashReport$0);
        crashReport.getCategory().addDetail("Type", DedicatedServer::lambda$addServerInfoToCrashReport$1);
        return crashReport;
    }

    @Override
    public Optional<String> func_230045_q_() {
        String string = this.getServerModName();
        return !"vanilla".equals(string) ? Optional.of("Definitely; Server brand changed to '" + string + "'") : Optional.empty();
    }

    @Override
    public void systemExitNow() {
        if (this.field_244714_r != null) {
            this.field_244714_r.close();
        }
        if (this.serverGui != null) {
            this.serverGui.func_219050_b();
        }
        if (this.rconThread != null) {
            this.rconThread.func_219591_b();
        }
        if (this.rconQueryThread != null) {
            this.rconQueryThread.func_219591_b();
        }
    }

    @Override
    public void updateTimeLightAndEntities(BooleanSupplier booleanSupplier) {
        super.updateTimeLightAndEntities(booleanSupplier);
        this.executePendingCommands();
    }

    @Override
    public boolean getAllowNether() {
        return this.getServerProperties().allowNether;
    }

    @Override
    public void fillSnooper(Snooper snooper) {
        snooper.addClientStat("whitelist_enabled", this.getPlayerList().isWhiteListEnabled());
        snooper.addClientStat("whitelist_count", this.getPlayerList().getWhitelistedPlayerNames().length);
        super.fillSnooper(snooper);
    }

    public void handleConsoleInput(String string, CommandSource commandSource) {
        this.pendingCommandList.add(new PendingCommand(string, commandSource));
    }

    public void executePendingCommands() {
        while (!this.pendingCommandList.isEmpty()) {
            PendingCommand pendingCommand = this.pendingCommandList.remove(0);
            this.getCommandManager().handleCommand(pendingCommand.sender, pendingCommand.command);
        }
    }

    @Override
    public boolean isDedicatedServer() {
        return false;
    }

    @Override
    public int func_241871_k() {
        return this.getServerProperties().rateLimit;
    }

    @Override
    public boolean shouldUseNativeTransport() {
        return this.getServerProperties().useNativeTransport;
    }

    @Override
    public DedicatedPlayerList getPlayerList() {
        return (DedicatedPlayerList)super.getPlayerList();
    }

    @Override
    public boolean getPublic() {
        return false;
    }

    @Override
    public String getHostname() {
        return this.getServerHostname();
    }

    @Override
    public int getPort() {
        return this.getServerPort();
    }

    @Override
    public String getMotd() {
        return this.getMOTD();
    }

    public void setGuiEnabled() {
        if (this.serverGui == null) {
            this.serverGui = MinecraftServerGui.func_219048_a(this);
        }
    }

    @Override
    public boolean getGuiEnabled() {
        return this.serverGui != null;
    }

    @Override
    public boolean shareToLAN(GameType gameType, boolean bl, int n) {
        return true;
    }

    @Override
    public boolean isCommandBlockEnabled() {
        return this.getServerProperties().enableCommandBlock;
    }

    @Override
    public int getSpawnProtectionSize() {
        return this.getServerProperties().spawnProtection;
    }

    @Override
    public boolean isBlockProtected(ServerWorld serverWorld, BlockPos blockPos, PlayerEntity playerEntity) {
        int n;
        if (serverWorld.getDimensionKey() != World.OVERWORLD) {
            return true;
        }
        if (this.getPlayerList().getOppedPlayers().isEmpty()) {
            return true;
        }
        if (this.getPlayerList().canSendCommands(playerEntity.getGameProfile())) {
            return true;
        }
        if (this.getSpawnProtectionSize() <= 0) {
            return true;
        }
        BlockPos blockPos2 = serverWorld.getSpawnPoint();
        int n2 = MathHelper.abs(blockPos.getX() - blockPos2.getX());
        int n3 = Math.max(n2, n = MathHelper.abs(blockPos.getZ() - blockPos2.getZ()));
        return n3 <= this.getSpawnProtectionSize();
    }

    @Override
    public boolean func_230541_aj_() {
        return this.getServerProperties().field_241080_Q_;
    }

    @Override
    public int getOpPermissionLevel() {
        return this.getServerProperties().opPermissionLevel;
    }

    @Override
    public int getFunctionLevel() {
        return this.getServerProperties().functionPermissionLevel;
    }

    @Override
    public void setPlayerIdleTimeout(int n) {
        super.setPlayerIdleTimeout(n);
        this.settings.func_219033_a(arg_0 -> this.lambda$setPlayerIdleTimeout$2(n, arg_0));
    }

    @Override
    public boolean allowLoggingRcon() {
        return this.getServerProperties().broadcastRconToOps;
    }

    @Override
    public boolean allowLogging() {
        return this.getServerProperties().broadcastConsoleToOps;
    }

    @Override
    public int getMaxWorldSize() {
        return this.getServerProperties().maxWorldSize;
    }

    @Override
    public int getNetworkCompressionThreshold() {
        return this.getServerProperties().networkCompressionThreshold;
    }

    protected boolean convertFiles() {
        int n;
        int n2;
        int n3;
        int n4;
        boolean bl = false;
        for (n4 = 0; !bl && n4 <= 2; ++n4) {
            if (n4 > 0) {
                LOGGER.warn("Encountered a problem while converting the user banlist, retrying in a few seconds");
                this.sleepFiveSeconds();
            }
            bl = PreYggdrasilConverter.convertUserBanlist(this);
        }
        n4 = 0;
        for (n3 = 0; n4 == 0 && n3 <= 2; ++n3) {
            if (n3 > 0) {
                LOGGER.warn("Encountered a problem while converting the ip banlist, retrying in a few seconds");
                this.sleepFiveSeconds();
            }
            n4 = PreYggdrasilConverter.convertIpBanlist(this) ? 1 : 0;
        }
        n3 = 0;
        for (n2 = 0; n3 == 0 && n2 <= 2; ++n2) {
            if (n2 > 0) {
                LOGGER.warn("Encountered a problem while converting the op list, retrying in a few seconds");
                this.sleepFiveSeconds();
            }
            n3 = PreYggdrasilConverter.convertOplist(this) ? 1 : 0;
        }
        n2 = 0;
        for (n = 0; n2 == 0 && n <= 2; ++n) {
            if (n > 0) {
                LOGGER.warn("Encountered a problem while converting the whitelist, retrying in a few seconds");
                this.sleepFiveSeconds();
            }
            n2 = PreYggdrasilConverter.convertWhitelist(this) ? 1 : 0;
        }
        n = 0;
        for (int i = 0; n == 0 && i <= 2; ++i) {
            if (i > 0) {
                LOGGER.warn("Encountered a problem while converting the player save files, retrying in a few seconds");
                this.sleepFiveSeconds();
            }
            n = PreYggdrasilConverter.convertSaveFiles(this) ? 1 : 0;
        }
        return bl || n4 != 0 || n3 != 0 || n2 != 0 || n != 0;
    }

    private void sleepFiveSeconds() {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }

    public long getMaxTickTime() {
        return this.getServerProperties().maxTickTime;
    }

    @Override
    public String getPlugins() {
        return "";
    }

    @Override
    public String handleRConCommand(String string) {
        this.rconConsoleSource.resetLog();
        this.runImmediately(() -> this.lambda$handleRConCommand$3(string));
        return this.rconConsoleSource.getLogContents();
    }

    public void func_213223_o(boolean bl) {
        this.settings.func_219033_a(arg_0 -> this.lambda$func_213223_o$4(bl, arg_0));
    }

    @Override
    public void stopServer() {
        super.stopServer();
        Util.shutdown();
    }

    @Override
    public boolean isServerOwner(GameProfile gameProfile) {
        return true;
    }

    @Override
    public int func_230512_b_(int n) {
        return this.getServerProperties().field_241081_R_ * n / 100;
    }

    @Override
    public String func_230542_k__() {
        return this.anvilConverterForAnvilFile.getSaveName();
    }

    @Override
    public boolean func_230540_aS_() {
        return this.settings.getProperties().field_241078_O_;
    }

    @Override
    @Nullable
    public IChatFilter func_244435_a(ServerPlayerEntity serverPlayerEntity) {
        return this.field_244714_r != null ? this.field_244714_r.func_244566_a(serverPlayerEntity.getGameProfile()) : null;
    }

    @Override
    public PlayerList getPlayerList() {
        return this.getPlayerList();
    }

    private ServerProperties lambda$func_213223_o$4(boolean bl, ServerProperties serverProperties) {
        return (ServerProperties)serverProperties.whitelistEnabled.func_244381_a(this.func_244267_aX(), bl);
    }

    private void lambda$handleRConCommand$3(String string) {
        this.getCommandManager().handleCommand(this.rconConsoleSource.getCommandSource(), string);
    }

    private ServerProperties lambda$setPlayerIdleTimeout$2(int n, ServerProperties serverProperties) {
        return (ServerProperties)serverProperties.playerIdleTimeout.func_244381_a(this.func_244267_aX(), n);
    }

    private static String lambda$addServerInfoToCrashReport$1() throws Exception {
        return "Dedicated Server (map_server.txt)";
    }

    private String lambda$addServerInfoToCrashReport$0() throws Exception {
        return this.func_230045_q_().orElse("Unknown (can't tell)");
    }
}

