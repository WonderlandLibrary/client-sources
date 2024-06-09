package HORIZON-6-0-SKIDPROTECTION;

public class EnchantmentOxygen extends Enchantment
{
    private static final String ÇŽÉ = "CL_00000120";
    
    public EnchantmentOxygen(final int p_i45766_1_, final ResourceLocation_1975012498 p_i45766_2_, final int p_i45766_3_) {
        super(p_i45766_1_, p_i45766_2_, p_i45766_3_, EnumEnchantmentType.Ó);
        this.Â("oxygen");
    }
    
    @Override
    public int Â(final int p_77321_1_) {
        return 10 * p_77321_1_;
    }
    
    @Override
    public int Ý(final int p_77317_1_) {
        return this.Â(p_77317_1_) + 30;
    }
    
    @Override
    public int Ø­áŒŠá() {
        return 3;
    }
}
