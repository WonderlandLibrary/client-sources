package net.minecraft.src;

public class EntityAISwimming extends EntityAIBase
{
    private EntityLiving theEntity;
    
    public EntityAISwimming(final EntityLiving par1EntityLiving) {
        this.theEntity = par1EntityLiving;
        this.setMutexBits(4);
        par1EntityLiving.getNavigator().setCanSwim(true);
    }
    
    @Override
    public boolean shouldExecute() {
        return this.theEntity.isInWater() || this.theEntity.handleLavaMovement();
    }
    
    @Override
    public void updateTask() {
        if (this.theEntity.getRNG().nextFloat() < 0.8f) {
            this.theEntity.getJumpHelper().setJumping();
        }
    }
}
