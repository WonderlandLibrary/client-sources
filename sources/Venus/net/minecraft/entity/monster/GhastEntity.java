/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.util.EnumSet;
import java.util.Random;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class GhastEntity
extends FlyingEntity
implements IMob {
    private static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(GhastEntity.class, DataSerializers.BOOLEAN);
    private int explosionStrength = 1;

    public GhastEntity(EntityType<? extends GhastEntity> entityType, World world) {
        super((EntityType<? extends FlyingEntity>)entityType, world);
        this.experienceValue = 5;
        this.moveController = new MoveHelperController(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(5, new RandomFlyGoal(this));
        this.goalSelector.addGoal(7, new LookAroundGoal(this));
        this.goalSelector.addGoal(7, new FireballAttackGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, 10, true, false, this::lambda$registerGoals$0));
    }

    public boolean isAttacking() {
        return this.dataManager.get(ATTACKING);
    }

    public void setAttacking(boolean bl) {
        this.dataManager.set(ATTACKING, bl);
    }

    public int getFireballStrength() {
        return this.explosionStrength;
    }

    @Override
    protected boolean isDespawnPeaceful() {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return true;
        }
        if (damageSource.getImmediateSource() instanceof FireballEntity && damageSource.getTrueSource() instanceof PlayerEntity) {
            super.attackEntityFrom(damageSource, 1000.0f);
            return false;
        }
        return super.attackEntityFrom(damageSource, f);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(ATTACKING, false);
    }

    public static AttributeModifierMap.MutableAttribute func_234290_eH_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10.0).createMutableAttribute(Attributes.FOLLOW_RANGE, 100.0);
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_GHAST_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_GHAST_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GHAST_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 5.0f;
    }

    public static boolean func_223368_b(EntityType<GhastEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        return iWorld.getDifficulty() != Difficulty.PEACEFUL && random2.nextInt(20) == 0 && GhastEntity.canSpawnOn(entityType, iWorld, spawnReason, blockPos, random2);
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 0;
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("ExplosionPower", this.explosionStrength);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("ExplosionPower", 0)) {
            this.explosionStrength = compoundNBT.getInt("ExplosionPower");
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return 2.6f;
    }

    private boolean lambda$registerGoals$0(LivingEntity livingEntity) {
        return Math.abs(livingEntity.getPosY() - this.getPosY()) <= 4.0;
    }

    static class MoveHelperController
    extends MovementController {
        private final GhastEntity parentEntity;
        private int courseChangeCooldown;

        public MoveHelperController(GhastEntity ghastEntity) {
            super(ghastEntity);
            this.parentEntity = ghastEntity;
        }

        @Override
        public void tick() {
            if (this.action == MovementController.Action.MOVE_TO && this.courseChangeCooldown-- <= 0) {
                this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
                Vector3d vector3d = new Vector3d(this.posX - this.parentEntity.getPosX(), this.posY - this.parentEntity.getPosY(), this.posZ - this.parentEntity.getPosZ());
                double d = vector3d.length();
                if (this.func_220673_a(vector3d = vector3d.normalize(), MathHelper.ceil(d))) {
                    this.parentEntity.setMotion(this.parentEntity.getMotion().add(vector3d.scale(0.1)));
                } else {
                    this.action = MovementController.Action.WAIT;
                }
            }
        }

        private boolean func_220673_a(Vector3d vector3d, int n) {
            AxisAlignedBB axisAlignedBB = this.parentEntity.getBoundingBox();
            for (int i = 1; i < n; ++i) {
                if (this.parentEntity.world.hasNoCollisions(this.parentEntity, axisAlignedBB = axisAlignedBB.offset(vector3d))) continue;
                return true;
            }
            return false;
        }
    }

    static class RandomFlyGoal
    extends Goal {
        private final GhastEntity parentEntity;

        public RandomFlyGoal(GhastEntity ghastEntity) {
            this.parentEntity = ghastEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            double d;
            double d2;
            MovementController movementController = this.parentEntity.getMoveHelper();
            if (!movementController.isUpdating()) {
                return false;
            }
            double d3 = movementController.getX() - this.parentEntity.getPosX();
            double d4 = d3 * d3 + (d2 = movementController.getY() - this.parentEntity.getPosY()) * d2 + (d = movementController.getZ() - this.parentEntity.getPosZ()) * d;
            return d4 < 1.0 || d4 > 3600.0;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return true;
        }

        @Override
        public void startExecuting() {
            Random random2 = this.parentEntity.getRNG();
            double d = this.parentEntity.getPosX() + (double)((random2.nextFloat() * 2.0f - 1.0f) * 16.0f);
            double d2 = this.parentEntity.getPosY() + (double)((random2.nextFloat() * 2.0f - 1.0f) * 16.0f);
            double d3 = this.parentEntity.getPosZ() + (double)((random2.nextFloat() * 2.0f - 1.0f) * 16.0f);
            this.parentEntity.getMoveHelper().setMoveTo(d, d2, d3, 1.0);
        }
    }

    static class LookAroundGoal
    extends Goal {
        private final GhastEntity parentEntity;

        public LookAroundGoal(GhastEntity ghastEntity) {
            this.parentEntity = ghastEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean shouldExecute() {
            return false;
        }

        @Override
        public void tick() {
            if (this.parentEntity.getAttackTarget() == null) {
                Vector3d vector3d = this.parentEntity.getMotion();
                this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw = -((float)MathHelper.atan2(vector3d.x, vector3d.z)) * 57.295776f;
            } else {
                LivingEntity livingEntity = this.parentEntity.getAttackTarget();
                double d = 64.0;
                if (livingEntity.getDistanceSq(this.parentEntity) < 4096.0) {
                    double d2 = livingEntity.getPosX() - this.parentEntity.getPosX();
                    double d3 = livingEntity.getPosZ() - this.parentEntity.getPosZ();
                    this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw = -((float)MathHelper.atan2(d2, d3)) * 57.295776f;
                }
            }
        }
    }

    static class FireballAttackGoal
    extends Goal {
        private final GhastEntity parentEntity;
        public int attackTimer;

        public FireballAttackGoal(GhastEntity ghastEntity) {
            this.parentEntity = ghastEntity;
        }

        @Override
        public boolean shouldExecute() {
            return this.parentEntity.getAttackTarget() != null;
        }

        @Override
        public void startExecuting() {
            this.attackTimer = 0;
        }

        @Override
        public void resetTask() {
            this.parentEntity.setAttacking(true);
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.parentEntity.getAttackTarget();
            double d = 64.0;
            if (livingEntity.getDistanceSq(this.parentEntity) < 4096.0 && this.parentEntity.canEntityBeSeen(livingEntity)) {
                World world = this.parentEntity.world;
                ++this.attackTimer;
                if (this.attackTimer == 10 && !this.parentEntity.isSilent()) {
                    world.playEvent(null, 1015, this.parentEntity.getPosition(), 0);
                }
                if (this.attackTimer == 20) {
                    double d2 = 4.0;
                    Vector3d vector3d = this.parentEntity.getLook(1.0f);
                    double d3 = livingEntity.getPosX() - (this.parentEntity.getPosX() + vector3d.x * 4.0);
                    double d4 = livingEntity.getPosYHeight(0.5) - (0.5 + this.parentEntity.getPosYHeight(0.5));
                    double d5 = livingEntity.getPosZ() - (this.parentEntity.getPosZ() + vector3d.z * 4.0);
                    if (!this.parentEntity.isSilent()) {
                        world.playEvent(null, 1016, this.parentEntity.getPosition(), 0);
                    }
                    FireballEntity fireballEntity = new FireballEntity(world, this.parentEntity, d3, d4, d5);
                    fireballEntity.explosionPower = this.parentEntity.getFireballStrength();
                    fireballEntity.setPosition(this.parentEntity.getPosX() + vector3d.x * 4.0, this.parentEntity.getPosYHeight(0.5) + 0.5, fireballEntity.getPosZ() + vector3d.z * 4.0);
                    world.addEntity(fireballEntity);
                    this.attackTimer = -40;
                }
            } else if (this.attackTimer > 0) {
                --this.attackTimer;
            }
            this.parentEntity.setAttacking(this.attackTimer > 10);
        }
    }
}

