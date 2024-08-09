/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.ResetAngerGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ZombifiedPiglinEntity
extends ZombieEntity
implements IAngerable {
    private static final UUID field_234344_b_ = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
    private static final AttributeModifier field_234349_c_ = new AttributeModifier(field_234344_b_, "Attacking speed boost", 0.05, AttributeModifier.Operation.ADDITION);
    private static final RangedInteger field_234350_d_ = TickRangeConverter.convertRange(0, 1);
    private int field_234345_bu_;
    private static final RangedInteger field_234346_bv_ = TickRangeConverter.convertRange(20, 39);
    private int field_234347_bw_;
    private UUID field_234348_bx_;
    private static final RangedInteger field_241403_bz_ = TickRangeConverter.convertRange(4, 6);
    private int field_241401_bA_;

    public ZombifiedPiglinEntity(EntityType<? extends ZombifiedPiglinEntity> entityType, World world) {
        super((EntityType<? extends ZombieEntity>)entityType, world);
        this.setPathPriority(PathNodeType.LAVA, 8.0f);
    }

    @Override
    public void setAngerTarget(@Nullable UUID uUID) {
        this.field_234348_bx_ = uUID;
    }

    @Override
    public double getYOffset() {
        return this.isChild() ? -0.05 : -0.45;
    }

    @Override
    protected void applyEntityAI() {
        this.goalSelector.addGoal(2, new ZombieAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]).setCallsForHelp(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, 10, true, false, this::func_233680_b_));
        this.targetSelector.addGoal(3, new ResetAngerGoal<ZombifiedPiglinEntity>(this, true));
    }

    public static AttributeModifierMap.MutableAttribute func_234352_eU_() {
        return ZombieEntity.func_234342_eQ_().createMutableAttribute(Attributes.ZOMBIE_SPAWN_REINFORCEMENTS, 0.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.23f).createMutableAttribute(Attributes.ATTACK_DAMAGE, 5.0);
    }

    @Override
    protected boolean shouldDrown() {
        return true;
    }

    @Override
    protected void updateAITasks() {
        ModifiableAttributeInstance modifiableAttributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (this.func_233678_J__()) {
            if (!this.isChild() && !modifiableAttributeInstance.hasModifier(field_234349_c_)) {
                modifiableAttributeInstance.applyNonPersistentModifier(field_234349_c_);
            }
            this.func_241409_eY_();
        } else if (modifiableAttributeInstance.hasModifier(field_234349_c_)) {
            modifiableAttributeInstance.removeModifier(field_234349_c_);
        }
        this.func_241359_a_((ServerWorld)this.world, false);
        if (this.getAttackTarget() != null) {
            this.func_241410_eZ_();
        }
        if (this.func_233678_J__()) {
            this.recentlyHit = this.ticksExisted;
        }
        super.updateAITasks();
    }

    private void func_241409_eY_() {
        if (this.field_234345_bu_ > 0) {
            --this.field_234345_bu_;
            if (this.field_234345_bu_ == 0) {
                this.func_234353_eV_();
            }
        }
    }

    private void func_241410_eZ_() {
        if (this.field_241401_bA_ > 0) {
            --this.field_241401_bA_;
        } else {
            if (this.getEntitySenses().canSee(this.getAttackTarget())) {
                this.func_241411_fa_();
            }
            this.field_241401_bA_ = field_241403_bz_.getRandomWithinRange(this.rand);
        }
    }

    private void func_241411_fa_() {
        double d = this.getAttributeValue(Attributes.FOLLOW_RANGE);
        AxisAlignedBB axisAlignedBB = AxisAlignedBB.fromVector(this.getPositionVec()).grow(d, 10.0, d);
        this.world.getLoadedEntitiesWithinAABB(ZombifiedPiglinEntity.class, axisAlignedBB).stream().filter(this::lambda$func_241411_fa_$0).filter(ZombifiedPiglinEntity::lambda$func_241411_fa_$1).filter(this::lambda$func_241411_fa_$2).forEach(this::lambda$func_241411_fa_$3);
    }

    private void func_234353_eV_() {
        this.playSound(SoundEvents.ENTITY_ZOMBIFIED_PIGLIN_ANGRY, this.getSoundVolume() * 2.0f, this.getSoundPitch() * 1.8f);
    }

    @Override
    public void setAttackTarget(@Nullable LivingEntity livingEntity) {
        if (this.getAttackTarget() == null && livingEntity != null) {
            this.field_234345_bu_ = field_234350_d_.getRandomWithinRange(this.rand);
            this.field_241401_bA_ = field_241403_bz_.getRandomWithinRange(this.rand);
        }
        if (livingEntity instanceof PlayerEntity) {
            this.func_230246_e_((PlayerEntity)livingEntity);
        }
        super.setAttackTarget(livingEntity);
    }

    @Override
    public void func_230258_H__() {
        this.setAngerTime(field_234346_bv_.getRandomWithinRange(this.rand));
    }

    public static boolean func_234351_b_(EntityType<ZombifiedPiglinEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        return iWorld.getDifficulty() != Difficulty.PEACEFUL && iWorld.getBlockState(blockPos.down()).getBlock() != Blocks.NETHER_WART_BLOCK;
    }

    @Override
    public boolean isNotColliding(IWorldReader iWorldReader) {
        return iWorldReader.checkNoEntityCollision(this) && !iWorldReader.containsAnyLiquid(this.getBoundingBox());
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        this.writeAngerNBT(compoundNBT);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.readAngerNBT((ServerWorld)this.world, compoundNBT);
    }

    @Override
    public void setAngerTime(int n) {
        this.field_234347_bw_ = n;
    }

    @Override
    public int getAngerTime() {
        return this.field_234347_bw_;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        return this.isInvulnerableTo(damageSource) ? false : super.attackEntityFrom(damageSource, f);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.func_233678_J__() ? SoundEvents.ENTITY_ZOMBIFIED_PIGLIN_ANGRY : SoundEvents.ENTITY_ZOMBIFIED_PIGLIN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_ZOMBIFIED_PIGLIN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ZOMBIFIED_PIGLIN_DEATH;
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficultyInstance) {
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
    }

    @Override
    protected ItemStack getSkullDrop() {
        return ItemStack.EMPTY;
    }

    @Override
    protected void func_230291_eT_() {
        this.getAttribute(Attributes.ZOMBIE_SPAWN_REINFORCEMENTS).setBaseValue(0.0);
    }

    @Override
    public UUID getAngerTarget() {
        return this.field_234348_bx_;
    }

    @Override
    public boolean func_230292_f_(PlayerEntity playerEntity) {
        return this.func_233680_b_(playerEntity);
    }

    private void lambda$func_241411_fa_$3(ZombifiedPiglinEntity zombifiedPiglinEntity) {
        zombifiedPiglinEntity.setAttackTarget(this.getAttackTarget());
    }

    private boolean lambda$func_241411_fa_$2(ZombifiedPiglinEntity zombifiedPiglinEntity) {
        return !zombifiedPiglinEntity.isOnSameTeam(this.getAttackTarget());
    }

    private static boolean lambda$func_241411_fa_$1(ZombifiedPiglinEntity zombifiedPiglinEntity) {
        return zombifiedPiglinEntity.getAttackTarget() == null;
    }

    private boolean lambda$func_241411_fa_$0(ZombifiedPiglinEntity zombifiedPiglinEntity) {
        return zombifiedPiglinEntity != this;
    }
}

