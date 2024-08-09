/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import com.google.common.collect.ImmutableList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.DefendVillageTargetGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.PatrolVillageGoal;
import net.minecraft.entity.ai.goal.ResetAngerGoal;
import net.minecraft.entity.ai.goal.ReturnToVillageGoal;
import net.minecraft.entity.ai.goal.ShowVillagerFlowerGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BlockParticleData;
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
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;

public class IronGolemEntity
extends GolemEntity
implements IAngerable {
    protected static final DataParameter<Byte> PLAYER_CREATED = EntityDataManager.createKey(IronGolemEntity.class, DataSerializers.BYTE);
    private int attackTimer;
    private int holdRoseTick;
    private static final RangedInteger field_234196_bu_ = TickRangeConverter.convertRange(20, 39);
    private int field_234197_bv_;
    private UUID field_234198_bw_;

    public IronGolemEntity(EntityType<? extends IronGolemEntity> entityType, World world) {
        super((EntityType<? extends GolemEntity>)entityType, world);
        this.stepHeight = 1.0f;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9, 32.0f));
        this.goalSelector.addGoal(2, new ReturnToVillageGoal((CreatureEntity)this, 0.6, false));
        this.goalSelector.addGoal(4, new PatrolVillageGoal(this, 0.6));
        this.goalSelector.addGoal(5, new ShowVillagerFlowerGoal(this));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new DefendVillageTargetGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, 10, true, false, this::func_233680_b_));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<MobEntity>(this, MobEntity.class, 5, false, false, IronGolemEntity::lambda$registerGoals$0));
        this.targetSelector.addGoal(4, new ResetAngerGoal<IronGolemEntity>(this, false));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(PLAYER_CREATED, (byte)0);
    }

    public static AttributeModifierMap.MutableAttribute func_234200_m_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 100.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25).createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1.0).createMutableAttribute(Attributes.ATTACK_DAMAGE, 15.0);
    }

    @Override
    protected int decreaseAirSupply(int n) {
        return n;
    }

    @Override
    protected void collideWithEntity(Entity entity2) {
        if (entity2 instanceof IMob && !(entity2 instanceof CreeperEntity) && this.getRNG().nextInt(20) == 0) {
            this.setAttackTarget((LivingEntity)entity2);
        }
        super.collideWithEntity(entity2);
    }

    @Override
    public void livingTick() {
        int n;
        int n2;
        int n3;
        BlockState blockState;
        super.livingTick();
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
        if (this.holdRoseTick > 0) {
            --this.holdRoseTick;
        }
        if (IronGolemEntity.horizontalMag(this.getMotion()) > 2.500000277905201E-7 && this.rand.nextInt(5) == 0 && !(blockState = this.world.getBlockState(new BlockPos(n3 = MathHelper.floor(this.getPosX()), n2 = MathHelper.floor(this.getPosY() - (double)0.2f), n = MathHelper.floor(this.getPosZ())))).isAir()) {
            this.world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, blockState), this.getPosX() + ((double)this.rand.nextFloat() - 0.5) * (double)this.getWidth(), this.getPosY() + 0.1, this.getPosZ() + ((double)this.rand.nextFloat() - 0.5) * (double)this.getWidth(), 4.0 * ((double)this.rand.nextFloat() - 0.5), 0.5, ((double)this.rand.nextFloat() - 0.5) * 4.0);
        }
        if (!this.world.isRemote) {
            this.func_241359_a_((ServerWorld)this.world, false);
        }
    }

    @Override
    public boolean canAttack(EntityType<?> entityType) {
        if (this.isPlayerCreated() && entityType == EntityType.PLAYER) {
            return true;
        }
        return entityType == EntityType.CREEPER ? false : super.canAttack(entityType);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putBoolean("PlayerCreated", this.isPlayerCreated());
        this.writeAngerNBT(compoundNBT);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.setPlayerCreated(compoundNBT.getBoolean("PlayerCreated"));
        this.readAngerNBT((ServerWorld)this.world, compoundNBT);
    }

    @Override
    public void func_230258_H__() {
        this.setAngerTime(field_234196_bu_.getRandomWithinRange(this.rand));
    }

    @Override
    public void setAngerTime(int n) {
        this.field_234197_bv_ = n;
    }

    @Override
    public int getAngerTime() {
        return this.field_234197_bv_;
    }

    @Override
    public void setAngerTarget(@Nullable UUID uUID) {
        this.field_234198_bw_ = uUID;
    }

    @Override
    public UUID getAngerTarget() {
        return this.field_234198_bw_;
    }

    private float func_226511_et_() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity2) {
        this.attackTimer = 10;
        this.world.setEntityState(this, (byte)4);
        float f = this.func_226511_et_();
        float f2 = (int)f > 0 ? f / 2.0f + (float)this.rand.nextInt((int)f) : f;
        boolean bl = entity2.attackEntityFrom(DamageSource.causeMobDamage(this), f2);
        if (bl) {
            entity2.setMotion(entity2.getMotion().add(0.0, 0.4f, 0.0));
            this.applyEnchantments(this, entity2);
        }
        this.playSound(SoundEvents.ENTITY_IRON_GOLEM_ATTACK, 1.0f, 1.0f);
        return bl;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        Cracks cracks = this.func_226512_l_();
        boolean bl = super.attackEntityFrom(damageSource, f);
        if (bl && this.func_226512_l_() != cracks) {
            this.playSound(SoundEvents.ENTITY_IRON_GOLEM_DAMAGE, 1.0f, 1.0f);
        }
        return bl;
    }

    public Cracks func_226512_l_() {
        return Cracks.func_226515_a_(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 4) {
            this.attackTimer = 10;
            this.playSound(SoundEvents.ENTITY_IRON_GOLEM_ATTACK, 1.0f, 1.0f);
        } else if (by == 11) {
            this.holdRoseTick = 400;
        } else if (by == 34) {
            this.holdRoseTick = 0;
        } else {
            super.handleStatusUpdate(by);
        }
    }

    public int getAttackTimer() {
        return this.attackTimer;
    }

    public void setHoldingRose(boolean bl) {
        if (bl) {
            this.holdRoseTick = 400;
            this.world.setEntityState(this, (byte)11);
        } else {
            this.holdRoseTick = 0;
            this.world.setEntityState(this, (byte)34);
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_IRON_GOLEM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_IRON_GOLEM_DEATH;
    }

    @Override
    protected ActionResultType func_230254_b_(PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        Item item = itemStack.getItem();
        if (item != Items.IRON_INGOT) {
            return ActionResultType.PASS;
        }
        float f = this.getHealth();
        this.heal(25.0f);
        if (this.getHealth() == f) {
            return ActionResultType.PASS;
        }
        float f2 = 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
        this.playSound(SoundEvents.ENTITY_IRON_GOLEM_REPAIR, 1.0f, f2);
        if (!playerEntity.abilities.isCreativeMode) {
            itemStack.shrink(1);
        }
        return ActionResultType.func_233537_a_(this.world.isRemote);
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.ENTITY_IRON_GOLEM_STEP, 1.0f, 1.0f);
    }

    public int getHoldRoseTick() {
        return this.holdRoseTick;
    }

    public boolean isPlayerCreated() {
        return (this.dataManager.get(PLAYER_CREATED) & 1) != 0;
    }

    public void setPlayerCreated(boolean bl) {
        byte by = this.dataManager.get(PLAYER_CREATED);
        if (bl) {
            this.dataManager.set(PLAYER_CREATED, (byte)(by | 1));
        } else {
            this.dataManager.set(PLAYER_CREATED, (byte)(by & 0xFFFFFFFE));
        }
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
    }

    @Override
    public boolean isNotColliding(IWorldReader iWorldReader) {
        BlockPos blockPos = this.getPosition();
        BlockPos blockPos2 = blockPos.down();
        BlockState blockState = iWorldReader.getBlockState(blockPos2);
        if (!blockState.canSpawnMobs(iWorldReader, blockPos2, this)) {
            return true;
        }
        for (int i = 1; i < 3; ++i) {
            BlockState blockState2;
            BlockPos blockPos3 = blockPos.up(i);
            if (WorldEntitySpawner.func_234968_a_(iWorldReader, blockPos3, blockState2 = iWorldReader.getBlockState(blockPos3), blockState2.getFluidState(), EntityType.IRON_GOLEM)) continue;
            return true;
        }
        return WorldEntitySpawner.func_234968_a_(iWorldReader, blockPos, iWorldReader.getBlockState(blockPos), Fluids.EMPTY.getDefaultState(), EntityType.IRON_GOLEM) && iWorldReader.checkNoEntityCollision(this);
    }

    @Override
    public Vector3d func_241205_ce_() {
        return new Vector3d(0.0, 0.875f * this.getEyeHeight(), this.getWidth() * 0.4f);
    }

    private static boolean lambda$registerGoals$0(LivingEntity livingEntity) {
        return livingEntity instanceof IMob && !(livingEntity instanceof CreeperEntity);
    }

    public static enum Cracks {
        NONE(1.0f),
        LOW(0.75f),
        MEDIUM(0.5f),
        HIGH(0.25f);

        private static final List<Cracks> field_226513_e_;
        private final float field_226514_f_;

        private Cracks(float f) {
            this.field_226514_f_ = f;
        }

        public static Cracks func_226515_a_(float f) {
            for (Cracks cracks : field_226513_e_) {
                if (!(f < cracks.field_226514_f_)) continue;
                return cracks;
            }
            return NONE;
        }

        private static double lambda$static$0(Cracks cracks) {
            return cracks.field_226514_f_;
        }

        static {
            field_226513_e_ = Stream.of(Cracks.values()).sorted(Comparator.comparingDouble(Cracks::lambda$static$0)).collect(ImmutableList.toImmutableList());
        }
    }
}

