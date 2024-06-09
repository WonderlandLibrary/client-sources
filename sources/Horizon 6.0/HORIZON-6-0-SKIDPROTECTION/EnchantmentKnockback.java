package HORIZON-6-0-SKIDPROTECTION;

public class EnchantmentKnockback extends Enchantment
{
    private static final String ÇŽÉ = "CL_00000118";
    
    protected EnchantmentKnockback(final int p_i45768_1_, final ResourceLocation_1975012498 p_i45768_2_, final int p_i45768_3_) {
        super(p_i45768_1_, p_i45768_2_, p_i45768_3_, EnumEnchantmentType.à);
        this.Â("knockback");
    }
    
    @Override
    public int Â(final int p_77321_1_) {
        return 5 + 20 * (p_77321_1_ - 1);
    }
    
    @Override
    public int Ý(final int p_77317_1_) {
        return super.Â(p_77317_1_) + 50;
    }
    
    @Override
    public int Ø­áŒŠá() {
        return 2;
    }
}
