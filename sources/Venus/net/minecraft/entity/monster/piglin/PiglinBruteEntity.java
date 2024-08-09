/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster.piglin;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
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
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinAction;
import net.minecraft.entity.monster.piglin.PiglinBruteBrain;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PiglinBruteEntity
extends AbstractPiglinEntity {
    protected static final ImmutableList<SensorType<? extends Sensor<? super PiglinBruteEntity>>> field_242343_d = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.NEAREST_ITEMS, SensorType.HURT_BY, SensorType.PIGLIN_BRUTE_SPECIFIC_SENSOR);
    protected static final ImmutableList<MemoryModuleType<?>> field_242342_bo = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.OPENED_DOORS, MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, MemoryModuleType.NEAREST_ADULT_PIGLINS, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.PATH, MemoryModuleType.ANGRY_AT, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.HOME);

    public PiglinBruteEntity(EntityType<? extends PiglinBruteEntity> entityType, World world) {
        super((EntityType<? extends AbstractPiglinEntity>)entityType, world);
        this.experienceValue = 20;
    }

    public static AttributeModifierMap.MutableAttribute func_242344_eS() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 50.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35f).createMutableAttribute(Attributes.ATTACK_DAMAGE, 7.0);
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        PiglinBruteBrain.func_242352_a(this);
        this.setEquipmentBasedOnDifficulty(difficultyInstance);
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficultyInstance) {
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.GOLDEN_AXE));
    }

    protected Brain.BrainCodec<PiglinBruteEntity> getBrainCodec() {
        return Brain.createCodec(field_242342_bo, field_242343_d);
    }

    @Override
    protected Brain<?> createBrain(Dynamic<?> dynamic) {
        return PiglinBruteBrain.func_242354_a(this, this.getBrainCodec().deserialize(dynamic));
    }

    public Brain<PiglinBruteEntity> getBrain() {
        return super.getBrain();
    }

    @Override
    public boolean func_234422_eK_() {
        return true;
    }

    @Override
    public boolean func_230293_i_(ItemStack itemStack) {
        return itemStack.getItem() == Items.GOLDEN_AXE ? super.func_230293_i_(itemStack) : false;
    }

    @Override
    protected void updateAITasks() {
        this.world.getProfiler().startSection("piglinBruteBrain");
        this.getBrain().tick((ServerWorld)this.world, this);
        this.world.getProfiler().endSection();
        PiglinBruteBrain.func_242358_b(this);
        PiglinBruteBrain.func_242360_c(this);
        super.updateAITasks();
    }

    @Override
    public PiglinAction func_234424_eM_() {
        return this.isAggressive() && this.func_242338_eO() ? PiglinAction.ATTACKING_WITH_MELEE_WEAPON : PiglinAction.DEFAULT;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        boolean bl = super.attackEntityFrom(damageSource, f);
        if (this.world.isRemote) {
            return true;
        }
        if (bl && damageSource.getTrueSource() instanceof LivingEntity) {
            PiglinBruteBrain.func_242353_a(this, (LivingEntity)damageSource.getTrueSource());
        }
        return bl;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PIGLIN_BRUTE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_PIGLIN_BRUTE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIGLIN_BRUTE_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.ENTITY_PIGLIN_BRUTE_STEP, 0.15f, 1.0f);
    }

    protected void func_242345_eT() {
        this.playSound(SoundEvents.ENTITY_PIGLIN_BRUTE_ANGRY, 1.0f, this.getSoundPitch());
    }

    @Override
    protected void func_241848_eP() {
        this.playSound(SoundEvents.ENTITY_PIGLIN_BRUTE_CONVRTED_TO_ZOMBIFIED, 1.0f, this.getSoundPitch());
    }
}

