/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive.fish;

import java.util.Random;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public abstract class AbstractFishEntity
extends WaterMobEntity {
    private static final DataParameter<Boolean> FROM_BUCKET = EntityDataManager.createKey(AbstractFishEntity.class, DataSerializers.BOOLEAN);

    public AbstractFishEntity(EntityType<? extends AbstractFishEntity> entityType, World world) {
        super((EntityType<? extends WaterMobEntity>)entityType, world);
        this.moveController = new MoveHelperController(this);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return entitySize.height * 0.65f;
    }

    public static AttributeModifierMap.MutableAttribute func_234176_m_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 3.0);
    }

    @Override
    public boolean preventDespawn() {
        return super.preventDespawn() || this.isFromBucket();
    }

    public static boolean func_223363_b(EntityType<? extends AbstractFishEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        return iWorld.getBlockState(blockPos).isIn(Blocks.WATER) && iWorld.getBlockState(blockPos.up()).isIn(Blocks.WATER);
    }

    @Override
    public boolean canDespawn(double d) {
        return !this.isFromBucket() && !this.hasCustomName();
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(FROM_BUCKET, false);
    }

    private boolean isFromBucket() {
        return this.dataManager.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean bl) {
        this.dataManager.set(FROM_BUCKET, bl);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putBoolean("FromBucket", this.isFromBucket());
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.setFromBucket(compoundNBT.getBoolean("FromBucket"));
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0f, 1.6, 1.4, EntityPredicates.NOT_SPECTATING::test));
        this.goalSelector.addGoal(4, new SwimGoal(this));
    }

    @Override
    protected PathNavigator createNavigator(World world) {
        return new SwimmerPathNavigator(this, world);
    }

    @Override
    public void travel(Vector3d vector3d) {
        if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(0.01f, vector3d);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.9));
            if (this.getAttackTarget() == null) {
                this.setMotion(this.getMotion().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(vector3d);
        }
    }

    @Override
    public void livingTick() {
        if (!this.isInWater() && this.onGround && this.collidedVertically) {
            this.setMotion(this.getMotion().add((this.rand.nextFloat() * 2.0f - 1.0f) * 0.05f, 0.4f, (this.rand.nextFloat() * 2.0f - 1.0f) * 0.05f));
            this.onGround = false;
            this.isAirBorne = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getSoundPitch());
        }
        super.livingTick();
    }

    @Override
    protected ActionResultType func_230254_b_(PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (itemStack.getItem() == Items.WATER_BUCKET && this.isAlive()) {
            this.playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, 1.0f, 1.0f);
            itemStack.shrink(1);
            ItemStack itemStack2 = this.getFishBucket();
            this.setBucketData(itemStack2);
            if (!this.world.isRemote) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity)playerEntity, itemStack2);
            }
            if (itemStack.isEmpty()) {
                playerEntity.setHeldItem(hand, itemStack2);
            } else if (!playerEntity.inventory.addItemStackToInventory(itemStack2)) {
                playerEntity.dropItem(itemStack2, true);
            }
            this.remove();
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        return super.func_230254_b_(playerEntity, hand);
    }

    protected void setBucketData(ItemStack itemStack) {
        if (this.hasCustomName()) {
            itemStack.setDisplayName(this.getCustomName());
        }
    }

    protected abstract ItemStack getFishBucket();

    protected boolean func_212800_dy() {
        return false;
    }

    protected abstract SoundEvent getFlopSound();

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_FISH_SWIM;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
    }

    static class MoveHelperController
    extends MovementController {
        private final AbstractFishEntity fish;

        MoveHelperController(AbstractFishEntity abstractFishEntity) {
            super(abstractFishEntity);
            this.fish = abstractFishEntity;
        }

        @Override
        public void tick() {
            if (this.fish.areEyesInFluid(FluidTags.WATER)) {
                this.fish.setMotion(this.fish.getMotion().add(0.0, 0.005, 0.0));
            }
            if (this.action == MovementController.Action.MOVE_TO && !this.fish.getNavigator().noPath()) {
                float f = (float)(this.speed * this.fish.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.fish.setAIMoveSpeed(MathHelper.lerp(0.125f, this.fish.getAIMoveSpeed(), f));
                double d = this.posX - this.fish.getPosX();
                double d2 = this.posY - this.fish.getPosY();
                double d3 = this.posZ - this.fish.getPosZ();
                if (d2 != 0.0) {
                    double d4 = MathHelper.sqrt(d * d + d2 * d2 + d3 * d3);
                    this.fish.setMotion(this.fish.getMotion().add(0.0, (double)this.fish.getAIMoveSpeed() * (d2 / d4) * 0.1, 0.0));
                }
                if (d != 0.0 || d3 != 0.0) {
                    float f2 = (float)(MathHelper.atan2(d3, d) * 57.2957763671875) - 90.0f;
                    this.fish.renderYawOffset = this.fish.rotationYaw = this.limitAngle(this.fish.rotationYaw, f2, 90.0f);
                }
            } else {
                this.fish.setAIMoveSpeed(0.0f);
            }
        }
    }

    static class SwimGoal
    extends RandomSwimmingGoal {
        private final AbstractFishEntity fish;

        public SwimGoal(AbstractFishEntity abstractFishEntity) {
            super(abstractFishEntity, 1.0, 40);
            this.fish = abstractFishEntity;
        }

        @Override
        public boolean shouldExecute() {
            return this.fish.func_212800_dy() && super.shouldExecute();
        }
    }
}

