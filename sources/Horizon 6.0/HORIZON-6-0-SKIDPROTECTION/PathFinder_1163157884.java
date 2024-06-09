package HORIZON-6-0-SKIDPROTECTION;

public class PathFinder_1163157884
{
    private Path_685325104 HorizonCode_Horizon_È;
    private PathPoint[] Â;
    private NodeProcessor Ý;
    private static final String Ø­áŒŠá = "CL_00000576";
    
    public PathFinder_1163157884(final NodeProcessor p_i45557_1_) {
        this.HorizonCode_Horizon_È = new Path_685325104();
        this.Â = new PathPoint[32];
        this.Ý = p_i45557_1_;
    }
    
    public PathEntity HorizonCode_Horizon_È(final IBlockAccess p_176188_1_, final Entity p_176188_2_, final Entity p_176188_3_, final float p_176188_4_) {
        return this.HorizonCode_Horizon_È(p_176188_1_, p_176188_2_, p_176188_3_.ŒÏ, p_176188_3_.£É().Â, p_176188_3_.Ê, p_176188_4_);
    }
    
    public PathEntity HorizonCode_Horizon_È(final IBlockAccess p_180782_1_, final Entity p_180782_2_, final BlockPos p_180782_3_, final float p_180782_4_) {
        return this.HorizonCode_Horizon_È(p_180782_1_, p_180782_2_, p_180782_3_.HorizonCode_Horizon_È() + 0.5f, p_180782_3_.Â() + 0.5f, p_180782_3_.Ý() + 0.5f, p_180782_4_);
    }
    
    private PathEntity HorizonCode_Horizon_È(final IBlockAccess p_176189_1_, final Entity p_176189_2_, final double p_176189_3_, final double p_176189_5_, final double p_176189_7_, final float p_176189_9_) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
        this.Ý.HorizonCode_Horizon_È(p_176189_1_, p_176189_2_);
        final PathPoint var10 = this.Ý.HorizonCode_Horizon_È(p_176189_2_);
        final PathPoint var11 = this.Ý.HorizonCode_Horizon_È(p_176189_2_, p_176189_3_, p_176189_5_, p_176189_7_);
        final PathEntity var12 = this.HorizonCode_Horizon_È(p_176189_2_, var10, var11, p_176189_9_);
        this.Ý.HorizonCode_Horizon_È();
        return var12;
    }
    
    private PathEntity HorizonCode_Horizon_È(final Entity p_176187_1_, final PathPoint p_176187_2_, final PathPoint p_176187_3_, final float p_176187_4_) {
        p_176187_2_.Âµá€ = 0.0f;
        p_176187_2_.Ó = p_176187_2_.Â(p_176187_3_);
        p_176187_2_.à = p_176187_2_.Ó;
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_176187_2_);
        PathPoint var5 = p_176187_2_;
        while (!this.HorizonCode_Horizon_È.Ý()) {
            final PathPoint var6 = this.HorizonCode_Horizon_È.Â();
            if (var6.equals(p_176187_3_)) {
                return this.HorizonCode_Horizon_È(p_176187_2_, p_176187_3_);
            }
            if (var6.Â(p_176187_3_) < var5.Â(p_176187_3_)) {
                var5 = var6;
            }
            var6.áŒŠÆ = true;
            for (int var7 = this.Ý.HorizonCode_Horizon_È(this.Â, p_176187_1_, var6, p_176187_3_, p_176187_4_), var8 = 0; var8 < var7; ++var8) {
                final PathPoint var9 = this.Â[var8];
                final float var10 = var6.Âµá€ + var6.Â(var9);
                if (var10 < p_176187_4_ * 2.0f && (!var9.HorizonCode_Horizon_È() || var10 < var9.Âµá€)) {
                    var9.Ø = var6;
                    var9.Âµá€ = var10;
                    var9.Ó = var9.Â(p_176187_3_);
                    if (var9.HorizonCode_Horizon_È()) {
                        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var9, var9.Âµá€ + var9.Ó);
                    }
                    else {
                        var9.à = var9.Âµá€ + var9.Ó;
                        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var9);
                    }
                }
            }
        }
        if (var5 == p_176187_2_) {
            return null;
        }
        return this.HorizonCode_Horizon_È(p_176187_2_, var5);
    }
    
    private PathEntity HorizonCode_Horizon_È(final PathPoint p_75853_1_, final PathPoint p_75853_2_) {
        int var3 = 1;
        for (PathPoint var4 = p_75853_2_; var4.Ø != null; var4 = var4.Ø) {
            ++var3;
        }
        final PathPoint[] var5 = new PathPoint[var3];
        PathPoint var4 = p_75853_2_;
        --var3;
        var5[var3] = p_75853_2_;
        while (var4.Ø != null) {
            var4 = var4.Ø;
            --var3;
            var5[var3] = var4;
        }
        return new PathEntity(var5);
    }
}
