package net.minecraft.entity.monster;

import net.minecraft.entity.*;

class GroupData implements IEntityLivingData
{
    public boolean field_142048_a;
    public boolean field_142046_b;
    
    private GroupData(final boolean p_i2348_2_, final boolean p_i2348_3_) {
        this.field_142048_a = false;
        this.field_142046_b = false;
        this.field_142048_a = p_i2348_2_;
        this.field_142046_b = p_i2348_3_;
    }
    
    GroupData(final EntityZombie this$0, final boolean p_i2349_2_, final boolean p_i2349_3_, final Object p_i2349_4_) {
        this(this$0, p_i2349_2_, p_i2349_3_);
    }
}
