/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class RavagerEntity
extends AbstractRaiderEntity {
    private static final Predicate<Entity> field_213690_b = RavagerEntity::lambda$static$0;
    private int attackTick;
    private int stunTick;
    private int roarTick;

    public RavagerEntity(EntityType<? extends RavagerEntity> entityType, World world) {
        super((EntityType<? extends AbstractRaiderEntity>)entityType, world);
        this.stepHeight = 1.0f;
        this.experienceValue = 20;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(4, new AttackGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.4));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0f));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, AbstractRaiderEntity.class).setCallsForHelp(new Class[0]));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<PlayerEntity>((MobEntity)this, PlayerEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<AbstractVillagerEntity>((MobEntity)this, AbstractVillagerEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<IronGolemEntity>((MobEntity)this, IronGolemEntity.class, true));
    }

    @Override
    protected void updateMovementGoalFlags() {
        boolean bl = !(this.getControllingPassenger() instanceof MobEntity) || this.getControllingPassenger().getType().isContained(EntityTypeTags.RAIDERS);
        boolean bl2 = !(this.getRidingEntity() instanceof BoatEntity);
        this.goalSelector.setFlag(Goal.Flag.MOVE, bl);
        this.goalSelector.setFlag(Goal.Flag.JUMP, bl && bl2);
        this.goalSelector.setFlag(Goal.Flag.LOOK, bl);
        this.goalSelector.setFlag(Goal.Flag.TARGET, bl);
    }

    public static AttributeModifierMap.MutableAttribute func_234297_m_() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 100.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3).createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.75).createMutableAttribute(Attributes.ATTACK_DAMAGE, 12.0).createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1.5).createMutableAttribute(Attributes.FOLLOW_RANGE, 32.0);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("AttackTick", this.attackTick);
        compoundNBT.putInt("StunTick", this.stunTick);
        compoundNBT.putInt("RoarTick", this.roarTick);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.attackTick = compoundNBT.getInt("AttackTick");
        this.stunTick = compoundNBT.getInt("StunTick");
        this.roarTick = compoundNBT.getInt("RoarTick");
    }

    @Override
    public SoundEvent getRaidLossSound() {
        return SoundEvents.ENTITY_RAVAGER_CELEBRATE;
    }

    @Override
    protected PathNavigator createNavigator(World world) {
        return new Navigator(this, world);
    }

    @Override
    public int getHorizontalFaceSpeed() {
        return 0;
    }

    @Override
    public double getMountedYOffset() {
        return 2.1;
    }

    @Override
    public boolean canBeSteered() {
        return !this.isAIDisabled() && this.getControllingPassenger() instanceof LivingEntity;
    }

    @Override
    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.isAlive()) {
            if (this.isMovementBlocked()) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0);
            } else {
                double d = this.getAttackTarget() != null ? 0.35 : 0.3;
                double d2 = this.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue();
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(MathHelper.lerp(0.1, d2, d));
            }
            if (this.collidedHorizontally && this.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) {
                boolean bl = false;
                AxisAlignedBB axisAlignedBB = this.getBoundingBox().grow(0.2);
                for (BlockPos blockPos : BlockPos.getAllInBoxMutable(MathHelper.floor(axisAlignedBB.minX), MathHelper.floor(axisAlignedBB.minY), MathHelper.floor(axisAlignedBB.minZ), MathHelper.floor(axisAlignedBB.maxX), MathHelper.floor(axisAlignedBB.maxY), MathHelper.floor(axisAlignedBB.maxZ))) {
                    BlockState blockState = this.world.getBlockState(blockPos);
                    Block block = blockState.getBlock();
                    if (!(block instanceof LeavesBlock)) continue;
                    bl = this.world.destroyBlock(blockPos, true, this) || bl;
                }
                if (!bl && this.onGround) {
                    this.jump();
                }
            }
            if (this.roarTick > 0) {
                --this.roarTick;
                if (this.roarTick == 10) {
                    this.roar();
                }
            }
            if (this.attackTick > 0) {
                --this.attackTick;
            }
            if (this.stunTick > 0) {
                --this.stunTick;
                this.func_213682_eh();
                if (this.stunTick == 0) {
                    this.playSound(SoundEvents.ENTITY_RAVAGER_ROAR, 1.0f, 1.0f);
                    this.roarTick = 20;
                }
            }
        }
    }

    private void func_213682_eh() {
        if (this.rand.nextInt(6) == 0) {
            double d = this.getPosX() - (double)this.getWidth() * Math.sin(this.renderYawOffset * ((float)Math.PI / 180)) + (this.rand.nextDouble() * 0.6 - 0.3);
            double d2 = this.getPosY() + (double)this.getHeight() - 0.3;
            double d3 = this.getPosZ() + (double)this.getWidth() * Math.cos(this.renderYawOffset * ((float)Math.PI / 180)) + (this.rand.nextDouble() * 0.6 - 0.3);
            this.world.addParticle(ParticleTypes.ENTITY_EFFECT, d, d2, d3, 0.4980392156862745, 0.5137254901960784, 0.5725490196078431);
        }
    }

    @Override
    protected boolean isMovementBlocked() {
        return super.isMovementBlocked() || this.attackTick > 0 || this.stunTick > 0 || this.roarTick > 0;
    }

    @Override
    public boolean canEntityBeSeen(Entity entity2) {
        return this.stunTick <= 0 && this.roarTick <= 0 ? super.canEntityBeSeen(entity2) : false;
    }

    @Override
    protected void constructKnockBackVector(LivingEntity livingEntity) {
        if (this.roarTick == 0) {
            if (this.rand.nextDouble() < 0.5) {
                this.stunTick = 40;
                this.playSound(SoundEvents.ENTITY_RAVAGER_STUNNED, 1.0f, 1.0f);
                this.world.setEntityState(this, (byte)39);
                livingEntity.applyEntityCollision(this);
            } else {
                this.launch(livingEntity);
            }
            livingEntity.velocityChanged = true;
        }
    }

    private void roar() {
        if (this.isAlive()) {
            for (Entity entity2 : this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(4.0), field_213690_b)) {
                if (!(entity2 instanceof AbstractIllagerEntity)) {
                    entity2.attackEntityFrom(DamageSource.causeMobDamage(this), 6.0f);
                }
                this.launch(entity2);
            }
            Vector3d vector3d = this.getBoundingBox().getCenter();
            for (int i = 0; i < 40; ++i) {
                double d = this.rand.nextGaussian() * 0.2;
                double d2 = this.rand.nextGaussian() * 0.2;
                double d3 = this.rand.nextGaussian() * 0.2;
                this.world.addParticle(ParticleTypes.POOF, vector3d.x, vector3d.y, vector3d.z, d, d2, d3);
            }
        }
    }

    private void launch(Entity entity2) {
        double d = entity2.getPosX() - this.getPosX();
        double d2 = entity2.getPosZ() - this.getPosZ();
        double d3 = Math.max(d * d + d2 * d2, 0.001);
        entity2.addVelocity(d / d3 * 4.0, 0.2, d2 / d3 * 4.0);
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 4) {
            this.attackTick = 10;
            this.playSound(SoundEvents.ENTITY_RAVAGER_ATTACK, 1.0f, 1.0f);
        } else if (by == 39) {
            this.stunTick = 40;
        }
        super.handleStatusUpdate(by);
    }

    public int func_213683_l() {
        return this.attackTick;
    }

    public int func_213684_dX() {
        return this.stunTick;
    }

    public int func_213687_eg() {
        return this.roarTick;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity2) {
        this.attackTick = 10;
        this.world.setEntityState(this, (byte)4);
        this.playSound(SoundEvents.ENTITY_RAVAGER_ATTACK, 1.0f, 1.0f);
        return super.attackEntityAsMob(entity2);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_RAVAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_RAVAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_RAVAGER_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.ENTITY_RAVAGER_STEP, 0.15f, 1.0f);
    }

    @Override
    public boolean isNotColliding(IWorldReader iWorldReader) {
        return !iWorldReader.containsAnyLiquid(this.getBoundingBox());
    }

    @Override
    public void applyWaveBonus(int n, boolean bl) {
    }

    @Override
    public boolean canBeLeader() {
        return true;
    }

    private static boolean lambda$static$0(Entity entity2) {
        return entity2.isAlive() && !(entity2 instanceof RavagerEntity);
    }

    class AttackGoal
    extends MeleeAttackGoal {
        final RavagerEntity this$0;

        public AttackGoal(RavagerEntity ravagerEntity) {
            this.this$0 = ravagerEntity;
            super(ravagerEntity, 1.0, true);
        }

        @Override
        protected double getAttackReachSqr(LivingEntity livingEntity) {
            float f = this.this$0.getWidth() - 0.1f;
            return f * 2.0f * f * 2.0f + livingEntity.getWidth();
        }
    }

    static class Navigator
    extends GroundPathNavigator {
        public Navigator(MobEntity mobEntity, World world) {
            super(mobEntity, world);
        }

        @Override
        protected PathFinder getPathFinder(int n) {
            this.nodeProcessor = new Processor();
            return new PathFinder(this.nodeProcessor, n);
        }
    }

    static class Processor
    extends WalkNodeProcessor {
        private Processor() {
        }

        @Override
        protected PathNodeType func_215744_a(IBlockReader iBlockReader, boolean bl, boolean bl2, BlockPos blockPos, PathNodeType pathNodeType) {
            return pathNodeType == PathNodeType.LEAVES ? PathNodeType.OPEN : super.func_215744_a(iBlockReader, bl, bl2, blockPos, pathNodeType);
        }
    }
}

