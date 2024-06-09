package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Random;

public class WorldGenBlockBlob extends WorldGenerator
{
    private final Block HorizonCode_Horizon_È;
    private final int Â;
    private static final String Ý = "CL_00000402";
    
    public WorldGenBlockBlob(final Block p_i45450_1_, final int p_i45450_2_) {
        super(false);
        this.HorizonCode_Horizon_È = p_i45450_1_;
        this.Â = p_i45450_2_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, BlockPos p_180709_3_) {
        while (p_180709_3_.Â() > 3) {
            if (!worldIn.Ø­áŒŠá(p_180709_3_.Âµá€())) {
                final Block var4 = worldIn.Â(p_180709_3_.Âµá€()).Ý();
                if (var4 == Blocks.Ø­áŒŠá || var4 == Blocks.Âµá€) {
                    break;
                }
                if (var4 == Blocks.Ý) {
                    break;
                }
            }
            p_180709_3_ = p_180709_3_.Âµá€();
        }
        if (p_180709_3_.Â() <= 3) {
            return false;
        }
        for (int var5 = this.Â, var6 = 0; var5 >= 0 && var6 < 3; ++var6) {
            final int var7 = var5 + p_180709_2_.nextInt(2);
            final int var8 = var5 + p_180709_2_.nextInt(2);
            final int var9 = var5 + p_180709_2_.nextInt(2);
            final float var10 = (var7 + var8 + var9) * 0.333f + 0.5f;
            for (final BlockPos var12 : BlockPos.Â(p_180709_3_.Â(-var7, -var8, -var9), p_180709_3_.Â(var7, var8, var9))) {
                if (var12.Ó(p_180709_3_) <= var10 * var10) {
                    worldIn.HorizonCode_Horizon_È(var12, this.HorizonCode_Horizon_È.¥à(), 4);
                }
            }
            p_180709_3_ = p_180709_3_.Â(-(var5 + 1) + p_180709_2_.nextInt(2 + var5 * 2), 0 - p_180709_2_.nextInt(2), -(var5 + 1) + p_180709_2_.nextInt(2 + var5 * 2));
        }
        return true;
    }
}
