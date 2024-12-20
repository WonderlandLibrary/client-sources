package net.minecraft.entity.monster;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
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

import javax.annotation.Nullable;
import java.util.List;

public class EvokerEntity extends SpellcastingIllagerEntity
{
    private SheepEntity wololoTarget;

    public EvokerEntity(EntityType <? extends EvokerEntity > type, World worldIn)
    {
        super(type, worldIn);
        this.experienceValue = 10;
    }

    protected void registerGoals()
    {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new EvokerEntity.CastingSpellGoal());
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PlayerEntity.class, 8.0F, 0.6D, 1.0D));
        this.goalSelector.addGoal(4, new EvokerEntity.SummonSpellGoal());
        this.goalSelector.addGoal(5, new EvokerEntity.AttackSpellGoal());
        this.goalSelector.addGoal(6, new EvokerEntity.WololoSpellGoal());
        this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, AbstractRaiderEntity.class)).setCallsForHelp());
        this.targetSelector.addGoal(2, (new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true)).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, (new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false)).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, false));
    }

    public static AttributeModifierMap.MutableAttribute func_234289_eI_()
    {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5D).createMutableAttribute(Attributes.FOLLOW_RANGE, 12.0D).createMutableAttribute(Attributes.MAX_HEALTH, 24.0D);
    }

    protected void registerData()
    {
        super.registerData();
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);
    }

    public SoundEvent getRaidLossSound()
    {
        return SoundEvents.ENTITY_EVOKER_CELEBRATE;
    }

    public void writeAdditional(CompoundNBT compound)
    {
        super.writeAdditional(compound);
    }

    protected void updateAITasks()
    {
        super.updateAITasks();
    }

    /**
     * Returns whether this Entity is on the same team as the given Entity.
     */
    public boolean isOnSameTeam(Entity entityIn)
    {
        if (entityIn == null)
        {
            return false;
        }
        else if (entityIn == this)
        {
            return true;
        }
        else if (super.isOnSameTeam(entityIn))
        {
            return true;
        }
        else if (entityIn instanceof VexEntity)
        {
            return this.isOnSameTeam(((VexEntity)entityIn).getOwner());
        }
        else if (entityIn instanceof LivingEntity && ((LivingEntity)entityIn).getCreatureAttribute() == CreatureAttribute.ILLAGER)
        {
            return this.getTeam() == null && entityIn.getTeam() == null;
        }
        else
        {
            return false;
        }
    }

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_EVOKER_AMBIENT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_EVOKER_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_EVOKER_HURT;
    }

    private void setWololoTarget(@Nullable SheepEntity wololoTargetIn)
    {
        this.wololoTarget = wololoTargetIn;
    }

    @Nullable
    private SheepEntity getWololoTarget()
    {
        return this.wololoTarget;
    }

    protected SoundEvent getSpellSound()
    {
        return SoundEvents.ENTITY_EVOKER_CAST_SPELL;
    }

    public void applyWaveBonus(int wave, boolean p_213660_2_)
    {
    }

    class AttackSpellGoal extends SpellcastingIllagerEntity.UseSpellGoal
    {
        private AttackSpellGoal()
        {
        }

        protected int getCastingTime()
        {
            return 40;
        }

        protected int getCastingInterval()
        {
            return 100;
        }

        protected void castSpell()
        {
            LivingEntity livingentity = EvokerEntity.this.getAttackTarget();
            double d0 = Math.min(livingentity.getPosY(), EvokerEntity.this.getPosY());
            double d1 = Math.max(livingentity.getPosY(), EvokerEntity.this.getPosY()) + 1.0D;
            float f = (float)MathHelper.atan2(livingentity.getPosZ() - EvokerEntity.this.getPosZ(), livingentity.getPosX() - EvokerEntity.this.getPosX());

            if (EvokerEntity.this.getDistanceSq(livingentity) < 9.0D)
            {
                for (int i = 0; i < 5; ++i)
                {
                    float f1 = f + (float)i * (float)Math.PI * 0.4F;
                    this.spawnFangs(EvokerEntity.this.getPosX() + (double)MathHelper.cos(f1) * 1.5D, EvokerEntity.this.getPosZ() + (double)MathHelper.sin(f1) * 1.5D, d0, d1, f1, 0);
                }

                for (int k = 0; k < 8; ++k)
                {
                    float f2 = f + (float)k * (float)Math.PI * 2.0F / 8.0F + ((float)Math.PI * 2F / 5F);
                    this.spawnFangs(EvokerEntity.this.getPosX() + (double)MathHelper.cos(f2) * 2.5D, EvokerEntity.this.getPosZ() + (double)MathHelper.sin(f2) * 2.5D, d0, d1, f2, 3);
                }
            }
            else
            {
                for (int l = 0; l < 16; ++l)
                {
                    double d2 = 1.25D * (double)(l + 1);
                    int j = 1 * l;
                    this.spawnFangs(EvokerEntity.this.getPosX() + (double)MathHelper.cos(f) * d2, EvokerEntity.this.getPosZ() + (double)MathHelper.sin(f) * d2, d0, d1, f, j);
                }
            }
        }

        private void spawnFangs(double p_190876_1_, double p_190876_3_, double p_190876_5_, double p_190876_7_, float p_190876_9_, int p_190876_10_)
        {
            BlockPos blockpos = new BlockPos(p_190876_1_, p_190876_7_, p_190876_3_);
            boolean flag = false;
            double d0 = 0.0D;

            do
            {
                BlockPos blockpos1 = blockpos.down();
                BlockState blockstate = EvokerEntity.this.world.getBlockState(blockpos1);

                if (blockstate.isSolidSide(EvokerEntity.this.world, blockpos1, Direction.UP))
                {
                    if (!EvokerEntity.this.world.isAirBlock(blockpos))
                    {
                        BlockState blockstate1 = EvokerEntity.this.world.getBlockState(blockpos);
                        VoxelShape voxelshape = blockstate1.getCollisionShape(EvokerEntity.this.world, blockpos);

                        if (!voxelshape.isEmpty())
                        {
                            d0 = voxelshape.getEnd(Direction.Axis.Y);
                        }
                    }

                    flag = true;
                    break;
                }

                blockpos = blockpos.down();
            }
            while (blockpos.getY() >= MathHelper.floor(p_190876_5_) - 1);

            if (flag)
            {
                EvokerEntity.this.world.addEntity(new EvokerFangsEntity(EvokerEntity.this.world, p_190876_1_, (double)blockpos.getY() + d0, p_190876_3_, p_190876_9_, p_190876_10_, EvokerEntity.this));
            }
        }

        protected SoundEvent getSpellPrepareSound()
        {
            return SoundEvents.ENTITY_EVOKER_PREPARE_ATTACK;
        }

        protected SpellcastingIllagerEntity.SpellType getSpellType()
        {
            return SpellcastingIllagerEntity.SpellType.FANGS;
        }
    }

    class CastingSpellGoal extends SpellcastingIllagerEntity.CastingASpellGoal
    {
        private CastingSpellGoal()
        {
        }

        public void tick()
        {
            if (EvokerEntity.this.getAttackTarget() != null)
            {
                EvokerEntity.this.getLookController().setLookPositionWithEntity(EvokerEntity.this.getAttackTarget(), (float)EvokerEntity.this.getHorizontalFaceSpeed(), (float)EvokerEntity.this.getVerticalFaceSpeed());
            }
            else if (EvokerEntity.this.getWololoTarget() != null)
            {
                EvokerEntity.this.getLookController().setLookPositionWithEntity(EvokerEntity.this.getWololoTarget(), (float)EvokerEntity.this.getHorizontalFaceSpeed(), (float)EvokerEntity.this.getVerticalFaceSpeed());
            }
        }
    }

    class SummonSpellGoal extends SpellcastingIllagerEntity.UseSpellGoal
    {
        private final EntityPredicate field_220843_e = (new EntityPredicate()).setDistance(16.0D).setLineOfSiteRequired().setUseInvisibilityCheck().allowInvulnerable().allowFriendlyFire();

        private SummonSpellGoal()
        {
        }

        public boolean shouldExecute()
        {
            if (!super.shouldExecute())
            {
                return false;
            }
            else
            {
                int i = EvokerEntity.this.world.getTargettableEntitiesWithinAABB(VexEntity.class, this.field_220843_e, EvokerEntity.this, EvokerEntity.this.getBoundingBox().grow(16.0D)).size();
                return EvokerEntity.this.rand.nextInt(8) + 1 > i;
            }
        }

        protected int getCastingTime()
        {
            return 100;
        }

        protected int getCastingInterval()
        {
            return 340;
        }

        protected void castSpell()
        {
            ServerWorld serverworld = (ServerWorld)EvokerEntity.this.world;

            for (int i = 0; i < 3; ++i)
            {
                BlockPos blockpos = EvokerEntity.this.getPosition().add(-2 + EvokerEntity.this.rand.nextInt(5), 1, -2 + EvokerEntity.this.rand.nextInt(5));
                VexEntity vexentity = EntityType.VEX.create(EvokerEntity.this.world);
                vexentity.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                vexentity.onInitialSpawn(serverworld, EvokerEntity.this.world.getDifficultyForLocation(blockpos), SpawnReason.MOB_SUMMONED, (ILivingEntityData)null, (CompoundNBT)null);
                vexentity.setOwner(EvokerEntity.this);
                vexentity.setBoundOrigin(blockpos);
                vexentity.setLimitedLife(20 * (30 + EvokerEntity.this.rand.nextInt(90)));
                serverworld.func_242417_l(vexentity);
            }
        }

        protected SoundEvent getSpellPrepareSound()
        {
            return SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON;
        }

        protected SpellcastingIllagerEntity.SpellType getSpellType()
        {
            return SpellcastingIllagerEntity.SpellType.SUMMON_VEX;
        }
    }

    public class WololoSpellGoal extends SpellcastingIllagerEntity.UseSpellGoal
    {
        private final EntityPredicate wololoTargetFlags = (new EntityPredicate()).setDistance(16.0D).allowInvulnerable().setCustomPredicate((p_220844_0_) ->
        {
            return ((SheepEntity)p_220844_0_).getFleeceColor() == DyeColor.BLUE;
        });

        public boolean shouldExecute()
        {
            if (EvokerEntity.this.getAttackTarget() != null)
            {
                return false;
            }
            else if (EvokerEntity.this.isSpellcasting())
            {
                return false;
            }
            else if (EvokerEntity.this.ticksExisted < this.spellCooldown)
            {
                return false;
            }
            else if (!EvokerEntity.this.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING))
            {
                return false;
            }
            else
            {
                List<SheepEntity> list = EvokerEntity.this.world.getTargettableEntitiesWithinAABB(SheepEntity.class, this.wololoTargetFlags, EvokerEntity.this, EvokerEntity.this.getBoundingBox().grow(16.0D, 4.0D, 16.0D));

                if (list.isEmpty())
                {
                    return false;
                }
                else
                {
                    EvokerEntity.this.setWololoTarget(list.get(EvokerEntity.this.rand.nextInt(list.size())));
                    return true;
                }
            }
        }

        public boolean shouldContinueExecuting()
        {
            return EvokerEntity.this.getWololoTarget() != null && this.spellWarmup > 0;
        }

        public void resetTask()
        {
            super.resetTask();
            EvokerEntity.this.setWololoTarget((SheepEntity)null);
        }

        protected void castSpell()
        {
            SheepEntity sheepentity = EvokerEntity.this.getWololoTarget();

            if (sheepentity != null && sheepentity.isAlive())
            {
                sheepentity.setFleeceColor(DyeColor.RED);
            }
        }

        protected int getCastWarmupTime()
        {
            return 40;
        }

        protected int getCastingTime()
        {
            return 60;
        }

        protected int getCastingInterval()
        {
            return 140;
        }

        protected SoundEvent getSpellPrepareSound()
        {
            return SoundEvents.ENTITY_EVOKER_PREPARE_WOLOLO;
        }

        protected SpellcastingIllagerEntity.SpellType getSpellType()
        {
            return SpellcastingIllagerEntity.SpellType.WOLOLO;
        }
    }
}
