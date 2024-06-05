package net.minecraft.enchantment;

import net.minecraft.entity.*;

static final class DamageIterator implements IModifier
{
    public EntityLivingBase field_151366_a;
    public Entity field_151365_b;
    
    private DamageIterator() {
    }
    
    DamageIterator(final Object p_i45359_1_) {
        this();
    }
    
    @Override
    public void calculateModifier(final Enchantment p_77493_1_, final int p_77493_2_) {
        p_77493_1_.func_151368_a(this.field_151366_a, this.field_151365_b, p_77493_2_);
    }
}
