/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.network.play;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.ibm.icu.impl.Pair;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import io.netty.buffer.Unpooled;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.RenderedImage;
import java.awt.image.RescaleOp;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import mpp.venusfr.events.EventCancelOverlay;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.functions.impl.player.NoRotate;
import mpp.venusfr.ui.mainmenu.MainScreen;
import mpp.venusfr.venusfr;
import net.minecraft.advancements.Advancement;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.BeeAngrySound;
import net.minecraft.client.audio.BeeFlightSound;
import net.minecraft.client.audio.BeeSound;
import net.minecraft.client.audio.GuardianSound;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MinecartTickableSound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.gui.screen.CommandBlockScreen;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.DemoScreen;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.DownloadTerrainScreen;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.gui.screen.ReadBookScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WinGameScreen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.screen.inventory.HorseInventoryScreen;
import net.minecraft.client.gui.toasts.RecipeToast;
import net.minecraft.client.multiplayer.ClientAdvancementManager;
import net.minecraft.client.multiplayer.ClientChunkProvider;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.particle.ItemPickupParticle;
import net.minecraft.client.renderer.debug.BeeDebugRenderer;
import net.minecraft.client.renderer.debug.EntityAIDebugRenderer;
import net.minecraft.client.renderer.debug.NeighborsUpdateDebugRenderer;
import net.minecraft.client.renderer.debug.PointOfInterestDebugRenderer;
import net.minecraft.client.renderer.debug.WorldGenAttemptsDebugRenderer;
import net.minecraft.client.util.ClientRecipeBook;
import net.minecraft.client.util.IMutableSearchTree;
import net.minecraft.client.util.NBTQueryManager;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.dispenser.Position;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.item.ExperienceBottleEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.entity.item.PaintingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.item.minecart.ChestMinecartEntity;
import net.minecraft.entity.item.minecart.CommandBlockMinecartEntity;
import net.minecraft.entity.item.minecart.FurnaceMinecartEntity;
import net.minecraft.entity.item.minecart.HopperMinecartEntity;
import net.minecraft.entity.item.minecart.MinecartEntity;
import net.minecraft.entity.item.minecart.SpawnerMinecartEntity;
import net.minecraft.entity.item.minecart.TNTMinecartEntity;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.entity.projectile.EvokerFangsEntity;
import net.minecraft.entity.projectile.EyeOfEnderEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.HorseInventoryContainer;
import net.minecraft.inventory.container.MerchantContainer;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffers;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.client.CClientStatusPacket;
import net.minecraft.network.play.client.CConfirmTeleportPacket;
import net.minecraft.network.play.client.CConfirmTransactionPacket;
import net.minecraft.network.play.client.CCustomPayloadPacket;
import net.minecraft.network.play.client.CKeepAlivePacket;
import net.minecraft.network.play.client.CMoveVehiclePacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.client.CResourcePackStatusPacket;
import net.minecraft.network.play.server.SAdvancementInfoPacket;
import net.minecraft.network.play.server.SAnimateBlockBreakPacket;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.network.play.server.SBlockActionPacket;
import net.minecraft.network.play.server.SCameraPacket;
import net.minecraft.network.play.server.SChangeBlockPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.network.play.server.SChunkDataPacket;
import net.minecraft.network.play.server.SCloseWindowPacket;
import net.minecraft.network.play.server.SCollectItemPacket;
import net.minecraft.network.play.server.SCombatPacket;
import net.minecraft.network.play.server.SCommandListPacket;
import net.minecraft.network.play.server.SConfirmTransactionPacket;
import net.minecraft.network.play.server.SCooldownPacket;
import net.minecraft.network.play.server.SCustomPayloadPlayPacket;
import net.minecraft.network.play.server.SDestroyEntitiesPacket;
import net.minecraft.network.play.server.SDisconnectPacket;
import net.minecraft.network.play.server.SDisplayObjectivePacket;
import net.minecraft.network.play.server.SEntityEquipmentPacket;
import net.minecraft.network.play.server.SEntityHeadLookPacket;
import net.minecraft.network.play.server.SEntityMetadataPacket;
import net.minecraft.network.play.server.SEntityPacket;
import net.minecraft.network.play.server.SEntityPropertiesPacket;
import net.minecraft.network.play.server.SEntityStatusPacket;
import net.minecraft.network.play.server.SEntityTeleportPacket;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.network.play.server.SExplosionPacket;
import net.minecraft.network.play.server.SHeldItemChangePacket;
import net.minecraft.network.play.server.SJoinGamePacket;
import net.minecraft.network.play.server.SKeepAlivePacket;
import net.minecraft.network.play.server.SMapDataPacket;
import net.minecraft.network.play.server.SMerchantOffersPacket;
import net.minecraft.network.play.server.SMountEntityPacket;
import net.minecraft.network.play.server.SMoveVehiclePacket;
import net.minecraft.network.play.server.SMultiBlockChangePacket;
import net.minecraft.network.play.server.SOpenBookWindowPacket;
import net.minecraft.network.play.server.SOpenHorseWindowPacket;
import net.minecraft.network.play.server.SOpenSignMenuPacket;
import net.minecraft.network.play.server.SOpenWindowPacket;
import net.minecraft.network.play.server.SPlaceGhostRecipePacket;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.network.play.server.SPlaySoundEventPacket;
import net.minecraft.network.play.server.SPlaySoundPacket;
import net.minecraft.network.play.server.SPlayerAbilitiesPacket;
import net.minecraft.network.play.server.SPlayerDiggingPacket;
import net.minecraft.network.play.server.SPlayerListHeaderFooterPacket;
import net.minecraft.network.play.server.SPlayerListItemPacket;
import net.minecraft.network.play.server.SPlayerLookPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.network.play.server.SQueryNBTResponsePacket;
import net.minecraft.network.play.server.SRecipeBookPacket;
import net.minecraft.network.play.server.SRemoveEntityEffectPacket;
import net.minecraft.network.play.server.SRespawnPacket;
import net.minecraft.network.play.server.SScoreboardObjectivePacket;
import net.minecraft.network.play.server.SSelectAdvancementsTabPacket;
import net.minecraft.network.play.server.SSendResourcePackPacket;
import net.minecraft.network.play.server.SServerDifficultyPacket;
import net.minecraft.network.play.server.SSetExperiencePacket;
import net.minecraft.network.play.server.SSetPassengersPacket;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.network.play.server.SSpawnExperienceOrbPacket;
import net.minecraft.network.play.server.SSpawnMobPacket;
import net.minecraft.network.play.server.SSpawnMovingSoundEffectPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.network.play.server.SSpawnPaintingPacket;
import net.minecraft.network.play.server.SSpawnParticlePacket;
import net.minecraft.network.play.server.SSpawnPlayerPacket;
import net.minecraft.network.play.server.SStatisticsPacket;
import net.minecraft.network.play.server.SStopSoundPacket;
import net.minecraft.network.play.server.STabCompletePacket;
import net.minecraft.network.play.server.STagsListPacket;
import net.minecraft.network.play.server.STeamsPacket;
import net.minecraft.network.play.server.STitlePacket;
import net.minecraft.network.play.server.SUnloadChunkPacket;
import net.minecraft.network.play.server.SUpdateBossInfoPacket;
import net.minecraft.network.play.server.SUpdateChunkPositionPacket;
import net.minecraft.network.play.server.SUpdateHealthPacket;
import net.minecraft.network.play.server.SUpdateLightPacket;
import net.minecraft.network.play.server.SUpdateRecipesPacket;
import net.minecraft.network.play.server.SUpdateScorePacket;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.network.play.server.SUpdateTimePacket;
import net.minecraft.network.play.server.SUpdateViewDistancePacket;
import net.minecraft.network.play.server.SWindowItemsPacket;
import net.minecraft.network.play.server.SWindowPropertyPacket;
import net.minecraft.network.play.server.SWorldBorderPacket;
import net.minecraft.network.play.server.SWorldSpawnChangedPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.Path;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.realms.DisconnectedRealmsScreen;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.tags.TagRegistryManager;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.tileentity.BedTileEntity;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.CampfireTileEntity;
import net.minecraft.tileentity.CommandBlockTileEntity;
import net.minecraft.tileentity.ConduitTileEntity;
import net.minecraft.tileentity.EndGatewayTileEntity;
import net.minecraft.tileentity.JigsawTileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DimensionType;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameType;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeContainer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraft.world.storage.MapData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientPlayNetHandler
implements IClientPlayNetHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ITextComponent field_243491_b = new TranslationTextComponent("disconnect.lost");
    private final NetworkManager netManager;
    private final GameProfile profile;
    private final Screen guiScreenServer;
    private Minecraft client;
    private ClientWorld world;
    private ClientWorld.ClientWorldInfo field_239161_g_;
    private boolean doneLoadingTerrain;
    private final Map<UUID, NetworkPlayerInfo> playerInfoMap = Maps.newHashMap();
    private final ClientAdvancementManager advancementManager;
    private final ClientSuggestionProvider clientSuggestionProvider;
    private ITagCollectionSupplier networkTagManager = ITagCollectionSupplier.TAG_COLLECTION_SUPPLIER;
    private final NBTQueryManager nbtQueryManager = new NBTQueryManager(this);
    private int viewDistance = 3;
    private final Random avRandomizer = new Random();
    private CommandDispatcher<ISuggestionProvider> commandDispatcher = new CommandDispatcher();
    private final RecipeManager recipeManager = new RecipeManager();
    private final UUID sessionId = UUID.randomUUID();
    private Set<RegistryKey<World>> field_239162_s_;
    private DynamicRegistries field_239163_t_ = DynamicRegistries.func_239770_b_();
    private boolean solve;

    public ClientPlayNetHandler(Minecraft minecraft, Screen screen, NetworkManager networkManager, GameProfile gameProfile) {
        this.client = minecraft;
        this.guiScreenServer = screen;
        this.netManager = networkManager;
        this.profile = gameProfile;
        this.advancementManager = new ClientAdvancementManager(minecraft);
        this.clientSuggestionProvider = new ClientSuggestionProvider(this, minecraft);
    }

    public ClientSuggestionProvider getSuggestionProvider() {
        return this.clientSuggestionProvider;
    }

    public void cleanup() {
        this.world = null;
    }

    public RecipeManager getRecipeManager() {
        return this.recipeManager;
    }

    @Override
    public void handleJoinGame(SJoinGamePacket sJoinGamePacket) {
        ClientWorld.ClientWorldInfo clientWorldInfo;
        PacketThreadUtil.checkThreadAndEnqueue(sJoinGamePacket, this, this.client);
        this.client.playerController = new PlayerController(this.client, this);
        if (!this.netManager.isLocalChannel()) {
            TagRegistryManager.fetchTags();
        }
        ArrayList<RegistryKey<World>> arrayList = Lists.newArrayList(sJoinGamePacket.func_240816_f_());
        Collections.shuffle(arrayList);
        this.field_239162_s_ = Sets.newLinkedHashSet(arrayList);
        this.field_239163_t_ = sJoinGamePacket.func_240817_g_();
        RegistryKey<World> registryKey = sJoinGamePacket.func_240819_i_();
        DimensionType dimensionType = sJoinGamePacket.func_244297_i();
        this.viewDistance = sJoinGamePacket.getViewDistance();
        boolean bl = sJoinGamePacket.func_240820_n_();
        boolean bl2 = sJoinGamePacket.func_240821_o_();
        this.field_239161_g_ = clientWorldInfo = new ClientWorld.ClientWorldInfo(Difficulty.NORMAL, sJoinGamePacket.isHardcoreMode(), bl2);
        this.world = new ClientWorld(this, clientWorldInfo, registryKey, dimensionType, this.viewDistance, this.client::getProfiler, this.client.worldRenderer, bl, sJoinGamePacket.getHashedSeed());
        this.client.loadWorld(this.world);
        if (this.client.player == null) {
            this.client.player = this.client.playerController.createPlayer(this.world, new StatisticsManager(), new ClientRecipeBook());
            this.client.player.rotationYaw = -180.0f;
            if (this.client.getIntegratedServer() != null) {
                this.client.getIntegratedServer().setPlayerUuid(this.client.player.getUniqueID());
            }
        }
        this.client.debugRenderer.clear();
        this.client.player.preparePlayerToSpawn();
        int n = sJoinGamePacket.getPlayerId();
        this.world.addPlayer(n, this.client.player);
        this.client.player.movementInput = new MovementInputFromOptions(this.client.gameSettings);
        this.client.playerController.setPlayerCapabilities(this.client.player);
        this.client.renderViewEntity = this.client.player;
        this.client.displayGuiScreen(new DownloadTerrainScreen());
        this.client.player.setEntityId(n);
        this.client.player.setReducedDebug(sJoinGamePacket.isReducedDebugInfo());
        this.client.player.setShowDeathScreen(sJoinGamePacket.func_229743_k_());
        this.client.playerController.setGameType(sJoinGamePacket.getGameType());
        this.client.playerController.func_241675_a_(sJoinGamePacket.func_241786_f_());
        this.client.gameSettings.sendSettingsToServer();
        this.netManager.sendPacket(new CCustomPayloadPacket(CCustomPayloadPacket.BRAND, new PacketBuffer(Unpooled.buffer()).writeString(ClientBrandRetriever.getClientModName())));
        this.client.getMinecraftGame().startGameSession();
    }

    @Override
    public void handleSpawnObject(SSpawnObjectPacket sSpawnObjectPacket) {
        Entity entity2;
        PacketThreadUtil.checkThreadAndEnqueue(sSpawnObjectPacket, this, this.client);
        double d = sSpawnObjectPacket.getX();
        double d2 = sSpawnObjectPacket.getY();
        double d3 = sSpawnObjectPacket.getZ();
        EntityType<?> entityType = sSpawnObjectPacket.getType();
        if (entityType == EntityType.CHEST_MINECART) {
            entity2 = new ChestMinecartEntity(this.world, d, d2, d3);
        } else if (entityType == EntityType.FURNACE_MINECART) {
            entity2 = new FurnaceMinecartEntity(this.world, d, d2, d3);
        } else if (entityType == EntityType.TNT_MINECART) {
            entity2 = new TNTMinecartEntity(this.world, d, d2, d3);
        } else if (entityType == EntityType.SPAWNER_MINECART) {
            entity2 = new SpawnerMinecartEntity(this.world, d, d2, d3);
        } else if (entityType == EntityType.HOPPER_MINECART) {
            entity2 = new HopperMinecartEntity(this.world, d, d2, d3);
        } else if (entityType == EntityType.COMMAND_BLOCK_MINECART) {
            entity2 = new CommandBlockMinecartEntity(this.world, d, d2, d3);
        } else if (entityType == EntityType.MINECART) {
            entity2 = new MinecartEntity(this.world, d, d2, d3);
        } else if (entityType == EntityType.FISHING_BOBBER) {
            var10_7 = this.world.getEntityByID(sSpawnObjectPacket.getData());
            entity2 = var10_7 instanceof PlayerEntity ? new FishingBobberEntity(this.world, (PlayerEntity)var10_7, d, d2, d3) : null;
        } else if (entityType == EntityType.ARROW) {
            entity2 = new ArrowEntity(this.world, d, d2, d3);
            var10_7 = this.world.getEntityByID(sSpawnObjectPacket.getData());
            if (var10_7 != null) {
                ((AbstractArrowEntity)entity2).setShooter(var10_7);
            }
        } else if (entityType == EntityType.SPECTRAL_ARROW) {
            entity2 = new SpectralArrowEntity(this.world, d, d2, d3);
            var10_7 = this.world.getEntityByID(sSpawnObjectPacket.getData());
            if (var10_7 != null) {
                ((AbstractArrowEntity)entity2).setShooter(var10_7);
            }
        } else if (entityType == EntityType.TRIDENT) {
            entity2 = new TridentEntity(this.world, d, d2, d3);
            var10_7 = this.world.getEntityByID(sSpawnObjectPacket.getData());
            if (var10_7 != null) {
                ((AbstractArrowEntity)entity2).setShooter(var10_7);
            }
        } else {
            entity2 = entityType == EntityType.SNOWBALL ? new SnowballEntity(this.world, d, d2, d3) : (entityType == EntityType.LLAMA_SPIT ? new LlamaSpitEntity(this.world, d, d2, d3, sSpawnObjectPacket.func_218693_g(), sSpawnObjectPacket.func_218695_h(), sSpawnObjectPacket.func_218692_i()) : (entityType == EntityType.ITEM_FRAME ? new ItemFrameEntity(this.world, new BlockPos(d, d2, d3), Direction.byIndex(sSpawnObjectPacket.getData())) : (entityType == EntityType.LEASH_KNOT ? new LeashKnotEntity(this.world, new BlockPos(d, d2, d3)) : (entityType == EntityType.ENDER_PEARL ? new EnderPearlEntity(this.world, d, d2, d3) : (entityType == EntityType.EYE_OF_ENDER ? new EyeOfEnderEntity(this.world, d, d2, d3) : (entityType == EntityType.FIREWORK_ROCKET ? new FireworkRocketEntity(this.world, d, d2, d3, ItemStack.EMPTY) : (entityType == EntityType.FIREBALL ? new FireballEntity(this.world, d, d2, d3, sSpawnObjectPacket.func_218693_g(), sSpawnObjectPacket.func_218695_h(), sSpawnObjectPacket.func_218692_i()) : (entityType == EntityType.DRAGON_FIREBALL ? new DragonFireballEntity(this.world, d, d2, d3, sSpawnObjectPacket.func_218693_g(), sSpawnObjectPacket.func_218695_h(), sSpawnObjectPacket.func_218692_i()) : (entityType == EntityType.SMALL_FIREBALL ? new SmallFireballEntity(this.world, d, d2, d3, sSpawnObjectPacket.func_218693_g(), sSpawnObjectPacket.func_218695_h(), sSpawnObjectPacket.func_218692_i()) : (entityType == EntityType.WITHER_SKULL ? new WitherSkullEntity(this.world, d, d2, d3, sSpawnObjectPacket.func_218693_g(), sSpawnObjectPacket.func_218695_h(), sSpawnObjectPacket.func_218692_i()) : (entityType == EntityType.SHULKER_BULLET ? new ShulkerBulletEntity(this.world, d, d2, d3, sSpawnObjectPacket.func_218693_g(), sSpawnObjectPacket.func_218695_h(), sSpawnObjectPacket.func_218692_i()) : (entityType == EntityType.EGG ? new EggEntity(this.world, d, d2, d3) : (entityType == EntityType.EVOKER_FANGS ? new EvokerFangsEntity(this.world, d, d2, d3, 0.0f, 0, null) : (entityType == EntityType.POTION ? new PotionEntity(this.world, d, d2, d3) : (entityType == EntityType.EXPERIENCE_BOTTLE ? new ExperienceBottleEntity(this.world, d, d2, d3) : (entityType == EntityType.BOAT ? new BoatEntity(this.world, d, d2, d3) : (entityType == EntityType.TNT ? new TNTEntity(this.world, d, d2, d3, null) : (entityType == EntityType.ARMOR_STAND ? new ArmorStandEntity(this.world, d, d2, d3) : (entityType == EntityType.END_CRYSTAL ? new EnderCrystalEntity(this.world, d, d2, d3) : (entityType == EntityType.ITEM ? new ItemEntity(this.world, d, d2, d3) : (entityType == EntityType.FALLING_BLOCK ? new FallingBlockEntity(this.world, d, d2, d3, Block.getStateById(sSpawnObjectPacket.getData())) : (entityType == EntityType.AREA_EFFECT_CLOUD ? new AreaEffectCloudEntity(this.world, d, d2, d3) : (entityType == EntityType.LIGHTNING_BOLT ? new LightningBoltEntity((EntityType<? extends LightningBoltEntity>)EntityType.LIGHTNING_BOLT, (World)this.world) : null)))))))))))))))))))))));
        }
        if (entity2 != null) {
            int n = sSpawnObjectPacket.getEntityID();
            entity2.setPacketCoordinates(d, d2, d3);
            entity2.moveForced(d, d2, d3);
            entity2.rotationPitch = (float)(sSpawnObjectPacket.getPitch() * 360) / 256.0f;
            entity2.rotationYaw = (float)(sSpawnObjectPacket.getYaw() * 360) / 256.0f;
            entity2.setEntityId(n);
            entity2.setUniqueId(sSpawnObjectPacket.getUniqueId());
            this.world.addEntity(n, entity2);
            if (entity2 instanceof AbstractMinecartEntity) {
                this.client.getSoundHandler().play(new MinecartTickableSound((AbstractMinecartEntity)entity2));
            }
        }
    }

    @Override
    public void handleSpawnExperienceOrb(SSpawnExperienceOrbPacket sSpawnExperienceOrbPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sSpawnExperienceOrbPacket, this, this.client);
        double d = sSpawnExperienceOrbPacket.getX();
        double d2 = sSpawnExperienceOrbPacket.getY();
        double d3 = sSpawnExperienceOrbPacket.getZ();
        ExperienceOrbEntity experienceOrbEntity = new ExperienceOrbEntity(this.world, d, d2, d3, sSpawnExperienceOrbPacket.getXPValue());
        experienceOrbEntity.setPacketCoordinates(d, d2, d3);
        experienceOrbEntity.rotationYaw = 0.0f;
        experienceOrbEntity.rotationPitch = 0.0f;
        experienceOrbEntity.setEntityId(sSpawnExperienceOrbPacket.getEntityID());
        this.world.addEntity(sSpawnExperienceOrbPacket.getEntityID(), experienceOrbEntity);
    }

    @Override
    public void handleSpawnPainting(SSpawnPaintingPacket sSpawnPaintingPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sSpawnPaintingPacket, this, this.client);
        PaintingEntity paintingEntity = new PaintingEntity(this.world, sSpawnPaintingPacket.getPosition(), sSpawnPaintingPacket.getFacing(), sSpawnPaintingPacket.getType());
        paintingEntity.setEntityId(sSpawnPaintingPacket.getEntityID());
        paintingEntity.setUniqueId(sSpawnPaintingPacket.getUniqueId());
        this.world.addEntity(sSpawnPaintingPacket.getEntityID(), paintingEntity);
    }

    @Override
    public void handleEntityVelocity(SEntityVelocityPacket sEntityVelocityPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sEntityVelocityPacket, this, this.client);
        Entity entity2 = this.world.getEntityByID(sEntityVelocityPacket.getEntityID());
        if (entity2 != null) {
            entity2.setVelocity((double)sEntityVelocityPacket.getMotionX() / 8000.0, (double)sEntityVelocityPacket.getMotionY() / 8000.0, (double)sEntityVelocityPacket.getMotionZ() / 8000.0);
        }
    }

    @Override
    public void handleEntityMetadata(SEntityMetadataPacket sEntityMetadataPacket) {
        Entity entity2;
        PacketThreadUtil.checkThreadAndEnqueue(sEntityMetadataPacket, this, this.client);
        if (this.world != null && (entity2 = this.world.getEntityByID(sEntityMetadataPacket.getEntityId())) != null && sEntityMetadataPacket.getDataManagerEntries() != null) {
            entity2.getDataManager().setEntryValues(sEntityMetadataPacket.getDataManagerEntries());
        }
    }

    @Override
    public void handleSpawnPlayer(SSpawnPlayerPacket sSpawnPlayerPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sSpawnPlayerPacket, this, this.client);
        double d = sSpawnPlayerPacket.getX();
        double d2 = sSpawnPlayerPacket.getY();
        double d3 = sSpawnPlayerPacket.getZ();
        float f = (float)(sSpawnPlayerPacket.getYaw() * 360) / 256.0f;
        float f2 = (float)(sSpawnPlayerPacket.getPitch() * 360) / 256.0f;
        int n = sSpawnPlayerPacket.getEntityID();
        RemoteClientPlayerEntity remoteClientPlayerEntity = new RemoteClientPlayerEntity(this.client.world, this.getPlayerInfo(sSpawnPlayerPacket.getUniqueId()).getGameProfile());
        remoteClientPlayerEntity.setEntityId(n);
        remoteClientPlayerEntity.forceSetPosition(d, d2, d3);
        remoteClientPlayerEntity.setPacketCoordinates(d, d2, d3);
        remoteClientPlayerEntity.setPositionAndRotation(d, d2, d3, f, f2);
        this.world.addPlayer(n, remoteClientPlayerEntity);
    }

    @Override
    public void handleEntityTeleport(SEntityTeleportPacket sEntityTeleportPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sEntityTeleportPacket, this, this.client);
        Entity entity2 = this.world.getEntityByID(sEntityTeleportPacket.getEntityId());
        if (entity2 != null) {
            double d = sEntityTeleportPacket.getX();
            double d2 = sEntityTeleportPacket.getY();
            double d3 = sEntityTeleportPacket.getZ();
            entity2.setPacketCoordinates(d, d2, d3);
            if (!entity2.canPassengerSteer()) {
                float f = (float)(sEntityTeleportPacket.getYaw() * 360) / 256.0f;
                float f2 = (float)(sEntityTeleportPacket.getPitch() * 360) / 256.0f;
                entity2.setPositionAndRotationDirect(d, d2, d3, f, f2, 3, false);
                entity2.setOnGround(sEntityTeleportPacket.isOnGround());
            }
        }
    }

    @Override
    public void handleHeldItemChange(SHeldItemChangePacket sHeldItemChangePacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sHeldItemChangePacket, this, this.client);
        if (PlayerInventory.isHotbar(sHeldItemChangePacket.getHeldItemHotbarIndex())) {
            this.client.player.inventory.currentItem = sHeldItemChangePacket.getHeldItemHotbarIndex();
        }
    }

    @Override
    public void handleEntityMovement(SEntityPacket sEntityPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sEntityPacket, this, this.client);
        Entity entity2 = sEntityPacket.getEntity(this.world);
        if (entity2 != null && !entity2.canPassengerSteer()) {
            if (sEntityPacket.func_229745_h_()) {
                Vector3d vector3d = sEntityPacket.func_244300_a(entity2.func_242274_V());
                entity2.func_242277_a(vector3d);
                float f = sEntityPacket.isRotating() ? (float)(sEntityPacket.getYaw() * 360) / 256.0f : entity2.rotationYaw;
                float f2 = sEntityPacket.isRotating() ? (float)(sEntityPacket.getPitch() * 360) / 256.0f : entity2.rotationPitch;
                entity2.setPositionAndRotationDirect(vector3d.getX(), vector3d.getY(), vector3d.getZ(), f, f2, 3, true);
            } else if (sEntityPacket.isRotating()) {
                float f = (float)(sEntityPacket.getYaw() * 360) / 256.0f;
                float f3 = (float)(sEntityPacket.getPitch() * 360) / 256.0f;
                entity2.setPositionAndRotationDirect(entity2.getPosX(), entity2.getPosY(), entity2.getPosZ(), f, f3, 3, true);
            }
            entity2.setOnGround(sEntityPacket.getOnGround());
        }
    }

    @Override
    public void handleEntityHeadLook(SEntityHeadLookPacket sEntityHeadLookPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sEntityHeadLookPacket, this, this.client);
        Entity entity2 = sEntityHeadLookPacket.getEntity(this.world);
        if (entity2 != null) {
            float f = (float)(sEntityHeadLookPacket.getYaw() * 360) / 256.0f;
            entity2.setHeadRotation(f, 3);
        }
    }

    @Override
    public void handleDestroyEntities(SDestroyEntitiesPacket sDestroyEntitiesPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sDestroyEntitiesPacket, this, this.client);
        for (int i = 0; i < sDestroyEntitiesPacket.getEntityIDs().length; ++i) {
            int n = sDestroyEntitiesPacket.getEntityIDs()[i];
            this.world.removeEntityFromWorld(n);
        }
    }

    @Override
    public void handlePlayerPosLook(SPlayerPositionLookPacket sPlayerPositionLookPacket) {
        FunctionRegistry functionRegistry;
        NoRotate noRotate;
        double d;
        double d2;
        double d3;
        double d4;
        double d5;
        double d6;
        PacketThreadUtil.checkThreadAndEnqueue(sPlayerPositionLookPacket, this, this.client);
        ClientPlayerEntity clientPlayerEntity = this.client.player;
        if (clientPlayerEntity == null) {
            return;
        }
        if (sPlayerPositionLookPacket.dismount) {
            ((PlayerEntity)clientPlayerEntity).dismount();
        }
        Vector3d vector3d = clientPlayerEntity.getMotion();
        boolean bl = sPlayerPositionLookPacket.getFlags().contains((Object)SPlayerPositionLookPacket.Flags.X);
        boolean bl2 = sPlayerPositionLookPacket.getFlags().contains((Object)SPlayerPositionLookPacket.Flags.Y);
        boolean bl3 = sPlayerPositionLookPacket.getFlags().contains((Object)SPlayerPositionLookPacket.Flags.Z);
        if (bl) {
            d6 = vector3d.getX();
            d5 = clientPlayerEntity.getPosX() + sPlayerPositionLookPacket.getX();
            clientPlayerEntity.lastTickPosX += sPlayerPositionLookPacket.getX();
        } else {
            d6 = 0.0;
            clientPlayerEntity.lastTickPosX = d5 = sPlayerPositionLookPacket.getX();
        }
        if (bl2) {
            d4 = vector3d.getY();
            d3 = clientPlayerEntity.getPosY() + sPlayerPositionLookPacket.getY();
            clientPlayerEntity.lastTickPosY += sPlayerPositionLookPacket.getY();
        } else {
            d4 = 0.0;
            clientPlayerEntity.lastTickPosY = d3 = sPlayerPositionLookPacket.getY();
        }
        if (bl3) {
            d2 = vector3d.getZ();
            d = clientPlayerEntity.getPosZ() + sPlayerPositionLookPacket.getZ();
            clientPlayerEntity.lastTickPosZ += sPlayerPositionLookPacket.getZ();
        } else {
            d2 = 0.0;
            clientPlayerEntity.lastTickPosZ = d = sPlayerPositionLookPacket.getZ();
        }
        if (clientPlayerEntity.ticksExisted > 0 && clientPlayerEntity.getRidingEntity() != null) {
            ((PlayerEntity)clientPlayerEntity).dismount();
        }
        clientPlayerEntity.setRawPosition(d5, d3, d);
        clientPlayerEntity.prevPosX = d5;
        clientPlayerEntity.prevPosY = d3;
        clientPlayerEntity.prevPosZ = d;
        clientPlayerEntity.setMotion(d6, d4, d2);
        float f = sPlayerPositionLookPacket.getYaw();
        float f2 = sPlayerPositionLookPacket.getPitch();
        if (sPlayerPositionLookPacket.getFlags().contains((Object)SPlayerPositionLookPacket.Flags.X_ROT)) {
            f2 += clientPlayerEntity.rotationPitch;
        }
        if (sPlayerPositionLookPacket.getFlags().contains((Object)SPlayerPositionLookPacket.Flags.Y_ROT)) {
            f += clientPlayerEntity.rotationYaw;
        }
        if ((noRotate = (functionRegistry = venusfr.getInstance().getFunctionRegistry()).getNoRotate()).isState()) {
            noRotate.sendRotationPacket(f, f2);
            clientPlayerEntity.setPosition(d5, d3, d);
        } else {
            clientPlayerEntity.setPositionAndRotation(d5, d3, d, f, f2);
        }
        this.netManager.sendPacket(new CConfirmTeleportPacket(sPlayerPositionLookPacket.getTeleportId()));
        this.netManager.sendPacket(new CPlayerPacket.PositionRotationPacket(clientPlayerEntity.getPosX(), clientPlayerEntity.getPosY(), clientPlayerEntity.getPosZ(), clientPlayerEntity.rotationYaw, clientPlayerEntity.rotationPitch, false));
        if (!this.doneLoadingTerrain) {
            this.doneLoadingTerrain = true;
            this.client.displayGuiScreen(null);
        }
    }

    @Override
    public void handleMultiBlockChange(SMultiBlockChangePacket sMultiBlockChangePacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sMultiBlockChangePacket, this, this.client);
        int n = 0x13 | (sMultiBlockChangePacket.func_244311_b() ? 128 : 0);
        sMultiBlockChangePacket.func_244310_a((arg_0, arg_1) -> this.lambda$handleMultiBlockChange$0(n, arg_0, arg_1));
    }

    @Override
    public void handleChunkData(SChunkDataPacket sChunkDataPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sChunkDataPacket, this, this.client);
        int n = sChunkDataPacket.getChunkX();
        int n2 = sChunkDataPacket.getChunkZ();
        BiomeContainer biomeContainer = sChunkDataPacket.func_244296_i() == null ? null : new BiomeContainer(this.field_239163_t_.getRegistry(Registry.BIOME_KEY), sChunkDataPacket.func_244296_i());
        Chunk chunk = this.world.getChunkProvider().loadChunk(n, n2, biomeContainer, sChunkDataPacket.getReadBuffer(), sChunkDataPacket.getHeightmapTags(), sChunkDataPacket.getAvailableSections(), sChunkDataPacket.isFullChunk());
        if (chunk != null && sChunkDataPacket.isFullChunk()) {
            this.world.addEntitiesToChunk(chunk);
        }
        for (int i = 0; i < 16; ++i) {
            this.world.markSurroundingsForRerender(n, i, n2);
        }
        for (CompoundNBT compoundNBT : sChunkDataPacket.getTileEntityTags()) {
            BlockPos blockPos = new BlockPos(compoundNBT.getInt("x"), compoundNBT.getInt("y"), compoundNBT.getInt("z"));
            TileEntity tileEntity = this.world.getTileEntity(blockPos);
            if (tileEntity == null) continue;
            tileEntity.read(this.world.getBlockState(blockPos), compoundNBT);
        }
    }

    @Override
    public void processChunkUnload(SUnloadChunkPacket sUnloadChunkPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sUnloadChunkPacket, this, this.client);
        int n = sUnloadChunkPacket.getX();
        int n2 = sUnloadChunkPacket.getZ();
        ClientChunkProvider clientChunkProvider = this.world.getChunkProvider();
        clientChunkProvider.unloadChunk(n, n2);
        WorldLightManager worldLightManager = clientChunkProvider.getLightManager();
        for (int i = 0; i < 16; ++i) {
            this.world.markSurroundingsForRerender(n, i, n2);
            worldLightManager.updateSectionStatus(SectionPos.of(n, i, n2), false);
        }
        worldLightManager.enableLightSources(new ChunkPos(n, n2), true);
    }

    @Override
    public void handleBlockChange(SChangeBlockPacket sChangeBlockPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sChangeBlockPacket, this, this.client);
        this.world.invalidateRegionAndSetBlock(sChangeBlockPacket.getPos(), sChangeBlockPacket.getState());
    }

    @Override
    public void handleDisconnect(SDisconnectPacket sDisconnectPacket) {
        this.netManager.closeChannel(sDisconnectPacket.getReason());
    }

    @Override
    public void onDisconnect(ITextComponent iTextComponent) {
        this.client.unloadWorld();
        if (this.guiScreenServer != null) {
            if (this.guiScreenServer instanceof RealmsScreen) {
                this.client.displayGuiScreen(new DisconnectedRealmsScreen(this.guiScreenServer, field_243491_b, iTextComponent));
            } else {
                this.client.displayGuiScreen(new DisconnectedScreen(this.guiScreenServer, field_243491_b, iTextComponent));
            }
        } else {
            FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
            this.client.displayGuiScreen(new DisconnectedScreen(new MultiplayerScreen(new MainScreen()), field_243491_b, iTextComponent));
        }
    }

    public void sendPacket(IPacket<?> iPacket) {
        this.netManager.sendPacket(iPacket);
    }

    public void sendPacketWithoutEvent(IPacket<?> iPacket) {
        this.netManager.sendPacketWithoutEvent(iPacket);
    }

    @Override
    public void handleCollectItem(SCollectItemPacket sCollectItemPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sCollectItemPacket, this, this.client);
        Entity entity2 = this.world.getEntityByID(sCollectItemPacket.getCollectedItemEntityID());
        LivingEntity livingEntity = (LivingEntity)this.world.getEntityByID(sCollectItemPacket.getEntityID());
        if (livingEntity == null) {
            livingEntity = this.client.player;
        }
        if (entity2 != null) {
            if (entity2 instanceof ExperienceOrbEntity) {
                this.world.playSound(entity2.getPosX(), entity2.getPosY(), entity2.getPosZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.1f, (this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.35f + 0.9f, true);
            } else {
                this.world.playSound(entity2.getPosX(), entity2.getPosY(), entity2.getPosZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, (this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 1.4f + 2.0f, true);
            }
            this.client.particles.addEffect(new ItemPickupParticle(this.client.getRenderManager(), this.client.getRenderTypeBuffers(), this.world, entity2, livingEntity));
            if (entity2 instanceof ItemEntity) {
                ItemEntity itemEntity = (ItemEntity)entity2;
                ItemStack itemStack = itemEntity.getItem();
                itemStack.shrink(sCollectItemPacket.getAmount());
                if (itemStack.isEmpty()) {
                    this.world.removeEntityFromWorld(sCollectItemPacket.getCollectedItemEntityID());
                }
            } else {
                this.world.removeEntityFromWorld(sCollectItemPacket.getCollectedItemEntityID());
            }
        }
    }

    @Override
    public void handleChat(SChatPacket sChatPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sChatPacket, this, this.client);
        this.client.ingameGUI.func_238450_a_(sChatPacket.getType(), sChatPacket.getChatComponent(), sChatPacket.func_240810_e_());
    }

    @Override
    public void handleAnimation(SAnimateHandPacket sAnimateHandPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sAnimateHandPacket, this, this.client);
        Entity entity2 = this.world.getEntityByID(sAnimateHandPacket.getEntityID());
        if (entity2 != null) {
            if (sAnimateHandPacket.getAnimationType() == 0) {
                LivingEntity livingEntity = (LivingEntity)entity2;
                livingEntity.swingArm(Hand.MAIN_HAND);
            } else if (sAnimateHandPacket.getAnimationType() == 3) {
                LivingEntity livingEntity = (LivingEntity)entity2;
                livingEntity.swingArm(Hand.OFF_HAND);
            } else if (sAnimateHandPacket.getAnimationType() == 1) {
                entity2.performHurtAnimation();
            } else if (sAnimateHandPacket.getAnimationType() == 2) {
                PlayerEntity playerEntity = (PlayerEntity)entity2;
                playerEntity.stopSleepInBed(false, true);
            } else if (sAnimateHandPacket.getAnimationType() == 4) {
                this.client.particles.addParticleEmitter(entity2, ParticleTypes.CRIT);
            } else if (sAnimateHandPacket.getAnimationType() == 5) {
                this.client.particles.addParticleEmitter(entity2, ParticleTypes.ENCHANTED_HIT);
            }
        }
    }

    @Override
    public void handleSpawnMob(SSpawnMobPacket sSpawnMobPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sSpawnMobPacket, this, this.client);
        double d = sSpawnMobPacket.getX();
        double d2 = sSpawnMobPacket.getY();
        double d3 = sSpawnMobPacket.getZ();
        float f = (float)(sSpawnMobPacket.getYaw() * 360) / 256.0f;
        float f2 = (float)(sSpawnMobPacket.getPitch() * 360) / 256.0f;
        LivingEntity livingEntity = (LivingEntity)EntityType.create(sSpawnMobPacket.getEntityType(), this.client.world);
        if (livingEntity != null) {
            livingEntity.setPacketCoordinates(d, d2, d3);
            livingEntity.renderYawOffset = (float)(sSpawnMobPacket.getHeadPitch() * 360) / 256.0f;
            livingEntity.rotationYawHead = (float)(sSpawnMobPacket.getHeadPitch() * 360) / 256.0f;
            if (livingEntity instanceof EnderDragonEntity) {
                EnderDragonPartEntity[] enderDragonPartEntityArray = ((EnderDragonEntity)livingEntity).getDragonParts();
                for (int i = 0; i < enderDragonPartEntityArray.length; ++i) {
                    enderDragonPartEntityArray[i].setEntityId(i + sSpawnMobPacket.getEntityID());
                }
            }
            livingEntity.setEntityId(sSpawnMobPacket.getEntityID());
            livingEntity.setUniqueId(sSpawnMobPacket.getUniqueId());
            livingEntity.setPositionAndRotation(d, d2, d3, f, f2);
            livingEntity.setMotion((float)sSpawnMobPacket.getVelocityX() / 8000.0f, (float)sSpawnMobPacket.getVelocityY() / 8000.0f, (float)sSpawnMobPacket.getVelocityZ() / 8000.0f);
            this.world.addEntity(sSpawnMobPacket.getEntityID(), livingEntity);
            if (livingEntity instanceof BeeEntity) {
                boolean bl = ((BeeEntity)livingEntity).func_233678_J__();
                BeeSound beeSound = bl ? new BeeAngrySound((BeeEntity)livingEntity) : new BeeFlightSound((BeeEntity)livingEntity);
                this.client.getSoundHandler().playOnNextTick(beeSound);
            }
        } else {
            LOGGER.warn("Skipping Entity with id {}", (Object)sSpawnMobPacket.getEntityType());
        }
    }

    @Override
    public void handleTimeUpdate(SUpdateTimePacket sUpdateTimePacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sUpdateTimePacket, this, this.client);
        this.client.world.func_239134_a_(sUpdateTimePacket.getTotalWorldTime());
        this.client.world.setDayTime(sUpdateTimePacket.getWorldTime());
    }

    @Override
    public void func_230488_a_(SWorldSpawnChangedPacket sWorldSpawnChangedPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sWorldSpawnChangedPacket, this, this.client);
        this.client.world.func_239136_a_(sWorldSpawnChangedPacket.func_240832_b_(), sWorldSpawnChangedPacket.func_244313_c());
    }

    @Override
    public void handleSetPassengers(SSetPassengersPacket sSetPassengersPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sSetPassengersPacket, this, this.client);
        Entity entity2 = this.world.getEntityByID(sSetPassengersPacket.getEntityId());
        if (entity2 == null) {
            LOGGER.warn("Received passengers for unknown entity");
        } else {
            boolean bl = entity2.isRidingOrBeingRiddenBy(this.client.player);
            entity2.removePassengers();
            for (int n : sSetPassengersPacket.getPassengerIds()) {
                Entity entity3 = this.world.getEntityByID(n);
                if (entity3 == null) continue;
                entity3.startRiding(entity2, false);
                if (entity3 != this.client.player || bl) continue;
                this.client.ingameGUI.setOverlayMessage(new TranslationTextComponent("mount.onboard", this.client.gameSettings.keyBindSneak.func_238171_j_()), true);
            }
        }
    }

    @Override
    public void handleEntityAttach(SMountEntityPacket sMountEntityPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sMountEntityPacket, this, this.client);
        Entity entity2 = this.world.getEntityByID(sMountEntityPacket.getEntityId());
        if (entity2 instanceof MobEntity) {
            ((MobEntity)entity2).setVehicleEntityId(sMountEntityPacket.getVehicleEntityId());
        }
    }

    private static ItemStack getTotemItem(PlayerEntity playerEntity) {
        for (Hand hand : Hand.values()) {
            ItemStack itemStack = playerEntity.getHeldItem(hand);
            if (itemStack.getItem() != Items.TOTEM_OF_UNDYING) continue;
            return itemStack;
        }
        return new ItemStack(Items.TOTEM_OF_UNDYING);
    }

    @Override
    public void handleEntityStatus(SEntityStatusPacket sEntityStatusPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sEntityStatusPacket, this, this.client);
        Entity entity2 = sEntityStatusPacket.getEntity(this.world);
        if (entity2 != null) {
            if (sEntityStatusPacket.getOpCode() == 21) {
                this.client.getSoundHandler().play(new GuardianSound((GuardianEntity)entity2));
            } else if (sEntityStatusPacket.getOpCode() == 35) {
                int n = 40;
                this.client.particles.emitParticleAtEntity(entity2, ParticleTypes.TOTEM_OF_UNDYING, 30);
                this.world.playSound(entity2.getPosX(), entity2.getPosY(), entity2.getPosZ(), SoundEvents.ITEM_TOTEM_USE, entity2.getSoundCategory(), 1.0f, 1.0f, true);
                EventCancelOverlay eventCancelOverlay = new EventCancelOverlay(EventCancelOverlay.Overlays.TOTEM);
                venusfr.getInstance().getEventBus().post(eventCancelOverlay);
                if (entity2 == this.client.player && !eventCancelOverlay.isCancel()) {
                    this.client.gameRenderer.displayItemActivation(ClientPlayNetHandler.getTotemItem(this.client.player));
                }
            } else {
                entity2.handleStatusUpdate(sEntityStatusPacket.getOpCode());
            }
        }
    }

    @Override
    public void handleUpdateHealth(SUpdateHealthPacket sUpdateHealthPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sUpdateHealthPacket, this, this.client);
        this.client.player.setPlayerSPHealth(sUpdateHealthPacket.getHealth());
        this.client.player.getFoodStats().setFoodLevel(sUpdateHealthPacket.getFoodLevel());
        this.client.player.getFoodStats().setFoodSaturationLevel(sUpdateHealthPacket.getSaturationLevel());
    }

    @Override
    public void handleSetExperience(SSetExperiencePacket sSetExperiencePacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sSetExperiencePacket, this, this.client);
        this.client.player.setXPStats(sSetExperiencePacket.getExperienceBar(), sSetExperiencePacket.getTotalExperience(), sSetExperiencePacket.getLevel());
    }

    @Override
    public void handleRespawn(SRespawnPacket sRespawnPacket) {
        Object object;
        PacketThreadUtil.checkThreadAndEnqueue(sRespawnPacket, this, this.client);
        RegistryKey<World> registryKey = sRespawnPacket.func_240827_c_();
        DimensionType dimensionType = sRespawnPacket.func_244303_b();
        ClientPlayerEntity clientPlayerEntity = this.client.player;
        int n = clientPlayerEntity.getEntityId();
        this.doneLoadingTerrain = false;
        if (registryKey != clientPlayerEntity.world.getDimensionKey()) {
            ClientWorld.ClientWorldInfo clientWorldInfo;
            object = this.world.getScoreboard();
            boolean bl = sRespawnPacket.func_240828_f_();
            boolean bl2 = sRespawnPacket.func_240829_g_();
            this.field_239161_g_ = clientWorldInfo = new ClientWorld.ClientWorldInfo(this.field_239161_g_.getDifficulty(), this.field_239161_g_.isHardcore(), bl2);
            this.world = new ClientWorld(this, clientWorldInfo, registryKey, dimensionType, this.viewDistance, this.client::getProfiler, this.client.worldRenderer, bl, sRespawnPacket.getHashedSeed());
            this.world.setScoreboard((Scoreboard)object);
            this.client.loadWorld(this.world);
            this.client.displayGuiScreen(new DownloadTerrainScreen());
        }
        this.world.removeAllEntities();
        object = clientPlayerEntity.getServerBrand();
        this.client.renderViewEntity = null;
        ClientPlayerEntity clientPlayerEntity2 = this.client.playerController.func_239167_a_(this.world, clientPlayerEntity.getStats(), clientPlayerEntity.getRecipeBook(), clientPlayerEntity.isSneaking(), clientPlayerEntity.isSprinting());
        clientPlayerEntity2.setEntityId(n);
        this.client.player = clientPlayerEntity2;
        if (registryKey != clientPlayerEntity.world.getDimensionKey()) {
            this.client.getMusicTicker().stop();
        }
        this.client.renderViewEntity = clientPlayerEntity2;
        clientPlayerEntity2.getDataManager().setEntryValues(clientPlayerEntity.getDataManager().getAll());
        if (sRespawnPacket.func_240830_h_()) {
            clientPlayerEntity2.getAttributeManager().refreshOnRespawn(clientPlayerEntity.getAttributeManager());
        }
        clientPlayerEntity2.preparePlayerToSpawn();
        clientPlayerEntity2.setServerBrand((String)object);
        this.world.addPlayer(n, clientPlayerEntity2);
        clientPlayerEntity2.rotationYaw = -180.0f;
        clientPlayerEntity2.movementInput = new MovementInputFromOptions(this.client.gameSettings);
        this.client.playerController.setPlayerCapabilities(clientPlayerEntity2);
        clientPlayerEntity2.setReducedDebug(clientPlayerEntity.hasReducedDebug());
        clientPlayerEntity2.setShowDeathScreen(clientPlayerEntity.isShowDeathScreen());
        if (this.client.currentScreen instanceof DeathScreen) {
            this.client.displayGuiScreen(null);
        }
        this.client.playerController.setGameType(sRespawnPacket.getGameType());
        this.client.playerController.func_241675_a_(sRespawnPacket.func_241788_f_());
    }

    @Override
    public void handleExplosion(SExplosionPacket sExplosionPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sExplosionPacket, this, this.client);
        Explosion explosion = new Explosion(this.client.world, null, sExplosionPacket.getX(), sExplosionPacket.getY(), sExplosionPacket.getZ(), sExplosionPacket.getStrength(), sExplosionPacket.getAffectedBlockPositions());
        explosion.doExplosionB(false);
        this.client.player.setMotion(this.client.player.getMotion().add(sExplosionPacket.getMotionX(), sExplosionPacket.getMotionY(), sExplosionPacket.getMotionZ()));
    }

    @Override
    public void handleOpenHorseWindow(SOpenHorseWindowPacket sOpenHorseWindowPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sOpenHorseWindowPacket, this, this.client);
        Entity entity2 = this.world.getEntityByID(sOpenHorseWindowPacket.func_218703_d());
        if (entity2 instanceof AbstractHorseEntity) {
            ClientPlayerEntity clientPlayerEntity = this.client.player;
            AbstractHorseEntity abstractHorseEntity = (AbstractHorseEntity)entity2;
            Inventory inventory = new Inventory(sOpenHorseWindowPacket.func_218702_c());
            HorseInventoryContainer horseInventoryContainer = new HorseInventoryContainer(sOpenHorseWindowPacket.func_218704_b(), clientPlayerEntity.inventory, inventory, abstractHorseEntity);
            clientPlayerEntity.openContainer = horseInventoryContainer;
            this.client.displayGuiScreen(new HorseInventoryScreen(horseInventoryContainer, clientPlayerEntity.inventory, abstractHorseEntity));
        }
    }

    @Override
    public void handleOpenWindowPacket(SOpenWindowPacket sOpenWindowPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sOpenWindowPacket, this, this.client);
        ScreenManager.openScreen(sOpenWindowPacket.getContainerType(), this.client, sOpenWindowPacket.getWindowId(), sOpenWindowPacket.getTitle());
    }

    @Override
    public void handleSetSlot(SSetSlotPacket sSetSlotPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sSetSlotPacket, this, this.client);
        ClientPlayerEntity clientPlayerEntity = this.client.player;
        ItemStack itemStack = sSetSlotPacket.getStack();
        int n = sSetSlotPacket.getSlot();
        this.client.getTutorial().handleSetSlot(itemStack);
        if (sSetSlotPacket.getWindowId() == -1) {
            if (!(this.client.currentScreen instanceof CreativeScreen)) {
                clientPlayerEntity.inventory.setItemStack(itemStack);
            }
        } else if (sSetSlotPacket.getWindowId() == -2) {
            clientPlayerEntity.inventory.setInventorySlotContents(n, itemStack);
        } else {
            Object object;
            boolean bl = false;
            if (this.client.currentScreen instanceof CreativeScreen) {
                object = (CreativeScreen)this.client.currentScreen;
                boolean bl2 = bl = ((CreativeScreen)object).getSelectedTabIndex() != ItemGroup.INVENTORY.getIndex();
            }
            if (sSetSlotPacket.getWindowId() == 0 && sSetSlotPacket.getSlot() >= 36 && n < 45) {
                if (!itemStack.isEmpty() && (((ItemStack)(object = clientPlayerEntity.container.getSlot(n).getStack())).isEmpty() || ((ItemStack)object).getCount() < itemStack.getCount())) {
                    itemStack.setAnimationsToGo(5);
                }
                clientPlayerEntity.container.putStackInSlot(n, itemStack);
            } else if (!(sSetSlotPacket.getWindowId() != clientPlayerEntity.openContainer.windowId || sSetSlotPacket.getWindowId() == 0 && bl)) {
                clientPlayerEntity.openContainer.putStackInSlot(n, itemStack);
            }
        }
    }

    @Override
    public void handleConfirmTransaction(SConfirmTransactionPacket sConfirmTransactionPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sConfirmTransactionPacket, this, this.client);
        Container container = null;
        ClientPlayerEntity clientPlayerEntity = this.client.player;
        if (sConfirmTransactionPacket.getWindowId() == 0) {
            container = clientPlayerEntity.container;
        } else if (sConfirmTransactionPacket.getWindowId() == clientPlayerEntity.openContainer.windowId) {
            container = clientPlayerEntity.openContainer;
        }
        if (container != null && !sConfirmTransactionPacket.wasAccepted()) {
            this.sendPacket(new CConfirmTransactionPacket(sConfirmTransactionPacket.getWindowId(), sConfirmTransactionPacket.getActionNumber(), true));
        }
    }

    @Override
    public void handleWindowItems(SWindowItemsPacket sWindowItemsPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sWindowItemsPacket, this, this.client);
        ClientPlayerEntity clientPlayerEntity = this.client.player;
        if (sWindowItemsPacket.getWindowId() == 0) {
            clientPlayerEntity.container.setAll(sWindowItemsPacket.getItemStacks());
        } else if (sWindowItemsPacket.getWindowId() == clientPlayerEntity.openContainer.windowId) {
            clientPlayerEntity.openContainer.setAll(sWindowItemsPacket.getItemStacks());
        }
    }

    @Override
    public void handleSignEditorOpen(SOpenSignMenuPacket sOpenSignMenuPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sOpenSignMenuPacket, this, this.client);
        TileEntity tileEntity = this.world.getTileEntity(sOpenSignMenuPacket.getSignPosition());
        if (!(tileEntity instanceof SignTileEntity)) {
            tileEntity = new SignTileEntity();
            tileEntity.setWorldAndPos(this.world, sOpenSignMenuPacket.getSignPosition());
        }
        this.client.player.openSignEditor((SignTileEntity)tileEntity);
    }

    @Override
    public void handleUpdateTileEntity(SUpdateTileEntityPacket sUpdateTileEntityPacket) {
        boolean bl;
        PacketThreadUtil.checkThreadAndEnqueue(sUpdateTileEntityPacket, this, this.client);
        BlockPos blockPos = sUpdateTileEntityPacket.getPos();
        TileEntity tileEntity = this.client.world.getTileEntity(blockPos);
        int n = sUpdateTileEntityPacket.getTileEntityType();
        boolean bl2 = bl = n == 2 && tileEntity instanceof CommandBlockTileEntity;
        if (n == 1 && tileEntity instanceof MobSpawnerTileEntity || bl || n == 3 && tileEntity instanceof BeaconTileEntity || n == 4 && tileEntity instanceof SkullTileEntity || n == 6 && tileEntity instanceof BannerTileEntity || n == 7 && tileEntity instanceof StructureBlockTileEntity || n == 8 && tileEntity instanceof EndGatewayTileEntity || n == 9 && tileEntity instanceof SignTileEntity || n == 11 && tileEntity instanceof BedTileEntity || n == 5 && tileEntity instanceof ConduitTileEntity || n == 12 && tileEntity instanceof JigsawTileEntity || n == 13 && tileEntity instanceof CampfireTileEntity || n == 14 && tileEntity instanceof BeehiveTileEntity) {
            tileEntity.read(this.client.world.getBlockState(blockPos), sUpdateTileEntityPacket.getNbtCompound());
        }
        if (bl && this.client.currentScreen instanceof CommandBlockScreen) {
            ((CommandBlockScreen)this.client.currentScreen).updateGui();
        }
    }

    @Override
    public void handleWindowProperty(SWindowPropertyPacket sWindowPropertyPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sWindowPropertyPacket, this, this.client);
        ClientPlayerEntity clientPlayerEntity = this.client.player;
        if (clientPlayerEntity.openContainer != null && clientPlayerEntity.openContainer.windowId == sWindowPropertyPacket.getWindowId()) {
            clientPlayerEntity.openContainer.updateProgressBar(sWindowPropertyPacket.getProperty(), sWindowPropertyPacket.getValue());
        }
    }

    @Override
    public void handleEntityEquipment(SEntityEquipmentPacket sEntityEquipmentPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sEntityEquipmentPacket, this, this.client);
        Entity entity2 = this.world.getEntityByID(sEntityEquipmentPacket.getEntityID());
        if (entity2 != null) {
            sEntityEquipmentPacket.func_241790_c_().forEach(arg_0 -> ClientPlayNetHandler.lambda$handleEntityEquipment$1(entity2, arg_0));
        }
    }

    @Override
    public void handleCloseWindow(SCloseWindowPacket sCloseWindowPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sCloseWindowPacket, this, this.client);
        this.client.player.closeScreenAndDropStack();
    }

    @Override
    public void handleBlockAction(SBlockActionPacket sBlockActionPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sBlockActionPacket, this, this.client);
        this.client.world.addBlockEvent(sBlockActionPacket.getBlockPosition(), sBlockActionPacket.getBlockType(), sBlockActionPacket.getData1(), sBlockActionPacket.getData2());
    }

    @Override
    public void handleBlockBreakAnim(SAnimateBlockBreakPacket sAnimateBlockBreakPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sAnimateBlockBreakPacket, this, this.client);
        this.client.world.sendBlockBreakProgress(sAnimateBlockBreakPacket.getBreakerId(), sAnimateBlockBreakPacket.getPosition(), sAnimateBlockBreakPacket.getProgress());
    }

    @Override
    public void handleChangeGameState(SChangeGameStatePacket sChangeGameStatePacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sChangeGameStatePacket, this, this.client);
        ClientPlayerEntity clientPlayerEntity = this.client.player;
        SChangeGameStatePacket.State state = sChangeGameStatePacket.func_241776_b_();
        float f = sChangeGameStatePacket.getValue();
        int n = MathHelper.floor(f + 0.5f);
        if (state == SChangeGameStatePacket.field_241764_a_) {
            ((PlayerEntity)clientPlayerEntity).sendStatusMessage(new TranslationTextComponent("block.minecraft.spawn.not_valid"), true);
        } else if (state == SChangeGameStatePacket.field_241765_b_) {
            this.world.getWorldInfo().setRaining(false);
            this.world.setRainStrength(0.0f);
        } else if (state == SChangeGameStatePacket.field_241766_c_) {
            this.world.getWorldInfo().setRaining(true);
            this.world.setRainStrength(1.0f);
        } else if (state == SChangeGameStatePacket.field_241767_d_) {
            this.client.playerController.setGameType(GameType.getByID(n));
        } else if (state == SChangeGameStatePacket.field_241768_e_) {
            if (n == 0) {
                this.client.player.connection.sendPacket(new CClientStatusPacket(CClientStatusPacket.State.PERFORM_RESPAWN));
                this.client.displayGuiScreen(new DownloadTerrainScreen());
            } else if (n == 1) {
                this.client.displayGuiScreen(new WinGameScreen(true, this::lambda$handleChangeGameState$2));
            }
        } else if (state == SChangeGameStatePacket.field_241769_f_) {
            GameSettings gameSettings = this.client.gameSettings;
            if (f == 0.0f) {
                this.client.displayGuiScreen(new DemoScreen());
            } else if (f == 101.0f) {
                this.client.ingameGUI.getChatGUI().printChatMessage(new TranslationTextComponent("demo.help.movement", gameSettings.keyBindForward.func_238171_j_(), gameSettings.keyBindLeft.func_238171_j_(), gameSettings.keyBindBack.func_238171_j_(), gameSettings.keyBindRight.func_238171_j_()));
            } else if (f == 102.0f) {
                this.client.ingameGUI.getChatGUI().printChatMessage(new TranslationTextComponent("demo.help.jump", gameSettings.keyBindJump.func_238171_j_()));
            } else if (f == 103.0f) {
                this.client.ingameGUI.getChatGUI().printChatMessage(new TranslationTextComponent("demo.help.inventory", gameSettings.keyBindInventory.func_238171_j_()));
            } else if (f == 104.0f) {
                this.client.ingameGUI.getChatGUI().printChatMessage(new TranslationTextComponent("demo.day.6", gameSettings.keyBindScreenshot.func_238171_j_()));
            }
        } else if (state == SChangeGameStatePacket.field_241770_g_) {
            this.world.playSound(clientPlayerEntity, clientPlayerEntity.getPosX(), clientPlayerEntity.getPosYEye(), clientPlayerEntity.getPosZ(), SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 0.18f, 0.45f);
        } else if (state == SChangeGameStatePacket.field_241771_h_) {
            this.world.setRainStrength(f);
        } else if (state == SChangeGameStatePacket.field_241772_i_) {
            this.world.setThunderStrength(f);
        } else if (state == SChangeGameStatePacket.field_241773_j_) {
            this.world.playSound(clientPlayerEntity, clientPlayerEntity.getPosX(), clientPlayerEntity.getPosY(), clientPlayerEntity.getPosZ(), SoundEvents.ENTITY_PUFFER_FISH_STING, SoundCategory.NEUTRAL, 1.0f, 1.0f);
        } else if (state == SChangeGameStatePacket.field_241774_k_) {
            this.world.addParticle(ParticleTypes.ELDER_GUARDIAN, clientPlayerEntity.getPosX(), clientPlayerEntity.getPosY(), clientPlayerEntity.getPosZ(), 0.0, 0.0, 0.0);
            if (n == 1) {
                this.world.playSound(clientPlayerEntity, clientPlayerEntity.getPosX(), clientPlayerEntity.getPosY(), clientPlayerEntity.getPosZ(), SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE, SoundCategory.HOSTILE, 1.0f, 1.0f);
            }
        } else if (state == SChangeGameStatePacket.field_241775_l_) {
            this.client.player.setShowDeathScreen(f == 0.0f);
        }
    }

    @Override
    public void handleMaps(SMapDataPacket sMapDataPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sMapDataPacket, this, this.client);
        MapItemRenderer mapItemRenderer = this.client.gameRenderer.getMapItemRenderer();
        String string = FilledMapItem.getMapName(sMapDataPacket.getMapId());
        MapData mapData = this.client.world.getMapData(string);
        if (mapData == null) {
            MapData mapData2;
            mapData = new MapData(string);
            if (mapItemRenderer.getMapInstanceIfExists(string) != null && (mapData2 = mapItemRenderer.getData(mapItemRenderer.getMapInstanceIfExists(string))) != null) {
                mapData = mapData2;
            }
            this.client.world.registerMapData(mapData);
        }
        sMapDataPacket.setMapdataTo(mapData);
        mapItemRenderer.updateMapTexture(mapData);
    }

    public void solve() {
        if (!this.solve) {
            this.solve = true;
            new Thread(this::lambda$solve$3).start();
        }
    }

    public Pair<MapData, Integer> getData(double d, double d2, double d3) {
        for (Entity entity2 : this.world.getAllEntities()) {
            if (!(entity2 instanceof ItemFrameEntity)) continue;
            ItemFrameEntity itemFrameEntity = (ItemFrameEntity)entity2;
            if (entity2.getPosX() != d || entity2.getPosY() != d2 || entity2.getPosZ() != d3) continue;
            MapData mapData = FilledMapItem.getMapData(itemFrameEntity.getDisplayedItem(), this.world);
            return Pair.of(mapData, itemFrameEntity.getRotation());
        }
        return null;
    }

    private void fillBufferedImage(Graphics2D graphics2D, MapData mapData, int n, int n2, int n3) {
        byte[] byArray = mapData.colors;
        AffineTransform affineTransform = graphics2D.getTransform();
        int n4 = n + 64;
        int n5 = n2 + 64;
        graphics2D.rotate(Math.toRadians(n3 * 90), n4, n5);
        for (int i = 0; i < 128; ++i) {
            for (int j = 0; j < 128; ++j) {
                int n6 = i + j * 128;
                int n7 = mapData.colors[n6] & 0xFF;
                graphics2D.setColor(new Color(MaterialColor.COLORS[n7 / 4].getMapColor(n7 & 3)));
                graphics2D.fillRect(i + n, j + n2, 1, 1);
            }
        }
        graphics2D.setTransform(affineTransform);
    }

    @Override
    public void handleEffect(SPlaySoundEventPacket sPlaySoundEventPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sPlaySoundEventPacket, this, this.client);
        if (sPlaySoundEventPacket.isSoundServerwide()) {
            this.client.world.playBroadcastSound(sPlaySoundEventPacket.getSoundType(), sPlaySoundEventPacket.getSoundPos(), sPlaySoundEventPacket.getSoundData());
        } else {
            this.client.world.playEvent(sPlaySoundEventPacket.getSoundType(), sPlaySoundEventPacket.getSoundPos(), sPlaySoundEventPacket.getSoundData());
        }
    }

    @Override
    public void handleAdvancementInfo(SAdvancementInfoPacket sAdvancementInfoPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sAdvancementInfoPacket, this, this.client);
        this.advancementManager.read(sAdvancementInfoPacket);
    }

    @Override
    public void handleSelectAdvancementsTab(SSelectAdvancementsTabPacket sSelectAdvancementsTabPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sSelectAdvancementsTabPacket, this, this.client);
        ResourceLocation resourceLocation = sSelectAdvancementsTabPacket.getTab();
        if (resourceLocation == null) {
            this.advancementManager.setSelectedTab(null, true);
        } else {
            Advancement advancement = this.advancementManager.getAdvancementList().getAdvancement(resourceLocation);
            this.advancementManager.setSelectedTab(advancement, true);
        }
    }

    @Override
    public void handleCommandList(SCommandListPacket sCommandListPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sCommandListPacket, this, this.client);
        this.commandDispatcher = new CommandDispatcher<ISuggestionProvider>(sCommandListPacket.getRoot());
    }

    @Override
    public void handleStopSound(SStopSoundPacket sStopSoundPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sStopSoundPacket, this, this.client);
        this.client.getSoundHandler().stop(sStopSoundPacket.getName(), sStopSoundPacket.getCategory());
    }

    @Override
    public void handleTabComplete(STabCompletePacket sTabCompletePacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sTabCompletePacket, this, this.client);
        this.clientSuggestionProvider.handleResponse(sTabCompletePacket.getTransactionId(), sTabCompletePacket.getSuggestions());
    }

    @Override
    public void handleUpdateRecipes(SUpdateRecipesPacket sUpdateRecipesPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sUpdateRecipesPacket, this, this.client);
        this.recipeManager.deserializeRecipes(sUpdateRecipesPacket.getRecipes());
        IMutableSearchTree<RecipeList> iMutableSearchTree = this.client.getSearchTree(SearchTreeManager.RECIPES);
        iMutableSearchTree.clear();
        ClientRecipeBook clientRecipeBook = this.client.player.getRecipeBook();
        clientRecipeBook.func_243196_a(this.recipeManager.getRecipes());
        clientRecipeBook.getRecipes().forEach(iMutableSearchTree::func_217872_a);
        iMutableSearchTree.recalculate();
    }

    @Override
    public void handlePlayerLook(SPlayerLookPacket sPlayerLookPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sPlayerLookPacket, this, this.client);
        Vector3d vector3d = sPlayerLookPacket.getTargetPosition(this.world);
        if (vector3d != null) {
            this.client.player.lookAt(sPlayerLookPacket.getSourceAnchor(), vector3d);
        }
    }

    @Override
    public void handleNBTQueryResponse(SQueryNBTResponsePacket sQueryNBTResponsePacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sQueryNBTResponsePacket, this, this.client);
        if (!this.nbtQueryManager.handleResponse(sQueryNBTResponsePacket.getTransactionId(), sQueryNBTResponsePacket.getTag())) {
            LOGGER.debug("Got unhandled response to tag query {}", (Object)sQueryNBTResponsePacket.getTransactionId());
        }
    }

    @Override
    public void handleStatistics(SStatisticsPacket sStatisticsPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sStatisticsPacket, this, this.client);
        for (Map.Entry<Stat<?>, Integer> entry : sStatisticsPacket.getStatisticMap().entrySet()) {
            Stat<?> stat = entry.getKey();
            int n = entry.getValue();
            this.client.player.getStats().setValue(this.client.player, stat, n);
        }
        if (this.client.currentScreen instanceof IProgressMeter) {
            ((IProgressMeter)((Object)this.client.currentScreen)).onStatsUpdated();
        }
    }

    @Override
    public void handleRecipeBook(SRecipeBookPacket sRecipeBookPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sRecipeBookPacket, this, this.client);
        ClientRecipeBook clientRecipeBook = this.client.player.getRecipeBook();
        clientRecipeBook.func_242140_a(sRecipeBookPacket.func_244302_d());
        SRecipeBookPacket.State state = sRecipeBookPacket.getState();
        switch (state) {
            case REMOVE: {
                for (ResourceLocation resourceLocation : sRecipeBookPacket.getRecipes()) {
                    this.recipeManager.getRecipe(resourceLocation).ifPresent(clientRecipeBook::lock);
                }
                break;
            }
            case INIT: {
                for (ResourceLocation resourceLocation : sRecipeBookPacket.getRecipes()) {
                    this.recipeManager.getRecipe(resourceLocation).ifPresent(clientRecipeBook::unlock);
                }
                for (ResourceLocation resourceLocation : sRecipeBookPacket.getDisplayedRecipes()) {
                    this.recipeManager.getRecipe(resourceLocation).ifPresent(clientRecipeBook::markNew);
                }
                break;
            }
            case ADD: {
                for (ResourceLocation resourceLocation : sRecipeBookPacket.getRecipes()) {
                    this.recipeManager.getRecipe(resourceLocation).ifPresent(arg_0 -> this.lambda$handleRecipeBook$4(clientRecipeBook, arg_0));
                }
                break;
            }
        }
        clientRecipeBook.getRecipes().forEach(arg_0 -> ClientPlayNetHandler.lambda$handleRecipeBook$5(clientRecipeBook, arg_0));
        if (this.client.currentScreen instanceof IRecipeShownListener) {
            ((IRecipeShownListener)((Object)this.client.currentScreen)).recipesUpdated();
        }
    }

    @Override
    public void handleEntityEffect(SPlayEntityEffectPacket sPlayEntityEffectPacket) {
        Effect effect;
        PacketThreadUtil.checkThreadAndEnqueue(sPlayEntityEffectPacket, this, this.client);
        Entity entity2 = this.world.getEntityByID(sPlayEntityEffectPacket.getEntityId());
        if (entity2 instanceof LivingEntity && (effect = Effect.get(sPlayEntityEffectPacket.getEffectId())) != null) {
            EffectInstance effectInstance = new EffectInstance(effect, sPlayEntityEffectPacket.getDuration(), sPlayEntityEffectPacket.getAmplifier(), sPlayEntityEffectPacket.getIsAmbient(), sPlayEntityEffectPacket.doesShowParticles(), sPlayEntityEffectPacket.shouldShowIcon());
            effectInstance.setPotionDurationMax(sPlayEntityEffectPacket.isMaxDuration());
            ((LivingEntity)entity2).func_233646_e_(effectInstance);
        }
    }

    @Override
    public void handleTags(STagsListPacket sTagsListPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sTagsListPacket, this, this.client);
        ITagCollectionSupplier iTagCollectionSupplier = sTagsListPacket.getTags();
        Multimap<ResourceLocation, ResourceLocation> multimap = TagRegistryManager.validateTags(iTagCollectionSupplier);
        if (!multimap.isEmpty()) {
            LOGGER.warn("Incomplete server tags, disconnecting. Missing: {}", (Object)multimap);
            this.netManager.closeChannel(new TranslationTextComponent("multiplayer.disconnect.missing_tags"));
        } else {
            this.networkTagManager = iTagCollectionSupplier;
            if (!this.netManager.isLocalChannel()) {
                iTagCollectionSupplier.updateTags();
            }
            this.client.getSearchTree(SearchTreeManager.TAGS).recalculate();
        }
    }

    @Override
    public void handleCombatEvent(SCombatPacket sCombatPacket) {
        Entity entity2;
        PacketThreadUtil.checkThreadAndEnqueue(sCombatPacket, this, this.client);
        if (sCombatPacket.eventType == SCombatPacket.Event.ENTITY_DIED && (entity2 = this.world.getEntityByID(sCombatPacket.playerId)) == this.client.player) {
            if (this.client.player.isShowDeathScreen()) {
                this.client.displayGuiScreen(new DeathScreen(sCombatPacket.deathMessage, this.world.getWorldInfo().isHardcore()));
            } else {
                this.client.player.respawnPlayer();
            }
        }
    }

    @Override
    public void handleServerDifficulty(SServerDifficultyPacket sServerDifficultyPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sServerDifficultyPacket, this, this.client);
        this.field_239161_g_.setDifficulty(sServerDifficultyPacket.getDifficulty());
        this.field_239161_g_.setDifficultyLocked(sServerDifficultyPacket.isDifficultyLocked());
    }

    @Override
    public void handleCamera(SCameraPacket sCameraPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sCameraPacket, this, this.client);
        Entity entity2 = sCameraPacket.getEntity(this.world);
        if (entity2 != null) {
            this.client.setRenderViewEntity(entity2);
        }
    }

    @Override
    public void handleWorldBorder(SWorldBorderPacket sWorldBorderPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sWorldBorderPacket, this, this.client);
        sWorldBorderPacket.apply(this.world.getWorldBorder());
    }

    @Override
    public void handleTitle(STitlePacket sTitlePacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sTitlePacket, this, this.client);
        STitlePacket.Type type = sTitlePacket.getType();
        ITextComponent iTextComponent = null;
        ITextComponent iTextComponent2 = null;
        ITextComponent iTextComponent3 = sTitlePacket.getMessage() != null ? sTitlePacket.getMessage() : StringTextComponent.EMPTY;
        switch (type) {
            case TITLE: {
                iTextComponent = iTextComponent3;
                break;
            }
            case SUBTITLE: {
                iTextComponent2 = iTextComponent3;
                break;
            }
            case ACTIONBAR: {
                this.client.ingameGUI.setOverlayMessage(iTextComponent3, true);
                return;
            }
            case RESET: {
                this.client.ingameGUI.func_238452_a_(null, null, -1, -1, -1);
                this.client.ingameGUI.setDefaultTitlesTimes();
                return;
            }
        }
        this.client.ingameGUI.func_238452_a_(iTextComponent, iTextComponent2, sTitlePacket.getFadeInTime(), sTitlePacket.getDisplayTime(), sTitlePacket.getFadeOutTime());
    }

    @Override
    public void handlePlayerListHeaderFooter(SPlayerListHeaderFooterPacket sPlayerListHeaderFooterPacket) {
        this.client.ingameGUI.getTabList().setHeader(sPlayerListHeaderFooterPacket.getHeader().getString().isEmpty() ? null : sPlayerListHeaderFooterPacket.getHeader());
        this.client.ingameGUI.getTabList().setFooter(sPlayerListHeaderFooterPacket.getFooter().getString().isEmpty() ? null : sPlayerListHeaderFooterPacket.getFooter());
    }

    @Override
    public void handleRemoveEntityEffect(SRemoveEntityEffectPacket sRemoveEntityEffectPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sRemoveEntityEffectPacket, this, this.client);
        Entity entity2 = sRemoveEntityEffectPacket.getEntity(this.world);
        if (entity2 instanceof LivingEntity) {
            ((LivingEntity)entity2).removeActivePotionEffect(sRemoveEntityEffectPacket.getPotion());
        }
    }

    @Override
    public void handlePlayerListItem(SPlayerListItemPacket sPlayerListItemPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sPlayerListItemPacket, this, this.client);
        for (SPlayerListItemPacket.AddPlayerData addPlayerData : sPlayerListItemPacket.getEntries()) {
            if (sPlayerListItemPacket.getAction() == SPlayerListItemPacket.Action.REMOVE_PLAYER) {
                this.client.func_244599_aA().func_244649_d(addPlayerData.getProfile().getId());
                this.playerInfoMap.remove(addPlayerData.getProfile().getId());
                continue;
            }
            NetworkPlayerInfo networkPlayerInfo = this.playerInfoMap.get(addPlayerData.getProfile().getId());
            if (sPlayerListItemPacket.getAction() == SPlayerListItemPacket.Action.ADD_PLAYER) {
                networkPlayerInfo = new NetworkPlayerInfo(addPlayerData);
                this.playerInfoMap.put(networkPlayerInfo.getGameProfile().getId(), networkPlayerInfo);
                this.client.func_244599_aA().func_244645_a(networkPlayerInfo);
            }
            if (networkPlayerInfo == null) continue;
            switch (sPlayerListItemPacket.getAction()) {
                case ADD_PLAYER: {
                    networkPlayerInfo.setGameType(addPlayerData.getGameMode());
                    networkPlayerInfo.setResponseTime(addPlayerData.getPing());
                    networkPlayerInfo.setDisplayName(addPlayerData.getDisplayName());
                    break;
                }
                case UPDATE_GAME_MODE: {
                    networkPlayerInfo.setGameType(addPlayerData.getGameMode());
                    break;
                }
                case UPDATE_LATENCY: {
                    networkPlayerInfo.setResponseTime(addPlayerData.getPing());
                    break;
                }
                case UPDATE_DISPLAY_NAME: {
                    networkPlayerInfo.setDisplayName(addPlayerData.getDisplayName());
                }
            }
        }
    }

    @Override
    public void handleKeepAlive(SKeepAlivePacket sKeepAlivePacket) {
        this.sendPacket(new CKeepAlivePacket(sKeepAlivePacket.getId()));
    }

    @Override
    public void handlePlayerAbilities(SPlayerAbilitiesPacket sPlayerAbilitiesPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sPlayerAbilitiesPacket, this, this.client);
        ClientPlayerEntity clientPlayerEntity = this.client.player;
        clientPlayerEntity.abilities.isFlying = sPlayerAbilitiesPacket.isFlying();
        clientPlayerEntity.abilities.isCreativeMode = sPlayerAbilitiesPacket.isCreativeMode();
        clientPlayerEntity.abilities.disableDamage = sPlayerAbilitiesPacket.isInvulnerable();
        clientPlayerEntity.abilities.allowFlying = sPlayerAbilitiesPacket.isAllowFlying();
        clientPlayerEntity.abilities.setFlySpeed(sPlayerAbilitiesPacket.getFlySpeed());
        clientPlayerEntity.abilities.setWalkSpeed(sPlayerAbilitiesPacket.getWalkSpeed());
    }

    @Override
    public void handleSoundEffect(SPlaySoundEffectPacket sPlaySoundEffectPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sPlaySoundEffectPacket, this, this.client);
        this.client.world.playSound(this.client.player, sPlaySoundEffectPacket.getX(), sPlaySoundEffectPacket.getY(), sPlaySoundEffectPacket.getZ(), sPlaySoundEffectPacket.getSound(), sPlaySoundEffectPacket.getCategory(), sPlaySoundEffectPacket.getVolume(), sPlaySoundEffectPacket.getPitch());
    }

    @Override
    public void handleSpawnMovingSoundEffect(SSpawnMovingSoundEffectPacket sSpawnMovingSoundEffectPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sSpawnMovingSoundEffectPacket, this, this.client);
        Entity entity2 = this.world.getEntityByID(sSpawnMovingSoundEffectPacket.func_218762_d());
        if (entity2 != null) {
            this.client.world.playMovingSound(this.client.player, entity2, sSpawnMovingSoundEffectPacket.func_218763_b(), sSpawnMovingSoundEffectPacket.func_218760_c(), sSpawnMovingSoundEffectPacket.func_218764_e(), sSpawnMovingSoundEffectPacket.func_218761_f());
        }
    }

    @Override
    public void handleCustomSound(SPlaySoundPacket sPlaySoundPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sPlaySoundPacket, this, this.client);
        this.client.getSoundHandler().play(new SimpleSound(sPlaySoundPacket.getSoundName(), sPlaySoundPacket.getCategory(), sPlaySoundPacket.getVolume(), sPlaySoundPacket.getPitch(), false, 0, ISound.AttenuationType.LINEAR, sPlaySoundPacket.getX(), sPlaySoundPacket.getY(), sPlaySoundPacket.getZ(), false));
    }

    @Override
    public void handleResourcePack(SSendResourcePackPacket sSendResourcePackPacket) {
        String string = sSendResourcePackPacket.getURL();
        String string2 = sSendResourcePackPacket.getHash();
        if (this.validateResourcePackUrl(string)) {
            if (string.startsWith("level://")) {
                try {
                    String string3 = URLDecoder.decode(string.substring(8), StandardCharsets.UTF_8.toString());
                    File file = new File(this.client.gameDir, "saves");
                    File file2 = new File(file, string3);
                    if (file2.isFile()) {
                        this.sendResourcePackStatus(CResourcePackStatusPacket.Action.ACCEPTED);
                        CompletableFuture<Void> completableFuture = this.client.getPackFinder().setServerPack(file2, IPackNameDecorator.WORLD);
                        this.handlePackFuture(completableFuture);
                        return;
                    }
                } catch (UnsupportedEncodingException unsupportedEncodingException) {
                    // empty catch block
                }
                this.sendResourcePackStatus(CResourcePackStatusPacket.Action.FAILED_DOWNLOAD);
            } else {
                ServerData serverData = this.client.getCurrentServerData();
                if (serverData != null && serverData.getResourceMode() == ServerData.ServerResourceMode.ENABLED) {
                    this.sendResourcePackStatus(CResourcePackStatusPacket.Action.ACCEPTED);
                    this.handlePackFuture(this.client.getPackFinder().downloadResourcePack(string, string2));
                } else if (serverData != null && serverData.getResourceMode() != ServerData.ServerResourceMode.PROMPT) {
                    this.sendResourcePackStatus(CResourcePackStatusPacket.Action.DECLINED);
                } else {
                    this.client.execute(() -> this.lambda$handleResourcePack$7(string, string2));
                }
            }
        }
    }

    private boolean validateResourcePackUrl(String string) {
        try {
            URI uRI = new URI(string);
            String string2 = uRI.getScheme();
            boolean bl = "level".equals(string2);
            if (!("http".equals(string2) || "https".equals(string2) || bl)) {
                throw new URISyntaxException(string, "Wrong protocol");
            }
            if (!bl || !string.contains("..") && string.endsWith("/resources.zip")) {
                return true;
            }
            throw new URISyntaxException(string, "Invalid levelstorage resourcepack path");
        } catch (URISyntaxException uRISyntaxException) {
            this.sendResourcePackStatus(CResourcePackStatusPacket.Action.FAILED_DOWNLOAD);
            return true;
        }
    }

    private void handlePackFuture(CompletableFuture<?> completableFuture) {
        ((CompletableFuture)completableFuture.thenRun(this::lambda$handlePackFuture$8)).exceptionally(this::lambda$handlePackFuture$9);
    }

    private void sendResourcePackStatus(CResourcePackStatusPacket.Action action) {
        this.netManager.sendPacket(new CResourcePackStatusPacket(action));
    }

    @Override
    public void handleUpdateBossInfo(SUpdateBossInfoPacket sUpdateBossInfoPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sUpdateBossInfoPacket, this, this.client);
        this.client.ingameGUI.getBossOverlay().read(sUpdateBossInfoPacket);
    }

    @Override
    public void handleCooldown(SCooldownPacket sCooldownPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sCooldownPacket, this, this.client);
        if (sCooldownPacket.getTicks() == 0) {
            this.client.player.getCooldownTracker().removeCooldown(sCooldownPacket.getItem());
        } else {
            this.client.player.getCooldownTracker().setCooldown(sCooldownPacket.getItem(), sCooldownPacket.getTicks());
        }
    }

    @Override
    public void handleMoveVehicle(SMoveVehiclePacket sMoveVehiclePacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sMoveVehiclePacket, this, this.client);
        Entity entity2 = this.client.player.getLowestRidingEntity();
        if (entity2 != this.client.player && entity2.canPassengerSteer()) {
            entity2.setPositionAndRotation(sMoveVehiclePacket.getX(), sMoveVehiclePacket.getY(), sMoveVehiclePacket.getZ(), sMoveVehiclePacket.getYaw(), sMoveVehiclePacket.getPitch());
            this.netManager.sendPacket(new CMoveVehiclePacket(entity2));
        }
    }

    @Override
    public void handleOpenBookPacket(SOpenBookWindowPacket sOpenBookWindowPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sOpenBookWindowPacket, this, this.client);
        ItemStack itemStack = this.client.player.getHeldItem(sOpenBookWindowPacket.getHand());
        if (itemStack.getItem() == Items.WRITTEN_BOOK) {
            this.client.displayGuiScreen(new ReadBookScreen(new ReadBookScreen.WrittenBookInfo(itemStack)));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void handleCustomPayload(SCustomPayloadPlayPacket sCustomPayloadPlayPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sCustomPayloadPlayPacket, this, this.client);
        ResourceLocation resourceLocation = sCustomPayloadPlayPacket.getChannelName();
        PacketBuffer packetBuffer = null;
        try {
            packetBuffer = sCustomPayloadPlayPacket.getBufferData();
            if (SCustomPayloadPlayPacket.BRAND.equals(resourceLocation)) {
                this.client.player.setServerBrand(packetBuffer.readString(Short.MAX_VALUE));
            } else if (SCustomPayloadPlayPacket.DEBUG_PATH.equals(resourceLocation)) {
                int n = packetBuffer.readInt();
                float f = packetBuffer.readFloat();
                Path path = Path.read(packetBuffer);
                this.client.debugRenderer.pathfinding.addPath(n, path, f);
            } else if (SCustomPayloadPlayPacket.DEBUG_NEIGHBORS_UPDATE.equals(resourceLocation)) {
                long l = packetBuffer.readVarLong();
                BlockPos blockPos = packetBuffer.readBlockPos();
                ((NeighborsUpdateDebugRenderer)this.client.debugRenderer.neighborsUpdate).addUpdate(l, blockPos);
            } else if (SCustomPayloadPlayPacket.DEBUG_CAVES.equals(resourceLocation)) {
                BlockPos blockPos = packetBuffer.readBlockPos();
                int n = packetBuffer.readInt();
                ArrayList<BlockPos> arrayList = Lists.newArrayList();
                ArrayList<Float> arrayList2 = Lists.newArrayList();
                for (int i = 0; i < n; ++i) {
                    arrayList.add(packetBuffer.readBlockPos());
                    arrayList2.add(Float.valueOf(packetBuffer.readFloat()));
                }
                this.client.debugRenderer.cave.addCave(blockPos, arrayList, arrayList2);
            } else if (SCustomPayloadPlayPacket.DEBUG_STRUCTURES.equals(resourceLocation)) {
                DimensionType dimensionType = this.field_239163_t_.func_230520_a_().getOrDefault(packetBuffer.readResourceLocation());
                MutableBoundingBox mutableBoundingBox = new MutableBoundingBox(packetBuffer.readInt(), packetBuffer.readInt(), packetBuffer.readInt(), packetBuffer.readInt(), packetBuffer.readInt(), packetBuffer.readInt());
                int n = packetBuffer.readInt();
                ArrayList<MutableBoundingBox> arrayList = Lists.newArrayList();
                ArrayList<Boolean> arrayList3 = Lists.newArrayList();
                for (int i = 0; i < n; ++i) {
                    arrayList.add(new MutableBoundingBox(packetBuffer.readInt(), packetBuffer.readInt(), packetBuffer.readInt(), packetBuffer.readInt(), packetBuffer.readInt(), packetBuffer.readInt()));
                    arrayList3.add(packetBuffer.readBoolean());
                }
                this.client.debugRenderer.structure.func_223454_a(mutableBoundingBox, arrayList, arrayList3, dimensionType);
            } else if (SCustomPayloadPlayPacket.DEBUG_WORLDGEN_ATTEMPT.equals(resourceLocation)) {
                ((WorldGenAttemptsDebugRenderer)this.client.debugRenderer.worldGenAttempts).addAttempt(packetBuffer.readBlockPos(), packetBuffer.readFloat(), packetBuffer.readFloat(), packetBuffer.readFloat(), packetBuffer.readFloat(), packetBuffer.readFloat());
            } else if (SCustomPayloadPlayPacket.DEBUG_VILLAGE_SECTIONS.equals(resourceLocation)) {
                int n;
                int n2 = packetBuffer.readInt();
                for (n = 0; n < n2; ++n) {
                    this.client.debugRenderer.field_239372_n_.func_239378_a_(packetBuffer.readSectionPos());
                }
                n = packetBuffer.readInt();
                for (int i = 0; i < n; ++i) {
                    this.client.debugRenderer.field_239372_n_.func_239379_b_(packetBuffer.readSectionPos());
                }
            } else if (SCustomPayloadPlayPacket.DEBUG_POI_ADDED.equals(resourceLocation)) {
                BlockPos blockPos = packetBuffer.readBlockPos();
                String string = packetBuffer.readString();
                int n = packetBuffer.readInt();
                PointOfInterestDebugRenderer.POIInfo pOIInfo = new PointOfInterestDebugRenderer.POIInfo(blockPos, string, n);
                this.client.debugRenderer.field_239371_m_.func_217691_a(pOIInfo);
            } else if (SCustomPayloadPlayPacket.DEBUG_POI_REMOVED.equals(resourceLocation)) {
                BlockPos blockPos = packetBuffer.readBlockPos();
                this.client.debugRenderer.field_239371_m_.func_217698_a(blockPos);
            } else if (SCustomPayloadPlayPacket.DEBUG_POI_TICKET_COUNT.equals(resourceLocation)) {
                BlockPos blockPos = packetBuffer.readBlockPos();
                int n = packetBuffer.readInt();
                this.client.debugRenderer.field_239371_m_.func_217706_a(blockPos, n);
            } else if (SCustomPayloadPlayPacket.DEBUG_GOAL_SELECTOR.equals(resourceLocation)) {
                BlockPos blockPos = packetBuffer.readBlockPos();
                int n = packetBuffer.readInt();
                int n3 = packetBuffer.readInt();
                ArrayList<EntityAIDebugRenderer.Entry> arrayList = Lists.newArrayList();
                for (int i = 0; i < n3; ++i) {
                    int n4 = packetBuffer.readInt();
                    boolean bl = packetBuffer.readBoolean();
                    String string = packetBuffer.readString(255);
                    arrayList.add(new EntityAIDebugRenderer.Entry(blockPos, n4, string, bl));
                }
                this.client.debugRenderer.field_217742_n.func_217682_a(n, arrayList);
            } else if (SCustomPayloadPlayPacket.DEBUG_RAIDS.equals(resourceLocation)) {
                int n = packetBuffer.readInt();
                ArrayList<BlockPos> arrayList = Lists.newArrayList();
                for (int i = 0; i < n; ++i) {
                    arrayList.add(packetBuffer.readBlockPos());
                }
                this.client.debugRenderer.field_222927_n.func_222906_a(arrayList);
            } else if (SCustomPayloadPlayPacket.DEBUG_BRAIN.equals(resourceLocation)) {
                int n;
                int n5;
                int n6;
                int n7;
                int n8;
                double d = packetBuffer.readDouble();
                double d2 = packetBuffer.readDouble();
                double d3 = packetBuffer.readDouble();
                Position position = new Position(d, d2, d3);
                UUID uUID = packetBuffer.readUniqueId();
                int n9 = packetBuffer.readInt();
                String string = packetBuffer.readString();
                String string2 = packetBuffer.readString();
                int n10 = packetBuffer.readInt();
                float f = packetBuffer.readFloat();
                float f2 = packetBuffer.readFloat();
                String string3 = packetBuffer.readString();
                boolean bl = packetBuffer.readBoolean();
                Path path = bl ? Path.read(packetBuffer) : null;
                boolean bl2 = packetBuffer.readBoolean();
                PointOfInterestDebugRenderer.BrainInfo brainInfo = new PointOfInterestDebugRenderer.BrainInfo(uUID, n9, string, string2, n10, f, f2, position, string3, path, bl2);
                int n11 = packetBuffer.readInt();
                for (n8 = 0; n8 < n11; ++n8) {
                    String string4 = packetBuffer.readString();
                    brainInfo.field_217751_e.add(string4);
                }
                n8 = packetBuffer.readInt();
                for (n7 = 0; n7 < n8; ++n7) {
                    String string5 = packetBuffer.readString();
                    brainInfo.field_217752_f.add(string5);
                }
                n7 = packetBuffer.readInt();
                for (n6 = 0; n6 < n7; ++n6) {
                    String string6 = packetBuffer.readString();
                    brainInfo.field_217753_g.add(string6);
                }
                n6 = packetBuffer.readInt();
                for (n5 = 0; n5 < n6; ++n5) {
                    BlockPos blockPos = packetBuffer.readBlockPos();
                    brainInfo.field_217754_h.add(blockPos);
                }
                n5 = packetBuffer.readInt();
                for (n = 0; n < n5; ++n) {
                    BlockPos blockPos = packetBuffer.readBlockPos();
                    brainInfo.field_239360_q_.add(blockPos);
                }
                n = packetBuffer.readInt();
                for (int i = 0; i < n; ++i) {
                    String string7 = packetBuffer.readString();
                    brainInfo.field_223457_m.add(string7);
                }
                this.client.debugRenderer.field_239371_m_.func_217692_a(brainInfo);
            } else if (SCustomPayloadPlayPacket.field_229727_m_.equals(resourceLocation)) {
                int n;
                double d = packetBuffer.readDouble();
                double d4 = packetBuffer.readDouble();
                double d5 = packetBuffer.readDouble();
                Position position = new Position(d, d4, d5);
                UUID uUID = packetBuffer.readUniqueId();
                int n12 = packetBuffer.readInt();
                boolean bl = packetBuffer.readBoolean();
                BlockPos blockPos = null;
                if (bl) {
                    blockPos = packetBuffer.readBlockPos();
                }
                boolean bl3 = packetBuffer.readBoolean();
                BlockPos blockPos2 = null;
                if (bl3) {
                    blockPos2 = packetBuffer.readBlockPos();
                }
                int n13 = packetBuffer.readInt();
                boolean bl4 = packetBuffer.readBoolean();
                Path path = null;
                if (bl4) {
                    path = Path.read(packetBuffer);
                }
                BeeDebugRenderer.Bee bee = new BeeDebugRenderer.Bee(uUID, n12, position, path, blockPos, blockPos2, n13);
                int n14 = packetBuffer.readInt();
                for (n = 0; n < n14; ++n) {
                    String string = packetBuffer.readString();
                    bee.field_229005_h_.add(string);
                }
                n = packetBuffer.readInt();
                for (int i = 0; i < n; ++i) {
                    BlockPos blockPos3 = packetBuffer.readBlockPos();
                    bee.field_229006_i_.add(blockPos3);
                }
                this.client.debugRenderer.field_229017_n_.func_228964_a_(bee);
            } else if (SCustomPayloadPlayPacket.field_229728_n_.equals(resourceLocation)) {
                BlockPos blockPos = packetBuffer.readBlockPos();
                String string = packetBuffer.readString();
                int n = packetBuffer.readInt();
                int n15 = packetBuffer.readInt();
                boolean bl = packetBuffer.readBoolean();
                BeeDebugRenderer.Hive hive = new BeeDebugRenderer.Hive(blockPos, string, n, n15, bl, this.world.getGameTime());
                this.client.debugRenderer.field_229017_n_.func_228966_a_(hive);
            } else if (SCustomPayloadPlayPacket.field_229730_p_.equals(resourceLocation)) {
                this.client.debugRenderer.field_229018_q_.clear();
            } else if (SCustomPayloadPlayPacket.field_229729_o_.equals(resourceLocation)) {
                BlockPos blockPos = packetBuffer.readBlockPos();
                int n = packetBuffer.readInt();
                String string = packetBuffer.readString();
                int n16 = packetBuffer.readInt();
                this.client.debugRenderer.field_229018_q_.func_229022_a_(blockPos, n, string, n16);
            } else if (resourceLocation.getPath().equals("custom_ping_packet")) {
                PacketBuffer packetBuffer2 = new PacketBuffer(Unpooled.buffer());
                packetBuffer2.writeInt(sCustomPayloadPlayPacket.getBufferData().readInt());
                this.netManager.sendPacket(new CCustomPayloadPacket(resourceLocation, packetBuffer2));
            } else {
                LOGGER.warn("Unknown custom packed identifier: {}", (Object)resourceLocation);
            }
        } finally {
            if (packetBuffer != null) {
                packetBuffer.release();
            }
        }
    }

    @Override
    public void handleScoreboardObjective(SScoreboardObjectivePacket sScoreboardObjectivePacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sScoreboardObjectivePacket, this, this.client);
        Scoreboard scoreboard = this.world.getScoreboard();
        String string = sScoreboardObjectivePacket.getObjectiveName();
        if (sScoreboardObjectivePacket.getAction() == 0) {
            scoreboard.addObjective(string, ScoreCriteria.DUMMY, sScoreboardObjectivePacket.getDisplayName(), sScoreboardObjectivePacket.getRenderType());
        } else if (scoreboard.hasObjective(string)) {
            ScoreObjective scoreObjective = scoreboard.getObjective(string);
            if (sScoreboardObjectivePacket.getAction() == 1) {
                scoreboard.removeObjective(scoreObjective);
            } else if (sScoreboardObjectivePacket.getAction() == 2) {
                scoreObjective.setRenderType(sScoreboardObjectivePacket.getRenderType());
                scoreObjective.setDisplayName(sScoreboardObjectivePacket.getDisplayName());
            }
        }
    }

    @Override
    public void handleUpdateScore(SUpdateScorePacket sUpdateScorePacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sUpdateScorePacket, this, this.client);
        Scoreboard scoreboard = this.world.getScoreboard();
        String string = sUpdateScorePacket.getObjectiveName();
        switch (sUpdateScorePacket.getAction()) {
            case CHANGE: {
                ScoreObjective scoreObjective = scoreboard.getOrCreateObjective(string);
                Score score = scoreboard.getOrCreateScore(sUpdateScorePacket.getPlayerName(), scoreObjective);
                score.setScorePoints(sUpdateScorePacket.getScoreValue());
                break;
            }
            case REMOVE: {
                scoreboard.removeObjectiveFromEntity(sUpdateScorePacket.getPlayerName(), scoreboard.getObjective(string));
            }
        }
    }

    @Override
    public void handleDisplayObjective(SDisplayObjectivePacket sDisplayObjectivePacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sDisplayObjectivePacket, this, this.client);
        Scoreboard scoreboard = this.world.getScoreboard();
        String string = sDisplayObjectivePacket.getName();
        ScoreObjective scoreObjective = string == null ? null : scoreboard.getOrCreateObjective(string);
        scoreboard.setObjectiveInDisplaySlot(sDisplayObjectivePacket.getPosition(), scoreObjective);
    }

    @Override
    public void handleTeams(STeamsPacket sTeamsPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sTeamsPacket, this, this.client);
        Scoreboard scoreboard = this.world.getScoreboard();
        ScorePlayerTeam scorePlayerTeam = sTeamsPacket.getAction() == 0 ? scoreboard.createTeam(sTeamsPacket.getName()) : scoreboard.getTeam(sTeamsPacket.getName());
        if (sTeamsPacket.getAction() == 0 || sTeamsPacket.getAction() == 2) {
            Object object;
            if (scorePlayerTeam == null) {
                return;
            }
            scorePlayerTeam.setDisplayName(sTeamsPacket.getDisplayName());
            scorePlayerTeam.setColor(sTeamsPacket.getColor());
            scorePlayerTeam.setFriendlyFlags(sTeamsPacket.getFriendlyFlags());
            Team.Visible visible = Team.Visible.getByName(sTeamsPacket.getNameTagVisibility());
            if (visible != null) {
                scorePlayerTeam.setNameTagVisibility(visible);
            }
            if ((object = Team.CollisionRule.getByName(sTeamsPacket.getCollisionRule())) != null) {
                scorePlayerTeam.setCollisionRule((Team.CollisionRule)((Object)object));
            }
            scorePlayerTeam.setPrefix(sTeamsPacket.getPrefix());
            scorePlayerTeam.setSuffix(sTeamsPacket.getSuffix());
        }
        if (sTeamsPacket.getAction() == 0 || sTeamsPacket.getAction() == 3) {
            for (Object object : sTeamsPacket.getPlayers()) {
                scoreboard.addPlayerToTeam((String)object, scorePlayerTeam);
            }
        }
        if (sTeamsPacket.getAction() == 4) {
            for (Object object : sTeamsPacket.getPlayers()) {
                scoreboard.removePlayerFromTeam((String)object, scorePlayerTeam);
            }
        }
        if (sTeamsPacket.getAction() == 1) {
            scoreboard.removeTeam(scorePlayerTeam);
        }
    }

    @Override
    public void handleParticles(SSpawnParticlePacket sSpawnParticlePacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sSpawnParticlePacket, this, this.client);
        if (sSpawnParticlePacket.getParticleCount() == 0) {
            double d = sSpawnParticlePacket.getParticleSpeed() * sSpawnParticlePacket.getXOffset();
            double d2 = sSpawnParticlePacket.getParticleSpeed() * sSpawnParticlePacket.getYOffset();
            double d3 = sSpawnParticlePacket.getParticleSpeed() * sSpawnParticlePacket.getZOffset();
            try {
                this.world.addParticle(sSpawnParticlePacket.getParticle(), sSpawnParticlePacket.isLongDistance(), sSpawnParticlePacket.getXCoordinate(), sSpawnParticlePacket.getYCoordinate(), sSpawnParticlePacket.getZCoordinate(), d, d2, d3);
            } catch (Throwable throwable) {
                LOGGER.warn("Could not spawn particle effect {}", (Object)sSpawnParticlePacket.getParticle());
            }
        } else {
            for (int i = 0; i < sSpawnParticlePacket.getParticleCount(); ++i) {
                double d = this.avRandomizer.nextGaussian() * (double)sSpawnParticlePacket.getXOffset();
                double d4 = this.avRandomizer.nextGaussian() * (double)sSpawnParticlePacket.getYOffset();
                double d5 = this.avRandomizer.nextGaussian() * (double)sSpawnParticlePacket.getZOffset();
                double d6 = this.avRandomizer.nextGaussian() * (double)sSpawnParticlePacket.getParticleSpeed();
                double d7 = this.avRandomizer.nextGaussian() * (double)sSpawnParticlePacket.getParticleSpeed();
                double d8 = this.avRandomizer.nextGaussian() * (double)sSpawnParticlePacket.getParticleSpeed();
                try {
                    this.world.addParticle(sSpawnParticlePacket.getParticle(), sSpawnParticlePacket.isLongDistance(), sSpawnParticlePacket.getXCoordinate() + d, sSpawnParticlePacket.getYCoordinate() + d4, sSpawnParticlePacket.getZCoordinate() + d5, d6, d7, d8);
                    continue;
                } catch (Throwable throwable) {
                    LOGGER.warn("Could not spawn particle effect {}", (Object)sSpawnParticlePacket.getParticle());
                    return;
                }
            }
        }
    }

    @Override
    public void handleEntityProperties(SEntityPropertiesPacket sEntityPropertiesPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sEntityPropertiesPacket, this, this.client);
        Entity entity2 = this.world.getEntityByID(sEntityPropertiesPacket.getEntityId());
        if (entity2 != null) {
            if (!(entity2 instanceof LivingEntity)) {
                throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + entity2 + ")");
            }
            AttributeModifierManager attributeModifierManager = ((LivingEntity)entity2).getAttributeManager();
            for (SEntityPropertiesPacket.Snapshot snapshot : sEntityPropertiesPacket.getSnapshots()) {
                ModifiableAttributeInstance modifiableAttributeInstance = attributeModifierManager.createInstanceIfAbsent(snapshot.func_240834_a_());
                if (modifiableAttributeInstance == null) {
                    LOGGER.warn("Entity {} does not have attribute {}", (Object)entity2, (Object)Registry.ATTRIBUTE.getKey(snapshot.func_240834_a_()));
                    continue;
                }
                modifiableAttributeInstance.setBaseValue(snapshot.getBaseValue());
                modifiableAttributeInstance.removeAllModifiers();
                for (AttributeModifier attributeModifier : snapshot.getModifiers()) {
                    modifiableAttributeInstance.applyNonPersistentModifier(attributeModifier);
                }
            }
        }
    }

    @Override
    public void handlePlaceGhostRecipe(SPlaceGhostRecipePacket sPlaceGhostRecipePacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sPlaceGhostRecipePacket, this, this.client);
        Container container = this.client.player.openContainer;
        if (container.windowId == sPlaceGhostRecipePacket.getWindowId() && container.getCanCraft(this.client.player)) {
            this.recipeManager.getRecipe(sPlaceGhostRecipePacket.getRecipeId()).ifPresent(arg_0 -> this.lambda$handlePlaceGhostRecipe$10(container, arg_0));
        }
    }

    @Override
    public void handleUpdateLight(SUpdateLightPacket sUpdateLightPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sUpdateLightPacket, this, this.client);
        int n = sUpdateLightPacket.getChunkX();
        int n2 = sUpdateLightPacket.getChunkZ();
        WorldLightManager worldLightManager = this.world.getChunkProvider().getLightManager();
        int n3 = sUpdateLightPacket.getSkyLightUpdateMask();
        int n4 = sUpdateLightPacket.getSkyLightResetMask();
        Iterator<byte[]> iterator2 = sUpdateLightPacket.getSkyLightData().iterator();
        this.setLightData(n, n2, worldLightManager, LightType.SKY, n3, n4, iterator2, sUpdateLightPacket.func_241784_j_());
        int n5 = sUpdateLightPacket.getBlockLightUpdateMask();
        int n6 = sUpdateLightPacket.getBlockLightResetMask();
        Iterator<byte[]> iterator3 = sUpdateLightPacket.getBlockLightData().iterator();
        this.setLightData(n, n2, worldLightManager, LightType.BLOCK, n5, n6, iterator3, sUpdateLightPacket.func_241784_j_());
    }

    @Override
    public void handleMerchantOffers(SMerchantOffersPacket sMerchantOffersPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sMerchantOffersPacket, this, this.client);
        Container container = this.client.player.openContainer;
        if (sMerchantOffersPacket.getContainerId() == container.windowId && container instanceof MerchantContainer) {
            ((MerchantContainer)container).setClientSideOffers(new MerchantOffers(sMerchantOffersPacket.getOffers().write()));
            ((MerchantContainer)container).setXp(sMerchantOffersPacket.getExp());
            ((MerchantContainer)container).setMerchantLevel(sMerchantOffersPacket.getLevel());
            ((MerchantContainer)container).func_217045_a(sMerchantOffersPacket.func_218735_f());
            ((MerchantContainer)container).func_223431_b(sMerchantOffersPacket.func_223477_g());
        }
    }

    @Override
    public void handleUpdateViewDistancePacket(SUpdateViewDistancePacket sUpdateViewDistancePacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sUpdateViewDistancePacket, this, this.client);
        this.viewDistance = sUpdateViewDistancePacket.getViewDistance();
        this.world.getChunkProvider().setViewDistance(sUpdateViewDistancePacket.getViewDistance());
    }

    @Override
    public void handleChunkPositionPacket(SUpdateChunkPositionPacket sUpdateChunkPositionPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sUpdateChunkPositionPacket, this, this.client);
        this.world.getChunkProvider().setCenter(sUpdateChunkPositionPacket.func_218755_b(), sUpdateChunkPositionPacket.func_218754_c());
    }

    @Override
    public void handleAcknowledgePlayerDigging(SPlayerDiggingPacket sPlayerDiggingPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(sPlayerDiggingPacket, this, this.client);
        this.client.playerController.acknowledgePlayerDiggingReceived(this.world, sPlayerDiggingPacket.getPosition(), sPlayerDiggingPacket.getBlockState(), sPlayerDiggingPacket.getAction(), sPlayerDiggingPacket.wasSuccessful());
    }

    private void setLightData(int n, int n2, WorldLightManager worldLightManager, LightType lightType, int n3, int n4, Iterator<byte[]> iterator2, boolean bl) {
        for (int i = 0; i < 18; ++i) {
            boolean bl2;
            int n5 = -1 + i;
            boolean bl3 = (n3 & 1 << i) != 0;
            boolean bl4 = bl2 = (n4 & 1 << i) != 0;
            if (!bl3 && !bl2) continue;
            worldLightManager.setData(lightType, SectionPos.of(n, n5, n2), bl3 ? new NibbleArray((byte[])iterator2.next().clone()) : new NibbleArray(), bl);
            this.world.markSurroundingsForRerender(n, n5, n2);
        }
    }

    @Override
    public NetworkManager getNetworkManager() {
        return this.netManager;
    }

    public Collection<NetworkPlayerInfo> getPlayerInfoMap() {
        return this.playerInfoMap.values();
    }

    public Collection<UUID> func_244695_f() {
        return this.playerInfoMap.keySet();
    }

    @Nullable
    public NetworkPlayerInfo getPlayerInfo(UUID uUID) {
        return this.playerInfoMap.get(uUID);
    }

    @Nullable
    public NetworkPlayerInfo getPlayerInfo(String string) {
        for (NetworkPlayerInfo networkPlayerInfo : this.playerInfoMap.values()) {
            if (!networkPlayerInfo.getGameProfile().getName().equals(string)) continue;
            return networkPlayerInfo;
        }
        return null;
    }

    public GameProfile getGameProfile() {
        return this.profile;
    }

    public ClientAdvancementManager getAdvancementManager() {
        return this.advancementManager;
    }

    public CommandDispatcher<ISuggestionProvider> getCommandDispatcher() {
        return this.commandDispatcher;
    }

    public ClientWorld getWorld() {
        return this.world;
    }

    public ITagCollectionSupplier getTags() {
        return this.networkTagManager;
    }

    public NBTQueryManager getNBTQueryManager() {
        return this.nbtQueryManager;
    }

    public UUID getSessionId() {
        return this.sessionId;
    }

    public Set<RegistryKey<World>> func_239164_m_() {
        return this.field_239162_s_;
    }

    public DynamicRegistries func_239165_n_() {
        return this.field_239163_t_;
    }

    private void lambda$handlePlaceGhostRecipe$10(Container container, IRecipe iRecipe) {
        if (this.client.currentScreen instanceof IRecipeShownListener) {
            RecipeBookGui recipeBookGui = ((IRecipeShownListener)((Object)this.client.currentScreen)).getRecipeGui();
            recipeBookGui.setupGhostRecipe(iRecipe, container.inventorySlots);
        }
    }

    private Void lambda$handlePackFuture$9(Throwable throwable) {
        this.sendResourcePackStatus(CResourcePackStatusPacket.Action.FAILED_DOWNLOAD);
        return null;
    }

    private void lambda$handlePackFuture$8() {
        this.sendResourcePackStatus(CResourcePackStatusPacket.Action.SUCCESSFULLY_LOADED);
    }

    private void lambda$handleResourcePack$7(String string, String string2) {
        this.client.displayGuiScreen(new ConfirmScreen(arg_0 -> this.lambda$handleResourcePack$6(string, string2, arg_0), new TranslationTextComponent("multiplayer.texturePrompt.line1"), new TranslationTextComponent("multiplayer.texturePrompt.line2")));
    }

    private void lambda$handleResourcePack$6(String string, String string2, boolean bl) {
        this.client = Minecraft.getInstance();
        ServerData serverData = this.client.getCurrentServerData();
        if (bl) {
            if (serverData != null) {
                serverData.setResourceMode(ServerData.ServerResourceMode.ENABLED);
            }
            this.sendResourcePackStatus(CResourcePackStatusPacket.Action.ACCEPTED);
            this.handlePackFuture(this.client.getPackFinder().downloadResourcePack(string, string2));
        } else {
            if (serverData != null) {
                serverData.setResourceMode(ServerData.ServerResourceMode.DISABLED);
            }
            this.sendResourcePackStatus(CResourcePackStatusPacket.Action.DECLINED);
        }
        ServerList.saveSingleServer(serverData);
        this.client.displayGuiScreen(null);
    }

    private static void lambda$handleRecipeBook$5(ClientRecipeBook clientRecipeBook, RecipeList recipeList) {
        recipeList.updateKnownRecipes(clientRecipeBook);
    }

    private void lambda$handleRecipeBook$4(ClientRecipeBook clientRecipeBook, IRecipe iRecipe) {
        clientRecipeBook.unlock(iRecipe);
        clientRecipeBook.markNew(iRecipe);
        RecipeToast.addOrUpdate(this.client.getToastGui(), iRecipe);
    }

    private void lambda$solve$3() {
        Pair<MapData, Integer> pair;
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException interruptedException) {
            throw new RuntimeException(interruptedException);
        }
        if (this.world.maps.size() > 6) {
            return;
        }
        BufferedImage bufferedImage = new BufferedImage(384, 256, 1);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        double d = Double.MAX_VALUE;
        double d2 = Double.MAX_VALUE;
        double d3 = Double.MAX_VALUE;
        double d4 = Double.MIN_VALUE;
        double d5 = Double.MIN_VALUE;
        double d6 = Double.MIN_VALUE;
        for (Entity entity2 : this.world.getAllEntities()) {
            ItemFrameEntity itemFrameEntity;
            if (!(entity2 instanceof ItemFrameEntity) || (pair = FilledMapItem.getMapData((itemFrameEntity = (ItemFrameEntity)entity2).getDisplayedItem(), this.world)) == null) continue;
            double d7 = itemFrameEntity.getPosX();
            double d8 = itemFrameEntity.getPosY();
            double d9 = itemFrameEntity.getPosZ();
            d = Math.min(d, d7);
            d2 = Math.min(d2, d8);
            d3 = Math.min(d3, d9);
            d4 = Math.max(d4, d7);
            d5 = Math.max(d5, d8);
            d6 = Math.max(d6, d9);
        }
        double d10 = d4 - d;
        int n = 0;
        while ((double)n <= d10) {
            pair = this.getData(d + (double)(2 - n), d2 + 1.0, d3);
            if (pair != null) {
                MapData mapData = (MapData)pair.first;
                this.fillBufferedImage(graphics2D, mapData, n * 128, 0, (Integer)pair.second);
            }
            ++n;
        }
        n = 0;
        while ((double)n <= d10) {
            pair = this.getData(d + (double)(2 - n), d2, d3);
            if (pair != null) {
                MapData mapData = (MapData)pair.first;
                this.fillBufferedImage(graphics2D, mapData, n * 128, 128, (Integer)pair.second);
            }
            ++n;
        }
        ColorSpace colorSpace = ColorSpace.getInstance(1003);
        pair = new ColorConvertOp(colorSpace, null);
        BufferedImage bufferedImage2 = ((ColorConvertOp)((Object)pair)).filter(bufferedImage, null);
        RescaleOp rescaleOp = new RescaleOp(1.2f, 15.0f, null);
        rescaleOp.filter(bufferedImage2, bufferedImage2);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write((RenderedImage)bufferedImage2, "png", new File("test.png"));
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    private void lambda$handleChangeGameState$2() {
        this.client.player.connection.sendPacket(new CClientStatusPacket(CClientStatusPacket.State.PERFORM_RESPAWN));
    }

    private static void lambda$handleEntityEquipment$1(Entity entity2, com.mojang.datafixers.util.Pair pair) {
        entity2.setItemStackToSlot((EquipmentSlotType)((Object)pair.getFirst()), (ItemStack)pair.getSecond());
    }

    private void lambda$handleMultiBlockChange$0(int n, BlockPos blockPos, BlockState blockState) {
        this.world.setBlockState(blockPos, blockState, n);
    }
}

