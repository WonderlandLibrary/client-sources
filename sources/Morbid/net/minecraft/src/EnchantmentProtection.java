package net.minecraft.src;

public class EnchantmentProtection extends Enchantment
{
    private static final String[] protectionName;
    private static final int[] baseEnchantability;
    private static final int[] levelEnchantability;
    private static final int[] thresholdEnchantability;
    public final int protectionType;
    
    static {
        protectionName = new String[] { "all", "fire", "fall", "explosion", "projectile" };
        baseEnchantability = new int[] { 1, 10, 5, 5, 3 };
        levelEnchantability = new int[] { 11, 8, 6, 8, 6 };
        thresholdEnchantability = new int[] { 20, 12, 10, 12, 15 };
    }
    
    public EnchantmentProtection(final int par1, final int par2, final int par3) {
        super(par1, par2, EnumEnchantmentType.armor);
        this.protectionType = par3;
        if (par3 == 2) {
            this.type = EnumEnchantmentType.armor_feet;
        }
    }
    
    @Override
    public int getMinEnchantability(final int par1) {
        return EnchantmentProtection.baseEnchantability[this.protectionType] + (par1 - 1) * EnchantmentProtection.levelEnchantability[this.protectionType];
    }
    
    @Override
    public int getMaxEnchantability(final int par1) {
        return this.getMinEnchantability(par1) + EnchantmentProtection.thresholdEnchantability[this.protectionType];
    }
    
    @Override
    public int getMaxLevel() {
        return 4;
    }
    
    @Override
    public int calcModifierDamage(final int par1, final DamageSource par2DamageSource) {
        if (par2DamageSource.canHarmInCreative()) {
            return 0;
        }
        final float var3 = (6 + par1 * par1) / 3.0f;
        return (this.protectionType == 0) ? MathHelper.floor_float(var3 * 0.75f) : ((this.protectionType == 1 && par2DamageSource.isFireDamage()) ? MathHelper.floor_float(var3 * 1.25f) : ((this.protectionType == 2 && par2DamageSource == DamageSource.fall) ? MathHelper.floor_float(var3 * 2.5f) : ((this.protectionType == 3 && par2DamageSource.isExplosion()) ? MathHelper.floor_float(var3 * 1.5f) : ((this.protectionType == 4 && par2DamageSource.isProjectile()) ? MathHelper.floor_float(var3 * 1.5f) : 0))));
    }
    
    @Override
    public String getName() {
        return "enchantment.protect." + EnchantmentProtection.protectionName[this.protectionType];
    }
    
    @Override
    public boolean canApplyTogether(final Enchantment par1Enchantment) {
        if (par1Enchantment instanceof EnchantmentProtection) {
            final EnchantmentProtection var2 = (EnchantmentProtection)par1Enchantment;
            return var2.protectionType != this.protectionType && (this.protectionType == 2 || var2.protectionType == 2);
        }
        return super.canApplyTogether(par1Enchantment);
    }
    
    public static int func_92093_a(final Entity par0Entity, int par1) {
        final int var2 = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.fireProtection.effectId, par0Entity.getLastActiveItems());
        if (var2 > 0) {
            par1 -= MathHelper.floor_float(par1 * var2 * 0.15f);
        }
        return par1;
    }
    
    public static double func_92092_a(final Entity par0Entity, double par1) {
        final int var3 = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.blastProtection.effectId, par0Entity.getLastActiveItems());
        if (var3 > 0) {
            par1 -= MathHelper.floor_double(par1 * (var3 * 0.15f));
        }
        return par1;
    }
}
