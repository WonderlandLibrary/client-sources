package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenIcePath extends WorldGenerator
{
    private Block HorizonCode_Horizon_È;
    private int Â;
    private static final String Ý = "CL_00000416";
    
    public WorldGenIcePath(final int p_i45454_1_) {
        this.HorizonCode_Horizon_È = Blocks.ŠÂµÏ;
        this.Â = p_i45454_1_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, BlockPos p_180709_3_) {
        while (worldIn.Ø­áŒŠá(p_180709_3_) && p_180709_3_.Â() > 2) {
            p_180709_3_ = p_180709_3_.Âµá€();
        }
        if (worldIn.Â(p_180709_3_).Ý() != Blocks.ˆà¢) {
            return false;
        }
        final int var4 = p_180709_2_.nextInt(this.Â - 2) + 2;
        final byte var5 = 1;
        for (int var6 = p_180709_3_.HorizonCode_Horizon_È() - var4; var6 <= p_180709_3_.HorizonCode_Horizon_È() + var4; ++var6) {
            for (int var7 = p_180709_3_.Ý() - var4; var7 <= p_180709_3_.Ý() + var4; ++var7) {
                final int var8 = var6 - p_180709_3_.HorizonCode_Horizon_È();
                final int var9 = var7 - p_180709_3_.Ý();
                if (var8 * var8 + var9 * var9 <= var4 * var4) {
                    for (int var10 = p_180709_3_.Â() - var5; var10 <= p_180709_3_.Â() + var5; ++var10) {
                        final BlockPos var11 = new BlockPos(var6, var10, var7);
                        final Block var12 = worldIn.Â(var11).Ý();
                        if (var12 == Blocks.Âµá€ || var12 == Blocks.ˆà¢ || var12 == Blocks.¥Ï) {
                            worldIn.HorizonCode_Horizon_È(var11, this.HorizonCode_Horizon_È.¥à(), 2);
                        }
                    }
                }
            }
        }
        return true;
    }
}
