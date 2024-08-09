/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import com.google.common.collect.Sets;
import java.util.LinkedHashSet;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.BoostHelper;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRideable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StriderEntity
extends AnimalEntity
implements IRideable,
IEquipable {
    private static final Ingredient field_234308_bu_ = Ingredient.fromItems(Items.WARPED_FUNGUS);
    private static final Ingredient field_234309_bv_ = Ingredient.fromItems(Items.WARPED_FUNGUS, Items.WARPED_FUNGUS_ON_A_STICK);
    private static final DataParameter<Integer> field_234310_bw_ = EntityDataManager.createKey(StriderEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> field_234311_bx_ = EntityDataManager.createKey(StriderEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> field_234312_by_ = EntityDataManager.createKey(StriderEntity.class, DataSerializers.BOOLEAN);
    private final BoostHelper field_234313_bz_;
    private TemptGoal field_234306_bA_;
    private PanicGoal field_234307_bB_;

    public StriderEntity(EntityType<? extends StriderEntity> entityType, World world) {
        super((EntityType<? extends AnimalEntity>)entityType, world);
        this.field_234313_bz_ = new BoostHelper(this.dataManager, field_234310_bw_, field_234312_by_);
        this.preventEntitySpawning = true;
        this.setPathPriority(PathNodeType.WATER, -1.0f);
        this.setPathPriority(PathNodeType.LAVA, 0.0f);
        this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0f);
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0f);
    }

    public static boolean func_234314_c_(EntityType<StriderEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        BlockPos.Mutable mutable = blockPos.toMutable();
        do {
            mutable.move(Direction.UP);
        } while (iWorld.getFluidState(mutable).isTagged(FluidTags.LAVA));
        return iWorld.getBlockState(mutable).isAir();
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        if (field_234310_bw_.equals(dataParameter) && this.world.isRemote) {
            this.field_234313_bz_.updateData();
        }
        super.notifyDataManagerChange(dataParameter);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(field_234310_bw_, 0);
        this.dataManager.register(field_234311_bx_, false);
        this.dataManager.register(field_234312_by_, false);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        this.field_234313_bz_.setSaddledToNBT(compoundNBT);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.field_234313_bz_.setSaddledFromNBT(compoundNBT);
    }

    @Override
    public boolean isHorseSaddled() {
        return this.field_234313_bz_.getSaddled();
    }

    @Override
    public boolean func_230264_L__() {
        return this.isAlive() && !this.isChild();
    }

    @Override
    public void func_230266_a_(@Nullable SoundCategory soundCategory) {
        this.field_234313_bz_.setSaddledFromBoolean(false);
        if (soundCategory != null) {
            this.world.playMovingSound(null, this, SoundEvents.ENTITY_STRIDER_SADDLE, soundCategory, 0.5f, 1.0f);
        }
    }

    @Override
    protected void registerGoals() {
        this.field_234307_bB_ = new PanicGoal(this, 1.65);
        this.goalSelector.addGoal(1, this.field_234307_bB_);
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.field_234306_bA_ = new TemptGoal((CreatureEntity)this, 1.4, false, field_234309_bv_);
        this.goalSelector.addGoal(3, this.field_234306_bA_);
        this.goalSelector.addGoal(4, new MoveToLavaGoal(this, 1.5));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.0, 60));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(9, new LookAtGoal(this, StriderEntity.class, 8.0f));
    }

    public void func_234319_t_(boolean bl) {
        this.dataManager.set(field_234311_bx_, bl);
    }

    public boolean func_234315_eI_() {
        return this.getRidingEntity() instanceof StriderEntity ? ((StriderEntity)this.getRidingEntity()).func_234315_eI_() : this.dataManager.get(field_234311_bx_).booleanValue();
    }

    @Override
    public boolean func_230285_a_(Fluid fluid) {
        return fluid.isIn(FluidTags.LAVA);
    }

    @Override
    public double getMountedYOffset() {
        float f = Math.min(0.25f, this.limbSwingAmount);
        float f2 = this.limbSwing;
        return (double)this.getHeight() - 0.19 + (double)(0.12f * MathHelper.cos(f2 * 1.5f) * 2.0f * f);
    }

    @Override
    public boolean canBeSteered() {
        Entity entity2 = this.getControllingPassenger();
        if (!(entity2 instanceof PlayerEntity)) {
            return true;
        }
        PlayerEntity playerEntity = (PlayerEntity)entity2;
        return playerEntity.getHeldItemMainhand().getItem() == Items.WARPED_FUNGUS_ON_A_STICK || playerEntity.getHeldItemOffhand().getItem() == Items.WARPED_FUNGUS_ON_A_STICK;
    }

    @Override
    public boolean isNotColliding(IWorldReader iWorldReader) {
        return iWorldReader.checkNoEntityCollision(this);
    }

    @Override
    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Override
    public Vector3d func_230268_c_(LivingEntity livingEntity) {
        Vector3d[] vector3dArray = new Vector3d[]{StriderEntity.func_233559_a_(this.getWidth(), livingEntity.getWidth(), livingEntity.rotationYaw), StriderEntity.func_233559_a_(this.getWidth(), livingEntity.getWidth(), livingEntity.rotationYaw - 22.5f), StriderEntity.func_233559_a_(this.getWidth(), livingEntity.getWidth(), livingEntity.rotationYaw + 22.5f), StriderEntity.func_233559_a_(this.getWidth(), livingEntity.getWidth(), livingEntity.rotationYaw - 45.0f), StriderEntity.func_233559_a_(this.getWidth(), livingEntity.getWidth(), livingEntity.rotationYaw + 45.0f)};
        LinkedHashSet<BlockPos> linkedHashSet = Sets.newLinkedHashSet();
        double d = this.getBoundingBox().maxY;
        double d2 = this.getBoundingBox().minY - 0.5;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (Vector3d vector3d : vector3dArray) {
            mutable.setPos(this.getPosX() + vector3d.x, d, this.getPosZ() + vector3d.z);
            for (double d3 = d; d3 > d2; d3 -= 1.0) {
                linkedHashSet.add(mutable.toImmutable());
                mutable.move(Direction.DOWN);
            }
        }
        for (BlockPos blockPos : linkedHashSet) {
            double d4;
            if (this.world.getFluidState(blockPos).isTagged(FluidTags.LAVA) || !TransportationHelper.func_234630_a_(d4 = this.world.func_242403_h(blockPos))) continue;
            Vector3d vector3d = Vector3d.copyCenteredWithVerticalOffset(blockPos, d4);
            for (Pose pose : livingEntity.getAvailablePoses()) {
                AxisAlignedBB axisAlignedBB = livingEntity.getPoseAABB(pose);
                if (!TransportationHelper.func_234631_a_(this.world, livingEntity, axisAlignedBB.offset(vector3d))) continue;
                livingEntity.setPose(pose);
                return vector3d;
            }
        }
        return new Vector3d(this.getPosX(), this.getBoundingBox().maxY, this.getPosZ());
    }

    @Override
    public void travel(Vector3d vector3d) {
        this.setAIMoveSpeed(this.func_234316_eJ_());
        this.ride(this, this.field_234313_bz_, vector3d);
    }

    public float func_234316_eJ_() {
        return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * (this.func_234315_eI_() ? 0.66f : 1.0f);
    }

    @Override
    public float getMountedSpeed() {
        return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * (this.func_234315_eI_() ? 0.23f : 0.55f);
    }

    @Override
    public void travelTowards(Vector3d vector3d) {
        super.travel(vector3d);
    }

    @Override
    protected float determineNextStepDistance() {
        return this.distanceWalkedOnStepModified + 0.6f;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(this.isInLava() ? SoundEvents.ENTITY_STRIDER_STEP_LAVA : SoundEvents.ENTITY_STRIDER_STEP, 1.0f, 1.0f);
    }

    @Override
    public boolean boost() {
        return this.field_234313_bz_.boost(this.getRNG());
    }

    @Override
    protected void updateFallState(double d, boolean bl, BlockState blockState, BlockPos blockPos) {
        this.doBlockCollisions();
        if (this.isInLava()) {
            this.fallDistance = 0.0f;
        } else {
            super.updateFallState(d, bl, blockState, blockPos);
        }
    }

    @Override
    public void tick() {
        if (this.func_241398_eP_() && this.rand.nextInt(140) == 0) {
            this.playSound(SoundEvents.ENTITY_STRIDER_HAPPY, 1.0f, this.getSoundPitch());
        } else if (this.func_241397_eO_() && this.rand.nextInt(60) == 0) {
            this.playSound(SoundEvents.ENTITY_STRIDER_RETREAT, 1.0f, this.getSoundPitch());
        }
        BlockState blockState = this.world.getBlockState(this.getPosition());
        BlockState blockState2 = this.getStateBelow();
        boolean bl = blockState.isIn(BlockTags.STRIDER_WARM_BLOCKS) || blockState2.isIn(BlockTags.STRIDER_WARM_BLOCKS) || this.func_233571_b_(FluidTags.LAVA) > 0.0;
        this.func_234319_t_(!bl);
        super.tick();
        this.func_234318_eL_();
        this.doBlockCollisions();
    }

    private boolean func_241397_eO_() {
        return this.field_234307_bB_ != null && this.field_234307_bB_.isRunning();
    }

    private boolean func_241398_eP_() {
        return this.field_234306_bA_ != null && this.field_234306_bA_.isRunning();
    }

    @Override
    protected boolean func_230286_q_() {
        return false;
    }

    private void func_234318_eL_() {
        if (this.isInLava()) {
            ISelectionContext iSelectionContext = ISelectionContext.forEntity(this);
            if (iSelectionContext.func_216378_a(FlowingFluidBlock.LAVA_COLLISION_SHAPE, this.getPosition(), true) && !this.world.getFluidState(this.getPosition().up()).isTagged(FluidTags.LAVA)) {
                this.onGround = true;
            } else {
                this.setMotion(this.getMotion().scale(0.5).add(0.0, 0.05, 0.0));
            }
        }
    }

    public static AttributeModifierMap.MutableAttribute func_234317_eK_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.175f).createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return !this.func_241397_eO_() && !this.func_241398_eP_() ? SoundEvents.ENTITY_STRIDER_AMBIENT : null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_STRIDER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_STRIDER_DEATH;
    }

    @Override
    protected boolean canFitPassenger(Entity entity2) {
        return this.getPassengers().isEmpty() && !this.areEyesInFluid(FluidTags.LAVA);
    }

    @Override
    public boolean isWaterSensitive() {
        return false;
    }

    @Override
    public boolean isBurning() {
        return true;
    }

    @Override
    protected PathNavigator createNavigator(World world) {
        return new LavaPathNavigator(this, world);
    }

    @Override
    public float getBlockPathWeight(BlockPos blockPos, IWorldReader iWorldReader) {
        if (iWorldReader.getBlockState(blockPos).getFluidState().isTagged(FluidTags.LAVA)) {
            return 10.0f;
        }
        return this.isInLava() ? Float.NEGATIVE_INFINITY : 0.0f;
    }

    @Override
    public StriderEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return EntityType.STRIDER.create(serverWorld);
    }

    @Override
    public boolean isBreedingItem(ItemStack itemStack) {
        return field_234308_bu_.test(itemStack);
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();
        if (this.isHorseSaddled()) {
            this.entityDropItem(Items.SADDLE);
        }
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
        if (bl && !this.isSilent()) {
            this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_STRIDER_EAT, this.getSoundCategory(), 1.0f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f);
        }
        return actionResultType;
    }

    @Override
    public Vector3d func_241205_ce_() {
        return new Vector3d(0.0, 0.6f * this.getEyeHeight(), this.getWidth() * 0.4f);
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        ILivingEntityData iLivingEntityData2;
        if (this.isChild()) {
            return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
        }
        if (this.rand.nextInt(30) == 0) {
            MobEntity mobEntity = EntityType.ZOMBIFIED_PIGLIN.create(iServerWorld.getWorld());
            iLivingEntityData2 = this.func_242331_a(iServerWorld, difficultyInstance, mobEntity, new ZombieEntity.GroupData(ZombieEntity.func_241399_a_(this.rand), false));
            mobEntity.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.WARPED_FUNGUS_ON_A_STICK));
            this.func_230266_a_(null);
        } else if (this.rand.nextInt(10) == 0) {
            AgeableEntity ageableEntity = EntityType.STRIDER.create(iServerWorld.getWorld());
            ageableEntity.setGrowingAge(-24000);
            iLivingEntityData2 = this.func_242331_a(iServerWorld, difficultyInstance, ageableEntity, null);
        } else {
            iLivingEntityData2 = new AgeableEntity.AgeableData(0.5f);
        }
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData2, compoundNBT);
    }

    private ILivingEntityData func_242331_a(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, MobEntity mobEntity, @Nullable ILivingEntityData iLivingEntityData) {
        mobEntity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, 0.0f);
        mobEntity.onInitialSpawn(iServerWorld, difficultyInstance, SpawnReason.JOCKEY, iLivingEntityData, null);
        mobEntity.startRiding(this, false);
        return new AgeableEntity.AgeableData(0.0f);
    }

    @Override
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return this.func_241840_a(serverWorld, ageableEntity);
    }

    static class MoveToLavaGoal
    extends MoveToBlockGoal {
        private final StriderEntity field_242332_g;

        private MoveToLavaGoal(StriderEntity striderEntity, double d) {
            super(striderEntity, d, 8, 2);
            this.field_242332_g = striderEntity;
        }

        @Override
        public BlockPos func_241846_j() {
            return this.destinationBlock;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return !this.field_242332_g.isInLava() && this.shouldMoveTo(this.field_242332_g.world, this.destinationBlock);
        }

        @Override
        public boolean shouldExecute() {
            return !this.field_242332_g.isInLava() && super.shouldExecute();
        }

        @Override
        public boolean shouldMove() {
            return this.timeoutCounter % 20 == 0;
        }

        @Override
        protected boolean shouldMoveTo(IWorldReader iWorldReader, BlockPos blockPos) {
            return iWorldReader.getBlockState(blockPos).isIn(Blocks.LAVA) && iWorldReader.getBlockState(blockPos.up()).allowsMovement(iWorldReader, blockPos, PathType.LAND);
        }
    }

    static class LavaPathNavigator
    extends GroundPathNavigator {
        LavaPathNavigator(StriderEntity striderEntity, World world) {
            super(striderEntity, world);
        }

        @Override
        protected PathFinder getPathFinder(int n) {
            this.nodeProcessor = new WalkNodeProcessor();
            return new PathFinder(this.nodeProcessor, n);
        }

        @Override
        protected boolean func_230287_a_(PathNodeType pathNodeType) {
            return pathNodeType != PathNodeType.LAVA && pathNodeType != PathNodeType.DAMAGE_FIRE && pathNodeType != PathNodeType.DANGER_FIRE ? super.func_230287_a_(pathNodeType) : true;
        }

        @Override
        public boolean canEntityStandOnPos(BlockPos blockPos) {
            return this.world.getBlockState(blockPos).isIn(Blocks.LAVA) || super.canEntityStandOnPos(blockPos);
        }
    }
}

