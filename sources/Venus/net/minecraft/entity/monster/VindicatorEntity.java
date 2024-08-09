/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import com.google.common.collect.Maps;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.GroundPathHelper;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerWorld;

public class VindicatorEntity
extends AbstractIllagerEntity {
    private static final Predicate<Difficulty> field_213681_b = VindicatorEntity::lambda$static$0;
    private boolean johnny;

    public VindicatorEntity(EntityType<? extends VindicatorEntity> entityType, World world) {
        super((EntityType<? extends AbstractIllagerEntity>)entityType, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new BreakDoorGoal(this));
        this.goalSelector.addGoal(2, new AbstractIllagerEntity.RaidOpenDoorGoal(this, this));
        this.goalSelector.addGoal(3, new AbstractRaiderEntity.FindTargetGoal(this, this, 10.0f));
        this.goalSelector.addGoal(4, new AttackGoal(this, this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, AbstractRaiderEntity.class).setCallsForHelp(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<PlayerEntity>((MobEntity)this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<AbstractVillagerEntity>((MobEntity)this, AbstractVillagerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolemEntity>((MobEntity)this, IronGolemEntity.class, true));
        this.targetSelector.addGoal(4, new JohnnyAttackGoal(this));
        this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0f));
    }

    @Override
    protected void updateAITasks() {
        if (!this.isAIDisabled() && GroundPathHelper.isGroundNavigator(this)) {
            boolean bl = ((ServerWorld)this.world).hasRaid(this.getPosition());
            ((GroundPathNavigator)this.getNavigator()).setBreakDoors(bl);
        }
        super.updateAITasks();
    }

    public static AttributeModifierMap.MutableAttribute func_234322_eI_() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35f).createMutableAttribute(Attributes.FOLLOW_RANGE, 12.0).createMutableAttribute(Attributes.MAX_HEALTH, 24.0).createMutableAttribute(Attributes.ATTACK_DAMAGE, 5.0);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        if (this.johnny) {
            compoundNBT.putBoolean("Johnny", false);
        }
    }

    @Override
    public AbstractIllagerEntity.ArmPose getArmPose() {
        if (this.isAggressive()) {
            return AbstractIllagerEntity.ArmPose.ATTACKING;
        }
        return this.getCelebrating() ? AbstractIllagerEntity.ArmPose.CELEBRATING : AbstractIllagerEntity.ArmPose.CROSSED;
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("Johnny", 0)) {
            this.johnny = compoundNBT.getBoolean("Johnny");
        }
    }

    @Override
    public SoundEvent getRaidLossSound() {
        return SoundEvents.ENTITY_VINDICATOR_CELEBRATE;
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        ILivingEntityData iLivingEntityData2 = super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
        ((GroundPathNavigator)this.getNavigator()).setBreakDoors(false);
        this.setEquipmentBasedOnDifficulty(difficultyInstance);
        this.setEnchantmentBasedOnDifficulty(difficultyInstance);
        return iLivingEntityData2;
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficultyInstance) {
        if (this.getRaid() == null) {
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_AXE));
        }
    }

    @Override
    public boolean isOnSameTeam(Entity entity2) {
        if (super.isOnSameTeam(entity2)) {
            return false;
        }
        if (entity2 instanceof LivingEntity && ((LivingEntity)entity2).getCreatureAttribute() == CreatureAttribute.ILLAGER) {
            return this.getTeam() == null && entity2.getTeam() == null;
        }
        return true;
    }

    @Override
    public void setCustomName(@Nullable ITextComponent iTextComponent) {
        super.setCustomName(iTextComponent);
        if (!this.johnny && iTextComponent != null && iTextComponent.getString().equals("Johnny")) {
            this.johnny = true;
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_VINDICATOR_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VINDICATOR_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_VINDICATOR_HURT;
    }

    @Override
    public void applyWaveBonus(int n, boolean bl) {
        boolean bl2;
        ItemStack itemStack = new ItemStack(Items.IRON_AXE);
        Raid raid = this.getRaid();
        int n2 = 1;
        if (n > raid.getWaves(Difficulty.NORMAL)) {
            n2 = 2;
        }
        boolean bl3 = bl2 = this.rand.nextFloat() <= raid.getEnchantOdds();
        if (bl2) {
            HashMap<Enchantment, Integer> hashMap = Maps.newHashMap();
            hashMap.put(Enchantments.SHARPNESS, n2);
            EnchantmentHelper.setEnchantments(hashMap, itemStack);
        }
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemStack);
    }

    private static boolean lambda$static$0(Difficulty difficulty) {
        return difficulty == Difficulty.NORMAL || difficulty == Difficulty.HARD;
    }

    static Random access$000(VindicatorEntity vindicatorEntity) {
        return vindicatorEntity.rand;
    }

    static class BreakDoorGoal
    extends net.minecraft.entity.ai.goal.BreakDoorGoal {
        public BreakDoorGoal(MobEntity mobEntity) {
            super(mobEntity, 6, field_213681_b);
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldContinueExecuting() {
            VindicatorEntity vindicatorEntity = (VindicatorEntity)this.entity;
            return vindicatorEntity.isRaidActive() && super.shouldContinueExecuting();
        }

        @Override
        public boolean shouldExecute() {
            VindicatorEntity vindicatorEntity = (VindicatorEntity)this.entity;
            return vindicatorEntity.isRaidActive() && VindicatorEntity.access$000(vindicatorEntity).nextInt(10) == 0 && super.shouldExecute();
        }

        @Override
        public void startExecuting() {
            super.startExecuting();
            this.entity.setIdleTime(0);
        }
    }

    class AttackGoal
    extends MeleeAttackGoal {
        final VindicatorEntity this$0;

        public AttackGoal(VindicatorEntity vindicatorEntity, VindicatorEntity vindicatorEntity2) {
            this.this$0 = vindicatorEntity;
            super(vindicatorEntity2, 1.0, false);
        }

        @Override
        protected double getAttackReachSqr(LivingEntity livingEntity) {
            if (this.attacker.getRidingEntity() instanceof RavagerEntity) {
                float f = this.attacker.getRidingEntity().getWidth() - 0.1f;
                return f * 2.0f * f * 2.0f + livingEntity.getWidth();
            }
            return super.getAttackReachSqr(livingEntity);
        }
    }

    static class JohnnyAttackGoal
    extends NearestAttackableTargetGoal<LivingEntity> {
        public JohnnyAttackGoal(VindicatorEntity vindicatorEntity) {
            super(vindicatorEntity, LivingEntity.class, 0, true, true, LivingEntity::attackable);
        }

        @Override
        public boolean shouldExecute() {
            return ((VindicatorEntity)this.goalOwner).johnny && super.shouldExecute();
        }

        @Override
        public void startExecuting() {
            super.startExecuting();
            this.goalOwner.setIdleTime(0);
        }
    }
}

