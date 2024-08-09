/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.ResetAngerGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;

public class PolarBearEntity
extends AnimalEntity
implements IAngerable {
    private static final DataParameter<Boolean> IS_STANDING = EntityDataManager.createKey(PolarBearEntity.class, DataSerializers.BOOLEAN);
    private float clientSideStandAnimation0;
    private float clientSideStandAnimation;
    private int warningSoundTicks;
    private static final RangedInteger field_234217_by_ = TickRangeConverter.convertRange(20, 39);
    private int field_234218_bz_;
    private UUID field_234216_bA_;

    public PolarBearEntity(EntityType<? extends PolarBearEntity> entityType, World world) {
        super((EntityType<? extends AnimalEntity>)entityType, world);
    }

    @Override
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return EntityType.POLAR_BEAR.create(serverWorld);
    }

    @Override
    public boolean isBreedingItem(ItemStack itemStack) {
        return true;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new AttackPlayerGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, 10, true, false, this::func_233680_b_));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<FoxEntity>(this, FoxEntity.class, 10, true, true, null));
        this.targetSelector.addGoal(5, new ResetAngerGoal<PolarBearEntity>(this, false));
    }

    public static AttributeModifierMap.MutableAttribute func_234219_eI_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 30.0).createMutableAttribute(Attributes.FOLLOW_RANGE, 20.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25).createMutableAttribute(Attributes.ATTACK_DAMAGE, 6.0);
    }

    public static boolean func_223320_c(EntityType<PolarBearEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        Optional<RegistryKey<Biome>> optional = iWorld.func_242406_i(blockPos);
        if (!Objects.equals(optional, Optional.of(Biomes.FROZEN_OCEAN)) && !Objects.equals(optional, Optional.of(Biomes.DEEP_FROZEN_OCEAN))) {
            return PolarBearEntity.canAnimalSpawn(entityType, iWorld, spawnReason, blockPos, random2);
        }
        return iWorld.getLightSubtracted(blockPos, 0) > 8 && iWorld.getBlockState(blockPos.down()).isIn(Blocks.ICE);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.readAngerNBT((ServerWorld)this.world, compoundNBT);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        this.writeAngerNBT(compoundNBT);
    }

    @Override
    public void func_230258_H__() {
        this.setAngerTime(field_234217_by_.getRandomWithinRange(this.rand));
    }

    @Override
    public void setAngerTime(int n) {
        this.field_234218_bz_ = n;
    }

    @Override
    public int getAngerTime() {
        return this.field_234218_bz_;
    }

    @Override
    public void setAngerTarget(@Nullable UUID uUID) {
        this.field_234216_bA_ = uUID;
    }

    @Override
    public UUID getAngerTarget() {
        return this.field_234216_bA_;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isChild() ? SoundEvents.ENTITY_POLAR_BEAR_AMBIENT_BABY : SoundEvents.ENTITY_POLAR_BEAR_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_POLAR_BEAR_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_POLAR_BEAR_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.ENTITY_POLAR_BEAR_STEP, 0.15f, 1.0f);
    }

    protected void playWarningSound() {
        if (this.warningSoundTicks <= 0) {
            this.playSound(SoundEvents.ENTITY_POLAR_BEAR_WARNING, 1.0f, this.getSoundPitch());
            this.warningSoundTicks = 40;
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(IS_STANDING, false);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.isRemote) {
            if (this.clientSideStandAnimation != this.clientSideStandAnimation0) {
                this.recalculateSize();
            }
            this.clientSideStandAnimation0 = this.clientSideStandAnimation;
            this.clientSideStandAnimation = this.isStanding() ? MathHelper.clamp(this.clientSideStandAnimation + 1.0f, 0.0f, 6.0f) : MathHelper.clamp(this.clientSideStandAnimation - 1.0f, 0.0f, 6.0f);
        }
        if (this.warningSoundTicks > 0) {
            --this.warningSoundTicks;
        }
        if (!this.world.isRemote) {
            this.func_241359_a_((ServerWorld)this.world, false);
        }
    }

    @Override
    public EntitySize getSize(Pose pose) {
        if (this.clientSideStandAnimation > 0.0f) {
            float f = this.clientSideStandAnimation / 6.0f;
            float f2 = 1.0f + f;
            return super.getSize(pose).scale(1.0f, f2);
        }
        return super.getSize(pose);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity2) {
        boolean bl = entity2.attackEntityFrom(DamageSource.causeMobDamage(this), (int)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
        if (bl) {
            this.applyEnchantments(this, entity2);
        }
        return bl;
    }

    public boolean isStanding() {
        return this.dataManager.get(IS_STANDING);
    }

    public void setStanding(boolean bl) {
        this.dataManager.set(IS_STANDING, bl);
    }

    public float getStandingAnimationScale(float f) {
        return MathHelper.lerp(f, this.clientSideStandAnimation0, this.clientSideStandAnimation) / 6.0f;
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.98f;
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        if (iLivingEntityData == null) {
            iLivingEntityData = new AgeableEntity.AgeableData(1.0f);
        }
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    class MeleeAttackGoal
    extends net.minecraft.entity.ai.goal.MeleeAttackGoal {
        final PolarBearEntity this$0;

        public MeleeAttackGoal(PolarBearEntity polarBearEntity) {
            this.this$0 = polarBearEntity;
            super(polarBearEntity, 1.25, true);
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity livingEntity, double d) {
            double d2 = this.getAttackReachSqr(livingEntity);
            if (d <= d2 && this.func_234040_h_()) {
                this.func_234039_g_();
                this.attacker.attackEntityAsMob(livingEntity);
                this.this$0.setStanding(true);
            } else if (d <= d2 * 2.0) {
                if (this.func_234040_h_()) {
                    this.this$0.setStanding(true);
                    this.func_234039_g_();
                }
                if (this.func_234041_j_() <= 10) {
                    this.this$0.setStanding(false);
                    this.this$0.playWarningSound();
                }
            } else {
                this.func_234039_g_();
                this.this$0.setStanding(true);
            }
        }

        @Override
        public void resetTask() {
            this.this$0.setStanding(true);
            super.resetTask();
        }

        @Override
        protected double getAttackReachSqr(LivingEntity livingEntity) {
            return 4.0f + livingEntity.getWidth();
        }
    }

    class PanicGoal
    extends net.minecraft.entity.ai.goal.PanicGoal {
        final PolarBearEntity this$0;

        public PanicGoal(PolarBearEntity polarBearEntity) {
            this.this$0 = polarBearEntity;
            super(polarBearEntity, 2.0);
        }

        @Override
        public boolean shouldExecute() {
            return !this.this$0.isChild() && !this.this$0.isBurning() ? false : super.shouldExecute();
        }
    }

    class HurtByTargetGoal
    extends net.minecraft.entity.ai.goal.HurtByTargetGoal {
        final PolarBearEntity this$0;

        public HurtByTargetGoal(PolarBearEntity polarBearEntity) {
            this.this$0 = polarBearEntity;
            super(polarBearEntity, new Class[0]);
        }

        @Override
        public void startExecuting() {
            super.startExecuting();
            if (this.this$0.isChild()) {
                this.alertOthers();
                this.resetTask();
            }
        }

        @Override
        protected void setAttackTarget(MobEntity mobEntity, LivingEntity livingEntity) {
            if (mobEntity instanceof PolarBearEntity && !mobEntity.isChild()) {
                super.setAttackTarget(mobEntity, livingEntity);
            }
        }
    }

    class AttackPlayerGoal
    extends NearestAttackableTargetGoal<PlayerEntity> {
        final PolarBearEntity this$0;

        public AttackPlayerGoal(PolarBearEntity polarBearEntity) {
            this.this$0 = polarBearEntity;
            super(polarBearEntity, PlayerEntity.class, 20, true, true, null);
        }

        @Override
        public boolean shouldExecute() {
            if (this.this$0.isChild()) {
                return true;
            }
            if (super.shouldExecute()) {
                for (PolarBearEntity polarBearEntity : this.this$0.world.getEntitiesWithinAABB(PolarBearEntity.class, this.this$0.getBoundingBox().grow(8.0, 4.0, 8.0))) {
                    if (!polarBearEntity.isChild()) continue;
                    return false;
                }
            }
            return true;
        }

        @Override
        protected double getTargetDistance() {
            return super.getTargetDistance() * 0.5;
        }
    }
}

