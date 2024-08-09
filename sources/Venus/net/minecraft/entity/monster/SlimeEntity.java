/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;

public class SlimeEntity
extends MobEntity
implements IMob {
    private static final DataParameter<Integer> SLIME_SIZE = EntityDataManager.createKey(SlimeEntity.class, DataSerializers.VARINT);
    public float squishAmount;
    public float squishFactor;
    public float prevSquishFactor;
    private boolean wasOnGround;

    public SlimeEntity(EntityType<? extends SlimeEntity> entityType, World world) {
        super((EntityType<? extends MobEntity>)entityType, world);
        this.moveController = new MoveHelperController(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new AttackGoal(this));
        this.goalSelector.addGoal(3, new FaceRandomGoal(this));
        this.goalSelector.addGoal(5, new HopGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, 10, true, false, this::lambda$registerGoals$0));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolemEntity>((MobEntity)this, IronGolemEntity.class, true));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(SLIME_SIZE, 1);
    }

    protected void setSlimeSize(int n, boolean bl) {
        this.dataManager.set(SLIME_SIZE, n);
        this.recenterBoundingBox();
        this.recalculateSize();
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(n * n);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2f + 0.1f * (float)n);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(n);
        if (bl) {
            this.setHealth(this.getMaxHealth());
        }
        this.experienceValue = n;
    }

    public int getSlimeSize() {
        return this.dataManager.get(SLIME_SIZE);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("Size", this.getSlimeSize() - 1);
        compoundNBT.putBoolean("wasOnGround", this.wasOnGround);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        int n = compoundNBT.getInt("Size");
        if (n < 0) {
            n = 0;
        }
        this.setSlimeSize(n + 1, true);
        super.readAdditional(compoundNBT);
        this.wasOnGround = compoundNBT.getBoolean("wasOnGround");
    }

    public boolean isSmallSlime() {
        return this.getSlimeSize() <= 1;
    }

    protected IParticleData getSquishParticle() {
        return ParticleTypes.ITEM_SLIME;
    }

    @Override
    protected boolean isDespawnPeaceful() {
        return this.getSlimeSize() > 0;
    }

    @Override
    public void tick() {
        this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5f;
        this.prevSquishFactor = this.squishFactor;
        super.tick();
        if (this.onGround && !this.wasOnGround) {
            int n = this.getSlimeSize();
            for (int i = 0; i < n * 8; ++i) {
                float f = this.rand.nextFloat() * ((float)Math.PI * 2);
                float f2 = this.rand.nextFloat() * 0.5f + 0.5f;
                float f3 = MathHelper.sin(f) * (float)n * 0.5f * f2;
                float f4 = MathHelper.cos(f) * (float)n * 0.5f * f2;
                this.world.addParticle(this.getSquishParticle(), this.getPosX() + (double)f3, this.getPosY(), this.getPosZ() + (double)f4, 0.0, 0.0, 0.0);
            }
            this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) / 0.8f);
            this.squishAmount = -0.5f;
        } else if (!this.onGround && this.wasOnGround) {
            this.squishAmount = 1.0f;
        }
        this.wasOnGround = this.onGround;
        this.alterSquishAmount();
    }

    protected void alterSquishAmount() {
        this.squishAmount *= 0.6f;
    }

    protected int getJumpDelay() {
        return this.rand.nextInt(20) + 10;
    }

    @Override
    public void recalculateSize() {
        double d = this.getPosX();
        double d2 = this.getPosY();
        double d3 = this.getPosZ();
        super.recalculateSize();
        this.setPosition(d, d2, d3);
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        if (SLIME_SIZE.equals(dataParameter)) {
            this.recalculateSize();
            this.rotationYaw = this.rotationYawHead;
            this.renderYawOffset = this.rotationYawHead;
            if (this.isInWater() && this.rand.nextInt(20) == 0) {
                this.doWaterSplashEffect();
            }
        }
        super.notifyDataManagerChange(dataParameter);
    }

    public EntityType<? extends SlimeEntity> getType() {
        return super.getType();
    }

    @Override
    public void remove() {
        int n = this.getSlimeSize();
        if (!this.world.isRemote && n > 1 && this.getShouldBeDead()) {
            ITextComponent iTextComponent = this.getCustomName();
            boolean bl = this.isAIDisabled();
            float f = (float)n / 4.0f;
            int n2 = n / 2;
            int n3 = 2 + this.rand.nextInt(3);
            for (int i = 0; i < n3; ++i) {
                float f2 = ((float)(i % 2) - 0.5f) * f;
                float f3 = ((float)(i / 2) - 0.5f) * f;
                SlimeEntity slimeEntity = this.getType().create(this.world);
                if (this.isNoDespawnRequired()) {
                    slimeEntity.enablePersistence();
                }
                slimeEntity.setCustomName(iTextComponent);
                slimeEntity.setNoAI(bl);
                slimeEntity.setInvulnerable(this.isInvulnerable());
                slimeEntity.setSlimeSize(n2, false);
                slimeEntity.setLocationAndAngles(this.getPosX() + (double)f2, this.getPosY() + 0.5, this.getPosZ() + (double)f3, this.rand.nextFloat() * 360.0f, 0.0f);
                this.world.addEntity(slimeEntity);
            }
        }
        super.remove();
    }

    @Override
    public void applyEntityCollision(Entity entity2) {
        super.applyEntityCollision(entity2);
        if (entity2 instanceof IronGolemEntity && this.canDamagePlayer()) {
            this.dealDamage((LivingEntity)entity2);
        }
    }

    @Override
    public void onCollideWithPlayer(PlayerEntity playerEntity) {
        if (this.canDamagePlayer()) {
            this.dealDamage(playerEntity);
        }
    }

    protected void dealDamage(LivingEntity livingEntity) {
        if (this.isAlive()) {
            int n = this.getSlimeSize();
            if (this.getDistanceSq(livingEntity) < 0.6 * (double)n * 0.6 * (double)n && this.canEntityBeSeen(livingEntity) && livingEntity.attackEntityFrom(DamageSource.causeMobDamage(this), this.func_225512_er_())) {
                this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
                this.applyEnchantments(this, livingEntity);
            }
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return 0.625f * entitySize.height;
    }

    protected boolean canDamagePlayer() {
        return !this.isSmallSlime() && this.isServerWorld();
    }

    protected float func_225512_er_() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return this.isSmallSlime() ? SoundEvents.ENTITY_SLIME_HURT_SMALL : SoundEvents.ENTITY_SLIME_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return this.isSmallSlime() ? SoundEvents.ENTITY_SLIME_DEATH_SMALL : SoundEvents.ENTITY_SLIME_DEATH;
    }

    protected SoundEvent getSquishSound() {
        return this.isSmallSlime() ? SoundEvents.ENTITY_SLIME_SQUISH_SMALL : SoundEvents.ENTITY_SLIME_SQUISH;
    }

    @Override
    protected ResourceLocation getLootTable() {
        return this.getSlimeSize() == 1 ? this.getType().getLootTable() : LootTables.EMPTY;
    }

    public static boolean func_223366_c(EntityType<SlimeEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        if (iWorld.getDifficulty() != Difficulty.PEACEFUL) {
            boolean bl;
            if (Objects.equals(iWorld.func_242406_i(blockPos), Optional.of(Biomes.SWAMP)) && blockPos.getY() > 50 && blockPos.getY() < 70 && random2.nextFloat() < 0.5f && random2.nextFloat() < iWorld.getMoonFactor() && iWorld.getLight(blockPos) <= random2.nextInt(8)) {
                return SlimeEntity.canSpawnOn(entityType, iWorld, spawnReason, blockPos, random2);
            }
            if (!(iWorld instanceof ISeedReader)) {
                return true;
            }
            ChunkPos chunkPos = new ChunkPos(blockPos);
            boolean bl2 = bl = SharedSeedRandom.seedSlimeChunk(chunkPos.x, chunkPos.z, ((ISeedReader)iWorld).getSeed(), 987234911L).nextInt(10) == 0;
            if (random2.nextInt(10) == 0 && bl && blockPos.getY() < 40) {
                return SlimeEntity.canSpawnOn(entityType, iWorld, spawnReason, blockPos, random2);
            }
        }
        return true;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4f * (float)this.getSlimeSize();
    }

    @Override
    public int getVerticalFaceSpeed() {
        return 1;
    }

    protected boolean makesSoundOnJump() {
        return this.getSlimeSize() > 0;
    }

    @Override
    protected void jump() {
        Vector3d vector3d = this.getMotion();
        this.setMotion(vector3d.x, this.getJumpUpwardsMotion(), vector3d.z);
        this.isAirBorne = true;
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        int n = this.rand.nextInt(3);
        if (n < 2 && this.rand.nextFloat() < 0.5f * difficultyInstance.getClampedAdditionalDifficulty()) {
            ++n;
        }
        int n2 = 1 << n;
        this.setSlimeSize(n2, false);
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    private float func_234304_m_() {
        float f = this.isSmallSlime() ? 1.4f : 0.8f;
        return ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * f;
    }

    protected SoundEvent getJumpSound() {
        return this.isSmallSlime() ? SoundEvents.ENTITY_SLIME_JUMP_SMALL : SoundEvents.ENTITY_SLIME_JUMP;
    }

    @Override
    public EntitySize getSize(Pose pose) {
        return super.getSize(pose).scale(0.255f * (float)this.getSlimeSize());
    }

    private boolean lambda$registerGoals$0(LivingEntity livingEntity) {
        return Math.abs(livingEntity.getPosY() - this.getPosY()) <= 4.0;
    }

    static boolean access$000(SlimeEntity slimeEntity) {
        return slimeEntity.onGround;
    }

    static class MoveHelperController
    extends MovementController {
        private float yRot;
        private int jumpDelay;
        private final SlimeEntity slime;
        private boolean isAggressive;

        public MoveHelperController(SlimeEntity slimeEntity) {
            super(slimeEntity);
            this.slime = slimeEntity;
            this.yRot = 180.0f * slimeEntity.rotationYaw / (float)Math.PI;
        }

        public void setDirection(float f, boolean bl) {
            this.yRot = f;
            this.isAggressive = bl;
        }

        public void setSpeed(double d) {
            this.speed = d;
            this.action = MovementController.Action.MOVE_TO;
        }

        @Override
        public void tick() {
            this.mob.rotationYawHead = this.mob.rotationYaw = this.limitAngle(this.mob.rotationYaw, this.yRot, 90.0f);
            this.mob.renderYawOffset = this.mob.rotationYaw;
            if (this.action != MovementController.Action.MOVE_TO) {
                this.mob.setMoveForward(0.0f);
            } else {
                this.action = MovementController.Action.WAIT;
                if (this.mob.isOnGround()) {
                    this.mob.setAIMoveSpeed((float)(this.speed * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.slime.getJumpDelay();
                        if (this.isAggressive) {
                            this.jumpDelay /= 3;
                        }
                        this.slime.getJumpController().setJumping();
                        if (this.slime.makesSoundOnJump()) {
                            this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), this.slime.func_234304_m_());
                        }
                    } else {
                        this.slime.moveStrafing = 0.0f;
                        this.slime.moveForward = 0.0f;
                        this.mob.setAIMoveSpeed(0.0f);
                    }
                } else {
                    this.mob.setAIMoveSpeed((float)(this.speed * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                }
            }
        }
    }

    static class FloatGoal
    extends Goal {
        private final SlimeEntity slime;

        public FloatGoal(SlimeEntity slimeEntity) {
            this.slime = slimeEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
            slimeEntity.getNavigator().setCanSwim(false);
        }

        @Override
        public boolean shouldExecute() {
            return (this.slime.isInWater() || this.slime.isInLava()) && this.slime.getMoveHelper() instanceof MoveHelperController;
        }

        @Override
        public void tick() {
            if (this.slime.getRNG().nextFloat() < 0.8f) {
                this.slime.getJumpController().setJumping();
            }
            ((MoveHelperController)this.slime.getMoveHelper()).setSpeed(1.2);
        }
    }

    static class AttackGoal
    extends Goal {
        private final SlimeEntity slime;
        private int growTieredTimer;

        public AttackGoal(SlimeEntity slimeEntity) {
            this.slime = slimeEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean shouldExecute() {
            LivingEntity livingEntity = this.slime.getAttackTarget();
            if (livingEntity == null) {
                return true;
            }
            if (!livingEntity.isAlive()) {
                return true;
            }
            return livingEntity instanceof PlayerEntity && ((PlayerEntity)livingEntity).abilities.disableDamage ? false : this.slime.getMoveHelper() instanceof MoveHelperController;
        }

        @Override
        public void startExecuting() {
            this.growTieredTimer = 300;
            super.startExecuting();
        }

        @Override
        public boolean shouldContinueExecuting() {
            LivingEntity livingEntity = this.slime.getAttackTarget();
            if (livingEntity == null) {
                return true;
            }
            if (!livingEntity.isAlive()) {
                return true;
            }
            if (livingEntity instanceof PlayerEntity && ((PlayerEntity)livingEntity).abilities.disableDamage) {
                return true;
            }
            return --this.growTieredTimer > 0;
        }

        @Override
        public void tick() {
            this.slime.faceEntity(this.slime.getAttackTarget(), 10.0f, 10.0f);
            ((MoveHelperController)this.slime.getMoveHelper()).setDirection(this.slime.rotationYaw, this.slime.canDamagePlayer());
        }
    }

    static class FaceRandomGoal
    extends Goal {
        private final SlimeEntity slime;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public FaceRandomGoal(SlimeEntity slimeEntity) {
            this.slime = slimeEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean shouldExecute() {
            return this.slime.getAttackTarget() == null && (SlimeEntity.access$000(this.slime) || this.slime.isInWater() || this.slime.isInLava() || this.slime.isPotionActive(Effects.LEVITATION)) && this.slime.getMoveHelper() instanceof MoveHelperController;
        }

        @Override
        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = 40 + this.slime.getRNG().nextInt(60);
                this.chosenDegrees = this.slime.getRNG().nextInt(360);
            }
            ((MoveHelperController)this.slime.getMoveHelper()).setDirection(this.chosenDegrees, true);
        }
    }

    static class HopGoal
    extends Goal {
        private final SlimeEntity slime;

        public HopGoal(SlimeEntity slimeEntity) {
            this.slime = slimeEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            return !this.slime.isPassenger();
        }

        @Override
        public void tick() {
            ((MoveHelperController)this.slime.getMoveHelper()).setSpeed(1.0);
        }
    }
}

