package HORIZON-6-0-SKIDPROTECTION;

public class EnchantmentDigging extends Enchantment
{
    private static final String ÇŽÉ = "CL_00000104";
    
    protected EnchantmentDigging(final int p_i45772_1_, final ResourceLocation_1975012498 p_i45772_2_, final int p_i45772_3_) {
        super(p_i45772_1_, p_i45772_2_, p_i45772_3_, EnumEnchantmentType.Ø);
        this.Â("digging");
    }
    
    @Override
    public int Â(final int p_77321_1_) {
        return 1 + 10 * (p_77321_1_ - 1);
    }
    
    @Override
    public int Ý(final int p_77317_1_) {
        return super.Â(p_77317_1_) + 50;
    }
    
    @Override
    public int Ø­áŒŠá() {
        return 5;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack p_92089_1_) {
        return p_92089_1_.HorizonCode_Horizon_È() == Items.áˆºà || super.HorizonCode_Horizon_È(p_92089_1_);
    }
}
