package HORIZON-6-0-SKIDPROTECTION;

public class EnchantmentArrowKnockback extends Enchantment
{
    private static final String ÇŽÉ = "CL_00000101";
    
    public EnchantmentArrowKnockback(final int p_i45775_1_, final ResourceLocation_1975012498 p_i45775_2_, final int p_i45775_3_) {
        super(p_i45775_1_, p_i45775_2_, p_i45775_3_, EnumEnchantmentType.ÂµÈ);
        this.Â("arrowKnockback");
    }
    
    @Override
    public int Â(final int p_77321_1_) {
        return 12 + (p_77321_1_ - 1) * 20;
    }
    
    @Override
    public int Ý(final int p_77317_1_) {
        return this.Â(p_77317_1_) + 25;
    }
    
    @Override
    public int Ø­áŒŠá() {
        return 2;
    }
}
