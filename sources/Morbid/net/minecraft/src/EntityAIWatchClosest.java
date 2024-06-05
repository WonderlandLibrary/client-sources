package net.minecraft.src;

public class EntityAIWatchClosest extends EntityAIBase
{
    private EntityLiving theWatcher;
    protected Entity closestEntity;
    private float field_75333_c;
    private int lookTime;
    private float field_75331_e;
    private Class watchedClass;
    
    public EntityAIWatchClosest(final EntityLiving par1EntityLiving, final Class par2Class, final float par3) {
        this.theWatcher = par1EntityLiving;
        this.watchedClass = par2Class;
        this.field_75333_c = par3;
        this.field_75331_e = 0.02f;
        this.setMutexBits(2);
    }
    
    public EntityAIWatchClosest(final EntityLiving par1EntityLiving, final Class par2Class, final float par3, final float par4) {
        this.theWatcher = par1EntityLiving;
        this.watchedClass = par2Class;
        this.field_75333_c = par3;
        this.field_75331_e = par4;
        this.setMutexBits(2);
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.theWatcher.getRNG().nextFloat() >= this.field_75331_e) {
            return false;
        }
        if (this.watchedClass == EntityPlayer.class) {
            this.closestEntity = this.theWatcher.worldObj.getClosestPlayerToEntity(this.theWatcher, this.field_75333_c);
        }
        else {
            this.closestEntity = this.theWatcher.worldObj.findNearestEntityWithinAABB(this.watchedClass, this.theWatcher.boundingBox.expand(this.field_75333_c, 3.0, this.field_75333_c), this.theWatcher);
        }
        return this.closestEntity != null;
    }
    
    @Override
    public boolean continueExecuting() {
        return this.closestEntity.isEntityAlive() && this.theWatcher.getDistanceSqToEntity(this.closestEntity) <= this.field_75333_c * this.field_75333_c && this.lookTime > 0;
    }
    
    @Override
    public void startExecuting() {
        this.lookTime = 40 + this.theWatcher.getRNG().nextInt(40);
    }
    
    @Override
    public void resetTask() {
        this.closestEntity = null;
    }
    
    @Override
    public void updateTask() {
        this.theWatcher.getLookHelper().setLookPosition(this.closestEntity.posX, this.closestEntity.posY + this.closestEntity.getEyeHeight(), this.closestEntity.posZ, 10.0f, this.theWatcher.getVerticalFaceSpeed());
        --this.lookTime;
    }
}
