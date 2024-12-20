package net.minecraft.src;

public class EntityAICreeperSwell extends EntityAIBase
{
    EntityCreeper swellingCreeper;
    EntityLiving creeperAttackTarget;
    
    public EntityAICreeperSwell(final EntityCreeper par1EntityCreeper) {
        this.swellingCreeper = par1EntityCreeper;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLiving var1 = this.swellingCreeper.getAttackTarget();
        return this.swellingCreeper.getCreeperState() > 0 || (var1 != null && this.swellingCreeper.getDistanceSqToEntity(var1) < 9.0);
    }
    
    @Override
    public void startExecuting() {
        this.swellingCreeper.getNavigator().clearPathEntity();
        this.creeperAttackTarget = this.swellingCreeper.getAttackTarget();
    }
    
    @Override
    public void resetTask() {
        this.creeperAttackTarget = null;
    }
    
    @Override
    public void updateTask() {
        if (this.creeperAttackTarget == null) {
            this.swellingCreeper.setCreeperState(-1);
        }
        else if (this.swellingCreeper.getDistanceSqToEntity(this.creeperAttackTarget) > 49.0) {
            this.swellingCreeper.setCreeperState(-1);
        }
        else if (!this.swellingCreeper.getEntitySenses().canSee(this.creeperAttackTarget)) {
            this.swellingCreeper.setCreeperState(-1);
        }
        else {
            this.swellingCreeper.setCreeperState(1);
        }
    }
}
