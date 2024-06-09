package HORIZON-6-0-SKIDPROTECTION;

public class EnchantmentWaterWorker extends Enchantment
{
    private static final String ÇŽÉ = "CL_00000124";
    
    public EnchantmentWaterWorker(final int p_i45761_1_, final ResourceLocation_1975012498 p_i45761_2_, final int p_i45761_3_) {
        super(p_i45761_1_, p_i45761_2_, p_i45761_3_, EnumEnchantmentType.Ó);
        this.Â("waterWorker");
    }
    
    @Override
    public int Â(final int p_77321_1_) {
        return 1;
    }
    
    @Override
    public int Ý(final int p_77317_1_) {
        return this.Â(p_77317_1_) + 40;
    }
    
    @Override
    public int Ø­áŒŠá() {
        return 1;
    }
}
