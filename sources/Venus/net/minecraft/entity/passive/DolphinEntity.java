/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.DolphinLookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreatheAirGoal;
import net.minecraft.entity.ai.goal.DolphinJumpGoal;
import net.minecraft.entity.ai.goal.FindWaterGoal;
import net.minecraft.entity.ai.goal.FollowBoatGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;

public class DolphinEntity
extends WaterMobEntity {
    private static final DataParameter<BlockPos> TREASURE_POS = EntityDataManager.createKey(DolphinEntity.class, DataSerializers.BLOCK_POS);
    private static final DataParameter<Boolean> GOT_FISH = EntityDataManager.createKey(DolphinEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> MOISTNESS = EntityDataManager.createKey(DolphinEntity.class, DataSerializers.VARINT);
    private static final EntityPredicate field_213810_bA = new EntityPredicate().setDistance(10.0).allowFriendlyFire().allowInvulnerable().setLineOfSiteRequired();
    public static final Predicate<ItemEntity> ITEM_SELECTOR = DolphinEntity::lambda$static$0;

    public DolphinEntity(EntityType<? extends DolphinEntity> entityType, World world) {
        super((EntityType<? extends WaterMobEntity>)entityType, world);
        this.moveController = new MoveHelperController(this);
        this.lookController = new DolphinLookController(this, 10);
        this.setCanPickUpLoot(false);
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        this.setAir(this.getMaxAir());
        this.rotationPitch = 0.0f;
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected void updateAir(int n) {
    }

    public void setTreasurePos(BlockPos blockPos) {
        this.dataManager.set(TREASURE_POS, blockPos);
    }

    public BlockPos getTreasurePos() {
        return this.dataManager.get(TREASURE_POS);
    }

    public boolean hasGotFish() {
        return this.dataManager.get(GOT_FISH);
    }

    public void setGotFish(boolean bl) {
        this.dataManager.set(GOT_FISH, bl);
    }

    public int getMoistness() {
        return this.dataManager.get(MOISTNESS);
    }

    public void setMoistness(int n) {
        this.dataManager.set(MOISTNESS, n);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(TREASURE_POS, BlockPos.ZERO);
        this.dataManager.register(GOT_FISH, false);
        this.dataManager.register(MOISTNESS, 2400);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("TreasurePosX", this.getTreasurePos().getX());
        compoundNBT.putInt("TreasurePosY", this.getTreasurePos().getY());
        compoundNBT.putInt("TreasurePosZ", this.getTreasurePos().getZ());
        compoundNBT.putBoolean("GotFish", this.hasGotFish());
        compoundNBT.putInt("Moistness", this.getMoistness());
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        int n = compoundNBT.getInt("TreasurePosX");
        int n2 = compoundNBT.getInt("TreasurePosY");
        int n3 = compoundNBT.getInt("TreasurePosZ");
        this.setTreasurePos(new BlockPos(n, n2, n3));
        super.readAdditional(compoundNBT);
        this.setGotFish(compoundNBT.getBoolean("GotFish"));
        this.setMoistness(compoundNBT.getInt("Moistness"));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new BreatheAirGoal(this));
        this.goalSelector.addGoal(0, new FindWaterGoal(this));
        this.goalSelector.addGoal(1, new SwimToTreasureGoal(this));
        this.goalSelector.addGoal(2, new SwimWithPlayerGoal(this, 4.0));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0, 10));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.addGoal(5, new DolphinJumpGoal(this, 10));
        this.goalSelector.addGoal(6, new MeleeAttackGoal(this, 1.2f, true));
        this.goalSelector.addGoal(8, new PlayWithItemsGoal(this));
        this.goalSelector.addGoal(8, new FollowBoatGoal(this));
        this.goalSelector.addGoal(9, new AvoidEntityGoal<GuardianEntity>(this, GuardianEntity.class, 8.0f, 1.0, 1.0));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, GuardianEntity.class).setCallsForHelp(new Class[0]));
    }

    public static AttributeModifierMap.MutableAttribute func_234190_eK_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 1.2f).createMutableAttribute(Attributes.ATTACK_DAMAGE, 3.0);
    }

    @Override
    protected PathNavigator createNavigator(World world) {
        return new SwimmerPathNavigator(this, world);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity2) {
        boolean bl = entity2.attackEntityFrom(DamageSource.causeMobDamage(this), (int)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
        if (bl) {
            this.applyEnchantments(this, entity2);
            this.playSound(SoundEvents.ENTITY_DOLPHIN_ATTACK, 1.0f, 1.0f);
        }
        return bl;
    }

    @Override
    public int getMaxAir() {
        return 1;
    }

    @Override
    protected int determineNextAir(int n) {
        return this.getMaxAir();
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return 0.3f;
    }

    @Override
    public int getVerticalFaceSpeed() {
        return 0;
    }

    @Override
    public int getHorizontalFaceSpeed() {
        return 0;
    }

    @Override
    protected boolean canBeRidden(Entity entity2) {
        return false;
    }

    @Override
    public boolean canPickUpItem(ItemStack itemStack) {
        EquipmentSlotType equipmentSlotType = MobEntity.getSlotForItemStack(itemStack);
        if (!this.getItemStackFromSlot(equipmentSlotType).isEmpty()) {
            return true;
        }
        return equipmentSlotType == EquipmentSlotType.MAINHAND && super.canPickUpItem(itemStack);
    }

    @Override
    protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
        ItemStack itemStack;
        if (this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty() && this.canEquipItem(itemStack = itemEntity.getItem())) {
            this.triggerItemPickupTrigger(itemEntity);
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemStack);
            this.inventoryHandsDropChances[EquipmentSlotType.MAINHAND.getIndex()] = 2.0f;
            this.onItemPickup(itemEntity, itemStack.getCount());
            itemEntity.remove();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAIDisabled()) {
            this.setAir(this.getMaxAir());
        } else {
            if (this.isInWaterRainOrBubbleColumn()) {
                this.setMoistness(2400);
            } else {
                this.setMoistness(this.getMoistness() - 1);
                if (this.getMoistness() <= 0) {
                    this.attackEntityFrom(DamageSource.DRYOUT, 1.0f);
                }
                if (this.onGround) {
                    this.setMotion(this.getMotion().add((this.rand.nextFloat() * 2.0f - 1.0f) * 0.2f, 0.5, (this.rand.nextFloat() * 2.0f - 1.0f) * 0.2f));
                    this.rotationYaw = this.rand.nextFloat() * 360.0f;
                    this.onGround = false;
                    this.isAirBorne = true;
                }
            }
            if (this.world.isRemote && this.isInWater() && this.getMotion().lengthSquared() > 0.03) {
                Vector3d vector3d = this.getLook(0.0f);
                float f = MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180)) * 0.3f;
                float f2 = MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180)) * 0.3f;
                float f3 = 1.2f - this.rand.nextFloat() * 0.7f;
                for (int i = 0; i < 2; ++i) {
                    this.world.addParticle(ParticleTypes.DOLPHIN, this.getPosX() - vector3d.x * (double)f3 + (double)f, this.getPosY() - vector3d.y, this.getPosZ() - vector3d.z * (double)f3 + (double)f2, 0.0, 0.0, 0.0);
                    this.world.addParticle(ParticleTypes.DOLPHIN, this.getPosX() - vector3d.x * (double)f3 - (double)f, this.getPosY() - vector3d.y, this.getPosZ() - vector3d.z * (double)f3 - (double)f2, 0.0, 0.0, 0.0);
                }
            }
        }
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 38) {
            this.func_208401_a(ParticleTypes.HAPPY_VILLAGER);
        } else {
            super.handleStatusUpdate(by);
        }
    }

    private void func_208401_a(IParticleData iParticleData) {
        for (int i = 0; i < 7; ++i) {
            double d = this.rand.nextGaussian() * 0.01;
            double d2 = this.rand.nextGaussian() * 0.01;
            double d3 = this.rand.nextGaussian() * 0.01;
            this.world.addParticle(iParticleData, this.getPosXRandom(1.0), this.getPosYRandom() + 0.2, this.getPosZRandom(1.0), d, d2, d3);
        }
    }

    @Override
    protected ActionResultType func_230254_b_(PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (!itemStack.isEmpty() && itemStack.getItem().isIn(ItemTags.FISHES)) {
            if (!this.world.isRemote) {
                this.playSound(SoundEvents.ENTITY_DOLPHIN_EAT, 1.0f, 1.0f);
            }
            this.setGotFish(false);
            if (!playerEntity.abilities.isCreativeMode) {
                itemStack.shrink(1);
            }
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        return super.func_230254_b_(playerEntity, hand);
    }

    public static boolean func_223364_b(EntityType<DolphinEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        if (blockPos.getY() > 45 && blockPos.getY() < iWorld.getSeaLevel()) {
            Optional<RegistryKey<Biome>> optional = iWorld.func_242406_i(blockPos);
            return (!Objects.equals(optional, Optional.of(Biomes.OCEAN)) || !Objects.equals(optional, Optional.of(Biomes.DEEP_OCEAN))) && iWorld.getFluidState(blockPos).isTagged(FluidTags.WATER);
        }
        return true;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_DOLPHIN_HURT;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_DOLPHIN_DEATH;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return this.isInWater() ? SoundEvents.ENTITY_DOLPHIN_AMBIENT_WATER : SoundEvents.ENTITY_DOLPHIN_AMBIENT;
    }

    @Override
    protected SoundEvent getSplashSound() {
        return SoundEvents.ENTITY_DOLPHIN_SPLASH;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_DOLPHIN_SWIM;
    }

    protected boolean closeToTarget() {
        BlockPos blockPos = this.getNavigator().getTargetPos();
        return blockPos != null ? blockPos.withinDistance(this.getPositionVec(), 12.0) : false;
    }

    @Override
    public void travel(Vector3d vector3d) {
        if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(this.getAIMoveSpeed(), vector3d);
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
    public boolean canBeLeashedTo(PlayerEntity playerEntity) {
        return false;
    }

    private static boolean lambda$static$0(ItemEntity itemEntity) {
        return !itemEntity.cannotPickup() && itemEntity.isAlive() && itemEntity.isInWater();
    }

    static Random access$000(DolphinEntity dolphinEntity) {
        return dolphinEntity.rand;
    }

    static Random access$100(DolphinEntity dolphinEntity) {
        return dolphinEntity.rand;
    }

    static Random access$200(DolphinEntity dolphinEntity) {
        return dolphinEntity.rand;
    }

    static class MoveHelperController
    extends MovementController {
        private final DolphinEntity dolphin;

        public MoveHelperController(DolphinEntity dolphinEntity) {
            super(dolphinEntity);
            this.dolphin = dolphinEntity;
        }

        @Override
        public void tick() {
            if (this.dolphin.isInWater()) {
                this.dolphin.setMotion(this.dolphin.getMotion().add(0.0, 0.005, 0.0));
            }
            if (this.action == MovementController.Action.MOVE_TO && !this.dolphin.getNavigator().noPath()) {
                double d;
                double d2;
                double d3 = this.posX - this.dolphin.getPosX();
                double d4 = d3 * d3 + (d2 = this.posY - this.dolphin.getPosY()) * d2 + (d = this.posZ - this.dolphin.getPosZ()) * d;
                if (d4 < 2.500000277905201E-7) {
                    this.mob.setMoveForward(0.0f);
                } else {
                    float f = (float)(MathHelper.atan2(d, d3) * 57.2957763671875) - 90.0f;
                    this.dolphin.renderYawOffset = this.dolphin.rotationYaw = this.limitAngle(this.dolphin.rotationYaw, f, 10.0f);
                    this.dolphin.rotationYawHead = this.dolphin.rotationYaw;
                    float f2 = (float)(this.speed * this.dolphin.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    if (this.dolphin.isInWater()) {
                        this.dolphin.setAIMoveSpeed(f2 * 0.02f);
                        float f3 = -((float)(MathHelper.atan2(d2, MathHelper.sqrt(d3 * d3 + d * d)) * 57.2957763671875));
                        f3 = MathHelper.clamp(MathHelper.wrapDegrees(f3), -85.0f, 85.0f);
                        this.dolphin.rotationPitch = this.limitAngle(this.dolphin.rotationPitch, f3, 5.0f);
                        float f4 = MathHelper.cos(this.dolphin.rotationPitch * ((float)Math.PI / 180));
                        float f5 = MathHelper.sin(this.dolphin.rotationPitch * ((float)Math.PI / 180));
                        this.dolphin.moveForward = f4 * f2;
                        this.dolphin.moveVertical = -f5 * f2;
                    } else {
                        this.dolphin.setAIMoveSpeed(f2 * 0.1f);
                    }
                }
            } else {
                this.dolphin.setAIMoveSpeed(0.0f);
                this.dolphin.setMoveStrafing(0.0f);
                this.dolphin.setMoveVertical(0.0f);
                this.dolphin.setMoveForward(0.0f);
            }
        }
    }

    static class SwimToTreasureGoal
    extends Goal {
        private final DolphinEntity dolphin;
        private boolean field_208058_b;

        SwimToTreasureGoal(DolphinEntity dolphinEntity) {
            this.dolphin = dolphinEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean isPreemptible() {
            return true;
        }

        @Override
        public boolean shouldExecute() {
            return this.dolphin.hasGotFish() && this.dolphin.getAir() >= 100;
        }

        @Override
        public boolean shouldContinueExecuting() {
            BlockPos blockPos = this.dolphin.getTreasurePos();
            return !new BlockPos((double)blockPos.getX(), this.dolphin.getPosY(), (double)blockPos.getZ()).withinDistance(this.dolphin.getPositionVec(), 4.0) && !this.field_208058_b && this.dolphin.getAir() >= 100;
        }

        @Override
        public void startExecuting() {
            if (this.dolphin.world instanceof ServerWorld) {
                ServerWorld serverWorld = (ServerWorld)this.dolphin.world;
                this.field_208058_b = false;
                this.dolphin.getNavigator().clearPath();
                BlockPos blockPos = this.dolphin.getPosition();
                Structure<IFeatureConfig> structure = (double)serverWorld.rand.nextFloat() >= 0.5 ? Structure.field_236377_m_ : Structure.field_236373_i_;
                BlockPos blockPos2 = serverWorld.func_241117_a_(structure, blockPos, 50, true);
                if (blockPos2 == null) {
                    Structure<IFeatureConfig> structure2 = structure.equals(Structure.field_236377_m_) ? Structure.field_236373_i_ : Structure.field_236377_m_;
                    BlockPos blockPos3 = serverWorld.func_241117_a_(structure2, blockPos, 50, true);
                    if (blockPos3 == null) {
                        this.field_208058_b = true;
                        return;
                    }
                    this.dolphin.setTreasurePos(blockPos3);
                } else {
                    this.dolphin.setTreasurePos(blockPos2);
                }
                serverWorld.setEntityState(this.dolphin, (byte)38);
            }
        }

        @Override
        public void resetTask() {
            BlockPos blockPos = this.dolphin.getTreasurePos();
            if (new BlockPos((double)blockPos.getX(), this.dolphin.getPosY(), (double)blockPos.getZ()).withinDistance(this.dolphin.getPositionVec(), 4.0) || this.field_208058_b) {
                this.dolphin.setGotFish(true);
            }
        }

        @Override
        public void tick() {
            World world = this.dolphin.world;
            if (this.dolphin.closeToTarget() || this.dolphin.getNavigator().noPath()) {
                BlockPos blockPos;
                Vector3d vector3d = Vector3d.copyCentered(this.dolphin.getTreasurePos());
                Vector3d vector3d2 = RandomPositionGenerator.findRandomTargetTowardsScaled(this.dolphin, 16, 1, vector3d, 0.3926991f);
                if (vector3d2 == null) {
                    vector3d2 = RandomPositionGenerator.findRandomTargetBlockTowards(this.dolphin, 8, 4, vector3d);
                }
                if (!(vector3d2 == null || world.getFluidState(blockPos = new BlockPos(vector3d2)).isTagged(FluidTags.WATER) && world.getBlockState(blockPos).allowsMovement(world, blockPos, PathType.WATER))) {
                    vector3d2 = RandomPositionGenerator.findRandomTargetBlockTowards(this.dolphin, 8, 5, vector3d);
                }
                if (vector3d2 == null) {
                    this.field_208058_b = true;
                    return;
                }
                this.dolphin.getLookController().setLookPosition(vector3d2.x, vector3d2.y, vector3d2.z, this.dolphin.getHorizontalFaceSpeed() + 20, this.dolphin.getVerticalFaceSpeed());
                this.dolphin.getNavigator().tryMoveToXYZ(vector3d2.x, vector3d2.y, vector3d2.z, 1.3);
                if (world.rand.nextInt(80) == 0) {
                    world.setEntityState(this.dolphin, (byte)38);
                }
            }
        }
    }

    static class SwimWithPlayerGoal
    extends Goal {
        private final DolphinEntity dolphin;
        private final double speed;
        private PlayerEntity targetPlayer;

        SwimWithPlayerGoal(DolphinEntity dolphinEntity, double d) {
            this.dolphin = dolphinEntity;
            this.speed = d;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean shouldExecute() {
            this.targetPlayer = this.dolphin.world.getClosestPlayer(field_213810_bA, this.dolphin);
            if (this.targetPlayer == null) {
                return true;
            }
            return this.targetPlayer.isSwimming() && this.dolphin.getAttackTarget() != this.targetPlayer;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return this.targetPlayer != null && this.targetPlayer.isSwimming() && this.dolphin.getDistanceSq(this.targetPlayer) < 256.0;
        }

        @Override
        public void startExecuting() {
            this.targetPlayer.addPotionEffect(new EffectInstance(Effects.DOLPHINS_GRACE, 100));
        }

        @Override
        public void resetTask() {
            this.targetPlayer = null;
            this.dolphin.getNavigator().clearPath();
        }

        @Override
        public void tick() {
            this.dolphin.getLookController().setLookPositionWithEntity(this.targetPlayer, this.dolphin.getHorizontalFaceSpeed() + 20, this.dolphin.getVerticalFaceSpeed());
            if (this.dolphin.getDistanceSq(this.targetPlayer) < 6.25) {
                this.dolphin.getNavigator().clearPath();
            } else {
                this.dolphin.getNavigator().tryMoveToEntityLiving(this.targetPlayer, this.speed);
            }
            if (this.targetPlayer.isSwimming() && this.targetPlayer.world.rand.nextInt(6) == 0) {
                this.targetPlayer.addPotionEffect(new EffectInstance(Effects.DOLPHINS_GRACE, 100));
            }
        }
    }

    class PlayWithItemsGoal
    extends Goal {
        private int field_205154_b;
        final DolphinEntity this$0;

        private PlayWithItemsGoal(DolphinEntity dolphinEntity) {
            this.this$0 = dolphinEntity;
        }

        @Override
        public boolean shouldExecute() {
            if (this.field_205154_b > this.this$0.ticksExisted) {
                return true;
            }
            List<ItemEntity> list = this.this$0.world.getEntitiesWithinAABB(ItemEntity.class, this.this$0.getBoundingBox().grow(8.0, 8.0, 8.0), ITEM_SELECTOR);
            return !list.isEmpty() || !this.this$0.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty();
        }

        @Override
        public void startExecuting() {
            List<ItemEntity> list = this.this$0.world.getEntitiesWithinAABB(ItemEntity.class, this.this$0.getBoundingBox().grow(8.0, 8.0, 8.0), ITEM_SELECTOR);
            if (!list.isEmpty()) {
                this.this$0.getNavigator().tryMoveToEntityLiving(list.get(0), 1.2f);
                this.this$0.playSound(SoundEvents.ENTITY_DOLPHIN_PLAY, 1.0f, 1.0f);
            }
            this.field_205154_b = 0;
        }

        @Override
        public void resetTask() {
            ItemStack itemStack = this.this$0.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
            if (!itemStack.isEmpty()) {
                this.func_220810_a(itemStack);
                this.this$0.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
                this.field_205154_b = this.this$0.ticksExisted + DolphinEntity.access$000(this.this$0).nextInt(100);
            }
        }

        @Override
        public void tick() {
            List<ItemEntity> list = this.this$0.world.getEntitiesWithinAABB(ItemEntity.class, this.this$0.getBoundingBox().grow(8.0, 8.0, 8.0), ITEM_SELECTOR);
            ItemStack itemStack = this.this$0.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
            if (!itemStack.isEmpty()) {
                this.func_220810_a(itemStack);
                this.this$0.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
            } else if (!list.isEmpty()) {
                this.this$0.getNavigator().tryMoveToEntityLiving(list.get(0), 1.2f);
            }
        }

        private void func_220810_a(ItemStack itemStack) {
            if (!itemStack.isEmpty()) {
                double d = this.this$0.getPosYEye() - (double)0.3f;
                ItemEntity itemEntity = new ItemEntity(this.this$0.world, this.this$0.getPosX(), d, this.this$0.getPosZ(), itemStack);
                itemEntity.setPickupDelay(40);
                itemEntity.setThrowerId(this.this$0.getUniqueID());
                float f = 0.3f;
                float f2 = DolphinEntity.access$100(this.this$0).nextFloat() * ((float)Math.PI * 2);
                float f3 = 0.02f * DolphinEntity.access$200(this.this$0).nextFloat();
                itemEntity.setMotion(0.3f * -MathHelper.sin(this.this$0.rotationYaw * ((float)Math.PI / 180)) * MathHelper.cos(this.this$0.rotationPitch * ((float)Math.PI / 180)) + MathHelper.cos(f2) * f3, 0.3f * MathHelper.sin(this.this$0.rotationPitch * ((float)Math.PI / 180)) * 1.5f, 0.3f * MathHelper.cos(this.this$0.rotationYaw * ((float)Math.PI / 180)) * MathHelper.cos(this.this$0.rotationPitch * ((float)Math.PI / 180)) + MathHelper.sin(f2) * f3);
                this.this$0.world.addEntity(itemEntity);
            }
        }
    }
}

