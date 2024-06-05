package net.minecraft.src;

public class EntityAIPanic extends EntityAIBase
{
    private EntityCreature theEntityCreature;
    private float speed;
    private double randPosX;
    private double randPosY;
    private double randPosZ;
    
    public EntityAIPanic(final EntityCreature par1EntityCreature, final float par2) {
        this.theEntityCreature = par1EntityCreature;
        this.speed = par2;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.theEntityCreature.getAITarget() == null && !this.theEntityCreature.isBurning()) {
            return false;
        }
        final Vec3 var1 = RandomPositionGenerator.findRandomTarget(this.theEntityCreature, 5, 4);
        if (var1 == null) {
            return false;
        }
        this.randPosX = var1.xCoord;
        this.randPosY = var1.yCoord;
        this.randPosZ = var1.zCoord;
        return true;
    }
    
    @Override
    public void startExecuting() {
        this.theEntityCreature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.theEntityCreature.getNavigator().noPath();
    }
}
