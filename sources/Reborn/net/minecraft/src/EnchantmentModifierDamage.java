package net.minecraft.src;

final class EnchantmentModifierDamage implements IEnchantmentModifier
{
    public int damageModifier;
    public DamageSource source;
    
    private EnchantmentModifierDamage() {
    }
    
    @Override
    public void calculateModifier(final Enchantment par1Enchantment, final int par2) {
        this.damageModifier += par1Enchantment.calcModifierDamage(par2, this.source);
    }
    
    EnchantmentModifierDamage(final Empty3 par1Empty3) {
        this();
    }
}
