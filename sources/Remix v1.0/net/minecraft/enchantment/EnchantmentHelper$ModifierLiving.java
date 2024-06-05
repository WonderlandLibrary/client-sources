package net.minecraft.enchantment;

import net.minecraft.entity.*;

static final class ModifierLiving implements IModifier
{
    public float livingModifier;
    public EnumCreatureAttribute entityLiving;
    
    private ModifierLiving() {
    }
    
    ModifierLiving(final Object p_i1928_1_) {
        this();
    }
    
    @Override
    public void calculateModifier(final Enchantment p_77493_1_, final int p_77493_2_) {
        this.livingModifier += p_77493_1_.func_152376_a(p_77493_2_, this.entityLiving);
    }
}
