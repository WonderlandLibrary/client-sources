package HORIZON-6-0-SKIDPROTECTION;

public class PathNavigateClimber extends PathNavigateGround
{
    private BlockPos Ó;
    private static final String à = "CL_00002245";
    
    public PathNavigateClimber(final EntityLiving p_i45874_1_, final World worldIn) {
        super(p_i45874_1_, worldIn);
    }
    
    @Override
    public PathEntity HorizonCode_Horizon_È(final BlockPos p_179680_1_) {
        this.Ó = p_179680_1_;
        return super.HorizonCode_Horizon_È(p_179680_1_);
    }
    
    @Override
    public PathEntity HorizonCode_Horizon_È(final Entity p_75494_1_) {
        this.Ó = new BlockPos(p_75494_1_);
        return super.HorizonCode_Horizon_È(p_75494_1_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final Entity p_75497_1_, final double p_75497_2_) {
        final PathEntity var4 = this.HorizonCode_Horizon_È(p_75497_1_);
        if (var4 != null) {
            return this.HorizonCode_Horizon_È(var4, p_75497_2_);
        }
        this.Ó = new BlockPos(p_75497_1_);
        this.Ø­áŒŠá = p_75497_2_;
        return true;
    }
    
    @Override
    public void Ø­áŒŠá() {
        if (!this.Ó()) {
            super.Ø­áŒŠá();
        }
        else if (this.Ó != null) {
            final double var1 = this.HorizonCode_Horizon_È.áŒŠ * this.HorizonCode_Horizon_È.áŒŠ;
            if (this.HorizonCode_Horizon_È.Ý(this.Ó) >= var1 && (this.HorizonCode_Horizon_È.Çªà¢ <= this.Ó.Â() || this.HorizonCode_Horizon_È.Ý(new BlockPos(this.Ó.HorizonCode_Horizon_È(), MathHelper.Ý(this.HorizonCode_Horizon_È.Çªà¢), this.Ó.Ý())) >= var1)) {
                this.HorizonCode_Horizon_È.ŒÏ().HorizonCode_Horizon_È(this.Ó.HorizonCode_Horizon_È(), this.Ó.Â(), this.Ó.Ý(), this.Ø­áŒŠá);
            }
            else {
                this.Ó = null;
            }
        }
    }
}
