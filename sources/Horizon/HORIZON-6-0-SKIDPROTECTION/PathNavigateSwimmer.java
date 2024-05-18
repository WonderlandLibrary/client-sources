package HORIZON-6-0-SKIDPROTECTION;

public class PathNavigateSwimmer extends PathNavigate
{
    private static final String Âµá€ = "CL_00002244";
    
    public PathNavigateSwimmer(final EntityLiving p_i45873_1_, final World worldIn) {
        super(p_i45873_1_, worldIn);
    }
    
    @Override
    protected PathFinder_1163157884 HorizonCode_Horizon_È() {
        return new PathFinder_1163157884(new SwimNodeProcessor());
    }
    
    @Override
    protected boolean áŒŠÆ() {
        return this.áˆºÑ¢Õ();
    }
    
    @Override
    protected Vec3 Ø() {
        return new Vec3(this.HorizonCode_Horizon_È.ŒÏ, this.HorizonCode_Horizon_È.Çªà¢ + this.HorizonCode_Horizon_È.£ÂµÄ * 0.5, this.HorizonCode_Horizon_È.Ê);
    }
    
    @Override
    protected void Âµá€() {
        final Vec3 var1 = this.Ø();
        final float var2 = this.HorizonCode_Horizon_È.áŒŠ * this.HorizonCode_Horizon_È.áŒŠ;
        final byte var3 = 6;
        if (var1.à(this.Ý.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Ý.Âµá€())) < var2) {
            this.Ý.HorizonCode_Horizon_È();
        }
        for (int var4 = Math.min(this.Ý.Âµá€() + var3, this.Ý.Ø­áŒŠá() - 1); var4 > this.Ý.Âµá€(); --var4) {
            final Vec3 var5 = this.Ý.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, var4);
            if (var5.à(var1) <= 36.0 && this.HorizonCode_Horizon_È(var1, var5, 0, 0, 0)) {
                this.Ý.Ý(var4);
                break;
            }
        }
        this.HorizonCode_Horizon_È(var1);
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
    }
    
    @Override
    protected boolean HorizonCode_Horizon_È(final Vec3 p_75493_1_, final Vec3 p_75493_2_, final int p_75493_3_, final int p_75493_4_, final int p_75493_5_) {
        final MovingObjectPosition var6 = this.Â.HorizonCode_Horizon_È(p_75493_1_, new Vec3(p_75493_2_.HorizonCode_Horizon_È, p_75493_2_.Â + this.HorizonCode_Horizon_È.£ÂµÄ * 0.5, p_75493_2_.Ý), false, true, false);
        return var6 == null || var6.HorizonCode_Horizon_È == MovingObjectPosition.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
    }
}
