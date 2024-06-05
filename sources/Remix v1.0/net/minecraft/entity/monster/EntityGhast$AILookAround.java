package net.minecraft.entity.monster;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;

class AILookAround extends EntityAIBase
{
    private EntityGhast field_179472_a;
    
    public AILookAround() {
        this.field_179472_a = EntityGhast.this;
        this.setMutexBits(2);
    }
    
    @Override
    public boolean shouldExecute() {
        return true;
    }
    
    @Override
    public void updateTask() {
        if (this.field_179472_a.getAttackTarget() == null) {
            final EntityGhast field_179472_a = this.field_179472_a;
            final EntityGhast field_179472_a2 = this.field_179472_a;
            final float n = -(float)Math.atan2(this.field_179472_a.motionX, this.field_179472_a.motionZ) * 180.0f / 3.1415927f;
            field_179472_a2.rotationYaw = n;
            field_179472_a.renderYawOffset = n;
        }
        else {
            final EntityLivingBase var1 = this.field_179472_a.getAttackTarget();
            final double var2 = 64.0;
            if (var1.getDistanceSqToEntity(this.field_179472_a) < var2 * var2) {
                final double var3 = var1.posX - this.field_179472_a.posX;
                final double var4 = var1.posZ - this.field_179472_a.posZ;
                final EntityGhast field_179472_a3 = this.field_179472_a;
                final EntityGhast field_179472_a4 = this.field_179472_a;
                final float n2 = -(float)Math.atan2(var3, var4) * 180.0f / 3.1415927f;
                field_179472_a4.rotationYaw = n2;
                field_179472_a3.renderYawOffset = n2;
            }
        }
    }
}
