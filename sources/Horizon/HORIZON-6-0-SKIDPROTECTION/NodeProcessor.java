package HORIZON-6-0-SKIDPROTECTION;

public abstract class NodeProcessor
{
    protected IBlockAccess HorizonCode_Horizon_È;
    protected IntHashMap Â;
    protected int Ý;
    protected int Ø­áŒŠá;
    protected int Âµá€;
    private static final String Ó = "CL_00001967";
    
    public NodeProcessor() {
        this.Â = new IntHashMap();
    }
    
    public void HorizonCode_Horizon_È(final IBlockAccess p_176162_1_, final Entity p_176162_2_) {
        this.HorizonCode_Horizon_È = p_176162_1_;
        this.Â.HorizonCode_Horizon_È();
        this.Ý = MathHelper.Ø­áŒŠá(p_176162_2_.áŒŠ + 1.0f);
        this.Ø­áŒŠá = MathHelper.Ø­áŒŠá(p_176162_2_.£ÂµÄ + 1.0f);
        this.Âµá€ = MathHelper.Ø­áŒŠá(p_176162_2_.áŒŠ + 1.0f);
    }
    
    public void HorizonCode_Horizon_È() {
    }
    
    protected PathPoint HorizonCode_Horizon_È(final int p_176159_1_, final int p_176159_2_, final int p_176159_3_) {
        final int var4 = PathPoint.HorizonCode_Horizon_È(p_176159_1_, p_176159_2_, p_176159_3_);
        PathPoint var5 = (PathPoint)this.Â.HorizonCode_Horizon_È(var4);
        if (var5 == null) {
            var5 = new PathPoint(p_176159_1_, p_176159_2_, p_176159_3_);
            this.Â.HorizonCode_Horizon_È(var4, var5);
        }
        return var5;
    }
    
    public abstract PathPoint HorizonCode_Horizon_È(final Entity p0);
    
    public abstract PathPoint HorizonCode_Horizon_È(final Entity p0, final double p1, final double p2, final double p3);
    
    public abstract int HorizonCode_Horizon_È(final PathPoint[] p0, final Entity p1, final PathPoint p2, final PathPoint p3, final float p4);
}
