/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive.horse;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class TraderLlamaEntity
extends LlamaEntity {
    private int despawnDelay = 47999;

    public TraderLlamaEntity(EntityType<? extends TraderLlamaEntity> entityType, World world) {
        super((EntityType<? extends LlamaEntity>)entityType, world);
    }

    @Override
    public boolean isTraderLlama() {
        return false;
    }

    @Override
    protected LlamaEntity createChild() {
        return EntityType.TRADER_LLAMA.create(this.world);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("DespawnDelay", this.despawnDelay);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("DespawnDelay", 0)) {
            this.despawnDelay = compoundNBT.getInt("DespawnDelay");
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0));
        this.targetSelector.addGoal(1, new FollowTraderGoal(this, this));
    }

    @Override
    protected void mountTo(PlayerEntity playerEntity) {
        Entity entity2 = this.getLeashHolder();
        if (!(entity2 instanceof WanderingTraderEntity)) {
            super.mountTo(playerEntity);
        }
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) {
            this.tryDespawn();
        }
    }

    private void tryDespawn() {
        if (this.canDespawn()) {
            int n = this.despawnDelay = this.isLeashedToTrader() ? ((WanderingTraderEntity)this.getLeashHolder()).getDespawnDelay() - 1 : this.despawnDelay - 1;
            if (this.despawnDelay <= 0) {
                this.clearLeashed(true, true);
                this.remove();
            }
        }
    }

    private boolean canDespawn() {
        return !this.isTame() && !this.isLeashedToStranger() && !this.isOnePlayerRiding();
    }

    private boolean isLeashedToTrader() {
        return this.getLeashHolder() instanceof WanderingTraderEntity;
    }

    private boolean isLeashedToStranger() {
        return this.getLeashed() && !this.isLeashedToTrader();
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        if (spawnReason == SpawnReason.EVENT) {
            this.setGrowingAge(0);
        }
        if (iLivingEntityData == null) {
            iLivingEntityData = new AgeableEntity.AgeableData(false);
        }
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    public class FollowTraderGoal
    extends TargetGoal {
        private final LlamaEntity field_220800_b;
        private LivingEntity field_220801_c;
        private int field_220802_d;
        final TraderLlamaEntity this$0;

        public FollowTraderGoal(TraderLlamaEntity traderLlamaEntity, LlamaEntity llamaEntity) {
            this.this$0 = traderLlamaEntity;
            super(llamaEntity, false);
            this.field_220800_b = llamaEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
        }

        @Override
        public boolean shouldExecute() {
            if (!this.field_220800_b.getLeashed()) {
                return true;
            }
            Entity entity2 = this.field_220800_b.getLeashHolder();
            if (!(entity2 instanceof WanderingTraderEntity)) {
                return true;
            }
            WanderingTraderEntity wanderingTraderEntity = (WanderingTraderEntity)entity2;
            this.field_220801_c = wanderingTraderEntity.getRevengeTarget();
            int n = wanderingTraderEntity.getRevengeTimer();
            return n != this.field_220802_d && this.isSuitableTarget(this.field_220801_c, EntityPredicate.DEFAULT);
        }

        @Override
        public void startExecuting() {
            this.goalOwner.setAttackTarget(this.field_220801_c);
            Entity entity2 = this.field_220800_b.getLeashHolder();
            if (entity2 instanceof WanderingTraderEntity) {
                this.field_220802_d = ((WanderingTraderEntity)entity2).getRevengeTimer();
            }
            super.startExecuting();
        }
    }
}

