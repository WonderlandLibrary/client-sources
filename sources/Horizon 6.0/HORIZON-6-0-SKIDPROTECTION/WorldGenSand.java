package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenSand extends WorldGenerator
{
    private Block HorizonCode_Horizon_È;
    private int Â;
    private static final String Ý = "CL_00000431";
    
    public WorldGenSand(final Block p_i45462_1_, final int p_i45462_2_) {
        this.HorizonCode_Horizon_È = p_i45462_1_;
        this.Â = p_i45462_2_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        if (worldIn.Â(p_180709_3_).Ý().Ó() != Material.Ø) {
            return false;
        }
        final int var4 = p_180709_2_.nextInt(this.Â - 2) + 2;
        final byte var5 = 2;
        for (int var6 = p_180709_3_.HorizonCode_Horizon_È() - var4; var6 <= p_180709_3_.HorizonCode_Horizon_È() + var4; ++var6) {
            for (int var7 = p_180709_3_.Ý() - var4; var7 <= p_180709_3_.Ý() + var4; ++var7) {
                final int var8 = var6 - p_180709_3_.HorizonCode_Horizon_È();
                final int var9 = var7 - p_180709_3_.Ý();
                if (var8 * var8 + var9 * var9 <= var4 * var4) {
                    for (int var10 = p_180709_3_.Â() - var5; var10 <= p_180709_3_.Â() + var5; ++var10) {
                        final BlockPos var11 = new BlockPos(var6, var10, var7);
                        final Block var12 = worldIn.Â(var11).Ý();
                        if (var12 == Blocks.Âµá€ || var12 == Blocks.Ø­áŒŠá) {
                            worldIn.HorizonCode_Horizon_È(var11, this.HorizonCode_Horizon_È.¥à(), 2);
                        }
                    }
                }
            }
        }
        return true;
    }
}
