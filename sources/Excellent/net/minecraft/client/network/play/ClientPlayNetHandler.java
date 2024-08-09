package net.minecraft.client.network.play;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import dev.excellent.client.module.impl.render.NoRender;
import dev.excellent.client.screen.mainmenu.MainMenu;
import dev.luvbeeq.baritone.Baritone;
import dev.luvbeeq.baritone.api.BaritoneAPI;
import dev.luvbeeq.baritone.api.IBaritone;
import dev.luvbeeq.baritone.api.event.events.ChunkEvent;
import dev.luvbeeq.baritone.api.event.events.type.EventState;
import dev.luvbeeq.baritone.cache.CachedChunk;
import i.gishreloaded.protection.annotation.Native;
import io.netty.buffer.Unpooled;
import net.minecraft.advancements.Advancement;
import net.minecraft.block.Block;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.*;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.screen.inventory.HorseInventoryScreen;
import net.minecraft.client.gui.toasts.RecipeToast;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.particle.ItemPickupParticle;
import net.minecraft.client.renderer.debug.*;
import net.minecraft.client.util.ClientRecipeBook;
import net.minecraft.client.util.IMutableSearchTree;
import net.minecraft.client.util.NBTQueryManager;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.Position;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.*;
import net.minecraft.entity.item.minecart.*;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.HorseInventoryContainer;
import net.minecraft.inventory.container.MerchantContainer;
import net.minecraft.item.*;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.Path;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.realms.DisconnectedRealmsScreen;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.scoreboard.*;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.tags.TagRegistryManager;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeContainer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraft.world.storage.MapData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

public class ClientPlayNetHandler implements IClientPlayNetHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ITextComponent field_243491_b = new TranslationTextComponent("disconnect.lost");

    /**
     * The NetworkManager instance used to communicate with the server, used to respond to various packets (primarilly
     * movement and plugin channel related ones) and check the status of the network connection externally
     */
    private final NetworkManager netManager;
    private final GameProfile profile;

    /**
     * Seems to be either null (integrated server) or an instance of either GuiMultiplayer (when connecting to a server)
     * or GuiScreenReamlsTOS (when connecting to MCO server)
     */
    private final Screen guiScreenServer;

    /**
     * Reference to the Minecraft instance, which many handler methods operate on
     */
    private Minecraft client;

    /**
     * Reference to the current ClientWorld instance, which many handler methods operate on
     */
    private ClientWorld world;
    private ClientWorld.ClientWorldInfo field_239161_g_;

    /**
     * True if the client has finished downloading terrain and may spawn. Set upon receipt of S08PacketPlayerPosLook,
     * reset upon respawning
     */
    private boolean doneLoadingTerrain;
    private final Map<UUID, NetworkPlayerInfo> playerInfoMap = Maps.newHashMap();
    private final ClientAdvancementManager advancementManager;
    private final ClientSuggestionProvider clientSuggestionProvider;
    private ITagCollectionSupplier networkTagManager = ITagCollectionSupplier.TAG_COLLECTION_SUPPLIER;
    private final NBTQueryManager nbtQueryManager = new NBTQueryManager(this);
    private int viewDistance = 3;

    /**
     * Just an ordinary random number generator, used to randomize audio pitch of item/orb pickup and randomize both
     * particlespawn offset and velocity
     */
    private final Random avRandomizer = new Random();
    private CommandDispatcher<ISuggestionProvider> commandDispatcher = new CommandDispatcher<>();
    private final RecipeManager recipeManager = new RecipeManager();
    private final UUID sessionId = UUID.randomUUID();
    private Set<RegistryKey<World>> field_239162_s_;
    private DynamicRegistries field_239163_t_ = DynamicRegistries.func_239770_b_();

    public ClientPlayNetHandler(Minecraft mcIn, Screen previousGuiScreen, NetworkManager networkManagerIn, GameProfile profileIn) {
        this.client = mcIn;
        this.guiScreenServer = previousGuiScreen;
        this.netManager = networkManagerIn;
        this.profile = profileIn;
        this.advancementManager = new ClientAdvancementManager(mcIn);
        this.clientSuggestionProvider = new ClientSuggestionProvider(this, mcIn);
    }

    public ClientSuggestionProvider getSuggestionProvider() {
        return this.clientSuggestionProvider;
    }

    /**
     * Clears the WorldClient instance associated with this NetHandlerPlayClient
     */
    public void cleanup() {
        this.world = null;
    }

    public RecipeManager getRecipeManager() {
        return this.recipeManager;
    }

    /**
     * Registers some server properties (gametype,hardcore-mode,terraintype,difficulty,player limit), creates a new
     * WorldClient and sets the player initial dimension
     */
    @Native
    public void handleJoinGame(SJoinGamePacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        this.client.playerController = new PlayerController(this.client, this);

        if (!this.netManager.isLocalChannel()) {
            TagRegistryManager.fetchTags();
        }

        ArrayList<RegistryKey<World>> arraylist = Lists.newArrayList(packetIn.func_240816_f_());
        Collections.shuffle(arraylist);
        this.field_239162_s_ = Sets.newLinkedHashSet(arraylist);
        this.field_239163_t_ = packetIn.func_240817_g_();
        RegistryKey<World> registrykey = packetIn.func_240819_i_();
        DimensionType dimensiontype = packetIn.func_244297_i();
        this.viewDistance = packetIn.getViewDistance();
        boolean flag = packetIn.func_240820_n_();
        boolean flag1 = packetIn.func_240821_o_();
        ClientWorld.ClientWorldInfo clientworld$clientworldinfo = new ClientWorld.ClientWorldInfo(Difficulty.NORMAL, packetIn.isHardcoreMode(), flag1);
        this.field_239161_g_ = clientworld$clientworldinfo;
        this.world = new ClientWorld(this, clientworld$clientworldinfo, registrykey, dimensiontype, this.viewDistance, this.client::getProfiler, this.client.worldRenderer, flag, packetIn.getHashedSeed());
        this.client.loadWorld(this.world);

        if (this.client.player == null) {
            this.client.player = this.client.playerController.createPlayer(this.world, new StatisticsManager(), new ClientRecipeBook());
            this.client.player.rotationYaw = -180.0F;

            if (this.client.getIntegratedServer() != null) {
                this.client.getIntegratedServer().setPlayerUuid(this.client.player.getUniqueID());
            }
        }

        this.client.debugRenderer.clear();
        this.client.player.preparePlayerToSpawn();
        int i = packetIn.getPlayerId();
        this.world.addPlayer(i, this.client.player);
        this.client.player.movementInput = new MovementInputFromOptions(this.client.gameSettings);
        this.client.playerController.setPlayerCapabilities(this.client.player);
        this.client.renderViewEntity = this.client.player;
        this.client.displayScreen(new DownloadTerrainScreen());
        this.client.player.setEntityId(i);
        this.client.player.setReducedDebug(packetIn.isReducedDebugInfo());
        this.client.player.setShowDeathScreen(packetIn.func_229743_k_());
        this.client.playerController.setGameType(packetIn.getGameType());
        this.client.playerController.func_241675_a_(packetIn.func_241786_f_());
        this.client.gameSettings.sendSettingsToServer();
        this.netManager.sendPacket(new CCustomPayloadPacket(CCustomPayloadPacket.BRAND, (new PacketBuffer(Unpooled.buffer())).writeString(ClientBrandRetriever.getClientModName())));
        this.client.getMinecraftGame().startGameSession();
    }

    /**
     * Spawns an instance of the objecttype indicated by the packet and sets its position and momentum
     */
    public void handleSpawnObject(SSpawnObjectPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        double d0 = packetIn.getX();
        double d1 = packetIn.getY();
        double d2 = packetIn.getZ();
        EntityType<?> entitytype = packetIn.getType();
        Entity entity;

        if (entitytype == EntityType.CHEST_MINECART) {
            entity = new ChestMinecartEntity(this.world, d0, d1, d2);
        } else if (entitytype == EntityType.FURNACE_MINECART) {
            entity = new FurnaceMinecartEntity(this.world, d0, d1, d2);
        } else if (entitytype == EntityType.TNT_MINECART) {
            entity = new TNTMinecartEntity(this.world, d0, d1, d2);
        } else if (entitytype == EntityType.SPAWNER_MINECART) {
            entity = new SpawnerMinecartEntity(this.world, d0, d1, d2);
        } else if (entitytype == EntityType.HOPPER_MINECART) {
            entity = new HopperMinecartEntity(this.world, d0, d1, d2);
        } else if (entitytype == EntityType.COMMAND_BLOCK_MINECART) {
            entity = new CommandBlockMinecartEntity(this.world, d0, d1, d2);
        } else if (entitytype == EntityType.MINECART) {
            entity = new MinecartEntity(this.world, d0, d1, d2);
        } else if (entitytype == EntityType.FISHING_BOBBER) {
            Entity entity1 = this.world.getEntityByID(packetIn.getData());

            if (entity1 instanceof PlayerEntity) {
                entity = new FishingBobberEntity(this.world, (PlayerEntity) entity1, d0, d1, d2);
            } else {
                entity = null;
            }
        } else if (entitytype == EntityType.ARROW) {
            entity = new ArrowEntity(this.world, d0, d1, d2);
            Entity entity2 = this.world.getEntityByID(packetIn.getData());

            if (entity2 != null) {
                ((AbstractArrowEntity) entity).setShooter(entity2);
            }
        } else if (entitytype == EntityType.SPECTRAL_ARROW) {
            entity = new SpectralArrowEntity(this.world, d0, d1, d2);
            Entity entity3 = this.world.getEntityByID(packetIn.getData());

            if (entity3 != null) {
                ((AbstractArrowEntity) entity).setShooter(entity3);
            }
        } else if (entitytype == EntityType.TRIDENT) {
            entity = new TridentEntity(this.world, d0, d1, d2);
            Entity entity4 = this.world.getEntityByID(packetIn.getData());

            if (entity4 != null) {
                ((AbstractArrowEntity) entity).setShooter(entity4);
            }
        } else if (entitytype == EntityType.SNOWBALL) {
            entity = new SnowballEntity(this.world, d0, d1, d2);
        } else if (entitytype == EntityType.LLAMA_SPIT) {
            entity = new LlamaSpitEntity(this.world, d0, d1, d2, packetIn.func_218693_g(), packetIn.func_218695_h(), packetIn.func_218692_i());
        } else if (entitytype == EntityType.ITEM_FRAME) {
            entity = new ItemFrameEntity(this.world, new BlockPos(d0, d1, d2), Direction.byIndex(packetIn.getData()));
        } else if (entitytype == EntityType.LEASH_KNOT) {
            entity = new LeashKnotEntity(this.world, new BlockPos(d0, d1, d2));
        } else if (entitytype == EntityType.ENDER_PEARL) {
            entity = new EnderPearlEntity(this.world, d0, d1, d2);
        } else if (entitytype == EntityType.EYE_OF_ENDER) {
            entity = new EyeOfEnderEntity(this.world, d0, d1, d2);
        } else if (entitytype == EntityType.FIREWORK_ROCKET) {
            entity = new FireworkRocketEntity(this.world, d0, d1, d2, ItemStack.EMPTY);
        } else if (entitytype == EntityType.FIREBALL) {
            entity = new FireballEntity(this.world, d0, d1, d2, packetIn.func_218693_g(), packetIn.func_218695_h(), packetIn.func_218692_i());
        } else if (entitytype == EntityType.DRAGON_FIREBALL) {
            entity = new DragonFireballEntity(this.world, d0, d1, d2, packetIn.func_218693_g(), packetIn.func_218695_h(), packetIn.func_218692_i());
        } else if (entitytype == EntityType.SMALL_FIREBALL) {
            entity = new SmallFireballEntity(this.world, d0, d1, d2, packetIn.func_218693_g(), packetIn.func_218695_h(), packetIn.func_218692_i());
        } else if (entitytype == EntityType.WITHER_SKULL) {
            entity = new WitherSkullEntity(this.world, d0, d1, d2, packetIn.func_218693_g(), packetIn.func_218695_h(), packetIn.func_218692_i());
        } else if (entitytype == EntityType.SHULKER_BULLET) {
            entity = new ShulkerBulletEntity(this.world, d0, d1, d2, packetIn.func_218693_g(), packetIn.func_218695_h(), packetIn.func_218692_i());
        } else if (entitytype == EntityType.EGG) {
            entity = new EggEntity(this.world, d0, d1, d2);
        } else if (entitytype == EntityType.EVOKER_FANGS) {
            entity = new EvokerFangsEntity(this.world, d0, d1, d2, 0.0F, 0, null);
        } else if (entitytype == EntityType.POTION) {
            entity = new PotionEntity(this.world, d0, d1, d2);
        } else if (entitytype == EntityType.EXPERIENCE_BOTTLE) {
            entity = new ExperienceBottleEntity(this.world, d0, d1, d2);
        } else if (entitytype == EntityType.BOAT) {
            entity = new BoatEntity(this.world, d0, d1, d2);
        } else if (entitytype == EntityType.TNT) {
            entity = new TNTEntity(this.world, d0, d1, d2, null);
        } else if (entitytype == EntityType.ARMOR_STAND) {
            entity = new ArmorStandEntity(this.world, d0, d1, d2);
        } else if (entitytype == EntityType.END_CRYSTAL) {
            entity = new EnderCrystalEntity(this.world, d0, d1, d2);
        } else if (entitytype == EntityType.ITEM) {
            entity = new ItemEntity(this.world, d0, d1, d2);
        } else if (entitytype == EntityType.FALLING_BLOCK) {
            entity = new FallingBlockEntity(this.world, d0, d1, d2, Block.getStateById(packetIn.getData()));
        } else if (entitytype == EntityType.AREA_EFFECT_CLOUD) {
            entity = new AreaEffectCloudEntity(this.world, d0, d1, d2);
        } else if (entitytype == EntityType.LIGHTNING_BOLT) {
            entity = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, this.world);
        } else {
            entity = null;
        }

        if (entity != null) {
            int i = packetIn.getEntityID();
            entity.setPacketCoordinates(d0, d1, d2);
            entity.moveForced(d0, d1, d2);
            entity.rotationPitch = (float) (packetIn.getPitch() * 360) / 256.0F;
            entity.rotationYaw = (float) (packetIn.getYaw() * 360) / 256.0F;
            entity.setEntityId(i);
            entity.setUniqueId(packetIn.getUniqueId());
            this.world.addEntity(i, entity);

            if (entity instanceof AbstractMinecartEntity) {
                this.client.getSoundHandler().play(new MinecartTickableSound((AbstractMinecartEntity) entity));
            }
        }
    }

    /**
     * Spawns an experience orb and sets its value (amount of XP)
     */
    public void handleSpawnExperienceOrb(SSpawnExperienceOrbPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        double d0 = packetIn.getX();
        double d1 = packetIn.getY();
        double d2 = packetIn.getZ();
        Entity entity = new ExperienceOrbEntity(this.world, d0, d1, d2, packetIn.getXPValue());
        entity.setPacketCoordinates(d0, d1, d2);
        entity.rotationYaw = 0.0F;
        entity.rotationPitch = 0.0F;
        entity.setEntityId(packetIn.getEntityID());
        this.world.addEntity(packetIn.getEntityID(), entity);
    }

    /**
     * Handles the spawning of a painting object
     */
    public void handleSpawnPainting(SSpawnPaintingPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        PaintingEntity paintingentity = new PaintingEntity(this.world, packetIn.getPosition(), packetIn.getFacing(), packetIn.getType());
        paintingentity.setEntityId(packetIn.getEntityID());
        paintingentity.setUniqueId(packetIn.getUniqueId());
        this.world.addEntity(packetIn.getEntityID(), paintingentity);
    }

    /**
     * Sets the velocity of the specified entity to the specified value
     */
    public void handleEntityVelocity(SEntityVelocityPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Entity entity = this.world.getEntityByID(packetIn.getEntityID());

        if (entity != null) {
            entity.setVelocity((double) packetIn.getMotionX() / 8000.0D, (double) packetIn.getMotionY() / 8000.0D, (double) packetIn.getMotionZ() / 8000.0D);
        }
    }

    /**
     * Invoked when the server registers new proximate objects in your watchlist or when objects in your watchlist have
     * changed -> Registers any changes locally
     */
    public void handleEntityMetadata(SEntityMetadataPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Entity entity = this.world.getEntityByID(packetIn.getEntityId());

        if (entity != null && packetIn.getDataManagerEntries() != null) {
            entity.getDataManager().setEntryValues(packetIn.getDataManagerEntries());
        }
    }

    /**
     * Handles the creation of a nearby player entity, sets the position and held item
     */
    public void handleSpawnPlayer(SSpawnPlayerPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        double d0 = packetIn.getX();
        double d1 = packetIn.getY();
        double d2 = packetIn.getZ();
        float f = (float) (packetIn.getYaw() * 360) / 256.0F;
        float f1 = (float) (packetIn.getPitch() * 360) / 256.0F;
        int i = packetIn.getEntityID();
        RemoteClientPlayerEntity remoteclientplayerentity = new RemoteClientPlayerEntity(this.client.world, this.getPlayerInfo(packetIn.getUniqueId()).getGameProfile());
        remoteclientplayerentity.setEntityId(i);
        remoteclientplayerentity.forceSetPosition(d0, d1, d2);
        remoteclientplayerentity.setPacketCoordinates(d0, d1, d2);
        remoteclientplayerentity.setPositionAndRotation(d0, d1, d2, f, f1);
        this.world.addPlayer(i, remoteclientplayerentity);
    }

    /**
     * Updates an entity's position and rotation as specified by the packet
     */
    public void handleEntityTeleport(SEntityTeleportPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Entity entity = this.world.getEntityByID(packetIn.getEntityId());

        if (entity != null) {
            double d0 = packetIn.getX();
            double d1 = packetIn.getY();
            double d2 = packetIn.getZ();
            entity.setPacketCoordinates(d0, d1, d2);

            if (!entity.canPassengerSteer()) {
                float f = (float) (packetIn.getYaw() * 360) / 256.0F;
                float f1 = (float) (packetIn.getPitch() * 360) / 256.0F;
                entity.setPositionAndRotationDirect(d0, d1, d2, f, f1, 3, true);
                entity.setOnGround(packetIn.isOnGround());
            }
        }
    }

    /**
     * Updates which hotbar slot of the player is currently selected
     */
    public void handleHeldItemChange(SHeldItemChangePacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);

        if (PlayerInventory.isHotbar(packetIn.getHeldItemHotbarIndex())) {
            this.client.player.inventory.currentItem = packetIn.getHeldItemHotbarIndex();
        }
    }

    /**
     * Updates the specified entity's position by the specified relative moment and absolute rotation. Note that
     * subclassing of the packet allows for the specification of a subset of this data (e.g. only rel. position, abs.
     * rotation or both).
     */
    public void handleEntityMovement(SEntityPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Entity entity = packetIn.getEntity(this.world);

        if (entity != null) {
            if (!entity.canPassengerSteer()) {
                if (packetIn.func_229745_h_()) {
                    Vector3d vector3d = packetIn.func_244300_a(entity.func_242274_V());
                    entity.setPacketCoordinates(vector3d);
                    float f = packetIn.isRotating() ? (float) (packetIn.getYaw() * 360) / 256.0F : entity.rotationYaw;
                    float f1 = packetIn.isRotating() ? (float) (packetIn.getPitch() * 360) / 256.0F : entity.rotationPitch;
                    entity.setPositionAndRotationDirect(vector3d.getX(), vector3d.getY(), vector3d.getZ(), f, f1, 3, false);
                } else if (packetIn.isRotating()) {
                    float f2 = (float) (packetIn.getYaw() * 360) / 256.0F;
                    float f3 = (float) (packetIn.getPitch() * 360) / 256.0F;
                    entity.setPositionAndRotationDirect(entity.getPosX(), entity.getPosY(), entity.getPosZ(), f2, f3, 3, false);
                }

                entity.setOnGround(packetIn.getOnGround());
            }
        }
    }

    /**
     * Updates the direction in which the specified entity is looking, normally this head rotation is independent of the
     * rotation of the entity itself
     */
    public void handleEntityHeadLook(SEntityHeadLookPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Entity entity = packetIn.getEntity(this.world);

        if (entity != null) {
            float f = (float) (packetIn.getYaw() * 360) / 256.0F;
            entity.setHeadRotation(f, 3);
        }
    }

    /**
     * Locally eliminates the entities. Invoked by the server when the items are in fact destroyed, or the player is no
     * longer registered as required to monitor them. The latter  happens when distance between the player and item
     * increases beyond a certain treshold (typically the viewing distance)
     */
    public void handleDestroyEntities(SDestroyEntitiesPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);

        for (int i = 0; i < packetIn.getEntityIDs().length; ++i) {
            int j = packetIn.getEntityIDs()[i];
            this.world.removeEntityFromWorld(j);
        }
    }

    public void handlePlayerPosLook(SPlayerPositionLookPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        PlayerEntity playerentity = this.client.player;
        Vector3d vector3d = playerentity == null ? Vector3d.ZERO : playerentity.getMotion();
        boolean flag = packetIn.getFlags().contains(SPlayerPositionLookPacket.Flags.X);
        boolean flag1 = packetIn.getFlags().contains(SPlayerPositionLookPacket.Flags.Y);
        boolean flag2 = packetIn.getFlags().contains(SPlayerPositionLookPacket.Flags.Z);
        double d0;
        double d1;

        if (playerentity == null) return;
        if (flag) {
            d0 = vector3d.getX();
            d1 = playerentity.getPosX() + packetIn.getX();
            playerentity.lastTickPosX += packetIn.getX();
        } else {
            d0 = 0.0D;
            d1 = packetIn.getX();
            playerentity.lastTickPosX = d1;
        }

        double d2;
        double d3;

        if (flag1) {
            d2 = vector3d.getY();
            d3 = playerentity.getPosY() + packetIn.getY();
            playerentity.lastTickPosY += packetIn.getY();
        } else {
            d2 = 0.0D;
            d3 = packetIn.getY();
            playerentity.lastTickPosY = d3;
        }

        double d4;
        double d5;

        if (flag2) {
            d4 = vector3d.getZ();
            d5 = playerentity.getPosZ() + packetIn.getZ();
            playerentity.lastTickPosZ += packetIn.getZ();
        } else {
            d4 = 0.0D;
            d5 = packetIn.getZ();
            playerentity.lastTickPosZ = d5;
        }

        if (playerentity.ticksExisted > 0 && playerentity.getRidingEntity() != null) {
            playerentity.dismount();
        }

        playerentity.setRawPosition(d1, d3, d5);
        playerentity.prevPosX = d1;
        playerentity.prevPosY = d3;
        playerentity.prevPosZ = d5;
        playerentity.setMotion(d0, d2, d4);
        float f = packetIn.getYaw();
        float f1 = packetIn.getPitch();

        if (packetIn.getFlags().contains(SPlayerPositionLookPacket.Flags.X_ROT)) {
            f1 += playerentity.rotationPitch;
        }

        if (packetIn.getFlags().contains(SPlayerPositionLookPacket.Flags.Y_ROT)) {
            f += playerentity.rotationYaw;
        }

        playerentity.setPositionAndRotation(d1, d3, d5, f, f1);
        this.netManager.sendPacket(new CConfirmTeleportPacket(packetIn.getTeleportId()));
        this.netManager.sendPacket(new CPlayerPacket.PositionRotationPacket(playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), playerentity.rotationYaw, playerentity.rotationPitch, false));

        if (!this.doneLoadingTerrain) {
            this.doneLoadingTerrain = true;
            this.client.displayScreen(null);
        }
    }

    /**
     * Received from the servers PlayerManager if between 1 and 64 blocks in a chunk are changed. If only one block
     * requires an update, the server sends S23PacketBlockChange and if 64 or more blocks are changed, the server sends
     * S21PacketChunkData
     */
    public void handleMultiBlockChange(SMultiBlockChangePacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        int i = 19 | (packetIn.func_244311_b() ? 128 : 0);
        packetIn.func_244310_a((p_243492_2_, p_243492_3_) ->
        {
            this.world.setBlockState(p_243492_2_, p_243492_3_, i);
        });
        if (!Baritone.settings().repackOnAnyBlockChange.value) {
            return;
        }
        ChunkPos[] chunkPos = new ChunkPos[1];
        packetIn.func_244310_a((pos, state) -> {
            if (CachedChunk.BLOCKS_TO_KEEP_TRACK_OF.contains(state.getBlock())) {
                chunkPos[0] = new ChunkPos(pos);
            }
        });
        if (chunkPos[0] == null) {
            return;
        }
        for (IBaritone ibaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            ClientPlayerEntity player = ibaritone.getPlayerContext().player();
            if (player != null && player.connection == this) {
                ibaritone.getGameEventHandler().onChunkEvent(
                        new ChunkEvent(
                                EventState.POST,
                                ChunkEvent.Type.POPULATE_FULL,
                                chunkPos[0].x,
                                chunkPos[0].z
                        )
                );
            }
        }
    }

    /**
     * Updates the specified chunk with the supplied data, marks it for re-rendering and lighting recalculation
     */
    public void handleChunkData(SChunkDataPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        int i = packetIn.getChunkX();
        int j = packetIn.getChunkZ();
        BiomeContainer biomecontainer = packetIn.func_244296_i() == null ? null : new BiomeContainer(this.field_239163_t_.getRegistry(Registry.BIOME_KEY), packetIn.func_244296_i());
        Chunk chunk = this.world.getChunkProvider().loadChunk(i, j, biomecontainer, packetIn.getReadBuffer(), packetIn.getHeightmapTags(), packetIn.getAvailableSections(), packetIn.isFullChunk());

        if (chunk != null && packetIn.isFullChunk()) {
            this.world.addEntitiesToChunk(chunk);
        }

        for (int k = 0; k < 16; ++k) {
            this.world.markSurroundingsForRerender(i, k, j);
        }

        for (CompoundNBT compoundnbt : packetIn.getTileEntityTags()) {
            BlockPos blockpos = new BlockPos(compoundnbt.getInt("x"), compoundnbt.getInt("y"), compoundnbt.getInt("z"));
            TileEntity tileentity = this.world.getTileEntity(blockpos);

            if (tileentity != null) {
                tileentity.read(this.world.getBlockState(blockpos), compoundnbt);
            }
        }
        for (IBaritone ibaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            ClientPlayerEntity player = ibaritone.getPlayerContext().player();
            if (player != null && player.connection == this) {
                ibaritone.getGameEventHandler().onChunkEvent(
                        new ChunkEvent(
                                EventState.POST,
                                packetIn.isFullChunk() ? ChunkEvent.Type.POPULATE_FULL : ChunkEvent.Type.POPULATE_PARTIAL,
                                packetIn.getChunkX(),
                                packetIn.getChunkZ()
                        )
                );
            }
        }
    }

    public void processChunkUnload(SUnloadChunkPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        for (IBaritone ibaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            ClientPlayerEntity player = ibaritone.getPlayerContext().player();
            if (player != null && player.connection == this) {
                ibaritone.getGameEventHandler().onChunkEvent(
                        new ChunkEvent(EventState.PRE, ChunkEvent.Type.UNLOAD, packetIn.getX(), packetIn.getZ())
                );
            }
        }
        int i = packetIn.getX();
        int j = packetIn.getZ();
        ClientChunkProvider clientchunkprovider = this.world.getChunkProvider();
        clientchunkprovider.unloadChunk(i, j);
        WorldLightManager worldlightmanager = clientchunkprovider.getLightManager();

        for (int k = 0; k < 16; ++k) {
            this.world.markSurroundingsForRerender(i, k, j);
            worldlightmanager.updateSectionStatus(SectionPos.of(i, k, j), true);
        }

        worldlightmanager.enableLightSources(new ChunkPos(i, j), false);

        for (IBaritone ibaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            ClientPlayerEntity player = ibaritone.getPlayerContext().player();
            if (player != null && player.connection == this) {
                ibaritone.getGameEventHandler().onChunkEvent(
                        new ChunkEvent(EventState.POST, ChunkEvent.Type.UNLOAD, packetIn.getX(), packetIn.getZ())
                );
            }
        }
    }

    /**
     * Updates the block and metadata and generates a blockupdate (and notify the clients)
     */
    public void handleBlockChange(SChangeBlockPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        this.world.invalidateRegionAndSetBlock(packetIn.getPos(), packetIn.getState());
        if (!Baritone.settings().repackOnAnyBlockChange.value) {
            return;
        }
        if (!CachedChunk.BLOCKS_TO_KEEP_TRACK_OF.contains(packetIn.getState().getBlock())) {
            return;
        }
        for (IBaritone ibaritone : BaritoneAPI.getProvider().getAllBaritones()) {
            ClientPlayerEntity player = ibaritone.getPlayerContext().player();
            if (player != null && player.connection == this) {
                ibaritone.getGameEventHandler().onChunkEvent(
                        new ChunkEvent(
                                EventState.POST,
                                ChunkEvent.Type.POPULATE_FULL,
                                packetIn.getPos().getX() >> 4,
                                packetIn.getPos().getZ() >> 4
                        )
                );
            }
        }
    }

    /**
     * Closes the network channel
     */
    public void handleDisconnect(SDisconnectPacket packetIn) {
        this.netManager.closeChannel(packetIn.getReason());
    }

    /**
     * Invoked when disconnecting, the parameter is a ChatComponent describing the reason for termination
     */
    public void onDisconnect(ITextComponent reason) {
        this.client.unloadWorld();

        if (this.guiScreenServer != null) {
            if (this.guiScreenServer instanceof RealmsScreen) {
                this.client.displayScreen(new DisconnectedRealmsScreen(this.guiScreenServer, field_243491_b, reason));
            } else {
                this.client.displayScreen(new DisconnectedScreen(this.guiScreenServer, field_243491_b, reason));
            }
        } else {
            this.client.displayScreen(new DisconnectedScreen(new MultiplayerScreen(new MainMenu()), field_243491_b, reason));
        }
    }

    public void sendPacket(IPacket<?> packetIn) {
        this.netManager.sendPacket(packetIn);
    }

    public void sendPacketWithoutEvent(IPacket<?> packetIn) {
        this.netManager.sendPacketWithoutEvent(packetIn);
    }

    public void handleCollectItem(SCollectItemPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Entity entity = this.world.getEntityByID(packetIn.getCollectedItemEntityID());
        LivingEntity livingentity = (LivingEntity) this.world.getEntityByID(packetIn.getEntityID());

        if (livingentity == null) {
            livingentity = this.client.player;
        }

        if (entity != null) {
            if (entity instanceof ExperienceOrbEntity) {
                this.world.playSound(entity.getPosX(), entity.getPosY(), entity.getPosZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.1F, (this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.35F + 0.9F, false);
            } else {
                this.world.playSound(entity.getPosX(), entity.getPosY(), entity.getPosZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, (this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 1.4F + 2.0F, false);
            }

            this.client.particles.addEffect(new ItemPickupParticle(this.client.getRenderManager(), this.client.getRenderTypeBuffers(), this.world, entity, livingentity));

            if (entity instanceof ItemEntity itementity) {
                ItemStack itemstack = itementity.getItem();
                itemstack.shrink(packetIn.getAmount());

                if (itemstack.isEmpty()) {
                    this.world.removeEntityFromWorld(packetIn.getCollectedItemEntityID());
                }
            } else {
                this.world.removeEntityFromWorld(packetIn.getCollectedItemEntityID());
            }
        }
    }

    /**
     * Prints a chatmessage in the chat GUI
     */
    public void handleChat(SChatPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        this.client.ingameGUI.func_238450_a_(packetIn.getType(), packetIn.getChatComponent(), packetIn.func_240810_e_());
    }

    /**
     * Renders a specified animation: Waking up a player, a living entity swinging its currently held item, being hurt
     * or receiving a critical hit by normal or magical means
     */
    public void handleAnimation(SAnimateHandPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);

        if (this.world == null) return;

        Entity entity = this.world.getEntityByID(packetIn.getEntityID());

        if (entity != null) {
            if (packetIn.getAnimationType() == 0) {
                LivingEntity livingentity = (LivingEntity) entity;
                livingentity.swingArm(Hand.MAIN_HAND);
            } else if (packetIn.getAnimationType() == 3) {
                LivingEntity livingentity1 = (LivingEntity) entity;
                livingentity1.swingArm(Hand.OFF_HAND);
            } else if (packetIn.getAnimationType() == 1) {
                entity.performHurtAnimation();
            } else if (packetIn.getAnimationType() == 2) {
                PlayerEntity playerentity = (PlayerEntity) entity;
                playerentity.stopSleepInBed(false, false);
            } else if (packetIn.getAnimationType() == 4) {
                this.client.particles.addParticleEmitter(entity, ParticleTypes.CRIT);
            } else if (packetIn.getAnimationType() == 5) {
                this.client.particles.addParticleEmitter(entity, ParticleTypes.ENCHANTED_HIT);
            }
        }
    }

    /**
     * Spawns the mob entity at the specified location, with the specified rotation, momentum and type. Updates the
     * entities Datawatchers with the entity metadata specified in the packet
     */
    public void handleSpawnMob(SSpawnMobPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        double d0 = packetIn.getX();
        double d1 = packetIn.getY();
        double d2 = packetIn.getZ();
        float f = (float) (packetIn.getYaw() * 360) / 256.0F;
        float f1 = (float) (packetIn.getPitch() * 360) / 256.0F;
        LivingEntity livingentity = (LivingEntity) EntityType.create(packetIn.getEntityType(), this.client.world);

        if (livingentity != null) {
            livingentity.setPacketCoordinates(d0, d1, d2);
            livingentity.renderYawOffset = (float) (packetIn.getHeadPitch() * 360) / 256.0F;
            livingentity.rotationYawHead = (float) (packetIn.getHeadPitch() * 360) / 256.0F;

            if (livingentity instanceof EnderDragonEntity) {
                EnderDragonPartEntity[] aenderdragonpartentity = ((EnderDragonEntity) livingentity).getDragonParts();

                for (int i = 0; i < aenderdragonpartentity.length; ++i) {
                    aenderdragonpartentity[i].setEntityId(i + packetIn.getEntityID());
                }
            }

            livingentity.setEntityId(packetIn.getEntityID());
            livingentity.setUniqueId(packetIn.getUniqueId());
            livingentity.setPositionAndRotation(d0, d1, d2, f, f1);
            livingentity.setMotion((float) packetIn.getVelocityX() / 8000.0F, (float) packetIn.getVelocityY() / 8000.0F, (float) packetIn.getVelocityZ() / 8000.0F);
            this.world.addEntity(packetIn.getEntityID(), livingentity);

            if (livingentity instanceof BeeEntity) {
                boolean flag = ((BeeEntity) livingentity).func_233678_J__();
                BeeSound beesound;

                if (flag) {
                    beesound = new BeeAngrySound((BeeEntity) livingentity);
                } else {
                    beesound = new BeeFlightSound((BeeEntity) livingentity);
                }

                this.client.getSoundHandler().playOnNextTick(beesound);
            }
        } else {
            LOGGER.warn("Skipping Entity with id {}", packetIn.getEntityType());
        }
    }

    public void handleTimeUpdate(SUpdateTimePacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        this.client.world.func_239134_a_(packetIn.getTotalWorldTime());
        this.client.world.setDayTime(packetIn.getWorldTime());
    }

    public void func_230488_a_(SWorldSpawnChangedPacket p_230488_1_) {
        PacketThreadUtil.checkThreadAndEnqueue(p_230488_1_, this, this.client);
        this.client.world.func_239136_a_(p_230488_1_.func_240832_b_(), p_230488_1_.func_244313_c());
    }

    public void handleSetPassengers(SSetPassengersPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Entity entity = this.world.getEntityByID(packetIn.getEntityId());

        if (entity == null) {
            LOGGER.warn("Received passengers for unknown entity");
        } else {
            boolean flag = entity.isRidingOrBeingRiddenBy(this.client.player);
            entity.removePassengers();

            for (int i : packetIn.getPassengerIds()) {
                Entity entity1 = this.world.getEntityByID(i);

                if (entity1 != null) {
                    entity1.startRiding(entity, true);

                    if (entity1 == this.client.player && !flag) {
                        this.client.ingameGUI.setOverlayMessage(new TranslationTextComponent("mount.onboard", this.client.gameSettings.keyBindSneak.func_238171_j_()), false);
                    }
                }
            }
        }
    }

    public void handleEntityAttach(SMountEntityPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Entity entity = this.world.getEntityByID(packetIn.getEntityId());

        if (entity instanceof MobEntity) {
            ((MobEntity) entity).setVehicleEntityId(packetIn.getVehicleEntityId());
        }
    }

    private static ItemStack getTotemItem(PlayerEntity player) {
        for (Hand hand : Hand.values()) {
            ItemStack itemstack = player.getHeldItem(hand);

            if (itemstack.getItem() == Items.TOTEM_OF_UNDYING) {
                return itemstack;
            }
        }

        return new ItemStack(Items.TOTEM_OF_UNDYING);
    }

    /**
     * Invokes the entities' handleUpdateHealth method which is implemented in LivingBase (hurt/death),
     * MinecartMobSpawner (spawn delay), FireworkRocket & MinecartTNT (explosion), IronGolem (throwing,...), Witch
     * (spawn particles), Zombie (villager transformation), Animal (breeding mode particles), Horse (breeding/smoke
     * particles), Sheep (...), Tameable (...), Villager (particles for breeding mode, angry and happy), Wolf (...)
     */
    public void handleEntityStatus(SEntityStatusPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Entity entity = packetIn.getEntity(this.world);

        if (entity != null) {
            if (packetIn.getOpCode() == 21) {
                this.client.getSoundHandler().play(new GuardianSound((GuardianEntity) entity));
            } else if (packetIn.getOpCode() == 35) {
                int i = 40;
                this.client.particles.emitParticleAtEntity(entity, ParticleTypes.TOTEM_OF_UNDYING, 30);
                this.world.playSound(entity.getPosX(), entity.getPosY(), entity.getPosZ(), SoundEvents.ITEM_TOTEM_USE, entity.getSoundCategory(), 1.0F, 1.0F, false);

                NoRender noRender = NoRender.singleton.get();
                if (!noRender.isEnabled() && noRender.getElements().isEnabled("Потеря тотема")) {
                    if (entity == this.client.player) {
                        this.client.gameRenderer.displayItemActivation(getTotemItem(this.client.player));
                    }
                }
            } else {
                entity.handleStatusUpdate(packetIn.getOpCode());
            }
        }
    }

    public void handleUpdateHealth(SUpdateHealthPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        this.client.player.setPlayerSPHealth(packetIn.getHealth());
        this.client.player.getFoodStats().setFoodLevel(packetIn.getFoodLevel());
        this.client.player.getFoodStats().setFoodSaturationLevel(packetIn.getSaturationLevel());
    }

    public void handleSetExperience(SSetExperiencePacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        this.client.player.setXPStats(packetIn.getExperienceBar(), packetIn.getTotalExperience(), packetIn.getLevel());
    }

    public void handleRespawn(SRespawnPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        RegistryKey<World> registrykey = packetIn.func_240827_c_();
        DimensionType dimensiontype = packetIn.func_244303_b();
        ClientPlayerEntity clientplayerentity = this.client.player;
        int i = clientplayerentity.getEntityId();
        this.doneLoadingTerrain = false;

        if (registrykey != clientplayerentity.world.getDimensionKey()) {
            Scoreboard scoreboard = this.world.getScoreboard();
            boolean flag = packetIn.func_240828_f_();
            boolean flag1 = packetIn.func_240829_g_();
            ClientWorld.ClientWorldInfo clientworld$clientworldinfo = new ClientWorld.ClientWorldInfo(this.field_239161_g_.getDifficulty(), this.field_239161_g_.isHardcore(), flag1);
            this.field_239161_g_ = clientworld$clientworldinfo;
            this.world = new ClientWorld(this, clientworld$clientworldinfo, registrykey, dimensiontype, this.viewDistance, this.client::getProfiler, this.client.worldRenderer, flag, packetIn.getHashedSeed());
            this.world.setScoreboard(scoreboard);
            this.client.loadWorld(this.world);
            this.client.displayScreen(new DownloadTerrainScreen());
        }

        this.world.removeAllEntities();
        String s = clientplayerentity.getServerBrand();
        this.client.renderViewEntity = null;
        ClientPlayerEntity clientplayerentity1 = this.client.playerController.createPlayer(this.world, clientplayerentity.getStats(), clientplayerentity.getRecipeBook(), clientplayerentity.isSneaking(), clientplayerentity.isSprinting());
        clientplayerentity1.setEntityId(i);
        this.client.player = clientplayerentity1;

        if (registrykey != clientplayerentity.world.getDimensionKey()) {
            this.client.getMusicTicker().stop();
        }

        this.client.renderViewEntity = clientplayerentity1;
        clientplayerentity1.getDataManager().setEntryValues(clientplayerentity.getDataManager().getAll());

        if (packetIn.func_240830_h_()) {
            clientplayerentity1.getAttributeManager().refreshOnRespawn(clientplayerentity.getAttributeManager());
        }

        clientplayerentity1.preparePlayerToSpawn();
        clientplayerentity1.setServerBrand(s);
        this.world.addPlayer(i, clientplayerentity1);
        clientplayerentity1.rotationYaw = -180.0F;
        clientplayerentity1.movementInput = new MovementInputFromOptions(this.client.gameSettings);
        this.client.playerController.setPlayerCapabilities(clientplayerentity1);
        clientplayerentity1.setReducedDebug(clientplayerentity.hasReducedDebug());
        clientplayerentity1.setShowDeathScreen(clientplayerentity.isShowDeathScreen());

        if (this.client.currentScreen instanceof DeathScreen) {
            this.client.displayScreen(null);
        }

        this.client.playerController.setGameType(packetIn.getGameType());
        this.client.playerController.func_241675_a_(packetIn.func_241788_f_());
    }

    /**
     * Initiates a new explosion (sound, particles, drop spawn) for the affected blocks indicated by the packet.
     */
    public void handleExplosion(SExplosionPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Explosion explosion = new Explosion(this.client.world, null, packetIn.getX(), packetIn.getY(), packetIn.getZ(), packetIn.getStrength(), packetIn.getAffectedBlockPositions());
        explosion.doExplosionB(true);
        this.client.player.setMotion(this.client.player.getMotion().add(packetIn.getMotionX(), packetIn.getMotionY(), packetIn.getMotionZ()));
    }

    public void handleOpenHorseWindow(SOpenHorseWindowPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Entity entity = this.world.getEntityByID(packetIn.func_218703_d());

        if (entity instanceof AbstractHorseEntity abstracthorseentity) {
            ClientPlayerEntity clientplayerentity = this.client.player;
            Inventory inventory = new Inventory(packetIn.func_218702_c());
            HorseInventoryContainer horseinventorycontainer = new HorseInventoryContainer(packetIn.func_218704_b(), clientplayerentity.inventory, inventory, abstracthorseentity);
            clientplayerentity.openContainer = horseinventorycontainer;
            this.client.displayScreen(new HorseInventoryScreen(horseinventorycontainer, clientplayerentity.inventory, abstracthorseentity));
        }
    }

    public void handleOpenWindowPacket(SOpenWindowPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        ScreenManager.openScreen(packetIn.getContainerType(), this.client, packetIn.getWindowId(), packetIn.getTitle());
    }

    /**
     * Handles pickin up an ItemStack or dropping one in your inventory or an open (non-creative) container
     */
    public void handleSetSlot(SSetSlotPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        PlayerEntity playerentity = this.client.player;
        ItemStack itemstack = packetIn.getStack();
        int i = packetIn.getSlot();
        this.client.getTutorial().handleSetSlot(itemstack);

        if (packetIn.getWindowId() == -1) {
            if (!(this.client.currentScreen instanceof CreativeScreen)) {
                playerentity.inventory.setItemStack(itemstack);
            }
        } else if (packetIn.getWindowId() == -2) {
            playerentity.inventory.setInventorySlotContents(i, itemstack);
        } else {
            boolean flag = false;

            if (this.client.currentScreen instanceof CreativeScreen creativescreen) {
                flag = creativescreen.getSelectedTabIndex() != ItemGroup.INVENTORY.getIndex();
            }

            if (packetIn.getWindowId() == 0 && packetIn.getSlot() >= 36 && i < 45) {
                if (!itemstack.isEmpty()) {
                    ItemStack itemstack1 = playerentity.container.getSlot(i).getStack();

                    if (itemstack1.isEmpty() || itemstack1.getCount() < itemstack.getCount()) {
                        itemstack.setAnimationsToGo(5);
                    }
                }

                playerentity.container.putStackInSlot(i, itemstack);
            } else if (packetIn.getWindowId() == playerentity.openContainer.windowId && (packetIn.getWindowId() != 0 || !flag)) {
                playerentity.openContainer.putStackInSlot(i, itemstack);
            }
        }
    }

    /**
     * Verifies that the server and client are synchronized with respect to the inventory/container opened by the player
     * and confirms if it is the case.
     */
    public void handleConfirmTransaction(SConfirmTransactionPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Container container = null;
        PlayerEntity playerentity = this.client.player;

        if (packetIn.getWindowId() == 0) {
            container = playerentity.container;
        } else if (packetIn.getWindowId() == playerentity.openContainer.windowId) {
            container = playerentity.openContainer;
        }

        if (container != null && !packetIn.wasAccepted()) {
            this.sendPacket(new CConfirmTransactionPacket(packetIn.getWindowId(), packetIn.getActionNumber(), true));
        }
    }

    /**
     * Handles the placement of a specified ItemStack in a specified container/inventory slot
     */
    public void handleWindowItems(SWindowItemsPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        PlayerEntity playerentity = this.client.player;

        if (packetIn.getWindowId() == 0) {
            playerentity.container.setAll(packetIn.getItemStacks());
        } else if (packetIn.getWindowId() == playerentity.openContainer.windowId) {
            playerentity.openContainer.setAll(packetIn.getItemStacks());
        }
    }

    /**
     * Creates a sign in the specified location if it didn't exist and opens the GUI to edit its text
     */
    public void handleSignEditorOpen(SOpenSignMenuPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        TileEntity tileentity = this.world.getTileEntity(packetIn.getSignPosition());

        if (!(tileentity instanceof SignTileEntity)) {
            tileentity = new SignTileEntity();
            tileentity.setWorldAndPos(this.world, packetIn.getSignPosition());
        }

        this.client.player.openSignEditor((SignTileEntity) tileentity);
    }

    /**
     * Updates the NBTTagCompound metadata of instances of the following entitytypes: Mob spawners, command blocks,
     * beacons, skulls, flowerpot
     */
    public void handleUpdateTileEntity(SUpdateTileEntityPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        BlockPos blockpos = packetIn.getPos();
        TileEntity tileentity = this.client.world.getTileEntity(blockpos);
        int i = packetIn.getTileEntityType();
        boolean flag = i == 2 && tileentity instanceof CommandBlockTileEntity;

        if (i == 1 && tileentity instanceof MobSpawnerTileEntity || flag || i == 3 && tileentity instanceof BeaconTileEntity || i == 4 && tileentity instanceof SkullTileEntity || i == 6 && tileentity instanceof BannerTileEntity || i == 7 && tileentity instanceof StructureBlockTileEntity || i == 8 && tileentity instanceof EndGatewayTileEntity || i == 9 && tileentity instanceof SignTileEntity || i == 11 && tileentity instanceof BedTileEntity || i == 5 && tileentity instanceof ConduitTileEntity || i == 12 && tileentity instanceof JigsawTileEntity || i == 13 && tileentity instanceof CampfireTileEntity || i == 14 && tileentity instanceof BeehiveTileEntity) {
            tileentity.read(this.client.world.getBlockState(blockpos), packetIn.getNbtCompound());
        }

        if (flag && this.client.currentScreen instanceof CommandBlockScreen) {
            ((CommandBlockScreen) this.client.currentScreen).updateGui();
        }
    }

    /**
     * Sets the progressbar of the opened window to the specified value
     */
    public void handleWindowProperty(SWindowPropertyPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        PlayerEntity playerentity = this.client.player;

        if (playerentity.openContainer != null && playerentity.openContainer.windowId == packetIn.getWindowId()) {
            playerentity.openContainer.updateProgressBar(packetIn.getProperty(), packetIn.getValue());
        }
    }

    public void handleEntityEquipment(SEntityEquipmentPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Entity entity = this.world.getEntityByID(packetIn.getEntityID());

        if (entity != null) {
            packetIn.func_241790_c_().forEach((p_241664_1_) ->
            {
                entity.setItemStackToSlot(p_241664_1_.getFirst(), p_241664_1_.getSecond());
            });
        }
    }

    /**
     * Resets the ItemStack held in hand and closes the window that is opened
     */
    public void handleCloseWindow(SCloseWindowPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        this.client.player.closeScreenAndDropStack();
    }

    /**
     * Triggers Block.onBlockEventReceived, which is implemented in BlockPistonBase for extension/retraction, BlockNote
     * for setting the instrument (including audiovisual feedback) and in BlockContainer to set the number of players
     * accessing a (Ender)Chest
     */
    public void handleBlockAction(SBlockActionPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        this.client.world.addBlockEvent(packetIn.getBlockPosition(), packetIn.getBlockType(), packetIn.getData1(), packetIn.getData2());
    }

    /**
     * Updates all registered IWorldAccess instances with destroyBlockInWorldPartially
     */
    public void handleBlockBreakAnim(SAnimateBlockBreakPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        this.client.world.sendBlockBreakProgress(packetIn.getBreakerId(), packetIn.getPosition(), packetIn.getProgress());
    }

    public void handleChangeGameState(SChangeGameStatePacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        PlayerEntity playerentity = this.client.player;
        SChangeGameStatePacket.State schangegamestatepacket$state = packetIn.func_241776_b_();
        float f = packetIn.getValue();
        int i = MathHelper.floor(f + 0.5F);

        if (schangegamestatepacket$state == SChangeGameStatePacket.field_241764_a_) {
            playerentity.sendStatusMessage(new TranslationTextComponent("block.minecraft.spawn.not_valid"), false);
        } else if (schangegamestatepacket$state == SChangeGameStatePacket.field_241765_b_) {
            this.world.getWorldInfo().setRaining(true);
            this.world.setRainStrength(0.0F);
        } else if (schangegamestatepacket$state == SChangeGameStatePacket.field_241766_c_) {
            this.world.getWorldInfo().setRaining(false);
            this.world.setRainStrength(1.0F);
        } else if (schangegamestatepacket$state == SChangeGameStatePacket.field_241767_d_) {
            this.client.playerController.setGameType(GameType.getByID(i));
        } else if (schangegamestatepacket$state == SChangeGameStatePacket.field_241768_e_) {
            if (i == 0) {
                this.client.player.connection.sendPacket(new CClientStatusPacket(CClientStatusPacket.State.PERFORM_RESPAWN));
                this.client.displayScreen(new DownloadTerrainScreen());
            } else if (i == 1) {
                this.client.displayScreen(new WinGameScreen(true, () ->
                {
                    this.client.player.connection.sendPacket(new CClientStatusPacket(CClientStatusPacket.State.PERFORM_RESPAWN));
                }));
            }
        } else if (schangegamestatepacket$state == SChangeGameStatePacket.field_241769_f_) {
            GameSettings gamesettings = this.client.gameSettings;

            if (f == 0.0F) {
                this.client.displayScreen(new DemoScreen());
            } else if (f == 101.0F) {
                this.client.ingameGUI.getChatGUI().printChatMessage(new TranslationTextComponent("demo.help.movement", gamesettings.keyBindForward.func_238171_j_(), gamesettings.keyBindLeft.func_238171_j_(), gamesettings.keyBindBack.func_238171_j_(), gamesettings.keyBindRight.func_238171_j_()));
            } else if (f == 102.0F) {
                this.client.ingameGUI.getChatGUI().printChatMessage(new TranslationTextComponent("demo.help.jump", gamesettings.keyBindJump.func_238171_j_()));
            } else if (f == 103.0F) {
                this.client.ingameGUI.getChatGUI().printChatMessage(new TranslationTextComponent("demo.help.inventory", gamesettings.keyBindInventory.func_238171_j_()));
            } else if (f == 104.0F) {
                this.client.ingameGUI.getChatGUI().printChatMessage(new TranslationTextComponent("demo.day.6", gamesettings.keyBindScreenshot.func_238171_j_()));
            }
        } else if (schangegamestatepacket$state == SChangeGameStatePacket.field_241770_g_) {
            this.world.playSound(playerentity, playerentity.getPosX(), playerentity.getPosYEye(), playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 0.18F, 0.45F);
        } else if (schangegamestatepacket$state == SChangeGameStatePacket.field_241771_h_) {
            this.world.setRainStrength(f);
        } else if (schangegamestatepacket$state == SChangeGameStatePacket.field_241772_i_) {
            this.world.setThunderStrength(f);
        } else if (schangegamestatepacket$state == SChangeGameStatePacket.field_241773_j_) {
            this.world.playSound(playerentity, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_PUFFER_FISH_STING, SoundCategory.NEUTRAL, 1.0F, 1.0F);
        } else if (schangegamestatepacket$state == SChangeGameStatePacket.field_241774_k_) {
            this.world.addParticle(ParticleTypes.ELDER_GUARDIAN, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), 0.0D, 0.0D, 0.0D);

            if (i == 1) {
                this.world.playSound(playerentity, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE, SoundCategory.HOSTILE, 1.0F, 1.0F);
            }
        } else if (schangegamestatepacket$state == SChangeGameStatePacket.field_241775_l_) {
            this.client.player.setShowDeathScreen(f == 0.0F);
        }
    }

    /**
     * Updates the worlds MapStorage with the specified MapData for the specified map-identifier and invokes a
     * MapItemRenderer for it
     */
    public void handleMaps(SMapDataPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        MapItemRenderer mapitemrenderer = this.client.gameRenderer.getMapItemRenderer();
        String s = FilledMapItem.getMapName(packetIn.getMapId());
        MapData mapdata = this.client.world.getMapData(s);

        if (mapdata == null) {
            mapdata = new MapData(s);

            if (mapitemrenderer.getMapInstanceIfExists(s) != null) {
                MapData mapdata1 = mapitemrenderer.getData(mapitemrenderer.getMapInstanceIfExists(s));

                if (mapdata1 != null) {
                    mapdata = mapdata1;
                }
            }

            this.client.world.registerMapData(mapdata);
        }

        packetIn.setMapdataTo(mapdata);
        mapitemrenderer.updateMapTexture(mapdata);
    }

    public void handleEffect(SPlaySoundEventPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);

        if (packetIn.isSoundServerwide()) {
            this.client.world.playBroadcastSound(packetIn.getSoundType(), packetIn.getSoundPos(), packetIn.getSoundData());
        } else {
            this.client.world.playEvent(packetIn.getSoundType(), packetIn.getSoundPos(), packetIn.getSoundData());
        }
    }

    public void handleAdvancementInfo(SAdvancementInfoPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        this.advancementManager.read(packetIn);
    }

    public void handleSelectAdvancementsTab(SSelectAdvancementsTabPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        ResourceLocation resourcelocation = packetIn.getTab();

        if (resourcelocation == null) {
            this.advancementManager.setSelectedTab(null, false);
        } else {
            Advancement advancement = this.advancementManager.getAdvancementList().getAdvancement(resourcelocation);
            this.advancementManager.setSelectedTab(advancement, false);
        }
    }

    public void handleCommandList(SCommandListPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        this.commandDispatcher = new CommandDispatcher<>(packetIn.getRoot());
    }

    public void handleStopSound(SStopSoundPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        this.client.getSoundHandler().stop(packetIn.getName(), packetIn.getCategory());
    }

    /**
     * This method is only called for manual tab-completion (the {@link
     * net.minecraft.command.arguments.SuggestionProviders#ASK_SERVER minecraft:ask_server} suggestion provider).
     */
    public void handleTabComplete(STabCompletePacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        this.clientSuggestionProvider.handleResponse(packetIn.getTransactionId(), packetIn.getSuggestions());
    }

    public void handleUpdateRecipes(SUpdateRecipesPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        this.recipeManager.deserializeRecipes(packetIn.getRecipes());
        IMutableSearchTree<RecipeList> imutablesearchtree = this.client.getSearchTree(SearchTreeManager.RECIPES);
        imutablesearchtree.clear();
        ClientRecipeBook clientrecipebook = this.client.player.getRecipeBook();
        clientrecipebook.func_243196_a(this.recipeManager.getRecipes());
        clientrecipebook.getRecipes().forEach(imutablesearchtree::func_217872_a);
        imutablesearchtree.recalculate();
    }

    public void handlePlayerLook(SPlayerLookPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Vector3d vector3d = packetIn.getTargetPosition(this.world);

        if (vector3d != null) {
            this.client.player.lookAt(packetIn.getSourceAnchor(), vector3d);
        }
    }

    public void handleNBTQueryResponse(SQueryNBTResponsePacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);

        if (!this.nbtQueryManager.handleResponse(packetIn.getTransactionId(), packetIn.getTag())) {
            LOGGER.debug("Got unhandled response to tag query {}", packetIn.getTransactionId());
        }
    }

    /**
     * Updates the players statistics or achievements
     */
    public void handleStatistics(SStatisticsPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);

        for (Entry<Stat<?>, Integer> entry : packetIn.getStatisticMap().entrySet()) {
            Stat<?> stat = entry.getKey();
            int i = entry.getValue();
            this.client.player.getStats().setValue(this.client.player, stat, i);
        }

        if (this.client.currentScreen instanceof IProgressMeter) {
            ((IProgressMeter) this.client.currentScreen).onStatsUpdated();
        }
    }

    public void handleRecipeBook(SRecipeBookPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        ClientRecipeBook clientrecipebook = this.client.player.getRecipeBook();
        clientrecipebook.func_242140_a(packetIn.func_244302_d());
        SRecipeBookPacket.State srecipebookpacket$state = packetIn.getState();

        switch (srecipebookpacket$state) {
            case REMOVE:
                for (ResourceLocation resourcelocation3 : packetIn.getRecipes()) {
                    this.recipeManager.getRecipe(resourcelocation3).ifPresent(clientrecipebook::lock);
                }

                break;

            case INIT:
                for (ResourceLocation resourcelocation1 : packetIn.getRecipes()) {
                    this.recipeManager.getRecipe(resourcelocation1).ifPresent(clientrecipebook::unlock);
                }

                for (ResourceLocation resourcelocation2 : packetIn.getDisplayedRecipes()) {
                    this.recipeManager.getRecipe(resourcelocation2).ifPresent(clientrecipebook::markNew);
                }

                break;

            case ADD:
                for (ResourceLocation resourcelocation : packetIn.getRecipes()) {
                    this.recipeManager.getRecipe(resourcelocation).ifPresent((p_217278_2_) ->
                    {
                        clientrecipebook.unlock(p_217278_2_);
                        clientrecipebook.markNew(p_217278_2_);
                        RecipeToast.addOrUpdate(this.client.getToastGui(), p_217278_2_);
                    });
                }
        }

        clientrecipebook.getRecipes().forEach((p_199527_1_) ->
        {
            p_199527_1_.updateKnownRecipes(clientrecipebook);
        });

        if (this.client.currentScreen instanceof IRecipeShownListener) {
            ((IRecipeShownListener) this.client.currentScreen).recipesUpdated();
        }
    }

    public void handleEntityEffect(SPlayEntityEffectPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Entity entity = this.world.getEntityByID(packetIn.getEntityId());

        if (entity instanceof LivingEntity) {
            Effect effect = Effect.get(packetIn.getEffectId());

            if (effect != null) {
                EffectInstance effectinstance = new EffectInstance(effect, packetIn.getDuration(), packetIn.getAmplifier(), packetIn.getIsAmbient(), packetIn.doesShowParticles(), packetIn.shouldShowIcon());
                effectinstance.setPotionDurationMax(packetIn.isMaxDuration());
                ((LivingEntity) entity).func_233646_e_(effectinstance);
            }
        }
    }

    public void handleTags(STagsListPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        ITagCollectionSupplier itagcollectionsupplier = packetIn.getTags();
        Multimap<ResourceLocation, ResourceLocation> multimap = TagRegistryManager.validateTags(itagcollectionsupplier);

        if (!multimap.isEmpty()) {
            LOGGER.warn("Incomplete server tags, disconnecting. Missing: {}", multimap);
            this.netManager.closeChannel(new TranslationTextComponent("multiplayer.disconnect.missing_tags"));
        } else {
            this.networkTagManager = itagcollectionsupplier;

            if (!this.netManager.isLocalChannel()) {
                itagcollectionsupplier.updateTags();
            }

            this.client.getSearchTree(SearchTreeManager.TAGS).recalculate();
        }
    }

    public void handleCombatEvent(SCombatPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);

        if (packetIn.eventType == SCombatPacket.Event.ENTITY_DIED) {
            Entity entity = this.world.getEntityByID(packetIn.playerId);

            if (entity == this.client.player) {
                if (this.client.player.isShowDeathScreen()) {
                    for (IBaritone ibaritone : BaritoneAPI.getProvider().getAllBaritones()) {
                        ClientPlayerEntity player = ibaritone.getPlayerContext().player();
                        if (player != null && player.connection == this) {
                            ibaritone.getGameEventHandler().onPlayerDeath();
                        }
                    }
                    this.client.displayScreen(new DeathScreen(packetIn.deathMessage, this.world.getWorldInfo().isHardcore()));
                } else {
                    this.client.player.respawnPlayer();
                }
            }
        }
    }

    public void handleServerDifficulty(SServerDifficultyPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        this.field_239161_g_.setDifficulty(packetIn.getDifficulty());
        this.field_239161_g_.setDifficultyLocked(packetIn.isDifficultyLocked());
    }

    public void handleCamera(SCameraPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Entity entity = packetIn.getEntity(this.world);

        if (entity != null) {
            this.client.setRenderViewEntity(entity);
        }
    }

    public void handleWorldBorder(SWorldBorderPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        packetIn.apply(this.world.getWorldBorder());
    }

    public void handleTitle(STitlePacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        STitlePacket.Type stitlepacket$type = packetIn.getType();
        ITextComponent itextcomponent = null;
        ITextComponent itextcomponent1 = null;
        ITextComponent itextcomponent2 = packetIn.getMessage() != null ? packetIn.getMessage() : StringTextComponent.EMPTY;

        switch (stitlepacket$type) {
            case TITLE:
                itextcomponent = itextcomponent2;
                break;

            case SUBTITLE:
                itextcomponent1 = itextcomponent2;
                break;

            case ACTIONBAR:
                this.client.ingameGUI.setOverlayMessage(itextcomponent2, false);
                return;

            case RESET:
                this.client.ingameGUI.func_238452_a_(null, null, -1, -1, -1);
                this.client.ingameGUI.setDefaultTitlesTimes();
                return;
        }

        this.client.ingameGUI.func_238452_a_(itextcomponent, itextcomponent1, packetIn.getFadeInTime(), packetIn.getDisplayTime(), packetIn.getFadeOutTime());
    }

    public void handlePlayerListHeaderFooter(SPlayerListHeaderFooterPacket packetIn) {
        this.client.ingameGUI.getTabList().setHeader(packetIn.getHeader().getString().isEmpty() ? null : packetIn.getHeader());
        this.client.ingameGUI.getTabList().setFooter(packetIn.getFooter().getString().isEmpty() ? null : packetIn.getFooter());
    }

    public void handleRemoveEntityEffect(SRemoveEntityEffectPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Entity entity = packetIn.getEntity(this.world);

        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).removeActivePotionEffect(packetIn.getPotion());
        }
    }

    public void handlePlayerListItem(SPlayerListItemPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);

        for (SPlayerListItemPacket.AddPlayerData splayerlistitempacket$addplayerdata : packetIn.getEntries()) {
            if (packetIn.getAction() == SPlayerListItemPacket.Action.REMOVE_PLAYER) {
                this.client.func_244599_aA().func_244649_d(splayerlistitempacket$addplayerdata.getProfile().getId());
                this.playerInfoMap.remove(splayerlistitempacket$addplayerdata.getProfile().getId());
            } else {
                NetworkPlayerInfo networkplayerinfo = this.playerInfoMap.get(splayerlistitempacket$addplayerdata.getProfile().getId());

                if (packetIn.getAction() == SPlayerListItemPacket.Action.ADD_PLAYER) {
                    networkplayerinfo = new NetworkPlayerInfo(splayerlistitempacket$addplayerdata);
                    this.playerInfoMap.put(networkplayerinfo.getGameProfile().getId(), networkplayerinfo);
                    this.client.func_244599_aA().func_244645_a(networkplayerinfo);
                }

                if (networkplayerinfo != null) {
                    switch (packetIn.getAction()) {
                        case ADD_PLAYER:
                            networkplayerinfo.setGameType(splayerlistitempacket$addplayerdata.getGameMode());
                            networkplayerinfo.setResponseTime(splayerlistitempacket$addplayerdata.getPing());
                            networkplayerinfo.setDisplayName(splayerlistitempacket$addplayerdata.getDisplayName());
                            break;

                        case UPDATE_GAME_MODE:
                            networkplayerinfo.setGameType(splayerlistitempacket$addplayerdata.getGameMode());
                            break;

                        case UPDATE_LATENCY:
                            networkplayerinfo.setResponseTime(splayerlistitempacket$addplayerdata.getPing());
                            break;

                        case UPDATE_DISPLAY_NAME:
                            networkplayerinfo.setDisplayName(splayerlistitempacket$addplayerdata.getDisplayName());
                    }
                }
            }
        }
    }

    public void handleKeepAlive(SKeepAlivePacket packetIn) {
        this.sendPacket(new CKeepAlivePacket(packetIn.getId()));
    }

    public void handlePlayerAbilities(SPlayerAbilitiesPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        PlayerEntity playerentity = this.client.player;
        playerentity.abilities.isFlying = packetIn.isFlying();
        playerentity.abilities.isCreativeMode = packetIn.isCreativeMode();
        playerentity.abilities.disableDamage = packetIn.isInvulnerable();
        playerentity.abilities.allowFlying = packetIn.isAllowFlying();
        playerentity.abilities.setFlySpeed(packetIn.getFlySpeed());
        playerentity.abilities.setWalkSpeed(packetIn.getWalkSpeed());
    }

    public void handleSoundEffect(SPlaySoundEffectPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        this.client.world.playSound(this.client.player, packetIn.getX(), packetIn.getY(), packetIn.getZ(), packetIn.getSound(), packetIn.getCategory(), packetIn.getVolume(), packetIn.getPitch());
    }

    public void handleSpawnMovingSoundEffect(SSpawnMovingSoundEffectPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Entity entity = this.world.getEntityByID(packetIn.func_218762_d());

        if (entity != null) {
            this.client.world.playMovingSound(this.client.player, entity, packetIn.func_218763_b(), packetIn.func_218760_c(), packetIn.func_218764_e(), packetIn.func_218761_f());
        }
    }

    public void handleCustomSound(SPlaySoundPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        this.client.getSoundHandler().play(new SimpleSound(packetIn.getSoundName(), packetIn.getCategory(), packetIn.getVolume(), packetIn.getPitch(), false, 0, ISound.AttenuationType.LINEAR, packetIn.getX(), packetIn.getY(), packetIn.getZ(), false));
    }

    public void handleResourcePack(SSendResourcePackPacket packetIn) {
        String s = packetIn.getURL();
        String s1 = packetIn.getHash();

        if (this.validateResourcePackUrl(s)) {
            if (s.startsWith("level://")) {
                String s2 = URLDecoder.decode(s.substring("level://".length()), StandardCharsets.UTF_8);
                File file1 = new File(this.client.gameDir, "saves");
                File file2 = new File(file1, s2);

                if (file2.isFile()) {
                    this.sendResourcePackStatus(CResourcePackStatusPacket.Action.ACCEPTED);
                    CompletableFuture<?> completablefuture = this.client.getPackFinder().setServerPack(file2, IPackNameDecorator.WORLD);
                    this.handlePackFuture(completablefuture);
                    return;
                }

                this.sendResourcePackStatus(CResourcePackStatusPacket.Action.FAILED_DOWNLOAD);
            } else {
                ServerData serverdata = this.client.getCurrentServerData();

                if (serverdata != null && serverdata.getResourceMode() == ServerData.ServerResourceMode.ENABLED) {
                    this.sendResourcePackStatus(CResourcePackStatusPacket.Action.ACCEPTED);
                    this.handlePackFuture(this.client.getPackFinder().downloadResourcePack(s, s1));
                } else if (serverdata != null && serverdata.getResourceMode() != ServerData.ServerResourceMode.PROMPT) {
                    this.sendResourcePackStatus(CResourcePackStatusPacket.Action.DECLINED);
                } else {
                    this.client.execute(() ->
                    {
                        this.client.displayScreen(new ConfirmScreen((p_217274_3_) -> {
                            this.client = Minecraft.getInstance();
                            ServerData serverdata1 = this.client.getCurrentServerData();

                            if (p_217274_3_) {
                                if (serverdata1 != null) {
                                    serverdata1.setResourceMode(ServerData.ServerResourceMode.ENABLED);
                                }

                                this.sendResourcePackStatus(CResourcePackStatusPacket.Action.ACCEPTED);
                                this.handlePackFuture(this.client.getPackFinder().downloadResourcePack(s, s1));
                            } else {
                                if (serverdata1 != null) {
                                    serverdata1.setResourceMode(ServerData.ServerResourceMode.DISABLED);
                                }

                                this.sendResourcePackStatus(CResourcePackStatusPacket.Action.DECLINED);
                            }

                            if (serverdata1 != null) {
                                ServerList.saveSingleServer(serverdata1);
                            }
                            this.client.displayScreen(null);
                        }, new TranslationTextComponent("multiplayer.texturePrompt.line1"), new TranslationTextComponent("multiplayer.texturePrompt.line2")));
                    });
                }
            }
        }
    }

    private boolean validateResourcePackUrl(String url) {
        try {
            URI uri = new URI(url);
            String s = uri.getScheme();
            boolean flag = "level".equals(s);

            if (!"http".equals(s) && !"https".equals(s) && !flag) {
                throw new URISyntaxException(url, "Wrong protocol");
            } else if (!flag || !url.contains("..") && url.endsWith("/resources.zip")) {
                return true;
            } else {
                throw new URISyntaxException(url, "Invalid levelstorage resourcepack path");
            }
        } catch (URISyntaxException urisyntaxexception) {
            this.sendResourcePackStatus(CResourcePackStatusPacket.Action.FAILED_DOWNLOAD);
            return false;
        }
    }

    private void handlePackFuture(CompletableFuture<?> futureIn) {
        futureIn.thenRun(() ->
        {
            this.sendResourcePackStatus(CResourcePackStatusPacket.Action.SUCCESSFULLY_LOADED);
        }).exceptionally((p_217276_1_) ->
        {
            this.sendResourcePackStatus(CResourcePackStatusPacket.Action.FAILED_DOWNLOAD);
            return null;
        });
    }

    private void sendResourcePackStatus(CResourcePackStatusPacket.Action action) {
        this.netManager.sendPacket(new CResourcePackStatusPacket(action));
    }

    public void handleUpdateBossInfo(SUpdateBossInfoPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        this.client.ingameGUI.getBossOverlay().read(packetIn);
    }

    public void handleCooldown(SCooldownPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);

        if (packetIn.getTicks() == 0) {
            this.client.player.getCooldownTracker().removeCooldown(packetIn.getItem());
        } else {
            this.client.player.getCooldownTracker().setCooldown(packetIn.getItem(), packetIn.getTicks());
        }
    }

    public void handleMoveVehicle(SMoveVehiclePacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Entity entity = this.client.player.getLowestRidingEntity();

        if (entity != this.client.player && entity.canPassengerSteer()) {
            entity.setPositionAndRotation(packetIn.getX(), packetIn.getY(), packetIn.getZ(), packetIn.getYaw(), packetIn.getPitch());
            this.netManager.sendPacket(new CMoveVehiclePacket(entity));
        }
    }

    public void handleOpenBookPacket(SOpenBookWindowPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        ItemStack itemstack = this.client.player.getHeldItem(packetIn.getHand());

        if (itemstack.getItem() == Items.WRITTEN_BOOK) {
            this.client.displayScreen(new ReadBookScreen(new ReadBookScreen.WrittenBookInfo(itemstack)));
        }
    }

    /**
     * Handles packets that have room for a channel specification. Vanilla implemented channels are "MC|TrList" to
     * acquire a MerchantRecipeList trades for a villager merchant, "MC|Brand" which sets the server brand? on the
     * player instance and finally "MC|RPack" which the server uses to communicate the identifier of the default server
     * resourcepack for the client to load.
     */
    public void handleCustomPayload(SCustomPayloadPlayPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        ResourceLocation resourcelocation = packetIn.getChannelName();
        PacketBuffer packetbuffer = null;

        try {
            packetbuffer = packetIn.getBufferData();

            if (SCustomPayloadPlayPacket.BRAND.equals(resourcelocation)) {
                this.client.player.setServerBrand(packetbuffer.readString(32767));
            } else if (SCustomPayloadPlayPacket.DEBUG_PATH.equals(resourcelocation)) {
                int i = packetbuffer.readInt();
                float f = packetbuffer.readFloat();
                Path path = Path.read(packetbuffer);
                this.client.debugRenderer.pathfinding.addPath(i, path, f);
            } else if (SCustomPayloadPlayPacket.DEBUG_NEIGHBORS_UPDATE.equals(resourcelocation)) {
                long l1 = packetbuffer.readVarLong();
                BlockPos blockpos9 = packetbuffer.readBlockPos();
                ((NeighborsUpdateDebugRenderer) this.client.debugRenderer.neighborsUpdate).addUpdate(l1, blockpos9);
            } else if (SCustomPayloadPlayPacket.DEBUG_CAVES.equals(resourcelocation)) {
                BlockPos blockpos2 = packetbuffer.readBlockPos();
                int k2 = packetbuffer.readInt();
                List<BlockPos> list1 = Lists.newArrayList();
                List<Float> list = Lists.newArrayList();

                for (int j = 0; j < k2; ++j) {
                    list1.add(packetbuffer.readBlockPos());
                    list.add(packetbuffer.readFloat());
                }

                this.client.debugRenderer.cave.addCave(blockpos2, list1, list);
            } else if (SCustomPayloadPlayPacket.DEBUG_STRUCTURES.equals(resourcelocation)) {
                DimensionType dimensiontype = this.field_239163_t_.func_230520_a_().getOrDefault(packetbuffer.readResourceLocation());
                MutableBoundingBox mutableboundingbox = new MutableBoundingBox(packetbuffer.readInt(), packetbuffer.readInt(), packetbuffer.readInt(), packetbuffer.readInt(), packetbuffer.readInt(), packetbuffer.readInt());
                int i4 = packetbuffer.readInt();
                List<MutableBoundingBox> list2 = Lists.newArrayList();
                List<Boolean> list4 = Lists.newArrayList();

                for (int k = 0; k < i4; ++k) {
                    list2.add(new MutableBoundingBox(packetbuffer.readInt(), packetbuffer.readInt(), packetbuffer.readInt(), packetbuffer.readInt(), packetbuffer.readInt(), packetbuffer.readInt()));
                    list4.add(packetbuffer.readBoolean());
                }

                this.client.debugRenderer.structure.func_223454_a(mutableboundingbox, list2, list4, dimensiontype);
            } else if (SCustomPayloadPlayPacket.DEBUG_WORLDGEN_ATTEMPT.equals(resourcelocation)) {
                ((WorldGenAttemptsDebugRenderer) this.client.debugRenderer.worldGenAttempts).addAttempt(packetbuffer.readBlockPos(), packetbuffer.readFloat(), packetbuffer.readFloat(), packetbuffer.readFloat(), packetbuffer.readFloat(), packetbuffer.readFloat());
            } else if (SCustomPayloadPlayPacket.DEBUG_VILLAGE_SECTIONS.equals(resourcelocation)) {
                int i2 = packetbuffer.readInt();

                for (int l2 = 0; l2 < i2; ++l2) {
                    this.client.debugRenderer.field_239372_n_.func_239378_a_(packetbuffer.readSectionPos());
                }

                int i3 = packetbuffer.readInt();

                for (int j4 = 0; j4 < i3; ++j4) {
                    this.client.debugRenderer.field_239372_n_.func_239379_b_(packetbuffer.readSectionPos());
                }
            } else if (SCustomPayloadPlayPacket.DEBUG_POI_ADDED.equals(resourcelocation)) {
                BlockPos blockpos3 = packetbuffer.readBlockPos();
                String s8 = packetbuffer.readString();
                int k4 = packetbuffer.readInt();
                PointOfInterestDebugRenderer.POIInfo pointofinterestdebugrenderer$poiinfo = new PointOfInterestDebugRenderer.POIInfo(blockpos3, s8, k4);
                this.client.debugRenderer.field_239371_m_.func_217691_a(pointofinterestdebugrenderer$poiinfo);
            } else if (SCustomPayloadPlayPacket.DEBUG_POI_REMOVED.equals(resourcelocation)) {
                BlockPos blockpos4 = packetbuffer.readBlockPos();
                this.client.debugRenderer.field_239371_m_.func_217698_a(blockpos4);
            } else if (SCustomPayloadPlayPacket.DEBUG_POI_TICKET_COUNT.equals(resourcelocation)) {
                BlockPos blockpos5 = packetbuffer.readBlockPos();
                int j3 = packetbuffer.readInt();
                this.client.debugRenderer.field_239371_m_.func_217706_a(blockpos5, j3);
            } else if (SCustomPayloadPlayPacket.DEBUG_GOAL_SELECTOR.equals(resourcelocation)) {
                BlockPos blockpos6 = packetbuffer.readBlockPos();
                int k3 = packetbuffer.readInt();
                int l4 = packetbuffer.readInt();
                List<EntityAIDebugRenderer.Entry> list3 = Lists.newArrayList();

                for (int i6 = 0; i6 < l4; ++i6) {
                    int j6 = packetbuffer.readInt();
                    boolean flag = packetbuffer.readBoolean();
                    String s = packetbuffer.readString(255);
                    list3.add(new EntityAIDebugRenderer.Entry(blockpos6, j6, s, flag));
                }

                this.client.debugRenderer.field_217742_n.func_217682_a(k3, list3);
            } else if (SCustomPayloadPlayPacket.DEBUG_RAIDS.equals(resourcelocation)) {
                int j2 = packetbuffer.readInt();
                Collection<BlockPos> collection = Lists.newArrayList();

                for (int i5 = 0; i5 < j2; ++i5) {
                    collection.add(packetbuffer.readBlockPos());
                }

                this.client.debugRenderer.field_222927_n.func_222906_a(collection);
            } else if (SCustomPayloadPlayPacket.DEBUG_BRAIN.equals(resourcelocation)) {
                double d0 = packetbuffer.readDouble();
                double d2 = packetbuffer.readDouble();
                double d4 = packetbuffer.readDouble();
                IPosition iposition = new Position(d0, d2, d4);
                UUID uuid = packetbuffer.readUniqueId();
                int l = packetbuffer.readInt();
                String s1 = packetbuffer.readString();
                String s2 = packetbuffer.readString();
                int i1 = packetbuffer.readInt();
                float f1 = packetbuffer.readFloat();
                float f2 = packetbuffer.readFloat();
                String s3 = packetbuffer.readString();
                boolean flag1 = packetbuffer.readBoolean();
                Path path1;

                if (flag1) {
                    path1 = Path.read(packetbuffer);
                } else {
                    path1 = null;
                }

                boolean flag2 = packetbuffer.readBoolean();
                PointOfInterestDebugRenderer.BrainInfo pointofinterestdebugrenderer$braininfo = new PointOfInterestDebugRenderer.BrainInfo(uuid, l, s1, s2, i1, f1, f2, iposition, s3, path1, flag2);
                int j1 = packetbuffer.readInt();

                for (int k1 = 0; k1 < j1; ++k1) {
                    String s4 = packetbuffer.readString();
                    pointofinterestdebugrenderer$braininfo.field_217751_e.add(s4);
                }

                int i8 = packetbuffer.readInt();

                for (int j8 = 0; j8 < i8; ++j8) {
                    String s5 = packetbuffer.readString();
                    pointofinterestdebugrenderer$braininfo.field_217752_f.add(s5);
                }

                int k8 = packetbuffer.readInt();

                for (int l8 = 0; l8 < k8; ++l8) {
                    String s6 = packetbuffer.readString();
                    pointofinterestdebugrenderer$braininfo.field_217753_g.add(s6);
                }

                int i9 = packetbuffer.readInt();

                for (int j9 = 0; j9 < i9; ++j9) {
                    BlockPos blockpos = packetbuffer.readBlockPos();
                    pointofinterestdebugrenderer$braininfo.field_217754_h.add(blockpos);
                }

                int k9 = packetbuffer.readInt();

                for (int l9 = 0; l9 < k9; ++l9) {
                    BlockPos blockpos1 = packetbuffer.readBlockPos();
                    pointofinterestdebugrenderer$braininfo.field_239360_q_.add(blockpos1);
                }

                int i10 = packetbuffer.readInt();

                for (int j10 = 0; j10 < i10; ++j10) {
                    String s7 = packetbuffer.readString();
                    pointofinterestdebugrenderer$braininfo.field_223457_m.add(s7);
                }

                this.client.debugRenderer.field_239371_m_.func_217692_a(pointofinterestdebugrenderer$braininfo);
            } else if (SCustomPayloadPlayPacket.field_229727_m_.equals(resourcelocation)) {
                double d1 = packetbuffer.readDouble();
                double d3 = packetbuffer.readDouble();
                double d5 = packetbuffer.readDouble();
                IPosition iposition1 = new Position(d1, d3, d5);
                UUID uuid1 = packetbuffer.readUniqueId();
                int k6 = packetbuffer.readInt();
                boolean flag4 = packetbuffer.readBoolean();
                BlockPos blockpos10 = null;

                if (flag4) {
                    blockpos10 = packetbuffer.readBlockPos();
                }

                boolean flag5 = packetbuffer.readBoolean();
                BlockPos blockpos11 = null;

                if (flag5) {
                    blockpos11 = packetbuffer.readBlockPos();
                }

                int l6 = packetbuffer.readInt();
                boolean flag6 = packetbuffer.readBoolean();
                Path path2 = null;

                if (flag6) {
                    path2 = Path.read(packetbuffer);
                }

                BeeDebugRenderer.Bee beedebugrenderer$bee = new BeeDebugRenderer.Bee(uuid1, k6, iposition1, path2, blockpos10, blockpos11, l6);
                int i7 = packetbuffer.readInt();

                for (int j7 = 0; j7 < i7; ++j7) {
                    String s11 = packetbuffer.readString();
                    beedebugrenderer$bee.field_229005_h_.add(s11);
                }

                int k7 = packetbuffer.readInt();

                for (int l7 = 0; l7 < k7; ++l7) {
                    BlockPos blockpos12 = packetbuffer.readBlockPos();
                    beedebugrenderer$bee.field_229006_i_.add(blockpos12);
                }

                this.client.debugRenderer.field_229017_n_.func_228964_a_(beedebugrenderer$bee);
            } else if (SCustomPayloadPlayPacket.field_229728_n_.equals(resourcelocation)) {
                BlockPos blockpos7 = packetbuffer.readBlockPos();
                String s9 = packetbuffer.readString();
                int j5 = packetbuffer.readInt();
                int k5 = packetbuffer.readInt();
                boolean flag3 = packetbuffer.readBoolean();
                BeeDebugRenderer.Hive beedebugrenderer$hive = new BeeDebugRenderer.Hive(blockpos7, s9, j5, k5, flag3, this.world.getGameTime());
                this.client.debugRenderer.field_229017_n_.func_228966_a_(beedebugrenderer$hive);
            } else if (SCustomPayloadPlayPacket.field_229730_p_.equals(resourcelocation)) {
                this.client.debugRenderer.field_229018_q_.clear();
            } else if (SCustomPayloadPlayPacket.field_229729_o_.equals(resourcelocation)) {
                BlockPos blockpos8 = packetbuffer.readBlockPos();
                int l3 = packetbuffer.readInt();
                String s10 = packetbuffer.readString();
                int l5 = packetbuffer.readInt();
                this.client.debugRenderer.field_229018_q_.func_229022_a_(blockpos8, l3, s10, l5);
            } else {
                LOGGER.warn("Unknown custom packed identifier: {}", resourcelocation);
            }
        } finally {
            if (packetbuffer != null) {
                packetbuffer.release();
            }
        }
    }

    /**
     * May create a scoreboard objective, remove an objective from the scoreboard or update an objectives' displayname
     */
    public void handleScoreboardObjective(SScoreboardObjectivePacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Scoreboard scoreboard = this.world.getScoreboard();
        String s = packetIn.getObjectiveName();

        if (packetIn.getAction() == 0) {
            scoreboard.addObjective(s, ScoreCriteria.DUMMY, packetIn.getDisplayName(), packetIn.getRenderType());
        } else if (scoreboard.hasObjective(s)) {
            ScoreObjective scoreobjective = scoreboard.getObjective(s);

            if (packetIn.getAction() == 1) {
                scoreboard.removeObjective(scoreobjective);
            } else if (packetIn.getAction() == 2) {
                scoreobjective.setRenderType(packetIn.getRenderType());
                scoreobjective.setDisplayName(packetIn.getDisplayName());
            }
        }
    }

    /**
     * Either updates the score with a specified value or removes the score for an objective
     */
    public void handleUpdateScore(SUpdateScorePacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Scoreboard scoreboard = this.world.getScoreboard();
        String s = packetIn.getObjectiveName();

        switch (packetIn.getAction()) {
            case CHANGE:
                ScoreObjective scoreobjective = scoreboard.getOrCreateObjective(s);
                Score score = scoreboard.getOrCreateScore(packetIn.getPlayerName(), scoreobjective);
                score.setScorePoints(packetIn.getScoreValue());
                break;

            case REMOVE:
                scoreboard.removeObjectiveFromEntity(packetIn.getPlayerName(), scoreboard.getObjective(s));
        }
    }

    /**
     * Removes or sets the ScoreObjective to be displayed at a particular scoreboard position (list, sidebar, below
     * name)
     */
    public void handleDisplayObjective(SDisplayObjectivePacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Scoreboard scoreboard = this.world.getScoreboard();
        String s = packetIn.getName();
        ScoreObjective scoreobjective = s == null ? null : scoreboard.getOrCreateObjective(s);
        scoreboard.setObjectiveInDisplaySlot(packetIn.getPosition(), scoreobjective);
    }

    /**
     * Updates a team managed by the scoreboard: Create/Remove the team registration, Register/Remove the player-team-
     * memberships, Set team displayname/prefix/suffix and/or whether friendly fire is enabled
     */
    public void handleTeams(STeamsPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Scoreboard scoreboard = this.world.getScoreboard();
        ScorePlayerTeam scoreplayerteam;

        if (packetIn.getAction() == 0) {
            scoreplayerteam = scoreboard.createTeam(packetIn.getName());
        } else {
            scoreplayerteam = scoreboard.getTeam(packetIn.getName());
        }

        if (packetIn.getAction() == 0 || packetIn.getAction() == 2) {
            scoreplayerteam.setDisplayName(packetIn.getDisplayName());
            scoreplayerteam.setColor(packetIn.getColor());
            scoreplayerteam.setFriendlyFlags(packetIn.getFriendlyFlags());
            Team.Visible team$visible = Team.Visible.getByName(packetIn.getNameTagVisibility());

            if (team$visible != null) {
                scoreplayerteam.setNameTagVisibility(team$visible);
            }

            Team.CollisionRule team$collisionrule = Team.CollisionRule.getByName(packetIn.getCollisionRule());

            if (team$collisionrule != null) {
                scoreplayerteam.setCollisionRule(team$collisionrule);
            }

            scoreplayerteam.setPrefix(packetIn.getPrefix());
            scoreplayerteam.setSuffix(packetIn.getSuffix());
        }

        if (packetIn.getAction() == 0 || packetIn.getAction() == 3) {
            for (String s : packetIn.getPlayers()) {
                scoreboard.addPlayerToTeam(s, scoreplayerteam);
            }
        }

        if (packetIn.getAction() == 4) {
            for (String s1 : packetIn.getPlayers()) {
                scoreboard.removePlayerFromTeam(s1, scoreplayerteam);
            }
        }

        if (packetIn.getAction() == 1) {
            scoreboard.removeTeam(scoreplayerteam);
        }
    }

    /**
     * Spawns a specified number of particles at the specified location with a randomized displacement according to
     * specified bounds
     */
    public void handleParticles(SSpawnParticlePacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);

        if (packetIn.getParticleCount() == 0) {
            double d0 = packetIn.getParticleSpeed() * packetIn.getXOffset();
            double d2 = packetIn.getParticleSpeed() * packetIn.getYOffset();
            double d4 = packetIn.getParticleSpeed() * packetIn.getZOffset();

            try {
                this.world.addParticle(packetIn.getParticle(), packetIn.isLongDistance(), packetIn.getXCoordinate(), packetIn.getYCoordinate(), packetIn.getZCoordinate(), d0, d2, d4);
            } catch (Throwable throwable1) {
                LOGGER.warn("Could not spawn particle effect {}", packetIn.getParticle());
            }
        } else {
            for (int i = 0; i < packetIn.getParticleCount(); ++i) {
                double d1 = this.avRandomizer.nextGaussian() * (double) packetIn.getXOffset();
                double d3 = this.avRandomizer.nextGaussian() * (double) packetIn.getYOffset();
                double d5 = this.avRandomizer.nextGaussian() * (double) packetIn.getZOffset();
                double d6 = this.avRandomizer.nextGaussian() * (double) packetIn.getParticleSpeed();
                double d7 = this.avRandomizer.nextGaussian() * (double) packetIn.getParticleSpeed();
                double d8 = this.avRandomizer.nextGaussian() * (double) packetIn.getParticleSpeed();

                try {
                    this.world.addParticle(packetIn.getParticle(), packetIn.isLongDistance(), packetIn.getXCoordinate() + d1, packetIn.getYCoordinate() + d3, packetIn.getZCoordinate() + d5, d6, d7, d8);
                } catch (Throwable throwable) {
                    LOGGER.warn("Could not spawn particle effect {}", packetIn.getParticle());
                    return;
                }
            }
        }
    }

    /**
     * Updates en entity's attributes and their respective modifiers, which are used for speed bonusses (player
     * sprinting, animals fleeing, baby speed), weapon/tool attackDamage, hostiles followRange randomization, zombie
     * maxHealth and knockback resistance as well as reinforcement spawning chance.
     */
    public void handleEntityProperties(SEntityPropertiesPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Entity entity = this.world.getEntityByID(packetIn.getEntityId());

        if (entity != null) {
            if (!(entity instanceof LivingEntity)) {
                throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + entity + ")");
            } else {
                AttributeModifierManager attributemodifiermanager = ((LivingEntity) entity).getAttributeManager();

                for (SEntityPropertiesPacket.Snapshot sentitypropertiespacket$snapshot : packetIn.getSnapshots()) {
                    ModifiableAttributeInstance modifiableattributeinstance = attributemodifiermanager.createInstanceIfAbsent(sentitypropertiespacket$snapshot.func_240834_a_());

                    if (modifiableattributeinstance == null) {
                        LOGGER.warn("Entity {} does not have attribute {}", entity, Registry.ATTRIBUTE.getKey(sentitypropertiespacket$snapshot.func_240834_a_()));
                    } else {
                        modifiableattributeinstance.setBaseValue(sentitypropertiespacket$snapshot.getBaseValue());
                        modifiableattributeinstance.removeAllModifiers();

                        for (AttributeModifier attributemodifier : sentitypropertiespacket$snapshot.getModifiers()) {
                            modifiableattributeinstance.applyNonPersistentModifier(attributemodifier);
                        }
                    }
                }
            }
        }
    }

    public void handlePlaceGhostRecipe(SPlaceGhostRecipePacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Container container = this.client.player.openContainer;

        if (container.windowId == packetIn.getWindowId() && container.getCanCraft(this.client.player)) {
            this.recipeManager.getRecipe(packetIn.getRecipeId()).ifPresent((p_241665_2_) ->
            {
                if (this.client.currentScreen instanceof IRecipeShownListener) {
                    RecipeBookGui recipebookgui = ((IRecipeShownListener) this.client.currentScreen).getRecipeGui();
                    recipebookgui.setupGhostRecipe(p_241665_2_, container.inventorySlots);
                }
            });
        }
    }

    public void handleUpdateLight(SUpdateLightPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        int i = packetIn.getChunkX();
        int j = packetIn.getChunkZ();
        WorldLightManager worldlightmanager = this.world.getChunkProvider().getLightManager();
        int k = packetIn.getSkyLightUpdateMask();
        int l = packetIn.getSkyLightResetMask();
        Iterator<byte[]> iterator = packetIn.getSkyLightData().iterator();
        this.setLightData(i, j, worldlightmanager, LightType.SKY, k, l, iterator, packetIn.func_241784_j_());
        int i1 = packetIn.getBlockLightUpdateMask();
        int j1 = packetIn.getBlockLightResetMask();
        Iterator<byte[]> iterator1 = packetIn.getBlockLightData().iterator();
        this.setLightData(i, j, worldlightmanager, LightType.BLOCK, i1, j1, iterator1, packetIn.func_241784_j_());
    }

    public void handleMerchantOffers(SMerchantOffersPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        Container container = this.client.player.openContainer;

        if (packetIn.getContainerId() == container.windowId && container instanceof MerchantContainer) {
            ((MerchantContainer) container).setClientSideOffers(new MerchantOffers(packetIn.getOffers().write()));
            ((MerchantContainer) container).setXp(packetIn.getExp());
            ((MerchantContainer) container).setMerchantLevel(packetIn.getLevel());
            ((MerchantContainer) container).func_217045_a(packetIn.func_218735_f());
            ((MerchantContainer) container).func_223431_b(packetIn.func_223477_g());
        }
    }

    public void handleUpdateViewDistancePacket(SUpdateViewDistancePacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        this.viewDistance = packetIn.getViewDistance();
        this.world.getChunkProvider().setViewDistance(packetIn.getViewDistance());
    }

    public void handleChunkPositionPacket(SUpdateChunkPositionPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        this.world.getChunkProvider().setCenter(packetIn.func_218755_b(), packetIn.func_218754_c());
    }

    public void handleAcknowledgePlayerDigging(SPlayerDiggingPacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
        this.client.playerController.acknowledgePlayerDiggingReceived(this.world, packetIn.getPosition(), packetIn.getBlockState(), packetIn.getAction(), packetIn.wasSuccessful());
    }

    private void setLightData(int chunkX, int chunkZ, WorldLightManager lightManager, LightType type, int p_217284_5_, int p_217284_6_, Iterator<byte[]> p_217284_7_, boolean p_217284_8_) {
        for (int i = 0; i < 18; ++i) {
            int j = -1 + i;
            boolean flag = (p_217284_5_ & 1 << i) != 0;
            boolean flag1 = (p_217284_6_ & 1 << i) != 0;

            if (flag || flag1) {
                lightManager.setData(type, SectionPos.of(chunkX, j, chunkZ), flag ? new NibbleArray(p_217284_7_.next().clone()) : new NibbleArray(), p_217284_8_);
                this.world.markSurroundingsForRerender(chunkX, j, chunkZ);
            }
        }
    }

    /**
     * Returns this the NetworkManager instance registered with this NetworkHandlerPlayClient
     */
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
    public NetworkPlayerInfo getPlayerInfo(UUID uniqueId) {
        return this.playerInfoMap.get(uniqueId);
    }

    @Nullable

    /**
     * Gets the client's description information about another player on the server.
     */
    public NetworkPlayerInfo getPlayerInfo(String name) {
        for (NetworkPlayerInfo networkplayerinfo : this.playerInfoMap.values()) {
            if (networkplayerinfo.getGameProfile().getName().equals(name)) {
                return networkplayerinfo;
            }
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
}
