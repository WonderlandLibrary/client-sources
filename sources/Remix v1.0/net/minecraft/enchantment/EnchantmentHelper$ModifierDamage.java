package net.minecraft.enchantment;

import net.minecraft.util.*;

static final class ModifierDamage implements IModifier
{
    public int damageModifier;
    public DamageSource source;
    
    private ModifierDamage() {
    }
    
    ModifierDamage(final Object p_i1929_1_) {
        this();
    }
    
    @Override
    public void calculateModifier(final Enchantment p_77493_1_, final int p_77493_2_) {
        this.damageModifier += p_77493_1_.calcModifierDamage(p_77493_2_, this.source);
    }
}
