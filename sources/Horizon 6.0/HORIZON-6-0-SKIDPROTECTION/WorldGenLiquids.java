package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenLiquids extends WorldGenerator
{
    private Block HorizonCode_Horizon_È;
    private static final String Â = "CL_00000434";
    
    public WorldGenLiquids(final Block p_i45465_1_) {
        this.HorizonCode_Horizon_È = p_i45465_1_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        if (worldIn.Â(p_180709_3_.Ø­áŒŠá()).Ý() != Blocks.Ý) {
            return false;
        }
        if (worldIn.Â(p_180709_3_.Âµá€()).Ý() != Blocks.Ý) {
            return false;
        }
        if (worldIn.Â(p_180709_3_).Ý().Ó() != Material.HorizonCode_Horizon_È && worldIn.Â(p_180709_3_).Ý() != Blocks.Ý) {
            return false;
        }
        int var4 = 0;
        if (worldIn.Â(p_180709_3_.Ø()).Ý() == Blocks.Ý) {
            ++var4;
        }
        if (worldIn.Â(p_180709_3_.áŒŠÆ()).Ý() == Blocks.Ý) {
            ++var4;
        }
        if (worldIn.Â(p_180709_3_.Ó()).Ý() == Blocks.Ý) {
            ++var4;
        }
        if (worldIn.Â(p_180709_3_.à()).Ý() == Blocks.Ý) {
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
        if (var4 == 3 && var5 == 1) {
            worldIn.HorizonCode_Horizon_È(p_180709_3_, this.HorizonCode_Horizon_È.¥à(), 2);
            worldIn.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, p_180709_3_, p_180709_2_);
        }
        return true;
    }
}
