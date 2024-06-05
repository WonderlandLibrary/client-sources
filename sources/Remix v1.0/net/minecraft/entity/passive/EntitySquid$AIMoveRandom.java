package net.minecraft.entity.passive;

import net.minecraft.entity.ai.*;
import net.minecraft.util.*;

class AIMoveRandom extends EntityAIBase
{
    private EntitySquid field_179476_a;
    
    AIMoveRandom() {
        this.field_179476_a = EntitySquid.this;
    }
    
    @Override
    public boolean shouldExecute() {
        return true;
    }
    
    @Override
    public void updateTask() {
        final int var1 = this.field_179476_a.getAge();
        if (var1 > 100) {
            this.field_179476_a.func_175568_b(0.0f, 0.0f, 0.0f);
        }
        else if (this.field_179476_a.getRNG().nextInt(50) == 0 || !EntitySquid.access$000(this.field_179476_a) || !this.field_179476_a.func_175567_n()) {
            final float var2 = this.field_179476_a.getRNG().nextFloat() * 3.1415927f * 2.0f;
            final float var3 = MathHelper.cos(var2) * 0.2f;
            final float var4 = -0.1f + this.field_179476_a.getRNG().nextFloat() * 0.2f;
            final float var5 = MathHelper.sin(var2) * 0.2f;
            this.field_179476_a.func_175568_b(var3, var4, var5);
        }
    }
}
