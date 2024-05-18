package net.minecraft.src;

public class EntityAILookAtVillager extends EntityAIBase
{
    private EntityIronGolem theGolem;
    private EntityVillager theVillager;
    private int lookTime;
    
    public EntityAILookAtVillager(final EntityIronGolem par1EntityIronGolem) {
        this.theGolem = par1EntityIronGolem;
        this.setMutexBits(3);
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theGolem.worldObj.isDaytime()) {
            return false;
        }
        if (this.theGolem.getRNG().nextInt(8000) != 0) {
            return false;
        }
        this.theVillager = (EntityVillager)this.theGolem.worldObj.findNearestEntityWithinAABB(EntityVillager.class, this.theGolem.boundingBox.expand(6.0, 2.0, 6.0), this.theGolem);
        return this.theVillager != null;
    }
    
    @Override
    public boolean continueExecuting() {
        return this.lookTime > 0;
    }
    
    @Override
    public void startExecuting() {
        this.lookTime = 400;
        this.theGolem.setHoldingRose(true);
    }
    
    @Override
    public void resetTask() {
        this.theGolem.setHoldingRose(false);
        this.theVillager = null;
    }
    
    @Override
    public void updateTask() {
        this.theGolem.getLookHelper().setLookPositionWithEntity(this.theVillager, 30.0f, 30.0f);
        --this.lookTime;
    }
}
