/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.spawner;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.StringUtils;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractSpawner {
    private static final Logger LOGGER = LogManager.getLogger();
    private int spawnDelay = 20;
    private final List<WeightedSpawnerEntity> potentialSpawns = Lists.newArrayList();
    private WeightedSpawnerEntity spawnData = new WeightedSpawnerEntity();
    private double mobRotation;
    private double prevMobRotation;
    private int minSpawnDelay = 200;
    private int maxSpawnDelay = 800;
    private int spawnCount = 4;
    @Nullable
    private Entity cachedEntity;
    private int maxNearbyEntities = 6;
    private int activatingRangeFromPlayer = 16;
    private int spawnRange = 4;

    @Nullable
    private ResourceLocation getEntityId() {
        String string = this.spawnData.getNbt().getString("id");
        try {
            return StringUtils.isNullOrEmpty(string) ? null : new ResourceLocation(string);
        } catch (ResourceLocationException resourceLocationException) {
            BlockPos blockPos = this.getSpawnerPosition();
            LOGGER.warn("Invalid entity id '{}' at spawner {}:[{},{},{}]", (Object)string, (Object)this.getWorld().getDimensionKey().getLocation(), (Object)blockPos.getX(), (Object)blockPos.getY(), (Object)blockPos.getZ());
            return null;
        }
    }

    public void setEntityType(EntityType<?> entityType) {
        this.spawnData.getNbt().putString("id", Registry.ENTITY_TYPE.getKey(entityType).toString());
    }

    private boolean isActivated() {
        BlockPos blockPos = this.getSpawnerPosition();
        return this.getWorld().isPlayerWithin((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, this.activatingRangeFromPlayer);
    }

    public void tick() {
        if (!this.isActivated()) {
            this.prevMobRotation = this.mobRotation;
        } else {
            World world = this.getWorld();
            BlockPos blockPos = this.getSpawnerPosition();
            if (!(world instanceof ServerWorld)) {
                double d = (double)blockPos.getX() + world.rand.nextDouble();
                double d2 = (double)blockPos.getY() + world.rand.nextDouble();
                double d3 = (double)blockPos.getZ() + world.rand.nextDouble();
                world.addParticle(ParticleTypes.SMOKE, d, d2, d3, 0.0, 0.0, 0.0);
                world.addParticle(ParticleTypes.FLAME, d, d2, d3, 0.0, 0.0, 0.0);
                if (this.spawnDelay > 0) {
                    --this.spawnDelay;
                }
                this.prevMobRotation = this.mobRotation;
                this.mobRotation = (this.mobRotation + (double)(1000.0f / ((float)this.spawnDelay + 200.0f))) % 360.0;
            } else {
                if (this.spawnDelay == -1) {
                    this.resetTimer();
                }
                if (this.spawnDelay > 0) {
                    --this.spawnDelay;
                    return;
                }
                boolean bl = false;
                for (int i = 0; i < this.spawnCount; ++i) {
                    double d;
                    CompoundNBT compoundNBT = this.spawnData.getNbt();
                    Optional<EntityType<?>> optional = EntityType.readEntityType(compoundNBT);
                    if (!optional.isPresent()) {
                        this.resetTimer();
                        return;
                    }
                    ListNBT listNBT = compoundNBT.getList("Pos", 6);
                    int n = listNBT.size();
                    double d4 = n >= 1 ? listNBT.getDouble(0) : (double)blockPos.getX() + (world.rand.nextDouble() - world.rand.nextDouble()) * (double)this.spawnRange + 0.5;
                    double d5 = n >= 2 ? listNBT.getDouble(1) : (double)(blockPos.getY() + world.rand.nextInt(3) - 1);
                    double d6 = d = n >= 3 ? listNBT.getDouble(2) : (double)blockPos.getZ() + (world.rand.nextDouble() - world.rand.nextDouble()) * (double)this.spawnRange + 0.5;
                    if (!world.hasNoCollisions(optional.get().getBoundingBoxWithSizeApplied(d4, d5, d))) continue;
                    ServerWorld serverWorld = (ServerWorld)world;
                    if (!EntitySpawnPlacementRegistry.canSpawnEntity(optional.get(), serverWorld, SpawnReason.SPAWNER, new BlockPos(d4, d5, d), world.getRandom())) continue;
                    Entity entity2 = EntityType.loadEntityAndExecute(compoundNBT, world, arg_0 -> AbstractSpawner.lambda$tick$0(d4, d5, d, arg_0));
                    if (entity2 == null) {
                        this.resetTimer();
                        return;
                    }
                    int n2 = world.getEntitiesWithinAABB(entity2.getClass(), new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 1, blockPos.getZ() + 1).grow(this.spawnRange)).size();
                    if (n2 >= this.maxNearbyEntities) {
                        this.resetTimer();
                        return;
                    }
                    entity2.setLocationAndAngles(entity2.getPosX(), entity2.getPosY(), entity2.getPosZ(), world.rand.nextFloat() * 360.0f, 0.0f);
                    if (entity2 instanceof MobEntity) {
                        MobEntity mobEntity = (MobEntity)entity2;
                        if (!mobEntity.canSpawn(world, SpawnReason.SPAWNER) || !mobEntity.isNotColliding(world)) continue;
                        if (this.spawnData.getNbt().size() == 1 && this.spawnData.getNbt().contains("id", 1)) {
                            ((MobEntity)entity2).onInitialSpawn(serverWorld, world.getDifficultyForLocation(entity2.getPosition()), SpawnReason.SPAWNER, null, null);
                        }
                    }
                    if (!serverWorld.func_242106_g(entity2)) {
                        this.resetTimer();
                        return;
                    }
                    world.playEvent(2004, blockPos, 0);
                    if (entity2 instanceof MobEntity) {
                        ((MobEntity)entity2).spawnExplosionParticle();
                    }
                    bl = true;
                }
                if (bl) {
                    this.resetTimer();
                }
            }
        }
    }

    private void resetTimer() {
        if (this.maxSpawnDelay <= this.minSpawnDelay) {
            this.spawnDelay = this.minSpawnDelay;
        } else {
            int n = this.maxSpawnDelay - this.minSpawnDelay;
            this.spawnDelay = this.minSpawnDelay + this.getWorld().rand.nextInt(n);
        }
        if (!this.potentialSpawns.isEmpty()) {
            this.setNextSpawnData(WeightedRandom.getRandomItem(this.getWorld().rand, this.potentialSpawns));
        }
        this.broadcastEvent(1);
    }

    public void read(CompoundNBT compoundNBT) {
        this.spawnDelay = compoundNBT.getShort("Delay");
        this.potentialSpawns.clear();
        if (compoundNBT.contains("SpawnPotentials", 0)) {
            ListNBT listNBT = compoundNBT.getList("SpawnPotentials", 10);
            for (int i = 0; i < listNBT.size(); ++i) {
                this.potentialSpawns.add(new WeightedSpawnerEntity(listNBT.getCompound(i)));
            }
        }
        if (compoundNBT.contains("SpawnData", 1)) {
            this.setNextSpawnData(new WeightedSpawnerEntity(1, compoundNBT.getCompound("SpawnData")));
        } else if (!this.potentialSpawns.isEmpty()) {
            this.setNextSpawnData(WeightedRandom.getRandomItem(this.getWorld().rand, this.potentialSpawns));
        }
        if (compoundNBT.contains("MinSpawnDelay", 0)) {
            this.minSpawnDelay = compoundNBT.getShort("MinSpawnDelay");
            this.maxSpawnDelay = compoundNBT.getShort("MaxSpawnDelay");
            this.spawnCount = compoundNBT.getShort("SpawnCount");
        }
        if (compoundNBT.contains("MaxNearbyEntities", 0)) {
            this.maxNearbyEntities = compoundNBT.getShort("MaxNearbyEntities");
            this.activatingRangeFromPlayer = compoundNBT.getShort("RequiredPlayerRange");
        }
        if (compoundNBT.contains("SpawnRange", 0)) {
            this.spawnRange = compoundNBT.getShort("SpawnRange");
        }
        if (this.getWorld() != null) {
            this.cachedEntity = null;
        }
    }

    public CompoundNBT write(CompoundNBT compoundNBT) {
        ResourceLocation resourceLocation = this.getEntityId();
        if (resourceLocation == null) {
            return compoundNBT;
        }
        compoundNBT.putShort("Delay", (short)this.spawnDelay);
        compoundNBT.putShort("MinSpawnDelay", (short)this.minSpawnDelay);
        compoundNBT.putShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
        compoundNBT.putShort("SpawnCount", (short)this.spawnCount);
        compoundNBT.putShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
        compoundNBT.putShort("RequiredPlayerRange", (short)this.activatingRangeFromPlayer);
        compoundNBT.putShort("SpawnRange", (short)this.spawnRange);
        compoundNBT.put("SpawnData", this.spawnData.getNbt().copy());
        ListNBT listNBT = new ListNBT();
        if (this.potentialSpawns.isEmpty()) {
            listNBT.add(this.spawnData.toCompoundTag());
        } else {
            for (WeightedSpawnerEntity weightedSpawnerEntity : this.potentialSpawns) {
                listNBT.add(weightedSpawnerEntity.toCompoundTag());
            }
        }
        compoundNBT.put("SpawnPotentials", listNBT);
        return compoundNBT;
    }

    @Nullable
    public Entity getCachedEntity() {
        if (this.cachedEntity == null) {
            this.cachedEntity = EntityType.loadEntityAndExecute(this.spawnData.getNbt(), this.getWorld(), Function.identity());
            if (this.spawnData.getNbt().size() != 1 || !this.spawnData.getNbt().contains("id", 1) || this.cachedEntity instanceof MobEntity) {
                // empty if block
            }
        }
        return this.cachedEntity;
    }

    public boolean setDelayToMin(int n) {
        if (n == 1 && this.getWorld().isRemote) {
            this.spawnDelay = this.minSpawnDelay;
            return false;
        }
        return true;
    }

    public void setNextSpawnData(WeightedSpawnerEntity weightedSpawnerEntity) {
        this.spawnData = weightedSpawnerEntity;
    }

    public abstract void broadcastEvent(int var1);

    public abstract World getWorld();

    public abstract BlockPos getSpawnerPosition();

    public double getMobRotation() {
        return this.mobRotation;
    }

    public double getPrevMobRotation() {
        return this.prevMobRotation;
    }

    private static Entity lambda$tick$0(double d, double d2, double d3, Entity entity2) {
        entity2.setLocationAndAngles(d, d2, d3, entity2.rotationYaw, entity2.rotationPitch);
        return entity2;
    }
}

