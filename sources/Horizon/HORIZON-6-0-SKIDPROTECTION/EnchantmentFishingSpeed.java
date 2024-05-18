package HORIZON-6-0-SKIDPROTECTION;

public class EnchantmentFishingSpeed extends Enchantment
{
    private static final String ÇŽÉ = "CL_00000117";
    
    protected EnchantmentFishingSpeed(final int p_i45769_1_, final ResourceLocation_1975012498 p_i45769_2_, final int p_i45769_3_, final EnumEnchantmentType p_i45769_4_) {
        super(p_i45769_1_, p_i45769_2_, p_i45769_3_, p_i45769_4_);
        this.Â("fishingSpeed");
    }
    
    @Override
    public int Â(final int p_77321_1_) {
        return 15 + (p_77321_1_ - 1) * 9;
    }
    
    @Override
    public int Ý(final int p_77317_1_) {
        return super.Â(p_77317_1_) + 50;
    }
    
    @Override
    public int Ø­áŒŠá() {
        return 3;
    }
}
