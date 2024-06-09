package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public abstract class WorldGenerator
{
    private final boolean HorizonCode_Horizon_È;
    private static final String Â = "CL_00000409";
    
    public WorldGenerator() {
        this(false);
    }
    
    public WorldGenerator(final boolean p_i2013_1_) {
        this.HorizonCode_Horizon_È = p_i2013_1_;
    }
    
    public abstract boolean HorizonCode_Horizon_È(final World p0, final Random p1, final BlockPos p2);
    
    public void Âµá€() {
    }
    
    protected void HorizonCode_Horizon_È(final World worldIn, final BlockPos p_175906_2_, final Block p_175906_3_) {
        this.HorizonCode_Horizon_È(worldIn, p_175906_2_, p_175906_3_, 0);
    }
    
    protected void HorizonCode_Horizon_È(final World worldIn, final BlockPos p_175905_2_, final Block p_175905_3_, final int p_175905_4_) {
        this.HorizonCode_Horizon_È(worldIn, p_175905_2_, p_175905_3_.Ý(p_175905_4_));
    }
    
    protected void HorizonCode_Horizon_È(final World worldIn, final BlockPos p_175903_2_, final IBlockState p_175903_3_) {
        if (this.HorizonCode_Horizon_È) {
            worldIn.HorizonCode_Horizon_È(p_175903_2_, p_175903_3_, 3);
        }
        else {
            worldIn.HorizonCode_Horizon_È(p_175903_2_, p_175903_3_, 2);
        }
    }
}
