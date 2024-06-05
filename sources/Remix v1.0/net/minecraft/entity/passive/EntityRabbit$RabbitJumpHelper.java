package net.minecraft.entity.passive;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;

public class RabbitJumpHelper extends EntityJumpHelper
{
    private EntityRabbit field_180070_c;
    private boolean field_180068_d;
    
    public RabbitJumpHelper(final EntityRabbit p_i45863_2_) {
        super(p_i45863_2_);
        this.field_180068_d = false;
        this.field_180070_c = p_i45863_2_;
    }
    
    public boolean func_180067_c() {
        return this.isJumping;
    }
    
    public boolean func_180065_d() {
        return this.field_180068_d;
    }
    
    public void func_180066_a(final boolean p_180066_1_) {
        this.field_180068_d = p_180066_1_;
    }
    
    @Override
    public void doJump() {
        if (this.isJumping) {
            this.field_180070_c.func_175524_b(EnumMoveType.STEP);
            this.isJumping = false;
        }
    }
}
