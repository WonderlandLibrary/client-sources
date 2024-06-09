package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIFollowOwner extends EntityAIBase
{
    private EntityTameable Ø­áŒŠá;
    private EntityLivingBase Âµá€;
    World HorizonCode_Horizon_È;
    private double Ó;
    private PathNavigate à;
    private int Ø;
    float Â;
    float Ý;
    private boolean áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00001585";
    
    public EntityAIFollowOwner(final EntityTameable p_i1625_1_, final double p_i1625_2_, final float p_i1625_4_, final float p_i1625_5_) {
        this.Ø­áŒŠá = p_i1625_1_;
        this.HorizonCode_Horizon_È = p_i1625_1_.Ï­Ðƒà;
        this.Ó = p_i1625_2_;
        this.à = p_i1625_1_.Š();
        this.Ý = p_i1625_4_;
        this.Â = p_i1625_5_;
        this.HorizonCode_Horizon_È(3);
        if (!(p_i1625_1_.Š() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        final EntityLivingBase var1 = this.Ø­áŒŠá.ŒÐƒà();
        if (var1 == null) {
            return false;
        }
        if (this.Ø­áŒŠá.áˆºÕ()) {
            return false;
        }
        if (this.Ø­áŒŠá.Âµá€(var1) < this.Ý * this.Ý) {
            return false;
        }
        this.Âµá€ = var1;
        return true;
    }
    
    @Override
    public boolean Â() {
        return !this.à.Ó() && this.Ø­áŒŠá.Âµá€(this.Âµá€) > this.Â * this.Â && !this.Ø­áŒŠá.áˆºÕ();
    }
    
    @Override
    public void Âµá€() {
        this.Ø = 0;
        this.áŒŠÆ = ((PathNavigateGround)this.Ø­áŒŠá.Š()).á();
        ((PathNavigateGround)this.Ø­áŒŠá.Š()).HorizonCode_Horizon_È(false);
    }
    
    @Override
    public void Ý() {
        this.Âµá€ = null;
        this.à.à();
        ((PathNavigateGround)this.Ø­áŒŠá.Š()).HorizonCode_Horizon_È(true);
    }
    
    @Override
    public void Ø­áŒŠá() {
        this.Ø­áŒŠá.Ñ¢á().HorizonCode_Horizon_È(this.Âµá€, 10.0f, this.Ø­áŒŠá.áˆºà());
        if (!this.Ø­áŒŠá.áˆºÕ() && --this.Ø <= 0) {
            this.Ø = 10;
            if (!this.à.HorizonCode_Horizon_È(this.Âµá€, this.Ó) && !this.Ø­áŒŠá.ÇŽà() && this.Ø­áŒŠá.Âµá€(this.Âµá€) >= 144.0) {
                final int var1 = MathHelper.Ý(this.Âµá€.ŒÏ) - 2;
                final int var2 = MathHelper.Ý(this.Âµá€.Ê) - 2;
                final int var3 = MathHelper.Ý(this.Âµá€.£É().Â);
                for (int var4 = 0; var4 <= 4; ++var4) {
                    for (int var5 = 0; var5 <= 4; ++var5) {
                        if ((var4 < 1 || var5 < 1 || var4 > 3 || var5 > 3) && World.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, new BlockPos(var1 + var4, var3 - 1, var2 + var5)) && !this.HorizonCode_Horizon_È.Â(new BlockPos(var1 + var4, var3, var2 + var5)).Ý().áˆºÑ¢Õ() && !this.HorizonCode_Horizon_È.Â(new BlockPos(var1 + var4, var3 + 1, var2 + var5)).Ý().áˆºÑ¢Õ()) {
                            this.Ø­áŒŠá.Â(var1 + var4 + 0.5f, var3, var2 + var5 + 0.5f, this.Ø­áŒŠá.É, this.Ø­áŒŠá.áƒ);
                            this.à.à();
                            return;
                        }
                    }
                }
            }
        }
    }
}
