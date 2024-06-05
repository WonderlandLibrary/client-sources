package net.minecraft.entity.monster;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;

class AISlimeAttack extends EntityAIBase
{
    private EntitySlime field_179466_a;
    private int field_179465_b;
    
    public AISlimeAttack() {
        this.field_179466_a = EntitySlime.this;
        this.setMutexBits(2);
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLivingBase var1 = this.field_179466_a.getAttackTarget();
        return var1 != null && var1.isEntityAlive();
    }
    
    @Override
    public void startExecuting() {
        this.field_179465_b = 300;
        super.startExecuting();
    }
    
    @Override
    public boolean continueExecuting() {
        final EntityLivingBase var1 = this.field_179466_a.getAttackTarget();
        boolean b;
        if (var1 == null) {
            b = false;
        }
        else if (!var1.isEntityAlive()) {
            b = false;
        }
        else {
            final int field_179465_b = this.field_179465_b - 1;
            this.field_179465_b = field_179465_b;
            b = (field_179465_b > 0);
        }
        return b;
    }
    
    @Override
    public void updateTask() {
        this.field_179466_a.faceEntity(this.field_179466_a.getAttackTarget(), 10.0f, 10.0f);
        ((SlimeMoveHelper)this.field_179466_a.getMoveHelper()).func_179920_a(this.field_179466_a.rotationYaw, this.field_179466_a.canDamagePlayer());
    }
}
