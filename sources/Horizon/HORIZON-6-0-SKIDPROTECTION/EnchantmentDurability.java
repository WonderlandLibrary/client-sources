package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class EnchantmentDurability extends Enchantment
{
    private static final String ÇŽÉ = "CL_00000103";
    
    protected EnchantmentDurability(final int p_i45773_1_, final ResourceLocation_1975012498 p_i45773_2_, final int p_i45773_3_) {
        super(p_i45773_1_, p_i45773_2_, p_i45773_3_, EnumEnchantmentType.áˆºÑ¢Õ);
        this.Â("durability");
    }
    
    @Override
    public int Â(final int p_77321_1_) {
        return 5 + (p_77321_1_ - 1) * 8;
    }
    
    @Override
    public int Ý(final int p_77317_1_) {
        return super.Â(p_77317_1_) + 50;
    }
    
    @Override
    public int Ø­áŒŠá() {
        return 3;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack p_92089_1_) {
        return p_92089_1_.Ø­áŒŠá() || super.HorizonCode_Horizon_È(p_92089_1_);
    }
    
    public static boolean HorizonCode_Horizon_È(final ItemStack p_92097_0_, final int p_92097_1_, final Random p_92097_2_) {
        return (!(p_92097_0_.HorizonCode_Horizon_È() instanceof ItemArmor) || p_92097_2_.nextFloat() >= 0.6f) && p_92097_2_.nextInt(p_92097_1_ + 1) > 0;
    }
}
