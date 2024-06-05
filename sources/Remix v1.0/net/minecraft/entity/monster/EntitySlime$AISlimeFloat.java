package net.minecraft.entity.monster;

import net.minecraft.entity.ai.*;
import net.minecraft.pathfinding.*;

class AISlimeFloat extends EntityAIBase
{
    private EntitySlime field_179457_a;
    
    public AISlimeFloat() {
        this.field_179457_a = EntitySlime.this;
        this.setMutexBits(5);
        ((PathNavigateGround)EntitySlime.this.getNavigator()).func_179693_d(true);
    }
    
    @Override
    public boolean shouldExecute() {
        return this.field_179457_a.isInWater() || this.field_179457_a.func_180799_ab();
    }
    
    @Override
    public void updateTask() {
        if (this.field_179457_a.getRNG().nextFloat() < 0.8f) {
            this.field_179457_a.getJumpHelper().setJumping();
        }
        ((SlimeMoveHelper)this.field_179457_a.getMoveHelper()).func_179921_a(1.2);
    }
}
