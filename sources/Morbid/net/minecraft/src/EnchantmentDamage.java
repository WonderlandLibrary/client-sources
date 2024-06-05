package net.minecraft.src;

public class EnchantmentDamage extends Enchantment
{
    private static final String[] protectionName;
    private static final int[] baseEnchantability;
    private static final int[] levelEnchantability;
    private static final int[] thresholdEnchantability;
    public final int damageType;
    
    static {
        protectionName = new String[] { "all", "undead", "arthropods" };
        baseEnchantability = new int[] { 1, 5, 5 };
        levelEnchantability = new int[] { 11, 8, 8 };
        thresholdEnchantability = new int[] { 20, 20, 20 };
    }
    
    public EnchantmentDamage(final int par1, final int par2, final int par3) {
        super(par1, par2, EnumEnchantmentType.weapon);
        this.damageType = par3;
    }
    
    @Override
    public int getMinEnchantability(final int par1) {
        return EnchantmentDamage.baseEnchantability[this.damageType] + (par1 - 1) * EnchantmentDamage.levelEnchantability[this.damageType];
    }
    
    @Override
    public int getMaxEnchantability(final int par1) {
        return this.getMinEnchantability(par1) + EnchantmentDamage.thresholdEnchantability[this.damageType];
    }
    
    @Override
    public int getMaxLevel() {
        return 5;
    }
    
    @Override
    public int calcModifierLiving(final int par1, final EntityLiving par2EntityLiving) {
        return (this.damageType == 0) ? MathHelper.floor_float(par1 * 2.75f) : ((this.damageType == 1 && par2EntityLiving.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) ? MathHelper.floor_float(par1 * 4.5f) : ((this.damageType == 2 && par2EntityLiving.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) ? MathHelper.floor_float(par1 * 4.5f) : 0));
    }
    
    @Override
    public String getName() {
        return "enchantment.damage." + EnchantmentDamage.protectionName[this.damageType];
    }
    
    @Override
    public boolean canApplyTogether(final Enchantment par1Enchantment) {
        return !(par1Enchantment instanceof EnchantmentDamage);
    }
    
    @Override
    public boolean canApply(final ItemStack par1ItemStack) {
        return par1ItemStack.getItem() instanceof ItemAxe || super.canApply(par1ItemStack);
    }
}
