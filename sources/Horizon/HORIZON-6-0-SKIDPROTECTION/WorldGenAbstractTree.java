package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public abstract class WorldGenAbstractTree extends WorldGenerator
{
    private static final String HorizonCode_Horizon_È = "CL_00000399";
    
    public WorldGenAbstractTree(final boolean p_i45448_1_) {
        super(p_i45448_1_);
    }
    
    protected boolean HorizonCode_Horizon_È(final Block p_150523_1_) {
        return p_150523_1_.Ó() == Material.HorizonCode_Horizon_È || p_150523_1_.Ó() == Material.áˆºÑ¢Õ || p_150523_1_ == Blocks.Ø­áŒŠá || p_150523_1_ == Blocks.Âµá€ || p_150523_1_ == Blocks.¥Æ || p_150523_1_ == Blocks.Ø­à || p_150523_1_ == Blocks.Ø || p_150523_1_ == Blocks.ÇŽà;
    }
    
    public void Â(final World worldIn, final Random p_180711_2_, final BlockPos p_180711_3_) {
    }
    
    protected void HorizonCode_Horizon_È(final World worldIn, final BlockPos p_175921_2_) {
        if (worldIn.Â(p_175921_2_).Ý() != Blocks.Âµá€) {
            this.HorizonCode_Horizon_È(worldIn, p_175921_2_, Blocks.Âµá€.¥à());
        }
    }
}
