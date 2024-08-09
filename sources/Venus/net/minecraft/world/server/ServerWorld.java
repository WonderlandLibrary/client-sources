/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.server;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEventData;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.INPC;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.merchant.IReputationTracking;
import net.minecraft.entity.merchant.IReputationType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.passive.horse.SkeletonHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SAnimateBlockBreakPacket;
import net.minecraft.network.play.server.SBlockActionPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SEntityStatusPacket;
import net.minecraft.network.play.server.SExplosionPacket;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.network.play.server.SPlaySoundEventPacket;
import net.minecraft.network.play.server.SSpawnMovingSoundEffectPacket;
import net.minecraft.network.play.server.SSpawnParticlePacket;
import net.minecraft.network.play.server.SWorldSpawnChangedPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.profiler.IProfiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.CSVWriter;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.DimensionType;
import net.minecraft.world.Explosion;
import net.minecraft.world.ExplosionContext;
import net.minecraft.world.ForcedChunksSaveData;
import net.minecraft.world.GameRules;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.ITickList;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.chunk.listener.IChunkStatusListener;
import net.minecraft.world.end.DragonFightManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.raid.RaidManager;
import net.minecraft.world.server.ChunkManager;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerTickList;
import net.minecraft.world.server.TicketType;
import net.minecraft.world.spawner.ISpecialSpawner;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.IServerWorldInfo;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapIdTracker;
import net.minecraft.world.storage.SaveFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ServerWorld
extends World
implements ISeedReader {
    public static final BlockPos field_241108_a_ = new BlockPos(100, 50, 0);
    private static final Logger LOGGER = LogManager.getLogger();
    private final Int2ObjectMap<Entity> entitiesById = new Int2ObjectLinkedOpenHashMap<Entity>();
    private final Map<UUID, Entity> entitiesByUuid = Maps.newHashMap();
    private final Queue<Entity> entitiesToAdd = Queues.newArrayDeque();
    private final List<ServerPlayerEntity> players = Lists.newArrayList();
    private final ServerChunkProvider field_241102_C_;
    boolean tickingEntities;
    private final MinecraftServer server;
    private final IServerWorldInfo field_241103_E_;
    public boolean disableLevelSaving;
    private boolean allPlayersSleeping;
    private int updateEntityTick;
    private final Teleporter worldTeleporter;
    private final ServerTickList<Block> pendingBlockTicks = new ServerTickList<Block>(this, ServerWorld::lambda$new$0, Registry.BLOCK::getKey, this::tickBlock);
    private final ServerTickList<Fluid> pendingFluidTicks = new ServerTickList<Fluid>(this, ServerWorld::lambda$new$1, Registry.FLUID::getKey, this::tickFluid);
    private final Set<PathNavigator> navigations = Sets.newHashSet();
    protected final RaidManager raids;
    private final ObjectLinkedOpenHashSet<BlockEventData> blockEventQueue = new ObjectLinkedOpenHashSet();
    private boolean insideTick;
    private final List<ISpecialSpawner> field_241104_N_;
    @Nullable
    private final DragonFightManager field_241105_O_;
    private final StructureManager field_241106_P_;
    private final boolean field_241107_Q_;

    public ServerWorld(MinecraftServer minecraftServer, Executor executor, SaveFormat.LevelSave levelSave, IServerWorldInfo iServerWorldInfo, RegistryKey<World> registryKey, DimensionType dimensionType, IChunkStatusListener iChunkStatusListener, ChunkGenerator chunkGenerator, boolean bl, long l, List<ISpecialSpawner> list, boolean bl2) {
        super(iServerWorldInfo, registryKey, dimensionType, minecraftServer::getProfiler, false, bl, l);
        this.field_241107_Q_ = bl2;
        this.server = minecraftServer;
        this.field_241104_N_ = list;
        this.field_241103_E_ = iServerWorldInfo;
        this.field_241102_C_ = new ServerChunkProvider(this, levelSave, minecraftServer.getDataFixer(), minecraftServer.func_240792_aT_(), executor, chunkGenerator, minecraftServer.getPlayerList().getViewDistance(), minecraftServer.func_230540_aS_(), iChunkStatusListener, () -> ServerWorld.lambda$new$2(minecraftServer));
        this.worldTeleporter = new Teleporter(this);
        this.calculateInitialSkylight();
        this.calculateInitialWeather();
        this.getWorldBorder().setSize(minecraftServer.getMaxWorldSize());
        this.raids = this.getSavedData().getOrCreate(this::lambda$new$3, RaidManager.func_234620_a_(this.getDimensionType()));
        if (!minecraftServer.isSinglePlayer()) {
            iServerWorldInfo.setGameType(minecraftServer.getGameType());
        }
        this.field_241106_P_ = new StructureManager(this, minecraftServer.func_240793_aU_().getDimensionGeneratorSettings());
        this.field_241105_O_ = this.getDimensionType().doesHasDragonFight() ? new DragonFightManager(this, minecraftServer.func_240793_aU_().getDimensionGeneratorSettings().getSeed(), minecraftServer.func_240793_aU_().getDragonFightData()) : null;
    }

    public void func_241113_a_(int n, int n2, boolean bl, boolean bl2) {
        this.field_241103_E_.setClearWeatherTime(n);
        this.field_241103_E_.setRainTime(n2);
        this.field_241103_E_.setThunderTime(n2);
        this.field_241103_E_.setRaining(bl);
        this.field_241103_E_.setThundering(bl2);
    }

    @Override
    public Biome getNoiseBiomeRaw(int n, int n2, int n3) {
        return this.getChunkProvider().getChunkGenerator().getBiomeProvider().getNoiseBiome(n, n2, n3);
    }

    public StructureManager func_241112_a_() {
        return this.field_241106_P_;
    }

    public void tick(BooleanSupplier booleanSupplier) {
        boolean bl;
        IProfiler iProfiler = this.getProfiler();
        this.insideTick = true;
        iProfiler.startSection("world border");
        this.getWorldBorder().tick();
        iProfiler.endStartSection("weather");
        boolean bl2 = this.isRaining();
        if (this.getDimensionType().hasSkyLight()) {
            if (this.getGameRules().getBoolean(GameRules.DO_WEATHER_CYCLE)) {
                int n = this.field_241103_E_.getClearWeatherTime();
                int n2 = this.field_241103_E_.getThunderTime();
                int n3 = this.field_241103_E_.getRainTime();
                boolean bl3 = this.worldInfo.isThundering();
                boolean bl4 = this.worldInfo.isRaining();
                if (n > 0) {
                    --n;
                    n2 = bl3 ? 0 : 1;
                    n3 = bl4 ? 0 : 1;
                    bl3 = false;
                    bl4 = false;
                } else {
                    if (n2 > 0) {
                        if (--n2 == 0) {
                            bl3 = !bl3;
                        }
                    } else {
                        n2 = bl3 ? this.rand.nextInt(12000) + 3600 : this.rand.nextInt(168000) + 12000;
                    }
                    if (n3 > 0) {
                        if (--n3 == 0) {
                            bl4 = !bl4;
                        }
                    } else {
                        n3 = bl4 ? this.rand.nextInt(12000) + 12000 : this.rand.nextInt(168000) + 12000;
                    }
                }
                this.field_241103_E_.setThunderTime(n2);
                this.field_241103_E_.setRainTime(n3);
                this.field_241103_E_.setClearWeatherTime(n);
                this.field_241103_E_.setThundering(bl3);
                this.field_241103_E_.setRaining(bl4);
            }
            this.prevThunderingStrength = this.thunderingStrength;
            this.thunderingStrength = this.worldInfo.isThundering() ? (float)((double)this.thunderingStrength + 0.01) : (float)((double)this.thunderingStrength - 0.01);
            this.thunderingStrength = MathHelper.clamp(this.thunderingStrength, 0.0f, 1.0f);
            this.prevRainingStrength = this.rainingStrength;
            this.rainingStrength = this.worldInfo.isRaining() ? (float)((double)this.rainingStrength + 0.01) : (float)((double)this.rainingStrength - 0.01);
            this.rainingStrength = MathHelper.clamp(this.rainingStrength, 0.0f, 1.0f);
        }
        if (this.prevRainingStrength != this.rainingStrength) {
            this.server.getPlayerList().func_232642_a_(new SChangeGameStatePacket(SChangeGameStatePacket.field_241771_h_, this.rainingStrength), this.getDimensionKey());
        }
        if (this.prevThunderingStrength != this.thunderingStrength) {
            this.server.getPlayerList().func_232642_a_(new SChangeGameStatePacket(SChangeGameStatePacket.field_241772_i_, this.thunderingStrength), this.getDimensionKey());
        }
        if (bl2 != this.isRaining()) {
            if (bl2) {
                this.server.getPlayerList().sendPacketToAllPlayers(new SChangeGameStatePacket(SChangeGameStatePacket.field_241766_c_, 0.0f));
            } else {
                this.server.getPlayerList().sendPacketToAllPlayers(new SChangeGameStatePacket(SChangeGameStatePacket.field_241765_b_, 0.0f));
            }
            this.server.getPlayerList().sendPacketToAllPlayers(new SChangeGameStatePacket(SChangeGameStatePacket.field_241771_h_, this.rainingStrength));
            this.server.getPlayerList().sendPacketToAllPlayers(new SChangeGameStatePacket(SChangeGameStatePacket.field_241772_i_, this.thunderingStrength));
        }
        if (this.allPlayersSleeping && this.players.stream().noneMatch(ServerWorld::lambda$tick$4)) {
            this.allPlayersSleeping = false;
            if (this.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
                long l = this.worldInfo.getDayTime() + 24000L;
                this.func_241114_a_(l - l % 24000L);
            }
            this.wakeUpAllPlayers();
            if (this.getGameRules().getBoolean(GameRules.DO_WEATHER_CYCLE)) {
                this.resetRainAndThunder();
            }
        }
        this.calculateInitialSkylight();
        this.func_241126_b_();
        iProfiler.endStartSection("chunkSource");
        this.getChunkProvider().tick(booleanSupplier);
        iProfiler.endStartSection("tickPending");
        if (!this.isDebug()) {
            this.pendingBlockTicks.tick();
            this.pendingFluidTicks.tick();
        }
        iProfiler.endStartSection("raid");
        this.raids.tick();
        iProfiler.endStartSection("blockEvents");
        this.sendQueuedBlockEvents();
        this.insideTick = false;
        iProfiler.endStartSection("entities");
        boolean bl5 = bl = !this.players.isEmpty() || !this.getForcedChunks().isEmpty();
        if (bl) {
            this.resetUpdateEntityTick();
        }
        if (bl || this.updateEntityTick++ < 300) {
            if (this.field_241105_O_ != null) {
                this.field_241105_O_.tick();
            }
            this.tickingEntities = true;
            Iterator iterator2 = this.entitiesById.int2ObjectEntrySet().iterator();
            while (true) {
                if (!iterator2.hasNext()) {
                    Entity entity2;
                    this.tickingEntities = false;
                    while ((entity2 = this.entitiesToAdd.poll()) != null) {
                        this.onEntityAdded(entity2);
                    }
                    this.tickBlockEntities();
                    break;
                }
                Int2ObjectMap.Entry entry = (Int2ObjectMap.Entry)iterator2.next();
                Entity entity3 = (Entity)entry.getValue();
                Entity entity4 = entity3.getRidingEntity();
                if (!this.server.func_230537_U_() && (entity3 instanceof AnimalEntity || entity3 instanceof WaterMobEntity)) {
                    entity3.remove();
                }
                if (!this.server.func_230538_V_() && entity3 instanceof INPC) {
                    entity3.remove();
                }
                iProfiler.startSection("checkDespawn");
                if (!entity3.removed) {
                    entity3.checkDespawn();
                }
                iProfiler.endSection();
                if (entity4 != null) {
                    if (!entity4.removed && entity4.isPassenger(entity3)) continue;
                    entity3.stopRiding();
                }
                iProfiler.startSection("tick");
                if (!entity3.removed && !(entity3 instanceof EnderDragonPartEntity)) {
                    this.guardEntityTick(this::updateEntity, entity3);
                }
                iProfiler.endSection();
                iProfiler.startSection("remove");
                if (entity3.removed) {
                    this.removeFromChunk(entity3);
                    iterator2.remove();
                    this.onEntityRemoved(entity3);
                }
                iProfiler.endSection();
            }
        }
        iProfiler.endSection();
    }

    protected void func_241126_b_() {
        if (this.field_241107_Q_) {
            long l = this.worldInfo.getGameTime() + 1L;
            this.field_241103_E_.setGameTime(l);
            this.field_241103_E_.getScheduledEvents().run(this.server, l);
            if (this.worldInfo.getGameRulesInstance().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
                this.func_241114_a_(this.worldInfo.getDayTime() + 1L);
            }
        }
    }

    public void func_241114_a_(long l) {
        this.field_241103_E_.setDayTime(l);
    }

    public void func_241123_a_(boolean bl, boolean bl2) {
        for (ISpecialSpawner iSpecialSpawner : this.field_241104_N_) {
            iSpecialSpawner.func_230253_a_(this, bl, bl2);
        }
    }

    private void wakeUpAllPlayers() {
        this.players.stream().filter(LivingEntity::isSleeping).collect(Collectors.toList()).forEach(ServerWorld::lambda$wakeUpAllPlayers$5);
    }

    public void tickEnvironment(Chunk chunk, int n) {
        Object object;
        Object object2;
        ChunkPos chunkPos = chunk.getPos();
        boolean bl = this.isRaining();
        int n2 = chunkPos.getXStart();
        int n3 = chunkPos.getZStart();
        IProfiler iProfiler = this.getProfiler();
        iProfiler.startSection("thunder");
        if (bl && this.isThundering() && this.rand.nextInt(100000) == 0 && this.isRainingAt((BlockPos)(object2 = this.adjustPosToNearbyEntity(this.getBlockRandomPos(n2, 0, n3, 15))))) {
            int n4;
            object = this.getDifficultyForLocation((BlockPos)object2);
            int n5 = n4 = this.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING) && this.rand.nextDouble() < (double)((DifficultyInstance)object).getAdditionalDifficulty() * 0.01 ? 1 : 0;
            if (n4) {
                SkeletonHorseEntity object3 = EntityType.SKELETON_HORSE.create(this);
                object3.setTrap(false);
                object3.setGrowingAge(0);
                object3.setPosition(((Vector3i)object2).getX(), ((Vector3i)object2).getY(), ((Vector3i)object2).getZ());
                this.addEntity(object3);
            }
            LightningBoltEntity lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(this);
            lightningBoltEntity.moveForced(Vector3d.copyCenteredHorizontally((Vector3i)object2));
            lightningBoltEntity.setEffectOnly(n4 != 0);
            this.addEntity(lightningBoltEntity);
        }
        iProfiler.endStartSection("iceandsnow");
        if (this.rand.nextInt(16) == 0) {
            object2 = this.getHeight(Heightmap.Type.MOTION_BLOCKING, this.getBlockRandomPos(n2, 0, n3, 15));
            object = ((BlockPos)object2).down();
            Biome biome = this.getBiome((BlockPos)object2);
            if (biome.doesWaterFreeze(this, (BlockPos)object)) {
                this.setBlockState((BlockPos)object, Blocks.ICE.getDefaultState());
            }
            if (bl && biome.doesSnowGenerate(this, (BlockPos)object2)) {
                this.setBlockState((BlockPos)object2, Blocks.SNOW.getDefaultState());
            }
            if (bl && this.getBiome((BlockPos)object).getPrecipitation() == Biome.RainType.RAIN) {
                this.getBlockState((BlockPos)object).getBlock().fillWithRain(this, (BlockPos)object);
            }
        }
        iProfiler.endStartSection("tickBlocks");
        if (n > 0) {
            for (ChunkSection chunkSection : chunk.getSections()) {
                if (chunkSection == Chunk.EMPTY_SECTION || !chunkSection.needsRandomTickAny()) continue;
                int n6 = chunkSection.getYLocation();
                for (int i = 0; i < n; ++i) {
                    FluidState fluidState;
                    BlockPos blockPos = this.getBlockRandomPos(n2, n6, n3, 15);
                    iProfiler.startSection("randomTick");
                    BlockState blockState = chunkSection.getBlockState(blockPos.getX() - n2, blockPos.getY() - n6, blockPos.getZ() - n3);
                    if (blockState.ticksRandomly()) {
                        blockState.randomTick(this, blockPos, this.rand);
                    }
                    if ((fluidState = blockState.getFluidState()).ticksRandomly()) {
                        fluidState.randomTick(this, blockPos, this.rand);
                    }
                    iProfiler.endSection();
                }
            }
        }
        iProfiler.endSection();
    }

    protected BlockPos adjustPosToNearbyEntity(BlockPos blockPos) {
        BlockPos blockPos2 = this.getHeight(Heightmap.Type.MOTION_BLOCKING, blockPos);
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(blockPos2, new BlockPos(blockPos2.getX(), this.getHeight(), blockPos2.getZ())).grow(3.0);
        List<LivingEntity> list = this.getEntitiesWithinAABB(LivingEntity.class, axisAlignedBB, this::lambda$adjustPosToNearbyEntity$6);
        if (!list.isEmpty()) {
            return list.get(this.rand.nextInt(list.size())).getPosition();
        }
        if (blockPos2.getY() == -1) {
            blockPos2 = blockPos2.up(2);
        }
        return blockPos2;
    }

    public boolean isInsideTick() {
        return this.insideTick;
    }

    public void updateAllPlayersSleepingFlag() {
        this.allPlayersSleeping = false;
        if (!this.players.isEmpty()) {
            int n = 0;
            int n2 = 0;
            for (ServerPlayerEntity serverPlayerEntity : this.players) {
                if (serverPlayerEntity.isSpectator()) {
                    ++n;
                    continue;
                }
                if (!serverPlayerEntity.isSleeping()) continue;
                ++n2;
            }
            this.allPlayersSleeping = n2 > 0 && n2 >= this.players.size() - n;
        }
    }

    @Override
    public ServerScoreboard getScoreboard() {
        return this.server.getScoreboard();
    }

    private void resetRainAndThunder() {
        this.field_241103_E_.setRainTime(0);
        this.field_241103_E_.setRaining(false);
        this.field_241103_E_.setThunderTime(0);
        this.field_241103_E_.setThundering(false);
    }

    public void resetUpdateEntityTick() {
        this.updateEntityTick = 0;
    }

    private void tickFluid(NextTickListEntry<Fluid> nextTickListEntry) {
        FluidState fluidState = this.getFluidState(nextTickListEntry.position);
        if (fluidState.getFluid() == nextTickListEntry.getTarget()) {
            fluidState.tick(this, nextTickListEntry.position);
        }
    }

    private void tickBlock(NextTickListEntry<Block> nextTickListEntry) {
        BlockState blockState = this.getBlockState(nextTickListEntry.position);
        if (blockState.isIn(nextTickListEntry.getTarget())) {
            blockState.tick(this, nextTickListEntry.position, this.rand);
        }
    }

    public void updateEntity(Entity entity2) {
        if (!(entity2 instanceof PlayerEntity) && !this.getChunkProvider().isChunkLoaded(entity2)) {
            this.chunkCheck(entity2);
        } else {
            entity2.forceSetPosition(entity2.getPosX(), entity2.getPosY(), entity2.getPosZ());
            entity2.prevRotationYaw = entity2.rotationYaw;
            entity2.prevRotationPitch = entity2.rotationPitch;
            if (entity2.addedToChunk) {
                ++entity2.ticksExisted;
                IProfiler iProfiler = this.getProfiler();
                iProfiler.startSection(() -> ServerWorld.lambda$updateEntity$7(entity2));
                iProfiler.func_230035_c_("tickNonPassenger");
                entity2.tick();
                iProfiler.endSection();
            }
            this.chunkCheck(entity2);
            if (entity2.addedToChunk) {
                for (Entity entity3 : entity2.getPassengers()) {
                    this.tickPassenger(entity2, entity3);
                }
            }
        }
    }

    public void tickPassenger(Entity entity2, Entity entity3) {
        if (!entity3.removed && entity3.getRidingEntity() == entity2) {
            if (entity3 instanceof PlayerEntity || this.getChunkProvider().isChunkLoaded(entity3)) {
                entity3.forceSetPosition(entity3.getPosX(), entity3.getPosY(), entity3.getPosZ());
                entity3.prevRotationYaw = entity3.rotationYaw;
                entity3.prevRotationPitch = entity3.rotationPitch;
                if (entity3.addedToChunk) {
                    ++entity3.ticksExisted;
                    IProfiler iProfiler = this.getProfiler();
                    iProfiler.startSection(() -> ServerWorld.lambda$tickPassenger$8(entity3));
                    iProfiler.func_230035_c_("tickPassenger");
                    entity3.updateRidden();
                    iProfiler.endSection();
                }
                this.chunkCheck(entity3);
                if (entity3.addedToChunk) {
                    for (Entity entity4 : entity3.getPassengers()) {
                        this.tickPassenger(entity3, entity4);
                    }
                }
            }
        } else {
            entity3.stopRiding();
        }
    }

    public void chunkCheck(Entity entity2) {
        if (entity2.func_233578_ci_()) {
            this.getProfiler().startSection("chunkCheck");
            int n = MathHelper.floor(entity2.getPosX() / 16.0);
            int n2 = MathHelper.floor(entity2.getPosY() / 16.0);
            int n3 = MathHelper.floor(entity2.getPosZ() / 16.0);
            if (!entity2.addedToChunk || entity2.chunkCoordX != n || entity2.chunkCoordY != n2 || entity2.chunkCoordZ != n3) {
                if (entity2.addedToChunk && this.chunkExists(entity2.chunkCoordX, entity2.chunkCoordZ)) {
                    this.getChunk(entity2.chunkCoordX, entity2.chunkCoordZ).removeEntityAtIndex(entity2, entity2.chunkCoordY);
                }
                if (!entity2.func_233577_ch_() && !this.chunkExists(n, n3)) {
                    if (entity2.addedToChunk) {
                        LOGGER.warn("Entity {} left loaded chunk area", (Object)entity2);
                    }
                    entity2.addedToChunk = false;
                } else {
                    this.getChunk(n, n3).addEntity(entity2);
                }
            }
            this.getProfiler().endSection();
        }
    }

    @Override
    public boolean isBlockModifiable(PlayerEntity playerEntity, BlockPos blockPos) {
        return !this.server.isBlockProtected(this, blockPos, playerEntity) && this.getWorldBorder().contains(blockPos);
    }

    public void save(@Nullable IProgressUpdate iProgressUpdate, boolean bl, boolean bl2) {
        ServerChunkProvider serverChunkProvider = this.getChunkProvider();
        if (!bl2) {
            if (iProgressUpdate != null) {
                iProgressUpdate.displaySavingString(new TranslationTextComponent("menu.savingLevel"));
            }
            this.saveLevel();
            if (iProgressUpdate != null) {
                iProgressUpdate.displayLoadingString(new TranslationTextComponent("menu.savingChunks"));
            }
            serverChunkProvider.save(bl);
        }
    }

    private void saveLevel() {
        if (this.field_241105_O_ != null) {
            this.server.func_240793_aU_().setDragonFightData(this.field_241105_O_.write());
        }
        this.getChunkProvider().getSavedData().save();
    }

    public List<Entity> getEntities(@Nullable EntityType<?> entityType, Predicate<? super Entity> predicate) {
        ArrayList<Entity> arrayList = Lists.newArrayList();
        ServerChunkProvider serverChunkProvider = this.getChunkProvider();
        for (Entity entity2 : this.entitiesById.values()) {
            if (entityType != null && entity2.getType() != entityType || !serverChunkProvider.chunkExists(MathHelper.floor(entity2.getPosX()) >> 4, MathHelper.floor(entity2.getPosZ()) >> 4) || !predicate.test(entity2)) continue;
            arrayList.add(entity2);
        }
        return arrayList;
    }

    public List<EnderDragonEntity> getDragons() {
        ArrayList<EnderDragonEntity> arrayList = Lists.newArrayList();
        for (Entity entity2 : this.entitiesById.values()) {
            if (!(entity2 instanceof EnderDragonEntity) || !entity2.isAlive()) continue;
            arrayList.add((EnderDragonEntity)entity2);
        }
        return arrayList;
    }

    public List<ServerPlayerEntity> getPlayers(Predicate<? super ServerPlayerEntity> predicate) {
        ArrayList<ServerPlayerEntity> arrayList = Lists.newArrayList();
        for (ServerPlayerEntity serverPlayerEntity : this.players) {
            if (!predicate.test(serverPlayerEntity)) continue;
            arrayList.add(serverPlayerEntity);
        }
        return arrayList;
    }

    @Nullable
    public ServerPlayerEntity getRandomPlayer() {
        List<ServerPlayerEntity> list = this.getPlayers(LivingEntity::isAlive);
        return list.isEmpty() ? null : list.get(this.rand.nextInt(list.size()));
    }

    @Override
    public boolean addEntity(Entity entity2) {
        return this.addEntity0(entity2);
    }

    public boolean summonEntity(Entity entity2) {
        return this.addEntity0(entity2);
    }

    public void addFromAnotherDimension(Entity entity2) {
        boolean bl = entity2.forceSpawn;
        entity2.forceSpawn = true;
        this.summonEntity(entity2);
        entity2.forceSpawn = bl;
        this.chunkCheck(entity2);
    }

    public void addDuringCommandTeleport(ServerPlayerEntity serverPlayerEntity) {
        this.addPlayer(serverPlayerEntity);
        this.chunkCheck(serverPlayerEntity);
    }

    public void addDuringPortalTeleport(ServerPlayerEntity serverPlayerEntity) {
        this.addPlayer(serverPlayerEntity);
        this.chunkCheck(serverPlayerEntity);
    }

    public void addNewPlayer(ServerPlayerEntity serverPlayerEntity) {
        this.addPlayer(serverPlayerEntity);
    }

    public void addRespawnedPlayer(ServerPlayerEntity serverPlayerEntity) {
        this.addPlayer(serverPlayerEntity);
    }

    private void addPlayer(ServerPlayerEntity serverPlayerEntity) {
        Entity entity2 = this.entitiesByUuid.get(serverPlayerEntity.getUniqueID());
        if (entity2 != null) {
            LOGGER.warn("Force-added player with duplicate UUID {}", (Object)serverPlayerEntity.getUniqueID().toString());
            entity2.detach();
            this.removePlayer((ServerPlayerEntity)entity2);
        }
        this.players.add(serverPlayerEntity);
        this.updateAllPlayersSleepingFlag();
        IChunk iChunk = this.getChunk(MathHelper.floor(serverPlayerEntity.getPosX() / 16.0), MathHelper.floor(serverPlayerEntity.getPosZ() / 16.0), ChunkStatus.FULL, false);
        if (iChunk instanceof Chunk) {
            iChunk.addEntity(serverPlayerEntity);
        }
        this.onEntityAdded(serverPlayerEntity);
    }

    private boolean addEntity0(Entity entity2) {
        if (entity2.removed) {
            LOGGER.warn("Tried to add entity {} but it was marked as removed already", (Object)EntityType.getKey(entity2.getType()));
            return true;
        }
        if (this.hasDuplicateEntity(entity2)) {
            return true;
        }
        IChunk iChunk = this.getChunk(MathHelper.floor(entity2.getPosX() / 16.0), MathHelper.floor(entity2.getPosZ() / 16.0), ChunkStatus.FULL, entity2.forceSpawn);
        if (!(iChunk instanceof Chunk)) {
            return true;
        }
        iChunk.addEntity(entity2);
        this.onEntityAdded(entity2);
        return false;
    }

    public boolean addEntityIfNotDuplicate(Entity entity2) {
        if (this.hasDuplicateEntity(entity2)) {
            return true;
        }
        this.onEntityAdded(entity2);
        return false;
    }

    private boolean hasDuplicateEntity(Entity entity2) {
        UUID uUID = entity2.getUniqueID();
        Entity entity3 = this.func_242105_c(uUID);
        if (entity3 == null) {
            return true;
        }
        LOGGER.warn("Trying to add entity with duplicated UUID {}. Existing {}#{}, new: {}#{}", (Object)uUID, (Object)EntityType.getKey(entity3.getType()), (Object)entity3.getEntityId(), (Object)EntityType.getKey(entity2.getType()), (Object)entity2.getEntityId());
        return false;
    }

    @Nullable
    private Entity func_242105_c(UUID uUID) {
        Entity entity2 = this.entitiesByUuid.get(uUID);
        if (entity2 != null) {
            return entity2;
        }
        if (this.tickingEntities) {
            for (Entity entity3 : this.entitiesToAdd) {
                if (!entity3.getUniqueID().equals(uUID)) continue;
                return entity3;
            }
        }
        return null;
    }

    public boolean func_242106_g(Entity entity2) {
        if (entity2.getSelfAndPassengers().anyMatch(this::hasDuplicateEntity)) {
            return true;
        }
        this.func_242417_l(entity2);
        return false;
    }

    public void onChunkUnloading(Chunk chunk) {
        this.tileEntitiesToBeRemoved.addAll(chunk.getTileEntityMap().values());
        ClassInheritanceMultiMap<Entity>[] classInheritanceMultiMapArray = chunk.getEntityLists();
        int n = classInheritanceMultiMapArray.length;
        for (int i = 0; i < n; ++i) {
            for (Entity entity2 : classInheritanceMultiMapArray[i]) {
                if (entity2 instanceof ServerPlayerEntity) continue;
                if (this.tickingEntities) {
                    throw Util.pauseDevMode(new IllegalStateException("Removing entity while ticking!"));
                }
                this.entitiesById.remove(entity2.getEntityId());
                this.onEntityRemoved(entity2);
            }
        }
    }

    public void onEntityRemoved(Entity entity2) {
        if (entity2 instanceof EnderDragonEntity) {
            for (EnderDragonPartEntity enderDragonPartEntity : ((EnderDragonEntity)entity2).getDragonParts()) {
                enderDragonPartEntity.remove();
            }
        }
        this.entitiesByUuid.remove(entity2.getUniqueID());
        this.getChunkProvider().untrack(entity2);
        if (entity2 instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity2;
            this.players.remove(serverPlayerEntity);
        }
        this.getScoreboard().removeEntity(entity2);
        if (entity2 instanceof MobEntity) {
            this.navigations.remove(((MobEntity)entity2).getNavigator());
        }
    }

    private void onEntityAdded(Entity entity2) {
        if (this.tickingEntities) {
            this.entitiesToAdd.add(entity2);
        } else {
            this.entitiesById.put(entity2.getEntityId(), entity2);
            if (entity2 instanceof EnderDragonEntity) {
                for (EnderDragonPartEntity enderDragonPartEntity : ((EnderDragonEntity)entity2).getDragonParts()) {
                    this.entitiesById.put(enderDragonPartEntity.getEntityId(), (Entity)enderDragonPartEntity);
                }
            }
            this.entitiesByUuid.put(entity2.getUniqueID(), entity2);
            this.getChunkProvider().track(entity2);
            if (entity2 instanceof MobEntity) {
                this.navigations.add(((MobEntity)entity2).getNavigator());
            }
        }
    }

    public void removeEntity(Entity entity2) {
        if (this.tickingEntities) {
            throw Util.pauseDevMode(new IllegalStateException("Removing entity while ticking!"));
        }
        this.removeFromChunk(entity2);
        this.entitiesById.remove(entity2.getEntityId());
        this.onEntityRemoved(entity2);
    }

    private void removeFromChunk(Entity entity2) {
        IChunk iChunk = this.getChunk(entity2.chunkCoordX, entity2.chunkCoordZ, ChunkStatus.FULL, true);
        if (iChunk instanceof Chunk) {
            ((Chunk)iChunk).removeEntity(entity2);
        }
    }

    public void removePlayer(ServerPlayerEntity serverPlayerEntity) {
        serverPlayerEntity.remove();
        this.removeEntity(serverPlayerEntity);
        this.updateAllPlayersSleepingFlag();
    }

    @Override
    public void sendBlockBreakProgress(int n, BlockPos blockPos, int n2) {
        for (ServerPlayerEntity serverPlayerEntity : this.server.getPlayerList().getPlayers()) {
            double d;
            double d2;
            double d3;
            if (serverPlayerEntity == null || serverPlayerEntity.world != this || serverPlayerEntity.getEntityId() == n || !((d3 = (double)blockPos.getX() - serverPlayerEntity.getPosX()) * d3 + (d2 = (double)blockPos.getY() - serverPlayerEntity.getPosY()) * d2 + (d = (double)blockPos.getZ() - serverPlayerEntity.getPosZ()) * d < 1024.0)) continue;
            serverPlayerEntity.connection.sendPacket(new SAnimateBlockBreakPacket(n, blockPos, n2));
        }
    }

    @Override
    public void playSound(@Nullable PlayerEntity playerEntity, double d, double d2, double d3, SoundEvent soundEvent, SoundCategory soundCategory, float f, float f2) {
        this.server.getPlayerList().sendToAllNearExcept(playerEntity, d, d2, d3, f > 1.0f ? (double)(16.0f * f) : 16.0, this.getDimensionKey(), new SPlaySoundEffectPacket(soundEvent, soundCategory, d, d2, d3, f, f2));
    }

    @Override
    public void playMovingSound(@Nullable PlayerEntity playerEntity, Entity entity2, SoundEvent soundEvent, SoundCategory soundCategory, float f, float f2) {
        this.server.getPlayerList().sendToAllNearExcept(playerEntity, entity2.getPosX(), entity2.getPosY(), entity2.getPosZ(), f > 1.0f ? (double)(16.0f * f) : 16.0, this.getDimensionKey(), new SSpawnMovingSoundEffectPacket(soundEvent, soundCategory, entity2, f, f2));
    }

    @Override
    public void playBroadcastSound(int n, BlockPos blockPos, int n2) {
        this.server.getPlayerList().sendPacketToAllPlayers(new SPlaySoundEventPacket(n, blockPos, n2, true));
    }

    @Override
    public void playEvent(@Nullable PlayerEntity playerEntity, int n, BlockPos blockPos, int n2) {
        this.server.getPlayerList().sendToAllNearExcept(playerEntity, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 64.0, this.getDimensionKey(), new SPlaySoundEventPacket(n, blockPos, n2, false));
    }

    @Override
    public void notifyBlockUpdate(BlockPos blockPos, BlockState blockState, BlockState blockState2, int n) {
        this.getChunkProvider().markBlockChanged(blockPos);
        VoxelShape voxelShape = blockState.getCollisionShape(this, blockPos);
        VoxelShape voxelShape2 = blockState2.getCollisionShape(this, blockPos);
        if (VoxelShapes.compare(voxelShape, voxelShape2, IBooleanFunction.NOT_SAME)) {
            for (PathNavigator pathNavigator : this.navigations) {
                if (pathNavigator.canUpdatePathOnTimeout()) continue;
                pathNavigator.onUpdateNavigation(blockPos);
            }
        }
    }

    @Override
    public void setEntityState(Entity entity2, byte by) {
        this.getChunkProvider().sendToTrackingAndSelf(entity2, new SEntityStatusPacket(entity2, by));
    }

    @Override
    public ServerChunkProvider getChunkProvider() {
        return this.field_241102_C_;
    }

    @Override
    public Explosion createExplosion(@Nullable Entity entity2, @Nullable DamageSource damageSource, @Nullable ExplosionContext explosionContext, double d, double d2, double d3, float f, boolean bl, Explosion.Mode mode) {
        Explosion explosion = new Explosion(this, entity2, damageSource, explosionContext, d, d2, d3, f, bl, mode);
        explosion.doExplosionA();
        explosion.doExplosionB(true);
        if (mode == Explosion.Mode.NONE) {
            explosion.clearAffectedBlockPositions();
        }
        for (ServerPlayerEntity serverPlayerEntity : this.players) {
            if (!(serverPlayerEntity.getDistanceSq(d, d2, d3) < 4096.0)) continue;
            serverPlayerEntity.connection.sendPacket(new SExplosionPacket(d, d2, d3, f, explosion.getAffectedBlockPositions(), explosion.getPlayerKnockbackMap().get(serverPlayerEntity)));
        }
        return explosion;
    }

    @Override
    public void addBlockEvent(BlockPos blockPos, Block block, int n, int n2) {
        this.blockEventQueue.add(new BlockEventData(blockPos, block, n, n2));
    }

    private void sendQueuedBlockEvents() {
        while (!this.blockEventQueue.isEmpty()) {
            BlockEventData blockEventData = this.blockEventQueue.removeFirst();
            if (!this.fireBlockEvent(blockEventData)) continue;
            this.server.getPlayerList().sendToAllNearExcept(null, blockEventData.getPosition().getX(), blockEventData.getPosition().getY(), blockEventData.getPosition().getZ(), 64.0, this.getDimensionKey(), new SBlockActionPacket(blockEventData.getPosition(), blockEventData.getBlock(), blockEventData.getEventID(), blockEventData.getEventParameter()));
        }
    }

    private boolean fireBlockEvent(BlockEventData blockEventData) {
        BlockState blockState = this.getBlockState(blockEventData.getPosition());
        return blockState.isIn(blockEventData.getBlock()) ? blockState.receiveBlockEvent(this, blockEventData.getPosition(), blockEventData.getEventID(), blockEventData.getEventParameter()) : false;
    }

    public ServerTickList<Block> getPendingBlockTicks() {
        return this.pendingBlockTicks;
    }

    public ServerTickList<Fluid> getPendingFluidTicks() {
        return this.pendingFluidTicks;
    }

    @Override
    @Nonnull
    public MinecraftServer getServer() {
        return this.server;
    }

    public Teleporter getDefaultTeleporter() {
        return this.worldTeleporter;
    }

    public TemplateManager getStructureTemplateManager() {
        return this.server.func_240792_aT_();
    }

    public <T extends IParticleData> int spawnParticle(T t, double d, double d2, double d3, int n, double d4, double d5, double d6, double d7) {
        SSpawnParticlePacket sSpawnParticlePacket = new SSpawnParticlePacket(t, false, d, d2, d3, (float)d4, (float)d5, (float)d6, (float)d7, n);
        int n2 = 0;
        for (int i = 0; i < this.players.size(); ++i) {
            ServerPlayerEntity serverPlayerEntity = this.players.get(i);
            if (!this.sendPacketWithinDistance(serverPlayerEntity, false, d, d2, d3, sSpawnParticlePacket)) continue;
            ++n2;
        }
        return n2;
    }

    public <T extends IParticleData> boolean spawnParticle(ServerPlayerEntity serverPlayerEntity, T t, boolean bl, double d, double d2, double d3, int n, double d4, double d5, double d6, double d7) {
        SSpawnParticlePacket sSpawnParticlePacket = new SSpawnParticlePacket(t, bl, d, d2, d3, (float)d4, (float)d5, (float)d6, (float)d7, n);
        return this.sendPacketWithinDistance(serverPlayerEntity, bl, d, d2, d3, sSpawnParticlePacket);
    }

    private boolean sendPacketWithinDistance(ServerPlayerEntity serverPlayerEntity, boolean bl, double d, double d2, double d3, IPacket<?> iPacket) {
        if (serverPlayerEntity.getServerWorld() != this) {
            return true;
        }
        BlockPos blockPos = serverPlayerEntity.getPosition();
        if (blockPos.withinDistance(new Vector3d(d, d2, d3), bl ? 512.0 : 32.0)) {
            serverPlayerEntity.connection.sendPacket(iPacket);
            return false;
        }
        return true;
    }

    @Override
    @Nullable
    public Entity getEntityByID(int n) {
        return (Entity)this.entitiesById.get(n);
    }

    @Nullable
    public Entity getEntityByUuid(UUID uUID) {
        return this.entitiesByUuid.get(uUID);
    }

    @Nullable
    public BlockPos func_241117_a_(Structure<?> structure, BlockPos blockPos, int n, boolean bl) {
        return !this.server.func_240793_aU_().getDimensionGeneratorSettings().doesGenerateFeatures() ? null : this.getChunkProvider().getChunkGenerator().func_235956_a_(this, structure, blockPos, n, bl);
    }

    @Nullable
    public BlockPos func_241116_a_(Biome biome, BlockPos blockPos, int n, int n2) {
        return this.getChunkProvider().getChunkGenerator().getBiomeProvider().findBiomePosition(blockPos.getX(), blockPos.getY(), blockPos.getZ(), n, n2, arg_0 -> ServerWorld.lambda$func_241116_a_$9(biome, arg_0), this.rand, false);
    }

    @Override
    public RecipeManager getRecipeManager() {
        return this.server.getRecipeManager();
    }

    @Override
    public ITagCollectionSupplier getTags() {
        return this.server.func_244266_aF();
    }

    @Override
    public boolean isSaveDisabled() {
        return this.disableLevelSaving;
    }

    @Override
    public DynamicRegistries func_241828_r() {
        return this.server.func_244267_aX();
    }

    public DimensionSavedDataManager getSavedData() {
        return this.getChunkProvider().getSavedData();
    }

    @Override
    @Nullable
    public MapData getMapData(String string) {
        return this.getServer().func_241755_D_().getSavedData().get(() -> ServerWorld.lambda$getMapData$10(string), string);
    }

    @Override
    public void registerMapData(MapData mapData) {
        this.getServer().func_241755_D_().getSavedData().set(mapData);
    }

    @Override
    public int getNextMapId() {
        return this.getServer().func_241755_D_().getSavedData().getOrCreate(MapIdTracker::new, "idcounts").getNextId();
    }

    public void func_241124_a__(BlockPos blockPos, float f) {
        ChunkPos chunkPos = new ChunkPos(new BlockPos(this.worldInfo.getSpawnX(), 0, this.worldInfo.getSpawnZ()));
        this.worldInfo.setSpawn(blockPos, f);
        this.getChunkProvider().releaseTicket(TicketType.START, chunkPos, 11, Unit.INSTANCE);
        this.getChunkProvider().registerTicket(TicketType.START, new ChunkPos(blockPos), 11, Unit.INSTANCE);
        this.getServer().getPlayerList().sendPacketToAllPlayers(new SWorldSpawnChangedPacket(blockPos, f));
    }

    public BlockPos getSpawnPoint() {
        BlockPos blockPos = new BlockPos(this.worldInfo.getSpawnX(), this.worldInfo.getSpawnY(), this.worldInfo.getSpawnZ());
        if (!this.getWorldBorder().contains(blockPos)) {
            blockPos = this.getHeight(Heightmap.Type.MOTION_BLOCKING, new BlockPos(this.getWorldBorder().getCenterX(), 0.0, this.getWorldBorder().getCenterZ()));
        }
        return blockPos;
    }

    public float func_242107_v() {
        return this.worldInfo.getSpawnAngle();
    }

    public LongSet getForcedChunks() {
        ForcedChunksSaveData forcedChunksSaveData = this.getSavedData().get(ForcedChunksSaveData::new, "chunks");
        return forcedChunksSaveData != null ? LongSets.unmodifiable(forcedChunksSaveData.getChunks()) : LongSets.EMPTY_SET;
    }

    public boolean forceChunk(int n, int n2, boolean bl) {
        boolean bl2;
        ForcedChunksSaveData forcedChunksSaveData = this.getSavedData().getOrCreate(ForcedChunksSaveData::new, "chunks");
        ChunkPos chunkPos = new ChunkPos(n, n2);
        long l = chunkPos.asLong();
        if (bl) {
            bl2 = forcedChunksSaveData.getChunks().add(l);
            if (bl2) {
                this.getChunk(n, n2);
            }
        } else {
            bl2 = forcedChunksSaveData.getChunks().remove(l);
        }
        forcedChunksSaveData.setDirty(bl2);
        if (bl2) {
            this.getChunkProvider().forceChunk(chunkPos, bl);
        }
        return bl2;
    }

    public List<ServerPlayerEntity> getPlayers() {
        return this.players;
    }

    @Override
    public void onBlockStateChange(BlockPos blockPos, BlockState blockState, BlockState blockState2) {
        Optional<PointOfInterestType> optional;
        Optional<PointOfInterestType> optional2 = PointOfInterestType.forState(blockState);
        if (!Objects.equals(optional2, optional = PointOfInterestType.forState(blockState2))) {
            BlockPos blockPos2 = blockPos.toImmutable();
            optional2.ifPresent(arg_0 -> this.lambda$onBlockStateChange$12(blockPos2, arg_0));
            optional.ifPresent(arg_0 -> this.lambda$onBlockStateChange$14(blockPos2, arg_0));
        }
    }

    public PointOfInterestManager getPointOfInterestManager() {
        return this.getChunkProvider().getPointOfInterestManager();
    }

    public boolean isVillage(BlockPos blockPos) {
        return this.func_241119_a_(blockPos, 0);
    }

    public boolean isVillage(SectionPos sectionPos) {
        return this.isVillage(sectionPos.getCenter());
    }

    public boolean func_241119_a_(BlockPos blockPos, int n) {
        if (n > 6) {
            return true;
        }
        return this.sectionsToVillage(SectionPos.from(blockPos)) <= n;
    }

    public int sectionsToVillage(SectionPos sectionPos) {
        return this.getPointOfInterestManager().sectionsToVillage(sectionPos);
    }

    public RaidManager getRaids() {
        return this.raids;
    }

    @Nullable
    public Raid findRaid(BlockPos blockPos) {
        return this.raids.findRaid(blockPos, 9216);
    }

    public boolean hasRaid(BlockPos blockPos) {
        return this.findRaid(blockPos) != null;
    }

    public void updateReputation(IReputationType iReputationType, Entity entity2, IReputationTracking iReputationTracking) {
        iReputationTracking.updateReputation(iReputationType, entity2);
    }

    public void writeDebugInfo(Path path) throws IOException {
        Object object2;
        ChunkManager chunkManager = this.getChunkProvider().chunkManager;
        try (Object object3 = Files.newBufferedWriter(path.resolve("stats.txt"), new OpenOption[0]);){
            ((Writer)object3).write(String.format("spawning_chunks: %d\n", chunkManager.getTicketManager().getSpawningChunksCount()));
            object2 = this.getChunkProvider().func_241101_k_();
            if (object2 != null) {
                for (Object2IntMap.Entry object4 : ((WorldEntitySpawner.EntityDensityManager)object2).func_234995_b_().object2IntEntrySet()) {
                    ((Writer)object3).write(String.format("spawn_count.%s: %d\n", ((EntityClassification)object4.getKey()).getName(), object4.getIntValue()));
                }
            }
            ((Writer)object3).write(String.format("entities: %d\n", this.entitiesById.size()));
            ((Writer)object3).write(String.format("block_entities: %d\n", this.loadedTileEntityList.size()));
            ((Writer)object3).write(String.format("block_ticks: %d\n", ((ServerTickList)this.getPendingBlockTicks()).func_225420_a()));
            ((Writer)object3).write(String.format("fluid_ticks: %d\n", ((ServerTickList)this.getPendingFluidTicks()).func_225420_a()));
            ((Writer)object3).write("distance_manager: " + chunkManager.getTicketManager().func_225412_c() + "\n");
            ((Writer)object3).write(String.format("pending_tasks: %d\n", this.getChunkProvider().func_225314_f()));
        }
        object3 = new CrashReport("Level dump", new Exception("dummy"));
        this.fillCrashReport((CrashReport)object3);
        object2 = Files.newBufferedWriter(path.resolve("example_crash.txt"), new OpenOption[0]);
        try {
            ((Writer)object2).write(((CrashReport)object3).getCompleteReport());
        } finally {
            if (object2 != null) {
                ((Writer)object2).close();
            }
        }
        object2 = path.resolve("chunks.csv");
        try (Object object5 = Files.newBufferedWriter((Path)object2, new OpenOption[0]);){
            chunkManager.func_225406_a((Writer)object5);
        }
        object5 = path.resolve("entities.csv");
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter((Path)object5, new OpenOption[0]);){
            ServerWorld.dumpEntities(bufferedWriter, this.entitiesById.values());
        }
        Path path2 = path.resolve("block_entities.csv");
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path2, new OpenOption[0]);){
            this.dumpBlockEntities(bufferedWriter);
        }
    }

    private static void dumpEntities(Writer writer, Iterable<Entity> iterable) throws IOException {
        CSVWriter cSVWriter = CSVWriter.func_225428_a().func_225423_a("x").func_225423_a("y").func_225423_a("z").func_225423_a("uuid").func_225423_a("type").func_225423_a("alive").func_225423_a("display_name").func_225423_a("custom_name").func_225422_a(writer);
        for (Entity entity2 : iterable) {
            ITextComponent iTextComponent = entity2.getCustomName();
            ITextComponent iTextComponent2 = entity2.getDisplayName();
            cSVWriter.func_225426_a(entity2.getPosX(), entity2.getPosY(), entity2.getPosZ(), entity2.getUniqueID(), Registry.ENTITY_TYPE.getKey(entity2.getType()), entity2.isAlive(), iTextComponent2.getString(), iTextComponent != null ? iTextComponent.getString() : null);
        }
    }

    private void dumpBlockEntities(Writer writer) throws IOException {
        CSVWriter cSVWriter = CSVWriter.func_225428_a().func_225423_a("x").func_225423_a("y").func_225423_a("z").func_225423_a("type").func_225422_a(writer);
        for (TileEntity tileEntity : this.loadedTileEntityList) {
            BlockPos blockPos = tileEntity.getPos();
            cSVWriter.func_225426_a(blockPos.getX(), blockPos.getY(), blockPos.getZ(), Registry.BLOCK_ENTITY_TYPE.getKey(tileEntity.getType()));
        }
    }

    @VisibleForTesting
    public void clearBlockEvents(MutableBoundingBox mutableBoundingBox) {
        this.blockEventQueue.removeIf(arg_0 -> ServerWorld.lambda$clearBlockEvents$15(mutableBoundingBox, arg_0));
    }

    @Override
    public void func_230547_a_(BlockPos blockPos, Block block) {
        if (!this.isDebug()) {
            this.notifyNeighborsOfStateChange(blockPos, block);
        }
    }

    @Override
    public float func_230487_a_(Direction direction, boolean bl) {
        return 1.0f;
    }

    public Iterable<Entity> func_241136_z_() {
        return Iterables.unmodifiableIterable(this.entitiesById.values());
    }

    public String toString() {
        return "ServerLevel[" + this.field_241103_E_.getWorldName() + "]";
    }

    public boolean func_241109_A_() {
        return this.server.func_240793_aU_().getDimensionGeneratorSettings().func_236228_i_();
    }

    @Override
    public long getSeed() {
        return this.server.func_240793_aU_().getDimensionGeneratorSettings().getSeed();
    }

    @Nullable
    public DragonFightManager func_241110_C_() {
        return this.field_241105_O_;
    }

    @Override
    public Stream<? extends StructureStart<?>> func_241827_a(SectionPos sectionPos, Structure<?> structure) {
        return this.func_241112_a_().func_235011_a_(sectionPos, structure);
    }

    @Override
    public ServerWorld getWorld() {
        return this;
    }

    @VisibleForTesting
    public String func_244521_F() {
        return String.format("players: %s, entities: %d [%s], block_entities: %d [%s], block_ticks: %d, fluid_ticks: %d, chunk_source: %s", this.players.size(), this.entitiesById.size(), ServerWorld.func_244524_a(this.entitiesById.values(), ServerWorld::lambda$func_244521_F$16), this.tickableTileEntities.size(), ServerWorld.func_244524_a(this.tickableTileEntities, ServerWorld::lambda$func_244521_F$17), ((ServerTickList)this.getPendingBlockTicks()).func_225420_a(), ((ServerTickList)this.getPendingFluidTicks()).func_225420_a(), this.getProviderName());
    }

    private static <T> String func_244524_a(Collection<T> collection, Function<T, ResourceLocation> function) {
        try {
            Object2IntOpenHashMap<ResourceLocation> object2IntOpenHashMap = new Object2IntOpenHashMap<ResourceLocation>();
            for (T t : collection) {
                ResourceLocation resourceLocation = function.apply(t);
                object2IntOpenHashMap.addTo(resourceLocation, 1);
            }
            return object2IntOpenHashMap.object2IntEntrySet().stream().sorted(Comparator.comparing(Object2IntMap.Entry::getIntValue).reversed()).limit(5L).map(ServerWorld::lambda$func_244524_a$18).collect(Collectors.joining(","));
        } catch (Exception exception) {
            return "";
        }
    }

    public static void func_241121_a_(ServerWorld serverWorld) {
        BlockPos blockPos = field_241108_a_;
        int n = blockPos.getX();
        int n2 = blockPos.getY() - 2;
        int n3 = blockPos.getZ();
        BlockPos.getAllInBoxMutable(n - 2, n2 + 1, n3 - 2, n + 2, n2 + 3, n3 + 2).forEach(arg_0 -> ServerWorld.lambda$func_241121_a_$19(serverWorld, arg_0));
        BlockPos.getAllInBoxMutable(n - 2, n2, n3 - 2, n + 2, n2, n3 + 2).forEach(arg_0 -> ServerWorld.lambda$func_241121_a_$20(serverWorld, arg_0));
    }

    @Override
    public Scoreboard getScoreboard() {
        return this.getScoreboard();
    }

    @Override
    public AbstractChunkProvider getChunkProvider() {
        return this.getChunkProvider();
    }

    public ITickList getPendingFluidTicks() {
        return this.getPendingFluidTicks();
    }

    public ITickList getPendingBlockTicks() {
        return this.getPendingBlockTicks();
    }

    private static void lambda$func_241121_a_$20(ServerWorld serverWorld, BlockPos blockPos) {
        serverWorld.setBlockState(blockPos, Blocks.OBSIDIAN.getDefaultState());
    }

    private static void lambda$func_241121_a_$19(ServerWorld serverWorld, BlockPos blockPos) {
        serverWorld.setBlockState(blockPos, Blocks.AIR.getDefaultState());
    }

    private static String lambda$func_244524_a$18(Object2IntMap.Entry entry) {
        return entry.getKey() + ":" + entry.getIntValue();
    }

    private static ResourceLocation lambda$func_244521_F$17(TileEntity tileEntity) {
        return Registry.BLOCK_ENTITY_TYPE.getKey(tileEntity.getType());
    }

    private static ResourceLocation lambda$func_244521_F$16(Entity entity2) {
        return Registry.ENTITY_TYPE.getKey(entity2.getType());
    }

    private static boolean lambda$clearBlockEvents$15(MutableBoundingBox mutableBoundingBox, BlockEventData blockEventData) {
        return mutableBoundingBox.isVecInside(blockEventData.getPosition());
    }

    private void lambda$onBlockStateChange$14(BlockPos blockPos, PointOfInterestType pointOfInterestType) {
        this.getServer().execute(() -> this.lambda$onBlockStateChange$13(blockPos, pointOfInterestType));
    }

    private void lambda$onBlockStateChange$13(BlockPos blockPos, PointOfInterestType pointOfInterestType) {
        this.getPointOfInterestManager().add(blockPos, pointOfInterestType);
        DebugPacketSender.func_218799_a(this, blockPos);
    }

    private void lambda$onBlockStateChange$12(BlockPos blockPos, PointOfInterestType pointOfInterestType) {
        this.getServer().execute(() -> this.lambda$onBlockStateChange$11(blockPos));
    }

    private void lambda$onBlockStateChange$11(BlockPos blockPos) {
        this.getPointOfInterestManager().remove(blockPos);
        DebugPacketSender.func_218805_b(this, blockPos);
    }

    private static MapData lambda$getMapData$10(String string) {
        return new MapData(string);
    }

    private static boolean lambda$func_241116_a_$9(Biome biome, Biome biome2) {
        return biome2 == biome;
    }

    private static String lambda$tickPassenger$8(Entity entity2) {
        return Registry.ENTITY_TYPE.getKey(entity2.getType()).toString();
    }

    private static String lambda$updateEntity$7(Entity entity2) {
        return Registry.ENTITY_TYPE.getKey(entity2.getType()).toString();
    }

    private boolean lambda$adjustPosToNearbyEntity$6(LivingEntity livingEntity) {
        return livingEntity != null && livingEntity.isAlive() && this.canSeeSky(livingEntity.getPosition());
    }

    private static void lambda$wakeUpAllPlayers$5(ServerPlayerEntity serverPlayerEntity) {
        serverPlayerEntity.stopSleepInBed(false, true);
    }

    private static boolean lambda$tick$4(ServerPlayerEntity serverPlayerEntity) {
        return !serverPlayerEntity.isSpectator() && !serverPlayerEntity.isPlayerFullyAsleep();
    }

    private RaidManager lambda$new$3() {
        return new RaidManager(this);
    }

    private static DimensionSavedDataManager lambda$new$2(MinecraftServer minecraftServer) {
        return minecraftServer.func_241755_D_().getSavedData();
    }

    private static boolean lambda$new$1(Fluid fluid) {
        return fluid == null || fluid == Fluids.EMPTY;
    }

    private static boolean lambda$new$0(Block block) {
        return block == null || block.getDefaultState().isAir();
    }
}

