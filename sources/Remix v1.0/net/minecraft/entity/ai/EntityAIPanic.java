package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityAIPanic extends EntityAIBase
{
    protected double speed;
    private EntityCreature theEntityCreature;
    private double randPosX;
    private double randPosY;
    private double randPosZ;
    
    public EntityAIPanic(final EntityCreature p_i1645_1_, final double p_i1645_2_) {
        this.theEntityCreature = p_i1645_1_;
        this.speed = p_i1645_2_;
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
