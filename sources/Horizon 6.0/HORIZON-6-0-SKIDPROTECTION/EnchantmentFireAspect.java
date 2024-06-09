package HORIZON-6-0-SKIDPROTECTION;

public class EnchantmentFireAspect extends Enchantment
{
    private static final String ÇŽÉ = "CL_00000116";
    
    protected EnchantmentFireAspect(final int p_i45770_1_, final ResourceLocation_1975012498 p_i45770_2_, final int p_i45770_3_) {
        super(p_i45770_1_, p_i45770_2_, p_i45770_3_, EnumEnchantmentType.à);
        this.Â("fire");
    }
    
    @Override
    public int Â(final int p_77321_1_) {
        return 10 + 20 * (p_77321_1_ - 1);
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
