/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.ResetAngerGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.village.PointOfInterest;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BeeEntity
extends AnimalEntity
implements IAngerable,
IFlyingAnimal {
    private static final DataParameter<Byte> DATA_FLAGS_ID = EntityDataManager.createKey(BeeEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Integer> ANGER_TIME = EntityDataManager.createKey(BeeEntity.class, DataSerializers.VARINT);
    private static final RangedInteger field_234180_bw_ = TickRangeConverter.convertRange(20, 39);
    private UUID lastHurtBy;
    private float rollAmount;
    private float rollAmountO;
    private int timeSinceSting;
    private int ticksWithoutNectarSinceExitingHive;
    private int stayOutOfHiveCountdown;
    private int numCropsGrownSincePollination;
    private int remainingCooldownBeforeLocatingNewHive = 0;
    private int remainingCooldownBeforeLocatingNewFlower = 0;
    @Nullable
    private BlockPos savedFlowerPos = null;
    @Nullable
    private BlockPos hivePos = null;
    private PollinateGoal pollinateGoal;
    private FindBeehiveGoal findBeehiveGoal;
    private FindFlowerGoal findFlowerGoal;
    private int underWaterTicks;

    public BeeEntity(EntityType<? extends BeeEntity> entityType, World world) {
        super((EntityType<? extends AnimalEntity>)entityType, world);
        this.moveController = new FlyingMovementController(this, 20, true);
        this.lookController = new BeeLookController(this, this);
        this.setPathPriority(PathNodeType.DANGER_FIRE, -1.0f);
        this.setPathPriority(PathNodeType.WATER, -1.0f);
        this.setPathPriority(PathNodeType.WATER_BORDER, 16.0f);
        this.setPathPriority(PathNodeType.COCOA, -1.0f);
        this.setPathPriority(PathNodeType.FENCE, -1.0f);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(DATA_FLAGS_ID, (byte)0);
        this.dataManager.register(ANGER_TIME, 0);
    }

    @Override
    public float getBlockPathWeight(BlockPos blockPos, IWorldReader iWorldReader) {
        return iWorldReader.getBlockState(blockPos).isAir() ? 10.0f : 0.0f;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new StingGoal(this, this, 1.4f, true));
        this.goalSelector.addGoal(1, new EnterBeehiveGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal((CreatureEntity)this, 1.25, Ingredient.fromTag(ItemTags.FLOWERS), false));
        this.pollinateGoal = new PollinateGoal(this);
        this.goalSelector.addGoal(4, this.pollinateGoal);
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(5, new UpdateBeehiveGoal(this));
        this.findBeehiveGoal = new FindBeehiveGoal(this);
        this.goalSelector.addGoal(5, this.findBeehiveGoal);
        this.findFlowerGoal = new FindFlowerGoal(this);
        this.goalSelector.addGoal(6, this.findFlowerGoal);
        this.goalSelector.addGoal(7, new FindPollinationTargetGoal(this));
        this.goalSelector.addGoal(8, new WanderGoal(this));
        this.goalSelector.addGoal(9, new SwimGoal(this));
        this.targetSelector.addGoal(1, new AngerGoal(this, this).setCallsForHelp(new Class[0]));
        this.targetSelector.addGoal(2, new AttackPlayerGoal(this));
        this.targetSelector.addGoal(3, new ResetAngerGoal<BeeEntity>(this, true));
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        if (this.hasHive()) {
            compoundNBT.put("HivePos", NBTUtil.writeBlockPos(this.getHivePos()));
        }
        if (this.hasFlower()) {
            compoundNBT.put("FlowerPos", NBTUtil.writeBlockPos(this.getFlowerPos()));
        }
        compoundNBT.putBoolean("HasNectar", this.hasNectar());
        compoundNBT.putBoolean("HasStung", this.hasStung());
        compoundNBT.putInt("TicksSincePollination", this.ticksWithoutNectarSinceExitingHive);
        compoundNBT.putInt("CannotEnterHiveTicks", this.stayOutOfHiveCountdown);
        compoundNBT.putInt("CropsGrownSincePollination", this.numCropsGrownSincePollination);
        this.writeAngerNBT(compoundNBT);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        this.hivePos = null;
        if (compoundNBT.contains("HivePos")) {
            this.hivePos = NBTUtil.readBlockPos(compoundNBT.getCompound("HivePos"));
        }
        this.savedFlowerPos = null;
        if (compoundNBT.contains("FlowerPos")) {
            this.savedFlowerPos = NBTUtil.readBlockPos(compoundNBT.getCompound("FlowerPos"));
        }
        super.readAdditional(compoundNBT);
        this.setHasNectar(compoundNBT.getBoolean("HasNectar"));
        this.setHasStung(compoundNBT.getBoolean("HasStung"));
        this.ticksWithoutNectarSinceExitingHive = compoundNBT.getInt("TicksSincePollination");
        this.stayOutOfHiveCountdown = compoundNBT.getInt("CannotEnterHiveTicks");
        this.numCropsGrownSincePollination = compoundNBT.getInt("CropsGrownSincePollination");
        this.readAngerNBT((ServerWorld)this.world, compoundNBT);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity2) {
        boolean bl = entity2.attackEntityFrom(DamageSource.causeBeeStingDamage(this), (int)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
        if (bl) {
            this.applyEnchantments(this, entity2);
            if (entity2 instanceof LivingEntity) {
                ((LivingEntity)entity2).setBeeStingCount(((LivingEntity)entity2).getBeeStingCount() + 1);
                int n = 0;
                if (this.world.getDifficulty() == Difficulty.NORMAL) {
                    n = 10;
                } else if (this.world.getDifficulty() == Difficulty.HARD) {
                    n = 18;
                }
                if (n > 0) {
                    ((LivingEntity)entity2).addPotionEffect(new EffectInstance(Effects.POISON, n * 20, 0));
                }
            }
            this.setHasStung(false);
            this.func_241356_K__();
            this.playSound(SoundEvents.ENTITY_BEE_STING, 1.0f, 1.0f);
        }
        return bl;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.hasNectar() && this.getCropsGrownSincePollination() < 10 && this.rand.nextFloat() < 0.05f) {
            for (int i = 0; i < this.rand.nextInt(2) + 1; ++i) {
                this.addParticle(this.world, this.getPosX() - (double)0.3f, this.getPosX() + (double)0.3f, this.getPosZ() - (double)0.3f, this.getPosZ() + (double)0.3f, this.getPosYHeight(0.5), ParticleTypes.FALLING_NECTAR);
            }
        }
        this.updateBodyPitch();
    }

    private void addParticle(World world, double d, double d2, double d3, double d4, double d5, IParticleData iParticleData) {
        world.addParticle(iParticleData, MathHelper.lerp(world.rand.nextDouble(), d, d2), d5, MathHelper.lerp(world.rand.nextDouble(), d3, d4), 0.0, 0.0, 0.0);
    }

    private void startMovingTo(BlockPos blockPos) {
        Vector3d vector3d;
        Vector3d vector3d2 = Vector3d.copyCenteredHorizontally(blockPos);
        int n = 0;
        BlockPos blockPos2 = this.getPosition();
        int n2 = (int)vector3d2.y - blockPos2.getY();
        if (n2 > 2) {
            n = 4;
        } else if (n2 < -2) {
            n = -4;
        }
        int n3 = 6;
        int n4 = 8;
        int n5 = blockPos2.manhattanDistance(blockPos);
        if (n5 < 15) {
            n3 = n5 / 2;
            n4 = n5 / 2;
        }
        if ((vector3d = RandomPositionGenerator.func_226344_b_(this, n3, n4, n, vector3d2, 0.3141592741012573)) != null) {
            this.navigator.setRangeMultiplier(0.5f);
            this.navigator.tryMoveToXYZ(vector3d.x, vector3d.y, vector3d.z, 1.0);
        }
    }

    @Nullable
    public BlockPos getFlowerPos() {
        return this.savedFlowerPos;
    }

    public boolean hasFlower() {
        return this.savedFlowerPos != null;
    }

    public void setFlowerPos(BlockPos blockPos) {
        this.savedFlowerPos = blockPos;
    }

    private boolean failedPollinatingTooLong() {
        return this.ticksWithoutNectarSinceExitingHive > 3600;
    }

    private boolean canEnterHive() {
        if (this.stayOutOfHiveCountdown <= 0 && !this.pollinateGoal.isRunning() && !this.hasStung() && this.getAttackTarget() == null) {
            boolean bl = this.failedPollinatingTooLong() || this.world.isRaining() || this.world.isNightTime() || this.hasNectar();
            return bl && !this.isHiveNearFire();
        }
        return true;
    }

    public void setStayOutOfHiveCountdown(int n) {
        this.stayOutOfHiveCountdown = n;
    }

    public float getBodyPitch(float f) {
        return MathHelper.lerp(f, this.rollAmountO, this.rollAmount);
    }

    private void updateBodyPitch() {
        this.rollAmountO = this.rollAmount;
        this.rollAmount = this.isNearTarget() ? Math.min(1.0f, this.rollAmount + 0.2f) : Math.max(0.0f, this.rollAmount - 0.24f);
    }

    @Override
    protected void updateAITasks() {
        boolean bl = this.hasStung();
        this.underWaterTicks = this.isInWaterOrBubbleColumn() ? ++this.underWaterTicks : 0;
        if (this.underWaterTicks > 20) {
            this.attackEntityFrom(DamageSource.DROWN, 1.0f);
        }
        if (bl) {
            ++this.timeSinceSting;
            if (this.timeSinceSting % 5 == 0 && this.rand.nextInt(MathHelper.clamp(1200 - this.timeSinceSting, 1, 1200)) == 0) {
                this.attackEntityFrom(DamageSource.GENERIC, this.getHealth());
            }
        }
        if (!this.hasNectar()) {
            ++this.ticksWithoutNectarSinceExitingHive;
        }
        if (!this.world.isRemote) {
            this.func_241359_a_((ServerWorld)this.world, true);
        }
    }

    public void resetTicksWithoutNectar() {
        this.ticksWithoutNectarSinceExitingHive = 0;
    }

    private boolean isHiveNearFire() {
        if (this.hivePos == null) {
            return true;
        }
        TileEntity tileEntity = this.world.getTileEntity(this.hivePos);
        return tileEntity instanceof BeehiveTileEntity && ((BeehiveTileEntity)tileEntity).isNearFire();
    }

    @Override
    public int getAngerTime() {
        return this.dataManager.get(ANGER_TIME);
    }

    @Override
    public void setAngerTime(int n) {
        this.dataManager.set(ANGER_TIME, n);
    }

    @Override
    public UUID getAngerTarget() {
        return this.lastHurtBy;
    }

    @Override
    public void setAngerTarget(@Nullable UUID uUID) {
        this.lastHurtBy = uUID;
    }

    @Override
    public void func_230258_H__() {
        this.setAngerTime(field_234180_bw_.getRandomWithinRange(this.rand));
    }

    private boolean doesHiveHaveSpace(BlockPos blockPos) {
        TileEntity tileEntity = this.world.getTileEntity(blockPos);
        if (tileEntity instanceof BeehiveTileEntity) {
            return !((BeehiveTileEntity)tileEntity).isFullOfBees();
        }
        return true;
    }

    public boolean hasHive() {
        return this.hivePos != null;
    }

    @Nullable
    public BlockPos getHivePos() {
        return this.hivePos;
    }

    @Override
    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPacketSender.func_229749_a_(this);
    }

    private int getCropsGrownSincePollination() {
        return this.numCropsGrownSincePollination;
    }

    private void resetCropCounter() {
        this.numCropsGrownSincePollination = 0;
    }

    private void addCropCounter() {
        ++this.numCropsGrownSincePollination;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) {
            if (this.stayOutOfHiveCountdown > 0) {
                --this.stayOutOfHiveCountdown;
            }
            if (this.remainingCooldownBeforeLocatingNewHive > 0) {
                --this.remainingCooldownBeforeLocatingNewHive;
            }
            if (this.remainingCooldownBeforeLocatingNewFlower > 0) {
                --this.remainingCooldownBeforeLocatingNewFlower;
            }
            boolean bl = this.func_233678_J__() && !this.hasStung() && this.getAttackTarget() != null && this.getAttackTarget().getDistanceSq(this) < 4.0;
            this.setNearTarget(bl);
            if (this.ticksExisted % 20 == 0 && !this.isHiveValid()) {
                this.hivePos = null;
            }
        }
    }

    private boolean isHiveValid() {
        if (!this.hasHive()) {
            return true;
        }
        TileEntity tileEntity = this.world.getTileEntity(this.hivePos);
        return tileEntity != null && tileEntity.getType() == TileEntityType.BEEHIVE;
    }

    public boolean hasNectar() {
        return this.getBeeFlag(1);
    }

    private void setHasNectar(boolean bl) {
        if (bl) {
            this.resetTicksWithoutNectar();
        }
        this.setBeeFlag(8, bl);
    }

    public boolean hasStung() {
        return this.getBeeFlag(1);
    }

    private void setHasStung(boolean bl) {
        this.setBeeFlag(4, bl);
    }

    private boolean isNearTarget() {
        return this.getBeeFlag(1);
    }

    private void setNearTarget(boolean bl) {
        this.setBeeFlag(2, bl);
    }

    private boolean isTooFar(BlockPos blockPos) {
        return !this.isWithinDistance(blockPos, 1);
    }

    private void setBeeFlag(int n, boolean bl) {
        if (bl) {
            this.dataManager.set(DATA_FLAGS_ID, (byte)(this.dataManager.get(DATA_FLAGS_ID) | n));
        } else {
            this.dataManager.set(DATA_FLAGS_ID, (byte)(this.dataManager.get(DATA_FLAGS_ID) & ~n));
        }
    }

    private boolean getBeeFlag(int n) {
        return (this.dataManager.get(DATA_FLAGS_ID) & n) != 0;
    }

    public static AttributeModifierMap.MutableAttribute func_234182_eX_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10.0).createMutableAttribute(Attributes.FLYING_SPEED, 0.6f).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3f).createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0).createMutableAttribute(Attributes.FOLLOW_RANGE, 48.0);
    }

    @Override
    protected PathNavigator createNavigator(World world) {
        FlyingPathNavigator flyingPathNavigator = new FlyingPathNavigator(this, this, world){
            final BeeEntity this$0;
            {
                this.this$0 = beeEntity;
                super(mobEntity, world);
            }

            @Override
            public boolean canEntityStandOnPos(BlockPos blockPos) {
                return !this.world.getBlockState(blockPos.down()).isAir();
            }

            @Override
            public void tick() {
                if (!this.this$0.pollinateGoal.isRunning()) {
                    super.tick();
                }
            }
        };
        flyingPathNavigator.setCanOpenDoors(true);
        flyingPathNavigator.setCanSwim(true);
        flyingPathNavigator.setCanEnterDoors(false);
        return flyingPathNavigator;
    }

    @Override
    public boolean isBreedingItem(ItemStack itemStack) {
        return itemStack.getItem().isIn(ItemTags.FLOWERS);
    }

    private boolean isFlowers(BlockPos blockPos) {
        return this.world.isBlockPresent(blockPos) && this.world.getBlockState(blockPos).getBlock().isIn(BlockTags.FLOWERS);
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_BEE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BEE_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }

    @Override
    public BeeEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return EntityType.BEE.create(serverWorld);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return this.isChild() ? entitySize.height * 0.5f : entitySize.height * 0.5f;
    }

    @Override
    public boolean onLivingFall(float f, float f2) {
        return true;
    }

    @Override
    protected void updateFallState(double d, boolean bl, BlockState blockState, BlockPos blockPos) {
    }

    @Override
    protected boolean makeFlySound() {
        return false;
    }

    public void onHoneyDelivered() {
        this.setHasNectar(true);
        this.resetCropCounter();
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return true;
        }
        Entity entity2 = damageSource.getTrueSource();
        if (!this.world.isRemote) {
            this.pollinateGoal.cancel();
        }
        return super.attackEntityFrom(damageSource, f);
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }

    @Override
    protected void handleFluidJump(ITag<Fluid> iTag) {
        this.setMotion(this.getMotion().add(0.0, 0.01, 0.0));
    }

    @Override
    public Vector3d func_241205_ce_() {
        return new Vector3d(0.0, 0.5f * this.getEyeHeight(), this.getWidth() * 0.2f);
    }

    private boolean isWithinDistance(BlockPos blockPos, int n) {
        return blockPos.withinDistance(this.getPosition(), (double)n);
    }

    @Override
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return this.func_241840_a(serverWorld, ageableEntity);
    }

    static PathNavigator access$000(BeeEntity beeEntity) {
        return beeEntity.navigator;
    }

    static PathNavigator access$100(BeeEntity beeEntity) {
        return beeEntity.navigator;
    }

    static PathNavigator access$200(BeeEntity beeEntity) {
        return beeEntity.navigator;
    }

    static PathNavigator access$300(BeeEntity beeEntity) {
        return beeEntity.navigator;
    }

    static PathNavigator access$400(BeeEntity beeEntity) {
        return beeEntity.navigator;
    }

    static PathNavigator access$500(BeeEntity beeEntity) {
        return beeEntity.navigator;
    }

    static PathNavigator access$600(BeeEntity beeEntity) {
        return beeEntity.navigator;
    }

    static PathNavigator access$700(BeeEntity beeEntity) {
        return beeEntity.navigator;
    }

    static PathNavigator access$800(BeeEntity beeEntity) {
        return beeEntity.navigator;
    }

    static PathNavigator access$900(BeeEntity beeEntity) {
        return beeEntity.navigator;
    }

    static PathNavigator access$1000(BeeEntity beeEntity) {
        return beeEntity.navigator;
    }

    static PathNavigator access$1100(BeeEntity beeEntity) {
        return beeEntity.navigator;
    }

    static PathNavigator access$1200(BeeEntity beeEntity) {
        return beeEntity.navigator;
    }

    static Random access$1300(BeeEntity beeEntity) {
        return beeEntity.rand;
    }

    static Random access$1400(BeeEntity beeEntity) {
        return beeEntity.rand;
    }

    static Random access$1500(BeeEntity beeEntity) {
        return beeEntity.rand;
    }

    static PathNavigator access$1600(BeeEntity beeEntity) {
        return beeEntity.navigator;
    }

    static Random access$1700(BeeEntity beeEntity) {
        return beeEntity.rand;
    }

    static PathNavigator access$1800(BeeEntity beeEntity) {
        return beeEntity.navigator;
    }

    static Random access$1900(BeeEntity beeEntity) {
        return beeEntity.rand;
    }

    static PathNavigator access$2000(BeeEntity beeEntity) {
        return beeEntity.navigator;
    }

    static Random access$2100(BeeEntity beeEntity) {
        return beeEntity.rand;
    }

    static Random access$2200(BeeEntity beeEntity) {
        return beeEntity.rand;
    }

    static PathNavigator access$2300(BeeEntity beeEntity) {
        return beeEntity.navigator;
    }

    static Random access$2400(BeeEntity beeEntity) {
        return beeEntity.rand;
    }

    static PathNavigator access$2500(BeeEntity beeEntity) {
        return beeEntity.navigator;
    }

    static PathNavigator access$2600(BeeEntity beeEntity) {
        return beeEntity.navigator;
    }

    static PathNavigator access$2700(BeeEntity beeEntity) {
        return beeEntity.navigator;
    }

    class BeeLookController
    extends LookController {
        final BeeEntity this$0;

        BeeLookController(BeeEntity beeEntity, MobEntity mobEntity) {
            this.this$0 = beeEntity;
            super(mobEntity);
        }

        @Override
        public void tick() {
            if (!this.this$0.func_233678_J__()) {
                super.tick();
            }
        }

        @Override
        protected boolean shouldResetPitch() {
            return !this.this$0.pollinateGoal.isRunning();
        }
    }

    class StingGoal
    extends MeleeAttackGoal {
        final BeeEntity this$0;

        StingGoal(BeeEntity beeEntity, CreatureEntity creatureEntity, double d, boolean bl) {
            this.this$0 = beeEntity;
            super(creatureEntity, d, bl);
        }

        @Override
        public boolean shouldExecute() {
            return super.shouldExecute() && this.this$0.func_233678_J__() && !this.this$0.hasStung();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return super.shouldContinueExecuting() && this.this$0.func_233678_J__() && !this.this$0.hasStung();
        }
    }

    class EnterBeehiveGoal
    extends PassiveGoal {
        final BeeEntity this$0;

        private EnterBeehiveGoal(BeeEntity beeEntity) {
            this.this$0 = beeEntity;
            super(beeEntity);
        }

        @Override
        public boolean canBeeStart() {
            TileEntity tileEntity;
            if (this.this$0.hasHive() && this.this$0.canEnterHive() && this.this$0.hivePos.withinDistance(this.this$0.getPositionVec(), 2.0) && (tileEntity = this.this$0.world.getTileEntity(this.this$0.hivePos)) instanceof BeehiveTileEntity) {
                BeehiveTileEntity beehiveTileEntity = (BeehiveTileEntity)tileEntity;
                if (!beehiveTileEntity.isFullOfBees()) {
                    return false;
                }
                this.this$0.hivePos = null;
            }
            return true;
        }

        @Override
        public boolean canBeeContinue() {
            return true;
        }

        @Override
        public void startExecuting() {
            TileEntity tileEntity = this.this$0.world.getTileEntity(this.this$0.hivePos);
            if (tileEntity instanceof BeehiveTileEntity) {
                BeehiveTileEntity beehiveTileEntity = (BeehiveTileEntity)tileEntity;
                beehiveTileEntity.tryEnterHive(this.this$0, this.this$0.hasNectar());
            }
        }
    }

    class PollinateGoal
    extends PassiveGoal {
        private final Predicate<BlockState> flowerPredicate;
        private int pollinationTicks;
        private int lastPollinationTick;
        private boolean running;
        private Vector3d nextTarget;
        private int ticks;
        final BeeEntity this$0;

        PollinateGoal(BeeEntity beeEntity) {
            this.this$0 = beeEntity;
            super(beeEntity);
            this.flowerPredicate = PollinateGoal::lambda$new$0;
            this.pollinationTicks = 0;
            this.lastPollinationTick = 0;
            this.ticks = 0;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canBeeStart() {
            if (this.this$0.remainingCooldownBeforeLocatingNewFlower > 0) {
                return true;
            }
            if (this.this$0.hasNectar()) {
                return true;
            }
            if (this.this$0.world.isRaining()) {
                return true;
            }
            if (BeeEntity.access$1500(this.this$0).nextFloat() < 0.7f) {
                return true;
            }
            Optional<BlockPos> optional = this.getFlower();
            if (optional.isPresent()) {
                this.this$0.savedFlowerPos = optional.get();
                BeeEntity.access$1600(this.this$0).tryMoveToXYZ((double)this.this$0.savedFlowerPos.getX() + 0.5, (double)this.this$0.savedFlowerPos.getY() + 0.5, (double)this.this$0.savedFlowerPos.getZ() + 0.5, 1.2f);
                return false;
            }
            return true;
        }

        @Override
        public boolean canBeeContinue() {
            if (!this.running) {
                return true;
            }
            if (!this.this$0.hasFlower()) {
                return true;
            }
            if (this.this$0.world.isRaining()) {
                return true;
            }
            if (this.completedPollination()) {
                return BeeEntity.access$1700(this.this$0).nextFloat() < 0.2f;
            }
            if (this.this$0.ticksExisted % 20 == 0 && !this.this$0.isFlowers(this.this$0.savedFlowerPos)) {
                this.this$0.savedFlowerPos = null;
                return true;
            }
            return false;
        }

        private boolean completedPollination() {
            return this.pollinationTicks > 400;
        }

        private boolean isRunning() {
            return this.running;
        }

        private void cancel() {
            this.running = false;
        }

        @Override
        public void startExecuting() {
            this.pollinationTicks = 0;
            this.ticks = 0;
            this.lastPollinationTick = 0;
            this.running = true;
            this.this$0.resetTicksWithoutNectar();
        }

        @Override
        public void resetTask() {
            if (this.completedPollination()) {
                this.this$0.setHasNectar(false);
            }
            this.running = false;
            BeeEntity.access$1800(this.this$0).clearPath();
            this.this$0.remainingCooldownBeforeLocatingNewFlower = 200;
        }

        @Override
        public void tick() {
            ++this.ticks;
            if (this.ticks > 600) {
                this.this$0.savedFlowerPos = null;
            } else {
                Vector3d vector3d = Vector3d.copyCenteredHorizontally(this.this$0.savedFlowerPos).add(0.0, 0.6f, 0.0);
                if (vector3d.distanceTo(this.this$0.getPositionVec()) > 1.0) {
                    this.nextTarget = vector3d;
                    this.moveToNextTarget();
                } else {
                    if (this.nextTarget == null) {
                        this.nextTarget = vector3d;
                    }
                    boolean bl = this.this$0.getPositionVec().distanceTo(this.nextTarget) <= 0.1;
                    boolean bl2 = true;
                    if (!bl && this.ticks > 600) {
                        this.this$0.savedFlowerPos = null;
                    } else {
                        if (bl) {
                            boolean bl3;
                            boolean bl4 = bl3 = BeeEntity.access$1900(this.this$0).nextInt(25) == 0;
                            if (bl3) {
                                this.nextTarget = new Vector3d(vector3d.getX() + (double)this.getRandomOffset(), vector3d.getY(), vector3d.getZ() + (double)this.getRandomOffset());
                                BeeEntity.access$2000(this.this$0).clearPath();
                            } else {
                                bl2 = false;
                            }
                            this.this$0.getLookController().setLookPosition(vector3d.getX(), vector3d.getY(), vector3d.getZ());
                        }
                        if (bl2) {
                            this.moveToNextTarget();
                        }
                        ++this.pollinationTicks;
                        if (BeeEntity.access$2100(this.this$0).nextFloat() < 0.05f && this.pollinationTicks > this.lastPollinationTick + 60) {
                            this.lastPollinationTick = this.pollinationTicks;
                            this.this$0.playSound(SoundEvents.ENTITY_BEE_POLLINATE, 1.0f, 1.0f);
                        }
                    }
                }
            }
        }

        private void moveToNextTarget() {
            this.this$0.getMoveHelper().setMoveTo(this.nextTarget.getX(), this.nextTarget.getY(), this.nextTarget.getZ(), 0.35f);
        }

        private float getRandomOffset() {
            return (BeeEntity.access$2200(this.this$0).nextFloat() * 2.0f - 1.0f) * 0.33333334f;
        }

        private Optional<BlockPos> getFlower() {
            return this.findFlower(this.flowerPredicate, 5.0);
        }

        private Optional<BlockPos> findFlower(Predicate<BlockState> predicate, double d) {
            BlockPos blockPos = this.this$0.getPosition();
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            int n = 0;
            while ((double)n <= d) {
                int n2 = 0;
                while ((double)n2 < d) {
                    int n3 = 0;
                    while (n3 <= n2) {
                        int n4;
                        int n5 = n4 = n3 < n2 && n3 > -n2 ? n2 : 0;
                        while (n4 <= n2) {
                            mutable.setAndOffset(blockPos, n3, n - 1, n4);
                            if (blockPos.withinDistance(mutable, d) && predicate.test(this.this$0.world.getBlockState(mutable))) {
                                return Optional.of(mutable);
                            }
                            n4 = n4 > 0 ? -n4 : 1 - n4;
                        }
                        n3 = n3 > 0 ? -n3 : 1 - n3;
                    }
                    ++n2;
                }
                n = n > 0 ? -n : 1 - n;
            }
            return Optional.empty();
        }

        private static boolean lambda$new$0(BlockState blockState) {
            if (blockState.isIn(BlockTags.TALL_FLOWERS)) {
                if (blockState.isIn(Blocks.SUNFLOWER)) {
                    return blockState.get(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER;
                }
                return false;
            }
            return blockState.isIn(BlockTags.SMALL_FLOWERS);
        }
    }

    class UpdateBeehiveGoal
    extends PassiveGoal {
        final BeeEntity this$0;

        private UpdateBeehiveGoal(BeeEntity beeEntity) {
            this.this$0 = beeEntity;
            super(beeEntity);
        }

        @Override
        public boolean canBeeStart() {
            return this.this$0.remainingCooldownBeforeLocatingNewHive == 0 && !this.this$0.hasHive() && this.this$0.canEnterHive();
        }

        @Override
        public boolean canBeeContinue() {
            return true;
        }

        @Override
        public void startExecuting() {
            this.this$0.remainingCooldownBeforeLocatingNewHive = 200;
            List<BlockPos> list = this.getNearbyFreeHives();
            if (!list.isEmpty()) {
                for (BlockPos blockPos : list) {
                    if (this.this$0.findBeehiveGoal.isPossibleHive(blockPos)) continue;
                    this.this$0.hivePos = blockPos;
                    return;
                }
                this.this$0.findBeehiveGoal.clearPossibleHives();
                this.this$0.hivePos = list.get(0);
            }
        }

        private List<BlockPos> getNearbyFreeHives() {
            BlockPos blockPos = this.this$0.getPosition();
            PointOfInterestManager pointOfInterestManager = ((ServerWorld)this.this$0.world).getPointOfInterestManager();
            Stream<PointOfInterest> stream = pointOfInterestManager.func_219146_b(UpdateBeehiveGoal::lambda$getNearbyFreeHives$0, blockPos, 20, PointOfInterestManager.Status.ANY);
            return stream.map(PointOfInterest::getPos).filter(this::lambda$getNearbyFreeHives$1).sorted(Comparator.comparingDouble(arg_0 -> UpdateBeehiveGoal.lambda$getNearbyFreeHives$2(blockPos, arg_0))).collect(Collectors.toList());
        }

        private static double lambda$getNearbyFreeHives$2(BlockPos blockPos, BlockPos blockPos2) {
            return blockPos2.distanceSq(blockPos);
        }

        private boolean lambda$getNearbyFreeHives$1(BlockPos blockPos) {
            return this.this$0.doesHiveHaveSpace(blockPos);
        }

        private static boolean lambda$getNearbyFreeHives$0(PointOfInterestType pointOfInterestType) {
            return pointOfInterestType == PointOfInterestType.BEEHIVE || pointOfInterestType == PointOfInterestType.BEE_NEST;
        }
    }

    public class FindBeehiveGoal
    extends PassiveGoal {
        private int ticks;
        private List<BlockPos> possibleHives;
        @Nullable
        private Path path;
        private int field_234183_f_;
        final BeeEntity this$0;

        FindBeehiveGoal(BeeEntity beeEntity) {
            this.this$0 = beeEntity;
            super(beeEntity);
            this.ticks = this.this$0.world.rand.nextInt(10);
            this.possibleHives = Lists.newArrayList();
            this.path = null;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canBeeStart() {
            return this.this$0.hivePos != null && !this.this$0.detachHome() && this.this$0.canEnterHive() && !this.isCloseEnough(this.this$0.hivePos) && this.this$0.world.getBlockState(this.this$0.hivePos).isIn(BlockTags.BEEHIVES);
        }

        @Override
        public boolean canBeeContinue() {
            return this.canBeeStart();
        }

        @Override
        public void startExecuting() {
            this.ticks = 0;
            this.field_234183_f_ = 0;
            super.startExecuting();
        }

        @Override
        public void resetTask() {
            this.ticks = 0;
            this.field_234183_f_ = 0;
            BeeEntity.access$000(this.this$0).clearPath();
            BeeEntity.access$100(this.this$0).resetRangeMultiplier();
        }

        @Override
        public void tick() {
            if (this.this$0.hivePos != null) {
                ++this.ticks;
                if (this.ticks > 600) {
                    this.makeChosenHivePossibleHive();
                } else if (!BeeEntity.access$200(this.this$0).hasPath()) {
                    if (!this.this$0.isWithinDistance(this.this$0.hivePos, 1)) {
                        if (this.this$0.isTooFar(this.this$0.hivePos)) {
                            this.reset();
                        } else {
                            this.this$0.startMovingTo(this.this$0.hivePos);
                        }
                    } else {
                        boolean bl = this.startMovingToFar(this.this$0.hivePos);
                        if (!bl) {
                            this.makeChosenHivePossibleHive();
                        } else if (this.path != null && BeeEntity.access$300(this.this$0).getPath().isSamePath(this.path)) {
                            ++this.field_234183_f_;
                            if (this.field_234183_f_ > 60) {
                                this.reset();
                                this.field_234183_f_ = 0;
                            }
                        } else {
                            this.path = BeeEntity.access$400(this.this$0).getPath();
                        }
                    }
                }
            }
        }

        private boolean startMovingToFar(BlockPos blockPos) {
            BeeEntity.access$500(this.this$0).setRangeMultiplier(10.0f);
            BeeEntity.access$600(this.this$0).tryMoveToXYZ(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1.0);
            return BeeEntity.access$700(this.this$0).getPath() != null && BeeEntity.access$800(this.this$0).getPath().reachesTarget();
        }

        private boolean isPossibleHive(BlockPos blockPos) {
            return this.possibleHives.contains(blockPos);
        }

        private void addPossibleHives(BlockPos blockPos) {
            this.possibleHives.add(blockPos);
            while (this.possibleHives.size() > 3) {
                this.possibleHives.remove(0);
            }
        }

        private void clearPossibleHives() {
            this.possibleHives.clear();
        }

        private void makeChosenHivePossibleHive() {
            if (this.this$0.hivePos != null) {
                this.addPossibleHives(this.this$0.hivePos);
            }
            this.reset();
        }

        private void reset() {
            this.this$0.hivePos = null;
            this.this$0.remainingCooldownBeforeLocatingNewHive = 200;
        }

        private boolean isCloseEnough(BlockPos blockPos) {
            if (this.this$0.isWithinDistance(blockPos, 1)) {
                return false;
            }
            Path path = BeeEntity.access$900(this.this$0).getPath();
            return path != null && path.getTarget().equals(blockPos) && path.reachesTarget() && path.isFinished();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return super.shouldContinueExecuting();
        }

        @Override
        public boolean shouldExecute() {
            return super.shouldExecute();
        }
    }

    public class FindFlowerGoal
    extends PassiveGoal {
        private int ticks;
        final BeeEntity this$0;

        FindFlowerGoal(BeeEntity beeEntity) {
            this.this$0 = beeEntity;
            super(beeEntity);
            this.ticks = this.this$0.world.rand.nextInt(10);
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canBeeStart() {
            return this.this$0.savedFlowerPos != null && !this.this$0.detachHome() && this.shouldMoveToFlower() && this.this$0.isFlowers(this.this$0.savedFlowerPos) && !this.this$0.isWithinDistance(this.this$0.savedFlowerPos, 1);
        }

        @Override
        public boolean canBeeContinue() {
            return this.canBeeStart();
        }

        @Override
        public void startExecuting() {
            this.ticks = 0;
            super.startExecuting();
        }

        @Override
        public void resetTask() {
            this.ticks = 0;
            BeeEntity.access$1000(this.this$0).clearPath();
            BeeEntity.access$1100(this.this$0).resetRangeMultiplier();
        }

        @Override
        public void tick() {
            if (this.this$0.savedFlowerPos != null) {
                ++this.ticks;
                if (this.ticks > 600) {
                    this.this$0.savedFlowerPos = null;
                } else if (!BeeEntity.access$1200(this.this$0).hasPath()) {
                    if (this.this$0.isTooFar(this.this$0.savedFlowerPos)) {
                        this.this$0.savedFlowerPos = null;
                    } else {
                        this.this$0.startMovingTo(this.this$0.savedFlowerPos);
                    }
                }
            }
        }

        private boolean shouldMoveToFlower() {
            return this.this$0.ticksWithoutNectarSinceExitingHive > 2400;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return super.shouldContinueExecuting();
        }

        @Override
        public boolean shouldExecute() {
            return super.shouldExecute();
        }
    }

    class FindPollinationTargetGoal
    extends PassiveGoal {
        final BeeEntity this$0;

        private FindPollinationTargetGoal(BeeEntity beeEntity) {
            this.this$0 = beeEntity;
            super(beeEntity);
        }

        @Override
        public boolean canBeeStart() {
            if (this.this$0.getCropsGrownSincePollination() >= 10) {
                return true;
            }
            if (BeeEntity.access$1300(this.this$0).nextFloat() < 0.3f) {
                return true;
            }
            return this.this$0.hasNectar() && this.this$0.isHiveValid();
        }

        @Override
        public boolean canBeeContinue() {
            return this.canBeeStart();
        }

        @Override
        public void tick() {
            if (BeeEntity.access$1400(this.this$0).nextInt(30) == 0) {
                for (int i = 1; i <= 2; ++i) {
                    int n;
                    BlockPos blockPos = this.this$0.getPosition().down(i);
                    BlockState blockState = this.this$0.world.getBlockState(blockPos);
                    Block block = blockState.getBlock();
                    boolean bl = false;
                    IntegerProperty integerProperty = null;
                    if (!block.isIn(BlockTags.BEE_GROWABLES)) continue;
                    if (block instanceof CropsBlock) {
                        CropsBlock cropsBlock = (CropsBlock)block;
                        if (!cropsBlock.isMaxAge(blockState)) {
                            bl = true;
                            integerProperty = cropsBlock.getAgeProperty();
                        }
                    } else if (block instanceof StemBlock) {
                        int n2 = blockState.get(StemBlock.AGE);
                        if (n2 < 7) {
                            bl = true;
                            integerProperty = StemBlock.AGE;
                        }
                    } else if (block == Blocks.SWEET_BERRY_BUSH && (n = blockState.get(SweetBerryBushBlock.AGE).intValue()) < 3) {
                        bl = true;
                        integerProperty = SweetBerryBushBlock.AGE;
                    }
                    if (!bl) continue;
                    this.this$0.world.playEvent(2005, blockPos, 0);
                    this.this$0.world.setBlockState(blockPos, (BlockState)blockState.with(integerProperty, blockState.get(integerProperty) + 1));
                    this.this$0.addCropCounter();
                }
            }
        }
    }

    class WanderGoal
    extends Goal {
        final BeeEntity this$0;

        WanderGoal(BeeEntity beeEntity) {
            this.this$0 = beeEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            return BeeEntity.access$2300(this.this$0).noPath() && BeeEntity.access$2400(this.this$0).nextInt(10) == 0;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return BeeEntity.access$2500(this.this$0).hasPath();
        }

        @Override
        public void startExecuting() {
            Vector3d vector3d = this.getRandomLocation();
            if (vector3d != null) {
                BeeEntity.access$2700(this.this$0).setPath(BeeEntity.access$2600(this.this$0).getPathToPos(new BlockPos(vector3d), 1), 1.0);
            }
        }

        @Nullable
        private Vector3d getRandomLocation() {
            Vector3d vector3d;
            if (this.this$0.isHiveValid() && !this.this$0.isWithinDistance(this.this$0.hivePos, 1)) {
                Vector3d vector3d2 = Vector3d.copyCentered(this.this$0.hivePos);
                vector3d = vector3d2.subtract(this.this$0.getPositionVec()).normalize();
            } else {
                vector3d = this.this$0.getLook(0.0f);
            }
            int n = 8;
            Vector3d vector3d3 = RandomPositionGenerator.findAirTarget(this.this$0, 8, 7, vector3d, 1.5707964f, 2, 1);
            return vector3d3 != null ? vector3d3 : RandomPositionGenerator.findGroundTarget(this.this$0, 8, 4, -2, vector3d, 1.5707963705062866);
        }
    }

    class AngerGoal
    extends HurtByTargetGoal {
        final BeeEntity this$0;

        AngerGoal(BeeEntity beeEntity, BeeEntity beeEntity2) {
            this.this$0 = beeEntity;
            super(beeEntity2, new Class[0]);
        }

        @Override
        public boolean shouldContinueExecuting() {
            return this.this$0.func_233678_J__() && super.shouldContinueExecuting();
        }

        @Override
        protected void setAttackTarget(MobEntity mobEntity, LivingEntity livingEntity) {
            if (mobEntity instanceof BeeEntity && this.goalOwner.canEntityBeSeen(livingEntity)) {
                mobEntity.setAttackTarget(livingEntity);
            }
        }
    }

    static class AttackPlayerGoal
    extends NearestAttackableTargetGoal<PlayerEntity> {
        AttackPlayerGoal(BeeEntity beeEntity) {
            super(beeEntity, PlayerEntity.class, 10, true, false, beeEntity::func_233680_b_);
        }

        @Override
        public boolean shouldExecute() {
            return this.canSting() && super.shouldExecute();
        }

        @Override
        public boolean shouldContinueExecuting() {
            boolean bl = this.canSting();
            if (bl && this.goalOwner.getAttackTarget() != null) {
                return super.shouldContinueExecuting();
            }
            this.target = null;
            return true;
        }

        private boolean canSting() {
            BeeEntity beeEntity = (BeeEntity)this.goalOwner;
            return beeEntity.func_233678_J__() && !beeEntity.hasStung();
        }
    }

    abstract class PassiveGoal
    extends Goal {
        final BeeEntity this$0;

        private PassiveGoal(BeeEntity beeEntity) {
            this.this$0 = beeEntity;
        }

        public abstract boolean canBeeStart();

        public abstract boolean canBeeContinue();

        @Override
        public boolean shouldExecute() {
            return this.canBeeStart() && !this.this$0.func_233678_J__();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return this.canBeeContinue() && !this.this$0.func_233678_J__();
        }
    }
}

