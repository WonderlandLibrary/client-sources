package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenVines extends WorldGenerator
{
    private static final String HorizonCode_Horizon_È = "CL_00000439";
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, BlockPos p_180709_3_) {
        while (p_180709_3_.Â() < 128) {
            if (worldIn.Ø­áŒŠá(p_180709_3_)) {
                for (final EnumFacing var7 : EnumFacing.Ý.HorizonCode_Horizon_È.HorizonCode_Horizon_È()) {
                    if (Blocks.ÇŽà.HorizonCode_Horizon_È(worldIn, p_180709_3_, var7)) {
                        final IBlockState var8 = Blocks.ÇŽà.¥à().HorizonCode_Horizon_È(BlockVine.à¢, var7 == EnumFacing.Ý).HorizonCode_Horizon_È(BlockVine.ŠÂµà, var7 == EnumFacing.Ó).HorizonCode_Horizon_È(BlockVine.¥à, var7 == EnumFacing.Ø­áŒŠá).HorizonCode_Horizon_È(BlockVine.Âµà, var7 == EnumFacing.Âµá€);
                        worldIn.HorizonCode_Horizon_È(p_180709_3_, var8, 2);
                        break;
                    }
                }
            }
            else {
                p_180709_3_ = p_180709_3_.Â(p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), 0, p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4));
            }
            p_180709_3_ = p_180709_3_.Ø­áŒŠá();
        }
        return true;
    }
}
