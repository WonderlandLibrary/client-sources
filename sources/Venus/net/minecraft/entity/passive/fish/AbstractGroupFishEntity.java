/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive.fish;

import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FollowSchoolLeaderGoal;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public abstract class AbstractGroupFishEntity
extends AbstractFishEntity {
    private AbstractGroupFishEntity groupLeader;
    private int groupSize = 1;

    public AbstractGroupFishEntity(EntityType<? extends AbstractGroupFishEntity> entityType, World world) {
        super((EntityType<? extends AbstractFishEntity>)entityType, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new FollowSchoolLeaderGoal(this));
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return this.getMaxGroupSize();
    }

    public int getMaxGroupSize() {
        return super.getMaxSpawnedInChunk();
    }

    @Override
    protected boolean func_212800_dy() {
        return !this.hasGroupLeader();
    }

    public boolean hasGroupLeader() {
        return this.groupLeader != null && this.groupLeader.isAlive();
    }

    public AbstractGroupFishEntity func_212803_a(AbstractGroupFishEntity abstractGroupFishEntity) {
        this.groupLeader = abstractGroupFishEntity;
        abstractGroupFishEntity.increaseGroupSize();
        return abstractGroupFishEntity;
    }

    public void leaveGroup() {
        this.groupLeader.decreaseGroupSize();
        this.groupLeader = null;
    }

    private void increaseGroupSize() {
        ++this.groupSize;
    }

    private void decreaseGroupSize() {
        --this.groupSize;
    }

    public boolean canGroupGrow() {
        return this.isGroupLeader() && this.groupSize < this.getMaxGroupSize();
    }

    @Override
    public void tick() {
        List<?> list;
        super.tick();
        if (this.isGroupLeader() && this.world.rand.nextInt(200) == 1 && (list = this.world.getEntitiesWithinAABB(this.getClass(), this.getBoundingBox().grow(8.0, 8.0, 8.0))).size() <= 1) {
            this.groupSize = 1;
        }
    }

    public boolean isGroupLeader() {
        return this.groupSize > 1;
    }

    public boolean inRangeOfGroupLeader() {
        return this.getDistanceSq(this.groupLeader) <= 121.0;
    }

    public void moveToGroupLeader() {
        if (this.hasGroupLeader()) {
            this.getNavigator().tryMoveToEntityLiving(this.groupLeader, 1.0);
        }
    }

    public void func_212810_a(Stream<AbstractGroupFishEntity> stream) {
        stream.limit(this.getMaxGroupSize() - this.groupSize).filter(this::lambda$func_212810_a$0).forEach(this::lambda$func_212810_a$1);
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
        if (iLivingEntityData == null) {
            iLivingEntityData = new GroupData(this);
        } else {
            this.func_212803_a(((GroupData)iLivingEntityData).groupLeader);
        }
        return iLivingEntityData;
    }

    private void lambda$func_212810_a$1(AbstractGroupFishEntity abstractGroupFishEntity) {
        abstractGroupFishEntity.func_212803_a(this);
    }

    private boolean lambda$func_212810_a$0(AbstractGroupFishEntity abstractGroupFishEntity) {
        return abstractGroupFishEntity != this;
    }

    public static class GroupData
    implements ILivingEntityData {
        public final AbstractGroupFishEntity groupLeader;

        public GroupData(AbstractGroupFishEntity abstractGroupFishEntity) {
            this.groupLeader = abstractGroupFishEntity;
        }
    }
}

