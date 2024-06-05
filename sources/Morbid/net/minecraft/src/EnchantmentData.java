package net.minecraft.src;

public class EnchantmentData extends WeightedRandomItem
{
    public final Enchantment enchantmentobj;
    public final int enchantmentLevel;
    
    public EnchantmentData(final Enchantment par1Enchantment, final int par2) {
        super(par1Enchantment.getWeight());
        this.enchantmentobj = par1Enchantment;
        this.enchantmentLevel = par2;
    }
    
    public EnchantmentData(final int par1, final int par2) {
        this(Enchantment.enchantmentsList[par1], par2);
    }
}
