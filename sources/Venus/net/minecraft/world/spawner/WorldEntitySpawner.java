/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.spawner;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeMagnifier;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.MobDensityTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class WorldEntitySpawner {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int field_234960_b_ = (int)Math.pow(17.0, 2.0);
    private static final EntityClassification[] field_234961_c_ = (EntityClassification[])Stream.of(EntityClassification.values()).filter(WorldEntitySpawner::lambda$static$0).toArray(WorldEntitySpawner::lambda$static$1);

    public static EntityDensityManager func_234964_a_(int n, Iterable<Entity> iterable, IInitialDensityAdder iInitialDensityAdder) {
        MobDensityTracker mobDensityTracker = new MobDensityTracker();
        Object2IntOpenHashMap<EntityClassification> object2IntOpenHashMap = new Object2IntOpenHashMap<EntityClassification>();
        Iterator<Entity> iterator2 = iterable.iterator();
        while (iterator2.hasNext()) {
            MobEntity mobEntity;
            Entity entity2 = iterator2.next();
            if (entity2 instanceof MobEntity && ((mobEntity = (MobEntity)entity2).isNoDespawnRequired() || mobEntity.preventDespawn())) continue;
            Entity entity3 = entity2;
            EntityClassification entityClassification = entity2.getType().getClassification();
            if (entityClassification == EntityClassification.MISC) continue;
            BlockPos blockPos = entity2.getPosition();
            long l = ChunkPos.asLong(blockPos.getX() >> 4, blockPos.getZ() >> 4);
            iInitialDensityAdder.query(l, arg_0 -> WorldEntitySpawner.lambda$func_234964_a_$2(blockPos, entity3, mobDensityTracker, object2IntOpenHashMap, entityClassification, arg_0));
        }
        return new EntityDensityManager(n, object2IntOpenHashMap, mobDensityTracker);
    }

    private static Biome func_234980_b_(BlockPos blockPos, IChunk iChunk) {
        return DefaultBiomeMagnifier.INSTANCE.getBiome(0L, blockPos.getX(), blockPos.getY(), blockPos.getZ(), iChunk.getBiomes());
    }

    public static void func_234979_a_(ServerWorld serverWorld, Chunk chunk, EntityDensityManager entityDensityManager, boolean bl, boolean bl2, boolean bl3) {
        serverWorld.getProfiler().startSection("spawner");
        for (EntityClassification entityClassification : field_234961_c_) {
            if (!bl && entityClassification.getPeacefulCreature() || !bl2 && !entityClassification.getPeacefulCreature() || !bl3 && entityClassification.getAnimal() || !entityDensityManager.func_234991_a_(entityClassification)) continue;
            WorldEntitySpawner.func_234967_a_(entityClassification, serverWorld, chunk, (arg_0, arg_1, arg_2) -> WorldEntitySpawner.lambda$func_234979_a_$3(entityDensityManager, arg_0, arg_1, arg_2), (arg_0, arg_1) -> WorldEntitySpawner.lambda$func_234979_a_$4(entityDensityManager, arg_0, arg_1));
        }
        serverWorld.getProfiler().endSection();
    }

    public static void func_234967_a_(EntityClassification entityClassification, ServerWorld serverWorld, Chunk chunk, IDensityCheck iDensityCheck, IOnSpawnDensityAdder iOnSpawnDensityAdder) {
        BlockPos blockPos = WorldEntitySpawner.getRandomHeight(serverWorld, chunk);
        if (blockPos.getY() >= 1) {
            WorldEntitySpawner.func_234966_a_(entityClassification, serverWorld, chunk, blockPos, iDensityCheck, iOnSpawnDensityAdder);
        }
    }

    public static void func_234966_a_(EntityClassification entityClassification, ServerWorld serverWorld, IChunk iChunk, BlockPos blockPos, IDensityCheck iDensityCheck, IOnSpawnDensityAdder iOnSpawnDensityAdder) {
        StructureManager structureManager = serverWorld.func_241112_a_();
        ChunkGenerator chunkGenerator = serverWorld.getChunkProvider().getChunkGenerator();
        int n = blockPos.getY();
        BlockState blockState = iChunk.getBlockState(blockPos);
        if (!blockState.isNormalCube(iChunk, blockPos)) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            int n2 = 0;
            block0: for (int i = 0; i < 3; ++i) {
                int n3 = blockPos.getX();
                int n4 = blockPos.getZ();
                int n5 = 6;
                MobSpawnInfo.Spawners spawners = null;
                ILivingEntityData iLivingEntityData = null;
                int n6 = MathHelper.ceil(serverWorld.rand.nextFloat() * 4.0f);
                int n7 = 0;
                for (int j = 0; j < n6; ++j) {
                    double d;
                    mutable.setPos(n3 += serverWorld.rand.nextInt(6) - serverWorld.rand.nextInt(6), n, n4 += serverWorld.rand.nextInt(6) - serverWorld.rand.nextInt(6));
                    double d2 = (double)n3 + 0.5;
                    double d3 = (double)n4 + 0.5;
                    PlayerEntity playerEntity = serverWorld.getClosestPlayer(d2, (double)n, d3, -1.0, true);
                    if (playerEntity == null || !WorldEntitySpawner.func_234978_a_(serverWorld, iChunk, mutable, d = playerEntity.getDistanceSq(d2, n, d3))) continue;
                    if (spawners == null) {
                        spawners = WorldEntitySpawner.func_234977_a_(serverWorld, structureManager, chunkGenerator, entityClassification, serverWorld.rand, mutable);
                        if (spawners == null) continue block0;
                        n6 = spawners.minCount + serverWorld.rand.nextInt(1 + spawners.maxCount - spawners.minCount);
                    }
                    if (!WorldEntitySpawner.func_234975_a_(serverWorld, entityClassification, structureManager, chunkGenerator, spawners, mutable, d) || !iDensityCheck.test(spawners.type, mutable, iChunk)) continue;
                    MobEntity mobEntity = WorldEntitySpawner.func_234973_a_(serverWorld, spawners.type);
                    if (mobEntity == null) {
                        return;
                    }
                    mobEntity.setLocationAndAngles(d2, n, d3, serverWorld.rand.nextFloat() * 360.0f, 0.0f);
                    if (!WorldEntitySpawner.func_234974_a_(serverWorld, mobEntity, d)) continue;
                    iLivingEntityData = mobEntity.onInitialSpawn(serverWorld, serverWorld.getDifficultyForLocation(mobEntity.getPosition()), SpawnReason.NATURAL, iLivingEntityData, null);
                    ++n7;
                    serverWorld.func_242417_l(mobEntity);
                    iOnSpawnDensityAdder.run(mobEntity, iChunk);
                    if (++n2 >= mobEntity.getMaxSpawnedInChunk()) {
                        return;
                    }
                    if (mobEntity.isMaxGroupSize(n7)) continue block0;
                }
            }
        }
    }

    private static boolean func_234978_a_(ServerWorld serverWorld, IChunk iChunk, BlockPos.Mutable mutable, double d) {
        if (d <= 576.0) {
            return true;
        }
        if (serverWorld.getSpawnPoint().withinDistance(new Vector3d((double)mutable.getX() + 0.5, mutable.getY(), (double)mutable.getZ() + 0.5), 24.0)) {
            return true;
        }
        ChunkPos chunkPos = new ChunkPos(mutable);
        return Objects.equals(chunkPos, iChunk.getPos()) || serverWorld.getChunkProvider().isChunkLoaded(chunkPos);
    }

    private static boolean func_234975_a_(ServerWorld serverWorld, EntityClassification entityClassification, StructureManager structureManager, ChunkGenerator chunkGenerator, MobSpawnInfo.Spawners spawners, BlockPos.Mutable mutable, double d) {
        EntityType<?> entityType = spawners.type;
        if (entityType.getClassification() == EntityClassification.MISC) {
            return true;
        }
        if (!entityType.func_225437_d() && d > (double)(entityType.getClassification().getInstantDespawnDistance() * entityType.getClassification().getInstantDespawnDistance())) {
            return true;
        }
        if (entityType.isSummonable() && WorldEntitySpawner.func_234976_a_(serverWorld, structureManager, chunkGenerator, entityClassification, spawners, mutable)) {
            EntitySpawnPlacementRegistry.PlacementType placementType = EntitySpawnPlacementRegistry.getPlacementType(entityType);
            if (!WorldEntitySpawner.canCreatureTypeSpawnAtLocation(placementType, serverWorld, mutable, entityType)) {
                return true;
            }
            if (!EntitySpawnPlacementRegistry.canSpawnEntity(entityType, serverWorld, SpawnReason.NATURAL, mutable, serverWorld.rand)) {
                return true;
            }
            return serverWorld.hasNoCollisions(entityType.getBoundingBoxWithSizeApplied((double)mutable.getX() + 0.5, mutable.getY(), (double)mutable.getZ() + 0.5));
        }
        return true;
    }

    @Nullable
    private static MobEntity func_234973_a_(ServerWorld serverWorld, EntityType<?> entityType) {
        try {
            Object obj = entityType.create(serverWorld);
            if (!(obj instanceof MobEntity)) {
                throw new IllegalStateException("Trying to spawn a non-mob: " + Registry.ENTITY_TYPE.getKey(entityType));
            }
            return (MobEntity)obj;
        } catch (Exception exception) {
            LOGGER.warn("Failed to create mob", (Throwable)exception);
            return null;
        }
    }

    private static boolean func_234974_a_(ServerWorld serverWorld, MobEntity mobEntity, double d) {
        if (d > (double)(mobEntity.getType().getClassification().getInstantDespawnDistance() * mobEntity.getType().getClassification().getInstantDespawnDistance()) && mobEntity.canDespawn(d)) {
            return true;
        }
        return mobEntity.canSpawn(serverWorld, SpawnReason.NATURAL) && mobEntity.isNotColliding(serverWorld);
    }

    @Nullable
    private static MobSpawnInfo.Spawners func_234977_a_(ServerWorld serverWorld, StructureManager structureManager, ChunkGenerator chunkGenerator, EntityClassification entityClassification, Random random2, BlockPos blockPos) {
        Biome biome = serverWorld.getBiome(blockPos);
        if (entityClassification == EntityClassification.WATER_AMBIENT && biome.getCategory() == Biome.Category.RIVER && random2.nextFloat() < 0.98f) {
            return null;
        }
        List<MobSpawnInfo.Spawners> list = WorldEntitySpawner.func_241463_a_(serverWorld, structureManager, chunkGenerator, entityClassification, blockPos, biome);
        return list.isEmpty() ? null : WeightedRandom.getRandomItem(random2, list);
    }

    private static boolean func_234976_a_(ServerWorld serverWorld, StructureManager structureManager, ChunkGenerator chunkGenerator, EntityClassification entityClassification, MobSpawnInfo.Spawners spawners, BlockPos blockPos) {
        return WorldEntitySpawner.func_241463_a_(serverWorld, structureManager, chunkGenerator, entityClassification, blockPos, null).contains(spawners);
    }

    private static List<MobSpawnInfo.Spawners> func_241463_a_(ServerWorld serverWorld, StructureManager structureManager, ChunkGenerator chunkGenerator, EntityClassification entityClassification, BlockPos blockPos, @Nullable Biome biome) {
        return entityClassification == EntityClassification.MONSTER && serverWorld.getBlockState(blockPos.down()).getBlock() == Blocks.NETHER_BRICKS && structureManager.func_235010_a_(blockPos, false, Structure.field_236378_n_).isValid() ? Structure.field_236378_n_.getSpawnList() : chunkGenerator.func_230353_a_(biome != null ? biome : serverWorld.getBiome(blockPos), structureManager, entityClassification, blockPos);
    }

    private static BlockPos getRandomHeight(World world, Chunk chunk) {
        ChunkPos chunkPos = chunk.getPos();
        int n = chunkPos.getXStart() + world.rand.nextInt(16);
        int n2 = chunkPos.getZStart() + world.rand.nextInt(16);
        int n3 = chunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE, n, n2) + 1;
        int n4 = world.rand.nextInt(n3 + 1);
        return new BlockPos(n, n4, n2);
    }

    public static boolean func_234968_a_(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, FluidState fluidState, EntityType<?> entityType) {
        if (blockState.hasOpaqueCollisionShape(iBlockReader, blockPos)) {
            return true;
        }
        if (blockState.canProvidePower()) {
            return true;
        }
        if (!fluidState.isEmpty()) {
            return true;
        }
        if (blockState.isIn(BlockTags.PREVENT_MOB_SPAWNING_INSIDE)) {
            return true;
        }
        return !entityType.func_233597_a_(blockState);
    }

    public static boolean canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType placementType, IWorldReader iWorldReader, BlockPos blockPos, @Nullable EntityType<?> entityType) {
        if (placementType == EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS) {
            return false;
        }
        if (entityType != null && iWorldReader.getWorldBorder().contains(blockPos)) {
            BlockState blockState = iWorldReader.getBlockState(blockPos);
            FluidState fluidState = iWorldReader.getFluidState(blockPos);
            BlockPos blockPos2 = blockPos.up();
            BlockPos blockPos3 = blockPos.down();
            switch (1.$SwitchMap$net$minecraft$entity$EntitySpawnPlacementRegistry$PlacementType[placementType.ordinal()]) {
                case 1: {
                    return fluidState.isTagged(FluidTags.WATER) && iWorldReader.getFluidState(blockPos3).isTagged(FluidTags.WATER) && !iWorldReader.getBlockState(blockPos2).isNormalCube(iWorldReader, blockPos2);
                }
                case 2: {
                    return fluidState.isTagged(FluidTags.LAVA);
                }
            }
            BlockState blockState2 = iWorldReader.getBlockState(blockPos3);
            if (!blockState2.canEntitySpawn(iWorldReader, blockPos3, entityType)) {
                return true;
            }
            return WorldEntitySpawner.func_234968_a_(iWorldReader, blockPos, blockState, fluidState, entityType) && WorldEntitySpawner.func_234968_a_(iWorldReader, blockPos2, iWorldReader.getBlockState(blockPos2), iWorldReader.getFluidState(blockPos2), entityType);
        }
        return true;
    }

    public static void performWorldGenSpawning(IServerWorld iServerWorld, Biome biome, int n, int n2, Random random2) {
        MobSpawnInfo mobSpawnInfo = biome.getMobSpawnInfo();
        List<MobSpawnInfo.Spawners> list = mobSpawnInfo.getSpawners(EntityClassification.CREATURE);
        if (!list.isEmpty()) {
            int n3 = n << 4;
            int n4 = n2 << 4;
            while (random2.nextFloat() < mobSpawnInfo.getCreatureSpawnProbability()) {
                MobSpawnInfo.Spawners spawners = WeightedRandom.getRandomItem(random2, list);
                int n5 = spawners.minCount + random2.nextInt(1 + spawners.maxCount - spawners.minCount);
                ILivingEntityData iLivingEntityData = null;
                int n6 = n3 + random2.nextInt(16);
                int n7 = n4 + random2.nextInt(16);
                int n8 = n6;
                int n9 = n7;
                for (int i = 0; i < n5; ++i) {
                    boolean bl = false;
                    for (int j = 0; !bl && j < 4; ++j) {
                        BlockPos blockPos = WorldEntitySpawner.getTopSolidOrLiquidBlock(iServerWorld, spawners.type, n6, n7);
                        if (spawners.type.isSummonable() && WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.getPlacementType(spawners.type), iServerWorld, blockPos, spawners.type)) {
                            MobEntity mobEntity;
                            Object obj;
                            float f = spawners.type.getWidth();
                            double d = MathHelper.clamp((double)n6, (double)n3 + (double)f, (double)n3 + 16.0 - (double)f);
                            double d2 = MathHelper.clamp((double)n7, (double)n4 + (double)f, (double)n4 + 16.0 - (double)f);
                            if (!iServerWorld.hasNoCollisions(spawners.type.getBoundingBoxWithSizeApplied(d, blockPos.getY(), d2)) || !EntitySpawnPlacementRegistry.canSpawnEntity(spawners.type, iServerWorld, SpawnReason.CHUNK_GENERATION, new BlockPos(d, (double)blockPos.getY(), d2), iServerWorld.getRandom())) continue;
                            try {
                                obj = spawners.type.create(iServerWorld.getWorld());
                            } catch (Exception exception) {
                                LOGGER.warn("Failed to create mob", (Throwable)exception);
                                continue;
                            }
                            ((Entity)obj).setLocationAndAngles(d, blockPos.getY(), d2, random2.nextFloat() * 360.0f, 0.0f);
                            if (obj instanceof MobEntity && (mobEntity = (MobEntity)obj).canSpawn(iServerWorld, SpawnReason.CHUNK_GENERATION) && mobEntity.isNotColliding(iServerWorld)) {
                                iLivingEntityData = mobEntity.onInitialSpawn(iServerWorld, iServerWorld.getDifficultyForLocation(mobEntity.getPosition()), SpawnReason.CHUNK_GENERATION, iLivingEntityData, null);
                                iServerWorld.func_242417_l(mobEntity);
                                bl = true;
                            }
                        }
                        n6 += random2.nextInt(5) - random2.nextInt(5);
                        n7 += random2.nextInt(5) - random2.nextInt(5);
                        while (n6 < n3 || n6 >= n3 + 16 || n7 < n4 || n7 >= n4 + 16) {
                            n6 = n8 + random2.nextInt(5) - random2.nextInt(5);
                            n7 = n9 + random2.nextInt(5) - random2.nextInt(5);
                        }
                    }
                }
            }
        }
    }

    private static BlockPos getTopSolidOrLiquidBlock(IWorldReader iWorldReader, EntityType<?> entityType, int n, int n2) {
        Vector3i vector3i;
        int n3 = iWorldReader.getHeight(EntitySpawnPlacementRegistry.func_209342_b(entityType), n, n2);
        BlockPos.Mutable mutable = new BlockPos.Mutable(n, n3, n2);
        if (iWorldReader.getDimensionType().getHasCeiling()) {
            do {
                mutable.move(Direction.DOWN);
            } while (!iWorldReader.getBlockState(mutable).isAir());
            do {
                mutable.move(Direction.DOWN);
            } while (iWorldReader.getBlockState(mutable).isAir() && mutable.getY() > 0);
        }
        if (EntitySpawnPlacementRegistry.getPlacementType(entityType) == EntitySpawnPlacementRegistry.PlacementType.ON_GROUND && iWorldReader.getBlockState((BlockPos)(vector3i = mutable.down())).allowsMovement(iWorldReader, (BlockPos)vector3i, PathType.LAND)) {
            return vector3i;
        }
        return mutable.toImmutable();
    }

    private static void lambda$func_234979_a_$4(EntityDensityManager entityDensityManager, MobEntity mobEntity, IChunk iChunk) {
        entityDensityManager.func_234990_a_(mobEntity, iChunk);
    }

    private static boolean lambda$func_234979_a_$3(EntityDensityManager entityDensityManager, EntityType entityType, BlockPos blockPos, IChunk iChunk) {
        return entityDensityManager.func_234989_a_(entityType, blockPos, iChunk);
    }

    private static void lambda$func_234964_a_$2(BlockPos blockPos, Entity entity2, MobDensityTracker mobDensityTracker, Object2IntOpenHashMap object2IntOpenHashMap, EntityClassification entityClassification, Chunk chunk) {
        MobSpawnInfo.SpawnCosts spawnCosts = WorldEntitySpawner.func_234980_b_(blockPos, chunk).getMobSpawnInfo().getSpawnCost(entity2.getType());
        if (spawnCosts != null) {
            mobDensityTracker.func_234998_a_(entity2.getPosition(), spawnCosts.getEntitySpawnCost());
        }
        object2IntOpenHashMap.addTo(entityClassification, 1);
    }

    private static EntityClassification[] lambda$static$1(int n) {
        return new EntityClassification[n];
    }

    private static boolean lambda$static$0(EntityClassification entityClassification) {
        return entityClassification != EntityClassification.MISC;
    }

    public static class EntityDensityManager {
        private final int field_234981_a_;
        private final Object2IntOpenHashMap<EntityClassification> field_234982_b_;
        private final MobDensityTracker field_234983_c_;
        private final Object2IntMap<EntityClassification> field_234984_d_;
        @Nullable
        private BlockPos field_234985_e_;
        @Nullable
        private EntityType<?> field_234986_f_;
        private double field_234987_g_;

        private EntityDensityManager(int n, Object2IntOpenHashMap<EntityClassification> object2IntOpenHashMap, MobDensityTracker mobDensityTracker) {
            this.field_234981_a_ = n;
            this.field_234982_b_ = object2IntOpenHashMap;
            this.field_234983_c_ = mobDensityTracker;
            this.field_234984_d_ = Object2IntMaps.unmodifiable(object2IntOpenHashMap);
        }

        private boolean func_234989_a_(EntityType<?> entityType, BlockPos blockPos, IChunk iChunk) {
            double d;
            this.field_234985_e_ = blockPos;
            this.field_234986_f_ = entityType;
            MobSpawnInfo.SpawnCosts spawnCosts = WorldEntitySpawner.func_234980_b_(blockPos, iChunk).getMobSpawnInfo().getSpawnCost(entityType);
            if (spawnCosts == null) {
                this.field_234987_g_ = 0.0;
                return false;
            }
            this.field_234987_g_ = d = spawnCosts.getEntitySpawnCost();
            double d2 = this.field_234983_c_.func_234999_b_(blockPos, d);
            return d2 <= spawnCosts.getMaxSpawnCost();
        }

        private void func_234990_a_(MobEntity mobEntity, IChunk iChunk) {
            MobSpawnInfo.SpawnCosts spawnCosts;
            EntityType<?> entityType = mobEntity.getType();
            BlockPos blockPos = mobEntity.getPosition();
            double d = blockPos.equals(this.field_234985_e_) && entityType == this.field_234986_f_ ? this.field_234987_g_ : ((spawnCosts = WorldEntitySpawner.func_234980_b_(blockPos, iChunk).getMobSpawnInfo().getSpawnCost(entityType)) != null ? spawnCosts.getEntitySpawnCost() : 0.0);
            this.field_234983_c_.func_234998_a_(blockPos, d);
            this.field_234982_b_.addTo(entityType.getClassification(), 1);
        }

        public int func_234988_a_() {
            return this.field_234981_a_;
        }

        public Object2IntMap<EntityClassification> func_234995_b_() {
            return this.field_234984_d_;
        }

        private boolean func_234991_a_(EntityClassification entityClassification) {
            int n = entityClassification.getMaxNumberOfCreature() * this.field_234981_a_ / field_234960_b_;
            return this.field_234982_b_.getInt(entityClassification) < n;
        }
    }

    @FunctionalInterface
    public static interface IInitialDensityAdder {
        public void query(long var1, Consumer<Chunk> var3);
    }

    @FunctionalInterface
    public static interface IDensityCheck {
        public boolean test(EntityType<?> var1, BlockPos var2, IChunk var3);
    }

    @FunctionalInterface
    public static interface IOnSpawnDensityAdder {
        public void run(MobEntity var1, IChunk var2);
    }
}

