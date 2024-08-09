/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.configuration;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.minecraft.WorldIdentifiers;
import com.viaversion.viaversion.api.protocol.version.BlockedProtocolVersions;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocol.BlockedProtocolVersionsImpl;
import com.viaversion.viaversion.util.Config;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class AbstractViaConfig
extends Config
implements ViaVersionConfig {
    private boolean checkForUpdates;
    private boolean preventCollision;
    private boolean useNewEffectIndicator;
    private boolean useNewDeathmessages;
    private boolean suppressMetadataErrors;
    private boolean shieldBlocking;
    private boolean noDelayShieldBlocking;
    private boolean showShieldWhenSwordInHand;
    private boolean hologramPatch;
    private boolean pistonAnimationPatch;
    private boolean bossbarPatch;
    private boolean bossbarAntiFlicker;
    private double hologramOffset;
    private int maxPPS;
    private String maxPPSKickMessage;
    private int trackingPeriod;
    private int warningPPS;
    private int maxPPSWarnings;
    private String maxPPSWarningsKickMessage;
    private boolean sendSupportedVersions;
    private boolean simulatePlayerTick;
    private boolean itemCache;
    private boolean nmsPlayerTicking;
    private boolean replacePistons;
    private int pistonReplacementId;
    private boolean chunkBorderFix;
    private boolean autoTeam;
    private boolean forceJsonTransform;
    private boolean nbtArrayFix;
    private BlockedProtocolVersions blockedProtocolVersions;
    private String blockedDisconnectMessage;
    private String reloadDisconnectMessage;
    private boolean suppressConversionWarnings;
    private boolean disable1_13TabComplete;
    private boolean minimizeCooldown;
    private boolean teamColourFix;
    private boolean serversideBlockConnections;
    private boolean reduceBlockStorageMemory;
    private boolean flowerStemWhenBlockAbove;
    private boolean vineClimbFix;
    private boolean snowCollisionFix;
    private boolean infestedBlocksFix;
    private int tabCompleteDelay;
    private boolean truncate1_14Books;
    private boolean leftHandedHandling;
    private boolean fullBlockLightFix;
    private boolean healthNaNFix;
    private boolean instantRespawn;
    private boolean ignoreLongChannelNames;
    private boolean forcedUse1_17ResourcePack;
    private JsonElement resourcePack1_17PromptMessage;
    private WorldIdentifiers map1_16WorldNames;
    private boolean cache1_17Light;
    private Map<String, String> chatTypeFormats;

    protected AbstractViaConfig(File file) {
        super(file);
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        this.loadFields();
    }

    protected void loadFields() {
        this.checkForUpdates = this.getBoolean("checkforupdates", false);
        this.preventCollision = this.getBoolean("prevent-collision", false);
        this.useNewEffectIndicator = this.getBoolean("use-new-effect-indicator", false);
        this.useNewDeathmessages = this.getBoolean("use-new-deathmessages", false);
        this.suppressMetadataErrors = this.getBoolean("suppress-metadata-errors", true);
        this.shieldBlocking = this.getBoolean("shield-blocking", false);
        this.noDelayShieldBlocking = this.getBoolean("no-delay-shield-blocking", true);
        this.showShieldWhenSwordInHand = this.getBoolean("show-shield-when-sword-in-hand", true);
        this.hologramPatch = this.getBoolean("hologram-patch", true);
        this.pistonAnimationPatch = this.getBoolean("piston-animation-patch", true);
        this.bossbarPatch = this.getBoolean("bossbar-patch", false);
        this.bossbarAntiFlicker = this.getBoolean("bossbar-anti-flicker", true);
        this.hologramOffset = this.getDouble("hologram-y", -0.96);
        this.maxPPS = this.getInt("max-pps", 800);
        this.maxPPSKickMessage = this.getString("max-pps-kick-msg", "Sending packets too fast? lag?");
        this.trackingPeriod = this.getInt("tracking-period", 6);
        this.warningPPS = this.getInt("tracking-warning-pps", 120);
        this.maxPPSWarnings = this.getInt("tracking-max-warnings", 3);
        this.maxPPSWarningsKickMessage = this.getString("tracking-max-kick-msg", "You are sending too many packets, :(");
        this.sendSupportedVersions = this.getBoolean("send-supported-versions", true);
        this.simulatePlayerTick = this.getBoolean("simulate-pt", false);
        this.itemCache = this.getBoolean("item-cache", false);
        this.nmsPlayerTicking = this.getBoolean("nms-player-ticking", false);
        this.replacePistons = this.getBoolean("replace-pistons", true);
        this.pistonReplacementId = this.getInt("replacement-piston-id", 0);
        this.chunkBorderFix = this.getBoolean("chunk-border-fix", true);
        this.autoTeam = this.getBoolean("auto-team", false);
        this.forceJsonTransform = this.getBoolean("force-json-transform", true);
        this.nbtArrayFix = this.getBoolean("chat-nbt-fix", false);
        this.blockedProtocolVersions = this.loadBlockedProtocolVersions();
        this.blockedDisconnectMessage = this.getString("block-disconnect-msg", "You are using an unsupported Minecraft version!");
        this.reloadDisconnectMessage = this.getString("reload-disconnect-msg", "Server reload, please rejoin!");
        this.minimizeCooldown = this.getBoolean("minimize-cooldown", false);
        this.teamColourFix = this.getBoolean("team-colour-fix", false);
        this.suppressConversionWarnings = this.getBoolean("suppress-conversion-warnings", true);
        this.disable1_13TabComplete = this.getBoolean("disable-1_13-auto-complete", true);
        this.serversideBlockConnections = this.getBoolean("serverside-blockconnections", false);
        this.reduceBlockStorageMemory = this.getBoolean("reduce-blockstorage-memory", true);
        this.flowerStemWhenBlockAbove = this.getBoolean("flowerstem-when-block-above", true);
        this.vineClimbFix = this.getBoolean("vine-climb-fix", true);
        this.snowCollisionFix = this.getBoolean("fix-low-snow-collision", true);
        this.infestedBlocksFix = this.getBoolean("fix-infested-block-breaking", false);
        this.tabCompleteDelay = this.getInt("1_13-tab-complete-delay", 0);
        this.truncate1_14Books = this.getBoolean("truncate-1_14-books", true);
        this.leftHandedHandling = this.getBoolean("left-handed-handling", false);
        this.fullBlockLightFix = this.getBoolean("fix-non-full-blocklight", true);
        this.healthNaNFix = this.getBoolean("fix-1_14-health-nan", false);
        this.instantRespawn = this.getBoolean("use-1_15-instant-respawn", true);
        this.ignoreLongChannelNames = this.getBoolean("ignore-long-1_16-channel-names", false);
        this.forcedUse1_17ResourcePack = this.getBoolean("forced-use-1_17-resource-pack", true);
        this.resourcePack1_17PromptMessage = this.getSerializedComponent("resource-pack-1_17-prompt");
        Map map = this.get("map-1_16-world-names", Map.class, new HashMap());
        this.map1_16WorldNames = new WorldIdentifiers(map.getOrDefault("overworld", "minecraft:overworld"), map.getOrDefault("nether", "minecraft:the_nether"), map.getOrDefault("end", "minecraft:the_end"));
        this.cache1_17Light = this.getBoolean("cache-1_17-light", false);
        this.chatTypeFormats = this.get("chat-types-1_19", Map.class, new HashMap());
    }

    private BlockedProtocolVersions loadBlockedProtocolVersions() {
        List<Integer> list = this.getListSafe("block-protocols", Integer.class, "Invalid blocked version protocol found in config: '%s'");
        List<String> list2 = this.getListSafe("block-versions", String.class, "Invalid blocked version found in config: '%s'");
        IntOpenHashSet intOpenHashSet = new IntOpenHashSet(list);
        int n = -1;
        int n2 = -1;
        for (String string : list2) {
            ProtocolVersion protocolVersion;
            if (string.isEmpty()) continue;
            char c = string.charAt(0);
            if (c == '<' || c == '>') {
                protocolVersion = this.protocolVersion(string.substring(1));
                if (protocolVersion == null) continue;
                if (c == '<') {
                    if (n != -1) {
                        Via.getPlatform().getLogger().warning("Already set lower bound " + n + " overridden by " + protocolVersion.getName());
                    }
                    n = protocolVersion.getVersion();
                    continue;
                }
                if (n2 != -1) {
                    Via.getPlatform().getLogger().warning("Already set upper bound " + n2 + " overridden by " + protocolVersion.getName());
                }
                n2 = protocolVersion.getVersion();
                continue;
            }
            protocolVersion = this.protocolVersion(string);
            if (protocolVersion == null || intOpenHashSet.add(protocolVersion.getVersion())) continue;
            Via.getPlatform().getLogger().warning("Duplicated blocked protocol version " + protocolVersion.getName() + "/" + protocolVersion.getVersion());
        }
        if (n != -1 || n2 != -1) {
            int n3 = n;
            int n4 = n2;
            intOpenHashSet.removeIf(arg_0 -> AbstractViaConfig.lambda$loadBlockedProtocolVersions$0(n3, n4, arg_0));
        }
        return new BlockedProtocolVersionsImpl(intOpenHashSet, n, n2);
    }

    private @Nullable ProtocolVersion protocolVersion(String string) {
        ProtocolVersion protocolVersion = ProtocolVersion.getClosest(string);
        if (protocolVersion == null) {
            Via.getPlatform().getLogger().warning("Unknown protocol version in block-versions: " + string);
            return null;
        }
        return protocolVersion;
    }

    @Override
    public boolean isCheckForUpdates() {
        return this.checkForUpdates;
    }

    @Override
    public void setCheckForUpdates(boolean bl) {
        this.checkForUpdates = bl;
        this.set("checkforupdates", bl);
    }

    @Override
    public boolean isPreventCollision() {
        return this.preventCollision;
    }

    @Override
    public boolean isNewEffectIndicator() {
        return this.useNewEffectIndicator;
    }

    @Override
    public boolean isShowNewDeathMessages() {
        return this.useNewDeathmessages;
    }

    @Override
    public boolean isSuppressMetadataErrors() {
        return this.suppressMetadataErrors;
    }

    @Override
    public boolean isShieldBlocking() {
        return this.shieldBlocking;
    }

    @Override
    public boolean isNoDelayShieldBlocking() {
        return this.noDelayShieldBlocking;
    }

    @Override
    public boolean isShowShieldWhenSwordInHand() {
        return this.showShieldWhenSwordInHand;
    }

    @Override
    public boolean isHologramPatch() {
        return this.hologramPatch;
    }

    @Override
    public boolean isPistonAnimationPatch() {
        return this.pistonAnimationPatch;
    }

    @Override
    public boolean isBossbarPatch() {
        return this.bossbarPatch;
    }

    @Override
    public boolean isBossbarAntiflicker() {
        return this.bossbarAntiFlicker;
    }

    @Override
    public double getHologramYOffset() {
        return this.hologramOffset;
    }

    @Override
    public int getMaxPPS() {
        return this.maxPPS;
    }

    @Override
    public String getMaxPPSKickMessage() {
        return this.maxPPSKickMessage;
    }

    @Override
    public int getTrackingPeriod() {
        return this.trackingPeriod;
    }

    @Override
    public int getWarningPPS() {
        return this.warningPPS;
    }

    @Override
    public int getMaxWarnings() {
        return this.maxPPSWarnings;
    }

    @Override
    public String getMaxWarningsKickMessage() {
        return this.maxPPSWarningsKickMessage;
    }

    @Override
    public boolean isSendSupportedVersions() {
        return this.sendSupportedVersions;
    }

    @Override
    public boolean isSimulatePlayerTick() {
        return this.simulatePlayerTick;
    }

    @Override
    public boolean isItemCache() {
        return this.itemCache;
    }

    @Override
    public boolean isNMSPlayerTicking() {
        return this.nmsPlayerTicking;
    }

    @Override
    public boolean isReplacePistons() {
        return this.replacePistons;
    }

    @Override
    public int getPistonReplacementId() {
        return this.pistonReplacementId;
    }

    @Override
    public boolean isChunkBorderFix() {
        return this.chunkBorderFix;
    }

    @Override
    public boolean isAutoTeam() {
        return this.preventCollision && this.autoTeam;
    }

    @Override
    public boolean isForceJsonTransform() {
        return this.forceJsonTransform;
    }

    @Override
    public boolean is1_12NBTArrayFix() {
        return this.nbtArrayFix;
    }

    @Override
    public boolean shouldRegisterUserConnectionOnJoin() {
        return true;
    }

    @Override
    public boolean is1_12QuickMoveActionFix() {
        return true;
    }

    @Override
    public BlockedProtocolVersions blockedProtocolVersions() {
        return this.blockedProtocolVersions;
    }

    @Override
    public String getBlockedDisconnectMsg() {
        return this.blockedDisconnectMessage;
    }

    @Override
    public String getReloadDisconnectMsg() {
        return this.reloadDisconnectMessage;
    }

    @Override
    public boolean isMinimizeCooldown() {
        return this.minimizeCooldown;
    }

    @Override
    public boolean is1_13TeamColourFix() {
        return this.teamColourFix;
    }

    @Override
    public boolean isSuppressConversionWarnings() {
        return this.suppressConversionWarnings;
    }

    @Override
    public boolean isDisable1_13AutoComplete() {
        return this.disable1_13TabComplete;
    }

    @Override
    public boolean isServersideBlockConnections() {
        return this.serversideBlockConnections;
    }

    @Override
    public String getBlockConnectionMethod() {
        return "packet";
    }

    @Override
    public boolean isReduceBlockStorageMemory() {
        return this.reduceBlockStorageMemory;
    }

    @Override
    public boolean isStemWhenBlockAbove() {
        return this.flowerStemWhenBlockAbove;
    }

    @Override
    public boolean isVineClimbFix() {
        return this.vineClimbFix;
    }

    @Override
    public boolean isSnowCollisionFix() {
        return this.snowCollisionFix;
    }

    @Override
    public boolean isInfestedBlocksFix() {
        return this.infestedBlocksFix;
    }

    @Override
    public int get1_13TabCompleteDelay() {
        return this.tabCompleteDelay;
    }

    @Override
    public boolean isTruncate1_14Books() {
        return this.truncate1_14Books;
    }

    @Override
    public boolean isLeftHandedHandling() {
        return this.leftHandedHandling;
    }

    @Override
    public boolean is1_9HitboxFix() {
        return true;
    }

    @Override
    public boolean is1_14HitboxFix() {
        return true;
    }

    @Override
    public boolean isNonFullBlockLightFix() {
        return this.fullBlockLightFix;
    }

    @Override
    public boolean is1_14HealthNaNFix() {
        return this.healthNaNFix;
    }

    @Override
    public boolean is1_15InstantRespawn() {
        return this.instantRespawn;
    }

    @Override
    public boolean isIgnoreLong1_16ChannelNames() {
        return this.ignoreLongChannelNames;
    }

    @Override
    public boolean isForcedUse1_17ResourcePack() {
        return this.forcedUse1_17ResourcePack;
    }

    @Override
    public JsonElement get1_17ResourcePackPrompt() {
        return this.resourcePack1_17PromptMessage;
    }

    @Override
    public WorldIdentifiers get1_16WorldNamesMap() {
        return this.map1_16WorldNames;
    }

    @Override
    public boolean cache1_17Light() {
        return this.cache1_17Light;
    }

    @Override
    public @Nullable String chatTypeFormat(String string) {
        return this.chatTypeFormats.get(string);
    }

    @Override
    public boolean isArmorToggleFix() {
        return true;
    }

    private static boolean lambda$loadBlockedProtocolVersions$0(int n, int n2, int n3) {
        if (n != -1 && n3 < n || n2 != -1 && n3 > n2) {
            ProtocolVersion protocolVersion = ProtocolVersion.getProtocol(n3);
            Via.getPlatform().getLogger().warning("Blocked protocol version " + protocolVersion.getName() + "/" + protocolVersion.getVersion() + " already covered by upper or lower bound");
            return false;
        }
        return true;
    }
}

