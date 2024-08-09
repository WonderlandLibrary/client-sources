/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Optional;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.AttackTargetTask;
import net.minecraft.entity.ai.brain.task.DummyTask;
import net.minecraft.entity.ai.brain.task.FindNewAttackTargetTask;
import net.minecraft.entity.ai.brain.task.FirstShuffledTask;
import net.minecraft.entity.ai.brain.task.ForgetAttackTargetTask;
import net.minecraft.entity.ai.brain.task.LookAtEntityTask;
import net.minecraft.entity.ai.brain.task.LookTask;
import net.minecraft.entity.ai.brain.task.MoveToTargetTask;
import net.minecraft.entity.ai.brain.task.RunSometimesTask;
import net.minecraft.entity.ai.brain.task.SupplementedTask;
import net.minecraft.entity.ai.brain.task.WalkRandomlyTask;
import net.minecraft.entity.ai.brain.task.WalkToTargetTask;
import net.minecraft.entity.ai.brain.task.WalkTowardsLookTargetTask;
import net.minecraft.entity.monster.IFlinging;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ZoglinEntity
extends MonsterEntity
implements IMob,
IFlinging {
    private static final DataParameter<Boolean> field_234327_d_ = EntityDataManager.createKey(ZoglinEntity.class, DataSerializers.BOOLEAN);
    private int field_234325_bu_;
    protected static final ImmutableList<? extends SensorType<? extends Sensor<? super ZoglinEntity>>> field_234324_b_ = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS);
    protected static final ImmutableList<? extends MemoryModuleType<?>> field_234326_c_ = ImmutableList.of(MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN);

    public ZoglinEntity(EntityType<? extends ZoglinEntity> entityType, World world) {
        super((EntityType<? extends MonsterEntity>)entityType, world);
        this.experienceValue = 5;
    }

    protected Brain.BrainCodec<ZoglinEntity> getBrainCodec() {
        return Brain.createCodec(field_234326_c_, field_234324_b_);
    }

    @Override
    protected Brain<?> createBrain(Dynamic<?> dynamic) {
        Brain<ZoglinEntity> brain = this.getBrainCodec().deserialize(dynamic);
        ZoglinEntity.func_234328_a_(brain);
        ZoglinEntity.func_234329_b_(brain);
        ZoglinEntity.func_234330_c_(brain);
        brain.setDefaultActivities(ImmutableSet.of(Activity.CORE));
        brain.setFallbackActivity(Activity.IDLE);
        brain.switchToFallbackActivity();
        return brain;
    }

    private static void func_234328_a_(Brain<ZoglinEntity> brain) {
        brain.registerActivity(Activity.CORE, 0, ImmutableList.of(new LookTask(45, 90), new WalkToTargetTask()));
    }

    private static void func_234329_b_(Brain<ZoglinEntity> brain) {
        brain.registerActivity(Activity.IDLE, 10, ImmutableList.of(new ForgetAttackTargetTask<ZoglinEntity>(ZoglinEntity::func_234335_eQ_), new RunSometimesTask<LivingEntity>(new LookAtEntityTask(8.0f), RangedInteger.createRangedInteger(30, 60)), new FirstShuffledTask(ImmutableList.of(Pair.of(new WalkRandomlyTask(0.4f), 2), Pair.of(new WalkTowardsLookTargetTask(0.4f, 3), 2), Pair.of(new DummyTask(30, 60), 1)))));
    }

    private static void func_234330_c_(Brain<ZoglinEntity> brain) {
        brain.registerActivity(Activity.FIGHT, 10, ImmutableList.of(new MoveToTargetTask(1.0f), new SupplementedTask<MobEntity>(ZoglinEntity::func_234331_eI_, new AttackTargetTask(40)), new SupplementedTask<MobEntity>(ZoglinEntity::isChild, new AttackTargetTask(15)), new FindNewAttackTargetTask()), MemoryModuleType.ATTACK_TARGET);
    }

    private Optional<? extends LivingEntity> func_234335_eQ_() {
        return ((List)this.getBrain().getMemory(MemoryModuleType.VISIBLE_MOBS).orElse(ImmutableList.of())).stream().filter(ZoglinEntity::func_234337_j_).findFirst();
    }

    private static boolean func_234337_j_(LivingEntity livingEntity) {
        EntityType<?> entityType = livingEntity.getType();
        return entityType != EntityType.ZOGLIN && entityType != EntityType.CREEPER && EntityPredicates.CAN_HOSTILE_AI_TARGET.test(livingEntity);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(field_234327_d_, false);
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        super.notifyDataManagerChange(dataParameter);
        if (field_234327_d_.equals(dataParameter)) {
            this.recalculateSize();
        }
    }

    public static AttributeModifierMap.MutableAttribute func_234339_m_() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 40.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3f).createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.6f).createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1.0).createMutableAttribute(Attributes.ATTACK_DAMAGE, 6.0);
    }

    public boolean func_234331_eI_() {
        return !this.isChild();
    }

    @Override
    public boolean attackEntityAsMob(Entity entity2) {
        if (!(entity2 instanceof LivingEntity)) {
            return true;
        }
        this.field_234325_bu_ = 10;
        this.world.setEntityState(this, (byte)4);
        this.playSound(SoundEvents.ENTITY_ZOGLIN_ATTACK, 1.0f, this.getSoundPitch());
        return IFlinging.func_234403_a_(this, (LivingEntity)entity2);
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity playerEntity) {
        return !this.getLeashed();
    }

    @Override
    protected void constructKnockBackVector(LivingEntity livingEntity) {
        if (!this.isChild()) {
            IFlinging.func_234404_b_(this, livingEntity);
        }
    }

    @Override
    public double getMountedYOffset() {
        return (double)this.getHeight() - (this.isChild() ? 0.2 : 0.15);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        boolean bl = super.attackEntityFrom(damageSource, f);
        if (this.world.isRemote) {
            return true;
        }
        if (bl && damageSource.getTrueSource() instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)damageSource.getTrueSource();
            if (EntityPredicates.CAN_HOSTILE_AI_TARGET.test(livingEntity) && !BrainUtil.isTargetWithinDistance(this, livingEntity, 4.0)) {
                this.func_234338_k_(livingEntity);
            }
            return bl;
        }
        return bl;
    }

    private void func_234338_k_(LivingEntity livingEntity) {
        this.brain.removeMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        this.brain.replaceMemory(MemoryModuleType.ATTACK_TARGET, livingEntity, 200L);
    }

    public Brain<ZoglinEntity> getBrain() {
        return super.getBrain();
    }

    protected void func_234332_eJ_() {
        Activity activity = this.brain.getTemporaryActivity().orElse(null);
        this.brain.switchActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        Activity activity2 = this.brain.getTemporaryActivity().orElse(null);
        if (activity2 == Activity.FIGHT && activity != Activity.FIGHT) {
            this.func_234334_eN_();
        }
        this.setAggroed(this.brain.hasMemory(MemoryModuleType.ATTACK_TARGET));
    }

    @Override
    protected void updateAITasks() {
        this.world.getProfiler().startSection("zoglinBrain");
        this.getBrain().tick((ServerWorld)this.world, this);
        this.world.getProfiler().endSection();
        this.func_234332_eJ_();
    }

    @Override
    public void setChild(boolean bl) {
        this.getDataManager().set(field_234327_d_, bl);
        if (!this.world.isRemote && bl) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(0.5);
        }
    }

    @Override
    public boolean isChild() {
        return this.getDataManager().get(field_234327_d_);
    }

    @Override
    public void livingTick() {
        if (this.field_234325_bu_ > 0) {
            --this.field_234325_bu_;
        }
        super.livingTick();
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 4) {
            this.field_234325_bu_ = 10;
            this.playSound(SoundEvents.ENTITY_ZOGLIN_ATTACK, 1.0f, this.getSoundPitch());
        } else {
            super.handleStatusUpdate(by);
        }
    }

    @Override
    public int func_230290_eL_() {
        return this.field_234325_bu_;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.world.isRemote) {
            return null;
        }
        return this.brain.hasMemory(MemoryModuleType.ATTACK_TARGET) ? SoundEvents.ENTITY_ZOGLIN_ANGRY : SoundEvents.ENTITY_ZOGLIN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_ZOGLIN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ZOGLIN_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.ENTITY_ZOGLIN_STEP, 0.15f, 1.0f);
    }

    protected void func_234334_eN_() {
        this.playSound(SoundEvents.ENTITY_ZOGLIN_ANGRY, 1.0f, this.getSoundPitch());
    }

    @Override
    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPacketSender.sendLivingEntity(this);
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        if (this.isChild()) {
            compoundNBT.putBoolean("IsBaby", false);
        }
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.getBoolean("IsBaby")) {
            this.setChild(false);
        }
    }
}

