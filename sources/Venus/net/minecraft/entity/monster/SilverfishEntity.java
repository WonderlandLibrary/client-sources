/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.util.EnumSet;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SilverfishBlock;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class SilverfishEntity
extends MonsterEntity {
    private SummonSilverfishGoal summonSilverfish;

    public SilverfishEntity(EntityType<? extends SilverfishEntity> entityType, World world) {
        super((EntityType<? extends MonsterEntity>)entityType, world);
    }

    @Override
    protected void registerGoals() {
        this.summonSilverfish = new SummonSilverfishGoal(this);
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(3, this.summonSilverfish);
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(5, new HideInStoneGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]).setCallsForHelp(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<PlayerEntity>((MobEntity)this, PlayerEntity.class, true));
    }

    @Override
    public double getYOffset() {
        return 0.1;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return 0.13f;
    }

    public static AttributeModifierMap.MutableAttribute func_234301_m_() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 8.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25).createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0);
    }

    @Override
    protected boolean canTriggerWalking() {
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SILVERFISH_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_SILVERFISH_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SILVERFISH_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.ENTITY_SILVERFISH_STEP, 0.15f, 1.0f);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return true;
        }
        if ((damageSource instanceof EntityDamageSource || damageSource == DamageSource.MAGIC) && this.summonSilverfish != null) {
            this.summonSilverfish.notifyHurt();
        }
        return super.attackEntityFrom(damageSource, f);
    }

    @Override
    public void tick() {
        this.renderYawOffset = this.rotationYaw;
        super.tick();
    }

    @Override
    public void setRenderYawOffset(float f) {
        this.rotationYaw = f;
        super.setRenderYawOffset(f);
    }

    @Override
    public float getBlockPathWeight(BlockPos blockPos, IWorldReader iWorldReader) {
        return SilverfishBlock.canContainSilverfish(iWorldReader.getBlockState(blockPos.down())) ? 10.0f : super.getBlockPathWeight(blockPos, iWorldReader);
    }

    public static boolean func_223331_b(EntityType<SilverfishEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        if (SilverfishEntity.canMonsterSpawn(entityType, iWorld, spawnReason, blockPos, random2)) {
            PlayerEntity playerEntity = iWorld.getClosestPlayer((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, 5.0, true);
            return playerEntity == null;
        }
        return true;
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }

    static class SummonSilverfishGoal
    extends Goal {
        private final SilverfishEntity silverfish;
        private int lookForFriends;

        public SummonSilverfishGoal(SilverfishEntity silverfishEntity) {
            this.silverfish = silverfishEntity;
        }

        public void notifyHurt() {
            if (this.lookForFriends == 0) {
                this.lookForFriends = 20;
            }
        }

        @Override
        public boolean shouldExecute() {
            return this.lookForFriends > 0;
        }

        @Override
        public void tick() {
            --this.lookForFriends;
            if (this.lookForFriends <= 0) {
                World world = this.silverfish.world;
                Random random2 = this.silverfish.getRNG();
                BlockPos blockPos = this.silverfish.getPosition();
                int n = 0;
                while (n <= 5 && n >= -5) {
                    int n2 = 0;
                    while (n2 <= 10 && n2 >= -10) {
                        int n3 = 0;
                        while (n3 <= 10 && n3 >= -10) {
                            BlockPos blockPos2 = blockPos.add(n2, n, n3);
                            BlockState blockState = world.getBlockState(blockPos2);
                            Block block = blockState.getBlock();
                            if (block instanceof SilverfishBlock) {
                                if (world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) {
                                    world.destroyBlock(blockPos2, true, this.silverfish);
                                } else {
                                    world.setBlockState(blockPos2, ((SilverfishBlock)block).getMimickedBlock().getDefaultState(), 0);
                                }
                                if (random2.nextBoolean()) {
                                    return;
                                }
                            }
                            n3 = (n3 <= 0 ? 1 : 0) - n3;
                        }
                        n2 = (n2 <= 0 ? 1 : 0) - n2;
                    }
                    n = (n <= 0 ? 1 : 0) - n;
                }
            }
        }
    }

    static class HideInStoneGoal
    extends RandomWalkingGoal {
        private Direction facing;
        private boolean doMerge;

        public HideInStoneGoal(SilverfishEntity silverfishEntity) {
            super(silverfishEntity, 1.0, 10);
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            if (this.creature.getAttackTarget() != null) {
                return true;
            }
            if (!this.creature.getNavigator().noPath()) {
                return true;
            }
            Random random2 = this.creature.getRNG();
            if (this.creature.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING) && random2.nextInt(10) == 0) {
                this.facing = Direction.getRandomDirection(random2);
                BlockPos blockPos = new BlockPos(this.creature.getPosX(), this.creature.getPosY() + 0.5, this.creature.getPosZ()).offset(this.facing);
                BlockState blockState = this.creature.world.getBlockState(blockPos);
                if (SilverfishBlock.canContainSilverfish(blockState)) {
                    this.doMerge = true;
                    return false;
                }
            }
            this.doMerge = false;
            return super.shouldExecute();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return this.doMerge ? false : super.shouldContinueExecuting();
        }

        @Override
        public void startExecuting() {
            if (!this.doMerge) {
                super.startExecuting();
            } else {
                World world = this.creature.world;
                BlockPos blockPos = new BlockPos(this.creature.getPosX(), this.creature.getPosY() + 0.5, this.creature.getPosZ()).offset(this.facing);
                BlockState blockState = world.getBlockState(blockPos);
                if (SilverfishBlock.canContainSilverfish(blockState)) {
                    world.setBlockState(blockPos, SilverfishBlock.infest(blockState.getBlock()), 3);
                    this.creature.spawnExplosionParticle();
                    this.creature.remove();
                }
            }
        }
    }
}

