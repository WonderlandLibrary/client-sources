package HORIZON-6-0-SKIDPROTECTION;

public class EnchantmentUntouching extends Enchantment
{
    private static final String ÇŽÉ = "CL_00000123";
    
    protected EnchantmentUntouching(final int p_i45763_1_, final ResourceLocation_1975012498 p_i45763_2_, final int p_i45763_3_) {
        super(p_i45763_1_, p_i45763_2_, p_i45763_3_, EnumEnchantmentType.Ø);
        this.Â("untouching");
    }
    
    @Override
    public int Â(final int p_77321_1_) {
        return 15;
    }
    
    @Override
    public int Ý(final int p_77317_1_) {
        return super.Â(p_77317_1_) + 50;
    }
    
    @Override
    public int Ø­áŒŠá() {
        return 1;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final Enchantment p_77326_1_) {
        return super.HorizonCode_Horizon_È(p_77326_1_) && p_77326_1_.ŒÏ != EnchantmentUntouching.µÕ.ŒÏ;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack p_92089_1_) {
        return p_92089_1_.HorizonCode_Horizon_È() == Items.áˆºà || super.HorizonCode_Horizon_È(p_92089_1_);
    }
}
