package net.minecraft.enchantment;

import net.minecraft.entity.*;

static final class HurtIterator implements IModifier
{
    public EntityLivingBase field_151364_a;
    public Entity field_151363_b;
    
    private HurtIterator() {
    }
    
    HurtIterator(final Object p_i45360_1_) {
        this();
    }
    
    @Override
    public void calculateModifier(final Enchantment p_77493_1_, final int p_77493_2_) {
        p_77493_1_.func_151367_b(this.field_151364_a, this.field_151363_b, p_77493_2_);
    }
}
