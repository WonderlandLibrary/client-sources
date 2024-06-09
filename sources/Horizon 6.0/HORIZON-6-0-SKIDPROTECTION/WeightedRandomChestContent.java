package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import java.util.Collections;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class WeightedRandomChestContent extends WeightedRandom.HorizonCode_Horizon_È
{
    private ItemStack HorizonCode_Horizon_È;
    private int Â;
    private int Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001505";
    
    public WeightedRandomChestContent(final Item_1028566121 p_i45311_1_, final int p_i45311_2_, final int p_i45311_3_, final int p_i45311_4_, final int p_i45311_5_) {
        super(p_i45311_5_);
        this.HorizonCode_Horizon_È = new ItemStack(p_i45311_1_, 1, p_i45311_2_);
        this.Â = p_i45311_3_;
        this.Ø­áŒŠá = p_i45311_4_;
    }
    
    public WeightedRandomChestContent(final ItemStack p_i1558_1_, final int p_i1558_2_, final int p_i1558_3_, final int p_i1558_4_) {
        super(p_i1558_4_);
        this.HorizonCode_Horizon_È = p_i1558_1_;
        this.Â = p_i1558_2_;
        this.Ø­áŒŠá = p_i1558_3_;
    }
    
    public static void HorizonCode_Horizon_È(final Random p_177630_0_, final List p_177630_1_, final IInventory p_177630_2_, final int p_177630_3_) {
        for (int var4 = 0; var4 < p_177630_3_; ++var4) {
            final WeightedRandomChestContent var5 = (WeightedRandomChestContent)WeightedRandom.HorizonCode_Horizon_È(p_177630_0_, p_177630_1_);
            final int var6 = var5.Â + p_177630_0_.nextInt(var5.Ø­áŒŠá - var5.Â + 1);
            if (var5.HorizonCode_Horizon_È.Â() >= var6) {
                final ItemStack var7 = var5.HorizonCode_Horizon_È.áˆºÑ¢Õ();
                var7.Â = var6;
                p_177630_2_.Ý(p_177630_0_.nextInt(p_177630_2_.áŒŠÆ()), var7);
            }
            else {
                for (int var8 = 0; var8 < var6; ++var8) {
                    final ItemStack var9 = var5.HorizonCode_Horizon_È.áˆºÑ¢Õ();
                    var9.Â = 1;
                    p_177630_2_.Ý(p_177630_0_.nextInt(p_177630_2_.áŒŠÆ()), var9);
                }
            }
        }
    }
    
    public static void HorizonCode_Horizon_È(final Random p_177631_0_, final List p_177631_1_, final TileEntityDispenser p_177631_2_, final int p_177631_3_) {
        for (int var4 = 0; var4 < p_177631_3_; ++var4) {
            final WeightedRandomChestContent var5 = (WeightedRandomChestContent)WeightedRandom.HorizonCode_Horizon_È(p_177631_0_, p_177631_1_);
            final int var6 = var5.Â + p_177631_0_.nextInt(var5.Ø­áŒŠá - var5.Â + 1);
            if (var5.HorizonCode_Horizon_È.Â() >= var6) {
                final ItemStack var7 = var5.HorizonCode_Horizon_È.áˆºÑ¢Õ();
                var7.Â = var6;
                p_177631_2_.Ý(p_177631_0_.nextInt(p_177631_2_.áŒŠÆ()), var7);
            }
            else {
                for (int var8 = 0; var8 < var6; ++var8) {
                    final ItemStack var9 = var5.HorizonCode_Horizon_È.áˆºÑ¢Õ();
                    var9.Â = 1;
                    p_177631_2_.Ý(p_177631_0_.nextInt(p_177631_2_.áŒŠÆ()), var9);
                }
            }
        }
    }
    
    public static List HorizonCode_Horizon_È(final List p_177629_0_, final WeightedRandomChestContent... p_177629_1_) {
        final ArrayList var2 = Lists.newArrayList((Iterable)p_177629_0_);
        Collections.addAll(var2, p_177629_1_);
        return var2;
    }
}
