/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.raid.Raid;

public abstract class PatrollerEntity
extends MonsterEntity {
    private BlockPos patrolTarget;
    private boolean patrolLeader;
    private boolean patrolling;

    protected PatrollerEntity(EntityType<? extends PatrollerEntity> entityType, World world) {
        super((EntityType<? extends MonsterEntity>)entityType, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new PatrolGoal<PatrollerEntity>(this, 0.7, 0.595));
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        if (this.patrolTarget != null) {
            compoundNBT.put("PatrolTarget", NBTUtil.writeBlockPos(this.patrolTarget));
        }
        compoundNBT.putBoolean("PatrolLeader", this.patrolLeader);
        compoundNBT.putBoolean("Patrolling", this.patrolling);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("PatrolTarget")) {
            this.patrolTarget = NBTUtil.readBlockPos(compoundNBT.getCompound("PatrolTarget"));
        }
        this.patrolLeader = compoundNBT.getBoolean("PatrolLeader");
        this.patrolling = compoundNBT.getBoolean("Patrolling");
    }

    @Override
    public double getYOffset() {
        return -0.45;
    }

    public boolean canBeLeader() {
        return false;
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        if (spawnReason != SpawnReason.PATROL && spawnReason != SpawnReason.EVENT && spawnReason != SpawnReason.STRUCTURE && this.rand.nextFloat() < 0.06f && this.canBeLeader()) {
            this.patrolLeader = true;
        }
        if (this.isLeader()) {
            this.setItemStackToSlot(EquipmentSlotType.HEAD, Raid.createIllagerBanner());
            this.setDropChance(EquipmentSlotType.HEAD, 2.0f);
        }
        if (spawnReason == SpawnReason.PATROL) {
            this.patrolling = true;
        }
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    public static boolean func_223330_b(EntityType<? extends PatrollerEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        return iWorld.getLightFor(LightType.BLOCK, blockPos) > 8 ? false : PatrollerEntity.canMonsterSpawn(entityType, iWorld, spawnReason, blockPos, random2);
    }

    @Override
    public boolean canDespawn(double d) {
        return !this.patrolling || d > 16384.0;
    }

    public void setPatrolTarget(BlockPos blockPos) {
        this.patrolTarget = blockPos;
        this.patrolling = true;
    }

    public BlockPos getPatrolTarget() {
        return this.patrolTarget;
    }

    public boolean hasPatrolTarget() {
        return this.patrolTarget != null;
    }

    public void setLeader(boolean bl) {
        this.patrolLeader = bl;
        this.patrolling = true;
    }

    public boolean isLeader() {
        return this.patrolLeader;
    }

    public boolean notInRaid() {
        return false;
    }

    public void resetPatrolTarget() {
        this.patrolTarget = this.getPosition().add(-500 + this.rand.nextInt(1000), 0, -500 + this.rand.nextInt(1000));
        this.patrolling = true;
    }

    protected boolean isPatrolling() {
        return this.patrolling;
    }

    protected void setPatrolling(boolean bl) {
        this.patrolling = bl;
    }

    public static class PatrolGoal<T extends PatrollerEntity>
    extends Goal {
        private final T owner;
        private final double field_220840_b;
        private final double field_220841_c;
        private long field_226542_d_;

        public PatrolGoal(T t, double d, double d2) {
            this.owner = t;
            this.field_220840_b = d;
            this.field_220841_c = d2;
            this.field_226542_d_ = -1L;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            boolean bl = ((PatrollerEntity)this.owner).world.getGameTime() < this.field_226542_d_;
            return ((PatrollerEntity)this.owner).isPatrolling() && ((MobEntity)this.owner).getAttackTarget() == null && !((Entity)this.owner).isBeingRidden() && ((PatrollerEntity)this.owner).hasPatrolTarget() && !bl;
        }

        @Override
        public void startExecuting() {
        }

        @Override
        public void resetTask() {
        }

        @Override
        public void tick() {
            boolean bl = ((PatrollerEntity)this.owner).isLeader();
            PathNavigator pathNavigator = ((MobEntity)this.owner).getNavigator();
            if (pathNavigator.noPath()) {
                List<PatrollerEntity> list = this.func_226544_g_();
                if (((PatrollerEntity)this.owner).isPatrolling() && list.isEmpty()) {
                    ((PatrollerEntity)this.owner).setPatrolling(true);
                } else if (bl && ((PatrollerEntity)this.owner).getPatrolTarget().withinDistance(((Entity)this.owner).getPositionVec(), 10.0)) {
                    ((PatrollerEntity)this.owner).resetPatrolTarget();
                } else {
                    Vector3d vector3d = Vector3d.copyCenteredHorizontally(((PatrollerEntity)this.owner).getPatrolTarget());
                    Vector3d vector3d2 = ((Entity)this.owner).getPositionVec();
                    Vector3d vector3d3 = vector3d2.subtract(vector3d);
                    vector3d = vector3d3.rotateYaw(90.0f).scale(0.4).add(vector3d);
                    Vector3d vector3d4 = vector3d.subtract(vector3d2).normalize().scale(10.0).add(vector3d2);
                    BlockPos blockPos = new BlockPos(vector3d4);
                    if (!pathNavigator.tryMoveToXYZ((blockPos = ((PatrollerEntity)this.owner).world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, blockPos)).getX(), blockPos.getY(), blockPos.getZ(), bl ? this.field_220841_c : this.field_220840_b)) {
                        this.func_226545_h_();
                        this.field_226542_d_ = ((PatrollerEntity)this.owner).world.getGameTime() + 200L;
                    } else if (bl) {
                        for (PatrollerEntity patrollerEntity : list) {
                            patrollerEntity.setPatrolTarget(blockPos);
                        }
                    }
                }
            }
        }

        private List<PatrollerEntity> func_226544_g_() {
            return ((PatrollerEntity)this.owner).world.getEntitiesWithinAABB(PatrollerEntity.class, ((Entity)this.owner).getBoundingBox().grow(16.0), this::lambda$func_226544_g_$0);
        }

        private boolean func_226545_h_() {
            Random random2 = ((LivingEntity)this.owner).getRNG();
            BlockPos blockPos = ((PatrollerEntity)this.owner).world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ((Entity)this.owner).getPosition().add(-8 + random2.nextInt(16), 0, -8 + random2.nextInt(16)));
            return ((MobEntity)this.owner).getNavigator().tryMoveToXYZ(blockPos.getX(), blockPos.getY(), blockPos.getZ(), this.field_220840_b);
        }

        private boolean lambda$func_226544_g_$0(PatrollerEntity patrollerEntity) {
            return patrollerEntity.notInRaid() && !patrollerEntity.isEntityEqual((Entity)this.owner);
        }
    }
}

