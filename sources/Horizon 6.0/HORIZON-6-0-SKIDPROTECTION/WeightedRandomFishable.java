package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WeightedRandomFishable extends WeightedRandom.HorizonCode_Horizon_È
{
    private final ItemStack HorizonCode_Horizon_È;
    private float Â;
    private boolean Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001664";
    
    public WeightedRandomFishable(final ItemStack p_i45317_1_, final int p_i45317_2_) {
        super(p_i45317_2_);
        this.HorizonCode_Horizon_È = p_i45317_1_;
    }
    
    public ItemStack HorizonCode_Horizon_È(final Random p_150708_1_) {
        final ItemStack var2 = this.HorizonCode_Horizon_È.áˆºÑ¢Õ();
        if (this.Â > 0.0f) {
            final int var3 = (int)(this.Â * this.HorizonCode_Horizon_È.áŒŠÆ());
            int var4 = var2.áŒŠÆ() - p_150708_1_.nextInt(p_150708_1_.nextInt(var3) + 1);
            if (var4 > var3) {
                var4 = var3;
            }
            if (var4 < 1) {
                var4 = 1;
            }
            var2.Â(var4);
        }
        if (this.Ø­áŒŠá) {
            EnchantmentHelper.HorizonCode_Horizon_È(p_150708_1_, var2, 30);
        }
        return var2;
    }
    
    public WeightedRandomFishable HorizonCode_Horizon_È(final float p_150709_1_) {
        this.Â = p_150709_1_;
        return this;
    }
    
    public WeightedRandomFishable HorizonCode_Horizon_È() {
        this.Ø­áŒŠá = true;
        return this;
    }
}
