package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenReed extends WorldGenerator
{
    private static final String HorizonCode_Horizon_È = "CL_00000429";
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        for (int var4 = 0; var4 < 20; ++var4) {
            final BlockPos var5 = p_180709_3_.Â(p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), 0, p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4));
            if (worldIn.Ø­áŒŠá(var5)) {
                final BlockPos var6 = var5.Âµá€();
                if (worldIn.Â(var6.Ø()).Ý().Ó() == Material.Ø || worldIn.Â(var6.áŒŠÆ()).Ý().Ó() == Material.Ø || worldIn.Â(var6.Ó()).Ý().Ó() == Material.Ø || worldIn.Â(var6.à()).Ý().Ó() == Material.Ø) {
                    for (int var7 = 2 + p_180709_2_.nextInt(p_180709_2_.nextInt(3) + 1), var8 = 0; var8 < var7; ++var8) {
                        if (Blocks.Ðƒáƒ.áŒŠÆ(worldIn, var5)) {
                            worldIn.HorizonCode_Horizon_È(var5.Â(var8), Blocks.Ðƒáƒ.¥à(), 2);
                        }
                    }
                }
            }
        }
        return true;
    }
}
