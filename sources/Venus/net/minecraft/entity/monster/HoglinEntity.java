/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.monster.HoglinTasks;
import net.minecraft.entity.monster.IFlinging;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZoglinEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class HoglinEntity
extends AnimalEntity
implements IMob,
IFlinging {
    private static final DataParameter<Boolean> field_234356_bw_ = EntityDataManager.createKey(HoglinEntity.class, DataSerializers.BOOLEAN);
    private int field_234357_bx_;
    private int field_234358_by_ = 0;
    private boolean field_234359_bz_ = false;
    protected static final ImmutableList<? extends SensorType<? extends Sensor<? super HoglinEntity>>> field_234354_bu_ = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.NEAREST_ADULT, SensorType.HOGLIN_SPECIFIC_SENSOR);
    protected static final ImmutableList<? extends MemoryModuleType<?>> field_234355_bv_ = ImmutableList.of(MemoryModuleType.BREED_TARGET, MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLIN, MemoryModuleType.AVOID_TARGET, MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, MemoryModuleType.NEAREST_VISIBLE_ADULT_HOGLINS, MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.NEAREST_REPELLENT, MemoryModuleType.PACIFIED);

    public HoglinEntity(EntityType<? extends HoglinEntity> entityType, World world) {
        super((EntityType<? extends AnimalEntity>)entityType, world);
        this.experienceValue = 5;
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity playerEntity) {
        return !this.getLeashed();
    }

    public static AttributeModifierMap.MutableAttribute func_234362_eI_() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 40.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3f).createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.6f).createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1.0).createMutableAttribute(Attributes.ATTACK_DAMAGE, 6.0);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity2) {
        if (!(entity2 instanceof LivingEntity)) {
            return true;
        }
        this.field_234357_bx_ = 10;
        this.world.setEntityState(this, (byte)4);
        this.playSound(SoundEvents.ENTITY_HOGLIN_ATTACK, 1.0f, this.getSoundPitch());
        HoglinTasks.func_234378_a_(this, (LivingEntity)entity2);
        return IFlinging.func_234403_a_(this, (LivingEntity)entity2);
    }

    @Override
    protected void constructKnockBackVector(LivingEntity livingEntity) {
        if (this.func_234363_eJ_()) {
            IFlinging.func_234404_b_(this, livingEntity);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        boolean bl = super.attackEntityFrom(damageSource, f);
        if (this.world.isRemote) {
            return true;
        }
        if (bl && damageSource.getTrueSource() instanceof LivingEntity) {
            HoglinTasks.func_234384_b_(this, (LivingEntity)damageSource.getTrueSource());
        }
        return bl;
    }

    protected Brain.BrainCodec<HoglinEntity> getBrainCodec() {
        return Brain.createCodec(field_234355_bv_, field_234354_bu_);
    }

    @Override
    protected Brain<?> createBrain(Dynamic<?> dynamic) {
        return HoglinTasks.func_234376_a_(this.getBrainCodec().deserialize(dynamic));
    }

    public Brain<HoglinEntity> getBrain() {
        return super.getBrain();
    }

    @Override
    protected void updateAITasks() {
        this.world.getProfiler().startSection("hoglinBrain");
        this.getBrain().tick((ServerWorld)this.world, this);
        this.world.getProfiler().endSection();
        HoglinTasks.func_234377_a_(this);
        if (this.func_234364_eK_()) {
            ++this.field_234358_by_;
            if (this.field_234358_by_ > 300) {
                this.func_241412_a_(SoundEvents.ENTITY_HOGLIN_CONVERTED_TO_ZOMBIFIED);
                this.func_234360_a_((ServerWorld)this.world);
            }
        } else {
            this.field_234358_by_ = 0;
        }
    }

    @Override
    public void livingTick() {
        if (this.field_234357_bx_ > 0) {
            --this.field_234357_bx_;
        }
        super.livingTick();
    }

    @Override
    protected void onGrowingAdult() {
        if (this.isChild()) {
            this.experienceValue = 3;
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(0.5);
        } else {
            this.experienceValue = 5;
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6.0);
        }
    }

    public static boolean func_234361_c_(EntityType<HoglinEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        return !iWorld.getBlockState(blockPos.down()).isIn(Blocks.NETHER_WART_BLOCK);
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        if (iServerWorld.getRandom().nextFloat() < 0.2f) {
            this.setChild(false);
        }
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    @Override
    public boolean canDespawn(double d) {
        return !this.isNoDespawnRequired();
    }

    @Override
    public float getBlockPathWeight(BlockPos blockPos, IWorldReader iWorldReader) {
        if (HoglinTasks.func_234380_a_(this, blockPos)) {
            return -1.0f;
        }
        return iWorldReader.getBlockState(blockPos.down()).isIn(Blocks.CRIMSON_NYLIUM) ? 10.0f : 0.0f;
    }

    @Override
    public double getMountedYOffset() {
        return (double)this.getHeight() - (this.isChild() ? 0.2 : 0.15);
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity playerEntity, Hand hand) {
        ActionResultType actionResultType = super.func_230254_b_(playerEntity, hand);
        if (actionResultType.isSuccessOrConsume()) {
            this.enablePersistence();
        }
        return actionResultType;
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 4) {
            this.field_234357_bx_ = 10;
            this.playSound(SoundEvents.ENTITY_HOGLIN_ATTACK, 1.0f, this.getSoundPitch());
        } else {
            super.handleStatusUpdate(by);
        }
    }

    @Override
    public int func_230290_eL_() {
        return this.field_234357_bx_;
    }

    @Override
    protected boolean canDropLoot() {
        return false;
    }

    @Override
    protected int getExperiencePoints(PlayerEntity playerEntity) {
        return this.experienceValue;
    }

    private void func_234360_a_(ServerWorld serverWorld) {
        ZoglinEntity zoglinEntity = this.func_233656_b_(EntityType.ZOGLIN, false);
        if (zoglinEntity != null) {
            zoglinEntity.addPotionEffect(new EffectInstance(Effects.NAUSEA, 200, 0));
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack itemStack) {
        return itemStack.getItem() == Items.CRIMSON_FUNGUS;
    }

    public boolean func_234363_eJ_() {
        return !this.isChild();
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(field_234356_bw_, false);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        if (this.func_234368_eV_()) {
            compoundNBT.putBoolean("IsImmuneToZombification", false);
        }
        compoundNBT.putInt("TimeInOverworld", this.field_234358_by_);
        if (this.field_234359_bz_) {
            compoundNBT.putBoolean("CannotBeHunted", false);
        }
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.func_234370_t_(compoundNBT.getBoolean("IsImmuneToZombification"));
        this.field_234358_by_ = compoundNBT.getInt("TimeInOverworld");
        this.func_234371_u_(compoundNBT.getBoolean("CannotBeHunted"));
    }

    public void func_234370_t_(boolean bl) {
        this.getDataManager().set(field_234356_bw_, bl);
    }

    private boolean func_234368_eV_() {
        return this.getDataManager().get(field_234356_bw_);
    }

    public boolean func_234364_eK_() {
        return !this.world.getDimensionType().isPiglinSafe() && !this.func_234368_eV_() && !this.isAIDisabled();
    }

    private void func_234371_u_(boolean bl) {
        this.field_234359_bz_ = bl;
    }

    public boolean func_234365_eM_() {
        return this.func_234363_eJ_() && !this.field_234359_bz_;
    }

    @Override
    @Nullable
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        HoglinEntity hoglinEntity = EntityType.HOGLIN.create(serverWorld);
        if (hoglinEntity != null) {
            hoglinEntity.enablePersistence();
        }
        return hoglinEntity;
    }

    @Override
    public boolean canFallInLove() {
        return !HoglinTasks.func_234386_c_(this) && super.canFallInLove();
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.world.isRemote ? null : HoglinTasks.func_234398_h_(this).orElse(null);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_HOGLIN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_HOGLIN_DEATH;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_HOSTILE_SWIM;
    }

    @Override
    protected SoundEvent getSplashSound() {
        return SoundEvents.ENTITY_HOSTILE_SPLASH;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.ENTITY_HOGLIN_STEP, 0.15f, 1.0f);
    }

    protected void func_241412_a_(SoundEvent soundEvent) {
        this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch());
    }

    @Override
    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPacketSender.sendLivingEntity(this);
    }
}

