package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;

public class EntityAISwimming extends EntityAIBase
{
    private EntityLiving theEntity;
    
    public EntityAISwimming(final EntityLiving p_i1624_1_) {
        this.theEntity = p_i1624_1_;
        this.setMutexBits(4);
        ((PathNavigateGround)p_i1624_1_.getNavigator()).func_179693_d(true);
    }
    
    @Override
    public boolean shouldExecute() {
        return this.theEntity.isInWater() || this.theEntity.func_180799_ab();
    }
    
    @Override
    public void updateTask() {
        if (this.theEntity.getRNG().nextFloat() < 0.8f) {
            this.theEntity.getJumpHelper().setJumping();
        }
    }
}
