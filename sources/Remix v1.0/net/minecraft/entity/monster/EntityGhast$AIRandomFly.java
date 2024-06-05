package net.minecraft.entity.monster;

import net.minecraft.entity.ai.*;
import java.util.*;

class AIRandomFly extends EntityAIBase
{
    private EntityGhast field_179454_a;
    
    public AIRandomFly() {
        this.field_179454_a = EntityGhast.this;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityMoveHelper var1 = this.field_179454_a.getMoveHelper();
        if (!var1.isUpdating()) {
            return true;
        }
        final double var2 = var1.func_179917_d() - this.field_179454_a.posX;
        final double var3 = var1.func_179919_e() - this.field_179454_a.posY;
        final double var4 = var1.func_179918_f() - this.field_179454_a.posZ;
        final double var5 = var2 * var2 + var3 * var3 + var4 * var4;
        return var5 < 1.0 || var5 > 3600.0;
    }
    
    @Override
    public boolean continueExecuting() {
        return false;
    }
    
    @Override
    public void startExecuting() {
        final Random var1 = this.field_179454_a.getRNG();
        final double var2 = this.field_179454_a.posX + (var1.nextFloat() * 2.0f - 1.0f) * 16.0f;
        final double var3 = this.field_179454_a.posY + (var1.nextFloat() * 2.0f - 1.0f) * 16.0f;
        final double var4 = this.field_179454_a.posZ + (var1.nextFloat() * 2.0f - 1.0f) * 16.0f;
        this.field_179454_a.getMoveHelper().setMoveTo(var2, var3, var4, 1.0);
    }
}
