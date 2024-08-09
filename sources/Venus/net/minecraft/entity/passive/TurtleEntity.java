/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TurtleEggBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.pathfinding.WalkAndSwimNodeProcessor;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TurtleEntity
extends AnimalEntity {
    private static final DataParameter<BlockPos> HOME_POS = EntityDataManager.createKey(TurtleEntity.class, DataSerializers.BLOCK_POS);
    private static final DataParameter<Boolean> HAS_EGG = EntityDataManager.createKey(TurtleEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_DIGGING = EntityDataManager.createKey(TurtleEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<BlockPos> TRAVEL_POS = EntityDataManager.createKey(TurtleEntity.class, DataSerializers.BLOCK_POS);
    private static final DataParameter<Boolean> GOING_HOME = EntityDataManager.createKey(TurtleEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> TRAVELLING = EntityDataManager.createKey(TurtleEntity.class, DataSerializers.BOOLEAN);
    private int isDigging;
    public static final Predicate<LivingEntity> TARGET_DRY_BABY = TurtleEntity::lambda$static$0;

    public TurtleEntity(EntityType<? extends TurtleEntity> entityType, World world) {
        super((EntityType<? extends AnimalEntity>)entityType, world);
        this.setPathPriority(PathNodeType.WATER, 0.0f);
        this.moveController = new MoveHelperController(this);
        this.stepHeight = 1.0f;
    }

    public void setHome(BlockPos blockPos) {
        this.dataManager.set(HOME_POS, blockPos);
    }

    private BlockPos getHome() {
        return this.dataManager.get(HOME_POS);
    }

    private void setTravelPos(BlockPos blockPos) {
        this.dataManager.set(TRAVEL_POS, blockPos);
    }

    private BlockPos getTravelPos() {
        return this.dataManager.get(TRAVEL_POS);
    }

    public boolean hasEgg() {
        return this.dataManager.get(HAS_EGG);
    }

    private void setHasEgg(boolean bl) {
        this.dataManager.set(HAS_EGG, bl);
    }

    public boolean isDigging() {
        return this.dataManager.get(IS_DIGGING);
    }

    private void setDigging(boolean bl) {
        this.isDigging = bl ? 1 : 0;
        this.dataManager.set(IS_DIGGING, bl);
    }

    private boolean isGoingHome() {
        return this.dataManager.get(GOING_HOME);
    }

    private void setGoingHome(boolean bl) {
        this.dataManager.set(GOING_HOME, bl);
    }

    private boolean isTravelling() {
        return this.dataManager.get(TRAVELLING);
    }

    private void setTravelling(boolean bl) {
        this.dataManager.set(TRAVELLING, bl);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(HOME_POS, BlockPos.ZERO);
        this.dataManager.register(HAS_EGG, false);
        this.dataManager.register(TRAVEL_POS, BlockPos.ZERO);
        this.dataManager.register(GOING_HOME, false);
        this.dataManager.register(TRAVELLING, false);
        this.dataManager.register(IS_DIGGING, false);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("HomePosX", this.getHome().getX());
        compoundNBT.putInt("HomePosY", this.getHome().getY());
        compoundNBT.putInt("HomePosZ", this.getHome().getZ());
        compoundNBT.putBoolean("HasEgg", this.hasEgg());
        compoundNBT.putInt("TravelPosX", this.getTravelPos().getX());
        compoundNBT.putInt("TravelPosY", this.getTravelPos().getY());
        compoundNBT.putInt("TravelPosZ", this.getTravelPos().getZ());
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        int n = compoundNBT.getInt("HomePosX");
        int n2 = compoundNBT.getInt("HomePosY");
        int n3 = compoundNBT.getInt("HomePosZ");
        this.setHome(new BlockPos(n, n2, n3));
        super.readAdditional(compoundNBT);
        this.setHasEgg(compoundNBT.getBoolean("HasEgg"));
        int n4 = compoundNBT.getInt("TravelPosX");
        int n5 = compoundNBT.getInt("TravelPosY");
        int n6 = compoundNBT.getInt("TravelPosZ");
        this.setTravelPos(new BlockPos(n4, n5, n6));
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        this.setHome(this.getPosition());
        this.setTravelPos(BlockPos.ZERO);
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    public static boolean func_223322_c(EntityType<TurtleEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        return blockPos.getY() < iWorld.getSeaLevel() + 4 && TurtleEggBlock.hasProperHabitat(iWorld, blockPos) && iWorld.getLightSubtracted(blockPos, 0) > 8;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.2));
        this.goalSelector.addGoal(1, new MateGoal(this, 1.0));
        this.goalSelector.addGoal(1, new LayEggGoal(this, 1.0));
        this.goalSelector.addGoal(2, new PlayerTemptGoal(this, 1.1, Blocks.SEAGRASS.asItem()));
        this.goalSelector.addGoal(3, new GoToWaterGoal(this, 1.0));
        this.goalSelector.addGoal(4, new GoHomeGoal(this, 1.0));
        this.goalSelector.addGoal(7, new TravelGoal(this, 1.0));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(9, new WanderGoal(this, 1.0, 100));
    }

    public static AttributeModifierMap.MutableAttribute func_234228_eK_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 30.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25);
    }

    @Override
    public boolean isPushedByWater() {
        return true;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return false;
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.WATER;
    }

    @Override
    public int getTalkInterval() {
        return 1;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return !this.isInWater() && this.onGround && !this.isChild() ? SoundEvents.ENTITY_TURTLE_AMBIENT_LAND : super.getAmbientSound();
    }

    @Override
    protected void playSwimSound(float f) {
        super.playSwimSound(f * 1.5f);
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_TURTLE_SWIM;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return this.isChild() ? SoundEvents.ENTITY_TURTLE_HURT_BABY : SoundEvents.ENTITY_TURTLE_HURT;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return this.isChild() ? SoundEvents.ENTITY_TURTLE_DEATH_BABY : SoundEvents.ENTITY_TURTLE_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        SoundEvent soundEvent = this.isChild() ? SoundEvents.ENTITY_TURTLE_SHAMBLE_BABY : SoundEvents.ENTITY_TURTLE_SHAMBLE;
        this.playSound(soundEvent, 0.15f, 1.0f);
    }

    @Override
    public boolean canFallInLove() {
        return super.canFallInLove() && !this.hasEgg();
    }

    @Override
    protected float determineNextStepDistance() {
        return this.distanceWalkedOnStepModified + 0.15f;
    }

    @Override
    public float getRenderScale() {
        return this.isChild() ? 0.3f : 1.0f;
    }

    @Override
    protected PathNavigator createNavigator(World world) {
        return new Navigator(this, world);
    }

    @Override
    @Nullable
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return EntityType.TURTLE.create(serverWorld);
    }

    @Override
    public boolean isBreedingItem(ItemStack itemStack) {
        return itemStack.getItem() == Blocks.SEAGRASS.asItem();
    }

    @Override
    public float getBlockPathWeight(BlockPos blockPos, IWorldReader iWorldReader) {
        if (!this.isGoingHome() && iWorldReader.getFluidState(blockPos).isTagged(FluidTags.WATER)) {
            return 10.0f;
        }
        return TurtleEggBlock.hasProperHabitat(iWorldReader, blockPos) ? 10.0f : iWorldReader.getBrightness(blockPos) - 0.5f;
    }

    @Override
    public void livingTick() {
        BlockPos blockPos;
        super.livingTick();
        if (this.isAlive() && this.isDigging() && this.isDigging >= 1 && this.isDigging % 5 == 0 && TurtleEggBlock.hasProperHabitat(this.world, blockPos = this.getPosition())) {
            this.world.playEvent(2001, blockPos, Block.getStateId(Blocks.SAND.getDefaultState()));
        }
    }

    @Override
    protected void onGrowingAdult() {
        super.onGrowingAdult();
        if (!this.isChild() && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            this.entityDropItem(Items.SCUTE, 1);
        }
    }

    @Override
    public void travel(Vector3d vector3d) {
        if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(0.1f, vector3d);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.9));
            if (!(this.getAttackTarget() != null || this.isGoingHome() && this.getHome().withinDistance(this.getPositionVec(), 20.0))) {
                this.setMotion(this.getMotion().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(vector3d);
        }
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity playerEntity) {
        return true;
    }

    @Override
    public void func_241841_a(ServerWorld serverWorld, LightningBoltEntity lightningBoltEntity) {
        this.attackEntityFrom(DamageSource.LIGHTNING_BOLT, Float.MAX_VALUE);
    }

    private static boolean lambda$static$0(LivingEntity livingEntity) {
        return livingEntity.isChild() && !livingEntity.isInWater();
    }

    static Random access$000(TurtleEntity turtleEntity) {
        return turtleEntity.rand;
    }

    static boolean access$100(TurtleEntity turtleEntity) {
        return turtleEntity.onGround;
    }

    static Random access$200(TurtleEntity turtleEntity) {
        return turtleEntity.rand;
    }

    static class MoveHelperController
    extends MovementController {
        private final TurtleEntity turtle;

        MoveHelperController(TurtleEntity turtleEntity) {
            super(turtleEntity);
            this.turtle = turtleEntity;
        }

        private void updateSpeed() {
            if (this.turtle.isInWater()) {
                this.turtle.setMotion(this.turtle.getMotion().add(0.0, 0.005, 0.0));
                if (!this.turtle.getHome().withinDistance(this.turtle.getPositionVec(), 16.0)) {
                    this.turtle.setAIMoveSpeed(Math.max(this.turtle.getAIMoveSpeed() / 2.0f, 0.08f));
                }
                if (this.turtle.isChild()) {
                    this.turtle.setAIMoveSpeed(Math.max(this.turtle.getAIMoveSpeed() / 3.0f, 0.06f));
                }
            } else if (TurtleEntity.access$100(this.turtle)) {
                this.turtle.setAIMoveSpeed(Math.max(this.turtle.getAIMoveSpeed() / 2.0f, 0.06f));
            }
        }

        @Override
        public void tick() {
            this.updateSpeed();
            if (this.action == MovementController.Action.MOVE_TO && !this.turtle.getNavigator().noPath()) {
                double d = this.posX - this.turtle.getPosX();
                double d2 = this.posY - this.turtle.getPosY();
                double d3 = this.posZ - this.turtle.getPosZ();
                double d4 = MathHelper.sqrt(d * d + d2 * d2 + d3 * d3);
                float f = (float)(MathHelper.atan2(d3, d) * 57.2957763671875) - 90.0f;
                this.turtle.renderYawOffset = this.turtle.rotationYaw = this.limitAngle(this.turtle.rotationYaw, f, 90.0f);
                float f2 = (float)(this.speed * this.turtle.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.turtle.setAIMoveSpeed(MathHelper.lerp(0.125f, this.turtle.getAIMoveSpeed(), f2));
                this.turtle.setMotion(this.turtle.getMotion().add(0.0, (double)this.turtle.getAIMoveSpeed() * (d2 /= d4) * 0.1, 0.0));
            } else {
                this.turtle.setAIMoveSpeed(0.0f);
            }
        }
    }

    static class PanicGoal
    extends net.minecraft.entity.ai.goal.PanicGoal {
        PanicGoal(TurtleEntity turtleEntity, double d) {
            super(turtleEntity, d);
        }

        @Override
        public boolean shouldExecute() {
            if (this.creature.getRevengeTarget() == null && !this.creature.isBurning()) {
                return true;
            }
            BlockPos blockPos = this.getRandPos(this.creature.world, this.creature, 7, 4);
            if (blockPos != null) {
                this.randPosX = blockPos.getX();
                this.randPosY = blockPos.getY();
                this.randPosZ = blockPos.getZ();
                return false;
            }
            return this.findRandomPosition();
        }
    }

    static class MateGoal
    extends BreedGoal {
        private final TurtleEntity turtle;

        MateGoal(TurtleEntity turtleEntity, double d) {
            super(turtleEntity, d);
            this.turtle = turtleEntity;
        }

        @Override
        public boolean shouldExecute() {
            return super.shouldExecute() && !this.turtle.hasEgg();
        }

        @Override
        protected void spawnBaby() {
            ServerPlayerEntity serverPlayerEntity = this.animal.getLoveCause();
            if (serverPlayerEntity == null && this.targetMate.getLoveCause() != null) {
                serverPlayerEntity = this.targetMate.getLoveCause();
            }
            if (serverPlayerEntity != null) {
                serverPlayerEntity.addStat(Stats.ANIMALS_BRED);
                CriteriaTriggers.BRED_ANIMALS.trigger(serverPlayerEntity, this.animal, this.targetMate, null);
            }
            this.turtle.setHasEgg(false);
            this.animal.resetInLove();
            this.targetMate.resetInLove();
            Random random2 = this.animal.getRNG();
            if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                this.world.addEntity(new ExperienceOrbEntity(this.world, this.animal.getPosX(), this.animal.getPosY(), this.animal.getPosZ(), random2.nextInt(7) + 1));
            }
        }
    }

    static class LayEggGoal
    extends MoveToBlockGoal {
        private final TurtleEntity turtle;

        LayEggGoal(TurtleEntity turtleEntity, double d) {
            super(turtleEntity, d, 16);
            this.turtle = turtleEntity;
        }

        @Override
        public boolean shouldExecute() {
            return this.turtle.hasEgg() && this.turtle.getHome().withinDistance(this.turtle.getPositionVec(), 9.0) ? super.shouldExecute() : false;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return super.shouldContinueExecuting() && this.turtle.hasEgg() && this.turtle.getHome().withinDistance(this.turtle.getPositionVec(), 9.0);
        }

        @Override
        public void tick() {
            super.tick();
            BlockPos blockPos = this.turtle.getPosition();
            if (!this.turtle.isInWater() && this.getIsAboveDestination()) {
                if (this.turtle.isDigging < 1) {
                    this.turtle.setDigging(false);
                } else if (this.turtle.isDigging > 200) {
                    World world = this.turtle.world;
                    world.playSound(null, blockPos, SoundEvents.ENTITY_TURTLE_LAY_EGG, SoundCategory.BLOCKS, 0.3f, 0.9f + world.rand.nextFloat() * 0.2f);
                    world.setBlockState(this.destinationBlock.up(), (BlockState)Blocks.TURTLE_EGG.getDefaultState().with(TurtleEggBlock.EGGS, TurtleEntity.access$000(this.turtle).nextInt(4) + 1), 0);
                    this.turtle.setHasEgg(true);
                    this.turtle.setDigging(true);
                    this.turtle.setInLove(600);
                }
                if (this.turtle.isDigging()) {
                    ++this.turtle.isDigging;
                }
            }
        }

        @Override
        protected boolean shouldMoveTo(IWorldReader iWorldReader, BlockPos blockPos) {
            return !iWorldReader.isAirBlock(blockPos.up()) ? false : TurtleEggBlock.isProperHabitat(iWorldReader, blockPos);
        }
    }

    static class PlayerTemptGoal
    extends Goal {
        private static final EntityPredicate field_220834_a = new EntityPredicate().setDistance(10.0).allowFriendlyFire().allowInvulnerable();
        private final TurtleEntity turtle;
        private final double speed;
        private PlayerEntity tempter;
        private int cooldown;
        private final Set<Item> temptItems;

        PlayerTemptGoal(TurtleEntity turtleEntity, double d, Item item) {
            this.turtle = turtleEntity;
            this.speed = d;
            this.temptItems = Sets.newHashSet(item);
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean shouldExecute() {
            if (this.cooldown > 0) {
                --this.cooldown;
                return true;
            }
            this.tempter = this.turtle.world.getClosestPlayer(field_220834_a, this.turtle);
            if (this.tempter == null) {
                return true;
            }
            return this.isTemptedBy(this.tempter.getHeldItemMainhand()) || this.isTemptedBy(this.tempter.getHeldItemOffhand());
        }

        private boolean isTemptedBy(ItemStack itemStack) {
            return this.temptItems.contains(itemStack.getItem());
        }

        @Override
        public boolean shouldContinueExecuting() {
            return this.shouldExecute();
        }

        @Override
        public void resetTask() {
            this.tempter = null;
            this.turtle.getNavigator().clearPath();
            this.cooldown = 100;
        }

        @Override
        public void tick() {
            this.turtle.getLookController().setLookPositionWithEntity(this.tempter, this.turtle.getHorizontalFaceSpeed() + 20, this.turtle.getVerticalFaceSpeed());
            if (this.turtle.getDistanceSq(this.tempter) < 6.25) {
                this.turtle.getNavigator().clearPath();
            } else {
                this.turtle.getNavigator().tryMoveToEntityLiving(this.tempter, this.speed);
            }
        }
    }

    static class GoToWaterGoal
    extends MoveToBlockGoal {
        private final TurtleEntity turtle;

        private GoToWaterGoal(TurtleEntity turtleEntity, double d) {
            super(turtleEntity, turtleEntity.isChild() ? 2.0 : d, 24);
            this.turtle = turtleEntity;
            this.field_203112_e = -1;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return !this.turtle.isInWater() && this.timeoutCounter <= 1200 && this.shouldMoveTo(this.turtle.world, this.destinationBlock);
        }

        @Override
        public boolean shouldExecute() {
            if (this.turtle.isChild() && !this.turtle.isInWater()) {
                return super.shouldExecute();
            }
            return !this.turtle.isGoingHome() && !this.turtle.isInWater() && !this.turtle.hasEgg() ? super.shouldExecute() : false;
        }

        @Override
        public boolean shouldMove() {
            return this.timeoutCounter % 160 == 0;
        }

        @Override
        protected boolean shouldMoveTo(IWorldReader iWorldReader, BlockPos blockPos) {
            return iWorldReader.getBlockState(blockPos).isIn(Blocks.WATER);
        }
    }

    static class GoHomeGoal
    extends Goal {
        private final TurtleEntity turtle;
        private final double speed;
        private boolean field_203129_c;
        private int field_203130_d;

        GoHomeGoal(TurtleEntity turtleEntity, double d) {
            this.turtle = turtleEntity;
            this.speed = d;
        }

        @Override
        public boolean shouldExecute() {
            if (this.turtle.isChild()) {
                return true;
            }
            if (this.turtle.hasEgg()) {
                return false;
            }
            if (this.turtle.getRNG().nextInt(700) != 0) {
                return true;
            }
            return !this.turtle.getHome().withinDistance(this.turtle.getPositionVec(), 64.0);
        }

        @Override
        public void startExecuting() {
            this.turtle.setGoingHome(false);
            this.field_203129_c = false;
            this.field_203130_d = 0;
        }

        @Override
        public void resetTask() {
            this.turtle.setGoingHome(true);
        }

        @Override
        public boolean shouldContinueExecuting() {
            return !this.turtle.getHome().withinDistance(this.turtle.getPositionVec(), 7.0) && !this.field_203129_c && this.field_203130_d <= 600;
        }

        @Override
        public void tick() {
            BlockPos blockPos = this.turtle.getHome();
            boolean bl = blockPos.withinDistance(this.turtle.getPositionVec(), 16.0);
            if (bl) {
                ++this.field_203130_d;
            }
            if (this.turtle.getNavigator().noPath()) {
                Vector3d vector3d = Vector3d.copyCenteredHorizontally(blockPos);
                Vector3d vector3d2 = RandomPositionGenerator.findRandomTargetTowardsScaled(this.turtle, 16, 3, vector3d, 0.3141592741012573);
                if (vector3d2 == null) {
                    vector3d2 = RandomPositionGenerator.findRandomTargetBlockTowards(this.turtle, 8, 7, vector3d);
                }
                if (vector3d2 != null && !bl && !this.turtle.world.getBlockState(new BlockPos(vector3d2)).isIn(Blocks.WATER)) {
                    vector3d2 = RandomPositionGenerator.findRandomTargetBlockTowards(this.turtle, 16, 5, vector3d);
                }
                if (vector3d2 == null) {
                    this.field_203129_c = true;
                    return;
                }
                this.turtle.getNavigator().tryMoveToXYZ(vector3d2.x, vector3d2.y, vector3d2.z, this.speed);
            }
        }
    }

    static class TravelGoal
    extends Goal {
        private final TurtleEntity turtle;
        private final double speed;
        private boolean field_203139_c;

        TravelGoal(TurtleEntity turtleEntity, double d) {
            this.turtle = turtleEntity;
            this.speed = d;
        }

        @Override
        public boolean shouldExecute() {
            return !this.turtle.isGoingHome() && !this.turtle.hasEgg() && this.turtle.isInWater();
        }

        @Override
        public void startExecuting() {
            int n = 512;
            int n2 = 4;
            Random random2 = TurtleEntity.access$200(this.turtle);
            int n3 = random2.nextInt(1025) - 512;
            int n4 = random2.nextInt(9) - 4;
            int n5 = random2.nextInt(1025) - 512;
            if ((double)n4 + this.turtle.getPosY() > (double)(this.turtle.world.getSeaLevel() - 1)) {
                n4 = 0;
            }
            BlockPos blockPos = new BlockPos((double)n3 + this.turtle.getPosX(), (double)n4 + this.turtle.getPosY(), (double)n5 + this.turtle.getPosZ());
            this.turtle.setTravelPos(blockPos);
            this.turtle.setTravelling(false);
            this.field_203139_c = false;
        }

        @Override
        public void tick() {
            if (this.turtle.getNavigator().noPath()) {
                Vector3d vector3d = Vector3d.copyCenteredHorizontally(this.turtle.getTravelPos());
                Vector3d vector3d2 = RandomPositionGenerator.findRandomTargetTowardsScaled(this.turtle, 16, 3, vector3d, 0.3141592741012573);
                if (vector3d2 == null) {
                    vector3d2 = RandomPositionGenerator.findRandomTargetBlockTowards(this.turtle, 8, 7, vector3d);
                }
                if (vector3d2 != null) {
                    int n = MathHelper.floor(vector3d2.x);
                    int n2 = MathHelper.floor(vector3d2.z);
                    int n3 = 34;
                    if (!this.turtle.world.isAreaLoaded(n - 34, 0, n2 - 34, n + 34, 0, n2 + 34)) {
                        vector3d2 = null;
                    }
                }
                if (vector3d2 == null) {
                    this.field_203139_c = true;
                    return;
                }
                this.turtle.getNavigator().tryMoveToXYZ(vector3d2.x, vector3d2.y, vector3d2.z, this.speed);
            }
        }

        @Override
        public boolean shouldContinueExecuting() {
            return !this.turtle.getNavigator().noPath() && !this.field_203139_c && !this.turtle.isGoingHome() && !this.turtle.isInLove() && !this.turtle.hasEgg();
        }

        @Override
        public void resetTask() {
            this.turtle.setTravelling(true);
            super.resetTask();
        }
    }

    static class WanderGoal
    extends RandomWalkingGoal {
        private final TurtleEntity turtle;

        private WanderGoal(TurtleEntity turtleEntity, double d, int n) {
            super(turtleEntity, d, n);
            this.turtle = turtleEntity;
        }

        @Override
        public boolean shouldExecute() {
            return !this.creature.isInWater() && !this.turtle.isGoingHome() && !this.turtle.hasEgg() ? super.shouldExecute() : false;
        }
    }

    static class Navigator
    extends SwimmerPathNavigator {
        Navigator(TurtleEntity turtleEntity, World world) {
            super(turtleEntity, world);
        }

        @Override
        protected boolean canNavigate() {
            return false;
        }

        @Override
        protected PathFinder getPathFinder(int n) {
            this.nodeProcessor = new WalkAndSwimNodeProcessor();
            return new PathFinder(this.nodeProcessor, n);
        }

        @Override
        public boolean canEntityStandOnPos(BlockPos blockPos) {
            TurtleEntity turtleEntity;
            if (this.entity instanceof TurtleEntity && (turtleEntity = (TurtleEntity)this.entity).isTravelling()) {
                return this.world.getBlockState(blockPos).isIn(Blocks.WATER);
            }
            return !this.world.getBlockState(blockPos.down()).isAir();
        }
    }
}

