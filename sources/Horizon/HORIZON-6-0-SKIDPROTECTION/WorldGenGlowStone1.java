package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenGlowStone1 extends WorldGenerator
{
    private static final String HorizonCode_Horizon_È = "CL_00000419";
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        if (!worldIn.Ø­áŒŠá(p_180709_3_)) {
            return false;
        }
        if (worldIn.Â(p_180709_3_.Ø­áŒŠá()).Ý() != Blocks.áŒŠÔ) {
            return false;
        }
        worldIn.HorizonCode_Horizon_È(p_180709_3_, Blocks.£Ø­à.¥à(), 2);
        for (int var4 = 0; var4 < 1500; ++var4) {
            final BlockPos var5 = p_180709_3_.Â(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), -p_180709_2_.nextInt(12), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
            if (worldIn.Â(var5).Ý().Ó() == Material.HorizonCode_Horizon_È) {
                int var6 = 0;
                for (final EnumFacing var10 : EnumFacing.values()) {
                    if (worldIn.Â(var5.HorizonCode_Horizon_È(var10)).Ý() == Blocks.£Ø­à) {
                        ++var6;
                    }
                    if (var6 > 1) {
                        break;
                    }
                }
                if (var6 == 1) {
                    worldIn.HorizonCode_Horizon_È(var5, Blocks.£Ø­à.¥à(), 2);
                }
            }
        }
        return true;
    }
}
