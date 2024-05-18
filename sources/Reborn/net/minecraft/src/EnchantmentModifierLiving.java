package net.minecraft.src;

final class EnchantmentModifierLiving implements IEnchantmentModifier
{
    public int livingModifier;
    public EntityLiving entityLiving;
    
    private EnchantmentModifierLiving() {
    }
    
    @Override
    public void calculateModifier(final Enchantment par1Enchantment, final int par2) {
        this.livingModifier += par1Enchantment.calcModifierLiving(par2, this.entityLiving);
    }
    
    EnchantmentModifierLiving(final Empty3 par1Empty3) {
        this();
    }
}
