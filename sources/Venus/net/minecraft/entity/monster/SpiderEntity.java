/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.ClimberPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class SpiderEntity
extends MonsterEntity {
    private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(SpiderEntity.class, DataSerializers.BYTE);

    public SpiderEntity(EntityType<? extends SpiderEntity> entityType, World world) {
        super((EntityType<? extends MonsterEntity>)entityType, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4f));
        this.goalSelector.addGoal(4, new AttackGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.addGoal(2, new TargetGoal<PlayerEntity>(this, PlayerEntity.class));
        this.targetSelector.addGoal(3, new TargetGoal<IronGolemEntity>(this, IronGolemEntity.class));
    }

    @Override
    public double getMountedYOffset() {
        return this.getHeight() * 0.5f;
    }

    @Override
    protected PathNavigator createNavigator(World world) {
        return new ClimberPathNavigator(this, world);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(CLIMBING, (byte)0);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote) {
            this.setBesideClimbableBlock(this.collidedHorizontally);
        }
    }

    public static AttributeModifierMap.MutableAttribute func_234305_eI_() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 16.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3f);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SPIDER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_SPIDER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SPIDER_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15f, 1.0f);
    }

    @Override
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }

    @Override
    public void setMotionMultiplier(BlockState blockState, Vector3d vector3d) {
        if (!blockState.isIn(Blocks.COBWEB)) {
            super.setMotionMultiplier(blockState, vector3d);
        }
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }

    @Override
    public boolean isPotionApplicable(EffectInstance effectInstance) {
        return effectInstance.getPotion() == Effects.POISON ? false : super.isPotionApplicable(effectInstance);
    }

    public boolean isBesideClimbableBlock() {
        return (this.dataManager.get(CLIMBING) & 1) != 0;
    }

    public void setBesideClimbableBlock(boolean bl) {
        byte by = this.dataManager.get(CLIMBING);
        by = bl ? (byte)(by | 1) : (byte)(by & 0xFFFFFFFE);
        this.dataManager.set(CLIMBING, by);
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        Object object;
        iLivingEntityData = super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
        if (iServerWorld.getRandom().nextInt(100) == 0) {
            object = EntityType.SKELETON.create(this.world);
            ((Entity)object).setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, 0.0f);
            ((AbstractSkeletonEntity)object).onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, null, null);
            ((Entity)object).startRiding(this);
        }
        if (iLivingEntityData == null) {
            iLivingEntityData = new GroupData();
            if (iServerWorld.getDifficulty() == Difficulty.HARD && iServerWorld.getRandom().nextFloat() < 0.1f * difficultyInstance.getClampedAdditionalDifficulty()) {
                ((GroupData)iLivingEntityData).setRandomEffect(iServerWorld.getRandom());
            }
        }
        if (iLivingEntityData instanceof GroupData && (object = ((GroupData)iLivingEntityData).effect) != null) {
            this.addPotionEffect(new EffectInstance((Effect)object, Integer.MAX_VALUE));
        }
        return iLivingEntityData;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return 0.65f;
    }

    static class AttackGoal
    extends MeleeAttackGoal {
        public AttackGoal(SpiderEntity spiderEntity) {
            super(spiderEntity, 1.0, true);
        }

        @Override
        public boolean shouldExecute() {
            return super.shouldExecute() && !this.attacker.isBeingRidden();
        }

        @Override
        public boolean shouldContinueExecuting() {
            float f = this.attacker.getBrightness();
            if (f >= 0.5f && this.attacker.getRNG().nextInt(100) == 0) {
                this.attacker.setAttackTarget(null);
                return true;
            }
            return super.shouldContinueExecuting();
        }

        @Override
        protected double getAttackReachSqr(LivingEntity livingEntity) {
            return 4.0f + livingEntity.getWidth();
        }
    }

    static class TargetGoal<T extends LivingEntity>
    extends NearestAttackableTargetGoal<T> {
        public TargetGoal(SpiderEntity spiderEntity, Class<T> clazz) {
            super((MobEntity)spiderEntity, clazz, true);
        }

        @Override
        public boolean shouldExecute() {
            float f = this.goalOwner.getBrightness();
            return f >= 0.5f ? false : super.shouldExecute();
        }
    }

    public static class GroupData
    implements ILivingEntityData {
        public Effect effect;

        public void setRandomEffect(Random random2) {
            int n = random2.nextInt(5);
            if (n <= 1) {
                this.effect = Effects.SPEED;
            } else if (n <= 2) {
                this.effect = Effects.STRENGTH;
            } else if (n <= 3) {
                this.effect = Effects.REGENERATION;
            } else if (n <= 4) {
                this.effect = Effects.INVISIBILITY;
            }
        }
    }
}

