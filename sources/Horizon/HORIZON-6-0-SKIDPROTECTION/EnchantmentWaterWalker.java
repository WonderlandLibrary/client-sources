package HORIZON-6-0-SKIDPROTECTION;

public class EnchantmentWaterWalker extends Enchantment
{
    private static final String ÇŽÉ = "CL_00002155";
    
    public EnchantmentWaterWalker(final int p_i45762_1_, final ResourceLocation_1975012498 p_i45762_2_, final int p_i45762_3_) {
        super(p_i45762_1_, p_i45762_2_, p_i45762_3_, EnumEnchantmentType.Ý);
        this.Â("waterWalker");
    }
    
    @Override
    public int Â(final int p_77321_1_) {
        return p_77321_1_ * 10;
    }
    
    @Override
    public int Ý(final int p_77317_1_) {
        return this.Â(p_77317_1_) + 15;
    }
    
    @Override
    public int Ø­áŒŠá() {
        return 3;
    }
}
