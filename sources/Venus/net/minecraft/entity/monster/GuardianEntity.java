/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.util.EnumSet;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.monster.ElderGuardianEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class GuardianEntity
extends MonsterEntity {
    private static final DataParameter<Boolean> MOVING = EntityDataManager.createKey(GuardianEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> TARGET_ENTITY = EntityDataManager.createKey(GuardianEntity.class, DataSerializers.VARINT);
    private float clientSideTailAnimation;
    private float clientSideTailAnimationO;
    private float clientSideTailAnimationSpeed;
    private float clientSideSpikesAnimation;
    private float clientSideSpikesAnimationO;
    private LivingEntity targetedEntity;
    private int clientSideAttackTime;
    private boolean clientSideTouchedGround;
    protected RandomWalkingGoal wander;

    public GuardianEntity(EntityType<? extends GuardianEntity> entityType, World world) {
        super((EntityType<? extends MonsterEntity>)entityType, world);
        this.experienceValue = 10;
        this.setPathPriority(PathNodeType.WATER, 0.0f);
        this.moveController = new MoveHelperController(this);
        this.clientSideTailAnimationO = this.clientSideTailAnimation = this.rand.nextFloat();
    }

    @Override
    protected void registerGoals() {
        MoveTowardsRestrictionGoal moveTowardsRestrictionGoal = new MoveTowardsRestrictionGoal(this, 1.0);
        this.wander = new RandomWalkingGoal(this, 1.0, 80);
        this.goalSelector.addGoal(4, new AttackGoal(this));
        this.goalSelector.addGoal(5, moveTowardsRestrictionGoal);
        this.goalSelector.addGoal(7, this.wander);
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(8, new LookAtGoal(this, GuardianEntity.class, 12.0f, 0.01f));
        this.goalSelector.addGoal(9, new LookRandomlyGoal(this));
        this.wander.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        moveTowardsRestrictionGoal.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 10, true, false, new TargetPredicate(this)));
    }

    public static AttributeModifierMap.MutableAttribute func_234292_eK_() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.ATTACK_DAMAGE, 6.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5).createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0).createMutableAttribute(Attributes.MAX_HEALTH, 30.0);
    }

    @Override
    protected PathNavigator createNavigator(World world) {
        return new SwimmerPathNavigator(this, world);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(MOVING, false);
        this.dataManager.register(TARGET_ENTITY, 0);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return false;
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.WATER;
    }

    public boolean isMoving() {
        return this.dataManager.get(MOVING);
    }

    private void setMoving(boolean bl) {
        this.dataManager.set(MOVING, bl);
    }

    public int getAttackDuration() {
        return 1;
    }

    private void setTargetedEntity(int n) {
        this.dataManager.set(TARGET_ENTITY, n);
    }

    public boolean hasTargetedEntity() {
        return this.dataManager.get(TARGET_ENTITY) != 0;
    }

    @Nullable
    public LivingEntity getTargetedEntity() {
        if (!this.hasTargetedEntity()) {
            return null;
        }
        if (this.world.isRemote) {
            if (this.targetedEntity != null) {
                return this.targetedEntity;
            }
            Entity entity2 = this.world.getEntityByID(this.dataManager.get(TARGET_ENTITY));
            if (entity2 instanceof LivingEntity) {
                this.targetedEntity = (LivingEntity)entity2;
                return this.targetedEntity;
            }
            return null;
        }
        return this.getAttackTarget();
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        super.notifyDataManagerChange(dataParameter);
        if (TARGET_ENTITY.equals(dataParameter)) {
            this.clientSideAttackTime = 0;
            this.targetedEntity = null;
        }
    }

    @Override
    public int getTalkInterval() {
        return 1;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isInWaterOrBubbleColumn() ? SoundEvents.ENTITY_GUARDIAN_AMBIENT : SoundEvents.ENTITY_GUARDIAN_AMBIENT_LAND;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return this.isInWaterOrBubbleColumn() ? SoundEvents.ENTITY_GUARDIAN_HURT : SoundEvents.ENTITY_GUARDIAN_HURT_LAND;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return this.isInWaterOrBubbleColumn() ? SoundEvents.ENTITY_GUARDIAN_DEATH : SoundEvents.ENTITY_GUARDIAN_DEATH_LAND;
    }

    @Override
    protected boolean canTriggerWalking() {
        return true;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return entitySize.height * 0.5f;
    }

    @Override
    public float getBlockPathWeight(BlockPos blockPos, IWorldReader iWorldReader) {
        return iWorldReader.getFluidState(blockPos).isTagged(FluidTags.WATER) ? 10.0f + iWorldReader.getBrightness(blockPos) - 0.5f : super.getBlockPathWeight(blockPos, iWorldReader);
    }

    @Override
    public void livingTick() {
        if (this.isAlive()) {
            if (this.world.isRemote) {
                Object object;
                this.clientSideTailAnimationO = this.clientSideTailAnimation;
                if (!this.isInWater()) {
                    this.clientSideTailAnimationSpeed = 2.0f;
                    object = this.getMotion();
                    if (((Vector3d)object).y > 0.0 && this.clientSideTouchedGround && !this.isSilent()) {
                        this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), this.getFlopSound(), this.getSoundCategory(), 1.0f, 1.0f, true);
                    }
                    this.clientSideTouchedGround = ((Vector3d)object).y < 0.0 && this.world.isTopSolid(this.getPosition().down(), this);
                } else {
                    this.clientSideTailAnimationSpeed = this.isMoving() ? (this.clientSideTailAnimationSpeed < 0.5f ? 4.0f : (this.clientSideTailAnimationSpeed += (0.5f - this.clientSideTailAnimationSpeed) * 0.1f)) : (this.clientSideTailAnimationSpeed += (0.125f - this.clientSideTailAnimationSpeed) * 0.2f);
                }
                this.clientSideTailAnimation += this.clientSideTailAnimationSpeed;
                this.clientSideSpikesAnimationO = this.clientSideSpikesAnimation;
                this.clientSideSpikesAnimation = !this.isInWaterOrBubbleColumn() ? this.rand.nextFloat() : (this.isMoving() ? (this.clientSideSpikesAnimation += (0.0f - this.clientSideSpikesAnimation) * 0.25f) : (this.clientSideSpikesAnimation += (1.0f - this.clientSideSpikesAnimation) * 0.06f));
                if (this.isMoving() && this.isInWater()) {
                    object = this.getLook(0.0f);
                    for (int i = 0; i < 2; ++i) {
                        this.world.addParticle(ParticleTypes.BUBBLE, this.getPosXRandom(0.5) - ((Vector3d)object).x * 1.5, this.getPosYRandom() - ((Vector3d)object).y * 1.5, this.getPosZRandom(0.5) - ((Vector3d)object).z * 1.5, 0.0, 0.0, 0.0);
                    }
                }
                if (this.hasTargetedEntity()) {
                    if (this.clientSideAttackTime < this.getAttackDuration()) {
                        ++this.clientSideAttackTime;
                    }
                    if ((object = this.getTargetedEntity()) != null) {
                        this.getLookController().setLookPositionWithEntity((Entity)object, 90.0f, 90.0f);
                        this.getLookController().tick();
                        double d = this.getAttackAnimationScale(0.0f);
                        double d2 = ((Entity)object).getPosX() - this.getPosX();
                        double d3 = ((Entity)object).getPosYHeight(0.5) - this.getPosYEye();
                        double d4 = ((Entity)object).getPosZ() - this.getPosZ();
                        double d5 = Math.sqrt(d2 * d2 + d3 * d3 + d4 * d4);
                        d2 /= d5;
                        d3 /= d5;
                        d4 /= d5;
                        double d6 = this.rand.nextDouble();
                        while (d6 < d5) {
                            this.world.addParticle(ParticleTypes.BUBBLE, this.getPosX() + d2 * (d6 += 1.8 - d + this.rand.nextDouble() * (1.7 - d)), this.getPosYEye() + d3 * d6, this.getPosZ() + d4 * d6, 0.0, 0.0, 0.0);
                        }
                    }
                }
            }
            if (this.isInWaterOrBubbleColumn()) {
                this.setAir(300);
            } else if (this.onGround) {
                this.setMotion(this.getMotion().add((this.rand.nextFloat() * 2.0f - 1.0f) * 0.4f, 0.5, (this.rand.nextFloat() * 2.0f - 1.0f) * 0.4f));
                this.rotationYaw = this.rand.nextFloat() * 360.0f;
                this.onGround = false;
                this.isAirBorne = true;
            }
            if (this.hasTargetedEntity()) {
                this.rotationYaw = this.rotationYawHead;
            }
        }
        super.livingTick();
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_GUARDIAN_FLOP;
    }

    public float getTailAnimation(float f) {
        return MathHelper.lerp(f, this.clientSideTailAnimationO, this.clientSideTailAnimation);
    }

    public float getSpikesAnimation(float f) {
        return MathHelper.lerp(f, this.clientSideSpikesAnimationO, this.clientSideSpikesAnimation);
    }

    public float getAttackAnimationScale(float f) {
        return ((float)this.clientSideAttackTime + f) / (float)this.getAttackDuration();
    }

    @Override
    public boolean isNotColliding(IWorldReader iWorldReader) {
        return iWorldReader.checkNoEntityCollision(this);
    }

    public static boolean func_223329_b(EntityType<? extends GuardianEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        return !(random2.nextInt(20) != 0 && iWorld.canBlockSeeSky(blockPos) || iWorld.getDifficulty() == Difficulty.PEACEFUL || spawnReason != SpawnReason.SPAWNER && !iWorld.getFluidState(blockPos).isTagged(FluidTags.WATER));
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (!this.isMoving() && !damageSource.isMagicDamage() && damageSource.getImmediateSource() instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)damageSource.getImmediateSource();
            if (!damageSource.isExplosion()) {
                livingEntity.attackEntityFrom(DamageSource.causeThornsDamage(this), 2.0f);
            }
        }
        if (this.wander != null) {
            this.wander.makeUpdate();
        }
        return super.attackEntityFrom(damageSource, f);
    }

    @Override
    public int getVerticalFaceSpeed() {
        return 1;
    }

    @Override
    public void travel(Vector3d vector3d) {
        if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(0.1f, vector3d);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.9));
            if (!this.isMoving() && this.getAttackTarget() == null) {
                this.setMotion(this.getMotion().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(vector3d);
        }
    }

    static class MoveHelperController
    extends MovementController {
        private final GuardianEntity entityGuardian;

        public MoveHelperController(GuardianEntity guardianEntity) {
            super(guardianEntity);
            this.entityGuardian = guardianEntity;
        }

        @Override
        public void tick() {
            if (this.action == MovementController.Action.MOVE_TO && !this.entityGuardian.getNavigator().noPath()) {
                Vector3d vector3d = new Vector3d(this.posX - this.entityGuardian.getPosX(), this.posY - this.entityGuardian.getPosY(), this.posZ - this.entityGuardian.getPosZ());
                double d = vector3d.length();
                double d2 = vector3d.x / d;
                double d3 = vector3d.y / d;
                double d4 = vector3d.z / d;
                float f = (float)(MathHelper.atan2(vector3d.z, vector3d.x) * 57.2957763671875) - 90.0f;
                this.entityGuardian.renderYawOffset = this.entityGuardian.rotationYaw = this.limitAngle(this.entityGuardian.rotationYaw, f, 90.0f);
                float f2 = (float)(this.speed * this.entityGuardian.getAttributeValue(Attributes.MOVEMENT_SPEED));
                float f3 = MathHelper.lerp(0.125f, this.entityGuardian.getAIMoveSpeed(), f2);
                this.entityGuardian.setAIMoveSpeed(f3);
                double d5 = Math.sin((double)(this.entityGuardian.ticksExisted + this.entityGuardian.getEntityId()) * 0.5) * 0.05;
                double d6 = Math.cos(this.entityGuardian.rotationYaw * ((float)Math.PI / 180));
                double d7 = Math.sin(this.entityGuardian.rotationYaw * ((float)Math.PI / 180));
                double d8 = Math.sin((double)(this.entityGuardian.ticksExisted + this.entityGuardian.getEntityId()) * 0.75) * 0.05;
                this.entityGuardian.setMotion(this.entityGuardian.getMotion().add(d5 * d6, d8 * (d7 + d6) * 0.25 + (double)f3 * d3 * 0.1, d5 * d7));
                LookController lookController = this.entityGuardian.getLookController();
                double d9 = this.entityGuardian.getPosX() + d2 * 2.0;
                double d10 = this.entityGuardian.getPosYEye() + d3 / d;
                double d11 = this.entityGuardian.getPosZ() + d4 * 2.0;
                double d12 = lookController.getLookPosX();
                double d13 = lookController.getLookPosY();
                double d14 = lookController.getLookPosZ();
                if (!lookController.getIsLooking()) {
                    d12 = d9;
                    d13 = d10;
                    d14 = d11;
                }
                this.entityGuardian.getLookController().setLookPosition(MathHelper.lerp(0.125, d12, d9), MathHelper.lerp(0.125, d13, d10), MathHelper.lerp(0.125, d14, d11), 10.0f, 40.0f);
                this.entityGuardian.setMoving(false);
            } else {
                this.entityGuardian.setAIMoveSpeed(0.0f);
                this.entityGuardian.setMoving(true);
            }
        }
    }

    static class AttackGoal
    extends Goal {
        private final GuardianEntity guardian;
        private int tickCounter;
        private final boolean isElder;

        public AttackGoal(GuardianEntity guardianEntity) {
            this.guardian = guardianEntity;
            this.isElder = guardianEntity instanceof ElderGuardianEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean shouldExecute() {
            LivingEntity livingEntity = this.guardian.getAttackTarget();
            return livingEntity != null && livingEntity.isAlive();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return super.shouldContinueExecuting() && (this.isElder || this.guardian.getDistanceSq(this.guardian.getAttackTarget()) > 9.0);
        }

        @Override
        public void startExecuting() {
            this.tickCounter = -10;
            this.guardian.getNavigator().clearPath();
            this.guardian.getLookController().setLookPositionWithEntity(this.guardian.getAttackTarget(), 90.0f, 90.0f);
            this.guardian.isAirBorne = true;
        }

        @Override
        public void resetTask() {
            this.guardian.setTargetedEntity(0);
            this.guardian.setAttackTarget(null);
            this.guardian.wander.makeUpdate();
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.guardian.getAttackTarget();
            this.guardian.getNavigator().clearPath();
            this.guardian.getLookController().setLookPositionWithEntity(livingEntity, 90.0f, 90.0f);
            if (!this.guardian.canEntityBeSeen(livingEntity)) {
                this.guardian.setAttackTarget(null);
            } else {
                ++this.tickCounter;
                if (this.tickCounter == 0) {
                    this.guardian.setTargetedEntity(this.guardian.getAttackTarget().getEntityId());
                    if (!this.guardian.isSilent()) {
                        this.guardian.world.setEntityState(this.guardian, (byte)21);
                    }
                } else if (this.tickCounter >= this.guardian.getAttackDuration()) {
                    float f = 1.0f;
                    if (this.guardian.world.getDifficulty() == Difficulty.HARD) {
                        f += 2.0f;
                    }
                    if (this.isElder) {
                        f += 2.0f;
                    }
                    livingEntity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.guardian, this.guardian), f);
                    livingEntity.attackEntityFrom(DamageSource.causeMobDamage(this.guardian), (float)this.guardian.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    this.guardian.setAttackTarget(null);
                }
                super.tick();
            }
        }
    }

    static class TargetPredicate
    implements Predicate<LivingEntity> {
        private final GuardianEntity parentEntity;

        public TargetPredicate(GuardianEntity guardianEntity) {
            this.parentEntity = guardianEntity;
        }

        @Override
        public boolean test(@Nullable LivingEntity livingEntity) {
            return (livingEntity instanceof PlayerEntity || livingEntity instanceof SquidEntity) && livingEntity.getDistanceSq(this.parentEntity) > 9.0;
        }

        @Override
        public boolean test(@Nullable Object object) {
            return this.test((LivingEntity)object);
        }
    }
}

