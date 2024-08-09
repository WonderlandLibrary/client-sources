/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BegGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.NonTamedTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.ai.goal.ResetAngerGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class WolfEntity
extends TameableEntity
implements IAngerable {
    private static final DataParameter<Boolean> BEGGING = EntityDataManager.createKey(WolfEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> COLLAR_COLOR = EntityDataManager.createKey(WolfEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> field_234232_bz_ = EntityDataManager.createKey(WolfEntity.class, DataSerializers.VARINT);
    public static final Predicate<LivingEntity> TARGET_ENTITIES = WolfEntity::lambda$static$0;
    private float headRotationCourse;
    private float headRotationCourseOld;
    private boolean isWet;
    private boolean isShaking;
    private float timeWolfIsShaking;
    private float prevTimeWolfIsShaking;
    private static final RangedInteger field_234230_bG_ = TickRangeConverter.convertRange(20, 39);
    private UUID field_234231_bH_;

    public WolfEntity(EntityType<? extends WolfEntity> entityType, World world) {
        super((EntityType<? extends TameableEntity>)entityType, world);
        this.setTamed(true);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<LlamaEntity>(this, this, LlamaEntity.class, 24.0f, 1.5, 1.5));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4f));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0, 10.0f, 2.0f, false));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0));
        this.goalSelector.addGoal(9, new BegGoal(this, 8.0f));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this, new Class[0]).setCallsForHelp(new Class[0]));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, 10, true, false, this::func_233680_b_));
        this.targetSelector.addGoal(5, new NonTamedTargetGoal<AnimalEntity>(this, AnimalEntity.class, false, TARGET_ENTITIES));
        this.targetSelector.addGoal(6, new NonTamedTargetGoal<TurtleEntity>(this, TurtleEntity.class, false, TurtleEntity.TARGET_DRY_BABY));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<AbstractSkeletonEntity>((MobEntity)this, AbstractSkeletonEntity.class, false));
        this.targetSelector.addGoal(8, new ResetAngerGoal<WolfEntity>(this, true));
    }

    public static AttributeModifierMap.MutableAttribute func_234233_eS_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3f).createMutableAttribute(Attributes.MAX_HEALTH, 8.0).createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(BEGGING, false);
        this.dataManager.register(COLLAR_COLOR, DyeColor.RED.getId());
        this.dataManager.register(field_234232_bz_, 0);
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15f, 1.0f);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putByte("CollarColor", (byte)this.getCollarColor().getId());
        this.writeAngerNBT(compoundNBT);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("CollarColor", 0)) {
            this.setCollarColor(DyeColor.byId(compoundNBT.getInt("CollarColor")));
        }
        this.readAngerNBT((ServerWorld)this.world, compoundNBT);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.func_233678_J__()) {
            return SoundEvents.ENTITY_WOLF_GROWL;
        }
        if (this.rand.nextInt(3) == 0) {
            return this.isTamed() && this.getHealth() < 10.0f ? SoundEvents.ENTITY_WOLF_WHINE : SoundEvents.ENTITY_WOLF_PANT;
        }
        return SoundEvents.ENTITY_WOLF_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_WOLF_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WOLF_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote && this.isWet && !this.isShaking && !this.hasPath() && this.onGround) {
            this.isShaking = true;
            this.timeWolfIsShaking = 0.0f;
            this.prevTimeWolfIsShaking = 0.0f;
            this.world.setEntityState(this, (byte)8);
        }
        if (!this.world.isRemote) {
            this.func_241359_a_((ServerWorld)this.world, false);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAlive()) {
            this.headRotationCourseOld = this.headRotationCourse;
            this.headRotationCourse = this.isBegging() ? (this.headRotationCourse += (1.0f - this.headRotationCourse) * 0.4f) : (this.headRotationCourse += (0.0f - this.headRotationCourse) * 0.4f);
            if (this.isInWaterRainOrBubbleColumn()) {
                this.isWet = true;
                if (this.isShaking && !this.world.isRemote) {
                    this.world.setEntityState(this, (byte)56);
                    this.func_242326_eZ();
                }
            } else if ((this.isWet || this.isShaking) && this.isShaking) {
                if (this.timeWolfIsShaking == 0.0f) {
                    this.playSound(SoundEvents.ENTITY_WOLF_SHAKE, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
                }
                this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
                this.timeWolfIsShaking += 0.05f;
                if (this.prevTimeWolfIsShaking >= 2.0f) {
                    this.isWet = false;
                    this.isShaking = false;
                    this.prevTimeWolfIsShaking = 0.0f;
                    this.timeWolfIsShaking = 0.0f;
                }
                if (this.timeWolfIsShaking > 0.4f) {
                    float f = (float)this.getPosY();
                    int n = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4f) * (float)Math.PI) * 7.0f);
                    Vector3d vector3d = this.getMotion();
                    for (int i = 0; i < n; ++i) {
                        float f2 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.getWidth() * 0.5f;
                        float f3 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.getWidth() * 0.5f;
                        this.world.addParticle(ParticleTypes.SPLASH, this.getPosX() + (double)f2, f + 0.8f, this.getPosZ() + (double)f3, vector3d.x, vector3d.y, vector3d.z);
                    }
                }
            }
        }
    }

    private void func_242326_eZ() {
        this.isShaking = false;
        this.timeWolfIsShaking = 0.0f;
        this.prevTimeWolfIsShaking = 0.0f;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        this.isWet = false;
        this.isShaking = false;
        this.prevTimeWolfIsShaking = 0.0f;
        this.timeWolfIsShaking = 0.0f;
        super.onDeath(damageSource);
    }

    public boolean isWolfWet() {
        return this.isWet;
    }

    public float getShadingWhileWet(float f) {
        return Math.min(0.5f + MathHelper.lerp(f, this.prevTimeWolfIsShaking, this.timeWolfIsShaking) / 2.0f * 0.5f, 1.0f);
    }

    public float getShakeAngle(float f, float f2) {
        float f3 = (MathHelper.lerp(f, this.prevTimeWolfIsShaking, this.timeWolfIsShaking) + f2) / 1.8f;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        } else if (f3 > 1.0f) {
            f3 = 1.0f;
        }
        return MathHelper.sin(f3 * (float)Math.PI) * MathHelper.sin(f3 * (float)Math.PI * 11.0f) * 0.15f * (float)Math.PI;
    }

    public float getInterestedAngle(float f) {
        return MathHelper.lerp(f, this.headRotationCourseOld, this.headRotationCourse) * 0.15f * (float)Math.PI;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return entitySize.height * 0.8f;
    }

    @Override
    public int getVerticalFaceSpeed() {
        return this.isSleeping() ? 20 : super.getVerticalFaceSpeed();
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return true;
        }
        Entity entity2 = damageSource.getTrueSource();
        this.func_233687_w_(true);
        if (entity2 != null && !(entity2 instanceof PlayerEntity) && !(entity2 instanceof AbstractArrowEntity)) {
            f = (f + 1.0f) / 2.0f;
        }
        return super.attackEntityFrom(damageSource, f);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity2) {
        boolean bl = entity2.attackEntityFrom(DamageSource.causeMobDamage(this), (int)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
        if (bl) {
            this.applyEnchantments(this, entity2);
        }
        return bl;
    }

    @Override
    public void setTamed(boolean bl) {
        super.setTamed(bl);
        if (bl) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0);
            this.setHealth(20.0f);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0);
        }
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0);
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        Item item = itemStack.getItem();
        if (this.world.isRemote) {
            boolean bl = this.isOwner(playerEntity) || this.isTamed() || item == Items.BONE && !this.isTamed() && !this.func_233678_J__();
            return bl ? ActionResultType.CONSUME : ActionResultType.PASS;
        }
        if (this.isTamed()) {
            if (this.isBreedingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                if (!playerEntity.abilities.isCreativeMode) {
                    itemStack.shrink(1);
                }
                this.heal(item.getFood().getHealing());
                return ActionResultType.SUCCESS;
            }
            if (!(item instanceof DyeItem)) {
                ActionResultType actionResultType = super.func_230254_b_(playerEntity, hand);
                if ((!actionResultType.isSuccessOrConsume() || this.isChild()) && this.isOwner(playerEntity)) {
                    this.func_233687_w_(!this.isSitting());
                    this.isJumping = false;
                    this.navigator.clearPath();
                    this.setAttackTarget(null);
                    return ActionResultType.SUCCESS;
                }
                return actionResultType;
            }
            DyeColor dyeColor = ((DyeItem)item).getDyeColor();
            if (dyeColor != this.getCollarColor()) {
                this.setCollarColor(dyeColor);
                if (!playerEntity.abilities.isCreativeMode) {
                    itemStack.shrink(1);
                }
                return ActionResultType.SUCCESS;
            }
        } else if (item == Items.BONE && !this.func_233678_J__()) {
            if (!playerEntity.abilities.isCreativeMode) {
                itemStack.shrink(1);
            }
            if (this.rand.nextInt(3) == 0) {
                this.setTamedBy(playerEntity);
                this.navigator.clearPath();
                this.setAttackTarget(null);
                this.func_233687_w_(false);
                this.world.setEntityState(this, (byte)7);
            } else {
                this.world.setEntityState(this, (byte)6);
            }
            return ActionResultType.SUCCESS;
        }
        return super.func_230254_b_(playerEntity, hand);
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 8) {
            this.isShaking = true;
            this.timeWolfIsShaking = 0.0f;
            this.prevTimeWolfIsShaking = 0.0f;
        } else if (by == 56) {
            this.func_242326_eZ();
        } else {
            super.handleStatusUpdate(by);
        }
    }

    public float getTailRotation() {
        if (this.func_233678_J__()) {
            return 1.5393804f;
        }
        return this.isTamed() ? (0.55f - (this.getMaxHealth() - this.getHealth()) * 0.02f) * (float)Math.PI : 0.62831855f;
    }

    @Override
    public boolean isBreedingItem(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return item.isFood() && item.getFood().isMeat();
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    public int getAngerTime() {
        return this.dataManager.get(field_234232_bz_);
    }

    @Override
    public void setAngerTime(int n) {
        this.dataManager.set(field_234232_bz_, n);
    }

    @Override
    public void func_230258_H__() {
        this.setAngerTime(field_234230_bG_.getRandomWithinRange(this.rand));
    }

    @Override
    @Nullable
    public UUID getAngerTarget() {
        return this.field_234231_bH_;
    }

    @Override
    public void setAngerTarget(@Nullable UUID uUID) {
        this.field_234231_bH_ = uUID;
    }

    public DyeColor getCollarColor() {
        return DyeColor.byId(this.dataManager.get(COLLAR_COLOR));
    }

    public void setCollarColor(DyeColor dyeColor) {
        this.dataManager.set(COLLAR_COLOR, dyeColor.getId());
    }

    @Override
    public WolfEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        WolfEntity wolfEntity = EntityType.WOLF.create(serverWorld);
        UUID uUID = this.getOwnerId();
        if (uUID != null) {
            wolfEntity.setOwnerId(uUID);
            wolfEntity.setTamed(false);
        }
        return wolfEntity;
    }

    public void setBegging(boolean bl) {
        this.dataManager.set(BEGGING, bl);
    }

    @Override
    public boolean canMateWith(AnimalEntity animalEntity) {
        if (animalEntity == this) {
            return true;
        }
        if (!this.isTamed()) {
            return true;
        }
        if (!(animalEntity instanceof WolfEntity)) {
            return true;
        }
        WolfEntity wolfEntity = (WolfEntity)animalEntity;
        if (!wolfEntity.isTamed()) {
            return true;
        }
        if (wolfEntity.isSleeping()) {
            return true;
        }
        return this.isInLove() && wolfEntity.isInLove();
    }

    public boolean isBegging() {
        return this.dataManager.get(BEGGING);
    }

    @Override
    public boolean shouldAttackEntity(LivingEntity livingEntity, LivingEntity livingEntity2) {
        if (!(livingEntity instanceof CreeperEntity) && !(livingEntity instanceof GhastEntity)) {
            if (livingEntity instanceof WolfEntity) {
                WolfEntity wolfEntity = (WolfEntity)livingEntity;
                return !wolfEntity.isTamed() || wolfEntity.getOwner() != livingEntity2;
            }
            if (livingEntity instanceof PlayerEntity && livingEntity2 instanceof PlayerEntity && !((PlayerEntity)livingEntity2).canAttackPlayer((PlayerEntity)livingEntity)) {
                return true;
            }
            if (livingEntity instanceof AbstractHorseEntity && ((AbstractHorseEntity)livingEntity).isTame()) {
                return true;
            }
            return !(livingEntity instanceof TameableEntity) || !((TameableEntity)livingEntity).isTamed();
        }
        return true;
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity playerEntity) {
        return !this.func_233678_J__() && super.canBeLeashedTo(playerEntity);
    }

    @Override
    public Vector3d func_241205_ce_() {
        return new Vector3d(0.0, 0.6f * this.getEyeHeight(), this.getWidth() * 0.4f);
    }

    @Override
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return this.func_241840_a(serverWorld, ageableEntity);
    }

    private static boolean lambda$static$0(LivingEntity livingEntity) {
        EntityType<?> entityType = livingEntity.getType();
        return entityType == EntityType.SHEEP || entityType == EntityType.RABBIT || entityType == EntityType.FOX;
    }

    static Random access$000(WolfEntity wolfEntity) {
        return wolfEntity.rand;
    }

    class AvoidEntityGoal<T extends LivingEntity>
    extends net.minecraft.entity.ai.goal.AvoidEntityGoal<T> {
        private final WolfEntity wolf;
        final WolfEntity this$0;

        public AvoidEntityGoal(WolfEntity wolfEntity, WolfEntity wolfEntity2, Class<T> clazz, float f, double d, double d2) {
            this.this$0 = wolfEntity;
            super(wolfEntity2, clazz, f, d, d2);
            this.wolf = wolfEntity2;
        }

        @Override
        public boolean shouldExecute() {
            if (super.shouldExecute() && this.avoidTarget instanceof LlamaEntity) {
                return !this.wolf.isTamed() && this.avoidLlama((LlamaEntity)this.avoidTarget);
            }
            return true;
        }

        private boolean avoidLlama(LlamaEntity llamaEntity) {
            return llamaEntity.getStrength() >= WolfEntity.access$000(this.this$0).nextInt(5);
        }

        @Override
        public void startExecuting() {
            this.this$0.setAttackTarget(null);
            super.startExecuting();
        }

        @Override
        public void tick() {
            this.this$0.setAttackTarget(null);
            super.tick();
        }
    }
}

