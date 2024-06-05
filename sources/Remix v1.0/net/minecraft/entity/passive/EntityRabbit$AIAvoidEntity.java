package net.minecraft.entity.passive;

import net.minecraft.entity.ai.*;
import com.google.common.base.*;
import net.minecraft.entity.*;

class AIAvoidEntity extends EntityAIAvoidEntity
{
    private EntityRabbit field_179511_d;
    
    public AIAvoidEntity(final Predicate p_i45865_2_, final float p_i45865_3_, final double p_i45865_4_, final double p_i45865_6_) {
        super(EntityRabbit.this, p_i45865_2_, p_i45865_3_, p_i45865_4_, p_i45865_6_);
        this.field_179511_d = EntityRabbit.this;
    }
    
    @Override
    public void updateTask() {
        super.updateTask();
    }
}
