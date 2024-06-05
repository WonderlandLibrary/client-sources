package net.minecraft.entity.monster;

import net.minecraft.entity.ai.*;

class AISlimeHop extends EntityAIBase
{
    private EntitySlime field_179458_a;
    
    public AISlimeHop() {
        this.field_179458_a = EntitySlime.this;
        this.setMutexBits(5);
    }
    
    @Override
    public boolean shouldExecute() {
        return true;
    }
    
    @Override
    public void updateTask() {
        ((SlimeMoveHelper)this.field_179458_a.getMoveHelper()).func_179921_a(1.0);
    }
}
