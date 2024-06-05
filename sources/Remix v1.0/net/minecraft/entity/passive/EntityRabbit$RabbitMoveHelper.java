package net.minecraft.entity.passive;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;

class RabbitMoveHelper extends EntityMoveHelper
{
    private EntityRabbit field_179929_g;
    
    public RabbitMoveHelper() {
        super(EntityRabbit.this);
        this.field_179929_g = EntityRabbit.this;
    }
    
    @Override
    public void onUpdateMoveHelper() {
        if (this.field_179929_g.onGround && !this.field_179929_g.func_175523_cj()) {
            this.field_179929_g.func_175515_b(0.0);
        }
        super.onUpdateMoveHelper();
    }
}
