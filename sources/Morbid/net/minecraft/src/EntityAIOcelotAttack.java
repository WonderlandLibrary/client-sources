package net.minecraft.src;

public class EntityAIOcelotAttack extends EntityAIBase
{
    World theWorld;
    EntityLiving theEntity;
    EntityLiving theVictim;
    int attackCountdown;
    
    public EntityAIOcelotAttack(final EntityLiving par1EntityLiving) {
        this.attackCountdown = 0;
        this.theEntity = par1EntityLiving;
        this.theWorld = par1EntityLiving.worldObj;
        this.setMutexBits(3);
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLiving var1 = this.theEntity.getAttackTarget();
        if (var1 == null) {
            return false;
        }
        this.theVictim = var1;
        return true;
    }
    
    @Override
    public boolean continueExecuting() {
        return this.theVictim.isEntityAlive() && this.theEntity.getDistanceSqToEntity(this.theVictim) <= 225.0 && (!this.theEntity.getNavigator().noPath() || this.shouldExecute());
    }
    
    @Override
    public void resetTask() {
        this.theVictim = null;
        this.theEntity.getNavigator().clearPathEntity();
    }
    
    @Override
    public void updateTask() {
        this.theEntity.getLookHelper().setLookPositionWithEntity(this.theVictim, 30.0f, 30.0f);
        final double var1 = this.theEntity.width * 2.0f * this.theEntity.width * 2.0f;
        final double var2 = this.theEntity.getDistanceSq(this.theVictim.posX, this.theVictim.boundingBox.minY, this.theVictim.posZ);
        float var3 = 0.23f;
        if (var2 > var1 && var2 < 16.0) {
            var3 = 0.4f;
        }
        else if (var2 < 225.0) {
            var3 = 0.18f;
        }
        this.theEntity.getNavigator().tryMoveToEntityLiving(this.theVictim, var3);
        this.attackCountdown = Math.max(this.attackCountdown - 1, 0);
        if (var2 <= var1 && this.attackCountdown <= 0) {
            this.attackCountdown = 20;
            this.theEntity.attackEntityAsMob(this.theVictim);
        }
    }
}
