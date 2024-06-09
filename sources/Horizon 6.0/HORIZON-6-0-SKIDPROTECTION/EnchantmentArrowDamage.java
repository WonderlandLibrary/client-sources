package HORIZON-6-0-SKIDPROTECTION;

public class EnchantmentArrowDamage extends Enchantment
{
    private static final String ÇŽÉ = "CL_00000098";
    
    public EnchantmentArrowDamage(final int p_i45778_1_, final ResourceLocation_1975012498 p_i45778_2_, final int p_i45778_3_) {
        super(p_i45778_1_, p_i45778_2_, p_i45778_3_, EnumEnchantmentType.ÂµÈ);
        this.Â("arrowDamage");
    }
    
    @Override
    public int Â(final int p_77321_1_) {
        return 1 + (p_77321_1_ - 1) * 10;
    }
    
    @Override
    public int Ý(final int p_77317_1_) {
        return this.Â(p_77317_1_) + 15;
    }
    
    @Override
    public int Ø­áŒŠá() {
        return 5;
    }
}
