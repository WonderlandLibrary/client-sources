package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenDeadBush extends WorldGenerator
{
    private static final String HorizonCode_Horizon_È = "CL_00000406";
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, BlockPos p_180709_3_) {
        Block var4;
        while (((var4 = worldIn.Â(p_180709_3_).Ý()).Ó() == Material.HorizonCode_Horizon_È || var4.Ó() == Material.áˆºÑ¢Õ) && p_180709_3_.Â() > 0) {
            p_180709_3_ = p_180709_3_.Âµá€();
        }
        for (int var5 = 0; var5 < 4; ++var5) {
            final BlockPos var6 = p_180709_3_.Â(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
            if (worldIn.Ø­áŒŠá(var6) && Blocks.á€.Ó(worldIn, var6, Blocks.á€.¥à())) {
                worldIn.HorizonCode_Horizon_È(var6, Blocks.á€.¥à(), 2);
            }
        }
        return true;
    }
}
