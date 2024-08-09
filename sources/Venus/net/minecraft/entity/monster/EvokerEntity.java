/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SpellcastingIllagerEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EvokerFangsEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EvokerEntity
extends SpellcastingIllagerEntity {
    private SheepEntity wololoTarget;

    public EvokerEntity(EntityType<? extends EvokerEntity> entityType, World world) {
        super((EntityType<? extends SpellcastingIllagerEntity>)entityType, world);
        this.experienceValue = 10;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new CastingSpellGoal(this));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0f, 0.6, 1.0));
        this.goalSelector.addGoal(4, new SummonSpellGoal(this));
        this.goalSelector.addGoal(5, new AttackSpellGoal(this));
        this.goalSelector.addGoal(6, new WololoSpellGoal(this));
        this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, AbstractRaiderEntity.class).setCallsForHelp(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<PlayerEntity>((MobEntity)this, PlayerEntity.class, true).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<AbstractVillagerEntity>((MobEntity)this, AbstractVillagerEntity.class, false).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolemEntity>((MobEntity)this, IronGolemEntity.class, false));
    }

    public static AttributeModifierMap.MutableAttribute func_234289_eI_() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5).createMutableAttribute(Attributes.FOLLOW_RANGE, 12.0).createMutableAttribute(Attributes.MAX_HEALTH, 24.0);
    }

    @Override
    protected void registerData() {
        super.registerData();
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
    }

    @Override
    public SoundEvent getRaidLossSound() {
        return SoundEvents.ENTITY_EVOKER_CELEBRATE;
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
    }

    @Override
    public boolean isOnSameTeam(Entity entity2) {
        if (entity2 == null) {
            return true;
        }
        if (entity2 == this) {
            return false;
        }
        if (super.isOnSameTeam(entity2)) {
            return false;
        }
        if (entity2 instanceof VexEntity) {
            return this.isOnSameTeam(((VexEntity)entity2).getOwner());
        }
        if (entity2 instanceof LivingEntity && ((LivingEntity)entity2).getCreatureAttribute() == CreatureAttribute.ILLAGER) {
            return this.getTeam() == null && entity2.getTeam() == null;
        }
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_EVOKER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_EVOKER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_EVOKER_HURT;
    }

    private void setWololoTarget(@Nullable SheepEntity sheepEntity) {
        this.wololoTarget = sheepEntity;
    }

    @Nullable
    private SheepEntity getWololoTarget() {
        return this.wololoTarget;
    }

    @Override
    protected SoundEvent getSpellSound() {
        return SoundEvents.ENTITY_EVOKER_CAST_SPELL;
    }

    @Override
    public void applyWaveBonus(int n, boolean bl) {
    }

    static Random access$000(EvokerEntity evokerEntity) {
        return evokerEntity.rand;
    }

    static Random access$100(EvokerEntity evokerEntity) {
        return evokerEntity.rand;
    }

    static Random access$200(EvokerEntity evokerEntity) {
        return evokerEntity.rand;
    }

    static Random access$300(EvokerEntity evokerEntity) {
        return evokerEntity.rand;
    }

    static Random access$400(EvokerEntity evokerEntity) {
        return evokerEntity.rand;
    }

    class CastingSpellGoal
    extends SpellcastingIllagerEntity.CastingASpellGoal {
        final EvokerEntity this$0;

        private CastingSpellGoal(EvokerEntity evokerEntity) {
            this.this$0 = evokerEntity;
            super(evokerEntity);
        }

        @Override
        public void tick() {
            if (this.this$0.getAttackTarget() != null) {
                this.this$0.getLookController().setLookPositionWithEntity(this.this$0.getAttackTarget(), this.this$0.getHorizontalFaceSpeed(), this.this$0.getVerticalFaceSpeed());
            } else if (this.this$0.getWololoTarget() != null) {
                this.this$0.getLookController().setLookPositionWithEntity(this.this$0.getWololoTarget(), this.this$0.getHorizontalFaceSpeed(), this.this$0.getVerticalFaceSpeed());
            }
        }
    }

    class SummonSpellGoal
    extends SpellcastingIllagerEntity.UseSpellGoal {
        private final EntityPredicate field_220843_e;
        final EvokerEntity this$0;

        private SummonSpellGoal(EvokerEntity evokerEntity) {
            this.this$0 = evokerEntity;
            super(evokerEntity);
            this.field_220843_e = new EntityPredicate().setDistance(16.0).setLineOfSiteRequired().setUseInvisibilityCheck().allowInvulnerable().allowFriendlyFire();
        }

        @Override
        public boolean shouldExecute() {
            if (!super.shouldExecute()) {
                return true;
            }
            int n = this.this$0.world.getTargettableEntitiesWithinAABB(VexEntity.class, this.field_220843_e, this.this$0, this.this$0.getBoundingBox().grow(16.0)).size();
            return EvokerEntity.access$000(this.this$0).nextInt(8) + 1 > n;
        }

        @Override
        protected int getCastingTime() {
            return 1;
        }

        @Override
        protected int getCastingInterval() {
            return 1;
        }

        @Override
        protected void castSpell() {
            ServerWorld serverWorld = (ServerWorld)this.this$0.world;
            for (int i = 0; i < 3; ++i) {
                BlockPos blockPos = this.this$0.getPosition().add(-2 + EvokerEntity.access$100(this.this$0).nextInt(5), 1, -2 + EvokerEntity.access$200(this.this$0).nextInt(5));
                VexEntity vexEntity = EntityType.VEX.create(this.this$0.world);
                vexEntity.moveToBlockPosAndAngles(blockPos, 0.0f, 0.0f);
                vexEntity.onInitialSpawn(serverWorld, this.this$0.world.getDifficultyForLocation(blockPos), SpawnReason.MOB_SUMMONED, null, null);
                vexEntity.setOwner(this.this$0);
                vexEntity.setBoundOrigin(blockPos);
                vexEntity.setLimitedLife(20 * (30 + EvokerEntity.access$300(this.this$0).nextInt(90)));
                serverWorld.func_242417_l(vexEntity);
            }
        }

        @Override
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON;
        }

        @Override
        protected SpellcastingIllagerEntity.SpellType getSpellType() {
            return SpellcastingIllagerEntity.SpellType.SUMMON_VEX;
        }
    }

    class AttackSpellGoal
    extends SpellcastingIllagerEntity.UseSpellGoal {
        final EvokerEntity this$0;

        private AttackSpellGoal(EvokerEntity evokerEntity) {
            this.this$0 = evokerEntity;
            super(evokerEntity);
        }

        @Override
        protected int getCastingTime() {
            return 1;
        }

        @Override
        protected int getCastingInterval() {
            return 1;
        }

        @Override
        protected void castSpell() {
            LivingEntity livingEntity = this.this$0.getAttackTarget();
            double d = Math.min(livingEntity.getPosY(), this.this$0.getPosY());
            double d2 = Math.max(livingEntity.getPosY(), this.this$0.getPosY()) + 1.0;
            float f = (float)MathHelper.atan2(livingEntity.getPosZ() - this.this$0.getPosZ(), livingEntity.getPosX() - this.this$0.getPosX());
            if (this.this$0.getDistanceSq(livingEntity) < 9.0) {
                float f2;
                int n;
                for (n = 0; n < 5; ++n) {
                    f2 = f + (float)n * (float)Math.PI * 0.4f;
                    this.spawnFangs(this.this$0.getPosX() + (double)MathHelper.cos(f2) * 1.5, this.this$0.getPosZ() + (double)MathHelper.sin(f2) * 1.5, d, d2, f2, 0);
                }
                for (n = 0; n < 8; ++n) {
                    f2 = f + (float)n * (float)Math.PI * 2.0f / 8.0f + 1.2566371f;
                    this.spawnFangs(this.this$0.getPosX() + (double)MathHelper.cos(f2) * 2.5, this.this$0.getPosZ() + (double)MathHelper.sin(f2) * 2.5, d, d2, f2, 3);
                }
            } else {
                for (int i = 0; i < 16; ++i) {
                    double d3 = 1.25 * (double)(i + 1);
                    int n = 1 * i;
                    this.spawnFangs(this.this$0.getPosX() + (double)MathHelper.cos(f) * d3, this.this$0.getPosZ() + (double)MathHelper.sin(f) * d3, d, d2, f, n);
                }
            }
        }

        private void spawnFangs(double d, double d2, double d3, double d4, float f, int n) {
            BlockPos blockPos = new BlockPos(d, d4, d2);
            boolean bl = false;
            double d5 = 0.0;
            do {
                BlockState blockState;
                VoxelShape voxelShape;
                BlockPos blockPos2;
                BlockState blockState2;
                if (!(blockState2 = this.this$0.world.getBlockState(blockPos2 = blockPos.down())).isSolidSide(this.this$0.world, blockPos2, Direction.UP)) continue;
                if (!this.this$0.world.isAirBlock(blockPos) && !(voxelShape = (blockState = this.this$0.world.getBlockState(blockPos)).getCollisionShape(this.this$0.world, blockPos)).isEmpty()) {
                    d5 = voxelShape.getEnd(Direction.Axis.Y);
                }
                bl = true;
                break;
            } while ((blockPos = blockPos.down()).getY() >= MathHelper.floor(d3) - 1);
            if (bl) {
                this.this$0.world.addEntity(new EvokerFangsEntity(this.this$0.world, d, (double)blockPos.getY() + d5, d2, f, n, this.this$0));
            }
        }

        @Override
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.ENTITY_EVOKER_PREPARE_ATTACK;
        }

        @Override
        protected SpellcastingIllagerEntity.SpellType getSpellType() {
            return SpellcastingIllagerEntity.SpellType.FANGS;
        }
    }

    public class WololoSpellGoal
    extends SpellcastingIllagerEntity.UseSpellGoal {
        private final EntityPredicate wololoTargetFlags;
        final EvokerEntity this$0;

        public WololoSpellGoal(EvokerEntity evokerEntity) {
            this.this$0 = evokerEntity;
            super(evokerEntity);
            this.wololoTargetFlags = new EntityPredicate().setDistance(16.0).allowInvulnerable().setCustomPredicate(WololoSpellGoal::lambda$new$0);
        }

        @Override
        public boolean shouldExecute() {
            if (this.this$0.getAttackTarget() != null) {
                return true;
            }
            if (this.this$0.isSpellcasting()) {
                return true;
            }
            if (this.this$0.ticksExisted < this.spellCooldown) {
                return true;
            }
            if (!this.this$0.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) {
                return true;
            }
            List<SheepEntity> list = this.this$0.world.getTargettableEntitiesWithinAABB(SheepEntity.class, this.wololoTargetFlags, this.this$0, this.this$0.getBoundingBox().grow(16.0, 4.0, 16.0));
            if (list.isEmpty()) {
                return true;
            }
            this.this$0.setWololoTarget(list.get(EvokerEntity.access$400(this.this$0).nextInt(list.size())));
            return false;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return this.this$0.getWololoTarget() != null && this.spellWarmup > 0;
        }

        @Override
        public void resetTask() {
            super.resetTask();
            this.this$0.setWololoTarget(null);
        }

        @Override
        protected void castSpell() {
            SheepEntity sheepEntity = this.this$0.getWololoTarget();
            if (sheepEntity != null && sheepEntity.isAlive()) {
                sheepEntity.setFleeceColor(DyeColor.RED);
            }
        }

        @Override
        protected int getCastWarmupTime() {
            return 1;
        }

        @Override
        protected int getCastingTime() {
            return 1;
        }

        @Override
        protected int getCastingInterval() {
            return 1;
        }

        @Override
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.ENTITY_EVOKER_PREPARE_WOLOLO;
        }

        @Override
        protected SpellcastingIllagerEntity.SpellType getSpellType() {
            return SpellcastingIllagerEntity.SpellType.WOLOLO;
        }

        private static boolean lambda$new$0(LivingEntity livingEntity) {
            return ((SheepEntity)livingEntity).getFleeceColor() == DyeColor.BLUE;
        }
    }
}

