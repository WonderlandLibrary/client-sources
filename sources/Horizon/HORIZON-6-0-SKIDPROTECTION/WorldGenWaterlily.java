package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenWaterlily extends WorldGenerator
{
    private static final String HorizonCode_Horizon_È = "CL_00000189";
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        for (int var4 = 0; var4 < 10; ++var4) {
            final int var5 = p_180709_3_.HorizonCode_Horizon_È() + p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8);
            final int var6 = p_180709_3_.Â() + p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4);
            final int var7 = p_180709_3_.Ý() + p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8);
            if (worldIn.Ø­áŒŠá(new BlockPos(var5, var6, var7)) && Blocks.Œá.Ø­áŒŠá(worldIn, new BlockPos(var5, var6, var7))) {
                worldIn.HorizonCode_Horizon_È(new BlockPos(var5, var6, var7), Blocks.Œá.¥à(), 2);
            }
        }
        return true;
    }
}
