/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import mpp.venusfr.events.EventEntityLeave;
import mpp.venusfr.venusfr;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.EntityTickableSound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.multiplayer.ClientChunkProvider;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.particle.FireworkParticle;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.color.ColorCache;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.profiler.IProfiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.CubeCoordinateIterator;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DimensionType;
import net.minecraft.world.EmptyTickList;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.ITickList;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeColors;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.ParticleEffectAmbience;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.storage.ISpawnWorldInfo;
import net.minecraft.world.storage.IWorldInfo;
import net.minecraft.world.storage.MapData;
import net.optifine.Config;
import net.optifine.CustomGuis;
import net.optifine.DynamicLights;
import net.optifine.RandomEntities;
import net.optifine.override.PlayerControllerOF;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.shaders.Shaders;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ClientWorld
extends World {
    private final Int2ObjectMap<Entity> entitiesById = new Int2ObjectOpenHashMap<Entity>();
    private final ClientPlayNetHandler connection;
    private final WorldRenderer worldRenderer;
    private final ClientWorldInfo field_239130_d_;
    private final DimensionRenderInfo field_239131_x_;
    private final Minecraft mc = Minecraft.getInstance();
    private final List<AbstractClientPlayerEntity> players = Lists.newArrayList();
    private Scoreboard scoreboard = new Scoreboard();
    public final Map<String, MapData> maps = Maps.newHashMap();
    private int timeLightningFlash;
    private final Object2ObjectArrayMap<ColorResolver, ColorCache> colorCaches = Util.make(new Object2ObjectArrayMap(3), ClientWorld::lambda$new$0);
    private final ClientChunkProvider field_239129_E_;
    private boolean playerUpdate = false;
    EventEntityLeave eventEntityLeave = new EventEntityLeave(null);

    public ClientWorld(ClientPlayNetHandler clientPlayNetHandler, ClientWorldInfo clientWorldInfo, RegistryKey<World> registryKey, DimensionType dimensionType, int n, Supplier<IProfiler> supplier, WorldRenderer worldRenderer, boolean bl, long l) {
        super(clientWorldInfo, registryKey, dimensionType, supplier, true, bl, l);
        this.connection = clientPlayNetHandler;
        this.field_239129_E_ = new ClientChunkProvider(this, n);
        this.field_239130_d_ = clientWorldInfo;
        this.worldRenderer = worldRenderer;
        this.field_239131_x_ = DimensionRenderInfo.func_243495_a(dimensionType);
        this.func_239136_a_(new BlockPos(8, 64, 8), 0.0f);
        this.calculateInitialSkylight();
        this.calculateInitialWeather();
        if (Reflector.CapabilityProvider_gatherCapabilities.exists()) {
            Reflector.call(this, Reflector.CapabilityProvider_gatherCapabilities, new Object[0]);
        }
        Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, this);
        if (this.mc.playerController != null && this.mc.playerController.getClass() == PlayerController.class) {
            this.mc.playerController = new PlayerControllerOF(this.mc, this.connection);
            CustomGuis.setPlayerControllerOF((PlayerControllerOF)this.mc.playerController);
        }
    }

    public DimensionRenderInfo func_239132_a_() {
        return this.field_239131_x_;
    }

    public void tick(BooleanSupplier booleanSupplier) {
        this.getWorldBorder().tick();
        this.func_239141_x_();
        this.getProfiler().startSection("blocks");
        this.field_239129_E_.tick(booleanSupplier);
        this.getProfiler().endSection();
    }

    private void func_239141_x_() {
        this.func_239134_a_(this.worldInfo.getGameTime() + 1L);
        if (this.worldInfo.getGameRulesInstance().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
            this.setDayTime(this.worldInfo.getDayTime() + 1L);
        }
    }

    public void func_239134_a_(long l) {
        this.field_239130_d_.setGameTime(l);
    }

    public void setDayTime(long l) {
        if (l < 0L) {
            l = -l;
            this.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(false, null);
        } else {
            this.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(true, null);
        }
        this.field_239130_d_.setDayTime(l);
    }

    public Iterable<Entity> getAllEntities() {
        return this.entitiesById.values();
    }

    public void tickEntities() {
        IProfiler iProfiler = this.getProfiler();
        iProfiler.startSection("entities");
        Iterator iterator2 = this.entitiesById.int2ObjectEntrySet().iterator();
        while (iterator2.hasNext()) {
            Int2ObjectMap.Entry entry = (Int2ObjectMap.Entry)iterator2.next();
            Entity entity2 = (Entity)entry.getValue();
            if (entity2.isPassenger()) continue;
            iProfiler.startSection("tick");
            if (!entity2.removed) {
                this.guardEntityTick(this::updateEntity, entity2);
            }
            iProfiler.endSection();
            iProfiler.startSection("remove");
            if (entity2.removed) {
                iterator2.remove();
                this.removeEntity(entity2);
            }
            iProfiler.endSection();
        }
        this.tickBlockEntities();
        iProfiler.endSection();
    }

    public void updateEntity(Entity entity2) {
        if (!(entity2 instanceof PlayerEntity) && !this.getChunkProvider().isChunkLoaded(entity2)) {
            this.checkChunk(entity2);
        } else {
            entity2.forceSetPosition(entity2.getPosX(), entity2.getPosY(), entity2.getPosZ());
            entity2.prevRotationYaw = entity2.rotationYaw;
            entity2.prevRotationPitch = entity2.rotationPitch;
            if (entity2.addedToChunk || entity2.isSpectator()) {
                ++entity2.ticksExisted;
                this.getProfiler().startSection(() -> ClientWorld.lambda$updateEntity$1(entity2));
                if (ReflectorForge.canUpdate(entity2)) {
                    entity2.tick();
                }
                this.getProfiler().endSection();
            }
            this.checkChunk(entity2);
            if (entity2.addedToChunk) {
                for (Entity entity3 : entity2.getPassengers()) {
                    this.updateEntityRidden(entity2, entity3);
                }
            }
        }
    }

    public void updateEntityRidden(Entity entity2, Entity entity3) {
        if (!entity3.removed && entity3.getRidingEntity() == entity2) {
            if (entity3 instanceof PlayerEntity || this.getChunkProvider().isChunkLoaded(entity3)) {
                entity3.forceSetPosition(entity3.getPosX(), entity3.getPosY(), entity3.getPosZ());
                entity3.prevRotationYaw = entity3.rotationYaw;
                entity3.prevRotationPitch = entity3.rotationPitch;
                if (entity3.addedToChunk) {
                    ++entity3.ticksExisted;
                    entity3.updateRidden();
                }
                this.checkChunk(entity3);
                if (entity3.addedToChunk) {
                    for (Entity entity4 : entity3.getPassengers()) {
                        this.updateEntityRidden(entity3, entity4);
                    }
                }
            }
        } else {
            entity3.stopRiding();
        }
    }

    private void checkChunk(Entity entity2) {
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

    public void onChunkUnloaded(Chunk chunk) {
        Collection collection = Reflector.ForgeWorld_tileEntitiesToBeRemoved.exists() ? (Collection)Reflector.getFieldValue(this, Reflector.ForgeWorld_tileEntitiesToBeRemoved) : this.tileEntitiesToBeRemoved;
        collection.addAll(chunk.getTileEntityMap().values());
        this.field_239129_E_.getLightManager().enableLightSources(chunk.getPos(), true);
    }

    public void onChunkLoaded(int n, int n2) {
        this.colorCaches.forEach((arg_0, arg_1) -> ClientWorld.lambda$onChunkLoaded$2(n, n2, arg_0, arg_1));
    }

    public void clearColorCaches() {
        this.colorCaches.forEach(ClientWorld::lambda$clearColorCaches$3);
    }

    @Override
    public boolean chunkExists(int n, int n2) {
        return false;
    }

    public int getCountLoadedEntities() {
        return this.entitiesById.size();
    }

    public void addPlayer(int n, AbstractClientPlayerEntity abstractClientPlayerEntity) {
        this.addEntityImpl(n, abstractClientPlayerEntity);
        this.players.add(abstractClientPlayerEntity);
    }

    public void addEntity(int n, Entity entity2) {
        this.addEntityImpl(n, entity2);
    }

    private void addEntityImpl(int n, Entity entity2) {
        if (!Reflector.EntityJoinWorldEvent_Constructor.exists() || !Reflector.postForgeBusEvent(Reflector.EntityJoinWorldEvent_Constructor, entity2, this)) {
            this.removeEntityFromWorld(n);
            this.entitiesById.put(n, entity2);
            this.getChunkProvider().getChunk(MathHelper.floor(entity2.getPosX() / 16.0), MathHelper.floor(entity2.getPosZ() / 16.0), ChunkStatus.FULL, false).addEntity(entity2);
            if (Reflector.IForgeEntity_onAddedToWorld.exists()) {
                Reflector.call(entity2, Reflector.IForgeEntity_onAddedToWorld, new Object[0]);
            }
            this.onEntityAdded(entity2);
        }
    }

    public void removeEntityFromWorld(int n) {
        Entity entity2 = (Entity)this.entitiesById.remove(n);
        if (entity2 != null) {
            entity2.remove();
            this.removeEntity(entity2);
            this.eventEntityLeave.setEntity(entity2);
            venusfr.getInstance().getEventBus().post(this.eventEntityLeave);
        }
    }

    private void removeEntity(Entity entity2) {
        entity2.detach();
        if (entity2.addedToChunk) {
            this.getChunk(entity2.chunkCoordX, entity2.chunkCoordZ).removeEntity(entity2);
        }
        this.players.remove(entity2);
        if (Reflector.IForgeEntity_onRemovedFromWorld.exists()) {
            Reflector.call(entity2, Reflector.IForgeEntity_onRemovedFromWorld, new Object[0]);
        }
        if (Reflector.EntityLeaveWorldEvent_Constructor.exists()) {
            Reflector.postForgeBusEvent(Reflector.EntityLeaveWorldEvent_Constructor, entity2, this);
        }
        this.onEntityRemoved(entity2);
    }

    public void addEntitiesToChunk(Chunk chunk) {
        for (Int2ObjectMap.Entry entry : this.entitiesById.int2ObjectEntrySet()) {
            Entity entity2 = (Entity)entry.getValue();
            int n = MathHelper.floor(entity2.getPosX() / 16.0);
            int n2 = MathHelper.floor(entity2.getPosZ() / 16.0);
            if (n != chunk.getPos().x || n2 != chunk.getPos().z) continue;
            chunk.addEntity(entity2);
        }
    }

    @Override
    @Nullable
    public Entity getEntityByID(int n) {
        return (Entity)this.entitiesById.get(n);
    }

    public void invalidateRegionAndSetBlock(BlockPos blockPos, BlockState blockState) {
        this.setBlockState(blockPos, blockState, 0);
    }

    @Override
    public void sendQuittingDisconnectingPacket() {
        this.connection.getNetworkManager().closeChannel(new TranslationTextComponent("multiplayer.status.quitting"));
    }

    public void animateTick(int n, int n2, int n3) {
        int n4 = 32;
        Random random2 = new Random();
        boolean bl = false;
        if (this.mc.playerController.getCurrentGameType() == GameType.CREATIVE) {
            for (ItemStack itemStack : this.mc.player.getHeldEquipment()) {
                if (itemStack.getItem() != Blocks.BARRIER.asItem()) continue;
                bl = true;
                break;
            }
        }
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i < 667; ++i) {
            this.animateTick(n, n2, n3, 16, random2, bl, mutable);
            this.animateTick(n, n2, n3, 32, random2, bl, mutable);
        }
    }

    public void animateTick(int n, int n2, int n3, int n4, Random random2, boolean bl, BlockPos.Mutable mutable) {
        int n5 = n + this.rand.nextInt(n4) - this.rand.nextInt(n4);
        int n6 = n2 + this.rand.nextInt(n4) - this.rand.nextInt(n4);
        int n7 = n3 + this.rand.nextInt(n4) - this.rand.nextInt(n4);
        mutable.setPos(n5, n6, n7);
        BlockState blockState = this.getBlockState(mutable);
        blockState.getBlock().animateTick(blockState, this, mutable, random2);
        FluidState fluidState = this.getFluidState(mutable);
        if (!fluidState.isEmpty()) {
            fluidState.animateTick(this, mutable, random2);
            IParticleData iParticleData = fluidState.getDripParticleData();
            if (iParticleData != null && this.rand.nextInt(10) == 0) {
                boolean bl2 = blockState.isSolidSide(this, mutable, Direction.DOWN);
                Vector3i vector3i = mutable.down();
                this.spawnFluidParticle((BlockPos)vector3i, this.getBlockState((BlockPos)vector3i), iParticleData, bl2);
            }
        }
        if (bl && blockState.isIn(Blocks.BARRIER)) {
            this.addParticle(ParticleTypes.BARRIER, (double)n5 + 0.5, (double)n6 + 0.5, (double)n7 + 0.5, 0.0, 0.0, 0.0);
        }
        if (!blockState.hasOpaqueCollisionShape(this, mutable)) {
            this.getBiome(mutable).getAmbientParticle().ifPresent(arg_0 -> this.lambda$animateTick$4(mutable, arg_0));
        }
    }

    private void spawnFluidParticle(BlockPos blockPos, BlockState blockState, IParticleData iParticleData, boolean bl) {
        if (blockState.getFluidState().isEmpty()) {
            VoxelShape voxelShape = blockState.getCollisionShape(this, blockPos);
            double d = voxelShape.getEnd(Direction.Axis.Y);
            if (d < 1.0) {
                if (bl) {
                    this.spawnParticle(blockPos.getX(), blockPos.getX() + 1, blockPos.getZ(), blockPos.getZ() + 1, (double)(blockPos.getY() + 1) - 0.05, iParticleData);
                }
            } else if (!blockState.isIn(BlockTags.IMPERMEABLE)) {
                double d2 = voxelShape.getStart(Direction.Axis.Y);
                if (d2 > 0.0) {
                    this.spawnParticle(blockPos, iParticleData, voxelShape, (double)blockPos.getY() + d2 - 0.05);
                } else {
                    BlockPos blockPos2 = blockPos.down();
                    BlockState blockState2 = this.getBlockState(blockPos2);
                    VoxelShape voxelShape2 = blockState2.getCollisionShape(this, blockPos2);
                    double d3 = voxelShape2.getEnd(Direction.Axis.Y);
                    if (d3 < 1.0 && blockState2.getFluidState().isEmpty()) {
                        this.spawnParticle(blockPos, iParticleData, voxelShape, (double)blockPos.getY() - 0.05);
                    }
                }
            }
        }
    }

    private void spawnParticle(BlockPos blockPos, IParticleData iParticleData, VoxelShape voxelShape, double d) {
        this.spawnParticle((double)blockPos.getX() + voxelShape.getStart(Direction.Axis.X), (double)blockPos.getX() + voxelShape.getEnd(Direction.Axis.X), (double)blockPos.getZ() + voxelShape.getStart(Direction.Axis.Z), (double)blockPos.getZ() + voxelShape.getEnd(Direction.Axis.Z), d, iParticleData);
    }

    private void spawnParticle(double d, double d2, double d3, double d4, double d5, IParticleData iParticleData) {
        this.addParticle(iParticleData, MathHelper.lerp(this.rand.nextDouble(), d, d2), d5, MathHelper.lerp(this.rand.nextDouble(), d3, d4), 0.0, 0.0, 0.0);
    }

    public void removeAllEntities() {
        Iterator iterator2 = this.entitiesById.int2ObjectEntrySet().iterator();
        while (iterator2.hasNext()) {
            Int2ObjectMap.Entry entry = (Int2ObjectMap.Entry)iterator2.next();
            Entity entity2 = (Entity)entry.getValue();
            if (!entity2.removed) continue;
            iterator2.remove();
            this.removeEntity(entity2);
        }
    }

    @Override
    public CrashReportCategory fillCrashReport(CrashReport crashReport) {
        CrashReportCategory crashReportCategory = super.fillCrashReport(crashReport);
        crashReportCategory.addDetail("Server brand", this::lambda$fillCrashReport$5);
        crashReportCategory.addDetail("Server type", this::lambda$fillCrashReport$6);
        return crashReportCategory;
    }

    @Override
    public void playSound(@Nullable PlayerEntity playerEntity, double d, double d2, double d3, SoundEvent soundEvent, SoundCategory soundCategory, float f, float f2) {
        if (Reflector.ForgeEventFactory_onPlaySoundAtEntity.exists()) {
            Object object = Reflector.ForgeEventFactory_onPlaySoundAtEntity.call(new Object[]{playerEntity, soundEvent, soundCategory, Float.valueOf(f), Float.valueOf(f2)});
            if (Reflector.callBoolean(object, Reflector.Event_isCanceled, new Object[0]) || Reflector.call(object, Reflector.PlaySoundAtEntityEvent_getSound, new Object[0]) == null) {
                return;
            }
            soundEvent = (SoundEvent)Reflector.call(object, Reflector.PlaySoundAtEntityEvent_getSound, new Object[0]);
            soundCategory = (SoundCategory)((Object)Reflector.call(object, Reflector.PlaySoundAtEntityEvent_getCategory, new Object[0]));
            f = Reflector.callFloat(object, Reflector.PlaySoundAtEntityEvent_getVolume, new Object[0]);
        }
        if (playerEntity == this.mc.player) {
            this.playSound(d, d2, d3, soundEvent, soundCategory, f, f2, true);
        }
    }

    @Override
    public void playMovingSound(@Nullable PlayerEntity playerEntity, Entity entity2, SoundEvent soundEvent, SoundCategory soundCategory, float f, float f2) {
        if (Reflector.ForgeEventFactory_onPlaySoundAtEntity.exists()) {
            Object object = Reflector.ForgeEventFactory_onPlaySoundAtEntity.call(new Object[]{playerEntity, soundEvent, soundCategory, Float.valueOf(f), Float.valueOf(f2)});
            if (Reflector.callBoolean(object, Reflector.Event_isCanceled, new Object[0]) || Reflector.call(object, Reflector.PlaySoundAtEntityEvent_getSound, new Object[0]) == null) {
                return;
            }
            soundEvent = (SoundEvent)Reflector.call(object, Reflector.PlaySoundAtEntityEvent_getSound, new Object[0]);
            soundCategory = (SoundCategory)((Object)Reflector.call(object, Reflector.PlaySoundAtEntityEvent_getCategory, new Object[0]));
            f = Reflector.callFloat(object, Reflector.PlaySoundAtEntityEvent_getVolume, new Object[0]);
        }
        if (playerEntity == this.mc.player) {
            this.mc.getSoundHandler().play(new EntityTickableSound(soundEvent, soundCategory, entity2));
        }
    }

    public void playSound(BlockPos blockPos, SoundEvent soundEvent, SoundCategory soundCategory, float f, float f2, boolean bl) {
        this.playSound((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, soundEvent, soundCategory, f, f2, bl);
    }

    @Override
    public void playSound(double d, double d2, double d3, SoundEvent soundEvent, SoundCategory soundCategory, float f, float f2, boolean bl) {
        double d4 = this.mc.gameRenderer.getActiveRenderInfo().getProjectedView().squareDistanceTo(d, d2, d3);
        SimpleSound simpleSound = new SimpleSound(soundEvent, soundCategory, f, f2, d, d2, d3);
        if (bl && d4 > 100.0) {
            double d5 = Math.sqrt(d4) / 40.0;
            this.mc.getSoundHandler().playDelayed(simpleSound, (int)(d5 * 20.0));
        } else {
            this.mc.getSoundHandler().play(simpleSound);
        }
    }

    @Override
    public void makeFireworks(double d, double d2, double d3, double d4, double d5, double d6, @Nullable CompoundNBT compoundNBT) {
        this.mc.particles.addEffect(new FireworkParticle.Starter(this, d, d2, d3, d4, d5, d6, this.mc.particles, compoundNBT));
    }

    @Override
    public void sendPacketToServer(IPacket<?> iPacket) {
        this.connection.sendPacket(iPacket);
    }

    @Override
    public RecipeManager getRecipeManager() {
        return this.connection.getRecipeManager();
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    @Override
    public ITickList<Block> getPendingBlockTicks() {
        return EmptyTickList.get();
    }

    @Override
    public ITickList<Fluid> getPendingFluidTicks() {
        return EmptyTickList.get();
    }

    @Override
    public ClientChunkProvider getChunkProvider() {
        return this.field_239129_E_;
    }

    @Override
    public boolean setBlockState(BlockPos blockPos, BlockState blockState, int n) {
        this.playerUpdate = this.isPlayerActing();
        boolean bl = super.setBlockState(blockPos, blockState, n);
        this.playerUpdate = false;
        return bl;
    }

    private boolean isPlayerActing() {
        if (this.mc.playerController instanceof PlayerControllerOF) {
            PlayerControllerOF playerControllerOF = (PlayerControllerOF)this.mc.playerController;
            return playerControllerOF.isActing();
        }
        return true;
    }

    public boolean isPlayerUpdate() {
        return this.playerUpdate;
    }

    public void onEntityAdded(Entity entity2) {
        RandomEntities.entityLoaded(entity2, this);
        if (Config.isDynamicLights()) {
            DynamicLights.entityAdded(entity2, Config.getRenderGlobal());
        }
    }

    public void onEntityRemoved(Entity entity2) {
        RandomEntities.entityUnloaded(entity2, this);
        if (Config.isDynamicLights()) {
            DynamicLights.entityRemoved(entity2, Config.getRenderGlobal());
        }
    }

    @Override
    @Nullable
    public MapData getMapData(String string) {
        return this.maps.get(string);
    }

    @Override
    public void registerMapData(MapData mapData) {
        this.maps.put(mapData.getName(), mapData);
    }

    @Override
    public int getNextMapId() {
        return 1;
    }

    @Override
    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    @Override
    public ITagCollectionSupplier getTags() {
        return this.connection.getTags();
    }

    @Override
    public DynamicRegistries func_241828_r() {
        return this.connection.func_239165_n_();
    }

    @Override
    public void notifyBlockUpdate(BlockPos blockPos, BlockState blockState, BlockState blockState2, int n) {
        this.worldRenderer.notifyBlockUpdate(this, blockPos, blockState, blockState2, n);
    }

    @Override
    public void markBlockRangeForRenderUpdate(BlockPos blockPos, BlockState blockState, BlockState blockState2) {
        this.worldRenderer.markBlockRangeForRenderUpdate(blockPos, blockState, blockState2);
    }

    public void markSurroundingsForRerender(int n, int n2, int n3) {
        this.worldRenderer.markSurroundingsForRerender(n, n2, n3);
    }

    @Override
    public void sendBlockBreakProgress(int n, BlockPos blockPos, int n2) {
        this.worldRenderer.sendBlockBreakProgress(n, blockPos, n2);
    }

    @Override
    public void playBroadcastSound(int n, BlockPos blockPos, int n2) {
        this.worldRenderer.broadcastSound(n, blockPos, n2);
    }

    @Override
    public void playEvent(@Nullable PlayerEntity playerEntity, int n, BlockPos blockPos, int n2) {
        try {
            this.worldRenderer.playEvent(playerEntity, n, blockPos, n2);
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Playing level event");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Level event being played");
            crashReportCategory.addDetail("Block coordinates", CrashReportCategory.getCoordinateInfo(blockPos));
            crashReportCategory.addDetail("Event source", playerEntity);
            crashReportCategory.addDetail("Event type", n);
            crashReportCategory.addDetail("Event data", n2);
            throw new ReportedException(crashReport);
        }
    }

    @Override
    public void addParticle(IParticleData iParticleData, double d, double d2, double d3, double d4, double d5, double d6) {
        this.worldRenderer.addParticle(iParticleData, iParticleData.getType().getAlwaysShow(), d, d2, d3, d4, d5, d6);
    }

    @Override
    public void addParticle(IParticleData iParticleData, boolean bl, double d, double d2, double d3, double d4, double d5, double d6) {
        this.worldRenderer.addParticle(iParticleData, iParticleData.getType().getAlwaysShow() || bl, d, d2, d3, d4, d5, d6);
    }

    @Override
    public void addOptionalParticle(IParticleData iParticleData, double d, double d2, double d3, double d4, double d5, double d6) {
        this.worldRenderer.addParticle(iParticleData, false, true, d, d2, d3, d4, d5, d6);
    }

    @Override
    public void addOptionalParticle(IParticleData iParticleData, boolean bl, double d, double d2, double d3, double d4, double d5, double d6) {
        this.worldRenderer.addParticle(iParticleData, iParticleData.getType().getAlwaysShow() || bl, true, d, d2, d3, d4, d5, d6);
    }

    public List<AbstractClientPlayerEntity> getPlayers() {
        return this.players;
    }

    @Override
    public Biome getNoiseBiomeRaw(int n, int n2, int n3) {
        return this.func_241828_r().getRegistry(Registry.BIOME_KEY).getOrThrow(Biomes.PLAINS);
    }

    public float getSunBrightness(float f) {
        float f2 = this.func_242415_f(f);
        float f3 = 1.0f - (MathHelper.cos(f2 * ((float)Math.PI * 2)) * 2.0f + 0.2f);
        f3 = MathHelper.clamp(f3, 0.0f, 1.0f);
        f3 = 1.0f - f3;
        f3 = (float)((double)f3 * (1.0 - (double)(this.getRainStrength(f) * 5.0f) / 16.0));
        f3 = (float)((double)f3 * (1.0 - (double)(this.getThunderStrength(f) * 5.0f) / 16.0));
        return f3 * 0.8f + 0.2f;
    }

    public Vector3d getSkyColor(BlockPos blockPos, float f) {
        float f2;
        float f3;
        float f4 = this.func_242415_f(f);
        float f5 = MathHelper.cos(f4 * ((float)Math.PI * 2)) * 2.0f + 0.5f;
        f5 = MathHelper.clamp(f5, 0.0f, 1.0f);
        Biome biome = this.getBiome(blockPos);
        int n = biome.getSkyColor();
        float f6 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f7 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f8 = (float)(n & 0xFF) / 255.0f;
        f6 *= f5;
        f7 *= f5;
        f8 *= f5;
        float f9 = this.getRainStrength(f);
        if (f9 > 0.0f) {
            f3 = (f6 * 0.3f + f7 * 0.59f + f8 * 0.11f) * 0.6f;
            f2 = 1.0f - f9 * 0.75f;
            f6 = f6 * f2 + f3 * (1.0f - f2);
            f7 = f7 * f2 + f3 * (1.0f - f2);
            f8 = f8 * f2 + f3 * (1.0f - f2);
        }
        if ((f3 = this.getThunderStrength(f)) > 0.0f) {
            f2 = (f6 * 0.3f + f7 * 0.59f + f8 * 0.11f) * 0.2f;
            float f10 = 1.0f - f3 * 0.75f;
            f6 = f6 * f10 + f2 * (1.0f - f10);
            f7 = f7 * f10 + f2 * (1.0f - f10);
            f8 = f8 * f10 + f2 * (1.0f - f10);
        }
        if (this.timeLightningFlash > 0) {
            f2 = (float)this.timeLightningFlash - f;
            if (f2 > 1.0f) {
                f2 = 1.0f;
            }
            f6 = f6 * (1.0f - (f2 *= 0.45f)) + 0.8f * f2;
            f7 = f7 * (1.0f - f2) + 0.8f * f2;
            f8 = f8 * (1.0f - f2) + 1.0f * f2;
        }
        return new Vector3d(f6, f7, f8);
    }

    public Vector3d getCloudColor(float f) {
        float f2;
        float f3;
        float f4 = this.func_242415_f(f);
        float f5 = MathHelper.cos(f4 * ((float)Math.PI * 2)) * 2.0f + 0.5f;
        f5 = MathHelper.clamp(f5, 0.0f, 1.0f);
        float f6 = 1.0f;
        float f7 = 1.0f;
        float f8 = 1.0f;
        float f9 = this.getRainStrength(f);
        if (f9 > 0.0f) {
            f3 = (f6 * 0.3f + f7 * 0.59f + f8 * 0.11f) * 0.6f;
            f2 = 1.0f - f9 * 0.95f;
            f6 = f6 * f2 + f3 * (1.0f - f2);
            f7 = f7 * f2 + f3 * (1.0f - f2);
            f8 = f8 * f2 + f3 * (1.0f - f2);
        }
        f6 *= f5 * 0.9f + 0.1f;
        f7 *= f5 * 0.9f + 0.1f;
        f8 *= f5 * 0.85f + 0.15f;
        f3 = this.getThunderStrength(f);
        if (f3 > 0.0f) {
            f2 = (f6 * 0.3f + f7 * 0.59f + f8 * 0.11f) * 0.2f;
            float f10 = 1.0f - f3 * 0.95f;
            f6 = f6 * f10 + f2 * (1.0f - f10);
            f7 = f7 * f10 + f2 * (1.0f - f10);
            f8 = f8 * f10 + f2 * (1.0f - f10);
        }
        return new Vector3d(f6, f7, f8);
    }

    public float getStarBrightness(float f) {
        float f2 = this.func_242415_f(f);
        float f3 = 1.0f - (MathHelper.cos(f2 * ((float)Math.PI * 2)) * 2.0f + 0.25f);
        f3 = MathHelper.clamp(f3, 0.0f, 1.0f);
        return f3 * f3 * 0.5f;
    }

    public int getTimeLightningFlash() {
        return this.timeLightningFlash;
    }

    @Override
    public void setTimeLightningFlash(int n) {
        this.timeLightningFlash = n;
    }

    @Override
    public float func_230487_a_(Direction direction, boolean bl) {
        boolean bl2 = this.func_239132_a_().func_239217_c_();
        boolean bl3 = Config.isShaders();
        if (!bl) {
            return bl2 ? 0.9f : 1.0f;
        }
        switch (1.$SwitchMap$net$minecraft$util$Direction[direction.ordinal()]) {
            case 1: {
                return bl2 ? 0.9f : (bl3 ? Shaders.blockLightLevel05 : 0.5f);
            }
            case 2: {
                return bl2 ? 0.9f : 1.0f;
            }
            case 3: 
            case 4: {
                if (Config.isShaders()) {
                    return Shaders.blockLightLevel08;
                }
                return 0.8f;
            }
            case 5: 
            case 6: {
                if (Config.isShaders()) {
                    return Shaders.blockLightLevel06;
                }
                return 0.6f;
            }
        }
        return 1.0f;
    }

    @Override
    public int getBlockColor(BlockPos blockPos, ColorResolver colorResolver) {
        ColorCache colorCache = this.colorCaches.get(colorResolver);
        return colorCache.getColor(blockPos, () -> this.lambda$getBlockColor$7(blockPos, colorResolver));
    }

    public int getBlockColorRaw(BlockPos blockPos, ColorResolver colorResolver) {
        int n = Minecraft.getInstance().gameSettings.biomeBlendRadius;
        if (n == 0) {
            return colorResolver.getColor(this.getBiome(blockPos), blockPos.getX(), blockPos.getZ());
        }
        int n2 = (n * 2 + 1) * (n * 2 + 1);
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        CubeCoordinateIterator cubeCoordinateIterator = new CubeCoordinateIterator(blockPos.getX() - n, blockPos.getY(), blockPos.getZ() - n, blockPos.getX() + n, blockPos.getY(), blockPos.getZ() + n);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        while (cubeCoordinateIterator.hasNext()) {
            mutable.setPos(cubeCoordinateIterator.getX(), cubeCoordinateIterator.getY(), cubeCoordinateIterator.getZ());
            int n6 = colorResolver.getColor(this.getBiome(mutable), mutable.getX(), mutable.getZ());
            n3 += (n6 & 0xFF0000) >> 16;
            n4 += (n6 & 0xFF00) >> 8;
            n5 += n6 & 0xFF;
        }
        return (n3 / n2 & 0xFF) << 16 | (n4 / n2 & 0xFF) << 8 | n5 / n2 & 0xFF;
    }

    public BlockPos func_239140_u_() {
        BlockPos blockPos = new BlockPos(this.worldInfo.getSpawnX(), this.worldInfo.getSpawnY(), this.worldInfo.getSpawnZ());
        if (!this.getWorldBorder().contains(blockPos)) {
            blockPos = this.getHeight(Heightmap.Type.MOTION_BLOCKING, new BlockPos(this.getWorldBorder().getCenterX(), 0.0, this.getWorldBorder().getCenterZ()));
        }
        return blockPos;
    }

    public float func_243489_v() {
        return this.worldInfo.getSpawnAngle();
    }

    public void func_239136_a_(BlockPos blockPos, float f) {
        this.worldInfo.setSpawn(blockPos, f);
    }

    public String toString() {
        return "ClientLevel";
    }

    @Override
    public ClientWorldInfo getWorldInfo() {
        return this.field_239130_d_;
    }

    @Override
    public IWorldInfo getWorldInfo() {
        return this.getWorldInfo();
    }

    @Override
    public AbstractChunkProvider getChunkProvider() {
        return this.getChunkProvider();
    }

    private int lambda$getBlockColor$7(BlockPos blockPos, ColorResolver colorResolver) {
        return this.getBlockColorRaw(blockPos, colorResolver);
    }

    private String lambda$fillCrashReport$6() throws Exception {
        return this.mc.getIntegratedServer() == null ? "Non-integrated multiplayer server" : "Integrated singleplayer server";
    }

    private String lambda$fillCrashReport$5() throws Exception {
        return this.mc.player.getServerBrand();
    }

    private void lambda$animateTick$4(BlockPos.Mutable mutable, ParticleEffectAmbience particleEffectAmbience) {
        if (particleEffectAmbience.shouldParticleSpawn(this.rand)) {
            this.addParticle(particleEffectAmbience.getParticleOptions(), (double)mutable.getX() + this.rand.nextDouble(), (double)mutable.getY() + this.rand.nextDouble(), (double)mutable.getZ() + this.rand.nextDouble(), 0.0, 0.0, 0.0);
        }
    }

    private static void lambda$clearColorCaches$3(ColorResolver colorResolver, ColorCache colorCache) {
        colorCache.invalidateAll();
    }

    private static void lambda$onChunkLoaded$2(int n, int n2, ColorResolver colorResolver, ColorCache colorCache) {
        colorCache.invalidateChunk(n, n2);
    }

    private static String lambda$updateEntity$1(Entity entity2) {
        return Registry.ENTITY_TYPE.getKey(entity2.getType()).toString();
    }

    private static void lambda$new$0(Object2ObjectArrayMap object2ObjectArrayMap) {
        object2ObjectArrayMap.put(BiomeColors.GRASS_COLOR, new ColorCache());
        object2ObjectArrayMap.put(BiomeColors.FOLIAGE_COLOR, new ColorCache());
        object2ObjectArrayMap.put(BiomeColors.WATER_COLOR, new ColorCache());
    }

    public static class ClientWorldInfo
    implements ISpawnWorldInfo {
        private final boolean hardcore;
        private final GameRules gameRules;
        private final boolean flatWorld;
        private int spawnX;
        private int spawnY;
        private int spawnZ;
        private float field_243490_g;
        private long gameTime;
        private long dayTime;
        private boolean raining;
        private Difficulty difficulty;
        private boolean field_239154_k_;

        public ClientWorldInfo(Difficulty difficulty, boolean bl, boolean bl2) {
            this.difficulty = difficulty;
            this.hardcore = bl;
            this.flatWorld = bl2;
            this.gameRules = new GameRules();
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
            return this.field_243490_g;
        }

        @Override
        public long getGameTime() {
            return this.gameTime;
        }

        @Override
        public long getDayTime() {
            return this.dayTime;
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
            this.field_243490_g = f;
        }

        public void setGameTime(long l) {
            this.gameTime = l;
        }

        public void setDayTime(long l) {
            this.dayTime = l;
        }

        @Override
        public void setSpawn(BlockPos blockPos, float f) {
            this.spawnX = blockPos.getX();
            this.spawnY = blockPos.getY();
            this.spawnZ = blockPos.getZ();
            this.field_243490_g = f;
        }

        @Override
        public boolean isThundering() {
            return true;
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
        public boolean isHardcore() {
            return this.hardcore;
        }

        @Override
        public GameRules getGameRulesInstance() {
            return this.gameRules;
        }

        @Override
        public Difficulty getDifficulty() {
            return this.difficulty;
        }

        @Override
        public boolean isDifficultyLocked() {
            return this.field_239154_k_;
        }

        @Override
        public void addToCrashReport(CrashReportCategory crashReportCategory) {
            ISpawnWorldInfo.super.addToCrashReport(crashReportCategory);
        }

        public void setDifficulty(Difficulty difficulty) {
            Reflector.ForgeHooks_onDifficultyChange.callVoid(new Object[]{difficulty, this.difficulty});
            this.difficulty = difficulty;
        }

        public void setDifficultyLocked(boolean bl) {
            this.field_239154_k_ = bl;
        }

        public double getVoidFogHeight() {
            return this.flatWorld ? 0.0 : 63.0;
        }

        public double getFogDistance() {
            return this.flatWorld ? 1.0 : 0.03125;
        }
    }
}

