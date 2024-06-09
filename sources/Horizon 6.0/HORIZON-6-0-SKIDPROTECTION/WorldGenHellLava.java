package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenHellLava extends WorldGenerator
{
    private final Block HorizonCode_Horizon_È;
    private final boolean Â;
    private static final String Ý = "CL_00000414";
    
    public WorldGenHellLava(final Block p_i45453_1_, final boolean p_i45453_2_) {
        this.HorizonCode_Horizon_È = p_i45453_1_;
        this.Â = p_i45453_2_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        if (worldIn.Â(p_180709_3_.Ø­áŒŠá()).Ý() != Blocks.áŒŠÔ) {
            return false;
        }
        if (worldIn.Â(p_180709_3_).Ý().Ó() != Material.HorizonCode_Horizon_È && worldIn.Â(p_180709_3_).Ý() != Blocks.áŒŠÔ) {
            return false;
        }
        int var4 = 0;
        if (worldIn.Â(p_180709_3_.Ø()).Ý() == Blocks.áŒŠÔ) {
            ++var4;
        }
        if (worldIn.Â(p_180709_3_.áŒŠÆ()).Ý() == Blocks.áŒŠÔ) {
            ++var4;
        }
        if (worldIn.Â(p_180709_3_.Ó()).Ý() == Blocks.áŒŠÔ) {
            ++var4;
        }
        if (worldIn.Â(p_180709_3_.à()).Ý() == Blocks.áŒŠÔ) {
            ++var4;
        }
        if (worldIn.Â(p_180709_3_.Âµá€()).Ý() == Blocks.áŒŠÔ) {
            ++var4;
        }
        int var5 = 0;
        if (worldIn.Ø­áŒŠá(p_180709_3_.Ø())) {
            ++var5;
        }
        if (worldIn.Ø­áŒŠá(p_180709_3_.áŒŠÆ())) {
            ++var5;
        }
        if (worldIn.Ø­áŒŠá(p_180709_3_.Ó())) {
            ++var5;
        }
        if (worldIn.Ø­áŒŠá(p_180709_3_.à())) {
            ++var5;
        }
        if (worldIn.Ø­áŒŠá(p_180709_3_.Âµá€())) {
            ++var5;
        }
        if ((!this.Â && var4 == 4 && var5 == 1) || var4 == 5) {
            worldIn.HorizonCode_Horizon_È(p_180709_3_, this.HorizonCode_Horizon_È.¥à(), 2);
            worldIn.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, p_180709_3_, p_180709_2_);
        }
        return true;
    }
}
