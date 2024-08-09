/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.end;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.EndPortalTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Unit;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.end.DragonSpawnState;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndPodiumFeature;
import net.minecraft.world.gen.feature.EndSpikeFeature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.server.ChunkHolder;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DragonFightManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Predicate<Entity> VALID_PLAYER = EntityPredicates.IS_ALIVE.and(EntityPredicates.withinRange(0.0, 128.0, 0.0, 192.0));
    private final ServerBossInfo bossInfo = (ServerBossInfo)new ServerBossInfo(new TranslationTextComponent("entity.minecraft.ender_dragon"), BossInfo.Color.PINK, BossInfo.Overlay.PROGRESS).setPlayEndBossMusic(false).setCreateFog(false);
    private final ServerWorld world;
    private final List<Integer> gateways = Lists.newArrayList();
    private final BlockPattern portalPattern;
    private int ticksSinceDragonSeen;
    private int aliveCrystals;
    private int ticksSinceCrystalsScanned;
    private int ticksSinceLastPlayerScan;
    private boolean dragonKilled;
    private boolean previouslyKilled;
    private UUID dragonUniqueId;
    private boolean scanForLegacyFight = true;
    private BlockPos exitPortalLocation;
    private DragonSpawnState respawnState;
    private int respawnStateTicks;
    private List<EnderCrystalEntity> crystals;

    public DragonFightManager(ServerWorld serverWorld, long l, CompoundNBT compoundNBT) {
        this.world = serverWorld;
        if (compoundNBT.contains("DragonKilled", 0)) {
            if (compoundNBT.hasUniqueId("Dragon")) {
                this.dragonUniqueId = compoundNBT.getUniqueId("Dragon");
            }
            this.dragonKilled = compoundNBT.getBoolean("DragonKilled");
            this.previouslyKilled = compoundNBT.getBoolean("PreviouslyKilled");
            if (compoundNBT.getBoolean("IsRespawning")) {
                this.respawnState = DragonSpawnState.START;
            }
            if (compoundNBT.contains("ExitPortalLocation", 1)) {
                this.exitPortalLocation = NBTUtil.readBlockPos(compoundNBT.getCompound("ExitPortalLocation"));
            }
        } else {
            this.dragonKilled = true;
            this.previouslyKilled = true;
        }
        if (compoundNBT.contains("Gateways", 0)) {
            ListNBT listNBT = compoundNBT.getList("Gateways", 3);
            for (int i = 0; i < listNBT.size(); ++i) {
                this.gateways.add(listNBT.getInt(i));
            }
        } else {
            this.gateways.addAll(ContiguousSet.create(Range.closedOpen(0, 20), DiscreteDomain.integers()));
            Collections.shuffle(this.gateways, new Random(l));
        }
        this.portalPattern = BlockPatternBuilder.start().aisle("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").aisle("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").aisle("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").aisle("  ###  ", " #   # ", "#     #", "#  #  #", "#     #", " #   # ", "  ###  ").aisle("       ", "  ###  ", " ##### ", " ##### ", " ##### ", "  ###  ", "       ").where('#', CachedBlockInfo.hasState(BlockMatcher.forBlock(Blocks.BEDROCK))).build();
    }

    public CompoundNBT write() {
        CompoundNBT compoundNBT = new CompoundNBT();
        if (this.dragonUniqueId != null) {
            compoundNBT.putUniqueId("Dragon", this.dragonUniqueId);
        }
        compoundNBT.putBoolean("DragonKilled", this.dragonKilled);
        compoundNBT.putBoolean("PreviouslyKilled", this.previouslyKilled);
        if (this.exitPortalLocation != null) {
            compoundNBT.put("ExitPortalLocation", NBTUtil.writeBlockPos(this.exitPortalLocation));
        }
        ListNBT listNBT = new ListNBT();
        for (int n : this.gateways) {
            listNBT.add(IntNBT.valueOf(n));
        }
        compoundNBT.put("Gateways", listNBT);
        return compoundNBT;
    }

    public void tick() {
        this.bossInfo.setVisible(!this.dragonKilled);
        if (++this.ticksSinceLastPlayerScan >= 20) {
            this.updatePlayers();
            this.ticksSinceLastPlayerScan = 0;
        }
        if (!this.bossInfo.getPlayers().isEmpty()) {
            this.world.getChunkProvider().registerTicket(TicketType.DRAGON, new ChunkPos(0, 0), 9, Unit.INSTANCE);
            boolean bl = this.isFightAreaLoaded();
            if (this.scanForLegacyFight && bl) {
                this.scanForLegacyFight();
                this.scanForLegacyFight = false;
            }
            if (this.respawnState != null) {
                if (this.crystals == null && bl) {
                    this.respawnState = null;
                    this.tryRespawnDragon();
                }
                this.respawnState.process(this.world, this, this.crystals, this.respawnStateTicks++, this.exitPortalLocation);
            }
            if (!this.dragonKilled) {
                if ((this.dragonUniqueId == null || ++this.ticksSinceDragonSeen >= 1200) && bl) {
                    this.findOrCreateDragon();
                    this.ticksSinceDragonSeen = 0;
                }
                if (++this.ticksSinceCrystalsScanned >= 100 && bl) {
                    this.findAliveCrystals();
                    this.ticksSinceCrystalsScanned = 0;
                }
            }
        } else {
            this.world.getChunkProvider().releaseTicket(TicketType.DRAGON, new ChunkPos(0, 0), 9, Unit.INSTANCE);
        }
    }

    private void scanForLegacyFight() {
        LOGGER.info("Scanning for legacy world dragon fight...");
        boolean bl = this.exitPortalExists();
        if (bl) {
            LOGGER.info("Found that the dragon has been killed in this world already.");
            this.previouslyKilled = true;
        } else {
            LOGGER.info("Found that the dragon has not yet been killed in this world.");
            this.previouslyKilled = false;
            if (this.findExitPortal() == null) {
                this.generatePortal(true);
            }
        }
        List<EnderDragonEntity> list = this.world.getDragons();
        if (list.isEmpty()) {
            this.dragonKilled = true;
        } else {
            EnderDragonEntity enderDragonEntity = list.get(0);
            this.dragonUniqueId = enderDragonEntity.getUniqueID();
            LOGGER.info("Found that there's a dragon still alive ({})", (Object)enderDragonEntity);
            this.dragonKilled = false;
            if (!bl) {
                LOGGER.info("But we didn't have a portal, let's remove it.");
                enderDragonEntity.remove();
                this.dragonUniqueId = null;
            }
        }
        if (!this.previouslyKilled && this.dragonKilled) {
            this.dragonKilled = false;
        }
    }

    private void findOrCreateDragon() {
        List<EnderDragonEntity> list = this.world.getDragons();
        if (list.isEmpty()) {
            LOGGER.debug("Haven't seen the dragon, respawning it");
            this.createNewDragon();
        } else {
            LOGGER.debug("Haven't seen our dragon, but found another one to use.");
            this.dragonUniqueId = list.get(0).getUniqueID();
        }
    }

    protected void setRespawnState(DragonSpawnState dragonSpawnState) {
        if (this.respawnState == null) {
            throw new IllegalStateException("Dragon respawn isn't in progress, can't skip ahead in the animation.");
        }
        this.respawnStateTicks = 0;
        if (dragonSpawnState == DragonSpawnState.END) {
            this.respawnState = null;
            this.dragonKilled = false;
            EnderDragonEntity enderDragonEntity = this.createNewDragon();
            for (ServerPlayerEntity serverPlayerEntity : this.bossInfo.getPlayers()) {
                CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayerEntity, enderDragonEntity);
            }
        } else {
            this.respawnState = dragonSpawnState;
        }
    }

    private boolean exitPortalExists() {
        for (int i = -8; i <= 8; ++i) {
            for (int j = -8; j <= 8; ++j) {
                Chunk chunk = this.world.getChunk(i, j);
                for (TileEntity tileEntity : chunk.getTileEntityMap().values()) {
                    if (!(tileEntity instanceof EndPortalTileEntity)) continue;
                    return false;
                }
            }
        }
        return true;
    }

    @Nullable
    private BlockPattern.PatternHelper findExitPortal() {
        Object object;
        int n;
        int n2;
        for (n2 = -8; n2 <= 8; ++n2) {
            for (n = -8; n <= 8; ++n) {
                object = this.world.getChunk(n2, n);
                for (TileEntity tileEntity : ((Chunk)object).getTileEntityMap().values()) {
                    BlockPattern.PatternHelper patternHelper;
                    if (!(tileEntity instanceof EndPortalTileEntity) || (patternHelper = this.portalPattern.match(this.world, tileEntity.getPos())) == null) continue;
                    BlockPos blockPos = patternHelper.translateOffset(3, 3, 3).getPos();
                    if (this.exitPortalLocation == null && blockPos.getX() == 0 && blockPos.getZ() == 0) {
                        this.exitPortalLocation = blockPos;
                    }
                    return patternHelper;
                }
            }
        }
        for (n = n2 = this.world.getHeight(Heightmap.Type.MOTION_BLOCKING, EndPodiumFeature.END_PODIUM_LOCATION).getY(); n >= 0; --n) {
            object = this.portalPattern.match(this.world, new BlockPos(EndPodiumFeature.END_PODIUM_LOCATION.getX(), n, EndPodiumFeature.END_PODIUM_LOCATION.getZ()));
            if (object == null) continue;
            if (this.exitPortalLocation == null) {
                this.exitPortalLocation = ((BlockPattern.PatternHelper)object).translateOffset(3, 3, 3).getPos();
            }
            return object;
        }
        return null;
    }

    private boolean isFightAreaLoaded() {
        for (int i = -8; i <= 8; ++i) {
            for (int j = 8; j <= 8; ++j) {
                IChunk iChunk = this.world.getChunk(i, j, ChunkStatus.FULL, true);
                if (!(iChunk instanceof Chunk)) {
                    return true;
                }
                ChunkHolder.LocationType locationType = ((Chunk)iChunk).getLocationType();
                if (locationType.isAtLeast(ChunkHolder.LocationType.TICKING)) continue;
                return true;
            }
        }
        return false;
    }

    private void updatePlayers() {
        HashSet<ServerPlayerEntity> hashSet = Sets.newHashSet();
        for (ServerPlayerEntity object2 : this.world.getPlayers(VALID_PLAYER)) {
            this.bossInfo.addPlayer(object2);
            hashSet.add(object2);
        }
        HashSet<ServerPlayerEntity> hashSet2 = Sets.newHashSet(this.bossInfo.getPlayers());
        hashSet2.removeAll(hashSet);
        Iterator iterator2 = hashSet2.iterator();
        while (iterator2.hasNext()) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)iterator2.next();
            this.bossInfo.removePlayer(serverPlayerEntity);
        }
    }

    private void findAliveCrystals() {
        this.ticksSinceCrystalsScanned = 0;
        this.aliveCrystals = 0;
        for (EndSpikeFeature.EndSpike endSpike : EndSpikeFeature.func_236356_a_(this.world)) {
            this.aliveCrystals += this.world.getEntitiesWithinAABB(EnderCrystalEntity.class, endSpike.getTopBoundingBox()).size();
        }
        LOGGER.debug("Found {} end crystals still alive", (Object)this.aliveCrystals);
    }

    public void processDragonDeath(EnderDragonEntity enderDragonEntity) {
        if (enderDragonEntity.getUniqueID().equals(this.dragonUniqueId)) {
            this.bossInfo.setPercent(0.0f);
            this.bossInfo.setVisible(true);
            this.generatePortal(false);
            this.spawnNewGateway();
            if (!this.previouslyKilled) {
                this.world.setBlockState(this.world.getHeight(Heightmap.Type.MOTION_BLOCKING, EndPodiumFeature.END_PODIUM_LOCATION), Blocks.DRAGON_EGG.getDefaultState());
            }
            this.previouslyKilled = true;
            this.dragonKilled = true;
        }
    }

    private void spawnNewGateway() {
        if (!this.gateways.isEmpty()) {
            int n = this.gateways.remove(this.gateways.size() - 1);
            int n2 = MathHelper.floor(96.0 * Math.cos(2.0 * (-Math.PI + 0.15707963267948966 * (double)n)));
            int n3 = MathHelper.floor(96.0 * Math.sin(2.0 * (-Math.PI + 0.15707963267948966 * (double)n)));
            this.generateGateway(new BlockPos(n2, 75, n3));
        }
    }

    private void generateGateway(BlockPos blockPos) {
        this.world.playEvent(3000, blockPos, 0);
        Features.END_GATEWAY_DELAYED.func_242765_a(this.world, this.world.getChunkProvider().getChunkGenerator(), new Random(), blockPos);
    }

    private void generatePortal(boolean bl) {
        EndPodiumFeature endPodiumFeature = new EndPodiumFeature(bl);
        if (this.exitPortalLocation == null) {
            this.exitPortalLocation = this.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.END_PODIUM_LOCATION).down();
            while (this.world.getBlockState(this.exitPortalLocation).isIn(Blocks.BEDROCK) && this.exitPortalLocation.getY() > this.world.getSeaLevel()) {
                this.exitPortalLocation = this.exitPortalLocation.down();
            }
        }
        endPodiumFeature.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).func_242765_a(this.world, this.world.getChunkProvider().getChunkGenerator(), new Random(), this.exitPortalLocation);
    }

    private EnderDragonEntity createNewDragon() {
        this.world.getChunkAt(new BlockPos(0, 128, 0));
        EnderDragonEntity enderDragonEntity = EntityType.ENDER_DRAGON.create(this.world);
        enderDragonEntity.getPhaseManager().setPhase(PhaseType.HOLDING_PATTERN);
        enderDragonEntity.setLocationAndAngles(0.0, 128.0, 0.0, this.world.rand.nextFloat() * 360.0f, 0.0f);
        this.world.addEntity(enderDragonEntity);
        this.dragonUniqueId = enderDragonEntity.getUniqueID();
        return enderDragonEntity;
    }

    public void dragonUpdate(EnderDragonEntity enderDragonEntity) {
        if (enderDragonEntity.getUniqueID().equals(this.dragonUniqueId)) {
            this.bossInfo.setPercent(enderDragonEntity.getHealth() / enderDragonEntity.getMaxHealth());
            this.ticksSinceDragonSeen = 0;
            if (enderDragonEntity.hasCustomName()) {
                this.bossInfo.setName(enderDragonEntity.getDisplayName());
            }
        }
    }

    public int getNumAliveCrystals() {
        return this.aliveCrystals;
    }

    public void onCrystalDestroyed(EnderCrystalEntity enderCrystalEntity, DamageSource damageSource) {
        if (this.respawnState != null && this.crystals.contains(enderCrystalEntity)) {
            LOGGER.debug("Aborting respawn sequence");
            this.respawnState = null;
            this.respawnStateTicks = 0;
            this.resetSpikeCrystals();
            this.generatePortal(false);
        } else {
            this.findAliveCrystals();
            Entity entity2 = this.world.getEntityByUuid(this.dragonUniqueId);
            if (entity2 instanceof EnderDragonEntity) {
                ((EnderDragonEntity)entity2).onCrystalDestroyed(enderCrystalEntity, enderCrystalEntity.getPosition(), damageSource);
            }
        }
    }

    public boolean hasPreviouslyKilledDragon() {
        return this.previouslyKilled;
    }

    public void tryRespawnDragon() {
        if (this.dragonKilled && this.respawnState == null) {
            Object object;
            BlockPos blockPos = this.exitPortalLocation;
            if (blockPos == null) {
                LOGGER.debug("Tried to respawn, but need to find the portal first.");
                object = this.findExitPortal();
                if (object == null) {
                    LOGGER.debug("Couldn't find a portal, so we made one.");
                    this.generatePortal(false);
                } else {
                    LOGGER.debug("Found the exit portal & temporarily using it.");
                }
                blockPos = this.exitPortalLocation;
            }
            object = Lists.newArrayList();
            BlockPos blockPos2 = blockPos.up(1);
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                List<EnderCrystalEntity> list = this.world.getEntitiesWithinAABB(EnderCrystalEntity.class, new AxisAlignedBB(blockPos2.offset(direction, 2)));
                if (list.isEmpty()) {
                    return;
                }
                object.addAll(list);
            }
            LOGGER.debug("Found all crystals, respawning dragon.");
            this.respawnDragon((List<EnderCrystalEntity>)object);
        }
    }

    private void respawnDragon(List<EnderCrystalEntity> list) {
        if (this.dragonKilled && this.respawnState == null) {
            BlockPattern.PatternHelper patternHelper = this.findExitPortal();
            while (patternHelper != null) {
                for (int i = 0; i < this.portalPattern.getPalmLength(); ++i) {
                    for (int j = 0; j < this.portalPattern.getThumbLength(); ++j) {
                        for (int k = 0; k < this.portalPattern.getFingerLength(); ++k) {
                            CachedBlockInfo cachedBlockInfo = patternHelper.translateOffset(i, j, k);
                            if (!cachedBlockInfo.getBlockState().isIn(Blocks.BEDROCK) && !cachedBlockInfo.getBlockState().isIn(Blocks.END_PORTAL)) continue;
                            this.world.setBlockState(cachedBlockInfo.getPos(), Blocks.END_STONE.getDefaultState());
                        }
                    }
                }
                patternHelper = this.findExitPortal();
            }
            this.respawnState = DragonSpawnState.START;
            this.respawnStateTicks = 0;
            this.generatePortal(true);
            this.crystals = list;
        }
    }

    public void resetSpikeCrystals() {
        for (EndSpikeFeature.EndSpike endSpike : EndSpikeFeature.func_236356_a_(this.world)) {
            for (EnderCrystalEntity enderCrystalEntity : this.world.getEntitiesWithinAABB(EnderCrystalEntity.class, endSpike.getTopBoundingBox())) {
                enderCrystalEntity.setInvulnerable(true);
                enderCrystalEntity.setBeamTarget(null);
            }
        }
    }
}

