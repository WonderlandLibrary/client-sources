/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarrotBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.JumpController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class RabbitEntity
extends AnimalEntity {
    private static final DataParameter<Integer> RABBIT_TYPE = EntityDataManager.createKey(RabbitEntity.class, DataSerializers.VARINT);
    private static final ResourceLocation KILLER_BUNNY = new ResourceLocation("killer_bunny");
    private int jumpTicks;
    private int jumpDuration;
    private boolean wasOnGround;
    private int currentMoveTypeDuration;
    private int carrotTicks;

    public RabbitEntity(EntityType<? extends RabbitEntity> entityType, World world) {
        super((EntityType<? extends AnimalEntity>)entityType, world);
        this.jumpController = new JumpHelperController(this, this);
        this.moveController = new MoveHelperController(this);
        this.setMovementSpeed(0.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.2));
        this.goalSelector.addGoal(2, new BreedGoal(this, 0.8));
        this.goalSelector.addGoal(3, new TemptGoal((CreatureEntity)this, 1.0, Ingredient.fromItems(Items.CARROT, Items.GOLDEN_CARROT, Blocks.DANDELION), false));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0f, 2.2, 2.2));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<WolfEntity>(this, WolfEntity.class, 10.0f, 2.2, 2.2));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<MonsterEntity>(this, MonsterEntity.class, 4.0f, 2.2, 2.2));
        this.goalSelector.addGoal(5, new RaidFarmGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.6));
        this.goalSelector.addGoal(11, new LookAtGoal(this, PlayerEntity.class, 10.0f));
    }

    @Override
    protected float getJumpUpwardsMotion() {
        if (!(this.collidedHorizontally || this.moveController.isUpdating() && this.moveController.getY() > this.getPosY() + 0.5)) {
            Path path = this.navigator.getPath();
            if (path != null && !path.isFinished()) {
                Vector3d vector3d = path.getPosition(this);
                if (vector3d.y > this.getPosY() + 0.5) {
                    return 0.5f;
                }
            }
            return this.moveController.getSpeed() <= 0.6 ? 0.2f : 0.3f;
        }
        return 0.5f;
    }

    @Override
    protected void jump() {
        double d;
        super.jump();
        double d2 = this.moveController.getSpeed();
        if (d2 > 0.0 && (d = RabbitEntity.horizontalMag(this.getMotion())) < 0.01) {
            this.moveRelative(0.1f, new Vector3d(0.0, 0.0, 1.0));
        }
        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)1);
        }
    }

    public float getJumpCompletion(float f) {
        return this.jumpDuration == 0 ? 0.0f : ((float)this.jumpTicks + f) / (float)this.jumpDuration;
    }

    public void setMovementSpeed(double d) {
        this.getNavigator().setSpeed(d);
        this.moveController.setMoveTo(this.moveController.getX(), this.moveController.getY(), this.moveController.getZ(), d);
    }

    @Override
    public void setJumping(boolean bl) {
        super.setJumping(bl);
        if (bl) {
            this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 0.8f);
        }
    }

    public void startJumping() {
        this.setJumping(false);
        this.jumpDuration = 10;
        this.jumpTicks = 0;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(RABBIT_TYPE, 0);
    }

    @Override
    public void updateAITasks() {
        if (this.currentMoveTypeDuration > 0) {
            --this.currentMoveTypeDuration;
        }
        if (this.carrotTicks > 0) {
            this.carrotTicks -= this.rand.nextInt(3);
            if (this.carrotTicks < 0) {
                this.carrotTicks = 0;
            }
        }
        if (this.onGround) {
            Object object;
            if (!this.wasOnGround) {
                this.setJumping(true);
                this.checkLandingDelay();
            }
            if (this.getRabbitType() == 99 && this.currentMoveTypeDuration == 0 && (object = this.getAttackTarget()) != null && this.getDistanceSq((Entity)object) < 16.0) {
                this.calculateRotationYaw(((Entity)object).getPosX(), ((Entity)object).getPosZ());
                this.moveController.setMoveTo(((Entity)object).getPosX(), ((Entity)object).getPosY(), ((Entity)object).getPosZ(), this.moveController.getSpeed());
                this.startJumping();
                this.wasOnGround = true;
            }
            if (!((JumpHelperController)(object = (JumpHelperController)this.jumpController)).getIsJumping()) {
                if (this.moveController.isUpdating() && this.currentMoveTypeDuration == 0) {
                    Path path = this.navigator.getPath();
                    Vector3d vector3d = new Vector3d(this.moveController.getX(), this.moveController.getY(), this.moveController.getZ());
                    if (path != null && !path.isFinished()) {
                        vector3d = path.getPosition(this);
                    }
                    this.calculateRotationYaw(vector3d.x, vector3d.z);
                    this.startJumping();
                }
            } else if (!((JumpHelperController)object).canJump()) {
                this.enableJumpControl();
            }
        }
        this.wasOnGround = this.onGround;
    }

    @Override
    public boolean func_230269_aK_() {
        return true;
    }

    private void calculateRotationYaw(double d, double d2) {
        this.rotationYaw = (float)(MathHelper.atan2(d2 - this.getPosZ(), d - this.getPosX()) * 57.2957763671875) - 90.0f;
    }

    private void enableJumpControl() {
        ((JumpHelperController)this.jumpController).setCanJump(false);
    }

    private void disableJumpControl() {
        ((JumpHelperController)this.jumpController).setCanJump(true);
    }

    private void updateMoveTypeDuration() {
        this.currentMoveTypeDuration = this.moveController.getSpeed() < 2.2 ? 10 : 1;
    }

    private void checkLandingDelay() {
        this.updateMoveTypeDuration();
        this.disableJumpControl();
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.jumpTicks != this.jumpDuration) {
            ++this.jumpTicks;
        } else if (this.jumpDuration != 0) {
            this.jumpTicks = 0;
            this.jumpDuration = 0;
            this.setJumping(true);
        }
    }

    public static AttributeModifierMap.MutableAttribute func_234224_eJ_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 3.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3f);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("RabbitType", this.getRabbitType());
        compoundNBT.putInt("MoreCarrotTicks", this.carrotTicks);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.setRabbitType(compoundNBT.getInt("RabbitType"));
        this.carrotTicks = compoundNBT.getInt("MoreCarrotTicks");
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.ENTITY_RABBIT_JUMP;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_RABBIT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_RABBIT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_RABBIT_DEATH;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity2) {
        if (this.getRabbitType() == 99) {
            this.playSound(SoundEvents.ENTITY_RABBIT_ATTACK, 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            return entity2.attackEntityFrom(DamageSource.causeMobDamage(this), 8.0f);
        }
        return entity2.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0f);
    }

    @Override
    public SoundCategory getSoundCategory() {
        return this.getRabbitType() == 99 ? SoundCategory.HOSTILE : SoundCategory.NEUTRAL;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        return this.isInvulnerableTo(damageSource) ? false : super.attackEntityFrom(damageSource, f);
    }

    private boolean isRabbitBreedingItem(Item item) {
        return item == Items.CARROT || item == Items.GOLDEN_CARROT || item == Blocks.DANDELION.asItem();
    }

    @Override
    public RabbitEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        RabbitEntity rabbitEntity = EntityType.RABBIT.create(serverWorld);
        int n = this.getRandomRabbitType(serverWorld);
        if (this.rand.nextInt(20) != 0) {
            n = ageableEntity instanceof RabbitEntity && this.rand.nextBoolean() ? ((RabbitEntity)ageableEntity).getRabbitType() : this.getRabbitType();
        }
        rabbitEntity.setRabbitType(n);
        return rabbitEntity;
    }

    @Override
    public boolean isBreedingItem(ItemStack itemStack) {
        return this.isRabbitBreedingItem(itemStack.getItem());
    }

    public int getRabbitType() {
        return this.dataManager.get(RABBIT_TYPE);
    }

    public void setRabbitType(int n) {
        if (n == 99) {
            this.getAttribute(Attributes.ARMOR).setBaseValue(8.0);
            this.goalSelector.addGoal(4, new EvilAttackGoal(this));
            this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]).setCallsForHelp(new Class[0]));
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<PlayerEntity>((MobEntity)this, PlayerEntity.class, true));
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<WolfEntity>((MobEntity)this, WolfEntity.class, true));
            if (!this.hasCustomName()) {
                this.setCustomName(new TranslationTextComponent(Util.makeTranslationKey("entity", KILLER_BUNNY)));
            }
        }
        this.dataManager.set(RABBIT_TYPE, n);
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        int n = this.getRandomRabbitType(iServerWorld);
        if (iLivingEntityData instanceof RabbitData) {
            n = ((RabbitData)iLivingEntityData).typeData;
        } else {
            iLivingEntityData = new RabbitData(n);
        }
        this.setRabbitType(n);
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    private int getRandomRabbitType(IWorld iWorld) {
        Biome biome = iWorld.getBiome(this.getPosition());
        int n = this.rand.nextInt(100);
        if (biome.getPrecipitation() == Biome.RainType.SNOW) {
            return n < 80 ? 1 : 3;
        }
        if (biome.getCategory() == Biome.Category.DESERT) {
            return 1;
        }
        return n < 50 ? 0 : (n < 90 ? 5 : 2);
    }

    public static boolean func_223321_c(EntityType<RabbitEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        BlockState blockState = iWorld.getBlockState(blockPos.down());
        return (blockState.isIn(Blocks.GRASS_BLOCK) || blockState.isIn(Blocks.SNOW) || blockState.isIn(Blocks.SAND)) && iWorld.getLightSubtracted(blockPos, 0) > 8;
    }

    private boolean isCarrotEaten() {
        return this.carrotTicks == 0;
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 1) {
            this.func_233569_aL_();
            this.jumpDuration = 10;
            this.jumpTicks = 0;
        } else {
            super.handleStatusUpdate(by);
        }
    }

    @Override
    public Vector3d func_241205_ce_() {
        return new Vector3d(0.0, 0.6f * this.getEyeHeight(), this.getWidth() * 0.4f);
    }

    @Override
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return this.func_241840_a(serverWorld, ageableEntity);
    }

    static boolean access$000(RabbitEntity rabbitEntity) {
        return rabbitEntity.onGround;
    }

    static boolean access$100(RabbitEntity rabbitEntity) {
        return rabbitEntity.isJumping;
    }

    static JumpController access$200(RabbitEntity rabbitEntity) {
        return rabbitEntity.jumpController;
    }

    public class JumpHelperController
    extends JumpController {
        private final RabbitEntity rabbit;
        private boolean canJump;
        final RabbitEntity this$0;

        public JumpHelperController(RabbitEntity rabbitEntity, RabbitEntity rabbitEntity2) {
            this.this$0 = rabbitEntity;
            super(rabbitEntity2);
            this.rabbit = rabbitEntity2;
        }

        public boolean getIsJumping() {
            return this.isJumping;
        }

        public boolean canJump() {
            return this.canJump;
        }

        public void setCanJump(boolean bl) {
            this.canJump = bl;
        }

        @Override
        public void tick() {
            if (this.isJumping) {
                this.rabbit.startJumping();
                this.isJumping = false;
            }
        }
    }

    static class MoveHelperController
    extends MovementController {
        private final RabbitEntity rabbit;
        private double nextJumpSpeed;

        public MoveHelperController(RabbitEntity rabbitEntity) {
            super(rabbitEntity);
            this.rabbit = rabbitEntity;
        }

        @Override
        public void tick() {
            if (RabbitEntity.access$000(this.rabbit) && !RabbitEntity.access$100(this.rabbit) && !((JumpHelperController)RabbitEntity.access$200(this.rabbit)).getIsJumping()) {
                this.rabbit.setMovementSpeed(0.0);
            } else if (this.isUpdating()) {
                this.rabbit.setMovementSpeed(this.nextJumpSpeed);
            }
            super.tick();
        }

        @Override
        public void setMoveTo(double d, double d2, double d3, double d4) {
            if (this.rabbit.isInWater()) {
                d4 = 1.5;
            }
            super.setMoveTo(d, d2, d3, d4);
            if (d4 > 0.0) {
                this.nextJumpSpeed = d4;
            }
        }
    }

    static class PanicGoal
    extends net.minecraft.entity.ai.goal.PanicGoal {
        private final RabbitEntity rabbit;

        public PanicGoal(RabbitEntity rabbitEntity, double d) {
            super(rabbitEntity, d);
            this.rabbit = rabbitEntity;
        }

        @Override
        public void tick() {
            super.tick();
            this.rabbit.setMovementSpeed(this.speed);
        }
    }

    static class AvoidEntityGoal<T extends LivingEntity>
    extends net.minecraft.entity.ai.goal.AvoidEntityGoal<T> {
        private final RabbitEntity rabbit;

        public AvoidEntityGoal(RabbitEntity rabbitEntity, Class<T> clazz, float f, double d, double d2) {
            super(rabbitEntity, clazz, f, d, d2);
            this.rabbit = rabbitEntity;
        }

        @Override
        public boolean shouldExecute() {
            return this.rabbit.getRabbitType() != 99 && super.shouldExecute();
        }
    }

    static class RaidFarmGoal
    extends MoveToBlockGoal {
        private final RabbitEntity rabbit;
        private boolean wantsToRaid;
        private boolean canRaid;

        public RaidFarmGoal(RabbitEntity rabbitEntity) {
            super(rabbitEntity, 0.7f, 16);
            this.rabbit = rabbitEntity;
        }

        @Override
        public boolean shouldExecute() {
            if (this.runDelay <= 0) {
                if (!this.rabbit.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) {
                    return true;
                }
                this.canRaid = false;
                this.wantsToRaid = this.rabbit.isCarrotEaten();
                this.wantsToRaid = true;
            }
            return super.shouldExecute();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return this.canRaid && super.shouldContinueExecuting();
        }

        @Override
        public void tick() {
            super.tick();
            this.rabbit.getLookController().setLookPosition((double)this.destinationBlock.getX() + 0.5, this.destinationBlock.getY() + 1, (double)this.destinationBlock.getZ() + 0.5, 10.0f, this.rabbit.getVerticalFaceSpeed());
            if (this.getIsAboveDestination()) {
                World world = this.rabbit.world;
                BlockPos blockPos = this.destinationBlock.up();
                BlockState blockState = world.getBlockState(blockPos);
                Block block = blockState.getBlock();
                if (this.canRaid && block instanceof CarrotBlock) {
                    Integer n = blockState.get(CarrotBlock.AGE);
                    if (n == 0) {
                        world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 1);
                        world.destroyBlock(blockPos, true, this.rabbit);
                    } else {
                        world.setBlockState(blockPos, (BlockState)blockState.with(CarrotBlock.AGE, n - 1), 1);
                        world.playEvent(2001, blockPos, Block.getStateId(blockState));
                    }
                    this.rabbit.carrotTicks = 40;
                }
                this.canRaid = false;
                this.runDelay = 10;
            }
        }

        @Override
        protected boolean shouldMoveTo(IWorldReader iWorldReader, BlockPos blockPos) {
            BlockState blockState;
            Block block = iWorldReader.getBlockState(blockPos).getBlock();
            if (block == Blocks.FARMLAND && this.wantsToRaid && !this.canRaid && (block = (blockState = iWorldReader.getBlockState(blockPos = blockPos.up())).getBlock()) instanceof CarrotBlock && ((CarrotBlock)block).isMaxAge(blockState)) {
                this.canRaid = true;
                return false;
            }
            return true;
        }
    }

    static class EvilAttackGoal
    extends MeleeAttackGoal {
        public EvilAttackGoal(RabbitEntity rabbitEntity) {
            super(rabbitEntity, 1.4, true);
        }

        @Override
        protected double getAttackReachSqr(LivingEntity livingEntity) {
            return 4.0f + livingEntity.getWidth();
        }
    }

    public static class RabbitData
    extends AgeableEntity.AgeableData {
        public final int typeData;

        public RabbitData(int n) {
            super(1.0f);
            this.typeData = n;
        }
    }
}

