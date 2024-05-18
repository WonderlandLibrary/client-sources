package net.minecraft.src;

import java.util.*;

public class EntityAIHurtByTarget extends EntityAITarget
{
    boolean field_75312_a;
    EntityLiving entityPathNavigate;
    
    public EntityAIHurtByTarget(final EntityLiving par1EntityLiving, final boolean par2) {
        super(par1EntityLiving, 16.0f, false);
        this.field_75312_a = par2;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        return this.isSuitableTarget(this.taskOwner.getAITarget(), true);
    }
    
    @Override
    public boolean continueExecuting() {
        return this.taskOwner.getAITarget() != null && this.taskOwner.getAITarget() != this.entityPathNavigate;
    }
    
    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.taskOwner.getAITarget());
        this.entityPathNavigate = this.taskOwner.getAITarget();
        if (this.field_75312_a) {
            final List var1 = this.taskOwner.worldObj.getEntitiesWithinAABB(this.taskOwner.getClass(), AxisAlignedBB.getAABBPool().getAABB(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0, this.taskOwner.posY + 1.0, this.taskOwner.posZ + 1.0).expand(this.targetDistance, 10.0, this.targetDistance));
            for (final EntityLiving var3 : var1) {
                if (this.taskOwner != var3 && var3.getAttackTarget() == null) {
                    var3.setAttackTarget(this.taskOwner.getAITarget());
                }
            }
        }
        super.startExecuting();
    }
    
    @Override
    public void resetTask() {
        if (this.taskOwner.getAttackTarget() != null && this.taskOwner.getAttackTarget() instanceof EntityPlayer && ((EntityPlayer)this.taskOwner.getAttackTarget()).capabilities.disableDamage) {
            super.resetTask();
        }
    }
}
