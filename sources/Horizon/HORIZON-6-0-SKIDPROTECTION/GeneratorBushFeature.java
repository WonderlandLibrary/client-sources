package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class GeneratorBushFeature extends WorldGenerator
{
    private BlockBush HorizonCode_Horizon_È;
    private static final String Â = "CL_00002000";
    
    public GeneratorBushFeature(final BlockBush p_i45633_1_) {
        this.HorizonCode_Horizon_È = p_i45633_1_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        for (int var4 = 0; var4 < 64; ++var4) {
            final BlockPos var5 = p_180709_3_.Â(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
            if (worldIn.Ø­áŒŠá(var5) && (!worldIn.£à.Å() || var5.Â() < 255) && this.HorizonCode_Horizon_È.Ó(worldIn, var5, this.HorizonCode_Horizon_È.¥à())) {
                worldIn.HorizonCode_Horizon_È(var5, this.HorizonCode_Horizon_È.¥à(), 2);
            }
        }
        return true;
    }
}
