/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.management;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Dynamic;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.network.play.server.SCustomPayloadPlayPacket;
import net.minecraft.network.play.server.SEntityStatusPacket;
import net.minecraft.network.play.server.SHeldItemChangePacket;
import net.minecraft.network.play.server.SJoinGamePacket;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.network.play.server.SPlayerAbilitiesPacket;
import net.minecraft.network.play.server.SPlayerListItemPacket;
import net.minecraft.network.play.server.SRespawnPacket;
import net.minecraft.network.play.server.SServerDifficultyPacket;
import net.minecraft.network.play.server.SSetExperiencePacket;
import net.minecraft.network.play.server.STagsListPacket;
import net.minecraft.network.play.server.STeamsPacket;
import net.minecraft.network.play.server.SUpdateRecipesPacket;
import net.minecraft.network.play.server.SUpdateTimePacket;
import net.minecraft.network.play.server.SUpdateViewDistancePacket;
import net.minecraft.network.play.server.SWorldBorderPacket;
import net.minecraft.network.play.server.SWorldSpawnChangedPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.BanList;
import net.minecraft.server.management.DemoPlayerInteractionManager;
import net.minecraft.server.management.IPBanEntry;
import net.minecraft.server.management.IPBanList;
import net.minecraft.server.management.OpEntry;
import net.minecraft.server.management.OpList;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.server.management.ProfileBanEntry;
import net.minecraft.server.management.WhiteList;
import net.minecraft.stats.ServerStatisticsManager;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DimensionType;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.border.IBorderListener;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.FolderName;
import net.minecraft.world.storage.IWorldInfo;
import net.minecraft.world.storage.PlayerData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class PlayerList {
    public static final File FILE_PLAYERBANS = new File("banned-players.json");
    public static final File FILE_IPBANS = new File("banned-ips.json");
    public static final File FILE_OPS = new File("ops.json");
    public static final File FILE_WHITELIST = new File("whitelist.json");
    private static final Logger LOGGER = LogManager.getLogger();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    private final MinecraftServer server;
    private final List<ServerPlayerEntity> players = Lists.newArrayList();
    private final Map<UUID, ServerPlayerEntity> uuidToPlayerMap = Maps.newHashMap();
    private final BanList bannedPlayers = new BanList(FILE_PLAYERBANS);
    private final IPBanList bannedIPs = new IPBanList(FILE_IPBANS);
    private final OpList ops = new OpList(FILE_OPS);
    private final WhiteList whiteListedPlayers = new WhiteList(FILE_WHITELIST);
    private final Map<UUID, ServerStatisticsManager> playerStatFiles = Maps.newHashMap();
    private final Map<UUID, PlayerAdvancements> advancements = Maps.newHashMap();
    private final PlayerData playerDataManager;
    private boolean whiteListEnforced;
    private final DynamicRegistries.Impl field_232639_s_;
    protected final int maxPlayers;
    private int viewDistance;
    private GameType gameType;
    private boolean commandsAllowedForAll;
    private int playerPingIndex;

    public PlayerList(MinecraftServer minecraftServer, DynamicRegistries.Impl impl, PlayerData playerData, int n) {
        this.server = minecraftServer;
        this.field_232639_s_ = impl;
        this.maxPlayers = n;
        this.playerDataManager = playerData;
    }

    public void initializeConnectionToPlayer(NetworkManager networkManager, ServerPlayerEntity serverPlayerEntity) {
        Object object;
        ServerWorld serverWorld;
        GameProfile gameProfile = serverPlayerEntity.getGameProfile();
        PlayerProfileCache playerProfileCache = this.server.getPlayerProfileCache();
        GameProfile gameProfile2 = playerProfileCache.getProfileByUUID(gameProfile.getId());
        String string = gameProfile2 == null ? gameProfile.getName() : gameProfile2.getName();
        playerProfileCache.addEntry(gameProfile);
        CompoundNBT compoundNBT = this.readPlayerDataFromFile(serverPlayerEntity);
        RegistryKey<World> registryKey = compoundNBT != null ? DimensionType.decodeWorldKey(new Dynamic<INBT>(NBTDynamicOps.INSTANCE, compoundNBT.get("Dimension"))).resultOrPartial(LOGGER::error).orElse(World.OVERWORLD) : World.OVERWORLD;
        ServerWorld serverWorld2 = this.server.getWorld(registryKey);
        if (serverWorld2 == null) {
            LOGGER.warn("Unknown respawn dimension {}, defaulting to overworld", (Object)registryKey);
            serverWorld = this.server.func_241755_D_();
        } else {
            serverWorld = serverWorld2;
        }
        serverPlayerEntity.setWorld(serverWorld);
        serverPlayerEntity.interactionManager.setWorld((ServerWorld)serverPlayerEntity.world);
        String string2 = "local";
        if (networkManager.getRemoteAddress() != null) {
            string2 = networkManager.getRemoteAddress().toString();
        }
        LOGGER.info("{}[{}] logged in with entity id {} at ({}, {}, {})", (Object)serverPlayerEntity.getName().getString(), (Object)string2, (Object)serverPlayerEntity.getEntityId(), (Object)serverPlayerEntity.getPosX(), (Object)serverPlayerEntity.getPosY(), (Object)serverPlayerEntity.getPosZ());
        IWorldInfo iWorldInfo = serverWorld.getWorldInfo();
        this.setPlayerGameTypeBasedOnOther(serverPlayerEntity, null, serverWorld);
        ServerPlayNetHandler serverPlayNetHandler = new ServerPlayNetHandler(this.server, networkManager, serverPlayerEntity);
        GameRules gameRules = serverWorld.getGameRules();
        boolean bl = gameRules.getBoolean(GameRules.DO_IMMEDIATE_RESPAWN);
        boolean bl2 = gameRules.getBoolean(GameRules.REDUCED_DEBUG_INFO);
        serverPlayNetHandler.sendPacket(new SJoinGamePacket(serverPlayerEntity.getEntityId(), serverPlayerEntity.interactionManager.getGameType(), serverPlayerEntity.interactionManager.func_241815_c_(), BiomeManager.getHashedSeed(serverWorld.getSeed()), iWorldInfo.isHardcore(), this.server.func_240770_D_(), this.field_232639_s_, serverWorld.getDimensionType(), serverWorld.getDimensionKey(), this.getMaxPlayers(), this.viewDistance, bl2, !bl, serverWorld.isDebug(), serverWorld.func_241109_A_()));
        serverPlayNetHandler.sendPacket(new SCustomPayloadPlayPacket(SCustomPayloadPlayPacket.BRAND, new PacketBuffer(Unpooled.buffer()).writeString(this.getServer().getServerModName())));
        serverPlayNetHandler.sendPacket(new SServerDifficultyPacket(iWorldInfo.getDifficulty(), iWorldInfo.isDifficultyLocked()));
        serverPlayNetHandler.sendPacket(new SPlayerAbilitiesPacket(serverPlayerEntity.abilities));
        serverPlayNetHandler.sendPacket(new SHeldItemChangePacket(serverPlayerEntity.inventory.currentItem));
        serverPlayNetHandler.sendPacket(new SUpdateRecipesPacket(this.server.getRecipeManager().getRecipes()));
        serverPlayNetHandler.sendPacket(new STagsListPacket(this.server.func_244266_aF()));
        this.updatePermissionLevel(serverPlayerEntity);
        serverPlayerEntity.getStats().markAllDirty();
        serverPlayerEntity.getRecipeBook().init(serverPlayerEntity);
        this.sendScoreboard(serverWorld.getScoreboard(), serverPlayerEntity);
        this.server.refreshStatusNextTick();
        TranslationTextComponent translationTextComponent = serverPlayerEntity.getGameProfile().getName().equalsIgnoreCase(string) ? new TranslationTextComponent("multiplayer.player.joined", serverPlayerEntity.getDisplayName()) : new TranslationTextComponent("multiplayer.player.joined.renamed", serverPlayerEntity.getDisplayName(), string);
        this.func_232641_a_(translationTextComponent.mergeStyle(TextFormatting.YELLOW), ChatType.SYSTEM, Util.DUMMY_UUID);
        serverPlayNetHandler.setPlayerLocation(serverPlayerEntity.getPosX(), serverPlayerEntity.getPosY(), serverPlayerEntity.getPosZ(), serverPlayerEntity.rotationYaw, serverPlayerEntity.rotationPitch);
        this.players.add(serverPlayerEntity);
        this.uuidToPlayerMap.put(serverPlayerEntity.getUniqueID(), serverPlayerEntity);
        this.sendPacketToAllPlayers(new SPlayerListItemPacket(SPlayerListItemPacket.Action.ADD_PLAYER, serverPlayerEntity));
        for (int i = 0; i < this.players.size(); ++i) {
            serverPlayerEntity.connection.sendPacket(new SPlayerListItemPacket(SPlayerListItemPacket.Action.ADD_PLAYER, this.players.get(i)));
        }
        serverWorld.addNewPlayer(serverPlayerEntity);
        this.server.getCustomBossEvents().onPlayerLogin(serverPlayerEntity);
        this.sendWorldInfo(serverPlayerEntity, serverWorld);
        if (!this.server.getResourcePackUrl().isEmpty()) {
            serverPlayerEntity.loadResourcePack(this.server.getResourcePackUrl(), this.server.getResourcePackHash());
        }
        Object object2 = serverPlayerEntity.getActivePotionEffects().iterator();
        while (object2.hasNext()) {
            object = object2.next();
            serverPlayNetHandler.sendPacket(new SPlayEntityEffectPacket(serverPlayerEntity.getEntityId(), (EffectInstance)object));
        }
        if (compoundNBT != null && compoundNBT.contains("RootVehicle", 1) && (object = EntityType.loadEntityAndExecute(((CompoundNBT)(object2 = compoundNBT.getCompound("RootVehicle"))).getCompound("Entity"), serverWorld, arg_0 -> PlayerList.lambda$initializeConnectionToPlayer$0(serverWorld, arg_0))) != null) {
            UUID uUID = ((CompoundNBT)object2).hasUniqueId("Attach") ? ((CompoundNBT)object2).getUniqueId("Attach") : null;
            if (((Entity)object).getUniqueID().equals(uUID)) {
                serverPlayerEntity.startRiding((Entity)object, false);
            } else {
                for (Entity entity2 : ((Entity)object).getRecursivePassengers()) {
                    if (!entity2.getUniqueID().equals(uUID)) continue;
                    serverPlayerEntity.startRiding(entity2, false);
                    break;
                }
            }
            if (!serverPlayerEntity.isPassenger()) {
                LOGGER.warn("Couldn't reattach entity to player");
                serverWorld.removeEntity((Entity)object);
                for (Entity entity2 : ((Entity)object).getRecursivePassengers()) {
                    serverWorld.removeEntity(entity2);
                }
            }
        }
        serverPlayerEntity.addSelfToInternalCraftingInventory();
    }

    protected void sendScoreboard(ServerScoreboard serverScoreboard, ServerPlayerEntity serverPlayerEntity) {
        HashSet<ScoreObjective> hashSet = Sets.newHashSet();
        for (ScorePlayerTeam object : serverScoreboard.getTeams()) {
            serverPlayerEntity.connection.sendPacket(new STeamsPacket(object, 0));
        }
        for (int i = 0; i < 19; ++i) {
            ScoreObjective scoreObjective = serverScoreboard.getObjectiveInDisplaySlot(i);
            if (scoreObjective == null || hashSet.contains(scoreObjective)) continue;
            for (IPacket<?> iPacket : serverScoreboard.getCreatePackets(scoreObjective)) {
                serverPlayerEntity.connection.sendPacket(iPacket);
            }
            hashSet.add(scoreObjective);
        }
    }

    public void func_212504_a(ServerWorld serverWorld) {
        serverWorld.getWorldBorder().addListener(new IBorderListener(this){
            final PlayerList this$0;
            {
                this.this$0 = playerList;
            }

            @Override
            public void onSizeChanged(WorldBorder worldBorder, double d) {
                this.this$0.sendPacketToAllPlayers(new SWorldBorderPacket(worldBorder, SWorldBorderPacket.Action.SET_SIZE));
            }

            @Override
            public void onTransitionStarted(WorldBorder worldBorder, double d, double d2, long l) {
                this.this$0.sendPacketToAllPlayers(new SWorldBorderPacket(worldBorder, SWorldBorderPacket.Action.LERP_SIZE));
            }

            @Override
            public void onCenterChanged(WorldBorder worldBorder, double d, double d2) {
                this.this$0.sendPacketToAllPlayers(new SWorldBorderPacket(worldBorder, SWorldBorderPacket.Action.SET_CENTER));
            }

            @Override
            public void onWarningTimeChanged(WorldBorder worldBorder, int n) {
                this.this$0.sendPacketToAllPlayers(new SWorldBorderPacket(worldBorder, SWorldBorderPacket.Action.SET_WARNING_TIME));
            }

            @Override
            public void onWarningDistanceChanged(WorldBorder worldBorder, int n) {
                this.this$0.sendPacketToAllPlayers(new SWorldBorderPacket(worldBorder, SWorldBorderPacket.Action.SET_WARNING_BLOCKS));
            }

            @Override
            public void onDamageAmountChanged(WorldBorder worldBorder, double d) {
            }

            @Override
            public void onDamageBufferChanged(WorldBorder worldBorder, double d) {
            }
        });
    }

    @Nullable
    public CompoundNBT readPlayerDataFromFile(ServerPlayerEntity serverPlayerEntity) {
        CompoundNBT compoundNBT;
        CompoundNBT compoundNBT2 = this.server.func_240793_aU_().getHostPlayerNBT();
        if (serverPlayerEntity.getName().getString().equals(this.server.getServerOwner()) && compoundNBT2 != null) {
            compoundNBT = compoundNBT2;
            serverPlayerEntity.read(compoundNBT2);
            LOGGER.debug("loading single player");
        } else {
            compoundNBT = this.playerDataManager.loadPlayerData(serverPlayerEntity);
        }
        return compoundNBT;
    }

    protected void writePlayerData(ServerPlayerEntity serverPlayerEntity) {
        PlayerAdvancements playerAdvancements;
        this.playerDataManager.savePlayerData(serverPlayerEntity);
        ServerStatisticsManager serverStatisticsManager = this.playerStatFiles.get(serverPlayerEntity.getUniqueID());
        if (serverStatisticsManager != null) {
            serverStatisticsManager.saveStatFile();
        }
        if ((playerAdvancements = this.advancements.get(serverPlayerEntity.getUniqueID())) != null) {
            playerAdvancements.save();
        }
    }

    public void playerLoggedOut(ServerPlayerEntity serverPlayerEntity) {
        Object object;
        ServerWorld serverWorld = serverPlayerEntity.getServerWorld();
        serverPlayerEntity.addStat(Stats.LEAVE_GAME);
        this.writePlayerData(serverPlayerEntity);
        if (serverPlayerEntity.isPassenger() && ((Entity)(object = serverPlayerEntity.getLowestRidingEntity())).isOnePlayerRiding()) {
            LOGGER.debug("Removing player mount");
            serverPlayerEntity.stopRiding();
            serverWorld.removeEntity((Entity)object);
            ((Entity)object).removed = true;
            for (Entity entity2 : ((Entity)object).getRecursivePassengers()) {
                serverWorld.removeEntity(entity2);
                entity2.removed = true;
            }
            serverWorld.getChunk(serverPlayerEntity.chunkCoordX, serverPlayerEntity.chunkCoordZ).markDirty();
        }
        serverPlayerEntity.detach();
        serverWorld.removePlayer(serverPlayerEntity);
        serverPlayerEntity.getAdvancements().dispose();
        this.players.remove(serverPlayerEntity);
        this.server.getCustomBossEvents().onPlayerLogout(serverPlayerEntity);
        object = serverPlayerEntity.getUniqueID();
        ServerPlayerEntity serverPlayerEntity2 = this.uuidToPlayerMap.get(object);
        if (serverPlayerEntity2 == serverPlayerEntity) {
            this.uuidToPlayerMap.remove(object);
            this.playerStatFiles.remove(object);
            this.advancements.remove(object);
        }
        this.sendPacketToAllPlayers(new SPlayerListItemPacket(SPlayerListItemPacket.Action.REMOVE_PLAYER, serverPlayerEntity));
    }

    @Nullable
    public ITextComponent canPlayerLogin(SocketAddress socketAddress, GameProfile gameProfile) {
        if (this.bannedPlayers.isBanned(gameProfile)) {
            ProfileBanEntry profileBanEntry = (ProfileBanEntry)this.bannedPlayers.getEntry(gameProfile);
            TranslationTextComponent translationTextComponent = new TranslationTextComponent("multiplayer.disconnect.banned.reason", profileBanEntry.getBanReason());
            if (profileBanEntry.getBanEndDate() != null) {
                translationTextComponent.append(new TranslationTextComponent("multiplayer.disconnect.banned.expiration", DATE_FORMAT.format(profileBanEntry.getBanEndDate())));
            }
            return translationTextComponent;
        }
        if (!this.canJoin(gameProfile)) {
            return new TranslationTextComponent("multiplayer.disconnect.not_whitelisted");
        }
        if (this.bannedIPs.isBanned(socketAddress)) {
            IPBanEntry iPBanEntry = this.bannedIPs.getBanEntry(socketAddress);
            TranslationTextComponent translationTextComponent = new TranslationTextComponent("multiplayer.disconnect.banned_ip.reason", iPBanEntry.getBanReason());
            if (iPBanEntry.getBanEndDate() != null) {
                translationTextComponent.append(new TranslationTextComponent("multiplayer.disconnect.banned_ip.expiration", DATE_FORMAT.format(iPBanEntry.getBanEndDate())));
            }
            return translationTextComponent;
        }
        return this.players.size() >= this.maxPlayers && !this.bypassesPlayerLimit(gameProfile) ? new TranslationTextComponent("multiplayer.disconnect.server_full") : null;
    }

    public ServerPlayerEntity createPlayerForUser(GameProfile gameProfile) {
        Object object3;
        Object object2;
        UUID uUID = PlayerEntity.getUUID(gameProfile);
        ArrayList<Object> arrayList = Lists.newArrayList();
        for (int i = 0; i < this.players.size(); ++i) {
            object2 = this.players.get(i);
            if (!((Entity)object2).getUniqueID().equals(uUID)) continue;
            arrayList.add(object2);
        }
        ServerPlayerEntity serverPlayerEntity = this.uuidToPlayerMap.get(gameProfile.getId());
        if (serverPlayerEntity != null && !arrayList.contains(serverPlayerEntity)) {
            arrayList.add(serverPlayerEntity);
        }
        for (Object object3 : arrayList) {
            ((ServerPlayerEntity)object3).connection.disconnect(new TranslationTextComponent("multiplayer.disconnect.duplicate_login"));
        }
        object2 = this.server.func_241755_D_();
        object3 = this.server.isDemo() ? new DemoPlayerInteractionManager((ServerWorld)object2) : new PlayerInteractionManager((ServerWorld)object2);
        return new ServerPlayerEntity(this.server, (ServerWorld)object2, gameProfile, (PlayerInteractionManager)object3);
    }

    public ServerPlayerEntity func_232644_a_(ServerPlayerEntity serverPlayerEntity, boolean bl) {
        this.players.remove(serverPlayerEntity);
        serverPlayerEntity.getServerWorld().removePlayer(serverPlayerEntity);
        BlockPos blockPos = serverPlayerEntity.func_241140_K_();
        float f = serverPlayerEntity.func_242109_L();
        boolean bl2 = serverPlayerEntity.func_241142_M_();
        ServerWorld serverWorld = this.server.getWorld(serverPlayerEntity.func_241141_L_());
        Optional<Object> optional = serverWorld != null && blockPos != null ? PlayerEntity.func_242374_a(serverWorld, blockPos, f, bl2, bl) : Optional.empty();
        ServerWorld serverWorld2 = serverWorld != null && optional.isPresent() ? serverWorld : this.server.func_241755_D_();
        PlayerInteractionManager playerInteractionManager = this.server.isDemo() ? new DemoPlayerInteractionManager(serverWorld2) : new PlayerInteractionManager(serverWorld2);
        ServerPlayerEntity serverPlayerEntity2 = new ServerPlayerEntity(this.server, serverWorld2, serverPlayerEntity.getGameProfile(), playerInteractionManager);
        serverPlayerEntity2.connection = serverPlayerEntity.connection;
        serverPlayerEntity2.copyFrom(serverPlayerEntity, bl);
        serverPlayerEntity2.setEntityId(serverPlayerEntity.getEntityId());
        serverPlayerEntity2.setPrimaryHand(serverPlayerEntity.getPrimaryHand());
        for (String object2 : serverPlayerEntity.getTags()) {
            serverPlayerEntity2.addTag(object2);
        }
        this.setPlayerGameTypeBasedOnOther(serverPlayerEntity2, serverPlayerEntity, serverWorld2);
        boolean bl3 = false;
        if (optional.isPresent()) {
            float f2;
            BlockState blockState = serverWorld2.getBlockState(blockPos);
            boolean bl4 = blockState.isIn(Blocks.RESPAWN_ANCHOR);
            Vector3d vector3d = (Vector3d)optional.get();
            if (!blockState.isIn(BlockTags.BEDS) && !bl4) {
                f2 = f;
            } else {
                Vector3d vector3d2 = Vector3d.copyCenteredHorizontally(blockPos).subtract(vector3d).normalize();
                f2 = (float)MathHelper.wrapDegrees(MathHelper.atan2(vector3d2.z, vector3d2.x) * 57.2957763671875 - 90.0);
            }
            serverPlayerEntity2.setLocationAndAngles(vector3d.x, vector3d.y, vector3d.z, f2, 0.0f);
            serverPlayerEntity2.func_242111_a(serverWorld2.getDimensionKey(), blockPos, f, bl2, true);
            bl3 = !bl && bl4;
        } else if (blockPos != null) {
            serverPlayerEntity2.connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241764_a_, 0.0f));
        }
        while (!serverWorld2.hasNoCollisions(serverPlayerEntity2) && serverPlayerEntity2.getPosY() < 256.0) {
            serverPlayerEntity2.setPosition(serverPlayerEntity2.getPosX(), serverPlayerEntity2.getPosY() + 1.0, serverPlayerEntity2.getPosZ());
        }
        IWorldInfo iWorldInfo = serverPlayerEntity2.world.getWorldInfo();
        serverPlayerEntity2.connection.sendPacket(new SRespawnPacket(serverPlayerEntity2.world.getDimensionType(), serverPlayerEntity2.world.getDimensionKey(), BiomeManager.getHashedSeed(serverPlayerEntity2.getServerWorld().getSeed()), serverPlayerEntity2.interactionManager.getGameType(), serverPlayerEntity2.interactionManager.func_241815_c_(), serverPlayerEntity2.getServerWorld().isDebug(), serverPlayerEntity2.getServerWorld().func_241109_A_(), bl));
        serverPlayerEntity2.connection.setPlayerLocation(serverPlayerEntity2.getPosX(), serverPlayerEntity2.getPosY(), serverPlayerEntity2.getPosZ(), serverPlayerEntity2.rotationYaw, serverPlayerEntity2.rotationPitch);
        serverPlayerEntity2.connection.sendPacket(new SWorldSpawnChangedPacket(serverWorld2.getSpawnPoint(), serverWorld2.func_242107_v()));
        serverPlayerEntity2.connection.sendPacket(new SServerDifficultyPacket(iWorldInfo.getDifficulty(), iWorldInfo.isDifficultyLocked()));
        serverPlayerEntity2.connection.sendPacket(new SSetExperiencePacket(serverPlayerEntity2.experience, serverPlayerEntity2.experienceTotal, serverPlayerEntity2.experienceLevel));
        this.sendWorldInfo(serverPlayerEntity2, serverWorld2);
        this.updatePermissionLevel(serverPlayerEntity2);
        serverWorld2.addRespawnedPlayer(serverPlayerEntity2);
        this.players.add(serverPlayerEntity2);
        this.uuidToPlayerMap.put(serverPlayerEntity2.getUniqueID(), serverPlayerEntity2);
        serverPlayerEntity2.addSelfToInternalCraftingInventory();
        serverPlayerEntity2.setHealth(serverPlayerEntity2.getHealth());
        if (bl3) {
            serverPlayerEntity2.connection.sendPacket(new SPlaySoundEffectPacket(SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE, SoundCategory.BLOCKS, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1.0f, 1.0f));
        }
        return serverPlayerEntity2;
    }

    public void updatePermissionLevel(ServerPlayerEntity serverPlayerEntity) {
        GameProfile gameProfile = serverPlayerEntity.getGameProfile();
        int n = this.server.getPermissionLevel(gameProfile);
        this.sendPlayerPermissionLevel(serverPlayerEntity, n);
    }

    public void tick() {
        if (++this.playerPingIndex > 600) {
            this.sendPacketToAllPlayers(new SPlayerListItemPacket(SPlayerListItemPacket.Action.UPDATE_LATENCY, this.players));
            this.playerPingIndex = 0;
        }
    }

    public void sendPacketToAllPlayers(IPacket<?> iPacket) {
        for (int i = 0; i < this.players.size(); ++i) {
            this.players.get((int)i).connection.sendPacket(iPacket);
        }
    }

    public void func_232642_a_(IPacket<?> iPacket, RegistryKey<World> registryKey) {
        for (int i = 0; i < this.players.size(); ++i) {
            ServerPlayerEntity serverPlayerEntity = this.players.get(i);
            if (serverPlayerEntity.world.getDimensionKey() != registryKey) continue;
            serverPlayerEntity.connection.sendPacket(iPacket);
        }
    }

    public void sendMessageToAllTeamMembers(PlayerEntity playerEntity, ITextComponent iTextComponent) {
        Team team = playerEntity.getTeam();
        if (team != null) {
            for (String string : team.getMembershipCollection()) {
                ServerPlayerEntity serverPlayerEntity = this.getPlayerByUsername(string);
                if (serverPlayerEntity == null || serverPlayerEntity == playerEntity) continue;
                serverPlayerEntity.sendMessage(iTextComponent, playerEntity.getUniqueID());
            }
        }
    }

    public void sendMessageToTeamOrAllPlayers(PlayerEntity playerEntity, ITextComponent iTextComponent) {
        Team team = playerEntity.getTeam();
        if (team == null) {
            this.func_232641_a_(iTextComponent, ChatType.SYSTEM, playerEntity.getUniqueID());
        } else {
            for (int i = 0; i < this.players.size(); ++i) {
                ServerPlayerEntity serverPlayerEntity = this.players.get(i);
                if (serverPlayerEntity.getTeam() == team) continue;
                serverPlayerEntity.sendMessage(iTextComponent, playerEntity.getUniqueID());
            }
        }
    }

    public String[] getOnlinePlayerNames() {
        String[] stringArray = new String[this.players.size()];
        for (int i = 0; i < this.players.size(); ++i) {
            stringArray[i] = this.players.get(i).getGameProfile().getName();
        }
        return stringArray;
    }

    public BanList getBannedPlayers() {
        return this.bannedPlayers;
    }

    public IPBanList getBannedIPs() {
        return this.bannedIPs;
    }

    public void addOp(GameProfile gameProfile) {
        this.ops.addEntry(new OpEntry(gameProfile, this.server.getOpPermissionLevel(), this.ops.bypassesPlayerLimit(gameProfile)));
        ServerPlayerEntity serverPlayerEntity = this.getPlayerByUUID(gameProfile.getId());
        if (serverPlayerEntity != null) {
            this.updatePermissionLevel(serverPlayerEntity);
        }
    }

    public void removeOp(GameProfile gameProfile) {
        this.ops.removeEntry(gameProfile);
        ServerPlayerEntity serverPlayerEntity = this.getPlayerByUUID(gameProfile.getId());
        if (serverPlayerEntity != null) {
            this.updatePermissionLevel(serverPlayerEntity);
        }
    }

    private void sendPlayerPermissionLevel(ServerPlayerEntity serverPlayerEntity, int n) {
        if (serverPlayerEntity.connection != null) {
            byte by = n <= 0 ? (byte)24 : (n >= 4 ? (byte)28 : (byte)((byte)(24 + n)));
            serverPlayerEntity.connection.sendPacket(new SEntityStatusPacket(serverPlayerEntity, by));
        }
        this.server.getCommandManager().send(serverPlayerEntity);
    }

    public boolean canJoin(GameProfile gameProfile) {
        return !this.whiteListEnforced || this.ops.hasEntry(gameProfile) || this.whiteListedPlayers.hasEntry(gameProfile);
    }

    public boolean canSendCommands(GameProfile gameProfile) {
        return this.ops.hasEntry(gameProfile) || this.server.isServerOwner(gameProfile) && this.server.func_240793_aU_().areCommandsAllowed() || this.commandsAllowedForAll;
    }

    @Nullable
    public ServerPlayerEntity getPlayerByUsername(String string) {
        for (ServerPlayerEntity serverPlayerEntity : this.players) {
            if (!serverPlayerEntity.getGameProfile().getName().equalsIgnoreCase(string)) continue;
            return serverPlayerEntity;
        }
        return null;
    }

    public void sendToAllNearExcept(@Nullable PlayerEntity playerEntity, double d, double d2, double d3, double d4, RegistryKey<World> registryKey, IPacket<?> iPacket) {
        for (int i = 0; i < this.players.size(); ++i) {
            double d5;
            double d6;
            double d7;
            ServerPlayerEntity serverPlayerEntity = this.players.get(i);
            if (serverPlayerEntity == playerEntity || serverPlayerEntity.world.getDimensionKey() != registryKey || !((d7 = d - serverPlayerEntity.getPosX()) * d7 + (d6 = d2 - serverPlayerEntity.getPosY()) * d6 + (d5 = d3 - serverPlayerEntity.getPosZ()) * d5 < d4 * d4)) continue;
            serverPlayerEntity.connection.sendPacket(iPacket);
        }
    }

    public void saveAllPlayerData() {
        for (int i = 0; i < this.players.size(); ++i) {
            this.writePlayerData(this.players.get(i));
        }
    }

    public WhiteList getWhitelistedPlayers() {
        return this.whiteListedPlayers;
    }

    public String[] getWhitelistedPlayerNames() {
        return this.whiteListedPlayers.getKeys();
    }

    public OpList getOppedPlayers() {
        return this.ops;
    }

    public String[] getOppedPlayerNames() {
        return this.ops.getKeys();
    }

    public void reloadWhitelist() {
    }

    public void sendWorldInfo(ServerPlayerEntity serverPlayerEntity, ServerWorld serverWorld) {
        WorldBorder worldBorder = this.server.func_241755_D_().getWorldBorder();
        serverPlayerEntity.connection.sendPacket(new SWorldBorderPacket(worldBorder, SWorldBorderPacket.Action.INITIALIZE));
        serverPlayerEntity.connection.sendPacket(new SUpdateTimePacket(serverWorld.getGameTime(), serverWorld.getDayTime(), serverWorld.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)));
        serverPlayerEntity.connection.sendPacket(new SWorldSpawnChangedPacket(serverWorld.getSpawnPoint(), serverWorld.func_242107_v()));
        if (serverWorld.isRaining()) {
            serverPlayerEntity.connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241765_b_, 0.0f));
            serverPlayerEntity.connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241771_h_, serverWorld.getRainStrength(1.0f)));
            serverPlayerEntity.connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241772_i_, serverWorld.getThunderStrength(1.0f)));
        }
    }

    public void sendInventory(ServerPlayerEntity serverPlayerEntity) {
        serverPlayerEntity.sendContainerToPlayer(serverPlayerEntity.container);
        serverPlayerEntity.setPlayerHealthUpdated();
        serverPlayerEntity.connection.sendPacket(new SHeldItemChangePacket(serverPlayerEntity.inventory.currentItem));
    }

    public int getCurrentPlayerCount() {
        return this.players.size();
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public boolean isWhiteListEnabled() {
        return this.whiteListEnforced;
    }

    public void setWhiteListEnabled(boolean bl) {
        this.whiteListEnforced = bl;
    }

    public List<ServerPlayerEntity> getPlayersMatchingAddress(String string) {
        ArrayList<ServerPlayerEntity> arrayList = Lists.newArrayList();
        for (ServerPlayerEntity serverPlayerEntity : this.players) {
            if (!serverPlayerEntity.getPlayerIP().equals(string)) continue;
            arrayList.add(serverPlayerEntity);
        }
        return arrayList;
    }

    public int getViewDistance() {
        return this.viewDistance;
    }

    public MinecraftServer getServer() {
        return this.server;
    }

    public CompoundNBT getHostPlayerData() {
        return null;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    private void setPlayerGameTypeBasedOnOther(ServerPlayerEntity serverPlayerEntity, @Nullable ServerPlayerEntity serverPlayerEntity2, ServerWorld serverWorld) {
        if (serverPlayerEntity2 != null) {
            serverPlayerEntity.interactionManager.func_241820_a(serverPlayerEntity2.interactionManager.getGameType(), serverPlayerEntity2.interactionManager.func_241815_c_());
        } else if (this.gameType != null) {
            serverPlayerEntity.interactionManager.func_241820_a(this.gameType, GameType.NOT_SET);
        }
        serverPlayerEntity.interactionManager.initializeGameType(serverWorld.getServer().func_240793_aU_().getGameType());
    }

    public void setCommandsAllowedForAll(boolean bl) {
        this.commandsAllowedForAll = bl;
    }

    public void removeAllPlayers() {
        for (int i = 0; i < this.players.size(); ++i) {
            this.players.get((int)i).connection.disconnect(new TranslationTextComponent("multiplayer.disconnect.server_shutdown"));
        }
    }

    public void func_232641_a_(ITextComponent iTextComponent, ChatType chatType, UUID uUID) {
        this.server.sendMessage(iTextComponent, uUID);
        this.sendPacketToAllPlayers(new SChatPacket(iTextComponent, chatType, uUID));
    }

    public ServerStatisticsManager getPlayerStats(PlayerEntity playerEntity) {
        ServerStatisticsManager serverStatisticsManager;
        UUID uUID = playerEntity.getUniqueID();
        ServerStatisticsManager serverStatisticsManager2 = serverStatisticsManager = uUID == null ? null : this.playerStatFiles.get(uUID);
        if (serverStatisticsManager == null) {
            File file;
            File file2 = this.server.func_240776_a_(FolderName.STATS).toFile();
            File file3 = new File(file2, uUID + ".json");
            if (!file3.exists() && (file = new File(file2, playerEntity.getName().getString() + ".json")).exists() && file.isFile()) {
                file.renameTo(file3);
            }
            serverStatisticsManager = new ServerStatisticsManager(this.server, file3);
            this.playerStatFiles.put(uUID, serverStatisticsManager);
        }
        return serverStatisticsManager;
    }

    public PlayerAdvancements getPlayerAdvancements(ServerPlayerEntity serverPlayerEntity) {
        UUID uUID = serverPlayerEntity.getUniqueID();
        PlayerAdvancements playerAdvancements = this.advancements.get(uUID);
        if (playerAdvancements == null) {
            File file = this.server.func_240776_a_(FolderName.ADVANCEMENTS).toFile();
            File file2 = new File(file, uUID + ".json");
            playerAdvancements = new PlayerAdvancements(this.server.getDataFixer(), this, this.server.getAdvancementManager(), file2, serverPlayerEntity);
            this.advancements.put(uUID, playerAdvancements);
        }
        playerAdvancements.setPlayer(serverPlayerEntity);
        return playerAdvancements;
    }

    public void setViewDistance(int n) {
        this.viewDistance = n;
        this.sendPacketToAllPlayers(new SUpdateViewDistancePacket(n));
        for (ServerWorld serverWorld : this.server.getWorlds()) {
            if (serverWorld == null) continue;
            serverWorld.getChunkProvider().setViewDistance(n);
        }
    }

    public List<ServerPlayerEntity> getPlayers() {
        return this.players;
    }

    @Nullable
    public ServerPlayerEntity getPlayerByUUID(UUID uUID) {
        return this.uuidToPlayerMap.get(uUID);
    }

    public boolean bypassesPlayerLimit(GameProfile gameProfile) {
        return true;
    }

    public void reloadResources() {
        for (PlayerAdvancements object : this.advancements.values()) {
            object.reset(this.server.getAdvancementManager());
        }
        this.sendPacketToAllPlayers(new STagsListPacket(this.server.func_244266_aF()));
        SUpdateRecipesPacket sUpdateRecipesPacket = new SUpdateRecipesPacket(this.server.getRecipeManager().getRecipes());
        for (ServerPlayerEntity serverPlayerEntity : this.players) {
            serverPlayerEntity.connection.sendPacket(sUpdateRecipesPacket);
            serverPlayerEntity.getRecipeBook().init(serverPlayerEntity);
        }
    }

    public boolean commandsAllowedForAll() {
        return this.commandsAllowedForAll;
    }

    private static Entity lambda$initializeConnectionToPlayer$0(ServerWorld serverWorld, Entity entity2) {
        return !serverWorld.summonEntity(entity2) ? null : entity2;
    }
}

