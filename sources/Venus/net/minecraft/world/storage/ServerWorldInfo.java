/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.command.TimerCallbackManager;
import net.minecraft.command.TimerCallbackSerializers;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.nbt.StringNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.UUIDCodec;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DefaultTypeReferences;
import net.minecraft.util.datafix.codec.DatapackCodec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.WorldGenSettingsExport;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.storage.IServerConfiguration;
import net.minecraft.world.storage.IServerWorldInfo;
import net.minecraft.world.storage.VersionData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerWorldInfo
implements IServerWorldInfo,
IServerConfiguration {
    private static final Logger LOGGER = LogManager.getLogger();
    private WorldSettings worldSettings;
    private final DimensionGeneratorSettings generatorSettings;
    private final Lifecycle lifecycle;
    private int spawnX;
    private int spawnY;
    private int spawnZ;
    private float spawnAngle;
    private long gameTime;
    private long dayTime;
    @Nullable
    private final DataFixer dataFixer;
    private final int version;
    private boolean dataFixed;
    @Nullable
    private CompoundNBT loadedPlayerNBT;
    private final int levelStorageVersion;
    private int clearWeatherTime;
    private boolean raining;
    private int rainTime;
    private boolean thundering;
    private int thunderTime;
    private boolean initialized;
    private boolean difficultyLocked;
    private WorldBorder.Serializer borderSerializer;
    private CompoundNBT dragonFightNBT;
    @Nullable
    private CompoundNBT customBossEventNBT;
    private int wanderingTraderSpawnDelay;
    private int wanderingTraderSpawnChance;
    @Nullable
    private UUID wanderingTraderID;
    private final Set<String> serverBrands;
    private boolean wasModded;
    private final TimerCallbackManager<MinecraftServer> schedueledEvents;

    private ServerWorldInfo(@Nullable DataFixer dataFixer, int n, @Nullable CompoundNBT compoundNBT, boolean bl, int n2, int n3, int n4, float f, long l, long l2, int n5, int n6, int n7, boolean bl2, int n8, boolean bl3, boolean bl4, boolean bl5, WorldBorder.Serializer serializer, int n9, int n10, @Nullable UUID uUID, LinkedHashSet<String> linkedHashSet, TimerCallbackManager<MinecraftServer> timerCallbackManager, @Nullable CompoundNBT compoundNBT2, CompoundNBT compoundNBT3, WorldSettings worldSettings, DimensionGeneratorSettings dimensionGeneratorSettings, Lifecycle lifecycle) {
        this.dataFixer = dataFixer;
        this.wasModded = bl;
        this.spawnX = n2;
        this.spawnY = n3;
        this.spawnZ = n4;
        this.spawnAngle = f;
        this.gameTime = l;
        this.dayTime = l2;
        this.levelStorageVersion = n5;
        this.clearWeatherTime = n6;
        this.rainTime = n7;
        this.raining = bl2;
        this.thunderTime = n8;
        this.thundering = bl3;
        this.initialized = bl4;
        this.difficultyLocked = bl5;
        this.borderSerializer = serializer;
        this.wanderingTraderSpawnDelay = n9;
        this.wanderingTraderSpawnChance = n10;
        this.wanderingTraderID = uUID;
        this.serverBrands = linkedHashSet;
        this.loadedPlayerNBT = compoundNBT;
        this.version = n;
        this.schedueledEvents = timerCallbackManager;
        this.customBossEventNBT = compoundNBT2;
        this.dragonFightNBT = compoundNBT3;
        this.worldSettings = worldSettings;
        this.generatorSettings = dimensionGeneratorSettings;
        this.lifecycle = lifecycle;
    }

    public ServerWorldInfo(WorldSettings worldSettings, DimensionGeneratorSettings dimensionGeneratorSettings, Lifecycle lifecycle) {
        this(null, SharedConstants.getVersion().getWorldVersion(), null, false, 0, 0, 0, 0.0f, 0L, 0L, 19133, 0, 0, false, 0, false, false, false, WorldBorder.DEFAULT_SERIALIZER, 0, 0, null, Sets.newLinkedHashSet(), new TimerCallbackManager<MinecraftServer>(TimerCallbackSerializers.field_216342_a), null, new CompoundNBT(), worldSettings.clone(), dimensionGeneratorSettings, lifecycle);
    }

    public static ServerWorldInfo decodeWorldInfo(Dynamic<INBT> dynamic, DataFixer dataFixer, int n, @Nullable CompoundNBT compoundNBT, WorldSettings worldSettings, VersionData versionData, DimensionGeneratorSettings dimensionGeneratorSettings, Lifecycle lifecycle) {
        long l = dynamic.get("Time").asLong(0L);
        CompoundNBT compoundNBT2 = (CompoundNBT)dynamic.get("DragonFight").result().map(Dynamic::getValue).orElseGet(() -> ServerWorldInfo.lambda$decodeWorldInfo$0(dynamic));
        return new ServerWorldInfo(dataFixer, n, compoundNBT, dynamic.get("WasModded").asBoolean(true), dynamic.get("SpawnX").asInt(0), dynamic.get("SpawnY").asInt(0), dynamic.get("SpawnZ").asInt(0), dynamic.get("SpawnAngle").asFloat(0.0f), l, dynamic.get("DayTime").asLong(l), versionData.getStorageVersionID(), dynamic.get("clearWeatherTime").asInt(0), dynamic.get("rainTime").asInt(0), dynamic.get("raining").asBoolean(true), dynamic.get("thunderTime").asInt(0), dynamic.get("thundering").asBoolean(true), dynamic.get("initialized").asBoolean(false), dynamic.get("DifficultyLocked").asBoolean(true), WorldBorder.Serializer.deserialize(dynamic, WorldBorder.DEFAULT_SERIALIZER), dynamic.get("WanderingTraderSpawnDelay").asInt(0), dynamic.get("WanderingTraderSpawnChance").asInt(0), dynamic.get("WanderingTraderId").read(UUIDCodec.CODEC).result().orElse(null), dynamic.get("ServerBrands").asStream().flatMap(ServerWorldInfo::lambda$decodeWorldInfo$1).collect(Collectors.toCollection(Sets::newLinkedHashSet)), new TimerCallbackManager<MinecraftServer>(TimerCallbackSerializers.field_216342_a, dynamic.get("ScheduledEvents").asStream()), (CompoundNBT)dynamic.get("CustomBossEvents").orElseEmptyMap().getValue(), compoundNBT2, worldSettings, dimensionGeneratorSettings, lifecycle);
    }

    @Override
    public CompoundNBT serialize(DynamicRegistries dynamicRegistries, @Nullable CompoundNBT compoundNBT) {
        this.updatePlayerData();
        if (compoundNBT == null) {
            compoundNBT = this.loadedPlayerNBT;
        }
        CompoundNBT compoundNBT2 = new CompoundNBT();
        this.serialize(dynamicRegistries, compoundNBT2, compoundNBT);
        return compoundNBT2;
    }

    private void serialize(DynamicRegistries dynamicRegistries, CompoundNBT compoundNBT, @Nullable CompoundNBT compoundNBT2) {
        ListNBT listNBT = new ListNBT();
        this.serverBrands.stream().map(StringNBT::valueOf).forEach(listNBT::add);
        compoundNBT.put("ServerBrands", listNBT);
        compoundNBT.putBoolean("WasModded", this.wasModded);
        CompoundNBT compoundNBT3 = new CompoundNBT();
        compoundNBT3.putString("Name", SharedConstants.getVersion().getName());
        compoundNBT3.putInt("Id", SharedConstants.getVersion().getWorldVersion());
        compoundNBT3.putBoolean("Snapshot", !SharedConstants.getVersion().isStable());
        compoundNBT.put("Version", compoundNBT3);
        compoundNBT.putInt("DataVersion", SharedConstants.getVersion().getWorldVersion());
        WorldGenSettingsExport<INBT> worldGenSettingsExport = WorldGenSettingsExport.create(NBTDynamicOps.INSTANCE, dynamicRegistries);
        DimensionGeneratorSettings.field_236201_a_.encodeStart(worldGenSettingsExport, this.generatorSettings).resultOrPartial(Util.func_240982_a_("WorldGenSettings: ", LOGGER::error)).ifPresent(arg_0 -> ServerWorldInfo.lambda$serialize$2(compoundNBT, arg_0));
        compoundNBT.putInt("GameType", this.worldSettings.getGameType().getID());
        compoundNBT.putInt("SpawnX", this.spawnX);
        compoundNBT.putInt("SpawnY", this.spawnY);
        compoundNBT.putInt("SpawnZ", this.spawnZ);
        compoundNBT.putFloat("SpawnAngle", this.spawnAngle);
        compoundNBT.putLong("Time", this.gameTime);
        compoundNBT.putLong("DayTime", this.dayTime);
        compoundNBT.putLong("LastPlayed", Util.millisecondsSinceEpoch());
        compoundNBT.putString("LevelName", this.worldSettings.getWorldName());
        compoundNBT.putInt("version", 19133);
        compoundNBT.putInt("clearWeatherTime", this.clearWeatherTime);
        compoundNBT.putInt("rainTime", this.rainTime);
        compoundNBT.putBoolean("raining", this.raining);
        compoundNBT.putInt("thunderTime", this.thunderTime);
        compoundNBT.putBoolean("thundering", this.thundering);
        compoundNBT.putBoolean("hardcore", this.worldSettings.isHardcoreEnabled());
        compoundNBT.putBoolean("allowCommands", this.worldSettings.isCommandsAllowed());
        compoundNBT.putBoolean("initialized", this.initialized);
        this.borderSerializer.serialize(compoundNBT);
        compoundNBT.putByte("Difficulty", (byte)this.worldSettings.getDifficulty().getId());
        compoundNBT.putBoolean("DifficultyLocked", this.difficultyLocked);
        compoundNBT.put("GameRules", this.worldSettings.getGameRules().write());
        compoundNBT.put("DragonFight", this.dragonFightNBT);
        if (compoundNBT2 != null) {
            compoundNBT.put("Player", compoundNBT2);
        }
        DatapackCodec.CODEC.encodeStart(NBTDynamicOps.INSTANCE, this.worldSettings.getDatapackCodec()).result().ifPresent(arg_0 -> ServerWorldInfo.lambda$serialize$3(compoundNBT, arg_0));
        if (this.customBossEventNBT != null) {
            compoundNBT.put("CustomBossEvents", this.customBossEventNBT);
        }
        compoundNBT.put("ScheduledEvents", this.schedueledEvents.write());
        compoundNBT.putInt("WanderingTraderSpawnDelay", this.wanderingTraderSpawnDelay);
        compoundNBT.putInt("WanderingTraderSpawnChance", this.wanderingTraderSpawnChance);
        if (this.wanderingTraderID != null) {
            compoundNBT.putUniqueId("WanderingTraderId", this.wanderingTraderID);
        }
    }

    @Override
    public int getSpawnX() {
        return this.spawnX;
    }

    @Override
    public int getSpawnY() {
        return this.spawnY;
    }

    @Override
    public int getSpawnZ() {
        return this.spawnZ;
    }

    @Override
    public float getSpawnAngle() {
        return this.spawnAngle;
    }

    @Override
    public long getGameTime() {
        return this.gameTime;
    }

    @Override
    public long getDayTime() {
        return this.dayTime;
    }

    private void updatePlayerData() {
        if (!this.dataFixed && this.loadedPlayerNBT != null) {
            if (this.version < SharedConstants.getVersion().getWorldVersion()) {
                if (this.dataFixer == null) {
                    throw Util.pauseDevMode(new NullPointerException("Fixer Upper not set inside LevelData, and the player tag is not upgraded."));
                }
                this.loadedPlayerNBT = NBTUtil.update(this.dataFixer, DefaultTypeReferences.PLAYER, this.loadedPlayerNBT, this.version);
            }
            this.dataFixed = true;
        }
    }

    @Override
    public CompoundNBT getHostPlayerNBT() {
        this.updatePlayerData();
        return this.loadedPlayerNBT;
    }

    @Override
    public void setSpawnX(int n) {
        this.spawnX = n;
    }

    @Override
    public void setSpawnY(int n) {
        this.spawnY = n;
    }

    @Override
    public void setSpawnZ(int n) {
        this.spawnZ = n;
    }

    @Override
    public void setSpawnAngle(float f) {
        this.spawnAngle = f;
    }

    @Override
    public void setGameTime(long l) {
        this.gameTime = l;
    }

    @Override
    public void setDayTime(long l) {
        this.dayTime = l;
    }

    @Override
    public void setSpawn(BlockPos blockPos, float f) {
        this.spawnX = blockPos.getX();
        this.spawnY = blockPos.getY();
        this.spawnZ = blockPos.getZ();
        this.spawnAngle = f;
    }

    @Override
    public String getWorldName() {
        return this.worldSettings.getWorldName();
    }

    @Override
    public int getStorageVersionId() {
        return this.levelStorageVersion;
    }

    @Override
    public int getClearWeatherTime() {
        return this.clearWeatherTime;
    }

    @Override
    public void setClearWeatherTime(int n) {
        this.clearWeatherTime = n;
    }

    @Override
    public boolean isThundering() {
        return this.thundering;
    }

    @Override
    public void setThundering(boolean bl) {
        this.thundering = bl;
    }

    @Override
    public int getThunderTime() {
        return this.thunderTime;
    }

    @Override
    public void setThunderTime(int n) {
        this.thunderTime = n;
    }

    @Override
    public boolean isRaining() {
        return this.raining;
    }

    @Override
    public void setRaining(boolean bl) {
        this.raining = bl;
    }

    @Override
    public int getRainTime() {
        return this.rainTime;
    }

    @Override
    public void setRainTime(int n) {
        this.rainTime = n;
    }

    @Override
    public GameType getGameType() {
        return this.worldSettings.getGameType();
    }

    @Override
    public void setGameType(GameType gameType) {
        this.worldSettings = this.worldSettings.setGameType(gameType);
    }

    @Override
    public boolean isHardcore() {
        return this.worldSettings.isHardcoreEnabled();
    }

    @Override
    public boolean areCommandsAllowed() {
        return this.worldSettings.isCommandsAllowed();
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }

    @Override
    public void setInitialized(boolean bl) {
        this.initialized = bl;
    }

    @Override
    public GameRules getGameRulesInstance() {
        return this.worldSettings.getGameRules();
    }

    @Override
    public WorldBorder.Serializer getWorldBorderSerializer() {
        return this.borderSerializer;
    }

    @Override
    public void setWorldBorderSerializer(WorldBorder.Serializer serializer) {
        this.borderSerializer = serializer;
    }

    @Override
    public Difficulty getDifficulty() {
        return this.worldSettings.getDifficulty();
    }

    @Override
    public void setDifficulty(Difficulty difficulty) {
        this.worldSettings = this.worldSettings.setDifficulty(difficulty);
    }

    @Override
    public boolean isDifficultyLocked() {
        return this.difficultyLocked;
    }

    @Override
    public void setDifficultyLocked(boolean bl) {
        this.difficultyLocked = bl;
    }

    @Override
    public TimerCallbackManager<MinecraftServer> getScheduledEvents() {
        return this.schedueledEvents;
    }

    @Override
    public void addToCrashReport(CrashReportCategory crashReportCategory) {
        IServerWorldInfo.super.addToCrashReport(crashReportCategory);
        IServerConfiguration.super.addToCrashReport(crashReportCategory);
    }

    @Override
    public DimensionGeneratorSettings getDimensionGeneratorSettings() {
        return this.generatorSettings;
    }

    @Override
    public Lifecycle getLifecycle() {
        return this.lifecycle;
    }

    @Override
    public CompoundNBT getDragonFightData() {
        return this.dragonFightNBT;
    }

    @Override
    public void setDragonFightData(CompoundNBT compoundNBT) {
        this.dragonFightNBT = compoundNBT;
    }

    @Override
    public DatapackCodec getDatapackCodec() {
        return this.worldSettings.getDatapackCodec();
    }

    @Override
    public void setDatapackCodec(DatapackCodec datapackCodec) {
        this.worldSettings = this.worldSettings.setDatapackCodec(datapackCodec);
    }

    @Override
    @Nullable
    public CompoundNBT getCustomBossEventData() {
        return this.customBossEventNBT;
    }

    @Override
    public void setCustomBossEventData(@Nullable CompoundNBT compoundNBT) {
        this.customBossEventNBT = compoundNBT;
    }

    @Override
    public int getWanderingTraderSpawnDelay() {
        return this.wanderingTraderSpawnDelay;
    }

    @Override
    public void setWanderingTraderSpawnDelay(int n) {
        this.wanderingTraderSpawnDelay = n;
    }

    @Override
    public int getWanderingTraderSpawnChance() {
        return this.wanderingTraderSpawnChance;
    }

    @Override
    public void setWanderingTraderSpawnChance(int n) {
        this.wanderingTraderSpawnChance = n;
    }

    @Override
    public void setWanderingTraderID(UUID uUID) {
        this.wanderingTraderID = uUID;
    }

    @Override
    public void addServerBranding(String string, boolean bl) {
        this.serverBrands.add(string);
        this.wasModded |= bl;
    }

    @Override
    public boolean isModded() {
        return this.wasModded;
    }

    @Override
    public Set<String> getServerBranding() {
        return ImmutableSet.copyOf(this.serverBrands);
    }

    @Override
    public IServerWorldInfo getServerWorldInfo() {
        return this;
    }

    @Override
    public WorldSettings getWorldSettings() {
        return this.worldSettings.clone();
    }

    private static void lambda$serialize$3(CompoundNBT compoundNBT, INBT iNBT) {
        compoundNBT.put("DataPacks", iNBT);
    }

    private static void lambda$serialize$2(CompoundNBT compoundNBT, INBT iNBT) {
        compoundNBT.put("WorldGenSettings", iNBT);
    }

    private static Stream lambda$decodeWorldInfo$1(Dynamic dynamic) {
        return Util.streamOptional(dynamic.asString().result());
    }

    private static INBT lambda$decodeWorldInfo$0(Dynamic dynamic) {
        return (INBT)dynamic.get("DimensionData").get("1").get("DragonFight").orElseEmptyMap().getValue();
    }
}

