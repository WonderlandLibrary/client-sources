package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.BlockPosition;
import net.minecraft.util.Vec3;

public class EntityAIMoveTowardsRestriction extends EntityAIBase
{
    private EntityCreature theEntity;
    private double movePosX;
    private double movePosY;
    private double movePosZ;
    private double movementSpeed;

    public EntityAIMoveTowardsRestriction(EntityCreature creatureIn, double speedIn)
    {
        this.theEntity = creatureIn;
        this.movementSpeed = speedIn;
        this.setMutexBits(1);
    }

    public boolean shouldExecute()
    {
        if (this.theEntity.isWithinHomeDistanceCurrentPosition())
        {
            return false;
        }
        else
        {
            BlockPosition blockpos = this.theEntity.getHomePosition();
            Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 16, 7, new Vec3((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ()));

            if (vec3 == null)
            {
                return false;
            }
            else
            {
                this.movePosX = vec3.xCoord;
                this.movePosY = vec3.yCoord;
                this.movePosZ = vec3.zCoord;
                return true;
            }
        }
    }

    public boolean continueExecuting()
    {
        return !this.theEntity.getNavigator().noPath();
    }

    public void startExecuting()
    {
        this.theEntity.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.movementSpeed);
    }
}
