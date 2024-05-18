package net.minecraft.src;

public class EntityAIRestrictSun extends EntityAIBase
{
    private EntityCreature theEntity;
    
    public EntityAIRestrictSun(final EntityCreature par1EntityCreature) {
        this.theEntity = par1EntityCreature;
    }
    
    @Override
    public boolean shouldExecute() {
        return this.theEntity.worldObj.isDaytime();
    }
    
    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().setAvoidSun(true);
    }
    
    @Override
    public void resetTask() {
        this.theEntity.getNavigator().setAvoidSun(false);
    }
}
