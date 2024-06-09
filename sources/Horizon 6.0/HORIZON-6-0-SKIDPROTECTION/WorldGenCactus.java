package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenCactus extends WorldGenerator
{
    private static final String HorizonCode_Horizon_È = "CL_00000404";
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        for (int var4 = 0; var4 < 10; ++var4) {
            final BlockPos var5 = p_180709_3_.Â(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
            if (worldIn.Ø­áŒŠá(var5)) {
                for (int var6 = 1 + p_180709_2_.nextInt(p_180709_2_.nextInt(3) + 1), var7 = 0; var7 < var6; ++var7) {
                    if (Blocks.Ñ¢Ç.áŒŠÆ(worldIn, var5)) {
                        worldIn.HorizonCode_Horizon_È(var5.Â(var7), Blocks.Ñ¢Ç.¥à(), 2);
                    }
                }
            }
        }
        return true;
    }
}
