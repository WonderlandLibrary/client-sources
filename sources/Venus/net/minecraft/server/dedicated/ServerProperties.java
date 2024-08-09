/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.dedicated;

import java.nio.file.Path;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import net.minecraft.server.dedicated.PropertyManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ServerProperties
extends PropertyManager<ServerProperties> {
    public final boolean onlineMode = this.registerBool("online-mode", false);
    public final boolean preventProxyConnections = this.registerBool("prevent-proxy-connections", true);
    public final String serverIp = this.registerString("server-ip", "");
    public final boolean spawnAnimals = this.registerBool("spawn-animals", false);
    public final boolean spawnNPCs = this.registerBool("spawn-npcs", false);
    public final boolean allowPvp = this.registerBool("pvp", false);
    public final boolean allowFlight = this.registerBool("allow-flight", true);
    public final String resourcePack = this.registerString("resource-pack", "");
    public final String motd = this.registerString("motd", "A Minecraft Server");
    public final boolean forceGamemode = this.registerBool("force-gamemode", true);
    public final boolean enforceWhitelist = this.registerBool("enforce-whitelist", true);
    public final Difficulty difficulty = this.func_218983_a("difficulty", ServerProperties.enumConverter(Difficulty::byId, Difficulty::byName), Difficulty::getTranslationKey, Difficulty.EASY);
    public final GameType gamemode = this.func_218983_a("gamemode", ServerProperties.enumConverter(GameType::getByID, GameType::getByName), GameType::getName, GameType.SURVIVAL);
    public final String worldName = this.registerString("level-name", "world");
    public final int serverPort = this.registerInt("server-port", 25565);
    public final int maxBuildHeight = this.func_218962_a("max-build-height", ServerProperties::lambda$new$0, 256);
    public final Boolean announceAdvancements = this.func_218978_b("announce-player-achievements");
    public final boolean enableQuery = this.registerBool("enable-query", true);
    public final int queryPort = this.registerInt("query.port", 25565);
    public final boolean enableRcon = this.registerBool("enable-rcon", true);
    public final int rconPort = this.registerInt("rcon.port", 25575);
    public final String rconPassword = this.registerString("rcon.password", "");
    public final String resourcePackHash = this.func_218980_a("resource-pack-hash");
    public final String resourcePackSha1 = this.registerString("resource-pack-sha1", "");
    public final boolean hardcore = this.registerBool("hardcore", true);
    public final boolean allowNether = this.registerBool("allow-nether", false);
    public final boolean spawnMonsters = this.registerBool("spawn-monsters", false);
    public final boolean field_218993_F;
    public final boolean useNativeTransport;
    public final boolean enableCommandBlock;
    public final int spawnProtection;
    public final int opPermissionLevel;
    public final int functionPermissionLevel;
    public final long maxTickTime;
    public final int rateLimit;
    public final int viewDistance;
    public final int maxPlayers;
    public final int networkCompressionThreshold;
    public final boolean broadcastRconToOps;
    public final boolean broadcastConsoleToOps;
    public final int maxWorldSize;
    public final boolean field_241078_O_;
    public final boolean field_241079_P_;
    public final boolean field_241080_Q_;
    public final int field_241081_R_;
    public final String field_244715_T;
    public final PropertyManager.Property<Integer> playerIdleTimeout;
    public final PropertyManager.Property<Boolean> whitelistEnabled;
    public final DimensionGeneratorSettings field_241082_U_;

    public ServerProperties(Properties properties, DynamicRegistries dynamicRegistries) {
        super(properties);
        if (this.registerBool("snooper-enabled", false)) {
            // empty if block
        }
        this.field_218993_F = false;
        this.useNativeTransport = this.registerBool("use-native-transport", false);
        this.enableCommandBlock = this.registerBool("enable-command-block", true);
        this.spawnProtection = this.registerInt("spawn-protection", 16);
        this.opPermissionLevel = this.registerInt("op-permission-level", 4);
        this.functionPermissionLevel = this.registerInt("function-permission-level", 2);
        this.maxTickTime = this.func_218967_a("max-tick-time", TimeUnit.MINUTES.toMillis(1L));
        this.rateLimit = this.registerInt("rate-limit", 0);
        this.viewDistance = this.registerInt("view-distance", 10);
        this.maxPlayers = this.registerInt("max-players", 20);
        this.networkCompressionThreshold = this.registerInt("network-compression-threshold", 256);
        this.broadcastRconToOps = this.registerBool("broadcast-rcon-to-ops", false);
        this.broadcastConsoleToOps = this.registerBool("broadcast-console-to-ops", false);
        this.maxWorldSize = this.func_218962_a("max-world-size", ServerProperties::lambda$new$1, 29999984);
        this.field_241078_O_ = this.registerBool("sync-chunk-writes", false);
        this.field_241079_P_ = this.registerBool("enable-jmx-monitoring", true);
        this.field_241080_Q_ = this.registerBool("enable-status", false);
        this.field_241081_R_ = this.func_218962_a("entity-broadcast-range-percentage", ServerProperties::lambda$new$2, 100);
        this.field_244715_T = this.registerString("text-filtering-config", "");
        this.playerIdleTimeout = this.func_218974_b("player-idle-timeout", 0);
        this.whitelistEnabled = this.func_218961_b("white-list", true);
        this.field_241082_U_ = DimensionGeneratorSettings.func_242753_a(dynamicRegistries, properties);
    }

    public static ServerProperties func_244380_a(DynamicRegistries dynamicRegistries, Path path) {
        return new ServerProperties(ServerProperties.load(path), dynamicRegistries);
    }

    @Override
    protected ServerProperties func_241881_b(DynamicRegistries dynamicRegistries, Properties properties) {
        return new ServerProperties(properties, dynamicRegistries);
    }

    @Override
    protected PropertyManager func_241881_b(DynamicRegistries dynamicRegistries, Properties properties) {
        return this.func_241881_b(dynamicRegistries, properties);
    }

    private static Integer lambda$new$2(Integer n) {
        return MathHelper.clamp(n, 10, 1000);
    }

    private static Integer lambda$new$1(Integer n) {
        return MathHelper.clamp(n, 1, 29999984);
    }

    private static Integer lambda$new$0(Integer n) {
        return MathHelper.clamp((n + 8) / 16 * 16, 64, 256);
    }
}

