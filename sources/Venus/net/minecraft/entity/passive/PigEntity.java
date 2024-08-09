/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.BoostHelper;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.IRideable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TransportationHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PigEntity
extends AnimalEntity
implements IRideable,
IEquipable {
    private static final DataParameter<Boolean> SADDLED = EntityDataManager.createKey(PigEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> BOOST_TIME = EntityDataManager.createKey(PigEntity.class, DataSerializers.VARINT);
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.CARROT, Items.POTATO, Items.BEETROOT);
    private final BoostHelper field_234214_bx_;

    public PigEntity(EntityType<? extends PigEntity> entityType, World world) {
        super((EntityType<? extends AnimalEntity>)entityType, world);
        this.field_234214_bx_ = new BoostHelper(this.dataManager, BOOST_TIME, SADDLED);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(4, new TemptGoal((CreatureEntity)this, 1.2, Ingredient.fromItems(Items.CARROT_ON_A_STICK), false));
        this.goalSelector.addGoal(4, new TemptGoal((CreatureEntity)this, 1.2, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute func_234215_eI_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25);
    }

    @Override
    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Override
    public boolean canBeSteered() {
        Entity entity2 = this.getControllingPassenger();
        if (!(entity2 instanceof PlayerEntity)) {
            return true;
        }
        PlayerEntity playerEntity = (PlayerEntity)entity2;
        return playerEntity.getHeldItemMainhand().getItem() == Items.CARROT_ON_A_STICK || playerEntity.getHeldItemOffhand().getItem() == Items.CARROT_ON_A_STICK;
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        if (BOOST_TIME.equals(dataParameter) && this.world.isRemote) {
            this.field_234214_bx_.updateData();
        }
        super.notifyDataManagerChange(dataParameter);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(SADDLED, false);
        this.dataManager.register(BOOST_TIME, 0);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        this.field_234214_bx_.setSaddledToNBT(compoundNBT);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.field_234214_bx_.setSaddledFromNBT(compoundNBT);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_PIG_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIG_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15f, 1.0f);
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity playerEntity, Hand hand) {
        boolean bl = this.isBreedingItem(playerEntity.getHeldItem(hand));
        if (!bl && this.isHorseSaddled() && !this.isBeingRidden() && !playerEntity.isSecondaryUseActive()) {
            if (!this.world.isRemote) {
                playerEntity.startRiding(this);
            }
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        ActionResultType actionResultType = super.func_230254_b_(playerEntity, hand);
        if (!actionResultType.isSuccessOrConsume()) {
            ItemStack itemStack = playerEntity.getHeldItem(hand);
            return itemStack.getItem() == Items.SADDLE ? itemStack.interactWithEntity(playerEntity, this, hand) : ActionResultType.PASS;
        }
        return actionResultType;
    }

    @Override
    public boolean func_230264_L__() {
        return this.isAlive() && !this.isChild();
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();
        if (this.isHorseSaddled()) {
            this.entityDropItem(Items.SADDLE);
        }
    }

    @Override
    public boolean isHorseSaddled() {
        return this.field_234214_bx_.getSaddled();
    }

    @Override
    public void func_230266_a_(@Nullable SoundCategory soundCategory) {
        this.field_234214_bx_.setSaddledFromBoolean(false);
        if (soundCategory != null) {
            this.world.playMovingSound(null, this, SoundEvents.ENTITY_PIG_SADDLE, soundCategory, 0.5f, 1.0f);
        }
    }

    @Override
    public Vector3d func_230268_c_(LivingEntity livingEntity) {
        Direction direction = this.getAdjustedHorizontalFacing();
        if (direction.getAxis() == Direction.Axis.Y) {
            return super.func_230268_c_(livingEntity);
        }
        int[][] nArray = TransportationHelper.func_234632_a_(direction);
        BlockPos blockPos = this.getPosition();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (Pose pose : livingEntity.getAvailablePoses()) {
            AxisAlignedBB axisAlignedBB = livingEntity.getPoseAABB(pose);
            for (int[] nArray2 : nArray) {
                Vector3d vector3d;
                mutable.setPos(blockPos.getX() + nArray2[0], blockPos.getY(), blockPos.getZ() + nArray2[1]);
                double d = this.world.func_242403_h(mutable);
                if (!TransportationHelper.func_234630_a_(d) || !TransportationHelper.func_234631_a_(this.world, livingEntity, axisAlignedBB.offset(vector3d = Vector3d.copyCenteredWithVerticalOffset(mutable, d)))) continue;
                livingEntity.setPose(pose);
                return vector3d;
            }
        }
        return super.func_230268_c_(livingEntity);
    }

    @Override
    public void func_241841_a(ServerWorld serverWorld, LightningBoltEntity lightningBoltEntity) {
        if (serverWorld.getDifficulty() != Difficulty.PEACEFUL) {
            ZombifiedPiglinEntity zombifiedPiglinEntity = EntityType.ZOMBIFIED_PIGLIN.create(serverWorld);
            zombifiedPiglinEntity.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
            zombifiedPiglinEntity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
            zombifiedPiglinEntity.setNoAI(this.isAIDisabled());
            zombifiedPiglinEntity.setChild(this.isChild());
            if (this.hasCustomName()) {
                zombifiedPiglinEntity.setCustomName(this.getCustomName());
                zombifiedPiglinEntity.setCustomNameVisible(this.isCustomNameVisible());
            }
            zombifiedPiglinEntity.enablePersistence();
            serverWorld.addEntity(zombifiedPiglinEntity);
            this.remove();
        } else {
            super.func_241841_a(serverWorld, lightningBoltEntity);
        }
    }

    @Override
    public void travel(Vector3d vector3d) {
        this.ride(this, this.field_234214_bx_, vector3d);
    }

    @Override
    public float getMountedSpeed() {
        return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.225f;
    }

    @Override
    public void travelTowards(Vector3d vector3d) {
        super.travel(vector3d);
    }

    @Override
    public boolean boost() {
        return this.field_234214_bx_.boost(this.getRNG());
    }

    @Override
    public PigEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return EntityType.PIG.create(serverWorld);
    }

    @Override
    public boolean isBreedingItem(ItemStack itemStack) {
        return TEMPTATION_ITEMS.test(itemStack);
    }

    @Override
    public Vector3d func_241205_ce_() {
        return new Vector3d(0.0, 0.6f * this.getEyeHeight(), this.getWidth() * 0.4f);
    }

    @Override
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return this.func_241840_a(serverWorld, ageableEntity);
    }
}

