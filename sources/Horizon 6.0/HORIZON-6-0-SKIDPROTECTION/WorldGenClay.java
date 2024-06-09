package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenClay extends WorldGenerator
{
    private Block HorizonCode_Horizon_È;
    private int Â;
    private static final String Ý = "CL_00000405";
    
    public WorldGenClay(final int p_i2011_1_) {
        this.HorizonCode_Horizon_È = Blocks.£É;
        this.Â = p_i2011_1_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        if (worldIn.Â(p_180709_3_).Ý().Ó() != Material.Ø) {
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
                        if (var12 == Blocks.Âµá€ || var12 == Blocks.£É) {
                            worldIn.HorizonCode_Horizon_È(var11, this.HorizonCode_Horizon_È.¥à(), 2);
                        }
                    }
                }
            }
        }
        return true;
    }
}
