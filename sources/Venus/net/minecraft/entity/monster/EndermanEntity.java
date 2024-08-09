/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.ResetAngerGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EndermanEntity
extends MonsterEntity
implements IAngerable {
    private static final UUID ATTACKING_SPEED_BOOST_ID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final AttributeModifier ATTACKING_SPEED_BOOST = new AttributeModifier(ATTACKING_SPEED_BOOST_ID, "Attacking speed boost", (double)0.15f, AttributeModifier.Operation.ADDITION);
    private static final DataParameter<Optional<BlockState>> CARRIED_BLOCK = EntityDataManager.createKey(EndermanEntity.class, DataSerializers.OPTIONAL_BLOCK_STATE);
    private static final DataParameter<Boolean> SCREAMING = EntityDataManager.createKey(EndermanEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> field_226535_bx_ = EntityDataManager.createKey(EndermanEntity.class, DataSerializers.BOOLEAN);
    private static final Predicate<LivingEntity> field_213627_bA = EndermanEntity::lambda$static$0;
    private int field_226536_bz_ = Integer.MIN_VALUE;
    private int targetChangeTime;
    private static final RangedInteger field_234286_bz_ = TickRangeConverter.convertRange(20, 39);
    private int field_234284_bA_;
    private UUID field_234285_bB_;

    public EndermanEntity(EntityType<? extends EndermanEntity> entityType, World world) {
        super((EntityType<? extends MonsterEntity>)entityType, world);
        this.stepHeight = 1.0f;
        this.setPathPriority(PathNodeType.WATER, -1.0f);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new StareGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal((CreatureEntity)this, 1.0, 0.0f));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(10, new PlaceBlockGoal(this));
        this.goalSelector.addGoal(11, new TakeBlockGoal(this));
        this.targetSelector.addGoal(1, new FindPlayerGoal(this, this::func_233680_b_));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<EndermiteEntity>(this, EndermiteEntity.class, 10, true, false, field_213627_bA));
        this.targetSelector.addGoal(4, new ResetAngerGoal<EndermanEntity>(this, false));
    }

    public static AttributeModifierMap.MutableAttribute func_234287_m_() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 40.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3f).createMutableAttribute(Attributes.ATTACK_DAMAGE, 7.0).createMutableAttribute(Attributes.FOLLOW_RANGE, 64.0);
    }

    @Override
    public void setAttackTarget(@Nullable LivingEntity livingEntity) {
        super.setAttackTarget(livingEntity);
        ModifiableAttributeInstance modifiableAttributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (livingEntity == null) {
            this.targetChangeTime = 0;
            this.dataManager.set(SCREAMING, false);
            this.dataManager.set(field_226535_bx_, false);
            modifiableAttributeInstance.removeModifier(ATTACKING_SPEED_BOOST);
        } else {
            this.targetChangeTime = this.ticksExisted;
            this.dataManager.set(SCREAMING, true);
            if (!modifiableAttributeInstance.hasModifier(ATTACKING_SPEED_BOOST)) {
                modifiableAttributeInstance.applyNonPersistentModifier(ATTACKING_SPEED_BOOST);
            }
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(CARRIED_BLOCK, Optional.empty());
        this.dataManager.register(SCREAMING, false);
        this.dataManager.register(field_226535_bx_, false);
    }

    @Override
    public void func_230258_H__() {
        this.setAngerTime(field_234286_bz_.getRandomWithinRange(this.rand));
    }

    @Override
    public void setAngerTime(int n) {
        this.field_234284_bA_ = n;
    }

    @Override
    public int getAngerTime() {
        return this.field_234284_bA_;
    }

    @Override
    public void setAngerTarget(@Nullable UUID uUID) {
        this.field_234285_bB_ = uUID;
    }

    @Override
    public UUID getAngerTarget() {
        return this.field_234285_bB_;
    }

    public void func_226539_l_() {
        if (this.ticksExisted >= this.field_226536_bz_ + 400) {
            this.field_226536_bz_ = this.ticksExisted;
            if (!this.isSilent()) {
                this.world.playSound(this.getPosX(), this.getPosYEye(), this.getPosZ(), SoundEvents.ENTITY_ENDERMAN_STARE, this.getSoundCategory(), 2.5f, 1.0f, true);
            }
        }
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        if (SCREAMING.equals(dataParameter) && this.func_226537_et_() && this.world.isRemote) {
            this.func_226539_l_();
        }
        super.notifyDataManagerChange(dataParameter);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        BlockState blockState = this.getHeldBlockState();
        if (blockState != null) {
            compoundNBT.put("carriedBlockState", NBTUtil.writeBlockState(blockState));
        }
        this.writeAngerNBT(compoundNBT);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        BlockState blockState = null;
        if (compoundNBT.contains("carriedBlockState", 1) && (blockState = NBTUtil.readBlockState(compoundNBT.getCompound("carriedBlockState"))).isAir()) {
            blockState = null;
        }
        this.setHeldBlockState(blockState);
        this.readAngerNBT((ServerWorld)this.world, compoundNBT);
    }

    private boolean shouldAttackPlayer(PlayerEntity playerEntity) {
        ItemStack itemStack = playerEntity.inventory.armorInventory.get(3);
        if (itemStack.getItem() == Blocks.CARVED_PUMPKIN.asItem()) {
            return true;
        }
        Vector3d vector3d = playerEntity.getLook(1.0f).normalize();
        Vector3d vector3d2 = new Vector3d(this.getPosX() - playerEntity.getPosX(), this.getPosYEye() - playerEntity.getPosYEye(), this.getPosZ() - playerEntity.getPosZ());
        double d = vector3d2.length();
        double d2 = vector3d.dotProduct(vector3d2 = vector3d2.normalize());
        return d2 > 1.0 - 0.025 / d ? playerEntity.canEntityBeSeen(this) : false;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return 2.55f;
    }

    @Override
    public void livingTick() {
        if (this.world.isRemote) {
            for (int i = 0; i < 2; ++i) {
                this.world.addParticle(ParticleTypes.PORTAL, this.getPosXRandom(0.5), this.getPosYRandom() - 0.25, this.getPosZRandom(0.5), (this.rand.nextDouble() - 0.5) * 2.0, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5) * 2.0);
            }
        }
        this.isJumping = false;
        if (!this.world.isRemote) {
            this.func_241359_a_((ServerWorld)this.world, false);
        }
        super.livingTick();
    }

    @Override
    public boolean isWaterSensitive() {
        return false;
    }

    @Override
    protected void updateAITasks() {
        float f;
        if (this.world.isDaytime() && this.ticksExisted >= this.targetChangeTime + 600 && (f = this.getBrightness()) > 0.5f && this.world.canSeeSky(this.getPosition()) && this.rand.nextFloat() * 30.0f < (f - 0.4f) * 2.0f) {
            this.setAttackTarget(null);
            this.teleportRandomly();
        }
        super.updateAITasks();
    }

    protected boolean teleportRandomly() {
        if (!this.world.isRemote() && this.isAlive()) {
            double d = this.getPosX() + (this.rand.nextDouble() - 0.5) * 64.0;
            double d2 = this.getPosY() + (double)(this.rand.nextInt(64) - 32);
            double d3 = this.getPosZ() + (this.rand.nextDouble() - 0.5) * 64.0;
            return this.teleportTo(d, d2, d3);
        }
        return true;
    }

    private boolean teleportToEntity(Entity entity2) {
        Vector3d vector3d = new Vector3d(this.getPosX() - entity2.getPosX(), this.getPosYHeight(0.5) - entity2.getPosYEye(), this.getPosZ() - entity2.getPosZ());
        vector3d = vector3d.normalize();
        double d = 16.0;
        double d2 = this.getPosX() + (this.rand.nextDouble() - 0.5) * 8.0 - vector3d.x * 16.0;
        double d3 = this.getPosY() + (double)(this.rand.nextInt(16) - 8) - vector3d.y * 16.0;
        double d4 = this.getPosZ() + (this.rand.nextDouble() - 0.5) * 8.0 - vector3d.z * 16.0;
        return this.teleportTo(d2, d3, d4);
    }

    private boolean teleportTo(double d, double d2, double d3) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(d, d2, d3);
        while (mutable.getY() > 0 && !this.world.getBlockState(mutable).getMaterial().blocksMovement()) {
            mutable.move(Direction.DOWN);
        }
        BlockState blockState = this.world.getBlockState(mutable);
        boolean bl = blockState.getMaterial().blocksMovement();
        boolean bl2 = blockState.getFluidState().isTagged(FluidTags.WATER);
        if (bl && !bl2) {
            boolean bl3 = this.attemptTeleport(d, d2, d3, false);
            if (bl3 && !this.isSilent()) {
                this.world.playSound(null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, this.getSoundCategory(), 1.0f, 1.0f);
                this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            }
            return bl3;
        }
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isScreaming() ? SoundEvents.ENTITY_ENDERMAN_SCREAM : SoundEvents.ENTITY_ENDERMAN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_ENDERMAN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ENDERMAN_DEATH;
    }

    @Override
    protected void dropSpecialItems(DamageSource damageSource, int n, boolean bl) {
        super.dropSpecialItems(damageSource, n, bl);
        BlockState blockState = this.getHeldBlockState();
        if (blockState != null) {
            this.entityDropItem(blockState.getBlock());
        }
    }

    public void setHeldBlockState(@Nullable BlockState blockState) {
        this.dataManager.set(CARRIED_BLOCK, Optional.ofNullable(blockState));
    }

    @Nullable
    public BlockState getHeldBlockState() {
        return this.dataManager.get(CARRIED_BLOCK).orElse(null);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return true;
        }
        if (damageSource instanceof IndirectEntityDamageSource) {
            for (int i = 0; i < 64; ++i) {
                if (!this.teleportRandomly()) continue;
                return false;
            }
            return true;
        }
        boolean bl = super.attackEntityFrom(damageSource, f);
        if (!this.world.isRemote() && !(damageSource.getTrueSource() instanceof LivingEntity) && this.rand.nextInt(10) != 0) {
            this.teleportRandomly();
        }
        return bl;
    }

    public boolean isScreaming() {
        return this.dataManager.get(SCREAMING);
    }

    public boolean func_226537_et_() {
        return this.dataManager.get(field_226535_bx_);
    }

    public void func_226538_eu_() {
        this.dataManager.set(field_226535_bx_, true);
    }

    @Override
    public boolean preventDespawn() {
        return super.preventDespawn() || this.getHeldBlockState() != null;
    }

    private static boolean lambda$static$0(LivingEntity livingEntity) {
        return livingEntity instanceof EndermiteEntity && ((EndermiteEntity)livingEntity).isSpawnedByPlayer();
    }

    static class StareGoal
    extends Goal {
        private final EndermanEntity enderman;
        private LivingEntity targetPlayer;

        public StareGoal(EndermanEntity endermanEntity) {
            this.enderman = endermanEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            this.targetPlayer = this.enderman.getAttackTarget();
            if (!(this.targetPlayer instanceof PlayerEntity)) {
                return true;
            }
            double d = this.targetPlayer.getDistanceSq(this.enderman);
            return d > 256.0 ? false : this.enderman.shouldAttackPlayer((PlayerEntity)this.targetPlayer);
        }

        @Override
        public void startExecuting() {
            this.enderman.getNavigator().clearPath();
        }

        @Override
        public void tick() {
            this.enderman.getLookController().setLookPosition(this.targetPlayer.getPosX(), this.targetPlayer.getPosYEye(), this.targetPlayer.getPosZ());
        }
    }

    static class PlaceBlockGoal
    extends Goal {
        private final EndermanEntity enderman;

        public PlaceBlockGoal(EndermanEntity endermanEntity) {
            this.enderman = endermanEntity;
        }

        @Override
        public boolean shouldExecute() {
            if (this.enderman.getHeldBlockState() == null) {
                return true;
            }
            if (!this.enderman.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) {
                return true;
            }
            return this.enderman.getRNG().nextInt(2000) == 0;
        }

        @Override
        public void tick() {
            Random random2 = this.enderman.getRNG();
            World world = this.enderman.world;
            int n = MathHelper.floor(this.enderman.getPosX() - 1.0 + random2.nextDouble() * 2.0);
            int n2 = MathHelper.floor(this.enderman.getPosY() + random2.nextDouble() * 2.0);
            int n3 = MathHelper.floor(this.enderman.getPosZ() - 1.0 + random2.nextDouble() * 2.0);
            BlockPos blockPos = new BlockPos(n, n2, n3);
            BlockState blockState = world.getBlockState(blockPos);
            BlockPos blockPos2 = blockPos.down();
            BlockState blockState2 = world.getBlockState(blockPos2);
            BlockState blockState3 = this.enderman.getHeldBlockState();
            if (blockState3 != null && this.func_220836_a(world, blockPos, blockState3 = Block.getValidBlockForPosition(blockState3, this.enderman.world, blockPos), blockState, blockState2, blockPos2)) {
                world.setBlockState(blockPos, blockState3, 0);
                this.enderman.setHeldBlockState(null);
            }
        }

        private boolean func_220836_a(World world, BlockPos blockPos, BlockState blockState, BlockState blockState2, BlockState blockState3, BlockPos blockPos2) {
            return blockState2.isAir() && !blockState3.isAir() && !blockState3.isIn(Blocks.BEDROCK) && blockState3.hasOpaqueCollisionShape(world, blockPos2) && blockState.isValidPosition(world, blockPos) && world.getEntitiesWithinAABBExcludingEntity(this.enderman, AxisAlignedBB.fromVector(Vector3d.copy(blockPos))).isEmpty();
        }
    }

    static class TakeBlockGoal
    extends Goal {
        private final EndermanEntity enderman;

        public TakeBlockGoal(EndermanEntity endermanEntity) {
            this.enderman = endermanEntity;
        }

        @Override
        public boolean shouldExecute() {
            if (this.enderman.getHeldBlockState() != null) {
                return true;
            }
            if (!this.enderman.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) {
                return true;
            }
            return this.enderman.getRNG().nextInt(20) == 0;
        }

        @Override
        public void tick() {
            Random random2 = this.enderman.getRNG();
            World world = this.enderman.world;
            int n = MathHelper.floor(this.enderman.getPosX() - 2.0 + random2.nextDouble() * 4.0);
            int n2 = MathHelper.floor(this.enderman.getPosY() + random2.nextDouble() * 3.0);
            int n3 = MathHelper.floor(this.enderman.getPosZ() - 2.0 + random2.nextDouble() * 4.0);
            BlockPos blockPos = new BlockPos(n, n2, n3);
            BlockState blockState = world.getBlockState(blockPos);
            Block block = blockState.getBlock();
            Vector3d vector3d = new Vector3d((double)MathHelper.floor(this.enderman.getPosX()) + 0.5, (double)n2 + 0.5, (double)MathHelper.floor(this.enderman.getPosZ()) + 0.5);
            Vector3d vector3d2 = new Vector3d((double)n + 0.5, (double)n2 + 0.5, (double)n3 + 0.5);
            BlockRayTraceResult blockRayTraceResult = world.rayTraceBlocks(new RayTraceContext(vector3d, vector3d2, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, this.enderman));
            boolean bl = blockRayTraceResult.getPos().equals(blockPos);
            if (block.isIn(BlockTags.ENDERMAN_HOLDABLE) && bl) {
                world.removeBlock(blockPos, true);
                this.enderman.setHeldBlockState(blockState.getBlock().getDefaultState());
            }
        }
    }

    static class FindPlayerGoal
    extends NearestAttackableTargetGoal<PlayerEntity> {
        private final EndermanEntity enderman;
        private PlayerEntity player;
        private int aggroTime;
        private int teleportTime;
        private final EntityPredicate field_220791_m;
        private final EntityPredicate field_220792_n = new EntityPredicate().setLineOfSiteRequired();

        public FindPlayerGoal(EndermanEntity endermanEntity, @Nullable Predicate<LivingEntity> predicate) {
            super(endermanEntity, PlayerEntity.class, 10, false, false, predicate);
            this.enderman = endermanEntity;
            this.field_220791_m = new EntityPredicate().setDistance(this.getTargetDistance()).setCustomPredicate(arg_0 -> FindPlayerGoal.lambda$new$0(endermanEntity, arg_0));
        }

        @Override
        public boolean shouldExecute() {
            this.player = this.enderman.world.getClosestPlayer(this.field_220791_m, this.enderman);
            return this.player != null;
        }

        @Override
        public void startExecuting() {
            this.aggroTime = 5;
            this.teleportTime = 0;
            this.enderman.func_226538_eu_();
        }

        @Override
        public void resetTask() {
            this.player = null;
            super.resetTask();
        }

        @Override
        public boolean shouldContinueExecuting() {
            if (this.player != null) {
                if (!this.enderman.shouldAttackPlayer(this.player)) {
                    return true;
                }
                this.enderman.faceEntity(this.player, 10.0f, 10.0f);
                return false;
            }
            return this.nearestTarget != null && this.field_220792_n.canTarget(this.enderman, this.nearestTarget) ? true : super.shouldContinueExecuting();
        }

        @Override
        public void tick() {
            if (this.enderman.getAttackTarget() == null) {
                super.setNearestTarget(null);
            }
            if (this.player != null) {
                if (--this.aggroTime <= 0) {
                    this.nearestTarget = this.player;
                    this.player = null;
                    super.startExecuting();
                }
            } else {
                if (this.nearestTarget != null && !this.enderman.isPassenger()) {
                    if (this.enderman.shouldAttackPlayer((PlayerEntity)this.nearestTarget)) {
                        if (this.nearestTarget.getDistanceSq(this.enderman) < 16.0) {
                            this.enderman.teleportRandomly();
                        }
                        this.teleportTime = 0;
                    } else if (this.nearestTarget.getDistanceSq(this.enderman) > 256.0 && this.teleportTime++ >= 30 && this.enderman.teleportToEntity(this.nearestTarget)) {
                        this.teleportTime = 0;
                    }
                }
                super.tick();
            }
        }

        private static boolean lambda$new$0(EndermanEntity endermanEntity, LivingEntity livingEntity) {
            return endermanEntity.shouldAttackPlayer((PlayerEntity)livingEntity);
        }
    }
}

