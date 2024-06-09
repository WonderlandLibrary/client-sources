package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityPolarBear extends EntityAnimal
{
    private static final DataParameter<Boolean> field_189798_bx = EntityDataManager.<Boolean>createKey(EntityPolarBear.class, DataSerializers.BOOLEAN);
    private float field_189799_by;
    private float field_189800_bz;
    private int field_189797_bB;

    public EntityPolarBear(World p_i47154_1_)
    {
        super(p_i47154_1_);
        this.setSize(1.3F, 1.4F);
    }

    public EntityAgeable createChild(EntityAgeable ageable)
    {
        return new EntityPolarBear(this.worldObj);
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean isBreedingItem(ItemStack stack)
    {
        return false;
    }

    protected void initEntityAI()
    {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityPolarBear.AIMeleeAttack());
        this.tasks.addTask(1, new EntityPolarBear.AIPanic());
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityPolarBear.AIHurtByTarget());
        this.targetTasks.addTask(2, new EntityPolarBear.AIAttackPlayer());
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    }

    protected SoundEvent getAmbientSound()
    {
        return this.isChild() ? SoundEvents.field_190027_es : SoundEvents.field_190026_er;
    }

    protected SoundEvent getHurtSound()
    {
        return SoundEvents.field_190029_eu;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.field_190028_et;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.field_190030_ev, 0.15F, 1.0F);
    }

    protected void func_189796_de()
    {
        if (this.field_189797_bB <= 0)
        {
            this.playSound(SoundEvents.field_190031_ew, 1.0F, 1.0F);
            this.field_189797_bB = 40;
        }
    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
        return LootTableList.field_189969_E;
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(field_189798_bx, Boolean.valueOf(false));
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (this.worldObj.isRemote)
        {
            this.field_189799_by = this.field_189800_bz;

            if (this.func_189793_df())
            {
                this.field_189800_bz = MathHelper.clamp_float(this.field_189800_bz + 1.0F, 0.0F, 6.0F);
            }
            else
            {
                this.field_189800_bz = MathHelper.clamp_float(this.field_189800_bz - 1.0F, 0.0F, 6.0F);
            }
        }

        if (this.field_189797_bB > 0)
        {
            --this.field_189797_bB;
        }
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

        if (flag)
        {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    public boolean func_189793_df()
    {
        return ((Boolean)this.dataManager.get(field_189798_bx)).booleanValue();
    }

    public void func_189794_p(boolean p_189794_1_)
    {
        this.dataManager.set(field_189798_bx, Boolean.valueOf(p_189794_1_));
    }

    public float func_189795_r(float p_189795_1_)
    {
        return (this.field_189799_by + (this.field_189800_bz - this.field_189799_by) * p_189795_1_) / 6.0F;
    }

    protected float func_189749_co()
    {
        return 0.98F;
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata)
    {
        if (livingdata instanceof EntityPolarBear.GroupData)
        {
            if (((EntityPolarBear.GroupData)livingdata).field_190101_a)
            {
                this.setGrowingAge(-24000);
            }
        }
        else
        {
            EntityPolarBear.GroupData entitypolarbear$groupdata = new EntityPolarBear.GroupData();
            entitypolarbear$groupdata.field_190101_a = true;
            livingdata = entitypolarbear$groupdata;
        }

        return (IEntityLivingData)livingdata;
    }

    class AIAttackPlayer extends EntityAINearestAttackableTarget<EntityPlayer>
    {
        public AIAttackPlayer()
        {
            super(EntityPolarBear.this, EntityPlayer.class, 20, true, true, null);
        }

        public boolean shouldExecute()
        {
            if (EntityPolarBear.this.isChild())
            {
                return false;
            }
            else
            {
                if (super.shouldExecute())
                {
                    for (EntityPolarBear entitypolarbear : EntityPolarBear.this.worldObj.getEntitiesWithinAABB(EntityPolarBear.class, EntityPolarBear.this.getEntityBoundingBox().expand(8.0D, 4.0D, 8.0D)))
                    {
                        if (entitypolarbear.isChild())
                        {
                            return true;
                        }
                    }
                }

                EntityPolarBear.this.setAttackTarget((EntityLivingBase)null);
                return false;
            }
        }

        protected double getTargetDistance()
        {
            return super.getTargetDistance() * 0.5D;
        }
    }

    class AIHurtByTarget extends EntityAIHurtByTarget
    {
        public AIHurtByTarget()
        {
            super(EntityPolarBear.this, false, new Class[0]);
        }

        public void startExecuting()
        {
            super.startExecuting();

            if (EntityPolarBear.this.isChild())
            {
                this.func_190105_f();
                this.resetTask();
            }
        }

        protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn)
        {
            if (creatureIn instanceof EntityPolarBear && !((EntityPolarBear)creatureIn).isChild())
            {
                super.setEntityAttackTarget(creatureIn, entityLivingBaseIn);
            }
        }
    }

    class AIMeleeAttack extends EntityAIAttackMelee
    {
        public AIMeleeAttack()
        {
            super(EntityPolarBear.this, 1.25D, true);
        }

        protected void func_190102_a(EntityLivingBase p_190102_1_, double p_190102_2_)
        {
            double d0 = this.getAttackReachSqr(p_190102_1_);

            if (p_190102_2_ <= d0 && this.attackTick <= 0)
            {
                this.attackTick = 20;
                this.attacker.attackEntityAsMob(p_190102_1_);
                EntityPolarBear.this.func_189794_p(false);
            }
            else if (p_190102_2_ <= d0 * 2.0D)
            {
                if (this.attackTick <= 0)
                {
                    EntityPolarBear.this.func_189794_p(false);
                    this.attackTick = 20;
                }

                if (this.attackTick <= 10)
                {
                    EntityPolarBear.this.func_189794_p(true);
                    EntityPolarBear.this.func_189796_de();
                }
            }
            else
            {
                this.attackTick = 20;
                EntityPolarBear.this.func_189794_p(false);
            }
        }

        public void resetTask()
        {
            EntityPolarBear.this.func_189794_p(false);
            super.resetTask();
        }

        protected double getAttackReachSqr(EntityLivingBase attackTarget)
        {
            return (double)(4.0F + attackTarget.width);
        }
    }

    class AIPanic extends EntityAIPanic
    {
        public AIPanic()
        {
            super(EntityPolarBear.this, 2.0D);
        }

        public boolean shouldExecute()
        {
            return !EntityPolarBear.this.isChild() && !EntityPolarBear.this.isBurning() ? false : super.shouldExecute();
        }
    }

    static class GroupData implements IEntityLivingData
    {
        public boolean field_190101_a;

        private GroupData()
        {
        }
    }
}
