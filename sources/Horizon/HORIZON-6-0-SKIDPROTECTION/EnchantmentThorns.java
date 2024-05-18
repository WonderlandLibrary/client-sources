package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class EnchantmentThorns extends Enchantment
{
    private static final String ÇŽÉ = "CL_00000122";
    
    public EnchantmentThorns(final int p_i45764_1_, final ResourceLocation_1975012498 p_i45764_2_, final int p_i45764_3_) {
        super(p_i45764_1_, p_i45764_2_, p_i45764_3_, EnumEnchantmentType.Âµá€);
        this.Â("thorns");
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
        return 3;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack p_92089_1_) {
        return p_92089_1_.HorizonCode_Horizon_È() instanceof ItemArmor || super.HorizonCode_Horizon_È(p_92089_1_);
    }
    
    @Override
    public void Â(final EntityLivingBase p_151367_1_, final Entity p_151367_2_, final int p_151367_3_) {
        final Random var4 = p_151367_1_.ˆÐƒØ();
        final ItemStack var5 = EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.áˆºÑ¢Õ, p_151367_1_);
        if (HorizonCode_Horizon_È(p_151367_3_, var4)) {
            p_151367_2_.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È((Entity)p_151367_1_), Â(p_151367_3_, var4));
            p_151367_2_.HorizonCode_Horizon_È("damage.thorns", 0.5f, 1.0f);
            if (var5 != null) {
                var5.HorizonCode_Horizon_È(3, p_151367_1_);
            }
        }
        else if (var5 != null) {
            var5.HorizonCode_Horizon_È(1, p_151367_1_);
        }
    }
    
    public static boolean HorizonCode_Horizon_È(final int p_92094_0_, final Random p_92094_1_) {
        return p_92094_0_ > 0 && p_92094_1_.nextFloat() < 0.15f * p_92094_0_;
    }
    
    public static int Â(final int p_92095_0_, final Random p_92095_1_) {
        return (p_92095_0_ > 10) ? (p_92095_0_ - 10) : (1 + p_92095_1_.nextInt(4));
    }
}
