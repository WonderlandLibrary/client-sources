package HORIZON-6-0-SKIDPROTECTION;

public class EnchantmentLootBonus extends Enchantment
{
    private static final String ÇŽÉ = "CL_00000119";
    
    protected EnchantmentLootBonus(final int p_i45767_1_, final ResourceLocation_1975012498 p_i45767_2_, final int p_i45767_3_, final EnumEnchantmentType p_i45767_4_) {
        super(p_i45767_1_, p_i45767_2_, p_i45767_3_, p_i45767_4_);
        if (p_i45767_4_ == EnumEnchantmentType.Ø) {
            this.Â("lootBonusDigger");
        }
        else if (p_i45767_4_ == EnumEnchantmentType.áŒŠÆ) {
            this.Â("lootBonusFishing");
        }
        else {
            this.Â("lootBonus");
        }
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
    
    @Override
    public boolean HorizonCode_Horizon_È(final Enchantment p_77326_1_) {
        return super.HorizonCode_Horizon_È(p_77326_1_) && p_77326_1_.ŒÏ != EnchantmentLootBonus.¥Æ.ŒÏ;
    }
}
