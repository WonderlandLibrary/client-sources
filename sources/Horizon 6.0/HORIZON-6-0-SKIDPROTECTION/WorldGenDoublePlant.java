package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenDoublePlant extends WorldGenerator
{
    private BlockDoublePlant.Â HorizonCode_Horizon_È;
    private static final String Â = "CL_00000408";
    
    public void HorizonCode_Horizon_È(final BlockDoublePlant.Â p_180710_1_) {
        this.HorizonCode_Horizon_È = p_180710_1_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        boolean var4 = false;
        for (int var5 = 0; var5 < 64; ++var5) {
            final BlockPos var6 = p_180709_3_.Â(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
            if (worldIn.Ø­áŒŠá(var6) && (!worldIn.£à.Å() || var6.Â() < 254) && Blocks.ÇªÇªÉ.Ø­áŒŠá(worldIn, var6)) {
                Blocks.ÇªÇªÉ.HorizonCode_Horizon_È(worldIn, var6, this.HorizonCode_Horizon_È, 2);
                var4 = true;
            }
        }
        return var4;
    }
}
