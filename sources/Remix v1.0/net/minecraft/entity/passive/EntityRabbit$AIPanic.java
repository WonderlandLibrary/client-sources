package net.minecraft.entity.passive;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;

class AIPanic extends EntityAIPanic
{
    private EntityRabbit field_179486_b;
    
    public AIPanic(final double p_i45861_2_) {
        super(EntityRabbit.this, p_i45861_2_);
        this.field_179486_b = EntityRabbit.this;
    }
    
    @Override
    public void updateTask() {
        super.updateTask();
        this.field_179486_b.func_175515_b(this.speed);
    }
}
