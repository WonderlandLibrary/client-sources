/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.IShearable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class SnowGolemEntity
extends GolemEntity
implements IShearable,
IRangedAttackMob {
    private static final DataParameter<Byte> PUMPKIN_EQUIPPED = EntityDataManager.createKey(SnowGolemEntity.class, DataSerializers.BYTE);

    public SnowGolemEntity(EntityType<? extends SnowGolemEntity> entityType, World world) {
        super((EntityType<? extends GolemEntity>)entityType, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.25, 20, 10.0f));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal((CreatureEntity)this, 1.0, 1.0000001E-5f));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<MobEntity>(this, MobEntity.class, 10, true, false, SnowGolemEntity::lambda$registerGoals$0));
    }

    public static AttributeModifierMap.MutableAttribute func_234226_m_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 4.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2f);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(PUMPKIN_EQUIPPED, (byte)16);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putBoolean("Pumpkin", this.isPumpkinEquipped());
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("Pumpkin")) {
            this.setPumpkinEquipped(compoundNBT.getBoolean("Pumpkin"));
        }
    }

    @Override
    public boolean isWaterSensitive() {
        return false;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) {
            int n = MathHelper.floor(this.getPosX());
            int n2 = MathHelper.floor(this.getPosY());
            int n3 = MathHelper.floor(this.getPosZ());
            BlockPos blockPos = new BlockPos(n, 0, n3);
            BlockPos blockPos2 = new BlockPos(n, n2, n3);
            if (this.world.getBiome(blockPos).getTemperature(blockPos2) > 1.0f) {
                this.attackEntityFrom(DamageSource.ON_FIRE, 1.0f);
            }
            if (!this.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) {
                return;
            }
            BlockState blockState = Blocks.SNOW.getDefaultState();
            for (int i = 0; i < 4; ++i) {
                n = MathHelper.floor(this.getPosX() + (double)((float)(i % 2 * 2 - 1) * 0.25f));
                BlockPos blockPos3 = new BlockPos(n, n2 = MathHelper.floor(this.getPosY()), n3 = MathHelper.floor(this.getPosZ() + (double)((float)(i / 2 % 2 * 2 - 1) * 0.25f)));
                if (!this.world.getBlockState(blockPos3).isAir() || !(this.world.getBiome(blockPos3).getTemperature(blockPos3) < 0.8f) || !blockState.isValidPosition(this.world, blockPos3)) continue;
                this.world.setBlockState(blockPos3, blockState);
            }
        }
    }

    @Override
    public void attackEntityWithRangedAttack(LivingEntity livingEntity, float f) {
        SnowballEntity snowballEntity = new SnowballEntity(this.world, this);
        double d = livingEntity.getPosYEye() - (double)1.1f;
        double d2 = livingEntity.getPosX() - this.getPosX();
        double d3 = d - snowballEntity.getPosY();
        double d4 = livingEntity.getPosZ() - this.getPosZ();
        float f2 = MathHelper.sqrt(d2 * d2 + d4 * d4) * 0.2f;
        snowballEntity.shoot(d2, d3 + (double)f2, d4, 1.6f, 12.0f);
        this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0f, 0.4f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
        this.world.addEntity(snowballEntity);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return 1.7f;
    }

    @Override
    protected ActionResultType func_230254_b_(PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (itemStack.getItem() == Items.SHEARS && this.isShearable()) {
            this.shear(SoundCategory.PLAYERS);
            if (!this.world.isRemote) {
                itemStack.damageItem(1, playerEntity, arg_0 -> SnowGolemEntity.lambda$func_230254_b_$1(hand, arg_0));
            }
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        return ActionResultType.PASS;
    }

    @Override
    public void shear(SoundCategory soundCategory) {
        this.world.playMovingSound(null, this, SoundEvents.ENTITY_SNOW_GOLEM_SHEAR, soundCategory, 1.0f, 1.0f);
        if (!this.world.isRemote()) {
            this.setPumpkinEquipped(true);
            this.entityDropItem(new ItemStack(Items.CARVED_PUMPKIN), 1.7f);
        }
    }

    @Override
    public boolean isShearable() {
        return this.isAlive() && this.isPumpkinEquipped();
    }

    public boolean isPumpkinEquipped() {
        return (this.dataManager.get(PUMPKIN_EQUIPPED) & 0x10) != 0;
    }

    public void setPumpkinEquipped(boolean bl) {
        byte by = this.dataManager.get(PUMPKIN_EQUIPPED);
        if (bl) {
            this.dataManager.set(PUMPKIN_EQUIPPED, (byte)(by | 0x10));
        } else {
            this.dataManager.set(PUMPKIN_EQUIPPED, (byte)(by & 0xFFFFFFEF));
        }
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SNOW_GOLEM_AMBIENT;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_SNOW_GOLEM_HURT;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SNOW_GOLEM_DEATH;
    }

    @Override
    public Vector3d func_241205_ce_() {
        return new Vector3d(0.0, 0.75f * this.getEyeHeight(), this.getWidth() * 0.4f);
    }

    private static void lambda$func_230254_b_$1(Hand hand, PlayerEntity playerEntity) {
        playerEntity.sendBreakAnimation(hand);
    }

    private static boolean lambda$registerGoals$0(LivingEntity livingEntity) {
        return livingEntity instanceof IMob;
    }
}

