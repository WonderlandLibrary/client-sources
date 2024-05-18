package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenMelon extends WorldGenerator
{
    private static final String HorizonCode_Horizon_È = "CL_00000424";
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        for (int var4 = 0; var4 < 64; ++var4) {
            final BlockPos var5 = p_180709_3_.Â(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
            if (Blocks.ˆÅ.Ø­áŒŠá(worldIn, var5) && worldIn.Â(var5.Âµá€()).Ý() == Blocks.Ø­áŒŠá) {
                worldIn.HorizonCode_Horizon_È(var5, Blocks.ˆÅ.¥à(), 2);
            }
        }
        return true;
    }
}
