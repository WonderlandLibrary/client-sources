package net.minecraft.src;

import java.util.*;

public class EntityAINearestAttackableTarget extends EntityAITarget
{
    EntityLiving targetEntity;
    Class targetClass;
    int targetChance;
    private final IEntitySelector field_82643_g;
    private EntityAINearestAttackableTargetSorter theNearestAttackableTargetSorter;
    
    public EntityAINearestAttackableTarget(final EntityLiving par1EntityLiving, final Class par2Class, final float par3, final int par4, final boolean par5) {
        this(par1EntityLiving, par2Class, par3, par4, par5, false);
    }
    
    public EntityAINearestAttackableTarget(final EntityLiving par1EntityLiving, final Class par2Class, final float par3, final int par4, final boolean par5, final boolean par6) {
        this(par1EntityLiving, par2Class, par3, par4, par5, par6, null);
    }
    
    public EntityAINearestAttackableTarget(final EntityLiving par1, final Class par2, final float par3, final int par4, final boolean par5, final boolean par6, final IEntitySelector par7IEntitySelector) {
        super(par1, par3, par5, par6);
        this.targetClass = par2;
        this.targetDistance = par3;
        this.targetChance = par4;
        this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTargetSorter(this, par1);
        this.field_82643_g = par7IEntitySelector;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
            return false;
        }
        if (this.targetClass == EntityPlayer.class) {
            final EntityPlayer var1 = this.taskOwner.worldObj.getClosestVulnerablePlayerToEntity(this.taskOwner, this.targetDistance);
            if (this.isSuitableTarget(var1, false)) {
                this.targetEntity = var1;
                return true;
            }
        }
        else {
            final List var2 = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(this.targetDistance, 4.0, this.targetDistance), this.field_82643_g);
            Collections.sort((List<Object>)var2, this.theNearestAttackableTargetSorter);
            for (final Entity var4 : var2) {
                final EntityLiving var5 = (EntityLiving)var4;
                if (this.isSuitableTarget(var5, false)) {
                    this.targetEntity = var5;
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }
}
