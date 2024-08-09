/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

public class DrownedEntity
extends ZombieEntity
implements IRangedAttackMob {
    private boolean swimmingUp;
    protected final SwimmerPathNavigator waterNavigator;
    protected final GroundPathNavigator groundNavigator;

    public DrownedEntity(EntityType<? extends DrownedEntity> entityType, World world) {
        super((EntityType<? extends ZombieEntity>)entityType, world);
        this.stepHeight = 1.0f;
        this.moveController = new MoveHelperController(this);
        this.setPathPriority(PathNodeType.WATER, 0.0f);
        this.waterNavigator = new SwimmerPathNavigator(this, world);
        this.groundNavigator = new GroundPathNavigator(this, world);
    }

    @Override
    protected void applyEntityAI() {
        this.goalSelector.addGoal(1, new GoToWaterGoal(this, 1.0));
        this.goalSelector.addGoal(2, new TridentAttackGoal(this, 1.0, 40, 10.0f));
        this.goalSelector.addGoal(2, new AttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(5, new GoToBeachGoal(this, 1.0));
        this.goalSelector.addGoal(6, new SwimUpGoal(this, 1.0, this.world.getSeaLevel()));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.0));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, DrownedEntity.class).setCallsForHelp(ZombifiedPiglinEntity.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, 10, true, false, this::shouldAttack));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<AbstractVillagerEntity>((MobEntity)this, AbstractVillagerEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolemEntity>((MobEntity)this, IronGolemEntity.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<TurtleEntity>(this, TurtleEntity.class, 10, true, false, TurtleEntity.TARGET_DRY_BABY));
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        iLivingEntityData = super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
        if (this.getItemStackFromSlot(EquipmentSlotType.OFFHAND).isEmpty() && this.rand.nextFloat() < 0.03f) {
            this.setItemStackToSlot(EquipmentSlotType.OFFHAND, new ItemStack(Items.NAUTILUS_SHELL));
            this.inventoryHandsDropChances[EquipmentSlotType.OFFHAND.getIndex()] = 2.0f;
        }
        return iLivingEntityData;
    }

    public static boolean func_223332_b(EntityType<DrownedEntity> entityType, IServerWorld iServerWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        boolean bl;
        Optional<RegistryKey<Biome>> optional = iServerWorld.func_242406_i(blockPos);
        boolean bl2 = bl = iServerWorld.getDifficulty() != Difficulty.PEACEFUL && DrownedEntity.isValidLightLevel(iServerWorld, blockPos, random2) && (spawnReason == SpawnReason.SPAWNER || iServerWorld.getFluidState(blockPos).isTagged(FluidTags.WATER));
        if (!Objects.equals(optional, Optional.of(Biomes.RIVER)) && !Objects.equals(optional, Optional.of(Biomes.FROZEN_RIVER))) {
            return random2.nextInt(40) == 0 && DrownedEntity.func_223333_a(iServerWorld, blockPos) && bl;
        }
        return random2.nextInt(15) == 0 && bl;
    }

    private static boolean func_223333_a(IWorld iWorld, BlockPos blockPos) {
        return blockPos.getY() < iWorld.getSeaLevel() - 5;
    }

    @Override
    protected boolean canBreakDoors() {
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isInWater() ? SoundEvents.ENTITY_DROWNED_AMBIENT_WATER : SoundEvents.ENTITY_DROWNED_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return this.isInWater() ? SoundEvents.ENTITY_DROWNED_HURT_WATER : SoundEvents.ENTITY_DROWNED_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return this.isInWater() ? SoundEvents.ENTITY_DROWNED_DEATH_WATER : SoundEvents.ENTITY_DROWNED_DEATH;
    }

    @Override
    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_DROWNED_STEP;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_DROWNED_SWIM;
    }

    @Override
    protected ItemStack getSkullDrop() {
        return ItemStack.EMPTY;
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficultyInstance) {
        if ((double)this.rand.nextFloat() > 0.9) {
            int n = this.rand.nextInt(16);
            if (n < 10) {
                this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.TRIDENT));
            } else {
                this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.FISHING_ROD));
            }
        }
    }

    @Override
    protected boolean shouldExchangeEquipment(ItemStack itemStack, ItemStack itemStack2) {
        if (itemStack2.getItem() == Items.NAUTILUS_SHELL) {
            return true;
        }
        if (itemStack2.getItem() == Items.TRIDENT) {
            if (itemStack.getItem() == Items.TRIDENT) {
                return itemStack.getDamage() < itemStack2.getDamage();
            }
            return true;
        }
        return itemStack.getItem() == Items.TRIDENT ? true : super.shouldExchangeEquipment(itemStack, itemStack2);
    }

    @Override
    protected boolean shouldDrown() {
        return true;
    }

    @Override
    public boolean isNotColliding(IWorldReader iWorldReader) {
        return iWorldReader.checkNoEntityCollision(this);
    }

    public boolean shouldAttack(@Nullable LivingEntity livingEntity) {
        if (livingEntity != null) {
            return !this.world.isDaytime() || livingEntity.isInWater();
        }
        return true;
    }

    @Override
    public boolean isPushedByWater() {
        return !this.isSwimming();
    }

    private boolean func_204715_dF() {
        if (this.swimmingUp) {
            return false;
        }
        LivingEntity livingEntity = this.getAttackTarget();
        return livingEntity != null && livingEntity.isInWater();
    }

    @Override
    public void travel(Vector3d vector3d) {
        if (this.isServerWorld() && this.isInWater() && this.func_204715_dF()) {
            this.moveRelative(0.01f, vector3d);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.9));
        } else {
            super.travel(vector3d);
        }
    }

    @Override
    public void updateSwimming() {
        if (!this.world.isRemote) {
            if (this.isServerWorld() && this.isInWater() && this.func_204715_dF()) {
                this.navigator = this.waterNavigator;
                this.setSwimming(false);
            } else {
                this.navigator = this.groundNavigator;
                this.setSwimming(true);
            }
        }
    }

    protected boolean isCloseToPathTarget() {
        double d;
        BlockPos blockPos;
        Path path = this.getNavigator().getPath();
        return path == null || (blockPos = path.getTarget()) == null || !((d = this.getDistanceSq(blockPos.getX(), blockPos.getY(), blockPos.getZ())) < 4.0);
    }

    @Override
    public void attackEntityWithRangedAttack(LivingEntity livingEntity, float f) {
        TridentEntity tridentEntity = new TridentEntity(this.world, (LivingEntity)this, new ItemStack(Items.TRIDENT));
        double d = livingEntity.getPosX() - this.getPosX();
        double d2 = livingEntity.getPosYHeight(0.3333333333333333) - tridentEntity.getPosY();
        double d3 = livingEntity.getPosZ() - this.getPosZ();
        double d4 = MathHelper.sqrt(d * d + d3 * d3);
        tridentEntity.shoot(d, d2 + d4 * (double)0.2f, d3, 1.6f, 14 - this.world.getDifficulty().getId() * 4);
        this.playSound(SoundEvents.ENTITY_DROWNED_SHOOT, 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
        this.world.addEntity(tridentEntity);
    }

    public void setSwimmingUp(boolean bl) {
        this.swimmingUp = bl;
    }

    static PathNavigator access$002(DrownedEntity drownedEntity, PathNavigator pathNavigator) {
        drownedEntity.navigator = pathNavigator;
        return drownedEntity.navigator;
    }

    static boolean access$100(DrownedEntity drownedEntity) {
        return drownedEntity.onGround;
    }

    static class MoveHelperController
    extends MovementController {
        private final DrownedEntity drowned;

        public MoveHelperController(DrownedEntity drownedEntity) {
            super(drownedEntity);
            this.drowned = drownedEntity;
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.drowned.getAttackTarget();
            if (this.drowned.func_204715_dF() && this.drowned.isInWater()) {
                if (livingEntity != null && livingEntity.getPosY() > this.drowned.getPosY() || this.drowned.swimmingUp) {
                    this.drowned.setMotion(this.drowned.getMotion().add(0.0, 0.002, 0.0));
                }
                if (this.action != MovementController.Action.MOVE_TO || this.drowned.getNavigator().noPath()) {
                    this.drowned.setAIMoveSpeed(0.0f);
                    return;
                }
                double d = this.posX - this.drowned.getPosX();
                double d2 = this.posY - this.drowned.getPosY();
                double d3 = this.posZ - this.drowned.getPosZ();
                double d4 = MathHelper.sqrt(d * d + d2 * d2 + d3 * d3);
                d2 /= d4;
                float f = (float)(MathHelper.atan2(d3, d) * 57.2957763671875) - 90.0f;
                this.drowned.renderYawOffset = this.drowned.rotationYaw = this.limitAngle(this.drowned.rotationYaw, f, 90.0f);
                float f2 = (float)(this.speed * this.drowned.getAttributeValue(Attributes.MOVEMENT_SPEED));
                float f3 = MathHelper.lerp(0.125f, this.drowned.getAIMoveSpeed(), f2);
                this.drowned.setAIMoveSpeed(f3);
                this.drowned.setMotion(this.drowned.getMotion().add((double)f3 * d * 0.005, (double)f3 * d2 * 0.1, (double)f3 * d3 * 0.005));
            } else {
                if (!DrownedEntity.access$100(this.drowned)) {
                    this.drowned.setMotion(this.drowned.getMotion().add(0.0, -0.008, 0.0));
                }
                super.tick();
            }
        }
    }

    static class GoToWaterGoal
    extends Goal {
        private final CreatureEntity field_204730_a;
        private double field_204731_b;
        private double field_204732_c;
        private double field_204733_d;
        private final double field_204734_e;
        private final World field_204735_f;

        public GoToWaterGoal(CreatureEntity creatureEntity, double d) {
            this.field_204730_a = creatureEntity;
            this.field_204734_e = d;
            this.field_204735_f = creatureEntity.world;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            if (!this.field_204735_f.isDaytime()) {
                return true;
            }
            if (this.field_204730_a.isInWater()) {
                return true;
            }
            Vector3d vector3d = this.func_204729_f();
            if (vector3d == null) {
                return true;
            }
            this.field_204731_b = vector3d.x;
            this.field_204732_c = vector3d.y;
            this.field_204733_d = vector3d.z;
            return false;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return !this.field_204730_a.getNavigator().noPath();
        }

        @Override
        public void startExecuting() {
            this.field_204730_a.getNavigator().tryMoveToXYZ(this.field_204731_b, this.field_204732_c, this.field_204733_d, this.field_204734_e);
        }

        @Nullable
        private Vector3d func_204729_f() {
            Random random2 = this.field_204730_a.getRNG();
            BlockPos blockPos = this.field_204730_a.getPosition();
            for (int i = 0; i < 10; ++i) {
                BlockPos blockPos2 = blockPos.add(random2.nextInt(20) - 10, 2 - random2.nextInt(8), random2.nextInt(20) - 10);
                if (!this.field_204735_f.getBlockState(blockPos2).isIn(Blocks.WATER)) continue;
                return Vector3d.copyCenteredHorizontally(blockPos2);
            }
            return null;
        }
    }

    static class TridentAttackGoal
    extends RangedAttackGoal {
        private final DrownedEntity field_204728_a;

        public TridentAttackGoal(IRangedAttackMob iRangedAttackMob, double d, int n, float f) {
            super(iRangedAttackMob, d, n, f);
            this.field_204728_a = (DrownedEntity)iRangedAttackMob;
        }

        @Override
        public boolean shouldExecute() {
            return super.shouldExecute() && this.field_204728_a.getHeldItemMainhand().getItem() == Items.TRIDENT;
        }

        @Override
        public void startExecuting() {
            super.startExecuting();
            this.field_204728_a.setAggroed(false);
            this.field_204728_a.setActiveHand(Hand.MAIN_HAND);
        }

        @Override
        public void resetTask() {
            super.resetTask();
            this.field_204728_a.resetActiveHand();
            this.field_204728_a.setAggroed(true);
        }
    }

    static class AttackGoal
    extends ZombieAttackGoal {
        private final DrownedEntity field_204726_g;

        public AttackGoal(DrownedEntity drownedEntity, double d, boolean bl) {
            super(drownedEntity, d, bl);
            this.field_204726_g = drownedEntity;
        }

        @Override
        public boolean shouldExecute() {
            return super.shouldExecute() && this.field_204726_g.shouldAttack(this.field_204726_g.getAttackTarget());
        }

        @Override
        public boolean shouldContinueExecuting() {
            return super.shouldContinueExecuting() && this.field_204726_g.shouldAttack(this.field_204726_g.getAttackTarget());
        }
    }

    static class GoToBeachGoal
    extends MoveToBlockGoal {
        private final DrownedEntity drowned;

        public GoToBeachGoal(DrownedEntity drownedEntity, double d) {
            super(drownedEntity, d, 8, 2);
            this.drowned = drownedEntity;
        }

        @Override
        public boolean shouldExecute() {
            return super.shouldExecute() && !this.drowned.world.isDaytime() && this.drowned.isInWater() && this.drowned.getPosY() >= (double)(this.drowned.world.getSeaLevel() - 3);
        }

        @Override
        public boolean shouldContinueExecuting() {
            return super.shouldContinueExecuting();
        }

        @Override
        protected boolean shouldMoveTo(IWorldReader iWorldReader, BlockPos blockPos) {
            BlockPos blockPos2 = blockPos.up();
            return iWorldReader.isAirBlock(blockPos2) && iWorldReader.isAirBlock(blockPos2.up()) ? iWorldReader.getBlockState(blockPos).canSpawnMobs(iWorldReader, blockPos, this.drowned) : false;
        }

        @Override
        public void startExecuting() {
            this.drowned.setSwimmingUp(true);
            DrownedEntity.access$002(this.drowned, this.drowned.groundNavigator);
            super.startExecuting();
        }

        @Override
        public void resetTask() {
            super.resetTask();
        }
    }

    static class SwimUpGoal
    extends Goal {
        private final DrownedEntity field_204736_a;
        private final double field_204737_b;
        private final int targetY;
        private boolean obstructed;

        public SwimUpGoal(DrownedEntity drownedEntity, double d, int n) {
            this.field_204736_a = drownedEntity;
            this.field_204737_b = d;
            this.targetY = n;
        }

        @Override
        public boolean shouldExecute() {
            return !this.field_204736_a.world.isDaytime() && this.field_204736_a.isInWater() && this.field_204736_a.getPosY() < (double)(this.targetY - 2);
        }

        @Override
        public boolean shouldContinueExecuting() {
            return this.shouldExecute() && !this.obstructed;
        }

        @Override
        public void tick() {
            if (this.field_204736_a.getPosY() < (double)(this.targetY - 1) && (this.field_204736_a.getNavigator().noPath() || this.field_204736_a.isCloseToPathTarget())) {
                Vector3d vector3d = RandomPositionGenerator.findRandomTargetBlockTowards(this.field_204736_a, 4, 8, new Vector3d(this.field_204736_a.getPosX(), this.targetY - 1, this.field_204736_a.getPosZ()));
                if (vector3d == null) {
                    this.obstructed = true;
                    return;
                }
                this.field_204736_a.getNavigator().tryMoveToXYZ(vector3d.x, vector3d.y, vector3d.z, this.field_204737_b);
            }
        }

        @Override
        public void startExecuting() {
            this.field_204736_a.setSwimmingUp(false);
            this.obstructed = false;
        }

        @Override
        public void resetTask() {
            this.field_204736_a.setSwimmingUp(true);
        }
    }
}

