package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenFlowers extends WorldGenerator
{
    private BlockFlower HorizonCode_Horizon_È;
    private IBlockState Â;
    private static final String Ý = "CL_00000410";
    
    public WorldGenFlowers(final BlockFlower p_i45632_1_, final BlockFlower.Â p_i45632_2_) {
        this.HorizonCode_Horizon_È(p_i45632_1_, p_i45632_2_);
    }
    
    public void HorizonCode_Horizon_È(final BlockFlower p_175914_1_, final BlockFlower.Â p_175914_2_) {
        this.HorizonCode_Horizon_È = p_175914_1_;
        this.Â = p_175914_1_.¥à().HorizonCode_Horizon_È(p_175914_1_.áŠ(), p_175914_2_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        for (int var4 = 0; var4 < 64; ++var4) {
            final BlockPos var5 = p_180709_3_.Â(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
            if (worldIn.Ø­áŒŠá(var5) && (!worldIn.£à.Å() || var5.Â() < 255) && this.HorizonCode_Horizon_È.Ó(worldIn, var5, this.Â)) {
                worldIn.HorizonCode_Horizon_È(var5, this.Â, 2);
            }
        }
        return true;
    }
}
