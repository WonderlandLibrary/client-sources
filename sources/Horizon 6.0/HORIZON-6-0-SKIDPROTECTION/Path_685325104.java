package HORIZON-6-0-SKIDPROTECTION;

public class Path_685325104
{
    private PathPoint[] HorizonCode_Horizon_È;
    private int Â;
    private static final String Ý = "CL_00000573";
    
    public Path_685325104() {
        this.HorizonCode_Horizon_È = new PathPoint[1024];
    }
    
    public PathPoint HorizonCode_Horizon_È(final PathPoint point) {
        if (point.Ø­áŒŠá >= 0) {
            throw new IllegalStateException("OW KNOWS!");
        }
        if (this.Â == this.HorizonCode_Horizon_È.length) {
            final PathPoint[] var2 = new PathPoint[this.Â << 1];
            System.arraycopy(this.HorizonCode_Horizon_È, 0, var2, 0, this.Â);
            this.HorizonCode_Horizon_È = var2;
        }
        this.HorizonCode_Horizon_È[this.Â] = point;
        point.Ø­áŒŠá = this.Â;
        this.HorizonCode_Horizon_È(this.Â++);
        return point;
    }
    
    public void HorizonCode_Horizon_È() {
        this.Â = 0;
    }
    
    public PathPoint Â() {
        final PathPoint var1 = this.HorizonCode_Horizon_È[0];
        final PathPoint[] horizonCode_Horizon_È = this.HorizonCode_Horizon_È;
        final int n = 0;
        final PathPoint[] horizonCode_Horizon_È2 = this.HorizonCode_Horizon_È;
        final int â = this.Â - 1;
        this.Â = â;
        horizonCode_Horizon_È[n] = horizonCode_Horizon_È2[â];
        this.HorizonCode_Horizon_È[this.Â] = null;
        if (this.Â > 0) {
            this.Â(0);
        }
        var1.Ø­áŒŠá = -1;
        return var1;
    }
    
    public void HorizonCode_Horizon_È(final PathPoint p_75850_1_, final float p_75850_2_) {
        final float var3 = p_75850_1_.à;
        p_75850_1_.à = p_75850_2_;
        if (p_75850_2_ < var3) {
            this.HorizonCode_Horizon_È(p_75850_1_.Ø­áŒŠá);
        }
        else {
            this.Â(p_75850_1_.Ø­áŒŠá);
        }
    }
    
    private void HorizonCode_Horizon_È(int p_75847_1_) {
        final PathPoint var2 = this.HorizonCode_Horizon_È[p_75847_1_];
        final float var3 = var2.à;
        while (p_75847_1_ > 0) {
            final int var4 = p_75847_1_ - 1 >> 1;
            final PathPoint var5 = this.HorizonCode_Horizon_È[var4];
            if (var3 >= var5.à) {
                break;
            }
            this.HorizonCode_Horizon_È[p_75847_1_] = var5;
            var5.Ø­áŒŠá = p_75847_1_;
            p_75847_1_ = var4;
        }
        this.HorizonCode_Horizon_È[p_75847_1_] = var2;
        var2.Ø­áŒŠá = p_75847_1_;
    }
    
    private void Â(int p_75846_1_) {
        final PathPoint var2 = this.HorizonCode_Horizon_È[p_75846_1_];
        final float var3 = var2.à;
        while (true) {
            final int var4 = 1 + (p_75846_1_ << 1);
            final int var5 = var4 + 1;
            if (var4 >= this.Â) {
                break;
            }
            final PathPoint var6 = this.HorizonCode_Horizon_È[var4];
            final float var7 = var6.à;
            PathPoint var8;
            float var9;
            if (var5 >= this.Â) {
                var8 = null;
                var9 = Float.POSITIVE_INFINITY;
            }
            else {
                var8 = this.HorizonCode_Horizon_È[var5];
                var9 = var8.à;
            }
            if (var7 < var9) {
                if (var7 >= var3) {
                    break;
                }
                this.HorizonCode_Horizon_È[p_75846_1_] = var6;
                var6.Ø­áŒŠá = p_75846_1_;
                p_75846_1_ = var4;
            }
            else {
                if (var9 >= var3) {
                    break;
                }
                this.HorizonCode_Horizon_È[p_75846_1_] = var8;
                var8.Ø­áŒŠá = p_75846_1_;
                p_75846_1_ = var5;
            }
        }
        this.HorizonCode_Horizon_È[p_75846_1_] = var2;
        var2.Ø­áŒŠá = p_75846_1_;
    }
    
    public boolean Ý() {
        return this.Â == 0;
    }
}
